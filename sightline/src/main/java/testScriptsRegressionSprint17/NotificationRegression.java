package testScriptsRegressionSprint17;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
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
import pageFactory.BaseClass;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class NotificationRegression {

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
	 * @Author Jeevitha
	 * @Description : To verify, As an Project admin user login, I will get an
	 *              notification when I will execute any folder under My Search in
	 *              saved search[RPMXCON-53873]
	 */

	@Test(description = "RPMXCON-53873", enabled = true, groups = { "regression" })
	public void verifyNotificationForNode() throws InterruptedException, Exception {
		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();
		String headerName = "Search Name";

		base.stepInfo("Test case Id: RPMXCON-53873 Notification");
		base.stepInfo(
				"To verify, As an Project admin user login, I will get an notification when I will execute any folder under My Search in saved search");

		// Login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("logged in as : " + Input.pa1FullName);

		// create the node 
		String newNode = saveSearch.createNewSearchGrp(Input.mySavedSearch);

		//save query in node
		session.basicContentSearch(Input.searchString2);
		session.saveSearchInNodeUnderGroup(searchName, newNode, Input.mySavedSearch);

		//Select Node 
		saveSearch.selectNodeUnderSpecificSearchGroup(Input.mySavedSearch, newNode);
		
		//get count of search in node
		int initialBg = base.initialBgCount();
		int sizeoflist = saveSearch.getListFromSavedSearchTable(headerName).size();
		
		//Execute Node
		saveSearch.performExecute();
		
		//verify Notification
		saveSearch.verifyExecuteAndReleaseNotify(initialBg, sizeoflist);

		// delete Node
		saveSearch.deleteNode(Input.mySavedSearch, newNode);

		// logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : To verify, As an Project admin user login, I will get an
	 *              notification when I will execute any saved query in saved search
	 *              page [RPMXCON-53870]
	 * @throws InterruptedException
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53870", enabled = true, groups = { "regression" })
	public void verifyNotifyForSearch() throws InterruptedException, Exception {
		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-53870 Notification");
		base.stepInfo(
				"To verify, As an Project admin user login, I will get an notification when I will execute any saved query in saved search page");

		// Login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("logged in as : " + Input.pa1FullName);

		// configure query & Save
		session.basicContentSearch(Input.searchString2);
		session.saveSearch(searchName);

		//select search
		saveSearch.savedSearch_Searchandclick(searchName);
		
		//execute Search
		int initialBg = base.initialBgCount();
		saveSearch.savedSearchExecute_Draft(searchName, initialBg);
		
		//verify Notification
		saveSearch.verifyExecuteAndReleaseNotify(initialBg, 1);

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, Input.yesButton);

		// logout
		login.logout();
	}

	/**
	 * @Author : Jeevitha
	 * @Description : To verify, as an Project admin user, I will get an
	 *              notification when I will perform Bulk Unrelease from saved
	 *              search [RPMXCON-53865]
	 * @throws InterruptedException
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53865", enabled = true, groups = { "regression" })
	public void verifyNotifyForReleasingSearch() throws InterruptedException, Exception {
		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-53865 Notification");
		base.stepInfo(
				"To verify, as an Project admin user, I will get an notification when I will perform Bulk Unrelease from saved search");

		// Login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("logged in as : " + Input.pa1FullName);

		// configure query and save
		session.basicContentSearch(Input.searchString2);
		session.saveSearch(searchName);

		//select search
		saveSearch.savedSearch_Searchandclick(searchName);
		
		//bulk unrelease the doc
		int initialBg = base.initialBgCount();
		saveSearch.performReleaseAction(Input.securityGroup, false);
		
		//verify notification
		saveSearch.verifyExecuteAndReleaseNotify(initialBg, 1);

		// release back to SG & Delete Search
		saveSearch.savedSearch_Searchandclick(searchName);
		saveSearch.performReleaseAction(Input.securityGroup, true);
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, Input.yesButton);

		// logout
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
