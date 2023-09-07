package legalhold.legalholdpagefactory.cases;

import automationLibrary.Driver;
import automationLibrary.Element;

import cucumber.api.java8.El;
import legalhold.BaseModule;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import scala.xml.Elem;

import java.io.IOException;
import java.util.List;

public class CaseCommunicationFactories extends BaseModule {

    public CaseCommunicationFactories(Driver driver) throws IOException {
        super("src/main/java/legalhold/selectors/cases/manage_case/communication/communication_page.properties", driver);
    }

    public String enterSeriesName() {
        WebElement inputSeriesName = driver.getWebDriver().findElement(By.id(locatorReader.getobjectLocator("inputSeriesName")));
        wait.until(ExpectedConditions.elementToBeClickable(inputSeriesName));
        String seriesName = faker.ancient().god() + " " + faker.ancient().hero();
        inputSeriesName.sendKeys(seriesName);
        return seriesName;
    }

    public void enableComplianceReminder() {
        List<WebElement> enable = driver.getWebDriver().findElements(By.cssSelector("input[modal-schedule-type='1'][name='Compliance Reminder']"));
//        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", enable);
        if (!enable.get(0).isSelected()) {
            enable.get(0).click();
        } else {
            System.out.println("Schedule already enabled");
        }
    }

    public void disableComplianceReminder() {
        List<WebElement> disable = driver.getWebDriver().findElements(By.cssSelector("input[modal-schedule-type='1'][name='Compliance Reminder']"));
//        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", disable);
        if (!disable.get(1).isSelected()) {
            disable.get(1).click();
        } else {
            System.out.println("Schedule already disabled");
        }
    }

    public void setComplianceReminderAsOneTime() {
        WebElement btnComplianceReminderSchedule = driver.getWebDriver().findElement(By.cssSelector(locatorReader.getobjectLocator("btnComplianceReminderSchedule")));
        wait.until(ExpectedConditions.elementToBeClickable(btnComplianceReminderSchedule));
        btnComplianceReminderSchedule.click();
        Element btnOneTime = driver.FindElementByCssSelector("input[aria-label='one time']");
        if (!btnOneTime.Selected()) {
            btnOneTime.Click();
        } else {
            System.out.println("Already set as One Time");
        }
        driver.FindElementById(locatorReader.getobjectLocator("btnScheduleModalSubmit")).Click();
    }


    public void TableFilteringVarification() {

        try {

            String expected_text = "Showing 1 to 1 of 1 entries";
            wait.until(ExpectedConditions.textToBe(By.id(locatorReader.getobjectLocator("paginationText")), expected_text));
        } catch (Exception e) {
            System.out.println("Expected Pagination Text Not Found" + e.getMessage());
        }
    }

    public void goToCreateCustodianCommunicationPage() {
        WebElement btnCreateCustodianCommunication = driver.getWebDriver().findElement(By.xpath(locatorReader.getobjectLocator("btnCreateCustodianCommunication")));
        wait.until(ExpectedConditions.elementToBeClickable(btnCreateCustodianCommunication));
        btnCreateCustodianCommunication.click();
        driver.waitForPageToBeReady();
        driver.getWebDriver().navigate().refresh();
        driver.waitForPageToBeReady();
        driver.scrollingToElementofAPage(btnCreateCustodianCommunication);
    }


    public void openAcknowledgmentSubTab() {
        WebElement btnScheduledReminderSchedule = driver.getWebDriver().findElement(By.cssSelector(locatorReader.getobjectLocator("btnScheduledReminderSchedule")));
        wait.until(ExpectedConditions.elementToBeClickable(btnScheduledReminderSchedule));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", btnScheduledReminderSchedule);
        Element acknowledgmentEmailTab = driver.FindElementByCssSelector(locatorReader.getobjectLocator("acknowledgmentEmailTab"));
        driver.scrollingToElementofAPage(acknowledgmentEmailTab);
        acknowledgmentEmailTab.Click();
    }

    public void openComplianceReminderSubTab() {
        WebElement btnScheduledReminderSchedule = driver.getWebDriver().findElement(By.cssSelector(locatorReader.getobjectLocator("btnScheduledReminderSchedule")));
        wait.until(ExpectedConditions.elementToBeClickable(btnScheduledReminderSchedule));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", btnScheduledReminderSchedule);
        Element complianceReminderEmailTab = driver.FindElementByCssSelector(locatorReader.getobjectLocator("complianceReminderEmailTab"));
        driver.scrollingToElementofAPage(complianceReminderEmailTab);
        complianceReminderEmailTab.Click();
    }

    public void openScheduledReminderSubTab() {
        WebElement btnScheduledReminderSchedule = driver.getWebDriver().findElement(By.cssSelector(locatorReader.getobjectLocator("btnScheduledReminderSchedule")));
        wait.until(ExpectedConditions.elementToBeClickable(btnScheduledReminderSchedule));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", btnScheduledReminderSchedule);
        Element scheduledReminderEmailTab = driver.FindElementByCssSelector(locatorReader.getobjectLocator("scheduledReminderEmailTab"));
        driver.scrollingToElementofAPage(scheduledReminderEmailTab);
        scheduledReminderEmailTab.Click();
    }

    public void openEscalationSubTab() {
        WebElement btnScheduledReminderSchedule = driver.getWebDriver().findElement(By.cssSelector(locatorReader.getobjectLocator("btnScheduledReminderSchedule")));
        wait.until(ExpectedConditions.elementToBeClickable(btnScheduledReminderSchedule));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", btnScheduledReminderSchedule);
        Element escalationEmailTab = driver.FindElementByCssSelector(locatorReader.getobjectLocator("escalationEmailTab"));
        driver.scrollingToElementofAPage(escalationEmailTab);
        escalationEmailTab.Click();
    }

    public void addMailToRecipients() throws IOException, InterruptedException {


        Element addMailToRecipientButton = driver.FindElementByClassName("mail-to-recipients");
        wait.until(ExpectedConditions.elementToBeClickable(addMailToRecipientButton.getWebElement()));
        addMailToRecipientButton.Click();

        Element availableTableEmployeeId = driver.FindElementByXPath("//table[@id='id-CommunicationAvailableMailTo']//thead//tr//th//input[@placeholder='Search Employee ID']");
        availableTableEmployeeId.Clear();
        availableTableEmployeeId.Click();
        availableTableEmployeeId.SendKeys("SLH-1");


        Element selectAllCheckBox = driver.FindElementByCssSelector("input[name='th-CommunicationAvailableMailTochkBoxAll']");
        wait.until(ExpectedConditions.elementToBeClickable(selectAllCheckBox.getWebElement()));
        Thread.sleep(5000);
        selectAllCheckBox.Click();

        Element addCustodiansButton = driver.FindElementById("add-user-MailToTab");
        addCustodiansButton.Click();


        Element confirmationModalOkButton = driver.FindElementById("modal-ok-MailToTab");
        wait.until(ExpectedConditions.elementToBeClickable(confirmationModalOkButton.getWebElement()));
        confirmationModalOkButton.Click();

        Element globalSaveButtonManageRecipients = driver.FindElementById("saveAndDoneButtonId");
        driver.scrollingToElementofAPage(globalSaveButtonManageRecipients);
        wait.until(ExpectedConditions.elementToBeClickable(globalSaveButtonManageRecipients.getWebElement()));
        Thread.sleep(3000);
        globalSaveButtonManageRecipients.Click();

        Element confirmationOkButton = driver.FindElementById("modalConfirmOkbutton");
        wait.until(ExpectedConditions.elementToBeClickable(confirmationOkButton.getWebElement()));
        confirmationOkButton.Click();


    }

    public void enterCommunicationNameAndDescription() {
        driver.FindElementByXPath(locatorReader.getobjectLocator("inputCommunicationName")).SendKeys(faker.lorem().sentence(3));
        driver.FindElementByXPath(locatorReader.getobjectLocator("inputCommunicationDescription")).SendKeys(faker.lorem().sentence(50));
    }

    public void enterAcknowledgmentEmailSubject(String subject) {
        driver.FindElementByCssSelector(locatorReader.getobjectLocator("inputEmailSubject")).SendKeys(subject);
    }

    public void enterComplianceReminderEmailSubject(String subject) {
        driver.FindElementByCssSelector(locatorReader.getobjectLocator("inputEmailSubject")).SendKeys(subject);
    }

    public void enterEscalationEmailSubject(String subject) {
        driver.FindElementByCssSelector(locatorReader.getobjectLocator("inputEmailSubject")).SendKeys(subject);
    }

    public void enterScheduledReminderEmailSubject(String subject) {
        driver.FindElementByCssSelector(locatorReader.getobjectLocator("inputEmailSubject")).SendKeys(subject);
    }

    public void typeEmailBody(String emailText) {
        try {
            WebElement tinyMCE = driver.getWebDriver().findElement(By.id(locatorReader.getobjectLocator("tinyMCE")));
            wait.until(ExpectedConditions.visibilityOf(tinyMCE));
            driver.switchTo().frame("editorTiny_ifr");
            driver.FindElementById("tinymce").SendKeys(emailText);
            driver.switchTo().defaultContent();
        } catch (Exception e) {
            System.out.println("TinyMCE Not Found");
            System.out.println(e.getMessage());
        }
    }

    public void saveCommunicationSeries() throws InterruptedException {

        WebElement btnSaveSeries = driver.getWebDriver().findElement(By.id(locatorReader.getobjectLocator("btnSaveCommunication")));
        wait.until(ExpectedConditions.elementToBeClickable(btnSaveSeries));
//        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", btnSaveSeries);
        btnSaveSeries.click();
        driver.FindElementById(locatorReader.getobjectLocator("okBtnSaveModal")).Click();
        Thread.sleep(5000);
        driver.waitForPageToBeReady();
    }

    public void startCommunicationSeries() throws InterruptedException {
        WebElement btnStarSeries = driver.getWebDriver().findElement(By.id(locatorReader.getobjectLocator("btnStartCommunication")));
        wait.until(ExpectedConditions.elementToBeClickable(btnStarSeries));
//        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", btnStarSeries);
        btnStarSeries.click();
        driver.FindElementById(locatorReader.getobjectLocator("okBtnStartModal")).Click();
        Thread.sleep(5000);
        driver.waitForPageToBeReady();
    }

    public void searchCustodianTypeSeriesByName(String seriesName) {
        try {
            WebElement seriesNameColumnFilterBox = driver.getWebDriver().findElement(By.xpath(locatorReader.getobjectLocator("seriesNameColumnFilterBox")));
            wait.until(ExpectedConditions.elementToBeClickable(seriesNameColumnFilterBox));
            seriesNameColumnFilterBox.sendKeys(seriesName);
            Element selectCommunicationTypeAsAcknowledgment = driver.FindElementByCssSelector(locatorReader.getobjectLocator("selectCommunicationType"));
            selectCommunicationTypeAsAcknowledgment.selectFromDropdown().selectByVisibleText("Acknowledgment");
            String expected_text = "Showing 1 to 1 of 1 entries";
            wait.until(ExpectedConditions.textToBe(By.id(locatorReader.getobjectLocator("rowCount")), expected_text));
        } catch (Exception E) {
            System.out.println("Case Name not found. The exception is: ");
            System.out.println(E.getMessage());
        }
    }

    public void searchNonCustodianTypeSeriesByName(String seriesName) {
        try {
            WebElement seriesNameColumnFilterBox = driver.getWebDriver().findElement(By.xpath(locatorReader.getobjectLocator("seriesNameColumnFilterBox")));
            wait.until(ExpectedConditions.elementToBeClickable(seriesNameColumnFilterBox));
            seriesNameColumnFilterBox.sendKeys(seriesName);
            Element selectCommunicationTypeAsNotice = driver.FindElementByCssSelector(locatorReader.getobjectLocator("selectCommunicationType"));
            selectCommunicationTypeAsNotice.selectFromDropdown().selectByVisibleText("Notice");
            String expected_text = "Showing 1 to 1 of 1 entries";
            wait.until(ExpectedConditions.textToBe(By.id(locatorReader.getobjectLocator("rowCount")), expected_text));
        } catch (Exception E) {
            System.out.println("Case Name not found. The exception is: ");
            System.out.println(E.getMessage());
        }
    }

    public void searchReleaseTypeSeriesByName(String seriesName) {
        try {
            WebElement seriesNameColumnFilterBox = driver.getWebDriver().findElement(By.xpath(locatorReader.getobjectLocator("seriesNameColumnFilterBox")));
            wait.until(ExpectedConditions.elementToBeClickable(seriesNameColumnFilterBox));
            seriesNameColumnFilterBox.sendKeys(seriesName);
            Element selectCommunicationTypeAsRelease = driver.FindElementByCssSelector(locatorReader.getobjectLocator("selectCommunicationType"));
            selectCommunicationTypeAsRelease.selectFromDropdown().selectByVisibleText("Release");
            String expected_text = "Showing 1 to 1 of 1 entries";
            wait.until(ExpectedConditions.textToBe(By.id(locatorReader.getobjectLocator("rowCount")), expected_text));
        } catch (Exception E) {
            System.out.println("Case Name not found. The exception is: ");
            System.out.println(E.getMessage());
        }
    }

    public void goToEditCustodianCommunicationPage(String seriesName) throws InterruptedException {
        searchCustodianTypeSeriesByName(seriesName);
        driver.FindElementByCssSelector(locatorReader.getobjectLocator("btnEditCommunication")).Click();
        driver.waitForPageToBeReady();
        WebElement tinyMCE = driver.getWebDriver().findElement(By.id(locatorReader.getobjectLocator("tinyMCE")));
        while (!tinyMCE.isDisplayed()) {
            driver.getWebDriver().navigate().refresh();
            Thread.sleep(1500);
        }
        driver.waitForPageToBeReady();
    }

    public void goToEditNonCustodianCommunicationPage(String seriesName) {
        searchNonCustodianTypeSeriesByName(seriesName);
        driver.FindElementByCssSelector(locatorReader.getobjectLocator("btnEditCommunication")).Click();
        driver.waitForPageToBeReady();
    }

    public void goToEditReleaseCommunicationPage(String seriesName) {
        searchReleaseTypeSeriesByName(seriesName);
        driver.FindElementByCssSelector(locatorReader.getobjectLocator("btnEditCommunication")).Click();
        driver.waitForPageToBeReady();
    }

    public void verifyPostSendForCustodianSeries(String seriesName) throws InterruptedException {
        try {
            searchCustodianTypeSeriesByName(seriesName);
            Element lastSentDateCell = driver.FindElementByXPath("//table[@id='id-communication']/tbody/tr[1]/td[6]");
            String lastSentDate = lastSentDateCell.getText();
            WebElement seriesNameColumnFilterBox = driver.getWebDriver().findElement(By.xpath(locatorReader.getobjectLocator("seriesNameColumnFilterBox")));
            seriesNameColumnFilterBox.clear();
            while (lastSentDate.equalsIgnoreCase("")) {
                Thread.sleep(1000);
                driver.getWebDriver().navigate().refresh();
                searchCustodianTypeSeriesByName(seriesName);
                lastSentDate = lastSentDateCell.getText();
            }
            seriesNameColumnFilterBox.clear();
            goToEditCustodianCommunicationPage(seriesName);
            Element inputSeriesName = driver.FindElementById(locatorReader.getobjectLocator("inputSeriesName"));
            Element acknowledgmentSentDateTableCell = driver.FindElementByCssSelector("td[communication-schedule-type='0']");
            String acknowledgmentSentDate = acknowledgmentSentDateTableCell.getText();
            if (!inputSeriesName.Enabled() && acknowledgmentSentDate.equalsIgnoreCase(lastSentDate)) {
                System.out.println("The communication series: " + seriesName + ", is in Post-Send mode");
            }
        }catch (Exception e){
            System.out.println("The communication series: " + seriesName + ", is NOT in Post-Send mode");
            System.out.println(e.getMessage());
        }
    }


}
