package org.raidar.app.sql.impl.builder;

import org.junit.Assert;
import org.junit.Test;
import org.raidar.app.sql.SqlBaseTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SqlExpressionTest extends SqlBaseTest {

    @Test
    public void testNullMapper() {

        SqlExpression expression = new SqlExpression();
        SqlExpression nullExpression = new SqlExpression(null);
        assertObjects(Assert::assertEquals, expression, nullExpression);
    }

    @Test
    public void testEmpty() {

        SqlExpression expression = new SqlExpression();
        assertSpecialEquals(expression);
        assertEmpty(expression.getText());
        assertTrue(expression.isEmpty());
    }

    @Test
    public void testEncloseEmpty() {

        SqlExpression expression = new SqlExpression();
        try {
            expression.enclose();
            fail(getFailedMessage(IllegalArgumentException.class));

        } catch (RuntimeException e) {

            assertEquals(IllegalArgumentException.class, e.getClass());
            assertNotNull(getExceptionMessage(e));
        }
    }
}