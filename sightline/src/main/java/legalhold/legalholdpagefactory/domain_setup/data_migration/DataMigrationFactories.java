package legalhold.legalholdpagefactory.domain_setup.data_migration;

import automationLibrary.Driver;
import legalhold.legalholdpagefactory.LHMenus;
import legalhold.legalholdpagefactory.Module_Navigation;
import legalhold.legalholdpagefactory.domain_setup.DomainSetupTabNavigation;
import legalhold.legalholdpagefactory.domain_setup.DomainSetupTabs;
import legalhold.setup.BaseModule;
import legalhold.utilities.parse_locators.LocatorReader;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.io.IOException;

public class DataMigrationFactories extends BaseModule {
    private DomainSetupTabNavigation domainSetupTabNavigation;
    private LocatorReader reader;

    public DataMigrationFactories(Driver driver) throws IOException {
        super("src/main/java/legalhold/selectors/domain_setup/data_migration/data_migration.properties", driver);
        reader = new LocatorReader("src/main/java/legalhold/selectors/uploadFile.properties");
        domainSetupTabNavigation = new DomainSetupTabNavigation(driver);
    }

    public void goToDataMigrationTab() throws InterruptedException{
        domainSetupTabNavigation.navigateToDomainSetupTab(DomainSetupTabs.Migration);
        Thread.sleep(2000);
    }

    public void openModalAndSelectFileForUpload() throws InterruptedException {
        var btnUpload = driver.FindElementById(locatorReader.getobjectLocator("btnUploadFiles"));
        wait.until(ExpectedConditions.elementToBeClickable(btnUpload.getWebElement()));
        btnUpload.Click();
        var btnChooseFile = driver.FindElementById(locatorReader.getobjectLocator("btnChooseFile"));
        wait.until(ExpectedConditions.elementToBeClickable(btnChooseFile.getWebElement()));
        btnChooseFile.SendKeys(reader.getFileName("MigrationZipFilePath"));
        var btnUploadSubmit = driver.FindElementById(locatorReader.getobjectLocator("btnUploadSubmit"));
        btnUploadSubmit.Click();
    }

    public void checkPendingStatus() throws InterruptedException {
        var uploadStatusElement = driver.FindElementById(locatorReader.getobjectLocator("uploadFileStatus"));
        var statusString = uploadStatusElement.getText();

        Assert.assertEquals(statusString, "In-Progress");
    }
}
