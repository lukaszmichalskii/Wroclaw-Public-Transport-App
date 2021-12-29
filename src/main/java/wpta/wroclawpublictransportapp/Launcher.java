package wpta.wroclawpublictransportapp;

/**
 * Main class of App
 */
public class Launcher {
    public static void main(String[] args) {
        System.setProperty("jxbrowser.license.key", SensitiveDataStorage.getJxBrowserLicenseKey());
        Main.main(args);
        System.exit(0);
    }
}
