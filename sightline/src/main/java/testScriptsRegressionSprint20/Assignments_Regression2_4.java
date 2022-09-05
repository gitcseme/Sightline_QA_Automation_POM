package testScriptsRegressionSprint20;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;
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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.Dashboard;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Assignments_Regression2_4 {

	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	AssignmentsPage assignPage;
	BaseClass baseClass;
	Input in;
	SoftAssert softAssertion;
	SecurityGroupsPage securityGroupsPage;
	TagsAndFoldersPage tagsAndFolderPage;
	DocViewRedactions docViewRedact;

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
		assignPage = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		softAssertion = new SoftAssert();
		sessionSearch = new SessionSearch(driver);

	}

	@DataProvider(name = "Users")
	public Object[][] CombinedSearchwithUsers() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password }, { Input.pa1userName, Input.pa1password },
				{ Input.rev1userName, Input.rev1password } };
		return users;
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
		assignPage.createAssignment(assgnName, Input.codeFormName);

		// edit assignment sort by metaData
		baseClass.stepInfo("Editing Existing Assignment sort by metaData");
		assignPage.editAssignmentUsingPaginationConcept(assgnName);
		assignPage.Assgnwithdocumentsequence(Input.metaDataName, Input.sortType);

		// bulk assign documents to assignment
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assgnName);

		// viewing the assignment in DocView page
		assignPage.selectAssignmentToViewinDocviewThreadMap(assgnName);

		// configuring the miniDocList with assignment metaData
		baseClass.waitForElement(miniDocList.getGearIcon());
		miniDocList.clickingGearIcon();
		miniDocList.removingAllExistingFieldsAndAddingNewField(Input.metaDataName);

		// verifying that Documents are sorted based on the Selected metadata.
		metaDataList = baseClass.availableListofElements(miniDocList.getListofDocIDinCW());
		baseClass.verifyOriginalSortOrder(metaDataList, metaDataList, "Ascending", true);
		baseClass.stepInfo("verified that Documents are sorted based on the Selected metadata.");

		// delete assignment
		assignPage.deleteAssgnmntUsingPagination(assgnName);

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
		assignPage.createAssignment_withoutSave(assgnName, Input.codeFormName);
		assignPage.Assgnwithdocumentsequence(Input.sortDataBy, Input.sortType);

		// edit assignment sort by metaData
		baseClass.stepInfo("Editing Existing Assignment sort by metaData");
		assignPage.editAssignmentUsingPaginationConcept(assgnName);
		assignPage.Assgnwithdocumentsequence(Input.metaDataName, Input.sortType);

		// bulk assign documents to assignment
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assgnName);

		// viewing the assignment in DocView page
		assignPage.selectAssignmentToViewinDocviewThreadMap(assgnName);

		// configuring the miniDocList with assignment metaData
		baseClass.waitForElement(miniDocList.getGearIcon());
		miniDocList.clickingGearIcon();
		miniDocList.removingAllExistingFieldsAndAddingNewField(Input.metaDataName);

		// verifying that Documents are sorted based on the updated metadata.
		metaDataList = baseClass.availableListofElements(miniDocList.getListofDocIDinCW());
		baseClass.verifyOriginalSortOrder(metaDataList, metaDataList, "Ascending", true);
		baseClass.passedStep("verified that Documents are sorted based on the updated metadata.");

		// delete assignment
		assignPage.deleteAssgnmntUsingPagination(assgnName);

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
		assignPage.createAssignment(assignmentName, Input.codeFormName);
		baseClass.stepInfo("Created assignment with name" + assignmentName);
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assignmentName);
		baseClass.stepInfo("Created a assignment " + assignmentName);

		baseClass.stepInfo("**Adding 2 reviewers and distributing docs to one reviewer alone**");
		assignPage.editAssignmentUsingPaginationConcept(assignmentName);
		String Distributeduser = assignPage.addMultipleReviewersAndDistributeToOnereviewer();
		baseClass.stepInfo("**selecting docs assigned reviewers to redistribute docs to other assignment "
				+ "associated reviewer**");
		String redistributeUser = assignPage.VerifyUserNotInListAfterRedistributedDocs();

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
		assignPage.navigateToAssignmentsPage();
		assignPage.validateTotalDocumentCountInManageAssignmentPageForMultipleAssignments(
				pairOfAssignmentsAndExpectedTotalDocCountInAssign);

		// Verifying that "Distribute Documents" tab is displayed and Total Documents
		// are displayed as "0"
		assignPage.verifyTotalCountOfDocsInMultipleAssignmentsInDistributeDocumentsTab(
				pairOfAssignmentsAndexptedDocCountInDistrTab);

		// delete Assignment
		assignPage.deleteMultipleAssgnmntUsingPagination(listOfAssignments);

		// logOut
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 30/08/2022 TestCase Id:RPMXCON-53759
	 * Description :To verify that copied Assignment must be displayed on RMU Dashboard and RU Dashboard
	 * @throws InterruptedException 
	 */
	@Test(description ="RPMXCON-53759",enabled = true, groups = { "regression" })
	public void verifyCopiedAssignmentAvailability() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-53759");
		baseClass.stepInfo("verify that copied Assignment must be displayed on RMU Dashboard and RU Dashboard");
		String assignmentName ="assignment1" + Utility.dynamicNameAppender();
		String assignGroup ="assigGroup"+ Utility.dynamicNameAppender();
		
		//login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Create new assignment");
		assignPage.navigateToAssignmentsPage();
		assignPage.createCascadeNonCascadeAssgnGroup(assignGroup, "No");
		assignPage.selectAssignmentGroup(assignGroup);
		assignPage.createAssignmentFromAssgnGroup(assignmentName, Input.codingFormName);
		baseClass.stepInfo("Copy assignment");
		assignPage.selectAssignmentGroup(assignGroup);
		assignPage.getSelectAssignment(assignmentName).waitAndClick(3);
		driver.scrollPageToTop();
		assignPage.getAssignmentActionDropdown().waitAndClick(5);
		baseClass.waitForElement(assignPage.getAssignmentActionDropdown());
		assignPage.getAssignmentAction_CopyAssignment().waitAndClick(5);
		baseClass.getYesBtn().waitAndClick(5);
		baseClass.VerifySuccessMessage("Record copied successfully");
		String copiedAssignName=assignPage.getSelectCopyAssignment().getText().trim();
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkAssignExisting(copiedAssignName);
		assignPage.selectAssignmentGroup(assignGroup);
		assignPage.getSelectAssignment(copiedAssignName).waitAndClick(3);
		driver.scrollPageToTop();
		baseClass.waitForElement(assignPage.getAssignmentActionDropdown());
		assignPage.getAssignmentActionDropdown().waitAndClick(5);
		assignPage.getAssignmentAction_EditAssignment().waitAndClick(5);
		assignPage.add2ReviewerAndDistribute();
		baseClass.stepInfo("verify copied assignment displayed in rmu dashboard");
		Dashboard dashBoard =new Dashboard(driver);
		dashBoard.navigateToDashboard();
		driver.waitForPageToBeReady();
		if(dashBoard.getSelectAssignmentFromDashborad(copiedAssignName).isElementAvailable(10)) {
			baseClass.passedStep("Copied assignment available in RMU dashboard");
		}
		else {
			baseClass.failedStep("Copied assignment not available");
		}
		loginPage.logout();
		baseClass.stepInfo("Login as rev and verify copied assignment availability");
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("verify copied assignment displayed in rev dashboard");
		if(assignPage.getSelectAssignmentAsReviewer(copiedAssignName).isElementAvailable(10)) {
			baseClass.passedStep("Copied assignment available in REV dashboard");
		}
		else {
			baseClass.failedStep("Copied assignment not available");
		}
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 30/08/2022 TestCase Id:RPMXCON-53652
	 * Description :To verify that in "Select Reviewers" section in Distribute Documents tab only those 
	 * reviewers are displayed which are added to an assignment in Manage Reviewer tab.
	 * @throws InterruptedException 
	 */
	@Test(description ="RPMXCON-53652",enabled = true, groups = { "regression" })
	public void verifyAddedReviewersInDistributeTab() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-53652");
		baseClass.stepInfo("verify that added reviewers displayed in distribute tab");
		String assignmentName ="assignment1" + Utility.dynamicNameAppender();
		String[] users = {Input.rmu1userName,Input.rev1userName};

		//login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignPage.createAssignment(assignmentName, Input.codeFormName);
		assignPage.editAssignmentUsingPaginationConcept(assignmentName);
		baseClass.stepInfo("add reviewers");
		assignPage.assignReviewers(users);
		baseClass.stepInfo("verify added users displayed in distribute documents tab");
		baseClass.waitForElement(assignPage.getDistributeTab());
		assignPage.getDistributeTab().waitAndClick(5);
		int availableUserCount =assignPage.getDistributeReviewerCount().size();
		if(availableUserCount == users.length) {
			if(assignPage.getSelectUserInDistributeTabsReviewerManager().isElementAvailable(5) && 
					assignPage.getSelect2ndUserInDistributeTab().isElementAvailable(5)) {
				baseClass.passedStep("Only the added reviewers are displayed in distribute tab");
			}
			else {
				baseClass.failedStep("added reviewers not displayed");
			}	
		}
		else {
			baseClass.failedStep("added reviewers not displayed correctly in distribute tab");
		}
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
		System.out.println("**Executed  Assignments_Regression2_2 .**");
	}

}
