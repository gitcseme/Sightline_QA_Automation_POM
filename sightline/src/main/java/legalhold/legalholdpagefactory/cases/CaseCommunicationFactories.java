package legalhold.legalholdpagefactory.cases;

import automationLibrary.Driver;
import automationLibrary.Element;
import legalhold.BaseModule;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;
import java.util.List;

public class CaseCommunicationFactories extends BaseModule {
    JavascriptExecutor jsExecutor;
    public CaseCommunicationFactories(Driver driver) throws IOException {
        super(driver);
        jsExecutor = (JavascriptExecutor) driver.getWebDriver();
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
