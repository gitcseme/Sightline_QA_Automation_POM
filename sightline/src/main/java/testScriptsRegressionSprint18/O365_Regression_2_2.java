package testScriptsRegressionSprint18;

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

public class O365_Regression_2_2 {

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
		colllectionData = collection.dataSetsCreationBasedOntheGridAvailability(firstName, lastName, collectionEmailId,
				selectedApp, colllectionData, selectedFolder, headerList, expectedCollectionStatus, "Button", 3, false,
				"");

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
		colllectionData = collection.dataSetsCreationBasedOntheGridAvailability(firstName, lastName, collectionEmailId,
				selectedApp, colllectionData, selectedFolder, headerList, null, "Button", 3, true, "");

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
		colllectionData = collection.dataSetsCreationBasedOntheGridAvailability(firstName, lastName, collectionEmailId,
				selectedApp, colllectionData, selectedFolder, headerList, null, "Button", 3, true, "");

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
