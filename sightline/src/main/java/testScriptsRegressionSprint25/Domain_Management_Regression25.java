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
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.DomainDashboard;
import pageFactory.LoginPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Domain_Management_Regression25 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Utility utility;
	SoftAssert softAssertion;
	DomainDashboard domainDashboard;

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
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		domainDashboard = new DomainDashboard(driver);
		
	}
	
	/**
	 * Author :Arunkumar date: 09/11/2022 TestCase Id:RPMXCON-52816
	 * Description :verify that Created By and Created On date column should be displayed on the Grid
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-52816",enabled = true, groups = { "regression" })
	public void verifyColumnDisplayedInGrid() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-52816");
		baseClass.stepInfo("verify that Created By and Created On date column should be displayed on the Grid");
		
		//Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");
		baseClass.stepInfo("go to manage clients section");
		domainDashboard.navigateToManageClientSection();
		baseClass.passedStep("Manage clients page displayed");
		baseClass.stepInfo("Verify columns displayed on grid");
		domainDashboard.verifyColumnPresentInClientGridTable();
		loginPage.logout();
	}
	
	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		baseClass = new BaseClass(driver);
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
		System.out.println("******TEST CASES FOR INGESTION EXECUTED******");

	}
}
