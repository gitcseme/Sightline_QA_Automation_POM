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
import pageFactory.DocListPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BulkActions_Regression_21 {

	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	BaseClass baseClass;
	Input in;
	SoftAssert assertion;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		in = new Input();
		in.loadEnvConfig();
	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		loginPage = new LoginPage(driver);
		sessionSearch = new SessionSearch(driver);
		baseClass = new BaseClass(driver);
		assertion = new SoftAssert();
		savedSearch = new SavedSearch(driver);

	}

	/**
	 * @author Raghuram.A
	 * @Date: 16/9/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :To verify that while the 'Total Selected Count' is In Progress,
	 *              Continue button should be disabled. RPMXCON-54489
	 */
	@Test(description = "RPMXCON-54489", groups = { "regression" }, enabled = true)
	public void verifyBulkAssignCountLoadActions() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-54489 Bulk Actions");
		baseClass.stepInfo(
				"To verify that while the 'Total Selected Count' is In Progress, Continue button should be disabled.");

		// login as Users
		baseClass.stepInfo("**Step-1 Login as User**");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// performing searching and saving it in newly created node
		baseClass.stepInfo(
				"**Step-2 Go to Search   Search by any keyword  Select Pure hits and add into shopping cart**");
		sessionSearch.basicContentSearch(Input.searchStringStar);

		// Add purehit and perform Bulk Action
		baseClass.stepInfo("**Step-3 Select Bulk Assign action**");
		sessionSearch.checkAddPureHit();
		sessionSearch.performBulkActionM("Bulk Assign");
		driver.waitForPageToBeReady();

		// 'Total Selected Document' count is in progress Select Existing assignment
		baseClass.stepInfo("**Step-4 'Total Selected Document' count is in progress    Select Existing assignment**");
		baseClass.stepInfo("Verify Continue button is in Disabled state");
		sessionSearch.verifyContinuButtonIsStatus("Disabled", "existingassignment"); // newassignmentdiv

		// Verify Continue button is disabled from Unassign document section
		baseClass.stepInfo("**Step-5 Verify Continue button is disabled from Unassign document section**");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		sessionSearch.performBulkActionM("Bulk Assign");
		driver.waitForPageToBeReady();
		sessionSearch.unassignClick();

		baseClass.stepInfo("Verify Continue button is in Disabled state in Unassign");
		sessionSearch.verifyContinuButtonIsStatus("Disabled", "divUnAssignDocuments"); // newassignmentdiv

		// logout
		loginPage.logout();
	}

	/**
	 * @author Raghuram.A
	 * @Date: 16/9/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :To verify that in Bulk Assign, the Parent level sampling method
	 *              is working as expected. RPMXCON-54463
	 */
	@Test(description = "RPMXCON-54463", groups = { "regression" }, enabled = true)
	public void verifyParentDocCountDisplayedAsExpected() throws InterruptedException {

		AssignmentsPage assign = new AssignmentsPage(driver);
		DocListPage docList = new DocListPage(driver);

		String finalCount;
		String docCount;
		String searchName = "savedSearch" + Utility.dynamicNameAppender();
		String assignName = "assignName" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id: RPMXCON-54463 Bulk Actions");
		baseClass.stepInfo("To verify that in Bulk Assign, the Parent level sampling method is working as expected");

		// login as Users
		baseClass.stepInfo("**Step-1 Login as User**");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// creating new Search Group under My Saved Search
		String nodeName = savedSearch.createSearchGroupAndReturn(Input.mySavedSearch, "RMU", "No");

		// Assignment creation
		baseClass.stepInfo("**Step-2 Create new assignment from assignments**");
		assign.createAssignment(assignName, Input.codingFormName);

		// performing searching and saving it in newly created node
		baseClass.stepInfo("**Step-3 Go to saved searches and click on assign to bulk assign**");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearchInNewNode(searchName, nodeName);

		// Navigate to SavedSearch Page
		savedSearch.navigateToSSPage();
		savedSearch.selectNode1(nodeName);

		// Bulk Assign
		savedSearch.getSavedSearchToBulkAssign().waitAndClick(5);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Clicked Assign Icon");

		// Bulk Assign via SavedSearch
		baseClass.stepInfo("**Step-4 Select assignment name and sampling method as Parent level and finalize**");
		finalCount = assign.assignwithSamplemethod(assignName, "Parent Level Docs Only", null);

		baseClass.stepInfo("**Step-5 Go to assignments and select the assignment  created**");
		assign.Viewindoclistfromassgn(assignName);
		driver.waitForPageToBeReady();

		// Parent doc count verification
		baseClass.stepInfo(
				"**Step-6 Navigate to doc list of assignment and check if the parent level docs are assigned**");
		docCount = docList.verifyingDocCount();

		// Doc count comparision
		baseClass.stepInfo("Verify that count displayed in the \"Total\" are the count of Parent Documents only.");
		baseClass.textCompareEquals(finalCount, docCount, "Only Parent Level documents are listed in the doclist page",
				"Other than parent level docs are listed - Count mismatches");

		// Delete created Node
		baseClass.stepInfo("Initiating delete nodes");
		savedSearch.navigateToSavedSearchPage();
		savedSearch.deleteNode(Input.mySavedSearch, nodeName);

		// logout
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
		System.out.println("**Executed Advanced search AdvanceSearchRegression_2_21**");
	}

}
