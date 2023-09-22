package legalhold.legalholdpagefactory.cases;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import legalhold.setup.BaseModule;
import legalhold.utilities.parse_locators.LocatorReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PreservationFactories extends BaseModule {

    public PreservationFactories(Driver driver) throws IOException {
        super("src/main/java/legalhold/selectors/cases/manage_case/preservation/preservation.properties", driver);
    }

    public void goToCreatePreservationHold() {
        Element btnCreatePreservation = driver.FindElementById(locatorReader.getobjectLocator("btnCreatePreservation"));
        wait.until(ExpectedConditions.elementToBeClickable(btnCreatePreservation.getWebElement()));
        btnCreatePreservation.waitAndClick(30);
        driver.waitForPageToBeReady();
    }

    public String enterHoldNameAndDescription() {
        String holdName = faker.funnyName().name() + " " + faker.country().capital() + " Hold";
        Element inputHoldName = driver.FindElementById(locatorReader.getobjectLocator("inputHoldName"));
        wait.until(ExpectedConditions.elementToBeClickable(inputHoldName.getWebElement()));
        inputHoldName.SendKeys(holdName);
        driver.FindElementById(locatorReader.getobjectLocator("inputHoldDescription")).SendKeys(faker.lorem().sentence(10));
        return holdName;
    }

    public void addCustodianBySelectAll(String empId) throws InterruptedException {
        String expectedPaginationText = "Showing 1 to 1 of 1 entries";
        driver.FindElementById(locatorReader.getobjectLocator("subTabCustodian")).waitAndClick(30);
        Element searchEmpIdAvailableCustodianTable = driver.FindElementByCssSelector(locatorReader.getobjectLocator("searchEmpIdAvailableCustodianTable"));
        wait.until(ExpectedConditions.elementToBeClickable(searchEmpIdAvailableCustodianTable.getWebElement()));
        searchEmpIdAvailableCustodianTable.SendKeys(empId);
        Thread.sleep(2000);
        wait.until(ExpectedConditions.textToBe(By.id(locatorReader.getobjectLocator("paginationTextAvailableCustodianTable")), expectedPaginationText));

        driver.FindElementByCssSelector(locatorReader.getobjectLocator("selectAllAvailableCustodianTable")).waitAndClick(30);
        driver.FindElementById(locatorReader.getobjectLocator("btnAddCustodian")).waitAndClick(30);

        Element searchEmpIdSelectedCustodianTable = driver.FindElementByCssSelector(locatorReader.getobjectLocator("searchEmpIdSelectedCustodianTable"));
        wait.until(ExpectedConditions.elementToBeClickable(searchEmpIdSelectedCustodianTable.getWebElement()));
        searchEmpIdSelectedCustodianTable.SendKeys(empId);
        Thread.sleep(2000);
        wait.until(ExpectedConditions.textToBe(By.id(locatorReader.getobjectLocator("paginationTextSelectedCustodianTable")), expectedPaginationText));
    }

    public void addTeamsBySelectAll(String teamName) throws InterruptedException {
        String expectedPaginationText = "Showing 1 to 1 of 1 entries";
        driver.FindElementById(locatorReader.getobjectLocator("subTabTeams")).waitAndClick(30);
        Thread.sleep(5000);
        Element searchTeamNamesAvailableMailboxTable = driver.FindElementByCssSelector(locatorReader.getobjectLocator("searchTeamNamesAvailableMailboxTable"));
        wait.until(ExpectedConditions.elementToBeClickable(searchTeamNamesAvailableMailboxTable.getWebElement()));
        searchTeamNamesAvailableMailboxTable.SendKeys(teamName);
        Thread.sleep(3000);
        wait.until(ExpectedConditions.textToBe(By.id(locatorReader.getobjectLocator("paginationTextAvailableTeamMailboxTable")), expectedPaginationText));

        driver.FindElementByCssSelector(locatorReader.getobjectLocator("selectAllAvailableTeamMailboxTable")).waitAndClick(30);
        driver.FindElementById(locatorReader.getobjectLocator("btnAddTeams")).waitAndClick(30);

        Element searchTeamNamesSelectedMailboxTable = driver.FindElementByCssSelector(locatorReader.getobjectLocator("searchTeamNamesSelectedMailboxTable"));
        wait.until(ExpectedConditions.elementToBeClickable(searchTeamNamesSelectedMailboxTable.getWebElement()));
        searchTeamNamesSelectedMailboxTable.SendKeys(teamName);
        Thread.sleep(2000);
        wait.until(ExpectedConditions.textToBe(By.id(locatorReader.getobjectLocator("paginationTextSelectedTeamMailboxTable")), expectedPaginationText));
    }

    public void addOneSiteBySelectAll(String siteName) throws InterruptedException {
        String expectedPaginationText = "Showing 1 to 1 of 1 entries";
        Thread.sleep(5000);
        Element searchSiteNameAvailableSiteTable = driver.FindElementByCssSelector(locatorReader.getobjectLocator("searchSiteNameAvailableSiteTable"));
        wait.until(ExpectedConditions.elementToBeClickable(searchSiteNameAvailableSiteTable.getWebElement()));
        searchSiteNameAvailableSiteTable.SendKeys(siteName);
        Thread.sleep(5000);
        wait.until(ExpectedConditions.textToBe(By.id(locatorReader.getobjectLocator("paginationTextAvailableSiteTable")), expectedPaginationText));

        driver.FindElementByCssSelector(locatorReader.getobjectLocator("selectAllAvailableSiteTable")).waitAndClick(30);
        driver.FindElementById(locatorReader.getobjectLocator("btnAddSite")).waitAndClick(30);

        Element searchSiteNameSelectedSiteTable = driver.FindElementByCssSelector(locatorReader.getobjectLocator("searchSiteNameSelectedSiteTable"));
        wait.until(ExpectedConditions.elementToBeClickable(searchSiteNameSelectedSiteTable.getWebElement()));
        searchSiteNameSelectedSiteTable.SendKeys(siteName);
        Thread.sleep(3000);
        wait.until(ExpectedConditions.textToBe(By.id(locatorReader.getobjectLocator("paginationTextSelectedSiteTable")), expectedPaginationText));
    }

    public void goToManageCustodianTeams() throws InterruptedException {
        Element btnManageCustodianTeams = driver.FindElementByCssSelector(locatorReader.getobjectLocator("btnManageCustodianTeams"));
        wait.until(ExpectedConditions.elementToBeClickable(btnManageCustodianTeams.getWebElement()));
        btnManageCustodianTeams.waitAndClick(30);
        Thread.sleep(3000);
        wait.until(ExpectedConditions.urlContains("/Preservation/ManageCustodianAndTeams"));
        System.out.println("Successfully navigated to Preservation Manage Custodian Page");
        driver.waitForPageToBeReady();
    }

    public void goToManageSite() throws InterruptedException {
        Element btnManageSite = driver.FindElementById(locatorReader.getobjectLocator("btnManageSite"));
        wait.until(ExpectedConditions.elementToBeClickable(btnManageSite.getWebElement()));
        btnManageSite.waitAndClick(30);
        Thread.sleep(3000);
        wait.until(ExpectedConditions.urlContains("/Preservation/PreservationManageSite"));
        System.out.println("Successfully navigated to Preservation Manage Site Page");
        driver.waitForPageToBeReady();
    }

    public void saveManagePreservationCustodianTeams() throws InterruptedException {
        Element btnSaveManageCustodianTeams = driver.FindElementByCssSelector(locatorReader.getobjectLocator("btnSaveManageCustodianTeams"));
        wait.until(ExpectedConditions.elementToBeClickable(btnSaveManageCustodianTeams.getWebElement()));
        driver.scrollingToElementofAPage(btnSaveManageCustodianTeams);
        btnSaveManageCustodianTeams.waitAndClick(30);
        driver.FindElementById(locatorReader.getobjectLocator("okBtnSaveModalManageCustodianTeams")).waitAndClick(30);
        Thread.sleep(2000);
        driver.waitForPageToBeReady();
    }

    public void saveManageSite() throws InterruptedException {
        Element btnSaveManageSites = driver.FindElementByCssSelector(locatorReader.getobjectLocator("btnSaveManageSites"));
        wait.until(ExpectedConditions.elementToBeClickable(btnSaveManageSites.getWebElement()));
        driver.scrollingToElementofAPage(btnSaveManageSites);
        btnSaveManageSites.waitAndClick(30);
        driver.FindElementById(locatorReader.getobjectLocator("okBtnSaveModalManageSite")).waitAndClick(30);
        Thread.sleep(2000);
        driver.waitForPageToBeReady();
    }

    public void savePreservationHold() throws InterruptedException {
        Element btnSavePreservationHold = driver.FindElementById(locatorReader.getobjectLocator("btnSavePreservationHold"));
        wait.until(ExpectedConditions.elementToBeClickable(btnSavePreservationHold.getWebElement()));
        driver.scrollingToElementofAPage(btnSavePreservationHold);
        btnSavePreservationHold.waitAndClick(30);

        driver.FindElementById(locatorReader.getobjectLocator("okBtnSaveModalPreservationHold")).waitAndClick(30);
        Thread.sleep(5000);
        driver.waitForPageToBeReady();
    }


    public void addPreservationCustodian(String empId) throws InterruptedException {
        goToManageCustodianTeams();
        addCustodianBySelectAll(empId);
        saveManagePreservationCustodianTeams();
    }

    public void addPreservationCustodianAndTeams(String empId, String teamName) throws InterruptedException {
        goToManageCustodianTeams();
        addCustodianBySelectAll(empId);
        addTeamsBySelectAll(teamName);
        saveManagePreservationCustodianTeams();
    }

    public void addPreservationSite(String siteName) throws InterruptedException {
        goToManageSite();
        addOneSiteBySelectAll(siteName);
        saveManageSite();
    }

    public void addPreservationTeams(String teamName) throws InterruptedException {
        goToManageCustodianTeams();
        addTeamsBySelectAll(teamName);
        saveManagePreservationCustodianTeams();
    }

    public int getActivePreservationCount() {
        Element dropdownStatus = driver.FindElementByCssSelector(locatorReader.getobjectLocator("dropdownStatus"));
        dropdownStatus.selectFromDropdown().selectByValue("Active");
        String paginationTextPreservationDataTable = driver.FindElementById(locatorReader.getobjectLocator("paginationTextPreservationDataTable")).getText();

        // Define a regular expression pattern to match integers
        Pattern pattern = Pattern.compile("of (\\d+)");

        // Create a Matcher object and apply the pattern to the input string
        Matcher matcher = pattern.matcher(paginationTextPreservationDataTable);

        // Find and extract the integer
        int activeCount = 0;
        while (matcher.find()) {
            activeCount = Integer.parseInt(matcher.group(1));
        }
        System.out.println("Extracted active preservation count: " + activeCount);
        driver.FindElementById(locatorReader.getobjectLocator("preservationDataTableClearFilter")).waitAndClick(30);
        return activeCount;
    }

    public int getErrorPreservationCount() {
        Element dropdownStatus = driver.FindElementByCssSelector(locatorReader.getobjectLocator("dropdownStatus"));
        dropdownStatus.selectFromDropdown().selectByValue("Error");
        String paginationTextPreservationDataTable = driver.FindElementById(locatorReader.getobjectLocator("paginationTextPreservationDataTable")).getText();

        Pattern pattern = Pattern.compile("of (\\d+)");
        Matcher matcher = pattern.matcher(paginationTextPreservationDataTable);

        int errorCount = 0;
        while (matcher.find()) {
            errorCount = Integer.parseInt(matcher.group(1));
        }
        System.out.println("Extracted Error preservation count: " + errorCount);
        driver.FindElementById(locatorReader.getobjectLocator("preservationDataTableClearFilter")).waitAndClick(30);
        return errorCount;
    }

    public int getTotalPreservationCountExceptError() {
        String paginationTextPreservationDataTable = driver.FindElementById(locatorReader.getobjectLocator("paginationTextPreservationDataTable")).getText();
        Element dropdownStatus = driver.FindElementByCssSelector(locatorReader.getobjectLocator("dropdownStatus"));
        dropdownStatus.selectFromDropdown().selectByValue("Error");
        String paginationTextPreservationDataTableAfterFiltering = driver.FindElementById(locatorReader.getobjectLocator("paginationTextPreservationDataTable")).getText();

        Pattern pattern = Pattern.compile("of (\\d+)");

        Matcher matcherAll = pattern.matcher(paginationTextPreservationDataTable);
        Matcher matcherError = pattern.matcher(paginationTextPreservationDataTableAfterFiltering);

        int errorCount = 0;
        int allCount = 0;
        while (matcherAll.find()) {
            allCount = Integer.parseInt(matcherAll.group(1));
        }
        while (matcherError.find()) {
            errorCount = Integer.parseInt(matcherError.group(1));
        }
        System.out.println("Extracted Error preservation count: " + errorCount);
        System.out.println("Extracted All preservation count: " + allCount);
        driver.FindElementById(locatorReader.getobjectLocator("preservationDataTableClearFilter")).waitAndClick(30);
        return allCount - errorCount;
    }
}
