package legalhold.sprint_36.testrunners;


import com.github.javafaker.Faker;
import legalhold.BaseRunner;
import legalhold.legalholdpagefactory.cases.CaseCommunicationFactories;
import legalhold.legalholdpagefactory.cases.CaseFactories;
import legalhold.legalholdpagefactory.template.TemplateFactories;
import legalhold.smoke_suite.cases.create_case.CreateCase;
import legalhold.smoke_suite.sl_slh_integration.login_to_sightline.LoginToSightline;
import legalhold.sprint_36.testcases.Sprint36CasePage;

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
    public String createdCase;
    public String createdCaseTemplate;

    public Sprint36CasePageTestRunner() throws ParseException, IOException, InterruptedException {
        caseFactories = new CaseFactories(driver);
        caseCommunicationFactories =new CaseCommunicationFactories(driver);
        sprint36CasePage = new Sprint36CasePage(driver);
        loginToSightline = new LoginToSightline(driver);
        createCase = new CreateCase(driver);
        templateFactories = new TemplateFactories(driver);
        faker = new Faker();
    }

    @Test(priority = 1)
    public void caseSaveWithMaxNumber() throws IOException, InterruptedException {
        loginToSightline.loginAsSystemAdmin("syslegalhold@gmail.com", "amikhelbona#2023", "Infinity Domain Expansion");
        createdCase = createCase.createRandomCases();
        driver.FindElementByXPath("//a[@href='/Case']").Click();
        driver.waitForPageToBeReady();
        caseFactories.goToEditCase(createdCase);
        sprint36CasePage.complianceReminderMaximumNumberSet(1);
        caseFactories.saveCase();
    }

    @Test(priority = 2)
    public void templateSaveWithMaxNumber() throws IOException, InterruptedException {
        sprint36CasePage.goToCase();
        createdCaseTemplate = templateFactories.createRandomCaseTemplate();
        sprint36CasePage.complianceReminderMaximumNumberSet(5);
        templateFactories.saveCaseTemplate();
        driver.FindElementByXPath("//a[@href='/Case']").Click();
        driver.waitForPageToBeReady();
        caseFactories.goToEditCase(createdCase);
        templateFactories.applyCaseTemplate(createdCaseTemplate);
        caseFactories.saveCase();
    }

    @Test(priority = 3)
    public void createCommunicationWithDefaultCaseComplianceReminderMaxNumberSchedule() {
        sprint36CasePage.goToCaseCommunicationTab();
        sprint36CasePage.goToCreateCustodianCommunicationPage();
        String caseNameCommunicationPage = driver.FindElementById("CaseName").getText();
        softAssert.assertEquals(caseNameCommunicationPage, createdCase);
        caseCommunicationFactories.enterCommunicationNameAndDescription();
        caseCommunicationFactories.enterAcknowledgmentEmailSubject();
    }


}
