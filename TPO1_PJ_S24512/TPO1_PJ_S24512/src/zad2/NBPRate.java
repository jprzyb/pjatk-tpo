package zad2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NBPRate {

    public static String URI = "http://api.nbp.pl/api/exchangerates/tables/{table}/";
    public static String T_URI;
    public static String callNBP(){
        try{
            URL url = new URL(T_URI);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            System.out.print(Service.debug ? "NBP Response: "+response+"\n" : "");
            return response.toString();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return "Something went wrong!";
    }
    public static void setUri(String uri, String table){
        String  result = uri.replace("{table}", table);
        System.out.print(Service.debug ? "NBP URI: "+result+"\n" : "");
        T_URI = result;
    }
    public static String getCurrencyCodeFromResponse(String response){
        String result = "N/A";
        String[] list = response.split(",");
        String currencyCode;

        try{
            currencyCode= CountryCurrency.getCurrency(CountryCurrency.getCurrencyFromCountry(
                    MainFrame.country.getText()
            ));
        }
        catch (Exception e){
            currencyCode = CountryCurrency.getCurrency(CountryCurrency.getCurrencyFromCountry(
                    Service.getCountry()
            ));
        }



        for(int i=0;i<list.length ;i++){
            if(list[i].contains(currencyCode)){
                result = list[i+1].replace("\"mid\":","").replace("\"","").replace("}","");
                System.out.print(Service.debug ? "NBP Currency Rate: "+result+"\n" : "");
                break;
            }
        }

        return result;
    }
    public static Double getRateAsDouble(String rate){
        if(rate.equals("N/A")) {
            System.out.print(Service.debug ? "NBP Currency Rate not found:  "+rate+"\n" : "");
            return -4.04;
        }
        return Double.parseDouble(rate);
    }
    public static Double twoTablesCall(){
        setUri(URI,"a");
        Double result = NBPRate.getRateAsDouble(
                NBPRate.getCurrencyCodeFromResponse(
                        NBPRate.callNBP()
                )
        );
        if(result == -4.04){
            setUri(URI, "b");
            result = NBPRate.getRateAsDouble(
                    NBPRate.getCurrencyCodeFromResponse(
                            NBPRate.callNBP()
                    )
            );
        }
        return result;
    }

}
