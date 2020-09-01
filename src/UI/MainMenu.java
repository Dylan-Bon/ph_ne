package UI;

import javax.swing.*;

public class MainMenu {
    private JPanel panelMainMenu;
    private JPanel topBar;
    private JPanel content;
    private JButton btnViewReports;
    private JButton btnCSInfo;
    private JButton btnMakeTransaction;
    private JButton btnManageStock;
    private JButton btnEye;

    public JPanel getPanelMainMenu() {
        return panelMainMenu;
    }

    /**
     * The main menu for the program with navigation to the pages:
     * Manage Stock,
     * View Reports,
     * Make Transactions,
     * Customer & Supplier Information
     */
    MainMenu() {
        btnManageStock.addActionListener(e-> Driver.swapToManageStock());
        btnViewReports.addActionListener(e->Driver.swapToViewReports());
        btnMakeTransaction.addActionListener(e-> Driver.swapToMakeTransaction());
        btnCSInfo.addActionListener(e -> Driver.swapToCustomerAndSupplierInfo());
    }
}
