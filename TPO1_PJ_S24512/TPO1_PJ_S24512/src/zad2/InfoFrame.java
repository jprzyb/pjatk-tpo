package zad2;

import javax.swing.*;
import java.awt.*;

public class InfoFrame extends JFrame {

    public InfoFrame(String info, String apiName){
        setTitle(apiName);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        setSize(400, 300);
        setPreferredSize(new Dimension(400,300));
        setLocationRelativeTo(null);
        pack();

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setText(info);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setWheelScrollingEnabled(true);
        scrollPane.setSize(new Dimension(400,300));
        scrollPane.setPreferredSize(new Dimension(400,300));
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

}
