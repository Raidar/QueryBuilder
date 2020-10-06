package org.raidar.app.sql.impl.provider;

import org.raidar.app.sql.api.builder.SqlParam;
import org.raidar.app.sql.api.provider.SqlParamMapper;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.raidar.app.sql.impl.constant.SqlConstants.NULL_VALUE;
import static org.raidar.app.sql.impl.utils.DateTimeUtils.formatDateTime;
import static org.raidar.app.sql.impl.utils.DateTimeUtils.toTimestampWithoutTimeZone;
import static org.raidar.app.sql.impl.utils.SqlUtils.escapeValue;

public class SqlParameterMapper implements SqlParamMapper {

    // Date format to use in queries.
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    public static final String SQL_DATE_FORMAT = "DD.MM.YYYY";

    // Datetime format to use in queries.
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
    public static final String SQL_TIMESTAMP_FORMAT = "YYYY-MM-DD HH24:MI:SS";

    public SqlParameterMapper() {
        // Nothing to do.
    }

    @Override
    public String toString(SqlParam param) {
        return toString(param.getName(), param.getValue());
    }

    @Override
    public String toString(String name, Serializable value) {

        if (value == null)
            return NULL_VALUE;

        if (value instanceof Number)
            return value.toString();

        if (value instanceof Boolean)
            return value.toString();

        if (value instanceof LocalDateTime)
            return toTimestamp((LocalDateTime) value);

        return escapeValue(value.toString());
    }

    private String toTimestamp(LocalDateTime value) {

        String stringValue = formatDateTime(value, DATETIME_FORMATTER);
        return toTimestampWithoutTimeZone(stringValue, SQL_TIMESTAMP_FORMAT);
    }
}
