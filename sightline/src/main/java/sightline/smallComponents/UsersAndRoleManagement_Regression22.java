package sightline.smallComponents;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class UsersAndRoleManagement_Regression22 {
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


	/**
	 * Author : Baskar date: NA Modified date:03/10/2022 Modified by: Baskar
	 * Description :Verify if RMU impersonate as Reviewer, he should able to impersonate 
	 *              back as Reviewer in other project,in which he is Reviewer
	 */

	@Test(description ="RPMXCON-53302",alwaysRun = true, groups = { "regression" } )
	public void validateRoleBackToRev() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-53302");
		baseClass.stepInfo(
				"Verify if RMU impersonate as Reviewer, he should able to "
				+ "impersonate back as Reviewer in other project,in which he is Reviewer");
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();

		// Login As rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		
		// changing the role from rmu to rev
		baseClass.impersonateRMUtoReviewer();
		baseClass.stepInfo("Changing the role from rmu to rev");
		
		// validating the rev dashboard page 
		baseClass.waitForElement(userManage.getDashBoardReviewer());
		boolean revDash=userManage.getDashBoardReviewer().isElementAvailable(2);
		softAssertion.assertTrue(revDash);
		baseClass.stepInfo("User on the Reviewer dashboard page");
		
		// changing the role again to rev 
		baseClass.impersonateRMUtoReviewer();
		baseClass.stepInfo("Changing the role from rev to rev");
		
		// validating the rev dashboard page again
		baseClass.waitForElement(userManage.getDashBoardReviewer());
		boolean revDashAgain=userManage.getDashBoardReviewer().isElementAvailable(2);
		softAssertion.assertTrue(revDashAgain);
		baseClass.stepInfo("User on the Reviewer dashboard page after selecting the same reviewer role");
		softAssertion.assertAll();
		baseClass.passedStep("Reviewer dashboard page displayed when back to back Reviewer role change");

		// logout
		loginPage.logout();
	}
	
	/**
	 * Author : Baskar date: NA Modified date:03/10/2022 Modified by: Baskar
	 * Description :Verify if RMU impersonate as Reviewer, he should able to impersonate 
	 *              back as RMU in different project in which he is assigned as RMU
	 */

	@Test(description ="RPMXCON-53301",alwaysRun = true, groups = { "regression" } )
	public void validateRoleBackToRmu() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-53301");
		baseClass.stepInfo(
				"Verify if RMU impersonate as Reviewer, he should able to impersonate "
				+ "back as RMU in different project in which he is assigned as RMU");
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();

		// Login As rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		
		// changing the role from rmu to rev
		baseClass.impersonateRMUtoReviewer();
		baseClass.stepInfo("Changing the role from rmu to rev");
		
		// validating the rev dashboard page 
		baseClass.waitForElement(userManage.getDashBoardReviewer());
		boolean revDash=userManage.getDashBoardReviewer().isElementAvailable(2);
		softAssertion.assertTrue(revDash);
		baseClass.stepInfo("User on the Reviewer dashboard page");
		
		// changing the role again to rmu 
		baseClass.impersonateReviewertoRMU();
		baseClass.stepInfo("Changing the role from rev to rmu");
		
		// validating the rmu dashboard page 
		baseClass.waitForElement(userManage.getRmuDashBoardPage());
		boolean rmuDashAgain=userManage.getRmuDashBoardPage().isElementAvailable(2);
		softAssertion.assertTrue(rmuDashAgain);
		baseClass.stepInfo("User on the Rmu dashboard page after selecting the rmu role");
		softAssertion.assertAll();
		baseClass.passedStep("Reviewer dashboard page displayed & Rmu dashborad page displayed after changing to rmu role");

		// logout
		loginPage.logout();
	}
	
	/**
	 * Author : Baskar date: NA Modified date:03/10/2022 Modified by: Baskar
	 * Description :Verify if SAU impersonate as Reviewer, he should able to impersonate back as RMU
	 */

	@Test(description ="RPMXCON-53289",alwaysRun = true, groups = { "regression" } )
	public void validateRoleSaToRevToRmu() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-53289");
		baseClass.stepInfo(
				"Verify if SAU impersonate as Reviewer, he should able to impersonate back as RMU");
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();

		// Login As sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		
		// changing the role from sa to rev
		baseClass.impersonateSAtoReviewer();
		baseClass.stepInfo("Changing the role from sa to rev");

		// validating the rev dashboard page 
		baseClass.waitForElement(userManage.getDashBoardReviewer());
		boolean revDash=userManage.getDashBoardReviewer().isElementAvailable(2);
		softAssertion.assertTrue(revDash);
		baseClass.stepInfo("User on the Reviewer dashboard page");
		
		// changing the role again to rmu
		baseClass.impersonateReviewertoRMU();
		baseClass.stepInfo("Changing the role from rev to rmu");
		
		// validating the rmu dashboard page 
		baseClass.waitForElement(userManage.getRmuDashBoardPage());
		boolean rmuDash=userManage.getRmuDashBoardPage().isElementAvailable(2);
		softAssertion.assertTrue(rmuDash);
		baseClass.stepInfo("User on the Rmu dashboard page after changing the rmu role");
		softAssertion.assertAll();
		baseClass.passedStep("Reviewer dashboard page displayed & Rmu dashborad page displayed after changing to rmu role");

		// logout
		loginPage.logout();
	}
	
	/**
	 * Author : Baskar date: NA Modified date:03/10/2022 Modified by: Baskar
	 * Description :Verify if PAU impersonate as Reviewer, he should able to 
	 *              impersonate back as PAU in same project
	 */

	@Test(description ="RPMXCON-53291",alwaysRun = true, groups = { "regression" } )
	public void validateRoleBackToPA() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-53291");
		baseClass.stepInfo(
				"Verify if PAU impersonate as Reviewer, he should able "
				+ "to impersonate back as PAU in same project");
		userManage = new UserManagement(driver);
		softAssertion = new SoftAssert();

		// Login As pa
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		// changing the role from pa to rev
		baseClass.impersonatePAtoReviewer();
		baseClass.stepInfo("Changing the role from pa to rev");
		
		// validating the rev dashboard page 
		baseClass.waitForElement(userManage.getDashBoardReviewer());
		boolean revDash=userManage.getDashBoardReviewer().isElementAvailable(2);
		softAssertion.assertTrue(revDash);
		baseClass.stepInfo("User on the Reviewer dashboard page");
		
		// changing the role again to pa
		baseClass.impersonateReviewertoPA(Input.projectName);
		baseClass.stepInfo("Changing the role from rev to pa");
		
		// validating the pa home page
		boolean rmuDashAgain=baseClass.getSelectSecurityGroup().isElementAvailable(2);
		softAssertion.assertFalse(rmuDashAgain);
		baseClass.stepInfo("User on the Pa home page after selecting the pa role");
		softAssertion.assertAll();
		baseClass.passedStep("Reviewer dashboard page displayed & Pa Home page displayed after changing to Pa role");

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
