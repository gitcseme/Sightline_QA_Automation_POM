
package sightline.reports;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import automationLibrary.Driver;
import pageFactory.BaseClass;
import pageFactory.ConceptExplorerPage;
import pageFactory.LoginPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ConceptExplorer_Phase1_Regression {
	Driver driver;
	LoginPage lp;
	BaseClass bc;
	ConceptExplorerPage CEPage;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		Input in = new Input();
		in.loadEnvConfig();

	}

	/**
	 * @author Jayanthi.ganesan
	 * @param username
	 * @param password
	 * @param role
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-56386", dataProvider = "Users_PARMU", groups = { "regression" })
	public void conceptExplorerDisplay(String username, String password, String role) throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-56386");
		bc.stepInfo("To verify that Users are able to view filters on Concept Explorer Page");
		lp.loginToSightLine(username, password);
		driver.waitForPageToBeReady();
		bc.stepInfo("Logged in as -" + role);
		CEPage = new ConceptExplorerPage(driver);
		CEPage.navigateToConceptExplorerPage();
		bc.waitForElement(CEPage.getfilterDocumentsBy());
		SoftAssert softAssertion = new SoftAssert();
		if (CEPage.getTally_SelectSource().isDisplayed() && CEPage.getfilterDocumentsBy().isDisplayed()) {
			softAssertion.assertTrue(CEPage.getfilterOptions().isDisplayed());
			bc.passedStep("Select Source and filter documents by option is displayed in Concept Explorer Report Page");
		} else {
			bc.failedStep("Select source and filter documents option is not displayed");
			softAssertion.assertTrue(false);
		}
		softAssertion.assertAll();
	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		lp = new LoginPage(driver);
		bc = new BaseClass(driver);

	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
		}
		try {
			lp.logout();
			lp.quitBrowser();
		} catch (Exception e) {
			lp.quitBrowser();
		}

		System.out.println("Executed :" + result.getMethod().getMethodName());

	}

	@DataProvider(name = "Users_PARMU")
	public Object[][] PA_RMU() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "RMU" },
				{ Input.pa1userName, Input.pa1password, "PA" } };
		return users;
	}

	@AfterClass(alwaysRun = true)
	public void close() {

		System.out.println("Executed :Concept Explorer Regression ");
	}
}
