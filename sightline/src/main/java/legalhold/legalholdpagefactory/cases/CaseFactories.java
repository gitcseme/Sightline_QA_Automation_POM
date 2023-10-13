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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public void populateCaseFields() {
        String[] allCaseFields = reader.getArray("caseFields");

        for (int i = 0; i < allCaseFields.length; i++) {
            Element individualCaseField = driver.FindElementById(allCaseFields[i]);

            if (allCaseFields[i].contains("Date")) {
                individualCaseField.Clear();
                SimpleDateFormat formatDate = new SimpleDateFormat("yyyy/MM/dd");
                String formattedDate = formatDate.format(faker.date().birthday());
                individualCaseField.SendKeys(formattedDate);
            } else if (allCaseFields[i].contains("Email")) {
                individualCaseField.Clear();
                individualCaseField.SendKeys(faker.internet().emailAddress());
            } else {
                individualCaseField.Clear();
                individualCaseField.SendKeys(faker.lorem().sentence(3));
            }
        }
    }

    public void searchCaseByName(String caseName) {
        try {
            WebElement caseNameColumnFilterBox = driver.getWebDriver().findElement(By.xpath(locatorReader.getobjectLocator("searchCaseNameColumn")));
            wait.until(ExpectedConditions.elementToBeClickable(caseNameColumnFilterBox));
            caseNameColumnFilterBox.clear();
            caseNameColumnFilterBox.sendKeys(caseName);
            String expected_text = "Showing 1 to 1 of 1 entries";
            Thread.sleep(3000);
            wait.until(ExpectedConditions.textToBe(By.id(locatorReader.getobjectLocator("rowCount")), expected_text));
        } catch (Exception E) {
            System.out.println("Case Name not found. The exception is: ");
            System.out.println(E.getMessage());
        }
    }

    public void searchNonExistentCaseByName(String caseName) {
        try {
            WebElement caseNameColumnFilterBox = driver.getWebDriver().findElement(By.xpath(locatorReader.getobjectLocator("searchCaseNameColumn")));
            wait.until(ExpectedConditions.elementToBeClickable(caseNameColumnFilterBox));
            caseNameColumnFilterBox.sendKeys(caseName);
            String expected_text = "Showing 0 to 0 of 0 entries";
            Thread.sleep(3000);
            wait.until(ExpectedConditions.textToBe(By.id(locatorReader.getobjectLocator("rowCount")), expected_text));
            Thread.sleep(2000);
        } catch (Exception E) {
            System.out.println("Case Name not found. The exception is: ");
            System.out.println(E.getMessage());
        }
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
            Element availableEmployeeIDFilterBox = driver.FindElementByCssSelector("table[id='id-manageCustodianDataTable'] thead tr th input[placeholder='Search Employee ID']");
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

    public void goToEditCase(String caseName) {
        try {
            searchCaseByName(caseName);
            driver.FindElementByXPath(locatorReader.getobjectLocator("btnEditCase")).waitAndClick(30);
            Thread.sleep(3000);
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
        Element btnComplianceReminderSchedule = driver.FindElementByCssSelector(reader.getobjectLocator("btnComplianceReminderSchedule"));
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
        Element btnComplianceReminderSchedule = driver.FindElementByCssSelector(reader.getobjectLocator("btnComplianceReminderSchedule"));
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
        Element btnComplianceReminderSchedule = driver.FindElementByCssSelector(reader.getobjectLocator("btnComplianceReminderSchedule"));
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
        Element btnComplianceReminderSchedule = driver.FindElementByCssSelector(reader.getobjectLocator("btnComplianceReminderSchedule"));
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
        Element btnComplianceReminderSchedule = driver.FindElementByCssSelector(reader.getobjectLocator("btnComplianceReminderSchedule"));
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

    public void setComplianceReminderAsCustomIntervalWithMaxNumber(int reminderInterval, int max) {

        Element btnComplianceReminderSchedule = driver.FindElementByCssSelector(reader.getobjectLocator("btnComplianceReminderSchedule"));
        wait.until(ExpectedConditions.elementToBeClickable(btnComplianceReminderSchedule.getWebElement()));

        driver.scrollingToElementofAPage(btnComplianceReminderSchedule);
        btnComplianceReminderSchedule.waitAndClick(30);

        Element customIntervalRadioButton = driver.FindElementByXPath(reader.getobjectLocator("customIntervalRadioButton"));

        String reminderIntervalValue = Integer.toString(reminderInterval);


        customIntervalRadioButton.Click();
        Element reminderIntervalDropdown = driver.FindElementByXPath(reader.getobjectLocator("reminderIntervalDropdown"));
        reminderIntervalDropdown.selectFromDropdown().selectByValue(reminderIntervalValue);

        Element weekDaysCheckbox = driver.FindElementByXPath(reader.getobjectLocator("weekDaysCheckbox"));
        weekDaysCheckbox.Click();


        List<WebElement> btnMax = driver.getWebDriver().findElements(By.cssSelector(reader.getobjectLocator("btnReminderOccurrenceCustomInterval")));
        if (!btnMax.get(1).isSelected()) {
            btnMax.get(1).click();
        }
        driver.FindElementByCssSelector(reader.getobjectLocator("inputMaxNumberCustomInterval")).Clear();
        driver.FindElementByCssSelector(reader.getobjectLocator("inputMaxNumberCustomInterval")).SendKeys(Integer.toString(max));
        driver.FindElementById(reader.getobjectLocator("btnScheduleModalSubmit")).Click();
    }

    public void setComplianceReminderAsCustomIntervalWithUnlimited(int reminderInterval) {

        Element btnComplianceReminderSchedule = driver.FindElementByCssSelector(reader.getobjectLocator("btnComplianceReminderSchedule"));
        wait.until(ExpectedConditions.elementToBeClickable(btnComplianceReminderSchedule.getWebElement()));

        driver.scrollingToElementofAPage(btnComplianceReminderSchedule);
        btnComplianceReminderSchedule.waitAndClick(30);

        Element customIntervalRadioButton = driver.FindElementByXPath(reader.getobjectLocator("customIntervalRadioButton"));

        String reminderIntervalValue = Integer.toString(reminderInterval);


        customIntervalRadioButton.Click();
        Element reminderIntervalDropdown = driver.FindElementByXPath(reader.getobjectLocator("reminderIntervalDropdown"));

        wait.until(ExpectedConditions.elementToBeClickable(reminderIntervalDropdown.getWebElement()));
        reminderIntervalDropdown.selectFromDropdown().selectByValue(reminderIntervalValue);

        Element weekDaysCheckbox = driver.FindElementByXPath(reader.getobjectLocator("weekDaysCheckbox"));
        weekDaysCheckbox.Click();


        List<WebElement> btnUnlimited = driver.getWebDriver().findElements(By.cssSelector(reader.getobjectLocator("btnReminderOccurrenceCustomInterval")));
        if (!btnUnlimited.get(0).isSelected()) {
            btnUnlimited.get(0).click();
        }
        driver.FindElementById(reader.getobjectLocator("btnScheduleModalSubmit")).waitAndClick(40);
    }


    public void setComplianceReminderAsTriWeeklyWithMaxNumber(String recurringDay, int max) {
        Element btnComplianceReminderSchedule = driver.FindElementByCssSelector(reader.getobjectLocator("btnComplianceReminderSchedule"));
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
        Element btnComplianceReminderSchedule = driver.FindElementByCssSelector(reader.getobjectLocator("btnComplianceReminderSchedule"));
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
        Element btnComplianceReminderSchedule = driver.FindElementByCssSelector(reader.getobjectLocator("btnComplianceReminderSchedule"));
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
        Element btnComplianceReminderSchedule = driver.FindElementByCssSelector(reader.getobjectLocator("btnComplianceReminderSchedule"));
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

    public void setScheduledReminderAsOneTime() {
        Element btnScheduledReminderSchedule = driver.FindElementByCssSelector(reader.getobjectLocator("btnScheduledReminderSchedule"));
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
        Element btnScheduledReminderSchedule = driver.FindElementByCssSelector(reader.getobjectLocator("btnScheduledReminderSchedule"));
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
        Element btnScheduledReminderSchedule = driver.FindElementByCssSelector(reader.getobjectLocator("btnScheduledReminderSchedule"));
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
        Element btnScheduledReminderSchedule = driver.FindElementByCssSelector(reader.getobjectLocator("btnScheduledReminderSchedule"));
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
        Element btnScheduledReminderSchedule = driver.FindElementByCssSelector(reader.getobjectLocator("btnScheduledReminderSchedule"));
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

    public void setEscalationSchedule(int escalationInterval) {
        String setEscalationInterval = Integer.toString(escalationInterval);
        WebElement btnEscalationSchedule = driver.getWebDriver().findElement(By.cssSelector(reader.getobjectLocator("btnEscalationSchedule")));
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

    public int getColumnIndexFromDataTable(String headerName) {
        var table = driver.FindElementById("id-CaseTable");
        wait.until(ExpectedConditions.elementToBeClickable(table.getWebElement()));
        int columnIndex = 0;

        var headerList = driver.FindElementsByXPath("//table[@id='id-CaseTable']//thead/tr[1]/th");
        for (int i = 0; i < headerList.size(); i++) {
            if (headerList.getElementByIndex(i).getText().equalsIgnoreCase(headerName)) {
                columnIndex = i + 1;
                break;
            }
        }

        if (columnIndex == 0) {
            driver.FindElementById(locatorReader.getobjectLocator("btnColumnSetup")).waitAndClick(30);
            var availableColumnList = driver.FindElementsByXPath("//tbody[@id='id-tablebody-available-CaseTable']/tr/td[2]");
            for (int i = 0; i < availableColumnList.size(); i++) {

                if (availableColumnList.getElementByIndex(i).getText().equalsIgnoreCase(headerName)) {
                    driver.FindElementByXPath("//tbody[@id='id-tablebody-available-CaseTable']/tr[" + (i + 1) + "]/td[1]/input").waitAndClick(30);
                    driver.FindElementByXPath(locatorReader.getobjectLocator("addBtnColumnSetup")).waitAndClick(30);
                    driver.FindElementByXPath(locatorReader.getobjectLocator("saveBtnColumnSetup")).waitAndClick(30);
                    break;
                }
            }
            columnIndex = headerList.size();
        }
        return columnIndex;
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

    public void NavigateToPreservationTab() throws InterruptedException {

        ManageCaseTabsNavigation();
        ManageCaseTabs.get(2).click();
        Thread.sleep(3000);
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


    public void goToAddCorrespondentPage() {
        var btnAddCorrespondent = driver.FindElementById(reader.getobjectLocator("btnAddCorrespondent"));
        wait.until(ExpectedConditions.elementToBeClickable(btnAddCorrespondent.getWebElement()));
        btnAddCorrespondent.waitAndClick(30);
    }

    public int getCorrespondentRow(String empEmail) {
        int rowNumber = 0;
        var emailList = driver.FindElementsByXPath("//table[@id='case-correspondent-table-id']/tbody/tr/td[2]");
        for (int i = 0; i < emailList.size(); i++) {
            if (emailList.getElementByIndex(i).getText().equalsIgnoreCase(empEmail)) {
                rowNumber = i + 1;
                break;
            }
        }
        if (rowNumber > 0) {
            return rowNumber;
        } else {
            throw new RuntimeException("Correspondent Not Found");
        }
    }


    public void openEditCorrespondentModal(String empEmail) {
        int rowNumber = getCorrespondentRow(empEmail);
        driver.FindElementByXPath("//table[@id='case-correspondent-table-id']/tbody/tr[" + rowNumber + "]/td[6]/div[1]/a[1]/img[1]").waitAndClick(30);
    }

    public int getCheckboxSelectCount() {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            if (driver.FindElementsById("Actions").getElementByIndex(i).Selected()) count++;
        }
        return count;
    }

    public void saveEditCorrespondentModal() {
        driver.FindElementById(reader.getobjectLocator("saveEditCorrespondentModal")).waitAndClick(30);
    }

    public void checkEscalationCheckbox() {
        var checkboxEscalation = driver.FindElementByXPath(reader.getobjectLocator("checkboxEscalation"));
        if (!checkboxEscalation.Selected()) checkboxEscalation.waitAndClick(30);
    }

    public void checkCCCheckbox() {
        var checkboxCC = driver.FindElementByXPath(reader.getobjectLocator("checkboxCC"));
        if (!checkboxCC.Selected()) checkboxCC.waitAndClick(30);
    }

    public void checkSummaryCommunicationCheckbox() {
        var checkboxSummaryCommunication = driver.FindElementByXPath(reader.getobjectLocator("checkboxSummaryCommunication"));
        if (!checkboxSummaryCommunication.Selected()) checkboxSummaryCommunication.waitAndClick(30);
    }

    public void uncheckSummaryCommunicationCheckbox() {
        int count = getCheckboxSelectCount();
        var checkboxSummaryCommunication = driver.FindElementByXPath(reader.getobjectLocator("checkboxSummaryCommunication"));
        if (checkboxSummaryCommunication.Selected() && count > 1) {
            checkboxSummaryCommunication.waitAndClick(30);
        } else {
            System.out.println("Only one correspondent checkbox is selected. Unchecking that will result in deleting" +
                    " the correspondent. So unchecking checkbox is not allowed.");
        }
    }

    public void uncheckCCCheckbox() {
        int count = getCheckboxSelectCount();
        var checkboxCC = driver.FindElementByXPath(reader.getobjectLocator("checkboxCC"));
        if (checkboxCC.Selected() && count > 1) {
            checkboxCC.waitAndClick(30);
        } else {
            System.out.println("Only one correspondent checkbox is selected. Unchecking that will result in deleting" +
                    " the correspondent. So unchecking checkbox is not allowed.");
        }
    }

    public void uncheckEscalationCheckbox() {
        int count = getCheckboxSelectCount();
        var checkboxEscalation = driver.FindElementByXPath(reader.getobjectLocator("checkboxEscalation"));
        if (checkboxEscalation.Selected() && count > 1) {
            checkboxEscalation.waitAndClick(30);
        } else {
            System.out.println("Only one correspondent checkbox is selected. Unchecking that will result in deleting" +
                    " the correspondent. So unchecking checkbox is not allowed.");
        }
    }

    public void editCorrespondentByCheckingEscalation(String empEmail) {
        openEditCorrespondentModal(empEmail);
        checkEscalationCheckbox();
        saveEditCorrespondentModal();
    }

    public void editCorrespondentByUncheckingEscalation(String empEmail) {
        openEditCorrespondentModal(empEmail);
        uncheckEscalationCheckbox();
        saveEditCorrespondentModal();
    }

    public void editCorrespondentByCheckingCC(String empEmail) {
        openEditCorrespondentModal(empEmail);
        checkCCCheckbox();
        saveEditCorrespondentModal();
    }

    public void editCorrespondentByUncheckingCC(String empEmail) {
        openEditCorrespondentModal(empEmail);
        uncheckCCCheckbox();
        saveEditCorrespondentModal();
    }

    public void editCorrespondentByCheckingSummaryCommunication(String empEmail) {
        openEditCorrespondentModal(empEmail);
        checkSummaryCommunicationCheckbox();
        saveEditCorrespondentModal();
    }

    public void editCorrespondentByUncheckingSummaryCommunication(String empEmail) {
        openEditCorrespondentModal(empEmail);
        uncheckSummaryCommunicationCheckbox();
        saveEditCorrespondentModal();
    }


    public void deleteCorrespondent(String empEmail) {
        int rowNumber = getCorrespondentRow(empEmail);
        driver.FindElementByXPath("//table[@id='case-correspondent-table-id']/tbody/tr[" + rowNumber + "]/td[6]/div[1]/a[2]/img[1]").waitAndClick(30);
        driver.FindElementById(reader.getobjectLocator("okBtnDeleteCorrespondentModal")).waitAndClick(30);

    }


    public HashMap<String, String> addCaseEscalationCorrespondent(String empId) throws InterruptedException {
        driver.FindElementById(reader.getobjectLocator("subTabEscalation")).waitAndClick(30);
        var searchEmpIdAvailableEscalationTable = driver.FindElementByCssSelector(reader.getobjectLocator("searchEmpIdAvailableEscalationTable"));
        wait.until(ExpectedConditions.elementToBeClickable(searchEmpIdAvailableEscalationTable.getWebElement()));
        searchEmpIdAvailableEscalationTable.Clear();
        searchEmpIdAvailableEscalationTable.SendKeys(empId);
        Thread.sleep(2000);

        String textPaginationAvailableEscalationTable = driver.FindElementById(reader.getobjectLocator("textPaginationAvailableEscalationTable")).getText();
        Thread.sleep(3000);
        Pattern pattern = Pattern.compile("of (\\d+)");
        Matcher matcher = pattern.matcher(textPaginationAvailableEscalationTable);

        int count = 0;
        while (matcher.find()) {
            count = Integer.parseInt(matcher.group(1));
        }
        HashMap<String, String> correspondentList = new HashMap<>();

        for (int i = 1; i <= count; i++) {
            String email = driver.FindElementByXPath("//table[@id='id-CorrespondentAvailableEscalation']//tbody//tr[" + i + "]//td[5]").getText();
            String id = driver.FindElementByXPath("//table[@id='id-CorrespondentAvailableEscalation']//tbody//tr[" + i + "]//td[2]").getText();
            correspondentList.put(id, email);
        }

        driver.FindElementByName(reader.getobjectLocator("selectAllAvailableEscalationTable")).waitAndClick(30);
        driver.FindElementById(reader.getobjectLocator("addBtnEscalation")).waitAndClick(30);
        driver.FindElementById(reader.getobjectLocator("okBtnEscalationAddModal")).waitAndClick(30);

        Thread.sleep(2000);

        driver.scrollingToBottomofAPage();
        driver.FindElementById(reader.getobjectLocator("btnSaveCorrespondent")).waitAndClick(30);
        driver.FindElementById(reader.getobjectLocator("okBtnSaveCorrespondentModal")).waitAndClick(30);
        return correspondentList;
    }
}

