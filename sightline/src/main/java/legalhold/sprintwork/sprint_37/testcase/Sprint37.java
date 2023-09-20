package legalhold.sprintwork.sprint_37.testcase;

import automationLibrary.Driver;
import automationLibrary.Element;
import legalhold.setup.BaseModule;
import legalhold.utilities.parse_locators.LocatorReader;

import java.io.IOException;

public class Sprint37 extends BaseModule {
    LocatorReader reader;

    public Sprint37(Driver driver) throws IOException {
        super("./src/main/java/legalhold/selectors/cases/casepage.properties", driver);
        reader = new LocatorReader("./src/main/java/legalhold/selectors/cases/manage_case/case_information/case_information.properties");
    }

    public void setComplianceReminderCustomInterval() {

        driver.FindElementByXPath(reader.getobjectLocator("btnComplianceReminderSchedule")).waitAndClick(30);
        Element customIntervalRadioButton = driver.FindElementByXPath(reader.getobjectLocator("customIntervalRadioButton"));
        customIntervalRadioButton.Click();
    }
}
