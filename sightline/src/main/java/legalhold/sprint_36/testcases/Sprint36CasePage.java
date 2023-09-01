package legalhold.sprint_36.testcases;

import automationLibrary.Driver;


import legalhold.BaseModule;
import legalhold.legalholdpagefactory.cases.CaseFactories;
import legalhold.utilities.parse_locators.LocatorReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.io.IOException;


public class Sprint36CasePage extends BaseModule {
    CaseFactories caseFactories;
    LocatorReader reader = new LocatorReader("./src/main/java/legalhold/selectors/cases/manage_case/case_information/case_information.properties");

    public Sprint36CasePage(Driver driver) throws IOException {
        super("./src/main/java/legalhold/selectors/cases/casepage.properties", driver);
        caseFactories = new CaseFactories(driver);
    }


    public void complianceReminderOnlyMaximumNumberSet(int max) {
        driver.FindElementByXPath(reader.getobjectLocator("btnComplianceReminderSchedule")).waitAndClick(30);
        driver.FindElementByXPath(reader.getobjectLocator("btnMaximumNumber")).Click();
        driver.FindElementByXPath(reader.getobjectLocator("inputMaxNumber")).Clear();

        driver.FindElementByXPath(reader.getobjectLocator("inputMaxNumber")).SendKeys(Integer.toString(max));
        driver.FindElementById(reader.getobjectLocator("btnScheduleModalSubmit")).Click();

        String newSchedule = driver.getWebDriver().findElement(By.xpath(reader.getobjectLocator("complianceReminderScheduleText"))).getText();
        softAssert.assertEquals(newSchedule, "Weekly, Monday, Wednesday, Friday (Maximum " + max + ")");
        System.out.println("Schedule matched!!");
    }

    public void goToCase(){
        driver.FindElementByXPath("//a[@href='/Template']").waitAndClick(30);
        driver.waitForPageToBeReady();
    }

    public void goToCaseCommunicationTab(){
        driver.FindElementById("navBtnCommunication").Click();
        driver.waitForPageToBeReady();
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

    public void goToCreateNonCustodianCommunicationPage(){
        WebElement btnCreateNonCustodianCommunication = driver.getWebDriver().findElement(By.xpath("//a[@aria-label='Add Non-Custodian Communication']"));
        wait.until(ExpectedConditions.elementToBeClickable(btnCreateNonCustodianCommunication));
        btnCreateNonCustodianCommunication.click();
        driver.waitForPageToBeReady();
        driver.getWebDriver().navigate().refresh();
        driver.waitForPageToBeReady();
        driver.scrollingToElementofAPage(btnCreateNonCustodianCommunication);
    }

    public void goToCreateReleaseCommunicationPage(){
        WebElement btnCreateReleaseCommunication = driver.getWebDriver().findElement(By.xpath("//a[@aria-label='Add Release Communication']"));
        wait.until(ExpectedConditions.elementToBeClickable(btnCreateReleaseCommunication));
        btnCreateReleaseCommunication.click();
        driver.waitForPageToBeReady();
        driver.getWebDriver().navigate().refresh();
        driver.waitForPageToBeReady();
        driver.scrollingToElementofAPage(btnCreateReleaseCommunication);
    }



}
