package testScriptsRegressionSprint21;

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
public class O365_Regression_21 {

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
//		Input in = new Input();
//		in.loadEnvConfig();

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
	 * @return
	 * @Author Jeevitha
	 * @Description : Verify that user should be able to ‘Save’ the collection as a
	 *              draft
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-60970", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public String verifyUserAbleToSaveCollectionAsDraft(String username, String password, String fullname)
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
		return dataName;
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
