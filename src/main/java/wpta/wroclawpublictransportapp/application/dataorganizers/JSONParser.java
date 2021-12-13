package wpta.wroclawpublictransportapp.application.dataorganizers;

import org.json.JSONArray;

public class JSONParser {
    public JSONArray parseJSON(String response) {
        return new JSONArray(response);
    }
}
