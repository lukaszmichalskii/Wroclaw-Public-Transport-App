package wpta.wroclawpublictransportapp.application.javascriptexecution;

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.dom.Document;
import com.teamdev.jxbrowser.dom.Element;
import com.teamdev.jxbrowser.dom.InputElement;
import com.teamdev.jxbrowser.frame.Frame;

/**
 * Class execute JavaScript code to render objects on map view
 */
public class VehicleLocationRender extends JavaScriptExecutor {

    private final String geoJSON;

    public VehicleLocationRender(Browser browser, String geoJSON) {
        super(browser);
        this.geoJSON = geoJSON;
    }

    @Override
    public void execute() {
        visualize();
    }

    private void visualize() {
        Document document = browser.mainFrame().flatMap(Frame::document).get();
        Element searchElement = document.findElementById("render").get();

        document.findElementById("geoJSON").ifPresent(address ->
                ((InputElement) address).value(geoJSON));

        searchElement.click();
    }
}
