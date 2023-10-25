package legalhold.legalholdpagefactory.domain_setup.data_migration;

import automationLibrary.Driver;
import legalhold.legalholdpagefactory.domain_setup.DomainSetupTabNavigation;
import legalhold.legalholdpagefactory.domain_setup.DomainSetupTabs;
import legalhold.setup.BaseModule;
import legalhold.utilities.parse_locators.LocatorReader;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    public void openModalAndSelectFileForUpload(String filePathKey) throws InterruptedException {
        var btnUpload = driver.FindElementById(locatorReader.getobjectLocator("btnUploadFiles"));
        wait.until(ExpectedConditions.elementToBeClickable(btnUpload.getWebElement()));
        btnUpload.Click();
        var btnChooseFile = driver.FindElementById(locatorReader.getobjectLocator("btnChooseFile"));
        wait.until(ExpectedConditions.elementToBeClickable(btnChooseFile.getWebElement()));
        Thread.sleep(2000);
        btnChooseFile.SendKeys(reader.getFileName(filePathKey));
        Thread.sleep(2000);
        var btnUploadSubmit = driver.FindElementById(locatorReader.getobjectLocator("btnUploadSubmit"));
        btnUploadSubmit.Click();
    }


    public void checkMigrationResultStatus(String statusToCheck, long delay) throws InterruptedException {
        Thread.sleep(delay);
        var uploadStatusElement = driver.FindElementById(locatorReader.getobjectLocator("uploadFileStatus"));
        var statusString = uploadStatusElement.getText();

        Assert.assertEquals(statusString, statusToCheck);
    }

    public void downloadReportAndVerify() throws InterruptedException {
        var fileHandler = createOrCleanFiles();
        var expectedFileName = getFormatterFileName();

        var downloadReportDiv = driver.getWebDriver().findElement(By.id(locatorReader.getobjectLocator("btnReportDownload")));
        var btnDownloadReportLink = downloadReportDiv.findElement(By.tagName("a"));
        btnDownloadReportLink.click();

        // need this wait for the download of the file to finish
        // otherwise downloaded file name can't be retrieved
        Thread.sleep(2000);

        var files = fileHandler.listFiles();
        Assert.assertEquals(files.length, 1);
        Assert.assertEquals(expectedFileName, files[0].getName());
    }

    private String getFormatterFileName() {
        var prefix = "MigrationValidationReport";
        var currentDate = LocalDate.now();
        var formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return prefix + formattedDate + ".csv";
    }

    private File createOrCleanFiles() {
        File fileHandler = new File(fileDownloadPath);
        if (fileHandler.exists()) {
            for (var file: fileHandler.listFiles()) {
                file.delete();
            }
        }
        else {
            fileHandler.mkdirs();
        }
        return fileHandler;
    }
}
