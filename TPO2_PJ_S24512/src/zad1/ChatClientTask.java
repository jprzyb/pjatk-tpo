/**
 *
 *  @author Przybylski Jakub S24512
 *
 */

package zad1;


import java.util.List;
import java.util.concurrent.ExecutionException;

public class ChatClientTask implements Runnable {
    ChatClient client;
    List<String> msgs;
    int wait;
    public ChatClientTask(ChatClient client, List<String> msgs, int wait){
        this.wait = wait;
        this.client = client;
        this.msgs = msgs;
        debug("Constructed: "+client.getId()+", wait="+ wait+", msgs="+msgs);
    }
    public static ChatClientTask create(ChatClient client, List<String> msgs, int wait) {
        return new ChatClientTask( client, msgs, wait);
    }

    @Override
    public void run() {
        debug("Execution started for: "+client.getId());
        try{
            client.login();
            Thread.sleep(wait);
            for(String m : msgs){
                client.send(m);
                Thread.sleep(wait);
            }
            client.logout();
            Thread.sleep(wait);
        }catch (Exception e){
            e.printStackTrace();
        }
        debug("Execution finished for: "+client.getId());
    }

    public ChatClient getClient() {
        return client;
    }

    public void get() throws InterruptedException, ExecutionException {
    }
    private void debug(String msg){
//        Debug.printDebug("[CCT]: "+msg);
    }
}
