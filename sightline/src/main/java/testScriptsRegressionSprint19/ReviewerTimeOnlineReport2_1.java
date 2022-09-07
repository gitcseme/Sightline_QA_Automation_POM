package testScriptsRegressionSprint19;

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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.LoginPage;
import pageFactory.ReviewerTimeOnlineReport;
import pageFactory.SessionSearch;
import pageFactory.UserLoginActivityReport;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ReviewerTimeOnlineReport2_1 {
	Driver driver;
	LoginPage lp;
	SoftAssert softAssertion;
	BaseClass bc;
	ReviewerTimeOnlineReport reviewerTimeOnlineRptPg;
	UserLoginActivityReport userLoginActivityRptPg;
	AssignmentsPage assgnmntPg;
	SessionSearch sessionSearch;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();

	}
	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		softAssertion = new SoftAssert();
		driver = new Driver();
		lp = new LoginPage(driver);
		bc = new BaseClass(driver);
		reviewerTimeOnlineRptPg = new ReviewerTimeOnlineReport(driver);
		userLoginActivityRptPg = new UserLoginActivityReport(driver);
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @description: To verify that Users are able to View Filtering options for Reviewer Time Online Report 
	 *                such as Reviewer Selection, Date Range & Assignment.
	 */
	@Test(description = "RPMXCON-48717", enabled = true, groups = { "regression" })
	public void verifyRTOreportGeneratedByFilteringOptions() throws InterruptedException, ParseException {
		bc.stepInfo("Test case Id: RPMXCON-48717"); 
		bc.stepInfo("To verify that Users are able to View Filtering options for Reviewer Time Online Report such as Reviewer Selection, Date Range & Assignment."); 
		// Login as Reviewer Manager
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		assgnmntPg = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);		
		String assignmentName =  "Assignment" + Utility.dynamicNameAppender();
		assgnmntPg.createAssignment(assignmentName, Input.codeFormName);			
		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.TallySearch);	
		sessionSearch.bulkAssignExisting(assignmentName);
		assgnmntPg.editAssignment(assignmentName);
		assgnmntPg.assignmentDistributingToReviewer();
		lp.logout();
		String fromDate = userLoginActivityRptPg.getCurrentUtcTime();
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		assgnmntPg.SelectAssignmentByReviewer(assignmentName);
		bc.waitTime(80); // This time is mandatory for this script to validate in time line reports validation
		lp.logout();
		String toDate = userLoginActivityRptPg.getCurrentUtcTime();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		reviewerTimeOnlineRptPg.navigateToReviewerTimeOnlineReport();
		reviewerTimeOnlineRptPg.generateRTOreport(assignmentName,fromDate,toDate, Input.rev1FullName);
		String totalTime=reviewerTimeOnlineRptPg.getColoumnValue(reviewerTimeOnlineRptPg.reviewerColumnNameHeader(), "Time Online",Input.rev1FullName);
		String actualAssignmentName=reviewerTimeOnlineRptPg.getColoumnValue(reviewerTimeOnlineRptPg.reviewerColumnNameHeader(), "Assignment Name",Input.rev1FullName);
		softAssertion.assertEquals(actualAssignmentName, assignmentName);
		softAssertion.assertAll();
		bc.passedStep("The selected assignment displayed in report successfully");
		System.out.println(totalTime);
		reviewerTimeOnlineRptPg.validateTime(totalTime, "min", "01");
		bc.passedStep("The report generated based on selected criteria successfully");
		assgnmntPg.deleteAssgnmntUsingPagination(assignmentName);
		lp.logout();		
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @description: To verify that Users are able to view Reviewer Time Online Report
	 */
	@Test(description = "RPMXCON-56290", enabled = true, groups = { "regression" })
	public void verifyUserAbleToGenerateReport() throws InterruptedException, ParseException {
		bc.stepInfo("Test case Id: RPMXCON-56290"); 
		bc.stepInfo("To verify that Users are able to view Reviewer Time Online Report"); 		
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.stepInfo("Successfully login as Reviewer Manager'" + Input.rmu1userName + "'");
		assgnmntPg = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);		
		String assignmentName =  "Assignment" + Utility.dynamicNameAppender();
		assgnmntPg.createAssignment(assignmentName, Input.codeFormName);			
		// search to Assignment creation
		sessionSearch.basicContentSearch(Input.TallySearch);	
		sessionSearch.bulkAssignExisting(assignmentName);
		assgnmntPg.editAssignment(assignmentName);
		assgnmntPg.assignmentDistributingToReviewer();
		lp.logout();
		String fromDate = userLoginActivityRptPg.getCurrentUtcTime();
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		assgnmntPg.SelectAssignmentByReviewer(assignmentName);
		bc.waitTime(80);// This time is mandatory for this script to validate in time line reports validation
		lp.logout();
		String toDate = userLoginActivityRptPg.getCurrentUtcTime();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		reviewerTimeOnlineRptPg.navigateToReviewerTimeOnlineReport();
		reviewerTimeOnlineRptPg.selectReviewer(Input.rev1FullName);
		reviewerTimeOnlineRptPg.selectDateRange(fromDate,toDate);
		reviewerTimeOnlineRptPg.applyChanges();	
		String totalTime=reviewerTimeOnlineRptPg.getColoumnValue(reviewerTimeOnlineRptPg.reviewerColumnNameHeader(), "Time Online",Input.rev1FullName);
		System.out.println(totalTime);
		reviewerTimeOnlineRptPg.validateTime(totalTime, "min", "01");
		bc.passedStep("The report is generated without assignment as expected");
		lp.logout();		
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
		bc.stepInfo("***Executed ReviewerTimeOnlineReport2_1****");	  

	}
}
