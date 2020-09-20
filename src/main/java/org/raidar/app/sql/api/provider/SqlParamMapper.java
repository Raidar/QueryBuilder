package org.raidar.app.sql.api.provider;

import org.raidar.app.sql.api.builder.SqlParam;

import java.io.Serializable;

public interface SqlParamMapper {

    String toString(SqlParam param);

    String toString(String name, Serializable value);
}
