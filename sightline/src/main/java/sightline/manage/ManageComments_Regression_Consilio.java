package sightline.manage;

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
import pageFactory.CommentsPage;
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

public class ManageComments_Regression_Consilio {
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Input ip;
	Utility utility;

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

	}
	
	@Test(description = "RPMXCON-66480", enabled = true, groups = { "regression" })
	public void verifyManagCommentsWIthSplChars() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-66480");
		baseClass.stepInfo("Verify that error message should be displayed when Comments added with < > * ; ‘ / ( ) # & from Manage-Comments");
		CommentsPage comments = new CommentsPage(driver);
		String commentName1 = "56comment" + Utility.dynamicNameAppender()+"<'>&#$(";
		// Login As PA
				loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
				comments.navigateToCommentsPage();
				comments.AddComments(commentName1);
				baseClass.passedStep("The commentName :- " + commentName1 + "failed to add in manage comments");
				driver.waitForPageToBeReady();
				loginPage.logout();

	}
	@Test(description = "RPMXCON-68805", enabled = true, groups = { "regression" })
	public void verifyManagCommentsWIthSpecificSplChars() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-68805");
		baseClass.stepInfo("Verify that error message display and application does NOT accepts - when \"Comment\" Name entered with special characters < > & ‘");
		CommentsPage comments = new CommentsPage(driver);
		String commentName1 = "comment" + Utility.dynamicNameAppender()+"<'>&";
		// Login As PA
				loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
				comments.navigateToCommentsPage();
				comments.AddComments(commentName1);
				baseClass.passedStep("The commentName :- " + commentName1 + "failed to add in manage comments");
				driver.waitForPageToBeReady();
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
		System.out.println("******TEST CASES FOR DOCVIEW EXECUTED******");

	}
}
