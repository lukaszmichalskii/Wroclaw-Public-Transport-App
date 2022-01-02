package wpta.wroclawpublictransportapp.application.dataproviders;

import org.json.JSONArray;
import org.json.JSONObject;
import wpta.wroclawpublictransportapp.application.dataorganizers.GeoJSONTransformer;
import wpta.wroclawpublictransportapp.application.dataorganizers.JSONParser;
import wpta.wroclawpublictransportapp.application.map.MapViewProvider;
import wpta.wroclawpublictransportapp.application.javascriptexecution.JavaScriptExecutor;
import wpta.wroclawpublictransportapp.application.javascriptexecution.VehicleLocationRender;

import javax.script.ScriptException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

/**
 * Thread managing the option of rendering of selected vehicles
 */
public class DownloadThread implements Runnable {

    private final GeoJSONTransformer geoJSONTransformer;
    private final Integer refreshTime;
    private final Downloader downloader;
    private Flag flag;

    public DownloadThread(URL url, String URLParameters, Integer refreshTime) {
        JSONParser jsonParser = new JSONParser();
        geoJSONTransformer = new GeoJSONTransformer();
        downloader = new Downloader(url, URLParameters, jsonParser);
        this.refreshTime = refreshTime;

        Thread thread = new Thread(this);
        flag = Flag.RUNNING;
        thread.start();

    }

    private void render() throws ScriptException, IOException, NoSuchMethodException {
        JSONArray locations = downloader.download();
        JSONObject locationsInGeoJSONForm = geoJSONTransformer.transformJSONtoGeoJSON(locations);
        JavaScriptExecutor jsExecutor = new VehicleLocationRender(MapViewProvider.getBrowser(), locationsInGeoJSONForm.toString());
        jsExecutor.execute();
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
