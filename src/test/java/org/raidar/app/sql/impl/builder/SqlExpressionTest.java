package org.raidar.app.sql.impl.builder;

import org.junit.Assert;
import org.junit.Test;
import org.raidar.app.sql.SqlBaseTest;
import org.raidar.app.sql.impl.utils.CommonUtils;
import org.raidar.app.sql.impl.utils.SqlUtils;
import org.raidar.app.sql.test.model.SqlTestExpression;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.raidar.app.sql.impl.constant.SqlConstants.*;

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

    @Test
    public void testNihil() {

        SqlExpression expression = new SqlExpression().nihil();
        assertNotEmpty(expression.getText());

        assertEquals(NULL_VALUE, expression.getText());
        testDefaultEnclosed(expression, NULL_VALUE);
    }

    @Test
    public void testField() {

        final String fieldName = "table.column";
        SqlExpression expression = new SqlExpression().field(fieldName);
        assertNotEmpty(expression.getText());

        assertEquals(fieldName, expression.getText());
        testDefaultEnclosed(expression, fieldName);

        EMPTY_VALUES.forEach(this::testFieldWhenEmpty);
    }

    private void testFieldWhenEmpty(String name) {

        testThrowing(IllegalArgumentException.class,
                () -> new SqlTestExpression().field(name)
        );
    }

    @Test
    public void testParam() {

        final String name = "parameterName";
        SqlExpression expression = new SqlExpression().param(name);
        assertNotEmpty(expression.getText());

        String expected = BIND_PREFIX + name;
        assertEquals(expected, expression.getText());
        testDefaultEnclosed(expression, expected);

        EMPTY_VALUES.forEach(this::testParamWhenEmpty);
    }

    private void testParamWhenEmpty(String name) {

        testThrowing(IllegalArgumentException.class,
                () -> new SqlTestExpression().param(name)
        );
    }

    @Test
    public void testValue() {

        final String value = "parameterValue";
        SqlExpression expression = new SqlExpression().value(value);
        assertNotEmpty(expression.getText());

        String expected = SqlUtils.escapeValue(value);
        assertEquals(expected, expression.getText());
        testDefaultEnclosed(expression, expected);
    }

    @Test
    public void testLiteral() {

        EMPTY_VALUES.forEach(this::testLiteralWhenEmpty);

        SqlExpression expression = new SqlExpression().literal("field");
        expression.isNull();

        testThrowing(IllegalArgumentException.class,
                () -> expression.literal("value")
        );
    }

    private void testLiteralWhenEmpty(String argument) {

        testThrowing(IllegalArgumentException.class,
                () -> new SqlTestExpression().literal(argument)
        );
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

    private void testThrowing(Class<?> expected, Runnable runnable) {

        try {
            runnable.run();

            fail(getFailedMessage(expected));

        } catch (RuntimeException e) {

            assertEquals(expected, e.getClass());
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