package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
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
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.ReportsPage;
import pageFactory.SavedSearch;
import pageFactory.SearchTermReportPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class SavedSearchRegression_New_Set_04 {

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
		Object[][] users = {
				{ Input.pa1userName, Input.pa1password, Input.pa1FullName, Input.shareSearchPA, Input.pa1userName,
						Input.pa1password },
				{ Input.pa1userName, Input.pa1password, Input.pa1FullName, Input.shareSearchDefaultSG,
						Input.pa1userName, Input.pa1password },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName, Input.shareSearchDefaultSG,
						Input.rmu1userName, Input.rmu1password },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName, Input.shareSearchDefaultSG,
						Input.rev1userName, Input.rev1password } };
		return users;
	}

	@DataProvider(name = "UserPaAndSaAndDa")
	public Object[][] UserPaAndSaAndDa() {
		Object[][] users = { { Input.pa1userName, Input.pa1password }, { Input.sa1userName, Input.sa1password },
				{ Input.da1userName, Input.da1password } };
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
	@Test(enabled = true, groups = { "regression" }, priority = 1)
	public void validateSharingAlreadySharedSGWithModificationsInMiddleOfHierarchyWithSecurityGroup() throws Exception {

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

		searchGroupSearchpIDpair_1 = saveSearch.collectionOfSearchIdsFromNodeCollections(newNodeList_1,
				nodeSearchpair_1, searchGroupSearchpIDpair_1);

		// share the 3th hierarchy level node with Default Security group
		saveSearch.navigateToSSPage();
		node = saveSearch.childNodeSelectionToShare(selectIndex1, newNodeList);
		System.out.println("Final : " + node);
		saveSearch.shareSavedNodePA(SGtoShare, node, false, true, nodeSearchpair.get(node));

		// verifying the searches as RMU
		saveSearch.navigateToSSPage();
		saveSearch.getSavedSearchGroupName(SGtoShare).waitAndClick(10);
		node = saveSearch.childNodeSelectionToShare(1, newNodeList);

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
	@Test(enabled = true, groups = { "regression" }, priority = 2)
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
	@Test(enabled = true, dataProvider = "UserPaAndSaAndDa", groups = { "regression" }, priority = 3)
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
		report.verifySearchFromTGReport(sourceOption, search, false, true);
		report.verifySearchFromTGReport(sourceOption, newNode, true, true);
		report.verifySearchFromTGReport(sourceOption, search0, false, false);
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
	@Test(enabled = true, groups = { "regression" }, priority = 4)
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
		base.waitForElement(saveSearch.getSavedSearchNewGroupExpand());
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);

		searchGroupSearchpIDpair = saveSearch.collectionOfSearchIdsFromNodeCollections(newNodeList, nodeSearchpair,
				searchGroupSearchpIDpair);

		// share the Root node with pa set_01
		saveSearch.navigateToSavedSearchPage();
		node = saveSearch.childNodeSelectionToShare(selectIndex, newNodeList);
		System.out.println("Final : " + node);
		saveSearch.shareSavedNodePA(SGtoShare, node, false, true, nodeSearchpair.get(node));
		driver.waitForPageToBeReady();
		saveSearch.verifyImpactinSharedchildNodes(SGtoShare, newNodeList, selectIndex, nodeSearchpair,
				searchGroupSearchpIDpair);

		// Adding new searches to root node and leaf node
		driver.getWebDriver().get(Input.url + "Search/Searches");
		HashMap<String, String> nodeNewSearchpair = session.addSearchInNodewithChildNode(newNodeList, 1);

		// modify existing search Name
		saveSearch.navigateToSavedSearchPage();
		driver.waitForPageToBeReady();
		base.waitForElement(saveSearch.getSavedSearchNewGroupExpand());
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		Map<String, String> nodeRenameSearchpair = saveSearch.modifyExistingSearchName(newNodeList, nodeSearchpair, 1);
		for (int i = 0; i < nodeRenameSearchpair.size(); i++) {
			nodeSearchpair.replace(newNodeList.get(i), nodeSearchpair.get(newNodeList.get(i)),
					nodeRenameSearchpair.get(newNodeList.get(i)));
		}

		// collecting search_ID in modified search in my saved search
		saveSearch.navigateToSavedSearchPage();
		base.waitForElement(saveSearch.getSavedSearchNewGroupExpand());
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		HashMap<String, String> modifiedSearchIDpair = new HashMap<String, String>();
		modifiedSearchIDpair = saveSearch.collectionOfSearchIdsFromNodeCollections(newNodeList, nodeSearchpair,
				modifiedSearchIDpair);

		// collecting search_ID in add search in my saved search
		saveSearch.navigateToSavedSearchPage();
		base.waitForElement(saveSearch.getSavedSearchNewGroupExpand());
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		HashMap<String, String> addSearchIDpair = new HashMap<String, String>();
		addSearchIDpair = saveSearch.collectionOfSearchIdsFromNodeCollections(newNodeList, nodeNewSearchpair,
				addSearchIDpair);

		// share the Root node with pa set_02
		saveSearch.navigateToSavedSearchPage();
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
	@Test(enabled = true, groups = { "regression" }, priority = 5)
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
		base.waitForElement(saveSearch.getSavedSearchNewGroupExpand());
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);

		searchGroupSearchpIDpair = saveSearch.collectionOfSearchIdsFromNodeCollections(newNodeList, nodeSearchpair,
				searchGroupSearchpIDpair);

		// share the Root node with pa set_01
		saveSearch.navigateToSavedSearchPage();
		node = saveSearch.childNodeSelectionToShare(selectIndex, newNodeList);
		System.out.println("Final : " + node);
		saveSearch.shareSavedNodePA(SGtoShare, node, false, true, nodeSearchpair.get(node));
		driver.waitForPageToBeReady();
		saveSearch.verifyImpactinSharedchildNodes(SGtoShare, newNodeList, selectIndex, nodeSearchpair,
				searchGroupSearchpIDpair);

		// Adding new searches to root node and leaf node
		driver.getWebDriver().get(Input.url + "Search/Searches");
		HashMap<String, String> nodeNewSearchpair = session.addSearchInNodewithChildNode(newNodeList, 1);

		// modify existing search Name
		saveSearch.navigateToSavedSearchPage();
		driver.waitForPageToBeReady();
		base.waitForElement(saveSearch.getSavedSearchNewGroupExpand());
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		Map<String, String> nodeRenameSearchpair = saveSearch.modifyExistingSearchName(newNodeList, nodeSearchpair, 1);
		for (int i = 0; i < nodeRenameSearchpair.size(); i++) {
			nodeSearchpair.replace(newNodeList.get(i), nodeSearchpair.get(newNodeList.get(i)),
					nodeRenameSearchpair.get(newNodeList.get(i)));
		}

		// collecting search_ID in modified search in my saved search
		saveSearch.navigateToSavedSearchPage();
		base.waitForElement(saveSearch.getSavedSearchNewGroupExpand());
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		HashMap<String, String> modifiedSearchIDpair = new HashMap<String, String>();
		modifiedSearchIDpair = saveSearch.collectionOfSearchIdsFromNodeCollections(newNodeList, nodeSearchpair,
				modifiedSearchIDpair);

		// collecting search_ID in add search in my saved search
		saveSearch.navigateToSavedSearchPage();
		base.waitForElement(saveSearch.getSavedSearchNewGroupExpand());
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		HashMap<String, String> addSearchIDpair = new HashMap<String, String>();
		addSearchIDpair = saveSearch.collectionOfSearchIdsFromNodeCollections(newNodeList, nodeNewSearchpair,
				addSearchIDpair);

		// share the Root node with pa set_02
		saveSearch.navigateToSavedSearchPage();
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
	@Test(enabled = true, groups = { "regression" }, priority = 6)
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
	@Test(enabled = true, groups = { "regression" }, priority = 7)
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
	@Test(enabled = true, groups = { "regression" }, priority = 8)
	public void performBulkActionsAgainstMySavedSearches() throws InterruptedException {

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
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);
		base.stepInfo("Test case Id: RPMXCON-57388 SavedSearch Sprint 08");
		base.stepInfo(
				"PA impersonate down as RMU/RU role, create Searchgroups and Searches, and then perform bulk actions against My saved searches in PAU role");

		String newNodePA = saveSearch.createASearchGroupandReturnName(searchPA);
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchPA);
		session.saveSearchInNewNode(searchPANode, newNodePA);
		base.stepInfo("Created searches and saved as PA");

		base.rolesToImp("PA", "RMU");

		String newNodeRMU = saveSearch.createASearchGroupandReturnName(searchRMU);
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchRMU);
		session.saveSearchInNewNode(searchRMUNode, newNodeRMU);
		base.stepInfo("Created searches and saved as RMU");

		base.rolesToImp("RMU", "REV");

		String newNodeREV = saveSearch.createASearchGroupandReturnName(searchREV);
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
		saveSearch.getToDocView().waitAndClick(5);

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
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 9)
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

		String newNodePA = saveSearch.createASearchGroupandReturnName(searchPA);
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchPA);
		session.saveSearchInNewNode(searchPANode, newNodePA);
		base.stepInfo("Created searches and saved as PA");

		base.rolesToImp("PA", "RMU");

		String newNodeRMU = saveSearch.createASearchGroupandReturnName(searchRMU);
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchRMU);
		session.saveSearchInNewNode(searchRMUNode, newNodeRMU);
		base.stepInfo("Created searches and saved as RMU");

		base.rolesToImp("RMU", "REV");

		String newNodeREV = saveSearch.createASearchGroupandReturnName(searchREV);
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
		saveSearch.getToDocView().waitAndClick(5);

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
	@Test(enabled = true, groups = { "regression" }, priority = 10)
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

		String newNodePA = saveSearch.createASearchGroupandReturnName(searchPA);
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchPA);
		session.saveSearchInNewNode(searchPANode, newNodePA);
		base.stepInfo("Created searches and saved as PA");

		base.rolesToImp("PA", "RMU");

		String newNodeRMU = saveSearch.createASearchGroupandReturnName(searchRMU);
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchRMU);
		session.saveSearchInNewNode(searchRMUNode, newNodeRMU);
		base.stepInfo("Created searches and saved as RMU");

		base.rolesToImp("RMU", "REV");

		String newNodeREV = saveSearch.createASearchGroupandReturnName(searchREV);
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
		saveSearch.getToDocView().waitAndClick(5);

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
	@Test(enabled = true, groups = { "regression" }, priority = 11)
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
		session.saveSearchInRootNode(searchName1, parentNode, childNode);

		// Share the node with Default security group
		saveSearch.navigateToSavedSearchPage();
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		saveSearch.shareSavedNodeToSG(Input.securityGroup, parentNode, searchName);

		// Executing the Search Group
		saveSearch.navigateToSavedSearchPage();
		saveSearch.getSavedSearchGroupName(Input.securityGroup).waitAndClick(10);
		saveSearch.selectNode1(parentNode);
		saveSearch.savedSearchExecute_SearchGRoup(searchName, pureHit);

		// verifying the Child Node present in the Node
		saveSearch.verifyNodePresentInSG(Input.shareSearchDefaultSG, parentNode);
		base.waitForElement(saveSearch.getSavedSearchNewGroupExpand());
		saveSearch.getSavedSearchNewGroupExpand().Click();
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
	@Test(enabled = true, groups = { "regression" }, priority = 12)
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

		base.stepInfo("-------Pre-requesties completed--------");

		// Search ID collection set 2
		saveSearch.navigateToSSPage();
		saveSearch.getSavedSearchGroupName(SGtoShare).waitAndClick(10);
		base.waitForElement(saveSearch.getSavedSearchNewGroupExpand());
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);

		searchGroupSearchpIDpair2 = saveSearch.collectionOfSearchIdsFromNodeCollections(newNodeList, nodeSearchpair,
				searchGroupSearchpIDpair);

		// Verify shared SG/Searches
		saveSearch.navigateToSSPage();
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
//							 TODO: handle exception
			}
		}
		try {
			login.quitBrowser();
		} catch (Exception e) {
			login.quitBrowser();
			login.clearBrowserCache();
		}
		System.out.println("Executed :" + result.getMethod().getMethodName());
	}

	@AfterClass(alwaysRun = true)
	public void close() {

		try {
			login.clearBrowserCache();
		} finally {
			login.clearBrowserCache();
		}
	}

}
