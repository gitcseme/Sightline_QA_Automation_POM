package legalhold.smoke_suite.manageCase;

import automationLibrary.CustomWebDriverWait;
import automationLibrary.Driver;
import automationLibrary.Element;
import legalhold.BaseModule;
import legalhold.legalholdpagefactory.Module_Navigation;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.List;

public class AddCaseCustodian extends BaseModule {
    public AddCaseCustodian(Driver driver) throws IOException {
        super("./src/main/java/legalhold/selectors/sl_slh_integration/navigation.properties", driver);
    }

    public void navigationToCustodianTab() throws IOException {

        driver.waitForPageToBeReady();
        WebDriverWait wait = new CustomWebDriverWait(driver.getWebDriver(), 30);
        Module_Navigation caseTabNavigation = new Module_Navigation(driver);

        caseTabNavigation.navigateToCaseTAB();

        Element CaseSearchBox = driver.FindElementByCssSelector("input[placeholder='Search Case Name']");


        wait.until(ExpectedConditions.elementToBeClickable(CaseSearchBox.getWebElement()));
        CaseSearchBox.SendKeys("Automation_Test");

        Element EditCase = driver.FindElementByXPath("(//img[@title='Edit case'])[1]");

        wait.until(ExpectedConditions.elementToBeClickable(EditCase.getWebElement()));
        EditCase.Click();

         List <WebElement> ManageCaseTabs = driver.getWebDriver().findElements(By.className("nav-link"));
         ManageCaseTabs.get(1).click();
         Element ManageCustodianButton = driver.FindElementById("btnManageCustodians");
        wait.until(ExpectedConditions.elementToBeClickable(ManageCustodianButton.getWebElement()));
        ManageCustodianButton.Click();


    }
}


