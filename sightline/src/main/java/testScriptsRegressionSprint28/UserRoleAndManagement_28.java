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
import pageFactory.SecurityGroupsPage;
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

	@DataProvider(name = "Users")
	public Object[][] users() {
		Object[][] users = {{ Input.pa1userName, Input.pa1password }, { Input.da1userName, Input.da1password }, 
		                    { Input.sa1userName, Input.sa1password }, { Input.rmu1userName, Input.rmu1password }};
		return users;
	}
	
	/**
	 * @author NA
	 * @Description :To Verify that user can remove attorney profile from an RMU user who is set with attorney profile.[RPMXCON-53251]
	 */
	@Test(description = "RPMXCON-53251", enabled = true, dataProvider = "Users", groups = { "regression" })
	public void verifyRemoveAttorneyFromRMU(String username, String password) throws Exception {
		SecurityGroupsPage sgPage = new SecurityGroupsPage(driver);
		UserManagement user = new UserManagement(driver);

		String firstName1 = "User01";
		String lastName1 = "RMU";
		String email1 = "user1"+Utility.dynamicNameAppender()+"@consilio.com";
		String role = Input.ReviewManager;
		String firstName2 = "User02";
		String lastName2 = "RMU";
		String email2 = "user2"+Utility.dynamicNameAppender()+"@consilio.com";
		String sgName = "Security Group" + Utility.dynamicNameAppender();	

		baseClass.stepInfo("RPMXCON-53251");
		baseClass.stepInfo("To Verify that user can remove attorney profile from an RMU user who is set with attorney profile");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		sgPage.navigateToSecurityGropusPageURL();
		sgPage.createSecurityGroups(sgName);
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		user.createNewUser(firstName1, lastName1, role, email1, Input.domainName, Input.projectName);
		user.createNewUser(firstName2, lastName2, role, email2, Input.domainName, Input.projectName);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		user.filterByName(email1);
		driver.waitForPageToBeReady();
		user.editLoginUser();
		baseClass.waitForElement(user.getEditAttorneyCheckBox());
		user.getEditAttorneyCheckBox().waitAndClick(5);
		baseClass.waitForElement(user.getSubmitChanges());
		user.getSubmitChanges().waitAndClick(5);
		
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		user.filterByName(email2);
		driver.waitForPageToBeReady();
		user.editLoginUser();
		baseClass.waitForElement(user.getEditAttorneyCheckBox());
		user.getEditAttorneyCheckBox().waitAndClick(5);

		user.addingSGToUser(Input.securityGroup, sgName);
		baseClass.waitForElement(user.getSubmitChanges());
		user.getSubmitChanges().waitAndClick(5);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		loginPage.logout();

		loginPage.loginToSightLine(username, password);
		user.navigateToUsersPAge();
		driver.waitForPageToBeReady();
		user.filterByName(email1);
		driver.waitForPageToBeReady();
		user.editLoginUser();
			
		baseClass.waitForElement(user.getEditAttorneyCheckBox());
		user.getEditAttorneyCheckBox().waitAndClick(5);
		baseClass.waitForElement(user.getSubmitChanges());
		user.getSubmitChanges().waitAndClick(5);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		user.filterByName(email1);
		driver.waitForPageToBeReady();
		user.editLoginUser();
		boolean status1 = user.getAttorneyStatusCheck().isElementAvailable(5);
		SoftAssert aserts = new SoftAssert();
		aserts.assertFalse(status1);
		aserts.assertAll();
		
		user.navigateToUsersPAge();
		driver.waitForPageToBeReady();
		user.filterByName(email2);
		driver.waitForPageToBeReady();
		user.editLoginUser();
			
		baseClass.waitForElement(user.getEditAttorneyCheckBox());
		user.getEditAttorneyCheckBox().waitAndClick(5);
		baseClass.waitForElement(user.getSubmitChanges());
		user.getSubmitChanges().waitAndClick(5);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		user.filterByName(email1);
		driver.waitForPageToBeReady();
		user.editLoginUser();
		boolean status2 = user.getAttorneyStatusCheck().isElementAvailable(5);
		aserts.assertFalse(status2);	
		aserts.assertAll();
		loginPage.logout();		

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		user.filterByName(email1);
		user.verifyDeleteUserPopup(true, Input.projectName);
		user.filterByName(email2);
		user.verifyDeleteUserPopup(true, Input.projectName);
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		sgPage.navigateToSecurityGropusPageURL();
		sgPage.deleteSecurityGroups(sgName);
		baseClass.passedStep("Verified - that user can remove attorney profile from an RMU user who is set with attorney profile");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 17/12/2022 TestCase Id:RPMXCON-52485
	 * Description :To verify user rights of user for all roles who is assigned to different projects 
	 * with different roles after saving user rights for any of the role of any project
	 * @throws Exception 
	 */
	@Test(description ="RPMXCON-52485",enabled = true, groups = { "regression" })
	public void verifyUserRightOverWriteFunctionality() throws Exception {
		
		baseClass.stepInfo("Test case Id: RPMXCON-52485");
		baseClass.stepInfo("Verify User rights for all roles assigned to different projects");
		
		String[] role = {Input.ProjectAdministrator,Input.ReviewManager,Input.Reviewer};
		String[] project = {Input.projectName,Input.additionalDataProject,Input.highVolumeProject};
		String email = "QaUser"+Utility.dynamicNameAppender()+"@consilio.com";
				
		//login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");
		//pre-requisite
		baseClass.stepInfo("assign valid user to different project with different role");
		for(int i=0;i<role.length;i++) {
			userManage.addNewUserAsDifferentUsers("SA", "QA", "user", role[i], email, 
					null, project[i]);
		}
		baseClass.stepInfo("edit pa role");
		userManage.NavigateToEditUserFunctionalityTab(email, project[0]);
		baseClass.stepInfo("edit search functionality(uncheck) and save");
		userManage.verifyStatusSearch("false");
		baseClass.stepInfo("verify overwrite functionality in Rmu and Rev");
		for(int k=1;k<role.length;k++) {
			userManage.NavigateToEditUserFunctionalityTab(email, project[k]);
			String status =userManage.verifySearchFunctionalityStatus();
			baseClass.compareTextViaContains(status, "Enabled", "Edited Functionality not overwrited for role--"+role[k], 
					"edited Functionality overwrited for role--"+role[k]);
		}
		//delete created user
		userManage.navigateToUsersPAge();
		userManage.filterByName(email);
		userManage.deleteUser();
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
