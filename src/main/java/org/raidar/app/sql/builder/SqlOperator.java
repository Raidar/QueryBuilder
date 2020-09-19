package org.raidar.app.sql.builder;

import org.raidar.app.sql.api.SqlParamMapper;

/** SQL-оператор. */
public class SqlOperator extends SqlBuilder {

    public SqlOperator() {
        super();
    }

    public SqlOperator(SqlParamMapper paramMapper) {
        super(paramMapper);
    }
}
