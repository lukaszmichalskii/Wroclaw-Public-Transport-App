package wpta.wroclawpublictransportapp.application.javascriptexecution;

import com.teamdev.jxbrowser.browser.Browser;

/**
 * Model of JavaScript Execution class
 */
public abstract class JavaScriptExecutor {
    protected final Browser browser;

    public JavaScriptExecutor(Browser browser) {
        this.browser = browser;
    }

    public abstract void execute();
}
