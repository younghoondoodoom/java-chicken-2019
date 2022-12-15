package domain.pos.validation.util;

import java.util.Arrays;
import java.util.List;

public class ValidateUtil {

    public static boolean isInRange(final int target, final int startInclusive, final int endInclusive) {
        return target >= startInclusive && target <= endInclusive;
    }

    public static boolean isExpectedNumber(final int target, final int... expectedNumbers) {
        return Arrays.stream(expectedNumbers).anyMatch(expectedNumber -> target == expectedNumber);
    }
}
