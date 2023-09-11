package legalhold.smoke_suite.Employee;

import automationLibrary.Driver;
import automationLibrary.Element;
import cucumber.api.java8.El;
import cucumber.api.java8.Th;
import legalhold.setup.BaseModule;
import legalhold.utilities.parse_locators.LocatorReader;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.io.IOException;

public class UploadEmployee extends BaseModule {
    LocatorReader reader;

    public UploadEmployee(Driver driver) throws IOException {
        super("src/main/java/legalhold/selectors/employee/employee.properties", driver);
        reader = new LocatorReader("src/main/java/legalhold/selectors/uploadFile.properties");
    }

    public void uploadValidEmployee() throws InterruptedException {

        Element manageEmployeeDropdown = driver.FindElementById("manageEmployeeDropdown");
        manageEmployeeDropdown.Click();

        Element uploadCsvButton = driver.FindElementById("upload-employee-csv");
        uploadCsvButton.Click();

        Element chooseFile = driver.FindElementById("empBulkAdd");
        wait.until(ExpectedConditions.elementToBeClickable(chooseFile.getWebElement()));


        chooseFile.SendKeys(reader.getFileName("UploadValidEmployeeFilePath"));

        Element employeeUploadButton = driver.FindElementById(locatorReader.getobjectLocator("employeeUploadButton"));
        employeeUploadButton.Click();
        driver.waitForPageToBeReady();
        Thread.sleep(7000);
        Element statusText = driver.FindElementByXPath(locatorReader.getobjectLocator("historyTableStatusData"));
        Element fileName = driver.FindElementByXPath(locatorReader.getobjectLocator("historyTableFileNameData"));


        if(statusText.getText() == "In Progress"){
            wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.id(locatorReader.getobjectLocator("employeeUploadButton")),"In Progress")));
        }
        Thread.sleep(3000);

        String expectedStatus = "Success";
        String expectedValidFileName = reader.getFileName("validFilename");


        Assert.assertEquals(statusText.getText(),expectedStatus);
        Assert.assertEquals(fileName.getText(),expectedValidFileName);

    }

    public void uploadInvalidEmployee() throws InterruptedException {

//        Element gotoEmployeeList = driver.FindElementByCssSelector("li[class='breadcrumb-item'] a");
//        gotoEmployeeList.Click();
//        driver.waitForPageToBeReady();


        Element manageEmployeeDropdown = driver.FindElementById("manageEmployeeDropdown");
        manageEmployeeDropdown.Click();

        Element uploadCsvButton = driver.FindElementById("upload-employee-csv");
        uploadCsvButton.Click();

        Element chooseFile = driver.FindElementById("empBulkAdd");
        wait.until(ExpectedConditions.elementToBeClickable(chooseFile.getWebElement()));


        chooseFile.SendKeys(reader.getFileName("UploadInvalidEmployeeFilePath"));


        Element employeeUploadButton = driver.FindElementById(locatorReader.getobjectLocator("employeeUploadButton"));
        employeeUploadButton.Click();
        driver.waitForPageToBeReady();
        Thread.sleep(7000);
        Element statusText = driver.FindElementByXPath(locatorReader.getobjectLocator("historyTableStatusData"));
        Element fileName = driver.FindElementByXPath(locatorReader.getobjectLocator("historyTableFileNameData"));
        Element errorNote = driver.FindElementByXPath(locatorReader.getobjectLocator("historyTableErrorNoteData"));

        if(statusText.getText() == "In Progress"){
            wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.id(locatorReader.getobjectLocator("employeeUploadButton")),"In Progress")));
        }
        Thread.sleep(20000);

        String expectedStatus = "Failed";
        String expectedInvalidFileName = reader.getFileName("invalidFilename");
        String expectedErrorNote = "Invalid Header";

        Assert.assertEquals(statusText.getText(),expectedStatus);
        Assert.assertEquals(fileName.getText(),expectedInvalidFileName);
        Assert.assertEquals(errorNote.getText(),expectedErrorNote);
    }
}