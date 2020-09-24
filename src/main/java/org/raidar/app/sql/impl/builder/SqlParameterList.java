package org.raidar.app.sql.impl.builder;

import org.raidar.app.sql.api.builder.SqlParam;
import org.raidar.app.sql.api.builder.SqlParamList;
import org.raidar.app.sql.impl.utils.CommonUtils;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.raidar.app.sql.impl.utils.CommonUtils.isBlank;

/** Список параметров SQL-запроса. */
public class SqlParameterList implements SqlParamList {

    /** Список bind-параметров. */
    private final Map<String, SqlParam> params = new HashMap<>();

    public SqlParameterList() {
        // Nothing to do.
    }

    public SqlParameterList(SqlParamList params) {

        if (params != null && !params.isEmpty()) {
            List<SqlParameter> list = params.stream().map(SqlParameter::new).collect(toList());
            add(list);
        }
    }

    @Override
    public int size() {
        return params.size();
    }

    @Override
    public boolean isEmpty() {
        return params.isEmpty();
    }

    @Override
    public boolean contains(String name) {
        return params.containsKey(name);
    }

    @Override
    public SqlParam get(String name) {
        return params.get(name);
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
    public void add(SqlParam param) {

        if (param == null)
            throw new IllegalArgumentException("The parameter is empty.");

        params.put(param.getName(), param);
    }

    @Override
    public void add(String name, Serializable value) {

        if (isBlank(name))
            throw new IllegalArgumentException("The parameter name is empty.");

        params.put(name, new SqlParameter(name, value));
    }

    @Override
    public void add(SqlParamList params) {

        if (params == null || params.isEmpty())
            return;

        add(params.stream());
    }

    @Override
    public Collection<? extends SqlParam> get() {

        return new ArrayList<>(params.values());
    }

    @Override
    public void set(Collection<? extends SqlParam> params) {

        clear();
        add(params);
    }

    @Override
    public void add(Collection<? extends SqlParam> params) {

        if (CommonUtils.isEmpty(params))
            return;

        add(params.stream());
    }

    @Override
    public Map<String, Serializable> getMap() {

        return this.params.values().stream().collect(toMap(SqlParam::getName, SqlParam::getValue));
    }

    @Override
    public void setMap(Map<String, Serializable> map) {

        clear();
        addMap(map);
    }

    @Override
    public void addMap(Map<String, Serializable> map) {

        if (CommonUtils.isEmpty(map))
            return;

        Stream<SqlParam> stream = map.entrySet().stream()
                .map(e -> new SqlParameter(e.getKey(), e.getValue()));
        add(stream);
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

    @Override
    public Iterator<SqlParam> iterator() {
        return params.values().iterator();
    }

    @Override
    public Stream<SqlParam> stream() {
        return params.values().stream();
    }

    @Override
    public void add(Stream<? extends SqlParam> stream) {

        this.params.putAll(stream.collect(toMap(SqlParam::getName, identity())));
    }
}
