package org.raidar.app.sql.impl.constant;

import java.util.regex.Pattern;

public class SqlConstants {

    // Types:
    public static final String TIMESTAMP_WITH_TIME_ZONE = "timestamp with time zone";
    public static final String TIMESTAMP_WITHOUT_TIME_ZONE = "timestamp without time zone";

    // Values:
    public static final String NULL_VALUE = "null";
    public static final String TRUE_VALUE = "true";
    public static final String FALSE_VALUE = "false";
    public static final String UNKNOWN_VALUE = "unknown";

    // Notation:
    public static final String BIND_PREFIX = ":";
    public static final String CAST_OPERATOR = "\\:\\:";

    public static final String QUERY_NEW_LINE = "\n";
    public static final String LIST_SEPARATOR = ",\n";
    public static final String NAME_SEPARATOR = ".";
    public static final String CLAUSE_SEPARATOR = " ";

    public static final String DEFAULT_ENCLOSE_START = "(";
    public static final String DEFAULT_ENCLOSE_END = ")";
    public static final String NEWLINE_ENCLOSE_START = DEFAULT_ENCLOSE_START + "\n";
    public static final String NEWLINE_ENCLOSE_END = "\n" + DEFAULT_ENCLOSE_END;

    // By default, NAMEDATALEN is 64 so the maximum identifier length is 63 bytes.
    private static final int NAME_DATA_LEN = 64;
    private static final String NAME_REGEX = "[A-Za-z][A-Za-z\\d_]{0," + (NAME_DATA_LEN - 2) + "}";
    public static final Pattern NAME_PATTERN = Pattern.compile(NAME_REGEX);

    public static final String TO_DATE_FORMAT = "to_date(%1$s, %2$s)";
    public static final String TO_TIMESTAMP_FORMAT = "to_timestamp(%1$s, %2$s)";

    private SqlConstants() {
        // Nothing to do.
    }
}
