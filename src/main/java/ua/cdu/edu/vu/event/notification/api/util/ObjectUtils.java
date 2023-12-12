package ua.cdu.edu.vu.event.notification.api.util;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.Objects;

@UtilityClass
public class ObjectUtils {

    public static boolean allNonNull(Object... args) {
        return Arrays.stream(args).allMatch(Objects::nonNull);
    }
}
