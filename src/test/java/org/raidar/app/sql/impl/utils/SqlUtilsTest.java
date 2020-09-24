package org.raidar.app.sql.impl.utils;

import org.junit.Test;
import org.raidar.app.sql.SqlBaseTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.raidar.app.sql.impl.utils.SqlUtils.*;

public class SqlUtilsTest extends SqlBaseTest {

    @Test
    public void testEncloseString() {

        assertEquals("()", enclose(""));
        assertEquals("(clause)", enclose("clause"));
        assertEquals("(operator)", enclose("operator"));
    }

    @Test
    public void testEscapeName() {

        assertNull(escapeName(null));
        assertEquals("", escapeName(""));
        assertEquals("\"name\"", escapeName("name"));
        assertEquals("\"name\"", escapeName("na\"me"));
        assertEquals("\"n.a.m.e\"", escapeName("n.a.m.e"));
    }

    @Test
    public void testEscapeValue() {

        assertNull(escapeValue(null));
        assertEquals("", escapeValue(""));
        assertEquals("'value'", escapeValue("value"));
        assertEquals("'va''lue'", escapeValue("va'lue"));
        assertEquals("'v.a.l.u.e'", escapeValue("v.a.l.u.e"));
    }
}