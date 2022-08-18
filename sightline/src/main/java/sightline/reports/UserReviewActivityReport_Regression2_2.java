package sightline.reports;

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
import pageFactory.CommunicationExplorerPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.UserReviewActivityReport;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class UserReviewActivityReport_Regression2_2 {
	Driver driver;
	LoginPage lp;
	SoftAssert softAssertion;
	BaseClass bc;
	UserReviewActivityReport userActivityRptPg;
	AssignmentsPage agnmt;
	SessionSearch search;
	DocViewPage docview;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();

	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description: Verify User Review Activity Report
	 */
	@Test(description = "RPMXCON-56941",groups = {"regression" },enabled = true)
	public void verifyUserReviewActivityReport()
			throws InterruptedException, ParseException {
		bc.stepInfo("Test case Id: RPMXCON-56941");
		bc.stepInfo("Verify User Review Activity Report");
		String assignmentName = "assignment" + Utility.dynamicNameAppender();
		agnmt = new AssignmentsPage(driver);
		search =new SessionSearch(driver);
		docview = new DocViewPage(driver);
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		String initialUtcTime =userActivityRptPg.getCurrentUtcTime();
		agnmt.createAssignment(assignmentName, Input.codeFormName);
		search.basicContentSearch(Input.TallySearch);
		search.bulkAssignExisting(assignmentName);
		bc.selectproject();
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		agnmt.assignmentDistributingToReviewer();
		lp.logout();
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		agnmt.SelectAssignmentByReviewer(assignmentName);
		driver.waitForPageToBeReady();
		bc.waitForElement(docview.getDocView_CurrentDocId());
		String completedDocId = docview.getDocView_CurrentDocId().getText();
		docview.editCodingForm("test");
		bc.waitTillElemetToBeClickable(agnmt.completeBtn());
		agnmt.completeBtn().waitAndClick(10);
		String actualCompltedTime = userActivityRptPg.getCurrentUtcTime();
		bc.waitForElement(docview.getDocView_CurrentDocId());
		bc.waitTime(3);
		String viewedDocId = docview.getDocView_CurrentDocId().getText();
		String expectedViewedDocTime = userActivityRptPg.getCurrentUtcTime();
		lp.logout();
		String finalUtcTime =userActivityRptPg.getCurrentUtcTime();
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		userActivityRptPg.navigateToUserReviewActivityReport();
		userActivityRptPg.generateUserReviewActivityReport(Input.rev1FullName, initialUtcTime, finalUtcTime);
		userActivityRptPg.validateStatusOfUser(completedDocId, " Completed");
		String expectedCompletedTime = userActivityRptPg.getDateFromReportsPage(completedDocId, " Completed");
		userActivityRptPg.validateDate(actualCompltedTime, expectedCompletedTime);
		userActivityRptPg.validateStatusOfUser(viewedDocId, "Viewed");
		String actualViewedDocTime = userActivityRptPg.getDateFromReportsPage(viewedDocId, "Viewed");
		userActivityRptPg.validateDate(actualViewedDocTime, expectedViewedDocTime);
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
		userActivityRptPg = new UserReviewActivityReport(driver);
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
//				{ Input.pa1userName, Input.pa1password, "PA" } 
				};
		return users;
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		
		bc.stepInfo("***Executed Communications Explorer_Regression1****");
		  

	}
}
