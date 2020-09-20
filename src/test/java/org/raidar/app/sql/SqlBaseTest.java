package org.raidar.app.sql;

import org.junit.Assert;

import java.math.BigInteger;
import java.util.function.BiConsumer;

import static org.junit.Assert.assertNotNull;

public class SqlBaseTest {

    public static void assertObjects(BiConsumer<Object, Object> doAssert, Object current, Object actual) {

        doAssert.accept(current, actual);
        
        if (current != null && actual != null) {

            doAssert.accept(current.hashCode(), actual.hashCode());
            doAssert.accept(current.toString(), actual.toString());
        }
    }

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
