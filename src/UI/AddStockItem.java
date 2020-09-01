package UI;

import Obj.DataManager;

import javax.swing.*;

public class AddStockItem {
    private JPanel topBar;
    private JLabel lblIcon;
    private JButton btnBack;
    private JButton btnLayout;
    private JButton btnHome;
    private JTextField txtName;
    private JTextField txtDescription;
    private JComboBox cmbExistingSupplier;
    private JTextField txtQuantity;
    private JButton btnUpload;
    private JCheckBox cbNewSupplier;
    private JLabel lblName;
    private JLabel lblItemDescription;
    private JLabel lblExisting;
    private JTextField txtImagePath;
    private JLabel lblNew;
    private JButton btnAdd;
    private JLabel lblUpload;
    private JLabel lblQuantity;
    private JPanel addStockItemPanel;
    private JLabel lblPrice;
    private JTextField txtPrice;

    /**
     * Holds the GUI elements for AddStockItem
     * This page allows the user to add a new StockItem to the system.
     * getAddStockItemPanel() should be called and set as the content pane.
     */
    AddStockItem(){
        cbNewSupplier.addActionListener(e -> {
            if(cbNewSupplier.isSelected()){
                cmbExistingSupplier.setEnabled(false);
            }
            else
                cmbExistingSupplier.setEnabled(true);
        });
        btnAdd.addActionListener(e -> {
            if(fieldsValid()){
                if(cbNewSupplier.isSelected()){
                    Driver.swapToAddSupplier();
                }
                else{
                    Driver.newStockItem(cmbExistingSupplier.getSelectedItem().toString());
                    JOptionPane.showMessageDialog(addStockItemPanel, "Item Added", "Add Stock", JOptionPane.INFORMATION_MESSAGE);
                    Driver.swapToManageStock();
                }
            }
        });
        btnHome.addActionListener(e -> Driver.swapToHome());
        btnBack.addActionListener(e -> Driver.swapToManageStock());
        btnUpload.setEnabled(false);
        txtImagePath.setEnabled(false);
    }

    /**
     * Populates the existing supplier combo box with supplier names.
     * @param supplierNames the array of supplier names.
     */
    public void populateSupplierComboBox(String[] supplierNames){
        for (String s :
                supplierNames) {
            cmbExistingSupplier.addItem(s);
        }
    }

    /**
     * @return the value typed in the name field
     */
    public String getName(){
        return txtName.getText();
    }

    /**
     * @return the value typed in the description field
     */
    public String getDescription(){
        return txtDescription.getText();
    }

    /**
     * @return the value typed in the image path field
     */
    public String getImagePath(){
        return txtImagePath.getText();
    }

    /**
     * @return the value typed in the price field
     */
    public String getPrice(){
        return txtPrice.getText();
    }

    /**
     * @return parse the value typed in the quantity field to integer
     */
    public int getQuantity(){
        return Integer.parseInt(txtQuantity.getText());
    }

    /**
     * MUST ADD - Validate if Existing supplier not selected and new supplier is false
     *          - Validation for image
     * @return true if all fields are valid, else display relevant error message
     */
    private boolean fieldsValid(){
        if(txtName.getText().trim().length()<2){
            JOptionPane.showMessageDialog(addStockItemPanel, "Invalid Name -  Name field must contain at least two non-space characters.",
                    "Invalid Name", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if(txtDescription.getText().trim().length()<8){
            JOptionPane.showMessageDialog(addStockItemPanel, "Invalid Description -  Description field must contain at least 8 non-space characters.",
                    "Invalid Description", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try{
            Double.parseDouble(txtPrice.getText().trim());
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(addStockItemPanel, "Price may only be a decimal number, no special characters are accepted.",
                    "Invalid Price", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if(Double.parseDouble(txtPrice.getText().trim())<10){
            JOptionPane.showMessageDialog(addStockItemPanel, "Invalid Price -  Price must contain a numeric value of 10 or over.",
                    "Invalid Price",JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try{
            Integer.parseInt(txtQuantity.getText().trim());
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(addStockItemPanel, "Invalid Quantity - Quantity field only accepts WHOLE numbers, no other characters/values are accepted.",
                    "Invalid Quantity",JOptionPane.WARNING_MESSAGE);
            System.out.println(e);
            return false;
        }
        if(Integer.parseInt(txtQuantity.getText().trim())<0){
            JOptionPane.showMessageDialog(addStockItemPanel, "Invalid Quantity - Quantity cannot be less than 0.", "Invalid Quantity",JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Returns the main UI panel of the class.
     * @return addStockItemPanel
     */
    public JPanel getAddStockItemPanel() {
        return addStockItemPanel;
    }
}