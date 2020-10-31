package org.raidar.app.sql.test.model;

import org.raidar.app.sql.api.builder.SqlParam;

import java.io.Serializable;

public class SqlTestParam implements SqlParam {

    private final String name;

    private final Serializable value;

    public SqlTestParam(String name, Serializable value) {

        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Serializable getValue() {
        return value;
    }

    @Override
    public boolean isNameEquals(String name) {
        return this.name.equals(name);
    }

    @Override
    public boolean isNameEquals(SqlParam param) {
        return isNameEquals(param.getName());
    }

    @Override
    public int compareTo(SqlParam o) {
        return name.compareTo(o.getName());
    }
}
