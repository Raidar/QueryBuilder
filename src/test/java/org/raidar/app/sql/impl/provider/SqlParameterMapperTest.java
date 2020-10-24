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

import static org.junit.Assert.assertEquals;

public class SqlParameterMapperTest extends SqlBaseTest {

    private static final SqlParameterMapper mapper = new SqlParameterMapper();

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

        GregorianCalendar calendar = new GregorianCalendar(2013, Calendar.DECEMBER, 11, 10, 9, 8);
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        LocalDate calendarDate = calendar.toZonedDateTime().toLocalDate();

        String expected = "to_date('2013-12-11', 'YYYY-MM-DD')";
        assertEquals(expected, mapper.toString(null, calendarDate));

        LocalDate parsedDate = LocalDate.parse("2013-12-11", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        assertEquals(expected, mapper.toString(null, parsedDate));
    }

    @Test
    public void testValueToStringWhenLocalDateTime() {

        GregorianCalendar calendar = new GregorianCalendar(2013, Calendar.DECEMBER, 11, 10, 9, 8);
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        LocalDateTime calendarDateTime = calendar.toZonedDateTime().toLocalDateTime();

        String expected = "to_timestamp('2013-12-11 10:09:08', 'YYYY-MM-DD HH24:MI:SS')\\:\\:timestamp without time zone";
        assertEquals(expected, mapper.toString(null, calendarDateTime));

        LocalDateTime parsedDateTime = LocalDateTime.parse("2013-12-11 10:09:08", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        assertEquals(expected, mapper.toString(null, parsedDateTime));
    }

    @Test
    public void testValueToStringWhenLocalDateTimeWithTimeZone() {

        final SqlParameterMapper mapper = new SqlParameterMapper();
        mapper.setUseTimeZone(true);

        GregorianCalendar calendar = new GregorianCalendar(2013, Calendar.DECEMBER, 11, 10, 9, 8);
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        LocalDateTime calendarDateTime = calendar.toZonedDateTime().toLocalDateTime();

        String expected = "to_timestamp('2013-12-11 10:09:08', 'YYYY-MM-DD HH24:MI:SS')\\:\\:timestamp with time zone";
        assertEquals(expected, mapper.toString(null, calendarDateTime));

        LocalDateTime parsedDateTime = LocalDateTime.parse("2013-12-11 10:09:08", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        assertEquals(expected, mapper.toString(null, parsedDateTime));
    }
}