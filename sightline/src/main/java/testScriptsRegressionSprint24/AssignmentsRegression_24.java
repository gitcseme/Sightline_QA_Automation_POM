package testScriptsRegressionSprint24;

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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class AssignmentsRegression_24 {

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
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :To verify that label of the textbox is changed where RMU enters
	 *              numbers for Sample method.RPMXCON-53637
	 */

	@Test(description = "RPMXCON-53637", enabled = true, groups = { "regression" })
	public void verifyLableOfTextboxChangedWhereRMUEnterNumberOfSampleMethod() throws InterruptedException {
		String assignmentName = "assignment" + Utility.dynamicNameAppender();
		String listOfSampleMethodOptions[] = { "Percent of Selected Docs", "Parent Level Docs Only",
				"Count of Selected Docs", "Inclusive Email" };

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-53637 Assignments");
		baseClass
				.stepInfo("To verify that label of the textbox is changed where RMU enters numbers for Sample method.");

		// create assignment
		assignment.createAssignment(assignmentName, Input.codeFormName);

		// perform basic content search
		sessionSearch.basicContentSearch(Input.searchString1);

		// perform bulk assign
		sessionSearch.bulkAssign();

		// Select Sample method as "Percent of Selected Docs" and Verify that Label is
		// changed to "Percentage to Assign"
		sessionSearch.changeSampleMethodAndVerifyTextBoxLableOfSampleMethod(listOfSampleMethodOptions[0], true, true);

		// Select Sample method as "Parent Level Docs Only" and Verify that label is
		// removed.
		sessionSearch.changeSampleMethodAndVerifyTextBoxLableOfSampleMethod(listOfSampleMethodOptions[1], true, false);

		// Select Sample method as "Inclusive Email" and Verify that label is removed.
		sessionSearch.changeSampleMethodAndVerifyTextBoxLableOfSampleMethod(listOfSampleMethodOptions[2], true, false);

		// Select sample method as "Count of Selected Docs" and Verify that label is
		// changed to "Number to Assign"
		sessionSearch.changeSampleMethodAndVerifyTextBoxLableOfSampleMethod(listOfSampleMethodOptions[3], true, false);

		// logout
		loginPage.logout();
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
