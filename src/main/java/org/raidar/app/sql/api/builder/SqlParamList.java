package org.raidar.app.sql.api.builder;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/** Список SQL-параметров для привязки значения. */
public interface SqlParamList extends Serializable {

    List<? extends SqlParam> get();

    SqlParam get(String name);

    Serializable getValue(String name);

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
