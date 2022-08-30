package testScriptsRegressionSprint20;

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

public class O365Regression_20 {

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

	@DataProvider(name = "PaAndRmuUserDetails")
	public Object[][] PaAndRmuUserDetails() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, "Project Administrator", "SA" },
				{ Input.rmu1userName, Input.rmu1password, "Review Manager", "SA" } };
		return users;
	}

	/**
	 * @Author Jeevitha
	 * @Description :Verify the attributes of the configured collection and
	 *              information on each of the selected custodian datasets
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-60966", enabled = true, groups = { "regression" })
	public void verifyAttributesOfInfoTab() throws Exception {
		HashMap<String, String> collectionData = new HashMap<>();
		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Drafts";
		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String headerList[] = { Input.collectionDataHeader1, Input.collectionDataHeader2, Input.collectionDataHeader3,
				Input.collectionDataHeader4, Input.collectionDataHeader5 };

		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };

		base.stepInfo("Test case Id: RPMXCON-60966 - O365");
		base.stepInfo(
				"Verify the attributes of the configured collection and information on each of the selected custodian datasets");

		// Login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, Input.pa1password);

		// Start Collection
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collectionData = collection.createNewCollection(collectionData, collectionName, true, null, false);

		// Add Dataset
		List<String> custodianDetails = collection.fillingDatasetSelection("Button", firstName, lastName,
				collectionEmailId, selectedApp, collectionData, collectionName, 3, selectedFolder, false, false, false,
				"-", true, true, "Save", "");

		// verify Summary & Start Tab
		collection.clickNextBtnOnDatasetTab();
		collection.verifySummaryAndStartAttributes();
		collection.verifyDataSetContents(headerList, firstName, lastName, selectedApp, collectionEmailId,
				custodianDetails.get(1), selectedFolder, " - ", "", false, 0);

		// Logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description :Verify that when collection gets failed then it displays a
	 *              notification on the top right corner in Notification list.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-61300", dataProvider = "PaAndRmuUserDetails", enabled = true, groups = {
			"regression" })
	public void verifyNotificationRecieved(String userName, String password, String role, String actionRole)
			throws Exception {
		HashMap<String, String> collectionData = new HashMap<>();
		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Drafts";
		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String headerListDataSets[] = { Input.collectionIdHeader, Input.collectionStatusHeader };
		String[] statusListToVerify = { Input.collectionFailedStatus };

		String[][] userRolesData = { { userName, role, actionRole } };

		base.stepInfo("Test case Id: RPMXCON-61300 - O365");
		base.stepInfo(
				"Verify that when collection gets failed then it displays a notification on the top right corner in Notification list.");

		// Login as User
		login.loginToSightLine(userName, password);

		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);

		// Start Collection
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collectionData = collection.createNewCollection(collectionData, collectionName, true, null, false);

		// Add Dataset
		collection.fillingDatasetSelection("Button", firstName, lastName, collectionEmailId, selectedApp,
				collectionData, collectionName, 3, selectedFolder, false, false, false, "-", true, true, "Save", "");

		// Start Collection & Get Notification Count
		int initialBg = base.initialBgCount();
		collection.clickNextBtnOnDatasetTab();
		collection.getStartBtn().waitAndClick(10);

		// verify Failed Status
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collection.verifyExpectedCollectionStatus(false, headerListDataSets, collectionName, statusListToVerify, 10,
				true, false, "", "");

		// verify notification
		collection.verifyNotification(initialBg, 1, collectionName);

		// Logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that "View Datasets" functionality is working fine on
	 *              "Manage Collections" screen.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-61041", dataProvider = "PaAndRmuUserDetails", enabled = true, groups = {
			"regression" })
	public void verifyViewDatasetIsWorking(String userName, String password, String role, String actionRole)
			throws Exception {
		HashMap<String, String> collectionData = new HashMap<>();
		List<String> custodianDetails = new ArrayList();

		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder1 = "Inbox";

		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String[][] userRolesData = { { userName, role, actionRole } };

		base.stepInfo("Test case Id: RPMXCON-61041 - O365");
		base.stepInfo("Verify that \"View Datasets\" functionality is working fine on \"Manage Collections\" screen.");

		// Login as User
		login.loginToSightLine(userName, password);

		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);

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

		// verify completed collection is displayed in datasets page
		dataSets.verifysearchBoxValue(collectionName, otherTileView);

		// Logout
		login.logout();
	}

	/**
	 * @author Raghuram A
	 * @throws Exception
	 * @Date: 08/24/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify final report on click of 'Download Final Report' when
	 *              Collection is in 'Completed' status. RPMXCON-61200
	 */
	@Test(description = "RPMXCON-61200", enabled = true, groups = { "regression" })
	public void verifyFileFormatDwnldAutomaticInitiate() throws Exception {
		HashMap<String, String> collectionData = new HashMap<>();
		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Inbox";
		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String headerListDataSets[] = { "Collection Id", "Collection Status", "Error Status" };
		String[] statusListToVerify = { Input.creatingDSstatus, Input.retreivingDSstatus, Input.virusScanStatus,
				Input.copyDSstatus };
		String[] statusList = { "Completed" };
		String expectedFileFormat = "xlsx";
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };

		base.stepInfo("Test case Id: RPMXCON-61200 - O365");
		base.stepInfo(
				"Verify final report on click of 'Download Final Report' when Collection is in 'Completed' status ");

		// Login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Login as User and verify Module Access
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, Input.pa1password);

		// create new Collection
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

		// Initial Notification count
		int Bgcount = base.initialBgCount();

		// Download Report
		collection.clickDownloadReportLink(collectionName, headerListDataSets, "Error Status", false, "");
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
	 * @Date: 08/24/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify 'Cancel Collection' action when Collection is in
	 *              'Datasets created' status RPMXCON-61094
	 */
	@Test(description = "RPMXCON-61094", enabled = true, groups = { "regression" })
	public void verifyCancelCollectionInDataSetCreationCompletedStats() throws Exception {
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

		base.stepInfo("Test case Id: RPMXCON-61094 - O365");
		base.stepInfo("Verify 'Cancel Collection' action when Collection is in 'Datasets created' status");

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
		collection.confirmationAction("Cancel", "Yes", Input.cancelCollectionNotification);

		// Cancel back to draft status
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionName, statusList, 15);

		// Logout
		login.logout();
	}

	/**
	 * @author Raghuram A
	 * @throws Exception
	 * @Date: 08/26/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that When User starts Collection process then Manage
	 *              collection screen Refresh Interval/ Reload automatically.
	 *              RPMXCON-61279
	 */
	@Test(description = "RPMXCON-61279", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyStausWorksWithoutRefresOrReloadingpageAuto(String userName, String password, String role)
			throws Exception {
		HashMap<String, String> collectionData = new HashMap<>();
		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Inbox";
		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String headerListDataSets[] = { "Collection Id", "Collection Status", "Error Status" };
		String[] statusListToVerify = { Input.creatingDSstatus, Input.retreivingDSstatus, Input.virusScanStatus,
				Input.copyDSstatus };
		String[] statusList = { "Completed" };
		String[][] userRolesData = { { userName, role, "SA" } };

		base.stepInfo("Test case Id: RPMXCON-61279 - O365");
		base.stepInfo(
				" Verify that When User starts Collection process then Manage collection screen Refresh Interval/ Reload automatically. ");

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
		driver.waitForPageToBeReady();

		// Verify Collection presence with expected Status
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		base.stepInfo(
				"When User starts Collection process then Manage collection screen should Refresh Interval/ Reload automatically. \r\n"
						+ "\r\n"
						+ "2.It should be refreshed to present the updated progress and status of collections.");
		collection.verifyExpectedCollectionStatus(false, headerListDataSets, collectionName, statusListToVerify, 10,
				true, false, "", "");

		// Completed status check
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionName, statusList, 10);
		driver.waitForPageToBeReady();
		base.stepInfo(
				"It's automatically refreshed to present the updated progress and status columns of collections as Expected.");

		// Logout
		login.logout();
	}
	
	/**
	 * @Author Mohan
	 * @Description : Verify that all configured Collections and associated properties are available on "Manage Collections" screen (Grid).
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-61012", dataProvider = "PaAndRmuUserDetails", enabled = true, groups = { "regression" })
	public void verifyManageCollectionScreenGridContainsTheHeaderListAndOtherDetails(String userName, String password, String role, String actionRole)
			throws Exception {

		base.stepInfo("Test case Id: RPMXCON-61012 - O365");
		base.stepInfo("Verify that all configured Collections and associated properties are available on \"Manage Collections\" screen (Grid).");

		
		String[][] userRolesData = { { userName, role, actionRole } };

		// Login as User
				login.loginToSightLine(userName, password);

				// Login as User and verify Module Access
				userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);

		// navigate to Collection page
		base.stepInfo("**Step-3 Click on left menu Datasets > Collections**");
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		
		// Verify Collection presence
		collection.getCollectionPageHeaderList();
		//logout
		login.logout();
		
	}
	
	
	/**
	 * @Author Mohan
	 * @Description : Verify that column “Retrieved Count” displays in Final status "Error section pop up" screen.  
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-61659", dataProvider = "PaAndRmuUserDetails", enabled = true, groups = { "regression" })
	public void verifyErroredDatasetsInCollectionWizard(String userName, String password, String role, String actionRole)
			throws Exception {

		base.stepInfo("Test case Id: RPMXCON-61659 - O365");
		base.stepInfo(
				"Verify that column “Retrieved Count” displays in Final status \"Error section pop up\" screen. ");
		
		String[][] userRolesData = { { userName, role, actionRole } };
		String dataSourceName = "Automation" + Utility.dynamicNameAppender();
		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Archive";

		// Login as User
		base.stepInfo("**Step-2 Login as Project Admin/RMU**");
		login.loginToSightLine(userName, password);
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
