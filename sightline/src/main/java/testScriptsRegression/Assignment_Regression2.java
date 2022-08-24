package testScriptsRegression;

import java.awt.AWTException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;

import org.testng.Assert;
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
import pageFactory.DocListPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import pageFactory.KeywordPage;
import testScriptsSmoke.Input;

public class Assignment_Regression2 {
	Driver driver;
	LoginPage lp;
	SessionSearch search;
	SavedSearch savedSearch;
	ArrayList<String> reviwersListInSearchPg;
	BaseClass bc;
	AssignmentsPage agnmt;
	KeywordPage keyword;
	String searchText;
	SoftAssert assertion;
	StringWriter sw;
	PrintWriter pw;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
//		Input in = new Input();
//	    in.loadEnvConfig();
		searchText = Input.TallySearch;
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
	}

	/**
	 * @author Jayanthi.ganesan
	 * 
	 * @description: To verify that RMU is able to sort the documents by selecting
	 *               option as Near Duplicate Docs (RPMXCON-53824)
	 */
	@Test(description ="RPMXCON-53824",enabled = true, groups = { "regression" })
	public void verifyDocSortedByNearDupesID() throws InterruptedException, ParseException, IOException {
		bc.stepInfo("To verify that RMU is able to sort the documents by selecting option as Near Duplicate Docs");
		bc.stepInfo("Test case Id:RPMXCON-53824");
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		agnmt.createAssignment_withoutSave(assignmentName, Input.codeFormName);
		driver.scrollingToBottomofAPage();
		agnmt.dragAndDropLiveSequence(" Email Threads");
		agnmt.dragAndDropLiveSequence(" DocID");
		agnmt.dragAndDropLiveSequence(" Family Members");
		bc.passedStep("Except NearDuplicate Docs, all other options are moved from live sequence");
		driver.scrollPageToTop();
		bc.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		bc.stepInfo("Created assignment with name" + assignmentName);
		bc.selectproject();
		search.basicContentSearch(Input.searchString2);
		search.bulkAssignNearDupeDocuments();
		search.bulkAssign_withoutshoppingCartAdd(assignmentName);
		bc.selectproject();
		search.basicContentSearch("this");
		search.bulkAssignNearDupeDocuments();
		search.bulkAssign_withoutshoppingCartAdd(assignmentName);
		bc.selectproject();
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		agnmt.addReviewerAndDistributeDocs();
		lp.logout();
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		bc.stepInfo("Logged in as reviewer user");
		agnmt.verifyTheMetaDataSortingAsPerRMUUserByAscending(assignmentName, "NearDupePrincipalDocID");
		lp.logout();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.stepInfo("Logged in as RMU user");
		agnmt.deleteAssgnmntUsingPagination(assignmentName);
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * 
	 * @description: To verify that RMU is able to sort the documents by selecting
	 *               option as Email Threads (RPMXCON-53823)
	 */

	@Test(description ="RPMXCON-53823",enabled = true, groups = { "regression" })
	public void verifyDocSortedByEmailThreadId() throws InterruptedException, ParseException, IOException {
		bc.stepInfo("To verify that RMU is able to sort the documents by selecting option as Email Threads.");
		bc.stepInfo("Test case Id:RPMXCON-53823");
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		agnmt.createAssignment_withoutSave(assignmentName, Input.codeFormName);
		driver.scrollingToBottomofAPage();
		agnmt.dragAndDropLiveSequence(" Near Duplicate Docs");
		agnmt.dragAndDropLiveSequence(" DocID");
		agnmt.dragAndDropLiveSequence(" Family Members");

		bc.passedStep("Except EmailThreads, all other options are moved from live sequence");
		driver.scrollPageToTop();
		bc.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		bc.stepInfo("Created assignment with name" + assignmentName);
		bc.selectproject();
		search.basicContentSearch(Input.searchString1);
		search.bulkAssignThreadedDocs();
		search.bulkAssign_withoutshoppingCartAdd(assignmentName);
		bc.selectproject();
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		agnmt.addReviewerAndDistributeDocs();
		lp.logout();
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		bc.stepInfo("Logged in as reviewer user");
		agnmt.verifyTheMetaDataSortingAsPerRMUUserByAscending(assignmentName, "EmailThreadID");
		lp.logout();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.stepInfo("Logged in as RMU user");
		agnmt.deleteAssgnmntUsingPagination(assignmentName);
		lp.logout();
	}
	

	/**
	 * @author Jayanthi.ganesan
	 * @description: Verify that help text pop does not appear when the user just
	 *               hovers the "?" icon on "Manage Assignments" screen.
	 */
	@Test(description ="RPMXCON-54945",enabled = true, groups = { "regression" })
	public void validateHelpPopUpWhenHoveringInAssignmentsPg()
			throws InterruptedException, ParseException, IOException {
		bc.stepInfo("Test case Id: RPMXCON-54945");
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.verifyHelpTextPopUpWhenHovering();
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @description: Verify that help text pop up appear when we click on help icon
	 *               and disappear when the user clicks on elsewhere in the
	 *               application on 'Manage Assignments' screen.
	 */

	@Test(description ="RPMXCON-54943",enabled = true, groups = { "regression" })
	public void verifyHelpTextPopUpWhenClickingInAssignmentsPg()
			throws InterruptedException, ParseException, IOException {
		bc.stepInfo("Test case Id: RPMXCON-54944, RPMXCON-54943");
		bc.stepInfo("Verify that help text pop up  appear when we click on help icon and disappear when the user "
				+ "clicks on elsewhere in the application on 'Manage Assignments' screen.");

		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.verifyHelpTextPopUpWhenClicking();
		lp.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws AWTException
	 * @description: Validate distributed list and results for searching by Assigned
	 *               Assignment workproduct
	 */
	@Test(description ="RPMXCON-54580",enabled = true, groups = { "regression" })
	public void verifyDistributedListResultsWithAssignedDocs() throws InterruptedException, AWTException {
		bc.stepInfo("Validate distributed list and results for searching by Assigned Assignment workproduct");
		bc.stepInfo("Test case Id: RPMXCON-54580");
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		assertion = new SoftAssert();
		agnmt.createAssignment(assignmentName, Input.codeFormName);
		bc.selectproject();
		int pureHits = search.basicContentSearch(searchText);
		search.bulkAssignExisting(assignmentName);
		bc.selectproject();
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		agnmt.add3ReviewerAndDistribute();
		bc.selectproject();
		search.switchToWorkproduct();
		reviwersListInSearchPg = search.selectAssignmentAndMultipleReviewersWPSWthFilters(assignmentName,
				Input.rev1userName, Input.rmu1userName, Input.pa1userName, "Assigned");
		search.serarchWP();
		int PureHitCountAfterSelectingCriteria = Integer.parseInt(search.verifyPureHitsCount());
		try {
			assertion.assertEquals(pureHits, PureHitCountAfterSelectingCriteria);
			bc.passedStep("The search results are displayed as per search criteria");
		} catch (Exception e) {
			bc.failedStep("The search results are not displayed as per search criteria");
		}
		agnmt.deleteSelectedAgnmt(assignmentName);
		assertion.assertAll();
		lp.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws AWTException
	 * @description: Validate distributed list and results for searching by
	 *               Assignment workproduct
	 */
	@Test(description ="RPMXCON-54581",enabled = true, groups = { "regression" })
	public void verifyDistributedListResultsOfWPAssgn() throws InterruptedException, AWTException {
		bc.stepInfo("Validate distributed list and results for searching by Assignment workproduct");
		bc.stepInfo("Test case Id: RPMXCON-54581");
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		assertion = new SoftAssert();
		agnmt.createAssignment(assignmentName, Input.codeFormName);
		int pureHits = search.basicContentSearch(searchText);
		search.bulkAssignExisting(assignmentName);
		bc.selectproject();
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		agnmt.add3ReviewerAndDistribute();
		bc.selectproject();
		search.switchToWorkproduct();
		reviwersListInSearchPg = search.selectAssignmentAndMultipleReviewersWPSWthFilters(assignmentName,
				Input.rev1userName, Input.rmu1userName, Input.pa1userName, "Choose Status:");
		search.serarchWP();
		int PureHitCountAfterSelectingCriteria = Integer.parseInt(search.verifyPureHitsCount());
		try {
			assertion.assertEquals(pureHits, PureHitCountAfterSelectingCriteria);
			bc.passedStep("The search results are displayed as per search criteria");
		} catch (Exception e) {
			bc.failedStep("The search results are not displayed as per search criteria");
		}
		agnmt.deleteSelectedAgnmt(assignmentName);
		assertion.assertAll();
		lp.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws AWTException
	 * @description: Validate distributed list and results for searching by Assigned
	 *               Assignment work product (SysAdmin/DAU/PAU impersonated as RMU)
	 */
	@Test(description ="RPMXCON-54582",enabled = true, groups = { "regression" })
	public void verifyDistributedListResultsWithAssignedDocsUsingImpersonation()
			throws InterruptedException, AWTException {
		bc.stepInfo("Validate distributed list and results for searching by"
				+ " Assigned Assignment workproduct(SysAdmin/DAU/PAU impersonated as RMU)");
		bc.stepInfo("Test case Id: RPMXCON-54582");
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		assertion = new SoftAssert();
		bc.stepInfo("Logged in as RMU user");
		agnmt.createAssignment(assignmentName, Input.codeFormName);
		int pureHits = search.basicContentSearch(searchText);
		search.bulkAssignExisting(assignmentName);
		bc.selectproject();
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		agnmt.add3ReviewerAndDistribute();
		lp.logout();
		lp.loginToSightLine(Input.sa1userName, Input.sa1password);
		bc.stepInfo("Logged in as SA user");
		bc.impersonateSAtoRMU();
		bc.stepInfo("Impersonated to RMU user successfully");
		search.switchToWorkproduct();
		reviwersListInSearchPg = search.selectAssignmentAndMultipleReviewersWPSWthFilters(assignmentName,
				Input.rev1userName, Input.rmu1userName, Input.pa1userName, "Assigned");
		search.serarchWP();
		bc.waitForElement(search.getPureHitsCount());
		int PureHitCountAfterSelectingCriteria = Integer.parseInt(search.verifyPureHitsCount());
		try {
			Assert.assertEquals(pureHits, PureHitCountAfterSelectingCriteria);
			bc.passedStep("The search results are displayed as per search criteria");
		} catch (Exception e) {
			bc.failedStep("The search results are displayed as per search criteria");
		}
		lp.logout();
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("Logged in as PA user");
		bc.impersonatePAtoRMU();
		bc.stepInfo("Impersonated to rmu user successfully");
		search.switchToWorkproduct();
		reviwersListInSearchPg = search.selectAssignmentAndMultipleReviewersWPSWthFilters(assignmentName,
				Input.rev1userName, Input.rmu1userName, Input.pa1userName, "Assigned");
		search.serarchWP();
		int PureHitCountAfterSelectingCriteria1 = Integer.parseInt(search.verifyPureHitsCount());
		try {
			assertion.assertEquals(pureHits, PureHitCountAfterSelectingCriteria1);
			bc.passedStep("The search results are displayed as per search criteria");
		} catch (Exception e) {
			bc.failedStep("The search results are not displayed as per search criteria");
		}
		agnmt.deleteAssgnmntUsingPagination(assignmentName);
		assertion.assertAll();
		lp.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws AWTException
	 * @description: Validate distributed list and results for searching by
	 *               Assignment work product (SysAdmin/DAU/PAU impersonated as RMU)
	 */
	@Test(description ="RPMXCON-54583",enabled = true, groups = { "regression" })
	public void verifyDistributedListResultsOfWPAssgnImpersonation() throws InterruptedException, AWTException {
		bc.stepInfo("Validate distributed list and results for searching by Assignment workproduct"
				+ " (SysAdmin/DAU/PAU impersonated as RMU)");
		bc.stepInfo("Test case Id: RPMXCON-54583");
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		assertion = new SoftAssert();
		bc.stepInfo("Logged in as RMU user");
		agnmt.createAssignment(assignmentName, Input.codeFormName);
		int pureHits = search.basicContentSearch(searchText);
		search.bulkAssignExisting(assignmentName);
		bc.selectproject();
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		agnmt.add3ReviewerAndDistribute();
		lp.logout();
		lp.loginToSightLine(Input.sa1userName, Input.sa1password);
		bc.stepInfo("Logged in as SA user");
		bc.impersonateSAtoRMU();
		bc.stepInfo("Impersonated to RMU user successfully");
		search.switchToWorkproduct();
		reviwersListInSearchPg = search.selectAssignmentAndMultipleReviewersWPSWthFilters(assignmentName,
				Input.rev1userName, Input.rmu1userName, Input.pa1userName, "Choose Status:");
		search.serarchWP();
		int PureHitCountAfterSelectingCriteria = Integer.parseInt(search.verifyPureHitsCount());
		try {
			assertion.assertEquals(pureHits, PureHitCountAfterSelectingCriteria);
			bc.passedStep("The search results are displayed as per search criteria");
		} catch (Exception e) {
			bc.failedStep("The search results are displayed as per search criteria");
		}
		lp.logout();
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("Logged in as PA user");
		bc.impersonatePAtoRMU();
		bc.stepInfo("Impersonated to rmu user successfully");
		search.switchToWorkproduct();
		reviwersListInSearchPg = search.selectAssignmentAndMultipleReviewersWPSWthFilters(assignmentName,
				Input.rev1userName, Input.rmu1userName, Input.pa1userName, "Choose Status:");
		search.serarchWP();
		int PureHitCountAfterSelectingCriteria1 = Integer.parseInt(search.verifyPureHitsCount());
		try {
			assertion.assertEquals(pureHits, PureHitCountAfterSelectingCriteria1);
			bc.passedStep("The search results are displayed as per search criteria");
		} catch (Exception e) {
			bc.failedStep("The search results are not displayed as per search criteria");
		}
		agnmt.deleteAssgnmntUsingPagination(assignmentName);
		assertion.assertAll();
		lp.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @description: To verify assignment progress bar should be displayed with
	 *               color bands when assignments are with Uncomplete and Complete
	 *               status RPMXCON-53723
	 */
	@Test(description ="RPMXCON-53723",enabled = true, groups = { "regression" })
	public void validateColorBandsForStatusBtns() throws InterruptedException {
		bc.stepInfo("To verify assignment progress bar should be displayed with color bands"
				+ " when assignments are with Uncomplete and Complete status");
		bc.stepInfo("Test case Id: RPMXCON-53723");
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		bc.stepInfo("Logged in as RMU user");
		agnmt.createAssignment(assignmentName, Input.codeFormName);
		search.basicContentSearch(searchText);
		search.bulkAssignExisting(assignmentName);
		bc.stepInfo("Created a assignment " + assignmentName);
		bc.selectproject();
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		agnmt.addReviewerAndDistributeDocs();
		lp.logout();
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		agnmt.verifyColorBands(assignmentName, "Un Completed");
		agnmt.completeDistributesDocsByReviewer(assignmentName);
		bc.selectproject();
		driver.waitForPageToBeReady();
		agnmt.verifyColorBands(assignmentName, "Completed");
		lp.logout();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		agnmt.deleteAssgnmntUsingPagination(assignmentName);
		lp.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @description: To verify that Unassigned RU is not displayed in the Manage
	 *               Reviewer and Distribute Documents RPMXCON-53724
	 */
	@Test(description ="RPMXCON-53724",enabled = true, groups = { "regression" })
	public void verifyUnassignedUserNotDisplayedInAgnmt() throws InterruptedException {
		bc.stepInfo("To verify that Unassigned RU is not displayed in the Manage Reviewer and Distribute Documents");
		bc.stepInfo("Test case Id: RPMXCON-53724");
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		bc.stepInfo("Logged in as RMU user");
		agnmt.createAssignment(assignmentName, Input.codeFormName);
		search.basicContentSearch(searchText);
		search.bulkAssignExisting(assignmentName);
		bc.stepInfo("Created a assignment " + assignmentName);
		bc.selectproject();
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		agnmt.ValidateUnassignUserInAgnmt();
		agnmt.deleteSelectedAgnmt(assignmentName);
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @description: To verify that on selecting specific category, only that
	 *               category documents are assign to Assignment- RPMXCON-53943.
	 */
	@Test(description ="RPMXCON-53943",enabled = true, groups = { "regression" })
	public void VerifyDocAssignedfromcategory() throws InterruptedException, ParseException, IOException {
		bc.stepInfo("Test case Id: RPMXCON-53943");
		bc.stepInfo("To verify that on selecting specific category, only that category documents are assign"
				+ " to Assignment.");
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		assertion = new SoftAssert();
		agnmt.createAssignment(assignmentName, Input.codeFormName);
		search.advancedContentSearch(Input.searchString2);
		int PureHits = Integer.parseInt(search.verifyPureHitsCount());
		search.bulkAssign();
		try {
			int Expected = agnmt.assgn_LikeDocs(assignmentName, agnmt.getAssgn_LikeDoc_Neartoggle(),
					agnmt.getAssgn_LikeDoc_NeartoggleDocCount(), PureHits);
			int Actual = Integer.parseInt(agnmt.verifydocsCountInAssgnPage(assignmentName));
			assertion.assertEquals(Actual, Expected);
			bc.passedStep("Sucessfully verified that on selecting specific category, only that category documents are"
					+ " assign to Assignment.");
			agnmt.deleteAssgnmntUsingPagination(assignmentName);
			assertion.assertAll();
		} catch (Exception e) {
			bc.stepInfo(e.getMessage());
			bc.failedStep("Exception Occured,Failed To verify that on selecting specific category, only that category "
					+ "documents are assign to Assignment.");
		}
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws AWTException
	 * 
	 * @description: To verify that RMU is able to view the configure button and
	 *               Instruction pop out elements on clicking the Configure
	 *               button(RPMXCON-53795).
	 */
	@Test(description ="RPMXCON-53795",enabled = true, groups = { "regression" })
	public void verifyInstructionPopUp() throws InterruptedException, AWTException {
		try {
			bc.stepInfo("To verify that RMU is able to view the Instruction pop out on clicking the Configure button");
			bc.stepInfo("Test case Id:RPMXCON-53795");
			String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
			agnmt.createAssignment_withoutSave(assignmentName, Input.codeFormName);
			agnmt.verifyFormattingToolBar_InstructionPopUp("yes");
			bc.waitForElement(agnmt.getAssignmentSaveButton());
			agnmt.getAssignmentSaveButton().waitAndClick(3);
			bc.stepInfo("Created assignment with name" + assignmentName);
			agnmt.editAssignmentUsingPaginationConcept(assignmentName);
			agnmt.verifyFormattingToolBar_InstructionPopUp("yes");
			bc.passedStep(
					"Sucessfully Verified that RMU is able to view the Elements Instruction pop out on clicking the Configure button");

		} catch (Exception e) {
			bc.stepInfo(e.getMessage());
			bc.failedStep(
					"Exception occured,Failed to verify Rmu is able to view instruction pop out Elements on New/Edit Assignments Page");

		}
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * 
	 * @description: To verify that RMU is able to sort the documents by selecting
	 *               option as Family Members (RPMXCON-53825)
	 */

	@Test(description ="RPMXCON-53825",enabled = true, groups = { "regression" })
	public void verifyDocSortedByFamilyId() throws InterruptedException, ParseException, IOException {
		bc.stepInfo("To verify that RMU is able to sort the documents by selecting option as FamilyMemebers.");
		bc.stepInfo("Test case Id:RPMXCON-53825");
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		agnmt.createAssignment_withoutSave(assignmentName, Input.codeFormName);
		driver.scrollingToBottomofAPage();
		agnmt.dragAndDropLiveSequence(" Email Threads");
		agnmt.dragAndDropLiveSequence(" Near Duplicate Docs");
		agnmt.dragAndDropLiveSequence(" DocID");
		bc.passedStep("Except Family Members, all other options are moved from live sequence");
		driver.scrollPageToTop();
		bc.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		bc.stepInfo("Created assignment with name" + assignmentName);
		bc.selectproject();
		search.basicContentSearch(searchText);
		search.bulkAssignExisting(assignmentName);
		bc.selectproject();
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		agnmt.addReviewerAndDistributeDocs();
		lp.logout();
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		bc.stepInfo("Logged in as reviewer user");
		agnmt.verifyFamilyIdSorting(assignmentName, "FamilyID", "DocID");
		lp.logout();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.stepInfo("Logged in as RMU user");
		agnmt.deleteAssgnmntUsingPagination(assignmentName);
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws AWTException
	 * 
	 * @description: To verify that RMU is able to view the configure button on New
	 *               Assignment Page(RPMXCON-53793).
	 */
	@Test(description ="RPMXCON-53793",enabled = true, groups = { "regression" })
	public void verifyConfigureButtonNewAssgn() throws InterruptedException, AWTException {
		try {
			bc.stepInfo("To verify that RMU is able to view the configure button on New Assignment Page");
			bc.stepInfo("Test case Id:RPMXCON-RPMXCON-53793");
			String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
			agnmt.createAssignment_withoutSave(assignmentName, Input.codeFormName);
			driver.scrollingToElementofAPage(agnmt.configureButton());
			bc.waitForElement(agnmt.configureButton());
			bc.ValidateElement_Presence(agnmt.configureButton(), "Configure Button");
			bc.passedStep("Sucessfully verified that RMU is able to view the configure button on New Assignment Page");
		} catch (Exception e) {
			bc.stepInfo(e.getMessage());
			e.printStackTrace();
			bc.failedStep(
					"Exception occured,Failed to verify Rmu is able to view  Configure Button  on New Assignments Page");
		}
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws AWTException
	 * 
	 * @description: To verify that RMU is able to view the configure button in Edit
	 *               Asssignments Page(RPMXCON-53794).
	 */
	@Test(description ="RPMXCON-53794",enabled = true, groups = { "regression" })
	public void verifyConfigureButton_EditAssgn() throws InterruptedException, AWTException {
		try {
			bc.stepInfo("To verify that RMU is able to view the configure button on Edit Assignment Page");
			bc.stepInfo("Test case Id:RPMXCON-53794");
			String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
			agnmt.createAssignment_withoutSave(assignmentName,Input.codeFormName);
			driver.scrollingToElementofAPage(agnmt.configureButton());
			bc.waitForElement(agnmt.configureButton());
			bc.ValidateElement_Presence(agnmt.configureButton(), "Configure Button");
			bc.waitForElement(agnmt.getAssignmentSaveButton());
			agnmt.getAssignmentSaveButton().waitAndClick(3);
			bc.stepInfo("Created assignment with name" + assignmentName);
			agnmt.editAssignmentUsingPaginationConcept(assignmentName);
			bc.passedStep("Navigated to Edit Assignment Page");
			driver.scrollingToElementofAPage(agnmt.configureButton());
			bc.waitForElement(agnmt.configureButton());
			bc.ValidateElement_Presence(agnmt.configureButton(), "Configure Button");
			agnmt.getAssignmentSaveButton().waitAndClick(5);
			bc.passedStep("Sucessfully verified that RMU is able to view the configure button on Edit Assignment Page");
			agnmt.deleteSelectedAgnmt(assignmentName);
		} catch (Exception e) {
			e.printStackTrace();
			bc.stepInfo(e.getMessage());
			bc.failedStep(
					"Exception occured,Failed to verify Rmu is able to view  Configure Button in Edit Assignments Page");
		}
		lp.logout();
	}
	/**
	 * @author Jayanthi.ganesan
	 * @description:To verify that on clicking Keyword button pop up is displayed on
	 *                 New/Edit Assignment Page
	 */
	@Test(description ="RPMXCON-53857",enabled = true, groups = { "regression" })
	public void VerifyKeywordPopUp() throws InterruptedException {
		try {
			bc.stepInfo("To verify that on clicking Keyword button pop up is displayed on New/Edit Assignment Page");
			bc.stepInfo("Test case Id:RPMXCON-53857");
			String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
			agnmt.createAssignment_withoutSave(assignmentName, Input.codeFormName);
			agnmt.verifyKeywordPopUp();
			bc.ValidateElement_Presence(agnmt.getAssgn_Keywordspopup(), "Key word PopUp");
			agnmt.getKeywordPopUpCancelBtn().waitAndClick(5);
			bc.waitForElement(agnmt.getAssignmentSaveButton());
			driver.scrollPageToTop();
			agnmt.getAssignmentSaveButton().waitAndClick(3);
			bc.stepInfo("Created assignment with name" + assignmentName);
			agnmt.editAssignmentUsingPaginationConcept(assignmentName);
			bc.passedStep("Navigated to Edit Assignment Page");
			agnmt.verifyKeywordPopUp();
			bc.ValidateElement_Presence(agnmt.getAssgn_Keywordspopup(), "Key word PopUp");
			agnmt.getKeywordPopUpCancelBtn().waitAndClick(5);
			agnmt.deleteSelectedAgnmt(assignmentName);
			bc.passedStep(
					"Sucessfully verified that on clicking Keyword button pop up is displayed on New/Edit Assignment Page");
		} catch (Exception e) {
			e.printStackTrace();
			bc.stepInfo(e.getMessage());
			bc.failedStep(
					"Exception occured,Failed to verify that on clicking Keyword button pop up is displayed on New/Edit Assignment Page\r\n"
							+ "");
		}
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * 
	 * @description: To verify that in "DOCUMENT PRESENTATION SEQUENCE" different
	 *               sequence options are displayed as per selection of tab by
	 *               RMU.(RPMXCON-53744)
	 */

	@Test(description ="RPMXCON-53744",enabled = true, groups = { "regression" })
	public void verifyLiveSequenceOptions() throws InterruptedException, ParseException, IOException {
		try {
			bc.stepInfo(
					"To verify that in DOCUMENT PRESENTATION SEQUENCEdifferent sequence options are displayed as per selection of tab by RMU.");
			bc.stepInfo("Test case Id:RPMXCON-53744");
			String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
			agnmt.createAssignment_withoutSave(assignmentName, Input.codeFormName);
			driver.scrollingToElementofAPage(agnmt.CatogeryTabClicked());
			bc.ValidateElement_Presence(agnmt.CatogeryTabClicked(), "Category Tab is selected as Default");
			bc.ValidateElement_Presence(agnmt.dragElement(" Email Threads"), " Email Threads");
			bc.ValidateElement_Presence(agnmt.dragElement(" Near Duplicate Docs"), "Near Duplicate Docs");
			bc.ValidateElement_Presence(agnmt.dragElement(" DocID"), "DocID");
			bc.ValidateElement_Presence(agnmt.dragElement(" Family Members"), " Family Members");
			bc.ValidateElement_Presence(agnmt.getAssgn_DocSequence_SortbyMetadata(), "Sort By MetaData Tab");
			agnmt.getAssgn_DocSequence_SortbyMetadata().Click();
			bc.ValidateElement_Presence(agnmt.getAssgn_DocSequence_Selectmetadata(), "MetaDataList DropDown");
			agnmt.getAssgn_DocSequence_Selectmetadata().selectFromDropdown().selectByVisibleText(Input.metaDataName);
			agnmt.getSortByMetaDataType().waitAndClick(3);
			agnmt.getSortByMetaDataType().selectFromDropdown().selectByVisibleText("Ascending");
			bc.ValidateElement_Presence(agnmt.getAssgn_DocSequence_Selectmetadata(),
					"MetaDataList DropDown selected as Custodian Name");
			bc.ValidateElement_Presence(agnmt.getSortByMetaDataType(), "Drop down Sort By Order as Ascending");
			bc.passedStep(
					"sucessfully verified that in DOCUMENT PRESENTATION SEQUENCE different sequence options are displayed as per selection of tab by RMU.");
		} catch (Exception e) {
			e.printStackTrace();
			bc.stepInfo(e.getMessage());
			bc.failedStep("Exception Occcured,Failed to verify Document PresentationSequence options displayed");

		}
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan	 * 
	 * @description: To verify that RMU can create New Assignments from
	 *               "Assign/Unassign Documents" pop up from Doclist page.
	 */

	@Test(description ="RPMXCON-53817",enabled = true,groups = { "regression" })
	public void CreateAssgn_DocList() throws InterruptedException {
		try {
			bc.stepInfo("Test case Id:RPMXCON-53817");
			bc.stepInfo(
					"To verify that RMU can create New Assignments from \"Assign/Unassign Documents\" pop up from Doclist page.");

			String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
			DocListPage doclist = new DocListPage(driver);
			search.basicContentSearch(Input.searchString1);
			search.ViewInDocList();
			doclist.DoclisttobulkAssign(assignmentName, "10");
			agnmt.FinalizeAssignmentAfterBulkAssign();
			agnmt.createAssignmentByBulkAssignOperation(assignmentName, Input.codeFormName);
			bc.passedStep(
					"sucessfully verified that RMU can create New Assignments from \"Assign/Unassign Documents\" pop up from Doclist page.");
		} catch (Exception e) {
			e.printStackTrace();
			bc.stepInfo(e.getMessage());
			bc.failedStep("Exception occured,failed to create assignment from assign/unassign popup from DocList Page");

		}
		lp.logout();

	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @description: To verify that configuration of Copied Assignment can be
	 *               changed. RPMXCON-53756
	 */
	@Test(description ="RPMXCON-53756",enabled = true, groups = { "regression" })
	public void verifyCopyAssignmentChanges() throws InterruptedException {
		bc.stepInfo("To verify that configuration of Copied Assignment can be changed.");
		bc.stepInfo("Test case Id: RPMXCON-53756");
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		bc.stepInfo("Logged in as RMU user");
		agnmt.createAssignment(assignmentName, Input.codeFormName);
		bc.selectproject();
		search.basicContentSearch(searchText);
		search.bulkAssignExisting(assignmentName);
		bc.selectproject();
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		String status = agnmt.updateAssignmentDetails(assignmentName);
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.viewSelectedAssgnUsingPagination("updated " + assignmentName);
		driver.scrollPageToTop();
		bc.waitForElement(agnmt.getAssignmentActionDropdown());
		agnmt.getAssignmentActionDropdown().waitAndClick(5);
		agnmt.getAssignmentAction_CopyAssignment().ScrollTo();
		agnmt.getAssignmentAction_CopyAssignment().waitAndClick(5);
		bc.getYesBtn().waitAndClick(5);
		bc.VerifySuccessMessage("Record copied successfully");
		agnmt.editCopyAssignmentUsingPaginationConcept("updated " + assignmentName);
		agnmt.verifyAgnmtDetailsInCopyAgnmt(assignmentName, Input.codeFormName, status);
	    agnmt.deleteSelectedAgnmt(assignmentName);
		agnmt.deleteAssgnmntUsingPagination("updated " + assignmentName);	
		lp.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @description: To verify that RMU changes in the Copied Assignment if
	 *               Cascading settings are ON/OFF RPMXCON-53760
	 */
	@Test(description ="RPMXCON-53760",enabled = true, groups = { "regression" })
	public void verifyCascadingSettingsOfCopyAssginment() throws InterruptedException {
		bc.stepInfo("To verify that RMU changes in the Copied Assignment if Cascading settings are ON/OFF");
		bc.stepInfo("Test case Id: RPMXCON-53760");
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		String assignmentName_2 = "AR2Assignment" + Utility.dynamicNameAppender();
		String CascasdeAssignmentGrpName = "CascadeAssignmentGrp" + Utility.dynamicNameAppender();
		String NonCascasdeAssignmentGrpName = "NonCascadeAssignmentGrp" + Utility.dynamicNameAppender();
		bc.stepInfo("Logged in as RMU user");
		agnmt.validateCopyAssgnCascadingNoncascadingSettings(CascasdeAssignmentGrpName, assignmentName, "Yes");
		bc.selectproject();
		agnmt.validateCopyAssgnCascadingNoncascadingSettings(NonCascasdeAssignmentGrpName, assignmentName_2, "No");
		lp.logout();
	
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @description: To verify that on marking the documents as completed the counts
	 *               are getting reflected correctly on RU Dashboard & My Activity
	 *               page. RPMXCON-53714
	 */
	@Test(description ="RPMXCON-53714",enabled = true, groups = { "regression" })
	public void verifyCompletedDocsCountInReviewerPg() throws InterruptedException {
		bc.stepInfo("To verify that on marking the documents as completed the counts are getting"
				+ " reflected correctly on RU Dashboard & My Activity page.");
		bc.stepInfo("Test case Id: RPMXCON-53714");
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		assertion = new SoftAssert();
		agnmt.createAssignment(assignmentName, Input.codeFormName);
		bc.selectproject();
		search.basicContentSearch(searchText);
		String pureHits = search.verifyPureHitsCount();
		search.bulkAssignExisting(assignmentName);
		bc.stepInfo("Created a assignment " + assignmentName);
		bc.selectproject();
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		agnmt.addReviewerAndDistributeDocs();
		bc.waitForElement(agnmt.getAssignment_BackToManageButton());
		agnmt.getAssignment_BackToManageButton().waitAndClick(5);
		agnmt.viewSelectedAssgnUsingPagination(assignmentName);
		driver.scrollPageToTop();
		agnmt.getAssignmentActionDropdown().waitAndClick(3);
		bc.waitForElement(agnmt.getAssignmentAction_Completedoc());
		agnmt.getAssignmentAction_Completedoc().waitAndClick(5);
		bc.getYesBtn().waitAndClick(5);
		bc.VerifySuccessMessage("All Documents successfully completed.");
		bc.impersonateRMUtoReviewer();
		bc.waitForElement(agnmt.getAssignmentsInreviewerPg());
		bc.waitForElement(agnmt.getAssignmentsBatchProgressInreviewerPg(assignmentName));
		try {
			assertion.assertEquals(agnmt.getAssignmentsBatchProgressInreviewerPg(assignmentName).getText(), "100%");
			bc.passedStep("The assignment batch progress is 100% when all documents are completed");
		} catch (Exception e) {
			bc.failedStep("The assignment batch progress is not displayed as 100% when all documents are completed");
		}
		agnmt.getAssignmentsInreviewerPg().waitAndClick(5);
		bc.waitForElement(agnmt.getAssignmentsCompletedCountInreviewerPg(assignmentName));
		try {
			assertion.assertEquals(agnmt.getAssignmentsCompletedCountInreviewerPg(assignmentName).getText(), pureHits);
			bc.passedStep("The completed documents count are displayed " + pureHits + " as expected");
		} catch (Exception e) {
			bc.failedStep("The completed documents count are displayed as expected");
		}
		try {
			assertion.assertEquals(agnmt.getAssignmentsTodoCountInreviewerPg(assignmentName).getText(), "0");
			bc.passedStep("The To do count are displayed " + "0" + " as expected");
		} catch (Exception e) {
			bc.failedStep("The To do count are not displayed " + "0" + " as expected");
		}
		assertion.assertAll();
		lp.logout();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		agnmt.deleteAssgnmntUsingPagination(assignmentName);
		lp.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @description: To verify that on marking the documents as completed the counts
	 *               are getting reflected correctly on RU Dashboard & My Activity
	 *               page. RPMXCON-53718
	 */
	@Test(description ="RPMXCON-53718",enabled = true, groups = { "regression" })
	public void verifyUnCompletedDocsCountInReviewerPg() throws InterruptedException {
		bc.stepInfo(
				"To verify that on marking the documents as un-completed the counts are getting reflected correctly on RU Dashboard & My Activity page.");
		bc.stepInfo("Test case Id: RPMXCON-53718");
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		assertion = new SoftAssert();
		agnmt.createAssignment(assignmentName, Input.codeFormName);
		bc.selectproject();
		search.basicContentSearch(searchText);
		String pureHits = search.verifyPureHitsCount();
		search.bulkAssignExisting(assignmentName);
		bc.stepInfo("Created a assignment " + assignmentName);
		bc.selectproject();
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		agnmt.addReviewerAndDistributeDocs();
		bc.waitForElement(agnmt.getAssignment_BackToManageButton());
		agnmt.getAssignment_BackToManageButton().waitAndClick(5);
		agnmt.viewSelectedAssgnUsingPagination(assignmentName);
		agnmt.getAssignmentActionDropdown().waitAndClick(3);
		bc.waitForElement(agnmt.getAssignmentAction_Completedoc());
		agnmt.getAssignmentAction_Completedoc().waitAndClick(5);
		bc.getYesBtn().waitAndClick(5);
		bc.VerifySuccessMessage("All Documents successfully completed.");
		agnmt.viewSelectedAssgnUsingPagination(assignmentName);
		driver.scrollingToElementofAPage(agnmt.getSelectAssignment(assignmentName));
		agnmt.getSelectAssignment(assignmentName).waitAndClick(3);
		driver.scrollPageToTop();
		agnmt.getAssignmentActionDropdown().waitAndClick(3);
		bc.waitForElement(agnmt.getAssignmentAction_UnCompletedoc());
		agnmt.getAssignmentAction_UnCompletedoc().waitAndClick(5);
		bc.getYesBtn().waitAndClick(5);
		bc.VerifySuccessMessage("All Documents successfully un-completed.");
		bc.impersonateRMUtoReviewer();
		bc.waitForElement(agnmt.getAssignmentsInreviewerPg());
		bc.waitForElement(agnmt.getAssignmentsBatchProgressInreviewerPg(assignmentName));
		try {
			assertion.assertEquals(agnmt.getAssignmentsBatchProgressInreviewerPg(assignmentName).getText(), "0%");
			assertion.assertAll();
			bc.passedStep("The assignment batch progress is 0% when all documents are incompleted");
		} catch (Exception e) {
			assertion.assertAll();
			bc.stepInfo(e.getMessage());
			bc.failedStep("The assignment batch progress is not displayed as 0% when all documents are incompleted");
		}
		agnmt.getAssignmentsInreviewerPg().waitAndClick(5);
		bc.waitForElement(agnmt.getAssignmentsCompletedCountInreviewerPg(assignmentName));
		try {
			assertion.assertEquals(agnmt.getAssignmentsCompletedCountInreviewerPg(assignmentName).getText(), "0");
			bc.passedStep("The completed documents count are displayed " + "0" + " as expected");
		} catch (Exception e) {
			assertion.assertAll();
			bc.failedStep("The completed documents count are not displayed as expected");
		}
		try {
			assertion.assertEquals(agnmt.getAssignmentsTodoCountInreviewerPg(assignmentName).getText(), pureHits);
			assertion.assertAll();
			bc.passedStep("The To do count are displayed " + pureHits + " as expected");
		} catch (Exception e) {
			bc.stepInfo(e.getMessage());
			bc.failedStep("The To do count are not displayed as expected");
		}
		lp.logout();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		agnmt.deleteAssgnmntUsingPagination(assignmentName);
		lp.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @description: To verify that validations are displayed properly.
	 *               RPMXCON-53706
	 */
	@Test(description ="RPMXCON-53706",enabled = true, groups = { "regression" })
	public void verifyDisplayedValidationsInManageReviewer() throws InterruptedException {
		bc.stepInfo("To verify that validations are displayed properly.");
		bc.stepInfo("Test case Id: RPMXCON-53706");
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		agnmt.createAssignment(assignmentName, Input.codeFormName);
		bc.selectproject();
		search.basicContentSearch(searchText);
		search.bulkAssignExisting(assignmentName);
		bc.stepInfo("Created a assignment " + assignmentName);
		bc.selectproject();
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		agnmt.verifyDisplayedValidations();
		agnmt.deleteSelectedAgnmt(assignmentName);
		lp.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @description: To verify that if RU has only and some completed documents then
	 *               on selecting Remove Documents options validation must be
	 *               displayed. RPMXCON-53693
	 */
	@Test(description ="RPMXCON-53693",enabled = true, groups = { "regression" })
	public void verifyDisplayValidationOnRemoveDocs() throws InterruptedException {
		bc.stepInfo(
				"To verify that if RU has only and some completed documents then on selecting Remove Documents options validation must be displayed.");
		bc.stepInfo("Test case Id: RPMXCON-53693");
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		agnmt.createAssignment(assignmentName, Input.codeFormName);
		bc.selectproject();
		int count = search.basicContentSearch(searchText);
		search.bulkAssignExisting(assignmentName);
		bc.stepInfo("Created a assignment " + assignmentName);
		bc.selectproject();
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		agnmt.verifyDisplayedValidationsOfRemovedDocs(count);
		agnmt.deleteSelectedAgnmt(assignmentName);
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description To verify that RMU can create New Assignments from
	 *              "Assign/Unassign Documents" with descending Metadata selection
	 */
	@Test(description ="RPMXCON-54916",enabled = true, groups = { "regression" })
	public void CreateAssgn_DescendingMetadata() throws InterruptedException {
		assertion = new SoftAssert();
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();

		bc.stepInfo("Test case Id:RPMXCON-54916");
		bc.stepInfo("To verify that RMU can create New Assignments from \"Assign/Unassign Documents\" "
				+ "with descending Metadata selection");
		search.basicContentSearch(searchText);
		search.bulkAssign();
		agnmt.FinalizeAssignmentAfterBulkAssign();
		agnmt.createAssignment_fromAssignUnassignPopup(assignmentName, Input.codeFormName);
		driver.scrollingToElementofAPage(agnmt.getAssgn_DocSequence_SortbyMetadata());
		agnmt.Assgnwithdocumentsequence(Input.metaDataName, "Descending");
		bc.passedStep("Assignment is created with sort by metadata as descending order--" + assignmentName);
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		driver.scrollingToElementofAPage(agnmt.getAssignment_ManageReviewersTab());
		agnmt.addReviewerAndDistributeDocs();
		lp.logout();
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		try {
			agnmt.verifyDescendingMetaDataSorting_DocList(assignmentName, Input.metaDataName);
			assertion.assertAll();
			bc.passedStep("sucessfully verified that whether RMU can create Assignments from assign/unassign documents"
					+ "with descending meta data sorting");
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep(
					"Exception occured,while verifying  whether RMU can create Assignments from assign/unassign documents"
							+ "with descending meta data sorting");

		}
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description To verify that RMU can create New Assignments from
	 *              "Assign/Unassign Documents" pop up if Search Results are saved.
	 */
	@Test(description ="RPMXCON-53816",enabled = true, groups = { "regression" })
	public void CreateAssignfromSavedSearch() throws InterruptedException, ParseException, IOException {
		try {
			assertion = new SoftAssert();
			bc.stepInfo("Test case Id:RPMXCON-53816");
			bc.stepInfo(
					"To verify that RMU can create New Assignments from \"Assign/Unassign Documents\" pop up if Search Results are saved.");
			bc.stepInfo("Test case Id: RPMXCON-53816");
			String savedSearchName = "SaveAssign" + Utility.dynamicNameAppender();
			String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
			search.advancedContentSearch(searchText);
			int ExpectedDocCount = Integer.parseInt(search.verifyPureHitsCount());
			System.out.println(ExpectedDocCount);
			search.saveSearchAdvanced(savedSearchName);
			bc.stepInfo("Created a saved search " + savedSearchName);
			savedSearch = new SavedSearch(driver);
			savedSearch.savedSearch_Searchandclick(savedSearchName);
			savedSearch.getSavedSearchToBulkAssign().waitAndClick(5);
			agnmt.FinalizeAssignmentAfterBulkAssign();
			agnmt.createAssignment_fromAssignUnassignPopup(assignmentName, Input.codeFormName);
			agnmt.getAssignmentSaveButton().waitAndClick(5);
			bc.passedStep(
					"assignment is created from saved search page assignUnassign document popup" + assignmentName);
			int ActualDocCount = Integer.parseInt(agnmt.verifydocsCountInAssgnPage(assignmentName));
			System.out.println(ActualDocCount);
			assertion.assertEquals(ActualDocCount, ExpectedDocCount);
			bc.passedStep(
					"Sucesfully verified that RMU can create New Assignments from \"Assign/Unassign Documents\" pop up "
							+ "if Search Results are saved.");
			assertion.assertAll();

		} catch (Exception e) {
			bc.stepInfo(e.getMessage());
			bc.failedStep("Failed to verify that RMU can create assignments from assign/unassign popup"
					+ " if search results are saved");
		}
		lp.logout();

	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @description: To verify that for Assignment if draw from pool in enable then
	 *               RU is able to view/use the "Draw From Pool". RPMXCON-53883
	 */
	@Test(description ="RPMXCON-53883",enabled = true, groups = { "regression" })
	public void validateDrawFromPoolLinkInReviewerPg() throws InterruptedException {
		assertion = new SoftAssert();
		bc.stepInfo(
				"To verify that if RU has only and some completed documents then on selecting Remove Documents options validation must be displayed.");
		bc.stepInfo("Test case Id: RPMXCON-53883");
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		agnmt.createAssignment(assignmentName, Input.codeFormName);
		bc.selectproject();
		int count = search.basicContentSearch(searchText);
		search.bulkAssignExisting(assignmentName);
		bc.stepInfo("Created a assignment " + assignmentName);
		bc.selectproject();
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		int unassignedDocs = agnmt.distributeHalfTheDocsToReviewer(count);
		bc.impersonateRMUtoReviewer();
		bc.waitForElement(agnmt.getAssignmentsInreviewerPg());
		agnmt.getAssignmentsInreviewerPg().waitAndClick(5);
		bc.waitForElement(agnmt.getAssignmentsDrawPoolInreviewerPg(assignmentName));
		if (agnmt.getAssignmentsDrawPoolInreviewerPg(assignmentName).isElementPresent() == true) {
			bc.passedStep("Draw pool link is displayed");
			bc.waitTillElemetToBeClickable(agnmt.getAssignmentsDrawPoolInreviewerPg(assignmentName));
			driver.scrollingToElementofAPage(agnmt.getAssignmentsDrawPoolInreviewerPg(assignmentName));
			agnmt.getAssignmentsDrawPoolInreviewerPg(assignmentName).waitAndClick(5);
		} else {
			bc.failedStep("Draw pool link is not displayed");
		}
		driver.Navigate().refresh();
		bc.waitForElement(agnmt.getAssignmentsTodoCountInreviewerPg(assignmentName));
		try {
			bc.waitTillTextToPresent(agnmt.getAssignmentsTodoCountInreviewerPg(assignmentName),Integer.toString(unassignedDocs));
			assertion.assertEquals(agnmt.getAssignmentsTodoCountInreviewerPg(assignmentName).getText(),
					Integer.toString(unassignedDocs));
			assertion.assertAll();
			bc.passedStep("The To do count are displayed " + unassignedDocs + " as expected");
		} catch (Exception e) {
			bc.failedStep("The To do count are not displayed as expected");
		}
		lp.logout();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		agnmt.deleteAssgnmntUsingPagination(assignmentName);
		lp.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @description: To verify that RMU is able to view/edit/move/cancel/delete
	 *               changes in Assignment Group RPMXCON-53594
	 */
	@Test(description ="RPMXCON-53594",enabled = true, groups = { "regression" })
	public void validateAddUpdateDeleteInassgnGrp() throws InterruptedException {
		assertion = new SoftAssert();
		bc.stepInfo("To verify that RMU is able to view/edit/move/cancel/delete changes in Assignment Group");
		bc.stepInfo("Test case Id: RPMXCON-53594");
		String assignmentGrpName = "assignmentGrp" + Utility.dynamicNameAppender();
		String assignmentGrpName_2 = "assignmentGrp" + Utility.dynamicNameAppender();
		String assignmentGrpName_3 = "assignmentGrp" + Utility.dynamicNameAppender();
		String assignmentGrpName_4 = "assignmentGrp" + Utility.dynamicNameAppender();
		String assgnGrpName = "assignmentGrp" + Utility.dynamicNameAppender();
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		String status = null;
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.createCascadeNonCascadeAssgnGroup(assgnGrpName, "No");
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.createCascadeNonCascadeAssgnGroup(assignmentGrpName, "No");
		agnmt.selectAssignmentGroup(assignmentGrpName);
		agnmt.createAssignmentFromAssgnGroup(assignmentName, Input.codeFormName);
		agnmt.getAssignment_BackToManageButton();
		agnmt.editAssgnGrp(assignmentGrpName, "Yes");
		status = agnmt.updateAssgnGrpDetails(assignmentGrpName_2);
		bc.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		agnmt.editAssgnGrp(assignmentGrpName_2, "Yes");
		agnmt.validateUpdatedDetailsInAssgnGrp(assignmentGrpName_2, status);
		bc.selectproject();
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.editAssgnGrp(assignmentGrpName_2, "No");
		status = agnmt.updateAssgnGrpDetails(assignmentGrpName_3);
		bc.waitTillElemetToBeClickable(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		lp.logout();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.editAssgnGrp(assignmentGrpName_3, "No");
		agnmt.validateUpdatedDetailsInAssgnGrp(assignmentGrpName_3, status);
		System.out.println(status + " " + assignmentGrpName_2);
		status = agnmt.updateAssgnGrpDetails(assignmentGrpName_4);
		System.out.println(status + " " + assignmentGrpName_2);
		bc.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		driver.waitForPageToBeReady();
		agnmt.dragAndDrop(assignmentGrpName_4, assgnGrpName);
		agnmt.selectChildAssgnGrpFromParentAssgnGroup(assgnGrpName, assignmentGrpName_4);
		agnmt.editAssgnGrp(assignmentGrpName_4, "Yes");
		bc.waitForElement(agnmt.getParentAssignmentGroupName());
		try {
			assertion.assertEquals(agnmt.getParentAssignmentGroupName().GetAttribute("value"), assgnGrpName);
			bc.passedStep("Child group comes under parent group as expected");
		} catch (Exception e) {
			bc.failedStep("Child group not comes under parent group as expected");
		}
		agnmt.getAssignment_BackToManageButton().waitAndClick(5);
		bc.waitForElement(agnmt.getSelectAssignment(assignmentName));
		agnmt.getSelectAssignment(assignmentName).waitAndClick(5);
		agnmt.getAssignmentActionDropdown().waitAndClick(3);
		bc.waitForElement(agnmt.getAssignmentAction_EditAssignment());
		agnmt.getAssignmentAction_EditAssignment().waitAndClick(3);
		bc.waitForElement(agnmt.getParentAssignmentGroupName());
		try {
			assertion.assertEquals(agnmt.getParentAssignmentGroupName().GetAttribute("value"), assignmentGrpName_4);
			bc.passedStep("Child group comes under parent group as expected");
		} catch (Exception e) {
			bc.failedStep("Child group not comes under parent group as expected");
		}
		agnmt.getAssignment_BackToManageButton().waitAndClick(5);
		bc.waitTillElemetToBeClickable(agnmt.getAssignmentActionDropdown());
		agnmt.getAssignmentActionDropdown().waitAndClick(5);
		bc.waitForElement(agnmt.getAssignmentAction_DeleteAssignment());
		agnmt.getAssignmentAction_DeleteAssignment().waitAndClick(3);
		bc.getYesBtn().waitAndClick(5);
		bc.passedStep("Assignment deleted successfully");
		agnmt.DeleteChildAssgnGroup(assignmentGrpName_4, assgnGrpName);
		agnmt.DeleteAssgnGroup(assgnGrpName);
		assertion.assertAll();
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * 
	 * @description: To verify that RMU is able to view option "View All Docs in
	 *               DocList"(RPMXCON-53761)
	 */
	@Test(description ="RPMXCON-53761",enabled = true, groups = { "regression" })
	public void verifyTagAllOptions() throws InterruptedException, IOException {
		bc.stepInfo("To verify that RMU is able to view Tag all option in Action dropdown in Manage Assignment Page");
		bc.stepInfo("Test case Id:RPMXCON-53761");
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		search.basicContentSearch(searchText);
		search.bulkAssign();
		agnmt.FinalizeAssignmentAfterBulkAssign();
		agnmt.createAssignment_fromAssignUnassignPopup(assignmentName, Input.codeFormName);
		driver.scrollingToElementofAPage(agnmt.getAssignmentSaveButton());
		bc.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(3);
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.viewSelectedAssgnUsingPagination(assignmentName);
		driver.scrollingToElementofAPage(agnmt.getSelectAssignment(assignmentName));
		agnmt.getSelectAssignment(assignmentName).waitAndClick(5);
		agnmt.VerifyTagAllOption();
		bc.waitForElement(agnmt.getAssignmentAction_DeleteAssignment());
		agnmt.getAssignmentAction_DeleteAssignment().waitAndClick(3);
		bc.getYesBtn().waitAndClick(5);
		bc.VerifySuccessMessage("Assignment deleted successfully");
		lp.logout();
	}

	/**
	 * 
	 * @author Jayanthi.ganesan
	 * @description Bulk assignment - Default Metadata list is retained even after
	 *              modifying bulk assignment
	 */
	@Test(description ="RPMXCON-54936",enabled = true, groups = { "regression" })
	public void verifyMetaDataList() throws InterruptedException, IOException {
		bc.stepInfo("Bulk assignment - Default Metadata list is retained even after modifying bulk assignment");
		bc.stepInfo("Test case Id:RPMXCON-54936");
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		search.basicContentSearch(searchText);
		search.bulkAssign();
		agnmt.FinalizeAssignmentAfterBulkAssign();
		agnmt.createAssignment_fromAssignUnassignPopup(assignmentName,Input.codeFormName);
		driver.scrollingToElementofAPage(agnmt.getAssignmentSaveButton());
		bc.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(3);
		bc.stepInfo("Created a Assignment--" + assignmentName);
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		// opening the assignment in edit mode
		agnmt.viewSelectedAssgnUsingPagination(assignmentName);
		driver.scrollingToElementofAPage(agnmt.getSelectAssignment(assignmentName));
		agnmt.getSelectAssignment(assignmentName).waitAndClick(5);
		agnmt.NavigateToNewEditAssignmentPage("edit");
		agnmt.getParentAssignmentGroupName().Displayed();
		agnmt.getSelectedClassification().selectFromDropdown().selectByVisibleText("2LR");
		agnmt.addReviewerAndDistributeDocs();
		driver.scrollingToElementofAPage(agnmt.getAssignmentSaveButton());
		bc.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(3);
		bc.stepInfo("Modified the assignment and distributed docs to the reviewer");
		agnmt.getAssignment_BackToManageButton().Click();
		driver.waitForPageToBeReady();
		// verifying the metadata list by clicking select metadata button in assignment
		// page and from doc view page.
		agnmt.NavigateToNewEditAssignmentPage("edit");
		try {
			agnmt.ValidateRetainedMetaDataList(assignmentName);
			bc.passedStep(
					"Sucessfully verified that Default Metadata list is retained even after modifying bulk assignment");
		} catch (Exception e) {
			bc.stepInfo(e.getMessage());
			bc.failedStep("Exception occurred");
		}
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description Quick Batch assignment - Default Metadata list is retained even
	 *              after modifying Quick batch.
	 */

	@Test(description ="RPMXCON-54934",enabled = true, groups = { "regression" })
	public void VerifyMetaList_QuickBatch() throws InterruptedException, ParseException, IOException {
		bc.stepInfo("Test case Id: RPMXCON-54934");
		bc.stepInfo("Quick Batch assignment - Default Metadata list is retained even after modifying Quick batch");
		// Creating quick batch assignment
		search.basicContentSearch(Input.searchString1);
		search.quickbatch();
		String assignmentQB1 = "AR2Assignment" + Utility.dynamicNameAppender();
		agnmt.createNewquickBatchWithoutReviewer(assignmentQB1, Input.codeFormName);
		bc.stepInfo("Created Quick batch Assignment --" + assignmentQB1);
		// opening the quick batch assignment in edit mode
		agnmt.editAssignmentUsingPaginationConcept(assignmentQB1);
		agnmt.getParentAssignmentGroupName().Displayed();
		agnmt.getSelectedClassification().selectFromDropdown().selectByVisibleText("2LR");
		agnmt.addReviewerAndDistributeDocs();
		bc.stepInfo("Modified the assignment and distributed docs to the reviewer");
		// verifying the metadata list by clicking select metadata button in assignment
		// page and from doc view page.
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		driver.waitForPageToBeReady();
		agnmt.NavigateToNewEditAssignmentPage("edit");
		try {
			agnmt.ValidateRetainedMetaDataList(assignmentQB1);
			bc.passedStep(
					"Sucessfully verified that Default Metadata list is retained even after modifying bulk assignment");
		} catch (Exception e) {
			bc.stepInfo(e.getMessage());
			bc.failedStep("Exception occurred");

		}
		lp.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @description: To verify that Edit Actions can be performed on Copied
	 *               Assignment RPMXCON-53758
	 */
	@Test(description ="RPMXCON-53758",enabled = true, groups = { "regression" })
	public void verifyActionFunctionsValidationsInCopyAssignment() throws InterruptedException {
		bc.stepInfo("To verify that Edit Actions can be performed on Copied Assignment");
		bc.stepInfo("Test case Id: RPMXCON-53758");
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		String tag = "tag" + Utility.dynamicNameAppender();
		String folder = "folder" + Utility.dynamicNameAppender();
		agnmt.createAssignment(assignmentName, Input.codeFormName);
		bc.selectproject();
		bc.stepInfo("Created a assignment " + assignmentName);
		bc.selectproject();
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.viewSelectedAssgnUsingPagination(assignmentName);
		agnmt.getSelectAssignment(assignmentName).waitAndClick(3);
		driver.scrollPageToTop();
		agnmt.getAssignmentActionDropdown().waitAndClick(5);
		bc.waitForElement(agnmt.getAssignmentActionDropdown());
		agnmt.getAssignmentAction_CopyAssignment().waitAndClick(5);
		bc.getYesBtn().waitAndClick(5);
		bc.VerifySuccessMessage("Record copied successfully");
		bc.selectproject();
		agnmt.editCopyAssignmentUsingPaginationConcept(assignmentName);
		String copyAssgnName = agnmt.getAssignmentName().GetAttribute("value");
		search.basicContentSearch(searchText);
		search.bulkAssignExistingForCopyAssignment(copyAssgnName);
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.NavigateToNewEditAssignmentPage("edit");
		agnmt.verifySuccessMsgValidations();
		bc.selectproject();
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.NavigateToNewEditAssignmentPage("edit");
		agnmt.validateNavigationOfDocViewPg(assignmentName);
		bc.selectproject();
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.NavigateToNewEditAssignmentPage("edit");
		agnmt.validateNavigationOfDocListPg();
		bc.selectproject();
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.NavigateToNewEditAssignmentPage("edit");
		bc.waitForElement(agnmt.getAssignment_ManageReviewersTab());
		agnmt.getAssignment_ManageReviewersTab().waitAndClick(5);
		agnmt.getAssgn_ManageRev_selectReviewer(Input.rmu1userName).waitAndClick(5);
		agnmt.selectActionsInManageRev(agnmt.getAssgn_ManageRev_Action_tagdoc());
		search.BulkActions_Tag(tag);
		bc.passedStep("Documents are tagged for the reviewer successfully");
		bc.selectproject();
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.NavigateToNewEditAssignmentPage("edit");
		bc.waitForElement(agnmt.getAssignment_ManageReviewersTab());
		agnmt.getAssignment_ManageReviewersTab().waitAndClick(5);
		agnmt.getAssgn_ManageRev_selectReviewer(Input.rmu1userName).waitAndClick(5);
		agnmt.selectActionsInManageRev(agnmt.getAssgn_ManageRev_Action_folderdoc());
		search.BulkActions_Folder(folder);
		bc.passedStep("Documents are foldered for the reviewer successfully");
		bc.selectproject();
		agnmt.deleteSelectedAgnmt(assignmentName);
		agnmt.deleteAssgnmntUsingPagination(assignmentName);
		lp.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @throws AWTException 
	 * @description: Verify that Keyword should automatically added to New
	 *               assignment through Bulk Assign from Save Search RPMXCON-54908
	 */
	@Test(description ="RPMXCON-54908",enabled = true, groups = { "regression" })
	public void validateKeywordsInAssignment() throws InterruptedException, AWTException {
		bc.stepInfo(
				"Verify that Keyword should automatically added to New assignment through Bulk Assign from Save Search");
		bc.stepInfo("Test case Id: RPMXCON-54908");
		String savedSearchName = "SaveAssign" + Utility.dynamicNameAppender();
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		String keywordName = "key"+Utility.dynamicNameAppender();
		keyword = new KeywordPage(driver);
		this.driver.getWebDriver().get(Input.url + "Keywords/Keywords");
		keyword.addKeywordWithColor(keywordName, "Red");
		search.advancedContentSearch(searchText);
		int ExpectedDocCount = Integer.parseInt(search.verifyPureHitsCount());
		System.out.println(ExpectedDocCount);
		search.saveSearchAdvanced(savedSearchName);
		bc.stepInfo("Created a saved search " + savedSearchName);
		savedSearch = new SavedSearch(driver);
		savedSearch.savedSearch_Searchandclick(savedSearchName);
		savedSearch.getSavedSearchToBulkAssign().waitAndClick(5);
		agnmt.FinalizeAssignmentAfterBulkAssign();
		agnmt.createAssignment_fromAssignUnassignPopup(assignmentName, Input.codingFormName);
		agnmt.verifyKeywordsPopup();
		this.driver.getWebDriver().get(Input.url + "Keywords/Keywords");
		keyword.deleteKeyword(keywordName);
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description To verify the cascading functionality if it is ON/OFF for parent
	 *              Assignment Group.
	 */

	@Test(description ="RPMXCON-53597",enabled = true, groups = { "regression" })
	public void VerifyCascadeFunctionality() throws InterruptedException {
		String cascadeSettings_yes = "Yes";
		String cascadeSettings_No = "No";
		String cascadeAsgnGrpName = "CascadeAssgnGrp" + Utility.dynamicNameAppender();
		String cascadeAssgnName = "cascadeAssign" + Utility.dynamicNameAppender();
		String childCascadeAssgnGroup = "childCascadeAssgnGroup" + Utility.dynamicNameAppender();
		String NoncascadeAsgnGrpName = "nonCascadeAssgnGrp" + Utility.dynamicNameAppender();
		String noncascadeAssgnName = "noncascadeAssign" + Utility.dynamicNameAppender();
		String childNonCascadeAssgnGroup = "childnonCascadeAssgnGroup" + Utility.dynamicNameAppender();
		bc.stepInfo("To verify the cascading functionality if it is ON/OFF for parent Assignment Group.");
		bc.stepInfo("Test case Id:RPMXCON-53597");
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.createCascadeNonCascadeAssgnGroup_withoutSave(cascadeAsgnGrpName, cascadeSettings_yes);
		String enabled = agnmt.getAssgnGrp_Create_DrawPooltoggle().Class();
		if (!enabled.equals("true")) {
			agnmt.getAssgnGrp_Create_DrawPooltoggle().waitAndClick(10);
		}
		bc.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		bc.stepInfo("Created A Cascade Assignment group -" + cascadeAsgnGrpName);
		agnmt.selectAssignmentGroup(cascadeAsgnGrpName);
		agnmt.createCascadeNonCascadeAssgnGroup_withoutSave(childCascadeAssgnGroup, cascadeSettings_No);
		agnmt.VerifyCascadefunctionality_ON();
		driver.scrollPageToTop();
		bc.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		bc.stepInfo("Created A Child Assign group " + childCascadeAssgnGroup
				+ " under Parent Cascade Assignment group -" + cascadeAsgnGrpName + "");
		agnmt.selectChildAssgnGrpFromParentAssgnGroup(cascadeAsgnGrpName, childCascadeAssgnGroup);
		agnmt.NavigateToNewEditAssignmentPage("new");
		agnmt.createAssignment_fromAssignUnassignPopup(cascadeAssgnName, Input.codeFormName);
		agnmt.VerifyCascadefunctionality_ON();
		driver.scrollPageToTop();
		bc.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		bc.stepInfo("Created A Cascade Assignment-" + cascadeAssgnName + "under Child Cascade assignment group-"
				+ cascadeAsgnGrpName + "");

		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.createCascadeNonCascadeAssgnGroup_withoutSave(NoncascadeAsgnGrpName, cascadeSettings_No);
		String enabled1 = agnmt.getAssgnGrp_Create_DrawPooltoggle().Class();
		if (!enabled1.equals("true")) {
			agnmt.getAssgnGrp_Create_DrawPooltoggle().waitAndClick(5);
		}
		bc.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		bc.stepInfo("Created A NonCascade Assignment group -" + NoncascadeAsgnGrpName);
		agnmt.selectAssignmentGroup(NoncascadeAsgnGrpName);
		agnmt.createCascadeNonCascadeAssgnGroup_withoutSave(childNonCascadeAssgnGroup, cascadeSettings_No);
		agnmt.verifyCascadeFunctionality_OFF();
		agnmt.ValidateEnabledToggleBtn_PresentationControl(9, 1);
		agnmt.ValidateEnabledToggleBtn_PresentationControl(7, 2);
		agnmt.ValidateEnabledToggleBtn_PresentationControl(7, 3);
		driver.scrollPageToTop();
		bc.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		bc.stepInfo("Created A Child Assign group " + childNonCascadeAssgnGroup
				+ " under Parent Cascade Assignment group -" + NoncascadeAsgnGrpName + "");
		agnmt.selectChildAssgnGrpFromParentAssgnGroup(NoncascadeAsgnGrpName, childNonCascadeAssgnGroup);
		agnmt.NavigateToNewEditAssignmentPage("new");
		agnmt.createAssignment_fromAssignUnassignPopup(noncascadeAssgnName, Input.codeFormName);
		agnmt.verifyCascadeFunctionality_OFF();
		agnmt.ValidateEnabledToggleBtn_PresentationControl(7, 1);
		agnmt.ValidateEnabledToggleBtn_PresentationControl(7, 2);
		agnmt.ValidateEnabledToggleBtn_PresentationControl(7, 3);
		agnmt.DeleteChildAssgnGroup(childNonCascadeAssgnGroup, NoncascadeAsgnGrpName);
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.DeleteAssgnGroup(NoncascadeAsgnGrpName);
		lp.logout();
	}

	/**
	 * 
	 * @author Jayanthi.ganesan
	 * @description Bulk assignment - Metadata list is retained even after modifying
	 *              bulk assignment for impersonated user
	 */
	@Test(description ="RPMXCON-54933",dataProvider = "Users", enabled = true, groups = { "regression" })
	public void verifyMetaDataList_impersonatedUser(String userName, String Password)
			throws InterruptedException, IOException {

		lp.logout();
		lp.loginToSightLine(userName, Password);
		if (userName.equalsIgnoreCase(Input.pa1userName)) {
			bc.impersonatePAtoRMU();
			bc.stepInfo("Logged in As PA and imperonated as RMU");
		}
		if (userName.equalsIgnoreCase(Input.sa1userName)) {
			bc.impersonateSAtoRMU();
			bc.stepInfo("Logged in As SA and imperonated as RMU");
		}

		bc.stepInfo("Bulk assignment - Metadata list is retained even after modifying "
				+ "bulk assignment for impersonated user");
		bc.stepInfo("Test case Id:RPMXCON-54933");
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		search.basicContentSearch(searchText);
		search.bulkAssign();
		agnmt.FinalizeAssignmentAfterBulkAssign();
		agnmt.createAssignment_fromAssignUnassignPopup(assignmentName,Input.codeFormName);
		driver.scrollingToElementofAPage(agnmt.getAssignmentSaveButton());
		bc.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(3);
		bc.stepInfo("Created a Assignment--" + assignmentName);
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		// opening the assignment in edit mode
		agnmt.viewSelectedAssgnUsingPagination(assignmentName);
		driver.scrollingToElementofAPage(agnmt.getSelectAssignment(assignmentName));
		agnmt.getSelectAssignment(assignmentName).waitAndClick(5);
		agnmt.NavigateToNewEditAssignmentPage("edit");
		agnmt.getParentAssignmentGroupName().Displayed();
		agnmt.getSelectedClassification().selectFromDropdown().selectByVisibleText("2LR");
		agnmt.addReviewerAndDistributeDocs();
		driver.scrollingToElementofAPage(agnmt.getAssignmentSaveButton());
		bc.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(3);
		bc.stepInfo("Modified the assignment and distributed docs to the reviewer");
		agnmt.getAssignment_BackToManageButton().Click();
		driver.waitForPageToBeReady();
		agnmt.NavigateToNewEditAssignmentPage("edit");
		try {
			agnmt.ValidateRetainedMetaDataList(assignmentName);
			bc.passedStep("Sucessfully verified that Default Metadata list is retained even after modifying bulk "
					+ "assignment for impersonated User");
		} catch (Exception e) {
			bc.stepInfo(e.getMessage());
			bc.failedStep("Exception occurred");
		}
		lp.logout();
	}

	@DataProvider(name = "Users")
	public Object[][] Users() {
		Object[][] usersList = { { Input.pa1userName, Input.pa1password }, { Input.sa1userName, Input.sa1password } };
		return usersList;
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param userName
	 * @param Password
	 * @description Quick Batch assignment - Default Metadata list is retained even
	 *              after modifying Quick batch for impersonated user
	 */
	@Test(description ="RPMXCON-54935",dataProvider = "Users", enabled = true, groups = { "regression" })
	public void VerifyMetaDataList_QuickBatchImpersonateadUser(String userName, String Password)
			throws InterruptedException, ParseException, IOException {
		bc.stepInfo("Test case Id: RPMXCON-54935");
		bc.stepInfo(
				"Quick Batch assignment - Default Metadata list is retained even after modifying Quick batch for impersonated user");
		// Creating quick batch assignment
		lp.logout();
		lp.loginToSightLine(userName, Password);
		if (userName.equalsIgnoreCase(Input.pa1userName)) {
			bc.impersonatePAtoRMU();
		}
		if (userName.equalsIgnoreCase(Input.sa1userName)) {
			bc.impersonateSAtoRMU();
		}
		// Creating quick batch assignment
		search.basicContentSearch(Input.searchString1);
		search.quickbatch();
		String assignmentQB1 = "AR2Assignment" + Utility.dynamicNameAppender();
		agnmt.createNewquickBatchWithoutReviewer(assignmentQB1, Input.codeFormName);
		bc.stepInfo("Created Quick batch Assignment --" + assignmentQB1);
		// opening the quick batch assignment in edit mode
		agnmt.editAssignmentUsingPaginationConcept(assignmentQB1);
		agnmt.getParentAssignmentGroupName().Displayed();
		agnmt.getSelectedClassification().selectFromDropdown().selectByVisibleText("2LR");
		agnmt.addReviewerAndDistributeDocs();
		bc.stepInfo("Modified the assignment and distributed docs to the reviewer");
		// verifying the metadata list by clicking select metadata button in assignment
		// page and from doc view page.
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		driver.waitForPageToBeReady();
		agnmt.NavigateToNewEditAssignmentPage("edit");
		try {
			agnmt.ValidateRetainedMetaDataList(assignmentQB1);
			bc.passedStep("Sucessfully verified that Default Metadata list is retained even after modifying bulk "
					+ "assignment for impersonated User.");
		} catch (Exception e) {
			bc.stepInfo(e.getMessage());
			bc.failedStep("Exception occurred");
		}
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @description RPMXCON-12669 Scenario 2 - Verify the display of 'Draw from
	 *              Pool' Action when 'Draw From Pool' option is enabled in
	 *              Assignment.
	 */
	@Test(description ="RPMXCON-54215",enabled = true, groups = { "regression" })
	public void VerifyDrawFromPooldisplay() throws InterruptedException, ParseException, IOException {
		bc.stepInfo("RPMXCON-12669 Scenario 2 - Verify the display of 'Draw from Pool' Action when 'Draw From Pool' "
				+ "option is enabled in   Assignment.");
		bc.stepInfo("Test case Id: RPMXCON-54215");
		String cascadeSettings_No = "No";
		String NoncascadeAsgnGrpName = "nonCascadeAssgnGrp" + Utility.dynamicNameAppender();
		String noncascadeAssgnName = "noncascadeAssign" + Utility.dynamicNameAppender();

		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.createCascadeNonCascadeAssgnGroup_withoutSave(NoncascadeAsgnGrpName, cascadeSettings_No);
		bc.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		bc.stepInfo("Created A NonCascade Assignment group -" + NoncascadeAsgnGrpName);
		agnmt.selectAssignmentGroup(NoncascadeAsgnGrpName);

		agnmt.NavigateToNewEditAssignmentPage("new");
		agnmt.createAssignment_fromAssignUnassignPopup(noncascadeAssgnName, Input.codeFormName);
		String enabled1 = agnmt.getAssgnGrp_Create_DrawPooltoggle().Class();
		if (!enabled1.equals("true")) {
			agnmt.getAssgnGrp_Create_DrawPooltoggle().waitAndClick(5);
		}
		bc.stepInfo("Draw pool option is enabled");
		driver.scrollPageToTop();
		bc.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		search.basicContentSearch(searchText);
		search.bulkAssignExisting(noncascadeAssgnName);
		agnmt.selectAssignmentGroup(NoncascadeAsgnGrpName);
		driver.scrollingToElementofAPage(agnmt.getSelectAssignment(noncascadeAssgnName));
		agnmt.getSelectAssignment(noncascadeAssgnName).waitAndClick(3);
		String docCount = agnmt.getDocCountInAssgnPg(noncascadeAssgnName).getText();
		agnmt.NavigateToNewEditAssignmentPage("edit");
		agnmt.assignmentDistributingToReviewerManager();
		bc.impersonateRMUtoReviewer();
		bc.stepInfo("Impersonated as reviewer");
		agnmt.VerifyTodoCountInReviewerPg(noncascadeAssgnName, docCount);
		lp.logout();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		agnmt.selectAssignmentGroup(NoncascadeAsgnGrpName);
		driver.scrollingToElementofAPage(agnmt.getSelectAssignment(noncascadeAssgnName));
		agnmt.getSelectAssignment(noncascadeAssgnName).waitAndClick(3);
		agnmt.NavigateToNewEditAssignmentPage("edit");
		agnmt.removeDocs(Input.rmu1userName);
		String docCount_afterRemovingDocs = "0";
		bc.impersonateRMUtoReviewer();
		bc.stepInfo("Impersonated as reviewer");
		agnmt.VerifyTodoCountInReviewerPg(noncascadeAssgnName, docCount_afterRemovingDocs);
		agnmt.verifyDrawPoolToggledisplay(noncascadeAssgnName, "enabled");
		lp.logout();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		agnmt.deleteAssignmentFromSingleAssgnGrp(NoncascadeAsgnGrpName, noncascadeAssgnName);
		agnmt.DeleteAssgnGroup(NoncascadeAsgnGrpName);
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @description RPMXCON-12669 Scenario 1 - Verify the display of 'Draw from
	 *              Pool' Action when 'Draw From Pool' option is disabled in
	 *              Assignment.
	 */
	@Test(description ="RPMXCON-54214",enabled = true, groups = { "regression" })
	public void VerifyDrawFromPooldisplay_ifToggledisabled() throws InterruptedException, ParseException, IOException {
		bc.stepInfo("RPMXCON-12669 Scenario 1 - Verify the display of 'Draw from Pool' Action when 'Draw From Pool' "
				+ "option is disabled in   Assignment.");
		bc.stepInfo("Test case Id: RPMXCON-54214");
		String cascadeSettings_No = "No";
		String NoncascadeAsgnGrpName = "nonCascadeAssgnGrp" + Utility.dynamicNameAppender();
		String noncascadeAssgnName = "noncascadeAssign" + Utility.dynamicNameAppender();

		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.createCascadeNonCascadeAssgnGroup_withoutSave(NoncascadeAsgnGrpName, cascadeSettings_No);
		bc.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		bc.stepInfo("Created A NonCascade Assignment group -" + NoncascadeAsgnGrpName);
		agnmt.selectAssignmentGroup(NoncascadeAsgnGrpName);

		agnmt.NavigateToNewEditAssignmentPage("new");
		agnmt.createAssignment_fromAssignUnassignPopup(noncascadeAssgnName, Input.codeFormName);
		String enabled1 = agnmt.getAssgnGrp_Create_DrawPooltoggle().Class();
		if (enabled1.equals("true")) {
			agnmt.getAssgnGrp_Create_DrawPooltoggle().waitAndClick(5);
		}
		bc.stepInfo("Draw pool option is disabled");
		driver.scrollPageToTop();
		bc.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		search.basicContentSearch(searchText);
		search.bulkAssignExisting(noncascadeAssgnName);
		agnmt.selectAssignmentGroup(NoncascadeAsgnGrpName);
		driver.scrollingToElementofAPage(agnmt.getSelectAssignment(noncascadeAssgnName));
		agnmt.getSelectAssignment(noncascadeAssgnName).waitAndClick(3);
		String docCount = agnmt.getDocCountInAssgnPg(noncascadeAssgnName).getText();
		agnmt.NavigateToNewEditAssignmentPage("edit");
		agnmt.assignmentDistributingToReviewerManager();
		bc.impersonateRMUtoReviewer();
		agnmt.VerifyTodoCountInReviewerPg(noncascadeAssgnName, docCount);
		lp.logout();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		driver.waitForPageToBeReady();
		agnmt.selectAssignmentGroup(NoncascadeAsgnGrpName);
		driver.scrollingToElementofAPage(agnmt.getSelectAssignment(noncascadeAssgnName));
		agnmt.getSelectAssignment(noncascadeAssgnName).waitAndClick(3);
		agnmt.NavigateToNewEditAssignmentPage("edit");
		agnmt.removeDocs(Input.rmu1userName);
		String docCount_afterRemovingDocs = "0";
		bc.impersonateRMUtoReviewer();
		agnmt.VerifyTodoCountInReviewerPg(noncascadeAssgnName, docCount_afterRemovingDocs);
		agnmt.verifyDrawPoolToggledisplay(noncascadeAssgnName, "disabled");
		lp.logout();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		agnmt.deleteAssignmentFromSingleAssgnGrp(NoncascadeAsgnGrpName, noncascadeAssgnName);
		agnmt.DeleteAssgnGroup(NoncascadeAsgnGrpName);
		lp.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @description: To verify that RMU can unassign the Pending RU from Assignment
	 *               if he is assigned with documents which are in Incomplete
	 *               state.. RPMXCON-54767
	 */
	@Test(description ="RPMXCON-54767",enabled = true, groups = { "regression" })
	public void verifyUnassignedDocsAfterUnassignedUser() throws InterruptedException {
		bc.stepInfo(
				"To verify that RMU can unassign the Pending RU from Assignment if he is assigned with documents which are in Incomplete state.");
		bc.stepInfo("Test case Id: RPMXCON-54767");
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		agnmt.createAssignment(assignmentName, Input.codeFormName);
		bc.selectproject();
		int count = search.basicContentSearch(searchText);
		search.bulkAssignExisting(assignmentName);
		bc.stepInfo("Created a assignment " + assignmentName);
		bc.selectproject();
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		agnmt.unassignTheAddedReviewer(count);
		agnmt.deleteSelectedAgnmt(assignmentName);
		lp.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @description: To verify that if documents are marked as Incomplete for
	 *               Completed Document then that documents can be Reassigned to
	 *               Reviewer. RPMXCON-53704
	 */
	@Test(description ="RPMXCON-53704",enabled = true, groups = { "regression" })
	public void verifyRedistributionDocs() throws InterruptedException {
		assertion = new SoftAssert();
		bc.stepInfo(
				"To verify that if documents are marked as Incomplete for Completed Document then that documents can be Reassigned to Reviewer.");
		bc.stepInfo("Test case Id: RPMXCON-53704");
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		agnmt.createAssignment(assignmentName, Input.codeFormName);
		bc.selectproject();
		int count = search.basicContentSearch(searchText);
		search.bulkAssignExisting(assignmentName);
		bc.stepInfo("Created a assignment " + assignmentName);
		bc.selectproject();
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		agnmt.verifyRedistributedDocsToAnotherUser(count);
		lp.logout();
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		bc.waitForElement(agnmt.getAssignmentsInreviewerPg());
		agnmt.getAssignmentsInreviewerPg().waitAndClick(5);
		bc.waitForElement(agnmt.getAssignmentsTodoCountInreviewerPg(assignmentName));
		try {
			bc.waitTillTextToPresent(agnmt.getAssignmentsTodoCountInreviewerPg(assignmentName), Integer.toString(count));
			assertion.assertEquals(agnmt.getAssignmentsTodoCountInreviewerPg(assignmentName).getText(),
					Integer.toString(count));
			assertion.assertAll();
			bc.passedStep("The To do count are displayed " + count + " as expected");
		} catch (Exception e) {
			bc.stepInfo(e.getMessage());
			bc.failedStep("The To do count are not displayed as expected");
		}
		lp.logout();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		agnmt.deleteAssgnmntUsingPagination(assignmentName);
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @description To verify the functionality of the Redistribute Documents for
	 *              the Pending Reviewer which has no documents assigned
	 */
	@Test(description ="RPMXCON-54765",enabled = true, groups = { "regression" })
	public void VerifyRedistributefunctionality() throws InterruptedException, ParseException, IOException {
		bc.stepInfo("To verify the functionality of the Redistribute Documents for the Pending Reviewer "
				+ "which has no documents assigned");
		bc.stepInfo("Test case Id: RPMXCON-54765");
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		agnmt.createAssignment(assignmentName, Input.codeFormName);
		bc.stepInfo("Created assignment with name" + assignmentName);
		bc.selectproject();
		search.basicContentSearch(Input.searchString1);
		search.bulkAssignExisting(assignmentName);
		bc.stepInfo("Created a assignment " + assignmentName);
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		String Distributeduser = agnmt.addMultipleReviewersAndDistributeToOnereviewer();
		System.out.println(Distributeduser);
		agnmt.VerifyTodocountinManageRevTab_RedistributeDocs();
		agnmt.deleteSelectedAgnmt(assignmentName);
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @description To verify that once the documents are reassigned to Pending
	 *              Reviewers then document counts are correctly displayed on Manage
	 *              Reviewer Tab and Manage Assignment Page.
	 */
	@Test(description ="RPMXCON-54766",enabled = true, groups = { "regression" })
	public void verifyRedistributeDocsCount() throws InterruptedException, ParseException, IOException {
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		bc.stepInfo(
				"To verify that once the documents are reassigned to Pending Reviewers then document counts are correctly displayed "
						+ "on Manage Reviewer Tab and Manage Assignment Page.");
		bc.stepInfo("Test case Id: RPMXCON-54766");
		agnmt.createAssignment(assignmentName, Input.codeFormName);
		bc.stepInfo("Created assignment with name" + assignmentName);
		search.basicContentSearch(Input.searchString1);
		search.bulkAssignExisting(assignmentName);
		bc.stepInfo("Created a assignment " + assignmentName);
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		String Distributeduser = agnmt.addMultipleReviewersAndDistributeToOnereviewer();
		System.out.println(Distributeduser);
		agnmt.VerifyRedistributeDocsInToDoCount_ReviewerPg(Distributeduser, assignmentName);
		lp.logout();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		agnmt.deleteAssgnmntUsingPagination(assignmentName);
		lp.logout();
	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		lp = new LoginPage(driver);
		agnmt = new AssignmentsPage(driver);
		bc = new BaseClass(driver);
		search = new SessionSearch(driver);
		sw = new StringWriter();
		pw = new PrintWriter(sw);
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		bc = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
		}
		try {			
			lp.quitBrowser();
		} catch (Exception e) {
			lp.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)
	public void close() {	
	UtilityLog.info("Executed Assignment Regression2 class.");		
	}


}
