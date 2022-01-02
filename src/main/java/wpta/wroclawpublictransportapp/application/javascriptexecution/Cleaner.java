package wpta.wroclawpublictransportapp.application.javascriptexecution;

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.dom.Document;
import com.teamdev.jxbrowser.dom.Element;
import com.teamdev.jxbrowser.frame.Frame;

public class Cleaner extends JavaScriptExecutor {

    public Cleaner(Browser browser) {
        super(browser);
    }

    @Override
    public void execute() {
        clean();
    }

    private void clean() {
        Document document = browser.mainFrame().flatMap(Frame::document).get();
        Element deleteElement = document.findElementById("deleteCircle").get();

        deleteElement.click();
    }
}
