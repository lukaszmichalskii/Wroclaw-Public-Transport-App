package wpta.wroclawpublictransportapp.controller;

import com.teamdev.jxbrowser.view.javafx.BrowserView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import wpta.wroclawpublictransportapp.application.alert.AlertManager;
import wpta.wroclawpublictransportapp.application.map.LocationSearch;
import wpta.wroclawpublictransportapp.application.map.MapViewProvider;
import wpta.wroclawpublictransportapp.controller.helpers.initialization.ComboBoxInitializer;
import wpta.wroclawpublictransportapp.controller.helpers.initialization.LineNumberInitialization;
import wpta.wroclawpublictransportapp.controller.helpers.initialization.TransportTypeChoiceInitialization;

import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    @FXML
    private BorderPane appInterface;

    @FXML
    private ComboBox<String> lineNumberChoice;

    @FXML
    private Button quitButton;

    @FXML
    private TextField searchTextField;

    @FXML
    private ComboBox<String> transportTypeChoice;

    private final MapViewProvider mapViewProvider;
    private final LocationSearch locationSearch;

    public AppController() {
        mapViewProvider = new MapViewProvider();
        locationSearch = new LocationSearch(mapViewProvider.getBrowser());
    }

    @FXML
    private void quit() {
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void search() {
        locationSearch.search(searchTextField.getText());
    }

    @FXML
    private void settings() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initMap();
        initTransportTypeOptions();
    }

    /**
     * Method responsible for set map view
     */
    private void initMap() {
        BrowserView mapView = mapViewProvider.provideMap();
        appInterface.setCenter(mapView);
    }

    /**
     * Init options of possible transport types
     */
    private void initTransportTypeOptions() {
        ComboBoxInitializer initializer = new TransportTypeChoiceInitialization();
        initializer.init(transportTypeChoice);
    }

    /**
     * Handling problems with managing content of line options based on transport choice
     */
    @FXML
    private void transportTypeChoiceOnActionPerformed() {
        try {
            if (transportTypeChoice.getValue().equals("Bus")) {
                initLineNumberOptions(transportTypeChoice.getValue());
            } else if (transportTypeChoice.getValue().equals("Tram")) {
                initLineNumberOptions(transportTypeChoice.getValue());
            }
        } catch (Exception e) {
            AlertManager.throwError("Error while choosing transport and line");
        }
    }

    private void initLineNumberOptions(String transportChoice) {
        ComboBoxInitializer initializer = new LineNumberInitialization(transportChoice);
        initializer.init(lineNumberChoice);
    }

}