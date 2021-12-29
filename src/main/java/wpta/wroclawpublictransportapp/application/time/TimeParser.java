package wpta.wroclawpublictransportapp.application.time;

import java.util.Objects;

/**
 * Class responsible for transforming picked time to proper time unit (in milliseconds)
 */
public class TimeParser {
    private final Integer secondsUnit = 60;
    private final Integer milliSecondsToSecondsScalar = 1000;

    public Integer parseTime(String pickedTime) {
        Integer time = Integer.valueOf(pickedTime.split(" ")[0]);
        String timeFormat = pickedTime.split(" ")[1];
        if (Objects.equals(timeFormat, "minutes") || Objects.equals(timeFormat, "minute")) {
            return time * secondsUnit * milliSecondsToSecondsScalar;
        } else if (Objects.equals(timeFormat, "seconds")) {
            return time * milliSecondsToSecondsScalar;
        }
        return null;
    }
}
