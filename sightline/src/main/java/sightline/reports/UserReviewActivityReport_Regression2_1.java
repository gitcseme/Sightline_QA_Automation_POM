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
import pageFactory.CommunicationExplorerPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.UserReviewActivityReport;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class UserReviewActivityReport_Regression2_1 {
	Driver driver;
	LoginPage lp;
	SoftAssert softAssertion;
	BaseClass bc;
	UserReviewActivityReport userActivityRptPg;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();

	}

	@Test(description = "RPMXCON-56369", dataProvider = "Users_PARMU", groups = {"regression" },enabled = true)
	public void verifyUserReviewReportActivityLinkIsPresent(String username, String password, String role)
			throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-56369");
		bc.stepInfo("To verify that User Review Activity Report link is provided on Report Landing Page.");
		lp.loginToSightLine(username, password);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		bc.waitForElement(userActivityRptPg.getReports_UserReviewActivity());
		if (userActivityRptPg.getReports_UserReviewActivity().isDisplayed()) {
			bc.passedStep(
					"User activity report link is displayed in reports landing page on other section when login as "
							+ role);
		} else {
			bc.failedStep("User activity report link is not displayed in reports landing page when login as " + role);
		}
		lp.logout();
	}
	
	@Test(description = "RPMXCON-48724", dataProvider = "Users_PARMU", groups = {"regression" },enabled = true)
	public void verifyUserReviewReportActivityPageIsDisplayed(String username, String password, String role)
			throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-48724");
		bc.stepInfo("To verify that on selecting User Activity Report link User Activity Report page is displayed.");
		lp.loginToSightLine(username, password);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		if (userActivityRptPg.getReports_UserReviewActivity().isDisplayed()) {
			bc.passedStep(
					"User activity report link is displayed in reports landing page on other section when login as "
							+ role);
		} else {
			bc.failedStep("User activity report link is not displayed in reports landing page when login as " + role);
		}
		userActivityRptPg.navigateToUserReviewActivityReport();
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
		userActivityRptPg = new UserReviewActivityReport(driver);
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
		
		bc.stepInfo("***Executed Communications Explorer_Regression1****");
		  

	}
}
