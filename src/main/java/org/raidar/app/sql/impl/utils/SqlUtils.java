package org.raidar.app.sql.impl.utils;

import org.raidar.app.sql.api.builder.SqlClause;
import org.raidar.app.sql.api.builder.SqlQuery;

import java.time.LocalDateTime;

import static org.raidar.app.sql.impl.SqlConstants.*;

public class SqlUtils {

    private SqlUtils() {
        // Nothing to do.
    }

    public static String enclose(String clause) {
        return CommonUtils.enclose(clause, DEFAULT_ENCLOSE_START, DEFAULT_ENCLOSE_END);
    }

    public static String enclose(SqlClause clause) {
        return CommonUtils.enclose(clause.getText(), NEWLINE_ENCLOSE_START, NEWLINE_ENCLOSE_END);
    }

    public static String escapeName(String name) {
        return CommonUtils.isEmpty(name) ? name : "\"" + name.replace("\"", "") + "\"";
    }

    public static String escapeValue(String value) {
        return CommonUtils.isEmpty(value) ? value : "'" + value.replace("'", "''") + "'";
    }

    public static boolean isEmpty(SqlQuery query) {
        return query == null || query.isEmpty();
    }

    public static boolean isEmpty(SqlClause expression) {
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
