package com.todo.utils.actions;

import com.todo.utils.logs.LogsManager;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebElement;

import java.time.Duration;
import java.util.Collections;

public class AndroidGestures {

    private final AndroidDriver driver;
    private final ElementActions actions;

    public AndroidGestures(AndroidDriver driver) {
        this.driver = driver;
        actions = new ElementActions(driver);
    }

    /**
     * Performs a long press gesture on the element specified by the locator.
     *
     * @param locator  element locator
     * @param duration duration of the long press in milliseconds for Android, or seconds for iOS
     * @return this AndroidGestures instance for method chaining
     */
    public AndroidGestures longPress(By locator, int duration) {
        LogsManager.info("Performing long press on element: " + locator.toString() + " for duration: " + duration + "ms");
        WebElement element = actions.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("mobile: longClickGesture",
                ImmutableMap.of("elementId", ((RemoteWebElement) element).getId(), "duration", duration));
        return this;
    }

    /**
     * Performs a double click gesture on the element specified by the locator.
     *
     * @param locator element locator
     * @return this AndroidGestures instance for method chaining
     */
    public AndroidGestures doubleClick(By locator) {
        LogsManager.info("Performing double click on element: " + locator.toString());
        WebElement element = actions.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("mobile: doubleClickGesture",
                ImmutableMap.of("elementId", ((RemoteWebElement) element).getId()));
        return this;
    }

    /**
     * Performs a tap gesture at the specified coordinates.
     *
     * @param x x-coordinate of the tap
     * @param y y-coordinate of the tap
     * @return this AndroidGestures instance for method chaining
     */
    public AndroidGestures tapByCoordinates(int x, int y) {
        LogsManager.info("Performing tap at coordinates (" + x + ", " + y + ")");
        driver.executeScript("mobile: clickGesture", ImmutableMap.of("x", x, "y", y));
        return this;
    }

    /**
     * Performs a tap gesture at the center of the screen.
     *
     * @return this AndroidGestures instance for method chaining
     */
    public AndroidGestures tapOnScreenCenter() {
        Dimension size = driver.manage().window().getSize();
        int centerX = size.getWidth() / 2;
        int centerY = size.getHeight() / 2;
        LogsManager.info("Performing tap at center coordinates (" + centerX + ", " + centerY + ")");
        driver.executeScript("mobile: clickGesture", ImmutableMap.of("x", centerX, "y", centerY));
        return this;
    }

    /**
     * Performs a swipe gesture on the element specified by the locator in the given direction.
     *
     * @param locator   element locator
     * @param direction direction to swipe (UP, DOWN, LEFT, RIGHT)
     * @return this AndroidGestures instance for method chaining
     */
    public AndroidGestures swipe(By locator, Direction direction) {
        LogsManager.info("Performing swipe on element: " + locator.toString() + " in direction: " + direction.name());
        WebElement element = actions.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("mobile: swipeGesture",
                ImmutableMap.of("elementId", ((RemoteWebElement) element).getId(), "direction", direction.name().toLowerCase(), "percent", 0.75));
        return this;
    }

    /**
     * Performs a swipe gesture starting from the center of the screen in the given direction.
     *
     * @param direction direction to swipe (UP, DOWN, LEFT, RIGHT)
     * @return this AndroidGestures instance for method chaining
     */
    public AndroidGestures swipeCenter(Direction direction) {
        LogsManager.info("Performing swipe from center in direction: " + direction.name());
        ((JavascriptExecutor) driver).executeScript("mobile: swipeGesture",
                ImmutableMap.of("direction", direction.name().toLowerCase(), "percent", 0.75));
        return this;
    }

    /**
     * Performs a swipe gesture on the screen in the given direction.
     * The swipe starts from 10% of the screen width and height, and covers 80% of the screen.
     *
     * @param direction direction to swipe (UP, DOWN, LEFT, RIGHT)
     * @return this AndroidGestures instance for method chaining
     */
    public AndroidGestures swipe(Direction direction) {
        LogsManager.info("Performing swipe in direction: " + direction.name() + " from center of the screen");
        // Get screen dimensions dynamically
        Dimension size = driver.manage().window().getSize();
        ((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.builder()
                .put("left", size.getWidth() * 0.1)   // 10% from the left
                .put("top", size.getHeight() * 0.1)   // 10% from the top
                .put("width", size.getWidth() * 0.8)  // 80% of screen width
                .put("height", size.getHeight() * 0.8) // 80% of screen height
                .put("direction", direction.name().toLowerCase())
                .put("percent", 0.75)
                .build());
        return this;
    }

    /**
     * Performs a drag and drop gesture from the source element specified by the locator to the given coordinates.
     *
     * @param sourceLocator element locator for the source element
     * @param x             x-coordinate to drop the element
     * @param y             y-coordinate to drop the element
     * @return this AndroidGestures instance for method chaining
     */
    public AndroidGestures dragAndDrop(By sourceLocator, int x, int y) {
        LogsManager.info("Performing drag and drop from element: " + sourceLocator.toString() + " to coordinates: (" + x + ", " + y + ")");
        WebElement source = actions.findElement(sourceLocator);
        ((JavascriptExecutor) driver).executeScript("mobile: dragGesture",
                ImmutableMap.of("elementId", ((RemoteWebElement) source).getId(), "endX", x, "endY", y));
        return this;
    }

    /**
     * Performs a pinch open gesture on the element specified by the locator.
     *
     * @param locator element locator
     * @return this AndroidGestures instance for method chaining
     */
    public AndroidGestures pinchOpen(By locator) {
        LogsManager.info("Performing pinch open gesture on element: " + locator.toString());
        WebElement element = actions.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("mobile: pinchOpenGesture",
                ImmutableMap.of("elementId", ((RemoteWebElement) element).getId(), "percent", 0.75));
        return this;
    }

    /**
     * Performs a pinch close gesture on the element specified by the locator.
     *
     * @param locator element locator
     * @return this AndroidGestures instance for method chaining
     */
    public AndroidGestures pinchClose(By locator) {
        LogsManager.info("Performing pinch close gesture on element: " + locator.toString());
        WebElement element = actions.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("mobile: pinchCloseGesture",
                ImmutableMap.of("elementId", ((RemoteWebElement) element).getId(), "percent", 0.75));
        return this;
    }
}