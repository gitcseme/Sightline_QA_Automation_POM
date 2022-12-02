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
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.ClassificationPage;
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
			bc.stepInfo("Verify that error message display and application does NOT accepts - when user add & Edits Assignment group with special characters < > & ‘");
			bc.stepInfo("Test case Id:RPMXCON-69084");	
			lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			String AssignmentGrpName="AssignmentGrp"+ Utility.dynamicNameAppender();
			this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
			System.out.println(AssignmentGrpName);
			
			agnmt.createAssgnGroup(AssignmentGrpName);
			
			//create Assignment group name with special characters

			agnmt.createAssgnGroupWithSplChars(AssignmentGrpNameSplChars);	
						
			//edit assignment group name with special chars
			
			agnmt.EditAssgnGroupSplChars(AssignmentGrpName);
			
			
	}
	
	@Test(description = "RPMXCON-69085",dataProvider="AssignmentSplChars", groups = { "regression" })
	public void verifyCreateNEditAssignmentNameWithSplChars(String AssignmentNameSplChars) throws InterruptedException {
			bc.stepInfo("Verify that error message display and application does NOT accepts - when user add & Edits Assignment name with special characters < > & ‘ ");
			bc.stepInfo("Test case Id:RPMXCON-69085");	
			lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			String AssignmentName="AssignmentName"+ Utility.dynamicNameAppender();	
			
			agnmt.createAssignment(AssignmentName, Input.codingFormName);
			
			//create Assignment name with special characters
			
			agnmt.createAssignmentWithSplChars(AssignmentNameSplChars);			
			
			//edit assignment name with special characterss		
		
			agnmt.editAssignmentWithSplChars(AssignmentName);

			
	}
	@Test(description = "RPMXCON-69087", enabled = true, groups = { "regression" })
	public void verifyRMUCanEnterSplCharsInClassificationField() throws Exception {
		bc.stepInfo("Verify that error message display and application does NOT accepts - when user add names in manage classification with special characters < > & ‘ ");
		bc.stepInfo("Test case Id:RPMXCON-69087");
		String maxQualified = "2";
		LoginPage lp = new LoginPage(driver);
		ClassificationPage clssp = new ClassificationPage(driver);
		Assertion a = new Assertion();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.stepInfo("Logged in as :" + Input.rmu1userName);

		bc.selectproject();
		
		clssp.navigateClassificationPage();
		
		bc.passedStep("Navigated to classification page");
		
		clssp.getMaxQualified().selectFromDropdown().selectByValue(maxQualified);
		int size=Integer.parseInt(maxQualified);
		for(int i=1;i<=size;i++) {
			String name=i+"LR<'>&";
			String RateVal="5<'>&";
			clssp.updateLevelClassificDetailsNotSave(i, name, "$ - USD", RateVal);
			String errorclassName=clssp.getClassificationNameQCErrormsg().getText();
			System.out.println("errorclassName :- "+errorclassName);
			a.assertEquals(errorclassName, "You cannot specify any special characters in the field value");
			String errorClassVal=clssp.getClassificationRatevalueErrormsgQC().getText();
			System.out.println("errorClassVal :- "+errorClassVal);
			a.assertEquals(errorClassVal, "The currency value specified is invalid.");
			
		}
		clssp.updateQCClassificDetailsNotSave("3LR<'>&", "$ - USD", "15<'>&");
		String errorclassName=clssp.getClassificationNameQCErrormsg().getText();
		a.assertEquals(errorclassName, "You cannot specify any special characters in the field value");
		String errorClassVal=clssp.getClassificationRatevalueErrormsgQC().getText();
		a.assertEquals(errorClassVal, "The currency value specified is invalid.");
		bc.passedStep("Entered classification fields with special characters and it failed to accept");
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
