package legalhold.legalholdpagefactory.cases;

import automationLibrary.Driver;
import automationLibrary.Element;
import cucumber.api.java8.Th;
import legalhold.setup.BaseModule;
import legalhold.utilities.parse_locators.LocatorReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.io.IOException;
import java.util.List;

public class CustodianFactories extends BaseModule {

    List<WebElement> ManageCaseTabs;
    LocatorReader reader;

    public CustodianFactories(Driver driver) throws IOException {

        super("./src/main/java/legalhold/selectors/cases/casepage.properties", driver);
        reader = new LocatorReader("src/main/java/legalhold/selectors/cases/manage_case/case_information/case_information.properties");
    }

    public void navigateToManageCustodiansPage(){
        Element ManageCustodianButton = driver.FindElementById("btnManageCustodians");
        wait.until(ExpectedConditions.elementToBeClickable(ManageCustodianButton.getWebElement()));
        ManageCustodianButton.Click();

        driver.waitForPageToBeReady();
    }

    public void searchManageCustodianAvailableCustodianById(String id) {
        try {
            Element availableEmployeeIDFilterBox = driver.FindElementByCssSelector("table[id='id-ManageEmpTable'] thead tr th input[placeholder='Search Employee ID']");
            wait.until(ExpectedConditions.elementToBeClickable(availableEmployeeIDFilterBox.getWebElement()));
            availableEmployeeIDFilterBox.Clear();
            availableEmployeeIDFilterBox.SendKeys(id);

            String expected_text = "Showing 1 to 1 of 1 entries";
            wait.until(ExpectedConditions.textToBe(By.id(locatorReader.getobjectLocator("rowCount")), expected_text));
        } catch (Exception E) {
            System.out.println("Custodian not found. The exception is: ");
            System.out.println(E.getMessage());
        }
    }
    public void searchManageCustodianSelectedeCustodianById(String id) {
        try {
            Element availableEmployeeIDFilterBox = driver.FindElementByCssSelector("table[id='id-manageCustodianDataTable'] thead tr th input[placeholder='Search Employee ID']");
            wait.until(ExpectedConditions.elementToBeClickable(availableEmployeeIDFilterBox.getWebElement()));
            availableEmployeeIDFilterBox.Clear();
            availableEmployeeIDFilterBox.SendKeys(id);

            String expected_text = "Showing 1 to 1 of 1 entries";
            wait.until(ExpectedConditions.textToBe(By.id(locatorReader.getobjectLocator("rowCount")), expected_text));
        } catch (Exception E) {
            System.out.println("Custodian not found. The exception is: ");
            System.out.println(E.getMessage());
        }
    }

    public void manageCustodianGlobalSave() throws InterruptedException {
        Element manageCustodianGlobalSavebtn = driver.FindElementById("saveAndDoneBtnId");
        driver.scrollingToElementofAPage(manageCustodianGlobalSavebtn);
        manageCustodianGlobalSavebtn.waitAndClick(10);

        driver.FindElementById("save-ok-btn").waitAndClick(20);

        Thread.sleep(6000);
    }

    public void addCustodianToCase(String custodianToAdd) throws InterruptedException {
        searchManageCustodianAvailableCustodianById(custodianToAdd);

        driver.FindElementByCssSelector("input[name='th-ManageEmpTablechkBoxAll']").waitAndClick(30);

        driver.FindElementById("addToCustodian").waitAndClick(20);
        Thread.sleep(5000);
        driver.FindElementById("addUserOkButton").waitAndClick(30);

        searchManageCustodianSelectedeCustodianById(custodianToAdd);

        manageCustodianGlobalSave();
    }


    public void verifyCustodianStatus(String expectedCustodianStatus) throws InterruptedException {

        driver.waitForPageToBeReady();
        Thread.sleep(4000);
        Element custodianTypeValue = driver.FindElementByCssSelector("td:nth-child(8)");
        wait.until(ExpectedConditions.elementToBeClickable(custodianTypeValue.getWebElement()));
        Assert.assertEquals(custodianTypeValue.getText(),expectedCustodianStatus);
    }

    public void searchCustodianById(String id) {
        try {
            Element availableEmployeeIDFilterBox = driver.FindElementByCssSelector("input[placeholder='Search Employee ID']");
            wait.until(ExpectedConditions.elementToBeClickable(availableEmployeeIDFilterBox.getWebElement()));
            availableEmployeeIDFilterBox.Clear();
            availableEmployeeIDFilterBox.SendKeys(id);

            String expected_text = "Showing 1 to 1 of 1 entries";

            wait.until(ExpectedConditions.textToBe(By.id(locatorReader.getobjectLocator("rowCount")), expected_text));
        } catch (Exception E) {
            System.out.println("Custodian not found. The exception is: ");
            System.out.println(E.getMessage());
        }
    }
}
