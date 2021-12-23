
package testScriptsRegression;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import org.testng.ITestResult;
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
import pageFactory.ReviewerProductivityReportPage;

import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ReviewerProductiviytReport_Regression {
	Driver driver;
	LoginPage lp;
	BaseClass bc;
	ReviewerProductivityReportPage reviewerProdReport;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		Input in = new Input();
		in.loadEnvConfig();

	}

	@Test(dataProvider = "Users_PARMU", groups = { "regression" }, priority = 1)
	public void reviewerProdPageDisplay(String username, String password, String role)
			throws InterruptedException, AWTException {
		LoginPage lp = new LoginPage(driver);
		bc.stepInfo("Test case Id: RPMXCON-56335");
		bc.stepInfo("To verify that Users are able to view Reviewer Productivity Report page.");
		lp.loginToSightLine(username, password);
		driver.waitForPageToBeReady();
		bc.stepInfo("Logged in as -" + role);
		ReviewerProductivityReportPage reviewerProdReport = new ReviewerProductivityReportPage(driver);
		reviewerProdReport.navigateTOReviewerPodReportPage();
	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		bc = new BaseClass(driver);

	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
		}
		try {
			LoginPage lp = new LoginPage(driver);
			lp.logout();
			lp.quitBrowser();
		} catch (Exception e) {
			lp.quitBrowser();
		}

		System.out.println("Executed :" + result.getMethod().getMethodName());

	}

	@DataProvider(name = "Users_PARMU")
	public Object[][] PA_RMU() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "RMU" },
				{ Input.pa1userName, Input.pa1password, "PA" } };
		return users;
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		System.out.println("Executed :ReviewerProductiviytReport_Regression ");

	}
}
