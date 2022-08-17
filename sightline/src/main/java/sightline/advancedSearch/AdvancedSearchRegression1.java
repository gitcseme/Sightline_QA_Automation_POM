package sightline.advancedSearch;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

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
import pageFactory.Categorization;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class AdvancedSearchRegression1 {

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

	@DataProvider(name = "Users")
	public Object[][] Users() {
		Object[][] users = { { Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password },
				{ Input.rev1userName, Input.rev1password }, };
		return users;
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify warning messgae displays if search token entered as
	 *              Date in Advanced Search Query Screen (Message 40001000011)
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57373", enabled = true, dataProvider = "Users", groups = { "regression" })
	public void verifyBellyBandMsg(String username, String password) throws InterruptedException {
		String search = "12/13/2000";

		// login as User
		login.loginToSightLine(username, password);
		base.stepInfo("RPMXCON-57373 Advanced Search");
		base.stepInfo(
				"Verify warning messgae displays if search token entered as Date in Advanced Search Query Screen (Message 40001000011)");

		// Verify Expanded Query
		session.wrongQueryAlertAdvanceSaerch(search, 3, "non fielded", null);
		session.advanedContentDraftSearch(search);

		// click yes Btn in warning message and verify result
		session.saveAndReturnPureHitCount();

		// click No Btn
		driver.scrollPageToTop();
		session.getModifyASearch().waitAndClick(10);
		session.SearchBtnAction();
		base.waitForElement(session.getNoBtn());
		session.getNoBtn().waitAndClick(10);

		// verify session search Page
		session.verifySessionSearchPageUrl();

		softAssertion.assertAll();
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify search result appears for parenthesis with terms in
	 *              Advanced Search Query screen ,(Message 40001000005)
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57369", enabled = true, dataProvider = "Users", groups = { "regression" })
	public void verifyResultForParenthesis(String username, String password) throws InterruptedException {
		String search = "(that this verification)";

		// login as User
		login.loginToSightLine(username, password);
		base.stepInfo("RPMXCON-57369 Advanced Search");
		base.stepInfo(
				"Verify search result appears for parenthesis with terms in Advanced Search Query screen ,(Message 40001000005)");

		// Verify Expanded Query
		session.wrongQueryAlertAdvanceSaerch(search, 10, "non fielded", null);
		session.advanedContentDraftSearch(search);

		// click yes Btn in warning message and verify result
		session.saveAndReturnPureHitCount();

		// click No Btn
		driver.scrollPageToTop();
		session.getModifyASearch().waitAndClick(10);
		session.SearchBtnAction();
		base.waitForElement(session.getNoBtn());
		session.getNoBtn().waitAndClick(10);

		// verify session search Page
		session.verifySessionSearchPageUrl();

		softAssertion.assertAll();
		login.logout();
	}

	@DataProvider(name = "reserve")
	public Object[][] reserve() {
		return new Object[][] { { "“iterative methodology(requirements OR collaboration)”~7" },
				{ "“iterative methodology(requirements OR collaboration)”~7" }, };
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that result appears for proximity having 2+ words with
	 *              OR in Advanced Search Query Screen.
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57306", enabled = true, dataProvider = "reserve", groups = { "regression" })
	public void verifyResultForProximityWith2plus(String search) throws InterruptedException {

		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-57306 Advanced Search");
		base.stepInfo(
				"Verify that result appears for proximity having 2+ words with OR in Advanced Search Query Screen.");

		// Verify Expanded Query
		session.wrongQueryAlertAdvanceSaerch(search, 13, "non fielded", null);

		softAssertion.assertAll();
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description :Verify that Dropped tiles are retained in shopping cart when
	 *              User Navigates Advanced Search (Pure Hit) >> "Analyze in
	 *              Communications Explorer" screen and Come back to Search Page.
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57255", enabled = true, groups = { "regression" })
	public void verifyCartAfterNaviToCommExp() throws InterruptedException {

		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-57255 Advanced Search");
		base.stepInfo(
				"Verify that Dropped tiles are retained in shopping cart when User Navigates Advanced Search (Pure Hit) >> \"Analyze in Communications Explorer\" screen and Come back to Search Page.");

		// Verify Expanded Query
		session.advancedMetaDataSearch(Input.metaDataName, null, Input.custodianName_Andrew, null);
		session.addPurehitAndActionAsConceptOrComm(true, false);

		session.navigateToSessionSearchPageURL();
		String passMSg = "Added Purehit is Present in Cart";
		base.ValidateElement_Presence(session.getRemoveAddBtn(), passMSg);

		softAssertion.assertAll();
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description :Verify that Dropped tiles are retained in shopping cart when
	 *              User Navigates Basic Search (Pure Hit) >> "Analyze in
	 *              Communications Explorer" screen and Come back to Search Page..
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57260", enabled = true, groups = { "regression" })
	public void verifyCartAfterNaviToCommExp_BS() throws InterruptedException {

		// login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-57260 Advanced Search");
		base.stepInfo(
				"Verify that Dropped tiles are retained in shopping cart when User Navigates Basic Search (Pure Hit) >> \"Analyze in Communications Explorer\" screen and Come back to Search Page.");

		// Verify Expanded Query
		session.basicMetaDataSearch(Input.metaDataName, null, Input.custodianName_Andrew, null);
		session.addPurehitAndActionAsConceptOrComm(true, false);

		session.navigateToSessionSearchPageURL();
		String passMSg = "Added Purehit is Present in Cart";
		base.ValidateElement_Presence(session.getRemoveAddBtn(), passMSg);

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
