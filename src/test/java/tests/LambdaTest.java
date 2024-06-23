package tests;

import io.qameta.allure.*;
import driver.DriverSetup;
import pages.LambdaPage;
import driver.TestListener;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TestListener.class)
@Feature("Тесты сайта lambda sample app")
public class LambdaTest extends DriverSetup {

    public static LambdaPage lambdaPage;
    private static final Logger logger = LoggerFactory.getLogger(LambdaTest.class);

    @Step("Page initialization")
    private void init(){
        logger.info("Start test page lambda sample app");
        lambdaPage = new LambdaPage(driver);
        driver.get(DriverSetup.getProperties("lambdaPage"));
        logger.info("Page get success");
    }

    @Step("Checking the list item")
    public void checkItem(Integer itemNum){
        logger.info("Item {} start check", itemNum);
        assertFalse(lambdaPage.isTickBox(itemNum));
        logger.info("Item not crossed out");

        lambdaPage.selectItem(itemNum);
        logger.info("Item clicked");

        assertTrue(lambdaPage.isTickBox(itemNum));
        logger.info("Item crossed out");

        assertEquals(lambdaPage.getNumCheckboxes(), (lambdaPage.getSizeList()-itemNum-1) + " of " + lambdaPage.getSizeList() + " remaining");
        logger.info("Label remaining checked");

        logger.info("Item checked " + itemNum);
    }

    @Step("Checking page header")
    public void testPageHeader(){
        assertEquals(lambdaPage.getHeadText(), "LambdaTest Sample App");
        logger.info("Text headPage equals LambdaTest Sample App");
    }

    @Step("Checking existing items")
    public void testExistingItems(){
        for (int i = 0; i < lambdaPage.getSizeList(); i++) {
            checkItem(i);
        }
        logger.info("All items checked");
    }

    @Step("Checking adding new item")
    public void testAddNewItem(){
        lambdaPage.addNewElement("New Item");
        logger.info("New item created with name 'New Item'");

        assertFalse(lambdaPage.isTickBox(lambdaPage.getSizeList() - 1));
        logger.info("Item not crossed out");

        assertEquals(lambdaPage.getNumCheckboxes(), "1 of " + lambdaPage.getSizeList() + " remaining");
        logger.info("Label remaining checked");

        checkItem(lambdaPage.getSizeList() - 1);
        logger.info("Last item checked");
    }

    @Test
    @Link(name = "LambdaTest Sample App", url = "https://lambdatest.github.io/sample-todo-app ")
    @DisplayName("Checking LambdaTest Sample App")
    @Description("Go to the LambdaTest Sample App, click on each item, create a new item, verify it.")
    @Epic("Test for site https://lambdatest.github.io/sample-todo-app ")
    public void test(){
        init();
        testPageHeader();
        testExistingItems();
        testAddNewItem();
    }
}
