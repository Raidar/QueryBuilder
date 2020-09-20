package org.raidar.app.sql.impl.builder;

import org.raidar.app.sql.api.builder.SqlClause;
import org.raidar.app.sql.api.builder.SqlQuery;
import org.raidar.app.sql.api.model.SqlExpressionPartEnum;
import org.raidar.app.sql.api.provider.SqlParamMapper;
import org.raidar.app.sql.impl.SqlUtils;
import org.raidar.app.sql.impl.provider.SqlParameterMapper;

import java.io.Serializable;
import java.util.Objects;

import static org.raidar.app.sql.impl.SqlConstants.*;

/** SQL-выражение - составляющая часть условия или вывода SQL-запроса. */
@SuppressWarnings({"unused", "SameParameterValue"})
public class SqlExpression implements SqlClause {

    private static final SqlParamMapper DEFAULT_PARAM_MAPPER = new SqlParameterMapper();

    // Unary operators:
    private static final String EXISTS = "exists";
    private static final String NOT_EXISTS = "not exists";

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

    private static final String LIKE = " like ";
    private static final String NOT_LIKE = " not like ";
    private static final String LIKE_ESCAPE = " escape ";

    private static final String IN = " in ";
    private static final String NOT_IN = " not in ";

    /** Текущее выражение. */
    private String expression = "";

    /** Текущая имеющаяся часть выражения. */
    private SqlExpressionPartEnum part = SqlExpressionPartEnum.EMPTY;

    /** Подстановщик значений bind-параметров для некоторых запросов. */
    private final SqlParamMapper paramMapper;

    public SqlExpression() {
        this(DEFAULT_PARAM_MAPPER);
    }

    public SqlExpression(SqlParamMapper paramMapper) {
        this.paramMapper = (paramMapper != null) ? paramMapper : DEFAULT_PARAM_MAPPER;
    }

    public void clear() {

        expression = "";
        part = SqlExpressionPartEnum.EMPTY;
    }

    public SqlExpression nihil() {
        return literal(NULL_VALUE);
    }

    public SqlExpression field(String name) {

        if (SqlUtils.isBlank(name))
            throw new IllegalArgumentException("A field name is empty.");

        return literal(name);
    }

    public SqlExpression param(String name) {

        if (SqlUtils.isBlank(name))
            throw new IllegalArgumentException("A parameter name is empty.");

        return literal(BIND_PREFIX + name);
    }

    public SqlExpression value(Serializable value) {
        return literal(paramMapper.toString(null, value));
    }

    public SqlExpression exists(SqlQuery query) {
        return subquery(query).prefixed(EXISTS);
    }

    public SqlExpression notExists(SqlQuery query) {
        return subquery(query).prefixed(NOT_EXISTS);
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

    public SqlExpression equal(SqlClause operand) {
        return binary(EQUAL, operand);
    }

    public SqlExpression notEqual(SqlClause operand) {
        return binary(NOT_EQUAL, operand);
    }

    public SqlExpression lessThan(SqlClause operand) {
        return binary(LESS_THAN, operand);
    }

    public SqlExpression lessThanOrEqual(SqlClause operand) {
        return binary(LESS_THAN_OR_EQUAL, operand);
    }

    public SqlExpression greaterThan(SqlClause operand) {
        return binary(GREATER_THAN, operand);
    }

    public SqlExpression greaterThanOrEqual(SqlClause operand) {
        return binary(GREATER_THAN_OR_EQUAL, operand);
    }

    public SqlExpression identical(SqlClause operand) {
        return binary(IDENTICAL, operand);
    }

    public SqlExpression notIdentical(SqlClause operand) {
        return binary(NOT_IDENTICAL, operand);
    }

    public SqlExpression between(SqlClause left, SqlClause right) {
        return ranged(BETWEEN, left, BETWEEN_CONCAT, right);
    }

    public SqlExpression notBetween(SqlClause left, SqlClause right) {
        return ranged(NOT_BETWEEN, left, BETWEEN_CONCAT, right);
    }

    public SqlExpression like(SqlClause operand) {
        return binary(LIKE, operand);
    }

    public SqlExpression notLike(SqlClause operand) {
        return binary(NOT_LIKE, operand);
    }

    public SqlExpression likeEscape(SqlClause operand, SqlClause escape) {
        return ternary(LIKE, operand, LIKE_ESCAPE, escape);
    }

    public SqlExpression notLikeEscape(SqlClause operand, SqlClause escape) {
        return ternary(NOT_LIKE, operand, LIKE_ESCAPE, escape);
    }

    public SqlExpression in(SqlQuery query) {
        return binary(IN, query != null ? query.enclose() : null);
    }

    public SqlExpression notIn(SqlQuery query) {
        return binary(NOT_IN, query != null ? query.enclose() : null);
    }

    /** Унарный префиксный оператор. */
    protected SqlExpression prefixed(String operator) {

        if (SqlUtils.isBlank(operator))
            throw new IllegalArgumentException("A prefixed operator is empty.");

        return with(operator + this.expression);
    }

    /** Унарный суффиксный оператор. */
    protected SqlExpression postfixed(String operator) {

        if (SqlUtils.isBlank(operator))
            throw new IllegalArgumentException("A postfixed operator is empty.");

        return with(this.expression + operator);
    }

    /** Бинарный оператор. */
    protected SqlExpression binary(String operator, SqlClause operand) {

        if (SqlUtils.isBlank(operator))
            throw new IllegalArgumentException("A binary operator is empty.");

        if (SqlUtils.isEmpty(operand))
            throw new IllegalArgumentException(String.format("An operand for '%s' is empty.", operator));

        return with(this.expression + operator + operand.getText());
    }

    /** Тернарный оператор. */
    protected SqlExpression ternary(String operator1, SqlClause operand1,
                                    String operator2, SqlClause operand2) {

        if (SqlUtils.isBlank(operator1))
            throw new IllegalArgumentException("A ternary first operator is empty.");

        if (SqlUtils.isBlank(operator2))
            throw new IllegalArgumentException("A ternary second operator is empty.");

        if (SqlUtils.isEmpty(operand1))
            throw new IllegalArgumentException(String.format("An operand for '%s' is empty.", operator1));

        if (SqlUtils.isEmpty(operand2))
            throw new IllegalArgumentException(String.format("An operand for '%s' is empty.", operator2));

        return with(this.expression + operator1 + operand1.getText() + operator2 + operand2.getText());
    }

    /** Диапазонный оператор. */
    protected SqlExpression ranged(String operator, SqlClause left,
                                   String separator, SqlClause right) {

        if (SqlUtils.isBlank(operator))
            throw new IllegalArgumentException("A ranged operator is empty.");

        if (SqlUtils.isBlank(separator))
            throw new IllegalArgumentException("A ranged separator is empty.");

        if (SqlUtils.isEmpty(left))
            throw new IllegalArgumentException(String.format("A left operand for '%s' operator is empty.", operator));

        if (SqlUtils.isEmpty(right))
            throw new IllegalArgumentException(String.format("A right operand for '%s' operator is empty.", operator));

        return with(this.expression + operator + left.getText() + separator + right.getText());
    }

    /** Литерал. */
    protected SqlExpression literal(String argument) {

        if (!SqlExpressionPartEnum.literalAllowed(part))
            throw new IllegalArgumentException("A literal is not allowed.");

        if (SqlUtils.isBlank(argument))
            throw new IllegalArgumentException("A literal argument is empty.");

        this.expression = argument;

        part = SqlExpressionPartEnum.LITERAL;

        return this;
    }

    /** Выражение с использованием литерала. */
    protected SqlExpression with(String expression) {

        if (!SqlExpressionPartEnum.expressionAllowed(part))
            throw new IllegalArgumentException("An expression is not allowed.");

        if (SqlUtils.isBlank(expression))
            throw new IllegalArgumentException("An expression argument is empty.");

        this.expression = expression;

        part = SqlExpressionPartEnum.EXPRESSION;

        return this;
    }

    /** Подзапрос (для некоторых выражений). */
    protected SqlExpression subquery(SqlQuery query) {

        if (!SqlExpressionPartEnum.subqueryAllowed(part))
            throw new IllegalArgumentException("A subquery is not allowed.");

        if (SqlUtils.isEmpty(query))
            throw new IllegalArgumentException("A subquery is empty.");

        this.expression = query.getText();

        part = SqlExpressionPartEnum.SUBQUERY;

        return enclose();
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
    public SqlExpression enclose() {

        if (isEmpty())
            throw new IllegalArgumentException("An expression is empty.");

        this.expression = this.expression.contains(QUERY_NEW_LINE)
                ? SqlUtils.enclose(this)
                : SqlUtils.enclose(this.expression);

        return this;
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
