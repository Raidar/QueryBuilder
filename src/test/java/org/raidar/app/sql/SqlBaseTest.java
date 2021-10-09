package org.raidar.app.sql;

import org.junit.Assert;

import java.math.BigInteger;
import java.util.*;
import java.util.function.BiConsumer;

import static org.junit.Assert.*;

public class SqlBaseTest {

    private static final String ERROR_EXPECTED = " error expected";

    /** Check string for empty. */
    public void assertEmpty(String string) {
        assertTrue(string == null || string.length() == 0);
    }

    /** Check string for not empty. */
    public void assertNotEmpty(String string) {
        assertFalse(string == null || string.length() == 0);
    }

    /** Check collection for empty. */
    public <T> void assertEmpty(Collection<T> collection) {
        assertTrue(collection == null || collection.isEmpty());
    }

    /** Check map for empty. */
    public <K, V> void assertEmpty(Map<K, V> map) {
        assertTrue(map == null || map.isEmpty());
    }

    /** Check collection for not empty. */
    public <T> void assertNotEmpty(Collection<T> collection) {
        assertFalse(collection == null || collection.isEmpty());
    }

    /** Check map for not empty. */
    public <K, V> void assertNotEmpty(Map<K, V> map) {
        assertFalse(map == null || map.isEmpty());
    }

    /** Check objects using `equals`, `hashCode`, and `toString`. */
    public <T> void assertObjects(BiConsumer<Object, Object> doAssert, T current, T actual) {

        doAssert.accept(current, actual);

        if (current == null || actual == null)
            return;

        if (isOverridingHashCode(current)) {
            doAssert.accept(current.hashCode(), actual.hashCode());
        }

        if (isOverridingToString(current)) {
            doAssert.accept(current.toString(), actual.toString());
        }
    }

    /** Check `hashCode` overriding in class. */
    private boolean isOverridingHashCode(Object o) {

        return o.hashCode() != System.identityHashCode(o);
    }

    /** Check `toString` overriding in class. */
    private boolean isOverridingToString(Object o) {

        String actual = o.toString();
        if (actual == null || actual.length() == 0)
            return true;

        String expected = o.getClass().getName() + "@" +
                Integer.toHexString(o.hashCode());

        return !actual.equals(expected);
    }

    /** Check objects by special branches of `equals`. */
    public void assertSpecialEquals(Object current) {

        assertNotNull(current);
        assertObjects(Assert::assertEquals, current, current);
        assertObjects(Assert::assertNotEquals, current, null);

        Object other = (!BigInteger.ZERO.equals(current)) ? BigInteger.ZERO : BigInteger.ONE;
        assertObjects(Assert::assertNotEquals, current, other);
    }

    /** Check collections for equality. */
    public <T> void assertCollectionEquals(Collection<T> expected, Collection<T> actual) {

        if (expected == null || expected.isEmpty()) {
            assertEmpty(actual);
            return;
        }

        assertEquals(expected.size(), actual.size());
        expected.forEach(item -> assertItemEquals(item, expected, actual));
    }

    /** Check collections for equality by item. */
    public <T> void assertItemEquals(T item, Collection<T> expected, Collection<T> actual) {

        if (item == null) {
            assertEquals(expected.stream().filter(Objects::isNull).count(),
                    actual.stream().filter(Objects::isNull).count()
            );
            return;
        }

        assertEquals(expected.stream().filter(item::equals).count(),
                actual.stream().filter(item::equals).count()
        );
    }

    /** Check maps for equality. */
    public <K, V> void assertMapEquals(Map<K, V> expected, Map<K, V> actual) {

        if (expected == null || expected.isEmpty()) {
            assertEmpty(actual);
            return;
        }

        assertEquals(expected.size(), actual.size());
        expected.forEach((k, v) -> assertItemEquals(k, v, actual));
    }

    /** Check maps for equality by item. */
    public <K, V> void assertItemEquals(K key, V value, Map<K, V> map) {

        assertTrue(map.containsKey(key));
        assertEquals(value, map.get(key));
    }

    /** Get expected class message to use in `fail`. */
    public String getFailedMessage(Class<?> expected) {
        return expected.getSimpleName() + ERROR_EXPECTED;
    }

    /** Get exception message to use in `assert`. */
    public String getExceptionMessage(Exception exception) {
        return (exception != null) ? exception.getMessage() : null;
    }

    /** Check runnable execution for throwing. */
    public void assertThrowing(Class<?> expected, Runnable runnable) {

        try {
            runnable.run();
            fail(getFailedMessage(expected));

        } catch (RuntimeException e) {

            assertEquals(expected, e.getClass());
            assertNotNull(getExceptionMessage(e));
        }
    }

    /** Check method execution for success. */
    public void assertSuccess(MethodExecutor executor) {
        try {
            executor.execute();

        } catch (RuntimeException e) {
            fail(getExceptionMessage(e));
        }
    }

    /** Check method execution for failure with single message. */
    public void assertFailure(MethodExecutor executor, Class<?> expectedClass) {
        try {
            executor.execute();
            fail(getFailedMessage(expectedClass));

        } catch (RuntimeException e) {
            assertEquals(expectedClass, e.getClass());
            assertNotNull(getExceptionMessage(e));
        }
    }

    /** Check method execution for failure with single message. */
    public void assertFailure(MethodExecutor executor, Class<?> expectedClass, String expectedMessage) {
        try {
            executor.execute();
            fail(getFailedMessage(expectedClass));

        } catch (RuntimeException e) {
            assertEquals(expectedClass, e.getClass());
            assertEquals(expectedMessage, getExceptionMessage(e));
        }
    }

    public interface MethodExecutor {
        void execute();
    }
}
