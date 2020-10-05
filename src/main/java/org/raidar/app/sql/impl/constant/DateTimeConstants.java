package org.raidar.app.sql.impl.constant;

import java.time.format.DateTimeFormatter;

public class DateTimeConstants {

    public static final String MIN_TIMESTAMP_VALUE = "'-infinity'";
    public static final String MAX_TIMESTAMP_VALUE = "'infinity'";

    // Date format to use in queries.
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    public static final String SQL_DATE_FORMAT = "DD.MM.YYYY";

    // Datetime format to use in queries.
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
    public static final String SQL_TIMESTAMP_FORMAT = "YYYY-MM-DD HH24:MI:SS";

    private DateTimeConstants() {
        // Nothing to do.
    }
}
