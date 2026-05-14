package com.todo.validations;

import com.todo.utils.FileUtils;
import com.todo.utils.WaitManager;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public abstract class BaseAssertion {

    protected AppiumDriver driver;
    protected WaitManager waitManager;

    // For using without WebDriver in API tests
    protected BaseAssertion() {
    }

    protected BaseAssertion(AppiumDriver driver) {
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
    }

    // Method to assert the boolean condition is true
    protected abstract void assertTrue(boolean condition, String message);

    // Method to assert the boolean condition is false
    protected abstract void assertFalse(boolean condition, String message);

    // Method to assert equality of two strings
    protected abstract void assertEquals(String actual, String expected, String message);

    public BaseAssertion Equals(String actual, String expected, String message) {
        assertEquals(actual, expected, message);
        return this;
    }

    // Method to assert that an element is visible on the page
    public void isElementVisible(By locator) {
        boolean flag = waitManager.fluentWait().until(driver1 ->
        {
            try {
                driver1.findElement(locator).isDisplayed();
                return true;
            } catch (Exception e) {
                return false;
            }
        });
        assertTrue(flag, "Element is not visible: " + locator);
    }

    // Method to assert that an element is hidden on the page
    public void isElementHidden(By locator) {
        boolean flag = waitManager.fluentWait().until(driver1 ->
        {
            try {
                return !driver1.findElement(locator).isDisplayed();
            } catch (Exception e) {
                return true;
            }
        });
        assertTrue(flag, "Element is visible: " + locator);
    }

    // Method to assert that an element is selected on the page
    public void isElementSelected(By locator) {
        boolean flag = waitManager.fluentWait().until(driver1 ->
        {
            try {
                return driver1.findElement(locator).isSelected();
            } catch (Exception e) {
                return false;
            }
        });
        assertTrue(flag, "Element is not selected: " + locator);
    }

    // Method to assert that an element is not selected on the page
    public void isElementNotSelected(By locator) {
        boolean flag = waitManager.fluentWait().until(driver1 ->
        {
            try {
                return !driver1.findElement(locator).isSelected();
            } catch (Exception e) {
                return true;
            }
        });
        assertTrue(flag, "Element is selected: " + locator);
    }

    // Method to assert that an element's attribute has a specific value
    public void elementAttributeHasValue(By locator, String attribute, String expectedValue) {
        boolean flag = waitManager.fluentWait().until(driver1 -> {
            try {
                String actualValue = driver1.findElement(locator).getAttribute(attribute);
                return expectedValue.equals(actualValue);
            } catch (Exception e) {
                return false;
            }
        });
        assertTrue(flag, "Element attribute '" + attribute + "' does not have expected value '"
                + expectedValue + "' for locator: " + locator);
    }

    // Method to assert the current page title
    public void assertPageTitle(String expectedTitle) {
        String actualTitle = driver.getTitle();
        assertEquals(actualTitle, expectedTitle, "Title does not match. Expected: " + expectedTitle + ", Actual: " + actualTitle);
    }

    // Method to assert that a file exists
    public void assertFileExists(String fileName, String message) {
        waitManager.fluentWait().until(
                d -> FileUtils.isFileExists(fileName)
        );
        assertTrue(FileUtils.isFileExists(fileName), message);
    }
}
