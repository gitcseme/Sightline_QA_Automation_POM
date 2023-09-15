package legalhold.sprintwork.sprint_36.testrunners;


import com.github.javafaker.Faker;
import legalhold.setup.BaseRunner;
import legalhold.legalholdpagefactory.Module_Navigation;
import legalhold.legalholdpagefactory.cases.CaseCommunicationFactories;
import legalhold.legalholdpagefactory.cases.CaseFactories;
import legalhold.legalholdpagefactory.template.TemplateFactories;
import legalhold.smoke_suite.cases.create_case.CreateCase;
import legalhold.smoke_suite.manageCase.AddCaseCustodian;
import legalhold.smoke_suite.sl_slh_integration.login_to_sightline.LoginToSightline;
import legalhold.sprintwork.sprint_36.testcases.Sprint36CasePage;

import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;

public class Sprint36CasePageTestRunner extends BaseRunner {

    CaseFactories caseFactories;
    CaseCommunicationFactories caseCommunicationFactories;
    Sprint36CasePage sprint36CasePage;
    LoginToSightline loginToSightline;
    CreateCase createCase;
    Faker faker;
    TemplateFactories templateFactories;
    Module_Navigation navigation;
    AddCaseCustodian addCaseCustodian;
    public String createdCase;
    public String createdCaseTemplate;
    public String createdCommunicationSeries;

    public Sprint36CasePageTestRunner() throws ParseException, IOException, InterruptedException {
        caseFactories = new CaseFactories(driver);
        caseCommunicationFactories = new CaseCommunicationFactories(driver);
        sprint36CasePage = new Sprint36CasePage(driver);
        loginToSightline = new LoginToSightline(driver);
        createCase = new CreateCase(driver);
        templateFactories = new TemplateFactories(driver);
        navigation = new Module_Navigation(driver);
        addCaseCustodian = new AddCaseCustodian(driver);
        faker = new Faker();
    }

    @Test(enabled = true)
    public void testing() throws InterruptedException {
//        createCase.createRandomCases();
//        caseFactories.populateCaseFields();
//        caseFactories.saveCase();
        navigation.navigateToTemplatesTAB();
    }

    @Test(priority = 1,enabled = false, description = "Creating a case with compliance reminder schedule as Maximum 1.")
    public void caseSaveWithMaxNumber() throws IOException, InterruptedException {

        createdCase = createCase.createRandomCases();
        navigation.navigateToCaseTAB();
        caseFactories.goToEditCase(createdCase);
        caseFactories.setComplianceReminderAsWeeklyWithMaxNumber(1);
        caseFactories.saveCase();
    }

    @Test(priority = 2,enabled = false, description = "Creating a case template with Compliance Reminder Maximum number as 5 " +
            "and applying that template to the created case from previous test case.")
    public void templateSaveWithMaxNumber() throws IOException, InterruptedException {
        navigation.navigateToTemplatesTAB();
        createdCaseTemplate = templateFactories.createRandomCaseTemplate();
        caseFactories.setComplianceReminderAsWeeklyWithMaxNumber(5);
        templateFactories.saveCaseTemplate();
        navigation.navigateToCaseTAB();
        caseFactories.goToEditCase(createdCase);
        templateFactories.applyCaseTemplate(createdCaseTemplate);
        caseFactories.saveCase();
    }

    @Test(priority = 3,enabled = false,description = "Adding multiple custodian to the case created case from previous test case.")
    public void addMultipleCustodianToACase() throws InterruptedException {
        caseFactories.NavigateToCustodiansTab();
        addCaseCustodian.upLoadCustodians();
    }

    @Test(priority = 4, enabled = false, description = "Creating a Custodian type communication series in that case after the template from previous " +
            "test case gets applied.")
    public void createCommunicationWithDefaultCaseComplianceReminderMaxNumberSchedule() throws InterruptedException, IOException {
        caseFactories.NavigateToCommunicationsTab();
        caseCommunicationFactories.goToCreateCustodianCommunicationPage();
        String caseNameCommunicationPage = driver.FindElementById("CaseName").getText();
        softAssert.assertEquals(caseNameCommunicationPage, createdCase);
        createdCommunicationSeries = caseCommunicationFactories.enterSeriesName();
        caseCommunicationFactories.addMailToRecipients("SLH-1");
        caseCommunicationFactories.enterCommunicationNameAndDescription();
        caseCommunicationFactories.enterAcknowledgmentEmailSubject("Automated Acknowledgment email for [CASE NAME]");
        caseCommunicationFactories.typeEmailBody("Email Type: Acknowledgment\n" +
                "[ACKNOWLEDGMENT LINK]\n" +
                "[CUSTODIAN PORTAL LINK]");
        caseCommunicationFactories.saveCommunicationSeries();
    }

    @Test(priority = 5,enabled = false, description = "Starting the previously created series with Compliance Reminder One Time")
    public void createCommunicationWithNewCaseComplianceReminderMaxNumberSchedule() throws InterruptedException {
        caseCommunicationFactories.goToEditCustodianCommunicationPage(createdCommunicationSeries);
        caseCommunicationFactories.enableComplianceReminder();
        caseCommunicationFactories.setComplianceReminderAsOneTime();
        caseCommunicationFactories.openComplianceReminderSubTab();
        caseCommunicationFactories.enterCommunicationNameAndDescription();
        caseCommunicationFactories.enterComplianceReminderEmailSubject("Automated Compliance Reminder email for [CASE NAME]");
        caseCommunicationFactories.typeEmailBody("Email Type: Compliance Reminder\n" +
                "[ACKNOWLEDGMENT LINK]\n" +
                "[CUSTODIAN PORTAL LINK]");

        caseCommunicationFactories.startCommunicationSeries();
        caseCommunicationFactories.verifyPostSendForCustodianSeries(createdCommunicationSeries);
    }
}
