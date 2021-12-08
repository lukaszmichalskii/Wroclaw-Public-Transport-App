package wpta.wroclawpublictransportapp.application.map;

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.dom.Document;
import com.teamdev.jxbrowser.dom.Element;
import com.teamdev.jxbrowser.dom.InputElement;
import com.teamdev.jxbrowser.frame.Frame;
import wpta.wroclawpublictransportapp.application.alert.AlertManager;

public class LocationSearch {

    private final Browser browser;

    public LocationSearch(Browser browser) {
        this.browser = browser;
    }
    public void search(String location) {
        if (!location.isEmpty()) {
            Document document = browser.mainFrame().flatMap(Frame::document).get();
            Element searchElement = document.findElementById("submit").get();

            document.findElementById("address").ifPresent(address ->
                    ((InputElement) address).value(location));

            searchElement.click();
        } else
            AlertManager.throwError("Empty location, please provide desired address");

    }
}
