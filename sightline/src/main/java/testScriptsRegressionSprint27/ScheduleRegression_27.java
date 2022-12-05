package testScriptsRegressionSprint27;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocViewPage;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SchedulesPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ScheduleRegression_27 {

	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	AssignmentsPage assignment;
	BaseClass baseClass;
	Input in;
	SoftAssert softAssert;
	KeywordPage keyPage;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		in = new Input();
		in.loadEnvConfig();
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		assignment = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		sessionSearch = new SessionSearch(driver);
		softAssert = new SoftAssert();

	}

	/**
	 * @author: 
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :Verify that appropriate page title displays on "Schedules"
	 *              screen RPMXCON-54101.
	 */
	@Test(description = "RPMXCON-54101", enabled = true, groups = { "regression" })
	public void verifyTitleDisplayOnScheduleScreen() throws AWTException {
		SchedulesPage schedule = new SchedulesPage(driver);

		// login as RMU
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-54101 ");
		baseClass.stepInfo("Verify that appropriate page title displays on \"Schedules\" screen");
		schedule.navigateToSchedulerPage();
		baseClass.stepInfo("User navigated to Schedules page successfully");
		driver.waitForPageToBeReady();
		String pageTitle = schedule.getPageTitle().getText();
		System.out.println(pageTitle);
		if (pageTitle.equals("Manage Schedules")) {
			baseClass.passedStep(pageTitle +"  Title is displayed on manage schedules page as expected");
		} else {
			baseClass.failedStep("Tile is not displayed on manage schedules page");

		}
	}



	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		System.out.println("**Executed  Assignment_Regression_sprint23 .**");
	}
}
