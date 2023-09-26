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

    @Test(priority = 1)
    public void createHold() throws InterruptedException {
        caseFactories.goToEditCase("Test");
        caseFactories.NavigateToPreservationTab();
        preservationFactories.goToCreatePreservationHold();
        createdPreservationHoldName = preservationFactories.enterHoldNameAndDescription();
        preservationFactories.addPreservationCustodianAndTeams("7", "demo");
        preservationFactories.addPreservationSite("jbush");
        preservationFactories.savePreservationHold();
    }

    @Test(priority = 2)
    public void getXYCount(){
        allCountExceptError = preservationFactories.getTotalPreservationCountExceptError();
        System.out.println("The value of Y is: "+allCountExceptError);
    }

    @Test(priority = 3)
    public void uploadZipFileForMigration() {
        dataMigrationFactories.goToDataMigrationTab();
        dataMigrationFactories.openModalAndSelectFileForUpload();
        dataMigrationFactories.checkPendingStatus();
    }
}
