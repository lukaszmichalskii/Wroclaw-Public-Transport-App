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
import wpta.wroclawpublictransportapp.application.javascriptexecution.AreaDrawer;
import wpta.wroclawpublictransportapp.application.javascriptexecution.Cleaner;
import wpta.wroclawpublictransportapp.application.javascriptexecution.JavaScriptExecutor;
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
    private void scan() {
        SectionLoader.load("gui/bus-finder-scanner.fxml", appInterface, BorderPaneLocation.LEFT);
    }

    @FXML
    private void refresh() {
        MapViewProvider.getBrowser().navigation().reload();
    }

    @FXML
    private void vehiclesLocationsMapView() {
        SectionLoader.load("gui/request-form.fxml", appInterface, BorderPaneLocation.LEFT);
        JavaScriptExecutor jsExecutor = new AreaDrawer(MapViewProvider.getBrowser(), null);
        JavaScriptExecutor cleaner = new Cleaner(MapViewProvider.getBrowser());
        jsExecutor.execute();
        cleaner.execute();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SectionLoader.load("gui/request-form.fxml", appInterface, BorderPaneLocation.LEFT);
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