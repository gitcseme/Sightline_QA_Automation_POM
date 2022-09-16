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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.BatchRedactionPage;
import pageFactory.DomainDashboard;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BatchRedactionRegression_21 {

	Driver driver;
	LoginPage login;
	SavedSearch saveSearch;
	SessionSearch session;
	BaseClass base;
	BatchRedactionPage batch;
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
	 * @Description : Verify when user selects the saved search containing audio
	 *              documents for batch redaction [RPMXCON-53389]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53389", enabled = true, groups = { "regression" })
	public void verifyRedactBtnIsDisabledForAudio() throws Exception {
		String audioSearch = "Search1" + Utility.dynamicNameAppender();
		String wpSearch = "Search2" + Utility.dynamicNameAppender();

		String[] searchList = { audioSearch, wpSearch };

		// Login as a User
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Logged In As : RMU");

		base.stepInfo("Test case Id:RPMXCON-53389 Batch Redaction");
		base.stepInfo("Verify when user selects the saved search containing audio documents for batch redaction");

		// Configure Audio Search
		session.audioSearch(Input.audioSearchString1, Input.language);
		session.saveSearch(audioSearch);

		// configure workProduct search
		base.selectproject();
		session.switchToWorkproduct();
		session.workProductSearch("redactions", Input.defaultRedactionTag, false);
		session.saveAndReturnPureHitCount();
		driver.scrollPageToTop();
		session.saveSearch(wpSearch);

		for (int i = 0; i < searchList.length; i++) {
			// Navigate to Batch redaction & verify Analyze Button
			batch.performAnalysisGroupForRedcation(searchList[i], null, 0, false, true);

			// verify search hit count , Readact doc
			batch.verifySearchTree(searchList[i]);

			// verify readct btn disabled
			batch.verifyRedactBtnDisabled(searchList[i], true);

			saveSearch.deleteSearch(searchList[i], Input.mySavedSearch, "Yes");
		}

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
		batch = new BatchRedactionPage(driver);
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
