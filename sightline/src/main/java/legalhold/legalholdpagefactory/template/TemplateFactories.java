package legalhold.legalholdpagefactory.template;

import automationLibrary.Driver;
import automationLibrary.Element;
import legalhold.setup.BaseModule;
import legalhold.utilities.parse_locators.LocatorReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.io.IOException;

public class TemplateFactories extends BaseModule {
    LocatorReader reader;

    public TemplateFactories(Driver driver) throws IOException {
        super("src/main/java/legalhold/selectors/template/templatelibrary.properties", driver);
        reader = new LocatorReader("src/main/java/legalhold/selectors/template/case_template/case_template.properties");
    }

    public void saveCaseTemplate() throws InterruptedException {
        Element btnSave = driver.FindElementById(reader.getobjectLocator("btnCaseTemplateSave"));
        wait.until(ExpectedConditions.elementToBeClickable(btnSave.getWebElement()));
        btnSave.waitAndClick(30);
        driver.FindElementById(reader.getobjectLocator("okBtnCaseTemplateSaveModal")).Click();

        Thread.sleep(5000);
        driver.waitForPageToBeReady();
    }

    public String createRandomCaseTemplate() {
        driver.FindElementById(locatorReader.getobjectLocator("btnCreateTemplate")).Click();

        WebElement enterTemplateName = driver.getWebDriver().findElement(By.id(locatorReader.getobjectLocator("enterTemplateName")));
        wait.until(ExpectedConditions.elementToBeClickable(enterTemplateName));
        String templateName = faker.country().name() + " " + faker.company().name();
        enterTemplateName.sendKeys(templateName);

        driver.FindElementById(locatorReader.getobjectLocator("btnTemplateModalSubmit")).waitAndClick(30);
        driver.waitForPageToBeReady();
        return templateName;
    }

    public void applyCaseTemplate(String templateName) {
        Element applyTemplateDropdown = driver.FindElementById("case-template-dropdown");
        wait.until(ExpectedConditions.elementToBeClickable(applyTemplateDropdown.getWebElement()));
        applyTemplateDropdown.selectFromDropdown().selectByVisibleText(templateName);
        driver.FindElementById("applyTemplateLink").Click();
        driver.FindElementById("apply-ok-btn").waitAndClick(30);
    }

    public void searchTemplate(String templateName) throws InterruptedException {
        Element templateNameFilterBox = driver.FindElementByCssSelector(locatorReader.getobjectLocator("templateNameFilterBox"));
        wait.until(ExpectedConditions.elementToBeClickable(templateNameFilterBox.getWebElement()));
        templateNameFilterBox.SendKeys(templateName);
        Thread.sleep(4000);
        String expected_text = "Showing 1 to 1 of 1 entries";
        wait.until(ExpectedConditions.textToBe(By.id(locatorReader.getobjectLocator("rowCount")), expected_text));
    }

    public void searchNonExistentTemplate(String templateName) throws InterruptedException {
        Element templateNameFilterBox = driver.FindElementByCssSelector(locatorReader.getobjectLocator("templateNameFilterBox"));
        wait.until(ExpectedConditions.elementToBeClickable(templateNameFilterBox.getWebElement()));
        templateNameFilterBox.SendKeys(templateName);
        Thread.sleep(4000);
        String expected_text = "Showing 0 to 0 of 0 entries";
        wait.until(ExpectedConditions.textToBe(By.id(locatorReader.getobjectLocator("rowCount")), expected_text));
    }

    public void goToEditTemplatePage(String templateName) throws InterruptedException {
        searchTemplate(templateName);
        Element btnEditTemplate = driver.FindElementByCssSelector(locatorReader.getobjectLocator("btnEditTemplate"));
        wait.until(ExpectedConditions.elementToBeClickable(btnEditTemplate.getWebElement()));
        btnEditTemplate.waitAndClick(30);
        Thread.sleep(4000);
        driver.waitForPageToBeReady();
    }

    public void deleteTemplate() throws InterruptedException {
        Element btnDeleteTemplate = driver.FindElementByCssSelector(locatorReader.getobjectLocator("btnDeleteTemplate"));
        wait.until(ExpectedConditions.elementToBeClickable(btnDeleteTemplate.getWebElement()));
        btnDeleteTemplate.waitAndClick(30);
        driver.FindElementById(locatorReader.getobjectLocator("okBtnDeleteModal")).waitAndClick(30);
        Thread.sleep(3000);
    }

    public String cloneTemplate(String templateName) throws InterruptedException {
        searchTemplate(templateName);
        Element btnCloneTemplate = driver.FindElementByCssSelector(locatorReader.getobjectLocator("btnCloneTemplate"));
        wait.until(ExpectedConditions.elementToBeClickable(btnCloneTemplate.getWebElement()));
        btnCloneTemplate.waitAndClick(30);
        Element enterClonedTemplateName = driver.FindElementById(locatorReader.getobjectLocator("enterClonedTemplateName"));
        wait.until(ExpectedConditions.elementToBeClickable(enterClonedTemplateName.getWebElement()));
        String clonedTemplateName = faker.country().name() + " " + faker.company().name() + " Cloned";
        enterClonedTemplateName.SendKeys(clonedTemplateName);
        driver.FindElementById(locatorReader.getobjectLocator("okBtnClonedTemplateModal")).waitAndClick(30);
        Thread.sleep(5000);

        return clonedTemplateName;
    }
}
