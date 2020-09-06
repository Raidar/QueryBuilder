package org.raidar.app.sql;

import java.util.regex.Pattern;

public class SqlConstants {

    public static final String NULL_VALUE = "null";
    public static final String TRUE_VALUE = "true";
    public static final String FALSE_VALUE = "false";
    public static final String UNKNOWN_VALUE = "unknown";

    public static final String BIND_PREFIX = ":";

    public static final String QUERY_NEW_LINE = "\n";
    public static final String LIST_SEPARATOR = ",\n";
    public static final String NAME_SEPARATOR = ".";

    // By default, NAMEDATALEN is 64 so the maximum identifier length is 63 bytes.
    private static final int NAME_DATA_LEN = 64;
    private static final String NAME_REGEX = "[A-Za-z][A-Za-z\\d_]{0," + (NAME_DATA_LEN - 2) + "}";
    public static final Pattern NAME_PATTERN = Pattern.compile(NAME_REGEX);
}
