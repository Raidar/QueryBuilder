package org.raidar.app.sql.api.builder;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

/** List of SQL parameters for values` binding. */
public interface SqlParamList extends Serializable, Iterable<SqlParam> {

    int size();

    boolean isEmpty();

    boolean contains(String name);

    SqlParam get(String name);

    Serializable getValue(String name);

    void clear();

    void add(SqlParam param);

    void add(String name, Serializable value);

    void add(SqlParamList params);

    Collection<? extends SqlParam> get();

    void set(Collection<? extends SqlParam> params);

    void add(Collection<? extends SqlParam> params);

    Map<String, Serializable> getMap();

    void setMap(Map<String, Serializable> map);

    void addMap(Map<String, Serializable> map);

    Stream<SqlParam> stream();

    void add(Stream<? extends SqlParam> stream);
}
