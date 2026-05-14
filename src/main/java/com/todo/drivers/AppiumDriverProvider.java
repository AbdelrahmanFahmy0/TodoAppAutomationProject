package com.todo.drivers;

import io.appium.java_client.AppiumDriver;

public interface AppiumDriverProvider {
    // Method to retrieve the current AppiumDriver instance
    AppiumDriver getAppiumDriver();
}