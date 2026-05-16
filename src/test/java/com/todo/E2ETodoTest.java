package com.todo;

import com.todo.drivers.GUIDriver;
import com.todo.drivers.UITest;
import com.todo.pages.TodosPage;
import io.qameta.allure.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.todo.utils.TimeManager.getSimpleTimestamp;

@Owner("Abdelrahman Fahmy")
@Epic("UI Tests")
@Feature("E2E Todo Test")
@UITest
public class E2ETodoTest extends BaseTest {

    // Variables
    String todoName = testData.getJsonData("todoName") + getSimpleTimestamp();
    String updatedTodoName = todoName + " Updated";

    // Tests
    @BeforeClass
    public void setup() {
        driver = new GUIDriver();
    }

    @BeforeMethod
    public void preCondition() {
        driver.androidDevice().openApp(appPackage);
    }

    @Test(description = "End-to-end test for a todo item")
    @Description("This test verifies the complete flow of creating a new todo item, updating its name, changing its status, and finally deleting it.")
    @Severity(SeverityLevel.BLOCKER)
    public void e2eTodoTest() {
        new TodosPage(driver)
                .clickAddButton()
                .enterTodoName(todoName)
                .clickSaveButton()
                .verifyTodoIsDisplayed(todoName)
                .changeTodoStatus(todoName)
                .verifyTodoIsCompleted(todoName)
                .swipeTodoForEdit(todoName)
                .enterTodoName(updatedTodoName)
                .clickSaveButton()
                .verifyTodoIsEdited(todoName, updatedTodoName)
                .swipeTodoForDelete(updatedTodoName)
                .clickConfirmButton()
                .verifyTodoIsDeleted(updatedTodoName);
    }
}
