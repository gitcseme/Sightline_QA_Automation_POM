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
			{ Input.pa1userName, Input.pa1password, "PA" },
			{Input.rmu1userName, Input.rmu1password, "RMU" }};
	}
	
	/**
	 * Author :Arunkumar date: 20/10/2022 TestCase Id:RPMXCON-52687
	 * Description :To verify that user will be displayed in 'Pending Activation User', 
	 * if his account is not activates.
	 * @throws Exception 
	 */
	@Test(description ="RPMXCON-52687",dataProvider="users",enabled = true, groups = { "regression" })
	public void verifyUserNotYetLoggedInStatus(String userName, String password,String role) throws Exception {
		
		baseClass.stepInfo("Test case Id: RPMXCON-52687");
		baseClass.stepInfo("Verify user displayed in User Not Yet Logged In");
		String email = "qa"+Utility.dynamicNameAppender()+"@consilio.com";
		String[] details = {"QA","User",email};
		loginPage.loginToSightLine(userName,password);
		baseClass.stepInfo("Logged in as "+role);
		userManage.navigateToUsersPAge();
		baseClass.stepInfo("create new user");
		userManage.addNewUserAsDifferentUsers(role, "QA", "User", Input.ReviewManager, 
				email, null, Input.projectName);
		baseClass.stepInfo("Verify user details in 'User Not Logged In' popup");
		userManage.verifyUserDetailsOnUserNotLoggedInPopup(details, email);	
		loginPage.logout();
		//deleting added new user
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		userManage.filterByName(email);
		userManage.deleteUser();
		loginPage.logout();	
	}
	
	/**
	 * Author :Arunkumar date: 20/10/2022 TestCase Id:RPMXCON-53174
	 * Description :Verify Manage Users list when "Show Inactive Users" toggle is ON
	 * @throws Exception 
	 */
	@Test(description ="RPMXCON-53174",enabled = true, groups = { "regression" })
	public void verifyInactiveUsersToggleStatus() throws Exception {
		
		baseClass.stepInfo("Test case Id: RPMXCON-53174");
		baseClass.stepInfo("Verify Manage Users list when 'Show Inactive Users' toggle is ON");
		
		//login as system admin
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");
		userManage.navigateToUsersPAge();
		driver.waitForPageToBeReady();
		//get users count before turning ON toggle
		int activeUsersCount = userManage.getUserCountInfo();
		baseClass.stepInfo("Total users count before turning ON toggle :"+activeUsersCount);
		baseClass.stepInfo("Turn ON the toggle and verify status");
		baseClass.waitForElement(userManage.getActiveInactiveBtn());
		userManage.getActiveInactiveBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		userManage.verifyInactiveUsersListDisplayedStatus();
		//get count after turning ON toggle
		int totalUsersCount = userManage.getUserCountInfo();
		baseClass.stepInfo("Total users count after turning ON toggle :"+totalUsersCount);
		if(totalUsersCount>activeUsersCount) {
			baseClass.passedStep("Users list shows inactive users along with active users");
		}
		else {
			baseClass.failedStep("inactive users list not displayed after turning on the toggle");
		}
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
