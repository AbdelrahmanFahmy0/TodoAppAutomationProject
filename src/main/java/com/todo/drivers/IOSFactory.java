package com.todo.drivers;

import com.todo.server.AppiumServerManager;
import com.todo.utils.dataReader.PropertyReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.openqa.selenium.Platform;

import java.net.URL;
import java.time.Duration;

public class IOSFactory extends AbstractDriver {

    XCUITestOptions options = new XCUITestOptions();

    private XCUITestOptions getOptions() {

        // ─── Device ───
        options.setDeviceName(PropertyReader.getProperty("ios.device.name"));
        options.setPlatformName(Platform.IOS.name());
        options.setPlatformVersion(PropertyReader.getProperty("ios.platform.version"));
        options.setAutomationName(PropertyReader.getProperty("ios.automation.name"));

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
        options.setNoReset(Boolean.parseBoolean(PropertyReader.getProperty("ios.no.reset"))); // Clean up app state after session

        // ─── WDA Config (for parallel execution on different ports) ───
        options.setWdaLaunchTimeout(Duration.ofSeconds(60));
        options.setWdaLocalPort(Integer.parseInt(PropertyReader.getProperty("ios.wda.local.port")));

        // ─── Stability ───
        options.setAutoAcceptAlerts(true);
        options.setAutoDismissAlerts(false);
        options.setUsePrebuiltWda(false);
        options.setSimulatorStartupTimeout(Duration.ofSeconds(120));

        return options;
    }

    @Override
    public void configureNativeOrHybridApp() {
        String appPath = PropertyReader.getProperty("ios.app.path");
        String bundleId = PropertyReader.getProperty("ios.bundle.id");
        boolean isAppInstalled = Boolean.parseBoolean(PropertyReader.getProperty("app.installed"));
        if (appPath != null && !appPath.isBlank() && !isAppInstalled) {
            options.setApp(appPath);
        } else {
            options.setBundleId(bundleId);
        }
    }

    @Override
    public void configureHybridCapabilities() {
        options.setAutoWebview(true);
        options.setCapability("appium:includeSafariInWebviews", true);
        options.setCapability("appium:webviewConnectTimeout", 10000);
    }

    @Override
    public void configureWebCapabilities() {
        options.setCapability("browserName", PropertyReader.getProperty("browser.name"));
    }

    @Override
    public AppiumDriver createDriver() {
        URL serverUrl = AppiumServerManager.getInstance().getServerUrl();
        return new IOSDriver(serverUrl, getOptions());
    }
}