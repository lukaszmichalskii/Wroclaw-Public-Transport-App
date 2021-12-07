package wpta.wroclawpublictransportapp.application.mapviewprovider;

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.view.javafx.BrowserView;
import wpta.wroclawpublictransportapp.application.engine.AppEngine;

import java.io.File;

public class MapViewProvider {
    private final String HTMLMapViewFilepath;
    private final Browser browser;

    public MapViewProvider() {
        this.browser = AppEngine.getEngine().newBrowser();
        this.HTMLMapViewFilepath = "D:\\PWr\\Jezyki programowania\\laboratorium\\WroclawPublicTransportApp\\src\\map\\map.html";
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
