package testScriptsRegressionSprint17;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.Dashboard;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Dashboard_Regression {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	DocViewRedactions docViewRedact;
	Input ip;

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		Input in = new Input();
		in.loadEnvConfig();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());

		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		Reporter.log("Logged in as User: " + Input.rmu1password);
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
		System.out.println("******TEST CASES FOR DOCVIEW EXECUTED******");

	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-54194
	 * @Description:To verify that RMU is able to view details in Reviewer Progress
	 *                 widget by selecting criteria as "Top 6 Reviewers with Most To
	 *                 Do Docs" and single Assignments
	 **/

	@Test(description = "RPMXCON-54194", enabled = true, groups = { "regression" })
	public void verifyReviewerProgress_MostToDoDocs() throws Exception {

		baseClass.stepInfo("Test case Id:RPMXCON-54194 Dashboard Component Sprint 17");
		baseClass.stepInfo(
				"To verify that RMU is able to view details in Reviewer Progress widget by selecting criteria as Top 6 Reviewers with Most To Do Docs and single Assignments");
		UtilityLog.info(Input.prodPath);
		SessionSearch sessionsearch = new SessionSearch(driver);
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		Dashboard dashBoard = new Dashboard(driver);

		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.verifyPureHitsCount();
		sessionsearch.bulkAssign();
		agnmt.FinalizeAssignmentAfterBulkAssign();
		String assignmentName = "ARassignment" + Utility.dynamicNameAppender();
		agnmt.createAssignment_fromAssignUnassignPopup(assignmentName, Input.codeFormName);
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		baseClass.stepInfo("Created a assignment " + assignmentName);
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		driver.waitForPageToBeReady();
		baseClass.stepInfo(assignmentName + " assignment opened in edit mode");
		agnmt.assignmentDistributingToReviewerManager();

		docViewPage.selectAssignmentfromDashborad(assignmentName);
		baseClass.stepInfo("Doc is viewed in the docView Successfully");
		driver.waitForPageToBeReady();
		docViewPage.CompleteTheDocumentInMiniDocList(2);
		dashBoard.AddNewWidgetToDashboard(Input.reviewerProgress);
		driver.scrollPageToTop();
		dashBoard.customizeReviewerProgressWidget(Input.ToDoDocs, Input.rmu1userName, assignmentName);
		if (dashBoard.mostToDoDocs_InsideWidget().isDisplayed()) {
			baseClass.passedStep("Widget displayed as per selected criteria");
		}

	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-54196
	 * @Description:To verify that RMU is able to view details in Reviewer Progress
	 *                 widget by selecting criteria as "Top 6 Reviewers with Most
	 *                 Complete Docs" and single Assignments
	 **/

	@Test(description = "RPMXCON-54196", enabled = true, groups = { "regression" })
	public void verifyReviewerProgress_MostCompletedDocs() throws Exception {

		baseClass.stepInfo("Test case Id:RPMXCON-54196 Dashboard Component Sprint 17");
		baseClass.stepInfo(
				"To verify that RMU is able to view details in Reviewer Progress widget by selecting criteria as Top 6 Reviewers with Most Complete Docs and single Assignments");
		UtilityLog.info(Input.prodPath);
		SessionSearch sessionsearch = new SessionSearch(driver);
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		Dashboard dashBoard = new Dashboard(driver);

		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.verifyPureHitsCount();
		sessionsearch.bulkAssign();
		agnmt.FinalizeAssignmentAfterBulkAssign();
		String assignmentName = "ARassignment" + Utility.dynamicNameAppender();
		agnmt.createAssignment_fromAssignUnassignPopup(assignmentName, Input.codeFormName);
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		baseClass.stepInfo("Created a assignment " + assignmentName);
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		driver.waitForPageToBeReady();
		baseClass.stepInfo(assignmentName + " assignment opened in edit mode");
		agnmt.assignmentDistributingToReviewerManager();

		docViewPage.selectAssignmentfromDashborad(assignmentName);
		baseClass.stepInfo("Doc is viewed in the docView Successfully");
		driver.waitForPageToBeReady();
		docViewPage.CompleteTheDocumentInMiniDocList(2);
		dashBoard.AddNewWidgetToDashboard(Input.reviewerProgress);
		driver.scrollPageToTop();
		dashBoard.customizeReviewerProgressWidget(Input.Complete, Input.rmu1userName, assignmentName);
		if (dashBoard.mostCompletedDocs_InsideWidget().isDisplayed()) {
			baseClass.passedStep("Widget displayed as per selected criteria");
		}
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-54207
	 * @Description:To verify that RMU can view the details on Tag Count Widget by
	 *                 selecting favourite Tags.
	 **/
	@Test(description = "RPMXCON-54207", enabled = true, groups = { "regression" })
	public void verifyTaggingWidget() throws Exception {

		baseClass.stepInfo("Test case Id:RPMXCON-54207 Dashboard Component Sprint 17");
		baseClass.stepInfo("To verify that RMU can view the details on Tag Count Widget by selecting favourite Tags.");
		UtilityLog.info(Input.prodPath);
		Dashboard dashBoard = new Dashboard(driver);
		dashBoard.AddNewWidgetToDashboard(Input.Tagging);
		dashBoard.verifyPrivilegedAndResponsiveTags();
		dashBoard.verifySelectedTagsInTaggingWidget();

	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-54212
	 * @Description:To verify that details in End to End is displayed correctly
	 **/
	@Test(description = "RPMXCON-54212", enabled = true, groups = { "regression" })
	public void verifyEyeToEyeWidget() throws Exception {

		baseClass.stepInfo("Test case Id:RPMXCON-54212 Dashboard Component Sprint 17");
		baseClass.stepInfo("To verify that details in End to End is displayed correctly.");
		UtilityLog.info(Input.prodPath);
		Dashboard dashBoard = new Dashboard(driver);
		dashBoard.AddNewWidgetToDashboard(Input.EndtoEnd);
		System.out.println("Released Count :" + dashBoard.releasedCount_EndToEnd().getText());
		System.out.println("Not Reviewed Count :" + dashBoard.notReviewedCount_EndToEnd().getText());
		System.out.println("Reviewed Count :" + dashBoard.reviewedCount_EndToEnd().getText());
		System.out.println("Total produced Count :" + dashBoard.totalProducedCount().getText());

		String value = dashBoard.testCS().GetCssValue("left");
		System.out.println(value);


	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-54876
	 * @Description:Verify "My Assignment to Work" widget on RMU dashboard
	 **/
	@Test(description = "RPMXCON-54876", enabled = true, groups = { "regression" })
	public void verifyMyAssignmentWidget() throws Exception {

		baseClass.stepInfo("Test case Id:RPMXCON-54876 Dashboard Component Sprint 17");
		baseClass.stepInfo("Verify My Assignment to Work widget on RMU dashboard");

		UtilityLog.info(Input.prodPath);
		SessionSearch sessionsearch = new SessionSearch(driver);
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		Dashboard dashBoard = new Dashboard(driver);

		String assignmentName = "Assignment" + Utility.dynamicNameAppender();
		;

		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.verifyPureHitsCount();
		sessionsearch.bulkAssign();
		agnmt.FinalizeAssignmentAfterBulkAssign();
		agnmt.createAssignment_fromAssignUnassignPopup(assignmentName, Input.codeFormName);
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		baseClass.stepInfo("Created a assignment " + assignmentName);
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		driver.waitForPageToBeReady();
		baseClass.stepInfo(assignmentName + " assignment opened in edit mode");
		agnmt.assignmentDistributingToReviewerManager();

		dashBoard.navigateToDashboard();
		baseClass.waitForElement(dashBoard.getSelectAssignmentFromDashborad(assignmentName));
		dashBoard.verifyAssignmentData(assignmentName);
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-54577
	 * @Description:PAU and DAU users should be included for "Reviewer Progress"
	 *                  widget on RMU Dashboard(Impersonate form SystemAdmin/PAU/DAU
	 *                  to RMU)
	 **/
	@Test(description = "RPMXCON-54577", enabled = true, groups = { "regression" })
	public void verifyReviewerProgressOnDashboard() throws Exception {

		baseClass.stepInfo("Test case Id:RPMXCON-54577 Dashboard Component Sprint 17");
		baseClass.stepInfo(
				"PAU and DAU users should be included for Reviewer Progress widget on RMU Dashboard(Impersonate form SystemAdmin/PAU/DAU to RMU)");

		UtilityLog.info(Input.prodPath);
		SessionSearch sessionsearch = new SessionSearch(driver);
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		Dashboard dashBoard = new Dashboard(driver);
		String SaveSearchName = "NewSearch" + UtilityLog.dynamicNameAppender();
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();

//		sessionsearch.basicContentSearch(Input.testData1);
//		sessionsearch.verifyPureHitsCount();
//		sessionsearch.bulkAssign();
//		agnmt.FinalizeAssignmentAfterBulkAssign();
//		agnmt.createAssignment_fromAssignUnassignPopup(assignmentName, Input.codeFormName);
//		agnmt.getAssignmentSaveButton().waitAndClick(5);
//		baseClass.stepInfo("Created a assignment " + assignmentName);
//		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
//		driver.waitForPageToBeReady();
//		baseClass.stepInfo(assignmentName + " assignment opened in edit mode");
//		agnmt.add4ReviewerAndDistribute();

		sessionsearch.basicContentSearch(Input.testData1);
		baseClass.stepInfo("Search for text input completed");
		String Hits = sessionsearch.verifyPureHitsCount();
		sessionsearch.saveSearch(SaveSearchName);
		sessionsearch.bulkAssign();
		// create Assignment and disturbute docs
		agnmt.assignmentCreation(assignmentName, Input.codeFormName);
		agnmt.add4ReviewerAndDistribute();
		baseClass.stepInfo(assignmentName + " Assignment Created and distributed to DA/PA/RMU/Rev");

	}

	@Test(description = "RPMXCON-54876", enabled = true, groups = { "regression" })
	public void test() throws Exception {

		baseClass.stepInfo("Test case Id:RPMXCON-54876 Dashboard Component Sprint 17");
		baseClass.stepInfo("Verify My Assignment to Work widget on RMU dashboard");

		UtilityLog.info(Input.prodPath);
		Dashboard dashBoard = new Dashboard(driver);

		dashBoard.testCS().GetAttribute("");

	}

}