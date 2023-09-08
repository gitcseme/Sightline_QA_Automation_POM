package legalhold.legalholdpagefactory.cases;

import automationLibrary.Driver;
import automationLibrary.Element;
import legalhold.setup.BaseModule;
import legalhold.utilities.parse_locators.LocatorReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.io.IOException;
import java.util.List;

public class CaseFactories extends BaseModule {

    List<WebElement> ManageCaseTabs;
    LocatorReader reader;
    public CaseFactories(Driver driver) throws IOException {

        super("./src/main/java/legalhold/selectors/cases/casepage.properties", driver);
        reader = new LocatorReader("src/main/java/legalhold/selectors/cases/manage_case/case_information/case_information.properties");
    }

    public void ManageCaseTabsNavigation(){
        ManageCaseTabs = driver.getWebDriver().findElements(By.className(locatorReader.getobjectLocator("ManageCaseTabs")));
    }

    public void searchCaseByName(String caseName) {
        try {
            WebElement caseNameColumnFilterBox = driver.getWebDriver().findElement(By.xpath(locatorReader.getobjectLocator("searchCaseNameColumn")));
            wait.until(ExpectedConditions.elementToBeClickable(caseNameColumnFilterBox));
            caseNameColumnFilterBox.sendKeys(caseName);
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
        ManageCaseTabs.get(2).click();
        driver.waitForPageToBeReady();
    }

    public  void NavigateToSurveysTab(){

        ManageCaseTabsNavigation();
        ManageCaseTabs.get(3).click();
        driver.waitForPageToBeReady();
    }

    public  void NavigateToCommunicationsTab(){

        ManageCaseTabsNavigation();
        ManageCaseTabs.get(4).click();
        driver.waitForPageToBeReady();
    }
}

