package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;

import org.testng.Assert;
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
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class AdvancedSearch_Regression1 {

	Driver driver;
	LoginPage loginPage;
	String searchText;
	SessionSearch search;
	SoftAssert softAssertion;
	AssignmentsPage assgnPage;
	SavedSearch savedSearch;
	TagsAndFoldersPage tagPage;
	int pureHit;
	BaseClass baseClass;
	String assignmentName = "Assignment" + Utility.dynamicNameAppender();
	ArrayList<String> reviwersListInAssgnPg;
	ArrayList<String> reviwersListInSearchPg;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		Input in = new Input();
		in.loadEnvConfig();
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		search = new SessionSearch(driver);
		assgnPage = new AssignmentsPage(driver);
		savedSearch = new SavedSearch(driver);
		tagPage = new TagsAndFoldersPage(driver);
		softAssertion = new SoftAssert();
		searchText = Input.searchString1;

	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
			loginPage.logoutWithoutAssert();
		}
		try {
			//loginPage.logout();
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * 
	 * @description:Validate distributed list and results for searching by Completed
	 *                       Assignment workproduct RPMXCON-57267
	 */

//	@Test(groups = { "regression" }, priority = 1)
	public void validateDistributedAssignmentsToReviewer() throws InterruptedException, ParseException, IOException {

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU user");
		baseClass.stepInfo("Validate distributed list and results for searching by Completed Assignment workproduct");
		baseClass.stepInfo("Test case Id: RPMXCON-57267");
		search = new SessionSearch(driver);
		assgnPage = new AssignmentsPage(driver);
		assgnPage.createAssignment(assignmentName, "Default Project Coding Form");
		baseClass.selectproject();
		search.basicContentSearch(Input.testData1);
		search.bulkAssignExisting(assignmentName);
		String PureHitCountBeforeDistribution = search.verifyPureHitsCount();
		baseClass.stepInfo("Created a assignment " + assignmentName);
		baseClass.selectproject();
		assgnPage.editAssignmentUsingPaginationConcept(assignmentName);
		reviwersListInAssgnPg = assgnPage.addReviewerAndDistributeDocs();
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in as review user");
		assgnPage.completeDistributesDocsByReviewer(assignmentName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU user");
		search.switchToWorkproduct();
		reviwersListInSearchPg = search.selectAssignmentInWPSWthFilters(assignmentName, Input.rev1userName,
				"Completed");
		search.serarchWP();
		String PureHitCountAfterDistribution = search.verifyPureHitsCount();
		UtilityLog.info("Pure hit count after Distribution of Docs  is " + PureHitCountAfterDistribution);
		assgnPage.deleteAssgnmntUsingPagination(assignmentName);
		softAssertion.assertEquals(PureHitCountBeforeDistribution, PureHitCountAfterDistribution);
		softAssertion.assertEquals(reviwersListInAssgnPg, reviwersListInSearchPg);
        softAssertion.assertAll();
        loginPage.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description Verify that Auto suggest functionality is working fine having
	 *              CustodianName more than 50 characters in Basic Search Screen.
	 *              RPMXCON-57580
	 */

//	@Test(groups = { "regression" }, priority = 2)
	public void validateCustodianNameAutosuggestSearch() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-57580");
		baseClass.stepInfo(
				"Verify that Auto suggest functionality is working fine having  CustodianName more than 50 characters in Basic Search Screen.");
		String metaDataFileName = "\"My CustodianName Longer Name Test My ABCDEFGHIJKLMâ€¦\"";
		String expectedFileDisplayInQuerySelection = "\"My CustodianName Longer Name Test My ABCDEFGHIJKLM*\"";
		String expectedFileDisplay = "CustodianName: (\"My CustodianName Longer Name Test My ABCDEFGHIJKLM*\")";
		String Value = "My Cu";
		String metadataFieldLabel = "CustodianName";
		baseClass.selectproject();
		search.validateAutosuggestSearchResult(metaDataFileName, metadataFieldLabel, Value, expectedFileDisplay,
				expectedFileDisplayInQuerySelection);
		loginPage.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description Verify that Auto suggest functionality is working fine having
	 *              DocFileName more than 50 characters in Basic Search Screen.
	 *              RPMXCON-57579
	 */
//	@Test(groups = { "regression" }, priority = 3)
	public void validateAutosuggestSearchDocfileName() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-57579");
		baseClass.stepInfo(
				"Verify that Auto suggest functionality is working fine having  CustodianName more than 50 characters in Basic Search Screen.");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		String metaDataFileName = "\"Yes the services are down because of Fire- and othâ€¦\"";
		String expectedFileDisplayInQuerySelection = "\"Yes the services are down because of Fire- and oth*\"";
		String expectedFileDisplay = "DocFileName: (\"Yes the services are down because of Fire- and oth*\")";
		String Value = "Yes ";
		String metadataFieldLabel = "DocFileName";
		baseClass.selectproject();
		search.validateAutosuggestSearchResult(metaDataFileName, metadataFieldLabel, Value, expectedFileDisplay,
				expectedFileDisplayInQuerySelection);
		loginPage.logout();

	}

	/**
	 * @author Jayanthi.ganesan
	 * 
	 * @description:To verify as an user login into the Application, user will be
	 *                 able to execute a advanced search query in saved
	 *                 search(RPMXCON-47772) To verify as an user login into the
	 *                 Application, user will be able to see the all saved query
	 *                 that is saved from advanced search(RPMXCON-47759)
	 */

	@Test(dataProvider = "SavedSearchwithUsers", groups = { "regression" }, priority = 4, enabled = true)
	public void verifySearchResultsInSavedSearchPg(String username, String password)
			throws InterruptedException, ParseException, IOException {
		baseClass.stepInfo("Test case Id: RPMXCON-47772");
		baseClass.stepInfo("Test case Id: RPMXCON-47759");
		loginPage.loginToSightLine(username, password);
		String savedSearchName = "test" + Utility.dynamicNameAppender();
		int pureHitCount = search.MetaDataSearchInAdvancedSearch(Input.metaDataName, "Andrew");
		search.saveSearchAdvanced(savedSearchName);
		savedSearch.savedSearchExecuteAndrefresh(savedSearchName, pureHitCount);
		if (username == Input.pa1userName) {
			baseClass.stepInfo("Advance search query is executed in saved search using PA user");
		}
		if (username == Input.rmu1userName) {
			baseClass.stepInfo("Advance search query is executed in saved search using RMU user");
		}
		if (username == Input.rev1userName) {
			baseClass.stepInfo("Advance search query is executed in saved search using Reviewer");
		}
		loginPage.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * 
	 * @description:To verify as an user login into the Application, User will be
	 *                 able to edit the advanced search saved query(RPMXCON-47768)
	 *                 To verify as an user login into the Application, User will be
	 *                 able to edit the advanced search saved query & after edit
	 *                 able to search the query
	 */ // (RPMXCON-47769)

	@Test(dataProvider = "SavedSearchwithUsers", groups = { "regression" }, priority = 5, enabled = true)
	public void verifyNavigationOfASinSavedSearchPg(String username, String password)
			throws InterruptedException, ParseException, IOException {
		baseClass.stepInfo("Test case Id: RPMXCON-47768");
		baseClass.stepInfo("Test case Id: RPMXCON-47769");
		loginPage.loginToSightLine(username, password);
		String savedSearchName = "test" + Utility.dynamicNameAppender();
		search.MetaDataSearchInAdvancedSearch(Input.metaDataName, "Andrew");
		search.saveSearchAdvanced(savedSearchName);
		savedSearch.savesearch_Edit(savedSearchName);
		search.clearExistingQueryAndSearchNewQuery(Input.metaDataName, "allen");
		if (username == Input.pa1userName) {
			baseClass.stepInfo(
					"Navigated to advance search page successfully from saved search page and new query is searched using PA user");
		}
		if (username == Input.rmu1userName) {
			baseClass.stepInfo(
					"Navigated to advance search page successfully from saved search page and new query is searched using RMU user");
		}
		if (username == Input.rev1userName) {
			baseClass.stepInfo(
					"Navigated to advance search page successfully from saved search page and new query is searched using Reviewer");
		}
		loginPage.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description Verify that Maximum confidence level is set proper on Advanced
	 *              Search screen
	 */
	@Test(groups = { "regression" }, priority = 5)
	public void VerifyAudioThreshhold_maximum() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-47703");
		baseClass.stepInfo("Verify that Maximum confidence level is set proper on Advanced Search screen");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		String AcutalValue = search.VerifyaudioSearchThreshold("right", "North American English", "Max");
		try {
			baseClass.stepInfo("Threshold Value displayed in Advanced search screen is" + AcutalValue);
			softAssertion.assertEquals(AcutalValue, "90");
			baseClass.passedStep(
					"Sucessfully Verified that Maximum confidence level is set proper on Advanced Search screen");
			softAssertion.assertAll();
		} catch (Exception e) {
			baseClass.failedStep(" Maximum confidence level is not set as expected on Advanced Search screen");
		}
		loginPage.logout();

	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description Verify that Minimum confidence level is set proper on Advanced
	 *              Search screen
	 */
	@Test(groups = { "regression" }, priority = 6)
	public void VerifyAudioThreshhold_minimum() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-47702");
		baseClass.stepInfo("Verify that Minimum confidence level is set proper on Advanced Search screen");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		String AcutalValue = search.VerifyaudioSearchThreshold("right", "North American English", "Min");
		try {
			softAssertion.assertEquals(AcutalValue, "50");
			baseClass.stepInfo("Threshold Value displayed in Advanced search screen is" + AcutalValue);
			baseClass.passedStep(
					"Sucessfully Verified that Minimum confidence level is set proper on Advanced Search screen");
			softAssertion.assertAll();
		} catch (Exception e) {
			baseClass.failedStep(" Minimum confidence level is not set as expected on Advanced Search screen");
		}
		loginPage.logout();

	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description Verify that Default confidence level is set proper on Advanced
	 *              Search screen
	 */
	@Test(groups = { "regression" }, priority = 7)
	public void VerifyAudioThreshhold_default() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-47701");
		baseClass.stepInfo("Verify that Default confidence level is set proper on Advanced Search screen");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		String AcutalValue = search.VerifyaudioSearchThreshold("right", "North American English", "default");
		try {
			baseClass.stepInfo("Threshold Value displayed in Advanced search screen is" + AcutalValue);
			softAssertion.assertEquals(AcutalValue, "70");
			baseClass.passedStep(
					"Sucessfully Verified that Default confidence level is set proper on Advanced Search screen");
			softAssertion.assertAll();
		} catch (Exception e) {
			baseClass.failedStep(" Default confidence level is not set as expected on Advanced Search screen");
		}
		loginPage.logout();

	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description Verify, As an user login into the Application, user will be able
	 *              to modify the existing search in advanced search RPMXCON-47752
	 */
	@Test(dataProvider = "SavedSearchwithUsers", groups = { "regression" }, priority = 8)
	public void validateModifyExistingSearchInAS(String username, String password) throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-47752");
		baseClass.stepInfo(
				"Verify, As an user login into the Application, user will be able to modify the existing search in advanced search");
		loginPage.loginToSightLine(username, password);
		int pureHitOfFirstSearch = search.advancedContentSearch(Input.testData1);
		int pureHitOfSecondsearch = search.modifyExistingQueryByOkandCancelBtn("Yes");
		try {
			softAssertion.assertEquals(pureHitOfFirstSearch, pureHitOfSecondsearch);
			baseClass.passedStep("Getting same search results after edit and search the existing query");
			softAssertion.assertAll();
		} catch (Exception e) {
			baseClass.failedStep("Not getting same search results after edit and search the existing query");
		}
		loginPage.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description Verify that Advanced Search is working properly for CreateDate
	 *              Metadata with IS Operator RPMXCON-47639
	 */
	@Test(dataProvider = "SavedSearchwithUsers", groups = { "regression" }, priority = 9)
	public void validateCreateDateMetadata(String username, String password) throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-47639");
		baseClass.stepInfo("Verify that Advanced Search is working properly for CreateDate Metadata with IS Operator");
		loginPage.loginToSightLine(username, password);
		baseClass.selectproject();
		int ExpectedpureHit = search.MetaDataSearchInAdvancedSearch("CreateDate", "2021-09-22");
		try {
			softAssertion.assertNotNull(ExpectedpureHit, "There is No Pure hits occured for the the given search term");
			baseClass.passedStep("Getting Pure hits for the search successfully");
			softAssertion.assertAll();
		} catch (Exception e) {
			baseClass.failedStep("Not getting Pure hits for the search successfully");
		}
		loginPage.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description To verify as an user login into the Application, I will get the
	 *              search result for Conceptual Similar Documents, when I will
	 *              search some query along with all saved search filter applied
	 *              from work product tab of advanced search RPMXCON-47676
	 */
	@Test(dataProvider = "SavedSearchwithUsers", groups = { "regression" }, priority = 10)
	public void validateConceptualDocsForAllSavedSearches(String username, String password)
			throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-47676");
		baseClass.stepInfo(
				"Verify the search result for Conceptual Similar Documents is working properly for all saved search filter of work product");
		loginPage.loginToSightLine(username, password);
		baseClass.selectproject();
		search.switchToWorkproduct();
		baseClass.stepInfo("Navigated to advance search and work product is clicked");
		baseClass.waitForElement(search.getSavedSearchBtn1());
		search.getSavedSearchBtn1().Click();
		search.selectSavedsearchesInTree("Shared with Default Security Group");
		search.selectSavedsearchesInTree("My Saved Search");
		try {
			baseClass.waitForElement(search.getMetaDataInserQuery());
			search.getMetaDataInserQuery().waitAndClick(15);
			baseClass.passedStep("Saved searches are selected from tree and inserted into query");
		} catch (Exception e) {
			baseClass.failedStep("Saved searches are not selected from tree and inserted into query");
		}
		driver.scrollPageToTop();
		baseClass.waitForElement(search.getQuerySearchButton());
		search.getQuerySearchButton().waitAndClick(5);
		int conceptualHits = search.runAndVerifyConceptualSearch();
		try {
			softAssertion.assertNotNull(conceptualHits, "There is No hits occured for the the given search term");
			baseClass.passedStep("Getting conceptual hits for the search successfully");
			softAssertion.assertAll();
		} catch (Exception e) {
			baseClass.failedStep("Not getting conceptual hits for the search successfully");
		}
		loginPage.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description To verify as an user login into the Application, I will get the
	 *              search result for Conceptual Similar Documents, when I will
	 *              search some query along with multiple saved search filter
	 *              applied from work product tab of advanced search RPMXCON-47675
	 */
	@Test(dataProvider = "SavedSearchwithUsers", groups = { "regression" }, priority = 11)
	public void validateConceptualDocsForSingleOrMultipleSavedSearches(String username, String password)
			throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-47675");
		baseClass.stepInfo(
				"Verify the search result for Conceptual Similar Documents is working properly for single or multiple saved search filter of work product");
		loginPage.loginToSightLine(username, password);
		baseClass.selectproject();
		search.switchToWorkproduct();
		baseClass.stepInfo("Navigated to advance search and work product is clicked");
		baseClass.waitForElement(search.getSavedSearchBtn1());
		search.getSavedSearchBtn1().Click();
		search.selectSavedsearchesInTree("Shared with Default Security Group");
		try {
			baseClass.waitForElement(search.getMetaDataInserQuery());
			search.getMetaDataInserQuery().waitAndClick(15);
			baseClass.passedStep("Saved searches are selected from tree and inserted into query");
		} catch (Exception e) {
			baseClass.failedStep("Saved searches are not selected from tree and inserted into query");
		}
		driver.scrollPageToTop();
		baseClass.waitForElement(search.getQuerySearchButton());
		search.getQuerySearchButton().waitAndClick(5);
		int conceptualHits = search.runAndVerifyConceptualSearch();
		try {
			softAssertion.assertNotNull(conceptualHits, "There is No hits occured for the the given search term");
			baseClass.passedStep("Getting conceptual hits for the search successfully");
			softAssertion.assertAll();
		} catch (Exception e) {
			baseClass.failedStep("Not getting conceptual hits for the search successfully");
		}
		loginPage.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description To verify as an user login into the Application, I will get the
	 *              search result for Email thread Documents, when I will search
	 *              some query along with all saved search filter applied from work
	 *              product tab of advanced search RPMXCON-47660
	 */

	@Test(dataProvider = "SavedSearchwithUsers", groups = { "regression" }, priority = 12)
	public void validateThreadedDocsForAllSavedSearches(String username, String password) throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-47660");
		baseClass.stepInfo(
				"Verify the search result for threaded Documents is working properly for all saved search filter of work product");
		loginPage.loginToSightLine(username, password);
		baseClass.selectproject();
		search.switchToWorkproduct();
		baseClass.stepInfo("Navigated to advance search and work product is clicked");
		baseClass.waitForElement(search.getSavedSearchBtn1());
		search.getSavedSearchBtn1().Click();
		search.selectSavedsearchesInTree("Shared with Default Security Group");
		search.selectSavedsearchesInTree("My Saved Search");
		try {
			baseClass.waitForElement(search.getMetaDataInserQuery());
			search.getMetaDataInserQuery().waitAndClick(15);
			baseClass.passedStep("Saved searches are selected from tree and inserted into query");
		} catch (Exception e) {
			baseClass.failedStep("Saved searches are not selected from tree and inserted into query");
		}
		driver.scrollPageToTop();
		baseClass.waitForElement(search.getQuerySearchButton());
		search.getQuerySearchButton().waitAndClick(5);
		String threadedDocs = search.verifyThreadedCount();
		try {
			softAssertion.assertNotNull(threadedDocs, "There is No hits occured for the the given search term");
			baseClass.passedStep("Getting threaded docs Count for the search successfully");
			softAssertion.assertAll();
		} catch (Exception e) {
			baseClass.failedStep("Not getting threaded docs Count for the search successfully");
		}
		loginPage.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description To verify as an user login into the Application, I will get the
	 *              search result for Email thread Documents, when I will search
	 *              some query along with multiple saved search filter applied from
	 *              work product tab of advanced search RPMXCON-47659
	 */

	@Test(dataProvider = "SavedSearchwithUsers", groups = { "regression" }, priority = 13)
	public void validateThreadedDocsForSingleOrMultipleSavedSearches(String username, String password)
			throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-47659");
		baseClass.stepInfo(
				"Verify the search result for threaded Documents is working properly for single or multiple saved search filter of work product");
		loginPage.loginToSightLine(username, password);
		baseClass.selectproject();
		search.switchToWorkproduct();
		baseClass.stepInfo("Navigated to advance search and work product is clicked");
		baseClass.waitForElement(search.getSavedSearchBtn1());
		search.getSavedSearchBtn1().Click();
		search.selectSavedsearchesInTree("Shared with Default Security Group");
		search.selectSavedsearchesInTree("My Saved Search");

		try {
			baseClass.waitForElement(search.getMetaDataInserQuery());
			search.getMetaDataInserQuery().waitAndClick(15);
			baseClass.passedStep("Saved searches are selected from tree and inserted into query");
		} catch (Exception e) {
			baseClass.failedStep("Saved searches are not selected from tree and inserted into query");
		}
		driver.scrollPageToTop();
		baseClass.waitForElement(search.getQuerySearchButton());
		search.getQuerySearchButton().waitAndClick(5);
		String threadedDocs = search.verifyThreadedCount();
		try {
			softAssertion.assertNotNull(threadedDocs, "There is No Pure hits occured for the the given search term");
			baseClass.passedStep("Getting threaded docs Count for the search successfully");
			softAssertion.assertAll();
		} catch (Exception e) {
			baseClass.failedStep("Not getting threaded docs Count for the search successfully");
		}
		loginPage.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description To verify, As an PA login into the Application, when user select
	 *              a multiple production with production status as "Already
	 *              Produced" from work product tab, he will be able to set that as
	 *              an search criteria
	 */
	@Test(groups = { "regression" }, priority = 14)
	public void VerifySearchCriteriaWithMultipleProductions() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-47755");
		baseClass.stepInfo("Verify user can set search criteria with multiple productions");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		String productionname = "p" + Utility.dynamicNameAppender();

		driver.getWebDriver().get(Input.url + "Search/Searches");
		search.switchToWorkproduct();
		search.selectMultipleProductionstWithAlreadyProducedFilter(productionname);
		search.verifySearchQueryForAlreadyProducedFilter();
		baseClass.passedStep(
				"Search criteria is set by selecting Multiple Production and Already Produced Optional Filter.");
		loginPage.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description To verify, As an PA login into the Application, when user select
	 *              a multiple production with production status as "Selected for
	 *              productions" from work product tab, he will be able to set that
	 *              as an search criteria
	 */
	@Test(groups = { "regression" }, priority = 15)
	public void VerifySearchCriteriaWithMultipleProductionsAndOptionalFilter() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-47754");
		baseClass.stepInfo("Verify user can set search criteria with multiple productions");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		String productionname = "p" + Utility.dynamicNameAppender();

		driver.getWebDriver().get(Input.url + "Search/Searches");
		search.switchToWorkproduct();
		search.selectMultipleProductionstWithSelectedForProductionFilter(productionname);
		search.verifySearchQueryForSelectedForProductionFilter();
		baseClass.passedStep(
				"Search criteria is set by selecting Multiple Production and Selected For Productions Optional Filter.");
		loginPage.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description Verify Audio threshold value saved correctly under My Saved
	 *              Search
	 */
	@Test(dataProvider = "AudioSearchwithUsers", groups = { "regression" }, priority = 16)
	public void VerifyAudio_ModifySearchInMySavedSearch(String username, String password, String ImpersonateOption)
			throws InterruptedException {
		String Search1 = "AudioSearchONE" + Utility.dynamicNameAppender();
		String Search2 = "AudioSearcTWO" + Utility.dynamicNameAppender();
		String Search3 = "AudioSearchThree" + Utility.dynamicNameAppender();
		baseClass.stepInfo("Test case Id: RPMXCON-46981");
		baseClass.stepInfo("Verify Audio threshold value saved correctly under My saved search");
		loginPage.loginToSightLine(username, password);
		if (ImpersonateOption == "PA") {
			baseClass.impersonateSAtoPA();
			baseClass.stepInfo("Logged in as SA and impersonated as PA");
		}
		if (ImpersonateOption == "RMU") {
			baseClass.impersonateSAtoRMU();
			baseClass.stepInfo("Logged in as SA and impersonated as RMU");
		}
		String ActualThresholdValue1 = search.VerifyaudioSearchThreshold("right", "North American English", "min");
		String actualPH1 = search.verifyPureHitsCount();
		baseClass.stepInfo(
				"Audio Search is done PureHit is : " + actualPH1 + "Threshold value is " + ActualThresholdValue1);
		search.saveSearchAdvanced_New(Search1, "My Saved Search");
		search.ModifyAudioSearch("morning well", "North American English", "max");
		String actualThreshold2 = search.GetConfidenceThresholdInSecondSearchResult().getText();
		String actualPH2 = search.getPureHitsCount_ModifySearch().getText();
		baseClass.stepInfo("Audio Search is done PureHit is : " + actualPH2 + "Threshold value is " + actualThreshold2);
		System.out.println("Audio Search is done PureHit is : " + actualPH2 + "Threshold value is " + actualThreshold2);
		search.saveSearchAdvanced_New(Search2, "My Saved Search");
		search.ModifyAudioSearch(null, "North American English", "min");
		String actualThreshold3 = search.GetConfidenceThresholdInSecondSearchResult().getText();
		String actualPH3 = search.getPureHitsCount_ModifySearch().getText();
		baseClass.stepInfo("Audio Search is done PureHit is : " + actualPH3 + "Threshold value is " + actualThreshold3);
		search.saveSearchAdvanced_New(Search3, "My Saved Search");
		try {
			savedSearch.savedSearch_Searchandclick(Search1);
			driver.waitForPageToBeReady();
			savedSearch.VerifyPureHitInSavedAudiosearch(Search1);
			softAssertion.assertEquals(ActualThresholdValue1, savedSearch.VerifyThresholdValueInSavedAudiosearch(Search1));
			softAssertion.assertEquals(actualPH1, savedSearch.VerifyPureHitInSavedAudiosearch(Search1));
			baseClass.passedStep(
					"Sucessfuly Verified the threshold value and PureHit Count for modified Audio search Displays as Expected in"
							+ " saved search page");
			savedSearch.savedSearch_Searchandclick(Search2);
			driver.waitForPageToBeReady();
			softAssertion.assertEquals(actualPH2, savedSearch.VerifyPureHitInSavedAudiosearch(Search2));
			softAssertion.assertEquals(actualThreshold2, savedSearch.VerifyThresholdValueInSavedAudiosearch(Search2));
			baseClass.passedStep(
					"Sucessfuly Verified the threshold value and PureHit Count for modified Audio search Displays as Expected in"
							+ " saved search page");
			savedSearch.savedSearch_Searchandclick(Search3);
			driver.waitForPageToBeReady();
			softAssertion.assertEquals(actualPH3, savedSearch.VerifyPureHitInSavedAudiosearch(Search3));
			softAssertion.assertEquals(actualThreshold3, savedSearch.VerifyThresholdValueInSavedAudiosearch(Search3));
			baseClass.passedStep(
					"Sucessfuly Verified the threshold value and PureHit Count for modified Audio search Displays as Expected in"
							+ " saved search page");
			softAssertion.assertAll();
			loginPage.logout();
		} catch (Exception e) {
			baseClass.failedStep("Threshold or PureHit count mismatched from sessions search page");

		}

	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description Verify Audio threshold value saved correctly under Shared folder
	 */
	@Test(dataProvider = "AudioSearchwithUsers", groups = { "regression" }, priority = 17)
	public void VerifyAudio_ModifySearchInSharedFolder(String username, String password, String ImpersonateOption)
			throws InterruptedException {
		String Search1 = "AudioSearchONE" + Utility.dynamicNameAppender();
		String Search2 = "AudioSearcTWO" + Utility.dynamicNameAppender();
		String Search3 = "AudioSearchThree" + Utility.dynamicNameAppender();
		baseClass.stepInfo("Test case Id: RPMXCON-46982");
		baseClass.stepInfo("Verify Audio threshold value saved correctly under  Shared folder");
		loginPage.loginToSightLine(username, password);
		if (ImpersonateOption == "PA") {
			baseClass.impersonateSAtoPA();
		}
		if (ImpersonateOption == "RMU") {
			baseClass.impersonateSAtoRMU();
		}
		String ActualThresholdValue1 = search.VerifyaudioSearchThreshold("right", "North American English", "min");
		String actualPH1 = search.verifyPureHitsCount();
		baseClass.stepInfo(
				"Audio Search is done PureHit is : " + actualPH1 + "Threshold value is " + ActualThresholdValue1);
		search.saveSearchAdvanced_New(Search1, "Shared with Default Security Group");
		search.ModifyAudioSearch("morning well", "German", "max");
		String actualThreshold2 = search.GetConfidenceThresholdInSecondSearchResult().getText();
		String actualPH2 = search.getPureHitsCount_ModifySearch().getText();
		baseClass.stepInfo("Audio Search is done PureHit is : " + actualPH2 + "Threshold value is " + actualThreshold2);
		System.out.println("Audio Search is done PureHit is : " + actualPH2 + "Threshold value is " + actualThreshold2);
		search.saveSearchAdvanced_New(Search2, "Shared with Default Security Group");
		search.ModifyAudioSearch(null, "North American English", "min");
		String actualThreshold3 = search.GetConfidenceThresholdInSecondSearchResult().getText();
		String actualPH3 = search.getPureHitsCount_ModifySearch().getText();
		baseClass.stepInfo("Audio Search is done PureHit is : " + actualPH3 + "Threshold value is " + actualThreshold3);
		search.saveSearchAdvanced_New(Search3, "Shared with Default Security Group");
		try {
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			driver.waitForPageToBeReady();
			baseClass.waitForElement(savedSearch.getSavedSearchGroupName(Input.securityGroup));
			savedSearch.getSavedSearchGroupName(Input.securityGroup).waitAndClick(10);
			savedSearch.savedSearch_SearchandSelect(Search1, "yes");
			driver.waitForPageToBeReady();
			savedSearch.VerifyPureHitInSavedAudiosearch(Search1);
			softAssertion.assertEquals(ActualThresholdValue1, savedSearch.VerifyThresholdValueInSavedAudiosearch(Search1));
			softAssertion.assertEquals(actualPH1, savedSearch.VerifyPureHitInSavedAudiosearch(Search1));
			baseClass.passedStep(
					"Sucessfuly Verified the threshold value and PureHit Count for modified Audio search Displays as Expected in"
							+ " saved search page");
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			baseClass.waitForElement(savedSearch.getSavedSearchGroupName(Input.securityGroup));
			savedSearch.getSavedSearchGroupName(Input.securityGroup).waitAndClick(10);
			savedSearch.savedSearch_SearchandSelect(Search2, "yes");
			driver.waitForPageToBeReady();
			softAssertion.assertEquals(actualPH2, savedSearch.VerifyPureHitInSavedAudiosearch(Search2));
			softAssertion.assertEquals(actualThreshold2, savedSearch.VerifyThresholdValueInSavedAudiosearch(Search2));
			baseClass.passedStep(
					"Sucessfuly Verified the threshold value and PureHit Count for modified Audio search Displays as Expected in"
							+ " saved search page");
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			baseClass.waitForElement(savedSearch.getSavedSearchGroupName(Input.securityGroup));
			savedSearch.getSavedSearchGroupName(Input.securityGroup).waitAndClick(10);
			savedSearch.savedSearch_SearchandSelect(Search3, "yes");
			driver.waitForPageToBeReady();
			softAssertion.assertEquals(actualPH3, savedSearch.VerifyPureHitInSavedAudiosearch(Search3));
			softAssertion.assertEquals(actualThreshold3, savedSearch.VerifyThresholdValueInSavedAudiosearch(Search3));
			baseClass.passedStep(
					"Sucessfuly Verified the threshold value and PureHit Count for modified Audio search Displays as Expected in"
							+ " saved search page");
			softAssertion.assertAll();
			loginPage.logout();
		} catch (Exception e) {
			baseClass.failedStep("Threshold or PureHit count mismatched from sessions search page");

		}

	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description Verify that Given language get selected from Language Pack /
	 *              Dialect list box
	 * 
	 */
	@Test(dataProvider = ("DDLanguage"), groups = { "regression" }, priority = 18)
	public void VerifyAudioSearchLanguageDropdown(String Language) throws InterruptedException {
		if (Language == "German") {
			baseClass.stepInfo("Test case Id: RPMXCON-47707");
		}
		if (Language == "International English") {
			baseClass.stepInfo("Test case Id: RPMXCON-47706");
		}
		baseClass.stepInfo(
				"Verify that " + Language + " language get selected from Language Pack / Dialect list box\r\n" + "");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		search.VerifyaudioSearchDropdown(Language);
		loginPage.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description To verify, as an user login into the Application, I will be able
	 *              to set the default precision for concept search in advanced
	 *              search & get the search results based on that .
	 */
	@Test(dataProvider = ("SavedSearchwithUsers"), groups = { "regression" }, priority = 19)
	public void VerifyDefaultConceptualSearch(String username, String password) throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-47745");
		baseClass.stepInfo("To verify, as an user login into the Application, I will be able to set the default "
				+ "precision for concept search in advanced search & get the search results based on that");
		loginPage.loginToSightLine(username, password);
		search.conceptualSearch_new("field report", "mid");
		loginPage.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description Verify that "Conceptually Similar" tile result appears when user
	 *              configured query with Advanced Search option (All threaded email
	 *              documents) and clicks on "Conceptually Similar" tile.
	 *              RPMXCON-57246
	 */
	@Test(groups = { "regression" }, priority = 20)
	public void verifyConceptualCountUsingAllthreadedAdvOption() throws InterruptedException {
        baseClass.stepInfo("Test case Id: RPMXCON-57246");
        baseClass.stepInfo("Verify that Conceptually Similartile result appears when user"
                + " configured query with Advanced Search option (All threaded email documents) and clicks on Conceptually Similar tile");
        loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
        baseClass.selectproject();
        search.conceptualSimilarSearchInAdvancedSearch("CustodianName", "Andrew", search.getadvoption_threaded());
        if (search.getThreadedCount().isElementAvailable(3) == false) {
            baseClass.passedStep("Pure hits for threaded documents are not displayed in search results");
        } else {
            baseClass.failedStep("Pure hits for threaded documents are displayed in search results");
        }
        loginPage.logout();
    }

	 /**
     * @author Jayanthi.ganesan
     * @throws InterruptedException
     * @description Verify that "Conceptually Similar" tile result appears when user
     *              configured query with Advanced Search option (Family member
     *              documents) and clicks on "Conceptually Similar" tile.
     *              RPMXCON-57247
     */
    @Test(groups = { "regression" }, priority = 21)
    public void verifyConceptualCountUsingFamilyMembersAdvOption() throws InterruptedException {
        baseClass.stepInfo("Test case Id: RPMXCON-57247");
        baseClass.stepInfo("Verify that Conceptually Similartile result appears when user"
                + " configured query with Advanced Search option (Family member documents) and clicks on Conceptually Similar tile");
        loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
        baseClass.selectproject();
        search.conceptualSimilarSearchInAdvancedSearch("CustodianName", "Andrew", search.getadvoption_family());
        if (search.getFamilyCount().isElementAvailable(3) == false) {
            baseClass.passedStep("Pure hits for family members are not displayed in search results");
        } else {
            baseClass.failedStep("Pure hits for family members are displayed in search results");
        }
        loginPage.logout();
    }
    /**
     * @author Jayanthi.ganesan
     * @throws InterruptedException
     * @description Verify that "Conceptually Similar" tile result appears when user
     *              configured query with Advanced Search option (90% or higher
     *              textual near duplicate documents) and clicks on "Conceptually
     *              Similar" tile. RPMXCON-57248
     */
    @Test(groups = { "regression" }, priority = 22)
    public void verifyConceptualCountUsingNearDupeDocsAdvOption() throws InterruptedException {
        baseClass.stepInfo("Test case Id: RPMXCON-57248");
        baseClass.stepInfo("Verify that Conceptually Similartile result appears when user"
                + " configured query with Advanced Search option (90% or higher textual near duplicate documents) and clicks on Conceptually Similar tile");
        loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
        baseClass.selectproject();
        search.conceptualSimilarSearchInAdvancedSearch("CustodianName", "Andrew", search.getadvoption_near());
        if (search.getNearDupeCount().isElementAvailable(3) == false) {
            baseClass.passedStep("Pure hits for near dupes are not displayed in search results");
        } else {
            baseClass.failedStep("Pure hits for near dupes are displayed in search results");
        }
        loginPage.logout();
    }

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description To verify as an user login into the Application, User can be
	 *              able to search by Applying By Pass sanitization filter on
	 *              Folders in work product tab TestCaseNo-47784
	 */
	@Test(groups = { "regression" }, priority = 23)
	public void verifySantizationFilterForFoldersInwp() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-47784");
		baseClass.stepInfo(
				"To verify as an user login into the Application, User can be able to search by Applying By Pass sanitization filter on Folders in work product tab");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.selectproject();
		search.sanitizationWithFolder();
		if (search.getWarningPopupMsg().Displayed()) {
			baseClass.failedStep(
					"User cannot able to search by Applying By Pass sanitization filter on Folders in work product tab");
		} else {
			baseClass.passedStep(
					"User can be able to search by Applying By Pass sanitization filter on Folders in work product tab");

		}
		loginPage.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description To verify as an user login into the Application, User can be
	 *              able to search by Applying By Pass sanitization filter on Saved
	 *              search in work product tab TestCaseNo-47785
	 */
	@Test(groups = { "regression" }, priority = 24)
	public void verifySantizationFilterForSavedSearchInwp() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-47785");
		baseClass.stepInfo(
				"To verify as an user login into the Application, User can be able to search by Applying By Pass sanitization filter on Saved search in work product tab");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.selectproject();
		search.sanitizationWithSavedSearch();

		if (search.getWarningPopupMsg().Displayed()) {
			baseClass.failedStep(
					"User cannot able to search by Applying By Pass sanitization filter on Saved search in work product tab");
		} else {
			baseClass.passedStep(
					"User can be able to search by Applying By Pass sanitization filter on Saved search in work product tab");
		}
		loginPage.logout();
	}

	@DataProvider(name = "DDLanguage")
	public Object[][] Audiosearch_Language() {
		Object[][] language = { { "German" }, { "International English" } };
		return language;
	}

	@DataProvider(name = "AudioSearchwithUsers")
	public Object[][] AudioSearchwithUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, null },
				{ Input.rmu1userName, Input.rmu1password, null }, { Input.sa1userName, Input.sa1password, "PA" },
				{ Input.sa1userName, Input.sa1password, "RMU" } };
		return users;
	}

	@DataProvider(name = "SavedSearchwithUsers")
	public Object[][] SavedSearchwithUsers() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password }, { Input.pa1userName, Input.pa1password },
				{ Input.rev1userName, Input.rev1password } };
		return users;
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		try {
//			LoginPage.clearBrowserCache();

		} catch (Exception e) {
			System.out.println("Sessions already closed");
		}
	}

}