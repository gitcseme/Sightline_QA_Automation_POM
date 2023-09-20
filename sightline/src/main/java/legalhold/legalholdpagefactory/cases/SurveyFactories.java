package legalhold.legalholdpagefactory.cases;

import automationLibrary.Driver;
import automationLibrary.Element;
import legalhold.setup.BaseModule;
import legalhold.utilities.parse_locators.LocatorReader;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.io.IOException;

public class SurveyFactories extends BaseModule {

    LocatorReader reader;
    public SurveyFactories(Driver driver) throws IOException {

        super("./src/main/java/legalhold/selectors/cases/casepage.properties", driver);
        reader = new LocatorReader("src/main/java/legalhold/selectors/cases/manage_case/survey/survey.properties");
    }

    public void gotoAddNewSurveyPage(){
        Element addNewSurveyButton = driver.FindElementById(reader.getobjectLocator("addNewSurveyBtn"));
        addNewSurveyButton.waitAndClick(10);


    }
    public void verifyPageHeader(String expectedHeader){
        Element pageHeader = driver.FindElementByXPath(reader.getobjectLocator("pageHeader"));
        wait.until(ExpectedConditions.elementToBeClickable(pageHeader.getWebElement()));
        Assert.assertEquals(pageHeader.getText(),expectedHeader);
    }
}
