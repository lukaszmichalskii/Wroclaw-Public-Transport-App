package wpta.wroclawpublictransportapp.controller.helpers.initialization;

import javafx.scene.control.ComboBox;

import java.util.ArrayList;
import java.util.List;

public class TramLinesInitializer implements ComboBoxInitializer {

    private List<String> tramLines;

    public TramLinesInitializer() {
        configureTramLines();
    }
    @Override
    public void init(ComboBox<String> options) {
        options.getItems().clear();
        options.getItems().addAll(tramLines);
    }

    private void configureTramLines() {
        tramLines = new ArrayList<>();
        // lines 1- 20
        for (int i = 1; i <= 23; i++) {
            tramLines.add(String.valueOf(i));
        }
        // remove non-existing lines
        tramLines.removeAll(List.of("12", "13", "14", "18", "19", "21", "22"));

        // other lines
        tramLines.addAll(List.of("31", "33", "70", "74"));
    }
}
