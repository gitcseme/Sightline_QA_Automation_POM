package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DomainManagement_IndiumRegression {
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Utility utility;
	UserManagement userManage;
	
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
	 * Author : Baskar date: NA Modified date:21/01/2021 Modified by: Baskar
	 * Description :Verify that System Admin can create user with Domain Admin role
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 1)
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
		loginPage.logout();

	}
	/**
	 * Author : Aathith date: NA Modified date: Modified by: 
	 * Description :Verify when Domain Admin impersonates as PA in domain project and then changes the project from header drop down
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 2)
	public void verifyDaImpersateAndChangeProject() throws Exception {
		baseClass = new BaseClass(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-53266");
		utility = new Utility(driver);
		baseClass.stepInfo("Verify when Domain Admin impersonates as PA in domain project and then changes the project from header drop down");
		userManage = new UserManagement(driver);
		
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		UtilityLog.info("Logged in as User: " + Input.da1userName);
		Reporter.log("Logged in as User: " + Input.da1password);

		baseClass.impersonateDAtoPAforMultiDominUser();
		baseClass.stepInfo("Impersonated as PA in same domain project");
		
		baseClass.selectproject(Input.additionalDataProject);
		baseClass.stepInfo("Changed the project from header drop down");
		driver.waitForPageToBeReady();
		baseClass.verifyCurrentProject(Input.additionalDataProject);
		
		baseClass.passedStep("Verified when Domain Admin impersonates as PA in domain project and then changes the project from header drop down");

		
		loginPage.logout();

	}
	/**
	 * Author : Aathith date: NA Modified date: Modified by: 
	 * Description :Verify when Domain Admin impersonates as RMU in domain project and then changes the project from header drop down
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 3)
	public void verifyDaImpersateRmuAndChangeProject() throws Exception {
		baseClass = new BaseClass(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-53265");
		utility = new Utility(driver);
		baseClass.stepInfo("Verify when Domain Admin impersonates as RMU in domain project and then changes the project from header drop down");
		userManage = new UserManagement(driver);
		
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		UtilityLog.info("Logged in as User: " + Input.da1userName);
		Reporter.log("Logged in as User: " + Input.da1password);

		baseClass.impersonateDAtoRMU();
		baseClass.stepInfo("Impersonated as RMU in same domain project");
		
		baseClass.selectproject(Input.additionalDataProject);
		baseClass.stepInfo("Changed the project from header drop down");
		driver.waitForPageToBeReady();
		baseClass.verifyCurrentProject(Input.additionalDataProject);
		
		baseClass.passedStep("Verified when Domain Admin impersonates as RMU in domain project and then changes the project from header drop down");

		
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
