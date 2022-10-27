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

public class Assignment_Regression22_23 {

	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	AssignmentsPage assignment;
	BaseClass baseClass;
	Input in;
	SoftAssert softAssert;

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
		assignment.add2ReviewerAndDistribute();

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
		assignment.add2ReviewerAndDistribute();

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
		assignment.add2ReviewerAndDistribute();
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
		assignment.addReviewerAndDistributeDocs();
		//added wait time since success message pop ups get overlapped so added some hard waits to avoid failure
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