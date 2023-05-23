package sightline.savedSearch;

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
import automationLibrary.ElementCollection;
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

public class SavedSearch_Phase1_Regression {

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
	SchedulesPage schedulePage;

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
		driver = new Driver();

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

	@DataProvider(name = "user_Roles")
	public Object[][] user_Roles() {
		Object[][] users = { { "RMU" }, { "REV" }, { "PA" } };
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

	@DataProvider(name = "UserSaUDaUPaU")
	public Object[][] UserSaUDaUPaU() {
		Object[][] users = { { Input.sa1userName, Input.sa1password, Input.sa1userName, "SA" },
				{ Input.da1userName, Input.da1password, Input.da1userName, "DA" },
				{ Input.pa1userName, Input.pa1password, Input.pa1FullName, "PA" } };
		return users;
	}

	@DataProvider(name = "SavedSearchwithoutReviewer")
	public Object[][] SavedSearchwithoutReviewer() {
		Object[][] users = { { Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password } };
		return users;
	}

	/**
	 * @author Raghuram Modified date:N/A Modified by: N/A - modified Dataprovider
	 *         parameter
	 * @throws InterruptedException
	 */

	@DataProvider(name = "PAandRMU")
	public Object[][] PAandRMU() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName } };
		return users;
	}

	@DataProvider(name = "SavedSearchwithRMUandREVwithSave")
	public Object[][] SavedSearchwithRMUandREVwithSave() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, Input.rmu1FullName, "MySaveSearch" },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName, "Folder" },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName, "MySaveSearch" },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName, "Folder" } };
		return users;
	}

	@DataProvider(name = "SavedSearchwithRMUandREVandPAwithSave")
	public Object[][] SavedSearchwithRMUandREVandPAwithSave() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName, "MySaveSearch" },
				{ Input.pa1userName, Input.pa1password, Input.pa1FullName, "Folder" },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName, "MySaveSearch" },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName, "Folder" },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName, "MySaveSearch" },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName, "Folder" } };
		return users;
	}

	@DataProvider(name = "SavedSearchwithSAImpAllRoles")
	public Object[][] SavedSearchwithSAImpAllRoles() {
		Object[][] users = { { Input.sa1userName, Input.sa1password, "SA", "PA", "MySaveSearch" },
				{ Input.sa1userName, Input.sa1password, "SA", "PA", "Folder" },
				{ Input.sa1userName, Input.sa1password, "SA", "RMU", "MySaveSearch" },
				{ Input.sa1userName, Input.sa1password, "SA", "RMU", "Folder" },
				{ Input.sa1userName, Input.sa1password, "SA", "REV", "MySaveSearch" },
				{ Input.sa1userName, Input.sa1password, "SA", "REV", "Folder" } };
		return users;
	}

	@DataProvider(name = "SavedSearchwithOnlyPAschedule")
	public Object[][] SavedSearchwithOnlyPAschedule() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName, "MySaveSearch" },
				{ Input.pa1userName, Input.pa1password, Input.pa1FullName, "Folder" } };
		return users;
	}

	@DataProvider(name = "SAwithAllroles")
	public Object[][] SAwithAllroles() {
		Object[][] users = {
//				{ Input.sa1userName, Input.sa1password, "SA", "Folder", "PA" },
				{ Input.sa1userName, Input.sa1password, "SA", "SubFolder", "PA" },
				{ Input.sa1userName, Input.sa1password, "SA", "SubFolder", "RMU" },
				{ Input.sa1userName, Input.sa1password, "SA", "SubFolder", "RMU" },
				{ Input.sa1userName, Input.sa1password, "SA", "SubFolder", "REV" },
				{ Input.sa1userName, Input.sa1password, "SA", "SubFolder", "REV" } };
		return users;
	}

	@DataProvider(name = "DAUwithAllroles")
	public Object[][] DAUwithAllroles() {
		Object[][] users = { { Input.da1userName, Input.da1password, "DA", "Folder", "PA" },
				{ Input.da1userName, Input.da1password, "DA", "SubFolder", "PA" },
				{ Input.da1userName, Input.da1password, "DA", "Folder", "RMU" },
				{ Input.da1userName, Input.da1password, "DA", "SubFolder", "RMU" },
				{ Input.da1userName, Input.da1password, "DA", "Folder", "REV" },
				{ Input.da1userName, Input.da1password, "DA", "SubFolder", "REV" } };
		return users;
	}

	@DataProvider(name = "DAUimpRMUandREV")
	public Object[][] DAUimpRMUandREV() {
		Object[][] users = { { Input.da1userName, Input.da1password, "DA", "MySaveSearch", "RMU" },
				{ Input.da1userName, Input.da1password, "DA", "Folder", "RMU" },
				{ Input.da1userName, Input.da1password, "DA", "MySaveSearch", "REV" },
				{ Input.da1userName, Input.da1password, "DA", "Folder", "REV" } };
		return users;
	}

	@DataProvider(name = "PAUimpRMUandREV")
	public Object[][] PAUimpRMUandREV() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, "PA", Input.shareSearchDefaultSG, "RMU" },
				{ Input.pa1userName, Input.pa1password, "PA", Input.shareSearchDefaultSG, "REV" } };
		return users;
	}

	/**
	 * @author Raghuram A Date: 9/07/21 Modified date:N/A Modified by:N/A
	 *         Description : Pre-requisites: 1. Project with 1K documents has
	 *         security groups SG1 and SG2 2. SG1 and SG2 has different sub-set of
	 *         project documents, around 100 in each
	 */
	@Test(description = "RPMXCON-54967", enabled = true, groups = { "regression" })
	public void preRequesties() throws InterruptedException {

		List<String> list = new ArrayList<String>();
		String searchName = "Search Name" + Utility.dynamicNameAppender();
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();

		// Login via PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As PA");

		sg = new SecurityGroupsPage(driver);
		String securitygroupname1 = "securitygroupname" + Utility.dynamicNameAppender();
		String securitygroupname2 = "securitygroupname" + Utility.dynamicNameAppender();

		sg.navigateToSecurityGropusPageURL();
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
	 * @author Raghuram A Created on : 11/3/21 modified on : N/A by : N/ADescription
	 *         : RMU/RU re-executing searches shared to Security group and verify
	 *         documents (RPMXCON-57380) Sprint 05
	 * @throws InterruptedException
	 * @Stabilization - done
	 */
	@Test(description = "RPMXCON-57380", enabled = true, dataProvider = "SavedSearchwithRMUandREVwithSave", groups = {
			"regression" })
	public void metaDataCombinationSharedSGExecution(String username, String password, String fullName, String saveFlow)
			throws InterruptedException, ParseException {

		int pureHits, finalCountresult;
		String exeDocCount;
		String SearchName = "SaveSearch" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-54967 - Saved Search Sprint 05");
		base.stepInfo("RMU/RU re-executing searches shared to Security group and verify documents");
		// Login as USER
		login.loginToSightLine(username, password);
		base.stepInfo("Loggedin As : " + fullName);

		pureHits = saveSearch.saveFlowCheckMethodWithShare(saveFlow, SearchName, username);
		saveSearch.savedSearch_SearchandSelect(SearchName, "Yes");
		exeDocCount = saveSearch.savedSearchExecute_Draft(SearchName, pureHits);

		// Get the count of total no.of document list
		finalCountresult = saveSearch.launchDocListViaSSandReturnDocCount();

		if (Integer.parseInt(exeDocCount) == finalCountresult) {
			softAssertion.assertEquals(Integer.parseInt(exeDocCount), finalCountresult);
			base.passedStep(
					"Shows all documents that are in the aggregate results set of all child search groups and searches");
		} else {
			base.failedStep("Count Mismatches");
		}

		login.logout();
	}

	/**
	 * @author Raghuram A Created on : 11/3/21 modified on : N/A by : N/ADescription
	 *         : RMU/RU re-executing searches saved under My Saved Searches and
	 *         verify documents (RPMXCON-57379) Sprint 05
	 * @throws InterruptedException
	 * @Stabilization - done
	 */
	@Test(description = "RPMXCON-57379", enabled = true, dataProvider = "SavedSearchwithRMUandREVwithSave", groups = {
			"regression" })
	public void metaDataCombinationMYSSnode(String username, String password, String fullName, String saveFlow)
			throws InterruptedException, ParseException {

		int pureHits, finalCountresult;
		String exeDocCount;
		String SearchName = "SaveSearch" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-57379 - Saved Search Sprint 05");
		base.stepInfo("RMU/RU re-executing searches saved under My Saved Searches and verify documents");
		// Login as RMU
		login.loginToSightLine(username, password);
		base.stepInfo("Loggedin As : " + fullName);

		pureHits = saveSearch.saveFlowCheckMethod(saveFlow, SearchName, username);
		saveSearch.savedSearch_SearchandSelect(SearchName, "Yes");
		exeDocCount = saveSearch.savedSearchExecute_Draft(SearchName, pureHits);

		// Get the count of total no.of document list
		finalCountresult = saveSearch.launchDocListViaSSandReturnDocCount();

		if (Integer.parseInt(exeDocCount) == finalCountresult) {
			softAssertion.assertEquals(Integer.parseInt(exeDocCount), finalCountresult);
			base.passedStep(
					"Shows all documents that are in the aggregate results set of all child search groups and searches");
		} else {
			base.failedStep("Count Mismatches");
		}

		login.logout();
	}

	/**
	 * @author Jeevitha Description :Validate deleting search shared by PAU/RMU to
	 *         specific <Security Group Name> by RMU/PAU user (RPMXCON-49896
	 *         Sprint-5)
	 */
	@Test(description = "RPMXCON-49896", enabled = true, groups = { "regression" })
	public void sharedDeleteAction() throws InterruptedException {

		String SearchName = "search0" + Utility.dynamicNameAppender();
		String SearchName1 = "search01" + Utility.dynamicNameAppender();

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case Id: RPMXCON-49896 - Saved Search");
		base.stepInfo("Validate deleting search shared by PAU/RMU to specific <Security Group Name> by RMU/PAU user");

		session.basicContentSearch(Input.searchString1);
		session.saveSearch(SearchName);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		base.stepInfo("Landed on SavedSearchPage");

		saveSearch.verify_sharedDSGroup(SearchName, Input.shareSearchDefaultSG);
		login.logout();

		// Login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		saveSearch.selectSavedSearchTAb(SearchName, Input.shareSearchDefaultSG, "No");

		session.basicContentSearch(Input.searchString1);
		session.saveSearch(SearchName1);

		saveSearch.navigateToSavedSearchPage();
		saveSearch.selectRootGroupTab(Input.mySavedSearch);
		saveSearch.verify_sharedDSGroup(SearchName1, Input.shareSearchDefaultSG);
		login.logout();

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		saveSearch.selectSavedSearchTAb(SearchName1, Input.shareSearchDefaultSG, "No");
		login.logout();

	}

	/**
	 * @author Raghuram A Created on : 11/3/21 modified on : N/A by : N/ADescription
	 *         : RMU/RU re-executing searches shared to Security group and verify
	 *         documents (RPMXCON-57383) Sprint 05
	 * @throws InterruptedException
	 * @Stabilization - done
	 */
	@Test(description = "RPMXCON-57383", enabled = true, dataProvider = "SavedSearchwithRMUandREVandPAwithSave", groups = {
			"regression" })
	public void metaDataCombinationSharedSGExecutionwithAllRoles(String username, String password, String fullName,
			String saveFlow) throws InterruptedException, ParseException {

		int pureHits, finalCountresult;
		String exeDocCount;
		String SearchName = "SaveSearch" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-57383 - Saved Search Sprint 05");
		base.stepInfo(
				"PAU saving search under <Shared with SecurityGroupName> re-executing searches and verify documents");
		// Login as USER
		login.loginToSightLine(username, password);
		base.stepInfo("Loggedin As : " + fullName);

		pureHits = saveSearch.saveFlowCheckMethodWithShare(saveFlow, SearchName, username);
		saveSearch.savedSearch_SearchandSelect(SearchName, "Yes");
		exeDocCount = saveSearch.savedSearchExecute_Draft(SearchName, pureHits);

		// Get the count of total no.of document list
		finalCountresult = saveSearch.launchDocListViaSSandReturnDocCount();

		if (Integer.parseInt(exeDocCount) == finalCountresult) {
			softAssertion.assertEquals(Integer.parseInt(exeDocCount), finalCountresult);
			base.passedStep(
					"Shows all documents that are in the aggregate results set of all child search groups and searches");
		} else {
			base.failedStep("Count Mismatches");
		}

		login.logout();
	}

	/**
	 * @author Raghuram A Created on : 11/18/21 modified on : N/A by : N/A
	 *         Description : Verify that Batch Search Upload Option disabled when
	 *         user Navigate Back from DocList on Saved Search Screen
	 *         (RPMXCON-49036) Sprint 05
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-49036", enabled = true, groups = { "regression" })
	public void batchSearchUploadDisableCheck() throws InterruptedException {

		String searchName = "Search Name" + Utility.dynamicNameAppender();
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		Boolean inputValue = true;
		int nodesToCreate = 2;
		int selectIndex = 0;
		String node;
		int finalCountresult;
		int latencyCheckTime = 5;
		String passMessage = "Application not hang or shows latency more than " + latencyCheckTime + " seconds.";
		String failureMsg = "Continues Loading more than " + latencyCheckTime + " seconds.";

		String SGtoShare = Input.shareSearchDefaultSG;

		base.stepInfo("Test case Id: RPMXCON-49036 - Saved Search Sprint 05");
		base.stepInfo(
				"Verify that Batch Search Upload Option disabled  when user Navigate Back from DocList on Saved Search Screen");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Landed on Saved Search
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		// Multiple Node Creation
		newNodeList = saveSearch.createSGAndReturn("PA", "No", nodesToCreate);
		System.out.println("Next adding searches to the created nodes");
		base.stepInfo("Next adding searches to the created nodes");

		// add save search in node
		driver.getWebDriver().get(Input.url + "Search/Searches");
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		node = saveSearch.childNodeSelectionToShare(selectIndex, newNodeList);
		System.out.println("Final : " + node);
		saveSearch.shareSavedNodePA(SGtoShare, node, false, true, nodeSearchpair.get(node));

		base.stepInfo("-------Pre-requesties completed--------");
		login.logout();

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Calculate the unique doc count
		driver.getWebDriver().get(Input.url + "Search/Searches");
//		session.basicContentSearchWithSaveChanges(Input.searchString5, "No", "First");
//		base.hitEnterKey(1);
//		session.selectOperatorInBasicSearch("OR");
		int hitCount = session.basicContentSearchWithSaveChanges(Input.searchString5, "Yes", "Third");
		base.stepInfo("Unique doc PureHit count : " + hitCount);

		// Launch DocList via Saved Search
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		String selectedNode = newNodeList.get(0);
		System.out.println("Root node : " + newNodeList.get(0));
		base.stepInfo("Root node : " + newNodeList.get(0));
		saveSearch.getSavedSearchGroupName(SGtoShare).waitAndClick(10);
		saveSearch.selectNode1(newNodeList.get(0));
		saveSearch.savedSearch_SearchandSelect(nodeSearchpair.get(selectedNode),"Yes");
		Element docListIcon = saveSearch.getDocListIcon();
		saveSearch.checkButtonDisabled(docListIcon, "Should be enabled", "DocList Icon");

		// Get the count of total no.of document list

		saveSearch.launchDocListViaSS(latencyCheckTime, passMessage, failureMsg);
		driver.waitForPageToBeReady();
		base.waitTime(2);
		finalCountresult = saveSearch.docListPageFooterCountCheck();
		System.out.println(hitCount + " : : " + finalCountresult);
		if (hitCount == finalCountresult) {
			softAssertion.assertEquals(hitCount, finalCountresult);
			base.passedStep(
					"Shows all documents that are in the aggregate results set of all child search groups and searches");
		} else {
			base.failedStep("Count Mismatches");
		}

		saveSearch.navigateBackToSavedSearchViaBacktoSourceDOcList(true, true, selectedNode);

		login.logout();

	}

	/**
	 * Author : Raghuram date: NA Modified date:NA Modified by: N/A TODO:BUG IN
	 * APPLICATION. EXPECT FAILURE ON THIS TEST CASE Description :PAU Schedule saved
	 * searches(with audio and non-audio docs) under <Shared with Project
	 * Administrator> and verify documents ( RPMXCON-57400 Sprint 05 )
	 */
	@Test(description = "RPMXCON-57400", enabled = true, dataProvider = "SavedSearchwithOnlyPAschedule", groups = {
			"regression" })
	public void scheduleSearchAudioandNonAudio(String username, String password, String fullName, String saveFlow)
			throws InterruptedException, ParseException {

		int pureHits, finalCountresult;
		String exeDocCount;
		String shareTo = Input.shareSearchPA;
		String SearchName = "Search" + Utility.dynamicNameAppender();

		// Login as PA
		login.loginToSightLine(username, password);
		base.stepInfo("Test case Id: RPMXCON-57400 - Saved Search");
		base.stepInfo(
				"PAU Schedule saved searches(with audio and non-audio docs) under <Shared with Project Administrator> and verify documents");
		base.failedMessage("Expected FAILURE case : RPMXCON-57400");

		// create new searchGroup and Save Search Audio / Non-Audio
		pureHits = saveSearch.saveFlowCheckAudioNonaudioWithShare(saveFlow, SearchName, fullName, shareTo);
		saveSearch.scheduleSavedSearchInMinute(SearchName,2);
		//// ----- Todo need to continue the script coding as it is failing to Schedule
		saveSearch.savedSearch_SearchandSelect(SearchName, "Yes");
		exeDocCount = saveSearch.getSavedSearchCount(SearchName).getText();

		// Get the count of total no.of document list
		finalCountresult = saveSearch.launchDocListViaSSandReturnDocCount();

		base.digitCompareEquals(Integer.parseInt(exeDocCount), finalCountresult,
				"Shows all documents that are in the aggregate results set of all child search groups and searches",
				"Count Mismatches");

		login.logout();

	}

	/**
	 * Author : Raghuram date: NA Modified date:NA Modified by: N/A TODO:BUG IN
	 * APPLICATION. EXPECT FAILURE ON THIS TEST CASE Description :Schedule saved
	 * searches(with audio and non-audio docs) under <Shared folders> and verify
	 * documents ( RPMXCON-57399 Sprint 05 )
	 */
	@Test(description = "RPMXCON-57399", enabled = true, dataProvider = "SavedSearchwithOnlyPAschedule", groups = {
			"regression" })
	public void scheduleSearchAudioandNonAudioSG(String username, String password, String fullName, String saveFlow)
			throws InterruptedException, ParseException {

		int pureHits, finalCountresult;
		String exeDocCount;
		String shareTo = Input.shareSearchDefaultSG;
		String SearchName = "Search" + Utility.dynamicNameAppender();

		// Login as PA
		login.loginToSightLine(username, password);
		base.stepInfo("Test case Id: RPMXCON-57399 - Saved Search");
		base.stepInfo(
				"Schedule saved searches(with audio and non-audio docs) under <Shared folders> and verify documents");
		base.failedMessage("Expected FAILURE case : RPMXCON-57399");

		// create new searchGroup and Save Search Audio / Non-Audio
		pureHits = saveSearch.saveFlowCheckAudioNonaudioWithShare(saveFlow, SearchName, fullName, shareTo);
		saveSearch.scheduleSavedSearches(SearchName);

		// ---Todo need to continue the script coding as it is failing to Schedule---//
		saveSearch.savedSearch_SearchandSelect(SearchName, "Yes");
		exeDocCount = saveSearch.getSavedSearchCount(SearchName).getText();

		// Get the count of total no.of document list
		finalCountresult = saveSearch.launchDocListViaSSandReturnDocCount();

		base.digitCompareEquals(Integer.parseInt(exeDocCount), finalCountresult,
				"Shows all documents that are in the aggregate results set of all child search groups and searches",
				"Count Mismatches");

		login.logout();

	}

	/**
	 * Author : Raghuram date: NA Modified date:NA Modified by: N/A TODO:BUG IN
	 * APPLICATION. EXPECT FAILURE ON THIS TEST CASE Description : Security group
	 * contains subset of project documents - Schedule saved searches(with audio and
	 * non-audio docs) under <My Saved Search> and verify documents ( RPMXCON-57401
	 * Sprint 05 )
	 */
	@Test(description = "RPMXCON-57401", enabled = true, dataProvider = "SavedSearchwithOnlyPAschedule", groups = {
			"regression" })
	public void scheduleSearchAudioandNonAudioSave(String username, String password, String fullName, String saveFlow)
			throws InterruptedException, ParseException {

		int pureHits, finalCountresult;
		String exeDocCount;
		String SearchName = "Search" + Utility.dynamicNameAppender();

		// Login as PA
		login.loginToSightLine(username, password);
		base.stepInfo("Test case Id: RPMXCON-57401 - Saved Search");
		base.stepInfo(
				"Security group contains subset of project documents - Schedule saved searches(with audio and non-audio docs) under <My Saved Search> and verify documents");
		base.failedMessage("Expected FAILURE case : RPMXCON-57401");

		// create new searchGroup and Save Search Audio / Non-Audio
		pureHits = saveSearch.saveFlowCheckAudioNonaudioWithoutShare(saveFlow, SearchName, fullName);
		saveSearch.scheduleSavedSearches(SearchName);

		// ---Todo need to continue the script coding as it is failing to Schedule---//
		saveSearch.savedSearch_SearchandSelect(SearchName, "Yes");
		exeDocCount = saveSearch.getSavedSearchCount(SearchName).getText();

		// Get the count of total no.of document list
		finalCountresult = saveSearch.launchDocListViaSSandReturnDocCount();

		base.digitCompareEquals(Integer.parseInt(exeDocCount), finalCountresult,
				"Shows all documents that are in the aggregate results set of all child search groups and searches",
				"Count Mismatches");

		login.logout();

	}

	/**
	 * @author Raghuram A Created on : 11/3/21 modified on : N/A by : N/A
	 *         Description : To verify, As an Admin user login, when user select any
	 *         folder or sub folder (Node) in saved search page under search group,
	 *         that will be highlighted as Admin(RPMXCON-47421) Sprint 06
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-47421", enabled = true, groups = { "regression" })
	public void folderHighlightCheck() throws InterruptedException, ParseException {

		String SearchName = "Search" + Utility.dynamicNameAppender();
		String passMsg = "Selected folder is highlighted";
		String failMsg = "Selected folder is Not highlighted / highlighted with wrong color code";

		base.stepInfo("Test case Id: RPMXCON-47421 - Saved Search Sprint 06");
		base.stepInfo(
				"To verify, As an Admin user login, when user select any folder or sub folder (Node) in saved search page under search group, that will be highlighted as Admin");
		// Login as USER
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		String newNode = saveSearch.createSearchGroupAndReturn(SearchName, Input.pa1FullName, "No");
		saveSearch.getCreatedNode(newNode).waitAndClick(5);
		driver.waitForPageToBeReady();
		base.stepInfo("Selected : " + newNode);
		String bgColor = saveSearch.getCreatedNode(newNode).GetCssValue("background-color");
		System.out.println(bgColor);
		bgColor = base.rgbTohexaConvertor(bgColor);

		// Text Comparision
		base.textCompareEquals(bgColor, Input.selectionHighlightColor, passMsg, failMsg);

		// Delete node
		base.stepInfo("Initiating delete node");
		saveSearch.deleteNode(Input.mySavedSearch, newNode);

		login.logout();
	}

	/**
	 * @author Raghuram A Created on : 11/3/21 modified on : N/A by : N/A
	 *         Description : SA impersonate down as PAU/RMU/RU, save search under
	 *         <Shared with SecurityGroupName>directly, re-execute saved search and
	 *         verify documents (RPMXCON-57385) Sprint 06
	 * @throws InterruptedException
	 * @Stablization - done
	 */
	@Test(description = "RPMXCON-57385", enabled = true, dataProvider = "SAwithAllroles", groups = { "regression" })
	public void metaDataCombinationSharedSGExecutionwithSAimpAllRoles(String username, String password, String userRole,
			String saveFlow, String impToRole) throws InterruptedException, ParseException {

		int pureHits;
		String exeDocCount;
		String SearchName = "SaveSearch" + Utility.dynamicNameAppender();
		String executionCountSuccessMsg = "Search result matches with the earlier count.";
		String executionCountFailureMsg = "Count Mismatches";

		base.stepInfo("Test case Id: RPMXCON-57385 - Saved Search Sprint 06");
		base.stepInfo(
				"SA impersonate down as PAU/RMU/RU, save search under <Shared with SecurityGroupName>directly, re-execute saved search and verify documents");
		// Login as USER
		login.loginToSightLine(username, password);
		base.stepInfo("Loggedin As : " + userRole);
		base.rolesToImp(userRole, impToRole);

		pureHits = saveSearch.saveFlowCheckMethod(saveFlow, SearchName, username);
		saveSearch.savedSearch_SearchandSelect(SearchName, "Yes");
		exeDocCount = saveSearch.savedSearchExecute_Draft(SearchName, pureHits);
		base.digitCompareEquals(pureHits, Integer.parseInt(exeDocCount), executionCountSuccessMsg,
				executionCountFailureMsg);

		// Get the count of total no.of document list
		int docCount = saveSearch.launchDocListViaSSandReturnDocCount();
		int reExeCount = Integer.parseInt(exeDocCount);
		base.digitCompareEquals(docCount, reExeCount, executionCountSuccessMsg, executionCountFailureMsg);

		login.logout();
	}

	/**
	 * @author Raghuram A Created on : 11/12/21 modified on : N/A by : N/A
	 *         Description : DAU impersonate down as PAU, save search under <Shared
	 *         with SecurityGroupName>directly, re-execute saved searches and verify
	 *         documents (RPMXCON-57384) Sprint 06
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57384", enabled = true, dataProvider = "DAUwithAllroles", groups = { "regression" })
	public void metaDataCombinationSharedSGExecutionwithDAimpAllRoles(String username, String password, String userRole,
			String saveFlow, String impToRole) throws InterruptedException, ParseException {

		int pureHits, finalCountresult;
		String exeDocCount;
		String SearchName = "SaveSearch" + Utility.dynamicNameAppender();
		String executionCountSuccessMsg = "Search result matches with the earlier count.";
		String executionCountFailureMsg = "Count Mismatches";
		String executionCountWithTagSuccessMsg = "Doc count matches with DocList count.";

		base.stepInfo("Test case Id: RPMXCON-57384 - Saved Search Sprint 06");
		base.stepInfo(
				"DAU impersonate down as PAU, save search under <Shared with SecurityGroupName>directly, re-execute saved searches and verify documents");
		// Login as USER
		login.loginToSightLine(username, password);
		base.stepInfo("Loggedin As : " + userRole);
		base.rolesToImp(userRole, impToRole);

		pureHits = saveSearch.saveFlowCheckMethod(saveFlow, SearchName, username);
		saveSearch.savedSearch_SearchandSelect(SearchName, "Yes");
		exeDocCount = saveSearch.savedSearchExecute_Draft(SearchName, pureHits);
		base.digitCompareEquals(pureHits, Integer.parseInt(exeDocCount), executionCountSuccessMsg,
				executionCountFailureMsg);

		// Get the count of total no.of document list
		finalCountresult = saveSearch.launchDocListViaSSandReturnDocCount();
		int reExeCount = Integer.parseInt(exeDocCount);
		base.digitCompareEquals(finalCountresult, reExeCount, executionCountWithTagSuccessMsg,
				executionCountFailureMsg);

		login.logout();
	}

	/**
	 * @author Raghuram A Created on : 11/12/21 modified on : N/A by : N/A
	 *         Description : DAU impersonating down as RMU/RU re-executing searches
	 *         saved under MY Saved Searches(RPMXCON-57382) Sprint 06
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57382", enabled = true, dataProvider = "DAUimpRMUandREV", groups = { "regression" })
	public void metaDataCombinationSharedSGExecutionwithDAimpRMUandREV(String username, String password,
			String userRole, String saveFlow, String impToRole) throws InterruptedException, ParseException {

		int pureHits, docCount;
		String exeDocCount, finalCountresult;
		String SearchName = "SaveSearch" + Utility.dynamicNameAppender();
		String executionCountSuccessMsg = "Search result matches with the earlier count.";
		String executionCountFailureMsg = "Count Mismatches";
		String executionCountWithTagSuccessMsg = "Doc count matches with DocList count.";

		base.stepInfo("Test case Id: RPMXCON-57382 - Saved Search Sprint 06");
		base.stepInfo("DAU impersonating down as RMU/RU re-executing searches saved under MY Saved Searches");
		// Login as USER
		login.loginToSightLine(username, password);
		base.stepInfo("Loggedin As : " + userRole);
		base.rolesToImp(userRole, impToRole);

		pureHits = saveSearch.saveFlowCheckMethod(saveFlow, SearchName, username);
		saveSearch.savedSearch_SearchandSelect(SearchName, "Yes");
		exeDocCount = saveSearch.savedSearchExecute_Draft(SearchName, pureHits);
		base.digitCompareEquals(pureHits, Integer.parseInt(exeDocCount), executionCountSuccessMsg,
				executionCountFailureMsg);

		// Get the count of total no.of document list
		docCount = saveSearch.launchDocListViaSSandReturnDocCount();
		int reExeCount = Integer.parseInt(exeDocCount);
		base.digitCompareEquals(docCount, reExeCount, executionCountWithTagSuccessMsg, executionCountFailureMsg);

		login.logout();
	}

	/**
	 * @author Jeevitha Description : To verify, As a Reviewer user login, when user
	 *         select more than one saved search (select a folder within \"My
	 *         Search\") then only tag, folder & delete will be enable RPMXCON-47417
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-47417", enabled = true, groups = { "regression" })
	public void verifyTagAndFolderBtn() throws InterruptedException {
		String searchName = "Search" + Utility.dynamicNameAppender();

		// will login to the application by entering pa credentials
		login.loginToSightLine(Input.rev1userName, Input.rev1password);
		base.stepInfo("Tescase ID : RPMXCON-47417 Saved search");
		base.stepInfo(
				"To verify, As a Reviewer user login, when user select more than one saved search (select a folder within \"My Search\") then only tag, folder & delete will be enable");

		// load the saved search page and click on create new search group
		String newNode = saveSearch.createSearchGroupAndReturn(searchName, "PA", "Yes");

		// will load the session search page and search
		session.basicContentSearch(Input.searchString2);

		// will save the query on created node under my saved search
		session.saveSearchInNodewithChildNode(searchName, newNode);

		// will move to saved search page and expand the my saved search section
		this.driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.selectNode1(newNode);

		saveSearch.savedSearch_SearchandSelect(searchName, "No");

		// will verify the status of the tag button
		base.waitForElement(saveSearch.getSavedSearchToBulkTag());
		String tagButtonStatus = saveSearch.getSavedSearchToBulkTag().GetCssValue("class");
		if (tagButtonStatus == "disabled") {
			softAssertion.assertEquals(tagButtonStatus, "disabled");
			base.failedStep("Tag button is disabled");
		} else {
			base.passedStep("Tag button is enabled");
		}

		// will verify the status of folder button
		String folderButtonStatus = saveSearch.getSavedSearchToBulkFolder().GetCssValue("class");

		if (folderButtonStatus == "disabled") {
			softAssertion.assertEquals(folderButtonStatus, "disabled");
			base.failedStep("Folder button is disabled ");
		} else {
			base.passedStep("Folder button is enabled");
		}
		login.logout();
	}

	/**
	 * @author Jeevitha Description : To verify, As a admin user login, when user
	 *         select more than one saved search (select a folder within "My Saved
	 *         Search") then user can able to perform any action on tag, folder,
	 *         assign, release, delete RPMXCON-47418
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-47418", enabled = true, groups = { "regression" })
	public void performAction() throws InterruptedException {
		String searchName = "Search" + Utility.dynamicNameAppender();
		String folderName = "Folder" + Utility.dynamicNameAppender();

		// will login to the application by entering pa credentials
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Tescase ID :RPMXCON-47418 Saved Search ");
		base.stepInfo(
				"To verify, As a admin user login, when user select more than one saved search (select a folder within \"My Saved Search\") then user can able to perform any action on tag, folder, assign, release, delete");

		// load the saved search page and click on create new search group
		String newNode = saveSearch.createSearchGroupAndReturn(searchName, "PA", "Yes");

		// will load the session search page and search
		session.basicContentSearch(Input.searchString2);

		// will save the query on created node under my saved search
		session.saveSearchInNodewithChildNode(searchName, newNode);

		// action bulk folder and delete query for selected node
		saveSearch.performBulkActionFromNode(searchName, newNode, folderName);

		driver.waitForPageToBeReady();
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.selectNode1(newNode);
		saveSearch.deleteFunctionality();

		login.logout();

	}

	/**
	 * @author Raghuram A Created on : 11/16/21 modified on : N/A by : N/A
	 *         Description : As a Admin user login, to check the back button
	 *         functionality when user selected any saved search query from saved
	 *         search page and apply edit on that, and will modify the search query
	 *         and execute the same after modification and after that save the
	 *         search RPMXCON-47383
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-47383", enabled = true, groups = { "regression" })
	public void editSearchQuery() throws InterruptedException {
		String savedName = "SaveSearch" + Utility.dynamicNameAppender();
		String modifiedSearch = "modifiedSaveSearch" + Utility.dynamicNameAppender();
		base.stepInfo("Test case Id: RPMXCON-47383 - Sprint 06 ");
		base.stepInfo(
				"As a Admin user login, to check the back button functionality when user selected any saved search query from saved search page and apply edit on that, and will modify the search query and execute the same after modification and after that save the search");

		// Logged in as Admin
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Verify SavedSearch page
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.verifySavedSearchPage();
		driver.waitForPageToBeReady();

		// Search and Save Search
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(savedName);

		driver.Navigate().to(Input.url + "SavedSearch/SavedSearches");
		saveSearch.savedSearch_SearchandSelect(savedName, "Yes");
		saveSearch.getSavedSearchEditButton().Click();
		driver.waitForPageToBeReady();

		// Modify Search
		session.modifySearchTextArea(1, Input.searchString1, Input.searchString2, "Save");
		driver.waitForPageToBeReady();
		session.SearchBtnAction();
//		session.getSearchButton().waitAndClick(5);
		driver.waitForPageToBeReady();
		session.saveSearchAtAnyRootGroup(modifiedSearch, Input.mySavedSearch);

		// Navigate back and Verify Saved Search Page
		base.stepInfo("Navigate back and Verify Saved Search Page");
		driver.Navigate().back();
		saveSearch.verifySavedSearchPage();
		login.logout();
	}

	/**
	 * @author Raghuram A Created on : 11/16/21 modified on : N/A by : N/A
	 *         Description : Validate saving edited search (executed or draft) under
	 *         My Searches or Shared Searches RPMXCON-49859
	 * @throws InterruptedException
	 * @Stabilization - done
	 */
	@Test(description = "RPMXCON-49859", enabled = true, groups = { "regression" })
	public void editSavedSearchInShareWithPa() throws Exception {
		String savedName = "Search_" + Utility.dynamicNameAppender();
		String savedName_1 = "SSearch_" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-49859 - Sprint 06");
		base.stepInfo("Validate saving edited search (executed or draft) under My Searches or Shared Searches");

		// Logged in as Admin
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Search and Save Search
		int pureHits = session.basicContentSearch(Input.searchString1);
		session.saveSearchAtAnyRootGroup(savedName, Input.shareSearchPA);

		saveSearch.selectSavedSearchTAb(savedName, Input.shareSearchPA, "Yes");
		driver.waitForPageToBeReady();
		String SearchID = saveSearch.getSelectSearchWithID(savedName).getText();
		// Get Search ID 1
		String exeDocCount = saveSearch.savedSearchExecute_Draft(savedName, pureHits);
		saveSearch.savedSearch_SearchandSelect(savedName, "Yes");
		saveSearch.getSavedSearchEditButton().waitAndClick(5);

		// Overwrite Search / Modify
		session.modifySearchTextArea(1, Input.searchString1, Input.searchString2, "Save");
		driver.waitForPageToBeReady();
		session.searchAndReturnPureHit_BS();
		session.saveAsOverwrittenSearch(Input.shareSearchPA, savedName, "first", "Success", "", null);
		session.saveSearchAtAnyRootGroup(savedName_1, Input.mySavedSearch);
		driver.waitForPageToBeReady();

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.verifySavedSearchPage();
		saveSearch.verifySearchAndSearchId(Input.mySavedSearch, SearchID, true, savedName_1, true);
		base.stepInfo("Modified and executed search saved under Shared with Project Administrator with different ID");
		saveSearch.verifySearchAndSearchId(Input.shareSearchPA, SearchID, true, savedName, true);

		// Logout and and login as PA2
		login.logout();
		login.loginToSightLine(Input.pa2userName, Input.pa2password);
		base.stepInfo("Loggedin As : " + Input.pa2userName);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.verifySavedSearchPage();
		saveSearch.verifySearchAndSearchId(Input.shareSearchPA, SearchID, true, savedName, true);
		base.stepInfo("Different PAU user : overwritten search and the changes reflects to all associated PAU users");
		driver.waitForPageToBeReady();

		login.logout();

	}

	/**
	 * @author Raghuram A Date: 11/17/21 Modified date:N/A Modified by: Description
	 *         : Validate sharing specific search/group to Project Administrators
	 *         that are already shared -RPMXCON-49851 Sprint 06
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-49851", enabled = true, groups = { "regression" })
	public void validateSharingAlreadySharedSG() throws InterruptedException, ParseException {
		int noOfNodesToCreate = 6;
		int selectIndex = 0;
		String SGtoShare = Input.shareSearchPA;
		String node;
		Boolean inputValue = true;
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		HashMap<String, String> searchGroupSearchpIDpair = new HashMap<>();
		HashMap<String, String> searchGroupSearchpIDpair2 = new HashMap<>();

		base.stepInfo("Test case Id: RPMXCON-49851 - Saved Search Sprint 06");
		base.stepInfo("Validate sharing specific search/group to Project Administrators that are already shared");

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

		// Search ID collection set 1
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.selectRootGroupTab(Input.mySavedSearch);
		saveSearch.rootGroupExpansion();
		driver.waitForPageToBeReady();
//		base.waitForElement(saveSearch.getSavedSearchNewGroupExpand());
//		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);

		searchGroupSearchpIDpair = saveSearch.collectionOfSearchIdsFromNodeCollections(newNodeList, nodeSearchpair,
				searchGroupSearchpIDpair);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.selectRootGroupTab(Input.mySavedSearch);
		driver.waitForPageToBeReady();
		node = saveSearch.childNodeSelectionToShare(selectIndex, newNodeList);
		System.out.println("Final : " + node);
		saveSearch.shareSavedNodePA(SGtoShare, node, false, true, nodeSearchpair.get(node));
		saveSearch.verifyImpactinSharedchildNodes(SGtoShare, newNodeList, selectIndex, nodeSearchpair,
				searchGroupSearchpIDpair);

		base.stepInfo("-------Pre-requesties completed--------");

		// Search ID collection set 2
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		saveSearch.getSavedSearchGroupName(SGtoShare).waitAndClick(10);
		saveSearch.rootGroupExpansion();

		searchGroupSearchpIDpair2 = saveSearch.collectionOfSearchIdsFromNodeCollections(newNodeList, nodeSearchpair,
				searchGroupSearchpIDpair);

		// Verify shared SG/Searches
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		node = saveSearch.childNodeSelectionToShare(selectIndex, newNodeList);
		System.out.println("Final : " + node);
		saveSearch.selectRootGroupTab(Input.mySavedSearch);
		saveSearch.selectNode1(node);
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
		base.stepInfo("ID verfication between shared searches");
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		saveSearch.verifyImpactinSharedchildNodes(SGtoShare, newNodeList, selectIndex, nodeSearchpair,
				searchGroupSearchpIDpair2);
		base.stepInfo("Verified Shared SG and Searches");

		login.logout();

	}

	/**
	 * @author Raghuram A Date: 11/17/21 Modified date:N/A Modified by: N/A
	 *         Description : Validate modifying searches/groups from the shared with
	 *         PAU by any other PAU user-RPMXCON-49852 Sprint 06
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-49852", enabled = true, groups = { "regression" })
	public void modifySeachOrSGwithSharedPA() throws InterruptedException, ParseException {
		String SearchName = "SaveSearch_" + Utility.dynamicNameAppender();
		String renamedSearchName = "Re-SaveSearch_" + Utility.dynamicNameAppender();
		int pureHits;

		base.stepInfo("Test case Id: RPMXCON-49852 - Sprint 06");
		base.stepInfo("Validate modifying searches/groups from the shared with PAU by any other PAU user");
		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Landed on Saved Search
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();

		// create new searchGroup/Search and Save Search
		String newNode = saveSearch.createSearchGroupAndReturn(SearchName, "PA", "Yes");
		pureHits = session.basicContentSearch(Input.searchString1);
		session.saveSearchInNewNode(SearchName, newNode);
		saveSearch.shareSavedSearchFromNode(SearchName, Input.shareSearchPA);

		// Landed on Saved Search
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		saveSearch.getSavedSearchGroupName(Input.shareSearchPA).waitAndClick(10);
		saveSearch.selectNode1(newNode);
		driver.waitForPageToBeReady();
		String modifiedSearchGroup = saveSearch.renameSearchGroup(newNode);
		base.CloseSuccessMsgpopup();
		saveSearch.savedSearch_SearchandSelect(SearchName, "Yes");
		saveSearch.renameSavedSearch(renamedSearchName);

		// logout
		login.logout();

		// Login as PA
		login.loginToSightLine(Input.pa2userName, Input.pa2password);
		base.stepInfo("Loggedin As : " + Input.pa2FullName);
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		saveSearch.verifySharedNode(Input.shareSearchPA, modifiedSearchGroup, " ", true, renamedSearchName, false);

		// logout
		login.logout();

	}

	/**
	 * @author Raghuram A Created on : 11/12/21 modified on : 11/18/21 by : Raghuram
	 *         A Description : PAU impersonating down as RMU/RU and re-executing
	 *         searches saved under My Saved Searches(RPMXCON-57381) Sprint 06
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57381", enabled = true, dataProvider = "PAUimpRMUandREV", groups = { "regression" })
	public void metaDataCombinationwithPAimpRMUandREV(String username, String password, String userRole,
			String saveFlow, String impToRole) throws InterruptedException, ParseException {

		int pureHits, docCount;
		String exeDocCount, finalCountresult;
		String SearchName = "SaveSearch" + Utility.dynamicNameAppender();
		String executionCountSuccessMsg = "Search result matches with the earlier count.";
		String executionCountFailureMsg = "Count Mismatches";
		String TagName = "Tag" + Utility.dynamicNameAppender(), finalCount;
		int latencyCheckTime = 5;
		String passMessage = "Application not hang or shows latency more than " + latencyCheckTime + " seconds.";
		String failureMsg = "Continues Loading more than " + latencyCheckTime + " seconds.";
		String executionCountWithTagSuccessMsg = "Document Count matches with the Execution count";

		base.stepInfo("Test case Id: RPMXCON-57381 - Saved Search Sprint 06");
		base.stepInfo("PAU impersonating down as RMU/RU and re-executing searches saved under My Saved Searches");
		// Login as USER
		login.loginToSightLine(username, password);
		base.stepInfo("Loggedin As : " + userRole);

		// create folder and add save search in folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTag(TagName, Input.securityGroup);

		pureHits = saveSearch.saveFlowCheckMethodWithShareSpecificToImp(saveFlow, SearchName, username, true,
				Input.shareSearchDefaultSG, true, userRole, impToRole, null, null, null);
		saveSearch.savedSearch_SearchandSelect(SearchName, "Yes");
		exeDocCount = saveSearch.savedSearchExecute_Draft(SearchName, pureHits);
		base.digitCompareEquals(pureHits, Integer.parseInt(exeDocCount), executionCountSuccessMsg,
				executionCountFailureMsg);

		// Bulk Tag
		saveSearch.getSavedSearchToBulkTag().Click();
		base.stepInfo("Clicked tag icon from Code");

		// Load latency Verification
		Element loadingElement = saveSearch.getTotalCountLoad();
		saveSearch.loadingCountVerify(loadingElement, latencyCheckTime, passMessage, failureMsg);
		finalCountresult = session.bulkTagExistingWithReturn(TagName);
		base.stepInfo("Completed Bulk Tag");
		base.stepInfo(
				"Navigating to Search >> Basic Search >> Advanced Search >> WorkProduct >> Tags (Select Same Tag which we have created in prerequesties.");
		base.selectproject();
		session.switchToWorkproduct();
		session.selectTagInASwp(TagName);
		int pureHit = session.serarchWP();
		docCount = Integer.parseInt(finalCountresult);
		base.digitCompareEquals(docCount, pureHit, executionCountWithTagSuccessMsg, executionCountFailureMsg);

		// Delete Created Tag
		base.rolesToImp(impToRole, userRole);
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.DeleteTag(TagName, Input.securityGroup);

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 11/18/21 Modified date:N/A Modified by: N/A
	 *         Description : Validate performing any action on searches/groups from
	 *         the shared with PAU by any other PAU user-RPMXCON-49855 Sprint 06
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-49855", enabled = true, groups = { "regression" })
	public void performAnyActionSharedPAU() throws InterruptedException, ParseException {
		String SearchName = "SaveSearch_" + Utility.dynamicNameAppender();
		String renamedSearchName = "Re-SaveSearch_" + Utility.dynamicNameAppender();
		int pureHits;

		base.stepInfo("Test case Id: RPMXCON-49855 - Sprint 06");
		base.stepInfo(
				"Validate performing any action on searches/groups from the shared with PAU by any other PAU user");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Landed on Saved Search
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();

		// create new searchGroup/Search and Save Search
		String newNode = saveSearch.createSearchGroupAndReturn(SearchName, "PA", "Yes");
		pureHits = session.basicContentSearch(Input.searchString1);
		session.saveSearchInNewNode(SearchName, newNode);
		saveSearch.shareSavedSearchFromNode(SearchName, Input.shareSearchPA);

		// logout
		login.logout();

		// Login as PA 2
		login.loginToSightLine(Input.pa2userName, Input.pa2password);
		base.stepInfo("Loggedin As : " + Input.pa2FullName);

		// Landed on Saved Search
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		saveSearch.getSavedSearchGroupName(Input.shareSearchPA).waitAndClick(10);
		saveSearch.selectNode1(newNode);
		driver.waitForPageToBeReady();
		String modifiedSearchGroup = saveSearch.renameSearchGroup(newNode);
		base.CloseSuccessMsgpopup();
		saveSearch.savedSearch_SearchandSelect(SearchName, "Yes");
		saveSearch.renameSavedSearch(renamedSearchName);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		saveSearch.verifySharedNode(Input.shareSearchPA, modifiedSearchGroup, " ", true, renamedSearchName, false);
		base.stepInfo("PAU should allowed to perform any action on SS/SG from the ribbon");

		// logout
		login.logout();

	}

	/**
	 * @author Raghuram A Date: 11/23/21 Modified date:N/A Modified by: N/A
	 *         Description : Verify that after modification of any search query
	 *         under "My Searches" from Manage Schedule - Modified Schedule
	 *         functionality is working proper in Saved searches-RPMXCON-57406
	 *         Sprint 06
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-57406", enabled = true, dataProvider = "PAandRMU", groups = { "regression" })
	public void manageScheduleCheck(String userName, String password, String fullName)
			throws InterruptedException, ParseException {
		String searchName = "SS" + Utility.dynamicNameAppender();
		ElementCollection availableSearchName;
		SchedulesPage schedulePage=new SchedulesPage(driver);
		// will login to the application by entering RMU credentials
		login.loginToSightLine(userName, password);
		base.stepInfo("Tescase ID : RPMXCON-57406 Saved search - Sprint 06");
		base.stepInfo(
				"Verify that after modification of any search query under My Searches   from Manage Schedule - Modified Schedule functionality is working proper in Saved searches");

		// Create node - Search - Save
		String newNode = saveSearch.createSearchGroupAndReturn(searchName, "PA", "Yes");
		session.basicContentSearch(Input.testData1);
		session.saveSearchInNodewithChildNode(searchName, newNode);
		this.driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.selectNode1(newNode);

		// after selecting the query will schedule for current time +02 Minutes
		base.stepInfo("schedule for current time +02 Minutes");
		saveSearch.scheduleSavedSearchInMinute(searchName, 2);

		// Navigate to manage-schedules page and edit the time to +03 minutes
		base.stepInfo("edit the time to +03 minutes");
		schedulePage.editScheduledSaveSearch(searchName, 3);

		// --need to wait and check status
		schedulePage.verifyScheduledTime(searchName);
		schedulePage.checkStatusComplete(searchName);

		base.stepInfo(
				"Scheduler should gets executed once last modified time over.     2. It should not execute twice. because we modified earlier time.  ");
		availableSearchName = schedulePage.getSearchNamesfromList(searchName);
		int listSize = availableSearchName.size();
		System.out.println(listSize);
		if (listSize > 1) {
			base.failedStep("Number of times executed : " + listSize);
		} else {
			base.passedStep("Number of times executed : " + listSize);
		}

		// Delete Created Node
		saveSearch.deleteNode(Input.mySavedSearch, newNode);

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 11/23/21 Modified date:N/A Modified by: N/A
	 *         Description : Verify the document count for RMU user when PA user
	 *         save the searches under 'My Searches', share with security group and
	 *         execute those searches from saved search-RPMXCON-57390 Sprint 06
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-57390", enabled = true, groups = { "regression" })
	public void verifyTheDocumentCountForRMUMultipleCombo() throws Exception {
		String[] combinations = { "Content and Metadata + Conceptual", "Content and Metadata + Audio",
				"Content and Metadata + Work Product", "Conceptual + Audio", "Conceptual + Work Product",
				"Audio + Work Product", "Content and Metadata + Conceptual + Audio + Work Product" };

		base.stepInfo("Tescase ID : RPMXCON-57390 Saved search - Sprint 06");
		base.stepInfo(
				"Verify the document count for RMU user when PA user save the searches under 'My Searches', share with security group and execute those searches from saved search");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// verify DocumentCount For RMU withmultipleCombinations
		saveSearch.verifyTheDocumentCountForRMU_withmultipleCombinations(combinations);

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 11/23/21 Modified date:N/A Modified by: N/A
	 *         Description : Validate RMU sharing search with Default Security
	 *         Group-RPMXCON-49877 Sprint 06
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-49877", enabled = true, groups = { "regression" })
	public void validatingSharingSearchesViaRMU() throws Exception {

		String searchName = "SSearch" + Utility.dynamicNameAppender();
		String SGtoShare = Input.shareSearchDefaultSG;
		String[] rolesToVerify = { "RMU-2", "PA-2" };

		base.stepInfo("Tescase ID : RPMXCON-49877 Saved search - Sprint 06");
		base.stepInfo("Validate RMU sharing search with Default Security Group");

		// Login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// Search and save under My Saved Search
		int pureHits = session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName);

		// Share Search
		String searchID = saveSearch.shareSavedSearchRMUreturnsOriginalSearchID(searchName, SGtoShare);
		login.logout();

		saveSearch.verifyShareSearchwithMultiplesRoles(rolesToVerify, searchName, SGtoShare, "RootSearch", searchID,
				null, null, "", "");

		base.passedStep("Validated RMU sharing search with Default Security Group");

		// Login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// Delete Searches
		base.stepInfo("Initiated Delete Searches");
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		// Delete Shared SG Searches
		base.stepInfo("Initiated Delete Shared SG Searches");
		saveSearch.deleteSearch(searchName, Input.shareSearchDefaultSG, "Yes");

		login.logout();

	}

	/**
	 * @author Date: 11/24/21 Modified date:N/A Modified by: N/A Description :To
	 *         verify back button functionality when user selects any saved search
	 *         query from saved search page and apply edit on that
	 *         Group-RPMXCON-47377 Sprint 06
	 * @throws InterruptedException
	 * @throws ParseException
	 */

	@Test(description = "RPMXCON-47377", enabled = true, dataProvider = "SavedSearchwithUsers", groups = {
			"regression" })
	public void validateBackButtonAsUser(String userName, String password, String fullName) throws Exception {
		String searchString = Input.searchString1;
		String searchName = "Search Name" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-47377 - Sprint 06");
		base.stepInfo(
				"To verify back button functionality when user selects any saved search query from saved search page and apply edit on that");

		// Login as User
		login.loginToSightLine(userName, password);
		base.stepInfo("Loggedin As : " + fullName);

		// Search and Save
		session.basicContentSearch(searchString);
		session.saveSearch(searchName);

		driver.Navigate().to(Input.url + "SavedSearch/SavedSearches");
		saveSearch.verifySavedSearchPage();
		driver.waitForPageToBeReady();
		saveSearch.savedSearch_SearchandSelect(searchName, "Yes");
		saveSearch.getSavedSearchEditButton().Click();
		driver.waitForPageToBeReady();
		driver.Navigate().back();
		saveSearch.verifySavedSearchPage();
		driver.waitForPageToBeReady();

		// Delete Searches
		base.stepInfo("Initiated Delete Searches");
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		// Logout
		login.logout();
	}

	/**
	 * @author Date: 11/24/21 Modified date:N/A Modified by: N/A Description :As a
	 *         Admin user login, to check the back button functionality when user
	 *         selected any saved search query from saved search page and apply edit
	 *         on that, and will modify the search query and execute the same after
	 *         modification-RPMXCON-47380 Sprint 06
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-47380", enabled = true, groups = { "regression" })
	public void validateBackButtonAsPa() throws Exception {
		String searchString1 = Input.searchString1;
		String searchName = "Search Name" + Utility.dynamicNameAppender();
		String searchString2 = Input.searchString2;

		base.stepInfo("Test case Id: RPMXCON-47380 Sprint 06");
		base.stepInfo(
				"As a Admin user login, to check the back button functionality when user selected any saved search query from saved search page and apply edit on that, and will modify the search query and execute the same after modification");

		// Login as Admin
		login.loginToSightLine(Input.pa2userName, Input.pa2password);
		base.stepInfo("Loggedin As : " + Input.pa2FullName);

		// Search and Save
		session.basicContentSearch(searchString1);
		session.saveSearch(searchName);

		// Edit Search Via SS
		driver.Navigate().to(Input.url + "SavedSearch/SavedSearches");
		saveSearch.verifySavedSearchPage();
		driver.waitForPageToBeReady();
		saveSearch.savedSearch_SearchandSelect(searchName, "Yes");
		saveSearch.getSavedSearchEditButton().Click();
		driver.waitForPageToBeReady();
		session.modifySearchTextArea(1, searchString1, searchString2, "Save");
		driver.waitForPageToBeReady();
		base.waitForElement(session.getSearchButton());
		session.getSearchButton().waitAndClick(10);
		driver.waitForPageToBeReady();

		// Navigate Back to SS
		driver.Navigate().back();
		saveSearch.verifySavedSearchPage();
		driver.waitForPageToBeReady();

		// Delete Searches
		base.stepInfo("Initiated Delete Searches");
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 11/23/21 Modified date:N/A Modified by: N/A
	 *         Description :Validate RMU sharing Search Group to Default Security
	 *         Group-RPMXCON-49878 Sprint 06
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-49878", enabled = true, groups = { "regression" })
	public void validatingSharingSGViaRMU() throws Exception {

		HashMap<String, String> nodeSearchDocCOuntpair = new HashMap<>();
		String searchName = "SSearch" + Utility.dynamicNameAppender();
		String SGtoShare = Input.shareSearchDefaultSG;
		String[] rolesToVerify = { "RMU-2", "PA-2" };
		String searchID, docCount;

		base.stepInfo("Tescase ID : RPMXCON-49878 Saved search - Sprint 06");
		base.stepInfo("Validate RMU sharing Search Group to Default Security Group");

		// Login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// Create Node
		base.stepInfo("Node (or) New Search Group creation");
		String newNode = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "PA", "Yes");

		// Search and save under My Saved Search
		int pureHits = session.basicContentSearch(Input.searchString1);
		session.saveSearchInNewNode(searchName, newNode);

		// Landed on Saved Search
		saveSearch.navigateToSavedSearchPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(2);
		saveSearch.rootGroupExpansion();

		// Share to SG
		searchID = saveSearch.shareSavedNodePA(SGtoShare, newNode, true, true, searchName);
		docCount = saveSearch.docResultCOuntCHeck(searchName);
		nodeSearchDocCOuntpair.put(searchName, docCount);
		login.logout();

		saveSearch.verifySharedNodehwithMultiplesRoles(rolesToVerify, searchName, SGtoShare, "RootSearch", searchID,
				null, null, newNode, "Single", "", nodeSearchDocCOuntpair, null);

		base.passedStep("Validated RMU sharing Search Group to Default Security Group");

		// Login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// Delete Searches
		base.stepInfo("Initiated Delete Searches");
		saveSearch.deleteNode(Input.mySavedSearch, newNode);

		// Delete Shared SG Searches
		base.stepInfo("Initiated Delete Shared SG Searches");
		saveSearch.deleteNode(Input.shareSearchDefaultSG, newNode);

		login.logout();

	}

	/**
	 * @author Raghuram A Date: 11/25/21 Modified date:N/A Modified by: N/A
	 *         Description :Validate RMU sharing search at Security Group level from
	 *         the leaf node of 5 hierarchy levels-RPMXCON-49879 Sprint 06
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-49879", enabled = true, groups = { "regression" })
	public void validatinghierarchySharingSGViaRMU() throws InterruptedException {

		int noOfNodesToCreate = 6;
		int selectIndex = 4;
		String SGtoShare = Input.shareSearchDefaultSG;
		String node;
		Boolean inputValue = true;
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		HashMap<String, String> searchGroupSearchpIDpair = new HashMap<>();
		String[] rolesToVerify = { "RMU-2", "PA-2" };
		String passMessage = "Saved Search shared with the group hierarchy till 5th level across all PAU users associated to the project ,Except the selected search, none of the searches in any hierarchical group should be shared";

		base.stepInfo("Test case Id: RPMXCON-49879 - Saved Search Sprint 06");
		base.stepInfo("Validate RMU sharing search at Security Group level from the leaf node of 5 hierarchy levels");

		// Login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// Navigate to Saved Search
		saveSearch.navigateToSavedSearchPage();

		// Multiple Node Creation
		newNodeList = saveSearch.createSGAndReturn("RMU", "No", noOfNodesToCreate);
		System.out.println("Node creation is done followed by adding searches to the created nodes");
		base.stepInfo("Node creation is done followed by adding searches to the created nodes");

		driver.getWebDriver().get(Input.url + "Search/Searches");

		// Adding searches to the created nodes
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);
		saveSearch.sortedMapList(nodeSearchpair);

		base.stepInfo("-------Pre-requesties completed--------");

		// Navigate to Saved Search
		saveSearch.navigateToSavedSearchPage();

		// Search ID collection
		base.waitForElement(saveSearch.getSavedSearchNewGroupExpand());
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);

		searchGroupSearchpIDpair = saveSearch.collectionOfSearchIdsFromNodeCollections(newNodeList, nodeSearchpair,
				searchGroupSearchpIDpair);

		// Navigate to Saved Search
		saveSearch.navigateToSavedSearchPage();

		node = saveSearch.childNodeSelectionToShare(selectIndex, newNodeList);
		System.out.println("Final : " + node);

		saveSearch.shareSavedNodePA(SGtoShare, node, false, true, nodeSearchpair.get(node));

		login.logout();
		// Verify with Different Users
		saveSearch.verifyListofsharedNodesandSearchesAcrossUsers(SGtoShare,node, newNodeList, selectIndex, nodeSearchpair,
				searchGroupSearchpIDpair, rolesToVerify, passMessage);

		// Login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// Delete Node
		base.stepInfo("Initiated Delete Searches");
		saveSearch.deleteNode(Input.mySavedSearch, newNodeList.get(0));

		// Delete Shared SG Nodes
		base.stepInfo("Initiated Delete Shared SG Searches");
		saveSearch.deleteNode(Input.shareSearchDefaultSG, newNodeList.get(0));

		login.logout();

	}

	/**
	 * @author Raghuram A Date: 11/25/21 Modified date:N/A Modified by: N/A
	 *         Description : Validate RMU sharing search group to Security Group
	 *         from root group-RPMXCON-49880 Sprint 06
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-49880", enabled = true, groups = { "regression" })
	public void validatingRootlevelhierarchySharingSGViaRMU() throws InterruptedException {

		int noOfNodesToCreate = 6;
		int selectIndex = 0;
		String SGtoShare = Input.shareSearchDefaultSG;
		String node;
		Boolean inputValue = true;
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		HashMap<String, String> searchGroupSearchpIDpair = new HashMap<>();
		String[] rolesToVerify = { "RMU-2", "PA-2" };
		String passMessage = "All groups and Searches within them are shared with the group hierarchy across all PAU users associated to the project";

		base.stepInfo("Test case Id: RPMXCON-49880 - Saved Search Sprint 06");
		base.stepInfo("Validate RMU sharing search group to Security Group from root group");

		// Login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// Navigate to Saved Search
		saveSearch.navigateToSavedSearchPage();

		// Multiple Node Creation
		newNodeList = saveSearch.createSGAndReturn("RMU", "No", noOfNodesToCreate);
		System.out.println("Node creation is done followed by adding searches to the created nodes");
		base.stepInfo("Node creation is done followed by adding searches to the created nodes");

		driver.getWebDriver().get(Input.url + "Search/Searches");

		// Adding searches to the created nodes
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);
		saveSearch.sortedMapList(nodeSearchpair);

		base.stepInfo("-------Pre-requesties completed--------");

		// Navigate to Saved Search
		saveSearch.navigateToSavedSearchPage();

		// Search ID collection
		base.waitForElement(saveSearch.getSavedSearchNewGroupExpand());
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);

		searchGroupSearchpIDpair = saveSearch.collectionOfSearchIdsFromNodeCollections(newNodeList, nodeSearchpair,
				searchGroupSearchpIDpair);

		// Navigate to Saved Search
		saveSearch.navigateToSavedSearchPage();

		node = saveSearch.childNodeSelectionToShare(selectIndex, newNodeList);
		System.out.println("Final : " + node);

		saveSearch.shareSavedNodePA(SGtoShare, node, false, true, nodeSearchpair.get(node));

		login.logout();
		// Verify with Different Users
		saveSearch.verifyListofsharedNodesandSearchesAcrossUsers(SGtoShare, newNodeList, selectIndex, nodeSearchpair,
				searchGroupSearchpIDpair, rolesToVerify, passMessage);

		// Login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// Delete Node
		base.stepInfo("Initiated Delete Searches");
		saveSearch.deleteNode(Input.mySavedSearch, newNodeList.get(0));

		// Delete Shared SG Nodes
		base.stepInfo("Initiated Delete Shared SG Searches");
		saveSearch.deleteNode(Input.shareSearchDefaultSG, newNodeList.get(0));

		login.logout();

	}

	/**
	 * @author Raghuram A Date: 11/26/21 Modified date:N/A Modified by: N/A
	 *         Description : Shared Steps: Renames an existing Basic saved search on
	 *         Saved Search Screen.-RPMXCON-47224 Sprint 06
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-47224", enabled = true, groups = { "regression" })
	public void verifyRenamingBasicSearchviaSS() throws InterruptedException {

		base.stepInfo("Test case Id: RPMXCON-47224 - Saved Search Sprint 06");
		base.stepInfo("Shared Steps: Renames an existing Basic saved search on Saved Search Screen.");

		// Login via PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		String searchName = "SavedSearch" + Utility.dynamicNameAppender();
		String searchNameRenamed = "RenamedSavedSearch" + Utility.dynamicNameAppender();

		base.stepInfo("Search and saveSearch in the created node");
		session.basicContentSearch(Input.searchString2);
		session.saveSearch(searchName);
		driver.waitForPageToBeReady();

		saveSearch.navigateToSavedSearchPage();

		saveSearch.verifyRenamedsavedSearch(searchName, searchNameRenamed);
		base.stepInfo("Renamed name appears for the existing query.");

		// Delete Searches
		base.stepInfo("Initiated Delete Searches");
		saveSearch.deleteSearch(searchNameRenamed, Input.mySavedSearch, "Yes");

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 11/26/21 Modified date:N/A Modified by: N/A
	 *         Description : Verify PAU/RMU/Reviewer can rename a saved search group
	 *         with in "My Saved Search" Folder-RPMXCON-47394 Sprint 06 -
	 *         92chromeDriver not supported
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-47394", enabled = true, dataProvider = "SavedSearchwithUsers", groups = {
			"regression" })
	public void renameSearchGroupWithAllUsers(String userName, String password, String fullName)
			throws InterruptedException, ParseException {
		String SearchName = "SaveSearch_" + Utility.dynamicNameAppender();
		String reNameSG;

		base.stepInfo("Test case Id: RPMXCON-47394 - Sprint 06");
		base.stepInfo("Verify PAU/RMU/Reviewer can rename a saved search group with in \"My Saved Search\" Folder");

		// Login as PA
		login.loginToSightLine(userName, password);
		base.stepInfo("Loggedin As : " + fullName);

		// Navigate to Saved Search
		saveSearch.navigateToSavedSearchPage();

		// create new searchGroup/Search and Save Search
		String newNode = saveSearch.createSearchGroupAndReturn(SearchName, "PA", "Yes");
		session.basicContentSearch(Input.searchString1);
		session.saveSearchInNewNode(SearchName, newNode);

		// Navigate to Saved Search
		saveSearch.navigateToSavedSearchPage();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(10);
		saveSearch.selectNode1(newNode);
		driver.waitForPageToBeReady();
		reNameSG = saveSearch.renameSearchGroup(newNode);
		base.CloseSuccessMsgpopup();

		// Navigate to Saved Search
		saveSearch.navigateToSavedSearchPage();
		// Delete Node
		base.stepInfo("Initiated Delete Searches");
		saveSearch.deleteNode(Input.mySavedSearch, reNameSG);

		base.selectproject();

		// logout
		login.logout();

	}

	/**
	 * @author Raghuram A Date: 11/26/21 Modified date:N/A Modified by: N/A
	 *         Description :Verify PAU/RMU/Reviewer having access to multiple
	 *         projects, any search saved is applicable to the respective project
	 *         Folder-RPMXCON-47386 Sprint 06
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-47386", enabled = true, dataProvider = "SavedSearchwithUsers", groups = {
			"regression" })
	public void verifySavedSearchAcrossProject(String useName, String password, String fullName)
			throws InterruptedException, ParseException {
		String SearchName = "SaveSearch_" + Utility.dynamicNameAppender();
		String currentProject;

		base.stepInfo("Test case Id: RPMXCON-47386 - Sprint 06");
		base.stepInfo(
				"Verify PAU/RMU/Reviewer having access to multiple projects, any search saved is applicable to the respective project");

		// Login as User
		login.loginToSightLine(useName, password);
		base.stepInfo("Loggedin As : " + fullName);
		driver.waitForPageToBeReady();
		currentProject = base.getProjectNames().getText();
		base.stepInfo("Project Selected : " + currentProject);

		// create new searchGroup/Search and Save Search
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(SearchName);

		// Navigate to Saved Search and Search
		saveSearch.navigateToSSPage();
		saveSearch.savedSearch_SearchandSelect_New(SearchName, "No");

		// Switch Project
		base.switchProject(currentProject);

		// Navigate to Saved Search and Search
		saveSearch.navigateToSSPage();
		saveSearch.savedSearch_SearchandSelect_New(SearchName, "No");

		// Switch back to Initiated Project and Delete Search
		base.selectproject();
		base.stepInfo("Switch back to Initial Project : " + base.getProjectNames().getText());
		saveSearch.deleteSearch(SearchName, Input.mySavedSearch, "Yes");

		// logout
		login.logout();

	}

	/**
	 * @author Raghuram A Date: 11/30/21 Modified date:N/A Modified by: N/A
	 *         Description : TC01_Verify that When PAU runs a RMU created search
	 *         (Content and Metadata) in PAU role then search returns documents in
	 *         the context of specific Security Group-RPMXCON-49841 Sprint 07
	 * @throws InterruptedException
	 * @throws ParseException
	 * @Stabilization - done
	 */
	@Test(description = "RPMXCON-49841", enabled = true, groups = { "regression" })
	public void PAUrunsRMUSearchValidation() throws InterruptedException, ParseException {

		String searchName = "Search Name" + Utility.dynamicNameAppender();
		String contentSearch = "ContextSearch Name" + Utility.dynamicNameAppender();
		String metaDataSearch = "MetaSearch Name" + Utility.dynamicNameAppender();
		String securitygroupname = "TestSG-" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-49841 - Sprint 07");
		base.stepInfo(
				"TC01_Verify that When PAU runs a RMU created search (Content and Metadata) in PAU role then search returns documents in the context of specific Security Group");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Doc release to Security Group SG
		base.stepInfo(
				"Prerequisites: Project with around 1000 docs and only 500 documents are released to Security Group SG  ");
		driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		sg.AddSecurityGroup(securitygroupname);
		base.stepInfo(securitygroupname + " : created");
		session.basicContentSearch(Input.searchString7);
		session.saveSearch(searchName);
		saveSearch.savedSearch_Searchandclick(searchName);
		saveSearch.preRequestCreation(searchName, securitygroupname);

		// Impersonate PA to RMU
		base.rolesToImp("PA", "RMU");
		base.selectsecuritygroup(securitygroupname);
		base.stepInfo("Current SG : " + sg.get_CurrentSG().getText());

		base.stepInfo(
				"1. RMU (with security group SG)Saved Content Search and Shared with specific Security group in Saved Search Screen.");
		int basicPureHit = session.basicContentSearch(Input.searchString1);
		session.saveSearch(contentSearch);

		base.selectproject();
		base.selectsecuritygroup(securitygroupname);
		base.stepInfo("Current SG : " + sg.get_CurrentSG().getText());

		base.stepInfo(
				"2. RMU (with security group SG)Saved Metadata Search and Shared with specific Security group in Saved Search Screen.");
		int advpureHit = session.advancedMetaDataSearch("CustodianName", null, Input.metaDataCN, null);
		session.saveAdvanceSearchInNode(metaDataSearch, null);

		// Share Searches - Navigate to Saved Search
		saveSearch.navigateToSavedSearchPage();
		saveSearch.shareSearchFlow(contentSearch, securitygroupname, "RMU");
		saveSearch.shareSearchFlow(metaDataSearch, securitygroupname, "RMU");

		login.logout();
		base.stepInfo("Pre-requesties completed");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Navigate to SavedSearch Page
		saveSearch.navigateToSSPage();

		// Content Search
		saveSearch.verifySharedGroupSearch(securitygroupname, contentSearch);
		saveSearch.savedSearchExecute_SearchGRoup(contentSearch, basicPureHit);

		driver.Navigate().refresh();

		// MeteDataSearch
		saveSearch.verifySharedGroupSearch(securitygroupname, metaDataSearch);
		saveSearch.savedSearchExecute_SearchGRoup(metaDataSearch, advpureHit);

		// Delete Created SG
		sg.deleteSecurityGroups(securitygroupname);

		login.logout();

	}

	/**
	 * @author Raghuram A Date: 11/30/21 Modified date:N/A Modified by: N/A
	 *         Description : TC04_Verify that When PAU runs a RMU created search
	 *         (Conceptual) in PAU role then search returns documents Under "Count
	 *         of Results" column on "Saved Search Screen"-RPMXCON-49837 Sprint 07
	 * @throws InterruptedException
	 * @throws ParseException
	 * @Stabilization - done
	 */
	@Test(description = "RPMXCON-49837", enabled = true, groups = { "regression" })
	public void PAUrunsRMUConceptualSearchValidation() throws InterruptedException, ParseException {

		String searchName = "Search Name" + Utility.dynamicNameAppender();
		String conceptualSearch = "conceptualSearch Name" + Utility.dynamicNameAppender();
		String securitygroupname = "TestSG-" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-49837 - Sprint 07");
		base.stepInfo(
				"TC04_Verify that When PAU runs a RMU created search (Conceptual) in PAU role then search returns documents Under \"Count of Results\" column on \"Saved Search Screen\"");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Doc release to Security Group SG
		driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		sg.AddSecurityGroup(securitygroupname);
		base.stepInfo(securitygroupname + " : created");
		session.basicContentSearch("Term");
		session.saveSearch(searchName);
		saveSearch.savedSearch_Searchandclick(searchName);
		saveSearch.preRequestCreation(searchName, securitygroupname);

		// Impersonate PA to RMU
		base.rolesToImp("PA", "RMU");
		base.selectsecuritygroup(securitygroupname);
		base.stepInfo("Current SG : " + sg.get_CurrentSG().getText());

		base.stepInfo("RMU User Saved \"Conceptual Search\" and Shared with Security group in Saved Search Screen.");
		driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.waitForPageToBeReady();
		int conceptualPureHit = session.conceptualSearch(Input.conceptualSearchString1, "");
		session.saveAdvanceSearchInNode(conceptualSearch, null);

		// Share Searches - Navigate to Saved Search
		saveSearch.navigateToSavedSearchPage();
		saveSearch.shareSearchFlow(conceptualSearch, securitygroupname, "RMU");

		login.logout();
		base.stepInfo("Pre-requesties completed");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Navigate to SavedSearch Page
		saveSearch.navigateToSSPage();

		// Content Search
		saveSearch.verifySharedGroupSearch(securitygroupname, conceptualSearch);
		saveSearch.savedSearchExecute_SearchGRoup(conceptualSearch, conceptualPureHit);

		// Delete Created SG
		sg.deleteSecurityGroups(securitygroupname);

		login.logout();

	}

	/**
	 * @author Raghuram A Date: 11/26/21 Modified date:N/A Modified by: N/A
	 *         Description : Validate sharing modified search/groups to Project
	 *         Administrators which are already shared-RPMXCON-49850 Sprint 07
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-49850", enabled = true, groups = { "regression" })
	public void validateSharingAlreadySharedSGWithModifications() throws Exception {

		int noOfNodesToCreate = 6;
		int selectIndex = 0;
		String SGtoShare = "Project Administrator";
		String node;
		Boolean inputValue = true;
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		HashMap<String, String> searchGroupSearchpIDpair = new HashMap<>();

		base.stepInfo("Test case Id: RPMXCON-49850 - Saved Search Sprint 07");
		base.stepInfo("Validate sharing modified search/groups to Project Administrators which are already shared");

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
		saveSearch.selectRootGroupTab(Input.mySavedSearch);
		saveSearch.rootGroupExpansion();

		searchGroupSearchpIDpair = saveSearch.collectionOfSearchIdsFromNodeCollections(newNodeList, nodeSearchpair,
				searchGroupSearchpIDpair);

		// share the Root node with pa set_01
		saveSearch.navigateToSavedSearchPage();
		saveSearch.selectRootGroupTab(Input.mySavedSearch);
		node = saveSearch.childNodeSelectionToShare(selectIndex, newNodeList);
		System.out.println("Final : " + node);
		saveSearch.shareSavedNodePA(SGtoShare, node, false, true, nodeSearchpair.get(node));
		driver.waitForPageToBeReady();
		saveSearch.verifyImpactinSharedchildNodes(SGtoShare,node, newNodeList, selectIndex, nodeSearchpair,
				searchGroupSearchpIDpair);

		// Adding new searches to root node and leaf node
		driver.getWebDriver().get(Input.url + "Search/Searches");
//		saveSearch.selectRootGroupTab(Input.mySavedSearch);
		HashMap<String, String> nodeNewSearchpair = session.addSearchInNodewithChildNode(newNodeList, 1);

		// modify existing search Name
		saveSearch.navigateToSavedSearchPage();
		driver.waitForPageToBeReady();
		saveSearch.selectRootGroupTab(Input.mySavedSearch);
		saveSearch.rootGroupExpansion();
		Map<String, String> nodeRenameSearchpair = saveSearch.modifyExistingSearchName(newNodeList, nodeSearchpair, 1);
		for (int i = 0; i < nodeRenameSearchpair.size(); i++) {
			nodeSearchpair.replace(newNodeList.get(i), nodeSearchpair.get(newNodeList.get(i)),
					nodeRenameSearchpair.get(newNodeList.get(i)));
		}

		// collecting search_ID in modified search in my saved search
		saveSearch.navigateToSavedSearchPage();
		saveSearch.selectRootGroupTab(Input.mySavedSearch);
		saveSearch.rootGroupExpansion();
		HashMap<String, String> modifiedSearchIDpair = new HashMap<String, String>();
		modifiedSearchIDpair = saveSearch.collectionOfSearchIdsFromNodeCollections(newNodeList, nodeSearchpair,
				modifiedSearchIDpair);

		// collecting search_ID in add search in my saved search
		saveSearch.navigateToSavedSearchPage();
		saveSearch.selectRootGroupTab(Input.mySavedSearch);
		saveSearch.rootGroupExpansion();
		HashMap<String, String> addSearchIDpair = new HashMap<String, String>();
		addSearchIDpair = saveSearch.collectionOfSearchIdsFromNodeCollections(newNodeList, nodeNewSearchpair,
				addSearchIDpair);

		// share the Root node with pa set_02
		saveSearch.navigateToSavedSearchPage();
		saveSearch.selectRootGroupTab(Input.mySavedSearch);
		node = saveSearch.childNodeSelectionToShare(selectIndex, newNodeList);
		System.out.println("Final : " + node);
		saveSearch.shareSavedNodePA(SGtoShare, node, false, true, nodeSearchpair.get(node));
		driver.waitForPageToBeReady();

		// verify the search with modifiedSearchIDpair
		saveSearch.verifyImpactinSharedchildNodes(SGtoShare,node, newNodeList, selectIndex, nodeSearchpair,
				modifiedSearchIDpair);
		login.logout();

		// login again as another pa to cross check

		login.loginToSightLine(Input.pa2userName, Input.pa2password);
		base.stepInfo("Loggedin As : " + Input.pa2FullName);
		base.selectproject();
		// Landed on Saved Search
		base.stepInfo("ID verfication between shared searches");
		saveSearch.navigateToSavedSearchPage();
		saveSearch.verifyImpactinSharedchildNodes(SGtoShare,node, newNodeList, selectIndex, nodeNewSearchpair,
				addSearchIDpair);
		base.stepInfo("Verified Shared SG and Searches");

		login.logout();

	}

	/**
	 * @author Raghuram A Date: 11/30/21 Modified date:N/A Modified by: N/A
	 *         Description : TC02_Verify that When PAU runs a RMU created search
	 *         (Audio) in PAU role then search returns documents Under "Count of
	 *         Results" column on "Saved Search Screen"-RPMXCON-49840 Sprint 07
	 * @throws InterruptedException
	 * @throws ParseException
	 * @Stabilization - done
	 */
	@Test(description = "RPMXCON-49840", enabled = true, groups = { "regression" })
	public void PAUrunsRMUAudioSearchValidation() throws InterruptedException, ParseException {

		String searchName = "Search Name" + Utility.dynamicNameAppender();
		String audioSearch = "audioSearch_Name" + Utility.dynamicNameAppender();
		String securitygroupname = "TestSG-" + Utility.dynamicNameAppender();
		String audioString = Input.audioSearch;
		String audioLanguage = Input.audioLanguage;

		base.stepInfo("Test case Id: RPMXCON-49840 - Sprint 07");
		base.stepInfo(
				"TC02_Verify that When PAU runs a RMU created search (Audio) in PAU role then search returns documents Under \"Count of Results\" column on \"Saved Search Screen\"");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Create Security Group
		base.stepInfo(
				"Prerequisites: Project with around 'n' docs and only 'n/2' documents are released to Security Group SG  ");
		driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		sg.AddSecurityGroup(securitygroupname);
		base.stepInfo(securitygroupname + " : created");

		// Release doc to SG
		session.audioSearch(Input.searchString1, Input.audioLanguage);
		session.saveAdvanceSearchInNode(searchName, null);
		saveSearch.savedSearch_Searchandclick(searchName);
		saveSearch.preRequestCreation(searchName, securitygroupname);

		// PAU runs RMU Audio Search Validation Flow
		saveSearch.PAUrunsRMUAudioSearchValidationFlow(securitygroupname, audioSearch, audioLanguage, audioString);

		// Delete Created SG
		sg.deleteSecurityGroups(securitygroupname);

		login.logout();

	}

	/**
	 * @author Raghuram A Description: To verify, As an user login into the
	 *         Application, it will not allow to upload a file for batch search
	 *         other than xlsx format(RPMXCON-47731)
	 * @param username
	 * @param password
	 * @param fullName
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-47731", enabled = true, dataProvider = "SavedSearchwithUsers", groups = {
			"regression" })
	public void saveSearchBatchUploadInvalidFileFormat(String username, String password, String fullName)
			throws InterruptedException {
		String search = "Search" + Utility.dynamicNameAppender();
		base.stepInfo("Test case Id: RPMXCON-47731 - Saved Search Sprint 05");

		// Login as User
		login.loginToSightLine(username, password);

		// Create Node
		String new_node = saveSearch.createSearchGroupAndReturn("Input.mysavedsearch", "PA", "No");
		driver.waitForPageToBeReady();
		saveSearch.getCreatedNode(new_node).waitAndClick(20);

		// upload batch file
		saveSearch.uploadBatchFile_D(Input.invalidBatchFileNewLocation, Input.xlsBatchFile, false);
		saveSearch.verifyBatchUploadMessage("Failure", false);

		login.logout();
	}

	/**
	 * @author Raghuram Description:User Impersonation - Validate SA Sharing saved
	 *         search after impersonating down as PAU/RMU/Reviewer RPMXCON-49897
	 *         Sprint 5
	 *         validateDAviaPASharingSavedSearchImpersonateAsRMUAndReviewer()
	 */

	@Test(description = "RPMXCON-49897", enabled = true, groups = { "regression" })
	public void validateSAviaPASharingSavedSearchImpersonateAsRMUAndReviewer() throws Exception {

		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		String SGtoShare = Input.shareSearchDefaultSG;
		String PAtoShare = Input.shareSearchPA;
		Boolean inputValue = true;
		String mySavedSearch = Input.mySavedSearch;

		base.stepInfo("Test case Id: RPMXCON-49897 - Saved Search Sprint 05");
		base.stepInfo("Validate SA Sharing saved search after impersonating down as PAU/RMU/Reviewer");

		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();
		String rmuSearch = "RMU Search Name" + UtilityLog.dynamicNameAppender();
		String reviewerSearch = "Reviewer Search Name" + UtilityLog.dynamicNameAppender();

		// Login as SA
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("User successfully logged into slightline webpage as PA with" + Input.sa1userName);

		// Impersonate as PA
		base.impersonateSAtoPA();
		base.stepInfo("Impersoante as PA from SA");

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
			if (saveSearch.getSGTab(PAtoShare).isElementAvailable(5)) {
				base.failedStep("Shared with project adminitrator security group is available for RMU user");
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
			if (saveSearch.getSGTab(PAtoShare).isElementAvailable(5)) {
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
			if (saveSearch.getSGTab(PAtoShare).isElementAvailable(5)) {
				base.failedStep("Shared with project adminitrator security group is available for RMU user");
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

		// Impersonate RMU to PA
		base.stepInfo("Impersonating RMU user as PA");
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
			if (saveSearch.getSGTab(PAtoShare).isElementAvailable(5)) {
				base.failedStep("Shared with project adminitrator security group is available for RMU user");
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
		saveSearch.getCollapsedSharedWithDefaultSecurityGroup().waitAndClick(10);
		saveSearch.verifySharedNode(newNodeList.get(0));
		saveSearch.verifySharedGroupSearch1(newNodeList.get(0), nodeSearchpair.get(newNodeList.get(0)), true);

		// Landed on Saved Search
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		// Multiple Node Creation
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(5);
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
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(5);
		saveSearch.rootGroupExpansion();
		saveSearch.shareSavedNodeWithDesiredGroup(newNodeList.get(0), SGtoShare);
		String RMUNode = newNodeList.get(0);

		// Impersonate RMU User to Reviewer
		base.stepInfo("Impersonating RMU user as Reviewer");
		base.impersonateRMUtoReviewer();

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		base.stepInfo("Verifying whether the search saved by PA role is not available for Reviewer user");
		try {
			if (saveSearch.getSGTab(PAtoShare).isElementAvailable(5)) {
				base.failedStep("Shared with project adminitrator security group is available for Reviewer user");
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
		saveSearch.getCollapsedSharedWithDefaultSecurityGroup().waitAndClick(10);
		saveSearch.verifySharedNode(PANode);
		saveSearch.verifySharedGroupSearch1(PANode, PANodeSearchName, true);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		base.stepInfo("Verifying whether the shared saved search by RMU is available in security group");
		saveSearch.getCollapsedSharedWithDefaultSecurityGroup().waitAndClick(10);
		saveSearch.verifySharedNode(RMUNode);
		saveSearch.verifySharedGroupSearch1(RMUNode, nodeSearchpair.get(RMUNode), true);
		String RMUNodeSearchName = nodeSearchpair.get(RMUNode);
		// Landed on Saved Search
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(5);
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
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(5);
		saveSearch.rootGroupExpansion();
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
	 * @author Raghuram
	 * @Description:User Impersonation - User Impersonation - Validate DAU Sharing
	 *                   saved search after impersonating down as PAU/RMU/Reviewer
	 *                   RPMXCON-49898 Sprint 5
	 */

	@Test(description = "RPMXCON-49898", enabled = true, groups = { "regression" })
	public void validateDAviaPASharingSavedSearchImpersonateAsRMUAndReviewer() throws Exception {

		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		String SGtoShare = Input.shareSearchDefaultSG;
		String PAtoShare = Input.shareSearchPA;
		Boolean inputValue = true;
		String mySavedSearch = Input.mySavedSearch;

		base.stepInfo("Test case Id: RPMXCON-49898 - Saved Search Sprint 05");
		base.stepInfo("Validate DA Sharing saved search after impersonating down as PAU/RMU/Reviewer");

		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();
		String rmuSearch = "RMU Search Name" + UtilityLog.dynamicNameAppender();
		String reviewerSearch = "Reviewer Search Name" + UtilityLog.dynamicNameAppender();

		// Login as DA
		login.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("User successfully logged into slightline webpage as DA with" + Input.da1userName);

		// Impersonate as PA
		base.impersonateDAtoPA();
		base.stepInfo("Impersoante as PA from DA");

		// Searching documents based on search string and saving the search

		base.stepInfo("Searching documents based on search string and saving the search");
		session.basicContentSearch(Input.searchString2);
		session.saveSearch(searchName);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();

		// Share Search via Saved Search
		base.stepInfo("Sharing the saved search with PA and with security group");
		saveSearch.shareSavedSearchAsPA(searchName, Input.securityGroup);

		// Impersonate PA User to RMU
		base.stepInfo("Impersonating PA user as RMU");
		base.impersonatePAtoRMU();

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();

		base.stepInfo("Verifying whether the search saved by PA role is not available for RMU user");
		try {

			if (saveSearch.getSGTab(PAtoShare).isElementAvailable(5)) {
				base.failedStep("Shared with project adminitrator security group is available for RMU user");
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
			if (saveSearch.getSGTab(PAtoShare).isElementAvailable(5)) {
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
			if (saveSearch.getSGTab(PAtoShare).isElementAvailable(5)) {
				base.failedStep("Shared with project adminitrator security group is available for RMU user");
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
			if (saveSearch.getSGTab(PAtoShare).isElementAvailable(5)) {
				base.failedStep("Shared with project adminitrator security group is available for RMU user");
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
		saveSearch.getCollapsedSharedWithDefaultSecurityGroup().waitAndClick(10);
		saveSearch.verifySharedNode(newNodeList.get(0));
		saveSearch.verifySharedGroupSearch1(newNodeList.get(0), nodeSearchpair.get(newNodeList.get(0)), true);

		// Landed on Saved Search
		base.selectproject();
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
		String RMUNode = newNodeList.get(0);

		// Impersonate RMU User to Reviewer
		base.stepInfo("Impersonating RMU user as Reviewer");
		base.impersonateRMUtoReviewer();

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		base.stepInfo("Verifying whether the search saved by PA role is not available for Reviewer user");
		try {
			if (saveSearch.getSGTab(PAtoShare).isElementAvailable(5)) {
				base.failedStep("Shared with project adminitrator security group is available for Reviewer user");
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
		saveSearch.getCollapsedSharedWithDefaultSecurityGroup().waitAndClick(10);
		saveSearch.verifySharedNode(PANode);
		saveSearch.verifySharedGroupSearch1(PANode, PANodeSearchName, true);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		base.stepInfo("Verifying whether the shared saved search by RMU is available in security group");
		saveSearch.getCollapsedSharedWithDefaultSecurityGroup().waitAndClick(10);
		saveSearch.verifySharedNode(RMUNode);
		saveSearch.verifySharedGroupSearch1(RMUNode, nodeSearchpair.get(RMUNode), true);
		String RMUNodeSearchName = nodeSearchpair.get(RMUNode);
		// Landed on Saved Search
		base.selectproject();
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

	/*
	 * @author jeevitha Description: (RPMXCON-57393)RMU/RU scheduling searches saved
	 * under My Saved Searches and verify documents
	 */
	@Test(description = "RPMXCON-57393", enabled = true, dataProvider = "SavedSearchwithRMUandREV", groups = {
			"regression" })
	public void verifySchedulingSearches(String userName, String password, String fullName) throws Exception {
		String savedSearch = "Search1" + Utility.dynamicNameAppender();
		String savedSearch2 = "Search2" + Utility.dynamicNameAppender();
		String savedSearch3 = "Search3" + Utility.dynamicNameAppender();
		String savedSearch4 = "Search4" + Utility.dynamicNameAppender();
		SchedulesPage schedule = new SchedulesPage(driver);
		String pureHit0;
		int pureHit4 = 0;

		// Login as PA
		login.loginToSightLine(userName, password);
		base.stepInfo("Test case Id: RPMXCON-57393 - Saved Search Sprint 04");

		// create Node
		String newNode1 = saveSearch.createSearchGroupAndReturn(searchName, fullName, "Yes");
		String newNode2 = saveSearch.createSearchGroupAndReturn(searchName, fullName, "Yes");
		String newNode3 = saveSearch.createSearchGroupAndReturn(searchName, fullName, "Yes");
		String newNode4 = saveSearch.createSearchGroupAndReturn(searchName, fullName, "Yes");

		int pureHit1 = session.advancedMetaDataSearch("CustodianName", null, Input.metaDataCN, null);
		session.saveSearchInNewNode(savedSearch, newNode1);

		session.getNewSearchButton().waitAndClick(20);
		int pureHit2 = session.advancedContentSearchWithSearchChanges(Input.searchString1, "Yes");
		session.saveSearchInNewNode(savedSearch2, newNode2);

		base.selectproject();
		int pureHit3 = session.conceptualSearch_new("field report", "mid");
		session.saveSearchInNewNode(savedSearch3, newNode3);
		System.out.println(pureHit3);

		base.selectproject();
		session.switchToWorkproduct();
		session.selectRedactioninWPS(Input.defaultRedactionTag);
		pureHit4 = session.saveAndReturnPureHitCount();
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

			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.selectNode1(Nodes);
			// Schedule save search
			saveSearch.scheduleSavedSearchInMinute(searches, 2);

			// Make sure shared Node reflected in the SG
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.selectRootGroupTab(Input.mySavedSearch);
			saveSearch.selectNode1(Nodes);
			saveSearch.savedSearch_SearchandSelect(searches, "Yes");

			// verify Document count After Execution
			String actualDocCount = saveSearch.getCountofDocs().getText();
			System.out.println(actualDocCount);
			softAssertion.assertEquals(pureHit0, actualDocCount);

			// Perform Bulk action
			driver.Navigate().refresh();
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.selectRootGroupTab(Input.mySavedSearch);
			saveSearch.selectNode1(Nodes);
			saveSearch.savedSearch_SearchandSelect(searches, "Yes");
			saveSearch.saveSearchToDoclist();
			k++;
		}
		softAssertion.assertAll();
		login.logout();
	}

	/*
	 * @author jeevitha Description: (RPMXCON-57393)RMU/RU scheduling searches saved
	 * under My Saved Searches and verify documents
	 */
	@Test(description = "RPMXCON-57393", enabled = true, dataProvider = "SavedSearchwithRMUandREV", groups = {
			"regression" })
	public void verifySchedulingSearches2(String userName, String password, String fullName) throws Exception {
		String savedSearch = "Search1" + Utility.dynamicNameAppender();
		String savedSearch2 = "Search2" + Utility.dynamicNameAppender();
		String savedSearch3 = "Search3" + Utility.dynamicNameAppender();
		String savedSearch4 = "Search4" + Utility.dynamicNameAppender();
		SchedulesPage schedule = new SchedulesPage(driver);
		String pureHit0;
		int pureHit4 = 0;

		// Login as PA
		login.loginToSightLine(userName, password);
		base.stepInfo("Test case Id: RPMXCON-57393 - Saved Search Sprint 04");

		// create Node
		String newNode1 = saveSearch.createSearchGroupAndReturn(searchName, fullName, "Yes");
		String child1 = saveSearch.createNewSearchGrp(newNode1, 1);
		String newNode2 = saveSearch.createSearchGroupAndReturn(searchName, fullName, "Yes");
		String child2 = saveSearch.createNewSearchGrp(newNode2, 1);
		String newNode3 = saveSearch.createSearchGroupAndReturn(searchName, fullName, "Yes");
		String child3 = saveSearch.createNewSearchGrp(newNode3, 1);
		String newNode4 = saveSearch.createSearchGroupAndReturn(searchName, fullName, "Yes");
		String child4 = saveSearch.createNewSearchGrp(newNode4, 1);

		int pureHit1 = session.advancedMetaDataSearch("CustodianName", null, Input.metaDataCN, null);
		session.saveSearchInRootNode(savedSearch, newNode1, child1);

		session.getNewSearchButton().waitAndClick(20);
		int pureHit2 = session.advancedContentSearchWithSearchChanges(Input.searchString1, "Yes");
		session.saveSearchInRootNode(savedSearch2, newNode2, child2);

		base.selectproject();
		int pureHit3 = session.conceptualSearch_new("field report", "mid");
		session.saveSearchInRootNode(savedSearch3, newNode3, child3);
		System.out.println(pureHit3);

		base.selectproject();
		session.switchToWorkproduct();
		session.selectRedactioninWPS(Input.defaultRedactionTag);
		pureHit4 = session.saveAndReturnPureHitCount();
		driver.scrollPageToTop();
		session.saveSearchInRootNode(savedSearch4, newNode4, child4);
		System.out.println(pureHit4);

		String dataSet[][] = { { savedSearch, String.valueOf(pureHit1) }, { savedSearch2, String.valueOf(pureHit2) },
				{ savedSearch3, String.valueOf(pureHit3) }, { savedSearch4, String.valueOf(pureHit4) } };
		String nodeList[][] = { { newNode1, child1 }, { newNode2, child2 }, { newNode3, child3 },
				{ newNode4, child4 } };

		int k = 0;

		for (int i = 0; i < dataSet.length; i++) {

			int j = 0;
			int l = 0;
			String searches = dataSet[i][j];
			j++;
			pureHit0 = dataSet[i][j];
			String Nodes = nodeList[k][l];
			l++;
			String childNode = nodeList[k][l];

			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.selectNode1(Nodes);
			saveSearch.selectNode1(childNode);
			// Schedule save search
			saveSearch.scheduleSavedSearchInMinute(searches, 2);

			// Make sure shared Node reflected in the SG
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.selectNode1(Nodes);
			saveSearch.selectNode1(childNode);
			saveSearch.savedSearch_SearchandSelect(searches, "Yes");

			// verify Document count After Execution
			String actualDocCount = saveSearch.getCountofDocs().getText();
			System.out.println(actualDocCount);
			softAssertion.assertEquals(pureHit0, actualDocCount);

			// Perform Bulk action
			driver.Navigate().refresh();
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.selectNode1(Nodes);
			saveSearch.selectNode1(childNode);
			saveSearch.savedSearch_SearchandSelect(searches, "Yes");
			saveSearch.saveSearchToDoclist();
			k++;

		}
		softAssertion.assertAll();
		login.logout();

	}

	/*
	 * @author jeevitha Description: (RPMXCON-57396)DAU impersonating down as RMU/RU
	 * schedule searches saved under MY Saved Searches
	 */
	@Test(description = "RPMXCON-57396", enabled = true, dataProvider = "userRolesOnlyrmuAndRev", groups = {
			"regression" })
	public void verifyDASchedulingSearches(String role) throws Exception {
		String savedSearch = "Search1" + Utility.dynamicNameAppender();
		String savedSearch2 = "Search2" + Utility.dynamicNameAppender();
		String savedSearch3 = "Search3" + Utility.dynamicNameAppender();
		String savedSearch4 = "Search4" + Utility.dynamicNameAppender();
		SchedulesPage schedule = new SchedulesPage(driver);
		String pureHit0;
		int pureHit4 = 0;

		// Login as PA
		login.loginToSightLine(Input.da1userName, Input.da1password);
		if (role.equalsIgnoreCase("RMU")) {
			base.impersonateSAtoRMU();
		} else if (role.equalsIgnoreCase("REV")) {
			base.impersonateSAtoReviewer();
		}
		base.stepInfo("Impersonated as : " + role);
		base.stepInfo("Test case Id: RPMXCON-57396 - Saved Search Sprint 04");

		// create Node
		String newNode1 = saveSearch.createSearchGroupAndReturn(searchName, role, "Yes");
		String newNode2 = saveSearch.createSearchGroupAndReturn(searchName, role, "Yes");
		String newNode3 = saveSearch.createSearchGroupAndReturn(searchName, role, "Yes");
		String newNode4 = saveSearch.createSearchGroupAndReturn(searchName, role, "Yes");

		int pureHit1 = session.advancedMetaDataSearch("CustodianName", null, Input.metaDataCN, null);
		session.saveSearchInNewNode(savedSearch, newNode1);

		session.getNewSearchButton().waitAndClick(20);
		int pureHit2 = session.advancedContentSearchWithSearchChanges(Input.searchString1, "Yes");
		session.saveSearchInNewNode(savedSearch2, newNode2);

		base.selectproject();
		int pureHit3 = session.conceptualSearch_new("field report", "mid");
		session.saveSearchInNewNode(savedSearch3, newNode3);
		System.out.println(pureHit3);

		base.selectproject();
		session.switchToWorkproduct();
		session.selectRedactioninWPS(Input.defaultRedactionTag);
		pureHit4 = session.saveAndReturnPureHitCount();
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

	/*
	 * @author jeevitha Description: (RPMXCON-57396)DAU impersonating down as RMU/RU
	 * schedule searches saved under MY Saved Searches
	 * 
	 */
	@Test(description = "RPMXCON-57396", enabled = true, dataProvider = "userRolesOnlyrmuAndRev", groups = {
			"regression" })
	public void verifyDASchedulingSearches2(String role) throws Exception {
		String savedSearch = "Search1" + Utility.dynamicNameAppender();
		String savedSearch2 = "Search2" + Utility.dynamicNameAppender();
		String savedSearch3 = "Search3" + Utility.dynamicNameAppender();
		String savedSearch4 = "Search4" + Utility.dynamicNameAppender();
		SchedulesPage schedule = new SchedulesPage(driver);
		String pureHit0;
		int pureHit4 = 0;

		// Login as PA
		login.loginToSightLine(Input.da1userName, Input.da1password);
		if (role.equalsIgnoreCase("RMU")) {
			base.impersonateSAtoRMU();
		} else if (role.equalsIgnoreCase("REV")) {
			base.impersonateSAtoReviewer();
		}
		base.stepInfo("Impersonated as : " + role);
		base.stepInfo("Test case Id: RPMXCON-57396 - Saved Search Sprint 04");

		// create Node
		String newNode1 = saveSearch.createSearchGroupAndReturn(searchName, role, "Yes");
		String child1 = saveSearch.createNewSearchGrp(newNode1, 1);
		String newNode2 = saveSearch.createSearchGroupAndReturn(searchName, role, "Yes");
		String child2 = saveSearch.createNewSearchGrp(newNode2, 1);
		String newNode3 = saveSearch.createSearchGroupAndReturn(searchName, role, "Yes");
		String child3 = saveSearch.createNewSearchGrp(newNode3, 1);
		String newNode4 = saveSearch.createSearchGroupAndReturn(searchName, role, "Yes");
		String child4 = saveSearch.createNewSearchGrp(newNode4, 1);

		int pureHit1 = session.advancedMetaDataSearch("CustodianName", null, Input.metaDataCN, null);
		session.saveSearchInRootNode(savedSearch, newNode1, child1);

		session.getNewSearchButton().waitAndClick(20);
		int pureHit2 = session.advancedContentSearchWithSearchChanges(Input.searchString1, "Yes");
		session.saveSearchInRootNode(savedSearch2, newNode2, child2);

		base.selectproject();
		int pureHit3 = session.conceptualSearch_new("field report", "mid");
		session.saveSearchInRootNode(savedSearch3, newNode3, child3);
		System.out.println(pureHit3);

		base.selectproject();
		session.switchToWorkproduct();
		session.selectRedactioninWPS(Input.defaultRedactionTag);
		pureHit4 = session.saveAndReturnPureHitCount();
		driver.scrollPageToTop();
		session.saveSearchInRootNode(savedSearch4, newNode4, child4);
		System.out.println(pureHit4);

		String dataSet[][] = { { savedSearch, String.valueOf(pureHit1) }, { savedSearch2, String.valueOf(pureHit2) },
				{ savedSearch3, String.valueOf(pureHit3) }, { savedSearch4, String.valueOf(pureHit4) } };
		String nodeList[][] = { { newNode1, child1 }, { newNode2, child2 }, { newNode3, child3 },
				{ newNode4, child4 } };

		int k = 0;

		for (int i = 0; i < dataSet.length; i++) {

			int j = 0;
			int l = 0;
			String searches = dataSet[i][j];
			j++;
			pureHit0 = dataSet[i][j];
			String Nodes = nodeList[k][l];
			l++;
			String childNode = nodeList[k][l];

			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.selectNode1(Nodes);
			saveSearch.selectNode1(childNode);
			// Schedule save search
			saveSearch.scheduleSavedSearchInMinute(searches, 2);

			// Make sure shared Node reflected in the SG
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.selectNode1(Nodes);
			saveSearch.selectNode1(childNode);
			saveSearch.savedSearch_SearchandSelect(searches, "Yes");

			// verify Document count After Execution
			String actualDocCount = saveSearch.getCountofDocs().getText();
			System.out.println(actualDocCount);
			softAssertion.assertEquals(pureHit0, actualDocCount);

			// Perform Bulk action
			driver.Navigate().refresh();
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.selectNode1(Nodes);
			saveSearch.selectNode1(childNode);
			saveSearch.savedSearch_SearchandSelect(searches, "Yes");
			saveSearch.saveSearchToDoclist();
			k++;

		}
		softAssertion.assertAll();
		login.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws Exception
	 * @description Verify that when batch upload is done with Security Groups then
	 *              count should be displayed correctly on saved search after Search
	 *              completes
	 */
	@Test(description = "RPMXCON-49092", enabled = true, groups = { "regression" })
	public void validateSecurityGroupCountsThroughBatchFile() throws Exception {
		base.stepInfo("Test case Id: RPMXCON-49092");
		base.stepInfo(
				"Verify that when batch upload is done with Security Groups then count should be displayed correctly on saved search after Search completes");
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		session.switchToWorkproduct();
		session.selectSecurityGinWPS(Input.securityGroup);
		int pureHits = session.serarchWP();
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.uploadWPBatchFile(saveSearch.renameFile(Input.WPbatchFile));
		base.passedStep("Batch file uploaded successfully");
		saveSearch.verifyDocCountsAndStatus("WP securityGroups", pureHits);
		driver.scrollPageToTop();
		saveSearch.getSavedSearchDeleteButton().waitAndClick(10);
		base.waitForElement(saveSearch.getDeleteOkBtn());
		saveSearch.getDeleteOkBtn().waitAndClick(10);
		base.VerifySuccessMessage("Save search tree node successfully deleted.");
		login.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @description Verify that when batch upload is done with Assignments then
	 *              count should be displayed correctly on saved search after Search
	 *              completes
	 */

	@Test(description = "RPMXCON-49095", enabled = true, groups = { "regression" })

	public void validateAssignmentDocCountsThroughBatchFile() throws Exception {
		base.stepInfo("Test case Id: RPMXCON-49095");
		base.stepInfo(
				"Verify that when batch upload is done with Assignments then count should be displayed correctly on saved search after Search completes");
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		String assignmentName = "batchAssignment";
		assign.createAssignment(assignmentName, Input.codeFormName);
		try {
			int pureHits = session.basicContentSearch("null");
			session.bulkAssignExisting(assignmentName);
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.uploadWPBatchFile(saveSearch.renameFile(Input.WPbatchFile));
			base.passedStep("Batch file uploaded successfully");
			saveSearch.verifyDocCountsAndStatus("WP assignment", pureHits);
			driver.scrollPageToTop();
			saveSearch.getSavedSearchDeleteButton().waitAndClick(10);
			base.waitForElement(saveSearch.getDeleteOkBtn());
			saveSearch.getDeleteOkBtn().waitAndClick(10);
			base.VerifySuccessMessage("Save search tree node successfully deleted.");
			assign.deleteAssgnmntUsingPagination(assignmentName);
			login.logout();
		} catch (Exception e) {
			assign.deleteAssgnmntUsingPagination(assignmentName);
			base.failedStep("Failed to validate document counts via batchfile ");
		}

	}

	/**
	 * @author jeevitha Description : Verify status and count in Saved Search screen
	 *         when user uploads Batch Search file with Basic and Advanced search
	 *         queries (RPMXCON-48536)
	 */
	@Test(description = "RPMXCON-48536", enabled = true, groups = { "regression" })
	public void verifyBatchUpload() throws Exception {
		String advanceSearch = "Basic Work Product";
		String basicsearch = "Basic Content Search without Family Members";
		String File = saveSearch.renameFile(Input.WPbatchFile);
		String nearDupe = "Near Duplicate Count";

		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case Id: RPMXCON-48536  Saved Search");
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.uploadWPBatchFile(File);
		saveSearch.getDocCountAndStatusOfBatch(advanceSearch, nearDupe, true);

		driver.waitForPageToBeReady();
		saveSearch.getDocCountAndStatusOfBatch(basicsearch, nearDupe, true);

		// Delete Uploaded File
		driver.Navigate().refresh();
		saveSearch.getMySeraches().waitAndClick(10);
		base.waitForElement(saveSearch.getSelectUploadedFile(File));
		saveSearch.getSelectUploadedFile(File).waitAndClick(20);

		saveSearch.deleteFunctionality();
		login.logout();
	}

	/**
	 * @author jeevitha Description : Verify that when batch upload is done with
	 *         Tags then count should be displayed correctly on saved search after
	 *         Search completes (RPMXCON-49089) @modified on : 12/4/21
	 */
	@Test(description = "RPMXCON-49089", enabled = true, dataProvider = "SavedSearchwithoutReviewer", groups = {
			"regression" })
	public void validateTagThroughBatchFile(String username, String password) throws Exception {
		String tag = "WPTag";
		String File = saveSearch.renameFile(Input.WPbatchFile);

		// login
		login.loginToSightLine(username, password);
		base.stepInfo("Test case Id: RPMXCON-49089 Saved Search");
		base.stepInfo(
				"Verify that when batch upload is done with Tags then count should be displayed correctly on saved search after Search completes");

		// Basic Search and perform Bulk Tag
		int pureHits = session.basicContentSearch(Input.searchString1);
		try {
			session.bulkTag(tag);
		} catch (InterruptedException e) {
			System.out.println("Tag Already Exists");
			session.bulkTagExisting(tag);
		}

		// upload batch File And verify Status
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.uploadWPBatchFile(File);
		saveSearch.verifyDocCountsAndStatus(tag, pureHits);

		// Delete Uploaded File
		driver.Navigate().refresh();
		saveSearch.getMySeraches().waitAndClick(10);
		base.waitForElement(saveSearch.getSelectUploadedFile(File));
		saveSearch.getSelectUploadedFile(File).waitAndClick(20);

		saveSearch.deleteFunctionality();

		// Delete Tag
		driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tag);

		login.logout();
	}

	/**
	 * @author jeevitha Description : Verify that when batch upload is done with
	 *         Folders then count should be displayed correctly on saved search
	 *         after Search completes (RPMXCON-49090)
	 * @modified on : 12/4/21 by : Jeevitha
	 */
	@Test(description = "RPMXCON-49090", enabled = true, dataProvider = "SavedSearchwithoutReviewer", groups = {
			"regression" })
	public void validateFolderThroughBatchFile(String username, String password) throws Exception {
		String folder = "WPFolder";
		String File = saveSearch.renameFile(Input.WPbatchFile);

		// login
		login.loginToSightLine(username, password);
		base.stepInfo("Test case Id: RPMXCON-49090 Saved Search");
		base.stepInfo(
				"Verify that when batch upload is done with Folders then count should be displayed correctly on saved search after Search completes");

		// Basic Search and perform Bulk Tag
		int pureHits = session.basicContentSearch(Input.searchString1);
		try {
			session.bulkFolder(folder);
		} catch (InterruptedException e) {
			System.out.println("Folder Already Exists");
			session.bulkFolderExisting(folder);
		}

		// upload batch File And verify Status
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.uploadWPBatchFile(File);
		saveSearch.verifyDocCountsAndStatus(folder, pureHits);

		// Delete Uploaded File
		driver.Navigate().refresh();
		saveSearch.getMySeraches().waitAndClick(10);
		base.waitForElement(saveSearch.getSelectUploadedFile(File));
		saveSearch.getSelectUploadedFile(File).waitAndClick(20);

		saveSearch.deleteFunctionality();
		// delete folder
		driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(folder);
		login.logout();
	}

	/**
	 * @author Raghuram A Description : Verify that application displays all
	 *         documents that are in the aggregate results set of Shared groups and
	 *         Export (RPMXCON-49016)
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-49016", enabled = true, groups = { "regression" })
	public void verifyExportDocs() throws InterruptedException {

		String SearchName = "Search" + Utility.dynamicNameAppender();
		String folderName = "Folder" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-49016 - Saved Search Sprint 05");
		base.stepInfo(
				"Verify that application displays all documents that are in the aggregate results set of Shared groups and Export");
		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo(Input.pa1FullName);

		// create new search group and save search
		String new_node = saveSearch.createASearchGroupandReturnName(SearchName);

		// create folder and add save search in folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(folderName, Input.securityGroup);

		// Search and Save
		int purehit = session.basicContentSearch(Input.searchString1);
		// session.saveSearchInNode(SearchName);
		session.saveSearchInNewNode(SearchName, new_node);
		driver.waitForPageToBeReady();
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		base.stepInfo("Landed on SavedSearchPage");

		// Share under available shared groups
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		String searchID1 = saveSearch.shareSavedNodePA("Shared with Default Security Group", new_node, true, true,
				SearchName); // Input.shareSearchDefaultSG

		// Make sure shared Node reflected in the SG & Select the shared Search
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.getSavedSearchGroupName("Shared with Default Security Group").waitAndClick(10); // Input.shareSearchDefaultSG
		saveSearch.selectNode1(new_node);
		saveSearch.savedSearch_SearchandSelect(SearchName, "Yes"); // modified

		// Verify Export
		saveSearch.getSavedSearchExportButton().Click();
		base.waitForElement(saveSearch.getExportPopup());

		report.customDataReportMethodExport(folderName, false);
		driver.waitForPageToBeReady();
		int Bgcount = base.initialBgCount();
		System.out.println("Initial bg count : " + Bgcount);
		// Download report
		report.downLoadReport(Bgcount);
		base.stepInfo("File Downloaded");

		base.stepInfo(
				"Verifying Exported file lists all the documents from the search group with  selected metadata and work product.");
		int countToCompare = saveSearch.fileVerificationSpecificMethod();
		base.stepInfo("Document count from the export : " + countToCompare);
		softAssertion.assertEquals(countToCompare, purehit);
		if (countToCompare == purehit) {
			System.out.println("Pass");
			base.passedStep(
					"Exported file lists all the documents from the search group with  selected metadata and work product -Purehit and File count matches");
		} else {
			System.out.println("Fail");
			base.failedStep("Purehit and File count doesn't match");
		}

		softAssertion.assertAll();
		login.logout();

	}

	/**
	 * @author Raghuram A Description : Validate DAU user is allowed to move
	 *         SearchGroups/Searches only within MySearches (RPMXCON-49948)
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-49948", enabled = true, groups = { "regression" })
	public void verifyMoveActionViaDAU() throws InterruptedException {

		base.stepInfo("RPMXCON-49948 Saved Search - Sprint 05");
		base.stepInfo("Validate DAU user is allowed to move SearchGroups/Searches only within MySearches");
		// Login as DAU
		login.loginToSightLine(Input.da1userName, Input.da1password);

		// Validate DAU user is allowed to move SearchGroups/Searches
		saveSearch.validateMoveActionsVIaDiffRoles();

		login.logout();

	}

	/**
	 * @author Raghuram A Description : To verify that user can release all the
	 *         tagged document into a security group which is associated from Save
	 *         Search->Bulk Tag. (RPMXCON-49957) Modified on : 12/4/21 - delete
	 *         datas
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-49957", enabled = true, groups = { "regression" })
	public void releaseAllTagtoSGviaSS() throws InterruptedException {
		String TagName = "Tag" + Utility.dynamicNameAppender(), finalCount;
		int pureHit, finalCountresult;
		int latencyCheckTime = 5;
		String passMessage = "Application not hang or shows latency more than " + latencyCheckTime + " seconds.";
		String failureMsg = "Continues Loading more than " + latencyCheckTime + " seconds.";
		String SearchName = "Search Name" + Utility.dynamicNameAppender();

		base.stepInfo("RPMXCON-49957 Saved Search - Sprint 05");
		base.stepInfo(
				"To verify that user can release all the tagged document into a security group which is associated from Save Search->Bulk Tag.");
		// Login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin as : " + Input.pa1FullName);

		// create new search group and save search
		saveSearch.createSearchGroupAndReturn("Input.mysavedsearch", "PA", "No");
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		String new_node = saveSearch.getSavedSearchNewNode().getText();
		base.stepInfo("Search and saveSearch in the created node");
		int purehit = session.basicContentSearch(Input.searchString1);
//		session.saveSearchInNode(SearchName);
		session.saveSearchInNewNode(SearchName, new_node);
		driver.waitForPageToBeReady();
		base.selectproject();

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		base.stepInfo("Landed on SavedSearchPage");
		saveSearch.selectNode1(new_node);
		saveSearch.savedSearch_SearchandSelect(SearchName, "Yes");
		saveSearch.getSavedSearchToBulkTag().Click();
		base.stepInfo("Clicked tag icon from Code");

		// Load latency Verification
		Element loadingElement = saveSearch.getTotalCountLoad();
		saveSearch.loadingCountVerify(loadingElement, latencyCheckTime, passMessage, failureMsg);
		finalCount = session.bulkActions_TagSS_returnCount(TagName);
		base.stepInfo("Completed Bulk Tag");

		// Tag >> Bulk release action
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.getTagName(TagName).waitAndClick(5);
		tagsAndFolderPage.selectActionArrow("Bulk release");
		int bulkReleaseDocCount = tagsAndFolderPage.verifyBulk_releaseCount(Input.securityGroup);
		base.stepInfo("Documents released to the tag : " + TagName + "  with SG : " + Input.securityGroup);

		base.stepInfo(
				"Navigating to Search >> Basic Search >> Advanced Search >> WorkProduct >> Tags >> Verify document count.");
		session.switchToWorkproduct();
		session.selectTagInASwp(TagName);
		pureHit = session.serarchWP();
		if (bulkReleaseDocCount == pureHit) {
			softAssertion.assertEquals(bulkReleaseDocCount, pureHit);
			base.passedStep("All documents released to the Security group.");
		} else {
			base.failedStep("Count Mismatches");
		}

		// Delete Node
		base.stepInfo("Initiated Delete Searches");
		saveSearch.deleteNode(Input.mySavedSearch, new_node);

		softAssertion.assertAll();
		login.logout();

	}

	/**
	 * @author Raghuram A Description : SA/DA/PA user impersonate down as RMU/RU
	 *         role, create Searchgroups and Searches, and then runs the
	 *         Communication report against My saved searches in PAU role
	 *         (RPMXCON-57402) Sprint 05
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57402", enabled = true, dataProvider = "UserSaUDaUPaU", groups = { "regression" })
	public void communicatioNReport(String username, String password, String fullName, String role)
			throws InterruptedException {

		String searchName = "Search Name" + Utility.dynamicNameAppender();
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();

		report = new ReportsPage(driver);
		base.stepInfo("Test case Id: RPMXCON-57402 - Saved Search Sprint 05");
		base.stepInfo(
				"SA/DA/PA user impersonate down as RMU/RU role, create Searchgroups and Searches, and then runs the Communication report against My saved searches in PAU role");

		// Login via User
		login.loginToSightLine(username, password);
		base.stepInfo("Loggedin As : " + role);
		// Pre-requesties
		base.rolesToImp(role, "PA");
		String newNodeFromPA = saveSearch.createSearchGroupAndReturn(searchName, "PA");
		int purehit = session.basicContentSearch(Input.searchString1);

		// Impersonate As RMU via PA and create new searchgroup
		base.rolesToImp("PA", "RMU");
		String newNodeFromRMU = saveSearch.createSearchGroupAndReturn(searchName, "RMU");

		// Save Search
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName);
		// session.saveSearchInNode(searchName);
		session.saveSearchInNewNode(searchName, newNodeFromRMU);

		// impersonate As REV and create new searchgroup
		base.rolesToImp("RMU", "REV");
		String newNodeFromRev = saveSearch.createSearchGroupAndReturn(searchName, "REV");

		// Save search
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName1);
		// session.saveSearchInNode(searchName1);
		session.saveSearchInNewNode(searchName1, newNodeFromRev);
		driver.waitForPageToBeReady();

		// impersonate As Reviewer to RMU
		driver.waitForPageToBeReady();
		base.rolesToImp("REV", "RMU");

		report.VerificationAndrcommunicationReport(newNodeFromPA, newNodeFromRMU, newNodeFromRev, searchName,
				searchName1, purehit);

		login.logout();

	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws Exception
	 * @description Verify that when batch upload is done with Saved Search Results
	 *              then count should be displayed correctly on saved search after
	 *              Search completes
	 */
	@Test(description = "RPMXCON-49093", enabled = true, dataProvider = "SavedSearchwithUsers", groups = {
			"regression" })
	public void validateSavedSearchResultsThroughBatchFile(String username, String password, String fullName)
			throws Exception {
		base.stepInfo("Test case Id: RPMXCON-49093");
		base.stepInfo(
				"Verify that when batch upload is done with Saved Search Results then count should be displayed correctly on saved search after Search completes");
		login.loginToSightLine(username, password);
		try {
			int pureHits = session.MetaDataSearchInAdvancedSearch(Input.metaDataName, "Andrew");
			session.saveSearchAdvanced("batchSavedSearch");
			base.selectproject();
			session.switchToWorkproduct();
			String id = session.getSavedSearchId("batchSavedSearch");
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			System.out.println(Input.WPbatchFile);
			String batchFIleName = saveSearch.renameFile(Input.WPbatchFile);
			System.out.println(batchFIleName);
			saveSearch.writeDataIntoExcel(batchFIleName, "savedSearches: [ ID: [" + id + "] ]");
			saveSearch.uploadWPBatchFile(batchFIleName);
			base.passedStep("Batch file uploaded successfully");
			driver.waitForPageToBeReady();
			saveSearch.verifyDocCountsAndStatus("WP savedSearch", pureHits);
			driver.scrollPageToTop();
			saveSearch.getSavedSearchDeleteButton().waitAndClick(10);
			base.waitForElement(saveSearch.getDeleteOkBtn());
			saveSearch.getDeleteOkBtn().waitAndClick(10);
			base.VerifySuccessMessage("Save search tree node successfully deleted.");
			saveSearch.SaveSearchDelete("batchSavedSearch");
			login.logout();
		} catch (Exception e) {
			saveSearch.SaveSearchDelete("batchSavedSearch");
			base.failedStep("Failed to validate document counts via batchfile ");
		}

	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws Exception
	 * @description To verify Refresh action on saved search screen under My Saved
	 *              Search and Shared folders
	 */
	@Test(description = "RPMXCON-49960", enabled = true, dataProvider = "SavedSearchwithUsers", groups = {
			"regression" })
	public void validateSavedSearchResultsThroughBatchFileAftreRefreshing(String username, String password,
			String fullname) throws Exception {
		base.stepInfo("Test case Id: RPMXCON-49960");
		base.stepInfo("To verify Refresh action on saved search screen under My Saved Search and Shared folders");
		login.loginToSightLine(username, password);
		try {
			session.MetaDataSearchInAdvancedSearch(Input.metaDataName, "Andrew");
			session.saveSearchAdvanced("batchSavedSearch");
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			System.out.println(Input.WPbatchFile);
			saveSearch.uploadWPBatchFile(saveSearch.renameFile(Input.WPbatchFile));
			base.passedStep("Batch file uploaded successfully");
			saveSearch.selectSavedSearch("WP savedSearch");
			saveSearch.getSavedSearchRefresh().waitAndClick(5);
			base.waitForElement(saveSearch.getSavedSearch_SearchName());
			saveSearch.getSavedSearch_SearchName().SendKeys("WP savedSearch");
			base.waitTillElemetToBeClickable(saveSearch.getSavedSearch_ApplyFilterButton());
			saveSearch.getSavedSearch_ApplyFilterButton().waitAndClick(10);
			base.waitForElement(saveSearch.getStatusBySavedSearchStatus("WP savedSearch", "COMPLETED"));
			if (saveSearch.getStatusBySavedSearchStatus("WP savedSearch", "COMPLETED").isElementPresent() == true) {
				base.passedStep("After the refreshing the query the status is displayed as completed as expected");
			} else {
				base.failedStep("After the refreshing the query the status is not displayed as completed");
			}
			driver.scrollPageToTop();
			saveSearch.getSavedSearchDeleteButton().waitAndClick(10);
			base.waitForElement(saveSearch.getDeleteOkBtn());
			saveSearch.getDeleteOkBtn().waitAndClick(10);
			base.VerifySuccessMessage("Save search tree node successfully deleted.");
			base.waitTillElemetToBeClickable(saveSearch.getSavedSearchGroupName(Input.mySavedSearch));
			saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(5);
			saveSearch.selectSavedSearch("batchSavedSearch");
			saveSearch.getSavedSearchRefresh().waitAndClick(5);
			saveSearch.selectSavedSearch("batchSavedSearch");
			base.waitForElement(saveSearch.getStatusBySavedSearchStatus("batchSavedSearch", "COMPLETED"));
			if (saveSearch.getStatusBySavedSearchStatus("batchSavedSearch", "COMPLETED").isElementPresent() == true) {
				base.passedStep("After the refreshing the query the status is displayed as completed as expected");
			} else {
				base.failedStep("After the refreshing the query the status is not displayed as completed");
			}
			saveSearch.SaveSearchDelete("batchSavedSearch");
			login.logout();
		} catch (Exception e) {
			saveSearch.SaveSearchDelete("batchSavedSearch");
			base.failedStep("Failed to validate document counts via batchfile ");
		}

	}

	/**
	 * @author Mohan Date: 11/01/21 Modified date:N/A Modified by: N/A Description :
	 *         Validate executing searches/groups from the shared with PAU by any
	 *         other PAU user-RPMXCON-49854 Sprint 05
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-49854", enabled = true, groups = { "regression" })
	public void validateSavedSearchPageProperOptions() throws InterruptedException, ParseException {
		int noOfNodesToCreate = 2;
		int selectIndex = 1;
		int rowNo = 9;
		int countNo = 3;
		String nodeName = Input.shareSearchPA;
		String defaultTab = "Shared with Default Security Group";
		String node;
		Boolean inputValue = true;
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();

		base.stepInfo("Test case Id: RPMXCON-49854");
		base.stepInfo(" Validate executing searches/groups from the shared with PAU by any other PAU user");

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
		saveSearch.shareSavedNodePA(nodeName, node, false, true, nodeSearchpair.get(node));

		System.out.println(nodeSearchpair.get(node));
		node = saveSearch.childNodeSelectionToShare(selectIndex, newNodeList);
		System.out.println("Final : " + node);
		saveSearch.shareSavedNodePA(defaultTab, node, false, true, nodeSearchpair.get(node));

		base.stepInfo("-------Pre-requesties completed--------");

		base.waitForElement(saveSearch.getSavedSearchTreeNode(nodeName));
		saveSearch.getSavedSearchTreeNode(nodeName).waitAndClick(10);
		saveSearch.selectChildNodeOfSharedWithProjectAdmin();
		saveSearch.executeChildNodeOfProjectAdmin(rowNo, countNo);
		saveSearch.selectRootGroupTab(Input.shareSearchDefaultSG);
		saveSearch.rootGroupExpansion();
		saveSearch.selectChildNodeOfSharedWithDefault();
		saveSearch.executeAnySpecficSearch(rowNo, countNo);

		login.logout();

		// Login as PA
		login.loginToSightLine(Input.pa2userName, Input.pa2password);
		base.stepInfo("Loggedin As : " + Input.pa2FullName);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.selectChildNodeOfSharedWithProjectAdmin();
		saveSearch.verifyChildNodeCount(countNo);
		saveSearch.selectRootGroupTab(Input.shareSearchDefaultSG);
		saveSearch.rootGroupExpansion();
		saveSearch.selectChildNodeOfSharedWithDefault();
		saveSearch.verifyChildNodeCount(countNo);

		// logout
		login.logout();
	}

	/**
	 * @author jayanthi ganesan Description: (RPMXCON-57398)SA impersonate down as
	 *         PAU/RMU/RU, save search under <Shared with
	 *         SecurityGroupName>directly, schedule saved search and verify
	 *         documents
	 * 
	 */
	@Test(description = "RPMXCON-57398", enabled = true, dataProvider = "user_Roles", groups = { "regression" })
	public void verifySASchedulingSearches_Impersonation(String role) throws Exception {
		String savedSearch1 = "Search1" + Utility.dynamicNameAppender();
		String savedSearch2 = "Search2" + Utility.dynamicNameAppender();
		String savedSearch3 = "Search3" + Utility.dynamicNameAppender();
		String savedSearch4 = "Search4" + Utility.dynamicNameAppender();
		SchedulesPage schedule = new SchedulesPage(driver);
		String pureHit0;
		int pureHit4 = 0;
		SoftAssert softAssertion=new SoftAssert();
		// Login as SA
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Logged in as SA");
		if (role.equalsIgnoreCase("RMU")) {
			base.impersonateSAtoRMU();
		} else if (role.equalsIgnoreCase("REV")) {
			base.impersonateSAtoReviewer();
		} else if (role.equalsIgnoreCase("PA")) {
			base.impersonateSAtoPA();
		}
		base.stepInfo("Impersonated as : " + role);
		base.stepInfo("Test case Id: RPMXCON-57398");
		base.stepInfo(
				"SA impersonate down as PAU/RMU/RU, save search under <Shared with SecurityGroupName>directly,schedule "
						+ "saved search and verify documents");

		// create Node
		String newNode1 = saveSearch.createSearchGroupAndReturn(searchName, role, "Yes");
		String child1 = saveSearch.createNewSearchGrp(newNode1, 1);
		String newNode2 = saveSearch.createSearchGroupAndReturn(searchName, role, "Yes");
		String child2 = saveSearch.createNewSearchGrp(newNode2, 1);
		String newNode3 = saveSearch.createSearchGroupAndReturn(searchName, role, "Yes");
		String child3 = saveSearch.createNewSearchGrp(newNode3, 1);
		String newNode4 = saveSearch.createSearchGroupAndReturn(searchName, role, "Yes");
		String child4 = saveSearch.createNewSearchGrp(newNode4, 1);

		int pureHit1 = session.advancedMetaDataSearch("CustodianName", null, Input.metaDataCN, null);
		session.saveSearchInNewNode(savedSearch1, newNode1);
		session.saveSearchInRootNode(savedSearch1, newNode1, child1);

		session.getNewSearchButton().waitAndClick(20);
		int pureHit2 = session.advancedContentSearchWithSearchChanges(Input.searchString1, "Yes");
		session.saveSearchInNewNode(savedSearch2, newNode2);
		session.saveSearchInRootNode(savedSearch2, newNode2, child2);

		base.selectproject();
		int pureHit3 = session.conceptualSearch_new("field report", "mid");
		session.saveSearchInNewNode(savedSearch3, newNode3);
		session.saveSearchInRootNode(savedSearch3, newNode3, child3);
		System.out.println(pureHit3);

		base.selectproject();
		session.switchToWorkproduct();
		session.selectRedactioninWPS(Input.defaultRedactionTag);
		pureHit4 = session.saveAndReturnPureHitCount();
		driver.scrollPageToTop();
		session.saveSearchInNewNode(savedSearch4, newNode4);
		session.saveSearchInRootNode(savedSearch4, newNode4, child4);
		System.out.println(pureHit4);
		// share node to SG
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		saveSearch.shareSavedNodeToSG(Input.shareSearchDefaultSG, newNode1, savedSearch1);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		saveSearch.shareSavedNodeToSG(Input.shareSearchDefaultSG, newNode2, savedSearch2);
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		saveSearch.shareSavedNodeToSG(Input.shareSearchDefaultSG, newNode3, savedSearch3);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		saveSearch.shareSavedNodeToSG(Input.shareSearchDefaultSG, newNode4, savedSearch4);

		String dataSet1[][] = { { savedSearch1, String.valueOf(pureHit1) }, { savedSearch2, String.valueOf(pureHit2) },
				{ savedSearch3, String.valueOf(pureHit3) }, { savedSearch4, String.valueOf(pureHit4) } };
		String nodeList1[][] = { { newNode1 }, { newNode2 }, { newNode3 }, { newNode4 } };

		int m = 0;
		for (int i = 0; i < dataSet1.length; i++) {

			int j = 0;
			int l = 0;
			String searches = dataSet1[i][j];
			j++;
			pureHit0 = dataSet1[i][j];
			String Nodes = nodeList1[m][l];

			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.selectNode1(Nodes);
			// Schedule save search
			saveSearch.scheduleSavedSearchInMinute(searches, 2);

			// verify Document count
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.selectNode1(Nodes);
			saveSearch.savedSearch_SearchandSelect(searches, "Yes");

			String actualDocCount = saveSearch.getCountofDocs().getText();
			System.out.println(actualDocCount);
			softAssertion.assertEquals(pureHit0, actualDocCount);

			// Verify doc count in DocList Page.
			int DocCountInDocList = saveSearch.launchDocListViaSSandReturnDocCount();
			try {
				softAssertion.assertEquals(Integer.parseInt(actualDocCount), DocCountInDocList);
				base.passedStep("DocCounts Displayed in DocListPage is as Expected");
			} catch (Exception e) {
				base.failedStep("DocCounts Displayed in DocListPage is not as Expected");

			}
			m++;
		}

		String dataSet[][] = { { savedSearch1, String.valueOf(pureHit1) }, { savedSearch2, String.valueOf(pureHit2) },
				{ savedSearch3, String.valueOf(pureHit3) }, { savedSearch4, String.valueOf(pureHit4) } };
		String nodeList[][] = { { newNode1, child1 }, { newNode2, child2 }, { newNode3, child3 },
				{ newNode4, child4 } };

		int k = 0;

		for (int i = 0; i < dataSet.length; i++) {

			int j = 0;
			int l = 0;
			String searches = dataSet[i][j];
			j++;
			pureHit0 = dataSet[i][j];
			String Nodes = nodeList[k][l];
			l++;
			String childNode = nodeList[k][l];

			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.selectNode1(Nodes);
			saveSearch.selectNode1(childNode);
			// Schedule save search
			saveSearch.scheduleSavedSearchInMinute(searches, 2);

			// verify Document count
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.selectNode1(Nodes);
			saveSearch.selectNode1(childNode);
			saveSearch.savedSearch_SearchandSelect(searches, "Yes");

			String actualDocCount = saveSearch.getCountofDocs().getText();
			System.out.println(actualDocCount);
			softAssertion.assertEquals(pureHit0, actualDocCount);

			// Verify DocCount in DocList Page.
			int DocCountInDocList = saveSearch.launchDocListViaSSandReturnDocCount();
			try {
				softAssertion.assertEquals(Integer.parseInt(actualDocCount), DocCountInDocList);
				base.passedStep("DocCounts Displayed in DocListPage is as Expected");
			} catch (Exception e) {
				base.failedStep("DocCounts Displayed in DocListPage is not as Expected");

			}
			k++;

		}
		softAssertion.assertAll();
		login.logout();

	}

	/**
	 * @author jayanthi ganesan Description: (RPMXCON-57397)DAU impersonate down as
	 *         PAU, save search under <Shared with SecurityGroupName>directly,
	 *         schedule saved searches and verify documents
	 * 
	 */
	@Test(description = "RPMXCON-57397", enabled = true, dataProvider = "user_Roles", groups = { "regression" })
	public void verifyDASchedulingSearches_Impersonation(String role) throws Exception {
		String savedSearch1 = "Search1" + Utility.dynamicNameAppender();
		String savedSearch2 = "Search2" + Utility.dynamicNameAppender();
		String savedSearch3 = "Search3" + Utility.dynamicNameAppender();
		String savedSearch4 = "Search4" + Utility.dynamicNameAppender();
		SoftAssert softAssertion=new SoftAssert();
		SchedulesPage schedule = new SchedulesPage(driver);
		String pureHit0;
		int pureHit4 = 0;

		// Login as DA
		login.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Logged in as DA");
		if (role.equalsIgnoreCase("RMU")) {
			base.impersonateSAtoRMU();
		} else if (role.equalsIgnoreCase("REV")) {
			base.impersonateSAtoReviewer();
		} else if (role.equalsIgnoreCase("PA")) {
			base.impersonateDAtoPA();
		}
		base.stepInfo("Impersonated as : " + role);
		base.stepInfo("DAU impersonate down as PAU, save search under <Shared with SecurityGroupName>directly,"
				+ " schedule saved searches and verify documents");
		base.stepInfo("Test case Id: RPMXCON-57397");
		// create Node
		String newNode1 = saveSearch.createSearchGroupAndReturn(searchName, role, "Yes");
		String child1 = saveSearch.createNewSearchGrp(newNode1, 1);
		String newNode2 = saveSearch.createSearchGroupAndReturn(searchName, role, "Yes");
		String child2 = saveSearch.createNewSearchGrp(newNode2, 1);
		String newNode3 = saveSearch.createSearchGroupAndReturn(searchName, role, "Yes");
		String child3 = saveSearch.createNewSearchGrp(newNode3, 1);
		String newNode4 = saveSearch.createSearchGroupAndReturn(searchName, role, "Yes");
		String child4 = saveSearch.createNewSearchGrp(newNode4, 1);

		int pureHit1 = session.advancedMetaDataSearch("CustodianName", null, Input.metaDataCN, null);
		session.saveSearchInNewNode(savedSearch1, newNode1);
		session.saveSearchInRootNode(savedSearch1, newNode1, child1);
		System.out.println("pureHit1 :-"+pureHit1);

		session.getNewSearchButton().waitAndClick(20);
		int pureHit2 = session.advancedContentSearchWithSearchChanges(Input.searchString1, "Yes");
		session.saveSearchInNewNode(savedSearch2, newNode2);
		session.saveSearchInRootNode(savedSearch2, newNode2, child2);
		System.out.println("pureHit2 :-"+pureHit2);
		
		base.selectproject();
		int pureHit3 = session.conceptualSearch_new("field report", "mid");
		session.saveSearchInNewNode(savedSearch3, newNode3);
		session.saveSearchInRootNode(savedSearch3, newNode3, child3);
		System.out.println("pureHit3 :-"+pureHit3);

		base.selectproject();
		session.switchToWorkproduct();
		session.selectRedactioninWPS(Input.defaultRedactionTag); // Input.defaultRedactionTag // modified by samapth
		pureHit4 = session.saveAndReturnPureHitCount();
		driver.scrollPageToTop();
		session.saveSearchInNewNode(savedSearch4, newNode4);
		session.saveSearchInRootNode(savedSearch4, newNode4, child4);
		System.out.println("pureHit4 :-"+pureHit4);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		saveSearch.shareSavedNodeToSG("Shared with Default Security Group", newNode1, savedSearch1);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		saveSearch.shareSavedNodeToSG("Shared with Default Security Group", newNode2, savedSearch2);
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		saveSearch.shareSavedNodeToSG("Shared with Default Security Group", newNode3, savedSearch3);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		saveSearch.shareSavedNodeToSG("Shared with Default Security Group", newNode4, savedSearch4);

		String dataSet1[][] = { { savedSearch1, String.valueOf(pureHit1) }, { savedSearch2, String.valueOf(pureHit2) },
				{ savedSearch3, String.valueOf(pureHit3) }, { savedSearch4, String.valueOf(pureHit4) } };
		String nodeList1[][] = { { newNode1 }, { newNode2 }, { newNode3 }, { newNode4 } };

		int m = 0;
		for (int i = 0; i < dataSet1.length; i++) {

			int j = 0;
			int l = 0;
			String searches = dataSet1[i][j];
			j++;
			pureHit0 = dataSet1[i][j];
			System.out.println("pureHit0 :-"+pureHit0);
			String Nodes = nodeList1[m][l];

			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			System.out.println("Nodes:-"+Nodes);
			
			saveSearch.selectNode1(Nodes);
			saveSearch.getSavedSearchRefresh();
			// Schedule save search
			saveSearch.scheduleSavedSearchInMinute(searches, 2);

			// VerifyDocCount
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.selectNode1(Nodes);
			saveSearch.savedSearch_SearchandSelect(searches, "Yes");
			String actualDocCount = saveSearch.getCountofDocs().getText();
			System.out.println("actualDocCount :-"+actualDocCount);
			softAssertion.assertEquals(pureHit0, actualDocCount);

			// verify DocCount in DocListPage.
			int DocCountInDocList = saveSearch.launchDocListViaSSandReturnDocCount();
			System.out.println("DocCountInDocList :-"+DocCountInDocList);
			try {
				softAssertion.assertEquals(Integer.parseInt(actualDocCount), DocCountInDocList);
				base.passedStep("DocCounts Displayed in DocListPage is as Expected");
			} catch (Exception e) {
				base.failedStep("DocCounts Displayed in DocListPage is not as Expected");

			}
			m++;
		}

		String dataSet[][] = { { savedSearch1, String.valueOf(pureHit1) }, { savedSearch2, String.valueOf(pureHit2) },
				{ savedSearch3, String.valueOf(pureHit3) }, { savedSearch4, String.valueOf(pureHit4) } };
		String nodeList[][] = { { newNode1, child1 }, { newNode2, child2 }, { newNode3, child3 },
				{ newNode4, child4 } };

		int k = 0;

		for (int i = 0; i < dataSet.length; i++) {

			int j = 0;
			int l = 0;
			String searches = dataSet[i][j];
			j++;
			pureHit0 = dataSet[i][j];
			String Nodes = nodeList[k][l];
			l++;
			String childNode = nodeList[k][l];
			System.out.println("pureHit0_1 :-"+pureHit0);
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.selectNode1(Nodes);
			saveSearch.selectNode1(childNode);
			// Schedule save search
			saveSearch.scheduleSavedSearchInMinute(searches, 2);

			// VerifyDocCount
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.selectNode1(Nodes);
			saveSearch.selectNode1(childNode);
			saveSearch.savedSearch_SearchandSelect(searches, "Yes");

			String actualDocCount = saveSearch.getCountofDocs().getText();
			System.out.println("actualDocCount_1 :-"+actualDocCount);
			softAssertion.assertEquals(pureHit0, actualDocCount);

			// Verify Doc Count In DocListPage.
			int DocCountInDocList = saveSearch.launchDocListViaSSandReturnDocCount();
			System.out.println("DocCountInDocList_1 :-"+DocCountInDocList);
			try {
				
				softAssertion.assertEquals(Integer.parseInt(actualDocCount), DocCountInDocList);
				base.passedStep("DocCounts Displayed in DocListPage is as Expected");
			} catch (Exception e) {
				base.failedStep("DocCounts Displayed in DocListPage is not as Expected");

			}
			k++;

		}
		softAssertion.assertAll();
		login.logout();

	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws Exception
	 * @description Verify that when batch upload is done with Productions then
	 *              count should be displayed correctly on saved search after Search
	 *              completes
	 */
	@Test(description = "RPMXCON-49094", enabled = true, dataProvider = "SavedSearchwithoutReviewer", groups = {
			"regression" })
	public void validateProductionsCountsThroughBatchFile(String username, String password) throws Exception {
		base.stepInfo("Test case Id: RPMXCON-49094");
		base.stepInfo(
				"Verify that when batch upload is done with Productions then count should be displayed correctly on saved search after Search completes");
		login.loginToSightLine(username, password);
		base.selectproject();
		String foldernameProd = "FolderProd" + Utility.dynamicNameAppender();
		String TagnameProd = "TagProd" + Utility.dynamicNameAppender();
		String productionname_2 = "Patch" + Utility.dynamicNameAppender();
		String PrefixID_2 = "A_" + Utility.dynamicNameAppender();
		String SuffixID_2 = "_P" + Utility.dynamicNameAppender();
		String Tagnameprev = "Privileged";
		driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(foldernameProd, Input.securityGroup);
		base.selectproject();
		tagsAndFolderPage.CreateTagwithClassification(TagnameProd, Tagnameprev);
		base.selectproject();
		session.basicContentSearch("prabu");
		session.bulkFolderExisting(foldernameProd);
		ProductionPage page = new ProductionPage(driver);
		driver.getWebDriver().get(Input.url + "Production/Home");
		page.CreateNewProduction(productionname_2, PrefixID_2, SuffixID_2, foldernameProd, TagnameProd);
		base.stepInfo("Created a Production " + productionname_2);
		session.switchToWorkproduct();
		session.selectProductionstInASwp(productionname_2);
		int pureHits = session.serarchWP();
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		String batchFIleName = saveSearch.renameFile(Input.WPbatchFile);
		saveSearch.writeProductionDataIntoExcel(batchFIleName,
				"productions: [ name: [" + productionname_2 + "], produced: \"true\" ]");
		saveSearch.uploadWPBatchFile(batchFIleName);
		base.passedStep("Batch file uploaded successfully");
		saveSearch.verifyDocCountsAndStatus("WP productions", 2);
		driver.scrollPageToTop();
		saveSearch.getSavedSearchDeleteButton().waitAndClick(10);
		base.waitForElement(saveSearch.getDeleteOkBtn());
		saveSearch.getDeleteOkBtn().waitAndClick(10);
		base.VerifySuccessMessage("Save search tree node successfully deleted.");
		login.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws Exception
	 * @description SA/DA/PA User impersonate down as RMU/RU role, create
	 *              Searchgroups and Searches, and then runs the Concept Explorer
	 *              report against My saved searches in PAU role
	 */
	@Test(description = "RPMXCON-57403", enabled = true, dataProvider = "UserSaUDaUPaU", groups = { "regression" })
	public void validateConceptualReportAgainstMySavedSearch(String username, String password, String name, String role)
			throws Exception {
		base.stepInfo("Test case Id: RPMXCON-57403");
		base.stepInfo(
				"SA/DA/PA User impersonate down as RMU/RU role, create Searchgroups and Searches, and then runs the Concept Explorer report against My saved searches in PAU role");
		String searchName = "Search Name" + Utility.dynamicNameAppender();
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();
		String searchName2 = "Search Name" + UtilityLog.dynamicNameAppender();
		report = new ReportsPage(driver);

		// Login via User
		login.loginToSightLine(username, password);
		base.stepInfo("Loggedin As : " + role);
		// Pre-requesties
		base.rolesToImp(role, "PA");
		String newNodeFromPA = saveSearch.createSearchGroupAndReturn(searchName, "PA");
		int purehit = session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName2);
		// session.saveSearchInNode(searchName2);
		session.saveSearchInNewNode(searchName2, newNodeFromPA);

		// Impersonate As RMU via PA and create new searchgroup
		base.rolesToImp("PA", "RMU");
		String newNodeFromRMU = saveSearch.createSearchGroupAndReturn(searchName, "RMU");

		// Save Search
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName);
//		session.saveSearchInNode(searchName);
		session.saveSearchInNewNode(searchName, newNodeFromRMU);

		// impersonate As REV and create new searchgroup
		base.rolesToImp("RMU", "REV");
		String newNodeFromRev = saveSearch.createSearchGroupAndReturn(searchName, "REV");

		// Save search
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName1);
//		session.saveSearchInNode(searchName1);
		session.saveSearchInNewNode(searchName1, newNodeFromRev);
		driver.waitForPageToBeReady();

		// impersonate As Reviewer to RMU
		driver.waitForPageToBeReady();
		base.rolesToImp("REV", "RMU");

		// verify the saved searches are present in report page
		
//		report.navigateToReportsPage();
		 this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		report.VerificationOfConceptualReport(newNodeFromPA, newNodeFromRMU, newNodeFromRev, searchName, searchName1,
				searchName2);

		// verify the generation of report under pau role
		report.selectSearchAndVerifyPurehitsInConceptualReports(searchName2, purehit);

		login.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws Exception
	 * @description SA/DA/PA User impersonate down as RMU/RU role, create
	 *              Searchgroups and Searches, and then runs the STR report against
	 *              My saved searches in PAU role
	 */
	@Test(description = "RPMXCON-57404", enabled = true, dataProvider = "UserSaUDaUPaU", groups = { "regression" })
	public void validateSearchTermReportAgainstMySavedSearch(String username, String password, String name, String role)
			throws Exception {
		base.stepInfo("Test case Id: RPMXCON-57404");
		base.stepInfo(
				"SA/DA/PA User impersonate down as RMU/RU role, create Searchgroups and Searches, and then runs the STR report against My saved searches in PAU role");
		String searchName = "Search Name" + Utility.dynamicNameAppender();
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();
		String searchName2 = "Search Name" + UtilityLog.dynamicNameAppender();
		report = new ReportsPage(driver);

		// Login via User
		login.loginToSightLine(username, password);
		base.stepInfo("Loggedin As : " + role);
		// Pre-requesties
		base.rolesToImp(role, "PA");
		String newNodeFromPA = saveSearch.createSearchGroupAndReturn(searchName, "PA");
		int purehit = session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName2);
//		session.saveSearchInNode(searchName2);
		session.saveSearchInNewNode(searchName2, newNodeFromPA);

		// Impersonate As RMU via PA and create new searchgroup
		base.rolesToImp("PA", "RMU");
		String newNodeFromRMU = saveSearch.createSearchGroupAndReturn(searchName, "RMU");

		// Save Search
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName);
		// session.saveSearchInNode(searchName);
		session.saveSearchInNewNode(searchName, newNodeFromRMU);

		// impersonate As REV and create new searchgroup
		base.rolesToImp("RMU", "REV");
		String newNodeFromRev = saveSearch.createSearchGroupAndReturn(searchName, "REV");

		// Save search
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName1);
		// session.saveSearchInNode(searchName1);
		session.saveSearchInNewNode(searchName1, newNodeFromRev);
		driver.waitForPageToBeReady();

		// impersonate As Reviewer to RMU
		driver.waitForPageToBeReady();
		base.rolesToImp("REV", "RMU");

		// verify the saved searches are present in report page
		report.VerificationOfSTReport(newNodeFromPA, newNodeFromRMU, newNodeFromRev, searchName, searchName1,
				searchName2);

		// verify the generation of report under pau role
		report.verifyDocCountsInSTReport(searchName2, purehit);

		login.logout();
	}

	/**
	 * @author jayanthi ganesan Description:To verify RMU/Reviewer is able to filter
	 *         saved search based on their status.
	 */
	@Test(description = "RPMXCON-49959", dataProvider = "SavedSearchwithRMUandREV", groups = {
			"regression" }, enabled = true)
	public void VerifyStatus(String username, String password, String fullName) throws Exception {
		base.stepInfo("Test case Id: RPMXCON-49959");
		base.stepInfo("To verify RMU/Reviewer is able to filter saved search based on their status");
		// Login via User
		login.loginToSightLine(username, password);

		String savedSearch1 = "SavedSearch" + Utility.dynamicNameAppender();
		String savedSearch = "SavedSearch" + Utility.dynamicNameAppender();

		base.stepInfo(
				"Selecting searches under My SavedSearches  folder and applying status filter to verify the status");

		session.basicContentSearch(Input.searchString1);
		session.saveSearch(savedSearch);
		base.selectproject();

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		// verifying the status column in saved search page
		saveSearch.CheckStatus("COMPLETED");

		// Creating search group and saving search under the newly created group.
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		String newNode1 = saveSearch.createSearchGroupAndReturn(searchName, "PA", "Yes");

		session.advancedMetaDataSearch("CustodianName", null, Input.metaDataCN, null);
		session.saveSearchInNewNode(savedSearch1, newNode1);
		base.stepInfo("Selecting searches under Search group under My savedSearch Tab and applying status filter");
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.selectRootGroupTab(Input.mySavedSearch);
		saveSearch.selectNode1(newNode1);
		// verifying the status column in saved search page
		saveSearch.CheckStatus("COMPLETED");
		login.logout();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.logBefore(testMethod.getName());
		// Open browser
		
		base = new BaseClass(driver);
		login = new LoginPage(driver);
		saveSearch = new SavedSearch(driver);
		session = new SessionSearch(driver);
		sg = new SecurityGroupsPage(driver);
		softAssertion = new SoftAssert();
		dcPage = new DocListPage(driver);
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		assign = new AssignmentsPage(driver);
		report = new ReportsPage(driver);
		miniDocListpage = new MiniDocListPage(driver);
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		Reporter.setCurrentTestResult(result);
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			try { // if any tc failed and dint logout!
				login.logoutWithoutAssert();
			} catch (Exception e) {
//						 TODO: handle exception
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

		System.out.println("Executed :");

	}

}
