package wpta.wroclawpublictransportapp.application.dataproviders;

import wpta.wroclawpublictransportapp.application.alert.AlertManager;
import wpta.wroclawpublictransportapp.application.alert.EmptyRequestException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class LocationDataProvider {

    private final APISettings apiSettings;
    private final URLRequestEncoder urlRequestEncoder;

    public LocationDataProvider() {
        apiSettings = new APISettings();
        urlRequestEncoder = new URLRequestEncoder(apiSettings.getURLPrefixForBuses(), apiSettings.getURLPrefixForTrams());
    }

    public void sendRequest(Map<String, List<String>> parameters) {
        try {
            String URLParameters = urlRequestEncoder.encodeURL(parameters);
            URL url = apiSettings.getApiURLAddress();
            Downloader downloader = new Downloader(url, URLParameters);
            downloader.download();
        } catch (MalformedURLException e) {
            AlertManager.throwError("Invalid URL. The URL may have changed, the services have been informed");
        } catch (IOException e) {
            AlertManager.throwError("Error while downloading data from API. Try again.");
        } catch (EmptyRequestException e) {
            AlertManager.throwError("Please select desired transport.");
        } catch (Exception e) {
            AlertManager.throwError("Something went wrong. Check your selections.");
            e.printStackTrace();
        }
    }
}
