package legalhold.sprintwork.sprint_38.testrunner;

import legalhold.legalholdpagefactory.LHMenus;
import legalhold.legalholdpagefactory.Module_Navigation;
import legalhold.legalholdpagefactory.cases.CaseFactories;
import legalhold.legalholdpagefactory.cases.PreservationFactories;
import legalhold.legalholdpagefactory.domain_setup.DomainSetupTabNavigation;
import legalhold.legalholdpagefactory.domain_setup.DomainSetupTabs;
import legalhold.legalholdpagefactory.domain_setup.data_migration.DataMigrationFactories;
import legalhold.setup.BaseRunner;
import legalhold.sprintwork.sprint_38.testcases.Sprint38;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

public class Sprint38TestRunner extends BaseRunner {
    CaseFactories caseFactories;
    PreservationFactories preservationFactories;
    Sprint38 sprint38;
    DomainSetupTabNavigation domainSetupTabNavigation;
    public static String createdPreservationHoldName;
    public static int preservationAllCountExceptReleased;
    public static int preservationAllActiveCount;
    private DataMigrationFactories dataMigrationFactories;

    public Sprint38TestRunner() throws ParseException, IOException, InterruptedException {
        caseFactories = new CaseFactories(driver);
        preservationFactories = new PreservationFactories(driver);
        dataMigrationFactories = new DataMigrationFactories(driver);
        sprint38 = new Sprint38(driver);
        domainSetupTabNavigation = new DomainSetupTabNavigation(driver);
    }

    @Test(priority = 1, enabled = true)
    public void goToPreservationTab() throws InterruptedException {
        caseFactories.goToEditCase("Demo 37");
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
    public void getXYCountAfterCreatingHold() throws InterruptedException, IOException {
        driver.scrollingToBottomofAPage();
        preservationAllActiveCount = preservationFactories.getActivePreservationCount();
        System.out.println("From Preservation Page: The value of X is: " + preservationAllActiveCount);
        preservationAllCountExceptReleased = preservationFactories.getTotalPreservationCountExceptReleased();
        System.out.println("From Preservation Page: The value of Y is: " + preservationAllCountExceptReleased);

        getNavigation().navigateToMenu(LHMenus.Cases);

        HashMap countMap = sprint38.getPreservationsColumn_X_Y_Value("Demo 37");
        System.out.println("From Case Table: The value of X is " + countMap.get("valueX") + " and the value of Y is " + countMap.get("valueY"));
        if ((Integer) countMap.get("valueX") == preservationAllActiveCount && (Integer) countMap.get("valueY") == preservationAllCountExceptReleased) {
            System.out.println("The X and Y value of the Preservations column on the Case List matches with the active and all preservation count of the" +
                    " preservation holds available in the case");
        } else {
            throw new RuntimeException("X/Y Count does not match");
        }
    }

    @Test(priority = 4, enabled = true)
    public void navigateBackToPreservationTab() throws InterruptedException {
        caseFactories.goToEditCase("Demo 37");
        caseFactories.NavigateToPreservationTab();
    }

    @Test(priority = 5, enabled = true)
    public void EditHold() throws InterruptedException {
        preservationFactories.goToEditPreservationHold(createdPreservationHoldName);
        preservationFactories.addPreservationCustodianAndTeams("PH-21", "Collection");
        preservationFactories.addPreservationSite("saitx");
        preservationFactories.savePreservationHold();
        preservationFactories.isPreservationHoldActive(createdPreservationHoldName);
    }

    @Test(priority = 6, enabled = true)
    public void getXYCountAfterEditingHold() throws InterruptedException, IOException {
        driver.scrollingToBottomofAPage();
        preservationAllActiveCount = preservationFactories.getActivePreservationCount();
        System.out.println("From Preservation Page: The value of X is: " + preservationAllActiveCount);
        preservationAllCountExceptReleased = preservationFactories.getTotalPreservationCountExceptReleased();
        System.out.println("From Preservation Page: The value of Y is: " + preservationAllCountExceptReleased);
        getNavigation().navigateToMenu(LHMenus.Cases);
        HashMap countMap = sprint38.getPreservationsColumn_X_Y_Value("Demo 37");
        System.out.println("From Case Table: The value of X is " + countMap.get("valueX") + " and the value of Y is " + countMap.get("valueY"));
        if ((Integer) countMap.get("valueX") == preservationAllActiveCount && (Integer) countMap.get("valueY") == preservationAllCountExceptReleased) {
            System.out.println("The X and Y value of the Preservations column on the Case List matches with the active and all preservation count of the" +
                    " preservation holds available in the case");
        } else {
            throw new RuntimeException("X/Y Count does not match");
        }
    }

    @Test(priority = 7, enabled = true)
    public void releasePreservations() throws InterruptedException {
        caseFactories.goToEditCase("Demo 37");
        caseFactories.NavigateToPreservationTab();
        preservationFactories.releasePreservationCustodianFromDataTable(createdPreservationHoldName, "ph");
        preservationFactories.verifyIfCustodianIsReleased(createdPreservationHoldName, "ph");
    }

    @Test(priority = 8, enabled = true)
    public void releaseWholePreservationHold() throws InterruptedException {
        preservationFactories.groupReleasePreservationHold(createdPreservationHoldName);
        preservationFactories.isPreservationHoldReleased(createdPreservationHoldName);
    }

    @Test(priority = 9, enabled = true)
    public void deleteWholePreservationHold() throws InterruptedException {
        preservationFactories.deletePreservationHold(createdPreservationHoldName);
        preservationFactories.isPreservationHoldDeleted(createdPreservationHoldName);
    }

    @Test(priority = 10, enabled = true)
    public void checkMigrationFailedStatus() throws InterruptedException, IOException {
        dataMigrationFactories.goToDataMigrationTab();
        dataMigrationFactories.openModalAndSelectFileForUpload("FailedMigrationFilePath");
        dataMigrationFactories.checkMigrationResultStatus("In-Progress", 2000);
        dataMigrationFactories.checkMigrationResultStatus("Failed", 5000);
    }

    @Test(priority = 11, enabled = true)
    public void checkMigrationPartiallyFailedStatus() throws InterruptedException, IOException {
        dataMigrationFactories.goToDataMigrationTab();
        dataMigrationFactories.openModalAndSelectFileForUpload("PartiallyFailedMigrationFilePath");
        dataMigrationFactories.checkMigrationResultStatus("In-Progress", 2000);
        dataMigrationFactories.checkMigrationResultStatus("Partially-Failed", 5000);
    }

    @Test(priority = 12, enabled = true)
    public void checkMigrationSuccessStatus() throws InterruptedException, IOException {
        dataMigrationFactories.goToDataMigrationTab();
        dataMigrationFactories.openModalAndSelectFileForUpload("SuccessMigrationFilePath");
        dataMigrationFactories.checkMigrationResultStatus("In-Progress", 1500);
        dataMigrationFactories.checkMigrationResultStatus("Success", 5000);
    }
}
