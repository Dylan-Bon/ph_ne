package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CustomerSalesReport {
    private JPanel customerSalesReportPanel;
    private JPanel topBar;
    private JLabel lblIcon;
    private JButton btnBack;
    private JButton btnLayout;
    private JButton btnHome;
    private JLabel lblName;
    private JLabel lblNumber;
    private JLabel lblId;
    private JLabel lblMonth;
    private JComboBox cmbCustomerId;
    private JComboBox cmbTimeframe;
    private JComboBox cmbMonth;
    private final String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    private JComboBox cmbYear;
    private JButton btnExport;
    private JTable tblCustSalesReport;
    private JScrollPane scrlCustSalesReport;
    private JButton btnSearch;
    private final String COLUMNS[] = {"Item Purchased", "Item ID#", "Qty Purchased", "Invoice Number", "Purchase Date", "Unit Price", "Subtotal", "Total (VAT Inc.)"};
    private DefaultTableModel tableModel = new DefaultTableModel(COLUMNS, 0);

    CustomerSalesReport(){
        btnBack.addActionListener(e -> Driver.swapToViewReports());
        btnHome.addActionListener(e -> Driver.swapToHome());
        populateComboBoxes();

        //works
        cmbTimeframe.addActionListener(e->{
            if(cmbTimeframe.getSelectedIndex()==0){
                cmbMonth.setEnabled(true);
            }else
                cmbMonth.setEnabled(false);
        });

        btnSearch.addActionListener(e -> {
            int customerId = Integer.parseInt(cmbCustomerId.getSelectedItem().toString());
            boolean monthly = false;
            String month = "";
            if(cmbTimeframe.getSelectedIndex()==0){
                monthly = true;
                month = Integer.toString(cmbMonth.getSelectedIndex()+1);
            }
            String year = cmbYear.getSelectedItem().toString();

            if(monthly){
                populateTableWithMonthly(customerId, month, year);
            }else
                populateTableWithYearly(customerId, year);
        });
        scrlCustSalesReport.getVerticalScrollBar().setUnitIncrement(16);
        tblCustSalesReport.setModel(tableModel);

    }

    private void populateTableWithMonthly(int customerId, String month, String year) {
        Driver.monthlyCustomerSalesReport(customerId, month, year);
    }

    private void populateTableWithYearly(int customerId, String year) {
    }

    private void populateComboBoxes(){
        cmbTimeframe.addItem("Monthly");
        cmbTimeframe.addItem("Yearly");
        for (String s :
                months) {
            cmbMonth.addItem(s);
        }
        cmbYear.addItem(2019);
        cmbYear.addItem(2020);
        cmbYear.addItem(2021);

    }
    
    void populateCmbCustomerId(int[] customerId){
        for (int i :
                customerId) {
            cmbCustomerId.addItem(i);
        }
    }

    public JPanel getCustomerSalesReportPanel() {
        return customerSalesReportPanel;
    }
}
