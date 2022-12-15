package sightline.assignments;

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
import pageFactory.BaseClass;
import pageFactory.ClientsPage;
import pageFactory.LoginPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ClientsPage_Regression {
	Driver driver;
	LoginPage loginPage;
	ClientsPage clientsPage;

	BaseClass baseClass;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		Input in = new Input();
		in.loadEnvConfig();
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		// Open browser
		
	}

	@Test(description ="RPMXCON-54960",groups = { "regression" })
	public void validateHelpPopUpWhenHoveringInClientPg() throws InterruptedException, ParseException, IOException {
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		clientsPage = new ClientsPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-54960");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA user");
		clientsPage.verifyHelpTextPopUpWhenHovering();
		loginPage.logout();
	}

	@Test(description ="RPMXCON-54958,RPMXCON-54959",groups = { "regression" })
	public void verifyHelpTextPopUpWhenClickingInClientPg() throws InterruptedException, ParseException, IOException {
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		clientsPage = new ClientsPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-54958, RPMXCON-54959");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA user");
		clientsPage.verifyHelpTextPopUpWhenClicking();
		loginPage.logout();
	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();

		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		clientsPage = new ClientsPage(driver);

	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		baseClass = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
		}
		try {			
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)
	public void close() {		
		System.out.println("Executed the clients regression class");
			
	}

}
