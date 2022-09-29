package testScriptsRegressionSprint22;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.TallyPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Tally_Regression_sprint22 {
	Driver driver;
	LoginPage lp;
	BaseClass bc;
	TallyPage tp;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		Input in = new Input();
		in.loadEnvConfig();
	}

	@Test(description = "RPMXCON-56215", dataProvider = "Users_PARMU", groups = { "regression" })
	public void verifyTally_WorkProduct(String username, String password, String role) throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-56215");
		bc.stepInfo("To Verify Proper Notification for Not Selecting Any Value from Tally BY in Tally");

		lp.loginToSightLine(username, password);

		String[] tallyBy = { "CustodianName", "DocFileType", "EmailAuthorName", "EmailAuthorAddress" };

		// iterating this for loop to change the tally by metadata value
		for (int i = 0; i < tallyBy.length; i++) {
			bc.stepInfo("**Navigating to tally Page and Selecting " + tallyBy[i] + " as tally by option.**");
			tp.navigateTo_Tallypage();
			tp.selectTallyByMetaDataField(tallyBy[i]);
			bc.VerifyErrorMessage("Please select a source to run the report");

			bc.passedStep("if we select tally by option as " + tallyBy[i] + " with out seelcting any source"
					+ "we wil get the error message as expected.");

		}
	}
	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		lp = new LoginPage(driver);
		bc = new BaseClass(driver);
		tp = new TallyPage(driver);
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
		System.out.println("**Executed Tally_Regression_sprint22**");

	}
}
