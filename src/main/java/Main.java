import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Main {
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("flights_and_forecast.json"));
            JSONObject data = (JSONObject) obj;
            JSONArray flights = (JSONArray) data.get("flights");
            JSONObject forecast = (JSONObject) data.get("forecast");
            for (Object flightObj : flights) {
                JSONObject flight = (JSONObject) flightObj;
                String fromCity = (String) flight.get("from");
                String toCity = (String) flight.get("to");
                long departureTime = (long) flight.get("departure");
                long duration = (long) flight.get("duration");
                long arrivalTime = departureTime + duration;
                JSONObject departureForecast = (JSONObject) ((JSONArray) forecast.get(fromCity)).get((int) departureTime);
                JSONObject arrivalForecast = (JSONObject) ((JSONArray) forecast.get(toCity)).get((int) arrivalTime);
                String status = "отменен";
                if ((long) departureForecast.get("wind") <= 30 && (long) departureForecast.get("visibility") >= 200 && (long) arrivalForecast.get("wind") <= 30 && (long) arrivalForecast.get("visibility") >= 200) {
                    status = "по расписанию";
                }
                System.out.println(flight.get("no") + " | " + fromCity + " -> " + toCity + " | " + status);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}