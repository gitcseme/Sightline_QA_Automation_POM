package legalhold.sprintwork.sprint_40.testrunners;

import legalhold.legalholdpagefactory.LHMenus;
import legalhold.legalholdpagefactory.cases.CaseFactories;
import legalhold.legalholdpagefactory.template.TemplateFactories;
import legalhold.setup.BaseRunner;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

public class Sprint40TestRunner extends BaseRunner {
    CaseFactories caseFactories;
    TemplateFactories templateFactories;

    public Sprint40TestRunner() throws ParseException, IOException, InterruptedException {
        caseFactories = new CaseFactories(driver);
        templateFactories = new TemplateFactories(driver);
    }


    @Test(priority = 1)
    public void addEditDeleteCaseCorrespondent() throws InterruptedException {
        caseFactories.goToEditCase("Automation_Test");
        caseFactories.goToAddCorrespondentPage();
        HashMap correspondentList = caseFactories.addCaseEscalationCorrespondent("Corr");
        String empEmail01 = (String) correspondentList.get("Corr-01");
        String empEmail02 = (String) correspondentList.get("Corr-02");
        String empEmail03 = (String) correspondentList.get("Corr-03");
        caseFactories.saveCase();
        caseFactories.editCorrespondentByUncheckingEscalation(empEmail02);
        caseFactories.editCorrespondentByCheckingCC(empEmail02);


        caseFactories.editCorrespondentByCheckingSummaryCommunication(empEmail03);
        caseFactories.editCorrespondentByUncheckingEscalation(empEmail03);
        caseFactories.saveCase();

        caseFactories.deleteCorrespondent(empEmail02);
        caseFactories.deleteCorrespondent(empEmail03);
        caseFactories.deleteCorrespondent(empEmail01);
        caseFactories.saveCase();
    }

    @Test(priority = 2, enabled = true)
    public void addEditDeleteTemplateCorrespondent() throws InterruptedException, IOException {
        getNavigation().navigateToMenu(LHMenus.Templates);
        templateFactories.goToEditTemplatePage("Egypt Bogisich Group");
        caseFactories.goToAddCorrespondentPage();
        HashMap correspondentList = caseFactories.addCaseEscalationCorrespondent("Corr");
        String empEmail01 = (String) correspondentList.get("Corr-01");
        String empEmail02 = (String) correspondentList.get("Corr-02");
        String empEmail03 = (String) correspondentList.get("Corr-03");
        templateFactories.saveCaseTemplate();
        templateFactories.goToEditTemplatePage("Egypt Bogisich Group");
        caseFactories.editCorrespondentByUncheckingEscalation(empEmail02);
        caseFactories.editCorrespondentByCheckingCC(empEmail02);


        caseFactories.editCorrespondentByCheckingSummaryCommunication(empEmail03);
        caseFactories.editCorrespondentByUncheckingEscalation(empEmail03);
        templateFactories.saveCaseTemplate();
        templateFactories.goToEditTemplatePage("Egypt Bogisich Group");

        caseFactories.deleteCorrespondent(empEmail02);
        caseFactories.deleteCorrespondent(empEmail03);
        caseFactories.deleteCorrespondent(empEmail01);
        templateFactories.saveCaseTemplate();
    }
}
