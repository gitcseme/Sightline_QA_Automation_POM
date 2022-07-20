package testScriptsRegressionSprint17;

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
import pageFactory.DocExplorerPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Assignments_Regression_2_1 {

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
	 * @author
	 * @throws InterruptedException
	 * @Date: 07/13/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : To verify that RMU is able to view Assingment Group and
	 *              Assignments tree in Assign/Unassign Document pop up..
	 *              RPMXCON-53633
	 */
	@Test(description = "RPMXCON-53633", enabled = true, groups = { "regression" })
	public void verifyRmuAbleToViewAssignmentGroupAndAssignmentTreeInAssignOrUnAssignDocumentPopUp() {

		baseClass.stepInfo("Test case Id: RPMXCON-53633");
		baseClass.stepInfo(
				"To verify that RMU is able to view Assingment Group and Assignments tree in Assign/Unassign Document pop up.");

		// Login as Reviewer Manager
		baseClass.stepInfo(
				"1. Login to the application as RM User.  2. Goto Search --> New Search.  3. Enter text to search and click on Search.  4. Drag the Tile under Selected Results and from drop down select Bulk Assign.");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// performing basic search
		sessionSearch.basicContentSearch(Input.searchString1);

		// performing bulk assign action
		sessionSearch.bulkAssign();

		// verifying the presence of Assign/Unassigned pop heading
		baseClass.ValidateElement_Presence(sessionSearch.getbulkassgnpopup(), "Assign/Unassigned pop");

		// verifying whether the Assign Documents is selected by Default
		String bgColorRbg = sessionSearch.getBulkAssignAssignDocumentsButton().GetCssValue("border-color");
		String bgColorHexa = baseClass.rgbTohexaConvertorCustomized(bgColorRbg, 4);
		System.out.println(bgColorHexa);
		baseClass.textCompareEquals(bgColorHexa, Input.progresBarColor,
				" Assign Document option is selected by default", " Assign Document option is not selected by default");

		// verifying whether the Existing Assignment Tab is selected by Default
		baseClass.printResutInReport(
				baseClass.ValidateElement_PresenceReturn(sessionSearch.getBulkAssignSelectedExistingAssignment()),
				"Existing Assignment tab is selected by Default.",
				"Existing Assignment tab is not selected by Default.", "Pass");

		// verifying the presence of tree of Assignment Group and Assignments
		baseClass.ValidateElementCollection_Presence(assignPage.getSelectcopyAssgnmToBulkAssign(),
				"Tree of Assignment Group and Assignments");

		// logout
		loginPage.logout();
	}

	/**
	 * @author Jeevitha
	 * @throws InterruptedException
	 * @Date: 07/13/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : To verify that only assigned documents are displayed in Mini
	 *              DocList. RPMXCON-54045
	 */
	@Test(description = "RPMXCON-54045", enabled = true, groups = { "regression" })
	public void verifyThatOnlyAssignedDocumentsDisplayedInMiniDocList() throws InterruptedException {

		String assgngrpName = "assgnGrp" + Utility.dynamicNameAppender();
		String assignmentName = "assignment" + Utility.dynamicNameAppender();
		baseClass.stepInfo("Test case Id: RPMXCON-54045");
		baseClass.stepInfo("To verify that only assigned documents are displayed in Mini DocList");

		// Login as Reviewer Manager
		baseClass.stepInfo("**Step-1 Login to RPMX as RMU**");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// creating assignment Group
		baseClass.stepInfo("**Step-2 & 3 Go to Manage --> Assignment and Select Assignment Group from tree view**");
		assignPage.navigateToAssignmentsPage();
		assignPage.createCascadeAssgnGroupWithSortBymetadata(assgngrpName);
		baseClass.stepInfo("Assignment Group is Created");

		// creating assignment under newly created Assignment group
		assignPage.selectAssignmentGroup(assgngrpName);
		assignPage.createAssignmentFromAssgnGroup(assignmentName, Input.codeFormName);

		// assign documents to created assignment
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assignmentName);

		// assigning reviewers to the Assignment and distributing the documents to the
		// reviewers
		baseClass.stepInfo(
				"**Step-4 Select Assignment from grid view which has documents and Reviewers assigned.    Select Edit Assignment from Action Dropdown**");
		assignPage.selectAssignmentGroup(assgngrpName);
		assignPage.getSelectAssignment(assignmentName).waitAndClick(5);
		assignPage.getAssignmentActionDropdown().waitAndClick(10);
		assignPage.assignmentActions("Edit");

		baseClass.stepInfo("**Step-5 Select Manage Reviewer tab**");
		assignPage.add2ReviewerAndDistribute();
		baseClass.passedStep("reviewers assigned to the Assignment and documents distributed to the reviewers");

		// verifying the count of document assigned to the reviewer with count of
		// documents listed in mini docList
		baseClass.stepInfo(
				"**Step-6 Select Reviewers from Grid View which has documents assigned.    From Action dropdown select View All Docs in DocView.    Verify that only assigned documents to reviewer should be displayed in Mini DocList.**");
		assignPage.Assignment_ManageRevtab_ViewinDocView();
		baseClass.passedStep(
				"verified that count of documents assigned to reviewer match with count of documents listed in Mini DocList");

		// logOut
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
		System.out.println("**Executed  QuickBatchRegression2_1 Regression5**");
	}

}
