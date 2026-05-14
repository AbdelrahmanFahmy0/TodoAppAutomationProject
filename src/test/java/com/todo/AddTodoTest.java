package com.todo;

import com.todo.drivers.GUIDriver;
import com.todo.drivers.UITest;
import com.todo.pages.TodosPage;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.todo.utils.TimeManager.getSimpleTimestamp;

@Owner("Abdelrahman Fahmy")
@Epic("UI Tests")
@Feature("Add Todo")
@UITest
public class AddTodoTest extends BaseTest {

    // Variables
    String todoName;

    // Tests
    @BeforeMethod
    public void setupTodoTest() {
        todoName = testData.getJsonData("todoName") + getSimpleTimestamp();
        driver = new GUIDriver();
    }

    @Test(description = "Add a new todo item successfully")
    @Description("This test verifies that a user can add a new todo item successfully.")
    @Severity(SeverityLevel.BLOCKER)
    public void addTodoTest() {
        new TodosPage(driver)
                .clickAddButton()
                .enterTodoName(todoName)
                .clickSaveButton()
                .verifyTodoIsDisplayed(todoName);
    }

    @Test(description = "Add a new todo item with special characters in the name")
    @Description("This test verifies that a user can add a new todo item with special characters in the name.")
    @Severity(SeverityLevel.NORMAL)
    public void addTodoWithSpecialCharactersTest() {
        String specialCharTodoName = todoName + " !@#$%^&*()_+";
        new TodosPage(driver)
                .clickAddButton()
                .enterTodoName(specialCharTodoName)
                .clickSaveButton()
                .verifyTodoIsDisplayed(specialCharTodoName);
    }

    @Test(description = "Add a new todo item with a long name")
    @Description("This test verifies that a user can add a new todo item with a long name.")
    @Severity(SeverityLevel.NORMAL)
    public void addTodoWithLongNameTest() {
        String longName = "tyrtynjihbgvfcdxswqazertyuioplkjhgfdsazxcvbnm1234567890";
        String longTodoName = todoName + longName;
        new TodosPage(driver)
                .clickAddButton()
                .enterTodoName(longTodoName)
                .clickSaveButton()
                .verifyTodoIsDisplayed(longTodoName);
    }

    @Test(description = "Add a duplicate todo item")
    @Description("This test verifies that a user can add a duplicate todo item.")
    @Severity(SeverityLevel.NORMAL)
    public void addDuplicateTodoTest() {
        new TodosPage(driver)
                .clickAddButton()
                .enterTodoName(todoName)
                .clickSaveButton()
                .clickAddButton()
                .enterTodoName(todoName)
                .clickSaveButton()
                .verifyDuplicatedTodosAdded(todoName);
    }

    @Test(description = "Close Add Todo popup by tapping on the screen")
    @Description("This test verifies that the Add Todo popup can be closed by tapping on the screen outside the popup.")
    @Severity(SeverityLevel.MINOR)
    public void closeAddTodoPopupTest() {
        new TodosPage(driver)
                .clickAddButton()
                .dismissPopupByTap()
                .verifyAddTodoPopupIsHidden();
    }

    @Test(description = "Verify that a newly added todo item is added at the top of the list")
    @Description("This test verifies that when a new todo item is added, it is added at the top of the list.")
    @Severity(SeverityLevel.NORMAL)
    public void verifyTodoIsAddedFirst() {
        new TodosPage(driver)
                .clickAddButton()
                .enterTodoName(todoName)
                .clickSaveButton()
                .verifyTodoIsAddedFirst(todoName);
    }

    @AfterMethod
    public void tearDown() {
        driver.quitDriver();
    }
}