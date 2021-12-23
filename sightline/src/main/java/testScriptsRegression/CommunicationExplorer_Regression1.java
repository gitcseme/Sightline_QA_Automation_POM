package testScriptsRegression;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import automationLibrary.Driver;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CommunicationExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SearchTermReportPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class CommunicationExplorer_Regression1 {
	Driver driver;
	LoginPage lp;
	SessionSearch search;
	SoftAssert softAssertion;
	BaseClass bc;
	String hitsCount;
	CommunicationExplorerPage comExpPage;
	String saveSearchNamePA = "ST" + Utility.dynamicNameAppender();
	String saveSearchNameRMU = "ST" + Utility.dynamicNameAppender();

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		Input in = new Input();
		in.loadEnvConfig();

		// Open browser
		driver = new Driver();
		bc = new BaseClass(driver);
		// Login as a PA
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		// Search and save it
		search = new SessionSearch(driver);
		search.basicContentSearch(Input.testData1);
		hitsCount = search.verifyPureHitsCount();
		search.saveSearch(saveSearchNamePA);
		lp.logout();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		// Search and save it
		search.basicContentSearch(Input.testData1);
		hitsCount = search.verifyPureHitsCount();
		search.saveSearch(saveSearchNameRMU);
		lp.logout();
		lp.quitBrowser();

	}

	@Test(dataProvider = "Users_PARMU", groups = { "regression" }, priority = 1, enabled = true)
	public void verifyReportGeneration_searches(String username, String password, String role)
			throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-56969");
		bc.stepInfo("Verify and generate Communications Explorer with source as Search");
		lp.loginToSightLine(username, password);
		bc.stepInfo("Logged in as -" + role);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		CommunicationExplorerPage comExpPage = new CommunicationExplorerPage(driver);
		comExpPage.generateReportusingSearch();
		bc.waitForElement(comExpPage.getCommunicationExplorer_ApplyResult());
		if (comExpPage.getCommunicationExplorer_ApplyResult().Displayed()) {
			softAssertion.assertTrue(true);
			bc.passedStep("After Selecting the My searches in select sources tab Report is generated.");

		} else {
			softAssertion.assertTrue(false);
			bc.failedStep("After Selecting the My searches in select sources tab Report is not generated.");

		}

	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		softAssertion = new SoftAssert();
		driver = new Driver();
		lp = new LoginPage(driver);
		bc = new BaseClass(driver);
		comExpPage = new CommunicationExplorerPage(driver);
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
		}
		try {
			lp.logout();
			LoginPage.clearBrowserCache();
			lp.quitBrowser();
		} catch (Exception e) {
			lp.quitBrowser();
		}

		System.out.println("Executed :" + result.getMethod().getMethodName());

	}

	@DataProvider(name = "Users_PARMU")
	public Object[][] PA_RMU() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "RMU" },
				{ Input.pa1userName, Input.pa1password, "PA" } };
		return users;
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			Input in = new Input();
			in.loadEnvConfig();
			driver = new Driver();
			lp = new LoginPage(driver);
			lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			SavedSearch savedSearch = new SavedSearch(driver);
			savedSearch.SaveSearchDelete(saveSearchNameRMU);
			lp.logout();
			lp.loginToSightLine(Input.pa1userName, Input.pa1password);
			savedSearch.SaveSearchDelete(saveSearchNamePA);
			lp.logout();
			LoginPage.clearBrowserCache();
			lp.quitBrowser();
		} catch (Exception e) {
			lp.quitBrowser();
		}

	}
}
