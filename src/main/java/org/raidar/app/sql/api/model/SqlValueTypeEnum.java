package org.raidar.app.sql.api.model;

/** Тип значения в SQL-предложении. */
public enum SqlValueTypeEnum {

    // to-do: Реализовать возможность задания типа параметра.
    AUTO,
    STRING,
    INT,
    LONG,
    BOOL,
    BIGINT,
    BIGDEC,
    ;
}
