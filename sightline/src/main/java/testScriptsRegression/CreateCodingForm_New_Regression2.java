package testScriptsRegression;

import static org.testng.Assert.assertFalse;
import pageFactory.DocViewPage;
import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;

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
import pageFactory.UserManagement;
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
	    driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearch.getBulkActionButton().Visible();
			}
		}), Input.wait30);
		Thread.sleep(2000); // App synch
		sessionSearch.getBulkActionButton().waitAndClick(5);
		Thread.sleep(2000); // App Synch
		docViewPage.getDocViewAction().waitAndClick(10);
		baseClass.waitTime(3); // added for stabilization
		System.out.println("Navigated to docView to view docs");
		UtilityLog.info("Navigated to docView to view docs");
	    driver.waitForPageToBeReady();
	    baseClass.waitTime(3);
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
	    driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearch.getBulkActionButton().Visible();
			}
		}), Input.wait30);
		Thread.sleep(2000); // App synch
		sessionSearch.getBulkActionButton().waitAndClick(5);
		Thread.sleep(2000); // App Synch

		docViewPage.getDocViewAction().waitAndClick(10);
		baseClass.waitTime(3); // added for stabilization

		System.out.println("Navigated to docView to view docs");
		UtilityLog.info("Navigated to docView to view docs");
	    driver.waitForPageToBeReady();
	    baseClass.waitTime(3);
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
	    driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearch.getBulkActionButton().Visible();
			}
		}), Input.wait30);
		Thread.sleep(2000); // App synch
		sessionSearch.getBulkActionButton().waitAndClick(5);
		Thread.sleep(2000); // App Synch
		docViewPage.getDocViewAction().waitAndClick(10);
		baseClass.waitTime(3); // added for stabilization
		System.out.println("Navigated to docView to view docs");
		UtilityLog.info("Navigated to docView to view docs");
	    driver.waitForPageToBeReady();
	    baseClass.waitTime(3);
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
	    driver.getWebDriver().get(Input.url + "Search/Searches");
	    driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearch.getBulkActionButton().Visible();
			}
		}), Input.wait30);
		Thread.sleep(2000); // App synch
		sessionSearch.getBulkActionButton().waitAndClick(5);
		Thread.sleep(2000); // App Synch
		docViewPage.getDocViewAction().waitAndClick(10);
		baseClass.waitTime(3); // added for stabilization
		System.out.println("Navigated to docView to view docs");
		UtilityLog.info("Navigated to docView to view docs");
	    driver.waitForPageToBeReady();
	    baseClass.waitTime(3);
	    driver.waitForPageToBeReady();
	    baseClass.waitTime(3);
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
		docViewPage.verifyTagsAreDisabledInPreviewBox(0);
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
		docViewPage.verifyTagsAreDisabledInPreviewBox(0);
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
		docViewPage.verifyTagsAreDisabledInPreviewBox(0);
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
			codingForm.assignCodingFormToSG("Default Project Coding Form");
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
	
	/**
	 * Author : Vijaya.Rani date: 02/03/22 NA Modified date: NA Modified by:NA
	 * Description :To verify custom coding form is editable or not when same
	 * document is assigned to two different assignments with assigned coding form
	 * in different security group. 'RPMXCON-50972' Sprint : 13
	 * 
	 * @throws AWTException
	 * @throws Exception
	 */
	@Test(enabled = true, dataProvider = "rmuAndrev", groups = { "regression" }, priority = 17)
	public void verifyCfEditableOrNotBasedOnDocStatusWithDiffrentCodingFormsWithSecurityGruop(String userName,
			String password, String user) throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-50972");
		baseClass.stepInfo(
				"To verify custom coding form is editable or not when same document is assigned to two different assignments with assigned coding form in different security group");

		String securityGroup = "Security" + Utility.dynamicNameAppender();
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Create security group
		securityGroupPage.navigateToSecurityGropusPageURL();
		securityGroupPage.AddSecurityGroup(securityGroup);

		// access to security group to REV
		userManagementPage.assignAccessToSecurityGroups(securityGroup, Input.rev1userName);
		userManagementPage.assignAccessToSecurityGroups(securityGroup, Input.rmu1userName);

		sessionSearch.basicContentSearch("null");
		sessionSearch.bulkRelease(securityGroup);

		loginPage.logout();
		
		// login as RMU
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Successfully login as " + user);
		
		System.out.println(assignment1);
		System.out.println(assignment2);
		// create new coding form
		if (user == "RMU") {
			this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
			codingForm.createCodingFormUsingTwoObjects(cfName1, null, null, null, "tag");
			codingForm.addcodingFormAddButton();
			codingForm.enterErrorAndHelpMsg(0, "Yes", "Help for testing", "Error for testing");
			String expectedFirstObjectName = codingForm.getCFObjectsLabel(0);
			System.out.println(expectedFirstObjectName);
			codingForm.saveCodingForm();
			this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
			codingForm.createCodingFormUsingTwoObjects(cfName2, null, null, null, "tag");
			codingForm.addcodingFormAddButton();
			codingForm.enterErrorAndHelpMsg(0, "Yes", "Help for testing", "Error for testing");
			codingForm.saveCodingForm();
			// create assignment
			sessionSearch.basicContentSearch("null");
			sessionSearch.bulkAssign();
			assignmentPage.assignmentCreation(assignment1, cfName1);
			assignmentPage.add2ReviewerAndDistribute();
			driver.getWebDriver().get(Input.url + "Search/Searches");
			sessionSearch.bulkAssignWithOutPureHit();
			assignmentPage.assignmentCreation(assignment2, cfName2);
			assignmentPage.add2ReviewerAndDistribute();
			baseClass.impersonateRMUtoReviewer();
			baseClass.stepInfo("Impersonated to reviewer successfully");
		}
		assignmentPage.SelectAssignmentByReviewer(assignment1);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		assignmentPage.completeAllDocsByReviewer(assignment1);
		driver.waitForPageToBeReady();
		if (reusableDocView.getUnCompleteButton().isElementAvailable(5) == true) {
			baseClass.passedStep("Documents are completed as expected");
		}
		docViewPage.verifyCodingFormName(codingform);
		baseClass.passedStep(
				"Coding form is non editable once all the docs are completed with diffrent assigned coding form");
		baseClass.selectproject();
		assignmentPage.SelectAssignmentByReviewer(assignment2);
		if (reusableDocView.getUnCompleteButton().isElementAvailable(5) == false) {
			baseClass.passedStep("Documents are not completed as expected with diffrent assigned coding form");
		}
		docViewPage.verifyCodingFormName(codingform);
		docViewPage.verifyTagsAreEnabled(0);
		docViewPage.verifyTagsAreEnabled(1);
		baseClass.passedStep("Coding form is editable for uncompleted docs");
		if (user == "REV") {
			loginPage.logout();
			// delete assignment and codinform
			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			assignmentPage.deleteAssgnmntUsingPagination(assignment1);
			assignmentPage.deleteAssgnmntUsingPagination(assignment2);
			this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
			codingForm.deleteCodingForm(cfName1, cfName1);
			this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
			codingForm.deleteCodingForm(cfName2, cfName2);
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
