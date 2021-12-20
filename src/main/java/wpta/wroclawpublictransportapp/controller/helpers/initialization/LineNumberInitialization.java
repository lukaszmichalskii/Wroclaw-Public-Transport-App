package wpta.wroclawpublictransportapp.controller.helpers.initialization;

import javafx.scene.control.ComboBox;

import java.util.Objects;

public class LineNumberInitialization implements ComboBoxInitializer {

    private ComboBoxInitializer linesInitializer;

    public LineNumberInitialization(String transportType) {
        if (Objects.equals(transportType, "Bus")) {
            linesInitializer = new BusLinesInitializer();
        } else if (Objects.equals(transportType, "Tram")) {
            linesInitializer = new TramLinesInitializer();
        }
    }

    @Override
    public void init(ComboBox<String> options) {
        linesInitializer.init(options);
    }
}
