package UI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @deprecated use MakeTransaction now due to Swing GUI builder.
 */
class oldMakeTransaction extends JPanel {
    oldMakeTransaction(){
        this.setLayout(new BorderLayout());
        JPanel topBar = new JPanel();
        this.add(topBar, BorderLayout.NORTH);
        try {
            BufferedImage logo = ImageIO.read(new File("src/Interface/Img/logo light.png"));
            JLabel lblLogo = new JLabel(new ImageIcon(logo));
            topBar.add(lblLogo);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JPanel custDetails = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        //labels
        JLabel lblNewCustomer = new JLabel("New Customer");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.ipadx = 10;
        gbc.ipady = 7;
        custDetails.add(lblNewCustomer,gbc);
        JLabel lblFirstName = new JLabel("First Name:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        custDetails.add(lblFirstName, gbc);
        JLabel lblLastName = new JLabel("Last Name:");
        gbc.gridy = 2;
        custDetails.add(lblLastName, gbc);
        JLabel lblFirstAdd = new JLabel("First Line of Address:");
        gbc.gridy = 3;
        custDetails.add(lblFirstAdd, gbc);
        JLabel lblSecondAdd = new JLabel("Second Line of Address:");
        gbc.gridy = 4;
        custDetails.add(lblSecondAdd, gbc);
        JLabel lblPostcode = new JLabel("Postcode");
        gbc.gridy++;
        custDetails.add(lblPostcode, gbc);
        JLabel lblContactNum = new JLabel("Contact Number:");
        gbc.gridy++;
        custDetails.add(lblContactNum, gbc);
        JLabel lblExistingCustomer = new JLabel("Existing Customer");
        gbc.gridy++;
        gbc.gridx++;
        custDetails.add(lblExistingCustomer, gbc);
        JLabel lblExContactNum = new JLabel("Contact Number:");
        gbc.gridx--;
        gbc.gridy++;
        custDetails.add(lblExContactNum, gbc);
        //end labels
        //input
        JTextField txtFName = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        custDetails.add(txtFName, gbc);
        JTextField txtLName = new JTextField();
        gbc.gridy++;
        custDetails.add(txtLName, gbc);
        JTextField txtFAddress = new JTextField();
        gbc.gridy++;
        custDetails.add(txtFAddress, gbc);
        JTextField txtLAddress = new JTextField();
        gbc.gridy++;
        custDetails.add(txtLAddress, gbc);
        JTextField txtPostcode = new JTextField();
        gbc.gridy++;
        custDetails.add(txtPostcode, gbc);
        JTextField txtContactNum = new JTextField();
        gbc.gridy++;
        custDetails.add(txtContactNum, gbc);
        gbc.gridy++;
        gbc.gridy++;
        JTextField txtExContactNum = new JTextField();
        custDetails.add(txtExContactNum, gbc);

        this.add(custDetails, BorderLayout.WEST);
        this.setVisible(true);
    }
}
