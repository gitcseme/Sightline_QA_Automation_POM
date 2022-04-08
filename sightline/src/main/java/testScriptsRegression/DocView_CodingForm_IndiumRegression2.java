package testScriptsRegression;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import org.testng.asserts.SoftAssert;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AnnotationLayer;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.CommentsPage;
import pageFactory.DocExplorerPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.KeywordPage;
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
import views.html.helper.input;

public class DocView_CodingForm_IndiumRegression2 {
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
	TagsAndFoldersPage tagsAndFoldersPage;
	KeywordPage keywordPage;
	ReusableDocViewPage reusableDocView;
	MiniDocListPage miniDocListPage;
	CommentsPage commentsPage;
	UserManagement userManagementPage;

	String projectFieldINT = "DataINT" + Utility.dynamicNameAppender();
	String projectFieldNVARCHAR = "Nvarchar" + Utility.dynamicNameAppender();
	String projectFieldBit = "DataBIT" + Utility.dynamicNameAppender();
	String assignmentCreationStampOff = "StampOFF" + Utility.dynamicNameAppender();
	String assignmentCreationOn = "StampON" + Utility.dynamicNameAppender();
	String saveText = "TestFolder" + Utility.dynamicNameAppender();
	String completeBtnCodingForm = "popupStamp" + Utility.dynamicNameAppender();
	String tinyInt = "TinyInt" + Utility.dynamicNameAppender();
	String smallInt = "SmallInt" + Utility.dynamicNameAppender();
	String avearageInt = "SmallInt" + Utility.dynamicNameAppender();
	String bigInt = "BigInt" + Utility.dynamicNameAppender();
	String hugeInt = "HugeInt" + Utility.dynamicNameAppender();
	String floppyIconTooltip = "Save this coding form as a new stamp";
	String roll = "rmu";
	String stampOverWrite = "The Stamp you selected is already in use. Do you want to overwrite this Stamp with the new selections?";
	String date = "Date" + Utility.dynamicNameAppender();
	String cfNameEdit = "CF" + Utility.dynamicNameAppender();
	String assignOne = "Assignment" + Utility.dynamicNameAppender();
	String assignTwo = "Assignment" + Utility.dynamicNameAppender();
	String assignThree = "Assignment" + Utility.dynamicNameAppender();
	String assignFour = "Assignment" + Utility.dynamicNameAppender();
	String assignFive = "Assignment" + Utility.dynamicNameAppender();
	String assign = "Assignment" + Utility.dynamicNameAppender();
	String assgn = "Assignment" + Utility.dynamicNameAppender();
	String assgnCf = "Assignment" + Utility.dynamicNameAppender();
	String cfName = "CfName" + Utility.dynamicNameAppender();
	String cfLarge = "CfNameLarge" + Utility.dynamicNameAppender();
	String dateTime = "DateTime" + Utility.dynamicNameAppender();
	String intData = "INT" + Utility.dynamicNameAppender();
	String NVARCHAR = "Nvarchar" + Utility.dynamicNameAppender();
	String time = "Time" + Utility.dynamicNameAppender();
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
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();
	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) throws ParseException, InterruptedException, IOException {
		System.out.println("Executing method : " + testMethod.getName());
		UtilityLog.info("Executing method : " + testMethod.getName());
		Input in = new Input();
		in.loadEnvConfig();
		driver = new Driver();
		baseClass = new BaseClass(driver);
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		savedSearch = new SavedSearch(driver);
		projectPage = new ProjectPage(driver);
		securityGroupPage = new SecurityGroupsPage(driver);
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		sessionSearch = new SessionSearch(driver);
		tagsAndFoldersPage = new TagsAndFoldersPage(driver);
		keywordPage = new KeywordPage(driver);
		reusableDocView = new ReusableDocViewPage(driver);
		miniDocListPage = new MiniDocListPage(driver);
		userManagementPage = new UserManagement(driver);

	}


	/**
	 * Author : Baskar date: NA Modified date:NA Modified by: Baskar TODO:BUG IN
	 * APPLICATION. EXPECT FAILURE ON THIS TEST CASE Description :Coding form child
	 * window: Verify when user clicks 'code same as last' after saving the document
	 * in context of assignment RPMXCON-52159 DocView/Coding Forms Sprint 01
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 1)
	public void codingFormChildWindowCodeSameAsLast() throws AWTException, InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52159");
		// login as Rmu
		String assignmentName = "AssName" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.failedStep("Expected failure case RPMXCON-52159");
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignmentName, Input.codingFormName);
		assignmentPage.toggleSaveButton();
		assignmentPage.assignmentDistributingToReviewer();

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");

		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assignmentName);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// child window code as last
		docViewPage.codingFormChildWindowCodeAsLast(comment);

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

	}

	/**
	 * Author : Baskar date: NA Modified date: NA Modified by: Baskar TODO:BUG IN
	 * APPLICATION. EXPECT FAILURE ON THIS TEST CASE Description :Coding form child
	 * window: Verify when user clicks 'code same as last' after saving the document
	 * on applying the stamp in context of assignment RPMXCON-52160
	 * DocView/CodingForms Sprint 01
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 2)
	public void codingFormAssignmentLevelCodingStampOFF() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52160");
		// login as Rmu
		String assignmentName = "AssName" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.failedStep("Expected failure case RPMXCON-52160");
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignmentName, Input.codingFormName);
		assignmentPage.toggleSaveButton();
		assignmentPage.assignmentDistributingToReviewer();

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");

		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Assignment Selection in Reviewer Login
		assignmentPage.SelectAssignmentByReviewer(assignmentName);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		docViewPage.docViewCodingFormPanelStampSelection(Input.stampSelection, comment);
		docViewPage.codingFormChildWindowCodeAsLast(comment);

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");


	}


	/**
	 * @Author : Baskar date:2/9/21 Modified date: NA Modified by: Baskar
	 * @TODO:BUG IN APPLICATION. EXPECT FAILURE ON THIS TEST CASE
	 * @Description : Verify that already saved stamp presentation should be
	 *              displayed with post-fix as 'Assigned' in edit coding stamp pop
	 *              up form Coding Form panel
	 * 
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 3)
	public void verifyingCodingStampPostFixColourParentWindow() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52226");
		String assgnColour = "AssignColour" + Utility.dynamicNameAppender();
		String assignmentName = "AssName" + Utility.dynamicNameAppender();
		// Login As Reviewer
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		codingForm.assignCodingFormToSG(Input.codeFormName);

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		// Stamp selection
		docViewPage.clickStampBtnAndVerifyINPopUp(assgnColour, Input.stampColours, Input.stampColours);

		// assignment creation
		driver.getWebDriver().get(Input.url + "Search/Searches");
		baseClass.waitForElement(sessionSearch.getBulkActionButton());
		sessionSearch.getBulkActionButton().waitAndClick(3);
		baseClass.waitForElement(sessionSearch.getBulkAssignAction());
		sessionSearch.getBulkAssignAction().waitAndClick(3);
		baseClass.stepInfo("performing bulk assign");
		UtilityLog.info("performing bulk assign");
		assignmentPage.assignmentCreation(assignmentName, Input.codingFormName);
		assignmentPage.toggleSaveButton();
		assignmentPage.assignmentDistributingToReviewer();

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");

		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assignmentName);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// Stamp selection
		docViewPage.clickStampBtnAndVerifyINPopUp(assgnColour, Input.stampColours, Input.stampColours);

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");
	}

	/**
	 * @Author : Baskar date:13/9/21 Modified date: NA Modified by: Baskar
	 * @TODO:BUG IN APPLICATION. EXPECT FAILURE ON THIS TEST CASE
	 * @Description : Verify that already saved stamp presentation should be
	 *              displayed with post-fix as 'Assigned' in edit coding stamp pop
	 *              up form Coding Form child window
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 4)
	public void verifyingCodingStampPostFixColourAndEditing() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52227");
		String assgnColour = "AssignColour" + Utility.dynamicNameAppender();
		String assignmentName = "AssName" + Utility.dynamicNameAppender();
		// Login As Reviewer
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		codingForm.assignCodingFormToSG(Input.codeFormName);

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		// Stamp selection
		docViewPage.openStampPopUpFromChildWindow(assgnColour, Input.stampColours, Input.stampColours);

		// assignment creation
		driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.switchTo().alert().accept();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sessionSearch.getBulkActionButton());
		sessionSearch.getBulkActionButton().waitAndClick(3);
		baseClass.waitForElement(sessionSearch.getBulkAssignAction());
		sessionSearch.getBulkAssignAction().waitAndClick(3);
		baseClass.stepInfo("performing bulk assign");
		UtilityLog.info("performing bulk assign");
		assignmentPage.assignmentCreation(assignmentName, Input.codingFormName);
		assignmentPage.toggleSaveButton();
		assignmentPage.assignmentDistributingToReviewer();

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");

		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assignmentName);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// Stamp selection
		docViewPage.openStampPopUpFromChildWindow(assgnColour, Input.stampColours, Input.stampColours);

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");


	}


	/**
	 * @Author : Baskar date:15/9/21 Modified date: NA Modified by: Baskar
	 * @TODO:BUG IN APPLICATION. EXPECT FAILURE ON THIS TEST CASE
	 * @Description : Verify when user clicks code same as last on selecting the
	 *              code same as action for document selected from mini doc list
	 *              child window in context of a security group
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 5)
	public void clickLastDocumentInChildWindow() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52132");
		String assgnColour = "AssignC" + Utility.dynamicNameAppender();

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.failedStep("Expected failure: Code same as last not clickable in child window");
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocList();
		docViewPage.codeSameAsLastSelectDocInDocList(assgnColour, Input.stampColour, Input.stampColour,
				Input.stampColour);

		loginPage.logout();


	}

	/**
	 * @Author : Mohan date:20/12/21 Modified date: NA Modified by: NA
	 * @Description : Reviewer of an assignment can not complete the document
	 *              outside his batch when 'Allow coding outside reviewer batch' is
	 *              off at assignment level 'RPMXCON-50962'
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 6)
	public void assignmentCannotCompleteDocsOutsideBatch() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-50962");
		baseClass.stepInfo(
				"Reviewer of an assignment can not complete the document outside his batch when 'Allow coding outside reviewer batch' is off at assignment level");
		String assName = "2022Loading" + Utility.dynamicNameAppender();
		

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Session search to doc view Coding Form
		baseClass.stepInfo("Step 1: Serach for the Docs and bulk assign it");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();

		baseClass.stepInfo(
				"Step 2: Disable the preferences 'Allow coding outside reviewer batch' for assignment and save   For RMU user impersonate as reviewer");
		assignmentPage.assignmentCreation(assName, Input.codingFormName);
		assignmentPage.toggleDisableCodeOutsideReviewersBatch();
		assignmentPage.add2ReviewerAndDistribute();

		baseClass.stepInfo(
				"Step 3: Select the assignment same as from pre-requisites and go to doc view page from my assignment");
		baseClass.impersonateRMUtoReviewer();
		assignmentPage.SelectAssignmentByReviewer(assName);

		baseClass.stepInfo(
				"Step 4: Select document from analytics panel outside the reviewer batch but within the assignment and action as code same as this, complete the document");
		driver.waitForPageToBeReady();
		docViewPage.selectDocIdInMiniDocList(Input.cfDocId1);

		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocView_Analytics_liDocumentConceptualSimilarab());
		docViewPage.getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(5);

		baseClass.waitForElement(docViewPage.getDocView_Analytics_Conceptual_FirstDoc());
		docViewPage.getDocView_Analytics_Conceptual_FirstDoc().waitAndClick(5);
		baseClass.waitForElement(docViewPage.getDocView_ChildWindow_ActionButton());
		docViewPage.getDocView_ChildWindow_ActionButton().waitAndClick(5);

		baseClass.waitForElement(docViewPage.getDocView_Analytics_Concept_Similar_CodeSameAs());
		docViewPage.getDocView_Analytics_Concept_Similar_CodeSameAs().waitAndClick(15);

		baseClass.VerifyErrorMessage(
				"Some selected docs are not distributed to you, and coding documents outside of your batch is not allowed by your review manager. Please uncheck them and try again.");
		//baseClass.CloseSuccessMsgpopup();

		driver.waitForPageToBeReady();
		docViewPage.editCodingFormComplete();

		// logout
		loginPage.logout();

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		baseClass.stepInfo(
				"Step 3: Select the assignment same as from pre-requisites and go to doc view page from my assignment");
		assignmentPage.SelectAssignmentByReviewer(assName);

		baseClass.stepInfo(
				"Step 4: Select document from analytics panel outside the reviewer batch but within the assignment and action as code same as this, complete the document");
		driver.waitForPageToBeReady();
		docViewPage.selectDocIdInMiniDocList(Input.cfDocId2);

		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocView_Analytics_liDocumentConceptualSimilarab());
		docViewPage.getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(5);

		baseClass.waitForElement(docViewPage.getDocView_Analytics_Conceptual_FirstDoc());
		docViewPage.getDocView_Analytics_Conceptual_FirstDoc().waitAndClick(5);
		baseClass.waitForElement(docViewPage.getDocView_ChildWindow_ActionButton());
		docViewPage.getDocView_ChildWindow_ActionButton().waitAndClick(5);

		baseClass.waitForElement(docViewPage.getDocView_Analytics_Concept_Similar_CodeSameAs());
		docViewPage.getDocView_Analytics_Concept_Similar_CodeSameAs().waitAndClick(15);

		baseClass.VerifyErrorMessage(
				"Some selected docs are not distributed to you, and coding documents outside of your batch is not allowed by your review manager. Please uncheck them and try again.");
		baseClass.CloseSuccessMsgpopup();

		driver.waitForPageToBeReady();
		docViewPage.editCodingFormComplete();

		// logout
		loginPage.logout();

	}


	/**
	 * @Author : Mohan date:21/12/21 Modified date: NA Modified by: NA
	 * @Description : Verify that when coding stamp is created/saved using the
	 *              coding of a completed document viewed from analytics panel
	 *              outside of reviewers batch, then on mouse hover tool tip should
	 *              be displayed for the stamp icon. 'RPMXCON-51577'
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 7)
	public void assignmentCannotCompleteDocsOutsideBatchCodingStamp() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51577");
		baseClass.stepInfo(
				"Verify that when coding stamp is created/saved using the coding of a completed document viewed from analytics panel outside of reviewers batch, then on mouse hover tool tip should be displayed for the stamp icon.");
		String assName = "2022Loading" + Utility.dynamicNameAppender();
		String stampName = "Stamp" + Utility.dynamicNameAppender();
		String colour = "BLUE";

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Session search to doc view Coding Form
		baseClass.stepInfo("Step 1: Serach for the Docs and bulk assign it");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();

		baseClass.stepInfo(
				"Step 2: Disable the preferences 'Allow coding outside reviewer batch' for assignment and save   For RMU user impersonate as reviewer");
		assignmentPage.assignmentCreation(assName, Input.codingFormName);
		assignmentPage.toggleDisableCodeOutsideReviewersBatch();
		assignmentPage.add2ReviewerAndDistribute();

		baseClass.stepInfo(
				"Step 3: Select the assignment same as from pre-requisites and go to doc view page from my assignment");
		baseClass.impersonateRMUtoReviewer();
		assignmentPage.SelectAssignmentByReviewer(assName);

		baseClass.stepInfo(
				"Step 4: Select document from analytics panel outside the reviewer batch but within the assignment and action as code same as this, complete the document");
		driver.waitForPageToBeReady();
		docViewPage.selectDocIdInMiniDocList(Input.cfDocId1);

		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocView_Analytics_liDocumentConceptualSimilarab());
		docViewPage.getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(5);

		baseClass.waitForElement(docViewPage.getDocView_Analytics_Conceptual_FirstDoc());
		docViewPage.getDocView_Analytics_Conceptual_FirstDoc().waitAndClick(5);
		baseClass.waitForElement(docViewPage.getDocView_ChildWindow_ActionButton());
		docViewPage.getDocView_ChildWindow_ActionButton().waitAndClick(5);

		baseClass.waitForElement(docViewPage.getDocView_Analytics_Concept_Similar_CodeSameAs());
		docViewPage.getDocView_Analytics_Concept_Similar_CodeSameAs().waitAndClick(15);

		baseClass.VerifyErrorMessage(
				"Some selected docs are not distributed to you, and coding documents outside of your batch is not allowed by your review manager. Please uncheck them and try again.");
		baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		driver.waitForPageToBeReady();

		docViewPage.editCodingFormComplete();

		docViewPage.selectDocInMiniDocList(Input.cfDocId1);

		reusableDocView.stampColourSelection(stampName, colour);
		driver.waitForPageToBeReady();

		Actions act = new Actions(driver.getWebDriver());
		act.moveToElement(docViewPage.getDocView_CodingForm_BlueIcon().getWebElement()).build().perform();
		baseClass.waitTime(3);
		baseClass.waitForElement(docViewPage.getDocView_CodingForm_BlueIcon());
		String toolTipName = docViewPage.getDocView_CodingForm_BlueIcon().getWebElement().getAttribute("title");
		softAssertion.assertEquals(stampName, toolTipName);
		softAssertion.assertAll();

		// logout
		loginPage.logout();

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		baseClass.stepInfo(
				"Step 3: Select the assignment same as from pre-requisites and go to doc view page from my assignment");
		assignmentPage.SelectAssignmentByReviewer(assName);

		baseClass.stepInfo(
				"Step 4: Select document from analytics panel outside the reviewer batch but within the assignment and action as code same as this, complete the document");
		driver.waitForPageToBeReady();
		docViewPage.selectDocIdInMiniDocList(Input.cfDocId2);

		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocView_Analytics_liDocumentConceptualSimilarab());
		docViewPage.getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(5);

		baseClass.waitForElement(docViewPage.getDocView_Analytics_Conceptual_FirstDoc());
		docViewPage.getDocView_Analytics_Conceptual_FirstDoc().waitAndClick(5);
		baseClass.waitForElement(docViewPage.getDocView_ChildWindow_ActionButton());
		docViewPage.getDocView_ChildWindow_ActionButton().waitAndClick(5);

		baseClass.waitForElement(docViewPage.getDocView_Analytics_Concept_Similar_CodeSameAs());
		docViewPage.getDocView_Analytics_Concept_Similar_CodeSameAs().waitAndClick(15);

		baseClass.VerifyErrorMessage(
				"Some selected docs are not distributed to you, and coding documents outside of your batch is not allowed by your review manager. Please uncheck them and try again.");
		baseClass.CloseSuccessMsgpopup();
        driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		docViewPage.editCodingFormComplete();

		docViewPage.selectDocInMiniDocList(Input.cfDocId2);

		reusableDocView.stampColourSelection(stampName, colour);
		driver.waitForPageToBeReady();
		Actions act1 = new Actions(driver.getWebDriver());
		act1.moveToElement(docViewPage.getDocView_CodingForm_BlueIcon().getWebElement()).build().perform();
		baseClass.waitTime(3);
		baseClass.waitForElement(docViewPage.getDocView_CodingForm_BlueIcon());
		String toolTipName1 = docViewPage.getDocView_CodingForm_BlueIcon().getWebElement().getAttribute("title");
		softAssertion.assertEquals(stampName, toolTipName1);
		softAssertion.assertAll();

		// logout
		loginPage.logout();

	}


	/**
	 * Author : Baskar date: NA Modified date:29/12/2021 Modified by: Baskar
	 * Description : Verify comment, metadata should be indexed for the document
	 * when user clicks 'Complete same as last doc' to the document in context of an
	 * assignment
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 8)
	public void validateCommentAndMetadataPureHitCont() throws InterruptedException, AWTException {
		projectPage = new ProjectPage(driver);
		securityGroupPage = new SecurityGroupsPage(driver);
		commentsPage = new CommentsPage(driver);
		docViewPage = new DocViewPage(driver);
		codingForm = new CodingForm(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-51151");
		baseClass.stepInfo("Verify comment, metadata should be indexed for the document when user clicks "
				+ "'Complete same as last doc' to the document in context of an assignment");
		UtilityLog.info("Started Execution for prerequisite");
		String assignName = "AAsign" + Utility.dynamicNameAppender();
		String addComment = "addomment" + Utility.dynamicNameAppender();
		String cfName = "coding" + Utility.dynamicNameAppender();
		String commentText = "ct" + Utility.dynamicNameAppender();
		String metadataText = "mt" + Utility.dynamicNameAppender();

		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");

		// Custom Field created with INT DataType
		projectPage.addCustomFieldProjectDataType(projectFieldINT, "NVARCHAR");
		baseClass.stepInfo("Custom meta data field created with INT datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(projectFieldINT);
		baseClass.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		commentsPage.AddComments(addComment);

		// Creating Coding Form
		codingForm.createCommentAndMetadata(projectFieldINT, addComment, cfName);
		baseClass.stepInfo("Project field added to coding form in Doc view");

		// searching document for assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignName, cfName);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.assignmentDistributingToReviewer();

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager'" + Input.rmu1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer '" + Input.rev1userName + "'");

		// selecting the assignment
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// verify the coding form panel
		int size = docViewPage.verifyCommentAndMetadataUsingComplete(addComment, commentText, projectFieldINT,
				metadataText);
		baseClass.waitTime(10);
		baseClass.stepInfo("Checking index of comment and metadata for saved document");
		int pureHit = sessionSearch.metadataAndCommentSearch(projectFieldINT, metadataText, addComment, commentText);

		softAssertion.assertEquals(size, pureHit);
		softAssertion.assertAll();

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");
	}


	/**
	 * @Author : Sakthivel date:03/01/2022 Modified date:NA
	 * @Description :To verify that tag should be read only when the dependent tag
	 *              is selected/unselected.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 9)
	public void verifyCfDependentTagSelectOrNot() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		codingForm = new CodingForm(driver);
		String codingfrom = "CF" + Utility.dynamicNameAppender();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		baseClass.stepInfo("Test case Id: RPMXCON- 51185");
		baseClass.stepInfo("To verify that tag should be read only when the dependent tag is selected/unselected");

		// creating CodingForm Tags.
		codingForm.createTagsSavedInCf(codingfrom);
		codingForm.verifyCfTagDependentSelctedOrNot();

		// Logout as Reviewer manager
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager'" + Input.rmu1userName + "'");
	}

	/**
	 * @author Sakthivel RPMXCON-51186 date:03/01/2022 Modified date:NA
	 * @Description :Verify on click of 'Save' button coding form should be
	 *              validated when coding form customized for all objects along with
	 *              all condition and Check Item.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 10)
	public void verifySaveBtnValidateCfCheckItem() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-51186");
		baseClass.stepInfo("Verify on click of 'Save' button coding form should be validated when coding form"
				+ "customized for all objects along with all condition and Check Item");
		String cfName = "CF" + Utility.dynamicNameAppender();
		String assignName = "CFAssignment" + Utility.dynamicNameAppender();
		String tag = "cfTag" + Utility.dynamicNameAppender();

		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		codingForm = new CodingForm(driver);
		docViewPage = new DocViewPage(driver);
		softAssertion = new SoftAssert();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// create tag
		tagsAndFoldersPage.CreateTag(tag, "Default Security Group");

		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		codingForm.addNewCodingFormButton();
		baseClass.waitForElement(codingForm.getCodingForm_Tag(tag));
		codingForm.getCodingForm_Tag(tag).waitAndClick(10);

		// create new coding form
		codingForm.verifyCodingFormObjectSaved(cfName);
		codingForm.passingCodingFormName(cfName);
		String getTagLabelCf = codingForm.getCodingForm_StaticText(2).GetAttribute("value");
		System.out.println(getTagLabelCf);
		String getVerifyStaticTextcf = codingForm.getCodingForm_StaticText(3).GetAttribute("value");
		System.out.println(getVerifyStaticTextcf);
		codingForm.saveCodingForm();
		baseClass.stepInfo("Coding form created all object along with check item");

		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");

		// session search in assignment
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		baseClass.stepInfo("Search with text input for docs completed");

		// Creating Assignment from Basic search
		assignmentPage.assignmentCreation(assignName, cfName);
		assignmentPage.toggleCodingStampEnabled();
		baseClass.stepInfo(
				"Doc is Assigned from basic Search and Assignment '" + assignName + "' is created Successfully");
		assignmentPage.add2ReviewerAndDistribute();
		assignmentPage.selectAssignmentToViewinDocviewThreadMap(assignName);
		driver.waitForPageToBeReady();

		// verify tag names
		baseClass.waitForElement(docViewPage.getInstructionTxt());
		String getVerifyAttachCount = docViewPage.getInstructionTxt().getText();
		System.out.println(getVerifyAttachCount);
		softAssertion.assertEquals(getTagLabelCf, getVerifyAttachCount);
		baseClass.stepInfo("saved codingform Object AttachCount is verify successfully");

		baseClass.waitForElement(docViewPage.getStaticText());
		String getVerifyStaticText = docViewPage.getStaticText().getText();
		softAssertion.assertEquals(getVerifyStaticTextcf, getVerifyStaticText);
		System.out.println(getVerifyStaticText);
		baseClass.stepInfo("saved codingform Object staticText is verify successfully");

		// verify DocView CodingForm saved object
		if (docViewPage.getAddComment1().Visible()) {
			baseClass.failedStep("verify coding form saved comment name in docview page is displayed ");
		} else {
			baseClass.passedStep("verify coding form saved comment name in docview page is not displayed ");
		}
		if (docViewPage.getMetaDataInputInDocView().Enabled()) {
			baseClass.failedStep("verify coding Form Saved commentbox is selectable");
		} else {
			baseClass.passedStep("verify coding Form Saved commentbox is not selectable");
		}
		docViewPage.verifyCodingFormName(cfName);
		docViewPage.validateRadioOrCheckGroupInDocviewPg("check-group");

		// validate Coding Form saved object is failed
		driver.waitForPageToBeReady();
		docViewPage.getSaveDoc().waitAndClick(10);
		docViewPage.errorMessage();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getCfCheckBox());
		docViewPage.getCfCheckBox().waitAndClick(10);
		docViewPage.getSaveDoc().waitAndClick(10);
		baseClass.stepInfo("Document saved successfully");

		// Logout as Reviewer manager
		loginPage.logout();

		// Login As reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Select the Assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");
		driver.waitForPageToBeReady();

		// verify tag names
		baseClass.waitForElement(docViewPage.getInstructionTxt());
		String getVerifyAttachCounts = docViewPage.getInstructionTxt().getText();
		softAssertion.assertEquals(getTagLabelCf, getVerifyAttachCounts);
		baseClass.stepInfo("saved codingform Object AttachCount is verify successfully");

		baseClass.waitForElement(docViewPage.getStaticText());
		String getVerifyStaticTexts = docViewPage.getStaticText().getText();
		softAssertion.assertEquals(getVerifyStaticTextcf, getVerifyStaticTexts);
		System.out.println(getVerifyStaticText);
		baseClass.stepInfo("saved codingform Object staticText is verify successfully");

		// verify DocView CodingForm saved object
		if (docViewPage.getAddComment1().Visible()) {
			baseClass.failedStep("verify coding form saved comment name in docview page is displayed ");
		} else {
			baseClass.passedStep("verify coding form saved comment name in docview page is not displayed ");
		}
		if (docViewPage.getMetaDataInputInDocView().Enabled()) {
			baseClass.failedStep("verify coding Form Saved commentbox is selectable");
		} else {
			baseClass.passedStep("verify coding Form Saved commentbox is not selectable");
		}
		docViewPage.verifyCodingFormName(cfName);
		docViewPage.validateRadioOrCheckGroupInDocviewPg("check-group");

		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getCfCheckBox());
		docViewPage.getCfCheckBox().waitAndClick(10);
		docViewPage.getCompleteDocBtn().waitAndClick(10);
		baseClass.stepInfo("Document saved successfully");

		// validate Coding Form saved object is failed
		driver.waitForPageToBeReady();
		docViewPage.getCompleteDocBtn().waitAndClick(10);
		docViewPage.errorMessage();
		baseClass.CloseSuccessMsgpopup();

		// logout as reviewer
		loginPage.logout();
		baseClass.stepInfo("Successfully logout as Reviewer'" + Input.rev1userName + "'");
		baseClass.passedStep("Verify on click of 'Save' button coding form should be validated when coding form+"
				+ "customized for all objects along with all condition and Check Item");
	}

	/**
	 * @Author : Baskar date:07/01/22 Modified date: NA Modified by: Baskar
	 * @Description : Verify on edit of the coding form objects, updated objects
	 *              should be displayed on doc view
	 */
	@DataProvider(name = "rmuRevLoginRole")
	public Object[][] rmuRevLoginRole() {
		return new Object[][] { { "rmu1", Input.rmu1userName, Input.rmu1password },
				{ "rev1", Input.rev1userName, Input.rev1password }, { "rmu2", Input.rmu1userName, Input.rmu1password },
				{ "rev2", Input.rev1userName, Input.rev1password }, };
	}

	@Test(enabled = true, dataProvider = "rmuRevLoginRole", groups = { "regression" }, priority = 11)
	public void afterEditCodingFormObjectShouldDisplay(String roll, String userName, String password)
			throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-51189");
		baseClass.stepInfo(
				"Verify on edit of the coding form objects, " + "updated objects should be displayed on doc view");

		codingForm = new CodingForm(driver);
		sessionSearch = new SessionSearch(driver);
		tagsAndFoldersPage = new TagsAndFoldersPage(driver);
		docViewPage = new DocViewPage(driver);
		softAssertion = new SoftAssert();

		String nameRmu1 = "rmu1";
		String nameRmu2 = "rmu2";
		String nameRev1 = "rev1";
		String nameRev2 = "rev2";
		String comment = "comment" + Utility.dynamicNameAppender();

		// login as RMU
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu2userName + "'");

		if (roll == nameRmu1) {
			// Creating Coding Form
			codingForm.commentRequired(cfNameEdit);
			// Assign to security group
			codingForm.assignCodingFormToSG(cfNameEdit);
			System.out.println(cfNameEdit);
			baseClass.stepInfo("Coding form assigned to security group");
		}
		if (roll == nameRmu1 || roll == nameRev1) {
			sessionSearch.basicContentSearch(Input.searchString1);
			sessionSearch.ViewInDocView();
			baseClass.stepInfo("user landed on the docview page");

			driver.waitForPageToBeReady();
			baseClass.waitForElement(docViewPage.getDocument_CommentsTextBox());
			docViewPage.getDocument_CommentsTextBox().SendKeys(comment);
			docViewPage.codingFormSaveButton();
			baseClass.VerifySuccessMessage("Document saved successfully");
		}
		if (roll == nameRmu2) {
			codingForm.editCodingForm(cfNameEdit);
			baseClass.stepInfo("Editing the coding form object and back to docview page");
			codingForm.specialObjectsBox("staticText");
			codingForm.addcodingFormAddButton();
			baseClass.waitForElement(codingForm.getSaveCFBtn());
			codingForm.getSaveCFBtn().waitAndClick(5);
			baseClass.waitForElement(codingForm.getCodingForm_Validation_ButtonYes());
			codingForm.getCodingForm_Validation_ButtonYes().waitAndClick(5);
			baseClass.VerifySuccessMessage("Coding Form updated successfully");
			baseClass.CloseSuccessMsgpopup();
		}
		if (roll == nameRmu2 || roll == nameRev2) {
			// Session search to docview to validate updated object in coding form
			sessionSearch.basicContentSearch(Input.searchString1);
			sessionSearch.ViewInDocView();
			boolean flag = docViewPage.getVerifyStaticText("Static Text").isDisplayed();
			softAssertion.assertTrue(flag);
			baseClass.passedStep("After editing coding form updated object displayed in docview page" + roll + "");
		}
		softAssertion.assertAll();
		// logout
		loginPage.logout();
	}
	@DataProvider(name = "userDetails")
	public Object[][] userLoginSaPaRmu() {
		return new Object[][] { { "sa", Input.sa1userName, Input.sa1password, "rmu" },
				{ "sa", Input.sa1userName, Input.sa1password, "rev" },
				{ "da", Input.da1userName, Input.da1password, "rmu" },
				{ "da", Input.da1userName, Input.da1password, "rev" },
				{ "pa", Input.pa1userName, Input.pa1password, "rmu" },
				{ "pa", Input.pa1userName, Input.pa1password, "rev" } };
	}

	/**
	 * @Author : Baskar date: 07/01/2022 Modified date: NA Modified by: Baskar
	 * @Description:Verify after impersonation on click of 'Save' button coding form
	 *                     should be validated outside of an assignment context
	 */

	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 12)
	public void validateCodingFormAfterImpersonateSecurity(String roll, String userName, String password,
			String impersonate) throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		codingForm = new CodingForm(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-51192");
		baseClass.stepInfo("Verify after impersonation on click of 'Save' "
				+ "button coding form should be validated outside of an assignment context");
		String comment = "comment" + Utility.dynamicNameAppender();
		String cfName = "cf" + Utility.dynamicNameAppender();

		// Login As
		loginPage.loginToSightLine(userName, password);

		if (roll.equalsIgnoreCase("rmu")) {
			// Creating Coding Form
			codingForm.commentRequired(cfName);
			baseClass.stepInfo("Coding form created as per default selection");
			// Assign to security group
			codingForm.assignCodingFormToSG(cfName);
			driver.waitForPageToBeReady();
			baseClass.stepInfo("Coding form assigned to security group");

		}
		baseClass.credentialsToImpersonateAsRMUREV(roll, impersonate);
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();
		docViewPage.getDocument_CommentsTextBox().Clear();
		docViewPage.codingFormSaveButton();
		docViewPage.errorMessage();
		baseClass.waitForElement(docViewPage.getDocument_CommentsTextBox());
		docViewPage.getDocument_CommentsTextBox().SendKeys(comment);
		docViewPage.codingFormSaveButton();
		driver.waitForPageToBeReady();
		baseClass.passedStep("Coding form validated as per customized field" + roll + "");

		driver.waitForPageToBeReady();
		loginPage.logout();
	}

	/**
	 * @Author : Iyappan.Kasinathan
	 * @Description:Verify when user clicks 'Save and Next' when vieweing the last
	 *                     document of mini doc list
	 */
	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 13)
	public void validateSaveAndNextActionInLastDocOfMiniDocList(String fullname, String username, String password)
			throws InterruptedException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		reusableDocView = new ReusableDocViewPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52115");
		baseClass.stepInfo("Verify when user clicks 'Save and Next' when vieweing the last document of mini doc list");
		// Login As
		loginPage.loginToSightLine(username, password);
		// Session search to doc view Coding Form
		if (fullname.contains("RMU")) {
			codingForm.assignCodingFormToSG(Input.codeFormName);
		}
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		docViewPage.selectPureHit();
		sessionSearch.advancedNewContentSearch1(Input.searchString1);
		sessionSearch.ViewInDocView();
		reusableDocView.selectLastDocInMiniDocList();
		baseClass.stepInfo("Last document is selected in parent minidoc list window");
		reusableDocView.editingCodingFormWithSaveAndNextButton();
		baseClass.passedStep("Coding form saved successfully in parent window");
		baseClass.VerifySuccessMessage("Document saved successfully");
		docViewPage.verifyMinidocListAndCodingFormInChildWindow();
		baseClass.stepInfo("Excepted Message:Document completed successfully");
		loginPage.logout();
	}

	/**
	 * Author : Iyappan.Kasinathan Description: When the user clicks "uncomplete" on
	 * a completed record, the application should not automatically jog forward to
	 * the next record.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 14)
	public void verifyDocsNotAutomaticallyMoveNextAfterUncompleteAction() throws InterruptedException, AWTException {
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		reusableDocView = new ReusableDocViewPage(driver);
		String assignmentName = "assignment" + Utility.dynamicNameAppender();
		baseClass.stepInfo("Test case Id: RPMXCON-51284");
		baseClass.stepInfo(
				"When the user clicks \"uncomplete\" on a completed record, the application should not automatically jog forward to the next record.");
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		// Search to Assignment Creation stamp level off
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignmentName, Input.codingFormName);
		assignmentPage.toggleSaveButton();
		assignmentPage.assignmentDistributingToReviewer();
		// logout Reviewer Manager
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager'" + Input.rmu1userName + "'");
		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rev1userName + "'");
		assignmentPage.SelectAssignmentByReviewer(assignmentName);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		// Coding Stamp Selection And code Same As Verify
		reusableDocView.verifyNavigationOfDocAfterUnCompleteTheDoc();
		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignmentPage.deleteAssgnmntUsingPagination(assignmentName);
		loginPage.logout();
	}

	/**
	 * Author : Baskar date: NA Modified date:10/01/2022 Modified by: Baskar
	 * Description : Verify comment and metadata indexing when clicked saved stamp
	 * for the document in security group context
	 */

    @Test(enabled = true, groups = { "regression" }, priority = 15)
	public void validateCommentAndMetadataPureHitCountSavedStamp() throws InterruptedException, AWTException {
		projectPage = new ProjectPage(driver);
		securityGroupPage = new SecurityGroupsPage(driver);
		commentsPage = new CommentsPage(driver);
		docViewPage = new DocViewPage(driver);
		codingForm = new CodingForm(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-52114");
		baseClass.stepInfo("Verify comment and metadata indexing when clicked "
				+ "saved stamp for the document in security group context");
		UtilityLog.info("Started Execution for prerequisite");
		String addComment = "addomment" + Utility.dynamicNameAppender();
		String cfName = "coding" + Utility.dynamicNameAppender();
		String commentText = "ct" + Utility.dynamicNameAppender();
		String metadataText = "mt" + Utility.dynamicNameAppender();
		String stamp = "stampName" + Utility.dynamicNameAppender();

		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");

		// Custom Field created with INT DataType
		projectPage.addCustomFieldProjectDataType(projectFieldINT, "NVARCHAR");
		baseClass.stepInfo("Custom meta data field created with INT datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(projectFieldINT);
		baseClass.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		commentsPage.AddComments(addComment);

		// Creating Coding Form
		codingForm.createCommentAndMetadata(projectFieldINT, addComment, cfName);
		baseClass.stepInfo("Project field added to coding form in Doc view");
		codingForm.assignCodingFormToSG(cfName);

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		// verify the coding form panel
		docViewPage.verifyCommentAndMetadataUsingSavedStamp(addComment, commentText, projectFieldINT, metadataText,
				stamp);
		baseClass.waitTime(30);
		baseClass.stepInfo("Checking index of comment and metadata for saved document");
		int pureHitCount = sessionSearch.metadataAndCommentSearch(projectFieldINT, metadataText, addComment,
				commentText);

		codingForm.assignCodingFormToSG(Input.codingFormName);
		codingForm.deleteCodingForm(cfName, cfName);

		softAssertion.assertEquals(1, pureHitCount);
		softAssertion.assertAll();
		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager'" + Input.rmu1userName + "'");

	}

	/**
	 * @Author : Baskar date:10/01/2022 Modified date: NA Modified by: Baskar
	 * @Description : The Code Same As Last icon should be unclickable if the user
	 *              has not yet completed a document in the context of the
	 *              assignment
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 16)
	public void validateLastDocsShouldNotClickable() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-51245");
		baseClass.stepInfo("The Code Same As Last icon should be unclickable if the user "
				+ "has not yet completed a document in the context of the assignment");
		String assignName = "Assignment" + Utility.dynamicNameAppender();

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// searching document for assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignName, Input.codingFormName);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.assignmentDistributingToReviewer();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rev1userName + "'");

		// selecting the assignment
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// validate code same as last button should not clickable Without completing
		// docs
		driver.waitForPageToBeReady();
		boolean flag = docViewPage.getCodeSameAsLast().Selected();
		softAssertion.assertFalse(flag);
		softAssertion.assertAll();
		baseClass.passedStep("Code same as Last button is disabled");

		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Iyappan.Kasinathan
	 * @Description :Verify on click of 'Save and Next' button coding form should be
	 *              validated as per the customized coding form using multiple tags
	 *              elements in context of security group
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 17)
	public void verifyCustomizedCodingFormUsingDisabledMultipleTags() throws InterruptedException {
		docViewPage = new DocViewPage(driver);
		codingForm = new CodingForm(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		reusableDocView = new ReusableDocViewPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52070");
		baseClass.stepInfo(
				"Verify on click of 'Save and Next' button coding form should be validated as per the customized coding form using multiple tags elements in context of security group");
		String codingform = "CFTags" + Utility.dynamicNameAppender();
		String defaultAction = "Make It Required";
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.createCodingFormUsingTwoObjects(codingform, null, null, null, "tag");
		codingForm.addcodingFormAddButton();
		codingForm.selectDefaultActions(0, defaultAction);
		codingForm.enterErrorAndHelpMsg(0, "Yes", "Help for testing", "Error for testing");
		String expectedFirstObjectName = codingForm.getCFObjectsLabel(0);
		System.out.println(expectedFirstObjectName);
		codingForm.selectDefaultActions(1, defaultAction);
		codingForm.enterErrorAndHelpMsg(1, "Yes", "Help for testing", "Error for testing");
		String expectedSecondObjectName = codingForm.getCFObjectsLabel(1);
		System.out.println(expectedSecondObjectName);
		codingForm.saveCodingForm();
		codingForm.assignCodingFormToSG(codingform);
		// create assignment
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		docViewPage.selectPureHit();
		sessionSearch.advancedNewContentSearch1(Input.searchString1);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getSaveAndNextButton());
		docViewPage.getSaveAndNextButton().waitAndClick(5);
		docViewPage.verifyCodingFormName(codingform);
		// verify tags are disbled
		docViewPage.verifyTagsAreDisabledInPreviewBox(0);
		docViewPage.verifyTagsAreDisabledInPreviewBox(1);
		baseClass.stepInfo("Tags are selected and disabled in parent window");
		// verify tag names
		docViewPage.verifyCodingFormTagNameInDocviewPg(0, expectedFirstObjectName);
		docViewPage.verifyCodingFormTagNameInDocviewPg(1, expectedSecondObjectName);
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		String parentId = reusableDocView.switchTochildWindow();
		docViewPage.verifyTagsAreDisabledInPreviewBox(0);
		docViewPage.verifyTagsAreDisabledInPreviewBox(1);
		baseClass.stepInfo("Tags are selected and disabled in child window");
		reusableDocView.childWindowToParentWindowSwitching(parentId);
		driver.Navigate().refresh();
		driver.switchTo().alert().accept();
		codingForm.assignCodingFormToSG(Input.codingFormName);
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.deleteCodingForm(codingform, codingform);
		codingForm.verifyCodingFormIsDeleted(codingform);
		loginPage.logout();
	}

	/**
	 * Author : Iyappan.Kasinathan Description: To verify that if document marked as
	 * un-completed, then user can edit the coding form.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 18)
	public void verifyUncompletedDocCfBeEdited() throws InterruptedException, AWTException {
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		reusableDocView = new ReusableDocViewPage(driver);
		String assignmentName = "assignment" + Utility.dynamicNameAppender();
		baseClass.stepInfo("Test case Id: RPMXCON-48709");
		baseClass.stepInfo("To verify that if document marked as un-completed, then user can edit the coding form.");
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Search to Assignment Creation stamp level off
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignmentName, Input.codingFormName);
		assignmentPage.toggleSaveButton();
		assignmentPage.add2ReviewerAndDistribute();
		baseClass.impersonateRMUtoReviewer();
		assignmentPage.SelectAssignmentByReviewer(assignmentName);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		// Validation
		reusableDocView.verifyNavigationOfDocAfterUnCompleteTheDoc();
		reusableDocView.editingCodingFormWithSaveButton();
		baseClass.passedStep("Coding form is updated and saved sucessfully as Reviewer manager");
		// logout Reviewer Manager
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager'" + Input.rmu1userName + "'");
		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rev1userName + "'");
		assignmentPage.SelectAssignmentByReviewer(assignmentName);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		// Validation
		reusableDocView.verifyNavigationOfDocAfterUnCompleteTheDoc();
		reusableDocView.editingCodingFormWithSaveButton();
		baseClass.passedStep("Coding form is updated and saved sucessfully as Reviewer");
		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignmentPage.deleteAssgnmntUsingPagination(assignmentName);
		loginPage.logout();
	}

	/**
	 * @Author : Iyappan.Kasinathan
	 * @Description: Verify when SA/DA/PA user after impersonation clicks 'code same
	 *               as last' after saving the document on applying the stamp
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 19)
	public void verifyCodeSameAsLastAfterSavingDocOnApplyingStamp() throws InterruptedException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52144");
		baseClass.stepInfo(
				"Verify when SA/DA/PA user after impersonation clicks 'code same as last' after saving the document on applying the stamp");
		// Login As
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		baseClass.impersonatePAtoRMU();
		baseClass.stepInfo("Impersonated as RMU");
		// Session search to doc view Coding Form
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		docViewPage.selectPureHit();
		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch1(Input.testData1);
		sessionSearch.ViewInDocViews();
		driver.waitForPageToBeReady();
		reusableDocView.stampColourSelection(Input.searchString1, Input.stampColour);
		reusableDocView.getDocView_MiniDocListIds(2).waitAndClick(10);
		reusableDocView.lastAppliedStamp(Input.stampColour);
		baseClass.stepInfo("Stamp is applied on the doc");
		baseClass.waitForElement(docViewPage.getSaveAndNextButton());
		docViewPage.getSaveAndNextButton().waitAndClick(5);
		reusableDocView.clickCodeSameAsLastAndVerifyNavigatedToNextDoc();
		baseClass.VerifySuccessMessage("Coded as per the coding form for the previous document");
		reusableDocView.getDocView_MiniDocListIds(5).waitAndClick(10);
		baseClass.stepInfo("Moved to other document in mini doclist successfully");
		reusableDocView.clickCodeSameAsLastAndVerifyNavigatedToNextDoc();
		baseClass.VerifySuccessMessage("Coded as per the coding form for the previous document");
		reusableDocView.deleteStampColour(Input.stampColour);
		loginPage.logout();
	}

	/**
	 * @Author : Iyappan.Kasinathan
	 * @Description:Verify that code same as last should be displayed in context of
	 *                     security group
	 */
	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 20)
	public void verifyCodeSameAsLAstIconDisplayed(String fullname, String username, String password)
			throws InterruptedException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48801");// missing
		baseClass.stepInfo("Verify that code same as last should be displayed in context of security group");
		// Login As
		loginPage.loginToSightLine(username, password);
		// Session search to doc view Coding Form
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		docViewPage.selectPureHit();
		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch1(Input.testData1);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getCodeSameAsLast());
		if (docViewPage.getCodeSameAsLast().isDisplayed()) {
			baseClass.passedStep("Code same as last icon is displayed");
		} else {
			baseClass.failedStep("Code same as last icon is not displayed");
		}
		loginPage.logout();
	}

	/**
	 * @Author : Iyappan.Kasinathan
	 * @Description: Verify when SA/DA/PA user after impersonation clicks 'code same
	 *               as last' after saving the document
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 21)
	public void verifyCodeSameAsLastAfterSavingDoc() throws InterruptedException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		String comment = "comment" + Utility.dynamicNameAppender();
		baseClass.stepInfo("Test case Id: RPMXCON-52143");
		baseClass.stepInfo(
				"Verify when SA/DA/PA user after impersonation clicks 'code same as last' after saving the document");
		// Login As
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		baseClass.impersonatePAtoRMU();
		baseClass.stepInfo("Impersonated as RMU");
		
		// assign default coding form
		codingForm.assignCodingFormToSG(Input.codeFormName);
		// Session search to doc view Coding Form
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		docViewPage.selectPureHit();
		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch1(Input.testData1);
		sessionSearch.ViewInDocViews();
		driver.waitForPageToBeReady();
		docViewPage.editCodingForm(comment);
		docViewPage.codingFormSaveAndNext();
		reusableDocView.clickCodeSameAsLastAndVerifyNavigatedToNextDoc();
		driver.waitForPageToBeReady();
		reusableDocView.clickCodeSameAsLastAndVerifyNavigatedToNextDoc();
		baseClass.waitForElement(reusableDocView.getClickDocviewID(2));
		reusableDocView.getClickDocviewID(2).waitAndClick(10);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(reusableDocView.getDocument_CommentsTextBox());
		String Actual = reusableDocView.getDocument_CommentsTextBox().GetAttribute("value");
		softAssertion.assertEquals(comment, Actual);
		softAssertion.assertAll();
		baseClass.passedStep("Coding form is displayed as expected");
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date:11/01/22 Modified date: NA Modified by: Baskar
	 * @Description : Verify on saving of the stamp coding form should not clear for
	 *              the document
	 */
	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 22)
	public void validateAfterSavingStampObjectNotClear(String fullName, String userName, String password)
			throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-48731");
		baseClass.stepInfo("Verify on saving of the stamp coding form should not clear for the document");

		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		docViewPage = new DocViewPage(driver);

		String comment = "comment" + Utility.dynamicNameAppender();
		String stamp = "stampName" + Utility.dynamicNameAppender();
		String rmu = "RMU";

		loginPage.loginToSightLine(userName, password);

		if (fullName.contains(rmu)) {
//			 searching document for assignment creation
			sessionSearch.basicContentSearch(Input.searchString2);
			sessionSearch.bulkAssign();
			assignmentPage.assignmentCreation(assignFive, Input.codingFormName);
			assignmentPage.toggleCodingStampEnabled();
			assignmentPage.add2ReviewerAndDistribute();
			baseClass.impersonateRMUtoReviewer();
			System.out.println(assignFive);
		}
		// selecting the assignment as reviewer
		assignmentPage.SelectAssignmentByReviewer(assignFive);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// validation object should not clear
		docViewPage.objectShouldNotClearWhileSavingStamp(comment, stamp);

		driver.waitForPageToBeReady();
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 11/01/2022 Modified date: NA Modified by: Baskar
	 * @Description:Verify that for RMU/Reviewer coding stamps option should be
	 *                     displayed outside the context of an assignment
	 */
	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 23)
	public void ValidateCodingStampOption(String fullName, String userName, String password)
			throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-48800");
		baseClass.stepInfo("Verify that for RMU/Reviewer coding stamps "
				+ "option should be displayed outside the context of an assignment");
		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);

		// Login As
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rev1userName + "'");

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		docViewPage.selectPureHit();

		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch1(Input.testData1);

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		docViewPage.verifyCodingStampDisplay();

		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date:11/01/22 Modified date: NA Modified by: Baskar
	 * @Description : Verify that on click of the coding stamp non-audio document
	 *              should be completed which is viewed after the audio document.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 24)
	public void validateSavedStampFromNonAudio() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-51888");
		baseClass.stepInfo("Verify that on click of the coding stamp non-audio document "
				+ "should be completed which is viewed after the audio document.");

		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		docViewPage = new DocViewPage(driver);

		String comment = "comment" + Utility.dynamicNameAppender();
		String stamp = "stampName" + Utility.dynamicNameAppender();
		String rmu = "rmu";
		// login as rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		if (roll.contains(rmu)) {
//			 searching document for assignment creation
			sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
			docViewPage.selectPureHit();
			baseClass.stepInfo("Searching Content documents based on search string");
			sessionSearch.advancedNewContentSearch1(Input.testData1);
			baseClass.waitForElement(sessionSearch.getPureHitAddBtn());
			sessionSearch.getPureHitAddBtn().waitAndClick(5);
			sessionSearch.bulkAssign();
			assignmentPage.assignmentCreation(assignFour, Input.codingFormName);
			assignmentPage.toggleCodingStampEnabled();
			assignmentPage.assignmentDistributingToReviewer();
			System.out.println(assignFour);
		}
		loginPage.logout();
		// login as rev
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		// selecting the assignment as reviewer
		assignmentPage.SelectAssignmentByReviewer(assignFour);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// validating saved stamp from non-audio
		docViewPage.verifyNonAudioDocs(comment, stamp);

		driver.waitForPageToBeReady();
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 11/01/2022 Modified date: NA Modified by: Baskar
	 * @Description:Verify user can delete the stamp from edit coding stamp pop up
	 */
	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 25)
	public void validateDeletionOfStampFromUser(String fullName, String userName, String password)
			throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-48730");
		baseClass.stepInfo("Verify user can delete the stamp from edit coding stamp pop up");
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		docViewPage = new DocViewPage(driver);

		String comment = "comment" + Utility.dynamicNameAppender();
		String stamp = "stampName" + Utility.dynamicNameAppender();
		String rmu = "RMU";

		loginPage.loginToSightLine(userName, password);

		if (fullName.contains(rmu)) {
//			 searching document for assignment creation
			sessionSearch.basicContentSearch(Input.searchString2);
			sessionSearch.bulkAssign();
			assignmentPage.assignmentCreation(assignThree, Input.codingFormName);
			assignmentPage.toggleCodingStampEnabled();
			assignmentPage.add2ReviewerAndDistribute();
			baseClass.impersonateRMUtoReviewer();
			System.out.println(assignThree);
		}
		// selecting the assignment as reviewer
		assignmentPage.SelectAssignmentByReviewer(assignThree);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// Click on saved stamp to delete
		docViewPage.deleteSavedStampFromAssign(comment, stamp);

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 11/02/2021 Modified date: NA Modified by: Baskar
	 * @Description:Verify user can edit the coding stamp with the different color
	 */
	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 26)
	public void validateNotYetAssignedColourUser(String fullName, String userName, String password)
			throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		baseClass.stepInfo("Test case Id: RPMXCON-48729");
		baseClass.stepInfo("Verify user can edit the coding stamp with the different color");
		String comment = "comment" + Utility.dynamicNameAppender();
		String fieldText = "stamp" + Utility.dynamicNameAppender();
		String reStamp = "stamp" + Utility.dynamicNameAppender();
		String rmu = "RMU";

		loginPage.loginToSightLine(userName, password);

		if (fullName.contains(rmu)) {
//			 searching document for assignment creation
			sessionSearch.basicContentSearch(Input.searchString2);
			sessionSearch.bulkAssign();
			assignmentPage.assignmentCreation(assignOne, Input.codingFormName);
			assignmentPage.toggleCodingStampEnabled();
			assignmentPage.add2ReviewerAndDistribute();
			baseClass.impersonateRMUtoReviewer();
			System.out.println(assignOne);
		}
		// selecting the assignment as reviewer
		assignmentPage.SelectAssignmentByReviewer(assignOne);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		docViewPage.editCodingForm(comment);
		docViewPage.codingStampButton();
		docViewPage.popUpAction(fieldText, Input.stampSelection);
		baseClass.VerifySuccessMessage("Coding Stamp saved successfully");
		docViewPage.pencilGearicon(Input.stampSelection);
		baseClass.waitForElement(docViewPage.getDrp_CodingEditStampColour());
		docViewPage.getDrp_CodingEditStampColour().waitAndClick(5);
		boolean flag = docViewPage.getStampPopUpDrpDwnColur(Input.stampSelection).isDisplayed();
		softAssertion.assertTrue(flag);
		docViewPage.getEditAssignedColour(Input.stampColour).waitAndClick(5);
		docViewPage.getCodingEditStampTextBox().SendKeys(reStamp);
		baseClass.waitForElement(docViewPage.getCodingStampSaveBtn());
		docViewPage.getCodingStampSaveBtn().waitAndClick(5);
		baseClass.stepInfo("Coding stamp updated successfully");
		docViewPage.verifyingComments(comment);

		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 11/02/2021 Modified date: NA Modified by: Baskar
	 * @Description:Verify when RMU/Reviewer clicks Code this document the same as
	 *                     the last coded document, when immediately preceding
	 *                     document is completed by applying coding stamp
	 */
	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 27)
	public void validateSavedStampAfterClickSameAsLast(String fullName, String userName, String password)
			throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		baseClass.stepInfo("Test case Id: RPMXCON-48708");
		baseClass.stepInfo("Verify when RMU/Reviewer clicks Code this document the same as the last coded document,"
				+ " when immediately preceding document is completed by applying coding stamp");
		String comment = "comment" + Utility.dynamicNameAppender();
		String fieldText = "stamp" + Utility.dynamicNameAppender();
		String rmu = "RMU";

		loginPage.loginToSightLine(userName, password);

		if (fullName.contains(rmu)) {
//			 searching document for assignment creation
			sessionSearch.basicContentSearch(Input.searchString2);
			sessionSearch.bulkAssign();
			assignmentPage.assignmentCreation(assignTwo, Input.codingFormName);
			assignmentPage.toggleCodingStampEnabled();
			assignmentPage.add2ReviewerAndDistribute();
			baseClass.impersonateRMUtoReviewer();
			System.out.println(assignTwo);
		}
		// selecting the assignment as reviewer
		assignmentPage.SelectAssignmentByReviewer(assignTwo);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		docViewPage.verifySavedStampAfterSameAsLast(comment, fieldText);

		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date:11/02/22 Modified date: NA Modified by: Baskar
	 * @Description : Verify when user clicks the 'Code same as last' on when
	 *              navigating to document on document navigation options [<<, <, >,
	 *              >>, enter document number]
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 28)
	public void validateNavigateOptionUsingCodeSameAsLast() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52141");
		baseClass.stepInfo("Verify when user clicks the 'Code same as last' on when navigating "
				+ "to document on document navigation options [<<, <, >, >>, enter document number]");

		String comment = "comment" + Utility.dynamicNameAppender();
		String stamp = "stamp" + Utility.dynamicNameAppender();

		// Login As rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date:18/01/22 Modified date: NA Modified by: Baskar
	 * @Description : Verify after impersonation on click of 'Save' button coding
	 *              form should be validated in context of an assignment
	 */
	@Test(enabled = true, dataProvider = "rmuDauSauLogin", groups = { "regression" }, priority = 29)
	public void validateAfterImpersonateCodingForm(String fullName, String userName, String password,
			String impersonate) throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		codingForm = new CodingForm(driver);
		assignmentPage = new AssignmentsPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-51191");
		baseClass.stepInfo("Verify after impersonation on click of 'Save' "
				+ "button coding form should be validated in context of an assignment");

		String cf = "cfName" + Utility.dynamicNameAppender();
		String rmu = "RMU";

		loginPage.loginToSightLine(userName, password);
		// Login As
		if (fullName.contains(rmu)) {
//			 searching document for assignment creation
			codingForm.commentRequired(cf);
			sessionSearch.basicContentSearch(Input.searchString2);
			sessionSearch.bulkAssign();
			assignmentPage.assignmentCreation(assign, cf);
			System.out.println(assign);
			assignmentPage.toggleCodingStampEnabled();
			assignmentPage.addReviewerAndDistributeDaPa();
		}
		if (fullName.equalsIgnoreCase("pa") || fullName.equalsIgnoreCase("sa") || fullName.equalsIgnoreCase("RMU")) {
			baseClass.credentialsToImpersonateAsRMUREV(fullName, impersonate);
			docViewPage.selectAssignmentfromDashborad(assign);
			docViewPage.validateWithoutEditUsingSave();
		}

		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 18/01/2021 Modified date: NA Modified by: Baskar
	 * @Description:Coding Form Child Window: Verify coding form objects should be
	 *                     displayed on edit coding stamp in context of security
	 *                     group
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 30)
	public void validateFromSGCodingStampViewCoding() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-52053");
		baseClass.stepInfo("Coding Form Child Window: Verify coding form objects should be "
				+ "displayed on edit coding stamp in context of security group");
		String comment = "comment" + Utility.dynamicNameAppender();
		String fieldText = "stamp" + Utility.dynamicNameAppender();

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		codingForm.assignCodingFormToSG("Default Project Coding Form");

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		docViewPage.selectPureHit();
		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch1(Input.testData1);
		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		// stamp saving as Prerequisites
		docViewPage.editCodingForm(comment);
		docViewPage.codingStampButton();
		docViewPage.popUpAction(fieldText, Input.stampSelection);
		// opening child window(Coding form)
		baseClass.stepInfo("performing action in coding form child window");
		docViewPage.clickGearIconOpenCodingFormChildWindow();
		docViewPage.switchToNewWindow(2);
		driver.waitForPageToBeReady();
		docViewPage.pencilGearIconCF(Input.stampSelection);
		docViewPage.closeWindow(1);
		docViewPage.switchToNewWindow(1);
		driver.waitForPageToBeReady();
		if (docViewPage.getCodingStampPopUpColurVerify(Input.stampSelection).isDisplayed()) {
			baseClass.passedStep("Coding stamp applied colour displayed in popup");
		} else {
			baseClass.failedStep("Coding stamp applied colour not displayed in popup");
		}
		docViewPage.clickViewCodingButton();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocumentsCommentViewCoding());
		docViewPage.getDocumentsCommentViewCoding().ScrollTo();
		String actual = docViewPage.getDocumentsCommentViewCoding().getText();
		softAssertion.assertEquals(comment, actual);
		baseClass.stepInfo("verify viewcodingstamp popup saved value is successfully displayed ");
		docViewPage.getViewCodingCloseButton().waitAndClick(5);
		baseClass.waitForElement(docViewPage.getDeletePopUpAssignedColour());
		docViewPage.getDeletePopUpAssignedColour().waitAndClick(10);
		softAssertion.assertAll();
		// logout
		loginPage.logout();
	}

	/**
	 * Author : Baskar date: NA Modified date:19/01/2021 Modified by: Baskar
	 * Description : Verify that read only custom metadata field value should be
	 * retained on click of saved stamp in context of security group
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 31)
	public void verifyMeataDataUsingSavedStamp() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52183");
		baseClass.stepInfo("Verify that read only custom metadata field value should be "
				+ "retained on click of saved stamp in context of security group");
		UtilityLog.info("Started Execution for prerequisite");

		String comment = "comment" + Utility.dynamicNameAppender();
		String fieldText = "stamp" + Utility.dynamicNameAppender();
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");

		// Custom Field created with INT DataType
		projectPage.addCustomFieldProjectDataType(projectFieldINT, "INT");
		baseClass.stepInfo("Custom meta data field created with INT datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(projectFieldINT);
		baseClass.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as Rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Creating Coding Form
		codingForm.addProjectFieldMetaData(Input.codingFormName, projectFieldINT);
		baseClass.stepInfo("Project field added to coding form in Doc view");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.rmu1userName + "'");

		// login as Rev
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		// verify the coding form panel
		docViewPage.metaDataUsingSavedStamp(comment, fieldText, projectFieldINT);

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager'" + Input.rmu1userName + "'");

	}

	/**
	 * @Author : Baskar date:18/01/22 Modified date: NA Modified by: Baskar
	 * @Description : [TC reference RPMXCON-51191] Verify after impersonation on
	 *              click of 'Save and Next' button coding form should be validated
	 *              in context of security group
	 */
	@Test(enabled = true, dataProvider = "impersoante", groups = { "regression" }, priority = 32)
	public void validateAfterImpersonateCodingFormInSG(String roll, String userName, String password,
			String impersonate) throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		codingForm = new CodingForm(driver);
		assignmentPage = new AssignmentsPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-52075");
		baseClass.stepInfo("[TC reference RPMXCON-51191] Verify after impersonation on click of "
				+ "'Save and Next' button coding form should be validated in context of security group");

		String cf = "cfName" + Utility.dynamicNameAppender();
		String rmu = "RMU";

		loginPage.loginToSightLine(userName, password);
		// Login As
		if (roll.equalsIgnoreCase(rmu)) {
//			 searching document for assignment creation
			codingForm.commentRequired(cf);
			codingForm.assignCodingFormToSG(cf);
		}
		if (roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rmu")
				|| roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rev")
				|| roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rev")
				|| roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rmu")
				|| roll.equalsIgnoreCase("da") && impersonate.equalsIgnoreCase("rmu")
				|| roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rev")
				|| roll.equalsIgnoreCase("rmu") && impersonate.equalsIgnoreCase("rmu")) {
			baseClass.credentialsToImpersonateAsRMUREV(roll, impersonate);
			sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
			docViewPage.selectPureHit();
			sessionSearch.advancedNewContentSearch1(Input.testData1);
			sessionSearch.ViewInDocViews();
			docViewPage.verifyAfterImpersoanteAudioNonAudioDocs();
		}
		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Gopinath Created date: NA Modified date: NA Modified by:NA
	 * @Description : To verify that comment should not be displayed on document, if
	 *              document is in two different security group and comment is in
	 *              one security group
	 */
	@Test(enabled = true, alwaysRun = false, groups = { "regression" }, priority = 33)
	public void verifyCommentTextFieldIsNotAppearedAtDiffSecurityGroup() throws Exception {
		String AnnotationLayerNew = Input.randomText + Utility.dynamicNameAppender();
		String namesg2 = Input.randomText + Utility.dynamicNameAppender();
		String namesg3 = Input.randomText + Utility.dynamicNameAppender();
		String codingForm = Input.randomText + Utility.dynamicNameAppender();
		String codingForm2 = Input.randomText + Utility.dynamicNameAppender();
		String action = "Make It Required";
		String comments = "comment";
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51005");
		loginPage = new LoginPage(driver);

		baseClass.stepInfo("Login with project administrator");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		Reporter.log("Logged in as User: " + Input.pa1userName);
		baseClass.stepInfo(
				"#### To verify that comment should not be displayed on document, if document is in two different security group and comment is in one security group ####");

		// creating two new security groups and adding annotation layer
		SecurityGroupsPage securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(namesg2);
		baseClass.CloseSuccessMsgpopup();
		driver.scrollPageToTop();
		securityGroupsPage.AddSecurityGroup(namesg3);

		// Creating annotation layer and assigning to newly created SGs
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		docViewRedact.createNewAnnotationLayer(AnnotationLayerNew);

		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnAnnotationLinkAndSelectAnnotation(AnnotationLayerNew);
		baseClass.CloseSuccessMsgpopup();

		securityGroupsPage.selectSecurityGroup(namesg2);
		securityGroupsPage.clickOnReductionTagAndSelectReduction(Input.defaultRedactionTag);
		baseClass.CloseSuccessMsgpopup();

		baseClass.stepInfo("Add comment to security group");
		securityGroupsPage.addCommentToSecurityGroup(namesg2, Input.documentComments);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		securityGroupsPage.addCommentToSecurityGroup(namesg3, Input.documentComments);

		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);

		sessionsearch.bulkRelease(namesg2);
		sessionsearch.bulkRelease(namesg3);
		driver.waitForPageToBeReady();

		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rmu1userName);
		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rev1userName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		CodingForm coding = new CodingForm(driver);
		// Switch To SG1
		docViewRedact.selectsecuritygroup(namesg2);
		coding.navigateToCodingFormPage();
		baseClass.stepInfo("Add coding form with comment");
		coding.addCodingFormWithCommentValidation(codingForm, "", Input.documentComments, comments, "", action);

		coding.navigateToCodingFormPage();
		baseClass.stepInfo("Coding form to security group");
		coding.CodingformToSecurityGroup(codingForm);

		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();
		DocViewPage docView = new DocViewPage(driver);
		docView.navigateToDocViewPageURL();
		driver.waitForPageToBeReady();
		docView.fillingTheDocviewCommentsSection(Input.comment);

		docViewRedact.selectsecuritygroup(namesg3);
		coding.navigateToCodingFormPage();
		baseClass.stepInfo("Add coding form with comment");
		coding.addCodingFormWithCommentValidation(codingForm2, "", Input.documentComments, comments, "", action);
		coding.navigateToCodingFormPage();
		baseClass.stepInfo("Coding form to security group");
		coding.CodingformToSecurityGroup(codingForm2);
		docViewRedact.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manger '" + Input.rmu1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		docViewRedact.selectsecuritygroup(namesg3);
		baseClass.stepInfo("Navigating to docview from session search");
		sessionsearch.navigateToSessionSearchPageURL();
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.addDocsMetCriteriaToActionBoard();

		baseClass.stepInfo("Verify comment inner text area field inner text.");
		docView.verifyCommentTextFieldInnerText(comments);
		docViewRedact.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		docViewRedact = new DocViewRedactions(driver);
		baseClass.stepInfo("Select security group for RMU");
		docViewRedact.selectsecuritygroup("Default Security Group");
		baseClass.stepInfo("RMU2 Assigned to Default Security Group");
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Select security group for REVIEWER");
		docViewRedact.selectsecuritygroup("Default Security Group");
		baseClass.stepInfo("Reviewer Assigned to Default Security Group");
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.deleteSecurityGroups(namesg2);
		securityGroupsPage.deleteSecurityGroups(namesg3);
		driver.Navigate().refresh();
		driver.scrollPageToTop();
		loginPage.logout();
		loginPage.quitBrowser();
		LoginPage.clearBrowserCache();
	}

	/**
	 * Author : Baskar date: NA Modified date:24/01/2021 Modified by: Baskar
	 * Description : Verify comment, metadata should be indexed for the document
	 * when user applies coding stamp to the document in context of an assignment
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 34)
	public void validateCodingStampPureHitCont() throws InterruptedException, AWTException {
		projectPage = new ProjectPage(driver);
		securityGroupPage = new SecurityGroupsPage(driver);
		commentsPage = new CommentsPage(driver);
		docViewPage = new DocViewPage(driver);
		codingForm = new CodingForm(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-51150");
		baseClass.stepInfo("Verify comment, metadata should be indexed for "
				+ "the document when user applies coding stamp to the document in context of an assignment");
		UtilityLog.info("Started Execution for prerequisite");
		String assignName = "AAsign" + Utility.dynamicNameAppender();
		String addComment = "addomment" + Utility.dynamicNameAppender();
		String cfName = "coding" + Utility.dynamicNameAppender();
		String commentText = "ct" + Utility.dynamicNameAppender();
		String metadataText = "mt" + Utility.dynamicNameAppender();
		String stamp = "stamp" + Utility.dynamicNameAppender();

		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");

		// Custom Field created with INT DataType
		projectPage.addCustomFieldProjectDataType(projectFieldINT, "NVARCHAR");
		baseClass.stepInfo("Custom meta data field created with INT datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(projectFieldINT);
		baseClass.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		commentsPage.AddComments(addComment);

		// Creating Coding Form
		codingForm.createCommentAndMetadata(projectFieldINT, addComment, cfName);
		baseClass.stepInfo("Project field added to coding form in Doc view");

		// searching document for assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignName, cfName);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.assignmentDistributingToReviewer();

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager'" + Input.rmu1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer '" + Input.rev1userName + "'");

		// selecting the assignment
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// verify the coding form panel
		int size = docViewPage.verifyCommentAndMetaDataUsingCodingStamp(addComment, commentText, projectFieldINT,
				metadataText, stamp);
		baseClass.stepInfo("Checking index of comment and metadata for saved document");
		int pureHit = sessionSearch.metadataAndCommentSearch(projectFieldINT, metadataText, addComment, commentText);

		softAssertion.assertEquals(size, pureHit);
		softAssertion.assertAll();

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");
	}

	/**
	 * @Author : Baskar date: 24/01/2021 Modified date: NA Modified by: Baskar
	 * @Description:To verify that user can complete the document.
	 */
	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 35)
	public void validateUserCanCompleteDocsInAssgn(String fullName, String userName, String password)
			throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		baseClass.stepInfo("Test case Id: RPMXCON-50983");
		baseClass.stepInfo("To verify that user can complete the document.");
		String comment = "comment" + Utility.dynamicNameAppender();
		String rmu = "RMU";

		loginPage.loginToSightLine(userName, password);

		if (fullName.contains(rmu)) {
//			 searching document for assignment creation
			sessionSearch.basicContentSearch(Input.searchString2);
			sessionSearch.bulkAssign();
			assignmentPage.assignmentCreation(assgn, Input.codingFormName);
			assignmentPage.toggleCodingStampEnabled();
			assignmentPage.add2ReviewerAndDistribute();
			baseClass.impersonateRMUtoReviewer();
			System.out.println(assgn);
		}
		// selecting the assignment as reviewer
		assignmentPage.SelectAssignmentByReviewer(assgn);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		docViewPage.verifyUserDocsCompleteBtn(comment);

		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 25/02/2022 Modified date: NA Modified by: Baskar
	 * @Description:Verify coding form objects should be displayed on edit coding
	 *                     stamp
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 36)
	public void validateViewCodingPopUp() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-51262");
		baseClass.stepInfo("Verify coding form objects should be displayed on edit coding stamp");
		String assign = "AAssgn" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();
		String fieldText = "stamp" + Utility.dynamicNameAppender();

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Create New Assignment
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assign, Input.codingFormName);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.assignmentDistributingToReviewer();

		// logout
		loginPage.logout();

		// login as reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting assignment by reviewer
		assignmentPage.SelectAssignmentByReviewer(assign);
		docViewPage.editCodingForm(comment);
		docViewPage.codingStampButton();
		docViewPage.popUpAction(fieldText, Input.stampSelection);
		docViewPage.pencilGearicon(Input.stampSelection);
		boolean EditStamp = docViewPage.getEditCodingStamp_PopUpWindow().Displayed();
		softAssertion.assertTrue(EditStamp);
		docViewPage.clickViewCodingButton();
		driver.waitForPageToBeReady();

		// validation from view coding stamp popup window
		boolean viewCoding = docViewPage.getViewCodingStamp_PopUpWindow().Displayed();
		softAssertion.assertTrue(viewCoding);
		baseClass.stepInfo("ViewCoding stamp popup window opened");
		baseClass.waitForElement(docViewPage.getDocumentsCommentViewCoding());
		docViewPage.getDocumentsCommentViewCoding().ScrollTo();
		String actual = docViewPage.getDocumentsCommentViewCoding().getText();
		softAssertion.assertEquals(comment, actual);
		baseClass.passedStep("While View coding stamp window stamp saved value loaded successfully");
		softAssertion.assertAll();

		// logout
		loginPage.logout();
	}

	/*
	 * @Author : Baskar date:28/01/22 Modified date: NA Modified by: Baskar
	 * 
	 * @Description : To verify that user can edit the custom coding form, and mark
	 * the document as 'Save'.
	 */

	@Test(enabled = true, dataProvider = "rmuRevRmuLogin", groups = { "regression" }, priority = 37)
	public void validateCustomCodingFormUsingSave(String roll, String userName, String password)
			throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-50981");
		baseClass.stepInfo("To verify that user can edit the custom coding form, and mark the document as 'Save'.");

		codingForm = new CodingForm(driver);
		sessionSearch = new SessionSearch(driver);
		tagsAndFoldersPage = new TagsAndFoldersPage(driver);
		docViewPage = new DocViewPage(driver);
		softAssertion = new SoftAssert();

		String comment = "comment" + Utility.dynamicNameAppender();
		String cf = "customCf" + Utility.dynamicNameAppender();

		// login as RMU
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		if (roll == "rmu") {
			// Creating Coding Form
			codingForm.commentRequired(cf);
			// Assign to security group
			codingForm.assignCodingFormToSG(cf);
			System.out.println(cf);
			baseClass.stepInfo("Coding form assigned to security group");
			baseClass.stepInfo("Customized coding form created successfully");
		}
		if (roll == "rmu" || roll == "rev") {
			// Session search to docview
			sessionSearch.basicContentSearch(Input.searchString1);
			sessionSearch.ViewInDocView();
			baseClass.stepInfo("User landed on the docview page");

			// validation customized coding form
			driver.waitForPageToBeReady();
			baseClass.waitForElement(docViewPage.getDocument_CommentsTextBox());
			docViewPage.getDocument_CommentsTextBox().SendKeys(comment);
			docViewPage.codingFormSaveButton();
			baseClass.VerifySuccessMessage("Document saved successfully");
			baseClass.stepInfo("Performing action in Child window");
			docViewPage.clickGearIconOpenCodingFormChildWindow();
			String parent = docViewPage.switchTochildWindow();
			baseClass.waitForElement(docViewPage.getDocument_CommentsTextBox());
			docViewPage.getDocument_CommentsTextBox().SendKeys(comment);
			docViewPage.codingFormSaveButton();
			docViewPage.childWindowToParentWindowSwitching(parent);
			baseClass.VerifySuccessMessage("Document saved successfully");
		}
		if (roll == "assgnCf") {
			codingForm.assignCodingFormToSG(Input.codingFormName);
		}

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Raghuram A date: 31/01/22 Modified date: NA Modified by: N/A
	 * @Description : Verify alert is prompted to the user if user edit any part of
	 *              coding form and navigates to other document from mini doc list
	 *              without saving - RPMXCON-50974
	 */

	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 38)
	public void editCodingNavigationCheck(String roll, String userName, String password)
			throws InterruptedException, AWTException {
		MiniDocListPage miniDocListpage = new MiniDocListPage(driver);
		String firnstDocname, secondDocname, docName;
		String comments = "Comment-" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id: RPMXCON-50974 Sprint 11");
		baseClass.stepInfo(
				"Verify alert is prompted to the user if user edit any part of coding form and navigates to other document from mini doc list without saving");

		// Login in as User
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Logged in as : " + userName);

		// Perform search and View in DocView
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();

		// Main method
		baseClass.waitForElement(docViewPage.getClickDocviewID(2));
		docViewPage.getClickDocviewID(2).waitAndClick(5);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getVerifyPrincipalDocument());
		firnstDocname = docViewPage.getVerifyPrincipalDocument().getText();
		System.out.println("Current Document Viewed : " + firnstDocname);
		baseClass.stepInfo("Current Document Viewed : " + firnstDocname);

		// Edit comments
		docViewPage.getAddComment1().Clear();
		docViewPage.getAddComment1().SendKeys(comments);
		baseClass.stepInfo("Coding form input : " + comments);

		// Switch to a different document
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getClickDocviewID(5));
		docViewPage.getClickDocviewID(5).waitAndClick(5);

		// Confirmation message "NO" flow
		baseClass.stepInfo("NO FLow : Navigation should not be done");
		baseClass.waitForElement(docViewPage.getNavigationMsgPopupNoBtn());
		docViewPage.getNavigationMsgPopupNoBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(miniDocListpage.getMainWindowActiveDocID());
		docName = miniDocListpage.getMainWindowActiveDocID().getText();
		System.out.println("Baskar:" + docName);
		baseClass.stepInfo("Current Document : " + docName);
		baseClass.textCompareEquals(firnstDocname, docName, "Document not navigated", "Document navigated");
		baseClass.waitForElement(docViewPage.getAudioComment());
		comments = docViewPage.getAudioComment().GetAttribute("value");
		baseClass.stepInfo("Coding form input : " + comments);

		// Switch to a different document
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getClickDocviewID(11));
		docViewPage.getClickDocviewID(11).waitAndClick(5);
		if (docViewPage.getNavigationMsg().isElementAvailable(5)) {
			baseClass.stepInfo(
					"Alert prompted to the user that their edits will not be saved and giving the user confirmation message with Yes/No buttons  ");
		}

		// Confirmation message "YES" flow
		baseClass.stepInfo(
				"YES FLow : Edits should not be saved and clicked document from the mini doc list should be loaded.   ");
		baseClass.waitForElement(docViewPage.getNavigationMsgPopupYesBtnD());
		docViewPage.getNavigationMsgPopupYesBtnD().waitAndClick(5);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(miniDocListpage.getMainWindowActiveDocID());
		secondDocname = miniDocListpage.getMainWindowActiveDocID().getText();
		System.out.println("Switched Document : " + secondDocname);
		baseClass.stepInfo("Switched Document : " + secondDocname);
		baseClass.textCompareNotEquals(secondDocname, docName, "Document switched", "Remains on the same document");

		// Back to Initial Document
		baseClass.stepInfo("Back to Initial document");
		driver.waitForPageToBeReady();
		baseClass.waitTime(3);
		baseClass.waitForElement(miniDocListpage.getDociD(docName));
		driver.scrollingToElementofAPage(miniDocListpage.getDociD(docName));
		miniDocListpage.getDociD(docName).waitAndClick(5);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(miniDocListpage.getMainWindowActiveDocID());
		docName = miniDocListpage.getMainWindowActiveDocID().getText();
		System.out.println("Current Document Viewed : " + docName);
		baseClass.stepInfo("Current Document Viewed : " + docName);

		// Edits should not be saved
		baseClass.waitForElement(docViewPage.getAudioComment());
		String retainedInput = docViewPage.getAudioComment().GetAttribute("value");
		baseClass.stepInfo("Retained Input : " + retainedInput);
		baseClass.textCompareNotEquals(comments, retainedInput, "Edited input not saved", "Input saved");

		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 01/02/2022 Modified date: NA Modified by: Baskar
	 * @Description:To verify that Project Admin cannot view the coding form.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 39)
	public void validateCodingFormInPaUser() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		baseClass.stepInfo("Test case Id: RPMXCON-50927");
		baseClass.stepInfo("To verify that Project Admin cannot view the coding form.");

		// Login as Rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Assign coding form
		codingForm.assignCodingFormToSG(Input.codingFormName);
		baseClass.passedStep(Input.codingFormName + "Coding form assigned to context of security group");

		// logout
		loginPage.logout();

		// Login as pa
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		// navigation to docview page from session search page
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();

		// validation for pa user in coding form
		docViewPage.validateCodingFormDisplayAsPaUser(Input.codingFormName);
		docViewPage.paWarningMsgForCodingForm();

		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 01/02/2022 Modified date: NA Modified by: Baskar
	 * @Description:To verify that user can view the coding form if it is Assigned
	 *                 to the assignment.
	 */
	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 40)
	public void validateAssignedCfInAssignment(String fullName, String userName, String password)
			throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		codingForm = new CodingForm(driver);
		softAssertion = new SoftAssert();
		baseClass.stepInfo("Test case Id: RPMXCON-50926");
		baseClass.stepInfo("To verify that user can view the coding form if it is Assigned to the assignment.");
		String rmu = "RMU";

		loginPage.loginToSightLine(userName, password);

		if (fullName.contains(rmu)) {
			// New Coding form creation
			codingForm.commentRequired(cfName);
//			 searching document for assignment creation
			sessionSearch.basicContentSearch(Input.searchString2);
			sessionSearch.bulkAssign();
			assignmentPage.assignmentCreation(assgnCf, cfName);
			assignmentPage.toggleCodingStampEnabled();
			assignmentPage.add2ReviewerAndDistribute();
			baseClass.impersonateRMUtoReviewer();
			System.out.println(assgnCf);
		}
		// selecting the assignment as reviewer
		assignmentPage.SelectAssignmentByReviewer(assgnCf);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// validation for assigned coding form
		docViewPage.verifyCodingFormName(cfName);
		baseClass.passedStep("Coding form displayed on right side of the panel");

		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 03/02/2022 Modified date: NA Modified by: Baskar
	 * @Description:Performance: Verify that doc view should not hang when large
	 *                           coding form is assigned to security group
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 41)
	public void validateCodingFormInPaUssser() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		codingForm = new CodingForm(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51457");
		baseClass.stepInfo("Performance: Verify that doc view should not hang when "
				+ "large coding form is assigned to security group");
		String cfLarge = "CfNameLarge" + Utility.dynamicNameAppender();
		// Login as Rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// creating long coding form
		codingForm.codingFormLarge(cfLarge);
		codingForm.assignCodingFormToSG(cfLarge);

		// navigation to docview page from session search page
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("User on the docview page from session search");
		driver.waitForPageToBeReady();

		// validation for large coding form in context of security group
		docViewPage.docviewPageLoadPerformanceForCF();

		// assign default project coding form
		codingForm.assignCodingFormToSGAlert(Input.codingFormName);

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 03/02/2022 Modified date: NA Modified by: Baskar
	 * @Description:Performance: Verify that doc view should not hang when large
	 *                           coding form is assigned to assignment
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 42)
	public void validatingLargeCfFromAssgn() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		codingForm = new CodingForm(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51456");
		baseClass.stepInfo("Performance: Verify that doc view should not hang "
				+ "when large coding form is assigned to assignment");

		String assignment = "AssCFLarge" + Utility.dynamicNameAppender();
		String cfLarge = "CfNameLarge" + Utility.dynamicNameAppender();

		// login as rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// assign default project coding form
		// creating long coding form
		codingForm.codingFormLarge(cfLarge);

//	    searching document for assignment creation and new coding form created (existing)
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignment, cfLarge);
		assignmentPage.add2ReviewerAndDistribute();
		System.out.println(assignment);

		// logout
		loginPage.logout();

		// login as rev
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting the assignment as reviewer
		assignmentPage.SelectAssignmentByReviewer(assignment);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// validation for large coding form in context of security group
		docViewPage.docviewPageLoadPerformanceForCF();

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 04/02/2022 Modified date: NA Modified by: Baskar
	 * @Description:Verify waning message is prompted to the user on
	 *                     undocking/docking after impersonation when user navigates
	 *                     away from the page without completing or saving from doc
	 *                     view
	 */
	@Test(enabled = true, dataProvider = "threeUser", groups = { "regression" }, priority = 43)
	public void validatePopUpMsgUsingAllOption(String roll, String userName, String password, String impersonate)
			throws InterruptedException {
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		codingForm = new CodingForm(driver);
		miniDocListPage = new MiniDocListPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-50925");
		baseClass.stepInfo("Verify waning message is prompted to the user on undocking/docking after "
				+ "impersonation when user navigates away from the page without completing or saving from doc view");
		String assignmentNameToManual = null;
		// Login as Reviewer Manager
		loginPage.loginToSightLine(userName, password);

		if (roll.equalsIgnoreCase("rmu")) {
			// search to Assignment creation
			sessionSearch.basicContentSearch(Input.searchString1);
			sessionSearch.bulkAssignNearDupeDocuments();
			assignmentNameToManual = miniDocListPage.assignmentCreationWithManualSortForDisToRMUAndRe();
			System.out.println("assignmentNameToChoose");
			baseClass.stepInfo("Created Assignment " + assignmentNameToManual);
		}
		switch (impersonate) {
		case "rmu":
			if (roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rmu")) {
				driver.waitForPageToBeReady();
				baseClass.impersonateSAtoRMU();
			}
			if (roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rmu")) {
				driver.waitForPageToBeReady();
				baseClass.impersonatePAtoRMU();
			}

		case "rev":
			if (roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rev")) {
				driver.waitForPageToBeReady();
				baseClass.impersonateSAtoReviewer();
			}
			if (roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rev")) {
				driver.waitForPageToBeReady();
				baseClass.impersonatePAtoReviewer();
			}
			if (roll.equalsIgnoreCase("rmu")) {
				driver.waitForPageToBeReady();
				baseClass.impersonateRMUtoReviewer();
			}
		}

		if (roll.equalsIgnoreCase("rmu")) {
			assignmentPage.SelectAssignmentByReviewer(assignmentNameToManual);
			baseClass.stepInfo("User on the doc view after selecting the assignment");
			docViewPage.popUpMessageUsingAllOption();
			docViewPage.popUpValidationDoneFromChildWindow();
		} else if (roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rmu")
				|| roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rev")
				|| roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rev")
				|| roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rmu")) {
			driver.waitForPageToBeReady();
			sessionSearch.basicContentSearch(Input.searchString1);
			sessionSearch.ViewNearDupeDocumentsInDocView();
			docViewPage.popUpMessageUsingAllOption();
			docViewPage.popUpValidationDoneFromChildWindow();
		}

		driver.waitForPageToBeReady();
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 04/02/2022 Modified date: NA Modified by: Baskar
	 * @Description:Verify waning message is prompted to the user on browser
	 *                     back/refresh after impersonation when user navigates away
	 *                     from the page without completing or saving from doc view
	 */
	@Test(enabled = true, dataProvider = "threeUser", groups = { "regression" }, priority = 44)
	public void validatePopUpMsgUsingBackAndRefresh(String roll, String userName, String password, String impersonate)
			throws InterruptedException {
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		codingForm = new CodingForm(driver);
		miniDocListPage = new MiniDocListPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-50924");
		baseClass.stepInfo("Verify waning message is prompted to the user on browser back/refresh after"
				+ " impersonation when user navigates away from the page without completing or saving from doc view");
		String assignmentNameToManual = null;
		// Login as Reviewer Manager
		loginPage.loginToSightLine(userName, password);

		if (roll.equalsIgnoreCase("rmu")) {
			// search to Assignment creation
			sessionSearch.basicContentSearch(Input.searchString1);
			sessionSearch.bulkAssignNearDupeDocuments();
			assignmentNameToManual = miniDocListPage.assignmentCreationWithManualSortForDisToRMUAndRe();
			System.out.println("assignmentNameToChoose");
			baseClass.stepInfo("Created Assignment " + assignmentNameToManual);
		}
		switch (impersonate) {
		case "rmu":
			if (roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rmu")) {
				driver.waitForPageToBeReady();
				baseClass.impersonateSAtoRMU();
			}
			if (roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rmu")) {
				driver.waitForPageToBeReady();
				baseClass.impersonatePAtoRMU();
			}

		case "rev":
			if (roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rev")) {
				driver.waitForPageToBeReady();
				baseClass.impersonateSAtoReviewer();
			}
			if (roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rev")) {
				driver.waitForPageToBeReady();
				baseClass.impersonatePAtoReviewer();
			}
			if (roll.equalsIgnoreCase("rmu")) {
				driver.waitForPageToBeReady();
				baseClass.impersonateRMUtoReviewer();
			}
		}

		if (roll.equalsIgnoreCase("rmu")) {
			assignmentPage.SelectAssignmentByReviewer(assignmentNameToManual);
			baseClass.stepInfo("User on the doc view after selecting the assignment");
			docViewPage.popUpMessageUsingAllOption();
		} else if (roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rmu")
				|| roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rev")
				|| roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rev")
				|| roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rmu")) {
			driver.waitForPageToBeReady();
			sessionSearch.basicContentSearch(Input.searchString1);
			sessionSearch.ViewNearDupeDocumentsInDocView();
			docViewPage.popUpMessageUsingAllOption();
		}

		driver.waitForPageToBeReady();
		loginPage.logout();

	}

	/**
	 * @Author : Brundha
	 * @Description:To verify that message should be displayed if no coding form is
	 *                 available for principal document and user select action as
	 *                 'Code Same as this'.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 45)
	public void validateCodeSameWhenNoCf() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		codingForm = new CodingForm(driver);
		softAssertion = new SoftAssert();
		baseClass.stepInfo("Test case Id: RPMXCON-50942");
		baseClass.stepInfo("To verify that message should be displayed if no coding form is"
				+ " available for principal document and user select action as 'Code Same as this'.");
		String cf = "cf" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Removing coding from for sg
		codingForm.commentRequired(cf);
		codingForm.assignCodingFormToSG(cf);
		codingForm.deleteCodingForm(cf, cf);

		// navigation to docview page from session search page
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewNearDupeDocumentsInDocView();

		// valiadte no coding form presence
		driver.waitForPageToBeReady();
		boolean flag = docViewPage.getNoDefaultCodingForm().Displayed();
		softAssertion.assertTrue(flag);
		baseClass.stepInfo("No coding available in context of security group");
		baseClass.waitForElement(docViewPage.getDocView_Analytics_NearDupeTab());
		docViewPage.getDocView_Analytics_NearDupeTab().waitAndClick(10);
		baseClass.waitForElement(docViewPage.getDocView_Analytics_NearDupe_Doc(1));
		docViewPage.getDocView_Analytics_NearDupe_Doc(1).waitAndClick(5);
		baseClass.waitForElement(docViewPage.getDocView_ChildWindow_ActionButton());
		docViewPage.getDocView_ChildWindow_ActionButton().waitAndClick(15);
		baseClass.waitForElement(docViewPage.getCodeSameAsNearDupe());
		docViewPage.getCodeSameAsNearDupe().waitAndClick(15);
		baseClass.VerifySuccessMessage("Code same performed successfully.");
		softAssertion.assertAll();
		loginPage.logout();
	}

	/**
	 * @Author : Brundha
	 * @Description:RPMXCON-51574 -Verify that when coding stamp is created/saved
	 *                            using the coding of a completed document, then on
	 *                            mouse hover tool tip should be displayed for the
	 *                            stamp icon in coding form child window.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 46)
	public void validateToolTipInDocViewCodingFormChildWindow() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		reusableDocView = new ReusableDocViewPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51574-DocView/Codingform");
		baseClass.stepInfo(
				"Verify that when coding stamp is created/saved using the coding of a completed document, then on mouse hover tool tip should be displayed for the stamp icon in coding form child window.");

		String comment = "comment" + Utility.dynamicNameAppender();
		String fieldText = "stamp" + Utility.dynamicNameAppender();
		String assign = "AAsign" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// searching document for assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assign, Input.codingFormName);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.add2ReviewerAndDistribute();

		// logout
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		// selecting the assignment as reviewer
		assignmentPage.SelectAssignmentByReviewer(assign);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// opening coding form child window
		driver.waitForPageToBeReady();

		String prndoc = docViewPage.getVerifyPrincipalDocument().getText();
		docViewPage.clickGearIconOpenCodingFormChildWindow();
		docViewPage.switchToNewWindow(2);
		docViewPage.editCodingForm(comment);
		docViewPage.completeButton();
		baseClass.stepInfo("Document completed successfully");

		// viewing the completed document again
		docViewPage.switchToNewWindow(1);
		baseClass.waitForElement(docViewPage.getDociD(prndoc));
		docViewPage.getDociD(prndoc).waitAndClick(5);
		docViewPage.switchToNewWindow(2);

		// saving the stamp as per completed docs
		docViewPage.codingStampButton();
		docViewPage.switchToNewWindow(1);
		docViewPage.popUpAction(fieldText, Input.stampSelection);
		baseClass.stepInfo("coding stamp saved successfully");

		// mouse over action over the tool tip
		docViewPage.switchToNewWindow(2);
		if (docViewPage.getCodingStampLastIcon(Input.stampSelection).Displayed()) {
			Actions builder = new Actions(driver.getWebDriver());
			driver.waitForPageToBeReady();
			baseClass.waitForElement(docViewPage.getCodingStampLastIcon(Input.stampSelection));

			builder.moveToElement(docViewPage.getCodingStampLastIcon(Input.stampSelection).getWebElement()).build()
					.perform();
			driver.waitForPageToBeReady();
			String ActualText = docViewPage.getSavedCodingStamp(Input.stampSelection).getWebElement()
					.getAttribute("title");
			baseClass.textCompareEquals(ActualText, fieldText, "Mouseover Text is displayed as expected",
					"Mouseover text is not displayed as expected");
		} else {
			baseClass.failedStep("Save this coding form as a new stamp not displayed");
		}
		docViewPage.closeWindow(1);
		docViewPage.switchToNewWindow(1);

		// overall assertion
		softAssertion.assertAll();
		loginPage.logout();

	}

	/**
	 * @Author : Brundha
	 * @Description:RPMXCON-51573 -Verify that when coding stamp is created/saved
	 *                            using the coding of a completed document, then on
	 *                            mouse hover tool tip should be displayed for the
	 *                            stamp icon.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 47)
	public void validateToolTipInDocViewCodingForm() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		reusableDocView = new ReusableDocViewPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51573-DocView/CodingForm");
		baseClass.stepInfo(
				"Verify that when coding stamp is created/saved using the coding of a completed document, then on mouse hover tool tip should be displayed for the stamp icon.");

		String fieldText = "stamp" + Utility.dynamicNameAppender();
		String assign = "AAsign" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// searching document for assignment creation
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assign, Input.codingFormName);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.add2ReviewerAndDistribute();

		// logout
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		assignmentPage.SelectAssignmentByReviewer(assign);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		driver.waitForPageToBeReady();
		String prndoc = docViewPage.getVerifyPrincipalDocument().getText();
		docViewPage.editCodingFormSave();

		baseClass.waitForElement(docViewPage.getDociD(prndoc));
		docViewPage.getDociD(prndoc).waitAndClick(5);

		reusableDocView.stampColourSelection(fieldText, Input.stampColour);
		// mouse over action over the tool tip
		baseClass.waitForElement(docViewPage.getCodingStampLastIcon(Input.stampColour));
		if (docViewPage.getCodingStampLastIcon(Input.stampColour).isDisplayed()) {
			Actions builder = new Actions(driver.getWebDriver());
			driver.waitForPageToBeReady();
			baseClass.waitForElement(docViewPage.getCodingStampLastIcon(Input.stampColour));
			builder.moveToElement(docViewPage.getCodingStampLastIcon(Input.stampColour).getWebElement()).build()
					.perform();
			driver.waitForPageToBeReady();
			baseClass.waitForElement(docViewPage.getSavedCodingStamp(Input.stampColour));
			String ActualText = docViewPage.getSavedCodingStamp(Input.stampColour).getWebElement()
					.getAttribute("title");
			baseClass.textCompareEquals(fieldText, ActualText, "Mouseover Text is displayed as expected",
					"Mouseover text is not displayed as expected");
		} else {
			baseClass.failedStep("Save this coding form as a new stamp not displayed");
		}

		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 10/02/22 NA Modified date: NA Modified by:NA
	 * Description :Verify on click of 'Save and Next' button coding form should be
	 * validated as per the customized coding form for tag element.'RPMXCON-52076'
	 * Sprint: 12
	 * 
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 48)
	public void verifyCodingFormvalidatedtagElement() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-52076");
		baseClass.stepInfo(
				"Verify on click of 'Save and Next' button coding form should be validated as per the customized coding form for tag element.");

		String codingform = "CFTags" + Utility.dynamicNameAppender();
		String defaultAction = "Make It Required";

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.createCodingFormUsingTwoObjects(codingform, null, null, null, "tag");
		codingForm.addcodingFormAddButton();
		codingForm.selectDefaultActions(0, defaultAction);
		codingForm.enterErrorAndHelpMsg(0, "Yes", "Help for testing", "Error for testing");
		String expectedFirstObjectName = codingForm.getCFObjectsLabel(0);
		System.out.println(expectedFirstObjectName);
		codingForm.saveCodingForm();
		codingForm.assignCodingFormToSG(codingform);

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		// verify tag names
		docViewPage.verifyCodingFormTagNameInDocviewPg(0, expectedFirstObjectName);

		driver.waitForPageToBeReady();
		docViewPage.popOutCodingFormPanel();

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}
		docViewPage.openChildWindowCodingFormInRadioGroup();

		driver.switchTo().window(parentWindowID);
		driver.Navigate().refresh();

		baseClass.passedStep(
				"Verified on click of 'Save and Next' button coding form should be validated as per the customized coding form for comment element in context of security group.");
		codingForm.assignCodingFormToSG(Input.codeFormName);
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.deleteCodingForm(codingform, codingform);
		codingForm.verifyCodingFormIsDeleted(codingform);

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 11/02/2022 Modified date: NA Modified by: Baskar
	 * @Description:Verify user can apply the saved coding stamp to other documents
	 *                     from mini doc list in context of security group
	 */
	@Test(enabled = true, dataProvider = "ContentAndAudio", groups = { "regression" }, priority = 49)
	public void validateSavedStampInSG(String method) throws InterruptedException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();

		String comment = "comment" + Utility.dynamicNameAppender();
		String stampName = "st" + Utility.dynamicNameAppender();

		String childURL = Input.url + "DocumentViewer/CodingFormChildWindow+";
		// Login As rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		baseClass.stepInfo("Test case Id: RPMXCON-52069");
		baseClass.stepInfo("Verify user can apply the saved coding stamp to other documents "
				+ "from mini doc list in context of security group");

		// Basic Search
		if (method.equals("Basic")) {
			sessionSearch.basicContentSearch(Input.searchString2);
			baseClass.stepInfo("User navigated to docview page from basic search");
		} else if (method.equals("Audio")) {// Advanced audio search
			sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
			baseClass.stepInfo("User navigated to docview page from advanced search");
		}
		sessionSearch.ViewInDocView();

		// editing the coding form and saving the stamp
		docViewPage.editCodingForm(comment);
		docViewPage.codingStampButton();
		docViewPage.popUpAction(stampName, Input.stampSelection);
		baseClass.stepInfo("Coding stamp saved with stamp colour and value");

		// verifying stamp saved with loaded value in different document
		baseClass.waitForElement(docViewPage.getDocView_Next());
		docViewPage.getDocView_Next().waitAndClick(5);
		String activeID = docViewPage.getDocView_CurrentDocId().getText();
		driver.waitForPageToBeReady();
		String prnDoc = docViewPage.getVerifyPrincipalDocument().getText();
		softAssertion.assertEquals(activeID, prnDoc);

		// Clicking the saved stamp
		docViewPage.lastAppliedStamp(Input.stampSelection);
		docViewPage.verifyingComments(comment);
		baseClass.passedStep("While clicking the saved stamp value as per expected one in parent window");

		// navigating to other docs for coding from child window valiation
		baseClass.waitForElement(docViewPage.getDocView_Next());
		docViewPage.getDocView_Next().waitAndClick(5);
		String activeTwo = docViewPage.getDocView_CurrentDocId().getText();
		driver.waitForPageToBeReady();
		String prnDocTwo = docViewPage.getVerifyPrincipalDocument().getText();
		softAssertion.assertEquals(activeTwo, prnDocTwo);

		// validation from child window
		docViewPage.clickGearIconOpenCodingFormChildWindow();
		String parentWindow = docViewPage.switchTochildWindow();
		if (driver.getUrl().equalsIgnoreCase(childURL)) {
			baseClass.stepInfo("Coding from child window opened");
		}

		// Clicking the saved stamp from child window
		docViewPage.lastAppliedStamp(Input.stampSelection);
		docViewPage.verifyingComments(comment);
		baseClass.passedStep("While clicking the saved stamp value as per expected one in child window");
		docViewPage.childWindowToParentWindowSwitching(parentWindow);

		// house keeping activity
		driver.Navigate().refresh();
		driver.switchTo().alert().accept();
		docViewPage.deleteStampColour(Input.stampSelection);

		softAssertion.assertAll();
		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 11/02/2022 Modified date: NA Modified by: Baskar
	 * @Description:Verify when 'Complete When Coding Stamp Applied' off at an
	 *                     assignment group/assignment level
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 50)
	public void validatingToggleCodingStampInAssign() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		baseClass.stepInfo("Test case Id: RPMXCON-51280");
		baseClass.stepInfo(
				"Verify when 'Complete When Coding Stamp Applied' " + "off at an assignment group/assignment level");

		String assignment = "Assgn" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();
		String stampName = "stamp" + Utility.dynamicNameAppender();

		// login as rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

//	    searching document for assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignment, Input.codingFormName);

		// Toggle validation
		driver.scrollingToBottomofAPage();
		baseClass.waitForElement(assignmentPage.getAssgn_CodingStampAplliedToggle());
		String stampToggleStatus = (assignmentPage.getAssgn_CodingStampAplliedToggle().GetAttribute("class"))
				.toString();
		if (stampToggleStatus.equalsIgnoreCase("false")) {
			assignmentPage.getAssgn_CodingStampAplliedToggle().waitAndClick(5);
		}
		softAssertion.assertEquals(stampToggleStatus, "false");
		baseClass.passedStep("Coding stamp toggle is disabled at assignment");
		driver.scrollPageToTop();
		assignmentPage.add2ReviewerAndDistribute();
		System.out.println(assignment);

		// logout
		loginPage.logout();

		// login as rev
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting the assignment as reviewer
		assignmentPage.SelectAssignmentByReviewer(assignment);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// validation for coding stamp in assignment level
		String prnDoc = docViewPage.getVerifyPrincipalDocument().getText();
		docViewPage.editCodingForm(comment);
		docViewPage.codingStampButton();
		docViewPage.popUpAction(stampName, Input.stampSelection);
		docViewPage.lastAppliedStamp(Input.stampSelection);
		docViewPage.verifyComments(comment);
		String prnSecDoc = docViewPage.getVerifyPrincipalDocument().getText();
		softAssertion.assertEquals(prnDoc, prnSecDoc);
		baseClass.stepInfo("Coding form object value loaded successfully");
		boolean completeButton = docViewPage.getCompleteDocBtn().Displayed();
		softAssertion.assertTrue(completeButton);
		baseClass.passedStep("Document not completed and not navigated to next docs from minidoclist");
		softAssertion.assertAll();

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 11/02/2022 Modified date: NA Modified by: Baskar
	 * @Description:Verify coding stamp should not be applied to completed document
	 *                     in an assignment
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 51)
	public void validatingSavedStampForCompletedDocs() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		baseClass.stepInfo("Test case Id: RPMXCON-51001");
		baseClass.stepInfo("Verify coding stamp should not be applied to " + "completed document in an assignment");

		String assignment = "Assgn" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();
		String commentTwo = "commentTwo" + Utility.dynamicNameAppender();
		String stampName = "stamp" + Utility.dynamicNameAppender();

		// login as rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// searching document for assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignment, Input.codingFormName);
		assignmentPage.add2ReviewerAndDistribute();
		System.out.println(assignment);

		// logout
		loginPage.logout();

		// login as rev
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting the assignment as reviewer
		assignmentPage.SelectAssignmentByReviewer(assignment);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// validation for saved coding stamp for completed docs in assignment level
		// saving the stamp
		String prnDoc = docViewPage.getVerifyPrincipalDocument().getText();
		docViewPage.editCodingForm(comment);
		docViewPage.codingStampButton();
		docViewPage.popUpAction(stampName, Input.stampSelection);

		// completing the docs
		docViewPage.editCodingForm(commentTwo);
		docViewPage.completeButton();
		docViewPage.getDociD(prnDoc).waitAndClick(5);
		driver.waitForPageToBeReady();
		docViewPage.lastAppliedStamp(Input.stampSelection);
		docViewPage.getDociD(prnDoc).waitAndClick(5);
		driver.waitForPageToBeReady();

		// verifying stamp loaded for completed docs
		baseClass.waitForElement(docViewPage.getDocument_CommentsTextBox());
		String text = docViewPage.getDocument_CommentsTextBox().GetAttribute("value");
		softAssertion.assertNotEquals(text, comment);
		baseClass.passedStep("Coding form stamp object value not updated");
		boolean completeButton = docViewPage.getUnCompleteButton().Displayed();
		softAssertion.assertTrue(completeButton);
	}

	/*
	 * @Author : date: 10/02/2022 Modified date: NA Modified by: Baskar
	 * 
	 * @Description:To verify that comment should displayed on the document.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 52)
	public void verifyCommentDisplayedOnTheDocument() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		String AssignStamp = "Assignment" + Utility.dynamicNameAppender();
		String docComment = "documentcomment1234";
		int count = 1;
		baseClass.stepInfo("Test case Id: RPMXCON-50986");
		baseClass.stepInfo("To verify that comment should displayed on the document.");

		// Login as Rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Perform search and View in DocView
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();

		// Apply comments to document
		String DocId = docViewPage.getClickDocviewID(1).getText();
		System.out.println(DocId);
		docViewPage.addCommentAndSave(docComment, true, count);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocument_CommentsTextBox());
		String DocComments = docViewPage.getDocument_CommentsTextBox().GetAttribute("value");
		System.out.println(DocComments);

		// searching document for assignmnet creation
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(AssignStamp, Input.codingFormName);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.assignmentDistributingToReviewer();
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rev1userName + "'");
		// selecting the assignment
		assignmentPage.SelectAssignmentByReviewer(AssignStamp);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		String DocId1 = docViewPage.getClickDocviewID(1).getText();
		System.out.println(DocId1);
		softAssertion.assertEquals(DocId, DocId1);
		baseClass.stepInfo("Same documenId is displayed on Docview");
		// verify last saved same docId is displayed
		driver.waitForPageToBeReady();
		String DocComments1 = docViewPage.getDocument_CommentsTextBox().GetAttribute("value");
		System.out.println(DocComments1);
		softAssertion.assertEquals(DocComments, DocComments1);
		if (DocComments.equals(DocComments1)) {
			baseClass.passedStep(DocComments + "...Previously saved documentId comment is displayed successfully"
					+ "on same documentId..." + DocComments1);

		} else {
			baseClass.failedMessage("document comment is not displayed");
		}
		softAssertion.assertAll();

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Brundha
	 * @Description:RPMXCON-51300 -Verify all of the stamp icons should be displayed
	 *                            with tool tips when redirected from my assignment
	 *                            and stamp are saved
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 53)
	public void validateToolTipInStampIcons() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		reusableDocView = new ReusableDocViewPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51300-DocView/CodingForm");
		baseClass.stepInfo(
				"Verify all of the stamp icons should be displayed with tool tips when redirected from my assignment and stamp are saved");

		String fieldText = "stamp" + Utility.dynamicNameAppender();
		String assign = "AAsign" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// searching document for assignment creation
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assign, Input.codingFormName);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.add2ReviewerAndDistribute();

		// logout
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		assignmentPage.SelectAssignmentByReviewer(assign);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		driver.waitForPageToBeReady();
		String prndoc = docViewPage.getVerifyPrincipalDocument().getText();
		docViewPage.editCodingFormSave();

		baseClass.waitForElement(docViewPage.getDociD(prndoc));
		docViewPage.getDociD(prndoc).waitAndClick(5);

		// Verifying tool tip for blue color saved stamp
		reusableDocView.stampColourSelection(fieldText, Input.stampColour);
		docViewPage.VerifySavedStampToolTip(Input.stampColour, fieldText);

		// Verifying tool tip for green color saved stamp
		reusableDocView.stampColourSelection(fieldText, Input.stampGreen);
		docViewPage.VerifySavedStampToolTip(Input.stampGreen, fieldText);

		// Verifying tool tip for yellow color saved stamp
		reusableDocView.stampColourSelection(fieldText, Input.stampSelection);
		docViewPage.VerifySavedStampToolTip(Input.stampSelection, fieldText);

		// Verifying tool tip for red color saved stamp
		reusableDocView.stampColourSelection(fieldText, Input.stampRed);
		docViewPage.VerifySavedStampToolTip(Input.stampRed, fieldText);

		// Verifying tool tip for black color saved stamp
		reusableDocView.stampColourSelection(fieldText, Input.stampColours);
		docViewPage.VerifySavedStampToolTip(Input.stampColours, fieldText);

		// overall assertion
		softAssertion.assertAll();
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 15/02/2022 Modified date: NA Modified by: Baskar
	 * @Description:Verify on click of the View Coding from edit coding stamp saved
	 *                     coding form should be displayed
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 54)
	public void validateSavedStampFromViewCoding() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-51263");
		baseClass.stepInfo(
				"Verify on click of the View Coding from edit coding " + "stamp saved coding form should be displayed");
		String assign = "AAssgn" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();
		String fieldText = "stamp" + Utility.dynamicNameAppender();

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Create New Assignment
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assign, Input.codingFormName);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.assignmentDistributingToReviewer();

		// logout
		loginPage.logout();

		// login as reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting assignment by reviewer
		assignmentPage.SelectAssignmentByReviewer(assign);
		docViewPage.editCodingForm(comment);
		docViewPage.codingStampButton();
		docViewPage.popUpAction(fieldText, Input.stampSelection);
		docViewPage.pencilGearicon(Input.stampSelection);
		boolean EditStamp = docViewPage.getEditCodingStamp_PopUpWindow().Displayed();
		softAssertion.assertTrue(EditStamp);
		docViewPage.clickViewCodingButton();
		driver.waitForPageToBeReady();

		// validation from view coding stamp popup window
		boolean viewCoding = docViewPage.getViewCodingStamp_PopUpWindow().Displayed();
		softAssertion.assertTrue(viewCoding);
		baseClass.stepInfo("ViewCoding stamp popup window opened");
		baseClass.waitForElement(docViewPage.getDocumentsCommentViewCoding());
		docViewPage.getDocumentsCommentViewCoding().ScrollTo();
		String actual = docViewPage.getDocumentsCommentViewCoding().getText();
		softAssertion.assertEquals(comment, actual);
		baseClass.passedStep("While View coding stamp window stamp saved value loaded successfully");
		softAssertion.assertAll();

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 15/02/2022 Modified date: NA Modified by: Baskar
	 * @Description:Verify on click of the View Coding from edit coding stamp saved
	 *                     coding form should be displayed in context of security
	 *                     group
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 55)
	public void validateViewCodingStampFromSG() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-52054");
		baseClass.stepInfo("Verify on click of the View Coding from edit coding stamp "
				+ "saved coding form should be displayed in context of security group");
		String comment = "comment" + Utility.dynamicNameAppender();
		String fieldText = "stamp" + Utility.dynamicNameAppender();

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		codingForm.assignCodingFormToSG("Default Project Coding Form");

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		docViewPage.selectPureHit();
		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch1(Input.testData1);
		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		// stamp saving as Prerequisites
		docViewPage.editCodingForm(comment);
		docViewPage.codingStampButton();
		docViewPage.popUpAction(fieldText, Input.stampSelection);

		// validation for edit coding stamp popup window
		docViewPage.pencilGearicon(Input.stampSelection);
		boolean EditStamp = docViewPage.getEditCodingStamp_PopUpWindow().Displayed();
		softAssertion.assertTrue(EditStamp);
		docViewPage.clickViewCodingButton();
		driver.waitForPageToBeReady();

		// validation from view coding stamp popup window
		boolean viewCoding = docViewPage.getViewCodingStamp_PopUpWindow().Displayed();
		softAssertion.assertTrue(viewCoding);
		baseClass.stepInfo("ViewCoding stamp popup window opened");
		baseClass.waitForElement(docViewPage.getDocumentsCommentViewCoding());
		docViewPage.getDocumentsCommentViewCoding().ScrollTo();
		String actual = docViewPage.getDocumentsCommentViewCoding().getText();
		softAssertion.assertEquals(comment, actual);
		baseClass.passedStep("While View coding stamp window stamp saved value loaded successfully");
		softAssertion.assertAll();
		docViewPage.getViewCodingCloseButton().waitAndClick(5);
		baseClass.waitForElement(docViewPage.getDeletePopUpAssignedColour());
		docViewPage.getDeletePopUpAssignedColour().waitAndClick(10);
		softAssertion.assertAll();
		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 15/02/2022 Modified date: NA Modified by: Baskar
	 * @Description:Coding Form Child Window: Verify on click of the View Coding
	 *                     from edit coding stamp saved coding form should be
	 *                     displayed in context of security group
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 56)
	public void validateViewCodingButtonFromCW() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-52055");
		baseClass.stepInfo("Coding Form Child Window: Verify on click of the View Coding from "
				+ "edit coding stamp saved coding form should be displayed in context of security group");
		String comment = "comment" + Utility.dynamicNameAppender();
		String fieldText = "stamp" + Utility.dynamicNameAppender();

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		codingForm.assignCodingFormToSG("Default Project Coding Form");

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		docViewPage.selectPureHit();
		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch1(Input.testData1);
		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		// stamp saving as Prerequisites
		docViewPage.editCodingForm(comment);
		docViewPage.codingStampButton();
		docViewPage.popUpAction(fieldText, Input.stampSelection);
		// opening child window(Coding form)
		baseClass.stepInfo("performing action in coding form child window");
		docViewPage.clickGearIconOpenCodingFormChildWindow();
		docViewPage.switchToNewWindow(2);
		driver.waitForPageToBeReady();
		docViewPage.pencilGearIconCF(Input.stampSelection);
		docViewPage.closeWindow(1);
		docViewPage.switchToNewWindow(1);
		driver.waitForPageToBeReady();

		// validation for edit coding stamp popup window
		boolean EditStamp = docViewPage.getEditCodingStamp_PopUpWindow().Displayed();
		softAssertion.assertTrue(EditStamp);
		docViewPage.clickViewCodingButton();
		driver.waitForPageToBeReady();

		// validation for view coding button popup window from child window
		boolean viewCoding = docViewPage.getViewCodingStamp_PopUpWindow().Displayed();
		softAssertion.assertTrue(viewCoding);
		baseClass.stepInfo("ViewCoding stamp popup window opened");
		baseClass.waitForElement(docViewPage.getDocumentsCommentViewCoding());
		docViewPage.getDocumentsCommentViewCoding().ScrollTo();
		String actual = docViewPage.getDocumentsCommentViewCoding().getText();
		softAssertion.assertEquals(comment, actual);
		baseClass.passedStep("While View coding stamp window stamp saved value loaded successfully in child window");
		softAssertion.assertAll();
		docViewPage.getViewCodingCloseButton().waitAndClick(5);
		baseClass.waitForElement(docViewPage.getDeletePopUpAssignedColour());
		docViewPage.getDeletePopUpAssignedColour().waitAndClick(10);
		softAssertion.assertAll();
		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 16/02/2022 Modified date: NA Modified by: Baskar
	 * @Description:Verify confirmation message should be displayed when overwriting
	 *                     the stamp with already saved stamp in context of security
	 *                     group
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 57)
	public void validateOverWriteMsgForSavedStampSG() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-52057");
		baseClass.stepInfo("Verify confirmation message should be displayed when "
				+ "overwriting the stamp with already saved stamp in context of security group");
		String comment = "comment" + Utility.dynamicNameAppender();
		String fieldText = "stamp" + Utility.dynamicNameAppender();

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Searching audio document and basic search
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		docViewPage.selectPureHit();
		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch1(Input.testData1);
		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		// saving the stamp as per Prerequisites
		docViewPage.editCodingForm(comment);
		docViewPage.codingStampButton();
		docViewPage.popUpAction(fieldText, Input.stampSelection);
		docViewPage.pencilGearicon(Input.stampSelection);
		driver.waitForPageToBeReady();

		// validation for saved stamp displaying in edit popup window
		docViewPage.getDrp_CodingEditStampColour().waitAndClick(5);
		boolean assignedColour = docViewPage.getStampPopUpDrpDwnColur(Input.stampSelection).Displayed();
		softAssertion.assertTrue(assignedColour);
		baseClass.stepInfo("Assigned colour stamp displayed in the drop down");

		// changing the colour for already saved colour
		docViewPage.getStampPopUpDrpDwnColur(Input.stampSelection).waitAndClick(5);
		docViewPage.codingStampPopUpSaveButton();
		String overWrite = docViewPage.getStampOverWriteMessage().getText().trim();
		System.out.println(overWrite);
		softAssertion.assertEquals(stampOverWrite, overWrite);
		docViewPage.getNavigationButton("Yes").waitAndClick(5);
		baseClass.passedStep("Confirmation message displayd when saving the already existing saved stamp");
		driver.waitForPageToBeReady();
		baseClass.VerifySuccessMessage("Coding stamp updated successfully");
		docViewPage.deleteStampColour(Input.stampSelection);
		softAssertion.assertAll();

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 16/02/2022 Modified date: NA Modified by: Baskar
	 * @Description:Verify user can save the coding stamp with the stamp color which
	 *                     is deleted in context of security group
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 58)
	public void validateUsingSavingAndDeletingTheStamp() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-52059");
		baseClass.stepInfo("Verify user can save the coding stamp with the "
				+ "stamp color which is deleted in context of security group");
		String comment = "comment" + Utility.dynamicNameAppender();
		String fieldText = "stamp" + Utility.dynamicNameAppender();

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Searching audio document and basic search
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		docViewPage.selectPureHit();
		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch1(Input.testData1);
		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		// saving the stamp as per Prerequisites
		docViewPage.editCodingForm(comment);
		docViewPage.codingStampButton();
		docViewPage.popUpAction(fieldText, Input.stampSelection);
		docViewPage.pencilGearicon(Input.stampSelection);
		driver.waitForPageToBeReady();

		// validation for saved stamp displaying in edit popup window
		boolean assignedColour = docViewPage.getCodingStampPopUpColurVerify(Input.stampSelection).isDisplayed();
		softAssertion.assertTrue(assignedColour);
		baseClass.stepInfo("Assigned colour stamp displayed in the drop down");
		baseClass.waitForElement(docViewPage.getDeletePopUpAssignedColour());
		docViewPage.getDeletePopUpAssignedColour().waitAndClick(10);
		baseClass.VerifySuccessMessage("Coding stamp deleted successfully");

		// Reassign the same colour after deleting the stamp
		docViewPage.editCodingForm(comment);
		docViewPage.codingStampButton();
		docViewPage.popUpAction(fieldText, Input.stampSelection);
		baseClass.VerifySuccessMessage("Coding Stamp saved successfully");
		docViewPage.deleteStampColour(Input.stampSelection);
		baseClass.passedStep("After the deleting " + Input.stampSelection + "stamp, same " + Input.stampSelection
				+ " stamp assigned again");

		// Performing same action in coding form child window as well
		docViewPage.clickGearIconOpenCodingFormChildWindow();
		baseClass.stepInfo("Performing action from coding form child window");
		docViewPage.switchToNewWindow(2);
		docViewPage.editCodingForm(comment);
		docViewPage.codingStampButton();
		docViewPage.switchToNewWindow(1);
		docViewPage.popUpAction(fieldText, Input.stampSelection);
		docViewPage.switchToNewWindow(2);
		driver.waitForPageToBeReady();
		docViewPage.pencilGearIconCF(Input.stampSelection);

		// validation for saved stamp displaying in edit popup window in child window
		docViewPage.switchToNewWindow(1);
		boolean assignedColourCF = docViewPage.getCodingStampPopUpColurVerify(Input.stampSelection).isDisplayed();
		softAssertion.assertTrue(assignedColourCF);
		baseClass.stepInfo("Assigned colour stamp displayed in the drop down");
		baseClass.waitForElement(docViewPage.getDeletePopUpAssignedColour());
		docViewPage.getDeletePopUpAssignedColour().waitAndClick(10);
		baseClass.VerifySuccessMessage("Coding stamp deleted successfully");

		// Reassign the same colour after deleting the stamp from child window
		docViewPage.switchToNewWindow(2);
		docViewPage.editCodingForm(comment);
		docViewPage.codingStampButton();
		docViewPage.closeWindow(1);
		docViewPage.switchToNewWindow(1);
		docViewPage.popUpAction(fieldText, Input.stampSelection);
		baseClass.VerifySuccessMessage("Coding Stamp saved successfully");
		baseClass.passedStep("After the deleting " + Input.stampSelection + "stamp, same " + Input.stampSelection
				+ " stamp assigned again from child window action");

		// House Keeping activity
		driver.Navigate().refresh();
		driver.switchTo().alert().accept();
		docViewPage.deleteStampColour(Input.stampSelection);
		softAssertion.assertAll();

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 17/02/2022 Modified date: NA Modified by: Baskar
	 * @Description:Verify confirmation message to overwrite should be displayed
	 *                     when user clicks 'Save' button without changing stamp
	 *                     color and name in context of security group
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 59)
	public void validateOverWriteMsgForRenamingStampName() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-52060");
		baseClass.stepInfo("Verify confirmation message to overwrite should be displayed when user "
				+ "clicks 'Save' button without changing stamp color and name in context of security group");
		String comment = "comment" + Utility.dynamicNameAppender();
		String fieldText = "stamp" + Utility.dynamicNameAppender();
		String stampTextTwo = "stamp2" + Utility.dynamicNameAppender();

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Searching audio document and basic search
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		docViewPage.selectPureHit();
		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch1(Input.testData1);
		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		// saving the stamp as per Prerequisites
		docViewPage.editCodingForm(comment);
		docViewPage.codingStampButton();
		docViewPage.popUpAction(fieldText, Input.stampSelection);
		docViewPage.pencilGearicon(Input.stampSelection);
		driver.waitForPageToBeReady();

		// validation for saved stamp displaying in edit popup window
		docViewPage.getDrp_CodingEditStampColour().waitAndClick(5);
		boolean assignedColour = docViewPage.getStampPopUpDrpDwnColur(Input.stampSelection).Displayed();
		softAssertion.assertTrue(assignedColour);
		baseClass.stepInfo("Assigned colour stamp displayed in the drop down");

		// Renaming the stamp Name
		String stampName = docViewPage.getCodingEditStampTextBox().GetAttribute("value");
		docViewPage.getCodingEditStampTextBox().SendKeys(stampTextTwo);
		String reNamingStamp = docViewPage.getCodingEditStampTextBox().GetAttribute("value");
		softAssertion.assertNotEquals(stampName, reNamingStamp);
		baseClass.stepInfo("Stamp renamed successfully");

		// validation after renaming the stamp name
		docViewPage.codingStampPopUpSaveButton();
		baseClass.passedStep("Confirmation message displayd for renaming stamp name with YES and NO button");
		baseClass.waitForElement(docViewPage.getStampOverWriteMessage());
		String overWriteNO = docViewPage.getStampOverWriteMessage().getText().trim();
		System.out.println(overWriteNO);
		softAssertion.assertEquals(stampOverWrite, overWriteNO);

		// Using No button
		docViewPage.getNavigationButton("No").waitAndClick(5);
		boolean notOverWrited = baseClass.VerifySuccessMessageB("Coding stamp updated successfully");
		softAssertion.assertFalse(notOverWrited);

		// Using Yes button
		docViewPage.codingStampPopUpSaveButton();
		baseClass.passedStep("Confirmation message displayd for renaming stamp name with YES and NO button");
		baseClass.waitForElement(docViewPage.getStampOverWriteMessageLast());
		String overWriteYES = docViewPage.getStampOverWriteMessageLast().getText().trim();
		System.out.println(overWriteYES);
		softAssertion.assertEquals(stampOverWrite, overWriteYES);
		docViewPage.getNavigationButton("Yes").waitAndClick(5);
		boolean overWrited = baseClass.VerifySuccessMessageB("Coding stamp updated successfully");
		softAssertion.assertTrue(overWrited);

		// House Keeping activity
		docViewPage.deleteStampColour(Input.stampSelection);
		softAssertion.assertAll();

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 17/02/2022 Modified date: NA Modified by: Baskar
	 * @Description:Verify tool tip should be displayed for the stamp icons on mouse
	 *                     over after editing the stamp in context of security group
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 60)
	public void validatToolTipForStampIcon() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-52063");
		baseClass.stepInfo("Verify tool tip should be displayed for the stamp icons on mouse "
				+ "over after editing the stamp in context of security group");
		String comment = "comment" + Utility.dynamicNameAppender();
		String fieldText = "stamp" + Utility.dynamicNameAppender();
		String stampTextTwo = "stamp2" + Utility.dynamicNameAppender();

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		codingForm.assignCodingFormToSG(Input.codingFormName);

		// Searching audio document and basic search
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		docViewPage.selectPureHit();
		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch1(Input.testData1);
		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		// saving the stamp as per Prerequisites
		docViewPage.editCodingForm(comment);
		docViewPage.codingStampButton();
		docViewPage.popUpAction(fieldText, Input.stampSelection);
		docViewPage.pencilGearicon(Input.stampSelection);
		driver.waitForPageToBeReady();

		// validation for edit coding stamp with different colour
		docViewPage.getDrp_CodingEditStampColour().waitAndClick(5);
		docViewPage.getEditAssignedColour(Input.stampColour).waitAndClick(5);
		docViewPage.getCodingEditStampTextBox().SendKeys(stampTextTwo);
		docViewPage.codingStampPopUpSaveButton();
		boolean stampSave = baseClass.VerifySuccessMessageB("Coding stamp updated successfully");
		softAssertion.assertTrue(stampSave);

		// mouser over at the saved stamp
		docViewPage.VerifySavedStampToolTip(Input.stampColour, stampTextTwo);

		// performing action from coding form child window
		docViewPage.clickGearIconOpenCodingFormChildWindow();
		String parentWindow = docViewPage.switchTochildWindow();
		baseClass.stepInfo("Performing action from coding form child window");

		// mouser over at the saved stamp from child window
		docViewPage.VerifySavedStampToolTip(Input.stampColour, stampTextTwo);
		docViewPage.childWindowToParentWindowSwitching(parentWindow);

		// House Keeping activity
		driver.Navigate().refresh();
		driver.switchTo().alert().accept();
		docViewPage.deleteStampColour(Input.stampColour);
		softAssertion.assertAll();

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 18/02/2022 Modified date: NA Modified by: Baskar
	 * @Description:Verify on click of the gear icon of the coding stamp saved stamp
	 *                     color should be clickable in context of security group
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 61)
	public void validatSavedStampShouldClickable() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-52064");
		baseClass.stepInfo("Verify on click of the gear icon of the coding stamp "
				+ "saved stamp color should be clickable in context of security group");
		String comment = "comment" + Utility.dynamicNameAppender();
		String fieldText = "stamp" + Utility.dynamicNameAppender();

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Searching audio document and basic search
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		docViewPage.selectPureHit();
		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch1(Input.testData1);
		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		// saving the stamp as per Prerequisites
		docViewPage.editCodingForm(comment);
		docViewPage.codingStampButton();
		docViewPage.popUpAction(fieldText, Input.stampSelection);
		docViewPage.pencilGearicon(Input.stampSelection);
		// boolean editMode=docViewPage.getStampInEditMode().Displayed();
		// softAssertion.assertTrue(editMode);
		baseClass.stepInfo("coding stamp in edit mode");
		driver.waitForPageToBeReady();

		// validation for edit coding stamp popup
		boolean EditStamp = docViewPage.getEditCodingStamp_PopUpWindow().Displayed();
		softAssertion.assertTrue(EditStamp);
		String stampName = docViewPage.getCodingEditStampTextBox().GetAttribute("value");
		softAssertion.assertEquals(stampName, fieldText);
		boolean stampColour = docViewPage.getCodingStampPopUpColurVerify(Input.stampSelection).Displayed();
		softAssertion.assertTrue(stampColour);
		baseClass.waitForElement(docViewPage.getCodingStampCancel());
		docViewPage.getCodingStampCancel().waitAndClick(5);
		baseClass.passedStep("Coding stamp popup window opened while clicking the saved stamp");
		baseClass.stepInfo("Assigned stamp name and assigned stamp colour displayed in popup window");

		// Validation for coding form child window
		docViewPage.clickGearIconOpenCodingFormChildWindow();
		String parentWinodw = docViewPage.switchTochildWindow();
		baseClass.stepInfo("performing action for coding form child window");
		docViewPage.pencilGearIconCF(Input.stampSelection);
		docViewPage.childWindowToParentWindowSwitching(parentWinodw);
		boolean EditStampCF = docViewPage.getEditCodingStamp_PopUpWindow().Displayed();
		softAssertion.assertTrue(EditStampCF);
		String stampNameCF = docViewPage.getCodingEditStampTextBox().GetAttribute("value");
		softAssertion.assertEquals(stampNameCF, fieldText);
		boolean stampColourCF = docViewPage.getCodingStampPopUpColurVerify(Input.stampSelection).Displayed();
		softAssertion.assertTrue(stampColourCF);

		// House Keeping activity
		driver.Navigate().refresh();
		driver.switchTo().alert().accept();
		docViewPage.deleteStampColour(Input.stampSelection);
		softAssertion.assertAll();

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 18/02/2022 Modified date: NA Modified by: Baskar
	 * @Description:[Covered Coding form child window]Verify user can edit the
	 *                       coding stamp with the different color in context of
	 *                       security group
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 62)
	public void validatWithoutChangingObjectUsingStamp() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-52065");
		baseClass.stepInfo("[Covered Coding form child window]Verify user can edit the "
				+ "coding stamp with the different color in context of security group");
		String comment = "comment" + Utility.dynamicNameAppender();
		String fieldText = "stamp" + Utility.dynamicNameAppender();
		String stampTextTwo = "stamp2" + Utility.dynamicNameAppender();
		String commentCf = "commentcf" + Utility.dynamicNameAppender();
		String fieldTextCf = "stampcf" + Utility.dynamicNameAppender();
		String stampTextTwoCf = "stamp2cf" + Utility.dynamicNameAppender();

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Searching audio document and basic search
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		docViewPage.selectPureHit();
		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch1(Input.testData1);
		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		// saving the stamp as per Prerequisites
		String prnDoc = docViewPage.getVerifyPrincipalDocument().getText();
		docViewPage.editCodingForm(comment);
		docViewPage.codingStampButton();
		docViewPage.popUpAction(fieldText, Input.stampSelection);
		docViewPage.pencilGearicon(Input.stampSelection);
		// boolean editMode=docViewPage.getStampInEditMode().Displayed();
//		softAssertion.assertTrue(editMode);
		baseClass.stepInfo("coding stamp in edit mode");
		driver.waitForPageToBeReady();

		// validation for edit coding stamp with different colour
		docViewPage.getDrp_CodingEditStampColour().waitAndClick(5);
		docViewPage.getEditAssignedColour(Input.stampColour).waitAndClick(5);
		docViewPage.getCodingEditStampTextBox().SendKeys(stampTextTwo);
		docViewPage.codingStampPopUpSaveButton();
		boolean stampSave = baseClass.VerifySuccessMessageB("Coding stamp updated successfully");
		softAssertion.assertTrue(stampSave);
		docViewPage.lastAppliedStamp(Input.stampColour);

		// validation for without edit coding form object just rename the stamp colour
		docViewPage.verifyingComments(comment);
		baseClass.passedStep("Coding form stamp object as per the expected value");
		docViewPage.deleteStampColour(Input.stampColour);

		// performing action from coding form child window
		docViewPage.clickGearIconOpenCodingFormChildWindow();
		String parentWindow = docViewPage.switchTochildWindow();
		baseClass.stepInfo("Performing action from coding form child window");
		docViewPage.editCodingForm(commentCf);
		docViewPage.codingStampButton();
		docViewPage.switchToNewWindow(1);
		docViewPage.popUpAction(fieldTextCf, Input.stampSelection);
		docViewPage.switchToNewWindow(2);
		docViewPage.pencilGearIconCF(Input.stampSelection);
//		boolean editModecf=docViewPage.getStampInEditMode().Displayed();
//		softAssertion.assertTrue(editModecf);
		baseClass.stepInfo("coding stamp in edit mode from child window");
		driver.waitForPageToBeReady();

		// validation for edit coding stamp with different colour from child window
		docViewPage.switchToNewWindow(1);
		docViewPage.getDrp_CodingEditStampColour().waitAndClick(5);
		docViewPage.getEditAssignedColour(Input.stampColour).waitAndClick(5);
		docViewPage.getCodingEditStampTextBox().SendKeys(stampTextTwoCf);
		docViewPage.codingStampPopUpSaveButton();
		boolean stampSavecf = baseClass.VerifySuccessMessageB("Coding stamp updated successfully");
		softAssertion.assertTrue(stampSavecf);
		docViewPage.switchToNewWindow(2);
		docViewPage.lastAppliedStamp(Input.stampColour);

		// validation for without edit coding form object just rename the stamp colour
		// in child window
		docViewPage.verifyingComments(commentCf);
		baseClass.passedStep("Coding form stamp object as per the expected value from child window");
		docViewPage.closeWindow(1);
		docViewPage.switchToNewWindow(1);

		// House Keeping activity
		driver.Navigate().refresh();
		driver.switchTo().alert().accept();
		docViewPage.deleteStampColour(Input.stampColour);
		softAssertion.assertAll();

		// logout
		loginPage.logout();
	}
	
	
	/**
	 * @Author : Baskar date:27/9/21 Modified date: NA Modified by: Baskar 
	 * @Description : [TC reference RPMXCON-51207] Verify on click of 'Save and Next' 
	 *                button coding form should be validated as per the customized Tags 
	 *                and Radio Group combined along with Radio Item on coding form screen 
	 *                in context of security group
	 * 
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 63)
	public void  createCodingFormWithTwotags() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52086");
		String codingfrom = "CF"+Utility.dynamicNameAppender();
		String tagnameprev = "Tagprev"+Utility.dynamicNameAppender();
		String tag="tag";

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
        // create tag
		tagsAndFoldersPage.CreateTag(tagnameprev,"Default Security Group");
		
		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.AddCodingFormWithTwoTag(codingfrom, tagnameprev, "tag",tag, "radio item");
		baseClass.stepInfo("Coding form saved with the two tags along with radio button");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(codingfrom);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1,Input.language);

		docViewPage.selectPureHit();

		// Search for non-audio documents
		baseClass.stepInfo("Searching non-audio documents based on search string");
		sessionSearch.advancedNewContentSearch1(Input.searchStringStar);

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();
		
		docViewPage.saveAndNextNewCodingFormTags();
		
		codingForm.assignCodingFormToSG("Default Project Coding Form");

		
		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");
	}
	
	/**
	 * @Author : Baskar date:27/9/21 Modified date: NA Modified by: Baskar 
	 * @Description : [TC reference RPMXCON-51206] Verify on click of 'Save and Next' button 
	 *                coding form should be validated as per the customized coding form for Tag/Comments/Metadata 
	 *                objects in context of security group
	 * 
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 64)
	public void  createCodingFormWithMetaData() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52085");
		String codingfrom = "CF" + Utility.dynamicNameAppender();
		String tagnameprev = "Tagprev" + Utility.dynamicNameAppender();
		String comment = "Comment" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		String text = codingForm.addCodingFormWithAllTabsMetaDataError(codingfrom, tagnameprev, comment);
		baseClass.stepInfo("Coding form saved with Editable metadata with error message");

		// Assign to security group
		codingForm.assignCodingFormToSG(codingfrom);
		baseClass.stepInfo("Coding form assigned to security group");

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		docViewPage.selectPureHit();

		// Search for non-audio documents
		baseClass.stepInfo("Searching non-audio documents based on search string");
		sessionSearch.advancedNewContentSearch1(Input.searchStringStar);

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		docViewPage.saveAndNextMetaDataErrorMsg(text);

		codingForm.assignCodingFormToSG("Default Project Coding Form");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");
	}
	
	/**
	 * @Author : Baskar date:27/9/21 Modified date: NA Modified by: Baskar 
	 * @Description : [TC reference RPMXCON-51204] Verify on click of 'Save and Next' 
	 *                button coding form should be validated as per the customized coding 
	 *                form using Comment object on coding form screen in context of security group
	 * 
	 */
	
	@Test(enabled = true, groups = { "regression" }, priority = 65)
	public void  createCodingFormWithComment() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52084");
		String codingfrom = "CF"+Utility.dynamicNameAppender();
		String tagnameprev = "Tagprev"+Utility.dynamicNameAppender();
		String comment = "Comment"+Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		CommentsPage cp= new CommentsPage(driver);
		cp.AddComments(comment);
		
		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.addCodingFormWithComment(codingfrom, tagnameprev, comment);
		baseClass.stepInfo("Coding form saved with comment with Make it display but not selectable");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(codingfrom);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1,Input.language);

		docViewPage.selectPureHit();

		// Search for non-audio documents
		baseClass.stepInfo("Searching non-audio documents based on search string");
		sessionSearch.advancedNewContentSearch1(Input.searchStringStar);

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		docViewPage.saveAndNextCommentNotClickable();
		
		codingForm.assignCodingFormToSG("Default Project Coding Form");
		
		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");
	}
	
	
	/**
	 * @Author : Baskar date:27/9/21 Modified date: NA Modified by: Baskar 
	 * @Description : [TC reference RPMXCON-51203] Verify on click of 'Save and Next' 
	 *                button coding form should be validated as per the customized coding form 
	 *                using Tag object along with Check Item in context of security group
	 */
	
	@Test(enabled = true, groups = { "regression" }, priority = 66)
	public void  createCodingFormWithTagsOptional() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52083");
		String tag="tag";
		String tagTypeCheck="check item";
		String action="Make It Optional";
		String codingfrom = "CF"+Utility.dynamicNameAppender();
		String tagnameprev = "Tagprev"+Utility.dynamicNameAppender();
		String comment = "Comment"+Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		 // create tag
		tagsAndFoldersPage.CreateTag(tagnameprev,"Default Security Group");
		
		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.addCodingFormWithTag(codingfrom, tagnameprev, comment,tag,tagTypeCheck,action);
		baseClass.stepInfo("Coding form saved with comment with Make it display but not selectable");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(codingfrom);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1,Input.language);

		docViewPage.selectPureHit();

		// Search for non-audio documents
		baseClass.stepInfo("Searching non-audio documents based on search string");
		sessionSearch.advancedNewContentSearch1(Input.searchStringStar);

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		docViewPage.saveAndNextNewCodingFormTags();
		
		codingForm.assignCodingFormToSG("Default Project Coding Form");
		
		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");
	}
	
	
	/**
	 * @Author : Baskar date:27/9/21 Modified date: NA Modified by: Baskar 
	 * @Description : [TC reference: RPMXCON-51200] Verify on click of 'Save and Next' 
	 *                button coding form should be validated as per the customized coding 
	 *                form using multiple comments elements
	 */
	
	@Test(enabled = true, groups = { "regression" }, priority = 67)
	public void  createCodingFormWithCommentError() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52071");
		String tag="tag";
		String tagTypeCheck="check item";
		String action="Make It Required";
		String comments="comment";
		String codingfrom = "CF"+Utility.dynamicNameAppender();
		String tagnameprev = "Tagprev"+Utility.dynamicNameAppender();
		String comment = "Comment"+Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		CommentsPage cp= new CommentsPage(driver);
		cp.AddComments(comment);
		
		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.addCodingFormWithCommentValidation(codingfrom, tagnameprev, comment,comments,tagTypeCheck,action);
		baseClass.stepInfo("Coding form saved with comment with Make it display but not selectable");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(codingfrom);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1,Input.language);

		docViewPage.selectPureHit();

		// Search for non-audio documents
		baseClass.stepInfo("Searching non-audio documents based on search string");
		sessionSearch.advancedNewContentSearch1(Input.searchStringStar);

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		docViewPage.saveAndNextCommentTextBoxValidation();
		
		codingForm.assignCodingFormToSG("Default Project Coding Form");
		
		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");
	}
	
	
	/**
	 * @Author : Baskar date:27/9/21 Modified date: NA Modified by: Baskar 
	 * @Description : Verify the appearance of the text in the Coding Form Panel of 
	 *                DocView from child window
	 */
	
	@Test(enabled = true, groups = { "regression" }, priority = 68)
	public void  appearanceTextParentWindow() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51507");
		String tag="tag";
		String tagTypeRadio="radio item";
		String action="Make It Required";
		String comments="comment";
		String codingfrom = "CF"+Utility.dynamicNameAppender();
		String tagnameprev = "Tagprev"+Utility.dynamicNameAppender();
		String comment = "Comment"+Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		 // create tag
		tagsAndFoldersPage.CreateTag(tagnameprev,"Default Security Group");
		
		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.addCodingFormWithTagInstructionText(codingfrom, tagnameprev, comment,tag,tagTypeRadio,action);
		baseClass.stepInfo("Coding form saved with comment with Make it display but not selectable");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(codingfrom);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		docViewPage.apperanceInstructionTextLeftParent();
		
		codingForm.assignCodingFormToSG("Default Project Coding Form");
		
		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");
	}
	
	
	/**
	 * @Author : Baskar date:27/9/21 Modified date: NA Modified by: Baskar 
	 * @Description : Verify the appearance of the text in the Coding Form Panel of 
	 *                DocView from parent window
	 */
	
	@Test(enabled = true, groups = { "regression" }, priority = 69)
	public void  appearanceTextChildWindow() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51506");
		String tag="tag";
		String tagTypeRadio="radio item";
		String action="Make It Required";
		String comments="comment";
		String codingfrom = "CF"+Utility.dynamicNameAppender();
		String tagnameprev = "Tagprev"+Utility.dynamicNameAppender();
		String comment = "Comment"+Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		 // create tag
		tagsAndFoldersPage.CreateTag(tagnameprev,"Default Security Group");
		
		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.addCodingFormWithTagInstructionText(codingfrom, tagnameprev, comment,tag,tagTypeRadio,action);
		baseClass.stepInfo("Coding form saved with comment with Make it display but not selectable");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(codingfrom);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		docViewPage.apperanceInstructionTextLeftChild();
		
		codingForm.assignCodingFormToSG("Default Project Coding Form");
		
		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");
	}
	
	
	/**
	 * @Author : Baskar date:27/9/21 Modified date: NA Modified by: Baskar 
	 * @Description : [TC reference RPMXCON-51202] Verify on click of 'Save and Next' 
	 *                button coding form should be validated as per the customized coding 
	 *                form using Multiple Static Text elements in context of security group
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 70)
	public void  CreateCodingFormUsingStatic() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52082");
		String codingfrom = "CF"+Utility.dynamicNameAppender();
		String staticText="Mandatory field to  be selected";
		UtilityLog.info("Started Execution for prerequisite");

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.addCodingFormWithStaticText(codingfrom,staticText);
		baseClass.stepInfo("Coding form saved with comment with Make it display but not selectable");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(codingfrom);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		docViewPage.staticTextErrorMessage();
		
		codingForm.assignCodingFormToSG("Default Project Coding Form");
		
		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");
	}
	
	/**
	 * @Author : Baskar date: NA Modified date:28/10/2021 Modified by: Baskar 
	 * @Description :Verify that custom metadata field value should be retained 
	 *               on doc view when created with DATETIME datatype in context of security group
	 */
	
	@Test(enabled = true, groups = { "regression" }, priority = 71)
	public void verifyThatCustomMetaDataFieldValueWithDATETIMEDatatype() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-52175");
	    String codingfrom = "CFDateTime"+Utility.dynamicNameAppender();
		UtilityLog.info("Started Execution for prerequisite");
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
	
		// Custom Field created with DATETIME DataType
		projectPage.addCustomFieldDataType(dateTime, "DATETIME");
		baseClass.stepInfo("Custom meta data field created with DATETIME datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(dateTime);
		baseClass.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Creating Coding Form
		codingForm.creatingCodingFormAndAssgnCustomFields(codingfrom, dateTime);
		baseClass.stepInfo("Project field added to coding form in Doc view");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(codingfrom);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// logout
		loginPage.logout();
		
		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		// verify the coding form panel
		docViewPage.verifyCodingFormDocViewINT(dateTime);

		// logout
		loginPage.logout();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		codingForm.assignCodingFormToSG("Default Project Coding Form");
//		Doing cleanup activity done in prerequities
		codingForm.deleteCodingForm(codingfrom,codingfrom);
		
		// logout
		loginPage.logout();
		
	}

	
	/**
	 * @Author : Baskar date: NA Modified date:28/10/2021 Modified by: Baskar 
	 * @Description :Verify that read only custom metadata field value should 
	 *               be retained on doc view when created with INT datatype in 
	 *               context of assignment and security group
	 */
	
	@Test(enabled = true, groups = { "regression" }, priority = 72)
	public void verifyThatCustomMetaDataFieldValueWithINTDataTypeAssign() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-52178");
	    String codingfrom = "CFINTDataType"+Utility.dynamicNameAppender();
	    String assgnCoding = "codingAssgn"+Utility.dynamicNameAppender();
		UtilityLog.info("Started Execution for prerequisite");
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
	
		// Custom Field created with INT DataType
		projectPage.addCustomFieldProjectDataType(intData, "INT");
		baseClass.stepInfo("Custom meta data field created with INT datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(intData);
		baseClass.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Creating Coding Form
		codingForm.creatingCodingFormAndAssgnCustomFields(codingfrom, intData);
		baseClass.stepInfo("Project field added to coding form in Doc view");
		
		// Session search to doc view to create assignment
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		
		assignmentPage.assignmentCreation(assgnCoding, codingfrom);
		assignmentPage.assignmentDistributingToReviewer();
		
		// logout
		loginPage.logout();
		
		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		
		// verify the coding form panel
		docViewPage.verifyCodingFormDocViewINT(intData);

		// logout
		loginPage.logout();
		
//		Doing house keeping activity
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		assignmentPage.deleteAssgnmntUsingPagination(assgnCoding);
		codingForm.deleteCodingForm(codingfrom,codingfrom);
		
		// logout
		loginPage.logout();

	}
	
	/**
	 * @Author : Baskar date: NA Modified date:28/10/2021 Modified by: Baskar 
	 * @Description :Verify that read only custom metadata field value should be 
	 *               retained on doc view when created with NVARCHAR datatype in 
	 *               context of assignment
	 */
	
	@Test(enabled = true, groups = { "regression" }, priority = 73)
	public void verifyThatCustomMetaDataFieldValueWithNVARCHARDataTypeAssign() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-52179");
	    String codingForms = "CFNVARCHARDataType"+Utility.dynamicNameAppender();
	    String assgnCoding = "codingAssgn"+Utility.dynamicNameAppender();
		UtilityLog.info("Started Execution for prerequisite");
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
	
		// Custom Field created with NVARCHAR DataType
		projectPage.addCustomFieldProjectDataType(NVARCHAR, "NVARCHAR");
		baseClass.stepInfo("Custom meta data field created with NVARCHAR datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(NVARCHAR);
		baseClass.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Creating Coding Form
		codingForm.creatingCodingFormAndAssgnCustomFields(codingForms, NVARCHAR);
		baseClass.stepInfo("Project field added to coding form in Doc view");
		
		// Session search to doc view to create assignment
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		
		assignmentPage.assignmentCreation(assgnCoding, codingForms);
		assignmentPage.assignmentDistributingToReviewer();
		
		// logout
		loginPage.logout();
		
		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		
		// verify the coding form panel
		docViewPage.verifyCodingFormDocViewINT(NVARCHAR);

		// logout
		loginPage.logout();
		
//		Doing house keeping activity
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		assignmentPage.deleteAssgnmntUsingPagination(assgnCoding);
		codingForm.deleteCodingForm(codingForms,codingForms);
		
		// logout
		loginPage.logout();

	}
	
	/**
	 * @Author : Baskar date: NA Modified date:28/10/2021 Modified by: Baskar 
	 * @Description :Verify that read only custom metadata field value should be 
	 *               retained on doc view when created with DATE datatype in
	 *               context of assignment
	 */
	
	@Test(enabled = true, groups = { "regression" }, priority = 74)
	public void verifyThatCustomMetaDataFieldValueWithDATEDatatypeAssgn() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-52180");
	    String codingForms = "CFDate"+Utility.dynamicNameAppender();
	    String assgnCoding = "codingAssgn"+Utility.dynamicNameAppender();

		UtilityLog.info("Started Execution for prerequisite");
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
	
		// Custom Field created with DATE DataType
		projectPage.addCustomFieldDataType(date, "Date");
		baseClass.stepInfo("Custom meta data field created with DATE datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(date);
		baseClass.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Creating Coding Form
		codingForm.creatingCodingFormAndAssgnCustomFields(codingForms, date);
		baseClass.stepInfo("Project field added to coding form in Doc view");
		
		// Session search to doc view to create assignment
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		
		assignmentPage.assignmentCreation(assgnCoding, codingForms);
		assignmentPage.assignmentDistributingToReviewer();
		
		// logout
		loginPage.logout();
		
		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		
		// verify the coding form panel
		docViewPage.verifyCodingFormDocViewINT(date);

		// logout
		loginPage.logout();
		
//		Doing house keeping activity
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		assignmentPage.deleteAssgnmntUsingPagination(assgnCoding);
		codingForm.deleteCodingForm(codingForms,codingForms);
		
		// logout
		loginPage.logout();

		
	}

	
	/**
	 * @Author : Baskar date: NA Modified date:28/10/2021 Modified by: Baskar 
	 * @Description :Verify that read only custom metadata field value should 
	 *               be retained on doc view when created with DATETIME
	 *               datatype in context of assignment
	 */
	
	@Test(enabled = true, groups = { "regression" }, priority = 75)
	public void verifyThatCustomMetaDataFieldValueWithDATETIMEDatatypeAssgn() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-52181");
	    String codingForms = "CFDateTime"+Utility.dynamicNameAppender();
	    String assgnCoding = "codingAssgn"+Utility.dynamicNameAppender();

		UtilityLog.info("Started Execution for prerequisite");
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
	
		// Custom Field created with DATETIME DataType
		projectPage.addCustomFieldDataType(dateTime, "DATETIME");
		baseClass.stepInfo("Custom meta data field created with DATETIME datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(dateTime);
		baseClass.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Creating Coding Form
		codingForm.creatingCodingFormAndAssgnCustomFields(codingForms, dateTime);
		baseClass.stepInfo("Project field added to coding form in Doc view");
		
		// Session search to doc view to create assignment
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		
		assignmentPage.assignmentCreation(assgnCoding, codingForms);
		assignmentPage.assignmentDistributingToReviewer();
		
		// logout
		loginPage.logout();
		
		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		
		// verify the coding form panel
		docViewPage.verifyCodingFormDocViewINT(dateTime);

		// logout
		loginPage.logout();
		
//		Doing house keeping activity
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		assignmentPage.deleteAssgnmntUsingPagination(assgnCoding);
		codingForm.deleteCodingForm(codingForms,codingForms);
		
		// logout
		loginPage.logout();

	}
	
	/**
	 * @Author : Baskar date: NA Modified date:28/10/2021 Modified by: Baskar 
	 * @Description :Verify that read only custom metadata field value should 
	 *               be retained in view coding pop up of saved stamp in 
	 *               context of security group
	 */
	
	@Test(enabled = true, groups = { "regression" }, priority = 76)
	public void verifyThatCustomMetaDataFieldValueDatatypeOnSavedStampPopUP() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-52184");
	    String codingfrom = "CFINT"+Utility.dynamicNameAppender();
	    String stampText = "New"+Utility.dynamicNameAppender();
		UtilityLog.info("Started Execution for prerequisite");
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
	
		// Custom Field created with INT DataType
		projectPage.addCustomFieldProjectDataType(intData, "INT");
		baseClass.stepInfo("Custom meta data field created with DATETIME datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(intData);
		baseClass.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Creating Coding Form
		codingForm.creatingCodingFormAndAssgnCustomFields(codingfrom, intData);
		baseClass.stepInfo("Project field added to coding form in Doc view");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(codingfrom);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// logout
		loginPage.logout();
		
		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		// verify the coding form panel
		docViewPage.viewCodingPopUp(stampText, Input.stampSelection,intData);
		
		// logout
		loginPage.logout();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		codingForm.assignCodingFormToSG("Default Project Coding Form");
//		Doing cleanup activity done in prerequities
		codingForm.deleteCodingForm(codingfrom,codingfrom);
		
		// logout
		loginPage.logout();
		
	}
	
	/**
	 * @Author : Baskar date: NA Modified date:28/10/2021 Modified by: Baskar 
	 * @Description :Verify that read only custom metadata field value should be 
	 *               retained on click of saved stamp in context of assignment
	 */
	
	@Test(enabled = true, groups = { "regression" }, priority = 77)
	public void verifyThatCustomMetaDataFieldValueWithINTDatatypeAssgn() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-52185");
	    String codingForms = "CFINT"+Utility.dynamicNameAppender();
	    String assgnCoding = "codingAssgn"+Utility.dynamicNameAppender();
	    String stampText = "New"+Utility.dynamicNameAppender();


		UtilityLog.info("Started Execution for prerequisite");
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
	
		// Custom Field created with INT DataType
		projectPage.addCustomFieldProjectDataType(intData, "INT");
		baseClass.stepInfo("Custom meta data field created with INT datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(intData);
		baseClass.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Creating Coding Form
		codingForm.creatingCodingFormAndAssgnCustomFields(codingForms, intData);
		baseClass.stepInfo("Project field added to coding form in Doc view");
		
		// Session search to doc view to create assignment
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		
		assignmentPage.assignmentCreation(assgnCoding, codingForms);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.assignmentDistributingToReviewer();
		
		// logout
		loginPage.logout();
		
		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		
		// verify the coding form panel
		docViewPage.clickOnSavedStamp(stampText, Input.stampSelection,intData);

		// logout
		loginPage.logout();
		
//		Doing house keeping activity
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		assignmentPage.deleteAssgnmntUsingPagination(assgnCoding);
		codingForm.deleteCodingForm(codingForms,codingForms);
		
		// logout
		loginPage.logout();

	}
	
	/**
	 * @Author : Baskar date: NA Modified date:28/10/2021 Modified by: Baskar 
	 * @Description :Verify that read only custom metadata field value should 
	 *               be retained in view coding pop up of saved stamp 
	 *               in context of assignment
	 */
	
	@Test(enabled = true, groups = { "regression" }, priority = 78)
	public void verifyThatCustomMetaDataFieldValueWithDATETIMEDatatypeAssgnStampPopUp() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-52186");
	    String codingForms = "CFDateTime"+Utility.dynamicNameAppender();
	    String assgnCoding = "codingAssgn"+Utility.dynamicNameAppender();
	    String stampText = "New"+Utility.dynamicNameAppender();

		UtilityLog.info("Started Execution for prerequisite");
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
	
		// Custom Field created with DATETIME DataType
		projectPage.addCustomFieldDataType(dateTime, "DATETIME");
		baseClass.stepInfo("Custom meta data field created with DATETIME datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(dateTime);
		baseClass.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Creating Coding Form
		codingForm.creatingCodingFormAndAssgnCustomFields(codingForms, dateTime);
		baseClass.stepInfo("Project field added to coding form in Doc view");
		
		// Session search to doc view to create assignment
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		
		assignmentPage.assignmentCreation(assgnCoding, codingForms);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.assignmentDistributingToReviewer();
		
		// logout
		loginPage.logout();
		
		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		
		// verify the coding form panel
		docViewPage.viewCodingPopUp(stampText, Input.stampSelection,dateTime);

		// logout
		loginPage.logout();
		
//		Doing house keeping activity
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		assignmentPage.deleteAssgnmntUsingPagination(assgnCoding);
		codingForm.deleteCodingForm(codingForms,codingForms);
		
		// logout
		loginPage.logout();

	}
	
	/**
	 * @Author : Baskar date: NA Modified date:9/11/2021 Modified by: Baskar 
	 * @Description :Verify that custom metadata field value should be retained 
	 *               on doc view when created with DATE datatype in 
	 *               context of security group
	 */
	
	@Test(enabled = true, groups = { "regression" }, priority = 79)
	public void verifyThatCustomMetaDataFieldValueDATEDataType() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-52174");
	    baseClass.stepInfo("Verify that custom metadata field value should be retained "
	    		+ "on doc view when created with DATE datatype in context of security group");
	    String codingfrom = "CFINT"+Utility.dynamicNameAppender();
		UtilityLog.info("Started Execution for prerequisite");
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
	
		// Custom Field created with DATE DataType
		projectPage.addCustomFieldDataType(date, "Date");
		baseClass.stepInfo("Custom meta data field created with DATE datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(date);
		baseClass.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// Login as Reviewer
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Creating Coding Form
		codingForm.creatingCodingFormAndAssgnCustomFields(codingfrom, date);
		baseClass.stepInfo("Project field added to coding form in Doc view");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(codingfrom);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// logout
		loginPage.logout();
		
		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		// verify the coding form panel
		docViewPage.verifyCodingFormDocViewINT(date);
		driver.waitForPageToBeReady();
		
		// logout
		loginPage.logout();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		codingForm.assignCodingFormToSG("Default Project Coding Form");
//		Doing cleanup activity done in prerequities
		codingForm.deleteCodingForm(codingfrom,codingfrom);
		
		// logout
		loginPage.logout();
		
	}
	
	/**
	 * @Author : Baskar date: NA Modified date:28/10/2021 Modified by: Baskar 
	 * @Description :Verify validation of coding form when coding form is 
	 *               created with metadata field as DateTime on saving of the stamp
	 */
	
	@Test(enabled = true, groups = { "regression" }, priority = 80)
	public void validationOfNonDateFormat() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51583");
	    baseClass.stepInfo("Verify validation of coding form when coding form is created "
	    		+ "with metadata field as DateOnly on saving of the stamp");
	    String codingfrom = "CFDateTime"+Utility.dynamicNameAppender();
	    String assgnCoding = "codingAssgn"+Utility.dynamicNameAppender();
	    String assgnColour = "ColourAssign"+Utility.dynamicNameAppender();
		UtilityLog.info("Started Execution for prerequisite");
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
	
		// Custom Field created with DateTime DataType
		projectPage.addCustomFieldDataType(dateTime, "DATETIME");
		baseClass.stepInfo("Custom meta data field created with DateTime datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(dateTime);
		baseClass.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Creating Coding Form
		codingForm.creatingCodingFormAndAssgnUsingParameter(codingfrom, dateTime,"Make it Optional");
		baseClass.stepInfo("Project field added to coding form in Doc view");
		
		// Session search to doc view to create assignment
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		
		assignmentPage.assignmentCreation(assgnCoding, codingfrom);
		assignmentPage.assignmentDistributingToReviewer();
		
		// logout
		loginPage.logout();
		
		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		
		// verify the coding form panel
		docViewPage.nonDateFormatValidation(dateTime,assgnColour,Input.stampSelection);

		// logout
		loginPage.logout();

	}
	
	/**
	 * @Author : Baskar date: NA Modified date:28/10/2021 Modified by: Baskar 
	 * @Description :Verify on click of 'Save'/'Complete button coding form should 
	 *               be validated as per the customized coding form using 
	 *               Multiple Static Text elements
	 */
	
	@Test(enabled = true, groups = { "regression" }, priority = 81)
	public void staticTextToDisplayInCodingForm() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51202");
	    baseClass.stepInfo("Verify on click of 'Save'/'Complete button coding form should be validated "
	    		+ "as per the customized coding form using Multiple Static Text elements");
	    String codingfrom = "CFStaticText"+Utility.dynamicNameAppender();
	    String assgnCoding = "codingAssgn"+Utility.dynamicNameAppender();
		String staticText="Mandatory field to  be selected";
		UtilityLog.info("Started Execution for prerequisite");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.addCodingFormWithStaticText(codingfrom,staticText);
		baseClass.stepInfo("Coding form created with static text");
		
		// Session search to doc view to create assignment
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		
		assignmentPage.assignmentCreation(assgnCoding, codingfrom);
		assignmentPage.assignmentDistributingToReviewer();
		
		// logout
		loginPage.logout();
		
		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		
		docViewPage.staticTextToBeDisplayed(staticText);

		// logout
		loginPage.logout();
		
//		Doing house keeping activity
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		assignmentPage.deleteAssgnmntUsingPagination(assgnCoding);
		codingForm.deleteCodingForm(codingfrom,codingfrom);
		
		// logout
		loginPage.logout();

	}
	
	/**
	 * @Author : Baskar date: NA Modified date:28/10/2021 Modified by: Baskar 
	 * @Description :Verify on click of 'Save'/'Complete button coding form should
	 *               be validated as per the customized coding form 
	 *               using Multiple Metadata elements
	 */
	
	@Test(enabled = true, groups = { "regression" }, priority = 82)
	public void metaDataUsingCustomizedCodingForm() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51201");
	    baseClass.stepInfo("Verify on click of 'Save'/'Complete button coding form should be "
	    		+ "validated as per the customized coding form using Multiple Metadata elements");
	    String codingfrom = "CFMetaData"+Utility.dynamicNameAppender();
	    String assgnCoding = "codingAssgn"+Utility.dynamicNameAppender();
	    String defaultAction="Make It Required";
		UtilityLog.info("Started Execution for prerequisite");
		
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
			
		
		// Custom Field created with INT DataType
		projectPage.addCustomFieldProjectDataType(intData, "INT");
		baseClass.stepInfo("Custom meta data field created with INT datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(intData);
		baseClass.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.metaDataValidation(codingfrom,intData,defaultAction);
		baseClass.stepInfo("Coding form created with metadata fieldValue");
		
		// Session search to doc view to create assignment
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		
		assignmentPage.assignmentCreation(assgnCoding, codingfrom);
		assignmentPage.assignmentDistributingToReviewer();
		
		// logout
		loginPage.logout();
		
		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		
		docViewPage.verifyCustomizedMetaDataCodingForm(intData);

		// logout
		loginPage.logout();
		
//		Doing house keeping activity
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		assignmentPage.deleteAssgnmntUsingPagination(assgnCoding);
		codingForm.deleteCodingForm(codingfrom,codingfrom);
		
		// logout
		loginPage.logout();

	}
	
	/**
	 * @Author : Iyappan.Kasinathan 
	 * @Description :Verify on click of 'Save'/'Complete button coding form should be validated as per 
	 *                  the customized coding form using multiple tags elements
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 83)
	public void verifyCustomizedCodingFormUsingMultipleTags() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51199");
	    baseClass.stepInfo("Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form using multiple tags elements");
	    String codingform = "CFTags"+Utility.dynamicNameAppender();
	    String assgnCoding = "codingAssgn"+Utility.dynamicNameAppender();
	    String tagName =null;
	    String commentName = null;
	    String metadataName = null;
	    String defaultAction="Make It Required";
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	codingForm.createCodingFormUsingTwoObjects(codingform, tagName, commentName, metadataName, "tag");
	 	codingForm.addcodingFormAddButton();
	 	codingForm.selectDefaultActions(0,defaultAction);
	 	codingForm.enterErrorAndHelpMsg(0,"Yes","Help for testing","Error for testing");
	 	String expectedFirstObjectName = codingForm.getCFObjectsLabel(0);
	 	System.out.println(expectedFirstObjectName);
	 	codingForm.selectDefaultActions(1,defaultAction);
	 	codingForm.enterErrorAndHelpMsg(1,"Yes","Help for testing","Error for testing");
	 	String expectedSecondObjectName = codingForm.getCFObjectsLabel(1);
	 	System.out.println(expectedSecondObjectName);
	 	codingForm.saveCodingForm();
	 	//create assignment
	 	sessionSearch.basicContentSearch("null");
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assgnCoding, codingform);
		assignmentPage.add2ReviewerAndDistribute();
		//Impersonate to reviewer
		baseClass.impersonateRMUtoReviewer();
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		docViewPage.verifyCodingFormName(codingform);
		//verify tags are disbled
		docViewPage.verifyTagsAreDisabledInPreviewBox(0);
		docViewPage.verifyTagsAreDisabledInPreviewBox(1);
		//verify tag names
		docViewPage.verifyCodingFormTagNameInDocviewPg(0,expectedFirstObjectName);
		docViewPage.verifyCodingFormTagNameInDocviewPg(1,expectedSecondObjectName);		
		loginPage.logout();
		//Login as reviewer
		loginPage.loginToSightLine(Input.rev1userName,Input.rev1password);
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		docViewPage.verifyCodingFormName(codingform);
		//verify tags are disbled
		docViewPage.verifyTagsAreDisabledInPreviewBox(0);
		docViewPage.verifyTagsAreDisabledInPreviewBox(1);
		//verify tag names
		docViewPage.verifyCodingFormTagNameInDocviewPg(0,expectedFirstObjectName);
		docViewPage.verifyCodingFormTagNameInDocviewPg(1,expectedSecondObjectName);
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
	 * @Description :Verify on click of 'Save'/'Complete button coding form should be validated as per the
	 *                         customized coding form for editable metadata fields
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 84)
	public void verifyCustomizedCodingFormUsingMultipleMetadatas() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51196");
	    baseClass.stepInfo("Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form"+
	                        " for editable metadata fields");
	    String codingform = "CFMetadatas"+Utility.dynamicNameAppender();
	    String assgnCoding = "codingAssgn"+Utility.dynamicNameAppender();
	    String tagName =null;
	    String commentName = null;
	    String metadataName = null;
	    String defaultAction="Make It Required";
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	codingForm.createCodingFormUsingTwoObjects(codingform, tagName, commentName, metadataName, "metadata");
	 	codingForm.addcodingFormAddButton();
	 	codingForm.selectDefaultActions(0,defaultAction);
	 	codingForm.enterErrorAndHelpMsg(0,"Yes","Help for testing","Error for testing");
	 	String expectedFirstObjectName = codingForm.getCFObjectsLabel(0);
	 	System.out.println(expectedFirstObjectName);
	 	codingForm.selectDefaultActions(1,defaultAction);
	 	codingForm.enterErrorAndHelpMsg(1,"Yes","Help for testing","Error for testing");
	 	String expectedSecondObjectName = codingForm.getCFObjectsLabel(1);
	 	System.out.println(expectedSecondObjectName);
	 	codingForm.saveCodingForm();
	 	//create assignment and asign to coding form
	 	sessionSearch.basicContentSearch("null");
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assgnCoding, codingform);
		assignmentPage.add2ReviewerAndDistribute();
		//Impersonate as reviewer
		baseClass.impersonateRMUtoReviewer();
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		//validations in docviewPage
		docViewPage.verifyCodingFormName(codingform);
		docViewPage.verifyCustomizedMetaDataCodingForm(intData);
		docViewPage.verifyCodingFormNameInDocviewPg(0,expectedFirstObjectName+"*");
		docViewPage.verifyCodingFormNameInDocviewPg(1,expectedSecondObjectName+"*");		
		docViewPage.verifyMetaDataHelpnErrorMsgInDocviewPg(expectedFirstObjectName,"Help for testing","Error for testing");
		docViewPage.verifyMetaDataHelpnErrorMsgInDocviewPg(expectedSecondObjectName,"Help for testing","Error for testing");
		loginPage.logout();
		//LogIn as reviewer
		loginPage.loginToSightLine(Input.rev1userName,Input.rev1password);
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		//validations in docviewPage
		docViewPage.verifyCodingFormName(codingform);
		docViewPage.verifyCustomizedMetaDataCodingForm(intData);		
		docViewPage.verifyCodingFormNameInDocviewPg(0,expectedFirstObjectName+"*");
		docViewPage.verifyCodingFormNameInDocviewPg(1,expectedSecondObjectName+"*");		
		docViewPage.verifyMetaDataHelpnErrorMsgInDocviewPg(expectedFirstObjectName,"Help for testing","Error for testing");
		docViewPage.verifyMetaDataHelpnErrorMsgInDocviewPg(expectedSecondObjectName,"Help for testing","Error for testing");
		loginPage.logout();
		//Delete assignment and codingform
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignmentPage.deleteAssgnmntUsingPagination(assgnCoding);
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.deleteCodingForm(codingform,codingform);	
		codingForm.verifyCodingFormIsDeleted(codingform);
		loginPage.logout();
	}
	/**
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Verify on click of 'Save'/'Complete button coding form should be validated as per
	 *                the customized coding form using radio group
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 85)
	public void verifyCustomizedCodingFormUsingRadioGroup() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51197");
	    baseClass.stepInfo("Verify on click of 'Save'/'Complete button coding form should be validated as per "+
	                                "the customized coding form using radio group");
	    String codingform = "CFTagRadioGrp"+Utility.dynamicNameAppender();
	    String assgnCoding = "codingAssgn"+Utility.dynamicNameAppender();
	    String tagName = "cfTag"+Utility.dynamicNameAppender();
	    String defaultAction="Make It Required";
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
        //create tag
	 	tagsAndFoldersPage.CreateTag(tagName, Input.securityGroup);
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	driver.waitForPageToBeReady();
	 	baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
	 	codingForm.getAddNewCodingFormBtn().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
		baseClass.waitForElement(codingForm.getCodingForm_Tag(tagName));
		codingForm.getCodingForm_Tag(tagName).waitAndClick(10);
	 	codingForm.addcodingFormAddButton();
	 	codingForm.selectRadioOrCheckGroup("radio item");
	 	codingForm.enterErrorAndHelpMsg(0,"No","Help for testing",null);
	 	String expectedFirstObjectName = codingForm.getCFObjectsLabel(0);
	 	System.out.println(expectedFirstObjectName);
	 	codingForm.selectDefaultActions(1,defaultAction);
	 	codingForm.enterErrorAndHelpMsg(1,"Yes","Help for testing","Error for testing");
	 	codingForm.saveCodingForm();
	 	//create assignment and assign to codingform
	 	sessionSearch.basicContentSearch("null");
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assgnCoding, codingform);
		assignmentPage.add2ReviewerAndDistribute();
		//Impersonated as reviewer
		baseClass.impersonateRMUtoReviewer();
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		//Validations in docview page
		docViewPage.verifyCodingFormName(codingform);
		docViewPage.verifyCustomizedMetaDataCodingForm(intData);
		docViewPage.validateRadioOrCheckGroupInDocviewPg("radio-group");
		docViewPage.verifyCodingFormRadioGrpTagNameInDocviewPg(0,expectedFirstObjectName);		
		docViewPage.verifyHelpnErrorMsgOfRadioGrpTagInDocviewPg(0,"Help for testing","Error for testing");		
		loginPage.logout();
		//LogIn as reviewer
		loginPage.loginToSightLine(Input.rev1userName,Input.rev1password);
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		//Validations in docview page
		docViewPage.verifyCodingFormName(codingform);
		docViewPage.verifyCustomizedMetaDataCodingForm(intData);		
		docViewPage.validateRadioOrCheckGroupInDocviewPg("radio-group");
		docViewPage.verifyCodingFormRadioGrpTagNameInDocviewPg(0,expectedFirstObjectName);		
		docViewPage.verifyHelpnErrorMsgOfRadioGrpTagInDocviewPg(0,"Help for testing","Error for testing");		
		loginPage.logout();
		//Delete assignments,codingform and tag
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignmentPage.deleteAssgnmntUsingPagination(assgnCoding);		
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.deleteCodingForm(codingform,codingform);	
		codingForm.verifyCodingFormIsDeleted(codingform);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFoldersPage.deleteAllTags(tagName);
		loginPage.logout();
	}
	/**
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Verify on click of 'Save'/'Complete button coding form should be validated as 
	 *                per the customized coding form using check group
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 86)
	public void verifyCustomizedCodingFormUsingCheckGroup() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51198");
	    baseClass.stepInfo("Verify on click of 'Save'/'Complete button coding form should be validated as per "+
	                              " the customized coding form using check group");
	    String codingform = "CFTagCheckGrp"+Utility.dynamicNameAppender();
	    String assgnCoding = "codingAssgn"+Utility.dynamicNameAppender();
	    String tagName = "cfTag"+Utility.dynamicNameAppender();
	    String defaultAction="Make It Required";
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
        //create tag
	 	tagsAndFoldersPage.CreateTag(tagName, Input.securityGroup);
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	driver.waitForPageToBeReady();
	 	baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
	 	codingForm.getAddNewCodingFormBtn().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
		baseClass.waitForElement(codingForm.getCodingForm_Tag(tagName));
		codingForm.getCodingForm_Tag(tagName).waitAndClick(10);
	 	codingForm.addcodingFormAddButton();
	 	codingForm.selectRadioOrCheckGroup("check item");
	 	codingForm.enterErrorAndHelpMsg(0,"No","Help for testing",null);
	 	String expectedFirstObjectName = codingForm.getCFObjectsLabel(0);
	 	System.out.println(expectedFirstObjectName);
	 	codingForm.selectDefaultActions(1,defaultAction);
	 	codingForm.enterErrorAndHelpMsg(1,"Yes","Help for testing","Error for testing");
	 	codingForm.saveCodingForm();
	 	//Create assignment and assign the coding form
	 	sessionSearch.basicContentSearch("null");
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assgnCoding, codingform);
		assignmentPage.add2ReviewerAndDistribute();
		//Impersonated as reviewer
		baseClass.impersonateRMUtoReviewer();
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		//Validations in docview page
		docViewPage.verifyCodingFormName(codingform);
		docViewPage.verifyCustomizedMetaDataCodingForm(intData);
		docViewPage.validateRadioOrCheckGroupInDocviewPg("check-group");
		docViewPage.verifyCodingFormCheckGrpTagNameInDocviewPg(0,expectedFirstObjectName);		
		docViewPage.verifyHelpnErrorMsgOfCheckGrpTagInDocviewPg(0,"Help for testing","Error for testing");		
		loginPage.logout();
		//LogIn as reviewer
		loginPage.loginToSightLine(Input.rev1userName,Input.rev1password);
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		//Validations in docview page
		docViewPage.verifyCodingFormName(codingform);
		docViewPage.verifyCustomizedMetaDataCodingForm(intData);		
		docViewPage.validateRadioOrCheckGroupInDocviewPg("check-group");
		docViewPage.verifyCodingFormCheckGrpTagNameInDocviewPg(0,expectedFirstObjectName);		
		docViewPage.verifyHelpnErrorMsgOfCheckGrpTagInDocviewPg(0,"Help for testing","Error for testing");		
		loginPage.logout();
		//Delete assignment,codingform and tag
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignmentPage.deleteAssgnmntUsingPagination(assgnCoding);		
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.deleteCodingForm(codingform,codingform);	
		codingForm.verifyCodingFormIsDeleted(codingform);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFoldersPage.deleteAllTags(tagName);
		loginPage.logout();
	}
	
	
	
	
	/**
	 * @Author : Iyappan.Kasinathan 
	 * @Description :Verify on click of 'Save'/'Complete button coding form should be validated as per the 
	 *               customized coding form using multiple comments elements
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 87)
	public void verifyCustomizedCodingFormUsingMultipleComments() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51200");
	    baseClass.stepInfo("Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding"+
	                                   " form using multiple comments elements");
	    String codingform = "CFComments"+Utility.dynamicNameAppender();
	    String assgnCoding = "codingAssgn"+Utility.dynamicNameAppender();
	    String tagName =null;
	    String commentName = null;
	    String metadataName = null;
	    String defaultAction="Make It Required";
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	codingForm.createCodingFormUsingTwoObjects(codingform, tagName, commentName, metadataName, "comment");
	 	codingForm.addcodingFormAddButton();
	 	codingForm.selectDefaultActions(0,defaultAction);
	 	codingForm.enterErrorAndHelpMsg(0,"Yes","Help for testing","Error for testing");
	 	String expectedFirstObjectName = codingForm.getCFObjectsLabel(0);
	 	System.out.println(expectedFirstObjectName);
	 	codingForm.selectDefaultActions(1,defaultAction);
	 	codingForm.enterErrorAndHelpMsg(1,"Yes","Help for testing","Error for testing");
	 	String expectedSecondObjectName = codingForm.getCFObjectsLabel(1);
	 	System.out.println(expectedSecondObjectName);
	 	codingForm.saveCodingForm();
	 	//create assignment
	 	sessionSearch.basicContentSearch("null");
		sessionSearch.bulkAssign();
		System.out.println(assgnCoding);
		assignmentPage.assignmentCreation(assgnCoding, codingform);
		assignmentPage.add2ReviewerAndDistribute();
		//impersonate as reviewer
		baseClass.impersonateRMUtoReviewer();
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		//validations on docview page
		baseClass.waitForElement(docViewPage.getCodingFormHelpText(expectedFirstObjectName));
		docViewPage.getCodingFormHelpText(expectedFirstObjectName).Clear();
		docViewPage.getCodingFormHelpText(expectedFirstObjectName).Clear();
		docViewPage.verifyCodingFormName(codingform);
		docViewPage.verifyCustomizedMetaDataCodingForm(intData);
		docViewPage.verifyCodingFormNameInDocviewPg(0,expectedFirstObjectName+"*");
		docViewPage.verifyCodingFormNameInDocviewPg(1,expectedSecondObjectName+"*");		
		docViewPage.verifyHelpnErrorMsgInDocviewPg(expectedFirstObjectName,"Help for testing","Error for testing");
		docViewPage.verifyHelpnErrorMsgInDocviewPg(expectedSecondObjectName,"Help for testing","Error for testing");
		loginPage.logout();
		//login as reviewer
		loginPage.loginToSightLine(Input.rev1userName,Input.rev1password);
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		//validations on docview page
		baseClass.waitForElement(docViewPage.getCodingFormHelpText(expectedFirstObjectName));
		docViewPage.verifyCodingFormName(codingform);
		docViewPage.getCodingFormHelpText(expectedFirstObjectName).Clear();
		docViewPage.getCodingFormHelpText(expectedFirstObjectName).Clear();
		docViewPage.verifyCustomizedMetaDataCodingForm(intData);		
		docViewPage.verifyCodingFormNameInDocviewPg(0,expectedFirstObjectName+"*");
		docViewPage.verifyCodingFormNameInDocviewPg(1,expectedSecondObjectName+"*");		
		docViewPage.verifyHelpnErrorMsgInDocviewPg(expectedFirstObjectName,"Help for testing","Error for testing");
		docViewPage.verifyHelpnErrorMsgInDocviewPg(expectedSecondObjectName,"Help for testing","Error for testing");		
		loginPage.logout();
		//delete codingform
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		System.out.println(assgnCoding);
		assignmentPage.deleteAssgnmntUsingPagination(assgnCoding);
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.deleteCodingForm(codingform,codingform);	
		codingForm.verifyCodingFormIsDeleted(codingform);
		loginPage.logout();
	}
	
	
	
	

	
	

	
	/**
	 * @Author : Baskar date:23/11/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify coding form for a document in an assignment after
	 *              applying coding stamp when different coding form is applied to
	 *              assignment and security group and document is viewed from basic
	 *              search
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 88)
	public void differentCodingStampShouldDisplayToSGAndAssgn() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-51004");
		baseClass.stepInfo("Verify coding form for a document in an assignment after applying "
				+ "       coding stamp when different coding form is applied to "
				+ "assignment and security group and document is viewed from basic search");
		String AssignStamp = "StampPopUp" + Utility.dynamicNameAppender();
		String assgnColour = "AssignC" + Utility.dynamicNameAppender();
		String codingFormName = "NewCF" + Utility.dynamicNameAppender();
		// Login As Reviewer
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

//		Creating new coding form
		codingForm.differentCodingForm(codingFormName);

//	    Assgn coding form to SG
		codingForm.assignCodingFormToSG(codingFormName);

		// searching document for assignmnet creation
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(AssignStamp, Input.codingFormName);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.assignmentDistributingToReviewer();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rev1userName + "'");

		// selecting the assignment
		assignmentPage.SelectAssignmentByReviewer(AssignStamp);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		String docId = docViewPage.stampForSameCodingForm(assgnColour, Input.stampSelection);

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		docViewPage.differentCoding(docId);

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

//		Doing house keeping activity
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		assignmentPage.deleteAssgnmntUsingPagination(AssignStamp);
		codingForm.assignCodingFormToSG("Default Project Coding Form");
		codingForm.deleteCodingForm(codingFormName, codingFormName);

		// logout
		loginPage.logout();
	}	
	/**
	 * @Author : Baskar date: 26/11/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify that for RMU/Reviewer user 'Code same as last' 
	 *                should not be enable on click of 'Save'/'Save and Next' 
	 *                when coding form created with static text
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 89)
	public void staticTextWarningMsgUsingSave() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52147");
		baseClass.stepInfo("Verify that for RMU/Reviewer user 'Code same as last' "
				+ "should not be enable on click of 'Save'/'Save and Next' "
				+ "when coding form created with static text");
		String staticText = "staticText" + Utility.dynamicNameAppender();
		String cfName = "CfStatic" + Utility.dynamicNameAppender();
		
		// Login as a Rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		driver.waitForPageToBeReady();

		// Creating Coding Form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.creationOfStaticText(staticText,cfName);
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Project field added to coding form in Doc view");

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		docViewPage.selectPureHit();

		baseClass.stepInfo("Searching non-audio documents based on search string");
		sessionSearch.advancedNewContentSearch1(Input.searchStringStar);
		sessionSearch.ViewInDocView();
		
		// verify warning message
		docViewPage.warningMessageStaticUsingSave();
		
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.deleteCodingForm(cfName,cfName);
		codingForm.assignCodingFormToSG("Default Project Coding Form");
		
		driver.waitForPageToBeReady();
		loginPage.logout();
	}
	
	/**
	 * @Author : Baskar date:29/11/21 Modified date: NA Modified by: Baskar 
	 * @Description : [TC reference RPMXCON-51205] Verify on click of 'Save and Next' 
	 *                coding form should be validated as per the customized coding form 
	 *                for Editable Metadata objects
	 */
	
	@Test(enabled = true, groups = { "regression" }, priority = 90)
	public void  verifyEditableMetaDataWithOptional() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52102");
		baseClass.stepInfo("Verify on click of 'Save and Next' coding form should be "
				+ "validated as per the customized coding form for Editable Metadata objects");
		String cfName = "CF"+Utility.dynamicNameAppender();
		String passingValue = "Edit"+Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		String textBox=codingForm.createWithOneMetaData(cfName);
		baseClass.stepInfo("Coding form saved with Editable metadata value with optional mode");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		
		docViewPage.verifyMetaDataTab(textBox, passingValue);

		codingForm.assignCodingFormToSG("Default Project Coding Form");
		codingForm.deleteCodingForm(cfName, cfName);
		
		// logout
		loginPage.logout();
	}
	
	/**
	 * @Author : Baskar date:29/11/21 Modified date: NA Modified by: Baskar 
	 * @Description : [TC reference RPMXCON-51210] Verify validation of coding form on 
	 *                click of 'Save and Next' using Tag/Comments/Metadata objects 
	 *                along with Check/Radio Group and Radio Item
	 */
	
	@Test(enabled = true, groups = { "regression" }, priority = 91)
	public void  verifyCustomizedUsingSaveAndNext() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52101");
		baseClass.stepInfo("Verify validation of coding form on "
				+ "click of 'Save and Next' using Tag/Comments/Metadata objects along "
				+ "with Check/Radio Group and Radio Item");
		String cfName = "CF"+Utility.dynamicNameAppender();
		String error="Error"+Utility.dynamicNameAppender();
		String help="Help"+Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.errorMsgForComments(cfName,help,error);
		baseClass.stepInfo("Coding form created with tag/comment/metadata");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		
		docViewPage.validRequiredComment(error);
		
		codingForm.assignCodingFormToSG("Default Project Coding Form");
		driver.waitForPageToBeReady();
		codingForm.deleteCodingForm(cfName,cfName);
		
		// logout
		loginPage.logout();
	}
	
	/**
	 * @Author : Baskar date:1/12/21 Modified date: NA Modified by: Baskar 
	 * @Description : Coding form child window: Verify that user should not save the 
	 *                coding stamp without selecting coding form for required fields 
	 *                in context of security group
	 */
	
	@Test(enabled = true, groups = { "regression" }, priority = 92)
	public void  validateChildwindowForRequiredTag() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52045");
		baseClass.stepInfo("Verify that user should not save the "
				+ "coding stamp without selecting coding form for required fields "
				+ "in context of security group");
		String cfName = "CF"+Utility.dynamicNameAppender();
		String fieldText="Stamp"+Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.commentRequired(cfName);
		baseClass.stepInfo("Coding form created with comments required tag");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		
		docViewPage.validateWithoutEditUsingStamp(fieldText);
		
		codingForm.assignCodingFormToSGAlert("Default Project Coding Form");
		driver.waitForPageToBeReady();
		codingForm.deleteCodingForm(cfName,cfName);
		
		// logout
		loginPage.logout();
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-52077
	 * @Description:To Verify on click of 'Save and Next' button coding form should be validated as per the customized coding form for comment element in context of security group. 
	 */
	@Test(enabled = true,groups = { "regression" }, priority = 93)
	public void  validateChildwindowErrorMsg() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52077");
		
		baseClass.stepInfo("Verify that user should not save the "
				+ "coding stamp without selecting coding form for required fields "
				+ "in context of security group");
		String cfName = "CF"+Utility.dynamicNameAppender();
		String fieldText="Stamp"+Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.saveCodingFormWithErrorAndTextmsg(cfName);
		baseClass.stepInfo("Coding form created with comments required tag");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		
		docViewPage.verifyErrorMsg();
		docViewPage.openChildWindow();
		
		baseClass.passedStep("Verified on click of 'Save and Next' button coding form should be validated as per the customized coding form for comment element in context of security group ");
		
		codingForm.assignCodingFormToSG("Default Project Coding Form");
		driver.waitForPageToBeReady();
		codingForm.deleteCodingForm(cfName,cfName);
		
		// logout
		loginPage.logout();
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-52078
	 * @Description:To Verify on click of 'Save and Next' button coding form should be validated as per the customized coding form for editable metadata fields in context of security group.
	 */
	@Test(enabled = true,groups = { "regression" }, priority = 94)
	public void  validateChildwindowErrorMsgInMeta() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52078");
		
		baseClass.stepInfo("Verify that user should not save the "
				+ "coding stamp without selecting coding form for required fields "
				+ "in context of security group");
		String cfName = "CF"+Utility.dynamicNameAppender();
		String fieldText="Stamp"+Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.saveCodingFormWithErrorAndTextmsgInMetaData(cfName);
		baseClass.stepInfo("Coding form created with comments required tag");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		
		docViewPage.verifyErrorMsgforMetaData();
		docViewPage.openChildWindowInMeta();
		
		baseClass.passedStep("Verified on click of 'Save and Next' button coding form should be validated as per the customized coding form for editable metadata fields in context of security group");		
		codingForm.assignCodingFormToSG("Default Project Coding Form");
		driver.waitForPageToBeReady();
		codingForm.deleteCodingForm(cfName,cfName);
		
		// logout
		loginPage.logout();
	}
	
	/**
	 * @Author : Baskar date:08/12/21 Modified date: NA Modified by: Baskar 
	 * @Description : Verify that a coding form appears correctly and properly in 
	 *                doc view screen - Search >> Doc View
	 */
	
	@Test(enabled = true, groups = { "regression" }, priority = 95)
	public void  validateCodingFormAppear() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51460");
		baseClass.stepInfo("Verify that a coding form appears correctly "
				+ "and properly in doc view screen - Search >> Doc View");
		String cfName = "CF"+Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.commentRequired(cfName);
		baseClass.stepInfo("Coding form created with comments");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		
		codingForm.ViewCFinDocViewThrSearch(cfName);
		docViewPage.verifyPanels();
		
		codingForm.assignCodingFormToSG("Default Project Coding Form");
		driver.waitForPageToBeReady();
		codingForm.deleteCodingForm(cfName,cfName);
		
		// logout
		loginPage.logout();
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-52080
	 * @Description : To Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form using check group.
	 */
	@Test(enabled = true,groups = { "regression" }, priority = 96)
	public void verifyCustomizedCodingFormUsingCheckGroupInDocView() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-52080");
	    baseClass.stepInfo("Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form using check groupthe customized coding form using radio group");
	    String cfName = "CFTagCheckGrp"+Utility.dynamicNameAppender();
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
       
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	codingForm.saveCodingFormWithCheckGroup(cfName);
	 	baseClass.stepInfo("Coding form created with Check Group in required tag");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		
		docViewPage.verifyErrorMsgforCheckGroup();

		docViewPage.openChildWindowInCheckGroup();

		baseClass.passedStep("Verified on click of 'Save'/'Complete button coding form should be validated as per the customized coding form using check group");
		codingForm.assignCodingFormToSGAlert("Default Project Coding Form");
		driver.waitForPageToBeReady();
		codingForm.deleteCodingForm(cfName,cfName);
		
		// logout
		loginPage.logout();
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-52087
	 * @Description : To  Verify on click of 'Save and Next' button coding form should be validated as per the customized coding form using Tags and Check group combined with Check Item in context of security group.
	 */
	@Test(enabled=false,groups = { "regression" }, priority = 97)
	public void verifyCustomizedCodingFormUsingTagAndCheckGroupInDocView() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-52087");
	    baseClass.stepInfo(" Verify on click of 'Save and Next' button coding form should be validated as per the customized coding form using Tags and Check group combined with Check Item in context of security group");
	    String cfName = "CFTagRadioGrp"+Utility.dynamicNameAppender();
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
       
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	codingForm.saveCodingForm2TagsWithCheckGroup(cfName);
	 	baseClass.stepInfo("Coding form created 2 tags with Check Group in hidden and required tag");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		
		docViewPage.verifyParentWindowCursor();
		docViewPage.verifyChildWindowCursor();

		baseClass.passedStep(" Verified on click of 'Save and Next' button coding form should be validated as per the customized coding form using Tags and Check group combined with Check Item in context of security group");
		codingForm.assignCodingFormToSG("Default Project Coding Form");
		driver.waitForPageToBeReady();
		codingForm.deleteCodingForm(cfName,cfName);
		
		// logout
		loginPage.logout();
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-52079
	 * @Description : To Verify on click of 'Save and Next' button coding form should be validated as per the customized coding form for comment element in context of security group.
	 */
	@Test(enabled = true,groups = { "regression" }, priority = 98)
	public void verifyCustomizedCodingFormUsingRadioGroupInDocView() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-52079");
	    baseClass.stepInfo("Verify on click of 'Save and Next' button coding form should be validated as per the customized coding form for comment element in context of security group"+
	                                "the customized coding form using radio group");
	    String cfName = "CFTagRadioGrp"+Utility.dynamicNameAppender();
	    String tagName="cftag"+Utility.dynamicNameAppender();
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
	 	//create tag
	 	tagsAndFoldersPage.CreateTag(tagName, Input.securityGroup);
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	codingForm.saveCodingFormWithRedioGroup(cfName,tagName);
	 	baseClass.stepInfo("Coding form created with Radio Group in required tag");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		
		docViewPage.verifyErrorMsgforRadioGroup();

		docViewPage.openChildWindowInRadioGroup();

		baseClass.passedStep("Verified on click of 'Save and Next' button coding form should be validated as per the customized coding form for comment element in context of security group.");
		codingForm.assignCodingFormToSG("Default Project Coding Form");
		driver.waitForPageToBeReady();
		codingForm.deleteCodingForm(cfName,cfName);
		
		// logout
		loginPage.logout();
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
	@Test(enabled = true, dataProvider = "rmuAndrev", groups = { "regression" }, priority = 99)
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
	
	
	
	/**
	 * @Author : Baskar date: NA Modified date:10/12/2021 Modified by: Baskar 
	 * @Description :Verify validation of coding form when coding form is created 
	 *               with metadata field as DateOnly on click of 'Save'
	 */
	
	@Test(enabled = true, groups = { "regression" }, priority = 100)
	public void nonDateFormatForDateUsingSave() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51580");
	    baseClass.stepInfo("Verify validation of coding form when coding form is "
	    		+ "created with metadata field as DateOnly on click of 'Save'");
	    String codingForms = "CFDate"+Utility.dynamicNameAppender();
	    String assgnCoding = "codingAssgn"+Utility.dynamicNameAppender();

		UtilityLog.info("Started Execution for prerequisite");
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
	
		// Custom Field created with DATE DataType
		projectPage.addCustomFieldDataType(date, "Date");
		baseClass.stepInfo("Custom meta data field created with DATE datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(date);
		baseClass.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Creating Coding Form
		codingForm.creatingCodingFormAndAssgnUsingParameter(codingForms, date,Input.optional);
		baseClass.stepInfo("Project field added to coding form in Doc view");
		
		// Session search to doc view to create assignment
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		
		assignmentPage.assignmentCreation(assgnCoding, codingForms);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.assignmentDistributingToReviewer();
		
		// logout
		loginPage.logout();
		
		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		
		// verify the coding form panel for non-date format
		docViewPage.nonDateFormatValidationUsingSave(date);

		// logout
		loginPage.logout();
	}
	
	/**
	 * @Author : Baskar date:12/10/2021 Modified date:12/10/2021 Modified by: Baskar 
	 * @Description :Verify validation of coding form when coding form is created with 
	 *               metadata field as DateTime on click of 'Save' outside of an assignment
	 */
	
	@Test(enabled = true, groups = { "regression" }, priority = 101)
	public void validationUsingAlphaAndNonDateFormat() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51607");
	    baseClass.stepInfo("Verify validation of coding form when coding form is created with "
	    		+ "metadata field as DateTime on click of 'Save' outside of an assignment");
	    String assgnCoding = "codingAssgn"+Utility.dynamicNameAppender();

		UtilityLog.info("Started Execution for prerequisite");
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
	
		// Custom Field created with DATE DataType
		projectPage.addCustomFieldDataType(date, "Date");
		projectPage.addCustomFieldDataType(dateTime, "DATETIME");
		projectPage.addCustomFieldProjectDataType(intData, "INT");
		baseClass.stepInfo("Custom meta data field created with datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(date);
		securityGroupPage.addProjectFieldtoSG(dateTime);
		securityGroupPage.addProjectFieldtoSG(intData);
		baseClass.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Creating Coding Form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.creatingCodingThreeDataType(assgnCoding, date,dateTime,intData);
		baseClass.stepInfo("Project field added to coding form in Doc view");
		
		// assign coding form to SG
		codingForm.assignCodingFormToSG(assgnCoding);
		
		// Session search to doc view to create assignment
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocViews();
		
		// verify the coding form panel for non-date format and alpha character
		docViewPage.nonDateFormatAndAlphaValidationUsingSave(date,dateTime,intData);

		// logout
		loginPage.logout();
	}
	
	/**
	 * @Author : Baskar date:10/12/21 Modified date: NA Modified by: Baskar 
	 * @Description : Verify on click of 'Save and Next' button coding form should be 
	 *                validated as per the default selected action for the coding form 
	 *                in context of security group
	 */
	
	@Test(enabled = true, groups = { "regression" }, priority = 102)
	public void  validateForRequiredTag() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52074");
		baseClass.stepInfo("Verify on click of 'Save and Next' button coding form "
				+ "should be validated as per the default selected action "
				+ "for the coding form in context of security group");
		String cfName = "CF"+Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.commentRequired(cfName);
		baseClass.stepInfo("Coding form created with comments required tag");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		
		docViewPage.validateErrorMsgRequiredComment();
		
		codingForm.assignCodingFormToSG("Default Project Coding Form");
		driver.waitForPageToBeReady();
		codingForm.deleteCodingForm(cfName,cfName);
		
		// logout
		loginPage.logout();
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-52089
	 * @Description : To  Verify on click of 'Save and Next' button coding form should be validated as per the customized coding form using Tag objects along with "Selected" condition in context of security group.
	 */
	@Test(enabled=false,groups = { "regression" }, priority = 103)
	public void verifyCodingFormWithNotSelectableAndOption() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-52089");
	    baseClass.stepInfo(" Verify on click of 'Save and Next' button coding form should be validated as per the customized coding form using Tag objects along with \"Selected\" condition in context of security group");
	    String cfName = "CFTag"+Utility.dynamicNameAppender();
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
       
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	codingForm.saveCodingForm2TagsWithNotSelectableAndOption(cfName);
	 	baseClass.stepInfo("Coding form created 2 tags with not Selectable And Option");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		
		docViewPage.verifyParentWindowCursor();
		docViewPage.verifyChildWindowCursor();

		baseClass.passedStep(" Verified on click of 'Save and Next' button coding form should be validated as per the customized coding form using Tag objects along with \"Selected\" condition in context of security group");
		codingForm.assignCodingFormToSGAlert("Default Project Coding Form");
		driver.waitForPageToBeReady();
		codingForm.deleteCodingForm(cfName,cfName);
		
		// logout
		loginPage.logout();
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-52090
	 * @Description : To   Verify on click of 'Save and Next' button coding form should be validated as per the customized coding form using Comments objects along with "Not Selected" condition in context of security group.
	 */
	@Test(enabled = true,groups = { "regression" }, priority = 104)
	public void verifyCodingFormCommentWithOptionalAndRequired() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-52090");
	    baseClass.stepInfo(" Verify on click of 'Save and Next' button coding form should be validated as per the customized coding form using Comments objects along with \"Not Selected\" condition in context of security group");
	    String cfName = "CFComment"+Utility.dynamicNameAppender();
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
       
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	codingForm.saveCodingForm2CommentWithOptionalAndRequired(cfName);
	 	baseClass.stepInfo("Coding form created 2 tags with not Selectable And Option");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		
		docViewPage.verifyErrorMsgInDocView();
		docViewPage.openChildWindowInCheckGroup();

		baseClass.passedStep(" Verified on click of 'Save and Next' button coding form should be validated as per the customized coding form using Comments objects along with \"Not Selected\" condition in context of security group");
		codingForm.assignCodingFormToSGAlert("Default Project Coding Form");
		driver.waitForPageToBeReady();
		codingForm.deleteCodingForm(cfName,cfName);
		
		// logout
		loginPage.logout();
	}
	

	
	/**
	 * @Author : Baskar date:13/12/21 Modified date: NA Modified by: Baskar 
	 * @Description : To verify that if coding form is not associated to the 
	 *                security group, document should not be saved.
	 */
	
	@Test(enabled = true, groups = { "regression" }, priority = 105)
	public void validateCodingFormWhenNotAssgn() throws InterruptedException {
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-50989");
		baseClass.stepInfo("To verify that if coding form is not associated "
				+ "to the security group, document should not be saved.");
		String cfName = "CF"+Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.commentRequired(cfName);
		baseClass.stepInfo("Coding form created with comments required tag");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		codingForm.deleteCodingForm(cfName,cfName);
		
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		
		docViewPage.noDefaultCodingForm();
		softAssertion.assertAll();
		
		codingForm.assignCodingFormToSGAlert("Default Project Coding Form");
		
		// logout
		loginPage.logout();
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-52091
	 * @Description : To  Verify on click of 'Save and Next' button coding form should be validated as per the customized coding form using Tags objects along with Selected and "Not Selected" condition in context of security group.
	 */
	@Test(enabled = true,groups = { "regression" }, priority = 106)
	public void verifyCodingFom2RadioGrpAddLogicWithSelectedNotSelected() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-52091");
	    baseClass.stepInfo(" Verify on click of 'Save and Next' button coding form should be validated as per the customized coding form using Tags objects along with Selected and \"Not Selected\" condition in context of security group");
	    String cfName = "CFComment"+Utility.dynamicNameAppender();
	    String tagName="cftag"+Utility.dynamicNameAppender();
	    String tagName1="cftag"+Utility.dynamicNameAppender();
	    
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
       
	 	//add tag
	 	tagsAndFoldersPage.CreateTag(tagName, Input.securityGroup);
	 	tagsAndFoldersPage.CreateTag(tagName1, Input.securityGroup);
	 	
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	codingForm.saveCodingForm2RadioGrpAddLogicWithSelectedNotSelected(cfName,tagName,tagName1);
	 	baseClass.stepInfo("Coding form created 2 Radio group with selected and not Selected");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		
		docViewPage.getRadioBoxforRdoGrp_2().waitAndClick(10);
		docViewPage.verifyErrorMsgInDocView();
		docViewPage.openChildWindowInCheckGroup();

		baseClass.passedStep(" Verified on click of 'Save and Next' button coding form should be validated as per the customized coding form using Tags objects along with Selected and \"Not Selected\" condition in context of security group");
		codingForm.assignCodingFormToSGAlert("Default Project Coding Form");
		driver.waitForPageToBeReady();
		codingForm.deleteCodingForm(cfName,cfName);
		
		// logout
		loginPage.logout();
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-52092
	 * @Description : To Verify on click of 'Save and Next' button coding form should be validated as per the customized coding form using Metadata objects along with "Not Selected" condition in context of security group.
	 */
	@Test(enabled = true,groups = { "regression" }, priority = 107)
	public void verifyCodingFom2MetaDataAddLogicNotSelected() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-52092");
	    baseClass.stepInfo("Verify on click of 'Save and Next' button coding form should be validated as per the customized coding form using Metadata objects along with \"Not Selected\" condition in context of security group");
	    String cfName = "CF"+Utility.dynamicNameAppender();
	    String meta = "demo"+Utility.dynamicNameAppender();
		String meta1 = "demo"+Utility.dynamicNameAppender();
		String intData= "INT";
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");

		// Custom Field created with INT DataType
		projectPage.clickingManageButton();
		projectPage.addMetaDataFieldUsingIntergerType(meta, intData, Input.docBasic, Input.tinyInt);
		projectPage.addMetaDataFieldUsingIntergerType(meta1, intData, Input.docBasic, Input.tinyInt);
		baseClass.stepInfo("Custom meta data field created with integer");
		
		securityGroupPage.addProjectFieldtoSG(meta);
		securityGroupPage.addProjectFieldtoSG(meta1);
		baseClass.stepInfo("Custom metadata integer field assign to security group");
		
		loginPage.logout();
		
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu2userName + "'");
       
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	codingForm.saveCodingForm2MetaDataAddLogicNotSelected(cfName,meta,meta1);
	 	baseClass.stepInfo("Coding form created 2 check group add logic is not Selected");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		
		docViewPage.verifyErrorMsgInDocView();
		docViewPage.openChildWindowInCheckGroup();

		baseClass.passedStep("Verified on click of 'Save and Next' button coding form should be validated as per the customized coding form using Metadata objects along with \"Not Selected\" condition in context of security group");
		codingForm.assignCodingFormToSGAlert("Default Project Coding Form");
		driver.waitForPageToBeReady();
		codingForm.deleteCodingForm(cfName,cfName);
		
		// logout
		loginPage.logout();
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-51195
	 * @Description : To Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form for comment element.
	 */
	@Test(enabled = true,groups = { "regression" }, priority = 108)
	public void verifySaveCompleteValidateCustomizedCodingForm() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51195");
	    baseClass.stepInfo("Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form for comment element");
	    String cfName = "CF"+Utility.dynamicNameAppender();
	    String assignName = "Assignment"+Utility.dynamicNameAppender();
		
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
       
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	driver.waitForPageToBeReady();
	 	codingForm.addNewCodingFormButton();
	 	codingForm.firstCheckBox("tag");
	 	codingForm.addcodingFormAddButton();
	 	codingForm.passingCodingFormName(cfName);
	 	codingForm.getCodingForm_StaticText(0).SendKeys("Field Label");
	 	codingForm.getCodingForm_ErrorMsg(0).SendKeys(Input.errorMsg);
	 	codingForm.getCodingForm_HelpMsg(0).SendKeys(Input.helpText);
	 	codingForm.saveCodingForm();
	 	baseClass.stepInfo("Comment Coding form created with Field label,error message,help text ");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		baseClass.stepInfo("Search with text input for docs completed");

		// Creating Assignment from Basic search
		assignmentPage = new AssignmentsPage(driver);
		assignmentPage.assignmentCreation(assignName, cfName);
		assignmentPage.toggleCodingStampEnabled();
		baseClass.stepInfo("Doc is Assigned from basic Search and Assignment '" + assignName + "' is created Successfully");
		assignmentPage.add2ReviewerAndDistribute();
		assignmentPage.selectAssignmentToViewinDocviewThreadMap(assignName);
		docViewPage.getSaveDoc().waitAndClick(10);
		baseClass.VerifySuccessMessage("Document saved successfully");
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");
		
		// Select the Assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");
				

		// Edit coding Form and complete Action
		docViewPage.getCompleteDocBtn().waitAndClick(10);
		baseClass.VerifySuccessMessage("Document completed successfully");
		baseClass.passedStep("Verified on click of 'Save'/'Complete button coding form should be validated as per the customized coding form for comment element");

		
		// logout
		loginPage.logout();
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-51203
	 * @Description : Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form using Tag object along with Check Item.
	 */
	@Test(enabled = true,groups = { "regression" }, priority = 109)
	public void verifySaveCompleteValidateTagCheckItemCodingForm() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51203");
	    baseClass.stepInfo("Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form using Tag object along with Check Item");
	    String cfName = "CF"+Utility.dynamicNameAppender();
	    String assignName = "Assignment"+Utility.dynamicNameAppender();
		
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
       
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	driver.waitForPageToBeReady();
	 	codingForm.addNewCodingFormButton();
	 	codingForm.firstCheckBox("tag");
	 	codingForm.addcodingFormAddButton();
	 	codingForm.selectRadioOrCheckGroup("check item");
	 	codingForm.selectDefaultActions(1, Input.optional);
	 	codingForm.passingCodingFormName(cfName);
	 	codingForm.saveCodingForm();
	 	baseClass.stepInfo("Tag Coding form created with tag along with check item ");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		baseClass.stepInfo("Search with text input for docs completed");

		// Creating Assignment from Basic search
		assignmentPage = new AssignmentsPage(driver);
		assignmentPage.assignmentCreation(assignName, cfName);
		assignmentPage.toggleCodingStampEnabled();
		baseClass.stepInfo("Doc is Assigned from basic Search and Assignment '" + assignName + "' is created Successfully");
		assignmentPage.add2ReviewerAndDistribute();
		assignmentPage.selectAssignmentToViewinDocviewThreadMap(assignName);
		docViewPage.getSaveDoc().waitAndClick(10);
		baseClass.VerifySuccessMessage("Document saved successfully");
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");
		
		// Select the Assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");
				

		// Edit coding Form and complete Action
		docViewPage.getCompleteDocBtn().waitAndClick(10);
		baseClass.VerifySuccessMessage("Document completed successfully");
		baseClass.passedStep("Verified on click of 'Save'/'Complete button coding form should be validated as per the customized coding form using Tag object along with Check Item");

		
		// logout
		loginPage.logout();
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-51204
	 * @Description : Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form using Comment object on coding form screen.
	 */
	@Test(enabled = true,groups = { "regression" }, priority = 110)
	public void verifySaveCompleteValidateCommentNotSelectableCodingForm() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51204");
	    baseClass.stepInfo("Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form using Comment object on coding form screen");
	    String cfName = "CF"+Utility.dynamicNameAppender();
	    String assignName = "Assignment"+Utility.dynamicNameAppender();
		
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
       
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	driver.waitForPageToBeReady();
	 	codingForm.addNewCodingFormButton();
	 	codingForm.firstCheckBox("comment");
	 	codingForm.addcodingFormAddButton();
	 	codingForm.selectDefaultActions(0, Input.notSelectable);
	 	codingForm.passingCodingFormName(cfName);
	 	codingForm.saveCodingForm();
	 	baseClass.stepInfo("Metadata Coding form created with Make it Optional");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		baseClass.stepInfo("Search with text input for docs completed");

		// Creating Assignment from Basic search
		assignmentPage = new AssignmentsPage(driver);
		assignmentPage.assignmentCreation(assignName, cfName);
		assignmentPage.toggleCodingStampEnabled();
		baseClass.stepInfo("Doc is Assigned from basic Search and Assignment '" + assignName + "' is created Successfully");
		assignmentPage.add2ReviewerAndDistribute();
		assignmentPage.selectAssignmentToViewinDocviewThreadMap(assignName);
		boolean flag=docViewPage.getAddComment1().Enabled();
		if(!flag) {
			System.out.println("document comment not selecable");
			baseClass.passedStep("document comment not selecable");
		}else {
			System.out.println("document comment selecable");
			baseClass.failedStep("document comment selecable");
		}
		docViewPage.getSaveDoc().waitAndClick(10);
		baseClass.VerifySuccessMessage("Document saved successfully");
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");
		
		// Select the Assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");
		
		// Edit coding Form and complete Action
		boolean flag1=docViewPage.getAddComment1().Enabled();
		if(!flag1) {
			System.out.println("document comment not selecable");
			baseClass.passedStep("document comment not selecable");
		}else {
			System.out.println("document comment selecable");
			baseClass.failedStep("document comment selecable");
		}
		docViewPage.getCompleteDocBtn().waitAndClick(10);
		baseClass.VerifySuccessMessage("Document completed successfully");
		baseClass.passedStep("Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form using Comment object on coding form screen");
		
		// logout
		loginPage.logout();
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-51205
	 * @Description : Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form for Editable Metadata objects
	 */
	@Test(enabled = true,groups = { "regression" }, priority = 111)
	public void verifySaveCompleteValidateMetaCodingForm() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51205");
	    baseClass.stepInfo("Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form for Editable Metadata objects");
	    String cfName = "CF"+Utility.dynamicNameAppender();
	    String assignName = "Assignment"+Utility.dynamicNameAppender();
		
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
       
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	driver.waitForPageToBeReady();
	 	codingForm.addNewCodingFormButton();
	 	codingForm.firstCheckBox("metadata");
	 	codingForm.addcodingFormAddButton();
	 	codingForm.selectDefaultActions(0, Input.optional);
	 	codingForm.passingCodingFormName(cfName);
	 	codingForm.saveCodingForm();
	 	baseClass.stepInfo("Comment Coding form created with Display but Not Selectable");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		baseClass.stepInfo("Search with text input for docs completed");

		// Creating Assignment from Basic search
		assignmentPage = new AssignmentsPage(driver);
		assignmentPage.assignmentCreation(assignName, cfName);
		assignmentPage.toggleCodingStampEnabled();
		baseClass.stepInfo("Doc is Assigned from basic Search and Assignment '" + assignName + "' is created Successfully");
		assignmentPage.add2ReviewerAndDistribute();
		assignmentPage.selectAssignmentToViewinDocviewThreadMap(assignName);
		driver.waitForPageToBeReady();
		docViewPage.getAttachCountTextBox().waitAndClick(5);
		docViewPage.getAttachCountTextBox().SendKeys("Edit document comment And Save");
		baseClass.stepInfo("text box is editble");
		docViewPage.getSaveDoc().waitAndClick(10);
		baseClass.VerifySuccessMessage("Document saved successfully");
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");
		
		// Select the Assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");
		
		// Edit coding Form and complete Action
		driver.waitForPageToBeReady();
		docViewPage.getAttachCountTextBox().waitAndClick(5);
		docViewPage.getAttachCountTextBox().SendKeys("Edit document comment And Save");
		baseClass.stepInfo("text box is editble");
		docViewPage.getCompleteDocBtn().waitAndClick(10);
		baseClass.VerifySuccessMessage("Document completed successfully");
		baseClass.passedStep("Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form for Editable Metadata objects");
		
		// logout
		loginPage.logout();
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-51206
	 * @Description : Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form for Tag/Comments/Metadata objects.
	 */
	@Test(enabled = true,groups = { "regression" }, priority = 112)
	public void verifySaveCompleteValidateTagCommentMetaCodingForm() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51206");
	    baseClass.stepInfo("Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form for Tag/Comments/Metadata objects");
	    String cfName = "CF"+Utility.dynamicNameAppender();
	    String tag="cfTag"+Utility.dynamicNameAppender();
	    String assignName = "Assignment"+Utility.dynamicNameAppender();
		
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
       
	 	// create tag
	 	tagsAndFoldersPage.CreateTag(tag,"Default Security Group");
	 	
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	driver.waitForPageToBeReady();
	 	codingForm.addNewCodingFormButton();
	 	baseClass.waitForElement(codingForm.getCodingForm_Tag(tag));
	 	codingForm.getCodingForm_Tag(tag).waitAndClick(10);
	 	codingForm.firstCheckBox(Input.comments);
	 	codingForm.addTwoCheckBox(Input.metaData);
	 	codingForm.addcodingFormAddButton();
	 	codingForm.selectRadioOrCheckGroup("check item");
	 	codingForm.selectDefaultActions(4, Input.required);
	 	codingForm.passingCodingFormName(cfName);
	 	codingForm.getCodingForm_HelpMsg(0).SendKeys(Input.helpText);
	 	codingForm.getCodingForm_ErrorMsg(4).SendKeys(Input.errorMsg);
	 	codingForm.getCodingForm_HelpMsg(4).SendKeys(Input.helpText);
	 	codingForm.saveCodingForm();
	 	baseClass.stepInfo("Coding form added with tag, comment,two metadata,check item, make it required created");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		baseClass.stepInfo("Search with text input for docs completed");

		// Creating Assignment from Basic search
		assignmentPage = new AssignmentsPage(driver);
		assignmentPage.assignmentCreation(assignName, cfName);
		assignmentPage.toggleCodingStampEnabled();
		baseClass.stepInfo("Doc is Assigned from basic Search and Assignment '" + assignName + "' is created Successfully");
		assignmentPage.add2ReviewerAndDistribute();
		assignmentPage.selectAssignmentToViewinDocviewThreadMap(assignName);
		driver.waitForPageToBeReady();
		//docViewPage.DocViewCodingForm4thCheckBox().waitAndClick(5);
		docViewPage.getSaveDoc().waitAndClick(10);
		docViewPage.errorMessage();
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");
		
		// Select the Assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");
		
		// Edit coding Form and complete Action
		driver.waitForPageToBeReady();
		//docViewPage.DocViewCodingForm4thCheckBox().waitAndClick(5);
		docViewPage.getCompleteDocBtn().waitAndClick(10);
		docViewPage.errorMessage();
		baseClass.passedStep("Verified on click of 'Save'/'Complete button coding form should be validated as per the customized coding form for Tag/Comments/Metadata objects");
		
		// logout
		loginPage.logout();
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-51207
	 * @Description : Verify on click of 'Save'/'Complete button coding form should be validated as per the customized Tags and Radio Group combined along with Radio Item on coding form screen.
	 */
	@Test(enabled = true,groups = { "regression" }, priority = 113)
	public void verifySaveCompleteValidate2TagRadioItemCodingForm() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51207");
	    baseClass.stepInfo("Verify on click of 'Save'/'Complete button coding form should be validated as per the customized Tags and Radio Group combined along with Radio Item on coding form screen");
	    String cfName = "CF"+Utility.dynamicNameAppender();
	    String assignName = "Assignment"+Utility.dynamicNameAppender();
		
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
	 	
	 	
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	driver.waitForPageToBeReady();
	 	codingForm.addNewCodingFormButton();
	 	codingForm.addTwoCheckBox(Input.tag);
	 	codingForm.addcodingFormAddButton();
	 	codingForm.selectRadioOrCheckGroup(0,"radio item");
	 	codingForm.selectRadioOrCheckGroup(1,"radio item");
	 	codingForm.selectDefaultActions(2, Input.optional);
	 	codingForm.selectDefaultActions(3, Input.notSelectable);
	 	codingForm.passingCodingFormName(cfName);
	 	codingForm.saveCodingForm();
	 	baseClass.stepInfo("Coding form added with tag,radio item, make it optional and not selectable created");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		baseClass.stepInfo("Search with text input for docs completed");

		// Creating Assignment from Basic search
		assignmentPage = new AssignmentsPage(driver);
		assignmentPage.assignmentCreation(assignName, cfName);
		assignmentPage.toggleCodingStampEnabled();
		baseClass.stepInfo("Doc is Assigned from basic Search and Assignment '" + assignName + "' is created Successfully");
		assignmentPage.add2ReviewerAndDistribute();
		assignmentPage.selectAssignmentToViewinDocviewThreadMap(assignName);
		driver.waitForPageToBeReady();
		docViewPage.getSaveDoc().waitAndClick(10);
		baseClass.VerifySuccessMessage("Document saved successfully");
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");
		
		// Select the Assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");
		
		// Edit coding Form and complete Action
		driver.waitForPageToBeReady();
		docViewPage.getCompleteDocBtn().waitAndClick(10);
		baseClass.VerifySuccessMessage("Document completed successfully");
		baseClass.passedStep("Verify on click of 'Save'/'Complete button coding form should be validated as per the customized Tags and Radio Group combined along with Radio Item on coding form screen");
		
		// logout
		loginPage.logout();
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-51214
	 * @Description : Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form using Metadata objects along with "Not Selected" condition.
	 */
	@Test(enabled = true,groups = { "regression" }, priority = 114)
	public void verifySaveCompleteValidatemetaDataNotSelectedCodingForm() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51214");
	    baseClass.stepInfo("Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form using Metadata objects along with \"Not Selected\" condition");
	   
	    String cfName = "CF"+Utility.dynamicNameAppender();
	    String assignName = "CFAssignment"+Utility.dynamicNameAppender();
	    String meta = "demo"+Utility.dynamicNameAppender();
		String meta1 = "demo"+Utility.dynamicNameAppender();
		String intData= "INT";
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");

		// Custom Field created with INT DataType
		projectPage.clickingManageButton();
		projectPage.addMetaDataFieldUsingIntergerType(meta, intData, Input.docBasic, Input.tinyInt);
		projectPage.addMetaDataFieldUsingIntergerType(meta1, intData, Input.docBasic, Input.tinyInt);
		baseClass.stepInfo("Custom meta data field created with integer");
		
		securityGroupPage.addProjectFieldtoSG(meta);
		securityGroupPage.addProjectFieldtoSG(meta1);
		baseClass.stepInfo("Custom metadata integer field assign to security group");
		
		loginPage.logout();
		
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu2userName + "'");
       
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	codingForm.saveCodingForm2MetaDataAddLogicNotSelected(cfName,meta,meta1);
	 	baseClass.stepInfo("Coding form created 2 metaData add logic is not Selected");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		baseClass.stepInfo("Search with text input for docs completed");

		// Creating Assignment from Basic search
		assignmentPage = new AssignmentsPage(driver);
		assignmentPage.assignmentCreation(assignName, cfName);
		assignmentPage.toggleCodingStampEnabled();
		baseClass.stepInfo("Doc is Assigned from basic Search and Assignment '" + assignName + "' is created Successfully");
		assignmentPage.add2ReviewerAndDistribute();
		assignmentPage.selectAssignmentToViewinDocviewThreadMap(assignName);
		driver.waitForPageToBeReady();
		docViewPage.getSaveDoc().waitAndClick(10);
		docViewPage.errorMessage();
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");
		
		// Select the Assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");
		
		// Edit coding Form and complete Action
		driver.waitForPageToBeReady();
		docViewPage.getCompleteDocBtn().waitAndClick(10);
		docViewPage.errorMessage();
		baseClass.passedStep("Verified on click of 'Save'/'Complete button coding form should be validated as per the customized coding form using Metadata objects along with \"Not Selected\" condition");
		
		// logout
		loginPage.logout();
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-51215
	 * @Description : Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form using all objects along with all condition and Radio Item
	 */
	@Test(enabled = true,groups = { "regression" }, priority = 115)
	public void verifySaveCompleteValidateAllObjectAlongWithRadioItemdCodingForm() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51215");
	    baseClass.stepInfo("Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form using all objects along with all condition and Radio Item");
	   
	    String cfName = "CF"+Utility.dynamicNameAppender();
	    String assignName = "CFAssignment"+Utility.dynamicNameAppender();
	    String tagName = "tag"+Utility.dynamicNameAppender();
		
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu2userName + "'");
	 	
	 	 // create tag
		tagsAndFoldersPage.CreateTag(tagName,"Default Security Group");
		
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	driver.waitForPageToBeReady();
	 	codingForm.addNewCodingFormButton();
	 	baseClass.waitForElement(codingForm.getCodingForm_Tag(tagName));
	 	codingForm.getCodingForm_Tag(tagName).waitAndClick(10);
	 	codingForm.firstCheckBox(Input.comments);
	 	codingForm.firstCheckBox(Input.metaData);
	 	codingForm.specialObjectsBox("staticText");
	 	codingForm.specialObjectsBox("radio");
	 	codingForm.specialObjectsBox("check");
	 	codingForm.addcodingFormAddButton();
	 	codingForm.getCF_TagTypes(0).selectFromDropdown().selectByVisibleText("Radio Item");
	 	driver.waitForPageToBeReady();
	 	codingForm.getCF_RadioGroup(0).selectFromDropdown().selectByIndex(1);
	 	codingForm.selectDefaultActions(1, Input.hidden);
	 	codingForm.selectDefaultActions(2, Input.notSelectable);
	 	codingForm.selectDefaultActions(4, Input.optional);
	 	codingForm.getCodingForm_ErrorMsg(4).SendKeys(Input.errorMsg);
	 	codingForm.getCodingForm_HelpMsg(4).SendKeys(Input.helpText);
	 	codingForm.selectDefaultActions(5, Input.required);
	 	driver.waitForPageToBeReady();
	 	driver.scrollPageToTop();
	 	codingForm.addLogicOptionWithIndex(1, 1, Input.select, Input.thisHidden);
	 	codingForm.addLogicOptionWithIndex(2, 1, Input.notSelect, Input.thisReadOnly);
	 	codingForm.addLogicOptionWithIndexWithoutIncreace(4, 1, Input.select, Input.thisOptional);
	 	codingForm.addLogicOptionWithIndexWithoutIncreace(5, 1, Input.notSelect, Input.thisRequired);
	 	codingForm.passingCodingFormName(cfName);
	 	codingForm.saveCodingForm();
	 	baseClass.stepInfo("Coding form created all object along with radio item");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		baseClass.stepInfo("Search with text input for docs completed");

		// Creating Assignment from Basic search
		assignmentPage = new AssignmentsPage(driver);
		assignmentPage.assignmentCreation(assignName, cfName);
		assignmentPage.toggleCodingStampEnabled();
		baseClass.stepInfo("Doc is Assigned from basic Search and Assignment '" + assignName + "' is created Successfully");
		assignmentPage.add2ReviewerAndDistribute();
		assignmentPage.selectAssignmentToViewinDocviewThreadMap(assignName);
		driver.waitForPageToBeReady();
		docViewPage.getSaveDoc().waitAndClick(10);
		docViewPage.errorMessage();
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");
		
		// Select the Assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");
		
		// Edit coding Form and complete Action
		driver.waitForPageToBeReady();
		docViewPage.getCompleteDocBtn().waitAndClick(10);
		docViewPage.errorMessage();
		baseClass.passedStep("Verified on click of 'Save'/'Complete button coding form should be validated as per the customized coding form using all objects along with all condition and Radio Item");
		
		// logout
		loginPage.logout();
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-51187
	 * @Description : Verify on click of 'Save' button coding form should be validated as per the default selected action for the coding form outside of an assignment
	 */
	@Test(enabled = true,groups = { "regression" }, priority = 116)
	public void verifyDefaultCodingforminDocView() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51187");
	    baseClass.stepInfo("Verify on click of 'Save' button coding form should be validated as per the default selected action for the coding form outside of an assignment");
	    String cfName = "CF"+Utility.dynamicNameAppender();
		
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
       
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	driver.waitForPageToBeReady();
	 	codingForm.addNewCodingFormButton();
	 	codingForm.firstCheckBox("tag");
	 	codingForm.firstCheckBox("comment");
	 	codingForm.firstCheckBox("metadata");
	 	codingForm.addcodingFormAddButton();
	 	codingForm.passingCodingFormName(cfName);
	 	codingForm.selectDefaultActions(1, Input.required);
	 	codingForm.saveCodingForm();
	 	baseClass.stepInfo("default coding form should be created");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewInDocViews();
		baseClass.stepInfo("Search with text input for docs completed");
		
		//docView
		docViewPage.getAddComment1().Clear();
		docViewPage.getSaveDoc().waitAndClick(10);
		docViewPage.errorMessage();
		baseClass.CloseSuccessMsgpopup();
		docViewPage.getAddComment1().SendKeys("Document commemnt added");
		docViewPage.getSaveDoc().waitAndClick(10);
		baseClass.VerifySuccessMessage("Document saved successfully");
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");
		
		sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewInDocViews();
		baseClass.stepInfo("Search with text input for docs completed");		

		// Edit coding Form and complete Action
		docViewPage.getAddComment1().Clear();
		docViewPage.getSaveDoc().waitAndClick(10);
		docViewPage.errorMessage();
		baseClass.CloseSuccessMsgpopup();
		docViewPage.getAddComment1().SendKeys("Document commemnt added");
		docViewPage.getSaveDoc().waitAndClick(10);
		baseClass.VerifySuccessMessage("Document saved successfully");
		baseClass.passedStep("Verified on click of 'Save' button coding form should be validated as per the default selected action for the coding form outside of an assignment");
		
		// logout
		loginPage.logout();
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-51188
	 * @Description : Verify on click of 'Complete' button coding form should be validated as per the default selected action for the coding form
	 */
	@Test(enabled = true,groups = { "regression" }, priority = 117)
	public void verifyDefaultCodingformAssignmentDocView() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51188");
	    baseClass.stepInfo("Verify on click of 'Complete' button coding form should be validated as per the default selected action for the coding form");
	    String cfName = "CF"+Utility.dynamicNameAppender();
		String assignName ="Assignment"+Utility.dynamicNameAppender();
	    
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
       
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	driver.waitForPageToBeReady();
	 	codingForm.addNewCodingFormButton();
	 	codingForm.firstCheckBox("tag");
	 	codingForm.firstCheckBox("comment");
	 	codingForm.firstCheckBox("metadata");
	 	codingForm.addcodingFormAddButton();
	 	codingForm.passingCodingFormName(cfName);
	 	codingForm.selectDefaultActions(1, Input.required);
	 	codingForm.saveCodingForm();
	 	baseClass.stepInfo("default coding form should be created");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		baseClass.stepInfo("Search with text input for docs completed");

		// Creating Assignment from Basic search
		assignmentPage = new AssignmentsPage(driver);
		assignmentPage.assignmentCreation(assignName, cfName);
		assignmentPage.toggleCodingStampEnabled();
		baseClass.stepInfo("Doc is Assigned from basic Search and Assignment '" + assignName + "' is created Successfully");
		assignmentPage.add2ReviewerAndDistribute();
		baseClass.impersonateRMUtoReviewer();
		driver.waitForPageToBeReady();
		
		//assignment
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");
		
		// Edit coding Form and complete Action
		driver.waitForPageToBeReady();
		docViewPage.getAddComment1().Clear();
		docViewPage.getCompleteDocBtn().waitAndClick(10);
		docViewPage.errorMessage();		
		baseClass.CloseSuccessMsgpopup();
		docViewPage.getAddComment1().SendKeys("Document commemnt added");
		docViewPage.getCompleteDocBtn().waitAndClick(10);
		baseClass.VerifySuccessMessage("Document completed successfully");
		
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");
		
		//assignment
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");
		
		// Edit coding Form and complete Action
		driver.waitForPageToBeReady();
		docViewPage.getAddComment1().Clear();
		docViewPage.getCompleteDocBtn().waitAndClick(10);
		docViewPage.errorMessage();		
		baseClass.CloseSuccessMsgpopup();
		docViewPage.getAddComment1().SendKeys("Document commemnt added");
		docViewPage.getCompleteDocBtn().waitAndClick(10);
		baseClass.VerifySuccessMessage("Document completed successfully");
		
		
		baseClass.passedStep("Verified on click of 'Complete' button coding form should be validated as per the default selected action for the coding form");
		
		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: NA Modified date:11/01/2022 Modified by: Baskar 
	 * @Description :Verify that custom metadata field value should be retained 
	 *               on doc view when created with TIME datatype in context of security group
	 */
	
	@Test(enabled = true, groups = { "regression" }, priority = 118)
	public void verifyThatCustomMetaDataFieldValueWithTIMEDatatype() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-52176");
	    baseClass.stepInfo("Verify that custom metadata field value should be retained on "
	    		+ "doc view when created with TIME datatype in context of security group");
	    securityGroupPage=new SecurityGroupsPage(driver);
	    codingForm=new CodingForm(driver);
	    sessionSearch=new SessionSearch(driver);
	    docViewPage=new DocViewPage(driver);
	    
	    String codingfrom = "CFDateTime"+Utility.dynamicNameAppender();
		UtilityLog.info("Started Execution for prerequisite");
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
	
		// Custom Field created with DATETIME DataType
		projectPage.addCustomFieldDataType(time, "Time");
		baseClass.stepInfo("Custom meta data field created with TIME datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(time);
		baseClass.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Creating Coding Form
		codingForm.creatingCodingFormAndAssgnCustomFields(codingfrom, time);
		baseClass.stepInfo("Project field added to coding form in Doc view");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(codingfrom);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// logout
		loginPage.logout();
		
		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		// verify the coding form panel
		docViewPage.verifyCodingFormDocViewINT(time);

		// logout
		loginPage.logout();
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		codingForm.assignCodingFormToSG("Default Project Coding Form");
//		Doing cleanup activity done in prerequities
		codingForm.deleteCodingForm(codingfrom,codingfrom);
		
		// logout
		loginPage.logout();
		
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-51209
	 * @Description : Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form using Tag/Comments/Metadata objects along with Check/Radio Group and Check Item
	 */
	@Test(enabled = true ,groups = { "regression" }, priority = 119)
	public void verifySaveCompleteValidateAllObjectAlongWithCheckItemdCodingForm() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51209");
	    baseClass.stepInfo("Verify on click of 'Save'/'Complete button Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form using Tag/Comments/Metadata objects along with Check/Radio Group and Check Item form should be validated as per the customized coding form using all objects along with all condition and Radio Item");
	   
	    String cfName = "CF"+Utility.dynamicNameAppender();
	    String assignName = "CFAssignment"+Utility.dynamicNameAppender();
	    String tagName = "tag"+Utility.dynamicNameAppender();
		
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
	 	
	 	 // create tag
		tagsAndFoldersPage.CreateTag(tagName,"Default Security Group");
		
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	driver.waitForPageToBeReady();
	 	codingForm.addNewCodingFormButton();
	 	baseClass.waitForElement(codingForm.getCodingForm_Tag(tagName));
	 	codingForm.getCodingForm_Tag(tagName).waitAndClick(10);
	 	codingForm.firstCheckBox(Input.comments);
	 	codingForm.firstCheckBox(Input.metaData);
	 	codingForm.specialObjectsBox("staticText");
	 	codingForm.specialObjectsBox("radio");
	 	codingForm.specialObjectsBox("check");
	 	driver.scrollPageToTop();
	 	codingForm.addcodingFormAddButton();
	 	codingForm.getCF_TagTypes(0).selectFromDropdown().selectByVisibleText("Check Item");
	 	driver.waitForPageToBeReady();
	 	codingForm.getCF_CheckGroup(0).selectFromDropdown().selectByIndex(1);
	 	codingForm.selectDefaultActions(1, Input.hidden);
	 	codingForm.selectDefaultActions(2, Input.notSelectable);
	 	codingForm.selectDefaultActions(4, Input.optional);
	 	codingForm.getCodingForm_ErrorMsg(4).SendKeys(Input.errorMsg);
	 	codingForm.getCodingForm_HelpMsg(4).SendKeys(Input.helpText);
	 	codingForm.selectDefaultActions(5, Input.required);
	 	codingForm.getCodingForm_ErrorMsg(5).SendKeys(Input.errorMsg);
	 	codingForm.getCodingForm_HelpMsg(5).SendKeys(Input.helpText);
	 	driver.waitForPageToBeReady();
	 	driver.scrollPageToTop();
	 	codingForm.passingCodingFormName(cfName);
	 	codingForm.saveCodingForm();
	 	baseClass.stepInfo("Coding form created all object along with check item");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		baseClass.stepInfo("Search with text input for docs completed");

		// Creating Assignment from Basic search
		assignmentPage = new AssignmentsPage(driver);
		assignmentPage.assignmentCreation(assignName, cfName);
		assignmentPage.toggleCodingStampEnabled();
		baseClass.stepInfo("Doc is Assigned from basic Search and Assignment '" + assignName + "' is created Successfully");
		assignmentPage.add2ReviewerAndDistribute();
		assignmentPage.selectAssignmentToViewinDocviewThreadMap(assignName);
		driver.waitForPageToBeReady();
		docViewPage.getSaveDoc().waitAndClick(10);
		docViewPage.errorMessage();
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");
		
		// Select the Assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");
		
		// Edit coding Form and complete Action
		driver.waitForPageToBeReady();
		docViewPage.getCompleteDocBtn().waitAndClick(10);
		docViewPage.errorMessage();
		baseClass.passedStep("Verified on click of 'Save'/'Complete button coding form should be validated as per the customized coding form using Tag/Comments/Metadata objects along with Check/Radio Group and Check Item");
		
		// logout
		loginPage.logout();
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-51210
	 * @Description : Verify validation of coding form on click of 'Save'/'Complete button using Tag/Comments/Metadata objects along with Check/Radio Group and Radio Item
	 */
	@Test(enabled = true ,groups = { "regression" }, priority = 120)
	public void verifySaveCompleteValidateAllObjectAlongWithCheckRadioItemdCodingForm() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51210");
	    baseClass.stepInfo("Verify validation of coding form on click of 'Save'/'Complete button using Tag/Comments/Metadata objects along with Check/Radio Group and Radio Item");
	   
	    String cfName = "CF"+Utility.dynamicNameAppender();
	    String assignName = "CFAssignment"+Utility.dynamicNameAppender();
	    String tagName = "tag"+Utility.dynamicNameAppender();
		
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu2userName + "'");
	 	
	 	 // create tag
		tagsAndFoldersPage.CreateTag(tagName,"Default Security Group");
		
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	driver.waitForPageToBeReady();
	 	codingForm.addNewCodingFormButton();
	 	baseClass.waitForElement(codingForm.getCodingForm_Tag(tagName));
	 	codingForm.getCodingForm_Tag(tagName).waitAndClick(10);
	 	codingForm.firstCheckBox(Input.comments);
	 	codingForm.firstCheckBox(Input.metaData);
	 	codingForm.specialObjectsBox("staticText");
	 	codingForm.specialObjectsBox("radio");
	 	codingForm.specialObjectsBox("check");
	 	driver.scrollPageToTop();
	 	codingForm.addcodingFormAddButton();
	 	codingForm.getCF_TagTypes(0).selectFromDropdown().selectByVisibleText("Check Item");
	 	driver.waitForPageToBeReady();
	 	codingForm.getCF_CheckGroup(0).selectFromDropdown().selectByIndex(1);
	 	codingForm.selectDefaultActions(1, Input.hidden);
	 	codingForm.selectDefaultActions(2, Input.required);
	 	codingForm.getCodingForm_ErrorMsg(2).SendKeys(Input.errorMsg);
	 	codingForm.getCodingForm_HelpMsg(2).SendKeys(Input.helpText);
	 	codingForm.selectDefaultActions(4, Input.hidden);
	 	codingForm.selectDefaultActions(5, Input.notSelectable);
	 	driver.waitForPageToBeReady();
	 	driver.scrollPageToTop();
	 	codingForm.passingCodingFormName(cfName);
	 	codingForm.saveCodingForm();
	 	baseClass.stepInfo("Coding form created all object along with check item");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		baseClass.stepInfo("Search with text input for docs completed");

		// Creating Assignment from Basic search
		assignmentPage = new AssignmentsPage(driver);
		assignmentPage.assignmentCreation(assignName, cfName);
		assignmentPage.toggleCodingStampEnabled();
		baseClass.stepInfo("Doc is Assigned from basic Search and Assignment '" + assignName + "' is created Successfully");
		assignmentPage.add2ReviewerAndDistribute();
		assignmentPage.selectAssignmentToViewinDocviewThreadMap(assignName);
		driver.waitForPageToBeReady();
		docViewPage.getSaveDoc().waitAndClick(10);
		docViewPage.errorMessage();
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");
		
		// Select the Assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");
		
		// Edit coding Form and complete Action
		driver.waitForPageToBeReady();
		docViewPage.getCompleteDocBtn().waitAndClick(10);
		docViewPage.errorMessage();
		baseClass.passedStep("Verified validation of coding form on click of 'Save'/'Complete button using Tag/Comments/Metadata objects along with Check/Radio Group and Radio Item");
		
		// logout
		loginPage.logout();
	}
	
	/**
	 * @Author : Baskar date: NA Modified date:11/02/2021 Modified by: Baskar 
	 * @Description :Verify validation of coding form when coding form is created 
	 *               with metadata field as DateTime on click of 'Complete'
	 */
	
	@Test(enabled = true, groups = { "regression" }, priority = 121)
	public void validationOfNonDateFormatINAssign() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51582");
	    baseClass.stepInfo("Verify validation of coding form when coding form is "
	    		+ "created with metadata field as DateTime on click of 'Complete'");
	    
	    String codingfrom = "CFDateTime"+Utility.dynamicNameAppender();
	    String assgnName = "Assign"+Utility.dynamicNameAppender();
	    String assgnColour = "ColourAssign"+Utility.dynamicNameAppender();
	    String dateTime = "DateTime" + Utility.dynamicNameAppender();
		UtilityLog.info("Started Execution for prerequisite");
		
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
	
		// Custom Field created with DateTime DataType
		projectPage.addCustomFieldDataType(dateTime, "DATETIME");
		baseClass.stepInfo("Custom meta data field created with DateTime datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(dateTime);
		baseClass.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Creating Coding Form
		codingForm.creatingCodingFormAndAssgnUsingParameter(codingfrom, dateTime,"Make it Optional");
		baseClass.stepInfo("Project field added to coding form in Doc view");
		
		// Session search to doc view to create assignment
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assgnName, codingfrom);
		assignmentPage.toggleSaveButton();
		assignmentPage.add2ReviewerAndDistribute();
		
		// logout
		loginPage.logout();
		
		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assgnName);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		
		// verify the coding form panel
		docViewPage.nonDateFormatValidationUsingSaveAndComplete(dateTime,assgnColour,Input.stampSelection);

		// logout
		loginPage.logout();
		
//		Doing house keeping activity
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		assignmentPage.deleteAssgnmntUsingPagination(assgnName);
		codingForm.deleteCodingForm(codingfrom,codingfrom);
		
		// logout
		loginPage.logout();

	}
	
	/**
	 * @Author : Baskar date: NA Modified date:14/02/2022 Modified by: Baskar 
	 * @Description :Verify on click of 'Save and Next' button coding form should be 
	 *               validated as per the customized coding form using Tag/Comments/Metadata 
	 *               objects along with Check/Radio Group and Check Item in context of security group
	 */
	@Test(enabled = true ,groups = { "regression" }, priority = 122)
	public void validationUsingSaceAndNextForCheckItemsRequiredTag() throws InterruptedException {
	    baseClass.stepInfo("Test case Id: RPMXCON-52088");
	    baseClass.stepInfo("Verify on click of 'Save and Next' button coding form should be "
	    		+ "validated as per the customized coding form using Tag/Comments/Metadata objects "
	    		+ "along with Check/Radio Group and Check Item in context of security group");
	   
	    String cfName = "CF"+Utility.dynamicNameAppender();
	    String tagName = "tag"+Utility.dynamicNameAppender();
		
	    // login as RMU
	 	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	 	baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
	 	
	 	 // create tag
		tagsAndFoldersPage.CreateTag(tagName,"Default Security Group");
		
	 	// create new coding form
	 	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 	driver.waitForPageToBeReady();
	 	codingForm.addNewCodingFormButton();
	 	baseClass.waitForElement(codingForm.getCodingForm_Tag(tagName));
	 	codingForm.getCodingForm_Tag(tagName).waitAndClick(10);
	 	codingForm.firstCheckBox(Input.comments);
	 	codingForm.firstCheckBox(Input.metaData);
	 	codingForm.specialObjectsBox(Input.staticText);
	 	codingForm.specialObjectsBox(Input.radioGroup);
	 	codingForm.specialObjectsBox(Input.checkGroup);
	 	driver.scrollPageToTop();
	 	codingForm.addcodingFormAddButton();
//	 	codingForm.getCF_TagTypes(0).selectFromDropdown().selectByVisibleText(Input.checkItem);
	 	driver.waitForPageToBeReady();
	 	codingForm.getCF_CheckGroup(0).selectFromDropdown().selectByIndex(1);
	 	codingForm.selectDefaultActions(1, Input.hidden);
	 	codingForm.selectDefaultActions(2, Input.notSelectable);
	 	codingForm.selectDefaultActions(4, Input.optional);
	 	codingForm.getCodingForm_ErrorMsg(4).SendKeys(Input.errorMsg);
	 	codingForm.getCodingForm_HelpMsg(4).SendKeys(Input.helpText);
	 	codingForm.selectDefaultActions(5, Input.required);
	 	codingForm.getCodingForm_ErrorMsg(5).SendKeys(Input.errorMsg);
	 	codingForm.getCodingForm_HelpMsg(5).SendKeys(Input.helpText);
	 	driver.waitForPageToBeReady();
	 	driver.scrollPageToTop();
	 	codingForm.passingCodingFormName(cfName);
	 	codingForm.saveCodingForm();
	 	baseClass.stepInfo("Coding form created all object along with check item");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		docViewPage.selectPureHit();
		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch1(Input.testData1);
		sessionSearch.ViewInDocViews();

		// Edit coding Form and action using save and next buton
		baseClass.stepInfo("Performing action for parent window");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getSaveAndNextButton());
		docViewPage.getSaveAndNextButton().waitAndClick(5);
		docViewPage.errorMessage();
		baseClass.passedStep("Validation messgae displayed for checkbox required tag in parent window");
		baseClass.stepInfo("Performing action for child window");
		docViewPage.clickGearIconOpenCodingFormChildWindow();
		String parentwindow=docViewPage.switchTochildWindow();
		baseClass.waitForElement(docViewPage.getSaveAndNextButton());
		docViewPage.getSaveAndNextButton().waitAndClick(5);
		docViewPage.childWindowToParentWindowSwitching(parentwindow);
		docViewPage.errorMessage();
		baseClass.passedStep("Validation messgae displayed for checkbox required tag in child window");

		// Reassign default coding form again
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.assignCodingFormToSG(Input.codingFormName);
		codingForm.deleteCodingForm(cfName, cfName);
		
		// logout
		loginPage.logout();
	}
	/**
	 * @Author : Baskar date:1/12/21 Modified date: NA Modified by: Baskar 
	 * @Description : Verify that user should not save the coding stamp without 
	 *                selecting coding form for required fields in context of security group
	 */
	
	@Test(enabled = true, groups = { "regression" }, priority = 123)
	public void  validateChildwindowForRequiredTagCF() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52044");
		baseClass.stepInfo("Verify that user should not save the coding stamp without "
				+ "selecting coding form for required fields in context of security group");
		String cfName = "CF"+Utility.dynamicNameAppender();
		String fieldText="Stamp"+Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		
		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.commentRequired(cfName);
		baseClass.stepInfo("Coding form created with comments required tag");
		
		// Assign to security group
		codingForm.assignCodingFormToSG(cfName);
		baseClass.stepInfo("Coding form assigned to security group");
		
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		
		docViewPage.validateWithoutEditUsingStamp(fieldText);
		
		codingForm.assignCodingFormToSGAlert("Default Project Coding Form");
		driver.waitForPageToBeReady();
		codingForm.deleteCodingForm(cfName,cfName);
		
		// logout
		loginPage.logout();
	}
	

	/**
	 * @Author : Baskar date: NA Modified date:10/12/2021 Modified by: Baskar 
	 * @Description :Verify validation of coding form when coding form is created with metadata 
	 *               field as DateTime on click of 'Save''
	 */
	
	@Test(enabled = true, groups = { "regression" }, priority = 124)
	public void validationOfNonDateFormatUsingSave() throws InterruptedException, AWTException {
	    baseClass.stepInfo("Test case Id: RPMXCON-51579");
	    baseClass.stepInfo("Verify validation of coding form when coding form is "
	    		+ "created with metadata field as DateOnly on click of 'Save'");
	    String codingForms = "CFDate"+Utility.dynamicNameAppender();
	    String assgnCoding = "codingAssgn"+Utility.dynamicNameAppender();

		UtilityLog.info("Started Execution for prerequisite");
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");
	
		// Custom Field created with DATE DataType
		projectPage.addCustomFieldDataType(date, "Date");
		baseClass.stepInfo("Custom meta data field created with DATE datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(date);
		baseClass.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Creating Coding Form
		codingForm.creatingCodingFormAndAssgnUsingParameter(codingForms, date,Input.optional);
		baseClass.stepInfo("Project field added to coding form in Doc view");
		
		// Session search to doc view to create assignment
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		
		assignmentPage.assignmentCreation(assgnCoding, codingForms);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.assignmentDistributingToReviewer();
		
		// logout
		loginPage.logout();
		
		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		
		// verify the coding form panel for non-date format
		docViewPage.nonDateFormatValidationUsingSave(date);

		// logout
		loginPage.logout();
	}
	
	/**
	 * @throws Exception 
	 * @Author : Iyappan.Kasinathan 
	 * @Description : Doc View: On navigating to other page after editing the coding form then from confirmation navigation message
	 *                on click of 'Yes' it should redirect to the respective page
	 */
	@Test(enabled = true, dataProvider = "UsersWithoutPA", groups = { "regression" }, priority = 125)
	public void verifyNavigationMsgByNavigateToOtherPg(String role,String userName, String password) throws Exception {
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
	@Test(enabled = true,dataProvider = "UsersWithoutPA", groups = { "regression" }, priority = 126)
	public void verifyNavigationMsgByClickEditProfile(String role, String username, String password) throws Exception {
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
	@Test(enabled = true,dataProvider = "UsersWithoutPA", groups = { "regression" }, priority = 127)
	public void verifyNavigationMsgByClickChangeRole(String role, String username, String password) throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-51623");
	    baseClass.stepInfo("Doc View: On click of Change Role link after editing the coding form then from confirmation navigation"+
	                       " message on click of 'Yes' it should redirect to the respective page");  
	    loginPage.loginToSightLine(username, password);
	    if(role=="RMU") {
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
	@Test(enabled = true,dataProvider = "UsersWithoutPA", groups = { "regression" }, priority = 128)
	public void verifyNavigationMsgByClickSignOut(String role,String username, String password) throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-51624");
	    baseClass.stepInfo("Doc View: On click of Sign Out link after editing the coding form then from confirmation navigation"+
	                      " message on click of 'Yes' it should redirect to the respective page");  
	    loginPage.loginToSightLine(username, password);
	    if(role=="RMU") {
	    	baseClass.stepInfo("Logged in as rmu user");
	    	codingForm.assignCodingFormToSG(Input.codeFormName);
	    	}else {
	    	baseClass.stepInfo("Logged in as reviewer");
	    }
	    sessionSearch.basicContentSearch("null");    
	    sessionSearch.ViewInDocView();
	    baseClass.stepInfo("Navigated to doc view page");
	    String ExpectedValue = codingForm.getObjectsInPreviewBox("Responsive").GetAttribute("checked");
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
	    String Actualvalue = codingForm.getObjectsInPreviewBox("Responsive").GetAttribute("checked");
	    softAssertion.assertEquals(Actualvalue, ExpectedValue);
	    baseClass.passedStep("No changes are saved after clicking yes button to navigate some other page");
	    softAssertion.assertAll();
	    loginPage.logout();
	}
	
	/**
	 * @Author : Iyappan.Kasinathan 
	 * @Description : To verify custom coding form is editable or not when same document is assigned to two different assignments with same assigned coding form
	 */
	@Test(enabled = true, dataProvider = "rmuAndrev", groups = { "regression" }, priority = 129)
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
	 * @Author : Iyappan.Kasinathan 
	 * @Description :Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form for tag element
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 130)
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
	@Test(enabled = true, groups = { "regression" }, priority = 131)
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
	@Test(enabled = true, dataProvider = "ImpersonationOfPA",groups = { "regression" }, priority = 132)
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
	@Test(enabled = true, dataProvider = "ImpersonationOfUsers", groups = { "regression" }, priority = 133)
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
	@Test(enabled = true, dataProvider = "rmuAndrev", groups = { "regression" }, priority = 134)
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
		miniDocListPage.configureMiniDocListToShowCompletedDocs();
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
		Object[][] users = { { "RMU" ,Input.rmu1userName, Input.rmu1password }, {"REV", Input.rev1userName, Input.rev1password } };
		return users;
	}
	
	@DataProvider(name = "rmuAndrev")
	public Object[][] rmuAndrev() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "RMU" }, { Input.rev1userName, Input.rev1password, "REV" } };
		return users;
	}
	

	@DataProvider(name = "ContentAndAudio")
	public Object[][] ContentAndAudio() {
		Object[][] ContentAndAudio = { { "Basic" }, { "Audio" }, };
		return ContentAndAudio;
	}

	@DataProvider(name = "paToRmuRev")
	public Object[][] paToRmuRev() {
		return new Object[][] { { "pa", Input.pa1userName, Input.pa1password, "rmu" },
				{ "pa", Input.pa1userName, Input.pa1password, "rev" } };
	}

	@DataProvider(name = "rmuRevLogin")
	public Object[][] userRmuRev() {
		return new Object[][] { { Input.rmu1FullName, Input.rmu1userName, Input.rmu1password },
				{ Input.rev1FullName, Input.rev1userName, Input.rev1password } };
	}

	@DataProvider(name = "daToRmuRev")
	public Object[][] daToRmuRev() {
		return new Object[][] { { "da", Input.da1userName, Input.da1password, "rmu" },
				{ "da", Input.da1userName, Input.da1password, "rev" } };
	}

	@DataProvider(name = "saToRmuRev")
	public Object[][] saToRmuRev() {
		return new Object[][] { { "sa", Input.sa1userName, Input.sa1password, "rmu" },
				{ "sa", Input.sa1userName, Input.sa1password, "rev" } };
	}

	@DataProvider(name = "rmuDauSauLogin")
	public Object[][] userRmuDaSa() {
		return new Object[][] { { Input.rmu1FullName, Input.rmu1userName, Input.rmu1password, "null" },
				{ "sa", Input.da1userName, Input.da1password, "rmu" },
				{ "pa", Input.pa1userName, Input.pa1password, "rmu" } };
	}

	@DataProvider(name = "impersoante")
	public Object[][] userLoginSaPaDaRmu() {
		return new Object[][] { { "rmu", Input.rmu1userName, Input.rmu1password, "null" },
				{ "sa", Input.sa1userName, Input.sa1password, "rmu" },
				{ "sa", Input.sa1userName, Input.sa1password, "rev" },
				{ "da", Input.da1userName, Input.da1password, "rmu" },
				{ "da", Input.da1userName, Input.da1password, "rev" },
				{ "pa", Input.pa1userName, Input.pa1password, "rmu" },
				{ "pa", Input.pa1userName, Input.pa1password, "rev" } };
	}

	@DataProvider(name = "rmuRevRmuLogin")
	public Object[][] userRmuRevRmu() {
		return new Object[][] { { "rmu", Input.rmu1userName, Input.rmu1password },
				{ "rev", Input.rev1userName, Input.rev1password },
				{ "assgnCf", Input.rmu1userName, Input.rmu1password } };
	}

	@DataProvider(name = "threeUser")
	public Object[][] threeUser() {
		return new Object[][] { { "rmu", Input.rmu1userName, Input.rmu1password, "rev" },
				{ "sa", Input.sa1userName, Input.sa1password, "rmu" },
				{ "sa", Input.sa1userName, Input.sa1password, "rev" },
				{ "pa", Input.pa1userName, Input.pa1password, "rmu" },
				{ "pa", Input.pa1userName, Input.pa1password, "rev" } };
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
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
