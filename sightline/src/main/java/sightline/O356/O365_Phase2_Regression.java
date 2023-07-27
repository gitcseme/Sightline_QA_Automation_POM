package sightline.O356;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;
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
import pageFactory.ProjectPage;
import pageFactory.SourceLocationPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class O365_Phase2_Regression {

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

	@DataProvider(name = "PaAndRmuUserDetails")
	public Object[][] PaAndRmuUserDetails() {
		Object[][] users = {
				{ Input.pa1userName, Input.pa1password, "Project Administrator", "SA" },
				{ Input.rmu1userName, Input.rmu1password, "Review Manager", "SA" } };
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

		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" },
				{ Input.rmu1userName, "Review Manager", "SA" } };

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

		String[][] userRolesData = { { Input.rev1userName, "Reviewer", "SA" } };

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

		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" },
				{ Input.rmu1userName, "Review Manager", "SA" } };

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

		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" },
				{ Input.rmu1userName, "Review Manager", "SA" } };

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

		String[][] userRolesData = { { username, fullname, fullname } };

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

		String[][] userRolesData = { { username, fullname, fullname } };

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

		String[][] userRolesData = { { username, fullname, fullname } };

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

		String[][] userRolesData = { { username, fullname, fullname } };

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
		collection.verifyCollectionInfoPage(dataname, "", false);

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

		String[][] userRolesData = { { username, fullname, fullname } };

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
		String headerName = "Data Source Name";
		List<String> SrcLocInSrcPage = new ArrayList<String>();

		base.stepInfo("Test case Id: RPMXCON-60529 - O365");
		base.stepInfo("Verify that collection wizard should list all configured source locations in the project");

		String[][] userRolesData = { { username, fullname, fullname } };

		// Login as User
		login.loginToSightLine(username, password);
		userManagement.navigateToUsersPAge();
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");

		// navigate to Source page
		dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);

		// Add new source location on Source Page and get the list of Available source
		// locations
		collection.performAddNewSource(null, dataname, Input.TenantID, Input.ApplicationID, Input.ApplicationKey);
		SrcLocInSrcPage = source.getListFromTable(headerName, true);

		// navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Click create New Collection
		collection.performCreateNewCollection();

		// verify available source locations in project is dispalyed on source selection
		// tab
		collection.verifyAddedSourceLocation(null, SrcLocInSrcPage);

		// delete created source location
		dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);
		source.deleteSourceLocation(dataname, false);

		// Logout
		login.logout();
	}

	/**
	 * @author Raghuram A
	 * @throws Exception
	 * @Date: 07/18/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that - Collections option is available Under “Datasets”
	 *              access control on bulk user access control screen. RPMXCON-60712
	 */
	@Test(description = "RPMXCON-60712", enabled = true, groups = { "regression" })
	public void verifyPAandRMUAccessForCollectionsViaBulkAssign() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-60712 - O365");
		base.stepInfo(
				"Verify that - Collections option is available Under “Datasets” access control on bulk user access control screen.");

		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", Input.pa1FullName },
				{ Input.rmu1userName, "Review Manager", Input.rmu1FullName } };

		// Login as SA
		base.stepInfo(
				"**Step-1 & 2 Pre-requisite : At least one PA User should exists and Login to Application with SysAdmin**");
		login.loginToSightLine(Input.sa1userName, Input.sa1password);

		base.stepInfo("**Step-3 Click on \"Bulk User Access Control\" button**");
		base.stepInfo("**Step-4 Select  \"Project Administrator\"  role from \"Role\" drop down**");
		base.stepInfo(
				"**Step-5 Verify that Collections option is available Under “Datasets” access control on bulk user access control screen**");
		base.stepInfo("**Step-6 Repeat Step4 and Step 5 for RMU User**");
		userManagement.verifyCollectionAndDatasetsAccessForUsersViaBulkAssign(userRolesData, false, false, "Yes", false,
				"", false, false, false);

		// Logout
		login.logout();

	}

	/**
	 * @author Raghuram A
	 * @throws Exception
	 * @Date: 07/18/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that - Collections option is NOT available Under
	 *              “Datasets” access control on bulk user access control screen.
	 *              RPMXCON-60713
	 */
	@Test(description = "RPMXCON-60713", enabled = true, groups = { "regression" })
	public void verifyREVAccessForCollectionsViaBulkAssign() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-60713 - O365");
		base.stepInfo(
				"Verify that - Collections option is NOT available Under “Datasets” access control on bulk user access control screen.");

		String[][] userRolesData = { { Input.rev1userName, "Reviewer", Input.rev1FullName } };

		// Login as SA
		base.stepInfo(
				"**Step-1 & 2 Pre-requisite : At least one Reviewer User should exists and Login to Application with SysAdmin**");
		login.loginToSightLine(Input.sa1userName, Input.sa1password);

		base.stepInfo("**Step-3 Click on \"Bulk User Access Control\" button**");
		base.stepInfo("**Step-4 Select  \"Reviewer\"  role from \"Role\" drop down**");
		base.stepInfo(
				"**Step-5 Verify that for Reviewer - Collections option is NOT available Under “Datasets” access control on bulk user access control screen**");
		userManagement.verifyCollectionAndDatasetsAccessForUsersViaBulkAssign(userRolesData, false, false, "Yes", false,
				"", false, false, false);

		// Logout
		login.logout();

	}

	/**
	 * @author Raghuram A
	 * @throws Exception
	 * @Date: 07/18/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that - By default Collections option is checked when
	 *              User selected “Datasets” access control on "bulk user access
	 *              control" Screen. RPMXCON-60716
	 */
	@Test(description = "RPMXCON-60716", enabled = true, groups = { "regression" })
	public void verifyPAandRMUdefaultCollectionAccess() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-60716 - O365");
		base.stepInfo(
				"Verify that - By default Collections option is checked when User selected “Datasets” access control on \"bulk user access control\" Screen.");

		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", Input.pa1FullName },
				{ Input.rmu1userName, "Review Manager", Input.rmu1FullName } };

		// Login as SA
		base.stepInfo(
				"**Step-1 & 2 Pre-requisite : At least one PA User should exists and Login to Application with SysAdmin**");
		login.loginToSightLine(Input.sa1userName, Input.sa1password);

		base.stepInfo("**Step-3 Click on \"Bulk User Access Control\" button**");
		base.stepInfo("**Step-4 Select  \"Project Administrator\"  role from \"Role\" drop down**");
		base.stepInfo(
				"**Step-5 Verify that Collections option is available Under “Datasets” access control on bulk user access control screen**");
		base.stepInfo(
				"**Step-6 Verify that - By default Collections option is checked when User selected “Datasets” access control on \"bulk user access control\" Screen**");
		base.stepInfo("**Step-7 Repeat same steps for RMU User**");
		userManagement.verifyCollectionAndDatasetsAccessForUsersViaBulkAssign(userRolesData, false, true, "Yes", false,
				"", false, false, false);

		// Logout
		login.logout();

	}

	/**
	 * @author Raghuram A
	 * @throws Exception
	 * @Date: 07/18/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that User can Navigate to 'Collections' and 'Source
	 *              Locations' screens when User gave Collection’s access control
	 *              permission from bulk user access control Screen. RPMXCON-60714
	 */
	@Test(description = "RPMXCON-60714", enabled = true, groups = { "regression" })
	public void verifyPAandRMUuserNavigationTOCollectionAndDatas() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-60714 - O365");
		base.stepInfo(
				"Verify that User can Navigate to 'Collections' and 'Source Locations' screens when User gave Collection’s access control permission from bulk user access control Screen");

		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", Input.pa1FullName },
				{ Input.rmu1userName, "Review Manager", Input.rmu1FullName } };

		// Login as SA
		base.stepInfo(
				"**Step-1 & 2 Pre-requisite : At least one PA User should have Collection’s access control permission.\r\n"
						+ "If NOT - follow click path mentioned in RPMXCON-60712 and Login to Application with SysAdmin**");
		login.loginToSightLine(Input.sa1userName, Input.sa1password);

		// User Access Handling
		userManagement.verifyCollectionAndDatasetsAccessForUsersViaBulkAssign(userRolesData, false, true, "Yes", true,
				"Enable", false, false, false);

		// Logout
		login.logout();

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("**Step-3 Navigate Dataset >> Collections**");
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		base.stepInfo("**Step-4 Navigate Dataset >> Source Locations**");
		dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);

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
	 * @Author Jeevitha
	 * @Description : Verify that user can configure the new collection with
	 *              selection of the existing source location
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-60608", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyNewCollectWithExistingSource(String username, String password, String fullname) throws Exception {
		String dataname = "Automation" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-60608 - O365");
		base.stepInfo(
				"Verify that user can configure the new collection with selection of the existing source location");

		String[][] userRolesData = { { username, fullname, fullname } };

		// Login as User
		login.loginToSightLine(username, password);
		userManagement.navigateToUsersPAge();
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");

		// Add new source location
		dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);
		collection.performAddNewSource(null, dataname, Input.TenantID, Input.ApplicationID, Input.ApplicationKey);

		// logout
		login.logout();

		// Login as User
		login.loginToSightLine(username, password);

		// navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Click create New Collection
		collection.performCreateNewCollection();

		// verify source locations in project is displayed on source selection
		collection.verifyAddedSourceLocation(dataname, null);

		// verify with selection of the existing source location
		collection.verifyCollectionInfoPage(dataname, "", false);

		// delete created source location
		dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);
		source.deleteSourceLocation(dataname, false);

		// Logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that from Collection Wizard user should be able to add
	 *              new source location 'Start a collection' > 'Set up a source
	 *              location' link
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-60605", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyAbleToAddNewSource(String username, String password, String fullname) throws Exception {
		String dataname = "Automation" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-60605 - O365");
		base.stepInfo(
				"Verify that from Collection Wizard user should be able to add new source location 'Start a collection' > 'Set up a source location' link");

		String[][] userRolesData = { { username, fullname, fullname } };

		// Login as User
		login.loginToSightLine(username, password);
		userManagement.navigateToUsersPAge();
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");

		// navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Click create New Collection
		collection.performCreateNewCollection();

		// Add new source location
		collection.performAddNewSource(null, dataname, Input.TenantID, Input.ApplicationID, Input.ApplicationKey);

		// verify Added source location is displayed
		collection.verifyAddedSourceLocation(dataname, null);

		// delete created source location
		dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);
		source.deleteSourceLocation(dataname, false);

		// Logout
		login.logout();
	}

	/**
	 * @Author Mohan
	 * @Description : Verify that from Collection Wizard user should be able to add
	 *              new source location 'Create New collection' > 'Set up a source
	 *              location' link.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-60603", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyCollectionWizardSetupASourceLocationToAddNewSourceLocation(String username, String password,
			String fullname) throws Exception {

		base.stepInfo("Test case Id: RPMXCON-60603 - O365");
		base.stepInfo(
				"Verify that from Collection Wizard user should be able to add new source location 'Create New collection' > 'Set up a source location' link");

		String[][] userRolesData = { { username, fullname, fullname } };
		String dataname = "NewMicrosoftO365" + Utility.dynamicNameAppender();

		// Login as User
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManagement.navigateToUsersPAge();
		userManagement.verifyCollectionAndDatasetsAccessForUsers(userRolesData, true, true, "Yes");
		login.logout();
		login.loginToSightLine(username, password);
		// navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		base.waitForElement(collection.getNewCollectionBtn());
		collection.getNewCollectionBtn().waitAndClick(5);
		
		// to verify source location is present or not
//		source.verifySourceLocationIsNotPresent();

		// To add New Source Location
		
		collection.performAddNewSource(null, dataname, Input.TenantID, Input.ApplicationID, Input.ApplicationKey);

		// Logout
		login.logout();

	}

	/**
	 * @Author Mohan
	 * @Description : Verify that from Collection Wizard user should be able to add
	 *              new source location on click of 'Start a collection' > 'Add New
	 *              Source Location' button
	 * @throws Exception
	 */
	
	@Test(description = "RPMXCON-60604",  enabled = true, groups = { "regression" })
	public void verifyCollectionWizardClickOnStartACollectionToAddNewSourceLocation() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-60604 - O365");
		base.stepInfo(
				"Verify that from Collection Wizard user should be able to add new source location 'Create New collection' > 'Set up a source location' link");

		String[][] userRolesDataPA =  { { Input.pa1userName, "Project Administrator", "SA" } };
				
		
		String datanamePA = "NewMicrosoftO365PA" + Utility.dynamicNameAppender();
		String datanameRMU = "NewMicrosoftO365RMU" + Utility.dynamicNameAppender();
		String projectName="NewProject" + Utility.dynamicNameAppender();
		ProjectPage project=new ProjectPage(driver);
		
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		project.navigateToProductionPage();
		project.AddDomainProject(projectName, Input.domainName);
		userManagement.navigateToUsersPAge();
		userManagement.AssignUserToProject(projectName, "Project Administrator", Input.pa1FullName);
		userManagement.AssignUserToProject(projectName, "Review Manager", Input.rmu1FullName);
		login.logout();
		// Login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.selectproject(projectName);
		userManagement.navigateToUsersPAge();
		
		userManagement.verifyCollectionAccess(projectName,userRolesDataPA, Input.sa1userName, Input.sa1password, Input.pa1password);
		base.selectproject(projectName);
		// To add New Source Location
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		base.waitForElement(collection.getStartALinkCollection());
		collection.getStartALinkCollection().waitAndClick(5);
		collection.performAddNewSource(null, datanamePA, Input.TenantID, Input.ApplicationID, Input.ApplicationKey);

		// Logout
		login.logout();
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.selectproject(projectName);
		userManagement.navigateToUsersPAge();
		String[][] userRolesDataRMU = {{ Input.rmu1userName, "Review Manager", "SA" } };
		userManagement.verifyCollectionAccess(projectName,userRolesDataRMU,  Input.sa1userName, Input.sa1password, Input.rmu1password);
		base.selectproject(projectName);
		// To add New Source Location
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		base.waitForElement(collection.getStartALinkCollection());
		collection.getStartALinkCollection().waitAndClick(5);
		collection.performAddNewSource(null, datanameRMU, Input.TenantID, Input.ApplicationID, Input.ApplicationKey);
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that - By default Collections option is checked when
	 *              User selected “Datasets” access control on “Edit User >>
	 *              Functionality” TAB.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-60709", enabled = true, groups = { "regression" })
	public void verifyByDefaultCollIsEnabled() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-60709 - O365");
		base.stepInfo(
				"Verify that - By default Collections option is checked when User selected “Datasets” access control on “Edit User >> Functionality” TAB.");

		// Login as SA
		base.stepInfo("**Step-1 & 2 Login to Application with SysAdmin**");
		login.loginToSightLine(Input.sa1userName, Input.sa1password);

		// verify collection button by default is checked when dataset is checked for PA
		userManagement.navigateToFunctionTab(Input.pa1userName, "SA", Input.projectName, true);
		userManagement.verifyCollectionButton(true, false);

		// verify collection button by default is checked when dataset is checked for
		// RMU
		driver.Navigate().refresh();
		base.waitTime(5);
		userManagement.navigateToFunctionTab(Input.rmu1userName, "SA", Input.projectName, true);
		userManagement.verifyCollectionButton(true, false);

		// Logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description :Verify that - after upgradation if 'Datasets' is selected then
	 *              “Collections” access control also gets selected on “Edit” User
	 *              Screen
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-60720", enabled = true, groups = { "regression" })
	public void verifyCLickAndUnclickOfColl() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-60720 - O365");
		base.stepInfo(
				"Verify that - after upgradation if 'Datasets' is selected then “Collections” access control also gets selected on “Edit” User Screen");

		// Login as SA
		base.stepInfo("**Step-1 & 2 Login to Application with SysAdmin**");
		login.loginToSightLine(Input.sa1userName, Input.sa1password);

		// verify collection button by default is checked when dataset is checked for PA
		userManagement.navigateToFunctionTab(Input.pa1userName, "SA", Input.projectName, true);
		userManagement.verifyCollectionButton(true, true);

		// Logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description :Verify that After upgradation if 'Datasets' is selected then
	 *              “Collections” access control also gets selected on “bulk user
	 *              access control\" Screen
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-60723", enabled = true, groups = { "regression" })
	public void verifyCollInBulkUserScreen() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-60723 - O365");
		base.stepInfo(
				"Verify that After upgradation if 'Datasets' is selected then “Collections” access control also gets selected on “bulk user access control\" Screen");

		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", Input.pa1FullName } };

		// Login as SA
		base.stepInfo(
				"**Step-1 & 2 Pre-requisite : At least one PA User should exists and Login to Application with SysAdmin**");
		login.loginToSightLine(Input.sa1userName, Input.sa1password);

		userManagement.verifyCollectionAndDatasetsAccessForUsersViaBulkAssign(userRolesData, true, true, "Yes", false,
				"", false, false, false);

		// Logout
		login.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description :Verify that when “Datasets” access control is NOT checked then
	 *              'Collections' option is also Un-checked on \"bulk user access
	 *              control\" Screen.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-60718", enabled = true, groups = { "regression" })
	public void verifyWhenDatasetIsUnchecked() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-60718 - O365");
		base.stepInfo(
				"Verify that when “Datasets” access control is NOT checked then 'Collections' option is also Un-checked on \"bulk user access control\" Screen.");

		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", Input.pa1FullName },
				{ Input.rmu1userName, "Review Manager", Input.rmu1FullName } };

		// Login as SA
		login.loginToSightLine(Input.sa1userName, Input.sa1password);

		userManagement.verifyCollectionAndDatasetsAccessForUsersViaBulkAssign(userRolesData, true, false,
				"unCheckValidation", false, "", false, false, false);

		// Logout
		login.logout();

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
		String collectiondataListVal = Input.collectionDatalistval;
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
		List<String> custodianDetails = collection.fillingDatasetSelection("Button",collectiondataListVal, firstName, lastName,
				collectionEmailId, selectedApp, collectionData, collectionName, 3, selectedFolder, false, false, false,
				"-", true, true, "Save", false,"",false);
		driver.waitForPageToBeReady();
		// verify Summary & Start Tab
		collection.clickNextBtnOnDatasetTab();
		base.waitTime(3);
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
		String collectiondataListVal = Input.collectionDatalistval;
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
		collection.fillingDatasetSelection("Button",collectiondataListVal, firstName, lastName, collectionEmailId, selectedApp,
				collectionData, collectionName, 3, selectedFolder, false, false, false, "-", true, true, "Save", false,"",false);

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
		String collectiondataListVal = Input.collectionDatalistval;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder1 = "Inbox";
		String headerListDataSets[] = { "Collection Id", "Collection Status", "Error Status" };
		String[] statusList = { "Completed" };
		String collectionNewName = "CollectionNew" + Utility.dynamicNameAppender();

		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String actualCollectionName;
		String[][] userRolesData = { { userName, role, actionRole } };

		base.stepInfo("Test case Id: RPMXCON-61041 - O365");
		base.stepInfo("Verify that \"View Datasets\" functionality is working fine on \"Manage Collections\" screen.");

		// Login as User
		login.loginToSightLine(userName, password);

		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);

		// get username
		String username = login.getCurrentUserName();

		// get other dataset tile view
		dataSets.navigateToDataSetsPage();
		String otherTileView = dataSets.getTileViewType();

		// Add New Collection Or get Already Present completed Collection Details
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collectionData = collection.createNewCollection(collectionData, collectionName, true, "Completed", true);

		// navigate to Collection page and get the data
		collectionName = base.returnKey(collectionData, "", false);

		// Click View Dataset or Create collection and click View Dataset
		if (collection.getNameBasedOnCollectionName(collectionName, username).isElementAvailable(3)) {
			base.stepInfo(collectionName + " : is Completed and Displayed in Collections Page");
			driver.waitForPageToBeReady();
			collection.clickViewDataset(collectionName);
			driver.waitForPageToBeReady();
			actualCollectionName = collectionName;
		} else {
			collectionData = collection.createNewCollection(collectionData, collectionNewName, true, null, false);
			custodianDetails = collection.fillingDatasetSelection("Button",collectiondataListVal, firstName, lastName, collectionEmailId,
					selectedApp, collectionData, collectionNewName, 3, selectedFolder1, true, true, true,
					Input.randomText, true, true, "Save", false,"",false);

			collection.clickNextBtnOnDatasetTab();
			driver.waitForPageToBeReady();
			collection.getStartBtn().waitAndClick(10);

			// click view dataset btn from collection page & check Completed status
			dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
			collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionNewName, statusList, 10);
			collection.clickViewDataset(collectionNewName);
			driver.waitForPageToBeReady();
			actualCollectionName = collectionNewName;

		}

		// verify is it navigating to datasets page
		driver.waitForPageToBeReady();
		base.verifyUrlLanding(Input.url + "en-us/ICE/Datasets", "Navigated To Dataset Page",
				"Navigation is not as expected");

		// verify completed collection is displayed in datasets page
		dataSets.verifysearchBoxValue(actualCollectionName, otherTileView);

		// Logout
		login.logout();
	}

	/**
	 * @author Raghuram A
	 * @throws Exception
	 * @Date: 08/24/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify final report on click of 'Download Final Report' when
	 *              Collection is in 'Completed' status. RPMXCON-61200
	 */
	@Test(description = "RPMXCON-61200", enabled = true, groups = { "regression" })
	public void verifyFileFormatDwnldAutomaticInitiate() throws Exception {
		HashMap<String, String> collectionData = new HashMap<>();
		String collectionEmailId = Input.collectionDataEmailId;
		String collectiondataListVal = Input.collectionDatalistval;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Inbox";
		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String headerListDataSets[] = { "Collection Id", "Collection Status", "Error Status" };
		String[] statusListToVerify = { Input.creatingDSstatus, Input.retreivingDSstatus, Input.virusScanStatus,
				Input.copyDSstatus };
		String[] statusList = { "Completed" };
		String expectedFileFormat = "xlsx";
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };

		base.stepInfo("Test case Id: RPMXCON-61200 - O365");
		base.stepInfo(
				"Verify final report on click of 'Download Final Report' when Collection is in 'Completed' status ");

		// Login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Login as User and verify Module Access
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, Input.pa1password);

		// create new Collection
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Click create New Collection with datasets
		collectionData = collection.createNewCollection(collectionData, collectionName, true, null, false);
		collection.fillingDatasetSelection("Button",collectiondataListVal, firstName, lastName, collectionEmailId, selectedApp,
				collectionData, collectionName, 3, selectedFolder, true, true, true, Input.randomText, true, true,
				"Save", false,"",false);

		// Start Collection
		collection.clickOnNextAndStartAnCollection();
		driver.waitForPageToBeReady();

		// Verify Collection presence with expected Status
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collection.verifyExpectedCollectionStatus(false, headerListDataSets, collectionName, statusListToVerify, 10,
				true, false, "", "");

		// Completed status check
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionName, statusList, 10);
		driver.waitForPageToBeReady();

		// Initial Notification count
		int Bgcount = base.initialBgCount();

		// Download Report
		collection.clickDownloadReportLink(collectionName, headerListDataSets, "Error Status", false, "");
		driver.waitForPageToBeReady();

		// Check NotificationCount
		base.checkNotificationCount(Bgcount, 1);
		base.notificationSelection("", false);
		base.waitTime(5);// for abnormal load time while downloading file

		// Format validation
		String fileName = base.GetLastModifiedFileName();
		base.validateFileFormat(fileName, expectedFileFormat);

		// Delete downloaded file
		base.stepInfo("Initiating delete for downloaded file");
		Files.deleteIfExists(Paths.get(fileName));

		// Logout
		login.logout();
	}

	/**
	 * @author Raghuram A
	 * @throws Exception
	 * @Date: 08/24/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify 'Cancel Collection' action when Collection is in
	 *              'Datasets created' status RPMXCON-61094
	 */
	@Test(description = "RPMXCON-61094", enabled = true, groups = { "regression" })
	public void verifyCancelCollectionInDataSetCreationCompletedStats() throws Exception {
		HashMap<String, String> collectionData = new HashMap<>();
		String collectionEmailId = Input.collectionDataEmailId;
		String collectiondataListVal = Input.collectionDatalistval;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Inbox";
		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String headerListDataSets[] = { "Collection Id", "Collection Status", "Error Status", "Collection Progress",
				"Action" };
		String[] statusListToVerify = { Input.creatingDSstatus, Input.retreivingDSstatus, Input.virusScanStatus };
		String[] statusList = { "Cancel in progress", "Draft" };
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };

		base.stepInfo("Test case Id: RPMXCON-61094 - O365");
		base.stepInfo("Verify 'Cancel Collection' action when Collection is in 'Datasets created' status");

		// Login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Login as User and verify Module Access
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, Input.pa1password);

		// Navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// create new Collection with Datasets and Initiate
		collectionData = collection.createNewCollection(collectionData, collectionName, true, null, false);
		collection.fillingDatasetSelection("Button",collectiondataListVal, firstName, lastName, collectionEmailId, selectedApp,
				collectionData, collectionName, 3, selectedFolder, true, true, true, Input.randomText, true, true,
				"Save", false,"",false);

		// Start Collection
		collection.clickOnNextAndStartAnCollection();
		driver.waitForPageToBeReady();

		// Verify Collection presence with expected Status
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collection.verifyExpectedCollectionStatus(false, headerListDataSets, collectionName, statusListToVerify, 10,
				true, true, "", "");

		// Cancel collection
		collection.clickDownloadReportLink(collectionName, headerListDataSets, "Action", false, "");
		driver.waitForPageToBeReady();
		collection.confirmationAction("Yes", Input.cancelCollectionNotification, false);

		// Cancel back to draft status
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionName, statusList, 15);

		// Logout
		login.logout();
	}

	/**
	 * @author Raghuram A
	 * @throws Exception
	 * @Date: 08/26/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that When User starts Collection process then Manage
	 *              collection screen Refresh Interval/ Reload automatically.
	 *              RPMXCON-61279
	 */
	@Test(description = "RPMXCON-61279", dataProvider = "PaAndRmuUserDetails", enabled = true, groups = {
			"regression" })
	public void verifyStausWorksWithoutRefresOrReloadingpageAuto(String userName, String password, String role,
			String actionRole) throws Exception {
		HashMap<String, String> collectionData = new HashMap<>();
		String collectionEmailId = Input.collectionDataEmailId;
		String collectiondataListVal = Input.collectionDatalistval;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Inbox";
		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String headerListDataSets[] = { "Collection Id", "Collection Status", "Error Status" };
		String[] statusListToVerify = { Input.creatingDSstatus, Input.retreivingDSstatus, Input.virusScanStatus,
				Input.copyDSstatus };
		String[] statusList = { "Completed" };
		String[][] userRolesData = { { userName, role, "SA" } };

		base.stepInfo("Test case Id: RPMXCON-61279 - O365");
		base.stepInfo(
				" Verify that When User starts Collection process then Manage collection screen Refresh Interval/ Reload automatically. ");

		// Login as User
		login.loginToSightLine(userName, password);

		// Login as User and verify Module Access
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);

		// create new Collection
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Click create New Collection with datasets
		collectionData = collection.createNewCollection(collectionData, collectionName, true, null, false);
		collection.fillingDatasetSelection("Button",collectiondataListVal, firstName, lastName, collectionEmailId, selectedApp,
				collectionData, collectionName, 3, selectedFolder, true, true, true, Input.randomText, true, true,
				"Save", false,"",false);

		// Start Collection
		collection.clickOnNextAndStartAnCollection();
		driver.waitForPageToBeReady();

		// Verify Collection presence with expected Status
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		base.stepInfo(
				"When User starts Collection process then Manage collection screen should Refresh Interval/ Reload automatically. \r\n"
						+ "\r\n"
						+ "2.It should be refreshed to present the updated progress and status of collections.");
		collection.verifyExpectedCollectionStatus(false, headerListDataSets, collectionName, statusListToVerify, 10,
				true, false, "", "");

		// Completed status check
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionName, statusList, 10);
		driver.waitForPageToBeReady();
		base.stepInfo(
				"It's automatically refreshed to present the updated progress and status columns of collections as Expected.");

		// Logout
		login.logout();
	}

	/**
	 * @Author Mohan
	 * @Description : Verify that all configured Collections and associated
	 *              properties are available on "Manage Collections" screen (Grid).
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-61012", dataProvider = "PaAndRmuUserDetails", enabled = true, groups = {
			"regression" })
	public void verifyManageCollectionScreenGridContainsTheHeaderListAndOtherDetails(String userName, String password,
			String role, String actionRole) throws Exception {

		base.stepInfo("Test case Id: RPMXCON-61012 - O365");
		base.stepInfo(
				"Verify that all configured Collections and associated properties are available on \"Manage Collections\" screen (Grid).");

		String[][] userRolesData = { { userName, role, actionRole } };

		// Login as User
		login.loginToSightLine(userName, password);

		// Login as User and verify Module Access
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);

		// navigate to Collection page
		base.stepInfo("**Step-3 Click on left menu Datasets > Collections**");
		base.selectproject();
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Verify Collection presence
		collection.getCollectionPageHeaderList();
		// logout
		login.logout();

	}

	/**
	 * @Author Mohan
	 * @Description : Verify that column “Retrieved Count” displays in Final status
	 *              "Error section pop up" screen.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-61659", dataProvider = "PaAndRmuUserDetails", enabled = true, groups = {
			"regression" })
	public void verifyErroredDatasetsInCollectionWizard(String userName, String password, String role,
			String actionRole) throws Exception {

		base.stepInfo("Test case Id: RPMXCON-61659 - O365");
		base.stepInfo(
				"Verify that column “Retrieved Count” displays in Final status \"Error section pop up\" screen. ");

		String[][] userRolesData = { { userName, role, actionRole } };
		String dataSourceName = "Automation" + Utility.dynamicNameAppender();
		String collectionEmailId = Input.collectionDataEmailId;
		String collectiondataListVal = Input.collectionDatalistval;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Archive";

		// Login as User
		base.stepInfo("**Step-2 Login as Project Admin/RMU**");
		login.loginToSightLine(userName, password);
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);

		// navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Click create New Collection
		collection.performCreateNewCollection();

		// Select source and Click create New Collection
		String srcLocation = collection.selectSourceFromTheListAvailable();

		// click created source location and verify navigated page
		HashMap<String, String> collectionInfoPage = collection.verifyCollectionInfoPage(srcLocation, dataSourceName,
				false);

		// initiate collection process
		collection.selectInitiateCollectionOrClickNext(true, true, true);

		// Add DataSets
		String dataSetNameGenerated = collection.addDataSetWithHandles("Button",collectiondataListVal,selectedFolder, firstName, lastName, collectionEmailId,
				selectedApp, collectionInfoPage, dataSourceName, 3,false,false,false,"",false,"",false);
		System.out.println(dataSetNameGenerated);

		// Select Folder
//		collection.folderToSelect(selectedFolder, true, true);
		base.waitForElement(collection.getActionBtn("Save & Done"));
		collection.getActionBtn("Save & Done").waitAndClick(5);

		base.waitForElement(collection.getConfirmationBtnAction("Confirm"));
		collection.getConfirmationBtnAction("Confirm").waitAndClick(5);
		base.VerifySuccessMessage("Dataset added successfully.");

		// Start A Collection
		collection.clickOnNextAndStartAnCollection();
		collection.verifyViewErrorDatasetsLink();

		// logout
		login.logout();

	}

	/**
	 * @Author Mohan
	 * @Description : Verify that when collection gets Paused due to errors then it
	 *              displays a notification on the top right corner in Notification
	 *              list.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-61303", dataProvider = "PaAndRmuUserDetails", enabled = true, groups = {
			"regression" })
	public void verifyErroredNotificationDatasetsInCollectionWizard(String userName, String password, String role,
			String actionRole) throws Exception {

		base.stepInfo("Test case Id: RPMXCON-61303 - O365");
		base.stepInfo(
				"Verify that when collection gets Paused due to errors then it displays a notification on the top right corner in Notification list.");

		String[][] userRolesData = { { userName, role, actionRole } };
		String dataSourceName = "Automation" + Utility.dynamicNameAppender();
		String collectionEmailId = Input.collectionDataEmailId;
		String collectiondataListVal = Input.collectionDatalistval;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Archive";

		// Login as User
		base.stepInfo("**Step-2 Login as Project Admin/RMU**");
		login.loginToSightLine(userName, password);
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);

		// navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Click create New Collection
		base.clearBullHornNotification();
		collection.performCreateNewCollection();

		// Select source and Click create New Collection
		String srcLocation = collection.selectSourceFromTheListAvailable();

		// click created source location and verify navigated page
		HashMap<String, String> collectionInfoPage = collection.verifyCollectionInfoPage(srcLocation, dataSourceName,
				false);

		// initiate collection process
		collection.selectInitiateCollectionOrClickNext(true, true, true);

		// Add DataSets
		String dataSetNameGenerated = collection.addDataSetWithHandles("Button",collectiondataListVal,selectedFolder, firstName, lastName, collectionEmailId,
				selectedApp, collectionInfoPage, dataSourceName, 3,false,false,false,"",false,"",false);
		System.out.println(dataSetNameGenerated);

		// Select Folder
//		collection.folderToSelect(selectedFolder, true, true);
		base.waitForElement(collection.getActionBtn("Save & Done"));
		collection.getActionBtn("Save & Done").waitAndClick(5);

		base.waitForElement(collection.getConfirmationBtnAction("Confirm"));
		collection.getConfirmationBtnAction("Confirm").waitAndClick(5);
		base.VerifySuccessMessage("Dataset added successfully.");

		// Start A Collection
		collection.clickOnNextAndStartAnCollection();
		collection.verifyNotificationIcon(0);
		base.passedStep(
				"When collection gets Paused due to errors then a notification on the top right corner in Notification list successfully.");

		// logout
		login.logout();

	}

	/**
	 * @Author Mohan
	 * @Description : Verify that a new collections workflow instance starts from
	 *              the Collections home page.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-61015", dataProvider = "PaAndRmuUserDetails", enabled = true, groups = {
			"regression" })
	public void verifyNewCollectionWorkFlow(String userName, String password, String role, String actionRole)
			throws Exception {

		base.stepInfo("Test case Id: RPMXCON-61015 - O365");
		base.stepInfo("Verify that a new collections workflow instance starts from the Collections home page.");

		String[][] userRolesData = { { userName, role, actionRole } };
		String dataSourceName = "AutomationCollectionWizard" + Utility.dynamicNameAppender();
		String collectionEmailId = Input.collectionDataEmailId;
		String collectiondataListVal = Input.collectionDatalistval;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Inbox";

		// Login as User
		base.stepInfo("**Step-2 Login as Project Admin/RMU**");
		login.loginToSightLine(userName, password);
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);

		// navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Click create New Collection
		collection.performCreateNewCollection();

		// Select source and Click create New Collection
		String srcLocation = collection.selectSourceFromTheListAvailable();

		// click created source location and verify navigated page
		HashMap<String, String> collectionInfoPage = collection.verifyCollectionInfoPage(srcLocation, dataSourceName,
				false);

		// initiate collection process
		collection.selectInitiateCollectionOrClickNext(true, true, true);

		// Add DataSets
		String dataSetNameGenerated = collection.addDataSetWithHandles("Button",collectiondataListVal,selectedFolder, firstName, lastName, collectionEmailId,
				selectedApp, collectionInfoPage, dataSourceName, 3,true,true,true,dataSourceName,false,"",false);
		System.out.println(dataSetNameGenerated);

		// Select Folder
//		collection.folderToSelect(selectedFolder, true, true);
//		collection.applyFilterToKeyword(dataSourceName);
		base.waitForElement(collection.getActionBtn("Save & Done"));
		collection.getActionBtn("Save & Done").waitAndClick(5);

		base.waitForElement(collection.getConfirmationBtnAction("Confirm"));
		collection.getConfirmationBtnAction("Confirm").waitAndClick(5);
		base.VerifySuccessMessage("Dataset added successfully.");

		// Start A Collection
		collection.clickOnNextAndStartAnCollection();

		// Verify Collection wizards
		driver.waitForPageToBeReady();
		driver.Navigate().refresh();
		collection.getCollectionPageHeaderList();
		base.passedStep("A new collections workflow instance is started from the Collections home page.");

		// logout
		login.logout();

	}

	/**
	 * @author Raghuram A
	 * @throws Exception
	 * @Date: 09/23/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that collection copy should not fail if file name
	 *              contains some special characters. RPMXCON-62887
	 */
	@Test(description = "RPMXCON-62887", enabled = true, groups = { "regression" })
	public void verifyDataRetreivalWithSpecialCharFilesFolder() throws Exception {
		HashMap<String, String> collectionData = new HashMap<>();
		String collectionEmailId = Input.collectionDataEmailId;
		String collectiondataListVal = Input.collectionDatalistval;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = Input.splCharEmailFolder;
		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String headerListDataSets[] = { "Collection Id", "Collection Status", "Error Status" };
		String[] statusListToVerify = { Input.creatingDSstatus, Input.retreivingDSstatus, Input.virusScanStatus,
				Input.copyDSstatus };
		String[] statusList = { "Completed" };
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };

		base.stepInfo("Test case Id: RPMXCON-62887 - O365");
		base.stepInfo("Verify that collection copy should not fail if file name contains some special characters.");
		base.failedMessage("Pre-requesties : Make sure you have a folder with special character - "
				+ Input.splCharEmailFolder
				+ " - in your configured email and should also have a file named with special characters in it");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Login as User and verify Module Access
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, Input.pa1password);

		// Create new Collection
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Click create New Collection with datasets
		collectionData = collection.createNewCollection(collectionData, collectionName, true, null, false);
		collection.fillingDatasetSelection("Button",collectiondataListVal, firstName, lastName, collectionEmailId, selectedApp,
				collectionData, collectionName, 3, selectedFolder, true, true, true, Input.randomText, true, true,
				"Save", false,"",false);

		// Start Collection
		collection.clickOnNextAndStartAnCollection();
		driver.waitForPageToBeReady();

		// Verify Collection presence with expected Status
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collection.verifyExpectedCollectionStatus(false, headerListDataSets, collectionName, statusListToVerify, 10,
				true, false, "", "");

		// Completed status check
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionName, statusList, 10);
		driver.waitForPageToBeReady();
		base.stepInfo(
				"Copying of dataset is successful even if the selected custodian folder contains file name with special characters");

		// Logout
		login.logout();
	}

	/**
	 * @author Raghuram A
	 * @throws Exception
	 * @Date: 09/23/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify 'Cancel Collection' action when Collection is in
	 *              'Retrieved' status RPMXCON-61068
	 */
	@Test(description = "RPMXCON-61068", enabled = true, groups = { "regression" })
	public void verifyCancelCollectionInDataRetrivedCompletedStats() throws Exception {
		HashMap<String, String> collectionData = new HashMap<>();
		String collectionEmailId = Input.collectionDataEmailId;
		String collectiondataListVal = Input.collectionDatalistval;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Inbox";
		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String headerListDataSets[] = { "Collection Id", "Collection Status", "Error Status", "Collection Progress",
				"Action" };
		String[] statusListToVerify = { Input.creatingDSstatus, Input.retreivingDSstatus, Input.virusScanStatus };
		String[] statusList = { "Cancel in progress", "Draft" };
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };

		base.stepInfo("Test case Id: RPMXCON-61068 - O365");
		base.stepInfo("Verify 'Cancel Collection' action when Collection is in 'Retrieved' status");

		// Login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Login as User and verify Module Access
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, Input.pa1password);

		// Navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// create new Collection with Datasets and Initiate
		collectionData = collection.createNewCollection(collectionData, collectionName, true, null, false);
		collection.fillingDatasetSelection("Button",collectiondataListVal, firstName, lastName, collectionEmailId, selectedApp,
				collectionData, collectionName, 3, selectedFolder, true, true, true, Input.randomText, true, true,
				"Save", false,"",false);

		// Start Collection
		collection.clickOnNextAndStartAnCollection();
		driver.waitForPageToBeReady();

		// Verify Collection presence with expected Status
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collection.verifyExpectedCollectionStatus(false, headerListDataSets, collectionName, statusListToVerify, 10,
				true, true, "", "");

		// Cancel collection
		collection.clickDownloadReportLink(collectionName, headerListDataSets, "Action", false, "");
		driver.waitForPageToBeReady();
		collection.confirmationAction("Yes", Input.cancelCollectionNotification, false);

		// Cancel back to draft status
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionName, statusList, 15);

		// Delete Collection
		collection.deleteUsingCollectionName(collectionName, true);

		// Logout
		login.logout();
	}

	/**
	 * @author Raghuram.A
	 * @param username
	 * @param password
	 * @param role
	 * @param actionRole
	 * @param actionUserName
	 * @param actionPassword
	 * @return
	 * @throws Exception
	 * @description : Pre-requesties for Collection draft creation
	 */
	public HashMap<String, String> verifyUserAbleToSaveCollectionAsDraft(String username, String password, String role,
			String actionRole, String actionUserName, String actionPassword, String selectedFolder,
			String additional1Status, Boolean additional2) throws Exception {

		HashMap<String, String> colllectionData = new HashMap<>();
		String collectionEmailId = Input.collectionDataEmailId;
		String collectiondataListVal = Input.collectionDatalistval;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String headerList[] = { Input.collectionDataHeader1, Input.collectionDataHeader2, Input.collectionDataHeader3,
				Input.collectionDataHeader4, Input.collectionDataHeader5, Input.collectionDataHeader6 };
		String headerListDataSets[] = { "Collection Id", "Collection Status" };
		String expectedCollectionStatus = "Draft";
		String collectionID = "";
		String dataName;
		HashMap<String, String> colllectionDataToReturn = new HashMap<>();

		// Add DataSets
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		colllectionData = collection.dataSetsCreationBasedOntheGridAvailability(firstName,collectiondataListVal, lastName, collectionEmailId,
				selectedApp, colllectionData, selectedFolder, headerList, additional1Status, "Button", 3, true, "",false,false,false,"",false,"",false);

		// navigate to Collection page and get the data
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		dataName = base.returnKey(colllectionData, "", false);
		System.out.println(dataName);
		collectionID = colllectionData.get(dataName);
		colllectionDataToReturn.put(collectionID, dataName);

		// Verify Collection presence
		collection.verifyExpectedCollectionIsPresentInTheGrid(headerListDataSets, dataName, expectedCollectionStatus,
				true, false, "");
		base.passedStep("Pre-requestied created colleciton Name :" + dataName);

		// return dataNmae created / used
		return colllectionDataToReturn;
	}

	/**
	 * @author Raghuram.A
	 * @Date: 09/23/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify sorting from Manage Collection page. RPMXCON-61633
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-61633", enabled = true, groups = { "regression" })
	public void verifySortingOfCollectionId() throws Exception {
		String selectedFolder = "Inbox";
		String sortType = "Descending";
		String columnToCheck = "Collection ID";
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };
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
		colllectionData = verifyUserAbleToSaveCollectionAsDraft(Input.pa1userName, Input.pa1password,
				"Project Administrator", "SA", Input.sa1userName, Input.sa1password, selectedFolder, "", false);
		collectionId = base.returnKey(colllectionData, "", false);

		// Click and check collection is columns is sorted in Descending
		base.stepInfo("Check Descending sorting from Manage Collections page for Collection ID");
		collection.getHeaderBtn(columnToCheck).waitAndClick(10);
		driver.waitForPageToBeReady();
		collection.verifySortingOrderOfCollectionPage(true, columnToCheck, sortType);
		base.passedStep("Sorting by Collection ID in DESC order is as Expected");

		// navigate to Collection page and Deletion
		base.stepInfo("Initiation collection deletion");
		collection.deleteUsingCollectionName(colllectionData.get(collectionId), true);

		// Logout
		login.logout();
	}

	/**
	 * @author Raghuram.A
	 * @Date: 09/26/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify sorting from Manage Collection page. RPMXCON-60913
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-60913", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyEditAnDeleteNotInCollWorkFlow(String username, String password, String fullname)
			throws Exception {
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };
		String dataname = "AASourceLocation" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-60913 - O365");
		base.stepInfo(
				"Verify that edit or delete option(s) are not available for a source location in a collection’s workflow.");

		// Login and Pre-requesties
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Pre-requesties - Access verification
		base.stepInfo("Collection Access Verification");
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);

		// navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Click create New Collection
		collection.performCreateNewCollection();

		// Add new source location
		collection.performAddNewSource(null, dataname, Input.TenantID, Input.ApplicationID, Input.ApplicationKey);

		// verify Added source location is displayed
		collection.verifyAddedSourceLocation(dataname, null);

		// Verify Edit and Delete options are not available in Collection flow
		base.printResutInReport(base.ValidateElement_AbsenceReturn(source.getSrcActionBtn(dataname, "Edit")),
				"Edit option(s) not available for a source location in a collection’s workflow.",
				"Edit option(s) should is available for a source location in a collection’s workflow.", "Pass");
		base.printResutInReport(base.ValidateElement_AbsenceReturn(source.getSrcActionBtn(dataname, "Delete")),
				"Delete option(s) not available for a source location in a collection’s workflow.",
				"Delete option(s) should is available for a source location in a collection’s workflow.", "Pass");

		// delete created source location
		base.stepInfo("Initiating delete for created source location");
		dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);
		source.columnHeader("Data Source Name").waitAndClick(3);
		source.deleteSourceLocation(dataname, false);

		// Logout
		login.logout();
	}

	/**
	 * @author Raghuram A
	 * @throws Exception
	 * @Date: 09/26/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that User can View the status and monitor the progress
	 *              of collection(s) on "Manage Screen" screen RPMXCON-61038
	 */
	@Test(description = "RPMXCON-61038", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyStausWorkProgress(String userName, String password, String role) throws Exception {
		HashMap<String, String> collectionData = new HashMap<>();
		String collectionEmailId = Input.collectionDataEmailId;
		String collectiondataListVal = Input.collectionDatalistval;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Inbox";
		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String headerListDataSets[] = { "Collection Id", "Collection Status", "Error Status", Input.progressBarHeader };
		String[] statusListToVerify = { Input.creatingDSstatus, Input.retreivingDSstatus, Input.virusScanStatus,
				Input.copyDSstatus };
		String[] statusList = { "Completed" };
		String[][] userRolesData = { { userName, role, "SA" } };

		base.stepInfo("Test case Id: RPMXCON-61038 - O365");
		base.stepInfo(
				"Verify that User can View the status and monitor the progress of collection(s) on \"Manage Screen\" screen");

		// Login as User
		login.loginToSightLine(userName, password);

		// Login as User and verify Module Access
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);

		// create new Collection
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Click create New Collection with datasets
		collectionData = collection.createNewCollection(collectionData, collectionName, true, null, false);
		collection.fillingDatasetSelection("Button",collectiondataListVal, firstName, lastName, collectionEmailId, selectedApp,
				collectionData, collectionName, 3, selectedFolder, true, true, true, Input.randomText, true, true,
				"Save", false,"",false);

		// Start Collection
		collection.clickOnNextAndStartAnCollection();

		// Verify Page Navigation
		driver.waitForPageToBeReady();
		base.verifyPageNavigation(Input.collectionPageUrl);
		base.passedStep("User should Navigate to \"Manage Collections\" screen.");
		base.stepInfo(
				"Verify that User can View the status and monitor the progress of collection(s) on \"Manage Screen\" screen");
		collection.verifyExpectedCollectionStatus(false, headerListDataSets, collectionName, statusListToVerify, 10,
				true, true, "", "");

		// Completed status check
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionName, statusList, 10);
		driver.waitForPageToBeReady();

		// Logout
		login.logout();
	}

	/**
	 * @Author Jeevitha R
	 * @Description : Verify that user can go to ‘Source Selection’ step on click of
	 *              ‘Back’ button from collection information’ step [RPMXCON-60654]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-60654", enabled = true, groups = { "regression" })
	public void verifyUserCanGoToSrceSelect() throws Exception {
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };
		String collectionName = "Collection" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-60654 - O365");
		base.stepInfo(
				"Verify that user can go to ‘Source Selection’ step on click of ‘Back’ button from collection information’ step");

		// Login and Pre-requesties
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Pre-requesties - Access verification
		base.stepInfo("Collection Access Verification");
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, Input.pa1password);

		// navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// verify collection page
		collection.performCreateNewCollection();

		// Select source and Click create New Collection
		String dataSourceName = collection.selectSourceFromTheListAvailable();

		// click created source location & enter collection name
		collection.verifyCollectionInfoPage(dataSourceName, collectionName, false);

		// select Do Not auto initiate collection process
		collection.selectInitiateCollectionOrClickNext(false, false, false);

		// click back button
		driver.waitForPageToBeReady();
		collection.getBackBtn().waitAndClick(10);

		// verify navigated to source selection tab
		driver.waitForPageToBeReady();
		collection.verifyCurrentTab("Source Selection");

		// Logout
		login.logout();
	}

	/**
	 * @author Raghuram A
	 * @throws Exception
	 * @Date: 09/28/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :Verify that User can View the status and monitor the progress
	 *              of collection(s) on \"Manage Screen\" screen. RPMXCON-61639
	 */
	@Test(description = "RPMXCON-61639", enabled = true, groups = { "regression" })
	public void verifyCollectionNameInDSpage() throws Exception {
		String selectedFolder = "Inbox";
		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String headerListDataSets[] = { "Collection Id", "Collection Status", "Error Status", Input.progressBarHeader,
				"Action" };
		String[] statusListToVerify = { Input.creatingDSstatus, Input.retreivingDSstatus, Input.virusScanStatus,
				Input.copyDSstatus };
		String[] statusList = { "Completed" };
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };

		base.stepInfo("Test case Id: RPMXCON-61639 - O365");
		base.stepInfo(
				"Verify that User can View the status and monitor the progress of collection(s) on \"Manage Screen\" screen");

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
		colllectionData = verifyUserAbleToSaveCollectionAsDraft(Input.pa1userName, Input.pa1password,
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

		// Completed status check
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionName, statusList, 10);
		driver.waitForPageToBeReady();

		// View in datasets page
		collection.clickViewDataset(collectionName);
		driver.waitForPageToBeReady();

		// Get collectioname from Dataset page and validate
		String collectionIdFromDS = base.getSpecifiedTextViaSplit(dataSets.getDataSetNameViaViewDS(), "_", 0);
		base.textCompareEquals(collectionId, collectionIdFromDS, "CollectionID is displayed on Dataset",
				"Collection ID mismatch in datasets page");

		// Logout
		login.logout();
	}

	/**
	 * @author Raghuram A
	 * @throws Exception
	 * @Date: 09/28/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify 'Delete' action when Collection is in 'Draft' status.
	 *              RPMXCON-61036
	 */
	@Test(description = "RPMXCON-61036", enabled = true, groups = { "regression" })
	public void verifyDraftStatusCollectionDeletion() throws Exception {
		String selectedFolder = "Inbox";
		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String headerListDataSets[] = { "Collection Id", "Collection Status", "Error Status", Input.progressBarHeader,
				"Action" };
		String expectedCollectionStatus = "Draft";
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };

		base.stepInfo("Test case Id: RPMXCON-61036 - O365");
		base.stepInfo("Verify 'Delete' action when Collection is in 'Draft' status");

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
		colllectionData = verifyUserAbleToSaveCollectionAsDraft(Input.pa1userName, Input.pa1password,
				"Project Administrator", "SA", Input.sa1userName, Input.sa1password, selectedFolder, "Draft", false);
		collectionId = base.returnKey(colllectionData, "", false);
		collectionName = colllectionData.get(collectionId);

		// Verify Collection presence
		collection.verifyExpectedCollectionIsPresentInTheGrid(headerListDataSets, collectionName,
				expectedCollectionStatus, true, false, "");

		// navigate to Collection page and Deletion
		base.stepInfo("Initiation collection  deletion");
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Collection Deletion with Action "No"
		base.stepInfo("Collection Deletion with Action \"No\"");
		collection.getCollectionsPageAction(collectionId).waitAndClick(5);
		collection.getCollectionsPageActionList(collectionId, "Delete").waitAndClick(5);
		collection.getConfirmationBtnAction("No").waitAndClick(5);
		base.stepInfo("Clicked No as delete confirmation");

		// verify Collection Presence in Manage collection Screen
		base.printResutInReport(base.ValidateElement_StatusReturn(collection.getCollectionAction(collectionName), 3),
				collectionName + " is Displayed in Manage Collection Screen",
				collectionName + " : is not Displayed after applying No as confirmation", "Pass");

		// Collection Deletion with Action "Yes"
		collection.collectionDeletion(collectionId);
		driver.waitForPageToBeReady();
		base.waitTime(3);

		// verify Collection Absence in Manage collection Screen
		base.printResutInReport(base.ValidateElement_StatusReturn(collection.getCollectionAction(collectionName), 3),
				collectionName + " deleted Successfully : is not Displayed in Manage Collection Screen",
				collectionName + " : is Displayed after deleting", "Fail");

		// Logout
		login.logout();
	}

	/**
	 * @Author Jeevitha R
	 * @Description : Verify that user can configure the collection with ‘Not
	 *              automatically initiate processing’ and can move to ‘Next’ step
	 *              of collection [RPMXCON-60652]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-60652", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyUserCanMoveNext(String username, String password, String fullname) throws Exception {
		String[][] userRolesData = { { username, fullname, "SA" } };
		String collectionName = "Collection" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-60652 - O365");
		base.stepInfo(
				"Verify that user can configure the collection with ‘Not automatically initiate processing’ and can move to ‘Next’ step of collection");

		// Login and Pre-requesties
		login.loginToSightLine(username, password);

		// Pre-requesties - Access verification
		base.stepInfo("Collection Access Verification");
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);

		// navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// verify collection page
		collection.performCreateNewCollection();

		// Select source and Click create New Collection
		String dataSourceName = collection.selectSourceFromTheListAvailable();

		// click created source location & enter collection name
		collection.verifyCollectionInfoPage(dataSourceName, collectionName, false);

		// select Do Not auto initiate collection process
		collection.selectInitiateCollectionOrClickNext(false, true, true);

		// Logout
		login.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that User can edit an existing source location from
	 *              “Source Locations “screen(grid). [RPMXCON-60795]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-60795", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyUserCanEditSrceName(String username, String password, String fullname) throws Exception {
		String dataname = "AAAutomation" + Utility.dynamicNameAppender();
		String RenamedDataName = "AARenamed" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-60795 - O365");
		base.stepInfo("Verify that User can edit an existing source location from “Source Locations “screen(grid).");

		String[][] userRolesData = { { username, fullname, "SA" } };

		// Login as User
		login.loginToSightLine(username, password);

		// Pre-requesties - Access verification
		base.stepInfo("Collection Access Verification");
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);

		// navigate to Source page
		dataSets.navigateToDataSets("Source", Input.sourceLocationPageUrl);

		// Add new source location on Source Page
		collection.performAddNewSource(null, dataname, Input.TenantID, Input.ApplicationID, Input.ApplicationKey);

		// Edit Data Source name from popup and save
		driver.waitForPageToBeReady();
		source.columnHeader("Data Source Name").waitAndClick(10);
		source.editSourceLocationDetails(dataname, true, collection.getDataSourceName(), "Data Source Name",
				RenamedDataName);

		// verify renamed dataname is dispalyed in grid
		driver.waitForPageToBeReady();
		source.columnHeader("Data Source Name").waitAndClick(10);
		source.verifySourceLocationName(RenamedDataName);

		// delete created source location
		source.deleteSourceLocation(RenamedDataName, false);

		// Logout
		login.logout();
	}

	/**
	 * @author Raghuram A
	 * @throws Exception
	 * @Date: 09/29/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify 'Start Collection' action when Collection is in 'Draft'
	 *              status. RPMXCON-61037
	 */
	@Test(description = "RPMXCON-61037", enabled = true, groups = { "regression" })
	public void verifyStartCollectionFromManageScreen() throws Exception {
		HashMap<String, String> collectionData = new HashMap<>();
		String selectedFolder = "Inbox";
		String collectionName = "Collection" + Utility.dynamicNameAppender();
		String headerListDataSets[] = { "Collection Id", "Collection Status", "Error Status" };
		String[] statusListToVerify = { Input.creatingDSstatus, Input.retreivingDSstatus, Input.virusScanStatus };
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };

		base.stepInfo("Test case Id: RPMXCON-61037 - O365");
		base.stepInfo("Verify 'Start Collection' action when Collection is in 'Draft' status.");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Login as User and verify Module Access
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, Input.pa1password);

		// Create new Collection
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Collection Draft creation
		collectionData = verifyUserAbleToSaveCollectionAsDraft(Input.pa1userName, Input.pa1password,
				"Project Administrator", "SA", Input.sa1userName, Input.sa1password, selectedFolder, "", false);
		String collectionId = base.returnKey(collectionData, "", false);
		collectionName = collectionData.get(collectionId);

		// Execute / Start collection Verifications
		collection.getCollectionsPageAction(collectionId).waitAndClick(5);
		collection.getCollectionsPageActionList(collectionId, "Start Collection").waitAndClick(5);
		driver.waitForPageToBeReady();
		base.VerifySuccessMessage("Collection extraction process started successfully.");

		// Verify Collection presence with expected Status
		base.stepInfo(
				"The application should start the collection process execution for the collection beginning with the first step i.e. 'Creation' - 'Retrieval'.");
		collection.verifyExpectedCollectionStatus(false, headerListDataSets, collectionName, statusListToVerify, 10,
				true, false, "", "");

		// Logout
		login.logout();
	}

	/**
	 * @author Raghuram A
	 * @throws Exception
	 * @Date: 09/30/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify 'Edit' action when Collection is in 'Draft' status.
	 *              RPMXCON-61035
	 */
	@Test(description = "RPMXCON-61035", enabled = true, groups = { "regression" })
	public void editCollectionDraftCheck() throws Exception {

		HashMap<String, String> colllectionData = new HashMap<>();
		HashMap<String, String> colllectionData2 = new HashMap<>();
		String collectionEmailId = Input.collectionDataEmailId;
		String collectiondataListVal = Input.collectionDatalistval;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Inbox";
		String headerList[] = { Input.collectionDataHeader1, Input.collectionDataHeader2, Input.collectionDataHeader3,
				Input.collectionDataHeader4, Input.collectionDataHeader5, Input.collectionDataHeader6 };
		String headerListDataSets[] = { "Collection Id", "Collection Status" };
		String expectedCollectionStatus = "Draft";
		String collectionID = "";
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };
		String collection2ndEmailId = Input.collection2ndEmailId;
		String collectiondatalistVal2 = Input.collection2nddatalistVal;
		String secondFirstName = Input.collsecondFirstName;
		String secondlastName = Input.collsecondlastName;
		String[] statusListToVerify = { Input.creatingDSstatus, Input.retreivingDSstatus, Input.virusScanStatus,
				Input.copyDSstatus };
		String[] statusList = { "Completed" };

		base.stepInfo("Test case Id: RPMXCON-61035 - O365");
		base.stepInfo("Verify 'Edit' action when Collection is in 'Draft' status");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Login as User and verify Module Access
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, Input.pa1password);

		// navigate to Collection page
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);

		// Add DataSets
		colllectionData = collection.dataSetsCreationBasedOntheGridAvailability(firstName,collectiondataListVal, lastName, collectionEmailId,
				selectedApp, colllectionData, selectedFolder, headerList, "", "Button", 3, false, "",false,false,false,"",false,"",false);

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
		collection.fillinDS(collectionName,collectiondatalistVal2, secondFirstName, secondlastName, collection2ndEmailId, selectedApp,
				colllectionData2, selectedFolder, headerList, "Button", 3, false, "Save", false, false,false,"",false,"", false);

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

		// Result
		base.stepInfo(
				"--------------------------------------------------------------------------------------------------------------------------");
		base.stepInfo(
				"Verified 'Edit' action when Collection is in 'Draft' status - Updated collection Name - Added datasets - Initiated Run");
		base.stepInfo(
				"--------------------------------------------------------------------------------------------------------------------------");

		// Logout
		login.logout();

	}

	/**
	 * @Author Jeevitha R
	 * @Description : Verify the validation message for Custodian name on clearing
	 *              the same field [RPMXCON-61631]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-61631", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyValidationMessage(String username, String password, String fullname) throws Exception {
		HashMap<String, String> collectionData = new HashMap<>();

		String collectionEmailId = Input.collectionDataEmailId;
		String collectiondataListVal = Input.collectionDatalistval;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;

		String[][] userRolesData = { { username, fullname, "SA" } };
		String collectionName = "Collection" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-61631 - O365");
		base.stepInfo("Verify the validation message for Custodian name on clearing the same field");

		// Login and Pre-requesties
		login.loginToSightLine(username, password);

		// Pre-requesties - Access verification
		base.stepInfo("Collection Access Verification");
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);

		// Add DataSets
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collectionData = collection.createNewCollection(collectionData, collectionName, true, null, false);
		collection.addNewDataSetCLick("Button");
		collection.selectCustodianInAddNewDataSetPopUp(firstName, collectiondataListVal, collectionEmailId, selectedApp);
		
		// Edit folder name and verify Dataset Selection Table
		driver.waitForPageToBeReady();
		base.clearATextBoxValue(collection.getCustodianIDInputTextField());
		
		base.waitForElement(collection.getFolderabLabel());
		collection.getFolderabLabel().javascriptclick(collection.getFolderabLabel());

		// Verify Custodian Fields Error Message
		collection.verifyErrorMessageOfDatasetTab(false, true, false);

		// Logout
		login.logout();
	}

	/**
	 * @Author Jeevitha R
	 * @Description : Verify that user can initiate collection on "Manage Screen"
	 *              screen [RPMXCON-61034]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-61034", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyCanInitiateCollection(String username, String password, String fullname) throws Exception {
		HashMap<String, String> collectionData = new HashMap<>();

		String collectionEmailId = Input.collectionDataEmailId;
		String collectiondataListVal = Input.collectionDatalistval;
		String firstName = Input.collectionDataFirstName;
		String lastName = Input.collectionDataLastName;
		String selectedApp = Input.collectionDataselectedApp;
		String selectedFolder = "Inbox";
		String headerListDataSets[] = { "Collection Id", "Collection Status", "Error Status" };
		String[] statusListToVerify = { Input.creatingDSstatus, Input.retreivingDSstatus, Input.virusScanStatus,
				Input.copyDSstatus };
		String[] statusList = { "Completed" };
		String[][] userRolesData = { { username, fullname, "SA" } };
		String collectionName = "Collection" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-61034 - O365");
		base.stepInfo("Verify that user can initiate collection on \"Manage Screen\" screen");

		// Login and Pre-requesties
		login.loginToSightLine(username, password);

		// Pre-requesties - Access verification
		base.stepInfo("Collection Access Verification");
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);

		// Start Collection
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collectionData = collection.createNewCollection(collectionData, collectionName, true, null, false);

		// Add Dataset
		collection.fillingDatasetSelection("Button",collectiondataListVal, firstName, lastName, collectionEmailId, selectedApp,
				collectionData, collectionName, 3, selectedFolder, false, false, false, "-", true, true, "Save",false,"",false);

		// Initiate collection
		collection.clickOnNextAndStartAnCollection();

		// verify Completed Status
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collection.verifyExpectedCollectionStatus(false, headerListDataSets, collectionName, statusListToVerify, 10,
				true, false, "", "");

		// Completed status check
		collection.verifyStatusUsingContainsTypeII(headerListDataSets, collectionName, statusList, 10);

		// Logout
		login.logout();
	}

	/**
	 * @Author Jeevitha R
	 * @Description : Verify that if Tenant ID is configured for the source location
	 *              then data source authentication will not perform [RPMXCON-61043]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-61043", dataProvider = "PaAndRmuUser", enabled = true, groups = { "regression" })
	public void verifyyAuthenticationIsNotRequired(String username, String password, String fullname) throws Exception {
		String[][] userRolesData = { { username, fullname, "SA" } };
		String collectionName = "Collection" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-61043 - O365");
		base.stepInfo(
				"Verify that if Tenant ID is configured for the source location then data source authentication will not perform");

		// Login and Pre-requesties
		login.loginToSightLine(username, password);

		// Pre-requesties - Access verification
		base.stepInfo("Collection Access Verification");
		userManagement.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, password);

		// Add DataSets
		dataSets.navigateToDataSets("Collections", Input.collectionPageUrl);
		collection.performCreateNewCollection();

		// Select source and Click create New Collection
		String dataSourceName = collection.selectSourceFromTheListAvailable();

		// click created source location and verify navigated page
		collection.verifyCollectionInfoPage(dataSourceName, collectionName, false);
		base.passedStep(" tenant id is stored, it is not require to authenticate to the data source");

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
