package wpta.wroclawpublictransportapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import wpta.wroclawpublictransportapp.application.time.TimeParser;
import wpta.wroclawpublictransportapp.controller.helpers.initialization.ComboBoxInitializer;
import wpta.wroclawpublictransportapp.controller.helpers.initialization.RefreshTimeInitializer;
import wpta.wroclawpublictransportapp.controller.helpers.loader.BorderPaneLocation;
import wpta.wroclawpublictransportapp.controller.helpers.loader.SectionLoader;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    @FXML
    private ComboBox<String> refreshOption;

    private final TimeParser timeParser;
    private BorderPane appInterface;

    public SettingsController() {
        timeParser = new TimeParser();
    }

    @FXML
    private void apply() {
        String pickedTime = refreshOption.getValue();
        Integer time = timeParser.parseTime(pickedTime);
        RequestController requestController = (RequestController) SectionLoader.load("gui/request-form.fxml", appInterface, BorderPaneLocation.LEFT);
        requestController.setRefreshTime(time);
    }

    @FXML
    private void cancel() {
        SectionLoader.load("gui/request-form.fxml", appInterface, BorderPaneLocation.LEFT);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initRefreshOptions();
    }

    private void initRefreshOptions() {
        ComboBoxInitializer initializer = new RefreshTimeInitializer();
        initializer.init(refreshOption);
    }

    public void setParent(BorderPane appInterface) {
        this.appInterface = appInterface;
    }
}
