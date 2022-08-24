package com.android.app.unittesting.staticf;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

public class CommandUtil {
    private CommandUtil() {
    }

    private static class SingletonHolder {
        private static final CommandUtil INSTANCE = new CommandUtil();
    }

    public static final CommandUtil getSingleInstance() {
        return SingletonHolder.INSTANCE;
    }

    public String getProperty(String propName) {
        String value = null;
        Object roSecureObj;
        try {
            roSecureObj = Class.forName("android.os.SystemProperties")
                    .getMethod("get", String.class)
                    .invoke(null, propName);
            if (roSecureObj != null) value = (String) roSecureObj;
        } catch (Exception e) {
            value = null;
        } finally {
            return value;
        }
    }
}
