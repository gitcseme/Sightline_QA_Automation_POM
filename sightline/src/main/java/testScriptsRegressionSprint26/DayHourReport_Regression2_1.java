package testScriptsRegressionSprint26;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
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
import pageFactory.AssignmentReviewProgressReport;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CommunicationExplorerPage;
import pageFactory.DayHourReport;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ReviewerReviewProgressReport;
import pageFactory.SessionSearch;
import pageFactory.UserReviewActivityReport;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DayHourReport_Regression2_1 {
	Driver driver;
	LoginPage lp;
	SoftAssert softAssertion;
	BaseClass bc;
	AssignmentsPage assgnmntPg;
	SessionSearch sessionSearch;
	DocViewPage docview;
	DayHourReport dhReport;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();

	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @description: Validate Distributed document count for Review Count report by Daily (RMU login)
	 */
	@Test(description = "RPMXCON-56949", enabled = true, groups = { "regression" })
	public void verifyDistributedDocsInReviewCountDailyReportByRMU() throws InterruptedException, ParseException {
		bc.stepInfo("Test case Id: RPMXCON-56949"); 
		bc.stepInfo("Validate Distributed document count for Review Count report by Daily (RMU login)"); 
		// Login as Reviewer Manager
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		assgnmntPg = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		dhReport = new DayHourReport(driver);		
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assgnmntPg.createAssignmentFromAssgnGroup(assignmentName, Input.codeFormName);			
		// search to Assignment creation
		int expectedDocs = sessionSearch.basicContentSearch(Input.TallySearch);	
		sessionSearch.bulkAssignExisting(assignmentName);
		driver.waitForPageToBeReady();
		assgnmntPg.editAssignmentUsingPaginationConcept(assignmentName);
		assgnmntPg.add2ReviewerAndDistribute();
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		dhReport.navigateToDayHourReport();
		String today = dhReport.getCurrentDate();
		dhReport.selectMultipleReviewer(Input.rev1FullName,Input.rmu1FullName);
		dhReport.selectAssignmentGroup(assignmentName);
		dhReport.selectDateRange(today,today);
		dhReport.applyChanges();	
		driver.waitForPageToBeReady();
		String actualDocsForReviewer=dhReport.getColoumnValue(dhReport.reviewerColumnNameHeader(), "Distributed Docs",Input.rev1FullName);
		String actualDocsForRmu=dhReport.getColoumnValue(dhReport.reviewerColumnNameHeader(), "Distributed Docs",Input.rmu1FullName);
		int totalDocs = Integer.parseInt(actualDocsForReviewer)+Integer.parseInt(actualDocsForRmu);
		softAssertion.assertEquals(totalDocs, expectedDocs);
		softAssertion.assertAll();
		bc.passedStep("Distributed Docs are displayed in report as expected");
		bc.passedStep("Total docs distributed are "+totalDocs+" displayed in report as expected");
		assgnmntPg.deleteAssgnmntUsingPagination(assignmentName);
		lp.logout();		
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @description: VValidate Distributed document count for Review Count report by daily (Project Admin login)
	 */
	@Test(description = "RPMXCON-56946", enabled = true, groups = { "regression" })
	public void verifyDistributedDocsInReviewCountDailyReportByPA() throws InterruptedException, ParseException {
		bc.stepInfo("Test case Id: RPMXCON-56946"); 
		bc.stepInfo("Validate Distributed document count for Review Count report by daily (Project Admin login)"); 
		// Login as Reviewer Manager
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		assgnmntPg = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		dhReport = new DayHourReport(driver);		
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assgnmntPg.createAssignmentFromAssgnGroup(assignmentName, Input.codeFormName);			
		// search to Assignment creation
		int expectedDocs = sessionSearch.basicContentSearch(Input.TallySearch);	
		sessionSearch.bulkAssignExisting(assignmentName);
		driver.waitForPageToBeReady();
		assgnmntPg.editAssignmentUsingPaginationConcept(assignmentName);
		assgnmntPg.add2ReviewerAndDistribute();
		lp.logout();
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		dhReport.navigateToDayHourReport();
		String today = dhReport.getCurrentDate();
		dhReport.selectMultipleReviewer(Input.rev1FullName,Input.rmu1FullName);
		dhReport.selectAssignmentGroup(assignmentName);
		dhReport.selectDateRange(today,today);
		dhReport.applyChanges();	
		driver.waitForPageToBeReady();
		String actualDocsForReviewer=dhReport.getColoumnValue(dhReport.reviewerColumnNameHeader(), "Distributed Docs",Input.rev1FullName);
		String actualDocsForRmu=dhReport.getColoumnValue(dhReport.reviewerColumnNameHeader(), "Distributed Docs",Input.rmu1FullName);
		int totalDocs = Integer.parseInt(actualDocsForReviewer)+Integer.parseInt(actualDocsForRmu);
		softAssertion.assertEquals(totalDocs, expectedDocs);
		softAssertion.assertAll();
		bc.passedStep("Distributed Docs are displayed in report as expected");
		bc.passedStep("Total docs distributed are "+totalDocs+" displayed in report as expected");
		lp.logout();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assgnmntPg.deleteAssgnmntUsingPagination(assignmentName);
		lp.logout();		
	}
	

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		softAssertion = new SoftAssert();
		driver = new Driver();
		lp = new LoginPage(driver);
		bc = new BaseClass(driver);
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		lp = new LoginPage(driver);
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

	@DataProvider(name = "Users_PARMU")
	public Object[][] PA_RMU() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "RMU" },
				{ Input.pa1userName, Input.pa1password, "PA" } 
				};
		return users;
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		
		bc.stepInfo("***Executed Communications Explorer_Regression1****");
		  

	}
}
