package org.raidar.app.sql.impl.utils;

import org.raidar.app.sql.api.builder.SqlClause;
import org.raidar.app.sql.api.builder.SqlStatement;

import static org.raidar.app.sql.impl.constant.SqlConstants.*;

public class SqlUtils {

    private SqlUtils() {
        // Nothing to do.
    }

    public static String enclose(String clause) {
        return CommonUtils.enclose(clause, DEFAULT_ENCLOSE_START, DEFAULT_ENCLOSE_END);
    }

    public static boolean isEnclosed(String text) {
        return CommonUtils.isEnclosed(text, DEFAULT_ENCLOSE_START, DEFAULT_ENCLOSE_END);
    }

    public static String enclose(SqlClause clause) {
        return CommonUtils.enclose(clause.getText(), NEWLINE_ENCLOSE_START, NEWLINE_ENCLOSE_END);
    }

    public static StringBuilder enclose(StringBuilder builder) {
        return CommonUtils.enclose(builder, DEFAULT_ENCLOSE_START, DEFAULT_ENCLOSE_END);
    }

    public static boolean isEnclosed(StringBuilder builder) {
        return CommonUtils.isEnclosed(builder, DEFAULT_ENCLOSE_START, DEFAULT_ENCLOSE_END);
    }

    public static String escapeName(String name) {
        return CommonUtils.isEmpty(name) ? name : "\"" + name.replace("\"", "") + "\"";
    }

    public static String escapeValue(String value) {
        return CommonUtils.isEmpty(value) ? value : "'" + value.replace("'", "''") + "'";
    }

    public static boolean isEmpty(SqlClause expression) {
        return expression == null || expression.isEmpty();
    }

    public static boolean isEmpty(SqlStatement statement) {
        return statement == null || statement.isEmpty();
    }
}
