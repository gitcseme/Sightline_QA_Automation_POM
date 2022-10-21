package testScriptsRegressionSprint24;

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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.ManageAssignment;
import pageFactory.MiniDocListPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class UserAndRoleManagement_Regression24 {

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
//			{ Input.sa1userName, Input.sa1password, "SA" },
				{ Input.pa1userName, Input.pa1password, "PA" } };
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
		String[] checkedCbPa = { Input.Searching, Input.DownloadNative, Input.Highlighting, Input.Redactions,
				Input.ReviewerRemarks, Input.AnalyticsPanels, Input.Manage, Input.Productions, Input.AllReports,
				Input.ConceptExplorer, Input.CommunicationsExplorer, Input.Categorize, Input.Datasets };
		String[] disabledCBPa = { Input.ManageDomainProjects };
		String[] uncheckedCBPa = { Input.ingestion };

		String[] checkedCb = { Input.Searching, Input.DownloadNative, Input.Highlighting, Input.Redactions,
				Input.ReviewerRemarks, Input.AnalyticsPanels, Input.Manage, Input.Productions, Input.AllReports,
				Input.ConceptExplorer, Input.CommunicationsExplorer, Input.Categorize };
		String[] disabledCB = { Input.ManageDomainProjects, Input.ingestion };
		String[] uncheckedCB = { Input.Datasets };

		String[] checkedCbRev = { Input.Searching, Input.DownloadNative, Input.Highlighting, Input.Redactions,
				Input.ReviewerRemarks, Input.AnalyticsPanels };
		String[] disabledCBRev = { Input.Manage, Input.ManageDomainProjects, Input.ingestion, Input.Productions,
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
