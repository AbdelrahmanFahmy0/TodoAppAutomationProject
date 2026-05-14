package com.todo.drivers;

import io.appium.java_client.AppiumDriver;

public abstract class AbstractDriver {

    public abstract AppiumDriver createDriver();

    public abstract void configureNativeOrHybridApp();

    public abstract void configureHybridCapabilities();

    public abstract void configureWebCapabilities();
}