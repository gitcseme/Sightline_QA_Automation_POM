package testScriptsRegressionSprint18;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

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
		Object[][] users = { { Input.pa1userName, Input.pa1password, "Project Administrator" },
				{ Input.rmu1userName, Input.rmu1password, "Review Manager" } };
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
