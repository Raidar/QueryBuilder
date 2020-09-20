package org.raidar.app.sql.api;

import java.io.Serializable;

/** SQL-параметр для привязки значения. */
public interface SqlParam extends Serializable {

    String getName();

    Serializable getValue();
}
