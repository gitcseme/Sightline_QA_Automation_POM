package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.openqa.selenium.JavascriptExecutor;
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
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.ManageAssignment;
import pageFactory.ReusableDocViewPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;
import views.html.helper.input;

public class UsersAndRoleManagement_Regression {
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	DocViewMetaDataPage docViewMetaDataPage;
	ManageAssignment manageAssignment;
	DocViewPage docView;
	Utility utility;
	AssignmentsPage agnmt;
	DocViewRedactions redact;
	SecurityGroupsPage security;
	UserManagement userManage;
	SessionSearch sessionSearch;
	DocViewPage docViewPage;
	SoftAssert softAssertion;

	@BeforeClass(alwaysRun = true)
	private void TestStart() throws Exception, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		// Open browser
		Input in = new Input();
		in.loadEnvConfig();
	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("Executing method : " + testMethod.getName());
		UtilityLog.info("Executing method : " + testMethod.getName());
		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);

	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * TestCase id : 52426 - To verify when assigns user to different project with
	 * different role of existing project. Description : To verify when assigns user
	 * to different project with different role of existing project.
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 1)
	public void createUserWithDifferentProjectAndRole() throws Exception {
		baseClass = new BaseClass(driver);
		String firstName = Input.randomText + Utility.dynamicNameAppender();
		String lastName = Input.randomText + Utility.dynamicNameAppender();
		String role = "Review Manager";
		String role2 = "Reviewer";
		String project1 = Input.projectName;
		String project2 = "MasterAutomationData";
		String emailId = Input.randomText + Utility.dynamicNameAppender() + "@consilio.com";
		baseClass.stepInfo("Test case Id: RPMXCON-52426");
		utility = new Utility(driver);
		baseClass.stepInfo(
				"#### To verify when assigns user to different project with different role of existing project ####");
		userManage = new UserManagement(driver);
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("Logged in as User: " + Input.sa1userName);
		Reporter.log("Logged in as User: " + Input.sa1password);

		baseClass.stepInfo("Create new user");
		userManage.createNewUser(firstName, lastName, role, emailId, " ", project1);

		baseClass.stepInfo(
				"Create new user with same username with different project and different role as previously created");
		userManage.createNewUser(firstName, lastName, role2, emailId, " ", project2);

		baseClass.passedStep(
				"Created new user with same username with different project and different role as previously created successfully..");

		baseClass.stepInfo("Delete added users");
		userManage.deleteAddedUser(firstName);
		
		loginPage.logout();
	}

	

	/**
	 * @author Gopinath
	 * @TestCase Id:52454 To verify users authentication session, from same machine
	 *           in browsers new window/new tab
	 * @Description:To To verify users authentication session, from same machine in
	 *                 browsers new window/new tab
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 2)
	public void verifyuserAuthenticationSession() {
		String userName = Input.rmu1userName;
		String password = Input.rmu1password;
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-52454");
		baseClass.stepInfo(
				"###To verify users authentication session, from same machine in browsers new window/new tab###");
		ReusableDocViewPage reusb = new ReusableDocViewPage(driver);
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("Logged in as User: " + Input.sa1userName);
		Reporter.log("Logged in as User: " + Input.sa1password);
		loginPage.logout();

		baseClass.stepInfo("user login to application in window 1 as session 1");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		baseClass.stepInfo("Opening second tab");
		((JavascriptExecutor) driver.getWebDriver()).executeScript("window.open()");

		baseClass.stepInfo("switching to second tab");
		String parentWindow = reusb.switchTochildWindow();
		String childWindow = driver.getWebDriver().getWindowHandle();

		baseClass.stepInfo("Entering Url in second tab");
		baseClass.waitTime(2);
		driver.getWebDriver().get(Input.url);
		System.out.println(Input.browserName + "is opned and loading application");

		baseClass.stepInfo("Enter user name and password");
		driver.waitForPageToBeReady();
		loginPage.getEuserName().waitAndClick(10); // to adjust with app!
		loginPage.getEuserName().isElementAvailable(10);
		loginPage.getEuserName().SendKeys(userName);
		// Fill password
		loginPage.getEpassword().SendKeys(password);

		baseClass.stepInfo("Cliac on loginbutton");
		loginPage.getEloginButton().Click();

		if (loginPage.getActiveSessionYesButton().isElementAvailable(3)) {
			baseClass.passedStep(
					"After click on Login Button In Second tab Active session popup is displayed with cancel and yes options");
		} else {
			baseClass.failedStep("Active Session popup is not dispalyed");
		}
		baseClass.waitForElement(loginPage.getActiveSessionYesButton());
		loginPage.getActiveSessionYesButton().Click();
		baseClass.waitForElement(loginPage.getWarningLogOutMessage());
		if (loginPage.getWarningLogOutMessage().isElementAvailable(5)) {
			driver.getWebDriver().switchTo().window(parentWindow);
			baseClass.waitForElement(loginPage.getWarningLogOutMessage());
			if (loginPage.getWarningLogOutMessage().isElementAvailable(5)) {
				baseClass.passedStep("Session was expire in both tabs");

			}
		} else {
			baseClass.failedStep("Session was not expired in second tab");
		}
		reusb.childWindowToParentWindowSwitching(childWindow);
		baseClass.stepInfo("login to appliction in second tab");
		loginPage.logout();
	}

	/**
	 * Author : Baskar date: NA Modified date:03/07/2022 Modified by: Baskar
	 * Description :To verify when 'Download Native' is Checekd/Unchecked from Edit
	 * User > functionality tab
	 */

	@DataProvider(name = "combinationRole")
	public Object[][] combination() {
		return new Object[][] { { "sa", Input.sa1userName, Input.sa1password, Input.rmu1userName, Input.rmu1password },
				{ "sa", Input.sa1userName, Input.sa1password, Input.rev1userName, Input.rev1password },
				{ "pa", Input.pa1userName, Input.pa1password, Input.rmu1userName, Input.rmu1password },
				{ "pa", Input.pa1userName, Input.pa1password, Input.rev1userName, Input.rev1password }, };
	}

	@Test(alwaysRun = true, dataProvider = "combinationRole", groups = { "regression" }, priority = 3)
	public void validatingNativeDownload(String roll, String userName, String password, String userNameTwo,
			String passWordTwo) throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52699");
		baseClass.stepInfo("To verify when 'Download Native' is Checekd/Unchecked from Edit User > functionality tab");
		userManage = new UserManagement(driver);
		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);

		// login
		loginPage.loginToSightLine(userName, password);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		userManage.passingUserName(userNameTwo);
		userManage.applyFilter();
		if (roll == "sa") {
			userManage.editFunctionality(Input.projectName);
		}
		if (roll == "pa") {
			userManage.editLoginUser();
		}

		// native uncheckbox
		userManage.getFunctionalityTab().waitAndClick(5);
		userManage.nativeDownload();

		// logout
		loginPage.logout();

		// login
		loginPage.loginToSightLine(userNameTwo, passWordTwo);
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		// validating download icon
		docViewPage.verifyNativeFile(false, false);

		// logout
		loginPage.logout();

		// login
		loginPage.loginToSightLine(userName, password);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		userManage.passingUserName(userNameTwo);
		userManage.applyFilter();
		if (roll == "sa") {
			userManage.editFunctionality(Input.projectName);
		}
		if (roll == "pa") {
			userManage.editLoginUser();
		}

		// native checkbox applying
		userManage.getFunctionalityTab().waitAndClick(5);
		userManage.nativeDownload();

		// logout
		loginPage.logout();

		// login
		loginPage.loginToSightLine(userNameTwo, passWordTwo);
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();

		// validating download icon
		docViewPage.verifyNativeFile(true, true);

		// logout
		loginPage.logout();

	}

	/**
	 * Author : Baskar date: NA Modified date:03/07/2022 Modified by: Baskar
	 * Description :To verify landing page for User when 'Ingestion' is
	 * Checekd/Unchecked from Edit User > functionality tab
	 */

	@DataProvider(name = "saAndPa")
	public Object[][] saAndPa() {
		return new Object[][] { { "sa", Input.sa1userName, Input.sa1password, Input.pa1userName, Input.pa1password },
				{ "pa", Input.pa1userName, Input.pa1password, Input.pa1userName, Input.pa1password }, };
	}

	@Test(alwaysRun = true, dataProvider = "saAndPa", groups = { "regression" }, priority = 4)
	public void validatingIngestionIcon(String roll, String userName, String password, String userNameTwo,
			String passWordTwo) throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52696");
		baseClass.stepInfo("To verify landing page for User when 'Ingestion' is"
				+ " Checekd/Unchecked from Edit User > functionality tab");
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();

		// login
		loginPage.loginToSightLine(userName, password);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		userManage.passingUserName(userNameTwo);
		userManage.applyFilter();
		if (roll == "sa") {
			userManage.editFunctionality(Input.projectName);
		}
		if (roll == "pa") {
			userManage.editLoginUser();
		}

		// Ingestion uncheckbox
		userManage.getFunctionalityTab().waitAndClick(5);
		userManage.verifyStatusIngestion("false");

		// logout
		loginPage.logout();

		// login
		loginPage.loginToSightLine(userNameTwo, passWordTwo);

		// validating ingestion icon
		userManage.verifyIngestionIcon(false, false);

		// logout
		loginPage.logout();

		// login
		loginPage.loginToSightLine(userName, password);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		driver.waitForPageToBeReady();

		// Ingestion checkbox
		userManage.passingUserName(userNameTwo);
		userManage.applyFilter();
		if (roll == "sa") {
			userManage.editFunctionality(Input.projectName);
		}
		if (roll == "pa") {
			userManage.editLoginUser();
		}
		userManage.getFunctionalityTab().waitAndClick(5);
		userManage.verifyStatusIngestion("true");

		// logout
		loginPage.logout();

		// login
		loginPage.loginToSightLine(userNameTwo, passWordTwo);
		driver.waitForPageToBeReady();
		// validating ingestion icon
		userManage.verifyIngestionIcon(true, true);

		// logout
		loginPage.logout();

	}

	/**
	 * Author : Baskar date: NA Modified date:08/03/2022 Modified by: Baskar
	 * Description :To verify landing page for User when 'Manage' is
	 * Checekd/Unchecked from Edit User > functionality tab
	 */

	@DataProvider(name = "differentRole")
	public Object[][] differentRole() {
		return new Object[][] { { "sa", Input.sa1userName, Input.sa1password, Input.pa1userName, Input.pa1password },
				{ "sa", Input.sa1userName, Input.sa1password, Input.rmu1userName, Input.rmu1password },
				{ "pa", Input.pa2userName, Input.pa2password, Input.pa1userName, Input.pa1password },
				{ "pa", Input.pa1userName, Input.pa1password, Input.rmu1userName, Input.rmu1password },
				{ "pa", Input.rmu2userName, Input.rmu2password, Input.rmu1userName, Input.rmu1password }, };
	}

	@Test(alwaysRun = true, dataProvider = "differentRole", groups = { "regression" }, priority = 5)
	public void validatingManageIcon(String roll, String userName, String password, String userNameTwo,
			String passWordTwo) throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52694");
		baseClass.stepInfo("To verify landing page for User when 'Manage' is Checekd/Unchecked "
				+ "from Edit User > functionality tab");
		userManage = new UserManagement(driver);

		// login
		loginPage.loginToSightLine(userName, password);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		userManage.passingUserName(userNameTwo);
		userManage.applyFilter();
		if (roll == "sa") {
			userManage.editFunctionality(Input.projectName);
		}
		if (roll == "pa") {
			userManage.editLoginUser();
		}

		// Manage uncheckbox
		userManage.getFunctionalityTab().waitAndClick(5);
		userManage.verifyManageCheckBox();

		// logout
		loginPage.logout();

		// login
		loginPage.loginToSightLine(userNameTwo, passWordTwo);

		// validating Manage icon
		userManage.verifyManageIcon(false, false);

		// logout
		loginPage.logout();

		// login
		loginPage.loginToSightLine(userName, password);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		userManage.passingUserName(userNameTwo);
		userManage.applyFilter();
		if (roll == "sa") {
			userManage.editFunctionality(Input.projectName);
		}
		if (roll == "pa") {
			userManage.editLoginUser();
		}

		// Manage checkbox applying
		userManage.getFunctionalityTab().waitAndClick(5);
		userManage.verifyManageCheckBox();

		// logout
		loginPage.logout();

		// login
		loginPage.loginToSightLine(userNameTwo, passWordTwo);

		// validating Manage icon
		userManage.verifyManageIcon(true, true);

		// logout
		loginPage.logout();

	}

	/**
	 * Author : Baskar date: NA Modified date:08/03/2022 Modified by: Baskar
	 * Description :To verify landing page for User when 'Categorize' is
	 * Checekd/Unchecked from Edit User > functionality tab
	 */

	@DataProvider(name = "fourRole")
	public Object[][] fourRole() {
		return new Object[][] { { "sa", Input.sa1userName, Input.sa1password, Input.pa1userName, Input.pa1password },
				{ "sa", Input.sa1userName, Input.sa1password, Input.rmu1userName, Input.rmu1password },
				{ "pa", Input.pa1userName, Input.pa1password, Input.pa1userName, Input.pa1password },
				{ "pa", Input.pa1userName, Input.pa1password, Input.rmu1userName, Input.rmu1password }, };
	}

	@Test(alwaysRun = true, dataProvider = "fourRole", groups = { "regression" }, priority = 6)
	public void validatingCategorizeIcon(String roll, String userName, String password, String userNameTwo,
			String passWordTwo) throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52692");
		baseClass.stepInfo("To verify landing page for User when 'Categorize' is "
				+ "Checekd/Unchecked from Edit User > functionality tab");
		userManage = new UserManagement(driver);

		// login
		loginPage.loginToSightLine(userName, password);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		userManage.passingUserName(userNameTwo);
		userManage.applyFilter();
		if (roll == "sa") {
			userManage.editFunctionality(Input.projectName);
		}
		if (roll == "pa") {
			userManage.editLoginUser();
		}

		// Categorize uncheckbox
		userManage.getFunctionalityTab().waitAndClick(5);
		userManage.verifyStatusCategorize("false");

		// logout
		loginPage.logout();

		// login
		loginPage.loginToSightLine(userNameTwo, passWordTwo);

		// validating Categorize icon
		userManage.verifyCategorizeIcon(false, false);

		// logout
		loginPage.logout();

		// login
		loginPage.loginToSightLine(userName, password);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		userManage.passingUserName(userNameTwo);
		userManage.applyFilter();
		if (roll == "sa") {
			userManage.editFunctionality(Input.projectName);
		}
		if (roll == "pa") {
			userManage.editLoginUser();
		}

		// Categorize checkbox applying
		userManage.getFunctionalityTab().waitAndClick(5);
		userManage.verifyStatusCategorize("true");

		// logout
		loginPage.logout();

		// login
		loginPage.loginToSightLine(userNameTwo, passWordTwo);

		// validating Categorize icon
		userManage.verifyCategorizeIcon(true, true);

		// logout
		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 08/03/2022 Modified date:NA Modified by:NA
	 * Description :To Verify User can not access to 'RunAnalytics' and 'Unpublish'
	 * even though 'INGESTIONS' right is unchecked. 'RPMXCON-52689' sprint-13
	 */
	@Test(alwaysRun = true, dataProvider = "saImpPa", groups = { "regression" }, priority = 7)
	public void validatingIngestionInLeftMenu(String roll, String userName, String password, String userNameTwo,
			String passWordTwo) throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52689");
		baseClass.stepInfo(
				"To Verify User can not access to 'RunAnalytics' and 'Unpublish' even though 'INGESTIONS' right is unchecked.");
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();

		// login
		loginPage.loginToSightLine(userName, password);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		userManage.passingUserName(userName);
		userManage.applyFilter();
		if (roll == "sa") {
			baseClass.waitForElement(userManage.getSelectUserToSaUserEdit());
			userManage.getSelectUserToSaUserEdit().waitAndClick(5);
		}
		// Ingestion uncheckbox
		userManage.getFunctionalityTab().waitAndClick(5);
		userManage.verifyStatusIngestion("false");
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		userManage.passingUserName(userNameTwo);
		userManage.applyFilter();
		if (roll == "sa") {
			userManage.editFunctionality(Input.projectName);
		}
		// Ingestion uncheckbox
		userManage.getFunctionalityTab().waitAndClick(5);
		userManage.verifyStatusIngestion("false");

		// logout
		loginPage.logout();

		// Login As SA
		loginPage.loginToSightLine(userName, password);

		// impersonate SA to PA
		baseClass.impersonateSAtoPA();

		// validating ingestion icon
		userManage.verifyIngestionIcon(false, false);
		// logout
		loginPage.logout();

		// login
		loginPage.loginToSightLine(userNameTwo, passWordTwo);
		driver.waitForPageToBeReady();
		// validating ingestion icon
		userManage.verifyIngestionIcon(false, false);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		userManage.passingUserName(userNameTwo);
		userManage.applyFilter();
		userManage.editLoginUser();

		// Ingestion checkbox
		userManage.getFunctionalityTab().waitAndClick(5);
		userManage.verifyStatusIngestion("true");

		// logout
		loginPage.logout();

		// login
		loginPage.loginToSightLine(userName, password);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		userManage.passingUserName(userName);
		userManage.applyFilter();
		if (roll == "sa") {
			baseClass.waitForElement(userManage.getSelectUserToSaUserEdit());
			userManage.getSelectUserToSaUserEdit().waitAndClick(5);
		}
		// Ingestion uncheckbox
		userManage.getFunctionalityTab().waitAndClick(5);
		userManage.verifyStatusIngestion("true");
		// logout
		loginPage.logout();
	}
	

	/**
	 * Author : Vijaya.Rani date: 09/03/2022 Modified date:NA Modified by:NA
	 * Description :Verify when user enters First Name to search in 'Filter by user
	 * name' text box and hits enter key. 'RPMXCON-53179' sprint-13
	 */

	@Test(alwaysRun = true, groups = { "regression" }, priority = 8)
	public void validatingEnterFirstNameSearchFilterByUserName() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-53179");
		baseClass.stepInfo(
				"Verify when user enters First Name to search in 'Filter by user name' text box and hits enter key.");
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();
		String fullName = Input.pa1FullName;
	    String[] splittingFullName=fullName.split(" ");
	    String firstName=splittingFullName[0];
		
		// Login As SA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");

		userManage.passingUserName(firstName);
		userManage.applyFilter();
		baseClass.stepInfo("Apply Filter Button in clicked successfully");

		driver.waitForPageToBeReady();
		String AfterfilterUserName = userManage.getFirstNameTab().getText().trim();
		System.out.println(AfterfilterUserName);

		softAssertion.assertEquals(firstName, AfterfilterUserName);
		baseClass.passedStep(
				"Users containing the entered first name is searched and listed under the firstName user list is Displayed Successfully");
		softAssertion.assertAll();

		// logout
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 09/03/2022 Modified date:NA Modified by:NA
	 * Description :Verify when user enters Last Name to search in 'Filter by user
	 * name' text box and clicks to Apply. 'RPMXCON-53178' sprint-13
	 */

	@Test(alwaysRun = true, groups = { "regression" }, priority = 9)
	public void validatingEnterLastNameSearchFilterByUserName() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-53178");
		baseClass.stepInfo(
				"Verify when user enters Last Name to search in 'Filter by user name' text box and clicks to Apply.");
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();
		String str = Input.pa1FullName;
		int index = str.lastIndexOf(" ");
		String lastString = str.substring(index +1);

		// Login As SA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");

		userManage.passingUserName(lastString);
		userManage.applyFilter();
		baseClass.stepInfo("Apply Filter Button in clicked successfully");

		driver.waitForPageToBeReady();
		String AfterfilterUserLastName = userManage.getLastNameTab().getText().trim();
		int indexTwo = AfterfilterUserLastName.lastIndexOf(" ");
		String lastAfterFilter = AfterfilterUserLastName.substring(indexTwo +1);
		if (lastString.contains(lastAfterFilter)) {
			baseClass.passedStep(
					"Users containing the entered Last name is searched and listed under the lastName user list is Displayed Successfully");
		}
		else {
			baseClass.failedStep("after applying the filter for last name is not displayed");
		}


		// logout
		loginPage.logout();
	}


	/**
	 * Author : Baskar date: NA Modified date:10/03/2022 Modified by: Baskar
	 * Description :Verify RMU can edit the user within his security group
	 */

	@Test(alwaysRun = true, groups = { "regression" }, priority = 10)
	public void validatingRmuUser() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52680");
		baseClass.stepInfo("Verify RMU can edit the user within his security group");
		userManage = new UserManagement(driver);
		SoftAssert assertion = new SoftAssert();

		// login
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		String expectedURL = Input.url + "User/UserListView";
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		String actualURL = driver.getUrl();
		assertion.assertEquals(expectedURL, actualURL);
		baseClass.stepInfo("Rmu user at the Manage userlist page");
		userManage.passingUserName(Input.rev1userName);
		userManage.applyFilter();
		// editing the user
		userManage.editLoginUser();
		userManage.getFunctionalityTab().waitAndClick(5);
		userManage.nativeDownload();
		boolean flagPopUp = userManage.getPopUpMessageEditUser().Displayed();
		assertion.assertTrue(flagPopUp);
		userManage.editLoginUser();
		userManage.getFunctionalityTab().waitAndClick(5);
		userManage.nativeDownload();
		boolean flagPopUps = userManage.getPopUpMessageEditUser().Displayed();
		assertion.assertTrue(flagPopUps);
		assertion.assertAll();
		baseClass.passedStep("Rmu user can able to edit the user with his security group");

		// logout
		loginPage.logout();
	}

	/**
	 * Author : Baskar date: NA Modified date:10/03/2022 Modified by: Baskar
	 * Description :Verify list box should be displayed for security group on edit
	 * user pop up for RMU and Reviewer
	 */

	@Test(alwaysRun = true, groups = { "regression" }, priority = 11)
	public void validatingForRmuUserSecurityGroupListBox() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52660");
		baseClass.stepInfo(
				"Verify list box should be displayed for security " + "group on edit user pop up for RMU and Reviewer");
		userManage = new UserManagement(driver);
		SoftAssert assertion = new SoftAssert();

		// login
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		baseClass.stepInfo("Sa user at the Manage userlist page");
		userManage.passingUserName(Input.rmu1userName);
		userManage.applyFilter();
		// editing the user``
		userManage.editFunctionality(Input.projectName);
		driver.waitForPageToBeReady();
		// validating security group list box
		((JavascriptExecutor) driver.getWebDriver()).executeScript("document.querySelector('#s1').scrollBy(0,2000)");
		boolean securityScrollList = (boolean) ((JavascriptExecutor) driver.getWebDriver()).executeScript(
				"return document.querySelector('#ddlSg').scrollHeight>document.querySelector('#ddlSg').clientHeight;");
		assertion.assertTrue(securityScrollList);
		baseClass.passedStep("Security group list box displayed in sa user manage userlist in edit popup window");
		int count = userManage.getSecurityGroupList().size();
		baseClass.stepInfo("Security group list box displayed with :" + count + " different SG Group");
		System.out.println(count);

		assertion.assertAll();

		// logout
		loginPage.logout();
	}

	/**
	 * Author : Baskar date: NA Modified date:20/03/2022 Modified by: Baskar
	 * Description :To verify landing page for User when 'Search' is Checked from
	 * Bulk User Access Control
	 */

	@DataProvider(name = "bulkSearch")
	public Object[][] bulkSearch() {
		return new Object[][] {
				{ "pa", Input.sa1userName, Input.sa1password, Input.pa1userName, Input.pa1password,
						"Project Administrator", "sa", Input.pa1FullName },
				{ "rmu", Input.sa1userName, Input.sa1password, Input.rmu1userName, Input.rmu1password, "Review Manager",
						"sa", Input.rmu1FullName },
				{ "rev", Input.sa1userName, Input.sa1password, Input.rev1userName, Input.rev1password, "Reviewer", "sa",
						Input.rev1FullName },
				{ "pa", Input.pa1userName, Input.pa1password, Input.pa1userName, Input.pa1password,
						"Project Administrator", "pa", Input.pa1FullName },
				{ "rmu", Input.pa1userName, Input.pa1password, Input.rmu1userName, Input.rmu1password, "Review Manager",
						"pa", Input.rmu1FullName },
				{ "rev", Input.pa1userName, Input.pa1password, Input.rev1userName, Input.rev1password, "Reviewer", "pa",
						Input.rev1FullName },
				{ "pa", Input.da1userName, Input.da1password, Input.pa1userName, Input.pa1password,
						"Project Administrator", "da", Input.pa1FullName },
				{ "rmu", Input.da1userName, Input.da1password, Input.rmu1userName, Input.rmu1password, "Review Manager",
						"da", Input.rmu1FullName },
				{ "rev", Input.da1userName, Input.da1password, Input.rev1userName, Input.rev1password, "Reviewer", "da",
						Input.rev1FullName }, };
	}

	@Test(alwaysRun = true, dataProvider = "bulkSearch", groups = { "regression" }, priority = 12)
	public void validatingBulkuserSearchIcon(String roll, String loginuser, String loginPass, String rollUser,
			String rollPass, String rollId, String assignRole, String fullName) throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52690");
		baseClass.stepInfo("To verify landing page for User when 'Search' is Checked from Bulk User Access Control");
		userManage = new UserManagement(driver);

		// login
		loginPage.loginToSightLine(loginuser, loginPass);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		driver.waitForPageToBeReady();
		userManage.getBulkUserAccessTab().waitAndClick(5);
		userManage.getSelectRollId().selectFromDropdown().selectByVisibleText(rollId);
		if (roll == "pa") {
			userManage.defaultSelectionCheckboxForAllRole(true, false, true, false, true, true, true, true, true, true,
					true, true, true, true, true);
		}
		if (roll == "rmu") {
			userManage.defaultSelectionCheckboxForAllRole(true, false, true, false, true, true, true, false, false,
					true, true, true, true, true, true);
		}
		if (roll == "rev") {
			userManage.defaultSelectionCheckboxForAllRole(false, false, false, false, false, false, false, false, false,
					false, true, true, true, true, true);
		}
		if (assignRole == "pa") {
			driver.scrollingToElementofAPage(userManage.getEnableRadioBtn());
			userManage.getEnableRadioBtn().waitAndClick(5);
			if (roll == "rmu" || roll == "rev") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		if (assignRole == "sa" || assignRole == "da") {
			driver.scrollingToElementofAPage(userManage.getEnableRadioBtn());
			userManage.getEnableRadioBtn().waitAndClick(5);
			userManage.getSelectingProject().waitAndClick(5);
			userManage.getSelectDropProject(Input.projectName).waitAndClick(10);
			if (roll == "rmu" || roll == "rev") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		driver.scrollingToElementofAPage(userManage.getSelectBulkUser(fullName));
		userManage.getSelectBulkUser(fullName).waitAndClick(5);
		userManage.getBulkUserSaveBtn().waitAndClick(5);
		baseClass.VerifySuccessMessage("Access rights applied successfully");
		// logout
		loginPage.logout();
		// login as userassigned for validation
		loginPage.loginToSightLine(rollUser, rollPass);
		// validating search icon
		userManage.verifySearchIcon(true, true, rollUser);
		// logout
		loginPage.logout();

	}

	/**
	 * Author : Baskar date: NA Modified date:20/03/2022 Modified by: Baskar
	 * Description :To verify landing page for User when 'Search' is Checked from
	 * Edit User > functionality tab
	 */

	@DataProvider(name = "threeRole")
	public Object[][] ThreeRole() {
		return new Object[][] { { "pa", Input.pa1userName, Input.pa1password, null, null },
				{ "rmu", Input.rmu1userName, Input.rmu1password, null, null },
				{ "rev", Input.pa1userName, Input.pa1password, Input.rev1userName, Input.rev1password }, };
	}

	@Test(alwaysRun = true, dataProvider = "threeRole", groups = { "regression" }, priority = 13)
	public void validatingSearchIcon(String roll, String loginuser, String loginPass, String assignUser,
			String assignPass) throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52691");
		baseClass.stepInfo(
				"To verify landing page for User when 'Search' is Checked from Edit User > functionality tab");
		userManage = new UserManagement(driver);
		SoftAssert assertion = new SoftAssert();

		// login
		loginPage.loginToSightLine(loginuser, loginPass);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		if (roll == "rev") {
			userManage.passingUserName(assignUser);
		}
		if (roll == "pa" || roll == "rmu") {
			userManage.passingUserName(loginuser);
		}
		userManage.applyFilter();
		// editing the user based on login role
		if (roll == "pa" || roll == "rmu" || roll == "rev") {
			userManage.editLoginUser();
		}
		userManage.getFunctionalityTab().waitAndClick(5);
		// validating checkbox checked
		String flag = userManage.getSearchCheck().GetAttribute("checked");
		assertion.assertTrue(Boolean.parseBoolean(flag));
		if (Boolean.parseBoolean(flag)) {
			baseClass.passedStep("Search checkbox is checked");
		} else {
			baseClass.failedStep("Search checkbox is not checked");
		}
		// logout
		loginPage.logout();
		// login as roll
		loginPage.loginToSightLine(loginuser, loginPass);

		// validating search icon
		userManage.verifySearchIcon(true, true, loginuser);
		// logout
		loginPage.logout();
	}

	/**
	 * Author : Baskar date: NA Modified date:20/03/2022 Modified by: Baskar
	 * Description :To verify landing page for User when 'Categorize' is
	 * Checekd/Unchecked from Bulk User Access Control
	 */

	@DataProvider(name = "bulkCategorize")
	public Object[][] basedRollId() {
		return new Object[][] {
				{ "pa", Input.sa1userName, Input.sa1password, "Project Administrator", Input.pa1userName,
						Input.pa1password, "sa", Input.pa1FullName },
				{ "rmu", Input.sa1userName, Input.sa1password, "Review Manager", Input.rmu1userName, Input.rmu1password,
						"sa", Input.rmu1FullName },
				{ "pa", Input.pa1userName, Input.pa1password, "Project Administrator", Input.pa1userName,
						Input.pa1password, "pa", Input.pa1FullName },
				{ "rmu", Input.pa1userName, Input.pa1password, "Review Manager", Input.rmu1userName, Input.rmu1password,
						"pa", Input.rmu1FullName },
				{ "pa", Input.da1userName, Input.da1password, "Project Administrator", Input.pa1userName,
						Input.pa1password, "da", Input.pa1FullName },
				{ "rmu", Input.da1userName, Input.da1password, "Review Manager", Input.rmu1userName, Input.rmu1password,
						"da", Input.rmu1FullName }, };
	}

	@Test(alwaysRun = true, dataProvider = "bulkCategorize", groups = { "regression" }, priority = 14)
	public void validatingBulkUserCategorizeIcon(String roll, String loginuser, String loginPass, String rollId,
			String rollUser, String rollPass, String assignRole, String firstName) throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52693");
		baseClass.stepInfo("To verify landing page for User when 'Categorize' is Checekd/Unchecked "
				+ "from Bulk User Access Control");
		userManage = new UserManagement(driver);

		// login
		loginPage.loginToSightLine(loginuser, loginPass);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		driver.waitForPageToBeReady();
		userManage.getBulkUserAccessTab().waitAndClick(5);
		userManage.getSelectRollId().selectFromDropdown().selectByVisibleText(rollId);
		if (roll == "pa") {
			userManage.defaultSelectionCheckboxForAllRole(true, false, true, true, true, true, false, true, true, true,
					true, true, true, true, true);
		}
		if (roll == "rmu") {
			userManage.defaultSelectionCheckboxForAllRole(true, false, true, true, true, true, false, false, false,
					true, true, true, true, true, true);
		}
		baseClass.stepInfo("Enabling the radio btn for Categorize checkbox");
		if (assignRole == "pa") {
			driver.scrollingToElementofAPage(userManage.getEnableRadioBtn());
			userManage.getEnableRadioBtn().waitAndClick(5);
			if (roll == "rmu") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		if (assignRole == "sa" || assignRole == "da") {
			driver.scrollingToElementofAPage(userManage.getEnableRadioBtn());
			userManage.getEnableRadioBtn().waitAndClick(5);
			userManage.getSelectingProject().waitAndClick(5);
			userManage.getSelectDropProject(Input.projectName).waitAndClick(10);
			if (roll == "rmu" || roll == "rev") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		driver.scrollingToElementofAPage(userManage.getSelectBulkUser(firstName));
		userManage.getSelectBulkUser(firstName).waitAndClick(5);
		userManage.getBulkUserSaveBtn().waitAndClick(5);
		baseClass.VerifySuccessMessage("Access rights applied successfully");
		// logout
		loginPage.logout();
		// login as userassigned for validation
		loginPage.loginToSightLine(rollUser, rollPass);
		// validating search icon
		userManage.verifyBulkCategorizeIcon(true, true, rollUser);
		// logout
		loginPage.logout();
		// login
		loginPage.loginToSightLine(loginuser, loginPass);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		driver.waitForPageToBeReady();
		userManage.getBulkUserAccessTab().waitAndClick(5);
		userManage.getSelectRollId().selectFromDropdown().selectByVisibleText(rollId);
		if (roll == "pa") {
			userManage.defaultSelectionCheckboxForAllRole(true, false, true, true, true, true, false, true, true, true,
					true, true, true, true, true);
		}
		if (roll == "rmu") {
			userManage.defaultSelectionCheckboxForAllRole(true, false, true, true, true, true, false, false, false,
					true, true, true, true, true, true);
		}
		baseClass.stepInfo("Disable the radio btn for Categorize checkbox");
		if (assignRole == "pa") {
			driver.scrollingToElementofAPage(userManage.getDisableRadioBtn());
			userManage.getDisableRadioBtn().waitAndClick(5);
			if (roll == "rmu") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		if (assignRole == "sa" || assignRole == "da") {
			driver.scrollingToElementofAPage(userManage.getDisableRadioBtn());
			userManage.getDisableRadioBtn().waitAndClick(5);
			userManage.getSelectingProject().waitAndClick(5);
			userManage.getSelectDropProject(Input.projectName).waitAndClick(10);
			if (roll == "rmu" || roll == "rev") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		driver.scrollingToElementofAPage(userManage.getSelectBulkUser(firstName));
		userManage.getSelectBulkUser(firstName).waitAndClick(5);
		userManage.getBulkUserSaveBtn().waitAndClick(5);
		baseClass.VerifySuccessMessage("Access rights applied successfully");
		// logout
		loginPage.logout();
		// login as userassigned for validation
		loginPage.loginToSightLine(rollUser, rollPass);
		// validating search icon
		userManage.verifyBulkCategorizeIcon(false, false, rollUser);
		// logout
		loginPage.logout();

	}

	/**
	 * Author : Baskar date: NA Modified date:20/03/2022 Modified by: Baskar
	 * Description :To verify landing page for User when 'Manage' is
	 * Checekd/Unchecked from Bulk User Access Control
	 */

	@DataProvider(name = "bulkManage")
	public Object[][] bulkManage() {
		return new Object[][] {
				{ "pa", Input.sa1userName, Input.sa1password, "Project Administrator", Input.pa1userName,
						Input.pa1password, "sa", Input.pa1FullName },
				{ "rmu", Input.sa1userName, Input.sa1password, "Review Manager", Input.rmu1userName, Input.rmu1password,
						"sa", Input.rmu1FullName },
				{ "pa", Input.pa2userName, Input.pa2password, "Project Administrator", Input.pa1userName,
						Input.pa1password, "pa", Input.pa1FullName },
				{ "rmu", Input.pa1userName, Input.pa1password, "Review Manager", Input.rmu1userName, Input.rmu1password,
						"pa", Input.rmu1FullName },
				{ "pa", Input.da1userName, Input.da1password, "Project Administrator", Input.pa1userName,
						Input.pa1password, "da", Input.pa1FullName },
				{ "rmu", Input.da1userName, Input.da1password, "Review Manager", Input.rmu1userName, Input.rmu1password,
						"da", Input.rmu1FullName }, };
	}

	@Test(alwaysRun = true, dataProvider = "bulkManage", groups = { "regression" }, priority = 15)
	public void validatingBulkUserManageIcon(String roll, String loginuser, String loginPass, String rollId,
			String rollUser, String rollPass, String assignRole, String firstName) throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52695");
		baseClass.stepInfo("To verify landing page for User when 'Manage' is Checekd/Unchecked "
				+ "from Bulk User Access Control");
		userManage = new UserManagement(driver);

		// login
		loginPage.loginToSightLine(loginuser, loginPass);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		driver.waitForPageToBeReady();
		userManage.getBulkUserAccessTab().waitAndClick(5);
		userManage.getSelectRollId().selectFromDropdown().selectByVisibleText(rollId);
		if (roll == "pa") {
			userManage.defaultSelectionCheckboxForAllRole(false, false, true, true, true, true, true, true, true, true,
					true, true, true, true, true);
		}
		if (roll == "rmu") {
			userManage.defaultSelectionCheckboxForAllRole(false, false, true, true, true, true, true, false, false,
					true, true, true, true, true, true);
		}
		baseClass.stepInfo("Disable the radio btn for Manage checkbox");
		if (assignRole == "pa") {
			driver.scrollingToElementofAPage(userManage.getDisableRadioBtn());
			userManage.getDisableRadioBtn().waitAndClick(5);
			if (roll == "rmu") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		if (assignRole == "sa" || assignRole == "da") {
			driver.scrollingToElementofAPage(userManage.getDisableRadioBtn());
			userManage.getDisableRadioBtn().waitAndClick(5);
			userManage.getSelectingProject().waitAndClick(5);
			userManage.getSelectDropProject(Input.projectName).waitAndClick(10);
			if (roll == "rmu") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		driver.scrollingToElementofAPage(userManage.getSelectBulkUser(firstName));
		userManage.getSelectBulkUser(firstName).waitAndClick(5);
		userManage.getBulkUserSaveBtn().waitAndClick(5);
		baseClass.VerifySuccessMessage("Access rights applied successfully");
		// logout
		loginPage.logout();
		// login as userassigned for validation
		loginPage.loginToSightLine(rollUser, rollPass);
		// validating search icon
		userManage.verifyBulkManageIcon(false, false, rollUser);
		// logout
		loginPage.logout();
		// login
		loginPage.loginToSightLine(loginuser, loginPass);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		driver.waitForPageToBeReady();
		userManage.getBulkUserAccessTab().waitAndClick(5);
		userManage.getSelectRollId().selectFromDropdown().selectByVisibleText(rollId);
		if (roll == "pa") {
			userManage.defaultSelectionCheckboxForAllRole(false, false, true, true, true, true, true, true, true, true,
					true, true, true, true, true);
		}
		if (roll == "rmu") {
			userManage.defaultSelectionCheckboxForAllRole(false, false, true, true, true, true, true, false, false,
					true, true, true, true, true, true);
		}
		baseClass.stepInfo("Enable the radio btn for Manage checkbox");
		if (assignRole == "pa") {
			driver.scrollingToElementofAPage(userManage.getEnableRadioBtn());
			userManage.getEnableRadioBtn().waitAndClick(5);
			if (roll == "rmu") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		if (assignRole == "sa" || assignRole == "da") {
			driver.scrollingToElementofAPage(userManage.getEnableRadioBtn());
			userManage.getEnableRadioBtn().waitAndClick(5);
			userManage.getSelectingProject().waitAndClick(5);
			userManage.getSelectDropProject(Input.projectName).waitAndClick(10);
			if (roll == "rmu") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		driver.scrollingToElementofAPage(userManage.getSelectBulkUser(firstName));
		userManage.getSelectBulkUser(firstName).waitAndClick(5);
		userManage.getBulkUserSaveBtn().waitAndClick(5);
		baseClass.VerifySuccessMessage("Access rights applied successfully");
		// logout
		loginPage.logout();
		// login as userassigned for validation
		loginPage.loginToSightLine(rollUser, rollPass);
		// validating search icon
		userManage.verifyBulkManageIcon(true, true, rollUser);
		// logout
		loginPage.logout();

	}

	/**
	 * Author : Baskar date: NA Modified date:20/03/2022 Modified by: Baskar
	 * Description :To verify landing page for User when 'Manage' is
	 * Checekd/Unchecked from Bulk User Access Control
	 */

	@DataProvider(name = "bulkIngestion")
	public Object[][] bulkIngestion() {
		return new Object[][] {
				{ "pa", Input.sa1userName, Input.sa1password, "Project Administrator", Input.pa1userName,
						Input.pa1password, "sa", Input.pa1FullName },
				{ "pa", Input.pa2userName, Input.pa2password, "Project Administrator", Input.pa1userName,
						Input.pa1password, "pa", Input.pa1FullName },
				{ "pa", Input.da1userName, Input.da1password, "Project Administrator", Input.pa1userName,
						Input.pa1password, "da", Input.pa1FullName }, };
	}

	@Test(alwaysRun = true, dataProvider = "bulkIngestion", groups = { "regression" }, priority = 16)
	public void validatingBulkUserIngestionIcon(String roll, String loginuser, String loginPass, String rollId,
			String rollUser, String rollPass, String assignRole, String firstName) throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52697");
		baseClass.stepInfo("To verify landing page for User when 'Manage' is Checekd/Unchecked "
				+ "from Bulk User Access Control");
		userManage = new UserManagement(driver);

		// login
		loginPage.loginToSightLine(loginuser, loginPass);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		driver.waitForPageToBeReady();
		userManage.getBulkUserAccessTab().waitAndClick(5);
		userManage.getSelectRollId().selectFromDropdown().selectByVisibleText(rollId);
		if (roll == "pa") {
			userManage.defaultSelectionCheckboxForAllRole(true, true, true, true, true, true, true, true, true, true,
					true, true, true, true, true);
		}
		baseClass.stepInfo("Disable the radio btn for Manage checkbox");
		if (assignRole == "pa") {
			driver.scrollingToElementofAPage(userManage.getDisableRadioBtn());
			userManage.getDisableRadioBtn().waitAndClick(5);
			if (roll == "rmu") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		if (assignRole == "sa" || assignRole == "da") {
			driver.scrollingToElementofAPage(userManage.getDisableRadioBtn());
			userManage.getDisableRadioBtn().waitAndClick(5);
			userManage.getSelectingProject().waitAndClick(5);
			userManage.getSelectDropProject(Input.projectName).waitAndClick(10);
			if (roll == "rmu") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		driver.scrollingToElementofAPage(userManage.getSelectBulkUser(firstName));
		userManage.getSelectBulkUser(firstName).waitAndClick(5);
		userManage.getBulkUserSaveBtn().waitAndClick(5);
		baseClass.VerifySuccessMessage("Access rights applied successfully");
		// logout
		loginPage.logout();
		// login as userassigned for validation
		loginPage.loginToSightLine(rollUser, rollPass);
		// validating search icon
		userManage.verifyBulkUserIngestionIcon(false, false, rollUser);
		// logout
		loginPage.logout();
		// login
		loginPage.loginToSightLine(loginuser, loginPass);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		driver.waitForPageToBeReady();
		userManage.getBulkUserAccessTab().waitAndClick(5);
		userManage.getSelectRollId().selectFromDropdown().selectByVisibleText(rollId);
		if (roll == "pa") {
			userManage.defaultSelectionCheckboxForAllRole(true, true, true, true, true, true, true, true, true, true,
					true, true, true, true, true);
		}
		baseClass.stepInfo("Enable the radio btn for Manage checkbox");
		if (assignRole == "pa") {
			driver.scrollingToElementofAPage(userManage.getEnableRadioBtn());
			userManage.getEnableRadioBtn().waitAndClick(5);
			if (roll == "rmu") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		if (assignRole == "sa" || assignRole == "da") {
			driver.scrollingToElementofAPage(userManage.getEnableRadioBtn());
			userManage.getEnableRadioBtn().waitAndClick(5);
			userManage.getSelectingProject().waitAndClick(5);
			userManage.getSelectDropProject(Input.projectName).waitAndClick(10);
			if (roll == "rmu") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		driver.scrollingToElementofAPage(userManage.getSelectBulkUser(firstName));
		userManage.getSelectBulkUser(firstName).waitAndClick(5);
		userManage.getBulkUserSaveBtn().waitAndClick(5);
		baseClass.VerifySuccessMessage("Access rights applied successfully");
		// logout
		loginPage.logout();
		// login as userassigned for validation
		loginPage.loginToSightLine(rollUser, rollPass);
		// validating search icon
		userManage.verifyBulkUserIngestionIcon(true, true, rollUser);
		// logout
		loginPage.logout();

	}

	/**
	 * Author : Baskar date: NA Modified date:20/03/2022 Modified by: Baskar
	 * Description :To verify landing page for User when 'All Repors' is
	 * Checekd/Unchecked from Bulk User Access Control
	 */

	@DataProvider(name = "bulkAllReport")
	public Object[][] bulkAllReport() {
		return new Object[][] {
				{ "pa", Input.sa1userName, Input.sa1password, "Project Administrator", Input.pa1userName,
						Input.pa1password, "sa", Input.pa1FullName },
				{ "rmu", Input.sa1userName, Input.sa1password, "Review Manager", Input.rmu1userName, Input.rmu1password,
						"sa", Input.rmu1FullName },
				{ "pa", Input.pa2userName, Input.pa2password, "Project Administrator", Input.pa1userName,
						Input.pa1password, "pa", Input.pa1FullName },
				{ "rmu", Input.pa1userName, Input.pa1password, "Review Manager", Input.rmu1userName, Input.rmu1password,
						"pa", Input.rmu1FullName },
				{ "pa", Input.da1userName, Input.da1password, "Project Administrator", Input.pa1userName,
						Input.pa1password, "da", Input.pa1FullName },
				{ "rmu", Input.da1userName, Input.da1password, "Review Manager", Input.rmu1userName, Input.rmu1password,
						"da", Input.rmu1FullName }, };
	}

	@Test(alwaysRun = true, dataProvider = "bulkAllReport", groups = { "regression" }, priority = 17)
	public void validatingBulkUserAllReportIcon(String roll, String loginuser, String loginPass, String rollId,
			String rollUser, String rollPass, String assignRole, String firstName) throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52700");
		baseClass.stepInfo("To verify landing page for User when 'All Repors' is Checekd/Unchecked "
				+ "from Bulk User Access Control");
		userManage = new UserManagement(driver);

		// login
		loginPage.loginToSightLine(loginuser, loginPass);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		driver.waitForPageToBeReady();
		userManage.getBulkUserAccessTab().waitAndClick(5);
		userManage.getSelectRollId().selectFromDropdown().selectByVisibleText(rollId);
		if (roll == "pa") {
			userManage.defaultSelectionCheckboxForAllRole(true, false, true, true, true, true, true, true, true, false,
					true, true, true, true, true);
		}
		if (roll == "rmu") {
			userManage.defaultSelectionCheckboxForAllRole(true, false, true, true, true, true, true, false, false,
					false, true, true, true, true, true);
		}
		baseClass.stepInfo("Disable the radio btn for AllReport checkbox");
		if (assignRole == "pa") {
			driver.scrollingToElementofAPage(userManage.getDisableRadioBtn());
			userManage.getDisableRadioBtn().waitAndClick(5);
			if (roll == "rmu") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		if (assignRole == "sa" || assignRole == "da") {
			driver.scrollingToElementofAPage(userManage.getDisableRadioBtn());
			userManage.getDisableRadioBtn().waitAndClick(5);
			userManage.getSelectingProject().waitAndClick(5);
			userManage.getSelectDropProject(Input.projectName).waitAndClick(10);
			if (roll == "rmu") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		driver.scrollingToElementofAPage(userManage.getSelectBulkUser(firstName));
		userManage.getSelectBulkUser(firstName).waitAndClick(5);
		userManage.getBulkUserSaveBtn().waitAndClick(5);
		baseClass.VerifySuccessMessage("Access rights applied successfully");
		// logout
		loginPage.logout();
		// login as userassigned for validation
		loginPage.loginToSightLine(rollUser, rollPass);
		// validating search icon
		userManage.verifyBulkUserAllReportIcon(false, false, rollUser);
		// logout
		loginPage.logout();
		// login
		loginPage.loginToSightLine(loginuser, loginPass);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		driver.waitForPageToBeReady();
		userManage.getBulkUserAccessTab().waitAndClick(5);
		userManage.getSelectRollId().selectFromDropdown().selectByVisibleText(rollId);
		if (roll == "pa") {
			userManage.defaultSelectionCheckboxForAllRole(true, false, true, true, true, true, true, true, true, false,
					true, true, true, true, true);
		}
		if (roll == "rmu") {
			userManage.defaultSelectionCheckboxForAllRole(true, false, true, true, true, true, true, false, false,
					false, true, true, true, true, true);
		}
		baseClass.stepInfo("Enable the radio btn for AllReport checkbox");
		if (assignRole == "pa") {
			driver.scrollingToElementofAPage(userManage.getEnableRadioBtn());
			userManage.getEnableRadioBtn().waitAndClick(5);
			if (roll == "rmu") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		if (assignRole == "sa" || assignRole == "da") {
			driver.scrollingToElementofAPage(userManage.getEnableRadioBtn());
			userManage.getEnableRadioBtn().waitAndClick(5);
			userManage.getSelectingProject().waitAndClick(5);
			userManage.getSelectDropProject(Input.projectName).waitAndClick(10);
			if (roll == "rmu") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		driver.scrollingToElementofAPage(userManage.getSelectBulkUser(firstName));
		userManage.getSelectBulkUser(firstName).waitAndClick(5);
		userManage.getBulkUserSaveBtn().waitAndClick(5);
		baseClass.VerifySuccessMessage("Access rights applied successfully");
		// logout
		loginPage.logout();
		// login as userassigned for validation
		loginPage.loginToSightLine(rollUser, rollPass);
		// validating search icon
		userManage.verifyBulkUserAllReportIcon(true, true, rollUser);
		// logout
		loginPage.logout();

	}

	/**
	 * Author : Baskar date: NA Modified date:20/03/2022 Modified by: Baskar
	 * Description :To verify for Project Admin when 'Download Native' is
	 * Checekd/Unchecked from Bulk User Access Control
	 */

	@DataProvider(name = "bulkDwNative")
	public Object[][] bulkDwNative() {
		return new Object[][] {
				{ "pa", Input.sa1userName, Input.sa1password, "Project Administrator", Input.pa1userName,
						Input.pa1password, "sa", Input.pa1FullName },
				{ "rmu", Input.sa1userName, Input.sa1password, "Review Manager", Input.rmu1userName, Input.rmu1password,
						"sa", Input.rmu1FullName },
				{ "rev", Input.sa1userName, Input.sa1password, "Reviewer", Input.rev1userName, Input.rev1password, "sa",
						Input.rev1FullName },
				{ "pa", Input.pa2userName, Input.pa2password, "Project Administrator", Input.pa1userName,
						Input.pa1password, "pa", Input.pa1FullName },
				{ "rmu", Input.pa1userName, Input.pa1password, "Review Manager", Input.rmu1userName, Input.rmu1password,
						"pa", Input.rmu1FullName },
				{ "rev", Input.pa1userName, Input.pa1password, "Reviewer", Input.rev1userName, Input.rev1password, "pa",
						Input.rev1FullName, },
				{ "pa", Input.da1userName, Input.da1password, "Project Administrator", Input.pa1userName,
						Input.pa1password, "da", Input.pa1FullName },
				{ "rmu", Input.da1userName, Input.da1password, "Review Manager", Input.rmu1userName, Input.rmu1password,
						"da", Input.rmu1FullName },
				{ "rev", Input.da1userName, Input.da1password, "Reviewer", Input.rev1userName, Input.rev1password, "da",
						Input.rev1FullName }, };
	}

	@Test(alwaysRun = true, dataProvider = "bulkDwNative", groups = { "regression" }, priority = 18)
	public void validatingBulkUserDownloadNativeIcon(String roll, String loginuser, String loginPass, String rollId,
			String rollUser, String rollPass, String assignRole, String firstName) throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52701");
		baseClass.stepInfo("To verify for Project Admin when 'Download Native' "
				+ "is Checekd/Unchecked from Bulk User Access Control");
		userManage = new UserManagement(driver);
		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);
		// login
		loginPage.loginToSightLine(loginuser, loginPass);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		driver.waitForPageToBeReady();
		userManage.getBulkUserAccessTab().waitAndClick(5);
		userManage.getSelectRollId().selectFromDropdown().selectByVisibleText(rollId);
		if (roll == "pa") {
			userManage.defaultSelectionCheckboxForAllRole(true, false, true, true, true, true, true, true, true, true,
					false, true, true, true, true);
		}
		if (roll == "rmu") {
			userManage.defaultSelectionCheckboxForAllRole(true, false, true, true, true, true, true, false, false, true,
					false, true, true, true, true);
		}
		if (roll == "rev") {
			userManage.defaultSelectionCheckboxForAllRole(false, false, false, true, false, false, false, false, false,
					false, false, true, true, true, true);
		}
		baseClass.stepInfo("Disable the radio btn for Download native checkbox");
		if (assignRole == "pa") {
			driver.scrollingToElementofAPage(userManage.getDisableRadioBtn());
			userManage.getDisableRadioBtn().waitAndClick(5);
			if (roll == "rmu" || roll == "rev") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		if (assignRole == "sa" || assignRole == "da") {
			driver.scrollingToElementofAPage(userManage.getDisableRadioBtn());
			userManage.getDisableRadioBtn().waitAndClick(5);
			userManage.getSelectingProject().waitAndClick(5);
			userManage.getSelectDropProject(Input.projectName).waitAndClick(10);
			if (roll == "rmu" || roll == "rev") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		driver.scrollingToElementofAPage(userManage.getSelectBulkUser(firstName));
		userManage.getSelectBulkUser(firstName).waitAndClick(5);
		userManage.getBulkUserSaveBtn().waitAndClick(5);
		baseClass.VerifySuccessMessage("Access rights applied successfully");
		// logout
		loginPage.logout();
		// login as userassigned for validation
		loginPage.loginToSightLine(rollUser, rollPass);
		// session search to docview
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		// validating Download native icon
		docViewPage.verifyBulkUserNativeFile(false, false, rollUser);
		// logout
		loginPage.logout();
		// login
		loginPage.loginToSightLine(loginuser, loginPass);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		driver.waitForPageToBeReady();
		userManage.getBulkUserAccessTab().waitAndClick(5);
		userManage.getSelectRollId().selectFromDropdown().selectByVisibleText(rollId);
		if (roll == "pa") {
			userManage.defaultSelectionCheckboxForAllRole(true, false, true, true, true, true, true, true, true, true,
					false, true, true, true, true);
		}
		if (roll == "rmu") {
			userManage.defaultSelectionCheckboxForAllRole(true, false, true, true, true, true, true, false, false, true,
					false, true, true, true, true);
		}
		if (roll == "rev") {
			userManage.defaultSelectionCheckboxForAllRole(false, false, false, false, false, false, false, false, false,
					false, false, true, true, true, true);
		}
		baseClass.stepInfo("Enable the radio btn for Download native checkbox");
		if (assignRole == "pa") {
			driver.scrollingToElementofAPage(userManage.getEnableRadioBtn());
			userManage.getEnableRadioBtn().waitAndClick(5);
			if (roll == "rmu" || roll == "rev") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		if (assignRole == "sa" || assignRole == "da") {
			driver.scrollingToElementofAPage(userManage.getEnableRadioBtn());
			userManage.getEnableRadioBtn().waitAndClick(5);
			userManage.getSelectingProject().waitAndClick(5);
			userManage.getSelectDropProject(Input.projectName).waitAndClick(10);
			if (roll == "rmu" || roll == "rev") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		driver.scrollingToElementofAPage(userManage.getSelectBulkUser(firstName));
		userManage.getSelectBulkUser(firstName).waitAndClick(5);
		userManage.getBulkUserSaveBtn().waitAndClick(5);
		baseClass.VerifySuccessMessage("Access rights applied successfully");
		// logout
		loginPage.logout();
		// login as userassigned for validation
		loginPage.loginToSightLine(rollUser, rollPass);
		// session search to docview
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		// validating Download native icon
		docViewPage.verifyBulkUserNativeFile(true, true, rollUser);
		// logout
		loginPage.logout();

	}
	
	@DataProvider(name = "saImpPa")
	public Object[][] saImpPa() {
		return new Object[][] { { "sa", Input.sa1userName, Input.sa1password, Input.pa1userName, Input.pa1password },
				};
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
		}
		try {
			// loginPage.logout();
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			// LoginPage.clearBrowserCache();

		} catch (Exception e) {
			System.out.println("Sessions already closed");
		}
	}
}
