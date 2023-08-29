package legalhold.sprint_36.testcases;

import automationLibrary.Driver;


import legalhold.BaseModule;
import legalhold.legalholdpagefactory.cases.CaseFactories;
import legalhold.utilities.parse_locators.LocatorReader;
import org.openqa.selenium.By;

import java.io.IOException;


public class Sprint36CasePage extends BaseModule {
    CaseFactories caseFactories;
    LocatorReader reader = new LocatorReader("./src/main/java/legalhold/selectors/cases/manage_case/case_information/case_information.properties");

    public Sprint36CasePage(Driver driver) throws IOException {
        super("./src/main/java/legalhold/selectors/cases/casepage.properties", driver);
        caseFactories = new CaseFactories(driver);
    }


    public void complianceReminderMaximumNumberSet(int max) {
        driver.FindElementByXPath(reader.getobjectLocator("btnComplianceReminderSchedule")).Click();
        driver.FindElementByXPath(reader.getobjectLocator("btnMaximumNumber")).Click();
        driver.FindElementByXPath(reader.getobjectLocator("inputMaxNumber")).Clear();

        driver.FindElementByXPath(reader.getobjectLocator("inputMaxNumber")).SendKeys(Integer.toString(max));
        driver.FindElementById(reader.getobjectLocator("btnScheduleModalSubmit")).Click();

        String newSchedule = driver.getWebDriver().findElement(By.xpath(reader.getobjectLocator("complianceReminderScheduleText"))).getText();
        softAssert.assertEquals(newSchedule, "Weekly, Monday, Wednesday, Friday (Maximum " + max + ")");
        System.out.println("Schedule matched!!");
    }


}
