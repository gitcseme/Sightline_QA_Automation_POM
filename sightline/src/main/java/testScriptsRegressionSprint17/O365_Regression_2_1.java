package testScriptsRegressionSprint17;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
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

public class O365_Regression_2_1 {

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
		Object[][] users = { { Input.pa1userName, Input.pa1password, "PA" },
				{ Input.rmu1userName, Input.rmu1password, "RMU" } 
		};
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
	@Test(description = "RPMXCON-60519", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyAttributesInPopUp(String username, String password, String fullname) throws Exception {

		base.stepInfo("Test case Id: RPMXCON-60519 - O365");
		base.stepInfo(
				"Verify the attributes from the 'Add Source Location' pop up should be Data Source Type, Data Source Name, Tenant ID, Application ID and Application Secret Key");

		String[][] userRolesData = { { username, fullname } };

		// Login as User
		login.loginToSightLine(username, password);
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
	@Test(description = "RPMXCON-60520", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyErrorMsgInPopUp(String username, String password, String fullname) throws Exception {

		base.stepInfo("Test case Id: RPMXCON-60520 - O365");
		base.stepInfo(
				"Verify that error messages should be displayed for the blank required fields on click of 'Save'");

		String[][] userRolesData = { { username, fullname } };

		// Login as User
		login.loginToSightLine(username, password);
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

	/**
	 * @Author Jeevitha
	 * @Description : Verify that from Collection Wizard user should be able to add
	 *              new source location on click of 'Create New Collection' > 'Add
	 *              New Source Location' button.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-60518", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifySourceLocationAdded(String username, String password, String fullname) throws Exception {
		String dataname = "Automation" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-60518 - O365");
		base.stepInfo(
				"Verify that from Collection Wizard user should be able to add new source location on click of 'Create New Collection' > 'Add New Source Location' button");

		String[][] userRolesData = { { username, fullname } };

		// Login as User
		login.loginToSightLine(username, password);
		userManagement.navigateToUsersPAge();
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");

		// navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Click create New Collection
		collection.performCreateNewCollection();

		// Verify Error Message In Add new source Popup
		collection.performAddNewSource(null, dataname, Input.TenantID, Input.ApplicationID, Input.ApplicationKey);

		// verify Added source location is displayed
		collection.verifyAddedSourceLocation(dataname, null);

		// delete created source location
		dataSets.navigateToDataSets("Source", "Collection/SourceLocation");
		source.deleteSourceLocation(dataname, false);

		// Logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that once source selection is configured succesfully
	 *              with connection to the source location, the user should be taken
	 *              to the next intended step of the collection wizard
	 * @param username
	 * @param password
	 * @param fullname
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-60532", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyOnceSrcLocIsConfig(String username, String password, String fullname) throws Exception {
		String dataname = "Automation" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-60532 - O365");
		base.stepInfo(
				"Verify that once source selection is configured succesfully with connection to the source location, the user should be taken to the next intended step of the collection wizard");

		String[][] userRolesData = { { username, fullname } };

		// Login as User
		login.loginToSightLine(username, password);
		userManagement.navigateToUsersPAge();
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");

		// navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Click create New Collection
		collection.performCreateNewCollection();

		// Verify Error Message In Add new source Popup
		collection.performAddNewSource(null, dataname, Input.TenantID, Input.ApplicationID, Input.ApplicationKey);

		// verify Added source location is displayed
		collection.verifyAddedSourceLocation(dataname, null);

		// click created source location and verify navigated page
		collection.verifyCollectionInfoPage(dataname, null, false);

		// delete created source location
		dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);
		source.deleteSourceLocation(dataname, false);

		// Logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that error message should be displayed if collection
	 *              name is blank on click of ‘Next’ button
	 * @param username
	 * @param password
	 * @param fullname
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-60647", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyErrorMsgInCollectInfoPage(String username, String password, String fullname) throws Exception {
		String dataname = "Automation" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-60647 - O365");
		base.stepInfo(
				"Verify that once source selection is configured succesfully with connection to the source location, the user should be taken to the next intended step of the collection wizard");

		String[][] userRolesData = { { username, fullname } };

		// Login as User
		login.loginToSightLine(username, password);
		userManagement.navigateToUsersPAge();
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");

		// navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Click create New Collection
		collection.performCreateNewCollection();

		// Verify Error Message In Add new source Popup
		collection.performAddNewSource(null, dataname, Input.TenantID, Input.ApplicationID, Input.ApplicationKey);

		// verify Added source location is displayed
		collection.verifyAddedSourceLocation(dataname, null);

		// click created source location and verify navigated page
		// verify error message when collection name is empty
		collection.verifyCollectionInfoPage(dataname, "", true);

		// delete created source location
		dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);
		source.deleteSourceLocation(dataname, false);

		// Logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that collection wizard should list all configured
	 *              source locations in the project
	 * @param username
	 * @param password
	 * @param fullname
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-60529", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyCollectionWizard(String username, String password, String fullname) throws Exception {
		String dataname = "Automation" + Utility.dynamicNameAppender();
		String headerName="Data Source Name";
		List<String> SrcLocInSrcPage = new ArrayList<String>();

		base.stepInfo("Test case Id: RPMXCON-60529 - O365");
		base.stepInfo("Verify that collection wizard should list all configured source locations in the project");

		String[][] userRolesData = { { username, fullname } };

		// Login as User
		login.loginToSightLine(username, password);
		userManagement.navigateToUsersPAge();
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");

		// navigate to Source page
		dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);

		// Add new source location on Source Page and get the list of Available source locations 
		collection.performAddNewSource(null, dataname, Input.TenantID, Input.ApplicationID, Input.ApplicationKey);
		SrcLocInSrcPage = source.getListFromTable(headerName, true);

		// navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Click create New Collection
		collection.performCreateNewCollection();

		// verify available source locations in project is dispalyed on source selection tab
		collection.verifyAddedSourceLocation(null, SrcLocInSrcPage);

		// delete created source location
		dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);
		source.deleteSourceLocation(dataname, false);
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
