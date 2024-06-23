package tests;

import driver.DriverSetup;
import driver.TestListener;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.LamodaPage;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TestListener.class)
@Feature("Tests for site Lamoda")
public class LamodaTest extends DriverSetup {
    private LamodaPage lamodaPage;
    private static final Logger logger = LoggerFactory.getLogger(LamodaTest.class);

    @Step("Start test page Lamoda")
    private void init(){
        logger.info("Start test page Lamoda");
        lamodaPage = new LamodaPage(driver);
        driver.get(DriverSetup.getProperties("lamoda"));
        logger.info("Page get success");
    }

    //проверка поиска продукта
    @Step("Search for a product through the search field")
    private void searchWithField(){
        logger.info("Product search");
        lamodaPage.searchItem("Nike");
        assertTrue(lamodaPage.isSearchNike());
        logger.info("Product found");
    }

    @Step("Log In")
    private void logIn(){
        lamodaPage.clickLogInButton1();
        logger.info("Log In window opener");
        lamodaPage.addEmail("alexander.shapovalov17@gmail.com");
        lamodaPage.addPassword("SaShA22032004!!");
        lamodaPage.clickLogInButton2();
        lamodaPage.clickAccountButton();
        assertTrue(lamodaPage.isLoggedIn());
    }

    @Step("Adding a product to favorites")
    private void addFavourite(){
        lamodaPage.goToShoesPage();
        logger.info("Going to shoes page");
        lamodaPage.addToFavourites();
        logger.info("Product added to favorites");
        lamodaPage.goToFavourites();
        logger.info("Open all favourites");
        assertTrue(lamodaPage.isFavouritesExist());
    }

    @Test
    @Link(name = "Lamoda", url = "https://lamoda.ru/")
    @DisplayName("Lamoda search verification")
    @Description("Enter \"Nike\" in the search field, click the search button")
    @Epic("Test for site https://lamoda.ru/ ")
    public void test1(){
        init();
        searchWithField();
    }

    @Test
    @Link(name = "Lamoda", url = "https://lamoda.ru/")
    @DisplayName("Check logIn")
    @Description("Click on the login button, enter your email and password, log in to your account")
    @Epic("Test for site https://lamoda.ru/ ")
    public void test2(){
        init();
        logIn();
    }

    @Test
    @Link(name = "Lamoda", url = "https://lamoda.ru/")
    @DisplayName("Check product sorting")
    @Description("Add an item to favorites, go to the favorites tab")
    @Epic("Test for site https://lamoda.ru/ ")
    public void test3(){
        init();
        addFavourite();
    }
}