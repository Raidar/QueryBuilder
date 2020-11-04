package org.raidar.app.sql.impl.utils;

import java.util.Collection;
import java.util.Map;

public class CommonUtils {

    private CommonUtils() {
        // Nothing to do.
    }

    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }

    public static boolean isBlank(String s) {
        return s == null || s.trim().length() == 0;
    }

    public static <T> boolean isEmpty(Collection<T> o) {
        return o == null || o.isEmpty();
    }

    public static <K, V> boolean isEmpty(Map<K, V> o) {
        return o == null || o.isEmpty();
    }

    public static String enclose(String s, String prefix, String suffix) {
        return prefix + s + suffix;
    }
}
