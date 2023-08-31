package legalhold.legalholdpagefactory.cases;

import automationLibrary.Driver;
import automationLibrary.Element;
import legalhold.BaseModule;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;
import java.util.List;

public class CaseFactories extends BaseModule {

    List<WebElement> ManageCaseTabs;
    public CaseFactories(Driver driver) throws IOException {

        super("./src/main/java/legalhold/selectors/cases/casepage.properties", driver);
    }

    public void ManageCaseTabsNavigation(){
        ManageCaseTabs = driver.getWebDriver().findElements(By.className(locatorReader.getobjectLocator("ManageCaseTabs")));
    }

    public void searchCaseByName(String caseName) {
        try {
            driver.FindElementByXPath(locatorReader.getobjectLocator("searchCaseNameColumn")).SendKeys(caseName);
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

    public  void NavigateToCaseInformationTab(){

        ManageCaseTabsNavigation();
        ManageCaseTabs.get(0).click();
        driver.waitForPageToBeReady();
    }

    public void NavigateToCustodiansTab(){

        ManageCaseTabsNavigation();
        ManageCaseTabs.get(1).click();
        driver.waitForPageToBeReady();
    }

    public  void NavigateToPreservationTab(){

        ManageCaseTabsNavigation();
        ManageCaseTabs.get(1).click();
        driver.waitForPageToBeReady();
    }

    public  void NavigateToSurveysTab(){

        ManageCaseTabsNavigation();
        ManageCaseTabs.get(1).click();
        driver.waitForPageToBeReady();
    }

    public  void NavigateToCommunicationsTab(){

        ManageCaseTabsNavigation();
        ManageCaseTabs.get(1).click();
        driver.waitForPageToBeReady();
    }

}

