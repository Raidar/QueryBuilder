package org.raidar.app.sql.builder;

import org.raidar.app.sql.SqlUtils;

/** Условие SQL-оператора. */
public class SqlCondition extends SqlBuilder {

    private static final String LOGICAL_AND = " AND ";
    private static final String LOGICAL_OR = " OR ";

    public SqlCondition() {
        // Nothing to do.
    }

    public SqlCondition and(SqlExpression expression) {
        return add(LOGICAL_AND, expression);
    }

    public SqlCondition or(SqlExpression expression) {
        return add(LOGICAL_OR, expression);
    }

    protected SqlCondition add(String operator, SqlExpression expression) {

        String value = expression != null ? expression.getText() : null;
        if (SqlUtils.isEmpty(value))
            return this;

        if (!isEmpty() || !SqlUtils.isEmpty(operator)) {
            append(operator);
        }

        append(expression.enclosed().getText());

        return this;
    }

    public SqlCondition and(SqlExpression... expressions) {
        return add(LOGICAL_AND, expressions);
    }

    public SqlCondition or(SqlExpression... expressions) {
        return add(LOGICAL_OR, expressions);
    }

    protected SqlCondition add(String concat, SqlExpression... expressions) {

        for (SqlExpression expression : expressions) {
            add(concat, expression);
        }

        return this;
    }

    @Override
    public String toString() {
        return "SqlCondition{" +
                super.toString() +
                '}';
    }
}
