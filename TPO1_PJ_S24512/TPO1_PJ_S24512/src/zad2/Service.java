/**
 *
 *  @author Przybylski Jakub S24512
 *
 */

package zad2;


public class Service {

    public static Boolean debug = false;
    public static String country;

    public Service(String country) {
        this.country = country;
    }

    public static String getWeather(String city) {
        return Weather.callWeather(city);
    }

    public static Double getRateFor(String currency) {
        return ExchangeRate.getExchangeRateAsDouble(
                ExchangeRate.getExchangeRateFromResponse(
                        ExchangeRate.callExchangeRate(currency),currency)
        );
    }

    public static Double getNBPRate() {
        return NBPRate.twoTablesCall();
    }

    public static String getCountry(){
        return country;
    }
}
