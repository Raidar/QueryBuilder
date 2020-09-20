package org.raidar.app.sql.impl.builder.custom;

import org.raidar.app.sql.impl.SqlConstants;
import org.raidar.app.sql.impl.builder.SqlExpression;

/** Пользовательское SQL-выражение. */
public class CustomSqlExpression extends SqlExpression {

    private static final String CUSTOM_AND = " AND ";

    public CustomSqlExpression() {
        // Nothing to do.
    }

    public CustomSqlExpression custom(String custom) {

        // For custom expression insert only:
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
