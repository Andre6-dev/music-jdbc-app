package org.ravn.calmusic.util;

public class DBUtil {

    public static void close(AutoCloseable resource) {
        try {
            if (resource != null) {
                resource.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
