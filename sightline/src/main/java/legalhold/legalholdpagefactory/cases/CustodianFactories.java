package legalhold.legalholdpagefactory.cases;

import automationLibrary.Driver;
import automationLibrary.Element;
import legalhold.setup.BaseModule;
import legalhold.utilities.parse_locators.LocatorReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustodianFactories extends BaseModule {

    List<WebElement> ManageCaseTabs;
    LocatorReader reader;
    LocatorReader custodianReader;
    CaseFactories caseFactories;
    CaseCommunicationFactories communicationFactories;

    public CustodianFactories(Driver driver) throws IOException {

        super("./src/main/java/legalhold/selectors/cases/casepage.properties", driver);
        reader = new LocatorReader("src/main/java/legalhold/selectors/cases/manage_case/case_information/case_information.properties");
        custodianReader = new LocatorReader("./src/main/java/legalhold/selectors/cases/manage_case/custodian/custodian.properties");

        caseFactories = new CaseFactories(driver);
        communicationFactories = new CaseCommunicationFactories(driver);
    }

    public void navigateToManageCustodiansPage(){
        Element ManageCustodianButton = driver.FindElementById("btnManageCustodians");
        wait.until(ExpectedConditions.elementToBeClickable(ManageCustodianButton.getWebElement()));
        ManageCustodianButton.Click();

        driver.waitForPageToBeReady();
    }

    public void searchManageCustodianAvailableCustodianById(String id) {
        try {
            Element availableEmployeeIDFilterBox = driver.FindElementByCssSelector("table[id='id-ManageEmpTable'] thead tr th input[placeholder='Search Employee ID']");
            wait.until(ExpectedConditions.elementToBeClickable(availableEmployeeIDFilterBox.getWebElement()));
            availableEmployeeIDFilterBox.Clear();
            availableEmployeeIDFilterBox.SendKeys(id);

            String expected_text = "Showing 1 to 1 of 1 entries";
            wait.until(ExpectedConditions.textToBe(By.id(locatorReader.getobjectLocator("rowCount")), expected_text));
        } catch (Exception E) {
            System.out.println("Custodian not found. The exception is: ");
            System.out.println(E.getMessage());
        }
    }
    public void searchManageCustodianSelectedeCustodianById(String id) {
        try {
            Element selectedEmployeeIDFilterBox = driver.FindElementByCssSelector("table[id='id-manageCustodianDataTable'] thead tr th input[placeholder='Search Employee ID']");
            wait.until(ExpectedConditions.elementToBeClickable(selectedEmployeeIDFilterBox.getWebElement()));
            selectedEmployeeIDFilterBox.Clear();
            selectedEmployeeIDFilterBox.SendKeys(id);

            String expected_text = "Showing 1 to 1 of 1 entries";
            wait.until(ExpectedConditions.textToBe(By.id(locatorReader.getobjectLocator("rowCount")), expected_text));
        } catch (Exception E) {
            System.out.println("Custodian not found. The exception is: ");
            System.out.println(E.getMessage());
        }
    }

    public void manageCustodianGlobalSave() throws InterruptedException {
        Element manageCustodianGlobalSavebtn = driver.FindElementById("saveAndDoneBtnId");
        driver.scrollingToElementofAPage(manageCustodianGlobalSavebtn);
        manageCustodianGlobalSavebtn.waitAndClick(10);

        driver.FindElementById("save-ok-btn").waitAndClick(20);

        Thread.sleep(6000);
    }

    public void addCustodianToCase(String custodianToAdd) throws InterruptedException {
        searchManageCustodianAvailableCustodianById(custodianToAdd);

        driver.FindElementByCssSelector("input[name='th-ManageEmpTablechkBoxAll']").waitAndClick(30);

        driver.FindElementById("addToCustodian").waitAndClick(20);
        Thread.sleep(5000);
        driver.FindElementById("addUserOkButton").waitAndClick(30);

        searchManageCustodianSelectedeCustodianById(custodianToAdd);

        manageCustodianGlobalSave();
    }


    public void verifyCustodianType(String expectedCustodianType) throws InterruptedException {

        driver.waitForPageToBeReady();
        Thread.sleep(4000);
        Element custodianTypeValue = driver.FindElementByCssSelector("td:nth-child(6)");
        wait.until(ExpectedConditions.elementToBeClickable(custodianTypeValue.getWebElement()));
        Assert.assertEquals(custodianTypeValue.getText(),expectedCustodianType);
    }

    public  void verifyAcknowledgementCompleteDate(String date) throws InterruptedException {
        driver.waitForPageToBeReady();
        Thread.sleep(4000);
        Element acknowledgementCompleteDateValue = driver.FindElementByCssSelector("#id-Custodian > tbody > tr > td:nth-child(12)");
        wait.until(ExpectedConditions.elementToBeClickable(acknowledgementCompleteDateValue.getWebElement()));
        System.out.println("valuee"+acknowledgementCompleteDateValue.getText());
    }

    public void verifyManualAcknowledgementReason(String reason) throws InterruptedException {
        driver.waitForPageToBeReady();
        Thread.sleep(4000);
        Element manualAcknowledgementReason = driver.FindElementByCssSelector("td:nth-child(11)");
        wait.until(ExpectedConditions.elementToBeClickable(manualAcknowledgementReason.getWebElement()));
        Assert.assertEquals(manualAcknowledgementReason.getText(),reason);
    }
    public void verifyReleaseDate(String expectedReleaseDate) throws InterruptedException {
        driver.waitForPageToBeReady();
        Thread.sleep(4000);
        Element releaseDate = driver.FindElementByCssSelector("td:nth-child(5)");
        wait.until(ExpectedConditions.elementToBeClickable(releaseDate.getWebElement()));
        Assert.assertEquals(releaseDate.getText(),expectedReleaseDate);
    }

    public void verifyCustodianStatus(String expectedStatus) throws InterruptedException {
        driver.waitForPageToBeReady();
        Thread.sleep(4000);
        Element custodianStatus = driver.FindElementByCssSelector("td:nth-child(7)");
        wait.until(ExpectedConditions.elementToBeClickable(custodianStatus.getWebElement()));
        Assert.assertEquals(custodianStatus.getText(),expectedStatus);
    }

    public void noDataAvailableCustodianTable() throws InterruptedException {
        String expectedPaginationText = "Showing 0 to 0 of 0 entries";
        Element paginationText = driver.FindElementById("id-Custodian_info");
        Thread.sleep(2000);
        String actualPaginationText = paginationText.getText();
        Assert.assertEquals(actualPaginationText,expectedPaginationText);
    }
    public void searchCustodianById(String id) {
        try {
            Element availableEmployeeIDFilterBox = driver.FindElementByCssSelector("input[placeholder='Search Employee ID']");
            wait.until(ExpectedConditions.elementToBeClickable(availableEmployeeIDFilterBox.getWebElement()));
            availableEmployeeIDFilterBox.Clear();
            availableEmployeeIDFilterBox.SendKeys(id);

            String expected_text = "Showing 1 to 1 of 1 entries";

            wait.until(ExpectedConditions.textToBe(By.id(locatorReader.getobjectLocator("rowCount")), expected_text));
        } catch (Exception E) {
            System.out.println("Custodian not found. The exception is: ");
            System.out.println(E.getMessage());
        }
    }

    public int getColumnIndexFromDataTable(String headerName) {
        var table = driver.FindElementById(custodianReader.getobjectLocator("custodianTable"));
        wait.until(ExpectedConditions.elementToBeClickable(table.getWebElement()));
        int columnIndex = 0;

        var headerList = driver.FindElementsByXPath(custodianReader.getobjectLocator("tableHeaderList"));
        for (int i = 0; i < headerList.size(); i++) {
            if (headerList.getElementByIndex(i).getText().equalsIgnoreCase(headerName)) {
                columnIndex = i + 1;
                break;
            }
        }

        if (columnIndex == 0) {
            driver.FindElementByXPath(custodianReader.getobjectLocator("columnSetupIcon")).waitAndClick(30);
            var availableColumnList = driver.FindElementsByXPath(custodianReader.getobjectLocator("availableColumnTable"));
            for (int i = 0; i < availableColumnList.size(); i++) {

                if (availableColumnList.getElementByIndex(i).getText().equalsIgnoreCase(headerName)) {
                    driver.FindElementByXPath("//tbody[@id='id-tablebody-available-Custodian']/tr[" + (i + 1) + "]/td[1]/input").waitAndClick(30);
                    driver.FindElementByXPath(custodianReader.getobjectLocator("addColumnButton")).waitAndClick(30);
                    driver.FindElementByXPath(custodianReader.getobjectLocator("columnSetupSaveBtn")).waitAndClick(30);
                    break;
                }
            }
            columnIndex = headerList.size();
        }
        return columnIndex;
    }



    public int getDataTableRowCount(){

        int rowCount = driver.FindElementsByClassName(custodianReader.getobjectLocator("custodianCheckboxes")).size();
        return rowCount;
    }

    public void searchByCustodianId(String empId) throws InterruptedException {
        Thread.sleep(3000);
        Element custodianIdSearchBox = driver.FindElementByXPath(custodianReader.getobjectLocator("custodianIdSearchBox"));
        wait.until(ExpectedConditions.elementToBeClickable(custodianIdSearchBox.getWebElement()));
        custodianIdSearchBox.Clear();
        custodianIdSearchBox.SendKeys(empId);
        Thread.sleep(5000);
    }

    public String verifySeriesInReleaseWithComDropdown(String seriesName){
        Element seriesDropdown = driver.FindElementById(custodianReader.getobjectLocator("selectReleaseComDropdown"));
        Select select = new Select(seriesDropdown.getWebElement());


        wait.until(ExpectedConditions.elementToBeClickable(seriesDropdown.getWebElement()));
        for (WebElement option : select.getOptions()) {

            if (option.getText().toLowerCase(Locale.ROOT).contains(seriesName.toLowerCase(Locale.ROOT))) {

                System.out.println("option:"+ option.getText());
//                select.selectByVisibleText(option.getText());
                return option.getText();

            }

        }
        return  null;

    }
    public void releaseWithCommunication(String empId, String seriesName) throws InterruptedException {

        Boolean isAllSilent = verifyAllCustodiansSilent(empId);
        searchByCustodianId(empId);


        int rowCount = getDataTableRowCount();


        if(rowCount > 1)
        {
            Element selectAllCheckbox = driver.FindElementByXPath(custodianReader.getobjectLocator("selectAllCheckBox"));
            selectAllCheckbox.waitAndClick(30);

            Element releaseCustodianButton = driver.FindElementById(custodianReader.getobjectLocator("releaseBulkBtn"));
            wait.until(ExpectedConditions.elementToBeClickable(releaseCustodianButton.getWebElement()));
            releaseCustodianButton.waitAndClick(20);



            if(driver.FindElementByClassName("toast-error").isDisplayed())
            {
                System.out.println("All selected custodians are already released");

                selectAllCheckbox.waitAndClick(30);
                return;
            }

        }

        else if(rowCount==1)
        {
            Element actionMenu = driver.FindElementByXPath(custodianReader.getobjectLocator("actionMenu"));
            actionMenu.waitAndClick(30);
            Thread.sleep(3000);


            if(driver.FindElementByXPath(custodianReader.getobjectLocator("releaseCustodianOption")).isDisplayed())
            {
                driver.FindElementByXPath(custodianReader.getobjectLocator("releaseCustodianOption")).waitAndClick(20);
            }else{
                System.out.println("Filtered custodian is already released");
                return;
            }
        }





        if(isAllSilent)
        {
            Element selectReleaseWithoutComCheckbox = driver.FindElementByXPath(custodianReader.getobjectLocator("selectReleaseWithoutCom"));
            wait.until(ExpectedConditions.elementToBeClickable(selectReleaseWithoutComCheckbox.getWebElement()));

            if(selectReleaseWithoutComCheckbox.Enabled())
                selectReleaseWithoutComCheckbox.Click();


            Element releaseSubmitBtn = driver.FindElementById(custodianReader.getobjectLocator("custodianReleaseSubmitButton"));
            wait.until(ExpectedConditions.elementToBeClickable(releaseSubmitBtn.getWebElement()));
            releaseSubmitBtn.Click();
        }

        else {

            Element selectReleaseWithComCheckbox = driver.FindElementByXPath(custodianReader.getobjectLocator("selectReleaseWithCom"));
            wait.until(ExpectedConditions.elementToBeClickable(selectReleaseWithComCheckbox.getWebElement()));
            selectReleaseWithComCheckbox.Click();


            Element seriesDropdown = driver.FindElementById(custodianReader.getobjectLocator("selectReleaseComDropdown"));
            wait.until(ExpectedConditions.elementToBeClickable(seriesDropdown.getWebElement()));


            Select select = new Select(seriesDropdown.getWebElement());

            String optionText = verifySeriesInReleaseWithComDropdown(seriesName);

            if(optionText != null)
            {

                select.selectByVisibleText(optionText);
            }else{
                throw new RuntimeException("The release communication doesn't exist in the case");
            }

            Element releaseSubmitBtn = driver.FindElementById(custodianReader.getobjectLocator("custodianReleaseSubmitButton"));
            wait.until(ExpectedConditions.elementToBeClickable(releaseSubmitBtn.getWebElement()));
            releaseSubmitBtn.Click();
        }

//        Element successToast = driver.FindElementByXPath("//p[contains(normalize-space(),'Custodian(s) Released Successfully')]");
        Element successToast = driver.FindElementByClassName(custodianReader.getobjectLocator("successToast"));
        wait.until(ExpectedConditions.elementToBeClickable(successToast.getWebElement()));
        String successToastMessageRelease = successToast.getText();
        System.out.println("Toast"+successToast.getText());
        Assert.assertTrue(successToastMessageRelease.contains("Released successfully"));

        Element selectAllCheckbox = driver.FindElementByXPath(custodianReader.getobjectLocator("selectAllCheckBox"));
        selectAllCheckbox.waitAndClick(30);


    }

    public void releaseWithoutCommunication(String empId) throws InterruptedException {

        searchByCustodianId(empId);


        int rowCount = getDataTableRowCount();

        if (rowCount > 1) {
            Element selectAllCheckbox = driver.FindElementByXPath(custodianReader.getobjectLocator("selectAllCheckBox"));
            selectAllCheckbox.waitAndClick(30);

            Element releaseCustodianButton = driver.FindElementById(custodianReader.getobjectLocator("releaseBulkBtn"));
            wait.until(ExpectedConditions.elementToBeClickable(releaseCustodianButton.getWebElement()));
            releaseCustodianButton.waitAndClick(20);


            if(driver.FindElementByClassName(custodianReader.getobjectLocator("errorToast")).isDisplayed())
            {
                System.out.println("All selected custodians are already released");
                return;
            }

        } else if (rowCount == 1) {
            Element actionMenu = driver.FindElementByXPath(custodianReader.getobjectLocator("actionMenu"));
            actionMenu.waitAndClick(30);


            if(driver.FindElementByXPath(custodianReader.getobjectLocator("releaseCustodianOption")).isDisplayed())
            {
                System.out.println("Filtered Custodian is already Released");
                driver.FindElementByXPath(custodianReader.getobjectLocator("releaseCustodianOption")).waitAndClick(20);
            }else{
                return;
            }
        }

        Element selectReleaseWithoutComCheckbox = driver.FindElementByXPath(custodianReader.getobjectLocator("selectReleaseWithoutCom"));
        wait.until(ExpectedConditions.elementToBeClickable(selectReleaseWithoutComCheckbox.getWebElement()));


        selectReleaseWithoutComCheckbox.Click();


        Element releaseSubmitBtn = driver.FindElementById(custodianReader.getobjectLocator("custodianReleaseSubmitButton"));
        wait.until(ExpectedConditions.elementToBeClickable(releaseSubmitBtn.getWebElement()));
        releaseSubmitBtn.Click();

        Element successToast = driver.FindElementByClassName(custodianReader.getobjectLocator("successToast"));
        wait.until(ExpectedConditions.elementToBeClickable(successToast.getWebElement()));
        String successToastMessageRelease = successToast.getText();
        System.out.println("Toast"+successToast.getText());
        Assert.assertTrue(successToastMessageRelease.contains("Released successfully"));


        }

    public int getDataTableCount() {
        String paginationTextCustodianDataTable = driver.FindElementById(custodianReader.getobjectLocator("custodianTablePaginationText")).getText();
        Pattern pattern = Pattern.compile("of (\\d+)");
        Matcher matcher = pattern.matcher(paginationTextCustodianDataTable);

        int count = 0;
        while (matcher.find()) {
            count = Integer.parseInt(matcher.group(1));
        }
        return count;
    }


        public List <String> verifyIfCustodianIsReleased(String empId) throws InterruptedException {

            searchByCustodianId(empId);
            int count = getDataTableCount();
            int iterator = 0;
            int active = 0;
            if (count > 1) {
                int statusIndex = getColumnIndexFromDataTable("Status");
                int employeeId = getColumnIndexFromDataTable("Employee Id");
                searchByCustodianId(empId);
                boolean flag = false;
                int c = 0;
                List<String> statusList = new ArrayList<>();
                List<String> releasedEmpIdList = new ArrayList<>();

                for (int i = 1; i <= count; i++) {
                    String status = driver.FindElementByXPath("//table[@id='id-Custodian']/tbody/tr[" + i + "]/td[" + statusIndex + "]").getText();
                    statusList.add(status);
                    if(status.equalsIgnoreCase("Released"))
                    {
                        releasedEmpIdList.add( driver.FindElementByXPath("//table[@id='id-Custodian']/tbody/tr[" + i + "]/td[" + employeeId + "]").getText());
                    }
                }
                for (String value : statusList) {
                    if (value.equalsIgnoreCase("Released")) {
                        c++;
                        if (c == count) flag = true;
                    }
                }
                while (flag!=true) {
                    List<String> statusList2 = new ArrayList<>();
                    c=0;
                    driver.getWebDriver().navigate().refresh();
                    caseFactories.NavigateToCustodiansTab();
                    Thread.sleep(8000);
                    searchByCustodianId(empId);
//                Thread.sleep(3000);
                    driver.waitForPageToBeReady();
                    for (int i = 1; i <= count; i++) {

                        String status = driver.FindElementByXPath("//table[@id='id-Custodian']/tbody/tr[" + i + "]/td[" + statusIndex + "]").getText();
                        statusList2.add(status);
                        if(status.equalsIgnoreCase("Released"))
                        {
                            releasedEmpIdList.add( driver.FindElementByXPath("//table[@id='id-Custodian']/tbody/tr[" + i + "]/td[" + employeeId + "]").getText());
                        }
                    }
                    for (String s : statusList2) {
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
                    System.out.println("All selected Custodians  are Released");
                    return  releasedEmpIdList;
                } else {
                    throw new RuntimeException("All selected Custodians  are NOT Released");

                }

            } else {
                int statusIndex = getColumnIndexFromDataTable("Status");
                int employeeId = getColumnIndexFromDataTable("Employee Id");
                List<String> releasedEmpIdList = new ArrayList<>();

                searchByCustodianId(empId);
                var status = driver.FindElementByXPath("//table[@id='id-Custodian']/tbody/tr[1]/td[" + statusIndex + "]").getText();
                while (status.equalsIgnoreCase("Active")) {
                    driver.getWebDriver().navigate().refresh();
                    caseFactories.NavigateToCustodiansTab();
                    Thread.sleep(13000);
                    searchByCustodianId(empId);
//                Thread.sleep(3000);
                    driver.waitForPageToBeReady();
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='id-Custodian']/tbody/tr[1]/td[" + statusIndex + "]")));
                    status = driver.FindElementByXPath("//table[@id='id-Custodian']/tbody/tr[1]/td[" + statusIndex + "]").getText();
                    iterator++;
                    if (status.equalsIgnoreCase("Released")) {
                        releasedEmpIdList.add(driver.FindElementByXPath("//table[@id='id-Custodian']/tbody/tr[1]/td[" + employeeId + "]").getText());
                        return releasedEmpIdList;
                    }
                    if (iterator == 15) break;
                }
                System.out.println("The selected custodian is in: " + status + " status");
                if (!status.equalsIgnoreCase("Released")) {
                    throw new RuntimeException("The selected custodian(s) are in NOT Released Status");
                } else {
                    System.out.println("The selected custodian is in Released Status");
                    releasedEmpIdList.add(driver.FindElementByXPath("//table[@id='id-Custodian']/tbody/tr[1]/td[" + employeeId + "]").getText());
                    return releasedEmpIdList;
                }
            }
        }

        public  boolean verifyAllCustodiansSilent(String empId) throws InterruptedException {
            searchByCustodianId(empId);
            int count = getDataTableCount();
            int iterator = 0;
            int active = 0;
            if (count > 1) {
                int statusIndex = getColumnIndexFromDataTable("Custodian Type");
                searchByCustodianId(empId);
                boolean flag = false;
                int c = 0;
                List<String> statusList = new ArrayList<>();
                for (int i = 1; i <= count; i++) {
                    statusList.add(driver.FindElementByXPath("//table[@id='id-Custodian']/tbody/tr[" + i + "]/td[" + statusIndex + "]").getText());
                }
                for (String value : statusList) {
                    if (value.equalsIgnoreCase("Silent")) {
                        c++;
                        if (c == count) flag = true;
                    }
                }

                if (flag) {
                    System.out.println("All selected Custodians  are Silent");
                    return true;
                } else {
                    System.out.println("All selected Custodians  are NOT Silent");
                    return false;
                }

            } else {
                int statusIndex = getColumnIndexFromDataTable("Custodian Type");

                    searchByCustodianId(empId);
//                Thread.sleep(3000);
                    driver.waitForPageToBeReady();
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='id-Custodian']/tbody/tr[1]/td[" + statusIndex + "]")));
                    var status = driver.FindElementByXPath("//table[@id='id-Custodian']/tbody/tr[1]/td[" + statusIndex + "]").getText();

                if (!status.equalsIgnoreCase("Silent")) {
                    System.out.println("The selected custodian(s) is not Silent");
                    return false;
                } else {
                    System.out.println("The selected custodian is silent");
                    return true;
                }
            }
    }

    public void clearFilter(){
        Element clearFilter = driver.FindElementByXPath("//img[@title='Clear Active Filters']");
        wait.until(ExpectedConditions.elementToBeClickable(clearFilter.getWebElement()));
        clearFilter.waitAndClick(10);
    }

    public List <String> getAllSilentCustodians() throws InterruptedException {


        driver.waitForPageToBeReady();
        Thread.sleep(3000);
        int typeIndex = getColumnIndexFromDataTable("Custodian Type");
        int empId = getColumnIndexFromDataTable("Employee Id");
        List<String> silentCustodiansList = new ArrayList<>();

        WebElement custodianTypeDropdown = driver.getWebDriver().findElement(By.xpath("//select[@displayname='Custodian Type']"));
        wait.until(ExpectedConditions.elementToBeClickable(custodianTypeDropdown));
        Select selectCustodianType = new Select(custodianTypeDropdown);
        selectCustodianType.selectByVisibleText("Silent");
        Thread.sleep(2000);
        int count = getDataTableCount();
        for (int i = 1; i <= count; i++) {


                String employeeId = driver.FindElementByXPath("//table[@id='id-Custodian']/tbody/tr[" + i + "]/td[" + empId + "]").getText();
                silentCustodiansList.add(employeeId);

        }
        System.out.println("ListType"+ silentCustodiansList);

        return silentCustodiansList;
    }

    public List <String> filterReleasedAndNonSilentCustodians(List <String> allSilents, List <String> allReleased) {

        List<String> filteredList = new ArrayList<>();


        for (String value : allReleased) {
            if (!allSilents.contains(value)) {
                filteredList.add(value);
            }
        }

        // Print the filtered list
        System.out.println("Filtered List: " + filteredList);
        return filteredList;
    }

    public void checkIfCustodiansAreAddedInReleaseCommunication(List <String> releasedList) throws InterruptedException {

        driver.waitForPageToBeReady();
        int matchCount=0;
        Thread.sleep(3000);
        int empId = communicationFactories.getColumnIndexFromMailToRecipientsDataTable("Employee ID");
        List<String> mailToRecipientsList = new ArrayList<>();

        int count = communicationFactories.getMailToDataTableCount();
        for (int i = 1; i <= count; i++) {


            String employeeId = driver.FindElementByXPath("//table[@id='id-CommunicationRecipientMailTo']/tbody/tr[" + i + "]/td[" + empId + "]").getText();
            mailToRecipientsList.add(employeeId);

        }
        System.out.println("MailToList"+ mailToRecipientsList);

        for(int i=0;i<releasedList.size();i++)
        {
            for(int j=0;j<mailToRecipientsList.size();j++)
            {
                if(releasedList.get(i).equalsIgnoreCase(mailToRecipientsList.get(j)))
                {
                    matchCount++;
                }


            }


        }

        if(matchCount == releasedList.size())
        {
            System.out.println("All custodians added in mail To List Successfully");
        }else{
            throw new RuntimeException("All expected custodians are not added in Mail To List");
        }


    }



    }



