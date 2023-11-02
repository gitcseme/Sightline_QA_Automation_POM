package legalhold.legalholdpagefactory.domain_setup.data_source;

import automationLibrary.Driver;
import automationLibrary.Element;
import legalhold.setup.BaseModule;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;


public class DataSourceFactories extends BaseModule {
    public DataSourceFactories(Driver driver) throws IOException {
        super("src/main/java/legalhold/selectors/domain_setup/data_source/data_source.properties", driver);
    }

    public void goToCreateDataSourcePage() throws InterruptedException {
        var btnCreateDataSource = driver.FindElementByCssSelector(locatorReader.getobjectLocator("btnCreateDataSource"));
        wait.until(ExpectedConditions.elementToBeClickable(btnCreateDataSource.getWebElement()));
        btnCreateDataSource.waitAndClick(30);
        Thread.sleep(2500);
    }

    public void goBackToDataSourceTabFromEditPage() throws InterruptedException {
        driver.FindElementByLinkText(locatorReader.getobjectLocator("breadCrumbBackToDataSource")).waitAndClick(30);
        Thread.sleep(2500);
    }

    public String enterDataSourceNameAndDescription() {
        var inputDataSourceName = driver.FindElementById(locatorReader.getobjectLocator("inputDataSourceName"));
        wait.until(ExpectedConditions.elementToBeClickable(inputDataSourceName.getWebElement()));
        var dataSourceName = faker.beer().name() + " " + faker.gameOfThrones().character() + " " + faker.gameOfThrones().dragon();
        inputDataSourceName.Clear();
        inputDataSourceName.SendKeys(dataSourceName);
        var inputDescription = driver.FindElementById(locatorReader.getobjectLocator("inputDataSourceDescription"));
        inputDescription.Clear();
        inputDescription.SendKeys(faker.lorem().sentence(10));
        return dataSourceName;
    }

    public void enterDataSourceUrl(String url) {
        var inputDataSourceUrl = driver.FindElementById(locatorReader.getobjectLocator("inputDataSourceUrl"));
        wait.until(ExpectedConditions.elementToBeClickable(inputDataSourceUrl.getWebElement()));
        inputDataSourceUrl.Clear();
        inputDataSourceUrl.SendKeys(url);
    }

    public void openConfigModal() {
        driver.FindElementByCssSelector(locatorReader.getobjectLocator("btnConfigure")).waitAndClick(30);
    }

    public void saveConfigModal() {
        driver.FindElementById(locatorReader.getobjectLocator("okBtnConfigModal")).waitAndClick(30);
    }

    public void enterTenantID(String tenantId) {
        var inputTenantID = driver.FindElementById(locatorReader.getobjectLocator("inputTenantID"));
        wait.until(ExpectedConditions.elementToBeClickable(inputTenantID.getWebElement()));
        inputTenantID.Clear();
        inputTenantID.SendKeys(tenantId);
    }

    public void enterApplicationID(String applicationId) {
        var inputApplicationID = driver.FindElementById(locatorReader.getobjectLocator("inputApplicationID"));
        wait.until(ExpectedConditions.elementToBeClickable(inputApplicationID.getWebElement()));
        inputApplicationID.Clear();
        inputApplicationID.SendKeys(applicationId);
    }

    public void verifySuccessfulTestConnection() throws InterruptedException {
        var btnDataConnection = driver.FindElementByCssSelector(locatorReader.getobjectLocator("btnDataConnection"));
        wait.until(ExpectedConditions.elementToBeClickable(btnDataConnection.getWebElement()));
        btnDataConnection.waitAndClick(30);
        var statusInProgress = wait.until(ExpectedConditions.elementToBeClickable(driver.FindElementByXPath(locatorReader.getobjectLocator("statusInProgress")).getWebElement()));
        Thread.sleep(1500);
        statusInProgress.isDisplayed();
        Thread.sleep(15000);

        var successTestConnectionStatus = driver.FindElementByXPath(locatorReader.getobjectLocator("successTestConnectionMessage"));
        var successTestConnectionMessage = successTestConnectionStatus.getText();
        if (successTestConnectionMessage.equalsIgnoreCase("Connected successfully.")) {
            System.out.println("Test Connection Successful");
            driver.FindElementByCssSelector(locatorReader.getobjectLocator("btnCancelSuccessModal")).waitAndClick(30);
        } else {
            var errorMessage = driver.FindElementById(locatorReader.getobjectLocator("errorMessage")).getText();
            driver.FindElementByCssSelector(locatorReader.getobjectLocator("btnCancelFailedModal")).waitAndClick(30);
            throw new RuntimeException("Test Connection Failed. Error message: " + errorMessage);
        }
    }

    public void verifyFailedTestConnection() throws InterruptedException {
        var btnDataConnection = driver.FindElementByCssSelector(locatorReader.getobjectLocator("btnDataConnection"));
        wait.until(ExpectedConditions.elementToBeClickable(btnDataConnection.getWebElement()));
        btnDataConnection.waitAndClick(30);
        var statusInProgress = wait.until(ExpectedConditions.elementToBeClickable(driver.FindElementByXPath(locatorReader.getobjectLocator("statusInProgress")).getWebElement()));
        Thread.sleep(1500);
        statusInProgress.isDisplayed();
        Thread.sleep(7000);

        var failedTestConnectionStatus = driver.FindElementByXPath(locatorReader.getobjectLocator("failedTestConnectionMessage"));
        var failedTestConnectionMessage = failedTestConnectionStatus.getText();
        if (failedTestConnectionMessage.equalsIgnoreCase("Connection failed. Please try again!")) {
            System.out.println("Test Connection Failed");
            driver.FindElementByCssSelector(locatorReader.getobjectLocator("btnCancelFailedModal")).waitAndClick(30);
        } else {
            driver.FindElementByCssSelector(locatorReader.getobjectLocator("btnCancelSuccessModal")).waitAndClick(30);
            throw new RuntimeException("Test Connection did NOT failed");
        }
    }

    public void enterUserName(String userName) {
        var inputUserName = driver.FindElementById(locatorReader.getobjectLocator("inputUserName"));
        wait.until(ExpectedConditions.elementToBeClickable(inputUserName.getWebElement()));
        inputUserName.Clear();
        inputUserName.SendKeys(userName);
    }

    public void enterPassword(String password) {
        var inputPassword = driver.FindElementById(locatorReader.getobjectLocator("inputPassword"));
        wait.until(ExpectedConditions.elementToBeClickable(inputPassword.getWebElement()));
        inputPassword.Clear();
        inputPassword.SendKeys(password);
    }

    public void enterClientSecret(String clientSecret) {
        var inputClientSecret = driver.FindElementById(locatorReader.getobjectLocator("inputClientSecret"));
        wait.until(ExpectedConditions.elementToBeClickable(inputClientSecret.getWebElement()));
        inputClientSecret.Clear();
        inputClientSecret.SendKeys(clientSecret);
    }

    public void saveDataSource() throws InterruptedException {
        var btnSaveDataSource = wait.until(ExpectedConditions.elementToBeClickable(driver.FindElementById(locatorReader.getobjectLocator("btnSaveDataSource")).getWebElement()));
        btnSaveDataSource.click();
        Thread.sleep(6000);
    }

    public int findDataSourceRow(String dataSourceName) {
        var dataSourceRow = 0;
        wait.until(ExpectedConditions.elementToBeClickable(driver.FindElementById("PreservationDataSource-Id").getWebElement()));
        var rowCount = driver.FindElementsByXPath("//table[@id='PreservationDataSource-Id']//tbody//tr//td[1]");

        for (int i = 0; i < rowCount.size(); i++) {
            if (rowCount.getElementByIndex(i).getText().equalsIgnoreCase(dataSourceName)) {
                dataSourceRow = i + 1;
                break;
            }
        }
        if (dataSourceRow == 0) {
            throw new RuntimeException("Data Source was NOT found");
        } else {
            return dataSourceRow;
        }
    }

    public void goToEditDataSource(String dataSourceName) throws InterruptedException {
        var dataSourceRow = findDataSourceRow(dataSourceName);

        Element btnEditDataSource = driver.FindElementByXPath("//table[@id='PreservationDataSource-Id']//tbody//tr[" + dataSourceRow + "]//td[6]//img[1]");
        wait.until(ExpectedConditions.elementToBeClickable(btnEditDataSource.getWebElement()));
        btnEditDataSource.waitAndClick(30);
        Thread.sleep(2000);
        driver.waitForPageToBeReady();
    }

    public String getStatusOfDataSourceFromTable(String dataSourceName) {
        var dataSourceRow = findDataSourceRow(dataSourceName);

        Element statusCell = driver.FindElementByXPath("//table[@id='PreservationDataSource-Id']//tbody//tr[" + dataSourceRow + "]//td[4]");
        wait.until(ExpectedConditions.elementToBeClickable(statusCell.getWebElement()));
        return statusCell.getText();
    }

    public void verifyDataSourceStatusEnabled(String dataSourceName) {
        var status = getStatusOfDataSourceFromTable(dataSourceName);
        if (status.equalsIgnoreCase("Enabled")) {
            System.out.println("Data Source is Enabled");
        } else {
            throw new RuntimeException("Data Source is Disabled");
        }
    }

    public void verifyDataSourceStatusDisabled(String dataSourceName) {
        var status = getStatusOfDataSourceFromTable(dataSourceName);
        if (status.equalsIgnoreCase("Disabled")) {
            System.out.println("Data Source is Disabled");
        } else {
            throw new RuntimeException("Data Source is Enabled");
        }
    }
}
