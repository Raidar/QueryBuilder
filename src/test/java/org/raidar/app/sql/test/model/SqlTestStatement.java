package org.raidar.app.sql.test.model;

import org.raidar.app.sql.api.builder.SqlClause;
import org.raidar.app.sql.api.builder.SqlParamList;
import org.raidar.app.sql.api.builder.SqlStatement;
import org.raidar.app.sql.impl.utils.SqlUtils;

public class SqlTestStatement implements SqlStatement {

    private final StringBuilder builder = new StringBuilder();

    private final SqlTestParamList params = new SqlTestParamList();

    @Override
    public void clear() {

        builder.setLength(0);
        params.clear();
    }

    @Override
    public String getText() {
        return builder.toString();
    }

    @Override
    public SqlParamList getParams() {
        return params;
    }

    @Override
    public boolean isEmpty() {
        return builder.length() == 0;
    }

    @Override
    public SqlClause enclose() {

        SqlUtils.enclose(builder);
        return this;
    }

    @Override
    public boolean isEnclosed() {
        return SqlUtils.isEnclosed(this.builder);
    }

    public SqlTestStatement add(String text) {

        builder.append(text);
        return this;
    }

    public SqlTestStatement add(SqlParamList params) {

        this.params.add(params);
        return this;
    }
}
