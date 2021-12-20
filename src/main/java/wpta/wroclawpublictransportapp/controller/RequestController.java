package wpta.wroclawpublictransportapp.controller;

import com.jfoenix.controls.JFXCheckBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import wpta.wroclawpublictransportapp.application.dataproviders.LocationDataProvider;
import wpta.wroclawpublictransportapp.application.map.MapViewProvider;
import wpta.wroclawpublictransportapp.application.visualizator.AreaDrawer;
import wpta.wroclawpublictransportapp.controller.helpers.AppSettings;

import java.net.URL;
import java.util.*;

public class RequestController implements Initializable {

    // Available buses
    @FXML
    private JFXCheckBox A, C, D, K, N, B100, B101, B103, B104, B105, B106, B107, B108, B109, B110, B111, B112, B113,
            B114, B115, B116, B118, B119, B120, B121, B122, B124, B125, B126, B127, B128, B129, B130, B131, B132, B133,
            B134, B136, B140, B142, B143, B144, B145, B146, B147, B148, B149, B150, B151, B204, B206, B241, B242, B243,
            B244,B245, B246, B247, B248, B249, B250, B251, B253, B255, B257, B259, B315, B319, B602, B607, B703, B709, B731;

    // Available trams
    @FXML
    private JFXCheckBox T1, T10, T11, T15, T16, T17, T2, T20, T23, T3, T31, T33, T4, T5, T6, T7, T70, T74, T8, T9;

    private ObservableList<JFXCheckBox> trams;
    private ObservableList<JFXCheckBox> buses;
    private final Map<String, List<String>> parameters;
    private final LocationDataProvider locationDataProvider;
    private Integer refreshTime = AppSettings.getDefaultRefreshTime();

    public RequestController() {
        locationDataProvider = new LocationDataProvider();
        parameters = new HashMap<>();
        parameters.put("tram", new ArrayList<>());
        parameters.put("bus", new ArrayList<>());
    }

    @FXML
    private void sendRequest() {

        parameters.get("tram").clear();
        parameters.get("bus").clear();

        for (JFXCheckBox tram: trams) {
            if (tram.isSelected()) {
                parameters.get("tram").add(tram.getText());
            }
        }

        for (JFXCheckBox bus: buses) {
            if (bus.isSelected()) {
                parameters.get("bus").add(bus.getText());
            }
        }
//        AlertManager.throwConfirmation("Request has been sent with " + msg + " parameters\n" + parameters);
        locationDataProvider.sendRequest(parameters, refreshTime);
    }

    @FXML
    private void scan() {
        AreaDrawer areaDrawer = new AreaDrawer(MapViewProvider.getBrowser());
        areaDrawer.draw(1000.0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        trams = FXCollections.observableList(List.of(T1, T10, T11, T15, T16, T17, T2, T20, T23, T3, T31, T33, T4, T5, T6, T7, T70, T74, T8, T9));
        buses = FXCollections.observableList(List.of(A, C, D, K, N, B100, B101, B103, B104, B105, B106, B107, B108, B109, B110, B111, B112, B113,
                B114, B115, B116, B118, B119, B120, B121, B122, B124, B125, B126, B127, B128, B129, B130, B131, B132, B133,
                B134, B136, B140, B142, B143, B144, B145, B146, B147, B148, B149, B150, B151, B204, B206, B241, B242, B243,
                B244,B245, B246, B247, B248, B249, B250, B251, B253, B255, B257, B259, B315, B319, B602, B607, B703, B709, B731));
    }

    public void setRefreshTime(Integer time) {
        this.refreshTime = time;
    }
}
