package legalhold.testrunners.smoke_suite_testrunners.sl_slh_integration;

import akka.stream.impl.Always;
import automationLibrary.Element;
import legalhold.BaseRunner;

import legalhold.legalholdpagefactory.cases.CaseCommunicationFactories;
import legalhold.legalholdpagefactory.cases.CaseFactories;
import legalhold.smoke_suite.Employee.CreateEmployee;
import legalhold.smoke_suite.Employee.DeleteEmployee;
import legalhold.smoke_suite.Employee.EditEmployee;
import legalhold.smoke_suite.Employee.UploadEmployee;
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

//    @Test
//    public void systemAdminLogin() throws IOException {
//        LoginToSightline loginToSightline = new LoginToSightline(driver);
//        loginToSightline.loginAsSystemAdmin("syslegalhold@gmail.com", "amikhelbona#2023", "Tokyo");
////        CreateCase createCase = new CreateCase(driver);
////        String caseName = createCase.createRandomCase();
////        System.out.println("Created case name is: " + caseName);
//    }

    @Test(priority = 1)
    public void domainAdminLogin() throws IOException, ParseException, InterruptedException {
        LoginToSightline loginToSightline = new LoginToSightline(driver);


        loginToSightline.loginAsDomainAdmin("userlegalhold@gmail.com","amikhelbona#2023","Tokyo");

    }

    @Test(priority = 2)
    public void CreateRandomEmployee() throws IOException, InterruptedException {
        Module_Navigation navigateToEmployeeTab = new Module_Navigation(driver);

        CreateEmployee createEmployee = new CreateEmployee(driver);
        EditEmployee editEmployee = new EditEmployee(driver);
        DeleteEmployee deleteEmployee = new DeleteEmployee(driver);
        UploadEmployee uploadEmployee = new UploadEmployee(driver);

        navigateToEmployeeTab.navigateToEmployeeTAB();

         String id = createEmployee.CreateEmployeeManually();
        createEmployee.verifyEmployeeId(id);
        System.out.println(id);
        String editedId = editEmployee.EditCreatedEmployee(id);
        createEmployee.verifyEmployeeId(editedId);
        deleteEmployee.deleteCreatedEmployee();
        driver.waitForPageToBeReady();
        uploadEmployee.uploadValidEmployee();
        navigateToEmployeeTab.navigateToEmployeeTAB();
        driver.waitForPageToBeReady();
        uploadEmployee.uploadInvalidEmployee();
    }

//    @Test(priority = 3)
//    public void custodian() throws IOException, InterruptedException {
//
//
//        AddCaseCustodian CreateCustodian = new AddCaseCustodian(driver);
//        CaseCommunicationFactories addRecipients = new CaseCommunicationFactories(driver);
//
//
//        CreateCustodian.navigationToCustodianTab();
//        CreateCustodian.upLoadCustodians();
//        CaseFactories caseFactories = new CaseFactories(driver);
//        caseFactories.NavigateToCommunicationsTab();
//        addRecipients.goToCreateCustodianCommunicationPage();
//        addRecipients.addMailToRecipients();
////        addRecipients.addMailToRecipients();
//
//
//
//    }

//    @Test
//    public void test() throws IOException {
//        CreateRandomEmployee createrandomemployee = new CreateRandomEmployee(driver);
//        createrandomemployee.test();
//    }
}
