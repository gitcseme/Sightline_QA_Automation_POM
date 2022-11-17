package testScriptsRegressionSprint26;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

import org.openqa.selenium.WebElement;
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
import pageFactory.CodingForm;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class CodingFormRegression_26 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Input ip;
	Utility utility;
	SessionSearch sessionSearch;
	AssignmentsPage assignPage;
	CodingForm codingForm;
	SoftAssert softAssert;

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
		codingForm = new CodingForm(driver);

	}

	/**
	 * @Author
	 * @Description : Verify that when user try to delete default set coding form an
	 *              error message occurred \"Error ! A coding form configured as
	 *              default coding form for a security group or an assignment cannot
	 *              be deleted\".
	 */
	@Test(description = "RPMXCON-65190", enabled = true, groups = { "regression" })
	public void verifyUserTryDeleteDefaultSetCodingFormErrorMessageOccured() {
		String codingform = "codingForm" + Utility.dynamicNameAppender();
		baseClass.stepInfo("Test case Id: RPMXCON-65190 CodingForm");
		baseClass.stepInfo(
				"Verify that when user try to delete default set coding form an error message occurred \"Error ! A coding form configured as default coding form for a security group or an assignment cannot be deleted\". ");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// creating new coding Form
		baseClass.stepInfo("creating new coding Form.");
		codingForm.navigateToCodingFormPage();
		codingForm.createCodingFormWithoutObjects(codingform);

		// setting newly created coding Form as Default Coding Form
		baseClass.stepInfo("setting newly created coding Form as Default Coding Form.");
		codingForm.AssignCFstoSG(codingform);

		// performing Delete action
		baseClass.stepInfo(" performing Delete action on  default set coding form.");
		codingForm.deleteCodingForm(codingform, codingform);

		// Verify that an error message occurred
		String expectedErrorMessage = "A coding form configured as default coding form for a security group or an assignment cannot be deleted.";
		baseClass.VerifyErrorMessage(expectedErrorMessage);
		baseClass
				.passedStep("Verified that when user try to delete default set coding form an error message occurred.");

		// deleting created coding form
		codingForm.selectDefaultProjectCodingForm();
		codingForm.deleteCodingForm(codingform, codingform);

		// logOut
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
		System.out.println("******TEST CASES FOR CODINGFORM EXECUTED******");

	}
}
