package org.raidar.app.sql.impl.utils;

import org.junit.Test;
import org.raidar.app.sql.impl.constant.SqlDateTimeConstants;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.raidar.app.sql.impl.constant.SqlConstants.*;
import static org.raidar.app.sql.impl.constant.SqlDateTimeConstants.SQL_DATETIME_NOW;
import static org.raidar.app.sql.impl.utils.SqlDateTimeUtils.*;
import static org.raidar.app.sql.impl.utils.SqlUtils.escapeValue;

public class SqlDateTimeUtilsTest {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

    private static final LocalDate TEST_DATE = LocalDate.of(2020, 2, 2);
    private static final String TEST_DATE_STRING = "2020-02-02";

    private static final LocalTime TEST_TIME = LocalTime.of(1, 0, 10);
    private static final LocalDateTime TEST_DATE_TIME = LocalDateTime.of(TEST_DATE, TEST_TIME);
    private static final String TEST_DATE_TIME_STRING = "2020-02-02 01:00:10";

    private static final String TEST_SQL_FORMAT = "format";
    private static final String TEST_SQL_DATE = String.format(TO_DATE_FORMAT,
            escapeValue(TEST_DATE_STRING), escapeValue(TEST_SQL_FORMAT)
    );
    private static final String TEST_SQL_TIMESTAMP = String.format(TO_TIMESTAMP_FORMAT,
            escapeValue(TEST_DATE_TIME_STRING), escapeValue(TEST_SQL_FORMAT)
    );

    @Test
    public void testFormatDate() {

        LocalDate nulled = null;
        assertNull(formatDate(nulled, null));
        assertNull(formatDate(nulled, DATE_FORMATTER));

        assertEquals(TEST_DATE_STRING, formatDate(TEST_DATE, DATE_FORMATTER));
    }

    @Test
    public void testFormatDateTime() {

        LocalDateTime nulled = null;
        assertNull(formatDateTime(nulled, null));
        assertNull(formatDateTime(nulled, DATE_TIME_FORMATTER));

        assertEquals(TEST_DATE_TIME_STRING, formatDateTime(TEST_DATE_TIME, DATE_TIME_FORMATTER));
    }

    @Test
    public void testToDate() {

        assertEquals(NULL_VALUE, toDate(null, TEST_SQL_FORMAT));
        assertEquals(escapeValue(SQL_DATETIME_NOW), toDate(SQL_DATETIME_NOW, TEST_SQL_FORMAT));

        assertEquals(TEST_SQL_DATE, toDate(TEST_DATE_STRING, TEST_SQL_FORMAT));
    }

    @Test
    public void testToTimestamp() {

        assertEquals(NULL_VALUE, toTimestamp(null, TEST_SQL_FORMAT));
        assertEquals(escapeValue(SQL_DATETIME_NOW), toTimestamp(SQL_DATETIME_NOW, TEST_SQL_FORMAT));

        assertEquals(TEST_SQL_TIMESTAMP, toTimestamp(TEST_DATE_TIME_STRING, TEST_SQL_FORMAT));
    }

    @Test
    public void testToTimestampWithTimeZone() {

        assertEquals(NULL_VALUE, toTimestampWithTimeZone(null, TEST_SQL_FORMAT));
        assertEquals(escapeValue(SQL_DATETIME_NOW) + CAST_OPERATOR + TIMESTAMP_WITH_TIME_ZONE,
                toTimestampWithTimeZone(SQL_DATETIME_NOW, TEST_SQL_FORMAT));

        assertEquals(TEST_SQL_TIMESTAMP + CAST_OPERATOR + TIMESTAMP_WITH_TIME_ZONE,
                toTimestampWithTimeZone(TEST_DATE_TIME_STRING, TEST_SQL_FORMAT));
    }

    @Test
    public void testToTimestampWithoutTimeZone() {

        assertEquals(NULL_VALUE, toTimestampWithoutTimeZone(null, TEST_SQL_FORMAT));
        assertEquals(escapeValue(SQL_DATETIME_NOW) + CAST_OPERATOR + TIMESTAMP_WITHOUT_TIME_ZONE,
                toTimestampWithoutTimeZone(SQL_DATETIME_NOW, TEST_SQL_FORMAT));

        assertEquals(TEST_SQL_TIMESTAMP + CAST_OPERATOR + TIMESTAMP_WITHOUT_TIME_ZONE,
                toTimestampWithoutTimeZone(TEST_DATE_TIME_STRING, TEST_SQL_FORMAT));
    }
}