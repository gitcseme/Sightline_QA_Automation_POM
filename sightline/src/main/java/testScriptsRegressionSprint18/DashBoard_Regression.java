package testScriptsRegressionSprint18;

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
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DashBoard_Regression {
	
	public class Dashboard_Regression {

		Driver driver;
		LoginPage loginPage;
		BaseClass baseClass;
		DocViewRedactions docViewRedact;
		Input ip;
		SoftAssert softAssertion;

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
		 * @author Sowndarya.Velraj created on:NA modified by:NA 
		 * TESTCASE No:RPMXCON-54192, 54188
		 * @Description:To verify that if RMU can view the details on Reviewer Progress widget on selecting specific RU.
		 * TESTCASE No:RPMXCON-54188
		 * @Description:To verify that RMU is able to view Review Progress By Reviewer widget on Dashboard.
		 **/

		@Test(description = "RPMXCON-54192,RPMXCON-54188", enabled = true, groups = { "regression" })
		public void verifyReviewerProgressDetails() throws Exception {

			baseClass.stepInfo("Test case Id:RPMXCON-54192 Dashboard Component Sprint 18");
			baseClass.stepInfo("To verify that if RMU can view the details on Reviewer Progress widget on selecting specific RU.");
			
			baseClass.stepInfo("Test case Id:RPMXCON-54188 Dashboard Component Sprint 18");
			baseClass.stepInfo("To verify that RMU is able to view Review Progress By Reviewer widget on Dashboard.");
			UtilityLog.info(Input.prodPath);
			
			SessionSearch sessionsearch = new SessionSearch(driver);
			AssignmentsPage agnmt = new AssignmentsPage(driver);
			Dashboard dashBoard = new Dashboard(driver);

			String assignmentName = "Assignment" + Utility.dynamicNameAppender();
			String[] reviewers = { Input.pa1FullName, Input.rmu1FullName};
			String[] reviewersToUpperCase = { Input.pa1FullName.toUpperCase(), Input.rmu1FullName.toUpperCase()};
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
		 * @Description:To verify that RMU is able to view "Customize Widget" pop up and criteria in it.
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
			String[] listOfReviewers = { Input.pa1FullName, Input.rmu1FullName,Input.rev1FullName,Input.da1FullName};
            String[] reviewers = {"PRODUCTION", Input.pa1FullName.toUpperCase(), Input.rmu1FullName.toUpperCase(),Input.rev1FullName.toUpperCase(),Input.da1FullName.toUpperCase(), "SPECIFIC REVIEWERS"};

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
			baseClass.stepInfo("PAU and DAU users should be included for Reviewer Productivity widget on RMU Dashboard");
			UtilityLog.info(Input.prodPath);
			SessionSearch sessionsearch = new SessionSearch(driver);
			AssignmentsPage agnmt = new AssignmentsPage(driver);
			Dashboard dashBoard = new Dashboard(driver);

			String assignmentName = "Assignment" + Utility.dynamicNameAppender();
			String SaveSearchName = "NewSearch" + UtilityLog.dynamicNameAppender();
			String[] listOfReviewers = { Input.pa1FullName, Input.rmu1FullName, Input.rev1FullName, Input.da1FullName };
			String[] reviewers = { "PRODUCTION","SPECIFIC REVIEWERS", Input.pa1FullName.toUpperCase(), Input.rmu1FullName.toUpperCase(),
					Input.rev1FullName.toUpperCase(), Input.da1FullName.toUpperCase() };

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

	}
	