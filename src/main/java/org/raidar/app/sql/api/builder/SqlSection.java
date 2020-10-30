package org.raidar.app.sql.api.builder;

/** SQL section - a parameterized SQL clause. */
public interface SqlSection extends SqlClause {

    SqlParamList getParams();
}
