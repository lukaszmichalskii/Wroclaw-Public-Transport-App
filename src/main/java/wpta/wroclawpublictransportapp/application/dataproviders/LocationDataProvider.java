package wpta.wroclawpublictransportapp.application.dataproviders;

import com.teamdev.jxbrowser.browser.Browser;
import wpta.wroclawpublictransportapp.application.alert.AlertManager;
import wpta.wroclawpublictransportapp.application.alert.EmptyRequestException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class LocationDataProvider {

    private final APISettings apiSettings;
    private final URLRequestEncoder urlRequestEncoder;
    private final Browser browser;

    public LocationDataProvider(Browser browser) {
        apiSettings = new APISettings();
        urlRequestEncoder = new URLRequestEncoder(apiSettings.getURLPrefixForBuses(), apiSettings.getURLPrefixForTrams());
        this.browser = browser;
    }

    public void sendRequest(Map<String, List<String>> parameters) {
        try {
            String URLParameters = urlRequestEncoder.encodeURL(parameters);
            URL url = apiSettings.getApiURLAddress();
            Thread thread = new Thread(new Downloader(url, URLParameters, browser));
            thread.start();
        } catch (MalformedURLException e) {
            AlertManager.throwError("Invalid URL. The URL may have changed, the services have been informed");
        } catch (EmptyRequestException e) {
            AlertManager.throwError("Please select desired transport.");
        } catch (Exception e) {
            AlertManager.throwError("Something went wrong. Check your selections.");
            e.printStackTrace();
        }
    }
}
