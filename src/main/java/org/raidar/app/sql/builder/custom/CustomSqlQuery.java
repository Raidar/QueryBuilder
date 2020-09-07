package org.raidar.app.sql.builder.custom;

import org.raidar.app.sql.api.SqlParam;
import org.raidar.app.sql.builder.SqlBuilder;
import org.raidar.app.sql.builder.SqlParameter;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.raidar.app.sql.SqlConstants.CLAUSE_SEPARATOR;

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

        return getParams().stream().collect(toMap(SqlParam::getName, SqlParam::getValue));
    }

    public void setParamsMap(Map<String, Serializable> map) {

        clearParams();
        add(map);
    }

    public void add(String sql) {

        append(CLAUSE_SEPARATOR);
        append(sql);
    }

    public void add(Map<String, Serializable> map) {

        if (map == null || map.isEmpty())
            return;

        List<SqlParameter> params = map.entrySet().stream()
                .map(e -> new SqlParameter(e.getKey(), e.getValue()))
                .collect(toList());

        bind(params);
    }

    public void add(String sql, Map<String, Serializable> map) {

        add(sql);
        add(map);
    }

    public void add(CustomSqlQuery query) {

        if (query == null)
            return;

        add(query.getSql(), query.getParameters());
    }

    public void add(String sql, List<SqlParameter> params) {

        add(sql);
        bind(params);
    }

    @Override
    public String toString() {
        return "CustomSqlQuery{" +
                super.toString() +
                '}';
    }
}
