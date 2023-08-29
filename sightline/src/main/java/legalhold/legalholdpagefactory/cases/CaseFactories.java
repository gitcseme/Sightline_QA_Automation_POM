package legalhold.legalholdpagefactory.cases;

import automationLibrary.Driver;
import legalhold.BaseModule;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;

public class CaseFactories extends BaseModule {

    public CaseFactories(Driver driver) throws IOException {
        super("./src/main/java/legalhold/selectors/cases/casepage.properties", driver);
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

}

