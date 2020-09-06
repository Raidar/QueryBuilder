package org.raidar.app.sql.builder.custom;

import org.raidar.app.sql.builder.SqlBuilder;

import java.io.Serializable;
import java.util.Map;

// Implement QueryWithParams here:
/** Пользовательский SQL-запрос. */
public class CustomSqlQuery extends SqlBuilder {

    public CustomSqlQuery() {
        // Nothing to do.
    }

    public String getSql() {
        return getText();
    }

    public void setSql(String sql) {

        clearText();
        append(sql);
    }

    public void setParams(Map<String, Serializable> params) {

        clearParams();
        bind(params);
    }

    public String getBindedValue() {

        // to-do: из QueryWithParams
        return getSql();
    }

    public void add(String sql) {

        append(sql);
    }

    public void add(Map<String, Serializable> params) {

        bind(params);
    }

    public void add(String sql, Map<String, Serializable> params) {

        add(sql);
        bind(params);
    }

    public void add(CustomSqlQuery query) {

        add(query.getSql(), query.getParams());
    }

    @Override
    public String toString() {
        return "CustomSqlQuery{" +
                super.toString() +
                '}';
    }
}
