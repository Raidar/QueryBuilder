package org.raidar.app.sql.impl;

import org.junit.Test;
import org.raidar.app.sql.SqlBaseTest;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.Assert.*;
import static org.raidar.app.sql.impl.SqlUtils.isBlank;
import static org.raidar.app.sql.impl.SqlUtils.isEmpty;

public class SqlUtilsTest extends SqlBaseTest {

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
}