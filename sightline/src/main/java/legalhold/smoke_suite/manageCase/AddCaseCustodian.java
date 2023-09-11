package legalhold.smoke_suite.manageCase;

import automationLibrary.Driver;
import automationLibrary.Element;
import legalhold.setup.BaseModule;
import legalhold.legalholdpagefactory.Module_Navigation;
import legalhold.legalholdpagefactory.cases.CaseFactories;
import legalhold.utilities.parse_locators.LocatorReader;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;

public class AddCaseCustodian extends BaseModule {

    LocatorReader reader;
    public AddCaseCustodian(Driver driver) throws IOException {
        super("src/main/java/legalhold/selectors/cases/manage_case/custodian/custodian.properties", driver);
        reader = new LocatorReader("src/main/java/legalhold/selectors/uploadFile.properties");
    }

    public void navigationToCustodianTab() throws IOException {

//        driver.waitForPageToBeReady();

        Module_Navigation caseTabNavigation = new Module_Navigation(driver);

        caseTabNavigation.navigateToCaseTAB();


        CaseFactories SearchCaseandGotoEdit = new CaseFactories(driver);
        SearchCaseandGotoEdit.goToEditCase("Automation_Test");

       CaseFactories navigateToCustodiansTab = new CaseFactories(driver);
       navigateToCustodiansTab.NavigateToCustodiansTab();


    }

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
}


