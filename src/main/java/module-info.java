module wpta.wroclawpublictransportapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.json;
    requires com.jfoenix;
    requires jxbrowser;
    requires jxbrowser.javafx;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens wpta.wroclawpublictransportapp to javafx.fxml;
    exports wpta.wroclawpublictransportapp;
    exports wpta.wroclawpublictransportapp.controller;
    opens wpta.wroclawpublictransportapp.controller to javafx.fxml;
}