package zad1;

public class Main {
    public static void main(String[] args) {
        String topicName = "chat_topic";
        String factoryName = "ConnectionFactory";
        new ChatApp(topicName, factoryName,"usr1", "usr1");
        new ChatApp(topicName, factoryName,"usr2", "usr2.");
        new ChatApp(topicName, factoryName,"usr3", "usr3");
    }
}
