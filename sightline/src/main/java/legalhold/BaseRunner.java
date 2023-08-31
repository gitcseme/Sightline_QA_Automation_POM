package legalhold;

import automationLibrary.CustomWebDriverWait;
import automationLibrary.Driver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
import testScriptsSmoke.Input;

import java.io.IOException;
import java.text.ParseException;


public class BaseRunner {
    protected Driver driver;
    protected WebDriverWait wait;
    protected SoftAssert softAssert;


    public BaseRunner() throws ParseException, IOException, InterruptedException {
        Input in = new Input();
        in.loadEnvConfig();
        driver = new Driver();
        wait = new CustomWebDriverWait(driver.getWebDriver(),30);
        softAssert = new SoftAssert();
    }

}
