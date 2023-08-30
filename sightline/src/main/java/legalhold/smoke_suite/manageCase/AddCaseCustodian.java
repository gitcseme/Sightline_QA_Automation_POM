package legalhold.smoke_suite.manageCase;

import automationLibrary.CustomWebDriverWait;
import automationLibrary.Driver;
import automationLibrary.Element;
import legalhold.BaseModule;
import legalhold.legalholdpagefactory.Module_Navigation;
import legalhold.legalholdpagefactory.cases.CaseFactories;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.List;

public class AddCaseCustodian extends BaseModule {
    public AddCaseCustodian(Driver driver) throws IOException {
        super("./src/main/java/legalhold/selectors/sl_slh_integration/case.properties", driver);
    }

    public void navigationToCustodianTab() throws IOException {

        driver.waitForPageToBeReady();

        Module_Navigation caseTabNavigation = new Module_Navigation(driver);

        caseTabNavigation.navigateToCaseTAB();


        CaseFactories SearchCaseandGotoEdit = new CaseFactories(driver);
        SearchCaseandGotoEdit.goToEditCase("Automation_Test");

         List <WebElement> ManageCaseTabs = driver.getWebDriver().findElements(By.className(locatorReader.getobjectLocator("ManageCaseTabs")));
         ManageCaseTabs.get(1).click();
         Element ManageCustodianButton = driver.FindElementById(locatorReader.getobjectLocator("CustodianTab"));
        wait.until(ExpectedConditions.elementToBeClickable(ManageCustodianButton.getWebElement()));
        ManageCustodianButton.Click();


    }
}


