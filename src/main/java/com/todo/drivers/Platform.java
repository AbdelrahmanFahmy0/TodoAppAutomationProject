package com.todo.drivers;

import static com.todo.utils.dataReader.PropertyReader.getProperty;

public enum Platform {

    // Each enum provides its own implementation of the abstract method getDriverFactory
    ANDROID {
        @Override
        public AbstractDriver getDriverFactory() {
            return new AndroidFactory();
        }
    },
    IOS {
        @Override
        public AbstractDriver getDriverFactory() {
            return new IOSFactory();
        }
    };

    // Abstract method to be implemented by each enum constant
    public abstract AbstractDriver getDriverFactory();

    // Static methods to determine the platform type based on properties
    public static boolean isAndroid() {
        return getProperty("platform").equalsIgnoreCase("android");
    }

    public static boolean isIOS() {
        return getProperty("platform").equalsIgnoreCase("ios");
    }

    // Static methods to determine if the platform is mobile or web based on properties
    public static boolean isMobile() {
        return getProperty("automated.mobile").equalsIgnoreCase("true");
    }

    public static boolean isWeb() {
        return getProperty("automated.mobile").equalsIgnoreCase("false");
    }
}