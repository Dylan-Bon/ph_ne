package UI;

import javax.swing.*;

public class ViewReports {
    private JPanel topBar;
    private JLabel lblIcon;
    private JButton btnLayout;
    private JButton btnHome;
    private JButton btnStockReport;
    private JButton btnCustomerSalesReport;
    private JPanel viewReportsPanel;

    /**
     * Holds the UI elements to allow the user to navigate to the Stock Report and Customer Sales Report pages.
     */
    ViewReports(){
        btnHome.addActionListener(e->Driver.swapToHome());
        btnStockReport.addActionListener(e->Driver.swapToStockReport());
        btnCustomerSalesReport.addActionListener(e->Driver.swapToCustomerSalesReport());
    }

    /**
     * @return the main JPanel of ViewReports
     */
    public JPanel getViewReportsPanel() {
        return viewReportsPanel;
    }
}
