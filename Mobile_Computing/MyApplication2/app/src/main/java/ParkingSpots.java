import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParkingSpots {
    JSONArray spots;

    public ParkingSpots() throws JSONException {
        spots = new JSONArray();
        populateSpots();
    }

    private void populateSpots() throws JSONException {
        // Creating and adding parking spot objects to the JSONArray
        JSONObject spot1 = new JSONObject();
        spot1.put("id", 1);
        spot1.put("name", "First Year");
        spot1.put("code", "-26.18517,28.02614");

        JSONObject spot2 = new JSONObject();
        spot2.put("id", 2);
        spot2.put("name", "Second Year");
        spot2.put("code", "-26.18750,28.02646");

        JSONObject spot3 = new JSONObject();
        spot3.put("id", 3);
        spot3.put("name", "Third Year");
        spot3.put("code", "-26.19252,28.02702");

        JSONObject spot4 = new JSONObject();
        spot4.put("id", 4);
        spot4.put("name", "Staff");
        spot4.put("code", "-26.19051,28.02766");

        JSONObject spot5 = new JSONObject();
        spot5.put("id", 5);
        spot5.put("id", "Staff & Wits Plus Parking");
        spot5.put("code", "-26.18967, 28.02748");

        spots.put(spot1);
        spots.put(spot2);
        spots.put(spot3);
        spots.put(spot4);
        spots.put(spot5);
    }

    public void printSpots() throws JSONException {
        // Printing out the JSON array
        System.out.println(spots.toString(2)); // Pretty print with an indentation of 2
    }

    public static void main(String[] args) throws JSONException {
        ParkingSpots parkingSpots = new ParkingSpots();
        parkingSpots.printSpots();
    }
}
