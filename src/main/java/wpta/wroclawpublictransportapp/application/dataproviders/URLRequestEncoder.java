package wpta.wroclawpublictransportapp.application.dataproviders;

import wpta.wroclawpublictransportapp.application.alert.EmptyRequestException;

import java.util.List;
import java.util.Map;

public class URLRequestEncoder {

    private final String URLPrefixForBuses;
    private final String URLPrefixForTrams;

    public URLRequestEncoder(String URLPrefixForBuses, String URLPrefixForTrams) {
        this.URLPrefixForBuses = URLPrefixForBuses;
        this.URLPrefixForTrams = URLPrefixForTrams;
    }

    public String encodeURL(Map<String , List<String>> parameters) throws EmptyRequestException {
        String busesURLRequest = encodeBusURLRequest(parameters.get("bus"));
        String tramsURLRequest = encodeTramURLRequest(parameters.get("tram"));

        if (busesURLRequest.equals("") && tramsURLRequest.equals(""))
            throw new EmptyRequestException("Empty transport selection");

        return createURLRequestParameters(busesURLRequest, tramsURLRequest);
    }

    private String encodeTramURLRequest(List<String> trams) {
        StringBuilder urlTramParameters = new StringBuilder();
        if (trams.size() > 0) {
            for (String tramLine: trams) {
                urlTramParameters.append(URLPrefixForTrams).append(tramLine).append("&");
            }
        } else
            return "";

        return urlTramParameters.toString();
    }

    private String encodeBusURLRequest(List<String> buses) {
        StringBuilder urlBusParameters = new StringBuilder();
        if (buses.size() > 0) {
            for (String busLine: buses) {
                urlBusParameters.append(URLPrefixForBuses).append(busLine).append("&");
            }
        } else
            return "";
        return urlBusParameters.toString();
    }

    private String createURLRequestParameters(String busesURLRequest, String tramsURLRequest) {
        StringBuilder URLParameters = new StringBuilder(busesURLRequest + tramsURLRequest);
        URLParameters.deleteCharAt(URLParameters.length()-1);
        return URLParameters.toString();
    }
}
