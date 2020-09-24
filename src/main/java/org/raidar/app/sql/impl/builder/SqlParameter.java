package org.raidar.app.sql.impl.builder;

import org.raidar.app.sql.api.builder.SqlParam;

import java.io.Serializable;
import java.util.Objects;

import static org.raidar.app.sql.impl.utils.CommonUtils.isBlank;

/** Параметр SQL-запроса. */
public class SqlParameter implements SqlParam {

    private final String name;

    private final Serializable value;

    public SqlParameter(String name, Serializable value) {

        if (isBlank(name))
            throw new IllegalArgumentException("The parameter name is empty.");

        this.name = name;
        this.value = value;
    }

    public SqlParameter(SqlParam param) {

        if (param == null)
            throw new IllegalArgumentException("The parameter is empty.");

        this.name = param.getName();
        this.value = param.getValue();
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

    @Override
    public int compareTo(SqlParam o) {
        return name.compareTo(o.getName());
    }
}
