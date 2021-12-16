package wpta.wroclawpublictransportapp.application.dataproviders;

import com.teamdev.jxbrowser.browser.Browser;
import org.json.JSONArray;
import org.json.JSONObject;
import wpta.wroclawpublictransportapp.application.dataorganizers.GeoJSONTransformer;
import wpta.wroclawpublictransportapp.application.dataorganizers.JSONParser;
import wpta.wroclawpublictransportapp.application.visualizator.Visualizer;

import javax.script.ScriptException;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class Downloader {

    private final URL url;
    private final String URLParameters;
    private final JSONParser jsonParser;
    private final GeoJSONTransformer geoJSONTransformer;
    private final Browser browser;

    public Downloader(URL url, String URLParameters, Browser browser) {
        this.url = url;
        this.URLParameters = URLParameters;
        jsonParser = new JSONParser();
        geoJSONTransformer = new GeoJSONTransformer();
        this.browser = browser;
    }

    public void download() throws IOException, ScriptException, NoSuchMethodException {
        ConnectionProvider connectionProvider = new ConnectionProvider(url);
        URLConnection connection = connectionProvider.openConnection();
        connection.setDoOutput(true);

        OutputStreamWriter osWriter = new OutputStreamWriter(connection.getOutputStream());
        osWriter.write(URLParameters);
        osWriter.flush();

        String response;
        BufferedReader bfReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        JSONArray locations = null;

        while ((response = bfReader.readLine()) != null) {
            locations = jsonParser.parseJSON(response);
        }

        JSONObject locationsInGeoJSONForm = geoJSONTransformer.transformJSONtoGeoJSON(locations);
        Visualizer visualizer = new Visualizer(browser);
        visualizer.visualize(locationsInGeoJSONForm.toString());
        BufferedWriter writer = new BufferedWriter(new FileWriter("test_geojson.txt"));

        writer.write(locationsInGeoJSONForm.toString());
        writer.close();
        osWriter.close();
        bfReader.close();
    }
}
