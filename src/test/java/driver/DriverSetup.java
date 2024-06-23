package driver;

import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.time.Duration;
import java.util.Properties;

public abstract class DriverSetup implements WebDriverListener {
    private static Logger logger = LoggerFactory.getLogger(DriverSetup.class);
    public static WebDriver driver;
    protected static Properties PROPERTIES;
    static {
        try(FileInputStream fileInputStream = new FileInputStream("src/test/resources/conf.properties")){
            PROPERTIES = new Properties();
            PROPERTIES.load(fileInputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static String getProperties(String key) {
        return PROPERTIES.getProperty(key);
    }

    @BeforeEach
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--user-data-dir=C:\\Users\\alexa\\AppData\\Local\\Google\\Chrome\\User Data\\Default");
        options.addArguments("--profile-directory=Profile 1");
        driver = new EventFiringDecorator(new CustomEvent()).decorate(new ChromeDriver(options));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        logger.info("Init driver success");
    }

}
