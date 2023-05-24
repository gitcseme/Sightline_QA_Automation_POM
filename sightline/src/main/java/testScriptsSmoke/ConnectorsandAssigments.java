package testScriptsSmoke;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.HashMap;

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
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.ClientsPage;
import pageFactory.CollectionPage;
import pageFactory.DataSets;
import pageFactory.DocExplorerPage;
import pageFactory.IngestionPage_Indium;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SchedulesPage;
import pageFactory.SessionSearch;
import pageFactory.SourceLocationPage;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;

public class ConnectorsandAssigments {
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Input in;
	AssignmentsPage agnmt;
	SessionSearch search;
	KeywordPage keyword;
	SoftAssert softAssertion;
	DocExplorerPage DocExpPage;
	ClientsPage clientsPage;
	SavedSearch savedSearch;
	TagsAndFoldersPage tagPage;
	UserManagement userManage;
	Utility utility;
	DataSets dataSets;
	CollectionPage collection;
	SourceLocationPage source;
	
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		in = new Input();
		in.loadEnvConfig();
	}
	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		search = new SessionSearch(driver);
		agnmt = new AssignmentsPage(driver);
		savedSearch = new SavedSearch(driver);
		tagPage = new TagsAndFoldersPage(driver);
		softAssertion = new SoftAssert();
		dataSets = new DataSets(driver);
		collection = new CollectionPage(driver);
		source = new SourceLocationPage(driver);

	}
	
	@Test(description = "RPMXCON-59201", enabled = true, groups = { "regression" })
	public void verifyAfterEditingAssignGroup() throws InterruptedException {
		String cascadeAsgnGrpName = "CascadeAssgnGrp" + Utility.dynamicNameAppender();
		String assignment = "Assignment" + Utility.dynamicNameAppender();
		String cascadeSettings_yes = "Yes";

		softAssertion = new SoftAssert();
		loginPage = new LoginPage(driver);
		agnmt = new AssignmentsPage(driver);
		search = new SessionSearch(driver);

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-59201 Assignments Sprint-10");
		baseClass.stepInfo(
				"Verify that after editing assignment group with cascading, changes should reflect in its respective assignment");

		// create Assignmnet group with Draw Toggle Disabled
		agnmt.navigateToAssignmentsPage();
		agnmt.createCascadeNonCascadeAssgnGroup_withoutSave(cascadeAsgnGrpName, cascadeSettings_yes);
		agnmt.toggleEnableOrDisableOfAssignPage(false, true, agnmt.getAssgnGrp_Create_DrawPooltoggle(),
				"Draw From Pool", true);

		// Create Assignment in Assignment Group
		agnmt.selectAssignmentGroup(cascadeAsgnGrpName);
		agnmt.createAssignmentFromAssgnGroup(assignment, Input.codeFormName);

		// Enable Draw Toggle in Assign Group
		agnmt.editAssgnGrp(cascadeAsgnGrpName, "Yes");
		driver.waitForPageToBeReady();
		agnmt.toggleEnableOrDisableOfAssignPage(true, false, agnmt.getAssgnGrp_Create_DrawPooltoggle(),
				"Draw From Pool", true);

		// verify Draw Toggle is Enabled in Assignment
		agnmt.selectAssignmentToView(assignment);
		baseClass.waitForElement(agnmt.getAssignmentActionDropdown());
		agnmt.getAssignmentAction_EditAssignment().waitAndClick(3);
		agnmt.VerifyDrawPoolToggleEnabled();

		// Delete Assign group and assign
		agnmt.navigateToAssignmentsPage();
		agnmt.deleteAssignmentFromSingleAssgnGrp(cascadeAsgnGrpName, assignment);
		agnmt.DeleteAssgnGroup(cascadeAsgnGrpName);
		loginPage.logout();

	}
	
	@Test(description ="RPMXCON-46980",dataProvider = "Users", groups = { "regression" } )
	public void verifyCombinedSearch(String UserName, String PassWord) throws InterruptedException, AWTException {
		baseClass.stepInfo("Validate search with combination of Content/Metadata, " + "Audio, Conceptual, WorkProduct");
		baseClass.stepInfo("Test case Id: RPMXCON-46980");
		String TagName = "ASTag" + Utility.dynamicNameAppender();
		String FolderName = "ASFolder" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(UserName, PassWord);
		if (UserName.equals(Input.rmu1userName)) {
			search.advancedContentSearch(Input.searchString2);
			search.bulkTag(TagName);
			baseClass.selectproject();
			search.advancedContentSearch(Input.searchString2);
			search.bulkFolder(FolderName);
		}
		try {
			int ExpectedPureHit = search.verifyCombinedSearch(FolderName, "folder", "no", "AND", "NOT", "OR");
			baseClass.stepInfo("PureHit count after combined search " + ExpectedPureHit);
			int ExpectedPureHit3 = search.verifyCombinedSearch(TagName, "tag", "yes", "OR", "NOT", "OR");
			baseClass.stepInfo("PureHit count after combined search " + ExpectedPureHit3);
			softAssertion.assertEquals(Input.expectedCombinedSearchHits1, ExpectedPureHit3);
			softAssertion.assertEquals(Input.expectedCombinedSearchHits2, ExpectedPureHit);
			softAssertion.assertAll();
			baseClass.passedStep(
					"Sucessfully verified search with combination of Content/Metadata, Audio, Conceptual, WorkProduct");
		} catch (Exception e) {
			e.printStackTrace();
			baseClass.failedStep(
					"Failed to verify search with combination of Content/Metadata, Audio, Conceptual, WorkProduct");
		}
		loginPage.logout();
	}
	@DataProvider(name = "Users")
	public Object[][] CombinedSearchwithUsers() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password }, { Input.pa1userName, Input.pa1password },
				{ Input.rev1userName, Input.rev1password } };
		return users;
	}
	
	@Test(description ="RPMXCON-49860",enabled = true, groups = { "regression" } )
	public void validateSecurityGroupSharingSearch() throws InterruptedException, ParseException {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-49860 SavedSearch Sprint 08");
		baseClass.stepInfo("Validate sharing search with Default Security Group");
		String SGtoShare = Input.shareSearchDefaultSG;

		String searchName = "Search Name" + Utility.dynamicNameAppender();

		search.basicContentSearch(Input.testData1);
		search.saveSearch(searchName);
		savedSearch.navigateToSSPage();
		savedSearch.savedSearch_SearchandSelect(searchName, "Yes");
		String searchID = savedSearch.SavedSearchSearchID(SGtoShare, searchName);
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		savedSearch.navigateToSSPage();
		savedSearch.getSavedSearchGroupName(SGtoShare).waitAndClick(5);
		savedSearch.verifySearchContents(searchName, searchID, true, true, null, null, "No");
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		savedSearch.navigateToSSPage();
		savedSearch.getSavedSearchGroupName(SGtoShare).waitAndClick(5);
		savedSearch.verifySearchContents(searchName, searchID, true, true, null, null, "No");
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		savedSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");
		savedSearch.deleteSearch(searchName, SGtoShare, "Yes");
		loginPage.logout();
	}
	@Test(description = "RPMXCON-49855", enabled = true, groups = { "regression" })
	public void performAnyActionSharedPAU() throws InterruptedException, ParseException {
		String SearchName = "SaveSearch_" + Utility.dynamicNameAppender();
		String renamedSearchName = "Re-SaveSearch_" + Utility.dynamicNameAppender();
		int pureHits;

		baseClass.stepInfo("Test case Id: RPMXCON-49855 - Sprint 06");
		baseClass.stepInfo(
				"Validate performing any action on searches/groups from the shared with PAU by any other PAU user");

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Landed on Saved Search
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();

		// create new searchGroup/Search and Save Search
		String newNode = savedSearch.createSearchGroupAndReturn(SearchName, "PA", "Yes");
		pureHits = search.basicContentSearch(Input.searchString1);
		search.saveSearchInNewNode(SearchName, newNode);
		savedSearch.shareSavedSearchFromNode(SearchName, Input.shareSearchPA);

		// logout
		loginPage.logout();

		// Login as PA 2
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		baseClass.stepInfo("Loggedin As : " + Input.pa2FullName);

		// Landed on Saved Search
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		savedSearch.getSavedSearchGroupName(Input.shareSearchPA).waitAndClick(10);
		savedSearch.selectNode1(newNode);
		driver.waitForPageToBeReady();
		String modifiedSearchGroup = savedSearch.renameSearchGroup(newNode);
		baseClass.CloseSuccessMsgpopup();
		savedSearch.savedSearch_SearchandSelect(SearchName, "Yes");
		savedSearch.renameSavedSearch(renamedSearchName);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		savedSearch.verifySharedNode(Input.shareSearchPA, modifiedSearchGroup, " ", true, renamedSearchName, false);
		baseClass.stepInfo("PAU should allowed to perform any action on SS/SG from the ribbon");

		// logout
		loginPage.logout();

	}
	@Test(description = "RPMXCON-57406", enabled = true, dataProvider = "PAandRMU", groups = { "regression" })
	public void manageScheduleCheck(String userName, String password, String fullName)
			throws InterruptedException, ParseException {
		String searchName = "SS" + Utility.dynamicNameAppender();
		ElementCollection availableSearchName;
		SchedulesPage schedulePage=new SchedulesPage(driver);
		// will login to the application by entering RMU credentials
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Tescase ID : RPMXCON-57406 Saved search - Sprint 06");
		baseClass.stepInfo(
				"Verify that after modification of any search query under My Searches   from Manage Schedule - Modified Schedule functionality is working proper in Saved searches");

		// Create node - Search - Save
		String newNode = savedSearch.createSearchGroupAndReturn(searchName, "PA", "Yes");
		search.basicContentSearch(Input.testData1);
		search.saveSearchInNodewithChildNode(searchName, newNode);
		this.driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		savedSearch.selectNode1(newNode);

		// after selecting the query will schedule for current time +02 Minutes
		baseClass.stepInfo("schedule for current time +02 Minutes");
		savedSearch.scheduleSavedSearchInMinute(searchName, 2);

		// Navigate to manage-schedules page and edit the time to +03 minutes
		baseClass.stepInfo("edit the time to +03 minutes");
		schedulePage.editScheduledSaveSearch(searchName, 3);

		// --need to wait and check status
		schedulePage.verifyScheduledTime(searchName);
		schedulePage.checkStatusComplete(searchName);

		baseClass.stepInfo(
				"Scheduler should gets executed once last modified time over.     2. It should not execute twice. because we modified earlier time.  ");
		availableSearchName = schedulePage.getSearchNamesfromList(searchName);
		int listSize = availableSearchName.size();
		System.out.println(listSize);
		if (listSize > 1) {
			baseClass.failedStep("Number of times executed : " + listSize);
		} else {
			baseClass.passedStep("Number of times executed : " + listSize);
		}

		// Delete Created Node
		savedSearch.deleteNode(Input.mySavedSearch, newNode);

		loginPage.logout();
	}
	@DataProvider(name = "PAandRMU")
	public Object[][] PAandRMU() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName } };
		return users;
	}
	
	@Test(description = "RPMXCON-48536", enabled = true, groups = { "regression" })
	public void verifyBatchUpload() throws Exception {
		String advanceSearch = "Basic Work Product";
		String basicsearch = "Basic Content Search without Family Members";
		String File = savedSearch.renameFile(Input.WPbatchFile);
		String nearDupe = "Near Duplicate Count";

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-48536  Saved Search");
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		savedSearch.uploadWPBatchFile(File);
		savedSearch.getDocCountAndStatusOfBatch(advanceSearch, nearDupe, true);

		driver.waitForPageToBeReady();
		savedSearch.getDocCountAndStatusOfBatch(basicsearch, nearDupe, true);

		// Delete Uploaded File
		driver.Navigate().refresh();
		savedSearch.getMySeraches().waitAndClick(10);
		baseClass.waitForElement(savedSearch.getSelectUploadedFile(File));
		savedSearch.getSelectUploadedFile(File).waitAndClick(20);

		savedSearch.deleteFunctionality();
		loginPage.logout();
	}
	@Test(description ="RPMXCON-52426",alwaysRun = true, groups = { "regression" } )
	public void createUserWithDifferentProjectAndRole() throws Exception {
		baseClass = new BaseClass(driver);
		String firstName = Input.randomText + Utility.dynamicNameAppender();
		String lastName = Input.randomText + Utility.dynamicNameAppender();
		String role = "Review Manager";
		String role2 = "Reviewer";
		String project1 = Input.projectName;
		String project2 = Input.additionalDataProject;
		String emailId = Input.randomText + Utility.dynamicNameAppender() + "@consilio.com";
		baseClass.stepInfo("Test case Id: RPMXCON-52426");
		utility = new Utility(driver);
		baseClass.stepInfo(
				"#### To verify when assigns user to different project with different role of existing project ####");
		userManage = new UserManagement(driver);
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("Logged in as User: " + Input.sa1userName);
		Reporter.log("Logged in as User: " + Input.sa1password);

		baseClass.stepInfo("Create new user");
		userManage.createNewUser(firstName, lastName, role, emailId, " ", project1);

		baseClass.stepInfo(
				"Create new user with same username with different project and different role as previously created");
		userManage.createNewUser(firstName, lastName, role2, emailId, " ", project2);
	

		baseClass.passedStep(
				"Created new user with same username with different project and different role as previously created successfully..");

		baseClass.stepInfo("Delete added users");
		userManage.deleteAddedUser(firstName);
		
		loginPage.logout();
	}
	@Test(description ="RPMXCON-52773",alwaysRun = true, groups = { "regression" })
	public void createNewUserForDomain() throws Exception {
		baseClass = new BaseClass(driver);
		String firstName = Input.randomText + Utility.dynamicNameAppender();
		String lastName = Input.randomText + Utility.dynamicNameAppender();
		String role = "Domain Administrator";
		String emailId = Input.randomText + Utility.dynamicNameAppender() + "@consilio.com";
		baseClass.stepInfo("Test case Id: RPMXCON-52773");
		utility = new Utility(driver);
		baseClass.stepInfo("Verify that System Admin can create user with Domain Admin role");
		userManage = new UserManagement(driver);
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("Logged in as User: " + Input.sa1userName);
		Reporter.log("Logged in as User: " + Input.sa1password);

		baseClass.stepInfo("Create new user for domain administration");
		userManage.createNewUser(firstName, lastName, role, emailId, " ", Input.projectName);

		baseClass.stepInfo("Domain admin role displayed in dropdown field");
		baseClass.passedStep("Created new user with Domain admin rule");

		baseClass.waitTime(5);
		baseClass.stepInfo("Delete added users");
		userManage.deleteAddedUser(firstName);
		loginPage.logout();

	}
	@Test(description = "RPMXCON-61034", dataProvider = "PAandRMU", enabled = true, groups = { "regression" })
	public void verifyCanInitiateCollection(String username, String password, String fullname) throws Exception {
		HashMap<String, String> collectionData = new HashMap<>();

		String collectionEmailId = Input.collectionDataEmailId;
		String collectiondataListVal = Input.collectionDatalistval;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Inbox";
		String headerListDataSets[] = { "Collection Id", "Collection Status", "Error Status" };
		String[] statusListToVerify = { Input.creatingDSstatus, Input.retreivingDSstatus, Input.virusScanStatus,
				Input.copyDSstatus };
		String[] statusList = { "Completed" };
		String[][] userRolesData = { { username, fullname, "SA" } };
		String collectionName = "Collection" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id: RPMXCON-61034 - O365");
		baseClass.stepInfo("Verify that user can initiate collection on \"Manage Screen\" screen");

		// Login and Pre-requesties
		loginPage.loginToSightLine(username, password);

		// Pre-requesties - Access verification
		baseClass.stepInfo("Collection Access Verification");
		userManage.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);

		// Start Collection
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collectionData = collection.createNewCollection(collectionData, collectionName, true, null, false);

		// Add Dataset
		collection.fillingDatasetSelection("Button",collectiondataListVal, firstName, lastName, collectionEmailId, selectedApp,
				collectionData, collectionName, 3, selectedFolder, false, false, false, "-", true, true, "Save", "");

		// Initiate collection
		collection.clickOnNextAndStartAnCollection();

		// verify Completed Status
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collection.verifyExpectedCollectionStatus(false, headerListDataSets, collectionName, statusListToVerify, 10,
				true, false, "", "");

		// Completed status check
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionName, statusList, 10);

		// Logout
		loginPage.logout();
	}
	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			System.out.println("Sessions already closed");
		}
	}

	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		baseClass = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}
}
