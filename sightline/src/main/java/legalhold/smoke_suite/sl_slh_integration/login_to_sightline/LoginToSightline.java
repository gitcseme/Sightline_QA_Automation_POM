package legalhold.smoke_suite.sl_slh_integration.login_to_sightline;

import automationLibrary.Driver;
import legalhold.BaseModule;
import org.openqa.selenium.support.ui.WebDriverWait;
import testScriptsSmoke.Input;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Callable;

public class LoginToSightline extends BaseModule {
    public WebDriverWait wait;
    Properties prop = new Properties();




    public LoginToSightline(Driver driver) throws IOException {
        super( driver);
    }

    public void loginAsSystemAdmin(String useremail, String userpassword, String tenant) throws IOException {

        LoadMoudleActionSteps("./src/main/java/legalhold/ModuleStep/LoginSteps.json");

        elementValueMapping.clear();
        elementValueMapping.put("userName",useremail);
        elementValueMapping.put("password",userpassword);
        elementValueMapping.put("tenant",tenant);
        elementValueMapping.put("product","productMenu");
        super.Run();

    }

    @Override
    public String GetValueFromElementName(String elementName) {
        return elementValueMapping.get(elementName);
    }

    public void loginAsDomainAdmin(String useremail, String userpassword, String tenant) throws IOException {

        //login.loginToSightLine(Input.sa1userName, Input.sa1password);
        login.loginToSightLine(useremail, userpassword);



        String text = driver.FindElementById("project-selector").getText();
        if(!text.equalsIgnoreCase(tenant))
        {
            System.out.println("not selected");
            domainDashboard.getDomainDrpDwn().Click();
            domainDashboard.availableDomains(tenant).Click();

        }

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
