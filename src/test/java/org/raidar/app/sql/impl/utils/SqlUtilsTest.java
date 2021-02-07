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

        assertEquals("()", enclose(""));
        assertEquals("(clause)", enclose("clause"));
        assertEquals("(operator)", enclose("operator"));
    }

    @Test
    public void testEncloseClause() {

        assertEquals("(\n\n)", enclose(new SqlLiteral()));
        assertEquals("(\nclause\n)", enclose(new SqlLiteral().field("clause")));
        assertEquals("(\n(true)\n)", enclose(new SqlCondition().with(SqlLiteral.TRUE)));
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