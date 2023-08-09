package sightline.assignments;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
import pageFactory.ClassificationPage;
import pageFactory.CodingForm;
import pageFactory.Dashboard;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Assignment_Phase2_Regression {

	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	AssignmentsPage assignment;
	BaseClass baseClass;
	Input in;
	SoftAssert softAssert;
	KeywordPage keyPage;
	DocViewRedactions docViewRedact;
	SecurityGroupsPage securityGroupsPage;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

//		in = new Input();
//		in.loadEnvConfig();
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		assignment = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		sessionSearch = new SessionSearch(driver);
		softAssert = new SoftAssert();

	}

	@DataProvider(name = "Users")
	public Object[][] CombinedSearchwithUsers() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password }, { Input.pa1userName, Input.pa1password },
				{ Input.rev1userName, Input.rev1password } };
		return users;
	}

	/**
	 * @author NA Testcase No:RPMXCON-54340
	 * @Description: To Verify that if the Assignment name is long, the last three
	 *               visible characters are an ellipsis
	 **/
	@Test(description = "RPMXCON-54340", enabled = true, groups = { "regression" })
	public void verifyAssignmentNameLong() throws Exception {
		AssignmentsPage assign = new AssignmentsPage(driver);
		SessionSearch session = new SessionSearch(driver);

		String assignmentName = "Assignment" + Utility.randomCharacterAppender(18);
		String expStatus = "ellipsis";

		baseClass.stepInfo("RPMXCON-54340");
		baseClass.stepInfo(
				"To Verify that if the Assignment name is long, the last three visible characters are an ellipsis");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in As : " + Input.rmu1userName);
		assign.navigateToAssignmentsPage();
		driver.waitForPageToBeReady();
		assign.createAssignment(assignmentName, Input.codingFormName);
		driver.waitForPageToBeReady();

		session.navigateToSessionSearchPageURL();
		driver.waitForPageToBeReady();
		session.basicContentSearch(Input.testData1);
		driver.waitForPageToBeReady();

		session.bulkAssign();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(session.getBulkAssignAssignDocumentsButton());
		session.getBulkAssignAssignDocumentsButton().waitAndClick(5);
		baseClass.waitForElement(session.getSelectAssignmentExisting(assignmentName));
		String actStatusAssignDoc = session.getSelectAssignmentExisting(assignmentName).GetCssValue("text-overflow")
				.trim();
		System.out.println(actStatusAssignDoc);

		baseClass.waitForElement(session.getUnAssignRadioBtn());
		session.getUnAssignRadioBtn().waitAndClick(5);
		baseClass.waitForElement(session.getExistingAssignmentToUnAssign(assignmentName));
		String actStatusUnAssignDoc = session.getExistingAssignmentToUnAssign(assignmentName)
				.GetCssValue("text-overflow").trim();
		System.out.println(actStatusAssignDoc);

		if (expStatus.equalsIgnoreCase(actStatusAssignDoc) && expStatus.equalsIgnoreCase(actStatusUnAssignDoc)) {
			baseClass.passedStep("For long Assignment name, last three visible characters displayed as an ellipsis");
		} else {
			baseClass
					.failedStep("For long Assignment name, last three visible characters Not displayed as an ellipsis");
		}
		baseClass.passedStep(
				"Verified - that if the Assignment name is long, the last three visible characters are an ellipsis");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-54324
	 * @Description: To Verify that if the Assignment name is long, the last three
	 *               visible characters are an ellipsis
	 **/
	@Test(description = "RPMXCON-54324", enabled = true, groups = { "regression" })
	public void verifyAssignmentNameLongDocs() throws Exception {
		AssignmentsPage assign = new AssignmentsPage(driver);
		SessionSearch session = new SessionSearch(driver);

		String assignmentName = "Assignment" + Utility.randomCharacterAppender(18);
		String expStatus = "ellipsis";

		baseClass.stepInfo("RPMXCON-54324");
		baseClass.stepInfo(
				"To Verify that if the Assignment name is long, the last three visible characters are an ellipsis");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in As : " + Input.rmu1userName);
		assign.navigateToAssignmentsPage();
		driver.waitForPageToBeReady();

		assign.createAssignment(assignmentName, Input.codingFormName);
		driver.waitForPageToBeReady();

		session.navigateToSessionSearchPageURL();
		driver.waitForPageToBeReady();
		session.basicContentSearch(Input.testData1);
		driver.waitForPageToBeReady();
		session.bulkAssignExisting(assignmentName);
		driver.waitForPageToBeReady();

		session.bulkAssign();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(session.getBulkAssignAssignDocumentsButton());
		session.getBulkAssignAssignDocumentsButton().waitAndClick(5);
		baseClass.waitForElement(session.getSelectAssignmentExisting(assignmentName));
		String actStatusAssignDoc = session.getSelectAssignmentExisting(assignmentName).GetCssValue("text-overflow")
				.trim();
		System.out.println(actStatusAssignDoc);

		baseClass.waitForElement(session.getUnAssignRadioBtn());
		session.getUnAssignRadioBtn().waitAndClick(5);
		baseClass.waitForElement(session.getExistingAssignmentToUnAssign(assignmentName));
		String actStatusUnAssignDoc = session.getExistingAssignmentToUnAssign(assignmentName)
				.GetCssValue("text-overflow").trim();
		System.out.println(actStatusAssignDoc);

		if (expStatus.equalsIgnoreCase(actStatusAssignDoc) && expStatus.equalsIgnoreCase(actStatusUnAssignDoc)) {
			baseClass.passedStep("For long Assignment name, last three visible characters displayed as an ellipsis");
		} else {
			baseClass
					.failedStep("For long Assignment name, last three visible characters Not displayed as an ellipsis");
		}
		baseClass.passedStep(
				"Verified - that if the Assignment name is long, the last three visible characters are an ellipsis");
		loginPage.logout();
	}

	/**
	 * @author Brundha.T Test case Id:RPMXCON-54400
	 * @throws InterruptedException
	 * @Description :Verify the User is able to see all RMU , Reviewers mapped to
	 *              Assignment.
	 */
	@Test(description = "RPMXCON-54400", enabled = true, groups = { "regression" })
	public void VerifyMappedUsersInAssignment() throws InterruptedException {

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-54400 Assignments");
		baseClass.stepInfo("Verify the User is able to see all RMU , Reviewers mapped to Assignment.");

		String assignmentName = "AssgnName" + Utility.dynamicNameAppender();
		String[] users = { Input.rmu1userName, Input.rev1userName };

		baseClass.stepInfo("Navigating to Assignment Page.");
		assignment.navigateToAssignmentsPage();

		baseClass.stepInfo("Create Assignment");
		assignment.createAssignment(assignmentName, Input.codeFormName);
		assignment.editAssignmentUsingPaginationConcept(assignmentName);

		baseClass.stepInfo("Add Reviewers");
		assignment.assignReviewers(users);

		assignment.navigateToAssignmentsPage();
		assignment.editAssignmentUsingPaginationConcept(assignmentName);
		baseClass.waitTillElemetToBeClickable(assignment.getAssignment_ManageReviewersTab());
		assignment.getAssignment_ManageReviewersTab().waitAndClick(10);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("verifying Mapped users in Assignment");
		int Size = assignment.getDashboadAssgn().size();
		System.out.println(Size);
		if (Size == 2) {
			baseClass.ValidateElement_Presence(assignment.getAssgn_ManageRev_selectReviewer(Input.rmu1userName),
					"Mapped user");
			baseClass.ValidateElement_Presence(assignment.getAssgn_ManageRev_selectReviewer(Input.rev1userName),
					"Mapped user");
			baseClass.passedStep("The user can able to see the mapped RMU/Rev Users to the Assignment");
		} else {
			baseClass.failedStep("The user can't able to see the mapped RMU/Rev Users to the Assignment");
		}

		loginPage.logout();

	}

	/**
	 * @author
	 * @Description : Validate Assignment classification values on Assignment
	 *              screen. [RPMXCON-54508]
	 */
	@Test(description = "RPMXCON-54508", enabled = true, groups = { "regression" })
	public void validateAssignmentClassificationValuesOnAssignmentScreen() {

		List<String> listOfClassifictionOptions = new ArrayList<String>(Arrays.asList("1LR", "2LR", "QC"));
		baseClass.stepInfo("Test case Id: RPMXCON-54508 Assignments.");
		baseClass.stepInfo("Validate Assignment classification values on Assignment screen.");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// navigating to create new assignment page
		baseClass.stepInfo("navigating to Create new Assignment page.");
		assignment.navigateToAssignmentsPage();
		assignment.NavigateToNewEditAssignmentPage("new");

		// verify that Classification list should contain following values 1LR, 2LR and
		// QC
		assignment.verifyClassificationOptionsFromAssignmentPage(listOfClassifictionOptions);
		baseClass.passedStep("verified that Classification list contain following values 1LR, 2LR and QC.");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-54325
	 * @Description:Verify that the full Assignment name appears in a mouse over
	 *                     tool tip, and the same is true of Assignment Groups
	 **/
	@Test(description = "RPMXCON-54325", enabled = true, groups = { "regression" })
	public void verifyFullAssignmentNameToolTip() throws Exception {
		AssignmentsPage assign = new AssignmentsPage(driver);
		SessionSearch session = new SessionSearch(driver);
		SoftAssert asserts = new SoftAssert();

		String assignMentGRP = "AssignGRP" + Utility.randomCharacterAppender(18);
		String assignmentName = "Assignment" + Utility.randomCharacterAppender(18);
		String expStatus = "ellipsis";

		baseClass.stepInfo("RPMXCON-54325");
		baseClass.stepInfo(
				"Verify that the full Assignment name appears in a mouse over tool tip, and the same is true of Assignment Groups");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in As : " + Input.rmu1userName);
		assign.navigateToAssignmentsPage();
		driver.waitForPageToBeReady();
		assign.createAssgnGroup(assignMentGRP);
		driver.waitForPageToBeReady();
		assign.createAssignment(assignmentName, Input.codingFormName);
		driver.waitForPageToBeReady();

		session.navigateToSessionSearchPageURL();
		driver.waitForPageToBeReady();
		session.basicContentSearch(Input.testData1);
		driver.waitForPageToBeReady();
		session.bulkAssignExisting(assignmentName);
		driver.waitForPageToBeReady();

		session.bulkAssign();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(session.getSelectAssignmentExisting(assignmentName));
		String actStatusAssignDoc = session.getSelectAssignmentExisting(assignmentName).GetCssValue("text-overflow")
				.trim();
		System.out.println(actStatusAssignDoc);

		baseClass = new BaseClass(driver);
		baseClass.moveWaitAndClick(session.getSelectAssignmentExisting(assignmentName), 15);
		baseClass.waitTime(2);
		String actStatusPopAssign = session.getExisAssignPOPOVER().GetCssValue("text-overflow").trim();
		System.out.println(actStatusPopAssign);

		baseClass.waitForElement(session.getSelectAssignmentExisting(assignMentGRP));
		baseClass.mouseHoverOnElement(session.getSelectAssignmentExisting(assignMentGRP));
		baseClass.waitTime(2);
		String actStatusPopAssignGRP = session.getExisAssignPOPOVER().GetCssValue("text-overflow").trim();
		System.out.println(actStatusPopAssignGRP);

		asserts.assertEquals(actStatusAssignDoc, expStatus);
		asserts.assertNotEquals(actStatusPopAssign, expStatus);
		asserts.assertNotEquals(actStatusPopAssignGRP, expStatus);
		asserts.assertAll();
		baseClass.passedStep(
				"Verified - that the full Assignment name appears in a mouse over tool tip, and the same is true of Assignment Groups");
		loginPage.logout();
	}

	/**
	 * @author
	 * @Description :"Verify when user selects \"Inclusive Emails\" sampling method
	 *              from Assign Documents pop up."RPMXCON-54343
	 */

	@Test(description = "RPMXCON-54343", enabled = true, groups = { "regression" })
	public void verifyUserSelectInclusiveEmailsSamplingMethodFromAssignDocumentsPopUp() {

		String samplingMethodOption = "Inclusive Email";
		baseClass.stepInfo("Test case Id: RPMXCON-54343 Assignments.");
		baseClass.stepInfo(
				"Verify when user selects \"Inclusive Emails\" sampling method from Assign Documents pop up.");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Performing Search and drag the result to shopping cart
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.addPureHit();
		// Performing Bulk Assign Action
		sessionSearch.bulkAssign();

		// Select Sampling Method as 'Inclusive Emails' From 'Existing Assignment' tab
		baseClass.stepInfo("Selecting Sampling Method as 'Inclusive Emails' From  'Existing Assignment' tab.");
		sessionSearch.changeSampleMethodAndVerifyTextBoxLableOfSampleMethod(samplingMethodOption, true, false);
		baseClass.passedStep(
				"Verified that On selection of this sampling method-> Input field is no longer present in 'Existing Assignment' tab.");

		// Select Sampling Method as 'Inclusive Emails' From 'New Assignment' tab
		baseClass.stepInfo("Selecting Sampling Method as 'Inclusive Emails' From  'New Assignment' tab.");
		driver.Navigate().refresh();
		sessionSearch.bulkAssign();
		baseClass.waitForElement(sessionSearch.getAssgn_NewAssignmnet());
		sessionSearch.getAssgn_NewAssignmnet().waitAndClick(5);
		baseClass.waitTime(4);
		sessionSearch.changeSampleMethodAndVerifyTextBoxLableOfSampleMethodFromNewAssignmentab(samplingMethodOption,
				true);
		baseClass.passedStep(
				"Verified that On selection of this sampling method-> Input field is no longer present in 'New Assignment' tab.");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:6/12/2022 RPMXCON-54219
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Draw from pool - Verify the display of 'Draw from Pool' Action
	 *              when 'Draw From Pool' option is enabled in Assignment with draw
	 *              from pool (i.e 20) is equal to the total docs assigned (i.e. 20)
	 *              and 1 document is completed.
	 */
	@Test(description = "RPMXCON-54219", enabled = true, groups = { "regression" })
	public void verifyDrawFromPoolIsNotDisplayedComplteCodingForm() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-54219");
		baseClass.stepInfo(
				"Draw from pool - Verify the display of 'Draw from Pool' Action when 'Draw From Pool' option is enabled in Assignment with draw from pool (i.e 20) is equal to the total docs assigned (i.e. 20) and 1 document is completed.");

		AssignmentsPage agnmt = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + Input.rmu1userName + "");

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();

		agnmt.FinalizeAssignmentAfterBulkAssign();
		agnmt.createAssignment_fromAssignUnassignPopup(assignmentName, Input.codeFormName);
		driver.waitForPageToBeReady();
		// Draw pool Toggle Disable
		agnmt.toggleEnableOrDisableOfAssignPage(false, true, agnmt.getAssgnGrp_Create_DrawPooltoggle(),
				"Draw From Pool", false);
		driver.scrollPageToTop();
		baseClass.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		baseClass.stepInfo("Created a Assignment " + assignmentName + " with draw pool toggle disabled");
		// Edit Assignment
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		// Draw pool Toggle Enable and making draw limit as 20
		agnmt.toggleEnableOrDisableOfAssignPage(true, false, agnmt.getAssgnGrp_Create_DrawPooltoggle(),
				"Draw From Pool", false);
		agnmt.getAssgnGrp_Create_DrawPoolCount().SendKeys("20");
		// distributing docs
		agnmt.addReviewerInManageReviewerTab();
		driver.scrollPageToTop();
		baseClass.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		agnmt.selectAssignmentToViewinDocview(assignmentName);
		driver.waitForPageToBeReady();
		docView.getDocViewDrpDwnCf().selectFromDropdown().selectByVisibleText(Input.codingFormName);
		docView.codingFormSaveButton();
		this.driver.getWebDriver().get(Input.url + "/Dashboard/Dashboard");
		if (!agnmt.getAssignmentsDrawPoolInreviewerPg(assignmentName).isElementAvailable(3)) {
			baseClass.passedStep("Draw pool link is Not displayed");
		} else {
			baseClass.failedStep("Draw pool link is displayed");
		}
		if (!agnmt.getrev_assgnprogress(assignmentName).isDisplayed()) {
			baseClass.passedStep("progress bar is displayed with 1 document completed and 19 left to do");
		} else {
			baseClass.failedStep("progress bar is not displayed");
		}

		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:6/12/2022 RPMXCON-54395
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify the word Redistribute in all the pop up.
	 */
	@Test(description = "RPMXCON-54395", enabled = true, groups = { "regression" })
	public void verifyRedistributeAllPopup() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-54395");
		baseClass.stepInfo("Verify the word Redistribute in all the pop up.");
		AssignmentsPage assignment = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + Input.rmu1userName + "");

		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.bulkAssign();

		baseClass.stepInfo("Create Assignment");
		assignment.createAssignmentNew(assignmentName, Input.codeFormName);

		assignment.editAssignmentUsingPaginationConcept(assignmentName);
		baseClass.waitTillElemetToBeClickable(assignment.getAssignment_ManageReviewersTab());
		assignment.getAssignment_ManageReviewersTab().waitAndClick(5);
		assignment.getAssgn_ManageRev_selectReviewer(Input.rev1userName).ScrollTo();
		assignment.getAssgn_ManageRev_selectReviewer(Input.rev1userName).waitAndClick(5);
		assignment.getAssgn_ManageRev_Action().waitAndClick(20);
		driver.waitForPageToBeReady();
		baseClass.waitTime(2);
		baseClass.waitForElement(assignment.getAssgn_RedistributeDoc());
		assignment.getAssgn_RedistributeDoc().waitAndClick(10);
		driver.waitForPageToBeReady();
		String CompareString = "Redistribute Documents";
		String ActualString = assignment.getMoveNotificationMessage().getText();
		System.out.println(ActualString);
		baseClass.compareTextViaContains(ActualString, CompareString,
				"Redistribute document is displayed in Belly band message",
				"Redistribute document is not displayed in Belly band message");
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:6/12/2022 RPMXCON-54399
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify the 'Keep families together when distributing' has a
	 *              contextual help icon in Redistribute Documents pop up.
	 */
	@Test(description = "RPMXCON-54399", enabled = true, groups = { "regression" })
	public void verifyRedistributeKeepFamiliesDistributingHelpIconDisplay() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-54399");
		baseClass.stepInfo(
				"Verify the 'Keep families together when distributing' has a contextual help icon in Redistribute Documents pop up");
		AssignmentsPage assignment = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + Input.rmu1userName + "");

		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.bulkAssign();

		baseClass.stepInfo("Create Assignment");
		assignment.createAssignmentNew(assignmentName, Input.codeFormName);

		assignment.editAssignmentUsingPaginationConcept(assignmentName);
		baseClass.waitTillElemetToBeClickable(assignment.getAssignment_ManageReviewersTab());
		assignment.getAssignment_ManageReviewersTab().waitAndClick(5);
		assignment.getAssgn_ManageRev_selectReviewer(Input.rev1userName).ScrollTo();
		assignment.getAssgn_ManageRev_selectReviewer(Input.rev1userName).waitAndClick(5);
		assignment.getAssgn_ManageRev_Action().waitAndClick(20);
		driver.waitForPageToBeReady();
		baseClass.waitTime(2);
		baseClass.waitForElement(assignment.getAssgn_RedistributeDoc());
		assignment.getAssgn_RedistributeDoc().waitAndClick(10);
		baseClass.getYesBtn().waitAndClick(20);
		driver.waitForPageToBeReady();
		baseClass.waitTime(8);
		if (assignment.getKeepFamiliesHelpIcon().isElementAvailable(3)) {
			baseClass.passedStep(
					"The help icon is displayed for 'Keep families together when distributing' in Redistribute Documents pop up");
		} else {
			baseClass.failedStep("The help icon is not displayed for Redistribute Documents pop up");
		}
		assignment.getKeepFamiliesHelpIcon().Click();
		driver.waitForPageToBeReady();
		String CompareString = "When distributing documents to multiple other reviewers, select this option if you do not want to break apart families.";
		String ActualString = assignment.getKeepFamiliesHelpText().getText();
		System.out.println(ActualString);
		if (ActualString.contains(CompareString)) {
			baseClass.passedStep("Keep families together when distributing help Msg is display");
		} else {
			baseClass.failedStep("Keep families together when distributing help Msg is not display");
		}
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-67536
	 * @Description:Verify that user can save/sort single or multiple coding form
	 *                     successfully while " + "creating new assignment in a user
	 *                     created assignment group with cascade setting OFF for new
	 *                     project
	 **/
	@Test(description = "RPMXCON-67536", groups = { "regression" })
	public void verifySingleMultiCFCasecadeOFF() throws InterruptedException {
		Dashboard dash = new Dashboard(driver);
		DocViewPage docView = new DocViewPage(driver);
		SoftAssert asserts = new SoftAssert();
		List<String> codeForm = new ArrayList<String>();
		codeForm.add(Input.codeFormName);

		String assignMentGRP = "AssignGRP" + Utility.dynamicNameAppender();
		String assignName = "Assignment" + Utility.dynamicNameAppender();
		String assignMentGRP1 = "AssignGRP1" + Utility.dynamicNameAppender();
		String assignName1 = "Assignment1" + Utility.dynamicNameAppender();

		baseClass.stepInfo("RPMXCON-67536");
		baseClass.stepInfo("Verify that user can save/sort single or multiple coding form successfully while "
				+ "creating new assignment in a user created assignment group with cascade setting OFF for new project");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignment.navigateToAssignmentsPage();
		assignment.createCascadeNonCascadeAssgnGroup(assignMentGRP, "No");
		assignment.selectAssignmentGroup(assignMentGRP);
		List<String> sorting = assignment.createAssignmentFromAssgnGroupByChangeSortingSequenceOfCF(assignName,
				Input.codeFormName);
		String ExpMultSorting = sorting.toString().replace(" (Set as Default)", "");
		sessionSearch.navigateToSessionSearchPageURL();
		driver.waitForPageToBeReady();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assignName);

		assignment.editAssignmentInAssignGroup(assignMentGRP, assignName);
		driver.waitForPageToBeReady();
		assignment.add2ReviewerAndDistribute(Input.rev1userName,Input.rmu1userName);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.waitForElement(dash.getSelectAssignmentFromDashborad(assignName));
		dash.getSelectAssignmentFromDashborad(assignName).waitAndClick(3);
		driver.waitForPageToBeReady();
		baseClass.waitForElementCollection(docView.listOfCodingFormInDocViewDropDown());
		String actMultiSorting = baseClass.availableListofElements(docView.listOfCodingFormInDocViewDropDown())
				.toString();
		asserts.assertEquals(ExpMultSorting, actMultiSorting);
		asserts.assertAll();
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignment.navigateToAssignmentsPage();
		assignment.createCascadeNonCascadeAssgnGroup(assignMentGRP1, "No");
		assignment.selectAssignmentGroup(assignMentGRP1);
		assignment.createAssignmentFromAssgnGroup(assignName1, Input.codeFormName);

		sessionSearch.navigateToSessionSearchPageURL();
		driver.waitForPageToBeReady();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assignName1);

		assignment.editAssignmentInAssignGroup(assignMentGRP1, assignName1);
		driver.waitForPageToBeReady();
		List<String> singleSorting = assignment.editExistingCodingForm(codeForm, Input.codeFormName, true);
		String ExpSingSorting = singleSorting.toString().replace(" (Set as Default)", "");
		driver.waitForPageToBeReady();
		assignment.add2ReviewerAndDistribute(Input.rev1userName,Input.rmu1userName);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.waitForElement(dash.getSelectAssignmentFromDashborad(assignName1));
		dash.getSelectAssignmentFromDashborad(assignName1).waitAndClick(3);
		driver.waitForPageToBeReady();
		baseClass.waitForElementCollection(docView.listOfCodingFormInDocViewDropDown());
		String actSingSorting = baseClass.availableListofElements(docView.listOfCodingFormInDocViewDropDown())
				.toString();
		System.out.println(actSingSorting);
		System.out.println(ExpSingSorting);
		asserts.assertEquals(ExpSingSorting, actSingSorting);
		asserts.assertAll();

		baseClass.passedStep("Verify that user can save/sort single or multiple coding form successfully while"
				+ " creating new assignment in a user created assignment group with cascade setting OFF for new project");
		loginPage.logout();

	}

	/**
	 * @author NA Testcase No:RPMXCON-54441
	 * @Description:Verify that Application disallow special characters in New
	 *                     Assignments screen when user performed COPY and PASTE
	 *                     (Special Characters) from Notepad.
	 **/
	@Test(description = "RPMXCON-54441", groups = { "regression" })
	public void verifySpclCharErrorMsgNewAssignScr() throws InterruptedException {
		String dataSet[] = { "AssignmentNameWith<test", "AssignmentNameWith(test", "AssignmentNameWith)test",
				"AssignmentNameWith[test", "AssignmentNameWith]test", "AssignmentNameWith{test",
				"AssignmentNameWith}test", "AssignmentNameWith:test", "AssignmentNameWith'test",
				"AssignmentNameWith~test", "AssignmentNameWith*test", "AssignmentNameWith?test",
				"AssignmentNameWith&test", "AssignmentNameWith$test", "AssignmentNameWith#test",
				"AssignmentNameWith@test", "AssignmentNameWith!test", "AssignmentNameWith-test",
				"AssignmentNameWith_test" };

		baseClass.stepInfo("RPMXCON - 54441");
		baseClass.stepInfo(
				"Verify that Application disallow special characters in New Assignments screen when user performed COPY and PASTE (Special Characters) from Notepad.");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged In As : " + Input.rmu1userName);

		assignment.navigateToAssignmentsPage();
		driver.waitForPageToBeReady();
		for (String name : dataSet) {
			assignment.selectAssignmentGroup("Root");
			assignment.NavigateToNewEditAssignmentPage("New");
			baseClass.copyandPasteString(name, assignment.getAssignmentName());
			baseClass.waitForElement(assignment.getAssignmentSaveButton());
			assignment.getAssignmentSaveButton().waitAndClick(5);
			assignment.verifyErrorMsginAssignmentName();
		}
		baseClass.passedStep(
				"Verified - that Application disallow special characters in New Assignments screen when user performed COPY and PASTE (Special Characters) from Notepad.");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-67537
	 * @Description:Verify that user can save/sort single or multiple coding form
	 *                     successfully while creating new assignment
	 **/
	@Test(description = "RPMXCON-67537", groups = { "regression" })
	public void verifySingleMultiCFCasecadeON() throws InterruptedException {
		Dashboard dash = new Dashboard(driver);
		DocViewPage docView = new DocViewPage(driver);
		SoftAssert asserts = new SoftAssert();
		List<String> codeForm = new ArrayList<String>();
		codeForm.add(Input.codeFormName);

		String assignMentGRP = "AssignGRP" + Utility.dynamicNameAppender();
		String assignName = "Assignment" + Utility.dynamicNameAppender();
		String assignMentGRP1 = "AssignGRP1" + Utility.dynamicNameAppender();
		String assignName1 = "Assignment1" + Utility.dynamicNameAppender();

		baseClass.stepInfo("RPMXCON-67537");
		baseClass.stepInfo(
				"Verify that user can save/sort single or multiple coding form successfully while creating new assignment"
						+ " in a user created assignment group with cascade setting ON for new project");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignment.navigateToAssignmentsPage();
		assignment.createCascadeNonCascadeAssgnGroup(assignMentGRP, "Yes");
		assignment.selectAssignmentGroup(assignMentGRP);
		List<String> sorting = assignment.createAssignmentFromAssgnGroupByChangeSortingSequenceOfCF(assignName,
				Input.codeFormName);
		String ExpMultSorting = sorting.toString().replace(" (Set as Default)", "");
		sessionSearch.navigateToSessionSearchPageURL();
		driver.waitForPageToBeReady();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assignName);

		assignment.editAssignmentInAssignGroup(assignMentGRP, assignName);
		assignment.add2ReviewerAndDistribute(Input.rev1userName,Input.rmu1userName);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.waitForElement(dash.getSelectAssignmentFromDashborad(assignName));
		dash.getSelectAssignmentFromDashborad(assignName).waitAndClick(3);
		driver.waitForPageToBeReady();
		baseClass.waitForElementCollection(docView.listOfCodingFormInDocViewDropDown());
		String actMultiSorting = baseClass.availableListofElements(docView.listOfCodingFormInDocViewDropDown())
				.toString();
		asserts.assertEquals(ExpMultSorting, actMultiSorting);
		asserts.assertAll();
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignment.navigateToAssignmentsPage();
		assignment.createCascadeNonCascadeAssgnGroup(assignMentGRP1, "Yes");
		assignment.selectAssignmentGroup(assignMentGRP1);
		assignment.createAssignmentFromAssgnGroup(assignName1, Input.codeFormName);

		sessionSearch.navigateToSessionSearchPageURL();
		driver.waitForPageToBeReady();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assignName1);

		assignment.editAssignmentInAssignGroup(assignMentGRP1, assignName1);
		List<String> singleSorting = assignment.editExistingCodingForm(codeForm, Input.codeFormName, true);
		String ExpSingSorting = singleSorting.toString().replace(" (Set as Default)", "");
		assignment.add2ReviewerAndDistribute(Input.rev1userName,Input.rmu1userName);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.waitForElement(dash.getSelectAssignmentFromDashborad(assignName1));
		dash.getSelectAssignmentFromDashborad(assignName1).waitAndClick(3);
		driver.waitForPageToBeReady();
		baseClass.waitForElementCollection(docView.listOfCodingFormInDocViewDropDown());
		String actSingSorting = baseClass.availableListofElements(docView.listOfCodingFormInDocViewDropDown())
				.toString();
		System.out.println(actSingSorting);
		System.out.println(ExpSingSorting);
		asserts.assertEquals(ExpSingSorting, actSingSorting);
		asserts.assertAll();

		baseClass.passedStep(
				"Verified that user can save/sort single or multiple coding form successfully while creating "
						+ "new assignment in a user created assignment group with cascade setting ON for new project");
		loginPage.logout();

	}

	/**
	 * @author sowndarya
	 * @Modified by: N/A
	 * @Description : User able to change Set as Default coding form in Add/Remove
	 *              Coding Forms from Assignment using radio button in Set
	 *              as.[RPMXCON-65551]
	 */
	@Test(description = "RPMXCON-65551", enabled = true, groups = { "regression" })
	public void verifySeDiffCodingFormToAssignment() throws InterruptedException {

		String assignmentName = "assignment" + Utility.dynamicNameAppender();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-65551");
		baseClass.stepInfo(
				"User able to change Set as Default coding form in Add/Remove Coding Forms from Assignment using radio button in Set as");

		String cfName = "C" + Utility.dynamicNameAppender();

		// create a new coding form
		CodingForm form = new CodingForm(driver);
		form.navigateToCodingFormPage();
		form.createCodingform(cfName);

		// creating Assignment
		assignment.navigateToAssignmentsPage();
		assignment.createAssignment(assignmentName, Input.codingFormName);
		// performing bulk Assign

		// edit the assignment with new coding form as default.
		assignment.editAssignmentUsingPaginationConcept(assignmentName);
		assignment.EditCodingformInSelectedAssignment(cfName);
		loginPage.logout();

	}

	/**
	 * @author N/A
	 * @Modified by: N/A
	 * @Description : Verify In Add/Edit Assignment toggle 'Allow users to save
	 *              without completing' is enabled,In Doc view context of an
	 *              Assignment we can save document without completing
	 *              it.[RPMXCON-65575]
	 */
	@Test(description = "RPMXCON-65575", enabled = true, groups = { "regression" })
	public void verifyToggleAllowUserSaveWithoutComplete() throws Exception {
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);
		Dashboard dash = new Dashboard(driver);
		DocViewPage docView = new DocViewPage(driver);

		String codingForm = Input.codingFormName;
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();

		baseClass.stepInfo("RPMXCON-65575");
		baseClass.stepInfo(
				"To Verify In Add/Edit Assignment toggle 'Allow users to save without completing' is enabled, "
						+ "In Doc view context of an Assignment we can save document without completing it");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in As : " + Input.rmu1userName);

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		driver.waitForPageToBeReady();
		assignmentsPage.createAssignmentWithAllowUserToSave(assignmentName, codingForm);

		assignmentsPage.editAssignmentUsingPaginationConcept(assignmentName);
		driver.waitForPageToBeReady();
		assignmentsPage.addReviewerAndDistributeDocs(Input.rev1userName);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in As : " + Input.rev1userName);
		baseClass.waitForElement(dash.getSelectAssignmentFromDashborad(assignmentName));
		dash.getSelectAssignmentFromDashborad(assignmentName).waitAndClick(5);
		driver.waitForPageToBeReady();

		baseClass.waitForElement(docView.getSaveDoc());
		if (docView.getSaveDoc().isElementAvailable(5)) {
			baseClass.passedStep("Save Coding Button Displaying As Expected");
		} else {
			baseClass.failedStep("Save Coding Button Displaying Not As Expected");
		}
		baseClass.passedStep(
				"Verified - In Add/Edit Assignment toggle 'Allow users to save without completing' is enabled,"
						+ " In Doc view context of an Assignment we can save document without completing it");
		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani ModifyDate:28/11/2022 RPMXCON-54216
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Draw from pool - Verify the display of 'Draw from Pool' Action
	 *              when 'Draw From Pool' option is enabled in Assignment with draw
	 *              from pool (i.e 20) is equal to the total docs assigned (i.e.
	 *              20).
	 */
	@Test(description = "RPMXCON-54216", enabled = true, groups = { "regression" })
	public void verifyDrawFromPoolIsNotDisplayedInReviewerPage() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-54216");
		baseClass.stepInfo(
				"Draw from pool - Verify the display of 'Draw from Pool' Action when 'Draw From Pool' option is enabled in Assignment with draw from pool (i.e 20) is equal to the total docs assigned (i.e. 20).");

		AssignmentsPage agnmt = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + Input.rmu1userName + "");

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();

		agnmt.FinalizeAssignmentAfterBulkAssign();
		agnmt.createAssignment_fromAssignUnassignPopup(assignmentName, Input.codeFormName);
		driver.waitForPageToBeReady();
		// Draw pool Toggle Disable
		agnmt.toggleEnableOrDisableOfAssignPage(false, true, agnmt.getAssgnGrp_Create_DrawPooltoggle(),
				"Draw From Pool", false);
		driver.scrollPageToTop();
		baseClass.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		baseClass.stepInfo("Created a Assignment " + assignmentName + " with draw pool toggle disabled");

		// Edit Assignment
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		// Draw pool Toggle Enable and making draw limit as 20
		agnmt.toggleEnableOrDisableOfAssignPage(true, false, agnmt.getAssgnGrp_Create_DrawPooltoggle(),
				"Draw From Pool", false);
		agnmt.getAssgnGrp_Create_DrawPoolCount().SendKeys("20");
		// distributing docs
		agnmt.distributeTheGivenDocCountToReviewer("20",Input.rev1userName);
		driver.scrollPageToTop();
		baseClass.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(5);

		this.driver.getWebDriver().get(Input.url + "/Dashboard/Dashboard");
		if (!agnmt.getAssignmentsDrawPoolInreviewerPg(assignmentName).isElementAvailable(3)) {
			baseClass.passedStep("Draw pool link is Not displayed");
		} else {
			baseClass.failedStep("Draw pool link is displayed");
		}
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:28/11/2022 RPMXCON-54217
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Draw from pool - Verify the display of 'Draw from Pool' Action
	 *              when 'Draw From Pool' option is enabled in Assignment with draw
	 *              from pool (i.e 20) is less than the total docs assigned (i.e.
	 *              40)
	 */
	@Test(description = "RPMXCON-54217", enabled = true, groups = { "regression" })
	public void verifyDrawFromPoolIsNotDisplayedInReviewerPageFromAsssignPage()
			throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-54217");
		baseClass.stepInfo(
				"Draw from pool - Verify the display of 'Draw from Pool' Action when 'Draw From Pool' option is enabled in Assignment with draw from pool (i.e 20) is less than the total docs assigned (i.e. 40)");

		AssignmentsPage agnmt = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + Input.rmu1userName + "");

		agnmt.navigateToAssignmentsPage();
		driver.waitForPageToBeReady();
		agnmt.getAssignmentActionDropdown().waitAndClick(10);
		agnmt.getAssignmentAction_NewAssignment().waitAndClick(10);
		agnmt.createAssignment_fromAssignUnassignPopup(assignmentName, Input.codeFormName);
		driver.waitForPageToBeReady();
		// Draw pool Toggle Disable
		agnmt.toggleEnableOrDisableOfAssignPage(false, true, agnmt.getAssgnGrp_Create_DrawPooltoggle(),
				"Draw From Pool", false);
		driver.scrollPageToTop();
		baseClass.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		baseClass.stepInfo("Created a Assignment " + assignmentName + " with draw pool toggle disabled");

		// Edit Assignment
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		// Draw pool Toggle Enable and making draw limit as 20
		agnmt.toggleEnableOrDisableOfAssignPage(true, false, agnmt.getAssgnGrp_Create_DrawPooltoggle(),
				"Draw From Pool", false);
		agnmt.getAssgnGrp_Create_DrawPoolCount().SendKeys("20");
		// distributing docs
		agnmt.distributeTheGivenDocCountToReviewer("20",Input.rev1userName);
		driver.scrollPageToTop();
		baseClass.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(5);

		this.driver.getWebDriver().get(Input.url + "/Dashboard/Dashboard");
		if (!agnmt.getAssignmentsDrawPoolInreviewerPg(assignmentName).isElementAvailable(3)) {
			baseClass.passedStep("Draw pool link is Not displayed");
		} else {
			baseClass.failedStep("Draw pool link is displayed");
		}
		loginPage.logout();
	}

	/**
	 * @author
	 * @Date: :N/A
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @description Validate editing Keywords associated to security group by PAU
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54757", enabled = true, groups = { "regression" })
	public void verifyEditKeyWordAssociateToSg() throws Exception {
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();
		String keywordname = "AAkeyword" + Utility.dynamicNameAppender();
		String Modifywordkeyname = "AAkeyword" + Utility.dynamicNameAppender();
		String color = "Blue";
		KeywordPage keyword = new KeywordPage(driver);
		baseClass.stepInfo("Validate editing Keywords associated to security group by PAU");
		baseClass.stepInfo("Test case Id: RPMXCON-54757");

		// Login as PAU
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Login as PAU");
		keyword.navigateToKeywordPage();
		keyword.addKeywordWithSG(Input.securityGroup, keywordname, color);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as RMU" + Input.rmu1userName);
		driver.waitForPageToBeReady();
		agnmt.createAssignment(assignmentName, Input.codingFormName);
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		driver.waitForPageToBeReady();
		agnmt.verifyKeywordPopUp();
		baseClass.ValidateElement_Presence(agnmt.getAssgn_Keywordspopup(), keywordname);
		baseClass.stepInfo(keywordname + " created keyword is present in assignmant page as expected");
		agnmt.getKeywordPopUpCancelBtn().waitAndClick(5);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Login as PAU" + Input.pa1userName);
		keyword.navigateToKeywordPage();
		keyword.editExistigKeyword(keywordname, Modifywordkeyname);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as RMU" + Input.rmu1userName);
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		driver.waitForPageToBeReady();
		agnmt.verifyKeywordPopUp();
		baseClass.ValidateElement_Presence(agnmt.getAssgn_Keywordspopup(), Modifywordkeyname);
		baseClass.stepInfo(Modifywordkeyname + " modified keyword is present in assignmant page as expected");
		agnmt.getKeywordPopUpCancelBtn().waitAndClick(5);
		keyword.deleteKeywordByName(Modifywordkeyname);
		loginPage.logout();

	}

	/**
	 * @author
	 * @Date: :N/A
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @description Validate editing Keywords created within security group by RMU
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54758", enabled = true, groups = { "regression" })
	public void verifyEditKeyWordSgByRmu() throws Exception {
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();
		String keywordname = "AAkeyword" + Utility.dynamicNameAppender();
		String Modifywordkeyname = "AAModifiedkeyword" + Utility.dynamicNameAppender();
		String color = "Blue";
		KeywordPage keyword = new KeywordPage(driver);
		baseClass.stepInfo("Validate editing Keywords created within security group by RMU");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as RMU " + Input.rmu1userName);
		keyword.navigateToKeywordPage();
		keyword.addKeywordWithSG(Input.securityGroup, keywordname, color);
		driver.waitForPageToBeReady();
		agnmt.createAssignment(assignmentName, Input.codingFormName);
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		driver.waitForPageToBeReady();
		agnmt.verifyKeywordPopUp();
		baseClass.ValidateElement_Presence(agnmt.getAssgn_Keywordspopup(), keywordname);
		baseClass.stepInfo(keywordname + " created keyword is present in assignmant page as expected");
		agnmt.getKeywordPopUpCancelBtn().waitAndClick(5);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		keyword.navigateToKeywordPage();
		keyword.editExistigKeyword(keywordname, Modifywordkeyname);
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		driver.waitForPageToBeReady();
		agnmt.verifyKeywordPopUp();
		baseClass.ValidateElement_Presence(agnmt.getAssgn_Keywordspopup(), Modifywordkeyname);
		baseClass.stepInfo(Modifywordkeyname + " modified keyword is present in assignmant page as expected");
		agnmt.getKeywordPopUpCancelBtn().waitAndClick(5);
		keyword.deleteKeywordByName(Modifywordkeyname);
		loginPage.logout();

	}

	/**
	 * @author
	 * @Date: :N/A
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @description Delete Keyword - Validate deleting Keywords associated to
	 *              security group
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54759", enabled = true, groups = { "regression" })
	public void verifyDeletingKeyWordAssociateToSg() throws Exception {
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();
		String keywordName = "AAkeyword" + Utility.dynamicNameAppender();
		String color = "Blue";
		KeywordPage keyword = new KeywordPage(driver);
		baseClass.stepInfo("Delete Keyword - Validate deleting Keywords associated to security group");
		baseClass.stepInfo("Test case Id: RPMXCON-54759");

		// Login as PAU
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PAU as with " + Input.pa1userName + "");
		keyword.navigateToKeywordPage();
		keyword.addKeywordWithSG(Input.securityGroup, keywordName, color);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");
		baseClass.stepInfo("Validate editing Keywords associated to security group by PAU");
		driver.waitForPageToBeReady();
		agnmt.createAssignment(assignmentName, Input.codingFormName);
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		driver.waitForPageToBeReady();
		agnmt.verifyKeywordPopUp();
		baseClass.ValidateElement_Presence(agnmt.getAssgn_Keywordspopup(), keywordName);
		baseClass.stepInfo(keywordName + " created keyword is present in assignmant page as expected");
		agnmt.getKeywordPopUpCancelBtn().waitAndClick(5);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Login as PAU");
		keyword.navigateToKeywordPage();
		keyword.deleteKeywordByName(keywordName);
		baseClass.stepInfo(keywordName + "Key word deleted sucessfully.");
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Login as " + Input.rmu1userName);
		List<String> KeywordsListInKEywordPageAfterdelete = new ArrayList<String>();
		KeywordsListInKEywordPageAfterdelete = baseClass.getAvailableListofElements(keyword.getKeywordsList());
		baseClass.stepInfo("Checking for the Keyword Abscence After deleting the keyword as expected.");
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		agnmt.verifyKeywordsBeforeAndAfterDelete(keywordName, KeywordsListInKEywordPageAfterdelete, false);
		agnmt.getKeywordPopUpCancelBtn().waitAndClick(5);
		loginPage.logout();

	}

	/**
	 * @author
	 * @Date: :N/A
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @description Validate removing and adding new keywords by PAU or RMU
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54760", enabled = true, groups = { "regression" })
	public void verifyRemovingAndAddingNewKeyWords() throws Exception {
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();
		String keywordNamePA = "AAkeyword" + Utility.dynamicNameAppender();
		String keywordName1PA = "AAkeyword" + Utility.dynamicNameAppender();
		String ModifiedkeywordNamePA = "AAModifiedkeyword" + Utility.dynamicNameAppender();
		String keywordNameRMU = "AAkeyword" + Utility.dynamicNameAppender();
		String keywordName1RMU = "AAkeyword" + Utility.dynamicNameAppender();
		String ModifiedkeywordNameRMU = "AAModifiedkeyword" + Utility.dynamicNameAppender();
		String color = "Blue";
		KeywordPage keyword = new KeywordPage(driver);
		baseClass.stepInfo("Validate removing and adding new keywords by PAU or RMU");

		// Login as PAU
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PAU as with " + Input.pa1userName + "");
		baseClass.stepInfo("Test case Id: RPMXCON-54760");
		keyword.navigateToKeywordPage();
		keyword.addKeywordWithSG(Input.securityGroup, keywordNamePA, color);
		baseClass.stepInfo(keywordNamePA + "Keyword added");
		keyword.addKeywordWithSG(Input.securityGroup, keywordName1PA, color);
		keyword.editExistigKeyword(keywordName1PA, ModifiedkeywordNamePA);
		baseClass.stepInfo(ModifiedkeywordNamePA + "Modified Keyword added");
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Validate editing Keywords created within security group by RMU");
		keyword.navigateToKeywordPage();
		keyword.addKeywordWithSG(Input.securityGroup, keywordNameRMU, color);
		baseClass.stepInfo(keywordNameRMU + "Keyword added");
		keyword.addKeywordWithSG(Input.securityGroup, keywordName1RMU, color);
		keyword.editExistigKeyword(keywordName1RMU, ModifiedkeywordNameRMU);
		baseClass.stepInfo(ModifiedkeywordNameRMU + "Modified Keyword added");
		driver.waitForPageToBeReady();
		agnmt.createAssignment(assignmentName, Input.codingFormName);
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		driver.waitForPageToBeReady();
		agnmt.verifyKeywordPopUp();
		driver.waitForPageToBeReady();
		baseClass.ValidateElement_Presence(agnmt.getAssgn_Keywordspopup(), keywordNameRMU);
		baseClass.stepInfo(keywordNameRMU + " Added Rmu keyword is present in assignmant page as expected");
		driver.waitForPageToBeReady();
		baseClass.ValidateElement_Presence(agnmt.getAssgn_Keywordspopup(), keywordNamePA);
		baseClass.stepInfo(keywordNameRMU + " Added PA keyword is present in assignmant page as expected");
		baseClass.ValidateElement_Presence(agnmt.getAssgn_Keywordspopup(), ModifiedkeywordNamePA);
		baseClass.stepInfo(
				ModifiedkeywordNamePA + " Added Modified Rmu keyword is present in assignmant page as expected");
		driver.waitForPageToBeReady();
		baseClass.ValidateElement_Presence(agnmt.getAssgn_Keywordspopup(), ModifiedkeywordNamePA);
		baseClass.stepInfo(
				ModifiedkeywordNamePA + " Added Modified PA keyword is present in assignmant page as expected");
		driver.waitForPageToBeReady();
		keyword.deleteKeywordByName(keywordNameRMU);
		keyword.deleteKeywordByName(keywordNamePA);
	}

	/**
	 * @author
	 * @throws Exception
	 * @Date: 08/22/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Validate updating existing assignment with Sort by metadata
	 *              field. RPMXCON-54503
	 */
	@Test(description = "RPMXCON-54503", enabled = true, groups = { "regression" })
	public void validateUpdatingExistingAssignmentWithSortByMetadata() throws InterruptedException, AWTException {
		List<String> metaDataList = new ArrayList<String>();
		String assgnName = "Assgn" + Utility.dynamicNameAppender();
		MiniDocListPage miniDocList = new MiniDocListPage(driver);

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id:RPMXCON-54503");
		baseClass.stepInfo("Validate updating existing assignment with Sort by metadata field.");

		// create assignment
		baseClass.stepInfo("creating assignment");
		assignment.createAssignment(assgnName, Input.codeFormName);

		// edit assignment sort by metaData
		baseClass.stepInfo("Editing Existing Assignment sort by metaData");
		assignment.editAssignmentUsingPaginationConcept(assgnName);
		assignment.Assgnwithdocumentsequence(Input.metaDataName, Input.sortType);

		// bulk assign documents to assignment
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assgnName);

		// viewing the assignment in DocView page
		assignment.selectAssignmentToViewinDocviewThreadMap(assgnName);

		// configuring the miniDocList with assignment metaData
		baseClass.waitForElement(miniDocList.getGearIcon());
		miniDocList.clickingGearIcon();
		miniDocList.removingAllExistingFieldsAndAddingNewField(Input.metaDataName);

		// verifying that Documents are sorted based on the Selected metadata.
		metaDataList = baseClass.availableListofElements(miniDocList.getListofDocIDinCW());
		baseClass.verifyOriginalSortOrder(metaDataList, metaDataList, "Ascending", true);
		baseClass.stepInfo("verified that Documents are sorted based on the Selected metadata.");

		// delete assignment
		assignment.deleteAssgnmntUsingPagination(assgnName);

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @throws Exception
	 * @Date: 08/22/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :Validate changing metadata for Sort by metadata field of an
	 *              existing assignment. RPMXCON-54504
	 */
	@Test(description = "RPMXCON-54504", enabled = true, groups = { "regression" })
	public void validateChangingMetadataForSortByMetadataFieldForExistingAssignment()
			throws InterruptedException, AWTException {
		List<String> metaDataList = new ArrayList<String>();
		String assgnName = "Assgn" + Utility.dynamicNameAppender();
		MiniDocListPage miniDocList = new MiniDocListPage(driver);

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id:RPMXCON-54504");
		baseClass.stepInfo("Validate changing metadata for Sort by metadata field of an existing assignment.");

		// create assignment with sort by metaData
		baseClass.stepInfo("create assignment with sort by metaData");
		assignment.createAssignment_withoutSave(assgnName, Input.codeFormName);
		assignment.Assgnwithdocumentsequence(Input.sortDataBy, Input.sortType);

		// edit assignment sort by metaData
		baseClass.stepInfo("Editing Existing Assignment sort by metaData");
		assignment.editAssignmentUsingPaginationConcept(assgnName);
		assignment.Assgnwithdocumentsequence(Input.metaDataName, Input.sortType);

		// bulk assign documents to assignment
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assgnName);

		// viewing the assignment in DocView page
		assignment.selectAssignmentToViewinDocviewThreadMap(assgnName);

		// configuring the miniDocList with assignment metaData
		baseClass.waitForElement(miniDocList.getGearIcon());
		miniDocList.clickingGearIcon();
		miniDocList.removingAllExistingFieldsAndAddingNewField(Input.metaDataName);

		// verifying that Documents are sorted based on the updated metadata.
		metaDataList = baseClass.availableListofElements(miniDocList.getListofDocIDinCW());
		baseClass.verifyOriginalSortOrder(metaDataList, metaDataList, "Ascending", true);
		baseClass.passedStep("verified that Documents are sorted based on the updated metadata.");

		// delete assignment
		assignment.deleteAssgnmntUsingPagination(assgnName);

		// logOut
		loginPage.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * 
	 * @description:RPMXCON-53698 To verify that in Redistribute Documents pop up
	 *                            only those Reviewers are displayed which are
	 *                            associated to the Assignments
	 * @description:RPMXCON-53697 To verify that in Redistribute Documents screen
	 *                            selected Reviewer is not displayed in the
	 *                            Reviewers list
	 * 
	 */
	@Test(description = "RPMXCON-53697,RPMXCON-53698", enabled = true, groups = { "regression" })
	public void verifyThedistributedUserNotInRedistributeList()
			throws InterruptedException, ParseException, IOException {
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();
		baseClass.stepInfo("To verify that in Redistribute Documents pop up only those Reviewers are"
				+ " displayed which are associated to the Assignments");
		baseClass.stepInfo("To verify that in Redistribute Documents screen "
				+ "selected Reviewer is not displayed in the Reviewers list");
		baseClass.stepInfo("Test case Id:RPMXCON-53697,RPMXCON-536978");
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		baseClass.stepInfo("**Assignment creation -bulk assing docs**");
		assignment.createAssignment(assignmentName, Input.codeFormName);
		baseClass.stepInfo("Created assignment with name" + assignmentName);
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assignmentName);
		baseClass.stepInfo("Created a assignment " + assignmentName);

		baseClass.stepInfo("**Adding 2 reviewers and distributing docs to one reviewer alone**");
		assignment.editAssignmentUsingPaginationConcept(assignmentName);
		String Distributeduser = assignment.addMultipleReviewersAndDistributeToOnereviewer();
		baseClass.stepInfo("**selecting docs assigned reviewers to redistribute docs to other assignment "
				+ "associated reviewer**");
		String redistributeUser = assignment.VerifyUserNotInListAfterRedistributedDocs();

		baseClass.stepInfo("**Validation for test case RPMXCON-53697**");
		if (Distributeduser != redistributeUser) {
			baseClass.passedStep("Selected distributed reviewer is not displayed in redistribute document pop up.");
		} else {
			baseClass.failedStep("selected Distributed Reviewer user appeared in redistribute document list.");
		}
		baseClass.stepInfo("**Validation for test case RPMXCON-53698**");
		System.out.println(redistributeUser);
		System.out.println(Input.rev1userName);
		if (redistributeUser.contains(Input.rev1userName)) {
			baseClass.passedStep("Redistribute Documents pop up displayed only those Reviewers"
					+ " which are associated to the Assignments");
		} else {
			baseClass.failedStep("Redistribute Documents pop up not displayed only those Reviewers"
					+ " which are associated to the Assignments");
		}
		loginPage.logout();
	}

	/**
	 * @author
	 * @Date: 25/8/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : To verify that Total Documents displayed on Distribute
	 *              Documents are correct if documents are unassigned to the
	 *              one/multiple Assignments.RPMXCON-53646
	 */

	@Test(description = "RPMXCON-53646", enabled = true, groups = { "regression" })
	public void verifyTotalDocumentsDisplayedOnDistributeDocumentsAreCorrectWhenDocumentsUnAssignedForMultipleAssignments()
			throws InterruptedException {

		String assignmentName01 = "Assgn" + Utility.dynamicNameAppender();
		String assignmentName02 = "Assgn" + Utility.dynamicNameAppender();
		int ExpectedTotalDocCountInAssign = 0;
		List<String> listOfAssignments = new ArrayList<String>();
		listOfAssignments.add(assignmentName01);
		listOfAssignments.add(assignmentName02);
		Map<String, Integer> pairOfAssignmentsAndExpectedTotalDocCountInAssign = new HashMap<String, Integer>();
		pairOfAssignmentsAndExpectedTotalDocCountInAssign.put(assignmentName01, ExpectedTotalDocCountInAssign);
		pairOfAssignmentsAndExpectedTotalDocCountInAssign.put(assignmentName02, ExpectedTotalDocCountInAssign);
		Map<String, Integer> pairOfAssignmentsAndexptedDocCountInDistrTab = pairOfAssignmentsAndExpectedTotalDocCountInAssign;
		String[][] pairOfAssignmentNameAndcodingForm = { { assignmentName01, Input.codeFormName },
				{ assignmentName02, Input.codeFormName } };

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id:RPMXCON-53646");
		baseClass.stepInfo(
				"To verify that Total Documents displayed on Distribute Documents are correct if documents are unassigned to the one/multiple Assignments.");

		// create Assignments using Bulk Assign
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignWithMultipleNewAssignmentWithPersistantHit(pairOfAssignmentNameAndcodingForm);

		// UnAssigning Documents from Assignments
		sessionSearch.bulkAssign();
		sessionSearch.UnAssignMultipleExistingAssignments(listOfAssignments);
		driver.waitForPageToBeReady();

		// Verify that "Assign/Unassign Documents" pop up is closed and displays Search
		// Page
		baseClass.printResutInReport(baseClass.ValidateElement_PresenceReturn(sessionSearch.getNewSearch()),
				"Landed back in Session search page", "Failed in landing page", "Pass");
		baseClass.printResutInReport(baseClass.ValidateElement_PresenceReturn(sessionSearch.getBulkAssignPopUpClosed()),
				"Verified that \"Assign/Unassign Documents\" pop up is closed and displays Search Page.",
				"Failed in landing page", "Pass");

		// verifying Assignment Total Document Count in Manage Assignment page.
		assignment.navigateToAssignmentsPage();
		assignment.validateTotalDocumentCountInManageAssignmentPageForMultipleAssignments(
				pairOfAssignmentsAndExpectedTotalDocCountInAssign);

		// Verifying that "Distribute Documents" tab is displayed and Total Documents
		// are displayed as "0"
		assignment.verifyTotalCountOfDocsInMultipleAssignmentsInDistributeDocumentsTab(
				pairOfAssignmentsAndexptedDocCountInDistrTab);

		// delete Assignment
		assignment.deleteMultipleAssgnmntUsingPagination(listOfAssignments);

		// logOut
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: 30/08/2022 TestCase Id:RPMXCON-53759 Description :To
	 * verify that copied Assignment must be displayed on RMU Dashboard and RU
	 * Dashboard
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53759", enabled = true, groups = { "regression" })
	public void verifyCopiedAssignmentAvailability() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-53759");
		baseClass.stepInfo("verify that copied Assignment must be displayed on RMU Dashboard and RU Dashboard");
		String assignmentName = "assignment1" + Utility.dynamicNameAppender();
		String assignGroup = "assigGroup" + Utility.dynamicNameAppender();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Create new assignment");
		assignment.navigateToAssignmentsPage();
		assignment.createCascadeNonCascadeAssgnGroup(assignGroup, "No");
		assignment.selectAssignmentGroup(assignGroup);
		assignment.createAssignmentFromAssgnGroup(assignmentName, Input.codingFormName);
		baseClass.stepInfo("Copy assignment");
		assignment.selectAssignmentGroup(assignGroup);
		assignment.getSelectAssignment(assignmentName).waitAndClick(3);
		driver.scrollPageToTop();
		assignment.getAssignmentActionDropdown().waitAndClick(5);
		baseClass.waitForElement(assignment.getAssignmentActionDropdown());
		assignment.getAssignmentAction_CopyAssignment().waitAndClick(5);
		baseClass.getYesBtn().waitAndClick(5);
		baseClass.VerifySuccessMessage("Record copied successfully");
		String copiedAssignName = assignment.getSelectCopyAssignment().getText().trim();
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkAssignExisting(copiedAssignName);
		assignment.selectAssignmentGroup(assignGroup);
		assignment.getSelectAssignment(copiedAssignName).waitAndClick(3);
		driver.scrollPageToTop();
		baseClass.waitForElement(assignment.getAssignmentActionDropdown());
		assignment.getAssignmentActionDropdown().waitAndClick(5);
		assignment.getAssignmentAction_EditAssignment().waitAndClick(5);
		assignment.add2ReviewerAndDistribute(Input.rev1userName,Input.rmu1userName);
		baseClass.stepInfo("verify copied assignment displayed in rmu dashboard");
		Dashboard dashBoard = new Dashboard(driver);
		dashBoard.navigateToDashboard();
		driver.waitForPageToBeReady();
		if (dashBoard.getSelectAssignmentFromDashborad(copiedAssignName).isElementAvailable(10)) {
			baseClass.passedStep("Copied assignment available in RMU dashboard");
		} else {
			baseClass.failedStep("Copied assignment not available");
		}
		loginPage.logout();
		baseClass.stepInfo("Login as rev and verify copied assignment availability");
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("verify copied assignment displayed in rev dashboard");
		if (assignment.getSelectAssignmentAsReviewer(copiedAssignName).isElementAvailable(10)) {
			baseClass.passedStep("Copied assignment available in REV dashboard");
		} else {
			baseClass.failedStep("Copied assignment not available");
		}
		loginPage.logout();

	}

	/**
	 * Author :Arunkumar date: 30/08/2022 TestCase Id:RPMXCON-53652 Description :To
	 * verify that in "Select Reviewers" section in Distribute Documents tab only
	 * those reviewers are displayed which are added to an assignment in Manage
	 * Reviewer tab.
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53652", enabled = true, groups = { "regression" })
	public void verifyAddedReviewersInDistributeTab() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-53652");
		baseClass.stepInfo("verify that added reviewers displayed in distribute tab");
		String assignmentName = "assignment1" + Utility.dynamicNameAppender();
		String[] users = { Input.rmu1userName, Input.rev1userName };

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignment.createAssignment(assignmentName, Input.codeFormName);
		assignment.editAssignmentUsingPaginationConcept(assignmentName);
		baseClass.stepInfo("add reviewers");
		assignment.assignReviewers(users);
		baseClass.stepInfo("verify added users displayed in distribute documents tab");
		baseClass.waitForElement(assignment.getDistributeTab());
		assignment.getDistributeTab().waitAndClick(5);
		int availableUserCount = assignment.getDistributeReviewerCount().size();
		if (availableUserCount == users.length) {
			if (assignment.getSelectUserInDistributeTabsGeneral(Input.rmu1userName).isElementAvailable(5)
					&& assignment.getSelectUserInDistributeTabsGeneral(Input.rev1userName).isElementAvailable(5)) {
				baseClass.passedStep("Only the added reviewers are displayed in distribute tab");
			} else {
				baseClass.failedStep("added reviewers not displayed");
			}
		} else {
			baseClass.failedStep("added reviewers not displayed correctly in distribute tab");
		}
		loginPage.logout();
	}

	/**
	 * @author
	 * @Date: 25/8/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : To verify that if Documents are assigned to Reviewer and some
	 *              documents are Unassigned then \"Unassigned Documents\" is
	 *              displays correct count. RPMXCON-53651
	 */
	@Test(description = "RPMXCON-53651", enabled = true, groups = { "regression" })
	public void verifyDocsAssignedToReviewerAndSomeDocsUnassignedThenUnassignDocsCountDisplayedCorrectly()
			throws InterruptedException {

		String assignmentName = "Assgn" + Utility.dynamicNameAppender();
		List<String> listOfReviewers = new ArrayList<String>(Arrays.asList("REV", "RMU"));
		List<String> listOfUsers = new ArrayList<String>(Arrays.asList(Input.rev1userName, Input.rmu1userName));

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id:RPMXCON-53651");
		baseClass.stepInfo(
				"To verify that if Documents are assigned to Reviewer and some documents are Unassigned then \"Unassigned Documents\" is displays correct count.");

		// create assignment
		assignment.createAssignment(assignmentName, Input.codeFormName);

		// adding Reviewers to Assignment
		assignment.editAssignment(assignmentName);
		assignment.addReviewers(listOfReviewers,listOfUsers);

		// Bulk Assigning the pureHit Count to Assignment
		int CountOfDocsAssignedToAssignment = sessionSearch.basicContentSearch(Input.searchString2);
		String countOfDocs = Integer.toString(CountOfDocsAssignedToAssignment);
		sessionSearch.bulkAssignExisting(assignmentName);

		// navigating to Distribute Documents Tab and verifying the Total count of docs
		// in Assignment
		assignment.editAssignment(assignmentName);
		baseClass.waitForElement(assignment.getDistributeTab());
		assignment.getDistributeTab().waitAndClick(5);
		assignment.verifyValuesFromDistributionTab(countOfDocs, assignment.getEditAggn_Distribute_Totaldoc());
		baseClass.passedStep("Distribute Documents Tab is displayed and Total Documents reflects the expected count : "
				+ countOfDocs);

		// Distributing the modified Doc Counts to Reviewers
		int[] DocCountDistrRevAndremUnAssignDocsCountAndDistrCountForEachRev = assignment
				.evenlyDistributedDocCountToReviewers(CountOfDocsAssignedToAssignment, listOfReviewers.size(), 1);
		String CountOfDocsAssignToReviewers = Integer
				.toString(DocCountDistrRevAndremUnAssignDocsCountAndDistrCountForEachRev[0]);
		assignment.distributeGivenDocCountToReviewers(CountOfDocsAssignToReviewers);

		// verifying the Unassign Documents in Distribute Documents Tab is displayed
		// with the remaining number of unassigned documents
		driver.Navigate().refresh();
		baseClass.waitForElement(assignment.getDistributeTab());
		assignment.getDistributeTab().waitAndClick(5);
		String unAssignDocsCount = Integer.toString(DocCountDistrRevAndremUnAssignDocsCountAndDistrCountForEachRev[1]);
		assignment.verifyValuesFromDistributionTab(unAssignDocsCount, assignment.getEditAggn_Distribute_Unassgndoc());
		baseClass.passedStep(
				"verified that Unassign Documents in Distribute Documents Tab is displayed with the remaining number of unassigned documents : "
						+ unAssignDocsCount);

		// verifying the Manage Reviewers tab is displayed and the "Distributed to User"
		// column reflects the expected counts for each reviewer
		baseClass.waitForElement(assignment.getAssignment_ManageReviewersTab());
		assignment.getAssignment_ManageReviewersTab().waitAndClick(5);
		String ExpectedDistributedCount = Integer
				.toString(DocCountDistrRevAndremUnAssignDocsCountAndDistrCountForEachRev[2]);
		assignment.verifyCountMatches(Input.rev1userName, 0, ExpectedDistributedCount, true, false, false, false);
		assignment.verifyCountMatches(Input.rmu1userName, 0, ExpectedDistributedCount, true, false, false, false);
		baseClass.passedStep(
				"verified that Manage Reviewers tab is displayed and the \"Distributed to User\" column reflects the expected counts for each reviewer.");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Date: 02/09/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that when Keep Families Together is enabled, the draw
	 *              will keep the entire family together irrespective of the size of
	 *              the batch with assignment document presentation sequence with
	 *              metadata. RPMXCON-65429
	 */
	@Test(description = "RPMXCON-65429", enabled = true, groups = { "regression" })
	public void verifyFamiliesTogetherEnabledDrawKeepEntirefamilyTogetherIrrespectiveOfPresentationSequenceMetadata()
			throws InterruptedException {

		String assignmentName = "Assignment" + Utility.dynamicNameAppender();
		List<String> listOfReviewers = new ArrayList<String>(Arrays.asList("REV", "RMU"));
		List<String> listOfUsers = new ArrayList<String>(Arrays.asList(Input.rev1userName, Input.rmu1userName));
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id:RPMXCON-65429");
		baseClass.stepInfo(
				"Verify that when Keep Families Together is enabled, the draw will keep the entire family together irrespective of the size of the batch with assignment document presentation sequence with metadata.");

		// creating Assignment
		assignment.createAssignment_withoutSave(assignmentName, Input.codeFormName);
		// giving Limit for the Draw from pool has 20
		assignment.getAssgnGrp_Create_DrawPoolCount().SendKeys("20");
		// Keep Families together should be disabled
		assignment.toggleEnableOrDisableOfAssignPage(true, false, assignment.getAssgn_keepFamiliesTogetherToggle(),
				"Keep Families Together", false);
		// Keep Email Threads Together as enabled
		assignment.toggleEnableOrDisableOfAssignPage(true, false, assignment.getAssgn_keepEmailThreadTogetherToggle(),
				"keep Email Thread Together", false);
		// adding Sort by Metadata in the Document Presentation Sequence
		assignment.Assgnwithdocumentsequence(Input.metaDataName, Input.sortType);
		baseClass.stepInfo("adding Sort by Metadata in the Document Presentation Sequence ");

		// Bulk Assign docoments to assignment
		int CountOfDocsAssignedToAssignment1 = sessionSearch.basicContentSearch(Input.TallySearch);
		sessionSearch.bulkAssignExisting(assignmentName);
		// adding reviewers to assignment
		assignment.editAssignment(assignmentName);
		assignment.addReviewers(listOfReviewers,listOfUsers);
		baseClass.stepInfo("adding reviewers to assignment");
		int[] DocCountDistrRevAndremUnAssignDocsCountAndDistrCountForEachRev = assignment
				.evenlyDistributedDocCountToReviewers(CountOfDocsAssignedToAssignment1, listOfReviewers.size(), 1);
		String CountOfDocsAssignToReviewers = Integer
				.toString(DocCountDistrRevAndremUnAssignDocsCountAndDistrCountForEachRev[0]);
		System.out.println(CountOfDocsAssignToReviewers);
		// few documents should be distributed to reviewers
		assignment.distributeGivenDocCountToReviewers(CountOfDocsAssignToReviewers);

		// Bulk Assign docoments to assignment
		baseClass.selectproject();
		int CountOfDocsAssignedToAssignment2 = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkAssignExisting(assignmentName);

		// logOut
		loginPage.logout();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Click On Draw Link of the Assignment in DashBoard page
		baseClass.waitForElement(assignment.getAssignmentsDrawPoolInreviewerPg(assignmentName));
		Actions action=new Actions(driver.getWebDriver());
		action.moveToElement(assignment.getAssignmentsDrawPoolInreviewerPg(assignmentName).getWebElement()).click().build().perform();
//		assignment.getAssignmentsDrawPoolInreviewerPg(assignmentName).waitAndClick(5);

		// verify On click of Draw from pool' all documents from same thread should be
		// assigned to the reviewer together irrespective of the draw from pool size to
		// the reviewer
		driver.Navigate().refresh();
		int actualTotalDocsCountInReviewerDashboard = Integer.parseInt(
				assignment.getTotalDocsCountInReviewerDashboard(assignmentName).getText().split(" ")[1].trim());
		int expectedTotalDocsCountInReviewerDashboard = CountOfDocsAssignedToAssignment2 
				+ DocCountDistrRevAndremUnAssignDocsCountAndDistrCountForEachRev[2]
				+ DocCountDistrRevAndremUnAssignDocsCountAndDistrCountForEachRev[2];
		baseClass.digitCompareEquals(actualTotalDocsCountInReviewerDashboard, expectedTotalDocsCountInReviewerDashboard,
				"Actual Total Document Count In Reviewer Dashboard : '" + actualTotalDocsCountInReviewerDashboard
						+ "' match with Expected Total Document Count : '" + expectedTotalDocsCountInReviewerDashboard
						+ "'",
				"Actual Total Document Count doesn't match with Expected Total Document Count.");
		baseClass.passedStep(
				"verified that On click of 'Draw from pool' all documents from entire family documents should be assigned to the reviewer together irrespective of the draw from pool size.");

		// delete assignment
		assignment.deleteAssgnmntUsingPagination(assignmentName);

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Date: 02/09/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :Verify that when Email Threads Together is enabled, the draw
	 *              will keep the entire email thread together irrespective of the
	 *              batch with assignment document presentation sequence with
	 *              metadata. RPMXCON-65430
	 */
	@Test(description = "RPMXCON-65430", enabled = true, groups = { "regression" })
	public void verifyEmailTogetherEnabledDrawKeepEntireEmailThreadTogetherIrrespectiveOfPresentationSequenceMetadata()
			throws InterruptedException {

		String assignmentName = "Assignment" + Utility.dynamicNameAppender();
		List<String> listOfReviewers = new ArrayList<String>(Arrays.asList("REV", "RMU"));
		List<String> listOfUsers = new ArrayList<String>(Arrays.asList(Input.rev1userName, Input.rmu1userName));
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id:RPMXCON-65430");
		baseClass.stepInfo(
				"Verify that when Email Threads Together is enabled, the draw will keep the entire email thread together irrespective of the batch with assignment document presentation sequence with metadata.");

		// creating Assignment
		assignment.createAssignment_withoutSave(assignmentName, Input.codeFormName);
		// giving Limit for the Draw from pool has 20
		assignment.getAssgnGrp_Create_DrawPoolCount().SendKeys("20");
		// Keep Email Threads Together as enabled
		assignment.toggleEnableOrDisableOfAssignPage(true, false, assignment.getAssgn_keepEmailThreadTogetherToggle(),
				"keep Email Thread Together", false);
		// Keep Families together should be disabled
		assignment.toggleEnableOrDisableOfAssignPage(false, true, assignment.getAssgn_keepFamiliesTogetherToggle(),
				"Keep Families Together", false);
		// adding Sort by Metadata in the Document Presentation Sequence
		assignment.Assgnwithdocumentsequence(Input.metaDataName, Input.sortType);
		baseClass.stepInfo("adding Sort by Metadata in the Document Presentation Sequence ");

		// Bulk Assign docoments to assignment
		int CountOfDocsAssignedToAssignment1 = sessionSearch.basicContentSearch(Input.TallySearch);
		sessionSearch.bulkAssignExisting(assignmentName);
		// adding reviewers to assignment
		assignment.editAssignment(assignmentName);
		assignment.addReviewers(listOfReviewers,listOfUsers);
		baseClass.stepInfo("adding reviewers to assignment");
		int[] DocCountDistrRevAndremUnAssignDocsCountAndDistrCountForEachRev = assignment
				.evenlyDistributedDocCountToReviewers(CountOfDocsAssignedToAssignment1, listOfReviewers.size(), 1);
		String CountOfDocsAssignToReviewers = Integer
				.toString(DocCountDistrRevAndremUnAssignDocsCountAndDistrCountForEachRev[0]);
		// few documents should be distributed to reviewers
		assignment.distributeGivenDocCountToReviewers(CountOfDocsAssignToReviewers);

		// Bulk Assign docoments to assignment
		baseClass.selectproject();
		int CountOfDocsAssignedToAssignment2 = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkAssignExisting(assignmentName);

		// logOut
		loginPage.logout();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Click On Draw Link of the Assignment in DashBoard page
		baseClass.waitForElement(assignment.getAssignmentsDrawPoolInreviewerPg(assignmentName));
		assignment.getAssignmentsDrawPoolInreviewerPg(assignmentName).waitAndClick(5);

		// verify On click of Draw from pool' all documents from same thread should be
		// assigned to the reviewer together irrespective of the draw from pool size to
		// the reviewer
		driver.Navigate().refresh();
		baseClass.waitForElement(assignment.getTotalDocsCountInReviewerDashboard(assignmentName));
		int actualTotalDocsCountInReviewerDashboard = Integer.parseInt(
				assignment.getTotalDocsCountInReviewerDashboard(assignmentName).getText().split(" ")[1].trim());
		System.out.println(assignment.getTotalDocsCountInReviewerDashboard(assignmentName).getText().split(" ")[1]);
		int expectedTotalDocsCountInReviewerDashboard = CountOfDocsAssignedToAssignment2
				+ DocCountDistrRevAndremUnAssignDocsCountAndDistrCountForEachRev[2]
				+ DocCountDistrRevAndremUnAssignDocsCountAndDistrCountForEachRev[2];
		baseClass.digitCompareEquals(actualTotalDocsCountInReviewerDashboard, expectedTotalDocsCountInReviewerDashboard,
				"Actual Total Document Count In Reviewer Dashboard : '" + actualTotalDocsCountInReviewerDashboard
						+ "' match with Expected Total Document Count : '" + expectedTotalDocsCountInReviewerDashboard
						+ "'",
				"Actual Total Document Count doesn't match with Expected Total Document Count.");
		baseClass.passedStep(
				"verified that On click of Draw from pool' all documents from same thread should be assigned to the reviewer together irrespective of the draw from pool size to the reviewer");

		// delete assignment
		assignment.deleteAssgnmntUsingPagination(assignmentName);

		// logOut
		loginPage.logout();
	}

	/**
	 * @author krishna
	 * @throws InterruptedException
	 * @Description :To Verify that on selecting Parent object then all its child
	 *              object gets selected RPMXCON-53907
	 */

	@Test(description = "RPMXCON-53907", enabled = true, groups = { "regression" })
	public void verifyAllChildSelectOnceSlctPrntGrp() throws Exception {
		String assignmentName1 = "AR2Assignment" + Utility.dynamicNameAppender();
		String parentAssgnGrp = "Root";

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as :" + Input.rmu1userName);
		baseClass.stepInfo("Test case Id: RPMXCON-53907 Assignments");
		baseClass.stepInfo("To Verify that on selecting Parent object then all its child object gets selected");

		assignment.createAssignment(assignmentName1, Input.codeFormName);
		sessionSearch.basicContentSearch(Input.searchString1);
		baseClass.waitForElement(sessionSearch.getDocsMetYourCriteriaLabel());
		baseClass.dragAndDrop(sessionSearch.getDocsMetYourCriteriaLabel(), sessionSearch.getActionPad());
		driver.waitForPageToBeReady();
		sessionSearch.bulkAssign();

		// Select An Assignment in Assign/Unassign Popup
		baseClass.waitForElement(sessionSearch.getSelectAssignmentExisting(assignmentName1));
		sessionSearch.getSelectAssignmentExisting(assignmentName1).Click();

		// after clicking on assignments moving the cursor near to continue button
		sessionSearch.getContinueButton().ScrollTo();
		// Verifying Only Selected Assignment gets Selected
		baseClass.waitForElement(sessionSearch.getSelectedExistingAssignments());
		if (sessionSearch.getSelectedExistingAssignments().isElementAvailable(6)) {
			String assgn = sessionSearch.getSelectedExistingAssignments().getText();
			if (assgn.equalsIgnoreCase(assignmentName1)) {
				baseClass.passedStep("selected Assignment gets selected");
			} else {
				baseClass.failedStep("Not Selected Assignment Gets Selected");
			}
		} else {
			baseClass.failedStep("No Assignment Selected in Popup");
		}

		// DeSelect the Assignment from Assign/UnAssign PopUp
		baseClass.waitForElement(sessionSearch.getSelectAssignmentExisting(assignmentName1));
		sessionSearch.getSelectAssignmentExisting(assignmentName1).Click();

		// Verifying All its downline objects get selected (Assignment Group and
		// Assignment) Once Parent Grp Selected
		baseClass.waitForElement(sessionSearch.getSelectAssignmentExisting(parentAssgnGrp));
		sessionSearch.getSelectAssignmentExisting(parentAssgnGrp).Click();
		baseClass.waitForElementCollection(sessionSearch.getassign_ExistingAssignments());
		for (WebElement ele : sessionSearch.getassign_ExistingAssignments().FindWebElements()) {
			String status = ele.getAttribute("aria-selected");
			if (status.equalsIgnoreCase("false")) {
				baseClass.failedStep("Not All its downline objects get selected (Assignment Group and Assignment).");
			}
		}
		baseClass.stepInfo("All its downline objects get selected (Assignment Group and Assignment).");

		// Verifying All its downline objects get Deselected (Assignment Group and
		// Assignment) Once Parent Grp DeSelected
		baseClass.waitForElement(sessionSearch.getSelectAssignmentExisting(parentAssgnGrp));
		sessionSearch.getSelectAssignmentExisting(parentAssgnGrp).Click();
		baseClass.waitForElementCollection(sessionSearch.getassign_ExistingAssignments());
		for (WebElement ele : sessionSearch.getassign_ExistingAssignments().FindWebElements()) {
			String status = ele.getAttribute("aria-selected");
			if (status.equalsIgnoreCase("true")) {
				baseClass.failedStep("Not All its downline objects get deselected (Assignment Group and Assignment).");
			}
		}
		baseClass.stepInfo("All its downline objects get deselected (Assignment Group and Assignment).");

		baseClass.passedStep("Verified - that on selecting Parent object then all its child object gets selected.");
		assignment.deleteAssgnmntUsingPagination(assignmentName1);
		loginPage.logout();
	}

	/**
	 * @author krishna
	 * @throws InterruptedException
	 * @Description :To verify that RMU can perform Persistent Search Hits from
	 *              saved search RPMXCON-53948
	 */

	@Test(description = "RPMXCON-53948", enabled = true, groups = { "regression" })
	public void verifyRMUPerformPersisSearchHitSS() throws Exception {

		String assignmentName1 = "AR2Assignment" + Utility.dynamicNameAppender();
		String searchName = "Search" + Utility.dynamicNameAppender();
		SavedSearch savedSearch = new SavedSearch(driver);

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as :" + Input.rmu1userName);
		baseClass.stepInfo("Test case Id: RPMXCON-53948.");
		baseClass.stepInfo("To verify that RMU can perform Persistent Search Hits from saved search");
		assignment.createAssignment(assignmentName1, Input.codeFormName);

		savedSearch.navigateToSavedSearchPage();
		String Node = savedSearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", Input.yesButton);
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearchInNewNode(searchName, Node);

		savedSearch.navigateToSavedSearchPage();
		savedSearch.selectRootGroupTab(Input.mySavedSearch);
		savedSearch.selectNode1(Node);
		savedSearch.savedSearch_SearchandSelect(searchName, Input.yesButton);

		baseClass.waitForElement(savedSearch.getSavedSearchToBulkAssign());
		savedSearch.getSavedSearchToBulkAssign().Click();

		assignment.assignwithSamplemethod(assignmentName1, "Percentage", "10");
		assignment.navigateToAssignmentsPage();
		
		assignment.selectAssignmentToViewinDocview(assignmentName1);
		

		DocViewPage docView = new DocViewPage(driver);
		docView.clickOnPersistantHitEyeIcon();
		String highlighTxt = docView.getPersistentHitWithoutClickingEyeIcon(Input.searchString1);
		System.out.println(highlighTxt);

		if (highlighTxt.equalsIgnoreCase(Input.searchString1)) {
			baseClass.passedStep("Text which is enter while performing search that text gets highlighted.");
		} else {
			baseClass.failedStep("Text which is enter while performing search that text Not gets highlighted.");
		}
		baseClass.passedStep("Verified -  that search text should get highlighted in Doc View");
		assignment.deleteAssgnmntUsingPagination(assignmentName1);
		savedSearch.deleteNode(Input.mySavedSearch, Node);
		baseClass.passedStep("verified - that RMU can perform Persistent Search Hits from saved search");
		loginPage.logout();
	}

	/**
	 * @author krishna
	 * @throws InterruptedException
	 * @Description :Verify that Application disallow special characters in Edit
	 *              Assignments screen when user performed COPY and PASTE (Special
	 *              Characters) from Notepad. RPMXCON-54440
	 */

	@Test(description = "RPMXCON-54440", groups = { "regression" })
	public void verifyErrorMsgForSpclCharEditAssignScr() throws InterruptedException {
		String assignmentName1 = "AR2Assignment" + Utility.dynamicNameAppender();
		BaseClass baseClass = new BaseClass(driver);

		String dataSet[][] = { { "AssignmentNameWith<test" }, { "AssignmentNameWith(test" },
				{ "AssignmentNameWith)test" }, { "AssignmentNameWith[test" }, { "AssignmentNameWith]test" },
				{ "AssignmentNameWith{test" }, { "AssignmentNameWith}test" }, { "AssignmentNameWith:test" },
				{ "AssignmentNameWith\"test" }, { "AssignmentNameWith'test" }, { "AssignmentNameWith~test" },
				{ "AssignmentNameWith*test" }, { "AssignmentNameWith?test" }, { "AssignmentNameWith&test" },
				{ "AssignmentNameWith$test" }, { "AssignmentNameWith#test" }, { "AssignmentNameWith@test" },
				{ "AssignmentNameWith!test" }, { "AssignmentNameWith-test" }, { "AssignmentNameWith_test" } };

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged In As : " + Input.rmu1userName);
		baseClass.stepInfo("Test case Id: RPMXCON-54440");
		baseClass.stepInfo(
				"Verify that Application disallow special characters in Edit Assignments screen when user performed COPY and PASTE (Special Characters) from Notepad.");
		assignment.createAssignment(assignmentName1, Input.codeFormName);
		assignment.editAssignmentUsingPaginationConcept(assignmentName1);
		for (int i = 0; i < dataSet.length; i++) {
			int j = 0;

			String renameAssign = dataSet[i][j];
			baseClass.waitForElement(assignment.getAssignmentName());
			assignment.getAssignmentName().Clear();
			baseClass.copyandPasteString(renameAssign, assignment.getAssignmentName());
			assignment.getAssignmentSaveButton().waitAndClick(5);
			assignment.verifyErrorMsginAssignmentName();
		}
		assignment.deleteAssgnmntUsingPagination(assignmentName1);
		baseClass.passedStep(
				"Verified - that Application disallow special characters in Edit Assignments screen when user performed COPY and PASTE (Special Characters) from Notepad.");
		loginPage.logout();
	}

	/**
	 * @author krishna
	 * @throws InterruptedException
	 * @Description :To check that when user clicks on \"Cancel\" button from the "
	 *              + "\"Add/remove coding form in this Assignment\" pop-up the
	 *              PopUp should get cancelled RPMXCON-65131
	 */
	@Test(description = "RPMXCON-65131", groups = { "regression" })
	public void verifyPopupCancelBtnSortCoding() throws InterruptedException {
		CodingForm cf = new CodingForm(driver);

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged In As : " + Input.rmu1userName);
		baseClass.stepInfo("Test case Id: RPMXCON-65131");
		baseClass.stepInfo("To check that when user clicks on \"Cancel\" button from the "
				+ "\"Add/remove coding form in this Assignment\" pop-up the PopUp should get cancelled");

		assignment.navigateToAssignmentsPage();
		assignment.selectAssignmentGroup("Root");
		assignment.NavigateToNewEditAssignmentPage("New");

		// Verifying Select and sort Coding form button present in New Assignment Page
		if (assignment.getSelectSortCodingForm_Tab().isElementAvailable(4)) {
			baseClass.passedStep("Select and sort Coding form button present in New Assignment Page");
			assignment.getSelectSortCodingForm_Tab().ScrollTo();
			assignment.getSelectSortCodingForm_Tab().waitAndClick(10);
			baseClass.stepInfo("Select and sort Coding form button Clicked");
		} else {
			baseClass.failedStep("Select and sort Coding form button Not present in New Assignment Page");
		}

		// Verifying Add / Remove Coding Forms in this Assignment Pop Up displaying or
		// not
		if (assignment.SelectCFPopUp_Step1().Visible()) {
			baseClass.passedStep("Step 01: Add / Remove Coding Forms in this Assignment Pop Up displaying.");
		} else {
			baseClass.failedStep("Step 01: Add / Remove Coding Forms in this Assignment Pop Up Not displaying.");
		}

		baseClass.waitForElement(assignment.getCFCancelBtn());
		assignment.getCFCancelBtn().Click();
		baseClass.stepInfo("CLicked Cancel button in Popup");

		// Verifying Add / Remove Coding Forms in this Assignment Pop Up displaying or
		// not
		if (assignment.SelectCFPopUp_Step1().Visible()) {
			baseClass.failedStep("Step 01: Add / Remove Coding Forms in this Assignment Pop Up displaying.");
		} else {
			baseClass.passedStep("Step 01: Add / Remove Coding Forms in this Assignment Pop Up Not displaying.");
		}

		driver.getWebDriver().get(Input.url + "CodingForm/Create");
		baseClass.verifyUrlLanding(Input.url + "CodingForm/Create", "Successfully Navigated to Manage Coding Form Page",
				"Not Navigated to Manage Coding Form Page");

		// Verifying Select and sort Coding form button present in Manage Coding Form
		// Page
		if (cf.getSetCodingFormToSG().isElementAvailable(4)) {
			baseClass.passedStep("Select and sort Coding form button present in Manage Coding Form Page");
			cf.getSetCodingFormToSG().ScrollTo();
			cf.getSetCodingFormToSG().waitAndClick(10);
			baseClass.stepInfo("Select and sort Coding form button Clicked");
		} else {
			baseClass.failedStep("Select and sort Coding form button Not present in Manage Coding Form Page");
		}

		// Verifying Add / Remove Coding Forms in this Assignment Pop Up displaying or
		// not in Manage Coding Form Page
		if (cf.getStep1CfPopUp().Visible()) {
			baseClass.passedStep("Step 01: Add / Remove Coding Forms in this Assignment Pop Up displaying.");
		} else {
			baseClass.failedStep("Step 01: Add / Remove Coding Forms in this Assignment Pop Up Not displaying.");
		}

		baseClass.waitForElement(cf.getCfPopUpCancel());
		cf.getCfPopUpCancel().Click();
		baseClass.stepInfo("CLicked Cancel button in Popup");

		// Verifying Add / Remove Coding Forms in this Assignment Pop Up displaying or
		// not in Manage Coding Form Page
		if (cf.getStep1CfPopUp().Visible()) {
			baseClass.failedStep("Step 01: Add / Remove Coding Forms in this Assignment Pop Up displaying.");
		} else {
			baseClass.passedStep("Step 01: Add / Remove Coding Forms in this Assignment Pop Up Not displaying.");
		}
		baseClass.passedStep("To check that when user clicks on \"Cancel\" button from the "
				+ "\"Add/remove coding form in this Assignment\" pop-up the PopUp should get cancelled");

		loginPage.logout();
	}

	/**
	 * @author jayanthi
	 * @throws InterruptedException
	 * @Description :To verify that RMU can unassign the documents from Assignments
	 *              for Saved Search.
	 */
	@Test(description = "RPMXCON-53644", enabled = true, groups = { "regression" })
	public void verifyUNassignedDocsCount_savedsearch() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-53644");
		baseClass.stepInfo("To verify that RMU can unassign the documents from Assignments for Saved Search.");
		String assgnName = "Assgn" + Utility.dynamicNameAppender();
		String searchName = "searchAssgn" + Utility.dynamicNameAppender();
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("**Pre Req-Creating saved search and creating/assigning docs to Assignment**");

		// performing basic search
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.returnPurehitCount();
		sessionSearch.saveSearch(searchName);
		baseClass.stepInfo("Saved the search with name " + searchName);

		SavedSearch savedsearch = new SavedSearch(driver);
		savedsearch.savedSearch_Searchandclick(searchName);
		savedsearch.getSavedSearchToBulkAssign().waitAndClick(10);
		sessionSearch.bulkAssign();
		assignment.FinalizeAssignmentAfterBulkAssign();
		assignment.createAssignment_fromAssignUnassignPopup(assgnName, Input.codeFormName);
		assignment.getAssignmentSaveButton().waitAndClick(5);
		baseClass.passedStep("Assignment is created and docs assigned from saved search with name -" + assgnName);
		baseClass.stepInfo("Un Assign The docs from assingment " + assgnName + " from saved search page.");
		savedsearch.savedSearch_Searchandclick(searchName);
		savedsearch.getSavedSearchToBulkAssign().waitAndClick(10);
		sessionSearch.UnAssignExistingAssignment(assgnName);

		int ActualDocCount;
		ActualDocCount = Integer.parseInt(assignment.verifydocsCountInAssgnPage(assgnName));
		baseClass.digitCompareEquals(ActualDocCount, 0, "Assigned Docs Count is zero for assignment- " + assgnName,
				"Assigned DocsCount is not zero");
		baseClass.passedStep("Sucessfully verified doc counts un-assigned in Assignment " + assgnName);
		assignment.deleteAssgnmntUsingPagination(assgnName);
		savedsearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		// logout
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: 18/08/2022 TestCase Id:RPMXCON-53649 Description :To
	 * verify that if RMU can create/edit the assignment with the Allow Reviewers to
	 * draw max of (docs) option set from 100 to 250
	 * 
	 * @throws InterruptedException
	 */
//	@Test(description = "RPMXCON-53649", enabled = true, groups = { "regression" })
	public void verifyEditDrawPoolLimit() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-53649");
		baseClass.stepInfo(
				"verify if RMU can create/edit the assignment to draw max of (docs) option set from 100 to 250");
		String assignmentName = "assignment1" + Utility.dynamicNameAppender();
		String poolValue1 = "100";
		String poolValue2 = "250";
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Create new assignment");
		assignment.createAssignment_withoutSave(assignmentName, Input.codeFormName);
		baseClass.stepInfo("Set pool setting and save assignment");
		baseClass.waitForElement(assignment.getAssgn_DocumnetCount());
		assignment.getAssgn_DocumnetCount().SendKeys(poolValue1);
		assignment.saveAssignment(assignmentName, Input.codeFormName);
		baseClass.stepInfo("select assignment and verify pool value");
		assignment.editAssignmentUsingPaginationConcept(assignmentName);
		assignment.verifyValuesFromDistributionTab(poolValue1, assignment.getPoolValueFromDistributeTab());
		driver.Navigate().refresh();
		baseClass.stepInfo("set pool value from 100 to 250");
		baseClass.waitForElement(assignment.getAssgn_DocumnetCount());
		assignment.getAssgn_DocumnetCount().SendKeys(poolValue2);
		assignment.saveAssignment(assignmentName, Input.codeFormName);
		baseClass.stepInfo("verify updated pool value in distribution tab");
		assignment.editAssignmentUsingPaginationConcept(assignmentName);
		assignment.verifyValuesFromDistributionTab(poolValue2, assignment.getPoolValueFromDistributeTab());
		loginPage.logout();

	}

	/**
	 * Author :Arunkumar date: 18/08/2022 TestCase Id:RPMXCON-53648 Description :To
	 * verify that if RMU created the assignment with the options ON/OFF Draw from
	 * pool, then in Distribute Documents it should display Draw from pool status as
	 * "Disabled"
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53648", enabled = true, groups = { "regression" })
	public void verifyDrawPoolDisabledStatus() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-53648");
		baseClass.stepInfo("verify Draw from pool status as 'Disabled' if created assignment with options ON/OFF");
		String assignName1 = "assignment1" + Utility.dynamicNameAppender();
		String assignName2 = "assignment2" + Utility.dynamicNameAppender();
		String poolStatus = "disabled";
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Create new assignments with toggle ON/OFF");
		assignment.createAssignment(assignName1, Input.codeFormName);
		assignment.createAssignment_withoutSave(assignName2, Input.codeFormName);
		baseClass.stepInfo("Set pool setting and save assignment");
		assignment.toggleEnableOrDisableOfAssignPage(false, true, assignment.getAssgnGrp_Create_DrawPooltoggle(),
				"Draw From Pool", true);
		baseClass.stepInfo("Select assignment with toggle status OFF and verify value");
		assignment.editAssignmentUsingPaginationConcept(assignName2);
		assignment.verifyValuesFromDistributionTab(poolStatus, assignment.getdrawFromPoolStatus());
		baseClass.stepInfo("Select assignment with toggle status ON");
		assignment.editAssignmentUsingPaginationConcept(assignName1);
		assignment.toggleEnableOrDisableOfAssignPage(false, true, assignment.getAssgnGrp_Create_DrawPooltoggle(),
				"Draw From Pool", true);
		assignment.editAssignmentUsingPaginationConcept(assignName1);
		assignment.verifyValuesFromDistributionTab(poolStatus, assignment.getdrawFromPoolStatus());
		loginPage.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description Verify the Default live sequence while creating the new
	 *              assignment
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-49086", enabled = true, groups = { "regression" })
	public void verifyDefaultLiveSequenceInNewAssignment() throws InterruptedException {
		baseClass.stepInfo("Verify the Default live sequence while creating the new assignment");
		baseClass.stepInfo("Test case Id:RPMXCON-49086");
		String assignmentName = "assgn" + Utility.dynamicNameAppender();
		SoftAssert sa = new SoftAssert();
		List<String> expectedLiveSequenceOrder = new ArrayList<>();
		List<String> actualLiveSequenceOrder = new ArrayList<>();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignment.createAssignment_withoutSave(assignmentName, Input.codeFormName);
		expectedLiveSequenceOrder = baseClass.getAvailableListofElements(assignment.getLiveSequenceMetadatas());
		baseClass.waitTillElemetToBeClickable(assignment.SaveButton());
		assignment.SaveButton().waitAndClick(10);
		assignment.editAssignmentUsingPaginationConcept(assignmentName);
		actualLiveSequenceOrder = baseClass.getAvailableListofElements(assignment.getLiveSequenceMetadatas());
		sa.assertEquals(expectedLiveSequenceOrder, actualLiveSequenceOrder);
		sa.assertAll();
		baseClass.passedStep(
				"The order of live sequence are selected during creation of new assignment is reflected in edit assignment successfully");
		assignment.deleteAssgnmntUsingPagination(assignmentName);
		loginPage.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description To verify that if there is no documents in Assignments then
	 *              instead of draw from pool link it will display zero
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-53886", enabled = true, groups = { "regression" })
	public void verifyDrawPoolLinkIsNotDisplayed() throws InterruptedException {
		baseClass.stepInfo(
				"To verify that if there is no documents in Assignments then instead of draw from pool link it will display zero");
		baseClass.stepInfo("Test case Id:RPMXCON-53886");
		String assignmentName = "assgn" + Utility.dynamicNameAppender();
		SoftAssert sa = new SoftAssert();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignment.createAssignment(assignmentName, Input.codeFormName);
		assignment.editAssignmentUsingPaginationConcept(assignmentName);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(assignment.getAssignment_ManageReviewersTab());
		baseClass.waitTillElemetToBeClickable(assignment.getAssignment_ManageReviewersTab());
		assignment.getAssignment_ManageReviewersTab().waitAndClick(10);
		baseClass.waitForElement(assignment.getAddReviewersBtn());
		baseClass.waitTillElemetToBeClickable(assignment.getAddReviewersBtn());
		assignment.getAddReviewersBtn().waitAndClick(10);
		baseClass.waitForElement(assignment.getSelectUserToAssig());
		assignment.getSelectUserToAssignGeneral(Input.rev1userName).WaitUntilPresent().ScrollTo();
		baseClass.waitTillElemetToBeClickable(assignment.getSelectUserToAssignGeneral(Input.rev1userName));
		assignment.getSelectUserToAssignGeneral(Input.rev1userName).waitAndClick(10);
		baseClass.waitForElement(assignment.getAdduserBtn());
		baseClass.waitTillElemetToBeClickable(assignment.getAdduserBtn());
		assignment.getAdduserBtn().waitAndClick(10);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		assignment.verifyDrawPoolToggledisplay(assignmentName, "disabled");
		baseClass.waitForElement(assignment.getTotalDocsCountInReviewerDashboard(assignmentName));
		String ActualDocs_value = assignment.getTotalDocsCountInReviewerDashboard(assignmentName).getText().trim();
		String TotslDocs = ActualDocs_value.substring(6, 9).trim();
		sa.assertEquals("0", TotslDocs);
		sa.assertAll();
		baseClass.passedStep(
				"Total documents are 0 and no draw pool link is displayed when no documents are assigned to an assignment");
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignment.deleteAssgnmntUsingPagination(assignmentName);
		loginPage.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description To verify that if documents are less than the value in
	 *              "Allowance of drawn document to:" then RU can draw all the
	 *              documents.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53885", enabled = true, groups = { "regression" })
	public void verifyAllowanceOfDrawDocuments() throws Exception {
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();
		String count = "8";
		SessionSearch search = new SessionSearch(driver);
		SoftAssert sa = new SoftAssert();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-53885");
		baseClass.stepInfo(
				"To verify that if documents are less than the value in \"Allowance of drawn document to:\" then RU can draw all the documents.");
		assignment.createAssignment_withoutSave(assignmentName, Input.codingFormName);
		assignment.toggleEnableOrDisableOfAssignPage(true, false, assignment.getAssgnGrp_Create_DrawPooltoggle(),
				"Draw From Pool", true);
		int purehit = search.basicContentSearch(Input.TallySearch);
		search.bulkAssignExisting(assignmentName);
		assignment.editAssignmentUsingPaginationConcept(assignmentName);
		assignment.distributeTheGivenDocCountToReviewer(count,Input.rev1userName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		driver.waitForPageToBeReady();
		assignment.verifyDrawPoolToggledisplay(assignmentName, "enabled");
		String DocsBeforeClickDrawAction = assignment.getTotalDocsCountInReviewerDashboard(assignmentName).getText()
				.trim();
		String TotalDocsBeforeClickDrawAction = DocsBeforeClickDrawAction.substring(6, 9).trim();
		sa.assertEquals(TotalDocsBeforeClickDrawAction, count);
		sa.assertAll();
		baseClass.passedStep("The expected value " + TotalDocsBeforeClickDrawAction
				+ " is displayed in reviewer page before draw action exactly");
		baseClass.waitForElement(assignment.getAssignmentsDrawPoolInreviewerPg(assignmentName));
		assignment.getAssignmentsDrawPoolInreviewerPg(assignmentName).Click();
		baseClass.waitForElementToBeGone(assignment.getAssignmentsDrawPoolInreviewerPg(assignmentName), 5);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(assignment.getTotalDocsCountInReviewerDashboard(assignmentName));
		driver.scrollingToElementofAPage(assignment.getTotalDocsCountInReviewerDashboard(assignmentName));
		String DocsAfterClickDrawAction = assignment.getTotalDocsCountInReviewerDashboard(assignmentName).getText()
				.trim();
		String TotalDocsAfterClickDrawAction = DocsAfterClickDrawAction.substring(6, 9).trim();
		sa.assertEquals(Integer.toString(purehit), TotalDocsAfterClickDrawAction);
		sa.assertAll();
		baseClass.passedStep("The expected value of remaining docs " + TotalDocsAfterClickDrawAction
				+ " is displayed in reviewer page after draw action exactly");
		baseClass.passedStep(
				"Successfully verified that if documents are less than the value in \"Allowance of drawn document to:\" then RU can draw all the documents.");
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignment.deleteAssgnmntUsingPagination(assignmentName);
		loginPage.logout();
	}

	/**
	 * @author Jayanthi.ganesan Verify that 3 tabs appear as defined when the user
	 *         has elected to Assign documents
	 */
	@Test(description = "RPMXCON-54335", groups = { "regression" }, enabled = true)
	public void verifyAssignOptions() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-54335");
		baseClass.stepInfo("Verify that 3 tabs appear as defined when the user has elected to Assign documents");

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");

		baseClass.stepInfo("Performing basic content search and dragging pure hit to cart.");
		sessionSearch.basicContentSearch(Input.testData1);
		baseClass.stepInfo("Clicking on bulk assign button");
		sessionSearch.bulkAssign();
		// verification of tab under assign radio button
		sessionSearch.verifyBulkAssignOptions();

		loginPage.logout();

	}

	/**
	 * Author :Arunkumar date: 19/09/2022 TestCase Id:RPMXCON-54751 Description :New
	 * Assignment - By default all keywords part of the security group should be
	 * selected for the assignment
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54751", enabled = true, groups = { "regression" })
	public void verifyKeywordSelectionInAssignment() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54751");
		baseClass.stepInfo("By default all keywords part of the security group should be selected for the assignment ");
		String assignmentName = "assign" + Utility.dynamicNameAppender();
		String[] keywords = { "test", "the", "and", "in", "of", "to", "at", "are", "for", "on" };
		List<String> keys = Arrays.asList(keywords);
		String color = "Red";

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");
		baseClass.stepInfo("Create keywords");
		KeywordPage keyPage = new KeywordPage(driver);
		keyPage.navigateToKeywordPage();
		for (int i = 0; i < keywords.length; i++) {
			keyPage.addKeyword(keywords[i], color);
		}
		baseClass.stepInfo("Create new assignment");
		assignment.createAssignment_withoutSave(assignmentName, Input.codingFormName);
		baseClass.stepInfo("Check keyword popup and save assignment details");
		assignment.verifyKeywordsAvailabilityInAssignment(keywords);
		baseClass.waitForElement(assignment.getAssignmentSaveButton());
		assignment.getAssignmentSaveButton().waitAndClick(10);
		baseClass.stepInfo("Assign document , add reviewer and distribute documents");
		sessionSearch.MetaDataSearchInBasicSearch(Input.sourceDocIdSearch, Input.sourceKeyDocId);
		sessionSearch.bulkAssignExisting(assignmentName);
		assignment.editAssignmentUsingPaginationConcept(assignmentName);
		assignment.addReviewerAndDistributeDocs(Input.rev1userName);
		loginPage.logout();
		baseClass.stepInfo("Login as reviewer and verify keyword highlight in docview");
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.waitForElement(assignment.getSelectAssignmentAsReviewer(assignmentName));
		assignment.getSelectAssignmentAsReviewer(assignmentName).waitAndClick(10);
		;
		driver.waitForPageToBeReady();
		DocViewPage docView = new DocViewPage(driver);
//		baseClass.waitForElement(docView.getPersistantHitEyeIcon());
//		docView.getPersistantHitEyeIcon().waitAndClick(10);
		docView.verifyPersistantHitsWithDocView(keys);
		baseClass.passedStep("All keywords related to assignment highlighted");
		loginPage.logout();
	}

	@Test(description = "RPMXCON-53716", enabled = true, groups = { "regression" })
	public void verifyUncompleteAllDocs() throws Exception {
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();
		String count = "2";

		SoftAssert sa = new SoftAssert();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-53716");
		baseClass.stepInfo(
				"To verify that RMU is able to mark as Incompleted for the remaining documents in the Assignments");

		baseClass.stepInfo("***Creating assignment and bulk assign docs and distributing docs to user*");
		assignment.createAssignment(assignmentName, Input.codingFormName);
		int purehit = sessionSearch.basicContentSearch(Input.TallySearch);
		sessionSearch.bulkAssignExisting(assignmentName);
		assignment.editAssignmentUsingPaginationConcept(assignmentName);
		assignment.distributeTheGivenDocCountToReviewer(count,Input.rev1userName);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Logged in as reviewer user");
		baseClass.stepInfo(
				"*Completing " + count + " docs from distributed documents for assignment-" + assignmentName + " **");
		// navigating from Dashboard to DocView
		DocViewPage docViewPage = new DocViewPage(driver);
		docViewPage.selectAssignmentfromDashborad(assignmentName);
		// Completing the documents
		driver.waitForPageToBeReady();
		docViewPage.CompleteTheDocumentInMiniDocList(Integer.parseInt(count));
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("** uncompleting All docs **");
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignment.viewSelectedAssgnUsingPagination(assignmentName);
		assignment.uncompleteAllDocs(assignmentName);

		// Taking count of Left To Do /Total Docs In Assignment/ Distributed to User/
		// Complete columns from manage assignments page .
		baseClass.stepInfo("**After uncompleting All docs verifying Various counts of docs coulumn in asignemnt**");
		assignment.viewSelectedAssgnUsingPagination(assignmentName);
		assignment.getSelectAssignment(assignmentName).isElementAvailable(2);
		assignment.getSelectAssignment(assignmentName).ScrollTo();
		assignment.getSelectAssignment(assignmentName).Click();
		String leftToDo = assignment.getRowValueFromAssignmentTable("Left To Do", assignmentName);
		String totalDocsInAsign = assignment.getRowValueFromAssignmentTable("Total Docs In Assignment", assignmentName);
		String distributesDocsCount = assignment.getRowValueFromAssignmentTable("Distributed to User", assignmentName);
		String completedDocsCount = assignment.getRowValueFromAssignmentTable("Complete", assignmentName);

		sa.assertEquals(leftToDo, count);
		sa.assertEquals(totalDocsInAsign, String.valueOf(purehit));
		sa.assertEquals(distributesDocsCount, count);
		sa.assertEquals(completedDocsCount, "0");
		sa.assertAll();
		loginPage.logout();
	}

	/**
	 * @author
	 * @Date: 21/9/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that belly band message appears when user configured
	 *              Proximity with Left double quotes only and combined with other
	 *              criteria in Advanced Search Query Screen. RPMXCON-57300
	 */
	@Test(description = "RPMXCON-54317", enabled = true, groups = { "regression" })
	public void verifyEmailAuthorNameAndAddressFieldDisplayedOnMetadataList() throws InterruptedException {
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();
		String metaData = "EmailAuthorNameAndAddress";

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id:RPMXCON-54317");
		baseClass.stepInfo(
				"Verify that 'EmailAuthorNameAndAddress' field should be displayed on metadata list while editing the assignment");

		// creating assignment
		baseClass.stepInfo("Step-2 Go to Manage > Assignment and select the assignment to edit**");
		assignment.createAssignment(assignmentName, Input.codingFormName);
		assignment.editAssignmentUsingPaginationConcept(assignmentName);

		// verify that EmailAuthorNameAndAddress field should be displayed in the
		// metadata pop up under Assigned Metadata list
		baseClass.stepInfo(
				"Step-3 Click 'Select Metadata' button to configure metadata to assignment and check for the EmailAuthorNameAndAddress field from pop up under Assigned Metadata list**");
		baseClass.waitForElement(assignment.GetSelectMetaDataBtn());
		assignment.GetSelectMetaDataBtn().waitAndClick(10);
		baseClass.ValidateElement_Presence(assignment.getSelectAssignedMDinAssignPage(metaData),
				"EmailAuthorNameAndAddress Metadata field");
		baseClass.passedStep(
				"Verified That EmailAuthorNameAndAddress field is displayed in the metadata pop up under Assigned Metadata list.");

		// delete assignment
		baseClass.stepInfo("Initiating Delete assignment");
		assignment.deleteAssgnmntUsingPagination(assignmentName);

		// logOut
		loginPage.logout();
	}

	/**
	 * @author Jayanthi.Ganesan
	 * @throws InterruptedException
	 * @Description :To verify that if Documents are assigned to Reviwer then
	 *              "Unassigned Documents" is displayed as "0".
	 */
	@Test(description = "RPMXCON-53650", enabled = true, groups = { "regression" })
	public void verifyUnassignedDocsCount_DistribTab() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-53650");
		baseClass.stepInfo("To verify that if Documents are assigned to Reviwer then "
				+ "\"Unassigned Documents\" is displayed as \"0\".");
		String assgnName = "Assgn" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		// creating assignment
		assignment.createAssignment(assgnName, Input.codeFormName);
		baseClass.stepInfo("Created Assignment with name  -" + assgnName);
		// performing basic search
		sessionSearch.basicContentSearch(Input.searchString1);
		String countToAssing = sessionSearch.verifyPureHitsCount();
		// performing bulk assign action and verifying doc count in assignment page.
		sessionSearch.bulkAssign();
		assignment.assignwithSamplemethod(assgnName, "Count of Selected Docs", countToAssing);
		baseClass.waitTillElemetToBeClickable(assignment.getAssignmentActionDropdown());
		driver.scrollPageToTop();
		assignment.getAssignmentActionDropdown().waitAndClick(10);
		assignment.assignmentActions("Edit");
		// adding reviewer and distributing docs
		assignment.addReviewerAndDistributeDocs(Input.rev1userName);
		// taking unassigned docs count
		driver.Navigate().refresh();
		assignment.getDistributeTab().Click();
		String uassignedDocCount = assignment.getEditAggn_Distribute_Unassgndoc().getText();
		baseClass.compareTextViaContains(uassignedDocCount, "0",
				"Count of UnAssigned docs under distribute tab  Displayed 0.",
				"Count of UnAssigned docs in distribute  Displayed is " + uassignedDocCount);

		assignment.deleteAssgnmntUsingPagination(assgnName);

		// logout
		loginPage.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @Description :To verify that created Coding Form is displayed on Coding Form
	 *              field for Assignment.
	 */
	@Test(description = "RPMXCON-53970", enabled = true, groups = { "regression" })
	public void verifyCreatedCfDisplayedInAssignment() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-53970");
		baseClass.stepInfo("To verify that created Coding Form is displayed on Coding Form field for Assignment.");
		String assignmentName = "Assgn" + Utility.dynamicNameAppender();
		String cfName = "cf" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		CodingForm codingForm = new CodingForm(driver);
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
		codingForm.getAddNewCodingFormBtn().waitAndClick(10);
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(cfName);
		codingForm.CreateCodingFormWithParameter(cfName, null, "Document_Comments", null, "comment");
		codingForm.addcodingFormAddButton();
		codingForm.saveCodingForm();
		assignment.createAssignment_withoutSave(assignmentName, cfName);
		baseClass.passedStep("Newly created codingform displayed in assignment successfully");
		codingForm.deleteCodingForm(cfName, cfName);
		loginPage.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @Description :To verify that Coding form field is displayed for Assignment
	 *              only
	 */
	@Test(description = "RPMXCON-53964", enabled = true, groups = { "regression" })
	public void verifyCfFieldDisplayedInAssignment() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-53964");
		baseClass.stepInfo("To verify that Coding form field is displayed for Assignment only");
		String assignmentName = "Assgn" + Utility.dynamicNameAppender();
		String assignmentGrpName = "AssgnGrp" + Utility.dynamicNameAppender();
		System.out.println(assignmentGrpName);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignment.createCascadeNonCascadeAssgnGroup_withoutSave(assignmentGrpName, "Yes");
		if (assignment.getSelectSortCodingForm_Tab().isDisplayed() == false) {
			baseClass.passedStep("Coding form field is not displayed in new assignment group page");
		} else {
			baseClass.failedStep("Coding form field is displayed in new assignment group page");
		}
		baseClass.waitTillElemetToBeClickable(assignment.SaveButton());
		assignment.SaveButton().waitAndClick(10);
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignment.editAssgnGrp(assignmentGrpName, "No");
		if (assignment.getSelectSortCodingForm_Tab().isDisplayed() == false) {
			baseClass.passedStep("Coding form field is not displayed in edit assignment group page");
		} else {
			baseClass.failedStep("Coding form field is displayed in edit assignment group page");
		}
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignment.DeleteAssgnGroup(assignmentGrpName);
		assignment.createAssignment(assignmentName, Input.codeFormName);
		baseClass.passedStep("Coding form fiels is displayed in new assignment page");
		assignment.editAssignmentUsingPaginationConcept(assignmentName);
		if (assignment.getSelectSortCodingForm_Tab().isDisplayed()) {
			baseClass.passedStep("Coding form field is displayed in edit assignment page");
		} else {
			baseClass.failedStep("Coding form field is not displayed in edit assignment page");
		}
		assignment.deleteAssgnmntUsingPagination(assignmentName);
		loginPage.logout();

	}

	/**
	 * @author jayanthi
	 * @throws InterruptedException
	 * @Description :To verify that RMU can assign the documents from Saved Search.
	 */
	@Test(description = "RPMXCON-53643", enabled = true, groups = { "regression" })
	public void verifyassignedDocsCount_savedsearch() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-53643");
		baseClass.stepInfo("To verify that RMU can assign the documents from Saved Search.");
		String assgnName = "Assgn" + Utility.dynamicNameAppender();
		String searchName = "searchAssgn" + Utility.dynamicNameAppender();
		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("**Pre REq-Creating Assignment**");
		// creating assignment
		assignment.createAssignment(assgnName, Input.codeFormName);
		baseClass.stepInfo("Created Assignment with name  -" + assgnName);
		// performing basic search
		sessionSearch.basicContentSearch(Input.searchString1);
		String countToAssing = sessionSearch.verifyPureHitsCount();
		sessionSearch.saveSearch(searchName);
		baseClass.stepInfo("Saved the search with name " + searchName);
		// performing bulk assign action and verifying doc count in assignment page.
		sessionSearch.bulkAssign();
		assignment.assignwithSamplemethod(assgnName, "Count of Selected Docs", countToAssing);

		// logout
		loginPage.logout();
	}

	/**
	 * @author Jayanthi.ganesan Verify that when the radio button selected is to
	 *         Unassign documents, only the Existing Assignment(s) tab appears
	 */
	@Test(description = "RPMXCON-54321", groups = { "regression" }, enabled = true)
	public void verifyUnAssignOptions() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-54321");
		baseClass.stepInfo("Verify that when the radio button selected is to Unassign documents, only the"
				+ " Existing Assignment(s) tab appears");

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");

		baseClass.stepInfo("Performing basic content search and dragging pure hit to cart.");
		sessionSearch.basicContentSearch(Input.testData1);
		baseClass.stepInfo("Clicking on bulk assign button");
		sessionSearch.bulkAssign();
		sessionSearch.verifyUnAssignOptions();

	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description To verify that numbers set in \"Allowance of drawn document
	 *              to:\" by RMU for Assignment, " + "that number of documents can
	 *              be drawn from General Pool by RU on clicking \"Draw from Pool\"
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53884", enabled = true, groups = { "regression" })
	public void verifyAllowOfDrawnGeneralPool() throws Exception {
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		String rev[] = { Input.rev1userName };
		String expDocCount = "20";

		LoginPage loginPage = new LoginPage(driver);
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		SessionSearch search = new SessionSearch(driver);
		SoftAssert sa = new SoftAssert();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-53884 Assignments");
		baseClass.stepInfo("To verify that numbers set in \"Allowance of drawn document to:\" by RMU for Assignment, "
				+ "that number of documents can be drawn from General Pool by RU on clicking \"Draw from Pool\"");

		assignment.createAssignment_withoutSave(assignmentName, Input.codingFormName);
//		Draw pool Toggle Enable and making draw limit as 20
		assignment.toggleEnableOrDisableOfAssignPage(true, false, assignment.getAssgnGrp_Create_DrawPooltoggle(),
				"Draw From Pool", true);
		baseClass.stepInfo("numbers set in Allowance of drawn document is 20");
		search.basicContentSearch(Input.searchStringStar);
		search.bulkAssign();
		assignment.assignwithSamplemethod(assignmentName, "Count of Selected Docs", "1000");
		baseClass.stepInfo("Successfully 1000 documents are assigned to the Assignment");
		assignment.editAssignmentUsingPaginationConcept(assignmentName);
		assignment.assignReviewers(rev);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		driver.waitForPageToBeReady();
		assignment.verifyDrawPoolToggledisplay(assignmentName, "enabled");
		baseClass.waitForElement(assignment.getAssignmentsDrawPoolInreviewerPg(assignmentName));
		assignment.getAssignmentsDrawPoolInreviewerPg(assignmentName).Click();
		baseClass.waitForElementToBeGone(assignment.getAssignmentsDrawPoolInreviewerPg(assignmentName), 5);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(assignment.getTotalDocsCountInReviewerDashboard(assignmentName));
		driver.scrollingToElementofAPage(assignment.getTotalDocsCountInReviewerDashboard(assignmentName));
		String ActualDocs_value = assignment.getTotalDocsCountInReviewerDashboard(assignmentName).getText().trim();
		String TotslDocs = ActualDocs_value.substring(6, 9).trim();
		sa.assertEquals(expDocCount, TotslDocs);
		sa.assertAll();
		baseClass.passedStep(
				"Successfully Verified That - numbers set in \"Allowance of drawn document to:\" by RMU for Assignment, "
						+ "that number of documents can be drawn from General Pool by RU on clicking \"Draw from Pool");
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignment.deleteAssgnmntUsingPagination(assignmentName);

		loginPage.logout();

	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description To verify that draw from pool link will be dispalyed after the
	 *              documents are completed for the assignment which was distributed
	 *              manually.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53888", enabled = true, groups = { "regression" })
	public void verifyDrawnPoolLinkafterCompleted() throws Exception {
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();

		LoginPage loginPage = new LoginPage(driver);
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		SessionSearch search = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		SoftAssert sa = new SoftAssert();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-53888 Assignments");
		baseClass.stepInfo("To verify that draw from pool link will be dispalyed after the documents are completed "
				+ "for the assignment which was distributed manually.");

		assignment.createAssignment_withoutSave(assignmentName, Input.codingFormName);
		assignment.toggleEnableOrDisableOfAssignPage(true, false, assignment.getAssgnGrp_Create_DrawPooltoggle(),
				"Draw From Pool", true);

		search.basicContentSearch(Input.searchString1);
		search.bulkAssignExisting(assignmentName);
		assignment.editAssignmentUsingPaginationConcept(assignmentName);
		String docDistriCount = assignment.distributeTheGivenDocCountToReviewer("2",Input.rev1userName);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		assignment.SelectAssignmentByReviewer(assignmentName);
		driver.waitForPageToBeReady();
		for (int i = 1; i <= Integer.parseInt(docDistriCount); i++) {
			docView.completeDocument(Input.randomText);
		}
		driver.waitForPageToBeReady();
		assignment.navigateBack(1);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		
		try {
		driver.scrollingToElementofAPage(assignment.getAssignmentsDrawPoolInreviewerPg(assignmentName));
		}catch(Exception e) {}
		boolean drawLink = assignment.getAssignmentsDrawPoolInreviewerPg(assignmentName).isDisplayed();
		sa.assertTrue(drawLink);
		sa.assertAll();
		baseClass.stepInfo(
				"The Draw link is visible to the RU after marking the manually distributed documents as Completed.");
		baseClass.passedStep(
				"verified - that draw from pool link will be dispalyed after the documents are completed for the assignment which was distributed manually.");
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignment.deleteAssgnmntUsingPagination(assignmentName);
		loginPage.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description verify that Total Documents displayed on Distribute Documents
	 *              are correct if one/multiple assignments are selected while
	 *              assigning the Assignments
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53645", enabled = true, groups = { "regression" })
	public void verifyTotalDocDisonDDMultAssign() throws Exception {
		String assignmentName1 = "AR2Assignment" + Utility.dynamicNameAppender();
		String assignmentName2 = "AR2Assignment" + Utility.dynamicNameAppender();
		String noToAssign = "1000";

		LoginPage loginPage = new LoginPage(driver);
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		SessionSearch search = new SessionSearch(driver);
		BaseClass baseClass = new BaseClass(driver);
		SoftAssert sa = new SoftAssert();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as :" + Input.rmu1userName);
		baseClass.stepInfo("Test case Id: RPMXCON-53645 Assignments");
		baseClass.stepInfo("verify that Total Documents displayed on Distribute Documents are correct"
				+ " if one/multiple assignments are selected while assigning the Assignments");
		assignment.createAssignment(assignmentName1, Input.codeFormName);
		assignment.createAssignment(assignmentName2, Input.codeFormName);

		int purehit = search.basicContentSearch(Input.searchStringStar);
		baseClass.waitForElement(search.getDocsMetYourCriteriaLabel());
		baseClass.dragAndDrop(search.getDocsMetYourCriteriaLabel(), search.getActionPad());
		driver.waitForPageToBeReady();
		search.bulkAssign();
		baseClass.stepInfo("Assign/Unassign Docs pop up is displayed");
		baseClass.waitForElement(search.getSelectAssignmentExisting(assignmentName1));
		search.getSelectAssignmentExisting(assignmentName1).Click();
		baseClass.stepInfo(assignmentName1 + "Selected in Assign Popup");
		baseClass.waitForElement(search.getSelectAssignmentExisting(assignmentName2));
		search.getSelectAssignmentExisting(assignmentName2).Click();
		baseClass.stepInfo(assignmentName2 + "Selected in Assign Popup");

		assignment.VerifySampleMethodDDOptions();
		baseClass.waitTillTextToPresent(assignment.getStartingCount(), String.valueOf(purehit));
		baseClass.waitForElement(assignment.getsampleMethod());
		assignment.getsampleMethod().selectFromDropdown().selectByVisibleText("Count of Selected Docs");
		baseClass.stepInfo("Count of Selected Docs Option Selected in SampleMethod DD");
		assignment.getCountToAssign().SendKeys(noToAssign);
		baseClass.stepInfo(noToAssign + "Entered in Number to Assign tab Successfully");
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		baseClass.waitTillElemetToBeClickable(search.getContinueButton());
		baseClass.waitForElement(search.getContinueButton());
		search.getContinueButton().waitAndClick(30);
		baseClass.waitForElement(search.getFinalizeButton());
		search.getFinalizeButton().waitAndClick(30);

		assignment.editAssignmentUsingPaginationConcept(assignmentName1);
		baseClass.waitForElement(assignment.getDistributeTab());
		assignment.getDistributeTab().Click();
		baseClass.waitForElement(assignment.getEditAggn_Distribute_Totaldoc());
		sa.assertEquals(noToAssign, assignment.getEditAggn_Distribute_Totaldoc().getText());
		baseClass.stepInfo("Distribute Documents is displayed and Total Documents is displayed as "
				+ assignment.getEditAggn_Distribute_Totaldoc().getText());

		baseClass.waitForElement(assignment.getAssignment_BackToManageButton());
		assignment.getAssignment_BackToManageButton().Click();

		assignment.editAssignmentUsingPaginationConcept(assignmentName2);
		baseClass.waitForElement(assignment.getDistributeTab());
		assignment.getDistributeTab().Click();
		baseClass.waitForElement(assignment.getEditAggn_Distribute_Totaldoc());
		sa.assertEquals(noToAssign, assignment.getEditAggn_Distribute_Totaldoc().getText());
		baseClass.stepInfo("Distribute Documents is displayed and Total Documents is displayed as 1000"
				+ assignment.getEditAggn_Distribute_Totaldoc().getText());
		sa.assertAll();
		baseClass.passedStep("verified - that Total Documents displayed on Distribute Documents are correct "
				+ "if one/multiple assignments are selected while assigning the Assignments");
		assignment.deleteAssgnmntUsingPagination(assignmentName1);
		assignment.deleteAssgnmntUsingPagination(assignmentName2);
		loginPage.logout();

	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description Verified that validations are displayed for fields Name,
	 *              RateValue, RateType as Expected
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53613", enabled = true, groups = { "regression" })
	public void verifyRateDDinClassPage() throws Exception {
		LoginPage lp = new LoginPage(driver);
		ClassificationPage clssp = new ClassificationPage(driver);
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		BaseClass baseClass = new BaseClass(driver);
		SoftAssert sa = new SoftAssert();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as :" + Input.rmu1userName);
		baseClass.stepInfo("Test case Id: RPMXCON-53613");
		baseClass.stepInfo("To Verify Rate Dropdown");

		clssp.navigateClassificationPage();
		clssp.VerifyRateDDOptions();
		clssp.verifyClassifactionFieldsErrorAlerts();
		baseClass.stepInfo("Verified that validations are displayed for fields Name, RateValue, RateType as Expected");

		// Verifying Name Field Error Message
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		clssp.getMaxQualified().selectFromDropdown().selectByVisibleText("--Select--");
		baseClass.waitForElement(clssp.getClassificationNameQC());
		clssp.updateQCClassificDetailsNotSave("!@#$%^&*()_+", "$ - AUD", "1");
		sa.assertEquals(clssp.getClassificationNameQCErrormsg().getText(),
				"You cannot specify any special characters in the field value");
		clssp.updateQCClassificDetailsNotSave("1LR", "$ - AUD", "1");
		sa.assertFalse(clssp.getClassificationNameQCErrormsg().isElementAvailable(5));
		clssp.updateQCClassificDetailsNotSave("1234565", "$ - AUD", "1");
		sa.assertFalse(clssp.getClassificationNameQCErrormsg().isElementAvailable(5));
		clssp.updateQCClassificDetailsNotSave("Minor", "$ - AUD", "1");
		sa.assertFalse(clssp.getClassificationNameQCErrormsg().isElementAvailable(5));
		baseClass.stepInfo("Verified that validations are displayed for Name Field as Expected");

		// Verifying Rate Value Field Error Message
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		clssp.getMaxQualified().selectFromDropdown().selectByVisibleText("--Select--");
		baseClass.waitForElement(clssp.getClassificationRatevalueQC());
		clssp.updateQCClassificDetailsNotSave("1LR", "$ - AUD", "!@#$%^&*()_+");
		sa.assertEquals(clssp.getClassificationRatevalueErrormsgQC().getText(),
				"The currency value specified is invalid.");
		clssp.updateQCClassificDetailsNotSave("1LR", "$ - AUD", "1RS");
		sa.assertEquals(clssp.getClassificationRatevalueErrormsgQC().getText(),
				"The currency value specified is invalid.");
		clssp.updateQCClassificDetailsNotSave("1LR", "$ - AUD", "1234565");
		sa.assertFalse(clssp.getClassificationRatevalueErrormsgQC().isElementAvailable(5));
		clssp.updateQCClassificDetailsNotSave("1LR", "$ - AUD", "65.65");
		sa.assertFalse(clssp.getClassificationRatevalueErrormsgQC().isElementAvailable(5));
		clssp.updateQCClassificDetailsNotSave("1LR", "$ - AUD", "Dollar");
		sa.assertEquals(clssp.getClassificationRatevalueErrormsgQC().getText(),
				"The currency value specified is invalid.");
		baseClass.stepInfo("Verified that validations are displayed for Rate Value Field as Expected");

		// Verifying maxQualifiedFunctionality Working Properly
		clssp.verifyMaxQualifiedFunctionality(1);
		clssp.verifyMaxQualifiedFunctionality(2);
		clssp.verifyMaxQualifiedFunctionality(3);
		clssp.verifyMaxQualifiedFunctionality(4);
		clssp.verifyMaxQualifiedFunctionality(5);

		// Save Classification For Project And Verify that in New Assignment Page
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		clssp.getMaxQualified().selectFromDropdown().selectByValue(Integer.toString(2));
		clssp.updateLevelClassificDetailsNotSave(1, "1LR", "$ - USD", "5");
		clssp.updateLevelClassificDetailsNotSave(2, "2LR", "$ - USD", "10");
		clssp.updateQCClassificDetailsNotSave("QC", "$ - USD", "15");
		baseClass.waitForElement(clssp.getSaveBtn());
		clssp.getSaveBtn().Click();
		assignment.navigateToAssignmentsPage();
		assignment.NavigateToNewEditAssignmentPage("new");

		List<String> expOptions = new ArrayList<>(Arrays.asList("1LR", "2LR", "QC"));
		List<String> actOptions = assignment.verifyClassificationDDOptions();
		baseClass.stepInfo(actOptions + " Options are available in classification DD");
		sa.assertEquals(actOptions, expOptions);
		baseClass.stepInfo("Verified that it displays following classifications:      a. 1LR      b. 2LR      c. QC");
		sa.assertAll();
		baseClass.passedStep("Successfully Verified Rate dropdown");
		lp.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description Verified that validations are displayed for fields Name,
	 *              RateValue, RateType as Expected
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53616", enabled = true, groups = { "regression" })
	public void verifyRMUCanDefineClassforProject() throws Exception {
		String maxQualified = "2";
		List<String> expOptions = new ArrayList<>(Arrays.asList("1LR", "2LR", "3LR"));

		LoginPage lp = new LoginPage(driver);
		ClassificationPage clssp = new ClassificationPage(driver);
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		BaseClass baseClass = new BaseClass(driver);
		SoftAssert sa = new SoftAssert();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as :" + Input.rmu1userName);
		baseClass.stepInfo("Test case Id: RPMXCON-53616");
		baseClass.stepInfo("To verify that if RMU define the Classification for project "
				+ "then it is displayed while creating the assignment for that project.");
		baseClass.selectproject();
		clssp.navigateClassificationPage();
		clssp.getMaxQualified().selectFromDropdown().selectByValue(maxQualified);
		baseClass.stepInfo(maxQualified + " Selected in Max Qualified DropDown");
		clssp.updateLevelClassificDetailsNotSave(1, "1LR", "$ - USD", "5");
		clssp.updateLevelClassificDetailsNotSave(2, "2LR", "$ - USD", "10");
		clssp.updateQCClassificDetailsNotSave("3LR", "$ - USD", "15");
		baseClass.waitForElement(clssp.getSaveBtn());
		clssp.getSaveBtn().Click();
		baseClass.VerifySuccessMessage("Cancel");
		baseClass.stepInfo("Classification for the project is saved Successfully");
		assignment.navigateToAssignmentsPage();
		assignment.NavigateToNewEditAssignmentPage("new");
		baseClass.stepInfo("New AssignmentPage Displaying");

		List<String> actOptions = assignment.verifyClassificationDDOptions();
		baseClass.stepInfo(actOptions + " Options are available in classification DD");
		sa.assertEquals(actOptions, expOptions);
		baseClass.stepInfo("Verified that it displays following classifications:      a. 1LR      b. 2LR      c. 3LR");
		baseClass.passedStep("verified - that if RMU define the Classification for project then it is displayed "
				+ "while creating the assignment for that project.");
		sa.assertAll();
		lp.logout();
	}

	/**
	 * Author :Arunkumar date: 04/08/2022 TestCase Id:RPMXCON-54033 Description :To
	 * verify that Tags displayed in Tags pop up are security group specific
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54033", enabled = true, groups = { "regression" })
	public void verifyTagsAreSecurityGroupSpecific() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54033");
		baseClass.stepInfo("Verify that Tags displayed in Tags pop up are security group specific.");
		String sgName1 = "SecurityGroup1_" + UtilityLog.dynamicNameAppender();
		String sgName2 = "SecurityGroup2_" + UtilityLog.dynamicNameAppender();
		String tagName1 = "securityGroup1_Tag" + UtilityLog.dynamicNameAppender();
		String tagName2 = "securityGroup2_Tag" + UtilityLog.dynamicNameAppender();
		String assignmentName1 = "assignment1" + UtilityLog.dynamicNameAppender();
		String assignmentName2 = "assignment2" + UtilityLog.dynamicNameAppender();
		String cfName1 = "codingForm1" + UtilityLog.dynamicNameAppender();
		String cfName2 = "codingForm2" + UtilityLog.dynamicNameAppender();
		securityGroupsPage = new SecurityGroupsPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Create security groups and assign");
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(sgName1);
		securityGroupsPage.AddSecurityGroup(sgName2);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkReleaseToMultipleSecurityGroups(sgName1, sgName2);
		docViewRedact.assignAccesstoSGs(sgName1, Input.rmu1userName);
		docViewRedact.assignAccesstoSGs(sgName1, Input.rev1userName);
		driver.waitForPageToBeReady();
		docViewRedact.assignAccesstoSGs(sgName2, Input.rmu6userName);
		docViewRedact.assignAccesstoSGs(sgName2, Input.rev1userName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("create assignment and tag for security group 1");
		assignment.createTagOrFolderCodingFormAssignment(sgName1, cfName1, assignmentName1, Input.testData1, "tag",
				tagName1);
		baseClass.stepInfo("get tags in popup available in security group 1");
		assignment.verifyTagOrFolderAvailabilityInAssignment(sgName1, assignmentName1, Input.rev1userName, "tag");
		if (!sessionSearch.getExistingTagSelectionCheckBox(tagName2).isElementAvailable(10)
				&& sessionSearch.getExistingTagSelectionCheckBox(tagName1).isElementAvailable(10)) {
			baseClass.passedStep("Tags are securityGroup specific");
		} else {
			baseClass.failedStep("Tags are not securityGroup specific");
		}
		driver.Navigate().refresh();
		baseClass.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu6userName, Input.rmu6password);
		assignment.createTagOrFolderCodingFormAssignment(sgName2, cfName2, assignmentName2, Input.testData1, "tag",
				tagName2);
		assignment.verifyTagOrFolderAvailabilityInAssignment(sgName2, assignmentName2, Input.rev1userName, "tag");
		if (!sessionSearch.getExistingTagSelectionCheckBox(tagName1).isElementAvailable(10)
				&& sessionSearch.getExistingTagSelectionCheckBox(tagName2).isElementAvailable(10)) {
			baseClass.passedStep("Tags are securityGroup specific");
		} else {
			baseClass.failedStep("Tags are not securityGroup specific");
		}
		driver.Navigate().refresh();
		baseClass.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();
		// Delete security group
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		if (!sgName1.equalsIgnoreCase(Input.securityGroup) && !sgName2.equalsIgnoreCase(Input.securityGroup)) {
			securityGroupsPage.deleteSecurityGroups(sgName1);
			driver.waitForPageToBeReady();
			securityGroupsPage.deleteSecurityGroups(sgName2);
		}
		loginPage.logout();

	}

	/**
	 * Author :Arunkumar date: 05/08/2022 TestCase Id:RPMXCON-54034 Description :To
	 * verify that Folders displayed in Folders pop up are security group specific
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54034", enabled = true, groups = { "regression" })
	public void verifyFoldersAreSecurityGroupSpecific() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-54034");
		baseClass.stepInfo("To verify that Folders displayed in Folders pop up are security group specific.");
		String sgName1 = "Security Group1_" + UtilityLog.dynamicNameAppender();
		String sgName2 = "Security Group2_" + UtilityLog.dynamicNameAppender();
		String folderName1 = "securityGroup1_Folder" + UtilityLog.dynamicNameAppender();
		String folderName2 = "securityGroup2_Folder" + UtilityLog.dynamicNameAppender();
		String assignmentName1 = "assignment1" + UtilityLog.dynamicNameAppender();
		String assignmentName2 = "assignment2" + UtilityLog.dynamicNameAppender();
		String cfName1 = "codingForm1" + UtilityLog.dynamicNameAppender();
		String cfName2 = "codingForm2" + UtilityLog.dynamicNameAppender();
		securityGroupsPage = new SecurityGroupsPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Create security groups and assign");
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(sgName1);
		securityGroupsPage.AddSecurityGroup(sgName2);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkReleaseToMultipleSecurityGroups(sgName1, sgName2);
		docViewRedact.assignAccesstoSGs(sgName1, Input.rmu1userName);
		docViewRedact.assignAccesstoSGs(sgName1, Input.rev1userName);
		driver.waitForPageToBeReady();
		docViewRedact.assignAccesstoSGs(sgName2, Input.rmu6userName);
		docViewRedact.assignAccesstoSGs(sgName2, Input.rev1userName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("create assignment and tag for security group 1");
		assignment.createTagOrFolderCodingFormAssignment(sgName1, cfName1, assignmentName1, Input.testData1, "folder",
				folderName1);
		baseClass.stepInfo("get tags in popup available in security group 1");
		assignment.verifyTagOrFolderAvailabilityInAssignment(sgName1, assignmentName1, Input.rev1userName, "folder");
		if (!sessionSearch.getExistingFolderSelectionCheckBox(folderName2).isElementAvailable(5)
				&& sessionSearch.getExistingFolderSelectionCheckBox(folderName1).isElementAvailable(5)) {
			baseClass.passedStep("Folders are securityGroup specific");
		} else {
			baseClass.failedStep("Folders are not securityGroup specific");
		}
		driver.Navigate().refresh();
		baseClass.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu6userName, Input.rmu6password);
		assignment.createTagOrFolderCodingFormAssignment(sgName2, cfName2, assignmentName2, Input.testData1, "folder",
				folderName2);
		assignment.verifyTagOrFolderAvailabilityInAssignment(sgName2, assignmentName2, Input.rev1userName, "folder");
		if (!sessionSearch.getExistingFolderSelectionCheckBox(folderName1).isElementAvailable(5)
				&& sessionSearch.getExistingFolderSelectionCheckBox(folderName2).isElementAvailable(5)) {
			baseClass.passedStep("Folders are securityGroup specific");
		} else {
			baseClass.failedStep("Folders are not securityGroup specific");
		}
		driver.Navigate().refresh();
		baseClass.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();
		// Delete security group
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		if (!sgName1.equalsIgnoreCase(Input.securityGroup) && !sgName2.equalsIgnoreCase(Input.securityGroup)) {
			securityGroupsPage.deleteSecurityGroups(sgName1);
			driver.waitForPageToBeReady();
			securityGroupsPage.deleteSecurityGroups(sgName2);
		}
		loginPage.logout();

	}

	/**
	 * @author
	 * @throws InterruptedException
	 * @Date: 07/13/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : To verify that RMU is able to view Assingment Group and
	 *              Assignments tree in Assign/Unassign Document pop up..
	 *              RPMXCON-53633
	 */
	@Test(description = "RPMXCON-53633", enabled = true, groups = { "regression" })
	public void verifyRmuAbleToViewAssignmentGroupAndAssignmentTreeInAssignOrUnAssignDocumentPopUp() {

		baseClass.stepInfo("Test case Id: RPMXCON-53633");
		baseClass.stepInfo(
				"To verify that RMU is able to view Assingment Group and Assignments tree in Assign/Unassign Document pop up.");

		// Login as Reviewer Manager
		baseClass.stepInfo(
				"1. Login to the application as RM User.  2. Goto Search --> New Search.  3. Enter text to search and click on Search.  4. Drag the Tile under Selected Results and from drop down select Bulk Assign.");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// performing basic search
		sessionSearch.basicContentSearch(Input.searchString1);

		// performing bulk assign action
		sessionSearch.bulkAssign();

		// verifying the presence of Assign/Unassigned pop heading
		baseClass.ValidateElement_Presence(sessionSearch.getbulkassgnpopup(), "Assign/Unassigned pop");

		// verifying whether the Assign Documents is selected by Default
		String bgColorRbg = sessionSearch.getBulkAssignAssignDocumentsButton().GetCssValue("border-color");
		String bgColorHexa = baseClass.rgbTohexaConvertorCustomized(bgColorRbg, 4);
		System.out.println(bgColorHexa);
		baseClass.textCompareEquals(bgColorHexa, Input.progresBarColor,
				" Assign Document option is selected by default", " Assign Document option is not selected by default");

		// verifying whether the Existing Assignment Tab is selected by Default
		baseClass.printResutInReport(
				baseClass.ValidateElement_PresenceReturn(sessionSearch.getBulkAssignSelectedExistingAssignment()),
				"Existing Assignment tab is selected by Default.",
				"Existing Assignment tab is not selected by Default.", "Pass");

		// verifying the presence of tree of Assignment Group and Assignments
		baseClass.ValidateElementCollection_Presence(assignment.getSelectcopyAssgnmToBulkAssign(),
				"Tree of Assignment Group and Assignments");

		// logout
		loginPage.logout();
	}

	/**
	 * @author Jeevitha
	 * @throws InterruptedException
	 * @Date: 07/13/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : To verify that only assigned documents are displayed in Mini
	 *              DocList. RPMXCON-54045
	 */
	@Test(description = "RPMXCON-54045", enabled = true, groups = { "regression" })
	public void verifyThatOnlyAssignedDocumentsDisplayedInMiniDocList() throws InterruptedException {

		String assgngrpName = "assgnGrp" + Utility.dynamicNameAppender();
		String assignmentName = "assignment" + Utility.dynamicNameAppender();
		baseClass.stepInfo("Test case Id: RPMXCON-54045");
		baseClass.stepInfo("To verify that only assigned documents are displayed in Mini DocList");

		// Login as Reviewer Manager
		baseClass.stepInfo("**Step-1 Login to RPMX as RMU**");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// creating assignment Group
		baseClass.stepInfo("**Step-2 & 3 Go to Manage --> Assignment and Select Assignment Group from tree view**");
		assignment.navigateToAssignmentsPage();
		assignment.createCascadeAssgnGroupWithSortBymetadata(assgngrpName);
		baseClass.stepInfo("Assignment Group is Created");

		// creating assignment under newly created Assignment group
		assignment.selectAssignmentGroup(assgngrpName);
		assignment.createAssignmentFromAssgnGroup(assignmentName, Input.codeFormName);

		// assign documents to created assignment
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assignmentName);

		// assigning reviewers to the Assignment and distributing the documents to the
		// reviewers
		baseClass.stepInfo(
				"**Step-4 Select Assignment from grid view which has documents and Reviewers assigned.    Select Edit Assignment from Action Dropdown**");
		assignment.selectAssignmentGroup(assgngrpName);
		assignment.getSelectAssignment(assignmentName).waitAndClick(5);
		assignment.getAssignmentActionDropdown().waitAndClick(10);
		assignment.assignmentActions("Edit");

		baseClass.stepInfo("**Step-5 Select Manage Reviewer tab**");
		assignment.add2ReviewerAndDistribute(Input.rev1userName,Input.rmu1userName);
		baseClass.passedStep("reviewers assigned to the Assignment and documents distributed to the reviewers");

		// verifying the count of document assigned to the reviewer with count of
		// documents listed in mini docList
		baseClass.stepInfo(
				"**Step-6 Select Reviewers from Grid View which has documents assigned.    From Action dropdown select View All Docs in DocView.    Verify that only assigned documents to reviewer should be displayed in Mini DocList.**");
		assignment.Assignment_ManageRevtab_ViewinDocView();
		baseClass.passedStep(
				"verified that count of documents assigned to reviewer match with count of documents listed in Mini DocList");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description Verifying the Live sequence in Assignment group and Assignment
	 * @throws InterruptedException
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test(description = "RPMXCON-49088", enabled = true, groups = { "regression" })
	public void verifyCascadedLiveSequenceOrderChangesInAssignment()
			throws InterruptedException, ParseException, IOException {
		baseClass.stepInfo("Verifying the Live sequence in Assignment group and Assignment");
		baseClass.stepInfo("Test case Id:RPMXCON-49088");
		String assignmentGroup = "assgnGrp" + Utility.dynamicNameAppender();
		SoftAssert sa = new SoftAssert();
		List<String> ListeBeforAlteredInAssgnGrp = new ArrayList<>();
		List<String> ListeAfterAlteredInAssgnGrp = new ArrayList<>();
		List<String> ListInAssgnmnt = new ArrayList<>();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignment.createCascadeNonCascadeAssgnGroup_withoutSave(assignmentGroup, "Yes");
		ListeBeforAlteredInAssgnGrp = baseClass.getAvailableListofElements(assignment.getLiveSequenceMetadatas());
		assignment.dragAndDropLiveSequenceFromTopToBottom(" Email Threads", " DocID");
		ListeAfterAlteredInAssgnGrp = baseClass.getAvailableListofElements(assignment.getLiveSequenceMetadatas());
		sa.assertNotEquals(ListeBeforAlteredInAssgnGrp, ListeAfterAlteredInAssgnGrp);
		baseClass.waitTillElemetToBeClickable(assignment.SaveButton());
		assignment.SaveButton().waitAndClick(10);
		assignment.selectAssignmentGroup(assignmentGroup);
		baseClass.waitForElement(assignment.getAssignmentActionDropdown());
		assignment.getAssignmentActionDropdown().waitAndClick(10);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(assignment.getAssignmentAction_NewAssignment());
		baseClass.waitTillElemetToBeClickable(assignment.getAssignmentAction_NewAssignment());
		assignment.getAssignmentAction_NewAssignment().waitAndClick(20);
		ListInAssgnmnt = baseClass.getAvailableListofElements(assignment.getLiveSequenceMetadatas());
		sa.assertEquals(ListeAfterAlteredInAssgnGrp, ListInAssgnmnt);
		sa.assertAll();
		baseClass.passedStep("The order of live sequence cascadedly reflected in assignment page");
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignment.DeleteAssgnGroup(assignmentGroup);
		loginPage.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description To verify that validations are displayed if Mandatory fields are
	 *              not entered on \"New Assignment\" page
	 * @throws InterruptedException
	 * @throws ParseException-
	 * @throws IOException
	 */
	@Test(description = "RPMXCON-53603", enabled = true, groups = { "regression" })
	public void verifyMandatoryFieldValidations() throws InterruptedException, ParseException, IOException {
		baseClass.stepInfo(
				"To verify that validations are displayed if Mandatory fields are not entered on \"New Assignment\" page");
		baseClass.stepInfo("Test case Id:RPMXCON-53603");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		baseClass.waitForElement(assignment.getAssignmentActionDropdown());
		assignment.getAssignmentActionDropdown().waitAndClick(10);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(assignment.getAssignmentAction_NewAssignment());
		baseClass.waitTillElemetToBeClickable(assignment.getAssignmentAction_NewAssignment());
		assignment.getAssignmentAction_NewAssignment().waitAndClick(20);
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(assignment.SaveButton());
		assignment.SaveButton().waitAndClick(10);
		assignment.validateErrorMessage(assignment.getQB_AssignemntName_ErrorMSg(), "Name required");
		assignment.validateErrorMessage(assignment.getCodingForm_AssignemntName_ErrorMSg(),
				"Please select Coding Form");
		loginPage.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description To verify that alphanumeric are accepted in "Assignment Group
	 *              Name" and "Assignment Name" fields
	 * @throws InterruptedException
	 * @throws ParseException-
	 * @throws IOException
	 */
	@Test(description = "RPMXCON-53592", enabled = true, groups = { "regression" })
	public void verifyAgnmtNameAcceptsAlphaNumericValues() throws InterruptedException, ParseException, IOException {
		baseClass.stepInfo(
				"To verify that alphanumeric are accepted in \"Assignment Group Name\" and \"Assignment Name\" fields");
		baseClass.stepInfo("Test case Id:RPMXCON-53592");
		ArrayList<String> splCharacters = new ArrayList<String>(
				Arrays.asList("!", "@", "#", "$", "%", "^", "&", "*", "(", ")"));
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		String assignmentGroup1 = "agnmntGrp" + Utility.dynamicNameAppender();
		String assignment1 = "agnmnt" + Utility.dynamicNameAppender();
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignment.createCascadeNonCascadeAssgnGroup(assignmentGroup1, "Yes");
		if (assignment.getAssgnGrp_Select(assignmentGroup1).isElementAvailable(5)) {
			baseClass
					.passedStep("The assignment group added with alpha numeric naming convention created successfully");
		} else {
			baseClass.failedStep("The assignment group doesn't accept alpha numeric values");
		}
		baseClass.stepInfo("Successfully verifed the assignment group accepted the alpha numeric values");
		for (String value : splCharacters) {
			String assignmentGroup = "agnmntGrp" + value;
			this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
			assignment.createCascadeNonCascadeAssgnGroup_withoutSave(assignmentGroup, "Yes");
			assignment.validateErrorMessage(assignment.getQB_AssignemntName_ErrorMSg(),
					"Please enter an assignment name without using special characters");
		}
		baseClass.stepInfo("Successfully verifed the assignment group not accepting the special characters");
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignment.createAssignment(assignment1, Input.codingFormName);
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignment.selectAssignmentToView(assignment1);
		if (assignment.getSelectAssignment(assignment1).isElementAvailable(5)) {
			baseClass.passedStep("The assignment added with alpha numeric naming convention created successfully");
		} else {
			baseClass.failedStep("The assignment name doesn't accept alpha numeric values");
		}
		baseClass.stepInfo("Successfully verifed the assignment name accepted the alpha numeric values");
		for (String value : splCharacters) {
			String assignment2 = "agnmnt" + value;
			this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
			assignment.createAssignment_withoutSave(assignment2, Input.codingFormName);
			assignment.validateErrorMessage(assignment.getQB_AssignemntName_ErrorMSg(),
					"Please enter an assignment name without using special characters");
		}
		assignment.deleteAssignment(assignment1);
		baseClass.waitTime(3);
		assignment.DeleteAssgnGroup(assignmentGroup1);
		loginPage.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description To verify that RMU can delete the assignment to which documents
	 *              are associated.
	 * @throws InterruptedException
	 * @throws ParseException-
	 * @throws IOException
	 */
	@Test(description = "RPMXCON-53609", enabled = true, groups = { "regression" })
	public void verifyRMUDeletesAssgnmnt() throws InterruptedException, ParseException, IOException {
		baseClass.stepInfo("To verify that RMU can delete the assignment to which documents are associated.");
		baseClass.stepInfo("Test case Id:RPMXCON-53609");
		SessionSearch search = new SessionSearch(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();
		assignment.createAssignment(assignmentName, Input.codeFormName);
		search.basicContentSearch(Input.TallySearch);
		search.bulkAssignExisting(assignmentName);
		assignment.editAssignmentUsingPaginationConcept(assignmentName);
		assignment.add3ReviewerAndDistribute();
		this.driver.getWebDriver().get(Input.url + "/Dashboard/Dashboard");
		baseClass.waitTillElemetToBeClickable(assignment.getAssignmentsInreviewerPg());
		assignment.getAssignmentsInreviewerPg().waitAndClick(10);
		baseClass.waitForElement(assignment.getAssignmentsCompletedCountInreviewerPg(assignmentName));
		String docs = assignment.getAssignmentsCompletedCountInreviewerPg(assignmentName).getText();
		int count = Integer.parseInt(docs);
		if (count >= 0) {
			baseClass.passedStep("Completed coloumn value shows only numeric values");
		} else {
			baseClass.failedStep("Completed coloumn value not displayed the values as expected");
		}
		assignment.deleteSelectedAgnmt(assignmentName);

	}

	/**
	 * @author Jayanthi.Ganesan
	 * @throws InterruptedException
	 * @Description :To verify that validation is displayed if there is zero
	 *              documents assigned and RMU selects View All Docs In DocList.
	 */
	@Test(description = "RPMXCON-54050", enabled = true, groups = { "regression" })
	public void verifyValidations_ViewInDocList() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-54050");
		baseClass.stepInfo("To verify that validation is displayed if there is zero documents "
				+ "assigned and RMU selects View All Docs In DocList.");
		String assgnName = "Assgn" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// performing basic search
		sessionSearch.basicContentSearch(Input.searchString1);

		// performing bulk assign action
		sessionSearch.bulkAssign();
		assignment.assignmentCreationAsPerCf(assgnName, Input.codeFormName);
		baseClass.stepInfo("Created Assignment and bulk assigned docs with name  -" + assgnName);
		String userToAdd[] = { Input.rev1userName };
		assignment.assignReviewers(userToAdd);

		assignment.getAssignment_ManageReviewersTab().waitAndClick(10);
		assignment.getAssgn_ManageRev_selectReviewer(Input.rev1userName).waitAndClick(10);
		assignment.selectActionsInManageRev(assignment.getAssignmentActionManageTab_ViewinDocList());

		baseClass.VerifyWarningMessage("No documents available to do the selected action");
		baseClass.stepInfo("If there is zero documents "
				+ "assigned and RMU selects View All Docs In DocList  validation is displayed and verified.");
		assignment.deleteAssgnmntUsingPagination(assgnName);
		// logout
		loginPage.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws AWTException
	 * @description: To verify that RMU is able to add Add Reviewers pop on click of
	 *               Add Reviewer button
	 */
	@Test(description = "RPMXCON-53621", enabled = true, groups = { "regression" })
	public void verifyRmuAbleToAddReviewersAndClosePopup() throws InterruptedException, AWTException {
		baseClass.stepInfo("To verify that RMU is able to add Add Reviewers pop on click of Add Reviewer button");
		baseClass.stepInfo("Test case Id: RPMXCON-53621");
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignment.createAssignment(assignmentName, Input.codeFormName);
		assignment.editAssignmentUsingPaginationConcept(assignmentName);
		String userToAdd[] = { Input.rev1userName };
		assignment.assignReviewers(userToAdd);
		driver.waitForPageToBeReady();
		assignment.getAssignment_ManageReviewersTab().waitAndClick(10);
		if (assignment.getAssgn_ManageRev_selectReviewer(Input.rev1userName).isElementAvailable(5)) {
			baseClass.passedStep("Selected reviewr from reviewer popup displayed in manage reviewers table");
		} else {
			baseClass.failedStep("Selected reviewr from reviewer popup not displayed in manage reviewers table");
		}
		assignment.verifyUserInReviewerTab(Input.rmu1userName);
		assignment.deleteAssgnmntUsingPagination(assignmentName);
		loginPage.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws AWTException
	 * @description: To verify that in "DOCUMENT PRESENTATION SEQUENCE" different
	 *               sequence options are displayed as per selection of tab by RMU.
	 */
	@Test(description = "RPMXCON-53599", enabled = true, groups = { "regression" })
	public void verifySequeceOptionsDisplayedAsPerSelection() throws InterruptedException, AWTException {
		baseClass.stepInfo(
				"To verify that in \"DOCUMENT PRESENTATION SEQUENCE\" different sequence options are displayed as per selection of tab by RMU.");
		baseClass.stepInfo("Test case Id: RPMXCON-53599");
		SoftAssert sa = new SoftAssert();
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		ArrayList<String> expectedLiveSequenceOrder = new ArrayList<String>(
				Arrays.asList("Email Threads", "Family Members", "Near Duplicate Docs", "DocID"));
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignment.createAssignment_withoutSave(assignmentName, Input.codeFormName);
		sa.assertEquals(assignment.getOptimizedSort().GetAttribute("aria-expanded"), "true",
				"Category tab is not selected as default");
		baseClass.passedStep("DOCUMENT PRESENTATION SEQUENCE default tab is selected as \"Category\" as expected");
		List<String> actualLiveSequenceOrder = baseClass
				.getAvailableListofElements(assignment.getLiveSequenceMetadatas());
		sa.assertEquals(expectedLiveSequenceOrder, actualLiveSequenceOrder,
				"The order of the live sequence are not displayed as expected");
		baseClass.passedStep("The order of the live sequence are displayed as expected");
		assignment.sortBymetaDataBtn().waitAndClick(5);
		baseClass.waitTillElemetToBeClickable(assignment.SelectMetadata());
		assignment.SelectMetadata().waitAndClick(3);
		if (assignment.SelectMetadataFromDropDown("DocFileName").isElementAvailable(5)) {
			baseClass.passedStep("Metadatas are displayed in the dropdown as expected");
		} else {
			baseClass.failedStep("Metadatas are not displayed in the dropdown as expected");
		}
		sa.assertAll();
		loginPage.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description Verify the Default live sequence while creating the new
	 *              assignment through bulk actions
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-49087", enabled = true, groups = { "regression" })
	public void verifyDefLivSeqinAssignCrtThrBulkAction() throws InterruptedException {
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		SessionSearch search = new SessionSearch(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-49087 Assignments");
		baseClass.stepInfo("Verify the Default live sequence while creating the new assignment through bulk actions");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged In As : " + Input.rmu1userName);
		search.basicContentSearch(Input.searchString1);
		search.bulkAssignWithNewAssignment();

		assignment.createAssignment_withoutSave(assignmentName, Input.codeFormName);
		assignment.VerifyLiveSequence();
		baseClass.stepInfo(
				"The live sequence order Displayed Email, then Family Members, then Near Dupes and then DocID "
						+ "In New Assignment Page");

		driver.scrollPageToTop();
		baseClass.waitForElement(assignment.getAssignmentSaveButton());
		assignment.getAssignmentSaveButton().waitAndClick(5);
		baseClass.stepInfo("Assignment Created Through Bulk Action");

		assignment.editAssignmentUsingPaginationConcept(assignmentName);
		baseClass.stepInfo("Assignment Opened In Edit Assignment Page");
		assignment.VerifyLiveSequence();
		baseClass.stepInfo("The Live Sequence Order Displays As We Expected In Edit Assignment Page");

		assignment.deleteAssgnmntUsingPagination(assignmentName);
		baseClass.passedStep(
				"Successfully Verified That - the Default live sequence while creating the new assignment through bulk actions");
		loginPage.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description Verify that in DOCUMENT PRESENTATION SEQUENCE RMU is able to
	 *              drag and drop the sequencing options in Category tab
	 * @throws InterruptedException
	 * @throws ParseException
	 * @throws IOException
	 */
	@Test(description = "RPMXCON-53745", enabled = true, groups = { "regression" })
	public void verifyRMUCanDDSeqOptionsInCategoryTab() throws InterruptedException, ParseException, IOException {
		String assgnGrp = "AR3assignGrp" + Utility.dynamicNameAppender();

		AssignmentsPage agnmt = new AssignmentsPage(driver);
		BaseClass baseClass = new BaseClass(driver);
		LoginPage loginPage = new LoginPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-53745 Assignments");
		baseClass.stepInfo(
				"Verify that in DOCUMENT PRESENTATION SEQUENCE  RMU is able to drag and drop the sequencing options in Category tab");

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged In As : " + Input.rmu1userName);
		assignment.navigateToAssignmentsPage();
		assignment.createCascadeNonCascadeAssgnGroup_withoutSave(assgnGrp, "No");
		driver.scrollingToElementofAPage(assignment.CatogeryTabClicked());
		baseClass.ValidateElement_Presence(assignment.CatogeryTabClicked(), "Category Tab is selected as Default");
		baseClass.ValidateElement_Presence(assignment.getAssgn_DocSequence_SortbyMetadata(), "Sort By MetaData Tab");
		baseClass.stepInfo(
				"Verified that DOCUMENT PRESENTATION SEQUENCE default tab is selected as Category and other tab Sort By Metadata are also available.");
		driver.scrollingToBottomofAPage();

		assignment.dragAndDropLiveSequence(" Email Threads");
		baseClass.ValidateElement_Presence(assignment.getunderAvailableCriteria(" Email Threads"), "Email Threads");
		baseClass.stepInfo("Email Threads Successfully Dragged From Live Sequence to Available Criteria");
		assignment.dragAndDropLiveSeqfrmAvailableCrit(" Email Threads");
		baseClass.ValidateElement_Presence(assignment.liveSequenceSorts(" Email Threads"), "Email Threads");
		baseClass.stepInfo("Email Threads Successfully Dragged From Available Criteria to Live Sequence");

		assignment.dragAndDropLiveSequence(" Family Members");
		baseClass.ValidateElement_Presence(assignment.getunderAvailableCriteria(" Family Members"), "Family Members");
		baseClass.stepInfo("Family Members Successfully Dragged From Live Sequence to Available Criteria");
		assignment.dragAndDropLiveSeqfrmAvailableCrit(" Family Members");
		baseClass.ValidateElement_Presence(assignment.liveSequenceSorts(" Family Members"), "Family Members");
		baseClass.stepInfo("Email Threads Successfully Dragged From Available Criteriae to Live Sequence");

		assignment.dragAndDropLiveSequence(" Near Duplicate Docs");
		baseClass.ValidateElement_Presence(assignment.getunderAvailableCriteria(" Near Duplicate Docs"),
				"Near Duplicate Docs");
		baseClass.stepInfo("Near Duplicate Docs Successfully Dragged From Live Sequence to Available Criteria");
		assignment.dragAndDropLiveSeqfrmAvailableCrit(" Near Duplicate Docs");
		baseClass.ValidateElement_Presence(assignment.liveSequenceSorts(" Near Duplicate Docs"), "Near Duplicate Docs");
		baseClass.stepInfo("Near Duplicate Docs Successfully Dragged From Available Criteriae to Live Sequence");

		assignment.dragAndDropLiveSequence(" DocID");
		baseClass.ValidateElement_Presence(assignment.getunderAvailableCriteria(" DocID"), "DocID");
		baseClass.stepInfo("DocID Successfully Dragged From Live Sequence to Available Criteria");
		assignment.dragAndDropLiveSeqfrmAvailableCrit(" DocID");
		baseClass.ValidateElement_Presence(assignment.liveSequenceSorts(" DocID"), "DocID");
		baseClass.stepInfo("DocID Successfully Dragged From Available Criteriae to Live Sequence");

		baseClass.passedStep(
				"Verified that - in DOCUMENT PRESENTATION SEQUENCE  RMU is able to drag and drop the sequencing options in Category tab");
		loginPage.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description Verify that assignments functionality is working correctly and
	 *              Properly
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54271", enabled = true, groups = { "regression" })
	public void verifyAssignFunctionalityWorkingProperly() throws Exception {
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		String keywordName = "key" + Utility.dynamicNameAppender();
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		BaseClass baseClass = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		KeywordPage keyword = new KeywordPage(driver);

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-54271");
		baseClass.stepInfo("Verify that assignments functionality is working correctly and Properly");
		baseClass.stepInfo("Logged In As : " + Input.rmu1userName);
		keyword.navigateToKeywordPage();
		keyword.addKeywordWithColor(keywordName, "Blue");

		assignment.createAssignment_withoutSave(assignmentName, Input.codeFormName);

		baseClass.waitForElement(assignment.getAssgnGrp_Create_DrawPoolCount());
		assignment.getAssgnGrp_Create_DrawPoolCount().SendKeys("2500");

		assignment.configInstructionKeywordMDFieldinAssignPage(Input.searchString1, Input.metaDataName);
		assignment.enableAllToogleUnderPresentationControl();
		baseClass.stepInfo(
				"All the toggles under 'CONTROL THE PRESENTATION OF DOCVIEW FOR REVIEWERS WHILE IN THIS ASSIGNMENT' are Enabled and Green in Color");
		System.out.println(
				"All the toggles under 'CONTROL THE PRESENTATION OF DOCVIEW FOR REVIEWERS WHILE IN THIS ASSIGNMENT' are Enabled and Green in Color");
		baseClass.waitTime(2);
		driver.scrollPageToTop();
		baseClass.waitForElement(assignment.getAssignmentSaveButton());
		assignment.getAssignmentSaveButton().Click();

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assignmentName);
		baseClass.stepInfo("Documents Assigned To Assignments Successfully");

		assignment.editAssignmentUsingPaginationConcept(assignmentName);
		assignment.distributeTheGivenDocCountToReviewer("10",Input.rev1userName);
		baseClass.waitForElement(assignment.getAssignmentSaveButton());
		assignment.getAssignmentSaveButton().Click();
		baseClass.VerifySuccessMessage("Assignment updated successfully");

		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in as reviewer user");
		DocViewPage docViewPage = new DocViewPage(driver);
		docViewPage.selectAssignmentfromDashborad(assignmentName);
		baseClass.stepInfo("Document Successfully Distributed to the User");
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignment.deleteAssgnmntUsingPagination(assignmentName);
		baseClass.passedStep("Verified That - Assignments Functionality is Working Correctly and Properly");
		loginPage.logout();
	}

	/**
	 * @author Krishna
	 * @description Verify that - After impersonation (Project Admin to RMU)-
	 *              Application disallow special characters in New Assignments
	 *              screen.
	 * @throws Exception
	 */

	@Test(description = "RPMXCON-54437", groups = { "regression" })
	public void verifyErrorMsgForSpclCharNewAssignScrPAtoRMU() throws InterruptedException {

		String dataSet[][] = { { "AssignmentNameWith<test" }, { "AssignmentNameWith(test" },
				{ "AssignmentNameWith)test" }, { "AssignmentNameWith[test" }, { "AssignmentNameWith]test" },
				{ "AssignmentNameWith{test" }, { "AssignmentNameWith}test" }, { "AssignmentNameWith:test" },
				{ "AssignmentNameWith\"test" }, { "AssignmentNameWith'test" }, { "AssignmentNameWith~test" },
				{ "AssignmentNameWith*test" }, { "AssignmentNameWith?test" }, { "AssignmentNameWith&test" },
				{ "AssignmentNameWith$test" }, { "AssignmentNameWith#test" }, { "AssignmentNameWith@test" },
				{ "AssignmentNameWith!test" }, { "AssignmentNameWith-test" }, { "AssignmentNameWith_test" } };

		LoginPage loginPage = new LoginPage(driver);
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		BaseClass baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-54437");
		baseClass.stepInfo(
				"Verify that - After impersonation (Project Admin to RMU)- Application disallow special characters in New Assignments screen.");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged In As :" + Input.pa1userName);
		baseClass.impersonatePAtoRMU();

		assignment.navigateToAssignmentsPage();
		for (int i = 0; i < dataSet.length; i++) {
			int j = 0;

			String renameAssign = dataSet[i][j];
			assignment.selectAssignmentGroup("Root");
			assignment.NavigateToNewEditAssignmentPage("New");
			baseClass.waitForElement(assignment.getAssignmentName());
			assignment.getAssignmentName().SendKeys(renameAssign);
			assignment.getAssignmentSaveButton().waitAndClick(5);
			assignment.verifyErrorMsginAssignmentName();
		}
		baseClass.passedStep(
				"Verified - that After impersonation (Project Admin to RMU)- Application disallow special characters in New Assignments screen");
		loginPage.logout();
	}

	/**
	 * @author Krishna
	 * @description Verify that - After impersonation (Project Admin to RMU)-
	 *              Application disallow special characters in New Assignments
	 *              screen.
	 * @throws Exception
	 */

	@Test(description = "RPMXCON-54438", groups = { "regression" })
	public void verifyErrorMsgForSpclCharEditAssignScrPAtoRMU() throws InterruptedException {

		String dataSet[][] = { { "AssignmentNameWith<test" }, { "AssignmentNameWith(test" },
				{ "AssignmentNameWith)test" }, { "AssignmentNameWith[test" }, { "AssignmentNameWith]test" },
				{ "AssignmentNameWith{test" }, { "AssignmentNameWith}test" }, { "AssignmentNameWith:test" },
				{ "AssignmentNameWith\"test" }, { "AssignmentNameWith'test" }, { "AssignmentNameWith~test" },
				{ "AssignmentNameWith*test" }, { "AssignmentNameWith?test" }, { "AssignmentNameWith&test" },
				{ "AssignmentNameWith$test" }, { "AssignmentNameWith#test" }, { "AssignmentNameWith@test" },
				{ "AssignmentNameWith!test" }, { "AssignmentNameWith-test" }, { "AssignmentNameWith_test" } };

		String assignmentName = "assignment" + Utility.dynamicNameAppender();

		LoginPage loginPage = new LoginPage(driver);
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		BaseClass baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-54438");
		baseClass.stepInfo(
				"Verify that - After impersonation (Project Admin to RMU)- Application disallow special characters in Edit Assignments screen");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged In As :" + Input.rmu1userName);

		assignment.createAssignment(assignmentName, Input.codingFormName);
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged In As :" + Input.pa1userName);
		baseClass.impersonatePAtoRMU();

		assignment.navigateToAssignmentsPage();
		assignment.editAssignment(assignmentName);

		for (int i = 0; i < dataSet.length; i++) {
			int j = 0;

			String renameAssign = dataSet[i][j];
			baseClass.waitForElement(assignment.getAssignmentName());
			assignment.getAssignmentName().SendKeys(renameAssign);
			driver.scrollPageToTop();
			baseClass.waitForElement(assignment.getAssignmentSaveButton());
			assignment.getAssignmentSaveButton().waitAndClick(5);
			assignment.verifyErrorMsginAssignmentName();
		}
		baseClass.passedStep(
				"Verified - that After impersonation (Project Admin to RMU)- Application disallow special characters in Edit Assignments screen");
		loginPage.logout();
	}

	/**
	 * @author Krishna
	 * @description Verify that Application disallow special characters in New
	 *              Assignments screen when user performed COPY and PASTE (Special
	 *              Characters) from Notepad.
	 * @throws Exception
	 */

	@Test(description = "RPMXCON-54439", groups = { "regression" })
	public void verifyErrorMsgForSpclCharNewAssignScr() throws InterruptedException {

		String dataSet[][] = { { "AssignmentNameWith<test" }, { "AssignmentNameWith(test" },
				{ "AssignmentNameWith)test" }, { "AssignmentNameWith[test" }, { "AssignmentNameWith]test" },
				{ "AssignmentNameWith{test" }, { "AssignmentNameWith}test" }, { "AssignmentNameWith:test" },
				{ "AssignmentNameWith\"test" }, { "AssignmentNameWith'test" }, { "AssignmentNameWith~test" },
				{ "AssignmentNameWith*test" }, { "AssignmentNameWith?test" }, { "AssignmentNameWith&test" },
				{ "AssignmentNameWith$test" }, { "AssignmentNameWith#test" }, { "AssignmentNameWith@test" },
				{ "AssignmentNameWith!test" }, { "AssignmentNameWith-test" }, { "AssignmentNameWith_test" } };

		LoginPage loginPage = new LoginPage(driver);
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		BaseClass baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-54439");
		baseClass.stepInfo(
				"Verify that Application disallow special characters in New Assignments screen when user performed COPY and PASTE (Special Characters) from Notepad.");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged In As : " + Input.rmu1userName);

		assignment.navigateToAssignmentsPage();
		for (int i = 0; i < dataSet.length; i++) {
			int j = 0;

			String renameAssign = dataSet[i][j];
			assignment.selectAssignmentGroup("Root");
			assignment.NavigateToNewEditAssignmentPage("New");
			baseClass.copyandPasteString(renameAssign, assignment.getAssignmentName());
			assignment.getAssignmentSaveButton().waitAndClick(5);
			assignment.verifyErrorMsginAssignmentName();
		}
		baseClass.passedStep(
				"Verified - that Application disallow special characters in New Assignments screen when user performed COPY and PASTE (Special Characters) from Notepad.");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-54495
	 * @Description:To Verify 'Keep families together' field enabled/disabled
	 **/
	@Test(description = "RPMXCON-54495", enabled = true, groups = { "regression" })
	public void verifykeepFamilyTogether() throws Exception {
		AssignmentsPage assignment = new AssignmentsPage(driver);
		SoftAssert asserts = new SoftAssert();

		String assignmentGRP = "AssigGRP" + Utility.dynamicNameAppender();

		baseClass.stepInfo("RPMXCON-54495");
		baseClass.stepInfo("To Verify 'Keep families together' field enabled/disabled");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("logged in As : " + Input.rmu1userName);

		assignment.navigateToAssignmentsPage();
		driver.waitForPageToBeReady();
		assignment.createCascadeNonCascadeAssgnGroup_withoutSave(assignmentGRP, "yes");
		assignment.toggleEnableOrDisableOfAssignPage(true, false, assignment.getAssgnGrp_Create_DrawPooltoggle(),
				"Draw From Pool", true);

		assignment.editAssgnGrp(assignmentGRP, "No");
		driver.waitForPageToBeReady();
		assignment.checkForToggleEnable(assignment.getAssgn_keepFamiliesTogetherToggle());
		assignment.checkForToggleEnable(assignment.getAssgn_keepEmailThreadTogetherToggle());
		assignment.checkForToggleEnable(assignment.getAssgnGrp_Create_DrawPooltoggle());

		assignment.navigateToAssignmentsPage();
		driver.waitForPageToBeReady();
		assignment.editAssgnGrp(assignmentGRP, "NO");
		driver.waitForPageToBeReady();
		assignment.toggleEnableOrDisableOfAssignPage(false, true, assignment.getAssgnGrp_Create_DrawPooltoggle(),
				"Draw From Pool", true);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignment.navigateToAssignmentsPage();
		driver.waitForPageToBeReady();
		assignment.editAssgnGrp(assignmentGRP, "No");
		driver.waitForPageToBeReady();

		boolean drawPool = assignment.verifyToggleEnableORDisabled(assignment.getAssgnGrp_Create_DrawPooltoggle(),
				"Draw From Pool");
		boolean keepEmail = assignment.verifyToggleEnableORDisabled(assignment.getAssgn_keepEmailThreadTogetherToggle(),
				"keep Email Toogle");
		boolean keepFamily = assignment.verifyToggleEnableORDisabled(assignment.getAssgn_keepFamiliesTogetherToggle(),
				"Keep Family Toogle");
		asserts.assertFalse(drawPool);
		asserts.assertFalse(keepEmail);
		asserts.assertFalse(keepFamily);
		asserts.assertAll();
		baseClass.passedStep("verified - 'Keep families together' field enabled/disabled");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-54404    
	 * @Description:Verify when RMU can input when count of left Docid in Reviewer
	 *                     batch.     
	 **/
	@Test(description = "RPMXCON-54404", enabled = true, groups = { "regression" })
	public void verifyRMUCanInputRev() throws Exception {
		AssignmentsPage assignment = new AssignmentsPage(driver);
		SessionSearch session = new SessionSearch(driver);
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();
		String expLeftTODO = "10";
		String expCount = "20";
		baseClass.stepInfo("RPMXCON-54404");
		baseClass.stepInfo("Verify when RMU can input when count of left Docid in Reviewer batch.");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in As : " + Input.rmu1userName);
		assignment.navigateToAssignmentsPage();
		assignment.createAssignment(assignmentName, Input.codeFormName);
		session.basicContentSearch(Input.searchString2);
		session.bulkAssign();
		assignment.assignwithSamplemethod(assignmentName, "Count of Selected Docs", "20");
		assignment.editAssignmentUsingPaginationConcept(assignmentName);
		assignment.add2ReviewerAndDistribute(Input.rev1userName,Input.rmu1userName);
		assignment.navigateToAssignmentsPage();
		assignment.editAssignmentUsingPaginationConcept(assignmentName);
		baseClass.waitForElement(assignment.getAssignment_ManageReviewersTab());
		assignment.getAssignment_ManageReviewersTab().waitAndClick(5);
		baseClass.waitForElement(assignment.getDistributedDocs(Input.rmu1userName, "4"));
		String rmuLeftTodo = assignment.getDistributedDocs(Input.rmu1userName, "4").getText();
		String revLeftTodo = assignment.getDistributedDocs(Input.rev1userName, "4").getText();
		baseClass.textCompareEquals(rmuLeftTodo, expLeftTODO, "RMU LeftToDoCount Expected",
				"RMU LeftToDoCount Not Expected");
		baseClass.textCompareEquals(revLeftTodo, expLeftTODO, "REV LeftToDoCount Expected",
				"REV LeftToDoCountNot Expected");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		assignment.RedistributeDocstouser(Input.rmu1userName);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(assignment.getAssignment_ManageReviewersTab());
		assignment.getAssignment_ManageReviewersTab().waitAndClick(5);
		baseClass.waitForElement(assignment.getDistributedDocs(Input.rev1userName, "4"));
		String actCount = assignment.getDistributedDocs(Input.rev1userName, "4").getText();
		baseClass.textCompareEquals(actCount, expCount, "All the Docs Shared", "All the Docs Not Shared");
		baseClass.passedStep("Verified - when RMU can input when count of left Docid in Reviewer batch.");
		loginPage.logout();
	}

	/**
	 * @author Brundha.T RPMXCON-54761
	 * @Description :To verify that RMU is able to view/add Add Reviewers pop on
	 *              click of Add Pending User Reviewer button
	 */
	@Test(description = "RPMXCON-54761", enabled = true, groups = { "regression" })
	public void verifyReviewersCountInWidget() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-54761");
		baseClass.stepInfo(
				"To verify that RMU is able to view/add Add Reviewers pop on click of Add Pending User Reviewer button");

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As " + Input.sa1userName);

		String assgngrpName = "assignmentGrp" + Utility.dynamicNameAppender();
		String newAssign = "assignment" + Utility.dynamicNameAppender();
		String FirstName = "QA1" + Utility.dynamicNameAppender();
		String LastName = "Automation";
		String MailID = "testing" + Utility.dynamicNameAppender() + "@consilio.com";

		UserManagement user = new UserManagement(driver);
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		SessionSearch search = new SessionSearch(driver);
		Dashboard dashBoard = new Dashboard(driver);

		user.navigateToUsersPAge();
		baseClass.stepInfo("Creating pending active user for reviewer");
		user.createUser(FirstName, LastName, Input.Reviewer, MailID, " ", Input.projectName);
		System.out.println(MailID);
		loginPage.logout();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");

		baseClass.stepInfo("Create Assignment Group");
		assignment.createAssgnGroup(assgngrpName);
		assignment.selectAssignmentGroup(assgngrpName);
		assignment.createAssignmentFromAssgnGroup(newAssign, Input.codingFormName);

		baseClass.stepInfo("Bulk Assigning");
		search.basicContentSearch(Input.testData1);
		search.bulkAssignExistingForCopyAssignment(assgngrpName);

		baseClass.stepInfo("Adding Reviewer and distributing");
		assignment.selectAssignmentGroup(assgngrpName);
		if (!assignment.getSelectAssignmentHighlightCheck(newAssign).isElementAvailable(5)) {
			assignment.getSelectAssignment(newAssign).waitAndClick(5);
		}
		driver.scrollPageToTop();
		assignment.getAssignmentActionDropdown().waitAndClick(3);
		baseClass.waitForElement(assignment.getAssignmentAction_EditAssignment());
		assignment.getAssignmentAction_EditAssignment().waitAndClick(3);
		assignment.addReviewerAndDistribute(MailID);

		baseClass.stepInfo("Verifying reviewers count in RMU dashboard");
		dashBoard.navigateToDashboard();
		dashBoard.AddNewWidgetToDashboard(Input.ReviewerProductivity);
		baseClass.waitTime(2);
		baseClass.waitForElementCollection(dashBoard.getReviewerslist());
		int Size = dashBoard.getReviewerslist().size();
		baseClass.ValidateElementCollection_Presence(dashBoard.getReviewerslist(), "Reviewers in dashboard");
		List<String> Reviewers = new ArrayList<>();
		List<WebElement> elementList = dashBoard.getReviewerslist().FindWebElements();
		for (WebElement webElementNames : elementList) {
			String RevName = webElementNames.getText();
			Reviewers.add(RevName);
		}
		System.out.println(Reviewers);
		baseClass.stepInfo("Reviewers in reviewer productivity widget" + Reviewers);
		System.out.println(Size);
		if (Size == 6) {
			baseClass.passedStep("Reviewers are displayed in dashboard");
		} else {
			baseClass.failedStep("Reviewers are not displayed as expected");
		}
		loginPage.logout();

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As " + Input.sa1userName);

		// delete the created user
		user.filterTodayCreatedUser();
		user.filterByName(MailID);
		user.deleteUser();
		loginPage.logout();
	}

	/**
	 * @author
	 * @Description :Verify the functionality of Redistribution for uneven numbers
	 *              of Documents is following "off the top" logic.RPMXCON-54402
	 */
	@Test(description = "RPMXCON-54402", groups = { "regression" })
	public void verifyFunctionalityOfRedistributionForUnevenDocumentFollowingOffTopLogics()
			throws InterruptedException {

		List<String> listOfReviewers = new ArrayList<String>(Arrays.asList("PA", "RMU"));
		List<String> listOfUsers=new ArrayList<String>(Arrays.asList(Input.pa1userName, Input.rmu1userName));
		String assignmentName = "assignment" + Utility.dynamicNameAppender();

		baseClass.stepInfo("RPMXCON-54402");
		baseClass.stepInfo(
				"verify the functionality of Redistribution for uneven numbers of Documents is following \"off the top\" logic.");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// performing basic search and bulk assign
		baseClass.stepInfo("performing basic search and bulk assign.");
		int pureHitCount = sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();

		// creating assignment
		assignment.assignmentCreationAsPerCf(assignmentName, Input.codeFormName);
		baseClass.stepInfo("Assignment Created : " + assignmentName);

		// adding reviewers and distributing the documents to reviewers.
		baseClass.stepInfo("adding reviewers and distributing the documents to reviewers.");
		int docCountToDistribute = assignment.oddOrEvenDocumentCountToDistribute(pureHitCount, "odd");
		assignment.distributeTheGivenDocCountToReviewer(Integer.toString(docCountToDistribute),Input.rev1userName);
		assignment.addReviewers(listOfReviewers,listOfUsers);

		// redistributing the documents to reviewers.
		baseClass.stepInfo("redistributing the documents to reviewers.");
		assignment.selectReviewerAndClickRedistributeAction();
		Actions act=new Actions(driver.getWebDriver());
		baseClass.waitForElement(assignment.getSelectReviewerInRedistributedDocsTab(Input.pa1userName));
		act.moveToElement(assignment.getSelectReviewerInRedistributedDocsTab(Input.pa1userName).getWebElement()).click().build().perform();
//		assignment.getSelectReviewerInRedistributedDocsTab(Input.pa1userName).waitAndClick(5);
		baseClass.waitForElement(assignment.getSelectReviewerInRedistributedDocsTab(Input.rmu1userName));
		act.moveToElement(assignment.getSelectReviewerInRedistributedDocsTab(Input.rmu1userName).getWebElement()).click().build().perform();
//		assignment.getSelectReviewerInRedistributedDocsTab(Input.rmu1userName).waitAndClick(5);
		assignment.getAssgn_Redistributepopup_save().waitAndClick(10);
		driver.Navigate().refresh();
		baseClass.waitForElement(assignment.getAssignment_ManageReviewersTab());
		assignment.getAssignment_ManageReviewersTab().waitAndClick(10);
		baseClass.waitTime(6);

		// Verify the Redistribution calculation happened among Reviewer 2 and reviewer
		// 3 From reviewer 1 Batch.
		int leastDocCount = (int) (docCountToDistribute / 2);
		int expectedPaDocCount = leastDocCount;
		baseClass.waitTime(3);
		int actualPaDocCount = Integer.parseInt(assignment.getDistributedDocs(Input.pa1userName).getText());
		baseClass.digitCompareEquals(expectedPaDocCount, actualPaDocCount,
				"Expected document count : '" + expectedPaDocCount + "' match with actual document count : '"
						+ actualPaDocCount + "'",
				"Expected document count : '" + expectedPaDocCount + "' doesn't match with actual document count : '"
						+ actualPaDocCount + "'");
		int expectedRmuDocCount = leastDocCount + 1;
		baseClass.waitTime(3);
		int actualRmuDocCount = Integer.parseInt(assignment.getDistributedDocs(Input.rmu1userName).getText());
		baseClass.digitCompareEquals(expectedRmuDocCount, actualRmuDocCount,
				"Expected document count : '" + expectedRmuDocCount + "' match with actual document count : '"
						+ actualRmuDocCount + "'",
				"Expected document count : '" + expectedRmuDocCount + "' doesn't match with actual document count : '"
						+ actualRmuDocCount + "'");
		baseClass.stepInfo(
				"Verified that Redistribution calculation happened among Reviewer 2 and reviewer 3 From reviewer 1 Batch.");
		// LogOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Description : Verify that Assignment objects appear with a checkbox, and
	 *              Assignment Group objects appear with a folder icon in Assign
	 *              Documents pop up.RPMXCON-54339
	 */
	@Test(description = "RPMXCON-54339", enabled = true, groups = { "regression" })
	public void verifyAssignmentObjectAppearWithCheckboxAndAssignmentGroupObjectAppearWithFolder() throws Exception {

		String assignmentName = "assignment" + Utility.dynamicNameAppender();
		String assignmentGroupName = "assignmentGroup" + Utility.dynamicNameAppender();

		System.out.println(assignmentName);
		System.out.println(assignmentGroupName);

		baseClass.stepInfo("RPMXCON-54339");
		baseClass.stepInfo(
				"Verify that Assignment objects appear with a checkbox, and Assignment Group objects appear with a folder icon in Assign Documents pop up.");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// creating Assignment Group
		baseClass.stepInfo("creating Assignment Group.");
		assignment.navigateToAssignmentsPage();
		assignment.createAssgnGroup(assignmentGroupName);
		// creating Assignment
		baseClass.stepInfo("creating Assignment.");
		assignment.createAssignment(assignmentName, Input.codingFormName);

		// performing basic content search
		baseClass.stepInfo("performing basic content search .");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();
		baseClass.waitTime(2);

		// verify that Assignment Group object folder icon appear in Existing
		// Assignment.
		baseClass.ValidateElement_Presence(
				assignment.getAssignmentGroupFolderIconInExistingAssignmentInBulkAssignTab(assignmentGroupName),
				"Assignment Group Folder Icon in Existing Assignment.");
		baseClass.stepInfo("verified that Assignment Group object folder icon appear in Existing Assignment.");

		// verify that Assignment object checkbox appear in Existing Assignment.
		baseClass.ValidateElement_Presence(
				assignment.getAssignmentCheckBoxInExistingAssignmentInBulkAssignTab(assignmentName),
				"Assignment CheckBox in Existing Assignment.");
		baseClass.stepInfo("verified that Assignment object checkbox appear in Existing Assignment.");

		// verify that Assignment Group object folder icon appear in New Assignment.
		baseClass.waitForElement(sessionSearch.getBulkAssign_NewAssignment());
		sessionSearch.getBulkAssign_NewAssignment().waitAndClick(5);
		baseClass.ValidateElement_Presence(
				assignment.getAssignmentGroupFolderIconInNewAssignmentInBulkAssignTab(assignmentGroupName),
				"Assignment Group Folder Icon in New Assignment.");
		baseClass.stepInfo("verified that Assignment Group object folder icon appear in New Assignment.");

		// deleting assignment
		assignment.navigateToAssignmentsPage();
		assignment.DeleteAssgnGroup(assignmentGroupName);
		// deleting assignment group
		assignment.deleteAssgnmntUsingPagination(assignmentName);

		// logout
		loginPage.logout();
	}

	/**
	 * @author NA
	 * @Description :Verify that user can save/sort single or multiple coding form
	 *              successfully .[RPMXCON-67511]
	 */
	@Test(description = "RPMXCON-67511", enabled = true, groups = { "regression" })
	public void verifySingleMultiCFSortExisProjCasCadeOFF() throws InterruptedException {
		List<String> listOfCFAfterSorting01 = new ArrayList<String>();
		List<String> listOfCFAfterSorting02 = new ArrayList<String>();
		listOfCFAfterSorting02.add(Input.codeFormName);
		String assignmentGroup01 = "assignmentGroup" + Utility.dynamicNameAppender();
		String assignmentGroup02 = "assignmentGroup" + Utility.dynamicNameAppender();
		String assignmentName01 = "assignment" + Utility.dynamicNameAppender();
		String assignmentName02 = "assignment" + Utility.dynamicNameAppender();
		DocViewPage docView = new DocViewPage(driver);
		CodingForm codingForm = new CodingForm(driver);

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-67511");
		baseClass.stepInfo("Verify that user can save/sort single or multiple coding form successfully "
				+ "while editing existing assignment in a user created assignment group with cascade setting OFF for existing project");
		List<String> listOfCodingForm = codingForm.createCodingformBasedOnCondition(3);

		assignment.navigateToAssignmentsPage();
		assignment.createCascadeNonCascadeAssgnGroup(assignmentGroup01, "No");
		baseClass.passedStep("creating assignment Group : '" + assignmentGroup01 + "' with cascading setting OFF");

		baseClass.stepInfo("creating assignment under newly created Assignment group");
		assignment.selectAssignmentGroup(assignmentGroup01);
		assignment.createAssignmentFromAssgnGroup(assignmentName01, Input.codingFormName);

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assignmentName01);

		baseClass.stepInfo("adding Reviewers and Distributing the Documents to Reviewers");
		assignment.editAssignmentInAssignGroup(assignmentGroup01, assignmentName01);
		assignment.add2ReviewerAndDistribute(Input.rev1userName,Input.rmu1userName);

		assignment.editAssignmentInAssignGroup(assignmentGroup01, assignmentName01);
		listOfCFAfterSorting01 = assignment.SelectAllCodingFormAndChangeSortingSequence(Input.codingFormName);
		baseClass.waitForElement(assignment.getAssignmentSaveButton());
		assignment.getAssignmentSaveButton().waitAndClick(3);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("selecting assignment from dashBoard to view in DocView");
		assignment.SelectAssignmentByReviewer(assignmentName01);
		baseClass.waitForElement(docView.getDocView_CodingFormlist());
		docView.getDocView_CodingFormlist().waitAndClick(5);
		List<String> listOfCFInDocViewDropDown01 = baseClass
				.availableListofElements(docView.listOfCodingFormInDocViewDropDown());
		baseClass.compareListViaContains(listOfCFAfterSorting01, listOfCFInDocViewDropDown01);
		baseClass.stepInfo(
				"verified that coding forms in Coding Form dropdown from doc view loaded as per the saved sort order.");
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignment.navigateToAssignmentsPage();
		assignment.createCascadeNonCascadeAssgnGroup(assignmentGroup02, "No");
		baseClass.stepInfo("creating assignment under newly created Assignment group");
		assignment.selectAssignmentGroup(assignmentGroup02);
		assignment.createAssignmentFromAssgnGroup(assignmentName02, listOfCFAfterSorting01.get(1));

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assignmentName02);

		baseClass.stepInfo("adding Reviewers and Distributing the Documents to Reviewers");
		assignment.editAssignmentInAssignGroup(assignmentGroup02, assignmentName02);
		assignment.add2ReviewerAndDistribute(Input.rev1userName,Input.rmu1userName);

		assignment.editAssignmentInAssignGroup(assignmentGroup02, assignmentName02);
		List<String> listOfCFName = new ArrayList<String>();
		listOfCFName.add(Input.codeFormName);
		listOfCFAfterSorting02 = assignment.editExistingCodingForm(listOfCFName, Input.codeFormName, false);
		baseClass.waitForElement(assignment.getAssignmentSaveButton());
		assignment.getAssignmentSaveButton().waitAndClick(3);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("selecting assignment from dashBoard to view in DocView");
		assignment.SelectAssignmentByReviewer(assignmentName02);
		baseClass.waitForElement(docView.getDocView_CodingFormlist());
		docView.getDocView_CodingFormlist().waitAndClick(5);
		List<String> listOfCFInDocViewDropDown02 = baseClass
				.availableListofElements(docView.listOfCodingFormInDocViewDropDown());
		baseClass.compareListViaContains(listOfCFAfterSorting02, listOfCFInDocViewDropDown02);
		baseClass.stepInfo(
				"verified that coding forms in Coding Form dropdown from doc view loaded as per the saved sort order.");
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		codingForm.navigateToCodingFormPage();
		codingForm.DeleteMultipleCodingform(listOfCodingForm);
		baseClass.passedStep(
				"Verify that user can save/sort single or multiple coding form successfully while editing existing assignment "
						+ "in a user created assignment group with cascade setting OFF for existing project");
		loginPage.logout();
	}

	/**
	 * @author NA
	 * @Description :Verify that user can save/sort single or multiple coding form
	 *              successfully while creating new assignment in root assignment
	 *              group for existing project[RPMXCON-67508]
	 */
	@Test(description = "RPMXCON-67508", enabled = true, groups = { "regression" })
	public void verifySingleMultiCFSortNewProjRootGRP() throws Exception {
		List<String> listOfCFAfterSorting01 = new ArrayList<String>();
		List<String> listOfCFAfterSorting02 = new ArrayList<String>();
		listOfCFAfterSorting02.add(Input.codeFormName);
		String assignmentName01 = "assignment" + Utility.dynamicNameAppender();
		String assignmentName02 = "assignment" + Utility.dynamicNameAppender();
		DocViewPage docView = new DocViewPage(driver);
		CodingForm codingForm = new CodingForm(driver);

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-67508");
		baseClass.stepInfo("Verify that user can save/sort single or multiple coding form successfully "
				+ "while creating new assignment in root assignment group for existing project");
		codingForm.createCodingformBasedOnCondition(3);

		assignment.navigateToAssignmentsPage();
		baseClass.stepInfo("creating assignment under Root Assignment group");
		assignment.createAssignment_withoutSave(assignmentName01, Input.codingFormName);
		listOfCFAfterSorting01 = assignment.SelectAllCodingFormAndChangeSortingSequence(Input.codingFormName);
		baseClass.waitForElement(assignment.getAssignmentSaveButton());
		assignment.getAssignmentSaveButton().waitAndClick(5);

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assignmentName01);

		baseClass.stepInfo("adding Reviewers and Distributing the Documents to Reviewers");
		assignment.editAssignmentUsingPaginationConcept(assignmentName01);
		assignment.add2ReviewerAndDistribute(Input.rev1userName,Input.rmu1userName);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("selecting assignment from dashBoard to view in DocView");
		assignment.SelectAssignmentByReviewer(assignmentName01);
		baseClass.waitForElement(docView.getDocView_CodingFormlist());
		docView.getDocView_CodingFormlist().waitAndClick(5);
		List<String> listOfCFInDocViewDropDown01 = baseClass
				.availableListofElements(docView.listOfCodingFormInDocViewDropDown());
		baseClass.compareListViaContains(listOfCFAfterSorting01, listOfCFInDocViewDropDown01);
		baseClass.stepInfo(
				"verified that coding forms in Coding Form dropdown from doc view loaded as per the saved sort order.");
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignment.navigateToAssignmentsPage();
		assignment.createAssignment_withoutSave(assignmentName02, listOfCFAfterSorting01.get(1));
		List<String> listOfCFName = new ArrayList<String>();
		listOfCFName.add(Input.codeFormName);
		listOfCFAfterSorting02 = assignment.editExistingCodingForm(listOfCFName, Input.codeFormName, false);
		baseClass.waitForElement(assignment.getAssignmentSaveButton());
		assignment.getAssignmentSaveButton().waitAndClick(3);

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assignmentName02);

		baseClass.stepInfo("adding Reviewers and Distributing the Documents to Reviewers");
		assignment.editAssignmentUsingPaginationConcept(assignmentName02);
		assignment.add2ReviewerAndDistribute(Input.rev1userName,Input.rmu1userName);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("selecting assignment from dashBoard to view in DocView");
		assignment.SelectAssignmentByReviewer(assignmentName02);
		baseClass.waitForElement(docView.getDocView_CodingFormlist());
		docView.getDocView_CodingFormlist().waitAndClick(5);
		List<String> listOfCFInDocViewDropDown02 = baseClass
				.availableListofElements(docView.listOfCodingFormInDocViewDropDown());
		baseClass.compareListViaContains(listOfCFAfterSorting02, listOfCFInDocViewDropDown02);
		baseClass.stepInfo(
				"verified that coding forms in Coding Form dropdown from doc view loaded as per the saved sort order.");
		loginPage.logout();

		String assignmentName03 = "Assignment03" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignWithNewAssignment();
		assignment.createAssignment_fromAssignUnassignPopup(assignmentName03, Input.codingFormName);
		List<String> listOfCFAfterSorting03 = assignment
				.SelectAllCodingFormAndChangeSortingSequence(Input.codingFormName);
		baseClass.waitForElement(assignment.getAssignmentSaveButton());
		assignment.getAssignmentSaveButton().waitAndClick(5);
		baseClass.stepInfo("adding Reviewers and Distributing the Documents to Reviewers");
		assignment.editAssignmentUsingPaginationConcept(assignmentName03);
		assignment.add2ReviewerAndDistribute(Input.rev1userName,Input.rmu1userName);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("selecting assignment from dashBoard to view in DocView");
		assignment.SelectAssignmentByReviewer(assignmentName03);
		baseClass.waitForElement(docView.getDocView_CodingFormlist());
		docView.getDocView_CodingFormlist().waitAndClick(5);
		List<String> listOfCFInDocViewDropDown03 = baseClass
				.availableListofElements(docView.listOfCodingFormInDocViewDropDown());
		baseClass.compareListViaContains(listOfCFAfterSorting03, listOfCFInDocViewDropDown03);
		baseClass.stepInfo(
				"verified that coding forms in Coding Form dropdown from doc view loaded as per the saved sort order.");
		baseClass.passedStep("Verified - that user can save/sort single or multiple coding form successfully "
				+ "while creating new assignment in root assignment group for existing project");
		loginPage.logout();
	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :New Assignment - Keywords created by PAU and RMU part of the
	 *              security group should be selected for the assignment.
	 *              RPMXCON-54752
	 */
	@Test(description = "RPMXCON-54752", enabled = true, groups = { "regression" })
	public void verifyNewAssignmentKeywordsCreatedByPAUandRMUPartOfSecurityGrpSelectedForAssignment()
			throws AWTException, InterruptedException {
		keyPage = new KeywordPage(driver);
		DocViewPage docView = new DocViewPage(driver);
		String assignmentName = "assignment" + Utility.dynamicNameAppender();
		String[][] keyword01 = { { "a", "Aqua" }, { "b", "Blue" }, { "c", "Gold" }, { "d", "Pink" }, { "e", "Aqua" } };
		String[][] keyword02 = { { "f", "Aqua" }, { "g", "Blue" }, { "h", "Gold" }, { "i", "Pink" }, { "j", "Aqua" } };

		// login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-54752 Assignments");
		baseClass.stepInfo(
				"New Assignment - Keywords created by PAU and RMU part of the security group should be selected for the assignment.");

		// creating keywords as PAU
		keyPage.navigateToKeywordPage();
		baseClass.stepInfo("creating keywords as PAU");
		List<String> listOfKeywordGroup01 = keyPage.addMultipleKeywords(keyword01, true, Input.securityGroup);
		loginPage.logout();

		// creating keywords as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		keyPage.navigateToKeywordPage();
		baseClass.stepInfo("creating keywords as RMU");
		List<String> listOfKeywordGroup02 = keyPage.addMultipleKeywords(keyword02, true, Input.securityGroup);

		// Adding into list
		listOfKeywordGroup01.addAll(listOfKeywordGroup02);

		// creating assignment with keywords
		assignment.createAssignment_withoutSave(assignmentName, Input.codingFormName);

		// verify that Add Keyword window should pop-up with all keywords associated the
		// security group (keywords created by both PAU and RMU) selected
		assignment.verifyKeywordsAvailabilityInAssignment(listOfKeywordGroup01);
		assignment.verifyAddedKeywordsChecked();
		baseClass.passedStep(
				"verified that Add Keyword window pop-up with all keywords associated the security group (keywords created by both PAU and RMU) selected.");
		assignment.getSaveBtn().waitAndClick(5);
		baseClass.stepInfo("assignment : '" + assignmentName + " created.");

		// performing bulk assign
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assignmentName);
		assignment.editAssignmentUsingPaginationConcept(assignmentName);

		// adding reviewers and distributing documents to reviewers
		baseClass.stepInfo("adding reviewers and distributing documents to reviewers");
		assignment.add2ReviewerAndDistribute(Input.rev1userName,Input.rmu1userName);

		// logOut
		loginPage.logout();

		// login as REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting assignment from DashBoard and navigating to DocView
		baseClass.stepInfo("selecting assignment from DashBoard and navigating to DocView.");
		assignment.SelectAssignmentByReviewer(assignmentName);

		// Keyword should be highlighted on the DocView
		docView.verifyKeywordsOnDocView(null, keyword01);
		baseClass.ValidateElementCollection_Presence(docView.getAnnotations(), "Highlights in Document");
		baseClass.passedStep("verified that Keywords are highlighted on the DocView.");
		docView.verifyKeywordsOnDocView(null, keyword02);
		baseClass.ValidateElementCollection_Presence(docView.getAnnotations(), "Highlights in Document");
		baseClass.passedStep("verified that Keywords are highlighted on the DocView.");

		// logOut
		loginPage.logout();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// delete all keywords
		keyPage.navigateToKeywordPage();
		keyPage.deleteMultipleKeywords(listOfKeywordGroup01);

		// delete assignment
		assignment.deleteAssignment(assignmentName);

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :Verify the typing in the reviewers name acts as a \"live
	 *              filter\" of the grid in Redistribute To Other Reviewer close pop
	 *              up. RPMXCON-54401
	 */

	@Test(description = "RPMXCON-54401", enabled = true, groups = { "regression" })
	public void verifyTypingReviewersNameActsAsLiveFilterOfGridInRedistributePopUp() throws InterruptedException {
		String assignmentName = "assignment" + Utility.dynamicNameAppender();
		int expectedReviewersCountBeforeFilter = 3;
		int expectedReviewersCountAfterFilter = 1;

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-54401 Assignments");
		baseClass.stepInfo(
				"Verify the typing in the reviewers name acts as a \"live filter\" of the grid in Redistribute To Other Reviewer close pop up.");

		// create assignment using Bulk Assign
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignWithNewAssignmentWithPersistantHit(assignmentName, Input.codeFormName);
		baseClass.passedStep("Assignment Name : '" + assignmentName + " created.");

		// add reviewers to assignment and Distribute Documents
		assignment.editAssignmentUsingPaginationConcept(assignmentName);
		assignment.add4ReviewerAndDistribute();
		baseClass.waitTime(6);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		// Verify the typing in the reviewers name acts as a "live filter" of the grid
		// in Redistribute To Other Reviewer close pop up.
		assignment.selectReviewerAndClickRedistributeAction();
		int actualReviewersCountBeforeFilter = assignment.getListOfReviewersInRedistributePopUp().size();
		softAssert.assertEquals(actualReviewersCountBeforeFilter, expectedReviewersCountBeforeFilter);
		assignment.getLiveFilterSearchInputField().SendKeys(Input.rmu1userName);
		baseClass.waitTime(2);
		int actualReviewersCountAfterFilter = assignment.getListOfReviewersInRedistributePopUp().size();
		String actualReviewer = baseClass.availableListofElements(assignment.getListOfReviewersInRedistributePopUp())
				.get(0);
		softAssert.assertEquals(actualReviewersCountAfterFilter, expectedReviewersCountAfterFilter);
		baseClass.compareTextViaContains(actualReviewer, Input.rmu1userName,
				"Expected reviewer : '" + actualReviewer + "' is get appeared after applying Live Filter.",
				"Expected reviewer doesn't match with actual reviewer.");
		baseClass.passedStep(
				"Verified That typing in the reviewers name acts as a \"live filter\" of the grid in Redistribute To Other Reviewer close pop up.");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :To verify that Export Data option is provided on Manage
	 *              Assignment Page. RPMXCON-54180
	 */
	@Test(description = "RPMXCON-54180", enabled = true, groups = { "regression" })
	public void verifyExportDataOptionProvidedOnManageAssignmentPage() {

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-54180 Assignments");
		baseClass.stepInfo("To verify that Export Data option is provided on Manage Assignment Page.");

		// navigating to Assignment Page
		assignment.navigateToAssignmentsPage();
		baseClass.stepInfo("Navigating to Assignment Page.");

		// verify That Export option should be displayed in Action drop down of Manage
		// Assignment page.
		driver.waitForPageToBeReady();
		baseClass.waitForElement(assignment.getAssignmentActionDropdown());
		assignment.getAssignmentActionDropdown().waitAndClick(5);
		baseClass.stepInfo("Clicking on Action Drop down in assignment page.");
		baseClass.ValidateElement_Presence(assignment.getAssgnAction_Export(), "Export Option");
		baseClass.stepInfo("Verified That Export option is displayed in Action drop down of Manage Assignment page.");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :To verify that Coding form field is displayed for Assignment
	 *              only. RPMXCON-53969
	 */
	@Test(description = "RPMXCON-53969", enabled = true, groups = { "regression" })
	public void verifyCodingFormFieldDispalyedForAssignment() {

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-53969 Assignments");
		baseClass.stepInfo("To verify that Coding form field is displayed for Assignment only.");

		// navigating to Assignment Page
		assignment.navigateToAssignmentsPage();

		// selecting new Assignment from dropDown
		driver.waitForPageToBeReady();
		assignment.NavigateToNewEditAssignmentPage("new");
		baseClass.stepInfo("New Assignment page is opened.");

		// Verify that Coding form field is displayed for assignment.
		driver.waitForPageToBeReady();
		baseClass.ValidateElement_Presence(assignment.getSelectSortCodingForm_Tab(), "Coding form field");
		baseClass.passedStep("Verified that Coding form field is displayed for assignment.");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :Verify the Redistribute functionality is working fine for whole
	 *              numbers. RPMXCON-54397
	 */

	@Test(description = "RPMXCON-54397", enabled = true, groups = { "regression" })
	public void verifyRedistributeFunctionalityWorkingFineForWholeNumbers() throws InterruptedException {

		String assignmentName = "assignment" + Utility.dynamicNameAppender();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-54397 Assignments");
		baseClass.stepInfo("Verify the Redistribute functionality is working fine for whole numbers.");

		// create assignment using Bulk Assign
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignWithNewAssignmentWithPersistantHit(assignmentName, Input.codeFormName);
		baseClass.passedStep("Assignment Name : '" + assignmentName + " created.");

		// add reviewers to assignment and Distribute Documents
		assignment.editAssignmentUsingPaginationConcept(assignmentName);
		assignment.add2ReviewerAndDistribute(Input.rev1userName,Input.rmu1userName);

		// Performing Redistribute Documents Action.
		assignment.getAssignment_ManageReviewersTab().waitAndClick(10);
		baseClass.waitTime(4);
		int rmuDistributedDocCount = Integer.parseInt(assignment.getDistributedDocs(Input.rmu1userName).getText());
		int revDistributedDocCount = Integer.parseInt(assignment.getDistributedDocs(Input.rev1userName).getText());
		assignment.selectReviewerAndClickRedistributeAction();
		assignment.getAssgn_Redistributepopup().waitAndClick(10);
		assignment.getAssgn_Redistributepopup_save().waitAndClick(10);
		baseClass.passedStep("Performing Redistribute Documents Action.");
		baseClass.waitTime(4);

		// verify that documents should be distributed as per left documents properly.
		int ExpectedRmuDistributedDocCountAfterRedistribute = rmuDistributedDocCount + revDistributedDocCount;
		int ActualRmuDistributedDocCountAfterRedistribute = Integer
				.parseInt(assignment.getDistributedDocs(Input.rmu1userName).getText());
		baseClass.digitCompareEquals(ActualRmuDistributedDocCountAfterRedistribute,
				ExpectedRmuDistributedDocCountAfterRedistribute,
				"Actual Distributed To User Doc Count : '" + ActualRmuDistributedDocCountAfterRedistribute
						+ "' match with the Expected Distributed To User Doc Count : '"
						+ ActualRmuDistributedDocCountAfterRedistribute + "'",
				"Actual Distributed To User Doc Count : '" + ActualRmuDistributedDocCountAfterRedistribute
						+ "' doesn't match with the Expected Distributed To User Doc Count : '"
						+ ActualRmuDistributedDocCountAfterRedistribute + "'");
		baseClass.passedStep("Verified that documents are distributed as per left documents properly.");

		// Delete Assignment
		assignment.deleteAssgnmntUsingPagination(assignmentName);

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :To verify the functionality of the ReDistribute Document for
	 *              Uncompleted Document.RPMXCON-53699
	 */

	@Test(description = "RPMXCON-53699", enabled = true, groups = { "regression" })
	public void verifyReDistributeDocumentForUncompletedDocument() throws InterruptedException {

		String assignmentName = "assignment" + Utility.dynamicNameAppender();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-53699");
		baseClass.stepInfo("To verify the functionality of the ReDistribute Document for Uncompleted Document.");

		// creating Assignment
		assignment.createAssignment(assignmentName, Input.codingFormName);
		// performing bulk Assign
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assignmentName);

		// adding reviewers and distributing the documents to reviewers
		assignment.editAssignmentUsingPaginationConcept(assignmentName);
		assignment.add2ReviewerAndDistribute(Input.rev1userName,Input.rmu1userName);
		baseClass.waitForElement(baseClass.getSuccessMsgHeader());
		baseClass.waitTime(6);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		// select the Reviewer having uncomplete documents & Redistribute documents to
		// another reviewer
		// verify All the documents from the Reviewer is reassign to another Reviewer.
		baseClass.stepInfo("perform ReDistribute Documents in Reviewers Tab.");
		assignment.RedistributeDocInManageReviewerTab(Input.rev1userName, Input.rmu1userName);
		baseClass.passedStep("Verified that documents of Reviewer are reassigned to another Reviewer");

		// logout
		loginPage.logout();
	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :To verify that label of the textbox is changed where RMU enters
	 *              numbers for Sample method.RPMXCON-53637
	 */

	@Test(description = "RPMXCON-53637", enabled = true, groups = { "regression" })
	public void verifyLableOfTextboxChangedWhereRMUEnterNumberOfSampleMethod() throws InterruptedException {
		String assignmentName = "assignment" + Utility.dynamicNameAppender();
		String listOfSampleMethodOptions[] = { "Percent of Selected Docs", "Parent Level Docs Only",
				"Count of Selected Docs", "Inclusive Email" };

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-53637 Assignments");
		baseClass
				.stepInfo("To verify that label of the textbox is changed where RMU enters numbers for Sample method.");

		// create assignment
		assignment.createAssignment(assignmentName, Input.codeFormName);

		// perform basic content search
		sessionSearch.basicContentSearch(Input.searchString1);

		// perform bulk assign
		sessionSearch.bulkAssign();

		// Select Sample method as "Percent of Selected Docs" and Verify that Label is
		// changed to "Percentage to Assign"
		sessionSearch.changeSampleMethodAndVerifyTextBoxLableOfSampleMethod(listOfSampleMethodOptions[0], true, true);

		// Select Sample method as "Parent Level Docs Only" and Verify that label is
		// removed.
		sessionSearch.changeSampleMethodAndVerifyTextBoxLableOfSampleMethod(listOfSampleMethodOptions[1], true, false);

		// Select Sample method as "Inclusive Email" and Verify that label is removed.
		sessionSearch.changeSampleMethodAndVerifyTextBoxLableOfSampleMethod(listOfSampleMethodOptions[2], true, false);

		// Select sample method as "Count of Selected Docs" and Verify that label is
		// changed to "Number to Assign"
		sessionSearch.changeSampleMethodAndVerifyTextBoxLableOfSampleMethod(listOfSampleMethodOptions[3], true, false);

		// logout
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: 19/10/2022 TestCase Id:RPMXCON-54327 Description
	 * :Verify that the Show Document Counts toggle is OFF by default
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-54327", enabled = true, groups = { "regression" })
	public void verifyShowDocsCountToggleStatus() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-54327");
		baseClass.stepInfo("Verify that the Show Document Counts toggle is OFF by default.");
		String assignName = "Aassign" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");
		baseClass.stepInfo("Pre-requisite: assigning docs to assignment");
		assignment.createAssignment(assignName, Input.codeFormName);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkAssignExisting(assignName);
		baseClass.stepInfo("Search for documents and action as bulk assign");
		baseClass.selectproject();
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkAssign();
		baseClass.stepInfo("Select unassign documents radio button");
		baseClass.waitForElement(assignment.getUnassignbutton());
		assignment.getUnassignbutton().waitAndClick(10);
		// verify
		if (assignment.getSelectAssignmentToBulkAssign(assignName).isElementAvailable(10)
				&& assignment.getExisitingAssignTab().isElementAvailable(10)) {
			baseClass.passedStep("Existing assignment,group and tab present in the assign/unassign popup");
			String toggleStatus = assignment.showDocCountToggle().GetAttribute("class");
			baseClass.stepInfo("Toggle off status :" + toggleStatus);
			if (toggleStatus.equalsIgnoreCase("true")) {
				baseClass.failedStep("Show docscount toggle enabled by default");
			} else {
				baseClass.passedStep("Show Document Count toggle is OFF by default");
			}
		} else {
			baseClass.failedStep("Existing tab and assignment not present in the assign/unassign popup");
		}
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: 19/10/2022 TestCase Id:RPMXCON-54326 Description
	 * :Verify that the Starting Count of Docs is consistent with the selection on
	 * the prior screen
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-54326", enabled = true, groups = { "regression" })
	public void verifyStartingCountOfDocsStatus() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-54326");
		baseClass.stepInfo("Verify that the Starting Count of Docs is consistent with selection.");

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");
		baseClass.stepInfo("Search for docs and action as bulk assign");
		int count = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkAssign();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify the starting count of docs");
		baseClass.waitForElement(assignment.getStartingCountDoc());
		int docCount = Integer.parseInt(assignment.getStartingCountDoc().getText());
		baseClass.stepInfo("count in shopping cart for pure hit :" + count);
		baseClass.stepInfo("starting count of docs in assign/unassign popup :" + docCount);
		if (count == docCount) {
			baseClass.passedStep("Starting count of docs same as selection in prior screen");
		} else {
			baseClass.failedStep("Starting count of docs not same as selection in prior screen");
		}
		loginPage.logout();
	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :Existing Assignment - All Keywords created by PAU and RMU part
	 *              of the security group should be selected for existing
	 *              assignment.. RPMXCON-54754
	 */
	@Test(description = "RPMXCON-54754", enabled = true, groups = { "regression" })
	public void verifyExistingAssignmentAllKeywordsCreadtedByPAUandRMUPartOfSecurityGroupSelectedForExistingAssignment()
			throws AWTException, InterruptedException {
		KeywordPage keyPage = new KeywordPage(driver);
		DocViewPage docView = new DocViewPage(driver);
		String assignmentName = "assignment" + Utility.dynamicNameAppender();
		String existingKeyword = "t";
		String existingKeywordGroup = "t" + Utility.dynamicNameAppender();
		String PauKeyword = "u";
		String RmuKeyword = "v";
		String RmuKeywordGroup = "v" + Utility.dynamicNameAppender();
		List<String> allKeywords = new ArrayList<String>();
		List<String> colors = new ArrayList<String>();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-54754 Assignments");
		baseClass.stepInfo(
				"Existing Assignment - All Keywords created by PAU and RMU part of the security group should be selected for existing assignment.");

		// creating keyword for pre-requisite
		keyPage.navigateToKeywordPage();

		// keyPage.addKeyword(existingKeyword,Input.KeyWordColour);
		keyPage.addKeyword(existingKeywordGroup, existingKeyword, Input.KeyWordColour);
		assignment.createAssignment(assignmentName, Input.codingFormName);

		// performing bulk assign
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assignmentName);
		assignment.editAssignmentUsingPaginationConcept(assignmentName);

		// adding reviewers and distributing documents to reviewers
		baseClass.stepInfo("adding reviewers and distributing documents to reviewers");
		assignment.add2ReviewerAndDistribute(Input.rev1userName,Input.rmu1userName);

		// logOut
		loginPage.logout();

		// login as PAU
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		// creating keyword as PAU
		keyPage.navigateToKeywordPage();
		baseClass.stepInfo("creating keyword as PAU");
		allKeywords.add(PauKeyword);
		colors.add(Input.KeyWordColour);
		List<String> listOfKeywordGroup = keyPage.addMultipleKeywords(allKeywords, colors, true, Input.securityGroup);
		listOfKeywordGroup.add(existingKeywordGroup);
		listOfKeywordGroup.add(RmuKeywordGroup);

		// logOut
		loginPage.logout();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// creating keyword as RMU
		keyPage.navigateToKeywordPage();
		baseClass.stepInfo("creating keyword as RMU");

		// keyPage.addKeyword(RmuKeyword,Input.KeyWordColour);
		keyPage.addKeyword(RmuKeywordGroup, RmuKeyword, Input.KeyWordColour);
		allKeywords.add(existingKeyword);
		allKeywords.add(RmuKeyword);
		String[] keywords = new String[listOfKeywordGroup.size()];
		listOfKeywordGroup.toArray(keywords);

		// verify that Add Keyword window pop-up with all keywords selected (added by
		// PAU and RMU as well)
		assignment.editAssignmentUsingPaginationConcept(assignmentName);
		assignment.verifyKeywordsAvailabilityInAssignment(keywords);
		assignment.verifyAddedKeywordsChecked();
		baseClass.passedStep(
				"verified that Add Keyword window pop-up with all keywords selected (added by PAU and RMU as well).");
		assignment.getSaveBtn().waitAndClick(5);

		// logOut
		loginPage.logout();

		// login as REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting assignment from DashBoard and navigating to DocView
		baseClass.stepInfo("selecting assignment from DashBoard and navigating to DocView.");
		assignment.SelectAssignmentByReviewer(assignmentName);

		// Keyword should be highlighted on the DocView
		docView.verifyKeywordsOnDocView(allKeywords, null);
		baseClass.ValidateElementCollection_Presence(docView.getAnnotations(), "Highlights in Document");
		baseClass.passedStep("verified that Keywords are highlighted on the DocView.");

		// logOut
		driver.waitForPageToBeReady();
		loginPage.logout();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// delete all keywords
		keyPage.navigateToKeywordPage();
		keyPage.deleteMultipleKeywords(listOfKeywordGroup);

		// delete assignment
		assignment.deleteAssignment(assignmentName);

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :Verify that EmailAuthorNameAndAddress should be displayed in
	 *              'Sort By Metadata' drop down below document presentation
	 *              sequence.RPMXCON-54318
	 */

	@Test(description = "RPMXCON-54318", enabled = true, groups = { "regression" })
	public void verifyEmailAuthorNameAndAdrsDisplayedInSortByMetadata() throws InterruptedException {

		String assignmentName = "Assignment" + Utility.dynamicNameAppender();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		baseClass.stepInfo(
				"Verify that EmailAuthorNameAndAddress should be displayed in 'Sort By Metadata' drop down below document presentation sequence.");
		baseClass.stepInfo("Test case Id: RPMXCON-54318.");

		// create Assignment
		assignment.createAssignment(assignmentName, Input.codeFormName);
		// select and Edit Assignment
		assignment.editAssignmentUsingPaginationConcept(assignmentName);

		// verify that EmailAuthorNameAndAddress is displayed in the DOCUMENT
		// PRESENTATION SEQUENCE > Sort by Metadata drop down list.
		assignment.verifyConfigureBtnAndSelectSortBymetaData("EmailAuthorNameAndAddress", "yes");
		baseClass.passedStep(
				"verified that EmailAuthorNameAndAddress is displayed in the DOCUMENT PRESENTATION SEQUENCE > Sort by Metadata drop down list.");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :Verify that 'EmailAuthorNameAndAddress' field should be
	 *              displayed on metadata list while creating
	 *              assignment.RPMXCON-54316
	 */

	@Test(description = "RPMXCON-54316", enabled = true, groups = { "regression" })
	public void verifyDisplayedOnMetadataListWhileCreatingAsgn() throws InterruptedException {

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		baseClass.stepInfo(
				"Verify that 'EmailAuthorNameAndAddress' field should be displayed on metadata list while creating assignment.");
		baseClass.stepInfo("Test case Id: RPMXCON-54316.");

		// navigating to create new Assignment Page
		baseClass.stepInfo("navigating to create new Assignment Page.");
		assignment.navigateToAssignmentsPage();
		assignment.NavigateToNewEditAssignmentPage("new");

		// Verify that in Create New Assignment page 'EmailAuthorNameAndAddress' field
		// should be displayed in the Select metadata pop up under Assigned Metadata
		// list
		baseClass.waitForElement(assignment.GetSelectMetaDataBtn());
		assignment.GetSelectMetaDataBtn().waitAndClick(5);
		baseClass.ValidateElement_Presence(assignment.getSelectAssignedMDinAssignPage("EmailAuthorNameAndAddress"),
				"EmailAuthorNameAndAddress field");
		baseClass.passedStep(
				"Verified that in Create New Assignment page 'EmailAuthorNameAndAddress' field should be displayed in the Select metadata pop up under Assigned Metadata list.");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :Verify that user can save/sort single or multiple coding form
	 *              successfully while creating new assignment in root assignment
	 *              group for new project.RPMXCON-67535
	 */

	@Test(description = "RPMXCON-67535", enabled = true, groups = { "regression" })
	public void verifyUserSortSingleOrMultipleCFWhileCreatingNewAssignmentInRootAssignmentGroup()
			throws InterruptedException {
		List<String> listOfCFAfterSorting01 = new ArrayList<String>();
		List<String> listOfCFAfterSorting02 = new ArrayList<String>();
		String assignmentName01 = "assignment" + Utility.dynamicNameAppender();
		String assignmentName02 = "assignment" + Utility.dynamicNameAppender();
		List<String> listOfAssignments = new ArrayList<String>(Arrays.asList(assignmentName01, assignmentName02));
		DocViewPage docView = new DocViewPage(driver);
		CodingForm codingForm = new CodingForm(driver);

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-67535");
		baseClass.stepInfo(
				"Verify that user can save/sort single or multiple coding form successfully while creating new assignment in root assignment group for new project.");

		// creating CF based on condition
		List<String> listOfCodingForm = codingForm.createCodingformBasedOnCondition(3);

		// creating assignment under Root assignment group
		baseClass.stepInfo("creating assignment under Root assignment group");
		assignment.navigateToAssignmentsPage();
		listOfCFAfterSorting01 = assignment.createAssignmentFromAssgnGroupByChangeSortingSequenceOfCF(assignmentName01,
				Input.codeFormName);

		// bulk Assign
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assignmentName01);

		// adding Reviewers and Distributing the Documents
		baseClass.stepInfo("adding Reviewers and Distributing the Documents to Reviewers");
		assignment.editAssignmentUsingPaginationConcept(assignmentName01);
		assignment.add2ReviewerAndDistribute(Input.rev1userName,Input.rmu1userName);

		// logout
		loginPage.logout();

		// login as Rev
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting assignment from dashBoard to view in DocView
		baseClass.stepInfo("selecting assignment from dashBoard to view in DocView");
		assignment.SelectAssignmentByReviewer(assignmentName01);

		// verify that coding forms in Coding Form dropdown from doc view loaded as per
		// the saved sort order.
		docView.getDocView_CodingFormlist().waitAndClick(5);
		List<String> listOfCFInDocViewDropDown01 = baseClass
				.availableListofElements(docView.listOfCodingFormInDocViewDropDown());
		baseClass.compareListViaContains(listOfCFAfterSorting01, listOfCFInDocViewDropDown01);
		baseClass.passedStep(
				"verified that coding forms in Coding Form dropdown from doc view loaded as per the saved sort order.");

		// logOut
		loginPage.logout();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// creating assignment under Root assignment group
		baseClass.stepInfo("creating assignment under  Root assignment group.");
		assignment.navigateToAssignmentsPage();
		assignment.createAssignmentFromAssgnGroup(assignmentName02, Input.codingFormName);
		listOfCFAfterSorting02.add(Input.codingFormName);

		// bulk Assign
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assignmentName02);

		// adding Reviewers and Distributing the Documents
		baseClass.stepInfo("adding Reviewers and Distributing the Documents to Reviewers");
		assignment.editAssignmentUsingPaginationConcept(assignmentName02);
		assignment.add2ReviewerAndDistribute(Input.rev1userName,Input.rmu1userName);

		// logout
		loginPage.logout();

		// login as Rev
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting assignment from dashBoard to view in DocView
		baseClass.stepInfo("selecting assignment from dashBoard to view in DocView");
		assignment.SelectAssignmentByReviewer(assignmentName02);

		// verify that coding forms in Coding Form dropdown from doc view loaded as per
		// the saved sort order.
		docView.getDocView_CodingFormlist().waitAndClick(5);
		List<String> listOfCFInDocViewDropDown02 = baseClass
				.availableListofElements(docView.listOfCodingFormInDocViewDropDown());
		baseClass.compareListViaContains(listOfCFAfterSorting02, listOfCFInDocViewDropDown02);
		baseClass.passedStep(
				"verified that coding forms in Coding Form dropdown from doc view loaded as per the saved sort order.");

		// logout
		loginPage.logout();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// delete assignments
		assignment.deleteMultipleAssgnmntUsingPagination(listOfAssignments);

		// delete multiplecoding form
		codingForm.navigateToCodingFormPage();
		codingForm.DeleteMultipleCodingform(listOfCodingForm);

		// logout
		loginPage.logout();
	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :Verify that user can save/sort single or multiple coding form
	 *              successfully while creating new assignment in a user created
	 *              assignment group with cascade setting OFF for existing
	 *              project.RPMXCON-67512
	 */

	@Test(description = "RPMXCON-67512", enabled = true, groups = { "regression" })
	public void verifyUserSortSingleOrMultipleCFWhileCreatingNewAssignmentInAssignGrpWithCascadeSettingOff()
			throws InterruptedException {
		List<String> listOfCFAfterSorting01 = new ArrayList<String>();
		List<String> listOfCFAfterSorting02 = new ArrayList<String>();
		String assignmentGroup01 = "assignmentGroup" + Utility.dynamicNameAppender();
		String assignmentGroup02 = "assignmentGroup" + Utility.dynamicNameAppender();
		String assignmentName01 = "assignment" + Utility.dynamicNameAppender();
		String assignmentName02 = "assignment" + Utility.dynamicNameAppender();
		DocViewPage docView = new DocViewPage(driver);
		CodingForm codingForm = new CodingForm(driver);

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-67512");
		baseClass.stepInfo(
				"Verify that user can save/sort single or multiple coding form successfully while creating new assignment in a user created assignment group with cascade setting OFF for existing project.");

		// creating CF based on condition
		List<String> listOfCodingForm = codingForm.createCodingformBasedOnCondition(3);

		// creating assignment Group with cascading setting On
		assignment.navigateToAssignmentsPage();
		assignment.createCascadeNonCascadeAssgnGroup(assignmentGroup01, "No");
		baseClass.passedStep("creating assignment Group : '" + assignmentGroup01 + "' with cascading setting OFF");

		// creating assignment under newly created Assignment group
		baseClass.stepInfo("creating assignment under newly created Assignment group");
		assignment.selectAssignmentGroup(assignmentGroup01);
		listOfCFAfterSorting01 = assignment.createAssignmentFromAssgnGroupByChangeSortingSequenceOfCF(assignmentName01,
				Input.codeFormName);

		// bulk Assign
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assignmentName01);

		// adding Reviewers and Distributing the Documents
		baseClass.stepInfo("adding Reviewers and Distributing the Documents to Reviewers");
		assignment.editAssignmentInAssignGroup(assignmentGroup01, assignmentName01);
		assignment.add2ReviewerAndDistribute(Input.rev1userName,Input.rmu1userName);

		// logout
		loginPage.logout();

		// login as Rev
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting assignment from dashBoard to view in DocView
		baseClass.stepInfo("selecting assignment from dashBoard to view in DocView");
		assignment.SelectAssignmentByReviewer(assignmentName01);

		// verify that coding forms in Coding Form dropdown from doc view loaded as per
		// the saved sort order.
		docView.getDocView_CodingFormlist().waitAndClick(5);
		List<String> listOfCFInDocViewDropDown01 = baseClass
				.availableListofElements(docView.listOfCodingFormInDocViewDropDown());
		baseClass.compareListViaContains(listOfCFAfterSorting01, listOfCFInDocViewDropDown01);
		baseClass.stepInfo(
				"verified that coding forms in Coding Form dropdown from doc view loaded as per the saved sort order.");

		// logOut
		loginPage.logout();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// creating assignment Group with cascading setting On
		assignment.navigateToAssignmentsPage();
		assignment.createCascadeNonCascadeAssgnGroup(assignmentGroup02, "No");
		baseClass.passedStep("creating assignment Group : '" + assignmentGroup02 + "' with cascading setting OFF");

		// creating assignment under newly created Assignment group
		baseClass.stepInfo("creating assignment under newly created Assignment group");
		assignment.selectAssignmentGroup(assignmentGroup02);
		listOfCFAfterSorting02.add(Input.codingFormName);
		assignment.createAssignmentFromAssgnGroup(assignmentName02, Input.codingFormName);

		// bulk Assign
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assignmentName02);

		// adding Reviewers and Distributing the Documents
		baseClass.stepInfo("adding Reviewers and Distributing the Documents to Reviewers");
		assignment.editAssignmentInAssignGroup(assignmentGroup02, assignmentName02);
		assignment.add2ReviewerAndDistribute(Input.rev1userName,Input.rmu1userName);

		// logout
		loginPage.logout();

		// login as Rev
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting assignment from dashBoard to view in DocView
		baseClass.stepInfo("selecting assignment from dashBoard to view in DocView");
		assignment.SelectAssignmentByReviewer(assignmentName02);

		// verify that coding forms in Coding Form dropdown from doc view loaded as per
		// the saved sort order.
		docView.getDocView_CodingFormlist().waitAndClick(5);
		List<String> listOfCFInDocViewDropDown02 = baseClass
				.availableListofElements(docView.listOfCodingFormInDocViewDropDown());
		baseClass.compareListViaContains(listOfCFAfterSorting02, listOfCFInDocViewDropDown02);
		baseClass.stepInfo(
				"verified that coding forms in Coding Form dropdown from doc view loaded as per the saved sort order.");

		// logout
		loginPage.logout();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// delete assignments and assignment Groups
		assignment.navigateToAssignmentsPage();
		assignment.deleteAssignmentFromSingleAssgnGrp(assignmentGroup01, assignmentName01);
		assignment.DeleteAssgnGroup(assignmentGroup01);
		assignment.deleteAssignmentFromSingleAssgnGrp(assignmentGroup02, assignmentName02);
		assignment.DeleteAssgnGroup(assignmentGroup02);

		// delete multiplecoding form
		codingForm.navigateToCodingFormPage();
		codingForm.DeleteMultipleCodingform(listOfCodingForm);

		// logout
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: 11/10/2022 TestCase Id:RPMXCON-54755 Description
	 * :De-associate keywords - Assignment should not display the de-associated
	 * keyword
	 * 
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-54755", enabled = true, groups = { "regression" })
	public void verifyDeassociatedKeywordInAssignment() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-54755");
		baseClass.stepInfo("verify reviewers name display in distribute document section");
		String assignmentName = "assign" + Utility.dynamicNameAppender();
		String[] associatedKeyword = { "Akey1", "Akey2" };
		String[] deassociatedKeyword = { "Dkey1", "DKey2" };

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");
		// Create a keyword and assignment
		KeywordPage keyPage = new KeywordPage(driver);
		
		for (int i = 0; i < associatedKeyword.length; i++) {
			keyPage.navigateToKeywordPage();
			keyPage.addKeyword(associatedKeyword[i], "Red");
		}
		for (int j = 0; j < deassociatedKeyword.length; j++) {
			keyPage.navigateToKeywordPage();
			keyPage.addKeyword(deassociatedKeyword[j], "Blue");
		}
		assignment.createAssignment(assignmentName, Input.codingFormName);
		loginPage.logout();
		baseClass.stepInfo("Login as PA and de-associate few keywords");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		// deleting few keywords
		for (int i = 0; i < deassociatedKeyword.length; i++) {
			keyPage.deleteKeywordByName(deassociatedKeyword[i]);
		}
		loginPage.logout();
		// login as RMU and verify existing assignment
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");
		baseClass.stepInfo("Select existing assignment ");
		assignment.editAssignmentUsingPaginationConcept(assignmentName);
		baseClass.stepInfo("verify associated keyword availability");
		assignment.verifyKeywordsAvailabilityInAssignment(associatedKeyword);
		baseClass.stepInfo("verify de-associated keyword availability");
		assignment.verifyDeAssociatedKeywordsAvailabilityInAssignment(deassociatedKeyword);
		loginPage.logout();
	}

	/**
	 * @author
	 * @Date: 12/10/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description Verify that the full Assignment name appears in a mouse over
	 *              tool tip, and the same is true of Assignment Groups.
	 *              RPMXCON-54341
	 */

	@Test(description = "RPMXCON-54341", enabled = true, groups = { "regression" })
	public void verifyFullAssignmnetNameAppearsInMouseOverToolTipAndSameForAssignmentGroups()
			throws InterruptedException {

		String assignmentGroup = "assignmentGroup" + Utility.dynamicNameAppender();
		String assignmentName = "assignment" + Utility.dynamicNameAppender();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		baseClass.stepInfo(
				"Verify that the full Assignment name appears in a mouse over tool tip, and the same is true of Assignment Groups.");
		baseClass.stepInfo("Test case Id: RPMXCON-54341");

		// creating assignment group
		assignment.navigateToAssignmentsPage();
		assignment.createCascadeNonCascadeAssgnGroup(assignmentGroup, "Yes");
		baseClass.passedStep("assignment Group : '" + assignmentGroup + "' Created");

		// creating assignment under newly created Assignment group
		baseClass.stepInfo("creating assignment under newly created Assignment group");
		assignment.selectAssignmentGroup(assignmentGroup);
		assignment.createAssignmentFromAssgnGroup(assignmentName, Input.codeFormName);

		// performing BulkAssign
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();

		// verify that when select action as Assign documents and mouse over to the
		// assignment group Full Assignment group name appears in a mouse over tool tip
		sessionSearch.verifyToolTipTextInAssignUnassignPopUp(assignmentGroup, "Assign");
		baseClass.passedStep(
				" verified that when select action as Assign documents and mouse over to the assignment group Full Assignment group name appears in a mouse over tool tip.");
		// verify that when select action as Unassign documents and mouse over to the
		// assignment group Full Assignment group name appears in a mouse over tool tip
		sessionSearch.verifyToolTipTextInAssignUnassignPopUp(assignmentGroup, "Unassign");
		baseClass.passedStep(
				"verified that when select action as Unassign documents and mouse over to the assignment group Full Assignment group name appears in a mouse over tool tip.");
		// verify that when select action as Assign documents and mouse over to the
		// assignment Full Assignment name appears in a mouse over tool tip
		sessionSearch.verifyToolTipTextInAssignUnassignPopUp(assignmentName, "Assign");
		baseClass.passedStep(
				"verified that when select action as Assign documents and mouse over to the assignment Full Assignment name appears in a mouse over tool tip.");
		// verify that when select action as Unassign documents and mouse over to the
		// assignment Full Assignment name appears in a mouse over tool tip
		sessionSearch.verifyToolTipTextInAssignUnassignPopUp(assignmentName, "Unassign");
		baseClass.passedStep(
				"verified that when select action as Unassign documents and mouse over to the assignment Full Assignment name appears in a mouse over tool tip.");

		// delete assignments and assignment Groups
		assignment.navigateToAssignmentsPage();
		assignment.deleteAssignmentFromSingleAssgnGrp(assignmentGroup, assignmentName);
		assignment.DeleteAssgnGroup(assignmentGroup);

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :Verify that after a Unassign from Existing Assignments is
	 *              complete, the system actually removes those selected DocIDs from
	 *              all selected Assignment(s).RPMXCON-67512
	 */

	@Test(description = "RPMXCON-54332", enabled = true, groups = { "regression" })
	public void verifyAfterUnassignExistingAssignmentsSystemRemovesSelectedDocIDsFromAllSelectedAssignments()
			throws InterruptedException {

		String assignmentGroup01 = "assignmentGroup" + Utility.dynamicNameAppender();
		String assignmentGroup02 = "assignmentGroup" + Utility.dynamicNameAppender();
		String assignmentName01 = "assignment" + Utility.dynamicNameAppender();
		String assignmentName02 = "assignment" + Utility.dynamicNameAppender();
		List<String> listOfAssignments = new ArrayList<String>(Arrays.asList(assignmentName01, assignmentName02));
		Map<String, Integer> pairOfAssignmentsAndExpectedTotalDocCountInAssign01 = new HashMap<String, Integer>();
		Map<String, Integer> pairOfAssignmentsAndExpectedTotalDocCountInAssign02 = new HashMap<String, Integer>();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-67512");
		baseClass.stepInfo(
				"Verify that after a Unassign from Existing Assignments is complete, the system actually removes those selected DocIDs from all selected Assignment(s).");

		// creating assignment Group
		assignment.navigateToAssignmentsPage();
		assignment.createCascadeNonCascadeAssgnGroup(assignmentGroup01, "Yes");
		baseClass.passedStep("assignment Group : '" + assignmentGroup01 + "' Created.");

		// creating assignment under newly created Assignment group
		baseClass.stepInfo("creating assignment under newly created Assignment group");
		assignment.selectAssignmentGroup(assignmentGroup01);
		assignment.createAssignmentFromAssgnGroup(assignmentName01, Input.codeFormName);

		// creating assignment Group
		assignment.navigateToAssignmentsPage();
		assignment.createCascadeNonCascadeAssgnGroup(assignmentGroup02, "Yes");
		baseClass.passedStep("assignment Group : '" + assignmentGroup01 + "' Created.");

		// creating assignment under newly created Assignment group
		baseClass.stepInfo("creating assignment under newly created Assignment group");
		assignment.selectAssignmentGroup(assignmentGroup02);
		assignment.createAssignmentFromAssgnGroup(assignmentName02, Input.codeFormName);

		// perform bulk Assign
		int pureHitCount = sessionSearch.basicContentSearch(Input.searchString1);
		pairOfAssignmentsAndExpectedTotalDocCountInAssign01.put(assignmentName01, pureHitCount);
		pairOfAssignmentsAndExpectedTotalDocCountInAssign02.put(assignmentName02, pureHitCount);
		sessionSearch.bulkAssignForMultipleExistingAssignments(listOfAssignments);

		// verifying whether the documents are assigned to assignments by checking the
		// TotalDoc column in assignment page
		assignment.selectAssignmentGroup(assignmentGroup01);
		assignment.validateTotalDocumentCountInManageAssignmentPageForMultipleAssignments(
				pairOfAssignmentsAndExpectedTotalDocCountInAssign01);
		assignment.selectAssignmentGroup(assignmentGroup02);
		assignment.validateTotalDocumentCountInManageAssignmentPageForMultipleAssignments(
				pairOfAssignmentsAndExpectedTotalDocCountInAssign02);

		// verifying Continue button should not be active until at least one Assignment
		// is selected from the Assignments/Group tree.
		sessionSearch.bulkAssign();
		sessionSearch.getBulkUntagbutton().waitAndClick(10);
		softAssert.assertEquals((boolean) sessionSearch.getContinueButton().Enabled(), false);
		baseClass.passedStep(
				"verified that Continue button should not be active until at least one Assignment is selected from the Assignments/Group tree.");

		// click Show Document Count Toggle
		sessionSearch.getShowDocumentCountToggle().waitAndClick(5);
		baseClass.stepInfo("Show Document Counts toggle is ON.");
		// perform Bulk Unassign
		sessionSearch.UnAssignMultipleExistingAssignments(listOfAssignments);

		// verifying whether the documents are Unassigned From the assignments by
		// checking the TotalDoc column in assignment page
		pairOfAssignmentsAndExpectedTotalDocCountInAssign01.replace(assignmentName01, 0);
		pairOfAssignmentsAndExpectedTotalDocCountInAssign02.replace(assignmentName02, 0);
		assignment.selectAssignmentGroup(assignmentGroup01);
		assignment.validateTotalDocumentCountInManageAssignmentPageForMultipleAssignments(
				pairOfAssignmentsAndExpectedTotalDocCountInAssign01);
		assignment.selectAssignmentGroup(assignmentGroup02);
		assignment.validateTotalDocumentCountInManageAssignmentPageForMultipleAssignments(
				pairOfAssignmentsAndExpectedTotalDocCountInAssign02);
		baseClass.passedStep(
				"Verified that After performing Bulk Unassign system remove those selected documents from all selected assignments");

		// delete assignments and assignment Groups
		assignment.deleteAssignmentFromSingleAssgnGrp(assignmentGroup01, assignmentName01);
		assignment.DeleteAssgnGroup(assignmentGroup01);
		assignment.deleteAssignmentFromSingleAssgnGrp(assignmentGroup02, assignmentName02);
		assignment.DeleteAssgnGroup(assignmentGroup02);

		// logout
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: 21/09/2022 TestCase Id:RPMXCON-53654 Description :To
	 * verify that in Distribute Document section it displays Reviewers name as Last
	 * name First Name and User Name
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53654", enabled = true, groups = { "regression" })
	public void verifyReviewerNameFormat() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53654");
		baseClass.stepInfo("verify reviewers name display in distribute document section");
		String assignmentName = "assign" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in as REV");
		baseClass.stepInfo("get first and last name of reviewer");
		baseClass.waitForElement(loginPage.getSignoutMenu());
		loginPage.getSignoutMenu().waitAndClick(10);
		baseClass.waitForElement(loginPage.getEditProfile());
		loginPage.getEditProfile().waitAndClick(10);
		baseClass.waitForElement(loginPage.getFirstName());
		String firstName = loginPage.getFirstName().GetAttribute("value");
		baseClass.waitForElement(loginPage.getLastName());
		String lastName = loginPage.getLastName().GetAttribute("value");
		loginPage.getEditprofilesave().waitAndClick(10);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");
		baseClass.stepInfo("create new assignment");
		assignment.createAssignment(assignmentName, Input.codingFormName);
		baseClass.stepInfo("Edit assignment -add reviewer");
		assignment.editAssignmentUsingPaginationConcept(assignmentName);
		assignment.addReviewerInManageReviewerTab();
		baseClass.stepInfo("verify reviewer name format in distribute documents section");
		baseClass.waitForElement(assignment.getDistributeTab());
		assignment.getDistributeTab().waitAndClick(10);
		baseClass.waitForElement(assignment.getReviewerInDistributeTab(Input.rev1userName));
		if (assignment.getReviewerInDistributeTab(Input.rev1userName).isElementAvailable(10)) {
			String actualUser = assignment.getReviewerInDistributeTab(Input.rev1userName).getText().trim();
			baseClass.stepInfo("Actual username format: " + actualUser);
			String expected = lastName + ", " + firstName + "(" + Input.rev1userName + ")";
			baseClass.stepInfo("Expected username format: " + expected);
			softAssert.assertEquals(actualUser, expected);
			softAssert.assertAll();
		} else {
			baseClass.failedStep("Added reviewer not available in distribute docs tab");
		}
		baseClass.passedStep("Reviewer name displayed in expected format");
		loginPage.logout();

	}

	/**
	 * Author :Arunkumar date: 22/09/2022 TestCase Id:RPMXCON-53634 Description :To
	 * verify that RMU is able to view Assingment Group and Assignments tree in
	 * Assign/Unassign Document pop up wrt to Projects.
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53634", enabled = true, groups = { "regression" })
	public void verifyAssiignUnassignPopupAsRMU() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53634");
		baseClass.stepInfo("verify assign/Unassign popup wrt projects");
		String[] project = { Input.projectName, Input.additionalDataProject };
		String[] assignName = { "assign1" + Utility.dynamicNameAppender(), "assign2" + Utility.dynamicNameAppender() };
		String[] assignGroup = { "assignGroup1" + Utility.dynamicNameAppender(),
				"assignGroup2" + Utility.dynamicNameAppender() };

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");
		baseClass.stepInfo("create assignment and group wrt to project");
		for (int i = 0; i < project.length; i++) {
			baseClass.selectproject(project[i]);
			assignment.navigateToAssignmentsPage();
			assignment.createAssgnGroup(assignGroup[i]);
			assignment.selectAssignmentGroup(assignGroup[i]);
			assignment.createAssignment(assignName[i], Input.codeFormName);
		}
		baseClass.selectproject(project[0]);
		baseClass.stepInfo("go to search and perform new search");
		sessionSearch.basicContentSearch("text");
		baseClass.stepInfo("drag the search result tile and select bulk assign");
		sessionSearch.addPureHit();
		sessionSearch.bulkAssign();
		assignment.verifyBulkAssignPopupWrtToProject(assignName[0], assignName[1]);
		baseClass.stepInfo("change the project and verify the bulk assign popup");
		baseClass.selectproject(project[1]);
		driver.waitForPageToBeReady();
		sessionSearch.basicContentSearch("text");
		baseClass.stepInfo("drag the search result tile and select bulk assign");
		sessionSearch.addPureHit();
		sessionSearch.bulkAssign();
		assignment.verifyBulkAssignPopupWrtToProject(assignName[1], assignName[0]);
		loginPage.logout();

	}

	/**
	 * @author
	 * @Date: 23/9/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :Verify that user can save/sort single or multiple coding form
	 *              successfully while editing existing assignment in a user created
	 *              assignment group with cascade setting ON for existing project
	 */

	@Test(description = "RPMXCON-67519", enabled = true, groups = { "regression" })
	public void verifyUserSortSingleOrMultipleCFWhileEditingExistingAssignmentInAssignGrpWithCascadeSettingOn()
			throws InterruptedException {
		List<String> listOfCFAfterSorting01 = new ArrayList<String>();
		List<String> listOfCFAfterSorting02 = new ArrayList<String>();
		listOfCFAfterSorting02.add(Input.codeFormName);
		String assignmentGroup01 = "assignmentGroup" + Utility.dynamicNameAppender();
		String assignmentGroup02 = "assignmentGroup" + Utility.dynamicNameAppender();
		String assignmentName01 = "assignment" + Utility.dynamicNameAppender();
		String assignmentName02 = "assignment" + Utility.dynamicNameAppender();
		DocViewPage docView = new DocViewPage(driver);
		CodingForm codingForm = new CodingForm(driver);

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-67519");
		baseClass.stepInfo(
				"Verify that user can save/sort single or multiple coding form successfully while editing existing assignment in a user created assignment group with cascade setting ON for existing project");

		// creating CF based on condition
		List<String> listOfCodingForm = codingForm.createCodingformBasedOnCondition(3);

		// creating assignment Group with cascading setting On
		assignment.navigateToAssignmentsPage();
		assignment.createCascadeNonCascadeAssgnGroup(assignmentGroup01, "Yes");
		baseClass.passedStep("creating assignment Group : '" + assignmentGroup01 + "' with cascading setting On");

		// creating assignment under newly created Assignment group
		baseClass.stepInfo("creating assignment under newly created Assignment group");
		assignment.selectAssignmentGroup(assignmentGroup01);
		assignment.createAssignmentFromAssgnGroup(assignmentName01, Input.codingFormName);

		// bulk Assign
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assignmentName01);

		// adding Reviewers and Distributing the Documents
		baseClass.stepInfo("adding Reviewers and Distributing the Documents to Reviewers");
		assignment.editAssignmentInAssignGroup(assignmentGroup01, assignmentName01);
		assignment.add2ReviewerAndDistribute(Input.rev1userName,Input.rmu1userName);

		assignment.editAssignmentInAssignGroup(assignmentGroup01, assignmentName01);
		listOfCFAfterSorting01 = assignment.SelectAllCodingFormAndChangeSortingSequence(Input.codingFormName);
		baseClass.waitForElement(assignment.getAssignmentSaveButton());
		assignment.getAssignmentSaveButton().waitAndClick(3);

		// logout
		loginPage.logout();

		// login as Rev
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting assignment from dashBoard to view in DocView
		baseClass.stepInfo("selecting assignment from dashBoard to view in DocView");
		assignment.SelectAssignmentByReviewer(assignmentName01);

		// verify that coding forms in Coding Form dropdown from doc view loaded as per
		// the saved sort order.
		docView.getDocView_CodingFormlist().waitAndClick(5);
		List<String> listOfCFInDocViewDropDown01 = baseClass
				.availableListofElements(docView.listOfCodingFormInDocViewDropDown());
		baseClass.compareListViaContains(listOfCFAfterSorting01, listOfCFInDocViewDropDown01);
		baseClass.stepInfo(
				"verified that coding forms in Coding Form dropdown from doc view loaded as per the saved sort order.");

		// logOut
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// creating assignment Group with cascading setting On
		assignment.navigateToAssignmentsPage();
		assignment.createCascadeNonCascadeAssgnGroup(assignmentGroup02, "Yes");
		baseClass.passedStep("creating assignment Group : '" + assignmentGroup02 + "' with cascading setting On");

		// creating assignment under newly created Assignment group
		baseClass.stepInfo("creating assignment under newly created Assignment group");
		assignment.selectAssignmentGroup(assignmentGroup02);
		assignment.createAssignmentFromAssgnGroup(assignmentName02, listOfCFAfterSorting01.get(1));

		// bulk Assign
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assignmentName02);

		// adding Reviewers and Distributing the Documents
		baseClass.stepInfo("adding Reviewers and Distributing the Documents to Reviewers");
		assignment.editAssignmentInAssignGroup(assignmentGroup02, assignmentName02);
		assignment.add2ReviewerAndDistribute(Input.rev1userName,Input.rmu1userName);

		assignment.editAssignmentInAssignGroup(assignmentGroup02, assignmentName02);
		List<String> listOfCFName = new ArrayList<String>();
		listOfCFName.add(Input.codeFormName);
		listOfCFAfterSorting02 = assignment.editExistingCodingForm(listOfCFName, Input.codeFormName, false);
		baseClass.waitForElement(assignment.getAssignmentSaveButton());
		assignment.getAssignmentSaveButton().waitAndClick(3);

		// logout
		loginPage.logout();

		// login as Rev
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting assignment from dashBoard to view in DocView
		baseClass.stepInfo("selecting assignment from dashBoard to view in DocView");
		assignment.SelectAssignmentByReviewer(assignmentName02);

		// verify that coding forms in Coding Form dropdown from doc view loaded as per
		// the saved sort order.
		docView.getDocView_CodingFormlist().waitAndClick(5);
		List<String> listOfCFInDocViewDropDown02 = baseClass
				.availableListofElements(docView.listOfCodingFormInDocViewDropDown());
		baseClass.compareListViaContains(listOfCFAfterSorting02, listOfCFInDocViewDropDown02);
		baseClass.stepInfo(
				"verified that coding forms in Coding Form dropdown from doc view loaded as per the saved sort order.");

		// logout
		loginPage.logout();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// delete assignments and assignment Groups
		assignment.navigateToAssignmentsPage();
		assignment.deleteAssignmentFromSingleAssgnGrp(assignmentGroup01, assignmentName01);
		assignment.DeleteAssgnGroup(assignmentGroup01);
		assignment.deleteAssignmentFromSingleAssgnGrp(assignmentGroup02, assignmentName02);
		assignment.DeleteAssgnGroup(assignmentGroup02);

		// delete multiplecoding form
		codingForm.navigateToCodingFormPage();
		codingForm.DeleteMultipleCodingform(listOfCodingForm);

		// logout
		loginPage.logout();

	}

	/**
	 * @author
	 * @Date: 23/9/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :Verify that user can save/sort single or multiple coding form
	 *              successfully while creating new assignment in a user created
	 *              assignment group with cascade setting ON for existing project.
	 */

	@Test(description = "RPMXCON-67520", enabled = true, groups = { "regression" })
	public void verifyUserSortSingleOrMultipleCFWhileCreatingNewAssignmentInAssignGrpWithCascadeSettingOn()
			throws InterruptedException {
		List<String> listOfCFAfterSorting01 = new ArrayList<String>();
		List<String> listOfCFAfterSorting02 = new ArrayList<String>();
		String assignmentGroup01 = "assignmentGroup" + Utility.dynamicNameAppender();
		String assignmentGroup02 = "assignmentGroup" + Utility.dynamicNameAppender();
		String assignmentName01 = "assignment" + Utility.dynamicNameAppender();
		String assignmentName02 = "assignment" + Utility.dynamicNameAppender();
		DocViewPage docView = new DocViewPage(driver);
		CodingForm codingForm = new CodingForm(driver);

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-67520");
		baseClass.stepInfo(
				"Verify that user can save/sort single or multiple coding form successfully while creating new assignment in a user created assignment group with cascade setting ON for existing project.");

		// creating CF based on condition
		List<String> listOfCodingForm = codingForm.createCodingformBasedOnCondition(3);

		// creating assignment Group with cascading setting On
		assignment.navigateToAssignmentsPage();
		assignment.createCascadeNonCascadeAssgnGroup(assignmentGroup01, "Yes");
		baseClass.passedStep("creating assignment Group : '" + assignmentGroup01 + "' with cascading setting On");

		// creating assignment under newly created Assignment group
		baseClass.stepInfo("creating assignment under newly created Assignment group");
		assignment.selectAssignmentGroup(assignmentGroup01);
		listOfCFAfterSorting01 = assignment.createAssignmentFromAssgnGroupByChangeSortingSequenceOfCF(assignmentName01,
				Input.codeFormName);

		// bulk Assign
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assignmentName01);

		// adding Reviewers and Distributing the Documents
		baseClass.stepInfo("adding Reviewers and Distributing the Documents to Reviewers");
		assignment.editAssignmentInAssignGroup(assignmentGroup01, assignmentName01);
		assignment.add2ReviewerAndDistribute(Input.rev1userName,Input.rmu1userName);

		// logout
		loginPage.logout();

		// login as Rev
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting assignment from dashBoard to view in DocView
		baseClass.stepInfo("selecting assignment from dashBoard to view in DocView");
		assignment.SelectAssignmentByReviewer(assignmentName01);

		// verify that coding forms in Coding Form dropdown from doc view loaded as per
		// the saved sort order.
		docView.getDocView_CodingFormlist().waitAndClick(5);
		List<String> listOfCFInDocViewDropDown01 = baseClass
				.availableListofElements(docView.listOfCodingFormInDocViewDropDown());
		baseClass.compareListViaContains(listOfCFAfterSorting01, listOfCFInDocViewDropDown01);
		baseClass.stepInfo(
				"verified that coding forms in Coding Form dropdown from doc view loaded as per the saved sort order.");

		// logOut
		loginPage.logout();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// creating assignment Group with cascading setting On
		assignment.navigateToAssignmentsPage();
		assignment.createCascadeNonCascadeAssgnGroup(assignmentGroup02, "Yes");
		baseClass.passedStep("creating assignment Group : '" + assignmentGroup02 + "' with cascading setting On");

		// creating assignment under newly created Assignment group
		baseClass.stepInfo("creating assignment under newly created Assignment group");
		assignment.selectAssignmentGroup(assignmentGroup02);
		listOfCFAfterSorting02.add(Input.codingFormName);
		assignment.createAssignmentFromAssgnGroup(assignmentName02, Input.codingFormName);

		// bulk Assign
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assignmentName02);

		// adding Reviewers and Distributing the Documents
		baseClass.stepInfo("adding Reviewers and Distributing the Documents to Reviewers");
		assignment.editAssignmentInAssignGroup(assignmentGroup02, assignmentName02);
		assignment.add2ReviewerAndDistribute(Input.rev1userName,Input.rmu1userName);

		// logout
		loginPage.logout();

		// login as Rev
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting assignment from dashBoard to view in DocView
		baseClass.stepInfo("selecting assignment from dashBoard to view in DocView");
		assignment.SelectAssignmentByReviewer(assignmentName02);

		// verify that coding forms in Coding Form dropdown from doc view loaded as per
		// the saved sort order.
		docView.getDocView_CodingFormlist().waitAndClick(5);
		List<String> listOfCFInDocViewDropDown02 = baseClass
				.availableListofElements(docView.listOfCodingFormInDocViewDropDown());
		baseClass.compareListViaContains(listOfCFAfterSorting02, listOfCFInDocViewDropDown02);
		baseClass.stepInfo(
				"verified that coding forms in Coding Form dropdown from doc view loaded as per the saved sort order.");

		// logout
		loginPage.logout();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// delete assignments and assignment Groups
		assignment.navigateToAssignmentsPage();
		assignment.deleteAssignmentFromSingleAssgnGrp(assignmentGroup01, assignmentName01);
		assignment.DeleteAssgnGroup(assignmentGroup01);
		assignment.deleteAssignmentFromSingleAssgnGrp(assignmentGroup02, assignmentName02);
		assignment.DeleteAssgnGroup(assignmentGroup02);

		// delete multiplecoding form
		codingForm.navigateToCodingFormPage();
		codingForm.DeleteMultipleCodingform(listOfCodingForm);

		// logout
		loginPage.logout();
	}

	/**
	 * @author
	 * @Date: 27/9/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :Verify that configured metadata as per the assignmnet group
	 *              should retain for assignment after distributing documents to the
	 *              user. RPMXCON-54281
	 */

	@Test(description = "RPMXCON-54281", enabled = true, groups = { "regression" })
	public void verifyConfiguredMetadataAsPerAssignmentGroupRetainAssignmentAfterDistributingDocumentsToUser()
			throws InterruptedException {

		String assignGroup = "assignmentGroup" + Utility.dynamicNameAppender();
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id:RPMXCON-54281");
		baseClass.stepInfo(
				"Verify that configured metadata as per the assignmnet group should retain for assignment after distributing documents to the user");

		// create Assignment Group
		baseClass.stepInfo("creating Assignment Group");
		assignment.navigateToAssignmentsPage();
		assignment.createCascadeNonCascadeAssgnGroup_withoutSave(assignGroup, "Yes");

		// getting the list of assigned metadata from assignment group
		baseClass.stepInfo("getting the list of assigned metadata from assignment group");
		List<String> ListOfAssignedMetadata = assignment.getAllAssignedOrUnAssignedMetadataFromConfigureMetadata(true);

		// getting the list of keywords from assignment group
		baseClass.stepInfo(" getting the list of keywords from assignment group");
		List<String> ListOfKeywords = assignment.getAllKeywordsFromAddKeywordPopup();
		assignment.getAssignmentSaveButton().waitAndClick(5);

		// peforming basic search and creating assignment using bulk assign
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignWithNewAssgn(assignGroup);
		assignment.createAssignmentByBulkAssignOperation(assignmentName, Input.codingFormName);

		// adding reviewer and Distributing the Documents
		assignment.editAssignmentInAssignGroup(assignGroup, assignmentName);
		assignment.add2ReviewerAndDistribute(Input.rev1userName,Input.rmu1userName);
		baseClass.waitForElement(assignment.getAssignmentSaveButton());
		assignment.getAssignmentSaveButton().waitAndClick(5);

		// getting the list of assigned metadata from assignment
		baseClass.stepInfo("getting the list of assigned metadata from assignment");
		List<String> ListOfAssignedMetadata02 = assignment
				.getAllAssignedOrUnAssignedMetadataFromConfigureMetadata(true);
		// getting the list of keywords from assignment
		baseClass.stepInfo("getting the list of keywords from assignment");
		List<String> ListOfKeywords02 = assignment.getAllKeywordsFromAddKeywordPopup();

		// verify that Configured metadata and keywords are retained in the same
		// assignment as per the assignment group
		baseClass.listCompareEquals(ListOfAssignedMetadata, ListOfAssignedMetadata02,
				"List of Assigned Metadata in Assigment Group match with the List of Assigned Metadata in Assigment",
				"Actual doesn't macth with Expected");
		baseClass.listCompareEquals(ListOfKeywords, ListOfKeywords02,
				"List of KeyWord in Assignment Group match List of KeyWord in Assignment",
				"Actual doesn't macth with Expected");
		baseClass.passedStep(
				"Verified that Configured metadata and keywords are retained in the same assignment as per the assignment group");

		// delete assignments and assignment Groups
		assignment.navigateToAssignmentsPage();
		assignment.deleteAssignmentFromSingleAssgnGrp(assignGroup, assignmentName);
		assignment.DeleteAssgnGroup(assignGroup);

		// logOut
		loginPage.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 */
	@Test(description = "RPMXCON-53700", enabled = true, groups = { "regression" })
	public void VerifyRedistributefunctionality_CompletedDocs()
			throws InterruptedException, ParseException, IOException {

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("To verify the functionality of the ReDistribute Document for Completed Document.");
		baseClass.stepInfo("Test case Id: RPMXCON-53700");
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();

		assignment.createAssignment(assignmentName, Input.codeFormName);
		baseClass.stepInfo("Created assignment with name" + assignmentName);

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assignmentName);
		baseClass.stepInfo("bulk assigned the assignment " + assignmentName);

		assignment.editAssignmentUsingPaginationConcept(assignmentName);
		assignment.addReviewerAndDistributeDocs(Input.rev1userName);
		// added wait time since success message pop ups get overlapped so added some
		// hard waits to avoid failure
		baseClass.waitTime(8);
		assignment.completeDocs(Input.rev1userName);
		baseClass.waitTime(4);
		assignment.VerifyWarnignMSGinManageRevTab_RedistributeDocs(Input.rev1userName, null);

		assignment.deleteAssgnmntUsingPagination(assignmentName);
		loginPage.logout();
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
		System.out.println("**Executed  Assignment_Regression22 .**");
	}
}
