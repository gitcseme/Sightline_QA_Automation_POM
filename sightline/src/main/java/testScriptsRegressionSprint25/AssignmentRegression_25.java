package testScriptsRegressionSprint25;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocViewPage;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class AssignmentRegression_25 {

	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	AssignmentsPage assignment;
	BaseClass baseClass;
	Input in;
	SoftAssert softAssert;
	KeywordPage keyPage;

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
		assignment = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		sessionSearch = new SessionSearch(driver);
		softAssert = new SoftAssert();

	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :Verify the typing in the reviewers name acts as a \"live
	 *              filter\" of the grid in Redistribute To Other Reviewer close pop
	 *              up. RPMXCON-54401
	 */

	@Test(description = "RPMXCON-54401", enabled = true, groups = { "regression" })
	public void verifyTypingReviewersNameActsAsLiveFilterOfGridInRedistributePopUp() throws InterruptedException {
		String assignmentName = "assignment" + Utility.dynamicNameAppender();
		int expectedReviewersCountBeforeFilter = 3;
		int expectedReviewersCountAfterFilter = 1;
		
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-54401 Assignments");
		baseClass.stepInfo(
				"Verify the typing in the reviewers name acts as a \"live filter\" of the grid in Redistribute To Other Reviewer close pop up.");

		// create assignment using Bulk Assign
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignWithNewAssignmentWithPersistantHit(assignmentName, Input.codeFormName);
		baseClass.passedStep("Assignment Name : '" + assignmentName + " created.");

		// add reviewers to assignment and Distribute Documents
		assignment.editAssignmentUsingPaginationConcept(assignmentName);
		assignment.add4ReviewerAndDistribute();
		driver.waitForPageToBeReady();
		baseClass.waitTime(2);

		// Verify the typing in the reviewers name acts as a "live filter" of the grid
		// in Redistribute To Other Reviewer close pop up.
		assignment.selectReviewerAndClickRedistributeAction();
		int actualReviewersCountBeforeFilter = assignment.getListOfReviewersInRedistributePopUp().size();
		softAssert.assertEquals(actualReviewersCountBeforeFilter, expectedReviewersCountBeforeFilter);
		assignment.getLiveFilterSearchInputField().SendKeys(Input.rmu1userName);
		baseClass.waitTime(2);
		int actualReviewersCountAfterFilter = assignment.getListOfReviewersInRedistributePopUp().size();
		String actualReviewer = baseClass.availableListofElements(assignment.getListOfReviewersInRedistributePopUp())
				.get(0);
		softAssert.assertEquals(actualReviewersCountAfterFilter, expectedReviewersCountAfterFilter);
		baseClass.compareTextViaContains(actualReviewer, Input.rmu1userName,
				"Expected reviewer : '" + actualReviewer + "' is get appeared after applying Live Filter.",
				"Expected reviewer doesn't match with actual reviewer.");
		baseClass.passedStep(
				"Verified That typing in the reviewers name acts as a \"live filter\" of the grid in Redistribute To Other Reviewer close pop up.");

		// logOut
		loginPage.logout();
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
		System.out.println("**Executed  Assignment_Regression_sprint23 .**");
	}
}