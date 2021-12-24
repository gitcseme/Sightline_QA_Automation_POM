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
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.ReportsPage;
import pageFactory.SavedSearch;
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
	 *              aggregate results set of "Shared With Project
	 *              Administrator/Shared With Default Security Group" and User
	 *              performs Execute option with Search groups [RPMXCON-49006]
	 * @param username
	 * @param password
	 * @param fullname
	 * @param SGtoShare
	 * @throws InterruptedException
	 */
	@Test(enabled = true, dataProvider = "UserAndShare", groups = { "regression" }, priority = 13)
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
		base.waitForElement(saveSearch.getSavedSearchNewGroupExpand());
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		saveSearch.navigateToSSPage();
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
	@Test(enabled = true, groups = { "regression" }, priority = 14)
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
