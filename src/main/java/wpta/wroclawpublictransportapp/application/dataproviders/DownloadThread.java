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

/**
 * Thread managing the option of rendering of selected vehicles
 */
public class DownloadThread implements Runnable {

    private final URL url;
    private final String URLParameters;
    private final JSONParser jsonParser;
    private final GeoJSONTransformer geoJSONTransformer;
    private final Browser browser;
    private final Integer refreshTime;
    private final Downloader downloader;
    private Flag flag;
    private Thread thread;

    public DownloadThread(URL url, String URLParameters, Integer refreshTime) {
        this.url = url;
        this.URLParameters = URLParameters;
        jsonParser = new JSONParser();
        geoJSONTransformer = new GeoJSONTransformer();
        downloader = new Downloader(url, URLParameters, jsonParser);
        this.browser = MapViewProvider.getBrowser();
        this.refreshTime = refreshTime;

        thread = new Thread(this);
        flag = Flag.RUNNING;
        thread.start();

    }

    private void render() throws ScriptException, IOException, NoSuchMethodException {
        JSONArray locations = downloader.download();
        JSONObject locationsInGeoJSONForm = geoJSONTransformer.transformJSONtoGeoJSON(locations);
        VehicleLocationRender vehicleLocationRender = new VehicleLocationRender(browser);
        vehicleLocationRender.visualize(locationsInGeoJSONForm.toString());
        BufferedWriter writer = new BufferedWriter(new FileWriter("test_geojson.txt"));

        writer.write(locationsInGeoJSONForm.toString());
        writer.close();
    }

    @Override
    public void run() {
        while (flag == Flag.RUNNING) {
            try {
                render();
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
