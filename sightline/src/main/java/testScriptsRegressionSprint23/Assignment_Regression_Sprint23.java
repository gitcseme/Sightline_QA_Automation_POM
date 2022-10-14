package testScriptsRegressionSprint23;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.DocViewPage;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Assignment_Regression_Sprint23 {

	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	AssignmentsPage assignment;
	BaseClass baseClass;
	Input in;
	SoftAssert softAssert;
	KeywordPage keyPage;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		in = new Input();
		in.loadEnvConfig();
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

	/**
	 * @author Jayanthi.ganesan
	 */
	@Test(description = "RPMXCON-54484", enabled = true, groups = { "regression" })
	public void VerifyTotalDocsSelectedFluctuation() throws InterruptedException, ParseException, IOException {

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		baseClass.stepInfo(
				"Verifying the fluctuation of document count for " + "all the bulk actions in Manage Assignments");
		baseClass.stepInfo("Test case Id: RPMXCON-54484");

		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		String folderTagNAme = "folderTag" + Utility.dynamicNameAppender();

		String count = String.valueOf(sessionSearch.basicContentSearch(Input.searchString1));
		sessionSearch.bulkAssign();
		assignment.FinalizeAssignmentAfterBulkAssign();
		assignment.createAssignment_fromAssignUnassignPopup(assignmentName, Input.codeFormName);
		driver.scrollingToElementofAPage(assignment.getAssignmentSaveButton());
		baseClass.waitForElement(assignment.getAssignmentSaveButton());
		assignment.getAssignmentSaveButton().waitAndClick(3);

		assignment.navigateToAssignmentsPage();
		assignment.viewSelectedAssgnUsingPagination(assignmentName);
		driver.scrollingToElementofAPage(assignment.getSelectAssignment(assignmentName));
		assignment.getSelectAssignment(assignmentName).waitAndClick(5);
		baseClass.stepInfo("Verification for Tag All Option");
		assignment.Bulk_TagAll_FolderAll(true);
		sessionSearch.bulkTag_FluctuationVerify(folderTagNAme, count);

		assignment.navigateToAssignmentsPage();
		assignment.viewSelectedAssgnUsingPagination(assignmentName);
		driver.scrollingToElementofAPage(assignment.getSelectAssignment(assignmentName));
		assignment.getSelectAssignment(assignmentName).waitAndClick(5);
		baseClass.stepInfo("Verification for Folder All Option");
		assignment.Bulk_TagAll_FolderAll(false);
		sessionSearch.bulkFolder_FluctuationVerify(folderTagNAme, count);

		assignment.deleteAssgnmntUsingPagination(assignmentName);
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
		assignment.add2ReviewerAndDistribute();

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
		assignment.add2ReviewerAndDistribute();

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
		assignment.add2ReviewerAndDistribute();

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
		assignment.add2ReviewerAndDistribute();

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
			keyPage.addKeyword(associatedKeyword[i], "Red");
		}
		for (int j = 0; j < deassociatedKeyword.length; j++) {
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
		softAssert.assertEquals(sessionSearch.getContinueButton().Enabled(), false);
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
		System.out.println("**Executed  Assignment_Regression_sprint23 .**");
	}
}
