package testScriptsRegressionSprint27;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CollectionPage;
import pageFactory.DataSets;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.SourceLocationPage;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class O365Regression_27 {
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
		softassert = new SoftAssert();
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
				{ Input.pa1userName, Input.pa1password, "Project Administrator" },
				{ Input.rmu1userName, Input.rmu1password, "Review Manager" },

		};
		return users;
	}

	/**
	 * @author Jeevitha
	 * @throws Exception
	 * @Description : Verify that application should not prompt message when
	 *              closing/cancelling the pop up without editing the dataset
	 *              [RPMXCON-61389 ]
	 */
	@Test(description = "RPMXCON-61389", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyApplicationClosesWithoutPrompMsg(String username, String password, String userRole)
			throws Exception {
		String selectedFolder = "Inbox";

		String collectionName = "";
		String[][] userRolesData = { { username, userRole, "SA" } };

		base.stepInfo("Test case Id: RPMXCON-61389 - O365");
		base.stepInfo(
				"Verify that application should not prompt message when closing/cancelling the pop up without editing the dataset");

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

		// navigating directly to collection information page
		collection.verifyCurrentTab("Collection Information");
		collection.getNextBtn().waitAndClick(10);

		// Click edit & verify Popup displayed .Check completed Tag is displayed for
		// Expected sections
		driver.waitForPageToBeReady();
		collection.verifyAddedDataSetFrmPopup(Input.collectionDataEmailId, collectionName, null, selectedFolder, false,
				collectionId, false);

		// Do not edit custodian,folder & click apply filter But do not edit.
		collection.editDatasetAndVerify(false, null, false, null, null, false, false, "", "", "Disabled", true);

		// Click to Close the pop up & verify popup closed without any prompt message
		collection.getDatasetPopupCloseBtn().waitAndClick(10);
		base.stepInfo("Clicked Close icon From Popup");
		base.ValidateElement_Absence(base.getSuccessMsg(), "No message is Displayed");
		base.printResutInReport(collection.getDatasetPopupCloseBtn().isDisplayed(), "Popup is Closed as expected",
				"Popup is not closed", "Fail");

		// Click edit & verify Popup displayed .Check completed Tag is displayed for
		// Expected sections
		driver.waitForPageToBeReady();
		collection.verifyAddedDataSetFrmPopup(Input.collectionDataEmailId, collectionName, null, selectedFolder, false,
				collectionId, false);

		// Do not edit custodian,folder & click apply filter But do not edit.
		collection.editDatasetAndVerify(false, null, false, null, null, false, false, "", "", "Disabled", true);

		// Click Cancel from the pop up & verify popup closed without any prompt message
		collection.SaveActionInDataSetPopup(false, null, null, null, null, null, "", "-", false, "");
		base.stepInfo("Clicked Cancel Button From Popup");
		base.ValidateElement_Absence(base.getSuccessMsg(), "No message is Displayed");
		base.printResutInReport(collection.getDatasetPopupCloseBtn().isDisplayed(), "Popup is Closed as expected",
				"Popup is not closed", "Fail");

		// Logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that application should not prompt message when
	 *              closing/cancelling the pop up without adding the dataset details
	 *              [RPMXCON-61390]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-61390", enabled = true, groups = { "regression" })
	public void verifyPromptMsgCloseWithoutAddingDataset() throws Exception {
		HashMap<String, String> collectionData = new HashMap<>();

		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };

		base.stepInfo("Test case Id: RPMXCON-61390 - O365");
		base.stepInfo(
				"Verify that application should not prompt message when closing/cancelling the pop up without adding the dataset details");

		// Login and Pre-requesties
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Pre-requesties - Access verification
		base.stepInfo("Collection Access Verification");
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, Input.pa1password);

		// Select source location & Add collection Information.
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collectionData = collection.createNewCollection(collectionData, collectionName, true, null, false);

		// Click to add dataset from Dataset Selection tab
		collection.addNewDataSetCLick("Button");
		driver.waitForPageToBeReady();

		// verify Custodian name & Dataset name fields
		base.ValidateElement_Presence(collection.getCustodianIDInputTextField(), "Custodian name field is Displayed");
		base.ValidateElement_Presence(collection.getDataSetNameTextFIeld(), "Dataset name field is Displayed");

		// Do not enter custodian name
		collection.getCustodianIDInputTextField().waitAndClick(5);
		base.waitTime(2);
		collection.getCustodianIDInputTextField().SendKeys("");

		// Click to Close the pop up & verify popup closed without any prompt message
		collection.getDatasetPopupCloseBtn().waitAndClick(10);
		base.stepInfo("Clicked Close icon From Popup");
		base.ValidateElement_Absence(base.getSuccessMsg(), "No message is Displayed");
		base.printResutInReport(collection.getDatasetPopupCloseBtn().isDisplayed(), "Popup is Closed as expected",
				"Popup is not closed", "Fail");

		// Click to add dataset from Dataset Selection tab
		collection.addNewDataSetCLick("Button");
		driver.waitForPageToBeReady();

		// verify Custodian name & Dataset name fields
		base.ValidateElement_Presence(collection.getCustodianIDInputTextField(), "Custodian name field is Displayed");
		base.ValidateElement_Presence(collection.getDataSetNameTextFIeld(), "Dataset name field is Displayed");

		// Do not enter custodian name
		collection.getCustodianIDInputTextField().waitAndClick(5);
		base.waitTime(2);
		collection.getCustodianIDInputTextField().SendKeys("");

		// Click Cancel from the pop up & verify popup closed without any prompt message
		base.waitForElement(collection.getActionBtn("Cancel"));
		collection.getActionBtn("Cancel").waitAndClick(10);
		base.stepInfo("Clicked Cancel Button From Popup");

		base.ValidateElement_Absence(base.getSuccessMsg(), "No message is Displayed");
		base.printResutInReport(collection.getDatasetPopupCloseBtn().isDisplayed(), "Popup is Closed as expected",
				"Popup is not closed", "Fail");

		// Logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that warning message should be displayed on entering
	 *              'Custodian/email' as invalid email address, valid email with
	 *              invalid domain name [RPMXCON-61280]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-61280", enabled = true, groups = { "regression" })
	public void verifyWarningMsgForEmailId() throws Exception {
		HashMap<String, String> collectionData = new HashMap<>();

		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };
		String[][] emailAddresses = { { "AA" + Input.emailAddressinput1, "Invalid Email Address" },
				{ Input.emailAddressinput1, "Valid Email Address With Invalid Domain" },
				{ Input.emailAddressinput2, "non-existing valid email address" } };

		base.stepInfo("Test case Id: RPMXCON-61280 - O365");
		base.stepInfo(
				"Verify that warning message should be displayed on entering 'Custodian/email' as invalid email address, valid email with invalid domain name");

		// Login and Pre-requesties
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Pre-requesties - Access verification
		base.stepInfo("Collection Access Verification");
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, Input.pa1password);

		// Select source location & Add collection Information.
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collection.createNewCollection(collectionData, collectionName, true, null, false);

		// Click to add dataset from Dataset Selection tab
		collection.addNewDataSetCLick("Button");
		driver.waitForPageToBeReady();

		// verify Custodian name & Dataset name fields
		base.ValidateElement_Presence(collection.getCustodianIDInputTextField(), "Custodian name field is Displayed");
		base.ValidateElement_Presence(collection.getDataSetNameTextFIeld(), "Dataset name field is Displayed");

		// Enter Email Addresses & Verify No custodian Inline Error Message
		collection.verifyNoCustodianErrorMsg(true, emailAddresses);

		// Logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that in ‘Custodian Name/Email’ user should be able to
	 *              specify a part of the account name/address and and application
	 *              should display the auto suggestion [RPMXCON-61252]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-61252", enabled = true, groups = { "regression" })
	public void verifyAutoSuggestionIsDisplayedForCustodian() throws Exception {
		HashMap<String, String> collectionData = new HashMap<>();

		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };

		base.stepInfo("Test case Id: RPMXCON-61252 - O365");
		base.stepInfo(
				"Verify that in ‘Custodian Name/Email’ user should be able to specify a part of the account name/address and and application should display the auto suggestion ");

		// Login and Pre-requesties
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Pre-requesties - Access verification
		base.stepInfo("Collection Access Verification");
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, Input.pa1password);

		// Select source location & Add collection Information.
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collection.createNewCollection(collectionData, collectionName, true, null, false);

		// Click to add dataset from Dataset Selection tab
		collection.addNewDataSetCLick("Button");
		driver.waitForPageToBeReady();

		// verify Custodian name & Dataset name fields
		base.ValidateElement_Presence(collection.getCustodianIDInputTextField(), "Custodian name field is Displayed");
		base.ValidateElement_Presence(collection.getDataSetNameTextFIeld(), "Dataset name field is Displayed");

		// Enter 3 characters & verify Auto suggestion is displayed
		String actualValue = collection.custodianNameSelectionInNewDataSet("Gou", Input.clientCollectionEmail02, true,
				false, "");
		base.textCompareNotEquals(actualValue, "",
				"On entering first three characters auto suggestion box is displayed",
				"Auto Suggestion Box is not Displayed");

		// Enter letter part of email address & verify Auto suggestion is displayed
		String actualValue2 = collection.custodianNameSelectionInNewDataSet(Input.clientcollectionFirstName02,
				Input.clientCollectionEmail02, true, false, "");
		base.textCompareNotEquals(actualValue2, "", "On entering letters of email id, auto suggestion box is displayed",
				"Auto Suggestion Box is not Displayed");

		// Logout
		login.logout();
	}

	/**
	 * @author sowndarya Testcase No:RPMXCON-68762
	 * @Description:Verify that error message display and application does NOT
	 *                     accepts - when "Folder Group" Name entered with special
	 *                     characters < > & ‘
	 **/
	@Test(description = "RPMXCON-68762", enabled = true, groups = { "regression" })
	public void verifyErrorMsgFolderAndFoldergroup() throws Exception {
		base.stepInfo("RPMXCON-68762");
		base.stepInfo(
				"Verify that error message display and application does NOT accepts - when \"Folder Group\" Name entered with special characters < > & ‘");
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in As : " + Input.pa1userName);

		String expected = "Special characters are not allowed.";
		TagsAndFoldersPage tags = new TagsAndFoldersPage(driver);
		tags.navigateToTagsAndFolderPage();

		// folder group
		String folderGroupName = "Folder&'Group" + Utility.dynamicRandomNumberAppender();
		tags.navigateToTagsAndFolderPage();
		driver.Navigate().refresh();
		base.waitForElement(tags.getFoldersTab());
		tags.getFoldersTab().waitAndClick(5);
		base.stepInfo("Folder Tab Clicked");
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

		login.logout();

	}

	/**
	 * @author Jeevitha
	 * @throws Exception
	 * @Description : Verify 'View Datasets' action when Collection is in 'Completed
	 *              with Errors' status [RPMXCON-61203]
	 */
	@Test(description = "RPMXCON-61203", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyViewDatasetForCompletedWithErr(String username, String password, String userRole)
			throws Exception {

		HashMap<String, String> collectionData = new HashMap<>();
		HashMap<String, String> colllectionData = new HashMap<>();

		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String collectionEmailId2 = Input.collection2ndEmailId;
		String firstName2 = Input.collsecondFirstName;
		String lastName2 = Input.collsecondlastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder1 = "Inbox";
		String headerListDataSets[] = { Input.collectionIdHeader, Input.collectionStatusHeader, Input.progressBarHeader,
				"Error Status" };
		String collectionID = "";
		String[][] userRolesData = { { username, userRole, "SA" } };
		String[] statusListToVerify = { Input.creatingDSstatus, Input.retreivingDSstatus };
		String[] statusList = { Input.reteriveDSErr };
		String[] statusListAfterIG = { Input.virusScanStatus, Input.copyDSstatus, Input.completedWithErr };
		String selectedFolder2 = "Drafts";
		String collectionName = "Collection" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-61203 - O365");
		base.stepInfo("Verify 'View Datasets' action when Collection is in 'Completed with Erros' status");

		// Login as PA
		login.loginToSightLine(username, password);

		// Login as User and verify Module Access
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);

		// Navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// create new Collection with Datasets and Initiate
		collectionData = collection.createNewCollection(collectionData, collectionName, false, null, false);
		collectionName = base.returnKey(collectionData, "", false);
		System.out.println(collectionName);
		collectionID = colllectionData.get(collectionName);

		// Fill in DS user 1
		collection.fillingDatasetSelection("Button", firstName, lastName, collectionEmailId, selectedApp,
				collectionData, collectionName, 3, selectedFolder1, true, true, true, Input.randomText, true, true,
				"Save", "");

		// Fill in DS user 2
		collection.fillingDatasetSelection("Button", firstName2, lastName2, collectionEmailId2, selectedApp,
				collectionData, collectionName, 3, selectedFolder2, true, true, true, Input.randomText, true, true,
				"Save", "");

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
		collection.getDataSetsHeaderIndex(headerListDataSets);

		// Continue Successful Datasets - Yes
		base.stepInfo("Click on Yes\r\n" + "Collection should get \"completed with error\"");
		collection.collectionAction(collectionName, "Continue Successful Datasets", true, "Yes", false, "");
		driver.waitForPageToBeReady();

		// verify Completed with error status
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionName, statusListAfterIG, 15);
		driver.waitForPageToBeReady();

		// Click On View Dataset
		collection.clickViewDataset(collectionName);
		driver.waitForPageToBeReady();

		// verify is it navigating to datasets page
		driver.waitForPageToBeReady();
		base.verifyUrlLanding(Input.url + "en-us/ICE/Datasets", "Navigated To Dataset Page",
				"Navigation is not as expected");

		// verify collection name displays in the Search/Filter text box.
		dataSets.verifysearchBoxValue(collectionName, "");

		// verify Datasets of the collection present in 'Draft' mode.
		dataSets.VerifyLastStatusOfCollection("Draft", 0, null);

		// Logout
		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @throws Exception
	 * @Description : verify that collection process execution should start from
	 *              collection wizard with automatic initiate processing selection
	 *              for collection and documents should be published with full
	 *              analytics [RPMXCON-61407]
	 */
	@Test(description = "RPMXCON-61407", enabled = true, groups = { "regression" })
	public void verifyCollectionIsPublished() throws Exception {
		HashMap<String, String> collectionData = new HashMap<>();
		HashMap<String, String> colllectionData = new HashMap<>();

		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder1 = "Analytics-1";
		String headerListDataSets[] = { Input.collectionIdHeader, Input.collectionStatusHeader, Input.progressBarHeader,
				"Error Status" };
		String collectionID = "";
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };
		String[] statusListToVerify = { Input.creatingDSstatus, Input.retreivingDSstatus, Input.virusScanStatus,
				Input.copyDSstatus };
		String[] statusList = { "Completed" };
		String collectionName = "Collection" + Utility.dynamicNameAppender();

		base.failedMessage(
				"Selected \"Analytics-1\" folder To publish only once in a project so make sure that current project doesn't have this dataset ingested");
		base.stepInfo("Test case Id: RPMXCON-61407 - O365");
		base.stepInfo(
				"erify that collection process execution should start from collection wizard with automatic initiate processing selection for collection and documents should be published with full analytics");

		// Enable KickOff Analytics
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		ProjectPage project = new ProjectPage(driver);
		project.navigateToProductionPage();
		base.stepInfo("Enable auto analytics");
		project.disableOrEnableKickOffAnalytics(Input.projectName, "Enable", false);
		login.logout();

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

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
		collection.fillingDatasetSelection("Button", firstName, lastName, collectionEmailId, selectedApp,
				collectionData, collectionName, 3, selectedFolder1, true, true, true, Input.randomText, true, true,
				"Save", "");

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

		// Click On View Dataset
		collection.clickViewDataset(collectionName);
		driver.waitForPageToBeReady();

		// verify is it navigating to datasets page
		driver.waitForPageToBeReady();
		base.verifyUrlLanding(Input.url + "en-us/ICE/Datasets", "Navigated To Dataset Page",
				"Navigation is not as expected");

		// verify collection name displays in the Search/Filter text box.
		dataSets.verifysearchBoxValue(collectionName, "");

		// verify Datasets of the collection is published
		dataSets.VerifyLastStatusOfCollection("Published", 25, collectionName);

		// Logout
		login.logout();
	}

	/**
	 * @Author Jeevitha R
	 * @Description :Verify that collections home screen present cleanly when the
	 *              user zooms in and out between 75% to 120% on resolutions
	 *              1366x768 on chrome browser. [RPMXCON-61248]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-61248", enabled = true, groups = { "regression" })
	public void verifyCollectionHomeScreenResol768() throws Exception {
		String selectedFolder = "Inbox";

		String collectionName = "";
		String[][] userRolesData = { { Input.pa1userName, Input.ProjectAdministrator, "SA" } };
		HashMap<String, String> colllectionData = new HashMap<>();
		String collectionId = "";

		base.stepInfo("RPMXCON-61248 O365");
		base.stepInfo(
				"Verify that collections home screen present cleanly when the user zooms in and out between 75% to 120% on resolutions 1366x768 on chrome browser.");

		// login
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		AssignmentsPage assignmentspage = new AssignmentsPage(driver);

		// Pre-requesties - Access verification
		base.stepInfo("Collection Access Verification");
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, Input.pa1password);

		// Navigate to collection Page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// fetch collection & Header before performing zoom in & zoom out
		if (collection.getCollectionListDatasetValueCollectionName().isElementAvailable(10)) {
			collectionName = collection.getCollectionListDatasetValueCollectionName().getText();
		} else {
			colllectionData = collection.verifyUserAbleToSaveCollectionAsDraft(Input.pa1userName, Input.pa1password,
					"Project Administrator", "SA", Input.sa1userName, Input.sa1password, selectedFolder, "", false);
			collectionId = base.returnKey(colllectionData, "", false);
			collectionName = colllectionData.get(collectionId);
		}
		driver.waitForPageToBeReady();
		List<String> headerList = base.availableListofElements(collection.getDataSetDetailsHeader());

		// Change Screen resolution to 1366*768
		driver.waitForPageToBeReady();
		driver.Manage().window().setSize(new Dimension(1366, 768));
		driver.waitForPageToBeReady();

		// Zoom in : increasing to 120%
		assignmentspage.BrowserResolutionMax(1);
		driver.waitForPageToBeReady();

		// Check collection presence
		base.printResutInReport(
				base.ValidateElement_PresenceReturn(collection.getCollectionNameElement(collectionName)),
				collectionName + " : is displayed in the grid", "Expected collection not available in the grid",
				"Pass");

		// verify All the Headers Is displayed within the screen & create new collection
		// btn is displayed within the screen & clickable
		driver.waitForPageToBeReady();
		collection.verifyCollectionPageIsDisplayedWithinTheScreen(headerList);

		// decreasing to 90%
		assignmentspage.BrowserResolutionMin(2);
		base.stepInfo("Zoom out : 90%");
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		driver.waitForPageToBeReady();

		// Check collection presence
		base.printResutInReport(
				base.ValidateElement_PresenceReturn(collection.getCollectionNameElement(collectionName)),
				collectionName + " : is displayed in the grid", "Expected collection not available in the grid",
				"Pass");

		// verify All the Headers Is displayed within the screen & create new collection
		// btn is displayed within the screen & clickable
		driver.waitForPageToBeReady();
		collection.verifyCollectionPageIsDisplayedWithinTheScreen(headerList);

		// decreasing to 75%
		assignmentspage.BrowserResolutionMin(1);
		base.stepInfo("Zoom out : 75%");
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		driver.waitForPageToBeReady();

		// Check collection presence
		base.printResutInReport(
				base.ValidateElement_PresenceReturn(collection.getCollectionNameElement(collectionName)),
				collectionName + " : is displayed in the grid", "Expected collection not available in the grid",
				"Pass");

		// verify All the Headers Is displayed within the screen & create new collection
		// btn is displayed within the screen & clickable
		driver.waitForPageToBeReady();
		collection.verifyCollectionPageIsDisplayedWithinTheScreen(headerList);

		// logout
		login.logout();
	}

	/**
	 * @Author Jeevitha R
	 * @Description : Verify that collections home screen present cleanly when the
	 *              user zooms in and out between 75% to 120% on resolutions
	 *              1920x1080 on chrome browser. [RPMXCON-61247]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-61247", enabled = true, groups = { "regression" })
	public void verifyCollectionHomeScreenResol1080() throws Exception {
		String selectedFolder = "Inbox";

		String collectionName = "";
		String[][] userRolesData = { { Input.pa1userName, Input.ProjectAdministrator, "SA" } };
		HashMap<String, String> colllectionData = new HashMap<>();
		String collectionId = "";

		base.stepInfo("RPMXCON-61247 O365");
		base.stepInfo(
				"Verify that collections home screen present cleanly when the user zooms in and out between 75% to 120% on resolutions 1920x1080 on chrome browser.");

		// login
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		AssignmentsPage assignmentspage = new AssignmentsPage(driver);

		// Pre-requesties - Access verification
		base.stepInfo("Collection Access Verification");
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, Input.pa1password);

		// Navigate to collection Page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// fetch collection & Header before performing zoom in & zoom out
		if (collection.getCollectionListDatasetValueCollectionName().isElementAvailable(10)) {
			collectionName = collection.getCollectionListDatasetValueCollectionName().getText();
		} else {
			colllectionData = collection.verifyUserAbleToSaveCollectionAsDraft(Input.pa1userName, Input.pa1password,
					"Project Administrator", "SA", Input.sa1userName, Input.sa1password, selectedFolder, "", false);
			collectionId = base.returnKey(colllectionData, "", false);
			collectionName = colllectionData.get(collectionId);
		}
		driver.waitForPageToBeReady();
		List<String> headerList = base.availableListofElements(collection.getDataSetDetailsHeader());

		// Change Screen resolution to 1366*768
		driver.waitForPageToBeReady();
		driver.Manage().window().setSize(new Dimension(1920, 1080));
		driver.waitForPageToBeReady();

		// Zoom in : increasing to 120%
		assignmentspage.BrowserResolutionMax(1);
		base.stepInfo("Zoom In : 125%");
		driver.waitForPageToBeReady();

		// Check collection presence
		base.printResutInReport(
				base.ValidateElement_PresenceReturn(collection.getCollectionNameElement(collectionName)),
				collectionName + " : is displayed in the grid", "Expected collection not available in the grid",
				"Pass");

		// verify All the Headers Is displayed within the screen & create new collection
		// btn is displayed within the screen & clickable
		driver.waitForPageToBeReady();
		collection.verifyCollectionPageIsDisplayedWithinTheScreen(headerList);

		// decreasing to 90%
		assignmentspage.BrowserResolutionMin(2);
		base.stepInfo("Zoom out : 90%");
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		driver.waitForPageToBeReady();

		// Check collection presence
		base.printResutInReport(
				base.ValidateElement_PresenceReturn(collection.getCollectionNameElement(collectionName)),
				collectionName + " : is displayed in the grid", "Expected collection not available in the grid",
				"Pass");

		// verify All the Headers Is displayed within the screen & create new collection
		// btn is displayed within the screen & clickable
		driver.waitForPageToBeReady();
		collection.verifyCollectionPageIsDisplayedWithinTheScreen(headerList);

		// decreasing to 75%
		assignmentspage.BrowserResolutionMin(1);
		base.stepInfo("Zoom out : 75%");
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		driver.waitForPageToBeReady();

		// Check collection presence
		base.printResutInReport(
				base.ValidateElement_PresenceReturn(collection.getCollectionNameElement(collectionName)),
				collectionName + " : is displayed in the grid", "Expected collection not available in the grid",
				"Pass");

		// verify All the Headers Is displayed within the screen & create new collection
		// btn is displayed within the screen & clickable
		driver.waitForPageToBeReady();
		collection.verifyCollectionPageIsDisplayedWithinTheScreen(headerList);

		// logout
		login.logout();
	}

	/**
	 * @author Jeevitha R
	 * @throws Exception
	 * @Description : Verify 'Cancel Collection' action when Collection is in
	 *              'Copying to datasets' status [RPMXCON-61120]
	 */
	@Test(description = "RPMXCON-61120", enabled = true, groups = { "regression" })
	public void verifyCancelCollectionInCopyingDataset() throws Exception {
		String selectedFolder = "Inbox";
		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String headerListDataSets[] = { Input.collectionIdHeader, Input.collectionStatusH, "Error Status",
				Input.progressBarHeader, Input.actionColumnName };
		String[] statusListToVerify = { Input.creatingDSstatus, Input.retreivingDSstatus, Input.virusScanStatus,
				Input.copyDSstatus };
		String[] statusListBeforeCancel = { Input.copyDSstatus };
		String[] statusList = { "Cancel in progress", "Draft" };
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };
		HashMap<String, Integer> colllectionDataHeadersIndex = new HashMap<>();
		HashMap<String, String> colllectionData = new HashMap<>();
		String collectionId = "";

		base.stepInfo("Test case Id: RPMXCON-61120 - O365");
		base.stepInfo("Verify 'Cancel Collection' action when Collection is in 'Copying to datasets' status");

		// Login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Login as User and verify Module Access
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, Input.pa1password);

		// Create new collection With filter in Draft State
		colllectionData = collection.verifyUserAbleToSaveCollectionAsDraft(Input.pa1userName, Input.pa1password,
				"Project Administrator", "SA", Input.sa1userName, Input.sa1password, selectedFolder, "", false);
		collectionId = base.returnKey(colllectionData, "", false);
		collectionName = colllectionData.get(collectionId);

		// Start Draft Collection from collection page
		collection.collectionAction(collectionName, "Start Collection", false, "", false, "");

		// Verify Collection All the Status including copying to dataset status & verify
		// progress bar
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collection.verifyExpectedCollectionStatus(false, headerListDataSets, collectionName, statusListToVerify, 30,
				true, true, "", "");

		base.stepInfo("Verify  status  : " + Input.copyDSstatus);
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionName, statusListBeforeCancel, 30);
		driver.waitForPageToBeReady();

		// Cancel collection when statsu is in copy to dataset status
		collection.collectionAction(collectionName, "Cancel Collection", true, "Yes", false, "");
		base.VerifySuccessMessage(Input.cancelCollectionNotification);

		// verify it is rolled back to Draft State
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionName, statusList, 30);

		// Progress status check
		colllectionDataHeadersIndex = collection.getDataSetsHeaderIndex(headerListDataSets);
		String collProgressStats = collection
				.getProgressBarStats(collectionName, colllectionDataHeadersIndex.get(Input.progressBarHeader))
				.getText();
		base.textCompareEquals("0.0%", collProgressStats, "Progress Bar is reset to : " + collProgressStats,
				"Progress Bar value remains the same");

		// Logout
		login.logout();
	}

	/**
	 * @author Jeevitha
	 * @throws Exception
	 * @Description : Verify error report on click of 'View Error Report' when
	 *              Collection is in 'Copied to Datasets' status [RPMXCON-61154]
	 */
	@Test(description = "RPMXCON-61154", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyErrorReportOnClickOfViewErrorLink(String username, String password, String userRole)
			throws Exception {

		HashMap<String, String> collectionData = new HashMap<>();

		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String collectionEmailId2 = Input.collection2ndEmailId;
		String firstName2 = Input.collsecondFirstName;
		String lastName2 = Input.collsecondlastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder1 = "Inbox";
		String headerListDataSets[] = { Input.collectionIdHeader, Input.collectionStatusHeader, Input.progressBarHeader,
				"Error Status" };
		String collectionID = "";
		String[][] userRolesData = { { username, userRole, "SA" } };
		String[] statusListToVerify = { Input.creatingDSstatus, Input.retreivingDSstatus, Input.reteriveDSErr };
		String[] statusListAfterIG = { Input.virusScanStatus, Input.copyDSstatus, Input.completedWithErr };
		String selectedFolder2 = "Drafts";
		String collectionName = "Collection" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-61154 - O365");
		base.stepInfo(
				"Verify error report on click of 'View Error Report' when Collection is in 'Copied to Datasets' status");

		// Login as User
		login.loginToSightLine(username, password);

		// Login as User and verify Module Access
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);

		// Navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// create new Collection with Datasets and Initiate
		collectionData = collection.createNewCollection(collectionData, collectionName, false, null, false);
		collectionID = collectionData.get(collectionName);
		System.out.println(collectionName);
		System.out.println(collectionID);

		// Fill in DS user 1
		collection.fillingDatasetSelection("Button", firstName, lastName, collectionEmailId, selectedApp,
				collectionData, collectionName, 3, selectedFolder1, true, true, true, Input.randomText, true, true,
				"Save", "");

		// Fill in DS user 2
		collection.fillingDatasetSelection("Button", firstName2, lastName2, collectionEmailId2, selectedApp,
				collectionData, collectionName, 3, selectedFolder2, true, true, true, Input.randomText, true, true,
				"Save", "");

		// Initiate collection
		collection.clickNextBtnOnDatasetTab();
		driver.waitForPageToBeReady();
		collection.collectionSaveAsDraft();

		// Start Draft Collection from collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collection.collectionAction(collectionName, "Start Collection", false, "", false, "");

		// verify status displayed
		base.stepInfo("Verify  status  : " + Input.copyDSstatus);
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionName, statusListToVerify, 30);
		driver.waitForPageToBeReady();

		// Continue Successful Datasets - Yes
		base.stepInfo("Click on Yes\r\n" + "Collection should get \"completed with error\"");
		collection.collectionAction(collectionName, "Continue Successful Datasets", true, "Yes", false, "");
		driver.waitForPageToBeReady();

		// verify Copying to datset status completed & click on view errro Report
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionName, statusListAfterIG, 15);
		driver.waitForPageToBeReady();
		
		//
		collection.clickDownloadReportLink(collectionName, headerListDataSets, "Error Status", false, "");
		base.stepInfo("Clicked on View Error report");
		driver.waitForPageToBeReady();

		// verify All the details Displayed in Popup & download report and verify
		String expectedTxt = "Retrieved with Errors: Some Files cannot be Retrieved, Click Download Error Report Link";
		collection.verifyViewErrorReportPopup(true, collectionID, expectedTxt);

		// Logout
		login.logout();
	}

	/**
	 * @author 
	 * @throws Exception
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that when 'Collection' is in ‘Completed With Errors’
	 *              status and user clicks on the 'CollectionID' from the Collection
	 *              Grid, it should present the 'Collection' details popup with the
	 *              detail information. RPMXCON-61010
	 */
	@Test(description = "RPMXCON-61010", enabled = true, groups = { "regression" })
	public void verifyCollectionInfoPopuoDetails() throws Exception {
		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String headerListDataSets[] = { "Collection Id", "Collection Status", "Error Status", Input.progressBarHeader,
				"Action" };
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };
		HashMap<String, String> collectionData = new HashMap<>();
		HashMap<String, Integer> colllectionDataHeadersIndex = new HashMap<>();
		String selectedFolder = "Drafts";
		String selectedFolder2 = "AnalyticsCheck1";
		String collectionId = "";
		String collectionEmailId = Input.collectionDataEmailId;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String headerList[] = { Input.collectionDataHeader1, Input.collectionDataHeader2, Input.collectionDataHeader3,
				Input.collectionDataHeader5, Input.retreivingDSCountH, Input.dateKeywordHeaderC };
		String collection2ndEmailId = Input.collection2ndEmailId;
		String secondFirstName = Input.collsecondFirstName;
		String secondlastName = Input.collsecondlastName;
		String[] statusListToVerify = { Input.creatingDSstatus, Input.retreivingDSstatus };
		String[] statusList = { Input.reteriveDSErr };
		String[] statusListAfterIG = { Input.completedWithErr };

		base.stepInfo("Test case Id: RPMXCON-61010 - O365");
		base.stepInfo(
				"Verify that when 'Collection' is in ‘Completed With Errors’ status and user clicks on the 'CollectionID' from the Collection Grid, it should present the 'Collection' details popup with the detail information");

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
		collectionData = collection.verifyCollectionInfoPage(dataSourceName, collectionName, false);
		collectionId = collectionData.get(collectionName);

		// Initiate collection process
		String destinationPath = collection.getDestinationPathLocation().getText();
		collection.selectInitiateCollectionOrClickNext(false, true, true);

		// Fill in DS user 1
		collection.fillingDatasetSelection("Button", firstName, lastName, collectionEmailId, selectedApp,
				collectionData, collectionName, 3, selectedFolder, true, true, true, Input.randomText, true, true,
				"Save & Add New Dataset", "");

		// Cancel Action
		collection.getActionBtn("Cancel").waitAndClick(10);
		driver.waitForPageToBeReady();

		// Fill in DS user 2
		collection.fillingDatasetSelection("Button", secondFirstName, secondlastName, collection2ndEmailId, selectedApp,
				collectionData, collectionName, 3, selectedFolder2, true, true, true, Input.randomText, true, true,
				"Save & Add New Dataset", "");

		// Cancel Action
		collection.getActionBtn("Cancel").waitAndClick(10);
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
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionName, statusListAfterIG, 20);
		driver.waitForPageToBeReady();

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

		// Verify Ran By is in Collection info popup
		base.stepInfo("Verify Expected Ran By User details is displayed in the collection info popup");
		base.textCompareEquals(currentUser,
				collection.getCollectionDataFromPopupCustomized("Collection Ran By:").getText(),
				"Expected Ran by user detail is displayed in Collection Info popup",
				"Expected Ran by user detail is not displayed in Collection Info popup");

		// Verify Collection Completion Date is in Collection info popup
		base.stepInfo("Verify Collection Completed Date and Time is displayed in the collection info popup");
		base.printResutInReport(
				base.ValidateElement_PresenceReturn(
						collection.getCollectionDataFromPopupCustomized("Collection Completion Date:")),
				"Collection Completed Date and Time is displayed in the collection info popup",
				"Collection Completed Date and Time is not displayed in the collection info popup", "Pass");
		base.stepInfo(collection.getCollectionDataFromPopupCustomized("Collection Completion Date:").getText());

		// Get user data retrived value
		base.stepInfo("Verify Expected Data retrived content is displayed in the collection info popup");
		String actualValue = collection
				.getDataSetDetails(firstName + " " + lastName,
						base.getIndex(collection.getCollectionDataSetDetailsHeader(), Input.collectionDataHeader4))
				.getText();
		collection.verifyRetrivedDataMatches(firstName, lastName, collectionId, selectedApp, actualValue, true, false,
				"");

		// verify DataSet Contents - retrieved count 0 is fixed value
		base.stepInfo("Verify other contents is displayed in the collection info popup as expected");
		collection.verifyDataSetContentsCustomize(collection.getCollectionDataSetDetailsHeader(), headerList, firstName,
				lastName, selectedApp, collectionEmailId, collectionEmailId, selectedFolder, "0", "Test", "", false, 0);

		// verify DataSet Contents - retrieved count 1 is fixed value
		base.stepInfo("Verify other contents is displayed in the collection info popup as expected");
		collection.verifyDataSetContentsCustomize(collection.getCollectionDataSetDetailsHeader(), headerList,
				secondFirstName, secondlastName, selectedApp, collection2ndEmailId, collection2ndEmailId,
				selectedFolder2, "1", "Test", "", false, 0);

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
