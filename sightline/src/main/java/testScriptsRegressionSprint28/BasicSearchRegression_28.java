package testScriptsRegressionSprint28;

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
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BasicSearchRegression_28 {

	Driver driver;
	LoginPage login;
	SavedSearch saveSearch;
	SessionSearch session;
	BaseClass base;
	SoftAssert softAssertion;
	Input in;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		in = new Input();
		in.loadEnvConfig();
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

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

	/**
	 * @author Jeevitha
	 * @Description : Verify that Application is not displaying warning message when
	 *              white-space character (exclamation mark ! ) embedded within a
	 *              Regular Expression query.[RPMXCON-61593]
	 */
	@Test(description = "RPMXCON-61593", enabled = true, groups = { "regression" })
	public void verifyApplicationWithExclamationMark() {

		String searchString = "\"##U\\!C Tester\"";
		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-61593 Basic Search");
		base.stepInfo(
				"Verify that Application is not displaying warning message when white-space character (exclamation mark ! ) embedded within Regular Expression query.");

		// configure search with exclamation mark & get purehit count
		base.stepInfo("Navigating to Basic search page");
		int purehit = session.basicContentSearch(searchString);
		base.stepInfo("Configuring the Search with given search Query. : " + searchString);

		// verify exclamation mark ! should treat as whitespace and it should return all
		// documents having word mentioned "U and C Tester "
		session.addNewSearch();
		int purehit2 = session.multipleBasicContentSearch("\"U&C Tester\"");
		base.digitCompareEquals(purehit, purehit2,
				"Query is returning all documents  having word mentioned 'U and C Tester'",
				"returnig Document is not as expected");

		// logOut
		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @Description : Verify that Application is not displaying warning message when
	 *              white-space character (ampersand & ) embedded within a Regular
	 *              Expression query.[RPMXCON-61591]
	 */
	@Test(description = "RPMXCON-61591", enabled = true, groups = { "regression" })
	public void verifyApplicationWithAmpersand() {

		String searchString = "\"##U\\&C Tester\"";
		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-61591 Basic Search");
		base.stepInfo(
				"Verify that Application is not displaying warning message when white-space character (ampersand & ) embedded within a Regular Expression query.");

		// configure search with ampersand & get purehit count
		base.stepInfo("Navigating to Basic search page");
		int purehit = session.basicContentSearch(searchString);
		base.stepInfo("Configuring the Search with given search Query. : " + searchString);

		// verify ampersand & should treat as whitespace and it should return all
		// documents having word mentioned "U and C Tester "
		session.addNewSearch();
		int purehit2 = session.multipleBasicContentSearch("\"U&C Tester\"");
		base.digitCompareEquals(purehit, purehit2,
				"Query is returning all documents  having word mentioned 'U and C Tester'",
				"returnig Document is not as expected");

		// logout
		login.logout();
	}

	@DataProvider(name = "SearchQueryWithSpace")
	public Object[][] SearchQueryWithSpace() {
		return new Object[][] { { "“stock investment” ~5", "7" }, { "\"stock investment\" ~5", "7" },
				{ "“stock investment” ~5", "7" }, { "\"stock investment\" ~5 OR stock", "8" } };
	}

	/**
	 * @author Jeevitha
	 * @Description : Verify that result appears for proximity search having spaces
	 *              between the proximity query in Basic Search Query
	 *              Screen.[RPMXCON-57288]
	 */
	@Test(description = "RPMXCON-57288", dataProvider = "SearchQueryWithSpace", enabled = true, groups = {
			"regression" })
	public void verifyResultAppearsForProximitySearchWithSpace(String searchString, String warningMsg) {

		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-57288 Basic Search");
		base.stepInfo(
				"Verify that result appears for proximity search having spaces between the proximity query in Basic Search Query Screen.");

		// configure search & fetch Query inserted in search box
		base.stepInfo("Navigating to Basic search page");
		session.basicContentDraftSearch(searchString);
		driver.waitForPageToBeReady();
		base.hitEnterKey(1);
		base.waitForElement(session.getQueryFromTextBox());
		String configuredquery = session.getQueryFromTextBox().getText();
		base.stepInfo("Configuring the Search with given search Query. : " + configuredquery);

		// verify expected warning message is displayed
		session.verifyWarningMessage(true, true, Integer.parseInt(warningMsg));
		session.getYesQueryAlert().waitAndClick(10);

		// Verify that result appears for proximity search having spaces between the
		// proximity query in Basic Search Query Screen.
		int purehit = session.returnPurehitCount();
		base.waitTime(2);
		base.compareTextViaContains(configuredquery, " ",
				"Result displayed for search having spaces between the proximity query",
				"Displayed Result query is not as expecting");

		// logout
		login.logout();
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
			login.quitBrowser();
		} catch (Exception e) {
			login.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		System.out.println("**Executed  Assignment_Regression_sprint23 .**");
	}
}
