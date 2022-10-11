package sightline.basicSearch;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

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
import pageFactory.BaseClass;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BasicSearchRegression_21 {

	Driver driver;
	LoginPage login;
	SavedSearch saveSearch;
	SessionSearch session;
	BaseClass base;
	SoftAssert softAssertion;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		Input in = new Input();
		in.loadEnvConfig();

	}

	@DataProvider(name = "data")
	public Object[][] data() {
		return new Object[][] { { "\"\"development methodology\" \"money related\"\"~5" },
				{ "“”development methodology” “money related””~5" },
				{ "\"\"development methodology” “money related””~5" }, };
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that result appears for proximity having 2 Phrases in
	 *              Basic Search Query Screen. - Metadata[RPMXCON-57332]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57332", enabled = true, dataProvider = "data", groups = { "regression" })
	public void verifyResultForTwoProxiPhrases(String data) throws InterruptedException {

		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-57332 Basic Search");
		base.stepInfo(
				"Verify that result appears for proximity having 2 Phrases in Basic Search Query Screen. - Metadata");

		// Configure Query
		session.basicContentDraftSearch(data);
		base.stepInfo("Configured Query is : " + data);

		// verify Warning message
		session.verifyWarningMessage(true, true, 2);
		session.tallyContinue(5);

		login.logout();
	}

	@DataProvider(name = "data2")
	public Object[][] data2() {
		return new Object[][] { { "Precision AND ProximitySearch Truthful”~5" },
				{ "Precision AND ProximitySearch Truthful\"~5" }, { "Precision AND ProximitySearch Truthful”~5" },
				{ "“Truthful Balance” OR ProximitySearch Truthful ” ~5" },
				{ "“Truthful Balance” OR ProximitySearch Truthful” ~5" },
				{ "\"Truthful Balance\" OR ProximitySearch Truthful \" ~5" },
				{ "\"Truthful Balance\" OR ProximitySearch Truthful\" ~5" },
				{ "“Truthful Balance” OR ProximitySearch Truthful” ~5" },
				{ "“Truthful Balance” OR ProximitySearch Truthful ” ~5" },
				{ "Precision AND ProximitySearch Truthful\" ~5" },
				{ "“Truthful Balance” OR ProximitySearch Truthful ” ~5" }, };
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that belly band message appears when user configured
	 *              Proximity with Right double quotes only and combined with other
	 *              criteria in Basic Search Query Screen.[RPMXCON-57298]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57298", enabled = true, dataProvider = "data2", groups = { "regression" })
	public void verifyMsgOfProxiWithRightDoubleQuotes(String data) throws InterruptedException {

		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-57298 Basic Search");
		base.stepInfo(
				"Verify that belly band message appears when user configured Proximity with Right double quotes only and combined with other criteria in Basic Search Query Screen.");

		// Configure Query
		session.basicContentDraftSearch(data);
		base.stepInfo("Configured Query is : " + data);

		// verify Warning message
		session.verifyWarningMessage(true, true, 3);
		session.tallyContinue(5);

		login.logout();
	}

	@DataProvider(name = "data3")
	public Object[][] data3() {
		return new Object[][] { { "Precision AND “ProximitySearch Truthful~5 " },
				{ "“Truthful Balance” AND Precision AND “ProximitySearch Truthful~5" },
				{ "Precision AND \"ProximitySearch Truthful~5" },
				{ "\"Truthful Balance\" AND Precision AND \"ProximitySearch Truthful~5" },
				{ "Precision AND “ProximitySearch Truthful~5" },
				{ "“Truthful Balance” AND Precision AND “ProximitySearch Truthful~5" },
				{ "Precision AND “ProximitySearch Truthful ~5 " },
				{ "“Truthful Balance” AND Precision AND “ProximitySearch Truthful ~5" }, };
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that belly band message appears when user configured
	 *              Proximity with Left double quotes only and combined with other
	 *              criteria in Basic Search Query Screen.[RPMXCON-57297]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57297", enabled = true, dataProvider = "data3", groups = { "regression" })
	public void verifyMsgOfProxiWithLeftDoubleQuotes(String data) throws InterruptedException {

		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-57297 Basic Search");
		base.stepInfo(
				"Verify that belly band message appears when user configured Proximity with Left double quotes only and combined with other criteria in Basic Search Query Screen.");

		// Configure Query
		session.basicContentDraftSearch(data);
		base.stepInfo("Configured Query is : " + data);

		// verify Warning message
		session.verifyWarningMessage(true, true, 3);
		session.tallyContinue(5);

		login.logout();
	}

	@DataProvider(name = "data4")
	public Object[][] data4() {
		return new Object[][] { { "stock investment”~5" }, { "stock investment\"~5" }, { "stock investment”~5" },
				{ "iterative methodology”" }, { "iterative methodology test*”" }, };
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that belly band message appears when user tries to run
	 *              search having Right double quote only in Basic Search Query
	 *              Screen.[RPMXCON-57284]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57284", enabled = true, dataProvider = "data4", groups = { "regression" })
	public void verifyMsgWithRightDoubleQuotes(String data) throws InterruptedException {

		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-57284 Basic Search");
		base.stepInfo(
				"Verify that belly band message appears when user tries to run search having Right double quote only in Basic Search Query Screen.");

		// Configure Query
		session.basicContentDraftSearch(data);
		base.stepInfo("Configured Query is : " + data);

		// verify Warning message
		session.verifyWarningMessage(true, true, 3);

		login.logout();
	}

	@DataProvider(name = "data5")
	public Object[][] data5() {
		return new Object[][] { { "“stock investment~5" }, { "\"stock investment~5" }, { "“stock investment~5" },
				{ "\"iterative methodology" }, { "iterative methodology\"" }, };
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that belly band message appears when user tries to run
	 *              search having left double quote only in Basic Search Query
	 *              Screen.[RPMXCON-57283]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57283", enabled = true, dataProvider = "data5", groups = { "regression" })
	public void verifyMsgWithLeftDoubleQuotes(String data) throws InterruptedException {

		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-57283 Basic Search");
		base.stepInfo(
				"Verify that belly band message appears when user tries to run search having left double quote only in Basic Search Query Screen.");

		// Configure Query
		session.basicContentDraftSearch(data);
		base.stepInfo("Configured Query is : " + data);

		// verify Warning message
		session.verifyWarningMessage(true, true, 3);

		login.logout();
	}

	@DataProvider(name = "data6")
	public Object[][] data6() {
		return new Object[][] { { "“stock investment~5”" }, { "\"stock investment~5\"" }, { "“stock investment~5”" }, };
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that result appears for proximity search having double
	 *              quote after the number in Basic Search Query
	 *              Screen.[RPMXCON-57286]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57286", enabled = true, dataProvider = "data6", groups = { "regression" })
	public void verifyResultForProxiWithDoubleQuotes(String data) throws InterruptedException {

		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-57286 Basic Search");
		base.stepInfo(
				"Verify that result appears for proximity search having double quote after the number in Basic Search Query Screen.");

		// Configure Query
		session.basicContentDraftSearch(data);
		base.stepInfo("Configured Query is : " + data);
		session.getSearchButtonSec().waitAndClick(10);

		// verify warning message is not displayed
		String expectedMsg = "Warning message is Not Displayed";
		boolean flag = session.getQueryAlertGetText().isElementAvailable(5);
		base.printResutInReport(flag, expectedMsg, "Warning message is displayed", "Fail");

		// verify Purehit
		session.returnPurehitCount();

		login.logout();
	}

	@DataProvider(name = "data7")
	public Object[][] data7() {
		return new Object[][] { { "“stock investment~5”" }, { "\"stock investment~5\"" }, };
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that Search result when user tries to run proximity
	 *              search having double quote after the number in Basic Search
	 *              Query Screen.[RPMXCON-57285]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57285", enabled = true, dataProvider = "data7", groups = { "regression" })
	public void verifyResultForProxiWithQuotes(String data) throws InterruptedException {

		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-57285 Basic Search");
		base.stepInfo(
				"Verify that Search result when user tries to run proximity search having double quote after the number in Basic Search Query Screen.");

		// Configure Query
		session.basicContentDraftSearch(data);
		base.stepInfo("Configured Query is : " + data);
		session.getSearchButtonSec().waitAndClick(10);

		// verify warning message is not displayed
		String expectedMsg = "Warning message is Not Displayed";
		boolean flag = session.getQueryAlertGetText().isElementAvailable(5);
		base.printResutInReport(flag, expectedMsg, "Warning message is displayed", "Fail");

		// verify Purehit
		session.returnPurehitCount();

		login.logout();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.logBefore(testMethod.getName());

		// Open browser
		driver = new Driver();
		base = new BaseClass(driver);
		login = new LoginPage(driver);
		softAssertion = new SoftAssert();
		session = new SessionSearch(driver);
		saveSearch = new SavedSearch(driver);
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		Reporter.setCurrentTestResult(result);
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			login.logoutWithoutAssert();
		}
		try {
			login.quitBrowser();
		} catch (Exception e) {
			login.quitBrowser();
		}
		System.out.println("Executed :" + result.getMethod().getMethodName());
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR Batch Redactions EXECUTED******");
		try {
//						login.clearBrowserCache();
		} catch (Exception e) {
			// no session avilable

		}
	}
}
