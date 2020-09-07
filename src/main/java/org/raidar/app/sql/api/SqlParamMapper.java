package org.raidar.app.sql.api;

import java.io.Serializable;

public interface SqlParamMapper {

    String toString(SqlParam param);

    String toString(String name, Serializable value);
}
