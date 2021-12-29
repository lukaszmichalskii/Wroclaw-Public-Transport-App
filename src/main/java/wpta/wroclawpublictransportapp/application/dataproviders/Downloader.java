package wpta.wroclawpublictransportapp.application.dataproviders;

import org.json.JSONArray;
import wpta.wroclawpublictransportapp.application.dataorganizers.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * Class responsible for downloading data from MPK API.
 */
public class Downloader {

    private final URL url;
    private final String URLParameters;
    private final JSONParser jsonParser;

    public Downloader(URL url, String URLParameters, JSONParser jsonParser) {
        this.url = url;
        this.URLParameters = URLParameters;
        this.jsonParser = jsonParser;
    }

    public JSONArray download() throws IOException {
        ConnectionProvider connectionProvider = new ConnectionProvider(url);
        URLConnection connection = connectionProvider.openConnection();
        connection.setDoOutput(true);

        OutputStreamWriter osWriter = new OutputStreamWriter(connection.getOutputStream());
        osWriter.write(URLParameters);
        osWriter.flush();

        String response;
        BufferedReader bfReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        JSONArray locations = null;

        while ((response = bfReader.readLine()) != null) {
            locations = jsonParser.parseJSON(response);
        }

        osWriter.close();
        bfReader.close();
        return locations;
    }
}
