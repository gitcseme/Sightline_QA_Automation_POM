package legalhold.sprintwork.sprint_38.testrunner;

import legalhold.legalholdpagefactory.cases.CaseFactories;
import legalhold.legalholdpagefactory.cases.PreservationFactories;
import legalhold.legalholdpagefactory.domain_setup.data_migration.DataMigrationFactories;
import legalhold.setup.BaseRunner;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;

public class Sprint38TestRunner extends BaseRunner {
    CaseFactories caseFactories;
    PreservationFactories preservationFactories;
    public static String createdPreservationHoldName;
    public static int allCountExceptError;
    public static int activeCount;
    private DataMigrationFactories dataMigrationFactories;

    public Sprint38TestRunner() throws ParseException, IOException, InterruptedException {
        caseFactories = new CaseFactories(driver);
        preservationFactories = new PreservationFactories(driver);
        dataMigrationFactories = new DataMigrationFactories(driver);
    }

    @Test(priority = 1, enabled = true)
    public void goToPreservationTab() throws InterruptedException {
        caseFactories.goToEditCase("Case Preservation Hold");
        caseFactories.NavigateToPreservationTab();
    }

    @Test(priority = 2, enabled = true)
    public void createHold() throws InterruptedException {
        preservationFactories.goToCreatePreservationHold();
        createdPreservationHoldName = preservationFactories.enterHoldNameAndDescription();
        preservationFactories.selectDataSourceFromDropdown("Data Source Parvez");
        preservationFactories.addPreservationCustodianAndTeams("PH-11", "demo");
        preservationFactories.addPreservationSite("jbush");
        preservationFactories.savePreservationHold();
        preservationFactories.isPreservationHoldActive(createdPreservationHoldName);
    }

    @Test(priority = 3, enabled = true)
    public void getXYCountFromCasePreservationTab() {
        allCountExceptError = preservationFactories.getTotalPreservationCountExceptError();
        System.out.println("The value of Y is: " + allCountExceptError);
    }

    @Test(priority = 4, enabled = true)
    public void EditHold() throws InterruptedException {

        preservationFactories.goToEditPreservationHold(createdPreservationHoldName);
        preservationFactories.addPreservationCustodianAndTeams("PH-21", "Collection");
        preservationFactories.addPreservationSite("an");
        preservationFactories.savePreservationHold();
        preservationFactories.isPreservationHoldActive(createdPreservationHoldName);

//        preservationFactories.groupReleasePreservationHold(createdPreservationHoldName);
//        preservationFactories.isPreservationHoldReleased(createdPreservationHoldName);
    }

    @Test(priority = 5, enabled = true)
    public void releasePreservation() throws InterruptedException {
        preservationFactories.releasePreservationCustodianFromDataTable(createdPreservationHoldName, "ph-11");
    }
  
    @Test(priority = 6)
    public void uploadZipFileForMigration() {
        dataMigrationFactories.goToDataMigrationTab();
        dataMigrationFactories.openModalAndSelectFileForUpload();
        dataMigrationFactories.checkPendingStatus();
    }

}
