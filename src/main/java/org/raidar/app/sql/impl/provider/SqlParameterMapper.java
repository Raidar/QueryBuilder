package org.raidar.app.sql.impl.provider;

import org.raidar.app.sql.api.builder.SqlParam;
import org.raidar.app.sql.api.provider.SqlParamMapper;

import java.io.Serializable;
import java.time.LocalDateTime;

import static org.raidar.app.sql.impl.SqlConstants.NULL_VALUE;
import static org.raidar.app.sql.impl.SqlUtils.*;

public class SqlParameterMapper implements SqlParamMapper {

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

        if (value instanceof Number) {
            return value.toString();
        }

        if (value instanceof LocalDateTime) {
            return toTimestampWithoutTimeZone(formatDateTime((LocalDateTime) value));
        }

        return escapeValue(value.toString());
    }
}
