package testScriptsRegressionSprint19;

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

public class O365Regression_19 {

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
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "Review Manager" },
				{ Input.pa1userName, Input.pa1password, "Project Administrator" }, };
		return users;
	}

	@DataProvider(name = "PaAndRmuUserDetails")
	public Object[][] PaAndRmuUserDetails() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, "Project Administrator", "SA" },
				{ Input.rmu1userName, Input.rmu1password, "Review Manager", "SA" } };
		return users;
	}

	/**
	 * @Author Jeevitha
	 * @Description :Verify that user should be able to specify filters that should
	 *              be applied on custodian’s data
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-60760", enabled = true, groups = { "regression" })
	public void verifyDatasetApplyFilter() throws Exception {
		HashMap<String, String> colllectionData = new HashMap<>();
		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Drafts";
		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };

		base.stepInfo("Test case Id: RPMXCON-60760 - O365");
		base.stepInfo("Verify that user should be able to specify filters that should be applied on custodian’s data");

		// Login as User
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManagement.navigateToUsersPAge();
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");

		// Logout
		login.logout();

		// Login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Add DataSets
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		colllectionData = collection.createNewCollection(colllectionData, collectionName, true, null, false);

		// add Dataset By Applying Filter
		collection.fillingDatasetSelection("Button", firstName, lastName, collectionEmailId, selectedApp,
				colllectionData, collectionName, 3, selectedFolder, true, true, true, Input.randomText, true, true,
				"Save", "");

		// Logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description :Verify that user should be able to add multiple custodian
	 *              datasets in a collection on click of ‘Add Datasets’ and check
	 *              the attributes from the dataset selection tab
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-60758", enabled = true, groups = { "regression" })
	public void verifyMultipleDataset() throws Exception {
		HashMap<String, String> colllectionData = new HashMap<>();
		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String collection2ndEmailId = Input.collection2ndEmailId;
		String secondFirstName = Input.collsecondFirstName;
		String secondlastName = Input.collsecondlastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Drafts";
		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };

		base.stepInfo("Test case Id: RPMXCON-60758 - O365");
		base.stepInfo(
				"Verify that user should be able to add multiple custodian datasets in a collection on click of ‘Add Datasets’ and check the attributes from the dataset selection tab");

		// Login as User
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManagement.navigateToUsersPAge();
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");

		// Logout
		login.logout();

		// Login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Add DataSets
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		colllectionData = collection.createNewCollection(colllectionData, collectionName, true, null, false);

		// add Dataset By Applying Filter
		collection.fillingDatasetSelection("Button", firstName, lastName, collectionEmailId, selectedApp,
				colllectionData, collectionName, 3, selectedFolder, true, true, true, Input.randomText, true, true,
				"Save", "");

		// add Dataset By Applying Filter
		collection.fillingDatasetSelection("Button", secondFirstName, secondlastName, collection2ndEmailId, selectedApp,
				colllectionData, collectionName, 3, selectedFolder, true, true, true, Input.randomText, true, true,
				"Save", "");

		// Logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that on click of ‘Save’ from Dataset Selection pop up,
	 *              new row should be added for the custodian specification in the
	 *              collection
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-60767", enabled = true, groups = { "regression" })
	public void verifyOnClickSaveNewRowAdded() throws Exception {
		HashMap<String, String> colllectionData = new HashMap<>();
		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Drafts";
		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };

		base.stepInfo("Test case Id: RPMXCON-60767 - O365");
		base.stepInfo(
				"Verify that on click of ‘Save’ from Dataset Selection pop up, new row should be added for the custodian specification in the collection");

		// Login as User
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManagement.navigateToUsersPAge();
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");

		// Logout
		login.logout();

		// Login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Add DataSets
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		colllectionData = collection.createNewCollection(colllectionData, collectionName, true, null, false);

		// Click cancel in Folder select Popup in Dataset selection
		collection.fillingDatasetSelection("Button", firstName, lastName, collectionEmailId, selectedApp,
				colllectionData, collectionName, 3, selectedFolder, true, true, true, Input.randomText, true, false,
				"Save", "");

		// add Dataset By Applying Filter & click verify new row added
		collection.fillingDatasetSelection("Button", firstName, lastName, collectionEmailId, selectedApp,
				colllectionData, collectionName, 3, selectedFolder, true, true, true, Input.randomText, true, true,
				"Save", "");

		// Logout
		login.logout();
	}

	/**
	 * @AUthor Jeevitha
	 * @dsecription : Verify that Application is not deleting a collection when User
	 *              selected "No" option on Collection’s home page.
	 * @param username
	 * @param password
	 * @param fullname
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-61293", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyCollectionIsNotDeleted(String username, String password, String fullname) throws Exception {
		HashMap<String, String> colllectionData = new HashMap<>();
		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Drafts";
		String headerList[] = { Input.collectionDataHeader1, Input.collectionDataHeader2, Input.collectionDataHeader3,
				Input.collectionDataHeader4, Input.collectionDataHeader5, Input.collectionDataHeader6 };
		String headerListDataSets[] = { "Collection Id", "Collection Status" };
		String expectedCollectionStatus = "Draft";
		String collectionID = "";
		String[][] userRolesData = { { username, fullname, "SA" } };

		base.stepInfo("Test case Id: RPMXCON-61293 - O365");
		base.stepInfo(
				"Verify that Application is not deleting a collection when User selected \"No\" option on Collection’s home page.E");

		// Login as User
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManagement.navigateToUsersPAge();
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");

		// Logout
		login.logout();

		// Login as User
		login.loginToSightLine(username, password);

		// Add DataSets
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		colllectionData = collection.dataSetsCreationBasedOntheGridAvailability(firstName, lastName, collectionEmailId,
				selectedApp, colllectionData, selectedFolder, headerList, expectedCollectionStatus, "Button", 3, true,
				"");

		// navigate to Collection page and get the data
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		String dataName = base.returnKey(colllectionData, "", false);
		System.out.println(dataName);
		collectionID = colllectionData.get(dataName);

		// Verify Collection presence
		collection.verifyExpectedCollectionIsPresentInTheGrid(headerListDataSets, dataName, expectedCollectionStatus,
				true, false, "");

		// Click delet and click no button
		collection.deleteUsingCollectionName(dataName, false);

		// Again verify Collection presence
		collection.verifyExpectedCollectionIsPresentInTheGrid(headerListDataSets, dataName, expectedCollectionStatus,
				true, false, "");
		// Logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that user should be able to edit/modify an existing
	 *              custodian dataset folder, filters and save the modifications
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-60766", enabled = true, groups = { "regression" })
	public void editAddedDataset() throws Exception {
		HashMap<String, String> collectionData = new HashMap<>();
		List<String> custodianDetails = new ArrayList();

		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String collection2ndEmailId = Input.collection2ndEmailId;
		String secondFirstName = Input.collsecondFirstName;
		String secondlastName = Input.collsecondlastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Drafts";
		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };

		base.stepInfo("Test case Id: RPMXCON-60766 - O365");
		base.stepInfo(
				"Verify that user should be able to edit/modify an existing custodian dataset folder, filters and save the modifications");

		// Login as User
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManagement.navigateToUsersPAge();
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");

		// Logout
		login.logout();

		// Login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Add DataSets
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collectionData = collection.createNewCollection(collectionData, collectionName, true, null, false);

		// Add Dataset By Applying Filter
		custodianDetails = collection.fillingDatasetSelection("Button", firstName, lastName, collectionEmailId,
				selectedApp, collectionData, collectionName, 3, selectedFolder, true, true, true, Input.randomText,
				true, true, "Save", "");

		// click Edit and verify The selected Details Are Retained
		collection.verifyAddedDataSetFrmPopup(collectionEmailId, collectionName, custodianDetails, selectedFolder, true,
				"Enabled");

		// Edit folder name and verify Dataset Selection Table
		collection.editDatasetAndVerify(false, null, false, null, null, true, false, selectedFolder, "Archive", null,
				false);
		collection.SaveActionInDataSetPopup(true, firstName, lastName, selectedApp, collectionEmailId,
				custodianDetails.get(1), "Archive", Input.randomText, true, "Dataset updated successfully.");

		// Edit Custodians Name and verify folders And application is Reset
		driver.waitForPageToBeReady();
		collection.editDatasetAndVerify(true, collectionEmailId, true, secondFirstName, collection2ndEmailId, true,
				true, "Archive", selectedFolder, "Disabled", true);

		// Logout
		login.logout();
	}

	/**
	 * @Author Mohan
	 * @Description : Verify the folders from the ‘Dataset Selection Popup’ and
	 *              select-unselect of folders
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-60778", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyFoldersDatasetSelectionPopup(String username, String password, String fullname) throws Exception {

		base.stepInfo("Test case Id: RPMXCON-60778 - O365");
		base.stepInfo("Verify the folders from the ‘Dataset Selection Popup’ and select-unselect of folders");

		String[][] userRolesData = { { username, fullname, "SA" } };

		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Calendars";

		// Login as User
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");

		login.logout();

		// Login As PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("User successfully logged into slightline webpage " + Input.pa1userName + "");

		// To add New Source Location
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		String dataName = "Automation" + Utility.dynamicNameAppender();

		driver.waitForPageToBeReady();

		// Click create New Collection
		collection.performCreateNewCollection();

		// Select source and Click create New Collection
		String dataSourceName = collection.selectSourceFromTheListAvailable();

		// click created source location and verify navigated page
		HashMap<String, String> collectionInfoPage = collection.verifyCollectionInfoPage(dataSourceName, dataName,
				false);

		// initiate collection process
		collection.selectInitiateCollectionOrClickNext(true, true, true);

		// Add DataSets
		String dataSetNameGenerated = collection.addDataSetWithHandles("Button", firstName, lastName, collectionEmailId,
				selectedApp, collectionInfoPage, dataName, 3);
		System.out.println(dataSetNameGenerated);

		// Folder Selection
		collection.folderToSelect(selectedFolder, true, false);
		base.passedStep(
				"User is able to select/unselect the folder from the pop up. Parent folder is checked, by default, the tree of folders and sub - folders under the parent folder is checked successfully");

		// Logout
		login.logout();
	}

	/**
	 * @Author Mohan
	 * @Description : Verify that User can change one source location type to
	 *              another from "Data Source Type" dropdown on “Add New Source
	 *              Location" screen.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-60827", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyOneSourceLocationDataSourceTypeDropDownAddNewSourceLocation(String username, String password,
			String fullname) throws Exception {

		base.stepInfo("Test case Id: RPMXCON-60827 - O365");
		base.stepInfo(
				"Verify that User can change one source location type to another from 'Data Source Type' dropdown on 'Add New Source Location' screen.");

		String[][] userRolesData = { { username, fullname, "SA" } };
		String dataSourceName = "Automation" + Utility.dynamicNameAppender();
		String dropDownValue = "Microsoft 365";

		// Login as User
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");

		login.logout();

		// Login as User
		login.loginToSightLine(username, password);
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Add new collection
		collection.performCreateNewCollection();

		// verify Source location type
		collection.verifyAnotherSourceLocationCanBeSelected(dropDownValue, dataSourceName, Input.TenantID,
				Input.ApplicationID, Input.ApplicationKey);

		// Logout
		login.logout();
	}

	/**
	 * @Author Mohan
	 * @Description : Verify that error message should be displayed from Dataset
	 *              Selection pop up, when required fields are blank
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-60850", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyErrorMessageInDAtaSelectionPopUp(String username, String password, String fullname)
			throws Exception {

		base.stepInfo("Test case Id: RPMXCON-60850 - O365");
		base.stepInfo(
				"Verify that error message should be displayed from Dataset Selection pop up, when required fields are blank");

		String[][] userRolesData = { { username, fullname, "SA" } };
		String dataSourceName = "Automation" + Utility.dynamicNameAppender();
		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Inbox";

		// Login as User
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");

		login.logout();

		// Login as User
		login.loginToSightLine(username, password);

		// navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Click create New Collection
		collection.performCreateNewCollection();

		// Select source and Click create New Collection
		String srcLocation = collection.selectSourceFromTheListAvailable();

		// click created source location and verify navigated page
		HashMap<String, String> collectionInfoPage = collection.verifyCollectionInfoPage(srcLocation, dataSourceName,
				false);

		// initiate collection process
		collection.selectInitiateCollectionOrClickNext(true, true, true);

		// Verify Custodian Fields Error Message
		collection.verifyErrorMessageInCutodianSelection("Button", firstName, lastName, collectionEmailId, selectedApp,
				collectionInfoPage, dataSourceName, 5);

		// verify Folder Error Message
		collection.verifyErrorMessageInFolderSelectionFields(selectedFolder);

		// verify Apply Filter button
		collection.verifyErrorMessageInApplyFilterField();

		// edit the Dataset and verify error message
		collection.editDatasetAndVerifyErrorMessage(selectedFolder);
		collection.verifyErrorMessageInFolderSelectionFields(selectedFolder);

		// Logout
		login.logout();
	}

	/**
	 * @Author Mohan
	 * @Description : Verify that on click of ‘Add Datasets’ link, Dataset selection
	 *              pop up should open
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-60757", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyAddDatasetsLinkInDataselectionPopup(String username, String password, String fullname)
			throws Exception {

		base.stepInfo("Test case Id: RPMXCON-60757 - O365");
		base.stepInfo("Verify that on click of ‘Add Datasets’ link, Dataset selection pop up should open");

		String[][] userRolesData = { { username, fullname, "SA" } };
		String dataSourceName = "Automation" + Utility.dynamicNameAppender();
		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Inbox";

		// Login as User
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");

		login.logout();

		// Login as User
		login.loginToSightLine(username, password);

		// navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Click create New Collection
		collection.performCreateNewCollection();

		// Select source and Click create New Collection
		String srcLocation = collection.selectSourceFromTheListAvailable();

		// click created source location and verify navigated page
		HashMap<String, String> collectionInfoPage = collection.verifyCollectionInfoPage(srcLocation, dataSourceName,
				false);

		// initiate collection process
		collection.selectInitiateCollectionOrClickNext(true, true, true);

		// verify Dataset page
		collection.verifyDatasetsPage();

		// Add DataSets
		String dataSetNameGenerated = collection.addDataSetWithHandles("Button", firstName, lastName, collectionEmailId,
				selectedApp, collectionInfoPage, dataSourceName, 3);
		System.out.println(dataSetNameGenerated);

		// Folder Selection
		collection.folderToSelect(selectedFolder, true, false);

		// logout
		login.logout();

	}

	/**
	 * @Author Jeevitha
	 * @dsecription : Verify that delete a collection functionality is working
	 *              proper on Collection’s home page.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-61292", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyCollectionAfterDeleted(String username, String password, String fullname) throws Exception {
		HashMap<String, String> colllectionData = new HashMap<>();
		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Drafts";
		String headerList[] = { Input.collectionDataHeader1, Input.collectionDataHeader2, Input.collectionDataHeader3,
				Input.collectionDataHeader4, Input.collectionDataHeader5, Input.collectionDataHeader6 };
		String headerListDataSets[] = { "Collection Id", "Collection Status" };
		String expectedCollectionStatus = "Draft";
		String collectionID = "";
		String[][] userRolesData = { { username, fullname, "SA" } };

		base.stepInfo("Test case Id: RPMXCON-61292 - O365");
		base.stepInfo("Verify that delete a collection functionality is working proper on Collection’s home page.");

		// Login as User
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManagement.navigateToUsersPAge();
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");

		// Logout
		login.logout();

		// Login as User
		login.loginToSightLine(username, password);

		// Add DataSets
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		colllectionData = collection.dataSetsCreationBasedOntheGridAvailability(firstName, lastName, collectionEmailId,
				selectedApp, colllectionData, selectedFolder, headerList, expectedCollectionStatus, "Button", 3, true,
				"");

		// navigate to Collection page and get the data
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		String dataName = base.returnKey(colllectionData, "", false);
		System.out.println(dataName);
		collectionID = colllectionData.get(dataName);

		// Verify Collection presence
		collection.verifyExpectedCollectionIsPresentInTheGrid(headerListDataSets, dataName, expectedCollectionStatus,
				true, false, "");

		// Click delete and click Yes button
		collection.deleteUsingCollectionName(dataName, true);

		// verify Collection Absence in Manage collection Screen
		boolean collectionAbsence = collection.getCollectionAction(dataName).isElementAvailable(5);

		String passMsg = dataName + " : is not Displayed in Manage Collection Screen";
		String failMsg = dataName + " : is Displayed after deleting";
		base.printResutInReport(collectionAbsence, passMsg, failMsg, "Fail");

		// Logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description :Verify that user can configure collection on "Manage Screen"
	 *              screen
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-61291", enabled = true, groups = { "regression" })
	public void verifyConfigureCollection() throws Exception {
		HashMap<String, String> collectionData = new HashMap<>();
		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Drafts";
		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String dataSourceName = "Automation" + Utility.dynamicNameAppender();

		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };

		base.stepInfo("Test case Id: RPMXCON-61291 - O365");
		base.stepInfo("Verify that user can configure collection on \"Manage Screen\" screen");

		// Login as User
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManagement.navigateToUsersPAge();
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");

		// Logout
		login.logout();

		// Login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// create new Collection
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collection.performCreateNewCollection();

		// Add New Source Location
		collection.performAddNewSource(null, dataSourceName, Input.TenantID, Input.ApplicationID, Input.ApplicationKey);

		// click created source location and verify navigated page
		collectionData = collection.verifyCollectionInfoPage(dataSourceName, collectionName, false);

		// initiate collection process
		collection.selectInitiateCollectionOrClickNext(true, true, true);

		// Add Dataset
		collection.fillingDatasetSelection("Button", firstName, lastName, collectionEmailId, selectedApp,
				collectionData, collectionName, 3, selectedFolder, true, true, true, Input.randomText, true, true,
				"Save", "");

		// Save As Draft
		collection.clickNextBtnOnDatasetTab();
		collection.collectionSaveAsDraft();

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
	public String verifyUserAbleToSaveCollectionAsDraft(String username, String password, String role,
			String actionRole, String actionUserName, String actionPassword, String selectedFolder, String additional1,
			Boolean additional2) throws Exception {

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
		String[][] userRolesData = { { username, role, actionRole } };

		base.stepInfo("Collection as DRAFT - Pre-requesties creation before Depends on Method");

		// Login as User
		login.loginToSightLine(actionUserName, actionPassword);
		userManagement.navigateToUsersPAge();
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");

		// Logout
		login.logout();

		// Login as User
		login.loginToSightLine(username, password);

		// Add DataSets
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		colllectionData = collection.dataSetsCreationBasedOntheGridAvailability(firstName, lastName, collectionEmailId,
				selectedApp, colllectionData, selectedFolder, headerList, expectedCollectionStatus, "Button", 3, true,
				"");

		// navigate to Collection page and get the data
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		dataName = base.returnKey(colllectionData, "", false);
		System.out.println(dataName);
		collectionID = colllectionData.get(dataName);

		// Verify Collection presence
		collection.verifyExpectedCollectionIsPresentInTheGrid(headerListDataSets, dataName, expectedCollectionStatus,
				true, false, "");
		base.passedStep("Pre-requestied created colleciton Name :" + dataName);

		// return dataNmae created / used
		return dataName;
	}

	/**
	 * @author Raghuram A
	 * @throws Exception
	 * @Date: 08/11/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that user should be able to enter the collection name
	 *              same as of deleted collection. RPMXCON-60648
	 */
	@Test(description = "RPMXCON-60648", enabled = true, groups = { "regression" })
	public void createCollectectionWithPrevDeletedName() throws Exception {

		HashMap<String, String> colllectionData = new HashMap<>();
		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Drafts";
		String headerList[] = { Input.collectionDataHeader1, Input.collectionDataHeader2, Input.collectionDataHeader3,
				Input.collectionDataHeader4, Input.collectionDataHeader5, Input.collectionDataHeader6 };
		String headerListDataSets[] = { "Collection Id", "Collection Status" };
		String expectedCollectionStatus = "Draft";
		String collectionID = "";

		base.stepInfo("Test case Id: RPMXCON-60648 - O365");
		base.stepInfo("Verify that user should be able to enter the collection name same as of deleted collection");
		base.stepInfo("**Step-1 Pre-requisites: User should have Dataset, Collection rights should be checked\r\n"
				+ "\r\n" + "		Collection should be configured with the source location/data source**");

		// Pre-requesties
		String dataName = verifyUserAbleToSaveCollectionAsDraft(Input.pa1userName, Input.pa1password,
				"Project Administrator", "SA", Input.sa1userName, Input.sa1password, selectedFolder, "", false);

		// navigate to Collection page and Deletion
		base.stepInfo("Initiation collection deletion");
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collection.deleteUsingCollectionName(dataName, true);

		// verify Collection Absence in Manage collection Screen
		base.printResutInReport(base.ValidateElement_StatusReturn(collection.getCollectionAction(dataName), 3),
				dataName + " deleted Successfully : is not Displayed in Manage Collection Screen",
				dataName + " : is Displayed after deleting", "Fail");

		// navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Add DataSets
		colllectionData = collection.dataSetsCreationBasedOntheGridAvailability(firstName, lastName, collectionEmailId,
				selectedApp, colllectionData, selectedFolder, headerList, null, "Button", 3, false, dataName);

		// navigate to Collection page and get the data
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		String dataNameCreated = base.returnKey(colllectionData, "", false);
		System.out.println(dataNameCreated);
		collectionID = colllectionData.get(dataNameCreated);

		// Verify Collection presence
		collection.verifyExpectedCollectionIsPresentInTheGrid(headerListDataSets, dataNameCreated,
				expectedCollectionStatus, true, false, "");

		// Comparer with the pre-requesties created name
		base.textCompareEquals(dataName, dataNameCreated,
				"Collection able to add successfully with same name as of deleted collection name",
				"Not able to create collection with the previous deleted collection name");

		// navigate to Collection page and Deletion
		base.stepInfo("Initiation collection  deletion");
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collection.collectionDeletion(collectionID);

		// verify Collection Absence in Manage collection Screen
		base.printResutInReport(base.ValidateElement_StatusReturn(collection.getCollectionAction(dataName), 3),
				dataName + " deleted Successfully : is not Displayed in Manage Collection Screen",
				dataName + " : is Displayed after deleting", "Fail");

		// Logout
		login.logout();

	}

	/**
	 * @author Raghuram A
	 * @throws Exception
	 * @Date: 08/11/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that Edit a collection functionality is working proper
	 *              on Collection’s home page.. RPMXCON-61290
	 */
	@Test(description = "RPMXCON-61290", dataProvider = "PaAndRmuUserDetails", enabled = true, groups = {
			"regression" })
	public void editCollectionDraftFunctionalityCheck(String userName, String password, String role, String actionRole)
			throws Exception {

		HashMap<String, String> colllectionData = new HashMap<>();
		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Drafts";
		String headerList[] = { Input.collectionDataHeader1, Input.collectionDataHeader2, Input.collectionDataHeader3,
				Input.collectionDataHeader4, Input.collectionDataHeader5, Input.collectionDataHeader6 };
		String headerListDataSets[] = { "Collection Id", "Collection Status" };
		String expectedCollectionStatus = "Draft";
		String collectionID = "";
		String modifiedCollectionName = "ModifiedCollection" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-61290 - O365");
		base.stepInfo("Verify that Edit a collection functionality is working proper on Collection’s home page.");
		base.stepInfo("**Step-1 Pre-requisites: User should have Dataset, Collection rights should be checked\r\n"
				+ "\r\n" + "		Collection should be configured with the source location/data source**");

		// Pre-requesties
		String dataName = verifyUserAbleToSaveCollectionAsDraft(userName, password, role, actionRole, Input.sa1userName,
				Input.sa1password, selectedFolder, "", false);

		// navigate to Collection page
		base.stepInfo("**Step-3 Click on left menu Datasets > Collections**");
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Add DataSets
		colllectionData = collection.dataSetsCreationBasedOntheGridAvailability(firstName, lastName, collectionEmailId,
				selectedApp, colllectionData, selectedFolder, headerList, expectedCollectionStatus, "Button", 3, false,
				"");

		// navigate to Collection page and get the data
		base.stepInfo("**Step-4 Click on  Action >> Edit Collection\"**");
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		dataName = base.returnKey(colllectionData, "", false);
		System.out.println(dataName);
		collectionID = colllectionData.get(dataName);

		// Verify Collection presence
		collection.verifyExpectedCollectionIsPresentInTheGrid(headerListDataSets, dataName, expectedCollectionStatus,
				true, false, "");

		// Edit Verifications
		collection.getCollectionsPageAction(collectionID).waitAndClick(5);
		collection.getCollectionsPageActionList(collectionID, "Edit").waitAndClick(5);
		base.waitForElement(collection.getCollectioName());
		collection.verifyCurrentTab("Collection Information");
		base.textCompareEquals(collection.getCollectionID().getText(), collectionID, "Collection id is retained ",
				"Collection id not retained");
		base.printResutInReport(base.ValidateElement_PresenceReturn(collection.getDisabledBackBtn()),
				"Back button is disabled as Expected", "Back button is not disabled", "Pass");

		// Modify Collection Name
		base.stepInfo("**Step-5 Now Modify Collection Name >> Click on Next till Summary Page\"**");
		collection.enterCollectionName(modifiedCollectionName);
		collection.nextAction("CollectionTab");
		driver.waitForPageToBeReady();
		collection.nextAction("DataSet");
		driver.waitForPageToBeReady();

		// Save As Draft
		base.stepInfo("**Step-6 Click on \"Save As Draft\"\"**");
		collection.collectionSaveAsDraft();

		// navigate to Collection page and get the data
		base.stepInfo(
				"**Step-7 Now Navigate to \"Manage Collection\" Screen and Verify that Modified Name appears on \"Manage Collection\" screen\"\"**");
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// verify Old old collection name is updated
		base.printResutInReport(base.ValidateElement_StatusReturn(collection.getCollectionAction(dataName), 3),
				dataName + " modified Successfully : Old name is not Displayed in Manage Collection Screen",
				dataName + " : Old name is Displayed after modifying", "Fail");

		// Verify Collection presence
		collection.verifyExpectedCollectionIsPresentInTheGrid(headerListDataSets, modifiedCollectionName,
				expectedCollectionStatus, true, false, "");

		// Logout
		login.logout();

	}

	/**
	 * @author Raghuram.A
	 * @Date: 08/16/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that on click of ‘Save and Add New Dataset’ application
	 *              should save the custodian dataset and should bring up the ‘Add
	 *              dataset’ pop up. RPMXCON-60771
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-60771", enabled = true, groups = { "regression" })
	public void verifySaveAndAddNewDataSetBtnOption() throws Exception {
		HashMap<String, String> colllectionData = new HashMap<>();
		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Drafts";
		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };

		base.stepInfo("Test case Id: RPMXCON-60771 - O365");
		base.stepInfo(
				"Verify that on click of ‘Save and Add New Dataset’ application should save the custodian dataset and should bring up the ‘Add dataset’ pop up");

		// Login as User
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManagement.navigateToUsersPAge();
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");

		// Logout
		login.logout();

		// Login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Add DataSets
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		colllectionData = collection.createNewCollection(colllectionData, collectionName, true, null, false);

		// Click cancel in Folder select Popup in Dataset selection
		collection.fillingDatasetSelection("Button", firstName, lastName, collectionEmailId, selectedApp,
				colllectionData, collectionName, 3, selectedFolder, true, true, true, Input.randomText, true, true,
				"Save & Add New Dataset", "");

		// Cancel Action
		collection.getActionBtn("Cancel").waitAndClick(10);
		driver.waitForPageToBeReady();
		base.printResutInReport(collection.getDataSetSelectionPopDisplay().isDisplayed(),
				"New Add dataset pop-up disapperared after Cancel action",
				"New Add dataset pop-up still remains after Cancel action", "Fail");

		// Logout
		login.logout();
	}

	/**
	 * @author Raghuram A
	 * @throws Exception
	 * @Date: 08/17/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that execute a draft collection functionality is
	 *              working proper on Collection’s home page. RPMXCON-61294
	 */
	@Test(description = "RPMXCON-61294", dataProvider = "PaAndRmuUserDetails", enabled = true, groups = {
			"regression" })
	public void verifyExutionOfDraftStatusCollection(String userName, String password, String role, String actionRole)
			throws Exception {

		HashMap<String, String> colllectionData = new HashMap<>();
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
		String[] statusListToVerify = { Input.creatingDSstatus };

		base.stepInfo("Test case Id: RPMXCON-61294 - O365");
		base.stepInfo(
				"Verify that execute a draft collection functionality is working proper on Collection’s home page.");
		base.failedMessage("Make sure E-mail source folder has datas - If not will end up with Data retrival error");
		base.stepInfo("**Step-1 Pre-requisites: User should have Dataset, Collection rights should be checked\r\n"
				+ "\r\n" + "		Collection should be configured with the source location/data source**");

		// Pre-requesties
		String dataName = verifyUserAbleToSaveCollectionAsDraft(userName, password, role, actionRole, Input.sa1userName,
				Input.sa1password, selectedFolder, "", false);

		// navigate to Collection page
		base.stepInfo("**Step-3 Click on left menu Datasets > Collections**");
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Add DataSets
		colllectionData = collection.dataSetsCreationBasedOntheGridAvailability(firstName, lastName, collectionEmailId,
				selectedApp, colllectionData, selectedFolder, headerList, expectedCollectionStatus, "Button", 3, false,
				"");

		// navigate to Collection page and get the data
		base.stepInfo("**Step-4 Click on  Action >> Edit Collection\"**");
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		dataName = base.returnKey(colllectionData, "", false);
		System.out.println(dataName);
		collectionID = colllectionData.get(dataName);

		// Verify Collection presence
		collection.verifyExpectedCollectionIsPresentInTheGrid(headerListDataSets, dataName, expectedCollectionStatus,
				true, false, "");

		// Execute / Start collection Verifications
		collection.getCollectionsPageAction(collectionID).waitAndClick(5);
		collection.getCollectionsPageActionList(collectionID, "Start Collection").waitAndClick(5);
		driver.waitForPageToBeReady();
		base.VerifySuccessMessage("Collection extraction process started successfully.");

		// Verify Collection presence with expected Status
		collection.verifyExpectedCollectionStatus(false, headerListDataSets, dataName, statusListToVerify, 10, true,
				false, "", "");

		// Logout
		login.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that Dataset Selection tab should present the grid with
	 *              the list of all configured custodian datasets in the collection
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-60754", enabled = true, groups = { "regression" })
	public void verifyMouseHoverPopupMsg() throws Exception {
		HashMap<String, String> collectionData = new HashMap<>();
		List<String> custodianDetails = new ArrayList();

		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder1 = "Drafts";

		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };

		base.stepInfo("Test case Id: RPMXCON-60754 - O365");
		base.stepInfo(
				"Verify that Dataset Selection tab should present the grid with the list of all configured custodian datasets in the collection ");

		// Login as User
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManagement.navigateToUsersPAge();
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");

		// Logout
		login.logout();

		// Login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Add DataSets
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collectionData = collection.createNewCollection(collectionData, collectionName, true, null, false);

		// Add Dataset By Applying Filter
		custodianDetails = collection.fillingDatasetSelection("Button", firstName, lastName, collectionEmailId,
				selectedApp, collectionData, collectionName, 3, selectedFolder1, true, true, true, Input.randomText,
				false, false, "", "");

		// Select multiple Folders And Save.
		collection.editDatasetAndVerify(false, null, false, null, null, true, false, selectedFolder1,
				custodianDetails.get(0), null, false);
		collection.SaveActionInDataSetPopup(true, null, null, null, null, null, null, Input.randomText, false,
				"Dataset added successfully.");

		// verify Mouse hover Popup Message is Displyed .
		collection.verifyMouseOverAction(Input.collectionDataHeader5, collectionEmailId);

		// Logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that 'View Datasets' of a collection functionality is
	 *              working proper on Collection’s home page.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-61295", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyViewDatasetsIsAsExpected(String username, String password, String fullname) throws Exception {
		HashMap<String, String> collectionData = new HashMap<>();
		List<String> custodianDetails = new ArrayList();

		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder1 = "Drafts";

		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String[][] userRolesData = { { username, fullname, "SA" } };

		base.stepInfo("Test case Id: RPMXCON-61295 - O365");
		base.stepInfo(
				"Verify that 'View Datasets' of a collection functionality is working proper on Collection’s home page.");

		// Login as User
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManagement.navigateToUsersPAge();
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");

		// Logout
		login.logout();

		// Login as User
		login.loginToSightLine(username, password);

		// get other dataset tile view
		dataSets.navigateToDataSetsPage();
		String otherTileView = dataSets.getTileViewType();

		// Add New Collection Or get Already Present completed Collection Details
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collectionData = collection.createNewCollection(collectionData, collectionName, true, "Completed", true);

		// navigate to Collection page and get the data
		collectionName = base.returnKey(collectionData, "", false);

		// Click View Dataset or Create collection and click View Dataset
		if (collection.getCollectionNameElement(collectionName).isElementAvailable(2)) {
			base.stepInfo(collectionName + " : is Completed and Displayed in Collections Page");
		} else {
			custodianDetails = collection.fillingDatasetSelection("Button", firstName, lastName, collectionEmailId,
					selectedApp, collectionData, collectionName, 3, selectedFolder1, true, true, true, Input.randomText,
					false, false, "", "");

			collection.clickNextBtnOnDatasetTab();
			collection.getStartBtn().waitAndClick(10);
		}

		// click view dataset btn from collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collection.clickViewDataset(collectionName);

		// verify is it navigating to datasets page
		driver.waitForPageToBeReady();
		base.verifyUrlLanding(Input.url + "en-us/ICE/Datasets", "Navigated To Dataset Page",
				"Navigation is not as expected");

		// verify if filter applied and verify collection tile view
		dataSets.verifysearchBoxValue(collectionName, otherTileView);

		// Logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that Sorting is working proper when user clicks on
	 *              column name "Total Retrieved Count" on "Manage Collections"
	 *              screen (Grid).
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-61635", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifySortingOfTotaRetrieved(String username, String password, String fullname) throws Exception {
		String selectedFolder = "Inbox";
		String sortType = "Descending";

		base.stepInfo("Test case Id: RPMXCON-61635 - O365");
		base.stepInfo(
				"Verify that Sorting is working proper when user clicks on column name \"Total Retrieved Count\" on \"Manage Collections\" screen (Grid).");

		// Pre-requesties
		String collectionName = verifyUserAbleToSaveCollectionAsDraft(username, password, fullname, "SA",
				Input.sa1userName, Input.sa1password, selectedFolder, "", false);

		// click once and check is it sorted in Ascending
		driver.Navigate().refresh();
		collection.verifySortingOrderOfCollectionPage(true, Input.totalRetrievedCount, Input.sortType);

		// click once again and check is it sorted in Descending
		collection.verifySortingOrderOfCollectionPage(true, Input.totalRetrievedCount, sortType);

		// Logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that "Total Retrieved Count" is NOT editable on "Manage
	 *              Collections" screen (Grid). screen (Grid).
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-61637", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyTotalRetrievedCountIsNotEditable(String username, String password, String fullname)
			throws Exception {
		String selectedFolder = "Inbox";

		base.stepInfo("Test case Id: RPMXCON-61637 - O365");
		base.stepInfo("Verify that \"Total Retrieved Count\" is NOT editable on \"Manage Collections\" screen (Grid).");

		// Pre-requesties
		String collectionName = verifyUserAbleToSaveCollectionAsDraft(username, password, fullname, "SA",
				Input.sa1userName, Input.sa1password, selectedFolder, "", false);

		// Try to select The Total Retrieved count for editing
		int index = base.getIndex(collection.getDataSetDetailsHeader(), Input.totalRetrievedCount);
		boolean status = collection.getDataSetDetails(collectionName, index).Selected();

		// verify if it is editable or Not
		if (!status) {
			base.passedStep("Total Retrieved Count is Not Editable");
		} else {
			base.failedStep("Total Retrieved Count is Editable");
		}

		// Logout
		login.logout();
	}

	/**
	 * @Author Mohan
	 * @Description : Verify that when User Refresh data on "Select Folders to
	 *              Collect" data then entire UI is NOT frozen on "Add dataset" pop
	 *              up screen.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-63870", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyAddDatasetsSelectFolderClickRefreshButtonLoadingFolderIcon(String username, String password,
			String fullname) throws Exception {

		base.stepInfo("Test case Id: RPMXCON-63870 - O365");
		base.stepInfo(
				"Verify that when User Refresh data on 'Select Folders to Collect' data then entire UI is NOT frozen on 'Add dataset' pop up screen.");

		String[][] userRolesData = { { username, fullname, "SA" } };
		String dataSourceName = "Automation" + Utility.dynamicNameAppender();
		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Inbox";

		// Login as User
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");

		login.logout();

		// Login as User
		login.loginToSightLine(username, password);

		// navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Click create New Collection
		collection.performCreateNewCollection();

		// Select source and Click create New Collection
		String srcLocation = collection.selectSourceFromTheListAvailable();

		// click created source location and verify navigated page
		HashMap<String, String> collectionInfoPage = collection.verifyCollectionInfoPage(srcLocation, dataSourceName,
				false);

		// initiate collection process
		collection.selectInitiateCollectionOrClickNext(true, true, true);

		// verify Dataset page
		collection.verifyDatasetsPage();

		// Add DataSets
		String dataSetNameGenerated = collection.addDataSetWithHandles("Button", firstName, lastName, collectionEmailId,
				selectedApp, collectionInfoPage, dataSourceName, 3);
		System.out.println(dataSetNameGenerated);

		// Verify Loading Folder
		collection.verifyCancelSaveAddNewDatasetSave();

		// Select Folder
		collection.folderToSelect(selectedFolder, false, true);
		base.passedStep(
				"When User Refresh data on 'Select Folders to Collect' data then entire UI should NOT frozen on 'Add dataset' pop up screen. and User navigated to other pages successfully");

		// logout
		login.logout();

	}

	/**
	 * @Author Mohan
	 * @Description : Verify that when User Clicks on "Select Folders to Collect"
	 *              data then entire UI is NOT frozen on "Add dataSet" pop up
	 *              screen.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-63869", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyAddDatasetsSelectFolderLoadingFolderIcon(String username, String password, String fullname)
			throws Exception {

		base.stepInfo("Test case Id: RPMXCON-63869 - O365");
		base.stepInfo(
				"Verify that when User Clicks on 'Select Folders to Collect' data then entire UI is NOT frozen on 'Add dataSet' pop up screen.");

		String[][] userRolesData = { { username, fullname, "SA" } };
		String dataSourceName = "Automation" + Utility.dynamicNameAppender();
		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Inbox";

		// Login as User
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");

		login.logout();

		// Login as User
		login.loginToSightLine(username, password);

		// navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Click create New Collection
		collection.performCreateNewCollection();

		// Select source and Click create New Collection
		String srcLocation = collection.selectSourceFromTheListAvailable();

		// click created source location and verify navigated page
		HashMap<String, String> collectionInfoPage = collection.verifyCollectionInfoPage(srcLocation, dataSourceName,
				false);

		// initiate collection process
		collection.selectInitiateCollectionOrClickNext(true, true, true);

		// verify Dataset page
		collection.verifyDatasetsPage();

		// Add DataSets
		String dataSetNameGenerated = collection.addDataSetWithHandles("Button", firstName, lastName, collectionEmailId,
				selectedApp, collectionInfoPage, dataSourceName, 3);
		System.out.println(dataSetNameGenerated);

		// Verify Loading Folder in
		collection.verifyLoadingFolderMessage();

		// Select Folder
		collection.folderToSelect(selectedFolder, false, true);
		base.passedStep(
				"When User Refresh data on 'Select Folders to Collect' data then entire UI should NOT frozen on 'Add dataset' pop up screen. and User navigated to other pages successfully");

		// logout
		login.logout();

	}

	/**
	 * @author Raghuram A
	 * @throws Exception
	 * @Date: 08/18/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that "Download Final Report" functionality is working
	 *              fine on "Manage Collections" screen. RPMXCON-61042
	 */
	@Test(description = "RPMXCON-61042", dataProvider = "PaAndRmuUserDetails", enabled = true, groups = {
			"regression" })
	public void verifyFileReportFormat(String userName, String password, String role, String actionRole)
			throws Exception {

		HashMap<String, String> colllectionData = new HashMap<>();
		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Inbox";
		String headerList[] = { Input.collectionDataHeader1, Input.collectionDataHeader2, Input.collectionDataHeader3,
				Input.collectionDataHeader4, Input.collectionDataHeader5, Input.collectionDataHeader6 };
		String headerListDataSets[] = { "Collection Id", "Collection Status", "Error Status" };
		String expectedCollectionStatus = "Draft";
		String collectionID = "";
		String[] statusListToVerify = { Input.creatingDSstatus, Input.retreivingDSstatus, Input.virusScanStatus,
				Input.copyDSstatus };
		String[] statusList = { "Completed" };
		String expectedFileFormat = "xlsx";

		base.stepInfo("Test case Id: RPMXCON-61042 - O365");
		base.stepInfo(
				"Verify that \"Download Final Report\" functionality is working fine on \"Manage Collections\" screen.");
		base.failedMessage("Make sure E-mail source folder has datas - If not will end up with Data retrival error");
		base.stepInfo("**Step-1 Pre-requisites: User should have Dataset, Collection rights should be checked\r\n"
				+ "\r\n" + "		Collection should be configured with the source location/data source**");

		// Pre-requesties
		String dataName = verifyUserAbleToSaveCollectionAsDraft(userName, password, role, actionRole, Input.sa1userName,
				Input.sa1password, selectedFolder, "", false);

		// navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Add DataSets
		colllectionData = collection.dataSetsCreationBasedOntheGridAvailability(firstName, lastName, collectionEmailId,
				selectedApp, colllectionData, selectedFolder, headerList, expectedCollectionStatus, "Button", 3, false,
				"");

		// navigate to Collection page and get the data
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		dataName = base.returnKey(colllectionData, "", false);
		System.out.println(dataName);
		collectionID = colllectionData.get(dataName);

		// Verify Collection presence
		collection.verifyExpectedCollectionIsPresentInTheGrid(headerListDataSets, dataName, expectedCollectionStatus,
				true, false, "");

		// Execute / Start collection Verifications
		collection.getCollectionsPageAction(collectionID).waitAndClick(5);
		collection.getCollectionsPageActionList(collectionID, "Start Collection").waitAndClick(5);
		driver.waitForPageToBeReady();
		base.VerifySuccessMessage("Collection extraction process started successfully.");

		// Verify Collection presence with expected Status
		collection.verifyExpectedCollectionStatus(false, headerListDataSets, dataName, statusListToVerify, 10, true,
				false, "", "");

		// Completed status check
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, dataName, statusList, 10);
		driver.waitForPageToBeReady();

		// Initial Notification count
		int Bgcount = base.initialBgCount();

		// Download Report
		collection.clickDownloadReportLink(dataName, headerListDataSets, "Error Status", false, "");
		driver.waitForPageToBeReady();

		// Check NotificationCount
		base.checkNotificationCount(Bgcount, 1);
		base.notificationSelection("", false);
		base.waitTime(5);// for abnormal load time while downloading file

		// Format validation
		String fileName = base.GetLastModifiedFileName();
		base.validateFileFormat(fileName, expectedFileFormat);

		// Delete downloaded file
		base.stepInfo("Initiating delete for downloaded file");
		Files.deleteIfExists(Paths.get(fileName));

		// Logout
		login.logout();

	}

	/**
	 * @author Raghuram A
	 * @throws Exception
	 * @Date: 08/22/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that user should be able to add the same custodian
	 *              dataset into the collection that was deleted. RPMXCON-60773
	 */
	@Test(description = "RPMXCON-60773", enabled = true, groups = { "regression" })
	public void reAddSameDatasetNameAsDeleted() throws Exception {
		HashMap<String, String> collectionData = new HashMap<>();
		List<String> custodianDetails = new ArrayList();

		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Drafts";
		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };

		base.stepInfo("Test case Id: RPMXCON-60773 - O365");
		base.stepInfo(
				"Verify that user should be able to add the same custodian dataset into the collection that was deleted");

		// Login as User
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManagement.navigateToUsersPAge();
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");

		// Logout
		login.logout();

		// Login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Add DataSets
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collectionData = collection.createNewCollection(collectionData, collectionName, true, null, false);

		// Add Dataset By Applying Filter
		custodianDetails = collection.fillingDatasetSelection("Button", firstName, lastName, collectionEmailId,
				selectedApp, collectionData, collectionName, 3, selectedFolder, true, true, true, Input.randomText,
				true, true, "Save", "");

		// Delete dataset with confirmation message as No
		driver.waitForPageToBeReady();
		collection.confirmationDataSetDelAction(true, collectionEmailId, "", "No", "", "");

		// Delete dataset with confirmation message as Yes
		driver.waitForPageToBeReady();
		collection.confirmationDataSetDelAction(true, collectionEmailId, "", "Yes", "Dataset deleted successfully.",
				"");

		// Verify dataSet is empty
		base.printResutInReport(
				base.ValidateElement_PresenceReturn(collection.getDatasetHomePageNoCutodainAvailableText()),
				"DataSet is empty", "DataSet is not Empty", "Pass");

		// Re-Add same Custodian details
		custodianDetails.clear();
		base.stepInfo("Re-Adding the same dataSet");
		custodianDetails = collection.fillingDatasetSelection("Button", firstName, lastName, collectionEmailId,
				selectedApp, collectionData, collectionName, 3, selectedFolder, true, true, true, Input.randomText,
				true, true, "Save", "");

		// Logout
		login.logout();
	}

	/**
	 * @Author Mohan
	 * @Description : Verify that column name "Total Retrieved Count" is available
	 *              on "Collections Details Pop up" screen (Grid).
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-61657", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyColumnNameTotalRetrievedCountInCollectionsDetailsPopUp(String username, String password,
			String fullname) throws Exception {

		base.stepInfo("Test case Id: RPMXCON-61657 - O365");
		base.stepInfo(
				"Verify that column name 'Total Retrieved Count' is available on 'Collections Details Pop up' screen (Grid).");

		String[][] userRolesData = { { username, fullname, fullname } };

		// Login as User
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");

		login.logout();

		// Login as User
		login.loginToSightLine(username, password);

		// navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// verify collections Page
		int collectionRowCount = collection.getTotalCountOfCollectionPresentInCollectionsPage(5);
		System.out.println(collectionRowCount);

		// verify 'Collections Details Pop up'
		collection.selectCollectionIdFromTheCollectionListPresent(collectionRowCount);

		// logout
		login.logout();

	}

	/**
	 * @Author Mohan
	 * @Description : Verify that for any errored dataset - Column “Error Count“
	 *              displays in all Data tables. (Dataset Creation) in "Error
	 *              section pop up" screen.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-61661", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyErroredDatasetsInCollectionWizard(String username, String password, String fullname)
			throws Exception {

		base.stepInfo("Test case Id: RPMXCON-61661 - O365");
		base.stepInfo(
				"Verify that for any errored dataset - Column “Error Count“ displays in all Data tables. (Dataset Creation) in \"Error section pop up\" screen.");

		String[][] userRolesData = { { username, fullname, fullname } };
		String dataSourceName = "Automation" + Utility.dynamicNameAppender();
		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Archive";

		// Login as User
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");

		login.logout();

		// Login as User
		login.loginToSightLine(username, password);

		// navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Click create New Collection
		collection.performCreateNewCollection();

		// Select source and Click create New Collection
		String srcLocation = collection.selectSourceFromTheListAvailable();

		// click created source location and verify navigated page
		HashMap<String, String> collectionInfoPage = collection.verifyCollectionInfoPage(srcLocation, dataSourceName,
				false);

		// initiate collection process
		collection.selectInitiateCollectionOrClickNext(true, true, true);

		// Add DataSets
		String dataSetNameGenerated = collection.addDataSetWithHandles("Button", firstName, lastName, collectionEmailId,
				selectedApp, collectionInfoPage, dataSourceName, 3);
		System.out.println(dataSetNameGenerated);

		// Select Folder
		collection.folderToSelect(selectedFolder, true, true);
		base.waitForElement(collection.getActionBtn("Save"));
		collection.getActionBtn("Save").waitAndClick(5);

		base.waitForElement(collection.getConfirmationBtnAction("Confirm"));
		collection.getConfirmationBtnAction("Confirm").waitAndClick(5);
		base.VerifySuccessMessage("Dataset added successfully.");

		// Start A Collection
		collection.clickOnNextAndStartAnCollection();
		collection.verifyViewErrorDatasetsLink();

		// logout
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
