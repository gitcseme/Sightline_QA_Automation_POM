package legalhold.sprint_36.testcases;

import automationLibrary.Driver;


import automationLibrary.Element;
import legalhold.BaseModule;
import legalhold.legalholdpagefactory.cases.CaseFactories;
import legalhold.utilities.parse_locators.LocatorReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;


public class Sprint36CasePage extends BaseModule {
    CaseFactories caseFactories;
    LocatorReader reader;
    LocatorReader seriesReader;

    public Sprint36CasePage(Driver driver) throws IOException {
        super("./src/main/java/legalhold/selectors/cases/casepage.properties", driver);
        caseFactories = new CaseFactories(driver);
        reader = new LocatorReader("./src/main/java/legalhold/selectors/cases/manage_case/case_information/case_information.properties");
        seriesReader = new LocatorReader("src/main/java/legalhold/selectors/cases/manage_case/communication/communication_page.properties");
    }


    public void caseComplianceReminderOnlyMaximumNumberSet(int max) {

        driver.FindElementByXPath(reader.getobjectLocator("btnComplianceReminderSchedule")).waitAndClick(30);
        Element btnMax = driver.FindElementByXPath(reader.getobjectLocator("btnMaximumNumber"));
        if (!btnMax.Selected()) {
            btnMax.Click();
        }
        driver.FindElementByXPath(reader.getobjectLocator("inputMaxNumber")).Clear();

        driver.FindElementByXPath(reader.getobjectLocator("inputMaxNumber")).SendKeys(Integer.toString(max));
        driver.FindElementById(reader.getobjectLocator("btnScheduleModalSubmit")).Click();
    }

    public void seriesComplianceReminderOnlyMaximumNumberSet(int max) {
        driver.FindElementByCssSelector(seriesReader.getobjectLocator("btnComplianceReminderSchedule")).waitAndClick(30);
        Element btnMax = driver.FindElementByXPath(reader.getobjectLocator("btnMaximumNumber"));
        if (!btnMax.Selected()) {
            btnMax.Click();
        }
        driver.FindElementByXPath(reader.getobjectLocator("inputMaxNumber")).Clear();

        driver.FindElementByXPath(reader.getobjectLocator("inputMaxNumber")).SendKeys(Integer.toString(max));
        driver.FindElementById(reader.getobjectLocator("btnScheduleModalSubmit")).Click();
    }

}
