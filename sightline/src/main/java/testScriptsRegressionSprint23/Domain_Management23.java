package testScriptsRegressionSprint23;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
import pageFactory.BatchPrintPage;
import pageFactory.DocListPage;
import pageFactory.DocViewRedactions;
import pageFactory.DomainDashboard;
import pageFactory.IngestionPage;
import pageFactory.LoginPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Domain_Management23 {
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Utility utility;
	UserManagement userManage;
	SoftAssert softAssertion;
	BatchPrintPage batchPrint;
	DomainDashboard domainDashboard;

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
	 * @author Brundha.T RPMXCON-52902
	 * @Description :Verify when Domain Admin selects role as 'Review
	 *              Manage'/'Reviewer' from Change Role pop up
	 */
	@Test(description = "RPMXCON-52902", enabled = true, groups = { "regression" })
	public void verifyRightInGrayForSelectedRole() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-52902");
		baseClass.stepInfo(
				"Verify when Domain Admin selects role as 'Review Manage'/'Reviewer' from Change Role pop up");
		// login as da
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("Successfully login as da user'" + Input.da1userName + "'");

		DomainDashboard dash = new DomainDashboard(driver);
		baseClass.waitTillElemetToBeClickable(baseClass.getSignoutMenu());
		baseClass.getSignoutMenu().Click();
		baseClass.waitTillElemetToBeClickable(baseClass.getChangeRole());
		baseClass.ValidateElement_Presence(baseClass.getChangeRole(), "Impersonate popup");
		baseClass.getChangeRole().Click();
		dash.validatingChangeRoleOptionInRMUAndReviewer(Input.Reviewer);
		dash.validatingChangeRoleOptionInRMUAndReviewer(Input.ReviewManager);
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
