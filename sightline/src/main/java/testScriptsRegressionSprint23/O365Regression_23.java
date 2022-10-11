package testScriptsRegressionSprint23;

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
