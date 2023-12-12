package ua.cdu.edu.vu.event.notification.api.util;

import lombok.experimental.UtilityClass;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@UtilityClass
public class DateTimeUtils {

    public static ZonedDateTime truncateToNextMinute(ZonedDateTime dateTime) {
        return dateTime.plusMinutes(1).truncatedTo(ChronoUnit.MINUTES);
    }
}
