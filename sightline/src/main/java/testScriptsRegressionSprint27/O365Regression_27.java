package testScriptsRegressionSprint27;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

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
