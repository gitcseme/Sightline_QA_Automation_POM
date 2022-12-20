package testScriptsRegressionSprint28;

import java.io.IOException;
import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class UserRoleManagement_Regression28 {
	
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
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
		userManage = new UserManagement(driver);
		loginPage = new LoginPage(driver);

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
