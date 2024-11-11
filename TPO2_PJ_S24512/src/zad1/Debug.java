package zad1;

public class Debug {
    public static boolean DEBUG = false;

    public static void printDebug(String msg){
        System.out.print(DEBUG ? msg + "\n" : "");
    }
}
