package legalhold.legalholdpagefactory.cases;

import automationLibrary.Driver;
import automationLibrary.Element;

import automationLibrary.ElementCollection;
import legalhold.setup.BaseModule;
import legalhold.utilities.parse_locators.LocatorReader;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.io.IOException;
import java.util.List;

public class CaseCommunicationFactories extends BaseModule {
    LocatorReader reader;

    public CaseCommunicationFactories(Driver driver) throws IOException {
        super("src/main/java/legalhold/selectors/cases/manage_case/communication/communication_page.properties", driver);
        reader = new LocatorReader("src/main/java/legalhold/selectors/cases/manage_case/case_information/case_information.properties");
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
        Element btnComplianceReminderSchedule = driver.FindElementByCssSelector(locatorReader.getobjectLocator("btnComplianceReminderSchedule"));
        wait.until(ExpectedConditions.elementToBeClickable(btnComplianceReminderSchedule.getWebElement()));
        driver.scrollingToElementofAPage(btnComplianceReminderSchedule);
        btnComplianceReminderSchedule.waitAndClick(30);
        Element btnOneTime = driver.FindElementByCssSelector(reader.getobjectLocator("btnOneTime"));
        if (!btnOneTime.Selected()) {
            btnOneTime.Click();
        } else {
            System.out.println("Already set as One Time");
        }
        driver.FindElementById(reader.getobjectLocator("btnScheduleModalSubmit")).Click();
    }

    public void setComplianceReminderAsWeeklyWithMaxNumber(int max) {
        Element btnComplianceReminderSchedule = driver.FindElementByCssSelector(locatorReader.getobjectLocator("btnComplianceReminderSchedule"));
        wait.until(ExpectedConditions.elementToBeClickable(btnComplianceReminderSchedule.getWebElement()));
        driver.scrollingToElementofAPage(btnComplianceReminderSchedule);
        btnComplianceReminderSchedule.waitAndClick(30);
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
        Element btnComplianceReminderSchedule = driver.FindElementByCssSelector(locatorReader.getobjectLocator("btnComplianceReminderSchedule"));
        wait.until(ExpectedConditions.elementToBeClickable(btnComplianceReminderSchedule.getWebElement()));
        driver.scrollingToElementofAPage(btnComplianceReminderSchedule);
        btnComplianceReminderSchedule.waitAndClick(30);
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
        Element btnComplianceReminderSchedule = driver.FindElementByCssSelector(locatorReader.getobjectLocator("btnComplianceReminderSchedule"));
        wait.until(ExpectedConditions.elementToBeClickable(btnComplianceReminderSchedule.getWebElement()));
        driver.scrollingToElementofAPage(btnComplianceReminderSchedule);
        btnComplianceReminderSchedule.waitAndClick(30);
        Element btnBiTriWeekly = driver.FindElementByCssSelector(reader.getobjectLocator("btnBiTriWeekly"));
        String str = recurringDay.toLowerCase();
        String recurringDayOfWeek = StringUtils.capitalize(str);

        btnBiTriWeekly.Click();
        Element recurringWeekDropdown = driver.FindElementByCssSelector(reader.getobjectLocator("recurringWeekDropdownBiTriWeekly"));
        recurringWeekDropdown.selectFromDropdown().selectByValue("2");
        Element recurringDayDropdown = driver.FindElementByCssSelector(reader.getobjectLocator("recurringDayDropdownBiTriWeekly"));
        recurringDayDropdown.selectFromDropdown().selectByVisibleText(recurringDayOfWeek);

        List<WebElement> btnUnlimited = driver.getWebDriver().findElements(By.cssSelector(reader.getobjectLocator("btnReminderOccurrenceBiTriWeekly")));
        if (!btnUnlimited.get(0).isSelected()) {
            btnUnlimited.get(0).click();
        }
        driver.FindElementById(reader.getobjectLocator("btnScheduleModalSubmit")).Click();
    }

    public void setComplianceReminderAsBiWeeklyWithMaxNumber(String recurringDay, int max) {
        Element btnComplianceReminderSchedule = driver.FindElementByCssSelector(locatorReader.getobjectLocator("btnComplianceReminderSchedule"));
        wait.until(ExpectedConditions.elementToBeClickable(btnComplianceReminderSchedule.getWebElement()));
        driver.scrollingToElementofAPage(btnComplianceReminderSchedule);
        btnComplianceReminderSchedule.waitAndClick(30);
        Element btnBiTriWeekly = driver.FindElementByCssSelector(reader.getobjectLocator("btnBiTriWeekly"));
        String str = recurringDay.toLowerCase();
        String recurringDayOfWeek = StringUtils.capitalize(str);

        btnBiTriWeekly.Click();
        Element recurringWeekDropdown = driver.FindElementByCssSelector(reader.getobjectLocator("recurringWeekDropdownBiTriWeekly"));
        recurringWeekDropdown.selectFromDropdown().selectByValue("2");
        Element recurringDayDropdown = driver.FindElementByCssSelector(reader.getobjectLocator("recurringDayDropdownBiTriWeekly"));
        recurringDayDropdown.selectFromDropdown().selectByVisibleText(recurringDayOfWeek);

        List<WebElement> btnMax = driver.getWebDriver().findElements(By.cssSelector(reader.getobjectLocator("btnReminderOccurrenceBiTriWeekly")));
        if (!btnMax.get(1).isSelected()) {
            btnMax.get(1).click();
        }
        driver.FindElementByXPath(reader.getobjectLocator("inputMaxNumberBiTriWeekly")).Clear();
        driver.FindElementByXPath(reader.getobjectLocator("inputMaxNumberBiTriWeekly")).SendKeys(Integer.toString(max));
        driver.FindElementById(reader.getobjectLocator("btnScheduleModalSubmit")).Click();
    }

    public void setComplianceReminderAsTriWeeklyWithMaxNumber(String recurringDay, int max) {
        Element btnComplianceReminderSchedule = driver.FindElementByCssSelector(locatorReader.getobjectLocator("btnComplianceReminderSchedule"));
        wait.until(ExpectedConditions.elementToBeClickable(btnComplianceReminderSchedule.getWebElement()));
        driver.scrollingToElementofAPage(btnComplianceReminderSchedule);
        btnComplianceReminderSchedule.waitAndClick(30);
        Element btnBiTriWeekly = driver.FindElementByCssSelector(reader.getobjectLocator("btnBiTriWeekly"));
        String str = recurringDay.toLowerCase();
        String recurringDayOfWeek = StringUtils.capitalize(str);

        btnBiTriWeekly.Click();
        Element recurringWeekDropdown = driver.FindElementByCssSelector(reader.getobjectLocator("recurringWeekDropdownBiTriWeekly"));
        recurringWeekDropdown.selectFromDropdown().selectByValue("3");
        Element recurringDayDropdown = driver.FindElementByCssSelector(reader.getobjectLocator("recurringDayDropdownBiTriWeekly"));
        recurringDayDropdown.selectFromDropdown().selectByVisibleText(recurringDayOfWeek);

        List<WebElement> btnMax = driver.getWebDriver().findElements(By.cssSelector(reader.getobjectLocator("btnReminderOccurrenceBiTriWeekly")));
        if (!btnMax.get(1).isSelected()) {
            btnMax.get(1).click();
        }
        driver.FindElementByXPath(reader.getobjectLocator("inputMaxNumberBiTriWeekly")).Clear();
        driver.FindElementByXPath(reader.getobjectLocator("inputMaxNumberBiTriWeekly")).SendKeys(Integer.toString(max));
        driver.FindElementById(reader.getobjectLocator("btnScheduleModalSubmit")).Click();
    }

    public void setComplianceReminderAsTriWeeklyUnlimited(String recurringDay) {
        Element btnComplianceReminderSchedule = driver.FindElementByCssSelector(locatorReader.getobjectLocator("btnComplianceReminderSchedule"));
        wait.until(ExpectedConditions.elementToBeClickable(btnComplianceReminderSchedule.getWebElement()));
        driver.scrollingToElementofAPage(btnComplianceReminderSchedule);
        btnComplianceReminderSchedule.waitAndClick(30);
        Element btnBiTriWeekly = driver.FindElementByCssSelector(reader.getobjectLocator("btnBiTriWeekly"));
        String str = recurringDay.toLowerCase();
        String recurringDayOfWeek = StringUtils.capitalize(str);

        btnBiTriWeekly.Click();
        Element recurringWeekDropdown = driver.FindElementByCssSelector(reader.getobjectLocator("recurringWeekDropdownBiTriWeekly"));
        recurringWeekDropdown.selectFromDropdown().selectByValue("3");
        Element recurringDayDropdown = driver.FindElementByCssSelector(reader.getobjectLocator("recurringDayDropdownBiTriWeekly"));
        recurringDayDropdown.selectFromDropdown().selectByVisibleText(recurringDayOfWeek);

        List<WebElement> btnUnlimited = driver.getWebDriver().findElements(By.cssSelector(reader.getobjectLocator("btnReminderOccurrenceBiTriWeekly")));
        if (!btnUnlimited.get(0).isSelected()) {
            btnUnlimited.get(0).click();
        }
        driver.FindElementById(reader.getobjectLocator("btnScheduleModalSubmit")).Click();
    }

    public void setComplianceReminderAsMonthlyWithMaxNumber(int recurringMonth, int recurringDay, int max) {
        Element btnComplianceReminderSchedule = driver.FindElementByCssSelector(locatorReader.getobjectLocator("btnComplianceReminderSchedule"));
        wait.until(ExpectedConditions.elementToBeClickable(btnComplianceReminderSchedule.getWebElement()));
        driver.scrollingToElementofAPage(btnComplianceReminderSchedule);
        btnComplianceReminderSchedule.waitAndClick(30);
        Element btnMonthly = driver.FindElementByCssSelector(reader.getobjectLocator("btnMonthly"));

        btnMonthly.Click();
        Element recurringMonthDropdown = driver.FindElementByCssSelector(reader.getobjectLocator("recurringMonthDropdown"));
        recurringMonthDropdown.selectFromDropdown().selectByValue(Integer.toString(recurringMonth));
        List<WebElement> recurringDayDropdown = driver.getWebDriver().findElements(By.cssSelector(reader.getobjectLocator("recurringDayDropdownMonthly")));
        Select selectFromDropdown = new Select(recurringDayDropdown.get(0));
        selectFromDropdown.selectByValue(Integer.toString(recurringDay));
        Element weekdaysOnlyCheckbox = driver.FindElementByCssSelector(reader.getobjectLocator("checkboxWeekdaysOnlyMonthly"));
        if (!weekdaysOnlyCheckbox.Selected()) {
            weekdaysOnlyCheckbox.Click();
        }

        List<WebElement> btnMax = driver.getWebDriver().findElements(By.cssSelector(reader.getobjectLocator("btnReminderOccurrenceMonthly")));
        if (!btnMax.get(1).isSelected()) {
            btnMax.get(1).click();
        }
        driver.FindElementByXPath(reader.getobjectLocator("inputMaxNumberMonthly")).Clear();
        driver.FindElementByXPath(reader.getobjectLocator("inputMaxNumberMonthly")).SendKeys(Integer.toString(max));
        driver.FindElementById(reader.getobjectLocator("btnScheduleModalSubmit")).Click();
    }

    public void setComplianceReminderAsMonthlyUnlimited(int recurringMonth, int recurringDay) {
        Element btnComplianceReminderSchedule = driver.FindElementByCssSelector(locatorReader.getobjectLocator("btnComplianceReminderSchedule"));
        wait.until(ExpectedConditions.elementToBeClickable(btnComplianceReminderSchedule.getWebElement()));
        driver.scrollingToElementofAPage(btnComplianceReminderSchedule);
        btnComplianceReminderSchedule.waitAndClick(30);
        Element btnMonthly = driver.FindElementByCssSelector(reader.getobjectLocator("btnMonthly"));

        btnMonthly.Click();
        Element recurringMonthDropdown = driver.FindElementByCssSelector(reader.getobjectLocator("recurringMonthDropdown"));
        recurringMonthDropdown.selectFromDropdown().selectByValue(Integer.toString(recurringMonth));
        List<WebElement> recurringDayDropdown = driver.getWebDriver().findElements(By.cssSelector(reader.getobjectLocator("recurringDayDropdownMonthly")));
        Select selectFromDropdown = new Select(recurringDayDropdown.get(0));
        selectFromDropdown.selectByValue(Integer.toString(recurringDay));
        Element weekdaysOnlyCheckbox = driver.FindElementByCssSelector(reader.getobjectLocator("checkboxWeekdaysOnlyMonthly"));
        if (!weekdaysOnlyCheckbox.Selected()) {
            weekdaysOnlyCheckbox.Click();
        }

        List<WebElement> btnUnlimited = driver.getWebDriver().findElements(By.cssSelector(reader.getobjectLocator("btnReminderOccurrenceMonthly")));
        if (!btnUnlimited.get(0).isSelected()) {
            btnUnlimited.get(0).click();
        }
        driver.FindElementById(reader.getobjectLocator("btnScheduleModalSubmit")).Click();
    }


    public void setEscalationSchedule(int escalationInterval) {
        String setEscalationInterval = Integer.toString(escalationInterval);
        WebElement btnEscalationSchedule = driver.getWebDriver().findElement(By.cssSelector(locatorReader.getobjectLocator("btnEscalationSchedule")));
        wait.until(ExpectedConditions.elementToBeClickable(btnEscalationSchedule));
        btnEscalationSchedule.click();
        Element setEscalationDropdown = driver.FindElementByCssSelector(reader.getobjectLocator("setEscalationDropdown"));
        if (!setEscalationDropdown.getText().equalsIgnoreCase(setEscalationInterval)) {
            setEscalationDropdown.selectFromDropdown().selectByValue(setEscalationInterval);
        } else {
            System.out.println("Escalation is already set as " + setEscalationInterval);
        }
        driver.FindElementById(reader.getobjectLocator("btnScheduleModalSubmit")).Click();
    }

    public void setScheduledReminderAsOneTime() {
        Element btnScheduledReminderSchedule = driver.FindElementByCssSelector(locatorReader.getobjectLocator("btnScheduledReminderSchedule"));
        wait.until(ExpectedConditions.elementToBeClickable(btnScheduledReminderSchedule.getWebElement()));
        driver.scrollingToElementofAPage(btnScheduledReminderSchedule);
        btnScheduledReminderSchedule.waitAndClick(30);
        Element btnOneTime = driver.FindElementByCssSelector(reader.getobjectLocator("btnOneTime"));
        if (!btnOneTime.Selected()) {
            btnOneTime.Click();
        } else {
            System.out.println("Already set as One Time");
        }
        driver.FindElementById(reader.getobjectLocator("btnScheduleModalSubmit")).Click();
    }

    public void setScheduledReminderAsWeekly() {
        Element btnScheduledReminderSchedule = driver.FindElementByCssSelector(locatorReader.getobjectLocator("btnScheduledReminderSchedule"));
        wait.until(ExpectedConditions.elementToBeClickable(btnScheduledReminderSchedule.getWebElement()));
        driver.scrollingToElementofAPage(btnScheduledReminderSchedule);
        btnScheduledReminderSchedule.waitAndClick(30);
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
        driver.FindElementById(reader.getobjectLocator("btnScheduleModalSubmit")).Click();
    }

    public void setScheduledReminderAsBiWeekly(String recurringDay) {
        Element btnScheduledReminderSchedule = driver.FindElementByCssSelector(locatorReader.getobjectLocator("btnScheduledReminderSchedule"));
        wait.until(ExpectedConditions.elementToBeClickable(btnScheduledReminderSchedule.getWebElement()));
        driver.scrollingToElementofAPage(btnScheduledReminderSchedule);
        btnScheduledReminderSchedule.waitAndClick(30);
        Element btnBiTriWeekly = driver.FindElementByCssSelector(reader.getobjectLocator("btnBiTriWeekly"));
        String str = recurringDay.toLowerCase();
        String recurringDayOfWeek = StringUtils.capitalize(str);

        btnBiTriWeekly.Click();
        Element recurringWeekDropdown = driver.FindElementByCssSelector(reader.getobjectLocator("recurringWeekDropdownBiTriWeekly"));
        recurringWeekDropdown.selectFromDropdown().selectByValue("2");
        Element recurringDayDropdown = driver.FindElementByCssSelector(reader.getobjectLocator("recurringDayDropdownBiTriWeekly"));
        recurringDayDropdown.selectFromDropdown().selectByVisibleText(recurringDayOfWeek);

        driver.FindElementById(reader.getobjectLocator("btnScheduleModalSubmit")).Click();
    }

    public void setScheduledReminderAsTriWeekly(String recurringDay) {
        Element btnScheduledReminderSchedule = driver.FindElementByCssSelector(locatorReader.getobjectLocator("btnScheduledReminderSchedule"));
        wait.until(ExpectedConditions.elementToBeClickable(btnScheduledReminderSchedule.getWebElement()));
        driver.scrollingToElementofAPage(btnScheduledReminderSchedule);
        btnScheduledReminderSchedule.waitAndClick(30);
        Element btnBiTriWeekly = driver.FindElementByCssSelector(reader.getobjectLocator("btnBiTriWeekly"));
        String str = recurringDay.toLowerCase();
        String recurringDayOfWeek = StringUtils.capitalize(str);

        btnBiTriWeekly.Click();
        Element recurringWeekDropdown = driver.FindElementByCssSelector(reader.getobjectLocator("recurringWeekDropdownBiTriWeekly"));
        recurringWeekDropdown.selectFromDropdown().selectByValue("3");
        Element recurringDayDropdown = driver.FindElementByCssSelector(reader.getobjectLocator("recurringDayDropdownBiTriWeekly"));
        recurringDayDropdown.selectFromDropdown().selectByVisibleText(recurringDayOfWeek);

        driver.FindElementById(reader.getobjectLocator("btnScheduleModalSubmit")).Click();

    }

    public void setScheduledReminderAsMonthly(int recurringMonth, int recurringDay) {
        Element btnScheduledReminderSchedule = driver.FindElementByCssSelector(locatorReader.getobjectLocator("btnScheduledReminderSchedule"));
        wait.until(ExpectedConditions.elementToBeClickable(btnScheduledReminderSchedule.getWebElement()));
        driver.scrollingToElementofAPage(btnScheduledReminderSchedule);
        btnScheduledReminderSchedule.waitAndClick(30);
        Element btnMonthly = driver.FindElementByCssSelector(reader.getobjectLocator("btnMonthly"));

        btnMonthly.Click();
        Element recurringMonthDropdown = driver.FindElementByCssSelector(reader.getobjectLocator("recurringMonthDropdown"));
        recurringMonthDropdown.selectFromDropdown().selectByValue(Integer.toString(recurringMonth));
        ElementCollection recurringDayDropdown = driver.FindElementsByCssSelector(reader.getobjectLocator("recurringDayDropdownMonthly"));
        recurringDayDropdown.getElementByIndex(0).selectFromDropdown().selectByValue(Integer.toString(recurringDay));
        Element weekdaysOnlyCheckbox = driver.FindElementByCssSelector(reader.getobjectLocator("checkboxWeekdaysOnlyMonthly"));
        if (!weekdaysOnlyCheckbox.Selected()) {
            weekdaysOnlyCheckbox.Click();
        }

        driver.FindElementById(reader.getobjectLocator("btnScheduleModalSubmit")).Click();
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
//        driver.scrollingToElementofAPage(btnCreateCustodianCommunication);
    }

    public void goToCreateNonCustodianCommunicationPage() {
        WebElement btnCreateNonCustodianCommunication = driver.getWebDriver().findElement(By.xpath(locatorReader.getobjectLocator("btnCreateNonCustodianCommunication")));
        wait.until(ExpectedConditions.elementToBeClickable(btnCreateNonCustodianCommunication));
        btnCreateNonCustodianCommunication.click();
        driver.waitForPageToBeReady();
        driver.getWebDriver().navigate().refresh();
        driver.waitForPageToBeReady();
//        driver.scrollingToElementofAPage(btnCreateNonCustodianCommunication);
    }

    public void goToCreateReleaseCommunicationPage() {
        WebElement btnCreateReleaseCommunication = driver.getWebDriver().findElement(By.xpath(locatorReader.getobjectLocator("btnCreateReleaseCommunication")));
        wait.until(ExpectedConditions.elementToBeClickable(btnCreateReleaseCommunication));
        btnCreateReleaseCommunication.click();
        driver.waitForPageToBeReady();
        driver.getWebDriver().navigate().refresh();
        driver.waitForPageToBeReady();
//        driver.scrollingToElementofAPage(btnCreateReleaseCommunication);
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

    public void backToCommunicationTab() {
        WebElement backToCommunicationTableBreadcrumb = driver.getWebDriver().findElement(By.id(locatorReader.getobjectLocator("backToCommunicationTableBreadcrumb")));
        wait.until(ExpectedConditions.visibilityOf(backToCommunicationTableBreadcrumb));
        backToCommunicationTableBreadcrumb.click();
        driver.waitForPageToBeReady();

    }

    public void addMailToRecipients() throws IOException, InterruptedException {


        Element addMailToRecipientButton = driver.FindElementByClassName(locatorReader.getobjectLocator("addMailtoRecipientButton"));
        wait.until(ExpectedConditions.elementToBeClickable(addMailToRecipientButton.getWebElement()));
        addMailToRecipientButton.Click();

        Element availableTableEmployeeId = driver.FindElementByXPath(locatorReader.getobjectLocator("availableTableEmployeeId"));
        availableTableEmployeeId.Clear();
        availableTableEmployeeId.Click();
        availableTableEmployeeId.SendKeys("SLH-1");


        Element selectAllCheckBox = driver.FindElementByCssSelector(locatorReader.getobjectLocator("selectAllCheckbox"));
        wait.until(ExpectedConditions.elementToBeClickable(selectAllCheckBox.getWebElement()));
        Thread.sleep(5000);
        selectAllCheckBox.Click();

        Element addCustodiansButton = driver.FindElementById(locatorReader.getobjectLocator("addCustodiansButton"));
        addCustodiansButton.Click();


        Element confirmationModalOkButton = driver.FindElementById(locatorReader.getobjectLocator("mailtoConfirmationOkBtn"));
        wait.until(ExpectedConditions.elementToBeClickable(confirmationModalOkButton.getWebElement()));
        confirmationModalOkButton.Click();

        Element globalSaveButtonManageRecipients = driver.FindElementById(locatorReader.getobjectLocator("manageRecipientsGlobalSveBtn"));
        driver.scrollingToElementofAPage(globalSaveButtonManageRecipients);
        wait.until(ExpectedConditions.elementToBeClickable(globalSaveButtonManageRecipients.getWebElement()));
        Thread.sleep(3000);
        globalSaveButtonManageRecipients.Click();

        Element confirmationOkButton = driver.FindElementById(locatorReader.getobjectLocator("manageRecipientsGlobalSaveOkBtn"));
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
            Element seriesNameColumnFilterBox = driver.FindElementByXPath(locatorReader.getobjectLocator("seriesNameColumnFilterBox"));
            wait.until(ExpectedConditions.elementToBeClickable(seriesNameColumnFilterBox.getWebElement()));
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locatorReader.getobjectLocator("seriesNameColumnFilterBox"))));
            seriesNameColumnFilterBox.SendKeys(seriesName);
            Element selectCommunicationTypeAsAcknowledgment = driver.FindElementByCssSelector(locatorReader.getobjectLocator("selectCommunicationType"));
            selectCommunicationTypeAsAcknowledgment.selectFromDropdown().selectByValue("Acknowledgement");
            String expected_text = "Showing 1 to 1 of 1 entries";
            Thread.sleep(1000);
            wait.until(ExpectedConditions.textToBe(By.id(locatorReader.getobjectLocator("rowCount")), expected_text));
            Thread.sleep(3000);
        } catch (Exception E) {
            System.out.println("Case Name not found. The exception is: ");
            System.out.println(E.getMessage());
        }
    }

    public void searchNonCustodianTypeSeriesByName(String seriesName) {
        try {
            Element seriesNameColumnFilterBox = driver.FindElementByXPath(locatorReader.getobjectLocator("seriesNameColumnFilterBox"));
            wait.until(ExpectedConditions.elementToBeClickable(seriesNameColumnFilterBox.getWebElement()));
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locatorReader.getobjectLocator("seriesNameColumnFilterBox"))));
            seriesNameColumnFilterBox.SendKeys(seriesName);
            Element selectCommunicationTypeAsNotice = driver.FindElementByCssSelector(locatorReader.getobjectLocator("selectCommunicationType"));
            selectCommunicationTypeAsNotice.selectFromDropdown().selectByValue("Notice");
            String expected_text = "Showing 1 to 1 of 1 entries";
            Thread.sleep(1000);
            wait.until(ExpectedConditions.textToBe(By.id(locatorReader.getobjectLocator("rowCount")), expected_text));
            Thread.sleep(3000);
        } catch (Exception E) {
            System.out.println("Case Name not found. The exception is: ");
            System.out.println(E.getMessage());
        }
    }

    public void searchReleaseTypeSeriesByName(String seriesName) {
        try {
            Element seriesNameColumnFilterBox = driver.FindElementByXPath(locatorReader.getobjectLocator("seriesNameColumnFilterBox"));
            wait.until(ExpectedConditions.elementToBeClickable(seriesNameColumnFilterBox.getWebElement()));
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locatorReader.getobjectLocator("seriesNameColumnFilterBox"))));
            seriesNameColumnFilterBox.SendKeys(seriesName);
            Thread.sleep(3000);
        } catch (Exception E) {
            System.out.println("Case Name not found. The exception is: ");
            System.out.println(E.getMessage());
        }
    }

    public void goToEditCustodianCommunicationPage(String seriesName) throws InterruptedException {
        driver.waitForPageToBeReady();
        Thread.sleep(4000);
        searchCustodianTypeSeriesByName(seriesName);
        Element btnEditSeries = driver.FindElementByCssSelector(locatorReader.getobjectLocator("btnEditCommunication"));
        wait.until(ExpectedConditions.elementToBeClickable(btnEditSeries.getWebElement()));
        Thread.sleep(5000);
        btnEditSeries.waitAndClick(30);
        driver.waitForPageToBeReady();
    }

    public void goToEditNonCustodianCommunicationPage(String seriesName) throws InterruptedException {
        driver.waitForPageToBeReady();
        Thread.sleep(4000);
        searchNonCustodianTypeSeriesByName(seriesName);
        Element btnEditSeries = driver.FindElementByCssSelector(locatorReader.getobjectLocator("btnEditCommunication"));
        wait.until(ExpectedConditions.elementToBeClickable(btnEditSeries.getWebElement()));
        Thread.sleep(5000);
        btnEditSeries.waitAndClick(30);
        driver.waitForPageToBeReady();
    }

    public void goToEditReleaseCommunicationPage(String seriesName) throws InterruptedException {
        driver.waitForPageToBeReady();
        Thread.sleep(4000);
        searchReleaseTypeSeriesByName(seriesName);
        Element btnEditSeries = driver.FindElementByCssSelector(locatorReader.getobjectLocator("btnEditCommunication"));
        wait.until(ExpectedConditions.elementToBeClickable(btnEditSeries.getWebElement()));
        Thread.sleep(5000);
        btnEditSeries.waitAndClick(30);
        driver.waitForPageToBeReady();
    }

    /* This method will keep refreshing the communication tab until a date+time stamp is visible on the Acknowledgment
    email row of that series. After it is visible, it will match the time stamp with the Communication Status table
    and will assert if the communication series name field is disabled or not.
    */
    public void verifyPostSendForCustodianSeries(String seriesName) throws InterruptedException {
        try {
            searchCustodianTypeSeriesByName(seriesName);
            Element lastSentDateCell = driver.FindElementByXPath("//table[@id='id-communication']/tbody/tr[1]/td[6]");
            wait.until(ExpectedConditions.visibilityOf(lastSentDateCell.getWebElement()));
            String lastSentDate = lastSentDateCell.getText();
            Element seriesNameColumnFilterBox = driver.FindElementByXPath(locatorReader.getobjectLocator("seriesNameColumnFilterBox"));
            wait.until(ExpectedConditions.elementToBeClickable(seriesNameColumnFilterBox.getWebElement()));
            seriesNameColumnFilterBox.Clear();
            while (lastSentDate.equalsIgnoreCase("")) {
                driver.getWebDriver().navigate().refresh();
                Thread.sleep(8000);
                driver.waitForPageToBeReady();
                searchCustodianTypeSeriesByName(seriesName);

                Element lastSentDateCellAfterRefresh = driver.FindElementByXPath("//table[@id='id-communication']/tbody/tr[1]/td[6]");
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='id-communication']/tbody/tr[1]/td[6]")));
                lastSentDate = lastSentDateCellAfterRefresh.getText();
                System.out.println("Last Sent Date is: " + lastSentDate);
            }
            System.out.println("Final Last Sent Date is: " + lastSentDate);
            driver.getWebDriver().navigate().refresh();
            driver.waitForPageToBeReady();
            goToEditCustodianCommunicationPage(seriesName);
            Element inputSeriesName = driver.FindElementById(locatorReader.getobjectLocator("inputSeriesName"));
            Element acknowledgmentSentDateTableCell = driver.FindElementByCssSelector("td[communication-schedule-type='0']");
            String acknowledgmentSentDate = acknowledgmentSentDateTableCell.getText();
            if (!inputSeriesName.Enabled() && acknowledgmentSentDate.equalsIgnoreCase(lastSentDate)) {
                System.out.println("The communication series: " + seriesName + ", is in Post-Send mode");
            }
        } catch (Exception e) {
            System.out.println("The communication series: " + seriesName + ", is NOT in Post-Send mode");
            System.out.println(e.getMessage());
        }
    }

    public void verifyPostSendForNonCustodianSeries(String seriesName) throws InterruptedException {
        try {
            searchNonCustodianTypeSeriesByName(seriesName);
            Element lastSentDateCell = driver.FindElementByXPath("//table[@id='id-communication']/tbody/tr[1]/td[6]");
            wait.until(ExpectedConditions.visibilityOf(lastSentDateCell.getWebElement()));
            String lastSentDate = lastSentDateCell.getText();
            Element seriesNameColumnFilterBox = driver.FindElementByXPath(locatorReader.getobjectLocator("seriesNameColumnFilterBox"));
            wait.until(ExpectedConditions.elementToBeClickable(seriesNameColumnFilterBox.getWebElement()));
            seriesNameColumnFilterBox.Clear();
            while (lastSentDate.equalsIgnoreCase("")) {
                driver.getWebDriver().navigate().refresh();
                Thread.sleep(8000);
                driver.waitForPageToBeReady();
                searchNonCustodianTypeSeriesByName(seriesName);

                Element lastSentDateCellAfterRefresh = driver.FindElementByXPath("//table[@id='id-communication']/tbody/tr[1]/td[6]");
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='id-communication']/tbody/tr[1]/td[6]")));
                lastSentDate = lastSentDateCellAfterRefresh.getText();
                System.out.println("Last Sent Date is: " + lastSentDate);
            }
            System.out.println("Final Last Sent Date is: " + lastSentDate);
            driver.getWebDriver().navigate().refresh();
            driver.waitForPageToBeReady();
            goToEditNonCustodianCommunicationPage(seriesName);
            Element inputSeriesName = driver.FindElementById(locatorReader.getobjectLocator("inputSeriesName"));
            Element acknowledgmentSentDateTableCell = driver.FindElementByCssSelector("td[communication-schedule-type='0']");
            String acknowledgmentSentDate = acknowledgmentSentDateTableCell.getText();
            if (!inputSeriesName.Enabled() && acknowledgmentSentDate.equalsIgnoreCase(lastSentDate)) {
                System.out.println("The communication series: " + seriesName + ", is in Post-Send mode");
            }
        } catch (Exception e) {
            System.out.println("The communication series: " + seriesName + ", is NOT in Post-Send mode");
            System.out.println(e.getMessage());
        }
    }

    public void verifyPostSendForReleaseSeries(String seriesName) throws InterruptedException {
        try {
            searchReleaseTypeSeriesByName(seriesName);
            Element lastSentDateCell = driver.FindElementByXPath("//table[@id='id-communication']/tbody/tr[1]/td[6]");
            wait.until(ExpectedConditions.visibilityOf(lastSentDateCell.getWebElement()));
            String lastSentDate = lastSentDateCell.getText();
            Element seriesNameColumnFilterBox = driver.FindElementByXPath(locatorReader.getobjectLocator("seriesNameColumnFilterBox"));
            wait.until(ExpectedConditions.elementToBeClickable(seriesNameColumnFilterBox.getWebElement()));
            seriesNameColumnFilterBox.Clear();
            while (lastSentDate.equalsIgnoreCase("")) {
                driver.getWebDriver().navigate().refresh();
                Thread.sleep(8000);
                driver.waitForPageToBeReady();
                searchReleaseTypeSeriesByName(seriesName);

                Element lastSentDateCellAfterRefresh = driver.FindElementByXPath("//table[@id='id-communication']/tbody/tr[1]/td[6]");
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='id-communication']/tbody/tr[1]/td[6]")));
                lastSentDate = lastSentDateCellAfterRefresh.getText();
                System.out.println("Last Sent Date is: " + lastSentDate);
            }
            System.out.println("Final Last Sent Date is: " + lastSentDate);
            driver.getWebDriver().navigate().refresh();
            driver.waitForPageToBeReady();
            goToEditReleaseCommunicationPage(seriesName);
            Element inputSeriesName = driver.FindElementById(locatorReader.getobjectLocator("inputSeriesName"));
            Element acknowledgmentSentDateTableCell = driver.FindElementByCssSelector("td[communication-schedule-type='0']");
            String acknowledgmentSentDate = acknowledgmentSentDateTableCell.getText();
            if (!inputSeriesName.Enabled() && acknowledgmentSentDate.equalsIgnoreCase(lastSentDate)) {
                System.out.println("The communication series: " + seriesName + ", is in Post-Send mode");
            }
        } catch (Exception e) {
            System.out.println("The communication series: " + seriesName + ", is NOT in Post-Send mode");
            System.out.println(e.getMessage());
        }
    }


}
