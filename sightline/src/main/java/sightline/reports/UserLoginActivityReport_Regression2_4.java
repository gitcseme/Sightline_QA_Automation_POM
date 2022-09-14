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

public class UserLoginActivityReport_Regression2_4 {
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
	 * @description: Verify that on selecting 'Login History' option from only show- drop down, should be able to see the users history
	 */
	@Test(description = "RPMXCON-58566",dataProvider = "Users_PARMU",groups = {"regression" },enabled = true)
	public void verifyUersLoginHistory(String username,String password, String role)
			throws InterruptedException, ParseException {
		bc.stepInfo("Test case Id: RPMXCON-58566");
		bc.stepInfo("Verify that on selecting 'Login History' option from only show- drop down, should be able to see the users history");	
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
		bc.waitTime(3);
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
		bc.passedStep("The user can able to see logged in users history with the selcted date range as expected");
		lp.logout();
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description: Verify that on selecting 'Current Logged-in users' option from only show- drop down, should be able to see the current logged in users on click of 'Apply Changes
	 */
	@Test(description = "RPMXCON-58567",dataProvider = "Users_PARMU",groups = {"regression" },enabled = true)
	public void verifyReportGeneratedAfterApplyChanges(String username,String password, String role)
			throws InterruptedException, ParseException {
		bc.stepInfo("Test case Id: RPMXCON-58567");
		bc.stepInfo("Verify that on selecting 'Current Logged-in users' option from only show- drop down, should be able to see the current logged in users on click of 'Apply Changes'");	
		
		lp.loginToSightLine(username,password);	
		bc.stepInfo("Login as PA successfully");
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		userLoginActivityRptPg.navigateToUserLoginActivityReport();
		userLoginActivityRptPg.selectLoginActivities("Login History");
		userLoginActivityRptPg.selectAllUsers();
		String today = userLoginActivityRptPg.getCurrentDate();
		userLoginActivityRptPg.selectDateRange(today, today);
		userLoginActivityRptPg.applyChanges();
		userLoginActivityRptPg.selectLoginActivities("Current Logged-in Users");
		if(userLoginActivityRptPg.getRerunReportText().isElementAvailable(5)) {
			bc.passedStep("Without apply changes the report is not generated");
		}else {
			bc.failedStep("Without apply changes the report is generated");
		}
		userLoginActivityRptPg.applyChanges();
		userLoginActivityRptPg.verifyCurrentLoggedInUserPresent(username);		
		bc.passedStep("Report is generated based");
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
		
		bc.stepInfo("***Executed UserLoginActivityReport_Regression2_4****");
		  

	}
}
