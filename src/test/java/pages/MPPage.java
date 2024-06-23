package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class MPPage {

    public WebDriver driver;
    public MPPage(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//a[@title='Расписание']")
    private WebElement linkSchedule;

    @FindBy(xpath = "//a[@href='https://rasp.dmami.ru/session']")
    private WebElement LinkSchedulePageGroup;

    @FindBy(xpath = "//input[@class='groups']")
    private WebElement inputGroups;

    @FindBy(xpath = "//div[contains(@class, 'found-groups')]")
    private WebElement groupsList;

    @FindBy(xpath = "//div[starts-with(@class,'schedule-day')]")
    private List<WebElement> dayOfWeeks;

    public int getDayOfWeek() {
        LocalDate currentDate = LocalDate.now();
        DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
        return dayOfWeek.getValue();
    }

    public void clickScheduleMenu(){
        linkSchedule.click();
    }

    public void clickLinkSchedulePageGroup(){
        String originalWindow = driver.getWindowHandle();
        LinkSchedulePageGroup.click();

        for (String windowHandle : driver.getWindowHandles()) {
            if(!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
    }

    public void inputGroup(String group){
        inputGroups.sendKeys(group);
    }

    public boolean checkListGroups(String group){
        List<WebElement> webElements = groupsList.findElements(By.tagName("div"));
        if (webElements.size() == 1 && webElements.get(0).getText().equals(group)) return true;
        else return false;
    }

    public void clickGroup(String group){
        groupsList.findElements(By.tagName("div")).get(0).click();
    }

    public boolean checkSelectedWeekDay(int weekDay){
        return (weekDay == getDayOfWeek());
    }
}
