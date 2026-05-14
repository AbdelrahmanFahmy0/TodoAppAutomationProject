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
@Feature("Complete Todo")
@UITest
public class CompleteTodoTest extends BaseTest {

    // Variables
    String todoName;

    // Tests
    @BeforeMethod
    public void setupTodoTest() {
        todoName = testData.getJsonData("todoName") + getSimpleTimestamp();
        driver = new GUIDriver();
        new TodosPage(driver).clickAddButton().enterTodoName(todoName).clickSaveButton();
    }

    @Test(description = "Complete a todo item successfully")
    @Description("This test verifies that a user can complete a todo item successfully.")
    @Severity(SeverityLevel.CRITICAL)
    public void completeTodoTest() {
        new TodosPage(driver)
                .changeTodoStatus(todoName)
                .verifyTodoIsCompleted(todoName);
    }

    @Test(description = "Uncheck a completed todo item")
    @Description("This test verifies that a user can uncheck a completed todo item and mark it as not completed.")
    @Severity(SeverityLevel.NORMAL)
    public void uncheckCompletedTodoTest() {
        new TodosPage(driver)
                .changeTodoStatus(todoName)
                .verifyTodoIsCompleted(todoName)
                .changeTodoStatus(todoName)
                .verifyTodoIsNotCompleted(todoName);
    }

    @Test(description = "Verify that a newly added todo item is unchecked by default")
    @Description("This test verifies that when a new todo item is added, it is not checked by default.")
    @Severity(SeverityLevel.NORMAL)
    public void verifyNewTodoIsUnchecked() {
        new TodosPage(driver)
                .verifyTodoIsNotCompleted(todoName);
    }

    @AfterMethod
    public void tearDown() {
        driver.quitDriver();
    }
}
