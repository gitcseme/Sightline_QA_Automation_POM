package testScriptsRegressionSprint20;

import java.io.IOException;
import java.lang.reflect.Method;
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
