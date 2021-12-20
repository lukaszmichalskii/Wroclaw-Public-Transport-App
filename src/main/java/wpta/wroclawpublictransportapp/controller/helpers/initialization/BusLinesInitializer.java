package wpta.wroclawpublictransportapp.controller.helpers.initialization;

import javafx.scene.control.ComboBox;

import java.util.ArrayList;
import java.util.List;

public class BusLinesInitializer implements ComboBoxInitializer {

    private List<String> busLines;

    public BusLinesInitializer() {
        configureBusLines();
    }
    @Override
    public void init(ComboBox<String> options) {
        options.getItems().clear();
        options.getItems().addAll(busLines);
    }

    private void configureBusLines() {
        busLines = new ArrayList<>();
        // lines 100 - 151
        for (int i = 100; i <= 151; i++) {
            busLines.add(String.valueOf(i));
        }
        busLines.removeAll(List.of("117", "123", "135", "137", "138", "139", "141"));

        // line 206
        busLines.add("206");

        // lines 240 - 259
        for (int i = 240; i <= 259; i++) {
            busLines.add(String.valueOf(i));
        }
        busLines.removeAll(List.of("252", "254", "256", "258"));

        // other lines
        busLines.addAll(List.of("315", "319", "602", "607", "703", "709", "731"));
    }
}
