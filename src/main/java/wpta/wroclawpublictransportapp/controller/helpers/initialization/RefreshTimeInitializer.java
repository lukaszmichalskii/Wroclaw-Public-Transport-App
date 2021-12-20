package wpta.wroclawpublictransportapp.controller.helpers.initialization;

import javafx.scene.control.ComboBox;

import java.util.ArrayList;
import java.util.List;

public class RefreshTimeInitializer implements ComboBoxInitializer {
    private List<String> refreshOptions;

    public RefreshTimeInitializer() {
        configureRefreshTimeOptions();
    }

    @Override
    public void init(ComboBox<String> options) {
        options.getItems().addAll(refreshOptions);
    }

    private void configureRefreshTimeOptions() {
        refreshOptions = new ArrayList<>(List.of("10 seconds", "15 seconds", "30 seconds", "1 minute", "2 minutes", "5 minutes"));
    }
}
