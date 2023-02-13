
package sightline.reports;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

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
import pageFactory.ReviewerResultReportPage;
import pageFactory.SchedulesPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ReviewResultReport_Regression {
	Driver driver;
	LoginPage lp;
	BaseClass bc;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

	Input in = new Input();
		in.loadEnvConfig();

	}

	/**
	 * @author Jayanthi.ganesan
	 * @param username
	 * @param password
	 * @param role
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-56422", dataProvider = "Users_PARMU", groups = { "regression" })
	public void reviewerResultDisplay(String username, String password, String role) throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-56422");
		bc.stepInfo("To verify that  Review Result Report should be displayed as per the selected criteria");
		lp = new LoginPage(driver);
		lp.loginToSightLine(username, password);
		driver.waitForPageToBeReady();
		bc.stepInfo("Logged in as -" + role);
		ReviewerResultReportPage revResultpage = new ReviewerResultReportPage(driver);
		revResultpage.navigateToReviewerResultReportPage();
		SoftAssert softAssertion = new SoftAssert();
		driver.waitForPageToBeReady();
		ArrayList<String> list = new ArrayList<String>(Arrays.asList("Date Range", "Document Types", "Tag Action Types",
				"Tags", "Redaction Tags", "Comment Types", "Productions"));
		for (String ele : list) {
			driver.scrollingToElementofAPage(revResultpage.getExpandButton(ele));
			if (revResultpage.getExpandButton(ele).isDisplayed()) {
				softAssertion.assertTrue(true);
				bc.passedStep(ele + " is displayed");

			} else {
				softAssertion.assertTrue(false);
				bc.failedStep(ele + " is not displayed");
			}
		}
		softAssertion.assertAll();
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param username
	 * @param password
	 * @param role
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-56451", dataProvider = "Users_PARMU", groups = { "regression" })
	public void verifyReviewerResultReportShare(String username, String password, String role)
			throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-56451");
		bc.stepInfo("To verify that user can Share the Review Result Report to other users Or share report by Email");
		lp = new LoginPage(driver);
		lp.loginToSightLine(username, password);
		driver.waitForPageToBeReady();
		bc.stepInfo("Logged in as -" + role);
		ReviewerResultReportPage revResultpage = new ReviewerResultReportPage(driver);
		revResultpage.navigateToReviewerResultReportPage();
		SoftAssert softAssertion = new SoftAssert();
		driver.waitForPageToBeReady();
		revResultpage.generateReport();
		revResultpage.shareReport();
		boolean status = bc.VerifySuccessMessageB("Your Report has been successfully shared with others.");
		softAssertion.assertTrue(status);
		softAssertion.assertAll();
		if (status) {
			bc.passedStep("Report Shared to user sucessfully");
		} else {
			bc.failedStep("Report not shared to any  user");
		}
		lp.logout();

	}

	/**
	 * @author Jayanthi.ganesan
	 * @param username
	 * @param password
	 * @param role
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-56455", groups = { "regression" })
	public void verifyReviewerResultReportShedule() throws InterruptedException, ParseException {
		bc.stepInfo("Test case Id: RPMXCON-56455");
		bc.stepInfo("To verify that users can schedule the Review Result Report.");
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		driver.waitForPageToBeReady();
		bc.stepInfo("Logged in as -PA");
		ReviewerResultReportPage revResultpage = new ReviewerResultReportPage(driver);
		revResultpage.navigateToReviewerResultReportPage();
		driver.waitForPageToBeReady();
		revResultpage.generateReport();
		revResultpage.getScheduleBtn().Click();
		revResultpage.scheduleReport();
		SchedulesPage sp = new SchedulesPage(driver);
		sp.verifyScheduledTime_Reports(1);
		sp.checkStatusComplete_reports();
		lp.logout();
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
		Reporter.setCurrentTestResult(result);
		LoginPage lp = new LoginPage(driver);
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
				{ Input.pa1userName, Input.pa1password, "PA" } };
		return users;
	}

	@AfterClass(alwaysRun = true)
	public void close() {

		System.out.println("Executed :Reviewer Result Report Regression ");
	}
}
