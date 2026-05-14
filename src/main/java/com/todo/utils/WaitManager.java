package com.todo.utils;

import com.todo.utils.dataReader.PropertyReader;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.ArrayList;

public class WaitManager {

    private final AppiumDriver driver;

    public WaitManager(AppiumDriver driver) {
        this.driver = driver;
    }

    // Method to create and return a FluentWait instance
    public FluentWait<AppiumDriver> fluentWait() {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(Long.parseLong(PropertyReader.getProperty("DEFAULT_WAIT"))))
                .pollingEvery(Duration.ofMillis(100))
                .ignoreAll(getExceptions());
    }

    // Method to get a list of exceptions to ignore during waits
    private ArrayList<Class<? extends Exception>> getExceptions() {
        ArrayList<Class<? extends Exception>> exceptions = new ArrayList<>();
        exceptions.add(NoSuchElementException.class);
        exceptions.add(StaleElementReferenceException.class);
        exceptions.add(ElementNotInteractableException.class);
        exceptions.add(ElementClickInterceptedException.class);
        exceptions.add(NoAlertPresentException.class);
        return exceptions;
    }
}