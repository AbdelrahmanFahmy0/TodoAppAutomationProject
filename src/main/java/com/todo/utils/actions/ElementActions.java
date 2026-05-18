package com.todo.utils.actions;

import com.todo.utils.WaitManager;
import com.todo.utils.logs.LogsManager;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.*;

import java.util.List;

public class ElementActions {

    private final AppiumDriver driver;
    private final WaitManager waitManager;

    public ElementActions(AppiumDriver driver) {
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
    }

    /**
     * Clicks on the element specified by the locator.
     * It scrolls to the element, and then performs the click action.
     *
     * @param locator element locator
     * @return this ElementActions instance for method chaining
     */
    public ElementActions click(By locator) {
        waitManager.fluentWait().until(d ->
                {
                    try {
                        scrollToElement(locator);
                        WebElement element = d.findElement(locator);
                        // Wait until the element is stable (not moving)
                        Point initialLocation = element.getLocation();
                        LogsManager.info("initialLocation: " + initialLocation);
                        Point finalLocation = element.getLocation();
                        LogsManager.info("finalLocation: " + finalLocation);
                        if (!initialLocation.equals(finalLocation)) {
                            return false; // still moving, wait longer
                        }
                        element.click();
                        LogsManager.info("Clicked on element: " + locator);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
        );
        return this;
    }

    /**
     * Types the given text into the element specified by the locator.
     * It scrolls to the element, clears any existing text, and then sends the new text.
     *
     * @param locator element locator
     * @param text    text to type into the element
     * @return this ElementActions instance for method chaining
     */
    public ElementActions type(By locator, String text) {
        waitManager.fluentWait().until(d ->
                {
                    try {
                        //scrollToElement(locator);
                        WebElement element = d.findElement(locator);
                        element.clear();
                        element.sendKeys(text);
                        LogsManager.info("Typed text '" + text + "' into element: " + locator);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
        );
        return this;
    }

    /**
     * Clears the text from the element specified by the locator.
     * It scrolls to the element, and then clears any existing text.
     *
     * @param locator element locator
     * @return this ElementActions instance for method chaining
     */
    public ElementActions clear(By locator) {
        waitManager.fluentWait().until(d ->
                {
                    try {
                        scrollToElement(locator);
                        WebElement element = d.findElement(locator);
                        element.clear();
                        LogsManager.info("Cleared text from element: " + locator);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
        );
        return this;
    }

    /**
     * Retrieves the text content of the element specified by the locator.
     * It waits until the element is present and has non-empty text before returning.
     *
     * @param locator element locator
     * @return text content of the element, or null if not found or empty
     */
    public String getText(By locator) {
        return waitManager.fluentWait().until(d ->
                {
                    try {
                        scrollToElement(locator);
                        WebElement element = d.findElement(locator);
                        String msg = element.getText();
                        LogsManager.info("Retrieved text from element: " + locator + " - Text: " + msg);
                        return !msg.isEmpty() ? msg : null;
                    } catch (Exception e) {
                        return null;
                    }
                }
        );
    }

    /**
     * Scrolls to the element containing the specified text using Android UIAutomator.
     * This method is specific to Android and uses a UIAutomator selector to find the element by its text content.
     *
     * @param text the text to scroll to
     * @return this ElementActions instance for method chaining
     */
    public ElementActions scrollToText(String text) {
        String script = String.format("new UiScrollable(new UiSelector()).scrollIntoView(text(\"" + text + "\"));");
        findElement(AppiumBy.androidUIAutomator(script));
        return this;
    }

    /**
     * Retrieves the value of the specified attribute from the element located by the given locator.
     * It scrolls to the element, and then gets the attribute value.
     *
     * @param locator   element locator
     * @param attribute name of the attribute to retrieve
     * @return value of the specified attribute, or null if not found
     */
    public String getAttributeValue(By locator, String attribute) {
        return waitManager.fluentWait().until(d ->
                {
                    try {
                        scrollToElement(locator);
                        WebElement element = d.findElement(locator);
                        String attrValue = element.getAttribute(attribute);
                        LogsManager.info("Retrieved attribute '" + attribute + "' value from element: " + locator + " - Value: " + attrValue);
                        return attrValue;
                    } catch (Exception e) {
                        return null;
                    }
                }
        );
    }

    /**
     * Finds a single element matching the given locator.
     *
     * @param locator element locator
     * @return the first matching element, or throws NoSuchElementException if not found
     */
    public WebElement findElement(By locator) {
        return driver.findElement(locator);
    }

    /**
     * Finds all elements matching the given locator.
     *
     * @param locator element locator
     * @return list of matching elements, or empty list if none found
     */
    public List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }

    /**
     * Checks if element is present and visible on screen.
     *
     * @param locator element locator
     * @return true if element is visible, false otherwise
     */
    public boolean isElementVisible(By locator) {
        List<WebElement> elements = driver.findElements(locator);
        return !elements.isEmpty() && elements.getFirst().isDisplayed();
    }

    /**
     * Checks if element is present and enabled for interaction.
     *
     * @param locator element locator
     * @return true if element is enabled, false otherwise
     */
    public boolean isElementEnabled(By locator) {
        try {
            return driver.findElement(locator).isEnabled();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Checks if element is present and selected (e.g., for checkboxes or radio buttons).
     *
     * @param locator element locator
     * @return true if element is selected, false otherwise
     */
    public boolean isElementSelected(By locator) {
        try {
            return driver.findElement(locator).isSelected();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Scrolls to the element specified by the locator using platform-specific gestures.
     * For Android, it uses AndroidGestures to scroll to the element.
     * For iOS, it uses IOSGestures to scroll to the element.
     *
     * @param locator element locator
     */
    private void scrollToElement(By locator) {
        if (driver instanceof AndroidDriver) {
            new AndroidGestures((AndroidDriver) driver).scrollToElement(locator);
        } else {
            new IOSGestures((IOSDriver) driver).scrollToElement(locator);
        }
    }
}