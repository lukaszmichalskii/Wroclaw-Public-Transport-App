package wpta.wroclawpublictransportapp.controller;

import com.teamdev.jxbrowser.view.javafx.BrowserView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import wpta.wroclawpublictransportapp.application.mapviewprovider.MapViewProvider;

import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    @FXML
    private BorderPane appInterface;

    @FXML
    private Button quitButton;

    @FXML
    private void quit() {
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void search() {

    }

    @FXML
    private void settings() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initMap();
    }

    private void initMap() {
        MapViewProvider mapViewProvider = new MapViewProvider();
        BrowserView mapView = mapViewProvider.provideMap();
        appInterface.setCenter(mapView);
    }
}