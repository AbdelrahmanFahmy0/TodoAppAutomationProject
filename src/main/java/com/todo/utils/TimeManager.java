package com.todo.utils;

public class TimeManager {

    // Screenshots - Logs - Reports
    public static String getTimestamp() {
        return new java.text.SimpleDateFormat("yyyy-MM-dd_hh-mm-ss").format(new java.util.Date());
    }

    // Unique timestamp for each data
    public static String getSimpleTimestamp() {
        return Long.toString(System.currentTimeMillis());
    }
}
