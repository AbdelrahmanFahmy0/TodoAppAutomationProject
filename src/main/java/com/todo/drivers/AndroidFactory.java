package com.todo.drivers;

import com.todo.server.AppiumServerManager;
import com.todo.utils.dataReader.PropertyReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.Platform;

import java.net.URL;

public class AndroidFactory extends AbstractDriver {

    UiAutomator2Options options = new UiAutomator2Options();

    private UiAutomator2Options getOptions() {

        // ─── Device ───
        options.setDeviceName(PropertyReader.getProperty("android.device.name"));
        options.setPlatformName(Platform.ANDROID.name());
        options.setAutomationName(PropertyReader.getProperty("android.automation.name"));

        // ─── App ───
        String appType = PropertyReader.getProperty("app.type").toUpperCase();
        switch (appType) {
            case "NATIVE":
                // Native-specific options
                configureNativeOrHybridApp();
                break;
            case "HYBRID":
                // Hybrid-specific options
                configureNativeOrHybridApp();
                configureHybridCapabilities();
                break;
            case "WEB":
                // Web-specific options
                configureWebCapabilities();
                break;
        }

        // ─── Resets ───
        options.setNoReset(Boolean.parseBoolean(PropertyReader.getProperty("android.no.reset"))); // Clean up app state after session

        // ─── Stability / Performance ───
        options.setAutoGrantPermissions(true);
        options.setIgnoreHiddenApiPolicyError(true);

        return options;
    }

    @Override
    public void configureNativeOrHybridApp() {
        String appPath = PropertyReader.getProperty("android.app.path");
        boolean isAppInstalled = Boolean.parseBoolean(PropertyReader.getProperty("app.installed"));
        if (appPath != null && !appPath.isBlank() && !isAppInstalled) {
            options.setApp(appPath);
        } else {
            options.setAppPackage(PropertyReader.getProperty("android.app.package"));
            options.setAppActivity(PropertyReader.getProperty("android.app.activity"));
        }
    }

    @Override
    public void configureHybridCapabilities() {
        options.setChromedriverExecutable(PropertyReader.getProperty("chrome.driver.path"));
        options.setAutoWebview(true);
    }

    @Override
    public void configureWebCapabilities() {
        options.setCapability("browserName", PropertyReader.getProperty("browser.name"));
        options.setChromedriverExecutable(PropertyReader.getProperty("chrome.driver.path"));
    }

    @Override
    public AppiumDriver createDriver() {
        URL serverUrl = AppiumServerManager.getInstance().getServerUrl();
        return new AndroidDriver(serverUrl, getOptions());
    }
}