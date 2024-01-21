package me.caleb;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Add API_KEY from weatherapi.com in .envfile
        Dotenv env = Dotenv.load();
        String API_KEY = env.get("API_KEY");

        String ANSI_RESET = "\u001b[0m";
        String ANSI_BLUE = "\u001b[34m";

        String banner = "\n" +
                " __      __               __  .__                      _____ __________.___ \n" +
                "/  \\    /  \\ ____ _____ _/  |_|  |__   ___________    /  _  \\\\______   \\   |\n" +
                "\\   \\/\\/   // __ \\\\__  \\\\   __\\  |  \\_/ __ \\_  __ \\  /  /_\\  \\|     ___/   |\n" +
                " \\        /\\  ___/ / __ \\|  | |   Y  \\  ___/|  | \\/ /    |    \\    |   |   |\n" +
                "  \\__/\\  /  \\___  >____  /__| |___|  /\\___  >__|    \\____|__  /____|   |___|\n" +
                "       \\/       \\/     \\/          \\/     \\/                \\/              \n";

        System.out.println(ANSI_BLUE + banner + ANSI_RESET);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter location such as zipcode, city, or auto:ip -> ");
        String q = scanner.nextLine();

        String url = "https://api.weatherapi.com/v1/current.json?key=%key%&q=%q%&aqi=no"
                .replace("%key%", API_KEY)
                .replace("%q%", q);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            JsonObject data = JsonParser.parseString(response.body().string()).getAsJsonObject();
            JsonObject location = data.get("location").getAsJsonObject();
            JsonObject current = data.get("current").getAsJsonObject();

            String name = location.get("name").getAsString();
            String currentF = current.get("temp_f").getAsString();
            String currentC = current.get("temp_c").getAsString();
            String windMph = current.get("wind_mph").getAsString();
            String windKph = current.get("wind_kph").getAsString();
            String windDeg = current.get("wind_degree").getAsString();
            String windDir = current.get("wind_dir").getAsString();

            System.out.println("-------------------------------");
            System.out.println("Location -> " + name);
            System.out.println("Current Fahrenheit -> " + currentF);
            System.out.println("Current Celsius -> " + currentC);
            System.out.println("Current Wind MPH -> " + windMph);
            System.out.println("Current Wind KPH -> " + windKph);
            System.out.println("Current Wind Degree -> " + windDeg);
            System.out.println("Current Wind Direction -> " + windDir);
            System.out.println("-------------------------------");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
