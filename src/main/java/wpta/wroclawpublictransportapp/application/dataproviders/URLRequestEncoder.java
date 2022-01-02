package wpta.wroclawpublictransportapp.application.dataproviders;

import wpta.wroclawpublictransportapp.application.alert.exceptions.EmptyRequestException;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Class used for encoding and creating URL parameters sending as a request to API
 */
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

    public String encodeURL(String transportType, String lineNumber) throws EmptyRequestException {
        if (Objects.equals(transportType, "") || Objects.equals(lineNumber, ""))
            throw new EmptyRequestException("Empty transport selection");

        StringBuilder urlParameters = new StringBuilder();
        if (Objects.equals(transportType, "Bus")) {
            urlParameters.append(URLPrefixForBuses).append(lineNumber);
        } else if (Objects.equals(transportType, "Tram")) {
            urlParameters.append(URLPrefixForTrams).append(lineNumber);
        } else {
            return "";
        }
        return urlParameters.toString();
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
