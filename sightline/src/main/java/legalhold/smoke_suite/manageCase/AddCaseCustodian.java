package legalhold.smoke_suite.manageCase;

import automationLibrary.Driver;
import automationLibrary.Element;
import legalhold.legalholdpagefactory.cases.CustodianFactories;
import legalhold.setup.BaseModule;
import legalhold.legalholdpagefactory.Module_Navigation;
import legalhold.legalholdpagefactory.cases.CaseFactories;
import legalhold.utilities.parse_locators.LocatorReader;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.io.IOException;

public class AddCaseCustodian extends BaseModule {

    LocatorReader reader;

    public AddCaseCustodian(Driver driver) throws IOException {
        super("src/main/java/legalhold/selectors/cases/manage_case/custodian/custodian.properties", driver);
        reader = new LocatorReader("src/main/java/legalhold/selectors/uploadFile.properties");
    }

//    public void navigationToCustodianTab() throws IOException {
//
////        driver.waitForPageToBeReady();
//
//        Module_Navigation caseTabNavigation = new Module_Navigation(driver);
//
//        caseTabNavigation.navigateToCaseTAB();
//
//
//        CaseFactories SearchCaseandGotoEdit = new CaseFactories(driver);
//        SearchCaseandGotoEdit.goToEditCase("Automation_Test");
//
//       CaseFactories navigateToCustodiansTab = new CaseFactories(driver);
//       navigateToCustodiansTab.NavigateToCustodiansTab();
//
//
//    }

    public void upLoadCustodians() throws InterruptedException {
        driver.waitForPageToBeReady();
        Element ManageCustodianButton = driver.FindElementById(locatorReader.getobjectLocator("CustodianTab"));
        wait.until(ExpectedConditions.elementToBeClickable(ManageCustodianButton.getWebElement()));
        ManageCustodianButton.Click();

        driver.waitForPageToBeReady();

        Element uploadCustodianDropdown = driver.FindElementByCssSelector(locatorReader.getobjectLocator("UploadCustodianDropdown"));
        uploadCustodianDropdown.Click();

        Element uploadCustodian = driver.FindElementByCssSelector(locatorReader.getobjectLocator("UploadCustodianButton"));
        uploadCustodian.Click();

        driver.waitForPageToBeReady();
        Element chooseFile = driver.FindElementById(locatorReader.getobjectLocator("ChooseFile"));


        wait.until(ExpectedConditions.elementToBeClickable(chooseFile.getWebElement()));

        chooseFile.SendKeys(reader.getFileName("UploadCustodianFilePath"));

        Element uploadCustodianButton = driver.FindElementById(locatorReader.getobjectLocator("CustodianUploadButton"));
        uploadCustodianButton.Click();

        Element custodianGlobalSave = driver.FindElementById(locatorReader.getobjectLocator("CustodianGlobalSave"));
        driver.scrollingToBottomofAPage();

        wait.until(ExpectedConditions.elementToBeClickable(custodianGlobalSave.getWebElement()));
        Thread.sleep(2000);
        custodianGlobalSave.Click();

        Element okButton = driver.FindElementById(locatorReader.getobjectLocator("CustodianSaveConfirmationOk"));
        wait.until(ExpectedConditions.elementToBeClickable(okButton.getWebElement()));
        okButton.Click();


    }

    public void makeCustodianSilent() throws IOException, InterruptedException {


        driver.FindElementByXPath("(//a[@aria-label='Action menu Dropdown'])[1]").waitAndClick(30);

        Element silentOption = driver.FindElementByXPath("//a[normalize-space()='Make Silent']");
        wait.until(ExpectedConditions.elementToBeClickable(silentOption.getWebElement()));
        silentOption.waitAndClick(30);
        Element okConfirmButton = driver.FindElementById("toggle_silent");
        wait.until(ExpectedConditions.elementToBeClickable(okConfirmButton.getWebElement()));
        okConfirmButton.waitAndClick(30);

        Thread.sleep(2000);

    }


    public void makeCustodianNonSilent() throws IOException, InterruptedException {


        driver.FindElementByXPath("(//a[@aria-label='Action menu Dropdown'])[1]").waitAndClick(30);

        Element nonSilentOption = driver.FindElementByXPath("//a[normalize-space()='Make Non Silent']");
        wait.until(ExpectedConditions.elementToBeClickable(nonSilentOption.getWebElement()));
        nonSilentOption.waitAndClick(30);
        Element okConfirmButton = driver.FindElementById("toggle_silent");
        wait.until(ExpectedConditions.elementToBeClickable(okConfirmButton.getWebElement()));
        okConfirmButton.waitAndClick(30);




    }

    public void manualAcknowledgementWithoutReason(){
//        driver.FindElementByXPath("(//a[@aria-label='Action menu Dropdown'])[1]").waitAndClick(30);
        driver.FindElementByXPath("//tr[@class='even']//img[@title='View Dropdown']").waitAndClick(30);
        Element manualAcknowledgementOption = driver.FindElementByXPath("//a[normalize-space()='Manual Acknowledgment']");
        wait.until(ExpectedConditions.elementToBeClickable(manualAcknowledgementOption.getWebElement()));
        manualAcknowledgementOption.waitAndClick(30);

        Element manualConfirmationOkButton = driver.FindElementById("manual_ack");
        wait.until(ExpectedConditions.elementToBeClickable(manualConfirmationOkButton.getWebElement()));
        manualConfirmationOkButton.waitAndClick(20);

    }

    public void manualAcknowledgementWithReason(String reason){
        driver.FindElementByXPath("//img[@title='View Dropdown']").waitAndClick(30);
        Element manualAcknowledgementOption = driver.FindElementByXPath("//a[normalize-space()='Manual Acknowledgment']");
        wait.until(ExpectedConditions.elementToBeClickable(manualAcknowledgementOption.getWebElement()));
        manualAcknowledgementOption.waitAndClick(30);

        Element manualAckReasonTextBox = driver.FindElementById("manual_ack_reason");
        wait.until(ExpectedConditions.elementToBeClickable(manualAckReasonTextBox.getWebElement()));
        manualAckReasonTextBox.SendKeys(reason);

        Element manualConfirmationOkButton = driver.FindElementById("manual_ack");
        wait.until(ExpectedConditions.elementToBeClickable(manualConfirmationOkButton.getWebElement()));
        manualConfirmationOkButton.waitAndClick(20);
    }

    public void releaseCustodianManually(){

        driver.FindElementByXPath("//img[@title='View Dropdown']").waitAndClick(30);
        Element releaseCustodianOption = driver.FindElementByXPath("//a[normalize-space()='Release Custodian']");
        wait.until(ExpectedConditions.elementToBeClickable(releaseCustodianOption.getWebElement()));
        releaseCustodianOption.waitAndClick(30);

        Element releaseCustodianConfirmationOk = driver.FindElementById("release_custodian_single");
        wait.until(ExpectedConditions.elementToBeClickable(releaseCustodianConfirmationOk.getWebElement()));
        releaseCustodianConfirmationOk.Click();

    }

    public void removeCustodian(){
        driver.FindElementByXPath("//img[@title='View Dropdown']").waitAndClick(30);
        Element removeCustodianOption = driver.FindElementByXPath("//a[normalize-space()='Remove Custodian']");
        wait.until(ExpectedConditions.elementToBeClickable(removeCustodianOption.getWebElement()));
        removeCustodianOption.waitAndClick(30);

        Element removeCustodianConfirmationOk = driver.FindElementById("remove-custodian-btn");
        wait.until(ExpectedConditions.elementToBeClickable(removeCustodianConfirmationOk.getWebElement()));
        removeCustodianConfirmationOk.Click();
    }
}


