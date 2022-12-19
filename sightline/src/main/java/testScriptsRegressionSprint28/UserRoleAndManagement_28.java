package testScriptsRegressionSprint28;

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
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class UserRoleAndManagement_28 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Utility utility;
	SoftAssert softAssertion;
	ProjectPage projects;
	UserManagement userManage;
	String projectName;
	String clientName;

	@BeforeClass(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		Input input = new Input();
		input.loadEnvConfig();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());

		driver = new Driver();
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		softAssertion = new SoftAssert();
		projects = new ProjectPage(driver);
		userManage = new UserManagement(driver);
	}

	@DataProvider(name = "addUserSameRole")
	public Object[][] addUserSameRole() {
		return new Object[][] {
				{ Input.sa1userName, Input.sa1password, "SA", Input.pa1userName, Input.ProjectAdministrator },
				{ Input.sa1userName, Input.sa1password, "SA", Input.rmu1userName, Input.ReviewManager },
				{ Input.sa1userName, Input.sa1password, "SA", Input.rev1userName, Input.Reviewer },
				{ Input.pa1userName, Input.pa1password, "PA", Input.pa1userName, Input.ProjectAdministrator },
				{ Input.pa1userName, Input.pa1password, "PA", Input.rmu1userName, Input.ReviewManager },
				{ Input.pa1userName, Input.pa1password, "PA", Input.rev1userName, Input.Reviewer },
				{ Input.rmu1userName, Input.rmu1password, "RMU", Input.rmu1userName, Input.ReviewManager },
				{ Input.rmu1userName, Input.rmu1password, "RMU", Input.rev1userName, Input.Reviewer } };
	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :To verify when user adds existing user with existing email
	 *              address with same role under same project. RPMXCON-52409
	 */

	@Test(description = "RPMXCON-52409", dataProvider = "addUserSameRole", enabled = true, groups = { "regression" })
	public void verifyUserAddsExistingUserWithExistingEmailAddressSameRoleUnderSameProject(String userName,
			String password, String logInAs, String emailId, String role) {

		baseClass.stepInfo("Test case Id: RPMXCON-52409");
		baseClass.stepInfo(
				"To verify when user adds existing user with existing email address with same role under same project.");

		// login as user
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Logged in as " + logInAs);

		// filtering the specific user and click on edit
		userManage.navigateToUsersPAge();
		userManage.filterByName(emailId);
		if (logInAs.equalsIgnoreCase("SA")) {
			userManage.editUserPagination(Input.projectName);
		} else {
			baseClass.waitForElement(userManage.getLoginUserEdit());
			userManage.getLoginUserEdit().waitAndClick(5);
		}

		// getting data from existing user
		baseClass.waitTime(2);
		String firstName = userManage.getFirstNameEditUserPopup().GetAttribute("value");
		System.out.println(firstName);
		String lastName = userManage.getLastNameEditUserPopup().GetAttribute("value");
		System.out.println(lastName);
		userManage.getCancel().waitAndClick(5);
		baseClass.stepInfo("getting data from existing user.");

		// verify that Application should check for the user if exists , should run a
		// check on whether user is in the same project and same role. Error code
		// message should be displaye
		userManage.addNewUserAndVerifyErrorMessageForSameRoleOrDifferentRole(firstName, lastName, role, emailId,
				Input.domainName, Input.projectName, logInAs, true,Input.securityGroup);
		baseClass.passedStep(
				"verified that Application check for the user if exists, run a check on whether user is in the same project and same role.  Error code message is displaye.");

		// logOut
		loginPage.logout();
	}

	@DataProvider(name = "addUserDifferentRole")
	public Object[][] addUserDifferentRole() {
		return new Object[][] { 
			{ Input.sa1userName, Input.sa1password, "SA", Input.pa1userName, Input.ReviewManager },
				{ Input.sa1userName, Input.sa1password, "SA", Input.rmu1userName, Input.Reviewer },
				{ Input.sa1userName, Input.sa1password, "SA", Input.rev1userName, Input.ProjectAdministrator },
				{ Input.pa1userName, Input.pa1password, "PA", Input.pa1userName, Input.ReviewManager },
				{ Input.pa1userName, Input.pa1password, "PA", Input.rmu1userName, Input.Reviewer },
				{ Input.pa1userName, Input.pa1password, "PA", Input.rev1userName, Input.ProjectAdministrator },
				{ Input.rmu1userName, Input.rmu1password, "RMU", Input.rmu1userName, Input.Reviewer },
				{ Input.rmu1userName, Input.rmu1password, "RMU", Input.rev1userName, Input.ReviewManager }
				};
	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :To verify when user adds existing user with existing email
	 *              address with different role under same project and security
	 *              group.RPMXCON-52410
	 */

	@Test(description = "RPMXCON-52410", dataProvider = "addUserDifferentRole", enabled = true, groups = {
			"regression" })
	public void verifyUserAddsExistingEmailWithDifferentRoleUnderSameProjectAndSecurityGroup(String userName,
			String password, String logInAs, String emailId, String role) {

		baseClass.stepInfo("Test case Id: RPMXCON-52410");
		baseClass.stepInfo(
				"To verify when user adds existing user with existing email address with different role under same project and security group.");

		// login as user
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Logged in as " + logInAs);

		// filtering the specific user and click on edit
		userManage.navigateToUsersPAge();
		userManage.filterByName(emailId);
		if (logInAs.equalsIgnoreCase("SA")) {
			userManage.editUserPagination(Input.projectName);
		} else {
			baseClass.waitForElement(userManage.getLoginUserEdit());
			userManage.getLoginUserEdit().waitAndClick(5);
		}

		// getting data from existing user
		baseClass.waitTime(2);
		String firstName = userManage.getFirstNameEditUserPopup().GetAttribute("value");
		System.out.println(firstName);
		String lastName = userManage.getLastNameEditUserPopup().GetAttribute("value");
		System.out.println(lastName);
		userManage.getCancel().waitAndClick(5);
		baseClass.stepInfo("getting data from existing user.");

		// verify that If a role conflict has occurred application should show a
		// notification that request failed with specific error code. User should be
		// only be in a single role in a security group under a project
		userManage.addNewUserAndVerifyErrorMessageForSameRoleOrDifferentRole(firstName, lastName, role, emailId,
				Input.domainName, Input.projectName, logInAs, false,Input.securityGroup);
		baseClass.passedStep(
				"verified that  If a role conflict has occurred application should show a notification that request failed with specific error code. User should be only be in a single role in a security group under a project.");

		// logOut
		loginPage.logout();
	}

	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		baseClass = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility base = new Utility(driver);
			base.screenShot(result);
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR PROJECTS EXECUTED******");

	}

}
