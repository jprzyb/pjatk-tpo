package zad2;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public static JTextField country;
    public static JTextField currency;
    public static JTextField city;
    public static JButton web;
    public MainFrame(){
        setTitle("TPO1_PJ_S24512");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setPreferredSize(new Dimension(500,500));
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
        pack();

        setAllComponents();

        add(GuiComponents.getlabel("Country:"));
        add(GuiComponents.getlabel("City:"));
        add(GuiComponents.getlabel("Currency:"));

        add(country);
        add(city);
        add(currency);
        add(web);

        revalidate();
        repaint();
    }

    public static void setAllComponents(){
        country = GuiComponents.getCountryTextField();
        city = GuiComponents.getCityTextField();
        currency = GuiComponents.getCurrencyCodeTextField();
        web = GuiComponents.getWebButton();
    }

}
