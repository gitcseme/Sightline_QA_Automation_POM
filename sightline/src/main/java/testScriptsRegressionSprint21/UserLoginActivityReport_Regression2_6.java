package testScriptsRegressionSprint21;

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
import pageFactory.ReportsPage;
import pageFactory.UserLoginActivityReport;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class UserLoginActivityReport_Regression2_6 {
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
	 * @throws InterruptedException 
	 * @throws ParseException 
	 * @description: Verify after impersonating as PA/RMU, by default user login activity report should present currently logged in PAUs, RMUs and reviewers in project
	 */
	@Test(description = "RPMXCON-58635",groups = {"regression" },enabled = true)
	public void verifyCurrentLoggedInUserAfterImpersonation() throws InterruptedException, ParseException {
		bc.stepInfo("Test case Id: RPMXCON-58635");
		bc.stepInfo("Verify after impersonating as PA/RMU, by default user login activity report should present currently logged in PAUs, RMUs and reviewers in project");
		SoftAssert sa = new SoftAssert();
		lp.loginToSightLine(Input.sa1userName, Input.sa1password);
		bc.impersonateSAtoPA();
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		userLoginActivityRptPg.navigateToUserLoginActivityReport();
		userLoginActivityRptPg.verifyCurrentLoggedInUserPresent(Input.sa1userName);
		String actualPARole = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(), "Role", Input.sa1userName);
		sa.assertEquals("Project Administrator", actualPARole);
		sa.assertAll();
		userLoginActivityRptPg.verifySelectionCriteria("Current Logged-in Users");
		bc.passedStep("After impersonating from SA to PA, able to view the SA user in currently logged in user as PA user");
		lp.logout();		
		lp.loginToSightLine(Input.sa1userName, Input.sa1password);
		bc.impersonateSAtoRMU();
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		userLoginActivityRptPg.navigateToUserLoginActivityReport();
		userLoginActivityRptPg.verifyCurrentLoggedInUserPresent(Input.sa1userName);
		String actualRMURole = userLoginActivityRptPg.getColoumnValue(userLoginActivityRptPg.columnHeader(), "Role", Input.sa1userName);
		sa.assertEquals("Review Manager", actualRMURole);
		userLoginActivityRptPg.verifySelectionCriteria("Current Logged-in Users");
		sa.assertAll();
		bc.passedStep("After impersonating from SA to RMU, able to view the SA user in currently logged in user as RMU user");
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
//				{ Input.pa1userName, Input.pa1password, "PA" } 
				};
		return users;
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		
		bc.stepInfo("***Executed UserLoginActivityReport_Regression2_4****");
		  

	}
}
