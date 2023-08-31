package legalhold.legalholdpagefactory.cases;

import automationLibrary.Driver;
import legalhold.BaseModule;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.io.IOException;

public class CaseFactories extends BaseModule {

    public CaseFactories(Driver driver) throws IOException {
        super("./src/main/java/legalhold/selectors/cases/casepage.properties", driver);
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
        driver.FindElementById("manageCaseSubmitBtn").Click();
        driver.FindElementById("modalCaseConfirmOkbutton").Click();
        String successToast = driver.FindElementByXPath("//p[normalize-space()='Success message. Case updated successfully']").getText();
        Assert.assertTrue(successToast.contains("Case updated successfully"));
        Thread.sleep(5000);
    }

}

