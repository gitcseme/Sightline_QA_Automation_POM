package testScriptsSmoke;

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

import com.aventstack.extentreports.Status;

import automationLibrary.Driver;
import executionMaintenance.ExtentTestManager;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.Utility;

public class TS_013_ValidateBatchUpload {
	Driver driver;
	LoginPage lp;
	SavedSearch saveSearch;
	BaseClass bc;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		
		// Open browser
		driver = new Driver();
		bc = new BaseClass(driver);
		lp = new LoginPage(driver);

	}

	@Test(groups = { "smoke", "regression" })
	public void batchUploadByPA() throws InterruptedException {
		// Login as a PA
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		saveSearch = new SavedSearch(driver);
		saveSearch.uploadBatchFile(saveSearch.renameFile());
		System.out.println("Successfully ran for PA user...");
		UtilityLog.info("Successfully ran for PA user...");
		driver.Navigate().refresh();
		lp.logout();

	}

	@Test(groups = { "regression" })
	public void batchUploadByRMU() throws InterruptedException {
		// Login as a Review manager
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		saveSearch = new SavedSearch(driver);
		saveSearch.uploadBatchFile(saveSearch.renameFile());
		System.out.println("Successfully ran for RMU user...");
		UtilityLog.info("Successfully ran for RMU user...");
		driver.Navigate().refresh();
		lp.logout();
	}

	@Test(groups = { "regression" })
	public void batchUploadByReviewer() throws InterruptedException {
		// Login as a Reviewer
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		saveSearch = new SavedSearch(driver);
		saveSearch.uploadBatchFile(saveSearch.renameFile());
		System.out.println("Successfully ran for REV user...");
		UtilityLog.info("Successfully ran for REV user...");
		lp.logout();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result,Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.logBefore(testMethod.getName());
		
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		Reporter.setCurrentTestResult(result);
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);

		}
		System.out.println("Executed :" + result.getMethod().getMethodName());
	}

	@AfterClass(alwaysRun = true)
	public void close() {

		try {
			driver.scrollPageToTop();

			// lp.quitBrowser();
		} finally {
			lp.quitBrowser();
			LoginPage.clearBrowserCache();
		}
	}
}