package com.todo.utils;

import com.todo.utils.dataReader.PropertyReader;

public class OSUtils {

    public enum OS {WINDOWS, MAC, LINUX, OTHER}

    // Get the current operating system
    public static OS getCurrentOS() {
        String os = PropertyReader.getProperty("os.name").toLowerCase();
        if (os.contains("win")) return OS.WINDOWS;
        if (os.contains("mac")) return OS.MAC;
        if (os.contains("nix") || os.contains("nux")) return OS.LINUX;
        return OS.OTHER;
    }
}
