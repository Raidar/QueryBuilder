package org.raidar.app.sql.api;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

/** Список SQL-параметров для привязки значения. */
public interface SqlParamList extends Serializable {

    Collection<? extends SqlParam> get();

    void clear();

    boolean isEmpty();

    void add(SqlParam param);

    void add(String name, Serializable value);

    void add(SqlParamList params);

    void add(Collection<? extends SqlParam> params);

    Map<String, Serializable> getMap();

    void setMap(Map<String, Serializable> map);

    void addMap(Map<String, Serializable> map);
}
