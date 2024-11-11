/**
 *
 *  @author Przybylski Jakub S24512
 *
 */

package zad2;


public class Main {
  public static void main(String[] args) {
    Service s = new Service("Poland");
    String weatherJson = s.getWeather("Warsaw");
    Double rate1 = s.getRateFor("USD");
    Double rate2 = s.getNBPRate();
    // ...
    // część uruchamiająca GUI
//    new MainFrame();
    System.out.println(weatherJson);
    System.out.println(rate1);
    System.out.println(rate2);
  }
}
