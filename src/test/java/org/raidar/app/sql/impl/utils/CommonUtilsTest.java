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

        assertEquals("<>", enclose("", "<", ">"));
        assertEquals("<a>", enclose("a", "<", ">"));
        assertEquals("<1>", enclose("1", "<", ">"));
    }
}