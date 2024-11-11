package zad1;

import javax.swing.*;
import java.awt.*;

public class ChatApp {
    private final JTextField textField;
    private final DefaultListModel<String> listModel;
    private final Chat chat;
    private final String userName;

    public ChatApp(String topic_name, String factoryName, String subscriptionName, String userName) {
        chat = new Chat(topic_name,factoryName, subscriptionName, this);
        this.userName = userName;
        JFrame frame = new JFrame(userName);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        textField = new JTextField();
        frame.add(textField, BorderLayout.NORTH);
        listModel = new DefaultListModel<>();
        JList<String> messageList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(messageList);
        frame.add(scrollPane, BorderLayout.CENTER);
        JButton sendButton = new JButton("Send");
        frame.add(sendButton, BorderLayout.SOUTH);
        sendButton.addActionListener(e -> sendMessage());
        frame.setVisible(true);
    }

    private void sendMessage() {
        String message = textField.getText();
        if (!message.isEmpty()) {
            chat.sendMessage(userName + ": " + message);
            textField.setText("");
        }
    }
    public void addNewMessage(String msg){
        listModel.addElement(msg);
    }
}
