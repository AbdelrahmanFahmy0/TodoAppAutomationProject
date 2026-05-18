package com.todo.server;

import com.todo.drivers.Platform;
import com.todo.utils.dataReader.PropertyReader;
import com.todo.utils.logs.LogsManager;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

import java.io.OutputStream;
import java.net.*;
import java.time.Duration;

import static com.todo.utils.PortUtils.killProcessOnPort;

/**
 * Manages the lifecycle of the Appium local server.
 * Thread-safe singleton that starts and stops the Appium server once
 * per test suite — not once per test.
 */
public class AppiumServerManager {

    private static volatile AppiumServerManager instance;
    private AppiumDriverLocalService service;
    public final String serverUrl = PropertyReader.getProperty("appium.server.url");
    public final int serverPort = Integer.parseInt(PropertyReader.getProperty("appium.server.port"));

    private AppiumServerManager() {
    }

    /**
     * Returns the singleton instance of AppiumServerManager.
     * Uses double-checked locking for thread safety and performance.
     */
    public static AppiumServerManager getInstance() {
        if (instance == null) {
            synchronized (AppiumServerManager.class) {
                if (instance == null) {
                    instance = new AppiumServerManager();
                }
            }
        }
        return instance;
    }

    /**
     * Starts the Appium server if auto-start is enabled and it isn't already running.
     */
    public synchronized void startServer() {

        if (isRunning()) {
            LogsManager.info("Appium server is already running at port " + serverPort);
            return;
        }

        LogsManager.info("Starting Appium server on port " + serverPort + " ...");
        // Build the Appium service with custom configuration
        AppiumServiceBuilder builder = new AppiumServiceBuilder()
                .withIPAddress(serverUrl.replace("http://", ""))
                .usingPort(serverPort)
                .withTimeout(Duration.ofSeconds(60))
                .withArgument(() -> "--use-drivers", getDriverArg())
                .withArgument(() -> "--log-level", "error")
                .withArgument(() -> "--log-timestamp")
                .withArgument(() -> "--relaxed-security");
        service = AppiumDriverLocalService.buildService(builder);

        // Pipe Appium logs into your LogsManager
        service.addOutPutStream(new OutputStream() {
            private final StringBuilder buffer = new StringBuilder();

            @Override
            public void write(int b) {
                char c = (char) b;
                if (c == '\n') {
                    String line = buffer.toString().trim();
                    if (!line.isEmpty()) {
                        LogsManager.error("[Appium] " + line);
                    }
                    buffer.setLength(0);
                } else {
                    buffer.append(c);
                }
            }
        });

        // Start the service
        service.start();

        if (service.isRunning()) {
            LogsManager.info("✅ Appium server started successfully at " + service.getUrl());
        } else {
            throw new RuntimeException("❌ Appium server failed to start!");
        }
    }

    /**
     * Stops the Appium server if it is running.
     */
    public synchronized void stopServer() {
        if (service != null) {
            LogsManager.info("Stopping Appium server ...");
            service.stop();
            killProcessOnPort(serverPort); // Ensure the port is freed
            LogsManager.info("✅ Appium server stopped.");
        }
    }

    /**
     * Returns the server URL for driver initialization.
     */
    public URL getServerUrl() {
        try {
            if (isRunning()) {
                return service.getUrl();
            }
            return new URI(serverUrl + ":" + serverPort).toURL();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get Appium server URL", e);
        }
    }

    /**
     * Checks if the Appium server is currently running.
     */
    public boolean isRunning() {
        return service != null && service.isRunning();
    }

    /**
     * Determines the appropriate driver argument based on the platform.
     */
    private String getDriverArg() {
        if (Platform.isAndroid()) {
            return PropertyReader.getProperty("android.automation.name").toLowerCase();
        } else {
            return PropertyReader.getProperty("ios.automation.name").toLowerCase();
        }
    }
}