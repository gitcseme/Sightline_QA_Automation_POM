package sightline.codingForms;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebElement;
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
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class CodingFormRegression_27 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Input ip;
	Utility utility;
	SessionSearch sessionSearch;
	AssignmentsPage assignPage;
	CodingForm codingForm;
	SoftAssert softAssert;
	DocViewPage docview;

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
		sessionSearch = new SessionSearch(driver);
		docview = new DocViewPage(driver);
		assignPage = new AssignmentsPage(driver);

	}

	/**
	 * @throws Exception 
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Verify that Coding Form Preview(Radio Button) works Properly in the context of a document review in Child Window in DocView
	 */
	@Test(description = "RPMXCON-54558",enabled = true, groups = { "regression" })
	public void verifyCfPreviewAsRadioBtn() throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-54558");
	    baseClass.stepInfo("Verify that Coding Form Preview(Radio Button) works Properly in the context of a document review in Child Window in DocView");
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
	    codingForm.getCF_RadioGrpObject().waitAndClick(10);	    
	    codingForm.addcodingFormAddButton();
	    codingForm.enterObjectName(4, "Responsive Group");
	    codingForm.selectDefaultActions(4, Input.optional);
	    codingForm.enterErrorAndHelpMsg(4, "No", "Responsiveness", null);
	    codingForm.selectTagTypeByIndex("radio item",1,0);
	    codingForm.selectTagTypeByIndex("radio item",1,1);	
	    codingForm.enterErrorAndHelpMsg(1, "No", "Is there some reason that review cannot be determined?", null);
	    driver.scrollPageToTop();
	    codingForm.getCF_CheckGrpObject().waitAndClick(10);
	    codingForm.addcodingFormAddButton();
	    codingForm.enterObjectName(5, "Tech Issue Group");
	    codingForm.selectDefaultActions(5,Input.hidden);
	    codingForm.enterErrorAndHelpMsg(5, "Yes", "Tech Issue", "If the document has a technical issue and cannot be reviewed, you must select a reason why from this list above");	
	    codingForm.enterErrorAndHelpMsg(2, "No", "Does this doc contain some type of issue that prohibits the ability for the record to be reviewed?", null);
	    codingForm.enterErrorAndHelpMsg(3, "No", "Does this doc contain some language besides what you can review?", null);
	    codingForm.selectTagTypeByIndex("check item",1,2);
	    codingForm.selectTagTypeByIndex("check item",1,3);	    
	    String expectedFirstObjectName = codingForm.getCFObjectsLabel(1);
	    codingForm.selectFieldLogicValues(5,expectedFirstObjectName,"Selected",actionName);
	    codingForm.saveCodingForm();
	    codingForm.assignCodingFormToSG(codingform);	    
	    sessionSearch.basicContentSearch("null");    
	    sessionSearch.ViewInDocView();
	    baseClass.stepInfo("Navigated to doc view page");
	    docview.clickGearIconOpenCodingFormChildWindow();
		String parentwindow=docview.switchTochildWindow();
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Responsive"));
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Technical_Issue"));
	    baseClass.elementNotdisplayed(codingForm.getCFPreviewObjectName("Processing_Issue"), "Processing issue tag");
	    baseClass.elementNotdisplayed(codingForm.getCFPreviewObjectName("Foreign_Language"), "Foreign language tag");
	    baseClass.passedStep("Responsive and technical issue tag alone is displayed as expected");
	    codingForm.selectObjectsInPreviewBox("Technical_Issue").waitAndClick(5);
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Processing_Issue"));
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Foreign_Language"));
	    baseClass.passedStep("The field logic applied in the coding form working as expected");
	    codingForm.selectObjectsInPreviewBox("Responsive").waitAndClick(15);
	    baseClass.waitTillElemetToBeClickable(docview.getCodingFormSaveButton());
	    docview.getCodingFormSaveButton().waitAndClick(10);
	    docview.childWindowToParentWindowSwitching(parentwindow);
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
	 * @Description : Verify that Coding Form Preview(CheckBox) works Properly in the context of a document review in Child Window in DocView
	 */
	@Test(description = "RPMXCON-54557",enabled = true, groups = { "regression" })
	public void verifyCfPreviewAsCheckBox() throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-54557");
	    baseClass.stepInfo("Verify that Coding Form Preview(CheckBox) works Properly in the context of a document review in Child Window in DocView");
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
	    codingForm.selectDefaultActions(4, Input.optional);
	    codingForm.enterErrorAndHelpMsg(4, "No", "Responsiveness", null);
	    codingForm.selectTagTypeByIndex("check item",1,0);
	    codingForm.selectTagTypeByIndex("check item",1,1);	
	    codingForm.enterErrorAndHelpMsg(1, "No", "Is there some reason that review cannot be determined?", null);
	    driver.scrollPageToTop();
	    codingForm.getCF_RadioGrpObject().waitAndClick(10);	
	   
	    codingForm.addcodingFormAddButton();
	    codingForm.enterObjectName(5, "Tech Issue Group");
	    codingForm.selectDefaultActions(5,Input.hidden);
	    codingForm.enterErrorAndHelpMsg(5, "Yes", "Tech Issue", "If the document has a technical issue and cannot be reviewed, you must select a reason why from this list above");	
	    codingForm.enterErrorAndHelpMsg(2, "No", "Does this doc contain some type of issue that prohibits the ability for the record to be reviewed?", null);
	    codingForm.enterErrorAndHelpMsg(3, "No", "Does this doc contain some language besides what you can review?", null);
	    codingForm.selectTagTypeByIndex("radio item",1,2);
	    codingForm.selectTagTypeByIndex("radio item",1,3);	    
	    String expectedFirstObjectName = codingForm.getCFObjectsLabel(1);
	    codingForm.selectFieldLogicValues(5,expectedFirstObjectName,"Selected",actionName);
	    codingForm.saveCodingForm();
	    codingForm.assignCodingFormToSG(codingform);	    
	    sessionSearch.basicContentSearch("null");    
	    sessionSearch.ViewInDocView();
	    baseClass.stepInfo("Navigated to doc view page");
	    docview.clickGearIconOpenCodingFormChildWindow();
		String parentwindow=docview.switchTochildWindow();
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Responsive"));
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Technical_Issue"));
	    baseClass.elementNotdisplayed(codingForm.getCFPreviewObjectName("Processing_Issue"), "Processing issue tag");
	    baseClass.elementNotdisplayed(codingForm.getCFPreviewObjectName("Foreign_Language"), "Foreign language tag");
	    baseClass.passedStep("Responsive and technical issue tag alone is displayed as expected");
	    codingForm.selectObjectsInPreviewBox("Technical_Issue").waitAndClick(5);
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Processing_Issue"));
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Foreign_Language"));
	    baseClass.passedStep("The field logic applied in the coding form working as expected");
	    codingForm.selectObjectsInPreviewBox("Technical_Issue").waitAndClick(15);
	    baseClass.elementNotdisplayed(codingForm.getCFPreviewObjectName("Processing_Issue"), "Processing issue tag");
	    baseClass.elementNotdisplayed(codingForm.getCFPreviewObjectName("Foreign_Language"), "Foreign language tag");
	    baseClass.waitTillElemetToBeClickable(docview.getCodingFormSaveButton());
	    docview.getCodingFormSaveButton().waitAndClick(10);
	    docview.childWindowToParentWindowSwitching(parentwindow);
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
	 * @Description : Verify on deleting of the assigned coding form/coding form objects message should be displayed.
	 */
	@Test(description = "RPMXCON-54163",enabled = true, groups = { "regression" })
	public void verifyErrorMsgDeletingCfAssignedSG() throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-54163");
	    baseClass.stepInfo("Verify on deleting of the assigned coding form/coding form objects message should be displayed.");
	    String codingformSG = "CF"+Utility.dynamicNameAppender();	 
	    String assignCf = "CF"+Utility.dynamicNameAppender();	 
	    String assignmentName = "assignment"+Utility.dynamicNameAppender();	 
	    //Create coding form as per attachment
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    driver.waitForPageToBeReady();
	    baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
		codingForm.getAddNewCodingFormBtn().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingformSG);
		//Add tags
	    codingForm.CreateCodingFormWithParameter(codingformSG,"Responsive",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    baseClass.stepInfo("All tags are added to coding form");
	    //Add special objects
	    driver.scrollPageToTop();	    
	    codingForm.getCF_CheckGrpObject().waitAndClick(10);    
	    codingForm.addcodingFormAddButton();
	    codingForm.enterObjectName(1, "Responsive Group");
	    codingForm.selectDefaultActions(1, Input.optional);
	    codingForm.enterErrorAndHelpMsg(1, "No", "Responsiveness", null);
	    codingForm.selectTagTypeByIndex("check item",1,0);
	    codingForm.saveCodingForm();
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    driver.waitForPageToBeReady();
	    baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
		codingForm.getAddNewCodingFormBtn().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(assignCf);
		//Add tags
	    codingForm.CreateCodingFormWithParameter(assignCf,"Responsive",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    baseClass.stepInfo("All tags are added to coding form");
	    //Add special objects
	    driver.scrollPageToTop();	    
	    codingForm.getCF_CheckGrpObject().waitAndClick(10);    
	    codingForm.addcodingFormAddButton();
	    codingForm.enterObjectName(1, "Responsive Group");
	    codingForm.selectDefaultActions(1, Input.optional);
	    codingForm.enterErrorAndHelpMsg(1, "No", "Responsiveness", null);
	    codingForm.selectTagTypeByIndex("check item",1,0);
	    codingForm.saveCodingForm();
	    codingForm.assignCodingFormToSG(codingformSG);	    
	    sessionSearch.basicContentSearch("crammer");    
	    sessionSearch.ViewInDocView();
	    baseClass.stepInfo("Navigated to doc view page");	    
	    baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Responsive"));
	    codingForm.selectObjectsInPreviewBox("Responsive").waitAndClick(15);
	    baseClass.waitTillElemetToBeClickable(docview.getCodingFormSaveButton());
	    docview.getCodingFormSaveButton().waitAndClick(10);
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		codingForm.getCodingForm_Search().SendKeys(codingformSG);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(codingForm.getCodingForm_DeleteButton(codingformSG));
		codingForm.getCodingForm_DeleteButton(codingformSG).waitAndClick(10);
		baseClass.VerifyErrorMessage("A coding form configured as default coding form for a security group or an assignment cannot be deleted.");
		baseClass.passedStep("Error message verified sucessfully when the coding form assigned to security group as expected");
		codingForm.assignCodingFormToSG(Input.codeFormName);		
		// Session search to doc view to create assignment
		baseClass.selectproject();
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignPage.assignmentCreation(assignmentName, assignCf);
		assignPage.assignmentDistributingToReviewer();
		// logout
		loginPage.logout();
		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");
		// Assignment Selection
		assignPage.SelectAssignmentByReviewer(assignmentName);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		baseClass.stepInfo("Navigated to doc view page");
		baseClass.elementDisplayCheck(codingForm.getCFPreviewObjectName("Responsive"));
		codingForm.selectObjectsInPreviewBox("Responsive").waitAndClick(15);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		codingForm.getCodingForm_Search().SendKeys(assignCf);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(codingForm.getCodingForm_DeleteButton(assignCf));
		codingForm.getCodingForm_DeleteButton(assignCf).waitAndClick(10);
		baseClass.VerifyErrorMessage(
				"A coding form configured as default coding form for a security group or an assignment cannot be deleted.");
		baseClass.passedStep("Error message verified sucessfully when coding form assigned to an assignment as expected");
		//delete codingform
	    codingForm.deleteCodingForm(codingformSG, codingformSG);
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
