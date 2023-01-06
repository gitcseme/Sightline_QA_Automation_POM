package sightline.projects;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import testScriptsSmoke.Input;

public class Projects_ToggleOnna_Consilio {

	
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	ProjectPage projects;
	@BeforeClass(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		
		
		  //Input input = new Input(); input.loadEnvConfig();
		 


		driver = new Driver();
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		projects = new ProjectPage(driver);
		UtilityLog.info("Logged in as User: " + Input.sa1userName);
		Reporter.log("Logged in as User: " + Input.sa1password);

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());
	}

	
	@Test(description = "RPMXCON-70305", alwaysRun = true,groups={"regression"})
	public void toggleOptionVisible() throws Exception {	
	
		baseClass.stepInfo("Test case Id: RPMXCON-70305");
		baseClass.stepInfo("Verify that while Editing a Project toggle option \"Sightline Collect, powered by Onna” (SCpbO)\" "
				+ "with an ON/OFF toggle option appears at the bottom of Project screen.");
		
		baseClass.stepInfo("Login to SAU");
		
		baseClass.stepInfo("Select project from Manage");
		projects.clickingManageButton();
		baseClass.stepInfo("select project and click edit");
		projects.editProject(Input.projectName);		
		driver.scrollingToBottomofAPage();
		
		if(projects.getSightlineOnnaToggle().isDisplayed()) {
			
			Assert.assertTrue(true);
			
		}
		
		else {
			
			Assert.assertTrue(false);
		}
		
		baseClass.stepInfo("Checking Status of toggle button");
		Assert.assertFalse(projects.getSightlineOnnaToggleStatus().Selected());
			
		baseClass.passedStep("Checked default status of toggle");
	
		baseClass.stepInfo("Sightline Collect, powered by Onna” (SCpbO) with an ON/OFF toggle option appears at the bottom of Project screen ");
		loginPage.logout();
	}
	
}