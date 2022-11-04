package testScriptsRegressionSprint25;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import pageFactory.BaseClass;
import pageFactory.LoginPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ReportEngineRegression_25 {
	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	Input ip;
	Utility utility;
	SoftAssert soft;
	UserManagement user;

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		Input in = new Input();
		in.loadEnvConfig();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());

		driver = new Driver();
		base = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		soft = new SoftAssert();
		user = new UserManagement(driver);
	}

	/**
	 * @Author Jeevitha
	 * @Description : Validate Project Admin is not allowed to impersonate to
	 *              Domain/System Admin while impersonating to RMU/Reviewer
	 *              [RPMXCON-56937]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-56937", enabled = true, groups = { "regression" })
	public void verifyPaIsNotAllowedToImpDaOrSa() throws Exception {

		// Login as a PA User
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged In As : PA");

		base.stepInfo("Test case Id:RPMXCON-56937 Report Engine");
		base.stepInfo(
				"Validate Project Admin is not allowed to impersonate to Domain/System Admin while impersonating to RMU/Reviewer");

		// click change role and open impersonate popup
		base.openImpersonateTab();

		// verify SA/DA role is not present in dropdown
		boolean saStatus = user.verifyRoleDD(Input.SystemAdministrator);
		boolean daStatus = base.getSelectRole(Input.DomainAdministrator).isElementAvailable(2);

		String passMsg1 = Input.SystemAdministrator + " is not present in Role Dropdown";
		String failMsg1 = Input.SystemAdministrator + " is present in dropdown";
		base.printResutInReport(saStatus, passMsg1, failMsg1, "Fail");

		String passMsg2 = Input.DomainAdministrator + " is not present in Role Dropdown";
		String failMsg2 = Input.DomainAdministrator + " is present in dropdown";
		base.printResutInReport(daStatus, passMsg2, failMsg2, "Fail");

		// Imoersonate to RMU
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		base.rolesToImp("PA", "RMU");

		// click change role and open impersonate popup
		base.openImpersonateTab();

		// verify SA/DA role is not present in dropdown
		boolean saStatusrev = user.verifyRoleDD(Input.SystemAdministrator);
		boolean da1Statusrev = base.getSelectRole(Input.DomainAdministrator).isElementAvailable(2);

		String passMsg1rev = Input.SystemAdministrator + " is not present in Role Dropdown";
		String failMsg1rev = Input.SystemAdministrator + " is present in dropdown";
		base.printResutInReport(saStatusrev, passMsg1rev, failMsg1rev, "Fail");

		String passMsg2rev = Input.DomainAdministrator + " is not present in Role Dropdown";
		String failMsg2rev = Input.DomainAdministrator + " is present in dropdown";
		base.printResutInReport(da1Statusrev, passMsg2rev, failMsg2rev, "Fail");

		// Impersonate to REV
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		base.rolesToImp("RMU", "REV");

		//select different project
		base.selectproject(Input.largeVolDataProject);
		
		//verify Current project after changing project
		base.verifyCurrentProject(Input.largeVolDataProject);

		// verify Current user role after changing project
		base.verifyCurrentRole(Input.Reviewer);

		// logout
		loginPage.logout();
	}

	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		base = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR Reports Communication EXECUTED******");

	}
}
