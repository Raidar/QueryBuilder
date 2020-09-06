package org.raidar.app.sql.api;

import java.io.Serializable;

/** SQL-предложение. */
public interface SqlClause extends Serializable {

    String getText();

    boolean isEmpty();

    SqlClause enclosed();
}
