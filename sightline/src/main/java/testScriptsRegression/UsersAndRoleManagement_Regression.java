package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.ManageAssignment;
import pageFactory.SecurityGroupsPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

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

	@BeforeMethod(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");

		Input input = new Input();
		input.loadEnvConfig();

		driver = new Driver();
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		UtilityLog.info("Logged in as User: " + Input.sa1userName);
		Reporter.log("Logged in as User: " + Input.sa1password);

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());
	}

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * TestCase id :  52426 - To verify when assigns user to different project with different role of existing project.
	 * Description : To verify when assigns user to different project with different role of existing project.
	 */
	@Test(alwaysRun = true,groups={"regression"},priority = 1)
	public void createUserWithDifferentProjectAndRole() throws Exception {		
		baseClass=new BaseClass(driver);
		String firstName = Input.randomText + Utility.dynamicNameAppender();
		String lastName = Input.randomText + Utility.dynamicNameAppender();
		String role = "Review Manager";
		String role2 = "Reviewer";
		String project1 = Input.projectName;
		String project2 = "MasterAutomationData";
		String emailId = Input.randomText + Utility.dynamicNameAppender()+"@consilio.com";
		baseClass.stepInfo("Test case Id: RPMXCON-52426");
		utility = new Utility(driver);
		baseClass.stepInfo("#### To verify when assigns user to different project with different role of existing project ####");
		userManage = new UserManagement(driver);
		
		baseClass.stepInfo("Create new user");
		userManage.createNewUser(firstName, lastName, role, emailId, " ",project1 );
		
		baseClass.stepInfo("Create new user with same username with different project and different role as previously created");
		userManage.createNewUser(firstName, lastName, role2, emailId, " ", project2);
		
		baseClass.passedStep("Created new user with same username with different project and different role as previously created successfully..");
	
		baseClass.stepInfo("Delete added users");
		userManage.deleteAddedUser(firstName);
	}
	
	/**
	 * Author : Baskar date: NA Modified date:21/01/2021 Modified by: Baskar
	 * Description :Verify that System Admin can create user with Domain Admin role
	 */
	@Test(alwaysRun = true,groups={"regression"},priority = 2)
	public void createNewUserForDomain() throws Exception {		
		baseClass=new BaseClass(driver);
		String firstName = Input.randomText + Utility.dynamicNameAppender();
		String lastName = Input.randomText + Utility.dynamicNameAppender();
		String role = "Domain Administrator";
		String emailId = Input.randomText + Utility.dynamicNameAppender()+"@consilio.com";
		baseClass.stepInfo("Test case Id: RPMXCON-52773");
		utility = new Utility(driver);
		baseClass.stepInfo("Verify that System Admin can create user with Domain Admin role");
		userManage = new UserManagement(driver);
		
		baseClass.stepInfo("Create new user for domain administration");
		userManage.createNewUser(firstName, lastName, role, emailId, " ",Input.projectName );
		
		baseClass.stepInfo("Domain admin role displayed in dropdown field");
		baseClass.passedStep("Created new user with Domain admin rule");
	
		baseClass.stepInfo("Delete added users");
		userManage.deleteAddedUser(firstName);
	}
	
	
	
	@AfterMethod(alwaysRun = true)
	public void close() {
		try {
			loginPage.logout();
		} finally {
			loginPage.closeBrowser();
			LoginPage.clearBrowserCache();
		} 
	}
	
     @AfterMethod(alwaysRun = true)
	 public void takeScreenShot(ITestResult result) {
 	 if(ITestResult.FAILURE==result.getStatus()){
 		 
 		Utility bc = new Utility(driver);
 		bc.screenShot(result);
 		try{ //if any tc failed and dint logout!
 		loginPage.logout();
 		}catch (Exception e) {
			// TODO: handle exception
		}
 	}
 	 System.out.println("Executed :" + result.getMethod().getMethodName());
 	
     }
 
}
