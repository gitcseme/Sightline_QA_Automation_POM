package sightline.smallComponents;

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
import pageFactory.DocListPage;
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

public class UsersAndRoleManagement_Regression23 {
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
	
	@DataProvider(name = "sadaparmu")
	public Object[][] SaDaPaRmu() {
		return new Object[][] {
				{ Input.sa1userName, Input.sa1password, Input.SystemAdministrator },
				{ Input.da1userName, Input.da1password, Input.DomainAdministrator},
				{ Input.pa1userName, Input.pa1password, Input.ProjectAdministrator },
				{ Input.rmu1userName, Input.rmu1password, Input.ReviewManager}};
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
	public void verifySecurityGroupChanges() throws Exception {
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
		baseClass.waitTime(4);
		baseClass.getSelectRole().selectFromDropdown().selectByVisibleText("Reviewer");
		baseClass.waitForElement(baseClass.getAvlDomain());
		baseClass.waitTime(4);
		baseClass.getAvlDomain().selectFromDropdown().selectByVisibleText(Input.domainName);
		baseClass.waitForElement(baseClass.getAvlProject());
		baseClass.waitTime(4);
		baseClass.getAvlProject().selectFromDropdown().selectByVisibleText(Input.projectName);
		
		// validation in change role sg
		baseClass.waitTime(4);
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
	
	/**
	 * Author : Baskar date: NA Modified date:10/10/2022 Modified by: Baskar
	 * Description :Validate DomainAdmin assigning billable/internal users at Project level
	 */

	@Test(description = "RPMXCON-53223", alwaysRun = true, groups = { "regression" })
	public void validateDaUserAssgnBillable() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-53223");
		baseClass.stepInfo("Validate DomainAdmin assigning billable/internal users at Project level");
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();
		String projectTwoName="Regression_AllDataset_Consilio2";
		String paFirstName = Input.randomText + Utility.dynamicNameAppender();
		String paLastName = Input.randomText + Utility.dynamicNameAppender();
		String emailIdPa = Input.randomText + Utility.dynamicNameAppender() + "@consilio.com";
		String rmuFirstName = Input.randomText + Utility.dynamicNameAppender();
		String rmuLastName = Input.randomText + Utility.dynamicNameAppender();
		String emailIdrmu = Input.randomText + Utility.dynamicNameAppender() + "@consilio.com";
		String revFirstName = Input.randomText + Utility.dynamicNameAppender();
		String revLastName = Input.randomText + Utility.dynamicNameAppender();
		String emailIdRev = Input.randomText + Utility.dynamicNameAppender() + "@consilio.com";

		// Login as da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.selectdomain(Input.domainName);
		
		// creating user
		this.driver.getWebDriver().get(Input.url+ "User/UserListView");
		userManage.createNewUserFromDa(paFirstName, paLastName, Input.ProjectAdministrator, emailIdPa, Input.domainName, Input.projectName);
		userManage.createNewUserFromDa(rmuFirstName, rmuLastName, Input.ReviewManager, emailIdrmu, Input.domainName, Input.projectName);
		userManage.createNewUserFromDa(revFirstName, revLastName, Input.Reviewer, emailIdRev, Input.domainName, Input.projectName);

		// assiging user as billable/non billable
		this.driver.getWebDriver().get(Input.url+ "User/UserListView");
		userManage.assignProjectBasedOnPara(projectTwoName,Input.ProjectAdministrator,false,paFirstName+" "+paLastName);
		baseClass.stepInfo("Assigning pa user as non-billable");
		userManage.assignProjectBasedOnPara(projectTwoName,Input.ReviewManager,true,rmuFirstName+" "+rmuLastName);
		baseClass.stepInfo("Assigning rmu user as billable");
		userManage.assignProjectBasedOnPara(projectTwoName,Input.Reviewer,true,revFirstName+" "+revLastName);
		baseClass.stepInfo("Assigning rev user as billable");

		// validating userlist page billable / non-billable
		this.driver.getWebDriver().get(Input.url+ "User/UserListView");
		//verify pa role
		userManage.passingUserName(emailIdPa);
		userManage.applyFilter();
		baseClass.waitTime(2);
		int indexBillable=baseClass.getIndex(userManage.getUserListHeaderIndex(), "Billable");
		String billFalse=userManage.getUserListUsingIndex(indexBillable).getText();
        System.out.println(billFalse);
        softAssertion.assertEquals(billFalse, "False");
        baseClass.passedStep("Cross checked from user list page PA role user is non-billable ");
        userManage.filterByName(emailIdPa);
		userManage.deleteUser();
        //verify rmu role
        userManage.passingUserName(emailIdrmu);
        userManage.applyFilter();
		baseClass.waitTime(2);
		int indexBillableRmu=baseClass.getIndex(userManage.getUserListHeaderIndex(), "Billable");
		String billTrueRmu=userManage.getUserListUsingIndex(indexBillableRmu).getText();
        System.out.println(billTrueRmu);
        softAssertion.assertEquals(billTrueRmu, "True");
        userManage.filterByName(emailIdrmu);
		userManage.deleteUser();
        baseClass.passedStep("Cross checked from user list page Review Manager role user is billable ");
        //verify rev role
        userManage.passingUserName(emailIdRev);
        userManage.applyFilter();
		baseClass.waitTime(2);
		int indexBillableRev=baseClass.getIndex(userManage.getUserListHeaderIndex(), "Billable");
		String billFalseRev=userManage.getUserListUsingIndex(indexBillableRev).getText();
        System.out.println(billFalseRev);
        softAssertion.assertEquals(billFalseRev, "True");
        userManage.filterByName(emailIdRev);
		userManage.deleteUser();
        baseClass.passedStep("Cross checked from user list page Reviewer role user is billable ");
		softAssertion.assertAll();
		// logout
		loginPage.logout();

	}

	
	/**
	 * Author : Baskar date: NA Modified date:10/10/2022 Modified by: Baskar
	 * Description :Validate SystemAdmin assigning billable and internal users as
	 *              PAU/RMU/Reviewer for specific project
	 */

	@Test(description = "RPMXCON-53221", alwaysRun = true, groups = { "regression" })
	public void validateSaUserAssgnBillable() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-53221");
		baseClass.stepInfo("Validate SystemAdmin assigning billable and "
				+ "internal users as PAU/RMU/Reviewer for specific project");
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();
		String projectTwoName="Regression_AllDataset_Consilio2";
		String paFirstName = Input.randomText + Utility.dynamicNameAppender();
		String paLastName = Input.randomText + Utility.dynamicNameAppender();
		String emailIdPa = Input.randomText + Utility.dynamicNameAppender() + "@consilio.com";
		String rmuFirstName = Input.randomText + Utility.dynamicNameAppender();
		String rmuLastName = Input.randomText + Utility.dynamicNameAppender();
		String emailIdrmu = Input.randomText + Utility.dynamicNameAppender() + "@consilio.com";
		String revFirstName = Input.randomText + Utility.dynamicNameAppender();
		String revLastName = Input.randomText + Utility.dynamicNameAppender();
		String emailIdRev = Input.randomText + Utility.dynamicNameAppender() + "@consilio.com";

		// Login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		
		// creating user
		this.driver.getWebDriver().get(Input.url+ "User/UserListView");
		userManage.createNewUser(paFirstName, paLastName, Input.ProjectAdministrator, emailIdPa, Input.domainName, Input.projectName);
		userManage.createNewUser(rmuFirstName, rmuLastName, Input.ReviewManager, emailIdrmu, Input.domainName, Input.projectName);
		userManage.createNewUser(revFirstName, revLastName, Input.Reviewer, emailIdRev, Input.domainName, Input.projectName);

		// assiging user as billable/non billable
		this.driver.getWebDriver().get(Input.url+ "User/UserListView");
		userManage.assignProjectBasedOnPara(projectTwoName,Input.ProjectAdministrator,true,paFirstName+" "+paLastName);
		baseClass.stepInfo("Assigning pa user as billable");
		userManage.assignProjectBasedOnPara(projectTwoName,Input.ReviewManager,true,rmuFirstName+" "+rmuLastName);
		baseClass.stepInfo("Assigning rmu user as billable");
		userManage.assignProjectBasedOnPara(projectTwoName,Input.Reviewer,true,revFirstName+" "+revLastName);
		baseClass.stepInfo("Assigning rev user as billable");

		// validating userlist page billable / non-billable
		this.driver.getWebDriver().get(Input.url+ "User/UserListView");
		driver.waitForPageToBeReady();
		//verify pa role
		userManage.passingUserName(emailIdPa);
		userManage.applyFilter();
		baseClass.waitTime(2);
		int indexBillable=baseClass.getIndex(userManage.getUserListHeaderIndex(), "Billable");
		String billFalse=userManage.getUserListUsingIndex(indexBillable).getText();
        System.out.println(billFalse);
        softAssertion.assertEquals(billFalse, "True");
        userManage.filterByName(emailIdPa);
		userManage.deleteUser();
        baseClass.passedStep("Cross checked from user list page PA role user is billable ");
        //verify rmu role
        userManage.passingUserName(emailIdrmu);
        userManage.applyFilter();
		baseClass.waitTime(2);
		int indexBillableRmu=baseClass.getIndex(userManage.getUserListHeaderIndex(), "Billable");
		String billTrueRmu=userManage.getUserListUsingIndex(indexBillableRmu).getText();
        System.out.println(billTrueRmu);
        softAssertion.assertEquals(billTrueRmu, "True");
        userManage.filterByName(emailIdrmu);
		userManage.deleteUser();
        baseClass.passedStep("Cross checked from user list page Review Manager role user is billable ");
        //verify rev role
        userManage.passingUserName(emailIdRev);
        userManage.applyFilter();
		baseClass.waitTime(2);
		int indexBillableRev=baseClass.getIndex(userManage.getUserListHeaderIndex(), "Billable");
		String billFalseRev=userManage.getUserListUsingIndex(indexBillableRev).getText();
        System.out.println(billFalseRev);
        softAssertion.assertEquals(billFalseRev, "True");
        userManage.filterByName(emailIdRev);
		userManage.deleteUser();
        baseClass.passedStep("Cross checked from user list page Reviewer role user is billable ");
		softAssertion.assertAll();
		// logout
		loginPage.logout();

	}

	/**
	 * @Author : Jeevitha
	 * @Description :Verify user should not be created under different security
	 *              group with different role [RPMXCON-52665]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-52665", enabled = true, groups = { "regression" })
	public void verifyUserNotAbleToAddDiffSgAndDiffRole() throws Exception {
		String securityGrp = "securityGroup" + Utility.dynamicNameAppender();

		userManage = new UserManagement(driver);
		security = new SecurityGroupsPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-52665  Users and Role Management");
		baseClass.stepInfo("Verify user should not be created under different security group with different role");

		// login as Project Admin
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		String currentUser = loginPage.getCurrentUserName();
		String[] userName = currentUser.split(" ");

		// Create Security group
		security.navigateToSecurityGropusPageURL();
		security.AddSecurityGroup(securityGrp);

		// logout
		loginPage.logout();

		// login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");

		// Add Existing User With Different Security Group
		baseClass.verifyUrlLanding(Input.url + "User/UserListView", "landed on User list page",
				"not on user list page");
		userManage.validateErrorMsgForNewUser(userName[0], userName[1], Input.ReviewManager, Input.pa1userName, "",
				Input.projectName, securityGrp, false);

		// verify Error Message
		String expectedErrorMsg = "20001000023 : The specified user cannot be added, since an identical user already exists in the project, but in a different role.";
		baseClass.VerifyErrorMessage(expectedErrorMsg);

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Jeevitha
	 * @Description :Impersonation from SA to RMU/Review and then switch to a
	 *              different project [RPMXCON-53270]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53270", enabled = true, groups = { "regression" })
	public void verifyImperFromSAToDifferentProject() throws Exception {
		String securityGrp1 = "securityGroup" + Utility.dynamicNameAppender();
		String securityGrp2 = "securityGroup" + Utility.dynamicNameAppender();

		userManage = new UserManagement(driver);
		security = new SecurityGroupsPage(driver);
		sessionSearch = new SessionSearch(driver);
		DocListPage doclist = new DocListPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-53270  Users and Role Management");
		baseClass.stepInfo("Impersonation from SA to RMU/Review and then switch to a different project");

		// login as Project Admin
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");

		// PRE-REQUISITE
		// Create Security group
		security.navigateToSecurityGropusPageURL();
		security.AddSecurityGroup(securityGrp1);
		security.AddSecurityGroup(securityGrp2);

		// Release Docs To SG
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();
		doclist.Selectpagelength("500");
		doclist.sortColumn(true, Input.documentKey, true);
		doclist.selectAllDocumentsInCurrentPageOnly();
		doclist.docListToBulkRelease(securityGrp2);

		doclist.sortColumn(true, Input.documentKey, false);
		doclist.selectAllDocumentsInCurrentPageOnly();
		doclist.docListToBulkRelease(securityGrp1);
		
		// logout
		loginPage.logout();

		// login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");

		// impersonate From SA to REV & verify Current User
		baseClass.rolesToImp("SA", "RMU");
		driver.waitForPageToBeReady();
		loginPage.getSignoutMenu().waitAndClick(10);
		baseClass.waitForElement(baseClass.getLoginedUserRole());
		String currentUser = baseClass.getLoginedUserRole().getText();
		System.out.println(currentUser);
		baseClass.textCompareEquals(currentUser, Input.ReviewManager, "Current Role is RMU",
				"Current role is not as expected");

		// verify DOC count in Project1 For RMU
		sessionSearch.navigateToSessionSearchPageURL();
		baseClass.stepInfo("No of Doc's in Project1");
		sessionSearch.verifyNoOfDocsInProject();

		// select project2
		baseClass.selectproject(Input.additionalDataProject);
		baseClass.stepInfo("Selected Project2");

		// verify Doc count in Project2 & purehit count for RMU
		baseClass.stepInfo("No of Doc's in Project1");
		sessionSearch.navigateToSessionSearchPageURL();
		int projectDocCountP2 = sessionSearch.verifyNoOfDocsInProject();
		int purehitP2 = sessionSearch.basicContentSearch(Input.searchStringStar);
		baseClass.digitCompareEquals(projectDocCountP2, purehitP2, "Expected DocCount is Available",
				"Expected DocCount is NOt Available");

		// impersonate From RMU to SA
		baseClass.rolesToImp("RMU", "SA");

		// impersonate From SA to REV
		baseClass.rolesToImp("SA", "REV");

		// verify Current user role
		driver.waitForPageToBeReady();
		loginPage.getSignoutMenu().waitAndClick(10);
		baseClass.waitForElement(baseClass.getLoginedUserRole());
		String currentUser2 = baseClass.getLoginedUserRole().getText();
		System.out.println(currentUser2);
		baseClass.textCompareEquals(currentUser2, Input.Reviewer, "Current Role is RMU",
				"Current role is not as expected");

		// verify DOC count in Project1 For REV
		sessionSearch.navigateToSessionSearchPageURL();
		baseClass.stepInfo("No of Doc's in Project1");
		sessionSearch.verifyNoOfDocsInProject();

		// select project2
		baseClass.selectproject(Input.additionalDataProject);
		baseClass.stepInfo("Selected Project2");

		// verify Doc count in Project2 & purehit count For REV
		sessionSearch.navigateToSessionSearchPageURL();
		int projectDocCountRevP2 = sessionSearch.verifyNoOfDocsInProject();
		int purehitRevP2 = sessionSearch.basicContentSearch(Input.searchStringStar);
		baseClass.digitCompareEquals(projectDocCountRevP2, purehitRevP2, "Expected DocCount is Available",
				"Expected DocCount is NOt Available");

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Jeevitha
	 * @Description : To verify when SysAdmin clicks to Delete user from user list
	 *              and cancels user deletion by clicking ‘Cancel’ button.
	 *              [RPMXCON-52401]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-52401", enabled = true, groups = { "regression" })
	public void verifyWhenUserClickDeleteUser() throws Exception {
		String firstName = Input.randomText + Utility.dynamicNameAppender();
		String lastName = Input.randomText + Utility.dynamicNameAppender();
		String role = Input.ProjectAdministrator;
		String emailId = Input.randomText + Utility.dynamicNameAppender() + "@consilio.com";

		userManage = new UserManagement(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-52401  Users and Role Management");
		baseClass.stepInfo(
				"To verify when SysAdmin clicks to Delete user from user list and cancels user deletion by clicking ‘Cancel’ button.");

		// login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");

		//Create USER
		baseClass.verifyUrlLanding(Input.url + "User/UserListView", "landed on User list page",
				"not on user list page");
		baseClass.stepInfo("Create new user for domain administration");
		userManage.createNewUser(firstName, lastName, role, emailId, " ", Input.projectName);

		//Click Delete Button And click Cancel In POpup
		driver.Navigate().refresh();
		userManage.passingUserName(emailId);
		userManage.applyFilter();
		userManage.verifyDeleteUserPopup(false, Input.projectName);

		// verify Element Present in Page
		driver.waitForPageToBeReady();
		baseClass.ValidateElement_Presence(userManage.getSelectUserToDelete(Input.projectName),
				"Created User is Present");

		//Click Delete Button And click OK In POpup
		userManage.verifyDeleteUserPopup(true, Input.projectName);
		userManage.applyFilter();
		
		// verify Element Absence in Page
		baseClass.ValidateElement_Absence(userManage.getSelectUserToDelete(Input.projectName),
				"Created User is Not Available");

		// logout
		loginPage.logout();
	}
	
	/**
	 * Author : Baskar date: NA Modified date:12/10/2022 Modified by: Baskar
	 * Description :Verify that after impersonation as Project Admin user can see
	 * "Is Locked" on edit user pop up
	 */

	@Test(description = "RPMXCON-52718", alwaysRun = true, groups = { "regression" })
	public void validateIsLockedPresentSaImpPa() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52718");
		baseClass.stepInfo(
				"Verify that after impersonation as Project Admin user can see \"Is Locked\" on edit user pop up");
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();
		sessionSearch = new SessionSearch(driver);
		security = new SecurityGroupsPage(driver);

		// Login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);

		// impersonate sa to pa
		baseClass.impersonateSAtoPA();

		// validate islocked checkbox is available
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		userManage.passingUserName(Input.pa1userName);
		userManage.applyFilter();
		userManage.editLoginUser();
		boolean ischecked = userManage.getIsLOckedCheckBox().isElementAvailable(2);
		softAssertion.assertTrue(ischecked);
		baseClass.passedStep(
				"Is locked checkbox displayed on edit user popup " + "window when user impersonate from sa to pa role");
		softAssertion.assertAll();
		// logout
		loginPage.logout();

	}

	/**
	 * Author : Baskar date: NA Modified date:12/10/2022 Modified by: Baskar
	 * Description :Verify that when "Is Locked" is unchecked from edit user pop up
	 * user should login successfully
	 */

	@Test(description = "RPMXCON-52717", alwaysRun = true, groups = { "regression" })
	public void validateAfterUncheckIsLockedUserCanLogin() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52717");
		baseClass.stepInfo("Verify that when \"Is Locked\" is "
				+ "unchecked from edit user pop up user should login successfully");
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();
		sessionSearch = new SessionSearch(driver);
		security = new SecurityGroupsPage(driver);

		// Login as pa
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		// as per pre-Prerequisites
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		userManage.passingUserName(Input.rmu1userName);
		userManage.applyFilter();
		userManage.editLoginUser();
		userManage.getIsLOckedCheckBox().waitAndClick(5);
		baseClass.waitForElement(userManage.getSubmit());
		userManage.getSubmit().waitAndClick(10);
		baseClass.stepInfo("locking the user in edit user popup window");

		// logout
		loginPage.logout();

		// Login as rmu to verify user login
		loginPage.loginToSightLineVerifyLockedUser(Input.rmu1userName, Input.rmu1password);
		boolean loginLocked = userManage.getLoginLocked().isElementAvailable(2);
		softAssertion.assertTrue(loginLocked);
		baseClass.passedStep("user cannot able to login to application when account locked");

		// Login as pa
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		userManage.passingUserName(Input.rmu1userName);
		userManage.applyFilter();
		userManage.editLoginUser();
		userManage.getIsLOckedCheckBox().waitAndClick(5);
		baseClass.waitForElement(userManage.getSubmit());
		userManage.getSubmit().waitAndClick(10);
		baseClass.stepInfo("unlocking the user in edit user popup window");

		// logout
		loginPage.logout();

		// Login as rmu again
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.passedStep("User can able to login to application after un-locking from edit user popup window");
		softAssertion.assertAll();
		// logout
		loginPage.logout();

	}
	
	/**
	 * Author :Arunkumar date: 12/10/2022 TestCase Id:RPMXCON-52425
	 * Description :To verify when assigns user to different project with same role of existing project
	 * @throws Exception 
	 */
	@Test(description ="RPMXCON-52425",enabled = true, groups = { "regression" })
	public void verifyAssigningSameRoleUser() throws Exception {
		
		baseClass.stepInfo("Test case Id: RPMXCON-52425");
		baseClass.stepInfo("verify when assigns user to different project with same role of existing project");
		userManage = new UserManagement(driver);
		String email ="AQA"+Utility.dynamicNameAppender()+"@consilio.com";
		String firstName = "QA";
		String lastName = "RMU"+ Utility.dynamicNameAppender();;
		String userName = firstName+" "+lastName;
		
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");
		baseClass.stepInfo("Add  RMU user to  project  and security group");
		userManage.createNewUser(firstName,lastName, Input.ReviewManager, email, 
					null, Input.projectName);
		baseClass.stepInfo("Assign same RMU user to different project and security group");
		userManage.AssignUserToProject(Input.additionalDataProject, Input.ReviewManager, userName);
		baseClass.passedStep("User able to assign same RMU user role to different project");
		loginPage.logout();
		//deleting user
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManage.filterByName(email);
		userManage.deleteUser();
		loginPage.logout();
	
	}
	
	/**
	 * Author :Arunkumar date: 12/10/2022 TestCase Id:RPMXCON-53211
	 * Description :Verify that error message should be displayed when adding existing non-billable
	 *  user as non-billable user under the same project with same/different role
	 * @throws Exception 
	 */
	@Test(description ="RPMXCON-53211",enabled = true, groups = { "regression" })
	public void verifyAddingExistingUserUnderSameProject() throws Exception {
		
		baseClass.stepInfo("Test case Id: RPMXCON-53211");
		baseClass.stepInfo("verify error message when adding existing user under the same project");
		userManage = new UserManagement(driver);
		String[] userName = {Input.da1userName, Input.pa1userName,Input.rmu1userName};
		String[] password = {Input.da1password, Input.pa1password,Input.rmu1password};
		String[] role = {Input.DomainAdministrator,Input.ProjectAdministrator,Input.ReviewManager};
		
		for(int i=0;i<role.length;i++) {
			loginPage.loginToSightLine(userName[i], password[i]);
			baseClass.stepInfo("Logged in as "+role[i]);
			userManage.navigateToUsersPAge();
			baseClass.stepInfo("Add existing user under the same project");
			userManage.verifyErrorMsgForCreatingExistedUser("QA", "user", role[i], 
					userName[i], null, Input.projectName);
			baseClass.passedStep("Error message displayed when adding existing user as user:"+role[i]);
			loginPage.logout();
		}
		
	}
	
	/**
	 * Author :Arunkumar date: 12/10/2022 TestCase Id:RPMXCON-53252
	 * Description :Verify that attorney profile should be displayed only for an RMU user role 
	 * when adding/editing the user with RMU role
	 * @throws Exception 
	 */
	@Test(description ="RPMXCON-53252",dataProvider="sadaparmu",enabled = true, groups = { "regression" })
	public void verifyAttorneyProfileOption(String userName,String password,String role) throws Exception {
		
		baseClass.stepInfo("Test case Id: RPMXCON-53252");
		baseClass.stepInfo("Verify that attorney profile should be displayed only for an RMU user");
		userManage = new UserManagement(driver);
		
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Logged in as:"+role);
		userManage.navigateToUsersPAge();
		//verifying the attorney profile for new and existing user
		baseClass.stepInfo("Verify attorney profile for new user role"+role);
		userManage.verifyAttorneyProfileForNewOrExistingUser("QA","user",null,role,"new");
		baseClass.stepInfo("Verify attorney profile for existing user role"+role);
		userManage.verifyAttorneyProfileForNewOrExistingUser(null,null,userName,role,"Existing");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 12/10/2022 TestCase Id:RPMXCON-53200
	 * Description :Verify that on Edit user pop up an option to indicate whether the user is
	 *  Consilio Internal or Client user (billable/not-billable) should be provided
	 * @throws Exception 
	 */
	@Test(description ="RPMXCON-53200",enabled = true, groups = { "regression" })
	public void verifyBillableOptionOnEditUserPopup() throws Exception {
		
		baseClass.stepInfo("Test case Id: RPMXCON-53200");
		baseClass.stepInfo("Validate billable/not-billable option in edit user popup");
		userManage = new UserManagement(driver);
		String[] user = {Input.pa1userName,Input.rmu1userName,Input.rev1userName,Input.da1userName};
		
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");
		baseClass.stepInfo("edit the user and check for option");
		for(int i=0;i<user.length;i++) {
			userManage.navigateToUsersPAge();
			userManage.verifyBillableUserCheckBoxStatus(user[i], "existing");
		}
		loginPage.logout();
	}

	/**
	 * @Author : Jeevitha
	 * @Description :Impersonation from DAU to RMU/Review and then switch to a
	 *              different project [RPMXCON-53271]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53271", enabled = true, groups = { "regression" })
	public void verifyImperFromDAToDifferentProject() throws Exception {
		String securityGrp1 = "securityGroup" + Utility.dynamicNameAppender();
		String securityGrp2 = "securityGroup" + Utility.dynamicNameAppender();

		userManage = new UserManagement(driver);
		security = new SecurityGroupsPage(driver);
		sessionSearch = new SessionSearch(driver);
		DocListPage doclist = new DocListPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-53271  Users and Role Management");
		baseClass.stepInfo("Impersonation from DAU to RMU/Review and then switch to a different project");

		// login as Project Admin
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");

		// PRE-REQUISITE
		// Create Security group
		security.navigateToSecurityGropusPageURL();
		security.AddSecurityGroup(securityGrp1);
		security.AddSecurityGroup(securityGrp2);

		// Release Docs To SG
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();
		doclist.Selectpagelength("500");
		doclist.sortColumn(true, Input.documentKey, true);
		doclist.selectAllDocumentsInCurrentPageOnly();
		doclist.docListToBulkRelease(securityGrp2);

		doclist.sortColumn(true, Input.documentKey, false);
		doclist.selectAllDocumentsInCurrentPageOnly();
		doclist.docListToBulkRelease(securityGrp1);
		
		// logout
		loginPage.logout();

		// login As DA
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Logged in as DA");

		// impersonate From DA to RMU & verify Current User
		baseClass.performImpersonation(Input.ReviewManager, Input.AutomationBackUpDomain, Input.largeVolDataProject,
				Input.securityGroup);

		driver.waitForPageToBeReady();
		loginPage.getSignoutMenu().waitAndClick(10);
		baseClass.waitForElement(baseClass.getLoginedUserRole());
		String currentUser = baseClass.getLoginedUserRole().getText();
		baseClass.textCompareEquals(currentUser, Input.ReviewManager, "Current Role is RMU",
				"Current role is not as expected");

		// verify DOC count in Project1 For RMU
		sessionSearch.navigateToSessionSearchPageURL();
		baseClass.stepInfo("No of Doc's in Project1");
		sessionSearch.verifyNoOfDocsInProject();

		// select project2
		baseClass.selectproject(Input.additionalDataProject);
		baseClass.stepInfo("Selected Project2");

		// verify Doc count in Project2 & purehit count for RMU
		baseClass.stepInfo("No of Doc's in Project1");
		sessionSearch.navigateToSessionSearchPageURL();
		int projectDocCountP2 = sessionSearch.verifyNoOfDocsInProject();
		int purehitP2 = sessionSearch.basicContentSearch(Input.searchStringStar);
		baseClass.digitCompareEquals(projectDocCountP2, purehitP2, "Expected DocCount is Available",
				"Expected DocCount is NOt Available");

		// impersonate From RMU to DA
		baseClass.performImpersonation(Input.DomainAdministrator, Input.AutomationBackUpDomain, "", "");

		// impersonate From DA to REV
		baseClass.performImpersonation(Input.Reviewer, Input.AutomationBackUpDomain, Input.largeVolDataProject,
				Input.securityGroup);

		// verify Current user role
		driver.waitForPageToBeReady();
		loginPage.getSignoutMenu().waitAndClick(10);
		baseClass.waitForElement(baseClass.getLoginedUserRole());
		String currentUser2 = baseClass.getLoginedUserRole().getText();
		baseClass.textCompareEquals(currentUser2, Input.Reviewer, "Current Role is RMU",
				"Current role is not as expected");

		// verify DOC count in Project1 For REV
		sessionSearch.navigateToSessionSearchPageURL();
		baseClass.stepInfo("No of Doc's in Project1");
		sessionSearch.verifyNoOfDocsInProject();

		// select project2
		baseClass.selectproject(Input.additionalDataProject);
		baseClass.stepInfo("Selected Project2");

		// verify Doc count in Project2 & purehit count For REV
		sessionSearch.navigateToSessionSearchPageURL();
		int projectDocCountRevP2 = sessionSearch.verifyNoOfDocsInProject();
		int purehitRevP2 = sessionSearch.basicContentSearch(Input.searchStringStar);
		baseClass.digitCompareEquals(projectDocCountRevP2, purehitRevP2, "Expected DocCount is Available",
				"Expected DocCount is NOt Available");

		// logout
		loginPage.logout();

		// login as Project Admin
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Delete SG
		security.deleteSecurityGroups(securityGrp1);
		security.deleteSecurityGroups(securityGrp2);

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Jeevitha
	 * @Description : To verify user rights from Edit Profile > Functionality for
	 *              Reviewer [RPMXCON-52474]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-52474", dataProvider = "sapa", enabled = true, groups = { "regression" })
	public void verifyFunctionalityForReviewer(String username, String password, String userRole) throws Exception {
		String[] checkedCb = { "Searching", "Download Native", "Highlighting", "Redactions", "Reviewer Remarks",
				"Analytics Panels" };
		String[] disabledCB = { "Manage", "Manage Domain Projects", "Ingestions", "Productions", "Datasets",
				"All Reports" };
		String[] uncheckedCB = { "Concept Explorer", "Communications Explorer" };
		userManage = new UserManagement(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-52474  Users and Role Management");
		baseClass.stepInfo("To verify user rights from Edit Profile > Functionality for Reviewer");

		// login as User
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged in as :" + userRole);

		// navigate to userpage
		userManage.navigateToUsersPAge();

		// filter Reviewer
		userManage.filterTheRole(Input.Reviewer);
		userManage.navigateToFunctionTab(Input.rev1userName, userRole, Input.projectName, true);

		// verify checkbox of functionality popup
		userManage.verifyCheckBoxStatus(checkedCb, disabledCB, uncheckedCB, true, true, true);

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Jeevitha
	 * @Description : Verify that error message should be displayed when adding
	 *              existing billable user as billable user under the same project
	 *              with same/different role [RPMXCON-53210]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53210", enabled = true, groups = { "regression" })
	public void verifyErrorMsgForAddingSameBillableUser() throws Exception {
		String firstName = Input.randomText + Utility.dynamicNameAppender();
		String lastName = Input.randomText + Utility.dynamicNameAppender();
		String role = Input.ProjectAdministrator;
		String emailId = Input.randomText + Utility.dynamicNameAppender() + "@consilio.com";
		String expectedErrorMsg = "20001000027 : The specified user cannot be added, since an identical user with the same role already exists in the system.";
		userManage = new UserManagement(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-53210  Users and Role Management");
		baseClass.stepInfo(
				"Verify that error message should be displayed when adding existing billable user as billable user under the same project with same/different role");

		// login As User
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Logged in as DA");
		baseClass.selectdomain(Input.domainName);

		// Create USER
		userManage.navigateToUsersPAge();
		userManage.openAddNewUserPopUp();
		userManage.getProjectBillableCheckBox().waitAndClick(10);
		userManage.createNewUser(firstName, lastName, role, emailId, " ", Input.projectName);
		baseClass.passedStep("Created Billable User");

		// Add Same User with billable & same project
		driver.Navigate().refresh();
		userManage.openAddNewUserPopUp();
		userManage.getProjectBillableCheckBox().waitAndClick(10);
		userManage.addNewUserWithoutVerifySuccesMsg(firstName, lastName, role, emailId, " ", Input.projectName);
		baseClass.VerifyErrorMessage(expectedErrorMsg);

		
		// Delete Created User
		driver.Navigate().refresh();
		userManage.passingUserName(emailId);
		userManage.applyFilter();
		userManage.verifyDeleteUserPopup(true, Input.projectName);

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Jeevitha
	 * @Description : To verify user rights from Edit Profile > Functionality for
	 *              RMU [RPMXCON-52473]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-52473", dataProvider = "sapa", enabled = true, groups = { "regression" })
	public void verifyFunctionalityForRMU(String username, String password, String userRole) throws Exception {
		String[] checkedCb = { "Searching", "Download Native", "Highlighting", "Redactions", "Reviewer Remarks",
				"Analytics Panels", "Manage", "Productions", "All Reports", "Concept Explorer",
				"Communications Explorer", "Categorize" };
		String[] disabledCB = { "Manage Domain Projects", "Ingestions" };
		String[] uncheckedCB = { "Datasets" };

		userManage = new UserManagement(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-52473  Users and Role Management");
		baseClass.stepInfo("To verify user rights from Edit Profile > Functionality for RMU");

		// login as User
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged in as :" + userRole);

		// navigate to userpage
		userManage.navigateToUsersPAge();

		// filter Review Manager
		baseClass.stepInfo("Filter Role for Review Manager");
		userManage.filterTheRole(Input.ReviewManager);
		userManage.navigateToFunctionTab(Input.rmu1userName, userRole, Input.projectName, true);

		// verify checkbox of functionality popup
		userManage.verifyCheckBoxStatus(checkedCb, disabledCB, uncheckedCB, true, true, true);

		// logout
		loginPage.logout();
	}
	
	/**
	 * Author : Baskar date: NA Modified date:14/10/2022 Modified by: Baskar
	 * Description :To verify that application should not allow to create existing
	 * user under the same project who is able to login to application and not
	 * confirmed the account.
	 */

	@DataProvider(name = "saparmu")
	public Object[][] saparmu() {
		return new Object[][] {
				{ Input.sa1userName, Input.sa1password, Input.ProjectAdministrator, Input.pa1userName, "pa" },
				{ Input.sa1userName, Input.sa1password, Input.ReviewManager, Input.rmu1userName, "rmu" } };
	}

	@Test(description = "RPMXCON-52393", dataProvider = "saparmu", alwaysRun = true, groups = { "regression" })
	public void verifyLoginUserShouldNotCreateNewUser(String userName, String password, String role, String email,
			String name) throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-52393");
		baseClass.stepInfo("To verify that application should not allow to create existing user "
				+ "under the same project who is able to login to application and not confirmed the account.");
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();
		String FirstName = Input.randomText + Utility.dynamicNameAppender();
		String LastName = Input.randomText + Utility.dynamicNameAppender();
		String emailId = Input.randomText + Utility.dynamicNameAppender() + "@consilio.com";

		// Login as sa
		loginPage.loginToSightLine(userName, password);

		// creating user with existing after set password
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		baseClass.stepInfo("Create user under existing emailid");
		if (name == "pa") {
			userManage.createNewUser(FirstName, LastName, role, email, Input.domainName, Input.projectName);
			boolean errorMsgExt = userManage.getLoginUserShouldNotCreate().isElementAvailable(2);
			String errorText = userManage.getLoginUserShouldNotCreate().getText();
            baseClass.stepInfo("Error message displayed as :"+errorText+"");
			softAssertion.assertTrue(errorMsgExt);
		}
		if (name == "rmu") {
			userManage.createNewUser(FirstName, LastName, role, email, Input.domainName, Input.projectName);
			boolean errorMsgExt = userManage.getLoginUserShouldNotCreateRmu().isElementAvailable(2);
			String errorText = userManage.getLoginUserShouldNotCreateRmu().getText();
            baseClass.stepInfo("Error message displayed as :"+errorText+"");
			softAssertion.assertTrue(errorMsgExt);
		}
		baseClass.passedStep("Error message displayed when user create existing emailid");
		userManage.createNewUser(FirstName, LastName, role, emailId, Input.domainName, Input.projectName);
		baseClass.CloseSuccessMsgpopup();
		baseClass.stepInfo("Create user under existing emailid,without reset password from unnique encrypted link");
		if (name == "pa") {
			userManage.createNewUser(FirstName, LastName, role, emailId, Input.domainName, Input.projectName);
			boolean errorMsgExt = userManage.getLoginUserShouldNotCreate().isElementAvailable(2);
			String errorText = userManage.getLoginUserShouldNotCreate().getText();
            baseClass.stepInfo("Error message displayed as :"+errorText+"");
			softAssertion.assertTrue(errorMsgExt);
		}
		if (name == "rmu") {
			userManage.createNewUser(FirstName, LastName, role, emailId, Input.domainName, Input.projectName);
			boolean errorMsgExt = userManage.getLoginUserShouldNotCreateRmu().isElementAvailable(2);
			String errorText = userManage.getLoginUserShouldNotCreateRmu().getText();
            baseClass.stepInfo("Error message displayed as :"+errorText+"");
			softAssertion.assertTrue(errorMsgExt);
		}
		baseClass.passedStep(
				"Error message displayed when user create existing emailid without resetting the password from emailid");
		softAssertion.assertAll();
		// logout
		loginPage.logout();

		
	}

	/**
	 * @Author : Baskar
	 * @Description :To verify when Sys Admin impersonate Reviewer role
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-52428", enabled = true, groups = { "regression" })
	public void verifySaImpRevValidateProjectHeader() throws Exception {
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();
		baseClass.stepInfo("Test case Id: RPMXCON-52428  Users and Role Management");
		baseClass.stepInfo("To verify when Sys Admin impersonate Reviewer role");

		// login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");
		loginPage.getSignoutMenu().waitAndClick(10);
		baseClass.waitForElement(baseClass.getLoginedUserRole());
		String currentUserSa = baseClass.getLoginedUserRole().getText();
		loginPage.getSignoutMenu().waitAndClick(10);
		System.out.println(currentUserSa);
		baseClass.textCompareEquals(currentUserSa, Input.SystemAdministrator, "Current Role is Sa",
				"Current role is not as expected");

		// impersonate From SA to REV & verify Current User
		baseClass.impersonateSAtoReviewer();
		driver.waitForPageToBeReady();
		loginPage.getSignoutMenu().waitAndClick(10);
		baseClass.waitForElement(baseClass.getLoginedUserRole());
		String currentUser = baseClass.getLoginedUserRole().getText();
		System.out.println(currentUser);
		baseClass.textCompareEquals(currentUser, Input.Reviewer, "Current Role is Reviewer",
				"Current role is not as expected");

		// validating project as per impersonation
		String project = baseClass.getProjectNames().getText().trim();
		System.out.println(project);
		softAssertion.assertEquals(project, Input.projectName);
		softAssertion.assertAll();
		baseClass.passedStep("Current project value selected in project header as per impersonation done");

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar
	 * @Description :To verify when Project Admin impersonate RMU role
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-52429", enabled = true, groups = { "regression" })
	public void verifyPaImpRmuValidateForCurrentSession() throws Exception {
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();
		baseClass.stepInfo("Test case Id: RPMXCON-52429  Users and Role Management");
		baseClass.stepInfo("To verify when Project Admin impersonate RMU role");

		// login As pa
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		loginPage.getSignoutMenu().waitAndClick(10);
		baseClass.waitForElement(baseClass.getLoginedUserRole());
		String currentUserSa = baseClass.getLoginedUserRole().getText();
		loginPage.getSignoutMenu().waitAndClick(10);
		System.out.println(currentUserSa);
		baseClass.textCompareEquals(currentUserSa, Input.ProjectAdministrator, "Current Role is PA",
				"Current role is not as expected");

		// impersonate From PA to RMU & verify Current User
		baseClass.impersonatePAtoRMU();
		driver.waitForPageToBeReady();
		loginPage.getSignoutMenu().waitAndClick(10);
		baseClass.waitForElement(baseClass.getLoginedUserRole());
		String currentUser = baseClass.getLoginedUserRole().getText();
		System.out.println(currentUser);
		baseClass.textCompareEquals(currentUser, Input.ReviewManager, "Current Role is Review Manager",
				"Current role is not as expected");

		// logout
		loginPage.logout();

		// Re-login to validate
		// login As pa
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Relogin again to validate the impersonation done for current session");

		// validating role for current session
		driver.waitForPageToBeReady();
		loginPage.getSignoutMenu().waitAndClick(10);
		baseClass.waitForElement(baseClass.getLoginedUserRole());
		String reloginRole = baseClass.getLoginedUserRole().getText();
		softAssertion.assertEquals(reloginRole, Input.ProjectAdministrator);
		softAssertion.assertAll();
		baseClass.passedStep("Role happened for the current session only when impersonation done");

		// logout
		loginPage.logout();
	}

	/**
	 * @Author : Baskar
	 * @Description :To verify when Project Admin impersonate Reviewer role
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-52430", enabled = true, groups = { "regression" })
	public void verifyPaImpRevValidateForCurrentSession() throws Exception {
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();
		baseClass.stepInfo("Test case Id: RPMXCON-52430  Users and Role Management");
		baseClass.stepInfo("To verify when Project Admin impersonate Reviewer role");

		// login As pa
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		loginPage.getSignoutMenu().waitAndClick(10);
		baseClass.waitForElement(baseClass.getLoginedUserRole());
		String currentUserSa = baseClass.getLoginedUserRole().getText();
		loginPage.getSignoutMenu().waitAndClick(10);
		System.out.println(currentUserSa);
		baseClass.textCompareEquals(currentUserSa, Input.ProjectAdministrator, "Current Role is PA",
				"Current role is not as expected");

		// impersonate From PA to Rev & verify Current User
		baseClass.impersonatePAtoReviewer();
		driver.waitForPageToBeReady();
		loginPage.getSignoutMenu().waitAndClick(10);
		baseClass.waitForElement(baseClass.getLoginedUserRole());
		String currentUser = baseClass.getLoginedUserRole().getText();
		System.out.println(currentUser);
		baseClass.textCompareEquals(currentUser, Input.Reviewer, "Current Role is Reviewer",
				"Current role is not as expected");

		// logout
		loginPage.logout();

		// Re-login to validate
		// login As pa
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Relogin again to validate the impersonation done for current session");

		// validating role for current session
		driver.waitForPageToBeReady();
		loginPage.getSignoutMenu().waitAndClick(10);
		baseClass.waitForElement(baseClass.getLoginedUserRole());
		String reloginRole = baseClass.getLoginedUserRole().getText();
		softAssertion.assertEquals(reloginRole, Input.ProjectAdministrator);
		softAssertion.assertAll();
		baseClass.passedStep("Role happened for the current session only when impersonation done");

		// logout
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
