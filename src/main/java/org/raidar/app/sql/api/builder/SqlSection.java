package org.raidar.app.sql.api.builder;

/** SQL-раздел - параметризованное SQL-предложение. */
public interface SqlSection extends SqlClause {

    SqlParamList getParams();
}
