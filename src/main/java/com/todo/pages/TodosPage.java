package com.todo.pages;

import com.todo.drivers.GUIDriver;
import com.todo.utils.actions.Direction;
import io.appium.java_client.AppiumBy;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class TodosPage {

    // Variables
    private final GUIDriver driver;

    // Constructor
    public TodosPage(GUIDriver driver) {
        this.driver = driver;
    }

    // Locators
    private final By addButton = AppiumBy.accessibilityId("Add");
    private final By todoNameInput = AppiumBy.id("com.todoproject.todoapp:id/edittext");
    private final By saveButton = AppiumBy.id("com.todoproject.todoapp:id/button_save");
    private final By confirmButton = AppiumBy.id("android:id/button1");
    private final By cancelButton = AppiumBy.id("android:id/button2");

    // Dynamic Locators
    private By todo(String name) {
        return AppiumBy.xpath("(//android.widget.CheckBox[@text='" + name + "'])[1]");
    }

    private By todoItem(String name) {
        return AppiumBy.xpath("//android.widget.CheckBox[@text='" + name + "']");
    }

    private By firstTodo() {
        return AppiumBy.xpath("(//android.widget.CheckBox)[1]");
    }

    // Actions
    @Step("Click Add Button")
    public TodosPage clickAddButton() {
        driver.element().click(addButton);
        return this;
    }

    @Step("Enter Todo Name: {name}")
    public TodosPage enterTodoName(String name) {
        driver.element().type(todoNameInput, name);
        return this;
    }

    @Step("Click Save Button")
    public TodosPage clickSaveButton() {
        driver.element().click(saveButton);
        return this;
    }

    @Step("Dismiss Popup by Tapping Outside")
    public TodosPage dismissPopupByTap() {
        driver.softAssert().isElementVisible(todoNameInput);
        driver.androidGesture().tapOnScreenCenter();
        return this;
    }

    @Step("Change Todo Status: {name}")
    public TodosPage changeTodoStatus(String name) {
        driver.element().click(todo(name));
        return this;
    }

    @Step("Swipe Todo for Edit: {name}")
    public TodosPage swipeTodoForEdit(String name) {
        driver.androidGesture().swipe(todo(name), Direction.LEFT);
        return this;
    }

    @Step("Swipe Todo for Delete: {name}")
    public TodosPage swipeTodoForDelete(String name) {
        driver.androidGesture().swipe(todo(name), Direction.RIGHT);
        return this;
    }

    @Step("Click Confirm Button")
    public TodosPage clickConfirmButton() {
        driver.element().click(confirmButton);
        return this;
    }

    @Step("Click Cancel Button")
    public TodosPage clickCancelButton() {
        driver.element().click(cancelButton);
        return this;
    }

    private String getFirstTodoName() {
        return driver.element().getAttributeValue(firstTodo(), "text");
    }

    // Assertions
    @Step("Verify Todo is Displayed: {name}")
    public TodosPage verifyTodoIsDisplayed(String name) {
        driver.softAssert().isElementVisible(todo(name));
        return this;
    }

    @Step("Verify Todo is Edited: Old Name: {oldName}, New Name: {newName}")
    public TodosPage verifyTodoIsEdited(String oldName, String newName) {
        driver.softAssert().isElementHidden(todo(oldName));
        driver.softAssert().isElementVisible(todo(newName));
        return this;
    }

    @Step("Verify Todo is Deleted: {name}")
    public TodosPage verifyTodoIsDeleted(String name) {
        driver.softAssert().isElementHidden(todo(name));
        return this;
    }

    @Step("Verify Add Todo Popup is Hidden")
    public TodosPage verifyAddTodoPopupIsHidden() {
        driver.softAssert().isElementHidden(todoNameInput);
        return this;
    }

    @Step("Verify Todo is Completed: {name}")
    public TodosPage verifyTodoIsCompleted(String name) {
        driver.softAssert().elementAttributeHasValue(todo(name), "checked", "true");
        return this;
    }

    @Step("Verify Todo is Not Completed: {name}")
    public TodosPage verifyTodoIsNotCompleted(String name) {
        driver.softAssert().elementAttributeHasValue(todo(name), "checked", "false");
        return this;
    }

    @Step("Verify Duplicate Todos Are Added: {name}")
    public TodosPage verifyDuplicatedTodosAdded(String name) {
        boolean isDuplicate = driver.element().findElements(todoItem(name)).size() > 1;
        driver.softAssert().assertTrue(isDuplicate, "Expected duplicated todos to exist but they were not found");
        return this;
    }

    @Step("Verify Todo is Added First: {name}")
    public TodosPage verifyTodoIsAddedFirst(String name) {
        String firstTodoName = getFirstTodoName();
        driver.softAssert().assertEquals(firstTodoName, name, "The first todo item is not the expected one");
        return this;
    }
}