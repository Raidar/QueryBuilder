package org.raidar.app.sql.builder;

import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.Assert.*;

public class SqlParameterTest extends SqlBaseTest {

    @Test
    public void testClass() {

        testClass("null", null, "null");
        testClass("str", "string value", "other value");
        testClass("int", 1, 2);
        testClass("long", 1L, 2L);
        testClass("bool", true, false);
        testClass("bigint", BigInteger.ONE, BigInteger.TWO);
        testClass("bigdec", BigDecimal.ONE, BigDecimal.TEN);
    }

    @Test
    public void testClassFailed() {

        testClassFailed(null, "null", IllegalArgumentException.class);
        testClassFailed(null, 1, IllegalArgumentException.class);
    }

    @Test
    public void testSpecialEquals() {

        SqlParameter current = new SqlParameter("text", "text");
        assertSpecialEquals(current);
    }

    private void testClass(String name, Serializable value, Serializable other) {

        SqlParameter current = new SqlParameter(name, value);
        assertNotNull(current);
        assertEquals(name, current.getName());
        assertEquals(value, current.getValue());

        SqlParameter same = new SqlParameter(name, value);
        assertObjects(Assert::assertEquals, current, same);

        SqlParameter copied = copy(current);
        assertObjects(Assert::assertEquals, current, copied);

        SqlParameter otherNamed = new SqlParameter(name + "_other", value);
        assertObjects(Assert::assertNotEquals, current, otherNamed);

        SqlParameter otherValued = new SqlParameter(name, other);
        assertObjects(Assert::assertNotEquals, current, otherValued);
        assertObjects(Assert::assertNotEquals, otherNamed, otherValued);

        SqlParameter otherTotal = new SqlParameter(otherNamed.getName(), otherValued.getValue());
        assertObjects(Assert::assertNotEquals, current, otherTotal);
        assertObjects(Assert::assertNotEquals, otherNamed, otherTotal);
        assertObjects(Assert::assertNotEquals, otherValued, otherTotal);

        SqlParameter otherText = new SqlParameter(name, "other");
        assertObjects(Assert::assertNotEquals, current, otherText);
    }

    @SuppressWarnings("SameParameterValue")
    private void testClassFailed(String name, Serializable value, Class<?> expectedClass) {

        try {
            new SqlParameter(name, value);
            fail(getFailedMessage(expectedClass));

        } catch (RuntimeException e) {

            assertEquals(expectedClass, e.getClass());
            assertNotNull(getExceptionMessage(e));
        }
    }

    private SqlParameter copy(SqlParameter source) {

        if (source == null)
            return null;

        return new SqlParameter(source.getName(), source.getValue());
    }
}