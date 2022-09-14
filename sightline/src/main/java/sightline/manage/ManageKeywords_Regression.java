package sightline.manage;

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

public class ManageKeywords_Regression {

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
	@Test(description = "RPMXCON-52749", enabled = true, groups = { "regression" })
	public void verifyManageKeyWordAutomaticallytruncated() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-52749");
		baseClass
				.stepInfo("In Manage Keywords, keywords added are automatically truncated without informing the user.");
		KeywordPage keyWord = new KeywordPage(driver);
		String keywordname = "Addkeyword" + Utility.dynamicNameAppender();
		String color = "Blue";
		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage as PA with " + Input.pa1userName + "");
		// Add keyword
		driver.waitForPageToBeReady();
		keyWord.navigateToKeywordPage();
		keyWord.addKeywordWithColor(keywordname, color);
		baseClass.passedStep("keyword group is not be more than 500 characters");
		keyWord.deleteKeyword(keywordname);
		// Add keyword
		driver.waitForPageToBeReady();
		keyWord.addKeywordWithColor(keywordname, color);
		baseClass.passedStep("keyword group is less than or equal to 500 characters");
		keyWord.deleteKeyword(keywordname);
		// Add keyword
		driver.waitForPageToBeReady();
		keyWord.addKeywordWithColor(keywordname, color);
		baseClass.passedStep(
				"Entered keyword group with 500 characters is considered by DocView and the corresponding highlights are applied");
		keyWord.deleteKeyword(keywordname);
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
