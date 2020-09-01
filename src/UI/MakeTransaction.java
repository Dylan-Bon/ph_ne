package UI;

import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MakeTransaction {
    private JPanel panelMakeTransaction;
    private JPanel topBar;
    private JPanel CustomerDetails;
    private JTextField txtFirstName;
    private JLabel lblNewCustomer;
    private JLabel lblFirstName;
    private JLabel lblSurname;
    private JLabel lblFLineAddress;
    private JLabel lblSLineAddress;
    private JTextField txtContactNumber;
    private JComboBox cmbExContactNumber;
    private JLabel lblPostcode;
    private JLabel lblContactNumber;
    private JLabel lblExistingCustomer;
    private JLabel lblExContactNumber;
    private JPanel shop;
    private JPanel filters;
    private JButton btnPhones; //filtering not implemented, button disabled
    private JButton btnCases; //filtering not implemented, button disabled
    private JButton btnAccessories; //filtering not implemented, button disabled
    private JScrollPane scrollWindow;
    private JPanel scrollContent;
    private JLabel lblIcon;
    private JButton btnCart;
    private JButton btnLayout;
    private JButton btnHome;
    private JTextField txtLastName;
    private JTextField txtFLineAddress;
    private JTextField txtSLineAddress;
    private JTextField txtPostcode;

    private boolean existingCustomer = false;

    public String firstName(){
        return txtFirstName.getText();
    }
    public String lastName(){
        return txtLastName.getText();
    }
    public String fLineAddress(){
        return txtFLineAddress.getText();
    }
    public String sLineAddress(){
        return txtSLineAddress.getText();
    }
    public String postcode(){
        return txtPostcode.getText();
    }
    public String contactNumber(){
        return txtContactNumber.getText();
    }


    /**
     * This holds the GUI elements that are needed for the user to access the 'Make Transaction' page.
     * getPanelMakeTransaction should be called and set as the content pane in order to view this page.
     */
    MakeTransaction(){
        btnHome.addActionListener(e->{
            Driver.swapToHome();
        });

        btnCart.addActionListener(e->{
            if(fieldsValid()){
                if(existingCustomer){
                    Driver.displayCart(cmbExContactNumber.getSelectedIndex()-1, existingCustomer);
                }else{
                    Driver.displayCart(firstName(),lastName(),fLineAddress(),sLineAddress(),postcode(),contactNumber(),existingCustomer);
                }
            }
        });
        scrollWindow.getVerticalScrollBar().setUnitIncrement(20);

        btnPhones.setEnabled(false);
        btnCases.setEnabled(false);
        btnAccessories.setEnabled(false);
    }

    /**
     * @return true if values in text fields are valid.
     */
    private boolean fieldsValid(){
        if(cmbExContactNumber.getSelectedIndex()!=0 && fieldsEmpty()){
            this.existingCustomer=true;
            return true;
        }
        else if(cmbExContactNumber.getSelectedIndex()!=0 && (!fieldsEmpty())){
            JOptionPane.showMessageDialog(panelMakeTransaction, "If selecting an existing customer, ensure all other fields are empty.\n" +
                    "If entering a new customer's details, ensure that the Existing Customer dropdown box is set to \"NONE\".", "Invalid Customer Entry",JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if(txtFirstName.getText().contains(" ")||txtLastName.getText().contains(" ")){
            JOptionPane.showMessageDialog(panelMakeTransaction, "Name Format - First Name and Last Name fields cannot contain spaces.", "Invalid Name",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if(txtFirstName.getText().length()<2||txtLastName.getText().length()<2){
            JOptionPane.showMessageDialog(panelMakeTransaction, "Name Length - First Name and Last Name fields must contain AT LEAST 2 characters.","Invalid Name",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if(txtFLineAddress.getText().trim().length()<4){
            JOptionPane.showMessageDialog(panelMakeTransaction, "Address Format - First Line of Address should be at least 4 characters long, not including spaces.",
                    "Invalid Address", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        //shortest town name in UK (which would go in sLineAddress) is Ì, located in Dumfries & Galloway
        if(txtSLineAddress.getText().trim().length()<1){
            JOptionPane.showMessageDialog(panelMakeTransaction, "Address Format - Second Line of Address should be at least 1 character long, not including spaces.",
                    "Invalid Address", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if(!Driver.validatePostcode(txtPostcode.getText())){
            JOptionPane.showMessageDialog(panelMakeTransaction, "Address Format - Please enter a valid UK postcode.", "Invalid Postcode", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if(txtContactNumber.getText().trim().length()<11||txtContactNumber.getText().trim().length()>11){
            JOptionPane.showMessageDialog(panelMakeTransaction, "Invalid Contact Number - Must be 11 digits long.", "Invalid Contact Number", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try{
            Long.parseLong(txtContactNumber.getText());
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(panelMakeTransaction, "Invalid Contact Number - Contact Number must only contain number characters.", "Invalid Contact Number",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        //if valid new customer
        return true;
    }

    /**
     * @return true if text fields are empty.
     */
    private boolean fieldsEmpty(){
        if(txtFirstName.getText().trim().isEmpty() && txtLastName.getText().trim().isEmpty() && txtFLineAddress.getText().trim().isEmpty()
            && txtSLineAddress.getText().trim().isEmpty() && txtPostcode.getText().trim().isEmpty() && txtContactNumber.getText().trim().isEmpty()){
            return true;
        }
        else
            return false;
    }

    /**
     * Populates the contact number combo box with existing customers contact number's
     * @param existingContactNumberList
     */
    void populateCmbExContactNumber(ArrayList<String> existingContactNumberList){
        cmbExContactNumber.addItem("NONE");
        for (String s :
                existingContactNumberList){
            cmbExContactNumber.addItem(s);
        }
    }

    /**
     * @return the main content panel of MakeTransaction i.e - panelMakeTransaction
     */
    JPanel getPanelMakeTransaction() {
        return panelMakeTransaction;
    }

    /**
     * Used to populate the window with StockItem's.
     * @param itemImagePath the file path to the StockItem's image.
     * @param itemName the name of the StockItem.
     * @param description the StockItem's description.
     * @param price the price of the StockItem.
     * @param ITEM_ID the unique id of the item
     * @param counter used to set the y-pos of item within scrollContent
     */
    public void generateStoreItem(String itemImagePath, String itemName, String description, String price, final int ITEM_ID, int counter){
        //set image space
        JPanel item = new JPanel();
        JLabel lblItemImage = new JLabel();
        ImageIcon icon = null;
        if(!itemImagePath.equals(null)){
            icon = new ImageIcon(itemImagePath);
        }
        lblItemImage.setIcon(icon);
        item.add(lblItemImage);

        //set description space
        JPanel itemDescription = new JPanel(new BorderLayout());
        JLabel lblItemName = new JLabel(itemName);
        JLabel lblItemID = new JLabel("Item ID: #"+ITEM_ID);
        lblItemName.setHorizontalTextPosition(SwingConstants.LEFT);
        itemDescription.add(lblItemName, BorderLayout.NORTH);
        itemDescription.add(lblItemID,BorderLayout.CENTER);


        JLabel lblDescription = new JLabel(description);
        lblDescription.setHorizontalTextPosition(SwingConstants.LEFT);
        itemDescription.add(lblDescription, BorderLayout.CENTER);
        item.add(itemDescription,BorderLayout.SOUTH);

        JPanel bottomSec = new JPanel();
        JLabel lblPrice = new JLabel("£"+price);
        bottomSec.add(lblPrice);
        JButton btnAdd = new JButton("Add");
        bottomSec.add(btnAdd);
        item.add(bottomSec, BorderLayout.SOUTH);
        item.setVisible(true);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = counter;
        scrollContent.add(item, gbc);

        btnAdd.addActionListener(e ->{
            Driver.addToCart(ITEM_ID);
            JOptionPane.showMessageDialog(panelMakeTransaction, lblItemName.getText(), "Item Added",JOptionPane.INFORMATION_MESSAGE);
        });
    }

    public boolean isExistingCustomer(){
        return this.existingCustomer;
    }

//    /**
//     * The returned index will match the index of the customer stored within customerList located in DataManager.
//     * @return index of selected existing contact number.
//     */
//    public int exContactNumber(){
//        return cmbExContactNumber.getSelectedIndex();
//    }

}
