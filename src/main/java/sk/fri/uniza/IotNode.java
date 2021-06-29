package sk.fri.uniza;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import sk.fri.uniza.api.WeatherStationService;
import sk.fri.uniza.model.WeatherData;

import java.io.IOException;
import java.util.List;

public class IotNode {
    private final Retrofit retrofit;
    private final WeatherStationService weatherStationService;

    public IotNode() {

        retrofit = new Retrofit.Builder()
                // Url adresa kde je umietnená WeatherStation služba
                .baseUrl("http://localhost:9000/")
                // Na konvertovanie JSON objektu na java POJO použijeme
                // Jackson knižnicu
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        // Vytvorenie inštancie komunikačného rozhrania
        weatherStationService = retrofit.create(WeatherStationService.class);

    }

    public WeatherStationService getWeatherStationService() {
        return weatherStationService;
    }

    double getAverageTemperature(String station,String from, String to)
    {
        Call<List<WeatherData>> historyWeather2 =
                getWeatherStationService().getHistoryWeather(station, from, to, List.of("airTemperature"));

        try {
            // Odoslanie požiadavky na server pomocou REST rozhrania
            Response<List<WeatherData>> response = historyWeather2.execute();

            if (response.isSuccessful()) { // Dotaz na server bol úspešný
                List<WeatherData> body = response.body();
                System.out.println(body);
                return body.stream().mapToDouble(WeatherData::getAirTemperature).sum() / body.size();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
