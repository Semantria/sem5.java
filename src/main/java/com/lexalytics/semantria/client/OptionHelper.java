package com.lexalytics.semantria.client;

import java.util.Map;

public class OptionHelper {
    /**
     * Test that all of the supplied options are set.
     * Strings must be non-null.
     * Booleans must be true.
     *
     * @param opts The options map
     * @param names One or more option names
     * @return boolean
     */
    public static boolean hasAllOptions(Map<String, Object> opts, String... names) {
        for (String name : names) {
            Object obj = opts.get(name);
            if (obj == null) {
                return false;
            }
            if (obj instanceof Boolean && !(boolean) obj) {
                return false;
            }
        }
        return true;
    }

    public static String getStringOption(Map<String, Object> opts, String name) {
        return (String) opts.get(name);
    }

    public static int getIntOption(Map<String, Object> opts, String name) {
        return Integer.parseInt(getStringOption(opts, name));
    }

    public static boolean getBooleanOption(Map<String, Object> opts, String name) {
        Object obj = opts.get(name);
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Boolean)) {
            return false;
        }
        return (boolean) obj;
    }


}
