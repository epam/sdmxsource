package org.sdmxsource.util;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/**
 * The type String util.
 */
public class StringUtil {
    private static final WeakHashMap<String, WeakReference<String>> s_manualCache = new WeakHashMap<String, WeakReference<String>>(100000);

    /**
     * Manual intern string.
     *
     * @param str the str
     * @return the string
     */
    public static String manualIntern(String str) {
        if (str == null) {
            return str;
        }
        final WeakReference<String> cached = s_manualCache.get(str);
        if (cached != null) {
            final String value = cached.get();
            if (value != null)
                return value;
        }
        str = new String(str);
        s_manualCache.put(str, new WeakReference<String>(str));
        return str;
    }

    /**
     * Trim leading whitespace string.
     *
     * @param s the s
     * @return the string
     */
    public static String trimLeadingWhitespace(final String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }
        for (int i = 0, len = s.length(); i < len; i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return s.substring(i);
            }
        }
        return "";
    }

    /**
     * Has text boolean.
     *
     * @param s the s
     * @return the boolean
     */
    public static boolean hasText(final String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        for (int i = 0, len = s.length(); i < len; i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return true;
            }
        }
        return false;
    }
}
