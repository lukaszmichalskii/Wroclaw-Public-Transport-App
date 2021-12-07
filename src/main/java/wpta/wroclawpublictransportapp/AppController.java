package wpta.wroclawpublictransportapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class AppController {
    @FXML
    private WebView map;

    @FXML
    private Button quitButton;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button settingsButton;

    @FXML
    private void quit() {
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void search() {

    }

    @FXML
    private void settings() {

    }
}