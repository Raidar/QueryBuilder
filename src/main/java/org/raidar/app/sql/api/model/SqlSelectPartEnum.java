package org.raidar.app.sql.api.model;

import static java.util.List.of;

/** Часть SQL-оператора SELECT. */
public enum SqlSelectPartEnum {

    EMPTY,
    WITH,
    SELECT,
    OUTPUT,
    FROM,
    JOIN,
    WHERE,
    ORDER_BY,
    LIMIT,
    OFFSET,
    ;

    public static boolean withAllowed(SqlSelectPartEnum value) {
        return of(SqlSelectPartEnum.EMPTY, SqlSelectPartEnum.WITH).contains(value);
    }

    public static boolean selectAllowed(SqlSelectPartEnum value) {
        return of(SqlSelectPartEnum.EMPTY, SqlSelectPartEnum.WITH).contains(value);
    }

    public static boolean outputAllowed(SqlSelectPartEnum value) {
        return SqlSelectPartEnum.SELECT.equals(value);
    }

    public static boolean fromAllowed(SqlSelectPartEnum value) {
        return of(SqlSelectPartEnum.OUTPUT, SqlSelectPartEnum.FROM).contains(value);
    }

    public static boolean joinAllowed(SqlSelectPartEnum value) {
        return of(SqlSelectPartEnum.FROM, SqlSelectPartEnum.JOIN).contains(value);
    }

    public static boolean whereAllowed(SqlSelectPartEnum value) {
        return of(SqlSelectPartEnum.FROM, SqlSelectPartEnum.JOIN).contains(value);
    }
}
