package sightline.basicSearch;

import java.awt.AWTException;
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

public class BasicSearchRegression_21_25 {

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
	
	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that Application is not displaying warning message when
	 *              white-space character (dot mark .) embedded within a Regular
	 *              Expression query.RPMXCON-61612
	 */

	@Test(description = "RPMXCON-61612", enabled = true, groups = { "regression" })
	public void verifyApplicationNotDisplayingWarningMessageDotMarkEmbeddedWithinRegularExpressionQuery() {

		String searchString = "\"##U\\.C Tester\"";
		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-61612 Basic Search");
		base.stepInfo(
				"Verify that Application is not displaying warning message when white-space character (dot mark .) embedded within a Regular Expression query.");

		// configuring the search
		base.stepInfo("Navigating to Basic search page");
		session.basicContentDraftSearch(searchString);
		base.stepInfo("Configuring the Search with given search Query.");

		// Verify that dot mark . is treating as whitespace and it returns pure hit
		// count on Basic search screen.
		base.stepInfo("Performing Search Action");
		session.searchAndReturnPureHit_BS();
		base.passedStep(
				"Verified that dot mark . is treating as whitespace and it returns pure hit count on Basic search screen.");

		// logOut
		login.logout();
	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that Application is not displaying warning message when
	 *              white-space character (forward slash mark /) embedded within a
	 *              Regular Expression query.RPMXCON-61613
	 */

	@Test(description = "RPMXCON-61613", enabled = true, groups = { "regression" })
	public void verifyApplicationNotDisplayingWarningMessageForwardSlashMarkEmbeddedWithinRegularExpressionQuery() {

		String searchString = "\"##U\\/C Tester\"";
		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-61613 Basic Search");
		base.stepInfo(
				"Verify that Application is not displaying warning message when white-space character (forward slash mark /) embedded within a Regular Expression query.");

		// configuring the search
		base.stepInfo("Navigating to Basic search page");
		session.basicContentDraftSearch(searchString);
		base.stepInfo("Configuring the Search with given search Query.");

		// Verify that forward slash mark / is treating as whitespace and it returns
		// pure hit count on Basic search screen.
		base.stepInfo("Performing Search Action");
		session.searchAndReturnPureHit_BS();
		base.passedStep(
				"Verified that forward slash mark / is treating as whitespace and it returns pure hit count on Basic search screen.");

		// logOut
		login.logout();
	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that Application is not displaying warning message when
	 *              white-space character (right square symbol ]) embedded within a
	 *              Regular Expression query.RPMXCON-61615
	 */

	@Test(description = "RPMXCON-61615", enabled = true, groups = { "regression" })
	public void verifyApplicationNotDisplayingWarningMessageRightSquareSymbolEmbeddedWithinRegularExpressionQuery() {

		String searchString = "\"##U\\]C Tester\"";
		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-61615 Basic Search");
		base.stepInfo(
				"Verify that Application is not displaying warning message when white-space character (right square  symbol ]) embedded within a Regular Expression query.");

		// configuring the search
		base.stepInfo("Navigating to Basic search page");
		session.basicContentDraftSearch(searchString);
		base.stepInfo("Configuring the Search with given search Query.");

		// Verify that right square symbol ] is treating as whitespace and it returns
		// pure hit count on Basic search screen.
		base.stepInfo("Performing Search Action");
		session.searchAndReturnPureHit_BS();
		base.passedStep(
				"Verified that right square symbol ] is treating as whitespace and it returns pure hit count on Basic search screen.");

		// logOut
		login.logout();
	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that Application is not displaying warning message when
	 *              white-space character (left square symbol [) embedded within a
	 *              Regular Expression query.RPMXCON-61616
	 */

	@Test(description = "RPMXCON-61616", enabled = true, groups = { "regression" })
	public void verifyApplicationNotDisplayingWarningMessageLeftSquareSymbolEmbeddedWithinRegularExpressionQuery() {

		String searchString = "\"##U\\[C Tester\"";
		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-61616 Basic Search");
		base.stepInfo(
				"Verify that Application is not displaying warning message when white-space character (left square  symbol [) embedded within a Regular Expression query.");

		// configuring the search
		base.stepInfo("Navigating to Basic search page");
		session.basicContentDraftSearch(searchString);
		base.stepInfo("Configuring the Search with given search Query.");

		// Verify that left square symbol [ is treating as whitespace and it returns
		// pure hit count on Basic search screen.
		base.stepInfo("Performing Search Action");
		session.searchAndReturnPureHit_BS();
		base.passedStep(
				"Verified that left square symbol [ is treating as whitespace and it returns pure hit count on Basic search screen.");

		// logOut
		login.logout();
	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that Application is not displaying warning message when
	 *              white-space character (less than symbol <) embedded within a
	 *              Regular Expression query. RPMXCON-61617
	 */

	@Test(description = "RPMXCON-61617", enabled = true, groups = { "regression" })
	public void verifyApplicationNotDisplayingWarningMessageLessThanSymbolEmbeddedWithinRegularExpressionQuery() {

		String searchString = "\"##U\\<C Tester\"";
		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-61617 Basic Search");
		base.stepInfo(
				"Verify that Application is not displaying warning message when white-space character (less than symbol <) embedded within a Regular Expression query.");

		// configuring the search
		base.stepInfo("Navigating to Basic search page");
		session.basicContentDraftSearch(searchString);
		base.stepInfo("Configuring the Search with given search Query.");

		// Verify that less than symbol < is treating as whitespace and it returns pure
		// hit count on Basic search screen.
		base.stepInfo("Performing Search Action");
		session.searchAndReturnPureHit_BS();
		base.passedStep(
				"Verified that less than symbol < is treating as whitespace and it returns pure hit count on Basic search screen.");

		// logOut
		login.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:15/11/2022 RPMXCON-57116
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that application displays warning A message on Advanced
	 *              Search if user enters "2009-09-20" without wrapper quotations.
	 */
	@Test(description = "RPMXCON-57116", enabled = true, groups = { "regression" })
	public void verifyApplicationDisplayWarningMsg() throws InterruptedException, AWTException {

		base.stepInfo("Test case Id: RPMXCON-57116");
		base.stepInfo(
				"Verify that application displays warning A message on Advanced Search if user enters \"2009-09-20\" without wrapper quotations.");

		SessionSearch sessionSearch = new SessionSearch(driver);
		String searchTerm = "2009-09-20";

		// Login As PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("User successfully logged into slightline webpage  PAU as with " + Input.pa1userName + "");

		driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.waitForPageToBeReady();
		base.stepInfo("Go to Search page enter the Searchterm");
		sessionSearch.getEnterSearchString().SendKeys(searchTerm);
		// Click on Search button
		sessionSearch.getSearchButton().waitAndClick(10);
		String actualMsg = sessionSearch.getWarningMsg().getText();
		System.out.println(actualMsg);
		base.stepInfo(actualMsg);
		String expectedMsg = "Your query contains an argument that may be intended to look for dates within any body content or metadata attribute. Please be advised that the search engine interprets dash - or slash / characters in non-fielded arguments as implied OR statement. For example, 1980/01/02 is treated by the search engine as 1980 OR 01 OR 02. The same is true of 1980-01-02. You can add quotation marks around a query to treat the dash or slash argument as a string. For example, \"1980/01/02\" is treated as is. The same is true of \"1980-01-02\".";
		driver.waitForPageToBeReady();
		if (actualMsg.contains(expectedMsg)) {
			base.passedStep("Observe that application warning message displayed successfully");
		} else {
			base.failedStep("No such message display");
		}

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
