package org.raidar.app.sql.builder;

import org.raidar.app.sql.SqlUtils;
import org.raidar.app.sql.model.SqlSelectPartEnum;

import static org.raidar.app.sql.SqlConstants.*;
import static org.raidar.app.sql.SqlUtils.enclose;
import static org.raidar.app.sql.SqlUtils.isBlank;

/** SQL-оператор SELECT. */
public class SqlSelect extends SqlOperator {

    private static final String ALIAS_OPERATOR = " AS ";

    private SqlSelectPartEnum part = SqlSelectPartEnum.EMPTY;

    public SqlSelect() {
        // Nothing to do.
    }

    public SqlSelect with(String name, String columns, SqlOperator operator) {

        if (!SqlSelectPartEnum.withAllowed(part))
            throw new IllegalArgumentException("A WITH clause is not allowed.");

        if (isBlank(name) || isBlank(columns) || SqlUtils.isEmpty(operator))
            throw new IllegalArgumentException("Some WITH arguments are empty.");

        if (SqlSelectPartEnum.EMPTY.equals(part)) {
            append("WITH ");
        } else {
            append(LIST_SEPARATOR);
        }

        append(name);

        if (!SqlUtils.isEmpty(columns)) {
            append(enclose(columns));
        }

        append(ALIAS_OPERATOR).append(operator.enclosed().getText()).bind(operator.getParams());

        part = SqlSelectPartEnum.WITH;

        return this;
    }

    public SqlSelect select() {

        if (!SqlSelectPartEnum.selectAllowed(part))
            throw new IllegalArgumentException("A SELECT clause is not allowed.");

        if (!isEmpty()) {
            append(QUERY_NEW_LINE);
        }

        append("SELECT ");

        part = SqlSelectPartEnum.SELECT;

        return this;
    }

    public SqlSelect distinct() {

        if (SqlSelectPartEnum.SELECT.equals(part)) {
            append("DISTINCT ");
        }

        return this;
    }

    public SqlSelect output(String list) {

        if (!SqlSelectPartEnum.outputAllowed(part))
            throw new IllegalArgumentException("An OUTPUT clause is not allowed.");

        if (isBlank(list))
            throw new IllegalArgumentException("The OUTPUT argument is empty.");

        append(list);

        part = SqlSelectPartEnum.OUTPUT;

        return this;
    }

    public SqlSelect count() {

        return output("count(*)");
    }

    public SqlSelect select(String list) {

        if (isBlank(list))
            throw new IllegalArgumentException("The SELECT argument is empty.");

        return select().output(list);
    }

    public SqlSelect from(String schemaName, String tableName, String alias, String columnAliases) {

        if (!SqlSelectPartEnum.fromAllowed(part))
            throw new IllegalArgumentException("FROM clause is not allowed.");

        if (isBlank(tableName))
            throw new IllegalArgumentException("Some FROM arguments are empty.");

        if (!isEmpty()) {
            append(QUERY_NEW_LINE);
        }

        if (SqlSelectPartEnum.OUTPUT.equals(part)) {
            append("FROM ");
        } else {
            append(LIST_SEPARATOR);
        }

        if (!SqlUtils.isEmpty(schemaName)) {
            append(schemaName).append(NAME_SEPARATOR);
        }

        append(tableName);

        if (!SqlUtils.isEmpty(alias)) {
            append(ALIAS_OPERATOR).append(alias);
        }

        if (!SqlUtils.isEmpty(columnAliases)) {
            append(enclose(columnAliases));
        }

        part = SqlSelectPartEnum.FROM;

        return this;
    }

    public SqlSelect from(String schemaName, String tableName, String alias) {

        return from(schemaName, tableName, alias, null);
    }

    // to-do: from query
    // to-do: join (all types)

    public SqlSelect where(SqlCondition condition) {

        if (!SqlSelectPartEnum.whereAllowed(part))
            throw new IllegalArgumentException("WHERE clause is not allowed.");

        if (SqlUtils.isEmpty(condition))
            throw new IllegalArgumentException("The WHERE argument is empty.");

        append("WHERE ").append(condition.getText()).bind(condition.getParams());

        part = SqlSelectPartEnum.WHERE;

        return this;
    }

    protected void clearText() {

        super.clearText();

        part = SqlSelectPartEnum.EMPTY;
    }

    @Override
    public String toString() {
        return "SqlSelect{" +
                super.toString() +
                ", part=" + part +
                '}';
    }
}
