package org.raidar.app.sql.model;

import static java.util.List.of;

/** Часть SQL-выражения. */
public enum SqlExpressionPartEnum {

    EMPTY,
    LITERAL,
    EXPRESSION
    ;

    public static boolean literalAllowed(SqlExpressionPartEnum value) {
        return SqlExpressionPartEnum.EMPTY.equals(value);
    }

    public static boolean expressionAllowed(SqlExpressionPartEnum value) {
        return of(SqlExpressionPartEnum.LITERAL, SqlExpressionPartEnum.EXPRESSION).contains(value);
    }
}
