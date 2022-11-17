package testScriptsRegressionSprint26;

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
import pageFactory.ClientsPage;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Projects_Regression26 {

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
	
	/**
	 * @author NA Testcase No:RPMXCON-56177
	 * @Description:Verify if Client Type is edited from Domain to Non-Domain then Processing Engine section does not displays
	 **/
	@Test(description = "RPMXCON-56177", enabled = true, groups = { "regression" })
	public void verifyClientEditDomainToNonDomain() throws Exception {
		
		String projectName = "Project" + Utility.dynamicNameAppender();
		String clientName = "c"+ Utility.dynamicNameAppender();
		String shrtName = Utility.randomCharacterAppender(5);
		String type ="Domain";
		String hcode = "hcode" + Utility.dynamicNameAppender();
		
		base.stepInfo("RPMXCON - 56177");
		base.stepInfo("To Verify if Client Type is edited from Domain to Non-Domain then Processing Engine section does not displays");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Logged in As : " + Input.sa1userName);
		
		ProjectPage project = new ProjectPage(driver);
		project.navigateToClientFromHomePage();
		driver.waitForPageToBeReady();
		project.addNewClient(clientName, shrtName, type);
		
		ClientsPage client = new ClientsPage(driver);
		client.navigateToClientPage();
		driver.waitForPageToBeReady();
		client.filterClient(clientName);	
		base.waitForElement(project.getEditBtn());
		project.getEditBtn().waitAndClick(5);	
		base.waitForElement(project.getSelectEntity());
		project.getSelectEntity().selectFromDropdown().selectByVisibleText("Not a Domain");	
		base.waitForElement(project.getClientNameSaveBtn());
		project.getClientNameSaveBtn().waitAndClick(10);
		
		project.navigateToProductionPage();
		driver.waitForPageToBeReady();
		driver.waitForPageToBeReady();		
		project.verifyProcessingEngineSectionCtrProj(projectName, clientName, hcode);
		
		project.navigateToProductionPage();
		driver.waitForPageToBeReady();
		project.filterTheProject(projectName);
		base.waitForElement(project.getEditProject(projectName));
		if(project.getEditProject(projectName).isElementAvailable(5)){
			base.passedStep("Project Created Successfully As Expected ");
		} else {
			base.failedStep("Project Not Created Successfully");
		}
		base.passedStep("Verified - if Client Type is edited from Domain to Non-Domain then Processing Engine section does not displays");
		loginPage.logout();
	}
}
