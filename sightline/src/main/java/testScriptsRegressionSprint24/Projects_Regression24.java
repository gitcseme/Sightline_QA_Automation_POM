package testScriptsRegressionSprint24;

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
import pageFactory.DomainDashboard;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.ProjectPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Projects_Regression24 {
	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	Utility utility;
	SoftAssert softAssertion;
	ProjectPage projects;
	String projectName;
	String clientName;
	ProductionPage page;

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
		softAssertion=new SoftAssert();
		projects= new ProjectPage(driver);
		page =new ProductionPage(driver);

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
		System.out.println("******TEST CASES FOR PRODUCTION EXECUTED******");

	}  
	
	/**
	 * @author NA Testcase No:RPMXCON-62851
	 * @Description:To Verify that When SAU/DAU changes the setting for Enable/Disable Project level Analytics,the changes should be saved.
	 **/
	@Test(description = "RPMXCON-62851", enabled = true, groups = { "regression" })
	public void verifySADAUChangeSettingProLvlAnal() throws Exception {
		base.stepInfo("RPMXCON-62851");
		base.stepInfo("To Verify that When SAU/DAU changes the setting for Enable/Disable Project level Analytics ,"
				+ " the changes should be saved.");
		
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Logged in As : " +Input.sa1userName);
		
		base = new BaseClass(driver);
		base.openImpersonateTab();
		base.selectImpersonateRole(Input.DomainAdministrator);
		base.selectImpersonateDomain(Input.domainName);
		base.waitForElement(base.getSaveChangeRole());
		base.getSaveChangeRole().waitAndClick(10);
		
		ProjectPage projPage = new ProjectPage(driver);
		projPage.navigateToProductionPage();
		projPage.editProject(Input.projectName);
		
		if(!projPage.getComponentLabel().isElementAvailable(5)) {
			base.waitForElement(projPage.getEnableAnalyticsToogle());
			projPage.getEnableAnalyticsToogle().waitAndClick(4);
			base.waitForElement(projPage.getButtonSaveProject());
			projPage.getButtonSaveProject().waitAndClick(5);
			driver.waitForPageToBeReady();
			projPage.editProject(Input.projectName);
		}
		
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		page.toggleOnCheck(projPage.getEnableAnalyticsToogle());
		driver.Navigate().refresh();
		base.waitForElement(projPage.getEnableAnalyticsToogle());
		projPage.getEnableAnalyticsToogle().waitAndClick(4);
		base.waitForElement(projPage.getButtonSaveProject());
		projPage.getButtonSaveProject().waitAndClick(5);
		projPage.editProject(Input.projectName);
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		page.toggleOffCheck(projPage.getEnableAnalyticsToogle());
		
		base.passedStep("Verified - that When SAU/DAU changes the setting for Enable/Disable Project level Analytics ,"
				+ " the changes should be saved.");
		loginPage.logout();
	}
	
	/**
	 * @author NA Testcase No:RPMXCON-56179
	 * @Description:To Verify when creating a non-domain project, the 'General' tab in Create Project should present 'Processing Settings' section
	 **/
	@Test(description = "RPMXCON-56179", enabled = true, groups = { "regression" })
	public void verifyProcessSettingCrtNonDomain() throws Exception {
		base.stepInfo("RPMXCON-56179");
		base.stepInfo("To Verify when creating a non-domain project, the 'General' tab in Create Project "
				+ "should present 'Processing Settings' section");
		
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Logged in As : " + Input.sa1userName);
		
		ProjectPage projPage = new ProjectPage(driver);
		projPage.navigateToProductionPage();
		base.waitForElement(projPage.getAddProjectBtn());
		projPage.getAddProjectBtn().waitAndClick(5);
		base.stepInfo("Successfully Clicked Add Project Button..");
		base.waitForElement(projPage.getSelectEntityType());
		projPage.getSelectEntityType().selectFromDropdown().selectByVisibleText("Domain");
		base.stepInfo("Domain Selected in Client Type DropDown");
		softAssertion.assertFalse(projPage.getProcessEngineTypeICE().isElementAvailable(10));
		softAssertion.assertFalse(projPage.getProcessEngineTypeNUIX().isElementAvailable(10));
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		base.waitForElement(projPage.getSelectEntityType());
		projPage.getSelectEntityType().selectFromDropdown().selectByVisibleText("Not a Domain");
		base.stepInfo("Not a Domain Selected in Client Type DropDown");
		softAssertion.assertTrue(projPage.getProcessEngineTypeICE().isElementAvailable(10));
		softAssertion.assertTrue(projPage.getProcessEngineTypeNUIX().isElementAvailable(10));
		softAssertion.assertAll();
		base.passedStep("Verified - when creating a non-domain project, the 'General' tab in Create Project "
				+ "should present 'Processing Settings' section");
		loginPage.logout();
	}
	
	/**
	 * @author NA Testcase No:RPMXCON-47013
	 * @Description: Verify that while editing the project, should be able to enable/disable analytics    
	 **/
	
	@Test(description = "RPMXCON-47013", enabled = true, groups = { "regression" })
	public void verifyEnabDisabToogleEditProj() throws Exception {
		String projectName = Input.randomText + Utility.dynamicNameAppender();
		
		base = new BaseClass(driver);
		ProjectPage projPage = new ProjectPage(driver);
		DomainDashboard dash = new DomainDashboard(driver);
		ProductionPage page = new ProductionPage(driver);
		
		base.stepInfo("Test case Id: RPMXCON-47013");
		base.stepInfo("To Verify that while editing the project, should be able to enable/disable analytics");
		
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Login as a DA user :"+Input.da1userName);
		
		projPage.navigateToProductionPage();
		base.clearBullHornNotification();
		projPage.AddDomainProjectViaDaUser(projectName);
		base.waitForNotification();
		dash.getNotificationMessage(0, projectName);
		
		projPage.navigateToProductionPage();
		projPage.editProject(projectName);
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		page.toggleOnCheck(projPage.getEnableAnalyticsToogle());
		base.stepInfo("Enable Analytics Enabled");
		
		driver.Navigate().refresh();
		base.waitForElement(projPage.getEnableAnalyticsToogle());
		projPage.getEnableAnalyticsToogle().waitAndClick(4);
		base.waitForElement(projPage.getButtonSaveProject());
		projPage.getButtonSaveProject().waitAndClick(5);
		projPage.editProject(projectName);
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		page.toggleOffCheck(projPage.getEnableAnalyticsToogle());
		base.stepInfo("After Click Enable Analytic Toogle and Save Toogle Disabled as Expected");
		
		driver.Navigate().refresh();
		base.waitForElement(projPage.getEnableAnalyticsToogle());
		projPage.getEnableAnalyticsToogle().waitAndClick(4);
		base.waitForElement(projPage.getButtonSaveProject());
		projPage.getButtonSaveProject().waitAndClick(5);
		projPage.editProject(projectName);
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		page.toggleOnCheck(projPage.getEnableAnalyticsToogle());
        base.stepInfo("After Click Enable Analytic Toogle and Save Toogle Enabled as Expected");	
		base.passedStep("Verified -  that while editing the project, should be able to enable/disable analytics");
		loginPage.logout();
	}

	

	/**
	 * @author sowndarya Testcase No:RPMXCON-56175
	 * @Description:Verify that when creating a non-domain client, the processing engine section doesn't present on 'Create Client' page
	 **/
	
	@Test(description = "RPMXCON-56175", enabled = true, groups = { "regression" })
	public void verifyNonDomainClient() throws Exception {
		String clientName = "C" + Utility.dynamicNameAppender();
		String shortName="S"+ Utility.dynamicNameAppender();
		
		base = new BaseClass(driver);
		
		base.stepInfo("Test case Id: RPMXCON-56175");
		base.stepInfo("Verify that when creating a non-domain client, the processing engine section doesn't present on 'Create Client' page");
		
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a SA user :"+Input.sa1userName);
		
		projects.navigateToClientFromHomePage();
		projects.addNewClient(clientName, shortName,"Not a Domain");
		
		String pass="The processing engine section not present on 'Create Client' page";
		String fail="The processing engine section is present on 'Create Client' page";
		
		Boolean result = projects.getProcessingEngineTxt().isDisplayed();
		base.printResutInReport(result, pass, fail,"Fail");
}
	
	
	/**
	 * @author sowndarya Testcase No:RPMXCON-55913
	 * @Description:Validate minimum length value for DocID Format for Domain project
	 **/
	
	@Test(description = "RPMXCON-55913", enabled = true, groups = { "regression" })
	public void verifyMinimumLengthValue_DomainProject() throws Exception {
		
		String projectName="P"+ utility.dynamicNameAppender();
		String clientName="CN"+utility.dynamicNameAppender();
		System.out.println(clientName);
		String shortName="S"+utility.dynamicRandomNumberAppender();
		base = new BaseClass(driver);
		
		base.stepInfo("Test case Id: RPMXCON-55913");
		base.stepInfo("Validate minimum length value for DocID Format for Domain project");
		
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a SA user :"+Input.sa1userName);
		
		projects.navigateToClientFromHomePage();
		projects.addNewClient(clientName, shortName, "Domain");
		System.out.println("client is created"+clientName);
		
		projects.navigateToProductionPage();
		
		//hovering action is performed temporarily to make the panel disappear
//		base.mouseHoverOnElement(projects.getManageProjectBtn());
//		driver.waitForPageToBeReady();
//		base.mouseHoverOnElement(projects.getAddProjectBtn());
		
		projects.AddDomainProject(projectName,clientName);
		System.out.println("project is created"+projectName);
		projects.navigateToProductionPage();
		
		//hovering action is performed temporarily to make the panel disappear
//		base.mouseHoverOnElement(projects.getAddProjectBtn());
//		driver.waitForPageToBeReady();
//		base.mouseHoverOnElement(projects.getAddProjectBtn());
		
		projects.editProject(projectName);
		driver.waitForPageToBeReady();
		
		//hovering action is performed temporarily to make the panel disappear
//		base.mouseHoverOnElement(projects.getManageProjectBtn());
//		driver.waitForPageToBeReady();
//		base.mouseHoverOnElement(projects.getProjectFolder());
		
		base.waitForElement(projects.getAddProject_SettingsTab());
		projects.getAddProject_SettingsTab().waitAndClick(10);

		base.stepInfo("Modify minimum length value with special characters and Save changes");
		base.waitForElement(projects.getMinLengthValue());
		projects.getMinLengthValue().waitAndClick(10);
		projects.getMinLengthValue().Clear();
		projects.getMinLengthValue().SendKeys("*&");
		String text = projects.getMinLengthValue().getText();
		base.printResutInReport(text.isEmpty(), "Special characters should not be accepted", "Special characters got accepted", "Pass");
		
		base.stepInfo("Enter minimum value with alphanumeric characters and save changes");
		projects.getMinLengthValue().Clear();
		projects.getMinLengthValue().SendKeys("A123");
		String text1 = projects.getMinLengthValue().getText();
		base.printResutInReport(text1.isEmpty(), "AlphaNumeric characters should not be accepted", "AlphaNumeric characters got accepted", "Pass");
		
		base.stepInfo("Enter minimum value with more than 12 and save changes");
		projects.getMinLengthValue().Clear();
		projects.getMinLengthValue().SendKeys("1234567890123");
		projects.getButtonSaveProject().waitAndClick(10);
		base.VerifyErrorMessage("The specified minimum length cannot be greater than 12.");
		
		base.stepInfo("Enter minimum value with less than or equal to 12 and save changes");
		projects.getMinLengthValue().Clear();
		projects.getMinLengthValue().SendKeys("8");
		projects.getButtonSaveProject().waitAndClick(10);
		if (projects.getBgTaskPopup().isElementAvailable(5)) {
			projects.getBtnOK().waitAndClick(10);
		}
		base.VerifySuccessMessage("Project updated successfully");
		
	}
	
	
	/**
	 * @author sowndarya Testcase No:RPMXCON-55912
	 * @Description:Validate minimum length value for DocID Format for Non-Domain project
	 **/
	
	@Test(description = "RPMXCON-55912", enabled = true, groups = { "regression" })
	public void verifyMinimumLengthValue_NonDomainProject() throws Exception {
		
		String projectName="P"+ utility.dynamicNameAppender();
		String clientName="CN"+utility.dynamicNameAppender();
		System.out.println(clientName);
		String shortName="S"+utility.dynamicRandomNumberAppender();
		String hCode="H"+utility.dynamicRandomNumberAppender();
		base = new BaseClass(driver);
		
		base.stepInfo("Test case Id: RPMXCON-55912");
		base.stepInfo("Validate minimum length value for DocID Format for Non-Domain project");
		
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a SA user :"+Input.sa1userName);
		
		projects.navigateToClientFromHomePage();
		projects.addNewClient_NonDomainProject(clientName, shortName, "Not a Domain");
		System.out.println("client is created"+clientName);
		
		projects.navigateToProductionPage();
		
		//hovering action is performed temporarily to make the panel disappear
//		base.mouseHoverOnElement(projects.getManageProjectBtn());
//		driver.waitForPageToBeReady();
//		base.mouseHoverOnElement(projects.getAddProjectBtn());
		
		projects.AddNonDomainProject(projectName, hCode);
		System.out.println("project is created"+projectName);
		projects.navigateToProductionPage();
		
		//hovering action is performed temporarily to make the panel disappear
//		base.mouseHoverOnElement(projects.getAddProjectBtn());
//		driver.waitForPageToBeReady();
//		base.mouseHoverOnElement(projects.getAddProjectBtn());
		
		projects.editProject(projectName);
		driver.waitForPageToBeReady();
		
		//hovering action is performed temporarily to make the panel disappear
//		base.mouseHoverOnElement(projects.getManageProjectBtn());
//		driver.waitForPageToBeReady();
//		base.mouseHoverOnElement(projects.getProjectFolder());
		
		base.waitForElement(projects.getAddProject_SettingsTab());
		projects.getAddProject_SettingsTab().waitAndClick(10);

		base.stepInfo("Modify minimum length value with special characters and Save changes");
		base.waitForElement(projects.getMinLengthValue());
		projects.getMinLengthValue().waitAndClick(10);
		projects.getMinLengthValue().Clear();
		projects.getMinLengthValue().SendKeys("*&");
		String text = projects.getMinLengthValue().getText();
		base.printResutInReport(text.isEmpty(), "Special characters should not be accepted", "Special characters got accepted", "Pass");
		
		base.stepInfo("Enter minimum value with alphanumeric characters and save changes");
		projects.getMinLengthValue().Clear();
		projects.getMinLengthValue().SendKeys("A123");
		String text1 = projects.getMinLengthValue().getText();
		base.printResutInReport(text1.isEmpty(), "AlphaNumeric characters should not be accepted", "AlphaNumeric characters got accepted", "Pass");
		
		base.stepInfo("Enter minimum value with more than 12 and save changes");
		projects.getMinLengthValue().Clear();
		projects.getMinLengthValue().SendKeys("1234567890123");
		projects.getButtonSaveProject().waitAndClick(10);
		base.VerifyErrorMessage("The specified minimum length cannot be greater than 12.");
		
		base.stepInfo("Enter minimum value with less than or equal to 12 and save changes");
		projects.getMinLengthValue().Clear();
		projects.getMinLengthValue().SendKeys("8");
		projects.getButtonSaveProject().waitAndClick(10);
		if (projects.getBgTaskPopup().isElementAvailable(5)) {
			projects.getBtnOK().waitAndClick(10);
		}
		base.VerifySuccessMessage("Project updated successfully");
		
	}
}