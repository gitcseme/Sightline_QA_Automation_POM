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

public class SavedSearch_Audio_Regression_Set_04 {
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
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.logBefore(testMethod.getName());

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
	 * @author Raghuram A Date: 10/5/21 Modified date:N/A Modified by: Description :
	 *         Validate SA user is allowed to move SearchGroups/Searches only within
	 *         MySearches : RPMXCON-49949 - Sprint 03
	 * @throws InterruptedException
	 * @throws ParseException
	 * @Stabilization - done - new imp
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 41)
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
	@Test(enabled = true, groups = { "regression" }, priority = 42)
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
	@Test(enabled = true, dataProvider = "SavedSearchwithUsers", groups = { "regression" }, priority = 43)
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
	@Test(enabled = true, groups = { "regression" }, priority = 44)
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
	@Test(enabled = true, groups = { "regression" }, priority = 45)
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
	@Test(enabled = true, groups = { "regression" }, priority = 46)
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
	@Test(enabled = true, groups = { "regression" }, priority = 47)
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

	@Test(enabled = true, groups = { "regression" }, priority = 50)
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
		saveSearch.getToDocView().waitAndClick(5);

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

	@Test(enabled = true, groups = { "regression" }, priority = 51)
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

	@Test(enabled = true, groups = { "regression" }, priority = 52)
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
	@Test(enabled = true, groups = { "regression" }, priority = 53)
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
	@Test(enabled = true, groups = { "regression" }, priority = 54)
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
	@Test(enabled = true, groups = { "regression" }, priority = 55)
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
	@Test(enabled = true, groups = { "regression" }, priority = 56)
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

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		Reporter.setCurrentTestResult(result);
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
		}
		try {
			base.clearAllCookies();
			login.quitBrowser();
		} catch (Exception e) {
			login.quitBrowser();
		}
		System.out.println("Executed :" + result.getMethod().getMethodName());
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		UtilityLog.info("******Execution completed for " + this.getClass().getSimpleName() + "********");
		try {
			login.quitBrowser();
		} catch (Exception e) {
			login.quitBrowser();
		}
	}
}
