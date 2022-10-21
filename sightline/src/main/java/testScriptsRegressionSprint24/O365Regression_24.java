package testScriptsRegressionSprint24;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
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

public class O365Regression_24 {

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
	 * @author
	 * @throws Exception
	 * @createdDate : 10/19/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify 'Cancel Collection' when Collection is in 'Retrieval
	 *              Failed' status RPMXCON-61090
	 */
	@Test(description = "RPMXCON-61090", enabled = false, groups = { "regression" })
	public void verifyCancelCollectionInRetrievalFailedStats() throws Exception {
		HashMap<String, String> collectionData = new HashMap<>();
		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Drafts";
		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String headerListDataSets[] = { "Collection Id", "Collection Status", "Error Status", "Collection Progress",
				"Action" };
		String[] statusListToVerify = { Input.creatingDSstatus, Input.retreivingDSstatus };
		String[] statusListBeforeCancel = { "Retrieve of datasets failed" };
		String[] statusList = { "Cancel in progress", "Draft" };
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };
		HashMap<String, Integer> colllectionDataHeadersIndex = new HashMap<>();

		base.stepInfo("Test case Id: RPMXCON-61090 - O365");
		base.stepInfo("Verify 'Cancel Collection' when Collection is in 'Retrieval Failed' status");
		base.stepInfo("Make sure you have Empty data in your email " + selectedFolder + " folder");

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

		// Completed status check
		base.stepInfo("Verify Retrievel datasets Failed error");
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionName, statusListBeforeCancel, 15);
		driver.waitForPageToBeReady();

		// Cancel collection
		collection.clickDownloadReportLink(collectionName, headerListDataSets, "Action", false, "");
		base.stepInfo("Clicked Cancel Collection");
		collection.confirmationAction("Yes", Input.cancelCollectionNotification, false);

		// Cancel back to draft status
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionName, statusList, 15);

		// Progress status check
		colllectionDataHeadersIndex = collection.getDataSetsHeaderIndex(headerListDataSets);
		String collProgressStats = collection
				.getProgressBarStats(collectionName, colllectionDataHeadersIndex.get(Input.progressBarHeader))
				.getText();
		base.textCompareEquals("0.0%", collProgressStats, "Progress Bar is reset to : " + collProgressStats,
				"Progress Bar value remains the same");

		// Delete Collection
		base.stepInfo("Initiating Delete Collection");
		collection.deleteUsingCollectionName(collectionName, true);

		// Logout
		login.logout();
	}

	/**
	 * @author
	 * @throws Exception
	 * @createdDate : 10/19/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify 'Cancel Collection' action when Collection is in
	 *              'Creating Datasets' status RPMXCON-61093
	 */
	@Test(description = "RPMXCON-61093", enabled = false, groups = { "regression" })
	public void verifyCancelCollectionInCreationDSstats() throws Exception {
		HashMap<String, String> collectionData = new HashMap<>();
		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Inbox";
		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String headerListDataSets[] = { "Collection Id", "Collection Status", "Error Status", "Collection Progress",
				"Action" };
		String[] statusListToVerify = { Input.creatingDSstatus };
		String[] statusList = { "Cancel in progress", "Draft" };
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };
		HashMap<String, Integer> colllectionDataHeadersIndex = new HashMap<>();

		base.stepInfo("Test case Id: RPMXCON-61093 - O365");
		base.stepInfo("Verify 'Cancel Collection' action when Collection is in 'Creating Datasets' status");

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
		base.stepInfo("Clicked Cancel Collection");
		collection.confirmationAction("Yes", Input.cancelCollectionNotification, false);

		// Cancel back to draft status
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionName, statusList, 15);

		// Progress status check
		colllectionDataHeadersIndex = collection.getDataSetsHeaderIndex(headerListDataSets);
		String collProgressStats = collection
				.getProgressBarStats(collectionName, colllectionDataHeadersIndex.get(Input.progressBarHeader))
				.getText();
		base.textCompareEquals("0.0%", collProgressStats, "Progress Bar is reset to : " + collProgressStats,
				"Progress Bar value remains the same");

		// Delete Collection
		base.stepInfo("Initiating Delete Collection");
		collection.deleteUsingCollectionName(collectionName, true);

		// Logout
		login.logout();

	}

	/**
	 * @author
	 * @throws Exception
	 * @createdDate : 10/19/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify 'Cancel' action when Collection is in 'Retrieved with
	 *              Errors' status RPMXCON-61076
	 */
	@Test(description = "RPMXCON-61076", enabled = false, groups = { "regression" })
	public void verifyIgnoreErrorsActionInRetrievedDSErr() throws Exception {

		HashMap<String, String> colllectionData = new HashMap<>();
		HashMap<String, String> colllectionData2 = new HashMap<>();
		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Drafts";
		String selectedFolder2 = "Inbox";
		String headerList[] = { Input.collectionDataHeader1, Input.collectionDataHeader2, Input.collectionDataHeader3,
				Input.collectionDataHeader4, Input.collectionDataHeader5, Input.collectionDataHeader6 };
		String headerListDataSets[] = { "Collection Id", "Collection Status", "Collection Progress", "Error Status" };
		String expectedCollectionStatus = "Draft";
		String collectionID = "";
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };
		String collection2ndEmailId = Input.collection2ndEmailId;
		String secondFirstName = Input.collsecondFirstName;
		String secondlastName = Input.collsecondlastName;
		String[] statusListToVerify = { Input.creatingDSstatus, Input.retreivingDSstatus };
		String[] statusList = { Input.reteriveDSErr };
		String[] statusListAfterIG = { "Cancel in progress", "Draft" };
		HashMap<String, Integer> colllectionDataHeadersIndex = new HashMap<>();

		base.stepInfo("Test case Id: RPMXCON-61076 - O365");
		base.stepInfo("Verify 'Cancel' action when Collection is in 'Retrieved with Errors' status");
		base.stepInfo("Make sure you have Empty data in your email " + selectedFolder + " folder");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Login as User and verify Module Access
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, Input.pa1password);

		// navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Add DataSets
		colllectionData = collection.dataSetsCreationBasedOntheGridAvailability(firstName, lastName, collectionEmailId,
				selectedApp, colllectionData, selectedFolder, headerList, "", "Button", 3, false, "");

		// Navigate to Collection page and get the data
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
				colllectionData2, selectedFolder2, headerList, "Button", 3, false, "Save", false, "");

		// Initiate collection
		collection.clickOnNextAndStartAnCollection();

		// Verify Page Navigation
		driver.waitForPageToBeReady();
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collection.verifyExpectedCollectionStatus(false, headerListDataSets, collectionName, statusListToVerify, 10,
				true, false, "", "");

		// Completed status check
		base.stepInfo("Verify Collection is in 'Retrieved with Errors'");
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionName, statusList, 15);
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
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionName, statusListAfterIG, 15);
		driver.waitForPageToBeReady();

		// Delete Collection
		base.stepInfo("Initiating Delete Collection");
		collection.deleteUsingCollectionName(collectionName, true);

		// Logout
		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @Description : Verify that column name "Total Retrieved Count" is available
	 *              on "Manage Collections" screen (Grid). [RPMXCON-61528]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-61528", enabled = true, groups = { "regression" })
	public void verifyRetrievedCountColumn() throws Exception {
		String selectedFolder = Input.RetrievalCountFold;
		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String headerListDataSets[] = { "Collection Id", "Collection Status", "Error Status", Input.progressBarHeader,
				"Action" };
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };
		HashMap<String, String> colllectionData = new HashMap<>();
		String collectionId = "";
		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String headerListDS[] = { Input.collectionDataHeader1, Input.collectionDataHeader2, Input.collectionDataHeader3,
				Input.collectionDataHeader5, Input.collectionDataHeader4, Input.collectionDataHeader6 };
		String[] statusList = { "Completed" };

		base.stepInfo("Test case Id: RPMXCON-61528 - O365");
		base.stepInfo(
				"Verify that column name \"Total Retrieved Count\" is available on \"Manage Collections\" screen (Grid).");

		// Login and Pre-requesties
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Pre-requesties - Access verification
		base.stepInfo("Collection Access Verification");
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, Input.pa1password);

		// Click create New Collection
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collection.performCreateNewCollection();

		// Select source and Click create New Collection
		String dataSourceName = collection.selectSourceFromTheListAvailable();
		base.stepInfo("Selected source location : " + dataSourceName);

		// click created source location and verify navigated page
		colllectionData = collection.verifyCollectionInfoPage(dataSourceName, collectionName, true);
		collectionName = base.returnKey(colllectionData, "", false);
		collectionId = colllectionData.get(collectionName);

		// DataSet creation
		collection.fillinDS(collectionName, firstName, lastName, collectionEmailId, selectedApp, colllectionData,
				selectedFolder, headerListDS, "Button", 3, false, "Save", false, "");
		driver.waitForPageToBeReady();

		// Start collection
		collection.clickOnNextAndStartAnCollection();
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// check completed status
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionName, statusList, 15);

		// verify Total retrieve count column is displayed
		List<String> headerListcolln = base.availableListofElements(collection.getDataSetDetailsHeader());
		base.compareListWithOnlyOneString(headerListcolln, Input.totalRetrievedCount,
				Input.totalRetrievedCount + " : Column is Displayed", "Expected Column is not Dispalyed");

		// verify Retrieved file count .
		int index = base.getIndex(collection.getDataSetDetailsHeader(), Input.totalRetrievedCount);
		String totalRetriveCount = collection.getDataSetDetails(collectionName, index).getText();
		base.textCompareEquals(totalRetriveCount, "2", "Total retreive count is as expected : " + totalRetriveCount,
				"Datasets Count is not as expected");

		// Logout
		login.logout();
	}

	/**
	 * @author
	 * @throws Exception
	 * @Date: 10/21/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that - User can Uncheck Collections access control and
	 *              remove the permissions for 'Collections' on Edit User Screen
	 *              RPMXCON-60711
	 */
	@Test(description = "RPMXCON-60711", enabled = true, groups = { "regression" })
	public void verifyPAandRMUAcessWhileDSUnchecked() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-60711 - O365");
		base.stepInfo(
				"Verify that - User can  Uncheck Collections access control and remove the permissions for 'Collections' on Edit User Screen");

		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" },
				{ Input.rmu1userName, "Review Manager", "SA" } };

		// Login as SA
		base.stepInfo("**Pre-requisite : At least one PA User should exists");
		base.stepInfo("**Login to Application with SA**");
		login.loginToSightLine(Input.sa1userName, Input.sa1password);

		// Now Uncheck \"Collections\" access control on Edit User Screen
		base.stepInfo(
				"** Verify that when “Datasets” access control is NOT checked then 'Collections' option is also Un-checked on Edit User Screen. **");
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, false, false, "Yes");

		// Logout
		login.logout();

	}

	/**
	 * @author
	 * @throws Exception
	 * @createdDate : 10/19/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that user should be able to add name for the collection
	 *              being added RPMXCON-60646
	 */
	@Test(description = "RPMXCON-60646", enabled = true, groups = { "regression" })
	public void verifyCollectionNameAdded() throws Exception {
		HashMap<String, String> collectionData = new HashMap<>();
		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };

		base.stepInfo("Test case Id: RPMXCON-60646 - O365");
		base.stepInfo("Verify that user should be able to add name for the collection being added");

		// Login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Login as User and verify Module Access
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, Input.pa1password);

		// Navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// create new Collection with Datasets and Initiate
		collectionData = collection.createNewCollection(collectionData, collectionName, true, null, false);

		// Logout
		login.logout();
	}

	/**
	 * @author
	 * @throws Exception
	 * @Date: 10/21/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that - User can Uncheck Collections access control and
	 *              remove the permissions for 'Collections' on Edit User Screen
	 *              RPMXCON-60710
	 */
	@Test(description = "RPMXCON-60710", enabled = true, groups = { "regression" })
	public void verifyPAandRMUAcessWhileUnchecked() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-60710 - O365");
		base.stepInfo(
				"Verify that - User can  Uncheck Collections access control and remove the permissions for 'Collections' on Edit User Screen");

		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" },
				{ Input.rmu1userName, "Review Manager", "SA" } };

		// Login as SA
		base.stepInfo("**Pre-requisite : At least one PA User should exists");
		base.stepInfo("**Login to Application with SA**");
		login.loginToSightLine(Input.sa1userName, Input.sa1password);

		// Now Uncheck \"Collections\" access control on Edit User Screen
		base.stepInfo("** Now Uncheck \"Collections\" access control on Edit User Screen **");
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, false, "Yes");

		// Logout
		login.logout();

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("** Via PA Click on Dataset Menu and Verify that \"Collections\" menu is not available **");
		dataSets.CollectionAndSourcecomponentAbsenceCheck();

		// Logout
		login.logout();

		// Login as RMU and repeat the same
		base.stepInfo("**Repeat Same Steps for RMU User**");
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("**Via RMU Click on Dataset Menu and Verify that \"Collections\" menu is not available **");
		dataSets.CollectionAndSourcecomponentAbsenceCheck();

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
