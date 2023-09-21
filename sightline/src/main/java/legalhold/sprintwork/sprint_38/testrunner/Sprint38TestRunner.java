package legalhold.sprintwork.sprint_38.testrunner;

import legalhold.legalholdpagefactory.Module_Navigation;
import legalhold.legalholdpagefactory.cases.CaseFactories;
import legalhold.legalholdpagefactory.cases.PreservationFactories;
import legalhold.setup.BaseRunner;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;

public class Sprint38TestRunner extends BaseRunner {
    Module_Navigation navigation;
    CaseFactories caseFactories;
    PreservationFactories preservationFactories;
    public static String createdPreservationHoldName;

    public Sprint38TestRunner() throws ParseException, IOException, InterruptedException {
        caseFactories = new CaseFactories(driver);
        navigation = new Module_Navigation(driver);
        preservationFactories = new PreservationFactories(driver);
    }

    @Test
    public void createHold() throws InterruptedException {
        caseFactories.goToEditCase("Test");
        caseFactories.NavigateToPreservationTab();
        preservationFactories.goToCreatePreservationHold();
        createdPreservationHoldName = preservationFactories.enterHoldNameAndDescription();
        preservationFactories.addPreservationCustodianAndTeams("7", "demo");
        preservationFactories.addPreservationSite("jbush");
        preservationFactories.savePreservationHold();
    }
}
