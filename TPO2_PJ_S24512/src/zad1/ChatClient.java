/**
 *
 *  @author Przybylski Jakub S24512
 *
 */

package zad1;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatClient {
    String host;
    int port;
    String id;
    StringBuilder chatView;
    SocketChannel socket;
    OutputStream out;
    InputStream in;

    public ChatClient(String host, int port, String id) {
        this.host = host;
        this.port  = port;
        this.id = id;
        chatView = new StringBuilder().append("=== ").append(id).append(" chat view\n");
        debug("Client id: "+id);
    }

    public void login(){
        try{
            String toSend = id+";;"+ChatServer.LOGIN_PROT +"::";
            debug(id+" is logging in.");
            socket = SocketChannel.open();
            socket.connect(new InetSocketAddress(host,port));
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.clear();
            buffer.put(toSend.getBytes());
            buffer.flip();
            socket.write(buffer);
            debug(id+" logged in.");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void logout(){
        try {
            String toSend = id+";;"+ChatServer.LOGOUT_PROT +"::";
            debug(id+" is logging out.");
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.clear();
            buffer.put(toSend.getBytes());
            buffer.flip();
            socket.write(buffer);

            ByteBuffer loggedOffMsg = ByteBuffer.allocate(1024);
            socket.read(loggedOffMsg);
            loggedOffMsg.flip();
            String serverLog = new String(loggedOffMsg.array(), loggedOffMsg.position(), loggedOffMsg.remaining());
            trimAndSaveServerLog(serverLog);

            debug(id+" is logged out.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(String req){
        try {
            String toSend = id+";;"+req+"::";
            debug(id+" is sending.");
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.clear();
            buffer.put(toSend.getBytes());
            buffer.flip();
            socket.write(buffer);
            debug(id+" sent: "+buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getChatView() {
        return chatView.toString();
    }

    public String getId(){
        return id;
    }

    private void trimAndSaveServerLog(String serverLog){
        List<String> logList = Arrays.asList(serverLog.split("\n"));
        StringBuilder res = new StringBuilder("");

        // removing logs before client logged in
        for(int i=0;i<logList.size();i++){
            if(!logList.get(i).contains(id)) logList.set(i, "DOnotADD");
            else break;

        }

        for(int i = logList.size()-1 ; i>=0 ; i--){
            if(!logList.get(i).contains(id)) logList.set(i, "DOnotADD");
            else break;
        }

        for(String s : logList){
            if(!s.equals("DOnotADD")) res.append(s.substring(s.indexOf(" ")+1)).append("\n");
        }
        addLog(res.toString());
    }
    private void addLog(String msg){
        chatView.append(msg).append("\n");
    }

    private void debug(String msg){
//        Debug.printDebug("[C]: "+msg);
    }
}
