package sightline.savedSearch;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
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
import automationLibrary.Element;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.BatchPrintPage;
import pageFactory.Categorization;
import pageFactory.DocListPage;
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

public class SavedSearch_Phase1_Regression4 {

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

		// To be moved to respective @Test
		softAssertion = new SoftAssert();
		sg = new SecurityGroupsPage(driver);
		dcPage = new DocListPage(driver);
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		assign = new AssignmentsPage(driver);
	}

	/**
	 * @author Raghuram Modified date:N/A Modified by: N/A - modified Dataprovider
	 *         parameter
	 * @throws InterruptedException
	 */
	@DataProvider(name = "SavedSearchwithUsers")
	public Object[][] SavedSearchwithUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName } };
		return users;
	}

	@DataProvider(name = "SavedSearchwithRMUandREV")
	public Object[][] SavedSearchwithRMUandREV() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName } };
		return users;
	}

	@DataProvider(name = "userRolesOnlyrmuAndRev")
	public Object[][] userRolesOnlyrmuAndRev() {
		Object[][] users = { { "RMU" }, { "REV" } };
		return users;
	}

	@DataProvider(name = "SavedSearchwithPAandRMUwithRole")
	public Object[][] SavedSearchwithPAandRMUwithRole() {
		Object[][] users = {
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName, "RMU", Input.pa1userName,
						Input.pa1password },
				{ Input.pa1userName, Input.pa1password, Input.pa1FullName, "PA", Input.rmu1userName,
						Input.rmu1password } };
		return users;
	}

	@DataProvider(name = "SavedSearchwithPAandRMUwithRoleANDshareType")
	public Object[][] SavedSearchwithPAandRMUwithRoleANDshareType() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName, "PA", 1 },
				{ Input.pa1userName, Input.pa1password, Input.pa1FullName, "PA", 2 },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName, "RMU", 0 },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName, "REV", 0 } };
		return users;
	}

	@DataProvider(name = "SavedSearchwithUsersandrole")
	public Object[][] SavedSearchwithUsersandrole() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName, "PA" },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName, "RMU" },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName, "REV" } };
		return users;
	}

	/**
	 * @author Raghuram A Date: 10/11/21 Modified date:N/A Modified by: Description
	 *         : To verify, as an user login into the Application, I will be able to
	 *         export the saved search from search group in Tab-Delimited format
	 *         -RPMXCON-47735 Sprint 04
	 * @throws InterruptedException
	 * @throws ParseException
	 * @Stabilization - done - new imp - done
	 */
	@Test(description = "RPMXCON-47735", enabled = true, dataProvider = "SavedSearchwithUsers", groups = {
			"regression" })
	public void verifyExport_TagDelimiter(String userName, String password, String fullName)
			throws InterruptedException {
		String SearchName = "comments" + Utility.dynamicNameAppender();
		String stypleToChoose = "Tab-Delimited";
		String filedTypetoChoose = "[,] 044";

		base.stepInfo("Test case Id: RPMXCON-47735 - Saved Search Sprint 04");
		// Login as PA
		login.loginToSightLine(userName, password);
		base.stepInfo("Loggedin As : " + fullName);

		// create new search group and save search
		String new_node = saveSearch.createSearchGroupAndReturn(searchName1, userName, "No");
		base.stepInfo("Search and saveSearch in the created node");
		int purehit = session.basicContentSearch(Input.searchString2);
		session.saveSearchInNewNode(SearchName, new_node);
		driver.waitForPageToBeReady();

		// Verify Export
		saveSearch.navigateToSSPage();
		saveSearch.selectNode1(new_node);
		saveSearch.savedSearch_SearchandSelect(SearchName, "Yes"); // modified
		saveSearch.verifyExportpopup(stypleToChoose, filedTypetoChoose);

		// Deleted NOde
		saveSearch.deleteNode(Input.mySavedSearch, new_node, true, true);

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 10/11/21 Modified date:N/A Modified by: Description
	 *         : Verify that correct number of documents appears when user Selects
	 *         "Bulk Release" action from Basic Search Screen -RPMXCON-47953 Sprint
	 *         04
	 * @throws InterruptedException
	 * @throws ParseException
	 * @Stabilization - done - new imp - done
	 */
	@Test(description = "RPMXCON-47953", enabled = true, groups = { "regression" })
	public void verifyBasic_bulkReleaseCount() throws InterruptedException {
		String SearchName = "comments" + Utility.dynamicNameAppender();
		String SecGroup = Input.securityGroup;

		base.stepInfo("Test case Id: RPMXCON-47953 - Saved Search Sprint 04");
		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// create new search group and save search
		String new_node = saveSearch.createSearchGroupAndReturn(SearchName, Input.pa1userName, "No");
		base.stepInfo("Search and saveSearch in the created node");

		int purehit = session.basicContentSearch(Input.searchString2);
		session.saveSearchInNewNode(SearchName, new_node);
		driver.waitForPageToBeReady();

		saveSearch.navigateToSSPage();
		saveSearch.selectNode1(new_node);

		saveSearch.savedSearch_SearchandSelect(SearchName, "Yes"); // modified
		base.waitForElement(saveSearch.getSavedSearchEditButton());
		saveSearch.getSavedSearchEditButton().waitAndClick(5);

		int newPurehit = session.verifyBulk_releaseCount(SecGroup);
		base.digitCompareEquals(purehit, newPurehit, "Verified the correct Bulkrelease document Number:" + newPurehit,
				"Bulkrelease document Number mismTches :" + newPurehit);

		// Deleted NOde
		saveSearch.deleteNode(Input.mySavedSearch, new_node, true, true);

		login.logout();
	}

	/**
	 * @author Jeevitha Date: 10/11/21 Modified date:N/A Modified by: Description :
	 *         Verify that when user Saved Query then SS:<Query Name> appears left
	 *         had side In Session search on Advanced Search screen - -RPMXCON-47955
	 *         Sprint 04
	 * @throws InterruptedException
	 * @throws ParseException
	 * @Stabilization - done - new imp - done
	 */
	@Test(description = "RPMXCON-47955", enabled = true, groups = { "regression" })
	public void savedQuerySSverify() throws InterruptedException {

		String SearchName1 = "test" + Utility.dynamicNameAppender();
		String searchText = Input.searchString1;

		base.stepInfo("Test case Id: RPMXCON-47955 - Saved Search Sprint 04");
		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// create new searchgroup
		String new_node = saveSearch.createSearchGroupAndReturn(SearchName1, Input.pa1userName, "No");

		int purehit = session.advancedContentSearch(searchText);
		session.saveSearchInNewNode(SearchName1, new_node);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		base.stepInfo("Landed on SavedSearchPage");
		saveSearch.selectNode1(new_node);
		saveSearch.savedSearch_SearchandSelect(SearchName1, "Yes"); // modified

		base.waitForElement(saveSearch.getSavedSearchEditButton());
		saveSearch.getSavedSearchEditButton().waitAndClick(5);
		session.verifySS();

		// Deleted NOde
		saveSearch.deleteNode(Input.mySavedSearch, new_node, true, true);

		login.logout();
	}

	/**
	 * @author Jeevitha Date: 10/11/21 Modified date:N/A Modified by: Description :
	 *         Verify that correct number of documents appears when user Selects
	 *         "Bulk Tag" action from Advanced Search Screen - -RPMXCON-47959 Sprint
	 *         04
	 * @throws InterruptedException
	 * @throws ParseException
	 * @Stabilization - done - new imp - done
	 */
	@Test(description = "RPMXCON-47959", enabled = true, groups = { "regression" })
	public void veridyCountFromBulkTagviaADS() throws InterruptedException {

		String SearchName1 = "test" + Utility.dynamicNameAppender();
		String searchText = Input.searchString1;
		String TagName = "tag" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-47959 - Saved Search Sprint 04");
		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// create new searchgroup
		String new_node = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, Input.pa1userName, "No");

		int purehit = session.advancedContentSearch(searchText);
		session.saveSearchInNewNode(SearchName1, new_node);

		saveSearch.navigateToSSPage();
		saveSearch.selectNode1(new_node);
		saveSearch.savedSearch_SearchandSelect(SearchName1, "Yes"); // modified

		base.waitForElement(saveSearch.getSavedSearchEditButton());
		saveSearch.getSavedSearchEditButton().waitAndClick(5);
		int PureHit = session.verifyBulkTag(TagName);

		base.digitCompareEquals(purehit, PureHit, "Verified the correct Bulktag document Number:" + PureHit,
				"Verified the correct Bulktag document Number:" + PureHit + "Mismatches");

		// Deleted NOde
		saveSearch.deleteNode(Input.mySavedSearch, new_node, true, true);

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 10/11/21 Modified date:N/A Modified by: Description
	 *         : Verify that correct number of documents appears when user Selects
	 *         "Bulk Folder" action from Advanced Search Screen - -RPMXCON-47958
	 *         Sprint 04
	 * @throws InterruptedException
	 * @throws ParseException
	 * @Stabilization - done - new imp - done
	 */
	@Test(description = "RPMXCON-47958", enabled = true, groups = { "regression" })
	public void veridyCountFromBulkFolderviaADS() throws InterruptedException {

		String SearchName1 = "test" + Utility.dynamicNameAppender();
		String searchText = Input.searchString1;
		String folderName = "folder" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-47958 - Saved Search Sprint 04");
		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// create new searchgroup
		String new_node = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, Input.pa1userName, "No");

		int purehit = session.advancedContentSearch(searchText);
		session.saveSearchInNewNode(SearchName1, new_node);

		saveSearch.navigateToSSPage();
		saveSearch.selectNode1(new_node);
		saveSearch.savedSearch_SearchandSelect(SearchName1, "Yes"); // modified

		base.waitForElement(saveSearch.getSavedSearchEditButton());
		saveSearch.getSavedSearchEditButton().waitAndClick(5);
		int pureHitCount = session.verifyBulkFolder(folderName);

		base.digitCompareEquals(purehit, pureHitCount,
				"Verified the correct Bulkfolder document Number:" + pureHitCount,
				"Verified the correct Bulkfolder document Number:" + pureHitCount + "Mismatches");

		// Deleted NOde
		saveSearch.deleteNode(Input.mySavedSearch, new_node, true, true);

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 10/11/21 Modified date:N/A Modified by: Description
	 *         : Verify share option for RMU and Reviewers - -RPMXCON-49954 Sprint
	 *         04
	 * @throws InterruptedException
	 * @throws ParseException
	 * @Stabilization - done
	 */
	@Test(description = "RPMXCON-49954", enabled = true, dataProvider = "SavedSearchwithRMUandREV", groups = {
			"regression" })
	public void verfiyShareforRMUandREV(String userName, String password, String fullName) throws InterruptedException {

		base.stepInfo("Test case Id: RPMXCON-49954 - Saved Search Sprint 04");
		// Login via Reviewer/RMU
		login.loginToSightLine(userName, password);
		base.stepInfo("Loggedin As : " + fullName);

		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();
		session.basicContentSearch(Input.searchString2);
		session.saveSearch(searchName1);

		// Choose search
		saveSearch.savedSearch_Searchandclick(searchName1);
		saveSearch.sharePoupVerificationOfAvailableSharedSG("NotClicked", "Close");

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 10/11/21 Modified date:N/A Modified by: Description
	 *         : Verify when user clicks on search icon available on top toolbar
	 *         from saved search screen, it should navigate to Basic Search -
	 *         -RPMXCON-49956 Sprint 04
	 * @throws InterruptedException
	 * @throws ParseException
	 * @Stabilization - done
	 */
	@Test(description = "RPMXCON-49956", enabled = true, dataProvider = "SavedSearchwithUsers", groups = {
			"regression" })
	public void verifyBSpageNavigationViaSS(String userName, String password, String fullName)
			throws InterruptedException {

		base.stepInfo("Test case Id: RPMXCON-49956 - Saved Search Sprint 04");
		// Login via Reviewer/RMU
		login.loginToSightLine(userName, password);
		base.stepInfo("Loggedin As : " + fullName);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		saveSearch.getbasicSearchIcon().Click();
		base.stepInfo("Clicked Search icon present at the top left of the tool bar");

		driver.waitForPageToBeReady();
		saveSearch.verifyBasicSearchPage();

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 10/6/21 Modified date:N/A Modified by: Description :
	 *         Verify that After adding Saved Query - application displays all
	 *         documents that are in the aggregate results- when User Performs Bulk
	 *         Folder from Child Search groups. -RPMXCON-49070 Sprint 04
	 * @throws InterruptedException
	 * @throws ParseException
	 * @Stabilize -- done - new imp - done
	 */
	
	@Test(description = "RPMXCON-49070", enabled = true, groups = { "regression" })
	public void aggregateResultWhileBulkAssignment() throws InterruptedException, ParseException {
		int latencyCheckTime = 5;
		String passMessage = "Application not hang or shows latency more than " + latencyCheckTime + " seconds.";
		String failureMsg = "Continues Loading more than " + latencyCheckTime + " seconds.";
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		Boolean inputValue = true;
		int finalCountresult, noOfNode = 2, purehitCount;
		String finalCount;
		String assignName = "assignName" + Utility.dynamicNameAppender();
		base.stepInfo("Test case Id: RPMXCON-49070 - Saved Search Sprint 04");

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
		saveSearch.navigateToSavedSearchPage();

		base.stepInfo("Root node selected : " + newNodeList.get(0));
		saveSearch.selectNode1(newNodeList.get(0));
		saveSearch.getSavedSearchToBulkAssign().waitAndClick(5);
		base.stepInfo("Clicked Assign Icon");

		// Load latency Verification SaveSearchToBulkFolder
		Element loadingElement = saveSearch.getbulkAssignTotalCountLoad();
		saveSearch.loadingCountVerify(loadingElement, latencyCheckTime, passMessage, failureMsg);
		finalCount = assign.assignDocstoNewAssgn(assignName);
		finalCountresult = Integer.parseInt(finalCount);
		base.stepInfo("Finalize count : " + finalCountresult);
		assign.quickAssignCreation(assignName, Input.codeFormName);
		session.switchToWorkproduct();
		purehitCount = session.selectAssignmentInWPSWs(assignName);
		base.stepInfo("PureHitcount via WP assignment selection : " + purehitCount);

		base.digitCompareEquals(finalCountresult, purehitCount,
				"After the Bulk Assignment - Pure hit appears like aggregate results set of all child search groups and searches   ",
				"Count Mismatches");

		// Delete Node
		saveSearch.deleteNode(Input.mySavedSearch, newNodeList.get(0), true, true);

		login.logout();
	}

	/**
	 * @author Jeevitha Description:Validate saving a new session search (executed
	 *         or draft) under My Searches or Shared Searches Sprint 04
	 * @throws InterruptedException
	 * @Stabilization- done - new imp - done
	 */
	@Test(description = "RPMXCON-49889", enabled = true, groups = { "regression" })
	public void verifySearchs() throws InterruptedException {
		String search1 = "Search4" + Utility.dynamicNameAppender();
		String search2 = "Search4" + Utility.dynamicNameAppender();

		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case Id: RPMXCON-49889 - Saved Search Sprint 04");
		session.navigateToSessionSearchPageURL();
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

		// Delete Search
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		saveSearch.navigateToSSPage();
		saveSearch.deleteSearch(search1, Input.shareSearchPA, "Yes");
		login.logout();
	}

	/**
	 * @author Jeevitha Description:PAU impersonating down as RMU/RU and scheduling
	 *         searches saved under My Saved Searches RPMXCON-57395 Sprint 04
	 * @throws InterruptedException
	 * @Stabilization - Done - new imp
	 */
	@Test(description = "RPMXCON-57395", enabled = true, dataProvider = "userRolesOnlyrmuAndRev", groups = {
			"regression" })
	public void verifyDocument2(String role) throws Exception {
		String savedSearch = "Search1" + Utility.dynamicNameAppender();
		String savedSearch2 = "Search2" + Utility.dynamicNameAppender();
		String savedSearch3 = "Search3" + Utility.dynamicNameAppender();
		String savedSearch4 = "Search4" + Utility.dynamicNameAppender();
		SchedulesPage schedule = new SchedulesPage(driver);
		String pureHit0;

		base.stepInfo("Test case Id: RPMXCON-57395 - Saved Search Sprint 04");
		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		if (role.equalsIgnoreCase("RMU")) {
			base.impersonatePAtoRMU();
		} else if (role.equalsIgnoreCase("REV")) {
			base.impersonatePAtoReviewer();
		}
		base.stepInfo("Impersonated as : " + role);

		// create new searchgroup
		String newNode1 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, role, "No");

		// create new searchgroup
		String newNode2 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, role, "No");

		// create new searchgroup
		String newNode3 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, role, "No");

		// create new searchgroup
		String newNode4 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, role, "No");

		int pureHit1 = session.advancedMetaDataSearch("CustodianName", null, Input.metaDataCN, null);
		session.saveSearchInNewNode(savedSearch, newNode1);

		session.getNewSearchButton().waitAndClick(20);
		int pureHit2 = session.advancedContentSearchWithSearchChanges(Input.searchString1, "Yes");
		session.saveSearchInNewNode(savedSearch2, newNode2);

		base.selectproject();
		int pureHit3 = session.conceptualSearch_new("field report", "mid");
		session.saveSearchInNewNode(savedSearch3, newNode3);
		System.out.println(pureHit3);

		session.getNewSearchButton().waitAndClick(20);
		session.getWorkproductBtnC().waitAndClick(30);
		session.selectRedactioninWPS(Input.defaultRedactionTag);
		int pureHit4 = session.saveAndReturnPureHitCount();
		driver.scrollPageToTop();
		session.saveSearchInNewNode(savedSearch4, newNode4);
		System.out.println(pureHit4);

		String dataSet[][] = { { savedSearch, String.valueOf(pureHit1) }, { savedSearch2, String.valueOf(pureHit2) },
				{ savedSearch3, String.valueOf(pureHit3) }, { savedSearch4, String.valueOf(pureHit4) } };
		String nodeList[][] = { { newNode1 }, { newNode2 }, { newNode3 }, { newNode4 } };

		int k = 0;
		for (int i = 0; i < dataSet.length; i++) {

			int j = 0;
			int l = 0;
			String searches = dataSet[i][j];
			j++;
			pureHit0 = dataSet[i][j];
			String Nodes = nodeList[k][l];

			// Make sure shared Node reflected in the SG
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");

			saveSearch.selectNode1(Nodes);
			// Schedule save search
			saveSearch.scheduleSavedSearchInMinute(searches, 2);

			// Make sure shared Node reflected in the SG
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.selectNode1(Nodes);
			saveSearch.savedSearch_SearchandSelect(searches, "Yes");

			// verify Document count After Execution
			String actualDocCount = saveSearch.getCountofDocs().getText();
			System.out.println(actualDocCount);
			softAssertion.assertEquals(pureHit0, actualDocCount);

			// Perform Bulk action
			driver.Navigate().refresh();
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.selectNode1(Nodes);
			saveSearch.savedSearch_SearchandSelect(searches, "Yes");
			saveSearch.saveSearchToDoclist();
			k++;
		}
		softAssertion.assertAll();
		login.logout();
	}

	/**
	 * @author Jeevitha Description:RMU/RU scheduling searches shared to Security
	 *         group and verify documents RPMXCON-57394 Sprint 04
	 * @Stabilization - Done - new imp
	 */

	@Test(description = "RPMXCON-57394", enabled = true, dataProvider = "SavedSearchwithRMUandREV", groups = {
			"regression" })
	public void verifyDocument1(String userName, String password, String fullName) throws Exception {
		String savedSearch = "Search1" + Utility.dynamicNameAppender();
		String savedSearch2 = "Search2" + Utility.dynamicNameAppender();
		String savedSearch3 = "Search3" + Utility.dynamicNameAppender();
		String savedSearch4 = "Search4" + Utility.dynamicNameAppender();
		SchedulesPage schedule = new SchedulesPage(driver);
		String pureHit0;
		int pureHit4 = 0;

		// Login as PA
		login.loginToSightLine(userName, password);
		base.stepInfo("Test case Id: RPMXCON-57394 - Saved Search Sprint 04");

		// create new searchgroup
		String newNode1 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, userName, "No");

		// create new searchgroup
		String newNode2 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, userName, "No");

		// create new searchgroup
		String newNode3 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, userName, "No");

		// create new searchgroup
		String newNode4 = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, userName, "No");

		int pureHit1 = session.advancedMetaDataSearch("CustodianName", null, Input.metaDataCN, null);
		session.saveSearchInNewNode(savedSearch, newNode1);

		session.getNewSearchButton().waitAndClick(20);
		int pureHit2 = session.advancedContentSearchWithSearchChanges(Input.searchString1, "Yes");
		session.saveSearchInNewNode(savedSearch2, newNode2);

		base.selectproject();
		int pureHit3 = session.conceptualSearch_new("field report", "mid");
		session.saveSearchInNewNode(savedSearch3, newNode3);
		System.out.println(pureHit3);

		session.getNewSearchButton().waitAndClick(20);
		session.getWorkproductBtnC().waitAndClick(30);
		session.selectRedactioninWPS(Input.defaultRedactionTag);
		pureHit4 = session.saveAndReturnPureHitCount();
		driver.scrollPageToTop();
		session.saveSearchInNewNode(savedSearch4, newNode4);
		System.out.println(pureHit4);

		// Landed on Saved Search
		saveSearch.navigateToSavedSearchPage();
		saveSearch.rootGroupExpansion();
		String searchID1 = saveSearch.shareSavedNodePA(Input.shareSearchDefaultSG, newNode1, true, true, savedSearch);

		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(3);
		saveSearch.rootGroupExpansion();
		String searchID2 = saveSearch.shareSavedNodePA(Input.shareSearchDefaultSG, newNode2, true, true, savedSearch2);

		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(3);
		saveSearch.rootGroupExpansion();
		String searchID3 = saveSearch.shareSavedNodePA(Input.shareSearchDefaultSG, newNode3, true, true, savedSearch3);

		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(3);
		saveSearch.rootGroupExpansion();
		String searchID4 = saveSearch.shareSavedNodePA(Input.shareSearchDefaultSG, newNode4, true, true, savedSearch4);

		String dataSet[][] = { { savedSearch, String.valueOf(pureHit1) }, { savedSearch2, String.valueOf(pureHit2) },
				{ savedSearch3, String.valueOf(pureHit3) }, { savedSearch4, String.valueOf(pureHit4) } };
		String nodeList[][] = { { newNode1 }, { newNode2 }, { newNode3 }, { newNode4 } };

		int k = 0;
		for (int i = 0; i < dataSet.length; i++) {

			int j = 0;
			int l = 0;
			String searches = dataSet[i][j];
			j++;
			pureHit0 = dataSet[i][j];
			String Nodes = nodeList[k][l];

			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.getSavedSearchGroupName(Input.shareSearchDefaultSG).waitAndClick(10);
			saveSearch.selectNode1(Nodes);
			// Schedule save search
			saveSearch.scheduleSavedSearchInMinute(searches, 2);

			// Make sure shared Node reflected in the SG
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.getSavedSearchGroupName(Input.shareSearchDefaultSG).waitAndClick(10);
			saveSearch.selectNode1(Nodes);
			saveSearch.savedSearch_SearchandSelect(searches, "Yes");

			// verify Document count After Execution
			String actualDocCount = saveSearch.getCountofDocs().getText();
			System.out.println(actualDocCount);
			softAssertion.assertEquals(pureHit0, actualDocCount);

			// Perform Bulk action
			driver.Navigate().refresh();
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.getSavedSearchGroupName(Input.shareSearchDefaultSG).waitAndClick(10);
			saveSearch.selectNode1(Nodes);
			saveSearch.savedSearch_SearchandSelect(searches, "Yes");
			saveSearch.saveSearchToDoclist();
			k++;
		}
		softAssertion.assertAll();
		login.logout();

	}

	/**
	 * @author Jeevitha Description: For RMU - Validate modifying search shared by
	 *         PAU/RMU to specific <Security Group Name> by both PAU and
	 *         RMU(RPMXCON-49892)
	 * @throws InterruptedException
	 * @S changes - Done - new imp
	 */
	@Test(description = "RPMXCON-49892", enabled = true, groups = { "regression" })
	public void validateModifyingSearch() throws InterruptedException {
		String search = "Search0" + Utility.dynamicNameAppender();

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case Id: RPMXCON-49892 - Saved Search Sprint 04");

		// search and save in Deafult SG
		int pureHit1 = session.basicContentSearch(Input.searchString1);
		session.saveSearchAtAnyRootGroup(search, Input.shareSearchDefaultSG);
		System.out.println("PureHit COunt of First Search is : " + pureHit1);
		base.stepInfo("PureHit COunt of First Search is : " + pureHit1);

		// Perform Edit function
		saveSearch.navigateToSSPage();
		saveSearch.getSavedSearchGroupName(Input.shareSearchDefaultSG).waitAndClick(10);
		saveSearch.savedSearch_SearchandSelect(search, "Yes");
		base.waitForElement(saveSearch.getSavedSearchEditButton());
		saveSearch.getSavedSearchEditButton().waitAndClick(5);

		// Modify
		session.modifySearchTextArea(1, Input.searchString1, Input.searchString2, "Save");
		base.stepInfo("Modified search query, & executed the same  ");
		driver.waitForPageToBeReady();
		int pureHit2 = session.searchAndReturnPureHit_BS();
		System.out.println("PureHit COunt of Modified Search is : " + pureHit2);
		base.stepInfo("PureHit COunt of Modified Search is : " + pureHit2);

		session.saveAsOverwrittenSearch(Input.shareSearchDefaultSG, search, "First", "Success", "", null);

		login.logout();
		// Login As RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Perform Edit function
		saveSearch.selectSavedSearchTAb(search, Input.shareSearchDefaultSG, "Yes");
		base.waitForElement(saveSearch.getSavedSearchEditButton());
		saveSearch.getSavedSearchEditButton().waitAndClick(5);

		// Modify Search And Overwrite
		session.modifySearchTextArea(1, Input.searchString2, Input.searchString5, "Save");
		base.stepInfo("Modified search query, & executed the same  ");
		driver.waitForPageToBeReady();
		int pureHit3 = session.searchAndReturnPureHit_BS();
		System.out.println("PureHit COunt of Modified Search is : " + pureHit3);
		base.stepInfo("PureHit COunt of Modified Search is : " + pureHit3);

		session.saveAsOverwrittenSearch(Input.shareSearchDefaultSG, search, "First", "Success", "", null);

		login.logout();

		// login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		saveSearch.selectSavedSearchTAb(search, Input.shareSearchDefaultSG, "Yes");

		// verify Document count
		String actualDocCount = saveSearch.getCountofDocs().getText();
		softAssertion.assertEquals(pureHit3, Integer.parseInt(actualDocCount));
		base.stepInfo("Verified Modified changes");

		login.logout();
		// Login As RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		saveSearch.selectSavedSearchTAb(search, Input.shareSearchDefaultSG, "Yes");

		// verify Document count
		String actualDocCount1 = saveSearch.getCountofDocs().getText();
		softAssertion.assertEquals(pureHit3, Integer.parseInt(actualDocCount1));
		base.stepInfo("Verified Modified changes");

		softAssertion.assertAll();
		login.logout();

		// login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		saveSearch.navigateToSSPage();
		base.stepInfo("Initiating delete search");
		saveSearch.deleteSearch(search, Input.shareSearchDefaultSG, "Yes");
		login.logout();

	}

	/**
	 * @author Jeevitha. Description:Validate saving executed search under My
	 *         Searches or Shared Searches(RPMXCON-49858)
	 * @throws InterruptedException
	 * @S changes - Done - new imp
	 */
	@Test(description = "RPMXCON-49858", enabled = true, groups = { "regression" })
	public void validateSavingExecutedSearch() throws InterruptedException {
		String search = "Search00" + Utility.dynamicNameAppender();
		String search1 = "Search01" + Utility.dynamicNameAppender();
		String search2 = "Search02" + Utility.dynamicNameAppender();
		String search3 = "Search03" + Utility.dynamicNameAppender();
		String search4 = "Search04" + Utility.dynamicNameAppender();

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case Id: RPMXCON-49858 - Saved Search Sprint 04");

		// search and save in Project Admin AS Completed query
		int pureHit1 = session.basicContentSearch(Input.searchString1);
		session.saveSearchAtAnyRootGroup(search, Input.shareSearchPA);

		// search and save in Project Admin AS Draft query
		session.getNewSearchButton().waitAndClick(20);
		session.basicContentSearchWithSaveChanges(Input.searchString5, "No", "Third");
		session.saveSearchAtAnyRootGroup(search3, Input.shareSearchPA);

		// Perform Edit function
		saveSearch.selectSavedSearchTAb(search, Input.shareSearchPA, "Yes");

		String firstsearch = saveSearch.getCountofDocs().getText();
		String docID1 = saveSearch.getSelectSearchWithID(search).getText();
		base.waitForElement(saveSearch.getSavedSearchEditButton());
		saveSearch.getSavedSearchEditButton().waitAndClick(5);

		// Execute ANd Overwrite
		// Modify Search And Overwrite
		session.modifySearchTextArea(1, Input.searchString1, Input.searchString6, "Save");
		base.stepInfo("Modified search query, & executed the same  ");
		driver.waitForPageToBeReady();

		int pureHit2 = session.searchAndReturnPureHit_BS();
		System.out.println("PureHit COunt of Modified Search is : " + pureHit2);
		base.stepInfo("PureHit COunt of Modified Search is : " + pureHit2);

		session.saveAsOverwrittenSearch(Input.shareSearchPA, search, "first", "Success", "", null);

		session.saveSearchInNewNode(search1, null);
		session.saveSearchAtAnyRootGroup(search2, Input.shareSearchPA);

		// Perform Edit function
		saveSearch.selectSavedSearchTAb(search, Input.shareSearchPA, "Yes");

		// verify overwritten Search Changes IS refelected
		String searchOverWritten = saveSearch.getCountofDocs().getText();
		softAssertion.assertNotSame(firstsearch, searchOverWritten);
		System.out.println("before Changes :" + firstsearch + "  After Changes : " + searchOverWritten);
		base.stepInfo("before Changes :" + firstsearch + "  After Changes : " + searchOverWritten);

		// verify Docid
		saveSearch.selectSavedSearchTAb(search1, Input.mySavedSearch, "Yes");
		String docID2 = saveSearch.getSelectSearchWithID(search1).getText();
		softAssertion.assertNotSame(docID1, docID2);
		System.out.println("Search has different DocID");
		base.stepInfo(docID1 + " : First Search" + docID2 + ": modifed search");

		// verify Docid
		driver.Navigate().refresh();
		saveSearch.selectSavedSearchTAb(search2, Input.shareSearchPA, "Yes");
		String docID3 = saveSearch.getSelectSearchWithID(search2).getText();
		softAssertion.assertNotSame(docID1, docID3);
		System.out.println("Search has different DocID");
		base.stepInfo(docID1 + " : First Search" + docID3 + ": modifed search");

		driver.Navigate().refresh();
		saveSearch.selectSavedSearchTAb(search3, Input.shareSearchPA, "Yes");
		base.waitForElement(saveSearch.getSavedSearchEditButton());
		saveSearch.getSavedSearchEditButton().waitAndClick(5);

		session.saveAsOverwrittenSearch(Input.shareSearchPA, search3, "First", "Success", "", null);
		session.saveSearchAtAnyRootGroup(search4, Input.shareSearchPA);

		softAssertion.assertAll();
		login.logout();

	}

	/**
	 * @author Jeevitha. Description:For RMU - Validate executing searches/groups
	 *         from the shared with <Security Group Name> by any other PAU
	 *         user(RPMXCON-49887)
	 * @throws InterruptedException S changes - Done - new imp
	 */
	@Test(description = "RPMXCON-49887", enabled = true, groups = { "regression" })
	public void validateSavingExecutedSearch1() throws InterruptedException {
		int noOfNodesToCreate = 3;
		int selectIndex = 0;
		String SGtoShare = Input.shareSearchDefaultSG;
		String node;
		Boolean inputValue = true;
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		HashMap<String, String> searchGroupSearchpIDpair = new HashMap<>();

		// Login as PA
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Test case Id: RPMXCON-49887 - Saved Search Sprint 04");

		// Landed on Saved Search
		saveSearch.navigateToSSPage();
		// Multiple Node Creation
		newNodeList = saveSearch.createSGAndReturn("PA", "No", noOfNodesToCreate);
		System.out.println("Node creation is done followed by adding searches to the created nodes");
		base.stepInfo("Node creation is done followed by adding searches to the created nodes");

		// Adding searches to the created nodes
		session.navigateToSessionSearchPageURL();
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);
		saveSearch.sortedMapList(nodeSearchpair);
		// Search ID collection
		saveSearch.navigateToSSPage();
		saveSearch.rootGroupExpansion();

		searchGroupSearchpIDpair = saveSearch.collectionOfSearchIdsFromNodeCollections(newNodeList, nodeSearchpair,
				searchGroupSearchpIDpair);

		saveSearch.navigateToSSPage();
		node = saveSearch.childNodeSelectionToShare(selectIndex, newNodeList);
		System.out.println("Final : " + node);
		saveSearch.shareSavedNodePA(SGtoShare, node, false, true, nodeSearchpair.get(node));

		// Execute search group
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		saveSearch.getSavedSearchGroupName(SGtoShare).waitAndClick(10);
		driver.waitForPageToBeReady();
		node = saveSearch.childNodeSelectionToShare(selectIndex, newNodeList);
		String pureHit = saveSearch.savedSearchExecute_SearchGRoup(nodeSearchpair.get(node), 0);

		// Execute Search in search group
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		saveSearch.getSavedSearchGroupName(SGtoShare).waitAndClick(10);
		driver.waitForPageToBeReady();
		node = saveSearch.childNodeSelectionToShare(selectIndex, newNodeList);
		saveSearch.savedSearch_SearchandSelect(nodeSearchpair.get(node), "Yes");
		System.out.println(nodeSearchpair.get(node));
		String pureHit1 = saveSearch.savedSearchExecute_Draft(nodeSearchpair.get(node), 0);

		login.logout();

		// login as other rmu
		login.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		saveSearch.navigateToSavedSearchPage();
		saveSearch.getSavedSearchGroupName(SGtoShare).waitAndClick(10);
		driver.waitForPageToBeReady();
		node = saveSearch.childNodeSelectionToShare(selectIndex, newNodeList);

		// verify doc count
		base.waitForElement(saveSearch.getCountofDocs());
		String docCount = saveSearch.getCountofDocs().getText();
		System.out.println("NO Of Docs Executed :" + docCount);
		base.stepInfo("NO Of Docs Executed :" + docCount);
		softAssertion.assertEquals(pureHit1, docCount);
		softAssertion.assertAll();
		login.logout();

		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("INitiating Delete nOde");
		saveSearch.deleteNode(Input.mySavedSearch, newNodeList.get(0));
		login.logout();
	}

	/**
	 * @author Raghuram A Date: 10/19/21 Modified date:N/A Modified by: Description
	 *         : For RMU - Validate sharing search to specific <Security Group Name>
	 *         by both PAU and RMU -RPMXCON-49893 Sprint 04 @ S changes - Done - new
	 *         imp
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-49893", enabled = true, dataProvider = "SavedSearchwithPAandRMUwithRole", groups = {
			"regression" })
	public void shareSearchSS(String userName, String password, String fullName, String role, String verifyUserName,
			String verifyPassword) throws InterruptedException, ParseException {
		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();
		String SGtoShare = Input.shareSearchDefaultSG;

		base.stepInfo("Test case Id: RPMXCON-49893 - Saved Search Sprint 03");

		login.loginToSightLine(userName, password);
		base.stepInfo("Loggedin As : " + fullName);

		session.basicContentSearch(Input.searchString2);
		session.saveSearch(searchName);

		saveSearch.shareSearchFlow(searchName, Input.securityGroup, role);
		login.logout();

		login.loginToSightLine(verifyUserName, verifyPassword);
		saveSearch.navigateToSSPage();

		saveSearch.verifySharedGroupSearch(SGtoShare, searchName);
		login.logout();

		login.loginToSightLine(userName, password);
		base.stepInfo("Loggedin As : " + fullName);
		base.stepInfo("Initiating Delete Search");
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		saveSearch.deleteSearch(searchName, Input.securityGroup, "Yes");

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 10/19/21 Modified date:N/A Modified by: Description
	 *         : Verify that application displays all documents that are in the
	 *         aggregate results set of "Shared With Default Security Group" and
	 *         when User Performs Bulk Assign from Search groups -RPMXCON-49008
	 *         Sprint 04 S changes - Done - new imp
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-49008", enabled = true, groups = { "regression" })
	public void aggregateResultWhileBulkAssignmentWithSecurityGrp() throws InterruptedException, ParseException {
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		HashMap<String, String> searchGroupSearchpIDpair = new HashMap<>();
		Boolean inputValue = true;
		int finalCountresult, noOfNode = 2, purehitCount, selectIndex = 0, latencyCheckTime = 5;
		String finalCount, node;
		String assignName = "assignName" + Utility.dynamicNameAppender();
		String SGtoShare = Input.shareSearchDefaultSG;
		String passMessage = "Application not hang or shows latency more than " + latencyCheckTime + " seconds.";
		String failureMsg = "Continues Loading more than " + latencyCheckTime + " seconds.";

		base.stepInfo("Test case Id: RPMXCON-49008 - Saved Search Sprint 04");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Navigate on Saved Search & Multiple Node Creation & save search in node
		saveSearch.navigateToSSPage();
		newNodeList = saveSearch.createSGAndReturn("PA", "No", noOfNode);
		System.out.println("Next adding searches to the created nodes");
		base.stepInfo("Next adding searches to the created nodes");
		driver.getWebDriver().get(Input.url + "Search/Searches");
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);

		// Search ID collection
		saveSearch.navigateToSSPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(5);
		saveSearch.rootGroupExpansion();

		searchGroupSearchpIDpair = saveSearch.collectionOfSearchIdsFromNodeCollections(newNodeList, nodeSearchpair,
				searchGroupSearchpIDpair);

		saveSearch.navigateToSSPage();
		node = saveSearch.childNodeSelectionToShare(selectIndex, newNodeList);
		System.out.println("Final : " + node);
		saveSearch.shareSavedNodePA(SGtoShare, node, false, true, nodeSearchpair.get(node));

		base.stepInfo("-------Pre-requesties completed--------");
		login.logout();

		// Login as PA
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// Launch DocVia via Saved Search
		saveSearch.navigateToSSPage();
		base.stepInfo("Root node selected : " + newNodeList.get(0));
		saveSearch.getSavedSearchGroupName(SGtoShare).waitAndClick(10);
		saveSearch.selectNode1(newNodeList.get(0));
		saveSearch.getSavedSearchToBulkAssign().waitAndClick(5);
		base.stepInfo("Clicked Assign Icon");

		// Load latency Verification SaveSearchToBulkFolder
		Element loadingElement = saveSearch.getbulkAssignTotalCountLoad();
		saveSearch.loadingCountVerify(loadingElement, latencyCheckTime, passMessage, failureMsg);
		finalCount = assign.assignDocstoNewAssgn(assignName);
		finalCountresult = Integer.parseInt(finalCount);
		base.stepInfo("Finalize count : " + finalCountresult);
		assign.quickAssignCreation(assignName, Input.codeFormName);
		session.switchToWorkproduct();
		purehitCount = session.selectAssignmentInWPSWs(assignName);
		base.stepInfo("PureHitcount via WP assignment selection : " + purehitCount);

		if (finalCountresult == purehitCount) {
			base.passedStep(
					"After the Bulk Assignment - Pure hit appears like aggregate results set of all child search groups and searches   ");
		} else {
			base.failedStep("Count Mismatches");
		}

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 10/19/21 Modified date:N/A Modified by: Description
	 *         : Verify that application displays all documents that are in the
	 *         aggregate results set of "Shared With Project Administrator/Shared
	 *         With Default Security Group" and User performs Refresh Search groups
	 *         (RPMXCON-49009 Sprint-04)
	 * @throws InterruptedException
	 * @throws ParseException
	 */

	@Test(description = "RPMXCON-49009", enabled = true, dataProvider = "SavedSearchwithPAandRMUwithRoleANDshareType", groups = {
			"regression" })
	public void showHideFieldsVIaSHared(String userName, String password, String fullName, String role, int type)
			throws InterruptedException, ParseException {
		String SGtoShare = Input.shareSearchDefaultSG;

		base.stepInfo("Test case Id: RPMXCON-49009 - Saved Search Sprint 04");

		login.loginToSightLine(userName, password);
		base.stepInfo("Loggedin As : " + fullName);
		// Navigate on Saved Search & Multiple Node Creation & save search in node
		saveSearch.navigateToSSPage();

		saveSearch.headerVerification(type, SGtoShare, role);

		login.logout();
	}

	/**
	 * @author Steffy Description:User Impersonation - Validate RMU Sharing saved
	 *         search after impersonating down as Reviewer RPMXCON-49919 Sprint 05
	 */

	@Test(description = "RPMXCON-49919", enabled = true, groups = { "regression" })
	public void validateRMUSharingSavedSearchImpersonateAsReviewer() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-49919 - Saved Search Sprint 05");
		base.stepInfo("Validate RMU Sharing saved search after impersonating down as Reviewer");

		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();
		String mySavedSearch = Input.mySavedSearch;

		// Login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("User successfully logged into slightline webpage as RMU with" + Input.rmu1userName);

		// Searching documents based on search string and saving the search
		base.stepInfo("Searching documents based on search string and saving the search");
		session.basicContentSearch(Input.searchString2);
		session.saveSearch(searchName);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");

		// Share Search via Saved Search
		base.stepInfo("Sharing the saved search with security group");
		saveSearch.shareSavedSearchRMU(searchName, Input.securityGroup);

		// Impersonate RMU to Reviewer
		base.stepInfo("Impersonating RMU user as Reviewer");
		base.impersonateRMUtoReviewer();

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");

		base.stepInfo("Verifying whether the search saved by RMU role is available in My saved search");
		saveSearch.verifySharedGroupSearch(mySavedSearch, searchName);

		base.stepInfo("Verifying whether the shared saved search is available in security group");
		saveSearch.verifySharedGroupSearch(Input.securityGroup, searchName);

		login.logout();

	}

	/**
	 * @author Steffy Description:User Impersonation - Validate PAU Sharing saved
	 *         search after impersonating down as RMU/Reviewer RPMXCON-49899 Sprint
	 *         5 @Stabilization - To Do
	 * 
	 */

	@Test(description = "RPMXCON-49899", enabled = true, groups = { "regression" })
	public void validatePASharingSavedSearchImpersonateAsRMUAndReviewer() throws Exception {

		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		String SGtoShare = Input.shareSearchDefaultSG;
		String PAtoShare = Input.shareSearchPA;
		Boolean inputValue = true;
		String mySavedSearch = Input.mySavedSearch;

		base.stepInfo("Test case Id: RPMXCON-49899 - Saved Search Sprint 05");
		base.stepInfo(
				"User Impersonation - Validate PAU Sharing saved search after impersonating down as RMU/Reviewer");

		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();
		String rmuSearch = "RMU Search Name" + UtilityLog.dynamicNameAppender();
		String reviewerSearch = "Reviewer Search Name" + UtilityLog.dynamicNameAppender();

		// Login as RMU
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("User successfully logged into slightline webpage as PA with" + Input.pa2userName);

		// Searching documents based on search string and saving the search

		base.stepInfo("Searching documents based on search string and saving the search");
		session.basicContentSearch(Input.searchString2);
		session.saveSearch(searchName);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();

		// Share Search via Saved Search
		base.stepInfo("Sharing the saved search with PA and with security group");
		saveSearch.shareSavedSearchAsPA(searchName, Input.securityGroup);

		// Impersonate PA User to RMU base.stepInfo("Impersonating PA user as RMU");
		base.impersonatePAtoRMU();

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();

		base.stepInfo("Verifying whether the search saved by PA role is not available for RMU user");
		try {
			if (saveSearch.getSGTab(PAtoShare).isElementAvailable(3)) {
				base.failedStep("Shared with project adminitrator security group is available for RMU user");
			} else {
				base.passedStep("Shared with project adminitrator security group is not available for RMU user");
			}
		} catch (Exception e) {
			base.passedStep("Shared with project adminitrator security group is not available for RMU user");
			UtilityLog.info("Shared with project adminitrator security group is not available for RMU user");
		}
		saveSearch.verifySharedGroupSearch1(mySavedSearch, searchName, false);

		base.stepInfo("Verifying whether the shared saved search is available in security group");
		saveSearch.verifySharedGroupSearch(Input.securityGroup, searchName);

		// Searching documents based on search string and saving the search base.
		base.stepInfo("Searching documents based on search string and saving the search");
		session.basicContentSearch(Input.searchString2);
		session.saveSearch(rmuSearch);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();

		// Share Search via Saved Search
		base.stepInfo("Sharing the saved search with security group");
		saveSearch.shareSavedSearchRMU(rmuSearch, Input.securityGroup);

		// Impersonate RMU to Reviewer
		base.stepInfo("Impersonating RMU user as Reviewer");
		base.impersonateRMUtoReviewer();

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();

		base.stepInfo("Verifying whether the search saved by PA role is not available for Reviewer user");
		try {
			if (saveSearch.getSGTab(PAtoShare).isElementAvailable(3)) {
				base.failedStep("Shared with project adminitrator security group is available for Reviewer user");
			}
		} catch (Exception e) {
			base.passedStep("Shared with project adminitrator security group is not available for Reviewer user");
			UtilityLog.info("Shared with project adminitrator security group is not available for Reviewer user");
		}
		saveSearch.verifySharedGroupSearch1(mySavedSearch, searchName, false);

		base.stepInfo("Verifying whether the search saved by RMU role is available in My saved search");
		saveSearch.verifySharedGroupSearch(mySavedSearch, rmuSearch);

		base.stepInfo("Verifying whether the shared saved search is available in security group");
		saveSearch.verifySharedGroupSearch(Input.securityGroup, rmuSearch);

		// Searching documents based on search string and saving the search base.
		base.stepInfo("Searching documents based on search string and saving the search");
		session.basicContentSearch(Input.searchString2);
		session.saveSearch(reviewerSearch);

		// Impersonate Reviewer to RMU
		base.stepInfo("Impersonating Reviewer user as RMU");
		base.impersonateReviewertoRMU();

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();

		base.stepInfo("Verifying whether the search saved by PA role is not available for RMU user");
		try {
			if (saveSearch.getSGTab(PAtoShare).isElementAvailable(3)) {
				base.failedStep("Shared with project adminitrator security group is available for RMU user");
			} else {
				base.passedStep("Shared with project adminitrator security group is not available for RMU user");
			}
		} catch (Exception e) {
			base.passedStep("Shared with project adminitrator security group is not available for RMU user");
			UtilityLog.info("Shared with project adminitrator security group is not available for RMU user");
		}
		saveSearch.verifySharedGroupSearch1(mySavedSearch, searchName, false);

		base.stepInfo("Verifying whether the search saved by RMU role is available in My saved search");
		saveSearch.verifySharedGroupSearch(mySavedSearch, rmuSearch);

		base.stepInfo("Verifying whether the search saved by Reviewer role is available in My saved search");
		saveSearch.verifySharedGroupSearch(mySavedSearch, reviewerSearch);

		// Impersonate RMU to PA base.stepInfo("Impersonating RMU user as PA");
		base.impersonateSAtoPA();

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();

		base.stepInfo("Verifying whether the search saved by PA role is available for PA user");
		saveSearch.verifySharedGroupSearch1(mySavedSearch, searchName, true);

		base.stepInfo("Verifying whether the search saved by RMU role is not available in My saved search");
		saveSearch.verifySharedGroupSearch1(mySavedSearch, rmuSearch, false);

		base.stepInfo("Verifying whether the search saved by Reviewer role is not available in My saved search");
		saveSearch.verifySharedGroupSearch1(mySavedSearch, reviewerSearch, false);

		// Landed on Saved Search
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		// Multiple Node Creation
		newNodeList = saveSearch.createSGAndReturn("PA", "No", 1);
		System.out.println("Node creation is done followed by adding searches to the created nodes");
		base.stepInfo("Node creation is done followed by adding searches to the created nodes");

		// Adding searches to the created nodes
		driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.waitForPageToBeReady();
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);

		base.stepInfo("-------Pre-requesties completed--------");

		// Search ID collection
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		base.waitForElement(saveSearch.getSavedSearchNewGroupExpand());
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		saveSearch.shareSavedNodeWithDesiredGroup(newNodeList.get(0), SGtoShare);
		saveSearch.shareSavedNodeWithDesiredGroup(newNodeList.get(0), PAtoShare);
		String PANode = newNodeList.get(0);

		// Impersonate PA User to RMU
		base.stepInfo("Impersonating PA user as RMU");
		base.impersonatePAtoRMU();

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		base.stepInfo("Verifying whether the search saved by PA role is not available for RMU user");
		try {
			if (saveSearch.getSGTab(PAtoShare).isElementAvailable(3)) {
				base.failedStep("Shared with project adminitrator security group is available for RMU user");
			} else {
				base.passedStep("Shared with project adminitrator security group is not available for RMU user");
			}
		} catch (Exception e) {
			base.passedStep("Shared with project adminitrator security group is not available for RMU user");
			UtilityLog.info("Shared with project adminitrator security group is not available for RMU user");
		}

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		String PANodeSearchName = nodeSearchpair.get(newNodeList.get(0));
		saveSearch.verifySharedGroupSearch1(mySavedSearch, PANodeSearchName, false);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		base.stepInfo("Verifying whether the shared saved search is available in security group");
		saveSearch.getCollapsedSharedWithDefaultSecurityGroup().waitAndClick(5); // added my sampath
		saveSearch.verifySharedNode(newNodeList.get(0));
		saveSearch.verifySharedGroupSearch1(newNodeList.get(0), nodeSearchpair.get(newNodeList.get(0)), true);

		// Landed on Saved Search
//		base.selectproject();
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(5); // added my sampath
		// Multiple Node Creation
		newNodeList = saveSearch.createSGAndReturn("PA", "No", 1);
		System.out.println("Node creation is done followed by adding searches to the created nodes");
		base.stepInfo("Node creation is done followed by adding searches to the created nodes");

		// Adding searches to the created nodes
		driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.waitForPageToBeReady();
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);

		base.stepInfo("-------Pre-requesties completed--------");

		// Search ID collection
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		base.waitForElement(saveSearch.getSavedSearchNewGroupExpand());
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		saveSearch.shareSavedNodeWithDesiredGroup(newNodeList.get(0), SGtoShare);
		String RMUNode = newNodeList.get(0);

		// Impersonate RMU User to Reviewer
		base.stepInfo("Impersonating RMU user as Reviewer");
		base.impersonateRMUtoReviewer();

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		base.stepInfo("Verifying whether the search saved by PA role is not available for Reviewer user");
		try {
			if (saveSearch.getSGTab(PAtoShare).isElementAvailable(3)) {
				base.failedStep("Shared with project adminitrator security group is available for Reviewer user");
			} else {
				base.passedStep("Shared with project adminitrator security group is not available for Reviewer user");
			}
		} catch (Exception e) {
			base.passedStep("Shared with project adminitrator security group is not available for Reviewer user");
			UtilityLog.info("Shared with project adminitrator security group is not available for Reviewer user");
		}

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		saveSearch.verifySharedGroupSearch1(mySavedSearch, PANodeSearchName, false);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		base.stepInfo("Verifying whether the shared saved search is available in security group");
		saveSearch.getCollapsedSharedWithDefaultSecurityGroup().waitAndClick(5); // added my sampath
		saveSearch.verifySharedNode(PANode);
		saveSearch.verifySharedGroupSearch1(PANode, PANodeSearchName, true);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		base.stepInfo("Verifying whether the shared saved search by RMU is available in security group");
		saveSearch.verifySharedNode(RMUNode);
		saveSearch.verifySharedGroupSearch1(RMUNode, nodeSearchpair.get(RMUNode), true);
		String RMUNodeSearchName = nodeSearchpair.get(RMUNode);
		// Landed on Saved Search
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(5); // added my sampath
		// Multiple Node Creation
		newNodeList = saveSearch.createSGAndReturn("PA", "No", 1);
		System.out.println("Node creation is done followed by adding searches to the created nodes");
		base.stepInfo("Node creation is done followed by adding searches to the created nodes");

		// Adding searches to the created nodes
		driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.waitForPageToBeReady();
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);

		// Search ID collection
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		base.waitForElement(saveSearch.getSavedSearchNewGroupExpand());
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		saveSearch.shareSavedNodeWithDesiredGroup(newNodeList.get(0), SGtoShare);
		String reviewerNode = newNodeList.get(0);

		// Impersonate PA User to RMU
		base.stepInfo("Impersonating Reviewer user as RMU");
		driver.scrollPageToTop();
		base.impersonateReviewertoRMU();

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		saveSearch.verifySharedGroupSearch1(mySavedSearch, PANodeSearchName, false);
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		saveSearch.selectNode1(RMUNode);
		driver.waitForPageToBeReady();
		saveSearch.verifySharedGroupSearch1(RMUNode, RMUNodeSearchName, true);
		saveSearch.selectNode1(reviewerNode);
		saveSearch.verifySharedGroupSearch1(reviewerNode, nodeSearchpair.get(reviewerNode), true);
		String reviewerSearchName = nodeSearchpair.get(reviewerNode);
		// Impersonate PA User to RMU
		base.stepInfo("Impersonating SA user as PA");
		base.impersonateSAtoPA();

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		saveSearch.selectNode1(PANode);
		saveSearch.verifySharedGroupSearch1(PANode, PANodeSearchName, true);
		saveSearch.verifySharedGroupSearch1(mySavedSearch, reviewerSearchName, false);
		saveSearch.verifySharedGroupSearch1(mySavedSearch, RMUNodeSearchName, false);

		login.logout();

	}

	/**
	 * @author Mohan A Date: 10/18/21 Modified date:N/A Modified by: Description :
	 *         For RMU - Validate saving edited search (executed or draft) under My
	 *         Searches or Shared Searches - RPMXCON-49891 Sprint 04
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-49891", enabled = true, groups = { "regression" })
	public void validateSavingEditedSearch() throws InterruptedException, AWTException {

		base.stepInfo("Test case Id: RPMXCON-49891");
		// Login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// Search
		String searchName1 = "Search Name1" + UtilityLog.dynamicNameAppender();
		String searchName2 = "Search Name2" + UtilityLog.dynamicNameAppender();
		String grpName1 = Input.shareSearchDefaultSG;
		String grpName2 = Input.mySavedSearch;

		session.basicContentSearch(Input.searchString1);
		session.saveSearchAtAnyRootGroup(searchName1, grpName1);

		// Perform Edit function
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.getSavedSearchGroupName(grpName1).waitAndClick(10);
		saveSearch.savedSearch_SearchandSelect(searchName1, "Yes");
		String getSearchID = saveSearch.GetSearchID(searchName1);
		base.waitForElement(saveSearch.getSavedSearchEditButton());
		saveSearch.getSavedSearchEditButton().waitAndClick(5);
		// Modify Search via Saved Search
		session.modifySearchTextArea(1, Input.searchString1, Input.searchString2, "Save");
		base.stepInfo("Modified search query, & executed the same  ");

		session.searchAndReturnPureHit_BS();
		session.saveAsOverwrittenSearch(grpName1, searchName1, "First", "Success", "", null);

		session.saveSearchInNewNode(searchName1, null);

		session.saveSearchAtAnyRootGroup(searchName2, grpName1);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.savedSearch_SearchandSelect(searchName1, "Yes");
		String getSearchID1 = saveSearch.GetSearchID(searchName1);

		saveSearch.verifySearchAndSearchId(grpName1, getSearchID, true, searchName1, true);
		saveSearch.verifySearchAndSearchId(grpName2, getSearchID1, true, searchName1, true);

		saveSearch.getSavedSearchGroupName(grpName1).waitAndClick(10);
		saveSearch.savedSearch_SearchandSelect(searchName2, "Yes");
		String getSearchID2 = saveSearch.GetSearchID(searchName2);

		saveSearch.verifySearchAndSearchId(grpName1, getSearchID2, true, searchName2, true);

		login.logout();

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.verifySearchAndSearchId(grpName1, getSearchID, true, searchName1, true);

		login.logout();

	}

	/**
	 * @author Mohan Date: 10/20/21 Modified date:N/A Modified by: Description : RMU
	 *         - Validate sharing specific search/group to Security Group that are
	 *         already shared -RPMXCON-49884 Sprint 04
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-49884", enabled = true, groups = { "regression" })
	public void validateSpecificSearchToSecurityGroup() throws InterruptedException, ParseException {
		int noOfNodesToCreate = 5;
		int selectIndex = 2;
		String SGtoShare = Input.shareSearchDefaultSG;
		String node;
		Boolean inputValue = true;
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		HashMap<String, String> searchGroupSearchpIDpair = new HashMap<>();

		base.stepInfo("Test case Id: RPMXCON-49884");
		base.stepInfo(" RMU - Validate sharing specific search/group to Security Group that are already shared");

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

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		node = saveSearch.childNodeSelectionToShare(selectIndex, newNodeList);
		System.out.println("Final : " + node);
		saveSearch.shareSavedNodePA(SGtoShare, node, false, true, nodeSearchpair.get(node));

		base.stepInfo("-------Pre-Condition completed--------");

		login.logout();

		// Login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		saveSearch.verifyImpactinSharedchildNodes(SGtoShare,node, newNodeList, selectIndex, nodeSearchpair,
				searchGroupSearchpIDpair);

		login.logout();
	}

	/**
	 * @author Jeevitha Description: Verify that error message against execution for
	 *         search group, where at least one search is referring to at least one
	 *         other searches in the same search group(RPMXCON-48582)
	 * @param username
	 * @param password
	 * @param fullName
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-48582", enabled = true, dataProvider = "SavedSearchwithUsers", groups = {
			"regression" })
	public void basicAdvances_WorkProduct(String username, String password, String fullName)
			throws InterruptedException {
		String SearchName = "test" + Utility.dynamicNameAppender();
		String search_name = "Test" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-48582 - Saved Search Sprint 04");
		// Login as User
		login.loginToSightLine(username, password);
		base.stepInfo("Loggedin As : " + fullName);

		// create new searchgroup
		saveSearch.createNewSearchGrp(SearchName);
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		String newNode = saveSearch.getSavedSearchNewNode().getText();
		session.basicContentSearch(Input.searchString1);
		session.saveSearchInNewNode(SearchName, newNode);//savedSearchWPNodeExpansion
		session.advanceWorkProduct(newNode,SearchName, search_name);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		base.stepInfo("Landed on SavedSearchPage");
		saveSearch.selectNode1(newNode);
		saveSearch.savedSearch_SearchandSelect(SearchName, "No");
		driver.waitForPageToBeReady();
		saveSearch.verifyexecuteErrorMessage(search_name);

		login.logout();
	}

	/**
	 * @author Raghuram A Description: Verify on selecting saved search with
	 *         Completed status and zero doc count and Doc View option warning
	 *         message should be displayed(RPMXCON-48614)
	 * @param username
	 * @param password
	 * @param fullName
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-48614", enabled = true, dataProvider = "SavedSearchwithUsers", groups = {
			"regression" })
	public void Basic_docViewAction(String username, String password, String fullName) throws InterruptedException {
		String SearchName = "test" + Utility.dynamicNameAppender();
		String text = Input.zeroPureHitString;

		base.stepInfo("Test case Id: RPMXCON-48614 - Saved Search Sprint 04");
		// Login as User
		login.loginToSightLine(username, password);
		base.stepInfo("Loggedin As : " + fullName);

		// create new searchgroup and save Search
		String newNode = saveSearch.createSearchGroupAndReturn(searchName1, username, "No");
		int pureHit = session.basicContentSearch(text);
		base.stepInfo("PureHit Count : " + pureHit);
		session.saveSearchInNewNode(SearchName, newNode);

		saveSearch.navigateToSSPage();
		base.stepInfo("Landed on SavedSearchPage");
		saveSearch.selectNode1(newNode);
		saveSearch.savedSearch_SearchandSelect(SearchName, "Yes");
		saveSearch.verifyWarning_messageForDocView(pureHit);

		login.logout();

	}

	/**
	 * @author Jeevitha Description : Validate Reviewer user is allowed to move
	 *         SearchGroups/Searches only within MySearches (RPMXCON-49947)
	 * @throws InterruptedException - new imp - done
	 */
	@Test(description = "RPMXCON-49947", enabled = true, groups = { "regression" })
	public void verifyMoveActionAsRev() throws InterruptedException {
		String searchName = "SavedSearch" + Utility.dynamicNameAppender();
		String search = "Search02" + Utility.dynamicNameAppender();

		// Login as PA
		login.loginToSightLine(Input.rev1userName, Input.rev1password);
		base.stepInfo("RPMXCON-49947 Saved Search");

		base.stepInfo("Node (or) New Search Group creation");
		String newNode = saveSearch.createSearchGroupAndReturn(searchName, "PA", "Yes");
		String newNode2 = saveSearch.createSearchGroupAndReturn(searchName, "PA", "Yes");

		base.stepInfo("Search and saveSearch in the created node");
		session.basicContentSearch(Input.searchString2);
		session.saveSearchInNewNode(searchName, newNode);
		session.saveSearchInNewNode(search, null);
		driver.waitForPageToBeReady();

		saveSearch.navigateToSSPage();
		base.stepInfo("Landed on SavedSearchPage");

		saveSearch.selectNode1(newNode);

		saveSearch.moveSearchToAnotherFolder(newNode2);
		saveSearch.moveActionButton("Save");
		base.stepInfo("Moved : " + newNode + " to " + newNode2);

		saveSearch.selectSavedSearchTAb(search, Input.mySavedSearch, "Yes");

		saveSearch.moveSearchToanotherGroup(newNode2, search);
		driver.getWebDriver().navigate().refresh();
		driver.waitForPageToBeReady();

		// Verify moved sub-folder is present in the selected folder
		saveSearch.selectNode1(newNode2);
		saveSearch.rootGroupExpansion();
		saveSearch.verifyNodePresent(newNode);

		base.stepInfo("Initiating Delete Search");
		saveSearch.deleteNode(Input.mySavedSearch, newNode2);

		login.logout();

	}

	/**
	 * @author Jeevitha Description : Validate RMU user is allowed to move
	 *         SearchGroups/Searches only within MySearches (RPMXCON-49946)
	 * @throws InterruptedException
	 * 
	 */
	@Test(description = "RPMXCON-49946", enabled = true, groups = { "regression" })
	public void verifyMoveActionAsRMU() throws InterruptedException {
		String searchName = "SavedSearch" + Utility.dynamicNameAppender();
		String search = "Search02" + Utility.dynamicNameAppender();

		// Login as PA
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("RPMXCON-49946 Saved Search");

		base.stepInfo("Node (or) New Search Group creation");
		String newNode = saveSearch.createSearchGroupAndReturn(searchName, "RMU", "Yes");
		String newNode2 = saveSearch.createSearchGroupAndReturn(searchName, "RMU", "Yes");

		base.stepInfo("Search and saveSearch in the created node");
		session.basicContentSearch(Input.searchString2);
		session.saveSearchInNewNode(searchName, newNode);
		session.saveSearchInNewNode(search, null);
		driver.waitForPageToBeReady();

		saveSearch.navigateToSSPage();
		base.stepInfo("Landed on SavedSearchPage");

		saveSearch.selectNode1(newNode);

		saveSearch.moveSearchToAnotherFolder(newNode2);
		saveSearch.moveActionButton("Save");
		base.stepInfo("Moved : " + newNode + " to " + newNode2);

		saveSearch.selectSavedSearchTAb(search, Input.mySavedSearch, "Yes");

		saveSearch.moveSearchToanotherGroup(newNode2, search);
		driver.getWebDriver().navigate().refresh();
		driver.waitForPageToBeReady();

		// Verify moved sub-folder is present in the selected folder
		saveSearch.selectNode1(newNode2);
		saveSearch.rootGroupExpansion();
		saveSearch.verifyNodePresent(newNode);

		base.impersonateRMUtoReviewer();
		String newNode3 = saveSearch.createSearchGroupAndReturn(searchName, "REV", "Yes");
		String newNode4 = saveSearch.createSearchGroupAndReturn(searchName, "REV", "Yes");

		session.basicContentSearch(Input.searchString2);
		session.saveSearchInNewNode(searchName, newNode3);
		session.saveSearchInNewNode(search, null);
		driver.waitForPageToBeReady();

		saveSearch.navigateToSSPage();
		base.stepInfo("Landed on SavedSearchPage");

		saveSearch.selectNode1(newNode3);
		base.stepInfo(" Renamed an existing saved search on Saved Search Screen using PA user");

		saveSearch.moveSearchToAnotherFolder(newNode4);
		saveSearch.moveActionButton("Save");
		base.stepInfo("Moved : " + newNode3 + " to " + newNode4);

		saveSearch.selectSavedSearchTAb(search, Input.mySavedSearch, "Yes");

		saveSearch.moveSearchToanotherGroup(newNode4, search);
		driver.getWebDriver().navigate().refresh();

		// Verify moved sub-folder is present in the selected folder
		saveSearch.selectNode1(newNode4);
		saveSearch.rootGroupExpansion();
		saveSearch.verifyNodePresent(newNode3);

		login.logout();

	}

	/**
	 * @author Mohan Date: 10/20/21 Modified date:N/A Modified by: N/A Description :
	 *         Verify that In Saved Search Page proper options are available when
	 *         user selects "My Saved Search" Groups -RPMXCON-49037 Sprint 05
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-49037", enabled = true, groups = { "regression" })
	public void validateSavedSearchPageProperOptions() throws InterruptedException, ParseException {
		int noOfNodesToCreate = 5;
		String nodeName = Input.shareSearchPA;
		String savedName = Input.mySavedSearch;
		Boolean inputValue = true;
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();

		base.stepInfo("Test case Id: RPMXCON-49037");
		base.stepInfo(
				" Verify that In Saved Search Page proper options are available when user selects 'My Saved Search' Groups");

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

		// Land on Saved Search Page
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");

		// verify the Saved Search Proper Option
		driver.waitForPageToBeReady();
		base.waitForElement(saveSearch.getSavedSearchTreeNode(savedName));
		saveSearch.getSavedSearchTreeNode(savedName).waitAndClick(10);
		saveSearch.verifySavedSearchProperOptionsMySavedSearchAsPA();
		base.stepInfo("-----------------------------" + savedName + " Completed--------------------------------");

		base.waitForElement(saveSearch.getSavedSearchTreeNode(nodeName));
		saveSearch.getSavedSearchTreeNode(nodeName).waitAndClick(10);
		saveSearch.verifySavedSearchProperOptionsSharedWithAsPARMUREV("PA");
		base.stepInfo("-----------------------------" + nodeName + " Completed--------------------------------");

		// navigate to Doclist and return back to Saved Search page
		saveSearch.navigateToDoclistAndReturnBackToSavedSearch();

		// verify the Saved Search Proper Option
		saveSearch.verifySavedSearchProperOptionsMySavedSearchAsPA();

		login.logout();
	}

	/**
	 * @author Mohan Date: 10/20/21 Modified date:N/A Modified by: N/A Description :
	 *         Verify that In Saved Search Page proper options are available when
	 *         user selects "Shared with <Security Group>" search group or a child
	 *         search group in "Shared with <Security Group>" -RPMXCON-49038 Sprint
	 *         05
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-49038", enabled = true, dataProvider = "SavedSearchwithUsersandrole", groups = {
			"regression" })
	public void validateSavedSearchPageProperOptionsSharedWith(String username, String password, String fullName,
			String role) throws InterruptedException, ParseException {

		int noOfNodesToCreate = 5;
		int selectIndex = 2;
		String SGtoShare = Input.shareSearchDefaultSG;
		String node;
		Boolean inputValue = true;
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();

		base.stepInfo("Test case Id: RPMXCON-49038");
		base.stepInfo(
				"Verify that In Saved Search Page proper options are available when user selects 'Shared with <Security Group>' search group or a child search group in 'Shared with <Security Group>'");

		// Login as User
		login.loginToSightLine(username, password);
		base.stepInfo("Loggedin As : " + fullName);

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

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		node = saveSearch.childNodeSelectionToShare(selectIndex, newNodeList);
		System.out.println("Final : " + node);
		saveSearch.shareSavedNodePA(SGtoShare, node, false, true, nodeSearchpair.get(node));

		base.stepInfo("-------Pre-Condition completed--------");

		// verify the Saved Search Proper Option
		driver.waitForPageToBeReady();

		base.waitForElement(saveSearch.getSavedSearchTreeNode(SGtoShare));
		saveSearch.getSavedSearchTreeNode(SGtoShare).waitAndClick(10);
		driver.waitForPageToBeReady();
		saveSearch.verifySavedSearchProperOptionsSharedWithAsPARMUREV(role);
		base.stepInfo("-----------------------------" + SGtoShare + " Completed--------------------------------");
		// navigate to Doclist and return back to Saved Search page
		saveSearch.navigateToDoclistAndReturnBackToSavedSearch();

		// verify Saved Search Proper Option
		saveSearch.verifySavedSearchProperOptionsSharedWithAsPARMUREV(role);

		base.stepInfo("-----------------------------" + SGtoShare + " Completed--------------------------------");

		// verify Saved Search by child node of Shared with
		saveSearch.selectChildNodeOfSharedWith();
		saveSearch.verifySavedSearchProperOptionsSharedWithAsPARMUREV(role);
		base.stepInfo("-----------------------------" + SGtoShare + " Completed--------------------------------");
		// navigate to Doclist and return back to Saved Search page
		saveSearch.navigateToDoclistAndReturnBackToSavedSearch();

		// verify Saved Search Proper Option
		saveSearch.verifySavedSearchProperOptionsSharedWithAsPARMUREV(role);

		login.logout();

	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		Reporter.setCurrentTestResult(result);
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
			login.logoutWithoutAssert();
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
	}

}
