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

public class O365Regression_23 {

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
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that when collection gets paused due to errors then it
	 *              displays in Orange/Yellow colour coded on "Manage Collections"
	 *              screen.. RPMXCON-61209
	 */
	@Test(description = "RPMXCON-61209", enabled = true, groups = { "regression" })
	public void verifyPausedStatusCollectionColorCode() throws Exception {

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
		String headerListDataSets[] = { "Collection Id", "Collection Status", "Collection Progress" };
		String expectedCollectionStatus = "Draft";
		String collectionID = "";
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };
		String collection2ndEmailId = Input.collection2ndEmailId;
		String secondFirstName = Input.collsecondFirstName;
		String secondlastName = Input.collsecondlastName;
		String[] statusListToVerify = { Input.creatingDSstatus, Input.retreivingDSstatus };
		String[] statusList = { "Retrieved datasets with errors" };
		HashMap<String, Integer> colllectionDataHeadersIndex = new HashMap<>();

		base.stepInfo("Test case Id: RPMXCON-61209 - O365");
		base.stepInfo(
				"Verify that when collection gets paused due to errors then it displays in Orange/Yellow colour coded on \"Manage Collections\" screen.");

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
				colllectionData2, selectedFolder2, headerList, "Button", 3, false, "Save", false, "");

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

		// Collection Header details
		colllectionDataHeadersIndex = collection.getDataSetsHeaderIndex(headerListDataSets);

		// Verify Collection progress bar presence
		base.printResutInReport(
				base.ValidateElement_PresenceReturn(collection.getCollectionProgressBar(collectionName,
						colllectionDataHeadersIndex.get(Input.collectionProgressH))),
				"Collection is Paused", "Collection is not yet paused", "Pass");
		base.stepInfo(collection
				.getCollectionPauseStats(collectionName, colllectionDataHeadersIndex.get(Input.collectionProgressH))
				.getText());

		// Error Color code verification
		String statsColor = base.getCSSValue(collection.getCollectionStatsDiv(collectionName,
				colllectionDataHeadersIndex.get(Input.collectionStatusH)), "color");
		String bgColorHexa = base.rgbTohexaConvertorCustomized(statsColor, 4);
		base.textCompareEquals(Input.collectionErrColorCodeOrange, bgColorHexa,
				"When collection gets paused due to some errors then it's displayed in Orange/Yellow colour coded on \"Manage Collections\" screen.\r\n"
						+ "",
				"Error color code failed");

		// Logout
		login.logout();
	}

	/**
	 * @author Raghuram A
	 * @throws Exception
	 * @Date: 09/28/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that when 'Collection' is in 'Draft' status and user
	 *              clicks on the 'CollectionID' from the Collection Grid, it should
	 *              present the 'Collection' details popup with the detail
	 *              information. RPMXCON-61039
	 */
	@Test(description = "RPMXCON-61039", enabled = true, groups = { "regression" })
	public void verifyCollectionInfoPopuoDetails() throws Exception {
		String selectedFolder = "Drafts";
		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String headerListDataSets[] = { "Collection Id", "Collection Status", "Error Status", Input.progressBarHeader,
				"Action" };
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };
		HashMap<String, String> colllectionData = new HashMap<>();
		String expectedCollectionStatus = "Draft";
		String collectionId = "";
		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String headerList[] = { Input.collectionDataHeader1, Input.collectionDataHeader2, Input.collectionDataHeader3,
				Input.collectionDataHeader5, Input.retreivingDSCountH, Input.dateKeywordHeaderC };
		String headerListDS[] = { Input.collectionDataHeader1, Input.collectionDataHeader2, Input.collectionDataHeader3,
				Input.collectionDataHeader5, Input.collectionDataHeader4, Input.collectionDataHeader6 };

		base.stepInfo("Test case Id: RPMXCON-61039 - O365");
		base.stepInfo(
				"Verify that when 'Collection' is in 'Draft' status and user clicks on the 'CollectionID' from the Collection Grid, it should present the 'Collection' details popup with the detail information");

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
		colllectionData = collection.verifyCollectionInfoPage(dataSourceName, collectionName, false);
		collectionName = base.returnKey(colllectionData, "", false);
		collectionId = colllectionData.get(collectionName);

		// Initiate collection process
		String destinationPath = collection.getDestinationPathLocation().getText();
		collection.selectInitiateCollectionOrClickNext(false, true, true);

		// DataSet creation
		collection.fillinDS(collectionName, firstName, lastName, collectionEmailId, selectedApp, colllectionData,
				selectedFolder, headerListDS, "Button", 3, false, "Save", false, "");
		driver.waitForPageToBeReady();
		collection.nextAction("DataSet");
		driver.waitForPageToBeReady();

		// Save As Draft
		collection.collectionSaveAsDraft();
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Verify Collection presence
		collection.verifyExpectedCollectionIsPresentInTheGrid(headerListDataSets, collectionName,
				expectedCollectionStatus, true, false, "");

		// Launch collection details
		collection.clickDownloadReportLink(collectionName, headerListDataSets, "Collection Id", false, "");
		driver.waitForPageToBeReady();
		base.printResutInReport(base.ValidateElement_AbsenceReturn((collection.getCollectionInfoPopUpStatus())),
				"Opened : Collection Info popup", "No Collection Info popup", "Pass");

		// Verify Collection Name in Collection info popup
		base.stepInfo("Verify Expected Collection name is displayed in the collection info popup");
		base.textCompareEquals(collectionName, collection.getCollectionNameFromInfoPopup().getText(),
				"Expected Collection name is displayed in Collection Info popup",
				"Expected Collection name is not displayed in Collection Info popup");

		// Verify Collection Id in Collection info popup
		base.stepInfo("Verify Expected Collection ID is displayed in the collection info popup");
		base.textCompareEquals(collectionId, collection.getCollectionIdFromInfoPopup().getText(),
				"Expected Collection ID is displayed in Collection Info popup",
				"Expected Collection ID is not displayed in Collection Info popup");

		// Verify Destination path in Collection info popup
		base.stepInfo("Verify Expected Destination path is displayed in the collection info popup");
		base.textCompareEquals(destinationPath, collection.getDestinationLocationInfoPopup().getText(),
				"Expected Destination path is displayed in Collection Info popup",
				"Expected Destination path is not displayed in Collection Info popup");

		// Verify Source Location in Collection info popup
		base.stepInfo("Verify Expected Source Location is displayed in the collection info popup");
		base.textCompareEquals("O365" + dataSourceName, collection.getSourceLocationFromInfoPopup().getText(),
				"Expected Source Location is displayed in Collection Info popup",
				"Expected Source Location is not displayed in Collection Info popup");

		// Verify Initiate Process in Collection info popup
		base.stepInfo("Verify Expected Initiate processing status is displayed in the collection info popup");
		base.textCompareEquals("Do not automatically initiate processing",
				collection.getInitiateStatInfoPopup().getText(),
				"Expected Initiate process status is displayed in Collection Info popup",
				"Expected Initiate process status is not displayed in Collection Info popup");

		// Get user data retrived value
		base.stepInfo("Verify Expected Data retrived content is displayed in the collection info popup");
		String actualValue = collection
				.getDataSetDetails(firstName + " " + lastName,
						base.getIndex(collection.getCollectionDataSetDetailsHeader(), Input.collectionDataHeader4))
				.getText();
		collection.verifyRetrivedDataMatches(firstName, lastName, collectionId, selectedApp, actualValue, true, false,
				"");

		// verify DataSet Contents
		base.stepInfo("Verify other contents is displayed in the collection info popup as expected");
		collection.verifyDataSetContentsCustomize(collection.getCollectionDataSetDetailsHeader(), headerList, firstName,
				lastName, selectedApp, collectionEmailId, Input.collectionDataEmailId, selectedFolder, "0", "-", "",
				false, 0);

		// Delete Collection
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		collection.deleteUsingCollectionName(collectionName, true);

		// Logout
		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @Description : Verify that when 'Collection' is in ‘Failed' status and user
	 *              clicks on the 'CollectionID' from the Collection Grid, it should
	 *              present the 'Collection' details popup with the detail
	 *              information [RPMXCON-61009]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-61009", enabled = true, groups = { "regression" })
	public void verifyCollectionInfoForFailed() throws Exception {
		String selectedFolder = "Drafts";
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
		String headerList[] = { Input.collectionDataHeader1, Input.collectionDataHeader2, Input.collectionDataHeader3,
				Input.collectionDataHeader5, Input.retreivingDSCountH, Input.dateKeywordHeaderC };
		String headerListDS[] = { Input.collectionDataHeader1, Input.collectionDataHeader2, Input.collectionDataHeader3,
				Input.collectionDataHeader5, Input.collectionDataHeader4, Input.collectionDataHeader6 };
		String[] statusListToVerify = { Input.creatingDSstatus, Input.retreivingDSstatus };
		String[] statusList = { "failed" };

		base.stepInfo("Test case Id: RPMXCON-61009 - O365");
		base.stepInfo(
				"Verify that when 'Collection' is in ‘Failed' status and user clicks on the 'CollectionID' from the Collection Grid, it should present the 'Collection' details popup with the detail information");

		// Login and Pre-requesties
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		String currentUser = login.getCurrentUserName();

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
		colllectionData = collection.verifyCollectionInfoPage(dataSourceName, collectionName, false);
		collectionName = base.returnKey(colllectionData, "", false);
		collectionId = colllectionData.get(collectionName);

		// Initiate collection process
		String destinationPath = collection.getDestinationPathLocation().getText();
		collection.selectInitiateCollectionOrClickNext(false, true, true);

		// DataSet creation
		collection.fillinDS(collectionName, firstName, lastName, collectionEmailId, selectedApp, colllectionData,
				selectedFolder, headerListDS, "Button", 3, false, "Save", false, "");
		driver.waitForPageToBeReady();

		// Save As Draft
		collection.clickOnNextAndStartAnCollection();
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Verify Collection presence
		collection.verifyExpectedCollectionStatus(false, headerListDataSets, collectionName, statusListToVerify, 10,
				true, true, "", "");

		// Failed status check
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionName, statusList, 15);

		// Launch collection details
		collection.clickDownloadReportLink(collectionName, headerListDataSets, "Collection Id", false, "");
		driver.waitForPageToBeReady();
		base.printResutInReport(base.ValidateElement_AbsenceReturn((collection.getCollectionInfoPopUpStatus())),
				"Opened : Collection Info popup", "No Collection Info popup", "Pass");

		// Verify Collection Name in Collection info popup
		base.stepInfo("Verify Expected Collection name is displayed in the collection info popup");
		base.textCompareEquals(collectionName, collection.getCollectionNameFromInfoPopup().getText(),
				"Expected Collection name is displayed in Collection Info popup",
				"Expected Collection name is not displayed in Collection Info popup");

		// Verify Collection Id in Collection info popup
		base.stepInfo("Verify Expected Collection ID is displayed in the collection info popup");
		base.textCompareEquals(collectionId, collection.getCollectionIdFromInfoPopup().getText(),
				"Expected Collection ID is displayed in Collection Info popup",
				"Expected Collection ID is not displayed in Collection Info popup");

		// Verify Destination path in Collection info popup
		base.stepInfo("Verify Expected Destination path is displayed in the collection info popup");
		base.textCompareEquals(destinationPath, collection.getDestinationLocationInfoPopup().getText(),
				"Expected Destination path is displayed in Collection Info popup",
				"Expected Destination path is not displayed in Collection Info popup");

		// Verify Source Location in Collection info popup
		base.stepInfo("Verify Expected Source Location is displayed in the collection info popup");
		base.textCompareEquals("O365" + dataSourceName, collection.getSourceLocationFromInfoPopup().getText(),
				"Expected Source Location is displayed in Collection Info popup",
				"Expected Source Location is not displayed in Collection Info popup");

		// Verify Initiate Process in Collection info popup
		base.stepInfo("Verify Expected Initiate processing status is displayed in the collection info popup");
		base.textCompareEquals("Do not automatically initiate processing",
				collection.getInitiateStatInfoPopup().getText(),
				"Expected Initiate process status is displayed in Collection Info popup",
				"Expected Initiate process status is not displayed in Collection Info popup");

		// Verify Ran By in Collection info popup
		base.stepInfo("Verify Expected RAN BY Details is displayed in the collection info popup");
		base.textCompareEquals(currentUser, collection.getPopupRanByDetail().getText(),
				"Expected Ran By Details is displayed in Collection Info popup",
				"Expected Ran By Details is not displayed in Collection Info popup");

		// Get user data retrived value
		base.stepInfo("Verify Expected Data retrived content is displayed in the collection info popup");
		String actualValue = collection
				.getDataSetDetails(firstName + " " + lastName,
						base.getIndex(collection.getCollectionDataSetDetailsHeader(), Input.collectionDataHeader4))
				.getText();
		collection.verifyRetrivedDataMatches(firstName, lastName, collectionId, selectedApp, actualValue, true, false,
				"");

		// verify DataSet Contents
		base.stepInfo("Verify other contents is displayed in the collection info popup as expected");
		collection.verifyDataSetContentsCustomize(collection.getCollectionDataSetDetailsHeader(), headerList, firstName,
				lastName, selectedApp, collectionEmailId, Input.collectionDataEmailId, selectedFolder, "0", "-", "",
				false, 0);

		// Logout
		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @Description : Verify that when 'Collection' is in ‘Completed' status and
	 *              user clicks on the 'CollectionID' from the Collection Grid, it
	 *              should present the 'Collection' details popup with the detail
	 *              information [RPMXCON-61008]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-61008", enabled = true, groups = { "regression" })
	public void verifyCollectionInfoForCompleted() throws Exception {
		String selectedFolder = "Inbox";
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
		String headerList[] = { Input.collectionDataHeader1, Input.collectionDataHeader2, Input.collectionDataHeader3,
				Input.collectionDataHeader5, Input.retreivingDSCountH, Input.dateKeywordHeaderC };
		String headerListDS[] = { Input.collectionDataHeader1, Input.collectionDataHeader2, Input.collectionDataHeader3,
				Input.collectionDataHeader5, Input.collectionDataHeader4, Input.collectionDataHeader6 };
		String[] statusListToVerify = { Input.creatingDSstatus, Input.retreivingDSstatus, Input.virusScanStatus };
		String[] statusList = { "Completed" };

		base.stepInfo("Test case Id: RPMXCON-61008 - O365");
		base.stepInfo(
				"Verify that when 'Collection' is in ‘Completed' status and user clicks on the 'CollectionID' from the Collection Grid, it should present the 'Collection' details popup with the detail information");

		// Login and Pre-requesties
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		String currentUser = login.getCurrentUserName();

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
		colllectionData = collection.verifyCollectionInfoPage(dataSourceName, collectionName, false);
		collectionName = base.returnKey(colllectionData, "", false);
		collectionId = colllectionData.get(collectionName);

		// Initiate collection process
		String destinationPath = collection.getDestinationPathLocation().getText();
		collection.selectInitiateCollectionOrClickNext(false, true, true);

		// DataSet creation
		collection.fillinDS(collectionName, firstName, lastName, collectionEmailId, selectedApp, colllectionData,
				selectedFolder, headerListDS, "Button", 3, false, "Save", false, "");
		driver.waitForPageToBeReady();

		// Save As Draft
		collection.clickOnNextAndStartAnCollection();
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Verify Collection presence
		collection.verifyExpectedCollectionStatus(false, headerListDataSets, collectionName, statusListToVerify, 10,
				true, true, "", "");

		// Failed status check
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionName, statusList, 15);

		// get Actual Total Retrieval count
		int index = base.getIndex(collection.getDataSetDetailsHeader(), Input.totalRetrievedCount);
		String totalRetriveCount = collection.getDataSetDetails(collectionName, index).getText();

		// Launch collection details
		collection.clickDownloadReportLink(collectionName, headerListDataSets, "Collection Id", false, "");
		driver.waitForPageToBeReady();
		base.printResutInReport(base.ValidateElement_AbsenceReturn((collection.getCollectionInfoPopUpStatus())),
				"Opened : Collection Info popup", "No Collection Info popup", "Pass");

		// Verify Collection Name in Collection info popup
		base.stepInfo("Verify Expected Collection name is displayed in the collection info popup");
		base.textCompareEquals(collectionName, collection.getCollectionNameFromInfoPopup().getText(),
				"Expected Collection name is displayed in Collection Info popup",
				"Expected Collection name is not displayed in Collection Info popup");

		// Verify Collection Id in Collection info popup
		base.stepInfo("Verify Expected Collection ID is displayed in the collection info popup");
		base.textCompareEquals(collectionId, collection.getCollectionIdFromInfoPopup().getText(),
				"Expected Collection ID is displayed in Collection Info popup",
				"Expected Collection ID is not displayed in Collection Info popup");

		// Verify Destination path in Collection info popup
		base.stepInfo("Verify Expected Destination path is displayed in the collection info popup");
		base.textCompareEquals(destinationPath, collection.getDestinationLocationInfoPopup().getText(),
				"Expected Destination path is displayed in Collection Info popup",
				"Expected Destination path is not displayed in Collection Info popup");

		// Verify Source Location in Collection info popup
		base.stepInfo("Verify Expected Source Location is displayed in the collection info popup");
		base.textCompareEquals("O365" + dataSourceName, collection.getSourceLocationFromInfoPopup().getText(),
				"Expected Source Location is displayed in Collection Info popup",
				"Expected Source Location is not displayed in Collection Info popup");

		// Verify Initiate Process in Collection info popup
		base.stepInfo("Verify Expected Initiate processing status is displayed in the collection info popup");
		base.textCompareEquals("Do not automatically initiate processing",
				collection.getInitiateStatInfoPopup().getText(),
				"Expected Initiate process status is displayed in Collection Info popup",
				"Expected Initiate process status is not displayed in Collection Info popup");

		// Verify Ran By in Collection info popup
		base.stepInfo("Verify Expected RAN BY Details is displayed in the collection info popup");
		base.textCompareEquals(currentUser, collection.getPopupRanByDetail().getText(),
				"Expected Ran By Details is displayed in Collection Info popup",
				"Expected Ran By Details is not displayed in Collection Info popup");

		// Get user data retrived value
		base.stepInfo("Verify Expected Data retrived content is displayed in the collection info popup");
		String actualValue = collection
				.getDataSetDetails(firstName + " " + lastName,
						base.getIndex(collection.getCollectionDataSetDetailsHeader(), Input.collectionDataHeader4))
				.getText();
		collection.verifyRetrivedDataMatches(firstName, lastName, collectionId, selectedApp, actualValue, true, false,
				"");

		// verify DataSet Contents
		base.stepInfo("Verify other contents is displayed in the collection info popup as expected");
		collection.verifyDataSetContentsCustomize(collection.getCollectionDataSetDetailsHeader(), headerList, firstName,
				lastName, selectedApp, collectionEmailId, Input.collectionDataEmailId, selectedFolder,
				totalRetriveCount, "-", "", false, 0);

		// Logout
		login.logout();
	}

	/**
	 * @author Raghuram A
	 * @throws Exception
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify 'Ignore errors and continue' action when Collection is
	 *              in 'Retrieved datasets with errors ' status . RPMXCON-61596
	 */
	@Test(description = "RPMXCON-61596", enabled = true, groups = { "regression" })
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
		String[] statusListToVerifyAfterIG = { Input.virusScanStatus, Input.copyDSstatus };
		String[] statusList = { Input.reteriveDSErr };
		String[] statusListAfterIG = { Input.completedWithErr };
		HashMap<String, Integer> colllectionDataHeadersIndex = new HashMap<>();

		base.stepInfo("Test case Id: RPMXCON-61596 - O365");
		base.stepInfo(
				"Verify 'Ignore errors and continue' action when Collection is in 'Retrieved datasets with errors ' status ");

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
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionName, statusList, 15);
		driver.waitForPageToBeReady();

		// Collection Header details
		colllectionDataHeadersIndex = collection.getDataSetsHeaderIndex(headerListDataSets);

		// Verify Collection progress bar presence
		collection.verifyCollectionPausedStatus(collectionName, colllectionDataHeadersIndex, "", false);

		// Ignore Errors and Continue - No
		base.stepInfo("Click on No");
		collection.collectionAction(collectionName, "Ignore Errors and Continue", true, "No", true,
				"The application will continue the workflow from where it is paused, with all those collection datasets that are completely successful as well as those datasets that are completed with errors, after ignoring those files that have errors.");
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionName, statusList, 15);
		collection.verifyCollectionPausedStatus(collectionName, colllectionDataHeadersIndex, "", false);

		// Ignore Errors and Continue - Yes
		base.stepInfo("Click on Yes\r\n" + "Collection should get \"completed with error\"");
		collection.collectionAction(collectionName, "Ignore Errors and Continue", true, "Yes", false, "");

		// Verify Page Navigation
		driver.waitForPageToBeReady();
		collection.verifyExpectedCollectionStatus(false, headerListDataSets, collectionName, statusListToVerifyAfterIG,
				10, true, false, "", "");

		// Completed status check
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionName, statusListAfterIG, 15);
		driver.waitForPageToBeReady();

		// View Error Report
		collection.clickDownloadReportLink(collectionName, headerListDataSets, "Error Status", false, "");
		base.stepInfo("Clicked on View Error report");
		driver.waitForPageToBeReady();

		// Show Details
		collection.getPopUpStatusShowDetailsLink("Final Status", "Collection completed with errors.").waitAndClick(5);
		driver.waitForPageToBeReady();
		int indexErrHeader = base.getIndex(collection.getErrHeaderTable(), "Status");
		base.printResutInReport(
				base.ValidateElement_PresenceReturn(
						collection.getPopUpStatus(collectionEmailId, "Failure", indexErrHeader)),
				"It shows the failed collection when user click on \"Show details\" link text",
				"It doesn't shows the failed collection", "Pass");
		base.printResutInReport(
				base.ValidateElement_PresenceReturn(
						collection.getPopUpStatus(collection2ndEmailId, "Success", indexErrHeader)),
				"It shows the succeed collection when user click on \"Show details\" link text",
				"It doesn't shows the passed collection", "Pass");

		// Logout
		login.logout();
	}

	/**
	 * @author 
	 * @throws Exception
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify 'Cancel Collection' action when Collection is in 'Virus
	 *              Scanning Completed' status RPMXCON-61092
	 */
	@Test(description = "RPMXCON-61092", enabled = true, groups = { "regression" })
	public void verifyCancelCollectionInVirusScanCompletedStats() throws Exception {
		HashMap<String, String> collectionData = new HashMap<>();
		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Inbox";
		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String headerListDataSets[] = { "Collection Id", "Collection Status", "Error Status", "Collection Progress",
				"Action" };
		String[] statusListToVerify = { Input.creatingDSstatus, Input.retreivingDSstatus, Input.virusScanStatus,
				Input.copyDSstatus };
		String[] statusList = { "Cancel in progress", "Draft" };
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };
		HashMap<String, Integer> colllectionDataHeadersIndex = new HashMap<>();

		base.stepInfo("Test case Id: RPMXCON-61092 - O365");
		base.stepInfo("Verify 'Cancel Collection' action when Collection is in 'Virus Scanning Completed' status");

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
	 * @author Jeevitha
	 * @throws Exception
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that user can execute (run) the collection process on a
	 *              draft collection RPMXCON-60617
	 */
	@Test(description = "RPMXCON-60617", enabled = true, groups = { "regression" })
	public void verifyEexecuteDraft() throws Exception {
		String selectedFolder = "Inbox";
		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String headerListDataSets[] = { "Collection Id", "Collection Status", "Error Status", Input.progressBarHeader,
				"Action" };
		String[] statusListToVerify = { Input.creatingDSstatus, Input.retreivingDSstatus };
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };

		base.stepInfo("Test case Id: RPMXCON-60617 - O365");
		base.stepInfo("Verify that user can execute (run) the collection process on a draft collection");

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
		colllectionData = collection.verifyUserAbleToSaveCollectionAsDraft(Input.pa1userName, Input.pa1password,
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
		base.passedStep("Collection execution started Successfully");

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