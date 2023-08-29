package legalhold.sprint_36.testrunners;

import legalhold.BaseRunner;
import legalhold.legalholdpagefactory.cases.CaseFactories;
import legalhold.smoke_suite.sl_slh_integration.login_to_sightline.LoginToSightline;
import legalhold.sprint_36.testcases.Sprint36CasePage;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;

public class Sprint36CasePageTestRunner extends BaseRunner {
    CaseFactories caseFactories;
    Sprint36CasePage sprint36CasePage;
    LoginToSightline loginToSightline;

    public Sprint36CasePageTestRunner() throws ParseException, IOException, InterruptedException {
        caseFactories = new CaseFactories(driver);
        sprint36CasePage = new Sprint36CasePage(driver);
        loginToSightline = new LoginToSightline(driver);
    }

    @Test
    public void runSprint36CasePage() throws IOException, InterruptedException {
        loginToSightline.loginAsSystemAdmin("syslegalhold@gmail.com", "amikhelbona#2023", "Infinity Domain Expansion");
        caseFactories.goToEditCase("Pizza goat");
        sprint36CasePage.complianceReminderMaximumNumberSet(1);
    }
}
