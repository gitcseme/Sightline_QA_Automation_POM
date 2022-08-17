package sightline.savedSearch;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
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
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class SavedSearchRegression_01 {

	Driver driver;
	LoginPage login;
	SavedSearch saveSearch;
	SessionSearch session;
	BaseClass base;
	SoftAssert softAssertion;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		Input in = new Input();
		in.loadEnvConfig();

	}

	@DataProvider(name = "SavedSearchwithUsers")
	public Object[][] SavedSearchwithUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName } };
		return users;
	}

	@DataProvider(name = "SavedSearchwithUsersAndShareGroup")
	public Object[][] SavedSearchwithUsersAndShareGroup() {
	Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName,Input.shareSearchPA },
	{ Input.pa1userName, Input.pa1password, Input.pa1FullName,Input.shareSearchDefaultSG },
	{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName, Input.shareSearchDefaultSG },
	{ Input.rev1userName, Input.rev1password, Input.rev1FullName, Input.shareSearchDefaultSG } };
	return users;
	}
	
	/**
	 * @Author
	 * @Description : To verify as a Reviewer user login, I will be able to search a
	 *              saved query based on search status 'COMPLETED' under Shared
	 *              folder
	 */
	@Test(description = "RPMXCON-47571", enabled = true, groups = { "regression" })
	public void verifyingAsReviewerApplyingCompleteStatus() {

		base.stepInfo("Test case Id: RPMXCON-47571 Saved search");
		base.stepInfo(
				"To verify as a Reviewer user login, I will be able to search a saved query based on search status 'COMPLETED' under Shared folder");

		// login as reviewer
		login.loginToSightLine(Input.rev1userName, Input.rev1password);
		base.stepInfo("logged in as : " + Input.rev1FullName);

		// selecting default security group and applying complete status
		saveSearch.navigateToSavedSearchPage();
		saveSearch.getSavedSearchGroupName(Input.shareSearchDefaultSG).waitAndClick(10);
		saveSearch.CheckStatus("COMPLETED");

		// logout
		login.logout();
	}

	/**
	 * @Author
	 * @Description : To verify , I will be able to search a saved query based on
	 *              search name in 'Shared with <Security Group>' search group
	 */
	@Test(description = "RPMXCON-47572", dataProvider = "SavedSearchwithUsers", enabled = true, groups = {
			"regression" })
	public void verifyUserAbleToSearchSavedQueryUnderSpecificSearchGroup(String username, String password,
			String fullName) throws InterruptedException {
		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-47572 Saved search");
		base.stepInfo(
				"To verify , I will be able to search a saved query based on search name in 'Shared with <Security Group>' search group");

		// Login as PA
		login.loginToSightLine(username, password);
		base.stepInfo("logged in as : " + fullName);

		// performing basic Content Search and Saving the Search
		session.basicContentSearch(Input.searchString2);
		session.saveSearchAtAnyRootGroup(searchName, Input.shareSearchDefaultSG);

		// performing search in saved search page to get the savedSearch saved under
		// specific search group
		saveSearch.selectSavedSearchTAb(searchName, Input.shareSearchDefaultSG, "No");
		softAssertion.assertEquals((boolean)saveSearch.getSearchName(searchName).isElementAvailable(5), true);
		softAssertion.assertAll();
		base.passedStep("Saved Search is displayed for Searched Name");

		// logout
		login.logout();
	}

	/**
	 * @Author
	 * @Description : RMU User - Verify that appropriate search Group appears under
	 *              the respective security Group on Saved Search Screen.
	 */
	@Test(description = "RPMXCON-48121", enabled = true, groups = { "regression" })
	public void verifyThatAppropriateSGAppearsUnderRespectiveSecurityGroup() throws Exception {
		UserManagement userManagement = new UserManagement(driver);
		SecurityGroupsPage securityGroupsPage = new SecurityGroupsPage(driver);
		String securityGroup = "securityGroup" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-48121 Saved search");
		base.stepInfo(
				"RMU User - Verify that appropriate search Group appears under the respective security Group on Saved Search Screen.");

		// login as PAU
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("logged in as : " + Input.pa2FullName);

		// Create security group
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(securityGroup);

		// access to security group to RMU
		userManagement.assignAccessToSecurityGroups(securityGroup, Input.rmu1userName);

		// logOut
		login.logout();

		// login as RMU
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("logged in as : " + Input.rmu2FullName);

		// creating new search group
		String newNode = saveSearch.createNewSearchGrp(Input.mySavedSearch);

		// selecting created security group and verifying the presence of newly created
		// search group
		base.selectsecuritygroup(securityGroup);
		saveSearch.verifyNodePresentInSG(Input.mySavedSearch, newNode);
		softAssertion.assertEquals((boolean)saveSearch.getSavedSearchGroupName(newNode).isElementAvailable(5), false);

		// selecting default security group and verifying the presence of newly created
		// search group
		base.selectsecuritygroup(Input.securityGroup);
		saveSearch.verifyNodePresentInSG(Input.mySavedSearch, newNode);
		softAssertion.assertEquals((boolean)saveSearch.getSavedSearchGroupName(newNode).isElementAvailable(5), true);
		softAssertion.assertAll();

		// deleting search group
		saveSearch.deleteNode(Input.mySavedSearch, newNode);

		// logout
		login.logout();

		// login as PAU
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("logged in as : " + Input.pa2FullName);

		// deleting the newly created Security group
		securityGroupsPage.deleteSecurityGroups(securityGroup);

		// logOut
		login.logout();

	}

	/**
	 * @Author
	 * @Description : Verify that correct number of documents appears when user
	 *              Selects \"View In DocView\" action from Advanced Search Screen
	 */
	@Test(description = "RPMXCON-57091", enabled = true, groups = { "regression" })
	public void verifyingNumbersOfDocumentsInDocViewWithPureHitCount() throws InterruptedException {
		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();
		DocViewPage docView = new DocViewPage(driver);
		base.stepInfo("Test case Id: RPMXCON-57091 Saved search");
		base.stepInfo(
				"Verify that correct number of documents appears when user Selects \"View In DocView\" action from Advanced Search Screen");
		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// performing basic Content Search and Saving the Search
		session.basicContentSearch(Input.searchString2);
		session.saveSearch(searchName);

		// selecting the saved Search and clicking Edit button
		saveSearch.navigateToSavedSearchPage();
		saveSearch.selectSavedSearch(searchName);
		saveSearch.getSavedSearchEditButton().waitAndClick(5);
		base.stepInfo("Edit button clicked in SavedSaerch Page");

		// getting the pureHit count
		int pureHit = Integer.parseInt(session.getPureHitsCount().getText());

		// getting the Document count in DocView page
		session.ViewInDocView();
		int docCount = docView.verifyingDocCount();

		// comparing the pureHit with Document count
		softAssertion.assertEquals(pureHit, docCount);
		softAssertion.assertAll();
		base.passedStep("pure Hit Count equals to Document Count in DocView Page");

		// logOut
		login.logout();

	}

	/**
	 * @throws Exception
	 * @Author :
	 * @Description : To verify, As an user login into the Application, I will be
	 *              able to see all the searches upload from xls sheet as batch
	 *              search upload in selected search group
	 */
	@Test(description = "RPMXCON-47730", enabled = true, groups = { "regression" })
	public void verifyUserAbleToUploadBatchFileInSelectedSearchGroup() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-47730");
		base.stepInfo(
				"To verify, As an user login into the Application, I will be able to see all the searches upload from xls sheet as batch search upload in selected search group");
		String renameFile = saveSearch.renameFile(Input.WPbatchFile);
		String columnHeading = "Search Name";
		String fileLocation = System.getProperty("user.dir") + Input.WPbatchFile + renameFile;

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// creating new node
		String newNode = saveSearch.createNewSearchGrp(Input.mySavedSearch);

		// selecting the created node and uploading the excel file
		saveSearch.selectNodeUnderSpecificSearchGroup(Input.mySavedSearch, newNode);
		List<String> searchNames = saveSearch.batchFileUpload(Input.WPbatchFile, renameFile);
		System.out.println("First step");

		// collecting search Names in excel sheet
		List<String> excelData = base.readExcelColumnData(fileLocation, columnHeading);
		System.out.println("data in excel sheet are readed");
		base.passedStep("data in excel sheet are readed");

		// comparing the search Names in excel sheet with search names in saved search
		// page
		base.compareListViaContains(searchNames, excelData);
		System.out.println("Search Names in excel sheet are Matches with the Search Names in Saved Search Page");
		base.passedStep("Search Names in excel sheet are Matches with the Search Names in Saved Search Page");

		// deleting the created node
		driver.scrollPageToTop();
		driver.Navigate().refresh();
		saveSearch.deleteNode(Input.mySavedSearch, newNode);
		
		// logOut
		login.logout();

	}

	/**
	 * @Author :
	 * @Description : Verify that correct number of documents appears when user
	 *              Selects \"Bulk Release\" action from Advanced Search Screen
	 */

	@Test(description = "RPMXCON-47961", enabled = true, groups = { "regression" })
	public void verifyPurehitCountWithDocumentCountInBulkRelease() throws Exception {

		base.stepInfo("Test case Id:RPMXCON-47961");
		base.stepInfo(
				"Verify that correct number of documents appears when user Selects \"Bulk Release\" action from Advanced Search Screen");

		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// searching and saving in advanced search
		int pureHit = session.advancedContentSearch(Input.searchString1);
		session.saveAdvancedSearchQuery(searchName);

		// selecting and editing the saved search
		saveSearch.savesearch_Edit(searchName);

		// performing bulk release
		int finalCount = session.verifyBulk_releaseCount(Input.securityGroup);

		// comparing the pureHit count and final document count
		softAssertion.assertEquals(pureHit, finalCount);
		softAssertion.assertAll();
		base.passedStep("pureHit count matches with the final count in bulk release");
	}

	/**
	 * @Author
	 * @Description : To verify, as an user login into the Application, I will be
	 *              able to export the saved search from search group in csv format
	 */

	@Test(description = "RPMXCON-47732", enabled = true, groups = { "regression" })
	public void verifyAbleToExportSavedSearchInCsvFormat() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-47732");
		base.stepInfo(
				"To verify, as an user login into the Application, I will be able to export the saved search from search group in csv format");

		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();
		List<String> metadata = new ArrayList<String>();
		metadata.add(Input.metaDataName);
		metadata.add(Input.docFileExt);
		String StyletoChoose = "CSV";
		String fieldTypeToChoose = "[,] 044";
		String TextTypetoChoose = "[\"] 034";
		String NewLineTypetoChoose = "[;] 059";
		String DateStyleTypetoChoose = "MM/DD/YYYY";

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// metadata search is performed and saved
		session.basicSearchWithMetaDataQuery(Input.metaDataCustodianNameInput, Input.metaDataName);
		session.saveSearch(searchName);

		// selecting the savedSearch
		saveSearch.navigateToSavedSearchPage();
		saveSearch.selectSavedSearch(searchName);

		// performing Export action & verify format
		saveSearch.getSavedSearchExportButton().waitAndClick(5);
		base.stepInfo("Export button clicked");
		int bgCount = saveSearch.configureExportPopup(metadata, StyletoChoose, fieldTypeToChoose, TextTypetoChoose,
				NewLineTypetoChoose, DateStyleTypetoChoose);
		saveSearch.downloadExportFile(bgCount, "csv");
		base.passedStep("able to export the saved search in csv format and download the File");

	}

	/**
	 * @Author
	 * @Description : To Verify, In Saved search page when user click on any of the
	 *              sub folder under \"My Search\" and select execute, it will
	 *              execute all the search query as Admin Login
	 */

	@Test(description = "RPMXCON-57017", enabled = true, groups = { "regression" })
	public void verifyUserAbleToExecuteSubFolderInMySearch() throws InterruptedException {
		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-57017");
		base.stepInfo(
				"To Verify, In Saved search page when user click on any of the sub folder under \"My Search\" and select execute, it will execute all the search query as Admin Login");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// create node under my saved search
		String newNode = saveSearch.createNewSearchGrp(Input.mySavedSearch);

		// performing search and saving under created node
		session.basicContentSearch(Input.searchString2);
		session.saveSearchInNewNode(searchName, newNode);

		// selecting the node and executing
		saveSearch.selectNodeUnderSpecificSearchGroup(Input.mySavedSearch, newNode);
		saveSearch.performExecute();
		base.passedStep("All the Search Query under selected folder is Executed");

		// delete the node
		saveSearch.deleteNode(Input.mySavedSearch, newNode);

		// logout
		login.logout();

	}

	/**
	 * @Author
	 * @Description : Verify Rename action for any of the shared folders on saved
	 *              search screen
	 */

	@Test(description = "RPMXCON-57023", dataProvider = "SavedSearchwithUsersAndShareGroup", enabled = true, groups = {
			"regression" })
	public void verifyRenameActionForSharedFolder(String username, String password, String fullName, String sharedGroup)
			throws InterruptedException, Exception {
		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-57023");
		base.stepInfo("Verify Rename action for any of the shared folders on saved search screen");

		// Login as User
		login.loginToSightLine(username, password);
		base.stepInfo("logged in as : " + fullName);

		// creating the node and saving the searchQuery and shared with shared Group
		String newNode = saveSearch.createNewSearchGrp(Input.mySavedSearch);
		session.basicContentSearch(Input.searchString2);
		session.saveSearchInNodeUnderGroup(searchName, newNode, Input.mySavedSearch);
		saveSearch.navigateToSavedSearchPage();
		saveSearch.shareSavedNodeWithDesiredGroup(newNode, sharedGroup);
		saveSearch.getCreatedNode(newNode).waitAndClick(20);
		saveSearch.deleteFunctionality();

		// selecting the shared group and verifying whether it highlighted
		saveSearch.selectRootGroupTab(sharedGroup);
		saveSearch.navigateToSavedSearchPage();
		String bgcolor = saveSearch.currentClickedNode().GetCssValue("background");
		String splitTerm = bgcolor.substring(bgcolor.indexOf("(") + 1, bgcolor.indexOf(")"));
		String color = base.rgbTohexaConvertor_Optional(null, false, splitTerm);
		base.textCompareEquals(color, Input.selectionHighlightColor, "selected search group get highlighted",
				"selected search group is not getting highlighted");

		// selecting the subnode under shared group
		saveSearch.selectNodeUnderSpecificSearchGroup(sharedGroup, newNode);
		saveSearch.deleteFunctionality();

		// verifying whether the rename option is disabled when shared group is selected
		saveSearch.selectRootGroupTab(sharedGroup);
		softAssertion.assertEquals((boolean)saveSearch.getRenameButtonDisabled().isElementAvailable(5), true);
		softAssertion.assertAll();
		base.passedStep("Rename action should is disabled when user selects any Shared folder");

		// logout
		login.logout();
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
		softAssertion = new SoftAssert();
		session = new SessionSearch(driver);
		saveSearch = new SavedSearch(driver);
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		Reporter.setCurrentTestResult(result);
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			login.logoutWithoutAssert();
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
		System.out.println("******TEST CASES FOR Batch Redactions EXECUTED******");
		try {
//				login.clearBrowserCache();
		} catch (Exception e) {
			// no session avilable

		}
	}

}
