package org.raidar.app.sql.impl.builder;

import org.raidar.app.sql.api.builder.SqlClause;
import org.raidar.app.sql.impl.utils.CommonUtils;
import org.raidar.app.sql.impl.utils.SqlUtils;

/** Условие SQL-оператора. */
public class SqlCondition extends SqlBuilder {

    private static final String LOGICAL_AND = " AND ";
    private static final String LOGICAL_OR = " OR ";

    public SqlCondition() {
        super();
    }

    public SqlCondition and(SqlClause clause) {
        return add(LOGICAL_AND, clause);
    }

    public SqlCondition or(SqlClause clause) {
        return add(LOGICAL_OR, clause);
    }

    protected SqlCondition add(String operator, SqlClause clause) {

        if (SqlUtils.isEmpty(clause))
            throw new IllegalArgumentException("The clause is empty.");

        if (!isEmpty() || !CommonUtils.isEmpty(operator)) {
            append(operator);
        }

        append(SqlUtils.enclose(clause.getText()));

        return this;
    }

    public SqlCondition and(SqlClause... clauses) {
        return add(LOGICAL_AND, clauses);
    }

    public SqlCondition or(SqlClause... clauses) {
        return add(LOGICAL_OR, clauses);
    }

    protected SqlCondition add(String concat, SqlClause... clauses) {

        for (SqlClause clause : clauses) {
            add(concat, clause);
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
