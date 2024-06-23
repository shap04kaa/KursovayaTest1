package driver;
import io.qameta.allure.Allure;
import org.codehaus.plexus.util.FileUtils;
import tests.LambdaTest;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.logging.LogType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class TestListener implements TestWatcher {
    private static Logger logger = LoggerFactory.getLogger(LambdaTest.class);

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        Allure.getLifecycle().addAttachment("Скриншот на месте падения теста", "image/png", "png",
                ((TakesScreenshot) DriverSetup.driver).getScreenshotAs(OutputType.BYTES));
        Allure.addAttachment("Логи после падения теста: ",String.valueOf(DriverSetup.driver.manage().logs().get(LogType.BROWSER).getAll()));
        DriverSetup.driver.quit();
        logger.error("Test failed");
        try {
            File srcFile = ((TakesScreenshot) DriverSetup.driver).getScreenshotAs(OutputType.FILE);
            DateTimeFormatter format = DateTimeFormatter.ofPattern("uuuu-MMM-dd-HH-mm-ss");
            String pathName = "testFailed-(" + context.getDisplayName().replaceAll(" ", "-").replaceAll("[\\/\\?]", "_") + ")-" + LocalDateTime.now().format(format) + ".png";
            FileUtils.copyFile(srcFile, new File(pathName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        DriverSetup.driver.quit();
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        Allure.getLifecycle().addAttachment("Скриншот после успешного прохождения теста", "image/png", "png",
                ((TakesScreenshot) DriverSetup.driver).getScreenshotAs(OutputType.BYTES));
        Allure.addAttachment("Логи после успешного прохождения теста: ",String.valueOf(DriverSetup.driver.manage().logs().get(LogType.BROWSER).getAll()));
        DriverSetup.driver.quit();
        logger.info("Test success");
        DriverSetup.driver.quit();
    }
}