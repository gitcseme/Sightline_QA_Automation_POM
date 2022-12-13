package sightline.projects;

import java.awt.AWTException;
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

import automationLibrary.Driver;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;
import pageFactory.ProjectFieldsPage;

public class ProjectFileds_Regression_Consilio {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Input ip;
	Utility utility;
	SessionSearch sessionSearch;
	SecurityGroupsPage securityGroupsPage;
	UserManagement userManage;
	DocExplorerPage docexp;
	AssignmentsPage assignPage;
	KeywordPage keywordPage;
	SavedSearch savedsearch;
	ProjectPage projectPage;
	ProjectFieldsPage projectFieldsPage;

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
		projectPage = new ProjectPage(driver);
		loginPage = new LoginPage(driver);

	}

	/**
	 * @author Vijaya.Rani ModifyDate:16/08/2022 RPMXCON-52749
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description In Manage Keywords, keywords added are automatically truncated
	 *              without informing the user.
	 */
	@Test(description = "RPMXCON-69083", enabled = true, groups = { "regression" })
	public void verifyProjectFieldsWIthSplChars() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-69083");
		baseClass.stepInfo("Verify that error message display and application does NOT accepts - when user add & Edits project fields with special characters < > & ‘");
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");
		 
		String fieldName = "fieldName" + Utility.dynamicNameAppender()+"<>&‘";
		String presentationFieldName = "presentationFieldName"+ Utility.dynamicNameAppender()+"<>&‘";
		String fldDescription = "fldDescription"+ Utility.dynamicNameAppender()+"<>&‘";
		
		//Navigate to Projects Page
		projectFieldsPage = new ProjectFieldsPage(driver);
		projectFieldsPage.navigateToProjectFieldsPage();
		projectFieldsPage.verifyWarningMessageAddProjectFieldWithSplCharacters(fieldName, presentationFieldName, "Chat", fldDescription, "BIT");

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
		System.out.println("******TEST CASES FOR DOCVIEW EXECUTED******");

	}
}
