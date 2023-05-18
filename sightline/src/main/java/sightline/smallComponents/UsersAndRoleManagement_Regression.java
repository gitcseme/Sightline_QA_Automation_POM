package sightline.smallComponents;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
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
import pageFactory.MiniDocListPage;
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
	MiniDocListPage miniDocListPage;

	@BeforeClass(alwaysRun = true)
	private void TestStart() throws Exception, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		// Open browser
//		Input in = new Input();
//		in.loadEnvConfig();
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
	@Test(description ="RPMXCON-52426",alwaysRun = true, groups = { "regression" } )
	public void createUserWithDifferentProjectAndRole() throws Exception {
		baseClass = new BaseClass(driver);
		String firstName = Input.randomText + Utility.dynamicNameAppender();
		String lastName = Input.randomText + Utility.dynamicNameAppender();
		String role = "Review Manager";
		String role2 = "Reviewer";
		String project1 = Input.projectName;
		String project2 = Input.additionalDataProject;
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
		baseClass.waitTime(2);

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
	@Test(description ="RPMXCON-52454",alwaysRun = true, groups = { "regression" } )
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

	@Test(description ="RPMXCON-52699",alwaysRun = true, dataProvider = "combinationRole", groups = { "regression" })
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

	@Test(description ="RPMXCON-52696",alwaysRun = true, dataProvider = "saAndPa", groups = { "regression" })
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

	@Test(description ="RPMXCON-52694",alwaysRun = true, dataProvider = "differentRole", groups = { "regression" } )
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

	@Test(description ="RPMXCON-52692",alwaysRun = true, dataProvider = "fourRole", groups = { "regression" })
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
	@Test(description ="RPMXCON-52689",alwaysRun = true, dataProvider = "saImpPa", groups = { "regression" } )
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

	@Test(description ="RPMXCON-53179",alwaysRun = true, groups = { "regression" } )
	public void validatingEnterFirstNameSearchFilterByUserName() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-53179");
		baseClass.stepInfo(
				"Verify when user enters First Name to search in 'Filter by user name' text box and hits enter key.");
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();
		String fullName = Input.pa1FullName;
	    String[] splittingFullName=fullName.split(" ");
	    String firstName=splittingFullName[1];
		
		// Login As SA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");

		userManage.passingUserName(firstName);
		userManage.applyFilter();
		baseClass.stepInfo("Apply Filter Button in clicked successfully");

		driver.waitForPageToBeReady();
		String AfterfilterUserName = userManage.getLastNameTab().getText().trim();
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

	@Test(description ="RPMXCON-53178",alwaysRun = true, groups = { "regression" })
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

	@Test(description ="RPMXCON-52680",alwaysRun = true, groups = { "regression" } )
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

	@Test(description ="RPMXCON-52660",alwaysRun = true, groups = { "regression" } )
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

	@Test(description ="RPMXCON-52690",alwaysRun = true, dataProvider = "bulkSearch", groups = { "regression" })
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
		if (assignRole == "sa" || assignRole == "da") {
			userManage.getSelectingProject().waitAndClick(5);
			userManage.getSelectDropProject(Input.projectName).waitAndClick(10);
		}
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
			userManage.getEnableRadioBtn().javascriptclick(userManage.getEnableRadioBtn());
			if (roll == "rmu" || roll == "rev") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		Actions Act = new Actions(driver.getWebDriver());
		Act.clickAndHold(userManage.getPopupWindowHeader().getWebElement());
		Act.moveToElement(userManage.getPopupWindowHeader().getWebElement(), -10, 10);
		Act.release().build().perform();
		userManage.getSelectBulkUser(fullName).javascriptclick(userManage.getSelectBulkUser(fullName));
		userManage.getBulkUserSaveBtn().javascriptclick(userManage.getBulkUserSaveBtn());
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

	@Test(description ="RPMXCON-52691",alwaysRun = true, dataProvider = "threeRole", groups = { "regression" } )
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
						"da", Input.rmu1FullName },
				};
	}

	@Test(description ="RPMXCON-52693",alwaysRun = true, dataProvider = "bulkCategorize", groups = { "regression" } )
	public void validatingBulkUserCategorizeIcon(String roll, String loginuser, String loginPass, String rollId,
			String rollUser, String rollPass, String assignRole, String firstName) throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52693");
		baseClass.stepInfo("To verify landing page for User when 'Categorize' is Checekd/Unchecked "
				+ "from Bulk User Access Control");
		userManage = new UserManagement(driver);

		// login
		loginPage.loginToSightLine(loginuser, loginPass);
		if(assignRole.equalsIgnoreCase("da")) {
		baseClass.selectdomain(Input.domainName);
		}
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		driver.waitForPageToBeReady();
		userManage.getBulkUserAccessTab().waitAndClick(5);
		userManage.getSelectRollId().selectFromDropdown().selectByVisibleText(rollId);
		if (assignRole == "sa" || assignRole == "da") {
			userManage.getSelectingProject().waitAndClick(5);
			userManage.getSelectDropProject(Input.projectName).waitAndClick(10);
			baseClass.waitTime(2);
		}
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
			userManage.getEnableRadioBtn().javascriptclick(userManage.getEnableRadioBtn());
			if (roll == "rmu" || roll == "rev") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		Actions Act = new Actions(driver.getWebDriver());
		Act.clickAndHold(userManage.getPopupWindowHeader().getWebElement());
		Act.moveToElement(userManage.getPopupWindowHeader().getWebElement(), -10, 10);
		Act.release().build().perform();
		userManage.getSelectBulkUser(firstName).javascriptclick(userManage.getSelectBulkUser(firstName));
		userManage.getBulkUserSaveBtn().javascriptclick(userManage.getBulkUserSaveBtn());
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
		if (assignRole == "sa" || assignRole == "da") {
			userManage.getSelectingProject().waitAndClick(5);
			userManage.getSelectDropProject(Input.projectName).waitAndClick(10);
			baseClass.waitTime(2);
		}
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
			userManage.getDisableRadioBtn().javascriptclick(userManage.getDisableRadioBtn());
//			userManage.getDisableRadioBtn().waitAndClick(5);
			if (roll == "rmu" || roll == "rev") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		Act.clickAndHold(userManage.getPopupWindowHeader().getWebElement());
		Act.moveToElement(userManage.getPopupWindowHeader().getWebElement(), -10, 10);
		Act.release().build().perform();
		userManage.getSelectBulkUser(firstName).javascriptclick(userManage.getSelectBulkUser(firstName));
		userManage.getBulkUserSaveBtn().javascriptclick(userManage.getBulkUserSaveBtn());
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

	@Test(description ="RPMXCON-52695",alwaysRun = true, dataProvider = "bulkManage", groups = { "regression" } )
	public void validatingBulkUserManageIcon(String roll, String loginuser, String loginPass, String rollId,
			String rollUser, String rollPass, String assignRole, String firstName) throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52695");
		baseClass.stepInfo("To verify landing page for User when 'Manage' is Checekd/Unchecked "
				+ "from Bulk User Access Control");
		userManage = new UserManagement(driver);

		// login
		loginPage.loginToSightLine(loginuser, loginPass);
		if(loginuser.equalsIgnoreCase(Input.da1userName)) {
		baseClass.selectdomain(Input.domainName);
		}
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		driver.waitForPageToBeReady();
		userManage.getBulkUserAccessTab().waitAndClick(5);
		userManage.getSelectRollId().selectFromDropdown().selectByVisibleText(rollId);
		if (assignRole == "sa" || assignRole == "da") {
			userManage.getSelectingProject().waitAndClick(5);
			userManage.getSelectDropProject(Input.projectName).waitAndClick(10);
			baseClass.waitTime(2);
		}
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
			userManage.getDisableRadioBtn().javascriptclick(userManage.getDisableRadioBtn());
			if (roll == "rmu") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		Actions Act = new Actions(driver.getWebDriver());
		Act.clickAndHold(userManage.getPopupWindowHeader().getWebElement());
		Act.moveToElement(userManage.getPopupWindowHeader().getWebElement(), -10, 10);
		Act.release().build().perform();
		userManage.getSelectBulkUser(firstName).javascriptclick(userManage.getSelectBulkUser(firstName));
		userManage.getBulkUserSaveBtn().javascriptclick(userManage.getBulkUserSaveBtn());
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
		if (assignRole == "sa" || assignRole == "da") {
			userManage.getSelectingProject().waitAndClick(5);
			userManage.getSelectDropProject(Input.projectName).waitAndClick(10);
			baseClass.waitTime(2);
		}
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
			userManage.getEnableRadioBtn().javascriptclick(userManage.getEnableRadioBtn());
			
			if (roll == "rmu") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		Act.clickAndHold(userManage.getPopupWindowHeader().getWebElement());
		Act.moveToElement(userManage.getPopupWindowHeader().getWebElement(), -10, 10);
		Act.release().build().perform();
		userManage.getSelectBulkUser(firstName).javascriptclick(userManage.getSelectBulkUser(firstName));
		userManage.getBulkUserSaveBtn().javascriptclick(userManage.getBulkUserSaveBtn());
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

	@Test(description ="RPMXCON-52697",alwaysRun = true, dataProvider = "bulkIngestion", groups = { "regression" })
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
		if (assignRole == "sa" || assignRole == "da") {
			userManage.getSelectingProject().waitAndClick(5);
			userManage.getSelectDropProject(Input.projectName).waitAndClick(10);
			baseClass.waitTime(2);
		}
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
			userManage.getDisableRadioBtn().javascriptclick(userManage.getDisableRadioBtn());
			
			if (roll == "rmu") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		Actions Act = new Actions(driver.getWebDriver());
		Act.clickAndHold(userManage.getPopupWindowHeader().getWebElement());
		Act.moveToElement(userManage.getPopupWindowHeader().getWebElement(), -10, 10);
		Act.release().build().perform();
		userManage.getSelectBulkUser(firstName).javascriptclick(userManage.getSelectBulkUser(firstName));
		userManage.getBulkUserSaveBtn().javascriptclick(userManage.getBulkUserSaveBtn());
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
		if (assignRole == "sa" || assignRole == "da") {
			userManage.getSelectingProject().waitAndClick(5);
			userManage.getSelectDropProject(Input.projectName).waitAndClick(10);
			baseClass.waitTime(2);
		}
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
			userManage.getEnableRadioBtn().javascriptclick(userManage.getEnableRadioBtn());
			
			if (roll == "rmu") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		Act.clickAndHold(userManage.getPopupWindowHeader().getWebElement());
		Act.moveToElement(userManage.getPopupWindowHeader().getWebElement(), -10, 10);
		Act.release().build().perform();
		userManage.getSelectBulkUser(firstName).javascriptclick(userManage.getSelectBulkUser(firstName));
		userManage.getBulkUserSaveBtn().javascriptclick(userManage.getBulkUserSaveBtn());
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

	@Test(description ="RPMXCON-52700",alwaysRun = true, dataProvider = "bulkAllReport", groups = { "regression" } )
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
		Actions Act = new Actions(driver.getWebDriver());
		Act.clickAndHold(userManage.getPopupWindowHeader().getWebElement());
		Act.moveToElement(userManage.getPopupWindowHeader().getWebElement(), -10, 10);
		Act.release().build().perform();
		userManage.getSelectBulkUser(firstName).javascriptclick(userManage.getSelectBulkUser(firstName));
		userManage.getBulkUserSaveBtn().javascriptclick(userManage.getBulkUserSaveBtn());
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
		Act.clickAndHold(userManage.getPopupWindowHeader().getWebElement());
		Act.moveToElement(userManage.getPopupWindowHeader().getWebElement(), -10, 10);
		Act.release().build().perform();
		userManage.getSelectBulkUser(firstName).javascriptclick(userManage.getSelectBulkUser(firstName));
		userManage.getBulkUserSaveBtn().javascriptclick(userManage.getBulkUserSaveBtn());
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

	@Test(description ="RPMXCON-52701",alwaysRun = true, dataProvider = "bulkDwNative", groups = { "regression" } )
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
		if (assignRole == "sa" || assignRole == "da") {
			userManage.getSelectingProject().waitAndClick(5);
			userManage.getSelectDropProject(Input.projectName).waitAndClick(10);
			baseClass.waitTime(2);
		}
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
			userManage.getDisableRadioBtn().javascriptclick(userManage.getDisableRadioBtn());
			if (roll == "rmu" || roll == "rev") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		Actions Act = new Actions(driver.getWebDriver());
		Act.clickAndHold(userManage.getPopupWindowHeader().getWebElement());
		Act.moveToElement(userManage.getPopupWindowHeader().getWebElement(), -10, 10);
		Act.release().build().perform();
		userManage.getSelectBulkUser(firstName).javascriptclick(userManage.getSelectBulkUser(firstName));
		userManage.getBulkUserSaveBtn().javascriptclick(userManage.getBulkUserSaveBtn());
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
		baseClass.waitForElement(loginPage.getSignoutMenu());
		// logout
		loginPage.logout();
		// login
		loginPage.loginToSightLine(loginuser, loginPass);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		driver.waitForPageToBeReady();
		userManage.getBulkUserAccessTab().waitAndClick(5);
		userManage.getSelectRollId().selectFromDropdown().selectByVisibleText(rollId);
		if (assignRole == "sa" || assignRole == "da") {
			userManage.getSelectingProject().waitAndClick(5);
			userManage.getSelectDropProject(Input.projectName).waitAndClick(10);
			baseClass.waitTime(2);
		}
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
			userManage.getEnableRadioBtn().javascriptclick(userManage.getEnableRadioBtn());
			
			if (roll == "rmu" || roll == "rev") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		Act.clickAndHold(userManage.getPopupWindowHeader().getWebElement());
		Act.moveToElement(userManage.getPopupWindowHeader().getWebElement(), -10, 10);
		Act.release().build().perform();
		userManage.getSelectBulkUser(firstName).javascriptclick(userManage.getSelectBulkUser(firstName));
		userManage.getBulkUserSaveBtn().javascriptclick(userManage.getBulkUserSaveBtn());
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
	
	/**
	 * Author : Baskar date: NA Modified date:22/04/2022 Modified by: Baskar
	 * Description :To verify landing page for User when 'Search' is Checked from
	 * Edit User > functionality tab
	 */

	@DataProvider(name = "impersonateRmu")
	public Object[][] impersonateRmu() {
		return new Object[][] { { "rmu", Input.sa1userName, Input.sa1password, Input.rmu1userName, Input.rmu1password },
				{ "rev", Input.sa1userName, Input.sa1password, Input.rev1userName, Input.rev1password },
				{ "rmu", Input.da1userName, Input.da1password, Input.rmu1userName, Input.rmu1password },
				{ "rev", Input.da1userName, Input.da1password, Input.rev1userName, Input.rev1password },
				{ "rmu", Input.pa1userName, Input.pa1password, Input.rmu1userName, Input.rmu1password },
				{ "rev", Input.pa1userName, Input.pa1password, Input.rev1userName, Input.rev1password }, };
	}

	@Test(description ="RPMXCON-53322",alwaysRun = true, dataProvider = "impersonateRmu", groups = { "regression" } )
	public void validatingRmuFunctionTab(String roll, String loginuser, String loginPass, String assignUser,
			String assignPass) throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-53322");
		baseClass.stepInfo("Validate impersonated RMU has access to Functionality tab and able to modify permissions");
		userManage = new UserManagement(driver);

		// login
		loginPage.loginToSightLine(loginuser, loginPass);
		baseClass.impersonateSAtoRMU();
		baseClass.selectproject(Input.projectName);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		List<String> rmuRevCount = userManage.getTableCoumnValue("Role");
		if (rmuRevCount.contains("Project Administrator")) {
			baseClass.failedStep("Project admin user also displayed after impersonate to rmu");
		} else {
			baseClass.passedStep("Rmu and Rev user only displayed after impersonate to rmu");

		}
		userManage.passingUserName(assignUser);
		userManage.applyFilter();
		userManage.editLoginUser();
		if (roll == "rmu") {
			userManage.getFunctionalityTab().waitAndClick(5);
			userManage.verifyStatusCategorize("false");
			baseClass.stepInfo("Categorize checkbox unselected for " + roll + " user");

			// verifying access permission r not for unselected one
			// logout
			loginPage.logout();
			loginPage.loginToSightLine(assignUser, assignPass);
			baseClass.selectproject(Input.projectName);

			// validating Categorize icon
			userManage.verifyCategorizeIcon(false, false);
			baseClass.passedStep("After impersoante for rmu user unselected checkbox is not displaying in left menu");

			// logout
			loginPage.logout();
		}
		if (roll == "rev") {
			userManage.getFunctionalityTab().waitAndClick(5);
			userManage.verifyStatusSearch("true");
			baseClass.stepInfo("Search checkbox selected for " + roll + " user");

			// verifying access permission r not for unselected one
			// logout
			loginPage.logout();
			loginPage.loginToSightLine(assignUser, assignPass);
			baseClass.selectproject(Input.projectName);

			// validating Categorize icon
			userManage.verifySearchIcon(true, true, roll);
			baseClass.passedStep("After impersoante for rev user selected checkbox is displaying in left menu");

			// logout
			loginPage.logout();
		}

	}

	/**
	 * Author : Baskar date: NA Modified date:22/04/2022 Modified by: Baskar
	 * Description :Validate RMU has access to Functionality tab and able to modify
	 * permissions
	 */

	@DataProvider(name = "rmuRev")
	public Object[][] rmuRev() {
		return new Object[][] {
				{ "rmu", Input.rmu1userName, Input.rmu1password, Input.rmu1userName, Input.rmu1password },
				{ "rev", Input.rmu1userName, Input.rmu1password, Input.rev1userName, Input.rev1password }, };
	}

	@Test(description ="RPMXCON-53321",alwaysRun = true, dataProvider = "rmuRev", groups = { "regression" } )
	public void validatingRmuFunctionToModify(String roll, String loginuser, String loginPass, String assignUser,
			String assignPass) throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-53321");
		baseClass.stepInfo("Validate RMU has access to Functionality tab and able to modify permissions");
		userManage = new UserManagement(driver);

		// login
		loginPage.loginToSightLine(loginuser, loginPass);
		baseClass.selectproject(Input.projectName);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		userManage.passingUserName(assignUser);
		userManage.applyFilter();
		userManage.editLoginUser();
		if (roll == "rmu") {
			userManage.getFunctionalityTab().waitAndClick(5);
			userManage.verifyStatusCategorize("false");
			baseClass.stepInfo("Categorize checkbox unselected for " + roll + " user");

			// verifying access permission r not for unselected one
			// logout
			loginPage.logout();
			loginPage.loginToSightLine(assignUser, assignPass);
			baseClass.selectproject(Input.projectName);

			// validating Categorize icon
			userManage.verifyCategorizeIcon(false, false);
			baseClass.stepInfo("Unselected checkbox is not displaying in left menu");
			baseClass.passedStep("Rmu user can modify permissions for reviewer manager user ");

			// logout
			loginPage.logout();
		}
		if (roll == "rev") {
			userManage.getFunctionalityTab().waitAndClick(5);
			userManage.verifyStatusSearch("true");
			baseClass.stepInfo("Search checkbox selected for " + roll + " user");

			// verifying access permission r not for unselected one
			// logout
			loginPage.logout();
			loginPage.loginToSightLine(assignUser, assignPass);
			baseClass.selectproject(Input.projectName);

			// validating Categorize icon
			userManage.verifySearchIcon(true, true, roll);
			baseClass.stepInfo("selected Search icon is displaying in left menu");
			baseClass.passedStep("Rmu user can modify permissions for reviewer user");

			// logout
			loginPage.logout();
		}

	}

	/**
	 * Author : Baskar date: NA Modified date:22/04/2022 Modified by: Baskar
	 * Description :Verify if SAU impersonate as Reviewer, he should able to
	 * impersonate back as PAU
	 */

	@Test(description ="RPMXCON-53288",alwaysRun = true, groups = { "regression" } )
	public void validatingSAImpRevBackToPau() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-53288");
		baseClass.stepInfo("Verify if SAU impersonate as Reviewer, he should able to impersonate back as PAU");
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();

		// login
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);

		// sa impersonating to reviewer
		baseClass.impersonateSAtoReviewer();

		// validating reviewer dashboard page
		Boolean revDashboard = userManage.getDashBoardReviewer().isElementAvailable(2);
		softAssertion.assertTrue(revDashboard);
		baseClass.passedStep("Successfully impersonated to SA to Reviewer user");

		// Reviewer impersonating to Pa user
		baseClass.impersonateSAtoPA();

		// validating Pa home page
		baseClass.waitForElement(loginPage.getSignoutMenu());
		loginPage.getSignoutMenu().waitAndClick(10);
		Boolean paHomePage = userManage.getPaHomePage().isElementAvailable(2);
		softAssertion.assertTrue(paHomePage);
		baseClass.passedStep("Successfully impersonated to Reviewer to project admin user");

		softAssertion.assertAll();

		// logout
		loginPage.logout();

	}

	/**
	 * Author : Baskar date: NA Modified date:22/04/2022 Modified by: Baskar
	 * Description :Verify for the "Filter by User Name" field from Manage Users
	 * page
	 */

	@Test(description ="RPMXCON-53175",alwaysRun = true, groups = { "regression" } )
	public void validatingUserNameMyEmail() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-53175");
		baseClass.stepInfo("Verify for the Filter by User Name field from Manage Users page");
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();
		String fullName = Input.pa1FullName;
		String[] splittingFullName = fullName.split(" ");
		String firstName = splittingFullName[0];

		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");

		// applying filter by using email
		userManage.passingUserName(Input.pa1userName);
		userManage.applyFilter();
		baseClass.stepInfo("Apply Filter Button in clicked successfully");

		driver.waitForPageToBeReady();
		String AfterfilterUserName = userManage.getFirstNameTab().getText().trim();
		System.out.println(AfterfilterUserName);

		softAssertion.assertEquals(firstName, AfterfilterUserName);
		baseClass.stepInfo("Filter action done by email address");
		baseClass.passedStep("After email filter done firstname displayed successfully as expecteds");
		softAssertion.assertAll();

		// logout
		loginPage.logout();
	}
	
	/**
	 * Author : Baskar date: NA Modified date:25/04/2022 Modified by: Baskar
	 * Description :To verify for Project Admin when 'Redactions' is
	 * Checekd/Unchecked from Bulk User Access Control
	 */

	@DataProvider(name = "PaUser")
	public Object[][] PaUser() {
		return new Object[][] { { "pa", Input.pa1userName, Input.pa1password, Input.pa1userName, Input.pa1password,
				"Project Administrator", "pa", Input.pa1FullName }, };
	}

    @Test(description ="RPMXCON-52702",alwaysRun = true, dataProvider = "PaUser", groups = { "regression" } )
	public void validatingBulkUserForPa(String roll, String loginuser, String loginPass, String rollUser,
			String rollPass, String rollId, String assignRole, String fullName) throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52702");
		baseClass.stepInfo("To verify for Project Admin when 'Redactions' is Checekd/Unchecked "
				+ "from Bulk User Access Control");
		userManage = new UserManagement(driver);
		softAssertion=new SoftAssert();
		sessionSearch=new SessionSearch(driver);

		// login
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		driver.waitForPageToBeReady();
		userManage.getBulkUserAccessTab().waitAndClick(5);
		userManage.getSelectRollId().selectFromDropdown().selectByVisibleText(rollId);
		if (roll == "pa") {
			userManage.defaultSelectionCheckboxForAllRole(true, false, true, true, true, true, true, true, true, true,
					true, false, true, true, true);
		}
		if (assignRole == "pa") {
			driver.scrollingToElementofAPage(userManage.getDisableRadioBtn());
			userManage.getDisableRadioBtn().javascriptclick(userManage.getDisableRadioBtn());
		}
		Actions Act = new Actions(driver.getWebDriver());
		Act.clickAndHold(userManage.getPopupWindowHeader().getWebElement());
		Act.moveToElement(userManage.getPopupWindowHeader().getWebElement(), -10, 10);
		Act.release().build().perform();
		userManage.getSelectBulkUser(fullName).javascriptclick(userManage.getSelectBulkUser(fullName));
		userManage.getBulkUserSaveBtn().javascriptclick(userManage.getBulkUserSaveBtn());
		baseClass.VerifySuccessMessage("Access rights applied successfully");
		baseClass.CloseSuccessMsgpopup();
		userManage.getBulkUserCancelBtn().waitAndClick(5);
		baseClass.stepInfo("Redaction tag is disabled");
        
		// impersonate to pa user
		baseClass.impersonatePAtoRMUAfterbulk();

		// Audio Search
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Launch DocVia via Search
		sessionSearch.ViewInDocViews();
		baseClass.passedStep("launched DocVIew via Search");
		
		// validating redaction tab
		baseClass.waitForElement(userManage.getRedaction());
		boolean redactionNotpresent=userManage.getRedaction().isDisplayed();
		softAssertion.assertFalse(redactionNotpresent);
		baseClass.passedStep("Redaction tag not displayed in docview page");

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
					true, false, true, true, true);
		}
		if (assignRole == "pa") {
			driver.scrollingToElementofAPage(userManage.getEnableRadioBtn());
			userManage.getEnableRadioBtn().javascriptclick(userManage.getEnableRadioBtn());
		}
		Act.clickAndHold(userManage.getPopupWindowHeader().getWebElement());
		Act.moveToElement(userManage.getPopupWindowHeader().getWebElement(), -10, 10);
		Act.release().build().perform();
		userManage.getSelectBulkUser(fullName).javascriptclick(userManage.getSelectBulkUser(fullName));
		userManage.getBulkUserSaveBtn().javascriptclick(userManage.getBulkUserSaveBtn());
		baseClass.VerifySuccessMessage("Access rights applied successfully");
		baseClass.CloseSuccessMsgpopup();
		userManage.getBulkUserCancelBtn().waitAndClick(5);
		baseClass.stepInfo("Redaction tag is Enabled");

		// impersonate to pa user
		baseClass.impersonatePAtoRMUAfterbulk();

		// Audio Search
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Launch DocVia via Search
		sessionSearch.ViewInDocViews();
		baseClass.passedStep("launched DocVIew via Search");

		// validating redaction tab
		baseClass.waitForElement(userManage.getRedaction());
		boolean redactiontpresent = userManage.getRedaction().isDisplayed();
		softAssertion.assertTrue(redactiontpresent);
		baseClass.passedStep("Redaction tag  displayed in docview page");

		softAssertion.assertAll();
		// logout
		loginPage.logout();

	}
     
     /**
 	 * Author : Baskar date: NA Modified date:25/04/2022 Modified by: Baskar
 	 * Description :To verify landing page for User when 'All Repors' is Checekd/Unchecked 
 	 *              from Edit User > functionality tab
 	 */

 	@Test(description ="RPMXCON-52698",alwaysRun = true, dataProvider = "fourRole", groups = { "regression" } )
 	public void validatingAllReportIcon(String roll, String userName, String password, String userNameTwo,
 			String passWordTwo) throws Exception {
 		baseClass.stepInfo("Test case Id: RPMXCON-52698");
 		baseClass.stepInfo("To verify landing page for User when 'All Repors' is Checekd/Unchecked "
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

 		// AllReport uncheckbox
 		userManage.getFunctionalityTab().waitAndClick(5);
 		userManage.verifyCheckboxStatusBasedOnCondition(userManage.getAllReportStatusCheck(),userManage.getAllReportIconCheck(),
 				"All Report","false");

 		// logout
 		loginPage.logout();

 		// login
 		loginPage.loginToSightLine(userNameTwo, passWordTwo);

 		// validating AllReport icon
 		userManage.verifyLeftIcon(userManage.getAllReportTab(),"All report",false,false);

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

 		// AllReport checkbox applying
 		userManage.getFunctionalityTab().waitAndClick(5);
 		userManage.verifyCheckboxStatusBasedOnCondition(userManage.getAllReportStatusCheck(),userManage.getAllReportIconCheck(),
 				"All Report","true");

 		// logout
 		loginPage.logout();

 		// login
 		loginPage.loginToSightLine(userNameTwo, passWordTwo);

 		// validating AllReport icon
 		userManage.verifyLeftIcon(userManage.getAllReportTab(),"All report",true,true);

 		// logout
 		loginPage.logout();

 	}
 	/**
 	 * Author : Aathith date: NA Modified date: /04/2022 Modified by: 
 	 * Description :To verify when Sys Admin edits the user rights of user assigned to more than one domain/non-domain project with RMU role
 	 */
 	
 	@Test(description ="RPMXCON-52480",alwaysRun = true, groups = { "regression" } )
 	public void verifySysAdminEditRoleOfRmu() throws Exception {
 		baseClass.stepInfo("Test case Id: RPMXCON-52480");
 		baseClass.stepInfo("To verify when Sys Admin edits the user rights of user assigned to more than one domain/non-domain project with RMU role");
 		userManage = new UserManagement(driver);
 		// login
 		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
 		Reporter.log("Logged in as User: " + Input.sa1userName);

 		userManage.passingUserName(Input.rmu1userName);
 		userManage.applyFilter();
 		userManage.verifyUserHasMoreThanOneProject();

 		userManage.editFunctionality(Input.projectName);
 		userManage.getFunctionalityTab().waitAndClick(5);
 		userManage.verifyCheckboxStatusBasedOnCondition(userManage.getDataSetStatus(),userManage.getDataSet(),"dataSet","true");
 		baseClass.CloseSuccessMsgpopup();

 		userManage.editFunctionality(Input.projectName);
 		userManage.getFunctionalityTab().waitAndClick(5);
 		if(userManage.getDataSetStatus().isElementAvailable(3)) {
 		baseClass.passedStep("user rights is saved in database for the respective project.");
 		}else {
 		baseClass.failedStep("not saved in database for the respective project");
 		}
 		userManage.getCancel().waitAndClick(10);

 		userManage.editFunctionality(Input.additionalDataProject);
 		userManage.getFunctionalityTab().waitAndClick(5);
 		if(!userManage.getDataSetStatus().isElementAvailable(1)) {
 		baseClass.passedStep("User rights saved for the user for Project1 is not be overwrite for Project2.");
 		}else {
 		baseClass.failedStep("verification failed");
 		}
 		userManage.getCancel().waitAndClick(10);
 		baseClass.passedStep("verified when Sys Admin edits the user rights of user assigned to more than one domain/non-domain project with RMU role");

 		userManage.editFunctionality(Input.projectName);
 		userManage.getFunctionalityTab().waitAndClick(5);
 		userManage.verifyCheckboxStatusBasedOnCondition(userManage.getDataSetStatus(),userManage.getDataSet(),"dataSet","false");

 		loginPage.logout();
 	}
 	

	/**
	 * Author : Baskar date: NA Modified date:26/04/2022 Modified by: Baskar
	 * Description :To verify that Bulk User Access Control button is not displayed
	 * on Manage User when logged in as an RMU
	 */

	@Test(description ="RPMXCON-52712",alwaysRun = true, groups = { "regression" } )
	public void validatingBulkUserTabPresence() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52712");
		baseClass.stepInfo("To verify that Bulk User Access Control button is not "
				+ "displayed on Manage User when logged in as an RMU");
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();

		// Login As rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");

		// validating bulk user tab presence
		driver.waitForPageToBeReady();
		Boolean bulkFlagfalse = userManage.getBulkUserAccessTab().isElementAvailable(2);
		softAssertion.assertFalse(bulkFlagfalse);
		softAssertion.assertAll();
		baseClass.passedStep("Bulk user tab icon not available for rmu user");

		// logout
		loginPage.logout();
	}

	/**
	 * Author : Baskar date: NA Modified date:26/04/2022 Modified by: Baskar
	 * Description :To verify when 'Analytics Panels' is Checekd/Unchecked from Bulk
	 * User Access Control
	 */

	@DataProvider(name = "analyticalPanel")
	public Object[][] analyticalPanel() {
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

	@Test(description ="RPMXCON-52707",alwaysRun = true, dataProvider = "analyticalPanel", groups = { "regression" } )
	public void validatingAnalyticalPanel(String roll, String loginuser, String loginPass, String rollId,
			String rollUser, String rollPass, String assignRole, String firstName) throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52707");
		baseClass.stepInfo("To verify when 'Analytics Panels' is Checekd/Unchecked from Bulk User Access Control");
		userManage = new UserManagement(driver);
		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);
		
		// login
		loginPage.loginToSightLine(loginuser, loginPass);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		driver.waitForPageToBeReady();
		userManage.getBulkUserAccessTab().waitAndClick(5);
		userManage.getSelectRollId().selectFromDropdown().selectByVisibleText(rollId);
		if (assignRole == "sa" || assignRole == "da") {
			userManage.getSelectingProject().waitAndClick(5);
			userManage.getSelectDropProject(Input.projectName).waitAndClick(10);
			baseClass.waitTime(2);
		}
		
		if (roll == "pa") {
			userManage.defaultSelectionCheckboxForAllRole(true, false, true, true, true, true, true, true, true, true,
					true, true, true, true, false);
		}
		if (roll == "rmu") {
			userManage.defaultSelectionCheckboxForAllRole(true, false, true, true, true, true, true, false, false, true,
					true, true, true, true, false);
		}
		if (roll == "rev") {
			userManage.defaultSelectionCheckboxForAllRole(false, false, false, true, false, false, false, false, false,
					false, true, true, true, true, false);
		}
		baseClass.stepInfo("Disable the radio btn for Analytical panel checkbox");
		if (assignRole == "pa") {
			driver.scrollingToElementofAPage(userManage.getDisableRadioBtn());
			userManage.getDisableRadioBtn().javascriptclick(userManage.getDisableRadioBtn());
			if (roll == "rmu" || roll == "rev") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		if (assignRole == "sa" || assignRole == "da") {
			driver.scrollingToElementofAPage(userManage.getDisableRadioBtn());
			userManage.getDisableRadioBtn().javascriptclick(userManage.getDisableRadioBtn());
			
			if (roll == "rmu" || roll == "rev") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		
		Thread.sleep(2000);
		
		Actions Act = new Actions(driver.getWebDriver());
		Act.clickAndHold(userManage.getPopupWindowHeader().getWebElement());
		Act.moveToElement(userManage.getPopupWindowHeader().getWebElement(), -10, 10);
		Act.release().build().perform();
		userManage.getSelectBulkUser(firstName).javascriptclick(userManage.getSelectBulkUser(firstName));
		userManage.getBulkUserSaveBtn().javascriptclick(userManage.getBulkUserSaveBtn());

		baseClass.VerifySuccessMessage("Access rights applied successfully");
		
		// logout
		loginPage.logout();
		// login as userassigned for validation
		loginPage.loginToSightLine(rollUser, rollPass);
		// session search to docview
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		// validating Analytical panel
		docViewPage.verifyElementNameBasedOnParameterDocView(false, false, "Analytical panel",
				docViewPage.getDocView_ChildWindow_ActionButton());
		// logout
		loginPage.logout();
		// login
		loginPage.loginToSightLine(loginuser, loginPass);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		driver.waitForPageToBeReady();
		userManage.getBulkUserAccessTab().waitAndClick(5);
		userManage.getSelectRollId().selectFromDropdown().selectByVisibleText(rollId);
		if (assignRole == "sa" || assignRole == "da") {
			userManage.getSelectingProject().waitAndClick(5);
			userManage.getSelectDropProject(Input.projectName).waitAndClick(10);
			baseClass.waitTime(2);
		}
		if (roll == "pa") {
			userManage.defaultSelectionCheckboxForAllRole(true, false, true, true, true, true, true, true, true, true,
					true, true, true, true, false);
		}
		if (roll == "rmu") {
			userManage.defaultSelectionCheckboxForAllRole(true, false, true, true, true, true, true, false, false, true,
					true, true, true, true, false);
		}
		if (roll == "rev") {
			userManage.defaultSelectionCheckboxForAllRole(false, false, false, true, false, false, false, false, false,
					false, true, true, true, true, false);
		}
		baseClass.stepInfo("Enable the radio btn for Analytical panel checkbox");
		if (assignRole == "pa") {
			driver.scrollingToElementofAPage(userManage.getEnableRadioBtn());
			userManage.getEnableRadioBtn().javascriptclick(userManage.getEnableRadioBtn());
			
			if (roll == "rmu" || roll == "rev") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		if (assignRole == "sa" || assignRole == "da") {
			driver.scrollingToElementofAPage(userManage.getEnableRadioBtn());
			userManage.getEnableRadioBtn().javascriptclick(userManage.getEnableRadioBtn());
			if (roll == "rmu" || roll == "rev") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		
		
		Act.clickAndHold(userManage.getPopupWindowHeader().getWebElement());
		Act.moveToElement(userManage.getPopupWindowHeader().getWebElement(), -10, 10);
		Act.release().build().perform();
		userManage.getSelectBulkUser(firstName).javascriptclick(userManage.getSelectBulkUser(firstName));
		userManage.getBulkUserSaveBtn().javascriptclick(userManage.getBulkUserSaveBtn());

		baseClass.VerifySuccessMessage("Access rights applied successfully");
		
		
		// logout
		loginPage.logout();
		// login as userassigned for validation
		loginPage.loginToSightLine(rollUser, rollPass);
		// session search to docview
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		// validating Analytical panel
		docViewPage.verifyElementNameBasedOnParameterDocView(true, true, "Analytical panel",
				docViewPage.getDocView_ChildWindow_ActionButton());

		// logout
		loginPage.logout();

	}

	/**
	 * Author : Baskar date: NA Modified date:26/04/2022 Modified by: Baskar
	 * Description :Verify security group from edit user pop up when user changes
	 * security group from header
	 */

	@Test(description ="RPMXCON-52679",alwaysRun = true, groups = { "regression" } )
	public void validatingRmuCanRemoveSG() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52679");
		baseClass.stepInfo(
				"Verify security group from edit user pop up when user " + "changes security group from header");
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();
		security = new SecurityGroupsPage(driver);
		String securityGroup = "sGName" + Utility.dynamicNameAppender();

		// Login As pa
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		// creating new sg
		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		driver.waitForPageToBeReady();
		security.AddSecurityGroup(securityGroup);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		driver.waitForPageToBeReady();
		userManage.passingUserName(Input.rmu1userName);
		userManage.applyFilter();
		userManage.editLoginUser();
		userManage.addingSGToUser(Input.securityGroup, securityGroup);

		// logout
		loginPage.logout();

		// Login As rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.selectsecuritygroup(securityGroup);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		driver.waitForPageToBeReady();
		userManage.passingUserName(Input.rmu1userName);
		userManage.applyFilter();
		userManage.editLoginUser();
		baseClass.waitTime(5);
		Select selectSG = new Select(userManage.userSelectSecurityGroup().getWebElement());
		int count = selectSG.getOptions().size();
		List<String> allSG = new ArrayList<String>();
		for (int j = 0; j < selectSG.getOptions().size(); j++) {
			if (count <= 1) {
				baseClass.passedStep("When user change the Sg from header changed sg displaying in edit user detailss");
			}
			allSG.add(selectSG.getOptions().get(j).getText());
		}
		baseClass.waitForElement(userManage.getEditCancel());
		userManage.getEditCancel().waitAndClick(5);
		baseClass.selectsecuritygroup(Input.securityGroup);
		softAssertion.assertEquals(securityGroup, String.join(" ", allSG));
		softAssertion.assertAll();

		// logout
		loginPage.logout();
	}

	/**
	 * Author : Baskar date: NA Modified date:26/04/2022 Modified by: Baskar
	 * Description :Verify after removing the security group user assigned security
	 * group should be updated
	 */

	@Test(description ="RPMXCON-52675",alwaysRun = true, groups = { "regression" } )
	public void validatingAfterdeletingSGFromSA() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52675");
		baseClass.stepInfo(
				"Verify after removing the security group user " + "assigned security group should be updated");
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();
		security = new SecurityGroupsPage(driver);
		String securityGroup = "sGName" + Utility.dynamicNameAppender();

		// Login As pa
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		// creating new sg
		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		driver.waitForPageToBeReady();
		security.AddSecurityGroup(securityGroup);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		userManage.passingUserName(Input.rmu1userName);
		userManage.applyFilter();
		userManage.editLoginUser();
		userManage.addingSGToUser(Input.securityGroup, securityGroup);

		// Deleting the security group
		security.deleteSecurityGroups(securityGroup);
		baseClass.stepInfo("Deleting the security group which created new");

		// logout
		loginPage.logout();

		// Login As sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		baseClass.waitForElement(userManage.getAssignUserButton());
		userManage.getAssignUserButton().waitAndClick(5);
		baseClass.waitForElement(userManage.getProjectTab());
		userManage.getProjectTab().waitAndClick(5);
		baseClass.waitForElement(userManage.getAssignUserProjectDrp_Dwn());
		userManage.getAssignUserProjectDrp_Dwn().waitAndClick(5);
		baseClass.waitForElement(userManage.getSelectDropProject(Input.projectName));
		userManage.getSelectDropProject(Input.projectName).waitAndClick(5);
		baseClass.waitForElement(userManage.getCheckingAssignedUserSG(Input.rmu1FullName));
		String assignedUserStatus=userManage.getCheckingAssignedUserSG(Input.rmu1FullName).getText();
		if (assignedUserStatus.contains(securityGroup)) {
			baseClass.failedStep("Deleted Sg displaying in assigned user window");
		}
		else {
			baseClass.passedStep("Deleted Sg not displaying in assigned user from SA");
		}
		// logout
		loginPage.logout();
	}
	
	/**
	 * Author : Baskar date: NA Modified date:26/04/2022 Modified by: Baskar
	 * Description :To verify for Project Admin when 'Reviewer Remarks' is Checekd/Unchecked 
	 *              from Bulk User Access Control
	 */


	@Test(description ="RPMXCON-52706",alwaysRun = true, dataProvider = "PaUser", groups = { "regression" })
	public void validatingBulkUserReviewerRemarksForPa(String roll, String loginuser, String loginPass, String rollUser,
			String rollPass, String rollId, String assignRole, String fullName) throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52706");
		baseClass.stepInfo("To verify for Project Admin when 'Reviewer Remarks' is Checekd/Unchecked "
				+ "from Bulk User Access Control");
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();
		sessionSearch = new SessionSearch(driver);

		// login
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		driver.waitForPageToBeReady();
		userManage.getBulkUserAccessTab().waitAndClick(5);
		userManage.getSelectRollId().selectFromDropdown().selectByVisibleText(rollId);
		if (roll == "pa") {
			userManage.defaultSelectionCheckboxForAllRole(true, false, true, true, true, true, true, true, true, true,
					true, true, true, false, true);
		}
		if (assignRole == "pa") {
			driver.scrollingToElementofAPage(userManage.getDisableRadioBtn());
			userManage.getDisableRadioBtn().javascriptclick(userManage.getDisableRadioBtn());
		}
		Actions Act = new Actions(driver.getWebDriver());
		Act.clickAndHold(userManage.getPopupWindowHeader().getWebElement());
		Act.moveToElement(userManage.getPopupWindowHeader().getWebElement(), -10, 10);
		Act.release().build().perform();
		userManage.getSelectBulkUser(fullName).javascriptclick(userManage.getSelectBulkUser(fullName));
		userManage.getBulkUserSaveBtn().javascriptclick(userManage.getBulkUserSaveBtn());
		baseClass.VerifySuccessMessage("Access rights applied successfully");
		baseClass.CloseSuccessMsgpopup();
		userManage.getBulkUserCancelBtn().waitAndClick(5);
		baseClass.stepInfo("Reviewer Remarks tag is disabled");

		// impersonate to pa user
		baseClass.impersonatePAtoRMUAfterbulk();

		// Audio Search
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Launch DocVia via Search
		sessionSearch.ViewInDocViews();
		baseClass.passedStep("launched DocVIew via Search");

		// validating redaction tab
		boolean remarksNotpresent = userManage.getAdvancedSearchAudioRemarkIcon().isElementAvailable(2);
		softAssertion.assertFalse(remarksNotpresent);
		baseClass.passedStep("Reviewer Remarks tag not displayed in docview page");

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
					true, true, true, false, true);
		}
		if (assignRole == "pa") {
			driver.scrollingToElementofAPage(userManage.getEnableRadioBtn());
			userManage.getEnableRadioBtn().javascriptclick(userManage.getEnableRadioBtn());
		}
		Act.clickAndHold(userManage.getPopupWindowHeader().getWebElement());
		Act.moveToElement(userManage.getPopupWindowHeader().getWebElement(), -10, 10);
		Act.release().build().perform();
		userManage.getSelectBulkUser(fullName).javascriptclick(userManage.getSelectBulkUser(fullName));
		userManage.getBulkUserSaveBtn().javascriptclick(userManage.getBulkUserSaveBtn());
		baseClass.VerifySuccessMessage("Access rights applied successfully");
		baseClass.CloseSuccessMsgpopup();
		userManage.getBulkUserCancelBtn().waitAndClick(5);
		baseClass.stepInfo("Reviewer Remarks tag is Enabled");

		// impersonate to pa user
		baseClass.impersonatePAtoRMUAfterbulk();

		// Audio Search
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);

		// Launch DocVia via Search
		sessionSearch.ViewInDocViews();
		baseClass.passedStep("launched DocVIew via Search");

		// validating redaction tab
		baseClass.waitForElement(userManage.getAdvancedSearchAudioRemarkIcon());
		boolean remarkspresent = userManage.getAdvancedSearchAudioRemarkIcon().isElementAvailable(2);
		softAssertion.assertTrue(remarkspresent);
		baseClass.passedStep("Reviewer Remarks tag  displayed in docview page");

		softAssertion.assertAll();
		// logout
		loginPage.logout();

	}


	@DataProvider(name = "Users")
	public Object[][] Users() {
		return new Object[][] {
				{  Input.rmu1userName, Input.rmu1password},
				{  Input.pa1userName, Input.pa1password},
				{Input.da1userName, Input.da1password },
				{Input.sa1userName, Input.sa1password}};
	}
	/**
	 * Author : Brundha date: NA Modified date:
	 * Description :Verify when user enters value in more than 3 fields 1) Filter By
	 * User Name 2) Filter By Role 3) Created on or After, search should return
	 * results which are an INTERSECTION of all the entered filter criteria
	 */
	@Test(description ="RPMXCON-53193",alwaysRun = true, dataProvider = "Users", groups = { "regression" })
	public void verifyingFilterTabInManageUser(String username,String Password) throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-53193");
		baseClass.stepInfo(
				"Verify when user enters value in more than 3 fields 1) Filter By User Name 2) Filter By Role 3) Created on or After, search should return results which are an INTERSECTION of all the entered filter criteria ");
						
		userManage = new UserManagement(driver);
		loginPage.loginToSightLine(username,Password);
		if(username.equalsIgnoreCase(Input.da1userName)) {
			baseClass.selectdomain(Input.domainName);
		}
		userManage.navigateToUsersPAge();
//		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		userManage.passingUserName(Input.rmu1userName);
		userManage.applyFilter();
		driver.waitForPageToBeReady();
		String firstName = userManage.getTableData("FIRST NAME", 1);
		System.out.println(firstName);
		driver.waitForPageToBeReady();
		userManage.verifyingFilterOptionInManageUser(Input.pageCount);
		
		baseClass.stepInfo("Validating user role in applied filter");
		userManage.validateFilterOptionInUserManage("ROLE",Input.userRole);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Validating username in applied filter");
		userManage.validateFilterOptionInUserManage("FIRST NAME",firstName);
		loginPage.logout();

	}
	
	/**
	 * Author : Baskar date: NA Modified date:27/04/2022 Modified by: Baskar
	 * Description :Verify that option to check/uncheck the option will be available
	 * only if the non-billable user is creating/editing a user
	 */

	@Test(description ="RPMXCON-53204",alwaysRun = true, groups = { "regression" } )
	public void validatingBilliableCheckBox() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-53204");
		baseClass.stepInfo("Verify that option to check/uncheck the option will be available "
				+ "only if the non-billable user is creating/editing a user");
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();

		// Login As rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");

		// validating billable user checkbox available from non-billable user
		driver.waitForPageToBeReady();
		baseClass.waitForElement(userManage.getAddUserBtn());
		userManage.getAddUserBtn().waitAndClick(5);
		baseClass.waitForElement(userManage.getAddNewUserPopUpWindow());
		boolean userWindow = userManage.getAddNewUserPopUpWindow().isElementAvailable(2);
		System.out.println(userWindow);
		softAssertion.assertTrue(userWindow);
		baseClass.stepInfo("Add user popup window opened successfully");
		baseClass.waitForElement(userManage.getBilliableUserText());
		String billiableName = userManage.getBilliableUserText().getText();
		System.out.println(billiableName);
		softAssertion.assertEquals("Billable User:", billiableName);
		baseClass.waitForElement(userManage.getBilliableUserCheckBox());
		boolean billiableCheckBox = userManage.getBilliableUserCheckBox().isElementAvailable(2);
		System.out.println(billiableCheckBox);
		softAssertion.assertTrue(billiableCheckBox);
		baseClass.passedStep("Billiable user checkbox available in add user popup window");

		softAssertion.assertAll();

		// logout
		loginPage.logout();
	}

	/**
	 * Author : Baskar date: NA Modified date:27/04/2022 Modified by: Baskar
	 * Description :Verify functionalities by PAU post impersonate as a RMU
	 */

	@Test(description ="RPMXCON-53166",alwaysRun = true, groups = { "regression" } )
	public void validatingAssignmentPaToRmuImp() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-53166");
		baseClass.stepInfo("Verify functionalities by PAU post impersonate as a RMU");
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();
		agnmt = new AssignmentsPage(driver);
		sessionSearch=new SessionSearch(driver);
		miniDocListPage=new MiniDocListPage(driver);
		docViewPage=new DocViewPage(driver);
		String assignmentName = "AssName" + Utility.dynamicNameAppender();
		String remarks = "remark" + Utility.dynamicNameAppender();
		String comment = "comment" + Utility.dynamicNameAppender();
		String headerName = "RedactionTags";
		Map<String, String> datas = new HashMap<String, String>();
		int iteration = 1;
		int index;

		// Login As rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		sessionSearch.audioSearch(Input.audioSearchString1, Input.audioLanguage);
		sessionSearch.bulkAssign();
		agnmt.assignmentCreation(assignmentName, Input.codingFormName);
		agnmt.toggleSaveButton();
		agnmt.assignmentDistributeToPa();

		// logout
		loginPage.logout();

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		// impersonate pa to rmu
		baseClass.impersonatePAtoRMU();
		miniDocListPage.chooseAnAssignmentFromDashBoard(assignmentName);
		
		// adding remarks
		datas = docViewPage.addRemarkToDocumentsT(iteration, remarks, true, "Success");
		baseClass.stepInfo("Remarks added successfully when Pa Impersonate To Rmu");
		
		// adding Redaction
		// Validate audio docs eye icon with persistent hits
		docViewPage.audioReduction(Input.defaultRedactionTag);

		index = baseClass.getIndex(docViewPage.getAudioRedactionTableHeader(), headerName);

		// AfterSave Default Selection
		String defautTagSelection = docViewPage.getAudioRedactionColumnValue(index).getText();
		baseClass.textCompareEquals(defautTagSelection, Input.defaultRedactionTag,
				"After Save : â€˜Default Redaction Tagâ€™ is displayed", "After Save : invalid redaction tag selected");
        baseClass.passedStep("Redaction added successfully when Pa Impersonate To Rmu");
        
		// Audio Redaction Tag deletion
		docViewPage.deleteAudioRedactionTag();
		driver.scrollPageToTop();
		String prnDoc=docViewPage.getVerifyPrincipalDocument().getText();
		
		// validating save button
		docViewPage.editCodingForm(comment);
		docViewPage.codingFormSaveButton();
		docViewPage.verifyingComments(comment);
		baseClass.passedStep("Document saved with some loaded value using save button when Pa Impersonate To Rmu ");
		
		// validating uncomplete button
        docViewPage.completeButton();
        docViewPage.getDociD(prnDoc).waitAndClick(5);
        boolean uncompleteBtn=docViewPage.getUnCompleteButton().isElementAvailable(2);
        softAssertion.assertTrue(uncompleteBtn);
		baseClass.passedStep("Document completed using complete button and tick mark displaying when Pa Impersonate To Rmu ");
		for (int i = 2; i <=3; i++) {
			docViewPage.getDocView_MiniDoc_ChildWindow_Selectdoc(i).waitAndClick(5);
		}
		docViewPage.clickCodeSameAs();
		baseClass.passedStep("Code same as action  performed when Pa Impersonate To Rmu ");
		softAssertion.assertAll();

		// logout
		loginPage.logout();
	}
	
	/**
	 * Author : Baskar date: NA Modified date:06/05/2022 Modified by: Baskar
	 * Description :To verify for user when 'Download Native' is Checekd/Unchecked from Bulk User Access Control
	 */

	@DataProvider(name = "bulkDw")
	public Object[][] bulkDw() {
		return new Object[][] {
				{ "rmu", Input.pa1userName, Input.pa1password, "Review Manager", Input.rmu1userName, Input.rmu1password,
						"pa", Input.rmu1FullName },
				{ "rev", Input.pa1userName, Input.pa1password, "Reviewer", Input.rev1userName, Input.rev1password, "pa",
						Input.rev1FullName, },
				{ "rmu", Input.sa1userName, Input.sa1password, "Review Manager", Input.rmu1userName, Input.rmu1password,
						"sa", Input.rmu1FullName },
				{ "rev", Input.sa1userName, Input.sa1password, "Reviewer", Input.rev1userName, Input.rev1password, "sa",
						Input.rev1FullName },
				{ "rmu", Input.da1userName, Input.da1password, "Review Manager", Input.rmu1userName, Input.rmu1password,
						"da", Input.rmu1FullName },
				{ "rev", Input.da1userName, Input.da1password, "Reviewer", Input.rev1userName, Input.rev1password, "da",
						Input.rev1FullName }, 
				};
	}

	@Test(description ="RPMXCON-52703",alwaysRun = true, dataProvider = "bulkDw", groups = { "regression" } )
	public void bulkAssignDownload(String roll, String loginuser, String loginPass, String rollId,


			String rollUser, String rollPass, String assignRole, String firstName) throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52703");
		baseClass.stepInfo("To verify for user when 'Download Native' is Checekd/Unchecked from Bulk User Access Control");
		userManage = new UserManagement(driver);
		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);
		// login
		loginPage.loginToSightLine(loginuser, loginPass);
		if (assignRole == "da") {
			baseClass.selectproject(Input.domainName);
		}
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		driver.waitForPageToBeReady();
		userManage.getBulkUserAccessTab().waitAndClick(5);
		userManage.getSelectRollId().selectFromDropdown().selectByVisibleText(rollId);
		if (assignRole == "sa" || assignRole == "da") {
			userManage.getSelectingProject().waitAndClick(5);
			userManage.getSelectDropProject(Input.projectName).waitAndClick(10);
			baseClass.waitTime(2);
		}
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
			userManage.getDisableRadioBtn().javascriptclick(userManage.getDisableRadioBtn());
//			userManage.getDisableRadioBtn().waitAndClick(5);
			if (roll == "rmu" || roll == "rev") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		if (assignRole == "sa" || assignRole == "da") {
			driver.scrollingToElementofAPage(userManage.getDisableRadioBtn());
			userManage.getDisableRadioBtn().javascriptclick(userManage.getDisableRadioBtn());
			
			if (roll == "rmu" || roll == "rev") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		Actions Act=new Actions(driver.getWebDriver());
		Act.clickAndHold(userManage.getPopupWindowHeader().getWebElement());
		Act.moveToElement(userManage.getPopupWindowHeader().getWebElement(), -10, 10);
		Act.release().build().perform();
		userManage.getSelectBulkUser(firstName).javascriptclick(userManage.getSelectBulkUser(firstName));
		userManage.getBulkUserSaveBtn().javascriptclick(userManage.getBulkUserSaveBtn());
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
		if (assignRole == "sa" || assignRole == "da") {
			userManage.getSelectingProject().waitAndClick(5);
			userManage.getSelectDropProject(Input.projectName).waitAndClick(10);
			baseClass.waitTime(2);
		}
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
			userManage.getEnableRadioBtn().javascriptclick(userManage.getEnableRadioBtn());
			if (roll == "rmu" || roll == "rev") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		if (assignRole == "sa" || assignRole == "da") {
			driver.scrollingToElementofAPage(userManage.getEnableRadioBtn());
			userManage.getEnableRadioBtn().javascriptclick(userManage.getEnableRadioBtn());
			if (roll == "rmu" || roll == "rev") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		Act.clickAndHold(userManage.getPopupWindowHeader().getWebElement());
		Act.moveToElement(userManage.getPopupWindowHeader().getWebElement(), -10, 10);
		Act.release().build().perform();
		userManage.getSelectBulkUser(firstName).javascriptclick(userManage.getSelectBulkUser(firstName));
		userManage.getBulkUserSaveBtn().javascriptclick(userManage.getBulkUserSaveBtn());
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

	/**
	 * Author : Baskar date: NA Modified date:06/05/2022 Modified by: Baskar
	 * Description :To verify for user when 'Redaction' is Checekd/Unchecked
	 *                from Bulk User Access Control
	 */

	@DataProvider(name = "bulkRedaction")
	public Object[][] bulkRedaction() {

		return new Object[][] {
				{ "rmu", Input.pa1userName, Input.pa1password, "Review Manager", Input.rmu1userName, Input.rmu1password,
						"pa", Input.rmu1FullName },
				{ "rev", Input.pa1userName, Input.pa1password, "Reviewer", Input.rev1userName, Input.rev1password, "pa",
						Input.rev1FullName, },
				{ "rmu", Input.sa1userName, Input.sa1password, "Review Manager", Input.rmu1userName, Input.rmu1password,
						"sa", Input.rmu1FullName },
				{ "rev", Input.sa1userName, Input.sa1password, "Reviewer", Input.rev1userName, Input.rev1password, "sa",
						Input.rev1FullName },
				{ "rmu", Input.da1userName, Input.da1password, "Review Manager", Input.rmu1userName, Input.rmu1password,
						"da", Input.rmu1FullName },
				{ "rev", Input.da1userName, Input.da1password, "Reviewer", Input.rev1userName, Input.rev1password, "da",
						Input.rev1FullName }, 
				};
	}

	@Test(description ="RPMXCON-52704",alwaysRun = true, dataProvider = "bulkRedaction", groups = { "regression" })
	public void bulkAssignRedaction(String roll, String loginuser, String loginPass, String rollId,
			String rollUser, String rollPass, String assignRole, String firstName) throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52704");
		baseClass.stepInfo("To verify for user when 'Redaction' is Checekd/Unchecked from "
				+ "Bulk User Access Control");
		userManage = new UserManagement(driver);
		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);
		softAssertion=new SoftAssert();
		// login
		loginPage.loginToSightLine(loginuser, loginPass);
		if (assignRole == "da") {
			baseClass.selectproject(Input.domainName);
		}
		
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		driver.waitForPageToBeReady();
		userManage.getBulkUserAccessTab().waitAndClick(5);
		Thread.sleep(2000);
		baseClass.waitForElement(userManage.getSelectRollId());
		userManage.getSelectRollId().selectFromDropdown().selectByVisibleText(rollId);
		if (assignRole == "sa" || assignRole == "da") {
			userManage.getSelectingProject().waitAndClick(5);
			userManage.getSelectDropProject(Input.projectName).waitAndClick(10);
			baseClass.waitTime(2);
		}
		if (roll == "pa") {
			userManage.defaultSelectionCheckboxForAllRole(true, false, true, true, true, true, true, true, true, true,
					true, false, true, true, true);
		}
		if (roll == "rmu") {
			userManage.defaultSelectionCheckboxForAllRole(true, false, true, true, true, true, true, false, false, true,
					true, false, true, true, true);
		}
		if (roll == "rev") {
			userManage.defaultSelectionCheckboxForAllRole(false, false, false, true, false, false, false, false, false,
					false, true, false, true, true, true);
		}
		baseClass.stepInfo("Disable the radio btn for Redaction checkbox");
		if (assignRole == "pa") {
			driver.scrollingToElementofAPage(userManage.getDisableRadioBtn());	
			userManage.getDisableRadioBtn().javascriptclick(userManage.getDisableRadioBtn());
			if (roll == "rmu" || roll == "rev") {
				baseClass.waitForElement(userManage.getBulkUserSecurityGroup());
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				baseClass.waitForElement(userManage.getSelectDropSG(Input.securityGroup));
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		if (assignRole == "sa" || assignRole == "da") {
			driver.scrollingToElementofAPage(userManage.getDisableRadioBtn());
			userManage.getDisableRadioBtn().javascriptclick(userManage.getDisableRadioBtn());
			
			if (roll == "rmu" || roll == "rev") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		
		Thread.sleep(2000);
		Actions Act = new Actions(driver.getWebDriver());
		Act.clickAndHold(userManage.getPopupWindowHeader().getWebElement());
		Act.moveToElement(userManage.getPopupWindowHeader().getWebElement(), -10, 10);
		Act.release().build().perform();
		userManage.getSelectBulkUser(firstName).javascriptclick(userManage.getSelectBulkUser(firstName));
		userManage.getBulkUserSaveBtn().javascriptclick(userManage.getBulkUserSaveBtn());

		baseClass.VerifySuccessMessage("Access rights applied successfully");
		loginPage.logout();
		// login as userassigned for validation
		loginPage.loginToSightLine(rollUser, rollPass);
		// session search to docview
		sessionSearch.audioSearch(Input.audioSearchString1, Input.audioLanguage);
		sessionSearch.ViewInDocView();
		// validating redaction tab
//		baseClass.waitForElement(userManage.getRedaction());
		boolean redactionNotpresent = userManage.getRedaction().isDisplayed();
		softAssertion.assertFalse(redactionNotpresent);
		baseClass.passedStep("Redaction tag not displayed in docview page");
		// logout
		loginPage.logout();
		// login
		loginPage.loginToSightLine(loginuser, loginPass);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		driver.waitForPageToBeReady();
		userManage.getBulkUserAccessTab().waitAndClick(5);
		userManage.getSelectRollId().selectFromDropdown().selectByVisibleText(rollId);
		if (assignRole == "sa" || assignRole == "da") {
			userManage.getSelectingProject().waitAndClick(5);
			userManage.getSelectDropProject(Input.projectName).waitAndClick(10);
			baseClass.waitTime(2);
		}
		if (roll == "pa") {
			userManage.defaultSelectionCheckboxForAllRole(true, false, true, true, true, true, true, true, true, true,
					true, false, true, true, true);
		}
		if (roll == "rmu") {		
			userManage.defaultSelectionCheckboxForAllRole(true, false, true, true, true, true, true, false, false, true,
					true, false, true, true, true);
		}
		if (roll == "rev") {
			userManage.defaultSelectionCheckboxForAllRole(false, false, false, true, false, false, false, false, false,
					false, true, false, true, true, true);
		}
		baseClass.stepInfo("Enable the radio btn for Redaction checkbox");
		if (assignRole == "pa") {
			driver.scrollingToElementofAPage(userManage.getEnableRadioBtn());
			userManage.getEnableRadioBtn().javascriptclick(userManage.getEnableRadioBtn());
			if (roll == "rmu" || roll == "rev") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		if (assignRole == "sa" || assignRole == "da") {
			driver.scrollingToElementofAPage(userManage.getEnableRadioBtn());
			userManage.getEnableRadioBtn().javascriptclick(userManage.getEnableRadioBtn());
			if (roll == "rmu" || roll == "rev") {
				userManage.getBulkUserSecurityGroup().waitAndClick(5);
				userManage.getSelectDropSG(Input.securityGroup).waitAndClick(5);
			}
		}
		Thread.sleep(2000);
		Act.clickAndHold(userManage.getPopupWindowHeader().getWebElement());
		Act.moveToElement(userManage.getPopupWindowHeader().getWebElement(), -10, 10);
		Act.release().build().perform();
		userManage.getSelectBulkUser(firstName).javascriptclick(userManage.getSelectBulkUser(firstName));
		userManage.getBulkUserSaveBtn().javascriptclick(userManage.getBulkUserSaveBtn());

		baseClass.VerifySuccessMessage("Access rights applied successfully");
		// logout
		loginPage.logout();
		// login as userassigned for validation
		loginPage.loginToSightLine(rollUser, rollPass);
		// session search to docview
		sessionSearch.audioSearch(Input.audioSearchString1, Input.audioLanguage);
		sessionSearch.ViewInDocView();
		// validating redaction tab
		baseClass.waitForElement(userManage.getRedaction());
		boolean redactiontpresent = userManage.getRedaction().isDisplayed();
		softAssertion.assertTrue(redactiontpresent);
		baseClass.passedStep("Redaction tag  displayed in docview page");
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
