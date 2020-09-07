package org.raidar.app.sql.api;

import java.io.Serializable;

public interface SqlParam {

    String getName();

    Serializable getValue();
}
