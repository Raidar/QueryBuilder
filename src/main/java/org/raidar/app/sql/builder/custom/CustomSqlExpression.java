package org.raidar.app.sql.builder.custom;

import org.raidar.app.sql.SqlConstants;
import org.raidar.app.sql.builder.SqlExpression;

/** Пользовательское SQL-выражение. */
public class CustomSqlExpression extends SqlExpression {

    private static final String CUSTOM_AND = " AND ";

    public CustomSqlExpression() {
        // Nothing to do.
    }

    public CustomSqlExpression custom(String custom) {

        // Заглушка для проставления пользовательского выражения.
        literal(SqlConstants.TRUE_VALUE);
        return (CustomSqlExpression) with(CUSTOM_AND + custom);
    }

    @Override
    public String toString() {
        return "CustomSqlExpression{" +
                super.toString() +
                '}';
    }
}
