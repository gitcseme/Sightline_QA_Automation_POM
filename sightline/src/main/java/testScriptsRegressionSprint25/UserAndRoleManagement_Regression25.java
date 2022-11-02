package testScriptsRegressionSprint25;

import java.io.IOException;
import java.lang.reflect.Method;

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
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class UserAndRoleManagement_Regression25 {
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	DocViewPage docView;
	Utility utility;
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
		userManage = new UserManagement(driver);

	}

	@DataProvider(name = "users")
	public Object[][] Users() {
		return new Object[][] { { Input.sa1userName, Input.sa1password, "SA" },
				{ Input.pa1userName, Input.pa1password, "PA" }, { Input.rmu1userName, Input.rmu1password, "RMU" } };
	}

	@DataProvider(name = "SaAndPaUser")
	public Object[][] SaAndPaUser() {
		return new Object[][] {
			{ Input.sa1userName, Input.sa1password, "SA" },
				{ Input.pa1userName, Input.pa1password, "PA" } };
	}
	
	/**
	 * Author :Mohan date: 02/11/2022 TestCase Id:RPMXCON-52914 Description :To
	 * verify project and domain drop down values when user change role to
	 * DA/PA/Reviewer/SA in Edit pop up as RMU user
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-52914", enabled = true, groups = { "regression" })
	public void verifyProjectAndDomainDropValuesWhenUserChageRoleToDASARMUReviewerFromPA() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52914");
		baseClass.stepInfo(
				"To verify project and domain drop down values when user change role to DA/PA/Reviewer/SA in Edit pop up as RMU user");

		// login as sys admin
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");

		// change role from DA to PA
		userManage.navigateToUsersPAge();
		userManage.filterByName(Input.da1userName);
		userManage.selectEditUserUsingPagination(Input.DomainAdministrator, null, null);
		baseClass.stepInfo("Change Role from DA to PA");
		userManage.changeRoleToAnyUser(Input.ProjectAdministrator, Input.projectName, null);
		userManage.selectEditUserUsingPagination(Input.projectName, null, null);
		baseClass.stepInfo("Change Role from PA to DA");
		userManage.changeRoleToDAFromAnyUser();

		// change role from RMU to PA
		userManage.navigateToUsersPAge();
		userManage.filterByName(Input.rmu1userName);
		userManage.selectEditUserUsingPagination(Input.projectName, null, null);
		baseClass.stepInfo("Change Role from RMU to PA");
		userManage.changeRoleToAnyUser(Input.ProjectAdministrator, Input.projectName, null);
		userManage.selectEditUserUsingPagination(Input.projectName, null, null);
		baseClass.stepInfo("Change Role from PA to RMU");
		userManage.changeRoleToAnyUser(Input.ReviewManager, Input.projectName, Input.securityGroup);

		// change role from Reviewer to PA
		userManage.navigateToUsersPAge();
		userManage.filterByName(Input.rev1userName);
		userManage.selectEditUserUsingPagination(Input.projectName, null, null);
		baseClass.stepInfo("Change Role from Reviewer to PA");
		userManage.changeRoleToAnyUser(Input.ProjectAdministrator, Input.projectName, null);
		userManage.selectEditUserUsingPagination(Input.projectName, null, null);
		baseClass.stepInfo("Change Role from PA to Reviewer");
		userManage.changeRoleToAnyUser(Input.Reviewer, Input.projectName, Input.securityGroup);

		loginPage.logout();

	}

	/**
	 * Author :Mohan date: 02/11/2022 TestCase Id:RPMXCON-52521 Description :To
	 * verify the user can access functionality as per the updated user rights.
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-52521", enabled = true, groups = { "regression" })
	public void verifyUserAccessFunctionalityAsPerUpdatedUserRights() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52521");
		baseClass.stepInfo("To verify the user can access functionality as per the updated user rights.");
		String[][] userRolesData = { { Input.pa1userName, "Project Administrator", "SA" } };

		// login as project admin
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		baseClass.ValidateElement_Presence(baseClass.text("Datasets"), "Datasets");
		baseClass.ValidateElement_Presence(baseClass.text("Categorize"), "Categorize");
		baseClass.passedStep("Access of functionality after login is default");
		loginPage.logout();

		// login as Sys Admin
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");
		userManage.navigateToUsersPAge();
		userManage.filterByName(Input.pa1userName);
		String userFirstName = userManage.getUserTableValuesFromManageUserTable("First Name", 1);
		String userLastName = userManage.getUserTableValuesFromManageUserTable("Last Name", 2);
		userManage.selectEditUserUsingPagination(Input.projectName, null, null);
		userManage.editFirstAndLastNameInEditUserPopup(userFirstName, userLastName);

		// Login as User and verify Module Access
		userManage.verifyCollectionAccess(userRolesData, Input.sa1userName, Input.sa1password, Input.pa1password);
		baseClass.passedStep("User is able to access the functionality as per the rights and logged in role.");
		loginPage.logout();
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
