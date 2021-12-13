package wpta.wroclawpublictransportapp.application.dataproviders;

import wpta.wroclawpublictransportapp.application.dataorganizers.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class Downloader {

    private final URL url;
    private final String URLParameters;
    private final JSONParser jsonParser;

    public Downloader(URL url, String URLParameters) {
        this.url = url;
        this.URLParameters = URLParameters;
        jsonParser = new JSONParser();
    }

    public void download() throws IOException {
        ConnectionProvider connectionProvider = new ConnectionProvider(url);
        URLConnection connection = connectionProvider.openConnection();
        connection.setDoOutput(true);

        OutputStreamWriter osWriter = new OutputStreamWriter(connection.getOutputStream());

        osWriter.write(URLParameters);
        osWriter.flush();

        String response;
        BufferedReader bfReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        while ((response = bfReader.readLine()) != null) {
            System.out.println(jsonParser.parseJSON(response));
        }
        osWriter.close();
        bfReader.close();
    }
}
