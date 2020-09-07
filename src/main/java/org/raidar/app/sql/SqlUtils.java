package org.raidar.app.sql;

import org.raidar.app.sql.builder.SqlBuilder;
import org.raidar.app.sql.builder.SqlExpression;

import java.time.LocalDateTime;

import static org.raidar.app.sql.SqlConstants.*;

public class SqlUtils {

    private SqlUtils() {
        // Nothing to do.
    }

    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }

    public static boolean isBlank(String s) {
        return s == null || s.trim().length() == 0;
    }

    public static String enclose(String sql) {
        return "(" + sql + ")";
    }

    public static String escapeName(String name) {

        return isEmpty(name) ? name : "\"" + name.replace("\"", "") + "\"";
    }

    public static String escapeValue(String value) {

        return isEmpty(value) ? value : "'" + value.replace("'", "''") + "'";
    }

    public static boolean isEmpty(SqlBuilder builder) {
        return builder == null || builder.isEmpty();
    }

    public static boolean isEmpty(SqlExpression expression) {
        return expression == null || expression.isEmpty();
    }

    public static String formatDateTime(LocalDateTime value) {
        return (value != null) ? value.format(DATETIME_FORMATTER) : null;
    }

    public static String toTimestamp(String value) {

        // Учесть другие константы: now, today etc.
        switch(value) {
            case MIN_TIMESTAMP_VALUE:
            case MAX_TIMESTAMP_VALUE:
                return value;

            default:
                return String.format(TO_TIMESTAMP_FORMAT, escapeValue(value), escapeValue(SQL_TIMESTAMP_FORMAT));
        }
    }

    public static String toTimestampWithTimeZone(String value) {

        return (value != null) ? toTimestamp(value) + CAST_OPERATOR + TIMESTAMP_WITH_TIME_ZONE : null;
    }

    public static String toTimestampWithoutTimeZone(String value) {

        return (value != null) ? toTimestamp(value) + CAST_OPERATOR + TIMESTAMP_WITHOUT_TIME_ZONE : null;
    }
}
