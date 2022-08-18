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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import automationLibrary.Driver;
import pageFactory.BaseClass;
import pageFactory.LoginPage;
import pageFactory.UserLoginActivityReport;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class UserLoginActivityReport_Regression2_1 {
	Driver driver;
	LoginPage lp;
	SoftAssert softAssertion;
	BaseClass bc;
	UserLoginActivityReport userLoginActivityRptPg;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();

	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description: To verify that report shows RMUs and Reviewers login and log out details when RMU runs the report
	 */
	@Test(description = "RPMXCON-48741",groups = {"regression" },enabled = true)
	public void verifyLoginDetailsWhenLoginAsRMU()
			throws InterruptedException, ParseException {
		bc.stepInfo("Test case Id: RPMXCON-48741");
		bc.stepInfo("To verify that report shows RMUs and Reviewers login and log out details when RMU runs the report");		
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.stepInfo("Login as RMU successfully");
		String expectedRmuLoginTime = userLoginActivityRptPg.getCurrentUtcTime();
		lp.logout();
		bc.stepInfo("Logged out as RMU successfully");
		String expectedRmuLogoutTime = userLoginActivityRptPg.getCurrentUtcTime();
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		bc.stepInfo("Login as REV successfully");
		String expectedRevLoginTime = userLoginActivityRptPg.getCurrentUtcTime();
		lp.logout();
		bc.stepInfo("Logged out as REV successfully");
		String expectedRevLogoutTime = userLoginActivityRptPg.getCurrentUtcTime();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);		
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		userLoginActivityRptPg.navigateToUserLoginActivityReport();
		userLoginActivityRptPg.selectLoginActivities("Login History");
		userLoginActivityRptPg.verifyPAuserNotPresent(Input.pa1FullName);
		userLoginActivityRptPg.selectAllUsers();
		String today = userLoginActivityRptPg.getCurrentDate();
		userLoginActivityRptPg.selectDateRange(today, today);
		userLoginActivityRptPg.applyChanges();
		userLoginActivityRptPg.sortLoginTimeColumn();
		String actualRmuRole = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(), "Role", Input.rmu1userName);
		String actualRmuLoginTime = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(), "Login Date/Time (UTC)", Input.rmu1userName);
		String actualRmuLogoutTime = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(), "Logout Date/Time (UTC)", Input.rmu1userName);
		String actualRevRole = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(), "Role", Input.rev1userName);
		String actualRevLoginTime = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(), "Login Date/Time (UTC)", Input.rev1userName);
		String actualRevLogoutTime = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(), "Logout Date/Time (UTC)", Input.rev1userName);
		userLoginActivityRptPg.validateDate(expectedRmuLoginTime, actualRmuLoginTime,actualRmuRole,"logged-in");
		userLoginActivityRptPg.validateDate(expectedRmuLogoutTime, actualRmuLogoutTime,actualRmuRole,"logged-out");
		userLoginActivityRptPg.validateDate(expectedRevLoginTime, actualRevLoginTime,actualRevRole,"logged-in");
		userLoginActivityRptPg.validateDate(expectedRevLogoutTime, actualRevLogoutTime,actualRevRole,"logged-out");			
		lp.logout();
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description: To verify that report shows Project Admin, RMUs and Reviewers login and log out details when Project Admin runs the report
	 */
	@Test(description = "RPMXCON-48742",groups = {"regression" },enabled = true)
	public void verifyLoginDetailsWhenLoginAsPA()
			throws InterruptedException, ParseException {
		bc.stepInfo("Test case Id: RPMXCON-48742");
		bc.stepInfo("To verify that report shows Project Admin, RMUs and Reviewers login and log out details when Project Admin runs the report");	
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("Login as PA successfully");
		String expectedPALoginTime = userLoginActivityRptPg.getCurrentUtcTime();
		lp.logout();
		bc.stepInfo("Logged out as PA successfully");
		String expectedPALogoutTime = userLoginActivityRptPg.getCurrentUtcTime();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.stepInfo("Login as RMU successfully");
		String expectedRmuLoginTime = userLoginActivityRptPg.getCurrentUtcTime();
		lp.logout();
		bc.stepInfo("Logged out as RMU successfully");
		String expectedRmuLogoutTime = userLoginActivityRptPg.getCurrentUtcTime();
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		bc.stepInfo("Login as REV successfully");
		String expectedRevLoginTime = userLoginActivityRptPg.getCurrentUtcTime();
		lp.logout();
		bc.stepInfo("Logged out as REV successfully");
		String expectedRevLogoutTime = userLoginActivityRptPg.getCurrentUtcTime();
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);	
		bc.stepInfo("Login as PA successfully");
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		userLoginActivityRptPg.navigateToUserLoginActivityReport();
		userLoginActivityRptPg.selectLoginActivities("Login History");
		userLoginActivityRptPg.selectAllUsers();
		String today = userLoginActivityRptPg.getCurrentDate();
		userLoginActivityRptPg.selectDateRange(today, today);
		userLoginActivityRptPg.applyChanges();
		userLoginActivityRptPg.sortLoginTimeColumn();
		String actualPaRole = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(), "Role", Input.pa1userName);
		String actualPaLoginTime = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(), "Login Date/Time (UTC)", Input.pa1userName);
		String actualPaLogoutTime = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(), "Logout Date/Time (UTC)", Input.pa1userName);
		String actualRmuRole = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(), "Role", Input.rmu1userName);
		String actualRmuLoginTime = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(), "Login Date/Time (UTC)", Input.rmu1userName);
		String actualRmuLogoutTime = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(), "Logout Date/Time (UTC)", Input.rmu1userName);
		String actualRevRole = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(), "Role", Input.rev1userName);
		String actualRevLoginTime = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(), "Login Date/Time (UTC)", Input.rev1userName);
		String actualRevLogoutTime = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(), "Logout Date/Time (UTC)", Input.rev1userName);
		userLoginActivityRptPg.validateDate(expectedPALoginTime, actualPaLoginTime,actualPaRole,"logged-in");
		userLoginActivityRptPg.validateDate(expectedPALogoutTime, actualPaLogoutTime,actualPaRole,"logged-out");
		userLoginActivityRptPg.validateDate(expectedRmuLoginTime, actualRmuLoginTime,actualRmuRole,"logged-in");
		userLoginActivityRptPg.validateDate(expectedRmuLogoutTime, actualRmuLogoutTime,actualRmuRole,"logged-out");
		userLoginActivityRptPg.validateDate(expectedRevLoginTime, actualRevLoginTime,actualRevRole,"logged-in");
		userLoginActivityRptPg.validateDate(expectedRevLogoutTime, actualRevLogoutTime,actualRevRole,"logged-out");	
		
		lp.logout();
	}

	

	

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		softAssertion = new SoftAssert();
		driver = new Driver();
		lp = new LoginPage(driver);
		bc = new BaseClass(driver);
		userLoginActivityRptPg = new UserLoginActivityReport(driver);
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		lp = new LoginPage(driver);
		bc = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
		}
		try {
			lp.quitBrowser();
		} catch (Exception e) {
			lp.quitBrowser();
		}
	}

	@DataProvider(name = "Users_PARMU")
	public Object[][] PA_RMU() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "RMU" },
				{ Input.pa1userName, Input.pa1password, "PA" } 
				};
		return users;
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		
		bc.stepInfo("***Executed UserLoginActivityReport_Regression2_1****");
		  

	}
}
