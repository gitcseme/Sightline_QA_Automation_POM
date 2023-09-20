package legalhold.testrunners.smoke_suite_testrunners.sl_slh_integration;

import legalhold.legalholdpagefactory.Module_Navigation;
import legalhold.legalholdpagefactory.cases.CaseFactories;
import legalhold.legalholdpagefactory.cases.CustodianFactories;
import legalhold.legalholdpagefactory.cases.SurveyFactories;
import legalhold.setup.BaseRunner;

import legalhold.legalholdpagefactory.cases.CaseCommunicationFactories;

import legalhold.smoke_suite.Employee.CreateEmployee;
import legalhold.smoke_suite.Employee.DeleteEmployee;
import legalhold.smoke_suite.Employee.EditEmployee;
import legalhold.smoke_suite.Employee.UploadEmployee;
import legalhold.smoke_suite.cases.create_case.CreateCase;

import legalhold.legalholdpagefactory.Module_Navigation;


import legalhold.smoke_suite.manageCase.AddCaseCustodian;
import legalhold.smoke_suite.sl_slh_integration.login_to_sightline.LoginToSightline;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;

public class LoginToSightlineTestRunner extends BaseRunner {

    CaseCommunicationFactories caseCommunicationFactories;
    CaseFactories caseFactories;
    Module_Navigation navigateToEmployeeTab;
    AddCaseCustodian CreateCustodian;
    CustodianFactories custodianFactories;
    SurveyFactories surveyFactories;

    public LoginToSightlineTestRunner() throws ParseException, IOException, InterruptedException {

        caseCommunicationFactories = new CaseCommunicationFactories(driver);
        caseFactories = new CaseFactories(driver);
        navigateToEmployeeTab = new Module_Navigation(driver);
        CreateCustodian = new AddCaseCustodian(driver);
        custodianFactories = new CustodianFactories(driver);
        surveyFactories = new SurveyFactories(driver);
    }

//    @Test
//    public void systemAdminLogin() throws IOException {
//        LoginToSightline loginToSightline = new LoginToSightline(driver);
//        loginToSightline.loginAsSystemAdmin("syslegalhold@gmail.com", "amikhelbona#2023", "Tokyo");
////        CreateCase createCase = new CreateCase(driver);
////        String caseName = createCase.createRandomCase();
////        System.out.println("Created case name is: " + caseName);
//    }

//    @Test(priority = 1)
//    public void domainAdminLogin() throws IOException, ParseException, InterruptedException {
//        LoginToSightline loginToSightline = new LoginToSightline(driver);
//
//
//        loginToSightline.loginAsDomainAdmin("userlegalhold@gmail.com","amikhelbona#2023","Tokyo");
//
//    }

//    @Test(priority = 2)
//    public void CreateRandomEmployee() throws IOException, InterruptedException {
//
//
//        CreateEmployee createEmployee = new CreateEmployee(driver);
//        EditEmployee editEmployee = new EditEmployee(driver);
//        DeleteEmployee deleteEmployee = new DeleteEmployee(driver);
//        UploadEmployee uploadEmployee = new UploadEmployee(driver);
//
//        navigateToEmployeeTab.navigateToEmployeeTAB();
//
//         String id = createEmployee.CreateEmployeeManually();
//        createEmployee.verifyEmployeeId(id);
//        System.out.println(id);
//        String editedId = editEmployee.EditCreatedEmployee(id);
//        createEmployee.verifyEmployeeId(editedId);
//        deleteEmployee.deleteCreatedEmployee();
//        driver.waitForPageToBeReady();
//        uploadEmployee.uploadValidEmployee();
//        navigateToEmployeeTab.navigateToEmployeeTAB();
//        driver.waitForPageToBeReady();
//        uploadEmployee.uploadInvalidEmployee();
//    }
//
    @Test(priority = 3)
    public void custodian() throws IOException, InterruptedException {




        Module_Navigation caseTabNavigation = new Module_Navigation(driver);

        caseTabNavigation.navigateToCaseTAB();


        CaseFactories SearchCaseandGotoEdit = new CaseFactories(driver);
        SearchCaseandGotoEdit.goToEditCase("Automation_Test");

        CaseFactories navigateToCustodiansTab = new CaseFactories(driver);
        navigateToCustodiansTab.NavigateToCustodiansTab();
        CreateCustodian.upLoadCustodians();





    }

    @Test(priority = 4)
    public void makeCustodianSilentAndNonSilent() throws IOException, InterruptedException {


        custodianFactories.navigateToManageCustodiansPage();

        custodianFactories.addCustodianToCase("SLH-Silent");


        driver.waitForPageToBeReady();
        custodianFactories.searchCustodianById("SLH-Silent");

//        AddCaseCustodian CreateCustodian = new AddCaseCustodian(driver);
        CreateCustodian.makeCustodianSilent();

        custodianFactories.searchCustodianById("SLH-Silent");
        custodianFactories.verifyCustodianStatus("Silent");

//        custodianFactories.searchCustodianById("SLH-Silent");
        CreateCustodian.makeCustodianNonSilent();
        custodianFactories.verifyCustodianType("");


    }

    @Test(priority = 5)
    public void SendAcknowledgement() throws IOException, InterruptedException {

        CaseFactories caseFactories = new CaseFactories(driver);
        caseFactories.NavigateToCommunicationsTab();
        caseCommunicationFactories.goToCreateCustodianCommunicationPage();

        String createdCommunicationSeries = caseCommunicationFactories.enterSeriesName();
        caseCommunicationFactories.addMailToRecipients("si");
        caseCommunicationFactories.enterCommunicationNameAndDescription();
        caseCommunicationFactories.enterAcknowledgmentEmailSubject("FA Automated Acknowledgment email for [CASE NAME]");
        caseCommunicationFactories.typeEmailBody("Email Type: Acknowledgment\n" +
                "[ACKNOWLEDGMENT LINK]\n" +
                "[CUSTODIAN PORTAL LINK]");
        Thread.sleep(2000);
        caseCommunicationFactories.startCommunicationSeries();

//        caseCommunicationFactories.goToEditCustodianCommunicationPage("Ares Eunostus");
//        caseCommunicationFactories.addMailToRecipients("SLH-Silent");
//        caseCommunicationFactories.saveCommunicationSeries();
//        caseCommunicationFactories.verifyPostSendForCustodianSeries("Ares Eunostus");
//        caseCommunicationFactories.backToCommunicationTab();
//        driver.waitForPageToBeReady();
//        CaseFactories navigateToCustodiansTab = new CaseFactories(driver);
//        navigateToCustodiansTab.NavigateToCustodiansTab();
//
    }

    @Test(priority = 6)
    public void manualAcknowledgementWithoutReason() throws InterruptedException {




        caseFactories.NavigateToCustodiansTab();

        driver.waitForPageToBeReady();
//        custodianFactories.searchCustodianById("SLH-Silent");
        custodianFactories.searchCustodianById("si");
        CreateCustodian.manualAcknowledgementWithoutReason();
        custodianFactories.searchCustodianById("silent");
        custodianFactories.verifyAcknowledgementCompleteDate("completeDate");
    }
    @Test(priority = 7)
    public void manualAcknowledgementWithReason() throws InterruptedException {

        custodianFactories.searchCustodianById("13");
        CreateCustodian.manualAcknowledgementWithReason("Manual Ack Test");
//        custodianFactories.verifyAcknowledgementCompleteDate("completeDate");
        custodianFactories.verifyManualAcknowledgementReason("Manual Ack Test");
    }
@Test(priority = 8)
public void releaseCustodian() throws InterruptedException {

    custodianFactories.searchCustodianById("13");
    CreateCustodian.releaseCustodianManually();
    custodianFactories.verifyReleaseDate("2023/09/19 02:37 PM");
    custodianFactories.verifyCustodianStatus("Released");

}
    @Test(priority = 9)
    public void removeCustodian() throws InterruptedException {

    custodianFactories.searchCustodianById("silent");
    CreateCustodian.removeCustodian();
    custodianFactories.noDataAvailableCustodianTable();
    }

    @Test(priority = 10)
    public void createSurvey(){
        caseFactories.NavigateToSurveysTab();
        surveyFactories.gotoAddNewSurveyPage();
        surveyFactories.verifyPageHeader("Create Survey");
    }
//    @Test
//    public void test() throws IOException {
//        CreateRandomEmployee createrandomemployee = new CreateRandomEmployee(driver);
//        createrandomemployee.test();
//    }
}
