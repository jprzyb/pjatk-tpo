package zad1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Set;
import java.nio.ByteBuffer;

public class ChatServer {


    public static final String LOGIN_PROT="LoginMe";
    public static final String LOGOUT_PROT="LogoutMe";
    String host;
    int port;
    ServerSocketChannel serverSocket;
    String serverLog;
    Thread serverThread;
    boolean running = false;
    Selector selector;
    int loggedIn;
    int loggedOut;

    public ChatServer(String host, int port) {
        this.host = host;
        this.port = port;
        this.serverLog="";
        this.serverThread = new Thread(this::serverLogic);
        this.serverSocket = null;
        this.loggedIn = 0;
        this.loggedOut = 0;
        debug("host="+host+", port="+port);
    }
    public void startServer(){
        running = true;
        try {
            selector = Selector.open();
            serverSocket = ServerSocketChannel.open();
            serverSocket.bind(new InetSocketAddress(port));
            serverSocket.configureBlocking(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.serverThread.start();
    }
    private void serverLogic() {
        try {
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);
            while (running){
                int readyChannels = selector.select();
                if (readyChannels == 0) continue;
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    keyIterator.remove();
                    if (key.isAcceptable()) {
                        handleClient(key);
                    } else if (key.isReadable()) {
                        handleClientMsg(key);
                    }
                }

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    private void handleClient(SelectionKey key){
        try{
            loggedIn++;
            ServerSocketChannel server = (ServerSocketChannel) key.channel();
            SocketChannel client = server.accept();
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ);
            debug("Accepted connection from " + client.getRemoteAddress());
            debug("Total clients logged in: "+loggedIn);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    private void handleClientMsg(SelectionKey key){
        try {
            String recivedMsg;
            SocketChannel client = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.clear();
            int bytesRead = client.read(buffer);

            buffer.flip();
            byte[] data = new byte[bytesRead];
            buffer.get(data);
            recivedMsg = new String(data);
            debug("Received: " + recivedMsg);

            if(recivedMsg.contains(LOGOUT_PROT)) {

                ByteBuffer loggedOffMsg = ByteBuffer.allocate(serverLog.getBytes().length);
                loggedOffMsg.put(serverLog.getBytes());
                loggedOffMsg.flip();
                client.write(loggedOffMsg);

                client.close();
                loggedOut++;
                debug("Total clients logged off: "+loggedOut);
            }
            addLog(recivedMsg);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void stopServer() {
        try {
            if(loggedIn == loggedOut && loggedIn>0 && loggedOut>0){
                running = false;
                this.serverSocket.close();
                serverThread.interrupt();
            }else {
                try {
                    Thread.sleep(100);
                    stopServer();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void addLog(String msg){
        if(msg.split("::").length>1){
            for(String miniMsg : msg.split("::")){
                addLog(miniMsg);
            }
        }
        else {
            String result;
            if(msg.contains(LOGIN_PROT)){
                String[] miniMsg = msg.split(";;");
                msg = miniMsg[0] + " logged in";
                result = msg.replace("::","");
            } else if (msg.contains(LOGOUT_PROT)) {
                String[] miniMsg = msg.split(";;");
                msg = miniMsg[0] + " logged out";
                result = msg.replace("::","");
            }else {
                String[] miniMsg = msg.split(";;");
                msg = miniMsg[1];
                result = msg.replace("::","");
            }
            serverLog+= LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"))
                    + " " + result + "\n";
        }
    }
    public String getServerLog() {
        return serverLog;
    }
    private void debug(String msg){
        Debug.printDebug("[S]: "+msg);
    }
}
