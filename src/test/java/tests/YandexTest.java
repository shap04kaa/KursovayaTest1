package tests;
import io.qameta.allure.*;

import driver.DriverSetup;
import driver.TestListener;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.YMPage;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.valueOf;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(TestListener.class)
@Feature("Tests Yandex Market page")
public class YandexTest extends DriverSetup {

    private YMPage yandexMarketPage;
    private static final Logger logger = LoggerFactory.getLogger(YandexTest.class);

    String SecondLaptopName;
    String SecondLaptopPrice;

    @Step("Page initialization")
    private void init(){
        logger.info("Start test page yandex market");
        yandexMarketPage = new YMPage(driver);
        driver.get(DriverSetup.getProperties("yandexmarket"));
        logger.info("Page get success");
    }

    @Step("Navigating to the catalog")
    private void stepTransitionToCatologist(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        yandexMarketPage.clickCatalog();
        logger.info("Pressed catalog");

        yandexMarketPage.clickCatalogElementGadgets();
        logger.info("Selected element computer");

        yandexMarketPage.clickCatalogElementLaptop();
        logger.info("Clicked Internal hard drives");
    }

    @Step("Retrieving each product")
    private void stepLogProduct(){
        List<Map<String, String>> listProduct = yandexMarketPage.getProduct(5);

        // Проверяем, что список продуктов не пуст
        assertFalse(listProduct.isEmpty());
        logger.info("Get five first product");

        for (int i = 0; i < listProduct.size(); i++) {
            if (i == 1){
                SecondLaptopName = listProduct.get(i).get("name");
                SecondLaptopPrice = listProduct.get(i).get("price");
            }
            logger.info("Get product " + (i + 1));
            logger.info("Name product " + listProduct.get(i).get("name") + " Price " + listProduct.get(i).get("price"));
        }

        assertTrue(listProduct.size() == 5);
        logger.info(listProduct.size() + " product printed");
    }

    @Step("Adding a product to basket")
    private void addToBasket(){
        for (int i1 = 0; i1 < 7; i1++){
            yandexMarketPage.clickAddToBasketButton();
        }
        for (int i2 = 0; i2 < 6; i2++){
            yandexMarketPage.deleteBasketButton();
        }
        logger.info("Product added to Basket");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @Step("Adding a product to favorites")
    private void goToBasket(){
        yandexMarketPage.clickBasketPage();

        String name1 = yandexMarketPage.getNameInBasket().replaceAll(" ", "");
        String name2 = SecondLaptopName.replaceAll(" ", "");


        if (name1.equals(name2)){
            logger.info("Product name matches");
        } else {
            logger.info("Product name not matches");
        }
        assertEquals(name1, name2);

        int price1 = valueOf(SecondLaptopPrice.replaceAll(" ", ""));
        int price2 = valueOf(yandexMarketPage.getPriceInBasket().replaceAll(" ", ""));
        if (price1 == price2){
            logger.info("Product price matches");
        } else {
            logger.info("Product price not matches");
        }
        assertEquals(price1, price2);

    }

    @Step
    public void clickOneMore(){
        yandexMarketPage.clickOneMoreButton();
        int price1 = valueOf(SecondLaptopPrice.replaceAll(" ", "")) * 2;
        int price2 = valueOf(yandexMarketPage.getPriceInBasket().replaceAll(" ", ""));

        if (price1 == price2){
            logger.info("Product new price matches");
        } else {
            logger.info("Product new price not matches");
        }
            assertEquals(price1, price2);
    }

    @Step
    public void deleteFromBasket(){
        yandexMarketPage.deleteFromBasket();
        driver.navigate().refresh();
        if (yandexMarketPage.isBasketEmpty()){
            logger.info("Basket is empty");
        } else {
            logger.info("Basket is not empty");
        }
        assertTrue(yandexMarketPage.isBasketEmpty());
    }


    @Test
    @Link(name = "Yandex Market", url = "https://market.yandex.ru")
    @DisplayName("Checking Yandex Market display")
    @Description("Navigate to the Yandex Market page, click the catalog button, select gadgets, laptops, gaming consoles, add to basket, go to basket, remove the product")
    @Epic("Test for site https://market.yandex.ru ")
    public void test(){
        init();
        stepTransitionToCatologist();
        stepLogProduct();
        addToBasket();
        goToBasket();
        clickOneMore();
        deleteFromBasket();
    }
}
