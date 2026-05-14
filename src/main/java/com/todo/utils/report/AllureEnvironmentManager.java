package com.todo.utils.report;

import com.todo.drivers.Platform;
import com.todo.utils.logs.LogsManager;
import com.google.common.collect.ImmutableMap;

import java.io.File;

import static com.todo.utils.dataReader.PropertyReader.getProperty;
import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

public class AllureEnvironmentManager {
    // Set Allure environment variables
    public static void setEnvironmentVariables() {
        // Base environment variables
        ImmutableMap.Builder<String, String> envBuilder = ImmutableMap.<String, String>builder()
                .put("OS", getProperty("os.name"))
                .put("Java Version:", getProperty("java.runtime.version"))
                .put("Server URL", getProperty("appium.server.url") + ":" + getProperty("appium.server.port"))
                .put("Platform", getProperty("platform").toUpperCase());

        // ─── Platform-specific ───
        if (Platform.isAndroid()) {
            envBuilder
                    .put("Device Name", getProperty("android.device.name"))
                    .put("App Package", getProperty("android.app.package"))
                    .put("Driver", getProperty("android.automation.name"));
        } else {
            envBuilder
                    .put("Device Name", getProperty("ios.device.name"))
                    .put("Bundle ID", getProperty("ios.bundle.id"))
                    .put("Driver", getProperty("ios.automation.name"));
        }
        allureEnvironmentWriter(envBuilder.build(), String.valueOf(AllureConstants.RESULTS_FOLDER) + File.separator);
        LogsManager.info("Allure environment variables set.");

        // Download and extract Allure binaries if not already present
        AllureBinaryManager.downloadAndExtract();
    }
}
