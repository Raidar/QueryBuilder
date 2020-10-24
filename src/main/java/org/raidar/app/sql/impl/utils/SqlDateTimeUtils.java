package org.raidar.app.sql.impl.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.raidar.app.sql.impl.constant.SqlDateTimeConstants.MAX_DATETIME_VALUE;
import static org.raidar.app.sql.impl.constant.SqlDateTimeConstants.MIN_DATETIME_VALUE;
import static org.raidar.app.sql.impl.constant.SqlConstants.*;
import static org.raidar.app.sql.impl.utils.SqlUtils.escapeValue;

public class SqlDateTimeUtils {

    private SqlDateTimeUtils() {
        // Nothing to do.
    }

    public static String formatDate(LocalDate value, DateTimeFormatter formatter) {

        return (value != null) ? value.format(formatter) : null;
    }

    public static String formatDateTime(LocalDateTime value, DateTimeFormatter formatter) {

        return (value != null) ? value.format(formatter) : null;
    }

    public static String toDate(String value, String format) {

        return toDateTime(TO_DATE_FORMAT, value, format);
    }

    public static String toTimestamp(String value, String format) {

        return toDateTime(TO_TIMESTAMP_FORMAT, value, format);
    }

    public static String toTimestampWithTimeZone(String value, String format) {

        return (value != null) ? toTimestamp(value, format) + CAST_OPERATOR + TIMESTAMP_WITH_TIME_ZONE : null;
    }

    public static String toTimestampWithoutTimeZone(String value, String format) {

        return (value != null) ? toTimestamp(value, format) + CAST_OPERATOR + TIMESTAMP_WITHOUT_TIME_ZONE : null;
    }

    private static String toDateTime(String format, String value, String valueFormat) {

        if (value == null)
            return NULL_VALUE;

        // Учесть другие константы: now, today etc.
        switch(value) {
            case MIN_DATETIME_VALUE:
            case MAX_DATETIME_VALUE:
                return escapeValue(value);

            default:
                return String.format(format, escapeValue(value), escapeValue(valueFormat));
        }
    }
}
