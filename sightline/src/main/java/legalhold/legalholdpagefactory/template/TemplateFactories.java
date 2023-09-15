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
        super("src/main/java/legalhold/selectors/template/templatelibrary.properties",driver);
        reader = new LocatorReader("src/main/java/legalhold/selectors/template/case_template/case_template.properties");
    }

    public void saveCaseTemplate() throws InterruptedException {
        Element btnSave = driver.FindElementById(reader.getobjectLocator("btnCaseTemplateSave"));
        wait.until(ExpectedConditions.elementToBeClickable(btnSave.getWebElement()));
        btnSave.waitAndClick(30);
        driver.FindElementById(reader.getobjectLocator("okBtnCaseTemplateSaveModal")).Click();

        WebElement successToast = driver.getWebDriver().findElement(By.id(reader.getobjectLocator("toastMessage")));
        wait.until(ExpectedConditions.visibilityOf(successToast));
        String toastMessage = successToast.getText();

        Assert.assertTrue(toastMessage.contains("Case template created successfull"));
        Thread.sleep(5000);
        driver.waitForPageToBeReady();
    }

    public String createRandomCaseTemplate(){
        driver.FindElementById(locatorReader.getobjectLocator("btnCreateTemplate")).Click();

        WebElement enterTemplateName = driver.getWebDriver().findElement(By.id(locatorReader.getobjectLocator("enterTemplateName")));
        wait.until(ExpectedConditions.elementToBeClickable(enterTemplateName));
        String templateName = faker.country().name() + " " + faker.company().name();
        enterTemplateName.sendKeys(templateName);

        driver.FindElementById(locatorReader.getobjectLocator("btnTemplateModalSubmit")).waitAndClick(30);
        driver.waitForPageToBeReady();
        return templateName;
    }

    public void applyCaseTemplate(String templateName){
        Element applyTemplateDropdown = driver.FindElementById("case-template-dropdown");
        wait.until(ExpectedConditions.elementToBeClickable(applyTemplateDropdown.getWebElement()));
        applyTemplateDropdown.selectFromDropdown().selectByVisibleText(templateName);
        driver.FindElementById("applyTemplateLink").Click();
        driver.FindElementById("apply-ok-btn").waitAndClick(30);
    }
}
