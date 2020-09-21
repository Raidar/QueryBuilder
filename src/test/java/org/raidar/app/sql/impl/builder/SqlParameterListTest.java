package org.raidar.app.sql.impl.builder;

import org.junit.Assert;
import org.junit.Test;
import org.raidar.app.sql.SqlBaseTest;
import org.raidar.app.sql.api.builder.SqlParam;

import java.io.Serializable;
import java.util.*;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class SqlParameterListTest extends SqlBaseTest {

    private static final SqlParameterList EMPTY_PARAMETER_LIST = new SqlParameterList();

    private static final String SINGLE_PARAM_NAME = "str";
    private static final String SINGLE_PARAM_VALUE = "string";
    private static final String FIRST_PARAM_NAME = "Int";
    private static final Integer FIRST_PARAM_VALUE = 3;
    private static final String SECOND_PARAM_NAME = "Dbl";
    private static final Double SECOND_PARAM_VALUE = 3.0;

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
        assertTrue(current.contains(SINGLE_PARAM_NAME));
        assertEquals(SINGLE_PARAM_VALUE, current.getValue(SINGLE_PARAM_NAME));

        current.clear();
        assertEmpty(current);
        assertFalse(current.contains(SINGLE_PARAM_NAME));
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
        current.add(createSingleParam());
        assertTrue(current.contains(SINGLE_PARAM_NAME));
        assertObjects(Assert::assertEquals, expected, current);

        SqlParameterList clone = new SqlParameterList(current);
        assertObjects(Assert::assertEquals, current, clone);
        assertNotSame(current.get(SINGLE_PARAM_NAME), clone.get(SINGLE_PARAM_NAME));

        current.clear();
        assertEmpty(current);
    }

    @Test
    public void testAddParamFailed() {

        SqlParameterList current = new SqlParameterList();
        try {
            SqlParameter nullParameter = null;
            current.add(nullParameter);
            fail(getFailedMessage(IllegalArgumentException.class));

        } catch (RuntimeException e) {

            assertEquals(IllegalArgumentException.class, e.getClass());
            assertNotNull(getExceptionMessage(e));
        }
    }

    @Test
    public void testAddParams() {

        SqlParameterList expected = createDoubleParamList();

        SqlParameterList current = new SqlParameterList();
        current.add(expected);
        assertTrue(current.contains(FIRST_PARAM_NAME));
        assertTrue(current.contains(SECOND_PARAM_NAME));
        assertObjects(Assert::assertEquals, expected, current);

        SqlParameterList clone = new SqlParameterList(current);
        assertObjects(Assert::assertEquals, current, clone);
        assertNotSame(current.get(FIRST_PARAM_NAME), clone.get(FIRST_PARAM_NAME));
        assertNotSame(current.get(SECOND_PARAM_NAME), clone.get(SECOND_PARAM_NAME));

        current.clear();
        assertEmpty(current);
    }

    @Test
    public void testAddEmptyParams() {

        SqlParameterList current = new SqlParameterList();

        SqlParameterList nullParams = null;
        current.add(nullParams);
        assertEmpty(current);

        SqlParameterList emptyParams = new SqlParameterList();
        current.add(emptyParams);
        assertEmpty(current);
    }

    @Test
    public void testProcessCollection() {

        SqlParameterList current = new SqlParameterList();

        List<SqlParam> list = null;
        current.add(list);
        assertEmpty(current);

        list = new ArrayList<>();
        current.add(list);
        assertEmpty(current);

        list = List.of(createSingleParam());
        current.add(list);
        assertSize(current, 1);
        assertTrue(current.contains(SINGLE_PARAM_NAME));
        assertEquals(list, current.get());

        list = createDoubleParams();
        current.set(list);
        assertSize(current, 2);
        assertEquals(list, current.get());
    }

    @Test
    public void testProcessMap() {

        SqlParameterList current = new SqlParameterList();

        current.addMap(null);
        assertEmpty(current);

        List<SqlParam> list = List.of(createSingleParam());
        Map<String, Serializable> map = new HashMap<>();
        current.addMap(map);
        assertEmpty(current);

        map.put(SINGLE_PARAM_NAME, SINGLE_PARAM_VALUE);

        current.addMap(map);
        assertSize(current, 1);
        assertTrue(current.contains(SINGLE_PARAM_NAME));
        assertEquals(list, current.get());
        assertEquals(map, current.getMap());

        list = createDoubleParams();
        map.clear();
        list.forEach(item -> map.put(item.getName(), item.getValue()));

        current.setMap(map);
        assertSize(current, 2);
        assertEquals(list, current.get());
        assertEquals(map, current.getMap());
    }

    @Test
    public void testSpecialEquals() {

        SqlParameterList current = new SqlParameterList();
        assertSpecialEquals(current);
    }

    @Test
    public void testIterator() {

        List<SqlParam> list = createDoubleParams();

        SqlParameterList current = createDoubleParamList();
        current.forEach(actual -> assertContains(list, actual));
    }

    @Test
    public void testStream() {

        List<SqlParam> list = createDoubleParams();

        SqlParameterList current = createDoubleParamList();
        current.stream().forEach(actual -> assertContains(list, actual));
    }

    private SqlParameter createSingleParam() {

        return new SqlParameter(SINGLE_PARAM_NAME, SINGLE_PARAM_VALUE);
    }

    private SqlParameterList createSingleParamList() {

        SqlParameterList result = new SqlParameterList();
        result.add(SINGLE_PARAM_NAME, SINGLE_PARAM_VALUE);

        return result;
    }

    private List<SqlParam> createDoubleParams() {

        return List.of(
                new SqlParameter(FIRST_PARAM_NAME, FIRST_PARAM_VALUE),
                new SqlParameter(SECOND_PARAM_NAME, SECOND_PARAM_VALUE)
        );
    }

    private SqlParameterList createDoubleParamList() {

        SqlParameterList result = new SqlParameterList();
        result.add(FIRST_PARAM_NAME, FIRST_PARAM_VALUE);
        result.add(SECOND_PARAM_NAME, SECOND_PARAM_VALUE);

        return result;
    }

    private void assertEmpty(SqlParameterList current) {

        assertNotNull(current.get());
        assertTrue(current.isEmpty());
        assertEquals(EMPTY_PARAMETER_LIST, current);
    }

    private void assertSize(SqlParameterList current, int size) {

        assertNotNull(current.get());
        assertEquals(size, current.size());
    }

    private void assertContains(List<SqlParam> list, SqlParam actual) {

        assertNotNull(actual);

        SqlParam expected = list.stream().filter(actual::isNameEquals).findFirst().orElse(null);
        assertNotNull(expected);
        assertEquals(expected, actual);
    }
}