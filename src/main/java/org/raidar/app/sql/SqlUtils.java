package org.raidar.app.sql;

import org.raidar.app.sql.builder.SqlBuilder;
import org.raidar.app.sql.builder.SqlExpression;

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

        return isEmpty(name) ? name : "\"" + name + "\"";
    }

    public static String escapeValue(String value) {

        return isEmpty(value) ? value : "'" + value.replace("'", "\\'") + "'";
    }

    public static boolean isEmpty(SqlBuilder builder) {
        return builder == null || builder.isEmpty();
    }

    public static boolean isEmpty(SqlExpression expression) {
        return expression == null || expression.isEmpty();
    }
}
