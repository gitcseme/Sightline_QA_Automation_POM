package legalhold.smoke_suite.sl_slh_integration.login_to_sightline;

import automationLibrary.Driver;
import automationLibrary.Element;
import executionMaintenance.UtilityLog;
import legalhold.BaseModule;
import legalhold.utilities.parse_locators.LocatorReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageFactory.LoginPage;
import testScriptsSmoke.Input;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Callable;

public class LoginToSightline extends BaseModule {
    public WebDriverWait wait;
    Properties prop = new Properties();

    LoginPage login;

    public LoginToSightline(Driver driver) throws IOException {
        super("./src/main/java/legalhold/selectors/sl_slh_integration/login.properties", driver);
        login = new LoginPage(driver);
        //prop.load(file);
    }

    public void loginAsSystemAdmin(String useremail, String userpassword, String tenant) throws IOException {

        //login.loginToSightLine(Input.sa1userName, Input.sa1password);
        login.loginToSightLine(useremail, userpassword);
        bc.impersonateSAtoDA(tenant);
        driver.FindElementById("productMenu").Click();
        driver.FindElementByCssSelector(".sightlineLegalHold a").Click();
        driver.waitForPageToBeReady();
        driver.WaitUntil((new Callable<Boolean>() {
            public Boolean call() {
                return driver.FindElementById("tenantSelector").Visible();
            }
        }), Input.wait30);


    }

    public void loginAsDomainAdmin(String useremail, String userpassword, String tenant) throws IOException {

        //login.loginToSightLine(Input.sa1userName, Input.sa1password);
        login.loginToSightLine(useremail, userpassword);


        domainDashboard.getDomainDrpDwn().Click();
        domainDashboard.availableDomains("Tokyo").Click();
        driver.waitForPageToBeReady();


        driver.FindElementById("productMenu").Click();
        driver.FindElementByCssSelector(".sightlineLegalHold a").Click();
        driver.waitForPageToBeReady();
        driver.WaitUntil((new Callable<Boolean>() {
            public Boolean call() {
                return driver.FindElementById("tenantSelector").Visible();
            }
        }), Input.wait30);
    }

}
