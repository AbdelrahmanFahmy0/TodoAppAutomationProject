package com.todo.listeners;

import com.todo.drivers.AppiumDriverProvider;
import com.todo.drivers.UITest;
import com.todo.media.ScreenshotsManager;
import com.todo.server.AppiumServerManager;
import com.todo.utils.FileUtils;
import com.todo.utils.dataReader.PropertyReader;
import com.todo.utils.logs.LogsManager;
import com.todo.utils.report.AllureAttachmentManager;
import com.todo.utils.report.AllureConstants;
import com.todo.utils.report.AllureEnvironmentManager;
import com.todo.utils.report.AllureReportGenerator;
import com.todo.validations.SoftAssertion;
import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.testng.*;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestNGListeners implements ISuiteListener, IExecutionListener, IInvokedMethodListener, ITestListener {

    public void onStart(ISuite suite) {
        suite.getXmlSuite().setName("Todo App Test Suite");
        LogsManager.info("════════════════════════════════════════════════════");
        LogsManager.info("  SUITE: " + suite.getName() + " | Started: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        LogsManager.info("════════════════════════════════════════════════════");
    }

    @Override
    public void onFinish(ITestContext context) {
        LogsManager.info("╔══════════════════════════════════════════════╗");
        LogsManager.info("║  Suite finished: " + context.getName());
        LogsManager.info("║  Passed: " + context.getPassedTests().size());
        LogsManager.info("║  Failed: " + context.getFailedTests().size());
        LogsManager.info("║  Skipped: " + context.getSkippedTests().size());
        LogsManager.info("╚══════════════════════════════════════════════╝");
    }

    public void onExecutionStart() {
        LogsManager.info("Test Execution started");
        cleanTestOutputDirectories();
        LogsManager.info("Directories cleaned");
        createTestOutputDirectories();
        LogsManager.info("Directories created");
        PropertyReader.loadProperties();
        LogsManager.info("Properties loaded");
        AllureEnvironmentManager.setEnvironmentVariables();
        LogsManager.info("Allure environment set");
        // Starting Appium server
        AppiumServerManager.getInstance().startServer();
    }

    public void onExecutionFinish() {
        // Stopping Appium server
        AppiumServerManager.getInstance().stopServer();
        // Generating an Allure report
        AllureReportGenerator.copyHistory();
        AllureReportGenerator.generateReports(false);
        AllureReportGenerator.generateReports(true);
        if (PropertyReader.getProperty("OpenAllureReportAfterExecution").equalsIgnoreCase("true")) {
            AllureReportGenerator.openReport(AllureReportGenerator.renameReport());
        }
        LogsManager.info("Test Execution Finished");
    }


    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            LogsManager.info("Test Case " + testResult.getName() + " started");
        }
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        AppiumDriver driver = null;
        if (method.isTestMethod()) {
            // Check if the test class is annotated with @UITest
            boolean isUITest = testResult.getTestClass().getRealClass().isAnnotationPresent(UITest.class);
            // Perform these actions only for UI tests
            if (isUITest) {
                if (testResult.getInstance() instanceof AppiumDriverProvider provider) {
                    driver = provider.getAppiumDriver();
                }
                switch (testResult.getStatus()) {
                    case ITestResult.SUCCESS ->
                            ScreenshotsManager.takeFullPageScreenshot(driver, "passed-" + testResult.getName());
                    case ITestResult.FAILURE ->
                            ScreenshotsManager.takeFullPageScreenshot(driver, "failed-" + testResult.getName());
                    case ITestResult.SKIP ->
                            ScreenshotsManager.takeFullPageScreenshot(driver, "skipped-" + testResult.getName());
                }
            }
            SoftAssertion.assertAll(testResult);
            AllureAttachmentManager.attachLogs();
        }
    }

    public void onTestSuccess(ITestResult result) {
        LogsManager.info("✅ TEST PASSED: " + result.getName() + getDuration(result));
    }

    public void onTestFailure(ITestResult result) {
        LogsManager.info("❌ TEST FAILED: " + result.getName() + getDuration(result));
    }

    public void onTestSkipped(ITestResult result) {
        LogsManager.info("⏭  TEST SKIPPED: " + result.getName() + getDuration(result));
    }

    // Cleaning and creating dirs (logs, screenshots, allure-results)
    private void cleanTestOutputDirectories() {
        // Implement logic to clean test output directories
        FileUtils.cleanDirectory(AllureConstants.RESULTS_FOLDER.toFile());
        FileUtils.cleanDirectory(new File(ScreenshotsManager.SCREENSHOTS_PATH));
        LogManager.shutdown();
        FileUtils.forceFileDelete(new File(LogsManager.LOGS_PATH + "logs.log"));
    }

    // Create the necessary directories for test outputs
    private void createTestOutputDirectories() {
        // Implement logic to create test output directories
        FileUtils.createDirectory(ScreenshotsManager.SCREENSHOTS_PATH);
    }

    // Helper method to calculate test duration
    private long getDuration(ITestResult result) {
        return result.getEndMillis() - result.getStartMillis();
    }
}
