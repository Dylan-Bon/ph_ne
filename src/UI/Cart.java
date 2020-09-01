package UI;

import javax.swing.*;
import java.awt.*;

public class Cart {
    private int existingContactNumberIndex;
    private String contactNumber;
    private String sLineAddress;
    private String lastName;
    private String fLineAddress;
    private String postcode;
    private String firstName;
    private JPanel cartPanel;
    private JButton btnBuy;
    private JLabel lblTotal;
    private JPanel bottomSec;
    private JPanel contentPanel;
    private JScrollPane contentScrollPane;
    private int maxYPos=-1;
    private final boolean existingCustomer;

    /**
     * Creates a new cart for new customer's
     * @param firstName first name of new customer.
     * @param lastName last name of new customer.
     * @param fLineAddress first line of address of new customer
     * @param sLineAddress second line of address of new customer
     * @param postcode postcode of new customer
     * @param contactNumber 11 digit contact number for new customer
     * @param existingCustomer = false.
     */
    Cart(String firstName, String lastName, String fLineAddress, String sLineAddress, String postcode, String contactNumber, boolean existingCustomer){
        setScrollSpeed();
        this.existingCustomer=existingCustomer;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fLineAddress = fLineAddress;
        this.sLineAddress = sLineAddress;
        this.postcode = postcode;
        this.contactNumber = contactNumber;
        btnBuy.addActionListener(e->{
            if(maxYPos==-1){
                JOptionPane.showMessageDialog(null, "There are no items to checkout.", "No Items in Cart", JOptionPane.WARNING_MESSAGE);
            }
            else{
                    if(!Driver.isExistingCustomer()){
                        Driver.generateTransaction(firstName, lastName,fLineAddress,sLineAddress,postcode,contactNumber);
                    }

            }
        });
    }

    /**
     * Creates a new cart for existing customers.
     * @param existingContactNumberIndex the contact number for the existing customer.
     * @param existingCustomer = true.
     */
    Cart(int existingContactNumberIndex, boolean existingCustomer){
        setScrollSpeed();
        this.existingContactNumberIndex = existingContactNumberIndex;
        this.existingCustomer = existingCustomer;
        btnBuy.addActionListener(e->{
            if(maxYPos==-1){
                JOptionPane.showMessageDialog(null, "There are no items to checkout.", "No Items in Cart", JOptionPane.WARNING_MESSAGE);
            }
            else{
                if(Driver.isExistingCustomer()){
                    Driver.generateTransaction(existingContactNumberIndex);
                }
            }
        });
    }
    public boolean isExistingCustomer() {
        return existingCustomer;
    }

    public int getExistingContactNumberIndex() {
        return existingContactNumberIndex;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getsLineAddress() {
        return sLineAddress;
    }

    public String getLastName() {
        return lastName;
    }

    public String getfLineAddress() {
        return fLineAddress;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getFirstName() {
        return firstName;
    }

    JPanel getCartPanel(){
        return cartPanel;
    }

    /**
     * Adds items to be displayed to cart
     * @param name the name of the StockItem
     * @param price the price of the StockItem
     * @param yPos this should be incremented so as to determine that the next item should be on the line below.
     */
    void generateCartItem(String name, int price, int yPos){
        JLabel lblName = new JLabel(name);
        lblName.setHorizontalAlignment(SwingConstants.LEFT);
        JLabel lblPrice = new JLabel("£"+price);
        lblPrice.setHorizontalAlignment(SwingConstants.RIGHT);
        JButton btnRemoveItem = new JButton("-");

        btnRemoveItem.addActionListener(e -> Driver.removeFromCart(lblName.getText()));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.ipadx = 5;
        gbc.ipady = 20;
        gbc.gridx = 0;
        gbc.gridy = yPos;
        contentPanel.add(lblName,gbc);
        gbc.gridx = 1;
        contentPanel.add(lblPrice,gbc);
        gbc.gridx = 2;
        contentPanel.add(btnRemoveItem,gbc);

        maxYPos=yPos;

    }

    /**
     * Sets the speed at which the window is able to scroll at to a unit increment of 16.
     */
    private void setScrollSpeed(){
        contentScrollPane.getVerticalScrollBar().setUnitIncrement(16);
    }

    /**
     * Sets the text of lblTotal which displays the total price of the items in the cart
     * @param total the total.
     */
    void setLblTotal(int total){
        lblTotal.setText("Total: £"+ total);
    }
}
