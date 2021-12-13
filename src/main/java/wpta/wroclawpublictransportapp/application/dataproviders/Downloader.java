package wpta.wroclawpublictransportapp.application.dataproviders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class Downloader {

    private final URL url;
    private final String URLParameters;

    public Downloader(URL url, String URLParameters) {
        this.url = url;
        this.URLParameters = URLParameters;
    }

    public void download() throws IOException {
        ConnectionProvider connectionProvider = new ConnectionProvider(url);
        URLConnection connection = connectionProvider.openConnection();
        connection.setDoOutput(true);

        OutputStreamWriter osWriter = new OutputStreamWriter(connection.getOutputStream());

        osWriter.write(URLParameters);
        osWriter.flush();

        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        osWriter.close();
        reader.close();
    }
}
