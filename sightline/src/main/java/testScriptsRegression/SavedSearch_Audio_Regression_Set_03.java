package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
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

public class SavedSearch_Audio_Regression_Set_03 {
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

		// Open browser
		driver = new Driver();
		base = new BaseClass(driver);
		login = new LoginPage(driver);
		saveSearch = new SavedSearch(driver);
		session = new SessionSearch(driver);
		sg = new SecurityGroupsPage(driver);
		softAssertion = new SoftAssert();
		dcPage = new DocListPage(driver);
		tagsAndFolderPage = new TagsAndFoldersPage(driver);

	}

	/**
	 * @author Raghuram A Date: 9/27/21 Modified date: N/A Modified by: Description
	 *         : TC05_Verify that application does not display any warnings when a
	 *         user execute search group which contains only Advanced Search(es) in
	 *         Draft and Completed state on Saved Search Screen. -RPMXCON-49829
	 *         Sprint 03 - new imp - done
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 26) // need to check with raghu
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
	@Test(enabled = true, groups = { "regression" }, priority = 27) // check with jeevitha
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
	@Test(enabled = true, groups = { "regression" }, priority = 28)
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
	@Test(enabled = true, dataProvider = "SavedSearchwithUsers", groups = { "regression" }, priority = 29)
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
	@Test(enabled = true, groups = { "regression" }, priority = 30)
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
	@Test(enabled = true, dataProvider = "SavedSearchwithUsers", groups = { "regression" }, priority = 31)
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
	@Test(groups = { "regression" }, priority = 32)
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
	@Test(groups = { "regression" }, priority = 33)
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
	@Test(groups = { "regression" }, priority = 34)
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

	@Test(enabled = true, groups = { "regression" }, priority = 35)
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

	@Test(enabled = true, groups = { "regression" }, priority = 36)
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
	@Test(enabled = true, groups = { "regression" }, priority = 37)
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
	@Test(enabled = true, groups = { "regression" }, priority = 38)
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
	 @Test(enabled = true, groups = { "regression" }, priority = 39)
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
	 @Test(enabled = true, groups = { "regression" }, priority = 40)
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
	 * @author Jayanthi.ganesan Modified date:9/22/21 Modified by: Raghuram A -
	 *         modified Dataprovider parameter
	 * @throws InterruptedException
	 * @description Renames an existing Advanced saved search on Saved Search
	 *              Screen. RPMXCON-49351
	 */
	@DataProvider(name = "SavedSearchwithUsers")
	public Object[][] SavedSearchwithUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName } };
		return users;
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.logBefore(testMethod.getName());
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		Reporter.setCurrentTestResult(result);
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			try { // if any tc failed and dint logout!
				login.logout();
			} catch (Exception e) {
//						 TODO: handle exception
			}
		}
		System.out.println("Executed :" + result.getMethod().getMethodName());
	}

	@AfterClass(alwaysRun = true)
	public void close() {

		try {
			driver.scrollPageToTop();

			login.closeBrowser();
		} finally {
			login.clearBrowserCache();
			LoginPage.clearBrowserCache();
		}
	}
}
