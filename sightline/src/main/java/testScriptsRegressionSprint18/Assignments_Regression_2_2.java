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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Assignments_Regression_2_2 {

	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	AssignmentsPage assignPage;
	BaseClass baseClass;
	Input in;
	SoftAssert softAssertion;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		in = new Input();
		in.loadEnvConfig();
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		assignPage = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		softAssertion = new SoftAssert();
		sessionSearch = new SessionSearch(driver);

	}

	/**
	 * @author Jayanthi.Ganesan
	 * @throws InterruptedException
	 * @Description :To verify that if Documents are assigned to Reviwer then
	 *              "Unassigned Documents" is displayed as "0".
	 */
	@Test(description = "RPMXCON-53650", enabled = true, groups = { "regression" })
	public void verifyUnassignedDocsCount_DistribTab() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-53650");
		baseClass.stepInfo("To verify that if Documents are assigned to Reviwer then "
				+ "\"Unassigned Documents\" is displayed as \"0\".");
		String assgnName = "Assgn" + Utility.dynamicNameAppender();

		// Login as Reviewer Manager
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		// creating assignment
		assignPage.createAssignment(assgnName, Input.codeFormName);
		baseClass.stepInfo("Created Assignment with name  -" + assgnName);
		// performing basic search
		sessionSearch.basicContentSearch(Input.searchString1);
		String countToAssing = sessionSearch.verifyPureHitsCount();
		// performing bulk assign action and verifying doc count in assignment page.
		sessionSearch.bulkAssign();
		assignPage.assignwithSamplemethod(assgnName, "Count of Selected Docs", countToAssing);
		baseClass.waitTillElemetToBeClickable(assignPage.getAssignmentActionDropdown());
		driver.scrollPageToTop();
		assignPage.getAssignmentActionDropdown().waitAndClick(10);
		assignPage.assignmentActions("Edit");
		// adding reviewer and distributing docs
		assignPage.addReviewerAndDistributeDocs();
		// taking unassigned docs count
		driver.Navigate().refresh();
		assignPage.getDistributeTab().Click();
		String uassignedDocCount = assignPage.getEditAggn_Distribute_Unassgndoc().getText();
		baseClass.compareTextViaContains(uassignedDocCount, "0",
				"Count of UnAssigned docs under distribute tab  Displayed 0.",
				"Count of UnAssigned docs in distribute  Displayed is " + uassignedDocCount);

		assignPage.deleteAssgnmntUsingPagination(assgnName);

		// logout
		loginPage.logout();
	}

	@DataProvider(name = "Users")
	public Object[][] CombinedSearchwithUsers() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password }, { Input.pa1userName, Input.pa1password },
				{ Input.rev1userName, Input.rev1password } };
		return users;
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
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
		System.out.println("**Executed  Assignments_Regression2_2 .**");
	}

}
