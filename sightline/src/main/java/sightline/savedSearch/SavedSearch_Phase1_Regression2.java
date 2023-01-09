package sightline.savedSearch;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.ReportsPage;
import pageFactory.SavedSearch;
import pageFactory.SearchTermReportPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class SavedSearch_Phase1_Regression2 {

	Driver driver;
	LoginPage login;
	SavedSearch saveSearch;
	SessionSearch session;
	BaseClass base;
	SoftAssert softAssertion;
	MiniDocListPage miniDocListPage;
	SearchTermReportPage searchTerm;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		Input in = new Input();
		in.loadEnvConfig();
	}

	@DataProvider(name = "SavedSearchwithRMUandREV")
	public Object[][] SavedSearchwithRMUandREV() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName } };
		return users;
	}

	@DataProvider(name = "UseraAndShareOprtions")
	public Object[][] UseraAndShareOprtions() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName, Input.shareSearchPA },
				{ Input.pa1userName, Input.pa1password, Input.pa1FullName, Input.shareSearchDefaultSG },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName, Input.shareSearchDefaultSG } };
		return users;
	}

	@DataProvider(name = "UserAndShare")
	public Object[][] UserAndShare() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName, Input.shareSearchPA },
				{ Input.pa1userName, Input.pa1password, Input.pa1FullName, Input.shareSearchDefaultSG },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName, Input.shareSearchDefaultSG },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName, Input.shareSearchDefaultSG } };
		return users;
	}

	@DataProvider(name = "UserPaAndSaAndDa")
	public Object[][] UserPaAndSaAndDa() {
		Object[][] users = { { Input.pa1userName, Input.pa1password },
//				{ Input.sa1userName, Input.sa1password },
//				{ Input.da1userName, Input.da1password }
		};
		return users;
	}

	@DataProvider(name = "AllTheUsers")
	public Object[][] AllTheUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password },
				{ Input.rev1userName, Input.rev1password } };
		return users;
	}

	@DataProvider(name = "PaAndRmuUser")
	public Object[][] PaAndRmuUser() {
		Object[][] users = { { Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password } };
		return users;
	}

	@DataProvider(name = "verifyOverwrittingSavedSearch")
	public Object[][] SavedSearchwithPAandRMUwithRole() {
		Object[][] users = { { "Yes", "COMPLETED" }, { "No", "DRAFT" } };
		return users;
	}

	@DataProvider(name = "verifyOverwrittingSavedSearchAsUser")
	public Object[][] verifyOverwrittingSavedSearchASUser() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName, "Yes", "COMPLETED", 1 },
				{ Input.pa1userName, Input.pa1password, Input.pa1FullName, "No", "DRAFT", 1 },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName, "Yes", "COMPLETED", 1 },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName, "No", "DRAFT", 1 },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName, "Yes", "COMPLETED", 1 },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName, "No", "DRAFT", 1 } };

		return users;
	}

	@DataProvider(name = "PaAndRmuUsers")
	public Object[][] PaAndRmuUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{ Input.pa2userName, Input.pa2password, Input.pa1FullName },
				{ Input.rmu2userName, Input.rmu2password, Input.rmu1FullName } };

		return users;
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod)
			throws IOException, ParseException, InterruptedException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.logBefore(testMethod.getName());

		driver = new Driver();
		base = new BaseClass(driver);
		login = new LoginPage(driver);
		saveSearch = new SavedSearch(driver);
		session = new SessionSearch(driver);
		softAssertion = new SoftAssert();
	}

	/**
	 * @author Jeevitha Date: 12/21/21 Modified date:N/A Modified by: Raghuram
	 * @Description :For RMU - Validate sharing modified search group to Security
	 *              Group that are already shared(middle of the hierarchy)
	 *              -RPMXCON-49882 Sprint 08
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-49882", enabled = true, groups = { "regression" })
	public void validateSharingAlreadySharedSGWithModificationsInMiddleOfHierarfchyWithSecurityGroup()
			throws Exception {

		int noOfNodesToCreate = 6;
		int selectIndex = 0;
		int selectIndex1 = 2;
		String SGtoShare = Input.securityGroup;
		String node;
		Boolean inputValue = true;
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		HashMap<String, String> searchGroupSearchpIDpair = new HashMap<>();
		HashMap<String, String> searchGroupSearchpIDpair_1 = new HashMap<>();
		int[] listTOSelect = { 2, 3, 4 };
		int[] listTOSelectPair = { 0, 1, 2 };

		base.stepInfo("Test case Id: RPMXCON-49882 - Saved Search Sprint 08");
		base.stepInfo(
				"For RMU - Validate sharing modified search group to Security Group that are already shared(middle of the hierarchy)");

		// Login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// Landed on Saved Search
		saveSearch.navigateToSSPage();
		// Multiple Node Creation
		newNodeList = saveSearch.createSGAndReturn("RMU", "No", noOfNodesToCreate);
		System.out.println("Node creation is done followed by adding searches to the created nodes");
		base.stepInfo("Node creation is done followed by adding searches to the created nodes");

		// Adding searches to the created nodes
		driver.getWebDriver().get(Input.url + "Search/Searches");
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);
		saveSearch.sortedMapList(nodeSearchpair);

		// Search ID collection set 1
		saveSearch.navigateToSSPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(2);
		saveSearch.rootGroupExpansion();

		searchGroupSearchpIDpair = saveSearch.collectionOfSearchIdsFromNodeCollections(newNodeList, nodeSearchpair,
				searchGroupSearchpIDpair);

//		saveSearch.navigateToSSPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(2);
		node = saveSearch.childNodeSelectionToShare(selectIndex, newNodeList);
		System.out.println("Final : " + node);
		saveSearch.shareSavedNodePA(SGtoShare, node, false, true, nodeSearchpair.get(node));
		saveSearch.verifyImpactinSharedchildNodes(SGtoShare, newNodeList, selectIndex, nodeSearchpair,
				searchGroupSearchpIDpair);
		driver.Navigate().refresh();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(2);

		// Adding new searches to root node and leafe node
		driver.getWebDriver().get(Input.url + "Search/Searches");
		session.addSearchInNodewithChildNode(newNodeList, 1);

		// modify existing search Name
		saveSearch.navigateToSSPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(2);
		saveSearch.rootGroupExpansion();
		saveSearch.modifyExistingSearchName(newNodeList, nodeSearchpair, 1);

		List<String> newNodeList_1 = new ArrayList<String>();
		base.waitTime(3);
		newNodeList_1 = base.listOfRandomIndexToAdd(newNodeList, listTOSelect);

		HashMap<String, String> nodeSearchpair_1 = new HashMap<String, String>();
		nodeSearchpair_1 = base.hashMapOfRandomIndexToAdd(newNodeList_1, listTOSelectPair, nodeSearchpair);

		// collecting search ID from 3th hierarchy level node
//		saveSearch.navigateToSSPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(2);
		node = saveSearch.childNodeSelectionToShare(1, newNodeList);

		saveSearch.rootGroupExpansion();
		searchGroupSearchpIDpair_1 = saveSearch.collectionOfSearchIdsFromNodeCollections(newNodeList_1,
				nodeSearchpair_1, searchGroupSearchpIDpair_1);

		// share the 3th hierarchy level node with Default Security group
		saveSearch.navigateToSavedSearchPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(2);
		node = saveSearch.childNodeSelectionToShare(selectIndex1, newNodeList);
		System.out.println("Final : " + node);
		saveSearch.shareSavedNodePA(SGtoShare, node, false, true, nodeSearchpair.get(node));

		// verifying the searches as RMU
		saveSearch.navigateToSSPage();
		saveSearch.getSavedSearchGroupName(SGtoShare).waitAndClick(10);
		node = saveSearch.childNodeSelectionToShare(1, newNodeList);
		saveSearch.rootGroupExpansion();
		saveSearch.verifyImpactsInSharedChildNodes(SGtoShare, newNodeList_1, selectIndex, nodeSearchpair_1,
				searchGroupSearchpIDpair_1);

		// Deleting the search Group in My Saved search
		saveSearch.deleteNode(Input.mySavedSearch, newNodeList.get(0));

		login.logout();

		// login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// verifying the searches as RMU
		saveSearch.navigateToSSPage();
		saveSearch.getSavedSearchGroupName(SGtoShare).waitAndClick(10);
		node = saveSearch.childNodeSelectionToShare(1, newNodeList);
		saveSearch.rootGroupExpansion();
		saveSearch.verifyImpactsInSharedChildNodes(SGtoShare, newNodeList_1, selectIndex, nodeSearchpair_1,
				searchGroupSearchpIDpair_1);

		login.logout();

		// login as PAU
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// verifying the searches as PAU
		saveSearch.navigateToSSPage();
		saveSearch.getSavedSearchGroupName(SGtoShare).waitAndClick(10);
		node = saveSearch.childNodeSelectionToShare(1, newNodeList);
		saveSearch.rootGroupExpansion();
		saveSearch.verifyImpactsInSharedChildNodes(SGtoShare, newNodeList_1, selectIndex, nodeSearchpair_1,
				searchGroupSearchpIDpair_1);

		// Deleting the search Group in Default Security Group
		saveSearch.deleteNode(SGtoShare, newNodeList.get(0));

		login.logout();

	}

	/**
	 * @author Jeevitha Date: 12/21/21 Modified date:N/A Modified by:N/A
	 * @Description : [RPMXCON-49865] Validate sharing modified search group to
	 *              Security Group that are already shared(middle of the hierarchy)
	 *              - Development -RPMXCON-49865 Sprint 08
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-49865", enabled = true, groups = { "regression" })
	public void modificationsInMiddleOfHierarchyWithSecurityGroupAsPA() throws Exception {

		int noOfNodesToCreate = 6;
		int selectIndex = 0;
		int selectIndex1 = 2;
		String SGtoShare = Input.securityGroup;
		String node;
		Boolean inputValue = true;
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		HashMap<String, String> searchGroupSearchpIDpair = new HashMap<>();
		HashMap<String, String> searchGroupSearchpIDpair_1 = new HashMap<>();
		int[] listTOSelect = { 2, 3, 4 };
		int[] listTOSelectPair = { 0, 1, 2 };

		base.stepInfo("Test case Id: RPMXCON-49865 - Saved Search Sprint 07");
		base.stepInfo(
				"[RPMXCON-49865] Validate sharing modified search group to Security Group that are already shared(middle of the hierarchy) - Development");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Landed on Saved Search
		saveSearch.navigateToSSPage();
		// Multiple Node Creation
		newNodeList = saveSearch.createSGAndReturn("PA", "No", noOfNodesToCreate);
		System.out.println("Node creation is done followed by adding searches to the created nodes");
		base.stepInfo("Node creation is done followed by adding searches to the created nodes");

		// Adding searches to the created nodes
		driver.getWebDriver().get(Input.url + "Search/Searches");
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);
		saveSearch.sortedMapList(nodeSearchpair);

		// Search ID collection set 1
		saveSearch.navigateToSSPage();
		base.waitForElement(saveSearch.getSavedSearchNewGroupExpand());
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);

		searchGroupSearchpIDpair = saveSearch.collectionOfSearchIdsFromNodeCollections(newNodeList, nodeSearchpair,
				searchGroupSearchpIDpair);

		saveSearch.navigateToSSPage();
		node = saveSearch.childNodeSelectionToShare(selectIndex, newNodeList);
		System.out.println("Final : " + node);
		saveSearch.shareSavedNodePA(SGtoShare, node, false, true, nodeSearchpair.get(node));
		saveSearch.verifyImpactinSharedchildNodes(SGtoShare, newNodeList, selectIndex, nodeSearchpair,
				searchGroupSearchpIDpair);

		// Adding new searches to root node and leafe node
		driver.getWebDriver().get(Input.url + "Search/Searches");
		session.addSearchInNodewithChildNode(newNodeList, 1);

		// modify existing search Name
		saveSearch.navigateToSSPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(5); // added my sampath
		base.waitForElement(saveSearch.getSavedSearchNewGroupExpand());
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		saveSearch.modifyExistingSearchName(newNodeList, nodeSearchpair, 1);

		List<String> newNodeList_1 = new ArrayList<String>();
		newNodeList_1 = base.listOfRandomIndexToAdd(newNodeList, listTOSelect);

		HashMap<String, String> nodeSearchpair_1 = new HashMap<String, String>();
		nodeSearchpair_1 = base.hashMapOfRandomIndexToAdd(newNodeList_1, listTOSelectPair, nodeSearchpair);

		// collecting search ID from 3th hierarchy level node
		saveSearch.navigateToSSPage();
		node = saveSearch.childNodeSelectionToShare(1, newNodeList);
		saveSearch.rootGroupExpansion();
		searchGroupSearchpIDpair_1 = saveSearch.collectionOfSearchIdsFromNodeCollections(newNodeList_1,
				nodeSearchpair_1, searchGroupSearchpIDpair_1);

		// share the 3th hierarchy level node with Default Security group
		saveSearch.navigateToSSPage();
		node = saveSearch.childNodeSelectionToShare(selectIndex1, newNodeList);
		System.out.println("Final : " + node);
		saveSearch.shareSavedNodePA(SGtoShare, node, false, true, nodeSearchpair.get(node));

		// verifying the searches as PA
		saveSearch.navigateToSSPage();
		saveSearch.getSavedSearchGroupName(SGtoShare).waitAndClick(10);
		node = saveSearch.childNodeSelectionToShare(1, newNodeList);
		saveSearch.rootGroupExpansion();
		saveSearch.verifyImpactsInSharedChildNodes(SGtoShare, newNodeList_1, selectIndex, nodeSearchpair_1,
				searchGroupSearchpIDpair_1);

		// Deleting the search Group in My Saved search
		saveSearch.deleteNode(Input.mySavedSearch, newNodeList.get(0));

		login.logout();

		// login as PAU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// verifying the searches as PAU
		saveSearch.navigateToSSPage();
		saveSearch.getSavedSearchGroupName(SGtoShare).waitAndClick(10);
		node = saveSearch.childNodeSelectionToShare(1, newNodeList);
		saveSearch.rootGroupExpansion();
		saveSearch.verifyImpactsInSharedChildNodes(SGtoShare, newNodeList_1, selectIndex, nodeSearchpair_1,
				searchGroupSearchpIDpair_1);

		// Deleting the search Group in Default Security Group
		saveSearch.deleteNode(SGtoShare, newNodeList.get(0));

		login.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description : SA/DA/PA User impersonate down as RMU/RU role, create
	 *              Searchgroups and Searches, and then runs the Timeline and Gaps
	 *              report against My saved searches in PAU role [RPMXCON- 57408]
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-57408", enabled = true, dataProvider = "UserPaAndSaAndDa", groups = { "regression" })
	public void verifyTimelineReport(String username, String password) throws InterruptedException, ParseException {
		String search1 = "Search" + Utility.dynamicNameAppender();
		String search2 = "Search" + Utility.dynamicNameAppender();
		String searc3 = "Search" + Utility.dynamicNameAppender();
		String search4 = "Search" + Utility.dynamicNameAppender();
		String search = "Search" + Utility.dynamicNameAppender();
		String search0 = "Search" + Utility.dynamicNameAppender();

		String sourceOption = "Searches";
		ReportsPage report = new ReportsPage(driver);

		login.loginToSightLine(username, password);
		base.stepInfo("Test case Id: RPMXCON-57408 SavedSearch Sprint 08");
		base.stepInfo(
				"SA/DA/PA User impersonate down as RMU/RU role, create Searchgroups and Searches, and then runs the Timeline and Gaps report against My saved searches in PAU role");

		base.rolesToImp("SA", "PA");

		String newNode = saveSearch.createASearchGroupandReturnName(search);
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(search);
		session.saveSearchInNewNode(search0, newNode);

		base.impersonatePAtoRMU();

		String newNode1 = saveSearch.createASearchGroupandReturnName(search1);
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(search1);
		session.saveSearchInNewNode(search2, newNode1);

		base.impersonateRMUtoReviewer();

		String newNode2 = saveSearch.createASearchGroupandReturnName(searc3);
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searc3);
		session.saveSearchInNewNode(search4, newNode2);

		base.impersonateReviewertoRMU();

		// check searches and node created by RMU
		report.navigateToTimelineReportPage();
		report.verifySearchFromTGReport(sourceOption, search1, false, true);
		report.verifySearchFromTGReport(sourceOption, newNode1, false, true);
		report.verifySearchFromTGReport(sourceOption, search2, false, false);

		// check searches and node created by REV
		report.verifySearchFromTGReport(sourceOption, searc3, false, true);
		report.verifySearchFromTGReport(sourceOption, newNode2, false, true);
		report.verifySearchFromTGReport(sourceOption, search4, false, false);

		// Check Search And node created by PA
		report.verifySearchFromTGReport(sourceOption, search, false, false);
		report.verifySearchFromTGReport(sourceOption, newNode, false, false);
		report.verifySearchFromTGReport(sourceOption, search0, false, false);

		base.impersonateSAtoPA();

		// check searches and node created by RMU
		report.navigateToTimelineReportPage();
		report.verifySearchFromTGReport(sourceOption, search1, false, false);
		report.verifySearchFromTGReport(sourceOption, newNode1, false, false);
		report.verifySearchFromTGReport(sourceOption, search2, false, false);

		// check searches and node created by REV
		report.verifySearchFromTGReport(sourceOption, searc3, false, false);
		report.verifySearchFromTGReport(sourceOption, newNode2, false, false);
		report.verifySearchFromTGReport(sourceOption, search4, false, false);

		// Check Search And node created by PA
		report.verifySearchFromTGReport(sourceOption, search0, false, false);
		report.verifySearchFromTGReport(sourceOption, search, false, true);
		report.verifySearchFromTGReport(sourceOption, newNode, true, true);
		report.generateTimelineAndGapsReport("LastSaveDate");

		// delete search
		saveSearch.deleteSearch(search, Input.mySavedSearch, "Yes");
		saveSearch.deleteNode(Input.mySavedSearch, newNode);

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 12/21/21 Modified date:N/A Modified by: N/A
	 *         Description : Validate sharing modified search/groups to Security
	 *         Group which are already shared-RPMXCON-49866 Sprint 08
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-49866", enabled = true, groups = { "regression" })
	public void validateSharingAlreadySharedSGWithModifications() throws Exception {

		int noOfNodesToCreate = 6;
		int selectIndex = 0;
		String SGtoShare = Input.shareSearchDefaultSG;
		String node;
		Boolean inputValue = true;
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		HashMap<String, String> searchGroupSearchpIDpair = new HashMap<>();

		base.stepInfo("Test case Id: RPMXCON-49866 - Saved Search Sprint 08");
		base.stepInfo("Validate sharing modified search/groups to Security Group which are already shared");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Navigate to Saved Search
		saveSearch.navigateToSavedSearchPage();

		// Multiple Node Creation
		newNodeList = saveSearch.createSGAndReturn("PA", "No", noOfNodesToCreate);
		base.stepInfo("Node creation is done followed by adding searches to the created nodes");

		// Adding searches to the created nodes
		driver.getWebDriver().get(Input.url + "Search/Searches");
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);
		saveSearch.sortedMapList(nodeSearchpair);

		// collection of search_ID in My Saved Search
		saveSearch.navigateToSavedSearchPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(2);
		saveSearch.rootGroupExpansion();

		searchGroupSearchpIDpair = saveSearch.collectionOfSearchIdsFromNodeCollections(newNodeList, nodeSearchpair,
				searchGroupSearchpIDpair);

		// share the Root node with pa set_01
		saveSearch.navigateToSavedSearchPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(2);
		node = saveSearch.childNodeSelectionToShare(selectIndex, newNodeList);
		System.out.println("Final : " + node);
		saveSearch.shareSavedNodePA(SGtoShare, node, false, true, nodeSearchpair.get(node));
		driver.waitForPageToBeReady();
		saveSearch.verifyImpactinSharedchildNodes(SGtoShare, newNodeList, selectIndex, nodeSearchpair,
				searchGroupSearchpIDpair);
		driver.Navigate().refresh();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(5);

		// Adding new searches to root node and leaf node
		driver.getWebDriver().get(Input.url + "Search/Searches");
		HashMap<String, String> nodeNewSearchpair = session.addSearchInNodewithChildNode(newNodeList, 1);

		// modify existing search Name
		saveSearch.navigateToSavedSearchPage();
		driver.waitForPageToBeReady();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(2);
		saveSearch.rootGroupExpansion();
		Map<String, String> nodeRenameSearchpair = saveSearch.modifyExistingSearchName(newNodeList, nodeSearchpair, 1);
		for (int i = 0; i < nodeRenameSearchpair.size(); i++) {
			nodeSearchpair.replace(newNodeList.get(i), nodeSearchpair.get(newNodeList.get(i)),
					nodeRenameSearchpair.get(newNodeList.get(i)));
		}

		// collecting search_ID in modified search in my saved search
		HashMap<String, String> modifiedSearchIDpair = new HashMap<String, String>();
		modifiedSearchIDpair = saveSearch.collectionOfSearchIdsFromNodeCollections(newNodeList, nodeSearchpair,
				modifiedSearchIDpair);

		// collecting search_ID in add search in my saved search
		HashMap<String, String> addSearchIDpair = new HashMap<String, String>();
		addSearchIDpair = saveSearch.collectionOfSearchIdsFromNodeCollections(newNodeList, nodeNewSearchpair,
				addSearchIDpair);

		// share the Root node with pa set_02
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(2);
		node = saveSearch.childNodeSelectionToShare(selectIndex, newNodeList);
		System.out.println("Final : " + node);
		saveSearch.shareSavedNodePA(SGtoShare, node, false, true, nodeSearchpair.get(node));
		driver.waitForPageToBeReady();

		// verify the search with modifiedSearchIDpair
		saveSearch.navigateToSSPage();
		saveSearch.verifyImpactinSharedchildNodes(SGtoShare, newNodeList, selectIndex, nodeSearchpair,
				modifiedSearchIDpair);
		login.logout();

		// login again as another pa to cross check

		login.loginToSightLine(Input.pa2userName, Input.pa2password);
		base.stepInfo("Loggedin As : " + Input.pa2FullName);
		// Landed on Saved Search
		base.stepInfo("ID verfication between shared searches");
		saveSearch.navigateToSavedSearchPage();
		saveSearch.verifyImpactinSharedchildNodes(SGtoShare, newNodeList, selectIndex, nodeNewSearchpair,
				addSearchIDpair);
		base.stepInfo("Verified Shared SG and Searches");

		login.logout();

		// login again as another RMU to cross check

		login.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		base.stepInfo("Loggedin As : " + Input.rmu2FullName);
		// Landed on Saved Search
		base.stepInfo("ID verfication between shared searches");
		saveSearch.navigateToSavedSearchPage();
		// verify the search with modifiedSearchIDpair
		saveSearch.verifyImpactinSharedchildNodes(SGtoShare, newNodeList, selectIndex, nodeSearchpair,
				modifiedSearchIDpair);
		saveSearch.navigateToSSPage();
		saveSearch.verifyImpactinSharedchildNodes(SGtoShare, newNodeList, selectIndex, nodeNewSearchpair,
				addSearchIDpair);
		base.stepInfo("Verified Shared SG and Searches");

		login.logout();

	}

	/**
	 * @author Raghuram A Date: 12/21/21 Modified date:N/A Modified by: N/A
	 *         Description : For RMU - Validate sharing modified search/groups to
	 *         Security Group which are already shared-RPMXCON-49883 Sprint 08
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-49883", enabled = true, groups = { "regression" })
	public void validateSharingAlreadySharedSGWithModificationsViaRMU() throws Exception {

		int noOfNodesToCreate = 6;
		int selectIndex = 0;
		String SGtoShare = Input.shareSearchDefaultSG;
		String node;
		Boolean inputValue = true;
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		HashMap<String, String> searchGroupSearchpIDpair = new HashMap<>();

		base.stepInfo("Test case Id: RPMXCON-49883 - Saved Search Sprint 08");
		base.stepInfo("For RMU - Validate sharing modified search/groups to Security Group which are already shared");

		// Login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// Navigate to Saved Search
		saveSearch.navigateToSavedSearchPage();

		// Multiple Node Creation
		newNodeList = saveSearch.createSGAndReturn("PA", "No", noOfNodesToCreate);
		base.stepInfo("Node creation is done followed by adding searches to the created nodes");

		// Adding searches to the created nodes
		driver.getWebDriver().get(Input.url + "Search/Searches");
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);
		saveSearch.sortedMapList(nodeSearchpair);

		// collection of search_ID in My Saved Search
		saveSearch.navigateToSavedSearchPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(2);
		saveSearch.rootGroupExpansion();

		searchGroupSearchpIDpair = saveSearch.collectionOfSearchIdsFromNodeCollections(newNodeList, nodeSearchpair,
				searchGroupSearchpIDpair);

		// share the Root node with pa set_01
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(2);
		node = saveSearch.childNodeSelectionToShare(selectIndex, newNodeList);
		System.out.println("Final : " + node);
		saveSearch.shareSavedNodePA(SGtoShare, node, false, true, nodeSearchpair.get(node));
		driver.waitForPageToBeReady();
		saveSearch.verifyImpactinSharedchildNodes(SGtoShare, newNodeList, selectIndex, nodeSearchpair,
				searchGroupSearchpIDpair);
		driver.Navigate().refresh();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(5);

		// Adding new searches to root node and leaf node
		driver.getWebDriver().get(Input.url + "Search/Searches");
		HashMap<String, String> nodeNewSearchpair = session.addSearchInNodewithChildNode(newNodeList, 1);

		// modify existing search Name
		saveSearch.navigateToSavedSearchPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(2);
		saveSearch.rootGroupExpansion();
		Map<String, String> nodeRenameSearchpair = saveSearch.modifyExistingSearchName(newNodeList, nodeSearchpair, 1);
		for (int i = 0; i < nodeRenameSearchpair.size(); i++) {
			nodeSearchpair.replace(newNodeList.get(i), nodeSearchpair.get(newNodeList.get(i)),
					nodeRenameSearchpair.get(newNodeList.get(i)));
		}

		// collecting search_ID in modified search in my saved search
		HashMap<String, String> modifiedSearchIDpair = new HashMap<String, String>();
		modifiedSearchIDpair = saveSearch.collectionOfSearchIdsFromNodeCollections(newNodeList, nodeSearchpair,
				modifiedSearchIDpair);

		// collecting search_ID in add search in my saved search
		HashMap<String, String> addSearchIDpair = new HashMap<String, String>();
		addSearchIDpair = saveSearch.collectionOfSearchIdsFromNodeCollections(newNodeList, nodeNewSearchpair,
				addSearchIDpair);

		// share the Root node with pa set_02
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(2);
		node = saveSearch.childNodeSelectionToShare(selectIndex, newNodeList);
		System.out.println("Final : " + node);
		saveSearch.shareSavedNodePA(SGtoShare, node, false, true, nodeSearchpair.get(node));
		driver.waitForPageToBeReady();

		// verify the search with modifiedSearchIDpair
		saveSearch.navigateToSSPage();
		saveSearch.verifyImpactinSharedchildNodes(SGtoShare, newNodeList, selectIndex, nodeSearchpair,
				modifiedSearchIDpair);
		login.logout();

		// login again as another pa to cross check

		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);
		// Landed on Saved Search
		base.stepInfo("ID verfication between shared searches");
		saveSearch.navigateToSavedSearchPage();
		saveSearch.verifyImpactinSharedchildNodes(SGtoShare, newNodeList, selectIndex, nodeNewSearchpair,
				addSearchIDpair);
		base.stepInfo("Verified Shared SG and Searches");

		login.logout();

		// login again as another RMU to cross check

		login.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		base.stepInfo("Loggedin As : " + Input.rmu2FullName);
		// Landed on Saved Search
		base.stepInfo("ID verfication between shared searches");
		saveSearch.navigateToSavedSearchPage();
		// verify the search with modifiedSearchIDpair
		saveSearch.verifyImpactinSharedchildNodes(SGtoShare, newNodeList, selectIndex, nodeSearchpair,
				modifiedSearchIDpair);
		saveSearch.navigateToSSPage();
		saveSearch.verifyImpactinSharedchildNodes(SGtoShare, newNodeList, selectIndex, nodeNewSearchpair,
				addSearchIDpair);
		base.stepInfo("Verified Shared SG and Searches");

		login.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that application displays all documents that are in the
	 *              aggregate results set of \"Default Security Group\" and User
	 *              Navigate Search groups to Report [RPMXCON-49014]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-49014", enabled = true, groups = { "regression" })
	public void verifyReportForAggregateSearchHitCountInStrPage() throws Exception {
		String search = "Search" + Utility.dynamicNameAppender();
		String search2 = "Search" + Utility.dynamicNameAppender();
		SearchTermReportPage str = new SearchTermReportPage(driver);

		// Login As User
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Test case Id: RPMXCON-49014");
		base.stepInfo(
				"Verify that application displays all documents that are in the aggregate results set of \"Default Security Group\" and User Navigate Search groups to Report");

		session.basicContentSearch(Input.searchString5);
		session.saveSearchInNewNode(search, null);

		saveSearch.shareSearchFlow(search, Input.shareSearchDefaultSG, "RMU");

		session.selectSavedsearchInASWp(Input.shareSearchDefaultSG);
		int hitCount = session.saveAndReturnPureHitCount();

		saveSearch.SavedSearchToTermReport_New(Input.shareSearchDefaultSG, false, null, search, "No");
		str.verifyaggregateCount("HITS");
		base.waitForElement(str.getHitsCount());
		int expectedHitsCount = Integer.parseInt(str.getHitsCount().getText());
		base.stepInfo("Aggregate Hit Count of searhces is :  " + expectedHitsCount);
		softAssertion.assertEquals(hitCount, expectedHitsCount);
		softAssertion.assertAll();

		// Delete Searches
		saveSearch.deleteSearch(search, Input.mySavedSearch, "Yes");
		saveSearch.deleteSearch(search, Input.shareSearchDefaultSG, "Yes");

		login.logout();

	}

	/**
	 * @author Jeevitha
	 * @throws InterruptedException
	 * @Description:verifying the All count of Basic and advance search after from
	 *                        save search[RPMXCON-48490]
	 */
	@Test(description = "RPMXCON-48490", enabled = true, groups = { "regression" })
	public void ValidateAllCountOfSavedSearch() throws InterruptedException {
		String BasicSearchName = "comments" + Utility.dynamicNameAppender();
		String AdvanceSearchName = "comments" + Utility.dynamicNameAppender();
		String nearDupe = "Near Duplicate Count";
		String conceptually = "Conceptually Similar Count";

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);
		base.stepInfo("Test case Id: RPMXCON-48490 - Saved Search");
		base.stepInfo(
				"Verify status and count are updated in Saved Search Screen when user executes Basic/Advanced search with Execute option from Saved Search");

		// Basic Search
		int purehit = session.basicContentSearch(Input.searchString1);
		session.saveSearch(BasicSearchName);

		// Advanced Search
		base.selectproject();
		session.advancedContentSearch(Input.searchString2);
		session.saveSearchAdvanced_New(AdvanceSearchName, Input.mySavedSearch);

		// Execute Basic Search
		saveSearch.savedSearchExecute(BasicSearchName, purehit);
		saveSearch.getDocCountAndStatusOfBatch(BasicSearchName, nearDupe, true);
		saveSearch.ApplyShowAndHideFilter(conceptually, BasicSearchName);

		// Execute Advanced Search
		saveSearch.savedSearchExecute(AdvanceSearchName, purehit);
		saveSearch.getDocCountAndStatusOfBatch(AdvanceSearchName, nearDupe, true);
		saveSearch.ApplyShowAndHideFilter(conceptually, AdvanceSearchName);

		// Delete Search
		saveSearch.deleteSearch(BasicSearchName, Input.mySavedSearch, "Yes");
		saveSearch.deleteSearch(AdvanceSearchName, Input.mySavedSearch, "Yes");

		login.logout();

	}

	/**
	 * @author Raghuram A Date: 12/23/21 Modified date:N/A Modified by:N/A
	 * @Description : PA impersonate down as RMU/RU role, create Searchgroups and
	 *              Searches, and then perform bulk actions against My saved
	 *              searches in PAU role -RPMXCON-57388 Sprint 08
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-57388", enabled = true, groups = { "regression" })
	public void performBulkActionsAgainstMySavedSearches() throws InterruptedException {

		miniDocListPage = new MiniDocListPage(driver);
		SearchTermReportPage searchTerm = new SearchTermReportPage(driver);

		String searchPA = "Search_PA_" + Utility.dynamicNameAppender();
		String searchPANode = "Search_PA_" + Utility.dynamicNameAppender();
		String searchRMU = "Search_RMU_" + Utility.dynamicNameAppender();
		String searchRMUNode = "Search_RMU_" + Utility.dynamicNameAppender();
		String searchREV = "Search_REV_" + Utility.dynamicNameAppender();
		String searchREVNode = "Search_REV_" + Utility.dynamicNameAppender();
		String[] searches = { searchPA, searchRMU, searchREV };
		String TagName = "Tag" + Utility.dynamicNameAppender();
		String folderName = "Folder" + Utility.dynamicNameAppender();

		int latencyCheckTime = 5;
		String passMessage = "Application not hang or shows latency more than " + latencyCheckTime + " seconds.";
		String failureMsg = "Continues Loading more than " + latencyCheckTime + " seconds.";
		String StyletoChoose = "CSV";
		String fieldTypeToChoose = "[,] 044";

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);
		base.stepInfo("Test case Id: RPMXCON-57388 SavedSearch Sprint 08");
		base.stepInfo(
				"PA impersonate down as RMU/RU role, create Searchgroups and Searches, and then perform bulk actions against My saved searches in PAU role");

		String newNodePA = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "searchPA", "No");
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchPA);
		session.saveSearchInNewNode(searchPANode, newNodePA);
		base.stepInfo("Created searches and saved as PA");

		base.rolesToImp("PA", "RMU");

		String newNodeRMU = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "searchRMU", "No");
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchRMU);
		session.saveSearchInNewNode(searchRMUNode, newNodeRMU);
		base.stepInfo("Created searches and saved as RMU");

		base.rolesToImp("RMU", "REV");

		String newNodeREV = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "searchREVs", "No");
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchREV);
		session.saveSearchInNewNode(searchREVNode, newNodeREV);
		base.stepInfo("Created searches and saved as REV");

		// base.rolesToImp("REV", "PA");
		base.impersonateSAtoPA();

		String[] searchNode = { newNodePA, newNodeRMU, newNodeREV };

		// Navigate to SavedSearch Page
		saveSearch.navigateToSSPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(3);

		// Verify Searches and Nodes
		base.stepInfo(
				"1. Search and Search group created with RMU or Reviewer role should not be available for PAU role ");
		base.stepInfo("2. Only Search groups and searches created by PAU role should be listed");
		saveSearch.searchesToVerify(true, searches, true, searchNode, null, null);

		// Get Count
		session.searchSavedSearchResult(Input.mySavedSearch);
		int aggregateHitCount = session.saveAndReturnPureHitCount();
		base.stepInfo("Aggregate Count : " + aggregateHitCount);
		System.out.println("Aggregate Count : " + aggregateHitCount);

		// Navigate to SavedSearch Page
		saveSearch.navigateToSavedSearchPage();

		base.stepInfo(
				"Select My Saved Search main folder and perform @Action and check for searches/document count against the action is performed.");

		base.selectproject();
		// TAG
		base.stepInfo("-----TAG------");
		HashMap<String, String> tagCount = saveSearch.bulkTagorFolderViaSS(true, true, Input.mySavedSearch, false,
				false, "", "", "Tag", 5, TagName, true, true, "", false, false);
		base.textCompareEquals(tagCount.get("PureHit Count"), Integer.toString(aggregateHitCount),
				"After the Bulk Tag - Pure hit appear like aggregate results set of all child search groups and searches  ",
				"Count Mismatches");
		base.selectproject();

		// FOLDER
		base.stepInfo("-----FOLDER------");
		HashMap<String, String> folderCount = saveSearch.bulkTagorFolderViaSS(true, true, Input.mySavedSearch, false,
				false, "", "", "Folder", 5, "", false, false, folderName, true, true);
		base.textCompareEquals(folderCount.get("PureHit Count"), Integer.toString(aggregateHitCount),
				"After the Bulk Folder - Pure hit shows the aggregate results set of all child search groups and searches ",
				"Count Mismatches");
		base.selectproject();

		base.stepInfo("-----DOC LIST------");
		// Navigate to SavedSearch Page
		saveSearch.navigateToSSPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(5);
		// Get the count of total no.of document list
		int finalCountresultDocList = saveSearch.launchDocListViaSSandReturnDocCount();

		base.stepInfo("-----DOC VIEW------");
		// Navigate to SavedSearch Page
		saveSearch.navigateToSavedSearchPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(5);
		saveSearch.docViewFromSS("View in DocView");

		// Load latency Verification
		Element loadingElement = session.getspinningWheel();
		saveSearch.loadingCountVerify(loadingElement, latencyCheckTime, passMessage, failureMsg);
		driver.waitForPageToBeReady();
		String currentUrl = driver.getWebDriver().getCurrentUrl();
		softAssertion.assertEquals(Input.url + "DocumentViewer/DocView", currentUrl);
		base.stepInfo("Navigated to DocView Page : " + currentUrl);

		// Main method
		miniDocListPage = new MiniDocListPage(driver);
		base.waitForElement(miniDocListPage.getDocumentCountFromDocView());
		String sizeofList = miniDocListPage.getDocumentCountFromDocView().getText();
		String documentSize = sizeofList.substring(sizeofList.indexOf("of") + 2, sizeofList.indexOf("Docs")).trim();
		System.out.println("Size : " + documentSize);
		base.stepInfo("Available documents in DocView page : " + sizeofList);

		base.digitCompareEquals(aggregateHitCount, Integer.parseInt(documentSize),
				"Shows all documents that are in the aggregate results set of all child search groups and searches",
				"Count Mismatches with the Documents");

		// Search Term Report
		base.stepInfo("-----STR------");
		saveSearch.SavedSearchToTermReport_New(Input.mySavedSearch, false, null, searchPA, "No");
		driver.waitForPageToBeReady();
		searchTerm.verifySTRForSearchFromSSPage(newNodePA, searchPA);
		base.waitForElement(searchTerm.getHitsCount());
		int expectedHitsCount = Integer.parseInt(searchTerm.getHitsCount().getText());
		base.stepInfo("Aggregate Hit Count of searhces is :  " + expectedHitsCount);
		base.digitCompareEquals(aggregateHitCount, expectedHitsCount, "STR count matches", "Count matches");
		softAssertion.assertEquals(aggregateHitCount, expectedHitsCount);

		// Export
		base.stepInfo("-----EXPORT------");
		saveSearch.navigateToSavedSearchPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(5);
		saveSearch.verifyExportpopup(StyletoChoose, fieldTypeToChoose);

		// Navigating to saved search page
		saveSearch.navigateToSavedSearchPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch);
		saveSearch.getSavedSearchExecuteButton().waitAndClick(5);
		saveSearch.getExecuteContinueBtn().waitAndClick(5);

		base.stepInfo("To verify Pending Status By applying filter");
		saveSearch.getStatusDropDown().waitAndClick(2);
		saveSearch.getLastStatusAsPending().Click();
		saveSearch.getStatusDropDown().Click();
		saveSearch.getSavedSearch_ApplyFilterButton().Click();
		saveSearch.verifyExecutionStatusInSavedSearchPage("PENDING");

		base.stepInfo("To verify Completed Status By applying filter");
		saveSearch.getStatusDropDown().Click();
		saveSearch.getLastStatusAsCompleted().Click();
		saveSearch.getSavedSearch_ApplyFilterButton().Click();
		saveSearch.verifyExecutionStatusInSavedSearchPage("COMPLETED");

		base.stepInfo("To verify In Progrss Status By applying filter");
		saveSearch.getStatusDropDown().Click();
		saveSearch.getLastStatusAsInProgress().Click();
		saveSearch.getSavedSearch_ApplyFilterButton().Click();
		saveSearch.verifyExecutionStatusInSavedSearchPage("INPROGRESS");

		softAssertion.assertAll();

		login.logout();

	}

	/**
	 * @author Raghuram A Date: 12/23/21 Modified date:N/A Modified by:N/A
	 * @Description : DA impersonate down as RMU/RU role, create Searchgroups and
	 *              Searches, and then perform bulk actions against My saved
	 *              searches in PAU role -RPMXCON-57387 Sprint 08
	 * @throws InterruptedException
	 * @throws ParseException
	 * 
	 */
	@Test(description = "RPMXCON-57387", enabled = true, groups = { "regression" })
	public void performBulkActionsAgainstMySavedSearchesDA() throws InterruptedException {

		miniDocListPage = new MiniDocListPage(driver);
		searchTerm = new SearchTermReportPage(driver);

		String searchPA = "Search_PA_" + Utility.dynamicNameAppender();
		String searchPANode = "Search_PA_" + Utility.dynamicNameAppender();
		String searchRMU = "Search_RMU_" + Utility.dynamicNameAppender();
		String searchRMUNode = "Search_RMU_" + Utility.dynamicNameAppender();
		String searchREV = "Search_REV_" + Utility.dynamicNameAppender();
		String searchREVNode = "Search_REV_" + Utility.dynamicNameAppender();
		String[] searches = { searchPA, searchRMU, searchREV };
		String TagName = "Tag" + Utility.dynamicNameAppender();
		String folderName = "Folder" + Utility.dynamicNameAppender();

		int latencyCheckTime = 5;
		String passMessage = "Application not hang or shows latency more than " + latencyCheckTime + " seconds.";
		String failureMsg = "Continues Loading more than " + latencyCheckTime + " seconds.";
		String StyletoChoose = "CSV";
		String fieldTypeToChoose = "[,] 044";

		// Login as PA
		login.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Loggedin As : " + Input.da1userName);
		base.stepInfo("Test case Id: RPMXCON-57387 SavedSearch Sprint 08");
		base.stepInfo(
				"DA impersonate down as RMU/RU role, create Searchgroups and Searches, and then perform bulk actions against My saved searches in PAU role");

		base.rolesToImp("DA", "PA");

		String newNodePA = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "PA", "No");
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchPA);
		session.saveSearchInNewNode(searchPANode, newNodePA);
		base.stepInfo("Created searches and saved as PA");

		base.rolesToImp("PA", "RMU");

		String newNodeRMU = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", "No");
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchRMU);
		session.saveSearchInNewNode(searchRMUNode, newNodeRMU);
		base.stepInfo("Created searches and saved as RMU");

		base.rolesToImp("RMU", "REV");

		String newNodeREV = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "REV", "No");
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchREV);
		session.saveSearchInNewNode(searchREVNode, newNodeREV);
		base.stepInfo("Created searches and saved as REV");

		// base.rolesToImp("REV", "PA");
		base.impersonateSAtoPA();

		String[] searchNode = { newNodePA, newNodeRMU, newNodeREV };

		// Navigate to SavedSearch Page
		saveSearch.navigateToSSPage();

		// Verify Searches and Nodes
		base.stepInfo(
				"1. Search and Search group created with RMU or Reviewer role should not be available for PAU role ");
		base.stepInfo("2. Only Search groups and searches created by PAU role should be listed");
		saveSearch.searchesToVerify(true, searches, true, searchNode, null, null);

		// Get Count
		session.searchSavedSearchResult(Input.mySavedSearch);
		int aggregateHitCount = session.saveAndReturnPureHitCount();
		base.stepInfo("Aggregate Count : " + aggregateHitCount);
		System.out.println("Aggregate Count : " + aggregateHitCount);

		// Navigate to SavedSearch Page
		saveSearch.navigateToSavedSearchPage();

		base.stepInfo(
				"Select My Saved Search main folder and perform @Action and check for searches/document count against the action is performed.");

		base.selectproject();
		// TAG
		base.stepInfo("-----TAG------");
		HashMap<String, String> tagCount = saveSearch.bulkTagorFolderViaSS(true, true, Input.mySavedSearch, false,
				false, "", "", "Tag", 5, TagName, true, true, "", false, false);
		base.textCompareEquals(tagCount.get("PureHit Count"), Integer.toString(aggregateHitCount),
				"After the Bulk Tag - Pure hit appear like aggregate results set of all child search groups and searches  ",
				"Count Mismatches");
		base.selectproject();

		// FOLDER
		base.stepInfo("-----FOLDER------");
		HashMap<String, String> folderCount = saveSearch.bulkTagorFolderViaSS(true, true, Input.mySavedSearch, false,
				false, "", "", "Folder", 5, "", false, false, folderName, true, true);
		base.textCompareEquals(folderCount.get("PureHit Count"), Integer.toString(aggregateHitCount),
				"After the Bulk Folder - Pure hit shows the aggregate results set of all child search groups and searches ",
				"Count Mismatches");
		base.selectproject();

		base.stepInfo("-----DOC LIST------");
		// Navigate to SavedSearch Page
		saveSearch.navigateToSSPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(5);
		// Get the count of total no.of document list
		int finalCountresultDocList = saveSearch.launchDocListViaSSandReturnDocCount();

		base.stepInfo("-----DOC VIEW------");
		// Navigate to SavedSearch Page
		saveSearch.navigateToSavedSearchPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(5);
		saveSearch.docViewFromSS("View in DocView");

		// Load latency Verification
		Element loadingElement = session.getspinningWheel();
		saveSearch.loadingCountVerify(loadingElement, latencyCheckTime, passMessage, failureMsg);
		driver.waitForPageToBeReady();
		String currentUrl = driver.getWebDriver().getCurrentUrl();
		softAssertion.assertEquals(Input.url + "DocumentViewer/DocView", currentUrl);
		base.stepInfo("Navigated to DocView Page : " + currentUrl);

		// Main method
		miniDocListPage = new MiniDocListPage(driver);
		base.waitForElement(miniDocListPage.getDocumentCountFromDocView());
		String sizeofList = miniDocListPage.getDocumentCountFromDocView().getText();
		String documentSize = sizeofList.substring(sizeofList.indexOf("of") + 2, sizeofList.indexOf("Docs")).trim();
		System.out.println("Size : " + documentSize);
		base.stepInfo("Available documents in DocView page : " + sizeofList);

		base.digitCompareEquals(aggregateHitCount, Integer.parseInt(documentSize),
				"Shows all documents that are in the aggregate results set of all child search groups and searches",
				"Count Mismatches with the Documents");

		// Search Term Report
		base.stepInfo("-----STR------");
		saveSearch.SavedSearchToTermReport_New(Input.mySavedSearch, false, null, searchPA, "No");
		driver.waitForPageToBeReady();
		searchTerm.verifySTRForSearchFromSSPage(newNodePA, searchPA);
		base.waitForElement(searchTerm.getHitsCount());
		int expectedHitsCount = Integer.parseInt(searchTerm.getHitsCount().getText());
		base.stepInfo("Aggregate Hit Count of searhces is :  " + expectedHitsCount);
		base.digitCompareEquals(aggregateHitCount, expectedHitsCount, "STR count matches", "Count matches");
		softAssertion.assertEquals(aggregateHitCount, expectedHitsCount);

		// Export
		base.stepInfo("-----EXPORT------");
		saveSearch.navigateToSavedSearchPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(5);
		saveSearch.verifyExportpopup(StyletoChoose, fieldTypeToChoose);

		// Navigating to saved search page
		saveSearch.navigateToSavedSearchPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch);
		saveSearch.getSavedSearchExecuteButton().waitAndClick(5);
		saveSearch.getExecuteContinueBtn().waitAndClick(5);

		base.stepInfo("To verify Pending Status By applying filter");
		saveSearch.getStatusDropDown().waitAndClick(2);
		saveSearch.getLastStatusAsPending().Click();
		saveSearch.getStatusDropDown().Click();
		saveSearch.getSavedSearch_ApplyFilterButton().Click();
		saveSearch.verifyExecutionStatusInSavedSearchPage("PENDING");

		base.stepInfo("To verify Completed Status By applying filter");
		saveSearch.getStatusDropDown().Click();
		saveSearch.getLastStatusAsCompleted().Click();
		saveSearch.getSavedSearch_ApplyFilterButton().Click();
		saveSearch.verifyExecutionStatusInSavedSearchPage("COMPLETED");

		base.stepInfo("To verify In Progrss Status By applying filter");
		saveSearch.getStatusDropDown().Click();
		saveSearch.getLastStatusAsInProgress().Click();
		saveSearch.getSavedSearch_ApplyFilterButton().Click();
		saveSearch.verifyExecutionStatusInSavedSearchPage("INPROGRESS");

		softAssertion.assertAll();

		login.logout();

	}

	/**
	 * @author Raghuram A Date: 12/23/21 Modified date:N/A Modified by:N/A
	 * @Description : SA impersonate down as RMU/RU role, create Searchgroups and
	 *              Searches, and then perform bulk actions against My saved
	 *              searches in PAU role -RPMXCON-57386 Sprint 08
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-57386", enabled = true, groups = { "regression" })
	public void performBulkActionsAgainstMySavedSearchesSA() throws InterruptedException {

		miniDocListPage = new MiniDocListPage(driver);
		searchTerm = new SearchTermReportPage(driver);

		String searchPA = "Search_PA_" + Utility.dynamicNameAppender();
		String searchPANode = "Search_PA_" + Utility.dynamicNameAppender();
		String searchRMU = "Search_RMU_" + Utility.dynamicNameAppender();
		String searchRMUNode = "Search_RMU_" + Utility.dynamicNameAppender();
		String searchREV = "Search_REV_" + Utility.dynamicNameAppender();
		String searchREVNode = "Search_REV_" + Utility.dynamicNameAppender();
		String[] searches = { searchPA, searchRMU, searchREV };
		String TagName = "Tag" + Utility.dynamicNameAppender();
		String folderName = "Folder" + Utility.dynamicNameAppender();

		int latencyCheckTime = 5;
		String passMessage = "Application not hang or shows latency more than " + latencyCheckTime + " seconds.";
		String failureMsg = "Continues Loading more than " + latencyCheckTime + " seconds.";
		String StyletoChoose = "CSV";
		String fieldTypeToChoose = "[,] 044";

		// Login as PA
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Loggedin As : " + Input.sa1userName);
		base.stepInfo("Test case Id: RPMXCON-57386 SavedSearch Sprint 08");
		base.stepInfo(
				"SA impersonate down as RMU/RU role, create Searchgroups and Searches, and then perform bulk actions against My saved searches in PAU role");

		base.rolesToImp("SA", "PA");

		String newNodePA = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "PA", "No");
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchPA);
		session.saveSearchInNewNode(searchPANode, newNodePA);
		base.stepInfo("Created searches and saved as PA");

		base.rolesToImp("PA", "RMU");

		String newNodeRMU = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", "No");
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchRMU);
		session.saveSearchInNewNode(searchRMUNode, newNodeRMU);
		base.stepInfo("Created searches and saved as RMU");

		base.rolesToImp("RMU", "REV");

		String newNodeREV = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "REV", "No");
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchREV);
		session.saveSearchInNewNode(searchREVNode, newNodeREV);
		base.stepInfo("Created searches and saved as REV");

		// base.rolesToImp("REV", "PA");
		base.impersonateSAtoPA();

		String[] searchNode = { newNodePA, newNodeRMU, newNodeREV };

		// Navigate to SavedSearch Page
		saveSearch.navigateToSSPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(2);

		// Verify Searches and Nodes
		base.stepInfo(
				"1. Search and Search group created with RMU or Reviewer role should not be available for PAU role ");
		base.stepInfo("2. Only Search groups and searches created by PAU role should be listed");
		saveSearch.searchesToVerify(true, searches, true, searchNode, null, null);

		// Get Count
		session.searchSavedSearchResult(Input.mySavedSearch);
		int aggregateHitCount = session.saveAndReturnPureHitCount();
		base.stepInfo("Aggregate Count : " + aggregateHitCount);
		System.out.println("Aggregate Count : " + aggregateHitCount);

		// Navigate to SavedSearch Page
		saveSearch.navigateToSavedSearchPage();

		base.stepInfo(
				"Select My Saved Search main folder and perform @Action and check for searches/document count against the action is performed.");

		base.selectproject();
		// TAG
		base.stepInfo("-----TAG------");
		HashMap<String, String> tagCount = saveSearch.bulkTagorFolderViaSS(true, true, Input.mySavedSearch, false,
				false, "", "", "Tag", 5, TagName, true, true, "", false, false);
		base.textCompareEquals(tagCount.get("PureHit Count"), Integer.toString(aggregateHitCount),
				"After the Bulk Tag - Pure hit appear like aggregate results set of all child search groups and searches  ",
				"Count Mismatches");
		base.selectproject();

		// FOLDER
		base.stepInfo("-----FOLDER------");
		HashMap<String, String> folderCount = saveSearch.bulkTagorFolderViaSS(true, true, Input.mySavedSearch, false,
				false, "", "", "Folder", 5, "", false, false, folderName, true, true);
		base.textCompareEquals(folderCount.get("PureHit Count"), Integer.toString(aggregateHitCount),
				"After the Bulk Folder - Pure hit shows the aggregate results set of all child search groups and searches ",
				"Count Mismatches");
		base.selectproject();

		base.stepInfo("-----DOC LIST------");
		// Navigate to SavedSearch Page
		saveSearch.navigateToSSPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(5);
		// Get the count of total no.of document list
		int finalCountresultDocList = saveSearch.launchDocListViaSSandReturnDocCount();

		base.stepInfo("-----DOC VIEW------");
		// Navigate to SavedSearch Page
		saveSearch.navigateToSavedSearchPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(5);
		saveSearch.docViewFromSS("View in DocView");

		// Load latency Verification
		Element loadingElement = session.getspinningWheel();
		saveSearch.loadingCountVerify(loadingElement, latencyCheckTime, passMessage, failureMsg);
		driver.waitForPageToBeReady();
		String currentUrl = driver.getWebDriver().getCurrentUrl();
		softAssertion.assertEquals(Input.url + "DocumentViewer/DocView", currentUrl);
		base.stepInfo("Navigated to DocView Page : " + currentUrl);

		// Main method
		miniDocListPage = new MiniDocListPage(driver);
		base.waitForElement(miniDocListPage.getDocumentCountFromDocView());
		String sizeofList = miniDocListPage.getDocumentCountFromDocView().getText();
		String documentSize = sizeofList.substring(sizeofList.indexOf("of") + 2, sizeofList.indexOf("Docs")).trim();
		System.out.println("Size : " + documentSize);
		base.stepInfo("Available documents in DocView page : " + sizeofList);

		base.digitCompareEquals(aggregateHitCount, Integer.parseInt(documentSize),
				"Shows all documents that are in the aggregate results set of all child search groups and searches",
				"Count Mismatches with the Documents");

		// Search Term Report
		base.stepInfo("-----STR------");
		saveSearch.SavedSearchToTermReport_New(Input.mySavedSearch, false, null, searchPA, "No");
		driver.waitForPageToBeReady();
		searchTerm.verifySTRForSearchFromSSPage(newNodePA, searchPA);
		base.waitForElement(searchTerm.getHitsCount());
		int expectedHitsCount = Integer.parseInt(searchTerm.getHitsCount().getText());
		base.stepInfo("Aggregate Hit Count of searhces is :  " + expectedHitsCount);
		base.digitCompareEquals(aggregateHitCount, expectedHitsCount, "STR count matches", "Count matches");
		softAssertion.assertEquals(aggregateHitCount, expectedHitsCount);

		// Export
		base.stepInfo("-----EXPORT------");
		saveSearch.navigateToSavedSearchPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(5);
		saveSearch.verifyExportpopup(StyletoChoose, fieldTypeToChoose);

		// Navigating to saved search page
		saveSearch.navigateToSavedSearchPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch);
		saveSearch.getSavedSearchExecuteButton().waitAndClick(5);
		saveSearch.getExecuteContinueBtn().waitAndClick(5);

		base.stepInfo("To verify Pending Status By applying filter");
		saveSearch.getStatusDropDown().waitAndClick(2);
		saveSearch.getLastStatusAsPending().Click();
		saveSearch.getStatusDropDown().Click();
		saveSearch.getSavedSearch_ApplyFilterButton().Click();
		saveSearch.verifyExecutionStatusInSavedSearchPage("PENDING");

		base.stepInfo("To verify Completed Status By applying filter");
		saveSearch.getStatusDropDown().Click();
		saveSearch.getLastStatusAsCompleted().Click();
		saveSearch.getSavedSearch_ApplyFilterButton().Click();
		saveSearch.verifyExecutionStatusInSavedSearchPage("COMPLETED");

		base.stepInfo("To verify In Progrss Status By applying filter");
		saveSearch.getStatusDropDown().Click();
		saveSearch.getLastStatusAsInProgress().Click();
		saveSearch.getSavedSearch_ApplyFilterButton().Click();
		saveSearch.verifyExecutionStatusInSavedSearchPage("INPROGRESS");

		softAssertion.assertAll();

		login.logout();

	}

	/**
	 * @author Raghuram.A Date: 12/24/21 Modified date:N/A Modified by:N/A
	 * @Description : Validate executing searches/groups from the shared with
	 *              <Security Group Name> by any other PAU user
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-49872", enabled = true, groups = { "regression" })
	public void validatingExecutedSavedsearchSearchesAndGroupsAsPauAndRmu() throws Exception {

		String searchName = "Search Name" + Utility.dynamicNameAppender();
		String searchName1 = "Search Name" + Utility.dynamicNameAppender();
		int noOfNodesToCreate = 2;
		List<String> newNodeList = new ArrayList<>();

		base.stepInfo("Test case Id: RPMXCON-49872 SavedSearch Sprint 08");
		base.stepInfo(
				"Validate executing searches/groups from the shared with <Security Group Name> by any other PAU user");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Multiple Node Creation
		saveSearch.navigateToSavedSearchPage();
		newNodeList = saveSearch.createSGAndReturn("PA", "No", noOfNodesToCreate);
		String parentNode = newNodeList.get(0);
		String childNode = newNodeList.get(1);

		// Adding searches to the created nodes
		session.navigateToSessionSearchPageURL();
		int pureHit = session.basicContentSearchWithSaveChanges(Input.searchString1, "Yes", "First");
		session.saveSearchInNewNode(searchName, parentNode);
		session.saveSearchInNodewithChildNode(searchName1, newNodeList.get(1), newNodeList);

		// Share the node with Default security group
		saveSearch.navigateToSavedSearchPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(3);
		saveSearch.rootGroupExpansion();
		saveSearch.shareSavedNodeToSG(Input.securityGroup, parentNode, searchName);

		// Executing the Search Group
		saveSearch.navigateToSavedSearchPage();
		saveSearch.getSavedSearchGroupName(Input.securityGroup).waitAndClick(10);
		saveSearch.selectNode1(parentNode);
		saveSearch.savedSearchExecute_SearchGRoup(searchName, pureHit);

		// verifying the Child Node present in the Node
		saveSearch.verifyNodePresentInSG(Input.shareSearchDefaultSG, parentNode);
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(5);
		saveSearch.getSharedGroupName(childNode).waitAndClick(10);
		base.passedStep(childNode + " : Is Present in " + parentNode);

		// Excecuting the Search
		driver.Navigate().refresh();
		saveSearch.getSavedSearchGroupName(Input.securityGroup).waitAndClick(10);
		saveSearch.selectNode1(parentNode);
		saveSearch.savedSearch_SearchandSelect(searchName, "Yes");
		saveSearch.savedSearchExecute_Draft(searchName, pureHit);

		Map<String, Integer> searchAndDocCountPair = new HashMap<String, Integer>();
		searchAndDocCountPair.put(searchName, (Integer) pureHit);

		login.logout();

		// login as another PAU and verifying the document count
		login.loginToSightLine(Input.pa2userName, Input.pa2password);
		base.stepInfo("Loggedin As : " + Input.pa2FullName);

		saveSearch.navigateToSavedSearchPage();
		saveSearch.getSavedSearchGroupName(Input.securityGroup).waitAndClick(10);
		saveSearch.selectNode1(newNodeList.get(0));
		saveSearch.verifyDocCountWithResult(searchAndDocCountPair);

		login.logout();

		// login as RMU and verifying the document count
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		saveSearch.navigateToSavedSearchPage();
		saveSearch.getSavedSearchGroupName(Input.securityGroup).waitAndClick(10);
		saveSearch.selectNode1(newNodeList.get(0));
		saveSearch.verifyDocCountWithResult(searchAndDocCountPair);

		// deleting the node
		saveSearch.deleteNode(Input.securityGroup, parentNode);
		softAssertion.assertAll();
		login.logout();
	}

	/**
	 * @author Raghuram A Date: 11/24/21 Modified date:N/A Modified by: Description
	 *         : Validate sharing specific search/group to Security Group that are
	 *         already shared -RPMXCON-49869 Sprint 08
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-49869", enabled = true, groups = { "regression" })
	public void validateSharingAlreadySharedSG() throws InterruptedException, ParseException {
		int noOfNodesToCreate = 6;
		int selectIndex = 0;
		String SGtoShare = Input.shareSearchDefaultSG;
		String node;
		Boolean inputValue = true;
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		HashMap<String, String> searchGroupSearchpIDpair = new HashMap<>();
		HashMap<String, String> searchGroupSearchpIDpair2 = new HashMap<>();

		base.stepInfo("Test case Id: RPMXCON-49869 - Saved Search Sprint 08");
		base.stepInfo("VValidate sharing specific search/group to Security Group that are already shared");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Landed on Saved Search
		saveSearch.navigateToSSPage();
		// Multiple Node Creation
		newNodeList = saveSearch.createSGAndReturn("PA", "No", noOfNodesToCreate);
		System.out.println("Node creation is done followed by adding searches to the created nodes");
		base.stepInfo("Node creation is done followed by adding searches to the created nodes");

		// Adding searches to the created nodes
		driver.getWebDriver().get(Input.url + "Search/Searches");
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);
		saveSearch.sortedMapList(nodeSearchpair);

		// Search ID collection set 1
		saveSearch.navigateToSSPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(3);
		saveSearch.rootGroupExpansion();

		searchGroupSearchpIDpair = saveSearch.collectionOfSearchIdsFromNodeCollections(newNodeList, nodeSearchpair,
				searchGroupSearchpIDpair);

		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(3);
		node = saveSearch.childNodeSelectionToShare(selectIndex, newNodeList);
		System.out.println("Final : " + node);
		saveSearch.shareSavedNodePA(SGtoShare, node, false, true, nodeSearchpair.get(node));
		driver.Navigate().refresh();
		saveSearch.verifyImpactinSharedchildNodes(SGtoShare, newNodeList, selectIndex, nodeSearchpair,
				searchGroupSearchpIDpair);

		base.stepInfo("-------Pre-requesties completed--------");

		// Search ID collection set 2
		saveSearch.getSavedSearchGroupName(SGtoShare).waitAndClick(3);

		searchGroupSearchpIDpair2 = saveSearch.collectionOfSearchIdsFromNodeCollections(newNodeList, nodeSearchpair,
				searchGroupSearchpIDpair);

		// Verify shared SG/Searches
		saveSearch.navigateToSSPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(3);
		node = saveSearch.childNodeSelectionToShare(selectIndex, newNodeList);
		System.out.println("Final : " + node);
		saveSearch.shareSavedNodePA(SGtoShare, node, false, true, nodeSearchpair.get(node));
		base.stepInfo("ID verfication between shared searches");
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		saveSearch.verifyImpactinSharedchildNodes(SGtoShare, newNodeList, selectIndex, nodeSearchpair,
				searchGroupSearchpIDpair2);

		login.logout();

		// Login as PA2 and Verify shared SG/Searches as different User
		login.loginToSightLine(Input.pa2userName, Input.pa2password);
		base.stepInfo("Loggedin As : " + Input.pa2FullName);
		// Landed on Saved Search
		saveSearch.navigateToSSPage();
		saveSearch.verifyImpactinSharedchildNodes(SGtoShare, newNodeList, selectIndex, nodeSearchpair,
				searchGroupSearchpIDpair2);
		base.stepInfo("Verified Shared SG and Searches");

		login.logout();

		// Login as PA2 and Verify shared SG/Searches as different User
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);
		// Landed on Saved Search
		saveSearch.navigateToSSPage();
		saveSearch.verifyImpactinSharedchildNodes(SGtoShare, newNodeList, selectIndex, nodeSearchpair,
				searchGroupSearchpIDpair2);
		base.stepInfo("Verified Shared SG and Searches");

		login.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that application displays all documents that are in the
	 *              aggregate results set of "Shared With Project
	 *              Administrator/Shared With Default Security Group" and User
	 *              performs Execute option with Search groups [RPMXCON-49006]
	 * @param username
	 * @param password
	 * @param fullname
	 * @param SGtoShare
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-49006", enabled = true, dataProvider = "UserAndShare", groups = { "regression" })
	public void verifyAppDispalysAggregateResult(String username, String password, String fullname, String SGtoShare)
			throws InterruptedException {
		int noOfNodesToCreate = 2;
		int selectIndex = 0;
		String node;
		Boolean inputValue = true;
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();

		// Login as PA
		login.loginToSightLine(username, password);

		base.stepInfo("Test case Id: RPMXCON-49006 - Saved Search Sprint 08");
		base.stepInfo(
				"Verify that application displays all documents that are in the aggregate results set of \"Shared With Project Administrator/Shared With Default Security Group\" and User performs Execute option with Search groups");

		// Multiple Node Creation
		saveSearch.navigateToSSPage();
		newNodeList = saveSearch.createSGAndReturn("PA", "No", noOfNodesToCreate);

		// Adding searches to the created nodes
		driver.getWebDriver().get(Input.url + "Search/Searches");
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);
		saveSearch.sortedMapList(nodeSearchpair);

		// verify Searches in child nodes and share to SG
		saveSearch.navigateToSSPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(3);
		node = saveSearch.childNodeSelectionToShare(selectIndex, newNodeList);
		System.out.println("Final : " + node);
		saveSearch.shareSavedNodePA(SGtoShare, node, false, true, nodeSearchpair.get(node));

		// Verify Search Status And Count in all nodes
		saveSearch.verifyStatusAndCountInAllChildNode(SGtoShare, newNodeList, selectIndex, nodeSearchpair);

		// Delete Node
		saveSearch.deleteNode(Input.mySavedSearch, newNodeList.get(0));
		saveSearch.deleteNode(SGtoShare, newNodeList.get(0));

		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that user can run any of the actions(DocList/Share),
	 *              when the saved search is in Complete state.[RPMXCON-48911]
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-48911", enabled = true, groups = { "regression" })
	public void verifyAppDispalysAggregateResult() throws InterruptedException, ParseException {
		String Search1 = "search" + Utility.dynamicNameAppender();
		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		base.stepInfo("Test case Id: RPMXCON-48911  Saved Search");
		base.stepInfo(
				"Verify that user can run any of the actions(DocList/Share), when the saved search is in Complete state.");

		// Basic Search
		session.navigateToSessionSearchPageURL();
		session.basicContentSearchWithSaveChanges(Input.searchString1, "No", "First");
		session.getSecondSearchBtn().waitAndClick(5);
		session.handleWhenAllResultsBtnInUncertainPopup();
		int purehit = session.returnPurehitCount();
		session.saveSearch(Search1);

		// Get Completed Status
		saveSearch.savedSearch_Searchandclick(Search1);
		driver.waitForPageToBeReady();
		String searchStatus = saveSearch.getSavedSearchStatus(Search1).getText();
		base.stepInfo(Search1 + " Last Status is : " + searchStatus);
		softAssertion.assertEquals(searchStatus, "COMPLETED");

		// perform share Action
		saveSearch.shareSavedSearchRMU(Search1, Input.shareSearchDefaultSG);

		// Delete search
		saveSearch.deleteSearch(Search1, Input.mySavedSearch, "Yes");
		softAssertion.assertAll();
		login.logout();

	}

	/**
	 * @AUthor Jeevitha
	 * @Description : Verify that BLANK \"Count\" gets display in conceptual Column
	 *              in Saved Search Screen when user saved a Background Basic search
	 *              Query [RPMXCON-48487]
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-48487", enabled = true, groups = { "regression" })
	public void verifyConceptualCountAsBlank() throws InterruptedException, ParseException {
		String Search1 = "search" + Utility.dynamicNameAppender();
		String conceptually = "Conceptually Similar Count";

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		base.stepInfo("Test case Id: RPMXCON-48487  Saved Search");
		base.stepInfo(
				"Verify that BLANK \"Count\" gets display in conceptual Column in Saved Search Screen when user saved a Background Basic search Query");

		// Basic Search
		session.navigateToSessionSearchPageURL();
		session.basicContentSearchWithSaveChanges(Input.searchString1, "No", "First");
		session.getSecondSearchBtn().waitAndClick(5);
		session.handleWhenAllResultsBtnInUncertainPopup();
		int purehit = session.returnPurehitCount();
		session.saveSearch(Search1);

		// Verify Conceptuall Column
		saveSearch.savedSearch_Searchandclick(Search1);
		String Count = saveSearch.ApplyShowAndHideFilter(conceptually, Search1);
		softAssertion.assertEquals(Count, "");
		softAssertion.assertAll();
		base.stepInfo("Conceptual Column Count is BLANK");

		// Delete Searche
		saveSearch.deleteSearch(Search1, Input.mySavedSearch, "Yes");

		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that \"Count\" and status gets updated in conceptual
	 *              column in Saved Search Screen when user Execute a Query with
	 *              Execute option from Saved Search [RPMXCON-48489]
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-48489", enabled = true, groups = { "regression" })
	public void verifyConceptualCountAfterExecute() throws InterruptedException, ParseException {
		String Search1 = "search" + Utility.dynamicNameAppender();
		String nearDupe = "Near Duplicate Count";
		String conceptually = "Conceptually Similar Count";

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		base.stepInfo("Test case Id: RPMXCON-48489  Saved Search");
		base.stepInfo(
				"Verify that \"Count\" and status gets updated in conceptual column in Saved Search Screen when user Execute a Query with Execute option from Saved Search");

		// Basic Search
		session.navigateToSessionSearchPageURL();
		session.basicContentSearchWithSaveChanges(Input.searchString1, "No", "First");
		session.getSecondSearchBtn().waitAndClick(5);
		session.handleWhenAllResultsBtnInUncertainPopup();
		int purehit = session.returnPurehitCount();
		session.saveSearch(Search1);

		// Verify Conceptuall Column
		saveSearch.savedSearchExecute(Search1, purehit);
		saveSearch.getDocCountAndStatusOfBatch(Search1, nearDupe, true);
		String Count = saveSearch.ApplyShowAndHideFilter(conceptually, Search1);
		softAssertion.assertNotEquals(Count, "");
		softAssertion.assertAll();
		base.stepInfo("Conceptual Column Count is Updated");

		// Delete Searche
		saveSearch.deleteSearch(Search1, Input.mySavedSearch, "Yes");

		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that Scheduler works properly on saved Search Screen
	 *              [RPMXCON-49034]
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-49034", enabled = true, groups = { "regression" })
	public void verifySchedulerWorks() throws InterruptedException, ParseException {
		String Search = "search" + Utility.dynamicNameAppender();

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		base.stepInfo("Test case Id: RPMXCON-49034  Saved Search");
		base.stepInfo("Verify that Scheduler works properly on saved Search Screen");

		// Save search in Node
		String node = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, Input.pa1FullName, "No");
		session.basicContentSearch(Input.searchString1);
		session.saveSearchInNewNode(Search, node);

		// verify schedule Btn
		saveSearch.navigateToSavedSearchPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(3);
		saveSearch.selectNode1(node);
		saveSearch.savedSearch_SearchandSelect(Search, "Yes");
		driver.waitForPageToBeReady();
		Element scheduleBtnStatus = saveSearch.getSavedSearch_ScheduleButton();
		saveSearch.checkButtonEnabled(scheduleBtnStatus, "Should be Enabled", "Schedule");

		// Perform Schedule Action
		saveSearch.scheduleSavedSearchInMinute(Search, 1);

		// Delete Search
		saveSearch.deleteNode(Input.mySavedSearch, node);

		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that Assignments Functionality works properly on Saved
	 *              Search Screen [RPMXCON-49035]
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-49035", enabled = true, groups = { "regression" })
	public void verifyAssignmentFunctionality() throws InterruptedException, ParseException {
		String Search = "search" + Utility.dynamicNameAppender();
		String assignment = "Assign" + Utility.dynamicNameAppender();
		AssignmentsPage assgnpage = new AssignmentsPage(driver);

		int noOfNodesToCreate = 3;
		int selectIndex = 0;
		String node;
		Boolean inputValue = true;
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();

		// Login as PA
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		base.stepInfo("Test case Id: RPMXCON-49035  Saved Search");
		base.stepInfo("Verify that Assignments Functionality works properly on Saved Search Screen");

		// Multiple Node Creation
		saveSearch.navigateToSSPage();
		newNodeList = saveSearch.createSGAndReturn("PA", "No", noOfNodesToCreate);

		// Adding searches to the created nodes
		session.navigateToSessionSearchPageURL();
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);
		saveSearch.sortedMapList(nodeSearchpair);

		// get Purehit count of All child Search groups
		session.selectSavedsearchInASWp(newNodeList.get(0));
		int pureHit = session.saveAndReturnPureHitCount();
		System.out.println(pureHit);
		driver.scrollPageToTop();

		// verify Searches in child nodes and share to SG
		saveSearch.navigateToSSPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(3);
		node = saveSearch.childNodeSelectionToShare(selectIndex, newNodeList);

		// verify Assign btn
		driver.waitForPageToBeReady();
		Element assignBtnStatus = saveSearch.getSavedSearchToBulkAssign();
		saveSearch.checkButtonEnabled(assignBtnStatus, "Should be Enabled", "Assign");

		// Perform Bulk Assign
		saveSearch.getSavedSearchToBulkAssign().waitAndClick(10);
		assgnpage.FinalizeAssignmentAfterBulkAssign();
		assgnpage.createAssignment_fromAssignUnassignPopup(assignment, Input.codeFormName);
		assgnpage.getAssignmentSaveButton().waitAndClick(5);
		base.stepInfo("Created a assignment " + assignment);

		// delete Created Assignment
		assgnpage.deleteAssgnmntUsingPagination(assignment);
		// Delete Node
		saveSearch.deleteNode(Input.mySavedSearch, newNodeList.get(0));

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 12/27/21 Modified date:N/A Modified by: Description
	 *         : Verify that application displays all documents that are in the
	 *         aggregate results set of all child search groups "My Saved Search"
	 *         and searches when User Performs Bulk Folder from Child Search
	 *         groups-RPMXCON-48922 Sprint 09
	 * @throws InterruptedException
	 * @throws ParseException
	 */

	@Test(description = "RPMXCON-48922", enabled = true, groups = { "regression" })
	public void aggregateResultFromNodeWhileBulkFolder() throws InterruptedException, ParseException {
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		Boolean inputValue = true;
		int aggregateHitCount, noOfNode = 3;
		String folderName = "Folder" + Utility.dynamicNameAppender();
		base.stepInfo("Test case Id: RPMXCON-48922 - Saved Search Sprint 09");
		base.stepInfo(
				"Verify that application displays all documents that are in the aggregate results set of all child search groups \"My Saved Search\" and searches when User Performs Bulk Folder from Child Search groups");

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

		base.stepInfo("-------Pre-requesties completed--------");
		login.logout();

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Calculate the unique doc count for the respective searches
		aggregateHitCount = session.getDocCountBtwnTwoSearches(true, Input.searchString5, Input.searchString6);
		base.selectproject();

		// FOLDER
		base.stepInfo("-----FOLDER------");
		HashMap<String, String> folderCount = saveSearch.bulkTagorFolderViaSS(true, true, Input.mySavedSearch, true,
				false, newNodeList.get(0), "", "Folder", 5, "", false, false, folderName, true, true);

		base.stepInfo(" aggregateHitCount " + aggregateHitCount);
		base.stepInfo(" Finalize Count " + folderCount.get("Finalize Count"));
		base.textCompareEquals(folderCount.get("Finalize Count"), Integer.toString(aggregateHitCount),
				"Document finalize count matches with aggregate results set of all child search groups and searches   ",
				"Count Mismatches");

		base.stepInfo(" Finalize Count " + folderCount.get("Finalize Count"));
		base.stepInfo(" PureHit Count " + folderCount.get("PureHit Count"));
		base.textCompareEquals(folderCount.get("PureHit Count"), folderCount.get("Finalize Count"),
				"After the Bulk Folder - Pure hit shows the aggregate results set of all child search groups and searches  ",
				"Count Mismatches");

		// Delete Node
		saveSearch.deleteNode(Input.mySavedSearch, newNodeList.get(0), true, true);

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 12/27/21 Modified date:N/A Modified by: Description
	 *         : Verify that application displays all documents that are in the
	 *         aggregate results set of all child search groups "My Saved Search"
	 *         and searches when User Performs Bulk Assign from Child Search groups
	 *         -RPMXCON-48923 Sprint 09
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-48923", enabled = true, groups = { "regression" })
	public void aggregateResultWhileBulkAssignment() throws InterruptedException, ParseException {
		AssignmentsPage assign = new AssignmentsPage(driver);

		int latencyCheckTime = 5;
		String passMessage = "Application not hang or shows latency more than " + latencyCheckTime + " seconds.";
		String failureMsg = "Continues Loading more than " + latencyCheckTime + " seconds.";
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		Boolean inputValue = true;
		int finalCountresult, noOfNode = 3, purehitCount, aggregateHitCount;
		String finalCount;
		String assignName = "assignName" + Utility.dynamicNameAppender();
		base.stepInfo("Test case Id: RPMXCON-48923 - Saved Search Sprint 09");
		base.stepInfo(
				"Verify that application displays all documents that are in the aggregate results set of all child search groups \"My Saved Search\" and searches when User Performs Bulk Assign from Child Search groups");

		// Login as PA
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// Navigate on Saved Search & Multiple Node Creation & save search in node
		saveSearch.navigateToSSPage();
		newNodeList = saveSearch.createSGAndReturn("RMU", "No", noOfNode);
		System.out.println("Next adding searches to the created nodes");
		base.stepInfo("Next adding searches to the created nodes");
		driver.getWebDriver().get(Input.url + "Search/Searches");
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);

		base.stepInfo("-------Pre-requesties completed--------");
		login.logout();

		// Login as PA
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Calculate the unique doc count for the respective searches
		aggregateHitCount = session.getDocCountBtwnTwoSearches(true, Input.searchString5, Input.searchString6);
		base.selectproject();

		// Launch DocVia via Saved Search
		saveSearch.navigateToSSPage();
		base.stepInfo("Root node selected : " + newNodeList.get(0));
		saveSearch.selectNode1(newNodeList.get(0));

		// verify Assign Button Enabled
		driver.waitForPageToBeReady();
		Element assignBtnStatus = saveSearch.getSavedSearchToBulkAssign();
		saveSearch.checkButtonEnabled(assignBtnStatus, "Should be Enabled", "Assign");

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

		base.stepInfo("Aggregate Hit count : " + aggregateHitCount);
		base.stepInfo("Finalize count : " + finalCountresult);
		base.digitCompareEquals(finalCountresult, aggregateHitCount,
				"Finalize hit appears like aggregate results set of all child search groups and searches   ",
				"Count Mismatches");

		base.stepInfo("Finalize count : " + finalCountresult);
		base.stepInfo("PureHit count : " + purehitCount);
		base.digitCompareEquals(finalCountresult, purehitCount,
				"After the Bulk Assignment - Pure hit appears like aggregate results set of all child search groups and searches   ",
				"Count Mismatches");

		// Delete Node
		driver.scrollPageToTop();
		saveSearch.deleteNode(Input.mySavedSearch, newNodeList.get(0), true, true);

		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : To Verify, In saved search page, \"Submit\" button should be
	 *              renamed to \"Execute\" [RPMXCON- 47387]
	 * @param username
	 * @param password
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-47387", enabled = true, dataProvider = "AllTheUsers", groups = { "regression" })
	public void verifySubmitBtnRenamedAsExecute(String username, String password) {

		// Login as PA
		login.loginToSightLine(username, password);

		base.stepInfo("Test case Id: RPMXCON-47387 - Saved Search Sprint 09");
		base.stepInfo("To Verify, In saved search page, \"Submit\" button should be renamed to \"Execute\"");

		// verify Execute Action
		saveSearch.navigateToSavedSearchPage();
		driver.waitForPageToBeReady();
		String displayedName = saveSearch.getSavedSearchExecuteButton().getText();
		softAssertion.assertNotEquals(displayedName, "Submit");
		softAssertion.assertEquals(displayedName, "Execute");
		System.out.println(displayedName + " : is Displayed in Action");
		base.stepInfo(displayedName + " : is Displayed in Action");

		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description :Verify that After adding Saved Query - application displays all
	 *              documents that are in the aggregate results - when User Navigate
	 *              Child Search groups to Report [RPMXCON-49067]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-49067", enabled = true, groups = { "regression" })
	public void verifyAfterAddingSavedQuery() throws InterruptedException {
		int noOfNodesToCreate = 3;
		int selectIndex = 0;
		String node;
		Boolean inputValue = true;
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		SearchTermReportPage str = new SearchTermReportPage(driver);

		// Login as PA
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		base.stepInfo("Test case Id: RPMXCON-49067 - Saved Search Sprint 08");
		base.stepInfo(
				"Verify that After adding Saved Query - application displays all documents that are in the aggregate results - when User Navigate Child Search groups to Report");

		// Multiple Node Creation
		saveSearch.navigateToSSPage();
		newNodeList = saveSearch.createSGAndReturn("PA", "No", noOfNodesToCreate);

		// Adding searches to the created nodes
		session.navigateToSessionSearchPageURL();
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);
		saveSearch.sortedMapList(nodeSearchpair);
		String search = nodeSearchpair.get(newNodeList.get(0));

		base.selectproject();
		int aggregateHitCount = session.getDocCountBtwnTwoSearches(true, Input.searchString5, Input.searchString6);
		System.out.println(aggregateHitCount);

		// verify Searches in child nodes and share to SG
		saveSearch.SavedSearchToTermReport_New(Input.mySavedSearch, true, newNodeList.get(0), search, "No");
		str.verifyaggregateCount("HITS");
		base.waitForElement(str.getHitsCount());
		int expectedHitsCount = Integer.parseInt(str.getHitsCount().getText());
		System.out.println(expectedHitsCount);
		base.stepInfo("Aggregate Hit Count of searhces is :  " + expectedHitsCount);
		softAssertion.assertEquals(aggregateHitCount, expectedHitsCount);
		softAssertion.assertAll();

		// Delete Node
		saveSearch.deleteNode(Input.mySavedSearch, newNodeList.get(0));
		login.logout();
	}

	/**
	 * @author Raghuram A Date: 12/27/21 Modified date:N/A Modified by: Description
	 *         : Verify that application displays all documents that are in the
	 *         aggregate results set of all child search groups "My Saved Search"
	 *         and searches when User Navigate Child Search groups to DocList
	 *         -RPMXCON-48916 Sprint 09
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-48916", enabled = true, groups = { "regression" })
	public void aggregateResultWhileDocListAction() throws InterruptedException, ParseException {

		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		Boolean inputValue = true;
		int finalCountresult, noOfNode = 3, aggregateHitCount;
		base.stepInfo("Test case Id: RPMXCON-48916 - Saved Search Sprint 09");
		base.stepInfo(
				"Verify that application displays all documents that are in the aggregate results set of all child search groups \"My Saved Search\" and searches when User Navigate Child Search groups to DocList");

		// Login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// Navigate on Saved Search & Multiple Node Creation & save search in node
		saveSearch.navigateToSSPage();
		newNodeList = saveSearch.createSGAndReturn("RMU", "No", noOfNode);
		System.out.println("Next adding searches to the created nodes");
		base.stepInfo("Next adding searches to the created nodes");
		driver.getWebDriver().get(Input.url + "Search/Searches");
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);

		base.stepInfo("-------Pre-requesties completed--------");
		login.logout();

		// Login as PA
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Calculate the unique doc count for the respective searches
		aggregateHitCount = session.getDocCountBtwnTwoSearches(true, Input.searchString5, Input.searchString6);
		base.selectproject();

		// Launch DocVia via Saved Search
		saveSearch.navigateToSSPage();
		base.stepInfo("Root node selected : " + newNodeList.get(0));
		saveSearch.selectNode1(newNodeList.get(0));

		// verify Assign Button Enabled
		driver.waitForPageToBeReady();
		Element docListBtnStatus = saveSearch.getDocListIcon();
		saveSearch.checkButtonEnabled(docListBtnStatus, "Should be Enabled", "DocList");

		// Get the count of total no.of document list
		finalCountresult = saveSearch.launchDocListViaSSandReturnDocCount();

		base.stepInfo("Aggregate Hit count : " + aggregateHitCount);
		base.stepInfo("Finalize count : " + finalCountresult);
		base.digitCompareEquals(finalCountresult, aggregateHitCount,
				"Shows all documents that are in the aggregate results set of all child search groups and searches",
				"Count Mismatches");

		// Delete Node
		driver.scrollPageToTop();
		saveSearch.deleteNode(Input.mySavedSearch, newNodeList.get(0), true, true);

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 12/27/21 Modified date:N/A Modified by: Description
	 *         : Verify that user is not able to save a session search onto an
	 *         existing saved search that is progress. RPMXCON-48913 Sprint 09
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-48913", enabled = true, groups = { "regression" })
	public void executionErrorInProgress() throws InterruptedException, ParseException {

		String savedSearchName = "Search Name" + UtilityLog.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-48913 - Saved Search Sprint 09");
		base.stepInfo(
				"Verify that user is not able to save a session search onto an existing saved search that is progress.");
		base.stepInfo("Flow can only be done for inputs with bulk data");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		int pureHit = session.basicContentSearch(Input.searchStringStar);
		session.saveSearch(savedSearchName);
		base.selectproject();
		session.basicContentSearch(Input.testData1);

		// Execute
		base.stepInfo("Select an existing saved search that is progress and try to Save it");
		saveSearch.savedSearch_Searchandclick(savedSearchName);
		saveSearch.getSavedSearchExecuteButton().Click();

		// Verify Overwrite
		session.navigateToSessionSearchPageURL();
		session.saveAsOverwrittenSearch(Input.mySavedSearch, savedSearchName, "First", "ExecutionErrorInProgress", "",
				null);

		// Delete Search
		saveSearch.deleteSearch(savedSearchName, Input.mySavedSearch, "Yes");

		login.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that \"Count\" gets updated in conceptual column in
	 *              Saved Search Screen when user Execute a Query with Execute
	 *              option from Saved Search [RPMXCON-48910]
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-48910", enabled = true, groups = { "regression" })
	public void verifyConceptualCountForBSAfterExecute() throws InterruptedException, ParseException {
		String Search = "search" + Utility.dynamicNameAppender();
		String conceptually = "Conceptually Similar Count";

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		base.stepInfo("Test case Id: RPMXCON-48910  Saved Search");
		base.stepInfo(
				"Verify that \"Count\" gets updated in conceptual column in Saved Search Screen when user Execute a Query with Execute option from Saved Search");

		// Basic Search
		session.navigateToSessionSearchPageURL();
		session.basicContentSearchWithSaveChanges(Input.searchString1, "No", "First");
		session.getSecondSearchBtn().waitAndClick(5);
		session.handleWhenAllResultsBtnInUncertainPopup();
		int purehit = session.returnPurehitCount();
		session.saveSearch(Search);

		// Verify Conceptual Column
		saveSearch.savedSearchExecute(Search, purehit);
		saveSearch.savedSearch_SearchandSelect(Search, "Yes");
		String Count = saveSearch.ApplyShowAndHideFilter(conceptually, Search);
		softAssertion.assertNotEquals(Count, "");
		softAssertion.assertAll();
		base.stepInfo("Conceptual Column Count is Updated");

		// Delete Search
		saveSearch.deleteSearch(Search, Input.mySavedSearch, "Yes");

		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that when batch upload is done with Redaction Labels
	 *              then count should be displayed correctly on saved search after
	 *              Search completes [RPMXCON-49091]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-49091", enabled = true, dataProvider = "PaAndRmuUser", groups = { "regression" })
	public void validateRedactionTagSearchViaBatchUpload(String username, String password) throws Exception {
		String search = "WP With RedactionLabel";
		String file = saveSearch.renameFile(Input.batchFileNewLocation);

		// Login as PA
		login.loginToSightLine(username, password);
		base.stepInfo("Test case Id: RPMXCON-49091");
		base.stepInfo(
				"Verify that when batch upload is done with Redaction Labels then count should be displayed correctly on saved search after Search completes");

		// workproduct search radaction tag purehit count
		session.switchToWorkproduct();
		session.selectRedactioninWPS(Input.defaultRedactionTag);
		int purehit = session.saveAndReturnPureHitCount();

		// Upload Error Query Through Batch File
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.uploadWPBatchFile_New(file, Input.batchFileNewLocation);
		base.waitTime(10);
		saveSearch.selectSavedSearch(search);
		base.waitForElement(saveSearch.getSavedSearchCount(search));
		String countOfDocs = saveSearch.getSavedSearchCount(search).getText();
		int docCount = Integer.parseInt(countOfDocs);
		base.stepInfo(docCount + " : is the Count of Doc");
		softAssertion.assertEquals(docCount, purehit);

		String status = saveSearch.getSavedSearchStatus(search).getText();
		base.stepInfo(status + "  is the Status Displayed");
		softAssertion.assertEquals(status, "COMPLETED");

		// Delete Uploaded File
		saveSearch.deleteUploadedBatchFile(file, Input.mySavedSearch, false, null);
		softAssertion.assertAll();

		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description :Verify that application displays all documents that are in the
	 *              aggregate results set of \"Default Security Group\" and when
	 *              User performs Execute option with Search groups[RPMXCON-49015]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-49015", enabled = true, groups = { "regression" })
	public void performExecuteWithSearchGroup() throws InterruptedException {
		int noOfNodesToCreate = 4;
		int selectIndex = 0;
		String SGtoShare = Input.shareSearchDefaultSG;
		String node;
		Boolean inputValue = true;
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("Test case Id: RPMXCON-49015 - Saved Search Sprint 09");
		base.stepInfo(
				"Verify that application displays all documents that are in the aggregate results set of \"Default Security Group\" and when User performs Execute option with Search groups");

		// Multiple Node Creation
		saveSearch.navigateToSSPage();
		newNodeList = saveSearch.createSGAndReturn("PA", "No", noOfNodesToCreate);

		// Adding searches to the created nodes
		driver.getWebDriver().get(Input.url + "Search/Searches");
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);
		saveSearch.sortedMapList(nodeSearchpair);
		String search = nodeSearchpair.get(newNodeList.get(0));

		// verify Searches in child nodes and share to SG
		saveSearch.navigateToSSPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(3);
		saveSearch.rootGroupExpansion();
		node = saveSearch.childNodeSelectionToShare(selectIndex, newNodeList);
		System.out.println("Final : " + node);
		saveSearch.shareSavedNodePA(SGtoShare, node, false, true, nodeSearchpair.get(node));

		// Execute
		driver.Navigate().refresh();
		saveSearch.getSavedSearchGroupName(Input.shareSearchDefaultSG).waitAndClick(10);
		saveSearch.performExecute();
		driver.waitForPageToBeReady();
		// Verify Search Status And Count in all nodes
		saveSearch.verifyStatusAndCountInAllChildNode(SGtoShare, newNodeList, selectIndex, nodeSearchpair);

		// Delete Node
		saveSearch.deleteNode(Input.mySavedSearch, newNodeList.get(0));
		saveSearch.deleteNode(SGtoShare, newNodeList.get(0));

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 12/29/21 Modified date:N/A Modified by: Description
	 *         : Verify that application displays all documents that are in the
	 *         aggregate results set of shared groups and when User performs Refresh
	 *         with Search groups (RPMXCON-49018 Sprint-09)
	 * @throws InterruptedException
	 * @throws ParseException
	 */

	@Test(description = "RPMXCON-49018", enabled = true, groups = { "regression" })
	public void showHideFieldsWithSharedSG() throws InterruptedException, ParseException {

		String basicSearchName = "comments" + Utility.dynamicNameAppender();
		String specificHeaderName = "Search Name";
		List<String> headerList1 = new ArrayList<>();
		List<String> headerList2 = new ArrayList<>();

		base.stepInfo("Test case Id: RPMXCON-49018 - Saved Search Sprint 09");
		base.stepInfo(
				"Verify that application displays all documents that are in the aggregate results set of shared groups and when User performs Refresh with Search groups");

		// Login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// Navigate on Saved Search & Multiple Node Creation & save search in node
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(basicSearchName);

		saveSearch.shareSearchFlow(basicSearchName, Input.shareSearchDefaultSG, "RMU");

		saveSearch.navigateToSSPage();
		saveSearch.getSavedSearchGroupName(Input.shareSearchDefaultSG).waitAndClick(3);

		ElementCollection headerListElementsBefore = saveSearch.getGridHeaderListSS();
		headerList1 = base.availableListofElements(headerListElementsBefore);
		Collections.sort(headerList1);
		base.stepInfo(headerList1.toString());

		saveSearch.methodTocheckHideandShowFunction(specificHeaderName);

		driver.waitForPageToBeReady();
		ElementCollection headerListElementsAfter = saveSearch.getGridHeaderListSS();
		headerList2 = base.availableListofElements(headerListElementsAfter);
		Collections.sort(headerList2);
		base.stepInfo(headerList2.toString());

		base.listCompareEquals(headerList1, headerList2, "all default columns are listed", "Mismatch with headers");

		// Delete Search
		saveSearch.deleteSearch(basicSearchName, Input.shareSearchDefaultSG, "Yes");

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 12/29/21 Modified date:N/A Modified by: Description
	 *         : Verify that application displays all documents that are in the
	 *         aggregate results set of all child search groups "My Saved Search"
	 *         and searches when User Performs Bulk Assign from Child Search groups
	 *         -RPMXCON-49017 Sprint 09
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-49017", enabled = true, groups = { "regression" })
	public void aggregateResultWhileBulkAssignmentSG() throws InterruptedException, ParseException {
		AssignmentsPage assign = new AssignmentsPage(driver);

		String basicSearchName = "BS_" + Utility.dynamicNameAppender();
		String passMsg = "Selected folder is highlighted";
		String failMsg = "Selected folder is Not highlighted / highlighted with wrong color code";

		int latencyCheckTime = 5;
		String passMessage = "Application not hang or shows latency more than " + latencyCheckTime + " seconds.";
		String failureMsg = "Continues Loading more than " + latencyCheckTime + " seconds.";
		int finalCountresult, purehitCount, aggregateHitCount;
		String finalCount;
		String assignName = "assignName" + Utility.dynamicNameAppender();
		base.stepInfo("Test case Id: RPMXCON-49017 - Saved Search Sprint 09");
		base.stepInfo(
				"Verify that application displays all documents that are in the aggregate results set of all child search groups \"My Saved Search\" and searches when User Performs Bulk Assign from Child Search groups");

		// Login as RMZU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// Navigate on Saved Search & Multiple Node Creation & save search in node
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(basicSearchName);

		saveSearch.shareSearchFlow(basicSearchName, Input.shareSearchDefaultSG, "RMU");

		base.stepInfo("-------Pre-requesties completed--------");
		login.logout();

		// Login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Get Count
		session.searchSavedSearchResult(Input.shareSearchDefaultSG);
		aggregateHitCount = session.saveAndReturnPureHitCount();
		base.stepInfo("Aggregate Count from Default Security Group: " + aggregateHitCount);
		System.out.println("Aggregate Count from Default Security Group : " + aggregateHitCount);
		base.selectproject();

		// Launch DocVia via Saved Search
		saveSearch.navigateToSavedSearchPage();
		saveSearch.getSavedSearchGroupName(Input.shareSearchDefaultSG).waitAndClick(3);

		base.stepInfo("Selected : " + Input.shareSearchDefaultSG);
		driver.waitForPageToBeReady();
		String bgColor = saveSearch.getSavedSearchGroupName(Input.shareSearchDefaultSG).GetCssValue("background-color");
		bgColor = base.rgbTohexaConvertor(bgColor);

		// Text Comparision
		base.textCompareEquals(bgColor, Input.selectionHighlightColor, passMsg, failMsg);

		// verify Assign Button Enabled
		driver.waitForPageToBeReady();
		Element assignBtnStatus = saveSearch.getSavedSearchToBulkAssign();
		saveSearch.checkButtonEnabled(assignBtnStatus, "Should be Enabled", "Assign");

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

		base.stepInfo("Aggregate Hit count : " + aggregateHitCount);
		base.stepInfo("Finalize count : " + finalCountresult);
		base.digitCompareEquals(finalCountresult, aggregateHitCount,
				"Finalize hit appears like aggregate results set of all child search groups and searches   ",
				"Count Mismatches");

		base.stepInfo("Finalize count : " + finalCountresult);
		base.stepInfo("PureHit count : " + purehitCount);
		base.digitCompareEquals(finalCountresult, purehitCount,
				"After the Bulk Assignment - Pure hit appears like aggregate results set of all child search groups and searches   ",
				"Count Mismatches");

		// Delete Node
		driver.scrollPageToTop();
		saveSearch.deleteSearch(basicSearchName, Input.mySavedSearch, "Yes");

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 12/29/21 Modified date:N/A Modified by: Description
	 *         : To verify that Overwritting Saved Searches must be allowed when the
	 *         Search is in Completed/Draft Status - RPMXCON-49033 Sprint 09
	 */
	@Test(description = "RPMXCON-49033", enabled = true, dataProvider = "verifyOverwrittingSavedSearch", groups = {
			"regression" })
	public void verifyOverwrittingSavedSearchInCompletedAndDraftStatus(String Query, String Status) throws Exception {

		String searchName1 = "SearchName" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-49033 - Saved Search Sprint 09");
		base.stepInfo(
				"To verify that Overwritting Saved Searches must be allowed when the Search is in Completed/Draft Status");

		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// saving the Search
		driver.getWebDriver().get(Input.url + "Search/Searches");
		session.basicContentSearchWithSaveChanges(Input.searchString1, Query, "First");
		session.saveSearch(searchName1);

		// selecting the savedSearch
		saveSearch.navigateToSSPage();
		saveSearch.savedSearch_SearchandSelect(searchName1, "Yes");
		saveSearch.verifyExecutionStatusInSavedSearchPage(Status);

		// modifying the saved search
		saveSearch.getSavedSearchEditButton().waitAndClick(10);
		base.waitTime(10);// To handle abnormal load time in application
		session.modifySearchTextArea(1, Input.searchString1, Input.searchString2, "Save");
		session.getSearchButton().Click();

		// Overwriting the the same saved saerch
		driver.waitForPageToBeReady();
		session.saveAsOverwrittenSearch(Input.mySavedSearch, searchName1, "First", "Success", "", null);

		login.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that status and counts are updated when Batch search
	 *              file is uploaded[RPMXCON-48905]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-48905", enabled = true, groups = { "regression" })
	public void verifyStatusAndCountForBatchFile() throws Exception {
		String headerName = "Search Name";
		String nearDupe = "Near Duplicate Count";
		String file = saveSearch.renameFile(Input.WPbatchFile);

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case Id: RPMXCON-48905");
		base.stepInfo("Verify that status and counts are updated when Batch search file is uploaded");

		// Upload Batch File And Get count of docs
		saveSearch.navigateToSavedSearchPage();
		saveSearch.uploadWPBatchFile(file);
		saveSearch.StatusAndCountForListOfSearches(headerName, nearDupe);

		// delete Uploaded Batch File
		saveSearch.deleteUploadedBatchFile(file, Input.mySavedSearch, false, null);
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : To verify, As a RM user login, when user select any folder or
	 *              sub folder (Node) in saved search page under search group, that
	 *              will be highlighted [RPMXCON-47422]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-47422", enabled = true, groups = { "regression" })
	public void verifyNodeHighlightRmu() throws Exception {
		String passMsg = "Selected folder is highlighted";
		String failMsg = "Selected folder is Not highlighted / highlighted with wrong color code";

		// Login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Test case Id: RPMXCON-47422");
		base.stepInfo(
				"To verify, As a RM user login, when user select any folder or sub folder (Node) in saved search page under search group, that will be highlighted");

		// Saved Search
		String node = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "PA", "No");

		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(2);
		saveSearch.selectNode1(node);
		base.stepInfo("Selected : " + node);
		driver.waitForPageToBeReady();
		String bgColor = saveSearch.getCreatedNode(node).GetCssValue("background-color");
		bgColor = base.rgbTohexaConvertor(bgColor);

		// Text Comparision
		base.textCompareEquals(bgColor, Input.selectionHighlightColor, passMsg, failMsg);

		// delete Node
		saveSearch.deleteNode(Input.mySavedSearch, node);
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description :To verify, As a Reviewer user login, when user select any
	 *              folder or sub folder (Node) in saved search page under search
	 *              group, that will be highlighted [RPMXCON-47423]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-47423", enabled = true, groups = { "regression" })
	public void verifySubNodeHighlightRev() throws Exception {
		String passMsg = "Selected folder is highlighted";
		String failMsg = "Selected folder is Not highlighted / highlighted with wrong color code";
		int noOfNodesToCreate = 2;
		int selectIndex = 1;
		List<String> newNodeList = new ArrayList<>();

		// Login as PA
		login.loginToSightLine(Input.rev1userName, Input.rev1password);
		base.stepInfo("Test case Id: RPMXCON-47423");
		base.stepInfo(
				"To verify, As a Reviewer user login, when user select any folder or sub folder (Node) in saved search page under search group, that will be highlighted");

		// Saved Search
		saveSearch.navigateToSavedSearchPage();
		newNodeList = saveSearch.createSGAndReturn("Rev", "No", noOfNodesToCreate);

		// select node
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(2);
		String node = saveSearch.childNodeSelectionToShare(selectIndex, newNodeList);
		base.stepInfo("Selected : " + node);
		driver.waitForPageToBeReady();
		String bgColor = saveSearch.getCreatedNode(node).GetCssValue("background-color");
		bgColor = base.rgbTohexaConvertor(bgColor);

		// Text Comparision
		base.textCompareEquals(bgColor, Input.selectionHighlightColor, passMsg, failMsg);

		// delete Node
		saveSearch.deleteNode(Input.mySavedSearch, newNodeList.get(0));
		login.logout();
	}

	/**
	 * @author Raghuram A Date: 12/30/21 Modified date:N/A Modified by:N/A
	 *         Description: Verify that relevant error message appears when user
	 *         deletes- batch Search "column header" and tries to upload same file
	 *         in Saved Search Screen.(RPMXCON-48537)
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-48537", enabled = true, groups = { "regression" })
	public void saveSearchBatchUploadInvalidHeaderData() throws InterruptedException {

		String fileName = Input.batchColumnHeaderErrorFileName;
		String fileFormat = ".xlsx";
		String sheetNum = "1";
		String batchNodeToCheck = fileName + "_" + sheetNum + "_Sheet" + sheetNum;

		base.stepInfo("Test case Id: RPMXCON-48537 - Saved Search Sprint 09");
		base.stepInfo(
				"Verify that relevant error message appears when user deletes- batch Search \"column header\" and tries to upload same file in Saved Search Screen.");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in as : " + Input.pa1FullName);

		saveSearch.navigateToSSPage();

		// upload batch file
		saveSearch.uploadBatchFile_D(Input.invalidBatchFileNewLocation, fileName + fileFormat, false);
		saveSearch.getSubmitToUpload().Click();
		saveSearch.verifyBatchUploadMessage("DataFailure", false);

		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(2);
		saveSearch.rootGroupExpansion();
		softAssertion.assertFalse(saveSearch.verifyNodePresent(batchNodeToCheck),
				"Searches not uploaded in Saved search screen.");
		softAssertion.assertAll();

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 12/30/21 Modified date:N/A Modified by:N/A
	 *         Description: Verify that Batch Search Upload should have ability to
	 *         upload multiple tabs/worksheets(10 sheets)(RPMXCON-48296)
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(description = "RPMXCON-48296", enabled = true, dataProvider = "AllTheUsers", groups = { "regression" })
	public void verifyMultipleTabWorkSheets(String userName, String password) throws InterruptedException, IOException {

		String fileName = Input.BatchFileWithMultiplesheetFile;
		String fileFormat = ".xlsx";
		String fileLocation = System.getProperty("user.dir") + Input.validBatchFileLocation;
		List<String> sheetList = new ArrayList<>();

		base.stepInfo("Test case Id: RPMXCON-48296 - Saved Search Sprint 09");
		base.stepInfo(
				"Verify that Batch Search Upload should have ability to upload multiple tabs/worksheets(10 sheets)");

		// Login as User
		login.loginToSightLine(userName, password);
		base.stepInfo("Logged in as : " + userName);

		int number_of_sheets = base.getTotalSheetCount(fileLocation, fileName + fileFormat);
		base.stepInfo("Total no.of sheets available in the workbook : " + number_of_sheets);

		saveSearch.navigateToSavedSearchPage();

		String fileToSelect = base.renameFile(true, fileLocation, fileName, fileFormat, false, "");
		System.out.println(fileToSelect);

		// upload batch file
		saveSearch.uploadBatchFile_D(Input.validBatchFileLocation, fileToSelect + fileFormat, false);
		saveSearch.getSubmitToUpload().Click();
		saveSearch.verifyBatchUploadMessage("Success", false);

		sheetList = saveSearch.verifyListOfNodes(sheetList, null, true, number_of_sheets, fileToSelect, null, null,
				true, Input.mySavedSearch);

		base.passedStep("Search groups created as per the naming convention  {Spreadsheet File Name} TabID{Tab Name}");

		String resetName = base.renameFile(false, fileLocation, fileToSelect, fileFormat, true, fileName);
		System.out.println(resetName);

		// Delete node
		saveSearch.deleteListofNode(Input.mySavedSearch, sheetList, true, true);

		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description: Verify that relevant error message appears when user
	 *               duplicates/(Repeats) - batch Search \"column header in same
	 *               sheet\" and tries to upload same file in Saved Search Screen.s
	 *               [RPMXCON-48538]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-48538", enabled = true, groups = { "regression" })
	public void verifyBatchFileError() throws InterruptedException {

		String fileName = Input.BatchFileduplicateHeader;
		String fileFormat = ".xlsx";
		String sheetNum = "1";
		String batchNodeToCheck = fileName + "_" + sheetNum + "_Sheet" + sheetNum;

		base.stepInfo("Test case Id: RPMXCON-48538 - Saved Search Sprint 09");
		base.stepInfo(
				"Verify that relevant error message appears when user duplicates/(Repeats) - batch Search \"column header in same sheet\" and tries to upload same file in Saved Search Screen.s");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in as : " + Input.pa1FullName);

		// Rename as dynamic fileName and store data respectively
		String fileLocation = System.getProperty("user.dir") + Input.invalidBatchFileNewLocation;
		String fileToSelect = base.renameFile(true, fileLocation, fileName, fileFormat, false, "");
		System.out.println(fileToSelect);

		saveSearch.navigateToSSPage();

		// upload batch file
		saveSearch.uploadBatchFile_D(Input.invalidBatchFileNewLocation, fileToSelect + fileFormat, false);
		saveSearch.getSubmitToUpload().Click();
		saveSearch.verifyBatchUploadMessage("DataFailure", false);

		// Reset FIleName
		base.renameFile(false, fileLocation, fileToSelect, fileFormat, true, fileName);

		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(2);
		saveSearch.rootGroupExpansion();
		softAssertion.assertFalse(saveSearch.verifyNodePresent(batchNodeToCheck),
				"Searches not uploaded in Saved search screen.");
		softAssertion.assertAll();

		login.logout();
	}

	/**
	 * @Author jeevitha
	 * @Description : When the user selects any query from Saved search, selected
	 *              search and it's folder is highlighted indicating the
	 *              selection[RPMXCON-47424]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-47424", enabled = true, groups = { "regression" })
	public void verifySearchUnderNodeHighlight() throws Exception {
		String search = "Search" + Utility.dynamicNameAppender();
		String passMsg = "Selected folder is highlighted";
		String failMsg = "Selected folder is Not highlighted / highlighted with wrong color code";

		// Login as RMU
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case Id: RPMXCON-47424");
		base.stepInfo(
				"When the user selects any query from Saved search, selected search and it's folder is highlighted indicating the selection");

		// Saved Search
		String node = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "PA", "No");

		// basic search
		session.basicContentSearch(Input.searchString1);
		session.saveSearchInNewNode(search, node);

		// selectsaved query from node
		saveSearch.navigateToSavedSearchPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(2);
		saveSearch.rootGroupExpansion();
		saveSearch.selectNode1(node);
		saveSearch.savedSearch_SearchandSelect(search, "Yes");
		base.stepInfo("Selected : " + search);
		driver.waitForPageToBeReady();
		String bgColor = saveSearch.getSelectWithName(search).GetCssValue("background-color");
		bgColor = base.rgbTohexaConvertor(bgColor);
		System.out.println(bgColor);

		// Text Comparision
		base.textCompareEquals(bgColor, Input.savedSearchColor, passMsg, failMsg);

		// delete Node
		saveSearch.deleteNode(Input.mySavedSearch, node);
		login.logout();
	}

	/**
	 * @author Raghuram A Date: 12/31/21 Modified date:N/A Modified by:N/A
	 * @Description: Verify that relevant error message appears when user modifies
	 *               (Rename with xyz) - batch Search "column header in Sheet2" and
	 *               tries to upload same file in Saved Search Screen
	 *               [RPMXCON-48539]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-48539", enabled = true, groups = { "regression" })
	public void verifyBatchFileRenamedHeaderSheet2() throws InterruptedException {

		String fileName = Input.BatchFileWithMultiplesheetFile;
		String fileFormat = ".xlsx";
		String batchNodeToCheck = fileName + "_" + 1 + "_Sheet" + 1;
		String renamedbatchSheet = fileName + "_" + 2 + "_Sheet" + 2;

		base.stepInfo("Test case Id: RPMXCON-48539 - Saved Search Sprint 09");
		base.stepInfo(
				"Verify that relevant error message appears when user modifies (Rename with xyz) - batch Search \"column header in Sheet2\" and tries to upload same file in Saved Search Screen");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in as : " + Input.pa1FullName);

		saveSearch.navigateToSSPage();

		// upload batch file
		saveSearch.uploadBatchFile_D(Input.invalidBatchFileNewLocation, fileName + fileFormat, false);
		saveSearch.getSubmitToUpload().Click();
		saveSearch.verifyBatchUploadMessage("DataFailure", false);

		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(2);
		saveSearch.rootGroupExpansion();
		softAssertion.assertFalse(saveSearch.verifyNodePresent(batchNodeToCheck),
				"Searches not uploaded in Saved search screen.");
		softAssertion.assertFalse(saveSearch.verifyNodePresent(renamedbatchSheet),
				"Searches not uploaded in Saved search screen.");
		softAssertion.assertAll();

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 12/31/21 Modified date:N/A Modified by: Description
	 *         : Overwriting saved Searches - User should be allowed to overwrite
	 *         the search in Completed/Draft status - RPMXCON-48943 Sprint 09
	 */
	@Test(description = "RPMXCON-48943", enabled = true, dataProvider = "verifyOverwrittingSavedSearchAsUser", groups = {
			"regression" })
	public void verifyOverwrittingSavedSearchInCompletedAndDraftStatusAsPAUandRMUandRev(String userName,
			String password, String fullName, String Query, String Status, int searchNum) throws Exception {

		String searchName1 = "Search Name" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-48943 - Saved Search Sprint 09");
		base.stepInfo(
				"Overwriting saved Searches - User should be allowed to overwrite the search in Completed/Draft status");

		login.loginToSightLine(userName, password);
		base.stepInfo("Loggedin As : " + fullName);

		// saving the Search
		driver.getWebDriver().get(Input.url + "Search/Searches");
		session.basicContentSearchWithSaveChanges(Input.searchString1, Query, "First");
		session.saveSearch(searchName1);

		// selecting the savedSearch
		saveSearch.navigateToSSPage();
		saveSearch.savedSearch_SearchandSelect(searchName1, "Yes");
		saveSearch.verifyExecutionStatusInSavedSearchPage(Status);

		// modifying the saved search
		saveSearch.getSavedSearchEditButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		session.modifySearchTextArea(searchNum, Input.searchString1, Input.searchString2, "Save");
		session.getSearchButton().Click();

		// Overwriting the the same saved saerch
		driver.waitForPageToBeReady();
		session.saveAsOverwrittenSearch(Input.mySavedSearch, searchName1, "First", "Success", "", null);

		// Deleting the SavedSearch
		saveSearch.navigateToSSPage();
		saveSearch.deleteSearch(searchName1, Input.mySavedSearch, "Yes");
		login.logout();

	}

	/**
	 * @author Jayanthi A Date: 4/2/22 Modified date:N/A Modified by: Description
	 *         RPMXCON-57185 Sprint 11
	 */
	@Test(description = "RPMXCON-57185", enabled = true, groups = { "regression" })
	public void verifyBulkUntag() throws Exception {

		String searchName1 = "Search Name" + Utility.dynamicNameAppender();
		String tagName = "tagName" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-57185 - Saved Search Sprint 11");
		base.stepInfo("Verify that UnTag works properly using Bulk " + "Tag Action in Saved Search Screen");

		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1userName);

		// saving the Search
		driver.getWebDriver().get(Input.url + "Search/Searches");
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName1);

		// selecting the savedSearch
		saveSearch.navigateToSSPage();
		saveSearch.SaveSearchToBulkTag(searchName1, tagName);
		base.stepInfo("Bulk tag done from saved search screen " + tagName);
		saveSearch.savedSearch_Searchandclick(searchName1);
		saveSearch.getSavedSearchToBulkTag().Click();
		saveSearch.bulkUnTag(tagName);
		base.stepInfo("Bulk UnTag done from saved search screen" + tagName);
		TagsAndFoldersPage tf = new TagsAndFoldersPage(driver);
		tf.navigateToTagsAndFolderPage();
		// verifying for UnTag Completed
		tf.verifyTagDocCount(tagName, 0);
		tf.navigateToTagsAndFolderPage();
		tf.deleteAllTags(tagName);
		// Deleting the SavedSearch
		saveSearch.navigateToSSPage();
		saveSearch.deleteSearch(searchName1, Input.mySavedSearch, "Yes");
		login.logout();

	}

	/**
	 * @author Jayanthi A Date: 4/2/22 Modified date:N/A Modified by: Description
	 *         RPMXCON-48756 Sprint 11
	 */
	@Test(description = "RPMXCON-48756", enabled = true, groups = { "regression" })
	public void verifyBulkFolder() throws Exception {

		String searchName1 = "Search Name" + Utility.dynamicNameAppender();
		String folderName = "folderSS" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-48756 - Saved Search Sprint 11");
		base.stepInfo("Verify that UnFolder works properly using Bulk Folder Action in Saved Search Screen");

		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1userName);

		// saving the Search
		driver.getWebDriver().get(Input.url + "Search/Searches");
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName1);

		// selecting the savedSearch
		saveSearch.navigateToSSPage();
		saveSearch.SaveSearchToBulkFolder(searchName1, folderName);
		base.stepInfo("Bulk folder done from saved search screen" + folderName);
		saveSearch.savedSearch_Searchandclick(searchName1);
		saveSearch.getSavedSearchToBulkFolder().Click();
		saveSearch.bulkUnFolder(folderName);
		base.stepInfo(folderName);
		TagsAndFoldersPage tf = new TagsAndFoldersPage(driver);
		tf.navigateToTagsAndFolderPage();
		// verifying for Unfolder Completed
		tf.verifyFolderDocCount(folderName, 0);
		tf.navigateToTagsAndFolderPage();
		tf.deleteAllFolders("folderSS");
		// Deleting the SavedSearch
		saveSearch.navigateToSSPage();
		saveSearch.deleteSearch(searchName1, Input.mySavedSearch, "Yes");
		login.logout();

	}

	/**
	 * @author Sowndarya.velraj Date: 02/04/22 Description:Verify that logged User
	 *         Information gets updated in "Last Submitted By" column in Saved
	 *         Search screen RPMXCON-48587 Sprint 12
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-48587", enabled = true, dataProvider = "PaAndRmuUsers", groups = { "regression" })
	public void verifyLastSubmittedBYAsRMU_User(String userName, String password, String fullName)
			throws InterruptedException, ParseException {

		String headername = "Last Submitted By";

		base.stepInfo("Test case Id: RPMXCON-48587 - Saved Search Sprint 11");
		base.stepInfo(
				"Verify that logged User Information gets updated in \"Last Submitted By\" column in Saved Search screen");
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		String currentUserName = login.getCurrentUserName();
		base.stepInfo("Loggedin As : " + currentUserName);

		// saving the Search
		String SearchName = "Search" + Utility.dynamicNameAppender();
		driver.getWebDriver().get(Input.url + "Search/Searches");
		int purehit = session.basicContentSearch(Input.TallySearch);
		session.saveSearch(SearchName);
		saveSearch.navigateToSavedSearchPage();
		saveSearch.shareSavedSearchRMU(SearchName, Input.shareSearchDefaultSG);

		login.logout();
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		currentUserName = login.getCurrentUserName();
		base.stepInfo("Loggedin As : " + currentUserName);
		saveSearch.selectSavedSearchTAb(SearchName, Input.shareSearchDefaultSG, Input.yesButton);
		saveSearch.savedSearchExecute_Draft(SearchName, purehit);
		driver.waitForPageToBeReady();
		String actualName = saveSearch.getListFromSavedSearchTable1(headername, SearchName);
		softAssertion.assertEquals(actualName, currentUserName);
		softAssertion.assertAll();
		login.logout();
	}

	/**
	 * @author Sowndarya.velraj Date: 02/04/22 Description:Verify that - After
	 *         impersonation (SysAdmin to RMU) - logged User Information gets
	 *         updated in "Last Submitted By" column in Saved Search screen
	 *         RPMXCON-48589 Sprint 12
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-48589", enabled = true, groups = { "regression" })
	public void verifyLastSubmittedBYAsSystemadmin_User() throws InterruptedException, ParseException {

		base.stepInfo("Test case Id: RPMXCON-48589 - Saved Search Sprint 11");
		base.stepInfo(
				"Verify that - After impersonation (SysAdmin to RMU) - logged User Information gets updated in \"Last Submitted By\" column in Saved Search screen");
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		String currentUserName = login.getCurrentUserName();
		base.stepInfo("Loggedin As : " + currentUserName);

		// saving the Search
		String headername = "Last Submitted By";

		// saving the Search
		String SearchName = "Search" + Utility.dynamicNameAppender();
		driver.getWebDriver().get(Input.url + "Search/Searches");
		int purehit = session.basicContentSearch(Input.TallySearch);
		session.saveSearch(SearchName);
		saveSearch.navigateToSavedSearchPage();
		saveSearch.shareSavedSearchRMU(SearchName, Input.shareSearchDefaultSG);

		login.logout();
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.impersonateSAtoRMU();
		currentUserName = login.getCurrentUserName();
		base.stepInfo("Loggedin As : " + currentUserName);
		saveSearch.selectSavedSearchTAb(SearchName, Input.shareSearchDefaultSG, Input.yesButton);
		saveSearch.savedSearchExecute_Draft(SearchName, purehit);
		driver.waitForPageToBeReady();
		String actualName = saveSearch.getListFromSavedSearchTable1(headername, SearchName);
		softAssertion.assertEquals(actualName, currentUserName);
		softAssertion.assertAll();
		login.logout();
	}

	/**
	 * @Author Sowndarya
	 * @Description : Verify that \"Count\" gets updated in conceptual column in
	 *              Saved Search Screen when user triggered Conceptually Similar
	 *              count from Advanced search and saved search. [RPMXCON-47568]
	 *              sprint 12
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-47568", enabled = true, groups = { "regression" })
	public void searchCompletedUnderSG() throws InterruptedException, ParseException {

		String statusToCheck = "COMPLETED";
		// Login as PA
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		base.stepInfo("Test case Id: 47568 Saved Search");
		base.stepInfo(
				"To verify as an RM user login, I will be able to search a saved query based on search status 'COMPLETED' under <Shared with Security Group> folder");

		// Navigate to SavedSearch Page
		saveSearch.navigateToSSPage();
		saveSearch.getSavedSearchGroupName(Input.shareSearchDefaultSG).waitAndClick(5);
//		saveSearch.verifyStatusFilter(statusToCheck);
		base.stepInfo("To verify Completed Status By applying filter");
		saveSearch.getStatusDropDown().waitAndClick(2);
		saveSearch.getLastStatusAs(statusToCheck).waitAndClick(2);
		saveSearch.getSavedSearch_ApplyFilterButton().waitAndClick(2);
		saveSearch.verifyStatusFilterT(statusToCheck, "Last Status", true);

		login.logout();

	}

	/**
	 * @author Raghuram A Date: 02/05/22 Modified date:N/A Modified by: Description
	 *         : Validate navigation to DocView screen from a saved search with
	 *         Completed status - RPMXCON-48610 Sprint 11
	 */
	@Test(description = "RPMXCON-48610", enabled = true, dataProvider = "AllTheUsers", groups = { "regression" })
	public void verifyDocsDisplayAndNavigatingToDocview(String username, String password) throws Exception {

		// Login as USER
		login.loginToSightLine(username, password);
		base.stepInfo("Logged in as : " + username);

		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();
		int latencyCheckTime = 5;
		String passMessage = "Application not hang or shows latency more than " + latencyCheckTime + " seconds.";
		String failureMsg = "Continues Loading more than " + latencyCheckTime + " seconds.";

		base.stepInfo("Test case Id: RPMXCON-48610 - Saved Search Sprint 11");
		base.stepInfo("Validate navigation to DocView screen from a saved search with Completed status");

		// add save search in node
		int purehit = session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName);

		// Launch DocVia via Saved Search
		saveSearch.navigateToSSPage();
		saveSearch.savedSearch_SearchandSelect(searchName, "Yes");
		saveSearch.checkStatus(searchName);
		saveSearch.docViewFromSS("View in DocView");

		// Load latency Verification
		Element loadingElement = session.getspinningWheel();
		saveSearch.loadingCountVerify(loadingElement, latencyCheckTime, passMessage, failureMsg);
		driver.waitForPageToBeReady();
		String currentUrl = driver.getWebDriver().getCurrentUrl();
		softAssertion.assertEquals(Input.url + "DocumentViewer/DocView", currentUrl);
		base.stepInfo("Navigated to DocView Page : " + currentUrl);

		// Main method
		miniDocListPage = new MiniDocListPage(driver);
		base.waitForElement(miniDocListPage.getDocumentCountFromDocView());
		String sizeofList = miniDocListPage.getDocumentCountFromDocView().getText();
		String documentSize = sizeofList.substring(sizeofList.indexOf("of") + 2, sizeofList.indexOf("Docs")).trim();
		System.out.println("Size : " + documentSize);
		base.stepInfo("Available documents in DocView page : " + sizeofList);

		base.digitCompareEquals(purehit, Integer.parseInt(documentSize),
				"Doc View page loaded with selected number of documents  ", "Count Mismatches with the Documents");

		// Delete Search
		base.stepInfo("Initiating Delete Search");
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : To Verify, As a Project Admin login, In saved search page, in
	 *              \"My Search\" section he will be only able to see his my
	 *              searches query, not other users my searches [RPMXCON-57018]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57018", enabled = true, groups = { "regression" })
	public void verifyPaUserNames() throws InterruptedException {
		String searchName = " Search" + Utility.dynamicNameAppender();
		String headerName = "Last Submitted By";
		String expectedName;
		List<String> list = new ArrayList<>();
		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("Test case Id: RPMXCON-57018 - Saved Search Sprint 11");
		base.stepInfo(
				"To Verify, As a Project Admin login, In saved search page, in \"My Search\" section he will be only able to see his my searches query, not other users my searches");

		// Get current user name
		expectedName = login.getCurrentUserName();

		// basic search
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName);

		// Verify Last Submitted names
		saveSearch.navigateToSavedSearchPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(5);
		list = saveSearch.getListFromSavedSearchTable(headerName);

		String passMsg = expectedName + " : is the Last Submitted Name Displayed";
		String failMsg = "Able to See other User Searches";
		base.compareListWithString(list, expectedName, passMsg, failMsg);

		softAssertion.assertAll();
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : To verify, As a RMU user login, when user select more than one
	 *              saved search (select a folder within \"My Search\") then only
	 *              tag will be enable [RPMXCON-47416]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-47416", enabled = true, groups = { "regression" })
	public void verifyTagAsRmu() throws InterruptedException {
		String headerName = "Last Submitted By";
		String passMsg = "Search Group list is Present";
		String failMsg = "Search Groups is Not Present";

		// login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Test case Id: RPMXCON-47416 - Saved Search Sprint 11");
		base.stepInfo(
				"To verify, As a RMU user login, when user select more than one saved search (select a folder within \"My Search\") then only tag will be enable");

		// verify TAG enabled
		saveSearch.navigateToSavedSearchPage();
		saveSearch.rootGroupExpansion();
		base.verifyElementCollectionIsNotEmpty(saveSearch.getList(), passMsg, failMsg);

		saveSearch.getSavedSearchNewNode().waitAndClick(5);
		Element tagButtonStatus = saveSearch.getSavedSearchToBulkTag();
		saveSearch.checkButtonEnabled(tagButtonStatus, "Should be Enabled", "Tag Option");

		login.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that \"Count\" display as BLANK in conceptual column in
	 *              Saved Search Screen when user is not triggered Conceptually
	 *              Similar count but the Advanced search is saved as a saved
	 *              search. [RPMXCON-48902]
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-48902", enabled = true, groups = { "regression" })
	public void verifyConceptualWithoutTriggerForAdvance() throws InterruptedException, ParseException {
		String Search = "search" + Utility.dynamicNameAppender();
		String conceptually = "Conceptually Similar Count";

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		base.stepInfo("Test case Id: RPMXCON-48902 Saved Search");
		base.stepInfo(
				"Verify that \"Count\" display as BLANK in conceptual column in Saved Search Screen when user is not triggered Conceptually Similar count but the Advanced search is saved as a saved search.");

		// Advanced Search
		session.advancedMetaDataForDraft(Input.metaDataName, null, Input.metaDataCN, null);
		session.saveAndReturnPureHitCount();
		driver.scrollPageToTop();
		String concept = session.verifyConceptuallySimilarCount();
		softAssertion.assertEquals(concept, " ");
		base.passedStep("Conceptually Similar count is not Triggered");
		session.saveSearchadvanced(Search);

		// Verify Conceptually Column
		saveSearch.savedSearch_Searchandclick(Search);
		String Count = saveSearch.ApplyShowAndHideFilter(conceptually, Search);
		softAssertion.assertEquals(Count, Input.TextEmpty);
		softAssertion.assertAll();
		base.stepInfo("Conceptual Column Count is BLANK");

		// Delete Searches
		saveSearch.deleteSearch(Search, Input.mySavedSearch, Input.yesButton);

		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that \"Count\" gets updated in conceptual column in
	 *              Saved Search Screen when user triggered Conceptually Similar
	 *              count from Basic search and saved search. [RPMXCON-48903]
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-48903", enabled = true, groups = { "regression" })
	public void verifyConceptAfterTriggerBasic() throws InterruptedException, ParseException {
		String Search = "search" + Utility.dynamicNameAppender();
		String conceptually = "Conceptually Similar Count";

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		base.stepInfo("Test case Id: RPMXCON-48903 Saved Search");
		base.stepInfo(
				"Verify that \"Count\" gets updated in conceptual column in Saved Search Screen when user triggered Conceptually Similar count from Basic search and saved search.");

		// Basic Search
		session.basicContentSearch(Input.searchString1);
		int concept = session.runAndVerifyConceptualSearch();
		base.CloseSuccessMsgpopup();
		base.passedStep("Conceptually Similar count is Triggered");
		session.saveSearch(Search);

		// Verify Conceptually Column
		saveSearch.savedSearch_Searchandclick(Search);
		String Count = saveSearch.ApplyShowAndHideFilter(conceptually, Search);
		base.textCompareEquals(Count, Integer.toString(concept), "Count matches", "Count mis-matches");
		base.stepInfo("Conceptual Column Count is Updated");

		// Delete Searches
		saveSearch.deleteSearch(Search, Input.mySavedSearch, Input.yesButton);

		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that \"Count\" gets updated in conceptual column in
	 *              Saved Search Screen when user triggered Conceptually Similar
	 *              count from Advanced search and saved search. [RPMXCON-48904]
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-48904", enabled = true, groups = { "regression" })
	public void verifyConceptAfterTriggerAdvance() throws InterruptedException, ParseException {
		String Search = "search" + Utility.dynamicNameAppender();
		String conceptually = "Conceptually Similar Count";

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		base.stepInfo("Test case Id: RPMXCON-48904 Saved Search");
		base.stepInfo(
				"Verify that \"Count\" gets updated in conceptual column in Saved Search Screen when user triggered Conceptually Similar count from Advanced search and saved search.");

		// Basic Search
		session.advancedContentSearch(Input.searchString1);
		int concept = session.runAndVerifyConceptualSearch();
		base.CloseSuccessMsgpopup();
		base.passedStep("Conceptually Similar count is Triggered");
		session.saveSearchadvanced(Search);

		// Verify Conceptually Column
		saveSearch.savedSearch_Searchandclick(Search);
		String Count = saveSearch.ApplyShowAndHideFilter(conceptually, Search);
		softAssertion.assertEquals(Integer.parseInt(Count), concept);
		softAssertion.assertAll();
		base.stepInfo("Conceptual Column Count is Updated");

		// Delete Searches
		saveSearch.deleteSearch(Search, Input.mySavedSearch, Input.yesButton);

		login.logout();
	}

	/**
	 * @Author Raghuram
	 * @Description : To verify as a Reviewer user login, I will be able to search a
	 *              saved query based on search status 'COMPLETED' under <Shared
	 *              with Security Group Name> folder [RPMXCON-47569] sprint 12
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-47569", enabled = true, groups = { "regression" })
	public void searchCompletedUnderSGAsRev() throws InterruptedException, ParseException {

		String statusToCheck = "COMPLETED";

		// Login as REV
		login.loginToSightLine(Input.rev1userName, Input.rev1password);
		base.stepInfo("Loggedin As : " + Input.rev1FullName);

		base.stepInfo("Test case Id: RPMXCON-47569 Saved Search");
		base.stepInfo(
				"To verify as a Reviewer user login, I will be able to search a saved query based on search status 'COMPLETED' under <Shared with Security Group Name> folder");

		// Navigate to SavedSearch Page
		saveSearch.navigateToSSPage();
		saveSearch.getSavedSearchGroupName(Input.shareSearchDefaultSG).waitAndClick(5);
		saveSearch.verifyStatusFilter(statusToCheck);

		login.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description : To verify as an RM user login, I will be able to search a
	 *              saved query based on search status 'COMPLETED' under Shared
	 *              folder [RPMXCON-47570] sprint 12
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-47570", enabled = true, groups = { "regression" })
	public void searchCompletedUnderSGAsRMU() throws InterruptedException, ParseException {

		String statusToCheck = "COMPLETED";

		// Login as REV
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		base.stepInfo("Test case Id: RPMXCON-47570 Saved Search");
		base.stepInfo(
				"To verify as an RM user login, I will be able to search a saved query based on search status 'COMPLETED' under Shared folder");

		// Navigate to SavedSearch Page
		saveSearch.navigateToSSPage();
		saveSearch.getSavedSearchGroupName(Input.shareSearchDefaultSG).waitAndClick(5);
		saveSearch.verifyStatusFilter(statusToCheck);

		login.logout();

	}

	/**
	 * @author Jayanthi A Date: 02/09/22 Modified date:N/A Modified by: Description
	 *         :Verify that User can navigate Renamed search to DocView from Saved
	 *         Search Screen- RPMXCON-48786 Sprint 11
	 */
	@Test(description = "RPMXCON-48786", enabled = true, dataProvider = "AllTheUsers", groups = { "regression" })
	public void verifyRenamedSearchNavigatingToDocview(String username, String password) throws Exception {

		// Login as USER
		login.loginToSightLine(username, password);
		base.stepInfo("Logged in as : " + username);

		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();
		String SearchRename = "rename" + UtilityLog.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-48786 - Saved Search Sprint 11");
		base.stepInfo("Verify that User can navigate Renamed search to DocView from Saved Search Screen.");

		// add save search in node
		int purehit = session.basicContentSearch(Input.testData1);
		session.saveSearch(searchName);

		saveSearch.verifyRenamedsavsearch(searchName, SearchRename);
		saveSearch.docViewFromSS("View in DocView");
		driver.waitForPageToBeReady();
		miniDocListPage = new MiniDocListPage(driver);
		base.waitForElement(miniDocListPage.getDocumentCountFromDocView());

		String currentUrl = driver.getWebDriver().getCurrentUrl();
		String expectedURL = Input.url + "DocumentViewer/DocView";
		if (expectedURL.equals(currentUrl)) {
			base.passedStep("Navigated to DocView Page : " + currentUrl);
			String sizeofList = miniDocListPage.getDocumentCountFromDocView().getText();
			String documentSize = sizeofList.substring(sizeofList.indexOf("of") + 2, sizeofList.indexOf("Docs")).trim();
			System.out.println("Size : " + documentSize);
			base.stepInfo("Available documents in DocView page : " + sizeofList);
			base.digitCompareEquals(purehit, Integer.parseInt(documentSize),
					"Doc View page loaded with selected number of documents  ", "Count Mismatches with the Documents");
		} else {
			base.failedStep("Not navigated to doc view page.");
		}

		// Delete Search
		base.stepInfo("Initiating Delete Search");
		saveSearch.deleteSearch(SearchRename, Input.mySavedSearch, "Yes");

		login.logout();
	}

	/**
	 * @Author Raghuram @Date: 02/09/22 @Modified date:N/A @Modified by:N/A
	 * @Description : To verify as an RM user login, I will be able to search a
	 *              saved query based on search status 'COMPLETED' under My Saved
	 *              Search folder [RPMXCON-47562] sprint 12
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-47562", enabled = true, groups = { "regression" })
	public void searchFilterBasedOnStatus() throws InterruptedException, ParseException {

		String SearchName = "SearchName" + Utility.dynamicNameAppender();
		String SearchName1 = "SearchName" + Utility.dynamicNameAppender();
		String statusToCheck = "COMPLETED";

		// Login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		base.stepInfo("Test case Id: RPMXCON-47562 Saved Search - Sprint 12");

		base.stepInfo(
				"To verify as an RM user login, I will be able to search a saved query based on search status 'COMPLETED' under My Saved Search folder");

		// Perform create node - Search - SaveSearch in nodes
		String nodeName = saveSearch.createSearchGroupAndReturn("", Input.rmu1FullName, "No");
		session.navigateToSessionSearchPageURL();
		session.basicContentSearchWithSaveChanges(Input.searchString2, "No", "First");
		session.saveSearchInNewNode(SearchName, nodeName);
		session.getNewSearch().waitAndClick(5);
		session.multipleBasicContentSearch(Input.searchString1);
		session.saveSearchInNewNode(SearchName1, nodeName);

		// Navigate to SavedSearch Page
		saveSearch.navigateToSSPage();
		saveSearch.selectNode1(nodeName);
		saveSearch.verifyStatusFilterT(statusToCheck, "Last Status", false);

		// Delete created Node
		base.stepInfo("Initiating delete nodes");
		saveSearch.deleteNode(Input.mySavedSearch, nodeName);

		login.logout();

	}

	/**
	 * @Author Raghuram @Date: 02/09/22 @Modified date:N/A @Modified by:N/A
	 * @Description : To verify as a Reviewer user login, I will be able to search a
	 *              saved query based on search status 'COMPLETED' under My Saved
	 *              Search [RPMXCON-47563] sprint 12
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-47563", enabled = true, groups = { "regression" })
	public void searchFilterBasedOnStatusREV() throws InterruptedException, ParseException {

		String SearchName = "SearchName" + Utility.dynamicNameAppender();
		String SearchName1 = "SearchName" + Utility.dynamicNameAppender();
		String statusToCheck = "COMPLETED";

		// Login as RMU
		login.loginToSightLine(Input.rev1userName, Input.rev1password);
		base.stepInfo("Loggedin As : " + Input.rev1FullName);

		base.stepInfo("Test case Id: RPMXCON-47563 Saved Search - Sprint 12");
		base.stepInfo(
				"To verify as a Reviewer user login, I will be able to search a saved query based on search status 'COMPLETED' under My Saved Search");

		// Perform create node - Search - SaveSearch in nodes
		String nodeName = saveSearch.createSearchGroupAndReturn("", Input.rev1FullName, "No");
		session.navigateToSessionSearchPageURL();
		session.basicContentSearchWithSaveChanges(Input.searchString2, "No", "First");
		session.saveSearchInNewNode(SearchName, nodeName);
		session.getNewSearch().waitAndClick(5);
		session.multipleBasicContentSearch(Input.searchString1);
		session.saveSearchInNewNode(SearchName1, nodeName);

		// Navigate to SavedSearch Page
		saveSearch.navigateToSSPage();
		saveSearch.selectNode1(nodeName);
		saveSearch.verifyStatusFilterT(statusToCheck, "Last Status", false);

		// Delete created Node
		base.stepInfo("Initiating delete nodes");
		saveSearch.deleteNode(Input.mySavedSearch, nodeName);

		login.logout();

	}

	/**
	 * @author Raghuram A @Date: 02/10/22 @Modified date:N/A @Modified by:N/A
	 *         Description : Verify that application displays all documents that are
	 *         in the aggregate results set of all child search groups "My Saved
	 *         Search" and searches when User performs Refresh with Child Search
	 *         groups (RPMXCON-48921 Sprint-12)
	 * @throws InterruptedException
	 * @throws ParseException
	 * @Stabilizaation - done
	 */

	@Test(description = "RPMXCON-48921", enabled = true, groups = { "regression" })
	public void refreshGridCheck() throws InterruptedException, ParseException {
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		Boolean inputValue = true;
		int noOfNode = 4;
		String specificHeaderName = "Search Name";
		String nodeToSelect;
		base.stepInfo("Test case Id: RPMXCON-48921 - Saved Search - Sprint 12");
		base.stepInfo(
				"Verify that application displays all documents that are in the aggregate results set of all child search groups \"My Saved Search\" and searches when User performs Refresh with Child Search groups");

		// Login as PA
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);
		// Navigate on Saved Search & Multiple Node Creation & save search in node
		saveSearch.navigateToSSPage();
		newNodeList = saveSearch.createSGAndReturn("RMU", "No", noOfNode);
		nodeToSelect = newNodeList.get(0);
		System.out.println("Next adding searches to the created nodes");
		base.stepInfo("Next adding searches to the created nodes");
		session.navigateToSessionSearchPageURL();
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);

		base.stepInfo("-------Pre-requesties completed--------");

		saveSearch.navigateToSSPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(2);
		base.stepInfo("Root node selected : " + nodeToSelect);
		saveSearch.selectNode1(nodeToSelect);

		saveSearch.methodTocheckHideandShowFunction(specificHeaderName);

		// Delete created Node
		base.stepInfo("Initiating delete nodes");
		saveSearch.deleteNode(Input.mySavedSearch, nodeToSelect);

		login.logout();
	}

	/**
	 * @author Raghuram A @Date: 02/10/22 @Modified date:N/A @Modified by:N/A
	 *         Description : Verify that application displays all documents that are
	 *         in the aggregate results set of all child search groups "My Saved
	 *         Search" and searches when User Performs Bulk Tag from Child Search
	 *         groups(RPMXCON-48917) - Sprint 12
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-48917", enabled = true, groups = { "regression" })
	public void performBulkTagActionPC() throws InterruptedException, ParseException {

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		int docCount, noOfNodesToCreate = 3;
		String finalCountresult;
		String executionCountSuccessMsg = "Aggregate tag result matches with the count.";
		String executionCountFailureMsg = "Count Mismatches";
		String TagName = "Tag" + Utility.dynamicNameAppender();
		int latencyCheckTime = 5;
		String executionCountWithTagSuccessMsg = "Document Count matches with the Execution count";
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		Boolean inputValue = true;
		String nodeToSelect;

		base.stepInfo("Test case Id: RPMXCON-48917 - Saved Search Sprint 12");
		base.stepInfo(
				"Verify that application displays all documents that are in the aggregate results set of all child search groups \"My Saved Search\" and searches when User Performs Bulk Tag from Child Search groups");

		// Login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// create folder and add save search in folder
		tagsAndFolderPage.CreateTag(TagName, Input.securityGroup);

		// Calculate the unique doc count for the respective searches
		int aggregateHitCount = session.getDocCountBtwnTwoSearches(true, Input.searchString5, Input.searchString6);
		base.stepInfo("Aggregate count : " + aggregateHitCount);
		base.selectproject();

		// Multiple Node Creation
		saveSearch.navigateToSSPage();
		newNodeList = saveSearch.createSGAndReturn("PA", "No", noOfNodesToCreate);
		nodeToSelect = newNodeList.get(0);
		System.out.println("Next adding searches to the created nodes");
		base.stepInfo("Next adding searches to the created nodes");

		// add save search in node
		driver.getWebDriver().get(Input.url + "Search/Searches");
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);

		base.stepInfo("-------Pre-requesties completed--------");

		saveSearch.navigateToSSPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(2);
		base.stepInfo("Root node selected : " + newNodeList.get(1));
		saveSearch.selectNode1(nodeToSelect);

		// Bulk Tag
		saveSearch.getSavedSearchToBulkTag().Click();
		base.stepInfo("Clicked tag icon from Code");

		// Load latency Verification
		saveSearch.loadTimeCheck(latencyCheckTime);

		finalCountresult = session.bulkTagExistingWithReturn(TagName);
		base.digitCompareEquals(aggregateHitCount, Integer.parseInt(finalCountresult), executionCountSuccessMsg,
				executionCountFailureMsg);
		base.stepInfo("Completed Bulk Tag");
		base.stepInfo(
				"Navigating to Search >> Basic Search >> Advanced Search >> WorkProduct >> Tags (Select Same Tag which we have created in prerequesties.");
		base.selectproject();
		session.switchToWorkproduct();
		session.selectTagInASwp(TagName);
		int pureHit = session.serarchWP();
		docCount = Integer.parseInt(finalCountresult);
		base.digitCompareEquals(docCount, pureHit, executionCountWithTagSuccessMsg, executionCountFailureMsg);

		// Delete created Node
		base.stepInfo("Initiating delete nodes");
		saveSearch.deleteNode(Input.mySavedSearch, nodeToSelect);

		// Tag deletion
		base.stepInfo("initiating tag deletion");
		driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		driver.waitForPageToBeReady();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.deleteAllTags(TagName);

		login.logout();
	}

	/**
	 * @Author Jeevitha @Date: 02/10/22 @Modified date:N/A @Modified by:N/A
	 * @Description : Verify that application displays all documents that are in the
	 *              aggregate results set of all child search groups "My Saved
	 *              Search" and searches when User Navigate Child Search groups to
	 *              Report [RPMXCON-48918] - Sprint 12
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-48918", enabled = true, groups = { "regression" })
	public void verifyReportForAggregateSearchHitCountInStrPageT() throws Exception {

		SearchTermReportPage str = new SearchTermReportPage(driver);

		int noOfNodesToCreate = 2;
		String executionCountSuccessMsg = "Aggregate result matches with the count.";
		String executionCountFailureMsg = "Count Mismatches";
		int latencyCheckTime = 5;
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		Boolean inputValue = true;
		String nodeToSelect;

		base.stepInfo("Test case Id: RPMXCON-48918 - Saved Search - Sprint 12");
		base.stepInfo(
				"Verify that application displays all documents that are in the aggregate results set of all child search groups \"My Saved Search\" and searches when User Navigate Child Search groups to Report");

		// Login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// Calculate the unique doc count for the respective searches
		int aggregateHitCount = session.getDocCountBtwnTwoSearches(true, Input.searchString5, Input.searchString6);
		base.stepInfo("Aggregate count : " + aggregateHitCount);
		base.selectproject();

		// Multiple Node Creation
		saveSearch.navigateToSSPage();
		newNodeList = saveSearch.createSGAndReturn("PA", "No", noOfNodesToCreate);
		nodeToSelect = newNodeList.get(0);
		System.out.println("Next adding searches to the created nodes");
		base.stepInfo("Next adding searches to the created nodes");

		// add save search in node
		session.navigateToSessionSearchPageURL();
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);

		base.stepInfo("-------Pre-requesties completed--------");

		saveSearch.navigateToSSPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(2);
		base.stepInfo("Root node selected : " + newNodeList.get(0));
		saveSearch.selectNode1(nodeToSelect);
		saveSearch.checkButtonEnabled(saveSearch.getSavedSearchToTermReport(), "Should be Enabled", "Report");
		saveSearch.getSavedSearchToTermReport().waitAndClick(5);
		driver.waitForPageToBeReady();

		// Load latency Verification
		saveSearch.loadTimeCheckWithPageSpinningWheel(latencyCheckTime);

		str.verifyaggregateCount("HITS");
		base.waitForElement(str.getHitsCount());
		int expectedHitsCount = Integer.parseInt(str.getHitsCount().getText());
		base.stepInfo("Hit Count of searhces is :  " + expectedHitsCount);
		base.stepInfo("Aggregate count : " + aggregateHitCount);
		base.digitCompareEquals(aggregateHitCount, expectedHitsCount, executionCountSuccessMsg,
				executionCountFailureMsg);

		// Delete created Node
		base.stepInfo("Initiating delete nodes");
		saveSearch.deleteNode(Input.mySavedSearch, nodeToSelect);

		login.logout();

	}

	/**
	 * @Author Jeevitha @Date: 02/10/22 @Modified date:N/A @Modified by:N/A
	 * @Description : Verify that error message against every execution for search
	 *              group, where at least one search is referring to at least one
	 *              other searches in the same search group [RPMXCON-48581] - Sprint
	 *              12
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-48581", enabled = true, groups = { "regression" })
	public void verifyErrorMsgOfSearchGroup() throws Exception {
		String Search = "Search" + Utility.dynamicNameAppender();
		String Search2 = "Search" + Utility.dynamicNameAppender();
		String passMsg = "Search Errored out As Expected";
		String failMSg = "Search is Not Errored As Expected";

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("Test case Id: RPMXCON-48581 Saved Search - Sprint 12");
		base.stepInfo(
				"Verify that error message against every execution for search group, where at least one search is referring to at least one other searches in the same search group");

		String node = saveSearch.createSearchGroupAndReturn(Search, "RMU", Input.yesButton);

		session.advancedContentSearch(Input.searchString1);
		session.saveSearchInNewNode(Search, node);

		session.selectSavedsearchInASWp(Search);
		session.saveAndReturnPureHitCount();
		driver.scrollPageToTop();
		session.saveSearchInNewNode(Search2, node);

		saveSearch.navigateToSavedSearchPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(2);
		saveSearch.selectNode1(node);
		saveSearch.performExecute();

		saveSearch.savedSearch_SearchandSelect(Search2, Input.yesButton);
		String status = saveSearch.getLastStatus();
		base.textCompareEquals(status, "ERROR", passMsg, failMSg);

		// Delete created Node
		base.stepInfo("Initiating delete nodes");
		saveSearch.deleteNode(Input.mySavedSearch, node);

		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @Description:Verify that User can save a DRAFT basic search, before it is
	 *                     executed in Saved Search Screen. [RPMXCON-48448]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-48448", enabled = true, groups = { "regression" })
	public void verifyDraftStatusForBasic() throws InterruptedException {
		String SearchName = "Search" + Utility.dynamicNameAppender();
		String expectedStatus = "DRAFT";
		String passMsg = "SearchStatus is Displayed As DRAFT";
		String failMsg = "SearchStatus is Not DRAFT";

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case Id: RPMXCON-48448 - Saved Search");
		base.stepInfo("Verify that User can save a DRAFT basic search, before it is executed in Saved Search Screen.");

		// create Node as PA
		String node1 = saveSearch.createSearchGroupAndReturn(SearchName, "PA", Input.yesButton);

		// Basic Search
		session.basicMetaDataDraftSearch(Input.metaDataName, null, Input.metaDataCN, null);
		session.saveSearchInNewNode(SearchName, node1);

		// verify status
		saveSearch.navigateToSavedSearchPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(2);
		saveSearch.selectNode1(node1);
		saveSearch.savedSearch_SearchandSelect(SearchName, Input.yesButton);
		String actualStatus = saveSearch.getLastStatus();

		base.textCompareEquals(actualStatus, expectedStatus, passMsg, failMsg);

		// delete Search
		saveSearch.deleteNode(Input.mySavedSearch, node1);
	}

	/**
	 * @author Jeevitha
	 * @Description:Verify that User can save DRAFT Advanced search, before it is
	 *                     executed in Saved Search Screen. [RPMXCON-48449]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-48449", enabled = true, groups = { "regression" })
	public void verifyDraftStatusForAdvance() throws InterruptedException {
		String SearchName = "Search" + Utility.dynamicNameAppender();
		String expectedStatus = "DRAFT";
		String passMsg = "SearchStatus is Displayed As DRAFT";
		String failMsg = "SearchStatus is Not DRAFT";

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case Id: RPMXCON-48449 - Saved Search");
		base.stepInfo("Verify that User can save DRAFT Advanced search, before it is executed in Saved Search Screen.");

		// create Node as PA
		String node1 = saveSearch.createSearchGroupAndReturn(SearchName, "PA", Input.yesButton);

		// Advance Search
		session.advancedMetaDataForDraft(Input.metaDataName, null, Input.metaDataCN, null);
		session.saveSearchInNewNode(SearchName, node1);

		// verify status
		saveSearch.navigateToSavedSearchPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(2);
		saveSearch.selectNode1(node1);
		saveSearch.savedSearch_SearchandSelect(SearchName, Input.yesButton);
		String actualStatus = saveSearch.getLastStatus();

		base.textCompareEquals(actualStatus, expectedStatus, passMsg, failMsg);

		// delete Search
		saveSearch.deleteNode(Input.mySavedSearch, node1);
	}

	/**
	 * @Author Jeevitha
	 * @Description : RMU User - Verify that User can renames existing search Group
	 *              under the respective Security Group on Saved Search Screen.
	 *              [RPMXCON-48447]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-48447", enabled = true, groups = { "regression" })
	public void verifyRenameExistingSearchGroup() throws Exception {
		String securityGroup = "SG" + Utility.dynamicNameAppender();
		String securityTab = "Shared with " + securityGroup;

		SecurityGroupsPage security = new SecurityGroupsPage(driver);
		UserManagement userManagement = new UserManagement(driver);

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("Test case Id: RPMXCON-48447 Saved Search");
		base.stepInfo(
				"RMU User - Verify that User can renames existing search Group under the respective Security Group on Saved Search Screen.");

		// Craete security group
		security.navigateToSecurityGropusPageURL();
		security.AddSecurityGroup(securityGroup);

		// access to security group to Rmu
		userManagement.assignAccessToSecurityGroups(securityGroup, Input.rmu1userName);
		userManagement.saveSecurityGroup();

		login.logout();

		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// create Node In Other SG
		base.selectsecuritygroup(securityGroup);
		base.stepInfo("Selected Security Group : " + securityGroup);
		String node1 = saveSearch.createSearchGroupAndReturn(securityTab, "RMU", Input.yesButton);

		// create Node in Default SG
		base.selectsecuritygroup(Input.securityGroup);
		base.stepInfo("Selected Security Group : " + Input.securityGroup);
		String node2 = saveSearch.createSearchGroupAndReturn(Input.shareSearchDefaultSG, "RMU", Input.yesButton);

		// Rename Node In Default SG
		String renamedNode = saveSearch.renameSearchGroup(node2);

		base.selectsecuritygroup(securityGroup);
		base.stepInfo("Selected Security Group : " + securityGroup);
		String passMsg = renamedNode + " : is Not Available";

		// verify Renamed Node In Other SG
		saveSearch.navigateToSavedSearchPage();
		base.waitTillElemetToBeClickable(saveSearch.getSavedSearchGroupName(securityTab));
		saveSearch.getSavedSearchGroupName(securityTab).waitAndClick(5);
		saveSearch.selectNode1(node1);

		Element RenamedNodeInDef = saveSearch.getSavedSearchNodeWithRespectiveSG(securityTab, renamedNode);
		base.ValidateElement_Absence(RenamedNodeInDef, passMsg);

		// verify Renamed Node in Default SG
		base.selectsecuritygroup(Input.securityGroup);
		base.stepInfo("Selected Security Group : " + Input.securityGroup);
		saveSearch.verifyNodePresentInSG(Input.shareSearchDefaultSG, renamedNode);

		// Delete Node
		saveSearch.deleteNode(Input.shareSearchDefaultSG, renamedNode);

		login.logout();

		// Delete Other SG
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		security.deleteSecurityGroups(securityGroup);
		login.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description : RMU User - Verify that deleted Search Group does not appear
	 *              under the respective Security Group on Saved Search Screen.
	 *              [RPMXCON-48128]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-48128", enabled = true, groups = { "regression" })
	public void verifyDeletedSearchGroup() throws Exception {
		String securityGroup = "SG" + Utility.dynamicNameAppender();
		String securityTab = "Shared with " + securityGroup;

		SecurityGroupsPage security = new SecurityGroupsPage(driver);
		UserManagement userManagement = new UserManagement(driver);

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("Test case Id: RPMXCON-48128 Saved Search");
		base.stepInfo(
				"RMU User - Verify that deleted Search Group does not appear under the respective Security Group on Saved Search Screen.");

		// Craete security group
		security.navigateToSecurityGropusPageURL();
		security.AddSecurityGroup(securityGroup);

		// access to security group to Rmu
		userManagement.assignAccessToSecurityGroups(securityGroup, Input.rmu1userName);
		userManagement.saveSecurityGroup();

		login.logout();

		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// create Node In Other SG
		base.selectsecuritygroup(securityGroup);
		base.stepInfo("Selected Security Group : " + securityGroup);
		String node1 = saveSearch.createSearchGroupAndReturn(securityTab, "RMU", Input.yesButton);

		// create Node in Default SG
		base.selectsecuritygroup(Input.securityGroup);
		base.stepInfo("Selected Security Group : " + Input.securityGroup);
		String node2 = saveSearch.createSearchGroupAndReturn(Input.shareSearchDefaultSG, "RMU", Input.yesButton);
		String node3 = saveSearch.createSearchGroupAndReturn(Input.shareSearchDefaultSG, "RMU", Input.yesButton);

		// Delete Node In Default SG
		saveSearch.deleteNode(Input.shareSearchDefaultSG, node2);
		base.stepInfo("Deleted Node : " + node2);

		base.selectsecuritygroup(securityGroup);
		base.stepInfo("Selected Security Group : " + securityGroup);
		String passMsg = node2 + " : is Not Available";

		// verify Node In Other SG
		saveSearch.navigateToSavedSearchPage();
		base.waitTillElemetToBeClickable(saveSearch.getSavedSearchGroupName(securityTab));
		saveSearch.getSavedSearchGroupName(securityTab).waitAndClick(5);
		saveSearch.selectNode1(node1);

		Element RenamedNodeInDef = saveSearch.getSavedSearchNodeWithRespectiveSG(securityTab, node2);
		base.ValidateElement_Absence(RenamedNodeInDef, passMsg);

		// verify Node in Default SG
		base.selectsecuritygroup(Input.securityGroup);
		base.stepInfo("Selected Security Group : " + Input.securityGroup);

		saveSearch.navigateToSavedSearchPage();
		base.waitTillElemetToBeClickable(saveSearch.getSavedSearchGroupName(Input.shareSearchDefaultSG));
		saveSearch.getSavedSearchGroupName(Input.shareSearchDefaultSG).waitAndClick(5);
		saveSearch.selectNode1(node3);

		base.ValidateElement_Absence(RenamedNodeInDef, passMsg);

		// Delete Node
		saveSearch.deleteNode(Input.shareSearchDefaultSG, node3);

		login.logout();

		// Delete Other SG
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		security.deleteSecurityGroups(securityGroup);
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
