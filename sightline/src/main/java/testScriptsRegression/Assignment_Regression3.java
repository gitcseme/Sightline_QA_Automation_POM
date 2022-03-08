package testScriptsRegression;

import static org.testng.Assert.assertFalse;
import pageFactory.DocViewPage;
import pageFactory.KeywordPage;

import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
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
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Assignment_Regression3 {
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Input ip;
	AssignmentsPage agnmt;
	SessionSearch search;
	KeywordPage keyword;
	SoftAssert softAssertion;
	DocExplorerPage DocExpPage;
	String assignmentName1 = "AR3assignment" + Utility.dynamicNameAppender();
	String ActualCount = null;
	String ingestionDataName="C113_QA_EmailConcatenatedData_SS_20211129145953453*";
	String ingestionMetaData="IngestionName";

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();
		driver = new Driver();
		baseClass = new BaseClass(driver);
		softAssertion = new SoftAssert();
	}

	@Test(description ="RPMXCON-54431",enabled = true, groups = { "regression" }, priority = 1)
	public void verifyDocCounts() throws Exception {
		baseClass = new BaseClass(driver);
		softAssertion = new SoftAssert();
		baseClass.stepInfo("Test case Id: RPMXCON-54431");
		baseClass.stepInfo("Verify 'Total Docs in Assignment' count displays correctly for existing "
				+ "assignment from Manage Assignment page");
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		String assignmentName = "AR3assignment" + Utility.dynamicNameAppender();
		SessionSearch sessionsearch = new SessionSearch(driver);
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		agnmt.createAssignment(assignmentName, Input.codeFormName);
		baseClass.stepInfo("Created a assignment " + assignmentName);
		sessionsearch.basicContentSearch(Input.testData1);
		String Actual = sessionsearch.verifyPureHitsCount();
		baseClass.stepInfo("Search with text input --Crammer- completed");
		sessionsearch.bulkAssignExisting(assignmentName);
		baseClass.stepInfo("Documents assigned to the existing assignment  " + assignmentName + " successfully");
		try {
			String Expected = agnmt.verifydocsCountInAssgnPage(assignmentName);
			softAssertion.assertEquals(Actual, Expected);
			softAssertion.assertAll();
			baseClass.passedStep("Correct document count is  displayed for the assignment on Manage Assignment Page");
			baseClass.stepInfo("Assigned Actual  Docs Count is  " + Actual);
			baseClass.stepInfo("Assigned Docs Count Displayed in Manage Assignemnts page is " + Expected);
		} catch (Exception e) {
			e.printStackTrace();
			baseClass.failedStep("Docs Count in manage assignment page is not  displayed as expected.");
		}
		loginPage.logout();
	}

	/**
	 * Author :Jayanthi G Test Case Id:RPMXCON-54432 @Description-Verify 'Total Docs
	 * in Assignment' count displays correctly on Edit Assignment-Distribute
	 * Documents tab
	 */

	@Test(description ="RPMXCON-54432",enabled = true, groups = { "regression" }, priority = 2)
	public void VerifyDocsCount_DistributeTab() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		String assignmentName2 = "AR3assignment" + Utility.dynamicNameAppender();
		baseClass.stepInfo("Test case Id:54432");
		baseClass.stepInfo("Verify 'Total Docs in Assignment' count displays correctly on Edit "
				+ "Assignment-Distribute Documents tab");
		SessionSearch sessionsearch = new SessionSearch(driver);
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		sessionsearch.basicContentSearch(Input.testData1);
		ActualCount = sessionsearch.verifyPureHitsCount();
		sessionsearch.bulkAssign();
		agnmt.FinalizeAssignmentAfterBulkAssign();
		agnmt.createAssignment_fromAssignUnassignPopup(assignmentName2, Input.codeFormName);
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		baseClass.stepInfo("Created a assignment " + assignmentName2);
		agnmt.editAssignmentUsingPaginationConcept(assignmentName2);
		driver.waitForPageToBeReady();
		baseClass.stepInfo(assignmentName2 + " assignment opened in edit mode");
		driver.scrollingToElementofAPage(agnmt.getDistributeTab());
		agnmt.getDistributeTab().Click();
		String actualDocs = agnmt.getEditAggn_Distribute_Totaldoc().getText();
		System.out.println(actualDocs);
		try {
			baseClass.waitTillTextToPresent(agnmt.getEditAggn_Distribute_Totaldoc(), ActualCount);
			String Expected = agnmt.getEditAggn_Distribute_Totaldoc().getText();
			softAssertion.assertEquals(ActualCount, Expected);
			softAssertion.assertAll();
			baseClass.passedStep(
					"Correct document count is  displayed for the assignment on Edit Assignment Page-Documents Distribute Tab");
			baseClass.stepInfo("Assigned Actual  Docs Count is  " + ActualCount);
			baseClass.stepInfo(
					"Assigned Docs Count Displayed in Edit Assignment Documents Distribute Tab  is " + Expected);
		} catch (Exception e) {
			e.printStackTrace();
			baseClass.failedStep("Docs Count in documents distribute tab is not displayed as expected.");
		}
		loginPage.logout();

	}

	/**
	 * Author :Jayanthi G Test Case Id:RPMXCON-54433 @Description-Verify if
	 * distribute all documents to Reviewer in assignment, then 'DISTRIBUTED to
	 * User' count displays correct count on Manage Reviewers tab
	 */
	@Test(description ="RPMXCON-54433",enabled = true, groups = { "regression" }, priority = 3)

	public void VerifyDocsCount_ReviewerTab() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Test case Id:RPMXCON-54433");
		baseClass.stepInfo(
				"Verify if distribute all documents to Reviewer in assignment, then 'DISTRIBUTED to User' count "
						+ "displays correct count on Manage Reviewers tab");
		SessionSearch sessionsearch = new SessionSearch(driver);
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		sessionsearch.basicContentSearch(Input.testData1);
		ActualCount = sessionsearch.verifyPureHitsCount();
		sessionsearch.bulkAssign();
		agnmt.FinalizeAssignmentAfterBulkAssign();
		agnmt.createAssignment_fromAssignUnassignPopup(assignmentName1, Input.codeFormName);
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		baseClass.stepInfo("Created a assignment " + assignmentName1);
		agnmt.editAssignmentUsingPaginationConcept(assignmentName1);
		driver.waitForPageToBeReady();
		baseClass.stepInfo(assignmentName1 + " assignment opened in edit mode");
		agnmt.addReviewerAndDistributeDocs();
		baseClass.waitForElement(agnmt.getAssignment_ManageReviewersTab());
		agnmt.getAssignment_ManageReviewersTab().waitAndClick(5);
		try {
			baseClass.waitTillTextToPresent(agnmt.getAssgn_ManageRev_DocCountsDistributedUser(), ActualCount);
			String Expected = agnmt.getAssgn_ManageRev_DocCountsDistributedUser().getText();
			softAssertion.assertEquals(ActualCount, Expected);
			softAssertion.assertAll();
			baseClass.passedStep(
					"Correct document count is  displayed for the assignment on Edit Assignment Page-Manage Reviewer Tab");
			baseClass.stepInfo("Assigned Actual  Docs Count is  " + ActualCount);
			baseClass.stepInfo(
					"Assigned Docs Count Displayed in Edit Assignment Documents Manage Reviewer Tab  is " + Expected);
		} catch (Exception e) {
			e.printStackTrace();
			baseClass.failedStep("Docs Count in documents Manage Reviewer tab is not displayed as expected.");
		}
		loginPage.logout();
	}

	/**
	 * Author :Jayanthi G Test Case Id:RPMXCON-54434 @Description-Verify document
	 * count should be displayed correctly on doc view when navigating to doc view
	 * in context of an assignment
	 */

	@Test(description ="RPMXCON-54434",enabled = true, groups = { "regression" }, priority = 4)

	public void VerifyDocsCount_DocView() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Test case Id:RPMXCON-54434");
		baseClass.stepInfo("Verify document count should be displayed correctly on doc view when navigating "
				+ "to doc view in context of an assignment");
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.viewSelectedAssgnUsingPagination(assignmentName1);
		agnmt.Checkclickedstatus(assignmentName1);
		agnmt.assgnViewInAllDocView();
		try {
			Thread.sleep(1000);
			driver.waitForPageToBeReady();
			baseClass.waitForElement(agnmt.getAssg_DocView_DocCOunt());
			String resultValue = agnmt.getAssg_DocView_DocCOunt().getText();
			ArrayList<String> result = new ArrayList<String>(Arrays.asList(resultValue.split(" ")));
			System.out.println(result.get(1));
			softAssertion.assertEquals(ActualCount, result.get(1));
			softAssertion.assertAll();
			baseClass.passedStep("Correct document count is  displayed for the assignment on Doc View Page");
			baseClass.stepInfo("Assigned Actual  Docs Count is  " + ActualCount);
			baseClass.stepInfo("Assigned Docs Count Displayed in  Doc View Page is " + resultValue);
		} catch (Exception e) {
			e.printStackTrace();
			baseClass.failedStep("Docs Count in documents Doc View is not displayed as expected.");
		}
		loginPage.logout();
	}

	/**
	 * Author :Jayanthi G Test Case Id:RPMXCON-54513
	 * 
	 * @Description-Verify that 'Show Default View Tab' enabled by default with
	 *                     cascading settings not enabled
	 */
	@Test(description ="RPMXCON-54513",enabled = true, groups = { "regression" }, priority = 5)

	public void VerifyToggleEnabled() throws Exception {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Test case Id:RPMXCON-54513");
		baseClass.stepInfo(
				"Verify that 'Show Default View Tab' enabled by default with cascading " + "settings not enabled");
		String assignmentGroup = "Assgrp" + Utility.dynamicNameAppender();
		String assignmentName = "AR3assignment" + Utility.dynamicNameAppender();
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.createCascadeNonCascadeAssgnGroup(assignmentGroup, "No");
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.bulkAssignWithNewAssgn(assignmentGroup);
		driver.waitForPageToBeReady();
		agnmt.checkForToggleEnable(agnmt.getDefaultViewToggle());
		baseClass.stepInfo("Created new assignment under the assignment group with cascading settings not enabled"
				+ " and also verified whether the 'Show Default View Tab' toggle is enabled by default");
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.editAssgnGrp(assignmentGroup, "No");
		agnmt.checkForToggleEnable(agnmt.getDefaultViewToggle());
		baseClass.stepInfo("'Show Default View Tab' toggle is enabled by default when new assignment group is created");
		agnmt.createAssignment_withoutSave(assignmentName, Input.codeFormName);
		agnmt.checkForToggleEnable(agnmt.getDefaultViewToggle());
		baseClass.stepInfo("'Show Default View Tab' toggle is enabled by default when new assignment  is created");
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.DeleteAssgnGroup(assignmentGroup);
		loginPage.logout();
	}

	/**
	 * @author Jayanthi
	 * @throws InterruptedException
	 * @throws AWTException
	 * @description: Delete keywords - Assignment should not display the deleted
	 *               keyword
	 */
	@Test(description ="RPMXCON-54756",enabled = true, groups = { "regression" }, priority = 6)
	public void validateKeywordsInAssignment() throws InterruptedException, AWTException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		baseClass.stepInfo("Delete keywords - Assignment should not display the deleted keyword");
		baseClass.stepInfo("Test case Id: RPMXCON-54756");
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		String assignmentName = "AR3assignment" + Utility.dynamicNameAppender();
		String keywordName = "keyword" + Utility.dynamicNameAppender();
		keyword = new KeywordPage(driver);
		this.driver.getWebDriver().get(Input.url + "Keywords/Keywords");
		keyword.addKeywordWithColor(keywordName, Input.KeyWordColour);
		baseClass.stepInfo("Key word added sucessfully.");
		List<String> KeywordsListInKEywordPage = new ArrayList<String>();
		driver.waitForPageToBeReady();
		KeywordsListInKEywordPage = baseClass.getAvailableListofElements(keyword.getKeywordsList());
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.createAssignment_withoutSave(assignmentName, Input.codeFormName);
		baseClass.stepInfo("Checking for the Keyword presence before deleting the keyword.");
		agnmt.verifyKeywordsBeforeAndAfterDelete(keywordName, KeywordsListInKEywordPage, true);
		agnmt.getKeywordPopUpCancelBtn().waitAndClick(5);
		driver.scrollPageToTop();
		baseClass.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		this.driver.getWebDriver().get(Input.url + "Keywords/Keywords");
		keyword.deleteKeyword(keywordName);
		baseClass.stepInfo("Key word deleted sucessfully.");
		driver.waitForPageToBeReady();
		List<String> KeywordsListInKEywordPageAfterdelete = new ArrayList<String>();
		KeywordsListInKEywordPageAfterdelete = baseClass.getAvailableListofElements(keyword.getKeywordsList());
		baseClass.stepInfo("Checking for the Keyword Abscence After deleting the keyword.");
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		agnmt.verifyKeywordsBeforeAndAfterDelete(keywordName, KeywordsListInKEywordPageAfterdelete, false);
		agnmt.getKeywordPopUpCancelBtn().waitAndClick(5);
		loginPage.logout();
	}

	/**
	 * @author Jayanthi
	 * @throws InterruptedException
	 * @description:Verifying the Default Live Sequence while creating a new
	 *                        assignment group
	 */
	@Test(description ="RPMXCON-54493",enabled = true, groups = { "regression" }, priority = 7)
	public void verifyLivesequence() throws InterruptedException, AWTException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		baseClass.stepInfo("Verifying the Default Live Sequence while creating a new assignment group");
		baseClass.stepInfo("Test case Id: RPMXCON-54493");
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		String assgnGrp = "AR3assignGrp" + Utility.dynamicNameAppender();
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.createCascadeNonCascadeAssgnGroup_withoutSave(assgnGrp, "No");
		agnmt.VerifyLiveSequence();
		driver.scrollPageToTop();
		baseClass.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		baseClass.stepInfo(assgnGrp + " is Created");
		baseClass.stepInfo(assgnGrp + " is opened in edit mode to check whether  What ever the default setting saved "
				+ "for live sequence will be reflected back or not.");
		agnmt.editAssgnGrp(assgnGrp, "No");
		agnmt.VerifyLiveSequence();
		baseClass.selectproject();
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.selectAssignmentGroup(assgnGrp);
		agnmt.DeleteAssgnGroup(assgnGrp);
		softAssertion.assertAll();
		loginPage.logout();
	}

	/**
	 * @author Jayanthi
	 * @throws InterruptedException
	 * @description:Verify default settings for toggles as well as Draw from Pool
	 *                     settings incl. Max Draw Count as 100 when creating
	 *                     assignment group
	 */
	@Test(description ="RPMXCON-60049",enabled = true, groups = { "regression" }, priority = 8)
	public void verifyToggle_AssgnGrp() throws InterruptedException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		baseClass.stepInfo("Verify default settings for toggles as well as Draw from Pool settings"
				+ " incl. Max Draw Count as 100 when creating assignment group");
		baseClass.stepInfo("Test case Id: RPMXCON-60049");
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		String assgnGrp = "AR3assignGrp" + Utility.dynamicNameAppender();
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.createCascadeNonCascadeAssgnGroup_withoutSave(assgnGrp, "No");
		agnmt.VerifyDrawPoolToggleEnabled();
		agnmt.verifyPresentationControlTogglesEnabledDisabled();
		driver.scrollPageToTop();
		baseClass.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		baseClass.stepInfo(assgnGrp + " Assignment Group  is Created");
		agnmt.editAssgnGrp(assgnGrp, "No");
		baseClass.stepInfo(assgnGrp + " Assignment Group  is Opened in edit mode.");
		agnmt.VerifyDrawPoolToggleEnabled();
		agnmt.verifyPresentationControlTogglesEnabledDisabled();
		driver.scrollPageToTop();
		agnmt.getBackToManageBtn().waitAndClick(5);
		baseClass.selectproject();
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.selectAssignmentGroup(assgnGrp);
		agnmt.DeleteAssgnGroup(assgnGrp);
		loginPage.logout();
	}

	/**
	 * @author Jayanthi
	 * @throws InterruptedException
	 * @description:Verify default settings for toggles, Draw from Pool settings
	 *                     incl. Max Draw Count as 100 when creating new assignment
	 *                     from manage assignment
	 */
	@Test(description ="RPMXCON-60048",enabled = true, groups = { "regression" }, priority = 9)
	public void verifyToggle_Assignments() throws InterruptedException, AWTException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		baseClass.stepInfo(
				"Verify default settings for toggles, Draw from Pool settings incl. Max Draw Count as 100 when creating "
						+ "new assignment from manage assignment");
		baseClass.stepInfo("Test case Id: RPMXCON-60048");
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		String assignmentName = "AR3assignment" + Utility.dynamicNameAppender();
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		agnmt.createAssignment_withoutSave(assignmentName, Input.codeFormName);
		agnmt.VerifyDrawPoolToggleEnabled();
		agnmt.verifyPresentationControlTogglesEnabledDisabled();
		driver.scrollPageToTop();
		baseClass.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		baseClass.stepInfo(assignmentName + " Assignment is Created");
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		baseClass.stepInfo(assignmentName + " Assignment is Opened in Edit mode");
		agnmt.VerifyDrawPoolToggleEnabled();
		agnmt.verifyPresentationControlTogglesEnabledDisabled();
		softAssertion.assertAll();
		loginPage.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description Verify default settings for toggles as well as Draw from Pool
	 *              settings incl. Max Draw Count as 100 when creating assignment
	 *              with Quick Batch
	 */
	@Test(description ="RPMXCON-60046",enabled = true, groups = { "regression" }, priority = 10)
	public void VerifyToggle_QuickBatch() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		String assignmentName = "AR3assignment" + Utility.dynamicNameAppender();
		baseClass.stepInfo("Test case Id: RPMXCON-60046");
		baseClass.stepInfo("Verify default settings for toggles as well as Draw from Pool settings incl. Max"
				+ " Draw Count as 100 when creating assignment with Quick Batch");
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		search = new SessionSearch(driver);
		// Creating quick batch assignment
		search.basicContentSearch(Input.searchString1);
		search.quickbatch();
		agnmt.createNewquickBatchWithoutReviewer(assignmentName, Input.codeFormName);
		baseClass.stepInfo("Created Quick batch Assignment --" + assignmentName);
		// opening the quick batch assignment in edit mode
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		agnmt.getParentAssignmentGroupName().Displayed();
		softAssertion.assertEquals(agnmt.getParentAssignmentGroupName().GetAttribute("Value"), "Root");
		softAssertion.assertEquals(agnmt.getAssignmentName().GetAttribute("Value"), assignmentName);
		agnmt.VerifyDrawPoolToggleEnabled();
		agnmt.verifyPresentationControlTogglesEnabledDisabled();
		driver.scrollPageToTop();
		baseClass.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		loginPage.logout();

	}

	/**
	 * @author Jayanthi.ganesan
	 * 
	 * @description:Verify default settings for toggles, Draw from Pool settings
	 *                     incl. Max Draw Count as 100 when creating new assignment
	 *                     with Bulk Assign
	 */

	@Test(description ="RPMXCON-60047",enabled= true,groups = { "regression" }, priority = 11)
	public void CreateAssgn_DocExp_VerifyToggle() throws InterruptedException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		try {
			baseClass.stepInfo("Test case Id:RPMXCON-60047");
			baseClass.stepInfo(
					"Verify default settings for toggles, Draw from Pool settings incl. Max Draw Count as 100 when "
							+ "creating new assignment with Bulk Assign");
			String assignmentName = "AR3Assignment" + Utility.dynamicNameAppender();
			DocListPage doclist = new DocListPage(driver);
			AssignmentsPage agnmt = new AssignmentsPage(driver);
			DocExpPage = new DocExplorerPage(driver);
			softAssertion = new SoftAssert();
			DocExpPage.documentsSelection(5);
			driver.scrollPageToTop();
			DocExpPage.docExp_BulkAssign();
			agnmt.FinalizeAssignmentAfterBulkAssign();
			agnmt.createAssignment_fromAssignUnassignPopup(assignmentName, Input.codeFormName);
			agnmt.VerifyDrawPoolToggleEnabled();
			agnmt.verifyPresentationControlTogglesEnabledDisabled();
			driver.scrollPageToTop();
			baseClass.waitForElement(agnmt.getAssignmentSaveButton());
			agnmt.getAssignmentSaveButton().waitAndClick(5);
			baseClass
					.passedStep("created a New Assignment from \"Assign/Unassign Documents\" pop up from DocExp page.");
			agnmt.editAssignmentUsingPaginationConcept(assignmentName);
			baseClass.stepInfo(assignmentName + " Assignment is Opened in Edit mode");
			agnmt.VerifyDrawPoolToggleEnabled();
			agnmt.verifyPresentationControlTogglesEnabledDisabled();
			softAssertion.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			baseClass.stepInfo(e.getMessage());
			baseClass.failedStep("Exception occured");
		}
		loginPage.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * 
	 * @description:
	 */

	@Test(description ="RPMXCON-54497",enabled = true,groups = { "regression" }, priority = 12)
	public void verifyDrawPoolToggle() throws InterruptedException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		baseClass.stepInfo("Test case Id:RPMXCON-54497");
		baseClass.stepInfo("Disable the \"Draw from Pool\" field settings in the Assignment");
		String assignmentName = "AR3Assignment" + Utility.dynamicNameAppender();
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		DocExpPage = new DocExplorerPage(driver);
		softAssertion = new SoftAssert();
		search = new SessionSearch(driver);
		search.basicContentSearch(Input.searchString2);
		search.bulkAssign();
		agnmt.assignmentCreation(assignmentName, Input.codeFormName);
		baseClass.waitForElement(agnmt.getAssgnGrp_Create_DrawPooltoggle());
		agnmt.getAssgnGrp_Create_DrawPooltoggle().Click();
		driver.scrollPageToTop();
		baseClass.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		softAssertion.assertEquals(agnmt.getAssgnGrp_Create_DrawPooltoggle().GetAttribute("class"), "false");
		baseClass.ValidateElement_Presence(agnmt.getEmailThreadsTogetherBtnDisabled(),
				"Disabled Email Threads Toggle Button ");
		baseClass.ValidateElement_Presence(agnmt.getKeepFamiliesBtnDisabled(), "Disabled KeepFamilies Toggle Button ");
		baseClass
				.passedStep("Sucessfully Verified the Disable the \"Draw from Pool\" field settings in the Assignment");
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		baseClass.stepInfo("Logged Out and Logged in again as same RMU User to "
				+ "check whether the updated setting are reflected or not");
		softAssertion.assertEquals(agnmt.getAssgnGrp_Create_DrawPooltoggle().GetAttribute("class"), "false");
		baseClass.ValidateElement_Presence(agnmt.getEmailThreadsTogetherBtnDisabled(),
				"Disabled Email Threads Toggle Button ");
		baseClass.ValidateElement_Presence(agnmt.getKeepFamiliesBtnDisabled(), "Disabled KeepFamilies Toggle Button ");
		softAssertion.assertAll();
		baseClass.passedStep(
				"Sucessfully Verified the Disable the \"Draw from Pool\" field settings in the Assignment is reflected or not by logging out and loggin as same user.");
		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that when Keep Families Together is disabled, the draw
	 *              will be limited to the configured draw size/limit
	 *              [RPMXCON-59178]
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-59178",enabled = true, groups = { "regression" }, priority = 13)
	public void verifyDrawLinkWhenFamiliesToggleDisabled() throws InterruptedException {
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		softAssertion = new SoftAssert();
		loginPage = new LoginPage(driver);
		agnmt = new AssignmentsPage(driver);
		search = new SessionSearch(driver);

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-59178 Assignments Sprint-10");
		baseClass.stepInfo(
				"Verify that when Keep Families Together is disabled, the draw will be limited to the configured draw size/limit");

		// assignment Creation
		int count = search.basicContentSearch(Input.TallySearch);
		search.bulkAssign();
		agnmt.assignmentCreation(assignmentName, Input.codeFormName);
		baseClass.stepInfo("Created Assignment name : " + assignmentName);

		// Disable Familes and email Thread Toggles Disable
		driver.scrollingToElementofAPage(agnmt.getKeepEmailThreadTogether_Text());
		String familyMem = agnmt.getKeepFamilyTogetther_Text().getText();
		agnmt.toggleEnableOrDisableOfAssignPage(false, true, agnmt.getAssgn_keepFamiliesTogetherToggle(), familyMem,
				false);

		String emailThread = agnmt.getKeepEmailThreadTogether_Text().getText();
		agnmt.toggleEnableOrDisableOfAssignPage(false, true, agnmt.getAssgn_keepEmailThreadTogetherToggle(),
				emailThread, true);

		// Distribute to RMU and REV
		agnmt.distributeHalfTheDocsToReviewer(count);
		agnmt.assignmentDistributingToReviewer();

		// verify Draw from pool line as RMU
		baseClass.selectproject();
		baseClass.waitForElement(agnmt.getAssignmentsInreviewerPg());
		agnmt.getAssignmentsInreviewerPg().waitAndClick(5);
		baseClass.ValidateElement_Absence(agnmt.getAssignmentsDrawPoolInreviewerPg(assignmentName),
				"Draw From pool Link is not Dispalyed For RMU");
		loginPage.logout();

		// verify Draw from pool line as REV
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.waitForElement(agnmt.getAssignmentsInreviewerPg());
		agnmt.getAssignmentsInreviewerPg().waitAndClick(5);
		baseClass.ValidateElement_Absence(agnmt.getAssignmentsDrawPoolInreviewerPg(assignmentName),
				"Draw From pool Link is not Dispalyed For REV");
		loginPage.logout();

		// Delete Assignment
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		agnmt.deleteAssgnmntUsingPagination(assignmentName);
		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that after editing assignment group with cascading,
	 *              changes should reflect in its respective assignment
	 *              [RPMXCON-59201]
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-59201",enabled = true, groups = { "regression" }, priority = 14)
	public void verifyAfterEditingAssignGroup() throws InterruptedException {
		String cascadeAsgnGrpName = "CascadeAssgnGrp" + Utility.dynamicNameAppender();
		String assignment = "Assignment" + Utility.dynamicNameAppender();
		String cascadeSettings_yes = "Yes";

		softAssertion = new SoftAssert();
		loginPage = new LoginPage(driver);
		agnmt = new AssignmentsPage(driver);
		search = new SessionSearch(driver);

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-59201 Assignments Sprint-10");
		baseClass.stepInfo(
				"Verify that after editing assignment group with cascading, changes should reflect in its respective assignment");

		//create Assignmnet group with Draw Toggle Disabled
		agnmt.navigateToAssignmentsPage();
		agnmt.createCascadeNonCascadeAssgnGroup_withoutSave(cascadeAsgnGrpName, cascadeSettings_yes);
		agnmt.toggleEnableOrDisableOfAssignPage(false, true, agnmt.getAssgnGrp_Create_DrawPooltoggle(),
				"Draw From Pool", true);

		//Create Assignment in Assignment Group 
		agnmt.selectAssignmentGroup(cascadeAsgnGrpName);
		agnmt.createAssignmentFromAssgnGroup(assignment, Input.codeFormName);

		//Enable Draw Toggle in Assign Group
		agnmt.editAssgnGrp(cascadeAsgnGrpName, "Yes");
		driver.waitForPageToBeReady();
		agnmt.toggleEnableOrDisableOfAssignPage(true, false, agnmt.getAssgnGrp_Create_DrawPooltoggle(),
				"Draw From Pool", true);

		//verify Draw Toggle is Enabled in Assignment
		agnmt.selectAssignmentToView(assignment);
		baseClass.waitForElement(agnmt.getAssignmentActionDropdown());
		agnmt.getAssignmentAction_EditAssignment().waitAndClick(3);
		agnmt.VerifyDrawPoolToggleEnabled();

		//Delete Assign group and assign
		agnmt.navigateToAssignmentsPage();
		agnmt.deleteAssignmentFromSingleAssgnGrp(cascadeAsgnGrpName, assignment);
		agnmt.DeleteAssgnGroup(cascadeAsgnGrpName);
		loginPage.logout();

	}
	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-59179",enabled = true, groups = { "regression" }, priority = 15)
	public void verifyDrawLinkWhenEmailThreadToggleEnabled() throws InterruptedException {
		String assignmentName = "AR2Assignment" + Utility.dynamicNameAppender();
		softAssertion = new SoftAssert();
		loginPage = new LoginPage(driver);
		agnmt = new AssignmentsPage(driver);
		search = new SessionSearch(driver);
		String SearchName1= "emailConcat"+Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-59179 ");
		baseClass.stepInfo("Verify that when Email Threads Together is enabled,"
				+ " the draw will keep the entire email thread together irrespective of the size of the batch");

		//Getting the ingested docs to assign to assignment
		int count = search.MetaDataSearchInAdvancedSearch(ingestionMetaData,ingestionDataName);
		search.saveSearchAtAnyRootGroup(SearchName1, Input.shareSearchDefaultSG);
		baseClass.stepInfo("Created a SavedSearch " + SearchName1);
		loginPage.logout();

		// assignment Creation
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		SavedSearch savedSearch= new SavedSearch(driver);
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		savedSearch.getSavedSearchGroupName(Input.securityGroup).waitAndClick(10);
		savedSearch.savedSearch_SearchandSelect(SearchName1, "Yes");
		savedSearch.getSavedSearchToBulkAssign().ScrollTo();
		savedSearch.getSavedSearchToBulkAssign().Click();
		agnmt.FinalizeAssignmentAfterBulkAssign();
		agnmt.createAssignment_fromAssignUnassignPopup(assignmentName, Input.codeFormName);
		
		baseClass.stepInfo("Created Assignment name : " + assignmentName);
		agnmt.dragAndDropLiveSequence(" Email Threads");
    	agnmt.dragAndDropLiveSequence(" Near Duplicate Docs");
    	agnmt.dragAndDropLiveSequence(" Family Members");
    	driver.waitForPageToBeReady();
    	//Draw pool toggle enable and setting draw limit as 20.
		agnmt.toggleEnableOrDisableOfAssignPage(true, false, agnmt.getAssgnGrp_Create_DrawPooltoggle(),
				"Draw From Pool", false);
    	// email Thread Toggles Enable
		agnmt.getKeepEmailThreadTogether_Text().ScrollTo();
		String emailThread = agnmt.getKeepEmailThreadTogether_Text().getText();
		agnmt.toggleEnableOrDisableOfAssignPage(true, false, agnmt.getAssgn_keepEmailThreadTogetherToggle(),
				emailThread, false);
		String familyMem = agnmt.getKeepFamilyTogetther_Text().getText();
		agnmt.toggleEnableOrDisableOfAssignPage(false, true, agnmt.getAssgn_keepFamiliesTogetherToggle(),
				familyMem, false);
		driver.scrollPageToTop();
		baseClass.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		// Distribute 33 Docs to  REV
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		agnmt.distributeTheGivenDocCountToReviewer("33");
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in as reviewer user");
		// navigating from Dashboard to DocView
		DocViewPage docViewPage=new DocViewPage(driver);
		docViewPage.selectAssignmentfromDashborad(assignmentName);
		baseClass.stepInfo("Doc is viewed in the docView Successfully");
		
		// Completing the 23 documents
		driver.waitForPageToBeReady();
		docViewPage.CompleteTheDocumentInMiniDocList(22);
		docViewPage.completeDocsBasedOnDocID(Input.docIDs+"3740");
		docViewPage.getDashboardButton().waitAndClick(5);
		baseClass.waitForElement(agnmt.getAssignmentsInreviewerPg());
		agnmt.getAssignmentsInreviewerPg().waitAndClick(5);
		baseClass.waitForElement(agnmt.getAssignmentsDrawPoolInreviewerPg(assignmentName));
		if (agnmt.getAssignmentsDrawPoolInreviewerPg(assignmentName).isElementPresent() == true) {
			baseClass.passedStep("Draw pool link is displayed");
			baseClass.waitTillElemetToBeClickable(agnmt.getAssignmentsDrawPoolInreviewerPg(assignmentName));
			driver.scrollingToElementofAPage(agnmt.getAssignmentsDrawPoolInreviewerPg(assignmentName));
			agnmt.getAssignmentsDrawPoolInreviewerPg(assignmentName).waitAndClick(5);
			driver.Navigate().refresh();
			baseClass.waitForElement(agnmt.getAssignmentsTodoCountInreviewerPg(assignmentName));
			String docCountInTODO=agnmt.getAssignmentsTodoCountInreviewerPg(assignmentName).getText();
			if(Integer.parseInt(docCountInTODO)==35) {
				baseClass.stepInfo("Doc Count displayed in ToDo Column after completing "
						+ "23 docs and clicking Draw Pool link is "+docCountInTODO);
				baseClass.passedStep("Sucessfully Verified that when Email Threads Together is enabled, the draw will keep"
						+ " the entire email thread together irrespective of the size of the batch");
				
			}
			else {
				baseClass.failedStep("Doc Count displayed in ToDo Column after completing "
							+ "23 docs and clicking Draw Pool link is "+docCountInTODO+" Which is not expected.");
				
			}
		} else {
			baseClass.failedStep("Draw pool link is not displayed");
		}
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		agnmt.deleteAssgnmntUsingPagination(assignmentName);
		loginPage.logout();
	}
	
	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-59177",enabled = true, groups = { "regression" }, priority = 16)
	public void verifyDrawLinkWhenFamilyToggleEnabled() throws InterruptedException {
		String assignmentName = "FamilyAssignment" + Utility.dynamicNameAppender();
		softAssertion = new SoftAssert();
		loginPage = new LoginPage(driver);
		agnmt = new AssignmentsPage(driver);
		search = new SessionSearch(driver);
		String SearchName1= "emailConcat"+Utility.dynamicNameAppender();
		//Getting the ingested docs to assign to assignment
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-59177");
		baseClass.stepInfo("Verify that when Keep Families Together is enabled, the draw will keep the "
				+ "entire family together irrespective of the size of the batch");

		// assignment Creation
		int count = search.MetaDataSearchInAdvancedSearch(ingestionMetaData,ingestionDataName);
		search.saveSearchAtAnyRootGroup(SearchName1, Input.shareSearchDefaultSG);
		baseClass.stepInfo("Created a SavedSearch " + SearchName1);
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		SavedSearch savedSearch= new SavedSearch(driver);
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		savedSearch.getSavedSearchGroupName(Input.securityGroup).waitAndClick(10);
		savedSearch.savedSearch_SearchandSelect(SearchName1, "Yes");
		savedSearch.getSavedSearchToBulkAssign().ScrollTo();
		savedSearch.getSavedSearchToBulkAssign().Click();
		agnmt.FinalizeAssignmentAfterBulkAssign();
		agnmt.createAssignment_fromAssignUnassignPopup(assignmentName, Input.codeFormName);
		
		agnmt.dragAndDropLiveSequence(" Email Threads");
    	agnmt.dragAndDropLiveSequence(" Near Duplicate Docs");
  
    	agnmt.dragAndDropLiveSequence(" Family Members");
    	driver.waitForPageToBeReady();
    	// Draw pool Toggle Enable and making draw limit as 20
		agnmt.toggleEnableOrDisableOfAssignPage(true, false, agnmt.getAssgnGrp_Create_DrawPooltoggle(),
				"Draw From Pool", false);
    	// email Thread Toggles Enable
		agnmt.getKeepEmailThreadTogether_Text().ScrollTo();
		String emailThread = agnmt.getKeepEmailThreadTogether_Text().getText();
		// Keep family Toggles Enable
		agnmt.toggleEnableOrDisableOfAssignPage(true, false, agnmt.getAssgn_keepEmailThreadTogetherToggle(),
				emailThread, false);
		String familyMem = agnmt.getKeepFamilyTogetther_Text().getText();
		agnmt.toggleEnableOrDisableOfAssignPage(true, false, agnmt.getAssgn_keepFamiliesTogetherToggle(),
				familyMem, false);
		driver.scrollPageToTop();
		baseClass.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		// Distribute 33 Docs to  REV
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		agnmt.distributeTheGivenDocCountToReviewer("31");

		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in as reviewer user");
		// navigating from Dashboard to DocView
		DocViewPage docViewPage=new DocViewPage(driver);
		docViewPage.selectAssignmentfromDashborad(assignmentName);
		baseClass.stepInfo("Doc is viewed in the docView Successfully");
		
		// Completing the 24 documents
		driver.waitForPageToBeReady();
		List<String>DocID1 = docViewPage.CompleteTheDocumentInMiniDocList(24);
		docViewPage.getDashboardButton().waitAndClick(5);
		baseClass.waitForElement(agnmt.getAssignmentsInreviewerPg());
		agnmt.getAssignmentsInreviewerPg().waitAndClick(5);
		baseClass.waitForElement(agnmt.getAssignmentsDrawPoolInreviewerPg(assignmentName));
		if (agnmt.getAssignmentsDrawPoolInreviewerPg(assignmentName).isElementPresent() == true) {
			baseClass.passedStep("Draw pool link is displayed");
			baseClass.waitTillElemetToBeClickable(agnmt.getAssignmentsDrawPoolInreviewerPg(assignmentName));
			driver.scrollingToElementofAPage(agnmt.getAssignmentsDrawPoolInreviewerPg(assignmentName));
			agnmt.getAssignmentsDrawPoolInreviewerPg(assignmentName).waitAndClick(5);
			driver.Navigate().refresh();
			baseClass.waitForElement(agnmt.getAssignmentsTodoCountInreviewerPg(assignmentName));
			String docCountInTODO=agnmt.getAssignmentsTodoCountInreviewerPg(assignmentName).getText();
			if(Integer.parseInt(docCountInTODO)==35) {
				baseClass.stepInfo("Doc Count displayed in ToDo Column after completing "+DocID1.size()+ " docs and clicking Draw Pool link is "+docCountInTODO);
				baseClass.passedStep("Sucessfully Verified that when Email Threads Together is enabled, the draw will keep"
						+ " the entire email thread together irrespective of the size of the batch");
				
			}
			else {
				baseClass.failedStep("Doc Count displayed in ToDo Column after completing "
							+ "23 docs and clicking Draw Pool link is "+docCountInTODO+" Which is not expected.");
				
			}
		} else {
			baseClass.failedStep("Draw pool link is not displayed");
		}
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		agnmt.deleteAssgnmntUsingPagination(assignmentName);
		loginPage.logout();
	}
	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-59197",enabled = true, groups = { "regression" }, priority = 17)
	public void verifyDrawaLimit_BothToggleOFF() throws InterruptedException {
		String assignmentName = "FamilyAssignment" + Utility.dynamicNameAppender();
		softAssertion = new SoftAssert();
		loginPage = new LoginPage(driver);
		agnmt = new AssignmentsPage(driver);
		search = new SessionSearch(driver);
		String SearchName1= "emailConcat"+Utility.dynamicNameAppender();
		//Getting the ingested docs to assign to assignment
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-59197");
		baseClass.stepInfo("Verify draw from pool after editing the assignment with both the toggles OFF");

	    search.MetaDataSearchInAdvancedSearch(ingestionMetaData,ingestionDataName);
		search.saveSearchAtAnyRootGroup(SearchName1, Input.shareSearchDefaultSG);
		baseClass.stepInfo("Created a SavedSearch " + SearchName1);
		loginPage.logout();
		// assignment Creation
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		SavedSearch savedSearch= new SavedSearch(driver);
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		savedSearch.getSavedSearchGroupName(Input.securityGroup).waitAndClick(10);
		savedSearch.savedSearch_SearchandSelect(SearchName1, "Yes");
		savedSearch.getSavedSearchToBulkAssign().ScrollTo();
		savedSearch.getSavedSearchToBulkAssign().Click();
		agnmt.FinalizeAssignmentAfterBulkAssign();
		agnmt.createAssignment_fromAssignUnassignPopup(assignmentName, Input.codeFormName);
    	driver.waitForPageToBeReady();
    	// Draw pool Toggle Disable
		agnmt.toggleEnableOrDisableOfAssignPage(false, true, agnmt.getAssgnGrp_Create_DrawPooltoggle(),
				"Draw From Pool", false);
		driver.scrollPageToTop();
		baseClass.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		
		//Edit Assignment
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		// Draw pool Toggle Enable and making draw limit as 20
		agnmt.toggleEnableOrDisableOfAssignPage(true, false, agnmt.getAssgnGrp_Create_DrawPooltoggle(),
				"Draw From Pool", false);
		agnmt.getAssgnGrp_Create_DrawPoolCount().SendKeys("20");
    	// email Thread Toggles  disable
		agnmt.getKeepEmailThreadTogether_Text().ScrollTo();
		String emailThread = agnmt.getKeepEmailThreadTogether_Text().getText();
		// Keep family Toggles disable
		agnmt.toggleEnableOrDisableOfAssignPage(false, true, agnmt.getAssgn_keepEmailThreadTogetherToggle(),
				emailThread, false);
		String familyMem = agnmt.getKeepFamilyTogetther_Text().getText();
		agnmt.toggleEnableOrDisableOfAssignPage(false, true, agnmt.getAssgn_keepFamiliesTogetherToggle(),
				familyMem, false);
		driver.scrollPageToTop();
		//distributing docs 
		agnmt.distributeTheGivenDocCountToReviewer("2");
		driver.scrollPageToTop();
		baseClass.waitForElement(agnmt.getAssignmentSaveButton());
		agnmt.getAssignmentSaveButton().waitAndClick(5);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in as reviewer user");
		// navigating from Dashboard to DocView
		DocViewPage docViewPage=new DocViewPage(driver);
		docViewPage.selectAssignmentfromDashborad(assignmentName);
		baseClass.stepInfo("Doc is viewed in the docView Successfully");
		
		// Completing  2 documents
		driver.waitForPageToBeReady();
		List<String>DocID1 = docViewPage.CompleteTheDocumentInMiniDocList(2);
		docViewPage.getDashboardButton().waitAndClick(5);
		baseClass.waitForElement(agnmt.getAssignmentsInreviewerPg());
		agnmt.getAssignmentsInreviewerPg().waitAndClick(5);
		baseClass.waitForElement(agnmt.getAssignmentsDrawPoolInreviewerPg(assignmentName));
		//Validation as per test step-7.Verification of draw limit.
		if (agnmt.getAssignmentsDrawPoolInreviewerPg(assignmentName).isElementPresent() == true) {
			baseClass.passedStep("Draw pool link is displayed");
			baseClass.waitTillElemetToBeClickable(agnmt.getAssignmentsDrawPoolInreviewerPg(assignmentName));
			driver.scrollingToElementofAPage(agnmt.getAssignmentsDrawPoolInreviewerPg(assignmentName));
			agnmt.getAssignmentsDrawPoolInreviewerPg(assignmentName).waitAndClick(5);
			driver.Navigate().refresh();
			baseClass.waitForElement(agnmt.getAssignmentsTodoCountInreviewerPg(assignmentName));
			String docCountInTODO=agnmt.getAssignmentsTodoCountInreviewerPg(assignmentName).getText();
			if(Integer.parseInt(docCountInTODO)==20) {
				baseClass.stepInfo("Doc Count displayed in ToDo Column after completing "+DocID1.size()+ " docs and after clicking Draw Pool link is "+docCountInTODO);
				baseClass.passedStep("Sucessfully Verified  draw from pool after editing the assignment with both the toggles OFF ");
				
			}
			else {
				baseClass.failedStep("Doc Count displayed in ToDo Column after completing "
							+ "2 docs and clicking Draw Pool link is "+docCountInTODO+" Which is not expected.");
				
			}
		} else {
			baseClass.failedStep("Draw pool link is not displayed");
		}
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		agnmt.deleteAssgnmntUsingPagination(assignmentName);
		loginPage.logout();
	}
	
	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		baseClass = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
			loginPage.logoutWithoutAssert();
		}
		try {
//			loginPage.logout();
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
			LoginPage.clearBrowserCache();
		}
	}

	@AfterClass(alwaysRun = true)
	public void close() throws ParseException, InterruptedException, IOException {
		System.out.println("******TEST CASES FOR ASSIGNMENTS-3 EXECUTED******");
		// Input in = new Input();
		// in.loadEnvConfig();
//		driver = new Driver();
//		loginPage = new LoginPage(driver);
//		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		try {
//		AssignmentsPage agnmt = new AssignmentsPage(driver);
//		agnmt.deleteAllAssignments("AR3");
//		loginPage.logout();
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
			LoginPage.clearBrowserCache();

		}
	}
}
