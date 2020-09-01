package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ManageStock {
    private JPanel topBar;
    private JLabel lblIcon;
    private JButton btnLayout;
    private JButton btnHome;
    private JButton btnUpdateStockLevel;
    private JButton btnRemove; // disabled
    private JButton btnAddNewItem;
    private JScrollPane scrlTablePane;
    private final String COLUMNS[] = {"ID#", "Item Name", "Qty", "Description", "Image", "Supplier ID#"};
    private JTable tblManageStock;
    private JPanel manageStockPanel;

    private DefaultTableModel tableModel = new DefaultTableModel(COLUMNS, 0){
        @Override
        public boolean isCellEditable(int i, int i1) {
            return false; //To change body of generated methods, choose Tools | Templates.
        }
    };

    /**
     * Holds then GUI elements for the Manage Stock page
     * Allows user to update the stock level of all StockItem's
     * TO IMPLEMENT:- the removal of StockItem's, this is currently disabled as it is dangerous to the application with it's current structure.
     */
    ManageStock(){
        scrlTablePane.getVerticalScrollBar().setUnitIncrement(16);
        tblManageStock.setModel(tableModel);
        tblManageStock.getTableHeader().setReorderingAllowed(false);

        btnHome.addActionListener(e -> Driver.swapToHome());

        btnAddNewItem.addActionListener(e-> Driver.swapToAddStockItem());

        btnRemove.addActionListener(e->{
            int row = tblManageStock.getSelectedRow();
            int selection = JOptionPane.showConfirmDialog(manageStockPanel, "Are you sure that you want to delete "+ tblManageStock.getValueAt(row,1)+
                    "?\nThis cannot be undone.", "Remove Stock Item", JOptionPane.YES_NO_OPTION);

            if(selection==JOptionPane.YES_OPTION){
                removeStockItem(row);
            }
        });

        btnUpdateStockLevel.addActionListener(e->{
            int row = tblManageStock.getSelectedRow();
            if(row==-1){
                JOptionPane.showMessageDialog(manageStockPanel, "Please select the item row you wish to change the quantity of before pressing this.",
                        "Update Stock Level", JOptionPane.WARNING_MESSAGE);
            }else{
                int selection = JOptionPane.showConfirmDialog(manageStockPanel, "Would you like to update the stock level for "+tblManageStock.getValueAt(row,1)+"?",
                        "Update Stock Level", JOptionPane.YES_NO_OPTION );

                if(selection==JOptionPane.YES_OPTION){
                    updateStockLevel(row);
                }
            }
        });

        btnRemove.setEnabled(false); //disabled as button calls deprecated method which is DANGEROUS. This will be further commented on within the testing document.
    }

    /**
     * Calls Driver.removeStockItem(String)
     * This removes the StockItem from the whole system (stockList and Stock.txt).
     * @deprecated This is bad. If you remove a StockItem & try to view a transaction which this item was in, the system will explode - 2AM thoughts.
     * @param row
     */
    private void removeStockItem(int row) {
        Driver.removeStockItem((String)tblManageStock.getValueAt(row,1));
    }

    /**
     * Updates the quantity for the selected item row.
     * @param row the row at which the selected item is at.
     */
    private void updateStockLevel(int row){
        int updatedQty = -1;
        boolean validQuantity = false;
        do {
            try{
                updatedQty = Integer.parseInt(JOptionPane.showInputDialog(manageStockPanel, "Current Stock for "+ tblManageStock.getValueAt(row,1)+ " is: "+tblManageStock.getValueAt(row,2)+
                        "\nPlease enter the value that you would like to set this to.", "Update Stock Level"));
            }catch (NumberFormatException e){
                JOptionPane.showMessageDialog(manageStockPanel, "Invalid Format - Please enter a numerical value.", "Invalid Format", JOptionPane.WARNING_MESSAGE);
            }
            if(updatedQty>=0&&updatedQty<501){
                //update qty
                Driver.updateStockLevel((String) tblManageStock.getValueAt(row,1), updatedQty);
                validQuantity = true;
            } else if(updatedQty<0||updatedQty>500){
                JOptionPane.showMessageDialog(manageStockPanel, "Invalid Number - Quantity must be between 0 and 500.", "Invalid Quanity", JOptionPane.WARNING_MESSAGE);
            }
        } while (!validQuantity);

    }

    /**
     * Adds a new row to the table
     * @param data Object[] the data to be added to the table
     */
    public void populateTblManageStock(Object data[]){
        tableModel.addRow(data);
    }

    /**
     * @return the main JPanel of ManageStock.
     */
    public JPanel getManageStockPanel() {
        return manageStockPanel;
    }
}