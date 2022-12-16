package testScriptsRegressionSprint28;

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
import pageFactory.BaseClass;
import pageFactory.DomainDashboard;
import pageFactory.LoginPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DomainManagement_Regression28 {
	
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Utility utility;
	SoftAssert softAssertion;
	DomainDashboard domainDashboard;

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
		domainDashboard = new DomainDashboard(driver);

	}
	
	/**
	 * @author  Date:NA ModifyDate:NA Testcase No:RPMXCON-52862
	 * @Description:Verify that after impersonation domain drop down in the header
	 *                     should be updated to reflect selected domain
	 **/
	@Test(description = "RPMXCON-52862", groups = { "regression" })
	public void verifyingImpersonatingSAToDAHeaderUpdatedToReflect() throws InterruptedException {

		DomainDashboard dash = new DomainDashboard(driver);
		baseClass.stepInfo("RPMXCON-52862");
		baseClass.stepInfo(
				"Verify that after impersonation domain drop down in the header should be updated to reflect selected domain");

		// login as SAU
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As : " + Input.sa1userName);
		baseClass.stepInfo("Selecting domain administrator and select domain");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(baseClass.getSignoutMenu());
		baseClass.getSignoutMenu().waitAndClick(10);
		baseClass.waitForElement(baseClass.getChangeRole());
		baseClass.getChangeRole().waitAndClick(5);
		baseClass.waitForElement(baseClass.getSelectRole());
		baseClass.getSelectRole().selectFromDropdown().selectByVisibleText(Input.DomainAdministrator);
		baseClass.waitForElement(baseClass.getAvlDomain());

		baseClass.stepInfo("verifying domain dropdown is displayed");
		baseClass.elementDisplayCheck(baseClass.getAvlDomain());

		baseClass.getAvlDomain().selectFromDropdown().selectByVisibleText(Input.domainName);
		baseClass.stepInfo(Input.domainName + " domain selected in dropdown");
		System.out.println(Input.domainName);
		baseClass.waitTime(3);
		baseClass.waitForElement(baseClass.getSaveChangeRole());
		baseClass.getSaveChangeRole().waitAndClick(5);
		System.out.println("Impersonated from SA to DA");

		baseClass.waitTime(5);
		baseClass.stepInfo("verifying Selected Domain ");
		if (dash.getCurrentDomainValue(Input.domainName).isDisplayed()) {
			baseClass.passedStep("User navigates domain drop down " + Input.domainName
					+ "  in the header has been updated to reflect selected domain as expected");
		} else {
			baseClass.failedStep("SA is not impersonated as DA and not navigates to domain  page");
		}

		loginPage.logout();

	}

	/**
	 * @author  Date:NA ModifyDate:NA Testcase No:RPMXCON-52984
	 * @Description:To verify that System Admin can select the users and unassigned
	 *                 for a selected project successfully.
	 **/
	@Test(description = "RPMXCON-52984", groups = { "regression" })
	public void verifySAUserUnAssignedSelectedProject() throws InterruptedException {

		UserManagement user = new UserManagement(driver);
		SoftAssert softassert = new SoftAssert();
		String firstname = "PA";
		String lastname = "consilio";
		baseClass.stepInfo("RPMXCON-52984");
		baseClass.stepInfo(
				"To verify that System Admin can select the users and  unassigned for a selected project successfully.");

		// login as SAU
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As : " + Input.sa1userName);
		user.UnAssignUserToProject(Input.largeVolDataProject, Input.ProjectAdministrator, Input.pa2FullName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		baseClass.stepInfo("Login as Pa");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(baseClass.getProjectNames());
		baseClass.getProjectNames().waitAndClick(3);
		baseClass.waitTime(5);
		softassert.assertFalse(baseClass.getSelectProject(Input.largeVolDataProject).isElementAvailable(10));
		baseClass.passedStep(Input.largeVolDataProject + "  Unassigned project is not displayed");
		softassert.assertAll();
		loginPage.logout();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		user.navigateToUsersPAge();
		user.createUser(firstname, lastname, "Project Administrator", Input.pa2userName, null,
				Input.largeVolDataProject);
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
		System.out.println("******TEST CASES EXECUTED******");

	}

}
