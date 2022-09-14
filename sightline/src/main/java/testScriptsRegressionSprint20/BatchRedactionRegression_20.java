package testScriptsRegressionSprint20;

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
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BatchRedactionRegression_20 {

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

	@DataProvider(name = "USERS")
	public Object[][] USERS() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, "PA" },
				{ Input.sa1userName, Input.sa1password, "SA" }, { Input.da1userName, Input.da1password, "DA" } };
		return users;
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that only RMU user can have access to "Batch
	 *              Redactions" page [RPMXCON-53326]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53326", enabled = true, groups = { "regression" })
	public void verifyBRPageIsAccessible() throws Exception {

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Logged In As : RMU");

		base.stepInfo("Test case Id:RPMXCON-53326 Batch Redaction");
		base.stepInfo("Verify that only RMU user can have access to \"Batch Redactions\" page");

		// verify navigation to Batch Redaction is Accessible
		batch.navigateToBRPage();

		// disable Docview-Redaction
		batch.assignRedactionRights(Input.rmu1userName, false);
		base.selectproject();

		// verify navigation to Batch Redaction is Accessible after Disable
		batch.navigateToBRPage();
		base.passedStep("Batch Redaction is Accessible Even after Disabling Docview-Redaction Control");

		// enable Docview-Redaction
		batch.assignRedactionRights(Input.rmu1userName, true);

		// logout
		login.logout();

		// Login as a DA
		login.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Logged In As : DA");

		// click active Project and verify Impersonation to RMU
		DomainDashboard domain = new DomainDashboard(driver);
		domain.clickActiveProject(Input.projectName);

		// verify navigation to Batch Redaction is Accessible after Impersonation
		driver.waitForPageToBeReady();
		batch.navigateToBRPage();

		// logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that SA/DA/PA after impersonating as RMU can have
	 *              access to "Batch Redactions" page [RPMXCON-53328]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53328", dataProvider = "USERS", enabled = true, groups = { "regression" })
	public void verifyBRPageIsAccessibleAfterImpersonate(String username, String password, String userRole)
			throws Exception {

		// Login as a User
		login.loginToSightLine(username, password);
		base.stepInfo("Logged In As : " + userRole);

		base.stepInfo("Test case Id:RPMXCON-53328 Batch Redaction");
		base.stepInfo("Verify that SA/DA/PA after impersonating as RMU can have access to \"Batch Redactions\" page");

		// Impersonate to RMU
		base.rolesToImp(userRole, "RMU");

		// verify navigation to Batch Redaction is Accessible after Impersonate to rmu
		batch.navigateToBRPage();

		// logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that 'Analyze Search for Redaction' button from Batch
	 *              Redactions page is enabled for the Saved Search(es)/Search
	 *              Group(s) [RPMXCON-53329]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53329", enabled = true, groups = { "regression" })
	public void verifyNavigatioNAndAnalyzeBtn() throws Exception {
		String searchName = "Search" + Utility.dynamicNameAppender();

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Logged In As : RMU");

		base.stepInfo("Test case Id:RPMXCON-53329 Batch Redaction");
		base.stepInfo(
				"Verify that 'Analyze Search for Redaction' button from Batch Redactions page is enabled for the Saved Search(es)/Search Group(s)");

		// Create saved search
		session.basicContentSearch(Input.testData1);
		session.saveSearch(searchName);

		// verify navigation to Batch Redaction is Accessible after Impersonate to rmu
		batch.navigateToBRPage();

		// verify Analyze Btn enable
		batch.loadBatchRedactionPage(searchName);
		batch.verifyAnalyzeBtn(searchName, null);
		base.waitForElement(batch.getAnalyzeSearchForSavedSearchResult(searchName));
		batch.getAnalyzeSearchForSavedSearchResult(searchName).waitAndClick(10);
		base.passedStep("'Analyze Search for Redaction' button is enabled");

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		// logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify the "Batch Redactions" home page for RMU user
	 *              [RPMXCON-53327]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53327", enabled = true, groups = { "regression" })
	public void verifyBatchRedactionHomePage() throws Exception {
		String searchName = "Search" + Utility.dynamicNameAppender();
		String[] actualSectionList = { "Batch Redactions", "Batch Redaction History" };
		String[] actualHistoryheader = { "BATCHID", "SAVED SEARCH", "REDACTION TAG", "# DOCS", "STATUS", "REPORT",
				"ACTION" };

		// Login as a RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Logged In As : RMU");

		base.stepInfo("Test case Id:RPMXCON-53327 Batch Redaction");
		base.stepInfo("Verify the \"Batch Redations\" home page for RMU user");

		// Create saved search
		session.basicContentSearch(Input.testData1);
		session.saveSearch(searchName);
		session.saveSearchAtAnyRootGroup(searchName, Input.shareSearchDefaultSG);

		// verify navigation to Batch Redaction
		batch.navigateToBRPage();

		// verify batch redaction page sections Headers
		List<String> sectionList = base.availableListofElements(batch.HeaderTabsPresentInBR());
		String passMsg = "Batch Redactions home page is Displayed with : " + sectionList;
		String failMsg = "Batch Redactions home page is Headers is not as Expected";
		base.compareArraywithDataList(actualSectionList, sectionList, true, passMsg, failMsg);

		// 1st section header and icon verification
		base.ValidateElement_Presence(batch.getSelectSearchHeader(), "Select a Search / Search Group");
		batch.SearchGroupHelpIcon();

		// verify tabs present
		batch.verifySelectSearchSection(Input.shareSearchDefaultSG);
		batch.verifySelectSearchSection(Input.mySavedSearch);

		// perform Batch redaction
		batch.VerifyBatchRedaction_ElementsDisplay(searchName, true);
		batch.viewAnalysisAndBatchReport(Input.defaultRedactionTag, Input.yesButton);
		batch.verifyBatchHistoryStatus(searchName);

		// verify history table header & verify help icon
		batch.BatchRedactionHistoryHelpIcon();
		
		List<String> historyHeaderList = base.availableListofElements(batch.batchRedactionHistoryHeader());
		String passMsg2 = "Batch Redactions History Table is Displayed with : " + historyHeaderList;
		String failMsg2 = "Batch Redactions History Table Headers is not as Expected";
		base.compareArraywithDataList(actualHistoryheader, historyHeaderList, true, passMsg2, failMsg2);

		// verify rollback & click here for report link
		base.ValidateElement_Presence(batch.getRollbackbtn(searchName), "Rollback Link ");
		base.ValidateElement_Presence(batch.getClickHereReportbtn(searchName), "Click here for Report Link");

		// Delete Search
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		saveSearch.deleteSearch(searchName, Input.shareSearchDefaultSG, "Yes");

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
//					login.clearBrowserCache();
		} catch (Exception e) {
			// no session avilable

		}
	}
}
