package controller;

import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherService {
    private static final String API_KEY = "1f0aa4fc0f98459881785945243011";

    public static String getWeather() {
        try {
            String city = "Colombo";
            String url = "http://api.weatherapi.com/v1/current.json?key=" + API_KEY + "&q=" + city + "&aqi=no";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject json = new JSONObject(response.body());

            String location = json.getJSONObject("location").getString("name");
            double temp = json.getJSONObject("current").getDouble("temp_c");
            String condition = json.getJSONObject("current").getJSONObject("condition").getString("text");
            double wind = json.getJSONObject("current").getDouble("wind_kph");
            int humidity = json.getJSONObject("current").getInt("humidity");

            // Pick an emoji for the condition (optional - basic matching)
            String emoji = "🌤️"; // default
            if (condition.toLowerCase().contains("rain")) emoji = "🌧️";
            else if (condition.toLowerCase().contains("sun")) emoji = "☀️";
            else if (condition.toLowerCase().contains("cloud")) emoji = "☁️";
            else if (condition.toLowerCase().contains("clear")) emoji = "🌙";

            return String.format("%s %.0f°C Today | %s →  💨 Wind: %.0fkm/h →  💧 Humidity: %d%%",
                    emoji, temp, location, wind, humidity);

        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Error fetching weather data.";
        }
    }

}
