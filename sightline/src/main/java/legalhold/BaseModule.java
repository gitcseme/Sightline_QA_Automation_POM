package legalhold;

import automationLibrary.CustomWebDriverWait;
import automationLibrary.Driver;
import com.github.javafaker.Faker;
import executionMaintenance.UtilityLog;
import legalhold.utilities.parse_locators.LocatorReader;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
import pageFactory.BaseClass;
import pageFactory.DomainDashboard;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseModule {


    protected BaseClass bc;
    protected LocatorReader locatorReader;
    protected Driver driver;
    protected DomainDashboard domainDashboard;
    protected Faker faker;
    protected SoftAssert softAssert;
    public WebDriverWait wait;


    public BaseModule(String selectorFilename, Driver driver) throws IOException {
        this.driver = driver;
        locatorReader=new LocatorReader(selectorFilename);
        bc=new BaseClass(driver);
        domainDashboard=new DomainDashboard(driver);
        faker = new Faker();
        softAssert = new SoftAssert();
        wait = new CustomWebDriverWait(driver.getWebDriver(),30);
    }
}
