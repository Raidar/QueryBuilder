package org.raidar.app.sql;

import org.junit.Assert;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SqlBaseTest {

    /** Check list for empty. */
    public static <T> void assertEmpty(List<T> list) {
        assertEquals(Collections.<T>emptyList(), list);
    }

    /** Check map for empty. */
    public static <K, V> void assertEmpty(Map<K, V> map) {
        assertEquals(Collections.<K, V>emptyMap(), map);
    }

    /** Check objects using `equals`, `hashCode`, and `toString`. */
    public static <T> void assertObjects(BiConsumer<Object, Object> doAssert, T current, T actual) {

        doAssert.accept(current, actual);
        
        if (current != null && actual != null) {

            doAssert.accept(current.hashCode(), actual.hashCode());
            doAssert.accept(current.toString(), actual.toString());
        }
    }

    /** Check objects by special branches of `equals`. */
    public static void assertSpecialEquals(Object current) {

        assertNotNull(current);
        assertObjects(Assert::assertEquals, current, current);
        assertObjects(Assert::assertNotEquals, current, null);

        Object other = (!BigInteger.ZERO.equals(current)) ? BigInteger.ZERO : BigInteger.ONE;
        assertObjects(Assert::assertNotEquals, current, other);
    }

    public static String getFailedMessage(Class<?> expected) {
        return expected.getSimpleName() + " error expected";
    }

    public static String getExceptionMessage(Exception exception) {
        return (exception != null) ? exception.getMessage() : null;
    }
}
