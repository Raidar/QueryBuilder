package org.raidar.app.sql.api.provider;

import org.raidar.app.sql.api.builder.SqlQuery;

public interface SqlQueryParamSubstitutor {

    String substitute(SqlQuery query);
}
