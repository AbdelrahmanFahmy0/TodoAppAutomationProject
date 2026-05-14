package com.todo.utils.actions;

import com.todo.utils.logs.LogsManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.connection.ConnectionStateBuilder;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.ScreenOrientation;

import java.time.Duration;
import java.util.List;

public class AndroidDeviceActions {

    private final AndroidDriver driver;

    public AndroidDeviceActions(AndroidDriver driver) {
        this.driver = driver;
    }

    // ─────────────────────────────────────────────
    // NAVIGATION
    // ─────────────────────────────────────────────

    /**
     * Presses the Back button on the Android device.
     *
     * @return The current instance of AndroidDeviceActions for method chaining.
     */
    public AndroidDeviceActions pressBack() {
        driver.pressKey(new KeyEvent(AndroidKey.BACK));
        LogsManager.info("Pressed Back button");
        return this;
    }

    /**
     * Presses the Home button on the Android device.
     *
     * @return The current instance of AndroidDeviceActions for method chaining.
     */
    public AndroidDeviceActions pressHome() {
        driver.pressKey(new KeyEvent(AndroidKey.HOME));
        LogsManager.info("Pressed Home button");
        return this;
    }

    /**
     * Presses the App Switcher button on the Android device.
     *
     * @return The current instance of AndroidDeviceActions for method chaining.
     */
    public AndroidDeviceActions pressAppSwitcher() {
        driver.pressKey(new KeyEvent(AndroidKey.APP_SWITCH));
        LogsManager.info("Pressed App Switcher button");
        return this;
    }

    /**
     * Opens an app on the Android device using its package name.
     *
     * @param appPackage The package name of the app to open (e.g., "com.example.myapp").
     * @return The current instance of AndroidDeviceActions for method chaining.
     */
    public AndroidDeviceActions openApp(String appPackage) {
        driver.activateApp(appPackage);
        LogsManager.info("Opened app with package: " + appPackage);
        return this;
    }

    /**
     * Closes an app on the Android device using its package name.
     *
     * @param appPackage The package name of the app to close (e.g., "com.example.myapp").
     * @return The current instance of AndroidDeviceActions for method chaining.
     */
    public AndroidDeviceActions closeApp(String appPackage) {
        driver.terminateApp(appPackage);
        LogsManager.info("Closed app with package: " + appPackage);
        return this;
    }

    /**
     * Relaunches an app on the Android device by first closing it and then opening it again using its package name.
     *
     * @param appPackage The package name of the app to relaunch (e.g., "com.example.myapp").
     * @return The current instance of AndroidDeviceActions for method chaining.
     */
    public AndroidDeviceActions relaunchApp(String appPackage) {
        closeApp(appPackage).openApp(appPackage);
        LogsManager.info("Relaunched app with package: " + appPackage);
        return this;
    }

    /**
     * Installs an app on the Android device from a specified file path.
     *
     * @param appPath The file path to the app APK (e.g., "./src/test/resources/myapp.apk").
     * @return The current instance of AndroidDeviceActions for method chaining.
     */
    public AndroidDeviceActions installApp(String appPath) {
        driver.installApp(appPath);
        LogsManager.info("Installed app from path: " + appPath);
        return this;
    }

    /**
     * Uninstalls an app from the Android device using its package name.
     *
     * @param appPackage The package name of the app to uninstall (e.g., "com.example.myapp").
     * @return The current instance of AndroidDeviceActions for method chaining.
     */
    public AndroidDeviceActions uninstallApp(String appPackage) {
        driver.removeApp(appPackage);
        LogsManager.info("Uninstalled app with package: " + appPackage);
        return this;
    }

    /**
     * Runs an app in the background for a specified number of seconds.
     *
     * @param seconds The number of seconds to run the app in the background.
     * @return The current instance of AndroidDeviceActions for method chaining.
     */
    public AndroidDeviceActions runAppInBackground(int seconds) {
        driver.runAppInBackground(Duration.ofSeconds(seconds));
        LogsManager.info("Ran app in background for " + seconds + " seconds");
        return this;
    }

    /**
     * Clears the app data for a specified app package.
     *
     * @param appPackage The package name of the app to clear data for (e.g., "com.example.myapp").
     * @return The current instance of AndroidDeviceActions for method chaining.
     */
    public AndroidDeviceActions clearAppData(String appPackage) {
        driver.executeScript("mobile: shell",
                java.util.Map.of("command", "pm", "args", List.of("clear", appPackage)));
        LogsManager.info("Cleared app data for: " + appPackage);
        return this;
    }

    /**
     * Retrieves the current activity name of the Android device.
     *
     * @return The name of the current activity.
     */
    public String getCurrentActivity() {
        String currentActivity = driver.currentActivity();
        LogsManager.info("Current activity: " + currentActivity);
        return currentActivity;
    }

    // ─────────────────────────────────────────────
    // KEYBOARD
    // ─────────────────────────────────────────────

    /**
     * Hides the on-screen keyboard on the Android device.
     *
     * @return The current instance of AndroidDeviceActions for method chaining.
     */
    public AndroidDeviceActions hideKeyboard() {
        driver.hideKeyboard();
        LogsManager.info("Keyboard hidden");
        return this;
    }

    /**
     * Checks if the on-screen keyboard is currently shown on the Android device.
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
     * Switches the screen orientation of the Android device to landscape mode.
     *
     * @return The current instance of AndroidDeviceActions for method chaining.
     */
    public AndroidDeviceActions switchToLandscape() {
        driver.rotate(ScreenOrientation.LANDSCAPE);
        LogsManager.info("Switched to landscape orientation");
        return this;
    }

    /**
     * Switches the screen orientation of the Android device to portrait mode.
     *
     * @return The current instance of AndroidDeviceActions for method chaining.
     */
    public AndroidDeviceActions switchToPortrait() {
        driver.rotate(ScreenOrientation.PORTRAIT);
        LogsManager.info("Switched to portrait orientation");
        return this;
    }

    /**
     * Retrieves the current screen orientation of the Android device.
     *
     * @return The current screen orientation (e.g., "LANDSCAPE" or "PORTRAIT").
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
     * Copies the specified text to the clipboard on the Android device.
     *
     * @param text The text to copy to the clipboard.
     * @return The current instance of AndroidDeviceActions for method chaining.
     */
    public AndroidDeviceActions copyToClipboard(String text) {
        driver.setClipboardText(text);
        LogsManager.info("Copied to clipboard: " + text);
        return this;
    }

    /**
     * Retrieves the current text from the clipboard on the Android device.
     *
     * @return The text currently stored in the clipboard.
     */
    public String pasteFromClipboard() {
        String clipboardText = driver.getClipboardText();
        LogsManager.info("Pasted from clipboard: " + clipboardText);
        return clipboardText;
    }

    // ─────────────────────────────────────────────
    // NETWORK
    // ─────────────────────────────────────────────

    /**
     * Toggles the WiFi connection on the Android device.
     *
     * @param enable true to enable WiFi, false to disable it.
     * @return The current instance of AndroidDeviceActions for method chaining.
     */
    public AndroidDeviceActions toggleWifi(boolean enable) {
        ConnectionStateBuilder state = new ConnectionStateBuilder(driver.getConnection());
        if (enable) {
            state.withWiFiEnabled();
        } else {
            state.withWiFiDisabled();
        }
        driver.setConnection(state.build());
        LogsManager.info((enable ? "Enabled" : "Disabled") + " WiFi");
        return this;
    }

    /**
     * Toggles the Airplane Mode on the Android device.
     *
     * @param enable true to enable Airplane Mode, false to disable it.
     * @return The current instance of AndroidDeviceActions for method chaining.
     */
    public AndroidDeviceActions toggleAirplaneMode(boolean enable) {
        ConnectionStateBuilder state = new ConnectionStateBuilder(driver.getConnection());
        if (enable) {
            state.withAirplaneModeEnabled();
        } else {
            state.withAirplaneModeDisabled();
        }
        driver.setConnection(state.build());
        LogsManager.info((enable ? "Enabled" : "Disabled") + " Airplane Mode");
        return this;
    }

    // ─────────────────────────────────────────────
    // Lock / Unlock
    // ─────────────────────────────────────────────

    /**
     * Locks the Android device.
     *
     * @return The current instance of AndroidDeviceActions for method chaining.
     */
    public AndroidDeviceActions lock() {
        driver.lockDevice();
        LogsManager.info("Device locked");
        return this;
    }

    /**
     * Unlocks the Android device.
     *
     * @return The current instance of AndroidDeviceActions for method chaining.
     */
    public AndroidDeviceActions unlock() {
        driver.unlockDevice();
        LogsManager.info("Device unlocked");
        return this;
    }

    /**
     * Checks if the Android device is currently locked.
     *
     * @return true if the device is locked, false otherwise.
     */
    public boolean isDeviceLocked() {
        boolean isLocked = driver.isDeviceLocked();
        LogsManager.info("Is device locked: " + isLocked);
        return isLocked;
    }

    // ─────────────────────────────────────────────
    // NOTIFICATIONS
    // ─────────────────────────────────────────────

    /**
     * Opens the notifications panel on the Android device.
     *
     * @return The current instance of AndroidDeviceActions for method chaining.
     */
    public AndroidDeviceActions openNotifications() {
        driver.openNotifications();
        LogsManager.info("Opened notifications");
        return this;
    }
}