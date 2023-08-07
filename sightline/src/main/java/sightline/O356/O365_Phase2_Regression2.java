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

public class O365_Phase2_Regression2 {

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

	@DataProvider(name = "PaAndRmuUserDetails")
	public Object[][] PaAndRmuUserDetails() {
		Object[][] users = { 
				{ Input.pa1userName, Input.pa1password, "Project Administrator", "SA" },
				{ Input.rmu1userName, Input.rmu1password, "Review Manager", "SA" } };
		return users;
	}

	@DataProvider(name = "AllTheUsers")
	public Object[][] AllTheUsers() {
		Object[][] users = { { Input.sa1userName, Input.sa1password }, { Input.pa1userName, Input.pa1password },
				{ Input.rmu1userName, Input.rmu1password }, { Input.rev1userName, Input.rev1password } };
		return users;
	}

	@DataProvider(name = "PaAndRmuUser")
	public Object[][] PaAndRmuUser() {
		Object[][] users = { 
				{ Input.rmu1userName, Input.rmu1password, "Review Manager" },
				{ Input.pa1userName, Input.pa1password, "Project Administrator" }, 
				};
		return users;
	}

	/**
	 * @Author Mohan
	 * @Description : Verify that User can access 'Manage Collections' screen
	 *              navigating through Dataset >> Manage Collections menu item.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-60962", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyManageCollectionScreenNavigateToManageCollectionPage(String username, String password,
			String fullname) throws Exception {

		base.stepInfo("Test case Id: RPMXCON-60962 - O365");
		base.stepInfo(
				"Verify that User can access 'Manage Collections' screen navigating through Dataset >> Manage Collections menu item.");

		String[][] userRolesData = { { username, fullname, fullname } };

		// Login as User
		login.loginToSightLine(username, password);
		userManagement.navigateToUsersPAge();
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");

		// To add New Source Location
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collection.verifyCollectionPage();

		// Logout
		login.logout();
	}

	/**
	 * @Author Mohan
	 * @Description : Verify that application is allowing to add a new source
	 *              location from Collections >> Set up Source >> Add New Source
	 *              Location screen.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-60852", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyCollectionWizardAddNewSourceLocation(String username, String password, String fullname)
			throws Exception {

		base.stepInfo("Test case Id: RPMXCON-60852 - O365");
		base.stepInfo(
				"Verify that from Collection Wizard user should be able to add new source location 'Create New collection' > 'Set up a source location' link");

		String[][] userRolesData = { { username, fullname, fullname } };
		String dataname = "Enron Office 1" + Utility.randomCharacterAppender(3);

		// Login as User
		login.loginToSightLine(username, password);
		userManagement.navigateToUsersPAge();
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");

		// navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// To add New Source Location
		base.waitForElement(collection.getNewCollectionBtn());
		collection.getNewCollectionBtn().waitAndClick(5);
		collection.performAddNewSource(null, dataname, Input.TenantID, Input.ApplicationID, Input.ApplicationKey);

		// To verify source name entered
		dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);
		source.verifySourceLocationName(dataname);

		// Logout
		login.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that mandatory fields display with red asterisk on
	 *              "Source Location" screen.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-60812", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyColorOfAsterisk(String username, String password, String fullname) throws Exception {

		base.stepInfo("Test case Id: RPMXCON-60812 - O365");
		base.stepInfo("Verify that mandatory fields display with red asterisk on \"Source Location\" screen.");

		String[][] userRolesData = { { username, fullname, fullname } };

		// Login as User
		login.loginToSightLine(username, password);
		userManagement.navigateToUsersPAge();
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");

		// navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Click create New Collection
		collection.performCreateNewCollection();

		// verify Add new source Pop up Attributes
		collection.verifyAddNewSourcePopupAttributes();

		// Verify Error Message In Add new source Popup
		driver.Navigate().refresh();
		collection.performAddNewSource(null, "", "", "", "");

		// Logout
		login.logout();
	}

	/**
	 * @author Raghuram A
	 * @throws Exception
	 * @Date: 07/28/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that user can edit the collection in draft mode which
	 *              is configured with source location. RPMXCON-60616
	 */
	@Test(description = "RPMXCON-60616", enabled = true, groups = { "regression" })
	public void editCollectionDraftCheck() throws Exception {

		HashMap<String, String> colllectionData = new HashMap<>();
		String collectionEmailId = Input.collectionDataEmailId;
		String collectiondataListVal = Input.collectionDatalistval;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Drafts";
		String headerList[] = { Input.collectionDataHeader1, Input.collectionDataHeader2, Input.collectionDataHeader3,
				Input.collectionDataHeader4, Input.collectionDataHeader5, Input.collectionDataHeader6 };
		String headerListDataSets[] = { "Collection Id", "Collection Status" };
		String expectedCollectionStatus = "Draft";
		String collectionID = "";
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };

		base.stepInfo("Test case Id: RPMXCON-60616 - O365");
		base.stepInfo(
				"Verify that user can edit the collection in draft mode which is configured with source location");
		base.stepInfo("**Step-1 Pre-requisites: User should have Dataset, Collection rights should be checked\r\n"
				+ "\r\n" + "		Collection should be configured with the source location/data source**");

		// Login as User
		base.stepInfo("**Step-2 Login as Project Admin/RMU**");
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManagement.navigateToUsersPAge();
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");

		// Logout
		login.logout();

		// Login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// navigate to Collection page
		base.stepInfo("**Step-3 Click on left menu Datasets > Collections**");
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Add DataSets
		colllectionData = collection.dataSetsCreationBasedOntheGridAvailability(firstName,collectiondataListVal , lastName, collectionEmailId,
				selectedApp, colllectionData, selectedFolder, headerList, expectedCollectionStatus, "Button", 3, false,
				"",false,false,false,"",false,"",false);

		// navigate to Collection page and get the data
		base.stepInfo("**Step-4 Click the 'Actions' link for the collection in Draft mode and select Edit action**");
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		String dataName = base.returnKey(colllectionData, "", false);
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

		// navigate to Collection page and Deletion
		base.stepInfo("Initiation collection  deletion");
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collection.collectionDeletion(collectionID);

		// Logout
		login.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that 'Office 365' gets selected in \"Data Source Type\"
	 *              dropdown by default on Collections >> Add New Source Location UI
	 *              screen.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-60810", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyDefaultSourceType(String username, String password, String fullname) throws Exception {
		String dataname = "Automation" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-60810 - O365");
		base.stepInfo(
				"Verify that 'Office 365' gets selected in \"Data Source Type\" dropdown by default on Collections >> Add New Source Location UI screen.");

		String[][] userRolesData = { { username, fullname, "SA" } };

		// Assigning Collection Access to User
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManagement.navigateToUsersPAge();
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");

		// Logout
		login.logout();

		// Login as User
		login.loginToSightLine(username, password);

		// navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Click create New Collection
		collection.performCreateNewCollection();

		// Verify by default Microsoft 365 is selected and Add new source location
		collection.performAddNewSource(null, dataname, Input.TenantID, Input.ApplicationID, Input.ApplicationKey);

		// verify Added source location is displayed
		collection.verifyAddedSourceLocation(dataname, null);

		// delete created source location
		dataSets.navigateToDataSets("Source", "Collection/SourceLocation");
		source.deleteSourceLocation(dataname, false);

		// Logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that 'Office 365' gets selected in "Data Source Type"
	 *              dropdown by default on Dataset >> Source Location >> "Add New
	 *              Source Location" screen.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-60809", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyDefaultSourceTypeInSrcePage(String username, String password, String fullname) throws Exception {
		String dataname = "Automation" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-60809 - O365");
		base.stepInfo(
				"Verify that 'Office 365' gets selected in \"Data Source Type\" dropdown by default on Dataset >> Source Location >> \"Add New Source Location\" screen.");

		String[][] userRolesData = { { username, fullname, "SA" } };

		// Assigning Collection Access to User
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManagement.navigateToUsersPAge();
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");

		// Logout
		login.logout();

		// Login as User
		login.loginToSightLine(username, password);

		// navigate to Collection page
		dataSets.navigateToDataSets("Source", "Collection/SourceLocation");

		// Verify by default Microsoft 365 is selected and Add new source location
		collection.performAddNewSource(null, dataname, Input.TenantID, Input.ApplicationID, Input.ApplicationKey);

		// delete created source location
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		source.deleteSourceLocation(dataname, false);

		// Logout
		login.logout();
	}

	/**
	 * @return
	 * @Author Jeevitha
	 * @Description : Verify that user should be able to ‘Save’ the collection as a
	 *              draft
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-60970", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyUserAbleToSaveCollectionAsDraft(String username, String password, String fullname)
			throws Exception {
		HashMap<String, String> colllectionData = new HashMap<>();
		String collectionEmailId = Input.collectionDataEmailId;
		String collectiondataListVal = Input.collectionDatalistval;
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

		base.stepInfo("Test case Id: RPMXCON-60970 - O365");
		base.stepInfo("Verify that user should be able to ‘Save’ the collection as a draft");

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
		colllectionData = collection.dataSetsCreationBasedOntheGridAvailability(firstName,collectiondataListVal, lastName, collectionEmailId,
				selectedApp, colllectionData, selectedFolder, headerList, null, "Button", 3, true, "",false,false,false,"",false,"",false);

		// navigate to Collection page and get the data
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		String dataName = base.returnKey(colllectionData, "", false);
		System.out.println(dataName);
		collectionID = colllectionData.get(dataName);

		// Verify Collection presence
		collection.verifyExpectedCollectionIsPresentInTheGrid(headerListDataSets, dataName, expectedCollectionStatus,
				true, false, "");

		// Logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that user can configure the collection with
	 *              ‘Automatically Initiate processing’ and can move to ‘Next’ step
	 *              of collection
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-60651", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyAutoInitiateProcess(String username, String password, String fullname) throws Exception {
		String dataname = "Automation" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-60651 - O365");
		base.stepInfo(
				"Verify that user can configure the collection with ‘Automatically Initiate processing’ and can move to ‘Next’ step of collection");

		String[][] userRolesData = { { username, fullname, "SA" } };

		// Assigning Collection Access to User
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManagement.navigateToUsersPAge();
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");

		// Logout
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
		collection.verifyCollectionInfoPage(srcLocation, dataname, false);

		// initiate collection process
		collection.selectInitiateCollectionOrClickNext(true, true, true);

		// Logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that error message should be displayed if user adds
	 *              collection name same as of existing collection
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-60649", enabled = true, groups = { "regression" })
	public void verifErroMsgWhenEnteringSameColName() throws Exception {
		String expectedErrorMsg = "16001000009 : Collection already exists. Please enter unique name.";
		String collectionNewName = "Collection" + Utility.dynamicNameAppender();

		HashMap<String, String> colllectionData = new HashMap<>();
		String collectionEmailId = Input.collectionDataEmailId;
		String collectiondataListVal = Input.collectionDatalistval;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Drafts";
		String headerList[] = { Input.collectionDataHeader1, Input.collectionDataHeader2, Input.collectionDataHeader3,
				Input.collectionDataHeader4, Input.collectionDataHeader5, Input.collectionDataHeader6 };
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };

		// Login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("--------------------------------------------");
		base.stepInfo("Test case Id: RPMXCON-60649 - O365");
		base.stepInfo(
				"Verify that error message should be displayed if user adds collection name same as of existing collection");

		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, Input.pa1password);

		// Pre-Requisite
		// Add DataSets To draft
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		colllectionData = collection.dataSetsCreationBasedOntheGridAvailability(firstName,collectiondataListVal, lastName, collectionEmailId,
				selectedApp, colllectionData, selectedFolder, headerList, null, "Button", 3, true, "",false,false,false,"",false,"",false);

		// navigate to Collection page and get the data
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		String collectionName = base.returnKey(colllectionData, "", false);

		// navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Click create New Collection
		collection.performCreateNewCollection();

		// Select source and Click create New Collection
		String srcLocation = collection.selectSourceFromTheListAvailable();

		// click created source location and verify navigated page
		collection.verifyCollectionInfoPage(srcLocation, collectionName, false);

		// initiate collection process & click next Btn
		collection.selectInitiateCollectionOrClickNext(false, true, false);

		// verify Error Message
		base.VerifyErrorMessage(expectedErrorMsg);

		// enter new collection name
		collection.getCollectioName().waitAndClick(10);
		collection.getCollectioName().SendKeys(collectionNewName);
		base.stepInfo("Entered New Collection Name : " + collectionNewName);

		// initiate collection process & click next Btn
		collection.selectInitiateCollectionOrClickNext(false, true, true);

		// navigate to Collection page and Deletion
		base.stepInfo("Initiate collection  deletion");
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collection.deleteUsingCollectionName(collectionName, true);

		// Logout
		login.logout();
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
		String collectiondataListVal = Input.collectionDatalistval;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String rootfoldername=firstName + " " + lastName + " ("
				+ collectionEmailId + ")";;

		// Login As PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		// selecting project
		base.selectproject(Input.ingestDataProject);
		base.stepInfo("User successfully logged into slightline webpage  PA as with " + Input.pa1userName + "");

		// Login as User and verify Module Access
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, Input.pa1password);

		// Create new Collection
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Click create New Collection with datasets
		collectionData = collection.createNewCollection(collectionData, collectionName, true, null, false);
		collection.addNewDataSetCLick("Button");
		collection.selectCustodianInAddNewDataSetPopUp(firstName, collectiondataListVal, collectionEmailId, selectedApp);

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
		String collectiondataListVal = Input.collectionDatalistval;
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
		// selecting project
		base.selectproject(Input.ingestDataProject);
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
		collection.fillinDS(dataSourceName,collectiondataListVal, firstName, lastName, collectionEmailId, selectedApp, collectionInfoPage,
				selectedFolder, headerListDS, "Button", 3, false, "Save",false,false,false,"",false,
				"",false);

		// Start A Collection
		collection.clickOnNextAndStartAnCollection();
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		driver.waitForPageToBeReady();

		// Verify Collection presence with expected Status
		collection.verifyExpectedCollectionStatus(false, headerListDataSets, dataSourceName, statusListToVerify, 25,
				true, false, "", "");

		// Completed status check
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, dataSourceName, statusList, 25);
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
		String collectiondataListVal = Input.collectionDatalistval;
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
		// selecting project
		base.selectproject(Input.ingestDataProject);
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
		collection.fillinDS(dataSourceName,collectiondataListVal, firstName, lastName, collectionEmailId, selectedApp, collectionInfoPage,
				selectedFolder, headerListDS, "Button", 3, false, "Save", false,false,false,"",false,
				"",false);

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
		collection.verifyExpectedCollectionStatus(false, headerListDataSets, dataSourceName, statusListToVerify, 25,
				true, false, "", "");

		// Completed status check
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, dataSourceName, statusList, 25);
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
		// selecting project
		base.selectproject(Input.ingestDataProject);
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
		// selecting project
		base.selectproject(Input.ingestDataProject);
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
		// selecting project
		base.selectproject(Input.ingestDataProject);
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
		// selecting project
		base.selectproject(Input.ingestDataProject);
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
		// selecting project
		base.selectproject(Input.ingestDataProject);
		// Pre-requesties - Access verification
		base.stepInfo("Collection Access Verification");
		userManagement.verifyCollectionAccess(Input.ingestDataProject, userRolesData, Input.sa1userName, Input.sa1password, password);
		
		base.selectproject(Input.ingestDataProject);
		// Collection Draft creation
		colllectionData = collection.verifyUserAbleToSaveCollectionAsDraft(username, password, userRole, "SA",
				Input.sa1userName, Input.sa1password, selectedFolder, "Draft", false);
		collectionId = base.returnKey(colllectionData, "", false);
		collectionName = colllectionData.get(collectionId);
		driver.Navigate().refresh();
		base.waitTime(2);
		// Edit The collection which is in Draft
		collection.getCollectionsPageAction(collectionId).javascriptclick(collection.getCollectionsPageAction(collectionId));
		collection.getCollectionsPageActionList(collectionId, "Edit").javascriptclick(collection.getCollectionsPageActionList(collectionId, "Edit"));
		
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
		String collectiondatalistVal2 = Input.collection2nddatalistVal;
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
		// selecting project
		base.selectproject(Input.ingestDataProject);
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
				collection2ndEmailId, true, true, "Archive", "Archive", "Disabled", true,false);
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

	/**
	 * @author Jeevitha
	 * @throws Exception
	 * @Description : Verify that when collection gets failed then it displays in
	 *              Red colour coded on "Manage Collections" screen.[RPMXCON-61208 ]
	 */
	@Test(description = "RPMXCON-61208", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyColourOfFailedStatus(String username, String password, String userRole) throws Exception {
		String selectedFolder = "Archive";
		String collectionName = "";
		String[] statusListToVerify = { Input.creatingDSstatus, Input.retreivingDSstatus };
		String headerListDataSets[] = { Input.collectionIdHeader, Input.collectionStatusHeader, "Error Status",
				Input.progressBarHeader, Input.actionColumnName };
		String[] statusList = { "failed" };
		String[][] userRolesData = { { username, userRole, "SA" } };

		base.stepInfo("Test case Id: RPMXCON-61208 - O365");
		base.stepInfo(
				"Verify that when collection gets failed then it displays in Red colour coded on \"Manage Collections\" screen.");

		HashMap<String, String> colllectionData = new HashMap<>();
		String collectionId = "";

		// Login and Pre-requesties
		login.loginToSightLine(username, password);
		// selecting project
		base.selectproject(Input.ingestDataProject);
		// Pre-requesties - Access verification
		base.stepInfo("Collection Access Verification");
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);

		// Collection Draft creation
		colllectionData = collection.verifyUserAbleToSaveCollectionAsDraft(username, password, userRole, "SA",
				Input.sa1userName, Input.sa1password, selectedFolder, "", false);
		collectionId = base.returnKey(colllectionData, "", false);
		collectionName = colllectionData.get(collectionId);

		// Start The collection which is in Draft
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		driver.waitForPageToBeReady();
		collection.getCollectionsPageAction(collectionId).waitAndClick(5);
		collection.getCollectionsPageActionList(collectionId, "Start Collection").waitAndClick(5);
		driver.waitForPageToBeReady();

		// Verify Collection presence
		collection.verifyExpectedCollectionStatus(false, headerListDataSets, collectionName, statusListToVerify, 20,
				true, false, "", "");

		// verify Failed status is displayed
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionName, statusList, 25);

		// verify colour of failed status is red
		int index = base.getIndex(collection.getCollectionListHeaderFields(), Input.collectionStatusHeader);
		String Colour = collection.getCollectionStatsDiv(collectionName, index).GetCssValue("color");
		String actualColour = base.rgbTohexaConvertor(Colour);
		base.textCompareEquals(actualColour, Input.colorCodeOfRed, "Failed status is displayed in red colour",
				"Failed status colour is not as expected");

		// Logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that deleted source location should not be displayed
	 *              when adding new collection [RPMXCON-61205]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-61205", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyDeletedSourceLocation(String username, String password, String fullname) throws Exception {
		String deletedSourceLoc = "AAutomation" + Utility.dynamicNameAppender();
		String headerName = "Data Source Name";
		List<String> SrcLocInSrcPage = new ArrayList<String>();

		base.stepInfo("Test case Id: RPMXCON-61205 - O365");
		base.stepInfo("Verify that deleted source location should not be displayed when adding new collection");

		String[][] userRolesData = { { username, fullname, fullname } };

		// Login as User
		login.loginToSightLine(username, password);


		// Pre-requesties - Access verification
		base.stepInfo("Collection Access Verification");
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);

		// navigate to Source page
		dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);

		// Add new source location on Source Page
		collection.performAddNewSource(null, deletedSourceLoc, Input.TenantID, Input.ApplicationID,
				Input.ApplicationKey);

		// delete created source location
		driver.waitForPageToBeReady();
		source.deleteSourceLocation(deletedSourceLoc, false);

		// get the list of Existing source location after deleting created source
		// location
		driver.waitForPageToBeReady();
		SrcLocInSrcPage = source.getListFromTable(headerName, true);

		// navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Click create New Collection
		collection.performCreateNewCollection();

		// verify Exisiting source locations in project is displayed on source selection
		// tab
		collection.verifyAddedSourceLocation(null, SrcLocInSrcPage);

		// verify deleted source location is not available
		base.ValidateElement_Absence(collection.getSourceLocation(deletedSourceLoc),
				deletedSourceLoc + " : deleted source location is not displayed in source location tab");

		// Logout
		login.logout();
	}

	/**
	 * @author
	 * @throws Exception
	 * @Description : Verify 'Cancel Collection' action when Collection is in
	 *              'Copied with Errors' status. RPMXCON-61156
	 */
	@Test(description = "RPMXCON-61156", enabled = true, groups = { "regression" })
	public void verifyIgnoreErrorsActionInRetrievedDSErr() throws Exception {

		HashMap<String, String> collectionData = new HashMap<>();
		HashMap<String, String> colllectionData = new HashMap<>();
		String collectionEmailId = Input.clientCollectionEmail01;
		String collectClientdatlistVal01=Input.clientEmaildatalistVal01;
		String firstName = Input.clientcollectionFirstName01;
		String lastName = Input.clientcollectionSecondName01;
		String collectionEmailId2 = Input.clientCollectionEmail02;
		String collectClientdatlistVal02=Input.clientEmaildatalistVal02;
		String firstName2 = Input.clientcollectionFirstName02;
		String lastName2 = Input.clientcollectionSecondName02;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Inbox";
		String headerListDataSets[] = { "Collection Id", "Collection Status", "Collection Progress", "Error Status" };
		String collectionID = "";
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };
		String[] statusListToVerify = { Input.creatingDSstatus, Input.retreivingDSstatus, Input.virusScanStatus,
				Input.copyDSstatus };
		String[] statusList = { Input.copyDSwithErr };
		String[] statusListAfterIG = { "Cancel in progress", "Draft" };
		HashMap<String, Integer> colllectionDataHeadersIndex = new HashMap<>();
		String rootFolderToSelect = Input.clientcollectionFirstName01 + " " + Input.clientcollectionSecondName01 + " ("
				+ Input.clientCollectionEmail01 + ")";
		String collectionName = "Collection" + Utility.dynamicNameAppender();

		base.stepInfo(
				"As per Client's advice have added 2 testing emails ppatil@consiliotest.com and Gouri.Dhavalikar@consiliotest.com - before triggering make sure expected testdatas are available in their respective folders as mentioned in RPMXCON-61159");
		base.stepInfo("Test case Id: RPMXCON-61156 - O365");
		base.stepInfo("Verify 'Cancel Collection' action when Collection is in 'Copied with Errors' status");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		// selecting project
		base.selectproject(Input.ingestDataProject);
		// Login as User and verify Module Access
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, Input.pa1password);

		// Navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// create new Collection with Datasets and Initiate
		collectionData = collection.createNewCollection(collectionData, collectionName, true, null, false);
		collectionName = base.returnKey(collectionData, "", false);
		System.out.println(collectionName);
		collectionID = colllectionData.get(collectionName);

		// Fill in DS user 1
		collection.fillingDatasetSelection("Button",collectClientdatlistVal01, firstName, lastName, collectionEmailId, selectedApp,
				collectionData, collectionName, 3, rootFolderToSelect, true, true, true, Input.randomText, true, true,
				"Save & Add New Dataset", false,"",true);
		
		base.waitForElement(collection.CancelDataSetBtn());

		// Cancel Action
		collection.CancelDataSetBtn().waitAndClick(10);
		collection.cancelConfirmationOfDatasetPopup("Yes");
		driver.waitForPageToBeReady();

		// Fill in DS user 2
		collection.fillingDatasetSelection("Button",collectClientdatlistVal02, firstName2, lastName2, collectionEmailId2, selectedApp,
				collectionData, collectionName, 3, selectedFolder, true, true, true, Input.randomText, true, true,
				"Save & Add New Dataset", false,"",false);

		// Cancel Action
		base.waitForElement(collection.CancelDataSetBtn());

		// Cancel Action
		collection.CancelDataSetBtn().waitAndClick(10);
		collection.cancelConfirmationOfDatasetPopup("Yes");
		driver.waitForPageToBeReady();

		// Initiate collection
		collection.clickOnNextAndStartAnCollection();

		// Verify Page Navigation
		driver.waitForPageToBeReady();
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collection.verifyExpectedCollectionStatus(false, headerListDataSets, collectionName, statusListToVerify, 30,
				true, false, "", "");

		// Completed status check
		base.stepInfo("Verify Collection is in 'Copied with Errors'");
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionName, statusList, 30);
		driver.waitForPageToBeReady();

		// Collection Header details
		colllectionDataHeadersIndex = collection.getDataSetsHeaderIndex(headerListDataSets);

		// Verify Collection progress bar presence
		collection.verifyCollectionPausedStatus(collectionName, colllectionDataHeadersIndex, "", false);

		// Cancel Collection
		base.stepInfo("Select action as 'Cancel' from the Action ");
		collection.collectionAction(collectionName, "Cancel Collection", false, "Yes", false, "");
		collection.confirmationAction("Yes", Input.cancelCollectionNotification, false);
		base.stepInfo("Clicked Cancel Collection and confirmed");

		// Completed status check
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionName, statusListAfterIG, 20);
		driver.waitForPageToBeReady();

		// Delete Collection
		base.stepInfo("Initiating Delete Collection");
		collection.deleteUsingCollectionName(collectionName, true);

		// Logout
		login.logout();
	}

	/**
	 * @author
	 * @throws Exception
	 * @Description : Verify 'Continue Successful Datasets' action when Collection
	 *              is in 'Copied datasets with errors' status. RPMXCON-61159
	 */
	@Test(description = "RPMXCON-61159", enabled = true, groups = { "regression" })
	public void verifyContinueSuccessfullCopiedDSErr() throws Exception {

		HashMap<String, String> collectionData = new HashMap<>();
		HashMap<String, String> colllectionData = new HashMap<>();
		String collectionEmailId = Input.clientCollectionEmail01;
		String collectClientdatlistVal01=Input.clientEmaildatalistVal01;
		String firstName = Input.clientcollectionFirstName01;
		String lastName = Input.clientcollectionSecondName01;
		String collectionEmailId2 = Input.clientCollectionEmail02;
		String collectClientdatlistVal02=Input.clientEmaildatalistVal02;
		String firstName2 = Input.clientcollectionFirstName02;
		String lastName2 = Input.clientcollectionSecondName02;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Inbox";
		String headerListDataSets[] = { "Collection Id", "Collection Status", "Collection Progress", "Error Status" };
		String collectionID = "";
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };
		String[] statusListToVerify = { Input.creatingDSstatus, Input.retreivingDSstatus, Input.virusScanStatus,
				Input.copyDSstatus };
		String[] statusList = { Input.copyDSwithErr };
		String[] statusListAfterIG = { Input.completedWithErr };
		HashMap<String, Integer> colllectionDataHeadersIndex = new HashMap<>();
		String rootFolderToSelect = Input.clientcollectionFirstName01 + " " + Input.clientcollectionSecondName01 + " ("
				+ Input.clientCollectionEmail01 + ")";
		String collectionName = "Collection" + Utility.dynamicNameAppender();

		base.stepInfo(
				"As per Client's advice have added 2 testing emails ppatil@consiliotest.com and Gouri.Dhavalikar@consiliotest.com - before triggering make sure expected testdatas are available in their respective folders as mentioned in RPMXCON-61159");
		base.stepInfo("Test case Id: RPMXCON-61159 - O365");
		base.stepInfo(
				"Verify 'Continue Successful Datasets' action when Collection is in 'Copied datasets with errors' status");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		// selecting project
		base.selectproject(Input.ingestDataProject);
		// Login as User and verify Module Access
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, Input.pa1password);

		// Navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// create new Collection with Datasets and Initiate
		collectionData = collection.createNewCollection(collectionData, collectionName, false, null, false);
		collectionName = base.returnKey(collectionData, "", false);
		System.out.println(collectionName);
		collectionID = colllectionData.get(collectionName);

		// Fill in DS user 1
		collection.fillingDatasetSelection("Button",collectClientdatlistVal01, firstName, lastName, collectionEmailId, selectedApp,
				collectionData, collectionName, 3, rootFolderToSelect, true, true, true, Input.randomText, true, true,
				"Save & Add New Dataset", false,"",true);

		// Cancel Action
		collection.CancelDataSetBtn().waitAndClick(10);
		collection.getCancelDataSetBtn("Yes").waitAndClick(10);
		driver.waitForPageToBeReady();

		// Fill in DS user 2
		collection.fillingDatasetSelection("Button",collectClientdatlistVal02, firstName2, lastName2, collectionEmailId2, selectedApp,
				collectionData, collectionName, 3, selectedFolder, true, true, true, Input.randomText, true, true,
				"Save & Add New Dataset", false,"",false);

		// Cancel Action
		collection.CancelDataSetBtn().waitAndClick(10);
		collection.getCancelDataSetBtn("Yes").waitAndClick(10);
		driver.waitForPageToBeReady();

		// Initiate collection
		collection.clickOnNextAndStartAnCollection();

		// Verify Page Navigation
		driver.waitForPageToBeReady();
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collection.verifyExpectedCollectionStatus(false, headerListDataSets, collectionName, statusListToVerify, 35,
				true, false, "", "");

		// Completed status check
		base.stepInfo("Verify Collection is in 'Copied with Errors'");
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionName, statusList, 35);
		driver.waitForPageToBeReady();

		// Collection Header details
		colllectionDataHeadersIndex = collection.getDataSetsHeaderIndex(headerListDataSets);

		// Verify Collection progress bar presence
		collection.verifyCollectionPausedStatus(collectionName, colllectionDataHeadersIndex, "", false);

		// Continue Successful Datasets - Yes
		base.stepInfo("Click on Yes\r\n" + "Collection should get \"completed with error\"");
		collection.collectionAction(collectionName, "Continue Successful Datasets", true, "Yes", false, "");
		driver.waitForPageToBeReady();

		// Completed status check
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionName, statusListAfterIG, 15);
		driver.waitForPageToBeReady();

		// Logout
		login.logout();
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
		String collectiondataListVal = Input.collectionDatalistval;
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
		collection.fillingDatasetSelection("Button",collectiondataListVal, firstName, lastName, collectionEmailId, selectedApp,
				colllectionData, collectionName, 3, selectedFolder, true, true, true, Input.randomText, true, true,
				"Save", false,"",false);

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
		String collectiondataListVal = Input.collectionDatalistval;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String collection2ndEmailId = Input.collection2ndEmailId;
		String collectiondatalistVal2 = Input.collection2nddatalistVal;
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
		collection.fillingDatasetSelection("Button",collectiondataListVal, firstName, lastName, collectionEmailId, selectedApp,
				colllectionData, collectionName, 3, selectedFolder, true, true, true, Input.randomText, true, true,
				"Save", false,"",false);

		// add Dataset By Applying Filter
		collection.fillingDatasetSelection("Button",collectiondatalistVal2, secondFirstName, secondlastName, collection2ndEmailId, selectedApp,
				colllectionData, collectionName, 3, selectedFolder, true, true, true, Input.randomText, true, true,
				"Save", false,"",false);

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
		String collectiondataListVal = Input.collectionDatalistval;
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
		collection.fillingDatasetSelection("Button",collectiondataListVal, firstName, lastName, collectionEmailId, selectedApp,
				colllectionData, collectionName, 3, selectedFolder, true, true, true, Input.randomText, true, false,
				"Save", false,"",false);
		base.waitForElement(collection.CancelDataSetBtn());
		collection.CancelDataSetBtn().waitAndClick(5);
		collection.cancelConfirmationOfDatasetPopup("Yes");

		// add Dataset By Applying Filter & click verify new row added
		collection.fillingDatasetSelection("Button",collectiondataListVal, firstName, lastName, collectionEmailId, selectedApp,
				colllectionData, collectionName, 3, selectedFolder, true, true, true, Input.randomText, true, true,
				"Save", false,"",false);

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
		String collectiondataListVal = Input.collectionDatalistval;
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
		colllectionData = collection.dataSetsCreationBasedOntheGridAvailability(firstName,collectiondataListVal, lastName, collectionEmailId,
				selectedApp, colllectionData, selectedFolder, headerList, expectedCollectionStatus, "Button", 3, true,
				"",false,false,false,"",false,"",false);

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
		String collectiondataListVal = Input.collectionDatalistval;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String collection2ndEmailId = Input.collection2ndEmailId;
		String collectiondatalistVal2 = Input.collection2nddatalistVal;
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
		custodianDetails = collection.fillingDatasetSelection("Button",collectiondataListVal, firstName, lastName, collectionEmailId,
				selectedApp, collectionData, collectionName, 3, selectedFolder, true, true, true, Input.randomText,
				true, true, "Save", false,"",false);
		List<String>custodianDetailsNew=new ArrayList<String>();
		custodianDetailsNew.add(collectionEmailId);
		custodianDetailsNew.add(custodianDetails.get(1));
		System.out.println(custodianDetailsNew);

		// click Edit and verify The selected Details Are Retained
		collection.verifyAddedDataSetFrmPopup(collectionEmailId, collectionName, custodianDetailsNew, selectedFolder, true,
				"Enabled", true);

		// Edit folder name and verify Dataset Selection Table
		collection.editDatasetAndVerify(false, null, false, null, null, true, false, selectedFolder, "Archive", null,
				false,false);
		collection.SaveActionInDataSetPopup(true, firstName, lastName, selectedApp, collectionEmailId,
				custodianDetails.get(1), "Archive", Input.randomText, true, "Dataset updated successfully.");

		// Edit Custodians Name and verify folders And application is Reset
		driver.waitForPageToBeReady();
		collection.editDatasetAndVerify(true, collectionEmailId, true, secondFirstName, collection2ndEmailId, true,
				true, "Archive", selectedFolder, "Disabled", true,false);

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
		String collectiondataListVal = Input.collectionDatalistval;
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

		collection.addNewDataSetCLick("Button");
		// Add DataSets
		String dataSetNameGenerated = collection.selectCustodianInAddNewDataSetPopUp(firstName,collectiondataListVal,collectionEmailId,selectedApp);
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
		String collectiondataListVal = Input.collectionDatalistval;
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
		collection.verifyErrorMessageInCutodianSelection("Button",collectiondataListVal, firstName, lastName, collectionEmailId, selectedApp,
				collectionInfoPage, dataSourceName, 5,false,false,false,"",false,false,"","");

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
		String collectiondataListVal = Input.collectionDatalistval;
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
		String dataSetNameGenerated = collection.selectCustodianInAddNewDataSetPopUp(firstName,collectiondataListVal,collectionEmailId,
				selectedApp);
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
		String collectiondataListVal = Input.collectionDatalistval;
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
		colllectionData = collection.dataSetsCreationBasedOntheGridAvailability(firstName,collectiondataListVal, lastName, collectionEmailId,
				selectedApp, colllectionData, selectedFolder, headerList, expectedCollectionStatus, "Button", 3, true,
				"",false,false,false,"",false,"",false);

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
		String collectiondataListVal = Input.collectionDatalistval;
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
		collection.fillingDatasetSelection("Button",collectiondataListVal, firstName, lastName, collectionEmailId, selectedApp,
				collectionData, collectionName, 3, selectedFolder, true, true, true, Input.randomText, true, true,
				"Save", false,"",false);

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
		String collectiondataListVal = Input.collectionDatalistval;
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
		colllectionData = collection.dataSetsCreationBasedOntheGridAvailability(firstName,collectiondataListVal, lastName, collectionEmailId,
				selectedApp, colllectionData, selectedFolder, headerList, expectedCollectionStatus, "Button", 3, true,
				"",false,false,false,"",false,"",false);

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
		String collectiondataListVal = Input.collectionDatalistval;
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
		driver.waitForPageToBeReady();
		base.waitTime(3); // To handle abnormal load time

		// verify Collection Absence in Manage collection Screen
		base.printResutInReport(base.ValidateElement_StatusReturn(collection.getCollectionAction(dataName), 3),
				dataName + " deleted Successfully : is not Displayed in Manage Collection Screen",
				dataName + " : is Displayed after deleting", "Fail");

		// navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Add DataSets
		colllectionData = collection.dataSetsCreationBasedOntheGridAvailability(firstName,collectiondataListVal, lastName, collectionEmailId,
				selectedApp, colllectionData, selectedFolder, headerList, null, "Button", 3, false, dataName,false,false,false,"",false,"",false);

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
		driver.waitForPageToBeReady();
		base.waitTime(3);
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
		String collectiondataListVal = Input.collectionDatalistval;
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
		colllectionData = collection.dataSetsCreationBasedOntheGridAvailability(firstName,collectiondataListVal, lastName, collectionEmailId,
				selectedApp, colllectionData, selectedFolder, headerList, expectedCollectionStatus, "Button", 3, false,
				"",false,false,false,"",false,"",false);

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
		String collectiondataListVal = Input.collectionDatalistval;
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
		collection.fillingDatasetSelection("Button",collectiondataListVal, firstName, lastName, collectionEmailId, selectedApp,
				colllectionData, collectionName, 3, selectedFolder, true, true, true, Input.randomText, true, true,
				"Save & Add New Dataset", false,"",false);

		// Cancel Action
		base.waitForElement(collection.CancelDataSetBtn());
		collection.CancelDataSetBtn().waitAndClick(5);
		collection.cancelConfirmationOfDatasetPopup("Yes");
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
		String collectiondataListVal = Input.collectionDatalistval;
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
		colllectionData = collection.dataSetsCreationBasedOntheGridAvailability(firstName,collectiondataListVal, lastName, collectionEmailId,
				selectedApp, colllectionData, selectedFolder, headerList, expectedCollectionStatus, "Button", 3, false,
				"",false,false,false,"",false,"",false);

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
		String collectiondataListVal = Input.collectionDatalistval;
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
		custodianDetails = collection.fillingDatasetSelection("Button",collectiondataListVal, firstName, lastName, collectionEmailId,
				selectedApp, collectionData, collectionName, 3, selectedFolder1, true, true, true, Input.randomText,
				false, false,"",false,"",false);

		// Select multiple Folders And Save.
		collection.editDatasetAndVerify(false, null, false, null, null, true, false, selectedFolder1,
				custodianDetails.get(0), null, false,true);
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
	public void verifyViewDatasetsIsAsExpected(String usernameDetail, String password, String fullname)
			throws Exception {
		HashMap<String, String> collectionData = new HashMap<>();
		List<String> custodianDetails = new ArrayList();

		String collectionEmailId = Input.collectionDataEmailId;
		String collectiondataListVal = Input.collectionDatalistval;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder1 = "Inbox";
		String headerListDataSets[] = { "Collection Id", "Collection Status", "Error Status" };
		String[] statusList = { "Completed" };
		String collectionNewName = "CollectionNew" + Utility.dynamicNameAppender();
		String actualCollectionName;
		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String[][] userRolesData = { { usernameDetail, fullname, "SA" } };

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
		login.loginToSightLine(usernameDetail, password);

		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);

		// get username
		String username = login.getCurrentUserName();

		// get other dataset tile view
		dataSets.navigateToDataSetsPage();
		String otherTileView = dataSets.getTileViewType();

		// Add New Collection Or get Already Present completed Collection Details
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collectionData = collection.createNewCollection(collectionData, collectionName, true, "Completed", true);

		// navigate to Collection page and get the data
		collectionName = base.returnKey(collectionData, "", false);

		// Click View Dataset or Create collection and click View Dataset
		if (collection.getNameBasedOnCollectionName(collectionName, username).isElementAvailable(3)) {
			base.stepInfo(collectionName + " : is Completed and Displayed in Collections Page");
			driver.waitForPageToBeReady();
			collection.clickViewDataset(collectionName);
			driver.waitForPageToBeReady();
			actualCollectionName = collectionName;
		} else {
			collectionData = collection.createNewCollection(collectionData, collectionNewName, true, null, false);
			custodianDetails = collection.fillingDatasetSelection("Button",collectiondataListVal, firstName, lastName, collectionEmailId,
					selectedApp, collectionData, collectionNewName, 3, selectedFolder1, true, true, true,
					Input.randomText, true, true, "Save", false,"",false);

			collection.clickNextBtnOnDatasetTab();
			driver.waitForPageToBeReady();
			collection.getStartBtn().waitAndClick(10);

			// click view dataset btn from collection page & check Completed status
			dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
			collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionNewName, statusList, 10);
			collection.clickViewDataset(collectionNewName);
			driver.waitForPageToBeReady();
			actualCollectionName = collectionNewName;

		}

		// verify is it navigating to datasets page
		driver.waitForPageToBeReady();
		base.verifyUrlLanding(Input.url + "en-us/ICE/Datasets", "Navigated To Dataset Page",
				"Navigation is not as expected");

		// verify completed collection is displayed in datasets page
		dataSets.verifysearchBoxValue(actualCollectionName, otherTileView);

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

		
		driver.Navigate().refresh();
		// click once and check is it sorted in Ascending
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
		String collectiondataListVal = Input.collectionDatalistval;
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
		String dataSetNameGenerated = collection.selectCustodianInAddNewDataSetPopUp(firstName, collectiondataListVal, collectionEmailId, selectedApp);
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
		String collectiondataListVal = Input.collectionDatalistval;
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
		String dataSetNameGenerated = collection.selectCustodianInAddNewDataSetPopUp(firstName, collectiondataListVal, collectionEmailId, selectedApp);
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
		String collectiondataListVal = Input.collectionDatalistval;
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
		colllectionData = collection.dataSetsCreationBasedOntheGridAvailability(firstName,collectiondataListVal, lastName, collectionEmailId,
				selectedApp, colllectionData, selectedFolder, headerList, expectedCollectionStatus, "Button", 3, false,
				"",false,false,false,"",false,"",false);

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
		String collectiondataListVal = Input.collectionDatalistval;
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
		custodianDetails = collection.fillingDatasetSelection("Button",collectiondataListVal, firstName, lastName, collectionEmailId,
				selectedApp, collectionData, collectionName, 3, selectedFolder, true, true, true, Input.randomText,
				true, true, "Save", false,"",false);

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
		custodianDetails = collection.fillingDatasetSelection("Button",collectiondataListVal,firstName, lastName, collectionEmailId,
				selectedApp, collectionData, collectionName, 3, selectedFolder, true, true, true, Input.randomText,
				true, true, "Save", false,"",false);

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

		String[][] userRolesData = { { username, fullname, "SA" } };

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

		String[][] userRolesData = { { username, fullname, "SA" } };
		String dataSourceName = "Automation" + Utility.dynamicNameAppender();
		String collectionEmailId = Input.collectionDataEmailId;
		String collectiondataListVal = Input.collectionDatalistval;
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
		
		collection.addNewDataSetCLick("Button");

		// initiate collection process
		collection.selectInitiateCollectionOrClickNext(true, true, true);
		

		// Add DataSets
		String dataSetNameGenerated = collection.selectCustodianInAddNewDataSetPopUp(firstName, collectiondataListVal, collectionEmailId, selectedApp);
		System.out.println(dataSetNameGenerated);

		// Select Folder
		collection.folderToSelect(selectedFolder, true, true);
		base.waitForElement(collection.getActionBtn("Save"));
		collection.getActionBtn("Save & Done").waitAndClick(5);

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
