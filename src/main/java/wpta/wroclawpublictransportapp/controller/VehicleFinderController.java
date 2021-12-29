package wpta.wroclawpublictransportapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import wpta.wroclawpublictransportapp.application.alert.AlertManager;
import wpta.wroclawpublictransportapp.controller.helpers.initialization.ComboBoxInitializer;
import wpta.wroclawpublictransportapp.controller.helpers.initialization.LineNumberInitialization;
import wpta.wroclawpublictransportapp.controller.helpers.initialization.TransportTypeChoiceInitialization;

import java.net.URL;
import java.util.ResourceBundle;

public class VehicleFinderController implements Initializable {

    @FXML
    private TextField addressTextField;

    @FXML
    private Button cancelButton;

    @FXML
    private ComboBox<String> lineNumberChoice;

    @FXML
    private TextField radiusTextField;

    @FXML
    private ComboBox<String> transportTypeChoice;

    @FXML
    void cancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void scan() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTransportTypeOptions();
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

    /**
     * Init options of possible transport types
     */
    private void initTransportTypeOptions() {
        ComboBoxInitializer initializer = new TransportTypeChoiceInitialization();
        initializer.init(transportTypeChoice);
    }
}
