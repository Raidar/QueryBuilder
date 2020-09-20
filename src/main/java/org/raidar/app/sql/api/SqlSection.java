package org.raidar.app.sql.api;

import java.util.List;

/** SQL-раздел - параметризованное SQL-предложение. */
public interface SqlSection extends SqlClause {

    SqlParamList getParams();
}
