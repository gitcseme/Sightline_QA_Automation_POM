package legalhold.smoke_suite.sl_slh_integration.login_to_sightline;

import automationLibrary.Driver;
import legalhold.BaseModule;
import legalhold.utilities.parse_locators.LocatorReader;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageFactory.LoginPage;
import testScriptsSmoke.Input;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LoginToSightline extends BaseModule {
    public Driver driver;
    public WebDriverWait wait;
    Properties prop = new Properties();

    LoginPage login;

    public LoginToSightline(Driver driver) throws IOException {
             super("./src/main/java/legalhold/selectors/sl_slh_integration/login.properties",driver);
             login=new LoginPage(driver);
        //prop.load(file);
    }
    public void login(String useremail, String userpassword) throws IOException {

        //login.loginToSightLine(Input.sa1userName, Input.sa1password);
        login.loginToSightLine(useremail,userpassword);
        bc.impersonateSAtoDA("Tokyo");

//        prop.load(file);
        /*WebElement username =
//        username.sendKeys(prop.getProperty("username"));
        username.sendKeys(useremail);
        WebElement pass = driver.findElement(parser.getbjectLocator("pass"));
//        pass.sendKeys(prop.getProperty("password"));
        pass.sendKeys(userpassword);
        WebElement btnLogin = driver.findElement(parser.getbjectLocator("btnLogin"));
        wait.until(ExpectedConditions.elementToBeClickable(btnLogin));
        btnLogin.click();
        String pageTitle = driver.findElement(parser.getbjectLocator("title")).getText();
        Assert.assertEquals(pageTitle, "Manage Users");*/
    }
}
