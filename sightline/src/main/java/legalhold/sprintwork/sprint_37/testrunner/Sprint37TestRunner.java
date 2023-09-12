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

    @Test(priority = 1, description = "")
    public void caseSaveWithMaxNumber() throws IOException, InterruptedException {

        createdCase = createCase.createRandomCases();
        navigation.navigateToCaseTAB();
        caseFactories.goToEditCase(createdCase);
        sprint37.setComplianceReminderCustomInterval();
//        caseFactories.saveCase();
    }
}
