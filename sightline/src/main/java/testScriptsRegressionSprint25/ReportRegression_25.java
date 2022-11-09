package testScriptsRegressionSprint25;

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

public class ReportRegression_25 {

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
	 * @Description : To verify that in Search Term Report page Searches criteria is
	 *              mandatory. [RPMXCON-56365]
	 */
	@Test(description = "RPMXCON-56365", groups = { "regression" }, enabled = true)
	public void verifySTRPageSearchIsMandatory() throws InterruptedException, ParseException {

		baseClass.stepInfo("Test case Id:RPMXCON-56365 Reports/Search Term");
		baseClass.stepInfo("To verify that in Search Term Report page Searches criteria is mandatory.");

		// Login as User
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Click Apply button without selecting any search
		reports.navigateToReportsPage("Search Term Report");
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(searchterm.mySavedSearchCheckbox());
		baseClass.waitTillElemetToBeClickable(searchterm.getApplyBtn());
		searchterm.getApplyBtn().waitAndClick(10);

		// verify Error message
		baseClass.VerifyErrorMessage("Please select at least one search.");

		// logout
		loginPage.logout();
	}

	/**
	 * @author Jeevitha.R
	 * @Description : To verify that if there is no data is STR then message is
	 *              displayed to User. [RPMXCON-56366]
	 */
	@Test(description = "RPMXCON-56366", groups = { "regression" }, enabled = true)
	public void verifyNoDatainSTRThenMsgDisplayed() throws InterruptedException, ParseException {

		baseClass.stepInfo("Test case Id:RPMXCON-56366 Reports/Search Term");
		baseClass.stepInfo("To verify that if there is no data is STR then message is displayed to User.");

		// Login as User
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		// create empty search group
		String searchGrp = savedSearch.createNewSearchGrp(Input.mySavedSearch);
		String[] selectSearchGrp = { searchGrp };

		// Select empty search group and Click Apply button
		reports.navigateToReportsPage("");
		searchterm.GenerateReportWithAnySearchOrTab(selectSearchGrp, false);

		// verify Error message
		baseClass.VerifyErrorMessage("Please select valid search. Report will not generate for empty group");

		// logout
		loginPage.logout();
	}

	/**
	 * @author Jeevitha.R
	 * @throws AWTException
	 * @Description : Search Term Report - Verify bulk folder action on Unique Hits
	 *              / Unique Family Hits records [RPMXCON-56596]
	 */
	@Test(description = "RPMXCON-56596", groups = { "regression" }, enabled = true)
	public void verifyViewBulkFolder() throws InterruptedException, ParseException, AWTException {
		String saveSearch1 = "Search1" + Utility.dynamicNameAppender();
		String saveSearch2 = "Search2" + Utility.dynamicNameAppender();
		String saveSearch3 = "Search3" + Utility.dynamicNameAppender();
		String folderName = "FOLDER" + Utility.dynamicNameAppender();

		String columnName = "Unique Hits".toUpperCase();

		String[] tabList = { Input.mySavedSearch, Input.shareSearchPA, Input.shareSearchDefaultSG };

		baseClass.stepInfo("Test case Id:RPMXCON-56596 Reports/Search Term");
		baseClass
				.stepInfo("Search Term Report - Verify bulk folder action on Unique Hits / Unique Family Hits records");

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
		searchterm.GenerateReportWithAnySearchOrTab(tabList, true);

		// Select Unique Hit column
		searchterm.selectColumnFromSTRPage(columnName);

		// get Unique Hit count from search term report page
		int hitcount = searchterm.verifyaggregateCount(columnName);

		// perform Bulk Folder
		searchterm.BulkFolder(folderName);
		baseClass.passedStep("Bulk Folder Performed Successfully");

		// verify the selected document is moved to the folder
		TagsAndFoldersPage tagsAndFolder = new TagsAndFoldersPage(driver);
		tagsAndFolder.navigateToTagsAndFolderPage();
		tagsAndFolder.selectingFolderAndVerifyingTheDocCount(folderName, hitcount);

		// delete search
		savedSearch.deleteSearch(saveSearch1, Input.mySavedSearch, Input.yesButton);
		savedSearch.deleteSearch(saveSearch2, Input.shareSearchPA, Input.yesButton);
		savedSearch.deleteSearch(saveSearch3, Input.shareSearchDefaultSG, Input.yesButton);

		// logout
		loginPage.logout();
	}

	/**
	 * @author Jeevitha.R
	 * @throws AWTException
	 * @Description : Search Term Report - Verify Scheduling/Sharing/Expoting/Saving
	 *              STR report with Unique Hits / Unique Family Hits column
	 *              [RPMXCON-56589]
	 */
	@Test(description = "RPMXCON-56589", groups = { "regression" }, enabled = true)
	public void verifyStrReport() throws InterruptedException, ParseException, AWTException {
		String saveSearch1 = "Search1" + Utility.dynamicNameAppender();
		String saveSearch2 = "Search2" + Utility.dynamicNameAppender();
		String saveSearch3 = "Search3" + Utility.dynamicNameAppender();
		String columnName = "Unique Hits".toUpperCase();

		String[] tabList = { Input.mySavedSearch, Input.shareSearchPA, Input.shareSearchDefaultSG };

		baseClass.stepInfo("Test case Id:RPMXCON-56589 Reports/Search Term");
		baseClass.stepInfo(
				"Search Term Report - Verify Scheduling/Sharing/Expoting/Saving STR report with Unique Hits / Unique Family Hits column");

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
		searchterm.GenerateReportWithAnySearchOrTab(tabList, true);

		// Select Unique Hit column
		searchterm.selectColumnFromSTRPage(columnName);

		// Perform Saving STR report
		searchterm.ValidateSearchTermreportSaveandImpact(columnName, false);

		// Perform Export STR Report
		baseClass.waitForElement(searchterm.getExportIcon());
		searchterm.getExportIcon().waitAndClick(10);
		baseClass.VerifySuccessMessage("Report save successfully");

		// perform Sharing STR  Report
		searchterm.performSharingAction(Input.rmu1FullName, Input.rmu1userName);

		// perform Schedule STR Report
		reports.navigateToReportsPage("");
		searchterm.GenerateReportWithAnySearchOrTab(tabList, true);
		searchterm.selectColumnFromSTRPage(columnName);
		searchterm.performScheduleAction(Input.rmu1FullName, Input.rmu1userName);

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
