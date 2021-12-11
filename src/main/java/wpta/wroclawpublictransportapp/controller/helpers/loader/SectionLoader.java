package wpta.wroclawpublictransportapp.controller.helpers.loader;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import wpta.wroclawpublictransportapp.Main;

import java.io.IOException;

public class SectionLoader {
    public static Object load(String GUIFormFilepath, BorderPane borderPane, BorderPaneLocation location) {
        Parent root = null;
        FXMLLoader fxmlLoader = null;
        try {
            fxmlLoader = new FXMLLoader(Main.class.getResource(GUIFormFilepath));
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (location == BorderPaneLocation.CENTER)
            borderPane.setCenter(root);
        else if (location == BorderPaneLocation.BOTTOM)
            borderPane.setBottom(root);
        else if (location == BorderPaneLocation.LEFT)
            borderPane.setLeft(root);
        else if (location == BorderPaneLocation.RIGHT)
            borderPane.setRight(root);
        else if (location == BorderPaneLocation.TOP)
            borderPane.setTop(root);

        return fxmlLoader.getController();
    }
}
