package org.raidar.app.sql.impl.builder;

import org.junit.Assert;
import org.junit.Test;
import org.raidar.app.sql.SqlBaseTest;
import org.raidar.app.sql.impl.utils.CommonUtils;
import org.raidar.app.sql.impl.utils.SqlUtils;
import org.raidar.app.sql.test.model.SqlTestLiteral;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.raidar.app.sql.impl.constant.SqlConstants.*;

public class SqlLiteralTest extends SqlBaseTest {

    private static final List<String> EMPTY_VALUES = createEmptyValues();

    @Test
    public void testNullMapper() {

        SqlLiteral literal = new SqlLiteral();
        SqlLiteral nullLiteral = new SqlLiteral(null);
        assertObjects(Assert::assertEquals, literal, nullLiteral);
    }

    @Test
    public void testSpecialEquals() {

        SqlLiteral literal = new SqlLiteral();
        assertSpecialEquals(literal);
    }

    @Test
    public void testEmpty() {

        SqlLiteral literal = new SqlLiteral();
        assertEmpty(literal.getText());
        assertTrue(literal.isEmpty());

        try {
            literal.enclose();

            fail(getFailedMessage(IllegalArgumentException.class));

        } catch (RuntimeException e) {

            assertEquals(IllegalArgumentException.class, e.getClass());
            assertNotNull(getExceptionMessage(e));
        }
    }

    @Test
    public void testNihil() {

        SqlLiteral literal = new SqlLiteral().nihil();
        assertNotEmpty(literal.getText());

        assertEquals(NULL_VALUE, literal.getText());
        testDefaultEnclosed(literal, NULL_VALUE);
    }

    @Test
    public void testField() {

        final String fieldName = "table.column";
        SqlLiteral literal = new SqlLiteral().field(fieldName);
        assertNotEmpty(literal.getText());

        assertEquals(fieldName, literal.getText());
        testDefaultEnclosed(literal, fieldName);

        EMPTY_VALUES.forEach(this::testFieldWhenEmpty);
    }

    private void testFieldWhenEmpty(String name) {

        testThrowing(IllegalArgumentException.class,
                () -> new SqlLiteral().field(name)
        );
    }

    @Test
    public void testParam() {

        final String name = "parameterName";
        SqlLiteral literal = new SqlLiteral().param(name);
        assertNotEmpty(literal.getText());

        String expected = BIND_PREFIX + name;
        assertEquals(expected, literal.getText());
        testDefaultEnclosed(literal, expected);

        EMPTY_VALUES.forEach(this::testParamWhenEmpty);
    }

    private void testParamWhenEmpty(String name) {

        testThrowing(IllegalArgumentException.class,
                () -> new SqlLiteral().param(name)
        );
    }

    @Test
    public void testValue() {

        final String value = "parameterValue";
        SqlLiteral literal = new SqlLiteral().value(value);
        assertNotEmpty(literal.getText());

        String expected = SqlUtils.escapeValue(value);
        assertEquals(expected, literal.getText());
        testDefaultEnclosed(literal, expected);
    }

    @Test
    public void testLiteral() {

        EMPTY_VALUES.forEach(this::testLiteralWhenEmpty);

        SqlLiteral literal = new SqlLiteral().literal("field");

        testThrowing(IllegalStateException.class,
                () -> literal.literal("value")
        );
    }

    private void testLiteralWhenEmpty(String argument) {

        testThrowing(IllegalArgumentException.class,
                () -> new SqlTestLiteral().literal(argument)
        );
    }

    private void testDefaultEnclosed(SqlLiteral literal, String value) {

        String actual = literal.enclose().getText();
        String expected = CommonUtils.enclose(value, DEFAULT_ENCLOSE_START, DEFAULT_ENCLOSE_END);
        assertEquals(expected, actual);
    }

    private void testNewLineEnclosed(SqlLiteral literal, String value) {

        String actual = literal.enclose().getText();
        String expected = CommonUtils.enclose(value, NEWLINE_ENCLOSE_START, NEWLINE_ENCLOSE_END);
        assertEquals(expected, actual);
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