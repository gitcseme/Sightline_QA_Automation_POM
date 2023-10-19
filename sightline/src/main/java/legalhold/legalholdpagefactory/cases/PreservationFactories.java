package legalhold.legalholdpagefactory.cases;

import automationLibrary.Driver;
import automationLibrary.Element;

import automationLibrary.ElementCollection;
import legalhold.setup.BaseModule;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
        Thread.sleep(5000);
        Element searchSiteNameAvailableSiteTable = driver.FindElementByCssSelector(locatorReader.getobjectLocator("searchSiteNameAvailableSiteTable"));
        wait.until(ExpectedConditions.elementToBeClickable(searchSiteNameAvailableSiteTable.getWebElement()));
        searchSiteNameAvailableSiteTable.SendKeys(siteName);
        Thread.sleep(3000);
//        wait.until(ExpectedConditions.textToBe(By.id(locatorReader.getobjectLocator("paginationTextAvailableSiteTable")), expectedPaginationText));

        driver.FindElementByCssSelector(locatorReader.getobjectLocator("selectAllAvailableSiteTable")).waitAndClick(30);
        driver.FindElementById(locatorReader.getobjectLocator("btnAddSite")).waitAndClick(30);


        Thread.sleep(3000);
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

    public int getTotalPreservationCountExceptReleased() {
        String paginationTextPreservationDataTable = driver.FindElementById(locatorReader.getobjectLocator("paginationTextPreservationDataTable")).getText();
        Element dropdownStatus = driver.FindElementByCssSelector(locatorReader.getobjectLocator("dropdownStatus"));
        dropdownStatus.selectFromDropdown().selectByValue("Released");
        String paginationTextPreservationDataTableAfterFiltering = driver.FindElementById(locatorReader.getobjectLocator("paginationTextPreservationDataTable")).getText();

        Pattern pattern = Pattern.compile("of (\\d+)");

        Matcher matcherAll = pattern.matcher(paginationTextPreservationDataTable);
        Matcher matcherError = pattern.matcher(paginationTextPreservationDataTableAfterFiltering);

        int releaseCount = 0;
        int allCount = 0;
        while (matcherAll.find()) {
            allCount = Integer.parseInt(matcherAll.group(1));
        }
        while (matcherError.find()) {
            releaseCount = Integer.parseInt(matcherError.group(1));
        }
        System.out.println("Extracted Error preservation count: " + releaseCount);
        System.out.println("Extracted All preservation count: " + allCount);
        driver.FindElementById(locatorReader.getobjectLocator("preservationDataTableClearFilter")).waitAndClick(30);
        return allCount - releaseCount;
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
        Thread.sleep(2000);
        WebElement statusCell = driver.getWebDriver().findElement(By.xpath("//table[@id='preservationHold_table_uniqueId']//tbody//tr[" + holdRow + "]//td[4]"));
        wait.until(ExpectedConditions.visibilityOf(statusCell));
        String status = statusCell.getText();

        if (status.equalsIgnoreCase("Active") || status.equalsIgnoreCase("Error") ||
                status.equalsIgnoreCase("Partially Active")) {
            wait.until(ExpectedConditions.elementToBeClickable(btnGroupReleasePreservation.getWebElement()));
            btnGroupReleasePreservation.waitAndClick(30);
            driver.FindElementById("pHold-release-modal-ok").waitAndClick(30);
            Thread.sleep(2500);
            System.out.println("Preservation Hold release request sent successfully");
            driver.waitForPageToBeReady();
        } else {
            System.out.println("Preservation Hold: " + holdName + ", is NOT in Active/Partially Active status for system to Release");
        }
    }


    public void deletePreservationHold(String holdName) throws InterruptedException {
        int holdRow = findPreservationRow(holdName);
        Element btnDeletePreservation = driver.FindElementByXPath("//table[@id='preservationHold_table_uniqueId']//tbody//tr[" + holdRow + "]//td[6]//div[1]//a[3]//img[1]");
        Thread.sleep(2000);
        WebElement statusCell = driver.getWebDriver().findElement(By.xpath("//table[@id='preservationHold_table_uniqueId']//tbody//tr[" + holdRow + "]//td[4]"));
        wait.until(ExpectedConditions.visibilityOf(statusCell));
        String status = statusCell.getText();

        if (status.equalsIgnoreCase("Active") || status.equalsIgnoreCase("Error") ||
                status.equalsIgnoreCase("Partially Active") || status.equalsIgnoreCase("Released")) {
            wait.until(ExpectedConditions.elementToBeClickable(btnDeletePreservation.getWebElement()));
            btnDeletePreservation.waitAndClick(30);
            driver.FindElementById("pHold-delete-modal-ok").waitAndClick(30);
            System.out.println("Preservation Hold Delete request Sent successfully");
            driver.waitForPageToBeReady();
        } else {
            System.out.println("Preservation Hold: " + holdName + ", is NOT in Active/Partially Active status for system to Delete");
        }
    }

    public void isPreservationHoldDeleted(String holdName) throws InterruptedException {
        ElementCollection holdList = driver.FindElementsByXPath("//table[@id='preservationHold_table_uniqueId']//tbody//tr//td[1]");
        List<String> holdNameList = new ArrayList<>();
        boolean flag = false;
        int count = 0, iterator = 0;
        for (int i = 0; i < holdList.size(); i++) {
            holdNameList.add(holdList.getElementByIndex(i).getText());
        }

        for (String s : holdNameList) {
            if (!s.equalsIgnoreCase(holdName) && s.equalsIgnoreCase("No data available in table")) {
                System.out.println("Preservation Hold: " + holdName + " is already Deleted successfully");
//                count = holdNameList.size();
                return;
            } else if (!s.equalsIgnoreCase(holdName)) count++;
        }

        if (count == holdNameList.size()) {
            flag = true;
            System.out.println("Preservation Hold: " + holdName + " is already Deleted successfully");
        } else {
            holdNameList.clear();
        }
        while (!flag) {
            int countAfterRefresh = 0;
            driver.getWebDriver().navigate().refresh();
            Thread.sleep(15000);
            driver.waitForPageToBeReady();
            holdList = driver.FindElementsByXPath("//table[@id='preservationHold_table_uniqueId']//tbody//tr//td[1]");
//            List<String> holdNameListAfterRefresh = new ArrayList<>();
            for (int i = 0; i < holdList.size(); i++) {
                var holdNameFromCellAfterRefresh = holdList.getElementByIndex(i).getText();
                System.out.println("Hold Name After Refresh: " + holdNameFromCellAfterRefresh);
                if (holdNameFromCellAfterRefresh.equalsIgnoreCase("No data available in table")) {
                    holdNameList.add(holdNameFromCellAfterRefresh);
                    break;
                } else {
                    holdNameList.add(holdNameFromCellAfterRefresh);
                }
            }
            for (String s : holdNameList) {
                if (!s.equalsIgnoreCase(holdName)) countAfterRefresh++;
            }
            if (countAfterRefresh == holdNameList.size()) {
                flag = true;
                System.out.println("Preservation Hold: " + holdName + " is Deleted successfully");
                break;
            } else {
                holdNameList.clear();
                iterator++;
                if (iterator == 20) {
                    throw new RuntimeException("Preservation Hold: " + holdName + " Delete unsuccessful within given timeline");
                }
            }
        }
    }


    public int findPreservationRow(String holdName) {
        int holdRow = 0;
        List<WebElement> rowCount = driver.getWebDriver().findElements(By.xpath("//table[@id='preservationHold_table_uniqueId']//tbody//tr//td[1]"));

        for (int i = 0; i < rowCount.size(); i++) {
            if (rowCount.get(i).getText().equalsIgnoreCase(holdName)) {
                holdRow = i + 1;
                break;
            }
        }
        if (holdRow == 0) {
            throw new RuntimeException("Preservation Hold was NOT found");
        } else {
            return holdRow;
        }
    }

    public void isPreservationHoldActive(String holdName) throws InterruptedException {
        int holdRow = findPreservationRow(holdName);
        WebElement statusCell = driver.getWebDriver().findElement(By.xpath("//table[@id='preservationHold_table_uniqueId']//tbody//tr[" + holdRow + "]//td[4]"));
        wait.until(ExpectedConditions.visibilityOf(statusCell));
        String status = statusCell.getText();
        int iterator = 0;
        while (status.equalsIgnoreCase("Pending Hold")) {
            driver.getWebDriver().navigate().refresh();
            Thread.sleep(30000);
            driver.waitForPageToBeReady();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='preservationHold_table_uniqueId']//tbody//tr[" + holdRow + "]//td[4]")));
            status = driver.FindElementByXPath("//table[@id='preservationHold_table_uniqueId']//tbody//tr[" + holdRow + "]//td[4]").getText();
            iterator++;
            if (status.equalsIgnoreCase("Active")) {
                break;
            }
            if (iterator == 15) break;
        }
        System.out.println("The status of " + holdName + " is: " + status);
        if (!status.equalsIgnoreCase("Active")) {
            throw new RuntimeException("The preservation Hold: " + holdName + ", is NOT in Active Status");
        } else {
            System.out.println("The preservation Hold: " + holdName + ", is in Active Status");
        }
    }

    public void isPreservationHoldPartiallyActive(String holdName) throws InterruptedException {
        int holdRow = findPreservationRow(holdName);
        WebElement statusCell = driver.getWebDriver().findElement(By.xpath("//table[@id='preservationHold_table_uniqueId']//tbody//tr[" + holdRow + "]//td[4]"));
        wait.until(ExpectedConditions.visibilityOf(statusCell));
        String status = statusCell.getText();
        int iterator = 0;
        while (status.equalsIgnoreCase("Pending Hold")) {
            driver.getWebDriver().navigate().refresh();
            Thread.sleep(30000);
            driver.waitForPageToBeReady();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='preservationHold_table_uniqueId']//tbody//tr[" + holdRow + "]//td[4]")));
            status = driver.FindElementByXPath("//table[@id='preservationHold_table_uniqueId']//tbody//tr[" + holdRow + "]//td[4]").getText();
            iterator++;
            if (status.equalsIgnoreCase("Partially Active")) {
                break;
            }
            if (iterator == 15) break;
        }
        System.out.println("The status of " + holdName + " is: " + status);
        if (!status.equalsIgnoreCase("Partially Active")) {
            throw new RuntimeException("The preservation Hold: " + holdName + ", is NOT in Partially Active Status");
        } else {
            System.out.println("The preservation Hold: " + holdName + ", is in Partially Active Status");
        }
    }

    public void isPreservationHoldError(String holdName) throws InterruptedException {

        int holdRow = findPreservationRow(holdName);
        WebElement statusCell = driver.getWebDriver().findElement(By.xpath("//table[@id='preservationHold_table_uniqueId']//tbody//tr[" + holdRow + "]//td[4]"));
        wait.until(ExpectedConditions.visibilityOf(statusCell));
        String status = statusCell.getText();
        int iterator = 0;
        while (status.equalsIgnoreCase("Pending Hold")) {
            driver.getWebDriver().navigate().refresh();
            Thread.sleep(30000);
            driver.waitForPageToBeReady();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='preservationHold_table_uniqueId']//tbody//tr[" + holdRow + "]//td[4]")));
            status = driver.FindElementByXPath("//table[@id='preservationHold_table_uniqueId']//tbody//tr[" + holdRow + "]//td[4]").getText();
            iterator++;
            if (status.equalsIgnoreCase("Error")) {
                break;
            }
            if (iterator == 30) break;
        }
        System.out.println("The status of " + holdName + " is: " + status);
        if (!status.equalsIgnoreCase("Error")) {
            throw new RuntimeException("The preservation Hold: " + holdName + ", is NOT in Error Status");
        } else {
            System.out.println("The preservation Hold: " + holdName + ", is in Error Status");
        }
    }

    public void isPreservationHoldReleased(String holdName) throws InterruptedException {
        int holdRow = findPreservationRow(holdName);
        WebElement statusCell = driver.getWebDriver().findElement(By.xpath("//table[@id='preservationHold_table_uniqueId']//tbody//tr[" + holdRow + "]//td[4]"));
        wait.until(ExpectedConditions.visibilityOf(statusCell));
        String status = statusCell.getText();
        int iterator = 0;
        while (status.equalsIgnoreCase("Pending Release")) {
            driver.getWebDriver().navigate().refresh();
            Thread.sleep(30000);
            driver.waitForPageToBeReady();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='preservationHold_table_uniqueId']//tbody//tr[" + holdRow + "]//td[4]")));
            status = driver.FindElementByXPath("//table[@id='preservationHold_table_uniqueId']//tbody//tr[" + holdRow + "]//td[4]").getText();
            iterator++;
            if (status.equalsIgnoreCase("Released")) {
                break;
            }
            if (iterator == 15) break;
        }
        System.out.println("The status of " + holdName + " is: " + status);
        if (!status.equalsIgnoreCase("Released")) {
            throw new RuntimeException("The preservation Hold: " + holdName + ", is NOT in Released Status");
        } else {
            System.out.println("The preservation Hold: " + holdName + ", is in Released Status");
        }
    }

    public void searchPreservationCustodianDataTable(String holdName, String empId) throws InterruptedException {
        driver.scrollingToBottomofAPage();
        getColumnIndexFromDataTable("Preservation Hold Name");
        Element searchHoldName = driver.FindElementByCssSelector(locatorReader.getobjectLocator("searchHoldName"));
        wait.until(ExpectedConditions.elementToBeClickable(searchHoldName.getWebElement()));
        searchHoldName.Clear();
        searchHoldName.SendKeys(holdName);

        getColumnIndexFromDataTable("Location");
        Element dropdownLocation = driver.FindElementByCssSelector(locatorReader.getobjectLocator("dropdownLocation"));
        wait.until(ExpectedConditions.elementToBeClickable(dropdownLocation.getWebElement()));
        dropdownLocation.selectFromDropdown().selectByValue("ExchangeMailbox");

        getColumnIndexFromDataTable("Employee id");
        Element searchEmployeeId = driver.FindElementByCssSelector(locatorReader.getobjectLocator("searchEmployeeId"));
        wait.until(ExpectedConditions.elementToBeClickable(searchEmployeeId.getWebElement()));
        searchEmployeeId.Clear();
        searchEmployeeId.SendKeys(empId);
        Thread.sleep(3000);
    }


    public void searchPreservationSiteDataTable(String holdName, String siteUrl) throws InterruptedException {
        driver.scrollingToBottomofAPage();
        getColumnIndexFromDataTable("Preservation Hold Name");
        Element searchHoldName = driver.FindElementByCssSelector(locatorReader.getobjectLocator("searchHoldName"));
        wait.until(ExpectedConditions.elementToBeClickable(searchHoldName.getWebElement()));
        searchHoldName.Clear();
        searchHoldName.SendKeys(holdName);

        getColumnIndexFromDataTable("Location");
        Element dropdownLocation = driver.FindElementByCssSelector(locatorReader.getobjectLocator("dropdownLocation"));
        wait.until(ExpectedConditions.elementToBeClickable(dropdownLocation.getWebElement()));
        dropdownLocation.selectFromDropdown().selectByValue("SharepointSite");

        getColumnIndexFromDataTable("Name (Custodian, Site, Folder)");
        Element searchName = driver.FindElementByCssSelector(locatorReader.getobjectLocator("searchName"));
        wait.until(ExpectedConditions.elementToBeClickable(searchName.getWebElement()));
        searchName.Clear();
        searchName.SendKeys(siteUrl);
        Thread.sleep(3000);
    }

    public void searchPreservationTeamsDataTable(String holdName, String teamName) throws InterruptedException {
        driver.scrollingToBottomofAPage();
        getColumnIndexFromDataTable("Preservation Hold Name");
        Element searchHoldName = driver.FindElementByCssSelector(locatorReader.getobjectLocator("searchHoldName"));
        wait.until(ExpectedConditions.elementToBeClickable(searchHoldName.getWebElement()));
        searchHoldName.Clear();
        searchHoldName.SendKeys(holdName);

        getColumnIndexFromDataTable("Location");
        Element dropdownLocation = driver.FindElementByCssSelector(locatorReader.getobjectLocator("dropdownLocation"));
        wait.until(ExpectedConditions.elementToBeClickable(dropdownLocation.getWebElement()));
        dropdownLocation.selectFromDropdown().selectByValue("TeamMailbox");

        getColumnIndexFromDataTable("Name (Custodian, Site, Folder)");
        Element searchName = driver.FindElementByCssSelector(locatorReader.getobjectLocator("searchName"));
        wait.until(ExpectedConditions.elementToBeClickable(searchName.getWebElement()));
        searchName.Clear();
        searchName.SendKeys(teamName);
        Thread.sleep(3000);
    }


    public int getDataTableCount() {
        String paginationTextPreservationDataTable = driver.FindElementById(locatorReader.getobjectLocator("paginationTextPreservationDataTable")).getText();
        Pattern pattern = Pattern.compile("of (\\d+)");
        Matcher matcher = pattern.matcher(paginationTextPreservationDataTable);

        int count = 0;
        while (matcher.find()) {
            count = Integer.parseInt(matcher.group(1));
        }
        return count;
    }

    public void releasePreservationCustodianFromDataTable(String holdName, String empId) throws InterruptedException {
        searchPreservationCustodianDataTable(holdName, empId);
        int count = getDataTableCount();

        if (count > 1) {
            int statusIndex = getColumnIndexFromDataTable("Status");
            boolean flag = false;
            int c = 0;
            List<String> statusList = new ArrayList<>();
            for (int i = 1; i <= count; i++) {
                statusList.add(driver.FindElementByXPath("//table[@id='id-Preservation']/tbody/tr[" + i + "]/td[" + statusIndex + "]").getText());
            }
            for (String value : statusList) {
                if (value.equalsIgnoreCase("Released")) {
                    c++;
                    if (c == count) flag = true;
                }
            }
            Element selectAllPreservationDataTable = driver.FindElementByCssSelector(locatorReader.getobjectLocator("selectAllPreservationDataTable"));
            wait.until(ExpectedConditions.elementToBeClickable(selectAllPreservationDataTable.getWebElement()));
            selectAllPreservationDataTable.waitAndClick(30);
            Element btnBulkRelease = driver.FindElementById(locatorReader.getobjectLocator("btnBulkRelease"));
            Thread.sleep(2000);
            if (!flag && btnBulkRelease.Enabled()) {
//                Element btnBulkRelease = driver.FindElementById(locatorReader.getobjectLocator("btnBulkRelease"));
                btnBulkRelease.waitAndClick(30);
                driver.FindElementById(locatorReader.getobjectLocator("okBtnBulkReleaseModal")).waitAndClick(30);

                Element toastMessageBulkRelease = driver.FindElementByXPath(locatorReader.getobjectLocator("toastMessageBulkRelease"));
                wait.until(ExpectedConditions.elementToBeClickable(toastMessageBulkRelease.getWebElement()));
                String successToastMessageBulkRelease = toastMessageBulkRelease.getText();
                Assert.assertTrue(successToastMessageBulkRelease.contains("Preservation bulk release"));
            } else {
                System.out.println("No Custodian is in Active status for system to Release");
            }
        } else if (count == 1) {
            int statusIndex = getColumnIndexFromDataTable("Status");
            var status = driver.FindElementByXPath("//table[@id='id-Preservation']/tbody/tr[1]/td[" + statusIndex + "]").getText();
            Element dataTableOptionMenu = driver.FindElementByCssSelector(locatorReader.getobjectLocator("dataTableOptionMenu"));
            if (!status.equalsIgnoreCase("Released") && dataTableOptionMenu.isDisplayed()) {
                wait.until(ExpectedConditions.elementToBeClickable(dataTableOptionMenu.getWebElement()));
                dataTableOptionMenu.waitAndClick(30);

                driver.FindElementByXPath(locatorReader.getobjectLocator("btnSingleRelease")).waitAndClick(30);
                driver.FindElementById(locatorReader.getobjectLocator("okBtnSingleRelease")).waitAndClick(30);

                Element toastMessageSingleRelease = driver.FindElementByXPath(locatorReader.getobjectLocator("toastMessageSingleRelease"));
                wait.until(ExpectedConditions.elementToBeClickable(toastMessageSingleRelease.getWebElement()));
                String successToastMessageBulkRelease = toastMessageSingleRelease.getText();
                Assert.assertTrue(successToastMessageBulkRelease.contains("Preservation Single Release"));
            } else {
                System.out.println("Filtered custodian is not Active for system to Release");
            }
        } else {
            throw new RuntimeException("No custodian is present for system to release after filtering");
        }
    }


    public void verifyIfCustodianIsReleased(String holdName, String empId) throws InterruptedException {

        searchPreservationCustodianDataTable(holdName, empId);
        int count = getDataTableCount();
        int iterator = 0;
        int pendingRelease = 0;
        if (count > 1) {
            int statusIndex = getColumnIndexFromDataTable("Status");
            boolean flag = false;
            int c = 0;
            List<String> statusList = new ArrayList<>();
            for (int i = 1; i <= count; i++) {
                statusList.add(driver.FindElementByXPath("//table[@id='id-Preservation']/tbody/tr[" + i + "]/td[" + statusIndex + "]").getText());
            }
            for (String value : statusList) {
                if (value.equalsIgnoreCase("Released")) {
                    c++;
                    if (c == count) flag = true;
                } else if (!value.equalsIgnoreCase("Pending Release")) {
                    pendingRelease++;
                }
            }
            while (!flag && pendingRelease != count) {
                statusList.clear();
                driver.getWebDriver().navigate().refresh();
                Thread.sleep(12000);
                searchPreservationCustodianDataTable(holdName, empId);
//                Thread.sleep(3000);
                driver.waitForPageToBeReady();
                for (int i = 1; i <= count; i++) {
                    statusList.add(driver.FindElementByXPath("//table[@id='id-Preservation']/tbody/tr[" + i + "]/td[" + statusIndex + "]").getText());
                }
                for (String s : statusList) {
                    if (s.equalsIgnoreCase("Released")) {
                        c++;
                        if (c == count) {
                            flag = true;
                            break;
                        }
                    }
                }
                iterator++;
                if (iterator == 15) break;
            }
            if (flag) {
                System.out.println("All Custodians from " + holdName + " are Released");
            } else {
                throw new RuntimeException("All Custodians from " + holdName + " are NOT Released");
            }

        } else if (count == 1) {
            int statusIndex = getColumnIndexFromDataTable("Status");
            var status = driver.FindElementByXPath("//table[@id='id-Preservation']/tbody/tr[1]/td[" + statusIndex + "]").getText();
            while (status.equalsIgnoreCase("Pending Release")) {
                driver.getWebDriver().navigate().refresh();
                Thread.sleep(12000);
                searchPreservationCustodianDataTable(holdName, empId);
//                Thread.sleep(3000);
                driver.waitForPageToBeReady();
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='id-Preservation']/tbody/tr[1]/td[" + statusIndex + "]")));
                status = driver.FindElementByXPath("//table[@id='id-Preservation']/tbody/tr[1]/td[" + statusIndex + "]").getText();
                if (status.equalsIgnoreCase("Released")) {
                    break;
                }
                iterator++;
                if (iterator == 15) break;
            }
            System.out.println("The custodian(s) of " + holdName + " are in: " + status + " status");
            if (!status.equalsIgnoreCase("Released")) {
                throw new RuntimeException("The custodian(s) of " + holdName + " are in NOT Released Status");
            } else {
                System.out.println("The custodian(s) of " + holdName + " are in Released Status");
            }
        } else {
            throw new RuntimeException("No available custodian(s) after filtering");
        }
    }


    public void releasePreservationSiteFromDataTable(String holdName, String siteUrl) throws InterruptedException {
        searchPreservationSiteDataTable(holdName, siteUrl);
        int count = getDataTableCount();

        if (count > 1) {
            int statusIndex = getColumnIndexFromDataTable("Status");
            boolean flag = false;
            int c = 0;
            List<String> statusList = new ArrayList<>();
            for (int i = 1; i <= count; i++) {
                statusList.add(driver.FindElementByXPath("//table[@id='id-Preservation']/tbody/tr[" + i + "]/td[" + statusIndex + "]").getText());
            }
            for (String value : statusList) {
                if (value.equalsIgnoreCase("Released")) {
                    c++;
                    if (c == count) flag = true;
                }
            }
            Element selectAllPreservationDataTable = driver.FindElementByCssSelector(locatorReader.getobjectLocator("selectAllPreservationDataTable"));
            wait.until(ExpectedConditions.elementToBeClickable(selectAllPreservationDataTable.getWebElement()));
            selectAllPreservationDataTable.waitAndClick(30);
            Element btnBulkRelease = driver.FindElementById(locatorReader.getobjectLocator("btnBulkRelease"));
            Thread.sleep(2000);
            if (!flag && btnBulkRelease.Enabled()) {
//                Element btnBulkRelease = driver.FindElementById(locatorReader.getobjectLocator("btnBulkRelease"));
                btnBulkRelease.waitAndClick(30);
                driver.FindElementById(locatorReader.getobjectLocator("okBtnBulkReleaseModal")).waitAndClick(30);

                Element toastMessageBulkRelease = driver.FindElementByXPath(locatorReader.getobjectLocator("toastMessageBulkRelease"));
                wait.until(ExpectedConditions.elementToBeClickable(toastMessageBulkRelease.getWebElement()));
                String successToastMessageBulkRelease = toastMessageBulkRelease.getText();
                Assert.assertTrue(successToastMessageBulkRelease.contains("Preservation bulk release"));
            } else {
                System.out.println("No Site is in Active status for system to Release");
            }
        } else if (count == 1) {
            int statusIndex = getColumnIndexFromDataTable("Status");
            var status = driver.FindElementByXPath("//table[@id='id-Preservation']/tbody/tr[1]/td[" + statusIndex + "]").getText();
            Element dataTableOptionMenu = driver.FindElementByCssSelector(locatorReader.getobjectLocator("dataTableOptionMenu"));
            if (!status.equalsIgnoreCase("Released") && dataTableOptionMenu.isDisplayed()) {
                wait.until(ExpectedConditions.elementToBeClickable(dataTableOptionMenu.getWebElement()));
                dataTableOptionMenu.waitAndClick(30);

                driver.FindElementByXPath(locatorReader.getobjectLocator("btnSingleRelease")).waitAndClick(30);
                driver.FindElementById(locatorReader.getobjectLocator("okBtnSingleRelease")).waitAndClick(30);

                Element toastMessageSingleRelease = driver.FindElementByXPath(locatorReader.getobjectLocator("toastMessageSingleRelease"));
                wait.until(ExpectedConditions.elementToBeClickable(toastMessageSingleRelease.getWebElement()));
                String successToastMessageBulkRelease = toastMessageSingleRelease.getText();
                Assert.assertTrue(successToastMessageBulkRelease.contains("Preservation Single Release"));
            } else {
                System.out.println("Filtered Site is not Active for system to Release");
            }
        } else {
            throw new RuntimeException("No Site found for system to release after filtering");
        }
    }


    public void verifyIfSiteIsReleased(String holdName, String siteUrl) throws InterruptedException {

        searchPreservationSiteDataTable(holdName, siteUrl);
        int count = getDataTableCount();
        int iterator = 0;
        int pendingRelease = 0;
        if (count > 1) {
            int statusIndex = getColumnIndexFromDataTable("Status");
            boolean flag = false;
            int c = 0;
            List<String> statusList = new ArrayList<>();
            for (int i = 1; i <= count; i++) {
                statusList.add(driver.FindElementByXPath("//table[@id='id-Preservation']/tbody/tr[" + i + "]/td[" + statusIndex + "]").getText());
            }
            for (String value : statusList) {
                if (value.equalsIgnoreCase("Released")) {
                    c++;
                    if (c == count) flag = true;
                } else if (!value.equalsIgnoreCase("Pending Release")) {
                    pendingRelease++;
                }
            }
            while (!flag && pendingRelease != count) {
                statusList.clear();
                driver.getWebDriver().navigate().refresh();
                Thread.sleep(12000);
                searchPreservationSiteDataTable(holdName, siteUrl);
//                Thread.sleep(3000);
                driver.waitForPageToBeReady();
                for (int i = 1; i <= count; i++) {
                    statusList.add(driver.FindElementByXPath("//table[@id='id-Preservation']/tbody/tr[" + i + "]/td[" + statusIndex + "]").getText());
                }
                for (String s : statusList) {
                    if (s.equalsIgnoreCase("Released")) {
                        c++;
                        if (c == count) {
                            flag = true;
                            break;
                        }
                    }
                }
                iterator++;
                if (iterator == 15) break;
            }
            if (flag) {
                System.out.println("All Sites from " + holdName + " are Released");
            } else {
                throw new RuntimeException("All Sites from " + holdName + " are NOT Released");
            }

        } else if (count == 1) {
            int statusIndex = getColumnIndexFromDataTable("Status");
            var status = driver.FindElementByXPath("//table[@id='id-Preservation']/tbody/tr[1]/td[" + statusIndex + "]").getText();
            while (status.equalsIgnoreCase("Pending Release")) {
                driver.getWebDriver().navigate().refresh();
                Thread.sleep(12000);
                searchPreservationSiteDataTable(holdName, siteUrl);
//                Thread.sleep(3000);
                driver.waitForPageToBeReady();
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='id-Preservation']/tbody/tr[1]/td[" + statusIndex + "]")));
                status = driver.FindElementByXPath("//table[@id='id-Preservation']/tbody/tr[1]/td[" + statusIndex + "]").getText();
                iterator++;
                if (status.equalsIgnoreCase("Released")) {
                    break;
                }
                if (iterator == 15) break;
            }
            System.out.println("The site(s) of " + holdName + " are in: " + status + " status");
            if (!status.equalsIgnoreCase("Released")) {
                throw new RuntimeException("The site(s) of " + holdName + " are in NOT Released Status");
            } else {
                System.out.println("The site(s) of " + holdName + " are in Released Status");
            }
        } else {
            throw new RuntimeException("No available Site(s) after filtering");
        }
    }

    public void releasePreservationTeamsFromDataTable(String holdName, String teamName) throws InterruptedException {
        searchPreservationTeamsDataTable(holdName, teamName);
        int count = getDataTableCount();

        if (count > 1) {
            int statusIndex = getColumnIndexFromDataTable("Status");
            boolean flag = false;
            int c = 0;
            List<String> statusList = new ArrayList<>();
            for (int i = 1; i <= count; i++) {
                statusList.add(driver.FindElementByXPath("//table[@id='id-Preservation']/tbody/tr[" + i + "]/td[" + statusIndex + "]").getText());
            }
            for (String value : statusList) {
                if (value.equalsIgnoreCase("Released")) {
                    c++;
                    if (c == count) flag = true;
                }
            }
            Element selectAllPreservationDataTable = driver.FindElementByCssSelector(locatorReader.getobjectLocator("selectAllPreservationDataTable"));
            wait.until(ExpectedConditions.elementToBeClickable(selectAllPreservationDataTable.getWebElement()));
            selectAllPreservationDataTable.waitAndClick(30);
            Element btnBulkRelease = driver.FindElementById(locatorReader.getobjectLocator("btnBulkRelease"));
            Thread.sleep(2000);
            if (!flag && btnBulkRelease.Enabled()) {
//                Element btnBulkRelease = driver.FindElementById(locatorReader.getobjectLocator("btnBulkRelease"));
                btnBulkRelease.waitAndClick(30);
                driver.FindElementById(locatorReader.getobjectLocator("okBtnBulkReleaseModal")).waitAndClick(30);

                Element toastMessageBulkRelease = driver.FindElementByXPath(locatorReader.getobjectLocator("toastMessageBulkRelease"));
                wait.until(ExpectedConditions.elementToBeClickable(toastMessageBulkRelease.getWebElement()));
                String successToastMessageBulkRelease = toastMessageBulkRelease.getText();
                Assert.assertTrue(successToastMessageBulkRelease.contains("Preservation bulk release"));
            } else {
                System.out.println("No Team Mailbox is in Active status for system to Release");
            }
        } else if (count == 1) {
            int statusIndex = getColumnIndexFromDataTable("Status");
            var status = driver.FindElementByXPath("//table[@id='id-Preservation']/tbody/tr[1]/td[" + statusIndex + "]").getText();
            Element dataTableOptionMenu = driver.FindElementByCssSelector(locatorReader.getobjectLocator("dataTableOptionMenu"));
            if (!status.equalsIgnoreCase("Released") && dataTableOptionMenu.isDisplayed()) {
                wait.until(ExpectedConditions.elementToBeClickable(dataTableOptionMenu.getWebElement()));
                dataTableOptionMenu.waitAndClick(30);

                driver.FindElementByXPath(locatorReader.getobjectLocator("btnSingleRelease")).waitAndClick(30);
                driver.FindElementById(locatorReader.getobjectLocator("okBtnSingleRelease")).waitAndClick(30);

                Element toastMessageSingleRelease = driver.FindElementByXPath(locatorReader.getobjectLocator("toastMessageSingleRelease"));
                wait.until(ExpectedConditions.elementToBeClickable(toastMessageSingleRelease.getWebElement()));
                String successToastMessageBulkRelease = toastMessageSingleRelease.getText();
                Assert.assertTrue(successToastMessageBulkRelease.contains("Preservation Single Release"));
            } else {
                System.out.println("Filtered Team Mailbox is not Active for system to Release");
            }
        } else {
            throw new RuntimeException("No Team Mailbox found for system to release after filtering");
        }
    }


    public void verifyIfTeamsIsReleased(String holdName, String teamName) throws InterruptedException {

        searchPreservationTeamsDataTable(holdName, teamName);
        int count = getDataTableCount();
        int iterator = 0;
        int pendingRelease = 0;
        if (count > 1) {
            int statusIndex = getColumnIndexFromDataTable("Status");
            boolean flag = false;
            int c = 0;
            List<String> statusList = new ArrayList<>();
            for (int i = 1; i <= count; i++) {
                statusList.add(driver.FindElementByXPath("//table[@id='id-Preservation']/tbody/tr[" + i + "]/td[" + statusIndex + "]").getText());
            }
            for (String value : statusList) {
                if (value.equalsIgnoreCase("Released")) {
                    c++;
                    if (c == count) flag = true;
                } else if (!value.equalsIgnoreCase("Pending Release")) {
                    pendingRelease++;
                }
            }
            while (!flag && pendingRelease != count) {
                statusList.clear();
                driver.getWebDriver().navigate().refresh();
                Thread.sleep(12000);
                searchPreservationTeamsDataTable(holdName, teamName);
//                Thread.sleep(3000);
                driver.waitForPageToBeReady();
                for (int i = 1; i <= count; i++) {
                    statusList.add(driver.FindElementByXPath("//table[@id='id-Preservation']/tbody/tr[" + i + "]/td[" + statusIndex + "]").getText());
                }
                for (String s : statusList) {
                    if (s.equalsIgnoreCase("Released")) {
                        c++;
                        if (c == count) {
                            flag = true;
                            break;
                        }
                    }
                }
                iterator++;
                if (iterator == 15) break;
            }
            if (flag) {
                System.out.println("All Team Mailboxes from " + holdName + " are Released");
            } else {
                throw new RuntimeException("All Team Mailboxes from " + holdName + " are NOT Released");
            }

        } else if (count == 1) {
            int statusIndex = getColumnIndexFromDataTable("Status");
            var status = driver.FindElementByXPath("//table[@id='id-Preservation']/tbody/tr[1]/td[" + statusIndex + "]").getText();
            while (status.equalsIgnoreCase("Pending Release")) {
                driver.getWebDriver().navigate().refresh();
                Thread.sleep(12000);
                searchPreservationTeamsDataTable(holdName, teamName);
//                Thread.sleep(3000);
                driver.waitForPageToBeReady();
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='id-Preservation']/tbody/tr[1]/td[" + statusIndex + "]")));
                status = driver.FindElementByXPath("//table[@id='id-Preservation']/tbody/tr[1]/td[" + statusIndex + "]").getText();
                iterator++;
                if (status.equalsIgnoreCase("Released")) {
                    break;
                }
                if (iterator == 15) break;
            }
            System.out.println("The Team Mailbox(s) of " + holdName + " are in: " + status + " status");
            if (!status.equalsIgnoreCase("Released")) {
                throw new RuntimeException("The Team Mailbox(s) of " + holdName + " are in NOT Released Status");
            } else {
                System.out.println("The Team Mailbox(s) of " + holdName + " are in Released Status");
            }
        } else {
            throw new RuntimeException("No available Team Mailbox(s) after filtering");
        }
    }


    public int getColumnIndexFromDataTable(String headerName) {
        var table = driver.FindElementById("id-Preservation");
        wait.until(ExpectedConditions.elementToBeClickable(table.getWebElement()));
        int columnIndex = 0;

        var headerList = driver.FindElementsByXPath("//table[@id='id-Preservation']//thead/tr[1]/th");
        for (int i = 0; i < headerList.size(); i++) {
            if (headerList.getElementByIndex(i).getText().equalsIgnoreCase(headerName)) {
                columnIndex = i + 1;
                break;
            }
        }

        if (columnIndex == 0) {
            driver.FindElementById(locatorReader.getobjectLocator("btnColumnSetup")).waitAndClick(30);
            var availableColumnList = driver.FindElementsByXPath("//tbody[@id='id-tablebody-available-Preservation']/tr/td[2]");
            for (int i = 0; i < availableColumnList.size(); i++) {

                if (availableColumnList.getElementByIndex(i).getText().equalsIgnoreCase(headerName)) {
                    driver.FindElementByXPath("//tbody[@id='id-tablebody-available-Preservation']/tr[" + (i + 1) + "]/td[1]/input").waitAndClick(30);
                    driver.FindElementByXPath(locatorReader.getobjectLocator("addBtnColumnSetup")).waitAndClick(30);
                    driver.FindElementByXPath(locatorReader.getobjectLocator("saveBtnColumnSetup")).waitAndClick(30);
                    break;
                }
            }
            columnIndex = headerList.size();
        }
        return columnIndex;
    }

}
