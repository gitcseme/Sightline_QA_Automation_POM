package sightline.assignments;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;

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
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Assignment_Regression_Consilio {

	Driver driver;
	LoginPage lp;
	SessionSearch search;
	SoftAssert softAssertion;
	BaseClass bc;
	AssignmentsPage agnmt;
	Input in;
	
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
		agnmt = new AssignmentsPage(driver);
		lp = new LoginPage(driver);
		bc = new BaseClass(driver);
		softAssertion = new SoftAssert();
		search = new SessionSearch(driver);
		

	}
	
	@Test(description = "RPMXCON-69084",dataProvider="AssignmentSplChars", groups = { "regression" })
	public void verifyCreateNEditAssignmentGrpWithSplChars(String AssignmentGrpNameSplChars) throws InterruptedException {
			lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			String AssignmentGrpName="AssignmentGrp"+ Utility.dynamicNameAppender();
			this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
			System.out.println(AssignmentGrpName);
			
			agnmt.createAssgnGroup(AssignmentGrpName);
			
			//create Assignment group with special characters

			agnmt.createAssgnGroupWithSplChars(AssignmentGrpNameSplChars);	
			
			//edit assignment name with special chars
			

			agnmt.EditAssgnGroupSplChars(AssignmentGrpName);
			
	}
	
	@Test(description = "RPMXCON-69085",dataProvider="AssignmentSplChars", groups = { "regression" })
	public void verifyCreateNEditAssignmentNameWithSplChars(String AssignmentNameSplChars) throws InterruptedException {
			lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			String AssignmentName="AssignmentName"+ Utility.dynamicNameAppender();
			
			
			agnmt.createAssignment(AssignmentName, Input.codingFormName);
			
			//create Assignment group with special characters
			
			agnmt.createAssignmentWithSplChars(AssignmentNameSplChars);
			
			//edit assignment name with special chars
			
		
			agnmt.editAssignmentWithSplChars(AssignmentName);
			
	}
	
	@DataProvider(name = "AssignmentSplChars")
	public Object[][] AssignmentSplChars() {
		Object[][] users = { { "Assignment<'>&" } };
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
			lp.quitBrowser();
		} catch (Exception e) {
			lp.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		System.out.println("**Executed  QuickBatchRegression2_1 Regression5**");
	}
}
