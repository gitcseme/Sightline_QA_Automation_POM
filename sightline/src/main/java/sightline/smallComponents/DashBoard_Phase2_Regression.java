package sightline.smallComponents;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

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
import pageFactory.Dashboard;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DashBoard_Phase2_Regression {

	

		Driver driver;
		LoginPage loginPage;
		BaseClass baseClass;
		DocViewRedactions docViewRedact;
		Input ip;
		SoftAssert softAssertion;
		Dashboard dashBoard;
		SessionSearch session;

		@BeforeClass(alwaysRun = true)

		private void TestStart() throws Exception, InterruptedException, IOException {

			System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

			Input in = new Input();
			in.loadEnvConfig();
		}

		@BeforeMethod(alwaysRun = true)
		public void beforeTestMethod(ITestResult result, Method testMethod)
				throws IOException, ParseException, Exception {

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
			baseClass.stepInfo(
					"To verify that RMU can view the details on Tag Count Widget by selecting favourite Tags.");
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

			// verified with Edge for the same in local
			baseClass.stepInfo("Test case Id:RPMXCON-54212 Dashboard Component Sprint 17");
			baseClass.stepInfo("To verify that details in End to End is displayed correctly.");
			UtilityLog.info(Input.prodPath);

			// Adding End to End Widget
			Dashboard dashBoard = new Dashboard(driver);
			dashBoard.AddNewWidgetToDashboard(Input.EndtoEnd);
			System.out.println("Released Count :" + dashBoard.releasedCount_EndToEnd().getText());
			System.out.println("Not Reviewed Count :" + dashBoard.notReviewedCount_EndToEnd().getText());
			System.out.println("Reviewed Count :" + dashBoard.reviewedCount_EndToEnd().getText());
			System.out.println("Total produced Count :" + dashBoard.totalProducedCount().getText());

			// Verifying Overall image is centered within the widget.
			baseClass.waitTime(4);
			String expected = "50%";
			String actual = dashBoard.checkIMageAtCenter();
			softAssertion = new SoftAssert();
			softAssertion.assertEquals(actual, expected);
			softAssertion.assertAll();

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
		 *         No:RPMXCON-54574
		 * @Description:PAU and DAU users should be considered in "Total Review
		 *                  Progress" widget on RMU Dashboard
		 **/

		@Test(description = "RPMXCON-54574", enabled = true, groups = { "regression" })
		public void verifyTotalReviewProgress_RMUDashboard() throws Exception {

			baseClass.stepInfo("Test case Id:RPMXCON-54574 Dashboard Component Sprint 17");
			baseClass.stepInfo(
					"PAU and DAU users should be considered in Total Review Progress widget on RMU Dashboard");
			UtilityLog.info(Input.prodPath);
			SessionSearch sessionsearch = new SessionSearch(driver);
			AssignmentsPage agnmt = new AssignmentsPage(driver);
			Dashboard dashBoard = new Dashboard(driver);

			// perform search and bulk assign
			sessionsearch.basicContentSearch(Input.searchString1);
			sessionsearch.verifyPureHitsCount();
			sessionsearch.bulkAssign();
			agnmt.FinalizeAssignmentAfterBulkAssign();

			// create assignment with 1LR classification and distribute documents to
			// reviewer
			String assignmentName = "Aassignment" + Utility.dynamicNameAppender();
			agnmt.createAssignmentWithClassification(assignmentName, "1LR", Input.codeFormName);
			agnmt.getAssignmentSaveButton().waitAndClick(5);
			baseClass.stepInfo("Created a assignment " + assignmentName);
			agnmt.editAssignmentUsingPaginationConcept(assignmentName);
			driver.waitForPageToBeReady();
			baseClass.stepInfo(assignmentName + " assignment opened in edit mode");
			agnmt.distributeToPAUandDAU();
			baseClass.stepInfo(assignmentName + " Assignment Created and distributed to DA/PA");

			// perform search and bulk assign
			baseClass.selectproject();
			sessionsearch.basicContentSearch(Input.searchString1);
			sessionsearch.verifyPureHitsCount();
			sessionsearch.bulkAssign();
			agnmt.FinalizeAssignmentAfterBulkAssign();

			// create assignment with 2LR classification and distribute documents to
			// reviewer
			String assignmentName2 = "Bassignment" + Utility.dynamicNameAppender();
			agnmt.createAssignmentWithClassification(assignmentName2, "2LR", Input.codeFormName);
			agnmt.getAssignmentSaveButton().waitAndClick(5);
			baseClass.stepInfo("Created a assignment " + assignmentName2);
			agnmt.editAssignmentUsingPaginationConcept(assignmentName2);
			driver.waitForPageToBeReady();
			baseClass.stepInfo(assignmentName2 + " assignment opened in edit mode");
			agnmt.distributeToPAUandDAU();
			baseClass.stepInfo(assignmentName2 + " Assignment Created and distributed to DA/PA");

			// perform search and bulk assign
			baseClass.selectproject();
			sessionsearch.basicContentSearch(Input.searchString1);
			sessionsearch.verifyPureHitsCount();
			sessionsearch.bulkAssign();
			agnmt.FinalizeAssignmentAfterBulkAssign();

			// create assignment with 1LR classification and distribute documents to
			// reviewer
			String assignmentName3 = "Cassignment" + Utility.dynamicNameAppender();
			agnmt.createAssignmentWithClassification(assignmentName3, "QC", Input.codeFormName);
			agnmt.getAssignmentSaveButton().waitAndClick(5);
			baseClass.stepInfo("Created a assignment " + assignmentName3);
			agnmt.editAssignmentUsingPaginationConcept(assignmentName3);
			driver.waitForPageToBeReady();
			baseClass.stepInfo(assignmentName3 + " assignment opened in edit mode");
			agnmt.distributeToPAUandDAU();
			baseClass.stepInfo(assignmentName3 + " Assignment Created and distributed to DA/PA");

			baseClass.stepInfo("created Assignments with classifications 1LR : " + assignmentName + " 2LR :"
					+ assignmentName2 + " QC :" + assignmentName3);
			// adding total review progress widget
			dashBoard.navigateToDashboard();
			dashBoard.AddNewWidgetToDashboard(Input.TotalReviewProgress);

			// 1LR classification details
			baseClass.passedStep("1LR completed percent" + dashBoard.get1LR_completedPercent().getText());
			baseClass.passedStep("1LR Distributed percent" + dashBoard.get1LR_distirbutedPercent().getText());
			baseClass.passedStep("1LR Not Distributed percent" + dashBoard.get1LR_notDistirbutedPercent().getText());

			// 2LR classification details
			driver.waitForPageToBeReady();
			baseClass.passedStep("2LR completed percent" + dashBoard.get2LR_completedPercent().getText());
			baseClass.passedStep("2LR Distributed percent" + dashBoard.get2LR_distirbutedPercent().getText());
			baseClass.passedStep("2LR Not Distributed percent" + dashBoard.get2LR_notDistirbutedPercent().getText());

			// QC classification details
			driver.waitForPageToBeReady();
			baseClass.passedStep("QC completed percent" + dashBoard.getQC_completedPercent().getText());
			baseClass.passedStep("QC Distributed percent" + dashBoard.getQC_distirbutedPercent().getText());
			baseClass.passedStep("QC Not Distributed percent" + dashBoard.getQC_notDistirbutedPercent().getText());

		}

		/**
		 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
		 *         No:RPMXCON-54575
		 * @Description:PAU and DAU users should be considered in "Total Review
		 *                  Progress" widget on RMU Dashboard (SystemAdmin/PAU/DAU users
		 *                  impersonate to RMU)
		 **/

		@Test(description = "RPMXCON-54575", enabled = true, groups = { "regression" })
		public void verifyTotalReviewProgress_Impersonate() throws Exception {

			baseClass.stepInfo("Test case Id:RPMXCON-54575 Dashboard Component Sprint 17");
			baseClass.stepInfo(
					"PAU and DAU users should be considered in Total Review Progress widget on RMU Dashboard (SystemAdmin/PAU/DAU users impersonate to RMU)");
			UtilityLog.info(Input.prodPath);
			SessionSearch sessionsearch = new SessionSearch(driver);
			AssignmentsPage agnmt = new AssignmentsPage(driver);
			Dashboard dashBoard = new Dashboard(driver);

			// create assignment with 1LR classification and distribute documents to
			// reviewer
			sessionsearch.basicContentSearch(Input.searchString1);
			sessionsearch.verifyPureHitsCount();
			sessionsearch.bulkAssign();
			agnmt.FinalizeAssignmentAfterBulkAssign();
			String assignmentName = "Aassignment" + Utility.dynamicNameAppender();
			agnmt.createAssignmentWithClassification(assignmentName, "1LR", Input.codeFormName);
			agnmt.getAssignmentSaveButton().waitAndClick(5);
			baseClass.stepInfo("Created a assignment " + assignmentName);
			agnmt.editAssignmentUsingPaginationConcept(assignmentName);
			driver.waitForPageToBeReady();
			baseClass.stepInfo(assignmentName + " assignment opened in edit mode");
			agnmt.add4ReviewerAndDistribute();
			baseClass.stepInfo(assignmentName + " Assignment Created and distributed to DA/PA");

			// create assignment with 2LR classification and distribute documents to
			// reviewer
			baseClass.selectproject();
			sessionsearch.basicContentSearch(Input.searchString1);
			sessionsearch.verifyPureHitsCount();
			sessionsearch.bulkAssign();
			agnmt.FinalizeAssignmentAfterBulkAssign();
			String assignmentName2 = "Bassignment" + Utility.dynamicNameAppender();
			agnmt.createAssignmentWithClassification(assignmentName2, "2LR", Input.codeFormName);
			agnmt.getAssignmentSaveButton().waitAndClick(5);
			baseClass.stepInfo("Created a assignment " + assignmentName2);
			agnmt.editAssignmentUsingPaginationConcept(assignmentName2);
			driver.waitForPageToBeReady();
			baseClass.stepInfo(assignmentName2 + " assignment opened in edit mode");
			agnmt.add4ReviewerAndDistribute();
			baseClass.stepInfo(assignmentName2 + " Assignment Created and distributed to DA/PA");

			// create assignment with QC classification and distribute documents to reviewer
			baseClass.selectproject();
			sessionsearch.basicContentSearch(Input.searchString1);
			sessionsearch.verifyPureHitsCount();
			sessionsearch.bulkAssign();
			agnmt.FinalizeAssignmentAfterBulkAssign();
			String assignmentName3 = "Cassignment" + Utility.dynamicNameAppender();
			agnmt.createAssignmentWithClassification(assignmentName3, "QC", Input.codeFormName);
			agnmt.getAssignmentSaveButton().waitAndClick(5);
			baseClass.stepInfo("Created a assignment " + assignmentName3);
			agnmt.editAssignmentUsingPaginationConcept(assignmentName3);
			driver.waitForPageToBeReady();
			baseClass.stepInfo(assignmentName3 + " assignment opened in edit mode");
			agnmt.add4ReviewerAndDistribute();
			baseClass.stepInfo(assignmentName3 + " Assignment Created and distributed to DA/PA");

			baseClass.stepInfo("created Assignments with classifications 1LR : " + assignmentName + " 2LR :"
					+ assignmentName2 + " QC :" + assignmentName3);

			loginPage.logout();
			loginPage.loginToSightLine(Input.da1userName, Input.da1password);
			baseClass.impersonateDAtoRMU();
			// adding total review progress widget
			dashBoard.navigateToDashboard();
			dashBoard.AddNewWidgetToDashboard(Input.TotalReviewProgress);

			// 1LR classification details
			baseClass.waitTime(2);
			baseClass.passedStep("1LR completed percent" + dashBoard.get1LR_completedPercent().getText());
			baseClass.passedStep("1LR Distributed percent" + dashBoard.get1LR_distirbutedPercent().getText());
			baseClass.passedStep("1LR Not Distributed percent" + dashBoard.get1LR_notDistirbutedPercent().getText());

			// 2LR classification details
			driver.waitForPageToBeReady();
			baseClass.passedStep("2LR completed percent" + dashBoard.get2LR_completedPercent().getText());
			baseClass.passedStep("2LR Distributed percent" + dashBoard.get2LR_distirbutedPercent().getText());
			baseClass.passedStep("2LR Not Distributed percent" + dashBoard.get2LR_notDistirbutedPercent().getText());

			// QC classification details
			driver.waitForPageToBeReady();
			baseClass.passedStep("QC completed percent" + dashBoard.getQC_completedPercent().getText());
			baseClass.passedStep("QC Distributed percent" + dashBoard.getQC_distirbutedPercent().getText());
			baseClass.passedStep("QC Not Distributed percent" + dashBoard.getQC_notDistirbutedPercent().getText());

			loginPage.logout();
			loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
			baseClass.impersonatePAtoRMU();

			// adding total review progress widget
			dashBoard.navigateToDashboard();
			dashBoard.AddNewWidgetToDashboard(Input.TotalReviewProgress);

			// 1LR classification details
			driver.waitForPageToBeReady();
			baseClass.waitTime(2);
			baseClass.passedStep("1LR completed percent" + dashBoard.get1LR_completedPercent().getText());
			baseClass.passedStep("1LR Distributed percent" + dashBoard.get1LR_distirbutedPercent().getText());
			baseClass.passedStep("1LR Not Distributed percent" + dashBoard.get1LR_notDistirbutedPercent().getText());

			// 2LR classification details
			driver.waitForPageToBeReady();
			baseClass.passedStep("2LR completed percent" + dashBoard.get2LR_completedPercent().getText());
			baseClass.passedStep("2LR Distributed percent" + dashBoard.get2LR_distirbutedPercent().getText());
			baseClass.passedStep("2LR Not Distributed percent" + dashBoard.get2LR_notDistirbutedPercent().getText());

			// QC classification details
			driver.waitForPageToBeReady();
			baseClass.passedStep("QC completed percent" + dashBoard.getQC_completedPercent().getText());
			baseClass.passedStep("QC Distributed percent" + dashBoard.getQC_distirbutedPercent().getText());
			baseClass.passedStep("QC Not Distributed percent" + dashBoard.getQC_notDistirbutedPercent().getText());

			loginPage.logout();
			loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
			baseClass.impersonateSAtoRMU();

			// adding total review progress widget
			dashBoard.navigateToDashboard();
			dashBoard.AddNewWidgetToDashboard(Input.TotalReviewProgress);

			// 1LR classification details
			driver.waitForPageToBeReady();
			baseClass.waitTime(2);
			baseClass.passedStep("1LR completed percent" + dashBoard.get1LR_completedPercent().getText());
			baseClass.passedStep("1LR Distributed percent" + dashBoard.get1LR_distirbutedPercent().getText());
			baseClass.passedStep("1LR Not Distributed percent" + dashBoard.get1LR_notDistirbutedPercent().getText());

			// 2LR classification details
			driver.waitForPageToBeReady();
			baseClass.passedStep("2LR completed percent" + dashBoard.get2LR_completedPercent().getText());
			baseClass.passedStep("2LR Distributed percent" + dashBoard.get2LR_distirbutedPercent().getText());
			baseClass.passedStep("2LR Not Distributed percent" + dashBoard.get2LR_notDistirbutedPercent().getText());

			// QC classification details
			driver.waitForPageToBeReady();
			baseClass.passedStep("QC completed percent" + dashBoard.getQC_completedPercent().getText());
			baseClass.passedStep("QC Distributed percent" + dashBoard.getQC_distirbutedPercent().getText());
			baseClass.passedStep("QC Not Distributed percent" + dashBoard.getQC_notDistirbutedPercent().getText());
		}

		/**
		 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
		 *         No:RPMXCON-54576
		 * @Description:PAU and DAU users should be included for "Reviewer Progress"
		 *                  widget on RMU Dashboard
		 **/
		@Test(description = "RPMXCON-54576", enabled = true, groups = { "regression" })
		public void verifyReviewerProgressOnDashboard() throws Exception {

			baseClass.stepInfo("Test case Id:RPMXCON-54576 Dashboard Component Sprint 17");
			baseClass.stepInfo("PAU and DAU users should be included for Reviewer Progress widget on RMU Dashboard");

			UtilityLog.info(Input.prodPath);
			SessionSearch sessionsearch = new SessionSearch(driver);
			AssignmentsPage agnmt = new AssignmentsPage(driver);
			Dashboard dashBoard = new Dashboard(driver);

			String assignmentName = "Assignment" + Utility.dynamicNameAppender();
			String[] reviewers = { Input.pa1FullName, Input.rmu1FullName, Input.rev1FullName, Input.da1FullName };
			String[] reviewersToUpperCase = { Input.pa1FullName.toUpperCase(), Input.rmu1FullName.toUpperCase(),
					Input.rev1FullName.toUpperCase(), Input.da1FullName.toUpperCase() };
			String SaveSearchName = "NewSearch" + UtilityLog.dynamicNameAppender();

			sessionsearch.basicContentSearch(Input.testData1);
			baseClass.stepInfo("Search for text input completed");
			sessionsearch.verifyPureHitsCount();
			sessionsearch.saveSearch(SaveSearchName);
			sessionsearch.bulkAssign();
			// create Assignment and disturbute docs
			agnmt.assignmentCreation(assignmentName, Input.codeFormName);
			agnmt.add4ReviewerAndDistribute();
			baseClass.stepInfo(assignmentName + " Assignment Created and distributed to DA/PA/RMU/Rev");

			dashBoard.navigateToDashboard();
			dashBoard.AddNewWidgetToDashboard(Input.reviewerProgress);
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			dashBoard.select4Reviewers_ReviewerProgressWidget(reviewers, assignmentName);
			dashBoard.verifyReviewerProgressData(reviewersToUpperCase);
		}

		/**
		 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
		 *         No:RPMXCON-54577
		 * @Description:PAU and DAU users should be included for "Reviewer Progress"
		 *                  widget on RMU Dashboard(Impersonate form SystemAdmin/PAU/DAU
		 *                  to RMU)
		 **/

		@Test(description = "RPMXCON-54577", enabled = true, groups = { "regression" })
		public void verifyReviewerProgress_RMUDashboardImpersonate() throws Exception {

			baseClass.stepInfo("Test case Id:RPMXCON-54577 Dashboard Component Sprint 17");
			baseClass.stepInfo(
					"PAU and DAU users should be included for Reviewer Progress widget on RMU Dashboard(Impersonate form SystemAdmin/PAU/DAU to RMU)");
			UtilityLog.info(Input.prodPath);
			SessionSearch sessionsearch = new SessionSearch(driver);
			AssignmentsPage agnmt = new AssignmentsPage(driver);
			Dashboard dashBoard = new Dashboard(driver);

			String assignmentName = "Assignment" + Utility.dynamicNameAppender();
			String[] reviewers = { Input.pa1FullName, Input.rmu1FullName, Input.rev1FullName, Input.da1FullName };
			String[] reviewersToUpperCase = { Input.pa1FullName.toUpperCase(), Input.rmu1FullName.toUpperCase(),
					Input.rev1FullName.toUpperCase(), Input.da1FullName.toUpperCase() };
			String SaveSearchName = "NewSearch" + UtilityLog.dynamicNameAppender();

			sessionsearch.basicContentSearch(Input.testData1);
			baseClass.stepInfo("Search for text input completed");
			sessionsearch.verifyPureHitsCount();
			sessionsearch.saveSearch(SaveSearchName);
			driver.waitForPageToBeReady();
			sessionsearch.bulkAssign();
			// create Assignment and disturbute docs
			agnmt.assignmentCreation(assignmentName, Input.codeFormName);
			agnmt.add4ReviewerAndDistribute();
			baseClass.stepInfo(assignmentName + " Assignment Created and distributed to DA/PA/RMU/Rev");

			loginPage.logout();
			loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
			driver.waitForPageToBeReady();
			baseClass.impersonatePAtoRMU();

			dashBoard.navigateToDashboard();
			dashBoard.AddNewWidgetToDashboard(Input.reviewerProgress);
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			dashBoard.select4Reviewers_ReviewerProgressWidget(reviewers, assignmentName);
			dashBoard.verifyReviewerProgressData(reviewersToUpperCase);
			baseClass.passedStep("Impersonated from PA to RMU and verified the widget");

			loginPage.logout();
			loginPage.loginToSightLine(Input.da1userName, Input.da1password);
			driver.waitForPageToBeReady();
			baseClass.waitTime(5);
			baseClass.impersonateDAtoRMU();

			dashBoard.navigateToDashboard();
			dashBoard.AddNewWidgetToDashboard(Input.reviewerProgress);
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			dashBoard.select4Reviewers_ReviewerProgressWidget(reviewers, assignmentName);
			driver.waitForPageToBeReady();
			dashBoard.verifyReviewerProgressData(reviewersToUpperCase);

			baseClass.passedStep("Impersonated from DA to RMU and verified the widget");

			loginPage.logout();
			loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
			driver.waitForPageToBeReady();
			baseClass.impersonateSAtoRMU();

			dashBoard.navigateToDashboard();
			dashBoard.AddNewWidgetToDashboard(Input.reviewerProgress);
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			dashBoard.select4Reviewers_ReviewerProgressWidget(reviewers, assignmentName);
			dashBoard.verifyReviewerProgressData(reviewersToUpperCase);

			baseClass.passedStep("Impersonated from SA to RMU and verified the widget");

		}

		/**
		 * @Author Jeevitha
		 * @Description : To verify that RMU is able to add the Total Review Progress
		 *              widget on Dashboard [RPMXCON-54177]
		 * @throws Exception
		 */
		@Test(description = "RPMXCON-54177", enabled = true, groups = { "regression" })
		public void verifyTotalReviewWidgetAdded() throws Exception {
			
			loginPage.logout();
			String widgetName = "Total Review Progress";

			// login As RMU
			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

			baseClass.stepInfo("Test case Id:RPMXCON-54177 Dashboard");
			baseClass.stepInfo("To verify that RMU is able to add the Total Review Progress widget on Dashboard");

			// Add Total review Progress Widget
			Dashboard dashBoard = new Dashboard(driver);
			dashBoard.AddNewWidgetToDashboard(widgetName);

			// verify whether widget added in dashboard
			driver.waitForPageToBeReady();
			baseClass.ValidateElement_Presence(dashBoard.DashboardWidgetHeader(widgetName), widgetName + " Widget");
			baseClass.passedStep(widgetName + " is displayed in Dashboard");

			// logout
			loginPage.logout();
		}

		/**
		 * @Author Jeevitha
		 * @Description : To verify that RMU can Add the TRP widget and it will be
		 *              maintained through the session [RPMXCON-54185]
		 * @throws Exception
		 */
		@Test(description = "RPMXCON-54185", enabled = true, groups = { "regression" })
		public void verifyWidgetIsMaintained() throws Exception {
			loginPage.logout();
			String widgetName = "Total Review Progress";

			// login As RMU
			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

			baseClass.stepInfo("Test case Id:RPMXCON-54185 Dashboard");
			baseClass.stepInfo(
					"To verify that RMU can Add the TRP widget and it will be maintained through the session");

			// verify whether widget added in dashboard
			Dashboard dashBoard = new Dashboard(driver);
			dashBoard.navigateToDashboard();
			driver.waitForPageToBeReady();
			boolean widgetStatus = baseClass
					.ValidateElement_PresenceReturn(dashBoard.DashboardWidgetHeader(widgetName));

			// Add Total review Progress Widget
			if (!widgetStatus) {
				dashBoard.AddNewWidgetToDashboard(widgetName);
			} else {
				baseClass.stepInfo(widgetName + " : Widget is Present in Dashboard");
			}

			List<String> widgetsBefore = baseClass.availableListofElements(dashBoard.countOfWidget());

			// navigate to another page
			SessionSearch session = new SessionSearch(driver);
			session.navigateToSessionSearchPageURL();
			baseClass.stepInfo("Navigated to SessionSearch Page");

			// navigate back to dashboard page
			dashBoard.navigateToDashboard();
			baseClass.stepInfo("Navigated Back to Dashboard Page");

			// verify all the widgets are maintained
			driver.waitForPageToBeReady();
			List<String> widgetsAfterNavigate = baseClass.availableListofElements(dashBoard.countOfWidget());
			String passMsg = "All previous Widgets are Maintained : " + widgetsAfterNavigate;
			String failMsg = "Widgets are not Maintained";
			baseClass.listCompareEquals(widgetsAfterNavigate, widgetsBefore, passMsg, failMsg);

			// logout
			loginPage.logout();
		}

		/**
		 * @Author Jeevitha
		 * @Description : To verify that on select widget in Widgets Library, RMU is
		 *              displayed with preview window selected widget. [RPMXCON-54181]
		 * @throws Exception
		 */
		@Test(description = "RPMXCON-54181", enabled = true, groups = { "regression" })
		public void verifyPreviewWindowDisplayed() throws Exception {
			loginPage.logout();

			// login As RMU
			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

			baseClass.stepInfo("Test case Id:RPMXCON-54181 Dashboard");
			baseClass.stepInfo(
					"To verify that on select widget in Widgets Library, RMU is displayed with preview window selected widget.");

			// Click wheel icon & then Click add new btn & verify buttons & Popup.
			Dashboard dashBoard = new Dashboard(driver);
			dashBoard.ClickWheelIconAndVerify(true);

			// verify respective widget Preview pop up is displayed.
			dashBoard.clickWidgetAndVerifyPopUp("", true, false);

			// logout
			loginPage.logout();
		}

		/**
		 * @Author Jeevitha
		 * @Description : To verify that RPR widget by default it will display "Top 6
		 *              Reviewers with Lowest Productivity" when added first time on
		 *              Dashboard by RMU. [RPMXCON-54189]
		 * @throws Exception
		 */
		@Test(description = "RPMXCON-54189", enabled = true, groups = { "regression" })
		public void verifyReviwerProductWidgetAdded() throws Exception {
			loginPage.logout();
			
			String widgetName = "Reviewer Productivity";
			String headerName = "TOP 6 REVIEWERS WITH LOWEST PRODUCTIVITY";

			// login As RMU
			loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

			baseClass.stepInfo("Test case Id:RPMXCON-54189 Dashboard");
			baseClass.stepInfo(
					"To verify that RPR widget by default it will display \"Top 6 Reviewers with Lowest Productivity\" when added first time on Dashboard by RMU.");

			// Add Reviewer Productivity Widget
			Dashboard dashBoard = new Dashboard(driver);
			dashBoard.AddNewWidgetToDashboard(widgetName);

			// verify whether widget added in dashboard
			driver.waitForPageToBeReady();
			baseClass.ValidateElement_Presence(dashBoard.DashboardWidgetHeader(widgetName), widgetName + " Widget");
			baseClass.passedStep(widgetName + " is displayed in Dashboard");

			// verify top 6 reviewers table is displayed
			List<String> reviewProgressTable = baseClass.availableListofElements(dashBoard.getReviewerProdTop6Header());
			baseClass.compareListWithOnlyOneString(reviewProgressTable, headerName,
					headerName + " : Review Progress is Displayed As default", "Review Progress is Not Displayed");

			// logout
			loginPage.logout();
		}

		/**
		 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
		 *         No:RPMXCON-54192, 54188
		 * @Description:To verify that if RMU can view the details on Reviewer Progress
		 *                 widget on selecting specific RU. TESTCASE No:RPMXCON-54188
		 * @Description:To verify that RMU is able to view Review Progress By Reviewer
		 *                 widget on Dashboard.
		 **/

		@Test(description = "RPMXCON-54192,RPMXCON-54188", enabled = true, groups = { "regression" })
		public void verifyReviewerProgressDetails() throws Exception {

			baseClass.stepInfo("Test case Id:RPMXCON-54192 Dashboard Component Sprint 18");
			baseClass.stepInfo(
					"To verify that if RMU can view the details on Reviewer Progress widget on selecting specific RU.");

			baseClass.stepInfo("Test case Id:RPMXCON-54188 Dashboard Component Sprint 18");
			baseClass.stepInfo("To verify that RMU is able to view Review Progress By Reviewer widget on Dashboard.");
			UtilityLog.info(Input.prodPath);

			SessionSearch sessionsearch = new SessionSearch(driver);
			AssignmentsPage agnmt = new AssignmentsPage(driver);
			Dashboard dashBoard = new Dashboard(driver);

			String assignmentName = "Assignment" + Utility.dynamicNameAppender();
			String[] reviewers = { Input.pa1FullName, Input.rmu1FullName };
			String[] reviewersToUpperCase = { Input.pa1FullName.toUpperCase(), Input.rmu1FullName.toUpperCase() };
			String SaveSearchName = "NewSearch" + UtilityLog.dynamicNameAppender();

			sessionsearch.basicContentSearch(Input.testData1);
			baseClass.stepInfo("Search for text input completed");
			sessionsearch.verifyPureHitsCount();
			sessionsearch.saveSearch(SaveSearchName);
			sessionsearch.bulkAssign();
			// create Assignment and disturbute docs
			agnmt.assignmentCreation(assignmentName, Input.codeFormName);
			agnmt.add4ReviewerAndDistribute();
			baseClass.stepInfo(assignmentName + " Assignment Created and distributed to DA/PA/RMU/Rev");

			dashBoard.navigateToDashboard();
			dashBoard.AddNewWidgetToDashboard(Input.reviewerProgress);
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			dashBoard.select4Reviewers_ReviewerProgressWidget(reviewers, assignmentName);
			dashBoard.verifyReviewerProgressData(reviewersToUpperCase);
		}

		/**
		 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
		 *         No:RPMXCON-54190
		 * @Description:To verify that RMU is able to view "Customize Widget" pop up and
		 *                 criteria in it.
		 **/

		@Test(description = "RPMXCON-54190", enabled = true, groups = { "regression" })
		public void verifyCustomizeWidget() throws Exception {

			baseClass.stepInfo("Test case Id:RPMXCON-54190 Dashboard Component Sprint 18");
			baseClass.stepInfo("To verify that RMU is able to view Customize Widget pop up and criteria in it.");
			UtilityLog.info(Input.prodPath);
			SessionSearch sessionsearch = new SessionSearch(driver);
			AssignmentsPage agnmt = new AssignmentsPage(driver);
			Dashboard dashBoard = new Dashboard(driver);

			String assignmentName = "Assignment" + Utility.dynamicNameAppender();
			String SaveSearchName = "NewSearch" + UtilityLog.dynamicNameAppender();
			String[] listOfReviewers = { Input.pa1FullName, Input.rmu1FullName, Input.rev1FullName, Input.da1FullName };
			String[] reviewers = { "PRODUCTION", Input.pa1FullName.toUpperCase(), Input.rmu1FullName.toUpperCase(),
					Input.rev1FullName.toUpperCase(), Input.da1FullName.toUpperCase(), "SPECIFIC REVIEWERS" };

			sessionsearch.basicContentSearch(Input.testData1);
			baseClass.stepInfo("Search for text input completed");
			sessionsearch.verifyPureHitsCount();
			sessionsearch.saveSearch(SaveSearchName);
			sessionsearch.bulkAssign();
			// create Assignment and disturbute docs
			agnmt.assignmentCreation(assignmentName, Input.codeFormName);
			agnmt.add4ReviewerAndDistribute();
			baseClass.stepInfo(assignmentName + " Assignment Created and distributed to PA/RMU");

			dashBoard.navigateToDashboard();
			dashBoard.AddNewWidgetToDashboard(Input.ReviewerProductivity);
			dashBoard.select4Reviewers_reviewerProductivityWidget(listOfReviewers, assignmentName);

			baseClass.waitForElementCollection(dashBoard.listOfReviewers());
			int size = dashBoard.listOfReviewers().size();
			System.out.println(size);
			baseClass.waitForElementCollection(dashBoard.listOfReviewers());
			List<String> availableListofElements = baseClass.availableListofElements(dashBoard.listOfReviewers());
			String passMsg = "Reviewers list should include associated PAU, RMU to the project";
			String failMsg = "Reviewers list is not displayed with assigned reviewers";
			baseClass.compareArraywithDataList(reviewers, availableListofElements, true, passMsg, failMsg);
		}

		/**
		 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
		 *         No:RPMXCON-54186
		 * @Description:To verify that added widget on the dashboard can be removed.
		 **/

		@Test(description = "RPMXCON-54186", enabled = true, groups = { "regression" })
		public void verifyDeleteWidget() throws Exception {
			baseClass.stepInfo("Test case Id:RPMXCON-54186 Dashboard Component Sprint 18");
			baseClass.stepInfo("To verify that added widget on the dashboard can be removed.");
			UtilityLog.info(Input.prodPath);
			Dashboard dashBoard = new Dashboard(driver);
			dashBoard.navigateToDashboard();
			dashBoard.DeleteWidgetFromDashboard();

		}

		/**
		 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
		 *         No:RPMXCON-54573
		 * @Description:PAU and DAU users should be included for "Reviewer Productivity"
		 *                  widget on RMU Dashboard
		 **/

		@Test(description = "RPMXCON-54573", enabled = true, groups = { "regression" })
		public void verifyReviewerProductivity_RMUDashboard() throws Exception {

			baseClass.stepInfo("Test case Id:RPMXCON-54573 Dashboard Component Sprint 17");
			baseClass
					.stepInfo("PAU and DAU users should be included for Reviewer Productivity widget on RMU Dashboard");
			UtilityLog.info(Input.prodPath);
			SessionSearch sessionsearch = new SessionSearch(driver);
			AssignmentsPage agnmt = new AssignmentsPage(driver);
			Dashboard dashBoard = new Dashboard(driver);

			String assignmentName = "Assignment" + Utility.dynamicNameAppender();
			String SaveSearchName = "NewSearch" + UtilityLog.dynamicNameAppender();
			String[] listOfReviewers = { Input.pa1FullName, Input.rmu1FullName, Input.rev1FullName, Input.da1FullName };
			String[] reviewers = { "PRODUCTION", "SPECIFIC REVIEWERS", Input.pa1FullName.toUpperCase(),
					Input.rmu1FullName.toUpperCase(), Input.rev1FullName.toUpperCase(),
					Input.da1FullName.toUpperCase() };

			sessionsearch.basicContentSearch(Input.testData1);
			baseClass.stepInfo("Search for text input completed");
			sessionsearch.verifyPureHitsCount();
			sessionsearch.saveSearch(SaveSearchName);
			sessionsearch.bulkAssign();
			// create Assignment and disturbute docs
			agnmt.assignmentCreation(assignmentName, Input.codeFormName);
			driver.waitForPageToBeReady();
			agnmt.add4ReviewerAndDistribute();
			baseClass.stepInfo(assignmentName + " Assignment Created and distributed to DA/PA/RMU/Rev");

			dashBoard.navigateToDashboard();
			dashBoard.AddNewWidgetToDashboard(Input.ReviewerProductivity);
			dashBoard.select4Reviewers_reviewerProductivityWidget(listOfReviewers, assignmentName);

			baseClass.waitForElementCollection(dashBoard.listOfReviewers());
			int size = dashBoard.listOfReviewers().size();

			List<String> availableListofElements = baseClass.availableListofElements(dashBoard.listOfReviewers());
			String passMsg = "Reviewers list should include associated DAU, PAU, RMU and Reviewer to the project";
			String failMsg = "Reviewers list is not displayed with assigned reviewers";
			baseClass.compareArraywithDataList(reviewers, availableListofElements, true, passMsg, failMsg);
		}

	}


