package wpta.wroclawpublictransportapp.controller;

import com.teamdev.jxbrowser.view.javafx.BrowserView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import wpta.wroclawpublictransportapp.application.map.LocationSearch;
import wpta.wroclawpublictransportapp.application.map.MapViewProvider;
import wpta.wroclawpublictransportapp.controller.helpers.loader.BorderPaneLocation;
import wpta.wroclawpublictransportapp.controller.helpers.loader.SectionLoader;

import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    @FXML
    private BorderPane appInterface;

    @FXML
    private Button quitButton;

    @FXML
    private TextField searchTextField;

    private final MapViewProvider mapViewProvider;
    private final LocationSearch locationSearch;

    public AppController() {
        mapViewProvider = new MapViewProvider();
        locationSearch = new LocationSearch(MapViewProvider.getBrowser());
    }

    @FXML
    private void quit() {
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Search option for user (provide location in search text field)
     */
    @FXML
    private void search() {
        locationSearch.search(searchTextField.getText());
    }

    @FXML
    private void refresh() {
        MapViewProvider.getBrowser().navigation().reload();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SettingsController settingsController = (SettingsController) SectionLoader.load("gui/settings.fxml", appInterface, BorderPaneLocation.LEFT);
        settingsController.setParent(appInterface);
        initMap();
    }

    /**
     * Method responsible for set map view
     */
    private void initMap() {
        BrowserView mapView = mapViewProvider.provideMap();
        appInterface.setCenter(mapView);
    }
}