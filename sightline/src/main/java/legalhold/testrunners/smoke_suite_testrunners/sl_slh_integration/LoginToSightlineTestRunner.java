package legalhold.testrunners.smoke_suite_testrunners.sl_slh_integration;

import legalhold.BaseRunner;
import legalhold.environment.Setup;

import legalhold.smoke_suite.cases.create_case.CreateCase;

import legalhold.legalholdpagefactory.CreateRandomEmployee;

import legalhold.smoke_suite.sl_slh_integration.login_to_sightline.LoginToSightline;
import org.testng.annotations.Test;
import testScriptsSmoke.Input;

import java.io.IOException;
import java.text.ParseException;

public class LoginToSightlineTestRunner extends BaseRunner {

    public LoginToSightlineTestRunner() throws ParseException, IOException, InterruptedException {

    }

    @Test(priority = 1)
    public void systemAdminLogin() throws IOException, ParseException, InterruptedException {
        LoginToSightline loginToSightline = new LoginToSightline(driver);
        loginToSightline.loginAsSystemAdmin("syslegalhold@gmail.com","amikhelbona#2023","Infinity Domain Expansion");
        CreateCase createCase = new CreateCase(driver);
        createCase.createRandomCases();
    }

    @Test(priority = 2)
    public void domainAdminLogin() throws IOException, ParseException, InterruptedException {
        LoginToSightline loginToSightline = new LoginToSightline(driver);
        CreateRandomEmployee createrandomemployee = new CreateRandomEmployee(driver);
        loginToSightline.loginAsDomainAdmin("userlegalhold@gmail.com","amikhelbona#2023","Tokyo");
        createrandomemployee.navigateToEmployeeTAB();
        createrandomemployee.CreateEmployeeManually();
//        createrandomemployee.verifyEmployeeCreation();
//        createrandomemployee.test();
    }


//    @Test
//    public void test() throws IOException {
//        CreateRandomEmployee createrandomemployee = new CreateRandomEmployee(driver);
//        createrandomemployee.test();
//    }
}
