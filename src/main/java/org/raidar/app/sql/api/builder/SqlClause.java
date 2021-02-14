package org.raidar.app.sql.api.builder;

import java.io.Serializable;

/** SQL clause - a base part of SQL statement. */
public interface SqlClause extends Serializable {

    void clear();

    String getText();

    boolean isEmpty();

    SqlClause enclose();

    boolean isEnclosed();
}
