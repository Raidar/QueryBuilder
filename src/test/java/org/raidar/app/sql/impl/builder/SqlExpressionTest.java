package org.raidar.app.sql.impl.builder;

import org.junit.Assert;
import org.junit.Test;
import org.raidar.app.sql.SqlBaseTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SqlExpressionTest extends SqlBaseTest {

    private static final List<String> EMPTY_VALUES = createEmptyValues();

    @Test
    public void testNullMapper() {

        SqlExpression expression = new SqlExpression();
        SqlExpression nullExpression = new SqlExpression(null);
        assertObjects(Assert::assertEquals, expression, nullExpression);
    }

    @Test
    public void testSpecialEquals() {

        SqlExpression expression = new SqlExpression();
        assertSpecialEquals(expression);
    }

    @Test
    public void testEmpty() {

        SqlExpression expression = new SqlExpression();
        assertEmpty(expression.getText());
        assertTrue(expression.isEmpty());

        try {
            expression.enclose();

            fail(getFailedMessage(IllegalArgumentException.class));

        } catch (RuntimeException e) {

            assertEquals(IllegalArgumentException.class, e.getClass());
            assertNotNull(getExceptionMessage(e));
        }
    }

    private static List<String> createEmptyValues() {

        List<String> result = new ArrayList<>(4);
        result.add(null);
        result.add("");
        result.add("  ");
        result.add("\t");

        return result;
    }
}