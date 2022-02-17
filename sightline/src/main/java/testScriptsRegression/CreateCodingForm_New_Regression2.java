package testScriptsRegression;

import static org.testng.Assert.assertFalse;
import pageFactory.DocViewPage;
import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.ProjectPage;
import pageFactory.ReusableDocViewPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;
import pageFactory.CodingForm;

public class CreateCodingForm_New_Regression2 {
	Driver driver;
	LoginPage loginPage;
	SoftAssert softAssertion;
	CodingForm codingForm;
	SavedSearch savedSearch;
	ProjectPage projectPage;
	SecurityGroupsPage securityGroupPage;
	DocViewPage docViewPage;
	AssignmentsPage assignmentPage;
	BaseClass baseClass;
	SessionSearch sessionSearch;
	TagsAndFoldersPage  tagsAndFoldersPage;
	ReusableDocViewPage reusableDocView;
	MiniDocListPage miniDocList;
	
	String assgnCoding = "codingAssgn"+Utility.dynamicNameAppender();
	String codingform = "CFTags"+Utility.dynamicNameAppender();
	String assignment1 = "assignment"+Utility.dynamicNameAppender();
    String assignment2 = "assignment"+Utility.dynamicNameAppender();
    String cfName1 = "cf"+Utility.dynamicNameAppender();
    String cfName2 = "cf"+Utility.dynamicNameAppender();

	String helpMsg1 = "Is there some reason that review cannot determined?";
	String helpMsg2 = "Does this doc contain some type of issue that prohibits the ability for the record to be reviewed";
	String helpMsg3 = "Does this doc contain some language besides what you can review?";
	String errorMsg = "If the document has technical issue cannot be reviewed,you must select reason why from the list above";
	String navigationConfirmationMsg ="This action will not save your edits, please save your changes before navigating away from Doc View. Do you want to still navigate away without saving your changes ?";

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
	    in.loadEnvConfig();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();
		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		// Login as a RMU
		
		loginPage = new LoginPage(driver);
	    assignmentPage = new AssignmentsPage(driver);
	    sessionSearch = new SessionSearch(driver);
	    softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		reusableDocView = new ReusableDocViewPage(driver);	
		docViewPage = new DocViewPage(driver);
		miniDocList = new MiniDocListPage(driver);
	}
    
	/**
	 * @throws Exception 
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Verify that Coding Form Preview is working Properly when user configured Field logic as Part of Radio Group objects in Coding Form
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 1)
	public void verifyCFPreviewLogicValidations() throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-54555");
	    baseClass.stepInfo("Verify that Coding Form Preview is working Properly when user configured Field logic as Part of Radio Group objects in Coding Form");
	    String defaultAction = "Make It Hidden";
	    String actionName = "Make this Required";
	    String codingform = "CFTag"+Utility.dynamicNameAppender();	   
	    //Create coding form as per attachment
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
	    softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
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
	    codingForm.getCF_RadioGrpObject().waitAndClick(10);
	    codingForm.addcodingFormAddButton();
	    codingForm.selectTagTypeByIndex("radio item",1,0);
	    codingForm.selectTagTypeByIndex("radio item",1,1);
	    driver.scrollPageToTop();
	    codingForm.getCF_CheckGrpObject().waitAndClick(10);
	    codingForm.addcodingFormAddButton();
	    codingForm.selectTagTypeByIndex("check item",1,2);
	    codingForm.selectTagTypeByIndex("check item",1,3);	    
	    codingForm.enterObjectName(4, "Responsive Group");
	    codingForm.enterObjectName(5, "Tech Issue Group");
	    codingForm.selectDefaultActions(5, defaultAction);	    
	    baseClass.stepInfo("Two check group and one radio group are added to coding form");	    
	    codingForm.enterErrorAndHelpMsg(1, "No",helpMsg1,null);
	    codingForm.enterErrorAndHelpMsg(2, "No",helpMsg2,null);
	    codingForm.enterErrorAndHelpMsg(3, "No",helpMsg3,null);
	    baseClass.waitForElement(codingForm.getCodingForm_ErrorMsg(5));
	    codingForm.getCodingForm_ErrorMsg(5).SendKeys(errorMsg);
	    codingForm.enterErrorAndHelpMsg(4, "No","Responsiveness","");
	    String expectedFirstObjectName = codingForm.getCFObjectsLabel(1);
	    codingForm.selectFieldLogicValues(5,expectedFirstObjectName,"Selected",actionName);
	    codingForm.saveCodingForm();
	    //validations
	    codingForm.verifyCFlogicValidationInPreviewDisplay();
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
	 * @Description : Verify that Coding Form Preview (Radio Button) works Properly in the context of a document review in Parent Window in DocView
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 2)
	public void verifyCFLogicValidationsWithRadioInDocViewParentWindow() throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-54556");
	    baseClass.stepInfo("Verify that Coding Form Preview (Radio Button) works Properly in the context of a document review in Parent Window in DocView");
	    String defaultAction = "Make It Hidden";
	    String actionName = "Make this Required";
	    String codingform = "CFTag"+Utility.dynamicNameAppender();	   
	    //Create coding form as per attachment
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	    softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
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
	    codingForm.getCF_RadioGrpObject().waitAndClick(10);
	    codingForm.addcodingFormAddButton();
	    codingForm.selectTagTypeByIndex("radio item",1,0);
	    codingForm.selectTagTypeByIndex("radio item",1,1);
	    driver.scrollPageToTop();
	    codingForm.getCF_CheckGrpObject().waitAndClick(10);
	    codingForm.addcodingFormAddButton();
	    codingForm.selectTagTypeByIndex("check item",1,2);
	    codingForm.selectTagTypeByIndex("check item",1,3);	    
	    codingForm.enterObjectName(4, "Responsive Group");
	    codingForm.enterObjectName(5, "Tech Issue Group");
	    codingForm.selectDefaultActions(5, defaultAction);	    
	    baseClass.stepInfo("Two check group and one radio group are added to coding form");	    
	    codingForm.enterErrorAndHelpMsg(1, "No", helpMsg1,null);
	    codingForm.enterErrorAndHelpMsg(2, "No",helpMsg2,null);
	    codingForm.enterErrorAndHelpMsg(3, "No",helpMsg3,null);
	    baseClass.waitForElement(codingForm.getCodingForm_ErrorMsg(5));
	    codingForm.getCodingForm_ErrorMsg(5).SendKeys(errorMsg);
	    codingForm.enterErrorAndHelpMsg(4, "No","Responsiveness",null);
	    String expectedFirstObjectName = codingForm.getCFObjectsLabel(1);
	    codingForm.selectFieldLogicValues(5,expectedFirstObjectName,"Selected",actionName);
	    codingForm.saveCodingForm();
	    codingForm.assignCodingFormToSG(codingform);
	    sessionSearch = new SessionSearch(driver);
	    sessionSearch.basicContentSearch(Input.searchString2);
	    sessionSearch.ViewInDocView();
	    //validations
	    reusableDocView = new ReusableDocViewPage(driver);
	    codingForm.verifyCFlogicValidationsInDocViewPg("radio-group");
	    baseClass.stepInfo("All the validations of parent window are successfully verified");
	    reusableDocView.clickGearIconOpenCodingFormChildWindow();
	    String parentId = reusableDocView.switchTochildWindow();
	    codingForm.verifyCFlogicValidationsInDocViewPg("radio-group");
	    baseClass.stepInfo("All the validations of child window are successfully verified");
	    reusableDocView.childWindowToParentWindowSwitching(parentId);
		//delete codingform
	    codingForm.assignCodingFormToSG("Default Project Coding Form");
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.deleteCodingForm(codingform, codingform);
	    codingForm.verifyCodingFormIsDeleted(codingform);
	    softAssertion.assertAll();
	    loginPage.logout();
	}
	/**
	 * @throws Exception 
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Verify that Coding Form Preview is working Properly when user configured Field logic as Part of 
	 *                Radio Group objects in Coding Form in the context of a document in an assignment
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 3)
	public void verifyCFLogicValidationsUsingAssignmentsInDocviewPg() throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-54562");
	    baseClass.stepInfo("Verify that Coding Form Preview is working Properly when user configured Field logic as Part of "+
	                       "Radio Group objects in Coding Form in the context of a document in an assignment");
	    String defaultAction = "Make It Hidden";
	    String actionName = "Make this Required";
	    String codingform = "CFTag"+Utility.dynamicNameAppender();	
	    String assignmentName = "CFassignment"+Utility.dynamicNameAppender();
	    //Create coding form as per attachment
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	    softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
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
	    codingForm.getCF_RadioGrpObject().waitAndClick(10);
	    codingForm.addcodingFormAddButton();
	    codingForm.selectTagTypeByIndex("radio item",1,0);
	    codingForm.selectTagTypeByIndex("radio item",1,1);
	    driver.scrollPageToTop();
	    codingForm.getCF_CheckGrpObject().waitAndClick(10);
	    codingForm.addcodingFormAddButton();
	    codingForm.selectTagTypeByIndex("check item",1,2);
	    codingForm.selectTagTypeByIndex("check item",1,3);	    
	    codingForm.enterObjectName(4, "Responsive Group");
	    codingForm.enterObjectName(5, "Tech Issue Group");
	    codingForm.selectDefaultActions(5, defaultAction);	    
	    baseClass.stepInfo("Two check group and one radio group are added to coding form");	    
	    codingForm.enterErrorAndHelpMsg(1, "No", helpMsg1,null);
	    codingForm.enterErrorAndHelpMsg(2, "No",helpMsg2,null);
	    codingForm.enterErrorAndHelpMsg(3, "No",helpMsg3,null);
	    baseClass.waitForElement(codingForm.getCodingForm_ErrorMsg(5));
	    codingForm.getCodingForm_ErrorMsg(5).SendKeys(errorMsg);
	    codingForm.enterErrorAndHelpMsg(4, "No","Responsiveness",null);
	    String expectedFirstObjectName = codingForm.getCFObjectsLabel(1);
	    codingForm.selectFieldLogicValues(5,expectedFirstObjectName,"Selected",actionName);
	    codingForm.saveCodingForm();
	    assignmentPage = new AssignmentsPage(driver);
	    sessionSearch = new SessionSearch(driver);
	 	sessionSearch.basicContentSearch("null");
	 	sessionSearch.bulkAssign();
	 	assignmentPage.assignmentCreation(assignmentName, codingform);
	 	assignmentPage.assignmentDistributingToReviewer();	 	
	 	loginPage.logout();
	 	// Login as Reviewer
	 	loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
	 	baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");
 		// Assignment Selection
 		assignmentPage.SelectAssignmentByReviewer(assignmentName);
 		baseClass.stepInfo("User on the doc view after selecting the assignment");
 		codingForm.verifyCFlogicValidationsAsRevInDocViewPg("radio-group");
 		loginPage.logout();
 		//delete codingform
 		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	    assignmentPage.deleteAssgnmntUsingPagination(assignmentName);
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.deleteCodingForm(codingform, codingform);
	    codingForm.verifyCodingFormIsDeleted(codingform);
	    softAssertion.assertAll();
	    loginPage.logout();
	}
	/**
	 * @throws Exception 
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Verify that Coding Form Preview (CheckBox) works Properly in the context of a document review in Parent Window in DocView
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 4)
	public void verifyCFLogicValidationsWithCheckTypeInDocViewParentWindow() throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-54559");
	    baseClass.stepInfo("Verify that Coding Form Preview (CheckBox) works Properly in the context of a document review in Parent Window in DocView");
	    String defaultAction = "Make It Hidden";
	    String actionName = "Make this Required";
	    String codingform = "CFTag"+Utility.dynamicNameAppender();	   
	    //Create coding form as per attachment
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	    softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
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
	    codingForm.getCF_CheckGrpObject().waitAndClick(10);
	    codingForm.addcodingFormAddButton();
	    codingForm.selectTagTypeByIndex("check item",1,0);
	    codingForm.selectTagTypeByIndex("check item",1,1);
	    driver.scrollPageToTop();
	    codingForm.getCF_RadioGrpObject().waitAndClick(10);
	    codingForm.addcodingFormAddButton();
	    codingForm.selectTagTypeByIndex("radio item",1,2);
	    codingForm.selectTagTypeByIndex("radio item",1,3);
	    codingForm.enterObjectName(4, "Responsive Group");
	    codingForm.enterObjectName(5, "Tech Issue Group");	   
	    codingForm.selectDefaultActions(5, defaultAction);	    
	    baseClass.stepInfo("Two tags created with check group and two tags are created with radio group");	    
	    codingForm.enterErrorAndHelpMsg(1, "No", helpMsg1,null);
	    codingForm.enterErrorAndHelpMsg(2, "No",helpMsg2,null);
	    codingForm.enterErrorAndHelpMsg(3, "No",helpMsg3,null);
	    baseClass.waitForElement(codingForm.getCodingForm_ErrorMsg(5));
	    codingForm.getCodingForm_ErrorMsg(5).SendKeys(errorMsg);
	    codingForm.enterErrorAndHelpMsg(4, "No","Responsiveness",null);
	    String expectedFirstObjectName = codingForm.getCFObjectsLabel(1);
	    codingForm.selectFieldLogicValues(5,expectedFirstObjectName,"Selected",actionName);
	    codingForm.saveCodingForm();
	    codingForm.assignCodingFormToSG(codingform);
	    sessionSearch = new SessionSearch(driver);
	    sessionSearch.basicContentSearch(Input.searchString2);
	    sessionSearch.ViewInDocView();
	    //validations
	    reusableDocView = new ReusableDocViewPage(driver);
	    codingForm.verifyCFlogicValidationsInDocViewPg("check-group");
	    baseClass.stepInfo("All the validations of parent window are successfully verified");
	    reusableDocView.clickGearIconOpenCodingFormChildWindow();
	    String parentId = reusableDocView.switchTochildWindow();
	    codingForm.verifyCFlogicValidationsInDocViewPg("check-group");
	    baseClass.stepInfo("All the validations of child window are successfully verified");
	    reusableDocView.childWindowToParentWindowSwitching(parentId);
		//delete codingform
	    codingForm.assignCodingFormToSG("Default Project Coding Form");
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.deleteCodingForm(codingform, codingform);
	    codingForm.verifyCodingFormIsDeleted(codingform);
	    softAssertion.assertAll();
	    loginPage.logout();
	}
	/**
	 * @throws Exception 
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Verify that when user edit the Coding Form then User can edit default action and
	 *                field logic Special Objects.(Check Group)
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 5)
	public void verifyEditCFFieldLogicValidation() throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-54535");
	    baseClass.stepInfo("Verify that when user edit the Coding Form then User can edit default action and field logic Special Objects.(Check Group)");
	    String defaultAction = "Make It Required";
	    String actionName = "Make this Required";
	    String defaultAction2 = "Make It Display But Not Selectable";
	    String codingform = "CFTag"+Utility.dynamicNameAppender();	
	    //Create coding form as per attachment
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
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
	    codingForm.getCF_RadioGrpObject().waitAndClick(10);
	    codingForm.addcodingFormAddButton();
	    codingForm.selectTagTypeByIndex("radio item",1,0);
	    codingForm.selectTagTypeByIndex("radio item",1,1);
	    driver.scrollPageToTop();
	    codingForm.getCF_CheckGrpObject().waitAndClick(10);
	    codingForm.addcodingFormAddButton();
	    codingForm.selectTagTypeByIndex("check item",1,2);
	    codingForm.selectTagTypeByIndex("check item",1,3);	    
	    codingForm.enterObjectName(4, "Responsive Group");
	    codingForm.enterObjectName(5, "Tech Issue Group");
	    codingForm.selectDefaultActions(5, defaultAction);	    
	    baseClass.stepInfo("Two check group and one radio group are added to coding form");	    
	    codingForm.enterErrorAndHelpMsg(1, "No", helpMsg1,null);
	    codingForm.enterErrorAndHelpMsg(2, "No",helpMsg2,null);
	    codingForm.enterErrorAndHelpMsg(3, "No",helpMsg3,null);
	    baseClass.waitForElement(codingForm.getCodingForm_ErrorMsg(5));
	    codingForm.getCodingForm_ErrorMsg(5).SendKeys(errorMsg);
	    codingForm.enterErrorAndHelpMsg(4, "No","Responsiveness",null);
	    String expectedSecondObjectName = codingForm.getCFObjectsLabel(1);
	    String expectedFirstObjectName = codingForm.getCFObjectsLabel(0);
	    codingForm.selectFieldLogicValues(5,expectedSecondObjectName,"Selected",actionName);
	    codingForm.saveCodingForm();
	    //Edit coding form
	    codingForm.editCodingFormAfterSavingCodingForm(codingform);
	    driver.waitForPageToBeReady();
	    codingForm.getExtendCFobject("Tech Issue Group").waitAndClick(10);
	    codingForm.selectDefaultActions(5, defaultAction2);	
	    System.out.println(expectedFirstObjectName);
	    baseClass.waitForElement(codingForm.getCFFieldLogicObject(5));
	    codingForm.getCFFieldLogicObject(5).waitAndClick(10);	    
	    codingForm.getCFselectFieldLogicObject(expectedFirstObjectName,5).waitAndClick(10);
	    driver.scrollPageToTop();
	    baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
	    codingForm.getCF_PreviewButton().waitAndClick(10);
	    //validations
	    codingForm.verifyDisabledTagInPreviewDisplay("Processing_Issue");
	    codingForm.verifyDisabledTagInPreviewDisplay("Foreign_Language");
	    baseClass.passedStep("The tags are disabled as per field logic rule");
	    codingForm.selectObjectsInPreviewBox("Responsive").waitAndClick(10);
	    codingForm.validatePreviewErrorMsg();	  
	    baseClass.passedStep("Got error message successfully according to field logic rules as expected");
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
	 * @Description : Verify that default action and field logic (Special Objects - Check Group) works
	 *                in the context of a document review in Child Window.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 6)
	public void verifyCFLogicValidationsInDocViewChildWindow() throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-54539");
	    baseClass.stepInfo("Verify that default action and field logic (Special Objects - Check Group) works "+
	                       "in the context of a document review in Child Window.");
	    String defaultAction = "Make It Hidden";
	    String actionName = "Make this Required";
	    String codingform = "CFTag"+Utility.dynamicNameAppender();	   
	    //Create coding form as per attachment
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	    softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
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
	    codingForm.getCF_RadioGrpObject().waitAndClick(10);
	    codingForm.addcodingFormAddButton();
	    codingForm.selectTagTypeByIndex("radio item",1,0);
	    codingForm.selectTagTypeByIndex("radio item",1,1);
	    driver.scrollPageToTop();
	    codingForm.getCF_CheckGrpObject().waitAndClick(10);
	    codingForm.addcodingFormAddButton();
	    codingForm.selectTagTypeByIndex("check item",1,2);
	    codingForm.selectTagTypeByIndex("check item",1,3);	    
	    codingForm.enterObjectName(4, "Responsive Group");
	    codingForm.enterObjectName(5, "Tech Issue Group");
	    codingForm.selectDefaultActions(5, defaultAction);	    
	    baseClass.stepInfo("Two check group and one radio group are added to coding form");	    
	    codingForm.enterErrorAndHelpMsg(1, "No", helpMsg1,null);
	    codingForm.enterErrorAndHelpMsg(2, "No",helpMsg2,null);
	    codingForm.enterErrorAndHelpMsg(3, "No",helpMsg3,null);
	    baseClass.waitForElement(codingForm.getCodingForm_ErrorMsg(5));
	    codingForm.getCodingForm_ErrorMsg(5).SendKeys(errorMsg);
	    codingForm.enterErrorAndHelpMsg(4, "No","Responsiveness",null);
	    String expectedFirstObjectName = codingForm.getCFObjectsLabel(1);
	    codingForm.selectFieldLogicValues(5,expectedFirstObjectName,"Selected",actionName);
	    codingForm.saveCodingForm();
	    codingForm.assignCodingFormToSG(codingform);
	    baseClass.stepInfo("Default security group is assigned to out customized coding form successfully");
	    sessionSearch.basicContentSearch(Input.searchString2);
	    sessionSearch.ViewInDocView();
	    //validations	    
	    reusableDocView.clickGearIconOpenCodingFormChildWindow();
	    String parentId = reusableDocView.switchTochildWindow();
	    codingForm.verifyCFlogicValidationsInDocViewPg("radio-group");
	    baseClass.stepInfo("All the validations of child window are successfully verified");
	    reusableDocView.childWindowToParentWindowSwitching(parentId);
		//delete codingform
	    codingForm.assignCodingFormToSG("Default Project Coding Form");
	    baseClass.stepInfo("Default security group is reverted to Default project coding form successfully");
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.deleteCodingForm(codingform, codingform);
	    codingForm.verifyCodingFormIsDeleted(codingform);
	    softAssertion.assertAll();
	    loginPage.logout();
	}
	/**
	 * @throws Exception 
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Doc View: On navigating to other page after editing the coding form then from confirmation navigation message
	 *                on click of 'Yes' it should redirect to the respective page
	 */
	@Test(enabled = true, dataProvider = "UsersWithoutPA", groups = { "regression" }, priority = 7)
	public void verifyNavigationMsgByNavigateToOtherPg(String userName, String password) throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-51621");
	    baseClass.stepInfo("Doc View: On navigating to other page after editing the coding form then from confirmation navigation message"
	    		+ " on click of 'Yes' it should redirect to the respective page");  
	    loginPage.loginToSightLine(userName, password);
	    sessionSearch.basicContentSearch("null");
	    sessionSearch.ViewInDocView();
	    baseClass.waitForElement(codingForm.selectObjectsInPreviewBox("Technical_Issue"));
	    codingForm.selectObjectsInPreviewBox("Technical_Issue").waitAndClick(10);
	    sessionSearch.getSearchBtn().waitAndClick(10);
	    baseClass.stepInfo("Search button is clicked from navigation buttons");
	    baseClass.waitForElement(docViewPage.getNavigationMsg());
	    String actualMsg = docViewPage.getNavigationMsg().getText();
	    softAssertion.assertEquals(navigationConfirmationMsg, actualMsg);
	    baseClass.passedStep("Got navigation confirmation message successfully");
	    docViewPage.verifyNavigationPopUpButtons();
	    docViewPage.getNavigationMsgPopupYesBtn().waitAndClick(10);
	    driver.getWebDriver().get(Input.url + "Search/Searches");
	    sessionSearch.ViewInDocView();
	    driver.waitForPageToBeReady();
	    baseClass.waitForElement(codingForm.selectObjectsInPreviewBox("Technical_Issue"));
	    String value = codingForm.getObjectsInPreviewBox("Responsive").GetAttribute("checked");
	    softAssertion.assertEquals(value, "true");
	    baseClass.passedStep("No changes are saved after clicking yes button to navigate some other page");
	    softAssertion.assertAll();
	    loginPage.logout();
	}
	/**
	 * @throws Exception 
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Doc View: On click of Edit Profile link after editing the coding form then from confirmation navigation message
	 *                on click of 'Yes' it should redirect to the respective page
	 */
	@Test(enabled = true,dataProvider = "UsersWithoutPA", groups = { "regression" }, priority = 8)
	public void verifyNavigationMsgByClickEditProfile(String username, String password) throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-51622");
	    baseClass.stepInfo("Doc View: On click of Edit Profile link after editing the coding form then from confirmation navigation message"
	    		+ "	on click of 'Yes' it should redirect to the respective page");  
	    loginPage.loginToSightLine(username, password);
	    sessionSearch.basicContentSearch("null");
	    sessionSearch.ViewInDocView();
	    baseClass.waitForElement(codingForm.selectObjectsInPreviewBox("Technical_Issue"));
	    codingForm.selectObjectsInPreviewBox("Technical_Issue").waitAndClick(10);
	    baseClass.waitForElement(baseClass.getSignoutMenu());
	    baseClass.getSignoutMenu().waitAndClick(5);
		baseClass.waitForElement(docViewPage.getEditProfile());
		docViewPage.getEditProfile().waitAndClick(10);
		baseClass.stepInfo("Edit profile button is clicked");
	    baseClass.waitForElement(docViewPage.getNavigationMsg());
	    String actualMsg = docViewPage.getNavigationMsg().getText();
	    softAssertion.assertEquals(navigationConfirmationMsg, actualMsg);
	    baseClass.passedStep("Got navigation confirmation message successfully");
	    docViewPage.verifyNavigationPopUpButtons();
	    docViewPage.getNavigationMsgPopupYesBtn().waitAndClick(10);
	    driver.getWebDriver().get(Input.url + "Search/Searches");
	    sessionSearch.ViewInDocView();
	    driver.waitForPageToBeReady();
	    baseClass.waitForElement(codingForm.getObjectsInPreviewBox("Responsive"));
	    String value = codingForm.getObjectsInPreviewBox("Responsive").GetAttribute("checked");
	    softAssertion.assertEquals(value, "true");
	    baseClass.passedStep("No changes are saved after clicking yes button to navigate some other page");
	    softAssertion.assertAll();
	    loginPage.logout();
	}
	/**
	 * @throws Exception 
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Doc View: On click of Change Role link after editing the coding form then from confirmation navigation
	 *                message on click of 'Yes' it should redirect to the respective page
	 */
	@Test(enabled = true,dataProvider = "UsersWithoutPA", groups = { "regression" }, priority = 9)
	public void verifyNavigationMsgByClickChangeRole(String username, String password) throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-51623");
	    baseClass.stepInfo("Doc View: On click of Change Role link after editing the coding form then from confirmation navigation"+
	                       " message on click of 'Yes' it should redirect to the respective page");  
	    loginPage.loginToSightLine(username, password);
	    if(username==Input.rmu1userName) {
	    	baseClass.stepInfo("Logged in as rmu user");
	    }else {
	    	baseClass.stepInfo("Logged in as reviewer");
	    }
	    sessionSearch.basicContentSearch("null");
	    sessionSearch.ViewInDocView();
	    baseClass.stepInfo("Navigated to doc view page");
	    baseClass.waitForElement(codingForm.selectObjectsInPreviewBox("Technical_Issue"));
	    codingForm.selectObjectsInPreviewBox("Technical_Issue").waitAndClick(10);
	    baseClass.waitForElement(baseClass.getSignoutMenu());
	    baseClass.getSignoutMenu().waitAndClick(5);
		baseClass.waitForElement(baseClass.getChangeRole());
		baseClass.getChangeRole().waitAndClick(10);
		baseClass.stepInfo("Change role action performed");
	    baseClass.waitForElement(docViewPage.getNavigationMsg());
	    String actualMsg = docViewPage.getNavigationMsg().getText();
	    softAssertion.assertEquals(navigationConfirmationMsg, actualMsg);
	    baseClass.passedStep("Got navigation confirmation message successfully");
	    docViewPage.verifyNavigationPopUpButtons();
	    docViewPage.getNavigationMsgPopupYesBtn().waitAndClick(10);
	    driver.getWebDriver().get(Input.url + "Search/Searches");
	    sessionSearch.ViewInDocView();
	    driver.waitForPageToBeReady();
	    baseClass.waitForElement(codingForm.getObjectsInPreviewBox("Responsive"));
	    String value = codingForm.getObjectsInPreviewBox("Responsive").GetAttribute("checked");
	    softAssertion.assertEquals(value, "true");
	    baseClass.passedStep("No changes are saved after clicking yes button to navigate some other page");
	    softAssertion.assertAll();
	    loginPage.logout();
	}
	/**
	 * @throws Exception 
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Doc View: On click of Sign Out link after editing the coding form then from confirmation navigation message on click of 'Yes' 
	 *                it should redirect to the respective page
	 */
	@Test(enabled = true,dataProvider = "UsersWithoutPA", groups = { "regression" }, priority = 10)
	public void verifyNavigationMsgByClickSignOut(String username, String password) throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-51624");
	    baseClass.stepInfo("Doc View: On click of Sign Out link after editing the coding form then from confirmation navigation"+
	                      " message on click of 'Yes' it should redirect to the respective page");  
	    loginPage.loginToSightLine(username, password);
	    if(username==Input.rmu1userName) {
	    	baseClass.stepInfo("Logged in as rmu user");
	    }else {
	    	baseClass.stepInfo("Logged in as reviewer");
	    }
	    sessionSearch.basicContentSearch("null");
	    sessionSearch.ViewInDocView();
	    baseClass.stepInfo("Navigated to doc view page");
	    baseClass.waitForElement(codingForm.selectObjectsInPreviewBox("Technical_Issue"));
	    codingForm.selectObjectsInPreviewBox("Technical_Issue").waitAndClick(10);
	    baseClass.waitForElement(baseClass.getSignoutMenu());
	    baseClass.getSignoutMenu().waitAndClick(5);
		baseClass.waitForElement(loginPage.getLogoutOption());
		loginPage.getLogoutOption().waitAndClick(10);
		baseClass.stepInfo("Sign out action performed");
	    baseClass.waitForElement(docViewPage.getNavigationMsg());
	    String actualMsg = docViewPage.getNavigationMsg().getText();
	    softAssertion.assertEquals(navigationConfirmationMsg, actualMsg);
	    baseClass.passedStep("Got navigation confirmation message successfully");
	    docViewPage.verifyNavigationPopUpButtons();
	    docViewPage.getNavigationMsgPopupYesBtn().waitAndClick(10);
	    loginPage.loginToSightLine(username, password);
	    sessionSearch.basicContentSearch("null");
	    sessionSearch.ViewInDocView();
	    driver.waitForPageToBeReady();
	    baseClass.waitForElement(codingForm.getObjectsInPreviewBox("Responsive"));
	    String value = codingForm.getObjectsInPreviewBox("Responsive").GetAttribute("checked");
	    softAssertion.assertEquals(value, "true");
	    baseClass.passedStep("No changes are saved after clicking yes button to navigate some other page");
	    softAssertion.assertAll();
	    loginPage.logout();
	}
	/**
	 * @Author : Iyappan.Kasinathan 
	 * @Description :Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form for tag element
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 11)
	public void verifyCfValidationAfterSaveAndCompleteAction() throws InterruptedException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51194");
	    baseClass.stepInfo("Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form for tag element");
	    String codingform = "CFTags"+Utility.dynamicNameAppender();
	    String assgnCoding = "codingAssgn"+Utility.dynamicNameAppender();
	    String defaultAction="Make It Required";
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	codingForm.createCodingFormUsingTwoObjects(codingform, null, null, null, "tag");
	 	codingForm.addcodingFormAddButton();
	 	codingForm.selectDefaultActions(0,defaultAction);
	 	codingForm.enterErrorAndHelpMsg(0,"Yes","Help for testing","Error for testing");
	 	String expectedFirstObjectName = codingForm.getCFObjectsLabel(0);
	 	System.out.println(expectedFirstObjectName);
	 	codingForm.saveCodingForm();
	 	codingForm.assignCodingFormToSG(codingform);
	 	//create assignment
	 	sessionSearch.basicContentSearch("null");
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assgnCoding, codingform);
		assignmentPage.toggleSaveButton();
		assignmentPage.add2ReviewerAndDistribute();
		//Impersonate to reviewer
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignmentPage.viewSelectedAssgnUsingPagination(assgnCoding);
		assignmentPage.assgnViewInAllDocView();
		reusableDocView.saveButton();
		docViewPage.verifyCodingFormName(codingform);
		//verify tags are disbled
		docViewPage.verifyTagsAreDisabled(0);
		//verify tag names
		docViewPage.verifyCodingFormTagNameInDocviewPg(0,expectedFirstObjectName);		
		loginPage.logout();
		//Login as reviewer
		loginPage.loginToSightLine(Input.rev1userName,Input.rev1password);
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		reusableDocView.completeButton();
		docViewPage.verifyCodingFormName(codingform);
		//verify tags are disbled
		docViewPage.verifyTagsAreDisabled(0);
		//verify tag names
		docViewPage.verifyCodingFormTagNameInDocviewPg(0,expectedFirstObjectName);
		loginPage.logout();
		//delete assignment and codinform
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignmentPage.deleteAssgnmntUsingPagination(assgnCoding);
		codingForm.assignCodingFormToSG(Input.codeFormName);
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.deleteCodingForm(codingform,codingform);	
		codingForm.verifyCodingFormIsDeleted(codingform);
		loginPage.logout();
	}
	/**
	 * @Author : Iyappan.Kasinathan 
	 * @Description :Verify RMU after impersonating as Reviewer coding form validations should be displayed on click of 'Complete' button
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 12)
	public void verifyCfValidationAfterCompleteActionUsingImpersonation() throws InterruptedException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51193");
	    baseClass.stepInfo("Verify RMU after impersonating as Reviewer coding form validations should be displayed on click of 'Complete' button");
	    String codingform = "CFTags"+Utility.dynamicNameAppender();
	    String assgnCoding = "codingAssgn"+Utility.dynamicNameAppender();
	    String defaultAction="Make It Required";
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	codingForm.createCodingFormUsingTwoObjects(codingform, null, null, null, "tag");
	 	codingForm.addcodingFormAddButton();
	 	codingForm.selectDefaultActions(0,defaultAction);
	 	codingForm.enterErrorAndHelpMsg(0,"Yes","Help for testing","Error for testing");
	 	String expectedFirstObjectName = codingForm.getCFObjectsLabel(0);
	 	System.out.println(expectedFirstObjectName);
	 	codingForm.saveCodingForm();
	 	//create assignment
	 	sessionSearch.basicContentSearch("null");
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assgnCoding, codingform);
		assignmentPage.add2ReviewerAndDistribute();
		//Impersonate to reviewer
		baseClass.impersonateRMUtoReviewer();
		baseClass.stepInfo("Impersonated to reviewer successfully");
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		reusableDocView.completeButton();
		docViewPage.verifyCodingFormName(codingform);
		//verify tags are disbled
		docViewPage.verifyTagsAreDisabled(0);
		//verify tag names
		docViewPage.verifyCodingFormTagNameInDocviewPg(0,expectedFirstObjectName);
		baseClass.passedStep("The validations of codingform objects after the complete action works as expected");
		loginPage.logout();
		//delete assignment and codinform
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignmentPage.deleteAssgnmntUsingPagination(assgnCoding);
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.deleteCodingForm(codingform,codingform);	
		codingForm.verifyCodingFormIsDeleted(codingform);
		loginPage.logout();
	}
	/**
	 * @Author : Iyappan.Kasinathan 
	 * @Description :To verify that if Project Admin impersonate as RMU Or Reviewer, coding form should be displayed on the Doc View.
	 */
	@Test(enabled = true, dataProvider = "ImpersonationOfPA",groups = { "regression" }, priority = 13)
	public void verifyCfDisplayedAfterImpersonation(String userName, String password, String user) throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-50939");
		baseClass.stepInfo("To verify that if Project Admin impersonate as RMU Or Reviewer, coding form should be displayed on the Doc View.");
		loginPage.loginToSightLine(userName, password);
		if(user=="rmu") {
			baseClass.impersonatePAtoRMU();
		}else {
			baseClass.impersonatePAtoReviewer();
		}
	    sessionSearch.basicContentSearch("null");
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocView_CFName());
		String codingformName = docViewPage.getDocView_CFName().getText();
		softAssertion.assertEquals(Input.codeFormName, codingformName);
		softAssertion.assertAll();
		baseClass.passedStep("Coding form in the docview page displayed to the user sucessfully");
		loginPage.logout();
	}
	/**
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Verify after impersonation document not marked as completed in an assignment, custom coding form is editable on doc view page
	 */
	@Test(enabled = true, dataProvider = "ImpersonationOfUsers", groups = { "regression" }, priority = 14)
	public void verifyEditableCfInDocviewPg(String userName, String password, String user) throws InterruptedException {
	    baseClass.stepInfo("Test case Id: RPMXCON-50969");
	    baseClass.stepInfo("Verify after impersonation document not marked as completed in an assignment, custom coding form is editable on doc view page");
	       
	    // login as RMU
	 	loginPage.loginToSightLine(userName, password);
	 	baseClass.stepInfo("Successfully login as "+user);
	 	// create new coding form
	 	if(user=="SA") {
	 	baseClass.impersonateSAtoRMU();
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	codingForm.createCodingFormUsingTwoObjects(codingform, null, null, null, "tag");
	 	codingForm.addcodingFormAddButton();
	 	codingForm.enterErrorAndHelpMsg(0,"Yes","Help for testing","Error for testing");
	 	String expectedFirstObjectName = codingForm.getCFObjectsLabel(0);
	 	System.out.println(expectedFirstObjectName);
	 	codingForm.saveCodingForm();
	 	codingForm.assignCodingFormToSG(codingform);
	 	//create assignment
	 	sessionSearch.basicContentSearch("null");
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assgnCoding, codingform);
		assignmentPage.add2ReviewerAndDistribute();
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignmentPage.viewSelectedAssgnUsingPagination(assgnCoding);
		assignmentPage.assgnViewInAllDocView();
	 	}
	 	if(user=="PA") {
	 		baseClass.impersonatePAtoRMU();
	 		assignmentPage.selectAssignmentToViewinDocview(assgnCoding);
	 	}
	 	if(user=="RMU") {
	 		baseClass.impersonateRMUtoReviewer();
	 		baseClass.stepInfo("Impersonated to reviewer successfully");
			assignmentPage.SelectAssignmentByReviewer(assgnCoding);
			baseClass.stepInfo("User on the doc view after selecting the assignment");
	 	}
		
		driver.waitForPageToBeReady();
		if(reusableDocView.getUnCompleteButton().isElementAvailable(5)==false) {
			baseClass.passedStep("Document is not completed as expected");		}
		docViewPage.verifyCodingFormName(codingform);
		docViewPage.verifyTagsAreEnabled(0);
		docViewPage.verifyTagsAreEnabled(1);
		if(user=="RMU") {
		loginPage.logout();
		//delete assignment and codinform
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignmentPage.deleteAssgnmntUsingPagination(assgnCoding);
		codingForm.assignCodingFormToSG(Input.codeFormName);
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.deleteCodingForm(codingform,codingform);	
		codingForm.verifyCodingFormIsDeleted(codingform);
		loginPage.logout();
		}
	}
	/**
	 * @Author : Iyappan.Kasinathan 
	 * @Description : To verify custom coding form is editable or not when same document is assigned to two different assignments with different assigned coding form
	 */
	@Test(enabled = true, dataProvider = "rmuAndrev", groups = { "regression" }, priority = 15)
	public void verifyCfEditableOrNotBasedOnDocStatusWithDiffrentCodingForms(String userName, String password, String user) throws InterruptedException {
	    baseClass.stepInfo("Test case Id: RPMXCON-50971");
	    baseClass.stepInfo("To verify custom coding form is editable or not when same document is assigned to two different assignments with different assigned coding form");	    
	    System.out.println(assignment1);
	    System.out.println(assignment2);
	    // login as RMU
	 	loginPage.loginToSightLine(userName, password);
	 	baseClass.stepInfo("Successfully login as "+user);
	 	// create new coding form
	 	if(user=="RMU") {
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	codingForm.createCodingFormUsingTwoObjects(cfName1, null, null, null, "tag");
	 	codingForm.addcodingFormAddButton();
	 	codingForm.enterErrorAndHelpMsg(0,"Yes","Help for testing","Error for testing");
	 	String expectedFirstObjectName = codingForm.getCFObjectsLabel(0);
	 	System.out.println(expectedFirstObjectName);
	 	codingForm.saveCodingForm();
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	codingForm.createCodingFormUsingTwoObjects(cfName2, null, null, null, "tag");
	 	codingForm.addcodingFormAddButton();
	 	codingForm.enterErrorAndHelpMsg(0,"Yes","Help for testing","Error for testing");
	 	codingForm.saveCodingForm();
	 	//create assignment
	 	sessionSearch.basicContentSearch("null");
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignment1, cfName1);
		assignmentPage.add2ReviewerAndDistribute();
		driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignment2, cfName2);
		assignmentPage.add2ReviewerAndDistribute();
		baseClass.impersonateRMUtoReviewer();
 		baseClass.stepInfo("Impersonated to reviewer successfully");
	 	}
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		assignmentPage.completeAllDocsByReviewer(assignment1);	 	
		driver.waitForPageToBeReady();
		miniDocList.configureMiniDocListToShowCompletedDocs();
		driver.waitForPageToBeReady();
		if(reusableDocView.getUnCompleteButton().isElementAvailable(5)==true) {
			baseClass.passedStep("Documents are completed as expected");		}
		docViewPage.verifyCodingFormName(codingform);
		docViewPage.verifyTagsAreDisabled(0);
		docViewPage.verifyTagsAreDisabled(1);
		baseClass.passedStep("Coding form is non editable once all the docs are completed with diffrent assigned coding form");
		baseClass.selectproject();
		assignmentPage.SelectAssignmentByReviewer(assignment2);
		if(reusableDocView.getUnCompleteButton().isElementAvailable(5)==false) {
			baseClass.passedStep("Documents are not completed as expected with diffrent assigned coding form");		}
		docViewPage.verifyCodingFormName(codingform);
		docViewPage.verifyTagsAreEnabled(0);
		docViewPage.verifyTagsAreEnabled(1);
		baseClass.passedStep("Coding form is editable for uncompleted docs");
		if(user=="REV") {
		loginPage.logout();
		//delete assignment and codinform
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignmentPage.deleteAssgnmntUsingPagination(assignment1);
		assignmentPage.deleteAssgnmntUsingPagination(assignment2);
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.deleteCodingForm(cfName1,cfName1);
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.deleteCodingForm(cfName2,cfName2);
		loginPage.logout();
		}	
		
	}
	/**
	 * @Author : Iyappan.Kasinathan 
	 * @Description : To verify custom coding form is editable or not when same document is assigned to two different assignments with same assigned coding form
	 */
	@Test(enabled = true, dataProvider = "rmuAndrev", groups = { "regression" }, priority = 16)
	public void verifyCfEditableOrNotBasedOnDocStatusWithSameCodingForm(String userName, String password, String user) throws InterruptedException {
	    baseClass.stepInfo("Test case Id: RPMXCON-50970");
	    baseClass.stepInfo("To verify custom coding form is editable or not when same document is assigned to two different assignments with same assigned coding form");	    
	    // login as RMU
	 	loginPage.loginToSightLine(userName, password);
	 	baseClass.stepInfo("Successfully login as "+user);
	 	// create new coding form
	 	if(user=="RMU") {
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	codingForm.createCodingFormUsingTwoObjects(cfName1, null, null, null, "tag");
	 	codingForm.addcodingFormAddButton();
	 	codingForm.enterErrorAndHelpMsg(0,"Yes","Help for testing","Error for testing");
	 	String expectedFirstObjectName = codingForm.getCFObjectsLabel(0);
	 	System.out.println(expectedFirstObjectName);
	 	codingForm.saveCodingForm();	 	
	 	//create assignment
	 	sessionSearch.basicContentSearch("null");
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignment1, cfName1);
		assignmentPage.add2ReviewerAndDistribute();
		driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignment2, cfName1);
		assignmentPage.add2ReviewerAndDistribute();
		baseClass.impersonateRMUtoReviewer();
 		baseClass.stepInfo("Impersonated to reviewer successfully");
	 	}
		assignmentPage.SelectAssignmentByReviewer(assignment1);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		assignmentPage.completeAllDocsByReviewer(assignment1);	 	
		driver.waitForPageToBeReady();
		if(reusableDocView.getUnCompleteButton().isElementAvailable(5)==true) {
			baseClass.passedStep("Documents are completed as expected");		}
		docViewPage.verifyCodingFormName(codingform);
		docViewPage.verifyTagsAreDisabled(0);
		docViewPage.verifyTagsAreDisabled(1);
		baseClass.passedStep("Coding form is non editable once all the docs are completed with same assigned coding form");
		baseClass.selectproject();
		assignmentPage.SelectAssignmentByReviewer(assignment2);
		if(reusableDocView.getUnCompleteButton().isElementAvailable(5)==false) {
			baseClass.passedStep("Documents are not completed as expected");		}
		docViewPage.verifyCodingFormName(codingform);
		docViewPage.verifyTagsAreEnabled(0);
		docViewPage.verifyTagsAreEnabled(1);
		baseClass.passedStep("Coding form is editable for uncompleted docs with same assigned coding form");
		if(user=="REV") {
		loginPage.logout();
		//delete assignment and codinform
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignmentPage.deleteAssgnmntUsingPagination(assignment1);
		assignmentPage.deleteAssgnmntUsingPagination(assignment2);
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.deleteCodingForm(cfName1,cfName1);
		loginPage.logout();
		}	
		
	}
	
	@DataProvider(name = "ImpersonationOfUsers")
	public Object[][] ImpersonationOfUsers() {
		Object[][] users = { { Input.sa1userName, Input.sa1password, "SA" }, { Input.pa1userName, Input.pa1password, "PA" }, { Input.rmu1userName, Input.rmu1password, "RMU" } };
		return users;
	}
	
	@DataProvider(name = "ImpersonationOfPA")
	public Object[][] ImpersonationOfPA() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, "rmu" }, { Input.pa1userName, Input.pa1password, "rev" } };
		return users;
	}
	
	@DataProvider(name = "UsersWithoutPA")
	public Object[][] UsersWithoutPA() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password }, { Input.rev1userName, Input.rev1password } };
		return users;
	}
	
	@DataProvider(name = "rmuAndrev")
	public Object[][] rmuAndrev() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "RMU" }, { Input.rev1userName, Input.rev1password, "REV" } };
		return users;
	}
	

	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		baseClass = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
			loginPage.logoutWithoutAssert();
		}
		try {
//			loginPage.logout();
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		//	LoginPage.clearBrowserCache();
		}
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR CODINGFORM EXECUTED******");
		try {
	       // loginPage.clearBrowserCache();
		} catch (Exception e) {
			// no session avilable

		}
	}
}
