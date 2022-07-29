package testScriptsRegressionSprint18;

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
import pageFactory.ClientsPage;
import pageFactory.DomainDashboard;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DomainManagement_Regression_04 {
	
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
	 * date: 07/29/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Verify after domain user impersonated down as a project admin into the "clicked project",he should be able to impersonate down to Reviewer
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-53125",enabled = true, groups = {"regression" })
	public void verifyImpersonateReviewer() throws InterruptedException  {
		
		base = new BaseClass(driver);
		dash = new DomainDashboard(driver);
		
		base.stepInfo("Test case Id: RPMXCON-53125");
		base.stepInfo("Verify after domain user impersonated down as a project admin into the \"clicked project\",he should be able to impersonate down to Reviewer");
		
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a da user :"+Input.da1userName);
		
		dash.goToFirstProject();
		base.impersonateRMUtoReviewer();
		driver.waitForPageToBeReady();
		if(base.text("Assignments within Assignment Group >>").isElementAvailable(8)) {
			base.passedStep("User should be successfully  impersonated down to Reviewer role");
		}else {
			base.failedStep("verification failed");
		}
		
		base.passedStep("Verified after domain user impersonated down as a project admin into the \"clicked project\",he should be able to impersonate down to Reviewer");
		loginPage.logout();
		
	}
	

	/**
	 * @Author :Aathith 
	 * date: 07/29/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :To verify user rights from Edit User > Functionality tab when Sys Admin changes role of Domain Admin 
	 * [assigned to single domain/non-domain project] user to Project Admin
	 */
	@Test(description = "RPMXCON-53061",enabled = true, groups = {"regression" })
	public void verifyUserRightsDomainToProjectAdmin() throws Exception  {
		
		user = new UserManagement(driver);
		softAssertion = new SoftAssert();
		
		base.stepInfo("Test case Id: RPMXCON-53061");
		base.stepInfo("To verify user rights from Edit User > Functionality tab when Sys Admin changes role of Domain Admin [assigned to single domain/non-domain project] user to Project Admin");
		
		//login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		//edit user
		user.filterByName(Input.da1userName);
		user.editLoginUser();
		
		//change role , get warning msg and click cancel
		user.getUserChangeDropDown().selectFromDropdown().selectByVisibleText(Input.ProjectAdministrator);
		driver.waitForPageToBeReady();
		softAssertion.assertEquals(user.getBellyBandMsg().getText().trim(),
				"The role of this user is being switched. The user permissions will be reset to the default permissions of the new role. Do you want to continue?");
		base.passedStep("Confirmation message is displayed to user saying \"The role of this user is being switched. "
				+ "The user permissions will be reset to the default permissions of the new role. Do you want to continue?\"");
		base.getNOBtn().waitAndClick(5);
		
		if(!base.text("Project :").isDisplayed()) {
			base.passedStep("Users role wasn't changed");
		}else {
			base.failedStep("verification failed");
		}
		
		//verify funcnality rights
		user.clickFunctionnalityTab();
		softAssertion.assertEquals((boolean)user.getAllBlockedUserRightInFuncnalityTab().isElementAvailable(1), false);
		base.passedStep("rights was displayed as per selected role on functionality tab.");
		
		user.clickDetailsTAb();
		user.getUserChangeDropDown().selectFromDropdown().selectByVisibleText(Input.DomainAdministrator);
		base.getNOBtn().waitAndClick(5);
		
		//click okay 
		driver.waitForPageToBeReady();
		user.getUserChangeDropDown().selectFromDropdown().selectByVisibleText(Input.ProjectAdministrator);
		base.getYesBtn().waitAndClick(5);
		
		if(base.text("Project :").isDisplayed()) {
			base.passedStep("Users role was changed");
		}else {
			base.failedStep("verification failed");
		}
		
		//verify funcnality tab
		user.clickFunctionnalityTab();
		softAssertion.assertEquals(user.getAllBlockedUserRightInFuncnalityTab().FindWebElements().size(), 1);
		base.passedStep("rights was displayed as per the selected role on functionality tab");

		softAssertion.assertAll();
		base.passedStep("verified user rights from Edit User > Functionality tab when Sys Admin changes role of Domain Admin [assigned to single domain/non-domain project] user to Project Admin");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 07/29/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :To verify user rights from Edit User > Functionality tab when Sys Admin changes role of Domain Admin
	 * [assigned to single domain/non-domain project] user to RMU
	 */
	@Test(description = "RPMXCON-53062",enabled = true, groups = {"regression" })
	public void verifyUserRightsDomainToRMU() throws Exception  {
		
		user = new UserManagement(driver);
		softAssertion = new SoftAssert();
		
		base.stepInfo("Test case Id: RPMXCON-53062");
		base.stepInfo("To verify user rights from Edit User > Functionality tab when Sys Admin changes role of Domain Admin[assigned to single domain/non-domain project] user to RMU");
		
		//login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		//edit user
		user.filterByName(Input.da1userName);
		user.editLoginUser();
		
		//change role , get warning msg and click cancel
		user.getUserChangeDropDown().selectFromDropdown().selectByVisibleText(Input.ReviewManager);
		driver.waitForPageToBeReady();
		softAssertion.assertEquals(user.getBellyBandMsg().getText().trim(),
				"The role of this user is being switched. The user permissions will be reset to the default permissions of the new role. Do you want to continue?");
		base.passedStep("Confirmation message is displayed to user saying \"The role of this user is being switched. "
				+ "The user permissions will be reset to the default permissions of the new role. Do you want to continue?\"");
		base.getNOBtn().waitAndClick(5);
		
		if(!base.text("Security Group :").isDisplayed()) {
			base.passedStep("Users role wasn't changed");
		}else {
			base.failedStep("verification failed");
		}
		
		//verify funcnality rights
		user.clickFunctionnalityTab();
		softAssertion.assertEquals((boolean)user.getAllBlockedUserRightInFuncnalityTab().isElementAvailable(1), false);
		base.passedStep("rights was displayed as per selected role on functionality tab.");
		
		user.clickDetailsTAb();
		user.getUserChangeDropDown().selectFromDropdown().selectByVisibleText(Input.DomainAdministrator);
		base.getNOBtn().waitAndClick(5);
		
		//click okay 
		driver.waitForPageToBeReady();
		user.getUserChangeDropDown().selectFromDropdown().selectByVisibleText(Input.ReviewManager);
		base.getYesBtn().waitAndClick(5);
		
		if(base.text("Security Group :").isDisplayed()) {
			base.passedStep("Users role was changed");
		}else {
			base.failedStep("verification failed");
		}
		
		//verify funcnality tab
		user.clickFunctionnalityTab();
		softAssertion.assertEquals(user.getAllBlockedUserRightInFuncnalityTab().FindWebElements().size(), 2);
		base.passedStep("rights was displayed as per the selected role on functionality tab");

		softAssertion.assertAll();
		base.passedStep("verified user rights from Edit User > Functionality tab "
				+ "when Sys Admin changes role of Domain Admin[assigned to single domain/non-domain project] user to RMU");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 07/29/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :To verify user rights from Edit User > Functionality tab when Sys Admin changes role of Domain Admin[assigned to single domain/non-domain project] user to Reviewer
	 */
	@Test(description = "RPMXCON-53063",enabled = true, groups = {"regression" })
	public void verifyUserRightsDomainToReviewer() throws Exception  {
		
		user = new UserManagement(driver);
		softAssertion = new SoftAssert();
		
		base.stepInfo("Test case Id: RPMXCON-53063");
		base.stepInfo("To verify user rights from Edit User > Functionality tab when Sys Admin changes role of Domain Admin[assigned to single domain/non-domain project] user to Reviewer");
		
		//login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		//edit user
		user.filterByName(Input.da1userName);
		user.editLoginUser();
		
		//change role , get warning msg and click cancel
		user.getUserChangeDropDown().selectFromDropdown().selectByVisibleText(Input.Reviewer);
		driver.waitForPageToBeReady();
		softAssertion.assertEquals(user.getBellyBandMsg().getText().trim(),
				"The role of this user is being switched. The user permissions will be reset to the default permissions of the new role. Do you want to continue?");
		base.passedStep("Confirmation message is displayed to user saying \"The role of this user is being switched. "
				+ "The user permissions will be reset to the default permissions of the new role. Do you want to continue?\"");
		base.getNOBtn().waitAndClick(5);
		
		if(!base.text("Security Group :").isDisplayed()) {
			base.passedStep("Users role wasn't changed");
		}else {
			base.failedStep("verification failed");
		}
		
		//verify funcnality rights
		user.clickFunctionnalityTab();
		softAssertion.assertEquals((boolean)user.getAllBlockedUserRightInFuncnalityTab().isElementAvailable(1), false);
		base.passedStep("rights was displayed as per selected role on functionality tab.");
		
		user.clickDetailsTAb();
		user.getUserChangeDropDown().selectFromDropdown().selectByVisibleText(Input.DomainAdministrator);
		base.getNOBtn().waitAndClick(5);
		
		//click okay 
		driver.waitForPageToBeReady();
		user.getUserChangeDropDown().selectFromDropdown().selectByVisibleText(Input.Reviewer);
		base.getYesBtn().waitAndClick(5);
		
		if(base.text("Security Group :").isDisplayed()) {
			base.passedStep("Users role was changed");
		}else {
			base.failedStep("verification failed");
		}
		
		//verify funcnality tab
		user.clickFunctionnalityTab();
		softAssertion.assertEquals(user.getAllBlockedUserRightInFuncnalityTab().FindWebElements().size(), 9);
		base.passedStep("rights was displayed as per the selected role on functionality tab");

		softAssertion.assertAll();
		base.passedStep("verified user rights from Edit User > Functionality tab when Sys Admin changes role of Domain Admin[assigned to single domain/non-domain project] user to Reviewer");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 07/29/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :To verify user rights from Edit User > Functionality tab when Sys Admin changes role of Project Admin[assigned to single domain/non-domain project] user to Domain Admin
	 */
	@Test(description = "RPMXCON-53064",enabled = true, groups = {"regression" })
	public void verifyUserRightsPaToDa() throws Exception  {
		
		user = new UserManagement(driver);
		softAssertion = new SoftAssert();
		
		base.stepInfo("Test case Id: RPMXCON-53064");
		base.stepInfo("To verify user rights from Edit User > Functionality tab when Sys Admin changes role of Project Admin[assigned to single domain/non-domain project] user to Domain Admin");
		
		//login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :"+Input.sa1userName);
		
		//edit user
		user.filterByName(Input.pa1userName);
		user.editLoginUser();
		
		//change role , get warning msg and click cancel
		user.getUserChangeDropDown().selectFromDropdown().selectByVisibleText(Input.DomainAdministrator);
		driver.waitForPageToBeReady();
		softAssertion.assertEquals(user.getBellyBandMsg().getText().trim(),
				"The role of this user is being switched. The user permissions will be reset to the default permissions of the new role. Do you want to continue?");
		base.passedStep("Confirmation message is displayed to user saying \"The role of this user is being switched. "
				+ "The user permissions will be reset to the default permissions of the new role. Do you want to continue?\"");
		base.getNOBtn().waitAndClick(5);
		
		if(base.text("Project :").isDisplayed()) {
			base.passedStep("Users role wasn't changed");
		}else {
			base.failedStep("verification failed");
		}
		
		//verify funcnality rights
		user.clickFunctionnalityTab();
		softAssertion.assertEquals(user.getAllBlockedUserRightInFuncnalityTab().FindWebElements().size(), 1);
		base.passedStep("rights was displayed as per selected role on functionality tab.");
		
		user.clickDetailsTAb();
		user.getUserChangeDropDown().selectFromDropdown().selectByVisibleText(Input.ProjectAdministrator);
		base.getNOBtn().waitAndClick(5);
		
		//click okay 
		driver.waitForPageToBeReady();
		user.getUserChangeDropDown().selectFromDropdown().selectByVisibleText(Input.DomainAdministrator);
		base.getYesBtn().waitAndClick(5);
		
		if(!base.text("Project :").isDisplayed()) {
			base.passedStep("Users role was changed");
		}else {
			base.failedStep("verification failed");
		}
		
		//verify funcnality tab
		user.clickFunctionnalityTab();
		softAssertion.assertEquals((boolean)user.getAllBlockedUserRightInFuncnalityTab().isElementAvailable(1), false);
		base.passedStep("rights was displayed as per the selected role on functionality tab");

		softAssertion.assertAll();
		base.passedStep("verified user rights from Edit User > Functionality tab when Sys Admin changes role of Project Admin[assigned to single domain/non-domain project] user to Domain Admin");
		loginPage.logout();
	}
	
	/**
	 * @Author :Aathith 
	 * date: 07/29/2022
	 * Modified date:NA 
	 * Modified by:
	 * @Description :Verify after domain user impersonated down as a project admin into the "clicked project",all existing menu items should display as usual
	 * @throws InterruptedException 
	 */
	@Test(description = "RPMXCON-53126",enabled = true, groups = {"regression" })
	public void verifyMenuDisplayInPA() throws InterruptedException  {
		
		dash = new DomainDashboard(driver);
		SessionSearch search = new SessionSearch(driver);
		softAssertion = new SoftAssert();
		
		base.stepInfo("Test case Id: RPMXCON-53126");
		base.stepInfo("Verify after domain user impersonated down as a project admin into the \"clicked project\",all existing menu items should display as usual");
		
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a da user :"+Input.da1userName);
		
		//go to project
		dash.filterProject(Input.projectName);
		dash.goToFirstProject();
		
		//verify menu
		softAssertion.assertEquals((boolean)base.text("Manage").isElementAvailable(3), true);
		softAssertion.assertEquals((boolean)base.text("Search").isElementAvailable(3), true);
		softAssertion.assertEquals((boolean)base.text("Reports").isElementAvailable(3), true);
		softAssertion.assertEquals((boolean)base.text("Productions").isElementAvailable(3), true);
		softAssertion.assertEquals((boolean)base.text("Batch Print").isElementAvailable(3), true);
		softAssertion.assertEquals((boolean)base.text("Categorize").isElementAvailable(3), true);
		base.passedStep("Menu links should be visible as:-   Manage Search Reports production Batch Print Categorize");
		
		//verify search result
		search.navigateToSessionSearchPageURL();
		softAssertion.assertNotEquals(search.basicContentSearch(Input.testData1), 0);
		base.passedStep("Search results is display properly.");
		
		softAssertion.assertAll();
		base.passedStep("Verified after domain user impersonated down as a project admin into the \"clicked project\",all existing menu items should display as usual");
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
		System.out.println("******TEST CASES FOR DomainManagement EXECUTED******");

	}

}