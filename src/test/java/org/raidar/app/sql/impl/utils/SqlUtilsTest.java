package org.raidar.app.sql.impl.utils;

import org.junit.Test;
import org.raidar.app.sql.SqlBaseTest;
import org.raidar.app.sql.api.builder.SqlClause;
import org.raidar.app.sql.api.builder.SqlStatement;
import org.raidar.app.sql.impl.builder.SqlCondition;
import org.raidar.app.sql.impl.builder.SqlExpression;
import org.raidar.app.sql.impl.builder.SqlLiteral;
import org.raidar.app.sql.impl.builder.SqlQuery;

import static org.junit.Assert.*;
import static org.raidar.app.sql.impl.utils.SqlUtils.*;

public class SqlUtilsTest extends SqlBaseTest {

    @Test
    public void testEncloseString() {

        testEncloseString("()", "");
        testEncloseString("(text)", "text");
    }

    private void testEncloseString(String expected, String text) {

        assertFalse(isEnclosed(text));

        String actual = enclose(text);
        assertEquals(expected, actual);
        assertTrue(isEnclosed(actual));
    }

    @Test
    public void testEncloseClause() {

        testEncloseClause("(\n\n)", new SqlLiteral(), false);
        testEncloseClause("(\nclause\n)", new SqlLiteral().field("clause"), false);
        testEncloseClause("(\n(true)\n)", new SqlCondition().with(SqlLiteral.TRUE), true);
    }

    private void testEncloseClause(String expected, SqlClause clause, boolean enclosed) {

        assertEquals(enclosed, isEnclosed(clause.getText()));

        String actual = enclose(clause);
        assertEquals(expected, actual);
        assertTrue(isEnclosed(actual));
    }

    @Test
    public void testEncloseBuilder() {

        testEncloseBuilder("()", "");
        testEncloseBuilder("(clause)", "clause");
        testEncloseBuilder("(clause clause)", "clause", " clause");
    }

    private void testEncloseBuilder(String expected, String... strings) {

        StringBuilder builder = new StringBuilder();
        for (String s: strings) {
            builder.append(s);
        }

        assertFalse(isEnclosed(builder));

        StringBuilder actual = enclose(builder);
        assertEquals(expected, actual.toString());
        assertTrue(isEnclosed(actual));
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

    @Test
    public void testIsEmptyClause() {

        SqlClause nulled = null;
        assertTrue(isEmpty(nulled));

        assertTrue(isEmpty(new SqlLiteral()));
        assertTrue(isEmpty(new SqlExpression()));
        assertFalse(isEmpty(new SqlLiteral().field("field")));
    }

    @Test
    public void testIsEmptyStatement() {

        SqlStatement nulled = null;
        assertTrue(isEmpty(nulled));

        assertTrue(isEmpty(new SqlQuery()));
        assertFalse(isEmpty(new SqlCondition().with(SqlLiteral.TRUE)));
    }
}