package legalhold.testrunners.smoke_suite_testrunners.sl_slh_integration;

import akka.stream.impl.Always;
import legalhold.BaseRunner;

import legalhold.smoke_suite.Employee.CreateEmployee;
import legalhold.smoke_suite.cases.create_case.CreateCase;

import legalhold.legalholdpagefactory.Module_Navigation;

import legalhold.smoke_suite.manageCase.AddCaseCustodian;
import legalhold.smoke_suite.sl_slh_integration.login_to_sightline.LoginToSightline;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;

public class LoginToSightlineTestRunner extends BaseRunner {

    public LoginToSightlineTestRunner() throws ParseException, IOException, InterruptedException {

    }

    @Test
    public void systemAdminLogin() throws IOException {
        LoginToSightline loginToSightline = new LoginToSightline(driver);
        loginToSightline.loginAsSystemAdmin("syslegalhold@gmail.com", "amikhelbona#2023", "Tokyo");
//        CreateCase createCase = new CreateCase(driver);
//        String caseName = createCase.createRandomCase();
//        System.out.println("Created case name is: " + caseName);
    }

    @Test
    public void domainAdminLogin() throws IOException, ParseException, InterruptedException {
        LoginToSightline loginToSightline = new LoginToSightline(driver);
        loginToSightline.loginAsDomainAdmin("userlegalhold@gmail.com", "amikhelbona#2023", "Infinity Domain Expansion");
    }



//    @Test
//    public void test() throws IOException {
//        CreateRandomEmployee createrandomemployee = new CreateRandomEmployee(driver);
//        createrandomemployee.test();
//    }
}
