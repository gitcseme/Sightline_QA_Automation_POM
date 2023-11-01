package legalhold.setup;

import automationLibrary.CustomWebDriverWait;
import automationLibrary.Driver;
import automationLibrary.Element;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import legalhold.ModuleStep.ModuleStep;
import legalhold.legalholdpagefactory.Module_Navigation;
import legalhold.utilities.parse_locators.LocatorReader;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
import pageFactory.BaseClass;
import pageFactory.DomainDashboard;
import pageFactory.LoginPage;
import testScriptsSmoke.Input;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class BaseModule {


    protected BaseClass bc;
    protected LocatorReader locatorReader;
    protected Driver driver;
    protected DomainDashboard domainDashboard;
    protected Faker faker;
    protected SoftAssert softAssert;
    protected WebDriverWait wait;
    List<ModuleStep> itemList;
    protected LoginPage login;
    protected Map<String, String> elementValueMapping = new HashMap<>();
    protected JavascriptExecutor jsExecutor;
    protected Module_Navigation navigation;

    protected final String fileDownloadPath = "C:\\BatchPrintFiles\\downloads";

    public BaseModule(String selectorFilename, Driver driver) throws IOException {
        this.driver = driver;
        locatorReader = new LocatorReader(selectorFilename);
        bc = new BaseClass(driver);
        domainDashboard = new DomainDashboard(driver);
        faker = new Faker();
        softAssert = new SoftAssert();
        wait = new CustomWebDriverWait(driver.getWebDriver(),30);
        login = new LoginPage(driver);
        jsExecutor = (JavascriptExecutor) driver.getWebDriver();
    }

    public BaseModule(Driver driver) throws IOException {
        this.driver = driver;
        bc = new BaseClass(driver);
        domainDashboard = new DomainDashboard(driver);
        faker = new Faker();
        softAssert = new SoftAssert();
        login = new LoginPage(driver);
        wait = new CustomWebDriverWait(driver.getWebDriver(),30);

    }

    public Module_Navigation getNavigation() throws IOException {
        return new Module_Navigation(driver);
    }


    public String GetValueFromElementName(String elementName) {
        System.out.println("This is a base class implementation");
        return "";
    }

    protected void LoadMoudleActionSteps(String stepFile){
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Read the JSON file into a List of Item objects
            itemList = objectMapper.readValue(new File(stepFile), new TypeReference<List<ModuleStep>>() {
            });

            // Now, you can work with the list of Item objects
            /*for (ModuleStep item : itemList) {
                System.out.println("ID: " + item.getId());
                System.out.println("Name: " + item.getName());
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void Run() {
        for (ModuleStep testStep : itemList) {
            String step = testStep.getStepName();
            String element = testStep.getElement();
            String action = testStep.getAction();
            String elementRef = testStep.getElementRef();


            switch (action) {
                case "logintosightline":
                    String userName = GetValueFromElementName("userName");
                    String password = GetValueFromElementName("password");
                    login.loginToSightLine(userName, password);
                    break;
                case "impersonateSAtoDA":
                    String tenant = GetValueFromElementName("tenant");
                    bc.impersonateSAtoDA(tenant);
                    break;
                case "WaitForDocumentReady":
                    driver.waitForPageToBeReady();
                    break;
                case "WaitUntil":
                    driver.WaitUntil((new Callable<Boolean>() {
                        public Boolean call() {
                            return driver.FindElementById(elementRef).Visible();
                        }
                    }), Input.wait30);
                    break;

                case "EnterText":
                    driver.FindElementById(elementRef).setText(GetValueFromElementName(testStep.getElement()));
                    break;
                case "Click":
                    GetElementFromElementRef(elementRef, testStep.getElementSelectorType()).Click();
                    break;
                    case "select":
                    var curElementRef= GetElementFromElementRef(elementRef, testStep.getElementSelectorType());
                        curElementRef.selectFromDropdown().selectByIndex(testStep.getSelectedIndex());
                    break;
                /*case "Verify Text":
                    String actualText = driver.findElement(By.id(element)).getText();
                    Assert.assertEquals(actualText, value);
                    break;*/
                default:
                    break;
                // Add more cases for other actions as needed
            }
        }
    }

    public Element GetElementFromElementRef(String elementRefId, String elementType) {

        if (elementType.equals("id"))
            return driver.FindElementById(elementRefId);
        else if (elementType.equals("css"))
            return driver.FindElementByCssSelector(elementRefId);
        else if (elementType.equals("xpath"))
            return driver.FindElementByXPath(elementRefId);



        return null;


    }

}
