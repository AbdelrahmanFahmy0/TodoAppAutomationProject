package com.todo.validations;

import io.appium.java_client.AppiumDriver;
import org.testng.Assert;

// Hard Assertion
public class HardAssertion extends BaseAssertion {

    // For using without WebDriver in API tests
    public HardAssertion() {
        super();
    }

    public HardAssertion(AppiumDriver driver) {
        super(driver);
    }

    @Override
    public void assertTrue(boolean condition, String message) {
        Assert.assertTrue(condition, message);
    }

    @Override
    public void assertFalse(boolean condition, String message) {
        Assert.assertFalse(condition, message);
    }

    @Override
    public void assertEquals(String actual, String expected, String message) {
        Assert.assertEquals(actual, expected, message);
    }
}
