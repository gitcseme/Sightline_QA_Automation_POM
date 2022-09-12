package testScriptsRegressionSprint21;

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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Assignments_Regression_2_5 {

	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	AssignmentsPage agnmt;
	BaseClass baseClass;
	Input in;

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
		agnmt = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		sessionSearch = new SessionSearch(driver);

	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description To verify that if documents are less than the value in "Allowance of drawn document to:" then RU can draw all the documents.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-53885", enabled = true, groups = { "regression" })
	public void verifyAllowanceOfDrawDocuments() throws Exception {
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();
		String count = "8";
		SessionSearch search = new SessionSearch(driver);
		SoftAssert sa = new SoftAssert();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-53885");
		baseClass.stepInfo("To verify that if documents are less than the value in \"Allowance of drawn document to:\" then RU can draw all the documents.");
		agnmt.createAssignment_withoutSave(assignmentName, Input.codingFormName);
		agnmt.toggleEnableOrDisableOfAssignPage(true, false, agnmt.getAssgnGrp_Create_DrawPooltoggle(),
				"Draw From Pool", true);
		int purehit =search.basicContentSearch(Input.TallySearch);
		search.bulkAssignExisting(assignmentName);
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		agnmt.distributeTheGivenDocCountToReviewer(count);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		driver.waitForPageToBeReady();
		agnmt.verifyDrawPoolToggledisplay(assignmentName, "enabled");
		String DocsBeforeClickDrawAction = agnmt.getTotalDocsCountInReviewerDashboard(assignmentName).getText().trim();
		String TotalDocsBeforeClickDrawAction = DocsBeforeClickDrawAction.substring(6,9).trim();
		sa.assertEquals(TotalDocsBeforeClickDrawAction, count);
		sa.assertAll();
		baseClass.passedStep("The expected value "+TotalDocsBeforeClickDrawAction+" is displayed in reviewer page before draw action exactly");
		baseClass.waitForElement(agnmt.getAssignmentsDrawPoolInreviewerPg(assignmentName));
		agnmt.getAssignmentsDrawPoolInreviewerPg(assignmentName).Click();		
		baseClass.waitForElementToBeGone(agnmt.getAssignmentsDrawPoolInreviewerPg(assignmentName), 5);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(agnmt.getTotalDocsCountInReviewerDashboard(assignmentName));
		driver.scrollingToElementofAPage(agnmt.getTotalDocsCountInReviewerDashboard(assignmentName));
		String DocsAfterClickDrawAction = agnmt.getTotalDocsCountInReviewerDashboard(assignmentName).getText().trim();
		String TotalDocsAfterClickDrawAction = DocsAfterClickDrawAction.substring(6,9).trim();
		sa.assertEquals(Integer.toString(purehit), TotalDocsAfterClickDrawAction);
		sa.assertAll();
		baseClass.passedStep("The expected value of remaining docs "+TotalDocsAfterClickDrawAction+" is displayed in reviewer page after draw action exactly");
		baseClass.passedStep("Successfully verified that if documents are less than the value in \"Allowance of drawn document to:\" then RU can draw all the documents.");
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		agnmt.deleteAssgnmntUsingPagination(assignmentName);		
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
		System.out.println("**Executed  Assignments_Regression2_5 .**");
	}

}
