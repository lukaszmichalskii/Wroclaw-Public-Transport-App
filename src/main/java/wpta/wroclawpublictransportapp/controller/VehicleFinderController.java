package wpta.wroclawpublictransportapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import wpta.wroclawpublictransportapp.application.alert.AlertManager;
import wpta.wroclawpublictransportapp.application.dataproviders.VehicleFinder;
import wpta.wroclawpublictransportapp.application.map.MapViewProvider;
import wpta.wroclawpublictransportapp.application.validation.GUIFormValidation;
import wpta.wroclawpublictransportapp.application.javascriptexecution.AreaDrawer;
import wpta.wroclawpublictransportapp.application.javascriptexecution.JavaScriptExecutor;
import wpta.wroclawpublictransportapp.controller.helpers.initialization.ComboBoxInitializer;
import wpta.wroclawpublictransportapp.controller.helpers.initialization.LineNumberInitialization;
import wpta.wroclawpublictransportapp.controller.helpers.initialization.TransportTypeChoiceInitialization;

import java.net.URL;
import java.util.ResourceBundle;

public class VehicleFinderController implements Initializable {

    @FXML
    private ComboBox<String> lineNumberChoice;

    @FXML
    private TextField radiusTextField;

    @FXML
    private ComboBox<String> transportTypeChoice;

    private final VehicleFinder vehicleFinder;

    public VehicleFinderController() {
        vehicleFinder = new VehicleFinder();
    }

    @FXML
    private void scan() {
        GUIFormValidation response = validateRadius(radiusTextField.getText());
        if (response == GUIFormValidation.EMPTY)
            AlertManager.throwError("Radius not provided");
        else if (response == GUIFormValidation.CORRECT)
            vehicleFinder.scan(transportTypeChoice.getValue(), lineNumberChoice.getValue(), radiusTextField.getText());
        else
            AlertManager.throwError("Invalid radius format.");
    }

    @FXML
    private void apply() {
        GUIFormValidation response = validateRadius(radiusTextField.getText());
        if (response == GUIFormValidation.EMPTY)
            AlertManager.throwError("Radius not provided");
        else if (response == GUIFormValidation.CORRECT) {
            JavaScriptExecutor jsExecutor = new AreaDrawer(MapViewProvider.getBrowser(), Double.valueOf(radiusTextField.getText()));
            jsExecutor.execute();
        } else
            AlertManager.throwError("Invalid radius format.");
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

    private GUIFormValidation validateRadius(String radius) {
        if (radius.isEmpty())
            return GUIFormValidation.EMPTY;

        try {
            Double.parseDouble(radius);
            return GUIFormValidation.CORRECT;
        } catch (Exception e) {
            return GUIFormValidation.INVALID;
        }
    }
}
