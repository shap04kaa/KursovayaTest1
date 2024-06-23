package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YMPage {
    private final WebDriver driver;
    public YMPage(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//div[@data-baobab-name='catalog']/button")
    private WebElement buttonCatalog;

    @FindBy(xpath = "//a[@class='_3yHCR']//span[text()='Электроника']")
    private WebElement catalogElementGadgets;

    @FindBy(xpath = "//a[@href='/catalog--noutbuki/26895412/list?hid=91013' and text()='Ноутбуки']")
    private WebElement catalogElementNotepad;

    @FindBy(xpath = "//div[@data-apiary-widget-name='@light/Organic']") // каждый Xbox с начала
    private List<WebElement> catalog;

    @FindBy(xpath = "//button[@aria-label='В корзину' and @data-auto='cartButton']")
    private WebElement addToBasketButton;

    @FindBy(xpath = "//button[@class='_1Kigx' and @aria-label='Уменьшить']")
    private WebElement deleteBasketButton;

    @FindBy(xpath = "//div[@class='cia-cs' and @data-zone-name='show' and @data-cs-name='navigate']//div[@data-baobab-name='title']")
    private WebElement nameInBasket;

    @FindBy(xpath = "//span[@data-auto='total-price']")
    private WebElement priceInBasket;

    @FindBy(xpath = "//div[@data-baobab-name='cart'][.//div[@role='alert' and contains(text(), 'Корзина')]]")
    private WebElement basketMenu;

    @FindBy(xpath = "//button[@data-auto='remove-button' and @aria-label='Удалить'")
    private WebElement delFromBasket;

    @FindBy(xpath = "//button[@aria-label='Увеличить' and @data-auto='offerAmountPlus'")
    private WebElement addOneMoreButton;

    public void clickCatalog(){
        buttonCatalog.click();
    }

    public String getPriceInBasket(){
        String text = priceInBasket.getText();
        return text;
    }

    public String getNameInBasket(){
        String text = nameInBasket.getText();
        return text;
    }

    public void clickCatalogElementGadgets(){
        Actions action = new Actions(driver);
        action.moveToElement(catalogElementGadgets).build().perform();
    }

    public void clickCatalogElementLaptop(){
        catalogElementNotepad.click();
    }

    public void deleteBasketButton(){
        deleteBasketButton.click();
    }

    public void clickBasketPage(){
        basketMenu.click();
    }

    public void clickOneMoreButton(){
        addOneMoreButton.click();
    }

    public void deleteFromBasket(){
        delFromBasket.click();
    }

    public boolean isBasketEmpty() {
        // Проверка на наличие текста, указывающего на пустое избранное
        List<WebElement> emptyMessage = driver.findElements(By.xpath("//span[text()='Войдите в аккаунт']"));
        return !emptyMessage.isEmpty(); // Если сообщение найдено, избранное пусто
    }

    public void clickAddToBasketButton(){
        addToBasketButton.click();
    }

    public List<Map<String, String>> getProduct(Integer count){
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for (int i = 0; i < count; i++) {
            WebElement product = catalog.get(i);
            WebElement name = product.findElement(By.xpath(".//h3[@data-auto='snippet-title']"));
            WebElement price = product.findElement(By.xpath(".//span[@data-auto='snippet-price-current']/span[1]"));

            HashMap<String, String> item = new HashMap<>();
            item.put("name", name.getText());
            item.put("price", price.getText());
            list.add(item);
        }
        return list;
    }
}