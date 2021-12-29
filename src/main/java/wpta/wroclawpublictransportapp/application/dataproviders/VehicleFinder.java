package wpta.wroclawpublictransportapp.application.dataproviders;

import wpta.wroclawpublictransportapp.application.alert.AlertManager;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Class responsible for finding a vehicle in the designated area
 */
public class VehicleFinder {
    private final APISettings apiSettings;
    private final URLRequestEncoder urlRequestEncoder;
    private AreaScannerThread areaScannerThread = null;

    public VehicleFinder() {
        apiSettings = new APISettings();
        urlRequestEncoder = new URLRequestEncoder(apiSettings.getURLPrefixForBuses(), apiSettings.getURLPrefixForTrams());
    }

    public void scan(String transportType, String lineNumber, String radius) {
        try {
            if (areaScannerThread != null)
                areaScannerThread.stop();
            String URLParameters = urlRequestEncoder.encodeURL(transportType, lineNumber);
            URL url = apiSettings.getApiURLAddress();
            areaScannerThread = new AreaScannerThread(url, URLParameters, radius);
        } catch (MalformedURLException e) {
            AlertManager.throwError("Invalid URL. The URL may have changed, the services have been informed.");
        } catch (Exception e) {
            AlertManager.throwError("Something went wrong. Check your selections.");
        }
    }
}
