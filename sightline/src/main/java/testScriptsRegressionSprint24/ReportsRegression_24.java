package testScriptsRegressionSprint24;

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
import pageFactory.BaseClass;
import pageFactory.CommunicationExplorerPage;
import pageFactory.ConceptExplorerPage;
import pageFactory.CustomDocumentDataReport;
import pageFactory.LoginPage;
import pageFactory.ReportsPage;
import pageFactory.SavedSearch;
import pageFactory.SearchTermReportPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ReportsRegression_24 {

	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	BaseClass baseClass;
	Input in;
	SoftAssert assertion;
	ReportsPage reports;
	ConceptExplorerPage conceptExplorer;
	CommunicationExplorerPage communicationExpPage;
	SearchTermReportPage searchterm;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		in = new Input();
		in.loadEnvConfig();
	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();

		sessionSearch = new SessionSearch(driver);
		reports = new ReportsPage(driver);
		conceptExplorer = new ConceptExplorerPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		assertion = new SoftAssert();
		savedSearch = new SavedSearch(driver);
		communicationExpPage = new CommunicationExplorerPage(driver);
		searchterm = new SearchTermReportPage(driver);
	}

	@DataProvider(name = "paRmuUsers")
	public Object[][] paRmuUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, "PA" },
				{ Input.rmu1userName, Input.rmu1password, "RMU" } };
		return users;
	}

	/**
	 * @author Jeevitha.R
	 * @Description : To verify that user can save the Search Term report and user
	 *              can view the saved report [RPMXCON-56508]
	 */
	@Test(description = "RPMXCON-56508", dataProvider = "paRmuUsers", groups = { "regression" }, enabled = true)
	public void verifyUserCanSaveSTR(String userName, String password, String role)
			throws InterruptedException, ParseException {
		String saveSearch1 = "Search1" + Utility.dynamicNameAppender();
		String saveSearch2 = "Search2" + Utility.dynamicNameAppender();
		String[] columnName = { "Hits", "Family Members" };
		String[] searchList = { saveSearch1, saveSearch2 };

		baseClass.stepInfo("Test case Id:RPMXCON-56508 Reports/Search Term");
		baseClass.stepInfo("To verify that user can save the Search Term report and user can view the saved report");

		// Login as User
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Logged In As : " + role);

		// Save searches
		sessionSearch.basicContentSearch(Input.searchString6);
		sessionSearch.saveSearch(saveSearch1);
		sessionSearch.saveSearch(saveSearch2);

		// select Search and generate STR report
		reports.navigateToReportsPage("");
		searchterm.GenerateReportWithAllSearches(searchList);

		// Unselect Columns and verify column present in report
		searchterm.ClickGearIconAndSelectOrUnselectColm(false, true, columnName, false);

		// save custom Report ,verify and click saved custom report
		String reportName = searchterm.ValidateSearchTermreportSaveandImpact(saveSearch1, true);
		reports.getCustomReport(reportName).waitAndClick(10);
		driver.waitForPageToBeReady();

		// Check report generated with saved searches is still Saved
		for (int i = 0; i < searchList.length; i++) {
			baseClass.waitForElement(searchterm.getsearchOrTab_CB(searchList[i]));
			String searchStatus = searchterm.getsearchOrTab_CB(searchList[i]).GetAttribute("class");
			baseClass.compareTextViaContains(searchStatus, "clicked",
					"Custom Report is saved with selected criteria : " + searchList[i],
					"Custom Report is not Saved with expected criteria");
		}

		// verify column is not available in Custom Report as Saved criteria
		searchterm.ClickGearIconAndSelectOrUnselectColm(true, false, columnName, false);

		// delete search
		savedSearch.deleteSearch(saveSearch1, Input.mySavedSearch, Input.yesButton);

		// logout
		loginPage.logout();
	}

	/**
	 * @author Jeevitha.R
	 * @throws AWTException
	 * @Description : Search Term Report - Verify bulk tag action on Unique Hits /
	 *              Unique Family Hits records [RPMXCON-56595]
	 */
	@Test(description = "RPMXCON-56595", groups = { "regression" }, enabled = true)
	public void verifyViewBulkTag() throws InterruptedException, ParseException, AWTException {
		String saveSearch1 = "Search1" + Utility.dynamicNameAppender();
		String saveSearch2 = "Search2" + Utility.dynamicNameAppender();
		String saveSearch3 = "Search3" + Utility.dynamicNameAppender();
		String tagName = "TAG" + Utility.dynamicNameAppender();

		String columnName = "Unique Hits".toUpperCase();

		String[] tabList = { Input.mySavedSearch, Input.shareSearchPA, Input.shareSearchDefaultSG };

		baseClass.stepInfo("Test case Id:RPMXCON-56595 Reports/Search Term");
		baseClass.stepInfo("Search Term Report - Verify bulk tag action on Unique Hits / Unique Family Hits records");

		// Login
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		// save search in All 3 TAbs
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(saveSearch1);

		sessionSearch.getNewSearchButton().waitAndClick(5);
		sessionSearch.multipleBasicContentSearch(Input.searchString2);
		sessionSearch.saveSearchAtAnyRootGroup(saveSearch2, Input.shareSearchPA);
		sessionSearch.saveSearchAtAnyRootGroup(saveSearch3, Input.shareSearchDefaultSG);

		// select ALL tabs and generate STR report
		reports.navigateToReportsPage("");
		searchterm.GenerateReportWithAnySearchOrTab(tabList);

		// Select Unique Hit column
		searchterm.selectColumnFromSTRPage(columnName);

		// perform Bulk Tag
		searchterm.BulkTag(tagName);
		baseClass.passedStep("Bulk tag Performed Successfully");

		// delete search
		savedSearch.deleteSearch(saveSearch1, Input.mySavedSearch, Input.yesButton);
		savedSearch.deleteSearch(saveSearch2, Input.shareSearchPA, Input.yesButton);
		savedSearch.deleteSearch(saveSearch3, Input.shareSearchDefaultSG, Input.yesButton);

		// logout
		loginPage.logout();
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
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		System.out.println("**Executed Communication explorer sprint 21**");
	}
}
