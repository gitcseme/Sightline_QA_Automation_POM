package testScriptsRegressionSprint26;

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
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Assingment_Regression26 {

	
	
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
	
	
	
	/**
	 * @author sowndarya
	 * @Modified by: N/A
	 * @Description : User able to change Set as Default coding form in Add/Remove Coding Forms from Assignment using radio button in Set as.[RPMXCON-65551]
	 */

	
	@Test(description = "RPMXCON-65551", enabled = true, groups = { "regression" })
	public void verifySeDiffCodingFormToAssignment() throws InterruptedException {

		String assignmentName = "assignment" + Utility.dynamicNameAppender();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-65551");
		baseClass.stepInfo("User able to change Set as Default coding form in Add/Remove Coding Forms from Assignment using radio button in Set as");

		String cfName="C"+ Utility.dynamicNameAppender();
		
		//create a new coding form
		CodingForm form = new CodingForm(driver);
		form.navigateToCodingFormPage();
		form.createCodingform(cfName);
		
		// creating Assignment
		assignment.navigateToAssignmentsPage();
		assignment.createAssignment(assignmentName, Input.codingFormName);
		// performing bulk Assign

		//edit the assignment with new coding form as default.
		assignment.editAssignmentUsingPaginationConcept(assignmentName);
		assignment.EditCodingformInSelectedAssignment(cfName);
		loginPage.logout();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
