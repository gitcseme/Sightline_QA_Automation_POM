package sightline.savedSearch;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Dimension;
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
import pageFactory.BatchRedactionPage;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.ReportsPage;
import pageFactory.SavedSearch;
import pageFactory.SearchTermReportPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.TallyPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class SavedsearchRegression_New_Set_06 {

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

	@DataProvider(name = "AllTheUsers")
	public Object[][] AllTheUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName } };
		return users;
	}

	@DataProvider(name = "UserAndSearchGroup")
	public Object[][] UserAndSearchGroup() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName, Input.mySavedSearch },
				{ Input.pa1userName, Input.pa1password, Input.pa1FullName, Input.shareSearchPA },
				{ Input.pa1userName, Input.pa1password, Input.pa1FullName, Input.shareSearchDefaultSG },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName, Input.mySavedSearch },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName, Input.shareSearchDefaultSG } };
		return users;
	}

	@DataProvider(name = "SwitchUsers")
	public Object[][] SavedSearchwithUsers() {
		Object[][] users = { { Input.sa1userName, Input.sa1password, "SA" },
				{ Input.da1userName, Input.da1password, "DA" }, { Input.pa1userName, Input.pa1password, "PA" } };
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
	 * @Author Jeevitha
	 * @Description : Verify that While editing saved search which has conceptual
	 *              search, slider value(Precision Sensitivity) and Precision
	 *              Sensitivity text box value should display its actual value
	 *              (value set during the searches) [RPMXCON-63580]
	 * @param username
	 * @param password
	 * @param fullName
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-63580",enabled = true, dataProvider = "AllTheUsers", groups = { "regression" } )
	public void verifyingPrecisionSensitivityValueWhileConfiguringWithrecisionSensitivityValueWhileEditing(
			String username, String password, String fullName) throws Exception {

		String conceptSearch = "search" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-63580 Saved Search");
		base.stepInfo(
				"Verify that While editing Saved serach which has conceptual search, slider value (precision sensitivity) and precision sensitivity text box value should display its actual value (Value set during the searches)");

		// Login as USER
		login.loginToSightLine(username, password);
		base.stepInfo("Loggedin as : " + fullName);

		// Create New Search Group
		String newNode = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "PA", "No");

		// Conceptual Search
		session.navigateToAdvancedSearchPage();
		String precisionValueBefore = session.verifyingPrecisionValue(true, Input.conceptualSearchString1, true, false);
		session.saveAndReturnPureHitCount();
		driver.scrollPageToTop();
		session.saveSearchInNewNode(conceptSearch, newNode);

		// selecting the savedSearch and click edit button
		saveSearch.selectNodeUnderSpecificSearchGroup(Input.mySavedSearch, newNode);
		saveSearch.savesearch_Edit(conceptSearch);

		driver.waitForPageToBeReady();
		session.getModifyASearch().waitAndClick(10);
		String precisionValueAfterEdit = session.verifyingPrecisionValue(false, null, false, false);

		String passMsg = precisionValueBefore
				+ " is the Precision value Before  and After traverse from savedsaerch page is "
				+ precisionValueAfterEdit;
		String failMsg = "Precision value befor and after is not Same";
		base.textCompareEquals(precisionValueAfterEdit, precisionValueBefore, passMsg, failMsg);

		// performing the search and verifying the Search Result Displayed
		session.saveAndReturnPureHitCount();

		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Nexidia Upgrade - Validate editing/executing non-audio
	 *              searches on . an exising project with having audio documents
	 *              [RPMXCON-47154]
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-47154",enabled = true, dataProvider = "UserAndSearchGroup", groups = { "regression" } )
	public void validateEditingOrExecutingNonaudioSearches(String username, String password, String fullname,
			String rootGroup) throws Exception {
		String search1 = "Search" + Utility.dynamicNameAppender();
		String search2 = "Search" + Utility.dynamicNameAppender();

		login.loginToSightLine(username, password);
		base.stepInfo("Loggedin As : " + fullname);

		base.stepInfo("Test case Id: RPMXCON-47154");
		base.stepInfo(
				"Nexidia Upgrade - Validate editing/executing non-audio searches on an exising project with having audio documents");

		String newNode = saveSearch.createSearchGroupAndReturn(rootGroup, "", Input.yesButton);
		session.basicContentSearch(Input.searchString1);
		session.saveSearchInNodeUnderGroup(search2, newNode, rootGroup);

		base.selectproject();
		int purehit = session.basicContentSearch(Input.searchString1);
		session.saveSearchAtAnyRootGroup(search1, rootGroup);

		saveSearch.selectSavedSearchTAb(search1, rootGroup, "Yes");
		saveSearch.getSavedSearchEditButton().waitAndClick(3);

		base.waitForElement(session.getNewSearchButton());
		session.verifySessionSearchPage();
		driver.waitForPageToBeReady();

		saveSearch.navigateToSavedSearchPage();
		saveSearch.selectSavedSearchTAb(search1, rootGroup, "Yes");
		base.waitForElement(saveSearch.getCountofDocs());
		String countBeforeExecuting = saveSearch.getCountofDocs().getText();
		String countAfterExecuting = saveSearch.savedSearchExecute_Draft(search1, purehit);

		String passMSg = " Count Before And after execution is same : " + countAfterExecuting;
		String failMsg = " Count Before And after execution is not same : ";
		base.textCompareEquals(countAfterExecuting, countBeforeExecuting, passMSg, failMsg);

		saveSearch.selectNodeUnderSpecificSearchGroup(rootGroup, newNode);
		base.waitTime(1);
		base.waitForElement(saveSearch.getCountofDocs());
		countBeforeExecuting = saveSearch.getCountofDocs().getText();
		saveSearch.performExecute();
		driver.waitForPageToBeReady();
		saveSearch.savedSearch_SearchandSelect(search2, "Yes");
		base.waitForElement(saveSearch.getCountofDocs());
		countAfterExecuting = saveSearch.getCountofDocs().getText();

		base.textCompareEquals(countAfterExecuting, countBeforeExecuting, passMSg, failMsg);

		login.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description : SA/DA/PA user impersonate down as RMU/RU role, create
	 *              Searchgroups and Searches, and then runs the Tally report
	 *              against My saved searches in PAU role [RPMXCON-57405]
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-57405",enabled = true, dataProvider = "SwitchUsers", groups = { "regression" } )
	public void verifyTAllyReportForSearch(String username, String password, String role) throws InterruptedException {
		String search1PA = "Search" + Utility.dynamicNameAppender();
		String search2PA = "Search" + Utility.dynamicNameAppender();
		String searc3RMU = "Search" + Utility.dynamicNameAppender();
		String search4RMU = "Search" + Utility.dynamicNameAppender();
		String search5REV = "Search" + Utility.dynamicNameAppender();
		String search6REV = "Search" + Utility.dynamicNameAppender();

		TallyPage tally = new TallyPage(driver);
		ReportsPage report = new ReportsPage(driver);

		base.stepInfo("Test case Id: RPMXCON-57405 Savedsearch");
		base.stepInfo(
				"SA/DA/PA user impersonate down as RMU/RU role, create Searchgroups and Searches, and then runs the Tally report against My saved searches in PAU role");

		// Login as user
		login.loginToSightLine(username, password);
		base.rolesToImp(role, "PA");

		String newNodePA = saveSearch.createNewSearchGrp(Input.mySavedSearch);
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(search1PA);
		session.saveSearchInNewNode(search2PA, newNodePA);

		base.rolesToImp("PA", "RMU");

		String newNodeRMU = saveSearch.createNewSearchGrp(Input.mySavedSearch);
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searc3RMU);
		session.saveSearchInNewNode(search4RMU, newNodeRMU);

		base.rolesToImp("RMU", "REV");

		String newNodeREV = saveSearch.createNewSearchGrp(Input.mySavedSearch);
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(search5REV);
		session.saveSearchInNewNode(search6REV, newNodeREV);

		// impersonate from REV to RMU
		base.rolesToImp("REV", "RMU");

		// go to tally page check search groups and searches for RMU
		tally.navigateTo_Tallypage();
		tally.getTally_SelectSource().waitAndClick(10);
		tally.getTally_Searches().waitAndClick(10);
		report.checkList(newNodePA, newNodeRMU, newNodeREV, searc3RMU, search5REV, "RMU", null);

		// impersonate from RMU to user
		base.rolesToImp("RMU", "PA");

		// go to tally page check search groups and searches for PA
		tally.navigateTo_Tallypage();
		tally.getTally_SelectSource().waitAndClick(10);
		tally.getTally_Searches().waitAndClick(10);
		report.checkList(newNodePA, newNodeRMU, newNodeREV, searc3RMU, search5REV, "PA", null);
		report.checkdataPresent(search1PA, "PA");

		// getting document count from tally page
		driver.Navigate().refresh();

		String[] searchList = { newNodePA, search1PA };
		tally.SelectSource_MultipleSavedSearch(searchList);
		tally.selectTallyByMetaDataField(Input.metaDataName);
		tally.validateMetaDataFieldName(Input.metaDataName);
		tally.verifyTallyChart();

		// logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : TC05_Verify that When PAU runs a RMU created search
	 *              (WorkProduct(Tags/Folder/Assignment)) in PAU role then search
	 *              returns documents Under \"Count of Results\" column on \"Saved
	 *              Search Screen\" [RPMXCON-49836] 
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-49836",enabled = true, groups = { "regression" } )
	public void verifySearchDocOfRmu() throws Exception {
		String folderName = "Folder" + Utility.dynamicNameAppender();
		String tagName = "TagName" + Utility.dynamicNameAppender();
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();
		String savedsearcTaghName = "SavedSearchTag" + Utility.dynamicNameAppender();
		String savedSearchFolderName = "SavedSearchFolder" + Utility.dynamicNameAppender();
		String savedSearchAssignmentName = "SavedSearchAssignment" + Utility.dynamicNameAppender();

		AssignmentsPage assign = new AssignmentsPage(driver);

		// login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Loggedin As : " + Input.rmu1FullName);

		base.stepInfo("Test case Id: RPMXCON-49836 Saved Search");
		base.stepInfo(
				"TC05_Verify that When PAU runs a RMU created search (WorkProduct(Tags/Folder/Assignment)) in PAU role then search returns documents Under \"Count of Results\" column on \"Saved Search Screen\" ");

		// Create Search Group
		String newNodeRMU = saveSearch.createNewSearchGrp(Input.mySavedSearch);

		// creating tag ,folder
		session.basicContentSearch(Input.searchString1);
		session.bulkTag(tagName);
		session.bulkFolderWithOutHitADD(folderName);

		// creating the assignment
		session.Removedocsfromresults();
		session.addNewSearch();
		session.multipleBasicContentSearch(Input.TallySearch);
		session.bulkAssign();
		assign.assignmentCreation(assignmentName, Input.codeFormName);

		// creating the savedSearch containing tag
		base.selectproject();
		session.switchToWorkproduct();
		session.selectTagInASwp(tagName);
		int tagPureHitCount = session.saveAndReturnPureHitCount();
		driver.scrollPageToTop();
		session.saveSearchInNewNode(savedsearcTaghName, newNodeRMU);

		// creating the savedSerach containing folder
		base.selectproject();
		session.switchToWorkproduct();
		session.selectFolderInASwp(folderName);
		int folderPureHitCount = session.saveAndReturnPureHitCount();
		driver.scrollPageToTop();
		session.saveSearchInNewNode(savedSearchFolderName, newNodeRMU);

		// creating the savedSerach containing assignment
		base.selectproject();
		session.switchToWorkproduct();
		int assignmentPureHitCount = session.selectAssignmentInWPSWs(assignmentName);
		driver.scrollPageToTop();
		session.saveSearchInNewNode(savedSearchAssignmentName, newNodeRMU);

		// sharing the savedSearch with security group
		saveSearch.selectNodeUnderSpecificSearchGroup(Input.mySavedSearch, newNodeRMU);
		saveSearch.shareSavedNodeWithDesiredGroup(newNodeRMU, Input.shareSearchDefaultSG);

		// logout
		login.logout();

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// verifying the count of results for savedSearch containing the tag
		saveSearch.selectNodeUnderSpecificSearchGroup(Input.shareSearchDefaultSG, newNodeRMU);
		saveSearch.performExecute();

		// Verify Search Status And Count in all nodes
		List<String> newNodeList = new ArrayList<>();
		newNodeList.add(newNodeRMU);
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		nodeSearchpair.put(newNodeRMU, savedsearcTaghName);

		saveSearch.verifyStatusAndCountInAllChildNode(Input.shareSearchDefaultSG, newNodeList, 0, nodeSearchpair);

		// logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Batch Upload : Verify that conceptually similar counts match
	 *              with expected counts post modify and run. [RPMXCON-49264]
	 * @param UserName
	 * @param PassWord
	 * @param fullName
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49264",enabled = true,groups = { "regression" }, dataProvider = "AllTheUsers")
	public void batchUploadAndVerifyConceptCount(String UserName, String PassWord, String fullName)
			throws InterruptedException {

		String headername_1 = "Search Name";
		String headername_2 = "Conceptually Similar Count";
		String file = saveSearch.renameFile(Input.SearchBatchFile);

		// login as user
		login.loginToSightLine(UserName, PassWord);
		base.stepInfo("Loggedin As : " + fullName);

		base.stepInfo("Test case id: RPMXCON-49264 Savedsearch");
		base.stepInfo(
				"Batch Upload : Verify that conceptually similar counts match with expected counts post modify and run.");

		// performing batch upload functionality using attached excel file
		saveSearch.navigateToSSPage();
		saveSearch.uploadWPBatchFile_New(file,Input.SearchBatchFile);
		base.stepInfo("Successfully uploaded the BAtch file");

		// collecting list of search name from SavedSearch DataTable
		List<String> SearchNamelist = saveSearch.getListFromSavedSearchTable(headername_1);

		// enabling the conceptually similar count in savedsearch datatable
		saveSearch.checkHideandShowFunction(headername_2);
		saveSearch.getFieldHeader(headername_2).ScrollTo();
		driver.waitForPageToBeReady();

		// collecting list of conceptually similar count from SavedSearch DataTable
		List<String> conceptuallySimilarCountList = saveSearch.getListFromSavedSearchTable(headername_2);
		driver.waitForPageToBeReady();

		// verifying Conceptually Similar Count In SessionSearche With Count In
		// SavedSearch for all the SavedSearch Terms
		session.verifyingConceptuallySimilarCountInSessionSearcheWithCountInSavedSearch(SearchNamelist,
				conceptuallySimilarCountList);

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
				login.logoutWithoutAssert();
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
		UtilityLog.info("******Execution completed for " + this.getClass().getSimpleName() + "********");
	}

}
