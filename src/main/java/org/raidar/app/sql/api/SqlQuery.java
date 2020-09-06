package org.raidar.app.sql.api;

import java.io.Serializable;
import java.util.Map;

/** SQL-запрос. */
public interface SqlQuery extends SqlClause {

    Map<String, Serializable> getParams();
}
