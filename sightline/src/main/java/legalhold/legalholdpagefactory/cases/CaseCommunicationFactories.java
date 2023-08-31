package legalhold.legalholdpagefactory.cases;

import automationLibrary.Driver;
import automationLibrary.Element;
import legalhold.BaseModule;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CaseCommunicationFactories extends BaseModule {
    JavascriptExecutor jsExecutor;
    public CaseCommunicationFactories(Driver driver) throws IOException {
        super(driver);
        jsExecutor = (JavascriptExecutor) driver.getWebDriver();
    }


    public void TableFilteringVarification() {

        try {

            String expected_text = "Showing 1 to 1 of 1 entries";
            wait.until(ExpectedConditions.textToBe(By.id(locatorReader.getobjectLocator("paginationText")), expected_text));
        }catch (Exception e){
            System.out.println("Expected Pagination Text Not Found" + e.getMessage());
        }
    }

    public void goToCreateCustodianCommunicationPage(){
        WebElement btnCreateCustodianCommunication = driver.getWebDriver().findElement(By.xpath("//a[@aria-label='Add Custodian Communication']"));
        wait.until(ExpectedConditions.elementToBeClickable(btnCreateCustodianCommunication));
        btnCreateCustodianCommunication.click();
        driver.waitForPageToBeReady();
        driver.getWebDriver().navigate().refresh();
        driver.waitForPageToBeReady();
        driver.scrollingToElementofAPage(btnCreateCustodianCommunication);
    }


    public void openAcknowledgmentSubTab() {
        WebElement btnScheduledReminderSchedule = driver.getWebDriver().findElement(By.cssSelector("a[modal-schedule-title='Schedule Reminder']"));
        wait.until(ExpectedConditions.elementToBeClickable(btnScheduledReminderSchedule));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", btnScheduledReminderSchedule);
        Element acknowledgmentEmailTab = driver.FindElementByCssSelector("a[communication-tab-type='0']");
        driver.scrollingToElementofAPage(acknowledgmentEmailTab);
        acknowledgmentEmailTab.Click();
    }

    public void openComplianceReminderSubTab() {
        WebElement btnScheduledReminderSchedule = driver.getWebDriver().findElement(By.cssSelector("a[modal-schedule-title='Schedule Reminder']"));
        wait.until(ExpectedConditions.elementToBeClickable(btnScheduledReminderSchedule));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", btnScheduledReminderSchedule);
        Element complianceReminderEmailTab = driver.FindElementByCssSelector("a[communication-tab-type='1']");
        driver.scrollingToElementofAPage(complianceReminderEmailTab);
        complianceReminderEmailTab.Click();
    }

    public void openScheduledReminderSubTab() {
        WebElement btnScheduledReminderSchedule = driver.getWebDriver().findElement(By.cssSelector("a[modal-schedule-title='Schedule Reminder']"));
        wait.until(ExpectedConditions.elementToBeClickable(btnScheduledReminderSchedule));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", btnScheduledReminderSchedule);
        Element scheduledReminderEmailTab = driver.FindElementByCssSelector("a[communication-tab-type='3']");
        driver.scrollingToElementofAPage(scheduledReminderEmailTab);
        scheduledReminderEmailTab.Click();
    }

    public void openEscalationSubTab() {
        WebElement btnScheduledReminderSchedule = driver.getWebDriver().findElement(By.cssSelector("a[modal-schedule-title='Schedule Reminder']"));
        wait.until(ExpectedConditions.elementToBeClickable(btnScheduledReminderSchedule));
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", btnScheduledReminderSchedule);
        Element escalationEmailTab = driver.FindElementByCssSelector("a[communication-tab-type='2']");
        driver.scrollingToElementofAPage(escalationEmailTab);
        escalationEmailTab.Click();
    }

    public void AddMailtoRecipients() throws IOException, InterruptedException {
        CaseFactories navigateToCommunicationsTab = new CaseFactories(driver);
        navigateToCommunicationsTab.NavigateToCommunicationsTab();
        goToCreateCustodianCommunicationPage();

        driver.waitForPageToBeReady();

        Element addMailToRecipientButton = driver.FindElementByClassName("mail-to-recipients");
        wait.until(ExpectedConditions.elementToBeClickable(addMailToRecipientButton.getWebElement()));
        addMailToRecipientButton.Click();



//            Element clearFilter = driver.FindElementByCssSelector("button[id='CommunicationAvailableMailTo-clear-filter-btn'] img[title='Clear Active Filters']");
//            clearFilter.Click();
            Element availableTableEmployeeId = driver.FindElementByXPath("//table[@id='id-CommunicationAvailableMailTo']//thead//tr//th//input[@placeholder='Search Employee ID']");
        availableTableEmployeeId.Clear();
        availableTableEmployeeId.Click();
        availableTableEmployeeId.SendKeys("SLH-1");


//        TableFilteringVarification();

//        Element checkBox = driver.FindElementByCssSelector("input[name='td-CommunicationAvailableMailTochkBoxAll']");
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

    public void enterCommunicationNameAndDescription(){
        driver.FindElementByXPath("//input[@aria-label='Communication Name']").SendKeys(faker.lorem().sentence(3));
        driver.FindElementByXPath("//textarea[@aria-label='Communication Description']").SendKeys(faker.lorem().sentence(50));
    }

    public void enterAcknowledgmentEmailSubject()
    {
        driver.FindElementByCssSelector("input[aria-label='Mail Subject']").SendKeys("Automation Acknowledgment");
    }

    public void enterComplianceReminderEmailSubject(){
        driver.FindElementByCssSelector("input[aria-label='Mail Subject']").SendKeys("Automation Compliance Reminder");
    }

    public void enterEscalationEmailSubject(){
        driver.FindElementByCssSelector("input[aria-label='Mail Subject']").SendKeys("Automation Escalation");
    }

    public void enterScheduledReminderEmailSubject(){
        driver.FindElementByCssSelector("input[aria-label='Mail Subject']").SendKeys("Automation Scheduled Reminder");
    }

}
