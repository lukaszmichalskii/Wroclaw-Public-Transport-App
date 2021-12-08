package wpta.wroclawpublictransportapp.application.alert;

import javafx.scene.control.Alert;

public interface AlertManager {
    static void throwError(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Something went wrong");
        alert.setContentText(msg);
        alert.show();
    }
}
