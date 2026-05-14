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
@Feature("Edit Todo")
@UITest
public class EditTodoTest extends BaseTest {

    // Variables
    String todoName;

    // Tests
    @BeforeMethod
    public void setupTodoTest() {
        todoName = testData.getJsonData("todoName") + getSimpleTimestamp();
        driver = new GUIDriver();
        new TodosPage(driver).clickAddButton().enterTodoName(todoName).clickSaveButton();
    }

    @Test(description = "Edit a todo item successfully")
    @Description("This test verifies that a user can edit a todo item successfully.")
    @Severity(SeverityLevel.CRITICAL)
    public void editTodoTest() {
        String newTodoName = todoName + " - Edited";
        new TodosPage(driver)
                .swipeTodoForEdit(todoName)
                .enterTodoName(newTodoName)
                .clickSaveButton()
                .verifyTodoIsEdited(todoName, newTodoName);
    }

    @Test(description = "Edit a todo item with special characters")
    @Description("This test verifies that a user can edit a todo item with special characters in the name.")
    @Severity(SeverityLevel.NORMAL)
    public void editTodoWithSpecialCharactersTest() {
        String newTodoName = todoName + " @#$%^&*()";
        new TodosPage(driver)
                .swipeTodoForEdit(todoName)
                .enterTodoName(newTodoName)
                .clickSaveButton()
                .verifyTodoIsEdited(todoName, newTodoName);
    }

    @Test(description = "Edit a completed todo item")
    @Description("This test verifies that a user can edit a completed todo item.")
    @Severity(SeverityLevel.NORMAL)
    public void editCompletedTodoTest() {
        String newTodoName = todoName + " - Completed";
        new TodosPage(driver)
                .changeTodoStatus(todoName)
                .swipeTodoForEdit(todoName)
                .enterTodoName(newTodoName)
                .clickSaveButton()
                .verifyTodoIsEdited(todoName, newTodoName);
    }

    @Test(description = "Edit a todo item with a long name")
    @Description("This test verifies that a user can edit a todo item with a long name.")
    @Severity(SeverityLevel.MINOR)
    public void editTodoWithLongNameTest() {
        String longName = "kjhgfdsapoiuytrewq1234567890lkjhgfdsapoiuytrewq1234567890";
        String newTodoName = todoName + longName;
        new TodosPage(driver)
                .swipeTodoForEdit(todoName)
                .enterTodoName(newTodoName)
                .clickSaveButton()
                .verifyTodoIsEdited(todoName, newTodoName);
    }

    @Test(description = "Edit a todo item with a duplicate name")
    @Description("This test verifies that a user can edit a todo item to have a duplicate name.")
    @Severity(SeverityLevel.NORMAL)
    public void editTodoWithDuplicateNameTest() {
        String secondTodoName = todoName + " Second";
        new TodosPage(driver)
                .clickAddButton()
                .enterTodoName(secondTodoName)
                .clickSaveButton()
                .swipeTodoForEdit(secondTodoName)
                .enterTodoName(todoName)
                .clickSaveButton()
                .verifyTodoIsEdited(secondTodoName, todoName)
                .verifyDuplicatedTodosAdded(todoName);
    }

    @Test(description = "Cancel editing a todo item")
    @Description("This test verifies that a user can edit a todo item to use an existing todo name.")
    @Severity(SeverityLevel.MINOR)
    public void cancelTodoEditingTest() {
        new TodosPage(driver)
                .swipeTodoForEdit(todoName)
                .dismissPopupByTap()
                .verifyAddTodoPopupIsHidden()
                .verifyTodoIsDisplayed(todoName);
    }

    @AfterMethod
    public void tearDown() {
        driver.quitDriver();
    }
}