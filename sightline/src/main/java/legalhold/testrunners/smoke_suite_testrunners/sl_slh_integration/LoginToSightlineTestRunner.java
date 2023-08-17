package legalhold.testrunners.smoke_suite_testrunners.sl_slh_integration;

import legalhold.BaseRunner;
import legalhold.environment.Setup;
import legalhold.smoke_suite.sl_slh_integration.login_to_sightline.LoginToSightline;
import org.testng.annotations.Test;
import testScriptsSmoke.Input;

import java.io.IOException;
import java.text.ParseException;

public class LoginToSightlineTestRunner extends BaseRunner {
    public LoginToSightlineTestRunner() throws ParseException, IOException, InterruptedException {


    }

    @Test(priority = 1)
    public void loginRun() throws IOException, ParseException, InterruptedException {
        //driver.get("https://sightlineqa.consilio.com");

        LoginToSightline loginToSightline = new LoginToSightline(driver);
        loginToSightline.login("syslegalhold@gmail.com","amikhelbona#2023","Infinity Domain Expansion");
    }
}
