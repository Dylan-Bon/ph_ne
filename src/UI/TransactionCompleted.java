package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TransactionCompleted {
    private JPanel topBar;
    private JLabel lblIcon;
    private JButton btnBack;
    private JButton btnLayout;
    private JButton btnHome;
    private JPanel TransactionCompletedPanel;
    private JPanel invoiceDetailsPanel;
    private JPanel invoicePanel;
    private JScrollPane scrlInvoice;
    private JLabel lblDeliveryDate;
    private JLabel lblPaymentDate;
    private JLabel lblUserID;
    private JLabel lblPostcode;
    private JLabel lblAddress;
    private JLabel lblCustID;
    private JLabel lblCustName;
    private JLabel lblDate;
    private JLabel lblTransactionID;
    private final String COLUMNS[] = {"QTY", "ITEM#", "ITEM NAME", "UNIT PRICE", "LINE TOTAL"};
    private DefaultTableModel tableModel = new DefaultTableModel(COLUMNS, 0);
    private JTable tblInvoice;
    private JTable tblTotal;
    private JScrollPane scrlTotal;
    private final String TOTALCOLUMNS[] = {" "," "};
    private DefaultTableModel totalTableModel = new DefaultTableModel(TOTALCOLUMNS,0);

    /**
     * Called when a transaction has been completed.
     * This class is used to generate the invoice for new orders.
     */
    TransactionCompleted(){
        scrlInvoice.getVerticalScrollBar().setUnitIncrement(16);
        scrlTotal.getVerticalScrollBar().setUnitIncrement(16);
        tblInvoice.setModel(tableModel);
        tblInvoice.getTableHeader().setReorderingAllowed(false);
        tblTotal.setModel(totalTableModel);
        tblTotal.getTableHeader().setReorderingAllowed(false);
        Driver.emptyCart();
    }

    /**
     * Populates the total table with the passed arguments.
     * @param subtotal the overall subtotal of the transaction.
     * @param vatCharges the overall VAT charge of the transaction.
     * @param total the overall total for the transaction.
     */
    public void populateTableTotal(double subtotal, double vatCharges, double total){
        Object rowOne[] = {"Subtotal: ",subtotal};
        totalTableModel.addRow(rowOne);
        Object rowTwo[] = {"VAT: ", vatCharges};
        totalTableModel.addRow(rowTwo);
        Object rowThree[] = {"Total: ", total};
        totalTableModel.addRow(rowThree);

        btnBack.addActionListener(e->{
            Driver.swapToMakeTransaction();
        });
        btnHome.addActionListener(e->{
            Driver.swapToHome();
        });
    }

    /**
     * Adds a new row of data to the items table.
     * @param data Object[] the row of data to be added.
     */
    public void addTableData(Object[] data) {
        tableModel.addRow(data);
    }

    public void setLblDeliveryDate(String deliveryDate) {
        this.lblDeliveryDate.setText(deliveryDate);
    }

    public void setLblPaymentDate(String paymentDate) {
        this.lblPaymentDate.setText(paymentDate);
    }

    public void setLblUserID(String userId) {
        this.lblUserID.setText(userId);
    }

    public void setLblPostcode(String postcode) {
        this.lblPostcode.setText(postcode);
    }

    public void setLblAddress(String address) {
        this.lblAddress.setText(address);
    }

    public void setLblCustID(String custId) {
        this.lblCustID.setText(custId);
    }

    public void setLblCustName(String custName) {
        this.lblCustName.setText(custName);
    }

    public void setLblDate(String transactionDate) {
        this.lblDate.setText(transactionDate);
    }

    public void setLblTransactionID(String transactionId) {
        this.lblTransactionID.setText(transactionId);
    }

    public JPanel getTransactionCompletedPanel() {
        return TransactionCompletedPanel;
    }
}
