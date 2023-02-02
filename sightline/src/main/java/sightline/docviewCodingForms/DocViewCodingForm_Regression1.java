package sightline.docviewCodingForms;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;
import java.util.Set;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.CommentsPage;
import pageFactory.DocExplorerPage;
import pageFactory.DocViewPage;
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

public class DocViewCodingForm_Regression1 {
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
	String assignmentCreationStampOff = "StampOFF" + Utility.dynamicNameAppender();
	String assignmentCreationOn = "StampON" + Utility.dynamicNameAppender();
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
	String assgnCf = "Assignment" + Utility.dynamicNameAppender();
	String cfName = "CfName" + Utility.dynamicNameAppender();

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

	//@Test(description = "RPMXCON-52172", enabled = true, groups = { "regression" })
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
	//@Test(description = "RPMXCON-52173", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-52162", enabled = true, groups = { "regression" })
	public void clickCodeSameAsLastAndCodeingStampSaved() throws AWTException, InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52162");
		// Login as Reviewer Manager
		String comment1 = "comment" + Utility.dynamicNameAppender();
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
		docViewPage.docViewCodingFormPanelStampSelection(Input.stampColour, comment1);
		baseClass.stepInfo("Coding form loaded with value and selected with stamp selection");
		docViewPage.docViewMiniCodeSameAs(comment1);

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

	//@Test(description = "RPMXCON-52157", enabled = true, groups = { "regression" })
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
	//@Test(description = "RPMXCON-52158", enabled = true, groups = { "regression" })
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
	//@Test(description = "RPMXCON-52161", enabled = true, groups = { "regression" })
	public void codingFormToggleShouldOFFAtAssignmentLevel() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52161");
		String comment1 = "comment" + Utility.dynamicNameAppender();
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
		docViewPage.docViewCodingFormPanelStampSelection(Input.stampColours, comment1);
		baseClass.stepInfo("Coding form loaded with value and selected with stamp selection");
		docViewPage.docViewMiniCodeSameAs(comment1);

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer '" + Input.rev1userName + "'");

	}

	/**
	 * Author : Baskar date: NA Modified date: 24/8/2021 Modified by: Baskar
	 * Description:Floppy icon to save the stamp is not clickable after doing the
	 * code as same from docview RPMXCON-51635 Sprint 01
	 */

	//@Test(description = "RPMXCON-51635", enabled = true, groups = { "regression" })
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
	 * Author : Baskar date: NA Modified date: NA Modified by: Baskar
	 * Description:Coding Form Child window: Verify that user can save the coding
	 * stamp after coding form selection for a document in context of security group
	 * RPMXCON-52043 Sprint 02
	 */
	//@Test(description = "RPMXCON-52043", enabled = true, groups = { "regression" })
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
		sessionSearch.advancedNewContentSearch1(Input.searchStringStar);

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		// CodingForm ChildWindow Stamp selection
		docViewPage.stampSelectionCodingForm(Input.stampColour);
		docViewPage.deleteStampColour(Input.stampColour);

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

	//@Test(description = "RPMXCON-52225", enabled = true, groups = { "regression" })
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
	 * @Author : Baskar date:04/01/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify checkmark should be displayed for document completed
	 *              after code same as this action
	 * 
	 */

	//@Test(description = "RPMXCON-51258", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-51184", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-52093", enabled = true, groups = { "regression" })
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
	//@Test(description = "RPMXCON-52154", enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" })
	public void verifyCfSavedStampDocNotPartInChildWindow(String fullName, String userName, String password)
			throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		String comment = "comment" + Utility.dynamicNameAppender();
		String stamp = "stamp" + Utility.dynamicNameAppender();

		// Login As RMU
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Test case Id: RPMXCON- 52154");
		baseClass.stepInfo("Verify when user clicks saved stamp from coding form child window when document"
				+ "not part of mini doc list is viewed from analytics panel child window");

		if (fullName.contains("RMU")) {
			codingForm.assignCodingFormToSG(Input.codeFormName);
		}

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Open the searched documents in doc view mini list");

		// To view the NearDupe Doc in the DocView
		sessionSearch.ViewNearDupeDocumentsInDocView();

		// Doc view coding form stamp selection
		driver.waitForPageToBeReady();
		docViewPage.editCodingForm(comment);
		docViewPage.codingStampButton();
		docViewPage.popUpAction(stamp, Input.stampColour);

		// CodingForm child window will open
		docViewPage.clickGearIconOpenCodingFormChildWindow();
		docViewPage.verifyNotPartofDocViewAnalyticalPanelNearDupeTab();
		docViewPage.closeWindow(2);
		docViewPage.switchToNewWindow(2);

		// click StamplastIcon and SavedBtn in ChildWindow.
		docViewPage.editCfSavedStampBtnSavedChildWindow(Input.stampColour, comment);
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
	 * @Author : Baskar date:14/9/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify when user clicks the 'Code same as last' on selecting
	 *              the document from history drop down
	 */
	//@Test(description = "RPMXCON-52140", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-52139", enabled = true, groups = { "regression" })
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
	 * @Author : Baskar date:04/01/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify checkmark should be displayed for document completed by
	 *              applying coding stamp after code same as this action
	 */

	//@Test(description = "RPMXCON-51259", enabled = true, groups = { "regression" })
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
	 * @Author : Baskar date:15/9/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify that after loading additional documents in mini doc
	 *              list 'Code same as last' should be working
	 */
	//@Test(description = "RPMXCON-52135", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-52130", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-52129", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-52100", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-52105", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-52104", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-52072", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-52156", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-52103", enabled = true, groups = { "regression" })
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
	//@Test(description = "RPMXCON-52137", enabled = true, groups = { "regression" })
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
	//@Test(description = "RPMXCON-52136", enabled = true, groups = { "regression" })
	public void clickAnalyticalPanelDocPartOfMiniDocList() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52136");

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
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
	//@Test(description = "RPMXCON-52133", enabled = true, groups = { "regression" })
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
		docViewPage.codeSameAsAnalyticalChildWindow(assgnColour, Input.stampColour, Input.stampColour,
				Input.stampColour);

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager '" + Input.rmu1userName + "'");

	}

	/**
	 * @Author : Baskar date:20/9/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify when user clicks the 'Code same as last' on selecting
	 *              the code same as action for document selected from analytics
	 *              panel child window in context of a security group
	 */

	@Test(description = "RPMXCON-52131", enabled = true, groups = { "regression" })
	public void codeSameAsLastInAnalyticalPanel() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-52131");
		String assgnColour = "AssignC" + Utility.dynamicNameAppender();
		String assgnFolder = "AFolder" + Utility.dynamicNameAppender();

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		codingForm.selectDefaultProjectCodingForm();
		driver.waitForPageToBeReady();
		// Session search to doc view after creating folder
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkFolderNearDupe(assgnFolder);
		tagsAndFoldersPage.ViewinDocViewthrFolder(assgnFolder);

		docViewPage.codeSameAsLastAnalytical(assgnColour, "RED", "RED", "RED");

		loginPage.logout();
		baseClass.stepInfo("Successfully logout Reviewer Manager '" + Input.rmu1userName + "'");

	}

	/**
	 * @Author : Baskar date:20/9/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify when user clicks the Save and Next on selecting the
	 *              code same as action for document selected from mini doc list in
	 *              context of a security group
	 */

	//@Test(description = "RPMXCON-52107", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-52224", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-52145", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-52126", enabled = true, groups = { "regression" })
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
		sessionSearch.advancedNewContentSearch1(Input.searchStringStar);

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

	//@Test(description = "RPMXCON-52097", enabled = true, dataProvider = "userDetails", groups = { "regression" })
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

	//@Test(description = "RPMXCON-52096", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-52095", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-52094", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-51220", enabled = true, groups = { "regression" })
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
	 * @Author : Iyappan.Kasinathan
	 * @Description :Verify on click of 'Save'/'Complete button coding form should
	 *              be validated as per the customized"+ " coding form using Tag
	 *              objects along with "Selected" condition
	 */
	//@Test(description = "RPMXCON-51211", enabled = true, groups = { "regression" })
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
	//@Test(description = "RPMXCON-51208", enabled = true, groups = { "regression" })
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
	//@Test(description = "RPMXCON-51213", enabled = true, groups = { "regression" })
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
	//@Test(description = "RPMXCON-51212", enabled = true, groups = { "regression" })
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
	 * @Author : Baskar date:03/01/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify when stamp is saved from completed document then stamp
	 *              should be applied to the un complete document to complete those
	 */

	//@Test(description = "RPMXCON-51578", enabled = true, groups = { "regression" })
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
		sessionSearch.basicContentSearch(Input.searchString1);
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
	 * @Author : Baskar date:23/11/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify coding stamp should be applied to saved documents in an
	 *              assignment
	 */

	//@Test(description = "RPMXCON-51002", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-51003", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-51587", enabled = true, groups = { "regression" })
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
		String tinyInt = "TinyInt" + Utility.dynamicNameAppender();
		String smallInt = "SmallInt" + Utility.dynamicNameAppender();
		String avearageInt = "SmallInt" + Utility.dynamicNameAppender();
		String bigInt = "BigInt" + Utility.dynamicNameAppender();
		String hugeInt = "HugeInt" + Utility.dynamicNameAppender();

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

	//@Test(description = "RPMXCON-52149", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-52106", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-52146", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-51575", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-51576", enabled = true, groups = { "regression" })
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
	//@Test(description = "RPMXCON-52073", enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" })
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
			codingForm.assignCodingFormToSG(Input.codingFormName);
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
	//@Test(description = "RPMXCON-50998", enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" })
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
			codingForm.assignCodingFormToSG(Input.codingFormName);
		}

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.testData1);
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
	//@Test(description = "RPMXCON-52041", enabled = true, dataProvider = "paToRmuRev", groups = { "regression" })
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
		sessionSearch.advancedNewContentSearch1(Input.testData1);

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
	//@Test(description = "RPMXCON-52046", enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" })
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
			codingForm.assignCodingFormToSG(Input.codingFormName);
		}

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		docViewPage.selectPureHit();

		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch1(Input.testData1);
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
	//@Test(description = "RPMXCON-52047", enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" })
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
			codingForm.assignCodingFormToSG(Input.codingFormName);
		}

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		docViewPage.selectPureHit();

		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch1(Input.testData1);
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
	//@Test(description = "RPMXCON-52048", enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" })
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
			codingForm.assignCodingFormToSG(Input.codingFormName);
		}

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		docViewPage.selectPureHit();

		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch1(Input.testData1);
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
	//@Test(description = "RPMXCON-52049", enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" })
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
			codingForm.assignCodingFormToSG(Input.codingFormName);
		}

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		docViewPage.selectPureHit();

		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch1(Input.testData1);
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
	//@Test(description = "RPMXCON-52050", enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" })
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
			codingForm.assignCodingFormToSG(Input.codingFormName);
		}

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		docViewPage.selectPureHit();

		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch1(Input.testData1);
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

	//@Test(description = "RPMXCON-51303", enabled = true, groups = { "regression" })
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
		assignmentPage.add2ReviewerAndDistribute();
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignmentPage.viewSelectedAssgnUsingPagination(assignmentName);
		baseClass.waitTillElemetToBeClickable(assignmentPage.getAssignmentActionDropdown());
		assignmentPage.getAssignmentActionDropdown().waitAndClick(10);
		assignmentPage.assignmentActions("DocView");
		reusableDocView.stampColourSelection(Input.searchString2, Input.stampColour);
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

	//@Test(description = "RPMXCON-51302", enabled = true, groups = { "regression" })
	public void verifyFloppyIconTooltip() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-51302");
		baseClass.stepInfo("Verify tool tip on mouse over of the floppy icon to save the stamp");
		String assignmentName = "assignment" + Utility.dynamicNameAppender();
		// Login as a Rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		codingForm.assignCodingFormToSG(Input.codingFormName);
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
	//@Test(description = "RPMXCON-51027", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-51024", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-52042", enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" })
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
			codingForm.assignCodingFormToSG(Input.codingFormName);
		}

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		docViewPage.selectPureHit();
		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch1(Input.testData1);
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
		softAssertion.assertAll();

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 06/12/2021 Modified date: NA Modified by: Baskar
	 * @Description:Coding Form Child Window: Verify that for DA impersonating as
	 *                     RMU/Reviewer coding stamps option should be displayed
	 *                     outside the context of an assignment
	 */
	//@Test(description = "RPMXCON-52040", enabled = true, dataProvider = "daToRmuRev", groups = { "regression" })
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
		sessionSearch.advancedNewContentSearch1(Input.testData1);

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
	//@Test(description = "RPMXCON-52038", enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" })
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
		sessionSearch.advancedNewContentSearch1(Input.testData1);

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
	//@Test(description = "RPMXCON-52037", enabled = true, dataProvider = "paToRmuRev", groups = { "regression" })
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
		sessionSearch.advancedNewContentSearch1(Input.testData1);

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
	//@Test(description = "RPMXCON-52035", enabled = true, dataProvider = "saToRmuRev", groups = { "regression" })
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
		sessionSearch.advancedNewContentSearch1(Input.testData1);

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

	//@Test(description = "RPMXCON-51681", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-51680", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-51679", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-51678", enabled = true, groups = { "regression" })
	public void validateCompleteBtnAfterScrollCF() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-51678");
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
	//@Test(description = "RPMXCON-51050", enabled = true, groups = { "regression" })
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
		docViewPage.completeDocumentRandom(2, comment);
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
	 * @Author : Baskar date: NA Modified date:03/03/2022 Modified by: Baskar
	 * @Description :Verify length validation for editable metadata field (datatype
	 *              NVARCHAR) from doc view coding form on click of Save
	 */

	//@Test(description = "RPMXCON-59483", enabled = true, groups = { "regression" })
	public void validateNvarcharUsingLength() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-59483");
		baseClass.stepInfo("Verify length validation for editable metadata field (datatype NVARCHAR) "
				+ "from doc view coding form on click of Save");
		String formName = "CFMetaData" + Utility.dynamicNameAppender();
		String NVARCHAR = "NVARCHAR";
		String Defaultaction = "Make It Required";
		String tinyInt = "TinyInt" + Utility.dynamicNameAppender();
		String smallInt = "SmallInt" + Utility.dynamicNameAppender();
		String avearageInt = "SmallInt" + Utility.dynamicNameAppender();
		String bigInt = "BigInt" + Utility.dynamicNameAppender();

		UtilityLog.info("Started Execution for prerequisite");
		DocExplorerPage docExplore = new DocExplorerPage(driver);

		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Successfully login as Project Administration'" + Input.pa1userName + "'");

		// Custom Field created with INT DataType
		projectPage.clickingManageButton();
		projectPage.addMetaDataFieldUsingIntergerType(tinyInt, NVARCHAR, Input.docBasic, Input.tinyInt);
		projectPage.addMetaDataFieldUsingIntergerType(smallInt, NVARCHAR, Input.docBasic, Input.smallInt);
		projectPage.addMetaDataFieldUsingIntergerType(avearageInt, NVARCHAR, Input.docBasic, Input.averageInt);
		projectPage.addMetaDataFieldUsingIntergerType(bigInt, NVARCHAR, Input.docBasic, Input.bigInt);
		baseClass.stepInfo("Custom meta data field created with NVARCHAR datatype");

		// Custom Field Assign to SecurityGroup
		securityGroupPage.addProjectFieldtoSG(tinyInt);
		securityGroupPage.addProjectFieldtoSG(smallInt);
		securityGroupPage.addProjectFieldtoSG(avearageInt);
		securityGroupPage.addProjectFieldtoSG(bigInt);
		baseClass.stepInfo("Custom metadata NVARCHAR field assign to security group");

		// logout
		loginPage.logout();
		baseClass.stepInfo("Successfully logout Project Administration'" + Input.pa1userName + "'");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");

		// create new coding form
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		codingForm.creatingCodingFormusingMultipleNvachar(formName, tinyInt, smallInt, avearageInt, bigInt,
				Defaultaction);
		baseClass.stepInfo("Coding form created with metadata fieldValue");
		codingForm.assignCodingFormToSG(formName);

		// docexplorer to docview
		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		docExplore.selectAllDocumentsFromCurrentPage();
		docExplore.docExpViewInDocView();

		// validating the coding form using nvarchar datatype
		docViewPage.passingNvacharDatatypeUsingLength(tinyInt, smallInt, avearageInt, bigInt, 20, 50, 400, 4000);
		baseClass.passedStep("Coding form saved with maximum NVARCHAR character successfully");
		docViewPage.passingNvacharDatatypeUsingLength(tinyInt, smallInt, avearageInt, bigInt, 19, 49, 399, 3999);
		baseClass.passedStep("Coding form saved with minimum NVARCHAR character successfully");
		baseClass.waitTime(2);
		codingForm.assignCodingFormToSG(Input.codeFormName);
		codingForm.deleteCodingForm(formName, formName);

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 08/12/2021 Modified date: NA Modified by: Baskar
	 * @Description:Verify tool tip on mouse over of the floppy icon to save the
	 *                     stamp in context of security group
	 */
	//@Test(description = "RPMXCON-52062", enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" })
	public void verifyToolTipStampBtn(String roll, String userName, String password)
			throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52062");
		baseClass.stepInfo(
				"Verify tool tip on mouse over of the floppy icon to save the stamp in context of security group");
		// Login As
		loginPage.loginToSightLine(userName, password);

		if (roll.contains("rmu")) {
			codingForm.assignCodingFormToSG(Input.codingFormName);
		}

		// Searching audio document with different term
		baseClass.stepInfo("Searching audio documents based on search string");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		docViewPage.selectPureHit();

		baseClass.stepInfo("Searching Content documents based on search string");
		sessionSearch.advancedNewContentSearch1(Input.testData1);

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
	//@Test(description = "RPMXCON-52061", enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" })
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
		sessionSearch.advancedNewContentSearch1(Input.testData1);

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
	//@Test(description = "RPMXCON-52127", enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" })
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
		sessionSearch.advancedNewContentSearch1(Input.testData1);

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
	//@Test(description = "RPMXCON-52125", enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" })
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
		sessionSearch.advancedNewContentSearch1(Input.testData1);

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
	//@Test(description = "RPMXCON-52099", enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" })
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
		sessionSearch.advancedNewContentSearch1(Input.testData1);

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
	//@Test(description = "RPMXCON-52098", enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" })
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
		sessionSearch.advancedNewContentSearch1(Input.testData1);

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
	//@Test(description = "RPMXCON-52120", enabled = true, groups = { "regression" })
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
	//@Test(description = "RPMXCON-51048", enabled = true, groups = { "regression" })
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
	 * @Author : Baskar date:06/12/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify that after loading additional documents in mini doc
	 *              list Complete action on the principal document should be working
	 */

	//@Test(description = "RPMXCON-51677", enabled = true, groups = { "regression" })
	public void validateCompleteBtnAfterScroll() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-51677");
		baseClass.stepInfo("Verify that after loading additional documents in mini doc list "
				+ "Complete action on the principal document should be working");
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
	 * @Author : Baskar date: NA Modified date:10/12/2021 Modified by: Baskar
	 * @Description :Verify validation of coding form when coding form is created
	 *              with metadata field as Integer on click of 'Save'
	 */

	//@Test(description = "RPMXCON-51581", enabled = true, groups = { "regression" })
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
		String tinyInt = "TinyInt" + Utility.dynamicNameAppender();
		String smallInt = "SmallInt" + Utility.dynamicNameAppender();
		String avearageInt = "SmallInt" + Utility.dynamicNameAppender();
		String bigInt = "BigInt" + Utility.dynamicNameAppender();
		String hugeInt = "HugeInt" + Utility.dynamicNameAppender();

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

	//@Test(description = "RPMXCON-51586", enabled = true, groups = { "regression" })
	public void metaDataUsingIntegerUsingComplete() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-51586");
		baseClass.stepInfo("Verify validation of coding form when coding form is "
				+ "created with metadata field as Integer on click of 'Complete'");
		String formName = "CFMetaData" + Utility.dynamicNameAppender();
		String assgnCoding = "codingAssgn" + Utility.dynamicNameAppender();
		String tinyInt = "TinyInt" + Utility.dynamicNameAppender();
		String smallInt = "SmallInt" + Utility.dynamicNameAppender();
		String avearageInt = "SmallInt" + Utility.dynamicNameAppender();
		String bigInt = "BigInt" + Utility.dynamicNameAppender();
		String hugeInt = "HugeInt" + Utility.dynamicNameAppender();
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
	//@Test(description = "RPMXCON-52110", enabled = true, groups = { "regression" })
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

	/**
	 * @Author : Baskar date:13/12/21 Modified date: NA Modified by: Baskar
	 * @Description :To verify that if document is complete is one Assignment, user
	 *              can modify same document in another assignment.
	 */
	//@Test(description = "RPMXCON-50991", enabled = true, groups = { "regression" })
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
		baseClass.waitForElement(docViewPage.getVerifyPrincipalDocument());
		String docId = docViewPage.getVerifyPrincipalDocument().getText();
		baseClass.stepInfo("Completed docs in userOne:" + docId + "");
		reusableDocView.editCodingForm(comment);
		docViewPage.getCompleteDocBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		// logout
		loginPage.logout();
		// validating docs in user two
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		assignmentPage.SelectAssignmentByReviewer(AssignStampTwo);
		baseClass.waitForElement(docViewPage.getDociD(docId));
		docViewPage.getDociD(docId).waitAndClick(5);
		baseClass.stepInfo("verifying completed docs in userOne:" + docId + "");
		baseClass.stepInfo("Same Comment displayed");
		reusableDocView.verifyingComments(comment);
		reusableDocView.completeButton();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDociD(docId));
		docViewPage.getDociD(docId).waitAndClick(5);
		reusableDocView.uncompleteButtonValidate();
		driver.waitForPageToBeReady();
		softAssertion.assertAll();
	}

	/**
	 * @Author : Baskar date:14/12/21 Modified date: NA Modified by: Baskar
	 * @Description : To verify for saved document in an assignment, custom coding
	 *              form is editable on doc view
	 */
	//@Test(description = "RPMXCON-50993", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-50994", enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" })
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
			codingForm.assignCodingFormToSG(Input.codingFormName);
		}

		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		reusableDocView.editCodingFormAndSaveWithStampColour(fieldText, Input.stampColour, comment);
		baseClass.VerifySuccessMessage("Coding Stamp saved successfully");
		reusableDocView.deleteStampColour(Input.stampColour);
		driver.waitForPageToBeReady();

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date:14/12/21 Modified date: NA Modified by: Baskar
	 * @Description : To verify user should not save the coding stamp without
	 *              selecting coding form for required fields
	 */

	//@Test(description = "RPMXCON-50995", enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" })
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
			codingForm.assignCodingFormToSG(Input.codingFormName);
		}

		sessionSearch.basicContentSearch(Input.searchString1);
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
	//@Test(description = "RPMXCON-50996", enabled = true, groups = { "regression" })
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
	//@Test(description = "RPMXCON-50997", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-50999", enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" })
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
			codingForm.assignCodingFormToSG(Input.codingFormName);
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

	//@Test(description = "RPMXCON-51000", enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" })
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
			codingForm.assignCodingFormToSG(Input.codingFormName);
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
	//@Test(description = "RPMXCON-51029", enabled = true, groups = { "regression" })
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

		docViewPage.completeDocumentRandom(2, comment);
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

	//@Test(description = "RPMXCON-51039", enabled = true, groups = { "regression" })
	public void validateProjectAdminCodeSameAsLast() throws InterruptedException, AWTException {
		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51039");
		baseClass.stepInfo("Verify for Project Admin 'Complete same as last doc' should not be displayed");
		// Login As
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		if (docViewPage.getCodeSameAsLast().isDisplayed()) {
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
	//@Test(description = "RPMXCON-51293", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-50929", enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" })
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
			codingForm.assignCodingFormToSG(Input.codingFormName);
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

	//@Test(description = "RPMXCON-50930", enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" })
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
			codingForm.assignCodingFormToSG(Input.codingFormName);
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

	//@Test(description = "RPMXCON-50961", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-50964", enabled = true, groups = { "regression" })
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
		docViewPage.performCodeSameForFamilyMembersDocumentsReviewer();

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
	//@Test(description = "RPMXCON-50963", enabled = true, groups = { "regression" })
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
		docViewPage.performCodeSameForFamilyMembersDocumentsReviewer();

		// Edit coding Form and complete Action
		docViewPage.editCodingFormComplete();
//		baseClass.VerifySuccessMessage("Document completed successfully");
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

	//@Test(description = "RPMXCON-50965", enabled = true, groups = { "regression" })
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
	//@Test(description = "RPMXCON-51294", enabled = true, groups = { "regression" })
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
	//@Test(description = "RPMXCON-51637", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-50928", enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" })
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
			codingForm.assignCodingFormToSG(Input.codingFormName);
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

	//@Test(description = "RPMXCON-50931", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-50932", enabled = true, groups = { "regression" })

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
		codingForm.assignCodingFormToSG(Input.codingFormName);
		loginPage.logout();

		softAssertion.assertAll();
	}

	/**
	 * @Author : Baskar date: 17/12/2021 Modified date: NA Modified by: Baskar
	 * @Description:To verify that user cannot view the 'Coding Stamps' on Doc View,
	 *                 if preferences set as 'OFF' from the assignment.
	 */
	//@Test(description = "RPMXCON-50937", enabled = true, groups = { "regression" })
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
	//@Test(description = "RPMXCON-52109", enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" })
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

	//@Test(description = "RPMXCON-51292", enabled = true, groups = { "regression" })

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

	//@Test(description = "RPMXCON-50938", enabled = true, groups = { "regression" })

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
			codingForm.assignCodingFormToSG(Input.codingFormName);
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

	//@Test(description = "RPMXCON-52036", enabled = true, dataProvider = "daToRmuRev", groups = { "regression" })
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
		sessionSearch.advancedNewContentSearch1(Input.testData1);

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

	//@Test(description = "RPMXCON-50940", enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" })

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
			codingForm.assignCodingFormToSG(Input.codingFormName);
		}

		// session search to docview panel
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.ViewInDocView();

		// docViewPage.verifyCodeSameForSavedDocs(comment);

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date:31/12/21 Modified date: NA Modified by: Baskar
	 * @Description : Verify checkmark should be displayed for document completed
	 *              same as last after code same as this action
	 */

	//@Test(description = "RPMXCON-51260", enabled = true, groups = { "regression" })
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
	 * @Author : Mohan date:20/12/21 Modified date: NA Modified by: NA
	 * @Description : Coding Form Child Window: Verify on saving of the stamp coding
	 *              form should not clear for the document in context of security
	 *              group 'RPMXCON-52067'
	 * @throws AWTException
	 */

	//@Test(description = "RPMXCON-52067", enabled = true, groups = { "regression" })

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

	//@Test(description = "RPMXCON-50966", enabled = true, groups = { "regression" })

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

	//@Test(description = "RPMXCON-50968", enabled = true, groups = { "regression" })

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
//		baseClass.VerifySuccessMessage("Document completed successfully");

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

	//@Test(description = "RPMXCON-50992", enabled = true, groups = { "regression" })
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
		baseClass.impersonateRMUtoReviewer();
		assignmentPage.SelectAssignmentByReviewer(assignmentName);
		driver.waitForPageToBeReady();
		baseClass.CloseSuccessMsgpopup();
		baseClass.stepInfo("User on the docview page after selecting the Assignment");
		driver.waitForPageToBeReady();
		miniDocListPage.configureMiniDocListToShowCompletedDocs();
		driver.waitForPageToBeReady();
		baseClass.waitTime(2);
		boolean completeButton = docViewPage.getCompleteDocBtn().Displayed();
		softAssertion.assertFalse(completeButton);
		baseClass.passedStep("Complete button not displayed");
		baseClass.waitForElement(assignmentPage.unCompleteBtn());
		boolean flag = assignmentPage.unCompleteBtn().Displayed();
		softAssertion.assertTrue(flag);
		softAssertion.assertAll();
		baseClass.passedStep("Coding form is not editable");
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 21/12/2021 Modified date: NA Modified by: Baskar
	 * @Description:Verify that when user clicks 'code same as last' when audio file
	 *                     is in play mode
	 */
	//@Test(description = "RPMXCON-52134", enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" })
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
	//@Test(description = "RPMXCON-52051", enabled = true, groups = { "regression" })
	public void verifyCodingStampSaved() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52051");
		baseClass.stepInfo(
				"Verify on saving of user stamp all control selections on coding form should not be clear, in context of security group");

		String colour = "BLUE";
		String comment = "comment" + Utility.dynamicNameAppender();
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
		docViewPage.docViewCodingFormPanelStampSelection(colour, comment);

		loginPage.logout();

	}

	/**
	 * @Author : Baskar date:31/12/21 Modified date: NA Modified by: Baskar
	 * @Description : To verify that Project Admin cannot complete or save or Save
	 *              and Next the document, cannot save stamp
	 */

	//@Test(description = "RPMXCON-50988", enabled = true, groups = { "regression" })
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
	 * @Author : Vijaya.Rani date:21/12/21 Modified date: NA Modified by: NA
	 * @Description : Verify when user clicks the 'Save and Next' on selecting the
	 *              document from history drop down. 'RPMXCON-52118' Sprint : 8
	 */

	//@Test(description = "RPMXCON-52118", enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" })
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
		sessionSearch.advancedNewContentSearch1(Input.searchStringStar);

		baseClass.stepInfo("Open the searched documents in doc view mini list");
		sessionSearch.ViewInDocViews();

		driver.waitForPageToBeReady();
		docViewPage.editCodingFormAndSaveWithStamp(filedText, Input.stampColours);
		docViewPage.performCodeFormAndStampInDocView(Input.stampColours, Input.stampColours, commands);

		loginPage.logout();

	}

	/**
	 * @Author : Vijaya.Rani date:21/12/21 Modified date: NA Modified by: NA
	 * @Description : Verify user clicks code same as last on navigating to doc view
	 *              from search. 'RPMXCON-52124' Sprint : 8
	 */

	//@Test(description = "RPMXCON-52124", enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" })
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
		sessionSearch.advancedNewContentSearch1(Input.searchStringStar);

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
	//@Test(description = "RPMXCON-52121", enabled = true, groups = { "regression" })
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
	//@Test(description = "RPMXCON-52122", enabled = true, groups = { "regression" })
	public void verifyToolTipCodeSameAsLast() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-52122");

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

	//@Test(description = "RPMXCON-52117", enabled = true, groups = { "regression" })
	public void verifiyNavigateToDocument() throws InterruptedException, AWTException {
		baseClass.stepInfo("Test case Id: RPMXCON-52117");

		baseClass.stepInfo(
				"Verify when user clicks the saved stamp when navigating to document on document navigation options [<<, <, >, >>, enter document number]");
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		codingForm = new CodingForm(driver);
		String filedText = "Stamp" + Utility.dynamicNameAppender();

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
		docViewPage.editCodingFormAndSaveWithStamp(filedText, Input.stampColours);
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
		docViewPage.deleteStampColour(Input.stampColours);

		baseClass.passedStep(
				"Verified when user clicks the saved stamp when navigating to document on document navigation options [<<, <, >, >>, enter document number]");
		loginPage.logout();

	}

	/**
	 * @Author : Baskar date: 22/12/2021 Modified date: NA Modified by: Baskar
	 * @Description:Verify that on click of the saved stamp should load the coding
	 *                     form values in context of security group
	 */
	//@Test(description = "RPMXCON-52068", enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" })
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
		sessionSearch.advancedNewContentSearch1(Input.testData1);

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
	//@Test(description = "RPMXCON-52066", enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" })
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
		sessionSearch.advancedNewContentSearch1(Input.testData1);

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

	//@Test(description = "RPMXCON-51749", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-51748", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-51747", enabled = true, groups = { "regression" })
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
	//@Test(description = "RPMXCON-50944", enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" })
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
		sessionSearch.advancedNewContentSearch1(Input.testData1);

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
	//@Test(description = "RPMXCON-51676", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-52138", enabled = true, groups = { "regression" })
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
		String projectFieldINT = "DataA" + Utility.dynamicNameAppender();

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
		sessionSearch.ViewInDocViews();

		// verify the coding form panel
		docViewPage.verifyCommentAndMetadata(addComment, commentText, projectFieldINT, metadataText);
		baseClass.stepInfo("Checking index of comment and metadata for saved document");
		sessionSearch.metadataAndCommentSearch(projectFieldINT, metadataText, addComment, commentText);

		codingForm.assignCodingFormToSG(Input.codingFormName);
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
	//@Test(description = "RPMXCON-52116", enabled = true, groups = { "regression" })
	public void savedStampInHistoryDropDown() throws InterruptedException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52116");
		baseClass.stepInfo("Verify when user clicks the saved stamp on selecting the document from history drop down");
		String comment = "comment" + Utility.dynamicNameAppender();
		String filedText = "Stamp" + Utility.dynamicNameAppender();

		// Login As Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		codingForm.assignCodingFormToSG("Default Project Coding Form");

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
	//@Test(description = "RPMXCON-52119", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-51584", enabled = true, groups = { "regression" })
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
		String date = "Date" + Utility.dynamicNameAppender();

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

	//@Test(description = "RPMXCON-51585", enabled = true, groups = { "regression" })
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
		String date = "Date" + Utility.dynamicNameAppender();

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

	//@Test(description = "RPMXCON-51301", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-51190", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-51032", enabled = true, groups = { "regression" })
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
	//@Test(description = "RPMXCON-51261", enabled = true, groups = { "regression" })
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
	//@Test(description = "RPMXCON-51291", enabled = true, groups = { "regression" })
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
	//@Test(description = "RPMXCON-51283", enabled = true, groups = { "regression" })
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
	//@Test(description = "RPMXCON-52123", enabled = true, groups = { "regression" })
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
	//@Test(description = "RPMXCON-51465", enabled = true, groups = { "regression" })
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

	//@Test(description = "RPMXCON-52113", enabled = true, groups = { "regression" })
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
		String projectFieldINT = "DataAA" + Utility.dynamicNameAppender();

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

		codingForm.assignCodingFormToSG(Input.codingFormName);
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
	//@Test(description = "RPMXCON-50984", enabled = true, groups = { "regression" })
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
	//@Test(description = "RPMXCON-50985", enabled = true, groups = { "regression" })
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
	 * @Author : Baskar date: 18/01/2021 Modified date: NA Modified by: Baskar
	 * @Description:Verify user can view the coding for the stamp on click of the
	 *                     'View Coding' button from edit coding stamp pop up in
	 *                     context of security group
	 */

	//@Test(description = "RPMXCON-52058", enabled = true, groups = { "regression" })
	public void validateFromSgViewCoding() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-52058");
		baseClass.stepInfo("Verify user can view the coding for the stamp on click of the "
				+ "'View Coding' button from edit coding stamp pop up in context of security group");
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
		docViewPage.pencilGearicon(Input.stampSelection);
		boolean EditStamp = docViewPage.getEditCodingStamp_PopUpWindow().Displayed();
		softAssertion.assertTrue(EditStamp);
		baseClass.stepInfo("Edit coding stamp popup window opened to re-edit the stamp");
		docViewPage.clickViewCodingButton();
		driver.waitForPageToBeReady();

		// validation for saved stamp in view coding popup
		boolean viewCoding = docViewPage.getViewCodingStamp_PopUpWindow().Displayed();
		softAssertion.assertTrue(viewCoding);
		baseClass.waitForElement(docViewPage.getDocumentsCommentViewCoding());
		docViewPage.getDocumentsCommentViewCoding().ScrollTo();
		String actual = docViewPage.getDocumentsCommentViewCoding().getText();
		softAssertion.assertEquals(comment, actual);
		baseClass.stepInfo("verify viewcodingstamp popup saved stamp value is successfully displayed ");
		docViewPage.getViewCodingCloseButton().waitAndClick(5);
		baseClass.waitForElement(docViewPage.getDeletePopUpAssignedColour());
		docViewPage.getDeletePopUpAssignedColour().waitAndClick(10);
		softAssertion.assertAll();
		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar date: 18/01/2021 Modified date: NA Modified by: Baskar
	 * @Description:Verify coding form objects should be displayed on edit coding
	 *                     stamp in context of security group
	 */

	//@Test(description = "RPMXCON-52052", enabled = true, groups = { "regression" })
	public void validateCodingFormObject() throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-52052");
		baseClass.stepInfo("Verify coding form objects should be "
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
		docViewPage.pencilGearicon(Input.stampSelection);
		boolean EditStamp = docViewPage.getEditCodingStamp_PopUpWindow().Displayed();
		softAssertion.assertTrue(EditStamp);
		if (docViewPage.getCodingStampPopUpColurVerify(Input.stampSelection).isDisplayed()) {
			baseClass.passedStep("Coding stamp applied colour displayed in popup");
		} else {
			baseClass.failedStep("Coding stamp applied colour not displayed in popup");
		}
		docViewPage.clickViewCodingButton();
		driver.waitForPageToBeReady();

		// validation for coding form saved object
		boolean viewCoding = docViewPage.getViewCodingStamp_PopUpWindow().Displayed();
		softAssertion.assertTrue(viewCoding);
		baseClass.waitForElement(docViewPage.getDocumentsCommentViewCoding());
		docViewPage.getDocumentsCommentViewCoding().ScrollTo();
		String actual = docViewPage.getDocumentsCommentViewCoding().getText();
		softAssertion.assertEquals(comment, actual);
		baseClass.stepInfo("Coding form saved object values displayed in viewcoding popup window");
		docViewPage.getViewCodingCloseButton().waitAndClick(5);
		baseClass.waitForElement(docViewPage.getDeletePopUpAssignedColour());
		docViewPage.getDeletePopUpAssignedColour().waitAndClick(10);
		softAssertion.assertAll();
		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Sakthivel date:30/12/2021 Modified date:NA
	 * @Description :Verify when user clicks 'Save and Next' when document not part
	 *              of mini doc list is viewed from analytics panel
	 */
	//@Test(description = "RPMXCON-52111", enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" })
	public void verifyDocNotPartInMiniDocListViewed(String fullName, String userName, String password)
			throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();

		// Login As RMU
		String rmu = "RMU";
		String comment = "comments" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		baseClass.stepInfo("Test case Id: RPMXCON- 52111");
		baseClass.stepInfo("Verify when user clicks 'Save and Next' when document not part"
				+ "of mini doc list is viewed from analytics panel");
		baseClass.stepInfo("Searching Content documents based on search string");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Open the searched documents in doc view mini list");

		// To view the NearDupe Doc in the DocView
		sessionSearch.ViewNearDupeDocumentsInDocView();
		docViewPage.verifyNotPartofDocViewAnalyticalPanelNearDupeTab();
		docViewPage.closeWindow(1);
		docViewPage.switchToNewWindow(1);
		driver.waitForPageToBeReady();

		// DocViewCodingform edit and saved
		docViewPage.editCodingForm(comment);
		driver.scrollPageToTop();
		docViewPage.codingFormSaveAndNext();
		baseClass.passedStep("Document saved successfully");

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
	//@Test(description = "RPMXCON-52108", enabled = true, groups = { "regression" })
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
	//@Test(description = "RPMXCON-52112", enabled = true, dataProvider = "rmuRevLogin", groups = { "regression" })
	public void verifySavedStampDocNotPartInDocList(String fullName, String userName, String password)
			throws InterruptedException, AWTException {
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		String filedText = "Stamp" + Utility.dynamicNameAppender();
		String comment = "Saving with Stamp";

		// Login As RMU
		String rmu = "RMU";
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		baseClass.stepInfo("Test case Id: RPMXCON- 52112");
		baseClass.stepInfo("Verify when user clicks saved stamp when document"
				+ "not part of mini doc list is viewed from analytics panel");

		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewNearDupeDocumentsInDocView();

		// DocViewCodingform edit and and stamp saved
		driver.waitForPageToBeReady();
		docViewPage.editCodingForm(comment);
		driver.scrollPageToTop();
		docViewPage.stampColourSelection(filedText, Input.stampColours);
		baseClass.stepInfo("Editing coding form and saving with a stamp colour has been done");
		docViewPage.verifyNotPartofDocViewAnalyticalPanelNearDupeTab();
		docViewPage.closeWindow(1);
		docViewPage.switchToNewWindow(1);
		driver.waitForPageToBeReady();

		// DocViewCodingform saved stamplastIcon click and saved doc
		docViewPage.getCodingFormStampClickAndSave(Input.stampColours);
		driver.Navigate().refresh();
		docViewPage.deleteStampColour(Input.stampColours);
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

	//@Test(description = "RPMXCON-52039", enabled = true, dataProvider = "saToRmuRev", groups = { "regression" })
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
		sessionSearch.advancedNewContentSearch1(Input.testData1);

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

	//@Test(description = "RPMXCON-50982", enabled = true, groups = { "regression" })
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
