package legalhold.sprintwork.sprint_36.testrunners;


import com.github.javafaker.Faker;
import legalhold.legalholdpagefactory.LHMenus;
import legalhold.legalholdpagefactory.domain_setup.DomainSetupTabNavigation;
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
    DomainSetupTabNavigation domainSetupTabNavigation;
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
        domainSetupTabNavigation = new DomainSetupTabNavigation(driver);
        addCaseCustodian = new AddCaseCustodian(driver);
        faker = new Faker();
    }

    @Test(priority = 1,enabled = true, description = "Creating a case with compliance reminder schedule as Maximum 1.")
    public void caseSaveWithMaxNumber() throws IOException, InterruptedException {

        createdCase = createCase.createRandomCases();
        caseFactories.populateCaseFields();
        caseFactories.setComplianceReminderAsCustomIntervalWithUnlimited(4);
        caseFactories.saveCase();
        navigation.navigateToMenu(LHMenus.Cases);
        caseFactories.goToEditCase(createdCase);
        caseFactories.setComplianceReminderAsWeeklyWithMaxNumber(1);
        caseFactories.saveCase();
    }

    @Test(priority = 2,enabled = true, description = "Creating a case template with Compliance Reminder Maximum number as 5 " +
            "and applying that template to the created case from previous test case.")
    public void templateSaveWithMaxNumber() throws IOException, InterruptedException {
        navigation.navigateToMenu(LHMenus.Templates);
        createdCaseTemplate = templateFactories.createRandomCaseTemplate();
        caseFactories.setComplianceReminderAsMonthlyUnlimited(7,25);
        templateFactories.saveCaseTemplate();
        templateFactories.goToEditTemplatePage(createdCaseTemplate);
        caseFactories.setComplianceReminderAsCustomIntervalWithMaxNumber(6,7);
        templateFactories.saveCaseTemplate();
        navigation.navigateToMenu(LHMenus.Cases);
        caseFactories.goToEditCase(createdCase);
        templateFactories.applyCaseTemplate(createdCaseTemplate);
        caseFactories.saveCase();
    }

    @Test(priority = 3,enabled = true,description = "Adding multiple custodian to the case created case from previous test case.")
    public void addMultipleCustodianToACase() throws InterruptedException {
        caseFactories.NavigateToCustodiansTab();
        addCaseCustodian.upLoadCustodians();
    }

    @Test(priority = 4, enabled = true, description = "Creating a Custodian type communication series in that case after the template from previous " +
            "test case gets applied.")
    public void createCommunicationWithDefaultCaseComplianceReminderMaxNumberSchedule() throws InterruptedException, IOException {
        caseFactories.NavigateToCommunicationsTab();
        caseCommunicationFactories.goToCreateCustodianCommunicationPage();
        String caseNameCommunicationPage = driver.FindElementById("CaseName").getText();
        softAssert.assertEquals(caseNameCommunicationPage, createdCase);
        createdCommunicationSeries = caseCommunicationFactories.enterSeriesName();
        caseCommunicationFactories.addMailToRecipients("Auto-1");
        caseCommunicationFactories.enterCommunicationNameAndDescription();
        caseCommunicationFactories.enterAcknowledgmentEmailSubject("Automated Acknowledgment email for [CASE NAME]");
        caseCommunicationFactories.typeEmailBody("Email Type: Acknowledgment\n" +
                "[ACKNOWLEDGMENT LINK]\n" +
                "[CUSTODIAN PORTAL LINK]");
        caseCommunicationFactories.saveCommunicationSeries();
    }

    @Test(priority = 5,enabled = true, description = "Starting the previously created series with Compliance Reminder One Time")
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
