package sk.fri.uniza;

import retrofit2.Call;
import retrofit2.Response;
import sk.fri.uniza.model.Location;
import sk.fri.uniza.model.WeatherData;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Hello IoT!
 */
public class App {
    public static void main(String[] args) {
        IotNode iotNode = new IotNode();
        // Vytvorenie požiadavky na získanie údajov o aktuálnom počasí z
        // meteo stanice s ID: station_1
        Call<Map<String, String>> currentWeather =
                iotNode.getWeatherStationService()
                        .getCurrentWeatherAsMap("station_1",
                                List.of("time", "date",
                                        "airTemperature"));
        try {
            // Odoslanie požiadavky na server pomocou REST rozhranie
            Response<Map<String, String>> response = currentWeather.execute();

            if (response.isSuccessful()) { // Dotaz na server bol úspešný
                //Získanie údajov vo forme Mapy stringov
                Map<String, String> body = response.body();
                System.out.println(body);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Vytvorenie požiadavky na získanie údajov o všetkých meteo staniciach
        Call<List<Location>> stationLocations =
                iotNode.getWeatherStationService().getStationLocations();

        try {
            Response<List<Location>> response = stationLocations.execute();

            if (response.isSuccessful()) { // Dotaz na server bol úspešný
                //Získanie údajov vo forme Zoznam lokacií
                List<Location> body = response.body();

                System.out.println(body);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Vytvorenie požiadavky na získanie údajov o aktuálnom počasí z
        // meteo stanice s ID: station_1
        Call<WeatherData> currentWeatherPojo =
                iotNode.getWeatherStationService()
                        .getCurrentWeather("station_1");


        try {
            // Odoslanie požiadavky na server pomocou REST rozhranie
            Response<WeatherData> response = currentWeatherPojo.execute();

            if (response.isSuccessful()) { // Dotaz na server bol úspešný
                //Získanie údajov vo forme inštancie triedy WeatherData
                WeatherData body = response.body();
                System.out.println(body);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

         Call<List<WeatherData>> historyWeather =
                iotNode.getWeatherStationService().getHistoryWeather("station_1","29/06/2021 11:00","29/06/2021 16:00");
        try {
            Response<List<WeatherData>> response = historyWeather.execute();
            if (response.isSuccessful()) { // Dotaz na server bol úspešný
                //Získanie údajov vo forme Zoznam lokacií
                List<WeatherData> body = response.body();
                System.out.println(body);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Call<List<WeatherData>> historyWeather2 =
                iotNode.getWeatherStationService().getHistoryWeather("station_1","29/06/2021 10:00","29/06/2021 11:00", List.of("date","time","airTemperature"));
        try {
            Response<List<WeatherData>> response = historyWeather2.execute();
            if (response.isSuccessful()) { // Dotaz na server bol úspešný
                //Získanie údajov vo forme Zoznam lokacií
                List<WeatherData> body = response.body();
                System.out.println(body);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
