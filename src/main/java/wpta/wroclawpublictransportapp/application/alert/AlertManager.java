package wpta.wroclawpublictransportapp.application.alert;

import javafx.scene.control.Alert;

public interface AlertManager {
    static void throwError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);
    }
}
