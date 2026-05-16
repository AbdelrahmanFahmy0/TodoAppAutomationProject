package com.todo;

import com.todo.drivers.AppiumDriverProvider;
import com.todo.drivers.GUIDriver;
import com.todo.utils.dataReader.JsonReader;
import com.todo.utils.dataReader.PropertyReader;
import io.appium.java_client.AppiumDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

public class BaseTest implements AppiumDriverProvider {

    // Variables
    protected GUIDriver driver;
    protected JsonReader testData = new JsonReader("todo-data");
    protected String appPackage = PropertyReader.getProperty("android.app.package");

    // Getting Active AppiumDriver instance
    @Override
    public AppiumDriver getAppiumDriver() {
        return driver.get();
    }

    // Hooks
    @AfterMethod
    public void postCondition() {
        driver.androidDevice().clearAppData(appPackage);
        driver.androidDevice().closeApp(appPackage);
    }

    @AfterClass
    public void tearDown() {
        driver.quitDriver();
    }
}
