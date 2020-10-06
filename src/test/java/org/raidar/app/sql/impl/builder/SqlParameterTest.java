package org.raidar.app.sql.impl.builder;

import org.junit.Assert;
import org.junit.Test;
import org.raidar.app.sql.SqlBaseTest;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class SqlParameterTest extends SqlBaseTest {

    private static final Map<String, List<Serializable>> PARAM_NAME_VALUES_MAP = createParamNameValuesMap();

    private static Map<String, List<Serializable>> createParamNameValuesMap() {

        Map<String, List<Serializable>> result = new HashMap<>(7);
        result.put("str", List.of("string value", "other value"));
        result.put("int", List.of(1, 10));
        result.put("long", List.of(1L, 10L));
        result.put("bool", List.of(true, false));
        result.put("BigInt", List.of(BigInteger.ONE, BigInteger.TEN));
        result.put("BigDec", List.of(BigDecimal.ONE, BigDecimal.TEN));

        return result;
    }

    @Test
    public void testClass() {

        PARAM_NAME_VALUES_MAP.forEach(this::testClass);
    }

    private void testClass(String name, List<Serializable> list) {

        testClass(name, null, list.get(0));
        testClass(name, list.get(0), list.get(1));
    }

    private void testClass(String name, Serializable value, Serializable other) {

        SqlParameter current = new SqlParameter(name, value);
        assertNotNull(current);
        assertEquals(name, current.getName());
        assertEquals(value, current.getValue());
        assertTrue(current.isNameEquals(name));

        SqlParameter same = new SqlParameter(name, value);
        assertObjects(Assert::assertEquals, current, same);
        assertTrue(same.isNameEquals(current));

        SqlParameter cloned = new SqlParameter(current);
        assertObjects(Assert::assertEquals, current, cloned);

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

    @Test
    public void testClassFailed() {

        testClassFailed(null, "null", IllegalArgumentException.class);
        testClassFailed(null, 0, IllegalArgumentException.class);
        testClassFailed(null, BigInteger.ZERO, IllegalArgumentException.class);
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

    @Test
    public void testCloneFailed() {

        try {
            new SqlParameter(null);
            fail(getFailedMessage(IllegalArgumentException.class));

        } catch (RuntimeException e) {

            assertEquals(IllegalArgumentException.class, e.getClass());
            assertNotNull(getExceptionMessage(e));
        }
    }

    @Test
    public void testSpecialEquals() {

        SqlParameter current = new SqlParameter("text", "text");
        assertSpecialEquals(current);
    }

    @Test
    public void testCompareTo() {

        PARAM_NAME_VALUES_MAP.forEach(this::testCompareTo);
    }

    private void testCompareTo(String name, List<Serializable> list) {

        Serializable value = list.get(0);
        SqlParameter current = new SqlParameter(name, value);

        SqlParameter similar = new SqlParameter(name, value);
        assertEquals(0, current.compareTo(similar));

        SqlParameter previous = new SqlParameter("A" + name.substring(1), value);
        assertTrue(current.compareTo(previous) > 0);

        previous = new SqlParameter("z" + name.substring(1), value);
        assertTrue(current.compareTo(previous) < 0);

        previous = new SqlParameter("A " + name, value);
        assertTrue(current.compareTo(previous) > 0);

        SqlParameter following = new SqlParameter(name.substring(0, name.length() - 1) + "a", value);
        assertTrue(current.compareTo(following) > 0);

        following = new SqlParameter(name.substring(0, name.length() - 1) + "z", value);
        assertTrue(current.compareTo(following) < 0);

        following = new SqlParameter(name + " z", value);
        assertTrue(current.compareTo(following) < 0);
    }

    private SqlParameter copy(SqlParameter source) {

        if (source == null)
            return null;

        return new SqlParameter(source.getName(), source.getValue());
    }
}