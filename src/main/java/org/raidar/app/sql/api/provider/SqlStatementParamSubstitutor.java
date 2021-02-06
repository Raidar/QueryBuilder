package org.raidar.app.sql.api.provider;

import org.raidar.app.sql.api.builder.SqlStatement;

/** Bind parameters` values substitutor in Sql statement. */
public interface SqlStatementParamSubstitutor {

    String substitute(SqlStatement statement);
}
