package org.raidar.app.sql.impl.builder;

import org.raidar.app.sql.api.builder.SqlParamList;
import org.raidar.app.sql.api.builder.SqlQuery;
import org.raidar.app.sql.impl.utils.CommonUtils;

import java.io.Serializable;
import java.util.Objects;

/** Построитель SQL-запроса. */
@SuppressWarnings("SameParameterValue")
public class SqlBuilder implements SqlQuery {

    /** Сборщик SQL-запроса. */
    private final StringBuilder builder = new StringBuilder();

    /** Список bind-параметров. */
    private final SqlParameterList params = new SqlParameterList();

    public SqlBuilder() {
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

    protected SqlBuilder append(String clause) {

        if (!CommonUtils.isEmpty(clause)) {
            builder.append(clause);
        }

        return this;
    }

    protected SqlBuilder prepend(String clause) {

        if (!CommonUtils.isEmpty(clause)) {
            builder.insert(0, clause);
        }

        return this;
    }

    public SqlBuilder bind(String name, Serializable value) {

        params.add(name, value);
        return this;
    }

    public SqlBuilder bind(SqlParamList params) {

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
    public SqlBuilder enclose() {

        if (isEmpty())
            throw new IllegalArgumentException("A query is empty.");

        builder.insert(0, "(").append(")");
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SqlBuilder that = (SqlBuilder)o;
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
        return "SqlBuilder{" +
                "builder=" + builder +
                ", params=" + params +
                '}';
    }
}
