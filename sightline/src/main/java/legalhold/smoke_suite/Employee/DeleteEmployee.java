package legalhold.smoke_suite.Employee;

import automationLibrary.Driver;
import automationLibrary.Element;
import legalhold.BaseModule;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.io.IOException;

public class DeleteEmployee extends BaseModule {
    public DeleteEmployee(Driver driver) throws IOException {
        super("./src/main/java/legalhold/selectors/sl_slh_integration/employees.properties", driver);
    }

    public void deleteCreatedEmployee() throws InterruptedException {
        driver.waitForPageToBeReady();
        Element deleteButton = driver.FindElementByCssSelector(locatorReader.getobjectLocator("employeeDeleteButton"));

        deleteButton.Click();

        Element confirmDelete = driver.FindElementById(locatorReader.getobjectLocator("deleteConfirmButton"));
        wait.until(ExpectedConditions.elementToBeClickable(confirmDelete.getWebElement()));
        driver.waitForPageToBeReady();
        confirmDelete.Click();

        String expectedPaginationText = "Showing 0 to 0 of 0 entries";
        Element paginationText = driver.FindElementById(locatorReader.getobjectLocator("paginationText"));
        Thread.sleep(2000);
        String actualPaginationText = paginationText.getText();
        Assert.assertEquals(actualPaginationText,expectedPaginationText);

    }
}
