package org.raidar.app.sql.impl.builder;

import org.junit.Assert;
import org.junit.Test;
import org.raidar.app.sql.SqlBaseTest;

import static org.junit.Assert.*;

public class SqlParameterListTest extends SqlBaseTest {

    private static final SqlParameterList EMPTY_PARAMETER_LIST = new SqlParameterList();

    private static final String SINGLE_PARAM_NAME = "str";
    private static final String SINGLE_PARAM_VALUE = "string";

    @Test
    public void testEmpty() {

        SqlParameterList current = new SqlParameterList();
        assertEmpty(current);

        current.clear();
        assertEmpty(current);
    }

    @Test
    public void testClone() {

        SqlParameterList nullClone = new SqlParameterList(null);
        assertEmpty(nullClone);

        SqlParameterList emptyClone = new SqlParameterList(EMPTY_PARAMETER_LIST);
        assertEmpty(emptyClone);
    }

    @Test
    public void testAddNameValue() {

        SqlParameterList current = new SqlParameterList();
        current.add(SINGLE_PARAM_NAME, SINGLE_PARAM_VALUE);
        assertSize(current, 1);
        assertNull(current.getValue("null"));
        assertEquals(SINGLE_PARAM_VALUE, current.getValue(SINGLE_PARAM_NAME));

        current.clear();
        assertEmpty(current);
    }

    @Test
    public void testAddNameValueFailed() {

        SqlParameterList current = new SqlParameterList();
        try {
            current.add(null, "string");
            fail(getFailedMessage(IllegalArgumentException.class));

        } catch (RuntimeException e) {

            assertEquals(IllegalArgumentException.class, e.getClass());
            assertNotNull(getExceptionMessage(e));
        }
    }

    @Test
    public void testAddParam() {

        SqlParameterList expected = createSingleParamList();

        SqlParameterList current = new SqlParameterList();
        current.add(new SqlParameter(SINGLE_PARAM_NAME, SINGLE_PARAM_VALUE));
        assertObjects(Assert::assertEquals, expected, current);

        SqlParameterList clone = new SqlParameterList(current);
        assertObjects(Assert::assertEquals, current, clone);
        assertNotSame(current.get().get(0), clone.get().get(0));

        current.clear();
        assertEmpty(current);
    }

    @Test
    public void testSpecialEquals() {

        SqlParameterList current = new SqlParameterList();
        assertSpecialEquals(current);
    }

    private SqlParameterList createSingleParamList() {

        SqlParameterList result = new SqlParameterList();
        result.add(SINGLE_PARAM_NAME, SINGLE_PARAM_VALUE);

        return result;
    }

    private void assertEmpty(SqlParameterList current) {

        assertNotNull(current.get());
        assertTrue(current.isEmpty());
        assertEquals(EMPTY_PARAMETER_LIST, current);
    }

    private void assertSize(SqlParameterList current, int size) {

        assertNotNull(current.get());
        assertEquals(size, current.get().size());
    }
}