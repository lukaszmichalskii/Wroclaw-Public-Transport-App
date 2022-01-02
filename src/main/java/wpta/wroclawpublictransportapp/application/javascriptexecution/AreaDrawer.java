package wpta.wroclawpublictransportapp.application.javascriptexecution;

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.dom.Document;
import com.teamdev.jxbrowser.dom.Element;
import com.teamdev.jxbrowser.dom.InputElement;
import com.teamdev.jxbrowser.frame.Frame;

/**
 * Class responsible for setting radius length
 */
public class AreaDrawer extends JavaScriptExecutor {
    private final Double radius;

    public AreaDrawer(Browser browser, Double radius) {
        super(browser);
        this.radius = radius;
    }

    @Override
    public void execute() {
        draw();
    }

    private void draw() {
        Document document = browser.mainFrame().flatMap(Frame::document).get();
        Element searchElement = document.findElementById("search").get();

        document.findElementById("radius").ifPresent(address ->
                ((InputElement) address).value(String.valueOf(radius)));

        searchElement.click();
    }
}
