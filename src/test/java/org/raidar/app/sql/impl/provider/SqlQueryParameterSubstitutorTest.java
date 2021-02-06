package org.raidar.app.sql.impl.provider;

import org.junit.Test;
import org.raidar.app.sql.SqlBaseTest;
import org.raidar.app.sql.api.builder.SqlStatement;
import org.raidar.app.sql.test.model.SqlTestParamList;
import org.raidar.app.sql.test.model.SqlTestStatement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.raidar.app.sql.impl.constant.SqlConstants.BIND_PREFIX;
import static org.raidar.app.sql.impl.utils.SqlUtils.escapeValue;

public class SqlQueryParameterSubstitutorTest extends SqlBaseTest {

    private static final String SQL_TEXT_FORMAT = "select %s, %s";
    private static final String SQL_PARAM_1_NAME = "param1";
    private static final String SQL_PARAM_2_NAME = "param2";
    private static final String SQL_PARAM_1_VALUE = "one";
    private static final String SQL_PARAM_2_VALUE = "two";

    private static final String SQL_BASIC_TEXT = String.format(SQL_TEXT_FORMAT,
            BIND_PREFIX + SQL_PARAM_1_NAME, BIND_PREFIX + SQL_PARAM_2_NAME);
    private static final String SQL_PARAM_TEXT = String.format(SQL_TEXT_FORMAT,
            escapeValue(SQL_PARAM_1_VALUE), escapeValue(SQL_PARAM_2_VALUE));

    private final SqlQueryParameterSubstitutor substitutor = new SqlQueryParameterSubstitutor();

    @Test
    public void testSubstitute() {

        SqlStatement query = createQuery();
        String result = substitutor.substitute(query);
        assertEquals(SQL_PARAM_TEXT, result);
    }

    @Test
    public void testSubstituteWhenEmpty() {

        SqlStatement query = createEmptyQuery();
        String result = substitutor.substitute(query);
        assertNull(result);
    }

    @Test
    public void testSubstituteWhenTextOnly() {

        SqlStatement query = createTextOnlyQuery();
        String result = substitutor.substitute(query);
        assertEquals(SQL_BASIC_TEXT, result);
    }

    @Test
    public void testSubstituteWhenNullMapper() {

        SqlQueryParameterSubstitutor substitutor = new SqlQueryParameterSubstitutor(null);

        SqlStatement query = createEmptyQuery();
        String result = substitutor.substitute(query);
        assertNull(result);
    }

    private SqlStatement createQuery() {

        SqlTestParamList params = new SqlTestParamList();
        params.add(SQL_PARAM_1_NAME, SQL_PARAM_1_VALUE);
        params.add(SQL_PARAM_2_NAME, SQL_PARAM_2_VALUE);

        SqlTestStatement query = new SqlTestStatement();
        query.add(SQL_BASIC_TEXT).add(params);

        return query;
    }

    private SqlStatement createEmptyQuery() {

        return new SqlTestStatement();
    }

    private SqlStatement createTextOnlyQuery() {

        SqlTestStatement query = new SqlTestStatement();
        query.add(SQL_BASIC_TEXT);

        return query;
    }
}