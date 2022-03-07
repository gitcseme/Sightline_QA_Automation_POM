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
	}

	/**
	 * Author : Baskar date: NA Modified date:21/01/2021 Modified by: Baskar
	 * Description :Verify that System Admin can create user with Domain Admin role
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 2)
	public void createNewUserForDomain() throws Exception {
		baseClass = new BaseClass(driver);
		String firstName = Input.randomText + Utility.dynamicNameAppender();
		String lastName = Input.randomText + Utility.dynamicNameAppender();
		String role = "Domain Administrator";
		String emailId = Input.randomText + Utility.dynamicNameAppender() + "@consilio.com";
		baseClass.stepInfo("Test case Id: RPMXCON-52773");
		utility = new Utility(driver);
		baseClass.stepInfo("Verify that System Admin can create user with Domain Admin role");
		userManage = new UserManagement(driver);
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("Logged in as User: " + Input.sa1userName);
		Reporter.log("Logged in as User: " + Input.sa1password);

		baseClass.stepInfo("Create new user for domain administration");
		userManage.createNewUser(firstName, lastName, role, emailId, " ", Input.projectName);

		baseClass.stepInfo("Domain admin role displayed in dropdown field");
		baseClass.passedStep("Created new user with Domain admin rule");

		baseClass.stepInfo("Delete added users");
		userManage.deleteAddedUser(firstName);
	}

	/**
	 * @author Gopinath
	 * @TestCase Id:52454 To verify users authentication session, from same machine
	 *           in browsers new window/new tab
	 * @Description:To To verify users authentication session, from same machine in
	 *                 browsers new window/new tab
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 3)
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
		loginPage.loginToSightLine(userName, password);
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

	@Test(alwaysRun = true, dataProvider = "combinationRole", groups = { "regression" }, priority = 4)
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

	@Test(alwaysRun = true, dataProvider = "saAndPa", groups = { "regression" }, priority = 5)
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
		userManage.verifyIngestion();

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
		userManage.verifyIngestion();

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

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
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
