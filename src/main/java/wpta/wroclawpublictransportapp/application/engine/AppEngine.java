package wpta.wroclawpublictransportapp.application.engine;

import com.teamdev.jxbrowser.engine.Engine;
import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;

public class AppEngine {
    private static Engine engine;

    public static Engine getEngine() {
        if (engine == null) {
            engine = initAppEngine();
        }
        return engine;
    }

    private static Engine initAppEngine() {
        engine = Engine.newInstance(HARDWARE_ACCELERATED);
        return engine;
    }
}
