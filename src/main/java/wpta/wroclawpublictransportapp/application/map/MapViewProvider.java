package wpta.wroclawpublictransportapp.application.map;

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.view.javafx.BrowserView;
import wpta.wroclawpublictransportapp.application.engine.AppEngine;

import java.io.File;

/**
 * Class responsible for provide mapView from Google Map sample map.
 */
public class MapViewProvider {
    private final String HTMLMapViewFilepath;
    private static Browser browser;

    public MapViewProvider() {
        browser = AppEngine.getEngine().newBrowser();
        this.HTMLMapViewFilepath = "D:\\PWr\\Jezyki programowania\\laboratorium\\WroclawPublicTransportApp\\map\\map.html";
    }

    public static Browser getBrowser() {
        return browser;
    }

    public BrowserView provideMap() {
        return loadMapView();
    }

    private BrowserView loadMapView() {
        File mapViewFile = getMapViewFile(HTMLMapViewFilepath);
        String mapViewAbsolutePath = getMapViewAbsolutePath(mapViewFile);
        browser.navigation().loadUrl(mapViewAbsolutePath);
        return BrowserView.newInstance(browser);
    }

    private String getMapViewAbsolutePath(File mapView) {
        return mapView.getAbsolutePath();
    }

    private File getMapViewFile(String HTMLMapViewFilepath) {
        return new File(HTMLMapViewFilepath);
    }
}
