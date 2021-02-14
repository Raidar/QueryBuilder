package org.raidar.app.sql.impl.builder;

import org.raidar.app.sql.api.builder.SqlParamList;
import org.raidar.app.sql.api.builder.SqlStatement;
import org.raidar.app.sql.impl.utils.CommonUtils;
import org.raidar.app.sql.impl.utils.SqlUtils;

import java.io.Serializable;
import java.util.Objects;

/** SQL query - an implementation of SQL statement. */
@SuppressWarnings("SameParameterValue")
public class SqlQuery implements SqlStatement {

    /** SQL query` text builder. */
    private final StringBuilder builder = new StringBuilder();

    /** List of bind parameters. */
    private final SqlParameterList params = new SqlParameterList();

    public SqlQuery() {
        // Nothing to do.
    }

    public void clear() {

        clearText();
        clearParams();
    }

    protected void clearText() {
        builder.setLength(0);
    }

    protected void clearParams() {
        params.clear();
    }

    protected SqlQuery append(String clause) {

        if (!CommonUtils.isEmpty(clause)) {
            builder.append(clause);
        }

        return this;
    }

    protected SqlQuery prepend(String clause) {

        if (!CommonUtils.isEmpty(clause)) {
            builder.insert(0, clause);
        }

        return this;
    }

    public SqlQuery bind(String name, Serializable value) {

        params.add(name, value);
        return this;
    }

    public SqlQuery bind(SqlParamList params) {

        this.params.add(params);
        return this;
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
    public SqlQuery enclose() {

        if (isEmpty())
            throw new IllegalArgumentException("A query is empty.");

        SqlUtils.enclose(builder);
        return this;
    }

    @Override
    public boolean isEnclosed() {
        return SqlUtils.isEnclosed(this.builder);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SqlQuery that = (SqlQuery)o;
        return (builder == that.builder ||
                Objects.equals(builder.toString(), that.builder.toString())) &&
                Objects.equals(params, that.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(builder, params);
    }

    @Override
    public String toString() {
        return "SqlQuery{" +
                "builder=" + builder +
                ", params=" + params +
                '}';
    }
}
