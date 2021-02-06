package org.raidar.app.sql.impl.builder.custom;

import org.raidar.app.sql.impl.builder.SqlLiteral;
import org.raidar.app.sql.impl.constant.SqlConstants;
import org.raidar.app.sql.impl.builder.SqlExpression;

/** Custom build of SQL expression. */
public class CustomSqlExpression extends SqlExpression {

    private static final String CUSTOM_AND = " AND ";

    public CustomSqlExpression() {
        // Nothing to do.
    }

    public CustomSqlExpression custom(String custom) {

        // For custom expression insert only:
        literal(SqlLiteral.TRUE);

        return (CustomSqlExpression) with(CUSTOM_AND + custom);
    }

    @Override
    public String toString() {
        return "CustomSqlExpression{" +
                super.toString() +
                '}';
    }
}
