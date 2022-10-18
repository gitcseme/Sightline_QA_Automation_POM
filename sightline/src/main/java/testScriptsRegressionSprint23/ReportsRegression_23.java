package testScriptsRegressionSprint23;

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
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ReportsRegression_23 {

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
	 * @Description : Verify the Total result counts by selecting multiple searches
	 *              in Search Term report [RPMXCON-56824]
	 */

	@Test(description = "RPMXCON-56824", dataProvider = "paRmuUsers", groups = { "regression" }, enabled = true)
	public void selectMultipleSearchAndVerifyTotalCount(String userName, String password, String role)
			throws InterruptedException, ParseException {
		String saveSearch1 = "Search1" + Utility.dynamicNameAppender();
		String saveSearch2 = "Search2" + Utility.dynamicNameAppender();
		String saveSearch3 = "Search3" + Utility.dynamicNameAppender();
		String[] searchList = { saveSearch1, saveSearch2, saveSearch3 };

		baseClass.stepInfo("Test case Id:RPMXCON-56824 Reports/Search Term");
		baseClass.stepInfo("Verify the Total result counts by selecting multiple searches in Search Term report");

		// Login
		loginPage.loginToSightLine(userName, password);

		int expectedHitCount = sessionSearch.basicContentSearch(Input.searchString4);
		sessionSearch.saveSearch(saveSearch1);
		sessionSearch.saveSearch(saveSearch2);
		sessionSearch.saveSearch(saveSearch3);

		// select multiple Searches
		reports.navigateToReportsPage("");
		searchterm.GenerateReportWithAllSearches(searchList);

		// verify aggregate hit count with total hit count
		searchterm.verifyaggregateCount("HITS");

		baseClass.waitForElement(searchterm.getHitsCount());
		String ActualHitCount = searchterm.getHitsCount().getText();
		baseClass.textCompareEquals(ActualHitCount, String.valueOf(expectedHitCount),
				"Aggregate Hit Count of searches is :  " + ActualHitCount, "Aggregate hitcount is not as expected");

		// delete search
		savedSearch.deleteSearch(saveSearch1, Input.mySavedSearch, Input.yesButton);
		savedSearch.deleteSearch(saveSearch2, Input.mySavedSearch, Input.yesButton);
		savedSearch.deleteSearch(saveSearch3, Input.mySavedSearch, Input.yesButton);

		// logout
		loginPage.logout();

	}

	/**
	 * @author Jeevitha.R
	 * @Description : Verify the Total result counts by selecting only single search
	 *              in Search Term report [RPMXCON-56823]
	 */

	@Test(description = "RPMXCON-56823", dataProvider = "paRmuUsers", groups = { "regression" }, enabled = true)
	public void selectSingleSearchAndVerifyTotalCount(String userName, String password, String role)
			throws InterruptedException, ParseException {
		String saveSearch1 = "Search1" + Utility.dynamicNameAppender();
		String[] searchList = { saveSearch1 };

		baseClass.stepInfo("Test case Id:RPMXCON-56823 Reports/Search Term");
		baseClass.stepInfo("Verify the Total result counts by selecting only single search in Search Term report");

		// Login
		loginPage.loginToSightLine(userName, password);

		int expectedHitCount = sessionSearch.basicContentSearch(Input.searchString6);
		sessionSearch.saveSearch(saveSearch1);

		// select Single Searches
		reports.navigateToReportsPage("");
		searchterm.GenerateReportWithAllSearches(searchList);

		// verify aggregate hit count with total hit count
		searchterm.verifyaggregateCount("HITS");

		baseClass.waitForElement(searchterm.getHitsCount());
		String ActualHitCount = searchterm.getHitsCount().getText();
		baseClass.textCompareEquals(ActualHitCount, String.valueOf(expectedHitCount),
				"Aggregate Hit Count of searches is :  " + ActualHitCount, "Aggregate hitcount is not as expected");

		// delete search
		savedSearch.deleteSearch(saveSearch1, Input.mySavedSearch, Input.yesButton);

		// logout
		loginPage.logout();

	}

	/**
	 * @author Jeevitha.R
	 * @Description : To verify that user can export the custom Search Term Report
	 *              [RPMXCON-56511]
	 */
	@Test(description = "RPMXCON-56511", dataProvider = "paRmuUsers", groups = { "regression" }, enabled = true)
	public void verifyUserCanExport(String userName, String password, String role)
			throws InterruptedException, ParseException {
		String saveSearch1 = "Search1" + Utility.dynamicNameAppender();
		String columnName = "Hits".toUpperCase();
		String[] searchList = { saveSearch1 };

		baseClass.stepInfo("Test case Id:RPMXCON-56511 Reports/Search Term");
		baseClass.stepInfo("To verify that user can export the custom Search Term Report");

		// Login
		loginPage.loginToSightLine(userName, password);

		sessionSearch.basicContentSearch(Input.searchString6);
		sessionSearch.saveSearch(saveSearch1);

		// select Search and generate STR report
		reports.navigateToReportsPage("");
		searchterm.GenerateReportWithAllSearches(searchList);

		// Select Hit column
		searchterm.selectColumnFromSTRPage(columnName);

		// save custom Report ,verify and click saved custom report
		String reportName = searchterm.ValidateSearchTermreportSaveandImpact(saveSearch1, true);
		reports.getCustomReport(reportName).waitAndClick(10);

		// Download export and verify Format
		searchterm.exportReportAndVerifyFormat(true, true);

		// delete search
		savedSearch.deleteSearch(saveSearch1, Input.mySavedSearch, Input.yesButton);

		// logout
		loginPage.logout();
	}

	/**
	 * @author Jeevitha.R
	 * @Description : Search Term Report - Verify view bulk action on Unique Hits /
	 *              Unique Family Hits records [RPMXCON-56593]
	 */
	@Test(description = "RPMXCON-56593", groups = { "regression" }, enabled = true)
	public void verifyViewBulkActions() throws InterruptedException, ParseException {
		String saveSearch1 = "Search1" + Utility.dynamicNameAppender();
		String saveSearch2 = "Search2" + Utility.dynamicNameAppender();
		String saveSearch3 = "Search3" + Utility.dynamicNameAppender();
		String columnName = "Unique Hits".toUpperCase();

		String[] tabList = { Input.mySavedSearch, Input.shareSearchPA, Input.shareSearchDefaultSG };

		baseClass.stepInfo("Test case Id:RPMXCON-56593 Reports/Search Term");
		baseClass.stepInfo("Search Term Report - Verify view bulk action on Unique Hits / Unique Family Hits records");

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

		// navigate to doclist
		searchterm.ViewInDocList();
		driver.waitForPageToBeReady();
		baseClass.verifyPageNavigation("en-us/Document/DocList");

		// delete search
		savedSearch.deleteSearch(saveSearch1, Input.mySavedSearch, Input.yesButton);
		savedSearch.deleteSearch(saveSearch2, Input.shareSearchPA, Input.yesButton);
		savedSearch.deleteSearch(saveSearch3, Input.shareSearchDefaultSG, Input.yesButton);

		// logout
		loginPage.logout();
	}

	/**
	 * @author Jeevitha.R
	 * @Description : To verify that user can Export all searches on Search Term
	 *              Report [RPMXCON-56486]
	 */
	@Test(description = "RPMXCON-56486", dataProvider = "paRmuUsers", groups = { "regression" }, enabled = true)
	public void verifyUserCanExportSearchOnSTR(String userName, String password, String role)
			throws InterruptedException, ParseException {
		String saveSearch1 = "Search1" + Utility.dynamicNameAppender();
		String saveSearch2 = "Search2" + Utility.dynamicNameAppender();
		String[] searchList = { saveSearch1, saveSearch2 };
		String columnName = "Hits".toUpperCase();
		String[] metaDataFields = { "CustodianName", Input.documentKey };

		CustomDocumentDataReport cddReport = new CustomDocumentDataReport(driver);

		baseClass.stepInfo("Test case Id:RPMXCON-56486 Reports/Search Term");
		baseClass.stepInfo("To verify that user can Export all searches on Search Term Report");

		// Login
		loginPage.loginToSightLine(userName, password);

		// Configure query & save search
		sessionSearch.basicContentSearch(Input.searchString6);
		sessionSearch.saveSearch(saveSearch1);
		sessionSearch.saveSearch(saveSearch2);

		// select Search and generate STR report
		reports.navigateToReportsPage("");
		searchterm.GenerateReportWithAllSearches(searchList);

		// Select Column
		searchterm.selectColumnFromSTRPage(columnName);

		// click on Export Data from action & navigate to export page
		searchterm.STR_ToExportData();
		driver.waitForPageToBeReady();

		// Select Metadata
		cddReport.selectMetaDataFields(metaDataFields);

		// run report,verify Success Msg & click on recieved Notification
		cddReport.runReportandVerifyFileDownloaded();

		// delete search
		savedSearch.deleteSearch(saveSearch1, Input.mySavedSearch, Input.yesButton);
		savedSearch.deleteSearch(saveSearch2, Input.mySavedSearch, Input.yesButton);

		// logout
		loginPage.logout();
	}

	/**
	 * @author Jeevitha.R
	 * @Description : To verify custom Search Term report should be overwrite or
	 *              update. [RPMXCON-56357]
	 */
	@Test(description = "RPMXCON-56357", groups = { "regression" }, enabled = true)
	public void verifyCustomReportOverwriteOrUpdate() throws InterruptedException, ParseException {
		String saveSearch1 = "Search1" + Utility.dynamicNameAppender();
		String[] searchList = { saveSearch1 };

		baseClass.stepInfo("Test case Id:RPMXCON-56357 Reports/Search Term");
		baseClass.stepInfo("To verify custom Search Term report should be overwrite or update.");

		// Login
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		sessionSearch.basicContentSearch(Input.searchString6);
		sessionSearch.saveSearch(saveSearch1);

		// select Search and generate STR report
		reports.navigateToReportsPage("");
		searchterm.GenerateReportWithAllSearches(searchList);

		// save custom Report ,verify and click saved custom report
		String reportName1 = searchterm.ValidateSearchTermreportSaveandImpact(saveSearch1, true);
		driver.waitForPageToBeReady();

		// generate second custom report
		searchterm.GenerateReportWithAllSearches(searchList);
		String reportName2 = searchterm.ValidateSearchTermreportSaveandImpact(saveSearch1, true);

		// Overwrite the Custom Report , verify popup & click yes button
		reports.getCustomReport(reportName2).waitAndClick(10);
		searchterm.CustomStrOverwriteOrUpdate(reportName2, true, reportName1, true, true);

		// Overwrite the Custom Report , verify popup & click No button
		searchterm.CustomStrOverwriteOrUpdate(reportName2, false, reportName1, true, false);

		// delete search
		savedSearch.deleteSearch(saveSearch1, Input.mySavedSearch, Input.yesButton);

		// logout
		loginPage.logout();
	}

	/**
	 * @author Jeevitha.R
	 * @Description : To verify that Users can expand/collapsed the Searches
	 *              Criteria. [RPMXCON-56364]
	 */
	@Test(description = "RPMXCON-56364", groups = { "regression" }, enabled = true)
	public void verifyExpandAndCollapsed() throws InterruptedException, ParseException {

		baseClass.stepInfo("Test case Id:RPMXCON-56364 Reports/Search Term");
		baseClass.stepInfo("To verify that Users can expand/collapsed the Searches Criteria.");

		// Login
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		// select Search and generate STR report
		reports.navigateToReportsPage("");
		baseClass.waitForElement(searchterm.getSearchTermReport());
		searchterm.getSearchTermReport().waitAndClick(10);

		searchterm.verifySearchExpand(true, false);
		searchterm.verifySearchExpand(true, true);

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
