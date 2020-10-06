package org.raidar.app.sql.impl.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.raidar.app.sql.impl.constant.DateTimeConstants.MAX_TIMESTAMP_VALUE;
import static org.raidar.app.sql.impl.constant.DateTimeConstants.MIN_TIMESTAMP_VALUE;
import static org.raidar.app.sql.impl.constant.SqlConstants.*;
import static org.raidar.app.sql.impl.utils.SqlUtils.escapeValue;

public class DateTimeUtils {

    private DateTimeUtils() {
        // Nothing to do.
    }

    public static String formatDateTime(LocalDateTime value, DateTimeFormatter formatter) {

        return (value != null) ? value.format(formatter) : null;
    }

    public static String toTimestamp(String value, String format) {

        // Учесть другие константы: now, today etc.
        switch(value) {
            case MIN_TIMESTAMP_VALUE:
            case MAX_TIMESTAMP_VALUE:
                return value;

            default:
                return String.format(TO_TIMESTAMP_FORMAT, escapeValue(value), escapeValue(format));
        }
    }

    public static String toTimestampWithTimeZone(String value, String format) {

        return (value != null) ? toTimestamp(value, format) + CAST_OPERATOR + TIMESTAMP_WITH_TIME_ZONE : null;
    }

    public static String toTimestampWithoutTimeZone(String value, String format) {

        return (value != null) ? toTimestamp(value, format) + CAST_OPERATOR + TIMESTAMP_WITHOUT_TIME_ZONE : null;
    }
}
