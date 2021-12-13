package wpta.wroclawpublictransportapp.application.dataproviders;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class ConnectionProvider {

    private final URL url ;

    public ConnectionProvider(URL apiURLAddress) {
        url = apiURLAddress;
    }

    public URLConnection openConnection() throws IOException {
        return url.openConnection();
    }
}
