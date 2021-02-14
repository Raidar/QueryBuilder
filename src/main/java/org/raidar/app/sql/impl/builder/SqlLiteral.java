package org.raidar.app.sql.impl.builder;

import org.raidar.app.sql.api.builder.SqlClause;
import org.raidar.app.sql.api.provider.SqlParamMapper;
import org.raidar.app.sql.impl.provider.SqlParameterMapper;
import org.raidar.app.sql.impl.utils.CommonUtils;
import org.raidar.app.sql.impl.utils.SqlUtils;

import java.io.Serializable;
import java.util.Objects;

import static org.raidar.app.sql.impl.constant.SqlConstants.*;

/** SQL literal - a part of SQL expression. */
@SuppressWarnings("SameParameterValue")
public class SqlLiteral implements SqlClause {

    private static final SqlParamMapper DEFAULT_PARAM_MAPPER = new SqlParameterMapper();

    public static final SqlLiteral TRUE = new SqlLiteral().value(Boolean.TRUE);
    public static final SqlLiteral FALSE = new SqlLiteral().value(Boolean.FALSE);

    /** Current literal. */
    private String literal = "";

    /** Mapper for bind parameters. */
    private final SqlParamMapper paramMapper;

    public SqlLiteral() {
        this(DEFAULT_PARAM_MAPPER);
    }

    public SqlLiteral(SqlParamMapper paramMapper) {
        this.paramMapper = (paramMapper != null) ? paramMapper : DEFAULT_PARAM_MAPPER;
    }

    @Override
    public void clear() {
        throw new IllegalStateException("A call of 'clear' is not allowed");
    }

    public SqlLiteral nihil() {
        return literal(NULL_VALUE);
    }

    public SqlLiteral field(String name) {

        if (CommonUtils.isBlank(name))
            throw new IllegalArgumentException("A field name is empty.");

        return literal(name);
    }

    public SqlLiteral param(String name) {

        if (CommonUtils.isBlank(name))
            throw new IllegalArgumentException("A parameter name is empty.");

        return literal(BIND_PREFIX + name);
    }

    public SqlLiteral value(Serializable value) {
        return literal(paramMapper.toString(null, value));
    }

    protected SqlLiteral literal(String argument) {

        if (CommonUtils.isBlank(argument))
            throw new IllegalArgumentException("A literal argument is empty.");

        if (!CommonUtils.isBlank(literal))
            throw new IllegalStateException("A literal is already defined.");

        this.literal = argument;

        return this;
    }

    @Override
    public String getText() {
        return literal;
    }

    @Override
    public boolean isEmpty() {
        return CommonUtils.isEmpty(literal);
    }

    @Override
    public SqlLiteral enclose() {

        if (isEmpty())
            throw new IllegalArgumentException("A literal is empty.");

        String enclosed = SqlUtils.enclose(this.literal);
        return new SqlLiteral().literal(enclosed);
    }

    @Override
    public boolean isEnclosed() {
        return SqlUtils.isEnclosed(this.literal);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SqlLiteral that = (SqlLiteral)o;
        return Objects.equals(literal, that.literal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(literal);
    }

    @Override
    public String toString() {
        return "SqlExpression{" +
                "literal='" + literal + '\'' +
                '}';
    }
}
