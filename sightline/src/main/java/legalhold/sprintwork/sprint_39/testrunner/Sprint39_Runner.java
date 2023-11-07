package legalhold.sprintwork.sprint_39.testrunner;

import legalhold.legalholdpagefactory.LHMenus;
import legalhold.legalholdpagefactory.cases.CaseCommunicationFactories;
import legalhold.legalholdpagefactory.cases.CaseFactories;
import legalhold.legalholdpagefactory.cases.CustodianFactories;
import legalhold.legalholdpagefactory.cases.PreservationFactories;
import legalhold.legalholdpagefactory.domain_setup.DomainSetupTabNavigation;
import legalhold.legalholdpagefactory.domain_setup.data_migration.DataMigrationFactories;
import legalhold.setup.BaseRunner;
import legalhold.sprintwork.sprint_39.testcases.Sprint39_ReleaseWithCom;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import com.github.javafaker.Faker;

public class Sprint39_Runner extends BaseRunner {


    CaseFactories caseFactories;
    CaseCommunicationFactories communicationFactories;
    CustodianFactories custodianFactories;
    PreservationFactories preservationFactories;
    Sprint39_ReleaseWithCom sprint39_releaseWithCom;
    Faker faker;
    String seriesName = "Release";
    private DataMigrationFactories dataMigrationFactories;
    DomainSetupTabNavigation domainSetupTabNavigation;


    public Sprint39_Runner() throws ParseException, IOException, InterruptedException {
        caseFactories = new CaseFactories(driver);
        faker = new Faker();
        communicationFactories = new CaseCommunicationFactories(driver);
        custodianFactories = new CustodianFactories(driver);
        sprint39_releaseWithCom = new Sprint39_ReleaseWithCom(driver);
        dataMigrationFactories = new DataMigrationFactories(driver);
        domainSetupTabNavigation = new DomainSetupTabNavigation(driver);
    }
//    @Test(priority = 1, enabled = true, description = "Creating a Release communication (Only Save)")
//    public void createReleaseCom() throws InterruptedException {
//        caseFactories.goToEditCase("Automation_Test");
//        caseFactories.NavigateToCommunicationsTab();
//        communicationFactories.goToCreateReleaseCommunicationPage();
//        seriesName = communicationFactories.enterSeriesName();
//        communicationFactories.enterCommunicationNameAndDescription();
//        String emailSub = "Release"+faker.number().digit();
//        communicationFactories.enterEmailSubject(emailSub);
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
        custodianFactories.releaseWithCommunication("Auto-1", seriesName);
        Thread.sleep(3000);
        custodianFactories.verifyIfCustodianIsReleased("Auto-1");
    }

    @Test(priority = 3, enabled = true, description = "Release single custodian with communication")
    public void releaseWithComSingle() throws InterruptedException {

        caseFactories.NavigateToCustodiansTab();
        custodianFactories.releaseWithCommunication("released",seriesName);
        Thread.sleep(3000);
        custodianFactories.verifyIfCustodianIsReleased("released");


    }

    @Test(priority = 4, enabled = true, description = "Release multiple custodians without communication")
    public void releaseWithoutComMultiple() throws InterruptedException {
        driver.getWebDriver().navigate().refresh();
        caseFactories.NavigateToCustodiansTab();
        custodianFactories.releaseWithoutCommunication("Auto-2");
        custodianFactories.verifyIfCustodianIsReleased("Auto-2");


    }

    @Test(priority = 5, enabled = true, description = "Release multiple custodians without communication")
    public void releaseWithoutComSingle() throws InterruptedException {
        driver.getWebDriver().navigate().refresh();
        caseFactories.NavigateToCustodiansTab();
        custodianFactories.releaseWithoutCommunication("PH-2");
        custodianFactories.verifyIfCustodianIsReleased("PH-2");


    }


    @Test(priority = 6, enabled = true, description = "Verify Release with Communication with all silent custodians")
    public void releaseWithAllSilent() throws InterruptedException {

        custodianFactories.releaseWithCommunication("releasedSilent",seriesName);
        custodianFactories.verifyIfCustodianIsReleased("releasedSilent");


    }

//        @Test(priority = 7, enabled = true)
//        public void checkMigrationFailedStatus() throws InterruptedException, IOException {
//            getNavigation().navigateToMenu(LHMenus.DomainSetup);
//            dataMigrationFactories.goToDataMigrationTab();
//            dataMigrationFactories.openModalAndSelectFileForUpload("FailedMigrationFilePath");
////            dataMigrationFactories.checkMigrationResultStatus("In-Progress", 0000);
//            dataMigrationFactories.checkMigrationResultStatus("Failed", 5000);
//        }
//
//        @Test(priority = 8, enabled = true)
//        public void checkMigrationPartiallyFailedStatus() throws InterruptedException, IOException {
//            dataMigrationFactories.goToDataMigrationTab();
//            dataMigrationFactories.openModalAndSelectFileForUpload("PartiallyFailedMigrationFilePath");
////            dataMigrationFactories.checkMigrationResultStatus("In-Progress", 0000);
//            dataMigrationFactories.checkMigrationResultStatus("Partially-Failed", 5000);
//        }
//
//        @Test(priority = 9, enabled = true)
//        public void checkMigrationSuccessStatus() throws InterruptedException, IOException {
//
//            dataMigrationFactories.goToDataMigrationTab();
//            dataMigrationFactories.openModalAndSelectFileForUpload("SuccessMigrationFilePath");
////            dataMigrationFactories.checkMigrationResultStatus("In-Progress", 0000);
//            dataMigrationFactories.checkMigrationResultStatus("Success", 5000);
//        }

    @Test(priority = 10,enabled = true)
    public void checkIfCustodiansAddedInReleaseCommunication() throws InterruptedException {

        custodianFactories.clearFilter();
        List<String> allSilents = custodianFactories.getAllSilentCustodians();
        custodianFactories.clearFilter();
        List <String> allReleased = custodianFactories.verifyIfCustodianIsReleased("released");
        List <String> releasedAndNonSilentList = custodianFactories.filterReleasedAndNonSilentCustodians(allSilents,allReleased);

        caseFactories.NavigateToCommunicationsTab();
        communicationFactories.goToEditReleaseCommunicationPage(seriesName);
        communicationFactories.navigateToManageRecipientPage();

        custodianFactories.checkIfCustodiansAreAddedInReleaseCommunication(releasedAndNonSilentList);
    }



}