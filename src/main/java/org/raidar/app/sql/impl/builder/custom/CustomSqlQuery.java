package org.raidar.app.sql.impl.builder.custom;

import org.raidar.app.sql.api.builder.SqlParamList;
import org.raidar.app.sql.impl.builder.SqlBuilder;
import org.raidar.app.sql.impl.provider.SqlQueryParameterSubstitutor;

import java.io.Serializable;
import java.util.Map;

import static org.raidar.app.sql.impl.constant.SqlConstants.CLAUSE_SEPARATOR;

// Implement QueryWithParams here:
/** Пользовательский SQL-запрос. */
public class CustomSqlQuery extends SqlBuilder {

    public CustomSqlQuery() {
        // Nothing to do.
    }

    public CustomSqlQuery(String sql) {

        super();
        add(sql);
    }

    public CustomSqlQuery(String sql, Map<String, Serializable> map) {

        super();
        add(sql, map);
    }

    public String getSql() {
        return getText();
    }

    public void setSql(String sql) {

        clearText();
        add(sql);
    }

    public Map<String, Serializable> getParamsMap() {
        return getParams().getMap();
    }

    public void setParamsMap(Map<String, Serializable> map) {
        getParams().setMap(map);
    }

    public void add(String sql) {

        append(CLAUSE_SEPARATOR);
        append(sql);
    }

    public void add(Map<String, Serializable> map) {
        getParams().addMap(map);
    }

    public void add(String sql, Map<String, Serializable> map) {

        add(sql);
        add(map);
    }

    public void add(CustomSqlQuery query) {

        if (query == null)
            return;

        add(query.getSql(), query.getParams());
    }

    public void add(String sql, SqlParamList params) {

        add(sql);
        bind(params);
    }

    @Override
    public String toString() {
        return "CustomSqlQuery{" +
                super.toString() +
                '}';
    }

    public String toParamText() {

        SqlQueryParameterSubstitutor builder = new SqlQueryParameterSubstitutor();
        return builder.substitute(this);
    }
}
