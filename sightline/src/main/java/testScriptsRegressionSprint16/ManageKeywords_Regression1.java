package testScriptsRegressionSprint16;

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
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CommentsPage;
import pageFactory.DataSets;
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

public class ManageKeywords_Regression1 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Input ip;
	Utility utility;
	SecurityGroupsPage securityGroupsPage;
	UserManagement userManage;
	DocExplorerPage docexp;
	AssignmentsPage assignPage;
	KeywordPage keywordPage;
	SavedSearch savedsearch;
	ProjectPage projectPage;

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
	 * @author Vijaya.Rani ModifyDate:01/07/2022 RPMXCON-52500
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description To verify when Sys Admin should be able to create keyword group
	 *              and keywords after impersonating
	 */
	@Test(description = "RPMXCON-52500", enabled = true, groups = { "regression" })
	public void verifyAfterImpersonationToAccessCreateKeyword() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-52500");
		baseClass.stepInfo(
				"To verify when Sys Admin should be able to create keyword group and keywords after impersonating.");
		KeywordPage keyWord = new KeywordPage(driver);
		String keywordname = "Addkeyword" + Utility.dynamicNameAppender();
		String color = "Blue";
		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as SA with " + Input.sa1userName + "");
		// Impersonate SA to RMU
		baseClass.impersonateSAtoRMU();
		// Add keyword
		keyWord.addKeywordWithColor(keywordname, color);
		baseClass.passedStep("After impersonating to access the create keyword group and keywords. ");
		loginPage.logout();

		// Login As SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as SA with " + Input.sa1userName + "");
		String keywordname1 = "Addkeyword1" + Utility.dynamicNameAppender();
		String color1 = "Blue";
		// Impersonate SA to PA
		baseClass.impersonateSAtoPA();
		// Add keyword
		keyWord.addKeywordWithColor(keywordname1, color1);
		baseClass.passedStep("After impersonating to access the create keyword group and keywords. ");
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:01/07/2022 RPMXCON-52501
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description To verify when Project Admin should be able to create keyword
	 *              group and keywords after impersonating to the RMU role
	 */
	@Test(description = "RPMXCON-52501", enabled = true, groups = { "regression" })
	public void verifyAfterImpersonationToCreateKeyword() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-52501");
		baseClass.stepInfo(
				"To verify when Project Admin should be able to create keyword group and keywords after impersonating to the RMU role.");
		KeywordPage keyWord = new KeywordPage(driver);
		String keywordname = "Addkeyword" + Utility.dynamicNameAppender();
		String color = "Blue";
		// Login As SA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");
		// Impersonate PA to RMU
		baseClass.impersonatePAtoRMU();

		// Add keyword
		keyWord.addKeywordWithColor(keywordname, color);
		baseClass.passedStep("After impersonating to RMU role from Project Admin, access to create keyword group and keywords in the selected project and the security group. ");

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
