package legalhold.sprintwork.sprint_37.testrunner;

import com.github.javafaker.Faker;
import legalhold.legalholdpagefactory.Module_Navigation;
import legalhold.legalholdpagefactory.cases.CaseCommunicationFactories;
import legalhold.legalholdpagefactory.cases.CaseFactories;
import legalhold.legalholdpagefactory.template.TemplateFactories;
import legalhold.setup.BaseRunner;
import legalhold.smoke_suite.cases.create_case.CreateCase;
import legalhold.smoke_suite.manageCase.AddCaseCustodian;
import legalhold.smoke_suite.sl_slh_integration.login_to_sightline.LoginToSightline;
import legalhold.sprintwork.sprint_36.testcases.Sprint36CasePage;
import legalhold.sprintwork.sprint_37.testcase.Sprint37;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;

public class Sprint37TestRunner extends BaseRunner {
    CaseFactories caseFactories;
    CaseCommunicationFactories caseCommunicationFactories;
    Sprint37 sprint37;
    LoginToSightline loginToSightline;
    CreateCase createCase;
    Faker faker;
    TemplateFactories templateFactories;
    Module_Navigation navigation;
    AddCaseCustodian addCaseCustodian;
    public String createdCase;
    public String createdCaseTemplate;
    public String createdCommunicationSeries;

    public Sprint37TestRunner() throws ParseException, IOException, InterruptedException {
        caseFactories = new CaseFactories(driver);
        caseCommunicationFactories = new CaseCommunicationFactories(driver);
        sprint37 = new Sprint37(driver);
        loginToSightline = new LoginToSightline(driver);
        createCase = new CreateCase(driver);
        templateFactories = new TemplateFactories(driver);
        navigation = new Module_Navigation(driver);
        addCaseCustodian = new AddCaseCustodian(driver);
        faker = new Faker();
    }

    @Test(priority = 1, description = "Creating a case with compliance reminder schedule as Every 2 days and Maximum 2.")
    public void caseSaveWithCustomIntervalAndMaxNumber() throws IOException, InterruptedException {


          createCase.createRandomCases();
//        navigation.navigateToCaseTAB();
//        caseFactories.goToEditCase(createdCase);
        caseFactories.setComplianceReminderAsCustomIntervalWithMaxNumber(2,2);
        caseFactories.saveCase();
        driver.waitForPageToBeReady();
    }
    @Test(priority = 2, description = "Creating a case with compliance reminder schedule as Every 3 days and Unlimited.")
    public void caseSaveWithCustomIntervalAndUnlimited() throws IOException, InterruptedException {

        navigation.navigateToCaseTAB();
        createdCase = createCase.createRandomCases();
//        navigation.navigateToCaseTAB();
//        caseFactories.goToEditCase(createdCase);
        driver.waitForPageToBeReady();
        caseFactories.setComplianceReminderAsCustomIntervalWithUnlimited(3);
        caseFactories.saveCase();
    }
    @Test(priority = 3, description = "Creating a case template with Compliance Reminder schedule as Every 7 days and Maximum 7 " +
            "and applying that template to the created case from previous test case.")
    public void templateSaveWithCustomIntervalAndMaxNumber() throws IOException, InterruptedException {
        navigation.navigateToTemplatesTAB();
        createdCaseTemplate = templateFactories.createRandomCaseTemplate();
        caseFactories.setComplianceReminderAsCustomIntervalWithMaxNumber(7,7);
        templateFactories.saveCaseTemplate();
        navigation.navigateToCaseTAB();
        caseFactories.goToEditCase(createdCase);
        templateFactories.applyCaseTemplate(createdCaseTemplate);
        caseFactories.saveCase();
        driver.waitForPageToBeReady();
    }
    @Test(priority = 4,description = "Adding multiple custodian to the case created case from previous test case.")
    public void addMultipleCustodianToACase() throws InterruptedException {
        caseFactories.NavigateToCustodiansTab();
        addCaseCustodian.upLoadCustodians();
        driver.waitForPageToBeReady();
    }

    @Test(priority = 5, description = "Creating a Custodian type communication series in that case after the template from previous " +
            "test case gets applied.")
    public void createCommunicationWithDefaultCaseComplianceReminderMaxNumberSchedule() throws InterruptedException, IOException {
        caseFactories.NavigateToCommunicationsTab();
        caseCommunicationFactories.goToCreateCustodianCommunicationPage();
        String caseNameCommunicationPage = driver.FindElementById("CaseName").getText();
        softAssert.assertEquals(caseNameCommunicationPage, createdCase);
        createdCommunicationSeries = caseCommunicationFactories.enterSeriesName();
        caseCommunicationFactories.addMailToRecipients();
        caseCommunicationFactories.enterCommunicationNameAndDescription();
        caseCommunicationFactories.enterAcknowledgmentEmailSubject("FA Automated Acknowledgment email for [CASE NAME]");
        caseCommunicationFactories.typeEmailBody("Email Type: Acknowledgment\n" +
                "[ACKNOWLEDGMENT LINK]\n" +
                "[CUSTODIAN PORTAL LINK]");
        caseCommunicationFactories.saveCommunicationSeries();
    }


    @Test(priority = 6, description = "Starting the previously created series with Compliance Reminder One Time")
    public void createCommunicationWithNewCaseComplianceReminderMaxNumberSchedule() throws InterruptedException {
        caseCommunicationFactories.goToEditCustodianCommunicationPage(createdCommunicationSeries);
        caseCommunicationFactories.enableComplianceReminder();
        caseCommunicationFactories.setComplianceReminderAsOneTime();
        caseCommunicationFactories.openComplianceReminderSubTab();
        caseCommunicationFactories.enterCommunicationNameAndDescription();
        caseCommunicationFactories.enterComplianceReminderEmailSubject("FA Automated Compliance Reminder email for [CASE NAME]");
        caseCommunicationFactories.typeEmailBody("Email Type: Compliance Reminder\n" +
                "[ACKNOWLEDGMENT LINK]\n" +
                "[CUSTODIAN PORTAL LINK]");

        caseCommunicationFactories.startCommunicationSeries();
        caseCommunicationFactories.verifyPostSendForCustodianSeries(createdCommunicationSeries);
    }
}
