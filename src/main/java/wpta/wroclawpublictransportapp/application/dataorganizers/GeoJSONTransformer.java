package wpta.wroclawpublictransportapp.application.dataorganizers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class GeoJSONTransformer {

    public JSONObject transformJSONtoGeoJSON(JSONArray data) {
        JSONObject featureCollection = new JSONObject();

        try {
            featureCollection.put("type", "featureCollection");
            JSONArray featureList = new JSONArray();
            // iterate through your list
            for (int i = 0; i < data.length(); i++) {
                // {"geometry": {"type": "Point", "coordinates": [-94.149, 36.33]}
                JSONObject point = new JSONObject();
                point.put("type", "Point");
                // construct a JSONArray from a string; can also use an array or list
                JSONObject transport = data.getJSONObject(i);
                BigDecimal lon = (BigDecimal) transport.get("x");
                BigDecimal lat = (BigDecimal) transport.get("y");
                JSONArray coord = new JSONArray("["+lon+","+lat+"]");
                point.put("coordinates", coord);
                JSONObject feature = new JSONObject();
                feature.put("geometry", point);
                featureList.put(feature);
                featureCollection.put("features", featureList);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("GeoJSONForm="+featureCollection);
        return featureCollection;
    }
}
