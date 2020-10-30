package org.raidar.app.sql.impl.builder;

import org.raidar.app.sql.api.builder.SqlParamList;
import org.raidar.app.sql.api.builder.SqlQuery;
import org.raidar.app.sql.impl.utils.CommonUtils;

import java.io.Serializable;
import java.util.Objects;

/** SQL build - an implementation of SQL query. */
@SuppressWarnings("SameParameterValue")
public class SqlBuild implements SqlQuery {

    /** SQL query` text builder. */
    private final StringBuilder builder = new StringBuilder();

    /** List of bind parameters. */
    private final SqlParameterList params = new SqlParameterList();

    public SqlBuild() {
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

    protected SqlBuild append(String clause) {

        if (!CommonUtils.isEmpty(clause)) {
            builder.append(clause);
        }

        return this;
    }

    protected SqlBuild prepend(String clause) {

        if (!CommonUtils.isEmpty(clause)) {
            builder.insert(0, clause);
        }

        return this;
    }

    public SqlBuild bind(String name, Serializable value) {

        params.add(name, value);
        return this;
    }

    public SqlBuild bind(SqlParamList params) {

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
        return builder.length() > 0;
    }

    @Override
    public SqlBuild enclose() {

        if (isEmpty())
            throw new IllegalArgumentException("A query is empty.");

        builder.insert(0, "(").append(")");
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SqlBuild that = (SqlBuild)o;
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
        return "SqlBuild{" +
                "builder=" + builder +
                ", params=" + params +
                '}';
    }
}
