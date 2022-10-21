package sightline.O356;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
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

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.CollectionPage;
import pageFactory.DataSets;
import pageFactory.LoginPage;
import pageFactory.SourceLocationPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class O365Regression_22 {

	Driver driver;
	LoginPage login;
	BaseClass base;
	UserManagement userManagement;
	DataSets dataSets;
	CollectionPage collection;
	SourceLocationPage source;

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

	@DataProvider(name = "PaAndRmuUser")
	public Object[][] PaAndRmuUser() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, "Project Administrator" },
				{ Input.rmu1userName, Input.rmu1password, "Review Manager" } };
		return users;
	}

	/**
	 * @author Raghuram A
	 * @throws Exception
	 * @Date: 09/23/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that collection copy should not fail if file name
	 *              contains some special characters. RPMXCON-62887
	 */
	@Test(description = "RPMXCON-62887", enabled = true, groups = { "regression" })
	public void verifyDataRetreivalWithSpecialCharFilesFolder() throws Exception {
		HashMap<String, String> collectionData = new HashMap<>();
		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = Input.splCharEmailFolder;
		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String headerListDataSets[] = { "Collection Id", "Collection Status", "Error Status" };
		String[] statusListToVerify = { Input.creatingDSstatus, Input.retreivingDSstatus, Input.virusScanStatus,
				Input.copyDSstatus };
		String[] statusList = { "Completed" };
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };

		base.stepInfo("Test case Id: RPMXCON-62887 - O365");
		base.stepInfo("Verify that collection copy should not fail if file name contains some special characters.");
		base.failedMessage("Pre-requesties : Make sure you have a folder with special character - "
				+ Input.splCharEmailFolder
				+ " - in your configured email and should also have a file named with special characters in it");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Login as User and verify Module Access
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, Input.pa1password);

		// Create new Collection
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Click create New Collection with datasets
		collectionData = collection.createNewCollection(collectionData, collectionName, true, null, false);
		collection.fillingDatasetSelection("Button", firstName, lastName, collectionEmailId, selectedApp,
				collectionData, collectionName, 3, selectedFolder, true, true, true, Input.randomText, true, true,
				"Save", "");

		// Start Collection
		collection.clickOnNextAndStartAnCollection();
		driver.waitForPageToBeReady();

		// Verify Collection presence with expected Status
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collection.verifyExpectedCollectionStatus(false, headerListDataSets, collectionName, statusListToVerify, 10,
				true, false, "", "");

		// Completed status check
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionName, statusList, 10);
		driver.waitForPageToBeReady();
		base.stepInfo(
				"Copying of dataset is successful even if the selected custodian folder contains file name with special characters");

		// Logout
		login.logout();
	}

	/**
	 * @author Raghuram A
	 * @throws Exception
	 * @Date: 09/23/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify 'Cancel Collection' action when Collection is in
	 *              'Retrieved' status RPMXCON-61068
	 */
	@Test(description = "RPMXCON-61068", enabled = true, groups = { "regression" })
	public void verifyCancelCollectionInDataRetrivedCompletedStats() throws Exception {
		HashMap<String, String> collectionData = new HashMap<>();
		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Inbox";
		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String headerListDataSets[] = { "Collection Id", "Collection Status", "Error Status", "Collection Progress",
				"Action" };
		String[] statusListToVerify = { Input.creatingDSstatus, Input.retreivingDSstatus, Input.virusScanStatus };
		String[] statusList = { "Cancel in progress", "Draft" };
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };

		base.stepInfo("Test case Id: RPMXCON-61068 - O365");
		base.stepInfo("Verify 'Cancel Collection' action when Collection is in 'Retrieved' status");

		// Login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Login as User and verify Module Access
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, Input.pa1password);

		// Navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// create new Collection with Datasets and Initiate
		collectionData = collection.createNewCollection(collectionData, collectionName, true, null, false);
		collection.fillingDatasetSelection("Button", firstName, lastName, collectionEmailId, selectedApp,
				collectionData, collectionName, 3, selectedFolder, true, true, true, Input.randomText, true, true,
				"Save", "");

		// Start Collection
		collection.clickOnNextAndStartAnCollection();
		driver.waitForPageToBeReady();

		// Verify Collection presence with expected Status
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collection.verifyExpectedCollectionStatus(false, headerListDataSets, collectionName, statusListToVerify, 10,
				true, true, "", "");

		// Cancel collection
		collection.clickDownloadReportLink(collectionName, headerListDataSets, "Action", false, "");
		driver.waitForPageToBeReady();
		collection.confirmationAction("Yes", Input.cancelCollectionNotification, false);

		// Cancel back to draft status
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionName, statusList, 15);

		// Delete Collection
		collection.deleteUsingCollectionName(collectionName, true);

		// Logout
		login.logout();
	}

	/**
	 * @author Raghuram.A
	 * @param username
	 * @param password
	 * @param role
	 * @param actionRole
	 * @param actionUserName
	 * @param actionPassword
	 * @return
	 * @throws Exception
	 * @description : Pre-requesties for Collection draft creation
	 */
	public HashMap<String, String> verifyUserAbleToSaveCollectionAsDraft(String username, String password, String role,
			String actionRole, String actionUserName, String actionPassword, String selectedFolder,
			String additional1Status, Boolean additional2) throws Exception {

		HashMap<String, String> colllectionData = new HashMap<>();
		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String headerList[] = { Input.collectionDataHeader1, Input.collectionDataHeader2, Input.collectionDataHeader3,
				Input.collectionDataHeader4, Input.collectionDataHeader5, Input.collectionDataHeader6 };
		String headerListDataSets[] = { "Collection Id", "Collection Status" };
		String expectedCollectionStatus = "Draft";
		String collectionID = "";
		String dataName;
		HashMap<String, String> colllectionDataToReturn = new HashMap<>();

		// Add DataSets
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		colllectionData = collection.dataSetsCreationBasedOntheGridAvailability(firstName, lastName, collectionEmailId,
				selectedApp, colllectionData, selectedFolder, headerList, additional1Status, "Button", 3, true, "");

		// navigate to Collection page and get the data
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		dataName = base.returnKey(colllectionData, "", false);
		System.out.println(dataName);
		collectionID = colllectionData.get(dataName);
		colllectionDataToReturn.put(collectionID, dataName);

		// Verify Collection presence
		collection.verifyExpectedCollectionIsPresentInTheGrid(headerListDataSets, dataName, expectedCollectionStatus,
				true, false, "");
		base.passedStep("Pre-requestied created colleciton Name :" + dataName);

		// return dataNmae created / used
		return colllectionDataToReturn;
	}

	/**
	 * @author Raghuram.A
	 * @Date: 09/23/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify sorting from Manage Collection page. RPMXCON-61633
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-61633", enabled = true, groups = { "regression" })
	public void verifySortingOfCollectionId() throws Exception {
		String selectedFolder = "Inbox";
		String sortType = "Descending";
		String columnToCheck = "Collection ID";
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };
		HashMap<String, String> colllectionData = new HashMap<>();
		String collectionId = "";

		base.stepInfo("Test case Id: RPMXCON-61633 - O365");
		base.stepInfo("Verify sorting from Manage Collection page");

		// Login and Pre-requesties
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Pre-requesties - Access verification
		base.stepInfo("Collection Access Verification");
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, Input.pa1password);

		// Collection Draft creation
		colllectionData = verifyUserAbleToSaveCollectionAsDraft(Input.pa1userName, Input.pa1password,
				"Project Administrator", "SA", Input.sa1userName, Input.sa1password, selectedFolder, "", false);
		collectionId = base.returnKey(colllectionData, "", false);

		// Click and check collection is columns is sorted in Descending
		base.stepInfo("Check Descending sorting from Manage Collections page for Collection ID");
		collection.getHeaderBtn(columnToCheck).waitAndClick(10);
		driver.waitForPageToBeReady();
		collection.verifySortingOrderOfCollectionPage(true, columnToCheck, sortType);
		base.passedStep("Sorting by Collection ID in DESC order is as Expected");

		// navigate to Collection page and Deletion
		base.stepInfo("Initiation collection deletion");
		collection.deleteUsingCollectionName(colllectionData.get(collectionId), true);

		// Logout
		login.logout();
	}

	/**
	 * @author Raghuram.A
	 * @Date: 09/26/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify sorting from Manage Collection page. RPMXCON-60913
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-60913", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyEditAnDeleteNotInCollWorkFlow(String username, String password, String fullname)
			throws Exception {
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };
		String dataname = "AASourceLocation" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-60913 - O365");
		base.stepInfo(
				"Verify that edit or delete option(s) are not available for a source location in a collection’s workflow.");

		// Login and Pre-requesties
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Pre-requesties - Access verification
		base.stepInfo("Collection Access Verification");
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);

		// navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Click create New Collection
		collection.performCreateNewCollection();

		// Add new source location
		collection.performAddNewSource(null, dataname, Input.TenantID, Input.ApplicationID, Input.ApplicationKey);

		// verify Added source location is displayed
		collection.verifyAddedSourceLocation(dataname, null);

		// Verify Edit and Delete options are not available in Collection flow
		base.printResutInReport(base.ValidateElement_AbsenceReturn(source.getSrcActionBtn(dataname, "Edit")),
				"Edit option(s) not available for a source location in a collection’s workflow.",
				"Edit option(s) should is available for a source location in a collection’s workflow.", "Pass");
		base.printResutInReport(base.ValidateElement_AbsenceReturn(source.getSrcActionBtn(dataname, "Delete")),
				"Delete option(s) not available for a source location in a collection’s workflow.",
				"Delete option(s) should is available for a source location in a collection’s workflow.", "Pass");

		// delete created source location
		base.stepInfo("Initiating delete for created source location");
		dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);
		source.columnHeader("Data Source Name").waitAndClick(3);
		source.deleteSourceLocation(dataname, false);

		// Logout
		login.logout();
	}

	/**
	 * @author Raghuram A
	 * @throws Exception
	 * @Date: 09/26/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that User can View the status and monitor the progress
	 *              of collection(s) on "Manage Screen" screen RPMXCON-61038
	 */
	@Test(description = "RPMXCON-61038", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyStausWorkProgress(String userName, String password, String role) throws Exception {
		HashMap<String, String> collectionData = new HashMap<>();
		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Inbox";
		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String headerListDataSets[] = { "Collection Id", "Collection Status", "Error Status", Input.progressBarHeader };
		String[] statusListToVerify = { Input.creatingDSstatus, Input.retreivingDSstatus, Input.virusScanStatus,
				Input.copyDSstatus };
		String[] statusList = { "Completed" };
		String[][] userRolesData = { { userName, role, "SA" } };

		base.stepInfo("Test case Id: RPMXCON-61038 - O365");
		base.stepInfo(
				"Verify that User can View the status and monitor the progress of collection(s) on \"Manage Screen\" screen");

		// Login as User
		login.loginToSightLine(userName, password);

		// Login as User and verify Module Access
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);

		// create new Collection
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Click create New Collection with datasets
		collectionData = collection.createNewCollection(collectionData, collectionName, true, null, false);
		collection.fillingDatasetSelection("Button", firstName, lastName, collectionEmailId, selectedApp,
				collectionData, collectionName, 3, selectedFolder, true, true, true, Input.randomText, true, true,
				"Save", "");

		// Start Collection
		collection.clickOnNextAndStartAnCollection();

		// Verify Page Navigation
		driver.waitForPageToBeReady();
		base.verifyPageNavigation(Input.collectionPageUrl);
		base.passedStep("User should Navigate to \"Manage Collections\" screen.");
		base.stepInfo(
				"Verify that User can View the status and monitor the progress of collection(s) on \"Manage Screen\" screen");
		collection.verifyExpectedCollectionStatus(false, headerListDataSets, collectionName, statusListToVerify, 10,
				true, true, "", "");

		// Completed status check
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionName, statusList, 10);
		driver.waitForPageToBeReady();

		// Logout
		login.logout();
	}

	/**
	 * @Author Jeevitha R
	 * @Description : Verify that user can go to ‘Source Selection’ step on click of
	 *              ‘Back’ button from collection information’ step [RPMXCON-60654]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-60654", enabled = true, groups = { "regression" })
	public void verifyUserCanGoToSrceSelect() throws Exception {
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };
		String collectionName = "Collection" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-60654 - O365");
		base.stepInfo(
				"Verify that user can go to ‘Source Selection’ step on click of ‘Back’ button from collection information’ step");

		// Login and Pre-requesties
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Pre-requesties - Access verification
		base.stepInfo("Collection Access Verification");
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, Input.pa1password);

		// navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// verify collection page
		collection.performCreateNewCollection();

		// Select source and Click create New Collection
		String dataSourceName = collection.selectSourceFromTheListAvailable();

		// click created source location & enter collection name
		collection.verifyCollectionInfoPage(dataSourceName, collectionName, false);

		// select Do Not auto initiate collection process
		collection.selectInitiateCollectionOrClickNext(false, false, false);

		// click back button
		driver.waitForPageToBeReady();
		collection.getBackBtn().waitAndClick(10);

		// verify navigated to source selection tab
		driver.waitForPageToBeReady();
		collection.verifyCurrentTab("Source Selection");

		// Logout
		login.logout();
	}

	/**
	 * @author Raghuram A
	 * @throws Exception
	 * @Date: 09/28/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :Verify that User can View the status and monitor the progress
	 *              of collection(s) on \"Manage Screen\" screen. RPMXCON-61639
	 */
	@Test(description = "RPMXCON-61639", enabled = true, groups = { "regression" })
	public void verifyCollectionNameInDSpage() throws Exception {
		String selectedFolder = "Inbox";
		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String headerListDataSets[] = { "Collection Id", "Collection Status", "Error Status", Input.progressBarHeader,
				"Action" };
		String[] statusListToVerify = { Input.creatingDSstatus, Input.retreivingDSstatus, Input.virusScanStatus,
				Input.copyDSstatus };
		String[] statusList = { "Completed" };
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };

		base.stepInfo("Test case Id: RPMXCON-61639 - O365");
		base.stepInfo(
				"Verify that User can View the status and monitor the progress of collection(s) on \"Manage Screen\" screen");

		HashMap<String, String> colllectionData = new HashMap<>();
		String collectionId = "";

		base.stepInfo("Test case Id: RPMXCON-61633 - O365");
		base.stepInfo("Verify sorting from Manage Collection page");

		// Login and Pre-requesties
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Pre-requesties - Access verification
		base.stepInfo("Collection Access Verification");
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, Input.pa1password);

		// Collection Draft creation
		colllectionData = verifyUserAbleToSaveCollectionAsDraft(Input.pa1userName, Input.pa1password,
				"Project Administrator", "SA", Input.sa1userName, Input.sa1password, selectedFolder, "", false);
		collectionId = base.returnKey(colllectionData, "", false);
		collectionName = colllectionData.get(collectionId);

		// Execute / Start collection Verifications
		collection.getCollectionsPageAction(collectionId).waitAndClick(5);
		collection.getCollectionsPageActionList(collectionId, "Start Collection").waitAndClick(5);
		driver.waitForPageToBeReady();
		base.VerifySuccessMessage("Collection extraction process started successfully.");

		// Verify Page Navigation
		driver.waitForPageToBeReady();
		collection.verifyExpectedCollectionStatus(false, headerListDataSets, collectionName, statusListToVerify, 10,
				true, true, "", "");

		// Completed status check
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionName, statusList, 10);
		driver.waitForPageToBeReady();

		// View in datasets page
		collection.clickViewDataset(collectionName);
		driver.waitForPageToBeReady();

		// Get collectioname from Dataset page and validate
		String collectionIdFromDS = base.getSpecifiedTextViaSplit(dataSets.getDataSetNameViaViewDS(), "_", 0);
		base.textCompareEquals(collectionId, collectionIdFromDS, "CollectionID is displayed on Dataset",
				"Collection ID mismatch in datasets page");

		// Logout
		login.logout();
	}

	/**
	 * @author Raghuram A
	 * @throws Exception
	 * @Date: 09/28/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify 'Delete' action when Collection is in 'Draft' status.
	 *              RPMXCON-61036
	 */
	@Test(description = "RPMXCON-61036", enabled = true, groups = { "regression" })
	public void verifyDraftStatusCollectionDeletion() throws Exception {
		String selectedFolder = "Inbox";
		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String headerListDataSets[] = { "Collection Id", "Collection Status", "Error Status", Input.progressBarHeader,
				"Action" };
		String expectedCollectionStatus = "Draft";
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };

		base.stepInfo("Test case Id: RPMXCON-61036 - O365");
		base.stepInfo("Verify 'Delete' action when Collection is in 'Draft' status");

		HashMap<String, String> colllectionData = new HashMap<>();
		String collectionId = "";

		base.stepInfo("Test case Id: RPMXCON-61633 - O365");
		base.stepInfo("Verify sorting from Manage Collection page");

		// Login and Pre-requesties
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Pre-requesties - Access verification
		base.stepInfo("Collection Access Verification");
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, Input.pa1password);

		// Collection Draft creation
		colllectionData = verifyUserAbleToSaveCollectionAsDraft(Input.pa1userName, Input.pa1password,
				"Project Administrator", "SA", Input.sa1userName, Input.sa1password, selectedFolder, "Draft", false);
		collectionId = base.returnKey(colllectionData, "", false);
		collectionName = colllectionData.get(collectionId);

		// Verify Collection presence
		collection.verifyExpectedCollectionIsPresentInTheGrid(headerListDataSets, collectionName,
				expectedCollectionStatus, true, false, "");

		// navigate to Collection page and Deletion
		base.stepInfo("Initiation collection  deletion");
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Collection Deletion with Action "No"
		base.stepInfo("Collection Deletion with Action \"No\"");
		collection.getCollectionsPageAction(collectionId).waitAndClick(5);
		collection.getCollectionsPageActionList(collectionId, "Delete").waitAndClick(5);
		collection.getConfirmationBtnAction("No").waitAndClick(5);
		base.stepInfo("Clicked No as delete confirmation");

		// verify Collection Presence in Manage collection Screen
		base.printResutInReport(base.ValidateElement_StatusReturn(collection.getCollectionAction(collectionName), 3),
				collectionName + " is Displayed in Manage Collection Screen",
				collectionName + " : is not Displayed after applying No as confirmation", "Pass");

		// Collection Deletion with Action "Yes"
		collection.collectionDeletion(collectionId);
		driver.waitForPageToBeReady();
		base.waitTime(3);

		// verify Collection Absence in Manage collection Screen
		base.printResutInReport(base.ValidateElement_StatusReturn(collection.getCollectionAction(collectionName), 3),
				collectionName + " deleted Successfully : is not Displayed in Manage Collection Screen",
				collectionName + " : is Displayed after deleting", "Fail");

		// Logout
		login.logout();
	}

	/**
	 * @Author Jeevitha R
	 * @Description : Verify that user can configure the collection with ‘Not
	 *              automatically initiate processing’ and can move to ‘Next’ step
	 *              of collection [RPMXCON-60652]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-60652", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyUserCanMoveNext(String username, String password, String fullname) throws Exception {
		String[][] userRolesData = { { username, fullname, "SA" } };
		String collectionName = "Collection" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-60652 - O365");
		base.stepInfo(
				"Verify that user can configure the collection with ‘Not automatically initiate processing’ and can move to ‘Next’ step of collection");

		// Login and Pre-requesties
		login.loginToSightLine(username, password);

		// Pre-requesties - Access verification
		base.stepInfo("Collection Access Verification");
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);

		// navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// verify collection page
		collection.performCreateNewCollection();

		// Select source and Click create New Collection
		String dataSourceName = collection.selectSourceFromTheListAvailable();

		// click created source location & enter collection name
		collection.verifyCollectionInfoPage(dataSourceName, collectionName, false);

		// select Do Not auto initiate collection process
		collection.selectInitiateCollectionOrClickNext(false, true, true);

		// Logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that User can edit an existing source location from
	 *              “Source Locations “screen(grid). [RPMXCON-60795]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-60795", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyUserCanEditSrceName(String username, String password, String fullname) throws Exception {
		String dataname = "AAAutomation" + Utility.dynamicNameAppender();
		String RenamedDataName = "AARenamed" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-60795 - O365");
		base.stepInfo("Verify that User can edit an existing source location from “Source Locations “screen(grid).");

		String[][] userRolesData = { { username, fullname, "SA" } };

		// Login as User
		login.loginToSightLine(username, password);

		// Pre-requesties - Access verification
		base.stepInfo("Collection Access Verification");
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password,password);

		// navigate to Source page
		dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);

		// Add new source location on Source Page
		collection.performAddNewSource(null, dataname, Input.TenantID, Input.ApplicationID, Input.ApplicationKey);

		// Edit Data Source name from popup and save
		driver.waitForPageToBeReady();
		source.columnHeader("Data Source Name").waitAndClick(10);
		source.editSourceLocationDetails(dataname, true, collection.getDataSourceName(), "Data Source Name",
				RenamedDataName);

		// verify renamed dataname is dispalyed in grid
		driver.waitForPageToBeReady();
		source.columnHeader("Data Source Name").waitAndClick(10);
		source.verifySourceLocationName(RenamedDataName);

		// delete created source location
		source.deleteSourceLocation(RenamedDataName, false);

		// Logout
		login.logout();
	}

	/**
	 * @author Raghuram A
	 * @throws Exception
	 * @Date: 09/29/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify 'Start Collection' action when Collection is in 'Draft'
	 *              status. RPMXCON-61037
	 */
	@Test(description = "RPMXCON-61037", enabled = true, groups = { "regression" })
	public void verifyStartCollectionFromManageScreen() throws Exception {
		HashMap<String, String> collectionData = new HashMap<>();
		String selectedFolder = "Inbox";
		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String headerListDataSets[] = { "Collection Id", "Collection Status", "Error Status" };
		String[] statusListToVerify = { Input.creatingDSstatus, Input.retreivingDSstatus, Input.virusScanStatus };
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };

		base.stepInfo("Test case Id: RPMXCON-61037 - O365");
		base.stepInfo("Verify 'Start Collection' action when Collection is in 'Draft' status.");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Login as User and verify Module Access
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, Input.pa1password);

		// Create new Collection
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Collection Draft creation
		collectionData = verifyUserAbleToSaveCollectionAsDraft(Input.pa1userName, Input.pa1password,
				"Project Administrator", "SA", Input.sa1userName, Input.sa1password, selectedFolder, "", false);
		String collectionId = base.returnKey(collectionData, "", false);
		collectionName = collectionData.get(collectionId);

		// Execute / Start collection Verifications
		collection.getCollectionsPageAction(collectionId).waitAndClick(5);
		collection.getCollectionsPageActionList(collectionId, "Start Collection").waitAndClick(5);
		driver.waitForPageToBeReady();
		base.VerifySuccessMessage("Collection extraction process started successfully.");

		// Verify Collection presence with expected Status
		base.stepInfo(
				"The application should start the collection process execution for the collection beginning with the first step i.e. 'Creation' - 'Retrieval'.");
		collection.verifyExpectedCollectionStatus(false, headerListDataSets, collectionName, statusListToVerify, 10,
				true, false, "", "");

		// Logout
		login.logout();
	}

	/**
	 * @author Raghuram A
	 * @throws Exception
	 * @Date: 09/30/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify 'Edit' action when Collection is in 'Draft' status.
	 *              RPMXCON-61035
	 */
	@Test(description = "RPMXCON-61035", enabled = true, groups = { "regression" })
	public void editCollectionDraftCheck() throws Exception {

		HashMap<String, String> colllectionData = new HashMap<>();
		HashMap<String, String> colllectionData2 = new HashMap<>();
		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Inbox";
		String headerList[] = { Input.collectionDataHeader1, Input.collectionDataHeader2, Input.collectionDataHeader3,
				Input.collectionDataHeader4, Input.collectionDataHeader5, Input.collectionDataHeader6 };
		String headerListDataSets[] = { "Collection Id", "Collection Status" };
		String expectedCollectionStatus = "Draft";
		String collectionID = "";
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };
		String collection2ndEmailId = Input.collection2ndEmailId;
		String secondFirstName = Input.collsecondFirstName;
		String secondlastName = Input.collsecondlastName;
		String[] statusListToVerify = { Input.creatingDSstatus, Input.retreivingDSstatus, Input.virusScanStatus,
				Input.copyDSstatus };
		String[] statusList = { "Completed" };

		base.stepInfo("Test case Id: RPMXCON-61035 - O365");
		base.stepInfo("Verify 'Edit' action when Collection is in 'Draft' status");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Login as User and verify Module Access
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, Input.pa1password);

		// navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Add DataSets
		colllectionData = collection.dataSetsCreationBasedOntheGridAvailability(firstName, lastName, collectionEmailId,
				selectedApp, colllectionData, selectedFolder, headerList, "", "Button", 3, false, "");

		// navigate to Collection page and get the data
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		String collectionName = base.returnKey(colllectionData, "", false);
		System.out.println(collectionName);
		collectionID = colllectionData.get(collectionName);

		// Verify Collection presence
		collection.verifyExpectedCollectionIsPresentInTheGrid(headerListDataSets, collectionName,
				expectedCollectionStatus, true, false, "");

		// Edit Verifications
		collection.getCollectionsPageAction(collectionID).waitAndClick(5);
		collection.getCollectionsPageActionList(collectionID, "Edit").waitAndClick(5);
		base.waitForElement(collection.getCollectioName());
		collection.verifyCurrentTab("Collection Information");
		base.textCompareEquals(collection.getCollectionID().getText(), collectionID, "Collection id is retained ",
				"Collection id not retained");
		base.printResutInReport(base.ValidateElement_PresenceReturn(collection.getDisabledBackBtn()),
				"Back button is disabled as Expected", "Back button is not disabled", "Pass");

		// Add DataSets - 2nd
		collectionName = collectionName + "Re";
		colllectionData2 = collection.dataSetsCreationBasedOntheGridAvailabilityT(collectionName, colllectionData2,
				false);
		collectionName = base.returnKey(colllectionData2, "", false);
		System.out.println(collectionName);
		collectionID = colllectionData.get(collectionName);

		// DataSet creation
		collection.fillinDS(collectionName, secondFirstName, secondlastName, collection2ndEmailId, selectedApp,
				colllectionData2, selectedFolder, headerList, "Button", 3, false, "Save", false, "");

		// Initiate collection
		collection.clickOnNextAndStartAnCollection();

		// Verify Page Navigation
		driver.waitForPageToBeReady();
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collection.verifyExpectedCollectionStatus(false, headerListDataSets, collectionName, statusListToVerify, 10,
				true, false, "", "");

		// Completed status check
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionName, statusList, 15);
		driver.waitForPageToBeReady();

		// Result
		base.stepInfo(
				"--------------------------------------------------------------------------------------------------------------------------");
		base.stepInfo(
				"Verified 'Edit' action when Collection is in 'Draft' status - Updated collection Name - Added datasets - Initiated Run");
		base.stepInfo(
				"--------------------------------------------------------------------------------------------------------------------------");

		// Logout
		login.logout();

	}

	/**
	 * @Author Jeevitha R
	 * @Description : Verify the validation message for Custodian name on clearing
	 *              the same field [RPMXCON-61631]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-61631", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyValidationMessage(String username, String password, String fullname) throws Exception {
		HashMap<String, String> collectionData = new HashMap<>();

		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;

		String[][] userRolesData = { { username, fullname, "SA" } };
		String collectionName = "Collection" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-61631 - O365");
		base.stepInfo("Verify the validation message for Custodian name on clearing the same field");

		// Login and Pre-requesties
		login.loginToSightLine(username, password);

		// Pre-requesties - Access verification
		base.stepInfo("Collection Access Verification");
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);

		// Add DataSets
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collectionData = collection.createNewCollection(collectionData, collectionName, true, null, false);

		// Add DataSets
		collection.addDataSetWithHandles("Button", firstName, lastName, collectionEmailId, selectedApp, collectionData,
				collectionName, 3);

		// Edit folder name and verify Dataset Selection Table
		driver.waitForPageToBeReady();
		base.clearATextBoxValue(collection.getCustodianIDInputTextField());

		// Verify Custodian Fields Error Message
		collection.verifyErrorMessageOfDatasetTab(true, true, false);

		// Logout
		login.logout();
	}

	/**
	 * @Author Jeevitha R
	 * @Description : Verify that user can initiate collection on "Manage Screen"
	 *              screen [RPMXCON-61034]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-61034", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyCanInitiateCollection(String username, String password, String fullname) throws Exception {
		HashMap<String, String> collectionData = new HashMap<>();

		String collectionEmailId = Input.collectionDataEmailId;
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

		base.stepInfo("Test case Id: RPMXCON-61034 - O365");
		base.stepInfo("Verify that user can initiate collection on \"Manage Screen\" screen");

		// Login and Pre-requesties
		login.loginToSightLine(username, password);

		// Pre-requesties - Access verification
		base.stepInfo("Collection Access Verification");
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);

		// Start Collection
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collectionData = collection.createNewCollection(collectionData, collectionName, true, null, false);

		// Add Dataset
		collection.fillingDatasetSelection("Button", firstName, lastName, collectionEmailId, selectedApp,
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
		login.logout();
	}

	/**
	 * @Author Jeevitha R
	 * @Description : Verify that if Tenant ID is configured for the source location
	 *              then data source authentication will not perform [RPMXCON-61043]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-61043", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyyAuthenticationIsNotRequired(String username, String password, String fullname) throws Exception {
		String[][] userRolesData = { { username, fullname, "SA" } };
		String collectionName = "Collection" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-61043 - O365");
		base.stepInfo(
				"Verify that if Tenant ID is configured for the source location then data source authentication will not perform");

		// Login and Pre-requesties
		login.loginToSightLine(username, password);

		// Pre-requesties - Access verification
		base.stepInfo("Collection Access Verification");
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);

		// Add DataSets
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collection.performCreateNewCollection();

		// Select source and Click create New Collection
		String dataSourceName = collection.selectSourceFromTheListAvailable();

		// click created source location and verify navigated page
		collection.verifyCollectionInfoPage(dataSourceName, collectionName, false);
		base.passedStep(" tenant id is stored, it is not require to authenticate to the data source");

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