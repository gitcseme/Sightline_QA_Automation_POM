package testScriptsRegressionSprint27;

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
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Projects_Regression27 {

	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	Utility utility;
	SoftAssert softAssertion;
	ProjectPage projects;
	String projectName;
	String clientName;

	@BeforeClass(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		Input input = new Input();
		input.loadEnvConfig();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());

		driver = new Driver();
		loginPage = new LoginPage(driver);
		base = new BaseClass(driver);
		softAssertion = new SoftAssert();
		projects = new ProjectPage(driver);

	}
	
	/**
	 * Author :Arunkumar date: 02/12/2022 TestCase Id:RPMXCON-56184 
	 * Description:Verify functionality if user cancel the Non-Domain Project creation
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-56184", enabled = true, groups = { "regression" })
	public void verifyIfUserCancelsNonDomainProjectCreation() throws InterruptedException {

		base.stepInfo("Test case Id: RPMXCON-56184");
		base.stepInfo("Verify functionality if user cancel the Non-Domain Project creation");
		String projectName = "QaProject" + Utility.dynamicNameAppender();
		String hCode = "H" + Utility.dynamicNameAppender();

		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Logged in as SA");
		base.stepInfo("Navigate to manage-project section and verify redirected url");
		projects.navigateToProductionPage();
		base.waitForElement(projects.getAddProjectBtn());
		projects.getAddProjectBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.verifyUrlLanding(Input.url + "en-us/Project/CreateProject", "Redirected to project creation page", 
				"not redirected to project creation page");
		base.stepInfo("enter project creation details");
		base.waitForElement(projects.getProjectName());
		projects.getProjectName().SendKeys(projectName);
		base.waitForElement(projects.getSelectEntityType());
		projects.getSelectEntityType().selectFromDropdown().selectByVisibleText("Not a Domain");
		base.waitForElement(projects.getHCode());
		projects.getHCode().SendKeys(hCode);
		base.stepInfo("click on cancel button");
		driver.scrollingToBottomofAPage();
		base.waitForElement(projects.getCancelButton());
		projects.getCancelButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.waitForElement(projects.getAddProjectBtn());
		// verify redirected url
		base.verifyUrlLanding(Input.url + "Project/Project", "Redirected to project page",
				"not redirected to project page");
		loginPage.logout();
	}
	
	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		base = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility base = new Utility(driver);
			base.screenShot(result);
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR PROJECTS EXECUTED******");

	}
}
