package UI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @deprecated use MainMenu now due to Swing GUI builder.
 */
public class oldMainMenu extends JPanel {
    oldMainMenu(){
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

        JPanel menuOptions = new JPanel();
        menuOptions.setLayout(new BoxLayout(menuOptions, BoxLayout.PAGE_AXIS));
        this.add(menuOptions, BorderLayout.CENTER);

        JButton btnManageStock = new JButton("Manage Stock");
        btnManageStock.setAlignmentX(0.5f);
        JButton btnViewReports = new JButton("View Reports");
        btnViewReports.setAlignmentX(0.5f);
        JButton btnCSInfo = new JButton("Customer/Supplier Information");
        btnCSInfo.setAlignmentX(0.5f);
        JButton btnMakeTransaction = new JButton("Make Transaction");
        btnMakeTransaction.setAlignmentX(0.5f);
        btnMakeTransaction.addActionListener(e->{
            this.setVisible(false);
            Driver.swapToMakeTransaction();
        });

        menuOptions.add(btnManageStock);
        menuOptions.add(btnViewReports);
        menuOptions.add(btnCSInfo);
        menuOptions.add(btnMakeTransaction);
        menuOptions.setVisible(true);
    }
}
