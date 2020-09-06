package org.raidar.app.sql.model;

import static java.util.List.of;

/** Часть SQL-выражения. */
public enum SqlExpressionPartEnum {

    EMPTY,
    VALUE,
    EXPRESSION
    ;

    public static boolean valueAllowed(SqlExpressionPartEnum value) {
        return SqlExpressionPartEnum.EMPTY.equals(value);
    }

    public static boolean expressionAllowed(SqlExpressionPartEnum value) {
        return of(SqlExpressionPartEnum.VALUE, SqlExpressionPartEnum.EXPRESSION).contains(value);
    }
}
