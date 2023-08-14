package legalhold.smoke_suite.sl_slh_integration.login_to_sightline;

import legalhold.utilities.parse_locators.FileParser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LoginToSightline {
    public WebDriver driver;
    public WebDriverWait wait;
    Properties prop = new Properties();
    FileInputStream file = new FileInputStream("./src/test/resources/logininfo/logininfo.properties");
    private FileParser parser;

    public LoginToSightline(WebDriver driver) throws IOException {
        parser = new FileParser("./src/main/java/legalhold/selectors/sl_slh_integration/login.properties");
        prop.load(file);
    }
    public void login(String useremail, String userpassword) throws IOException {
//        prop.load(file);
        WebElement username = driver.findElement(parser.getbjectLocator("username"));
//        username.sendKeys(prop.getProperty("username"));
        username.sendKeys(useremail);
        WebElement pass = driver.findElement(parser.getbjectLocator("pass"));
//        pass.sendKeys(prop.getProperty("password"));
        pass.sendKeys(userpassword);
        WebElement btnLogin = driver.findElement(parser.getbjectLocator("btnLogin"));
        wait.until(ExpectedConditions.elementToBeClickable(btnLogin));
        btnLogin.click();
        String pageTitle = driver.findElement(parser.getbjectLocator("title")).getText();
        Assert.assertEquals(pageTitle, "Manage Users");
    }
}
