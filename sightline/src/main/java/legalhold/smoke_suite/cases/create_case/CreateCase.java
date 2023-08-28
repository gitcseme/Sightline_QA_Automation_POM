package legalhold.smoke_suite.cases.create_case;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import cucumber.api.java.lv.Tad;
import legalhold.BaseModule;
import org.testng.Assert;
import org.testng.annotations.Test;
import scala.xml.Elem;
import testScriptsSmoke.Input;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Callable;

public class CreateCase extends BaseModule {

    public CreateCase(Driver driver) throws IOException {
        super("./src/main/java/legalhold/selectors/cases/casepage.properties", driver);
    }

    public String createRandomCase() {
        driver.get("https://legalholdpt.consilio.com/Case");
        driver.waitForPageToBeReady();
        Element createCaseButton = driver.FindElementByXPath(locatorReader.getobjectLocator("btnCreateCase"));
        driver.WaitUntil((new Callable<Boolean>() {

            public Boolean call() {
                return createCaseButton.Visible();
            }
        }), Input.wait30);
        createCaseButton.Click();
        Element enterCaseName = driver.FindElementById(locatorReader.getobjectLocator("enterCaseName"));
        String caseName = faker.food().dish() + " " + faker.animal().name();
        enterCaseName.SendKeys(caseName);
        Element ownerDropdown = driver.FindElementById(locatorReader.getobjectLocator("selectCaseOwner"));
        ElementCollection availableOwner = ownerDropdown.FindElementsBytagName("option");
//        try {
//
//            if (availableOwner.size() > 0) {
//                ownerDropdown.selectFromDropdown().selectByIndex(1);
//                driver.FindElementById(locatorReader.getobjectLocator("btnSubmit")).Click();
//                driver.waitForPageToBeReady();
//                String createdCaseName = driver.FindElementById("headerCaseName").getText();
//                softAssert.assertEquals(caseName, createdCaseName);
//                System.out.println("Case Name Asserted");
//
//            }
//
//        }
//
//        catch (Exception e){
//            System.out.println("No user available in domain");
//        }
//        return caseName;
        ownerDropdown.selectFromDropdown().selectByIndex(1);
        driver.FindElementById(locatorReader.getobjectLocator("btnSubmit")).Click();
        driver.waitForPageToBeReady();
        String createdCaseName = driver.FindElementById("headerCaseName").getText();
        softAssert.assertEquals(caseName, createdCaseName);
        System.out.println("Case Name Asserted");
        return caseName;
    }
}
