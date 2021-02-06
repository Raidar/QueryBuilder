package org.raidar.app.sql.test.model;

import org.raidar.app.sql.api.provider.SqlParamMapper;
import org.raidar.app.sql.impl.builder.SqlLiteral;

public class SqlTestLiteral extends SqlLiteral {

    public SqlTestLiteral() {
        super();
    }

    public SqlTestLiteral(SqlParamMapper paramMapper) {
        super(paramMapper);
    }

    @Override
    public SqlLiteral literal(String argument) {
        return super.literal(argument);
    }
}
