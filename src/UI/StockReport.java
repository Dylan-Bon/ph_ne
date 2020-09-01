package UI;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.FileNotFoundException;

public class StockReport {
    private JPanel topBar;
    private JLabel lblIcon;
    private JButton btnBack;
    private JButton btnLayout;
    private JButton btnHome;
    private JPanel stockReportPanel;
    private JButton btnFilterStock;
    private JButton btnExport;
    private JTable tblStock;
    private final String COLUMNS[] = {"ID#", "Item Name", "Qty", "Supplier ID#", "Description"};
    private DefaultTableModel tableModel = new DefaultTableModel(COLUMNS, 0);
    private DefaultTableModel filteredTableModel = new DefaultTableModel(COLUMNS, 0);
    private JScrollPane scrlStockTable;

    boolean filtering = false;

    /**
     * This page allows the user to view a 'stock' report and filter the report to view 'low' stock with a quantity of 5 or less.
     * This page also supports exporting the current table view to PDF, currently the output is stored at src/output/StockReport.pdf.
     */
    StockReport(){
        btnHome.addActionListener(e -> Driver.swapToHome());
        btnBack.addActionListener(e -> Driver.swapToViewReports());
        btnFilterStock.addActionListener(e -> {
            if (!filtering){
                tblStock.setModel(filteredTableModel);
                filtering=true;
                btnFilterStock.setText("Show All Stock");
            }
            else {
                tblStock.setModel(tableModel);
                filtering=false;
                btnFilterStock.setText("Show Only Low Stock");
            }
        });

        btnExport.addActionListener(e -> printToPDF());

        scrlStockTable.getVerticalScrollBar().setUnitIncrement(16);
        tblStock.setModel(tableModel);
        tblStock.getTableHeader().setReorderingAllowed(false);
    }

    /**
     * Prints what is currently being displayed int tblStock to a PDF.
     */
    private void printToPDF(){
        //Document document = new Document(PdfDocument, PageSize.A4.rotate());
        String destination = "src/output/StockReport.pdf";
        try{
            PdfWriter pdfWriter = new PdfWriter(destination);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document document = new Document(pdfDocument, PageSize.A4.rotate());
            float[] columnWidths = {50f,150f,50f,50f,200f};
            Table table = new Table(columnWidths);
            for (String s :
                    COLUMNS) {
                table.addCell(s);
            }

            for (int i = 0; i<tblStock.getRowCount(); i++){
                table.addCell(Integer.toString((Integer) tblStock.getValueAt(i, 0)));
                table.addCell((String)tblStock.getValueAt(i, 1));
                table.addCell(Integer.toString((Integer) tblStock.getValueAt(i, 2)));
                table.addCell(Integer.toString((Integer) tblStock.getValueAt(i, 3)));
                table.addCell((String) tblStock.getValueAt(i, 4));
            }

            document.add(table);
            document.close();
            JOptionPane.showMessageDialog(null,"StockReport.pdf created at: "+destination);
            //{"ID#", "Item Name", "Qty", "Supplier ID#", "Description"}
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(stockReportPanel, "Please make sure that StockReport.pdf is not currently open.",
                    "File not found", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Takes an Object[] arg and adds it as a row to the table model.
     * @param tblRowData the data to be added as a row to the table.
     */
    void populateTblStock(Object[] tblRowData){
        tableModel.addRow(tblRowData);
        if((Integer)tblRowData[2]<6){
            lowStockColour();
        }
    }

    /**
     * NOT IMPLEMENTED.
     */
    private void lowStockColour(){

    }

    /**
     * Adds a new row of data to the table.
     * @param tblRowData Object[] the data to be added to the table.
     */
    void populateFilteredStock(Object[] tblRowData){
        filteredTableModel.addRow(tblRowData);
    }

    /**
     * @return the main JPanel of this class.
     */
    JPanel getStockReportPanel() {
        return stockReportPanel;
    }
}
