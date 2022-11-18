package testScriptsRegressionSprint26;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
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

public class O365_Regression26 {

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
