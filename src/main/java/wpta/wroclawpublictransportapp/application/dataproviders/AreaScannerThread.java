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

    private void scan() throws IOException {
        boolean found = false;
        final String[] areaCenter = new String[1];
        Double[] coords;
        Document document = browser.mainFrame().flatMap(Frame::document).get();
        document.findElementById("coordinates").ifPresent(address ->
               areaCenter[0] = ((InputElement) address).value()
        );

        coords = coordinatesExtractor.extract(areaCenter[0]);
        System.out.println(coords[0]+", "+coords[1]);
        Coordinate center = new Coordinate(coords[0], coords[1]);
        Coordinate tmp;
        DistanceCalculator distanceCalculator = new DistanceCalculator();

        JSONArray locations = downloader.download();
        for (int i = 0; i < locations.length(); i++) {
            double x = locations.getJSONObject(i).getDouble("x");
            double y = locations.getJSONObject(i).getDouble("y");
            System.out.println(x+", "+y);

            tmp = new Coordinate(x,y);
            double distance = distanceCalculator.calculateDistance(center, tmp);
            System.out.println(distance+"/"+radius);
            if (distance <= Double.parseDouble(radius)) {
                found = true;
                break;
            }
        }

        if (found)
            Platform.runLater(() -> AlertManager.throwInformation("Selected vehicle is in the designated area."));
        else
            Platform.runLater(() -> AlertManager.throwInformation("Vehicle not found in the designated area."));
    }

    @Override
    public void run() {
        while (flag == Flag.RUNNING) {
            try {
                scan();
                stop();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        flag = Flag.STOP;
    }
}
