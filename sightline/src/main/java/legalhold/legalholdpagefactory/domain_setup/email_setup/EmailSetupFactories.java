package legalhold.legalholdpagefactory.domain_setup.email_setup;

import automationLibrary.Driver;
import legalhold.legalholdpagefactory.domain_setup.DomainSetupTabNavigation;
import legalhold.legalholdpagefactory.domain_setup.DomainSetupTabs;
import legalhold.legalholdpagefactory.domain_setup.ModalStatus;
import legalhold.setup.BaseModule;
import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.io.IOException;
import java.util.List;

public class EmailSetupFactories extends BaseModule {
    private DomainSetupTabNavigation domainSetupTabNavigation;

    public EmailSetupFactories(Driver driver) throws IOException {
        super("src/main/java/legalhold/selectors/domain_setup/email_setup/email_setup.properties", driver);
        domainSetupTabNavigation = new DomainSetupTabNavigation(driver);
    }

    public void goToEmailSetupTab() {
        domainSetupTabNavigation.navigateToDomainSetupTab(DomainSetupTabs.Email);
        var emailTab = driver.FindElementById(locatorReader.getobjectLocator("emailSetupTab"));

        var classes = emailTab.getWebElement().getAttribute("class");
        Assert.assertEquals("nav-link active", classes);

        var emailTextSpanElement = emailTab.FindElementByclassName("d-sm-block");
        var elementText = emailTextSpanElement.getText();
        Assert.assertEquals("Email", elementText);
    }

    public void putNameAndEmail(String name, String email) {
        var fromNameInput = driver.FindElementById(locatorReader.getobjectLocator("fromName"));
        fromNameInput.SendKeys(name);

        var fromEmailInput = driver.FindElementById(locatorReader.getobjectLocator("fromEmail"));
        fromEmailInput.SendKeys(email);
    }

    public void saveEmailSettingsAndVerify() throws InterruptedException {
        var saveButton = driver.FindElementById(locatorReader.getobjectLocator("saveButton"));
        wait.until(ExpectedConditions.elementToBeClickable(saveButton.getWebElement()));
        saveButton.Click();

        Thread.sleep(1000);
        var modalSaveConfirm = driver.FindElementById(locatorReader.getobjectLocator("modalSaveConfirm"));
        Assert.assertTrue(modalSaveConfirm.isDisplayed());

        var okButton = modalSaveConfirm.FindElementById(locatorReader.getobjectLocator("modalConfirmOkayBtn"));
        wait.until(ExpectedConditions.elementToBeClickable(okButton.getWebElement()));
        okButton.Click();

        Thread.sleep(1500);

        var toastContainer = driver.FindElementById(locatorReader.getobjectLocator("toastContainer"));
        Assert.assertTrue(toastContainer.isDisplayed());
        var toastMessageText = toastContainer.FindElementByclassName("toast-message").FindElement(By.tagName("h5")).getText();

        Assert.assertEquals("Success", toastMessageText);
    }

    public void selectSmtpServer(String server) {
        var emailServerDropdown = driver.FindElementById(locatorReader.getobjectLocator("emailServerDropdown"));
        emailServerDropdown.selectFromDropdown().selectByVisibleText(server);
    }

    public void fillClientServerFields(List<Pair<String, String>> smtpSettings) {
        for (var fieldInfo : smtpSettings) {
            var inputElement = driver.FindElementById(locatorReader.getobjectLocator(fieldInfo.getLeft()));
            inputElement.SendKeys(fieldInfo.getRight());
        }
    }

    public void checkTestConnectionStatus(ModalStatus status) throws InterruptedException {
        String modalLocatorName = getModalLocatorByStatus(status);

        var btnSmtpTestConnection = driver.FindElementById(locatorReader.getobjectLocator("btnSmtpTestConnection"));
        btnSmtpTestConnection.Click();

        Thread.sleep(6000);

        var modalTestConnection = driver.FindElementById(locatorReader.getobjectLocator(modalLocatorName));
        Assert.assertTrue(modalTestConnection.isDisplayed());

        Thread.sleep(1000);
        modalTestConnection.FindElement(By.tagName("a")).Click();
    }

    private String getModalLocatorByStatus(ModalStatus status) {
        if (status == ModalStatus.Loading) {
            return "modalTestConnectionLoading";
        }
        else if (status == ModalStatus.Failed) {
            return "modalTestConnectionFailed";
        }

        return "modalTestConnectionSuccess";
    }

}
