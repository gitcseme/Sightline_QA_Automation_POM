package legalhold.legalholdpagefactory.cases;

import automationLibrary.Driver;
import automationLibrary.Element;
import legalhold.setup.BaseModule;
import legalhold.utilities.parse_locators.LocatorReader;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.io.IOException;
import java.util.List;

public class CaseFactories extends BaseModule {

    List<WebElement> ManageCaseTabs;
    LocatorReader reader;

    public CaseFactories(Driver driver) throws IOException {

        super("./src/main/java/legalhold/selectors/cases/casepage.properties", driver);
        reader = new LocatorReader("src/main/java/legalhold/selectors/cases/manage_case/case_information/case_information.properties");
    }

    public void ManageCaseTabsNavigation() {
        ManageCaseTabs = driver.getWebDriver().findElements(By.className(locatorReader.getobjectLocator("ManageCaseTabs")));
    }

    public void searchCaseByName(String caseName) {
        try {
            WebElement caseNameColumnFilterBox = driver.getWebDriver().findElement(By.xpath(locatorReader.getobjectLocator("searchCaseNameColumn")));
            wait.until(ExpectedConditions.elementToBeClickable(caseNameColumnFilterBox));
            caseNameColumnFilterBox.sendKeys(caseName);
            String expected_text = "Showing 1 to 1 of 1 entries";
            wait.until(ExpectedConditions.textToBe(By.id(locatorReader.getobjectLocator("rowCount")), expected_text));
        } catch (Exception E) {
            System.out.println("Case Name not found. The exception is: ");
            System.out.println(E.getMessage());
        }
    }

    public void goToEditCase(String caseName) {
        try {
            searchCaseByName(caseName);
            driver.FindElementByXPath(locatorReader.getobjectLocator("btnEditCase")).Click();
            driver.waitForPageToBeReady();
        } catch (Exception E) {
            System.out.println("Edit case icon not found. The exception is: ");
            System.out.println(E.getMessage());
        }
    }

    public void saveCase() throws InterruptedException {
        Element btnCaseSave = driver.FindElementById(reader.getobjectLocator("manageCaseSubmitBtn"));
        driver.scrollingToElementofAPage(btnCaseSave);
        btnCaseSave.waitAndClick(30);
        driver.FindElementById(reader.getobjectLocator("caseSaveModalConfirmOkbutton")).Click();
        String successToast = driver.FindElementByXPath(reader.getobjectLocator("caseSaveSuccessToastMessage")).getText();
        Assert.assertTrue(successToast.contains("Case updated successfully"));
        Thread.sleep(5000);
        driver.waitForPageToBeReady();
    }

    public void setComplianceReminderAsOneTime() {
        WebElement btnComplianceReminderSchedule = driver.getWebDriver().findElement(By.cssSelector(reader.getobjectLocator("btnComplianceReminderSchedule")));
        wait.until(ExpectedConditions.elementToBeClickable(btnComplianceReminderSchedule));
        btnComplianceReminderSchedule.click();
        Element btnOneTime = driver.FindElementByCssSelector(reader.getobjectLocator("btnOneTime"));
        if (!btnOneTime.Selected()) {
            btnOneTime.Click();
        } else {
            System.out.println("Already set as One Time");
        }
        driver.FindElementById(reader.getobjectLocator("btnScheduleModalSubmit")).Click();
    }

    public void setComplianceReminderAsWeeklyWithMaxNumber(int max) {
        WebElement btnComplianceReminderSchedule = driver.getWebDriver().findElement(By.cssSelector(reader.getobjectLocator("btnComplianceReminderSchedule")));
        wait.until(ExpectedConditions.elementToBeClickable(btnComplianceReminderSchedule));
        btnComplianceReminderSchedule.click();
        Element btnWeekly = driver.FindElementByCssSelector(reader.getobjectLocator("btnWeekly"));

        btnWeekly.Click();
        if (!driver.FindElementByCssSelector("input[aria-label=\"Monday\"]").Selected()) {
            driver.FindElementByCssSelector("input[aria-label=\"Monday\"]").waitAndClick(30);
        }
        if (!driver.FindElementByCssSelector("input[aria-label=\"Tuesday\"]").Selected()) {
            driver.FindElementByCssSelector("input[aria-label=\"Tuesday\"]").waitAndClick(30);
        }
        if (!driver.FindElementByCssSelector("input[aria-label=\"Wednesday\"]").Selected()) {
            driver.FindElementByCssSelector("input[aria-label=\"Wednesday\"]").waitAndClick(30);
        }
        if (!driver.FindElementByCssSelector("input[aria-label=\"Thursday\"]").Selected()) {
            driver.FindElementByCssSelector("input[aria-label=\"Thursday\"]").waitAndClick(30);
        }
        if (!driver.FindElementByCssSelector("input[aria-label=\"Friday\"]").Selected()) {
            driver.FindElementByCssSelector("input[aria-label=\"Friday\"]").waitAndClick(30);
        }
        Element btnMax = driver.FindElementByXPath(reader.getobjectLocator("btnMaximumNumberWeekly"));
        if (!btnMax.Selected()) {
            btnMax.Click();
        }
        driver.FindElementByXPath(reader.getobjectLocator("inputMaxNumberWeekly")).Clear();
        driver.FindElementByXPath(reader.getobjectLocator("inputMaxNumberWeekly")).SendKeys(Integer.toString(max));
        driver.FindElementById(reader.getobjectLocator("btnScheduleModalSubmit")).Click();
    }

    public void setComplianceReminderAsWeeklyUnlimited() {
        WebElement btnComplianceReminderSchedule = driver.getWebDriver().findElement(By.cssSelector(reader.getobjectLocator("btnComplianceReminderSchedule")));
        wait.until(ExpectedConditions.elementToBeClickable(btnComplianceReminderSchedule));
        btnComplianceReminderSchedule.click();
        Element btnWeekly = driver.FindElementByCssSelector(reader.getobjectLocator("btnWeekly"));

        btnWeekly.Click();
        if (!driver.FindElementByCssSelector("input[aria-label=\"Monday\"]").Selected()) {
            driver.FindElementByCssSelector("input[aria-label=\"Monday\"]").waitAndClick(30);
        }
        if (!driver.FindElementByCssSelector("input[aria-label=\"Tuesday\"]").Selected()) {
            driver.FindElementByCssSelector("input[aria-label=\"Tuesday\"]").waitAndClick(30);
        }
        if (!driver.FindElementByCssSelector("input[aria-label=\"Wednesday\"]").Selected()) {
            driver.FindElementByCssSelector("input[aria-label=\"Wednesday\"]").waitAndClick(30);
        }
        if (!driver.FindElementByCssSelector("input[aria-label=\"Thursday\"]").Selected()) {
            driver.FindElementByCssSelector("input[aria-label=\"Thursday\"]").waitAndClick(30);
        }
        if (!driver.FindElementByCssSelector("input[aria-label=\"Friday\"]").Selected()) {
            driver.FindElementByCssSelector("input[aria-label=\"Friday\"]").waitAndClick(30);
        }

        List<WebElement> btnUnlimited = driver.getWebDriver().findElements(By.cssSelector(reader.getobjectLocator("btnReminderOccurrenceWeekly")));
        if (!btnUnlimited.get(0).isSelected()) {
            btnUnlimited.get(0).click();
        }
        driver.FindElementById(reader.getobjectLocator("btnScheduleModalSubmit")).Click();
    }

    public void setComplianceReminderAsBiWeeklyUnlimited(String recurringDay) {
        WebElement btnComplianceReminderSchedule = driver.getWebDriver().findElement(By.cssSelector(reader.getobjectLocator("btnComplianceReminderSchedule")));
        wait.until(ExpectedConditions.elementToBeClickable(btnComplianceReminderSchedule));
        btnComplianceReminderSchedule.click();
        Element btnBiTriWeekly = driver.FindElementByCssSelector("input[aria-label='Bi/Tri-Weekly']");
        String str = recurringDay.toLowerCase();
        String recurringDayOfWeeek = StringUtils.capitalize(str);

        if (!btnBiTriWeekly.Selected()) {
            btnBiTriWeekly.Click();
            Element recurringWeekDropdown = driver.FindElementByCssSelector("select[name='selectBox'][aria-label='Select a Recurring week']");
            recurringWeekDropdown.selectFromDropdown().selectByValue("2");
            Element recurringDayDropdown = driver.FindElementByCssSelector("select[name='selectBox'][aria-label='Select Recurring day']");
            recurringDayDropdown.selectFromDropdown().selectByVisibleText(recurringDayOfWeeek);
        } else {
            System.out.println("Already selected as Bi/Tri Weekly");

        }
        List<WebElement> btnUnlimited = driver.getWebDriver().findElements(By.cssSelector(reader.getobjectLocator("btnReminderOccurrenceBiTriWeekly")));
        if (!btnUnlimited.get(0).isSelected()) {
            btnUnlimited.get(0).click();
        }
        driver.FindElementById(reader.getobjectLocator("btnScheduleModalSubmit")).Click();
    }

    public void setComplianceReminderAsBiWeeklyWithMaxNumber(String recurringDay, int max) {
        WebElement btnComplianceReminderSchedule = driver.getWebDriver().findElement(By.cssSelector(reader.getobjectLocator("btnComplianceReminderSchedule")));
        wait.until(ExpectedConditions.elementToBeClickable(btnComplianceReminderSchedule));
        btnComplianceReminderSchedule.click();
        Element btnBiTriWeekly = driver.FindElementByCssSelector("input[aria-label='Bi/Tri-Weekly']");
        String str = recurringDay.toLowerCase();
        String recurringDayOfWeeek = StringUtils.capitalize(str);

        if (!btnBiTriWeekly.Selected()) {
            btnBiTriWeekly.Click();
            Element recurringWeekDropdown = driver.FindElementByCssSelector("select[name='selectBox'][aria-label='Select a Recurring week']");
            recurringWeekDropdown.selectFromDropdown().selectByValue("2");
            Element recurringDayDropdown = driver.FindElementByCssSelector("select[name='selectBox'][aria-label='Select Recurring day']");
            recurringDayDropdown.selectFromDropdown().selectByVisibleText(recurringDayOfWeeek);
        } else {
            System.out.println("Already selected as Bi/Tri Weekly");
        }
//        Element btnMax = driver.FindElementByXPath(reader.getobjectLocator("btnMaximumNumberWeekly"));
        List<WebElement> btnMax = driver.getWebDriver().findElements(By.cssSelector(reader.getobjectLocator("btnReminderOccurrenceBiTriWeekly")));
        if (!btnMax.get(1).isSelected()) {
            btnMax.get(1).click();
        }
        driver.FindElementByXPath(reader.getobjectLocator("inputMaxNumberBiTriWeekly")).Clear();
        driver.FindElementByXPath(reader.getobjectLocator("inputMaxNumberBiTriWeekly")).SendKeys(Integer.toString(max));
        driver.FindElementById(reader.getobjectLocator("btnScheduleModalSubmit")).Click();
    }

    public void setScheduledReminderAsOneTime() {
        WebElement btnScheduledReminderSchedule = driver.getWebDriver().findElement(By.cssSelector(reader.getobjectLocator("btnScheduledReminderSchedule")));
        wait.until(ExpectedConditions.elementToBeClickable(btnScheduledReminderSchedule));
        btnScheduledReminderSchedule.click();
        Element btnOneTime = driver.FindElementByCssSelector(reader.getobjectLocator("btnOneTime"));
        if (!btnOneTime.Selected()) {
            btnOneTime.Click();
        } else {
            System.out.println("Already set as One Time");
        }
        driver.FindElementById(reader.getobjectLocator("btnScheduleModalSubmit")).Click();
    }

    public void setEscalationSchedule(int escalationInterval) {
        String setEscalationInterval = Integer.toString(escalationInterval);
        WebElement btnEscalationSchedule = driver.getWebDriver().findElement(By.cssSelector(reader.getobjectLocator("btnEscalationSchedule")));
        wait.until(ExpectedConditions.elementToBeClickable(btnEscalationSchedule));
        btnEscalationSchedule.click();
        Element setEscalationDropdown = driver.FindElementByCssSelector(reader.getobjectLocator("setEscalationDropdown"));
        if (!setEscalationDropdown.getText().equalsIgnoreCase(setEscalationInterval)) {
            setEscalationDropdown.selectFromDropdown().selectByValue(setEscalationInterval);
        } else {
            System.out.println("Escalation already set as " + setEscalationInterval);
        }
    }

    public void NavigateToCaseInformationTab() {

        ManageCaseTabsNavigation();
        ManageCaseTabs.get(0).click();
        driver.waitForPageToBeReady();
    }

    public void NavigateToCustodiansTab() {

        ManageCaseTabsNavigation();
        ManageCaseTabs.get(1).click();
        driver.waitForPageToBeReady();
    }

    public void NavigateToPreservationTab() {

        ManageCaseTabsNavigation();
        ManageCaseTabs.get(2).click();
        driver.waitForPageToBeReady();
    }

    public void NavigateToSurveysTab() {

        ManageCaseTabsNavigation();
        ManageCaseTabs.get(3).click();
        driver.waitForPageToBeReady();
    }

    public void NavigateToCommunicationsTab() {

        ManageCaseTabsNavigation();
        ManageCaseTabs.get(4).click();
        driver.waitForPageToBeReady();
    }
}

