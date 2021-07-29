package testScriptsSmoke;

import java.io.IOException;
import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.InstanceSettings;
import pageFactory.LoginPage;
import pageFactory.Utility;

public class TS_014_2FAValidation {
	Driver driver;
	LoginPage lp;
	InstanceSettings auth2FA;

	@BeforeClass(alwaysRun = true)
	public void preCondition() {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		// Open browser
		driver = new Driver();
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.sa1userName, Input.sa1password);
		// enable 2fa
		auth2FA = new InstanceSettings(driver);
		auth2FA.enable2FA();
		lp.logout();
	}

	@Test(groups = { "regression" })
	public void saLogin2FAEnabled() throws InterruptedException {
		// Login as a SA
		lp.loginToSightLine(Input.sa1userName, Input.sa1password);
		lp.logout();

	}

	@Test(groups = { "smoke", "regression" })
	public void paLogin2FAEnabled() throws InterruptedException {
		// Login as a PA
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		lp.logout();

	}

	@Test(groups = { "regression" })
	public void rmuLogin2FAEnabled() throws InterruptedException {
		// Login as a RMU
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		lp.logout();

	}

	@Test(groups = { "regression" })
	public void revLogin2FAEnabled() throws InterruptedException {
		// Login as a Rev
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		lp.logout();

	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) throws IOException {
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.logBefore(testMethod.getName());
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);

		}
		System.out.println("Executed :" + result.getMethod().getMethodName());

	}

	@AfterClass(alwaysRun = true)
	public void close() {
		lp.loginToSightLine(Input.sa1userName, Input.sa1password);
		// disable 2fa
		auth2FA.disable2FA();
		try {
			lp.logout();
			// lp.quitBrowser();
		} finally {
			lp.quitBrowser();
			lp.clearBrowserCache();
		}
	}
}
