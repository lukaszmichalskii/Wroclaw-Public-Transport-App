package wpta.wroclawpublictransportapp.controller.helpers;

public class AppSettings {
    private static final Integer DEFAULT_REFRESH_TIME = 30000; // 30 seconds

    public static Integer getDefaultRefreshTime() {
        return DEFAULT_REFRESH_TIME;
    }
}
