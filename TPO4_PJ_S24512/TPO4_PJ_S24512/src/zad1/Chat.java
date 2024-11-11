package zad1;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Chat{
    private final Session session;
    private final MessageProducer sender;
    private final TopicSubscriber subscriber;
    private final ChatApp chatApp;
    public Chat(String topic_name, String factoryName, String subscriptionName, ChatApp chatApp){
        this.chatApp = chatApp;
        try { // init config
            Context context = new InitialContext();
            ConnectionFactory factory = (ConnectionFactory) context.lookup(factoryName);
            Topic topic = (Topic) context.lookup(topic_name);
            Connection connection = factory.createConnection();
            session = connection.createSession(
                    false, Session.AUTO_ACKNOWLEDGE);
            subscriber = session.createDurableSubscriber(
                    topic, subscriptionName);
            Destination dest = (Destination) context.lookup(topic_name);
            sender = session.createProducer(dest);
            connection.start();
            start();
        } catch (NamingException | JMSException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() {
        Thread receive = new Thread(this::receiving);
        receive.start();
    }
    private void receiving(){
        while(true){
            chatApp.addNewMessage(receiveMessages());
        }
    }
    public void sendMessage(String msg){
        try {
            TextMessage message = session.createTextMessage();
            message.setText(msg);
            sender.send(message);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
    private String receiveMessages(){
        try{
            Message message = subscriber.receive();
            TextMessage text = (TextMessage) message;
            return text.getText();
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
