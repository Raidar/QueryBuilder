package org.raidar.app.sql.api.model;

import static java.util.List.of;

/** Part of SQL expression. */
public enum SqlExpressionPartEnum {

    EMPTY,
    LITERAL,
    SUBQUERY,
    EXPRESSION
    ;

    public static boolean literalAllowed(SqlExpressionPartEnum value) {
        return SqlExpressionPartEnum.EMPTY.equals(value);
    }

    public static boolean subqueryAllowed(SqlExpressionPartEnum value) {
        return SqlExpressionPartEnum.EMPTY.equals(value);
    }

    public static boolean expressionAllowed(SqlExpressionPartEnum value) {
        return of(SqlExpressionPartEnum.LITERAL,
                SqlExpressionPartEnum.SUBQUERY,
                SqlExpressionPartEnum.EXPRESSION
        ).contains(value);
    }
}
