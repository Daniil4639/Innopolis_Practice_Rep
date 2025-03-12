package bus_app.utils;

import java.util.logging.Logger;

public class LoggerController {

    private LoggerController() {}

    private static final Logger LOGGER =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void makeRecord(String msg) {
        LOGGER.info(msg);
    }
}
