package com.weather;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.JsonObject;


public class WeatherClient {
	// Open-Meteo API (No API Key Required)
    private static final String API_URL = "https://api.open-meteo.com/v1/forecast?latitude=35&longitude=139&current_weather=true";

    public static void main(String[] args) {
        try {
            // Create URL object
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Check the response code
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Parse JSON response using Gson
                Gson gson = new Gson();
                JsonObject json = gson.fromJson(response.toString(), JsonObject.class);
                JsonObject currentWeather = json.getAsJsonObject("current_weather");

                // Display structured weather info
                System.out.println("=== Current Weather in Nhavi ===");
                System.out.println("Temperature    : " + currentWeather.get("temperature") + " °C");
                System.out.println("Wind Speed     : " + currentWeather.get("windspeed") + " km/h");
                System.out.println("Wind Direction : " + currentWeather.get("winddirection") + "°");
                System.out.println("Time           : " + currentWeather.get("time").getAsString());
                System.out.println("=================================");

            } else {
                System.out.println("Request failed. HTTP Code: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
