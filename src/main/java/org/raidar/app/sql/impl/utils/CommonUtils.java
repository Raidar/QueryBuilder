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

    public static boolean isEmpty(StringBuilder b) {
        return b == null || b.length() == 0;
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

    public static boolean isEnclosed(String s, String prefix, String suffix) {
        return !isEmpty(s) && s.startsWith(prefix) && s.endsWith(suffix);
    }

    public static StringBuilder enclose(StringBuilder builder, String prefix, String suffix) {
        return builder.insert(0, prefix).append(suffix);
    }

    public static boolean isEnclosed(StringBuilder builder, String prefix, String suffix) {
        return !isEmpty(builder) &&
                builder.length() >= prefix.length() + suffix.length() &&
                prefix.equals(builder.substring(0, prefix.length())) &&
                suffix.equals(builder.substring(builder.length() - suffix.length()));
    }
}
