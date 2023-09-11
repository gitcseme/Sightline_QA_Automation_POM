package legalhold.setup;

import automationLibrary.CustomWebDriverWait;
import automationLibrary.Driver;
import legalhold.legalholdpagefactory.login_logout.LogoutLegalHold;
import legalhold.smoke_suite.sl_slh_integration.login_to_sightline.LoginToSightline;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.asserts.SoftAssert;

import pageFactory.*;
import testScriptsSmoke.Input;

import java.io.IOException;
import java.text.ParseException;



public class BaseRunner {
    protected Driver driver;
    protected WebDriverWait wait;
    protected SoftAssert softAssert;
    protected LoginToSightline loginToSightline;
    protected LogoutLegalHold logoutLegalHold;


    public BaseRunner() throws ParseException, IOException, InterruptedException {
        Input in = new Input();
        in.loadEnvConfig();
        driver = new Driver();
        loginToSightline = new LoginToSightline(driver);
        logoutLegalHold = new LogoutLegalHold(driver);
        wait = new CustomWebDriverWait(driver.getWebDriver(),30);
        softAssert = new SoftAssert();
    }

    @BeforeClass(alwaysRun = true)
    public void login() throws IOException {
        loginToSightline.loginAsSystemAdmin("syslegalhold@gmail.com", "amikhelbona#2023", "Tokyo");
    }

    @AfterClass(alwaysRun = true)
    public void closeBrowser() throws InterruptedException {
        logoutLegalHold.logOutFromLegalHold();
        logoutLegalHold.logOutFromSightline();
        driver.close();
    }

}
