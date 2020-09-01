package UI;
/**
 * ph_ne
 * Version 0.1
 * Current version allows for:
 * making an order & producing an invoice for the order,
 * monitoring and altering stock levels,
 * addition of new customers + suppliers + stock items,
 * view a stock 'report' with the ability to filter 'low stock' and export to PDF,
 * customer and supplier's addresses and contact numbers may be updated
 *
 * Any customers, suppliers, stock and transactions are saved and read from a text file for permanent storage
 *
 * STILL TO IMPLEMENT - login screen and separate user levels (user and admin), customer sales report,
 * removal of StockItems needs to be fixed (this is currently disabled and it's relevant methods are marked as deprecated).
 *
 * @author Dylan Bonner
 */

import Obj.DataManager;
import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Driver {
    public static MakeTransaction makeTransactionWindow = null;
    private static JFrame cartFrame;
    private static final DataManager manager = new DataManager();
    private static Cart cart;
    private static TransactionCompleted transactionCompleted;
    private static ManageStock manageStock;
    private static AddSupplier addSupplier;
    private static AddStockItem addStockItem;
    private static final ViewReports viewReports =  new ViewReports();
    private static StockReport stockReport;
    private static CustomerSalesReport customerSalesReport;
    private static CustomerAndSupplierInfo customerAndSupplierInfo;

    public static void main(String args[]){
        initialiseSystem();
        initialiseUI();
    }

    /**
     * Will read in current customer, supplier, transaction and stock details.
     * TO ADD - If FileNotFoundException is thrown, offer to create a new file.
     */
    private static void initialiseSystem() {
        try {
            manager.fetchCurrentCustomers();
        } catch (FileNotFoundException e) {
            int selection = JOptionPane.showConfirmDialog(null, "Could not find Customer.txt, would you like to create a new file?",
                    "File Not Found", JOptionPane.YES_NO_OPTION);
            if (selection==JOptionPane.YES_OPTION){
                manager.createNewFile("Customer.txt");
            } else{
                System.exit(0);
            }
        }
        try {
            manager.fetchCurrentSuppliers();
        } catch (FileNotFoundException e) {
            int selection = JOptionPane.showConfirmDialog(null, "Could not find Supplier.txt, would you like to create a new file?",
                    "File Not Found", JOptionPane.YES_NO_OPTION);
            if (selection==JOptionPane.YES_OPTION){
                manager.createNewFile("Supplier.txt");
            } else{
                System.exit(0);
            }
        }
        try {
            manager.fetchCurrentStock();
        } catch (FileNotFoundException e) {
            int selection = JOptionPane.showConfirmDialog(null, "Could not find Stock.txt, would you like to create a new file?",
                    "File Not Found", JOptionPane.YES_NO_OPTION);
            if (selection==JOptionPane.YES_OPTION){
                manager.createNewFile("Stock.txt");
            } else{
                System.exit(0);
            }
        }
        try {
            manager.fetchCurrentTransaction();
        } catch (FileNotFoundException e) {
            int selection = JOptionPane.showConfirmDialog(null, "Could not find Transaction.txt, would you like to create a new file?",
                    "File Not Found", JOptionPane.YES_NO_OPTION);
            if (selection==JOptionPane.YES_OPTION){
                manager.createNewFile("Transaction.txt");
            } else{
                System.exit(0);
            }
        }

    }

    static JFrame frame = new JFrame("ph_ne");

    /**
     * Launches the first UI page (main menu) and sets default close operation of frame.
     */
    private static void initialiseUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new MainMenu().getPanelMainMenu());

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    /**
     * @param postcode - the postcode to be validated.
     * @return true if postcode is valid, allows for an optional space character between postcode segments.
     */
    public static boolean validatePostcode(String postcode){
        return manager.validatePostcode(postcode);
    }

    /**
     * Swaps the UI window to makeTransactionWindow and populates the storefront to include items
     */
    static void swapToMakeTransaction(){
        //works
        makeTransactionWindow = new MakeTransaction();
        manager.populateStore();
        makeTransactionWindow.populateCmbExContactNumber(manager.getExistingContactNumberList());
        frame.setContentPane(makeTransactionWindow.getPanelMakeTransaction());

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    /**
     * Swaps the UI window to home/main menu page
     */
    static void swapToHome(){
        frame.setContentPane(new MainMenu().getPanelMainMenu());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
    //<-------Cart------->
    /**
     * Displays the cart page for an existing customer
     */
    static void displayCart(int existingContactNumberIndex, boolean existingCustomer){
        cartFrame = new JFrame();
        cartFrame.setTitle("Cart");
        cartFrame.setPreferredSize(new Dimension(300,400));
        cartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cart = new Cart(existingContactNumberIndex, existingCustomer);
        String names[] = manager.getCurrentCartItemNames();
        int prices[] = manager.getCurrentCartItemPrices();
        int yPos=0;
        //setup names
        for(int i=0;i<prices.length;i++){
            cart.generateCartItem(names[i], prices[i], yPos);
            yPos++;
        }
        //setup total
        int totalPrice=0;
        for (int price : prices) {
            totalPrice += price;
        }
        cart.setLblTotal(totalPrice);
        cartFrame.setContentPane(cart.getCartPanel());
        cartFrame.pack();
        cartFrame.setLocationRelativeTo(null); //display in center of screen
        cartFrame.setVisible(true);

    }
    /**
     * Displays the cart page for a new customer
     */
    static void displayCart(String firstName, String lastName, String fLineAddress, String sLineAddress, String postcode, String contactNumber, boolean existingCustomer){
        cartFrame = new JFrame();
        cartFrame.setTitle("Cart");
        cartFrame.setPreferredSize(new Dimension(300,400));
        cartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cart = new Cart(firstName, lastName,fLineAddress,sLineAddress,postcode,contactNumber,existingCustomer);
        String names[] = manager.getCurrentCartItemNames();
        int prices[] = manager.getCurrentCartItemPrices();
        int yPos=0;
        //setup names
        for(int i=0;i<prices.length;i++){
            cart.generateCartItem(names[i], prices[i], yPos);
            yPos++;
        }
        //setup total
        int totalPrice=0;
        for (int price : prices) {
            totalPrice += price;
        }
        cart.setLblTotal(totalPrice);
        cartFrame.setContentPane(cart.getCartPanel());
        cartFrame.pack();
        cartFrame.setLocationRelativeTo(null); //display in center of screen
        cartFrame.setVisible(true);

    }

    /**
     * Adds an item to the cart using the item ID.
     * @param itemID self explanatory
     */
    static void addToCart(int itemID){
        manager.addToCart(itemID);
    }

    /**
     * Removes the item from the cart, disposes the old cart and displays/creates a new cart
     * @param name self explanatory
     */
    static void removeFromCart(String name){
        JOptionPane.showMessageDialog(null, manager.removeCartItem(name),"Remove Item", JOptionPane.INFORMATION_MESSAGE);
        //check if existing
        boolean existingCustomer;
        int existingContactNumberIndex = 0;
        String firstName = "";
        String lastName = "";
        String fLineAddress = "";
        String sLineAddress = "";
        String postcode = "";
        String contactNumber = "";
        existingCustomer = cart.isExistingCustomer();
        if(existingCustomer){
            existingContactNumberIndex = cart.getExistingContactNumberIndex();
        }else {
            firstName = cart.getFirstName();
            lastName = cart.getLastName();
            fLineAddress = cart.getfLineAddress();
            sLineAddress = cart.getsLineAddress();
            postcode = cart.getPostcode();
            contactNumber = cart.getContactNumber();
        }
        cartFrame.dispose();
        cartFrame.setVisible(false);
        //call correct method to construct
        if(existingCustomer){
            displayCart(existingContactNumberIndex, true);
        } else{
            displayCart(firstName,lastName,fLineAddress,sLineAddress,postcode,contactNumber, false);
        }

    }

    /**
     * Creates a new Transaction for existing customers
     * @param existingContactNumberIndex the index of the Customer in customerList located in DataManager
     */
    static void generateTransaction(int existingContactNumberIndex){
        System.out.println(existingContactNumberIndex);
        manager.newTransaction(existingContactNumberIndex);
        cartFrame.dispose();
        cartFrame.setVisible(false);
    }

    /**
     * Creates a new Transaction and a new Customer
     * @param firstName
     * @param lastName
     * @param fLineAddress
     * @param sLineAddress
     * @param postcode
     * @param contactNumber
     */
    static void generateTransaction(String firstName, String lastName, String fLineAddress, String sLineAddress, String postcode, String contactNumber){
        int custIndex = manager.newCustomer(firstName,lastName,fLineAddress,sLineAddress,postcode,contactNumber);
        manager.newTransaction(custIndex);
        cartFrame.dispose();
        cartFrame.setVisible(false);
    }

    /**
     * @return true if existing customer, false if not
     */
    static boolean isExistingCustomer(){
        return makeTransactionWindow.isExistingCustomer();
    }

    /**
     * This is called when there is not enough stock to fulfil an order.
     * Displays a warning message to let the user know what items are unavailable, and what the available quantity of the item(s) is.
     * @param unavailableQuantity
     */
    public static void stockUnavailable(ArrayList<String> unavailableQuantity) {
        String unavailable = "";

        for (String s :
                unavailableQuantity) {
            String unavailableItem = "\n".concat(s);
            unavailable = unavailable.concat(unavailableItem);
        }
        JOptionPane.showMessageDialog(frame,"Some items were unavailable, cannot process the transaction for current items:"+unavailable,
                "Items Unavailable", JOptionPane.WARNING_MESSAGE);
    }
    
    //<-------!Cart!------->

    //<=======TransactionCompleted=======>

    /**
     * Sets the current contentPane to display a new TransactionCompleted window.
     * Calls populateTransactionCustomerDetails(), populateItemsTable() and populateTotalsTable().
     */
    public static void swapToTransactionCompleted(){
        transactionCompleted = new TransactionCompleted();
        populateTransactionCustomerDetails();
        populateItemsTable();
        populateTotalsTable();
        frame.setContentPane(transactionCompleted.getTransactionCompletedPanel());

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        Dimension minimumSize = new Dimension(700,500);
        frame.setMinimumSize(minimumSize);
        frame.setVisible(true);
    }

    /**
     * Populates part of the invoice that is displayed in TransactionCompleted.
     * Adds transaction id, all dates and all customer details to the 'invoice'.
     */
    private static void populateTransactionCustomerDetails(){
        transactionCompleted.setLblTransactionID(Integer.toString(manager.getMaxTransactionId()));
        transactionCompleted.setLblDate(manager.getTransactionDate(manager.getTransactionList().size()-1));
        transactionCompleted.setLblCustName(manager.getCustomerNameFromLastTransaction());
        transactionCompleted.setLblCustID(Integer.toString(manager.getCustomerIdFromLastTransaction()));
        transactionCompleted.setLblAddress(manager.getCustomerAddressFromLastTransaction());
        transactionCompleted.setLblPostcode(manager.getCustomerPostcodeFromLastTransaction());
        transactionCompleted.setLblDeliveryDate(manager.getTransactionDeliveryDate(manager.getTransactionList().size()-1));
        transactionCompleted.setLblPaymentDate(manager.getTransactionPaymentDate(manager.getTransactionList().size()-1));

    }

    /**
     * Populates part of the invoice that is displayed in TransactionCompleted.
     * Adds a new row in the invoice table for items ordered, quantity, unit price and line total for each individual StockItem ordered.
     */
    private static void populateItemsTable(){
        int transactionIndex = manager.getMaxTransactionId()-1;
        int numberOfItemRows = manager.getTransactionItemsSize(transactionIndex);
        for(int i = 0;i<numberOfItemRows;i++){
            int quantity = manager.getTransactionQuantity(transactionIndex,i);
            String itemName = manager.getTransactionItemName(transactionIndex, i);
            int itemId = manager.getTransactionItemId(transactionIndex,i);
            double unitPrice = manager.getTransactionItemUnitPrice(transactionIndex, i);
            double itemRowPrice = manager.getTransactionItemRowPrice(transactionIndex, i);

            Object data[] = {quantity, itemId, itemName, unitPrice, itemRowPrice};
            transactionCompleted.addTableData(data);
        }

    }

    /**
     * Populates part of the invoice that is displayed in TransactionCompleted.
     * Adds the subtotal, vat charges and overall total to the 'invoice'.
     */
    private static void populateTotalsTable(){
        int transactionIndex = manager.getMaxTransactionId()-1;
        double subtotal = manager.getTransactionSubtotal(transactionIndex);
        double vatCharges = manager.getTransactionVatCharges(transactionIndex);
        double total = manager.getTransactionTotal(transactionIndex);
        transactionCompleted.populateTableTotal(subtotal,vatCharges,total);
    }

    /**
     * Calls DataManager.emptyCart() to remove all values within the cart list.
     * This should be called once a Transaction has been completed.
     */
    static void emptyCart() {
        manager.emptyCart();
    }
    //<=======!TransactionCompleted!========>

    //<-------ManageStock-------->

    /**
     * Sets the current content pane to display the Manage Stock page
     * calls populateManageStockTable()
     */
    public static void swapToManageStock(){
        manageStock = new ManageStock();
        populateManageStockTable();

        frame.setContentPane(manageStock.getManageStockPanel());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    /**
     * Populates the table in ManageStock to show all StockItems; stock id, name, current quantity level, description, image and supplier id
     */
    private static void populateManageStockTable(){
        for(int i=0;i<manager.getStockList().size();i++){
            final int ITEM_ID = manager.GET_STOCKITEM_ID(i);
            String itemName = manager.getStockItemName(i);
            int qty = manager.getStockItemQuantity(i);
            String description = manager.getStockItemDescription(i);
            String imagePath = manager.getStockItemImagePath(i);
            final int SUPPLIER_ID = manager.GET_STOCKITEM_SUPPLIER_ID(i);
            Object tableRowData[] = {ITEM_ID,itemName,qty,description,imagePath,SUPPLIER_ID};
            manageStock.populateTblManageStock(tableRowData);
        }
    }

    /**
     * Updates the stock level for a StockItem.
     * @param stockItemName name of StockItem to be updated.
     * @param newQuantity the new quantity.
     */
    static void updateStockLevel(String stockItemName, int newQuantity){
        manager.updateStockLevel(stockItemName, newQuantity);
    }

    /**
     * @deprecated This is bad. If you remove a StockItem & try to view a transaction where this item was in, the system will explode - 2AM thoughts.
     * SYSTEM NEEDS NEW DESIGN FOR THIS TO WORK. This issue will be further explained during the testing report and the evaluation stage.
     * @param stockItemName the name of the StockItem to be removed
     */
    static void removeStockItem(String stockItemName){
        manager.removeStockItem(stockItemName);
    }

    /**
     * Sets the current content pane to addStockItemPanel
     * This page allows the user to add a new stock item to the system
     */
    public static void swapToAddStockItem(){
        addStockItem = new AddStockItem();
        addStockItem.populateSupplierComboBox(manager.getSupplierNames());

        frame.setContentPane(addStockItem.getAddStockItemPanel());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    /**
     * Sets the current content pane to addSupplierPanel
     * This page allows the user to add a new supplier to the system
     * A new Supplier may only be added when adding a new Item that doesn't come from an existing Supplier
     */
    public static void swapToAddSupplier() {
        addSupplier = new AddSupplier();

        frame.setContentPane(addSupplier.getAddSupplierPanel());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

    }

    /**
     * Adds a new Supplier and passes the supplierName to newStockItem so that a new StockItem can be instantiated.
     */
    public static void newSupplier(){
        String supplierName = addSupplier.getName();
        String fLineAddress = addSupplier.getFLineAddress();
        String sLineAddress = addSupplier.getSLineAddress();
        String postcode = addSupplier.getPostcode();
        String contactNumber = addSupplier.getContactNumber();

        manager.newSupplier(supplierName, fLineAddress, sLineAddress, postcode, contactNumber);
        newStockItem(supplierName);
    }

    /**
     * Adds a new StockItem to the system
     * @param supplierName the name of the supplier which supplies the new StockItem
     */
    public static void newStockItem(String supplierName){
        String itemName = addStockItem.getName();
        String description = addStockItem.getDescription();
        String imagePath = addStockItem.getImagePath();
        String price = addStockItem.getPrice();
        int quantity = addStockItem.getQuantity();

        manager.newStockItem(itemName, description, supplierName, imagePath, price, quantity);
    }
    //<-------!ManageStock!-------->

    //<=======CustomerAndSupplierInfo========>

    /**
     * Swaps the content pane to the main panel of the customerAndSupplierInfo page
     * This page allows the user to view each of both Supplier's and Customer's; id's, names, contact number, addresses and postcodes
     * Contact Number's and Addresses + postcodes can be updated here.
     */
    public static void swapToCustomerAndSupplierInfo(){
        customerAndSupplierInfo = new CustomerAndSupplierInfo();

        for (int i = 0; i < manager.getCustomerList().size(); i++){
            Object[] row = manager.getCustomerDetails(i);
            customerAndSupplierInfo.populateCustomerModel(row);
        }
        for (int i = 0; i < manager.getSupplierList().size(); i++){
            Object[] row = manager.getSupplierDetails(i);
            customerAndSupplierInfo.populateSupplierModel(row);
        }

        frame.setContentPane(customerAndSupplierInfo.getcAndSPanel());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    /**
     * Updates the selected Customer's contact number.
     * @param id the id of the specific Customer.
     * @param contactNum the new contact number.
     */
    static void updateCustomerContactNumber(int id, String contactNum) {
        manager.updateCustomerContactNumber(id, contactNum);
    }

    /**
     * Updates the selected Customer's full address including postcode.
     * @param id the id of the specific Customer.
     * @param fLineAddress the new first line of address.
     * @param sLineAddress the new second line of address.
     * @param postcode the new postcode.
     */
    static void updateCustomerAddress(int id, String fLineAddress, String sLineAddress, String postcode) {
        manager.updateCustomerAddress(id, fLineAddress, sLineAddress, postcode);
    }

    /**
     * Updates the selected Supplier's contact number
     * @param id the id of the specific Supplier
     * @param contactNum the new contact number
     */
    public static void updateSupplierContactNumber(int id, String contactNum) {
        manager.updateSupplierContactNumber(id, contactNum);
    }

    /**
     * Updates the selected Supplier's full address including postcode.
     * @param id the id of the specific Supplier.
     * @param fLineAddress the new first line of address.
     * @param sLineAddress the new second line of address.
     * @param postcode the new postcode.
     */
    public static void updateSupplierAddress(int id, String fLineAddress, String sLineAddress, String postcode) {
        manager.updateSupplierAddress(id, fLineAddress, sLineAddress, postcode);
    }
    //<=======!CustomerAndSupplierInfo!========>


    //<-------REPORTS-------->

    /**
     * Swaps the content pane to the main panel of the viewReports page
     * This page allows the user to choose between viewing a stock report or customer sales report
     * NOTE - CUSTOMER SALES REPORT NOT IMPLEMENTED
     */
    public static void swapToViewReports(){
        frame.setContentPane(viewReports.getViewReportsPanel());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    /**
     * Swaps the content pane to the main panel of stockReport
     * This page allows the user to view a 'stock' report and filter the report to view 'low' stock with a quantity of 5 or less.
     * This page also supports exporting to PDF, currently the output is stored at src/output/StockReport.pdf
     */
    public static void swapToStockReport() {
        stockReport = new StockReport();
        populateStockTable();
        populateFilteredStockTable();

        frame.setContentPane(stockReport.getStockReportPanel());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    /**
     * Populates the contents of stockTable with each StockItem's; id, name, qty, supplier id, description.
     */
    public static void populateStockTable(){
        for(int i=0;i<manager.getStockList().size();i++){
            final int ITEM_ID = manager.GET_STOCKITEM_ID(i);
            String itemName = manager.getStockItemName(i);
            int qty = manager.getStockItemQuantity(i);
            String description = manager.getStockItemDescription(i);
            final int SUPPLIER_ID = manager.GET_STOCKITEM_SUPPLIER_ID(i);

            Object tableRowData[] = {ITEM_ID, itemName, qty, SUPPLIER_ID, description};

            stockReport.populateTblStock(tableRowData);
        }
    }

    /**
     * Populates the contents of filteredStock with StockItem's that have a current quantity of 5 or less.
     * The details stored of the StockItem's are; id, name, qty, supplier id, description.
     */
    public static void populateFilteredStockTable(){
        for(int i=0;i<manager.getStockList().size();i++) {
            final int ITEM_ID = manager.GET_STOCKITEM_ID(i);
            String itemName = manager.getStockItemName(i);
            int qty = manager.getStockItemQuantity(i);
            String description = manager.getStockItemDescription(i);
            final int SUPPLIER_ID = manager.GET_STOCKITEM_SUPPLIER_ID(i);

            if (qty < 6) {
                Object tableRowData[] = {ITEM_ID, itemName, qty, SUPPLIER_ID, description};
                stockReport.populateFilteredStock(tableRowData);
            }
        }
    }

    /**
     * Swaps the content pane to the main panel of the CustomerSalesReport page
     * THIS PAGE IS NOT FULLY IMPLEMENTED - The report does not generate currently.
     */
    public static void swapToCustomerSalesReport() {
        customerSalesReport = new CustomerSalesReport();
        customerSalesReport.populateCmbCustomerId(manager.getCustomerIds());
                
        frame.setContentPane(customerSalesReport.getCustomerSalesReportPanel());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    /**
     * UNIMPLEMENTED METHOD - to be used for generating Customer Sales Report
     * @param customerId
     * @param month
     * @param year
     */
    public static void monthlyCustomerSalesReport(int customerId, String month, String year) {
        String customerName, contactNumber;
        String itemName, purchaseDate;
        int itemId, quantity, transactionId;
        double unitPrice, lineTotal, total; //total is different from total in Transaction, this total should be calculated by StockItem.getPrice()*quantity.

    }
    //<-------!REPORTS!-------->
}