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
import pageFactory.ReportsPage;
import pageFactory.UserLoginActivityReport;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class UserLoginActivityReport_Regression2_2 {
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
	 * @description: Verify that user can save the current logged-in users, user login activity report
	 */
	@Test(description = "RPMXCON-58639",dataProvider = "Users_PARMU",groups = {"regression" },enabled = true)
	public void verifyUserSaveCurrentLoggedInUserReport(String username,String password, String role) {
		bc.stepInfo("Test case Id: RPMXCON-58639");
		bc.stepInfo("Verify that user can save the current logged-in users, user login activity report");	
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
	 * @description: To verify that user can Export with all deatils from User Login Activity report
	 */
	@Test(description = "RPMXCON-56521", dataProvider = "Users_PARMU",groups = {"regression" },enabled = true)
	public void verifyUserExportUserLoginActivityReport(String username,String paasword,String role) throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-56521");
		bc.stepInfo("To verify that user can Export with all deatils from User Login Activity report");	
		ReportsPage report = new ReportsPage(driver);
		SoftAssert sa = new SoftAssert();
		lp.loginToSightLine(username, paasword);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		userLoginActivityRptPg.navigateToUserLoginActivityReport();
		userLoginActivityRptPg.verifyCurrentLoggedInUserPresent(username);
		int filesInDirBeforeDownloading = bc.getDirFilesCount();
		int Bgcount = bc.initialBgCount();
		userLoginActivityRptPg.exportReport();
		bc.passedStep("Export success message verifed successfully for "+role);
		report.downLoadReport(Bgcount);
		int filesInDirAfterDownloading = bc.getDirFilesCount();
		sa.assertEquals(filesInDirAfterDownloading, filesInDirBeforeDownloading+1,"File is not downloaded");
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
		
		bc.stepInfo("***Executed UserLoginActivityReport_Regression2_2****");
		  

	}
}
