package org.raidar.app.sql.builder;

import org.raidar.app.sql.SqlUtils;
import org.raidar.app.sql.api.SqlParam;

import java.io.Serializable;
import java.util.Objects;

public class SqlParameter implements SqlParam {

    private final String name;

    private final Serializable value;

    public SqlParameter(String name, Serializable value) {

        if (SqlUtils.isBlank(name))
            throw new IllegalArgumentException("The parameter name is empty.");

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SqlParameter that = (SqlParameter)o;
        return Objects.equals(name, that.name) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }

    @Override
    public String toString() {
        return "SqlParameter{" +
                "name='" + name + '\'' +
                (value != null ? ", value=" + value : "") +
                '}';
    }
}
