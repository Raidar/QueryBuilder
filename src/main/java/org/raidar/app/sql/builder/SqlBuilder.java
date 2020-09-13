package org.raidar.app.sql.builder;

import org.raidar.app.sql.SqlUtils;
import org.raidar.app.sql.api.SqlParam;
import org.raidar.app.sql.api.SqlParamMapper;
import org.raidar.app.sql.api.SqlQuery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.raidar.app.sql.SqlConstants.BIND_PREFIX;

/** Построитель SQL-запроса. */
@SuppressWarnings({"unused", "SameParameterValue"})
public class SqlBuilder implements SqlQuery {

    private static final SqlParamMapper DEFAULT_PARAM_MAPPER = new SqlParameterMapper();

    /** Сборщик SQL-запроса. */
    private final StringBuilder builder = new StringBuilder();

    /** Список bind-параметров. */
    private final List<SqlParameter> params = new ArrayList<>();

    /** Подстановщик значений bind-параметров для некоторых запросов. */
    private final SqlParamMapper paramMapper;

    public SqlBuilder() {

        this(DEFAULT_PARAM_MAPPER);
    }

    public SqlBuilder(SqlParamMapper paramMapper) {

        this.paramMapper = (paramMapper != null) ? paramMapper : DEFAULT_PARAM_MAPPER;
    }

    protected SqlBuilder append(String clause) {

        if (!SqlUtils.isEmpty(clause)) {
            builder.append(clause);
        }

        return this;
    }

    protected SqlBuilder prepend(String clause) {

        if (!SqlUtils.isEmpty(clause)) {
            builder.insert(0, clause);
        }

        return this;
    }

    public SqlBuilder bind(String name, Serializable value) {

        if (SqlUtils.isEmpty(name))
            throw new IllegalArgumentException("A parameter name is empty.");

        getParameters().add(new SqlParameter(name, value));

        return this;
    }

    public SqlBuilder bind(List<SqlParameter> params) {

        if (params != null && !params.isEmpty()) {
            getParameters().addAll(params);
        }

        return this;
    }

    public String toParamText() {

        if (isEmpty())
            return null;

        String result = getText();

        if (params.isEmpty())
            return result;

        // to-do: Переписать для ускорения: проходить sql по ":(bind)" и собирать result.
        for (SqlParam param : params) {
            result = result.replace(BIND_PREFIX + param.getName(), paramMapper.toString(param));
        }

        return result;
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
    public List<? extends SqlParam> getParams() {
        return params;
    }

    protected List<SqlParameter> getParameters() {
        return params;
    }

    @Override
    public boolean isEmpty() {
        return builder.length() > 0;
    }

    @Override
    public SqlBuilder enclose() {

        if (isEmpty())
            throw new IllegalArgumentException("A query is empty.");

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
