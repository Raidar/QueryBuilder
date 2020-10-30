package org.raidar.app.sql.api.builder;

import java.io.Serializable;

/** SQL parameter - a bind part of SQL query. */
public interface SqlParam extends Serializable, Comparable<SqlParam> {

    String getName();

    Serializable getValue();

    boolean isNameEquals(String name);

    boolean isNameEquals(SqlParam param);
}
