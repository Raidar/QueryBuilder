package org.raidar.app.sql.impl.utils;

import org.raidar.app.sql.api.builder.SqlClause;
import org.raidar.app.sql.api.builder.SqlQuery;

import static org.raidar.app.sql.impl.constant.SqlConstants.*;

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
}
