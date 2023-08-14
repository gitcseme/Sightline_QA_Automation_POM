package legalhold.testrunners.smoke_suite_testrunners.sl_slh_integration;

import legalhold.environment.Setup;
import legalhold.smoke_suite.sl_slh_integration.login_to_sightline.LoginToSightline;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginToSightlineTestRunner extends Setup {
    @Test(priority = 1)
    public void loginRun() throws IOException {
        driver.get("https://sightlineqa.consilio.com");
        LoginToSightline loginToSightline = new LoginToSightline(driver);
        loginToSightline.login("syslegalhold.gmail.com","amikhelbona#2023");
    }
}
