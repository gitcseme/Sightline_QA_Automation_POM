package sightline.savedSearch;

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

public class SavedSearch_Audio_Regression_Set_02 {
	Driver driver;
	LoginPage login;
	SavedSearch saveSearch;
	SessionSearch session;
	BaseClass base;
	SecurityGroupsPage sg;
	SoftAssert softAssertion;
	DocListPage dcPage;

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

	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
		}
		try {
			base.clearAllCookies();
			login.quitBrowser();
		} catch (Exception e) {
			login.quitBrowser();
		}
//		login.clearBrowserCache();
		System.out.println("Executed :" + result.getMethod().getMethodName());
	}

	@AfterClass(alwaysRun = true)
	public void close() {

		UtilityLog.info("******Execution completed for " + this.getClass().getSimpleName() + "********");
	}
}
