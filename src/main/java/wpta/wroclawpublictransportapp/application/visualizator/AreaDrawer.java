package wpta.wroclawpublictransportapp.application.visualizator;

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.dom.Document;
import com.teamdev.jxbrowser.dom.Element;
import com.teamdev.jxbrowser.dom.InputElement;
import com.teamdev.jxbrowser.frame.Frame;

public class AreaDrawer {
    private final Browser browser;

    public AreaDrawer(Browser browser) {
        this.browser = browser;
    }

    public void draw(Double radius) {
        Document document = browser.mainFrame().flatMap(Frame::document).get();
        Element searchElement = document.findElementById("search").get();

        document.findElementById("area").ifPresent(address ->
                ((InputElement) address).value(String.valueOf(radius)));

        searchElement.click();
    }
}
