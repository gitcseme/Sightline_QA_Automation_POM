package sightline.savedSearch;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
import automationLibrary.Element;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.BatchPrintPage;
import pageFactory.Categorization;
import pageFactory.DocListPage;
import pageFactory.DocumentAuditReportPage;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.ProductionPage;
import pageFactory.ReportsPage;
import pageFactory.SavedSearch;
import pageFactory.SchedulesPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class SavedSearch_Phase1_AudioRegresssion {
	Driver driver;
	LoginPage login;
	SavedSearch saveSearch;
	SessionSearch session;
	BaseClass base;
	SecurityGroupsPage sg;
	SoftAssert softAssertion;
	ReportsPage report;
	TagsAndFoldersPage tagsAndFolderPage;
	Categorization categorize;
	AssignmentsPage assign;
	ProductionPage page;
	BatchPrintPage batch;
	DocListPage dcPage;
	MiniDocListPage miniDocListpage;

	public static int purehits;
	String folderName = "Folder" + Utility.dynamicNameAppender();
	String searchName = "Search Name" + Utility.dynamicNameAppender();
	String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();
	String searchNameRMU = "RMU" + Utility.dynamicNameAppender();
	String securitygroupname = "securitygroupname" + Utility.dynamicNameAppender();

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		Input in = new Input();
		in.loadEnvConfig();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod)
			throws IOException, ParseException, InterruptedException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.logBefore(testMethod.getName());

		// Open browser
		driver = new Driver();
		base = new BaseClass(driver);
		login = new LoginPage(driver);
		session = new SessionSearch(driver);
		saveSearch = new SavedSearch(driver);
		sg = new SecurityGroupsPage(driver);
		softAssertion = new SoftAssert();
		dcPage = new DocListPage(driver);
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
	}

	/**
	 * @author Jayanthi.ganesan Modified date:9/22/21 Modified by: Raghuram A -
	 *         modified Dataprovider parameter
	 * @throws InterruptedException
	 * @description Renames an existing Advanced saved search on Saved Search
	 *              Screen. RPMXCON-49351
	 */
	@DataProvider(name = "SavedSearchwithUsers") // commented for Stabilization check
	public Object[][] SavedSearchwithUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName } };
		return users;
	}

	@DataProvider(name = "TestUser") // commented for Stabilization check
	public Object[][] TestUser() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName } };
		return users;
	}

	/**
	 * @author Raghuram A Date: 9/27/21 Modified date: N/A Modified by: Description
	 *         : TC05_Verify that application does not display any warnings when a
	 *         user execute search group which contains only Advanced Search(es) in
	 *         Draft and Completed state on Saved Search Screen. -RPMXCON-49829
	 *         Sprint 03 - new imp - done
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-49829", enabled = true, groups = { "regression" }) // need to check with raghu
	public void verifyAdvancesearch() throws InterruptedException {
		String Search1 = "search" + Utility.dynamicNameAppender();
		String Search2 = "search" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-49829 - Saved Search Sprint 03");
		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// create new searchgroup
		String newNode = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "PA", "No");
		System.out.println(newNode);

		String newNode1 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "PA", "No");
		System.out.println(newNode1);

		driver.getWebDriver().get(Input.url + "Search/Searches");

		int pureHit1 = session.advancedContentSearchWithSearchChanges(Input.searchString1, "Yes");
		System.out.println(pureHit1);
		session.saveSearchInNewNode(Search1, newNode);
		session.getNewSearchButton().waitAndClick(5);

		int pureHit2 = session.advancedContentSearchWithSearchChanges(Input.searchString1, "No");
		System.out.println(pureHit2);
		session.saveSearchInNewNode(Search2, newNode1);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.selectNode1(newNode);

		saveSearch.savedSearch_SearchandSelect(Search1, "Yes");
		saveSearch.savedSearchExecute_Draft(Search1, pureHit1);
		driver.Navigate().refresh();
		saveSearch.selectNode1(newNode1);

		saveSearch.savedSearch_SearchandSelect(Search2, "Yes");
		saveSearch.savedSearchExecute_Draft(Search2, pureHit2);

		base.stepInfo("Initiating delete search");
		saveSearch.deleteNode(Input.mySavedSearch, newNode);
		saveSearch.deleteNode(Input.mySavedSearch, newNode1);

		login.logout();

	}

	/**
	 * @author Jeevitha R Date: 9/27/21 Modified date: N/A Modified by: Description
	 *         : TC03_Verify that application does not display any warnings when a
	 *         user execute search group which contains Basic and Advanced
	 *         Search(es) in Draft state on Saved Search Screen. -RPMXCON-49831
	 *         Sprint 03 - new imp - done
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-49831", enabled = true, groups = { "regression" }) // check with jeevitha
	public void verifyBasicAdvance_save() throws InterruptedException {
		String Search1 = "search" + Utility.dynamicNameAppender();
		String Search2 = "search" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-49831 - Saved Search Sprint 03");
		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// create new searchgroup
		String newNode = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "PA", "No");
		System.out.println(newNode);

		String newNode1 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "PA", "No");
		System.out.println(newNode1);

		// Draft Query
		driver.getWebDriver().get(Input.url + "Search/Searches");
		int purehit = session.basicContentSearchWithSaveChanges(Input.searchString1, "No", "First");
		System.out.println(purehit);
		session.saveSearchInNewNode(Search1, newNode);

		session.getNewSearchButton().waitAndClick(5);
		int pureHit2 = session.advancedContentSearchWithSearchChanges(Input.searchString1, "No");
		System.out.println(pureHit2);
		session.saveSearchInNewNode(Search2, newNode1);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.selectNode1(newNode);

		saveSearch.savedSearch_SearchandSelect(Search1, "Yes");
		saveSearch.savedSearchExecute_Draft(Search1, purehit);
		driver.Navigate().refresh();
		saveSearch.selectNode1(newNode1);

		saveSearch.savedSearch_SearchandSelect(Search2, "Yes");
		saveSearch.savedSearchExecute_Draft(Search2, pureHit2);

		base.stepInfo("Initiating delete search");
		saveSearch.deleteNode(Input.mySavedSearch, newNode);
		saveSearch.deleteNode(Input.mySavedSearch, newNode1);

		login.logout();

	}

	/**
	 * @author Jeevitha Description: TC04_Verify that application does not display
	 *         any warnings when a user execute search group which contains only
	 *         Basic Search(es) in Draft and Completed state on Saved Search
	 *         Screen.(RPMXCON-49830)
	 * @Modified on: 12/4/21 by : Jeevitha - new imp done
	 * @param username
	 * @param password
	 * @param fullname
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-49830", enabled = true, groups = { "regression" })
	public void saveSearchBasicDraft() throws InterruptedException, ParseException {
		String search = "Search6" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-49830 - Saved Search Sprint 03");
		// Login as PA
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// create new searchgroup
		String newNode = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "PA", "No");
		System.out.println(newNode);

		String newNode1 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "PA", "No");
		System.out.println(newNode1);

		// Draft Query
		driver.getWebDriver().get(Input.url + "Search/Searches");
		int purehit = session.basicContentSearchWithSaveChanges(Input.searchString1, "No", "First");
		System.out.println(purehit);
		session.saveSearchInNewNode(search, newNode);

		int purehit2 = session.searchAndReturnPureHit_BS();
		session.saveSearchInNewNode(search, newNode1);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.selectNode1(newNode);

		saveSearch.savedSearch_SearchandSelect(search, "Yes");
		saveSearch.savedSearchExecute_Draft(search, purehit);

		saveSearch.selectNode1(newNode1);

		saveSearch.savedSearch_SearchandSelect(search, "Yes");
		saveSearch.savedSearchExecute_Draft(search, purehit2);

		base.stepInfo("Initiating delete search");
		saveSearch.deleteNode(Input.mySavedSearch, newNode);
		saveSearch.deleteNode(Input.mySavedSearch, newNode1);

		login.logout();

	}

	/**
	 * @author Raghuram A Date: 9/27/21 Modified date:N/A Modified by: Description :
	 *         Validate UI changes on Saved Search screen -RPMXCON-49842 Sprint 03
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-49842", enabled = true, dataProvider = "SavedSearchwithUsers", groups = {
			"regression" })
	public void UIchangesonSSScreen(String userName, String password, String fullName) throws InterruptedException {
		base.stepInfo("Test case Id: RPMXCON-49842 - Saved Search Sprint 03");
		// Login as PA
		login.loginToSightLine(userName, password);
		base.stepInfo("Loggedin As : " + fullName);

		saveSearch.UIchangesVerificationFlow(userName);

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 9/28/21 Modified date:N/A Modified by: Description :
	 *         Validate saving executed search (executed or draft) under My Searches
	 *         or Shared Searches -RPMXCON-49875 Sprint 03
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-49875", enabled = true, groups = { "regression" })
	public void validateSavingExecutedSearch() throws InterruptedException {

		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();
		String SGtoShare = Input.shareSearchDefaultSG;

		base.stepInfo("Test case Id: RPMXCON-49875 - Saved Search Sprint 03");
		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName + "for Pre-requesties");

		// create new searchgroup
		String newNode = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "PA", "No");

		// add save search in node
		int purehit = session.basicContentSearch(Input.searchString1);
		session.saveSearchInNewNode(searchName1, newNode);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.selectNode1(newNode);

		saveSearch.savedSearch_SearchandSelect(searchName1, "No");

		// Search and SaveSearch
		saveSearch.shareSavedNodePA(SGtoShare, newNode, true, true, searchName1);
		driver.waitForPageToBeReady();

		login.logout();
		base.stepInfo("-----Pre-requesties completed-----");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.selectNode1(newNode);
		saveSearch.savedSearch_SearchandSelect(searchName1, "Yes");
		saveSearch.savedSearchExecute2(searchName1, purehit);

		saveSearch.getSavedSearchEditButton().waitAndClick(5);
		driver.waitForPageToBeReady();

		saveSearch.validatingSaves(SGtoShare, newNode, searchName1);

		login.logout();
	}

	/**
	 * @author Jeevitha Description: Verify that basic Batch Search Upload returns
	 *         correct count on any standard data set.(RPMXCON-49076)
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-49076", enabled = true, dataProvider = "SavedSearchwithUsers", groups = {
			"regression" })
	public void saveSearchBatchUpload(String username, String password, String fullName) throws InterruptedException {
		String search = "Search" + Utility.dynamicNameAppender();
		base.stepInfo("Test case Id: RPMXCON-49076 - Saved Search Sprint 03");

		// Login as PA
		login.loginToSightLine(username, password);

		// create new search group
		String newNode = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, fullName, "No");
		saveSearch.uploadBatchFile_New(saveSearch.renameFile(Input.batchFileNewLocation));

		driver.getWebDriver().get(Input.url + "Search/Searches");
		session.basicContentSearchWithSaveChanges("pipeline", "No", "First");
		session.selectOperatorInBasicSearch("OR");
		int pureHit = session.basicContentSearchWithSaveChanges("gas", "Yes", "Third");
		session.saveSearchInNode(search);

		saveSearch.navigateToSavedSearchPage();
		saveSearch.selectNode1(newNode);
		saveSearch.savedSearch_SearchandSelect(search, "Yes");
		String CountOfDoc = saveSearch.getCountofDocs().getText();
		softAssertion.assertEquals(pureHit, Integer.parseInt(CountOfDoc));
		softAssertion.assertAll();

		saveSearch.navigateToSavedSearchPage();
		base.stepInfo("Initiatind delete node");
		saveSearch.deleteNode(Input.mySavedSearch, newNode);

		login.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description Verify that relevant message appears when user Navigate -
	 *              Advanced Search(Audio) - DRAFT Query >> Edit from saved search
	 *              page on Search Screen. RPMXCON-48636
	 */
	@Test(description = "RPMXCON-48636", enabled = true, groups = { "regression" })
	public void VerifyReleventMessageInAudioDraftQuery() throws InterruptedException {
		base.stepInfo("Test case Id:RPMXCON-48636");
		base.stepInfo(
				"Verify that relevant message appears when user Navigate - Advanced Search(Audio) - DRAFT Query >> "
						+ "Edit from saved search page on Search Screen");

		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		String DraftQuery = "AudioDraftquery" + Utility.dynamicNameAppender();
		session.CreateDraftaudioSearchQuery(Input.audioSearchString1, "North American English");
		session.saveDraftQuery(DraftQuery);
		saveSearch.savesearch_Edit(DraftQuery);
		base.waitForElementToBeGone(session.getspinningWheel(), 20);
		if (session.getspinningWheel().isElementAvailable(1) == false) {
			base.passedStep("Continues spinning Wheel is not present");
		} else {
			base.failedStep("Continues spinning Wheel is present");
		}
		session.verifyReleventMessage_draftQuery();

		login.logout();

	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description Verify that relevant message appears when user Navigate -
	 *              Advanced Search(Conceptual) - DRAFT Query >> Edit from saved
	 *              search page on Search Screen. RPMXCON-48637
	 */
	@Test(description = "RPMXCON-48637", enabled = true, groups = { "regression" })
	public void VerifyReleventMessageInConceptualDraftQuery() throws InterruptedException {
		base.stepInfo("Test case Id:RPMXCON-48637");
		base.stepInfo(
				"Verify that relevant message appears when user Navigate - Advanced Search(Conceptual) - DRAFT Query >> "
						+ "Edit from saved search page on Search Screen");
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		String DraftQuery = "ConceptualDraftquery" + Utility.dynamicNameAppender();
		session.CreateDraftConceptualsearch("field report");
		session.saveDraftQuery(DraftQuery);
		saveSearch.savesearch_Edit(DraftQuery);
		base.waitForElementToBeGone(session.getspinningWheel(), 20);
		if (session.getspinningWheel().isElementAvailable(1) == false) {
			base.passedStep("Continues spinning Wheel is not present");
		} else {
			base.failedStep("Continues spinning Wheel is present");
		}
		session.verifyReleventMessage_draftQuery();

		login.logout();

	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description Verify that relevant message appears when user Navigate -
	 *              Advanced Search(WorkProduct) - DRAFT Query >> Edit from saved
	 *              search page on Search Screen. RPMXCON-RPMXCON-48638
	 */
	@Test(description = "RPMXCON-48638", enabled = true, groups = { "regression" })
	public void VerifyReleventMessageInWorkproductDraftQuery() throws InterruptedException {
		base.stepInfo("Test case Id:RPMXCON-48638");
		base.stepInfo("Verify that relevant message appears when user Navigate - Advanced Search(WorkProduct) - "
				+ "DRAFT Query >> Edit from saved search page on Search Screen");

		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		TagsAndFoldersPage tagPage = new TagsAndFoldersPage(driver);
		String DraftQuery = "Draftquery" + Utility.dynamicNameAppender();
		String TagName = "DraftTag" + Utility.dynamicNameAppender();
		tagPage.CreateTag(TagName, "Default Security Group");
		session.basicContentSearch(Input.searchString2);
		session.bulkTagExisting(TagName);
		base.selectproject();
		session.switchToWorkproduct();
		session.selectTagInASwp(TagName);
		base.stepInfo("Configured a search query on Tag -" + TagName + " ");
		session.saveDraftQuery(DraftQuery);
		saveSearch.savesearch_Edit(DraftQuery);
		base.waitForElementToBeGone(session.getspinningWheel(), 20);
		if (session.getspinningWheel().isElementAvailable(1) == false) {
			base.passedStep("Continues spinning Wheel is not present");
		} else {
			base.failedStep("Continues spinning Wheel is present");
		}
		session.verifyReleventMessage_draftQuery();

		login.logout();

	}

	/**
	 * @author Raghuram A Date: 9/29/21 Modified date:10/7/21 Modified by:Raghuram
	 * @Description : Verify that After adding Saved Query - application displays
	 *              all documents that are in the aggregate results - when User
	 *              Performs Bulk Tag from Child Search groups -RPMXCON-49065 Sprint
	 *              03
	 * @throws InterruptedException
	 * @throws ParseException
	 */

	@Test(description = "RPMXCON-49065", enabled = true, groups = { "regression" })
	public void aggregateResultWhileBulkTag() throws InterruptedException, ParseException {
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		String TagName = "Tag" + Utility.dynamicNameAppender(), finalCount;
		Boolean inputValue = true;
		int noOfNodesToCreate = 6, pureHit, finalCountresult;
		int latencyCheckTime = 5;
		String passMessage = "Application not hang or shows latency more than " + latencyCheckTime + " seconds.";
		String failureMsg = "Continues Loading more than " + latencyCheckTime + " seconds.";
		base.stepInfo("Test case Id: RPMXCON-49065 - Saved Search Sprint 03");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Landed on Saved Search
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		// Multiple Node Creation
		newNodeList = saveSearch.createSGAndReturn("PA", "No", noOfNodesToCreate);
		System.out.println("Next adding searches to the created nodes");
		base.stepInfo("Next adding searches to the created nodes");

		// add save search in node
		driver.getWebDriver().get(Input.url + "Search/Searches");
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);

		base.stepInfo("-------Pre-requesties completed--------");
		login.logout();

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		base.stepInfo("Root node : " + newNodeList.get(0));
		saveSearch.selectNode1(newNodeList.get(0));
		saveSearch.getSavedSearchToBulkTag().Click();
		base.stepInfo("Clicked tag icon from Code");

		// Load latency Verification
		Element loadingElement = saveSearch.getTotalCountLoad();
		saveSearch.loadingCountVerify(loadingElement, latencyCheckTime, passMessage, failureMsg);
		finalCount = session.bulkActions_TagSS_returnCount(TagName);
		base.stepInfo("Completed Bulk Tag");
		base.stepInfo(
				"Navigating to Search >> Basic Search >> Advanced Search >> WorkProduct >> Tags (Select Same Tag which we have created in prerequesties.");
		session.switchToWorkproduct();
		session.selectTagInASwp(TagName);
		pureHit = session.serarchWP();
		finalCountresult = Integer.parseInt(finalCount);
		base.digitCompareEquals(pureHit, finalCountresult,
				"After the Bulk Tag - Pure hit appear like aggregate results set of all child search groups and searches   s",
				"Count Mismatches");

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 10/6/21 Modified date:N/A Modified by: Description :
	 *         Verify that After adding Saved Query - application displays all
	 *         documents that are in the aggregate results when User Navigate Child
	 *         Search groups to DocList -RPMXCON-49064 Sprint 03
	 * @throws InterruptedException
	 * @throws ParseException
	 */

	@Test(description = "RPMXCON-49064", enabled = true, groups = { "regression" })
	public void aggregateResultDocsINDocList() throws InterruptedException, ParseException {
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		Boolean inputValue = true;
		int finalCountresult;
		base.stepInfo("Test case Id: RPMXCON-49064 - Saved Search Sprint 03");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Landed on Saved Search
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		// Multiple Node Creation
		newNodeList = saveSearch.createSGAndReturn("PA", "No", 6);
		System.out.println("Next adding searches to the created nodes");
		base.stepInfo("Next adding searches to the created nodes");

		// add save search in node
		driver.getWebDriver().get(Input.url + "Search/Searches");
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);

		base.stepInfo("-------Pre-requesties completed--------");
		login.logout();

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Calculate the unique doc count
		driver.getWebDriver().get(Input.url + "Search/Searches");
		session.basicContentSearchWithSaveChanges(Input.searchString5, "No", "First");
		base.hitEnterKey(1);
		session.selectOperatorInBasicSearch("OR");
		int hitCount = session.basicContentSearchWithSaveChanges(Input.searchString6, "Yes", "Third");
		base.stepInfo("Unique doc PureHit count : " + hitCount);

		// Launch DocList via Saved Search
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		System.out.println("Root node : " + newNodeList.get(0));
		base.stepInfo("Root node : " + newNodeList.get(0));
		saveSearch.selectNode1(newNodeList.get(0));

		// Get the count of total no.of document list
		finalCountresult = saveSearch.launchDocListViaSSandReturnDocCount();

		base.digitCompareEquals(hitCount, finalCountresult,
				"Shows all documents that are in the aggregate results set of all child search groups and searches",
				"Count Mismatches");

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 9/30/21 Modified date:N/A Modified by: Description :
	 *         For RMU - Validate sharing search group to a Security Group from any
	 *         of the node in the group hierarchy -RPMXCON-49881 Sprint 03
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-49881", enabled = true, groups = { "regression" })
	public void hierarchyRandomCheck() throws InterruptedException, ParseException {
		int noOfNodesToCreate = 6;
		int selectIndex = 2;
		String SGtoShare = Input.shareSearchDefaultSG;
		String node;
		Boolean inputValue = true;
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		HashMap<String, String> searchGroupSearchpIDpair = new HashMap<>();

		base.stepInfo("Test case Id: RPMXCON-49881 - Saved Search Sprint 03");
		base.stepInfo(
				" Validating sharing search group to a Security Group from any of the node in the group hierarchy");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Landed on Saved Search
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		// Multiple Node Creation
		newNodeList = saveSearch.createSGAndReturn("PA", "No", noOfNodesToCreate);
		System.out.println("Node creation is done followed by adding searches to the created nodes");
		base.stepInfo("Node creation is done followed by adding searches to the created nodes");

		// Adding searches to the created nodes
		driver.getWebDriver().get(Input.url + "Search/Searches");
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);
		saveSearch.sortedMapList(nodeSearchpair);

		base.stepInfo("-------Pre-requesties completed--------");

		// Search ID collection
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		base.waitForElement(saveSearch.getSavedSearchNewGroupExpand());
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);

		searchGroupSearchpIDpair = saveSearch.collectionOfSearchIdsFromNodeCollections(newNodeList, nodeSearchpair,
				searchGroupSearchpIDpair);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		node = saveSearch.childNodeSelectionToShare(selectIndex, newNodeList);
		System.out.println("Final : " + node);
		saveSearch.shareSavedNodePA(SGtoShare, node, false, true, nodeSearchpair.get(node));

		login.logout();
		// Login as PA
		login.loginToSightLine(Input.pa2userName, Input.pa2password);
		base.stepInfo("Loggedin As : " + Input.pa2FullName);
		// Landed on Saved Search
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		saveSearch.verifyImpactinSharedchildNodes(SGtoShare, newNodeList, selectIndex, nodeSearchpair,
				searchGroupSearchpIDpair);
		base.passedStep(
				"Only the selected search group and their child groups are with Searches, none of the searches in parent hierarchical group is shared");

		login.logout();

		// Login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);
		// Landed on Saved Search
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		saveSearch.verifyImpactinSharedchildNodes(SGtoShare, newNodeList, selectIndex, nodeSearchpair,
				searchGroupSearchpIDpair);
		base.passedStep(
				"Only the selected search group and their child groups are with Searches, none of the searches in parent hierarchical group is shared");

		login.logout();

	}

	/**
	 * @author Raghuram A Date: 10/5/21 Modified date:N/A Modified by: Description :
	 *         Validate sharing search group to Project Administrators from root
	 *         group : RPMXCON-49847 - Sprint 03
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-49847", enabled = true, groups = { "regression" })
	public void sharingRootNode() throws InterruptedException, ParseException {
		int noOfNodesToCreate = 6;
		int selectIndex = 0;
		String SGtoShare = Input.shareSearchPA;
		String node;
		Boolean inputValue = true;
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		HashMap<String, String> searchGroupSearchpIDpair = new HashMap<>();

		base.stepInfo("Test case Id: RPMXCON-49847 - Saved Search Sprint 03");
		base.stepInfo("Validating sharing search group to Project Administrators from root group");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Landed on Saved Search
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		// Multiple Node Creation
		newNodeList = saveSearch.createSGAndReturn("PA", "No", noOfNodesToCreate);
		System.out.println("Node creation is done followed by adding searches to the created nodes");
		base.stepInfo("Node creation is done followed by adding searches to the created nodes");

		// Adding searches to the created nodes
		driver.getWebDriver().get(Input.url + "Search/Searches");
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);
		saveSearch.sortedMapList(nodeSearchpair);

		base.stepInfo("-------Pre-requesties completed--------");

		// Search ID collection
		saveSearch.navigateToSavedSearchPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(2);
		saveSearch.rootGroupExpansion();

		searchGroupSearchpIDpair = saveSearch.collectionOfSearchIdsFromNodeCollections(newNodeList, nodeSearchpair,
				searchGroupSearchpIDpair);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		node = saveSearch.childNodeSelectionToShare(selectIndex, newNodeList);
		System.out.println("Final : " + node);
		saveSearch.shareSavedNodePA(SGtoShare, node, false, true, nodeSearchpair.get(node));

		login.logout();
		// Login as PA
		login.loginToSightLine(Input.pa2userName, Input.pa2password);
		base.stepInfo("Loggedin As : " + Input.pa2FullName);

		// Landed on Saved Search
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		saveSearch.verifyImpactinSharedchildNodes(SGtoShare, newNodeList, selectIndex, nodeSearchpair,
				searchGroupSearchpIDpair);
		base.passedStep(
				"All groups and Searches within them are shared with the group hierarchy across all PAU users associated to the project ");

		login.logout();

	}

	/**
	 * @author Raghuram A Date: 10/5/21 Modified date:N/A Modified by: Description :
	 *         Validate sharing search to Project Administrators from the leaf node
	 *         of 5 group levels : RPMXCON-49846 - Sprint 03
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-49846", enabled = true, groups = { "regression" })
	public void sharingLastLeafNode() throws InterruptedException, ParseException {
		int noOfNodesToCreate = 6;
		int selectIndex = 4;
		String SGtoShare = Input.shareSearchPA;
		String node;
		Boolean inputValue = true;
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		HashMap<String, String> searchGroupSearchpIDpair = new HashMap<>();

		base.stepInfo("Test case Id: RPMXCON-49846 - Saved Search Sprint 03");
		base.stepInfo("Validating sharing search to Project Administrators from the leaf node of 5 group levels");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Landed on Saved Search
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();

		// Multiple Node Creation
		newNodeList = saveSearch.createSGAndReturn("PA", "No", noOfNodesToCreate);
		System.out.println("Node creation is done followed by adding searches to the created nodes");
		base.stepInfo("Node creation is done followed by adding searches to the created nodes");

		driver.getWebDriver().get(Input.url + "Search/Searches");

		// Adding searches to the created nodes
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);
		saveSearch.sortedMapList(nodeSearchpair);

		base.stepInfo("-------Pre-requesties completed--------");

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();

		// Search ID collection
		base.waitForElement(saveSearch.getSavedSearchNewGroupExpand());
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);

		searchGroupSearchpIDpair = saveSearch.collectionOfSearchIdsFromNodeCollections(newNodeList, nodeSearchpair,
				searchGroupSearchpIDpair);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();

		node = saveSearch.childNodeSelectionToShare(selectIndex, newNodeList);
		System.out.println("Final : " + node);

		saveSearch.shareSavedNodePA(SGtoShare, node, false, true, nodeSearchpair.get(node));

		login.logout();
		// Login as PA
		login.loginToSightLine(Input.pa2userName, Input.pa2password);
		base.stepInfo("Loggedin As : " + Input.pa2FullName);

		// Landed on Saved Search
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();

		saveSearch.verifyImpactinSharedchildNodes(SGtoShare, newNodeList, selectIndex, nodeSearchpair,
				searchGroupSearchpIDpair);
		base.passedStep(
				"Saved Search shared with the group hierarchy till 5th level across all PAU users associated to the project ,Except the selected search, none of the searches in any hierarchical group should be shared");

		login.logout();

	}

	/**
	 * @author Raghuram A Date: 10/1/21 Modified date:N/A Modified by: Description :
	 *         Verify the document count for RMU user when PA user save the searches
	 *         under 'Share with Security Group', and execute those searches from
	 *         saved search : RPMXCON-57391 - Sprint 03
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-57391", enabled = true, groups = { "regression" })
	public void VerifyTheDocumentCountforRMU() throws InterruptedException {
		base.stepInfo("Test case Id: RPMXCON-57391");
		String tagName = "TAG" + Utility.dynamicNameAppender();
		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		tagsAndFolderPage.CreateTag(tagName, Input.securityGroup);

		Map<String, Integer> purehit = session.advancedSearchWithCombinations(Input.searchString1, Input.searchString1,
				"morning", Input.language, tagName);
		// System.out.println(purehit);
		login.logout();

		// Login as a Rmu
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		saveSearch.getDefaultSecurityGroupExpand().waitAndClick(10);
		saveSearch.verifyDocCountWithResult(purehit);

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 10/5/21 Modified date:N/A Modified by: Description :
	 *         Validate SA user is allowed to move SearchGroups/Searches only within
	 *         MySearches : RPMXCON-49949 - Sprint 03
	 * @throws InterruptedException
	 * @throws ParseException
	 * @Stabilization - done - new imp
	 */
	@Test(description = "RPMXCON-49949", enabled = true, groups = { "regression" })
	public void validateSAuserisAllowedtoMoveSearchGroupsSearches() throws InterruptedException {
		base.stepInfo("Test case Id: RPMXCON-49949");
		// Login as SA
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Loggedin As : " + Input.sa1userName);
		base.impersonateSAtoPA();
		saveSearch.validateSAuserisAllowedtoMove();

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 10/5/21 Modified date:N/A Modified by: Description :
	 *         Validate PAU is able to move shared SearchGroups/Searches within
	 *         MySearches : RPMXCON-49950 - Sprint 03
	 * @throws InterruptedException
	 * @throws ParseException
	 * @Stabilization - done - new imp
	 */
	@Test(description = "RPMXCON-49950", enabled = true, groups = { "regression" })
	public void validatePAUisabletoMoveSharedSearchGroupsSearches() throws InterruptedException {
		base.stepInfo("Test case Id: RPMXCON-49950");
		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);
		saveSearch.validatePAUisabletoMoveSharedSGS();

		login.logout();
	}

	/**
	 * @author Jeevitha Description: Verify that Saved Search >> Uploaded Batch
	 *         Search query works properly(RPMXCON-49614)
	 * @param username
	 * @param password
	 * @param fullName
	 * @throws InterruptedException
	 * @Stabilization - done - new imp
	 */
	@Test(description = "RPMXCON-49614", enabled = true, dataProvider = "SavedSearchwithUsers", groups = {
			"regression" })
	public void saveSearchBatchUpload1(String username, String password, String fullName) throws InterruptedException {
		String search = "Search" + Utility.dynamicNameAppender();
		base.stepInfo("Test case Id: RPMXCON-49614 - Saved Search Sprint 03");

		// Login as PA
		login.loginToSightLine(username, password);

		// upload batch file
		saveSearch.navigateToSavedSearchPage();
		saveSearch.uploadBatchFile_New(saveSearch.renameFile(Input.batchFileNewLocation));

		login.logout();
	}

	/**
	 * @author Jeevitha Description:Verify the count for RMU/Reviewer when SA after
	 *         impersonating as PA save searches, share with SG and executes same
	 *         searches
	 * @throws InterruptedException
	 * @Stabilization - done
	 */
	@Test(description = "RPMXCON-57392", enabled = true, groups = { "regression" })
	public void shareSGAndVerifyCount() throws InterruptedException {
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();
		String tagName = "TAG" + Utility.dynamicNameAppender();
		String securityGroup = "Security" + Utility.dynamicNameAppender();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);

		// Login as SA
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Test case Id: RPMXCON-57392 - Saved Search Sprint 03");
		base.stepInfo("Loggedin As : " + Input.pa1FullName + "for Pre-requesties");

		// Impersonate TO PA
		base.impersonateSAtoPA();

		// Search Combination Of Audio And Work Product
		tagsAndFolderPage.CreateTag(tagName, Input.securityGroup);
		driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		sg.AddSecurityGroup(securityGroup);
		driver.getWebDriver().get(Input.url + "Search/Searches");
		int pureHit = session.combinationOfAudioAndWP(tagName, "tag", "OR", Input.audioSearchString1);
		session.bulkRelease(securityGroup);
		session.saveSearchAtAnyRootGroup1(searchName1, "Shared with " + securityGroup);

		// Impersonate To RMU
		base.impersonatePAtoRMU();
		base.selectsecuritygroup(securityGroup);
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		base.waitForElement(saveSearch.getSavedSearchGroupName(securityGroup));
		saveSearch.getSavedSearchGroupName(securityGroup).waitAndClick(10);
		saveSearch.savedSearch_SearchandSelect(searchName1, "Yes");

		// Verify Doc Count AS RMU
		String actualDocCount = saveSearch.getCountofDocs().getText();
		softAssertion.assertEquals(pureHit, Integer.parseInt(actualDocCount));

		// Impersonate As PA and Execute Search
		base.impersonateSAtoPA();
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		base.waitForElement(saveSearch.getSavedSearchGroupName(securityGroup));
		saveSearch.getSavedSearchGroupName(securityGroup).waitAndClick(10);
		saveSearch.savedSearch_SearchandSelect(searchName1, "Yes");
		saveSearch.savedSearchExecute2(searchName1, pureHit);

		// Impersonate RMU And Verify Doc Count
		base.impersonatePAtoRMU();
		base.selectsecuritygroup(securityGroup);
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		base.waitForElement(saveSearch.getSavedSearchGroupName(securityGroup));
		saveSearch.getSavedSearchGroupName(securityGroup).waitAndClick(10);
		saveSearch.savedSearch_SearchandSelect(searchName1, "Yes");
		driver.waitForPageToBeReady();

		String actualDocCount2 = saveSearch.getCountofDocs().getText();
		base.stepInfo("Document count : " + actualDocCount2);
		softAssertion.assertEquals(pureHit, Integer.parseInt(actualDocCount2));

		base.impersonateSAtoPA();
		sg.deleteSecurityGroups(securityGroup);

		softAssertion.assertAll();
		login.logout();
	}

	/**
	 * @author Jeevitha Description: Verify that relevant message appears when user
	 *         Navigate - Basic Search - DRAFT Query >> Edit from saved search page
	 *         on Search Screen(RPMXCON-48634)
	 * @Stabilization - done
	 */
	@Test(description = "RPMXCON-48634", enabled = true, groups = { "regression" })
	public void verifyRelevantMessageInBS() {
		String searchName1 = "Search" + Utility.dynamicNameAppender();

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case Id: RPMXCON-48634 - Saved Search Sprint 04");

		// Save basic search Draft Query
		driver.getWebDriver().get(Input.url + "Search/Searches");
		session.basicContentSearchWithSaveChanges(Input.searchString2, "No", "First");
		session.saveSearch(searchName1);

		// Click Edit in Saved Search
		saveSearch.savedSearchEdit(searchName1);

		// verify navigate to session search page
		driver.waitForPageToBeReady();
		String currentUrl = driver.getWebDriver().getCurrentUrl();
		System.out.println(currentUrl);
		base.stepInfo(currentUrl);
		softAssertion.assertEquals(Input.url + "Search/Searches", currentUrl);

		// verify Drafted Query text
		String actualMsg = session.draftQueryTextBS().getText();
		System.out.println(actualMsg);
		base.stepInfo(actualMsg);
		String expectedMsg = "Since this search was never run - it's in Draft State - there are no results to be displayed. Please execute the search to see results.";
		softAssertion.assertEquals(actualMsg, expectedMsg);

		softAssertion.assertAll();
		login.logout();
	}

	/**
	 * @author Jeevitha Description: Verify that relevant message appears when user
	 *         Navigate - Advanced Search(Content&Metadata) - DRAFT Query >> Edit
	 *         from saved search page on Search Screen(RPMXCON-48635)
	 * @throws InterruptedException
	 * @Stabilization - done
	 */
	@Test(description = "RPMXCON-48635", enabled = true, groups = { "regression" })
	public void verifyRelevantMessageAS() throws InterruptedException {
		String searchName2 = "Search" + Utility.dynamicNameAppender();

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case Id: RPMXCON-48635 - Saved Search Sprint 04");

		// Save basic search Draft Query
		driver.getWebDriver().get(Input.url + "Search/Searches");
		session.advancedContentSearchWithSearchChanges(Input.searchString2, "No");
		session.saveSearchInNewNode(searchName2, null);

		// Click Edit in Saved Search
		saveSearch.savedSearchEdit(searchName2);

		// verify navigate to session search page
		driver.waitForPageToBeReady();
		String currentUrl = driver.getWebDriver().getCurrentUrl();
		System.out.println(currentUrl);
		base.stepInfo(currentUrl);
		softAssertion.assertEquals(Input.url + "Search/Searches", currentUrl);

		// verify Drafted Query text
		String actualMsg = session.draftQueryTextAS().getText();
		System.out.println(actualMsg);
		base.stepInfo(actualMsg);
		String expectedMsg = "Since this search was never run - it's in Draft State - there are no results to be displayed. Please execute the search to see results.";
		softAssertion.assertEquals(actualMsg, expectedMsg);
		softAssertion.assertAll();
		login.logout();
	}

	/**
	 * @author Jeevitha Description: Validate deleting searches/groups from the
	 *         shared with PAU by any other PAU user(RPMXCON-49853)
	 * @throws InterruptedException
	 * @Stabilization - done
	 */
	@Test(description = "RPMXCON-49853", enabled = true, groups = { "regression" })
	public void validateDeletingSearches() throws InterruptedException {
		String search = "String" + Utility.dynamicNameAppender();
		String search2 = "String" + Utility.dynamicNameAppender();

		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case Id: RPMXCON-49853 - Saved Search Sprint 04");

		// create new searchgroup
		String newNode1 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "PA", "No");
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(5);
		String subNode = saveSearch.createNewSearchGrp(newNode1, 2);

		// create new searchgroup
		String newNode2 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "PA", "No");

		// Search and save in First Node
		session.basicContentSearch(Input.searchString1);
		session.saveSearchInNewNode(search, newNode1);

		// Search And save in Second Node
		session.getNewSearchButton().waitAndClick(20);
		session.basicContentSearchWithSaveChanges(Input.searchString2, "Yes", "Third");
		session.saveSearchInNewNode(search2, newNode2);

		// verify SHared node AN delete Node
		saveSearch.navigateToSavedSearchPage();
		saveSearch.rootGroupExpansion();
		String searchID1 = saveSearch.shareSavedNodePA(Input.shareSearchPA, newNode1, true, true, search);

		saveSearch.deleteFunctionality();
		System.out.println("Successfully Deleted Shared Node and all Sub Nodes");
		base.stepInfo("Successfully Deleted Shared Node and All Sub Nodes");

		// verify Shared node and Delete Search IN node
		driver.Navigate().refresh();
		saveSearch.rootGroupExpansion();
		String searchID2 = saveSearch.shareSavedNodePA(Input.shareSearchPA, newNode2, true, true, search2);

		saveSearch.savedSearch_SearchandSelect(search2, "Yes");
		saveSearch.deleteFunctionality();
		System.out.println("Successfully Deleted Shared search");
		base.stepInfo("Successfully Deleted Shared Search");

		login.logout();

		// login as Another PA
		login.loginToSightLine(Input.pa2userName, Input.pa2password);

		// verify Search Group after deleting in Other PA
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.verifySharedNode(Input.shareSearchPA, newNode1, searchID1, false, search, false);

		// verify Search after Deleting IN other PA
		driver.Navigate().refresh();
		saveSearch.verifySharedNode(Input.shareSearchPA, newNode2, searchID2, true, search, false);

		login.logout();

	}

	// ---Added on 10/11/21
	/**
	 * @author Raghuram A Date: 10/6/21 Modified date:N/A Modified by: Description :
	 *         Verify that After adding Saved Query - application displays all
	 *         documents that are in the aggregate results when User Navigate Child
	 *         Search groups to DocView. -RPMXCON-49063 Sprint 04
	 * @throws InterruptedException
	 * @throws ParseException
	 * @Stabilization - changes done
	 */

	@Test(description = "RPMXCON-49063", enabled = true, groups = { "regression" })
	public void aggregateResultDocsINDocView() throws InterruptedException, ParseException {
		miniDocListpage = new MiniDocListPage(driver);
		int latencyCheckTime = 5;
		String passMessage = "Application not hang or shows latency more than " + latencyCheckTime + " seconds.";
		String failureMsg = "Continues Loading more than " + latencyCheckTime + " seconds.";
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		Boolean inputValue = true;
		int finalCountresult, hitCount;
		base.stepInfo("Test case Id: RPMXCON-49063 - Saved Search Sprint 04");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Navigate on Saved Search & Multiple Node Creation & save search in node
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		newNodeList = saveSearch.createSGAndReturn("PA", "No", 3);
		System.out.println("Next adding searches to the created nodes");
		base.stepInfo("Next adding searches to the created nodes");
		driver.getWebDriver().get(Input.url + "Search/Searches");
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);

		base.stepInfo("-------Pre-requesties completed--------");
		login.logout();

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Calculate the unique doc count for the respective searches
		hitCount = session.getDocCountBtwnTwoSearches(true, Input.searchString5, Input.searchString6);

		// Launch DocVia via Saved Search
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		base.stepInfo("Root node : " + newNodeList.get(0));
		saveSearch.selectNode1(newNodeList.get(0));
		saveSearch.docViewFromSS("View in DocView");

		// Load latency Verification
		Element loadingElement = session.getspinningWheel();
		saveSearch.loadingCountVerify(loadingElement, latencyCheckTime, passMessage, failureMsg);
		driver.waitForPageToBeReady();
		String currentUrl = driver.getWebDriver().getCurrentUrl();
		softAssertion.assertEquals(Input.url + "DocumentViewer/DocView", currentUrl);
		base.stepInfo("Navigated to DocView Page : " + currentUrl);

		// Main method
		base.waitForElementCollection(miniDocListpage.getListofDocIDinCW());
		int sizeofList = miniDocListpage.getListofDocIDinCW().size();
		System.out.println("Size : " + sizeofList);
		base.stepInfo("Available documents in DocView page : " + sizeofList);

		base.digitCompareEquals(hitCount, sizeofList,
				"Shows all documents that are in the aggregate results set of all child search groups and searches",
				"Count Mismatches with the Documents");

		softAssertion.assertEquals(hitCount, sizeofList);
		softAssertion.assertAll();

		// delete Node
		saveSearch.deleteNode(Input.mySavedSearch, newNodeList.get(0));

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 10/6/21 Modified date:N/A Modified by: Description :
	 *         Verify that After adding Saved Query - application displays all
	 *         documents that are in the aggregate results- when User Performs Bulk
	 *         Folder from Child Search groups. -RPMXCON-49066 Sprint 04
	 * @throws InterruptedException
	 * @throws ParseException
	 * @Stabilization - done
	 */

	@Test(description = "RPMXCON-49066", enabled = true, groups = { "regression" })
	public void aggregateResultWhileBulkFolder() throws InterruptedException, ParseException {
		int latencyCheckTime = 5;
		String passMessage = "Application not hang or shows latency more than " + latencyCheckTime + " seconds.";
		String failureMsg = "Continues Loading more than " + latencyCheckTime + " seconds.";
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		Boolean inputValue = true;
		int finalCountresult, hitCount, noOfNode = 3;
		String purehitCount;
		String folderName = "Folder" + Utility.dynamicNameAppender();
		base.stepInfo("Test case Id: RPMXCON-49066 - Saved Search Sprint 04");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Navigate on Saved Search & Multiple Node Creation & save search in node
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		newNodeList = saveSearch.createSGAndReturn("PA", "No", noOfNode);
		System.out.println("Next adding searches to the created nodes");
		base.stepInfo("Next adding searches to the created nodes");
		driver.getWebDriver().get(Input.url + "Search/Searches");
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);

		base.stepInfo("-------Pre-requesties completed--------");
		login.logout();

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Launch DocVia via Saved Search
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		base.stepInfo("Root node selected : " + newNodeList.get(0));
		saveSearch.selectNode1(newNodeList.get(0));
		saveSearch.getSavedSearchToBulkFolder().waitAndClick(5);
		base.stepInfo("Clicked Folder Icon");

		// Load latency Verification SaveSearchToBulkFolder
		Element loadingElement = saveSearch.getTotalCountLoad();
		saveSearch.loadingCountVerify(loadingElement, latencyCheckTime, passMessage, failureMsg);
		purehitCount = session.BulkActions_Folder_returnCount(folderName);
		base.stepInfo("Completed Bulk Folder mapping");
		base.stepInfo(
				"Navigating to Search >> Basic Search >> Advanced Search >> WorkProduct >> Folder (Selected Same Folder which we have created in prerequesties.");
		session.switchToWorkproduct();
		session.selectFolderInASwp(folderName);
		hitCount = session.serarchWP();
		finalCountresult = Integer.parseInt(purehitCount);

		base.digitCompareEquals(hitCount, finalCountresult,
				"After the Bulk Folder - Pure hit shows the aggregate results set of all child search groups and searches  ",
				"Count Mismatches");

		softAssertion.assertEquals(hitCount, finalCountresult);
		softAssertion.assertAll();

		// delete Node
		saveSearch.deleteNode(Input.mySavedSearch, newNodeList.get(0));

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 10/6/21 Modified date:N/A Modified by: Description :
	 *         Verify that After adding Saved Query - application displays all
	 *         documents that are in the aggregate results - when User performs
	 *         Refresh with Child Search groups (RPMXCON-49071 Sprint-04)
	 * @throws InterruptedException
	 * @throws ParseException
	 * @Stabilizaation - done
	 */

	@Test(description = "RPMXCON-49071", enabled = true, groups = { "regression" })
	public void showHideFields() throws InterruptedException, ParseException {
		int latencyCheckTime = 5;
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		Boolean inputValue = true;
		int noOfNode = 2;
		String specificHeaderName = "Search Name";
		base.stepInfo("Test case Id: RPMXCON-49071 - Saved Search Sprint 04");

		// Login as PA
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);
		// Navigate on Saved Search & Multiple Node Creation & save search in node
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		newNodeList = saveSearch.createSGAndReturn("PA", "No", noOfNode);
		System.out.println("Next adding searches to the created nodes");
		base.stepInfo("Next adding searches to the created nodes");
		driver.getWebDriver().get(Input.url + "Search/Searches");
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);

		base.stepInfo("-------Pre-requesties completed--------");
		login.logout();

		// Login as PA
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		// Launch DocVia via Saved Search
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		base.stepInfo("Root node selected : " + newNodeList.get(0));
		saveSearch.selectNode1(newNodeList.get(0));

		saveSearch.methodTocheckHideandShowFunction(specificHeaderName);

		// delete Node
		saveSearch.deleteNode(Input.mySavedSearch, newNodeList.get(0));

		login.logout();
	}

	/**
	 * @author Jeevitha Description:Validate saving a new session search (executed
	 *         or draft) under My Searches or Shared Searches
	 * @throws InterruptedException
	 * @Stabilization - done
	 */
	@Test(description = "RPMXCON-49857", enabled = true, groups = { "regression" })
	public void verifySearchs() throws InterruptedException {
		String search1 = "Search4" + Utility.dynamicNameAppender();
		String search2 = "Search4" + Utility.dynamicNameAppender();

		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case Id: RPMXCON-49857 - Saved Search Sprint 04");
		driver.getWebDriver().get(Input.url + "Search/Searches");
		session.basicContentSearchWithSaveChanges(Input.searchString1, "Yes", "First");
		session.saveSearchAtAnyRootGroup(search1, Input.shareSearchPA);

		base.waitForElement(session.getNewSearchButton());
		session.getNewSearchButton().waitAndClick(10);

		session.basicContentSearchWithSaveChanges(Input.searchString2, "Yes", "Third");
		session.saveSearchInNewNode(search2, null);

		login.logout();
		login.loginToSightLine(Input.pa2userName, Input.pa2password);

		// verify
		// Make sure shared Node reflected in the SG
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.getSavedSearchGroupName(Input.shareSearchPA).waitAndClick(10);
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		saveSearch.savedSearch_SearchandSelect(search1, "Yes");

		softAssertion.assertTrue(saveSearch.getSearchName(search1).Displayed());
		System.out.println(search1 + " : is Present");
		base.stepInfo(search1 + " : is Present");
		softAssertion.assertAll();
		login.logout();

	}

	/**
	 * @author Jeevitha Description: For RMU - Validate performing any action on
	 *         searches/groups from the shared with <Security Group Name> by any
	 *         other PAU user
	 * @throws InterruptedException
	 * @Stabilization - done
	 */
	@Test(description = "RPMXCON-49888", enabled = true, groups = { "regression" })
	public void validatePerformingAnyAction() throws InterruptedException {
		String search = "String" + Utility.dynamicNameAppender();
		String search2 = "String" + Utility.dynamicNameAppender();

		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case Id: RPMXCON-49888 - Saved Search Sprint 04");

		// create new searchgroup
		String newNode1 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "PA", "No");

		// create new searchgroup
		String newNode2 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "PA", "No");

		// create new searchgroup
		String newNode3 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "PA", "No");

		// Search and save in First Node
		int purehit1 = session.basicContentSearch(Input.searchString1);
		session.saveSearchInNewNode(search, newNode1);

		// Search And save in Second Node
		session.getNewSearchButton().waitAndClick(20);
		int purehit2 = session.basicContentSearchWithSaveChanges(Input.searchString2, "Yes", "Third");
		session.saveSearchInNewNode(search2, newNode2);

		// perform action on Node
		saveSearch.navigateToSavedSearchPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(3);
		saveSearch.selectNode1(newNode1);
		saveSearch.moveSearchToAnotherFolder(newNode3);
		saveSearch.moveActionButton("Save");
		driver.waitForPageToBeReady();
		String ExpectedMSg = "Save search tree node successfully moved.";
		base.VerifySuccessMessage(ExpectedMSg);
		base.stepInfo("Moved : " + newNode1 + " to " + newNode3);

		// perform action on search in node
		driver.Navigate().refresh();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(3);
		saveSearch.selectNode1(newNode2);
		saveSearch.savedSearch_SearchandSelect(search2, "Yes");
		saveSearch.savedSearchExecute2(search2, purehit2);

		login.logout();

	}

	/**
	 * @author Jeevitha Description: For RMU - Validate deleting searches/groups
	 *         from the shared with <Security Group Name> by any other PAU user
	 * @throws InterruptedException
	 * @Stabilization - done
	 */
	@Test(description = "RPMXCON-49886", enabled = true, groups = { "regression" })
	public void validateDeletingSearchesASRMU() throws InterruptedException {
		String search = "String" + Utility.dynamicNameAppender();
		String search2 = "String" + Utility.dynamicNameAppender();

		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Test case Id: RPMXCON-49886 - Saved Search Sprint 04");

//		 create new searchgroup 
		String newNode1 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", "No");
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(5);
		String subNode = saveSearch.createNewSearchGrp(newNode1, 2);

		// create new searchgroup
		String newNode2 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", "No");

		// Search and save in First Node
		session.basicContentSearch(Input.searchString1);
		session.saveSearchInNewNode(search, newNode1);

		// Search And save in Second Node
		session.getNewSearchButton().waitAndClick(20);
		session.basicContentSearchWithSaveChanges(Input.searchString2, "Yes", "Third");
		session.saveSearchInNewNode(search2, newNode2);

		// verify SHared node AN delete Node
		saveSearch.navigateToSavedSearchPage();
		saveSearch.rootGroupExpansion();
		String searchID1 = saveSearch.shareSavedNodePA(Input.shareSearchDefaultSG, newNode1, true, true, search);

		saveSearch.deleteFunctionality();
		System.out.println("Successfully Deleted Shared Node and all Sub Nodes");
		base.stepInfo("Successfully Deleted Shared Node and All Sub Nodes");

		// verify Shared node and Delete Search IN node
		driver.Navigate().refresh();
		saveSearch.rootGroupExpansion();
		String searchID2 = saveSearch.shareSavedNodePA(Input.shareSearchDefaultSG, newNode2, true, true, search2);

		saveSearch.savedSearch_SearchandSelect(search2, "Yes");
		saveSearch.deleteFunctionality();
		System.out.println("Successfully Deleted Shared search");
		base.stepInfo("Successfully Deleted Shared Search");

		login.logout();

		// login as Another PA
		login.loginToSightLine(Input.pa2userName, Input.pa2password);

		// verify Search Group after deleting in Other PA
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.verifySharedNode(Input.shareSearchDefaultSG, newNode1, searchID1, false, search, false);

		// verify Search after Deleting IN other PA
		driver.Navigate().refresh();
		saveSearch.verifySharedNode(Input.shareSearchDefaultSG, newNode2, searchID2, true, search, false);

		login.logout();

		// login as Another RMU
		login.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		// verify Search Group after deleting in Other PA
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches"); //
		saveSearch.verifySharedNode(Input.shareSearchDefaultSG, newNode1, searchID1, false, search, false);

		// verify Search after Deleting IN other PA
		driver.Navigate().refresh();
		saveSearch.verifySharedNode(Input.shareSearchDefaultSG, newNode2, searchID2, true, search, false);

		login.logout();

	}

	/**
	 * @author jeevitha Description: PAU saving search under <Shared with
	 *         SecurityGroupName>, schedule search and verify documents
	 * @throws Exception
	 * @Stabilization - ToDO -advancedContentSearchWithSearchChanges()
	 */
	@Test(description = "RPMXCON-48813", enabled = true, groups = { "regression" })
	public void verifyDocument() throws Exception {
		String savedSearch = "Search1" + Utility.dynamicNameAppender();
		String savedSearch2 = "Search2" + Utility.dynamicNameAppender();
		String savedSearch3 = "Search3" + Utility.dynamicNameAppender();
		String savedSearch4 = "Search4" + Utility.dynamicNameAppender();
		String tagName = "TAG" + Utility.dynamicNameAppender();
		String folder = "FOLDERX02" + Utility.dynamicNameAppender();
		SchedulesPage schedule = new SchedulesPage(driver);
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		String pureHit0;

		// Login as PA
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Test case Id: RPMXCON-48813 - Saved Search Sprint 03");

		tagsAndFolderPage.CreateTag(tagName, Input.securityGroup);
		int pureHit1 = session.advancedMetaDataSearch("CustodianName", null, Input.metaDataCN, null);
		session.saveSearchInNewNode(savedSearch, null);

		session.getNewSearchButton().waitAndClick(20);
		int pureHit2 = session.advancedContentSearchWithSearchChanges(Input.searchString1, "Yes");
		session.saveSearchInNewNode(savedSearch2, null);

		base.selectproject();
		int pureHit3 = session.conceptualSearch_new("right form", "mid");
		session.saveSearchInNewNode(savedSearch3, null);
		session.bulkTagExisting(tagName);
		System.out.println(pureHit3);

		session.getNewSearchButton().waitAndClick(20);
		session.advancedSearchWorkProduct(tagName);
		int pureHit4 = session.saveAndReturnPureHitCount();
		driver.scrollPageToTop();
		session.saveSearchInNewNode(savedSearch4, null);
		System.out.println(pureHit4);

		String dataSet[][] = { { savedSearch, String.valueOf(pureHit1) }, { savedSearch2, String.valueOf(pureHit2) },
				{ savedSearch3, String.valueOf(pureHit3) }, { savedSearch4, String.valueOf(pureHit4) } };
		for (int i = 0; i < 4; i++) {

			int j = 0;
			String searches = dataSet[i][j];
			j++;
			pureHit0 = dataSet[i][j];
			System.out.println(pureHit0);
			String tagName2 = "TAG" + Utility.dynamicNameAppender();
			// Schedule save search
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.scheduleSavedSearchInMinute(searches, 2);

			// verify Document count After Execution
			saveSearch.savedSearch_Searchandclick(searches);
			String actualDocCount = saveSearch.getCountofDocs().getText();
			System.out.println(actualDocCount);
			softAssertion.assertEquals(pureHit0, actualDocCount);

			// Perform Bulk action
			driver.Navigate().refresh();
			saveSearch.SaveSearchToBulkTag(searches, tagName2);
		}
		softAssertion.assertAll();
		login.logout();
	}

	/**
	 * @author Raghuram A Date: 9/14/21 Modified date:N/A Modified by: Description :
	 *         Verify move action by moving Search/SearchGroup to another search
	 *         group within "My Saved Search" -RPMXCON-47393 Sprint 03
	 * @Stabilization : Completed
	 */
	@Test(description = "RPMXCON-47393", enabled = true, dataProvider = "TestUser", groups = { "regression" })
	public void verifyMoveActionSS(String UserName, String PassWord, String fullName) throws InterruptedException {

		base.stepInfo("Test case Id: RPMXCON-47393 - Saved Search Sprint 03");
		base.stepInfo("Node (or) New Search Group creation");

		// Login via PA
		login.loginToSightLine(UserName, PassWord);

		String searchName = "Search " + Utility.dynamicNameAppender();
		String newNode = saveSearch.createSearchGroupAndReturn(searchName, "PA", "Yes");
		String newNode2 = saveSearch.createSearchGroupAndReturn(searchName, "PA", "Yes");

		base.stepInfo("Search and saveSearch in the created node");
		String savedSearchName1 = "Search Name" + UtilityLog.dynamicNameAppender();
		session.basicContentSearch(Input.searchString2);

//		session.saveSearchInNode(savedSearchName1);
		session.saveSearchInNewNode(savedSearchName1, newNode2);
		driver.waitForPageToBeReady();

		// verify Move Action via SavedSearch SMethod
		saveSearch.verifyMoveActionSSMethod(savedSearchName1, newNode, newNode2, true, true, true);

		// Delete Node
		saveSearch.deleteNode(Input.mySavedSearch, newNode2);

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 9/21/21 Modified date:N/A Modified by: Description :
	 *         Verify that User can Move a renamed search to any place within My
	 *         Saved Search -RPMXCON-49352 Sprint 03
	 * @Stabilization : Completed
	 */
	@Test(description = "RPMXCON-49352", enabled = true, dataProvider = "SavedSearchwithUsers", groups = {
			"regression" })

	public void verifyMoveRenamedSearch(String UserName, String PassWord, String fullName) throws InterruptedException {

		base.stepInfo("Test case Id: RPMXCON-49352 - Saved Search Sprint 03");

		// Login via PA
		login.loginToSightLine(UserName, PassWord);
		base.stepInfo("Loggedin As : " + fullName);

		String searchName = "SavedSearch" + Utility.dynamicNameAppender();
		String searchNameRenamed = "RenamedSavedSearch" + Utility.dynamicNameAppender();

		base.stepInfo("Node (or) New Search Group creation");
		String newNode = saveSearch.createSearchGroupAndReturn(searchName, "PA", "Yes");
		String newNode2 = saveSearch.createSearchGroupAndReturn(searchName, "PA", "Yes");

		base.stepInfo("Search and saveSearch in the created node");
		session.basicContentSearch(Input.searchString2);

//		session.saveSearchInNode(searchName);
		session.saveSearchInNewNode(searchName, newNode2);

		driver.waitForPageToBeReady();

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		base.stepInfo("Landed on SavedSearchPage");

		saveSearch.selectNode1(newNode2);
		saveSearch.verifyRenamedsavedSearch(searchName, searchNameRenamed);
		base.stepInfo(" Renamed an existing saved search on Saved Search Screen using PA user");

		saveSearch.moveSearchToanotherGroup(newNode, searchNameRenamed);

		// Delete Node
		saveSearch.deleteNode(Input.mySavedSearch, newNode);
		saveSearch.deleteNode(Input.mySavedSearch, newNode2);

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 9/21/21 Modified date:N/A Modified by: Description :
	 *         Verify that User can Edit renamed search on Saved Search Screen.
	 *         -RPMXCON-49354 Sprint 03
	 * @Stabilization : Completed
	 */
	@Test(description = "RPMXCON-49354", enabled = true, dataProvider = "SavedSearchwithUsers", groups = {
			"regression" })
	public void editRenamedSearchSS(String UserName, String PassWord, String fullName) throws InterruptedException {
		base.stepInfo("Test case Id: RPMXCON-49354 - Saved Search Sprint 03");
		// Login as RMU
		login.loginToSightLine(UserName, PassWord);
		base.stepInfo("Loggedin As : " + fullName);

		// Search and SaveSearch
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();
		String searchNameRenamed = "RenamedSavedSearch" + Utility.dynamicNameAppender();
		session.basicContentSearch(Input.searchString2);
		session.saveSearch(searchName1);

		// Rename Search
		saveSearch.navigateToSSPage();
		saveSearch.verifyRenamedsavedSearch(searchName1, searchNameRenamed);
		base.stepInfo(searchNameRenamed + " : Renamed an existing saved search on Saved Search Screen using PA user");

		// Modify Search via Saved Search
		saveSearch.savedSearchEdit(searchNameRenamed);
		session.modifySearchTextArea(1, Input.searchString2, Input.searchString1, "Save");
		base.passedStep("User able to edit an already renamed search");

		// Delete Search
		saveSearch.deleteSearch(searchNameRenamed, Input.mySavedSearch, "Yes");

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 9/22/21 Modified date:N/A Modified by: Description :
	 *         Verify that User can share a renamed search from Saved Search Screen.
	 *         -RPMXCON-49353 Sprint 03
	 * @throws ParseException
	 * @Stabilization : Completed
	 */
	@Test(description = "RPMXCON-49353", enabled = true, groups = { "regression" })
	public void shareaRenamedSearchSS() throws InterruptedException, ParseException {
		base.stepInfo("Test case Id: RPMXCON-49353 - Saved Search Sprint 03");
		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Search
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();
		String searchNameRenamed = "RenamedSavedSearch" + Utility.dynamicNameAppender();
		session.basicContentSearch(Input.searchString2);
		session.saveSearch(searchName1);

		// Rename Search
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		base.stepInfo("Landed on SavedSearchPage");
		saveSearch.verifyRenamedsavedSearch(searchName1, searchNameRenamed);
		base.stepInfo(searchNameRenamed + " : Renamed an existing saved search on Saved Search Screen using PA user");

		// Modify Search via Saved Search
		saveSearch.shareSavedSearchAsPA(searchNameRenamed, Input.securityGroup);
		login.logout();

		// Verify as RMU
		base.stepInfo("Verify as Reviewer Manager");
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);
		saveSearch.navigateToSSPage();
		saveSearch.verifySharedGroupSearch(Input.securityGroup, searchNameRenamed);
		login.logout();

		// Verify as REV
		base.stepInfo("Verify as Reviewer");
		login.loginToSightLine(Input.rev1userName, Input.rev1password);
		base.stepInfo("Loggedin As : " + Input.rev1FullName);
		saveSearch.navigateToSSPage();
		saveSearch.verifySharedGroupSearch(Input.securityGroup, searchNameRenamed);
		login.logout();

		// Delete Search
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		saveSearch.deleteSearch(searchNameRenamed, Input.mySavedSearch, "Yes");
		saveSearch.deleteSearch(searchNameRenamed, Input.securityGroup, "Yes");

		login.logout();

	}

	/**
	 * @author Raghuram A Date: 9/23/21 Modified date:9/29/21 Modified by: Raghuram
	 *         A Description : Validate sharing Search Group with Project
	 *         Administrator Group -RPMXCON-49845 Sprint 03
	 * @throws InterruptedException
	 * @throws ParseException
	 * @Stabilization : Completed
	 */
	@Test(description = "RPMXCON-49845", enabled = true, groups = { "regression" })
	public void sharingSearchGroupToPA() throws InterruptedException, ParseException {

		String SGtoShare = Input.shareSearchPA;
		System.out.println(SGtoShare);

		base.stepInfo("Test case Id: RPMXCON-49845 - Saved Search Sprint 03");
		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		base.stepInfo("Node (or) New Search Group creation");
		String newNode = saveSearch.createSearchGroupAndReturn(searchName, "PA", "Yes");

		// Search and Save in the SG
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();
		session.basicContentSearch(Input.searchString2);

//		session.saveSearchInNode(searchName1);
		session.saveSearchInNewNode(searchName1, newNode);

		// Landed on Saved Search
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);

		// Share to SG - modified 9/29/21
		String searchID = saveSearch.shareSavedNodePA(SGtoShare, newNode, true, true, searchName1);
		login.logout();

		// Login as PA
		login.loginToSightLine(Input.pa2userName, Input.pa2password);
		base.stepInfo("Loggedin As : " + Input.pa2FullName);

		// Verify Search - modified 9/29/21
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.verifySharedNode(SGtoShare, newNode, searchID, true, searchName1, false);

		login.logout();

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Delete Node
		saveSearch.deleteNode(SGtoShare, newNode, true, true);
	}

	/**
	 * @author Raghuram A Date: 9/24/21 Modified date:N/A Modified by: Description :
	 *         Validate sharing search with Project Administrators -RPMXCON-49844
	 *         Sprint 03
	 * @throws InterruptedException
	 * @throws ParseException
	 * @Stabilization : Completed
	 */
	@Test(description = "RPMXCON-49844", enabled = true, groups = { "regression" })
	public void sharingSearchWithPA() throws InterruptedException, ParseException {

		String SGtoShare = Input.shareSearchPA;

		base.stepInfo("Test case Id: RPMXCON-49844 - Saved Search Sprint 03");
		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Search
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();
		session.basicContentSearch(Input.searchString2);
		session.saveSearch(searchName1);

		// Modify Search via Saved Search
//		saveSearch.shareSavedSearchAsPA(searchName1, Input.securityGroup);
		saveSearch.shareSavedSearchAsPA(searchName1, SGtoShare);
		login.logout();

		// Login as PA
		login.loginToSightLine(Input.pa2userName, Input.pa2password);
		base.stepInfo("Loggedin As : " + Input.pa2FullName);

		saveSearch.navigateToSSPage();
		saveSearch.verifySharedGroupSearch(SGtoShare, searchName1);
		login.logout();

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Delete Search
		saveSearch.deleteSearch(searchName1, Input.mySavedSearch, "Yes");
		saveSearch.deleteSearch(searchName1, SGtoShare, "Yes");

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 9/23/21 Modified date:9/29/21 Modified by: Raghuram
	 *         A Description : Validate performing any action on searches/groups
	 *         from the shared with <Security Group Name> by any other PAU user
	 *         -RPMXCON-4949873845 Sprint 03
	 * @throws InterruptedException
	 * @throws ParseException
	 * @Stabilization : Completed-In
	 */
	@Test(description = "RPMXCON-49873", enabled = true, groups = { "regression" })
	public void performActionOnSGviaSG() throws InterruptedException, ParseException {

		String SGtoShare = Input.shareSearchDefaultSG;
		String SavedSearchRenamed = "SavedSearchRenamed" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-49873 - Saved Search Sprint 03");
		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		base.stepInfo("Node (or) New Search Group creation");
		String newNode = saveSearch.createSearchGroupAndReturn(searchName, "PA", "Yes");

		// Search and Save in the SG
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();
		session.basicContentSearch(Input.searchString2);

//		session.saveSearchInNode(searchName1);
		session.saveSearchInNewNode(searchName1, newNode);

		// Landed on Saved Search
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.rootGroupExpansion();
		driver.waitForPageToBeReady();

		// Share to SG
		String searchID = saveSearch.shareSavedNodePA(SGtoShare, newNode, true, true, searchName1);
		login.logout();

		// Login as PA2
		login.loginToSightLine(Input.pa2userName, Input.pa2password);
		base.stepInfo("Loggedin As : " + Input.pa2FullName);

		// Verify Search
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.verifySharedNode(SGtoShare, newNode, searchID, true, searchName1, false);

		// Rename SearchGroup and verify
		base.stepInfo("Verify PAU allowed to perform action (Rename)");
		String SearchGroupRenamed = saveSearch.renameSearchGroup(newNode);
		base.waitForElement(saveSearch.getSavedSearchGroupName(SearchGroupRenamed));
		softAssertion.assertTrue(saveSearch.getSavedSearchGroupName(SearchGroupRenamed).isElementAvailable(2));
		saveSearch.getSavedSearchGroupName(SearchGroupRenamed).waitAndClick(5);

		// Report
		base.stepInfo("Verify PAU allowed to perform action (STR report)");
		saveSearch.SavedSearchToTermReport(SearchGroupRenamed);

		// Saved Search
		base.stepInfo("Verify PAU allowed to perform action Schedule)");
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.verifySharedNode(SGtoShare, SearchGroupRenamed, searchID, true, searchName1, false);
		saveSearch.scheduleSavedSearch(searchName1);
		base.passedStep("Schedule Action is Enabled and has Access");

		softAssertion.assertAll();
		login.logout();

		// Login as PA1
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		saveSearch.deleteNode(Input.mySavedSearch, newNode);

		login.logout();

	}

	/**
	 * @author Raghuram A Date: 9/25/21 Modified date:N/A Modified by: Description :
	 *         Validate saving executed search (executed or draft) under My Searches
	 *         or Shared Searches -RPMXCON-49874 Sprint 03
	 * @throws InterruptedException
	 * @Stabilization : Completed
	 */
	@Test(description = "RPMXCON-49874", enabled = true, groups = { "regression" })
	public void newSearchViaSavedSearch() throws InterruptedException {

		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();
		String searchName2 = "Search name" + UtilityLog.dynamicNameAppender();
		String SGtoShare = Input.shareSearchDefaultSG;

		base.stepInfo("Test case Id: RPMXCON-49874 - Saved Search Sprint 03");
		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Navigate to Saved Search
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		base.stepInfo("Navigated to SavedSearch Page");

		driver.waitForPageToBeReady();
		saveSearch.getbasicSearchIcon().Click();

		// Search and SaveSearch
		session.basicContentSearch(Input.searchString2);
		session.saveSearch(searchName1);
		base.stepInfo("New Session Search saved under My Searches");
		driver.waitForPageToBeReady();
		session.saveSearchAtAnyRootGroup(searchName2, SGtoShare);
		base.stepInfo("New Session Search saved under Shared with '" + SGtoShare
				+ "' and available to all associated PAU and RMU users");

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		base.stepInfo("Under My Saved Search");
		saveSearch.savedSearch_SearchandSelect(searchName1, "No");
		saveSearch.verifySharedGroupSearch(SGtoShare, searchName2);

		login.logout();

		// Login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		base.stepInfo("Under : " + SGtoShare);
		saveSearch.verifySharedGroupSearch(SGtoShare, searchName2);

		login.logout();

		// Login as PAU2
		login.loginToSightLine(Input.pa2userName, Input.pa2password);
		base.stepInfo("Loggedin As : " + Input.pa2FullName);
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		base.stepInfo("Under : " + SGtoShare);
		saveSearch.verifySharedGroupSearch(SGtoShare, searchName2);

		login.logout();

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Delete Search
		saveSearch.deleteSearch(searchName1, Input.mySavedSearch, "Yes");
		saveSearch.deleteSearch(searchName2, SGtoShare, "Yes");

		login.logout();

	}

	/**
	 * @author Jeevitha R Date: 9/25/21 Modified date: N/A Modified by: Description
	 *         : TC02_Verify that application does not display any warnings when a
	 *         user execute search group which contains only Advanced Search(es) in
	 *         Draft state on Saved Search Screen. -RPMXCON-49832 Sprint 03
	 * @throws InterruptedException
	 * @Stabilization : Completed - In
	 */
	@Test(description = "RPMXCON-49832", enabled = true, dataProvider = "SavedSearchwithUsers", groups = {
			"regression" })
	public void saveSearchAdvDraft(String userName, String password, String fullName)
			throws InterruptedException, ParseException {
		String search = "Search6" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-49832 - Saved Search Sprint 03");
		// Login as PA
		login.loginToSightLine(userName, password);
		base.stepInfo("Loggedin As : " + fullName);

		// create new searchgroup
		String newNode = saveSearch.createSearchGroupAndReturn(searchName1, fullName, "Yes");

		// add save search in node -- modified
		driver.getWebDriver().get(Input.url + "Search/Searches");
		int purehit = session.advancedContentSearchWithSearchChanges(Input.searchString1, "No");

//		session.saveSearchInNode(search);
		session.saveSearchInNewNode(search, newNode);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.selectNode1(newNode);

		saveSearch.savedSearch_SearchandSelect(search, "Yes");
		saveSearch.savedSearchExecute_Draft(search, purehit);

		// Delete Node
		saveSearch.deleteNode(Input.mySavedSearch, newNode);

		login.logout();

	}

	/**
	 * @author Raghuram A Date: 9/27/21 Modified date: N/A Modified by: Description
	 *         : TC06_Verify that application does not display any warnings when a
	 *         user execute search group which contains Basic and Advanced
	 *         Search(es) in Draft and Completed state on Saved Search Screen.
	 *         -RPMXCON-49828 Sprint 03
	 * @throws InterruptedException
	 * @Stabilization : Completed
	 */
	@Test(description = "RPMXCON-49828", enabled = true, groups = { "regression" })
	public void verifyBasic_Advancesearch() throws InterruptedException {
		String Search1 = "search" + Utility.dynamicNameAppender();
		String Search2 = "search" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-49828 - Saved Search Sprint 03");
		// Login as PA
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// create new searchgroup
		String newNode = saveSearch.createNewSearchGrp(Input.mySavedSearch);
		System.out.println(newNode);

		String newNode1 = saveSearch.createNewSearchGrp(Input.mySavedSearch);
		System.out.println(newNode1);

		// Draft Query
		driver.getWebDriver().get(Input.url + "Search/Searches");
		int purehit = session.basicContentSearchWithSaveChanges(Input.searchString1, "No", "First");
		System.out.println(purehit);
		session.saveSearchInNewNode(Search1, newNode);

		session.getNewSearchButton().waitAndClick(5);
		int pureHit2 = session.advancedContentSearchWithSearchChanges(Input.searchString1, "Yes");
		System.out.println(pureHit2);
		session.saveSearchInNewNode(Search2, newNode1);

		saveSearch.navigateToSavedSearchPage();
		saveSearch.selectNode1(newNode);

		saveSearch.savedSearch_SearchandSelect(Search1, "Yes");
		saveSearch.savedSearchExecute_Draft(Search1, purehit);
		driver.Navigate().refresh();
		saveSearch.selectNode1(newNode1);

		saveSearch.savedSearch_SearchandSelect(Search2, "Yes");
		saveSearch.savedSearchExecute_Draft(Search2, pureHit2);

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 9/15/21 Modified date:N/A Modified by: A Description
	 *         : To verify as an RM user login, I will be able to search a saved
	 *         query based on search name from My Search folder RPMXCON-47460-
	 *         Sprint 03
	 */
	@Test(description = "RPMXCON-47460", enabled = true, groups = { "regression" })
	public void searchAsavedQueryBasedOnSearchName() throws InterruptedException {

		base.stepInfo("Test case Id: RPMXCON-47460 - Saved Search Sprint 03");
		String search_Name1 = "Search1" + Utility.dynamicNameAppender();
		// Login via Reviewer Manager
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// create new searchgroup
		String newNode = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "REV", "No");

		// add save search in node
		int purehit = session.basicContentSearch(Input.searchString1);
		session.saveSearchInNewNode(search_Name1, newNode);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.selectNode1(newNode);

		saveSearch.savedSearch_SearchandSelect(search_Name1, "yes");

		login.logout();
	}

	/**
	 * @author Jeevitha Date: 9/16/21 Modified date:N/A Modified by: A Description :
	 *         To verify as a Reviewer user login, I will be able to search a saved
	 *         query based on search name from My Search folder RPMXCON-47561-
	 *         Sprint 03
	 */
	@Test(description = "RPMXCON-47460", enabled = true, groups = { "regression" })
	public void verifySavedSearchAsRev() throws InterruptedException {
		base.stepInfo("Test case Id: RPMXCON-47460 - Saved Search Sprint 03");
		String search_Name1 = "Search1" + Utility.dynamicNameAppender();

		login.loginToSightLine(Input.rev1userName, Input.rev1password);
		base.stepInfo("Loggedin As : " + Input.rev1FullName);

		// create new searchgroup
		String newNode = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "REV", "No");

		// add save search in node
		int purehit = session.basicContentSearch(Input.searchString1);
		session.saveSearchInNewNode(search_Name1, newNode);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.selectNode1(newNode);

		saveSearch.savedSearch_SearchandSelect(search_Name1, "yes");

		login.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description Renames an existing Advanced saved search on Saved Search
	 *              Screen. RPMXCON-49351
	 */
	@Test(description = "RPMXCON-49351", enabled = true, dataProvider = "SavedSearchwithUsers", groups = {
			"regression" })
	public void ValidateRenamedAdvancedSavedSearch(String UserName, String PassWord, String fullName)
			throws InterruptedException {
		base.stepInfo("TEST CASE ID- 49351 --- Saved search");
		base.stepInfo("Renames an existing Advanced saved search on Saved Search Screen.");
		String searchName = "SavedSearch" + Utility.dynamicNameAppender();
		String searchName2 = "RenamedSavedSearch" + Utility.dynamicNameAppender();

		login.loginToSightLine(UserName, PassWord);
		session.MetaDataSearchInAdvancedSearch(Input.metaDataName, Input.custodianName_allen);
		session.saveSearchAdvanced(searchName);
		saveSearch.verifyRenamedsavsearch(searchName, searchName2);
		if (UserName == Input.pa1userName) {
			base.stepInfo(" Renamed an existing Advanced saved search on Saved Search Screen using PA user");
		}
		if (UserName == Input.rmu1userName) {
			base.stepInfo("Renamed an existing Advanced saved search on Saved Search Screen using RMU user");
		}
		if (UserName == Input.rev1userName) {
			base.stepInfo("Renamed an existing Advanced saved search on Saved Search Screen using Reviewer");
		}
		login.logout();
	}

	/**
	 * @author Jeevitha Search Audio File And Shares With Project
	 *         Administrator.(RPMXCON-57419) Search Audio File And Share with
	 *         Default Security Group.(RPMXCON-57420) search audio file and Share
	 *         with security group.(RPMXCON-57421) schedule Save search
	 * @throws InterruptedException
	 * @throws ParseException
	 * @Stabilization - done
	 */
	@Test(description = "RPMXCON-57419,RPMXCON-57420,RPMXCON-57421", enabled = true, groups = { "regression" })
	public void searchAudioAndSharePA() throws InterruptedException, ParseException {
		// Login as a PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-57419,RPMXCON-57420,RPMXCON-57421  SavedSearch ");

		session.AudioAndNonAudioSearch(Input.audioSearchString1, "North American English");
		session.saveSearchadvanced(searchName);

//		Create security group
		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		sg.AddSecurityGroup(securitygroupname);

		// Shared with project Administrator and Default Security group
		saveSearch.shareSavedSearchAsPA(searchName, "Default");
		base.stepInfo("Shared Successfuly with  Project Administartor");
		base.stepInfo("Shared Successfuly with  Default Security Group");

		// Shared with Security group
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(5);
		saveSearch.shareSavedSearchAsPA(searchName, securitygroupname);
		base.stepInfo("Shared Successfuly with  Security group");

		// Schedule save search
		saveSearch.scheduleSavedSearch(searchName);

		// Delete Security group
		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		sg.deleteSecurityGroups(securitygroupname);

		System.out.println("Successfully ran for PA user");
		base.stepInfo("Successfully ran for PA user");
		login.logout();
	}

	/**
	 * @author Jeevitha Description : Search Audio File And Shares With Default
	 *         Security group. and schedule the save search(RPMXCON-57420)
	 * @Stabilization - done - session.saveSearchadvanced(searchName);
	 */
	@Test(description = "RPMXCON-57420", enabled = true, groups = { "regression" })
	public void searchAudioAndShareToDefaultsgRmu() throws InterruptedException, ParseException {
		// Login as a Rmu
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("RPMXCON-57420  SavedSearch ");

		base.selectsecuritygroup("Default Security Group");

		// Search audio file and save the file
		session.AudioAndNonAudioSearch(Input.audioSearchString1, "North American English");
		session.saveSearchadvanced(searchName);
		saveSearch.navigateToSavedSearchPage();

		// Shared with Default Security group
		saveSearch.shareSavedSearchRMU(searchName, "Default");
		base.stepInfo("Shared Successfuly with  Default Security Group");

		Thread.sleep(2000); // Added for consolidated execution
		// Schedule save search)
		saveSearch.scheduleSavedSearch(searchName);

		System.out.println("Successfully ran for RMU user");
		base.stepInfo("Successfully ran for RMU user");

		login.logout();
	}

	/**
	 * @author Jeevitha Description : Search Audio File And Shares With Default
	 *         Security group. and schedule the save search(RPMXCON-57420)
	 * @Stabilization - done - session.saveSearchadvanced(searchName);
	 */
	@Test(description = "RPMXCON-57421", enabled = true, groups = { "regression" })
	public void searchAudioAndShareToDefaultsgRev() throws InterruptedException, ParseException {
		// Login as a Rev
		login.loginToSightLine(Input.rev1userName, Input.rev1password);
		base.stepInfo("RPMXCON-57421  SavedSearch ");

		base.selectsecuritygroup("Default Security Group");

		// search audio file and save
		session.AudioAndNonAudioSearch(Input.audioSearchString1, "North American English");
		session.saveSearchadvanced(searchName);
		saveSearch.navigateToSavedSearchPage();

		// Shared with Default Security group
		saveSearch.shareSavedSearchRMU(searchName, "Default");
		base.stepInfo(" shared Successfuly with  Default Security Group");

		// Schedule save search
		saveSearch.scheduleSavedSearch(searchName);

		System.out.println("Successfully ran for REV user");
		base.stepInfo("Successfully ran for REV user");

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 9/07/21 Modified date:N/A Modified by:N/A
	 *         Description : Pre-requisites: 1. Project with 1K documents has
	 *         security groups SG1 and SG2 2. SG1 and SG2 has different sub-set of
	 *         project documents, around 100 in each
	 */
	@Test(description = "RPMXCON-49952", groups = { "regression" })
	public void preRequesties() throws InterruptedException {

		List<String> list = new ArrayList<String>();
		String searchName = "Search Name" + Utility.dynamicNameAppender();
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();

		// Login via PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As PA");

		sg = new SecurityGroupsPage(driver);
		driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		String securitygroupname1 = "securitygroupname" + Utility.dynamicNameAppender();
		String securitygroupname2 = "securitygroupname" + Utility.dynamicNameAppender();

		sg.AddSecurityGroup(securitygroupname1);
		System.out.println(securitygroupname1 + " : Added");
		sg.AddSecurityGroup(securitygroupname2);
		System.out.println(securitygroupname2 + " : Added");

		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName);

		login.logout();
		// Login via PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As PA");

		session.basicContentSearch(Input.searchString2);
		session.saveSearch(searchName1);

		list.add(searchName);
		list.add(searchName1);
		for (String a : list) {
			System.out.println(a);
			saveSearch.savedSearch_Searchandclick(a);
			saveSearch.preRequestCreation(a, securitygroupname1);
		}

		login.logout();

	}

	/**
	 * @author Raghuram A Date: 9/01/21 Modified date:N/A Modified by: Raghuram A
	 *         Description : For Reviewer Login - Validate available options for
	 *         user while saving a search query from session search page
	 * @param testMethod
	 */
	@Test(description = "RPMXCON-49952", enabled = true, groups = { "regression" })
	public void verifyAvailableOptionsFromSessionSearch() {

		base.stepInfo("Test case Id: RPMXCON-49952 - Saved Search Sprint 02");
		login.loginToSightLine(Input.rev1userName, Input.rev1password);

		session.basicContentSearch(Input.searchString2);
		base.waitForElement(session.getSaveSearch_Button());
		session.getSaveSearch_Button().waitAndClick(15);

		session.saveSearchPopupVerification();
		base.stepInfo("Pop-up verification completed");

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 9/01/21 Modified date:9/06/21 Modified by: Raghuram
	 *         A Description : SA/DA/PA user impersonate down as RMU/RU role, create
	 *         Searchgroups and Searches, and then runs the Custom Document Data
	 *         Report against My saved searches in PAU role
	 * @param testMethod
	 * @throws InterruptedException
	 * @Stabilization - done
	 */
	@Test(description = "RPMXCON-57409", enabled = true, groups = { "regression" })
	public void customDataReportAgainstPA() throws InterruptedException {

		String folderName = "Folder" + Utility.dynamicNameAppender();
		String searchName = "Search Name" + Utility.dynamicNameAppender();
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();

		report = new ReportsPage(driver);
		base.stepInfo("Test case Id: RPMXCON-57409 - Saved Search Sprint 02");

		// Login via PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As PA");
		String newNodeFromPA = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "PA", "No");
		int purehit = session.basicContentSearch(Input.searchString1);

		// Get Count
		session.searchSavedSearchResult(Input.mySavedSearch);
		int aggregateHitCount = session.saveAndReturnPureHitCount();

		// Impersonate As RMU via PA and create new searchgroup
		base.impersonatePAtoRMU();
		base.stepInfo("Impersonated As RMU");
		String newNodeFromRMU = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", "No");

		// Save Search
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName);
		session.saveSearchInNewNode(searchName, newNodeFromRMU);

		// impersonate As REV and create new searchgroup
		base.impersonateRMUtoReviewer();
		base.stepInfo("Impersonated As Reviewer");
		String newNodeFromRev = saveSearch.createSearchGroupAndReturn(searchName, "REV", "No");

		// Save search
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName1);
		session.saveSearchInNewNode(searchName1, newNodeFromRev);
		driver.waitForPageToBeReady();

		// impersonate As Reviewer to RMU
		driver.waitForPageToBeReady();
		base.impersonateReviewertoRMU();
		base.stepInfo("Impersonated As RMU");

		report.VerificationAndreportGenerator(newNodeFromPA, newNodeFromRMU, newNodeFromRev, "", searchName,
				searchName1, aggregateHitCount);

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 9/03/21 Modified date:N/A Modified by: Raghuram A
	 *         Description : SAU/DAU/PAU impersonate down as RMU/RU role, create
	 *         Searchgroups and Searches, and then runs Categorization against My
	 *         saved searches in PAU role
	 * @param testMethod
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57414", enabled = true, groups = { "regression" })
	public void categorizationAgainstPAsavedSearch() throws InterruptedException {

		String folderName = "Folder" + Utility.dynamicNameAppender();
		String searchName = "Search Name" + Utility.dynamicNameAppender();
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();

		report = new ReportsPage(driver);
		base.stepInfo("Test case Id: RPMXCON-57414 - Saved Search Sprint 02");

		// Login via PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As PA");
		String newNodeFromPA = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "PA", "No");

		// impersonate As RMU via PA and create new searchgroup
		base.impersonatePAtoRMU();
		base.stepInfo("Impersonated As RMU");
		String newNodeFromRMU = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", "No");

		// create folder and add save search in folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(folderName, "Default Security Group");
		int purehit = session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName);
		session.saveSearchInNewNode(searchName, newNodeFromRMU);

		// impersonate As REV and create new searchgroup
		base.impersonateRMUtoReviewer();
		base.stepInfo("Impersonated As Reviewer");
		String newNodeFromRev = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "REV", "No");

		// create folder and add save search in folder
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName1);
		session.saveSearchInNewNode(searchName1, newNodeFromRev);

		driver.waitForPageToBeReady();
		// impersonate As Reviewer to RMU
		base.impersonateReviewertoRMU();
		base.stepInfo("Impersonated As RMU");

		categorize = new Categorization(driver);
		categorize.categorizationFlow(newNodeFromPA, newNodeFromRMU, newNodeFromRev, searchName, searchName1, "RMU");

		// impersonate As PA via RMU
		base.rolesToImp("RMU", "PA");
		base.stepInfo("Back As PA");

		categorize.categorizationFlow(newNodeFromPA, newNodeFromRMU, newNodeFromRev, searchName, searchName1, "PA");

		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @throws InterruptedException Description:PAU impersonate down as RMU/RU role,
	 *                              create Searchgroups and Searches, and then runs
	 *                              the Advanced Batch Management Report against My
	 *                              saved searches in RMU role (RPMXCON-57417)
	 */
	@Test(description = "RPMXCON-57417", enabled = true, groups = { "regression" })
	public void advanceBatchManagementReport() throws InterruptedException {
		String searchName1 = "Search01" + Utility.dynamicNameAppender();
		String folderName = "Folder01" + Utility.dynamicNameAppender();
		String assignmentName = "Assignmenet01" + Utility.dynamicNameAppender();
		String codingform = "CodingForm01" + Utility.dynamicNameAppender();

		// impersonate As RMU
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("(RPMXCON-57417 Saved Search");

		report = new ReportsPage(driver);
		base.impersonatePAtoRMU();

		// create new searchgroup
		String newNode = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", "No");

		// create folder and add save search in folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(folderName, "Default Security Group");
		int purehit = session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName1);
		session.saveSearchInNewNode(searchName1, newNode);
		session.bulkFolderExisting(folderName);
		assign = new AssignmentsPage(driver);
		assign.createAssignment(assignmentName, Input.codeFormName);

		// impersonate As REV
		base.impersonateRMUtoReviewer();

		// create new searchgroup
		String newNode1 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "REV", "No");

		// create folder and add save search in folder
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName1);
		session.saveSearchInNewNode(searchName1, newNode1);

		session.bulkFolderExisting(folderName);

		// impersonate As RMu
		driver.Manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		base.waitForElement(base.getSignoutMenu());
		base.impersonateReviewertoRMU();

		driver.waitForPageToBeReady();
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");

		int totalDocs = report.advanceBatchManagementReport(folderName, assignmentName);
		System.out.println("Purehit = " + purehit + "," + "total docs = " + totalDocs);
		softAssertion.assertEquals(purehit, totalDocs);
		softAssertion.assertAll();
		login.logout();

	}

	/**
	 * @author Jeevitha R
	 * @throws InterruptedException Description: PAU impersonate down as RMU/RU
	 *                              role, create Searchgroups and Searches, and then
	 *                              runs the Review Results Report against My saved
	 *                              searches in PAU role RPMXCON-57413
	 * @Stabilization - done
	 */
	@Test(description = "RPMXCON-57413", enabled = true, groups = { "regression" })
	public void reviewResultReport() throws InterruptedException {
		String TagName = "Tag" + Utility.dynamicNameAppender();
		String folderName = "Folder" + Utility.dynamicNameAppender();
		String TagName1 = "Tag1" + Utility.dynamicNameAppender();
		String folderName1 = "Folder1" + Utility.dynamicNameAppender();

		// impersonate As RMU
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-57413 Saved Search");

		// Pre-reqiuisite
		base.impersonatePAtoRMU();

		// create folder and add save search in folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(folderName, "Default Security Group");
		tagsAndFolderPage.CreateTag(TagName, "Default Security Group");
		int purehitRMU = session.basicContentSearch(Input.searchString1);
		session.bulkFolderExisting(folderName);
		session.bulkTagExisting(TagName);
		System.out.println(purehitRMU);

//		 impersonate As REV
		base.waitForElement(base.getSignoutMenu());
		base.impersonateRMUtoReviewer();

		int purehitReviewer = session.basicContentSearch(Input.searchString1);
		session.bulkFolderExisting(folderName);
		session.bulkTagExisting(TagName);
		System.out.println(purehitReviewer);

//		impersonate as RMU
		base.waitForElement(base.getSignoutMenu());
		base.impersonatePAtoRMU();
		report = new ReportsPage(driver);
		report.reviewResultReport();
		report.selectFoldersInReviewResult(folderName);
		report.selectTagsInReviewResult(TagName);
		report.applyChangesButton().waitAndClick(10);

		// impersonate As PA
		driver.waitForPageToBeReady();
		base.waitForElement(report.getPageHeader());
		base.rolesToImp("RMU", "PA");

		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		report.reviewResultReport();
		report.verifyFolderNotPresent(folderName, TagName);

		// create folder and add save search in folder
		driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(folderName1, "Default Security Group");
		tagsAndFolderPage.CreateTag(TagName1, "Default Security Group");
		int purehitPA = session.basicContentSearch(Input.searchString1);
		session.bulkFolderExisting(folderName1);
		session.bulkTagExisting(TagName1);
		System.out.println(purehitPA);

		report.reviewResultReport();
		report.selectFoldersInReviewResult(folderName1);
		report.selectTagsInReviewResult(TagName1);
		report.applyChangesButton().Click();
		login.logout();

	}

	/**
	 * @author Jeevitha
	 * @throws InterruptedException Description: PAU impersonate down as RMU/RU
	 *                              role, create Searchgroups and Searches, and then
	 *                              runs Production against My saved searches in PAU
	 *                              role successfully RPMXCON-57416
	 */
	@Test(description = "RPMXCON-57416", enabled = true, groups = { "regression" })
	public void productionManagementReport() throws InterruptedException {
		String searchGroup = "Group2" + Utility.dynamicNameAppender();
		String saveSearch1 = "search2" + Utility.dynamicNameAppender();
		String saveSearch2 = "search3" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String productionname1 = "p1" + Utility.dynamicNameAppender();
		String productionname2 = "p2" + Utility.dynamicNameAppender();
		String SearchNodeNamePA = "PA1" + Utility.dynamicNameAppender();

		// impersonate As RMU
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-57416 Saved Search");
		base.impersonatePAtoRMU();

		// create new searchgroup
		String Node1 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", "No");

		// add save search
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(saveSearch1);
		session.saveSearchInNewNode(saveSearch2, Node1);

		// impersonate As REV
		driver.Manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		base.waitForElement(base.getSignoutMenu());
		base.impersonateRMUtoReviewer();

		// create new searchgroup
		String Node2 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "REV", "No");

		// add save search
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(saveSearch1);
		session.saveSearchInNewNode(saveSearch2, Node2);

		// impersonate As RMU
		driver.Manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		base.waitForElement(base.getSignoutMenu());
		base.impersonatePAtoRMU();

		// To Select & check mySavedSearch and savedNodeSearch
		ProductionPage page = new ProductionPage(driver);
		page.navigateToProductionPage();
		driver.waitForPageToBeReady();
		String beginningBates = page.getRandomNumber(2);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocuSelectionPage(saveSearch1, saveSearch2);
		page.navigateToNextSection();
		System.out.println("Search groups And Search Of Rmu& Rev Selected Successfully ");
		base.stepInfo("Search groups And Search Of Rmu& Rev Selected Successfully ");

		// impersonate As PA
		driver.Manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		base.rolesToImp("RMU", "PA");

		// create new searchgroup
		String nw_node = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "PA", "No");

		// To check searches Not present in PA
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		page.addANewProduction(productionname1);
		page.fillingDATSection();
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocuSelectionPage(searchGroup, saveSearch2);
		System.out.println("Search groups and search Of RMU is not available");
		base.stepInfo("Search groups and search Of RMU is not available");

		// add save search
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(saveSearch2);
		session.saveSearchInNewNode(SearchNodeNamePA, nw_node);

		// To Select & check mySavedSearch and savedNodeSearch
		driver.getWebDriver().get(Input.url + "Production/Home");
		page.addANewProduction(productionname2);
		page.fillingDATSection();
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocuSelectionPage(saveSearch2, SearchNodeNamePA);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname2);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
//	page.fillingGeneratePage();
		page.fillingGeneratePageWithContinueGenerationPopup();
		login.logout();

	}

	/**
	 * @author Jeevitha
	 * @throws InterruptedException Description : PA impersonate down as RMU/RU
	 *                              role, create Searchgroups and Searches, and then
	 *                              runs Batch Print against My saved searches in
	 *                              PAU role RPMXCON-57415
	 */
	@Test(description = "RPMXCON-57415", enabled = true, groups = { "regression" })
	public void batchPrint() throws InterruptedException {
		String searchGroup = "Group2" + Utility.dynamicNameAppender();
		String saveSearch1 = "search2" + Utility.dynamicNameAppender();
		String saveSearch2 = "search3" + Utility.dynamicNameAppender();

		// impersonate As RMU
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-57415 Saved search");
		base.impersonatePAtoRMU();

		// create new searchgroup
		String newNode = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", "No");

		// create folder and add save search in folder
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(saveSearch1);
		session.saveSearchInNewNode(saveSearch2, newNode);

		// impersonate As REV
		base.impersonateRMUtoReviewer();

		// create new searchgroup
		String newNode1 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "REV", "No");
		System.out.println(newNode1);
		// create folder and add save search in folder
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(saveSearch1);
		session.saveSearchInNewNode(saveSearch2, newNode1);

		// impersonate As RMU
		driver.waitForPageToBeReady();
		base.rolesToImp("REV", "RMU");

		// To Select & check mySavedSearch and savedNodeSearch
		BatchPrintPage batch = new BatchPrintPage(driver);
		batch.navigateToBatchPrintPage();
		batch.saveSearchRadiobutton(saveSearch1);

		// impersonate As PA
		driver.Manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		base.waitForElement(base.getSignoutMenu());
		base.rolesToImp("RMU", "PA");

		batch = new BatchPrintPage(driver);
		batch.navigateToBatchPrintPage();
		batch.saveSearchRadiobutton(saveSearch1);

		// create new searchgroup
		String new_node = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "PA", "No");
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName);
		driver.getWebDriver().get(Input.url + "BatchPrint/");
		batch.saveSearchRadiobutton(searchName);
		login.logout();

	}

	/**
	 * Date: 9/13/21 Modified date:1/5/2022 Modified by: Description : Schedule
	 * saved searches(with audio and non-audio docs) under <My Saved Search> and
	 * verify documents - RPMXCON-57418 Sprint 03
	 */
	@Test(description = "RPMXCON-57418", enabled = true, groups = { "regression" })
	public void searchAndShareAsPa() throws InterruptedException, ParseException {
		// Login as a PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-57418 SavedSearch ");

		String newNode = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "PA", "No");
		session.AudioAndNonAudioSearch(Input.audioSearchString1, "North American English");
		session.saveAdvanceSearchInNode(searchName, newNode);

		// Shared with Security group
		saveSearch.shareSavedSearchFromNode(searchName, "Default");
		base.stepInfo("Shared Successfuly with  Security group");

		// Schedule save search
		driver.getWebDriver().navigate().refresh();
		driver.waitForPageToBeReady();
		saveSearch.getSavedSearchGroupName(Input.shareSearchDefaultSG).waitAndClick(3);
		saveSearch.selectNode1(newNode);
		saveSearch.scheduleSavedSearch(searchName);

		System.out.println("Successfully ran for PA user");
		base.stepInfo("Successfully ran for PA user");
		login.logout();
	}

	/**
	 * Date: 9/13/21 Modified date:1/5/2022 Modified by: Description : SA/DA/PA
	 * impersonate down as RMU/RU role, create Searchgroups and Searches, and then
	 * runs the Document Audit Report against My saved searches in PAU role -
	 * RPMXCON-57410 Sprint 03
	 * 
	 * @Stabilzation - done
	 */
	@Test(description = "RPMXCON-57410", enabled = true, groups = { "regression" })
	public void documentAuditReport() throws InterruptedException {
		String searchGroup = "Group1" + Utility.dynamicNameAppender();
		String saveSearch1 = "search1" + Utility.dynamicNameAppender();
		String saveSearch2 = "search2" + Utility.dynamicNameAppender();
		String assignment = "Assign" + Utility.dynamicNameAppender();
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// impersonate As RMU
		base.impersonatePAtoRMU();
		base.stepInfo("RPMXCON-57410 Saved Search");

		// create new searchgroup
		String newNodeRMU = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", "No");
		System.out.println(newNodeRMU);

		session.basicContentSearch(Input.searchString1);
		session.saveSearch(saveSearch1);
		session.saveSearchInNewNode(saveSearch2, newNodeRMU);

//		impersonate As REV
		base.impersonateRMUtoReviewer();

//		create new searchgroup
		String newNodeREV = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "REV", "No");
		System.out.println(newNodeREV);

		session.basicContentSearch(Input.searchString1);
		session.saveSearch(saveSearch1);
		session.saveSearchInNewNode(saveSearch2, newNodeREV);

//		impersonate as RMU
		base.waitTime(2);
		base.impersonatePAtoRMU();
		DocumentAuditReportPage documentAuditReport = new DocumentAuditReportPage(driver);
		documentAuditReport.verifySource(saveSearch1, saveSearch2);

//		impersonate As PA
		base.waitTime(5);
		base.impersonateSAtoPA();

//		create new searchgroup
		String newNodePA = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "PA", "No");
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(saveSearch2);
		session.saveSearchInNewNode(newNodePA, newNodePA);

//		impersonate As PA
		base.waitTime(3);
		base.impersonateSAtoPA();

		documentAuditReport.verifySource(saveSearch1, saveSearch2);

		documentAuditReport.verifySource(newNodePA, saveSearch2);
		login.logout();
	}

	/**
	 * Date: 9/13/21 Modified date:1/5/2022 Modified by: Description : For RMU -
	 * Validate modifying searches/groups from the shared with <Security Group Name>
	 * by any other PAU user - RPMXCON-49885 Sprint 03
	 */
	@Test(description = "RPMXCON-49885", enabled = true, groups = { "regression" })
	public void verifySharedNode() throws InterruptedException {
		String SearchNamePA = "Search1" + Utility.dynamicNameAppender();
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-49885 Saved Search");

		// create new searchgroup
		String newNodePA = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "PA", "No");
		System.out.println(newNodePA);
		saveSearch.shareSavedNodePA(newNodePA);
		saveSearch.verifySharedNode(Input.shareSearchDefaultSG, newNodePA, "", false, "", false);

		// impersonate As RMU
		base.impersonatePAtoRMU();
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.verifySharedNode(Input.shareSearchDefaultSG, newNodePA, "", false, "", false);
		saveSearch.deleteSharedNode(newNodePA);

		// impersonate As RMU
		driver.waitForPageToBeReady();
		base.impersonateSAtoPA();

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.verifySharedNode(Input.shareSearchDefaultSG, newNodePA, "", false, "", false);
		login.logout();
	}

	/**
	 * @author Raghuram A Date: 9/13/21 Modified date:N/A Modified by: Description :
	 *         As a RM user login, to check the back button functionality when user
	 *         selected any saved search query from saved search page and apply edit
	 *         on that, and will modify the search query and execute the same after
	 *         modification - RPMXCON-47381 Sprint 03
	 * @Stabilization - done
	 */
	@Test(description = "RPMXCON-47381", enabled = true, groups = { "regression" })
	public void checkBackButton() throws InterruptedException {

		base.stepInfo("Test case Id: RPMXCON-47381 - Saved Search Sprint 03");
		// Login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// Search
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();
		session.basicContentSearch(Input.searchString2);
		session.saveSearch(searchName1);

		// Modify Search via Saved Search
		saveSearch.savedSearchEdit(searchName1);
		session.modifySearchTextArea(1, Input.searchString2, Input.searchString1, "Save");
		base.stepInfo("Modified search query, & executed the same  ");

		// Navigate back and Verify landing page
		driver.Navigate().back();
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		String currentUrl = driver.getWebDriver().getCurrentUrl();
		softAssertion.assertEquals(Input.url + "SavedSearch/SavedSearches", currentUrl);
		base.stepInfo("Navigated back : " + currentUrl);

		softAssertion.assertAll();
		login.logout();
	}

	/**
	 * @author Raghuram A Date: 9/15/21 Modified date:9/16/21 Modified by: Raghuram
	 *         A Description : To verify, As an Reviewer user login, When I will
	 *         share a search from saved search, In share search pop up window I
	 *         will be able to search all the Users belong to same project
	 *         RPMXCON-47459- Sprint 03
	 */
	@Test(description = "RPMXCON-47459", enabled = true, groups = { "regression" })
	public void shareSearchPopup() throws InterruptedException {

		base.stepInfo("Test case Id: RPMXCON-47459 - Saved Search Sprint 03");
		// Login via Reviewer
		login.loginToSightLine(Input.rev1userName, Input.rev1password);
		base.stepInfo("Loggedin As : " + Input.rev1FullName);

		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();
		session.basicContentSearch(Input.searchString2);
		session.saveSearch(searchName1);

		// Choose search
		saveSearch.savedSearch_Searchandclick(searchName1);
		saveSearch.sharePoupVerificationOfAvailableSharedSG("NotClicked", "Close");

		login.logout();
	}

	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
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

		UtilityLog.info("******Execution completed for " + this.getClass().getSimpleName() + "********");
	}
}
