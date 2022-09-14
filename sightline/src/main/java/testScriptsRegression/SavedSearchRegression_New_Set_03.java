package testScriptsRegression;

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
import pageFactory.DocListPage;
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

public class SavedSearchRegression_New_Set_03 {

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

	@DataProvider(name = "SavedSearchwithPAandRMU")
	public Object[][] SavedSearchwithPAandRMU() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password }, { Input.pa1userName, Input.pa1password } };
		return users;
	}

	@DataProvider(name = "UseraAndShareOprtions")
	public Object[][] UseraAndShareOprtions() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName, Input.shareSearchPA },
				{ Input.pa1userName, Input.pa1password, Input.pa1FullName, Input.shareSearchDefaultSG },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName, Input.shareSearchDefaultSG } };
		return users;
	}

	@DataProvider(name = "Execute")
	public Object[][] User() {
		Object[][] users = { { Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password },
				{ Input.rev1userName, Input.rev1password } };
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

	/**
	 * @author jeevitha
	 * @description : Verify that application does not display any warnings when a
	 *              user executes a search group under 'Shared with <Security
	 *              Group>' which contains only Advanced Search(es) in Draft state
	 *              on Saved Search Screen.(RPMXCON-49826)
	 * @param userName
	 * @param password
	 * @param fullName
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-49826",enabled = true, dataProvider = "SavedSearchwithRMUandREV", groups = { "regression" } )
	public void verifyAdvancesearchInDraftStateSharedUnderSecurityGroup(String userName, String password,
			String fullName) throws Exception {
		String searchName = "SearchName_" + Utility.dynamicNameAppender();
		String security_group = Input.shareSearchDefaultSG;
		String searchGroup = Input.mySavedSearch;

		base.stepInfo("Test case Id: RPMXCON-49826 - Saved Search Sprint 03");
		base.stepInfo(
				"Verify that application does not display any warnings when a user executes a search group under 'Shared with <Security Group>' which contains only Advanced Search(es) in Draft state on Saved Search Screen.");

		// Login as PA
		login.loginToSightLine(userName, password);
		base.stepInfo("Loggedin As : " + fullName);

		// create new searchgroup
		String newNode = saveSearch.createSearchGroupAndReturn(searchName, userName);
		System.out.println(newNode);

		// saving the search in node in Draft State
		driver.getWebDriver().get(Input.url + "Search/Searches");
		int pureHit2 = session.advancedContentSearchWithSearchChanges(Input.searchString1, "No");
		System.out.println(pureHit2);
		session.saveSearchInNewNode(searchName, newNode);

		// sharing the search group with default security group
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.selectNode1(newNode);
		saveSearch.shareSavedNodeWithDesiredGroup(newNode, security_group);

		// Executing the SavedSearch in Draft State in savedSearch page and verifying
		// whether Waring message appeared
		driver.Navigate().refresh();
		saveSearch.getSavedSearchGroupName(security_group).waitAndClick(10);
		saveSearch.selectNode1(newNode);
		saveSearch.savedSearchExecute_SearchGRoup(searchName, pureHit2);

		// deleting the searches
		saveSearch.deleteNode(security_group, newNode);
		saveSearch.deleteNode(searchGroup, newNode);

		login.logout();

	}

	/**
	 * @author Raghuram A Description : To verify, As a admin user login, when user
	 *         select more than one saved search (select a folder within "My Saved
	 *         Search") then only tag, folder, assign, release & delete will be
	 *         enable RPMXCON-47415
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47415",enabled = true, groups = { "regression" } )
	public void verifyTagAndFolderBtnViaAdmin() throws InterruptedException {
		String searchName = "Search" + Utility.dynamicNameAppender();

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);
		base.stepInfo("Tescase ID : RPMXCON-47415 Saved search");
		base.stepInfo(
				"To verify, As a admin user login, when user select more than one saved search (select a folder within \"My Saved Search\") then only tag, folder, assign, release & delete will be enable");

		// load the saved search page and click on create new search group
		String newNode = saveSearch.createSearchGroupAndReturn(searchName, "PA", "Yes");

		// will load the session search page and search
		session.basicContentSearch(Input.searchString2);

		// will save the query on created node under my saved search
		session.saveSearchInNodewithChildNode(searchName, newNode);

		// will move to saved search page and expand the my saved search section
		// Navigate to Saved Search and Search
		saveSearch.navigateToSSPage();
		saveSearch.selectNode1(newNode);

		saveSearch.savedSearch_SearchandSelect(searchName, "No");

		driver.waitForPageToBeReady();
		Element tagButtonStatus = saveSearch.getSavedSearchToBulkTag();
		saveSearch.checkButtonEnabled(tagButtonStatus, "Should be Enabled", "Tag Option");

		Element folderButtonStatus = saveSearch.getSavedSearchToBulkFolder();
		saveSearch.checkButtonEnabled(folderButtonStatus, "Should be Enabled", "Folder");

		Element fscheduleButtonStatus = saveSearch.getSavedSearch_ScheduleButton();
		saveSearch.checkButtonDisabled(fscheduleButtonStatus, "Should be Disabled", "Schedule Option");

		Element deleteuttonStatus = saveSearch.getSavedSearchDeleteButton();
		saveSearch.checkButtonEnabled(deleteuttonStatus, "Should be Enabled", "Delete Option");

		Element releaseButtonStatus = saveSearch.getReleaseIcon();
		saveSearch.checkButtonEnabled(releaseButtonStatus, "Should be Enabled", "Release Option");

		// Delete node
		saveSearch.deleteNode(Input.mySavedSearch, newNode);
		login.logout();
	}

	/**
	 * @author jeevitha
	 * @description :Verify that application does not display any warnings when a
	 *              user executes a search group under 'Shared with Project
	 *              Administrators' which contains only Basic Search(es) in Draft
	 *              state on Saved Search Screen.(RPMXCON-49824)
	 * @param userName
	 * @param password
	 * @param fullName
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-49824",enabled = true, groups = { "regression" } )
	public void verifyBasicSearchInDraftStateSharedUnderPA() throws Exception {

		String Search1 = "search" + Utility.dynamicNameAppender();
		String searchGroup = "My Saved Search";
		String sharedGroup = Input.shareSearchPA;

		base.stepInfo("Test case Id: RPMXCON-49824");
		base.stepInfo(
				"Verify that application does not display any warnings when a user executes a search group under 'Shared with Project Administrators' which contains only Basic Search(es) in Draft state on Saved Search Screen.");
		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// create new searchgroup
		String newNode = saveSearch.createSearchGroupAndReturn(Search1, Input.pa1FullName);
		System.out.println(newNode);

		// Draft Query
		driver.getWebDriver().get(Input.url + "Search/Searches");
		int purehit = session.basicContentSearchWithSaveChanges(Input.searchString1, "No", "First");
		System.out.println(purehit);
		session.saveSearchInNewNode(Search1, newNode);

		// sharing the search group with default security group
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.selectNode1(newNode);
		saveSearch.shareSavedNodeWithDesiredGroup(newNode, sharedGroup);

		// Executing the SavedSearch in Draft State in savedSearch page and verifying
		// whether Waring message appeared
		driver.Navigate().refresh();
		saveSearch.getSavedSearchGroupName(sharedGroup).waitAndClick(10);
		saveSearch.selectNode1(newNode);
		saveSearch.savedSearchExecute_SearchGRoup(Search1, purehit);

		// deleting the searches
		saveSearch.deleteNode(sharedGroup, newNode);
		saveSearch.deleteNode(searchGroup, newNode);

		login.logout();
	}

	/**
	 * @author jeevitha
	 * @description : TC11_Verify that application does not display any warnings
	 *              when a user executes a search group under \"Shared with Project
	 *              Administrators\" which contains only Advanced Search(es) in
	 *              Draft state on Saved Search Screen.[RPMXCON-49823]
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-49833",enabled = true, groups = { "regression" } )
	public void verifyAdvanceSearchInDraftStateSharedUnderPA() throws Exception {

		String Search1 = "search" + Utility.dynamicNameAppender();
		String searchGroup = Input.mySavedSearch;
		String sharedGroup = Input.shareSearchPA;

		base.stepInfo("Test case Id: RPMXCON-49833  Saved Search");
		base.stepInfo(
				"\" TC01_Verify that application does not display any warnings when a user execute search group which contains only Basic Search(es) in Draft state on Saved Search Screen.\"");
		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// create new searchgroup
		String newNode = saveSearch.createSearchGroupAndReturn(Search1, Input.pa1FullName);
		System.out.println(newNode);

		// Draft Query
		driver.getWebDriver().get(Input.url + "Search/Searches");
		int purehit = session.advancedContentSearchWithSearchChanges(Input.searchString2, "No");
		session.saveSearchInNewNode(Search1, newNode);

		// sharing the search group with Shared with PA
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.selectNode1(newNode);
		saveSearch.shareSavedNodeWithDesiredGroup(newNode, sharedGroup);

		driver.Navigate().refresh();
		saveSearch.getSavedSearchGroupName(sharedGroup).waitAndClick(10);
		saveSearch.selectNode1(newNode);
		base.waitForElement(saveSearch.getSavedSearchStatus(Search1));
		String searchStatus = saveSearch.getSavedSearchStatus(Search1).getText();
		softAssertion.assertEquals(searchStatus, "DRAFT");
		saveSearch.savedSearchExecute_SearchGRoup(Search1, purehit);

		// deleting the searches
		saveSearch.deleteNode(sharedGroup, newNode);
		saveSearch.deleteNode(searchGroup, newNode);

		login.logout();
	}

	/**
	 * @author jeevitha
	 * @description : Reviewer User - Verify that User can Share Query the
	 *              respective Security Group on Saved SEarch Screen.[RPMXCON-48140]
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-48140",enabled = true, groups = { "regression" } )
	public void verifySharedSearchGroupUnderRespectiveSecurityGroup() throws Exception {
		String othSG = "Security Group_" + UtilityLog.dynamicNameAppender();
		String defSG = Input.securityGroup;
		String searchGroup = Input.mySavedSearch;

		SecurityGroupsPage sg = new SecurityGroupsPage(driver);
		UserManagement userManagemet = new UserManagement(driver);

		base.stepInfo("Test case Id: RPMXCON-48140   Saved Search");
		base.stepInfo(
				"Reviewer User - Verify that User can Share Query the respective Security Group on Saved SEarch Screen.");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Create Security Group
		driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		sg.AddSecurityGroup(othSG);
		base.stepInfo(othSG + " : created");

		// Assign access to SG
		userManagemet.assignAccessToSecurityGroups(othSG, Input.rev1userName);

		login.logout();

		// login as RMU
		login.loginToSightLine(Input.rev1userName, Input.rev1password);
		base.stepInfo("Loggedin As : " + Input.rev1userName);

//		String node1 = saveSearch.createASearchGroupandReturnName(searchGroup);
		String node1 = saveSearch.createSearchGroupAndReturn("Input.mysavedsearch", "rev", "No");

		// (Prerequisite)
		// share search group with Default Security group
		driver.Navigate().refresh();
		saveSearch.selectNode1(node1);
		saveSearch.shareSavedNodeWithDesiredGroup(node1, defSG);

		// share search group with other Security group
		base.selectsecuritygroup(othSG);
//		String node2 = saveSearch.createASearchGroupandReturnName(searchGroup);
		String node2 = saveSearch.createSearchGroupAndReturn("Input.mysavedsearch", "rev", "No");

		driver.Navigate().refresh();
		saveSearch.selectNode1(node2);
		saveSearch.shareSavedNodeWithDesiredGroup(node2, othSG);

		// creste searchGroup share with default SG
		base.selectsecuritygroup(defSG);

//		String newNode = saveSearch.createASearchGroupandReturnName(searchGroup);
		String newNode = saveSearch.createSearchGroupAndReturn("Input.mysavedsearch", "rev", "No");

		driver.Navigate().refresh();
		saveSearch.selectNode1(node1);
		saveSearch.shareSavedNodeWithDesiredGroup(newNode, defSG);

		// verify whether shared search group present in Other Security group
		base.selectsecuritygroup(othSG);
		saveSearch.verifyNodePresentInSG(othSG, newNode);

		// verify whether shared search group present in Default Security group
		base.selectsecuritygroup(defSG);
		saveSearch.verifyNodePresentInSG(defSG, newNode);

		// Deleting the Search Group
		saveSearch.deleteNode(searchGroup, node1);
		saveSearch.deleteNode(searchGroup, newNode);
		saveSearch.deleteNode(defSG, node1);
		saveSearch.deleteNode(defSG, newNode);

		login.logout();

		// Deleting the SG
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);
		sg.deleteSecurityGroups(othSG);
		login.logout();

	}

	/**
	 * @author jeevitha
	 * @description : Reviewer User - Verify that executed search groups appears
	 *              under the respective Security Group on Saved Search Screen.
	 *              [RPMXCON-48138]
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-48138",enabled = true, groups = { "regression" } )
	public void verifyExecutedSearchGroupUnderRespectiveSecurityGroupAsReviewer() throws Exception {
		String othSG = "Security Group_" + UtilityLog.dynamicNameAppender();
		String searchName = "search" + UtilityLog.dynamicNameAppender();

		String defSG = Input.securityGroup;
		String searchGroup = Input.mySavedSearch;

		SecurityGroupsPage sg = new SecurityGroupsPage(driver);
		UserManagement userManagemet = new UserManagement(driver);

		base.stepInfo("Test case Id: RPMXCON-48138 - Saved Search");
		base.stepInfo(
				"Reviewer User - Verify that executed search groups appears under the respective Security Group on Saved Search Screen.");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Create Security Group
		driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		sg.AddSecurityGroup(othSG);
		base.stepInfo(othSG + " : created");

		// Assign access to SG

		userManagemet.assignAccessToSecurityGroups(othSG, Input.rev1userName);

		login.logout();

		// login as RMU
		login.loginToSightLine(Input.rev1userName, Input.rev1password);
		base.stepInfo("Loggedin As : " + Input.rev1userName);

		String node1 = saveSearch.createASearchGroupandReturnName(searchGroup);

		int purehit = session.basicContentSearch(Input.searchString1);
		session.saveSearchInNewNode(searchName, node1);

		// (Prerequisite)
		// share search group with Default Security group
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.selectNode1(node1);
		saveSearch.shareSavedNodeWithDesiredGroup(node1, defSG);

		// share search group with other Security group
		base.selectsecuritygroup(othSG);
		String node2 = saveSearch.createASearchGroupandReturnName(searchGroup);
		driver.Navigate().refresh();
		saveSearch.selectNode1(node2);
		saveSearch.shareSavedNodeWithDesiredGroup(node2, othSG);

		// creste searchGroup share with default SG
		base.selectsecuritygroup(defSG);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.selectNode1(node1);
		saveSearch.savedSearchExecute_SearchGRoup(searchName, purehit);

		// verify whether shared search group present in Other Security group
		base.selectsecuritygroup(othSG);
		saveSearch.verifyNodePresentInSG(othSG, node1);

		// verify whether shared search group present in Default Security group
		base.selectsecuritygroup(defSG);
		saveSearch.verifyNodePresentInSG(defSG, node1);

		// Deleting the Search Group
		saveSearch.deleteNode(searchGroup, node1);
		saveSearch.deleteNode(defSG, node1);

		login.logout();

		// Deleting the SG
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);
		sg.deleteSecurityGroups(othSG);
		login.logout();
	}

	/**
	 * @author jeevitha
	 * @description : Reviewer User - Verify that User can Exports Search Group
	 *              under the respective Security Group on Saved Search Screen.
	 *              [RPMXCON-48141]
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-48141",enabled = true, groups = { "regression" } )
	public void verifyExportedSearchGroupUnderRespectiveSecurityGroup() throws Exception {
		String othSG = "Security Group_" + UtilityLog.dynamicNameAppender();
		String searchName = "search" + UtilityLog.dynamicNameAppender();

		String defSG = Input.securityGroup;
		String searchGroup = Input.mySavedSearch;
		String StyletoChoose = "CSV";
		String fieldTypeToChoose = "[,] 044";

		SecurityGroupsPage sg = new SecurityGroupsPage(driver);
		UserManagement userManagemet = new UserManagement(driver);

		base.stepInfo("Test case Id: RPMXCON-48141 - Saved search");
		base.stepInfo(
				"Reviewer User - Verify that User can Exports Search Group Under The Respective Security Group on Saved Search Screen.");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Create Security Group
		driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		sg.AddSecurityGroup(othSG);
		base.stepInfo(othSG + " : created");

		// Assign access to SG
		userManagemet.assignAccessToSecurityGroups(othSG, Input.rev1userName);

		login.logout();

		// login as RMU
		login.loginToSightLine(Input.rev1userName, Input.rev1password);
		base.stepInfo("Loggedin As : " + Input.rev1userName);

//		String node1 = saveSearch.createASearchGroupandReturnName(searchGroup);
		String node1 = saveSearch.createSearchGroupAndReturn(searchGroup, "REV", Input.yesButton);

		int purehit = session.basicContentSearch(Input.searchString1);
		session.saveSearchInNewNode(searchName, node1);

		// (Prerequisite)
		// share search group with Default Security group
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.selectNode1(node1);
		saveSearch.shareSavedNodeWithDesiredGroup(node1, defSG);

		// share search group with other Security group
		base.selectsecuritygroup(othSG);

//		String node2 = saveSearch.createASearchGroupandReturnName(searchGroup);
		String node2 = saveSearch.createSearchGroupAndReturn(searchGroup, "REV", Input.yesButton);

		driver.Navigate().refresh();
		saveSearch.selectNode1(node2);
		saveSearch.shareSavedNodeWithDesiredGroup(node2, othSG);

		// creste searchGroup share with default SG
		base.selectsecuritygroup(defSG);
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.selectNode1(node1);
		saveSearch.verifyExportpopup(StyletoChoose, fieldTypeToChoose);

		// verify whether shared search group present in Other Security group
		base.selectsecuritygroup(othSG);
		saveSearch.verifyNodePresentInSG(othSG, node1);

		// verify whether shared search group present in Default Security group
		base.selectsecuritygroup(defSG);
		saveSearch.verifyNodePresentInSG(defSG, node1);

		// Deleting the Search Group
		saveSearch.deleteNode(searchGroup, node1);
		saveSearch.deleteNode(defSG, node1);

		login.logout();

		// Deleting the SG
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);
		sg.deleteSecurityGroups(othSG);
		login.logout();
	}

	/**
	 * @author jeevitha
	 * @description : Reviewer User - Verify that deleted Search Group does not
	 *              appear under the respective Security Group on Saved Search
	 *              Screen. [RPMXCON-48137]
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-48137",enabled = true, groups = { "regression" } )
	public void verifyDeletedSearchGroupUnderRespectiveSecurityGroup() throws Exception {
		String othSG = "Security Group_" + UtilityLog.dynamicNameAppender();
		String searchName = "search" + UtilityLog.dynamicNameAppender();

		String defSG = Input.securityGroup;
		String searchGroup = Input.mySavedSearch;

		SecurityGroupsPage sg = new SecurityGroupsPage(driver);
		UserManagement userManagemet = new UserManagement(driver);

		base.stepInfo("Test case Id: RPMXCON-48137  -  Saved Search");
		base.stepInfo(
				"Reviewer User - Verify that deleted Search Group does not appear under the respective Security Group on Saved Search Screen.");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Create Security Group
		driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		sg.AddSecurityGroup(othSG);
		base.stepInfo(othSG + " : created");

		// Assign access to SG
		userManagemet.assignAccessToSecurityGroups(othSG, Input.rev1userName);

		login.logout();

		// login as RMU
		login.loginToSightLine(Input.rev1userName, Input.rev1password);
		base.stepInfo("Loggedin As : " + Input.rev1userName);

		String node1 = saveSearch.createASearchGroupandReturnName(searchGroup);
		int purehit = session.basicContentSearch(Input.searchString1);
		session.saveSearchInNewNode(searchName, node1);

		// (Prerequisite)
		// share search group with Default Security group
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.selectNode1(node1);
		saveSearch.shareSavedNodeWithDesiredGroup(node1, defSG);

		// share search group with other Security group
		base.selectsecuritygroup(othSG);
		String node2 = saveSearch.createASearchGroupandReturnName(searchGroup);
		driver.Navigate().refresh();
		saveSearch.selectNode1(node2);
		saveSearch.shareSavedNodeWithDesiredGroup(node2, othSG);

		// create searchGroup share with default SG
		base.selectsecuritygroup(defSG);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.selectNode1(node1);
		saveSearch.deleteNode(defSG, node1);

		// verify whether shared search group present in Other Security group
		base.selectsecuritygroup(othSG);
		saveSearch.verifyNodePresentInSG(othSG, node1);

		// verify whether shared search group present in Default Security group
		base.selectsecuritygroup(defSG);
		saveSearch.verifyNodePresentInSG(defSG, node1);

		// Deleting the Search Group
		saveSearch.deleteNode(searchGroup, node1);

		login.logout();

		// Deleting the SG
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);
		sg.deleteSecurityGroups(othSG);
		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @Description : Verify that application displays all documents that are in the
	 *              aggregate results set of "Default Security Group" and User
	 *              Performs Bulk Folder from Search groups[RPMXCON-49013]
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-49013",enabled = true, groups = { "regression" } )
	public void verifyingBulkFolderAggregateCountWithPureHitCount() throws Exception {
		String searchName1 = "Search Name" + Utility.dynamicNameAppender();
		String folderName = "Folder" + Utility.dynamicNameAppender();
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Test case Id: RPMXCON-49013");
		base.stepInfo(
				"Verify that application displays all documents that are in the aggregate results set of \"Default Security Group\" and User Performs Bulk Folder from Search groups");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// saving the Search
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName1);

		// share the search with Default Security group
		saveSearch.shareSavedSearchRMU(searchName1, Input.securityGroup);

		// Bulk Folder Functionality
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.getSavedSearchGroupName(Input.securityGroup).waitAndClick(10);
		saveSearch.getSavedSearchToBulkFolder().waitAndClick(10);
		String aggregateDocCount = session.BulkActions_Folder_returnCount(folderName);
		Integer aggregateDocCount_01 = Integer.parseInt(aggregateDocCount);
		int aggregateDocCount_02 = aggregateDocCount_01.intValue();

		// Advanced Search by selecting Created Folder
		base.selectproject();
		session.switchToWorkproduct();
		session.selectFolderInASwp(folderName);
		driver.scrollPageToTop();
		session.getAdvanceSearch_btn_Current().waitAndClick(10);
		driver.waitForPageToBeReady();

		// Comparing the aggregate Result count and pureHits Count
		session.verifyPureHitsCountWithProductionCount(aggregateDocCount_02);

		// deleting the searches
		saveSearch.deleteSearch(searchName1, Input.mySavedSearch, "Yes");
		saveSearch.deleteSearch(searchName1, Input.securityGroup, "Yes");

		// deleting the Folder
		driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(folderName);

		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @Description : TC11_Verify that application does not display any warnings
	 *              when a user executes a search group under \"Shared with Project
	 *              Administrators\" which contains only Advanced Search(es) in
	 *              Draft state on Saved Search Screen.[RPMXCON-49823]
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-49823",enabled = true, groups = { "regression" } )
	public void verifyAppDoesnotDisplayWarning() throws Exception {

		String Search1 = "search" + Utility.dynamicNameAppender();
		String searchGroup = Input.mySavedSearch;

		base.stepInfo("Test case Id: RPMXCON-49823  Saved Search");
		base.stepInfo(
				"TC11_Verify that application does not display any warnings when a user executes a search group under \"Shared with Project Administrators\" which contains only Advanced Search(es) in Draft state on Saved Search Screen.");
		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// create new searchgroup
		String newNode = saveSearch.createSearchGroupAndReturn(Search1, Input.pa1FullName);
		System.out.println(newNode);

		// Draft Query
		driver.getWebDriver().get(Input.url + "Search/Searches");
		int purehit = session.basicContentSearchWithSaveChanges(Input.searchString1, "No", "First");
		session.saveSearchInNewNode(Search1, newNode);

		// Execute Search group
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.selectNode1(newNode);
		base.waitForElement(saveSearch.getSavedSearchStatus(Search1));
		String searchStatus = saveSearch.getSavedSearchStatus(Search1).getText();
		softAssertion.assertEquals(searchStatus, "DRAFT");
		saveSearch.savedSearchExecute_SearchGRoup(Search1, purehit);

		base.waitForElement(saveSearch.getSavedSearchStatus(Search1));
		String searchStatusAfter = saveSearch.getSavedSearchStatus(Search1).getText();
		softAssertion.assertEquals(searchStatusAfter, "COMPLETED");

		// deleting the searches
		saveSearch.deleteNode(searchGroup, newNode);
		softAssertion.assertAll();
		login.logout();
	}

	/**
	 * @author sowndarya.velraj
	 * @description :TC12_Verify that application does not display any warnings when
	 *              a user executes a search group under "Shared with Project
	 *              Administrators' which contains Basic and Advanced Search(es) in
	 *              Draft state on Saved Search Screen.(RPMXCON-49822)
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-49822",enabled = true, groups = { "regression" } )
	public void verifyBasicSearchAndAdvancedSearchInDraftStateSharedUnderPA() throws Exception {

		String Search1 = "search" + Utility.dynamicNameAppender();
		String Search2 = "search2" + Utility.dynamicNameAppender();
		String searchGroup = "My Saved Search";
		String sharedGroup = "Shared With Project Administrator";

		base.stepInfo("Test case Id: RPMXCON-49822");
		base.stepInfo(
				"Verify that application does not display any warnings when a user executes a search group under 'Shared with Project Administrators' which contains only Basic Search(es) in Draft state on Saved Search Screen.");
		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// create new searchgroup
		String newNode = saveSearch.createSearchGroupAndReturn(Search1, Input.pa1FullName);
		System.out.println(newNode);

		// Basic search Query as Draft
		driver.getWebDriver().get(Input.url + "Search/Searches");
		int purehit = session.basicContentSearchWithSaveChanges(Input.searchString1, "No", "First");
		System.out.println(purehit);
		session.saveSearchInNewNode(Search1, newNode);

		driver.Navigate().refresh();

		base.waitForElement(session.getNewSearchButton());
		session.getNewSearchButton().waitAndClick(10);

		// Advanced Search Query as Draft

//		driver.getWebDriver().get(Input.url + "Search/Searches");
		int pureHit2 = session.advancedContentSearchWithSearchChanges(Input.searchString1, "No");
		System.out.println(pureHit2);
		session.saveSearchInNewNode(Search2, newNode);

		// sharing the search group with default security group
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.selectNode1(newNode);
		saveSearch.shareSavedNodeWithDesiredGroup(newNode, Input.shareSearchPA);

		// Executing the SavedSearch in Draft State in savedSearch page and verifying
		// whether Waring message appeared
		driver.Navigate().refresh();
		saveSearch.getSavedSearchGroupName(sharedGroup).waitAndClick(10);
		saveSearch.selectNode1(newNode);
		saveSearch.savedSearchExecute_SearchGRoup(Search1, purehit);

		// deleting the searches
		saveSearch.deleteNode(sharedGroup, newNode);
		saveSearch.deleteNode(searchGroup, newNode);
		base.passedStep(
				"Verify that application does not display any warnings when a user executes a search group  which contains Basic and Advanced Search(es) in  Draft state on Saved Search Screen");

		login.logout();
	}

	/**
	 * @author sowndarya.velraj
	 * @description :Validate saving edited search (executed or draft) under My
	 *              Searches or Shared Searches for PAU(RPMXCON-49876)
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-49876",enabled = true, groups = { "regression" } )
	public void verifyBasicSearchAndAdvancedSearchIngDraftStateSharedUnderPA() throws Exception {

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();
		String SGtoShare = Input.shareSearchDefaultSG;

		base.stepInfo("Test case Id: RPMXCON-49876 - Saved Search Sprint 08");

//		driver.Manage().window().fullscreen();
		// create new searchgroup
		String newNode = saveSearch.createASearchGroupandReturnName(searchName1);

		// add save search in node
		int purehit = session.basicContentSearch(Input.searchString1);

//		session.saveSearchInNode(searchName1);
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

		session.modifySearchTextArea(1, Input.searchString1, Input.searchString2, "Save");
		saveSearch.validatingSaves(SGtoShare, newNode, searchName1);

		login.logout();
	}

	/**
	 * @author sowndarya.velraj
	 * @description :Verify that application displays all documents that are in the
	 *              aggregate results set of "Public Searches" and User Navigate
	 *              Search groups to DocView.(RPMXCON-49010)
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-49010",enabled = true, groups = { "regression" } )
	public void verifyDocumentsDisplayAndNavigatingToDocview() throws Exception {

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();
		int latencyCheckTime = 5;
		String passMessage = "Application not hang or shows latency more than " + latencyCheckTime + " seconds.";
		String failureMsg = "Continues Loading more than " + latencyCheckTime + " seconds.";

		base.stepInfo("Test case Id: RPMXCON-49010 - Saved Search Sprint 08");

//		driver.Manage().window().fullscreen();

		// create new searchgroup
		String newNode = saveSearch.createASearchGroupandReturnName(searchName1);

		// add save search in node
		int purehit = session.basicContentSearch(Input.searchString1);

//		session.saveSearchInNode(searchName1);
		session.saveSearchInNewNode(searchName1, newNode);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.selectNode1(newNode);

		// Search and SaveSearch
		saveSearch.shareSavedNodePA(Input.securityGroup, newNode, true, true, searchName1);

		// Launch DocVia via Saved Search
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		saveSearch.getSavedSearchGroupName(Input.securityGroup);
		base.stepInfo("Root node : " + newNode);

		saveSearch.selectNode1(newNode);
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
				"Shows all documents that are in the aggregate results set of all child search groups and searches",
				"Count Mismatches with the Documents");
		driver.waitForPageToBeReady();
		login.logout();
	}

	/**
	 * @author Raghuram A Date: 12/16/21 Modified date:N/A Modified by: N/A
	 *         Description : Verify that application displays all documents that are
	 *         in the aggregate results set of "Shared With Project
	 *         Administrator/Shared With Default Security Group" and User performs
	 *         Export with Search groups (RPMXCON-49007)
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49007",enabled = true, dataProvider = "UseraAndShareOprtions", groups = { "regression" } )
	public void aggregateResultsSetOfSharedGroups(String userName, String password, String fullName, String shareTo)
			throws InterruptedException {

		ReportsPage report;
		report = new ReportsPage(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		final int Bgcount;
		int purehit;

		String SearchName = "Search" + Utility.dynamicNameAppender();
		String folderName = "Folder" + Utility.dynamicNameAppender();
		String shareToGroup = shareTo;

		base.stepInfo("Test case Id: RPMXCON-49007 - Saved Search Sprint 08");
		base.stepInfo(
				"Verify that application displays all documents that are in the aggregate results set of \"Shared With Project Administrator/Shared With Default Security Group\" and User performs Export with Search groups");
		// Login as PA
		login.loginToSightLine(userName, password);
		base.stepInfo(fullName);

		// create new search group and save search
		String new_node = saveSearch.createASearchGroupandReturnName(SearchName);

		// create folder and add save search in folder
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.CreateFolder(folderName, Input.securityGroup);

		// Search and Save
		purehit = session.basicContentSearch(Input.searchString1);
//		session.saveSearchInNode(SearchName);
		session.saveSearchInNewNode(SearchName, new_node);
		driver.waitForPageToBeReady();
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		base.stepInfo("Landed on SavedSearchPage");

		// Share under available shared groups
		saveSearch.selectRootGroupTab(Input.mySavedSearch);
		saveSearch.rootGroupExpansion();
		String searchID1 = saveSearch.shareSavedNodePA(shareToGroup, new_node, true, true, SearchName);

		// Make sure shared Node reflected in the SG & Select the shared Search
		saveSearch.navigateToSSPage();
		saveSearch.getSavedSearchGroupName(shareToGroup).waitAndClick(10);
		saveSearch.selectNode1(new_node);
		saveSearch.savedSearch_SearchandSelect(SearchName, "Yes"); // modified

		// Get initial notification count
		Bgcount = base.initialBgCount();

		// Verify Export
		saveSearch.getSavedSearchExportButton().Click();
		base.waitForElement(saveSearch.getExportPopup());

		report.customDataReportMethodExport(folderName, false);
		driver.waitForPageToBeReady();

		// Download report
		report.downLoadReport(Bgcount);
		driver.waitForPageToBeReady();
		base.stepInfo("File Downloaded");

		base.stepInfo(
				"Verifying Exported file lists all the documents from the search group with  selected metadata and work product.");
		int countToCompare = saveSearch.fileVerificationSpecificMethod();
		base.stepInfo("Document count from the export : " + countToCompare);

		base.digitCompareEquals(countToCompare, purehit,
				"Exported file lists all the documents from the search group with selected metadata and work product.",
				"Purehit and File count doesn't match");

		// Delete Node
		saveSearch.deleteNode(Input.mySavedSearch, new_node, true, true);

		login.logout();

	}

	/**
	 * @author Raghuram A Date: 12/16/21 Modified date:N/A Modified by: N/A
	 *         Description : Validate sharing search group to Security Group from
	 *         root group-RPMXCON-49863 Sprint 08
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description ="RPMXCON-49863",enabled = true, groups = { "regression" } )
	public void validatingRootlevelhierarchySharingSG() throws InterruptedException {

		int noOfNodesToCreate = 6;
		int selectIndex = 0;
		String SGtoShare = Input.shareSearchDefaultSG;
		String node;
		Boolean inputValue = true;
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		HashMap<String, String> searchGroupSearchpIDpair = new HashMap<>();
		String[] rolesToVerify = { "RMU-2", "PA-2" };
		String passMessage = "Validate sharing search group to Security Group from root group";

		base.stepInfo("Test case Id: RPMXCON-49863 - Saved Search Sprint 06");
		base.stepInfo("Validate RMU sharing search group to Security Group from root group");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Navigate to Saved Search
		saveSearch.navigateToSSPage();

		// Multiple Node Creation
		newNodeList = saveSearch.createSGAndReturn("PA", "No", noOfNodesToCreate);
		System.out.println("Node creation is done followed by adding searches to the created nodes");
		base.stepInfo("Node creation is done followed by adding searches to the created nodes");

		driver.getWebDriver().get(Input.url + "Search/Searches");

		// Adding searches to the created nodes
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);
		saveSearch.sortedMapList(nodeSearchpair);

		base.stepInfo("-------Pre-requesties completed--------");

		// Navigate to Saved Search
		saveSearch.navigateToSSPage();

		// Search ID collection
		base.waitForElement(saveSearch.getSavedSearchNewGroupExpand());
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(10);

		searchGroupSearchpIDpair = saveSearch.collectionOfSearchIdsFromNodeCollections(newNodeList, nodeSearchpair,
				searchGroupSearchpIDpair);

		// Navigate to Saved Search
		saveSearch.navigateToSSPage();

		node = saveSearch.childNodeSelectionToShare(selectIndex, newNodeList);
		System.out.println("Final : " + node);

		saveSearch.shareSavedNodePA(SGtoShare, node, true, true, nodeSearchpair.get(node));// false -> true

		login.logout();
		// Verify with Different Users
		saveSearch.verifyListofsharedNodesandSearchesAcrossUsers(SGtoShare, newNodeList, selectIndex, nodeSearchpair,
				searchGroupSearchpIDpair, rolesToVerify, passMessage);

		// Login as RMU
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Delete Node
		base.stepInfo("Initiated Delete Searches");
		saveSearch.deleteNode(Input.mySavedSearch, newNodeList.get(0));

		// Delete Shared SG Nodes
		base.stepInfo("Initiated Delete Shared SG Searches");
		saveSearch.deleteNode(Input.shareSearchDefaultSG, newNodeList.get(0));

		login.logout();

	}

	/**
	 * @author Raghuram A Date: 12/16/21 Modified date:N/A Modified by: Description
	 *         : Validate sharing search group to a Security Group from any of the
	 *         node in the group hierarchy -RPMXCON-49864 Sprint 08
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description ="RPMXCON-49864",enabled = true, groups = { "regression" } )
	public void hierarchyShareFromAnyLevel() throws InterruptedException, ParseException {
		int noOfNodesToCreate = 6;
		int selectIndex = 2;
		String SGtoShare = Input.shareSearchDefaultSG;
		String node;
		Boolean inputValue = true;
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		HashMap<String, String> searchGroupSearchpIDpair = new HashMap<>();
		String[] rolesToVerify = { "RMU-2", "PA-2" };
		String passMessage = "Only the selected search group and their child groups are with Searches, none of the searches in parent hierarchical group is shared";

		base.stepInfo("Test case Id: RPMXCON-49864 - Saved Search Sprint 08");
		base.stepInfo("Validate sharing search group to a Security Group from any of the node in the group hierarchy");

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

		base.stepInfo("-------Pre-requesties completed--------");

		// Search ID collection
		saveSearch.navigateToSSPage();
		base.waitForElement(saveSearch.getSavedSearchNewGroupExpand());
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);

		searchGroupSearchpIDpair = saveSearch.collectionOfSearchIdsFromNodeCollections(newNodeList, nodeSearchpair,
				searchGroupSearchpIDpair);

		saveSearch.navigateToSSPage();
		node = saveSearch.childNodeSelectionToShare(selectIndex, newNodeList);
		System.out.println("Final : " + node);
		saveSearch.shareSavedNodePA(SGtoShare, node, false, true, nodeSearchpair.get(node));

		login.logout();

		// Verify with Different Users
		saveSearch.verifyListofsharedNodesandSearchesAcrossUsers(SGtoShare, newNodeList, selectIndex, nodeSearchpair,
				searchGroupSearchpIDpair, rolesToVerify, passMessage);

		// Login as RMU
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Delete Node
		base.stepInfo("Initiated Delete Searches");
		saveSearch.deleteNode(Input.mySavedSearch, newNodeList.get(0));

		// Delete Shared SG Nodes
		base.stepInfo("Initiated Delete Shared SG Searches");
		saveSearch.deleteNode(Input.shareSearchDefaultSG, newNodeList.get(0));

		login.logout();

	}

	/**
	 * @author Brundha RPMXCON-49012
	 * @Description : Verify that application displays all documents that are in the
	 *              aggregate results set of "Public Shared" and User Performs Bulk
	 *              Tag from Search groups]
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-49012",enabled = true, groups = { "regression" } )
	public void verifyingBulkTagAggregateCountWithPureHitCount() throws Exception {
		String searchName1 = "Search Name" + Utility.dynamicNameAppender();
		String Tag = "Tag" + Utility.dynamicNameAppender();
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Test case Id: RPMXCON-49012");
		base.stepInfo(
				"Verify that application displays all documents that are in the aggregate results set of \"Default Security Group\" and User Performs Bulk Folder from Search groups");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// saving the Search
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName1);

		// share the search with Default Security group
		saveSearch.shareSavedSearchRMU(searchName1, Input.securityGroup);

		// Bulk Folder Functionality
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.getSavedSearchGroupName(Input.securityGroup).waitAndClick(10);
		saveSearch.getSavedSearchToBulkTag().waitAndClick(10);

		driver.getWebDriver().get(Input.url + "Search/Searches");

		int aggregateDocCount = session.verifyBulkTag(Tag);
		Integer aggregateDocCount_01 = aggregateDocCount;
		int aggregateDocCount_02 = aggregateDocCount_01.intValue();

		// Advanced Search by selecting Created Folder
		base.selectproject();
		session.switchToWorkproduct();
		session.selectTagInASwp(Tag);
		driver.scrollPageToTop();
		session.getAdvanceSearch_btn_Current().waitAndClick(10);
		driver.waitForPageToBeReady();

		// Comparing the aggregate Result count and pureHits Count
		session.verifyPureHitsCountWithProductionCount(aggregateDocCount_02);

		// deleting the searches
		saveSearch.deleteSearch(searchName1, Input.mySavedSearch, "Yes");
		saveSearch.deleteSearch(searchName1, Input.securityGroup, "Yes");

		login.logout();
	}

	/**
	 * @author Brundha
	 * @Description :[testcase id-RPMXCON-49953]To Verify, When user select
	 *              execution on any of the folder under "My Saved Search", it will
	 *              execute the individual search query for maximum 30 sec after
	 *              that it will move to poison queue
	 * @param username
	 * @param password
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49953",enabled = true, dataProvider = "Execute", groups = { "regression" } )
	public void executingTheSavedSearchFolder(String username, String password) throws InterruptedException {

		login.loginToSightLine(username, password);
		base.stepInfo("Test case Id: RPMXCON-49953 Batch Redaction Sprint 08");
		base.stepInfo(" Verify  When user select execution on any of the folder under My Saved Search");

		String searchName = "Search Name" + Utility.dynamicNameAppender();

		SavedSearch savesearch = new SavedSearch(driver);
		String newNode = savesearch.createSearchGroupAndReturn(searchName, "Users", "Yes");

		SessionSearch search = new SessionSearch(driver);
		search.basicContentSearch(Input.testData1);
		search.saveSearchInNodewithChildNode(searchName, newNode);

		SavedSearch savedsearch = new SavedSearch(driver);
		savedsearch.verifyExecutionOfFolder(searchName);

		login.logout();

	}

	/**
	 * @author brundha
	 * @Description :[testcase id-RPMXCON-57389]PAU re-executing searches
	 *              shared(with audio and non-audio docs) to Security group and
	 *              verify documents
	 * 
	 * @param username
	 * @param password
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description ="RPMXCON-57389",enabled = true, dataProvider = "SavedSearchwithPAandRMU", groups = { "regression" } )
	public void selectDefaultSecurityGroup(String username, String password)
			throws InterruptedException, ParseException {
		base = new BaseClass(driver);
		login.loginToSightLine(username, password);
		base.stepInfo("Test case Id: RPMXCON-57389 Batch Redaction Sprint 08");
		base.stepInfo(
				"PAU re-executing searches shared(with audio and non-audio docs) to Security group and verify documents");
		int pureHit;

		String searchName = "Search Name" + Utility.dynamicNameAppender();
		String foldername = "folder" + Utility.dynamicNameAppender();

		SavedSearch savesearch = new SavedSearch(driver);
		String newNode = savesearch.createSearchGroupAndReturn(searchName, "username", "Yes");

		SessionSearch search = new SessionSearch(driver);
		pureHit = search.AudioAndNonAudioSearch(Input.audioSearchString1, Input.language);
		search.saveSearchInNodewithChildNode(searchName, newNode);
		SavedSearch savedsearches = new SavedSearch(driver);
		savedsearches.navigateToSavedSearchPage();
		savedsearches.shareDefaultSecurityGroup(Input.securityGroup, searchName);
		savedsearches.savedSearchExecute_Draft(searchName, pureHit);

		savedsearches.bulkFolder(foldername, pureHit);

		login.logout();
	}

	/**
	 * @Author
	 * @Description : Validate deleting searches/groups from the shared with
	 *              <Security Group Name> by any other PAU user [RPMXCON-49871]
	 */
	@Test(description ="RPMXCON-49871",enabled = true, groups = { "regression" } )
	public void validatingDeletedSavedsearchSearchesAndGroupsAsPau() throws Exception {

		String searchName = "Search Name" + Utility.dynamicNameAppender();
		String searchName1 = "Search Name" + Utility.dynamicNameAppender();
		int noOfNodesToCreate = 2;
		List<String> newNodeList = new ArrayList<>();

		base.stepInfo("Test case Id: RPMXCON-49871");
		base.stepInfo(
				"Validate deleting searches/groups from the shared with  <Security Group Name> by any other PAU user");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);
		// Landed on Saved Search
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		// Multiple Node Creation
		newNodeList = saveSearch.createSGAndReturn("PA", "No", noOfNodesToCreate);

		// Adding searches to the created nodes
		session.basicContentSearch(Input.searchString1);
		session.saveSearchInNewNode(searchName, newNodeList.get(0));
//		session.saveSearchInRootNode(searchName1, newNodeList.get(0), newNodeList.get(1));
		session.saveSearchInNodewithChildNode(searchName1, newNodeList.get(1));

		// //Share the node with Default security group
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		saveSearch.rootGroupExpansion();
		saveSearch.shareSavedNodeToSG(Input.shareSearchDefaultSG, newNodeList.get(0), searchName);

		// Deleting the searches within the group
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		saveSearch.getSavedSearchGroupName(Input.shareSearchDefaultSG).waitAndClick(10);
		saveSearch.selectNode1(newNodeList.get(0));
		saveSearch.savedSearch_SearchandSelect(searchName, "Yes");
		saveSearch.deleteFunctionality();

		// verify deleted search
		driver.Navigate().refresh();
		saveSearch.getSavedSearchGroupName(Input.shareSearchDefaultSG).waitAndClick(10);
		saveSearch.selectNode1(newNodeList.get(0));
		saveSearch.savedSearch_SearchandSelect(searchName, "No");

		// Deleting the Node
		saveSearch.deleteNode(Input.shareSearchDefaultSG, newNodeList.get(0));

		saveSearch.verifyNodePresentInSG(Input.shareSearchDefaultSG, newNodeList.get(0));
		saveSearch.verifyNodePresentInSG(Input.shareSearchDefaultSG, newNodeList.get(1));

		login.logout();

		// Login and verify as PA2
		login.loginToSightLine(Input.pa2userName, Input.pa2password);
		saveSearch.verifyNodePresentInSG(Input.shareSearchDefaultSG, newNodeList.get(0));
		login.logout();

		// Login and verify as RMU2
		login.loginToSightLine(Input.rmu2userName, Input.rmu2password);
		saveSearch.verifyNodePresentInSG(Input.shareSearchDefaultSG, newNodeList.get(0));
		login.logout();
	}

	/**
	 * @Author
	 * @Description : Validate modifying searches/groups from the shared with
	 *              <Security Group Name> by any other PAU user[RPMXCON-49870]
	 */
	@Test(description ="RPMXCON-49870",enabled = true, groups = { "regression" } )
	public void validatingRenamedSavedsearchSearchesAndGroupsAsPauAndRmu() throws Exception {

		String searchName1 = "Search Name" + Utility.dynamicNameAppender();
		String renamedSearchName1 = "Renamed Search Nmae" + Utility.dynamicNameAppender();
		base.stepInfo("Test case Id: RPMXCON-49870");
		base.stepInfo(
				"Validate modifying searches/groups from the shared with  <Security Group Name> by any other PAU user");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

//		String node1 = saveSearch.createASearchGroupandReturnName(searchName1);
		String node1 = saveSearch.createSearchGroupAndReturn(searchName1, "PA", Input.yesButton);

		// Adding search to the created node
		session.basicContentSearch(Input.searchString2);
		session.saveSearchInNewNode(searchName1, node1);

		// Share the node with default Security group
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		saveSearch.shareSavedNodeToSG(Input.shareSearchDefaultSG, node1, searchName1);

		// Rename Search group
		driver.Navigate().refresh();
		saveSearch.getSavedSearchGroupName(Input.shareSearchDefaultSG).Click();

		saveSearch.rootGroupExpansion();
//		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);

		String renamedNode = saveSearch.renameSearchGroup(node1);

		// renaming the Search
		driver.Navigate().refresh();
		saveSearch.getSavedSearchGroupName(Input.shareSearchDefaultSG).waitAndClick(10);
		saveSearch.selectNode1(renamedNode);
		saveSearch.verifyRenamedsavedSearch(searchName1, renamedSearchName1);

		login.logout();

		// login as PAU and Validate the Renamed search and Search Group
		login.loginToSightLine(Input.pa2userName, Input.pa2password);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		saveSearch.getSavedSearchGroupName(Input.shareSearchDefaultSG).waitAndClick(10);
		base.waitForElement(saveSearch.getSavedSearchNewGroupExpand());
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		saveSearch.verifyNodePresentInSG(Input.shareSearchDefaultSG, renamedNode);
		saveSearch.savedSearch_SearchandSelect(renamedSearchName1, "No");
		login.logout();

		// login as RMU and Validate the Renamed search and Search Group
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		saveSearch.getSavedSearchGroupName(Input.shareSearchDefaultSG).waitAndClick(10);
		base.waitForElement(saveSearch.getSavedSearchNewGroupExpand());
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		saveSearch.verifyNodePresentInSG(Input.shareSearchDefaultSG, renamedNode);
		saveSearch.savedSearch_SearchandSelect(renamedSearchName1, "No");

		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @Description : Verify that when batch upload is done then count should be
	 *              displayed correctly on saved search after Search
	 *              completes[RPMXCON-49615]
	 * @param username
	 * @param Password
	 * @param fullName
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-49615",enabled = true, dataProvider = "Execute", groups = { "regression" } )
	public void validateBatchUploadAndSearch(String username, String Password) throws Exception {
		String search = "Basic Content Search without Family Members Included";
		String file = saveSearch.renameFile(Input.WPbatchFile);

		// Login as PA
		login.loginToSightLine(username, Password);
		base.stepInfo("Test case Id: RPMXCON-49615");
		base.stepInfo(
				"Verify that when batch upload is done then count should be displayed correctly on saved search after Search completes");

		// Upload Batch File And Get count of docs
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");

		saveSearch.uploadWPBatchFile_New(file, Input.WPbatchFile);
		saveSearch.selectSavedSearch(search);
		base.waitForElement(saveSearch.getSavedSearchCount(search));
		String countOfDocs = saveSearch.getSavedSearchCount(search).getText();

		// Navigate to session search verify count of doc's with purehit count
		saveSearch.getSavedSearchEditButton().waitAndClick(10);
		int pureHit = session.modifyExistingQueryByOkandCancelBtn(null);
		softAssertion.assertEquals(Integer.parseInt(countOfDocs), pureHit);

		// Delete Uploaded File
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.getMySeraches().waitAndClick(10);
		base.waitForElement(saveSearch.getSelectUploadedFile(file));
		saveSearch.getSelectUploadedFile(file).waitAndClick(20);
		saveSearch.deleteFunctionality();
		softAssertion.assertAll();

		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : TC09_Verify that application does not display any warnings
	 *              when a user executes a search group under \"Shared with
	 *              <Security Group>\" which contains Basic and Advanced Search(es)
	 *              in Draft state on Saved Search Screen.[RPMXCON-49825]
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-49825",enabled = true, dataProvider = "SavedSearchwithRMUandREV", groups = { "regression" } ) /// Fixed
	public void verifyAppDoesnotDisplayWarning(String username, String password, String fullName) throws Exception {

		String Search1 = "search" + Utility.dynamicNameAppender();
		String Search2 = "search" + Utility.dynamicNameAppender();

		String searchGroup = Input.mySavedSearch;

		base.stepInfo("Test case Id: RPMXCON-49825  Saved Search");
		base.stepInfo(
				"TC09_Verify that application does not display any warnings when a user executes a search group under \"Shared with <Security Group>\" which contains Basic and Advanced Search(es) in Draft state on Saved Search Screen.");

		// Login as PA
		login.loginToSightLine(username, password);
		base.stepInfo("Loggedin As : " + fullName);

		// create new searchgroup
		String newNode = saveSearch.createSearchGroupAndReturn(Search1, fullName);
		System.out.println(newNode);

		// BAsic search Draft Query
		driver.getWebDriver().get(Input.url + "Search/Searches");
		session.basicContentSearchWithSaveChanges(Input.searchString1, "No", "First");
		session.saveSearchInNewNode(Search1, newNode);
		base.selectproject();

		// Advanced Search Query as Draft
		driver.getWebDriver().get(Input.url + "Search/Searches");
		session.advancedContentSearchWithSearchChanges(Input.searchString2, "No");
		session.saveSearchInNewNode(Search2, newNode);
		String searches = Search1 + Search2;

		// Share to Default security Group
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.selectRootGroupTab(Input.mySavedSearch);
		saveSearch.rootGroupExpansion();
		saveSearch.shareSavedNodeToSG(Input.shareSearchDefaultSG, newNode, searches);

		// Execute Search group and verify Status
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		saveSearch.getSavedSearchGroupName(Input.shareSearchDefaultSG).waitAndClick(10);
		saveSearch.selectNode1(newNode);
		base.waitForElement(saveSearch.getSavedSearchStatus(Search1));
		String searchStatus = saveSearch.getSavedSearchStatus(Search1).getText();
		softAssertion.assertEquals(searchStatus, "DRAFT");
		base.waitForElement(saveSearch.getSavedSearchStatus(Search2));
		String searchStatus2 = saveSearch.getSavedSearchStatus(Search2).getText();
		softAssertion.assertEquals(searchStatus2, "DRAFT");
		saveSearch.savedSearchExecute_SearchGRoup(Search1, 0);

		String searchStatusAfter = saveSearch.getSavedSearchStatus(Search1).getText();
		softAssertion.assertEquals(searchStatusAfter, "COMPLETED");
		saveSearch.savedSearch_SearchandSelect(Search2, "Yes");
		String searchStatusAfter2 = saveSearch.getSavedSearchStatus(Search2).getText();
		softAssertion.assertEquals(searchStatusAfter2, "COMPLETED");

		// deleting the searches
		saveSearch.deleteNode(searchGroup, newNode);
		saveSearch.deleteNode(Input.shareSearchDefaultSG, newNode);
		softAssertion.assertAll();
		login.logout();
	}

	/**
	 * @author sowndarya.velraj
	 * @Description : Verify that application displays all documents that are in the
	 *              aggregate results set of "Public Shared" and User Navigate
	 *              Search groups to DocList[RPMXCON-49011]
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-49011",enabled = true, groups = { "regression" } )
	public void verifyDocumentDisplayAndNavigateToDocList() throws Exception {
		String searchName1 = "Search Name" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-49011");
		base.stepInfo(
				"Verify that application displays all documents that are in the aggregate results set of  \"Public Shared\" and User Navigate\r\n"
						+ "	 *              Search groups to DocList");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// saving the Search
		session.basicContentSearch(Input.searchString5);
		session.saveSearchAtAnyRootGroup(searchName1, Input.shareSearchDefaultSG);

		// Launch DocList via Saved Search

		saveSearch.selectSavedSearchTAb(searchName1, Input.shareSearchDefaultSG, "No");

		saveSearch.launchDocListViaSSandReturnDocCount();

		base.stepInfo(
				"Application displays all documents that are in the aggregate results set of DefaultSG and User Navigate Search groups to DocList");
		login.logout();
	}

	/**
	 * @author sowndarya.velraj
	 * @Description : TC07_Verify that When PAU runs a RMU created search
	 *              (WorkProduct(Assignments)) in PAU role then search returns
	 *              documents Under "Count of Results" column on "Saved Search
	 *              Screen"[RPMXCON-49834]
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-49834",enabled = true, groups = { "regression" } )
	public void verifyRMUCreatedAssignmentReflectsInPAU() throws Exception {
		String assignmentName = "assignmentName" + Utility.dynamicNameAppender();
		String searchName = "searchName" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-49834");
		base.stepInfo(
				"PAU runs a RMU created search (WorkProduct(Assignments)) in PAU role then search returns documents ");

		// Login as PA
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		assignmentPage.createAssignment(assignmentName, Input.codeFormName);
		session.basicContentSearch(Input.searchString1);
		session.bulkAssignExisting(assignmentName);

		base.selectproject();

		session.switchToWorkproduct();
		int purehit = session.selectAssignmentInWPSWs(assignmentName);
		driver.scrollPageToTop();
		session.saveSearchAdvanced_New(searchName, Input.mySavedSearch);

		saveSearch.selectSavedSearchTAb(searchName, Input.mySavedSearch, "Yes");
		saveSearch.shareSavedSearchRMU(searchName, Input.shareSearchDefaultSG);
		login.logout();

		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.getSavedSearchGroupName(Input.shareSearchDefaultSG).waitAndClick(10);
		saveSearch.savedSearch_SearchandSelect(searchName, "Yes");
		saveSearch.savedSearchExecute_Draft(searchName, purehit);

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 12/17/21 Modified date:N/A Modified by: Description
	 *         : Verify that application displays all documents that are in the
	 *         aggregate results set of "Shared With Project Administrator/Shared
	 *         With Default Security Group " and when User Navigate to DocView.
	 *         -RPMXCON-49001 Sprint 08
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description ="RPMXCON-49001",enabled = true, groups = { "regression" } )
	public void aggregateResultDocsINDocViewwithSPA() throws InterruptedException, ParseException {
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		Boolean inputValue = true;
		int hitCount;

		String[][] verificationDatas = { { Input.pa1userName, Input.pa1password, "PA", Input.shareSearchDefaultSG },
				{ Input.pa1userName, Input.pa1password, "PA", Input.shareSearchPA },
				{ Input.rmu1userName, Input.rmu1password, "RMU", Input.shareSearchDefaultSG },
				{ Input.rev1userName, Input.rev1password, "REV", Input.shareSearchDefaultSG } };

		base.stepInfo("Test case Id: RPMXCON-49001 - Saved Search Sprint 08");
		base.stepInfo(
				"Verify that application displays all documents that are in the aggregate results set of \"Shared With Project Administrator/Shared With Default Security Group \" and when User Navigate to DocView.");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Navigate on Saved Search & Multiple Node Creation & save search in node
		saveSearch.navigateToSSPage();
		newNodeList = saveSearch.createSGAndReturn("PA", "No", 2);
		base.stepInfo("Next adding searches to the created nodes");
		driver.getWebDriver().get(Input.url + "Search/Searches");
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);

		// Share Search via Saved Search
		saveSearch.navigateToSSPage();
		base.stepInfo("Sharing the saved search with PA and with security group");
		String node = saveSearch.childNodeSelectionToShare(0, newNodeList);
		System.out.println("Final : " + node);

		saveSearch.shareSavedNodePA(Input.shareSearchDefaultSG, node, false, false, nodeSearchpair.get(node));

		saveSearch.navigateToSSPage();
		base.stepInfo("Sharing the saved search with PA and with security group");
		saveSearch.childNodeSelectionToShare(0, newNodeList);

		saveSearch.shareSavedNodePA(Input.shareSearchPA, node, false, false, nodeSearchpair.get(node));

		base.stepInfo("-------Pre-requesties completed--------");
		base.selectproject();

		// Calculate the unique doc count for the respective searches
		hitCount = session.getDocCountBtwnTwoSearches(true, Input.searchString5, Input.searchString6);
		login.logout();

		saveSearch.docViewAggregateCountCustomize(verificationDatas, hitCount, newNodeList);

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Delete Node
		saveSearch.deleteNode(Input.mySavedSearch, newNodeList.get(0), true, true);
		saveSearch.deleteNode(Input.shareSearchPA, newNodeList.get(0), true, true);
		saveSearch.deleteNode(Input.shareSearchDefaultSG, newNodeList.get(0), true, true);

		login.logout();

	}

	/**
	 * @author Raghuram A Date: 12/17/21 Modified date:N/A Modified by: Description
	 *         : Verify that application displays all documents that are in the
	 *         aggregate results set of "Shared With Project Administrator/Shared
	 *         With Default Security Group" and User Navigate to DocList
	 *         -RPMXCON-49002 Sprint 08
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description ="RPMXCON-49002",enabled = true, groups = { "regression" } )
	public void aggregateResultDocsINDocListwithSPA() throws InterruptedException, ParseException {
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		Boolean inputValue = true;
		int hitCount;

		String[][] verificationDatas = { { Input.pa1userName, Input.pa1password, "PA", Input.shareSearchDefaultSG },
				{ Input.pa1userName, Input.pa1password, "PA", Input.shareSearchPA },
				{ Input.rmu1userName, Input.rmu1password, "RMU", Input.shareSearchDefaultSG },
				{ Input.rev1userName, Input.rev1password, "REV", Input.shareSearchDefaultSG } };

		base.stepInfo("Test case Id: RPMXCON-49002 - Saved Search Sprint 08");
		base.stepInfo(
				"Verify that application displays all documents that are in the aggregate results set of \"Shared With Project Administrator/Shared With Default Security Group\" and User Navigate  to DocList");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Navigate on Saved Search & Multiple Node Creation & save search in node
		saveSearch.navigateToSSPage();
		newNodeList = saveSearch.createSGAndReturn("PA", "No", 2);
		base.stepInfo("Next adding searches to the created nodes");
		driver.getWebDriver().get(Input.url + "Search/Searches");
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);

		// Share Search via Saved Search
		saveSearch.navigateToSSPage();
		base.stepInfo("Sharing the saved search with PA and with security group");
		String node = saveSearch.childNodeSelectionToShare(0, newNodeList);
		System.out.println("Final : " + node);

		saveSearch.shareSavedNodePA(Input.shareSearchDefaultSG, node, false, false, nodeSearchpair.get(node));

		saveSearch.navigateToSSPage();
		base.stepInfo("Sharing the saved search with PA and with security group");
		saveSearch.childNodeSelectionToShare(0, newNodeList);

		saveSearch.shareSavedNodePA(Input.shareSearchPA, node, false, false, nodeSearchpair.get(node));

		base.stepInfo("-------Pre-requesties completed--------");
		base.selectproject();

		// Calculate the unique doc count for the respective searches
		hitCount = session.getDocCountBtwnTwoSearches(true, Input.searchString5, Input.searchString6);
		login.logout();

		saveSearch.docListAggregateCountCustomize(verificationDatas, hitCount, newNodeList);

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Delete Node
		saveSearch.deleteNode(Input.mySavedSearch, newNodeList.get(0), true, true);
		saveSearch.deleteNode(Input.shareSearchPA, newNodeList.get(0), true, true);
		saveSearch.deleteNode(Input.shareSearchDefaultSG, newNodeList.get(0), true, true);

		login.logout();

	}

	/**
	 * @author Raghuram A Date: 12/17/21 Modified date:N/A Modified by: Description
	 *         : Verify that When PAU runs a RMU created search
	 *         (WorkProduct(Folders)) in PAU role then searchreturns documents Under
	 *         'Count of Results' column on 'Saved Search Screen' -RPMXCON-49835
	 *         Sprint 08
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description ="RPMXCON-49835",enabled = true, groups = { "regression" } )
	public void verifyDocCountOfRMUSavedSearchAsPAU() throws Exception {

		String searchName1 = "Search Name" + Utility.dynamicNameAppender();
		String searchName2 = "Search Name" + Utility.dynamicNameAppender();
		String folderName = "Folder" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-49835");
		base.stepInfo(
				"Verify that When PAU runs a RMU created search (WorkProduct(Folders)) in PAU role then searchreturns documents Under 'Count of Results' column on 'Saved Search Screen'");

		// Login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// saving the Search
		driver.getWebDriver().get(Input.url + "Search/Searches");
		session.basicContentSearchWithSaveChanges(Input.searchString1, "Yes", "First");
		session.saveSearch(searchName1);

		// creating the folder
		saveSearch.navigateToSSPage();
		saveSearch.selectSavedSearch(searchName1);
		saveSearch.getSavedSearchToBulkFolder().waitAndClick(10);
		session.BulkActions_Folder_returnCount(folderName);

		// creating the Search Query with WorkProduct Folders
		// session.advancedSearchWorkPoductFolder(folderName);
		base.selectproject();
		session.switchToWorkproduct();
		session.selectFolderInASwp(folderName);
		driver.scrollPageToTop();
		// int purehitCount = session.searchAndReturnPureHit_Advance();
		int purehitCount = session.saveAndReturnPureHitCount();
		driver.scrollPageToTop();
		session.saveSearchAdvanced_New(searchName2, Input.mySavedSearch);

		// Sharing the Search with Default Security group
		saveSearch.navigateToSSPage();
		saveSearch.shareSavedSearchRMU(searchName2, Input.shareSearchDefaultSG);

		login.logout();

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// verifying the Count of Results as PAU
		saveSearch.navigateToSSPage();
		saveSearch.getSavedSearchGroupName(Input.shareSearchDefaultSG).waitAndClick(10);
		saveSearch.savedSearch_SearchandSelect(searchName2, "Yes");
		String DocCount = saveSearch.savedSearchExecute_Draft(searchName2, purehitCount);
		int docCount = Integer.parseInt(DocCount);
		softAssertion.assertEquals(purehitCount, docCount);
		softAssertion.assertAll();

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 10/5/21 Modified date:N/A Modified by: Description :
	 *         Validate sharing search at Security Group level from the leaf node of
	 *         5 hierarchy levels : RPMXCON-49862 - Sprint 08
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description ="RPMXCON-49862",enabled = true, groups = { "regression" } )
	public void sharingLastLeafNode() throws InterruptedException, ParseException {
		int noOfNodesToCreate = 6;
		int selectIndex = 4;
		String SGtoShare = Input.shareSearchDefaultSG;
		String node;
		Boolean inputValue = true;
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		HashMap<String, String> searchGroupSearchpIDpair = new HashMap<>();
		String[] rolesToVerify = { "RMU-2", "PA-2" };
		String passMessage = "Except the selected search, none of the searches in any hierarchical group are shared";

		base.stepInfo("Test case Id: RPMXCON-49862 - Saved Search Sprint 08");
		base.stepInfo("Validate sharing search at Security Group level from the leaf node of 5 hierarchy levels");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Landed on Saved Search
		saveSearch.navigateToSSPage();

		// Multiple Node Creation
		newNodeList = saveSearch.createSGAndReturn("PA", "No", noOfNodesToCreate);
		System.out.println("Node creation is done followed by adding searches to the created nodes");
		base.stepInfo("Node creation is done followed by adding searches to the created nodes");

		driver.getWebDriver().get(Input.url + "Search/Searches");

		// Adding searches to the created nodes
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);
		saveSearch.sortedMapList(nodeSearchpair);

		base.stepInfo("-------Pre-requesties completed--------");

		saveSearch.navigateToSSPage();

		// Search ID collection
		base.waitForElement(saveSearch.getSavedSearchNewGroupExpand());
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);

		searchGroupSearchpIDpair = saveSearch.collectionOfSearchIdsFromNodeCollections(newNodeList, nodeSearchpair,
				searchGroupSearchpIDpair);

		saveSearch.navigateToSSPage();
		node = saveSearch.childNodeSelectionToShare(selectIndex, newNodeList);
		System.out.println("Final : " + node);

		saveSearch.shareSavedNodePA(SGtoShare, node, false, true, nodeSearchpair.get(node));

		login.logout();

		// Verify with Different Users
		saveSearch.verifyListofsharedNodesandSearchesAcrossUsers(SGtoShare, newNodeList, selectIndex, nodeSearchpair,
				searchGroupSearchpIDpair, rolesToVerify, passMessage);

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Delete Node
		base.stepInfo("Initiated Delete Searches");
		saveSearch.deleteNode(Input.mySavedSearch, newNodeList.get(0));

		// Delete Shared SG Nodes
		base.stepInfo("Initiated Delete Shared SG Searches");
		saveSearch.deleteNode(Input.shareSearchDefaultSG, newNodeList.get(0));

		login.logout();

	}

	/**
	 * @author Raghuram A Date: 12/16/21 Modified date:N/A Modified by: Description
	 *         : Validate sharing search group to Project Administrators from middle
	 *         of the group hierarchy -RPMXCON-49848 Sprint 08
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description ="RPMXCON-49848",enabled = true, groups = { "regression" } )
	public void shareToPAfrommiddleOfHierarchy() throws InterruptedException, ParseException {
		int noOfNodesToCreate = 6;
		int selectIndex = 2;
		String SGtoShare = Input.shareSearchPA;
		String node;
		Boolean inputValue = true;
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		HashMap<String, String> searchGroupSearchpIDpair = new HashMap<>();
		String[] rolesToVerify = { "PA-2" };
		String passMessage = "Except the select search group and their child groups, none of the searches in parent hierarchical group should be shared";

		base.stepInfo("Test case Id: RPMXCON-49848 - Saved Search Sprint 08");
		base.stepInfo("Validate sharing search group to Project Administrators from middle of the group hierarchy");

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

		base.stepInfo("-------Pre-requesties completed--------");

		// Search ID collection
		saveSearch.navigateToSSPage();
		base.waitForElement(saveSearch.getSavedSearchNewGroupExpand());
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);

		searchGroupSearchpIDpair = saveSearch.collectionOfSearchIdsFromNodeCollections(newNodeList, nodeSearchpair,
				searchGroupSearchpIDpair);

		saveSearch.navigateToSSPage();
		node = saveSearch.childNodeSelectionToShare(selectIndex, newNodeList);
		System.out.println("Final : " + node);
		saveSearch.shareSavedNodePA(SGtoShare, node, false, true, nodeSearchpair.get(node));

		login.logout();

		// Verify with Different Users
		saveSearch.verifyListofsharedNodesandSearchesAcrossUsers(SGtoShare, newNodeList, selectIndex, nodeSearchpair,
				searchGroupSearchpIDpair, rolesToVerify, passMessage);

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Delete Node
		base.stepInfo("Initiated Delete Searches");
		saveSearch.deleteNode(Input.mySavedSearch, newNodeList.get(0));

		// Delete Shared SG Nodes
		base.stepInfo("Initiated Delete Shared SG Searches");
		saveSearch.deleteNode(SGtoShare, newNodeList.get(0));

		login.logout();

	}

	/**
	 * @author brundha
	 * @Description :Validate sharing Search Group to Default Security Group
	 * 
	 *              Test case Id: RPMXCON-49861
	 * @throws InterruptedException
	 * @throws ParseException
	 */

	@Test(description ="RPMXCON-49861",enabled = true, groups = { "regression" } )

	public void validateSecurityGroupSharingNode() throws InterruptedException, ParseException {
		base = new BaseClass(driver);
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case Id: RPMXCON-49861 saved search Sprint 08");
		base.stepInfo("Validate sharing Search Group to Default Security Group");
		String SGtoShare = Input.shareSearchDefaultSG;
		String searchID;
		String searchName = "Search Name" + Utility.dynamicNameAppender();

		SavedSearch savesearch = new SavedSearch(driver);
		String newNode = savesearch.createSearchGroupAndReturn(searchName, "username", "Yes");
		session.basicContentSearch(Input.testData1);

		session.saveSearchInNodewithChildNode(searchName, newNode);
		saveSearch.navigateToSSPage();
		savesearch.getSavedSearchNewGroupExpand().waitAndClick(10);
		searchID = savesearch.shareSavedNodePA(SGtoShare, newNode, true, true, searchName);
		login.logout();

		driver.waitForPageToBeReady();
		login.loginToSightLine(Input.pa2userName, Input.pa2password);
		saveSearch.navigateToSSPage();
		savesearch.verifySharedNode(SGtoShare, newNode, searchID, true, searchName, true);
		login.logout();

		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		saveSearch.navigateToSSPage();
		savesearch.verifySharedNode(SGtoShare, newNode, searchID, true, searchName, true);
		login.logout();

	}

	/**
	 * @author Brundha
	 * @Description :Validate sharing search with Default Security Group
	 *
	 *              Test case Id: RPMXCON-49860
	 * @throws InterruptedException
	 * @throws ParseException
	 */

	@Test(description ="RPMXCON-49860",enabled = true, groups = { "regression" } )
	public void validateSecurityGroupSharingSearch() throws InterruptedException, ParseException {
		base = new BaseClass(driver);
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case Id: RPMXCON-49860 SavedSearch Sprint 08");
		base.stepInfo("Validate sharing search with Default Security Group");
		String SGtoShare = Input.shareSearchDefaultSG;

		String searchName = "Search Name" + Utility.dynamicNameAppender();

		session.basicContentSearch(Input.testData1);
		session.saveSearch(searchName);
		saveSearch.navigateToSSPage();
		saveSearch.savedSearch_SearchandSelect(searchName, "Yes");
		String searchID = saveSearch.SavedSearchSearchID(SGtoShare, searchName);
		login.logout();

		login.loginToSightLine(Input.pa2userName, Input.pa2password);
		saveSearch.navigateToSSPage();
		saveSearch.getSavedSearchGroupName(SGtoShare).waitAndClick(5);
		saveSearch.verifySearchContents(searchName, searchID, true, true, null, null, "No");
		login.logout();

		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		saveSearch.navigateToSSPage();
		saveSearch.getSavedSearchGroupName(SGtoShare).waitAndClick(5);
		saveSearch.verifySearchContents(searchName, searchID, true, true, null, null, "No");
		login.logout();

		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		saveSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		saveSearch.deleteSearch(searchName, SGtoShare, "Yes");
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that \"Count\" display as BLANK in Saved Search Screen
	 *              when user saved a ERROR Query through Batch Search Upload
	 *              [RPMXCON-48520]
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-48520",enabled = true, groups = { "regression" } )
	public void validateErrorSearchViaBatchUpload() throws Exception {
		String search = "Invalid Name";
		String file = saveSearch.renameFile(Input.errorQueryFileLocation);

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case Id: RPMXCON-48520");
		base.stepInfo(
				"Verify that \"Count\" display as BLANK in Saved Search Screen when user saved a ERROR Query through Batch Search Upload");

		// Upload Error Query Through Batch File
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.uploadWPBatchFile_New(file, Input.errorQueryFileLocation);
		saveSearch.selectSavedSearch(search);
		base.waitForElement(saveSearch.getSavedSearchCount(search));
		String countOfDocs = saveSearch.getSavedSearchCount(search).getText();
		base.stepInfo(countOfDocs + " : is the Count of Doc");
		softAssertion.assertEquals(countOfDocs, "");

		String status = saveSearch.getSavedSearchStatus(search).getText();
		base.stepInfo(status + "  is the Status Displayed");
		softAssertion.assertEquals(status, "ERROR");

		// Delete Uploaded File
		saveSearch.deleteUploadedBatchFile(file, Input.mySavedSearch, false, null);
		softAssertion.assertAll();

		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that application displays all documents that are in the
	 *              aggregate results set of \"Shared With Project
	 *              Administrator/Shared With Default Security Group\" and User
	 *              Navigate Search groups to Report [RPMXCON-49005]
	 * @param username
	 * @param password
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-49005",enabled = true, dataProvider = "SavedSearchwithPAandRMU", groups = { "regression" } )
	public void verifyReportWhenSearchNavigateToSTRPage(String username, String password) throws Exception {
		String search = "Search" + Utility.dynamicNameAppender();
		String search2 = "Search" + Utility.dynamicNameAppender();
		SearchTermReportPage str = new SearchTermReportPage(driver);

		// Login As User
		login.loginToSightLine(username, password);
		base.stepInfo("Test case Id: RPMXCON-49005");
		base.stepInfo(
				"Verify that application displays all documents that are in the aggregate results set of \"Shared With Project Administrator/Shared With Default Security Group\" and User Navigate Search groups to Report");

//		String node1 = saveSearch.createASearchGroupandReturnName(search);
		String node1 = saveSearch.createSearchGroupAndReturn(search, Input.yesButton);
//		String node2 = saveSearch.createASearchGroupandReturnName(search);
		String node2 = saveSearch.createSearchGroupAndReturn(search, "PA", Input.yesButton);

		session.basicContentSearch(Input.searchString1);
		session.saveSearchInNewNode(search, node1);

		if (username == Input.pa1userName) {
			session.navigateToSessionSearchPageURL();
			session.saveSearchInNewNode(search2, node2);
			saveSearch.navigateToSavedSearchPage();

//			saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
			saveSearch.rootGroupExpansion();
			saveSearch.shareSavedNodeWithDesiredGroup(node2, Input.shareSearchPA);
			saveSearch.SavedSearchToTermReport_New(Input.shareSearchPA, true, node2, search2, "No");
			str.verifySTRForSearchFromSSPage(node2, search2);

		}

		saveSearch.navigateToSavedSearchPage();
		driver.waitForPageToBeReady();

//		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(10);
		saveSearch.rootGroupExpansion();
		saveSearch.shareSavedNodeWithDesiredGroup(node1, Input.shareSearchDefaultSG);
		saveSearch.SavedSearchToTermReport_New(Input.shareSearchDefaultSG, true, node1, search, "No");
		str.verifySTRForSearchFromSSPage(node1, search);

		login.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that When PAU runs a RMU created search (Metadata) in
	 *              PAU role then search returns documents Under 'Count of Results'
	 *              column on 'Saved Search Screen' [RPMXCON-49839]
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-49839",enabled = true, groups = { "regression" } )
	public void verifyDocCountOfRMUSavedSearchAsPAUForMetadata() throws Exception {

		String searchName1 = "Search Name" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-49839");
		base.stepInfo(
				"Verify that When PAU runs a RMU created search (Metadata) in PAU role then search returns documents Under 'Count of Results' column on 'Saved Search Screen'");

		// Login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// Creating the MetaData Search query
		int pureHit1 = session.advancedMetaDataSearch("CustodianName", null, Input.metaDataCN, null);
		session.saveSearch(searchName1);

		// Sharing the Search with Default Security group
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		saveSearch.shareSavedSearchRMU(searchName1, Input.shareSearchDefaultSG);

		// deleting the search
		saveSearch.deleteSearch(searchName1, Input.mySavedSearch, "Yes");

		login.logout();

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// verifying the Count of Results as PAU
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.getSavedSearchGroupName(Input.shareSearchDefaultSG).waitAndClick(10);
		saveSearch.savedSearch_SearchandSelect(searchName1, "Yes");
		String DocCount = saveSearch.savedSearchExecute_Draft(searchName1, pureHit1);
		int DocCount1 = Integer.parseInt(DocCount);
		softAssertion.assertEquals(DocCount1, pureHit1);
		softAssertion.assertAll();

		// deleting the search
		saveSearch.deleteSearch(searchName1, Input.securityGroup, "Yes");

		login.logout();

	}

	@DataProvider(name = "SavedSearchwithUsers")
	public Object[][] SavedSearchwithUsers() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password }, { Input.pa1userName, Input.pa1password },
				{ Input.rev1userName, Input.rev1password } };
		return users;
	}

	/**
	 * @author Brundha Modified date:N/A Modified by: Description : Validate sharing
	 *         options for PAU/RMU/Reviewer user -RPMXCON-49843 Sprint 08
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49843",enabled = true, dataProvider = "SavedSearchwithUsers", groups = { "regression" } )
	public void UIchangesonSSScreen(String userName, String password) throws InterruptedException {
		base.stepInfo("Test case Id: RPMXCON-49843 - Validate sharing options for PAU/RMU/Reviewer user");
		// Login as PA
		login.loginToSightLine(userName, password);
		base.stepInfo("Loggedin As : " + userName);

		saveSearch.UIchangesVerificationFlow(userName);

		login.logout();
	}

	/**
	 * @author Brundha
	 * @Description :Validate modifying search shared by PAU/RMU to specific
	 *              <Security Group Name> by both PAU and RMU
	 *
	 *              Test case Id: RPMXCON-49894
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description ="RPMXCON-49894",enabled = true, groups = { "regression" } )
	public void verifyModifySearchesInSecurityGroup() throws InterruptedException, ParseException {
		base = new BaseClass(driver);
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Test case Id: RPMXCON-49894 SavedSearch Sprint 08");
		base.stepInfo(
				"Validate modifying search shared by PAU/RMU to specific <Security Group Name> by both PAU and RMU");
		String SGtoShare = Input.shareSearchDefaultSG;

		String searchName = "Search Name" + Utility.dynamicNameAppender();
		// String searchName1 = "Search Name" + Utility.dynamicNameAppender();

		session.basicContentSearch(Input.testData1);
		session.saveSearch(searchName);
		base.selectproject();
		saveSearch.navigateToSSPage();
		saveSearch.savedSearch_SearchandSelect(searchName, "Yes");
		String searchID = saveSearch.SavedSearchSearchID(SGtoShare, searchName);
		driver.waitForPageToBeReady();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		saveSearch.getDefaultSecurityGroupExpand().waitAndClick(10);
		driver.waitForPageToBeReady();
		String mySvedSearchID = saveSearch.getMySaveSearchID(searchName, "Yes");
		base.textCompareNotEquals(mySvedSearchID, searchID,
				" " + searchID + " and " + mySvedSearchID + " are not same as expected",
				"" + searchID + " and " + mySvedSearchID + " is same ");

		saveSearch.savedSearch_SearchandSelect(searchName, "Yes");

		driver.waitForPageToBeReady();
		saveSearch.getSavedSearchEditButton().waitAndClick(10);

		// Overwrite Search / Modify
		session.modifySearchTextArea(1, Input.testData1, Input.searchString2, "Save");
		session.saveAsOverwrittenSearch(SGtoShare, searchName, "first", "Success", "", null);
		login.logout();

		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		saveSearch.navigateToSSPage();
		saveSearch.getSavedSearchGroupName(SGtoShare).waitAndClick(5);
		saveSearch.verifySearchContents(searchName, searchID, true, true, null, null, "No");
		login.logout();

		login.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		saveSearch.navigateToSSPage();
		saveSearch.getSavedSearchGroupName(SGtoShare).waitAndClick(5);
		saveSearch.verifySearchContents(searchName, searchID, true, true, null, null, "No");

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 12/20/21 Modified date:N/A Modified by:Raghuram
	 * @Description : Verify that application displays all documents that are in the
	 *              aggregate results set of "Shared With Project
	 *              Administrator/Shared With Default Security Group" and User
	 *              Performs Bulk Tag from Search groups -RPMXCON-49003 Sprint 08
	 * @throws InterruptedException
	 * @throws ParseException
	 */

	@Test(description ="RPMXCON-49003",enabled = true, dataProvider = "UserAndShare", groups = { "regression" } )
	public void aggregateResultWhileBulkTagAllUsers(String userNameToCreateTag, String passwordToCreateTag,
			String fullName, String shareTo, String actionUserName, String actionPassword)
			throws InterruptedException, ParseException {
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		String TagName = "Tag" + Utility.dynamicNameAppender(), finalCount;
		Boolean inputValue = true;
		int noOfNodesToCreate = 2, pureHit, finalCountresult, selectIndex = 0;
		int latencyCheckTime = 5;
		String passMessage = "Application not hang or shows latency more than " + latencyCheckTime + " seconds.";
		String failureMsg = "Continues Loading more than " + latencyCheckTime + " seconds.";
		base.stepInfo("Test case Id: RPMXCON-49003 - Saved Search Sprint 08");
		base.stepInfo(
				"Verify that application displays all documents that are in the aggregate results set of \"Shared With Project Administrator/Shared With Default Security Group\" and User Performs Bulk Tag from Search groups");

		// Login as User
		login.loginToSightLine(userNameToCreateTag, passwordToCreateTag);
		base.stepInfo("Loggedin As : " + fullName);

		// Create Tag
		base.stepInfo("Create Tag");
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateTag(TagName, shareTo);

		login.logout();

		login.loginToSightLine(actionUserName, actionPassword);
		base.stepInfo("Loggedin As : " + actionUserName);

		// Landed on Saved Search
		saveSearch.navigateToSSPage();

		// Multiple Node Creation
		newNodeList = saveSearch.createSGAndReturn(Input.rev1FullName, "No", noOfNodesToCreate);
		System.out.println("Next adding searches to the created nodes");
		base.stepInfo("Next adding searches to the created nodes");

		// add save search in node
		driver.getWebDriver().get(Input.url + "Search/Searches");
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);

		base.stepInfo("-------Pre-requesties completed--------");

		saveSearch.navigateToSSPage();
		String node = saveSearch.childNodeSelectionToShare(selectIndex, newNodeList);
		System.out.println("Final : " + node);
		saveSearch.shareSavedNodePA(shareTo, node, false, true, nodeSearchpair.get(node));

		base.selectproject();

		// Landed on Saved Search
		saveSearch.navigateToSSPage();

		saveSearch.getSavedSearchGroupName(shareTo).waitAndClick(5);
		saveSearch.getSavedSearchToBulkTag().Click();

		// Load latency Verification
		Element loadingElement = saveSearch.getTotalCountLoad();
		saveSearch.loadingCountVerify(loadingElement, latencyCheckTime, passMessage, failureMsg);
		finalCount = session.bulkTagExistingWithReturn(TagName);
		base.stepInfo("Completed Bulk Tag");
		base.stepInfo(
				"Navigating to Search >> Basic Search >> Advanced Search >> WorkProduct >> Tags (Select Same Tag which we have created in prerequesties.");
		session.switchToWorkproduct();
		session.selectTagInASwp(TagName);
		pureHit = session.serarchWP();
		finalCountresult = Integer.parseInt(finalCount);
		base.stepInfo("Finalized Tag count : " + finalCountresult);
		base.stepInfo("Aggreagate Tag search count : " + pureHit);

		base.digitCompareEquals(pureHit, finalCountresult,
				"After the Bulk Tag - Pure hit appear like aggregate results set of all child search groups and searches   ",
				"Count Mismatches");

		// Delete Node
		saveSearch.deleteNode(Input.mySavedSearch, newNodeList.get(0), true, true);
		saveSearch.deleteNode(shareTo, newNodeList.get(0), true, true);

		login.logout();
	}

	/**
	 * @author Raghuram A Date: 12/20/21 Modified date:N/A Modified by:Raghuram
	 * @Description : Verify that application displays all documents that are in the
	 *              aggregate results set of "Shared With Project
	 *              Administrator/Shared With Default Security Group" and User
	 *              Performs Bulk Folder from Search groups -RPMXCON-49004 Sprint 08
	 * @throws InterruptedException
	 * @throws ParseException
	 */

	@Test(description ="RPMXCON-49004",enabled = true, dataProvider = "UserAndShare", groups = { "regression" } )
	public void aggregateResultWhileBulkFolder(String userNameToCreateTag, String passwordToCreateTag, String fullName,
			String shareTo, String actionUserName, String actionPassword) throws InterruptedException, ParseException {
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		String folderName = "Folder" + Utility.dynamicNameAppender(), finalCount;
		Boolean inputValue = true;
		int noOfNodesToCreate = 2, pureHit, finalCountresult, selectIndex = 0;
		int latencyCheckTime = 5;
		String passMessage = "Application not hang or shows latency more than " + latencyCheckTime + " seconds.";
		String failureMsg = "Continues Loading more than " + latencyCheckTime + " seconds.";
		base.stepInfo("Test case Id: RPMXCON-49004 - Saved Search Sprint 08");
		base.stepInfo(
				"Verify that application displays all documents that are in the aggregate results set of \"Shared With Project Administrator/Shared With Default Security Group\" and  when User Performs Bulk Folder from Search groups");

		// Login as User
		login.loginToSightLine(userNameToCreateTag, passwordToCreateTag);
		base.stepInfo("Loggedin As : " + fullName);

		// Create Tag
		base.stepInfo("Create Tag");
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(folderName, shareTo);
		driver.waitForPageToBeReady();

		login.logout();

		login.loginToSightLine(actionUserName, actionPassword);
		base.stepInfo("Loggedin As : " + actionUserName);

		// Landed on Saved Search
		saveSearch.navigateToSSPage();

		// Multiple Node Creation
		newNodeList = saveSearch.createSGAndReturn(fullName, "No", noOfNodesToCreate);
		System.out.println("Next adding searches to the created nodes");
		base.stepInfo("Next adding searches to the created nodes");

		// add save search in node
		driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.waitForPageToBeReady();
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);

		base.stepInfo("-------Pre-requesties completed--------");

		saveSearch.navigateToSSPage();
		String node = saveSearch.childNodeSelectionToShare(selectIndex, newNodeList);
		System.out.println("Final : " + node);
		saveSearch.shareSavedNodePA(shareTo, node, false, true, nodeSearchpair.get(node));

		base.selectproject();

		// Landed on Saved Search
		saveSearch.navigateToSSPage();

		saveSearch.getSavedSearchGroupName(shareTo).waitAndClick(5);
		saveSearch.getSavedSearchToBulkFolder().waitAndClick(5);

		// Load latency Verification
		Element loadingElement = saveSearch.getTotalCountLoad();
		saveSearch.loadingCountVerify(loadingElement, latencyCheckTime, passMessage, failureMsg);
		finalCount = session.bulkFolderExistingWithReturn(folderName);
		base.stepInfo("Completed Bulk Folder mapping");
		base.stepInfo(
				"Navigating to Search >> Basic Search >> Advanced Search >> WorkProduct >> Folder (Selected Same Folder which we have created in prerequesties.");
		session.switchToWorkproduct();
		session.selectFolderInASwp(folderName);
		pureHit = session.serarchWP();
		finalCountresult = Integer.parseInt(finalCount);
		base.stepInfo("Finalized Folder count : " + finalCountresult);
		base.stepInfo("Aggreagate Folder search count : " + pureHit);

		base.digitCompareEquals(pureHit, finalCountresult,
				"After the Bulk Folder - Pure hit shows the aggregate results set of all child search groups and searches  ",
				"Count Mismatches");

		// Delete Node
		saveSearch.deleteNode(Input.mySavedSearch, newNodeList.get(0), true, true);
		saveSearch.deleteNode(shareTo, newNodeList.get(0), true, true);

		login.logout();
	}

	/**
	 * @author Brundha
	 * @Description :For RMU - Validate saving executed search (executed or draft)
	 *              under My Searches or Shared Searches
	 *
	 *              Test case Id: RPMXCON-49890
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description ="RPMXCON-49890",enabled = true, groups = { "regression" } )
	public void verifyOverwrittenSearchesID() throws InterruptedException, ParseException {
		base = new BaseClass(driver);
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Test case Id: RPMXCON-49890 SavedSearch Sprint 08");
		base.stepInfo(
				"For RMU - Validate saving executed search (executed or draft) under My Searches or Shared Searches");

		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();
		String SGtoShare = Input.shareSearchDefaultSG;

		// create new searchgroup

		String newNode = saveSearch.createNewSearchGrp(searchName1);

		// add save search in node
		int purehit = session.basicContentSearch(Input.searchString1);

//		session.saveSearchInNode(searchName1);
		session.saveSearchInNewNode(searchName1, newNode);

		saveSearch.navigateToSavedSearchPage();
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

		saveSearch.navigateToSavedSearchPage();

		saveSearch.getSavedSearchGroupName(SGtoShare).waitAndClick(10);
		saveSearch.selectNode1(newNode);
		saveSearch.savedSearch_SearchandSelect(searchName1, "Yes");
		saveSearch.savedSearchExecute2(searchName1, purehit);

		saveSearch.getSavedSearchEditButton().waitAndClick(5);

		driver.waitForPageToBeReady();

		saveSearch.validatingSaves(SGtoShare, newNode, searchName1);

		login.logout();

	}

	/**
	 * @author Sowndarya.velraj A Date: 12/20/21 Modified date:N/A Modified by:
	 *         Description :Validate executing search shared by PAU/RMU to specific
	 *         <Security Group Name> by both PAU and RMU -RPMXCON-49895 Sprint 08
	 */
	@Test(description ="RPMXCON-49895",enabled = true, groups = { "regression" } )
	public void validateSharedSearchByPAUandRMU() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-49895");
		base.stepInfo("To verify User is able to filter saved search based on their status");
		String searchName = "search" + Utility.dynamicNameAppender();
		String othSG = "Security Group_" + UtilityLog.dynamicNameAppender();

		SecurityGroupsPage sg = new SecurityGroupsPage(driver);
		UserManagement userManagemet = new UserManagement(driver);

		// Login as PAU
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Create Security Group
		driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		sg.AddSecurityGroup(othSG);
		base.stepInfo(othSG + " : created");

		// Assign access to SG
		userManagemet.assignAccessToSecurityGroups(othSG, Input.rmu1userName);

		// saving the Search
		driver.getWebDriver().get(Input.url + "Search/Searches");
		session.basicContentSearchWithSaveChanges(Input.searchString5, "No", "First");
		base.hitEnterKey(1);
		session.selectOperatorInBasicSearch("OR");
		int hitCount = session.basicContentSearchWithSaveChanges(Input.searchString6, "Yes", "Third");

		session.saveSearch(searchName);
		session.bulkRelease(othSG);

		saveSearch.shareSavedSearchAsPA(searchName, othSG);
		saveSearch.selectSavedSearchTAb(searchName, othSG, "Yes");
		saveSearch.savedSearchExecute_Draft(searchName, hitCount);
		driver.waitForPageToBeReady();
		String docCount1 = saveSearch.getCountofDocs().getText();
		saveSearch.savedSearchToDocList(searchName);

		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(2);
		doclist.bulkUnRelease(othSG);

		login.logout();

		// Login as PAU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);
		base.selectsecuritygroup(othSG);

		// saving the Search
		driver.getWebDriver().get(Input.url + "Search/Searches");
		session.basicContentSearchWithSaveChanges(Input.searchString5, "No", "First");
		base.hitEnterKey(1);
		session.selectOperatorInBasicSearch("OR");
		int hitCount2 = session.basicContentSearchWithSaveChanges(Input.searchString6, "Yes", "Third");

		saveSearch.selectSavedSearchTAb(searchName, othSG, "Yes");
		saveSearch.savedSearchExecute_Draft(searchName, hitCount2);
		driver.waitForPageToBeReady();
		String docCount2 = saveSearch.getCountofDocs().getText();

		base.selectsecuritygroup(Input.securityGroup);
		login.logout();

		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		saveSearch.selectSavedSearchTAb(searchName, othSG, "Yes");
		driver.waitForPageToBeReady();
		String docCount3 = saveSearch.getCountofDocs().getText();

		softAssertion.assertEquals(docCount2, docCount3);
		softAssertion.assertAll();

		sg.deleteSecurityGroups(othSG);
		login.logout();

	}

	/**
	 * @author Sowndarya.velraj A Date: 12/20/21 Modified date:N/A Modified by:
	 *         Description :To verify User is able to filter saved search based on
	 *         their status -RPMXCON-49958 Sprint 08
	 */
	@Test(description ="RPMXCON-49958",enabled = true, groups = { "regression" } )
	public void verifySavedSearchFilter() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-49958");
		base.stepInfo("To verify User is able to filter saved search based on their status");

		String searchName1 = "ANode" + Utility.dynamicNameAppender();
		String file = saveSearch.renameFile(Input.errorQueryFileLocation);

// Login as PAU
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

// saving the Search
		driver.getWebDriver().get(Input.url + "Search/Searches");
		session.basicContentSearch(Input.searchStringStar);
		session.saveSearch(searchName1);

// Navigating to saved search page
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
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

// Upload Error Query Through Batch File
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.uploadWPBatchFile_New(file, Input.errorQueryFileLocation);

		base.stepInfo("To verify Error Status By applying filter");
		saveSearch.getStatusDropDown().Click();
		saveSearch.getLastStatusAsError().Click();
		saveSearch.getSavedSearch_ApplyFilterButton().Click();
		saveSearch.verifyExecutionStatusInSavedSearchPage("ERROR");

		base.stepInfo("To verify Failed Status By applying filter");
		saveSearch.getStatusDropDown().Click();
		saveSearch.getLastStatusAsFailed().Click();
		saveSearch.getSavedSearch_ApplyFilterButton().Click();
		saveSearch.verifyExecutionStatusInSavedSearchPage("FAILED");

		saveSearch.selectRootGroupTab(Input.mySavedSearch);
		saveSearch.SaveSearchDelete(searchName1);
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description :Verify that Save search group not have reference of saved
	 *              search in same group will not show any error on \"Execute\"
	 *              [RPMXCON-48621]
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-48621",enabled = true, dataProvider = "Execute", groups = { "regression" } )
	public void verifySearchesExecute(String username, String password) throws Exception {
		String Search = "Search" + Utility.dynamicNameAppender();
		String Search2 = "Search" + Utility.dynamicNameAppender();
		String Search3 = "Search" + Utility.dynamicNameAppender();
		String headerName = "Last Status";
		String compareString = "COMPLETED";
		String passMsg = "Execute operation is successfull";
		String failMSg = "Error message is displayed";
		List<String> list = new ArrayList<>();

		// Login as PA
		login.loginToSightLine(username, password);

		base.stepInfo("Test case Id: RPMXCON-48621 Saved Search");
		base.stepInfo(
				"Verify that Save search group not have reference of saved search in same group will not show any error on \"Execute\"");

		String node = saveSearch.createSearchGroupAndReturn(Search, "RMU", Input.yesButton);

		session.basicContentSearch(Input.searchString5);
		session.saveSearchInNewNode(Search, node);
		session.saveSearchInNewNode(Search2, node);
		session.saveSearchInNewNode(Search3, node);

		saveSearch.navigateToSavedSearchPage();
		saveSearch.selectNode1(node);
		saveSearch.savedSearchExecute_SearchGRoup(Search, 0);

		driver.Navigate().refresh();
		list = saveSearch.getListFromSavedSearchTable(headerName);
		base.compareListWithString(list, compareString, passMsg, failMSg);

		// Delete Node
		saveSearch.deleteNode(Input.mySavedSearch, node);
		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @Description:Verify that logged User Information gets updated in "Last
	 *                     Submitted By" column in Saved Search screen
	 *                     [RPMXCON-48588] Sprint 12
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description ="RPMXCON-48588",enabled = true, groups = { "regression" } )
	public void verifyLastSubmittedBYAsRMU_User1() throws InterruptedException, ParseException {
		String SearchName = "Search" + Utility.dynamicNameAppender();
		String SearchName2 = "Search" + Utility.dynamicNameAppender();
		String SearchName3 = "Search" + Utility.dynamicNameAppender();

		String headername = "Last Submitted By";
		List<String> list = new ArrayList<>();

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case Id: RPMXCON-48588 - Saved Search Sprint 11");
		base.stepInfo(
				"Verify that logged User Information gets updated in \"Last Submitted By\" column in Saved Search screen");
		String pausername = login.getCurrentUserName();

		// create Node AS PA
		String node = saveSearch.createSearchGroupAndReturn(SearchName, "PA", Input.yesButton);

		// saving the Search
		session.basicContentSearch(Input.TallySearch);
		session.saveSearchInNewNode(SearchName, node);

		// SHare node to Security Group
		saveSearch.navigateToSavedSearchPage();
		saveSearch.shareSavedNodeWithDesiredGroup(node, Input.shareSearchDefaultSG);

		login.logout();
		// login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		String username = login.getCurrentUserName();
		String passMsg = username + " : is the Last Submiited Name Displayed";
		String failMsg = "Able to See other User Searches";
		base.stepInfo("Logged in username : " + username);

		// create Node as RMU
		String node1 = saveSearch.createSearchGroupAndReturn(SearchName2, "RMU", Input.yesButton);

		// save The Search in node and folder
		session.navigateToSessionSearchPageURL();
		int purehit = session.basicContentSearch(Input.TallySearch);
		session.saveSearch(SearchName2);
		session.saveSearchInNewNode(SearchName3, node1);

		// verify Last Submitted Status For all the search under My saved Search
		saveSearch.navigateToSavedSearchPage();
		base.waitForElement(saveSearch.getSavedSearchGroupName(Input.mySavedSearch));
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(10);
		list = saveSearch.getListFromSavedSearchTable(headername);
		base.compareListWithString(list, username, passMsg, failMsg);

		// verify Last Submitted For node After execute operation[My saved Search]
		saveSearch.selectNode1(node1);
		saveSearch.savedSearchExecute_SearchGRoup(SearchName3, purehit);
		driver.Navigate().refresh();
		saveSearch.selectNode1(node1);
		String actualName = saveSearch.getListFromSavedSearchTable1(headername, SearchName3);
		base.textCompareEquals(actualName, username, passMsg, failMsg);

		// verify Last Submiited For node After execute operation [Security Group]
		saveSearch.selectNodeUnderSpecificSearchGroup(Input.shareSearchDefaultSG, node);
		saveSearch.savedSearchExecute_SearchGRoup(SearchName, purehit);
		driver.Navigate().refresh();
		saveSearch.selectNode1(node);
		String actualName2 = saveSearch.getListFromSavedSearchTable1(headername, SearchName);
		base.textCompareEquals(actualName2, username, passMsg, failMsg);

		// Delete Node
		saveSearch.deleteNode(Input.mySavedSearch, node1);
		saveSearch.deleteNode(Input.shareSearchDefaultSG, node);
		login.logout();
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
//							 TODO: handle exception
			}
		}
		try {
			login.quitBrowser();
		} catch (Exception e) {
			login.closeBrowser();
			login.clearBrowserCache();
		}
		System.out.println("Executed :" + result.getMethod().getMethodName());
	}

	@AfterClass(alwaysRun = true)
	public void close() {

		System.out.println("Executed :" );

	}

}
