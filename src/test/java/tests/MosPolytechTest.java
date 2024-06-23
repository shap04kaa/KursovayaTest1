package tests;

import driver.DriverSetup;
import driver.TestListener;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.MPPage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(TestListener.class)
@Feature("Tests Moscow Polytech")
public class MosPolytechTest extends DriverSetup {

    public static MPPage mosPolytechPage;
    private static final Logger logger = LoggerFactory.getLogger(MosPolytechTest.class);

    private int getDayWeekNumber(){
        return LocalDate.now().getDayOfWeek().getValue();
    }

    @Step("Page initialization")
    private void init(){
        logger.info("Start test page mospolytech");
        mosPolytechPage = new MPPage(driver);
        driver.get(DriverSetup.getProperties("mospolytech"));
        logger.info("Page get success");
    }

    @Step("Checking schedule page")
    private void CheckSchedulePage() {
        mosPolytechPage.clickScheduleMenu();
        logger.info("Click button schedule");
    }

    @Step("Checking group schedule")
    private void CheckGroupSchedule() {
        mosPolytechPage.clickLinkSchedulePageGroup();
        logger.info("Click button schedule group");

        driver.manage().deleteCookieNamed("group");
        driver.navigate().refresh();
        mosPolytechPage.inputGroup("221-361");
        logger.info("Input group");

        assertTrue(mosPolytechPage.checkListGroups("221-361"));
        logger.info("Group list checked");

        mosPolytechPage.clickGroup("221-361");
        logger.info("Group selected");
    }

    @Step("Checking day of week")
    private void CheckDay() {
        int numDay = getDayWeekNumber();

        if (numDay == 7){
            logger.info("The day is not chosen because it is Sunday");
        }else {
            assertTrue(mosPolytechPage.checkSelectedWeekDay(numDay));
            logger.info("Check selected week day");
        }
    }

    @Test
    @Link(name = "Moscow Polytech", url = "https://mospolytech.ru/")
    @DisplayName("Checking the schedule display on the Moscow Polytechnic website")
    @Description("Go to the Moscow Polytech website, click on the schedule, click on the schedule again, go to the schedule page, enter the group, select the group, check the highlight of the weekday.")
    @Epic("Test for site https://mospolytech.ru/ ")
    public void test(){
        init();
        CheckSchedulePage();
        CheckGroupSchedule();
        CheckDay();
    }
}
