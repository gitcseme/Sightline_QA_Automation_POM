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
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class UserRoleManagement_Regression27 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
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
		baseClass = new BaseClass(driver);
		softAssertion = new SoftAssert();
		projects = new ProjectPage(driver);

	}

	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		baseClass = new BaseClass(driver);
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

	/**
	 * @author NA Testcase No:RPMXCON-53222
	 * @Description:To Validate SystemAdmin modifying assigned billable/internal users for a Domain"
	 **/
	@Test(description = "RPMXCON-53222", enabled = true, groups = { "regression" })
	public void validateSAModifAssigUser() throws Exception {
		ProjectPage project = new ProjectPage(driver);
		UserManagement user = new UserManagement(driver);
		
		String clientName = "" + Utility.dynamicNameAppender();
		String shrtType = "" + Utility.randomCharacterAppender(4);
		
		baseClass.stepInfo("RPMXCON-53222");
		baseClass.stepInfo("To Validate SystemAdmin modifying assigned billable/internal users for a Domain");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As : " + Input.sa1userName);

		project.navigateToClientFromHomePage();
		driver.waitForPageToBeReady();
		project.addNewClient(clientName, shrtType, "Domain");
		
		user.navigateToUsersPAge();
		driver.waitForPageToBeReady();
		user.AssignUserToDomain(clientName, Input.pa1FullName);	
		user.AssignUserToDomain(clientName, Input.rmu1FullName);
		user.unAssignUserToDomain(clientName, Input.pa1FullName);
		user.AssignUserToDomain(clientName, Input.rev1FullName);

	
		baseClass.waitForElement(user.getAssignUserButton());
		user.getAssignUserButton().waitAndClick(10);
		baseClass.waitForElement(user.getSelectDomainname());
		user.getSelectDomainname().selectFromDropdown().selectByVisibleText(clientName);
		if(user.getAssignedDomain(Input.rmu1FullName).isElementAvailable(15) && user.getAssignedDomain(Input.rev1FullName).isElementAvailable(15)) {
			baseClass.passedStep("Modified user changes successfully saved for the selected Domain");
		} else {
			baseClass.failedStep("Modified user changes not saved for the selected Domain As Expected");
		}
		baseClass.passedStep("To Validate SystemAdmin modifying assigned billable/internal users for a Domain");
		loginPage.logout();

	}
	
}
