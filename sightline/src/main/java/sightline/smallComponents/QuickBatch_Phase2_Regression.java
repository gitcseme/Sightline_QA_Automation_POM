package sightline.smallComponents;

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
import automationLibrary.Driver;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class QuickBatch_Phase2_Regression {

	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	AssignmentsPage assignPage;
	BaseClass baseClass;
	Input in;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		//in = new Input();
		//in.loadEnvConfig();
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		assignPage = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);

	}

	/**
	 * @Author Jayanthi 12/07/2022
	 * @Description : Verify that error message should be displayed when Assignment name entered with 
				       < > * ; ‘ / ( ) # & with Quick Batch(Doc Explorer->select doc->Action->Quick Batch)
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-65055", groups = { "regression" })
	public void verifyErrorAlert() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-65055");
		baseClass.stepInfo("Verify that error message should be displayed when Assignment name entered with "
				+ "< > * ; ‘ / ( ) # & with Quick Batch(Doc Explorer->select doc->Action->Quick Batch)");
		String assignmentQB4 = "*&(#$%><";
		String expErrorMSg = "Please enter an assignment name without using special characters";

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		DocExplorerPage docexp = new DocExplorerPage(driver);

		docexp.DocExplorertoquickBatch();
		baseClass.stepInfo("Navigated to doc explorer, selected documents and actioned as 'Quick Batch'");
		assignPage.verifyErrorMSg_QuickBatchAssing(assignmentQB4, Input.codeFormName, expErrorMSg);

		loginPage.logout();
	}

	@DataProvider(name = "Users")
	public Object[][] CombinedSearchwithUsers() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password }, { Input.pa1userName, Input.pa1password },
				{ Input.rev1userName, Input.rev1password } };
		return users;
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
		System.out.println("**Executed  QuickBatchRegression2_1 Regression5**");
	}
}
