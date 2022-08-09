package testScriptsRegressionSprint19;

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
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import pageFactory.BaseClass;
import pageFactory.ClientsPage;
import pageFactory.DomainDashboard;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Projects_Regresssion {
	
	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	Input ip;
	Utility utility;
	SoftAssert softAssertion;
	DomainDashboard dash;
	ProjectPage project;
	ClientsPage client;
	UserManagement user;
	
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
		base = new BaseClass(driver);
		loginPage = new LoginPage(driver);
	}
	
	/**
	 * @Author :Aathith 
	 * date: 08/09/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Verify that the notification should be received upon completion of the project creation
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-48768",enabled = true, groups = {"regression" })
	public void verifyNotificationAfterprojectCompletion() throws InterruptedException  {
		
		base = new BaseClass(driver);
		project = new ProjectPage(driver);
		dash = new DomainDashboard(driver);
		
		base.stepInfo("Test case Id: RPMXCON-48768");
		base.stepInfo("Verify that the notification should be received upon completion of the project creation");
		
		String name = Input.randomText + Utility.dynamicNameAppender();
		
		//login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		project.navigateToProductionPage();
		base.clearBullHornNotification();
		project.AddDomainProject(name,Input.domainName);
		base.waitForNotification();
		dash.getNotificationMessage(0, name);
		
		base.passedStep("Verified that the notification should be received upon completion of the project creation");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 08/09/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :To verify the 'Cancel' and 'Save' buttons
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-55587",enabled = true, groups = {"regression" })
	public void verifyCancelSaveButton() throws InterruptedException  {
		
		project = new ProjectPage(driver);
		softAssertion = new SoftAssert();
		dash = new DomainDashboard(driver);
		
		base.stepInfo("Test case Id: RPMXCON-55587");
		base.stepInfo("To verify the 'Cancel' and 'Save' buttons");
		
		String name = Input.randomText + Utility.dynamicNameAppender();
		String[] errormsg = {"You must specify the project name", "You must specify the client entity", "You must specify a project Hcode",
				"You must specify a value for the project db server.", "Project Workspace is required", "Ingestion Server Path is required",
				"Production Server Path is required", "You must specify the project folder", "You must specify the ingestion folder.",
				"You must specify the production folder"};
		
		//login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		//click cancel
		project.navigateToProductionPage();
		base.clearBullHornNotification();
		project.AddDomainProjectDetailsWithoutSave(name,Input.domainName);
		project.getCancelButton().waitAndClick(5);
		
		//click save without details
		project.getAddProjectBtn().waitAndClick(5);
		project.getButtonSaveProject().waitAndClick(5);
		for(String err: errormsg) {
			softAssertion.assertTrue((boolean)base.text(err).isElementAvailable(3),err);
		}
		base.passedStep("Error message was displayed for all mandatory fields");
		
		//save with details
		project.navigateToProductionPage();
		project.AddDomainProject(name, Input.domainName);
		dash.getNotificationMessage(0, name);
		base.clickFirstBackRoundTask();
		String url = driver.getUrl();
		softAssertion.assertEquals(url, Input.url + "Project/Project");
		softAssertion.assertAll();
		
		base.passedStep("To verify the 'Cancel' and 'Save' buttons");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 08/09/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :To verify user can search
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-55593",enabled = true, groups = {"regression" })
	public void verifyUserCanSearch() throws InterruptedException  {
		
		project = new ProjectPage(driver);
		softAssertion = new SoftAssert();
		
		base.stepInfo("Test case Id: RPMXCON-55593");
		base.stepInfo("To verify user can search");
		
		String randomName = Utility.randomCharacterAppender(10);
		
		//login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		//verify
		project.filterTheProject(Input.projectName);
		softAssertion.assertTrue((boolean)base.text(Input.projectName).isElementAvailable(3));
		base.passedStep("Project details was displayed.");
		project.filterTByClientName(Input.domainName);
		softAssertion.assertTrue((boolean)base.text(Input.domainName).isElementAvailable(3));
		base.passedStep("Record/s was displayed for selected Client.");
		
		project.filterTheProject(Input.projectName);
		project.filterTByClientName(Input.domainName);
		softAssertion.assertTrue((boolean)base.text(Input.projectName).isElementAvailable(3));
		softAssertion.assertTrue((boolean)base.text(Input.domainName).isElementAvailable(3));
		base.passedStep("Record was displayed for selected Project Name and Client.");
		
		project.filterTheProject(randomName);
		softAssertion.assertTrue((boolean)base.text("Your query returned no data").isElementAvailable(3));
		project.filterTByClientName(randomName);
		softAssertion.assertTrue((boolean)base.text("Your query returned no data").isElementAvailable(3));
		
		project.filterTheProject(Input.projectName);
		project.filterTByClientName(randomName);
		softAssertion.assertTrue((boolean)base.text("Your query returned no data").isElementAvailable(3));
		
		project.filterTheProject(randomName);
		project.filterTByClientName(Input.domainName);
		softAssertion.assertTrue((boolean)base.text("Your query returned no data").isElementAvailable(3));
		
		project.clearFilter();
		softAssertion.assertFalse((boolean)base.text("Your query returned no data").isDisplayed());
		base.passedStep("Message wsas displayed ‘Your query returned no data");
		
		softAssertion.assertAll();
		base.passedStep("verified user can search");
		loginPage.logout();
	}	
	
	/**
	 * @Author :Aathith 
	 * date: 08/09/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :To verify that on Manage -Projects page, label should be display as 'Project'
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-55908",enabled = true, groups = {"regression" })
	public void verifyPageTitle() throws InterruptedException  {
		
		project = new ProjectPage(driver);
		softAssertion = new SoftAssert();
		
		base.stepInfo("Test case Id: RPMXCON-55908");
		base.stepInfo("To verify that on Manage -Projects page, label should be display as 'Project'");
		
		//login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		//verify
		project.navigateToProductionPage();
		driver.waitForPageToBeReady();
		softAssertion.assertEquals(project.getPageTitle().getText().trim(), "Project");
		base.passedStep("Label was display as 'Project'");
		
		softAssertion.assertAll();
		base.passedStep("verified that on Manage -Projects page, label should be display as 'Project'");
		loginPage.logout();
	}	
	
	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		base = new BaseClass(driver);
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
		System.out.println("******TEST CASES FOR Projects EXECUTED******");

	}

}
