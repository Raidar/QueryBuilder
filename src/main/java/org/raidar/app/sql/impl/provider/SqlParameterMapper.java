package org.raidar.app.sql.impl.provider;

import org.raidar.app.sql.api.builder.SqlParam;
import org.raidar.app.sql.api.provider.SqlParamMapper;
import org.raidar.app.sql.impl.utils.SqlDateTimeUtils;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.raidar.app.sql.impl.constant.SqlConstants.NULL_VALUE;
import static org.raidar.app.sql.impl.utils.SqlDateTimeUtils.*;
import static org.raidar.app.sql.impl.utils.SqlUtils.escapeValue;

/** Mapper of SQL parameters. */
public class SqlParameterMapper implements SqlParamMapper {

    // Date format to use in queries.
    private String dateFormat = "yyyy-MM-dd";
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(dateFormat);
    private String sqlDateFormat = "YYYY-MM-DD";

    // Datetime format to use in queries.
    private String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormat);
    private String sqlDateTimeFormat = "YYYY-MM-DD HH24:MI:SS";

    private boolean useTimeZone = false;

    public SqlParameterMapper() {
        // Nothing to do.
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public String getSqlDateFormat() {
        return sqlDateFormat;
    }

    public void setDateFormat(String format, String sqlFormat) {

        dateFormat = format;
        dateFormatter = DateTimeFormatter.ofPattern(this.dateFormat);
        sqlDateFormat = sqlFormat;
    }

    public String getDateTimeFormat() {
        return dateTimeFormat;
    }

    public String getSqlDateTimeFormat() {
        return sqlDateTimeFormat;
    }

    public void setDateTimeFormat(String format, String sqlFormat) {

        dateTimeFormat = format;
        dateTimeFormatter = DateTimeFormatter.ofPattern(this.dateTimeFormat);
        sqlDateTimeFormat = sqlFormat;
    }

    public boolean getUseTimeZone() {
        return useTimeZone;
    }

    public void setUseTimeZone(boolean useTimeZone) {
        this.useTimeZone = useTimeZone;
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

        if (value instanceof LocalDate)
            return toDate((LocalDate) value);

        if (value instanceof LocalDateTime)
            return toTimestamp((LocalDateTime) value);

        return escapeValue(value.toString());
    }

    private String toDate(LocalDate value) {

        String stringValue = formatDate(value, dateFormatter);
        return SqlDateTimeUtils.toDate(stringValue, sqlDateFormat);
    }

    private String toTimestamp(LocalDateTime value) {

        String stringValue = formatDateTime(value, dateTimeFormatter);

        return useTimeZone
                ? toTimestampWithTimeZone(stringValue, sqlDateTimeFormat)
                : toTimestampWithoutTimeZone(stringValue, sqlDateTimeFormat);
    }
}
