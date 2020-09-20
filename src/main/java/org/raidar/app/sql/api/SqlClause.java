package org.raidar.app.sql.api;

import java.io.Serializable;

/** SQL-предложение - часть SQL-запроса. */
public interface SqlClause extends Serializable {

    void clear();

    String getText();

    boolean isEmpty();

    SqlClause enclose();
}
