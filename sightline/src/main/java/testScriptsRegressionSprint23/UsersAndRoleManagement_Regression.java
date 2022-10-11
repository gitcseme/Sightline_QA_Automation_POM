package testScriptsRegressionSprint23;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.openqa.selenium.JavascriptExecutor;
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

	@DataProvider(name = "sapa")
	public Object[][] SaPa() {
		return new Object[][] { { Input.sa1userName, Input.sa1password, "sa" },
				{ Input.pa1userName, Input.pa1password, "pa" } };
	}
	
	@DataProvider(name = "users")
	public Object[][] Users() {
		return new Object[][] { { Input.sa1userName, Input.sa1password, "SA" },
				{ Input.pa1userName, Input.pa1password, "PA" },
				{Input.rmu1userName, Input.rmu1password, "RMU" }};
	}

	/**
	 * Author : Baskar date: NA Modified date:03/10/2022 Modified by: Baskar
	 * Description :To verify user can have 'Enable', 'Disable' options on bulk user
	 * rights
	 */

	@Test(description = "RPMXCON-52552", dataProvider = "sapa", alwaysRun = true, groups = { "regression" })
	public void validateBulkUserRadioOptions(String userName, String password, String role) throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52552");
		baseClass.stepInfo("To verify user can have 'Enable', 'Disable' options on bulk user rights");
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();

		// Login As sa
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Login as : " + role + "");
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		driver.waitForPageToBeReady();
		userManage.getBulkUserAccessTab().waitAndClick(5);

		// verifying Enable and Disable option
		driver.scrollingToElementofAPage(userManage.getEnableRole());
		boolean roleEnable = userManage.getEnableRole().GetAttribute("role").contains("radio");
		softAssertion.assertTrue(roleEnable);
		baseClass.stepInfo("Enable Radio button options available in bulk user popup");
		driver.scrollingToElementofAPage(userManage.getDisableRole());
		boolean roleDisable = userManage.getDisableRole().GetAttribute("role").contains("radio");
		softAssertion.assertTrue(roleDisable);
		baseClass.stepInfo("Disable Radio button options available in bulk user popup");
		softAssertion.assertAll();
		baseClass.passedStep("Both Enable and disable radio button are available in bulk user popup window");
		// logout
		loginPage.logout();
	}

	/**
	 * Author : Baskar date: NA Modified date:03/10/2022 Modified by: Baskar
	 * Description :Verify when user changes the security group from the header drop
	 * down
	 */

	@Test(description = "RPMXCON-52676", alwaysRun = true, groups = { "regression" })
	public void validateBulkUserRadioOptions() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52676");
		baseClass.stepInfo("Verify when user changes the security group from the header drop down");
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();
		sessionSearch = new SessionSearch(driver);
		security = new SecurityGroupsPage(driver);
		String securityGroup = "securityGroup" + Utility.dynamicNameAppender();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Create security group
		security.navigateToSecurityGropusPageURL();
		security.AddSecurityGroup(securityGroup);

		// access to security group to REV and rmu
		userManage.assignAccessToSecurityGroups(securityGroup, Input.rmu1userName);
		userManage.assignAccessToSecurityGroups(securityGroup, Input.rev1userName);
		int count = sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		System.out.println(count);
		sessionSearch.bulkRelease(securityGroup);
		// logout
		loginPage.logout();

		// login as rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// selecting sg
		baseClass.selectsecuritygroup(securityGroup);
		baseClass.stepInfo("Changing the security group");

		// validation
		String releasedCount = userManage.getReleasedCount().getText();
		softAssertion.assertEquals(count, Integer.parseInt(releasedCount));
		baseClass.passedStep("user under the new created sg which selected at top header for rmu user");
		// logout
		loginPage.logout();

		// login as rev
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		// selecting sg
		baseClass.selectsecuritygroup(securityGroup);
		baseClass.stepInfo("Changing the security group");

		// validation
		this.driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(userManage.getReleasedCountRev());
		String releasedCountRev = userManage.getReleasedCountRev().getText();
		int actualRev = Integer.parseInt(releasedCountRev.replaceAll("[\\D]", ""));
		softAssertion.assertEquals(count, actualRev);
		baseClass.passedStep("user under the new created sg which selected at top header for rev user");
		softAssertion.assertAll();
		// logout
		loginPage.logout();

	}

	/**
	 * Author : Baskar date: NA Modified date:03/10/2022 Modified by: Baskar
	 * Description :Verify security group from change role pop up when user changes
	 * security group from header
	 */

	@Test(description = "RPMXCON-52677", alwaysRun = true, groups = { "regression" })
	public void validateSGFromChangeRolePopUp() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52677");
		baseClass.stepInfo(
				"Verify security group from change role pop up when user " + "changes security group from header");
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();
		sessionSearch = new SessionSearch(driver);
		security = new SecurityGroupsPage(driver);
		String securityGroup = "securityGroup" + Utility.dynamicNameAppender();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Create security group
		security.navigateToSecurityGropusPageURL();
		security.AddSecurityGroup(securityGroup);

		// access to security group to REV and rmu
		userManage.assignAccessToSecurityGroups(securityGroup, Input.rmu1userName);

		// logout
		loginPage.logout();

		// login as rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// selecting sg
		baseClass.selectsecuritygroup(securityGroup);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Changing the security group");

		baseClass.waitForElement(baseClass.getSignoutMenu());
		baseClass.getSignoutMenu().waitAndClick(10);
		baseClass.waitForElement(baseClass.getChangeRole());
		baseClass.getChangeRole().waitAndClick(10);
		baseClass.waitForElement(baseClass.getSelectRole());
		baseClass.getSelectRole().selectFromDropdown().selectByVisibleText("Reviewer");
		baseClass.waitForElement(baseClass.getAvlDomain());
		baseClass.getAvlDomain().selectFromDropdown().selectByVisibleText(Input.domainName);
		baseClass.waitForElement(baseClass.getAvlProject());
		baseClass.getAvlProject().selectFromDropdown().selectByVisibleText(Input.projectName);
		
		// validation in change role sg
		boolean flag = baseClass.dropDownValueCheck(baseClass.getSelectSecurityGroup(), securityGroup);
		softAssertion.assertTrue(flag);
		baseClass.passedStep("Changed Sg in header top value are displayed in change role popup window");
		softAssertion.assertAll();
		
		// logout
		loginPage.logout();

	}
	
	/**
	 * Author :Arunkumar date: 07/10/2022 TestCase Id:RPMXCON-52432
	 * Description :To verify when Reviewer try to impersonates
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-52432",enabled = true, groups = { "regression" })
	public void verifyReviewerImpersonate() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-52432");
		baseClass.stepInfo("Verify when Reviewer try to impersonate");
		userManage = new UserManagement(driver);
		//login as reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in as Reviewer");
		// verify landing page of reviewer
		baseClass.verifyUrlLanding(Input.url + "ReviewerDashboard/Index", "Respective Landing Page displayed",
				"Not displayed");
		baseClass.stepInfo("Verify impersonating as reviewer");
		userManage.verifyRevImpersonation();
		baseClass.passedStep("Impersonation not allowed for reviewer user");
		loginPage.logout();	
	}
	
	/**
	 * Author :Arunkumar date: 07/10/2022 TestCase Id:RPMXCON-52472
	 * Description :To verify user rights from Edit Profile > Functionality for Project Admin
	 * @throws Exception 
	 */
	@Test(description ="RPMXCON-52472",dataProvider = "sapa",enabled = true, groups = { "regression" })
	public void verifyUserFunctionalityForPA(String userName, String password,String role) throws Exception {
		
		baseClass.stepInfo("Test case Id: RPMXCON-52472");
		baseClass.stepInfo("verify user rights from Edit Profile > Functionality for Project Admin");
		userManage = new UserManagement(driver);
		//login as SA and verify functionality tab
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Logged in as "+role);
		baseClass.stepInfo("Apply filter for PA role");
		userManage.navigateToUsersPAge();
		userManage.verifyFunctionalityTab(Input.pa1userName,Input.ProjectAdministrator);
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 07/10/2022 TestCase Id:RPMXCON-52471
	 * Description :To verify user rights from Edit Profile > Functionality for Sys Admin role
	 * @throws Exception 
	 */
	@Test(description ="RPMXCON-52471",enabled = true, groups = { "regression" })
	public void verifyUserFunctionalityForSA() throws Exception {
		
		baseClass.stepInfo("Test case Id: RPMXCON-52471");
		baseClass.stepInfo("verify user rights from Edit Profile > Functionality for Sys Admin role");
		userManage = new UserManagement(driver);
		//login as reviewer
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");
		baseClass.verifyUrlLanding(Input.url + "User/UserListView", "landed on User list page",
				"not on user list page");
		baseClass.stepInfo("Apply filter for SA role and verify");
		userManage.verifyFunctionalityTab(Input.sa1userName,Input.SystemAdministrator);
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 10/10/2022 TestCase Id:RPMXCON-52380
	 * Description :verify after impersonation user can create new user with the impersonated and below roles
	 * @throws Exception 
	 */
	@Test(description ="RPMXCON-52380",enabled = true, groups = { "regression" })
	public void verifyUserCreationAfterImpersonation() throws Exception {
		
		baseClass.stepInfo("Test case Id: RPMXCON-52380");
		baseClass.stepInfo("verify after impersonation user can create new user with the impersonated and below roles");
		userManage = new UserManagement(driver);
		//login as SA and impersonate as PA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");
		baseClass.stepInfo("Impersonate as project admin");
		baseClass.impersonateSAtoPA();
		baseClass.stepInfo("Verify user creation popup");
		userManage.verifyAddNewUserPopup("Reviewer");
		loginPage.logout();
		//login as SA and impersonate as RMU
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");
		baseClass.stepInfo("Impersonate as Review manager");
		baseClass.impersonateSAtoRMU();
		baseClass.stepInfo("Verify user creation popup");
		userManage.verifyAddNewUserPopup("Reviewer");
		loginPage.logout();
		//login as PA and impersonate as RMU
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Impersonate as Review manager");
		baseClass.impersonatePAtoRMU();
		baseClass.stepInfo("Verify user creation popup");
		userManage.verifyAddNewUserPopup("Reviewer");
		loginPage.logout();	
	}
	
	/**
	 * Author :Arunkumar date: 10/10/2022 TestCase Id:RPMXCON-52745
	 * Description :Validate 'Recovery email address' in the Manage User UI while Adding a new User
	 * @throws Exception 
	 */
	@Test(description ="RPMXCON-52745",dataProvider = "users",enabled = true, groups = { "regression" })
	public void verifyRecoveryEmailUIForNewUser(String userName, String password,String role) throws Exception {
		
		baseClass.stepInfo("Test case Id: RPMXCON-52745");
		baseClass.stepInfo("Validate 'Recovery email address' in the Manage User UI while Adding a new User");
		userManage = new UserManagement(driver);
		String email = "QA"+Utility.dynamicNameAppender()+"@consilio.com";
		
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Logged in as "+role);
		userManage.navigateToUsersPAge();
		baseClass.stepInfo("check recovery email status while adding new user");
		userManage.verifyRecoveryEmailFieldStatus(email, Input.projectName, "new");
		userManage.addNewUserAsDifferentUsers(role, "QA", "User", Input.ReviewManager, 
				email, null, Input.projectName);
		baseClass.passedStep(role + "user not prompted any error message for Recovery E-mail address");
		loginPage.logout();
		//deleting added new user
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManage.filterByName(email);
		userManage.deleteUser();
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 11/10/2022 TestCase Id:RPMXCON-52746
	 * Description :Validate 'Recovery email address' in the Manage User UI while Editing existing User
	 * @throws Exception 
	 */
	@Test(description ="RPMXCON-52746",enabled = true, groups = { "regression" })
	public void verifyRecoveryEmailUIForExistingUser() throws Exception {
		
		baseClass.stepInfo("Test case Id: RPMXCON-52746");
		baseClass.stepInfo("Validate 'Recovery email address' in the Manage User UI while Editing existing User");
		userManage = new UserManagement(driver);
		String email = "AQA"+Utility.dynamicNameAppender()+"@consilio.com";
		String[] userName = {Input.sa1userName, Input.pa1userName,Input.rmu1userName};
		String[] password = {Input.sa1password, Input.pa1password,Input.rmu1password};
		String[] edit = {"modifiedSA","modifiedPA","modifiedRMU"};
			
		//creating new user for editing 
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManage.createNewUser("Qa","Pa", Input.ReviewManager, email, 
				null, Input.projectName);
		loginPage.logout();
		//Login as user and verify recovery email status for existing user
		for(int i=0;i<userName.length;i++) {
			loginPage.loginToSightLine(userName[i], password[i]);
			baseClass.stepInfo("Logged in as"+userName[i]);
			userManage.navigateToUsersPAge();
			baseClass.stepInfo("check recovery email status while editing existing user");
			userManage.verifyRecoveryEmailFieldStatus(email, Input.projectName, "Existing");
			userManage.editExistingUserDetail(email, Input.projectName, userManage.getLastNameEditPopup(),
					edit[i]);
			baseClass.passedStep( "PA user not prompted any error message for Recovery E-mail address");
			loginPage.logout();
		}		
		//deleting user
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManage.filterByName(email);
		userManage.deleteUser();
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
