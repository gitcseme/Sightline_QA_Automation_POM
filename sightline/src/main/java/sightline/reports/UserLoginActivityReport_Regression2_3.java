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
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import automationLibrary.Driver;
import pageFactory.BaseClass;
import pageFactory.LoginPage;
import pageFactory.ReportsPage;
import pageFactory.UserLoginActivityReport;
import pageFactory.Utility;
import testScriptsSmoke.Input;
public class UserLoginActivityReport_Regression2_3 {
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
	 * @description: Verify that saved custom report of current logged-in users, should displays correctly
	 */
	@Test(description = "RPMXCON-58641",dataProvider = "Users_PARMU",groups = {"regression" },enabled = true)
	public void verifyUserSaveCurrentLoggedInUserReport(String username,String password, String role) {
		bc.stepInfo("Test case Id: RPMXCON-58641");
		bc.stepInfo("Verify that saved custom report of current logged-in users, should displays correctly");	
		ReportsPage report = new ReportsPage(driver);
		String reportName = "ULAR"+Utility.dynamicNameAppender();
		lp.loginToSightLine(username, password);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		userLoginActivityRptPg.navigateToUserLoginActivityReport();
		userLoginActivityRptPg.verifyCurrentLoggedInUserPresent(username);
		userLoginActivityRptPg.saveUserloginActivityReport(reportName);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		userLoginActivityRptPg.verifyReportSavedSuccessfully(reportName);
		bc.waitTillElemetToBeClickable(userLoginActivityRptPg.customReports(reportName));
		userLoginActivityRptPg.customReports(reportName).waitAndClick(10);
		userLoginActivityRptPg.applyChanges();
		userLoginActivityRptPg.verifySelectionCriteria("Current Logged-in Users");
		userLoginActivityRptPg.verifyCurrentLoggedInUserPresent(username);
		report.deleteCustomReport(reportName);
		lp.logout();
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException 
	 * @throws ParseException 
	 * @description: Verify that user should not be displayed in report, when user sign outs from application
	 */
	@Test(description = "RPMXCON-58568", dataProvider = "Users_PARMU",groups = {"regression" },enabled = true)
	public void verifyUserNotDisplayedWhenLoggedOut(String username,String paasword,String role) throws InterruptedException, ParseException {
		bc.stepInfo("Test case Id: RPMXCON-58568");
		bc.stepInfo("Verify that user should not be displayed in report, when user sign outs from application");	
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		lp.logout();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		lp.logout();
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		lp.logout();
		lp.loginToSightLine(username, paasword);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		userLoginActivityRptPg.navigateToUserLoginActivityReport();
		userLoginActivityRptPg.verifyCurrentLoggedInUserPresent(username);
		userLoginActivityRptPg.verifyLoggedOutUserNotPresent(Input.rev1userName);
		if(role=="PA") {
		userLoginActivityRptPg.verifyLoggedOutUserNotPresent(Input.rmu1userName);
		}else {
			userLoginActivityRptPg.verifyLoggedOutUserNotPresent(Input.pa1userName);
		}
		bc.passedStep("Successfully verifed the user is not displayed when user logged out from application");
		lp.logout();
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException 
	 * @throws ParseException 
	 * @description: Verify that for PA user, by default user login activity report should present currently logged in PAUs, RMUs and reviewers in project
	 */
	@Test(description = "RPMXCON-58564",groups = {"regression" },enabled = true)
	public void verifyLoggedInUserPresentByPA() throws InterruptedException, ParseException {
		bc.stepInfo("Test case Id: RPMXCON-58564");
		bc.stepInfo("Verify that for PA user, by default user login activity report should present currently logged in PAUs, RMUs and reviewers in project");	
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		this.driver.get(Input.url);
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		this.driver.get(Input.url);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		userLoginActivityRptPg.navigateToUserLoginActivityReport();
		userLoginActivityRptPg.verifyCurrentLoggedInUserPresent(Input.pa1userName);
		userLoginActivityRptPg.verifyCurrentLoggedInUserPresent(Input.rmu1userName);
		userLoginActivityRptPg.verifyCurrentLoggedInUserPresent(Input.rev1userName);
		if(userLoginActivityRptPg.getOnlyShowActivity("Current Logged-in Users").isElementAvailable(5)) {
			bc.passedStep("Logged in users alone show in current logged in activity report");
		}else {
			bc.failedStep("Logged in users not show in current logged in activity report");
		}		
		lp.logout();
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException 
	 * @throws ParseException 
	 * @description: Verify that for RMU user, by default user login activity report should present currently logged in RMUs and reviewers in the assigned security group
	 */
	@Test(description = "RPMXCON-58565",groups = {"regression" },enabled = true)
	public void verifyLoggedInUserPresentByRmu() throws InterruptedException, ParseException {
		bc.stepInfo("Test case Id: RPMXCON-58565");
		bc.stepInfo("Verify that for RMU user, by default user login activity report should present currently logged in RMUs and reviewers in the assigned security group");	
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		this.driver.get(Input.url);
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		this.driver.get(Input.url);
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		userLoginActivityRptPg.navigateToUserLoginActivityReport();
		if(userLoginActivityRptPg.getCurrentLoggedInUser(Input.pa1userName).isElementAvailable(5)==false) {
			bc.passedStep("PA user is not displayed in the report when loggen in as RMU");
		}else {
			bc.failedStep("PA user is displayed in the report when loggen in as RMU");
		}
		userLoginActivityRptPg.verifyCurrentLoggedInUserPresent(Input.rmu1userName);
		userLoginActivityRptPg.verifyCurrentLoggedInUserPresent(Input.rev1userName);
		if(userLoginActivityRptPg.getOnlyShowActivity("Current Logged-in Users").isElementAvailable(5)) {
			bc.passedStep("Logged in users alone show in current logged in activity report");
		}else {
			bc.failedStep("Logged in users not show in current logged in activity report");
		}		
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
		
		bc.stepInfo("***Executed UserLoginActivityReport_Regression2_2****");
		  

	}
}
