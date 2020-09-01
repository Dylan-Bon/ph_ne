package Obj;
//java.time is unfamiliar
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class Transaction {
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private final int CUSTOMER_ID, TRANSACTION_ID;
    private String dateOfTransaction, deliveryDate, paymentDate;
    private ArrayList<StockItem> itemsOrdered = new ArrayList<>();
    private ArrayList<Integer> quantity = new ArrayList<>();
    private double subtotal, vatCharges, total;
    /**
     * Item price before tax
     */
    private ArrayList<Double> itemRowPrice = new ArrayList<>();

    /**
     * Tax to be applied to item price
     */
    private ArrayList<Double> unitPrice = new ArrayList<>();

    /**
     * Transaction hold all information about an order (Transaction)
     * dateOfTransaction is set automatically
     * @param TRANSACTION_ID
     * @param CUSTOMER_ID
     */
    Transaction(int TRANSACTION_ID, int CUSTOMER_ID){

        Date currentDate = new Date();
        LocalDateTime localDateTime = currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        dateOfTransaction = dateFormatter.format(localDateTime);

        localDateTime = localDateTime.plusDays(3);
        paymentDate = dateFormatter.format(localDateTime);

        localDateTime = localDateTime.plusDays(2);
        deliveryDate = dateFormatter.format(localDateTime);

        this.TRANSACTION_ID = TRANSACTION_ID;
        this.CUSTOMER_ID = CUSTOMER_ID;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public String getDateOfTransaction() {
        return dateOfTransaction;
    }

    /**
     * @param index an index for itemRowPrice ArrayList.
     * @return the item row price at the index.
     */
    public double getIndexedItemRowPrice(int index){
        return itemRowPrice.get(index);
    }

    /**
     * @param index an index for unitPrice ArrayList.
     * @return the unit price at the index.
     */
    public double getIndexedUnitPrice(int index){
        return unitPrice.get(index);
    }

    /**
     * @param index an index for itemsOrdered ArrayList.
     * @return the name of the StockItem at the index.
     */
    public String getIndexedTransactionItemName(int index){
        return itemsOrdered.get(index).getName();
    }

    /**
     * @param index an index for itemsOrdered ArrayList.
     * @return the description of the StockItem at the index.
     */
    public String getIndexedTransactionItemDescription(int index){
        return itemsOrdered.get(index).getDescription();
    }

    /**
     * @param index an index for itemsOrdered ArrayList.
     * @return the ITEM_ID of the StockItem at the index.
     */
    public int getIndexedItemId(int index) {
        return itemsOrdered.get(index).getITEM_ID();
    }

    /**
     * Adds a new Stock Item and the quantity ordered, to this Transaction.
     * @param stockItem the StockItem to be added.
     * @param quantity the quantity of the StockItem that has been ordered.
     */
    public void addItemsAndQuantity(StockItem stockItem, int quantity){
        this.itemsOrdered.add(stockItem);
        this.quantity.add(quantity);
    }

    public ArrayList<StockItem> getItemsOrdered() {
        return itemsOrdered;
    }

    /**
     * Returns item ordered at given index
     * @param index
     * @return itemsOrdered @ index
     */
    public StockItem getIndexedItemOrdered(int index){
        return itemsOrdered.get(index);
    }

    void setItemsOrdered(ArrayList<StockItem> itemsOrdered) {
        this.itemsOrdered = itemsOrdered;
    }

    public ArrayList<Integer> getQuantity() {
        return quantity;
    }

    /**
     * Returns item quantity at the given index
     * @param index
     * @return quantity @ index
     */
    public int getIndexedQuantity(int index){
        return quantity.get(index);
    }

    public void setQuantity(ArrayList<Integer> quantity) {
        this.quantity = quantity;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getVatCharges() {
        return vatCharges;
    }

    public void setVatCharges(double vatCharges) {
        this.vatCharges = vatCharges;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getTRANSACTION_ID() {
        return TRANSACTION_ID;
    }

    public int getCUSTOMER_ID() {
        return CUSTOMER_ID;
    }

    /**
     * Calculates the itemRowPrice.
     * This should be called once the itemsOrdered and quantity ArrayLists have been populated.
     */
    public void calculateItemRowPrices(){
        for(int i=0;i<itemsOrdered.size();i++){
            itemRowPrice.add(calculateItemRowPrice(Integer.parseInt(itemsOrdered.get(i).getPrice()), quantity.get(i)));
        }
        for(int i=0;i<itemsOrdered.size();i++){
            unitPrice.add(calculateUnitPrice(Integer.parseInt(itemsOrdered.get(i).getPrice())));
        }
    }

    /**
     * Calculates pre-tax unit price.
     * @param postTaxPrice the standard price of an item (inc. tax).
     * @return unitPrice
     */
    private double calculateUnitPrice(double postTaxPrice){
        double unitPrice = ((postTaxPrice/100)*80);
        return unitPrice;
    }

    /**
     * Calculates pre-tax item price for a quantity of one item
     * @param postTaxPrice
     * @param quantity
     * @return itemRowPrice
     */
    private double calculateItemRowPrice(double postTaxPrice, int quantity){
        double itemRowPrice = ((postTaxPrice/100)*80)*quantity;
        return itemRowPrice;
    }

    /**
     * Calculates Total, overall VAT Charge and Subtotal
     */
    public void calculateTotals(){
        this.total = calculateTotal();
        calculateOtherTotals();
    }

    /**
     * @return total
     */
    private double calculateTotal(){
        int total = 0;
        for (int i=0;i<itemsOrdered.size();i++){
            total+=(Integer.parseInt(itemsOrdered.get(i).getPrice())*quantity.get(i));
        }
        return total;
    }

    /**
     * Calculates and sets the total VAT charge (vatCharge) and subtotal
     */
    private void calculateOtherTotals(){
        double vatCharge = (total/100)*20;
        double subtotal = total-vatCharge;

        this.vatCharges = vatCharge;
        this.subtotal = subtotal;
    }

    public ArrayList<Double> getItemRowPrice() {
        return itemRowPrice;
    }

    public void setItemRowPrice(ArrayList<Double> itemRowPrice) {
        this.itemRowPrice = itemRowPrice;
    }

    public ArrayList<Double> getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(ArrayList<Double> unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setDateOfTransaction(String dateOfTransaction) {
        this.dateOfTransaction = dateOfTransaction;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }
}