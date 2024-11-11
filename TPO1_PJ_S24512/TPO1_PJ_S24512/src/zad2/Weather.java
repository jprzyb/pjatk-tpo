package zad2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Weather {

    static final String URI = "https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={apikey}";
    static final String APIKEY = "2b853c092a489b93e8e50b88a65cb7d6";
    public static String callWeather(String city){

        try{
            String geo[] = CityGeolocation.getGeolocationFromResponse(CityGeolocation.callApi(city));
            URL url = new URL(setUri(URI,APIKEY,geo[0], geo[1]));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            System.out.print(Service.debug ? "Weather Response: "+response+"\n" : "");
            return response.toString();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return "Something went wrong!";
    }
    public static String setUri(String uri, String apiKey, String lat, String lon){
        String result = uri.replace("{lat}", lat).replace("{lon}", lon).replace("{apikey}", apiKey).trim();
        System.out.print(Service.debug ? "Weather URI: "+result+"\n" : "");
        return result;
    }

}
