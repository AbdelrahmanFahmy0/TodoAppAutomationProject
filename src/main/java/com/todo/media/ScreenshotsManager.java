package com.todo.media;

import com.todo.utils.TimeManager;
import com.todo.utils.logs.LogsManager;
import com.todo.utils.report.AllureAttachmentManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;

public class ScreenshotsManager {

    public static final String SCREENSHOTS_PATH = "test-output/screenshots/";

    // Take full page screenshot
    public static void takeFullPageScreenshot(WebDriver driver, String screenshotName) {
        try {
            // Capture screenshot using TakesScreenshot
            File screenshotSrc = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            // Save screenshot to a file
            File screenshotFile = new File(SCREENSHOTS_PATH + screenshotName + "-" + TimeManager.getTimestamp() + ".png");
            FileUtils.copyFile(screenshotSrc, screenshotFile);
            // Attach the screenshot to Allure report
            AllureAttachmentManager.attachScreenshot(screenshotName, screenshotFile.getAbsolutePath());
            LogsManager.info("Capturing Screenshot Succeeded");
        } catch (Exception e) {
            LogsManager.error("Failed to Capture Screenshot " + e.getMessage());
        }
    }

    // Take a screenshot of a specific element
    public static void takeElementScreenshot(WebDriver driver, By elementSelector) {
        try {
            // Capture screenshot using TakesScreenshot
            String ariaName = driver.findElement(elementSelector).getAccessibleName();
            File screenshotSrc = driver.findElement(elementSelector).getScreenshotAs(OutputType.FILE);
            // Save screenshot to a file
            File screenshotFile = new File(SCREENSHOTS_PATH + ariaName + "-" + TimeManager.getTimestamp() + ".png");
            FileUtils.copyFile(screenshotSrc, screenshotFile);
            // Attach the screenshot to Allure report
            AllureAttachmentManager.attachScreenshot(ariaName, screenshotFile.getAbsolutePath());
            LogsManager.info("Capturing Screenshot Succeeded");
        } catch (Exception e) {
            LogsManager.error("Failed to Capture Element Screenshot", e.getMessage());
        }
    }
}
