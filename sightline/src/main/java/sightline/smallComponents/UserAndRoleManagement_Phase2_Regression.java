package sightline.smallComponents;

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
import pageFactory.Dashboard;
import pageFactory.DocExplorerPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class UserAndRoleManagement_Phase2_Regression {

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
		return new Object[][] { { Input.sa1userName, Input.sa1password, "SA" },
				{ Input.pa1userName, Input.pa1password, "PA" } };
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
		baseClass.waitTime(2);
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

	/**
	 * Author :Arunkumar date: 20/10/2022 TestCase Id:RPMXCON-52687 Description :To
	 * verify that user will be displayed in 'Pending Activation User', if his
	 * account is not activates.
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-52687", dataProvider = "users", enabled = true, groups = { "regression" })
	public void verifyUserNotYetLoggedInStatus(String userName, String password, String role) throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52687");
		baseClass.stepInfo("Verify user displayed in User Not Yet Logged In");
		String email = "qa" + Utility.dynamicNameAppender() + "@consilio.com";
		String[] details = { "QA", "User", email };
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Logged in as " + role);
		userManage.navigateToUsersPAge();
		baseClass.stepInfo("create new user");
		userManage.addNewUserAsDifferentUsers(role, "QA", "User", Input.ReviewManager, email, null, Input.projectName);
		baseClass.stepInfo("Verify user details in 'User Not Logged In' popup");
		userManage.verifyUserDetailsOnUserNotLoggedInPopup(details, email);
		loginPage.logout();
		// deleting added new user
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManage.filterByName(email);
		userManage.deleteUser();
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: 20/10/2022 TestCase Id:RPMXCON-53174 Description
	 * :Verify Manage Users list when "Show Inactive Users" toggle is ON
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53174", enabled = true, groups = { "regression" })
	public void verifyInactiveUsersToggleStatus() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-53174");
		baseClass.stepInfo("Verify Manage Users list when 'Show Inactive Users' toggle is ON");

		// login as system admin
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");
		userManage.navigateToUsersPAge();
		driver.waitForPageToBeReady();
		// get users count before turning ON toggle
		int activeUsersCount = userManage.getUserCountInfo();
		baseClass.stepInfo("Total users count before turning ON toggle :" + activeUsersCount);
		baseClass.stepInfo("Turn ON the toggle and verify status");
		baseClass.waitForElement(userManage.getActiveInactiveBtn());
		userManage.getActiveInactiveBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		userManage.verifyInactiveUsersListDisplayedStatus();
		// get count after turning ON toggle
		int totalUsersCount = userManage.getUserCountInfo();
		baseClass.stepInfo("Total users count after turning ON toggle :" + totalUsersCount);
		if (totalUsersCount > activeUsersCount) {
			baseClass.passedStep("Users list shows inactive users along with active users");
		} else {
			baseClass.failedStep("inactive users list not displayed after turning on the toggle");
		}
		loginPage.logout();
	}

	/**
	 * @Author : Jeevitha
	 * @Description :To verify when RMU impersonates Reviewer role within his/her
	 *              security group [RPMXCON-52431]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-52431", enabled = true, groups = { "regression" })
	public void verifyWhenRmuImpersonatesRev() throws Exception {

		userManage = new UserManagement(driver);
		security = new SecurityGroupsPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-52431  Users and Role Management");
		baseClass.stepInfo("To verify when RMU impersonates Reviewer role within his/her security group");

		// login as Review manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");

		// Impersonate from Review manager to Reviewer
		baseClass.rolesToImp("RMU", "REV");

		// check Role After impersonation
		if (baseClass.getPageTitle().isElementAvailable(6)) {
			baseClass.passedStep("landing page of Reviewer is displayed.");

			driver.waitForPageToBeReady();
			loginPage.getSignoutMenu().waitAndClick(10);
			baseClass.waitForElement(baseClass.getLoginedUserRole());
			String currentRole = baseClass.getLoginedUserRole().getText();
			baseClass.textCompareEquals(currentRole, Input.Reviewer, "Current Role is REV",
					"Current role is not as expected");
		} else {
			baseClass.failedStep("landing page of Reviewer is not displayed.");
		}

		// logout
		loginPage.logout();
		baseClass.stepInfo("Logged out Successfully");

		// Relogin to same Credential
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Re-Logged with Same RMU");

		// Check Role
		driver.waitForPageToBeReady();
		loginPage.getSignoutMenu().waitAndClick(10);
		baseClass.waitForElement(baseClass.getLoginedUserRole());
		String currentRole = baseClass.getLoginedUserRole().getText();
		baseClass.textCompareEquals(currentRole, Input.ReviewManager, "Current Role is RMU",
				"Current role is not as expected");

		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Jeevitha
	 * @Description : To verify user rights for all user after impersonation
	 *              [RPMXCON-52484]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-52484", dataProvider = "SaAndPaUser", enabled = true, groups = { "regression" })
	public void verifyFunctionalityForAllUsers(String username, String password, String userRole) throws Exception {
		String[] checkedCbPa = { Input.Searching, Input.Highlighting, Input.Redactions, Input.ReviewerRemarks,
				Input.AnalyticsPanels, Input.Manage, Input.Productions, Input.AllReports, Input.ConceptExplorer,
				Input.CommunicationsExplorer, Input.Categorize, Input.Datasets };
		String[] disabledCBPa = { Input.ManageDomainProjects };
		String[] uncheckedCBPa = { Input.Ingestions };

		String[] checkedCb = { Input.Searching, Input.Highlighting, Input.Redactions, Input.ReviewerRemarks,
				Input.AnalyticsPanels, Input.Manage, Input.Productions, Input.AllReports, Input.ConceptExplorer,
				Input.CommunicationsExplorer, Input.Categorize };
		String[] disabledCB = { Input.ManageDomainProjects, Input.Ingestions };
		String[] uncheckedCB = { Input.Datasets };

		String[] checkedCbRev = { Input.Searching, Input.Highlighting, Input.Redactions, Input.ReviewerRemarks,
				Input.AnalyticsPanels };
		String[] disabledCBRev = { Input.Manage, Input.ManageDomainProjects, Input.Ingestions, Input.Productions,
				Input.Datasets, Input.AllReports };
		String[] uncheckedCBRev = { Input.ConceptExplorer, Input.CommunicationsExplorer };

		userManage = new UserManagement(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-52484  Users and Role Management");
		baseClass.stepInfo("To verify user rights for all user after impersonation");

		// login as User
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged in as :" + userRole);

		// Impersonate from Review manager to Reviewer
		baseClass.rolesToImp(userRole, "PA");

		// navigate to userpage
		userManage.navigateToUsersPAge();

		String[][] usersToCheck = { { Input.ProjectAdministrator, Input.pa1userName, "PA" },
				{ Input.ReviewManager, Input.rmu1userName, "PA" }, { Input.Reviewer, Input.rev1userName, "PA" } };

		// verify Checked, unchecked, & Disabled checkbox of users
		// And verification for Check or uncheck Component and save
		userManage.verifyFunctionalityCheckbox(usersToCheck, checkedCbPa, disabledCBPa, uncheckedCBPa, checkedCb,
				disabledCB, uncheckedCB, checkedCbRev, disabledCBRev, uncheckedCBRev, true, Input.DownloadNative);

		// logout
		loginPage.logout();
	}

	/**
	 * Author :Mohan date: 28/10/2022 TestCase Id:RPMXCON-52418 Description :To
	 * verify when Project Admin emulates RMU role and check Dashboard and my
	 * assignments page
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-52418", enabled = true, groups = { "regression" })
	public void verifyProjectAdminEmulatesRMURoleAndCheckDashboardAndAssignmentPage() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52418");
		baseClass
				.stepInfo("To verify when Project Admin emulates RMU role and check Dashboard and my assignments page");

		// login as project admin
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");

		// Verify logged in user is PA
		DocExplorerPage docExplorerPage = new DocExplorerPage(driver);
		docExplorerPage.verifyUserIsOnDocExplorerPage();

		// Impersonate from PA to RMU
		baseClass.impersonatePAtoRMU();
		Dashboard dashboard = new Dashboard(driver);
		dashboard.verifyDashboardPage();

		// verify when clicked on assignment should go to assignment page
		dashboard.clickonAssignmentPanelTile();

		// logout
		loginPage.logout();

	}

	/**
	 * Author :Mohan date: 26/10/2022 TestCase Id:RPMXCON-52546 Description :To
	 * verify Sys Admin can change user rights in bulk for Project Admin
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-52546", enabled = true, groups = { "regression" })
	public void verifySystemAdminChangeUserRightsInBulkAsPA() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52546");
		baseClass.stepInfo("To verify Sys Admin can change user rights in bulk for Project Admin");

		// login as project admin
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");

		userManage.navigateToUsersPAge();
		userManage.filterByName(Input.pa1userName);
		String PA1 = userManage.getfirstUserName();
		driver.Navigate().refresh();
		userManage.filterByName(Input.pa2userName);
		String PA2 = userManage.getfirstUserName();
		String[] users = { PA1, PA2 };

		// select role and enable
		baseClass.stepInfo("Enable users rights");
		userManage.enableOrDisableUsersRights("Enable", users);
		loginPage.logout();

		String[] username = { Input.pa1userName, Input.pa2userName };
		String[] password = { Input.pa1password, Input.pa2password };

		baseClass.stepInfo("verifying Enabled users rights");
		for (int i = 0; i < username.length; i++) {
			loginPage.loginToSightLine(username[i], password[i]);

			baseClass.stepInfo("Login as a pa user :" + username[i]);
			baseClass.ValidateElement_Presence(baseClass.text("Datasets"), "Datasets");
			baseClass.ValidateElement_Presence(baseClass.text("Categorize"), "Categorize");
			loginPage.logout();
		}

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Login as a sa user :" + Input.sa1userName);
		// disable rights
		userManage.navigateToUsersPAge();
		baseClass.stepInfo("Disable users rights");
		userManage.enableOrDisableUsersRights("disable", users);
		loginPage.logout();

		baseClass.stepInfo("verifying disabled users rights");
		for (int j = 0; j < username.length; j++) {
			loginPage.loginToSightLine(username[j], password[j]);

			baseClass.stepInfo("Login as a pa user :" + username[j]);
			if (!baseClass.text("Datasets").isElementAvailable(2)) {
				baseClass.passedStep("Dataset is not available");
			} else {
				baseClass.failedStep("Dataset is available");
			}

			if (!baseClass.text("Categorize").isElementAvailable(2)) {
				baseClass.passedStep("Categorize is not available");
			} else {
				baseClass.failedStep("Categorize is available");
			}

			loginPage.logout();
		}
		baseClass.passedStep("verified Domain Admin can change user rights in bulk for PA");

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManage.navigateToUsersPAge();
		userManage.enableOrDisableUsersRights("Enable", users);

		loginPage.logout();
	}

	/**
	 * Author :Mohan date: 26/10/2022 TestCase Id:RPMXCON-52887 Description :To
	 * verify project and domain drop down values when user change role to
	 * DA/SA/RMU/Reviewer in Edit pop up as PA user
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-52887", enabled = true, groups = { "regression" })
	public void verifyProjectAndDomainDropValuesWhenUserChageRoleToDASARMUReviewer() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52887");
		baseClass.stepInfo(
				"To verify project and domain drop down values when user change role to DA/SA/RMU/Reviewer in Edit pop up as PA user");

		// login as sys admin
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");

		// change role from PA to DA
		userManage.navigateToUsersPAge();
		userManage.filterByName(Input.pa2userName);
		userManage.selectEditUserUsingPagination(Input.projectName, null, null);
		baseClass.stepInfo("Change Role from PA to DA");
		userManage.changeRoleToDAFromAnyUser();
		userManage.navigateToUsersPAge();
		userManage.filterByName(Input.pa2userName);
		userManage.selectEditUserUsingPagination(Input.DomainAdministrator, null, null);
		baseClass.stepInfo("Change Role from DA to PA");
		userManage.changeRoleToAnyUser(Input.ProjectAdministrator, Input.projectName, null);

		// change role from RMU to DA
		userManage.navigateToUsersPAge();
		userManage.filterByName(Input.rmu2userName);
		userManage.selectEditUserUsingPagination(Input.projectName, null, null);
		baseClass.stepInfo("Change Role from RMU to DA");
		userManage.changeRoleToDAFromAnyUser();
		userManage.navigateToUsersPAge();
		userManage.filterByName(Input.rmu2userName);
		userManage.selectEditUserUsingPagination(Input.DomainAdministrator, null, null);
		baseClass.stepInfo("Change Role from DA to RMU");
		userManage.changeRoleToAnyUser(Input.ReviewManager, Input.projectName, Input.securityGroup);

		// change role from RMU to DA
		userManage.navigateToUsersPAge();
		userManage.filterByName(Input.rev2userName);
		userManage.selectEditUserUsingPagination(Input.projectName, null, null);
		baseClass.stepInfo("Change Role from Reviewer to DA");
		userManage.changeRoleToDAFromAnyUser();
		userManage.navigateToUsersPAge();
		userManage.filterByName(Input.rev2userName);
		userManage.selectEditUserUsingPagination(Input.DomainAdministrator, null, null);
		baseClass.stepInfo("Change Role from DA to Reviewer");
		userManage.changeRoleToAnyUser(Input.Reviewer, Input.projectName, Input.securityGroup);

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
