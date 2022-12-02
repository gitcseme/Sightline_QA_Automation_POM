package testScriptsRegressionSprint26;

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
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BasicSearchRegression_26 {

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

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that Application is not displaying warning message when
	 *              white-space character (Subtraction mark -) embedded within a
	 *              Regular Expression query.RPMXCON-61610
	 */
	@Test(description = "RPMXCON-61610", enabled = true, groups = { "regression" })
	public void verifyApplicationNotDisplayingWarningMessageSubtractionMarkEmbeddedWithinRegularExpressionQuery() {

		String searchString = "\"##U-C Tester\"";
		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-61610 Basic Search");
		base.stepInfo(
				"Verify that Application is not displaying warning message when white-space character (Subtraction mark -) embedded within a Regular Expression query.");

		// configuring the search
		base.stepInfo("Navigating to Basic search page");
		session.basicContentDraftSearch(searchString);
		base.stepInfo("Configuring the Search with given search Query.");

		// Verify that Subtraction mark - is treating as whitespace and it returns pure
		// hit count on Basic search screen.
		base.stepInfo("Performing Search Action");
		int purehit = session.searchAndReturnPureHit_BS();
		base.passedStep(
				"Verified that Subtraction mark - is treating as whitespace and it returns pure hit count on Basic search screen.");

		// verify returning documents having word U and C Tester
		session.addNewSearch();
		int purehit2 = session.multipleBasicContentSearch("\"U&C Tester\"");
		base.digitCompareEquals(purehit, purehit2,
				"it is returning all documents  having word mentioned 'U and C Tester'",
				"returnig Document is not as expected");

		// logOut
		login.logout();
	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that Application is not displaying warning message when
	 *              white-space character (asterisk mark *) embedded within a
	 *              Regular Expression query.RPMXCON-61611
	 */
	@Test(description = "RPMXCON-61611", enabled = true, groups = { "regression" })
	public void verifyApplicationNotDisplayingWarningMessageAsteriskMarkEmbeddedWithinRegularExpressionQuery() {

		String searchString = "\"##U\\*C Tester\"";
		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-61611 Basic Search");
		base.stepInfo(
				"Verify that Application is not displaying warning message when white-space character (asterisk mark *) embedded within a Regular Expression query.");

		// configuring the search
		base.stepInfo("Navigating to Basic search page");
		session.basicContentDraftSearch(searchString);
		base.stepInfo("Configuring the Search with given search Query.");

		// Verify that asterisk mark * is treating as whitespace and it returns pure hit
		// count on Basic search screen.
		base.stepInfo("Performing Search Action");
		int purehit = session.searchAndReturnPureHit_BS();
		base.passedStep(
				"Verified that asterisk mark * is treating as whitespace and it returns pure hit count on Basic search screen.");

		// verify returning documents having word U and C Tester
		session.addNewSearch();
		int purehit2 = session.multipleBasicContentSearch("\"U&C Tester\"");
		base.digitCompareEquals(purehit, purehit2,
				"it is returning all documents  having word mentioned 'U and C Tester'",
				"returnig Document is not as expected");

		// logOut
		login.logout();
	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that Application is not displaying warning message when
	 *              white-space character (dollar $) embedded within a Regular
	 *              Expression query.RPMXCON-61600
	 */
	@Test(description = "RPMXCON-61600", enabled = true, groups = { "regression" })
	public void verifyApplicationNotDisplayingWarningMessagedollarEmbeddedWithinRegularExpressionQuery() {

		String searchString = "\"##U\\$C Tester\"";
		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-61600 Basic Search");
		base.stepInfo(
				"Verify that Application is not displaying warning message when white-space character (dollar $) embedded within a Regular Expression query.");

		// configuring the search
		base.stepInfo("Navigating to Basic search page");
		session.basicContentDraftSearch(searchString);
		base.stepInfo("Configuring the Search with given search Query.");

		// Verify that dollar $ is treating as whitespace and it returns pure hit count
		// on Basic search screen.
		base.stepInfo("Performing Search Action");
		int purehit = session.searchAndReturnPureHit_BS();
		base.passedStep(
				"Verified that dollar $ is treating as whitespace and it returns pure hit count on Basic search screen.");

		// verify returning documents having word U and C Tester
		session.addNewSearch();
		int purehit2 = session.multipleBasicContentSearch("\"U&C Tester\"");
		base.digitCompareEquals(purehit, purehit2,
				"it is returning all documents  having word mentioned 'U and C Tester'",
				"returnig Document is not as expected");

		// logOut
		login.logout();
	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that Application is not displaying warning message when
	 *              white-space character (space) embedded within a Regular
	 *              Expression query.RPMXCON-61595
	 */
	@Test(description = "RPMXCON-61595", enabled = true, groups = { "regression" })
	public void verifyApplicationNotDisplayingWarningMessageSpaceEmbeddedWithinRegularExpressionQuery() {

		String searchString = "\"##U\\ C Tester\"";
		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-61595 Basic Search");
		base.stepInfo(
				"Verify that Application is not displaying warning message when white-space character (space) embedded within a Regular Expression query.");

		// configuring the search
		base.stepInfo("Navigating to Basic search page");
		session.basicContentDraftSearch(searchString);
		base.stepInfo("Configuring the Search with given search Query.");

		// Verify that Space is treating as whitespace and it returns pure hit count on
		// Basic search screen.
		base.stepInfo("Performing Search Action");
		int purehit = session.searchAndReturnPureHit_BS();
		base.passedStep(
				"Verified that Space is treating as whitespace and it returns pure hit count on Basic search screen.");

		// verify returning documents having word U and C Tester
		session.addNewSearch();
		int purehit2 = session.multipleBasicContentSearch("\"U&C Tester\"");
		base.digitCompareEquals(purehit, purehit2,
				"it is returning all documents  having word mentioned 'U and C Tester'",
				"returnig Document is not as expected");

		// logOut
		login.logout();
	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that Application is not displaying warning message when
	 *              white-space character (colon mark : ) embedded within a Regular
	 *              Expression query.RPMXCON-61594
	 */
	@Test(description = "RPMXCON-61594", enabled = true, groups = { "regression" })
	public void verifyApplicationNotDisplayingWarningMessageColonMarkEmbeddedWithinRegularExpressionQuery() {

		String searchString = "\"##U\\:C Tester\"";
		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-61594 Basic Search");
		base.stepInfo(
				"Verify that Application is not displaying warning message when white-space character (colon mark : ) embedded within a Regular Expression query.");

		// configuring the search
		base.stepInfo("Navigating to Basic search page");
		session.basicContentDraftSearch(searchString);
		base.stepInfo("Configuring the Search with given search Query.");

		// Verify that colon mark : is treating as whitespace and it returns pure hit
		// count on Basic search screen.
		base.stepInfo("Performing Search Action");
		int purehit = session.searchAndReturnPureHit_BS();
		base.passedStep(
				"Verified that colon mark : is treating as whitespace and it returns pure hit count on Basic search screen.");

		// verify returning documents having word U and C Tester
		session.addNewSearch();
		int purehit2 = session.multipleBasicContentSearch("\"U&C Tester\"");
		base.digitCompareEquals(purehit, purehit2,
				"it is returning all documents  having word mentioned 'U and C Tester'",
				"returnig Document is not as expected");

		// logOut
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
//								login.clearBrowserCache();
		} catch (Exception e) {
			// no session avilable

		}
	}
}
