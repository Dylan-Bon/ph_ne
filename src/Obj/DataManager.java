package Obj;

import UI.Driver;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataManager {
    private final String customerFilePath = "src"+ File.separator+"Interface"+File.separator+"Customer.txt";
    private final String supplierFilePath = "src"+ File.separator+"Interface"+File.separator+"Supplier.txt";
    private final String stockFilePath = "src"+ File.separator+"Interface"+File.separator+"Stock.txt";
    private final String transactionFilePath = "src" + File.separator+"Interface"+File.separator+"Transaction.txt";

    private final File customerFile = new File(customerFilePath);
    private final File supplierFile = new File(supplierFilePath);
    private final File stockFile = new File(stockFilePath);
    private final File transactionFile = new File(transactionFilePath);
    /**
     * Used to store added items within cart
     */
    private ArrayList<StockItem> currentCart = new ArrayList();
    private int maxCustomerId = 0;
    private ArrayList<Customer> customerList = new ArrayList<>();
    private int maxSupplierId = 0;
    private ArrayList<Supplier> supplierList = new ArrayList<>();
    private int maxStockId = 0;
    private ArrayList<StockItem> stockList = new ArrayList<>();
    private int maxTransactionId = 0;
    private ArrayList<Transaction> transactionList = new ArrayList<>();

    /**
     * Reads in each line of customerFile, creates a new customer for each line and adds each customer to customerList.
     * Each line of the file is formatted as shown:-
     * Name\tSurname\tContactNumber\tFirstLineAddress\tSecondLineAddress\tPostcode\tID
     * @throws FileNotFoundException
     */
    public void fetchCurrentCustomers() throws FileNotFoundException {
        Scanner sc = new Scanner(customerFile);
        String fileLine = "";
        //
        int tabPos1;
        int tabPos2;
        int tabPos3;
        int tabPos4;
        int tabPos5;
        int tabPos6;

        while(sc.hasNextLine()){
            fileLine = sc.nextLine();
            tabPos1 = fileLine.indexOf('\t');
            tabPos2 = tabPos1+(fileLine.substring(tabPos1+1).indexOf('\t'))+1;
            tabPos3 = tabPos2+(fileLine.substring(tabPos2+1).indexOf('\t'))+1;
            tabPos4 = tabPos3+(fileLine.substring(tabPos3+1).indexOf('\t'))+1;
            tabPos5 = tabPos4+(fileLine.substring(tabPos4+1).indexOf('\t'))+1;
            tabPos6 = fileLine.lastIndexOf('\t');

            String name = fileLine.substring(0,tabPos1);
            String surname = fileLine.substring(tabPos1+1,tabPos2);
            String contactNumber = fileLine.substring(tabPos2+1, tabPos3);
            String firstLineAddress = fileLine.substring(tabPos3+1, tabPos4);
            String secondLineAddress = fileLine.substring(tabPos4+1, tabPos5);
            String postcode = fileLine.substring(tabPos5+1, tabPos6);
            int id = Integer.parseInt(fileLine.substring(tabPos6+1));
            if(maxCustomerId<id){
                maxCustomerId = id;
            }
            fileLine = "";

            customerList.add(new Customer(name, surname, contactNumber, firstLineAddress, secondLineAddress, postcode, id));
            //System.out.println(new Customer(name, surname, contactNumber, firstLineAddress, secondLineAddress, postcode, id)); for testing purposes
        }
    }

    /**
     * Reads in each line of supplierFile, creates a new supplier for each line and adds each supplier to supplierList.
     * Each line of the file is formatted as shown:-
     * Name\tContactNumber\tFirstLineAddress\tSecondLineAddress\tPostcode\tID
     * @throws FileNotFoundException
     */
    public void fetchCurrentSuppliers() throws FileNotFoundException {
        Scanner sc = new Scanner(supplierFile);
        String fileLine = "";

        int tabPos1;
        int tabPos2;
        int tabPos3;
        int tabPos4;
        int tabPos5;

        while(sc.hasNextLine()){
            fileLine = sc.nextLine();
            tabPos1 = fileLine.indexOf('\t');
            tabPos2 = tabPos1+(fileLine.substring(tabPos1+1).indexOf('\t'))+1;
            tabPos3 = tabPos2+(fileLine.substring(tabPos2+1).indexOf('\t'))+1;
            tabPos4 = tabPos3+(fileLine.substring(tabPos3+1).indexOf('\t'))+1;
            tabPos5 = fileLine.lastIndexOf('\t');

            String name = fileLine.substring(0,tabPos1);
            String contactNumber = fileLine.substring(tabPos1+1, tabPos2);
            String firstLineAddress = fileLine.substring(tabPos2+1, tabPos3);
            String secondLineAddress = fileLine.substring(tabPos3+1, tabPos4);
            String postcode = fileLine.substring(tabPos4+1, tabPos5);
            int id = Integer.parseInt(fileLine.substring(tabPos5+1));
            if(maxSupplierId<id){
                maxSupplierId = id;
            }
            fileLine = "";

            supplierList.add(new Supplier(name, contactNumber, firstLineAddress, secondLineAddress, postcode, id));
            //System.out.println(new Supplier(name, contactNumber, firstLineAddress, secondLineAddress, postcode, id)); testing purposes
        }
    }

    /**
     * Reads in each line of stockFile, creates a new stock item for each line and adds each item to stockList.
     * @throws FileNotFoundException
     */
    public void fetchCurrentStock() throws FileNotFoundException {
        Scanner sc = new Scanner(stockFile);
        String fileLine = "";
        //Name\tPrice\tDescription\tSupplierName\tItemImagePath\tItem_ID\tQuantity
        int tabPos1;
        int tabPos2;
        int tabPos3;
        int tabPos4;
        int tabPos5;
        int tabPos6;

        while(sc.hasNextLine()){
            //System.out.println("========reading stock item...=========");
            fileLine = sc.nextLine();
            tabPos1 = fileLine.indexOf('\t');
            tabPos2 = tabPos1+(fileLine.substring(tabPos1+1).indexOf('\t'))+1;
            tabPos3 = tabPos2+(fileLine.substring(tabPos2+1).indexOf('\t'))+1;
            tabPos4 = tabPos3+(fileLine.substring(tabPos3+1).indexOf('\t'))+1;
            tabPos5 = tabPos4+(fileLine.substring(tabPos4+1).indexOf('\t'))+1;
            tabPos6 = fileLine.lastIndexOf('\t');

            String name = fileLine.substring(0,tabPos1);
            String price = fileLine.substring(tabPos1+1,tabPos2);
            String description = fileLine.substring(tabPos2+1, tabPos3);
            String supplierName = fileLine.substring(tabPos3+1, tabPos4);
            String itemImagePath = fileLine.substring(tabPos4+1, tabPos5);
            int id = Integer.parseInt(fileLine.substring(tabPos5+1, tabPos6));
            int quantity = Integer.parseInt(fileLine.substring(tabPos6+1));

            if(maxStockId < id){
                maxStockId = id;
            }

            fileLine = "";
            //add StockItem(line) to stockList
            stockList.add(new StockItem(name, price, description, supplierName, itemImagePath, id, quantity));
            //System.out.println(new StockItem(name, price, description, supplierName, itemImagePath, id, quantity)); testing purposes
        }
    }

    /**
     * Reads in Transaction.txt and generates a new Transaction object per each Transaction saved in the file.
     * Transaction.txt stores Transactions in a format of:
     * NEW-TRANSACTION
     * TransactionID	CustomerID	dateOfTransaction	deliveryDate	paymentDate	subtotal	vatCharges	total
     * \nITEMS:	item1	item2	item3	item4....
     * \nQUANTITY:	qty1	qty2	qty3	qty4....
     * \nROW PRICES:	rp1	rp2	rp3	rp4....
     * \nUNIT PRICES:	up1	up2	up3	up4....
     * \nEND-OF-TRANSACTION
     */
    public void fetchCurrentTransaction() throws FileNotFoundException {
        Scanner sc = new Scanner(transactionFile);
        String fileLine = "";
        boolean transactionEnded = true;

        int transactionId, customerId;
        String dateOfTransaction, deliveryDate, paymentDate;
        double subtotal, vatCharges, total;
        ArrayList<StockItem> itemsOrdered;
        ArrayList<Integer> quantity;
        ArrayList<Double> itemRowPrice, unitPrice;
        int currentTab;
        int nextTab; //used to find the next TAB delimiter in the document, this is used to read in separated values

        while(sc.hasNextLine()){
            //System.out.println("=======reading transactions...=======");
            fileLine = sc.nextLine();

            if(transactionEnded){
                transactionEnded = false;

                fileLine = sc.nextLine();  //Move to first transaction line

                //GETTING FIRST LINE OF DETAILS
                currentTab= fileLine.indexOf('\t');
                transactionId = Integer.parseInt(fileLine.substring(0, currentTab));

                nextTab = currentTab+1 + fileLine.substring(currentTab+1).indexOf('\t');
                customerId = Integer.parseInt(fileLine.substring(currentTab+1, nextTab));

                currentTab = nextTab;
                nextTab = currentTab+1 + fileLine.substring(currentTab+1).indexOf('\t');
                dateOfTransaction = fileLine.substring(currentTab+1, nextTab);

                currentTab = nextTab;
                nextTab = currentTab+1 + fileLine.substring(currentTab+1).indexOf('\t');
                deliveryDate = fileLine.substring(currentTab+1, nextTab);

                currentTab = nextTab;
                nextTab = currentTab+1 + fileLine.substring(currentTab+1).indexOf('\t');
                paymentDate = fileLine.substring(currentTab+1, nextTab);

                currentTab = nextTab;
                nextTab = currentTab+1 + fileLine.substring(currentTab+1).indexOf('\t');
                subtotal = Double.parseDouble(fileLine.substring(currentTab+1, nextTab));

                currentTab = nextTab;
                nextTab = currentTab+1 + fileLine.substring(currentTab+1).indexOf('\t');
                vatCharges = Double.parseDouble(fileLine.substring(currentTab+1, nextTab));

                currentTab = nextTab;
                total = Double.parseDouble(fileLine.substring(currentTab+1));

                //ABOVE WORKS
                //ACQUIRED FIRST LINE OF DETAILS, advance scanner to get items ordered
                itemsOrdered = new ArrayList<>();
                fileLine = sc.nextLine();
                currentTab = fileLine.indexOf('\t');

                if(fileLine.substring(currentTab+1).contains("\t")) {
                    do {
                        nextTab = currentTab + 1 + fileLine.substring(currentTab+1).indexOf("\t");
                        for (StockItem s :
                                stockList) {
                            if (s.getName().equals(fileLine.substring(currentTab + 1, nextTab))) {
                                itemsOrdered.add(s);
                            }
                        }
                        currentTab = nextTab;
                    }while(fileLine.substring(currentTab+1).contains("\t"));
                }

                for (StockItem s :
                        stockList) {
                    if (s.getName().equals(fileLine.substring(currentTab + 1))) {
                        itemsOrdered.add(s);
                    }
                }

                //ACQUIRED ITEMS, advance scanner to get quantities
                quantity = new ArrayList<>();
                fileLine = sc.nextLine();
                currentTab = fileLine.indexOf('\t');

                if(fileLine.substring(currentTab+1).contains("\t")) {
                    do {
                        nextTab = currentTab + 1 + fileLine.substring(currentTab+1).indexOf("\t");
                        quantity.add(Integer.parseInt(fileLine.substring(currentTab+1, nextTab)));
                        currentTab = nextTab;
                    }while(fileLine.substring(currentTab+1).contains("\t"));
                }

                quantity.add(Integer.parseInt(fileLine.substring(currentTab+1)));

                //ACQUIRED QUANTITY, advance scanner to get item row prices
                itemRowPrice = new ArrayList<>();
                fileLine = sc.nextLine();
                currentTab = fileLine.indexOf('\t');

                if(fileLine.substring(currentTab+1).contains("\t")) {
                    do {
                        nextTab = currentTab + 1 + fileLine.substring(currentTab+1).indexOf("\t");
                        itemRowPrice.add(Double.parseDouble(fileLine.substring(currentTab+1, nextTab)));
                        currentTab = nextTab;
                    }while(fileLine.substring(currentTab+1).contains("\t"));
                }

                itemRowPrice.add(Double.parseDouble(fileLine.substring(currentTab+1)));

                //ACQUIRED ITEM ROW PRICES, advance scanner to get unit prices
                unitPrice = new ArrayList<>();
                fileLine = sc.nextLine();
                currentTab = fileLine.indexOf('\t');

                if(fileLine.substring(currentTab+1).contains("\t")) {
                    do {
                        nextTab = currentTab + 1 + fileLine.substring(currentTab+1).indexOf("\t");
                        unitPrice.add(Double.parseDouble(fileLine.substring(currentTab+1, nextTab)));
                        currentTab = nextTab;
                    }while(fileLine.substring(currentTab+1).contains("\t"));
                }

                unitPrice.add(Double.parseDouble(fileLine.substring(currentTab+1)));

                //ACQUIRED ALL TRANSACTION INFORMATION, now advance scanner then instantiate new Transaction and add to transactionList
                sc.nextLine();
                Transaction temp = new Transaction(transactionId, customerId);
                temp.setDateOfTransaction(dateOfTransaction);
                temp.setDeliveryDate(deliveryDate);
                temp.setPaymentDate(paymentDate);
                temp.setSubtotal(subtotal);
                temp.setVatCharges(vatCharges);
                temp.setTotal(total);
                temp.setItemsOrdered(itemsOrdered);
                temp.setQuantity(quantity);
                temp.setItemRowPrice(itemRowPrice);
                temp.setUnitPrice(unitPrice);

                if(transactionId>maxTransactionId){
                    maxTransactionId = transactionId;
                }

                transactionList.add(temp);
                transactionEnded = true;
            }
        }
    }

    /**
     * Creates a new text file, depending on the passed parameter
     * @param s the file to be made (either Stock.txt, Customer.txt, Supplier.txt or Transaction.txt)
     */
    public void createNewFile(String s) {

        if (s.equals("Stock.txt")){
            File newStockFile = new File(stockFilePath);
            try {
                FileWriter makeFile = new FileWriter(newStockFile);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Unable to make new file.");
                e.printStackTrace();
                System.exit(0);
            }
        }

        if (s.equals("Customer.txt")){
            File newCustomerFile = new File(customerFilePath);
            try {
                FileWriter makeFile = new FileWriter(newCustomerFile);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Unable to make new file.");
                e.printStackTrace();
                System.exit(0);
            }
        }

        if (s.equals("Supplier.txt")){
            File newSupplierFile = new File(supplierFilePath);
            try {
                FileWriter makeFile = new FileWriter(newSupplierFile);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Unable to make new file.");
                e.printStackTrace();
                System.exit(0);
            }
        }

        if (s.equals("Transaction.txt")){
            File newTransactionFile = new File(transactionFilePath);
            try {
                FileWriter makeFile = new FileWriter(newTransactionFile);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Unable to make new file.");
                e.printStackTrace();
                System.exit(0);
            }
        }
    }

    /**
     * Overwrites the current contents of Stock.txt with the contents of stockList.
     * This should be called when a change to a stockItem has been made - i.e when the quantity has been altered.
     */
    private void overwriteStockFile(){
        try(FileWriter overwrite = new FileWriter(stockFile,false)){
            for (int i=0; i<stockList.size();i++){
                StockItem tempSelection = stockList.get(i);
                if (i != 0) {
                    overwrite.write("\n");
                }
                overwrite.write(tempSelection.getName()+"\t"+tempSelection.getPrice()+"\t"+tempSelection.getDescription()+"\t"+tempSelection.getSupplierName()+"\t"+
                        tempSelection.getItemImagePath()+"\t"+tempSelection.getITEM_ID()+"\t"+tempSelection.getQuantity());
            }
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "ERROR - There was a problem when overwriting Stock.txt." +
                    "\nPlease ensure the file has not been removed, renamed or relocated", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Saves the Transaction located at transactionList.get(index) to Transaction.txt, appending the current information in the document.
     * @param index - the index of the Transaction within transactionList to be saved.
     */
    private void saveTransactionList(int index){
        Transaction temp = transactionList.get(index);

        try(FileWriter save = new FileWriter(transactionFile, true)){
            if(transactionFile.length()>1){
                save.write("\n");
            }

            save.write("NEW-TRANSACTION");
            save.write("\n" + temp.getTRANSACTION_ID() + "\t" + temp.getCUSTOMER_ID() + "\t" + temp.getDateOfTransaction() + "\t" + temp.getDeliveryDate() + "\t" +
                    temp.getPaymentDate() + "\t" + temp.getSubtotal() + "\t" + temp.getVatCharges() + "\t"+ temp.getTotal());

            //save StockItems
            save.write("\nITEMS:");
            for (StockItem s :
                    temp.getItemsOrdered()) {
                save.write("\t" + s.getName());
            }

            //save quantities
            save.write("\nQUANTITY:");
            for (int i :
                    temp.getQuantity()) {
                save.write("\t"+i);
            }

            //save item row prices
            save.write("\nROW PRICES:");
            for(double d :
                temp.getItemRowPrice()) {
                save.write("\t"+d);
                }

            //save unit prices
            save.write("\nUNIT PRICES:");
            for (double d :
                    temp.getUnitPrice()) {
                save.write("\t"+d);
            }
            save.write("\nEND-OF-TRANSACTION");
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "ERROR - There was a problem when writing to Transaction.txt." +
                    "\nPlease ensure the file has not been removed, renamed or relocated", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    //<--------list getters--------->
    public Collection<Customer> getCustomerList() {
        return customerList;
    }
    public Collection<Supplier> getSupplierList() {
        return supplierList;
    }
    public Collection<StockItem> getStockList() {
        return stockList;
    }
    public Collection<Transaction> getTransactionList() {
        return transactionList;
    }
    //<--------!list getters!-------->

    /**
     * @param postcode the postcode to be validated.
     * @return true if postcode is valid, allows for an optional space character between postcode segments.
     */
    public boolean validatePostcode(String postcode){
        //regex provided https://stackoverflow.com/questions/164979/regex-for-matching-uk-postcodes - User: gb2d
        String regex = "(GIR 0AA)|((([A-Z-[QVX]][0-9][0-9]?)|(([A-Z-[QVX]][A-Z-[IJZ]][0-9][0-9]?)|(([A-Z-[QVX]][0-9][A-HJKSTUW])|([A-Z-[QVX]][A-Z-[IJZ]][0-9][ABEHMNPRVWXY]))))\\s?[0-9][A-Z-[CIKMOV]]{2})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(postcode);
        return matcher.matches();
    }

    //<-------MakeTransaction------->
    /**
     * Populates the 'Make Transaction' page with all stock stored in stockList.
     * If a StockItem has no image, the default image is set to "samsungS20Plus.png" at present.
     */
    public void populateStore(){
        int counter = 0;
        for(StockItem s:getStockList()){
            if(s.getItemImagePath()==(null)){
                Driver.makeTransactionWindow.generateStoreItem("src/Interface/Img/Phones/samsungS20Plus.png",s.getName(),s.getDescription(),s.getPrice(),s.getITEM_ID(),counter);
            } else{
                Driver.makeTransactionWindow.generateStoreItem(s.getItemImagePath(),s.getName(),s.getDescription(),s.getPrice(),s.getITEM_ID(),counter);
            }
            counter++;
        }
    }

    /**
     * @return list of all contact numbers from customerList
     */
    public ArrayList<String> getExistingContactNumberList(){
        ArrayList<String> existingContactNumberList = new ArrayList<>();
        for (Customer c :
                getCustomerList()) {
            existingContactNumberList.add(c.getContactNumber());
        }
        return existingContactNumberList;
    }
    //<-------!MakeTransaction!------->

    //<-------Cart------->
    /**
     * Add stock item to currentCart.
     * @param ID the id of the StockItem to be added to the cart.
     */
    public void addToCart(int ID){
        for(StockItem s:stockList){
            if(ID==s.getITEM_ID()){
                currentCart.add(s);
            }
        }
    }

    /**
     * Returns all StockItem's that are stored in currentCart ArrayList.
     * @return currentCart.
     */
    public ArrayList<StockItem> getCurrentCart(){
        return currentCart;
    }

    /**
     * Returns an array of names stored within currentCart
     * @return itemNames[]
     */
    public String[] getCurrentCartItemNames(){
        String[] itemNames = new String[currentCart.size()];
        for(int i=0;i<itemNames.length;i++){
            itemNames[i] = currentCart.get(i).getName();
        }
        return itemNames;
    }

    /**
     * Returns an array of integer prices
     * @return itemPrices[]
     */
    public int[] getCurrentCartItemPrices(){
        int[] itemPrices = new int[currentCart.size()];
        for(int i=0;i<itemPrices.length;i++){
            itemPrices[i] = Integer.parseInt(currentCart.get(i).getPrice());
        }
        return itemPrices;
    }
    /**
     * @param name the name of the StockItem to be removed from currentCart.
     * @return "Item Removed" if the name is found within currentCart.
     */
    public String removeCartItem(String name){
        for(StockItem s:currentCart){
            if(name.equals(s.getName())){
                currentCart.remove(s);
                return "Item Removed.";
            }
        }
        return "Unable to Remove Item.";
    }

    /**
     * Empties all contents for currentCart when called.
     * This should be called after a transaction is made.
     */
    public void emptyCart(){
        currentCart.clear();
    }

    //<------!Cart!------->


    //<======TransactionCompleted=======>
    /**
     * Called to generate a new Transaction.
     * Generates new Transaction if all Stock is available, else calls Driver.stockUnavailable()
     * calls updateStockLevelForCurrentTransaction
     * calls overwriteStockFile - to save all changes to quantities.
     */
    public void newTransaction(int customerIndex){
        //collate all items that are of the same name
        ArrayList<StockItem> transactionItemsList = collateItems();
        //get qty of StockItem(s)
        ArrayList<Integer> quantity = getCollatedQuantity(transactionItemsList);
        //EVERYTHING ABOVE WORKS

        ArrayList <String> unavailableQuantity = new ArrayList<>();

        for(int i=0; i<transactionItemsList.size();i++){
            if(transactionItemsList.get(i).getQuantity()<quantity.get(i)){
                unavailableQuantity.add("Available Stock for "+transactionItemsList.get(i).getName()+ " is: "+transactionItemsList.get(i).getQuantity());
            }
        }

        if(!unavailableQuantity.isEmpty()){
            Driver.stockUnavailable(unavailableQuantity);
        } else{
            Transaction t = new Transaction(assignTransactionId(), customerList.get(customerIndex).getID());
            for(int i=0;i<transactionItemsList.size();i++){
                t.addItemsAndQuantity(transactionItemsList.get(i), quantity.get(i));
            }
            //do work
            t.calculateItemRowPrices();
            t.calculateTotals();
            transactionList.add(t);
            saveTransactionList(transactionList.size()-1);
            //deduct stock from current levels
            updateStockLevelForCurrentTransaction(transactionItemsList, quantity);
            overwriteStockFile();
            //display invoice window
            Driver.swapToTransactionCompleted();
        }
    }

    /**
     * Updates the stock level after a new Transaction has been made following an order.
     * @param transactionItemsList list of all items in the transaction.
     * @param quantity list of all quantities where the index of each quantity refers to the StockItem at the same index.
     */
    private void updateStockLevelForCurrentTransaction(ArrayList<StockItem> transactionItemsList, ArrayList<Integer> quantity){
        for(int i=0;i<transactionItemsList.size();i++){
            for (StockItem s :
                    stockList) {
                if (s.getName().equals(transactionItemsList.get(i).getName())){
                    int qtyToDeductBy = quantity.get(i);
                    int currentQty = s.getQuantity();

                    s.setQuantity(currentQty-qtyToDeductBy);
                }
            }
        }
    }

    /**
     * @return name of customer from last processed Transaction
     */
    public String getCustomerNameFromLastTransaction(){
        String customerName;
        int transactionCustomerId = getCustomerIdFromLastTransaction();
        for (Customer c :
                customerList) {
            if (c.getID()==transactionCustomerId){
                customerName = c.getName()+" "+c.getSurname();
                return customerName;
            }
        }
        return "name - unavailable";
    }

    /**
     * @return last processed transactions first line off address, second line address
     */
    public String getCustomerAddressFromLastTransaction(){
        String customerAddress;
        int transactionCustomerId = getCustomerIdFromLastTransaction();
        for (Customer c :
                customerList) {
            if (c.getID() == transactionCustomerId){
                customerAddress = c.getFirstLineAddress()+", "+c.getSecondLineAddress();
                return customerAddress;
            }
        }
        return "address - unavailable";
    }

    /**
     * @return postcode of customer from the last processed Transaction.
     */
    public String getCustomerPostcodeFromLastTransaction(){
        String customerPostcode;
        int transactionCustomerId = getCustomerIdFromLastTransaction();
        for (Customer c :
                customerList) {
            if (c.getID() == transactionCustomerId){
                customerPostcode = c.getPostcode();
                return customerPostcode;
            }
        }
        return "postcode - unavailable";
    }

    /**
     * @return id of the Customer that performed the previous transaction. This is used when generating the invoice.
     */
    public int getCustomerIdFromLastTransaction(){
        return transactionList.get(transactionList.size()-1).getCUSTOMER_ID();
    }

    /**
     * @return the highest Transaction id within transactionList
     */
    public int getMaxTransactionId(){
        return maxTransactionId;
    }

    /**
     * @return new unique Transaction id
     */
    private int assignTransactionId(){
        return maxTransactionId+=1;
    }

    /**
     * Instantiates a new Customer and adds to customerList.
     * @param firstName the first name of the customer.
     * @param lastName the surname of the customer.
     * @param fLineAddress the first line of address.
     * @param sLineAddress the second line of address.
     * @param postcode the postcode.
     * @param contactNumber 11 digit contact number for the customer.
     * @return the index of the Customer within customerList
     */
    public int newCustomer(String firstName, String lastName, String fLineAddress, String sLineAddress, String postcode, String contactNumber){
        int customerId = assignCustomerId();
        customerList.add(new Customer(firstName, lastName, contactNumber, fLineAddress, sLineAddress, postcode, customerId));
        saveCustomerList(customerId);
        return customerList.size()-1;
    }

    /**
     * Assigns a unique customer ID
     * @return maxCustomerId+1
     */
    private int assignCustomerId(){
        return maxCustomerId+=1;
    }

    /**
     * Returns index location of Customer that has customerId within customerList.
     * If customer is not found, return -123.
     * @param customerId
     * @return Customer
     */
    private int findCustomerById(int customerId){
        for (Customer c :
                customerList) {
            if (c.getID() == customerId) {
                return customerList.indexOf(c);
            }
        }
        return -123;
    }

    /**
     * Saves the Customer that has ID equal to customerId to Customer.txt
     * Appends the existing details in Customer.txt
     * @param customerId
     */
    private void saveCustomerList(int customerId){
        Customer temp = customerList.get(findCustomerById(customerId));
        customerFile.setWritable(true);
        try(FileWriter save = new FileWriter(customerFile, true)){
            if (customerFile.length() >= 1) {
                save.write("\n");
            }
            save.write(temp.getName()+"\t"+temp.getSurname()+"\t"+temp.getContactNumber()+"\t"+temp.getFirstLineAddress()+"\t"+temp.getSecondLineAddress()
            +"\t"+temp.getPostcode()+"\t"+temp.getID());
        }catch (IOException e){
            JOptionPane.showMessageDialog(null,"ERROR - Could not access Customer.txt, please ensure file has not been moved or renamed.",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Returns an ArrayList of unique StockItems, no duplicate values.
     * @return ArrayList<StockItem> transactionItems
     */
    private ArrayList<StockItem> collateItems(){
        ArrayList<StockItem> transactionItems = new ArrayList<>();
        //populate transactionItems with unique values from currentCart, no duplicates.
        for(StockItem s:currentCart){
            if(!transactionItems.contains(s)){
                transactionItems.add(s);
            }
        }
        return transactionItems;
    }

    /**
     * Returns an ArrayList of quantities, with an index corresponding to that of transactionItemsList within the method: newTransaction().
     * @param transactionItemsList
     * @return ArrayList<Integer> transactionQuantityList
     */
    private ArrayList<Integer> getCollatedQuantity(ArrayList<StockItem> transactionItemsList){
        ArrayList<Integer>  transactionQuantityList = new ArrayList<>();
        for (StockItem stockItem : transactionItemsList) {
            int qty = 0;
            for (StockItem s : currentCart) {
                if (stockItem.getName().equals(s.getName())) {
                    qty++;
                }
            }
            transactionQuantityList.add(qty);
        }
        return transactionQuantityList;
    }

    //<======!TransactionCompleted!======>

    //<------Transaction------->

    /**
     * @param transactionIndex index of Transaction in transactionList.
     * @return delivery date of Transaction
     */
    public String getTransactionDeliveryDate(int transactionIndex){
        return transactionList.get(transactionIndex).getDeliveryDate();
    }

    /**
     * @param transactionIndex index of Transaction in transactionList.
     * @return date of Transaction
     */
    public String getTransactionDate(int transactionIndex){
        return transactionList.get(transactionIndex).getDateOfTransaction();
    }

    /**
     * @param transactionIndex index of Transaction in transactionList.
     * @return payment date of Transaction
     */
    public String getTransactionPaymentDate(int transactionIndex){
        return transactionList.get(transactionIndex).getPaymentDate();
    }

    /**
     * @param transactionListIndex the index of a Transaction within transactionList.
     * @return the ArrayList size of itemsOrdered, within a specific Transaction.
     */
    public int getTransactionItemsSize(int transactionListIndex){
        return transactionList.get(transactionListIndex).getItemsOrdered().size();
    }

    /**
     * @param transactionListIndex the index of a Transaction within transactionList.
     * @param quantityIndex the index for a specific quantity within the Transaction's quantity ArrayList.
     * @return the indexed transaction's quantity @ quantityIndex
     */
    public int getTransactionQuantity(int transactionListIndex, int quantityIndex){
        return transactionList.get(transactionListIndex).getIndexedQuantity(quantityIndex);
    }

    /**
     * @param transactionIndex the index of a Transaction within transactionList.
     * @param itemsOrderedIndex the index for a specific StockItem within the Transaction's itemsOrdered ArrayList.
     * @return the indexed transaction's ITEM_ID @ itemsOrdered, itemsOrderedIndex
     */
    public int getTransactionItemId(int transactionIndex, int itemsOrderedIndex) {
        return transactionList.get(transactionIndex).getIndexedItemId(itemsOrderedIndex);
    }

    /**
     * @param transactionListIndex the index of a Transaction within transactionList.
     * @param itemsOrderedIndex the index for a specific StockItem within the Transaction's itemsOrdered ArrayList.
     * @return the indexed transaction's name @ itemsOrderedIndex from itemsOrdered
     */
    public String getTransactionItemName(int transactionListIndex, int itemsOrderedIndex) {
        return transactionList.get(transactionListIndex).getIndexedTransactionItemName(itemsOrderedIndex);
    }

    /**
     * @param transactionListIndex the index of a Transaction within transactionList.
     * @param itemsOrderedIndex the index for a specific StockItem within the Transaction's itemsOrdered ArrayList.
     * @return the indexed transaction's description @ itemsOrderedIndex from itemsOrdered
     */
    public String getTransactionItemDescription(int transactionListIndex, int itemsOrderedIndex) {
        return transactionList.get(transactionListIndex).getIndexedTransactionItemDescription(itemsOrderedIndex);
    }

    /**
     * @param transactionListIndex the index of a Transaction within transactionList.
     * @param unitPriceIndex the index for a specific unit price within the Transaction's unitPrice ArrayList.
     * @return the indexed transaction's unit price @ unitPriceIndex from unitPrice
     */
    public double getTransactionItemUnitPrice(int transactionListIndex, int unitPriceIndex) {
        return transactionList.get(transactionListIndex).getIndexedUnitPrice(unitPriceIndex);
    }

    /**
     * @param transactionListIndex the index of a Transaction within transactionList.
     * @param itemRowPriceIndex the index for a specific item row price within the Transaction's itemRowPrice ArrayList.
     * @return the indexed transaction's item row price @ itemRowPriceIndex from itemRowPrice
     */
    public double getTransactionItemRowPrice(int transactionListIndex, int itemRowPriceIndex) {
        return transactionList.get(transactionListIndex).getIndexedItemRowPrice(itemRowPriceIndex);
    }

    /**
     * @param transactionIndex the index of a Transaction within transactionList.
     * @return the transaction subtotal.
     */
    public double getTransactionSubtotal(int transactionIndex) {
        return transactionList.get(transactionIndex).getSubtotal();
    }

    /**
     * @param transactionIndex the index of a Transaction within transactionList.
     * @return the transaction overall VAT charge.
     */
    public double getTransactionVatCharges(int transactionIndex) {
        return transactionList.get(transactionIndex).getVatCharges();
    }

    /**
     * @param transactionIndex the index of a Transaction within transactionList.
     * @return the transaction total charge.
     */
    public double getTransactionTotal(int transactionIndex) {
        return transactionList.get(transactionIndex).getTotal();
    }
    //<-------!Transaction!------->

    //<-------ManageStock-------->

    /**
     * @param index the index of a StockItem within stockList.
     * @return the item id of the StockItem at stockList.get(index).
     */
    public final int GET_STOCKITEM_ID(int index){
        return stockList.get(index).getITEM_ID();
    }

    /**
     * @param index the index of a StockItem within stockList.
     * @return the item name of the StockItem at stockList.get(index).
     */
    public String getStockItemName(int index){
        return stockList.get(index).getName();
    }

    /**
     * @param index the index of a StockItem within stockList.
     * @return the quantity of the StockItem at stockList.get(index)
     */
    public int getStockItemQuantity(int index){
        return stockList.get(index).getQuantity();
    }

    /**
     * @param index the index of a StockItem within stockList.
     * @return the description of the StockItem at stockList.get(index).
     */
    public String getStockItemDescription(int index){
        return stockList.get(index).getDescription();
    }

    /**
     * @param index the index of a StockItem within stockList.
     * @return the itemImagePath of the StockItem at stockList.get(index).
     */
    public String getStockItemImagePath(int index){
        if (stockList.get(index).getDescription().equals(null)){
            return "no-image";
        } else
        return stockList.get(index).getItemImagePath();
    }

    /**
     * @param index the index value of the StockItem within stockList
     * @return the StockItem's unique ID
     */
    public final int GET_STOCKITEM_SUPPLIER_ID(int index){
        String supplierName = stockList.get(index).getSupplierName();
        for (Supplier s :
                supplierList) {
            if (s.getName().equals(supplierName)) {
                return s.getID();
            }
        }
        return -1;
    }

    /**
     * @return String array of existing supplier names.
     */
    public String[] getSupplierNames(){
        int size = supplierList.size();
        String supplierNames[] = new String[size];

        for(int i = 0;i<supplierList.size();i++){
            supplierNames[i] = supplierList.get(i).getName();
        }
        return supplierNames;
    }

    /**
     * @return unique Supplier ID.
     */
    private int assignSupplierId(){
        return maxSupplierId+=1;
    }

    /**
     * @return unique Stock ID.
     */
    private int assignStockId(){
        return maxStockId+=1;
    }

    /**
     * Adds a new Supplier with the passed parameters to supplierList.
     * Calls saveSupplierList
     * @param supplierName String name of supplier
     * @param fLineAddress String first line of address
     * @param sLineAddress String second line of address i.e Town
     * @param postcode String postcode
     * @param contactNumber String telephone number
     */
    public void newSupplier(String supplierName, String fLineAddress, String sLineAddress, String postcode, String contactNumber) {
        int supplierId = assignSupplierId();
        supplierList.add(new Supplier(supplierName, contactNumber, fLineAddress, sLineAddress, postcode, supplierId));

        saveSupplierList(supplierId);
    }

    /**
     * Adds a new StockItem with the passed parameters to stockList.
     * Calls saveStockList
     * @param itemName String name of item
     * @param description String description of the Stock Item
     * @param supplierName String name of the supplier of the Stock Item
     * @param imagePath String for the filepath of the item's image
     * @param price String price of item (vat inclusive)
     * @param quantity int value of current stock quantity for item
     */
    public void newStockItem(String itemName, String description, String supplierName, String imagePath, String price, int quantity){
        int stockId = assignStockId();
        if(!imagePath.trim().isEmpty()){
            stockList.add(new StockItem(itemName, price, description, supplierName, imagePath, assignStockId(), quantity));
        }else
            stockList.add(new StockItem(itemName, price, description, supplierName, stockId, quantity));

        saveStockList(stockId);
    }

    /**
     * Finds a stock item in stockList that has the same ID as itemId
     * @param itemId the unique Item ID
     * @return index of StockItem, if no match return -1
     */
    private int findStockItemById(int itemId){
        for (StockItem s :
                stockList) {
            if (s.getITEM_ID() == itemId){
                return stockList.indexOf(s);
            }
        }
        return-1;
    }

    /**
     * Finds a supplier in supplierList that has the same ID as supplierId
     * @param supplierId the unique Supplier ID
     * @return index of Supplier, if no match return -1
     */
    private int findSupplierById(int supplierId){
        for (Supplier s :
                supplierList) {
            if (s.getID() == supplierId){
                return supplierList.indexOf(s);
            }
        }
        return -1;
    }

    /**
     * Saves the StockItem with ID that matches passed itemId to Stock.txt
     * Appends the existing text file.
     * @param itemId the unique Item ID
     */
    public void saveStockList(int itemId){
        StockItem temp = stockList.get(findStockItemById(itemId));

        try(FileWriter save = new FileWriter(stockFile, true)){
            if(stockFile.length()>1){
                save.write("\n");
            }
            if(temp.getItemImagePath()==null||temp.getItemImagePath().isEmpty()){
                save.write(temp.getName()+"\t"+temp.getPrice()+"\t"+temp.getDescription()+"\t"+temp.getSupplierName()+"\tnull\t"+temp.getITEM_ID()+"\t"+temp.getQuantity());
            }else{
                save.write(temp.getName()+"\t"+temp.getPrice()+"\t"+temp.getDescription()+"\t"+temp.getSupplierName()+"\t"+temp.getItemImagePath()+"\t"+temp.getITEM_ID()+"\t"+temp.getQuantity());
            }

        }catch(IOException e) {
            JOptionPane.showMessageDialog(null, "ERROR - Could not access Stock.txt, please ensure the file has not been moved or renamed",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Saves the Supplier with ID that matches passed supplierId to Supplier.txt.
     * Appends the existing text file.
     * @param supplierId the unique Supplier ID
     */
    public void saveSupplierList(int supplierId){
        Supplier temp = supplierList.get(findSupplierById(supplierId));

        try(FileWriter save = new FileWriter(supplierFile, true)){
            if(supplierFile.length()>1){
                save.write("\n");
            }
            save.write(temp.getName()+"\t"+temp.getContactNumber()+"\t"+temp.getFirstLineAddress()+"\t"+temp.getSecondLineAddress()+"\t"+temp.getPostcode()+"\t"+temp.getID());

        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "ERROR - Could not access Supplier.txt, please ensure the file has not been moved or renamed",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Alters the stock quantity for a stock item with a name that matches stockItemName.
     * The stock item's quantity is set to the value of newQuantity.
     * Stock.txt is then updated by calling overwriteStockFile().
     * @param stockItemName String name of the stock item to be updated
     * @param newQuantity this value will be the new quantity for the Stock Item
     */
    public void updateStockLevel(String stockItemName, int newQuantity) {
        for (StockItem s :
                stockList) {
            if (s.getName().equals(stockItemName)){
                s.setQuantity(newQuantity);
            }
        }
        overwriteStockFile();
        Driver.swapToManageStock();
    }

    /**
     * @deprecated This is bad. If you remove a StockItem & try to view a transaction where this item was present, the system will THROW A FIT - 2AM thoughts.
     * @param stockItemName name of the Stock Item
     */
    public void removeStockItem(String stockItemName) {
        stockList.removeIf(s -> s.getName().equals(stockItemName));
        overwriteStockFile();
        Driver.swapToManageStock();
    }
    //<-------!ManageStock--------->

    //<=======CustomerAndSupplierInfo========>
    /**
     * Returns details of a Customer which can be used to populate a table row.
     * @param index used to identify a Customer in customerList
     * @return Object{id, name + surname, contactNumber, fLineAddress, sLineAddress, postcode}
     */
    public Object[] getCustomerDetails(int index) {
        Customer temp = customerList.get(index);
        return new Object[]{temp.getID(), temp.getName()+" "+temp.getSurname(), temp.getContactNumber(), temp.getFirstLineAddress()+", "+temp.getSecondLineAddress(),
                temp.getPostcode()};
    }

    /**
     * Returns details of a Supplier which can be used to populate a table row.
     * @param index used to identify a Supplier in supplierList
     * @return Object{id, name, contactNumber, fLineAddress, sLineAddress, postcode}
     */
    public Object[] getSupplierDetails(int index){
        Supplier temp = supplierList.get(index);
        Object[] supplierData = {temp.getID(), temp.getName(), temp.getContactNumber(), temp.getFirstLineAddress()+", "+temp.getSecondLineAddress(),
                temp.getPostcode()};
        return supplierData;
    }

    /**
     * Sets the contact number of an existing Customer within customerList.
     * @param id the id of the Customer.
     * @param contactNum the new contact number.
     */
    public void updateCustomerContactNumber(int id, String contactNum) {
        for (Customer c :
                customerList) {
            if (c.getID() == id){
                c.setContactNumber(contactNum);
            }
        }
        overwriteCustomerFile();
    }

    /**
     * Sets the address details of an existing Customer within customerList.
     * @param id the id of the Customer.
     * @param fLineAddress the new first line of address.
     * @param sLineAddress the new second line of address.
     * @param postcode the new postcode.
     */
    public void updateCustomerAddress(int id, String fLineAddress, String sLineAddress, String postcode) {
        for (Customer c :
                customerList) {
            if (c.getID() == id) {
                c.setFirstLineAddress(fLineAddress);
                c.setSecondLineAddress(sLineAddress);
                c.setPostcode(postcode);
            }
        }
        overwriteCustomerFile();
    }

    /**
     * Overwrites the contents of Supplier.txt with the current values in supplierList.
     */
    private void overwriteSupplierFile(){
        try(FileWriter overwrite = new FileWriter(supplierFile, false)){
            for(int i = 0; i<supplierList.size(); i++){
                Supplier temp = supplierList.get(i);
                if (i != 0) {
                    overwrite.write("\n");
                }
                overwrite.write(temp.getName()+"\t"+temp.getContactNumber()+"\t"+temp.getFirstLineAddress()+"\t"+temp.getSecondLineAddress()
                        +"\t"+temp.getPostcode()+"\t"+temp.getID());
            }
        }catch (IOException e){
            JOptionPane.showMessageDialog(null, "ERROR - There was a problem when overwriting Supplier.txt." +
                    "\nPlease ensure the file has not been removed, renamed or relocated", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Overwrites the contents of Customer.txt with the current values in customerList.
     */
    private void overwriteCustomerFile(){
        try(FileWriter overwrite = new FileWriter(customerFile, false)){
            for(int i = 0; i<customerList.size(); i++){
                Customer temp = customerList.get(i);
                if (i != 0) {
                    overwrite.write("\n");
                }
                overwrite.write(temp.getName()+"\t"+temp.getSurname()+"\t"+temp.getContactNumber()+"\t"+temp.getFirstLineAddress()+"\t"+temp.getSecondLineAddress()
                        +"\t"+temp.getPostcode()+"\t"+temp.getID());
            }
        }catch (IOException e){
            JOptionPane.showMessageDialog(null, "ERROR - There was a problem when overwriting Customer.txt." +
                    "\nPlease ensure the file has not been removed, renamed or relocated", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Sets the contact number of an existing Supplier within supplierList.
     * @param id the id of the Supplier.
     * @param contactNum the new contact number.
     */
    public void updateSupplierContactNumber(int id, String contactNum) {
        for (Supplier s :
                supplierList) {
            if (s.getID() == id){
                s.setContactNumber(contactNum);
            }
        }
        overwriteSupplierFile();
    }
    /**
     * Sets the address details of an existing supplier within supplierList.
     * @param id the id of the Supplier.
     * @param fLineAddress the new first line of address.
     * @param sLineAddress the new second line of address.
     * @param postcode the new postcode.
     */
    public void updateSupplierAddress(int id, String fLineAddress, String sLineAddress, String postcode) {
        for (Supplier s :
                supplierList) {
            if (s.getID() == id){
                s.setFirstLineAddress(fLineAddress);
                s.setSecondLineAddress(sLineAddress);
                s.setPostcode(postcode);
            }
        }
        overwriteSupplierFile();
    }

    //<=======!CustomerAndSupplierInfo!========>


    //<========Reports========>
    /**
     * @return int array of all Customer Id's from Customer objects within customerList.
     */
    public int[] getCustomerIds(){
        int[] customerIds = new int[customerList.size()];
        for (int i=0; i<customerList.size(); i++){
            customerIds[i] = customerList.get(i).getID();
        }
        return customerIds; //used to populate comboBox of customer id's
    }
    //<========!Reports!=========>
}