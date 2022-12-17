package testScriptsRegressionSprint28;

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
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Assignment_Regression28 {

	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	AssignmentsPage assignment;
	BaseClass base;
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
		base = new BaseClass(driver);
		sessionSearch = new SessionSearch(driver);
		softAssert = new SoftAssert();

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

	/**
	 * @author NA Testcase No:RPMXCON-54495
	 * @Description:To Verify 'Keep families together' field enabled/disabled
	 **/
	@Test(description = "RPMXCON-54495", enabled = true, groups = { "regression" })
	public void verifykeepFamilyTogether() throws Exception {
		AssignmentsPage assignment = new AssignmentsPage(driver);
		SoftAssert asserts = new SoftAssert();

		String assignmentGRP = "AssigGRP" + Utility.dynamicNameAppender();

		base.stepInfo("RPMXCON-54495");
		base.stepInfo("To Verify 'Keep families together' field enabled/disabled");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("logged in As : " + Input.rmu1userName);

		assignment.navigateToAssignmentsPage();
		driver.waitForPageToBeReady();
		assignment.createCascadeNonCascadeAssgnGroup_withoutSave(assignmentGRP, "yes");
		assignment.toggleEnableOrDisableOfAssignPage(true, false, assignment.getAssgnGrp_Create_DrawPooltoggle(),
				"Draw From Pool", true);

		assignment.editAssgnGrp(assignmentGRP, "No");
		driver.waitForPageToBeReady();
		assignment.checkForToggleEnable(assignment.getAssgn_keepFamiliesTogetherToggle());
		assignment.checkForToggleEnable(assignment.getAssgn_keepEmailThreadTogetherToggle());
		assignment.checkForToggleEnable(assignment.getAssgnGrp_Create_DrawPooltoggle());

		assignment.navigateToAssignmentsPage();
		driver.waitForPageToBeReady();
		assignment.editAssgnGrp(assignmentGRP, "NO");
		driver.waitForPageToBeReady();
		assignment.toggleEnableOrDisableOfAssignPage(false, true, assignment.getAssgnGrp_Create_DrawPooltoggle(),
				"Draw From Pool", true);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		assignment.navigateToAssignmentsPage();
		driver.waitForPageToBeReady();
		assignment.editAssgnGrp(assignmentGRP, "No");
		driver.waitForPageToBeReady();
		boolean drawPool = assignment.verifyToggleEnableORDisabled(assignment.getAssgnGrp_Create_DrawPooltoggle(),
				"Draw From Pool");
		boolean keepEmail = assignment.verifyToggleEnableORDisabled(assignment.getAssgn_keepEmailThreadTogetherToggle(),
				"keep Email Toogle");
		boolean keepFamily = assignment.verifyToggleEnableORDisabled(assignment.getAssgn_keepFamiliesTogetherToggle(),
				"Keep Family Toogle");
		asserts.assertFalse(drawPool);
		asserts.assertFalse(keepEmail);
		asserts.assertFalse(keepFamily);
		asserts.assertAll();
		base.passedStep("verified - 'Keep families together' field enabled/disabled");
		loginPage.logout();

	}

	/**
	 * @author
	 * @Description :Verify the functionality of Redistribution for uneven numbers
	 *              of Documents is following "off the top" logic. [RPMXCON-54402]
	 */
	@Test(description = "RPMXCON-54402", groups = { "regression" })
	public void verifyFunctionalityOfRedistributionForUnevenDocumentFollowingOffTopLogics()
			throws InterruptedException {

		List<String> listOfReviewers = new ArrayList<String>(Arrays.asList("PA", "RMU"));
		String assignmentName = "assignment" + Utility.dynamicNameAppender();

		base.stepInfo("RPMXCON-54402  Assignment");
		base.stepInfo(
				"verify the functionality of Redistribution for uneven numbers of Documents is following \"off the top\" logic.");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// performing basic search and bulk assign
		base.stepInfo("performing basic search and bulk assign.");
		int pureHitCount = sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();

		// creating assignment
		assignment.assignmentCreationAsPerCf(assignmentName, Input.codeFormName);
		base.stepInfo("Assignment Created : " + assignmentName);

		// adding reviewers and distributing the documents to reviewers.
		base.stepInfo("adding reviewers and distributing the documents to reviewers.");
		int docCountToDistribute = assignment.oddOrEvenDocumentCountToDistribute(pureHitCount, "odd");
		assignment.distributeTheGivenDocCountToReviewer(Integer.toString(docCountToDistribute));
		assignment.addReviewers(listOfReviewers);

		// redistributing the documents to reviewers.
		base.stepInfo("redistributing the documents to reviewers.");
		assignment.selectReviewerAndClickRedistributeAction();
		base.waitForElement(assignment.getSelectReviewerInRedistributedDocsTab(Input.pa1userName));
		assignment.getSelectReviewerInRedistributedDocsTab(Input.pa1userName).waitAndClick(5);
		base.waitForElement(assignment.getSelectReviewerInRedistributedDocsTab(Input.rmu1userName));
		assignment.getSelectReviewerInRedistributedDocsTab(Input.rmu1userName).waitAndClick(5);
		assignment.getAssgn_Redistributepopup_save().waitAndClick(10);
		driver.Navigate().refresh();
		base.waitForElement(assignment.getAssignment_ManageReviewersTab());
		assignment.getAssignment_ManageReviewersTab().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.waitTime(2);

		// Verify the Redistribution calculation happened among Reviewer 2 and reviewer
		// 3 From reviewer 1 Batch.
		int leastDocCount = (int) (docCountToDistribute / 2);
		int expectedPaDocCount = leastDocCount;
		driver.waitForPageToBeReady();
		base.waitForElement(assignment.getDistributedDocs(Input.pa1userName));
		int actualPaDocCount = Integer.parseInt(assignment.getDistributedDocs(Input.pa1userName).getText());
		base.digitCompareEquals(expectedPaDocCount, actualPaDocCount,
				"Expected document count : '" + expectedPaDocCount + "' match with actual document count : '"
						+ actualPaDocCount + "'",
				"Expected document count : '" + expectedPaDocCount + "' doesn't match with actual document count : '"
						+ actualPaDocCount + "'");
		int expectedRmuDocCount = leastDocCount + 1;
		driver.waitForPageToBeReady();
		base.waitForElement(assignment.getDistributedDocs(Input.rmu1userName));
		int actualRmuDocCount = Integer.parseInt(assignment.getDistributedDocs(Input.rmu1userName).getText());
		base.digitCompareEquals(expectedRmuDocCount, actualRmuDocCount,
				"Expected document count : '" + expectedRmuDocCount + "' match with actual document count : '"
						+ actualRmuDocCount + "'",
				"Expected document count : '" + expectedRmuDocCount + "' doesn't match with actual document count : '"
						+ actualRmuDocCount + "'");
		base.stepInfo(
				"Verified that Redistribution calculation happened among Reviewer 2 and reviewer 3 From reviewer 1 Batch.");

		// LogOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Description :Verify that - Application returns all the documents which are
	 *              available under Assignments - Distributed To in search
	 *              result. [RPMXCON-48112]
	 */
	@Test(description = "RPMXCON-48112", enabled = true, groups = { "regression" })
	public void verifyApplicationReturnsAllDocumentsAvailableUnderAssignments() throws InterruptedException {

		AssignmentsPage assignment = new AssignmentsPage(driver);
		String assignmentName = "assignment" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-48112");
		base.stepInfo(
				"Verify that - Application returns all the documents which are available under Assignments - Distributed To in search result.");

		// login
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// performing basic search and bulkAssign
		base.stepInfo("performing basic search and bulkAssign.");
		int pureHit = sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();

		// creating Assignment
		assignment.assignmentCreationAsPerCf(assignmentName, Input.codeFormName);
		int docDistributeCount = Math.round(pureHit / 2);

		// adding Reviewer and Distributing the documents to the Reviewer
		base.stepInfo("adding Reviewer and Distributing the documents to the Reviewer.");
		assignment.distributeTheGivenDocCountToReviewer(Integer.toString(docDistributeCount));

		// getting Total Documents In Assignment
		assignment.navigateToAssignmentsPage();
		int assignmentDocCount = Integer.parseInt(assignment.selectAssignmentToView(assignmentName));
		base.stepInfo("Total Documents In Assignment '" + assignmentName + "' : " + assignmentDocCount);

		// performing Assignment work product search.
		base.stepInfo("performing Assignment work product search.");
		base.selectproject();
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.workProductSearch("assignments", assignmentName, true);
		int WPAssignPureHitCount = sessionSearch.serarchWP();

		// Verify that - Application returns all the documents which are available under
		// Assignments - Distributed To in search result.
		base.digitCompareEquals(assignmentDocCount, WPAssignPureHitCount,
				"Verified that Application returns all the documents which are available under Assignments - Distributed To  in search result.",
				"pureHit doesn't match with Assignment Document count.");

		// logOut
		loginPage.logout();
	}

}
