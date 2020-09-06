package org.raidar.app.sql.builder;

import org.raidar.app.sql.SqlUtils;
import org.raidar.app.sql.api.SqlClause;
import org.raidar.app.sql.model.SqlExpressionPartEnum;

import java.io.Serializable;
import java.util.Objects;

import static org.raidar.app.sql.SqlConstants.*;
import static org.raidar.app.sql.SqlUtils.enclose;

/** SQL-выражение. */
public class SqlExpression implements SqlClause {

    // Unary operators:
    private static final String LOGICAL_NOT = "NOT ";
    private static final String IS = " is ";
    private static final String IS_NOT = " is not ";

    // Binary operators:
    private static final String EQUAL = " = ";
    private static final String NOT_EQUAL = " <> ";
    private static final String LESS_THAN = " < ";
    private static final String LESS_THAN_OR_EQUAL = " <= ";
    private static final String GREATER_THAN = " > ";
    private static final String GREATER_THAN_OR_EQUAL = " >= ";

    // -- NULL compatible comparison operators:
    private static final String IDENTICAL = " is not distinct of ";
    private static final String NOT_IDENTICAL = " is distinct of ";

    // Ranged operators:
    private static final String BETWEEN = " between ";
    private static final String NOT_BETWEEN = " not between ";
    private static final String BETWEEN_CONCAT = " and ";

    private String expression = "";

    private SqlExpressionPartEnum part = SqlExpressionPartEnum.EMPTY;

    public SqlExpression() {
        // Nothing to do.
    }

    public SqlExpression nihil() {
        return literal(NULL_VALUE);
    }

    public SqlExpression field(String name) {
        return literal(name);
    }

    public SqlExpression param(String name) {
        return literal(BIND_PREFIX + name);
    }

    public SqlExpression value(Serializable value) {
        // to-do: из QueryWithParams
        return literal(value != null ? value.toString() : null);
    }

    public SqlExpression not() {
        return prefixed(LOGICAL_NOT);
    }

    public SqlExpression isNull() {
        return postfixed(IS + NULL_VALUE);
    }

    public SqlExpression isNotNull() {
        return postfixed(IS_NOT + NULL_VALUE);
    }

    public SqlExpression isTrue() {
        return postfixed(IS + TRUE_VALUE);
    }

    public SqlExpression isNotTrue() {
        return postfixed(IS_NOT + TRUE_VALUE);
    }

    public SqlExpression isFalse() {
        return postfixed(IS + FALSE_VALUE);
    }

    public SqlExpression isNotFalse() {
        return postfixed(IS_NOT + FALSE_VALUE);
    }

    public SqlExpression equal(SqlExpression operand) {
        return binary(EQUAL, operand);
    }

    public SqlExpression notEqual(SqlExpression operand) {
        return binary(NOT_EQUAL, operand);
    }

    public SqlExpression lessThan(SqlExpression operand) {
        return binary(LESS_THAN, operand);
    }

    public SqlExpression lessThanOrEqual(SqlExpression operand) {
        return binary(LESS_THAN_OR_EQUAL, operand);
    }

    public SqlExpression greaterThan(SqlExpression operand) {
        return binary(GREATER_THAN, operand);
    }

    public SqlExpression greaterThanOrEqual(SqlExpression operand) {
        return binary(GREATER_THAN_OR_EQUAL, operand);
    }

    public SqlExpression identical(SqlExpression operand) {
        return binary(IDENTICAL, operand);
    }

    public SqlExpression notIdentical(SqlExpression operand) {
        return binary(NOT_IDENTICAL, operand);
    }

    public SqlExpression between(SqlExpression left, SqlExpression right) {
        return ranged(BETWEEN, left, BETWEEN_CONCAT, right);
    }

    public SqlExpression notBetween(SqlExpression left, SqlExpression right) {
        return ranged(NOT_BETWEEN, left, BETWEEN_CONCAT, right);
    }

    /** Унарный префиксный оператор. */
    protected SqlExpression prefixed(String operator) {
        return with(operator + this.expression);
    }

    /** Унарный суффиксный оператор. */
    protected SqlExpression postfixed(String operator) {
        return with(this.expression + operator);
    }

    /** Бинарный оператор. */
    protected SqlExpression binary(String operator, SqlExpression operand) {
        return with(this.expression + operator + operand.expression);
    }

    /** Диапазонный оператор. */
    protected SqlExpression ranged(String operator, SqlExpression left,
                                   String separator, SqlExpression right) {
        return with(this.expression + operator + left.expression + separator + right.expression);
    }

    protected SqlExpression literal(String argument) {

        if (!SqlExpressionPartEnum.literalAllowed(part))
            throw new IllegalArgumentException("A LITERAL is not allowed.");

        if (SqlUtils.isBlank(argument))
            throw new IllegalArgumentException("A LITERAL argument is empty.");

        this.expression = argument;

        part = SqlExpressionPartEnum.LITERAL;

        return this;
    }

    protected SqlExpression with(String expression) {

        if (!SqlExpressionPartEnum.expressionAllowed(part))
            throw new IllegalArgumentException("An EXPRESSION is not allowed.");

        if (SqlUtils.isBlank(expression))
            throw new IllegalArgumentException("An EXPRESSION argument is empty.");

        if (!SqlUtils.isBlank(expression)) {
            this.expression = expression;

            part = SqlExpressionPartEnum.EXPRESSION;
        }

        return this;
    }

    @Override
    public String getText() {
        return expression;
    }

    @Override
    public boolean isEmpty() {
        return SqlUtils.isEmpty(expression);
    }

    @Override
    public SqlExpression enclosed() {
        return isEmpty() ? this : with(enclose(this.expression));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SqlExpression that = (SqlExpression)o;
        return Objects.equals(expression, that.expression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression);
    }

    @Override
    public String toString() {
        return "SqlExpression{" +
                "expression='" + expression + '\'' +
                ", part=" + part +
                '}';
    }
}
