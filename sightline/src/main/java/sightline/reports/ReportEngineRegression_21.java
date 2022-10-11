package sightline.reports;

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

public class ReportEngineRegression_21 {

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
	 * @Description : Verify the role drop down for when Domain admin edits the
	 *              other User should not display system admin option.
	 *              [RPMXCON-56926]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-56926", enabled = true, groups = { "regression" })
	public void verifySAOptionIsNotPresentInDD() throws Exception {

		// Login as a DA User
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Logged In As : DA");

		base.stepInfo("Test case Id:RPMXCON-56926 Batch Redaction");
		base.stepInfo(
				"Verify the role drop down for when Domain admin edits the other User should not display system admin option.");

		//Click edit for user
		user.navigateToUsersPAge();
		user.filterByName(Input.pa1userName);
		user.selectEditUserUsingPagination(Input.projectName, false, "");
		
		//verify SA role is not present in dropdown
		boolean status = user.verifyRoleDD(Input.SystemAdministrator);
		String passMsg = Input.SystemAdministrator + " is not present in Role Dropdown";
		String failMsg = Input.SystemAdministrator + " is present in dropdown";
		base.printResutInReport(status, passMsg, failMsg, "Fail");

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
