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
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.ClientsPage;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Projects_Regression28 {


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
	 * @author NA Testcase No:RPMXCON-55570
	 * @Description:To verify that user can delete the Client.
	 **/
	@Test(description = "RPMXCON-55570", enabled = true, groups = { "regression" })
	public void verifyUserCanDeleteClient() throws Exception {
		ProjectPage project = new ProjectPage(driver);
		ClientsPage client = new ClientsPage(driver);
		SoftAssert asserts = new SoftAssert();
		
		String clientname = "" + Utility.dynamicNameAppender();
		String shrtType = "" + Utility.randomCharacterAppender(4); 
		
		baseClass.stepInfo("RPMXCON-55570");
		baseClass.stepInfo("To verify that user can delete the Client.");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in As : " + Input.sa1userName);
		
		project.navigateToClientFromHomePage();
		project.addNewClient(clientname, shrtType, "Domain");
		
		client.filterClient(clientname);
		baseClass.waitForElement(client.getClientDeleteBtn(clientname));
		asserts.assertTrue(client.getClientDeleteBtn(clientname).isElementAvailable(5));
		asserts.assertAll();
		baseClass.stepInfo(clientname + " Client Created Successfully");
		client.deleteClinet(clientname);	
		client.filterClient(clientname);
		baseClass.waitForElement(client.getClientDeleteBtn(clientname));
		asserts.assertFalse(client.getClientDeleteBtn(clientname).isElementAvailable(2));
		asserts.assertAll();
		baseClass.stepInfo(clientname + " Client Deleted Successfully");
		baseClass.passedStep("Verified - that user can delete the Client.");
		loginPage.logout();
	}
	
	/**
	 * @author  TestCase id:55585 DATE:NA
	 * @Description:UI_To verify that error message is displayed if user enters the
	 *                    invalid value for No of Documents.
	 */
	@Test(description = "RPMXCON-55585", enabled = true, groups = { "regression" })
	public void verifyErrorMsgDisplayedInvalidValue() throws Exception {
		ProjectPage project = new ProjectPage(driver);
		
		baseClass.stepInfo("Testcase -RPMXCON-55585  project");
		String DocNumber = "Abc";
		String DocNumber1 = "-234";
		String DocNumber2 = "34#";

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo(
				"UI_To verify that error message is displayed if user enters the invalid value for No of Documents.");
		project.navigateToProjectsPage();

		// verify no of doc error Message
		baseClass.waitForElement(project.getAddProjectBtn());
		project.getAddProjectBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		project.verifyDocumentNumberErrorMessage(DocNumber);
		driver.waitForPageToBeReady();
		project.verifyDocumentNumberErrorMessage(DocNumber1);
		driver.waitForPageToBeReady();
		project.verifyDocumentNumberErrorMessage(DocNumber2);
	}
}
