package org.raidar.app.sql.impl.builder;

import org.junit.Assert;
import org.junit.Test;
import org.raidar.app.sql.SqlBaseTest;
import org.raidar.app.sql.impl.utils.CommonUtils;

import static org.junit.Assert.*;
import static org.raidar.app.sql.impl.constant.SqlConstants.*;

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

        testEmptyEnclosed(expression);
    }

    @Test
    public void testLiteral() {

    }

    private void testEmptyEnclosed(SqlExpression expression) {
        try {
            expression.enclose();
            fail(getFailedMessage(IllegalArgumentException.class));

        } catch (RuntimeException e) {

            assertEquals(IllegalArgumentException.class, e.getClass());
            assertNotNull(getExceptionMessage(e));
        }
    }

    private void testDefaultEnclosed(SqlExpression expression, String value) {

        String actual = expression.enclose().getText();
        String expected = CommonUtils.enclose(value, DEFAULT_ENCLOSE_START, DEFAULT_ENCLOSE_END);
        assertEquals(expected, actual);
    }

    private void testNewLineEnclosed(SqlExpression expression, String value) {

        String actual = expression.enclose().getText();
        String expected = CommonUtils.enclose(value, NEWLINE_ENCLOSE_START, NEWLINE_ENCLOSE_END);
        assertEquals(expected, actual);
    }
}