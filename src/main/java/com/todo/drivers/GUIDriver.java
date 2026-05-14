package com.todo.drivers;

import com.todo.utils.actions.*;
import com.todo.utils.dataReader.PropertyReader;
import com.todo.utils.logs.LogsManager;
import com.todo.validations.HardAssertion;
import com.todo.validations.SoftAssertion;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class GUIDriver {

    //Read a platform type from properties
    private final String platformType = PropertyReader.getProperty("platform");

    //ThreadLocal to manage AppiumDriver instances per thread
    private final ThreadLocal<AppiumDriver> driverThreadLocal = new ThreadLocal<>();

    //Constructor to initialize the driver based on platform type
    public GUIDriver() {
        LogsManager.info("═══ Creating GUIDriver for platform: " + platformType + " ═══");
        Platform platform = Platform.valueOf(platformType.toUpperCase());
        AbstractDriver abstractDriver = platform.getDriverFactory();
        AppiumDriver driver = abstractDriver.createDriver();
        driverThreadLocal.set(driver);
    }

    //Element Actions
    public ElementActions element() {
        return new ElementActions(get());
    }

    //Gestures Actions
    public AndroidGestures androidGesture() {
        return new AndroidGestures((AndroidDriver) get());
    }

    public IOSGestures iosGesture() {
        return new IOSGestures((IOSDriver) get());
    }

    //Device Actions
    public AndroidDeviceActions androidDevice() {
        return new AndroidDeviceActions((AndroidDriver) get());
    }

    public IOSDeviceActions iosDevice() {
        return new IOSDeviceActions((IOSDriver) get());
    }

    //Soft Assertions
    public SoftAssertion softAssert() {
        return new SoftAssertion(get());
    }

    //Hard Assertions
    public HardAssertion hardAssert() {
        return new HardAssertion(get());
    }

    //Get Driver
    public AppiumDriver get() {
        return driverThreadLocal.get();
    }

    //Quit Driver
    public void quitDriver() {
        boolean isNotBrowser = !PropertyReader.getProperty("app.type").equalsIgnoreCase("web");
        if (isNotBrowser) {
            terminateApp();
        }
        get().quit();
        driverThreadLocal.remove();
    }

    private void terminateApp() {
        String appPackage = PropertyReader.getProperty("android.app.package");
        String appBundleId = PropertyReader.getProperty("ios.bundle.id");
        if (get() instanceof AndroidDriver) {
            androidDevice().closeApp(appPackage);
        } else {
            iosDevice().closeApp(appBundleId);
        }
        LogsManager.info("✅ App terminated successfully");
    }
}