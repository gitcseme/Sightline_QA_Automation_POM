package legalhold.legalholdpagefactory.global_notice;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import legalhold.setup.BaseModule;
import legalhold.utilities.parse_locators.LocatorReader;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;

public class GlobalNoticeFactories extends BaseModule {
    protected LocatorReader reader;

    public GlobalNoticeFactories(Driver driver) throws IOException {
        super("src/main/java/legalhold/selectors/global_notice/global_notice.properties", driver);
        reader = new LocatorReader("src/main/java/legalhold/selectors/cases/manage_case/case_information/case_information.properties");
    }

    public void goToCreateGlobalNoticePage() throws InterruptedException {
        var btnCreateGlobalNotice = wait.until(ExpectedConditions.elementToBeClickable(driver.FindElementByCssSelector(locatorReader.getobjectLocator("btnCreateGlobalNotice")).getWebElement()));
        btnCreateGlobalNotice.click();
        Thread.sleep(3000);
        driver.waitForPageToBeReady();
        var breadcrumbGlobalNoticePage = driver.FindElementsByClassName(locatorReader.getobjectLocator("breadcrumbGlobalNoticePage")).getElementByIndex(1).getText();
        if (!breadcrumbGlobalNoticePage.equalsIgnoreCase("Create Global Notice")) {
            throw new RuntimeException("Bread Crumb text for create global notice page is NOT 'Create Global Notice'. The actual text is: " + breadcrumbGlobalNoticePage);
        }
    }

    public void goToEditGlobalNoticePage(String globalNoticeName) throws InterruptedException {
        var searchGlobalNoticeName = driver.FindElementByCssSelector(locatorReader.getobjectLocator("searchGlobalNoticeName"));
        wait.until(ExpectedConditions.elementToBeClickable(searchGlobalNoticeName.getWebElement()));
        searchGlobalNoticeName.SendKeys(globalNoticeName);
        driver.FindElementByXPath("//table[@id='id-GlobalNotice']/tbody/tr[1]/td[6]//img").waitAndClick(30);
        Thread.sleep(3000);
        driver.waitForPageToBeReady();
        var enterGlobalNoticeName = driver.FindElementById(locatorReader.getobjectLocator("enterGlobalNoticeName")).GetAttribute("value");
        if (!enterGlobalNoticeName.equalsIgnoreCase(globalNoticeName)) {
            throw new RuntimeException("Global Notice name does NOT match with what was searched on the Global Notice data table");
        }
    }

    public String enterGlobalNoticeNameAndDescription() {
        var globalNoticeName = faker.country().name() + " " + faker.team().sport() + " " + faker.job().title();
        driver.FindElementById(locatorReader.getobjectLocator("enterGlobalNoticeName")).SendKeys(globalNoticeName);
        driver.FindElementById(locatorReader.getobjectLocator("enterGlobalNoticeDescription")).SendKeys(faker.lorem().sentence(10));
        return globalNoticeName;
    }

    public void setGlobalNoticeAsOneTime() {
        Element btnGlobalNoticeSchedule = driver.FindElementByName(locatorReader.getobjectLocator("btnGlobalNoticeSchedule"));
        wait.until(ExpectedConditions.elementToBeClickable(btnGlobalNoticeSchedule.getWebElement()));
        driver.scrollingToElementofAPage(btnGlobalNoticeSchedule);
        btnGlobalNoticeSchedule.waitAndClick(30);
        Element btnOneTime = driver.FindElementByCssSelector(reader.getobjectLocator("btnOneTime"));
        if (!btnOneTime.Selected()) {
            btnOneTime.Click();
        } else {
            System.out.println("Already set as One Time");
        }
        driver.FindElementById(reader.getobjectLocator("btnScheduleModalSubmit")).Click();
    }

    public void setGlobalNoticeAsWeekly() {
        Element btnGlobalNoticeSchedule = driver.FindElementByName(locatorReader.getobjectLocator("btnGlobalNoticeSchedule"));
        wait.until(ExpectedConditions.elementToBeClickable(btnGlobalNoticeSchedule.getWebElement()));
        driver.scrollingToElementofAPage(btnGlobalNoticeSchedule);
        btnGlobalNoticeSchedule.waitAndClick(30);
        Element btnWeekly = driver.FindElementByCssSelector(reader.getobjectLocator("btnWeekly"));

        btnWeekly.Click();
        if (!driver.FindElementByCssSelector("input[aria-label='Monday']").Selected()) {
            driver.FindElementByCssSelector("input[aria-label='Monday']").waitAndClick(30);
        }
        if (!driver.FindElementByCssSelector("input[aria-label='Tuesday']").Selected()) {
            driver.FindElementByCssSelector("input[aria-label='Tuesday']").waitAndClick(30);
        }
        if (!driver.FindElementByCssSelector("input[aria-label='Wednesday']").Selected()) {
            driver.FindElementByCssSelector("input[aria-label='Wednesday']").waitAndClick(30);
        }
        if (!driver.FindElementByCssSelector("input[aria-label='Thursday']").Selected()) {
            driver.FindElementByCssSelector("input[aria-label='Thursday']").waitAndClick(30);
        }
        if (!driver.FindElementByCssSelector("input[aria-label='Friday']").Selected()) {
            driver.FindElementByCssSelector("input[aria-label='Friday']").waitAndClick(30);
        }
        driver.FindElementById(reader.getobjectLocator("btnScheduleModalSubmit")).Click();
    }

    public void setGlobalNoticeAsBiWeekly(String recurringDay) {
        Element btnGlobalNoticeSchedule = driver.FindElementByName(locatorReader.getobjectLocator("btnGlobalNoticeSchedule"));
        wait.until(ExpectedConditions.elementToBeClickable(btnGlobalNoticeSchedule.getWebElement()));
        driver.scrollingToElementofAPage(btnGlobalNoticeSchedule);
        btnGlobalNoticeSchedule.waitAndClick(30);
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

    public void setGlobalNoticeAsTriWeekly(String recurringDay) {
        Element btnGlobalNoticeSchedule = driver.FindElementByName(locatorReader.getobjectLocator("btnGlobalNoticeSchedule"));
        wait.until(ExpectedConditions.elementToBeClickable(btnGlobalNoticeSchedule.getWebElement()));
        driver.scrollingToElementofAPage(btnGlobalNoticeSchedule);
        btnGlobalNoticeSchedule.waitAndClick(30);
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

    public void setGlobalNoticeAsMonthly(int recurringMonth, int recurringDay) {
        Element btnGlobalNoticeSchedule = driver.FindElementByName(locatorReader.getobjectLocator("btnGlobalNoticeSchedule"));
        wait.until(ExpectedConditions.elementToBeClickable(btnGlobalNoticeSchedule.getWebElement()));
        driver.scrollingToElementofAPage(btnGlobalNoticeSchedule);
        btnGlobalNoticeSchedule.waitAndClick(30);
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

    public void enterGlobalNoticeEmailSubject(String globalNoticeEmailSubject) {
//        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.FindElementById(locatorReader.getobjectLocator("enterGlobalNoticeEmailSubject")).getWebElement());
        driver.scrollingToBottomofAPage();
        var enterGlobalNoticeEmailSubject = wait.until(ExpectedConditions.elementToBeClickable(driver.FindElementByName(locatorReader.getobjectLocator("enterGlobalNoticeEmailSubject")).getWebElement()));
        enterGlobalNoticeEmailSubject.sendKeys(globalNoticeEmailSubject);
    }

    public void typeEmailBody(String emailText) {
        WebElement tinyMCE = driver.getWebDriver().findElement(By.id(locatorReader.getobjectLocator("tinyMCE")));
        wait.until(ExpectedConditions.visibilityOf(tinyMCE));
        if (tinyMCE.isDisplayed()) {
            driver.switchTo().frame("editorTiny_ifr");
            driver.FindElementById("tinymce").SendKeys(emailText);
            driver.switchTo().defaultContent();
        } else {
            throw new RuntimeException("TinyMCE not found");
        }
    }

    public void saveGlobalNotice() throws InterruptedException {
        Element btnSaveGlobalNotice = driver.FindElementById(locatorReader.getobjectLocator("btnSaveGlobalNotice"));
        wait.until(ExpectedConditions.elementToBeClickable(btnSaveGlobalNotice.getWebElement()));
        driver.scrollingToBottomofAPage();
        btnSaveGlobalNotice.waitAndClick(30);
        driver.FindElementById(locatorReader.getobjectLocator("okBtnSaveModal")).waitAndClick(30);
        Thread.sleep(5000);
        driver.waitForPageToBeReady();
    }

    public void startGlobalNotice() throws InterruptedException {
        Element btnStartGlobalNotice = driver.FindElementById(locatorReader.getobjectLocator("btnStartGlobalNotice"));
        wait.until(ExpectedConditions.elementToBeClickable(btnStartGlobalNotice.getWebElement()));
        driver.scrollingToBottomofAPage();
        btnStartGlobalNotice.waitAndClick(30);
        driver.FindElementById(locatorReader.getobjectLocator("okBtnStartModal")).waitAndClick(30);
        Thread.sleep(5000);
        driver.waitForPageToBeReady();
    }

    public void addMailToRecipients(String empId) throws IOException, InterruptedException {
        Element addMailToRecipientButton = driver.FindElementByClassName(locatorReader.getobjectLocator("addMailtoRecipientButton"));
        wait.until(ExpectedConditions.elementToBeClickable(addMailToRecipientButton.getWebElement()));
        addMailToRecipientButton.waitAndClick(30);

        driver.FindElementById(locatorReader.getobjectLocator("mailToSubTab")).waitAndClick(30);
        Element availableTableEmployeeId = driver.FindElementByXPath(locatorReader.getobjectLocator("availableTableEmployeeId"));
        availableTableEmployeeId.Clear();
        availableTableEmployeeId.SendKeys(empId);


        Element selectAllCheckBox = driver.FindElementByCssSelector(locatorReader.getobjectLocator("selectAllCheckbox"));
        wait.until(ExpectedConditions.elementToBeClickable(selectAllCheckBox.getWebElement()));
        Thread.sleep(5000);
        selectAllCheckBox.Click();

        Element addCustodiansButton = driver.FindElementById(locatorReader.getobjectLocator("addCustodiansButton"));
        addCustodiansButton.waitAndClick(30);


        Element confirmationModalOkButton = driver.FindElementById(locatorReader.getobjectLocator("mailtoConfirmationOkBtn"));
        wait.until(ExpectedConditions.elementToBeClickable(confirmationModalOkButton.getWebElement()));
        confirmationModalOkButton.waitAndClick(30);

        Element globalSaveButtonManageRecipients = driver.FindElementById(locatorReader.getobjectLocator("manageRecipientsGlobalSveBtn"));
        driver.scrollingToElementofAPage(globalSaveButtonManageRecipients);
        wait.until(ExpectedConditions.elementToBeClickable(globalSaveButtonManageRecipients.getWebElement()));
        globalSaveButtonManageRecipients.waitAndClick(30);

        Element confirmationOkButton = driver.FindElementById(locatorReader.getobjectLocator("manageRecipientsGlobalSaveOkBtn"));
        wait.until(ExpectedConditions.elementToBeClickable(confirmationOkButton.getWebElement()));
        confirmationOkButton.Click();
    }

    public void addSummaryCommunicationRecipients(String empId) throws IOException, InterruptedException {
        Element addSummaryCommunicationRecipientButton = driver.FindElementByClassName(locatorReader.getobjectLocator("addSummaryCommunicationRecipientButton"));
        wait.until(ExpectedConditions.elementToBeClickable(addSummaryCommunicationRecipientButton.getWebElement()));
        addSummaryCommunicationRecipientButton.waitAndClick(30);

        driver.FindElementById(locatorReader.getobjectLocator("summaryCommunicationSubTab")).waitAndClick(30);
        Element availableTableSummaryCommunicationEmployeeId = driver.FindElementByXPath(locatorReader.getobjectLocator("availableTableSummaryCommunicationEmployeeId"));
        availableTableSummaryCommunicationEmployeeId.Clear();
        availableTableSummaryCommunicationEmployeeId.SendKeys(empId);


        Element selectAllSummaryCommunicationCheckbox = driver.FindElementByCssSelector(locatorReader.getobjectLocator("selectAllSummaryCommunicationCheckbox"));
        wait.until(ExpectedConditions.elementToBeClickable(selectAllSummaryCommunicationCheckbox.getWebElement()));
        Thread.sleep(5000);
        selectAllSummaryCommunicationCheckbox.waitAndClick(30);

        Element addCustodiansSummaryCommunicationButton = driver.FindElementById(locatorReader.getobjectLocator("addCustodiansSummaryCommunicationButton"));
        addCustodiansSummaryCommunicationButton.waitAndClick(30);


        Element summaryConfirmationOkBtn = driver.FindElementById(locatorReader.getobjectLocator("summaryConfirmationOkBtn"));
        wait.until(ExpectedConditions.elementToBeClickable(summaryConfirmationOkBtn.getWebElement()));
        summaryConfirmationOkBtn.waitAndClick(30);

        Element globalSaveButtonManageRecipients = driver.FindElementById(locatorReader.getobjectLocator("manageRecipientsGlobalSveBtn"));
        driver.scrollingToElementofAPage(globalSaveButtonManageRecipients);
        wait.until(ExpectedConditions.elementToBeClickable(globalSaveButtonManageRecipients.getWebElement()));
        globalSaveButtonManageRecipients.waitAndClick(30);

        Element confirmationOkButton = driver.FindElementById(locatorReader.getobjectLocator("manageRecipientsGlobalSaveOkBtn"));
        wait.until(ExpectedConditions.elementToBeClickable(confirmationOkButton.getWebElement()));
        confirmationOkButton.Click();
    }

}
