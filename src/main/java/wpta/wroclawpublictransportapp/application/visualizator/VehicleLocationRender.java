package wpta.wroclawpublictransportapp.application.visualizator;

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.dom.Document;
import com.teamdev.jxbrowser.dom.Element;
import com.teamdev.jxbrowser.dom.InputElement;
import com.teamdev.jxbrowser.frame.Frame;

/**
 * Class execute JavaScript code to render objects on map view
 */
public class VehicleLocationRender {

    private final Browser browser;

    public VehicleLocationRender(Browser browser) {
        this.browser = browser;
    }

    public void visualize(String geoJSON) {
        Document document = browser.mainFrame().flatMap(Frame::document).get();
        Element searchElement = document.findElementById("render").get();

        document.findElementById("geoJSON").ifPresent(address ->
                ((InputElement) address).value(geoJSON));

        searchElement.click();
    }
}
