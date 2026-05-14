package com.todo;

import com.todo.drivers.AppiumDriverProvider;
import com.todo.drivers.GUIDriver;
import com.todo.utils.dataReader.JsonReader;
import io.appium.java_client.AppiumDriver;
import org.testng.annotations.BeforeClass;

public class BaseTest implements AppiumDriverProvider {

    // Variables
    protected GUIDriver driver;
    protected JsonReader testData = new JsonReader("todo-data");

    // Getting Active AppiumDriver instance
    @Override
    public AppiumDriver getAppiumDriver() {
        return driver.get();
    }
}
