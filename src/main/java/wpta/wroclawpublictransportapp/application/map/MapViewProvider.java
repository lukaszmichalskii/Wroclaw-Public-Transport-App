package wpta.wroclawpublictransportapp.application.map;

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.view.javafx.BrowserView;
import wpta.wroclawpublictransportapp.Main;
import wpta.wroclawpublictransportapp.application.engine.AppEngine;

import java.util.Objects;

/**
 * Class responsible for provide mapView from Google Map sample map.
 */
public class MapViewProvider {
    private final String HTMLMapViewFilepath;
    private static Browser browser;

    public MapViewProvider() {
        browser = AppEngine.getEngine().newBrowser();
        this.HTMLMapViewFilepath = "map/map.html";
    }

    public static Browser getBrowser() {
        return browser;
    }

    public BrowserView provideMap() {
        return loadMapView();
    }

    private BrowserView loadMapView() {
        browser.navigation().loadUrl(Objects.requireNonNull(Main.class.getResource(HTMLMapViewFilepath)).toExternalForm());
        return BrowserView.newInstance(browser);
    }
}
