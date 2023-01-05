package sightline.basicSearch;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
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
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BasicSearch_Phase2_Regression1 {

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
	//	Input in = new Input();
	//	in.loadEnvConfig();

	}

	@DataProvider(name = "reserveWords")
	public Object[][] dataMethod() {
		return new Object[][] { { Input.pa1userName, Input.pa1password, "(\"test test\"" },
				{ Input.rmu1userName, Input.rmu1password, "(\"test test\"" },
				{ Input.pa1userName, Input.pa1password, "\"test test\")" },
				{ Input.rmu1userName, Input.rmu1password, "\"test test\")" } };
	}

	@DataProvider(name = "Users")
	public Object[][] Users() {
		Object[][] users = { { Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password },
				{ Input.rev1userName, Input.rev1password } };
		return users;
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify belly band message appears if Parantheses is missing
	 *              for Pharse search in Basic Search Query screen (Warning message
	 *              40001000008)
	 * @throws InterruptedException
	 */

	@Test(description = "RPMXCON-57359", enabled = true, dataProvider = "reserveWords", groups = { "regression" })
	public void verifyBellyBandMsg(String username, String password, String data1) throws InterruptedException {

		// login as User
		login.loginToSightLine(username, password);
		base.stepInfo("RPMXCON-57359 Basic Search");
		base.stepInfo(
				"Verify belly band message appears if Parantheses is missing for Pharse search in Basic Search Query screen (Warning message 40001000008)");

		// Verify Expanded Query
		session.wrongQueryAlertBasicSaerch(data1, 12, "non fielded", null);
		base.waitForElement(session.getYesQueryAlert());
		session.getYesQueryAlert().waitAndClick(10);

		// verify session search Page
		session.verifySessionSearchPageUrl();

		softAssertion.assertAll();
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify result appears for phrases that are wrapped within
	 *              parenthesis
	 * @param username
	 * @param password
	 * @throws InterruptedException
	 */

	@Test(description = "RPMXCON-57347", enabled = true, dataProvider = "Users", groups = { "regression" })

	public void verifyWrappedParenthesis(String username, String password) throws InterruptedException {
		String search = "(\"that this\")";

		// login as Users
		login.loginToSightLine(username, password);
		base.stepInfo("RPMXCON-57347 Basic Search");
		base.stepInfo("Verify result appears for phrases that are wrapped within parenthesis");

		// Verify Expanded Query
		session.basicContentSearch(search);

		softAssertion.assertAll();
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify result appears for phrases that are wrapped within
	 *              parenthesis
	 * @param username
	 * @param password
	 * @throws InterruptedException
	 */

	@Test(description = "RPMXCON-57345",enabled = true, dataProvider = "Users", groups = { "regression" })
	public void verifyPhraseInBasicSearch(String username, String password) throws InterruptedException {
		String search = "\"Government Agency Correspondance\"";

		// login as Users
		login.loginToSightLine(username, password);
		base.stepInfo("RPMXCON-57345 Basic Search");
		base.stepInfo("Verify that search result appears for Phrase in Basic Search screen");

		// Verify Expanded Query
		session.basicContentSearch(search);

		softAssertion.assertAll();
		login.logout();
	}

	@DataProvider(name = "reserve")
	public Object[][] reserve() {
		return new Object[][] { { "\"discrepancy in\"" }, { "\"john@consilio.com\"" }, };
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that result appears for phrase(in double quote) search
	 *              in Basic Search Query Screen.
	 * @param data1
	 * @throws InterruptedException
	 */

	@Test(description = "RPMXCON-57277", enabled = true, dataProvider = "reserve", groups = { "regression" })

	public void verifyPhraseForSearch(String data1) throws InterruptedException {

		// login as Users
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-57277 Basic Search");
		base.stepInfo("Verify that result appears for phrase(in double quote) search in Basic Search Query Screen.");

		// Verify Expanded Query
		session.basicContentSearch(data1);

		softAssertion.assertAll();
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that application does not display warning A message on
	 *              Advanced Search if user enters "2009-09-20" with quotations.
	 *              [RPMXCON-57117]
	 * @throws InterruptedException
	 */

	@Test(description = "RPMXCON-57117", enabled = true, groups = { "regression" })

	public void verifyWarningMsgOnAdvPage() throws InterruptedException {

		// login as Users
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-57117 Basic Search");
		base.stepInfo(
				"Verify that application does not display warning A message on Advanced Search if user enters \"2009-09-20\" with quotations.");

		// Verify Expanded Query
		session.navigateToSessionSearchPageURL();
		session.advancedContentSearchWithSearchChanges("\"2009-09-20\"", "No");
		session.SearchBtnAction();

		// verify if warning message is displayed
		String expectedMsg = "Warning message is not Displayed ";
		boolean flag = session.getQueryAlertGetText().isElementAvailable(5);
		base.printResutInReport(flag, "Warning message is displayed", expectedMsg, "Fail");

		softAssertion.assertAll();
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that application does not display warning B message on
	 *              Basic Search if user enters "bi - weekly" into Non-Date field
	 *              with wrapper quotations. [RPMXCON-57115]
	 * @throws InterruptedException
	 */

	@Test(description = "RPMXCON-57115", enabled = true, groups = { "regression" })

	public void verifyWarningMsgOnBsPage() throws InterruptedException {

		// login as Users
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-57115 Basic Search");
		base.stepInfo(
				"Verify that application does not display warning B message on Basic Search if user enters \"bi - weekly\" into Non-Date field with wrapper quotations.");

		// Verify Expanded Query
		session.navigateToSessionSearchPageURL();
		session.basicMetaDataDraftSearch("EmailSubject", null, "\"bi-weekly\"", null);
		session.addNewSearch();

		// verify if warning message is displayed
		String expectedMsg = "Warning Message is Not Displayed ";
		boolean flag = session.getQueryAlertGetText().isElementAvailable(5);
		base.printResutInReport(flag, "Warning message is displayed", expectedMsg, "Fail");

		softAssertion.assertAll();
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
//				login.clearBrowserCache();
		} catch (Exception e) {
			// no session avilable

		}
	}
}
