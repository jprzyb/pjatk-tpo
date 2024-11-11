package zad2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class CityGeolocation {
    static final String URI = "http://api.openweathermap.org/geo/1.0/direct?q={city}&appid={apikey}";
    static final String APIKEY = "2b853c092a489b93e8e50b88a65cb7d6";

    public static String setUri(String city){
        String result = URI.replace("{city}",city).replace("{apikey}", APIKEY).trim();
        System.out.print(Service.debug ? "City Geolocation URI: "+result+"\n" : "");
        return result;
    }

    public static String callApi(String city){
        try{
            URL url = new URL(setUri(city));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            System.out.print(Service.debug ? "City Geolocation Response: "+response+"\n" : "");
            return response.toString();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return "Something went wrong!";
    }

    public static String[] getGeolocationFromResponse(String response){
        String[] s = response.split(",");
        String[] result = {"",""};


        for(String e : s){
            if(e.contains("lat")){
                result[0] = e.replace("\"lat\":", "");
                System.out.print(Service.debug ? "City Geolocation lat: "+result[0]+"\n" : "");
            }
            else if(e.contains("lon")){
                result[1] = e.replace("\"lon\":", "");
                System.out.print(Service.debug ? "City Geolocation lon: "+result[1]+"\n" : "");
            }
        }

        return result;
    }

}
