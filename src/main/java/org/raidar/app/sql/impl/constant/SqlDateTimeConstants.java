package org.raidar.app.sql.impl.constant;

import java.util.List;

public class SqlDateTimeConstants {

    public static final String SQL_DATETIME_NOW = "now"; // current transaction's start time
    public static final String SQL_DATETIME_MIN = "-infinity";
    public static final String SQL_DATETIME_MAX = "infinity";

    public static final String SQL_DATETIME_ZERO = "allballs"; // 00:00:00.00 UTC
    public static final String SQL_UNIX_ZERO = "epoch"; // 1970-01-01 00:00:00+00

    public static final String SQL_MIDNIGHT_TODAY = "today";
    public static final String SQL_MIDNIGHT_TOMORROW = "tomorrow";
    public static final String SQL_MIDNIGHT_YESTERDAY = "yesterday";

    private static final List<String> SQL_SPECIALS = List.of(
            SQL_DATETIME_NOW, SQL_DATETIME_MIN, SQL_DATETIME_MAX,
            SQL_DATETIME_ZERO, SQL_UNIX_ZERO,
            SQL_MIDNIGHT_TODAY, SQL_MIDNIGHT_TOMORROW, SQL_MIDNIGHT_YESTERDAY
    );

    private SqlDateTimeConstants() {
        // Nothing to do.
    }

    public static List<String> getSqlSpecials() {
        return SQL_SPECIALS;
    }
}
