package com.todo.utils.actions;

import com.todo.utils.logs.LogsManager;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.ScreenOrientation;

import java.time.Duration;
import java.util.HashMap;

public class IOSDeviceActions {

    private final IOSDriver driver;

    public IOSDeviceActions(IOSDriver driver) {
        this.driver = driver;
    }

    // ─────────────────────────────────────────────
    // NAVIGATION
    // ─────────────────────────────────────────────

    /**
     * Presses the Home button on the iOS device.
     * Note: This may not work on simulators or certain iOS versions due to platform restrictions.
     *
     * @return The current instance of IOSDeviceActions for method chaining.
     */
    public IOSDeviceActions pressHome() {
        driver.executeScript("mobile: pressButton", ImmutableMap.of("name", "home"));
        LogsManager.info("Pressed Home button");
        return this;
    }

    /**
     * Opens the app with the specified bundle ID.
     *
     * @param bundleId The bundle ID of the app to open.
     * @return The current instance of IOSDeviceActions for method chaining.
     */
    public IOSDeviceActions openApp(String bundleId) {
        driver.activateApp(bundleId);
        LogsManager.info("Opened app with bundle ID: " + bundleId);
        return this;
    }

    /**
     * Closes the app with the specified bundle ID.
     *
     * @param bundleId The bundle ID of the app to close.
     * @return The current instance of IOSDeviceActions for method chaining.
     */
    public IOSDeviceActions closeApp(String bundleId) {
        driver.terminateApp(bundleId);
        LogsManager.info("Closed app with bundle ID: " + bundleId);
        return this;
    }

    /**
     * Relaunches the app with the specified bundle ID by first closing it and then opening it again.
     *
     * @param bundleId The bundle ID of the app to relaunch.
     * @return The current instance of IOSDeviceActions for method chaining.
     */
    public IOSDeviceActions relaunchApp(String bundleId) {
        closeApp(bundleId).openApp(bundleId);
        LogsManager.info("Relaunched app with bundle ID: " + bundleId);
        return this;
    }

    /**
     * Sends the app to the background for a specified number of seconds.
     *
     * @param seconds The number of seconds to keep the app in the background.
     * @return The current instance of IOSDeviceActions for method chaining.
     */
    public IOSDeviceActions runAppInBackground(int seconds) {
        driver.runAppInBackground(Duration.ofSeconds(seconds));
        LogsManager.info("Sent app to background for " + seconds + " seconds");
        return this;
    }

    /**
     * Installs an app on the device from the specified file path.
     *
     * @param appPath The file path of the app to install (e.g., .ipa file).
     * @return The current instance of IOSDeviceActions for method chaining.
     */
    public IOSDeviceActions installApp(String appPath) {
        driver.installApp(appPath);
        LogsManager.info("Installed app from path: " + appPath);
        return this;
    }

    /**
     * Uninstalls an app from the device using its bundle ID.
     *
     * @param bundleId The bundle ID of the app to uninstall.
     * @return The current instance of IOSDeviceActions for method chaining.
     */
    public IOSDeviceActions uninstallApp(String bundleId) {
        driver.removeApp(bundleId);
        LogsManager.info("Uninstalled app with bundle ID: " + bundleId);
        return this;
    }

    // ─────────────────────────────────────────────
    // KEYBOARD
    // ─────────────────────────────────────────────

    /**
     * Hides the on-screen keyboard if it is currently displayed.
     *
     * @return The current instance of IOSDeviceActions for method chaining.
     */
    public IOSDeviceActions hideKeyboard() {
        driver.hideKeyboard();
        LogsManager.info("Hide the keyboard");
        return this;
    }

    /**
     * Checks if the on-screen keyboard is currently displayed.
     *
     * @return true if the keyboard is shown, false otherwise.
     */
    public boolean isKeyboardShown() {
        boolean isShown = driver.isKeyboardShown();
        LogsManager.info("Is keyboard shown: " + isShown);
        return isShown;
    }

    // ─────────────────────────────────────────────
    // ORIENTATION
    // ─────────────────────────────────────────────

    /**
     * Switches the device orientation to landscape mode.
     *
     * @return The current instance of IOSDeviceActions for method chaining.
     */
    public IOSDeviceActions switchToLandscape() {
        driver.rotate(ScreenOrientation.LANDSCAPE);
        LogsManager.info("Switched to landscape orientation");
        return this;
    }

    /**
     * Switches the device orientation to portrait mode.
     *
     * @return The current instance of IOSDeviceActions for method chaining.
     */
    public IOSDeviceActions switchToPortrait() {
        driver.rotate(ScreenOrientation.PORTRAIT);
        LogsManager.info("Switched to portrait orientation");
        return this;
    }

    /**
     * Retrieves the current orientation of the device.
     *
     * @return A string representing the current orientation (e.g., "LANDSCAPE" or "PORTRAIT").
     */
    public String getOrientation() {
        String orientation = driver.getOrientation().value();
        LogsManager.info("Current orientation: " + orientation);
        return orientation;
    }

    // ─────────────────────────────────────────────
    // CLIPBOARD
    // ─────────────────────────────────────────────

    /**
     * Copies the specified text to the device's clipboard.
     *
     * @param text The text to copy to the clipboard.
     * @return The current instance of IOSDeviceActions for method chaining.
     */
    public IOSDeviceActions copyToClipboard(String text) {
        driver.setClipboardText(text);
        LogsManager.info("Copied to clipboard: " + text);
        return this;
    }

    /**
     * Retrieves the current text from the device's clipboard.
     *
     * @return The text currently stored in the clipboard.
     */
    public String pasteFromClipboard() {
        String clipboardText = driver.getClipboardText();
        LogsManager.info("Pasted from clipboard: " + clipboardText);
        return clipboardText;
    }

    // ─────────────────────────────────────────────
    // Lock / Unlock
    // ─────────────────────────────────────────────

    /**
     * Locks the device.
     *
     * @return The current instance of IOSDeviceActions for method chaining.
     */
    public IOSDeviceActions lock() {
        driver.lockDevice();
        LogsManager.info("Device locked");
        return this;
    }

    /**
     * Unlocks the device.
     *
     * @return The current instance of IOSDeviceActions for method chaining.
     */
    public IOSDeviceActions unlock() {
        driver.unlockDevice();
        LogsManager.info("Device unlocked");
        return this;
    }

    /**
     * Checks if the device is currently locked.
     *
     * @return true if the device is locked, false otherwise.
     */
    public boolean isDeviceLocked() {
        boolean isLocked = driver.isDeviceLocked();
        LogsManager.info("Is device locked: " + isLocked);
        return isLocked;
    }

    // ─────────────────────────────────────────────
    // Alerts
    // ─────────────────────────────────────────────

    /**
     * Accepts the currently displayed alert on the device.
     *
     * @return The current instance of IOSDeviceActions for method chaining.
     */
    public IOSDeviceActions acceptAlert() {
        driver.switchTo().alert().accept();
        LogsManager.info("Alert accepted");
        return this;
    }

    /**
     * Dismisses the currently displayed alert on the device.
     *
     * @return The current instance of IOSDeviceActions for method chaining.
     */
    public IOSDeviceActions dismissAlert() {
        driver.switchTo().alert().dismiss();
        LogsManager.info("Alert dismissed");
        return this;
    }

    // ─────────────────────────────────────────────
    // NOTIFICATION CENTER & CONTROL CENTER
    // ─────────────────────────────────────────────

    /**
     * Opens the notification center by performing a swipe down gesture from the top of the screen.
     *
     * @return The current instance of IOSDeviceActions for method chaining.
     */
    public IOSDeviceActions openNotificationCenter() {
        driver.executeScript("mobile: swipeDown", ImmutableMap.of("duration", 0.5));
        LogsManager.info("Opened notification center");
        return this;
    }

    /**
     * Opens the control center by performing a swipe up gesture from the bottom of the screen.
     *
     * @return The current instance of IOSDeviceActions for method chaining.
     */
    public IOSDeviceActions openControlCenter() {
        driver.executeScript("mobile: swipeUp", ImmutableMap.of("duration", 0.5));
        LogsManager.info("Opened control center");
        return this;
    }

    // ─────────────────────────────────────────────
    // NETWORK
    // ─────────────────────────────────────────────

    /**
     * Toggles the airplane mode on the device. Note that this may not work on simulators or certain iOS versions due to platform restrictions.
     *
     * @return The current instance of IOSDeviceActions for method chaining.
     */
    public IOSDeviceActions toggleAirplaneMode() {
        driver.executeScript("mobile: toggleAirplaneMode");
        LogsManager.info("Toggled airplane mode");
        return this;
    }

    /**
     * Toggles the WiFi on the device. Note that this may not work on simulators or certain iOS versions due to platform restrictions.
     *
     * @return The current instance of IOSDeviceActions for method chaining.
     */
    public IOSDeviceActions toggleWiFi() {
        driver.executeScript("mobile: toggleWiFi");
        LogsManager.info("Toggled WiFi");
        return this;
    }

    /**
     * Toggles the Bluetooth mode on the device. Note that this may not work on simulators or certain iOS versions due to platform restrictions.
     *
     * @return The current instance of IOSDeviceActions for method chaining.
     */
    public IOSDeviceActions toggleBluetoothMode() {
        driver.executeScript("mobile: toggleBluetoothMode");
        LogsManager.info("Toggled Bluetooth mode");
        return this;
    }

    /**
     * Sets the device's location to the specified latitude and longitude. Note that this may not work on simulators or certain iOS versions due to platform restrictions.
     *
     * @param latitude  The latitude to set for the device's location.
     * @param longitude The longitude to set for the device's location.
     * @return The current instance of IOSDeviceActions for method chaining.
     */
    public IOSDeviceActions setLocation(double latitude, double longitude) {
        driver.executeScript("mobile: setLocation",
                new HashMap<String, Double>() {{
                    put("latitude", latitude);
                    put("longitude", longitude);
                }});
        LogsManager.info("Set location to latitude: " + latitude + ", longitude: " + longitude);
        return this;
    }
}
