package org.raidar.app.sql.impl.utils;

import org.junit.Test;
import org.raidar.app.sql.SqlBaseTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static org.junit.Assert.*;
import static org.raidar.app.sql.impl.utils.CommonUtils.*;

public class CommonUtilsTest extends SqlBaseTest {

    private static final String ENCLOSE_PREFIX = "<";
    private static final String ENCLOSE_SUFFIX = ">";

    @Test
    public void testIsEmptyString() {

        assertTrue(isEmpty(""));
        assertFalse(isEmpty(" "));
        assertFalse(isEmpty("abc"));
        assertFalse(isEmpty("abc def"));
        assertFalse(isEmpty("123"));
        assertFalse(isEmpty("."));
    }

    @Test
    public void testIsEmptyBuilder() {

        assertTrue(isEmptyBuilder(null));
        assertTrue(isEmptyBuilder(""));
        assertFalse(isEmptyBuilder(" "));
        assertFalse(isEmptyBuilder("abc"));
        assertFalse(isEmptyBuilder("abc def"));
        assertFalse(isEmptyBuilder("123"));
        assertFalse(isEmptyBuilder("."));
    }

    private boolean isEmptyBuilder(String s) {
        StringBuilder b = (s != null) ? new StringBuilder(s) : null;
        return isEmpty(b);
    }

    @Test
    public void testIsBlankString() {

        assertTrue(isBlank(""));
        assertTrue(isBlank(" "));
        assertFalse(isBlank("abc"));
        assertFalse(isBlank("abc def"));
        assertFalse(isBlank("123"));
        assertFalse(isBlank("."));
    }

    @Test
    public void testIsEmptyCollection() {

        assertTrue(isEmpty(emptyList()));
        assertTrue(isEmpty(new ArrayList<>()));

        List<String> list = List.of("", " ", "a", "1");
        assertFalse(isEmpty(list));

        list = new ArrayList<>(list);
        assertFalse(isEmpty(list));

        list.clear();
        assertTrue(isEmpty(list));
    }

    @Test
    public void testIsEmptyMap() {

        assertTrue(isEmpty(emptyMap()));
        assertTrue(isEmpty(new HashMap<>()));

        Map<String, String> map = new HashMap<>();
        map.put("", null);
        map.put(" ", "---");
        map.put("a", "letter");
        map.put("1", "digit");
        assertFalse(isEmpty(map));

        map = new HashMap<>(map);
        assertFalse(isEmpty(map));

        map.clear();
        assertTrue(isEmpty(map));
    }

    @Test
    public void testEncloseString() {

        testEncloseString("<>", "");
        testEncloseString("<a>", "a");
        testEncloseString("<1>", "1");
    }

    private void testEncloseString(String expected, String s) {

        assertFalse(isEnclosedString(s));

        String actual = enclose(s, ENCLOSE_PREFIX, ENCLOSE_SUFFIX);
        assertEquals(expected, actual);
        assertTrue(isEnclosedString(actual));
    }

    @Test
    public void testIsEnclosedString() {

        assertFalse(isEnclosedString(""));
        assertFalse(isEnclosedString("s"));
        assertFalse(isEnclosedString(ENCLOSE_PREFIX + "s"));
        assertFalse(isEnclosedString("s" + ENCLOSE_SUFFIX));
        assertTrue(isEnclosedString(ENCLOSE_PREFIX + "s" + ENCLOSE_SUFFIX));
    }

    private boolean isEnclosedString(String s) {
        return isEnclosed(s, ENCLOSE_PREFIX, ENCLOSE_SUFFIX);
    }

    @Test
    public void testEncloseBuilder() {

        testEncloseBuilder("<>", "");
        testEncloseBuilder("<a>", "a");
        testEncloseBuilder("<1>", "1");
        testEncloseBuilder("<ab>", "a", "b");
        testEncloseBuilder("<ab>", "a", "", "b");
    }

    private void testEncloseBuilder(String expected, String... strings) {

        StringBuilder builder = new StringBuilder();
        for (String s: strings) {
            builder.append(s);
        }

        assertFalse(isEnclosed(builder, ENCLOSE_PREFIX, ENCLOSE_SUFFIX));

        StringBuilder actual = enclose(builder, ENCLOSE_PREFIX, ENCLOSE_SUFFIX);
        assertEquals(expected, actual.toString());
        assertTrue(isEnclosed(actual, ENCLOSE_PREFIX, ENCLOSE_SUFFIX));
    }

    @Test
    public void testIsEnclosedBuilder() {

        assertFalse(isEnclosedBuilder(""));
        assertFalse(isEnclosedBuilder("s"));
        assertFalse(isEnclosedBuilder(ENCLOSE_PREFIX, "s"));
        assertFalse(isEnclosedBuilder("s", ENCLOSE_SUFFIX));
        assertTrue(isEnclosedBuilder(ENCLOSE_PREFIX, "s", ENCLOSE_SUFFIX));
    }

    private boolean isEnclosedBuilder(String... strings) {

        StringBuilder builder = new StringBuilder();
        for (String s: strings) {
            builder.append(s);
        }

        return isEnclosed(builder, ENCLOSE_PREFIX, ENCLOSE_SUFFIX);
    }
}