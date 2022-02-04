package testScriptsRegression;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocView_CodingForm_Regression {
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

	}

	/**
	 * Author : Baskar date: NA Modified date:24/8/2021 Modified by: Baskar
	 * Description : Verify that custom metadata field value should be retained on
	 * doc view when created with INT datatype in context of security group
	 * RPMXCON-52172 DocView/Coding Forms Sprint 01
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 1)
	public void verifyThatCustomMetaDataFieldValueWithINTDatatype() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52172");
		UtilityLog.info("Started Execution for prerequisite");
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

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Creating Coding Form
		codingForm.addProjectFieldMetaData(Input.codingFormName, projectFieldINT);
		baseClass.stepInfo("Project field added to coding form in Doc view");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		// verify the coding form panel
		docViewPage.verifyCodingFormDocViewINT(projectFieldINT);

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager'" + Input.rmu1userName + "'");

	}

	/**
	 * Author : Baskar date: NA Modified date:24/8/2021 Modified by: Baskar
	 * Description :Verify that custom metadata field value should be retained on
	 * doc view when created with NVARCHAR datatype in context of security group
	 * RPMXCON-52173 DocView/Coding Forms Sprint 01
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 2)
	public void verifyThatCustomMetaDataFieldValueWithNVARCHARDatatype() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52173");
		UtilityLog.info("Started Execution for prerequisite");
		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");

		// Custom Field created with NVARCHAR DataType
		projectPage.addCustomFieldProjectDataType(projectFieldNVARCHAR, "NVARCHAR");
		baseClass.stepInfo("Custom meta data field created with NVARCHAR datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(projectFieldNVARCHAR);
		baseClass.stepInfo("Custom meta data field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Creating Coding Form
		codingForm.addProjectFieldMetaData(Input.codingFormName, projectFieldNVARCHAR);
		baseClass.stepInfo("Project field added to coding form in Doc view");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		// verify the coding form panel
		docViewPage.verifyCodingFormDocViewNVARCHAR(projectFieldNVARCHAR);

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager'" + Input.rmu1userName + "'");

	}

	/**
	 * Author : Baskar date: NA Modified date:24/8/2021 Modified by: Baskar
	 * Description :Verify when user clicks code same as last on selecting the code
	 * same as action for document selected from mini doc list child window in
	 * context of assignment RPMXCON-52162 DocView/Coding Forms Sprint 01
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 3)
	public void clickCodeSameAsLastAndCodeingStampSaved() throws AWTException, InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52162");
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.createAssignmentNew(assignmentCreationOn, Input.codingFormName);

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager'" + Input.rmu1userName + "'");

		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assignmentCreationOn);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// Coding Stamp Selection And code Same As Verify
		docViewPage.docViewCodingFormPanelStampSelection(Input.stampColour);
		baseClass.stepInfo("Coding form loaded with value and selected with stamp selection");
		docViewPage.docViewMiniCodeSameAs();

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

	}

	/**
	 * Author : Baskar date: NA Modified date:24/8/2021 Modified by: Baskar
	 * Description:Verify when user clicks 'code same as last' after saving the
	 * document in context of assignment RPMXCON-52157 DocView/Coding Forms Sprint
	 * 01
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 4)
	public void verifyUserClickCodeSameAsLastAfterSaving() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52157");
		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assignmentCreationOn);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// user click code as last document after saving in coding form panel
		docViewPage.userClickCodeSameAsAfterSaving();

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");
	}

	/**
	 * Author : Baskar date: NA Modified date: 24/8/2021 Modified by: Baskar
	 * Description:Coding form child window: Verify reviewer clicks code same as
	 * last on navigating to doc view from dashboard assignment assignment
	 * RPMXCON-52158 DocView/Coding Forms Sprint 01
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 5)
	public void codeSameAsLastShouldNotClickableOnChildWindow() throws AWTException, InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52158");
		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assignmentCreationOn);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// Code same Last Disabled CodingForm in child window
		docViewPage.childWindowCodeSameAsLastDisabled();

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfullyy logout Reviewer '" + Input.rev1userName + "'");

	}

	/**
	 * Author : Baskar date: NA Modified date: 24/8/2021 Modified by: Baskar
	 * Description:Verify when user clicks code same as last on selecting the code
	 * same as action for document selected from mini doc list in context of
	 * assignment RPMXCON-52161 DocView/Coding Forms Sprint 01
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 6)
	public void codingFormToggleShouldOFFAtAssignmentLevel() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52161");
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Search to Assignment Creation stamp level off
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignmentCreationStampOff, Input.codingFormName);
		assignmentPage.toggleSaveButton();
		assignmentPage.assignmentDistributingToReviewer();

		// logout Reviewer Manager
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager'" + Input.rmu1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rev1userName + "'");
		assignmentPage.SelectAssignmentByReviewer(assignmentCreationStampOff);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// Coding Stamp Selection And code Same As Verify
		docViewPage.docViewCodingFormPanelStampSelection(Input.stampColours);
		baseClass.stepInfo("Coding form loaded with value and selected with stamp selection");
		docViewPage.docViewMiniCodeSameAs();

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

	}

	/**
	 * Author : Baskar date: NA Modified date: 24/8/2021 Modified by: Baskar
	 * Description:Floppy icon to save the stamp is not clickable after doing the
	 * code as same from docview RPMXCON-51635 Sprint 01
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 7)
	public void floppyIconToSaveStamp() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51635");
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(completeBtnCodingForm, Input.codingFormName);
		assignmentPage.assignmentDistributingToReviewer();

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager'" + Input.rmu1userName + "'");

		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(completeBtnCodingForm);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// CodingStamp popup verfiy
		docViewPage.codingStampPopupShouldOpen();

		loginPage.logout();
	}

	/**
	 * Author : Baskar date: NA Modified date:NA Modified by: Baskar TODO:BUG IN
	 * APPLICATION. EXPECT FAILURE ON THIS TEST CASE Description :Coding form child
	 * window: Verify when user clicks 'code same as last' after saving the document
	 * in context of assignment RPMXCON-52159 DocView/Coding Forms Sprint 01
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 8)
	public void codingFormChildWindowCodeSameAsLast() throws AWTException, InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52159");
		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.failedStep("Expected failure case RPMXCON-52159");
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assignmentCreationOn);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// child window code as last
		docViewPage.codingFormChildWindowCodeAsLast();

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

	@Test(enabled = true, groups = { "regression" }, priority = 9)
	public void codingFormAssignmentLevelCodingStampOFF() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52160");
		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.failedStep("Expected failure case RPMXCON-52160");
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Assignment Selection in Reviewer Login
		assignmentPage.SelectAssignmentByReviewer(assignmentCreationStampOff);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		docViewPage.docViewCodingFormPanelStampSelection(Input.stampSelection);
		docViewPage.codingFormChildWindowCodeAsLast();

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

	}

	/**
	 * Author : Baskar date: NA Modified date: NA Modified by: Baskar
	 * Description:Coding Form Child window: Verify that user can save the coding
	 * stamp after coding form selection for a document in context of security group
	 * RPMXCON-52043 Sprint 02
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 10)
	public void codingFormChildWindowStampColourSelection() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52043");
		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rev1userName + "'");

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		docViewPage.selectPureHit();

		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch(Input.searchStringStar);

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		// CodingForm ChildWindow Stamp selection
		docViewPage.stampSelectionCodingForm(Input.stampColour);

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

	@Test(enabled = true, groups = { "regression" }, priority = 11)
	public void verifyingCodingStampPostFixColourParentWindow() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52226");
		String assgnColour = "AssignColour" + Utility.dynamicNameAppender();
		// Login As Reviewer
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.failedStep("Expected Failure Parent window Pencil gear icon is not clickable");

		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		// Stamp selection
		docViewPage.clickStampBtnAndVerifyINPopUp(assgnColour, Input.stampSelection, Input.stampSelection);

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

	}

	/**
	 * @Author : Baskar date:2/9/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify that already saved stamp presentation should be
	 *              displayed with post-fix as 'Assigned' in coding stamp pop up
	 *              form Coding Form child window
	 * 
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 12)
	public void verifyingCodingStampPostFixColourChildWindowPopUp() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52225");
		String AssignStamp = "StampPopUp" + Utility.dynamicNameAppender();
		String assgnColour = "AssignC" + Utility.dynamicNameAppender();
		// Login As Reviewer
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		// CodingForm ChildWindow should open popup
		docViewPage.openStampPopUpFromChildWindow(assgnColour, Input.stampColours, Input.stampColours);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		// searching document for assignmnet creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(AssignStamp, Input.codingFormName);
		assignmentPage.assignmentDistributingToReviewer();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rev1userName + "'");

		// selecting the assignment
		assignmentPage.SelectAssignmentByReviewer(AssignStamp);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// CodingForm ChildWindow should open popup after assignment creation
		docViewPage.openStampPopUpFromChildWindow(assgnColour, Input.stampColours, Input.stampColours);

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

	@Test(enabled = true, groups = { "regression" }, priority = 13)
	public void verifyingCodingStampPostFixColourAndEditing() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52227");
		String assgnColour = "AssignColour" + Utility.dynamicNameAppender();
		// Login As Reviewer
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.failedStep("Expected Failure:Gear stamp icon is not clickable in parent window");
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		// Stamp selection
		docViewPage.clickStampBtnAndVerifyINPopUp(assgnColour, Input.stampSelection, Input.stampSelection);

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

	}

	/**
	 * @Author : Baskar date:14/9/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify when user clicks the 'Code same as last' on selecting
	 *              the document from history drop down
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 14)
	public void codeSameAsLastInHistoryDropDown() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52140");

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		docViewPage.codeSameAsLastHistoryDropDown();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager '" + Input.rmu1userName + "'");

	}

	/**
	 * @Author : Baskar date:14/9/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify when user clicks 'Code same as last' when vieweing the
	 *              last document of mini doc list
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 15)
	public void viewLastDocumentFromMiniDocList() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52139");

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.ViewInDocView();

		docViewPage.codeSameAsLastInMiniDocListLastDocument();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager '" + Input.rmu1userName + "'");

	}

	/**
	 * @Author : Baskar date:15/9/21 Modified date: NA Modified by: Baskar
	 * @TODO:BUG IN APPLICATION. EXPECT FAILURE ON THIS TEST CASE
	 * @Description : Verify when user clicks code same as last on selecting the
	 *              code same as action for document selected from mini doc list
	 *              child window in context of a security group
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 16)
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

		loginPage.logout();
		docViewPage.codeSameAsLastSelectDocInDocList(assgnColour, Input.stampSelection, Input.stampSelection,
				Input.stampSelection);
	}

	/**
	 * @Author : Baskar date:15/9/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify that after loading additional documents in mini doc
	 *              list 'Code same as last' should be working
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 17)
	public void afterLoadingDisplayLastDocumentShouldClick() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52135");
		String AssignStamp = "Loading" + Utility.dynamicNameAppender();

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentWithSaveButtonForRmu(AssignStamp, Input.codingFormName);
		baseClass.waitForElement(assignmentPage.getAssignmentAction_ViewinDocView());
		assignmentPage.getAssignmentAction_ViewinDocView().waitAndClick(5);
		baseClass.stepInfo("Assignment selected and viewAllDocs in docview");
		docViewPage.loadingDisplayLastDocumentBtnClick();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager '" + Input.rmu1userName + "'");

	}

	/**
	 * @Author : Baskar date:20/9/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify when user clicks code same as last on selecting the
	 *              code same as action for document selected from mini doc list in
	 *              context of a security group
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 18)
	public void codeSameAsLastMiniDocList() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52130");
		String creatingAssgn = "Assign" + Utility.dynamicNameAppender();
		String assgnColour = "AssignC" + Utility.dynamicNameAppender();

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentWithSaveButtonForRmu(creatingAssgn, Input.codingFormName);
		baseClass.waitForElement(assignmentPage.getAssignmentAction_ViewinDocView());
		assignmentPage.getAssignmentAction_ViewinDocView().waitAndClick(5);
		baseClass.stepInfo("Assignment selected and viewAllDocs in docview");

		docViewPage.codeSameAsMiniDocList(assgnColour, Input.stampSelection, Input.stampSelection,
				Input.stampSelection);

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager '" + Input.rmu1userName + "'");

	}

	/**
	 * @Author : Baskar date:20/9/21 Modified date: NA Modified by: Baskar
	 * @Description : Coding form child window: Verify when user clicks 'code same
	 *              as last' after saving the document on applying the stamp
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 19)
	public void moveToOtherDocsClickCodeSameAsLast() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52129");
		String assgnColour = "AssignC" + Utility.dynamicNameAppender();
		String assgnFolder = "AFolder" + Utility.dynamicNameAppender();

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkFolder(assgnFolder);
		tagsAndFoldersPage.ViewinDocViewthrFolder(assgnFolder);

		docViewPage.codeSameAsLastChildWindow(assgnColour, Input.stampSelection, Input.stampSelection,
				Input.stampSelection);
		driver.waitForPageToBeReady();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager '" + Input.rmu1userName + "'");
	}

	/**
	 * @Author : Baskar date:21/9/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify that 'Save and Next' should not be displayed on doc
	 *              view in context of an assignment
	 * 
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 20)
	public void saveAndNextShouldNotDisplay() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52100");
		String AssignStamp = "Assignment" + Utility.dynamicNameAppender();
		// Login As Reviewer
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// searching document for assignmnet creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(AssignStamp, Input.codingFormName);
		assignmentPage.assignmentDistributingToReviewer();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rev1userName + "'");

		// selecting the assignment
		assignmentPage.SelectAssignmentByReviewer(AssignStamp);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// CodingForm ChildWindow should open popup after assignment creation
		docViewPage.shouldNotDisplaySaveAndNext();

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

	}

	/**
	 * Author : Baskar date: NA Modified date: 21/9/2021 Modified by: Baskar
	 * Description:Verify that for RMU/Reviewer coding stamps option should be
	 * displayed in context of security group when navigating from Session Search >
	 * Doc List
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 21)
	public void codingStampShouldDisplayWhnNavigateFromDocList() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52105");
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocList();

		// CodingStamp popup verfiy
		docViewPage.navigateFromDocListCodingStampShouldDisplay();

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager'" + Input.rmu1userName + "'");

	}

	/**
	 * Author : Baskar date: NA Modified date: 21/9/2021 Modified by: Baskar
	 * Description:Verify that for RMU/Reviewer coding stamps option should be
	 * displayed in context of security group when navigating from Saved Search
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 22)
	public void codingStampShouldDisplayWhnNavigateFromSavedSearch() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52104");
		String savedSearchs = "AsavedToDocview" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(savedSearchs);
		savedSearch.savedSearchToDocView(savedSearchs);
		baseClass.stepInfo("User navigated to docviiew page from saved search page");

		// CodingStamp popup verfiy
		docViewPage.navigateFromSavedSearchCodingStampShouldDisplay();

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager'" + Input.rmu1userName + "'");

	}

	/**
	 * @Author : Baskar date:21/9/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify when user clicks the saved stamp on selecting the code
	 *              same as action for document selected from mini doc list in
	 *              context of a security group
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 23)
	public void codeSameAsCodingStampLast() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52072");
		String assgnColour = "Textboxname" + Utility.dynamicNameAppender();

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		docViewPage.codeSameAsMiniDocListStampLastIcon(assgnColour, Input.stampSelection, Input.stampSelection,
				Input.stampSelection);

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager '" + Input.rmu1userName + "'");
	}

	/**
	 * @Author : Baskar date:14/9/21 Modified date: NA Modified by: Baskar
	 * @Description : Coding Form child window updates with parent window
	 * 
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 24)
	public void verifyingUpdateParentAndChildWindow() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52156");
		String AssignStamp = "StampPopUp" + Utility.dynamicNameAppender();
		String assgnColour = "AssignC" + Utility.dynamicNameAppender();
		// Login As Reviewer
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// searching document for assignmnet creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(AssignStamp, Input.codingFormName);
		assignmentPage.toggleSaveButton();
		assignmentPage.assignmentDistributingToReviewer();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager '" + Input.rmu1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rev1userName + "'");

		// selecting the assignment
		assignmentPage.SelectAssignmentByReviewer(AssignStamp);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		docViewPage.codingStampVerificationBothParentAndChildWindow(assgnColour, Input.stampColours,
				Input.stampColours);

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

	}

	/**
	 * @Author : Baskar date:24/9/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify when user saves the coding stamp in context of SG and
	 *              navigate to Doc view from Assignment, application should allow
	 *              the user to save new coding stamp when same coding form assigned
	 *              to SG and assignment
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 25)
	public void verifyApplicationAllowToSaveCodinStampWithImpersonate() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52103");
		String AssignStamp = "StampPopUp" + Utility.dynamicNameAppender();
		String assgnColour = "AssignC" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to DocView
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.ViewInDocView();

		// DocView To select coding stamp colour
		docViewPage.selectingStampColour(assgnColour, Input.stampColours);
		driver.getWebDriver().get(Input.url + "Search/Searches");

		// Session search to assignment creation
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(AssignStamp, Input.codingFormName);
		assignmentPage.toggleSaveButton();
		assignmentPage.assignmentDistributingToReviewerManager();
		baseClass.passedStep("Assignment created and assigned to reviewer");

		// Impersonate to Reviewer
		baseClass.impersonateRMUtoReviewer();
		baseClass.stepInfo("User '" + Input.rmu1userName + "' impersonate as Reviewer");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(AssignStamp);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// DocView To select coding stamp colour in assignment level
		docViewPage.selectingStampColour(assgnColour, Input.stampColours);

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");
	}

	/**
	 * @Author : Baskar date:15/9/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify when user clicks 'Code same as last' when document not
	 *              part of mini doc list is viewed from analytics panel/analytics
	 *              panel child window
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 26)
	public void clickAnalyticalPanelDoc() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52137");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewNearDupeDocumentsInDocView();

		docViewPage.analyticalDocs();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager '" + Input.rmu1userName + "'");

	}

	/**
	 * @Author : Baskar date:15/9/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify when user clicks 'Code same as last' when document part
	 *              of mini doc list is viewed from analytics panel/analytics panel
	 *              child window
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 27)
	public void clickAnalyticalPanelDocPartOfMiniDocList() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52136");

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewNearDupeDocumentsInDocView();

		docViewPage.analyticalDocsPartOfMiniDocList();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager '" + Input.rmu1userName + "'");

	}

	/**
	 * @Author : Baskar date:15/9/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify when user clicks the 'Code same as last' on selecting
	 *              the code same as action for document selected from analytics
	 *              panel child window in context of a security group
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 28)
	public void codeSameAsLastInAnalyticalChildWindow() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52133");
		String assgnColour = "AssignC" + Utility.dynamicNameAppender();
		String assgnFolder = "AFolder" + Utility.dynamicNameAppender();

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkFolderNearDupe(assgnFolder);
		tagsAndFoldersPage.ViewinDocViewthrFolder(assgnFolder);

		// DocView
		docViewPage.codeSameAsAnalyticalChildWindow(assgnColour, Input.stampSelection, Input.stampSelection,
				Input.stampSelection);

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager '" + Input.rmu1userName + "'");

	}

	/**
	 * @Author : Baskar date:20/9/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify when user clicks the 'Code same as last' on selecting
	 *              the code same as action for document selected from analytics
	 *              panel child window in context of a security group
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 29)
	public void codeSameAsLastInAnalyticalPanel() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52131");
		String assgnColour = "AssignC" + Utility.dynamicNameAppender();
		String assgnFolder = "AFolder" + Utility.dynamicNameAppender();

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Session search to doc view after creating folder
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkFolderNearDupe(assgnFolder);
		tagsAndFoldersPage.ViewinDocViewthrFolder(assgnFolder);

		docViewPage.codeSameAsLastAnalytical(assgnColour, Input.stampSelection, Input.stampSelection,
				Input.stampSelection);

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager '" + Input.rmu1userName + "'");

	}

	/**
	 * @Author : Baskar date:20/9/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify when user clicks the Save and Next on selecting the
	 *              code same as action for document selected from mini doc list in
	 *              context of a security group
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 30)
	public void codeSameAsSaveAndNext() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52107");
		String creatingAssgn = "Assign" + Utility.dynamicNameAppender();
		String assgnColour = "AssignC" + Utility.dynamicNameAppender();

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Session search to doc view Coding Form after creating assignment
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewNearDupeDocumentsToBulkAssign();
		assignmentPage.assignmentWithSaveButtonForRmu(creatingAssgn, Input.codingFormName);
		baseClass.waitForElement(assignmentPage.getAssignmentAction_ViewinDocView());
		assignmentPage.getAssignmentAction_ViewinDocView().waitAndClick(5);
		baseClass.stepInfo("Assignment selected and viewAllDocs in docview");

		docViewPage.codeSameAsSaveAndNext();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager '" + Input.rmu1userName + "'");

	}

	/**
	 * @Author : Baskar date:2/9/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify that already saved stamp presentation should be
	 *              displayed with post-fix as 'Assigned' in coding stamp pop up
	 *              form Coding Form panel
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 31)
	public void verifyingCodingStampPostFixColour() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52224");
		String AssignStamp = "StampPopUp" + Utility.dynamicNameAppender();
		String assgnColour = "AssignC" + Utility.dynamicNameAppender();
		// Login As Reviewer
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		// CodingForm ChildWindow should open popup
		docViewPage.openStampPopUpFromParentWindowWindow(assgnColour, Input.stampColours, Input.stampColours);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		// searching document for assignmnet creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(AssignStamp, Input.codingFormName);
		assignmentPage.assignmentDistributingToReviewer();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rev1userName + "'");

		// selecting the assignment
		assignmentPage.SelectAssignmentByReviewer(AssignStamp);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// CodingForm ChildWindow should open popup after assignment creation
		docViewPage.openStampPopUpFromParentWindowWindow(assgnColour, Input.stampColours, Input.stampColours);

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");
	}

	/**
	 * @Author : Baskar date:09/11/21 Modified date: NA Modified by: Baskar
	 * @Description :Verify that for RMU/Reviewer code same as last option should be
	 *              displayed in context of security group when navigating from
	 *              Saved Search
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 32)
	public void verifyCodeSameAsLastDisplayedFromSavedSearch() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52145");
		baseClass.stepInfo("Verify that for RMU/Reviewer code same as last option should be displayed "
				+ "in context of security group when navigating from Saved Search");
		String savedSearchs = "ASavedSearch" + Utility.dynamicNameAppender();

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

//		Navigation to docview from saved search
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(savedSearchs);
		savedSearch.savedSearchToDocView(savedSearchs);
		baseClass.stepInfo("User navigated to docviiew page from saved search page");

		docViewPage.codeSameAsLastIsDisplayed();

		loginPage.logout();

		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");
	}

	/**
	 * @Author : Baskar date:09/11/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify when user clicks 'code same as last' after saving the
	 *              document on applying the stamp
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 33)
	public void afterApplyingStampClickOnCodeSameAsLast() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52126");
		baseClass.stepInfo(
				"Verify when user clicks 'code same as last' after saving the document on applying the stamp");
		String assgnColour = "AssignC" + Utility.dynamicNameAppender();

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		docViewPage.selectPureHit();

		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch(Input.searchStringStar);

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		docViewPage.codeSameAsLastAfterStamp(assgnColour, Input.stampSelection, Input.stampSelection);

		loginPage.logout();

		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");
	}

	/**
	 * @Author : Baskar date:09/11/21 Modified date: NA Modified by: Baskar
	 * @Description :Verify SA/DA/PA after impersonating as RMU/Reviewer role edits
	 *              the coding form and clicks 'Save and Next' button in context of
	 *              security group
	 */
	@DataProvider(name = "userDetails")
	public Object[][] userLoginSaPaRmu() {
		return new Object[][] { { "sa", Input.sa1userName, Input.sa1password, "rmu" },
				{ "sa", Input.sa1userName, Input.sa1password, "rev" },
				{ "da", Input.da1userName, Input.da1password, "rmu" },
				{ "da", Input.da1userName, Input.da1password, "rev" },
				{ "pa", Input.pa1userName, Input.pa1password, "rmu" },
				{ "pa", Input.pa1userName, Input.pa1password, "rev" } };
	}

	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 34)
	public void afterImpersonationUserEditCodingForm(String roll, String userName, String password, String impersonate)
			throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52097");
		baseClass.stepInfo("Verify SA/DA/PA after impersonating as RMU/Reviewer role "
				+ "edits the coding form and clicks 'Save and Next' button in context of security group");
		// Login As
		loginPage.loginToSightLine(userName, password);

		baseClass.credentialsToImpersonateAsRMUREV(roll, impersonate);

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		docViewPage.verifyAfterImpersonateUserCanEditCodingForm();

		loginPage.logout();

	}

	/**
	 * @Author : Baskar date:09/11/21 Modified date: NA Modified by: Baskar
	 * @Description :Verify user with RMU/Reviewer role edits the coding form and
	 *              clicks 'Save and Next' button in context of security group
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 35)
	public void editCodingFormWithSaveAndNext() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52096");
		baseClass.stepInfo("Verify user with RMU/Reviewer role edits the coding form "
				+ "and clicks 'Save and Next' button in context of security group");
		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		docViewPage.verifyAfterImpersonateUserCanEditCodingForm();

		loginPage.logout();
	}

	/**
	 * @Author : Baskar date:09/11/21 Modified date: NA Modified by: Baskar
	 * @Description :Verify when clicking the 'Save and Next' button after clicking
	 *              the saved stamp in context of security group
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 36)
	public void clickSaveAndNextAfterSavedStamp() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52095");
		baseClass.stepInfo("Verify when clicking the 'Save and Next' button after "
				+ "clicking the saved stamp in context of security group");
		String assgnColour = "AssignC" + Utility.dynamicNameAppender();

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		docViewPage.saveAndNextWithSavedStamp(assgnColour, Input.stampSelection);

		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 11/10/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify that document is selected to view from history drop
	 *              down selection when hits panel is open then enable/disable
	 *              should be retained
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 37)
	public void verifyHitPanelUsingSaveAndNext() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52094");
		baseClass.stepInfo("Verify that on clicking 'Save and Next' "
				+ "when hits panel is open then enable/disable should be " + "retained in context of security group");
		String hitTerms = "Than" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

//		Add keywords
		this.driver.getWebDriver().get(Input.url + "Keywords/Keywords");
		keywordPage.AddKeyword(hitTerms, hitTerms);

//		Go to docview via advanced search
		sessionSearch.advancedContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		docViewPage.getPersistentHit(hitTerms);
		docViewPage.verifyPersistentHitPanelUsingSaceAndNext(hitTerms);

		this.driver.getWebDriver().get(Input.url + "Keywords/Keywords");
		driver.waitForPageToBeReady();
		keywordPage.getDeleteButton(hitTerms).waitAndClick(5);
		keywordPage.getYesButton().waitAndClick(5);
		driver.waitForPageToBeReady();

		loginPage.logout();

	}

	/**
	 * @Author : Baskar date:09/11/21 Modified date: NA Modified by: Baskar
	 * @Description :To verify when user edits the coding form of main document and
	 *              without saving selects action 'Remove Code same as this' from
	 *              family members on documents selection
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 38)
	public void editCodingFormAndRemoveCodeSameAs() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-51220");
		baseClass.stepInfo("To verify when user edits the coding form of main document "
				+ "and without saving selects action 'Remove Code same as this' from "
				+ "family members on documents selection");

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.viewDocViewFamilyMemberDocuments();

		docViewPage.editAndRemoveCodeSameAS();

		loginPage.logout();
	}

	/**
	 * @Author : Mohan date:15/11/21 Modified date: NA Modified by: NA
	 * @Description :Verify that new TAGs gets added on Coding Form Screen
	 *              'RPMXCON-53994'
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 39)
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
		baseClass.passedStep("New Tags are added on Coding Form Screen Successfully");

		loginPage.logout();
	}

	/**
	 * @Author : Mohan date:15/11/21 Modified date: NA Modified by: NA
	 * @Description :Verify that User (RMU) Saves customized coding form name only
	 *              without any objects 'RPMXCON-53951'
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 40)
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
	 * @Description :Verify that Preview displays correctly and properly for Static
	 *              object on coding form screen 'RPMXCON-54057'
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 41)
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
		baseClass.passedStep(
				"Preview is displayed correctly and properly for Static object along with all configured options successfully");
		loginPage.logout();

	}

	/**
	 * @Author : Mohan date:16/11/21 Modified date: NA Modified by: NA
	 * @Description :Verify that Comments gets added into Coding Form Screen
	 *              successfully 'RPMXCON-54134'
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 42)
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
	@Test(enabled = true, groups = { "regression" }, priority = 43)
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
	 * @Author : Baskar date:23/11/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify coding stamp should be applied to saved documents in an
	 *              assignment
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 44)
	public void codingStampShouldApply() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-51002");
		baseClass.stepInfo("Verify coding stamp should be applied to saved documents in an assignment");
		String AssignStamp = "StampPopUp" + Utility.dynamicNameAppender();
		String assgnColour = "AssignC" + Utility.dynamicNameAppender();
		// Login As Reviewer
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// searching document for assignmnet creation
		sessionSearch.basicContentSearch(Input.searchString2);
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

		docViewPage.codingStampForSavedDocument(assgnColour, Input.stampSelection);

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");
	}

	/**
	 * @Author : Baskar date:23/11/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify coding form for document after applying coding stamp
	 *              when same coding form is applied to assignment and security
	 *              group and document is viewed from basic search
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 45)
	public void codingStampShouldDisplayToSGAndAssgn() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-51003");
		baseClass.stepInfo("Verify coding form for document after applying coding stamp "
				+ "when same coding form is applied to assignment and security group "
				+ "and document is viewed from basic search");
		String AssignStamp = "StampPopUp" + Utility.dynamicNameAppender();
		String assgnColour = "AssignC" + Utility.dynamicNameAppender();
		// Login As Reviewer
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// searching document for assignmnet creation
		sessionSearch.basicContentSearch(Input.searchString2);
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

		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		docViewPage.stampReVerificationFromSG(docId);

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");
	}

	/**
	 * @Author : Baskar date: NA Modified date:24/11/2021 Modified by: Baskar
	 * @Description :Verify validation of coding form when coding form is created
	 *              with metadata field as Integer on saving of the stamp
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 46)
	public void metaDataUsingInteger() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-51587");
		baseClass.stepInfo("Verify validation of coding form when coding form is "
				+ "created with metadata field as Integer on saving of the stamp");
		String formName = "CFMetaData" + Utility.dynamicNameAppender();
		String assgnCoding = "codingAssgn" + Utility.dynamicNameAppender();
		String intData = "INT";
		String Defaultaction = "Make It Required";
		String stamptext = "Stamp" + Utility.dynamicNameAppender();
		String alpha = "a@";
		UtilityLog.info("Started Execution for prerequisite");

		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");

		// Custom Field created with INT DataType
		projectPage.clickingManageButton();
		projectPage.addMetaDataFieldUsingIntergerType(tinyInt, intData, Input.docBasic, Input.tinyInt);
		projectPage.addMetaDataFieldUsingIntergerType(smallInt, intData, Input.docBasic, Input.smallInt);
		projectPage.addMetaDataFieldUsingIntergerType(avearageInt, intData, Input.docBasic, Input.averageInt);
		projectPage.addMetaDataFieldUsingIntergerType(bigInt, intData, Input.docBasic, Input.bigInt);
		projectPage.addMetaDataFieldUsingIntergerType(hugeInt, intData, Input.docBasic, Input.hugeInt);
		baseClass.stepInfo("Custom meta data field created with integer");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(tinyInt);
		securityGroupPage.addProjectFieldtoSG(smallInt);
		securityGroupPage.addProjectFieldtoSG(avearageInt);
		securityGroupPage.addProjectFieldtoSG(bigInt);
		securityGroupPage.addProjectFieldtoSG(hugeInt);
		baseClass.stepInfo("Custom metadata integer field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.creatingCodingFormusingMultipleInteger(formName, tinyInt, smallInt, avearageInt, bigInt, hugeInt,
				Defaultaction);
		baseClass.stepInfo("Coding form created with metadata fieldValue");

		// Session search to doc view to create assignment
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();

		assignmentPage.assignmentCreation(assgnCoding, formName);
		assignmentPage.assignmentDistributingToReviewer();

		// logout
		loginPage.logout();

		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		docViewPage.verifyingIntergerMetaData(alpha, stamptext, Input.stampSelection, tinyInt, smallInt, avearageInt,
				bigInt, hugeInt);

		// logout
		loginPage.logout();

//		Doing house keeping activity
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		assignmentPage.deleteAssgnmntUsingPagination(assgnCoding);
		codingForm.deleteCodingForm(formName, formName);

		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 26/11/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify that when Doc View page is refreshed/re-visited then
	 *              'Code same as last' should not be enable
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 47)
	public void refreshAndVerifyCodeSameAsLast() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52149");
		baseClass.stepInfo("Verify that when Doc View page is refreshed/re-visited "
				+ "then 'Code same as last' should not be enable");
		String savedSearchs = "Asaved" + Utility.dynamicNameAppender();

		// Login as a Rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		driver.waitForPageToBeReady();

		// searching for document and get saving
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.saveSearch(savedSearchs);
		savedSearch.savedSearchToDocView(savedSearchs);
		baseClass.stepInfo("user on the saved search page");

		// Verify code same as last disable in docview page
		docViewPage.verifyCodeSameAsLastDisable();
		driver.waitForPageToBeReady();
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 26/11/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify that for RMU/Reviewer coding stamps option should be
	 *              displayed in context of security group when navigating from Tags
	 *              and Folder
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 48)
	public void codingStampDisplayFromTagsAndFolder() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52106");
		baseClass.stepInfo("Verify that for RMU/Reviewer coding stamps option should be "
				+ "displayed in context of security group when navigating from Tags and Folder");
		String assgnFolder = "AFolder" + Utility.dynamicNameAppender();
		String assgnTag = "ATag" + Utility.dynamicNameAppender();

		// Login as a Rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		driver.waitForPageToBeReady();

		// searching for document and making folder
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkFolder(assgnFolder);
		tagsAndFoldersPage.ViewinDocViewthrFolder(assgnFolder);
		baseClass.stepInfo("user on the docview page after creating folder");

		// verify coding stamp button on docview panel after folder creation
		reusableDocView.stampButtonShouldDisplay();

		// searching for document and making tag
		driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionSearch.bulkTag(assgnTag);
		tagsAndFoldersPage.ViewinDocViewthrTag(assgnTag);
		baseClass.stepInfo("user on the docview page after creating tag");

		// verify coding stamp button on docview panel after folder creation
		reusableDocView.stampButtonShouldDisplay();

		driver.waitForPageToBeReady();
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 26/11/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify that for RMU/Reviewer code same as last option should
	 *              be displayed in context of security group when navigating from
	 *              Tags and Folder
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 49)
	public void codeSameLastDisplayFromTagsAndFolder() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52146");
		baseClass.stepInfo("Verify that for RMU/Reviewer code same as last option should be "
				+ "displayed in context of security group when navigating from Tags and Folder");
		String assgnFolder = "AFolder" + Utility.dynamicNameAppender();
		String assgnTag = "ATag" + Utility.dynamicNameAppender();

		// Login as a Rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		driver.waitForPageToBeReady();

		// searching for document and making folder
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkFolder(assgnFolder);
		tagsAndFoldersPage.ViewinDocViewthrFolder(assgnFolder);
		baseClass.stepInfo("user on the docview page after creating folder");

		// verify coding stamp button on docview panel after folder creation
		reusableDocView.codeSameAsLastDisplay();

		// searching for document and making tag
		driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionSearch.bulkTag(assgnTag);
		tagsAndFoldersPage.ViewinDocViewthrTag(assgnTag);
		baseClass.stepInfo("user on the docview page after creating tag");

		// verify coding stamp button on docview panel after folder creation
		reusableDocView.codeSameAsLastDisplay();

		driver.waitForPageToBeReady();
		loginPage.logout();
	}

	/**
	 * @Author : Iyappan.Kasinathan
	 * @Description : On mouse hover tool tip should be displayed for the saved
	 *              stamp with the coding form of completed document when document
	 *              completed by Code same as this action
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 50)
	public void verifySavedStampToolTipByCodeSameAsAction() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51575");
		baseClass.stepInfo("On mouse hover tool tip should be displayed for the saved stamp with the coding form of "
				+ "completed document when document completed by Code same as this action");
		String assignmentName = "assignment" + Utility.dynamicNameAppender();
		// Login as a Rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		driver.waitForPageToBeReady();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		// create assignment
		assignmentPage.assignmentCreation(assignmentName, Input.codingFormName);
		assignmentPage.add2ReviewerAndDistribute();
		baseClass.impersonateRMUtoReviewer();
		assignmentPage.SelectAssignmentByReviewer(assignmentName);
		reusableDocView.clickCheckBoxDocListActionCodeSameAs();
		baseClass.stepInfo("Code same as action performed");
		baseClass.passedStep("Chain link icon is displayed as expected");
		reusableDocView.editingCodingFormWithCompleteButton();
		baseClass.passedStep("Completed icon is displayed as expected");
		reusableDocView.stampColourSelection(Input.searchString2, Input.stampColour);
		docViewPage.verifySavedStampTooltip(Input.searchString2, Input.stampColour);
		baseClass.stepInfo("Tooltip successfully verified in parent window");
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		String parentId = reusableDocView.switchTochildWindow();
		docViewPage.verifySavedStampTooltip(Input.searchString2, Input.stampColour);
		baseClass.stepInfo("Tooltip successfully verified in child window");
		reusableDocView.childWindowToParentWindowSwitching(parentId);
		baseClass.stepInfo("successfully verifed the validations as rmu user");
		loginPage.logout();
		// login as reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		assignmentPage.SelectAssignmentByReviewer(assignmentName);
		// validations
		reusableDocView.clickCheckBoxDocListActionCodeSameAs();
		baseClass.stepInfo("Code same as action performed");
		baseClass.passedStep("Chain link icon is displayed as expected");
		reusableDocView.editingCodingFormWithCompleteButton();
		baseClass.passedStep("Completed icon is displayed as expected");
		reusableDocView.stampColourSelection(Input.searchString2, Input.stampColour);
		docViewPage.verifySavedStampTooltip(Input.searchString2, Input.stampColour);
		baseClass.stepInfo("Tooltip successfully verified in parent window");
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		String parentId2 = reusableDocView.switchTochildWindow();
		docViewPage.verifySavedStampTooltip(Input.searchString2, Input.stampColour);
		baseClass.stepInfo("Tooltip successfully verified in child window");
		reusableDocView.childWindowToParentWindowSwitching(parentId2);
		loginPage.logout();
		// delete assignment
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignmentPage.deleteAssgnmntUsingPagination(assignmentName);
		softAssertion.assertAll();
		loginPage.logout();
	}

	/**
	 * @Author : Iyappan.Kasinathan
	 * @Description : On mouse hover tool tip should be displayed for the saved
	 *              stamp with the coding form of completed document when document
	 *              completed by Complete same as last
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 51)
	public void verifySavedStampToolTipByCompleteSameAsLast() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51576");
		baseClass.stepInfo("On mouse hover tool tip should be displayed for the saved stamp with the coding form of "
				+ "completed document when document completed by Complete same as last");
		String assignmentName = "assignment" + Utility.dynamicNameAppender();
		// Login as a Rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		driver.waitForPageToBeReady();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		// create assignment
		assignmentPage.assignmentCreation(assignmentName, Input.codingFormName);
		assignmentPage.add2ReviewerAndDistribute();
		baseClass.impersonateRMUtoReviewer();
		assignmentPage.SelectAssignmentByReviewer(assignmentName);
		reusableDocView.editCodingForm(Input.searchString1);
		reusableDocView.clickCheckBoxDocListActionCodeSameAs();
		baseClass.stepInfo("Code same as action performed");
		baseClass.passedStep("Chain link icon is displayed as expected");
		reusableDocView.editingCodingFormWithCompleteButton();
		baseClass.passedStep("Completed icon is displayed as expected");
		docViewPage.navigateToUncompleteDoc();
		reusableDocView.clickCodeSameAsLast();
		docViewPage.getHeader().waitAndClick(5);
		baseClass.passedStep("Completed icon is displayed as expected after code same last as action performed");
		softAssertion.assertEquals(reusableDocView.getDocView_DefaultViewTab().Displayed().booleanValue(), true);
		baseClass.passedStep("Document displaying in default view page");
		reusableDocView.editCodingForm(Input.searchString1);
		reusableDocView.stampColourSelection(Input.searchString2, Input.stampColour);
		docViewPage.verifySavedStampTooltip(Input.searchString2, Input.stampColour);
		baseClass.stepInfo("Tooltip successfully verified in parent window");
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		String parentId = reusableDocView.switchTochildWindow();
		docViewPage.verifySavedStampTooltip(Input.searchString2, Input.stampColour);
		baseClass.stepInfo("Tooltip successfully verified in child window");
		reusableDocView.childWindowToParentWindowSwitching(parentId);
		baseClass.stepInfo("successfully verifed the validations as rmu user");
		loginPage.logout();
		// login as reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		assignmentPage.SelectAssignmentByReviewer(assignmentName);
		reusableDocView.editCodingForm(Input.searchString1);
		// validations
		reusableDocView.clickCheckBoxDocListActionCodeSameAs();
		baseClass.stepInfo("Code same as action performed");
		baseClass.passedStep("Chain link icon is displayed as expected");
		reusableDocView.editingCodingFormWithCompleteButton();
		baseClass.passedStep("Completed icon is displayed as expected");
		docViewPage.navigateToUncompleteDoc();
		reusableDocView.clickCodeSameAsLast();
		docViewPage.getHeader().waitAndClick(5);
		baseClass.passedStep("Completed icon is displayed as expected after code same last as action performed");
		softAssertion.assertEquals(reusableDocView.getDocView_DefaultViewTab().Displayed().booleanValue(), true);
		baseClass.passedStep("Document displaying in default view page");
		reusableDocView.editCodingForm(Input.searchString1);
		reusableDocView.stampColourSelection(Input.searchString2, Input.stampColour);
		docViewPage.verifySavedStampTooltip(Input.searchString2, Input.stampColour);
		baseClass.stepInfo("Tooltip successfully verified in parent window");
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		String parentId2 = reusableDocView.switchTochildWindow();
		docViewPage.verifySavedStampTooltip(Input.searchString2, Input.stampColour);
		baseClass.stepInfo("Tooltip successfully verified in child window");
		reusableDocView.childWindowToParentWindowSwitching(parentId2);
		loginPage.logout();
		// delete assignment
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.selectproject();
		assignmentPage.deleteAssgnmntUsingPagination(assignmentName);
		softAssertion.assertAll();
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 30/11/2021 Modified date: NA Modified by: Baskar
	 * @Description:Verify when user clicks the saved stamp on selecting the code
	 *                     same as action for document selected from analytics panel
	 *                     in context of a security group
	 */
	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 52)
	public void selAnalyticalDocChainLinkShouldDisplay(String fullName, String userName, String password)
			throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52073");
		baseClass.stepInfo("Verify when user clicks the saved stamp on selecting the code same as action "
				+ "for document selected from analytics panel in context of a security group");
		String fieldText = "Stamp" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();
		String rmu = "RMU";
		// Login As
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rev1userName + "'");

		if (fullName.contains(rmu)) {
			codingForm.assignCodingFormByCondition(Input.codingFormName);
		}

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewNearDupeDocumentsInDocView();

		// select doc from analytics action as code same
		docViewPage.verifyAnalyticsCodeSameAs(fieldText, comment);

		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 30/11/2021 Modified date: NA Modified by: Baskar
	 * @Description:Verify color stamps which are saved should not be clickable from
	 *                     coding stamp pop up
	 */
	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 53)
	public void savedStampColourNotClickable(String fullName, String userName, String password)
			throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-50998");
		baseClass.stepInfo("Verify color stamps which are saved should not be " + "clickable from coding stamp pop up");
		String fieldText = "Stamp" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();
		String rmu = "RMU";
		// Login As
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rev1userName + "'");

		if (fullName.contains(rmu)) {
			codingForm.assignCodingFormByCondition(Input.codingFormName);
		}

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		// saved stamp should not clickable again if assigned
		docViewPage.verifySavedColourNotClickable(fieldText, comment);

		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 30/11/2021 Modified date: NA Modified by: Baskar
	 * @Description:Coding Form Child Window: Verify that for PA impersonating as
	 *                     RMU/Reviewer coding stamps option should be displayed
	 *                     outside the context of an assignment
	 */
	@Test(enabled = true, dataProvider = "paToRmuRev", groups = { "regression" }, priority = 54)
	public void verifyCodingStampOptionFromChildWindowCF(String roll, String userName, String password,
			String impersonate) throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52041");
		baseClass.stepInfo("Coding Form Child Window: Verify that for PA impersonating as RMU/Reviewer "
				+ "coding stamps option should be displayed outside the context of an assignment");

		// Login As
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rev1userName + "'");

		baseClass.paImpersonateAsRMUREV(roll, impersonate);

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		docViewPage.selectPureHit();

		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch(Input.testData1);

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		docViewPage.verifyCodingStampDisplay();

		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 30/11/2021 Modified date: NA Modified by: Baskar
	 * @Description:Verify colors available for the coding stamp from doc view and
	 *                     coding stamp pop up in context of security group
	 */
	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 55)
	public void verifyDefaultStampColour(String fullName, String userName, String password)
			throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52046");
		baseClass.stepInfo("Verify colors available for the coding stamp from "
				+ "doc view and coding stamp pop up in context of security group");
		String rmu = "RMU";
		// Login As
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rev1userName + "'");

		if (fullName.contains(rmu)) {
			codingForm.assignCodingFormByCondition(Input.codingFormName);
		}

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		docViewPage.selectPureHit();

		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch(Input.testData1);
		sessionSearch.ViewInDocViews();

		List<String> cfDefault = reusableDocView.getDefaultPopUpStampColour();
		List<String> popUpDefault = reusableDocView.getDefaultCodingFormColour();
		softAssertion.assertEquals(cfDefault, popUpDefault);
		baseClass.passedStep("Default stamp colour are same in popup window and coding form");

		// logout
		loginPage.logout();
		softAssertion.assertAll();

	}

	/**
	 * @Author : Baskar date: 1/12/2021 Modified date: NA Modified by: Baskar
	 * @Description:Verify color stamps which are saved should not be clickable from
	 *                     save coding stamp pop up in context of security group
	 */
	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 56)
	public void savedStampColourNotClickableSG(String fullName, String userName, String password)
			throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52047");
		baseClass.stepInfo("Verify color stamps which are saved should not be clickable "
				+ "from save coding stamp pop up in context of security group");
		String fieldText = "Stamp" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();
		String rmu = "RMU";
		// Login As
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rev1userName + "'");

		if (fullName.contains(rmu)) {
			codingForm.assignCodingFormByCondition(Input.codingFormName);
		}

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		docViewPage.selectPureHit();

		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch(Input.testData1);
		sessionSearch.ViewInDocViews();

		// saved stamp should not clickable again if assigned
		docViewPage.verifySavedColourNotClickable(fieldText, comment);

		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 1/12/2021 Modified date: NA Modified by: Baskar
	 * @Description:Coding Form Child window: Verify color stamps which are saved
	 *                     should not be clickable from coding stamp pop up in
	 *                     context of security group
	 */
	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 57)
	public void savedStampColourNotClickableSGChild(String fullName, String userName, String password)
			throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52048");
		baseClass.stepInfo("Coding Form Child window: Verify color stamps which are saved should not be "
				+ " clickable from coding stamp pop up in context of security group");
		String fieldText = "Stamp" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();
		String rmu = "RMU";
		// Login As
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rev1userName + "'");

		if (fullName.contains(rmu)) {
			codingForm.assignCodingFormByCondition(Input.codingFormName);
		}

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		docViewPage.selectPureHit();

		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch(Input.testData1);
		sessionSearch.ViewInDocViews();

		// saved stamp should not clickable again if assigned
		docViewPage.verifySavedColourNotClickableChildWindow(fieldText, comment);

		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 1/12/2021 Modified date: NA Modified by: Baskar
	 * @Description:Verify when user clicks 'Cancel' button coding stamp should not
	 *                     be saved from coding stamp pop up when all fields are
	 *                     entered in context of security group
	 */
	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 58)
	public void validateCancelButtonInParent(String fullName, String userName, String password)
			throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52049");
		baseClass.stepInfo("Verify when user clicks 'Cancel' button coding stamp should not be saved from coding stamp "
				+ "pop up when all fields are entered in context of security group");
		String fieldText = "Stamp" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();
		String rmu = "RMU";
		// Login As
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rev1userName + "'");

		if (fullName.contains(rmu)) {
			codingForm.assignCodingFormByCondition(Input.codingFormName);
		}

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		docViewPage.selectPureHit();

		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch(Input.testData1);
		sessionSearch.ViewInDocViews();

		// saved stamp should not clickable again if assigned
		reusableDocView.editCodingFormAndCancel(fieldText, Input.stampSelection, comment);

		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 1/12/2021 Modified date: NA Modified by: Baskar
	 * @Description:Coding Form Child window: Verify when user clicks 'Cancel'
	 *                     button coding stamp should not be saved from coding stamp
	 *                     pop up when all fields are entered in context of security
	 *                     group
	 */
	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 59)
	public void validateCancelButtonInChild(String fullName, String userName, String password)
			throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52050");
		baseClass.stepInfo(
				"Coding Form Child window: Verify when user clicks 'Cancel' button coding stamp should not be saved from coding "
						+ "stamp pop up when all fields are entered in context of security group");
		String fieldText = "Stamp" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();
		String rmu = "RMU";
		// Login As
		loginPage.loginToSightLine(userName, password);

		if (fullName.contains(rmu)) {
			codingForm.assignCodingFormByCondition(Input.codingFormName);
		}

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		docViewPage.selectPureHit();

		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch(Input.testData1);
		sessionSearch.ViewInDocViews();

		// saved stamp should not clickable again if assigned
		docViewPage.verifyCancelButtonInChild(fieldText, comment);

		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Iyappan.Kasinathan
	 * @Description : Verify tool tip should be displayed for the stamp icons on
	 *              mouse over after editing the stamp
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 60)
	public void verifyToolTipAfterEditingTheStamp() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51303");
		baseClass.stepInfo(
				"Verify tool tip should be displayed for the stamp icons on mouse over after editing the stamp");
		String assignmentName = "assignment" + Utility.dynamicNameAppender();
		// Login as a Rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		driver.waitForPageToBeReady();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		// create assignment
		assignmentPage.assignmentCreation(assignmentName, Input.codingFormName);
		assignmentPage.addReviewerAndDistributeDocs();
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignmentPage.viewSelectedAssgnUsingPagination(assignmentName);
		baseClass.waitTillElemetToBeClickable(assignmentPage.getAssignmentActionDropdown());
		assignmentPage.getAssignmentActionDropdown().waitAndClick(10);
		assignmentPage.assignmentActions("DocView");
		reusableDocView.stampColourSelection(Input.searchString2, Input.stampColour);
		driver.Navigate().refresh();
		reusableDocView.editStampColour(Input.searchString1, Input.stampColours, Input.stampColour);
		docViewPage.verifySavedStampTooltip(Input.searchString1, Input.stampColours);
		baseClass.stepInfo("Tooltip verified successfully");
		baseClass.stepInfo("Successfully verifed the validations as rmu user");
		loginPage.logout();
		// login as reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		assignmentPage.SelectAssignmentByReviewer(assignmentName);
		// validations
		reusableDocView.stampColourSelection(Input.searchString2, Input.stampColour);
		driver.Navigate().refresh();
		reusableDocView.editStampColour(Input.searchString2, Input.stampColours, Input.stampColour);
		docViewPage.verifySavedStampTooltip(Input.searchString2, Input.stampColours);
		baseClass.stepInfo("Tooltip verified successfully");
		baseClass.stepInfo("Successfully verifed the validations as reviewer");
		loginPage.logout();
		// delete assignment
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignmentPage.deleteAssgnmntUsingPagination(assignmentName);
		softAssertion.assertAll();
		loginPage.logout();
	}

	/**
	 * @Author : Iyappan.Kasinathan
	 * @Description : Verify tool tip on mouse over of the floppy icon to save the
	 *              stamp
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 61)
	public void verifyFloppyIconTooltip() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51302");
		baseClass.stepInfo("Verify tool tip on mouse over of the floppy icon to save the stamp");
		String assignmentName = "assignment" + Utility.dynamicNameAppender();
		// Login as a Rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		driver.waitForPageToBeReady();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		// create assignment
		assignmentPage.assignmentCreation(assignmentName, Input.codingFormName);
		assignmentPage.addReviewerAndDistributeDocs();
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignmentPage.viewSelectedAssgnUsingPagination(assignmentName);
		baseClass.waitTillElemetToBeClickable(assignmentPage.getAssignmentActionDropdown());
		assignmentPage.getAssignmentActionDropdown().waitAndClick(10);
		assignmentPage.assignmentActions("DocView");
		reusableDocView.verifyFloppyIconToolTip(floppyIconTooltip);
		baseClass.stepInfo("Successfully verifed the validations as rmu user");
		loginPage.logout();
		// login as reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		assignmentPage.SelectAssignmentByReviewer(assignmentName);
		// validations
		reusableDocView.verifyFloppyIconToolTip(floppyIconTooltip);
		baseClass.stepInfo("Successfully verifed the validations as reviewer");
		loginPage.logout();
		// delete assignment
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignmentPage.deleteAssgnmntUsingPagination(assignmentName);
		softAssertion.assertAll();
		loginPage.logout();
	}

	/**
	 * @Author : Iyappan.Kasinathan
	 * @Description : Verify when RMU/Reviewer clicks Code this document the same as
	 *              the last coded document, when immediately preceding document is
	 *              completed by clicking the 'Complete' button
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 62)
	public void verifyCodeSameAsLastAction() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51027");
		baseClass.stepInfo(
				"Verify when RMU/Reviewer clicks Code this document the same as the last coded document, when immediately"
						+ " preceding document is completed by clicking the 'Complete' button");
		String assignmentName = "assignment" + Utility.dynamicNameAppender();
		// Login as a Rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		driver.waitForPageToBeReady();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		// create assignment
		assignmentPage.assignmentCreation(assignmentName, Input.codingFormName);
		assignmentPage.add2ReviewerAndDistribute();
		baseClass.impersonateRMUtoReviewer();
		baseClass.stepInfo("Impersonated as reviewer");
		assignmentPage.SelectAssignmentByReviewer(assignmentName);
		// validations
		reusableDocView.editTextBoxInCodingFormWithCompleteButton(Input.searchString1);
		baseClass.passedStep("Tick icon is displayed in mini doc list after completing the respective document");
		reusableDocView.clickCodeSameAsLast();
		baseClass.passedStep(
				"Current document also completed as per previous code form pattern after codeSameAs action performed");
		baseClass.stepInfo("Successfully verifed the validations as review manager");
		loginPage.logout();
		// login as reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		assignmentPage.SelectAssignmentByReviewer(assignmentName);
		// validations
		reusableDocView.editTextBoxInCodingFormWithCompleteButton(Input.searchString1);
		baseClass.passedStep("Tick icon is displayed in mini doc list after completing the respective document");
		reusableDocView.clickCodeSameAsLast();
		baseClass.passedStep(
				"Current document also completed as per previous code form pattern after codeSameAs action performed");
		baseClass.stepInfo("Successfully verifed the validations as reviewer");
		loginPage.logout();
		// delete assignment
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignmentPage.deleteAssgnmntUsingPagination(assignmentName);
		softAssertion.assertAll();
		loginPage.logout();
	}

	/**
	 * @Author : Iyappan.Kasinathan
	 * @Description : Verify for RMU/Reviewer check mark should not be displayed in
	 *              the mini doc list for uncomplete document in doc view in context
	 *              of an assignment
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 63)
	public void verifyCheckMarkInUnCompleteDoc() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51024");
		baseClass.stepInfo(
				"Verify for RMU/Reviewer check mark should not be displayed in the mini doc list for uncomplete document in doc view in context of an assignment");
		String assignmentName = "assignment" + Utility.dynamicNameAppender();
		// Login as a Rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		driver.waitForPageToBeReady();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		// create assignment
		assignmentPage.assignmentCreation(assignmentName, Input.codingFormName);
		assignmentPage.add2ReviewerAndDistribute();
		baseClass.impersonateRMUtoReviewer();
		baseClass.stepInfo("Impersonated as reviewer");
		assignmentPage.SelectAssignmentByReviewer(assignmentName);
		reusableDocView.verifyCompletedIconNotDisplayed();
		baseClass.stepInfo("Successfully verifed the validations as review manager");
		loginPage.logout();
		// login as reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in as reviewer");
		assignmentPage.SelectAssignmentByReviewer(assignmentName);
		// validations
		reusableDocView.verifyCompletedIconNotDisplayed();
		baseClass.stepInfo("Successfully verifed the validations as reviewer");
		loginPage.logout();
		// delete assignment
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignmentPage.deleteAssgnmntUsingPagination(assignmentName);
		softAssertion.assertAll();
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 06/12/2021 Modified date: NA Modified by: Baskar
	 * @Description:Verify that user can save the coding stamp after coding form
	 *                     selection for a document in context of security group
	 */

	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 64)
	public void validateCodingForm(String fullName, String userName, String password)
			throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52042");
		baseClass.stepInfo("Verify that user can save the coding stamp after coding form selection "
				+ "for a document in context of security group");
		String fieldText = "Stamp" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();
		String rmu = "RMU";
		// Login As
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rev1userName + "'");

		if (fullName.contains(rmu)) {
			codingForm.assignCodingFormByCondition(Input.codingFormName);
		}

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		docViewPage.selectPureHit();
		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch(Input.testData1);
		sessionSearch.ViewInDocViews();

		// Edit the coding form and save with stamp
		reusableDocView.editCodingForm(comment);
		baseClass.waitForElement(docViewPage.getCodingFormStampButton());
		docViewPage.getCodingFormStampButton().waitAndClick(5);
		reusableDocView.popUpAction(fieldText, Input.stampSelection);
		baseClass.VerifySuccessMessage("Coding Stamp saved successfully");
		softAssertion.assertTrue(true, "Coding stamp saved successfully");
		driver.waitForPageToBeReady();

		// Doing house keeping activity
		driver.getWebDriver().navigate().refresh();
		reusableDocView.deleteStampColour(Input.stampSelection);

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 06/12/2021 Modified date: NA Modified by: Baskar
	 * @Description:Coding Form Child Window: Verify that for DA impersonating as
	 *                     RMU/Reviewer coding stamps option should be displayed
	 *                     outside the context of an assignment
	 */
	@Test(enabled = true, dataProvider = "daToRmuRev", groups = { "regression" }, priority = 65)
	public void validateDAImpersonateShouldDisplayCodingstamp(String roll, String userName, String password,
			String impersonate) throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52040");
		baseClass.stepInfo(
				"Coding Form Child Window: Verify that for DA impersonating as RMU/Reviewer coding stamps option "
						+ "should be displayed outside the context of an assignment");
		String rmu = "rmu";
		String rev = "rev";
		// Login As
		loginPage.loginToSightLine(userName, password);

		baseClass.daImpersonateAsRMUREV(roll, impersonate);

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		docViewPage.selectPureHit();

		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch(Input.testData1);

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		// verifying coding stamps
		docViewPage.verifyCodingStampDisplay();
		if (impersonate == rmu) {
			baseClass.passedStep("Coding stamp option displayed after DA impersonate as RMU");
		} else if (impersonate == rev) {
			baseClass.passedStep("Coding stamp option displayed after DA impersonate as REV");
		}

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 06/12/2021 Modified date: NA Modified by: Baskar
	 * @Description:Coding form child window: Verify that for RMU/Reviewer coding
	 *                     stamps option should be displayed outside the context of
	 *                     an assignment
	 */
	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 66)
	public void validateCodingstampForRmuRev(String fullName, String userName, String password)
			throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52038");
		baseClass.stepInfo("Coding form child window: Verify that for RMU/Reviewer coding stamps option should "
				+ "be displayed outside the context of an assignment");
		// Login As
		loginPage.loginToSightLine(userName, password);

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		docViewPage.selectPureHit();

		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch(Input.testData1);

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		// verifying coding stamps
		docViewPage.verifyCodingStampDisplay();

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 06/12/2021 Modified date: NA Modified by: Baskar
	 * @Description:Verify that for PA impersonating as RMU/Reviewer coding stamps
	 *                     option should be displayed outside the context of an
	 *                     assignment
	 */
	@Test(enabled = true, dataProvider = "paToRmuRev", groups = { "regression" }, priority = 67)
	public void verifyCodingStampOptionPaImpersonateRmuRev(String roll, String userName, String password,
			String impersonate) throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52037");
		baseClass.stepInfo("Verify that for PA impersonating as RMU/Reviewer coding stamps option should be displayed "
				+ " outside the context of an assignment");
		String rmu = "rmu";
		String rev = "rev";
		// Login As
		loginPage.loginToSightLine(userName, password);

		baseClass.paImpersonateAsRMUREV(roll, impersonate);

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		docViewPage.selectPureHit();

		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch(Input.testData1);

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		docViewPage.validateCodingstampInParent();
		if (impersonate == rmu) {
			baseClass.passedStep("Coding stamp option displayed after PA impersonate as RMU");
		} else if (impersonate == rev) {
			baseClass.passedStep("Coding stamp option displayed after PA impersonate as REV");
		}

		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 06/12/2021 Modified date: NA Modified by: Baskar
	 * @Description:Verify that for SA impersonating as RMU/Reviewer coding stamps
	 *                     option should be displayed outside the context of an
	 *                     assignment
	 */
	@Test(enabled = true, dataProvider = "saToRmuRev", groups = { "regression" }, priority = 68)
	public void verifyCodingStampOptionSaImpersonateRmuRev(String roll, String userName, String password,
			String impersonate) throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52035");
		baseClass.stepInfo("Verify that for SA impersonating as RMU/Reviewer coding stamps option should be displayed "
				+ " outside the context of an assignment");
		String rmu = "rmu";
		String rev = "rev";
		// Login As
		loginPage.loginToSightLine(userName, password);

		baseClass.SaImpersonateAsRMUREV(roll, impersonate);

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		docViewPage.selectPureHit();

		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch(Input.testData1);

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		docViewPage.validateCodingstampInParent();
		if (impersonate == rmu) {
			baseClass.passedStep("Coding stamp option displayed after SA impersonate as RMU");
		} else if (impersonate == rev) {
			baseClass.passedStep("Coding stamp option displayed after SA impersonate as REV");
		}

		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date:06/12/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify that after loading additional documents in mini doc
	 *              list child window 'Complete same as last' action on the
	 *              principal document should be working
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 69)
	public void validateChildWinodwCodeSameAsLast() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-51681");
		baseClass.stepInfo("Verify that after loading additional documents in mini doc list child window "
				+ "'Complete same as last' action on the principal document should be working");
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

		// validate checkmark icon and code same as last button
		docViewPage.checkMarkiconverify();

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date:06/12/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify that after loading additional documents in mini doc
	 *              list Complete action on the principal document should be working
	 *              on clicking of the coding stamp
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 70)
	public void validationForSavedCodingStamp() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-51680");
		baseClass.stepInfo("Verify that after loading additional documents in mini doc list Complete "
				+ "action on the principal document should be working on clicking of the coding stamp");
		String assignName = "Assignment" + Utility.dynamicNameAppender();
		String stampName = "StampText" + Utility.dynamicNameAppender();

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

		// validate checkmark icon and saved coding stamp
		docViewPage.checkMarkIconUsingSavedStamp(stampName);

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date:06/12/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify that after loading additional documents in mini doc
	 *              list 'Complete same as last' action on the principal document
	 *              should be working
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 71)
	public void validateCodeSameAsLast() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-51679");
		baseClass.stepInfo("Verify that after loading additional documents in mini doc list "
				+ "    'Complete same as last' action on the principal document should be working");
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

		// validate checkmark icon and code same as last button
		docViewPage.checkMarkIconverifyParentWindow();

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date:06/12/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify that after loading additional documents in mini doc
	 *              list child window and parent window Complete action on the
	 *              principal document should be working
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 72)
	public void validateCompleteBtnAfterScroll() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-51678");
		baseClass.stepInfo("Test case Id: RPMXCON-51677");
		baseClass.stepInfo("Verify that after loading additional documents in mini doc list child window "
				+ "and parent window Complete action on the principal document should be working");
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

		// validate checkmark icon and code same as last button
		docViewPage.validateCompleteBtnAndVerifyCheckMark();

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date:06/12/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify RMU after impersonating as Reviewer can see uncomplete
	 *              button in an assignment for the completed document in the mini
	 *              doc list
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 73)
	public void validateAfterImpersonateCheckMark() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-51050");
		baseClass.stepInfo("Verify RMU after impersonating as Reviewer can see uncomplete button "
				+ "in an assignment for the completed document in the mini doc list");
		String assignName = "Assignment" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		if (roll.equalsIgnoreCase("rmu")) {
//			 searching document for assignment creation
			sessionSearch.basicContentSearch(Input.searchString2);
			sessionSearch.bulkAssign();
			assignmentPage.assignmentCreation(assignName, Input.codingFormName);
			assignmentPage.toggleCodingStampEnabled();
			assignmentPage.add2ReviewerAndDistribute();
			docViewPage.selectAssignmentfromDashborad(assignName);
			driver.waitForPageToBeReady();
			System.out.println(assignName);
		}
		// complete document in random
		docViewPage.completeDocumentRandom(3, comment);
		// impersoante to rev
		baseClass.impersonateRMUtoReviewer();
		// selecting the assignment
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		// validate complete doc to display uncomplete button
		docViewPage.unCompleteButtonDisplay();
		driver.waitForPageToBeReady();
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date:06/12/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify when RMU impersonates as Reviewer and complete same as
	 *              last doc when preceding document is completed
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 74)
	public void validateAfterImpersonateSameAsLast() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-51608");
		baseClass.stepInfo("Verify when RMU impersonates as Reviewer and complete "
				+ "same as last doc when preceding document is completed");
		String assignName = "Assignment" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();
		String fieldText = "stamp" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		if (roll.equalsIgnoreCase("rmu")) {
//			searching document for assignment creation
			sessionSearch.basicContentSearch(Input.searchString2);
			sessionSearch.bulkAssign();
			assignmentPage.assignmentCreation(assignName, Input.codingFormName);
			assignmentPage.toggleCodingStampEnabled();
			assignmentPage.assignmentDistributingToReviewer();
			driver.waitForPageToBeReady();
			System.out.println(assignName);
		}
		loginPage.logout();
		// login as reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		// selecting the assignment
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// validate complete doc to display uncomplete button
		docViewPage.stampAndCompleteBtnValidation(comment, fieldText, Input.stampSelection);
		driver.waitForPageToBeReady();
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 08/12/2021 Modified date: NA Modified by: Baskar
	 * @Description:Verify tool tip on mouse over of the floppy icon to save the
	 *                     stamp in context of security group
	 */
	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 75)
	public void verifyToolTipStampBtn(String roll, String userName, String password)
			throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52062");
		baseClass.stepInfo(
				"Verify tool tip on mouse over of the floppy icon to save the stamp in context of security group");
		// Login As
		loginPage.loginToSightLine(userName, password);

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		docViewPage.selectPureHit();

		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch(Input.testData1);

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		docViewPage.validateToolTipTitle(floppyIconTooltip);

		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 08/12/2021 Modified date: NA Modified by: Baskar
	 * @Description:Verify all of the saved stamp icons should be displayed with
	 *                     tool tips when redirected in context of security group
	 */
	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 76)
	public void verifyToolTipAfterSavingStamp(String roll, String userName, String password)
			throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52061");
		baseClass.stepInfo("Verify all of the saved stamp icons should be "
				+ "displayed with tool tips when redirected in context of security group");
		String fieldText = "stamp" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();

		// Login As
		loginPage.loginToSightLine(userName, password);

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		docViewPage.selectPureHit();

		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch(Input.testData1);

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		docViewPage.validateToolTipTitleForSavedStamp(fieldText, comment);

		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 08/12/2021 Modified date: NA Modified by: Baskar
	 * @Description:Coding form child window: Verify user clicks code same as last
	 *                     on navigating to doc view from search
	 */
	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 77)
	public void validateCodeSameAsLastCW(String roll, String userName, String password)
			throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52127");
		baseClass.stepInfo("Coding form child window: Verify user clicks "
				+ "code same as last on navigating to doc view from search");

		// Login As
		loginPage.loginToSightLine(userName, password);

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		docViewPage.selectPureHit();

		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch(Input.testData1);

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		// child window validation
		docViewPage.childWinodwValidationForCodeSameAsLast();

		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 08/12/2021 Modified date: NA Modified by: Baskar
	 * @Description:Verify when user clicks 'code same as last' after saving the
	 *                     document
	 */
	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 78)
	public void validateCodeSameAsLastAfterSaving(String roll, String userName, String password)
			throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52125");
		baseClass.stepInfo("Verify when user clicks 'code same as last' after saving the document");
		String fieldText = "stamp" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();
		// Login As
		loginPage.loginToSightLine(userName, password);

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		docViewPage.selectPureHit();

		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch(Input.testData1);

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		// child window validation
		docViewPage.codeSameAsLastValid(fieldText, comment);

		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 08/12/2021 Modified date: NA Modified by: Baskar
	 * @Description:Coding form child window: Verify user with RMU/Reviewer role
	 *                     edits the coding form and clicks 'Save and Next' button
	 *                     in context of security group
	 */
	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 79)
	public void validateSaveAndNextCF(String roll, String userName, String password)
			throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52099");
		baseClass.stepInfo("Coding form child window: Verify user with RMU/Reviewer role edits the "
				+ "coding form and clicks 'Save and Next' button in context of security group");
		// Login As
		loginPage.loginToSightLine(userName, password);

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		docViewPage.selectPureHit();

		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch(Input.testData1);

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		// child window validation
		docViewPage.validateSaveAndNext();

		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 08/12/2021 Modified date: NA Modified by: Baskar
	 * @Description:Coding form Child window: Verify when clicking the 'Save and
	 *                     Next' button after clicking the saved stamp in context of
	 *                     security group
	 */
	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 80)
	public void validateSavedStampCF(String roll, String userName, String password)
			throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52098");
		baseClass.stepInfo("Coding form Child window: Verify when clicking the 'Save and Next'"
				+ " button after clicking the saved stamp in context of security group");
		String fieldText = "stamp" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();
		// Login As
		loginPage.loginToSightLine(userName, password);

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		docViewPage.selectPureHit();

		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch(Input.testData1);

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		// child window validation
		docViewPage.validateSavedStamp(fieldText, comment);
		// logout
		loginPage.logout();

	}

	/**
	 * @Author : sowndarya.velraj date:12/9/21
	 * @Description :Verify that after loading additional documents in mini doc list
	 *              user clicks the saved stamp
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 81)
	public void verifyLoadingAdditionalDocumentsInMinidoclist() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52120");
		String AssignStamp = "StampPopUp" + Utility.dynamicNameAppender();
		String fieldText = "fieldText" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// search to DocView
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		// create assignment
		assignmentPage.assignmentCreation(AssignStamp, Input.codingFormName);
		assignmentPage.toggleCodingStampEnabled();
		baseClass.stepInfo("Created a assignment " + AssignStamp);

		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");

		assignmentPage.viewSelectedAssgnUsingPagination(AssignStamp);
		baseClass.waitTillElemetToBeClickable(assignmentPage.getAssignmentActionDropdown());
		assignmentPage.getAssignmentActionDropdown().waitAndClick(10);
		assignmentPage.assignmentActions("DocView");
		baseClass.stepInfo("Viewing Documents from Doc view");
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();

		reusableDocView.editCodingFormAndSaveWithStampColour(fieldText, Input.stampSelection, comment);
		baseClass.stepInfo("Editing coding form and saving with a stamp colour has been done");

		reusableDocView.scrollingDocumentInMiniDocList();
		baseClass.stepInfo("Scrolling mini doclist and viewing loading text has been done");

		reusableDocView.lastAppliedStamp(Input.stampSelection);
		baseClass.stepInfo("Clicking previous selected stamp");

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rmu1userName + "'");
	}

	/**
	 * @Author : Baskar date:09/12/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify after impersonation user can see the check mark icon
	 *              for completed document in context of an assignment
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 82)
	public void afterImpersonateCheckMarkIconDisPlay() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-51048");
		baseClass.stepInfo("Verify after impersonation user can see the check mark icon "
				+ "for completed document in context of an assignment");
		String assignName = "Assignment" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		if (roll.equalsIgnoreCase("rmu")) {
//			searching document for assignment creation
			sessionSearch.basicContentSearch(Input.searchString2);
			sessionSearch.bulkAssign();
			assignmentPage.assignmentCreation(assignName, Input.codingFormName);
			assignmentPage.toggleCodingStampEnabled();
			assignmentPage.add2ReviewerAndDistribute();
			driver.waitForPageToBeReady();
			System.out.println(assignName);
		}
		baseClass.impersonateRMUtoReviewer();
		// selecting the assignment
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// validation of checkmark
		docViewPage.checkMarkVerification();

		driver.waitForPageToBeReady();
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date:09/12/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify user can apply coding stamp for the document once
	 *              marked as un-complete in an assignment
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 83)
	public void afterImpersonateCanSaveStamp() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-51049");
		baseClass.stepInfo(
				"Verify user can apply coding stamp for the " + "document once marked as un-complete in an assignment");
		String assignName = "Assignment" + Utility.dynamicNameAppender();
		String filedText = "stampName" + Utility.dynamicNameAppender();
		String comment = "commentValue" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		if (roll.equalsIgnoreCase("rmu")) {
//			searching document for assignment creation
			sessionSearch.basicContentSearch(Input.searchString2);
			sessionSearch.bulkAssign();
			assignmentPage.assignmentCreation(assignName, Input.codingFormName);
			assignmentPage.toggleCodingStampEnabled();
			assignmentPage.add2ReviewerAndDistribute();
			driver.waitForPageToBeReady();
			System.out.println(assignName);
		}
		baseClass.impersonateRMUtoReviewer();
		// selecting the assignment
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// validation of completing document
		docViewPage.validateSavedStampAfterImpersonate(filedText, comment);

		driver.waitForPageToBeReady();
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: NA Modified date:10/12/2021 Modified by: Baskar
	 * @Description :Verify validation of coding form when coding form is created
	 *              with metadata field as Integer on click of 'Save'
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 84)
	public void metaDataUsingIntegerUsingSave() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-51581");
		baseClass.stepInfo("Verify validation of coding form when coding form is "
				+ "created with metadata field as Integer on click of 'Save'");
		String formName = "CFMetaData" + Utility.dynamicNameAppender();
		String assgnCoding = "codingAssgn" + Utility.dynamicNameAppender();
		String intData = "INT";
		String Defaultaction = "Make It Required";
		String stamptext = "Stamp" + Utility.dynamicNameAppender();
		String alpha = "a@";
		UtilityLog.info("Started Execution for prerequisite");

		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");

		// Custom Field created with INT DataType
		projectPage.clickingManageButton();
		projectPage.addMetaDataFieldUsingIntergerType(tinyInt, intData, Input.docBasic, Input.tinyInt);
		projectPage.addMetaDataFieldUsingIntergerType(smallInt, intData, Input.docBasic, Input.smallInt);
		projectPage.addMetaDataFieldUsingIntergerType(avearageInt, intData, Input.docBasic, Input.averageInt);
		projectPage.addMetaDataFieldUsingIntergerType(bigInt, intData, Input.docBasic, Input.bigInt);
		projectPage.addMetaDataFieldUsingIntergerType(hugeInt, intData, Input.docBasic, Input.hugeInt);
		baseClass.stepInfo("Custom meta data field created with integer");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(tinyInt);
		securityGroupPage.addProjectFieldtoSG(smallInt);
		securityGroupPage.addProjectFieldtoSG(avearageInt);
		securityGroupPage.addProjectFieldtoSG(bigInt);
		securityGroupPage.addProjectFieldtoSG(hugeInt);
		baseClass.stepInfo("Custom metadata integer field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.creatingCodingFormusingMultipleInteger(formName, tinyInt, smallInt, avearageInt, bigInt, hugeInt,
				Defaultaction);
		baseClass.stepInfo("Coding form created with metadata fieldValue");

		// Session search to doc view to create assignment
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();

		assignmentPage.assignmentCreation(assgnCoding, formName);
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

		docViewPage.verifyingIntergerMetaDataUsingSave(alpha, stamptext, Input.stampSelection, tinyInt, smallInt,
				avearageInt, bigInt, hugeInt);

		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: NA Modified date:10/12/2021 Modified by: Baskar
	 * @Description :Verify validation of coding form when coding form is created
	 *              with metadata field as Integer on click of 'Complete'
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 85)
	public void metaDataUsingIntegerUsingComplete() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-51586");
		baseClass.stepInfo("Verify validation of coding form when coding form is "
				+ "created with metadata field as Integer on click of 'Complete'");
		String formName = "CFMetaData" + Utility.dynamicNameAppender();
		String assgnCoding = "codingAssgn" + Utility.dynamicNameAppender();
		String intData = "INT";
		String Defaultaction = "Make It Required";
		String stamptext = "Stamp" + Utility.dynamicNameAppender();
		String alpha = "a@";
		UtilityLog.info("Started Execution for prerequisite");

		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");

		// Custom Field created with INT DataType
		projectPage.clickingManageButton();
		projectPage.addMetaDataFieldUsingIntergerType(tinyInt, intData, Input.docBasic, Input.tinyInt);
		projectPage.addMetaDataFieldUsingIntergerType(smallInt, intData, Input.docBasic, Input.smallInt);
		projectPage.addMetaDataFieldUsingIntergerType(avearageInt, intData, Input.docBasic, Input.averageInt);
		projectPage.addMetaDataFieldUsingIntergerType(bigInt, intData, Input.docBasic, Input.bigInt);
		projectPage.addMetaDataFieldUsingIntergerType(hugeInt, intData, Input.docBasic, Input.hugeInt);
		baseClass.stepInfo("Custom meta data field created with integer");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(tinyInt);
		securityGroupPage.addProjectFieldtoSG(smallInt);
		securityGroupPage.addProjectFieldtoSG(avearageInt);
		securityGroupPage.addProjectFieldtoSG(bigInt);
		securityGroupPage.addProjectFieldtoSG(hugeInt);
		baseClass.stepInfo("Custom metadata integer field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.creatingCodingFormusingMultipleInteger(formName, tinyInt, smallInt, avearageInt, bigInt, hugeInt,
				Defaultaction);
		baseClass.stepInfo("Coding form created with metadata fieldValue");

		// Session search to doc view to create assignment
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();

		assignmentPage.assignmentCreation(assgnCoding, formName);
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

		docViewPage.verifyingIntergerMetaDataUsingComplete(alpha, stamptext, Input.stampSelection, tinyInt, smallInt,
				avearageInt, bigInt, hugeInt);

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date:10/12/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify that after loading additional documents in mini doc
	 *              list 'Save and Next' should be working
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 86)
	public void afterLoadingDisplayClickSaveAndNext() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52110");
		baseClass.stepInfo("Verify that after loading additional documents in mini doc list "
				+ "'Save and Next' should be working");
		String AssignStamp = "Loading" + Utility.dynamicNameAppender();

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(AssignStamp, Input.codingFormName);
		assignmentPage.toggleCodingStampEnabled();
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignmentPage.viewSelectedAssgnUsingPagination(AssignStamp);
		baseClass.waitTillElemetToBeClickable(assignmentPage.getAssignmentActionDropdown());
		assignmentPage.getAssignmentActionDropdown().waitAndClick(10);
		assignmentPage.assignmentActions("DocView");

		docViewPage.afterLoadingDisplayClickSaveAndnext();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager '" + Input.rmu1userName + "'");

	}

//////Changes done as per stabilization team below one//////
	////// For my Reference where//////@Start priority 88

	/**
	 * @Author : Baskar date:13/12/21 Modified date: NA Modified by: Baskar
	 * @Description :To verify that if document is complete is one Assignment, user
	 *              can modify same document in another assignment.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 87)
	public void validateCompletedDocsFromOtherUser() throws InterruptedException {
		softAssertion = new SoftAssert();
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		reusableDocView = new ReusableDocViewPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-50991");
		baseClass.stepInfo("To verify that if document is complete is one Assignment, "
				+ "user can modify same document in another assignment.");
		String AssignStamp = "Loading" + Utility.dynamicNameAppender();
		String AssignStampTwo = "Loading" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Creating assignment One
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(AssignStamp, Input.codingFormName);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.assignmentDistributingToReviewer();
		// Creating assignment Two
		driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sessionSearch.getBulkActionButton());
		sessionSearch.getBulkActionButton().waitAndClick(3);
		baseClass.waitForElement(sessionSearch.getBulkAssignAction());
		sessionSearch.getBulkAssignAction().waitAndClick(5);
		assignmentPage.assignmentCreation(AssignStampTwo, Input.codingFormName);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.assignmentDistributingToReviewer();

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager '" + Input.rmu1userName + "'");

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		assignmentPage.SelectAssignmentByReviewer(AssignStamp);
		// completing docs in user one
		String docId = docViewPage.getVerifyPrincipalDocument().getText();
		baseClass.stepInfo("Completed docs in userOne:" + docId + "");
		reusableDocView.editCodingForm(comment);
		docViewPage.getCompleteDocBtn().waitAndClick(5);
		// logout
		loginPage.logout();
		// validating docs in user two
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		assignmentPage.SelectAssignmentByReviewer(AssignStampTwo);
		docViewPage.getDociD(docId).waitAndClick(5);
		baseClass.stepInfo("verifying completed docs in userOne:" + docId + "");
		baseClass.stepInfo("Same Comment displayed");
		reusableDocView.verifyingComments(comment);
		reusableDocView.completeButton();
		docViewPage.getDociD(docId).waitAndClick(5);
		reusableDocView.uncompleteButtonValidate();
		softAssertion.assertAll();
	}

	/**
	 * @Author : Baskar date:14/12/21 Modified date: NA Modified by: Baskar
	 * @Description : To verify for saved document in an assignment, custom coding
	 *              form is editable on doc view
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 88)
	public void validateAfterSavingDocsThruAssgn() throws InterruptedException {
		softAssertion = new SoftAssert();
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		reusableDocView = new ReusableDocViewPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-50993");
		baseClass.stepInfo(
				"To verify for saved document in an assignment, " + "custom coding form is editable on doc view");
		String assign = "AAssgn" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();
		String commentOne = "comment" + Utility.dynamicNameAppender();

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Create New Assignment
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assign, Input.codingFormName);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.assignmentDistributingToReviewer();

		// logout
		loginPage.logout();

		// select assgn by reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		assignmentPage.SelectAssignmentByReviewer(assign);
		String docId = docViewPage.getVerifyPrincipalDocument().getText();
		baseClass.stepInfo("Completed docs in Assignment:" + docId + "");
		reusableDocView.editCodingForm(comment);
		reusableDocView.saveButton();

		// logout
		loginPage.logout();

		// again login as Rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Selecting the same assgn again and verifying
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		driver.waitForPageToBeReady();
		assignmentPage.viewSelectedAssgnUsingPagination(assign);
		assignmentPage.getSelectAssignment(assign).waitAndClick(5);
		driver.scrollPageToTop();
		baseClass.waitTillElemetToBeClickable(assignmentPage.getAssignmentActionDropdown());
		assignmentPage.getAssignmentActionDropdown().waitAndClick(5);
		assignmentPage.assignmentActions("DocView");
		driver.waitForPageToBeReady();
		docViewPage.getDociD(docId).waitAndClick(5);
		reusableDocView.verifyingComments(comment);
		baseClass.stepInfo("Changes doing in docview page through manage assignmnet");
		reusableDocView.editCodingForm(commentOne);
		reusableDocView.saveButton();
		reusableDocView.verifyingComments(commentOne);
		softAssertion.assertNotEquals(comment, commentOne);
		softAssertion.assertAll();

		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date:14/12/21 Modified date: NA Modified by: Baskar
	 * @Description : To verify user can save the coding stamp after coding form
	 *              selection for a document in an assignment
	 */

	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 89)
	public void ValidateUserCanSaveCodingForm(String fullName, String userName, String password)
			throws InterruptedException, AWTException {
		sessionSearch = new SessionSearch(driver);
		reusableDocView = new ReusableDocViewPage(driver);
		codingForm = new CodingForm(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-50994");
		baseClass.stepInfo("To verify user can save the coding stamp after coding form "
				+ "selection for a document in an assignment");
		String fieldText = "StampPopUp" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();
		String rmu = "RMU";
		// Login As
		loginPage.loginToSightLine(userName, password);

		if (fullName.contains(rmu)) {
			codingForm.assignCodingFormByCondition(Input.codingFormName);
		}

		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		reusableDocView.editCodingFormAndSaveWithStampColour(fieldText, Input.stampColour, comment);
		baseClass.VerifySuccessMessage("Coding Stamp saved successfully");

		driver.getWebDriver().navigate().refresh();
		driver.waitForPageToBeReady();
		reusableDocView.deleteStampColour(Input.stampColour);

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date:14/12/21 Modified date: NA Modified by: Baskar
	 * @Description : To verify user should not save the coding stamp without
	 *              selecting coding form for required fields
	 */

	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 90)
	public void ValidateStampWithoutEditCodingForm(String fullName, String userName, String password)
			throws InterruptedException, AWTException {
		sessionSearch = new SessionSearch(driver);
		reusableDocView = new ReusableDocViewPage(driver);
		codingForm = new CodingForm(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-50995");
		baseClass.stepInfo("To verify user should not save the coding stamp without "
				+ "selecting coding form for required fields");
		String fieldText = "StampPopUp" + Utility.dynamicNameAppender();
		String rmu = "RMU";
		// Login As
		loginPage.loginToSightLine(userName, password);

		if (fullName.contains(rmu)) {
			codingForm.assignCodingFormByCondition(Input.codingFormName);
		}

		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		reusableDocView.clearComments();
		reusableDocView.stampColourSelection(fieldText, Input.stampColour);

		baseClass.VerifyErrorMessage("Coding Form validation failed");
		baseClass.passedStep("Application not allowed user to save without editing coding form");

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date:14/12/21 Modified date: NA Modified by: Baskar
	 * @Description :To verify user can apply the saved coding stamp to other
	 *              documents in an assignment
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 91)
	public void validateSavedStamp() throws InterruptedException {
		softAssertion = new SoftAssert();
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-50996");
		baseClass.stepInfo("To verify user can apply the saved coding stamp to other documents in an assignment");
		String assign = "AAssgn" + Utility.dynamicNameAppender();
		String filedText = "stamp" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Create New Assignment
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assign, Input.codingFormName);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.assignmentDistributingToReviewerManager();

		// impersonate
		baseClass.impersonateRMUtoReviewer();
		// select assignment as reviewer
		assignmentPage.SelectAssignmentByReviewer(assign);
		// validate saved stamp
		docViewPage.vefiyingSavedStamp(filedText, comment);

		softAssertion.assertAll();
		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 14/12/2021 Modified date: NA Modified by: Baskar
	 * @Description:To verify colors available for the coding stamp from doc view
	 *                 and coding stamp pop up
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 92)
	public void verifyDefaultStampColourFromAssignment() throws InterruptedException, AWTException {
		softAssertion = new SoftAssert();
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		reusableDocView = new ReusableDocViewPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-50997");
		baseClass
				.stepInfo("To verify colors available for the coding stamp " + "from doc view and coding stamp pop up");
		String assign = "AAssgn" + Utility.dynamicNameAppender();
		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Create New Assignment
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assign, Input.codingFormName);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.assignmentDistributingToReviewer();

		// logout
		loginPage.logout();
		// login as reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		assignmentPage.SelectAssignmentByReviewer(assign);

		List<String> cfDefault = reusableDocView.getDefaultPopUpStampColour();
		List<String> popUpDefault = reusableDocView.getDefaultCodingFormColour();
		softAssertion.assertEquals(cfDefault, popUpDefault);
		baseClass.passedStep("Default stamp colour are same in popup window and coding form via assignment");

		softAssertion.assertAll();
		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date:15/12/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify validation messages should be displayed on coding stamp
	 *              pop up when all fields are blank when added as required field
	 */

	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 93)
	public void validateCodingStampPopUpSave(String fullName, String userName, String password)
			throws InterruptedException, AWTException {
		sessionSearch = new SessionSearch(driver);
		codingForm = new CodingForm(driver);
		docViewPage = new DocViewPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-50999");
		baseClass.stepInfo("To verify user should not save the coding stamp without "
				+ "selecting coding form for required fields");
		String comment = "comment" + Utility.dynamicNameAppender();
		String rmu = "RMU";
		// Login As
		loginPage.loginToSightLine(userName, password);

		if (fullName.contains(rmu)) {
			codingForm.assignCodingFormByCondition(Input.codingFormName);
		}

		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		docViewPage.editCodingForm(comment);
		docViewPage.codingStampButton();
		docViewPage.codingStampPopUpSaveButton();

		baseClass.VerifyErrorMessage("Please enter the details.");
		baseClass.passedStep("Error message displayed when required field are not entered");

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date:15/12/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify when user clicks 'Cancel' button coding stamp should
	 *              not be saved from coding stamp pop up when all fields are
	 *              entered
	 */

	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 94)
	public void validateCodingStampPopUpCancel(String fullName, String userName, String password)
			throws InterruptedException, AWTException {
		sessionSearch = new SessionSearch(driver);
		codingForm = new CodingForm(driver);
		docViewPage = new DocViewPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51000");
		baseClass.stepInfo("Verify when user clicks 'Cancel' button coding stamp "
				+ "should not be saved from coding stamp pop up when all fields are entered");
		String comment = "comment" + Utility.dynamicNameAppender();
		String fieldText = "stamp" + Utility.dynamicNameAppender();
		String rmu = "RMU";
		// Login As
		loginPage.loginToSightLine(userName, password);

		if (fullName.contains(rmu)) {
			codingForm.assignCodingFormByCondition(Input.codingFormName);
		}

		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		docViewPage.editCodingForm(comment);
		docViewPage.codingStampButton();
		docViewPage.codingStampcancelButtonValidate(fieldText, Input.stampSelection);

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 15/12/2021 Modified date: NA Modified by: Baskar
	 * @Description:To verify that user can uncomplete the document which marked as
	 *                 Completed.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 95)
	public void validateUncompleteBtnToClickCompleteBtn() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-51029");
		baseClass.stepInfo("To verify that user can uncomplete the document which marked as Completed.");
		String assign = "AAssgn" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();

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

		assignmentPage.SelectAssignmentByReviewer(assign);

		docViewPage.completeDocumentRandom(3, comment);
		docViewPage.unCompleteButtonDisplay();
		docViewPage.unCompleteButtonToCompleteBtn();

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date:15/12/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify for Project Admin 'Complete same as last doc' should
	 *              not be displayed
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 96)
	public void validateProjectAdminCodeSameAsLast() throws InterruptedException, AWTException {
		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51039");
		baseClass.stepInfo("Verify for Project Admin 'Complete same as last doc' should not be displayed");
		// Login As
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		if (docViewPage.getCodeSameAsLast().isElementAvailable(1)) {
			baseClass.failedStep("Code same as last button is displayed");
		} else {
			baseClass.passedStep("Code same as last button is not displayed");
		}

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 15/12/2021 Modified date: NA Modified by: Baskar
	 * @Description:Verify user can save the coding stamp with the stamp color which
	 *                     is deleted
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 97)
	public void validateFromAssgnCodingStampToDelete() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51293");
		baseClass.stepInfo("Verify user can save the coding stamp with the stamp color which is deleted");
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

		assignmentPage.SelectAssignmentByReviewer(assign);
		docViewPage.editCodingForm(comment);
		docViewPage.codingStampButton();
		docViewPage.popUpAction(fieldText, Input.stampSelection);
		docViewPage.deleteStampColour(Input.stampSelection);
		baseClass.VerifySuccessMessage("Coding stamp deleted successfully");
		baseClass.stepInfo("Assign same Coding stamp colour which is deleted");
		docViewPage.editCodingForm(comment);
		docViewPage.codingStampButton();
		docViewPage.popUpAction(fieldText, Input.stampSelection);
		baseClass.VerifySuccessMessage("Coding Stamp saved successfully");

		// logout
		loginPage.logout();

	}

	/**
	 * Author : Baskar date: 16/12/2021 Modified date: 16/12/2021 Modified by:
	 * Baskar Description:To verify that if coding form is assigned to the security
	 * group and user navigates to Doc View from Saved Search.
	 */

	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 98)
	public void validateCodingFormToDisplayFromSavedSearch(String fullName, String userName, String password)
			throws InterruptedException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		savedSearch = new SavedSearch(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-50929");
		baseClass.stepInfo("To verify that if coding form is assigned to the security group "
				+ "and user navigates to Doc View from Saved Search.");
		String savedSearchs = "AsavedToDocview" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		if (fullName.contains("rmu")) {
			codingForm.assignCodingFormByCondition(Input.codingFormName);
		}
		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(savedSearchs);
		savedSearch.savedSearchToDocView(savedSearchs);
		baseClass.stepInfo("User navigated to docviiew page from saved search page");

		// CodingStamp popup verfiy
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocView_CFName());
		softAssertion.assertEquals(Input.codingFormName, docViewPage.getDocView_CFName().getText());
		baseClass.stepInfo("Codingform displayed in docview panel when navigate from saved search");

		softAssertion.assertAll();

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager'" + Input.rmu1userName + "'");

	}

	/**
	 * Author : Baskar date: 16/12/2021 Modified date: 16/12/2021 Modified by:
	 * Baskar Description:To verify that if coding form is assigned to the security
	 * group and user navigates to Doc View from Doc List.
	 */

	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 99)
	public void validateCodingFormToDisplayFromDocList(String fullName, String userName, String password)
			throws InterruptedException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-50930");
		baseClass.stepInfo("To verify that if coding form is assigned to the security group "
				+ "and user navigates to Doc View from Doc List.");

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		if (fullName.contains("rmu")) {
			codingForm.assignCodingFormByCondition(Input.codingFormName);
		}
		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocList();
		docViewPage.docListCheckBoxAndViewInDocView();
		baseClass.stepInfo("User navigated to docview page from doclist page");

		// CodingStamp popup verfiy
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocView_CFName());
		softAssertion.assertEquals(Input.codingFormName, docViewPage.getDocView_CFName().getText());
		baseClass.stepInfo("Codingform displayed in docview panel when navigate from doclist page");

		softAssertion.assertAll();

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager'" + Input.rmu1userName + "'");

	}

	/**
	 * @Author : Aathith date:14/12/21 Modified date: NA Modified by: Aathith
	 *         RPMXCON-50961
	 * @Description : RMU can set the 'Allow reviewers to code docs outside
	 *              reviewer's batch (but within assignment) ' on in an assignment.
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 105)
	public void allowReviewerstoCodedocsOutsrideReviewerbatch() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-50961");

		baseClass.stepInfo(
				"RMU can set the 'Allow reviewers to code docs outside reviewer's batch (but within assignment) ' on in an assignment");
		String assignName = "Assignment" + Utility.dynamicNameAppender();
		System.out.println(assignName);
		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		baseClass.stepInfo("Search with text input for docs completed");

		// Creating Assignment from Basic search
		assignmentPage.assignmentCreation(assignName, Input.codeFormName);
		assignmentPage.allowReviewer();
		baseClass.stepInfo(
				"Doc is Assigned from basic Search and Assignment '" + assignName + "' is created Successfully");
		assignmentPage.add2ReviewerAndDistribute();
		driver.scrollPageToTop();
		baseClass.waitTillElemetToBeClickable(assignmentPage.getAssignmentSaveButton());
		assignmentPage.getAssignmentSaveButton().waitAndClick(10);

		baseClass.passedStep("allowed reviewers to code docs outside reviewer's batch (but within assignment)");
		loginPage.logout();
	}

	/**
	 * @Author : Aathith date:14/12/21 Modified date: NA Modified by: Aathith
	 *         RPMXCON-50964
	 * @Description : To verify after impersonation user can complete the document
	 *              outside reviewer batch on doc view when redirects from my
	 *              assignment.
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 106)
	public void disableAllowReviewerstoCodedocsOutsrideReviewerbatchRmuImporsonateRve()
			throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-50964");

		baseClass.stepInfo(
				"verify after impersonation user can complete the document outside reviewer batch on doc view when redirects from my assignment");
		String assignName = "Assignment" + Utility.dynamicNameAppender();
		System.out.println(assignName);
		// Login As Reviewer Manager

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		baseClass.stepInfo("Search with text input for docs completed");

		// Creating Assignment from Basic search
		assignmentPage.assignmentCreation(assignName, Input.codeFormName);
		assignmentPage.allowReviewer();
		baseClass.stepInfo(
				"Doc is Assigned from basic Search and Assignment '" + assignName + "' is created Successfully");
		assignmentPage.add2ReviewerAndDistribute();
		driver.scrollPageToTop();
		assignmentPage.getAssignmentSaveButton().waitAndClick(10);
		baseClass.impersonateRMUtoReviewer();

		// Select the Assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		// perform code same as Conceptual Documents
		docViewPage.performCodeSameForFamilyMembersDocuments();

		// Edit coding Form and complete Action
		docViewPage.editCodingFormComplete();
		baseClass.VerifySuccessMessage("Document completed successfully");
		baseClass.passedStep(
				"verified after impersonation user can complete the document outside reviewer batch on doc view when redirects from my assignment");
		loginPage.logout();
	}

	/**
	 * @Author : Aathith date:14/12/21 Modified date: NA Modified by: Aathith
	 *         RPMXCON-50963
	 * @Description : To Verify Reviewer can complete the document outside reviewer
	 *              batch from analytics panel by selecting 'Code same as this'
	 *              action when 'Allow coding outside reviewer batch' is on in an
	 *              assignment.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 107)

	public void enableAllowReviewerstoCodedocsOutsrideReviewerbatchINRmuRve()
			throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-50963");

		baseClass.stepInfo(
				"Verify Reviewer can complete the document outside reviewer batch from analytics panel by selecting 'Code same as this' action when 'Allow coding outside reviewer batch' is on in an assignment");
		String assignName = "Assignment" + Utility.dynamicNameAppender();
		System.out.println(assignName);
		// Login As Reviewer Manager

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssignFamilyMemberDocuments();
		baseClass.stepInfo("Search with text input for docs completed");

		// Creating Assignment from Basic search
		assignmentPage.assignmentCreation(assignName, Input.codeFormName);
		assignmentPage.allowReviewer();
		baseClass.stepInfo(
				"Doc is Assigned from basic Search and Assignment '" + assignName + "' is created Successfully");
		assignmentPage.add2ReviewerAndDistribute();
		driver.scrollPageToTop();
		assignmentPage.getAssignmentSaveButton().waitAndClick(10);
		baseClass.impersonateRMUtoReviewer();

		// Select the Assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		// perform code same as Conceptual Documents
		docViewPage.performCodeSameForFamilyMembersDocumentsWithScroll();

		// Edit coding Form and complete Action
		docViewPage.editCodingFormComplete();
		baseClass.VerifySuccessMessage("Document completed successfully");
		// baseClass.passedStep("allowed reviewers to code docs outside reviewer's batch
		// (but within assignment)");
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// Select the Assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		// perform code same as Conceptual Documents
		docViewPage.performCodeSameForFamilyMembersDocumentsReviewer();

		// Edit coding Form and complete Action
		docViewPage.editCodingFormComplete();
		baseClass.VerifySuccessMessage("Document completed successfully");
		baseClass.passedStep(
				"Verified Reviewer can complete the document outside reviewer batch from analytics panel by selecting 'Code same as this' action when 'Allow coding outside reviewer batch' is on in an assignment");
		loginPage.logout();
	}

	/**
	 * @Author : Aathith date:15/12/21 Modified date: NA Modified by: Aathith
	 *         RPMXCON-50965
	 * @Description : To verify for user document marked as completed in an
	 *              assignment, custom coding form is not editable on doc view page
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 108)
	public void verifyDocMarkCompletecodingFormNotEditable() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-50965");

		baseClass.stepInfo(
				"verify for user document marked as completed in an assignment, custom coding form is not editable on doc view page");
		String assignName = "Assignment" + Utility.dynamicNameAppender();
		System.out.println(assignName);
		// Login As Reviewer Manager

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		baseClass.stepInfo("Search with text input for docs completed");

		// Creating Assignment from Basic search
		assignmentPage.assignmentCreation(assignName, Input.codeFormName);

		baseClass.stepInfo(
				"Doc is Assigned from basic Search and Assignment '" + assignName + "' is created Successfully");
		assignmentPage.add2ReviewerAndDistribute();
		driver.scrollPageToTop();
		assignmentPage.getAssignmentSaveButton().waitAndClick(10);
		baseClass.impersonateRMUtoReviewer();

		// Select the Assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		// perform code same as Conceptual Documents

		// Edit coding Form and complete Action
		docViewPage.editCodingFormComplete();
		baseClass.VerifySuccessMessage("Document completed successfully");

		docViewPage.enableShowCompletedDoc();
		driver.waitForPageToBeReady();
		docViewPage.completedDocEditableCheck();

		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// Select the Assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		// Edit coding Form and complete Action
		docViewPage.editCodingFormComplete();
		baseClass.VerifySuccessMessage("Document completed successfully");
		docViewPage.enableShowCompletedDoc();
		docViewPage.completedDocEditableCheck();
		baseClass.passedStep(
				"verified for user document marked as completed in an assignment, custom coding form is not editable on doc view page");
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 17/12/2021 Modified date: NA Modified by: Baskar
	 * @Description:Verify confirmation message to overwrite should be displayed
	 *                     when user clicks 'Save' button without changing stamp
	 *                     color and name
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 109)
	public void validateOverWriteMsgForSavedStamp() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-51294");
		baseClass.stepInfo("Verify confirmation message to overwrite should be displayed "
				+ "when user clicks 'Save' button without changing stamp color and name");
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

		assignmentPage.SelectAssignmentByReviewer(assign);
		docViewPage.editCodingForm(comment);
		docViewPage.codingStampButton();
		docViewPage.popUpAction(fieldText, Input.stampSelection);
		docViewPage.pencilGearicon(Input.stampSelection);
		driver.waitForPageToBeReady();
		if (docViewPage.getCodingStampPopUpColurVerify(Input.stampSelection).isDisplayed()) {
			baseClass.passedStep("Coding stamp applied colour displayed in popup");
		} else {
			baseClass.failedStep("Coding stamp applied colour not displayed in popup");
		}
		docViewPage.codingStampPopUpSaveButton();
		String overWrite = docViewPage.getStampOverWriteMessage().getText().trim();
		System.out.println(overWrite);
		softAssertion.assertEquals(stampOverWrite, overWrite);
		docViewPage.getNavigationButton("Yes").waitAndClick(5);
		driver.waitForPageToBeReady();
		baseClass.VerifySuccessMessage("Coding stamp updated successfully");
		softAssertion.assertAll();
		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 17/12/2021 Modified date: NA Modified by: Baskar
	 * @Description:Verify that in DocView, Complete button and coding stamps should
	 *                     not disappear after Draw from Pool action
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 110)
	public void validateCompleteAndCodingStampBtnToDisplay() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-51637");
		baseClass.stepInfo("Verify that in DocView, Complete button and coding stamps "
				+ "should not disappear after Draw from Pool action");
		String assign = "AAssgn" + Utility.dynamicNameAppender();

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

		assignmentPage.SelectAssignmentByReviewer(assign);
		docViewPage.editingCodingFormWithCompleteButton();
		assignmentPage.SelectAssignmentByReviewer(assign);
		docViewPage.getClickDocviewID(2).waitAndClick(5);
		docViewPage.stampButtonShouldDisplay();
		docViewPage.completeBtnShouldDisplay();

		// logout
		loginPage.logout();
	}

	/**
	 * Author : Baskar date: 17/12/2021 Modified date: 16/12/2021 Modified by:
	 * Baskar Description:To verify that if coding form is assigned to the security
	 * group and user navigates to Doc View from Basic Search.
	 */

	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 111)
	public void validateCodingFormToDisplayFromBasicSearch(String fullName, String userName, String password)
			throws InterruptedException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-50928");
		baseClass.stepInfo("To verify that if coding form is assigned to the security group and "
				+ "user navigates to Doc View from Basic Search.");

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		if (fullName.contains("RMU")) {
			codingForm.assignCodingFormByCondition(Input.codingFormName);
		}
		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("User navigated to docview page ");

		// CodingStamp popup verfiy
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocView_CFName());
		softAssertion.assertEquals(Input.codingFormName, docViewPage.getDocView_CFName().getText());
		baseClass.stepInfo("Codingform displayed in docview panel when navigate from basic search page");

		softAssertion.assertAll();

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager'" + Input.rmu1userName + "'");

	}

	/**
	 * Author : Baskar date: 17/12/2021 Modified date: 16/12/2021 Modified by:
	 * Baskar Description:To verify that if RMU edits the coding form, then updated
	 * coding form should be displayed on Doc View.
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 112)
	public void validateUpdatedCodingForm() throws InterruptedException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-50931");
		baseClass.stepInfo("To verify that if RMU edits the coding form, "
				+ "then updated coding form should be displayed on Doc View.");

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		codingForm.editCodingForm(Input.codingFormName);
		codingForm.firstCheckBox("metadata");
		codingForm.addcodingFormAddButton();
		String text = codingForm.getCFMetaFirstText().getText();
		codingForm.codingFormSaveButton();
		baseClass.VerifySuccessMessage("Coding Form updated successfully");
		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("User navigated to docview page ");
		driver.waitForPageToBeReady();
		driver.scrollingToElementofAPage(docViewPage.getReadOnlyTextBox(text));
		boolean flag = docViewPage.getReadOnlyTextBox(text).isDisplayed();
		softAssertion.assertTrue(flag);
		softAssertion.assertAll();
		baseClass.passedStep("Coding form updated in docview after editing the manage coding form");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager'" + Input.rmu1userName + "'");

	}

	/**
	 * Author : Baskar date: 17/12/2021 Modified date: 16/12/2021 Modified by:
	 * Baskar Description:To verify that if user delete the coding form which is
	 * assigned to the assignment, it should displayed the message on Doc View.
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 113)

	public void validateCodingFormAfterDelete() throws InterruptedException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		codingForm = new CodingForm(driver);
		softAssertion = new SoftAssert();

		String cfName = "cf" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id: RPMXCON-50932");
		baseClass.stepInfo("To verify that if user delete the coding form which is assigned "
				+ "to the assignment, it should displayed the message on Doc View.");

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		codingForm.commentRequired(cfName);
		codingForm.assignCodingFormToSG(cfName);
		codingForm.deleteCodingForm(cfName, cfName);
		// search to and go to docview from rmu
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();
		boolean flag = docViewPage.getNoDefaultCodingForm().isDisplayed();
		softAssertion.assertTrue(flag);
		baseClass.passedStep("Coding form not displyed for RMu");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager'" + Input.rmu1userName + "'");

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer '" + Input.rev1userName + "'");
		// search to and go to docview from rmu
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();
		boolean flagOne = docViewPage.getNoDefaultCodingForm().isDisplayed();
		softAssertion.assertTrue(flagOne);
		baseClass.passedStep("Coding form not displyed for Rev");
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer'" + Input.rev1userName + "'");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		codingForm.assignCodingFormByCondition(Input.codingFormName);
		loginPage.logout();

		softAssertion.assertAll();
	}

	/**
	 * @Author : Baskar date: 17/12/2021 Modified date: NA Modified by: Baskar
	 * @Description:To verify that user cannot view the 'Coding Stamps' on Doc View,
	 *                 if preferences set as 'OFF' from the assignment.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 114)
	public void validateForAssgnCodingStampNotDisplay() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-50937");
		baseClass.stepInfo("To verify that user cannot view the 'Coding Stamps' on Doc View, "
				+ "if preferences set as 'OFF' from the assignment.");
		String assign = "AAssgn" + Utility.dynamicNameAppender();

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Create New Assignment
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assign, Input.codingFormName);
		baseClass.waitForElement(assignmentPage.getCodingStampToggleForAssgn());
		driver.scrollingToBottomofAPage();
		assignmentPage.getCodingStampToggleForAssgn().waitAndClick(5);
		driver.scrollPageToTop();
		assignmentPage.getAssignmentSaveButton().waitAndClick(5);
		baseClass.CloseSuccessMsgpopup();
		assignmentPage.add2ReviewerAndDistribute();
		baseClass.impersonateRMUtoReviewer();
		assignmentPage.SelectAssignmentByReviewer(assign);
		docViewPage.codingStampShouldNotDisplay();
		// logout
		loginPage.logout();
		// login as reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		assignmentPage.SelectAssignmentByReviewer(assign);
		docViewPage.codingStampShouldNotDisplay();
		driver.waitForPageToBeReady();

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 17/12/2021 Modified date: NA Modified by: Baskar
	 * @Description:Verify that when user clicks 'Save and Next' when audio file is
	 *                     in play mode
	 */
	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 115)
	public void validateAudioFileSaveAndNext(String roll, String userName, String password)
			throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-52109");
		baseClass.stepInfo("Verify that when user clicks 'Save and Next' when audio file is in play mode");
		// Login As
		loginPage.loginToSightLine(userName, password);

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		// child window validation
		docViewPage.validateSaveAndNextForAudioDocs();

		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 20/12/2021 Modified date: NA Modified by: Baskar
	 * @Description:Verify user can view the coding for the stamp on click of the
	 *                     'View Coding' button from edit coding stamp pop up
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 116)

	public void validateFromAssgnCodingStampViewCoding() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-51292");
		baseClass.stepInfo("Verify user can view the coding for the stamp on click of the "
				+ "'View Coding' button from edit coding stamp pop up");
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

		assignmentPage.SelectAssignmentByReviewer(assign);
		docViewPage.editCodingForm(comment);
		docViewPage.codingStampButton();
		docViewPage.popUpAction(fieldText, Input.stampSelection);
		docViewPage.pencilGearicon(Input.stampSelection);
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
		baseClass.stepInfo("verify viewcodingstamp saved page is successfully displayed ");
		softAssertion.assertAll();
		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 20/12/2021 Modified date: NA Modified by: Baskar
	 * @Description:To verify that if SysAdmin impersonate as RMU Or Reviewer,
	 *                 coding form should be displayed on the Doc View.
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 117)

	public void validateCodingFormShouldDisplayAfterImpFromSA() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		codingForm = new CodingForm(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-50938");
		baseClass.stepInfo("To verify that if SysAdmin impersonate as RMU Or Reviewer, "
				+ "coding form should be displayed on the Doc View.");

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		if (roll.contains("rmu")) {
			codingForm.assignCodingFormByCondition(Input.codingFormName);
		}

		// logout
		loginPage.logout();

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// impersonate as reviewer
		baseClass.impersonateSAtoReviewer();
		baseClass.stepInfo("Impersonating Sa to reviewer");

		// session search to docview panel
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.ViewInDocView();

		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocView_CFName());
		softAssertion.assertEquals(Input.codingFormName, docViewPage.getDocView_CFName().getText());
		baseClass.stepInfo("Codingform displayed in docview panel after impersonation");

		softAssertion.assertAll();
		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 20/12/2021 Modified date: NA Modified by: Baskar
	 * @Description:Verify that for DA impersonating as RMU/Reviewer coding stamps
	 *                     option should be displayed outside the context of an
	 *                     assignment
	 */

	@Test(enabled = true, dataProvider = "daToRmuRev", groups = { "regression" }, priority = 118)

	public void validateDAImpersonateShouldDisplayCodingstampPa(String roll, String userName, String password,
			String impersonate) throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-52036");
		baseClass.stepInfo("Verify that for DA impersonating as RMU/Reviewer coding stamps "
				+ "option should be displayed outside the context of an assignment");
		String rmu = "rmu";
		String rev = "rev";
		// Login As
		loginPage.loginToSightLine(userName, password);

		baseClass.daImpersonateAsRMUREV(roll, impersonate);

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		docViewPage.selectPureHit();

		baseClass.stepInfo("Searching Content documents based on search string");
		// sessionSearch.advancedLastContentSearch(Input.testData1);

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		// verifying coding stamps
		docViewPage.verifyCodingStampDisplayParent();
		if (impersonate == rmu) {
			baseClass.passedStep("Coding stamp option displayed after DA impersonate as RMU");
		} else if (impersonate == rev) {
			baseClass.passedStep("Coding stamp option displayed after DA impersonate as REV");
		}

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 20/12/2021 Modified date: NA Modified by: Baskar
	 * @Description:To verify that user can view the marked document along with the
	 *                 coding form in default view.
	 */

	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 119)

	public void validateCodingFormForCodeSameAsSaved(String fullName, String userName, String password)
			throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		codingForm = new CodingForm(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-50940");
		baseClass.stepInfo(
				"To verify that user can view the marked document along with the coding form in default view.");

		String comment = "comment" + Utility.dynamicNameAppender();
		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		if (fullName.contains("RMU")) {
			codingForm.assignCodingFormByCondition(Input.codingFormName);
		}

		// session search to docview panel
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.ViewInDocView();

		// docViewPage.verifyCodeSameForSavedDocs(comment);

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Mohan date:20/12/21 Modified date: NA Modified by: NA
	 * @Description : Reviewer of an assignment can not complete the document
	 *              outside his batch when 'Allow coding outside reviewer batch' is
	 *              off at assignment level 'RPMXCON-50962'
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 120)

	public void assignmentCannotCompleteDocsOutsideBatch() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-50962");
		baseClass.stepInfo(
				"Reviewer of an assignment can not complete the document outside his batch when 'Allow coding outside reviewer batch' is off at assignment level");
		String assName = "2022Loading" + Utility.dynamicNameAppender();
		int id = 23;
		int reviewerId = 1;

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
		baseClass.waitForElement(docViewPage.getDocView_DefaultViewTab());
		baseClass.waitForElement(docViewPage.getDocView_NumTextBox());
		docViewPage.getDocView_NumTextBox().Clear();
		docViewPage.getDocView_NumTextBox().SendKeys(Integer.toString(id));
		docViewPage.getDocView_NumTextBox().Enter();

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

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		baseClass.stepInfo(
				"Step 3: Select the assignment same as from pre-requisites and go to doc view page from my assignment");
		assignmentPage.SelectAssignmentByReviewer(assName);

		baseClass.stepInfo(
				"Step 4: Select document from analytics panel outside the reviewer batch but within the assignment and action as code same as this, complete the document");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocView_DefaultViewTab());
		baseClass.waitForElement(docViewPage.getDocView_NumTextBox());
		docViewPage.getDocView_NumTextBox().Clear();
		docViewPage.getDocView_NumTextBox().SendKeys(Integer.toString(reviewerId));
		docViewPage.getDocView_NumTextBox().Enter();

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
	 * @Author : Mohan date:20/12/21 Modified date: NA Modified by: NA
	 * @Description : Coding Form Child Window: Verify on saving of the stamp coding
	 *              form should not clear for the document in context of security
	 *              group 'RPMXCON-52067'
	 * @throws AWTException
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 121)

	public void verifyCodingSatmpSavedInChildWindow() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52067");
		baseClass.stepInfo(
				"Coding Form Child Window: Verify on saving of the stamp coding form should not clear for the document in context of security group");

		String stampName = "2022Loading" + Utility.dynamicNameAppender();
		String colour = "BLUE";
		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Session search to doc view Coding Form
		baseClass.stepInfo(
				"Step 2: Search for documents and go to doc view in context of security group  Search for audio documents and go to doc view in context of security group");
		sessionSearch.basicContentSearch(Input.searchString1);
		docViewPage.selectThreadMapPureHit();
		// sessionSearch.audioSearchAfterBasciContentSearchIsDone(Input.audioSearchString1,
		// Input.language);
		sessionSearch.ViewInDocViews();

		baseClass.stepInfo("Step 3: Click the gear icon to pop out the panels and pop out the coding form panel");
		driver.waitForPageToBeReady();
		docViewPage.popOutCodingFormPanel();

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		baseClass.stepInfo("Step 4: Edit the coding form and save the coding stamp");
		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}
		docViewPage.editCodingFormSave();

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		driver.Navigate().refresh();

		reusableDocView.stampColourSelection(stampName, colour);

		loginPage.logout();

	}

	/**
	 * @Author : Aathith date:21/12/21 Modified date: NA Modified by: Aathith
	 *         RPMXCON-50966
	 * @Description : To verify for user document not marked as completed in an
	 *              assignment, custom coding form is editable on doc view page.
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 122)

	public void verifyDocNotMarkCompletecodingFormEditable() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-50966");

		baseClass.stepInfo(
				"To verify for user document not marked as completed in an assignment, custom coding form is editable on doc view page");
		String assignName = "Assignment" + Utility.dynamicNameAppender();
		System.out.println(assignName);
		// Login As Reviewer Manager

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		baseClass.stepInfo("Search with text input for docs completed");

		// Creating Assignment from Basic search
		assignmentPage = new AssignmentsPage(driver);
		assignmentPage.assignmentCreation(assignName, Input.codeFormName);

		baseClass.stepInfo(
				"Doc is Assigned from basic Search and Assignment '" + assignName + "' is created Successfully");
		assignmentPage.add2ReviewerAndDistribute();
		driver.scrollPageToTop();
		assignmentPage.getAssignmentSaveButton().waitAndClick(10);
		baseClass.impersonateRMUtoReviewer();

		// Select the Assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		// perform code same as Conceptual Documents

		docViewPage = new DocViewPage(driver);
		// Editable check
		docViewPage.markInCompletedDocEditableCheck();

		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// Select the Assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		// Edit coding Form and complete Action
		docViewPage.markInCompletedDocEditableCheck();

		baseClass.passedStep(
				"verified for user document not marked as completed in an assignment, custom coding form is editable on doc view page");
		loginPage.logout();
	}

	/**
	 * @Author : Aathith date:21/12/21 Modified date: NA Modified by: Aathith
	 *         RPMXCON-50968
	 * @Description : Verify after impersonation document marked as completed in an
	 *              assignment, custom coding form is not editable on doc view page.
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 123)

	public void verifyMarkCompletedDocumentNotEditable() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-50968");

		baseClass.stepInfo(
				"Verify after impersonation document marked as completed in an assignment, custom coding form is not editable on doc view page");
		String assignName = "Assignment" + Utility.dynamicNameAppender();
		System.out.println(assignName);
		// Login As Reviewer Manager

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.bulkAssign();
		baseClass.stepInfo("Search with text input for docs completed");

		// Creating Assignment from Basic search
		assignmentPage = new AssignmentsPage(driver);
		assignmentPage.assignmentCreation(assignName, Input.codeFormName);

		baseClass.stepInfo(
				"Doc is Assigned from basic Search and Assignment '" + assignName + "' is created Successfully");
		assignmentPage.add2ReviewerAndDistribute();
		driver.scrollPageToTop();
		assignmentPage.getAssignmentSaveButton().waitAndClick(10);
		baseClass.impersonateRMUtoReviewer();

		// Select the Assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		// Edit coding Form and complete Action
		docViewPage = new DocViewPage(driver);
		docViewPage.editCodingFormComplete();
		baseClass.VerifySuccessMessage("Document completed successfully");

		docViewPage.get1stDocinMiniDocView().waitAndClick(5);
		driver.waitForPageToBeReady();
		docViewPage.completedDocEditableCheck();

		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// Select the Assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		// Edit coding Form and complete Action
		docViewPage.editCodingFormComplete();
		baseClass.VerifySuccessMessage("Document completed successfully");
		docViewPage.get1stDocinMiniDocView().waitAndClick(5);
		docViewPage.completedDocEditableCheck();
		baseClass.passedStep(
				"Verified after impersonation document marked as completed in an assignment, custom coding form is not editable on doc view page");
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 21/12/2021 Modified date: NA Modified by: Baskar
	 * @Description:To verify that if document is completed by RMU, then same
	 *                 document should not be modified on the doc view by Reviewer
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 124)
	public void validateCompleteDocsFromReviewer() throws InterruptedException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		codingForm = new CodingForm(driver);
		assignmentPage = new AssignmentsPage(driver);
		miniDocListPage = new MiniDocListPage(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-50992");
		baseClass.stepInfo("To verify that if document is completed by RMU, "
				+ "then same document should not be modified on the doc view by Reviewer");
		String assignmentName = "assignment" + Utility.dynamicNameAppender();

		// Login as a Rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		driver.waitForPageToBeReady();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		// create assignment
		assignmentPage.assignmentCreation(assignmentName, Input.codingFormName);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.add2ReviewerAndDistribute();
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignmentPage.viewSelectedAssgnUsingPagination(assignmentName);
		baseClass.waitTillElemetToBeClickable(assignmentPage.getAssignmentActionDropdown());
		assignmentPage.getAssignmentActionDropdown().waitAndClick(10);
		baseClass.waitForElement(assignmentPage.getAssignmentAction_CompleteDoc());
		assignmentPage.getAssignmentAction_CompleteDoc().waitAndClick(10);
		baseClass.getYesBtn().waitAndClick(10);
		baseClass.VerifySuccessMessage("All Documents successfully completed.");
		baseClass.stepInfo("Completing all document from assignment");
		// selecting assignment
		assignmentPage.SelectAssignmentByReviewer(assignmentName);
		baseClass.stepInfo("User on the docview page after selecting the  Assignment");
		driver.waitForPageToBeReady();
		miniDocListPage.configureMiniDocListToShowCompletedDocs();
		driver.waitForPageToBeReady();
		if (docViewPage.getCompleteDocBtn().isElementAvailable(1)) {
			baseClass.failedStep("Complete button displayed");
		} else {
			baseClass.passedStep("Complete button not displayed");
		}
		boolean flag = assignmentPage.unCompleteBtn().isDisplayed();
		softAssertion.assertTrue(flag);
		boolean flagOne = docViewPage.getDocument_CommentsTextBox().Enabled();
		softAssertion.assertTrue(flagOne);
		softAssertion.assertAll();
		baseClass.passedStep("Coding form is not editable");

		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 21/12/2021 Modified date: NA Modified by: Baskar
	 * @Description:Verify that when user clicks 'code same as last' when audio file
	 *                     is in play mode
	 */
	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 125)
	public void validateCodeSameAsLast(String fullName, String userName, String password)
			throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		String comment = "comment" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id: RPMXCON-52134");
		baseClass.stepInfo("Verify that when user clicks 'code same as last' when audio file is in play mode");
		// Login As
		loginPage.loginToSightLine(userName, password);

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		sessionSearch.ViewInDocViews();

		// docViewPage.audioDocsCodeSameAsLast(comment);

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Mohan date:21/12/21 Modified date: NA Modified by: NA
	 * @Description : Verify on saving of user stamp all control selections on
	 *              coding form should not be clear, in context of security group
	 *              'RPMXCON-52051'
	 * @throws AWTException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 126)
	public void verifyCodingStampSaved() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52051");
		baseClass.stepInfo(
				"Verify on saving of user stamp all control selections on coding form should not be clear, in context of security group");

		String colour = "BLUE";
		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Session search to doc view Coding Form
		baseClass.stepInfo(
				"Step 2: Search for documents and go to doc view in context of security group  Search for audio documents and go to doc view in context of security group");
		sessionSearch.basicContentSearch(Input.searchString1);
		docViewPage.selectNearDupePureHit();
		/// sessionSearch.audioSearchAfterBasciContentSearchIsDone(Input.audioSearch,
		/// Input.language);
		sessionSearch.ViewInDocViews();

		baseClass.stepInfo(
				"Step 3&4: Edit the coding form and click [floppy icon] to save the coding stamp and Enter name, select color for the stamp from the drop down and Save the coding stamp");
		docViewPage.docViewCodingFormPanelStampSelection(colour);

		loginPage.logout();

	}

	/**
	 * @Author : Mohan date:21/12/21 Modified date: NA Modified by: NA
	 * @Description : Verify that when coding stamp is created/saved using the
	 *              coding of a completed document viewed from analytics panel
	 *              outside of reviewers batch, then on mouse hover tool tip should
	 *              be displayed for the stamp icon. 'RPMXCON-51577'
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 127)
	public void assignmentCannotCompleteDocsOutsideBatchCodingStamp() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51577");
		baseClass.stepInfo(
				"Verify that when coding stamp is created/saved using the coding of a completed document viewed from analytics panel outside of reviewers batch, then on mouse hover tool tip should be displayed for the stamp icon.");
		String assName = "2022Loading" + Utility.dynamicNameAppender();
		int id = 23;
		String stampName = "Bee" + Utility.dynamicNameAppender();
		String colour = "BLUE";
		int reviewerId = 1;

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
		baseClass.waitForElement(docViewPage.getDocView_DefaultViewTab());
		baseClass.waitForElement(docViewPage.getDocView_NumTextBox());
		docViewPage.getDocView_NumTextBox().Clear();
		docViewPage.getDocView_NumTextBox().SendKeys(Integer.toString(id));
		docViewPage.getDocView_NumTextBox().Enter();

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

		baseClass.waitForElement(docViewPage.getDocView_DefaultViewTab());
		baseClass.waitForElement(docViewPage.getDocView_NumTextBox());
		docViewPage.getDocView_NumTextBox().Clear();
		docViewPage.getDocView_NumTextBox().SendKeys(Integer.toString(id));
		docViewPage.getDocView_NumTextBox().Enter();

		reusableDocView.stampColourSelection(stampName, colour);

		Actions act = new Actions(driver.getWebDriver());
		act.moveToElement(docViewPage.getDocView_CodingForm_BlueIcon().getWebElement());
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
		baseClass.waitForElement(docViewPage.getDocView_DefaultViewTab());
		baseClass.waitForElement(docViewPage.getDocView_NumTextBox());
		docViewPage.getDocView_NumTextBox().Clear();
		docViewPage.getDocView_NumTextBox().SendKeys(Integer.toString(reviewerId));
		docViewPage.getDocView_NumTextBox().Enter();

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

		baseClass.waitForElement(docViewPage.getDocView_DefaultViewTab());
		baseClass.waitForElement(docViewPage.getDocView_NumTextBox());
		docViewPage.getDocView_NumTextBox().Clear();
		docViewPage.getDocView_NumTextBox().SendKeys(Integer.toString(reviewerId));
		docViewPage.getDocView_NumTextBox().Enter();

		reusableDocView.stampColourSelection(stampName, colour);

		Actions act1 = new Actions(driver.getWebDriver());
		act1.moveToElement(docViewPage.getDocView_CodingForm_BlueIcon().getWebElement());
		String toolTipName1 = docViewPage.getDocView_CodingForm_BlueIcon().getWebElement().getAttribute("title");
		softAssertion.assertEquals(stampName, toolTipName1);
		softAssertion.assertAll();

		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Vijaya.Rani date:21/12/21 Modified date: NA Modified by: NA
	 * @Description : Verify when user clicks the 'Save and Next' on selecting the
	 *              document from history drop down. 'RPMXCON-52118' Sprint : 8
	 */

	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 128)
	public void verifyUserClickSaveAndNextSelectDropDown(String roll, String userName, String password)
			throws InterruptedException, AWTException {

		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		codingForm = new CodingForm(driver);
		String filedText = "Stamp" + Utility.dynamicNameAppender();
		String commands = "Stamp" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id: RPMXCON-52118");
		baseClass.stepInfo(
				"Verify when user clicks the 'Save and Next' on selecting the document from history drop down");

		// Login As
		loginPage.loginToSightLine(userName, password);

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		docViewPage.selectPureHit();

		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch(Input.searchStringStar);

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		driver.waitForPageToBeReady();
		docViewPage.editCodingFormAndSaveWithStamp(filedText, Input.stampColour);
		docViewPage.performCodeFormAndStampInDocView(Input.stampColour, Input.stampSelection, commands);

		loginPage.logout();

	}

	/**
	 * @Author : Vijaya.Rani date:21/12/21 Modified date: NA Modified by: NA
	 * @Description : Verify user clicks code same as last on navigating to doc view
	 *              from search. 'RPMXCON-52124' Sprint : 8
	 */

	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 129)
	public void verifyUserClickCodeSameAsLastInDocViewFromSearch(String roll, String userName, String password)
			throws InterruptedException, AWTException {

		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		codingForm = new CodingForm(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-52124");
		baseClass.stepInfo("Verify user clicks code same as last on navigating to doc view from search");

		// Login As
		loginPage.loginToSightLine(userName, password);

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		docViewPage.selectPureHit();

		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch(Input.searchStringStar);

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		docViewPage.performCodeSameAsLastInDocView();

		loginPage.logout();

	}

	/**
	 * @author Aathith.Senthilkumar RPMXCON-52121
	 * @DisCription Verify the automatically selected audio redaction tag when
	 *              shared annotation layer with shared redactation tags in security
	 *              groups and all documents are released to security groups.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 130)
	public void verifyAllDocumentAreReleaseToSG() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-52121");

		baseClass.stepInfo(
				"Verify after impersonation document marked as completed in an assignment, custom coding form is not editable on doc view page");

		// Login As
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		baseClass.impersonatePAtoReviewer();

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		docViewPage.selectPureHit();

		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch1(Input.testData1);

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();
		docViewPage.codeSameAsLastDisplayCheck();
		docViewPage.getDocView_NumTextBox().SendKeys("7");
		docViewPage.getDocView_NumTextBox().Enter();
		docViewPage.codeSameAsLastDisplayCheck();
		baseClass.passedStep(
				"Verified after impersonation document marked as completed in an assignment, custom coding form is not editable on doc view page");
		loginPage.logout();
	}

	/**
	 * @author Aathith.Senthilkumar RPMXCON-52122
	 * @DisCription Verify tool tip on mouse hover of the icon to code same as last.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 131)
	public void verifyToolTipCodeSameAsLast() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-52121");

		baseClass.stepInfo("Verify tool tip on mouse hover of the icon to code same as last");

		// Login As
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		docViewPage.selectPureHit();

		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch1(Input.testData1);

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();
		docViewPage.verifyCodeSameAsLastDocMsgIsDisplayed("Code this document the same as the last coded document");
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		reusableDocView.switchTochildWindow();
		docViewPage.verifyCodeSameAsLastDocMsgIsDisplayed("Code this document the same as the last coded document");

		docViewPage.closeWindow(1);
		docViewPage.switchToNewWindow(1);
		baseClass.passedStep("Verified tool tip on mouse hover of the icon to code same as last");
		loginPage.logout();
	}

	/**
	 * @author Aathith.Senthilkumar RPMXCON-52117
	 * @DisCription Verify when user clicks the saved stamp when navigating to
	 *              document on document navigation options [<<, <, >, >>, enter
	 *              document number].
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 132)
	public void verifiyNavigateToDocument() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52117");

		baseClass.stepInfo(
				"Verify when user clicks the saved stamp when navigating to document on document navigation options [<<, <, >, >>, enter document number]");
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		codingForm = new CodingForm(driver);
		String filedText = "Stamp" + Utility.dynamicNameAppender();
		// String commands = "Stamp" + Utility.dynamicNameAppender();

		// Login As rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		docViewPage.selectPureHit();
		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch1(Input.testData1);
		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();
		docViewPage.editCodingFormAndSaveWithStamp(filedText, Input.stampColour);
		docViewPage.verifyThatIsLastDoc();
		docViewPage.getDocView_NumTextBox().SendKeys("3");
		driver.waitForPageToBeReady();
		docViewPage.getDocView_NumTextBox().Enter();
		docViewPage.getStampBlueColour().waitAndClick(5);
		driver.waitForPageToBeReady();
		docViewPage.getSelectSaveLink().waitAndClick(5);
		baseClass.VerifySuccessMessage("Document saved successfully");
		docViewPage.verifyThatIsLastDoc();
		docViewPage.getDocView_NumTextBox().SendKeys("5");
		driver.waitForPageToBeReady();
		docViewPage.getDocView_NumTextBox().Enter();
		docViewPage.getStampBlueColour().waitAndClick(5);
		driver.waitForPageToBeReady();
		docViewPage.getSelectSaveLink().waitAndClick(5);
		driver.scrollPageToTop();
		docViewPage.deleteBlueStamp();

		baseClass.passedStep(
				"Verified when user clicks the saved stamp when navigating to document on document navigation options [<<, <, >, >>, enter document number]");
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 22/12/2021 Modified date: NA Modified by: Baskar
	 * @Description:Verify that on click of the saved stamp should load the coding
	 *                     form values in context of security group
	 */
	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 133)
	public void validateSavedStampSG(String fullName, String userName, String password)
			throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		String comment = "comment" + Utility.dynamicNameAppender();
		String fieldText = "Stamp" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id: RPMXCON-52068");
		baseClass.stepInfo(
				"Verify that on click of the saved stamp should load the coding form values in context of security group");

		// Login As
		loginPage.loginToSightLine(userName, password);

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		docViewPage.selectPureHit();

		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch(Input.testData1);

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		docViewPage.editCodingForm(comment);
		docViewPage.stampColourSelection(fieldText, Input.stampSelection);

		// Click on saved stamp
		docViewPage.lastAppliedStamp(Input.stampSelection);
		driver.waitForPageToBeReady();
		docViewPage.verifySavedStamp(comment);
		driver.getWebDriver().navigate().refresh();
		docViewPage.deleteStampColour(Input.stampSelection);

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 22/12/2021 Modified date: NA Modified by: Baskar
	 * @Description:Verify user can delete the stamp from edit coding stamp pop up
	 *                     in context of security group
	 */
	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 134)
	public void validateDeletionOfStamp(String fullName, String userName, String password)
			throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		String comment = "comment" + Utility.dynamicNameAppender();
		String fieldText = "Stamp" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id: RPMXCON-52066");
		baseClass.stepInfo(
				"Verify user can delete the stamp from edit coding stamp pop up in context of security group");

		// Login As
		loginPage.loginToSightLine(userName, password);

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		docViewPage.selectPureHit();

		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch(Input.testData1);

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		// Click on saved stamp to delete
		docViewPage.deleteSavedStamp(comment, fieldText);

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 22/12/2021 Modified date: NA Modified by: Baskar
	 * @Description : Verify that on completing the document after applying coding
	 *              stamp when hits panel is open then enable/disable should be
	 *              retained
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 135)
	public void verifyHitPanelUsingStamp() throws InterruptedException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		keywordPage = new KeywordPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-51749");
		baseClass.stepInfo("Verify that on clicking 'Save and Next' "
				+ "when hits panel is open then enable/disable should be " + "retained in context of security group");
		String hitTerms = "Than" + Utility.dynamicNameAppender();
		String Assignment = "AAsign" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();
		String stampText = "stamp" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

//		Add keywords
		this.driver.getWebDriver().get(Input.url + "Keywords/Keywords");
		keywordPage.AddKeyword(hitTerms, hitTerms);

		// Create New Assignment
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(Assignment, Input.codingFormName);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.assignmentDistributingToReviewer();

		// logou
		loginPage.logout();
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		// select assignment from reviewer and got to docview
		assignmentPage.SelectAssignmentByReviewer(Assignment);

		docViewPage.getPersistentHit(hitTerms);
		docViewPage.verifySavedStampForHitPanel(hitTerms, comment, stampText);

		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 22/12/2021 Modified date: NA Modified by: Baskar
	 * @Description :Verify that on completing the document same as last with code
	 *              same as action for other documents when hits panel is open then
	 *              enable/disable should be retained
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 136)
	public void verifyHitPanelCodeSameAsAndCodeLast() throws InterruptedException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		keywordPage = new KeywordPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-51748");
		baseClass.stepInfo("Verify that on completing the document same as last with code same as action "
				+ "for other documents when hits panel is open then enable/disable should be retained");
		String hitTerms = "Than" + Utility.dynamicNameAppender();
		String Assignment = "AAsign" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

//		Add keywords
		this.driver.getWebDriver().get(Input.url + "Keywords/Keywords");
		keywordPage.AddKeyword(hitTerms, hitTerms);

		// Create New Assignment
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(Assignment, Input.codingFormName);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.assignmentDistributingToReviewer();

		// logout
		loginPage.logout();
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		// select assignment from reviewer and got to docview
		assignmentPage.SelectAssignmentByReviewer(Assignment);

		docViewPage.getPersistentHit(hitTerms);
		docViewPage.verifyCodeSameAsLastForHitPanel(hitTerms, comment);

		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 22/12/2021 Modified date: NA Modified by: Baskar
	 * @Description :Verify that on completing the document when hits panel is open
	 *              then enable/disable should be retained
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 137)
	public void verifyHitPanelCompleteBtn() throws InterruptedException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		keywordPage = new KeywordPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-51747");
		baseClass.stepInfo("Verify that on completing the document when hits panel "
				+ "is open then enable/disable should be retained");
		String hitTerms = "Than" + Utility.dynamicNameAppender();
		String Assignment = "AAsign" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

//		Add keywords
		this.driver.getWebDriver().get(Input.url + "Keywords/Keywords");
		keywordPage.AddKeyword(hitTerms, hitTerms);

		// Create New Assignment
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(Assignment, Input.codingFormName);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.assignmentDistributingToReviewer();

		// logout
		loginPage.logout();
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		// select assignment from reviewer and got to docview
		assignmentPage.SelectAssignmentByReviewer(Assignment);

		docViewPage.getPersistentHit(hitTerms);
		docViewPage.verifyCompleteBtnForHitPanel(hitTerms, comment);

		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 23/12/2021 Modified date: NA Modified by: Baskar
	 * @Description:To verify that if user updates coding form in Doc View then it
	 *                 will displayed the modification for other documents-Coding
	 *                 form also.
	 * 
	 */
	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 138)
	public void validateSavedDocsAfterCodeSameAs(String fullName, String userName, String password)
			throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		String comment = "comment" + Utility.dynamicNameAppender();
		String commentTwo = "commentTwo" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id: RPMXCON-50944");
		baseClass.stepInfo(
				"To verify that if user updates coding form in Doc View then it will displayed the modification for other documents-Coding form also.\r\n"
						+ "");

		// Login As
		loginPage.loginToSightLine(userName, password);

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		docViewPage.selectPureHit();

		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch(Input.testData1);

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		// Save coding form
		docViewPage.editCodingForm(comment);
		docViewPage.codingFormSaveButton();
		for (int i = 1; i <= 2; i++) {
			baseClass.waitForElement(docViewPage.getDocView_MiniDoc_ChildWindow_Selectdoc(i));
			docViewPage.getDocView_MiniDoc_ChildWindow_Selectdoc(i).waitAndClick(5);
		}
		docViewPage.clickCodeSameAs();
		docViewPage.editCodingForm(commentTwo);
		docViewPage.codingFormSaveButton();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Verify the coding form for current document");
		docViewPage.verifyComments(commentTwo);
		docViewPage.getClickDocviewID(2).WaitUntilPresent().waitAndClick(5);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Verify the coding form for code same as document");
		docViewPage.verifyComments(commentTwo);

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 20/12/2021 Modified date: NA Modified by: Baskar
	 * @Description:In DocView, after loading/reloading mini doclist with additional
	 *                 docs, the Complete action on the principal document is should
	 *                 be working
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 139)
	public void validateFirstAndLastDocsShouldComplete() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-51676");
		baseClass.stepInfo("In DocView, after loading/reloading mini doclist with additional docs, "
				+ "the Complete action on the principal document is should be working");
		String assign = "AAssgn" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Create New Assignment
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assign, Input.codingFormName);
		baseClass.waitForElement(assignmentPage.getAssgnGrp_Create_DrawPoolCount());
		assignmentPage.getAssgnGrp_Create_DrawPoolCount().SendKeys("500");
		driver.scrollPageToTop();
		baseClass.waitForElement(assignmentPage.getAssignmentSaveButton());
		assignmentPage.getAssignmentSaveButton().waitAndClick(5);
		baseClass.CloseSuccessMsgpopup();
		assignmentPage.assignmentDistributingToReviewer();

		// logout
		loginPage.logout();
		// login as reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		// selecting assignment and going to docview page
		assignmentPage.SelectAssignmentByReviewer(assign);
		docViewPage.editCodingForm(comment);
		docViewPage.completeButton();
		baseClass.stepInfo("Principal document completed successfully");
		docViewPage.scrollingDocumentInMiniDocList();
		docViewPage.getDocIdLast().waitAndClick(5);
		String lastDoc = docViewPage.getVerifyPrincipalDocument().getText();
		docViewPage.editCodingForm(comment);
		docViewPage.completeButton();
		String lastSeDoc = docViewPage.getVerifyPrincipalDocument().getText();
		softAssertion.assertEquals(lastDoc, lastSeDoc);
		baseClass.stepInfo("Last document completed successfully");
		softAssertion.assertAll();

		// logout
		loginPage.logout();
	}

	/**
	 * Author : Baskar date: NA Modified date:24/8/2021 Modified by: Baskar
	 * Description : Verify comment and metadata indexing when clicked 'code same as
	 * last' for the document in security group context
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 140)
	public void validateCommentAndMetadataPureHitCount() throws InterruptedException, AWTException {
		projectPage = new ProjectPage(driver);
		securityGroupPage = new SecurityGroupsPage(driver);
		commentsPage = new CommentsPage(driver);
		docViewPage = new DocViewPage(driver);
		codingForm = new CodingForm(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-52138");
		baseClass.stepInfo("Verify comment and metadata indexing when clicked 'code same as last' "
				+ "for the document in security group context");
		UtilityLog.info("Started Execution for prerequisite");
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
		codingForm.assignCodingFormToSG(cfName);

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		// verify the coding form panel
		docViewPage.verifyCommentAndMetadata(addComment, commentText, projectFieldINT, metadataText);
		baseClass.stepInfo("Checking index of comment and metadata for saved document");
		sessionSearch.metadataAndCommentSearch(projectFieldINT, metadataText, addComment, commentText);

		codingForm.assignCodingFormByCondition(Input.codingFormName);
		codingForm.deleteCodingForm(cfName, cfName);

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager'" + Input.rmu1userName + "'");

	}

	/**
	 * @Author : Baskar date:27/12/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify when user clicks the saved stamp on selecting the
	 *              document from history drop down
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 141)
	public void savedStampInHistoryDropDown() throws InterruptedException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52116");
		baseClass.stepInfo("Verify when user clicks the saved stamp on selecting the document from history drop down");
		String comment = "comment" + Utility.dynamicNameAppender();
		String filedText = "Stamp" + Utility.dynamicNameAppender();

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		docViewPage.selectPureHit();

		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch1(Input.testData1);

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		// validation in history drop down for saved stamp
		docViewPage.savedStampHistoryDropDown(comment, filedText);

		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date:27/12/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify when user clicks the 'Saved and Next' when navigating
	 *              to document on document navigation options [<<, <, >, >>, enter
	 *              document number]
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 142)
	public void validateNavigateOptionUsingSaveAndNext() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52119");
		baseClass.stepInfo("Verify when user clicks the 'Saved and Next' when navigating to"
				+ " document on document navigation options [<<, <, >, >>, enter document number]");

		String comment = "comment" + Utility.dynamicNameAppender();

		// Login As rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		docViewPage.selectPureHit();

		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch1(Input.testData1);

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		docViewPage.verifySaveAndNextUsingNavigationOption(comment);

		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: NA Modified date:27/12/2021 Modified by: Baskar
	 * @Description :Verify validation of coding form when coding form is created
	 *              with metadata field as DateOnly on click of 'Complete'
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 143)
	public void nonDateFormatForDateUsingComplete() throws InterruptedException, AWTException {
		projectPage = new ProjectPage(driver);
		securityGroupPage = new SecurityGroupsPage(driver);
		codingForm = new CodingForm(driver);
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-51584");
		baseClass.stepInfo("Verify validation of coding form when coding form is "
				+ "created with metadata field as DateOnly on click of 'Complete'");
		String codingForms = "CFDate" + Utility.dynamicNameAppender();
		String assgnCoding = "codingAssgn" + Utility.dynamicNameAppender();

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
		codingForm.creatingCodingFormAndAssgnUsingParameter(codingForms, date, Input.optional);
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
		docViewPage.nonDateFormatValidationUsingComplete(date);

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: NA Modified date:27/12/2021 Modified by: Baskar
	 * @Description :Verify validation of coding form when coding form is created
	 *              with metadata field as DateOnly on saving of the stamp
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 144)
	public void validationOfNonDateFormatDateOnly() throws InterruptedException, AWTException {
		projectPage = new ProjectPage(driver);
		securityGroupPage = new SecurityGroupsPage(driver);
		codingForm = new CodingForm(driver);
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-51585");
		baseClass.stepInfo("Verify validation of coding form when coding form is "
				+ "created with metadata field as DateOnly on saving of the stamp");

		String codingfrom = "CFDate" + Utility.dynamicNameAppender();
		String assgnCoding = "codingAssgn" + Utility.dynamicNameAppender();
		String assgnColour = "ColourAssign" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();
		UtilityLog.info("Started Execution for prerequisite");

		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");

		// Custom Field created with DateTime DataType
		projectPage.addCustomFieldDataType(date, "Date");
		baseClass.stepInfo("Custom meta data field created with DateTime datatype");

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
		codingForm.creatingCodingFormAndAssgnUsingParameter(codingfrom, date, "Make it Optional");
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
		docViewPage.nonDateFormatValidationDateOnly(date, assgnColour, Input.stampSelection, comment);

		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: NA Modified date:27/12/2021 Modified by: Baskar
	 * @Description :Verify tool tip should be displayed on mouse over of the icon
	 *              to complete document same as last
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 145)
	public void validateTollTipFromAssgnCodeLast() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-51301");
		baseClass.stepInfo(
				"Verify tool tip should be displayed on mouse over of the icon to complete document same as last");
		String assgnCoding = "codingAssgn" + Utility.dynamicNameAppender();

		UtilityLog.info("Started Execution for prerequisite");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Session search to doc view to create assignment
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();

		assignmentPage.assignmentCreation(assgnCoding, Input.codingFormName);
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
		docViewPage.verifyCodeSameAsLastDocMsgIsDisplayed("Code this document the same as the last coded document");

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: NA Modified date:27/12/2021 Modified by: Baskar
	 * @Description :Verify on unassigned coding form/coding form objects should be
	 *              deleted
	 * 
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 146)
	public void validateUnAssignedCFAndCfObjectToRemove() throws InterruptedException, AWTException {
		codingForm = new CodingForm(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-51190");
		baseClass.stepInfo("Verify on unassigned coding form/coding form objects should be deleted");

		String codingfrom = "unAssign" + Utility.dynamicNameAppender();
		UtilityLog.info("Started Execution for prerequisite");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Creating Coding Form
		codingForm.verifyUnassignedCf(codingfrom);
		baseClass.stepInfo("Coding form deleted successfully after object removed");

		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: NA Modified date:27/12/2021 Modified by: Baskar
	 * @Description :Verify on saving of user stamp all control selections on coding
	 *              form should not be clear, this is in context of an assignment
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 147)
	public void validateStampControlShouldNotClaerFromAssgn() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-51032");
		baseClass.stepInfo("Verify on saving of user stamp all control selections on coding form "
				+ "should not be clear, this is in context of an assignment\r\n" + "");
		String assgnCoding = "codingAssgn" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();
		String fieldText = "stamp" + Utility.dynamicNameAppender();

		UtilityLog.info("Started Execution for prerequisite");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Session search to doc view to create assignment
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();

		// create assignment
		assignmentPage.assignmentCreation(assgnCoding, Input.codingFormName);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.add2ReviewerAndDistribute();

		baseClass.impersonateRMUtoReviewer();

		// selecting assignment
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);

		// contol selection verification
		docViewPage.controlSelectionShouldNotClear(comment, fieldText);

		// logout
		loginPage.logout();

		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Assignment Selection
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// contol selection verification
		docViewPage.controlSelectionShouldNotClear(comment, fieldText);

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 27/12/2021 Modified date: NA Modified by: Baskar
	 * @Description:Verify on click of the gear icon of the coding stamp saved stamp
	 *                     color should be clickable
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 148)
	public void validateSavedStampToDisplayFromAssign() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-51261");
		baseClass
				.stepInfo("Verify on click of the gear icon of the coding stamp saved stamp color should be clickable");
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

		assignmentPage.SelectAssignmentByReviewer(assign);
		docViewPage.editCodingForm(comment);
		docViewPage.codingStampButton();
		docViewPage.popUpAction(fieldText, Input.stampSelection);
		docViewPage.pencilGearicon(Input.stampSelection);
		driver.waitForPageToBeReady();
		String stampName = docViewPage.getCodingEditStampTextBox().GetAttribute("value");
		if (docViewPage.getCodingStampPopUpColurVerify(Input.stampSelection).isDisplayed()
				&& stampName.equals(fieldText)) {
			baseClass.passedStep("Coding stamp name displayed for applied colour");
			baseClass.passedStep("Coding stamp applied colour displayed in popup");
		} else {
			baseClass.failedStep("Coding stamp applied colour not displayed in popup");
		}
		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 27/12/2021 Modified date: NA Modified by: Baskar
	 * @Description:Verify confirmation message should be displayed when overwriting
	 *                     the stamp with already saved stamp
	 * 
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 149)
	public void validateOverWriteMsgForAlreadySavedStamp() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-51291");
		baseClass.stepInfo("Verify confirmation message should be displayed when overwriting the "
				+ "stamp with already saved stamp");
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

		assignmentPage.SelectAssignmentByReviewer(assign);
		docViewPage.editCodingForm(comment);
		docViewPage.codingStampButton();
		docViewPage.popUpAction(fieldText, Input.stampSelection);
		docViewPage.pencilGearicon(Input.stampSelection);
		driver.waitForPageToBeReady();
		if (docViewPage.getCodingStampPopUpColurVerify(Input.stampSelection).isDisplayed()) {
			baseClass.passedStep("Coding stamp applied colour displayed in popup");
		} else {
			baseClass.failedStep("Coding stamp applied colour not displayed in popup");
		}
		docViewPage.getDrp_CodingEditStampColour().waitAndClick(5);
		docViewPage.getEditAssignedColour(Input.stampSelection).waitAndClick(5);
		docViewPage.getCodingStampSaveBtn().waitAndClick(5);
		String overWrite = docViewPage.getStampOverWriteMessage().getText().trim();
		System.out.println(overWrite);
		softAssertion.assertEquals(stampOverWrite, overWrite);
		baseClass.passedStep("Overwrite message displayed for already saved stamp");
		docViewPage.getNavigationButton("Yes").waitAndClick(5);
		driver.waitForPageToBeReady();
		baseClass.VerifySuccessMessage("Coding stamp updated successfully");
		softAssertion.assertAll();
		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 28/12/2021 Modified date: NA Modified by: Baskar
	 * @Description:Verify Complete When Coding Stamp Applied toggle color when it
	 *                     on/off for assignment group
	 * 
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 150)
	public void validateToggleColour() throws InterruptedException, AWTException {
		assignmentPage = new AssignmentsPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-51283");
		baseClass
				.stepInfo("Verify Complete When Coding Stamp Applied toggle color when it on/off for assignment group");

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignmentPage.VerifyToggleColour();

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date:28/12/21 Modified date: NA Modified by: Baskar
	 * @Description : Coding form child window: Verify that code same as last should
	 *              be displayed in context of security group
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 151)
	public void codeSameAsLastShouldDisplay() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-52123");
		baseClass.stepInfo("Coding form child window: Verify that code same as last "
				+ "should be displayed in context of security group");

		// Login As rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		docViewPage.selectPureHit();

		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch1(Input.testData1);

		baseClass.stepInfo("User on the docview page");
		sessionSearch.ViewInDocViews();

		// verify code same as last displaying in child window
		baseClass.stepInfo("Coding form child window get opened");
		docViewPage.clickGearIconOpenCodingFormChildWindow();
		docViewPage.switchToNewWindow(2);
		boolean flag = docViewPage.getCodeSameAsLast().isDisplayed();
		softAssertion.assertTrue(flag);
		driver.close();
		docViewPage.switchToNewWindow(1);
		softAssertion = new SoftAssert();
		baseClass.passedStep("Code same as last button displayed in coding form child window");
		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 29/12/2021 Modified date: NA Modified by: Baskar
	 * @Description:Verify that scrolling should work for the comment box entered
	 *                     with large text and complete the same document
	 * 
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 152)
	public void validateScrollToDisplayForComment() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-51465");
		baseClass.stepInfo("Verify that scrolling should work for the comment "
				+ "box entered with large text and complete the same document");
		String assign = "AAssgn" + Utility.dynamicNameAppender();

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

		// selecting assignment from rev
		assignmentPage.SelectAssignmentByReviewer(assign);

		// Scroll verification
		docViewPage.editCodingFormScrollComplete();

		// logout
		loginPage.logout();
	}

	/**
	 * Author : Baskar date: NA Modified date:29/12/2021 Modified by: Baskar
	 * Description : Verify comment and metadata indexing when clicked 'Save and
	 * Next' for the document in security group context
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 153)
	public void validateCommentAndMetadataPureHitCountSaveAndNext() throws InterruptedException, AWTException {
		projectPage = new ProjectPage(driver);
		securityGroupPage = new SecurityGroupsPage(driver);
		commentsPage = new CommentsPage(driver);
		docViewPage = new DocViewPage(driver);
		codingForm = new CodingForm(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-52113");
		baseClass.stepInfo("Verify comment and metadata indexing when clicked 'Save and Next' "
				+ "for the document in security group context");
		UtilityLog.info("Started Execution for prerequisite");
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
		codingForm.assignCodingFormToSG(cfName);

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		// verify the coding form panel
		docViewPage.verifyCommentAndMetadataUsingSaveAndNext(addComment, commentText, projectFieldINT, metadataText);
		baseClass.stepInfo("Checking index of comment and metadata for saved document");
		sessionSearch.metadataAndCommentSearch(projectFieldINT, metadataText, addComment, commentText);

		codingForm.assignCodingFormByCondition(Input.codingFormName);
		codingForm.deleteCodingForm(cfName, cfName);

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager'" + Input.rmu1userName + "'");

	}

	/**
	 * @Author : Iyappan.Kasinathan
	 * @Description :To verify that user can change the label of the field in coding
	 *              form, it should displayed on the Doc View.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 154)
	public void verifyEditedCfLabelsInDocview() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-50984");
		baseClass.stepInfo(
				"To verify that user can change the label of the field in coding form, it should displayed on the Doc View");
		docViewPage = new DocViewPage(driver);
		codingForm = new CodingForm(driver);
		assignmentPage = new AssignmentsPage(driver);
		softAssertion = new SoftAssert();
		String codingform = "CFmetadata" + Utility.dynamicNameAppender();
		String assignmentName = "assignment" + Utility.dynamicNameAppender();
		System.out.println(assignmentName);
		// Login as a rmu user
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		// create codingform
		codingForm.createCodingFormUsingFirstObject(codingform, "tag");
		baseClass.waitForElement(codingForm.getCodingForm_CommentTab());
		codingForm.getCodingForm_CommentTab().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingForm_FirstComment());
		codingForm.getCodingForm_FirstComment().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingForm_EDITABLE_METADATA_Tab());
		codingForm.getCodingForm_EDITABLE_METADATA_Tab().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingForm_FirstMetadata());
		codingForm.getCodingForm_FirstMetadata().waitAndClick(10);
		codingForm.addcodingFormAddButton();
		codingForm.enterObjectName(0, Input.searchString1);
		codingForm.enterObjectName(1, Input.searchString2);
		codingForm.enterObjectName(2, Input.searchString4);
		codingForm.saveCodingForm();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		// assignmentPage.assignmentWithSaveButtonForRmu(assignmentName, codingform);
		assignmentPage.assignmentCreation(assignmentName, codingform);
		assignmentPage.toggleSaveButton();
		assignmentPage.assignmentDistributingToReviewer();
//		// logout Reviewer Manager
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager'" + Input.rmu1userName + "'");
		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rev1userName + "'");
		assignmentPage.SelectAssignmentByReviewer(assignmentName);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		docViewPage.verifyCodingFormTagNameInDocviewPg(0, Input.searchString1);
		docViewPage.verifyCodingFormTagNameInDocviewPg(1, Input.searchString2);
		docViewPage.verifyCodingFormTagNameInDocviewPg(2, Input.searchString4);
		baseClass.passedStep("Edited labels in coding form are displayed in docview page as expected");
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		codingForm.deleteCodingForm(codingform, codingform);
		assignmentPage.deleteAssgnmntUsingPagination(assignmentName);
		loginPage.logout();
	}

	/**
	 * @Author : Iyappan.Kasinathan
	 * @Description : To verify that user can view the helpful tooltip in doc view
	 *              coding form.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 155)
	public void verifyHelpToolTipInDocviewCf() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-50985");
		baseClass.stepInfo("To verify that user can view the helpful tooltip in doc view coding form.");
		docViewPage = new DocViewPage(driver);
		codingForm = new CodingForm(driver);
		assignmentPage = new AssignmentsPage(driver);
		softAssertion = new SoftAssert();
		String codingform = "CFmetadata" + Utility.dynamicNameAppender();
		String assignmentName = "assignment" + Utility.dynamicNameAppender();
		System.out.println(assignmentName);
		// Login as a rmu user
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		// create codingform
		codingForm.createCodingFormUsingFirstObject(codingform, "tag");
		codingForm.addcodingFormAddButton();
		codingForm.enterErrorAndHelpMsg(0, "No", "Help for testing", null);
		codingForm.saveCodingForm();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignmentName, codingform);
		assignmentPage.add2ReviewerAndDistribute();
		baseClass.impersonateRMUtoReviewer();
		assignmentPage.SelectAssignmentByReviewer(assignmentName);
		String actualResult1 = docViewPage.getCodingFormTaglabel(0).GetAttribute("title");
		softAssertion.assertEquals("Help for testing", actualResult1);
		baseClass.passedStep("The helptext tooltip successfully verified as review manager");
//		// logout Reviewer Manager
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager'" + Input.rmu1userName + "'");
		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rev1userName + "'");
		assignmentPage.SelectAssignmentByReviewer(assignmentName);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		// assignmentPage.assgnViewInAllDocView();
		String actualResult2 = docViewPage.getCodingFormTaglabel(0).GetAttribute("title");
		softAssertion.assertEquals("Help for testing", actualResult2);
		baseClass.passedStep("The helptext tooltip successfully verified as reviewer");
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		codingForm.deleteCodingForm(codingform, codingform);
		assignmentPage.deleteAssgnmntUsingPagination(assignmentName);
		loginPage.logout();
		softAssertion.assertAll();

	}

	/**
	 * @Author : Sakthivel date:30/12/2021 Modified date:NA
	 * @Description :Verify that when user in on Images tab and completes the
	 *              document from coding form child window then should be on Images
	 *              tab for next navigated document
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 156)
	public void verifyImageTabCompleteForNextNavigatedDoc() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		assignmentPage = new AssignmentsPage(driver);
		String assignmentName = "assignment" + Utility.dynamicNameAppender();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		baseClass.stepInfo("Test case Id: RPMXCON- 51920");
		baseClass.stepInfo("Verify that when user in on Images tab and completes the document from coding form "
				+ "child window then should be on Images tab for next navigated document");

		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.searchText);
		sessionSearch.bulkAssign();

		// Assignment creating and saving the assignment
		assignmentPage.assignmentCreation(assignmentName, "Default Project Coding Form");
		baseClass.stepInfo("Assignment is saved succcessfully");

		// Assignment saved and distributing to reviewer
		assignmentPage.assignmentDistributingToReviewer();
		baseClass.stepInfo("distrubuting to reviwer");

		// Logout as Reviewer manager
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager'" + Input.rmu1userName + "'");

		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		// Assignment Selection and Reviewer
		assignmentPage.SelectAssignmentByReviewer(assignmentName);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// Method for viewing doc view images.
		docViewPage.verifyDocViewImages();
		docViewPage.verifyCodingFormChildWindowCursorNavigatedToImageTabDisplayed();
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer'" + Input.rev1password + "'");
		softAssertion.assertAll();
	}

	/**
	 * @Author : Sakthivel date:30/12/2021 Modified date:NA
	 * @Description : Verify that when user in on Images tab and completes the
	 *              document from coding form child window after applying stamp then
	 *              should be on Images tab for next navigated document
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 157)
	public void verifyCfSavedStampChildNavigatedDoc() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		assignmentPage = new AssignmentsPage(driver);
		String assignmentName = "assignment" + Utility.dynamicNameAppender();
		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		baseClass.stepInfo("Test case Id: RPMXCON- 51921");
		baseClass.stepInfo(
				"Verify that when user in on Images tab and completes the document from coding form child window after applying stamp"
						+ " then should be on Images tab for next navigated document");

		// searching document for assignment creation
		sessionSearch.basicContentSearch(Input.searchText);
		sessionSearch.bulkAssign();

		// Assignment Saved
		assignmentPage.assignmentCreation(assignmentName, Input.codingFormName);
		baseClass.stepInfo("Assignment is saved succcessfully");

		// Assignment saved and distributing to reviewer
		assignmentPage.assignmentDistributingToReviewer();
		baseClass.stepInfo("distrubuting to reviwer");

		// Logout As Review Manager
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager'" + Input.rmu1userName + "'");

		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");

		assignmentPage.SelectAssignmentByReviewer(assignmentName);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// Method for viewing doc view images.
		docViewPage.verifyDocViewImages();
		docViewPage.verifyCfStampChildCursorNavigatedToDocViewImage(Input.stampColour, Input.stampColour);

		// Logout As Reviewer
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer'" + Input.rev1password + "'");
		softAssertion.assertAll();
	}

	/**
	 * @Author : Sakthivel date:30/12/2021 Modified date:NA
	 * @Description :Verify when user clicks 'Save and Next' when document not part
	 *              of mini doc list is viewed from analytics panel
	 */
	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 158)
	public void verifyDocNotPartInMiniDocListViewed(String fullName, String userName, String password)
			throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();

		// Login As RMU
		String rmu = "RMU";
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		baseClass.stepInfo("Test case Id: RPMXCON- 52111");
		baseClass.stepInfo("Verify when user clicks 'Save and Next' when document not part"
				+ "of mini doc list is viewed from analytics panel");
		baseClass.stepInfo("Searching Content documents based on search string");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		baseClass.stepInfo("Open the searched documents in doc view mini list");

		// To view the NearDupe Doc in the DocView
		sessionSearch.ViewNearDupeDocumentsInDocView();
		docViewPage.verifyNotPartofDocViewAnalyticalPanelNearDupeTab();

		// DocViewCodingform edit and saved
		docViewPage.editingCodingFormWithSaveAndNextButton();

		// Logout As Reviewer
		loginPage.logout();
		softAssertion.assertAll();
		baseClass.stepInfo("Successfully logout Reviewer Manager'" + Input.rmu1userName + "'");
	}

	/**
	 * @Author : Sakthivel date:30/12/2021 Modified date:NA
	 * @Description :Verify when user clicks the 'Save and Next' on selecting the
	 *              code same as action for document selected from analytics panel
	 *              in context of a security group
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 159)
	public void verifyCodeSameAsDocSelectedFromAnalyticsPanel() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		assignmentPage = new AssignmentsPage(driver);
		String assignmentName = "assignment" + Utility.dynamicNameAppender();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		baseClass.stepInfo("Test case Id: RPMXCON- 52108");
		baseClass.stepInfo("Verify when user clicks the 'Save and Next' on selecting the code same as"
				+ "action for document selected from analytics panel in context of a security group");

		// searching document for assignment creation
		sessionSearch.basicContentSearch(Input.searchText);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignmentName, Input.codingFormName);
		baseClass.stepInfo("Assignment is saved succcessfully");

		// Assignment saved and distributing to reviewer
		assignmentPage.assignmentDistributingToReviewer();
		baseClass.stepInfo("distrubuting to reviwer");

		// Logout as Reviewer Manager
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager'" + Input.rmu1userName + "'");

		// Login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		baseClass.stepInfo("Open the searched documents in doc view mini list");

		// To view the NearDupe Doc in the DocView
		sessionSearch.ViewNearDupeDocumentsInDocView();
		docViewPage.verifyCodeSameAsIconIsRemoved();

		// Logout As Reviewer
		loginPage.logout();
		softAssertion.assertAll();
		baseClass.stepInfo("Successfully logout Reviewer'" + Input.rev1password + "'");
	}

	/**
	 * @Author : Sakthivel date:30/12/2021 Modified date:NA
	 * @Description :Verify when user clicks saved stamp when document not part of
	 *              mini doc list is viewed from analytics panel
	 */
	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 160)
	public void verifySavedStampDocNotPartInDocList(String fullName, String userName, String password)
			throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		String filedText = "Stamp" + Utility.dynamicNameAppender();

		// Login As RMU
		String rmu = "RMU";
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		baseClass.stepInfo("Test case Id: RPMXCON- 52112");
		baseClass.stepInfo("Verify when user clicks saved stamp when document"
				+ "not part of mini doc list is viewed from analytics panel");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewNearDupeDocumentsInDocView();

		// DocViewCodingform edit and and stamp saved
		driver.waitForPageToBeReady();
		docViewPage.editCodingFormAndSaveWithStamp(filedText, Input.stampColour);
		baseClass.stepInfo("Editing coding form and saving with a stamp colour has been done");
		docViewPage.verifyNotPartofDocViewAnalyticalPanelNearDupeTab();

		// DocViewCodingform saved stamplastIcon click and saved doc
		docViewPage.getCodingFormStampClickAndSave(Input.stampColour);
		docViewPage.deleteStampColour(Input.stampColour);
		loginPage.logout();
		softAssertion.assertAll();
		baseClass.stepInfo("Successfully logout Reviewer Manager'" + Input.rmu1userName + "'");
	}

	/**
	 * @Author : Baskar date: 30/11/2021 Modified date: NA Modified by: Baskar
	 * @Description:Coding Form Child Window: Verify that for SA impersonating as
	 *                     RMU/Reviewer coding stamps option should be displayed
	 *                     outside the context of an assignment
	 */

	@Test(enabled = true, dataProvider = "saToRmuRev", groups = { "regression" }, priority = 161)
	public void verifyCodingStampOptionFromChildWindowSACF(String roll, String userName, String password,
			String impersonate) throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52039");
		baseClass.stepInfo("Coding Form Child Window: Verify that for SA impersonating as RMU/Reviewer coding stamps "
				+ "option should be displayed outside the context of an assignment");

		// Login As
		String rmu = "rmu";
		String rev = "rev";
		// Login As
		loginPage.loginToSightLine(userName, password);

		baseClass.SaImpersonateAsRMUREV(roll, impersonate);
		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		docViewPage.selectPureHit();

		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch(Input.testData1);

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		docViewPage.verifyCodingStampDisplay();

		if (impersonate == rmu) {
			baseClass.passedStep("Coding stamp option displayed after SA impersonate as RMU");
		} else if (impersonate == rev) {
			baseClass.passedStep("Coding stamp option displayed after SA impersonate as REV");
		}

		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date:30/12/21 Modified date: NA Modified by: Baskar
	 * @Description : To verify that if comment is required and user is complete the
	 *              document without adding comment, alert message should be
	 *              displayed.
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 162)
	public void validateCommentUsingSave() throws InterruptedException {
		docViewPage = new DocViewPage(driver);
		codingForm = new CodingForm(driver);
		sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-50982");
		baseClass.stepInfo("To verify that if comment is required and user is complete "
				+ "the document without adding comment, alert message should be displayed.");
		String cfName = "CF" + Utility.dynamicNameAppender();

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

		docViewPage.validateWithoutEditUsingSave();

		codingForm.assignCodingFormToSG("Default Project Coding Form");
		driver.waitForPageToBeReady();
		codingForm.deleteCodingForm(cfName, cfName);

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date:31/12/21 Modified date: NA Modified by: Baskar
	 * @Description : To verify that Project Admin cannot complete or save or Save
	 *              and Next the document, cannot save stamp
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 163)
	public void validateProjectAdminCodeSameA() throws InterruptedException, AWTException {
		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-50988");
		baseClass.stepInfo("To verify that Project Admin cannot complete or "
				+ "save or Save and Next the document, cannot save stamp");

		// Login As
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Session search to docview
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		// validation for pa
		if (docViewPage.getCodeSameAsLast().isDisplayed() && docViewPage.getSaveAndNextButton().isDisplayed()
				&& docViewPage.getSaveButton().isDisplayed() && docViewPage.getCodingFormStampButton().isDisplayed()) {
			baseClass.failedStep("Coding form default options are avilable");
		} else {
			baseClass.passedStep("Coding form default options are not avilable");
		}
		baseClass.waitForElement(docViewPage.getDocView_EditMode());
		docViewPage.getDocView_EditMode().waitAndClick(10);
		docViewPage.getHeader().Click();
		if (docViewPage.getDocView_HdrCoddingForm().Displayed()) {
			baseClass.failedStep("Coding from child window expand button is available");
		} else {
			baseClass.passedStep("Coding from child window expand button is not available");
		}

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date:31/12/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify checkmark should be displayed for document completed
	 *              same as last after code same as this action
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 164)
	public void validateCheckMarkIcon() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-51260");

		baseClass.stepInfo("Verify checkmark should be displayed for document completed same "
				+ "as last after code same as this action");
		String assignName = "Assignment" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();

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

		// validate checkmark icon and code same as last button
		docViewPage.validateCheckMarkCodeSameAs(comment);

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Iyappan.Kasinathan
	 * @Description :Verify on click of 'Save'/'Complete button coding form should
	 *              be validated as per the customized"+ " coding form using Tag
	 *              objects along with "Selected" condition
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 165)
	public void verifyCompleteButtonTagObjectsValidation() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-51211");
		baseClass.stepInfo(
				"Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form using Tag objects along with \"Selected\" condition");
		docViewPage = new DocViewPage(driver);
		codingForm = new CodingForm(driver);
		assignmentPage = new AssignmentsPage(driver);
		softAssertion = new SoftAssert();
		reusableDocView = new ReusableDocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		String codingform = "cf" + Utility.dynamicNameAppender();
		String assgnCoding = "codingAssgn" + Utility.dynamicNameAppender();
		String defaultAction = "Make It Display But Not Selectable";
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.createCodingFormUsingTwoObjects(codingform, null, null, null, "tag");
		codingForm.addcodingFormAddButton();
		codingForm.selectDefaultActions(0, defaultAction);
		codingForm.saveCodingForm();
		// create assignment and asign to coding form
		sessionSearch.basicContentSearch("null");
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assgnCoding, codingform);
		assignmentPage.add2ReviewerAndDistribute();
		// Impersonate as reviewer
		baseClass.impersonateRMUtoReviewer();
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		reusableDocView.completeButton();
		baseClass.VerifySuccessMessage("Document completed successfully");
		baseClass.passedStep("Review manager: Coding form validated successfully");
		// validations in docviewPage
		loginPage.logout();
		// LogIn as reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		reusableDocView.completeButton();
		baseClass.VerifySuccessMessage("Document completed successfully");
		baseClass.passedStep("Review manager: Coding form validated successfully");
		// validations in docviewPage
		loginPage.logout();
		// Delete assignment and codingform
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignmentPage.deleteAssgnmntUsingPagination(assgnCoding);
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.deleteCodingForm(codingform, codingform);
		codingForm.verifyCodingFormIsDeleted(codingform);
		loginPage.logout();
	}

	/**
	 * @Author : Iyappan.Kasinathan
	 * @Description :Verify on click of 'Save'/'Complete button coding form should
	 *              be validated as per the customized coding form using Tags and
	 *              Check group combined with Check Item
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 166)
	public void verifyCompleteButtonValidationUsingTagCheckGroup() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-51208");
		baseClass.stepInfo(
				"Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form"
						+ " using Tags and Check group combined with Check Item");
		docViewPage = new DocViewPage(driver);
		codingForm = new CodingForm(driver);
		assignmentPage = new AssignmentsPage(driver);
		softAssertion = new SoftAssert();
		reusableDocView = new ReusableDocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		tagsAndFoldersPage = new TagsAndFoldersPage(driver);
		String codingform = "CFMetadatas" + Utility.dynamicNameAppender();
		String assgnCoding = "codingAssgn" + Utility.dynamicNameAppender();
		String tagName = "tag" + Utility.dynamicNameAppender();
		String defaultAction = "Make It Hidden";
		String defaultAction1 = "Make It Required";
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		// create new coding form
		tagsAndFoldersPage.CreateTag(tagName, Input.securityGroup);
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.createCodingFormUsingFirstObject(codingform, "tag");
		codingForm.CreateCodingFormWithParameter(codingform, tagName, null, null, "tag");
		baseClass.stepInfo("Coding form created by using two tags");
		codingForm.addcodingFormAddButton();
		codingForm.getCF_CheckGrpObject().waitAndClick(10);
		codingForm.addcodingFormAddButton();
		codingForm.selectTagTypeByIndex("check item", 1, 0);
		codingForm.getCF_CheckGrpObject().waitAndClick(10);
		codingForm.addcodingFormAddButton();
		codingForm.selectTagTypeByIndex("check item", 2, 1);
		codingForm.selectDefaultActions(2, defaultAction);
		codingForm.selectDefaultActions(3, defaultAction1);
		codingForm.saveCodingForm();
		// create assignment and asign to coding form
		sessionSearch.basicContentSearch("null");
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assgnCoding, codingform);
		assignmentPage.add2ReviewerAndDistribute();
		// Impersonate as reviewer
		baseClass.impersonateRMUtoReviewer();
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		reusableDocView.completeButton();
		baseClass.VerifyErrorMessage("Coding Form validation failed");
		baseClass.passedStep("Review manager: Coding form validation is failed as expected");
		// validations in docviewPage
		loginPage.logout();
		// LogIn as reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		reusableDocView.completeButton();
		baseClass.VerifyErrorMessage("Coding Form validation failed");
		baseClass.passedStep("Reviewer: Coding form validation is failed as expected");
		// validations in docviewPage
		loginPage.logout();
		// Delete assignment and codingform
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignmentPage.deleteAssgnmntUsingPagination(assgnCoding);
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.deleteCodingForm(codingform, codingform);
		codingForm.verifyCodingFormIsDeleted(codingform);
		loginPage.logout();
	}

	/**
	 * @Author : Iyappan.Kasinathan
	 * @Description :Verify on click of 'Save'/'Complete button coding form should
	 *              be validated as per the customized coding form using Tags
	 *              objects along with Selected and "Not Selected" condition
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 167)
	public void verifyCompleteButtonValidationUsingTagRadiogrpLogics() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-51213");
		baseClass.stepInfo(
				"Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form using "
						+ "Tags objects along with Selected and \"Not Selected\" condition");
		docViewPage = new DocViewPage(driver);
		codingForm = new CodingForm(driver);
		assignmentPage = new AssignmentsPage(driver);
		softAssertion = new SoftAssert();
		reusableDocView = new ReusableDocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		tagsAndFoldersPage = new TagsAndFoldersPage(driver);
		String codingform = "CFMetadatas" + Utility.dynamicNameAppender();
		String assgnCoding = "codingAssgn" + Utility.dynamicNameAppender();
		String tagName = "tag" + Utility.dynamicNameAppender();
		String defaultAction = "Make It Hidden";
		String defaultAction1 = "Make It Required";
		String actionName = "Make this Required";
		String actionName1 = "Make this Optional";
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		// create new coding form
		tagsAndFoldersPage.CreateTag(tagName, Input.securityGroup);
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.createCodingFormUsingFirstObject(codingform, "tag");
		codingForm.CreateCodingFormWithParameter(codingform, tagName, null, null, "tag");
		codingForm.addcodingFormAddButton();
		baseClass.stepInfo("Coding form created by two tags");
		codingForm.getCF_RadioGrpObject().waitAndClick(10);
		codingForm.addcodingFormAddButton();
		codingForm.selectTagTypeByIndex("radio item", 1, 0);
		codingForm.getCF_RadioGrpObject().waitAndClick(10);
		codingForm.addcodingFormAddButton();
		codingForm.selectTagTypeByIndex("radio item", 2, 1);
		codingForm.selectDefaultActions(2, defaultAction);
		String expectedFirstObjectName = codingForm.getCFObjectsLabel(0);
		System.out.println(expectedFirstObjectName);
		codingForm.selectDefaultActions(3, defaultAction1);
		String expectedSecondObjectName = codingForm.getCFObjectsLabel(1);
		System.out.println(expectedSecondObjectName);
		codingForm.selectFieldLogicValues(2, expectedSecondObjectName, "Selected", actionName);
		codingForm.selectFieldLogicValues(3, expectedFirstObjectName, "Not Selected", actionName1);
		baseClass.stepInfo(defaultAction + " and " + defaultAction1 + " are selected respectively");
		codingForm.saveCodingForm();
		// create assignment and asign to coding form
		sessionSearch.basicContentSearch("null");
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assgnCoding, codingform);
		assignmentPage.add2ReviewerAndDistribute();
		// Impersonate as reviewer
		baseClass.impersonateRMUtoReviewer();
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		baseClass.waitForElement(codingForm.selectTagInPreviewBox(2));
		codingForm.selectTagInPreviewBox(2).waitAndClick(10);
		reusableDocView.completeButton();
		baseClass.VerifyErrorMessage("Coding Form validation failed");
		baseClass.passedStep("Review manager: Coding form validation is failed as expected");
		// validations in docviewPage
		loginPage.logout();
		// LogIn as reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		baseClass.waitForElement(codingForm.selectTagInPreviewBox(2));
		codingForm.selectTagInPreviewBox(2).waitAndClick(10);
		reusableDocView.completeButton();
		baseClass.VerifyErrorMessage("Coding Form validation failed");
		baseClass.passedStep("Reviewer: Coding form validation is failed as expected");
		// validations in docviewPage
		loginPage.logout();
		// Delete assignment and codingform
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignmentPage.deleteAssgnmntUsingPagination(assgnCoding);
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.deleteCodingForm(codingform, codingform);
		codingForm.verifyCodingFormIsDeleted(codingform);
		loginPage.logout();
	}

	/**
	 * @Author : Iyappan.Kasinathan
	 * @Description : Verify on click of 'Save'/'Complete button coding form should
	 *              be validated as per the customized coding form using Comments
	 *              objects along with "Not Selected" condition
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 168)
	public void verifyCompleteValidationUsingCommentsLogics() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-51212");
		baseClass.stepInfo(
				"Verify on click of 'Save'/'Complete button coding form should be validated as per the customized coding form"
						+ " using Comments objects along with \"Not Selected\" condition");
		docViewPage = new DocViewPage(driver);
		codingForm = new CodingForm(driver);
		assignmentPage = new AssignmentsPage(driver);
		softAssertion = new SoftAssert();
		reusableDocView = new ReusableDocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		tagsAndFoldersPage = new TagsAndFoldersPage(driver);
		String codingform = "CFMetadatas" + Utility.dynamicNameAppender();
		String assgnCoding = "codingAssgn" + Utility.dynamicNameAppender();
		String defaultAction = "Make It Optional";
		String defaultAction1 = "Make It Required";
		String actionName = "Make this Read Only";
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.createCodingFormUsingTwoObjects(codingform, null, null, null, "comment");
		baseClass.stepInfo("Coding form created by two comments");
		codingForm.addcodingFormAddButton();
		codingForm.selectDefaultActions(0, defaultAction);
		String expectedFirstObjectName = codingForm.getCFObjectsLabel(0);
		codingForm.selectDefaultActions(1, defaultAction1);
		codingForm.selectFieldLogicValues(1, expectedFirstObjectName, "Not Selected", actionName);
		baseClass.stepInfo(defaultAction + " and " + defaultAction1 + " are selected respectively");
		codingForm.saveCodingForm();
		// create assignment and asign to coding form
		sessionSearch.basicContentSearch("null");
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assgnCoding, codingform);
		assignmentPage.add2ReviewerAndDistribute();
		// Impersonate as reviewer
		baseClass.impersonateRMUtoReviewer();
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		baseClass.waitTillElemetToBeClickable(reusableDocView.getDocument_CommentsTextBox());
		reusableDocView.getDocument_CommentsTextBox().Clear();
		reusableDocView.getDocument_CommentsTextBox().SendKeys(Input.searchString1);
		reusableDocView.completeButton();
		baseClass.VerifyErrorMessage("Coding Form validation failed");
		baseClass.passedStep("Review manager: Coding form validation is failed as expected");
		// validations in docviewPage
		loginPage.logout();
		// LogIn as reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		assignmentPage.SelectAssignmentByReviewer(assgnCoding);
		baseClass.stepInfo("User on the doc view after selecting the assignment");
		baseClass.waitTillElemetToBeClickable(reusableDocView.getDocument_CommentsTextBox());
		reusableDocView.getDocument_CommentsTextBox().Clear();
		reusableDocView.getDocument_CommentsTextBox().SendKeys(Input.searchString1);
		reusableDocView.completeButton();
		baseClass.VerifyErrorMessage("Coding Form validation failed");
		baseClass.passedStep("Reviewer: Coding form validation is failed as expected");
		// validations in docviewPage
		loginPage.logout();
		// Delete assignment and codingform
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignmentPage.deleteAssgnmntUsingPagination(assgnCoding);
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.deleteCodingForm(codingform, codingform);
		codingForm.verifyCodingFormIsDeleted(codingform);
		loginPage.logout();
	}

	/**
	 * Author : Baskar date: NA Modified date:29/12/2021 Modified by: Baskar
	 * Description : Verify comment, metadata should be indexed for the document
	 * when user clicks 'Complete same as last doc' to the document in context of an
	 * assignment
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 169)
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
		baseClass.stepInfo("Checking index of comment and metadata for saved document");
		int pureHit = sessionSearch.metadataAndCommentSearch(projectFieldINT, metadataText, addComment, commentText);

		softAssertion.assertEquals(size, pureHit);
		softAssertion.assertAll();

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");
	}

	/**
	 * @Author : Baskar date:03/01/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify when stamp is saved from completed document then stamp
	 *              should be applied to the un complete document to complete those
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 170)
	public void validateSavedStampForCompleteDocs() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-51578");
		baseClass.stepInfo("Verify when stamp is saved from completed document then stamp "
				+ "should be applied to the un complete document to complete those");
		String assignName = "Assignment" + Utility.dynamicNameAppender();
		String stamp = "stamp" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// searching document for assignment creation
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssignNearDupeDocuments();
		assignmentPage.assignmentCreation(assignName, Input.codingFormName);
		assignmentPage.toggleCodingStampEnabled();
		assignmentPage.toggleDisableCodeOutsideReviewersBatch();
		assignmentPage.assignmentDistributingToReviewer();

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

		// Login As Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rev1userName + "'");

		// selecting the assignment
		assignmentPage.SelectAssignmentByReviewer(assignName);
		baseClass.stepInfo("User on the doc view after selecting the assignment");

		docViewPage.completedDocsSavingstap(stamp, comment, 1);

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date:04/01/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify checkmark should be displayed for document completed by
	 *              applying coding stamp after code same as this action
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 171)
	public void validateCheckMarkIconForCodingStamp() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-51259");
		baseClass.stepInfo("Verify checkmark should be displayed for document completed "
				+ "by applying coding stamp after code same as this action");
		String assignName = "Assignment" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();
		String stamp = "comment" + Utility.dynamicNameAppender();

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

		// validate checkmark icon and code same as last button
		docViewPage.validateCodeSameAsIconAndApplyStamp(stamp, comment);

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date:04/01/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify checkmark should be displayed for document completed
	 *              after code same as this action
	 * 
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 172)
	public void validateCheckMarkIconForComplete() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-51258");
		baseClass.stepInfo(
				"Verify checkmark should be displayed for document completed " + "after code same as this action");
		String assignName = "Assignment" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();

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

		// validate checkmark icon and code same as last button
		docViewPage.validateCodeSameAsIconAndEdit(comment);

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date:05/01/22Modified date: NA Modified by: Baskar
	 * @Description : To verify that once document is completed, coding stamp and
	 *              code same as last button should not be clickable.
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 173)
	public void validateCodeSameAsLastShouldDisable() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		softAssertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-51184");
		baseClass.stepInfo("To verify that once document is completed, coding stamp "
				+ "and code same as last button should not be clickable.");
		String assignName = "Assignment" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();

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

		// validate code same as last button should not clickable
		String prnDoc = docViewPage.getVerifyPrincipalDocument().getText();
		docViewPage.editCodingForm(comment);
		docViewPage.completeButton();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDociD(prnDoc));
		docViewPage.getDociD(prnDoc).waitAndClick(5);
		driver.waitForPageToBeReady();
		boolean flag = docViewPage.getCodeSamelastDisable().GetAttribute("class").contains("completed-overlay");
		softAssertion.assertTrue(flag);
		softAssertion.assertAll();
		baseClass.passedStep("For completed docs code same as Last button is disabled");
		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date:05/01/21 Modified date: NA Modified by: Baskar
	 * @Description : [TC reference RPMXCON-51215] Verify on click of 'Save and
	 *              Next' button coding form should be validated as per the
	 *              customized coding form using all objects along with all
	 *              condition and Radio Item in context of security group
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 174)
	public void validateUsingAllObjectRadioItem() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52093");
		baseClass.stepInfo("[TC reference RPMXCON-51215] Verify on click of 'Save and Next' "
				+ "button coding form should be validated as per the customized "
				+ "coding form using all objects along with all condition and Radio Item in context of security group");

		codingForm = new CodingForm(driver);
		sessionSearch = new SessionSearch(driver);
		tagsAndFoldersPage = new TagsAndFoldersPage(driver);
		docViewPage = new DocViewPage(driver);
		softAssertion = new SoftAssert();

		String cfName = "CF" + Utility.dynamicNameAppender();
		String tagName = "tag" + Utility.dynamicNameAppender();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu2userName + "'");

		// create tag
		tagsAndFoldersPage.CreateTag(tagName, "Default Security Group");

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

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		baseClass.stepInfo("user landed on the docview page");

		boolean falg = docViewPage.selectCodingFormCheckBoxes(tagName).Selected();
		softAssertion.assertFalse(falg);
		docViewPage.codingFormSaveAndNext();
		docViewPage.errorMessage();
		softAssertion.assertAll();
		baseClass.passedStep("Error message validation displayed for required Radio tag using Save and Next");

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Sakthivel date:03/01/2022 Modified date:NA
	 * @Description :Verify when user clicks saved stamp from coding form child
	 *              window when document not part of mini doc list is viewed from
	 *              analytics panel child window.
	 */
	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 175)
	public void verifyCfSavedStampDocNotPartInChildWindow(String fullName, String userName, String password)
			throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();

		// Login As RMU
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Test case Id: RPMXCON- 52154");
		baseClass.stepInfo("Verify when user clicks saved stamp from coding form child window when document"
				+ "not part of mini doc list is viewed from analytics panel child window");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString2);
		baseClass.stepInfo("Open the searched documents in doc view mini list");

		// To view the NearDupe Doc in the DocView
		sessionSearch.ViewNearDupeDocumentsInDocView();

		// Doc view coding form stamp selection
		driver.waitForPageToBeReady();
		driver.Navigate().refresh();
		docViewPage.docViewCodingFormPanelStampSelectionWithoutSave(Input.stampColour);

		// CodingForm child window will open
		docViewPage.clickGearIconOpenCodingFormChildWindow();
		docViewPage.verifyNotPartofDocViewAnalyticalPanelNearDupeTab();
		docViewPage.closeWindow(2);
		docViewPage.switchToNewWindow(2);

		// click StamplastIcon and SavedBtn in ChildWindow.
		docViewPage.editCfSavedStampBtnSavedChildWindow(Input.stampColour);
		docViewPage.closeWindow(1);
		docViewPage.switchToNewWindow(1);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		docViewPage.deleteStampColour(Input.stampColour);

		// Logout
		loginPage.logout();
		softAssertion.assertAll();
	}

	/**
	 * @Author : Sakthivel date:03/01/2022 Modified date:NA
	 * @Description :To verify that tag should be read only when the dependent tag
	 *              is selected/unselected.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 176)
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
	@Test(enabled = true, groups = { "regression" }, priority = 177)
	public void verifySaveBtnValidateCfCheckItem() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-51186");
		baseClass.stepInfo("Verify on click of 'Save' button coding form should be validated when coding form"
				+ "customized for all objects along with all condition and Check Item");
		String cfName = "CF" + Utility.dynamicNameAppender();
		String assignName = "CFAssignment" + Utility.dynamicNameAppender();
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		codingForm = new CodingForm(driver);
		docViewPage = new DocViewPage(driver);
		softAssertion = new SoftAssert();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu2userName + "'");

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
		baseClass.waitForElement(docViewPage.getAttachCountTextBox());
		String getVerifyAttachCount = docViewPage.getAttachCountTextBox().getText();
		softAssertion.assertEquals(getTagLabelCf, getVerifyAttachCount);
		System.out.println(getVerifyAttachCount);
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
		docViewPage.verifyCfAttachCountInDocView();
		docViewPage.verifyCodingFormName(cfName);
		docViewPage.validateRadioOrCheckGroupInDocviewPg("check-group");

		// validate Coding Form saved object is failed
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getCfCheckBox());
		docViewPage.getCfCheckBox().waitAndClick(10);
		docViewPage.getSaveDoc().waitAndClick(10);
		docViewPage.errorMessage();
		baseClass.CloseSuccessMsgpopup();

		// validate Coding Form saved object is passed
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getCfCheckBox());
		docViewPage.getCfCheckBox().waitAndClick(10);
		docViewPage.getSaveDoc().waitAndClick(10);
		baseClass.VerifySuccessMessage("Document saved successfully");
		softAssertion.assertAll();

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
		baseClass.waitForElement(docViewPage.getAttachCountTextBox());
		String getVerifyAttachCounts = docViewPage.getAttachCountTextBox().getText();
		softAssertion.assertEquals(getTagLabelCf, getVerifyAttachCounts);
		System.out.println(getVerifyAttachCount);
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
		docViewPage.verifyCfAttachCountInDocView();
		docViewPage.verifyCodingFormName(cfName);
		docViewPage.validateRadioOrCheckGroupInDocviewPg("check-group");

		// validate Coding Form saved object is failed
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getCfCheckBox());
		docViewPage.getCfCheckBox().waitAndClick(10);
		docViewPage.getSaveDoc().waitAndClick(10);
		docViewPage.errorMessage();
		baseClass.CloseSuccessMsgpopup();

		// validate Coding Form saved object is passed
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getCfCheckBox());
		docViewPage.getCfCheckBox().waitAndClick(10);
		docViewPage.getSaveDoc().waitAndClick(10);
		baseClass.VerifySuccessMessage("Document saved successfully");
		softAssertion.assertAll();

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

	@Test(enabled = true, dataProvider = "rmuRevLoginRole", groups = { "regression" }, priority = 178)
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

	/**
	 * @Author : Baskar date: 07/01/2022 Modified date: NA Modified by: Baskar
	 * @Description:Verify after impersonation on click of 'Save' button coding form
	 *                     should be validated outside of an assignment context
	 */

	@Test(enabled = true, dataProvider = "userDetailss", groups = { "regression" }, priority = 179)
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
	 * @Description:Verify after impersonation on click of 'Save' button coding form
	 *                     should be validated outside of an assignment context
	 */
	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 180)
	public void validateSaveAndNextActionInLastDocOfMiniDocList(String fullname, String username, String password)
			throws InterruptedException {
		docViewPage = new DocViewPage(driver);
		codingForm = new CodingForm(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		reusableDocView = new ReusableDocViewPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52115");
		baseClass.stepInfo("Verify when user clicks 'Save and Next' when vieweing the last document of mini doc list");
		// Login As
		loginPage.loginToSightLine(username, password);
		// Session search to doc view Coding Form
		// sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		docViewPage.selectPureHit();
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
	@Test(enabled = true, groups = { "regression" }, priority = 181)
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

	@Test(enabled = true, groups = { "regression" }, priority = 182)
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
		baseClass.stepInfo("Checking index of comment and metadata for saved document");
		int pureHitCount = sessionSearch.metadataAndCommentSearch(projectFieldINT, metadataText, addComment,
				commentText);

		codingForm.assignCodingFormByCondition(Input.codingFormName);
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

	@Test(enabled = true, groups = { "regression" }, priority = 183)
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
	@Test(enabled = true, groups = { "regression" }, priority = 184)
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
		docViewPage.verifyTagsAreDisabled(0);
		docViewPage.verifyTagsAreDisabled(1);
		baseClass.stepInfo("Tags are selected and disabled in parent window");
		// verify tag names
		docViewPage.verifyCodingFormTagNameInDocviewPg(0, expectedFirstObjectName);
		docViewPage.verifyCodingFormTagNameInDocviewPg(1, expectedSecondObjectName);
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		String parentId = reusableDocView.switchTochildWindow();
		docViewPage.verifyTagsAreDisabled(0);
		docViewPage.verifyTagsAreDisabled(1);
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
	@Test(enabled = true, groups = { "regression" }, priority = 185)
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
	 * @Description:Verify that code same as last should be displayed in context of
	 *                     security group
	 */

	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 186)
	public void verifyCodeSameAsLAstIconDisplayed(String fullname, String username, String password)
			throws InterruptedException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52115");
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
	 *               as last' after saving the document on applying the stamp
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 187)
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
		sessionSearch.ViewInDocView();
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
	 * @Description: Verify when SA/DA/PA user after impersonation clicks 'code same
	 *               as last' after saving the document
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 188)
	public void verifyCodeSameAsLastAfterSavingDoc() throws InterruptedException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52143");
		baseClass.stepInfo(
				"Verify when SA/DA/PA user after impersonation clicks 'code same as last' after saving the document");
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
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();
		reusableDocView.editingCodingFormWithSaveAndNextButton();
		reusableDocView.clickCodeSameAsLastAndVerifyNavigatedToNextDoc();
		baseClass.VerifySuccessMessage("Coded as per the coding form for the previous document");
		reusableDocView.clickCodeSameAsLastAndVerifyNavigatedToNextDoc();
		baseClass.VerifySuccessMessage("Coded as per the coding form for the previous document");
		reusableDocView.getDocView_MiniDocListIds(2).waitAndClick(10);
		String Actual = reusableDocView.getDocument_CommentsTextBox().WaitUntilPresent().GetAttribute("value");
		softAssertion.assertEquals("Edited and save with save button", Actual);
		softAssertion.assertAll();
		baseClass.passedStep("Coding form is displayed as expected");
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date:11/01/22 Modified date: NA Modified by: Baskar
	 * @Description : Verify on saving of the stamp coding form should not clear for
	 *              the document
	 */
	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 189)
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
	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 190)
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
	@Test(enabled = true, groups = { "regression" }, priority = 191)
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
	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 192)
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
	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 193)
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
		baseClass.VerifySuccessMessage("Coding stamp updated successfully");
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
	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 194)
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
	@Test(enabled = true, groups = { "regression" }, priority = 195)
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
	@Test(enabled = true, dataProvider = "rmuDauSauLogin", groups = { "regression" }, priority = 196)
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

	@Test(enabled = true, groups = { "regression" }, priority = 197)
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
		String actual = docViewPage.getDocumentsCommentViewCoding().GetAttribute("value");
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

	@Test(enabled = true, groups = { "regression" }, priority = 198)
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
	@Test(enabled = true, dataProvider = "impersoante", groups = { "regression" }, priority = 199)
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
	 * @TestCase_id : 51005 - To verify that comment should not be displayed on
	 *              document, if document is in two different security group and
	 *              comment is in one security group.
	 * @Description : To verify that comment should not be displayed on document, if
	 *              document is in two different security group and comment is in
	 *              one security group
	 */
	@Test(alwaysRun = false, groups = { "regression" }, priority = 200)
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
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		Reporter.log("Logged in as User: " + Input.pa2userName);
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

		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rmu2userName);
		docViewRedact.assignAccesstoSGs(namesg2, namesg3, Input.rev1userName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

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
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

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

	@Test(enabled = true, groups = { "regression" }, priority = 201)
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
	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 202)
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
	 * @Author : Steffy date: 28/01/2021 Modified date: NA Modified by:
	 * @Description:Verify assignment progress bar refreshesh after completing the
	 *                     document same as last prior documents should be completed
	 *                     by clicking complete button after selecting code same as
	 *                     this action.
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 203)
	public void verifyAssignmentProgressBarDocCompleteAfterCodeSameAs() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-51275");
		baseClass.stepInfo(
				"Verify assignment progress bar refreshesh after completing the document same as last prior documents should be completed by "
						+ "clicking complete button after selecting code same as this action");
		sessionSearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		docViewPage = new DocViewPage(driver);
		String searchString = Input.searchString1;
		String codingForm = Input.codeFormName;
		String docTextbox = "assignment click";
		String assname = "assgnment" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Basic Search and select the pure hit count
		baseClass.stepInfo("Step 1: Searching documents based on search string and Navigate to DocView");
		sessionSearch.basicContentSearch(searchString);
		sessionSearch.bulkAssign();

		// create Assignment and disturbute docs
		baseClass.stepInfo("Step 2: Create assignment and distribute the docs");
		assignmentsPage.assignDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, SessionSearch.pureHit);
		driver.waitForPageToBeReady();
		System.out.println(assname);
		loginPage.logout();

		// login as Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		assignmentsPage.SelectAssignmentByReviewer(assname);
		docViewPage.selectDocsFromMiniDocsAndCodeSameAs();
		baseClass.waitForElement(docViewPage.getDocument_CommentsTextBox());
		docViewPage.getDocument_CommentsTextBox().SendKeys(docTextbox);
		driver.scrollPageToTop();
		baseClass.waitForElement(docViewPage.getCompleteDocBtn());
		docViewPage.getCompleteDocBtn().waitAndClick(20);
		baseClass.VerifySuccessMessage("Document completed successfully");
		driver.waitForPageToBeReady();
		docViewRedact.getHomeDashBoard().waitAndClick(10);

		// verify assignment progress bar in completed docs
		baseClass.waitForElement(assignmentsPage.getBatchAssignmentBar(assname));
		if ((assignmentsPage.getBatchAssignmentBar(assname).isDisplayed())) {
			System.out.println("completed doc is refreshed in assignment bar");
			baseClass.passedStep("Assignment progress bar refreshed on completed doc");
			softAssertion.assertTrue(assignmentsPage.getBatchAssignmentBar(assname).isDisplayed());

		} else {
			System.out.println("not completed");
			baseClass.failedStep("Doc not completed");
		}

		assignmentsPage.SelectAssignmentByReviewer(assname);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewRedact.getDocView_MiniDoc_Selectdoc(3));
		docViewRedact.getDocView_MiniDoc_Selectdoc(3).waitAndClick(5);
		docViewPage.completeButton();
		baseClass.waitTime(1);
		docViewPage.clickCodeSameAsLast();
		docViewRedact.getHomeDashBoard().waitAndClick(10);

		// verify assignment progress bar in completed docs
		baseClass.waitForElement(assignmentsPage.getBatchAssignmentBar(assname));
		if ((assignmentsPage.getBatchAssignmentBar(assname).isDisplayed())) {
			System.out.println("completed doc is refreshed in assignment bar");
			baseClass.passedStep("Assignment progress bar refreshed on completed doc");
			softAssertion.assertTrue(assignmentsPage.getBatchAssignmentBar(assname).isDisplayed());

		} else {
			System.out.println("not completed");
			baseClass.failedStep("Doc not completed");
		}
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

	@Test(enabled = true, dataProvider = "rmuRevRmuLogin", groups = { "regression" }, priority = 203)
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

	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 204)
	public void editCodingNavigationCheck(String roll, String userName, String password) throws InterruptedException, AWTException {

		MiniDocListPage miniDocListpage = new MiniDocListPage(driver);

		List<String> docIDlist = new ArrayList<>();
		int sizeofList;
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
		baseClass.waitForElementCollection(miniDocListpage.getListofDocIDinCW());
		sizeofList = miniDocListpage.getListofDocIDinCW().size();
		docIDlist = baseClass.availableListofElements(miniDocListpage.getListofDocIDinCW());

		firnstDocname = miniDocListpage.docToCHoose(sizeofList, docIDlist);
		System.out.println("Current Document Viewed : " + firnstDocname);
		baseClass.stepInfo("Current Document Viewed : " + firnstDocname);

		// Edit comments
		docViewPage.getAddComment1().Clear();
		docViewPage.getAddComment1().SendKeys(comments);
		baseClass.stepInfo("Coding form input : " + comments);

		// Switch to a different document
		driver.waitForPageToBeReady();
		baseClass.waitForElementCollection(miniDocListpage.getListofDocIDinCW());
		baseClass.waitTime(3);
		miniDocListpage.docToCHoose(sizeofList, docIDlist, false);

		// Confirmation message "NO" flow
		baseClass.stepInfo("NO FLow : Navigation should not be done");
		docViewPage.getNavigationMsgPopupNoBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		docName = miniDocListpage.getMainWindowActiveDocID().getText();
		baseClass.stepInfo("Current Document : " + docName);
		baseClass.textCompareEquals(firnstDocname, docName, "Document not navigated", "Document navigated");
		comments = docViewPage.getAudioComment().GetAttribute("value");
		baseClass.stepInfo("Coding form input : " + comments);

		// Switch to a different document
		driver.waitForPageToBeReady();
		baseClass.waitForElementCollection(miniDocListpage.getListofDocIDinCW());
		baseClass.waitTime(3);
		miniDocListpage.docToCHoose(sizeofList, docIDlist, false);

		if (docViewPage.getNavigationMsg().isElementAvailable(5)) {
			baseClass.stepInfo(
					"Alert prompted to the user that their edits will not be saved and giving the user confirmation message with Yes/No buttons  ");
		}

		// Confirmation message "YES" flow
		baseClass.stepInfo(
				"YES FLow : Edits should not be saved and clicked document from the mini doc list should be loaded.   ");
		docViewPage.getNavigationMsgPopupYesBtnD().waitAndClick(5);
		driver.waitForPageToBeReady();
		secondDocname = miniDocListpage.getMainWindowActiveDocID().getText();
		System.out.println("Switched Document : " + secondDocname);
		baseClass.stepInfo("Switched Document : " + secondDocname);
		baseClass.textCompareNotEquals(secondDocname, docName, "Document switched", "Remains on the same document");

		// Back to Initial Document
		baseClass.stepInfo("Back to Initial document");
		driver.waitForPageToBeReady();
		baseClass.waitTime(3);
		miniDocListpage.getDociD(docName).waitAndClick(5);
		driver.waitForPageToBeReady();
		docName = miniDocListpage.getMainWindowActiveDocID().getText();
		System.out.println("Current Document Viewed : " + docName);
		baseClass.stepInfo("Current Document Viewed : " + docName);

		// Edits should not be saved
		String retainedInput = docViewPage.getAudioComment().GetAttribute("value");
		baseClass.stepInfo("Retained Input : " + retainedInput);
		baseClass.textCompareNotEquals(comments, retainedInput, "Edited input not saved", "Input saved");

		loginPage.logout();

	}
	
	/**
	 * @Author : Baskar date: 01/02/2022 Modified date: NA Modified by: Baskar
	 * @Description:To verify that Project Admin cannot view the coding form.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 205)
	public void validateCodingFormInPaUser() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		baseClass.stepInfo("Test case Id: RPMXCON-50927");
		baseClass.stepInfo("To verify that Project Admin cannot view the coding form.");

		// Login as Rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Assign coding form
		codingForm.assignCodingFormByCondition(Input.codingFormName);
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
	@Test(enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" }, priority = 206)
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
	 *              coding form is assigned to security group
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 207)
	public void validateCodingFormInPaUssser() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		codingForm = new CodingForm(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51457");
		baseClass.stepInfo("Performance: Verify that doc view should not hang when "
				+ "large coding form is assigned to security group");

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
		
		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 03/02/2022 Modified date: NA Modified by: Baskar
	 * @Description:Performance: Verify that doc view should not hang when large 
	 *              coding form is assigned to assignment
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 208)
	public void validatingLargeCfFromAssgn() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		codingForm = new CodingForm(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51456");
		baseClass.stepInfo("Performance: Verify that doc view should not hang "
				+ "when large coding form is assigned to assignment");

		String assignment = "AssCFLarge" + Utility.dynamicNameAppender();

		// login as rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// assign default project coding form
		codingForm.assignCodingFormToSG(Input.codingFormName);

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
	* @Author : Brundha 
	* @Description:To verify that message should be displayed if no coding form is
	* available for principal document and user select action as 'Code Same as this'.
	*/
	@Test(enabled = true, groups = { "regression" }, priority = 209)
	public void validateCodeSameWhenNoCf() throws InterruptedException, AWTException {
	docViewPage = new DocViewPage(driver);
	sessionSearch = new SessionSearch(driver);
	codingForm = new CodingForm(driver);
	softAssertion=new SoftAssert();
	baseClass.stepInfo("Test case Id: RPMXCON-50942");
	baseClass.stepInfo("To verify that message should be displayed if no coding form is"
	+ " available for principal document and user select action as 'Code Same as this'.");
	String cf = "cf" + Utility.dynamicNameAppender();
	
	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

	// Removing coding from for sg
	codingForm.commentRequired(cf);
	codingForm.assignCodingFormToSG(cf);
	codingForm.deleteCodingForm(cf,cf);

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

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
		}
		try {
			// loginPage.logout();
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			// LoginPage.clearBrowserCache();

		} catch (Exception e) {
			System.out.println("Sessions already closed");
		}
	}

}
