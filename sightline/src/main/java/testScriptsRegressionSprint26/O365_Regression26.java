package testScriptsRegressionSprint26;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.HashMap;
import java.util.concurrent.Callable;

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
import pageFactory.CollectionPage;
import pageFactory.DataSets;
import pageFactory.LoginPage;
import pageFactory.SourceLocationPage;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class O365_Regression26 {

	Driver driver;
	LoginPage login;
	BaseClass base;
	UserManagement userManagement;
	DataSets dataSets;
	CollectionPage collection;
	SourceLocationPage source;
	SoftAssert softassert;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		Input in = new Input();
		in.loadEnvConfig();

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
		userManagement = new UserManagement(driver);
		dataSets = new DataSets(driver);
		collection = new CollectionPage(driver);
		source = new SourceLocationPage(driver);
	}

	@DataProvider(name = "AllTheUsers")
	public Object[][] AllTheUsers() {
		Object[][] users = { { Input.sa1userName, Input.sa1password }, { Input.pa1userName, Input.pa1password },
				{ Input.rmu1userName, Input.rmu1password }, { Input.rev1userName, Input.rev1password } };
		return users;
	}

	@DataProvider(name = "PaAndRmuUser")
	public Object[][] PaAndRmuUser() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, "Project Administrator" },
				{ Input.rmu1userName, Input.rmu1password, "Review Manager" },

		};
		return users;
	}

	/**
	 * @author Mohan Date:16/11/2022 RPMXCON-61632
	 * @throws Exception
	 * @Description Verify that processing icon should be displayed for Node
	 *              select/unselect on folder tree
	 */
	@Test(description = "RPMXCON-61632", enabled = true, groups = { "regression" })
	public void verifyProcessingIconDisplayedForNodeSelectUnselectOnFolderTree() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-61632");
		base.stepInfo("Verify that processing icon should be displayed for Node select/unselect on folder tree ");
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };
		HashMap<String, String> collectionData = new HashMap<>();
		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String collectionName = "Collection" + Utility.dynamicNameAppender();

		// Login As PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		// Login as User and verify Module Access
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, Input.pa1password);

		// Create new Collection
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Click create New Collection with datasets
		collectionData = collection.createNewCollection(collectionData, collectionName, true, null, false);
		collection.addDataSetWithHandles("Button", firstName, lastName, collectionEmailId, selectedApp, collectionData,
				collectionName, 3);

		// validate processiong Icon is diplayed
		collection.loadingIconOnDataSetPage();

		login.logout();

	}

	/**
	 * @author
	 * @throws Exception
	 * @Date: 11/18/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that in Collection Summary report change label, Dest
	 *              Path to “Destination Path”. RPMXCON-61636
	 */
	@Test(description = "RPMXCON-61636", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyLabelChangeAndEMptyFilterValuesFromDataGenerated(String userName, String password, String role)
			throws Exception {

		HashMap<String, String> colllectionData = new HashMap<>();
		String[][] userRolesData = { { userName, role, "SA" } };
		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Inbox";
		String headerListDS[] = { Input.collectionDataHeader1, Input.collectionDataHeader2, Input.collectionDataHeader3,
				Input.collectionDataHeader5, Input.collectionDataHeader4, Input.collectionDataHeader6 };
		String headerListDataSets[] = { "Collection Id", "Collection Status", "Error Status" };
		String[] statusListToVerify = { Input.creatingDSstatus, Input.retreivingDSstatus, Input.virusScanStatus,
				Input.copyDSstatus };
		String[] statusList = { "Completed" };
		String expectedFileFormat = "xlsx";
		String dataSourceName = "Automation" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-61636 - O365");
		base.stepInfo("Verify that in Collection Summary report change label, Dest Path to “Destination Path”");
		base.failedMessage("Make sure E-mail source folder has datas - If not will end up with Data retrival error");
		base.stepInfo("**Step-1 Login as Project Admin/RMU **");
		base.stepInfo("**Step-2 Pre-requisites: Collections should be added **");

		// Login and Pre-requesties
		login.loginToSightLine(userName, password);
		base.stepInfo("User Role : " + role);

		// Pre-requesties - Access verification
		base.stepInfo("Collection Access Verification");
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);

		// navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Click create New Collection
		collection.performCreateNewCollection();

		// Select source and Click create New Collection
		String srcLocation = collection.selectSourceFromTheListAvailable();

		// click created source location and verify navigated page
		HashMap<String, String> collectionInfoPage = collection.verifyCollectionInfoPage(srcLocation, dataSourceName,
				false);

		// Initiate collection process
		collection.selectInitiateCollectionOrClickNext(true, true, true);

		// DataSet creation
		collection.fillinDS(dataSourceName, firstName, lastName, collectionEmailId, selectedApp, collectionInfoPage,
				selectedFolder, headerListDS, "Button", 3, false, "Save", false, "");

		// Start A Collection
		collection.clickOnNextAndStartAnCollection();
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		driver.waitForPageToBeReady();

		// Verify Collection presence with expected Status
		collection.verifyExpectedCollectionStatus(false, headerListDataSets, dataSourceName, statusListToVerify, 10,
				true, false, "", "");

		// Completed status check
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, dataSourceName, statusList, 10);
		driver.waitForPageToBeReady();

		// Initial Notification count
		int Bgcount = base.initialBgCount();

		// Download Report
		base.stepInfo("**Step-3 Download report from Manage collections page and check the label **");
		collection.clickDownloadReportLink(dataSourceName, headerListDataSets, "Error Status", false, "");
		driver.waitForPageToBeReady();

		// Check NotificationCount
		base.checkNotificationCount(Bgcount, 1);
		base.notificationSelection("", false);
		base.waitTime(5);// for abnormal load time while downloading file

		// Format validation
		String fileName = base.GetLastModifiedFileName();
		base.validateFileFormat(fileName, expectedFileFormat);

		// Label Verification
		base.stepInfo("**Step-4 Check the filters value in case of no filters **");
		colllectionData = collection.testreadExcelData(fileName, 0, 0, "Collection Summary", "", false);
		base.textCompareEquals(collectionInfoPage.get(dataSourceName), colllectionData.get("Collection Id"),
				"Reading the data from " + colllectionData.get("Collection Id") + " report", "Export data Mis-match");

		// Destination path label verification
		base.printResutInReport(base.checkExpectedKeyisPresent(colllectionData, "Destination Path"),
				"Report is downloaded and label changed from Dest Path to “Destination Path”",
				"Report is downloaded and label not changed from Dest Path to “Destination Path”", "Pass");

		// File Verification Dataset
		colllectionData = collection.testreadExcelData(fileName, 0, 0, "Dataset Summary", "", false);
		base.printStringHashMapDetails(colllectionData);

		base.stepInfo("-------------DateRange Filter Value-------------------");
		base.textCompareEquals("", colllectionData.get("DateRange Filter"),
				"DateRange Filter is empty when no input is given",
				"DateRange Filter is not empty when no input is given");

		base.stepInfo("-------------Keyword Filter Value-------------------");
		base.textCompareEquals("", colllectionData.get("Keyword Filter"),
				"Keyword Filter is empty when no input is given", "Keyword Filter is not empty when no input is given");

		// Delete downloaded file
		base.stepInfo("Initiating delete for downloaded file");
		Files.deleteIfExists(Paths.get(fileName));

		// Logout
		login.logout();

	}

	/**
	 * @author
	 * @throws Exception
	 * @Date: 11/18/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that destination path should be displayed as relative
	 *              path in collection module. RPMXCON-61634
	 */
	@Test(description = "RPMXCON-61634", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyRelativeDestionPathInExport(String userName, String password, String role) throws Exception {

		HashMap<String, String> colllectionData = new HashMap<>();
		String[][] userRolesData = { { userName, role, "SA" } };
		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Inbox";
		String headerListDS[] = { Input.collectionDataHeader1, Input.collectionDataHeader2, Input.collectionDataHeader3,
				Input.collectionDataHeader5, Input.collectionDataHeader4, Input.collectionDataHeader6 };
		String headerListDataSets[] = { "Collection Id", "Collection Status", "Error Status" };
		String[] statusListToVerify = { Input.creatingDSstatus, Input.retreivingDSstatus, Input.virusScanStatus,
				Input.copyDSstatus };
		String[] statusList = { "Completed" };
		String expectedFileFormat = "xlsx";
		String dataSourceName = "Automation" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-61634 - O365");
		base.stepInfo("Verify that destination path should be displayed as relative path in collection module");
		base.failedMessage("Make sure E-mail source folder has datas - If not will end up with Data retrival error");

		// Login and Pre-requesties
		base.stepInfo("**Step-1 Login as Project Admin/RMU **");
		login.loginToSightLine(userName, password);
		base.stepInfo("User Role : " + role);
		base.stepInfo("**Step-2 Pre-requisites: Collections should be added **");

		// Pre-requesties - Access verification
		base.stepInfo("**Step-3 Go to Datasets > Collections page **");
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);

		// navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Click create New Collection
		collection.performCreateNewCollection();

		// Select source and Click create New Collection
		String srcLocation = collection.selectSourceFromTheListAvailable();

		// click created source location and verify navigated page
		base.stepInfo(
				"**Step-5 While performing New collection ,check the Destination path from Collection Information tab **");
		HashMap<String, String> collectionInfoPage = collection.verifyCollectionInfoPage(srcLocation, dataSourceName,
				false);
		String destinationPath = collectionInfoPage.get("DestinationPath");

		// initiate collection process
		collection.selectInitiateCollectionOrClickNext(true, true, true);

		// DataSet creation
		collection.fillinDS(dataSourceName, firstName, lastName, collectionEmailId, selectedApp, collectionInfoPage,
				selectedFolder, headerListDS, "Button", 3, false, "Save", false, "");

		// Compare path with Summary details
		driver.waitForPageToBeReady();
		collection.nextAction("DataSet");
		driver.waitForPageToBeReady();
		base.stepInfo(
				"**Step-6 While performing New collection ,  check the Destination path from Collection Summary tab **");
		String destinationFromSummaryPage = collection.getDestinationPathLocation("Destination Path:").getText();
		base.textCompareEquals(destinationPath, destinationFromSummaryPage,
				"Expected Relative Destination path is displayed in both Collection info and Summary Page",
				"Expected Relative Destination path is not displayed in both Collection info and Summary Page");

		// Save As Draft
		collection.collectionSaveAsDraft();
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Verify Collection presence
		collection.verifyExpectedCollectionIsPresentInTheGrid(headerListDataSets, dataSourceName, "Draft", true, false,
				"");

		// Start Collection
		collection.collectionAction(dataSourceName, "Start Collection", false, "", false, "");
		driver.waitForPageToBeReady();

		// Verify Collection presence with expected Status
		collection.verifyExpectedCollectionStatus(false, headerListDataSets, dataSourceName, statusListToVerify, 10,
				true, false, "", "");

		// Completed status check
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, dataSourceName, statusList, 10);
		driver.waitForPageToBeReady();

		// Initial Notification count
		int Bgcount = base.initialBgCount();

		// Download Report
		base.stepInfo(
				"**Step-7 Click on Download Final report from collection page  and check the Destination path from Collection Summary report **");
		collection.clickDownloadReportLink(dataSourceName, headerListDataSets, "Error Status", false, "");
		driver.waitForPageToBeReady();

		// Check NotificationCount
		base.checkNotificationCount(Bgcount, 1);
		base.notificationSelection("", false);
		base.waitTime(5);// for abnormal load time while downloading file

		// Format validation
		String fileName = base.GetLastModifiedFileName();
		base.validateFileFormat(fileName, expectedFileFormat);

		// Data Verification
		base.stepInfo("In downloaded report Check the filters value in case of no filters");
		colllectionData = collection.testreadExcelData(fileName, 0, 0, "Collection Summary", "", false);
		base.textCompareEquals(collectionInfoPage.get(dataSourceName), colllectionData.get("Collection Id"),
				"Reading the data from " + colllectionData.get("Collection Id") + " report", "Export data Mis-match");

		// Relative destination path validation
		base.textCompareEquals(destinationFromSummaryPage, colllectionData.get("Destination Path"),
				"Destination path is displayed as relative path in collection Summary module of Generated Export",
				"Destination path is not displayed as relative path in collection Summary module of Generated Export");

		// Delete downloaded file
		base.stepInfo("Initiating delete for downloaded file");
		Files.deleteIfExists(Paths.get(fileName));

		// Launch collection details
		base.stepInfo(
				"**Step-4 Click on the collection ID and check the Destination path from Collection Details pop up **");
		collection.clickDownloadReportLink(dataSourceName, headerListDataSets, "Collection Id", false, "");
		driver.waitForPageToBeReady();
		base.printResutInReport(base.ValidateElement_AbsenceReturn((collection.getCollectionInfoPopUpStatus())),
				"Opened : Collection Info popup", "No Collection Info popup", "Pass");

		// Verify Destination path in Collection info popup
		base.textCompareEquals(destinationPath, collection.getDestinationLocationInfoPopup().getText(),
				"Destination path is displayed as relative path in collection module as expected",
				"Destination path is displayed as relative path in collection module as expected");

		// Logout
		login.logout();

	}

	/**
	 * @author sowndarya Testcase No:RPMXCON-68767
	 * @Description:Verify that error message display and application does NOT
	 *                     accepts - when "Folder" Name entered with special
	 *                     characters < > & ‘
	 **/
	@Test(description = "RPMXCON-68767", enabled = true, groups = { "regression" })
	public void verifyErrorMsgFolderAndFoldergroup() throws Exception {
		base.stepInfo("RPMXCON-68768");
		base.stepInfo(
				"To Verify that error message display and application does NOT accepts - when \"Folder\" Name entered with special characters < > & ‘");
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in As : " + Input.pa1userName);
		String foldername = "Folder&‘" + Utility.dynamicRandomNumberAppender();
		String expected = "Special characters are not allowed.";

		// folder
		TagsAndFoldersPage tags = new TagsAndFoldersPage(driver);
		tags.navigateToTagsAndFolderPage();
		tags.createNewFolderNotSave(foldername);
		String folderError = tags.getFolderrrorMsg().getText();
		System.out.println(folderError);
		softassert = new SoftAssert();
		softassert.assertEquals(folderError, expected);

		// folder group
		String folderGroupName = "Folder&'Group" + Utility.dynamicRandomNumberAppender();
		tags.navigateToTagsAndFolderPage();
		driver.Navigate().refresh();
		base.waitForElement(tags.getFoldersTab());
		tags.getFoldersTab().waitAndClick(5);
		base.stepInfo("Tag Tab Clicked");
		base.waitForElement(tags.getAllFolderRoot());
		tags.getAllFolderRoot().waitAndClick(5);
		base.waitForElement(tags.getFolderActionDropDownArrow());
		tags.getFolderActionDropDownArrow().waitAndClick(5);
		base.waitForElement(tags.getNewFOlderGroupAction());
		tags.getNewFOlderGroupAction().waitAndClick(10);
		base.waitForElement(tags.getNewFolderGroupInputTextBox());
		tags.getNewFolderGroupInputTextBox().SendKeys(folderGroupName);
		base.waitTime(2);
		String folderGroupError = tags.getFolderGroupErrorMsg().getText();
		System.out.println(folderGroupError);
		softassert.assertEquals(folderGroupError, expected);

	}

	/**
	 * @author sowndarya Testcase No:RPMXCON-68768
	 * @Description:Verify that error message display and application does NOT
	 *                     accepts - when "Tag" Name entered with special characters
	 *                     < > & ‘
	 **/
	@Test(description = "RPMXCON-68768", enabled = true, groups = { "regression" })
	public void verifyErrorMsgTagAndtaggroup() throws Exception {

		base.stepInfo("RPMXCON-68768");
		base.stepInfo(
				"To Verify that error message display and application does NOT accepts - when \"Tag\" Name entered with special characters < > & ‘");
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in As : " + Input.pa1userName);
		String tagname = "Tag&‘" + Utility.dynamicRandomNumberAppender();
		String expected = "Special characters are not allowed.";

		// tag
		TagsAndFoldersPage tags = new TagsAndFoldersPage(driver);
		tags.navigateToTagsAndFolderPage();
		tags.createNewTagNotSave(tagname, Input.tagNamePrev);
		String tagserror = tags.getErrorMsg().getText();
		System.out.println(tagserror);
		softassert = new SoftAssert();
		softassert.assertEquals(tagserror, expected);

		// tag group
		String tagGroupName = "Tag&'Group" + Utility.dynamicRandomNumberAppender();
		tags.navigateToTagsAndFolderPage();
		driver.Navigate().refresh();
		base.waitForElement(tags.getTagsTab());
		tags.getTagsTab().waitAndClick(5);
		base.stepInfo("Tag Tab Clicked");
		base.waitForElement(tags.getAllTagRoot());
		tags.getAllTagRoot().waitAndClick(5);
		tags.selectActionArrow("New Tag Group");
		base.waitForElement(tags.getNewTagGroupInputTextBox());
		tags.getNewTagGroupInputTextBox().SendKeys(tagGroupName);
		base.waitTime(2);
		String tagGrouperror = tags.getTagGroupErrorMsg().getText();
		System.out.println(tagGrouperror);
		softassert.assertEquals(tagGrouperror, expected);

	}

	/**
	 * @author Jeevitha
	 * @throws Exception
	 * @Description : Verify that when editing a collection in draft mode,
	 *              collection wizard should be able to use a connection to the
	 *              source location should be able to save the collection in draft
	 *              [RPMXCON-61374 ]
	 */
	@Test(description = "RPMXCON-61374", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyEditingDraftMode(String username, String password, String userRole) throws Exception {
		String selectedFolder = "Inbox";
		String[][] userRolesData = { { username, userRole, "SA" } };

		base.stepInfo("Test case Id: RPMXCON-61374 - O365");
		base.stepInfo(
				"Verify that when editing a collection in draft mode, collection wizard should be able to use a connection to the source location should be able to save the collection in draft");

		HashMap<String, String> colllectionData = new HashMap<>();
		String collectionId = "";

		// Login and Pre-requesties
		login.loginToSightLine(username, password);

		// Pre-requesties - Access verification
		base.stepInfo("Collection Access Verification");
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);

		// Collection Draft creation
		colllectionData = collection.verifyUserAbleToSaveCollectionAsDraft(username, password, userRole, "SA",
				Input.sa1userName, Input.sa1password, selectedFolder, "Draft", false);
		collectionId = base.returnKey(colllectionData, "", false);

		// Edit The Draft collection
		collection.getCollectionsPageAction(collectionId).waitAndClick(5);
		collection.getCollectionsPageActionList(collectionId, "Edit").waitAndClick(5);
		driver.waitForPageToBeReady();

		// Verify whether it is selecting already configured source location
		collection.verifyCurrentTab("Collection Information");
		base.passedStep(
				"Navigated to Collection Information page Directly by selecting Already configured source location is used for the collection");

		// Logout
		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @throws Exception
	 * @Description : Verify that when editing a collection in draft mode, should be
	 *              able to save the collection in draft without making any
	 *              changes[RPMXCON-61375 ]
	 */
	@Test(description = "RPMXCON-61375", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyEditingWithoutMakingChanges(String username, String password, String userRole) throws Exception {
		String selectedFolder = "Inbox";
		String collectionName = "";
		String headerListDataSets[] = { "Collection Id", "Collection Status", "Error Status", Input.progressBarHeader,
				"Action" };

		String[][] userRolesData = { { username, userRole, "SA" } };

		base.stepInfo("Test case Id: RPMXCON-61375 - O365");
		base.stepInfo(
				"Verify that when editing a collection in draft mode, should be able to save the collection in draft without making any changes");

		HashMap<String, String> colllectionData = new HashMap<>();
		String collectionId = "";

		// Login and Pre-requesties
		login.loginToSightLine(username, password);

		// Pre-requesties - Access verification
		base.stepInfo("Collection Access Verification");
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);

		// Collection Draft creation
		colllectionData = collection.verifyUserAbleToSaveCollectionAsDraft(username, password, userRole, "SA",
				Input.sa1userName, Input.sa1password, selectedFolder, "Draft", false);
		collectionId = base.returnKey(colllectionData, "", false);
		collectionName = colllectionData.get(collectionId);

		// Edit The collection which is in Draft
		collection.getCollectionsPageAction(collectionId).waitAndClick(5);
		collection.getCollectionsPageActionList(collectionId, "Edit").waitAndClick(5);
		driver.waitForPageToBeReady();

		// Verify whether it is selecting already configured source location &
		// navigating directly to collection information page
		collection.verifyCurrentTab("Collection Information");
		base.passedStep(
				"Navigated to Collection Information page Directly by selecting Already configured source location is used for the collection");

		// verify Back button is Disabled & validate collection id is not editable
		base.textCompareEquals(collection.getCollectionID().getText(), collectionId, "Collection id is retained ",
				"Collection id not retained");
		String actualTag = collection.getCollectionID().TagName();
		base.textCompareNotEquals(actualTag, "input", "collection id is not editable", "collection id is not editable");

		base.printResutInReport(base.ValidateElement_PresenceReturn(collection.getDisabledBackBtn()),
				"Back button is disabled as Expected", "Back button is not disabled", "Pass");

		// Do not edit Collection name, Datasets Click on 'Save as Draft' button from
		// 'Summary and Start Collection' tab
		collection.getNextBtn().waitAndClick(10);
		collection.clickNextBtnOnDatasetTab();
		driver.waitForPageToBeReady();
		collection.verifyCurrentTab("Summary and Start Collection");
		collection.collectionSaveAsDraft();

		// Verify same collection saved with Collection Status as draft
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collection.verifyExpectedCollectionIsPresentInTheGrid(headerListDataSets, collectionName, "Draft", true, false,
				"");
		base.passedStep("same collection is saved with Collection Status as 'Draft' : " + collectionName);

		// Logout
		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @throws Exception
	 * @Description : Verify that user should be able to execute a configured draft
	 *              collection from collection wizard without making any changes
	 *              [RPMXCON-61376 ]
	 */
	@Test(description = "RPMXCON-61376", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyExecuteWithoutAnyChange(String username, String password, String userRole) throws Exception {
		String selectedFolder = "Inbox";
		String collectionName = "";
		String headerListDataSets[] = { "Collection Id", "Collection Status", "Error Status", Input.progressBarHeader,
				"Action" };
		String[][] userRolesData = { { username, userRole, "SA" } };
		String[] statusListToVerify = { Input.creatingDSstatus };

		base.stepInfo("Test case Id: RPMXCON-61376 - O365");
		base.stepInfo(
				"Verify that user should be able to execute a configured draft collection from collection wizard without making any changes");

		HashMap<String, String> colllectionData = new HashMap<>();
		String collectionId = "";

		// Login and Pre-requesties
		login.loginToSightLine(username, password);

		// Pre-requesties - Access verification
		base.stepInfo("Collection Access Verification");
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);

		// Collection Draft creation
		colllectionData = collection.verifyUserAbleToSaveCollectionAsDraft(username, password, userRole, "SA",
				Input.sa1userName, Input.sa1password, selectedFolder, "Draft", false);
		collectionId = base.returnKey(colllectionData, "", false);
		collectionName = colllectionData.get(collectionId);

		// Edit The collection which is in Draft
		collection.getCollectionsPageAction(collectionId).waitAndClick(5);
		collection.getCollectionsPageActionList(collectionId, "Edit").waitAndClick(5);
		driver.waitForPageToBeReady();

		// Verify whether it is selecting already configured source location &
		// navigating directly to collection information page
		collection.verifyCurrentTab("Collection Information");
		base.passedStep(
				"Navigated to Collection Information page Directly by selecting Already configured source location is used for the collection");

		// verify Back button is Disabled & validate collection id is not editable
		base.textCompareEquals(collection.getCollectionID().getText(), collectionId, "Collection id is retained ",
				"Collection id not retained");
		String actualTag = collection.getCollectionID().TagName();
		base.textCompareNotEquals(actualTag, "input", "collection id is not editable", "collection id is not editable");

		base.printResutInReport(base.ValidateElement_PresenceReturn(collection.getDisabledBackBtn()),
				"Back button is disabled as Expected", "Back button is not disabled", "Pass");

		// Do not edit Collection name, Datasets Click on 'Save as Draft' button from
		// 'Summary and Start Collection' tab
		collection.getNextBtn().waitAndClick(10);
		collection.clickNextBtnOnDatasetTab();
		driver.waitForPageToBeReady();
		collection.verifyCurrentTab("Summary and Start Collection");
		collection.collectionSaveAsDraft();
		base.passedStep("same collection is saved with Collection Status as 'Draft' : " + collectionName);

		// Start collection
		driver.waitForPageToBeReady();
		base.waitForElement(collection.getStartCollectionButton());
		collection.getStartCollectionButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.passedStep("Started Collection in summary and start collection tab :  " + collectionName);

		// Verify Collection execution process started with same datasets
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionName, statusListToVerify, 15);
		base.passedStep("Collection execution started successfully for : " + collectionName);

		// Logout
		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @throws Exception
	 * @Description : Verify that user should be able to execute a configured draft
	 *              collection from collection wizard after editing collection
	 *              configuration [RPMXCON-61377 ]
	 */
	@Test(description = "RPMXCON-61377", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyExecuteWithEditCollection(String username, String password, String userRole) throws Exception {
		String selectedFolder = "Inbox";

		String collectionName = "";
		String selectedApp = Input.collectionDataselectedApp;
		String collectionEmailId = Input.collectionDataEmailId;
		String collection2ndEmailId = Input.collection2ndEmailId;
		String secondFirstName = Input.collsecondFirstName;
		String renamedCollectionName = "Collection" + Utility.dynamicNameAppender();

		String headerListDataSets[] = { "Collection Id", "Collection Status", "Error Status", Input.progressBarHeader,
				"Action" };
		String[][] userRolesData = { { username, userRole, "SA" } };
		String[] statusListToVerify = { Input.creatingDSstatus };

		base.stepInfo("Test case Id: RPMXCON-61377 - O365");
		base.stepInfo(
				"Verify that user should be able to execute a configured draft collection from collection wizard after editing collection configuration");

		HashMap<String, String> colllectionData = new HashMap<>();
		String collectionId = "";

		// Login and Pre-requesties
		login.loginToSightLine(username, password);

		// Pre-requesties - Access verification
		base.stepInfo("Collection Access Verification");
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);

		// Collection Draft creation
		colllectionData = collection.verifyUserAbleToSaveCollectionAsDraft(username, password, userRole, "SA",
				Input.sa1userName, Input.sa1password, selectedFolder, "", false);
		collectionId = base.returnKey(colllectionData, "", false);
		collectionName = colllectionData.get(collectionId);

		// Edit The collection which is in Draft
		collection.getCollectionsPageAction(collectionId).waitAndClick(5);
		collection.getCollectionsPageActionList(collectionId, "Edit").waitAndClick(5);
		driver.waitForPageToBeReady();

		// Verify whether it is selecting already configured source location &
		// navigating directly to collection information page
		collection.verifyCurrentTab("Collection Information");
		base.passedStep(
				"Navigated to Collection Information page Directly by selecting Already configured source location is used for the collection");

		// verify Back button is Disabled & validate collection id is not editable
		base.textCompareEquals(collection.getCollectionID().getText(), collectionId, "Collection id is retained ",
				"Collection id not retained");
		String actualTag = collection.getCollectionID().TagName();
		base.textCompareNotEquals(actualTag, "input", "collection id is not editable", "collection id is not editable");

		base.printResutInReport(base.ValidateElement_PresenceReturn(collection.getDisabledBackBtn()),
				"Back button is disabled as Expected", "Back button is not disabled", "Pass");

		// Edit collection name
		collection.enterCollectionName(renamedCollectionName);
		collection.getNextBtn().waitAndClick(10);

		// Edit datasets details & verify the details
		driver.waitForPageToBeReady();
		String datasetNameGenerator = collection.editDatasetAndVerify(true, collectionEmailId, true, secondFirstName,
				collection2ndEmailId, true, true, "Archive", "Archive", "Disabled", true);
		collection.SaveActionInDataSetPopup(true, secondFirstName, Input.collsecondlastName, selectedApp,
				collection2ndEmailId, datasetNameGenerator, "Archive", "-", true, "Dataset updated successfully.");
		collection.clickNextBtnOnDatasetTab();
		driver.waitForPageToBeReady();

		// Click on 'Save as Draft' button from 'Summary and Start Collection' tab 
		collection.verifyCurrentTab("Summary and Start Collection");
		collection.collectionSaveAsDraft();
		base.passedStep("Edited collection is saved with Collection Status as 'Draft' : " + renamedCollectionName);

		// Start collection from 'Summary and Start Collection' tab
		driver.waitForPageToBeReady();
		base.waitForElement(collection.getStartCollectionButton());
		collection.getStartCollectionButton().waitAndClick(10);
		driver.waitForPageToBeReady();

		// Verify Collection execution process started with edited datasets
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, renamedCollectionName, statusListToVerify, 15);
		base.passedStep("Collection execution started successfully for : " + renamedCollectionName);

		// Logout
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
