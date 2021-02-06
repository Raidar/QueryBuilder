package org.raidar.app.sql.test.model;

import org.raidar.app.sql.api.builder.SqlStatement;
import org.raidar.app.sql.api.provider.SqlParamMapper;
import org.raidar.app.sql.impl.builder.SqlExpression;

public class SqlTestExpression extends SqlExpression {

    public SqlTestExpression() {
        super();
    }

    public SqlTestExpression(SqlParamMapper paramMapper) {
        super(paramMapper);
    }

    @Override
    public SqlExpression literal(String argument) {
        return super.literal(argument);
    }

    @Override
    public SqlExpression with(String expression) {
        return super.with(expression);
    }

    @Override
    public SqlExpression subquery(SqlStatement query) {
        return super.subquery(query);
    }
}
