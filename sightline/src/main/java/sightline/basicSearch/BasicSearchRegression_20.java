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
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BasicSearchRegression_20 {

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
				{ Input.rev1userName, Input.rev1password } };
		return users;
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that application does not display warning B message on
	 *              Advanced Search if user enters "bi-weekly" with quotations.
	 *              [RPMXCON-57121]
	 * @throws InterruptedException
	 */

	@Test(description = "RPMXCON-57121", enabled = true, groups = { "regression" })
	public void verifyWarningMsgOnAdvPage() throws InterruptedException {
		String search = "\"bi-weekly\"";

		// login as Users
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-57121 Basic Search");
		base.stepInfo(
				"Verify that application does not display warning B message on Advanced Search if user enters \"bi-weekly\" with quotations.");

		// configure Query with Double Quotes
		session.navigateToAdvancedSearchPage();
		session.advancedContentSearchConfigure(search);
		session.addNewSearch();

		// verify warning message is not displayed
		String expectedMsg = "Warning message containing YOUR QUERY CONTAINS A HYPHEN CHARACTER is Not Displayed";
		boolean flag = session.getQueryAlertGetText().isElementAvailable(5);
		base.printResutInReport(flag, "Warning message is displayed", expectedMsg, "Fail");

		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that application does not display warning A message on
	 *              Advanced Search if user enters "2009/09/20" with quotations.
	 *              [RPMXCON-57119]
	 * @throws InterruptedException
	 */

	@Test(description = "RPMXCON-57119", enabled = true, groups = { "regression" })
	public void verifyWarningMsgForDateOnAdvPage() throws InterruptedException {
		String search = "\"2009/09/20\"";

		// login as Users
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-57119 Basic Search");
		base.stepInfo(
				"Verify that application does not display warning A message on Advanced Search if user enters \"2009/09/20\" with quotations.");

		// configure Query with Double Quotes
		session.navigateToAdvancedSearchPage();
		session.advancedContentSearchConfigure(search);
		session.addNewSearch();

		// verify warning message is not displayed
		String expectedMsg = "Warning message containing YOUR QUERY CONTAINS AN ARGUMENT is Not Displayed";
		boolean flag = session.getQueryAlertGetText().isElementAvailable(5);
		base.printResutInReport(flag, "Warning message is displayed", expectedMsg, "Fail");

		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that application does not display warning B message on
	 *              Basic Search if user enters "bi-weekly" with quotations.
	 *              [RPMXCON-57112]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57112", enabled = true, groups = { "regression" })
	public void verifyWarningMsgOnBsPage() throws InterruptedException {
		String search = "\"bi-weekly\"";

		// login as Users
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-57112 Basic Search");
		base.stepInfo(
				"Verify that application does not display warning B message on Basic Search if user enters \"bi-weekly\" with quotations.");

		// configure Query with Double Quotes
		session.basicContentDraftSearch(search);
		session.addNewSearch();

		// verify warning message is not displayed
		String expectedMsg = "Warning message containing YOUR QUERY CONTAINS A HYPHEN CHARACTER is Not Displayed";
		boolean flag = session.getQueryAlertGetText().isElementAvailable(5);
		base.printResutInReport(flag, "Warning message is displayed", expectedMsg, "Fail");

		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that Basic Search works properly - for MasterDate time
	 *              metadata - Provide only dates (not times) with "Is" operator
	 *              [RPMXCON-57220]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57220", enabled = true, groups = { "regression" })
	public void verifyMasterdateWorksProperly() throws InterruptedException {

		// login as Users
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-57220 Basic Search");
		base.stepInfo(
				"Verify that Basic Search works properly - for MasterDate time metadata - Provide only dates (not times) with \"Is\" operator");

		// configure Query with Double Quotes
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		session.basicMetaDataSearch(Input.masterDateText, "IS", dateFormat.format(date), null);
		String query = session.configuredQuery();
		
		String passMsg = "Basic Search works properly - for MasterDate time metadata - Provide only dates (not times) with \"Is\" operator  ";
		String failMsg = "Basic Search doesnot work as expected";
		base.textCompareEquals(query,
				"MasterDate: [" + dateFormat.format(date) + " TO " + dateFormat.format(date) + "]", passMsg, failMsg);
		
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
//					login.clearBrowserCache();
		} catch (Exception e) {
			// no session avilable

		}
	}
}
