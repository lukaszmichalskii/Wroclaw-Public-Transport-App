package wpta.wroclawpublictransportapp.controller.helpers.initialization;

import javafx.scene.control.ComboBox;

import java.util.ArrayList;
import java.util.List;

public class TransportTypeChoiceInitialization implements ComboBoxInitializer {
    @Override
    public void init(ComboBox<String> options) {
        List<String> transportTypes = new ArrayList<>(List.of("Bus", "Tram"));
        options.getItems().addAll(transportTypes);
        options.getSelectionModel().select(null);
    }
}
