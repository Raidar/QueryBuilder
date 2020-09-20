package org.raidar.app.sql.api;

import java.util.List;

/** SQL-запрос. */
public interface SqlQuery extends SqlSection {

    String toParamText();
}
