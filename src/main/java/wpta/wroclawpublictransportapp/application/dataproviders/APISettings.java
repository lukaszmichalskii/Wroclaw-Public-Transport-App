package wpta.wroclawpublictransportapp.application.dataproviders;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Class stores encoded characters ready to use while creating URL parameters sending as a request to API
 */
public class APISettings {
    private final String apiURLAddress;
    private final String URLPrefixForBuses;
    private final String URLPrefixForTrams;

    public APISettings() {
        apiURLAddress = "https://mpk.wroc.pl/bus_position";
        URLPrefixForBuses = "busList%5Bbus%5D%5B%5D=";
        URLPrefixForTrams = "busList%5Btram%5D%5B%5D=";
    }

    public URL getApiURLAddress() throws MalformedURLException {
        return new URL(apiURLAddress);
    }

    public String getURLPrefixForBuses() {
        return URLPrefixForBuses;
    }

    public String getURLPrefixForTrams() {
        return URLPrefixForTrams;
    }
}
