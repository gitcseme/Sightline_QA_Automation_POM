package sightline.codingForms;

import pageFactory.DocViewPage;

import java.awt.AWTException;
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
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.ProjectPage;
import pageFactory.ReusableDocViewPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;
import pageFactory.CodingForm;

public class CodingForm_IndiumRegression {
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
	UserManagement userManagementPage;
	
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
		userManagementPage = new UserManagement(driver);
	}
    
	/**
	 * @throws Exception 
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Verify that Coding Form Preview is working Properly when user configured Field logic as Part of Radio Group objects in Coding Form
	 */
	@Test(description = "RPMXCON-54555",enabled = true, groups = { "regression" })
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
	@Test(description = "RPMXCON-54556",enabled = true, groups = { "regression" })
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
	@Test(description = "RPMXCON-54562",enabled = true, groups = { "regression" })
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
	@Test(description = "RPMXCON-54559",enabled = true, groups = { "regression" })
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
	@Test(description = "RPMXCON-54535",enabled = true, groups = { "regression" })
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
	@Test(description = "RPMXCON-54539",enabled = true, groups = { "regression" })
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
	 * @Author : Mohan date:15/11/21 Modified date: NA Modified by: NA
	 * @Description :Verify that User (RMU) Saves customized coding form name only
	 *              without any objects 'RPMXCON-53951'
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-53951",enabled = true, groups = { "regression" })
	public void verifyCodingFormNameWithoutAnyObject() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-53951");
		baseClass.stepInfo("Verify that User (RMU) Saves customized coding form name only  without any objects");

		String CFName = "CF" + Utility.dynamicNameAppender();

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// select Tags
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");

		baseClass.stepInfo("Step 2: Create Coding Form");
		codingForm.createCodingFormWithoutObjects(CFName);

		baseClass.stepInfo("Step 3: Verify that Coding Form gets created on 'Manage Coding Form' screen");

		codingForm.verifyCodingFormNameWithoutObjectCreation(CFName);
		codingForm.deleteCodingForm(CFName, CFName);
		codingForm.verifyCodingFormIsDeleted(CFName);
		baseClass.passedStep(
				"To Verify that User (RMU) Saves customized coding form name only without any objects has been successfully verified");

		loginPage.logout();
	}
	/**
	 * @Author : Mohan date:15/11/21 Modified date: NA Modified by: NA
	 * @Description :Verify that new TAGs gets added on Coding Form Screen
	 *              'RPMXCON-53994'
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-53994",enabled = true, groups = { "regression" })
	public void verifyNewTagsAddedToCodingForm() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-53994");
		baseClass.stepInfo("Verify that  new TAGs gets added on Coding Form Screen");

		String codingfrom = "CF" + Utility.dynamicNameAppender();
		String tag = "tag";

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// select Tags
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");

		baseClass.stepInfo("Step 2: Drag new Tag");
		codingForm.createCodingFormUsingTwoObjects(codingfrom, null, null, null, tag);

		baseClass.waitForElement(codingForm.getCodingForm_AddToFormButton());
		codingForm.getCodingForm_AddToFormButton().waitAndClick(10);

		String cfObjectsLabel1 = codingForm.getCFObjectsLabel(0);
		String cfObjectsLabel2 = codingForm.getCFObjectsLabel(1);
		baseClass.passedStep("New tags are added successfully");

		baseClass.stepInfo("Step 3:Verify that new TAGs gets added on Coding Form Screen");
		baseClass.waitForElement(codingForm.getCF_PreviewButton());
		codingForm.getCF_PreviewButton().waitAndClick(10);
		String cfPreviewObjectsLabel1 = codingForm.getCFPreviewObjectsLabel(0);
		String cfPreviewObjectsLabel2 = codingForm.getCFPreviewObjectsLabel(1);

		softAssertion.assertEquals(cfObjectsLabel1, cfPreviewObjectsLabel1);
		softAssertion.assertEquals(cfObjectsLabel2, cfPreviewObjectsLabel2);
		softAssertion.assertAll();
		baseClass.passedStep("New Tags are added on Coding Form Screen Successfully");

		loginPage.logout();
	}
	/**
	 * @Author : Mohan date:15/11/21 Modified date: NA Modified by: NA
	 * @Description :Verify that Preview displays correctly and properly for Static
	 *              object on coding form screen 'RPMXCON-54057'
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-54057",enabled = true, groups = { "regression" })
	public void verifyPreviewDispalysStaticObjectProperly() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-54057");
		baseClass.stepInfo(
				"Verify that Preview displays correctly and properly for Static object on coding form screen");

		String CFName = "CF" + Utility.dynamicNameAppender();
		String fieldName = "Static";

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// select Tags
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");

		baseClass.stepInfo("Step 2&3: Create Coding Form with Static Object and Save Coding Form");
		codingForm.addCodingFormWithStaticText(CFName, fieldName);

		baseClass.stepInfo("Step 4: Edit Coding Form");
		codingForm.editCodingFormAfterSavingCodingForm(CFName);

		baseClass.stepInfo(
				"Step 5&6: Click on 'Preview' button and Verify that Preview displays correctly and properly for Static object on coding form screen");
		baseClass.waitForElement(codingForm.getCF_PreviewButton());
		codingForm.getCF_PreviewButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		String cfPreviewObjectsLabel1 = codingForm.getCFPreviewObjectsLabel(0);
		softAssertion.assertEquals(fieldName, cfPreviewObjectsLabel1);

		driver.waitForPageToBeReady();
		baseClass.waitForElement(codingForm.getCFPreviewPopUpOkBtn());
		codingForm.getCFPreviewPopUpOkBtn().waitAndClick(10);
		baseClass.waitForElement(codingForm.getManageCodingFormButton());
		codingForm.getManageCodingFormButton().waitAndClick(10);
		codingForm.deleteCodingForm(CFName, CFName);
//		codingForm.verifyCodingFormIsDeleted(CFName);
		softAssertion.assertAll();
		baseClass.passedStep(
				"Preview is displayed correctly and properly for Static object along with all configured options successfully");
		loginPage.logout();

	}
	/**
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Verify that Preview displays correctly and properly for Tag objects along with "Selected"
	 *                condition user on coding form screen
	 */
	@Test(description = "RPMXCON-54062",enabled = true, groups = { "regression" })
	public void validatePreviewDisplayTagObjectsByEditing() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-54062");
	    baseClass.stepInfo("Verify that Preview displays correctly and properly for Tag objects along "+
	                            "with \"Selected\" condition user on coding form screen");
	    String codingform = "CFTag"+Utility.dynamicNameAppender();
	    String defaultAction1 = "Make It Display But Not Selectable";
	    String defaultAction2 = "Make It Hidden";
	    String actionName = "Make this Hidden";
	    //LogIn as rmu user
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	    //Create coding form
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.createCodingFormUsingTwoObjects(codingform,null,null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    codingForm.selectDefaultActions(0,defaultAction1);
	    codingForm.selectDefaultActions(1,defaultAction2);
	 	String expectedSecondObjectName = codingForm.getCFObjectsLabel(1);
	 	codingForm.selectFieldLogicValues(0,expectedSecondObjectName,"Selected",actionName);
	 	codingForm.saveCodingForm();
	 	codingForm.editCodingFormAfterSavingCodingForm(codingform);
	    //verify validation popup
	    codingForm.verifyCFValidation();
	    baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
	    codingForm.getCF_PreviewButton().waitAndClick(10);
	    //verify preview validation
	    docViewPage.verifyTagsAreDisabledInPreviewBox(0);
	    baseClass.ValidateElement_Absence(codingForm.getCFPreviewObjectName(expectedSecondObjectName), "Expected tag name is not present");
	    baseClass.passedStep("Expected tag name is not present");
	    codingForm.validatePreviewSuccessMsg();
	    //delete codingform
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.deleteCodingForm(codingform, codingform);
	    codingForm.verifyCodingFormIsDeleted(codingform);
	    loginPage.logout();
	}
	/**
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Verify that Preview displays correctly and properly for Comments objects along with
	 *                "Not Selected" condition user on coding form screen
	 */
	@Test(description = "RPMXCON-54063",enabled = true, groups = { "regression" })
	public void validatePreviewDisplayCommentObjectsByEditing() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-54063");
	    baseClass.stepInfo("Verify that Preview displays correctly and properly for Comments objects along with "+
	                             "\"Not Selected\" condition user on coding form screen");
	    String codingform = "CFTag"+Utility.dynamicNameAppender();
	    String defaultAction1 = "Make It Optional";
	    String defaultAction2 = "Make It Required";
	    String actionName = "Make this Read Only";
	    //LogIn as rmu user
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	    //Create coding form
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.createCodingFormUsingTwoObjects(codingform,null,null,null,"comment");
	    codingForm.addcodingFormAddButton();
	    codingForm.selectDefaultActions(0,defaultAction1);
	    codingForm.selectDefaultActions(1,defaultAction2);
	 	String expectedFirstObjectName = codingForm.getCFObjectsLabel(0);
	 	String expectedSecondObjectName = codingForm.getCFObjectsLabel(1);
	 	driver.scrollingToBottomofAPage();
	 	codingForm.selectFieldLogicValues(1,expectedFirstObjectName,"Not Selected",actionName);
	 	codingForm.saveCodingForm();
 	 	codingForm.editCodingFormAfterSavingCodingForm(codingform);
	    //verify validation popup
	    codingForm.verifyCFValidation();
	    baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
	    codingForm.getCF_PreviewButton().waitAndClick(10);
	    //verify preview validation
	    codingForm.verifyCommentDisabled(expectedSecondObjectName);	    
	    codingForm.validatePreviewSuccessMsg();
	    baseClass.stepInfo("Without pass any text to both comment tab got successfull msg as expected");
	    baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
	    codingForm.getCF_PreviewButton().waitAndClick(10);
	    baseClass.waitForElement(codingForm.getCFPreviewCommentField(expectedFirstObjectName));
	    codingForm.getCFPreviewCommentField(expectedFirstObjectName).SendKeys(Input.searchString1);
	    codingForm.validatePreviewErrorMsg();
	    baseClass.stepInfo("Pass text to first comment tab alone and  got error msg as expected");
	    baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
	    codingForm.getCF_PreviewButton().waitAndClick(10);
	    baseClass.waitForElement(codingForm.getCFPreviewCommentField(expectedFirstObjectName));
	    codingForm.getCFPreviewCommentField(expectedFirstObjectName).SendKeys(Input.searchString1);
	    baseClass.waitForElement(codingForm.getCFPreviewCommentField(expectedSecondObjectName));
	    codingForm.getCFPreviewCommentField(expectedSecondObjectName).SendKeys(Input.searchString1);
	    codingForm.validatePreviewSuccessMsg();
	    baseClass.stepInfo("By passing text to both comments tab got successfull msg as expected");
	    //delete codingform
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.deleteCodingForm(codingform, codingform);
	    codingForm.verifyCodingFormIsDeleted(codingform);
	    loginPage.logout();
	}
	/**
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Verify that Preview displays correctly and properly for Comment object for new coding form
	 */
	@Test(description = "RPMXCON-54069",enabled = true, groups = { "regression" })
	public void validatePreviewDisplayOfComment() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-54069");
	    baseClass.stepInfo("Verify that Preview displays correctly and properly for Comment object for new coding form");
	    String codingform = "CFcomment"+Utility.dynamicNameAppender();
	    String defaultAction="Make It Display But Not Selectable";
	    //LogIn as rmu user
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	    //Create coding form
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.createCodingFormUsingFirstObject(codingform, "comment");
	    codingForm.addcodingFormAddButton();
	    codingForm.selectDefaultActions(0,defaultAction);
	    String commentName = codingForm.getCFObjectsLabel(0);
	    codingForm.saveCodingForm();
	    //verify validation popup
	    codingForm.verifyCFValidation();
	    baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
	    codingForm.getCF_PreviewButton().waitAndClick(10);
	    //verify preview validation
	    codingForm.verifyCommentDisabled(commentName);
	    codingForm.verifyCFObjectsLabel(0, commentName);
	    codingForm.validatePreviewSuccessMsg();
	    //delete codingform
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.deleteCodingForm(codingform, codingform);
	    codingForm.verifyCodingFormIsDeleted(codingform);
	    loginPage.logout();
	}
	/**
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Verify that Preview displays correctly and properly for Editable Metadata object for new coding form
	 */
	@Test(description = "RPMXCON-54070",enabled = true, groups = { "regression" })
	public void validatePreviewDisplayOfMetadata() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-54070");
	    baseClass.stepInfo("Verify that Preview displays correctly and properly for Editable Metadata object for new coding form");
	    String codingform = "CFmetadata"+Utility.dynamicNameAppender();
	    String defaultAction="Make It Optional";
	    //LogIn as rmu user
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	    //Create coding form
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.createCodingFormUsingFirstObject(codingform, "metadata");
	    codingForm.addcodingFormAddButton();
	    codingForm.selectDefaultActions(0,defaultAction);
	    String metaDataName = codingForm.getCFObjectsLabel(0);
	    codingForm.saveCodingForm();
	    //verify validation popup
	    codingForm.verifyCFValidation();
	    baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
	    codingForm.getCF_PreviewButton().waitAndClick(10);
	    //verify preview validation
	    codingForm.verifyCFObjectsLabel(0, metaDataName);
	    baseClass.stepInfo("Verify the field without enter any text");
	    codingForm.validatePreviewSuccessMsg();
	    baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
	    codingForm.getCF_PreviewButton().waitAndClick(10);
	    baseClass.waitTillElemetToBeClickable( codingForm.getCFmetadataTextBox(metaDataName));
	    codingForm.getCFmetadataTextBox(metaDataName).SendKeys(Input.searchString1);
	    baseClass.stepInfo("Verify the field by passing the text");
	    codingForm.validatePreviewSuccessMsg();
	    //delete codingform
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.deleteCodingForm(codingform, codingform);
	    codingForm.verifyCodingFormIsDeleted(codingform);
	    loginPage.logout();
	}
	/**
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Verify that Preview displays correctly and properly for 
	 *                Tag/Comments/Metadata objects for New coding form
	 */
	@Test(description = "RPMXCON-54071",enabled = true, groups = { "regression" })
	public void validatePreviewDisplayOfMultipleObjects() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-54071");
	    baseClass.stepInfo("Verify that Preview displays correctly and properly for Tag/Comments/Metadata objects for New coding form");
	    String codingform = "CFmetadata"+Utility.dynamicNameAppender();
	    //Login as a rmu user
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    //create codingform
	    codingForm.createCodingFormUsingFirstObject(codingform,"tag");
	    baseClass.waitForElement(codingForm.getCodingForm_CommentTab());
	    codingForm.getCodingForm_CommentTab().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingForm_FirstComment());
		codingForm.getCodingForm_FirstComment().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingForm_EDITABLE_METADATA_Tab());
		codingForm.getCodingForm_EDITABLE_METADATA_Tab().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingForm_FirstMetadata());
		codingForm.getCodingForm_FirstMetadata().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingForm_SecondMetadata());
		codingForm.getCodingForm_SecondMetadata().waitAndClick(10);
		codingForm.addcodingFormAddButton();
	 	codingForm.enterErrorAndHelpMsg(0,"Yes","Help for testing","Error for testing");
	 	String tagName = codingForm.getCFObjectsLabel(0);
	 	String commentName = codingForm.getCFObjectsLabel(1);
	 	String metadataName1 = codingForm.getCFObjectsLabel(2);
	 	String metadatName2 = codingForm.getCFObjectsLabel(3);
	 	codingForm.saveCodingForm();
	 	//verify validation popup
	    codingForm.verifyCFValidation();
	    baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
	    codingForm.getCF_PreviewButton().waitAndClick(10);
	    //verify preview validation
	    codingForm.verifyCFObjectsLabel(0,tagName);
	    codingForm.verifyCFObjectsLabel(1,commentName);
	    codingForm.verifyCFObjectsLabel(2,metadataName1);
	    codingForm.verifyCFObjectsLabel(3,metadatName2);
	    codingForm.validatePreviewSuccessMsg();
	    //delete codingform
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.deleteCodingForm(codingform, codingform);
	    codingForm.verifyCodingFormIsDeleted(codingform);
	    loginPage.logout();
	}
	/**
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Verify that Preview displays correctly and properly for Static object for New coding form
	 */
	@Test(description = "RPMXCON-54072",enabled = true, groups = { "regression" })
	public void validatePreviewDisplayOfStaticText() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-54072");
	    baseClass.stepInfo("Verify that Preview displays correctly and properly for Static object for New coding form");
	    String codingform = "CFStatic"+Utility.dynamicNameAppender();
	    //LogIn as rmu user
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	    //Create coding form
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.addCodingFormWithStaticText(codingform,Input.searchString1);
	    //verify validation popup
	    codingForm.verifyCFValidation();
	    baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
	    codingForm.getCF_PreviewButton().waitAndClick(10);
	    //verify preview validation
	    codingForm.verifyCFObjectsLabel(0, Input.searchString1);
	    codingForm.validatePreviewSuccessMsg();
	    //delete codingform
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.deleteCodingForm(codingform, codingform);
	    codingForm.verifyCodingFormIsDeleted(codingform);
	    loginPage.logout();
	}
	/**
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Verify that Preview displays correctly and properly for Tag objects along with "Selected"
	 *               condition user for New coding form
	 */
	@Test(description = "RPMXCON-54077",enabled = true, groups = { "regression" })
	public void validatePreviewDisplayTagObjects() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-54077");
	    baseClass.stepInfo("Verify that Preview displays correctly and properly for Tag objects along with"+
	                           " \"Selected\" condition user for New coding form");
	    String codingform = "CFTag"+Utility.dynamicNameAppender();
	    String defaultAction1 = "Make It Display But Not Selectable";
	    String defaultAction2 = "Make It Hidden";
	    String actionName = "Make this Hidden";
	    //LogIn as rmu user
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	    //Create coding form
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.createCodingFormUsingTwoObjects(codingform,null,null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    codingForm.selectDefaultActions(0,defaultAction1);
	    codingForm.selectDefaultActions(1,defaultAction2);
	 	String expectedSecondObjectName = codingForm.getCFObjectsLabel(1);
	 	codingForm.selectFieldLogicValues(0,expectedSecondObjectName,"Selected",actionName);
	 	codingForm.saveCodingForm();
	    //verify validation popup
	    codingForm.verifyCFValidation();
	    baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
	    codingForm.getCF_PreviewButton().waitAndClick(10);
	    //verify preview validation
	    docViewPage.verifyTagsAreDisabledInPreviewBox(0);
	    baseClass.ValidateElement_Absence(codingForm.getCFPreviewObjectName(expectedSecondObjectName), "Expected tag name is not present");
	    baseClass.passedStep("Expected tag name is not present");
	    codingForm.validatePreviewSuccessMsg();
	    //delete codingform
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.deleteCodingForm(codingform, codingform);
	    codingForm.verifyCodingFormIsDeleted(codingform);
	    loginPage.logout();
	}	
	/**
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Verify that Preview displays correctly and properly for Comments objects 
	 *                along with "Not Selected" condition user for New coding form
	 */
	@Test(description = "RPMXCON-54078",enabled = true, groups = { "regression" })
	public void validatePreviewDisplayCommentObjects() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-54078");
	    baseClass.stepInfo("Verify that Preview displays correctly and properly for Comments objects along with"+
	                               " \"Not Selected\" condition user for New coding form");
	    String codingform = "CFTag"+Utility.dynamicNameAppender();
	    String defaultAction1 = "Make It Optional";
	    String defaultAction2 = "Make It Required";
	    String actionName = "Make this Read Only";
	    //LogIn as rmu user
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	    //Create coding form
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.createCodingFormUsingTwoObjects(codingform,null,null,null,"comment");
	    codingForm.addcodingFormAddButton();
	    codingForm.selectDefaultActions(0,defaultAction1);
	    codingForm.selectDefaultActions(1,defaultAction2);
	 	String expectedFirstObjectName = codingForm.getCFObjectsLabel(0);
	 	String expectedSecondObjectName = codingForm.getCFObjectsLabel(1);
	 	driver.scrollingToBottomofAPage();
	 	codingForm.selectFieldLogicValues(1,expectedFirstObjectName,"Not Selected",actionName);
	 	codingForm.saveCodingForm();
	    //verify validation popup
	    codingForm.verifyCFValidation();
	    baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
	    codingForm.getCF_PreviewButton().waitAndClick(10);
	    //verify preview validation
	    codingForm.verifyCommentDisabled(expectedSecondObjectName);	    
	    codingForm.validatePreviewSuccessMsg();
	    baseClass.stepInfo("Without pass any text to both comment tab got successfull msg as expected");
	    baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
	    codingForm.getCF_PreviewButton().waitAndClick(10);
	    baseClass.waitForElement(codingForm.getCFPreviewCommentField(expectedFirstObjectName));
	    codingForm.getCFPreviewCommentField(expectedFirstObjectName).SendKeys(Input.searchString1);
	    codingForm.validatePreviewErrorMsg();
	    baseClass.stepInfo("Pass text to first comment tab alone and  got error msg as expected");
	    baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
	    codingForm.getCF_PreviewButton().waitAndClick(10);
	    baseClass.waitForElement(codingForm.getCFPreviewCommentField(expectedFirstObjectName));
	    codingForm.getCFPreviewCommentField(expectedFirstObjectName).SendKeys(Input.searchString1);
	    baseClass.waitForElement(codingForm.getCFPreviewCommentField(expectedSecondObjectName));
	    codingForm.getCFPreviewCommentField(expectedSecondObjectName).SendKeys(Input.searchString1);
	    codingForm.validatePreviewSuccessMsg();
	    baseClass.stepInfo("By passing text to both comments tab got successfull msg as expected");
	    //delete codingform
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.deleteCodingForm(codingform, codingform);
	    codingForm.verifyCodingFormIsDeleted(codingform);
	    loginPage.logout();
	}
	/**
	 * @Author : Mohan date:16/11/21 Modified date: NA Modified by: NA
	 * @Description :Verify that Comments gets added into Coding Form Screen
	 *              successfully 'RPMXCON-54134'
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-54134",enabled = true, groups = { "regression" })
	public void verifyCommentsAddedToCodingForm() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-54134");
		baseClass.stepInfo("Verify that Comments gets added into Coding Form Screen successfully");

		String codingfrom = "CF" + Utility.dynamicNameAppender();
		String comment = "comment";

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// select Comments
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");

		baseClass.stepInfo("Step 2: Drag new Comment");
		codingForm.createCodingFormUsingTwoObjects(codingfrom, null, null, null, comment);

		baseClass.waitForElement(codingForm.getCodingForm_AddToFormButton());
		codingForm.getCodingForm_AddToFormButton().waitAndClick(10);

		String cfObjectsLabel1 = codingForm.getCFObjectsLabel(0);
		String cfObjectsLabel2 = codingForm.getCFObjectsLabel(1);
		baseClass.passedStep("New Comment are added successfully");

		baseClass.stepInfo("Step 3:Observe that Comments gets added into Coding Form Screen successfully");
		baseClass.waitForElement(codingForm.getCF_PreviewButton());
		codingForm.getCF_PreviewButton().waitAndClick(10);
		String cfPreviewObjectsLabel1 = codingForm.getCFPreviewObjectsLabel(0);
		String cfPreviewObjectsLabel2 = codingForm.getCFPreviewObjectsLabel(1);

		softAssertion.assertEquals(cfObjectsLabel1, cfPreviewObjectsLabel1);
		softAssertion.assertEquals(cfObjectsLabel2, cfPreviewObjectsLabel2);
		baseClass.passedStep("Comments are added into Coding Form Screen Successfully");

		loginPage.logout();
	}
	/**
	 * @Author : Mohan date:17/11/21 Modified date: NA Modified by: NA
	 * @Description :Verify that Remove functionality is working correctly and
	 *              properly for TAGS in ascending order on coding form screen
	 *              'RPMXCON-54228'
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-54228",enabled = true, groups = { "regression" })
	public void verifyRemoveFuctionalityForTagsCodingForm() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-54228");
		baseClass.stepInfo(
				"Verify that Remove functionality is working correctly and properly for TAGS in ascending order on coding form screen");

		String codingfrom = "CF" + Utility.dynamicNameAppender();
		String tag = "tag";

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// select Tags
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");

		baseClass.stepInfo("Step 2 & 3: Click on TAGS TAB and Select 3 TAGS from available Tag group");
		codingForm.createCodingFormUsingTwoObjects(codingfrom, null, null, null, tag);

		baseClass.waitForElement(codingForm.getCodingForm_SelectTagCheckBox(3));
		codingForm.getCodingForm_SelectTagCheckBox(3).waitAndClick(10);

		baseClass.stepInfo("Step 4:Verify that new TAGs gets added on Coding Form Screen");
		baseClass.waitForElement(codingForm.getCodingForm_AddToFormButton());
		codingForm.getCodingForm_AddToFormButton().waitAndClick(10);

		String cfObjectsLabel1 = codingForm.getCFObjectsLabel(0);
		String cfObjectsLabel2 = codingForm.getCFObjectsLabel(1);
		String cfObjectsLabel3 = codingForm.getCFObjectsLabel(2);
		baseClass.passedStep("New tags are added successfully");

		baseClass.stepInfo(
				"Step 5 & 6:Click on 'Remove' link one by one in ascending order like - Tag 1 to Tag 3 and Click on 'Yes' button");
		for (int i = 0; i <= 2; i++) {
			driver.waitForPageToBeReady();

			codingForm.selectRemoveLinkFromCodingForm(i);

		}
		baseClass.passedStep("Tags are removed successfully");

		baseClass.stepInfo(
				"Step 7:Verify that Remove functionality is working correctly and properly  on coding form screen  ");
		for (int i = 1; i <= 3; i++) {
			driver.waitForPageToBeReady();
			codingForm.verifyRemoveLinkCodingForm(i);
		}
		baseClass.passedStep(
				"Remove functionality is working correctly and properly on coding form screen successfully");

		loginPage.logout();
	}
	/**
	 * @Author : Mohan date:23/11/21 Modified date: NA Modified by: NA 
	 * @Description :Verify that While creating a Coding Form, Associated 
	 * RadioGroup Tag(s)- values does not disappear in coding form. 'RPMXCON-54545'
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-54545",enabled = true, groups = { "regression" })
	public void verifyCodingFormWithRadioGroupTagAndCheckGroup() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-54545");
		baseClass.stepInfo("Verify that While creating a Coding Form, Associated RadioGroup Tag(s)- values does not disappear in coding form.");
		
		
		String codingfrom = "CF"+Utility.dynamicNameAppender();
		String radio="Responsive Group";
		String check="Tech Issue Group";
		String tag = "tag";
		String tag1Lable = "Responsive";
		String tag2Lable = "Technical_Issue";
		String tag3Lable = "Processing_Issue";


		// Login As Reviewer Manager
		baseClass.stepInfo("Step 2: Login to Application with RMU");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		//select Tags
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		
		baseClass.stepInfo("Step 3&4: Navigate Manage >> Coding Form and Create a Coding Form as per attachment.");
		driver.waitForPageToBeReady();
		codingForm.createCodingFormUsingTwoObjects(codingfrom,null,null,null, tag);
		codingForm.addcodingFormAddButton();
		codingForm.enterObjectName(0, tag1Lable);
		String cfObjectsLabel = codingForm.getCFObjectsLabel(0);
		codingForm.enterObjectName(1, tag2Lable);
		String cfObjectsLabel1 = codingForm.getCFObjectsLabel(1);
		driver.scrollPageToTop();
		baseClass.waitForElement(codingForm.getCodingForm_SelectTagCheckBox(3));
		codingForm.getCodingForm_SelectTagCheckBox(3).waitAndClick(10);
		codingForm.addcodingFormAddButton();
		codingForm.enterObjectName(2, tag3Lable);
		String cfObjectsLabel2 = codingForm.getCFObjectsLabel(2);
		driver.scrollPageToTop();
		codingForm.getCF_RadioGrpObject().waitAndClick(10);
		codingForm.addcodingFormAddButton();
		codingForm.enterObjectName(3, radio);
		driver.scrollPageToTop();
		codingForm.getCF_CheckGrpObject().waitAndClick(10);
	    codingForm.addcodingFormAddButton();
		codingForm.enterObjectName(4, check);
		codingForm.selectTagTypeByIndex("radio item",1,0);
		codingForm.selectTagTypeByIndex("radio item",1,1);
		codingForm.selectTagTypeByIndex("check item",1,2);
		codingForm.saveCodingForm();
		
		baseClass.stepInfo("Step 5: Verify that While creating a Coding Form, Associated RadioGroup Tag(s)- values does not disappear in coding form.");
		codingForm.editCodingFormAfterSavingCodingForm(codingfrom);
		baseClass.waitForElement(codingForm.getCF_PreviewButton());
		codingForm.getCF_PreviewButton().waitAndClick(10);
		String cfPreviewObjectsLabel = codingForm.getCFPreviewRadioText(cfObjectsLabel).getText();
		String cfPreviewObjectsLabel1 = codingForm.getCFPreviewRadioText(cfObjectsLabel1).getText();
		String cfPreviewObjectsLabel2 = codingForm.getCFPreviewCheckText(cfObjectsLabel2).getText();
		
		softAssertion.assertEquals(cfObjectsLabel,cfPreviewObjectsLabel);
		softAssertion.assertEquals(cfObjectsLabel1,cfPreviewObjectsLabel1);
		softAssertion.assertEquals(cfObjectsLabel2,cfPreviewObjectsLabel2);
		baseClass.passedStep("While creating a Coding Form, Associated RadioGroup Tag(s)- values are not disappear in coding form successfully");
		
		driver.waitForPageToBeReady();
		baseClass.waitForElement(codingForm.getCFPreviewPopUpOkBtn());
		codingForm.getCFPreviewPopUpOkBtn().waitAndClick(10);
		baseClass.waitForElement(codingForm.getManageCodingFormButton());
		codingForm.getManageCodingFormButton().waitAndClick(10);
		codingForm.deleteCodingForm(codingfrom, codingfrom);
		codingForm.verifyCodingFormIsDeleted(codingfrom);
		loginPage.logout();
	}
	/**
	 * @throws Exception 
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Verify that Coding Form Preview is working Properly when user configured Field logic as
	 *                 Part of Radio Group objects in Coding Form
	 */
	@Test(description = "RPMXCON-54568",enabled = true, groups = { "regression" })
	public void validatePreviewDisplayUsingRadioGroupObjects() throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-54568");
	    baseClass.stepInfo("Verify that Coding Form Preview is working Properly when user configured Field logic as"+
	                        " Part of Radio Group objects in Coding Form");
	    String defaultAction = "Make It Hidden";
	    String actionName = "Make this Required";
	    String codingform = "CFTag"+Utility.dynamicNameAppender();	   
		//LogIn as rmu
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	    //Create coding form as per attachment
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    driver.waitForPageToBeReady();
	    baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
		codingForm.getAddNewCodingFormBtn().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
	    codingForm.CreateCodingFormWithParameter(codingform,"Responsive",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Technical_Issue",null,null,"tag");	  
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Processing_Issue",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Foreign_Language",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Privileged",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    baseClass.stepInfo("All tags are added to coding form");
	    //Add special objects
	    codingForm.getCF_RadioGrpObject().waitAndClick(10);
	    codingForm.addcodingFormAddButton();
	    codingForm.selectTagTypeByIndex("radio item",1,0);
	    codingForm.selectTagTypeByIndex("radio item",1,1);
	    codingForm.getCF_RadioGrpObject().waitAndClick(10);
	    driver.scrollPageToTop();
	    codingForm.addcodingFormAddButton();
	    codingForm.selectTagTypeByIndex("radio item",2,2);
	    codingForm.selectTagTypeByIndex("radio item",2,3);
	    driver.scrollPageToTop();
	    codingForm.getCF_CheckGrpObject().waitAndClick(10);
	    codingForm.addcodingFormAddButton();
	    codingForm.selectTagTypeByIndex("check item",1,4);
	    codingForm.enterObjectName(5, "Responsive Group");
	    codingForm.enterObjectName(6, "Tech Issue Group");
	    codingForm.enterObjectName(7, "Check Group");
	    codingForm.selectDefaultActions(6, defaultAction);
	    String expectedFirstObjectName = codingForm.getCFObjectsLabel(0);
	    String expectedSecondObjectName = codingForm.getCFObjectsLabel(1);
	    String expecteFourthObjectName = codingForm.getCFObjectsLabel(4);
	    codingForm.selectFieldLogicValues(6,expectedFirstObjectName,"Selected",actionName);
	    codingForm.selectOperatorAndFieldLogics(6,2,expectedSecondObjectName,"Selected","OR");
	    codingForm.selectOperatorAndFieldLogics(6,3,expecteFourthObjectName,"Selected","OR");
	    codingForm.saveCodingForm();
	    baseClass.stepInfo("Two check group and one radio group are added to coding form");
	    //validations
	    try {
	    baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
	    codingForm.getCF_PreviewButton().waitAndClick(10);
	    baseClass.ValidateElement_Presence(codingForm.getTagInPreviewBoxHidden(6), "Hidden tag");
	    baseClass.passedStep("The tags are hidden as expected");
	    codingForm.validatePreviewSuccessMsg();
	    baseClass.stepInfo("Apply field logic rules in tech issue radio group");
		baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
		codingForm.getCF_PreviewButton().waitAndClick(10);
	    driver.waitForPageToBeReady();
	    codingForm.selectTagInPreviewBox(7).waitAndClick(10);
		codingForm.validatePreviewErrorMsg();
		baseClass.passedStep("Got error messsage successfully as expected");
	    }catch(Exception e) {
	    	e.printStackTrace();
	    	baseClass.failedStep("Failed to validate preview display");
	    }
		//delete codingform
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.deleteCodingForm(codingform, codingform);
	    codingForm.verifyCodingFormIsDeleted(codingform);
	    loginPage.logout();
	}
	/**
	 * @throws Exception 
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Verify that Coding Form Preview is working Properly when user configured Field logic
	 *                as Part of Check Group objects in Coding Form
	 */
	@Test(description = "RPMXCON-54569",enabled = true, groups = { "regression" })
	public void validatePreviewDisplayUsingCheckGroupObjects() throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-54569");
	    baseClass.stepInfo("Verify that Coding Form Preview is working Properly when user configured Field logic as"+
	                                   " Part of Check Group objects in Coding Form");
	    String defaultAction = "Make It Hidden";
	    String actionName = "Make this Required";
	    String codingform = "CFTag"+Utility.dynamicNameAppender();	   
		//LogIn as rmu
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	    //Create coding form as per attachment
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
	    codingForm.CreateCodingFormWithParameter(codingform,"Privileged",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    baseClass.stepInfo("All tags are added to coding form");
	    //Add special objects
	    codingForm.getCF_CheckGrpObject().waitAndClick(10);
	    codingForm.addcodingFormAddButton();
	    codingForm.selectTagTypeByIndex("check item",1,0);
	    codingForm.selectTagTypeByIndex("check item",1,1);
	    codingForm.getCF_CheckGrpObject().waitAndClick(10);
	    driver.scrollPageToTop();
	    codingForm.addcodingFormAddButton();
	    codingForm.selectTagTypeByIndex("check item",2,2);
	    codingForm.selectTagTypeByIndex("check item",2,3);
	    driver.scrollPageToTop();
	    codingForm.getCF_RadioGrpObject().waitAndClick(10);
	    codingForm.addcodingFormAddButton();
	    codingForm.selectTagTypeByIndex("radio item",1,4);
	    codingForm.enterObjectName(5, "Responsive Group");
	    codingForm.enterObjectName(6, "Tech Issue Group");
	    codingForm.enterObjectName(7, "Radio Group");
	    codingForm.selectDefaultActions(6, defaultAction);
	    baseClass.stepInfo("Two check group and one radio group are added to coding form");
	    String expectedFirstObjectName = codingForm.getCFObjectsLabel(0);
	    String expectedSecondObjectName = codingForm.getCFObjectsLabel(1);
	    String expecteFourthObjectName = codingForm.getCFObjectsLabel(4);
	    codingForm.selectFieldLogicValues(6,expectedFirstObjectName,"Selected",actionName);
	    codingForm.selectOperatorAndFieldLogics(6,2,expectedSecondObjectName,"Selected","OR");
	    codingForm.selectOperatorAndFieldLogics(6,3,expecteFourthObjectName,"Selected","OR");
	    codingForm.saveCodingForm();
	    //validations
	    try {
	    baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
	    codingForm.getCF_PreviewButton().waitAndClick(10);
	    baseClass.ValidateElement_Presence(codingForm.getTagInPreviewBoxHidden(6), "Hidden tag");
	    baseClass.passedStep("The tags are hidden as expected");
	    codingForm.validatePreviewSuccessMsg();
	    baseClass.stepInfo("Apply field logic rules in tech issue check group");
		baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
		codingForm.getCF_PreviewButton().waitAndClick(10);
	    driver.waitForPageToBeReady();
	    codingForm.selectTagInPreviewBox(7).waitAndClick(10);
		codingForm.validatePreviewErrorMsg();
		baseClass.passedStep("Got error messsage successfully as expected");
	    }catch(Exception e) {
	    	e.printStackTrace();
	    	baseClass.failedStep("Failed to validate preview display");
	    }
		//delete codingform
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    codingForm.deleteCodingForm(codingform, codingform);
	    codingForm.verifyCodingFormIsDeleted(codingform);
	    loginPage.logout();
	}
	
	
	/**
	 * @throws Exception 
	 * @author Malayala.Seenivasan 
	 * @Description : Verify that Coding Form Preview is working Properly 
	 *                when user configured Field logic as Part of Check Group objects in Coding Form
	 */
	@Test(description = "RPMXCON-54554",enabled = true, groups = { "regression" })
	public void validatePreviewDisplayUsingCheckGroup() throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-54554");
	    baseClass.stepInfo("Verify that Coding Form Preview is working Properly when user configured Field"
	    		+ " logic as Part of Check Group objects in Coding Form");
	    String defaultAction = "Make this Hidden";
	    String actionName = "Make this Required";
	    String helpTextprocess="Is there some reason that review cannot be determined?";
	    String processhelp="Does this doc contain some type of issue that prohibits the ability for the record to be reviewed?";
	    String language="Does this doc contain some language besides what you can review?";
	    String errorMsg="If the document has a technical issue and cannot be reviewed, you must select a reason why from this list above";
	    String codingform = "CFTag"+Utility.dynamicNameAppender();	   
		//LogIn as rmu
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	    //Create coding form as per attachment
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    driver.waitForPageToBeReady();
	    baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
		codingForm.getAddNewCodingFormBtn().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
		
		// adding check group
		codingForm.specialObjectsBox("check");
		codingForm.addcodingFormAddButton();
		codingForm.getCodingForm_StaticText(0).SendKeys("Responsive Group");
		codingForm.getCodingForm_HelpMsg(0).SendKeys("Responsiveness");
		codingForm.selectDefaultActions(0, Input.optional);
		
		//Add tags
	    codingForm.CreateCodingFormWithParameter(codingform,"Responsive",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Technical_Issue",null,null,"tag");	  
	    codingForm.addcodingFormAddButton();
	    codingForm.selectTagTypeByIndex("check item",1,1);
	    codingForm.selectTagTypeByIndex("check item",1,2);
	    codingForm.getCodingForm_HelpMsg(2).SendKeys(helpTextprocess);
	    String expectedThirdObjectName = codingForm.getCFObjectsLabel(2);
	    
	    // adding radio group
	    driver.scrollPageToTop();
	    baseClass.waitTime(3);
	    codingForm.specialObjectsBox("radio");
		codingForm.addcodingFormAddButton();
		codingForm.getCodingForm_StaticText(3).SendKeys("Tech Issue Group");
		codingForm.selectDefaultActions(3, Input.hidden);
		codingForm.selectFieldLogicValues(3,expectedThirdObjectName,"Selected",actionName);
	    codingForm.getCodingForm_ErrorMsg(3).SendKeys(errorMsg);
	    
	   // add tags to radio group
	    codingForm.CreateCodingFormWithParameter(codingform,"Processing_Issue",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Foreign_Language",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    baseClass.stepInfo("All tags are added to coding form");
	    
	    codingForm.selectTagTypeByIndex("radio item",1,4);
	    codingForm.getCodingForm_HelpMsg(4).SendKeys(processhelp);
	    codingForm.selectTagTypeByIndex("radio item",1,5);
	    codingForm.getCodingForm_HelpMsg(5).SendKeys(language);
	    driver.scrollPageToTop();
	    codingForm.codingFormSaveButton();
	    //validations
	    baseClass.waitTillElemetToBeClickable(codingForm.getCF_PreviewButton());
	    codingForm.getCF_PreviewButton().waitAndClick(10);
	    codingForm.selectObjectsInPreviewBox("Technical_Issue").waitAndClick(5);
	   boolean falgTrueOne= codingForm.getCF_PreviewTagNameHidden("Processing_Issue").isDisplayed();
	   boolean falgTrueTwo= codingForm.getCF_PreviewTagNameHidden("Foreign_Language").isDisplayed();
	   if (falgTrueOne==falgTrueTwo==true) {
		   baseClass.passedStep("While clicking the second checkbox two tag are appeared");
	}
	   else {
		baseClass.failedStep("tag not appearing when clicked second checkbox");
	}
	   codingForm.selectObjectsInPreviewBox("Technical_Issue").waitAndClick(5);
	   codingForm.selectObjectsInPreviewBox("Responsive").waitAndClick(5);
	   boolean falgFalseOne= codingForm.getCF_PreviewTagNameHidden("Processing_Issue").isDisplayed();
	   boolean falgFalseTwo= codingForm.getCF_PreviewTagNameHidden("Foreign_Language").isDisplayed();
	   softAssertion.assertEquals(falgFalseTwo,false);
	   softAssertion.assertEquals(falgFalseOne,false);
	    baseClass.passedStep("While clicking the first checkbox two tag are not appeared");
	   softAssertion.assertAll();
	   
	   // logout
	   loginPage.logout();
	}
	
	/**
	 * @throws Exception 
	 * @author Malayala.Seenivasan 
	 * @Description : Verify that While creating a Coding Form, 
	 *                Associated CheckGroups Tag(s)- values does not disappear in coding form.
	 */
	@Test(description = "RPMXCON-54546",enabled = true, groups = { "regression" })
	public void validateWhileConfigure() throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-54546");
	    baseClass.stepInfo("Verify that While creating a Coding Form, Associated CheckGroups Tag(s)-"
	    		+ " values does not disappear in coding form.");
	    String codingform = "CFTag"+Utility.dynamicNameAppender();	   
		//LogIn as rmu
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	    //Create coding form as per attachment
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    driver.waitForPageToBeReady();
	    baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
		codingForm.getAddNewCodingFormBtn().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
		
		// adding check group
		codingForm.specialObjectsBox("check");
		codingForm.addcodingFormAddButton();
		codingForm.getCodingForm_StaticText(0).SendKeys("Responsive Group");
		codingForm.getCodingForm_HelpMsg(0).SendKeys("Responsiveness");
		codingForm.selectDefaultActions(0, Input.optional);
		
		//Add tags
	    codingForm.CreateCodingFormWithParameter(codingform,"Responsive",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    codingForm.CreateCodingFormWithParameter(codingform,"Technical_Issue",null,null,"tag");	  
	    codingForm.addcodingFormAddButton();
	    codingForm.selectTagTypeByIndex("check item",1,1);
	    codingForm.selectTagTypeByIndex("check item",1,2);
	    
	    // adding radio group
	    driver.scrollPageToTop();
	    baseClass.waitTime(3);
	    codingForm.specialObjectsBox("radio");
		codingForm.addcodingFormAddButton();
		codingForm.getCodingForm_StaticText(3).SendKeys("Tech Issue Group");
	    
	   // add tags to radio group
	    codingForm.CreateCodingFormWithParameter(codingform,"Processing_Issue",null,null,"tag");
	    codingForm.addcodingFormAddButton();
	    driver.scrollPageToTop();
	    codingForm.codingFormSaveButton();
	    //validations
	   boolean falgTrueOne= codingForm.getCF_PreviewTagNameHidden("Responsive Group").isDisplayed();
	   softAssertion.assertEquals(falgTrueOne,true);
	   boolean falgTrueTwo= codingForm.getCF_PreviewTagNameHidden("Responsive").isDisplayed();
	   softAssertion.assertEquals(falgTrueTwo,true);
	   boolean falgTrueThird= codingForm.getCF_PreviewTagNameHidden("Technical_Issue").isDisplayed();
	   softAssertion.assertEquals(falgTrueThird,true);
		baseClass.passedStep("All previous configured tag displayed as expected");
		softAssertion.assertAll();
		
		// logout
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
		try {;
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
