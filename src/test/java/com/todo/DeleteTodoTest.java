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
@Feature("Delete Todo")
@UITest
public class DeleteTodoTest extends BaseTest {

    // Variables
    String todoName;

    // Tests
    @BeforeMethod
    public void setupTodoTest() {
        todoName = testData.getJsonData("todoName") + getSimpleTimestamp();
        driver = new GUIDriver();
        new TodosPage(driver).clickAddButton().enterTodoName(todoName).clickSaveButton();
    }

    @Test(description = "Delete a todo item successfully")
    @Description("This test verifies that a user can delete a todo successfully by swiping it and confirming the deletion.")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteTodoTest() {
        new TodosPage(driver)
                .swipeTodoForDelete(todoName)
                .clickConfirmButton()
                .verifyTodoIsDeleted(todoName);
    }

    @Test(description = "Cancel todo deletion")
    @Description("This test verifies that a user can cancel the deletion of a todo item.")
    @Severity(SeverityLevel.NORMAL)
    public void cancelTodoDeletionTest() {
        new TodosPage(driver)
                .swipeTodoForDelete(todoName)
                .clickCancelButton()
                .verifyTodoIsDisplayed(todoName);
    }

    @Test(description = "Delete a completed todo item")
    @Description("This test verifies that a user can delete a completed todo item successfully.")
    @Severity(SeverityLevel.NORMAL)
    public void deleteCompletedTodoTest() {
        new TodosPage(driver)
                .changeTodoStatus(todoName)
                .swipeTodoForDelete(todoName)
                .clickConfirmButton()
                .verifyTodoIsDeleted(todoName);
    }

    @AfterMethod
    public void tearDown() {
        driver.quitDriver();
    }
}