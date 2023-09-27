package legalhold.legalholdpagefactory.cases;

import automationLibrary.Driver;
import automationLibrary.Element;

import legalhold.setup.BaseModule;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import scala.xml.Elem;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.openqa.selenium.support.locators.RelativeLocator.with;

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

    public void selectDataSourceFromDropdown(String dataSourceName) {
//        Element dropdownDataSource = driver.FindElementByXPath(locatorReader.getobjectLocator("dropdownDataSource"));
//        wait.until(ExpectedConditions.elementToBeClickable(dropdownDataSource.getWebElement()));
//        dropdownDataSource.selectFromDropdown().selectByVisibleText(dataSourceName);

        WebElement dropdownDataSource = driver.getWebDriver().findElement(By.xpath(locatorReader.getobjectLocator("dropdownDataSource")));
        wait.until(ExpectedConditions.elementToBeClickable(dropdownDataSource));

        Select select = new Select(dropdownDataSource);
        List<WebElement> allOptions = select.getOptions();
        String matchedValue = null;
        for (WebElement option : allOptions) {
            if (option.getText().contains(dataSourceName)) {
                matchedValue = option.getAttribute("value");
                break;
            }
        }
        select.selectByValue(matchedValue);
    }

    public int addCustodianBySelectAll(String empId) throws InterruptedException {
//        String expectedPaginationText = "Showing 1 to 1 of 1 entries";
        driver.FindElementById(locatorReader.getobjectLocator("subTabCustodian")).waitAndClick(30);
        Element searchEmpIdAvailableCustodianTable = driver.FindElementByCssSelector(locatorReader.getobjectLocator("searchEmpIdAvailableCustodianTable"));
        wait.until(ExpectedConditions.elementToBeClickable(searchEmpIdAvailableCustodianTable.getWebElement()));
        searchEmpIdAvailableCustodianTable.SendKeys(empId);
        Thread.sleep(2000);
//        wait.until(ExpectedConditions.textToBe(By.id(locatorReader.getobjectLocator("paginationTextAvailableCustodianTable")), expectedPaginationText));

        driver.FindElementByCssSelector(locatorReader.getobjectLocator("selectAllAvailableCustodianTable")).waitAndClick(30);
        driver.FindElementById(locatorReader.getobjectLocator("btnAddCustodian")).waitAndClick(30);
        Thread.sleep(2000);

        String paginationTextSelectedCustodianTable = driver.FindElementById(locatorReader.getobjectLocator("paginationTextSelectedCustodianTable")).getText();
        Thread.sleep(2000);
        Pattern pattern = Pattern.compile("of (\\d+)");
        Matcher matcher = pattern.matcher(paginationTextSelectedCustodianTable);

        int count = 0;
        while (matcher.find()) {
            count = Integer.parseInt(matcher.group(1));
        }
//        wait.until(ExpectedConditions.textToBe(By.id(locatorReader.getobjectLocator("paginationTextSelectedCustodianTable")), expectedPaginationText));
        return count;
    }

    public int addTeamsBySelectAll(String teamName) throws InterruptedException {
//        String expectedPaginationText = "Showing 1 to 1 of 1 entries";
        driver.FindElementById(locatorReader.getobjectLocator("subTabTeams")).waitAndClick(30);
        Thread.sleep(5000);
        Element searchTeamNamesAvailableMailboxTable = driver.FindElementByCssSelector(locatorReader.getobjectLocator("searchTeamNamesAvailableMailboxTable"));
        wait.until(ExpectedConditions.elementToBeClickable(searchTeamNamesAvailableMailboxTable.getWebElement()));
        searchTeamNamesAvailableMailboxTable.SendKeys(teamName);
        Thread.sleep(3000);
//        wait.until(ExpectedConditions.textToBe(By.id(locatorReader.getobjectLocator("paginationTextAvailableTeamMailboxTable")), expectedPaginationText));

        driver.FindElementByCssSelector(locatorReader.getobjectLocator("selectAllAvailableTeamMailboxTable")).waitAndClick(30);
        driver.FindElementById(locatorReader.getobjectLocator("btnAddTeams")).waitAndClick(30);

        Thread.sleep(2000);

        String paginationTextSelectedTeamMailboxTable = driver.FindElementById(locatorReader.getobjectLocator("paginationTextSelectedTeamMailboxTable")).getText();
        Pattern pattern = Pattern.compile("of (\\d+)");
        Matcher matcher = pattern.matcher(paginationTextSelectedTeamMailboxTable);

        int count = 0;
        while (matcher.find()) {
            count = Integer.parseInt(matcher.group(1));
        }
        return count;
    }

    public int addSiteBySelectAll(String siteName) throws InterruptedException {
//        String expectedPaginationText = "Showing 1 to 1 of 1 entries";
        Thread.sleep(5000);
        Element searchSiteNameAvailableSiteTable = driver.FindElementByCssSelector(locatorReader.getobjectLocator("searchSiteNameAvailableSiteTable"));
        wait.until(ExpectedConditions.elementToBeClickable(searchSiteNameAvailableSiteTable.getWebElement()));
        searchSiteNameAvailableSiteTable.SendKeys(siteName);
        Thread.sleep(15000);
//        wait.until(ExpectedConditions.textToBe(By.id(locatorReader.getobjectLocator("paginationTextAvailableSiteTable")), expectedPaginationText));

        driver.FindElementByCssSelector(locatorReader.getobjectLocator("selectAllAvailableSiteTable")).waitAndClick(30);
        driver.FindElementById(locatorReader.getobjectLocator("btnAddSite")).waitAndClick(30);


        Thread.sleep(15000);
        String paginationTextSelectedSiteTable = driver.FindElementById(locatorReader.getobjectLocator("paginationTextSelectedSiteTable")).getText();
        Pattern pattern = Pattern.compile("of (\\d+)");
        Matcher matcher = pattern.matcher(paginationTextSelectedSiteTable);

        int count = 0;
        while (matcher.find()) {
            count = Integer.parseInt(matcher.group(1));
        }
        return count;
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
//        int previousCount = getTotalPreservationCustodianTeamCount();
        goToManageCustodianTeams();
        int addedCustodianTeam = addCustodianBySelectAll(empId);
        saveManagePreservationCustodianTeams();
        int afterCount = getTotalPreservationCustodianTeamCount();
        if (addedCustodianTeam == afterCount) {
            System.out.println("Count Matched");
        } else {
            throw new RuntimeException("Count did NOT matched");
        }
    }

    public void addPreservationCustodianAndTeams(String empId, String teamName) throws InterruptedException {
//        int previousCount = getTotalPreservationCustodianTeamCount();
        goToManageCustodianTeams();
        int addedCustodian = addCustodianBySelectAll(empId);
        int addedTeams = addTeamsBySelectAll(teamName);
        saveManagePreservationCustodianTeams();
        int afterCount = getTotalPreservationCustodianTeamCount();
        if (addedCustodian + addedTeams == afterCount) {
            System.out.println("Count Matched");
        } else {
            throw new RuntimeException("Count did NOT matched");
        }
    }

    public void addPreservationSite(String siteName) throws InterruptedException {
//        int previousCount = getTotalPreservationSiteCount();
        goToManageSite();
        int addedSite = addSiteBySelectAll(siteName);
        saveManageSite();
        int afterCount = getTotalPreservationSiteCount();
        if (addedSite == afterCount) {
            System.out.println("Count Matched");
        } else {
            throw new RuntimeException("Count did NOT matched");
        }
    }

    public void addPreservationTeams(String teamName) throws InterruptedException {
//        int previousCount = getTotalPreservationCustodianTeamCount();
        goToManageCustodianTeams();
        int addedTeams = addTeamsBySelectAll(teamName);
        saveManagePreservationCustodianTeams();
        int afterCount = getTotalPreservationCustodianTeamCount();
        if (addedTeams == afterCount) {
            System.out.println("Count Matched");
        } else {
            throw new RuntimeException("Count did NOT matched");
        }
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

    public int getTotalPreservationCustodianTeamCount() {
        WebElement element = driver.getWebDriver().findElements(By.className("col-lg-10")).get(0);
        wait.until(ExpectedConditions.visibilityOf(element));
        String divText = driver.getWebDriver().findElements(By.className("col-lg-10")).get(0).getText();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(divText);
        int count = 0;
        while (matcher.find()) {
            count = Integer.parseInt(matcher.group());
        }
        System.out.println("Total Preservation Custodian and Teams count is: " + count);
        return count;
    }

    public int getTotalPreservationSiteCount() {
        WebElement element = driver.getWebDriver().findElements(By.className("col-lg-10")).get(1);
        wait.until(ExpectedConditions.visibilityOf(element));
        String divText = driver.getWebDriver().findElements(By.className("col-lg-10")).get(1).getText();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(divText);
        int count = 0;
        while (matcher.find()) {
            count = Integer.parseInt(matcher.group());
        }
        System.out.println("Total Preservation Site count is: " + count);
        return count;
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

    public void goToEditPreservationHold(String holdName) throws InterruptedException {
        int holdRow = findPreservationRow(holdName);
        Element btnEditPreservation = driver.FindElementByXPath("//table[@id='preservationHold_table_uniqueId']//tbody//tr[" + holdRow + "]//td[6]//div[1]//a[1]//img[1]");
        wait.until(ExpectedConditions.elementToBeClickable(btnEditPreservation.getWebElement()));
        btnEditPreservation.waitAndClick(30);
        Thread.sleep(2000);
        driver.waitForPageToBeReady();
        if (driver.FindElementByXPath(locatorReader.getobjectLocator("dropdownDataSource")).Enabled()) {
            throw new RuntimeException("Data Source dropdown is NOT disabled!");
        }
    }


    public void groupReleasePreservationHold(String holdName) throws InterruptedException {
        int holdRow = findPreservationRow(holdName);
        Element btnGroupReleasePreservation = driver.FindElementByXPath("//table[@id='preservationHold_table_uniqueId']//tbody//tr[" + holdRow + "]//td[6]//div[1]//a[2]//img[1]");
        wait.until(ExpectedConditions.elementToBeClickable(btnGroupReleasePreservation.getWebElement()));
        btnGroupReleasePreservation.waitAndClick(30);
        driver.FindElementById("pHold-release-modal-ok").waitAndClick(30);
        Thread.sleep(2500);
        driver.waitForPageToBeReady();
    }

    public int findPreservationRow(String holdName) {
        int holdRow = 0;
        List<WebElement> rowCount = driver.getWebDriver().findElements(By.xpath("//table[@id='preservationHold_table_uniqueId']//tbody//tr//td[1]"));

        for (int i = 0; i < rowCount.size(); i++) {
            if (rowCount.get(i).getText().equalsIgnoreCase(holdName)) {
                holdRow = i;
                break;
            }
        }
        if (holdRow == 0) System.out.println("Preservation Hold was NOT found");
        return holdRow + 1;
    }

    public void isPreservationHoldActive(String holdName) throws InterruptedException {
        boolean flag = false;
        int holdRow = findPreservationRow(holdName);
        WebElement statusCell = driver.getWebDriver().findElement(By.xpath("//table[@id='preservationHold_table_uniqueId']//tbody//tr[" + holdRow + "]//td[4]"));
        wait.until(ExpectedConditions.visibilityOf(statusCell));
        String status = statusCell.getText();
        int iterator = 0;
        while (!status.equalsIgnoreCase("Active") && status.equalsIgnoreCase("Pending Hold")) {
            driver.getWebDriver().navigate().refresh();
            Thread.sleep(30000);
            driver.waitForPageToBeReady();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='preservationHold_table_uniqueId']//tbody//tr[" + holdRow + "]//td[4]")));
            status = driver.FindElementByXPath("//table[@id='preservationHold_table_uniqueId']//tbody//tr[" + holdRow + "]//td[4]").getText();
            iterator++;
            if (status.equalsIgnoreCase("Active")) {
                flag = true;
                break;
            }
            if (iterator == 15) break;
        }
        System.out.println("The status of " + holdName + " is: " + status);
        if (flag == false) {
            throw new RuntimeException("The preservation Hold: " + holdName + ", is NOT in Active Status");
        } else {
            System.out.println("The preservation Hold: " + holdName + ", is in Active Status");
        }
    }

    public void isPreservationHoldPartiallyActive(String holdName) throws InterruptedException {
        boolean flag = false;
        int holdRow = findPreservationRow(holdName);
        WebElement statusCell = driver.getWebDriver().findElement(By.xpath("//table[@id='preservationHold_table_uniqueId']//tbody//tr[" + holdRow + "]//td[4]"));
        wait.until(ExpectedConditions.visibilityOf(statusCell));
        String status = statusCell.getText();
        int iterator = 0;
        while (!status.equalsIgnoreCase("Partially Active") && status.equalsIgnoreCase("Pending Hold")) {
            driver.getWebDriver().navigate().refresh();
            Thread.sleep(30000);
            driver.waitForPageToBeReady();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='preservationHold_table_uniqueId']//tbody//tr[" + holdRow + "]//td[4]")));
            status = driver.FindElementByXPath("//table[@id='preservationHold_table_uniqueId']//tbody//tr[" + holdRow + "]//td[4]").getText();
            iterator++;
            if (status.equalsIgnoreCase("Partially Active")) {
                flag = true;
                break;
            }
            if (iterator == 15) break;
        }
        System.out.println("The status of " + holdName + " is: " + status);
        if (flag == false) {
            throw new RuntimeException("The preservation Hold: " + holdName + ", is NOT in Partially Active Status");
        } else {
            System.out.println("The preservation Hold: " + holdName + ", is in Partially Active Status");
        }
    }

    public void isPreservationHoldError(String holdName) throws InterruptedException {
        boolean flag = false;
        int holdRow = findPreservationRow(holdName);
        WebElement statusCell = driver.getWebDriver().findElement(By.xpath("//table[@id='preservationHold_table_uniqueId']//tbody//tr[" + holdRow + "]//td[4]"));
        wait.until(ExpectedConditions.visibilityOf(statusCell));
        String status = statusCell.getText();
        int iterator = 0;
        while (!status.equalsIgnoreCase("Error") && status.equalsIgnoreCase("Pending Hold")) {
            driver.getWebDriver().navigate().refresh();
            Thread.sleep(30000);
            driver.waitForPageToBeReady();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='preservationHold_table_uniqueId']//tbody//tr[" + holdRow + "]//td[4]")));
            status = driver.FindElementByXPath("//table[@id='preservationHold_table_uniqueId']//tbody//tr[" + holdRow + "]//td[4]").getText();
            iterator++;
            if (status.equalsIgnoreCase("Error")) {
                flag = true;
                break;
            }
            if (iterator == 15) break;
        }
        System.out.println("The status of " + holdName + " is: " + status);
        if (flag == false) {
            throw new RuntimeException("The preservation Hold: " + holdName + ", is NOT in Error Status");
        } else {
            System.out.println("The preservation Hold: " + holdName + ", is in Error Status");
        }
    }

    public void isPreservationHoldReleased(String holdName) throws InterruptedException {
        boolean flag = false;
        int holdRow = findPreservationRow(holdName);
        WebElement statusCell = driver.getWebDriver().findElement(By.xpath("//table[@id='preservationHold_table_uniqueId']//tbody//tr[" + holdRow + "]//td[4]"));
        wait.until(ExpectedConditions.visibilityOf(statusCell));
        String status = statusCell.getText();
        int iterator = 0;
        while (!status.equalsIgnoreCase("Released") && status.equalsIgnoreCase("Pending Release")) {
            driver.getWebDriver().navigate().refresh();
            Thread.sleep(30000);
            driver.waitForPageToBeReady();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='preservationHold_table_uniqueId']//tbody//tr[" + holdRow + "]//td[4]")));
            status = driver.FindElementByXPath("//table[@id='preservationHold_table_uniqueId']//tbody//tr[" + holdRow + "]//td[4]").getText();
            iterator++;
            if (status.equalsIgnoreCase("Released")) {
                flag = true;
                break;
            }
            if (iterator == 15) break;
        }
        System.out.println("The status of " + holdName + " is: " + status);
        if (flag == false) {
            throw new RuntimeException("The preservation Hold: " + holdName + ", is NOT in Released Status");
        } else {
            System.out.println("The preservation Hold: " + holdName + ", is in Released Status");
        }
    }

    public void searchPreservationCustodianDataTable(String holdName, String empId) {
        Element searchHoldName = driver.FindElementByCssSelector(locatorReader.getobjectLocator("searchHoldName"));
        wait.until(ExpectedConditions.elementToBeClickable(searchHoldName.getWebElement()));
        searchHoldName.SendKeys(holdName);

        Element dropdownLocation = driver.FindElementByCssSelector(locatorReader.getobjectLocator("dropdownLocation"));
        wait.until(ExpectedConditions.elementToBeClickable(dropdownLocation.getWebElement()));
        dropdownLocation.selectFromDropdown().selectByValue("ExchangeMailbox");
        Element searchEmployeeId = driver.FindElementByCssSelector(locatorReader.getobjectLocator("searchEmployeeId"));
        wait.until(ExpectedConditions.elementToBeClickable(searchEmployeeId.getWebElement()));
        searchEmployeeId.SendKeys(empId);
    }

    public void releasePreservationCustodianFromDataTable(String holdName, String empId) {
        searchPreservationCustodianDataTable(holdName, empId);

        String paginationTextPreservationDataTable = driver.FindElementById(locatorReader.getobjectLocator("paginationTextPreservationDataTable")).getText();
        Pattern pattern = Pattern.compile("of (\\d+)");
        Matcher matcher = pattern.matcher(paginationTextPreservationDataTable);

        int count = 0;
        while (matcher.find()) {
            count = Integer.parseInt(matcher.group(1));
        }

        if (count > 1) {
            Element selectAllPreservationDataTable = driver.FindElementByCssSelector(locatorReader.getobjectLocator("selectAllPreservationDataTable"));
            wait.until(ExpectedConditions.elementToBeClickable(selectAllPreservationDataTable.getWebElement()));
            selectAllPreservationDataTable.waitAndClick(30);

            Element btnBulkRelease = driver.FindElementById(locatorReader.getobjectLocator("btnBulkRelease"));
            btnBulkRelease.waitAndClick(30);
            driver.FindElementById(locatorReader.getobjectLocator("okBtnBulkReleaseModal")).waitAndClick(30);

            Element toastMessageBulkRelease = driver.FindElementByXPath(locatorReader.getobjectLocator("toastMessageBulkRelease"));
            wait.until(ExpectedConditions.elementToBeClickable(toastMessageBulkRelease.getWebElement()));
            String successToastMessageBulkRelease = toastMessageBulkRelease.getText();
            Assert.assertTrue(successToastMessageBulkRelease.contains("Preservation bulk release"));
        } else {
            Element dataTableOptionMenu = driver.FindElementByCssSelector(locatorReader.getobjectLocator("dataTableOptionMenu"));
            wait.until(ExpectedConditions.elementToBeClickable(dataTableOptionMenu.getWebElement()));
            dataTableOptionMenu.waitAndClick(30);

            driver.FindElementByXPath(locatorReader.getobjectLocator("btnSingleRelease")).waitAndClick(30);
            driver.FindElementById(locatorReader.getobjectLocator("okBtnSingleRelease")).waitAndClick(30);

            Element toastMessageSingleRelease = driver.FindElementByXPath(locatorReader.getobjectLocator("toastMessageSingleRelease"));
            wait.until(ExpectedConditions.elementToBeClickable(toastMessageSingleRelease.getWebElement()));
            String successToastMessageBulkRelease = toastMessageSingleRelease.getText();
            Assert.assertTrue(successToastMessageBulkRelease.contains("Preservation Single Release"));
        }
    }


}
