package org.raidar.app.sql.impl.builder;

import org.raidar.app.sql.api.builder.SqlParam;
import org.raidar.app.sql.api.builder.SqlParamList;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/** Список параметров SQL-запроса. */
public class SqlParameterList implements SqlParamList {

    /** Список bind-параметров. */
    private final List<SqlParam> params = new ArrayList<>();

    public SqlParameterList() {
        // Nothing to do.
    }

    public SqlParameterList(SqlParamList params) {

        if (params != null && !params.isEmpty()) {
            List<SqlParameter> list = params.get().stream().map(SqlParameter::new).collect(toList());
            add(list);
        }
    }

    @Override
    public List<? extends SqlParam> get() {
        return params;
    }

    @Override
    public SqlParam get(String name) {
        return params.stream().filter(param -> name.equals(param.getName())).findFirst().orElse(null);
    }

    @Override
    public Serializable getValue(String name) {
        SqlParam param = get(name);
        return (param != null) ? param.getValue() : null;
    }

    @Override
    public void clear() {
        params.clear();
    }

    @Override
    public boolean isEmpty() {
        return params.isEmpty();
    }

    @Override
    public void add(SqlParam param) {
        params.add(param);
    }

    @Override
    public void add(String name, Serializable value) {
        params.add(new SqlParameter(name, value));
    }

    @Override
    public void add(SqlParamList params) {

        if (params == null || params.isEmpty())
            return;

        this.params.addAll(params.get());
    }

    @Override
    public void add(Collection<? extends SqlParam> params) {

        if (params == null || params.isEmpty())
            return;

        this.params.addAll(params);
    }

    @Override
    public Map<String, Serializable> getMap() {
        return this.params.stream().collect(Collectors.toMap(SqlParam::getName, SqlParam::getValue));
    }

    @Override
    public void setMap(Map<String, Serializable> map) {

        clear();
        addMap(map);
    }

    @Override
    public void addMap(Map<String, Serializable> map) {

        if (map == null || map.isEmpty())
            return;

        List<SqlParam> params = map.entrySet().stream()
                .map(e -> new SqlParameter(e.getKey(), e.getValue()))
                .collect(toList());
        add(params);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SqlParameterList that = (SqlParameterList)o;
        return Objects.equals(params, that.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(params);
    }

    @Override
    public String toString() {
        return "SqlParameterList{" +
                "params=" + params.toString() +
                '}';
    }
}
