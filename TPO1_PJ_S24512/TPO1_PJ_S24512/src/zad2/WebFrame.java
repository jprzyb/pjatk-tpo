package zad2;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;

public class WebFrame extends JFrame {

    public WebFrame(String country){

        setTitle(country+" Wiki");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);

        JFXPanel jfxPanel = new JFXPanel();
        add(jfxPanel, BorderLayout.CENTER);

        Platform.runLater(() -> initWebBrowser(jfxPanel));
    }

    private static void initWebBrowser(JFXPanel jfxPanel) {

        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        webEngine.load("https://pl.wikipedia.org/wiki/"+ MainFrame.city.getText().trim());

        // Ustawienie sceny
        Scene scene = new Scene(webView);
        jfxPanel.setScene(scene);
    }

}
