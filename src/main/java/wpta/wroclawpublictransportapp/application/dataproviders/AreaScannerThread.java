package wpta.wroclawpublictransportapp.application.dataproviders;

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.dom.Document;
import com.teamdev.jxbrowser.dom.InputElement;
import com.teamdev.jxbrowser.frame.Frame;
import javafx.application.Platform;
import org.json.JSONArray;
import wpta.wroclawpublictransportapp.application.alert.AlertManager;
import wpta.wroclawpublictransportapp.application.dataorganizers.CoordinatesExtractor;
import wpta.wroclawpublictransportapp.application.dataorganizers.JSONParser;
import wpta.wroclawpublictransportapp.application.distancecalc.Coordinate;
import wpta.wroclawpublictransportapp.application.distancecalc.DistanceCalculator;
import wpta.wroclawpublictransportapp.application.map.MapViewProvider;

import java.io.IOException;
import java.net.URL;

/**
 * Thread managing the option of finding the desired vehicle in the designated area
 */
public class AreaScannerThread implements Runnable {

    private final CoordinatesExtractor coordinatesExtractor;
    private final Browser browser;
    private final Downloader downloader;
    private final String radius;
    private Flag flag;

    public AreaScannerThread(URL url, String URLParameters, String radius) {
        JSONParser jsonParser = new JSONParser();
        coordinatesExtractor = new CoordinatesExtractor();
        this.browser = MapViewProvider.getBrowser();
        downloader = new Downloader(url, URLParameters, jsonParser);
        this.radius = radius;

        Thread thread = new Thread(this);
        flag = Flag.RUNNING;
        thread.start();
    }

    private boolean scan() throws IOException {
        Coordinate markedAreaCoords = getMarkedAreaCoordinates();
        JSONArray locations = downloader.download();
        return performCalculations(markedAreaCoords, locations, new DistanceCalculator());
    }

    @Override
    public void run() {
        while (flag == Flag.RUNNING) {
            try {
                showStatement(scan());
                stop();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        flag = Flag.STOP;
    }

    private void showStatement(Boolean result) {
        if (result)
            Platform.runLater(() -> AlertManager.throwInformation("Selected vehicle is in the designated area."));
        else
            Platform.runLater(() -> AlertManager.throwInformation("Vehicle not found in the designated area."));
    }

    private Coordinate getMarkedAreaCoordinates() {
        final String[] areaCenter = new String[1];
        Document document = browser.mainFrame().flatMap(Frame::document).get();
        document.findElementById("coordinates").ifPresent(address ->
                areaCenter[0] = ((InputElement) address).value()
        );

        return coordinatesExtractor.extract(areaCenter[0]);
    }

    private boolean performCalculations(Coordinate target, JSONArray locations, DistanceCalculator calculationsEngine) {
        Coordinate dummyCoords;

        for (int i = 0; i < locations.length(); i++) {
            dummyCoords = new Coordinate(locations.getJSONObject(i).getDouble("x"), locations.getJSONObject(i).getDouble("y"));
            double distance = calculationsEngine.calculateDistance(target, dummyCoords);
            if (distance <= Double.parseDouble(radius)) {
                return true;
            }
        }

        return false;
    }
}
