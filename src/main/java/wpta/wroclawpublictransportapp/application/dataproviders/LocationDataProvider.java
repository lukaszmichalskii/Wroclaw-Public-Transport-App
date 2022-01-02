package wpta.wroclawpublictransportapp.application.dataproviders;

import wpta.wroclawpublictransportapp.application.alert.AlertManager;
import wpta.wroclawpublictransportapp.application.alert.exceptions.EmptyRequestException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Class responsible for delivering selected vehicle positions
 */
public class LocationDataProvider {

    private final APISettings apiSettings;
    private final URLRequestEncoder urlRequestEncoder;
    private static DownloadThread downloadThread = null;

    public LocationDataProvider() {
        apiSettings = new APISettings();
        urlRequestEncoder = new URLRequestEncoder(apiSettings.getURLPrefixForBuses(), apiSettings.getURLPrefixForTrams());
    }

    public void sendRequest(Map<String, List<String>> parameters, Integer time) {
        try {
            if (downloadThread != null)
                stopProcess();
            String URLParameters = urlRequestEncoder.encodeURL(parameters);
            URL url = apiSettings.getApiURLAddress();
            downloadThread = new DownloadThread(url, URLParameters, time);
        } catch (MalformedURLException e) {
            AlertManager.throwError("Invalid URL. The URL may have changed, the services have been informed");
        } catch (EmptyRequestException e) {
            AlertManager.throwError("Please select desired transport.");
        } catch (Exception e) {
            AlertManager.throwError("Something went wrong. Check your selections.");
            e.printStackTrace();
        }
    }

    public static void stopProcess() {
        if (downloadThread != null)
            downloadThread.stop();
    }
}
