package com.todo.utils.actions;

import com.todo.utils.logs.LogsManager;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebElement;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

public class IOSGestures {

    private final IOSDriver driver;
    private final ElementActions actions;

    public IOSGestures(IOSDriver driver) {
        this.driver = driver;
        actions = new ElementActions(driver);
    }

    /**
     * Scrolls to the element specified by the locator using iOS native scroll.
     * It performs multiple scroll attempts until the element is found and visible,
     * or throws an exception if not found after max attempts.
     *
     * @param locator element locator
     */
    public void scrollToElement(By locator) {
        int maxAttempts = 10;
        for (int i = 0; i < maxAttempts; i++) {
            List<WebElement> targets = driver.findElements(locator);
            if (!targets.isEmpty() && targets.getFirst().isDisplayed()) {
                return;
            }
            driver.executeScript("mobile: scroll", ImmutableMap.of("direction", "down"));
        }
        throw new NoSuchElementException("Element not found after " + maxAttempts + " scrolls: " + locator);
    }

    /**
     * Performs a long press gesture on the element specified by the locator.
     *
     * @param locator  element locator
     * @param duration duration of the long press in milliseconds for Android, or seconds for iOS
     * @return this IOSGestures instance for method chaining
     */
    public IOSGestures longPress(By locator, int duration) {
        LogsManager.info("Performing long press on element: " + locator.toString() + " for duration: " + duration + "s");
        WebElement element = actions.findElement(locator);
        driver.executeScript("mobile: touchAndHold",
                ImmutableMap.of("elementId", ((RemoteWebElement) element).getId(), "duration", duration));
        return this;
    }

    /**
     * Performs a scroll gesture on the element specified by the locator in the given direction.
     *
     * @param locator   element locator
     * @param direction direction to scroll (UP, DOWN, LEFT, RIGHT)
     * @return this IOSGestures instance for method chaining
     */
    public IOSGestures scroll(By locator, Direction direction) {
        LogsManager.info("Performing scroll on element: " + locator.toString() + " in direction: " + direction.name());
        WebElement element = actions.findElement(locator);
        driver.executeScript("mobile: scroll",
                ImmutableMap.of("elementId", ((RemoteWebElement) element).getId(), "direction", direction.name().toLowerCase()));
        return this;
    }

    /**
     * Performs a double click gesture on the element specified by the locator.
     *
     * @param locator element locator
     * @return this IOSGestures instance for method chaining
     */
    public IOSGestures doubleClick(By locator) {
        LogsManager.info("Performing double click on element: " + locator.toString());
        WebElement element = actions.findElement(locator);
        driver.executeScript("mobile: doubleTap",
                ImmutableMap.of("elementId", ((RemoteWebElement) element).getId()));
        return this;
    }

    /**
     * Performs a tap gesture at the specified coordinates.
     *
     * @param x x-coordinate of the tap
     * @param y y-coordinate of the tap
     * @return this IOSGestures instance for method chaining
     */
    public IOSGestures tapByCoordinates(int x, int y) {
        LogsManager.info("Performing tap at coordinates (" + x + ", " + y + ")");
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 0)
                .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(new Pause(finger, Duration.ofMillis(100)))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        try {
            driver.perform(Collections.singletonList(tap));
        } catch (Exception e) {
            LogsManager.error("Failed to perform tap at coordinates (" + x + ", " + y + "): " + e.getMessage());
        }
        return this;
    }

    /**
     * Performs a swipe gesture on the element specified by the locator in the given direction.
     *
     * @param locator   element locator
     * @param direction direction to swipe (UP, DOWN, LEFT, RIGHT)
     * @return this IOSGestures instance for method chaining
     */
    public IOSGestures swipe(By locator, Direction direction) {
        LogsManager.info("Performing swipe on element: " + locator.toString() + " in direction: " + direction.name());
        WebElement element = actions.findElement(locator);
        driver.executeScript("mobile: swipe",
                ImmutableMap.of("elementId", ((RemoteWebElement) element).getId(), "direction", direction.name().toLowerCase(), "percent", 0.75));
        return this;
    }

    /**
     * Performs a swipe gesture starting from the center of the screen in the given direction.
     *
     * @param direction direction to swipe (UP, DOWN, LEFT, RIGHT)
     * @return this IOSGestures instance for method chaining
     */
    public IOSGestures swipeCenter(Direction direction) {
        LogsManager.info("Performing swipe from center in direction: " + direction.name());
        driver.executeScript("mobile: swipe",
                ImmutableMap.of("direction", direction.name().toLowerCase(), "percent", 0.75));
        return this;
    }

    /**
     * Performs a drag and drop gesture from the source element to the target element.
     *
     * @param sourceLocator element locator for the source element
     * @param targetLocator element locator for the target element
     * @return this IOSGestures instance for method chaining
     */
    public IOSGestures dragAndDrop(By sourceLocator, By targetLocator) {
        LogsManager.info("Performing drag and drop from element: " + sourceLocator.toString() + " to element: " + targetLocator.toString());
        WebElement source = actions.findElement(sourceLocator);
        WebElement target = actions.findElement(targetLocator);
        driver.executeScript("mobile: dragFromToWithVelocity",
                ImmutableMap.of("fromElementId", ((RemoteWebElement) source).getId(),
                        "toElementId", ((RemoteWebElement) target).getId(), "pressDuration", 0.5,
                        "holdDuration", 0.1, "velocity", 1000));
        return this;
    }

    /**
     * Performs a pinch gesture on the element specified by the locator with the given scale.
     *
     * @param locator element locator
     * @param scale   scale a factor for the pinch gesture (e.g., 0 to 1 for zoom out, greater than 1 for zoom in)
     * @return this IOSGestures instance for method chaining
     */
    public IOSGestures pinch(By locator, double scale) {
        LogsManager.info("Performing pinch on element: " + locator.toString() + " with scale: " + scale);
        WebElement element = actions.findElement(locator);
        driver.executeScript("mobile: pinch",
                ImmutableMap.of("elementId", ((RemoteWebElement) element).getId(), "scale", scale, "velocity", 1.0));
        return this;
    }
}