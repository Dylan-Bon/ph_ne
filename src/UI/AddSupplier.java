package UI;

import javax.swing.*;

public class AddSupplier {
    private JPanel topBar;
    private JLabel lblIcon;
    private JButton btnBack;
    private JButton btnLayout;
    private JButton btnHome;
    private JLabel lblName;
    private JLabel lblFLineAddress;
    private JLabel lblSLineAddress;
    private JLabel lblPostcode;
    private JLabel lblContact;
    private JTextField txtName;
    private JTextField txtFLineAddress;
    private JTextField txtSLineAddress;
    private JTextField txtPostcode;
    private JTextField txtContact;
    private JButton btnAdd;
    private JPanel addSupplierPanel;

    /**
     * Holds the GUI elements for AddSupplier.
     * This page allows the user to add a new Supplier to the system.
     * getAddSupplierPanel() should be called and set as the content pane.
     */
    AddSupplier(){
        btnHome.addActionListener(e -> Driver.swapToHome());
        btnBack.addActionListener(e -> Driver.swapToAddStockItem());
        btnAdd.addActionListener(e -> {
            if(fieldsValid()){
                Driver.newSupplier();
                JOptionPane.showMessageDialog(addSupplierPanel, "Supplier & Item Added.", "Supplier & Stock Item Added", JOptionPane.INFORMATION_MESSAGE);
                Driver.swapToManageStock();
            }
        });
    }

    /**
     * @return the value in the name field.
     */
    public String getName(){
        return txtName.getText();
    }

    /**
     * @return the value in the first line of address field.
     */
    public String getFLineAddress(){
        return txtFLineAddress.getText();
    }

    /**
     * @return the value in the second line of address field.
     */
    public String getSLineAddress(){
        return txtSLineAddress.getText();
    }

    /**
     * @return the value in the postcode field.
     */
    public String getPostcode(){
        return txtPostcode.getText();
    }

    /**
     * @return the value in the contact number field.
     */
    public String getContactNumber(){
        return txtContact.getText().trim();
    }

    /**
     * @return true if all fields contain valid input, else returns false and a relevant warning message is displayed
     */
    private boolean fieldsValid(){
        if(txtName.getText().trim().length()<2){
            JOptionPane.showMessageDialog(addSupplierPanel, "Invalid Name -  Name field must contain at least two non-space characters.",
                    "Invalid Name", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if(txtFLineAddress.getText().trim().length()<4){
            JOptionPane.showMessageDialog(addSupplierPanel, "Invalid Address - First Line of Address must contain at least 4 non-space characters.",
                    "Invalid Address", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if(txtSLineAddress.getText().trim().length()<1){
            JOptionPane.showMessageDialog(addSupplierPanel, "Invalid Address - Second Line of Address cannot be empty.",
                    "Invalid Address", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if(!Driver.validatePostcode(txtPostcode.getText())){
            JOptionPane.showMessageDialog(addSupplierPanel, "Address Format - Please enter a valid UK postcode.", "Invalid Postcode", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try{
            Long.parseLong(txtContact.getText());
        }catch (NumberFormatException numberFormatException){
            JOptionPane.showMessageDialog(addSupplierPanel, "Invalid Contact Number - Contact Number must only contain number characters.", "Invalid Contact Number",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if(txtContact.getText().trim().length()<11||txtContact.getText().trim().length()>11){
            JOptionPane.showMessageDialog(addSupplierPanel, "Invalid Contact Number - Must be 11 digits long.", "Invalid Contact Number", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * @return the main UI panel of AddSupplier
     */
    public JPanel getAddSupplierPanel() {
        return addSupplierPanel;
    }
}