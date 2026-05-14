package com.todo.validations;

import com.todo.utils.logs.LogsManager;
import io.appium.java_client.AppiumDriver;
import org.testng.ITestResult;
import org.testng.asserts.SoftAssert;

//Soft Assertion
public class SoftAssertion extends BaseAssertion {

    private static SoftAssert softAssert = new SoftAssert();
    private static boolean used = false; // Flag to track usage

    // For using without WebDriver in API tests
    public SoftAssertion() {
        super();
    }

    public SoftAssertion(AppiumDriver driver) {
        super(driver);
    }

    @Override
    public void assertTrue(boolean condition, String message) {
        used = true; // Mark that an assertion was made
        softAssert.assertTrue(condition, message);
    }

    @Override
    public void assertFalse(boolean condition, String message) {
        used = true; // Mark that an assertion was made
        softAssert.assertFalse(condition, message);
    }

    @Override
    public void assertEquals(String actual, String expected, String message) {
        used = true; // Mark that an assertion was made
        softAssert.assertEquals(actual, expected, message);
    }

    public static void assertAll(ITestResult result) {
        if (!used) return; // If no assertions were made, do nothing
        try {
            softAssert.assertAll();
        } catch (AssertionError e) {
            LogsManager.error("Assertion failed:", e.getMessage());
            result.setStatus(ITestResult.FAILURE);
            result.setThrowable(e);
        } finally {
            softAssert = new SoftAssert(); // Reset the soft assert instance
        }
    }
}
