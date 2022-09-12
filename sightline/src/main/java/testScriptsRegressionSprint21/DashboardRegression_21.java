package testScriptsRegressionSprint21;

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

public class DashboardRegression_21 {

	Driver driver;
	LoginPage login;
	BaseClass base;
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
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());

		driver = new Driver();
		base = new BaseClass(driver);
		login = new LoginPage(driver);
		dashBoard = new Dashboard(driver);
		session = new SessionSearch(driver);

	}

	/**
	 * @Author Jeevitha
	 * @Description : To verify that RMU is able to add the Total Review Progress
	 *              widget on Dashboard [RPMXCON-54177]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54177", enabled = true, groups = { "regression" })
	public void verifyTotalReviewWidgetAdded() throws Exception {
		String widgetName = "Total Review Progress";

		// login As RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		base.stepInfo("Test case Id:RPMXCON-54177 Dashboard");
		base.stepInfo("To verify that RMU is able to add the Total Review Progress widget on Dashboard");

		// Add Total review Progress Widget
		dashBoard.AddNewWidgetToDashboard(widgetName);

		// verify whether widget added in dashboard
		driver.waitForPageToBeReady();
		base.ValidateElement_Presence(dashBoard.DashboardWidgetHeader(widgetName), widgetName + " Widget");
		base.passedStep(widgetName + " is displayed in Dashboard");

		// logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : To verify that RMU can Add the TRP widget and it will be
	 *              maintained through the session [RPMXCON-54185]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54185", enabled = true, groups = { "regression" })
	public void verifyWidgetIsMaintained() throws Exception {
		String widgetName = "Total Review Progress";

		// login As RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		base.stepInfo("Test case Id:RPMXCON-54185 Dashboard");
		base.stepInfo("To verify that RMU can Add the TRP widget and it will be maintained through the session");

		// verify whether widget added in dashboard
		dashBoard.navigateToDashboard();
		driver.waitForPageToBeReady();
		boolean widgetStatus = base.ValidateElement_PresenceReturn(dashBoard.DashboardWidgetHeader(widgetName));

		// Add Total review Progress Widget
		if (!widgetStatus) {
			dashBoard.AddNewWidgetToDashboard(widgetName);
		} else {
			base.stepInfo(widgetName + " : Widget is Present in Dashboard");
		}

		List<String> widgetsBefore = base.availableListofElements(dashBoard.countOfWidget());

		// navigate to another page
		session.navigateToSessionSearchPageURL();
		base.stepInfo("Navigated to SessionSearch Page");

		// navigate back to dashboard page
		dashBoard.navigateToDashboard();
		base.stepInfo("Navigated Back to Dashboard Page");

		// verify all the widgets are maintained
		driver.waitForPageToBeReady();
		List<String> widgetsAfterNavigate = base.availableListofElements(dashBoard.countOfWidget());
		String passMsg = "All previous Widgets are Maintained : " + widgetsAfterNavigate;
		String failMsg = "Widgets are not Maintained";
		base.listCompareEquals(widgetsAfterNavigate, widgetsBefore, passMsg, failMsg);

		// logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : To verify that on select widget in Widgets Library, RMU is
	 *              displayed with preview window selected widget. [RPMXCON-54181]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-54181", enabled = true, groups = { "regression" })
	public void verifyPreviewWindowDisplayed() throws Exception {

		// login As RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		base.stepInfo("Test case Id:RPMXCON-54181 Dashboard");
		base.stepInfo(
				"To verify that on select widget in Widgets Library, RMU is displayed with preview window selected widget.");

		// Click wheel icon & then Click add new btn & verify buttons & Popup.
		dashBoard.ClickWheelIconAndVerify(true);

		// verify respective widget Preview pop up is displayed.
		dashBoard.clickWidgetAndVerifyPopUp("", true, false);

		// logout
		login.logout();
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
		String widgetName = "Reviewer Productivity";
		String headerName = "TOP 6 REVIEWERS WITH LOWEST PRODUCTIVITY";

		// login As RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		base.stepInfo("Test case Id:RPMXCON-54189 Dashboard");
		base.stepInfo(
				"To verify that RPR widget by default it will display \"Top 6 Reviewers with Lowest Productivity\" when added first time on Dashboard by RMU.");

		// Add Reviewer Productivity Widget
		dashBoard.AddNewWidgetToDashboard(widgetName);

		// verify whether widget added in dashboard
		driver.waitForPageToBeReady();
		base.ValidateElement_Presence(dashBoard.DashboardWidgetHeader(widgetName), widgetName + " Widget");
		base.passedStep(widgetName + " is displayed in Dashboard");

		// verify top 6 reviewers table is displayed
		List<String> reviewProgressTable = base.availableListofElements(dashBoard.getReviewerProdTop6Header());
		base.compareListWithString(reviewProgressTable, headerName,
				headerName + " : Review Progress is Displayed As default", "Review Progress is Not Displayed");

		// logout
		login.logout();
	}

	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
		}
		try {
			login.quitBrowser();
		} catch (Exception e) {
			login.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR DOCVIEW EXECUTED******");
	}

}
