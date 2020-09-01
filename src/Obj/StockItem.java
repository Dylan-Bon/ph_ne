package Obj;


class StockItem {
    private String price, name, description, supplierName;
    //The filepath of the picture to be displayed alongside the item.
    private String itemImagePath=null;
    private int quantity;
    //Unique item ID
    private final int ITEM_ID;

    /**
     * Create new stock item with an image
     * @param name name of the StockItem
     * @param price price of the StockItem
     * @param description description of the StockItem
     * @param supplierName name of the Supplier
     * @param itemImagePath the path to the item's image
     * @param item_id the unique ID of the item
     * @param quantity the current stock level quantity for this item
     */
    StockItem(String name, String price, String description, String supplierName, String itemImagePath, int item_id, int quantity) {
        ITEM_ID = item_id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.supplierName = supplierName;
        if(!itemImagePath.equals("null")){
            this.itemImagePath = itemImagePath;
        }
        this.quantity = quantity;
    }

    /**
     * Create new stock item without an image
     * @param name name of the StockItem
     * @param price price of the StockItem
     * @param description description of the StockItem
     * @param supplierName name of the Supplier
     * @param item_id the unique ID of the item
     * @param quantity the current stock level quantity for this item
     */
    StockItem(String name, String price, String description, String supplierName, int item_id, int quantity) {
        ITEM_ID = item_id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.supplierName = supplierName;
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getItemImagePath() {
        return itemImagePath;
    }

    public void setItemImagePath(String itemImagePath) {
        this.itemImagePath = itemImagePath;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getITEM_ID() {
        return ITEM_ID;
    }

    public String toString(){
        return String.format("Name: %s\nPrice: £%s\nDescription: %s\nSupplier: %s\nImage Path: %s\nItem ID: %d\nQuantity: %d", name, price, description, supplierName, itemImagePath,ITEM_ID,quantity);
    }
}
