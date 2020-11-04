package org.raidar.app.sql.impl.provider;

import org.junit.Test;
import org.raidar.app.sql.SqlBaseTest;
import org.raidar.app.sql.impl.builder.SqlParameter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import static org.junit.Assert.*;

public class SqlParameterMapperTest extends SqlBaseTest {

    private static final GregorianCalendar CALENDAR = createTestCalendar();

    private static final String JAVA_DATE_FORMAT = "yyyy-MM-dd";
    private static final String SQL_DATE_FORMAT = "YYYY-MM-DD";

    private static final String JAVA_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String SQL_DATETIME_FORMAT = "YYYY-MM-DD HH24:MI:SS";

    private final SqlParameterMapper mapper = new SqlParameterMapper();

    @Test
    public void testParamToString() {

        assertEquals("null", mapper.toString(new SqlParameter("null", null)));
        assertEquals("'string'", mapper.toString(new SqlParameter("str", "string")));
    }

    @Test
    public void testValueToString() {

        assertEquals("null", mapper.toString(null, null));
        assertEquals("'string'", mapper.toString(null, "string"));
    }

    @Test
    public void testValueToStringWhenNumber() {

        assertEquals("1", mapper.toString(null, 1));
        assertEquals("1", mapper.toString(null, 1L));
        assertEquals("1", mapper.toString(null, BigInteger.ONE));

        assertEquals("1.1", mapper.toString(null, 1.1));
        assertEquals("1.1", mapper.toString(null, BigDecimal.valueOf(1.1)));
    }

    @Test
    public void testValueToStringWhenBoolean() {

        assertEquals("true", mapper.toString(null, true));
        assertEquals("false", mapper.toString(null, false));
    }

    @Test
    public void testValueToStringWhenLocalDate() {

        testValueToStringWhenLocalDate(mapper);

        SqlParameterMapper customMapper = getCustomMapper();
        testValueToStringWhenLocalDate(customMapper);
    }

    private void testValueToStringWhenLocalDate(SqlParameterMapper mapper) {

        LocalDate calendarDate = CALENDAR.toZonedDateTime().toLocalDate();

        assertEquals(JAVA_DATE_FORMAT, mapper.getDateFormat());
        assertEquals(SQL_DATE_FORMAT, mapper.getSqlDateFormat());

        String expected = "to_date('2013-12-11', '" + SQL_DATE_FORMAT + "')";
        assertEquals(expected, mapper.toString(null, calendarDate));

        LocalDate parsedDate = LocalDate.parse("2013-12-11",
                DateTimeFormatter.ofPattern(JAVA_DATE_FORMAT));
        assertEquals(expected, mapper.toString(null, parsedDate));
    }

    @Test
    public void testValueToStringWhenLocalDateTime() {

        testValueToStringWhenLocalDateTime(mapper);

        SqlParameterMapper customMapper = getCustomMapper();
        testValueToStringWhenLocalDateTime(customMapper);
    }

    private void testValueToStringWhenLocalDateTime(SqlParameterMapper mapper) {

        LocalDateTime calendarDateTime = CALENDAR.toZonedDateTime().toLocalDateTime();

        assertEquals(JAVA_DATETIME_FORMAT, mapper.getDateTimeFormat());
        assertEquals(SQL_DATETIME_FORMAT, mapper.getSqlDateTimeFormat());

        assertFalse(mapper.getUseTimeZone());

        String expected = "to_timestamp('2013-12-11 10:09:08', '" +
                SQL_DATETIME_FORMAT + "')\\:\\:timestamp without time zone";
        assertEquals(expected, mapper.toString(null, calendarDateTime));

        LocalDateTime parsedDateTime = LocalDateTime.parse("2013-12-11 10:09:08",
                DateTimeFormatter.ofPattern(JAVA_DATETIME_FORMAT));
        assertEquals(expected, mapper.toString(null, parsedDateTime));
    }

    @Test
    public void testValueToStringWhenLocalDateTimeWithTimeZone() {

        LocalDateTime calendarDateTime = CALENDAR.toZonedDateTime().toLocalDateTime();

        final SqlParameterMapper mapper = getTimeZoneUsedMapper();
        assertTrue(mapper.getUseTimeZone());

        String expected = "to_timestamp('2013-12-11 10:09:08', '" +
                SQL_DATETIME_FORMAT + "')\\:\\:timestamp with time zone";
        assertEquals(expected, mapper.toString(null, calendarDateTime));

        LocalDateTime parsedDateTime = LocalDateTime.parse("2013-12-11 10:09:08",
                DateTimeFormatter.ofPattern(JAVA_DATETIME_FORMAT));
        assertEquals(expected, mapper.toString(null, parsedDateTime));
    }

    private SqlParameterMapper getCustomMapper() {

        SqlParameterMapper mapper = new SqlParameterMapper();
        mapper.setDateFormat(JAVA_DATE_FORMAT, SQL_DATE_FORMAT);
        mapper.setDateTimeFormat(JAVA_DATETIME_FORMAT, SQL_DATETIME_FORMAT);

        return mapper;
    }

    private SqlParameterMapper getTimeZoneUsedMapper() {

        SqlParameterMapper mapper = new SqlParameterMapper();
        mapper.setUseTimeZone(true);

        return mapper;
    }

    private static GregorianCalendar createTestCalendar() {

        GregorianCalendar calendar = new GregorianCalendar(2013, Calendar.DECEMBER, 11, 10, 9, 8);
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));

        return calendar;
    }
}