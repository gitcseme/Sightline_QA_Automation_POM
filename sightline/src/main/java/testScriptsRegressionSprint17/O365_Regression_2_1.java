package testScriptsRegressionSprint17;

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
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class O365_Regression_2_1 {

	Driver driver;
	LoginPage login;
	BaseClass base;
	UserManagement userManagement;
	DataSets dataSets;
	CollectionPage collection;

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
	}

	@DataProvider(name = "PaAndRmuUser")
	public Object[][] PaAndRmuUser() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, "PA" },
				{ Input.rmu1userName, Input.rmu1password, "RMU" } };
		return users;
	}

	/**
	 * @author Raghuram A
	 * @throws Exception
	 * @Date: 07/11/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that for PAU\RMU - Collections option is available
	 *              Under “Datasets” access control on “Edit User >> Functionality”
	 *              TAB. RPMXCON-60512
	 */
	@Test(description = "RPMXCON-60512", enabled = true, groups = { "regression" })
	public void verifyPAandRMUAccessForCollections() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-60512 - O365");
		base.stepInfo(
				"Verify that for PAU\\RMU - Collections option is available Under “Datasets” access control on “Edit User >> Functionality” TAB.  ");

		String[][] userRolesData = { { Input.pa1userName, "PA" }, { Input.rmu1userName, "RMU" } };

		// Login as SA
		base.stepInfo("**Step-1 & 2 Login to Application with SysAdmin**");
		login.loginToSightLine(Input.sa1userName, Input.sa1password);

		base.stepInfo("**Step-3 Select Same User mentioned in Pre-requisite and Click on \"Edit\" Action**");
		base.stepInfo("**Step-4 Click on \"Functionality\" TAB**");
		base.stepInfo(
				"**Step-5 Verify that for PAU\\RMU - Collections option is available Under “Datasets” access control on “Edit User >> Functionality” TAB.  **");
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");

		// Logout
		login.logout();

	}

	/**
	 * @author Raghuram A
	 * @throws Exception
	 * @Date: 07/11/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that for Reviewer - Collections option is NOT available
	 *              Under “Datasets” access control on “Edit User >> Functionality”
	 *              TAB. RPMXCON-60514
	 */
	@Test(description = "RPMXCON-60514", enabled = true, groups = { "regression" })
	public void verifyREVAccessForCollections() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-60514 - )365");
		base.stepInfo(
				"Verify that for Reviewer - Collections option is NOT available Under “Datasets” access control on “Edit User >> Functionality” TAB.  ");

		String[][] userRolesData = { { Input.rev1userName, "REV" } };

		// Login as SA
		base.stepInfo("**Step-1 & 2 Login to Application with SysAdmin**");
		login.loginToSightLine(Input.sa1userName, Input.sa1password);

		base.stepInfo("**Step-3 Select Same User mentioned in Pre-requisite and Click on \"Edit\" Action**");
		base.stepInfo("**Step-4 Click on \"Functionality\" TAB**");
		base.stepInfo(
				"**Step-5 Verify that for PAU\\RMU - Collections option is available Under “Datasets” access control on “Edit User >> Functionality” TAB.  **");
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");

		// Logout
		login.logout();

	}

	/**
	 * @author Raghuram A
	 * @throws Exception
	 * @Date: 07/12/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that User can Navigate to 'Collections' and 'Source
	 *              Locations' screens when User gave Collection’s access control
	 *              permission from Edit User screen. RPMXCON-60515
	 */
	@Test(description = "RPMXCON-60515", enabled = true, groups = { "regression" })
	public void verifyPAandRMUnavigationToCollections() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-60515 - O365");
		base.stepInfo(
				"Verify that User can Navigate to 'Collections' and 'Source Locations' screens when User gave Collection’s access control permission from Edit User screen");

		String[][] userRolesData = { { Input.pa1userName, "PA" }, { Input.rmu1userName, "RMU" } };

		// Login as SA
		base.stepInfo(
				"**Step-1 Pre-requisite : At least one PA User should have Collection’s access control permission.\r\n"
						+ "If NOT - follow click path mentioned in RPMXCON-60512");
		base.stepInfo("**Step-2 Login to Application with PA**");
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");

		// Logout
		login.logout();

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("**Step-3 Navigate Dataset >> Collections**");
		dataSets.navigateToDataSets("Collections", "Collection/Collections");

		base.stepInfo("**Step-4 Navigate Dataset >> Source Locations**");
		dataSets.navigateToDataSets("Source", "Collection/SourceLocation");

		// Logout
		login.logout();

		// Login as RMU
		base.stepInfo("**Step-5 Repeat Same Steps for RMU User**");
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		base.stepInfo("**Step-3 Navigate Dataset >> Collections**");
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		base.stepInfo("**Step-4 Navigate Dataset >> Source Locations**");
		dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);

		// Logout
		login.logout();

	}

	/**
	 * @author Raghuram A
	 * @throws Exception
	 * @Date: 07/12/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :Verify that 'Collections' and 'Source Locations' options does
	 *              not appear when User does not give Collection’s access control
	 *              permission from Edit User Screen. RPMXCON-60516
	 */
	@Test(description = "RPMXCON-60516", enabled = true, groups = { "regression" })
	public void verifyPAandRMUAcessWhileUnchecked() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-60516 - O365");
		base.stepInfo(
				"Verify that 'Collections' and 'Source Locations' options does not appear when User does not give Collection’s access control permission from Edit User Screen");

		String[][] userRolesData = { { Input.pa1userName, "PA" }, { Input.rmu1userName, "RMU" } };

		// Login as SA
		base.stepInfo(
				"**Step-1 Pre-requisite : At least one PA User should have Collection’s access control permission.\r\n"
						+ "If NOT - follow click path mentioned in RPMXCON-60512");
		base.stepInfo("**Step-2 Login to Application with PA**");
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, false, "Yes");

		// Logout
		login.logout();

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo(
				"**Step-3 Click on Dataset Menu options and Verify that 'Collections' and 'Source Locations' options does not appear **");
		dataSets.CollectionAndSourcecomponentAbsenceCheck();

		// Logout
		login.logout();

		// Login as RMU
		base.stepInfo("**Step-4 Repeat Same Steps for RMU User**");
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo(
				"**Step-3 Click on Dataset Menu options and Verify that 'Collections' and 'Source Locations' options does not appear **");
		dataSets.CollectionAndSourcecomponentAbsenceCheck();

		// Logout
		login.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify the attributes from the 'Add Source Location' pop up
	 *              should be Data Source Type, Data Source Name, Tenant ID,
	 *              Application ID and Application Secret Key .
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-60519", enabled = true, groups = { "regression" })
	public void verifyAttributesInPopUp() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-60519 - O365");
		base.stepInfo(
				"Verify the attributes from the 'Add Source Location' pop up should be Data Source Type, Data Source Name, Tenant ID, Application ID and Application Secret Key");

		String[][] userRolesData = { { Input.pa1userName, "PA" } };

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		userManagement.navigateToUsersPAge();
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");

		// navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Click create New Collection
		collection.performCreateNewCollection();

		// verify Add new source Pop up Attributes
		collection.verifyAddNewSourcePopupAttributes();

		// Logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that error messages should be displayed for the blank
	 *              required fields on click of 'Save'.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-60520", enabled = true, groups = { "regression" })
	public void verifyErrorMsgInPopUp() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-60520 - O365");
		base.stepInfo(
				"Verify that error messages should be displayed for the blank required fields on click of 'Save'");

		String[][] userRolesData = { { Input.pa1userName, "PA" } };

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		userManagement.navigateToUsersPAge();
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");

		// navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Click create New Collection
		collection.performCreateNewCollection();

		// verify Add new source Pop up Attributes
		collection.verifyAddNewSourcePopupAttributes();

		// Verify Error Message In Add new source Popup
		driver.Navigate().refresh();
		collection.performAddNewSource(null, "", "", "", "");

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
