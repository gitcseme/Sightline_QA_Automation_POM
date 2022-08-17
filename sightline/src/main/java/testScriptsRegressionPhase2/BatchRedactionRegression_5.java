package testScriptsRegressionPhase2;

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
import pageFactory.BatchRedactionPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BatchRedactionRegression_5 {

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
	 * @Description : Verify that on click of “Analyze Search for Redaction”, batch
	 *              redaction run should be initiated to find out the list of
	 *              “redaction keyword/term occurrences” followed the list of
	 *              documents for list of occurrences [RPMXCON-48805]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-48805", enabled = true, groups = { "regression" })
	public void verifyAnalyeSearchSuccessMsg() throws Exception {
		String searchName = "Search" + Utility.dynamicNameAppender();

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		base.stepInfo("Test case Id:RPMXCON-48805 Batch Redaction");
		base.stepInfo(
				"Verify that on click of “Analyze Search for Redaction”, batch redaction run should be initiated to find out the list of “redaction keyword/term occurrences” followed the list of documents for list of occurrences");

		// Create saved search
		session.basicContentSearch(Input.testData1);
		session.saveSearch(searchName);

		// Perform Analysis of search
		batch.loadBatchRedactionPage(searchName);
		batch.verifyAnalyzeBtn(searchName, null);
		base.waitForElement(batch.getAnalyzeSearchForSavedSearchResult(searchName));
		batch.getAnalyzeSearchForSavedSearchResult(searchName).waitAndClick(10);

		// verify Success Message
		base.VerifySuccessMessageB(
				"Your Request to Analyze corpus for redaction term hits has been added to the background.  Once it is complete, the \"bullhorn\" icon in the upper right hand corner will turn red to notify you of the results of your request.");

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
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
