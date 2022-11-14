package testScriptsRegressionSprint25;

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
import pageFactory.ABMReportPage;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CommunicationExplorerPage;
import pageFactory.ConceptExplorerPage;
import pageFactory.CustomDocumentDataReport;
import pageFactory.LoginPage;
import pageFactory.ReportsPage;
import pageFactory.SavedSearch;
import pageFactory.SearchTermReportPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ABMReportRegression_25 {

	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	BaseClass baseClass;
	Input in;
	SoftAssert assertion;
	ReportsPage reports;
	ABMReportPage AbmReportPage;
	AssignmentsPage assignmentsPage;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		in = new Input();
		in.loadEnvConfig();
	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();

		sessionSearch = new SessionSearch(driver);
		reports = new ReportsPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		assertion = new SoftAssert();
		AbmReportPage = new ABMReportPage(driver);
		assignmentsPage = new AssignmentsPage(driver);
	}

	/**
	 * @author Jeevitha.R
	 * @throws AWTException
	 * @Description : Verify ABM Assignment level Report [RPMXCON-56706]
	 */
	@Test(description = "RPMXCON-56706", groups = { "regression" }, enabled = true)
	public void verifyAbmAssignmentLevelReport() throws InterruptedException, ParseException, AWTException {
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id:RPMXCON-56706 Reports/Advanced Batch Management");
		baseClass.stepInfo("Verify ABM Assignment level Report");

		// Login
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// perform Bulk Assign
		int purehit = sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();

		// create Assignment
		assignmentsPage.FinalizeAssignmentAfterBulkAssign();
		assignmentsPage.createAssignment_fromAssignUnassignPopup(assignmentName, Input.codeFormName);
		assignmentsPage.getAssignmentSaveButton().waitAndClick(5);
		baseClass.stepInfo("Created a assignment " + assignmentName);

		// Navigate to Advance batch managemnet page
		reports.navigateToReportsPage("Advanced Batch Management Report");

		// Select source as assignment
		AbmReportPage.selectSources("Assignments", assignmentName);

		// Select Reviewers
		AbmReportPage.selectReviewers(true, true, "");

		// Select Assignments
		AbmReportPage.selectAssignment(true, assignmentName, null);

		// Click Apply Changes Button & verify Report generated with selected criteria.F
		AbmReportPage.verifyReportGenerated(true, true, purehit, false, null);

		// logout
		loginPage.logout();
	}

	/**
	 * @author Jeevitha.R
	 * @throws AWTException
	 * @Description : Validate reviewer selection is not impacted after performing
	 *              any action on ABM report [RPMXCON-56814]
	 */
	@Test(description = "RPMXCON-56814", groups = { "regression" }, enabled = true)
	public void verifyReviewerSelectionISNotImpacted() throws InterruptedException, ParseException, AWTException {
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id:RPMXCON-56814 Reports/Advanced Batch Management");
		baseClass.stepInfo("Validate reviewer selection is not impacted after performing any action on ABM report");

		// Login
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// perform Bulk Assign
		int purehit = sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();

		// create Assignment
		assignmentsPage.FinalizeAssignmentAfterBulkAssign();
		assignmentsPage.createAssignment_fromAssignUnassignPopup(assignmentName, Input.codeFormName);
		assignmentsPage.getAssignmentSaveButton().waitAndClick(5);
		baseClass.stepInfo("Created a assignment " + assignmentName);

		// Navigate to Advance batch managemnet page
		reports.navigateToReportsPage("Advanced Batch Management Report");

		// Select source as assignment
		AbmReportPage.selectSources("Assignments", assignmentName);

		// Select ALL Reviewers
		AbmReportPage.selectReviewers(true, false, "");

		// Select Assignments
		AbmReportPage.selectAssignment(true, assignmentName, null);

		// genearte Report & verify Report generated with selected criteria &
		// verify if selected reviewers & assignments are not impacted
		AbmReportPage.verifyReportGenerated(true, false, 0, true, assignmentName);

		// logout
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
		System.out.println("**Executed Communication explorer sprint 21**");
	}
}
