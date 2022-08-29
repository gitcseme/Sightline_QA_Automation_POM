package testScriptsRegressionSprint20;

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

public class UserLoginActivityReport_Regression2_5 {
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
	 * @description: Verify that user can export the current logged-in users, user login activity report
	 */
	@Test(description = "RPMXCON-58638",dataProvider = "Users_PARMU",groups = {"regression" },enabled = true)
	public void verifyExportForUserLoginActivityReport(String username,String password, String role)
			throws InterruptedException, ParseException {
		bc.stepInfo("Test case Id: RPMXCON-58638");
		bc.stepInfo("Verify that user can export the current logged-in users, user login activity report");	
		ReportsPage report = new ReportsPage(driver);
		SoftAssert sa = new SoftAssert();
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
		bc.passedStep("The user can able to see logged in users history with the selcted date range as expected");
		int filesInDirBeforeDownloading = bc.getDirFilesCount();
		int Bgcount = bc.initialBgCount();
		userLoginActivityRptPg.exportReport();
		bc.passedStep("Export success message verifed successfully for "+role);
		report.downLoadReport(Bgcount);
		int filesInDirAfterDownloading = bc.getDirFilesCount();
		sa.assertEquals(filesInDirAfterDownloading, filesInDirBeforeDownloading+1,"File is not downloaded");
		sa.assertAll();	
		userLoginActivityRptPg.selectLoginActivities("Current Logged-in Users");
		userLoginActivityRptPg.applyChanges();
		int filesInDirBeforeDownloading1 = bc.getDirFilesCount();
		int Bgcount1 = bc.initialBgCount();
		userLoginActivityRptPg.exportReport();
		bc.passedStep("Export success message verifed successfully for "+role);
		report.downLoadReport(Bgcount1);
		int filesInDirAfterDownloading1 = bc.getDirFilesCount();
		sa.assertEquals(filesInDirAfterDownloading1, filesInDirBeforeDownloading1+1,"File is not downloaded");
		sa.assertAll();	
		bc.passedStep("User login activity report is downloaded successfully");
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
