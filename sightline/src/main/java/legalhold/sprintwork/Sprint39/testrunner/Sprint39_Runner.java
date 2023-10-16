package legalhold.sprintwork.Sprint39.testrunner;

import legalhold.legalholdpagefactory.cases.CaseCommunicationFactories;
import legalhold.legalholdpagefactory.cases.CaseFactories;
import legalhold.legalholdpagefactory.cases.CustodianFactories;
import legalhold.legalholdpagefactory.cases.PreservationFactories;
import legalhold.legalholdpagefactory.domain_setup.data_migration.DataMigrationFactories;
import legalhold.setup.BaseRunner;
import legalhold.sprintwork.Sprint39.testcases.Sprint39_ReleaseWithCom;
import legalhold.sprintwork.sprint_38.testcases.Sprint38;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;
import com.github.javafaker.Faker;

public class Sprint39_Runner extends BaseRunner {


    CaseFactories caseFactories;
    CaseCommunicationFactories communicationFactories;
    CustodianFactories custodianFactories;
    PreservationFactories preservationFactories;
    Sprint39_ReleaseWithCom sprint39_releaseWithCom;
    Faker faker;
    String seriesName = "Release";


    public Sprint39_Runner() throws ParseException, IOException, InterruptedException {
        caseFactories = new CaseFactories(driver);
        faker = new Faker();
        communicationFactories = new CaseCommunicationFactories(driver);
        custodianFactories = new CustodianFactories(driver);
        sprint39_releaseWithCom = new Sprint39_ReleaseWithCom(driver);
    }
//    @Test(priority = 1, enabled = true, description = "Creating a Release communication (Only Save)")
//    public void createReleaseCom() throws InterruptedException {
//        caseFactories.goToEditCase("Automation_Test");
//        caseFactories.NavigateToCommunicationsTab();
//        communicationFactories.goToCreateReleaseCommunicationPage();
//        seriesName = communicationFactories.enterSeriesName();
//        communicationFactories.enterCommunicationNameAndDescription();
//        communicationFactories.enterEmailSubject("Release"+faker.number());
//        driver.scrollingToBottomofAPage();
//        communicationFactories.typeEmailBody("Release");
//        communicationFactories.saveCommunicationSeries();
//
//
//    }


    @Test(priority = 2, enabled = true, description = "Release multiple custodians with communication")
    public void releaseWithComMultiple() throws InterruptedException {
        caseFactories.goToEditCase("Automation_Test");
        caseFactories.NavigateToCustodiansTab();
        custodianFactories.releaseWithCommunication("Auto-1",seriesName);
        Thread.sleep(3000);
        custodianFactories.verifyIfCustodianIsReleased("Auto-1");


    }
    @Test(priority = 3, enabled = true, description = "Release single custodian with communication")
    public void releaseWithComSingle() throws InterruptedException {

        caseFactories.NavigateToCustodiansTab();
        custodianFactories.releaseWithCommunication("PH-1",seriesName);
        Thread.sleep(3000);
        custodianFactories.verifyIfCustodianIsReleased("PH-1");


    }
    @Test(priority = 4,enabled = true, description = "Release multiple custodians without communication")
    public void releaseWithoutComMultiple() throws InterruptedException {
        driver.getWebDriver().navigate().refresh();
        caseFactories.NavigateToCustodiansTab();
        custodianFactories.releaseWithoutCommunication("Auto-2");
        custodianFactories.verifyIfCustodianIsReleased("Auto-2");



    }

    @Test(priority = 5,enabled = true, description = "Release multiple custodians without communication")
    public void releaseWithoutComSingle() throws InterruptedException {
        driver.getWebDriver().navigate().refresh();
        caseFactories.NavigateToCustodiansTab();
        custodianFactories.releaseWithoutCommunication("PH-2");
        custodianFactories.verifyIfCustodianIsReleased("PH-2");



    }

}
