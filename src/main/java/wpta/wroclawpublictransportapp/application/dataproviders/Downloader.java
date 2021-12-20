package wpta.wroclawpublictransportapp.application.dataproviders;

import com.teamdev.jxbrowser.browser.Browser;
import org.json.JSONArray;
import org.json.JSONObject;
import wpta.wroclawpublictransportapp.application.dataorganizers.GeoJSONTransformer;
import wpta.wroclawpublictransportapp.application.dataorganizers.JSONParser;
import wpta.wroclawpublictransportapp.application.map.MapViewProvider;
import wpta.wroclawpublictransportapp.application.visualizator.VehicleLocationRender;

import javax.script.ScriptException;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class Downloader implements Runnable {

    private final URL url;
    private final String URLParameters;
    private final JSONParser jsonParser;
    private final GeoJSONTransformer geoJSONTransformer;
    private final Browser browser;
    private final Integer refreshTime;
    private Flag flag;
    private Thread thread;

    public Downloader(URL url, String URLParameters, Integer refreshTime) {
        this.url = url;
        this.URLParameters = URLParameters;
        jsonParser = new JSONParser();
        geoJSONTransformer = new GeoJSONTransformer();
        this.browser = MapViewProvider.getBrowser();
        this.refreshTime = refreshTime;

        thread = new Thread(this);
        flag = Flag.RUNNING;
        thread.start();

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
        VehicleLocationRender vehicleLocationRender = new VehicleLocationRender(browser);
        vehicleLocationRender.visualize(locationsInGeoJSONForm.toString());
        BufferedWriter writer = new BufferedWriter(new FileWriter("test_geojson.txt"));

        writer.write(locationsInGeoJSONForm.toString());
        writer.close();
        osWriter.close();
        bfReader.close();
    }

    @Override
    public void run() {
        while (flag == Flag.RUNNING) {
            try {
                download();
                Thread.sleep(refreshTime);
            } catch (IOException | ScriptException | NoSuchMethodException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        flag = Flag.STOP;
    }
}
