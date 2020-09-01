package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CustomerAndSupplierInfo {
    private JPanel topBar;
    private JLabel lblIcon;
    private JButton btnLayout; //Dark theme not implemented
    private JButton btnHome;
    private JButton btnEditDetails;
    private JComboBox<String> cmbCustSupp;
    private JScrollPane scrlCustSupp;
    private final String[] CUST_SUPP = {"Customer", "Supplier"};
    private JTable tblCustSupp;
    private JPanel cAndSPanel;
    private final String[] COLUMNS = {"ID#", "Name", "Contact Number", "Address", "Postcode"};

    private DefaultTableModel customerModel = new DefaultTableModel(COLUMNS, 0){
        @Override
        public boolean isCellEditable(int i, int i1) {
            return false;
        }
    };

    private DefaultTableModel supplierModel = new DefaultTableModel(COLUMNS, 0){
        @Override
        public boolean isCellEditable(int i, int i1) {
            return false;
        }
    };

    /**
     * GUI to allow the user to view Customer & Supplier information and to change a selected Customer/Supplier's contact number and address.
     */
    CustomerAndSupplierInfo(){
        populateCustSupp();

        cmbCustSupp.addActionListener(e -> {
            if (cmbCustSupp.getSelectedIndex()==1){
                swapToSupplierModel();
            }else
                swapToCustomerModel();
        });

        tblCustSupp.setModel(customerModel);
        tblCustSupp.getTableHeader().setReorderingAllowed(false);
        scrlCustSupp.getVerticalScrollBar().setUnitIncrement(16);

        btnEditDetails.addActionListener(e->{
            int selectedRow = tblCustSupp.getSelectedRow();

            if (selectedRow == -1){
                JOptionPane.showMessageDialog(cAndSPanel, "Please select a row before pressing this button.",
                        "No row selected", JOptionPane.WARNING_MESSAGE);
            }else {
                int selectedValue = JOptionPane.showConfirmDialog(cAndSPanel, "Would you like update the details for "+ tblCustSupp.getValueAt(selectedRow, 1),
                        "Update Details", JOptionPane.YES_NO_OPTION);

                if(selectedValue == JOptionPane.YES_OPTION){
                    if(tblCustSupp.getModel()==supplierModel){
                        updateSupplierDetails();
                    } else{
                        updateCustomerDetails();
                    }
                }
            }
        });

        btnLayout.setEnabled(false);
        btnHome.addActionListener(e -> Driver.swapToHome());
    }

    /**
     * Gives the user a choice as to whether they wish to update either the customer's address or contact number and then takes in input for the selected choice.
     * The input is validated and then the details are updated and the page is refreshed so as to display the changes.
     */
    private void updateCustomerDetails() {
        System.out.println("cust stuff");
        int id = Integer.parseInt(String.valueOf(tblCustSupp.getValueAt(tblCustSupp.getSelectedRow(),0)));

        int selectedValue;
        String[] options = {"Contact Number", "Address"};
        selectedValue = JOptionPane.showOptionDialog(cAndSPanel, "Please select what details you would like to update",
                "Update Details", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, null);

        if (selectedValue==0){
            String contactNum;
            do{
                contactNum = JOptionPane.showInputDialog(cAndSPanel, "Please enter a 11 digit contact number.", "Update Contact Number");
                try {
                    Integer.parseInt(contactNum);
                } catch (NumberFormatException e){
                    JOptionPane.showMessageDialog(cAndSPanel, "Invalid Format - Contact Number only accepts integer values.", "Invalid Format", JOptionPane.WARNING_MESSAGE);
                }
            }while (contactNum.trim().length()<11);
            Driver.updateCustomerContactNumber(id, contactNum);
        } else if (selectedValue == 1){
            String fLineAddress, sLineAddress, postcode;
            do {
                fLineAddress = JOptionPane.showInputDialog(cAndSPanel, "Please enter the new first line of address.", "Update Address");
            } while (fLineAddress.trim().length()<5);
            do {
                sLineAddress = JOptionPane.showInputDialog(cAndSPanel, "Please enter the town of the new address", "Update Address");
            } while (sLineAddress.trim().length()<3);
            do {
                postcode = JOptionPane.showInputDialog(cAndSPanel, "Please enter the new postcode", "Update Address");
            } while(!Driver.validatePostcode(postcode));
            Driver.updateCustomerAddress(id, fLineAddress, sLineAddress, postcode);
        }
        Driver.swapToCustomerAndSupplierInfo();
    }

    /**
     * Gives the user a choice as to whether they wish to update either the supplier's address or contact number and then takes in input for the selected choice.
     * The input is validated and then the details are updated and the page is refreshed so as to display the changes.
     */
    private void updateSupplierDetails() {
        System.out.println("not cust stuff");
        int id = Integer.parseInt(String.valueOf(tblCustSupp.getValueAt(tblCustSupp.getSelectedRow(),0)));

        int selectedValue;
        String[] options = {"Contact Number", "Address"};
        selectedValue = JOptionPane.showOptionDialog(cAndSPanel, "Please select what details you would like to update",
                "Update Details", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, null);

        if (selectedValue==0){
            String contactNum;
            do{
                contactNum = JOptionPane.showInputDialog(cAndSPanel, "Please enter a 11 digit contact number.", "Update Contact Number");
                try {
                    Integer.parseInt(contactNum);
                } catch (NumberFormatException e){
                    JOptionPane.showMessageDialog(cAndSPanel, "Invalid Format - Contact Number only accepts integer values.", "Invalid Format", JOptionPane.WARNING_MESSAGE);
                }
            }while (contactNum.trim().length()<11);
            Driver.updateSupplierContactNumber(id, contactNum);

        } else if (selectedValue == 1){
            String fLineAddress, sLineAddress, postcode;
            do {
                fLineAddress = JOptionPane.showInputDialog(cAndSPanel, "Please enter the new first line of address.", "Update Address");
            } while (fLineAddress.trim().length()<5);
            do {
                //shortest town name in UK (which would go in sLineAddress) is Ì, located in Dumfries & Galloway
                sLineAddress = JOptionPane.showInputDialog(cAndSPanel, "Please enter the town of the new address", "Update Address");
            } while (sLineAddress.trim().length()<1);
            do {
                postcode = JOptionPane.showInputDialog(cAndSPanel, "Please enter the new postcode.", "Update Address");
            } while(!Driver.validatePostcode(postcode));
            Driver.updateSupplierAddress(id, fLineAddress, sLineAddress, postcode);
        }

        Driver.swapToCustomerAndSupplierInfo();
    }

    /**
     * @return the CustomerAndSupplierInfo panel so that it can be used as the content pane.
     */
    public JPanel getcAndSPanel() {
        return cAndSPanel;
    }

    /**
     * Swaps the table model to customerModel.
     */
    private void swapToCustomerModel() {
        tblCustSupp.setModel(customerModel);
    }

    /**
     * Swaps the table model to supplierModel.
     */
    private void swapToSupplierModel() {
        tblCustSupp.setModel(supplierModel);
    }

    /**
     * Adds a row to the supplierModel.
     * @param row the row data to be added to supplierModel.
     */
    void populateSupplierModel(Object[] row) {
        supplierModel.addRow(row);
    }

    /**
     * Adds a row to the customerModel.
     * @param row the row data to be added to customerModel.
     */
    void populateCustomerModel(Object[] row) {
        customerModel.addRow(row);
    }

    /**
     * populates the comboBox options for cmbCustSupp.
     */
    private void populateCustSupp() {
        for (String s :
                CUST_SUPP) {
            cmbCustSupp.addItem(s);
        }
    }
}
