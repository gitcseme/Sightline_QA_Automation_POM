package testScriptsRegressionSprint24;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.LoginPage;
import pageFactory.ReusableDocViewPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class CodingForm_Regrression_24 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Input ip;
	Utility utility;
	SessionSearch sessionSearch;
	AssignmentsPage assignPage;
	CodingForm codingForm;

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		Input in = new Input();
		in.loadEnvConfig();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());

		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		codingForm = new CodingForm(driver);

	}
	/**
	 * @throws Exception 
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Verify that CF for Special Objects (Radio Group) saved with default action and field logic.
	 */
	@Test(description = "RPMXCON-54542",enabled = true, groups = { "regression" })
	public void verifydefaultActionAndFieldLogicValidation() throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-54542");
	    baseClass.stepInfo("Verify that CF for Special Objects (Radio Group) saved with default action and field logic.");
	    String actionName = "Make this Required";
	    String codingform = "CF"+Utility.dynamicNameAppender();	   
	    //Create coding form as per attachment
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		SoftAssert softAssertion = new SoftAssert();
		
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    driver.waitForPageToBeReady();
	    baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
		codingForm.getAddNewCodingFormBtn().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
		//Add tags
	    codingForm.CreateCodingFormWithParameter(codingform,"Responsive",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Technical_Issue",null,null,"tag");	  
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Processing_Issue",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Foreign_Language",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    baseClass.stepInfo("All tags are added to coding form");
	    //Add special objects
	    driver.scrollPageToTop();
	    codingForm.getCF_CheckGrpObject().waitAndClick(10);	    
	    codingForm.addcodingFormAddButton();
	    codingForm.enterObjectName(4, "Responsive Group");
	    codingForm.selectDefaultActions(4, Input.required);
	    codingForm.enterErrorAndHelpMsg(4, "Yes", "Responsiveness", "You must select one of these options");
	    codingForm.selectTagTypeByIndex("check item",1,0);
	    codingForm.selectTagTypeByIndex("check item",1,1);	
	    codingForm.enterErrorAndHelpMsg(1, "No", "Is there some reason that review cannot be determined?", null);
	    driver.scrollPageToTop();
	    codingForm.getCF_RadioGrpObject().waitAndClick(10);
	    codingForm.addcodingFormAddButton();
	    codingForm.enterObjectName(5, "Tech Issue Group");
	    codingForm.selectDefaultActions(5,Input.hidden);
	    codingForm.enterErrorAndHelpMsg(5, "Yes", "Tech Issue", "If the document has a technical issue and cannot be reviewed, you must select a reason why from this list above");
	    codingForm.selectTagTypeByIndex("check item",1,0);
	    codingForm.selectTagTypeByIndex("check item",1,1);	
	    codingForm.enterErrorAndHelpMsg(2, "No", "Does this doc contain some type of issue that prohibits the ability for the record to be reviewed?", null);
	    codingForm.enterErrorAndHelpMsg(3, "No", "Does this doc contain some language besides what you can review?", null);
	    codingForm.selectTagTypeByIndex("radio item",1,2);
	    codingForm.selectTagTypeByIndex("radio item",1,3);	    
	    String expectedFirstObjectName = codingForm.getCFObjectsLabel(1);
	    codingForm.selectFieldLogicValues(5,expectedFirstObjectName,"Selected",actionName);
	    codingForm.saveCodingForm();
	    //validations
	    codingForm.editCodingForm(codingform);		 
		 //validate
	    codingForm.clickPreviewButon();
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Responsive"));
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Technical_Issue"));
	    baseClass.elementNotdisplayed(codingForm.getCFPreviewObjectName("Processing_Issue"), "Processing issue tag");
	    baseClass.elementNotdisplayed(codingForm.getCFPreviewObjectName("Foreign_Language"), "Foreign language tag");
	    baseClass.passedStep("Responsive and technical issue tag alone is displayed as expected");
	    codingForm.selectObjectsInPreviewBox("Technical_Issue").waitAndClick(5);
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Processing_Issue"));
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Foreign_Language"));
	    baseClass.passedStep("The field logic applied in the coding form working as expected");
		//delete codingform
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.deleteCodingForm(codingform, codingform);
	    codingForm.verifyCodingFormIsDeleted(codingform);
	    softAssertion.assertAll();
	    loginPage.logout();
	}
	/**
	 * @throws Exception 
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Verify that CF for Verify that default action and field logic (Special Objects - Radio Group) works in the context of a document review in Parent Window
	 */
	@Test(description = "RPMXCON-54543",enabled = true, groups = { "regression" })
	public void verifydefaultActionAndFieldLogicValidationInParentWindow() throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-54543");
	    baseClass.stepInfo("Verify that default action and field logic (Special Objects - Radio Group) works in the context of a document review in Parent Window");
	    String actionName = "Make this Required";
	    String codingform = "CF"+Utility.dynamicNameAppender();	   
	    //Create coding form as per attachment
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		SoftAssert softAssertion = new SoftAssert();
		SessionSearch sessionSearch = new SessionSearch(driver);	
		ReusableDocViewPage rd = new ReusableDocViewPage(driver);
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    driver.waitForPageToBeReady();
	    baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
		codingForm.getAddNewCodingFormBtn().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
		//Add tags
	    codingForm.CreateCodingFormWithParameter(codingform,"Responsive",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Technical_Issue",null,null,"tag");	  
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Processing_Issue",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Foreign_Language",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    baseClass.stepInfo("All tags are added to coding form");
	    //Add special objects
	    driver.scrollPageToTop();
	    codingForm.getCF_CheckGrpObject().waitAndClick(10);	    
	    codingForm.addcodingFormAddButton();
	    codingForm.enterObjectName(4, "Responsive Group");
	    codingForm.selectDefaultActions(4, Input.required);
	    codingForm.enterErrorAndHelpMsg(4, "Yes", "Responsiveness", "You must select one of these options");
	    codingForm.selectTagTypeByIndex("check item",1,0);
	    codingForm.selectTagTypeByIndex("check item",1,1);	
	    codingForm.enterErrorAndHelpMsg(1, "No", "Is there some reason that review cannot be determined?", null);
	    driver.scrollPageToTop();
	    codingForm.getCF_RadioGrpObject().waitAndClick(10);
	    codingForm.addcodingFormAddButton();
	    codingForm.enterObjectName(5, "Tech Issue Group");
	    codingForm.selectDefaultActions(5,Input.hidden);
	    codingForm.enterErrorAndHelpMsg(5, "Yes", "Tech Issue", "If the document has a technical issue and cannot be reviewed, you must select a reason why from this list above");
	    codingForm.selectTagTypeByIndex("check item",1,0);
	    codingForm.selectTagTypeByIndex("check item",1,1);	
	    codingForm.enterErrorAndHelpMsg(2, "No", "Does this doc contain some type of issue that prohibits the ability for the record to be reviewed?", null);
	    codingForm.enterErrorAndHelpMsg(3, "No", "Does this doc contain some language besides what you can review?", null);
	    codingForm.selectTagTypeByIndex("radio item",1,2);
	    codingForm.selectTagTypeByIndex("radio item",1,3);	    
	    String expectedFirstObjectName = codingForm.getCFObjectsLabel(1);
	    codingForm.selectFieldLogicValues(5,expectedFirstObjectName,"Selected",actionName);
	    codingForm.saveCodingForm();
	    //validations
	    codingForm.assignCodingFormToSG(codingform);
		sessionSearch.basicContentSearch(Input.TallySearch);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(codingForm.getCFPreviewObjectName("Responsive"));
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Responsive"));
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Technical_Issue"));
	    baseClass.elementNotdisplayed(codingForm.getCFPreviewObjectName("Processing_Issue"), "Processing issue tag");
	    baseClass.elementNotdisplayed(codingForm.getCFPreviewObjectName("Foreign_Language"), "Foreign language tag");
	    baseClass.passedStep("Responsive and technical issue tag alone is displayed as expected");
	    baseClass.waitTillElemetToBeClickable(codingForm.selectObjectsInPreviewBox("Technical_Issue"));
	    codingForm.selectObjectsInPreviewBox("Technical_Issue").waitAndClick(25);
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Processing_Issue"));
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Foreign_Language"));
	    baseClass.passedStep("The field logic applied in the coding form is working in parent window as expected");
	    driver.Navigate().refresh();
	    baseClass.handleAlert();
	    codingForm.assignCodingFormToSG(Input.codeFormName);
	    //delete codingform
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.deleteCodingForm(codingform, codingform);
	    codingForm.verifyCodingFormIsDeleted(codingform);
	    softAssertion.assertAll();
	    loginPage.logout();
	}
	/**
	 * @throws Exception 
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Verify that default action and field logic (Special Objects - Radio Group) works in the context of a document review in Child Window.
	 */
	@Test(description = "RPMXCON-54544",enabled = true, groups = { "regression" })
	public void verifydefaultActionAndFieldLogicValidationInChildWindow() throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-54544");
	    baseClass.stepInfo("Verify that default action and field logic (Special Objects - Radio Group) works in the context of a document review in Child Window.");
	    String actionName = "Make this Required";
	    String codingform = "CF"+Utility.dynamicNameAppender();	   
	    //Create coding form as per attachment
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		SoftAssert softAssertion = new SoftAssert();
		SessionSearch sessionSearch = new SessionSearch(driver);	
		ReusableDocViewPage rd = new ReusableDocViewPage(driver);
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    driver.waitForPageToBeReady();
	    baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
		codingForm.getAddNewCodingFormBtn().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
		//Add tags
	    codingForm.CreateCodingFormWithParameter(codingform,"Responsive",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Technical_Issue",null,null,"tag");	  
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Processing_Issue",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Foreign_Language",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    baseClass.stepInfo("All tags are added to coding form");
	    //Add special objects
	    driver.scrollPageToTop();
	    codingForm.getCF_CheckGrpObject().waitAndClick(10);	    
	    codingForm.addcodingFormAddButton();
	    codingForm.enterObjectName(4, "Responsive Group");
	    codingForm.selectDefaultActions(4, Input.required);
	    codingForm.enterErrorAndHelpMsg(4, "Yes", "Responsiveness", "You must select one of these options");
	    codingForm.selectTagTypeByIndex("check item",1,0);
	    codingForm.selectTagTypeByIndex("check item",1,1);	
	    codingForm.enterErrorAndHelpMsg(1, "No", "Is there some reason that review cannot be determined?", null);
	    driver.scrollPageToTop();
	    codingForm.getCF_RadioGrpObject().waitAndClick(10);
	    codingForm.addcodingFormAddButton();
	    codingForm.enterObjectName(5, "Tech Issue Group");
	    codingForm.selectDefaultActions(5,Input.hidden);
	    codingForm.enterErrorAndHelpMsg(5, "Yes", "Tech Issue", "If the document has a technical issue and cannot be reviewed, you must select a reason why from this list above");
	    codingForm.selectTagTypeByIndex("check item",1,0);
	    codingForm.selectTagTypeByIndex("check item",1,1);	
	    codingForm.enterErrorAndHelpMsg(2, "No", "Does this doc contain some type of issue that prohibits the ability for the record to be reviewed?", null);
	    codingForm.enterErrorAndHelpMsg(3, "No", "Does this doc contain some language besides what you can review?", null);
	    codingForm.selectTagTypeByIndex("radio item",1,2);
	    codingForm.selectTagTypeByIndex("radio item",1,3);	    
	    String expectedFirstObjectName = codingForm.getCFObjectsLabel(1);
	    codingForm.selectFieldLogicValues(5,expectedFirstObjectName,"Selected",actionName);
	    codingForm.saveCodingForm();
	    //validations
	    codingForm.assignCodingFormToSG(codingform);
		sessionSearch.basicContentSearch(Input.TallySearch);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();
		rd.clickGearIconOpenCodingFormChildWindow();
		String parentWindow = rd.switchTochildWindow();
		baseClass.stepInfo("Child window is opened");
		baseClass.waitForElement(codingForm.getCFPreviewObjectName("Responsive"));
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Responsive"));
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Technical_Issue"));
	    baseClass.elementNotdisplayed(codingForm.getCFPreviewObjectName("Processing_Issue"), "Processing issue tag");
	    baseClass.elementNotdisplayed(codingForm.getCFPreviewObjectName("Foreign_Language"), "Foreign language tag");
	    baseClass.passedStep("Responsive and technical issue tag alone is displayed as expected");
	    baseClass.waitTillElemetToBeClickable(codingForm.selectObjectsInPreviewBox("Technical_Issue"));
	    codingForm.selectObjectsInPreviewBox("Technical_Issue").waitAndClick(25);
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Processing_Issue"));
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Foreign_Language"));
	    baseClass.passedStep("The field logic applied in the coding form is working in child window as expected");
	    rd.childWindowToParentWindowSwitching(parentWindow);
	    driver.Navigate().refresh();
	    baseClass.handleAlert();
	    codingForm.assignCodingFormToSG(Input.codeFormName);
	    //delete codingform
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.deleteCodingForm(codingform, codingform);
	    codingForm.verifyCodingFormIsDeleted(codingform);
	    softAssertion.assertAll();
	    loginPage.logout();
	}
	
	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		baseClass = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR CODINGFORM EXECUTED******");

	}
}