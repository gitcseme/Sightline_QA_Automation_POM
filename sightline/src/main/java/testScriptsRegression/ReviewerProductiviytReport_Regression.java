
package testScriptsRegression;

import java.awt.AWTException;
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
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ReviewerProductivityReportPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ReviewerProductiviytReport_Regression {
	Driver driver;
	LoginPage lp;
	BaseClass bc;
	ReviewerProductivityReportPage reviewerProdReport;
	DocViewPage docViewPage;
	SoftAssert	softAssertion;
	String assignmentName2 = "RevProd" + Utility.dynamicNameAppender();
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		Input in = new Input();
		in.loadEnvConfig();

	}
/**
 * @author Jayanthi.ganesan
 * @param username
 * @param password
 * @param role
 * @throws InterruptedException
 * @throws AWTException
 */
	@Test(dataProvider = "Users_PARMU", groups = { "regression" }, priority = 1)
	public void reviewerProdPageDisplay(String username, String password, String role)
			throws InterruptedException, AWTException {
		LoginPage lp = new LoginPage(driver);
		bc.stepInfo("Test case Id: RPMXCON-56335");
		bc.stepInfo("To verify that Users are able to view Reviewer Productivity Report page.");
		lp.loginToSightLine(username, password);
		driver.waitForPageToBeReady();
		bc.stepInfo("Logged in as -" + role);
		ReviewerProductivityReportPage reviewerProdReport = new ReviewerProductivityReportPage(driver);
		reviewerProdReport.navigateTOReviewerPodReportPage();
		lp.logout();
	}
	
	@Test(dataProvider = "Users_PARMU", groups = { "regression" }, priority = 2)
	public void verifyDistributedCount(String username, String password, String role)
			throws InterruptedException, AWTException {
		LoginPage lp = new LoginPage(driver);
		bc.stepInfo("Test case Id: RPMXCON-48733");
		bc.stepInfo("Verify the 'Distributed Docs Completed by This Reviewer' in the 'Reviewer Productivity Report'.");
		lp.loginToSightLine(username, password);
		driver.waitForPageToBeReady();
		bc.stepInfo("Logged in as -" + role);
		ReviewerProductivityReportPage rp = new ReviewerProductivityReportPage(driver);
		rp.navigateTOReviewerPodReportPage();
		rp.generateReport(null);
		rp.verifyColumnDisplay(rp.getTableHeader("Distributed Docs Completed by This Reviewer"),"Distributed Docs Completed by This Reviewer");
		lp.logout();
	}
	@Test(dataProvider = "Users_PARMU", groups = { "regression" }, priority = 3)
	public void verifyTotalDocsCount(String username, String password, String role)
			throws InterruptedException, AWTException {
		LoginPage lp = new LoginPage(driver);
	lp.loginToSightLine(username, password);
	UtilityLog.info("Logged in as User: " + Input.rmu1userName);
	bc.stepInfo("Test case Id:RPMXCON-48734");
	bc.stepInfo("Verify the 'Total Docs Completed by This Reviewer' in the 'Reviewer Productivity Report'");
	AssignmentsPage agnmt = new AssignmentsPage(driver);
	if(role=="RMU") {
	SessionSearch sessionsearch = new SessionSearch(driver);	
	sessionsearch.basicContentSearch(Input.testData1);
	sessionsearch.verifyPureHitsCount();
	sessionsearch.bulkAssign();
	bc.stepInfo("Search with text input for docs completed");
	// Creating Assignment from Basic search
	agnmt.assignmentCreation(assignmentName2, Input.codeFormName);
	bc.stepInfo("Doc is Assigned from basic Search and Assignment '" + assignmentName2 + "' is created Successfully");
	agnmt.editAssignmentUsingPaginationConcept(assignmentName2);
	driver.waitForPageToBeReady();
	bc.stepInfo(assignmentName2 + " assignment opened in edit mode");
	agnmt.addReviewerAndDistributeDocs();
	lp.logout();
	lp.loginToSightLine(Input.rev1userName, Input.rev1password);
	bc.stepInfo("Successfully login as Reviewer'" + Input.rev1userName + "'");
	// navigating from Dashboard to DocView
	DocViewPage docViewPage=new DocViewPage(driver);
	docViewPage.selectAssignmentfromDashborad(assignmentName2);
	bc.stepInfo("Doc is viewed in the docView Successfully");
	
	// Completing the 2 documents
	driver.waitForPageToBeReady();
	bc.waitTime(5);
	docViewPage.CompleteTheDocumentInMiniDocList(2);
	bc.stepInfo("Document completed successfully");
	lp.logout();
	lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);	
	}
	ReviewerProductivityReportPage rp = new ReviewerProductivityReportPage(driver);
	rp.navigateTOReviewerPodReportPage();
	rp.generateReport(assignmentName2);
	softAssertion = new SoftAssert();
	
	//checking the total docs count value in reviwer productivity page
	softAssertion.assertEquals("2",rp.verifyColumnValueDisplay(rp.getTableHeaders(),"TOTAL DOCS COMPLETED BY THIS REVIEWER"));
	softAssertion.assertAll();
	bc.passedStep("Sucessfully verified  the 'Total Docs Completed by This Reviewer' in the 'Reviewer Productivity Report'.");
	lp.logout();
	}
	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		bc = new BaseClass(driver);

	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		Reporter.setCurrentTestResult(result);
		LoginPage lp = new LoginPage(driver);
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
				{ Input.pa1userName, Input.pa1password, "PA" } };
		return users;
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		System.out.println("Executed :ReviewerProductiviytReport_Regression ");

	}
}
