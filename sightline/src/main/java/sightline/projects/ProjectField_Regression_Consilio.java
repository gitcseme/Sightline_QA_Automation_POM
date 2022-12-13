package sightline.projects;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import pageFactory.BaseClass;
import pageFactory.DataSets;
import pageFactory.DocListPage;
import pageFactory.IngestionPage_Indium;
import pageFactory.LoginPage;
import pageFactory.ProjectFieldsPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ProjectField_Regression_Consilio {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	SessionSearch sessionSearch;
	DocListPage docList;
	Input ip;
	ProjectFieldsPage projectFieldsPage;

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		ip = new Input();
		ip.loadEnvConfig();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());

		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		projectFieldsPage = new ProjectFieldsPage(driver);
	}


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
		System.out.println("******TEST CASES FOR PROJECTFIELD EXECUTED******");

	}
}
