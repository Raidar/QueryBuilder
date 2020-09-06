package org.raidar.app.sql.builder;

import org.raidar.app.sql.SqlUtils;
import org.raidar.app.sql.api.SqlQuery;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** Построитель SQL-запроса. */
public class SqlBuilder implements SqlQuery {

    /** Сборщик SQL-запроса. */
    private final StringBuilder builder = new StringBuilder();

    /** Набор bind-параметров. */
    private final Map<String, Serializable> params = new HashMap<>();

    public SqlBuilder() {
        // Nothing to do.
    }

    //protected StringBuilder getBuilder() {
    //    return builder;
    //}

    protected SqlBuilder append(String clause) {

        if (!SqlUtils.isEmpty(clause)) {
            builder.append(clause);
        }

        return this;
    }

    protected SqlBuilder prepend(String clause) {

        builder.insert(0, clause);
        return this;
    }

    public SqlBuilder bind(String name, Serializable value) {

        if (!SqlUtils.isEmpty(name)) {
            getParams().put(name, value);
        }

        return this;
    }

    public SqlBuilder bind(Map<String, Serializable> params) {

        if (params != null && !params.isEmpty()) {
            getParams().putAll(params);
        }

        return this;
    }

    protected void clearText() {
        builder.setLength(0);
    }

    protected void clearParams() {
        params.clear();
    }

    @Override
    public String getText() {
        return builder.toString();
    }

    @Override
    public Map<String, Serializable> getParams() {
        return params;
    }

    @Override
    public boolean isEmpty() {
        return builder.length() > 0;
    }

    @Override
    public SqlBuilder enclosed() {

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
