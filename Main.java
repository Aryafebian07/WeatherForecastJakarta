import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

class Main {
    public static void main(String[] args) {
        String apiKey = "e968ec017bee7fe4d025fbf19c3eb8d2";
        String city = "Jakarta";
        String apiUrl = "http://api.openweathermap.org/data/2.5/forecast?q=" + city + "&cnt=40&units=metric&appid=" + apiKey;
      
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            JSONParser parser = new JSONParser();
            JSONObject data = (JSONObject) parser.parse(response.toString());
            JSONArray list = (JSONArray) data.get("list");

            System.out.println("Weather Forecast:");
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat outputFormat = new SimpleDateFormat("E, dd MMM yyyy");
          
            String currentDate = null;
          
            for (int i = 0; i < list.size(); i++) {
                JSONObject dayData = (JSONObject) list.get(i);
                String date = (String) dayData.get("dt_txt");
                Date date2 = inputFormat.parse(date);
                String formattedDate = outputFormat.format(date2);
                JSONObject mainData = (JSONObject) dayData.get("main");
                String temperature = mainData.get("temp").toString();
                if (!formattedDate.equals(currentDate)) {
                  System.out.println(formattedDate + ": " + temperature + "°C");
                  currentDate = formattedDate;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}