package org.raidar.app.sql.impl.provider;

import org.raidar.app.sql.api.builder.SqlParam;
import org.raidar.app.sql.api.builder.SqlQuery;
import org.raidar.app.sql.api.provider.SqlParamMapper;
import org.raidar.app.sql.api.provider.SqlQueryParamSubstitutor;

import static org.raidar.app.sql.impl.constant.SqlConstants.BIND_PREFIX;

/** Substitutor of bind-parameters` values for some queries. */
public class SqlQueryParameterSubstitutor implements SqlQueryParamSubstitutor {

    private static final SqlParamMapper DEFAULT_PARAM_MAPPER = new SqlParameterMapper();

    private final SqlParamMapper paramMapper;

    public SqlQueryParameterSubstitutor() {
        this(DEFAULT_PARAM_MAPPER);
    }

    public SqlQueryParameterSubstitutor(SqlParamMapper paramMapper) {
        this.paramMapper = (paramMapper != null) ? paramMapper : DEFAULT_PARAM_MAPPER;
    }

    public String substitute(SqlQuery query) {

        if (query.isEmpty())
            return null;

        String result = query.getText();

        if (query.getParams().isEmpty())
            return result;

        // to-do: Rewrite to speed-up: loop sql by ":(bind)" and collect result.
        for (SqlParam param : query.getParams().get()) {
            result = result.replace(BIND_PREFIX + param.getName(), paramMapper.toString(param));
        }

        return result;
    }
}
