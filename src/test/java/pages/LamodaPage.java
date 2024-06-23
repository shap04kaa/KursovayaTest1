package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LamodaPage {
    private final WebDriver driver;

    public LamodaPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    //поиск
    @FindBy(xpath = "//input[@type='text' and @maxlength='128' and @placeholder='Поиск' and contains(@class, '_input_1su1z_19') and contains(@class, '_inputShown_1su1z_43')]\n")
    private WebElement searchForm;

    @FindBy(xpath = "//button[@type='button' and @class='x-button x-button_primaryPremium x-button_32 _button_1su1z_11' and @aria-label='' and @role='button']/div[@class='icon icon__search-white icon_direction-down icon_24 _icon_1su1z_15']\n")
    private WebElement searchButton;

    @FindBy(xpath = "//input[@name='Электронная почта' and @type='text' and @class='input-material__input-user-agent input-material__input' and @aria-label='Электронная почта' and @autocomplete='username']\n")
    private WebElement logInFormForEmail;

    @FindBy(xpath = "//input[@name='password' and @type='password' and @class='input-material__input-user-agent input-material__input' and @autocomplete='current-password']\n")
    private WebElement logInFormForPassword;

    @FindBy(xpath = "//button[@class='x-button x-button_secondaryFilledWeb7184 x-button_32 _button_1xhq2_8 _item_1xhq2_13' and @type='button' and text()='Войти']\n")
    private WebElement logInButton1;

    @FindBy(xpath = "//button[@class='x-button x-button_primaryFilledWeb7184 x-button_56 x-button_intrinsic-width _submit_7r0bx_31' and @type='submit' and @aria-label='Войти' and text()='Войти']\n")
    private WebElement logInButton2;

    @FindBy(xpath = "//div[@class='input-material__placeholder input-material__placeholder' and text()='Введите пароль']")
    private WebElement passwordLine;

    @FindBy(xpath = "//span[@class='_text_1jcg6_41' and text()='Профиль']\n")
    private WebElement accountButton;

    @FindBy(xpath = "//div[contains(@class, 'x-product-card-description__brand-name') and contains(@class, 'x-product-card-description__brand-name_faded') and text() = 'Nike']")
    private WebElement nikeTitle;

    @FindBy(xpath = "//span[@class='_text_1jcg6_41' and text() = 'Профиль']")
    private WebElement accountTitle;

    @FindBy(xpath = "//span[normalize-space(text())='Эксклюзивы и скидки']\n")
    private WebElement discontButton;

    @FindBy(xpath = "//div[@class='_titleWrapper_14umw_6' and contains(text(), 'Избранное')]\n")
    private WebElement noFavourites;

    @FindBy(xpath = "//div[@class='icon icon_heart-catalog icon_direction-down icon_32 undefined']")
    private WebElement addToFavouriteButton;

    @FindBy(xpath = "//span[@class='_text_y38rd_13' and text()='Избранное']\n")
    private WebElement favouriteButtonMenu;

    public void searchItem(String query) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(searchForm));
        searchForm.clear();
        searchForm.sendKeys(query);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        searchButton.click();
    }

    public void addEmail(String email){
        logInFormForEmail.clear();
        logInFormForEmail.sendKeys(email);
    }

    public boolean isSearchNike(){
        if (nikeTitle.getText().equals("Nike") || nikeTitle.getText().equals("Jordan")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isLoggedIn(){
        if (accountTitle.getText().equals("Профиль")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isFavouritesExist(){
        if (noFavourites.getText().equals("Избранное")) {
            return true;
        } else {
            return false;
        }
    }

    public void addPassword(String pass){
        passwordLine.click();
        logInFormForPassword.clear();
        logInFormForPassword.sendKeys(pass);
    }

    public void goToShoesPage(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        discontButton.click();
    }

    public void clickLogInButton1(){logInButton1.click();}

    public void goToFavourites(){favouriteButtonMenu.click();}

    public void addToFavourites(){addToFavouriteButton.click();}

    public void clickLogInButton2(){
        logInButton2.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void clickAccountButton(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        accountButton.click();
    }

}