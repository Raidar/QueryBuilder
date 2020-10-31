package org.raidar.app.sql.test.model;

import org.raidar.app.sql.api.builder.SqlParam;
import org.raidar.app.sql.api.builder.SqlParamList;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public class SqlTestParamList implements SqlParamList {

    private final Map<String, SqlParam> params = new HashMap<>();

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
        return get(name).getValue();
    }

    @Override
    public void clear() {
        params.clear();
    }

    @Override
    public void add(SqlParam param) {
        params.put(param.getName(), param);
    }

    @Override
    public void add(String name, Serializable value) {
        params.put(name, new SqlTestParam(name, value));
    }

    @Override
    public void add(SqlParamList params) {
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

        Stream<SqlParam> stream = map.entrySet().stream()
                .map(e -> new SqlTestParam(e.getKey(), e.getValue()));
        add(stream);
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
