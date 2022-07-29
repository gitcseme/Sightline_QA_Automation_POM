package testScriptsRegressionSprint18;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.TallyPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class AdvancedSearchRegression {
	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	TagsAndFoldersPage tagPage;
	AssignmentsPage assignPage;
	BaseClass baseClass;
	Input in;
	SoftAssert assertion;
	String tagHitsCount;
	String tagName = "Tag" + Utility.dynamicNameAppender();

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		in = new Input();
		in.loadEnvConfig();
		// Open browser
		driver = new Driver();

		sessionSearch = new SessionSearch(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver); 

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Pre requesite creation
		// Performing Star search since this will add all avail docs from default sec
		// group .
		sessionSearch.basicContentSearch(Input.searchStringStar);
		tagHitsCount = sessionSearch.verifyPureHitsCount();// expected value
		sessionSearch.bulkTag(tagName);
		baseClass.stepInfo("Created a Tag " + tagName + "Count of docs bulk tagged " + tagHitsCount);
		loginPage.quitBrowser();

	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		sessionSearch = new SessionSearch(driver);
		savedSearch = new SavedSearch(driver);
		tagPage = new TagsAndFoldersPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		assertion = new SoftAssert();
	}

	@DataProvider(name = "searchTerms")
	public Object[][] searchTerms() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "\"Good Morn!ing2\"" },
				{ Input.pa1userName, Input.pa1password, "\"Good Morn~ing1\"" },
				{ Input.rev1userName, Input.rev1password, "\"Good Morn@ing3\"" } };
		return users;
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that warning message appears in Advanced Search when a
	 *              user configured an audio search term with any special characters
	 *              and performed "copy to New Search"
	 * @param username
	 * @param password
	 * @param User
	 * @throws ParseException
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-49368", dataProvider = "searchTerms", enabled = true, groups = { "regression" })
	public void verifyWarningMessageAndCopyToNew(String username, String password, String searchTerm)
			throws ParseException, Exception {

		// login as User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-49368  Advanced Search");
		baseClass.stepInfo(
				"Verify that warning message appears in Advanced Search when a user configured an audio search term with any special characters and performed \"copy to New Search\"");

		// configure audio query
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.CreateDraftaudioSearchQuery(Input.audioSearchString1, Input.language);
		baseClass.hitEnterKey(1);

		// edit query and add spcl character
		sessionSearch.modifySearchTextArea(1, Input.audioSearchString1, "\"Good Morn~ing1\"", "Save");
		driver.waitForPageToBeReady();

		// verify copy to new search action
		sessionSearch.verifyCopyToNewSearch();

		// verify warning message
		sessionSearch.verifyWarningMessage(true, 1);

		// logOut
		loginPage.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description : To verify as an PA user login into the Application, I will be
	 *              able to select multiple Security Group as an search criteria & I
	 *              am able to search a query based on that
	 * @throws ParseException
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-57076", enabled = true, groups = { "regression" })
	public void verifySearchForSG() throws ParseException, Exception {

		// login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		baseClass.stepInfo("Test case Id: RPMXCON-57076  Advanced Search");
		baseClass.stepInfo(
				"To verify as an PA user login into the Application, I will be able to select multiple Security Group as an search criteria & I am able to search a query based on that");

		// select security group in WP
		sessionSearch.switchToWorkproduct();
		sessionSearch.workProductSearch("security group", Input.securityGroup, false);

		// configure content query
		sessionSearch.advancedContentSearchWithSearchChanges(Input.searchString2, "No");
		int purehit = sessionSearch.saveAndReturnPureHitCount();
		baseClass.stepInfo("Search result is dispalyed and purehit is : " + purehit);

		// logOut
		loginPage.logout();

	}

	/**
	 * @Author Jayanthi
	 * @Description : Verify that - When OR and NOT are part of literal(Folder Name)
	 *              then it does not treat as operator and returns all the documents
	 *              on Advanced search screen.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-50046", enabled = true, groups = { "regression" })
	public void verifyBulkFolderWithOperators() throws Exception {

		// login as PA
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		baseClass.stepInfo("Test case Id: RPMXCON-50046  Advanced Search");
		baseClass.stepInfo(
				"Verify that - When OR and NOT are part of literal(Folder Name) then it does not treat as operator "
						+ "and returns all the documents on Advanced search screen");
		String fodlerNAme = "Private OR Hidden NOT Technical" + Utility.dynamicNameAppender();
		// performing basic search
		sessionSearch.basicContentSearch(Input.searchString1);
		String countToBulkFolder = sessionSearch.verifyPureHitsCount();

		// performing bulk folder action with fodler name (OR and NOT are part of name )
		// and verifying doc count in sessionsearch page .
		sessionSearch.bulkFolder(fodlerNAme);
		baseClass.stepInfo("folder created with  name  " + fodlerNAme + "OR and NOT are part of literal " + "and "
				+ countToBulkFolder + " docs were bulk foldered to it");
		baseClass.selectproject();
		// select folder(OR and NOT are part of name ) in WP
		sessionSearch.switchToWorkproduct();
		sessionSearch.workProductSearch("folder", fodlerNAme, false);
		baseClass.stepInfo("Configured Seach query under work product tab  with folder : " + fodlerNAme);

		int purehit = sessionSearch.saveAndReturnPureHitCount();
		baseClass.stepInfo("Search result is dispalyed and purehit is : " + purehit);
		String passMsg = "When OR and NOT are part of literal(Folder Name) then it does not treat as operator "
				+ "and returns all the documents on Advanced search screen";
		String failMsg = "When OR and NOT are part of literal(Folder Name) then the document returned  are not as expected"
				+ " on Advanced search screen";
		baseClass.textCompareEquals(countToBulkFolder, String.valueOf(purehit), passMsg, failMsg);

		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagPage.deleteAllFolders(fodlerNAme);

		// logOut
		loginPage.logout();

	}

	/**
	 * @author Jayanthi.ganesan
	 * @description:Verify that - Application returns all the documents which are
	 *                     available under selected group and Assignments -
	 *                     distributed with AND operator in search result.
	 */
	@Test(description = "RPMXCON-57168", groups = { "regression" }, enabled = true)
	public void verifyDocsCntDistributedAssgnments_AND() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-57168");
		baseClass.stepInfo(
				"Verify that - Application returns all the documents which are available under selected group and "
						+ "Assignments - distributed with AND operator in search result.");
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");
		AssignmentsPage agnmt = new AssignmentsPage(driver);

		baseClass.stepInfo("**Pre Requesite Assignment Creation/Docs distribution/completing docs.**");

		agnmt.createAssignment(assignmentName, Input.codeFormName);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkAssignExisting(assignmentName);

		baseClass.selectproject();
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		agnmt.assignmentDistributingToReviewerManager();

		// navigating from Dashboard to DocView
		DocViewPage docViewPage = new DocViewPage(driver);
		docViewPage.selectAssignmentfromDashborad(assignmentName);
		baseClass.stepInfo("Doc is viewed in the docView Successfully");
		docViewPage.CompleteTheDocumentInMiniDocList(3);

		baseClass.selectproject();
		sessionSearch.navigateToAdvancedSearchPage();
		// Adding WP tag into search text box
		sessionSearch.workProductSearch("tag", tagName, true);
		baseClass.stepInfo(
				"Inserting a query with  a Tag " + tagName + "and Count of docs avail in that tag is " + tagHitsCount);
		// Adding Operator into search text box
		sessionSearch.selectOperator("AND");
		baseClass.stepInfo("Selecting AND Operator");

		// Adding WP assignment Assigned status into search text box
		baseClass.stepInfo("Selecting distributed /Completed status/Date range assignment into Query");
		sessionSearch.selectAssignmentAndReviewers_Status_dateRange(assignmentName, Input.rmu1userName, null, null,
				"Completed", true);
		baseClass.stepInfo("Configured a Query with TagName:[ " + tagName + "] AND  Assignments:[" + assignmentName
				+ ":DistributedTo,Status:Completed,DateRange]");

		sessionSearch.serarchWP();
		driver.waitForPageToBeReady();
		String PureHitCount = sessionSearch.verifyPureHitsCount();
		baseClass.stepInfo(
				"Pure hits count value after Configuring a Query with TagName:[ " + tagName + "] AND  Assignments:["
						+ assignmentName + ":DistributedTo,Staus:Completed,DateRange.] is " + PureHitCount);
		SoftAssert assertion = new SoftAssert();
		// validation of pure hits
		assertion.assertEquals(PureHitCount, "3");
		assertion.assertAll();
		baseClass.passedStep("Application   returned all the documents which are available under "
				+ "selected  group in search result   for the configured query." + PureHitCount);
		baseClass.passedStep(
				"Sucessfully Verified that - Application returns all the documents which are available under selected group and Assignments - distributed/Completed status/Date Range with "
						+ "AND operator in search result.");

		agnmt.deleteAssgnmntUsingPagination(assignmentName);
		loginPage.logout();

	}
	/**
	 * @author Jayanthi.ganesan
	 * @description:Verify that - Application returns all the documents which are
	 *                     available under selected group and Assignments - Assigned
	 *                     status with AND operator in search result.
	 */
	@Test(description = "RPMXCON-57169", groups = { "regression" }, enabled = true)
	public void verifyDocsCntAssignedAssgnments_AND() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-57169");
		baseClass.stepInfo(
				"Verify that - Application returns all the documents which are available under selected group and"
						+ " Assignments - Assigned status with AND operator in search result.");

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");

		// taking the docs count under Assignment/ assigned status
		sessionSearch.switchToWorkproduct();
		sessionSearch.selectCompletedAssignmentInWP("Assigned");
		baseClass.stepInfo("Configured a Query with  Assignments:[Assigned:\"true\"]");
		sessionSearch.serarchWP();
		driver.waitForPageToBeReady();
		String PureHitCount = sessionSearch.verifyPureHitsCount();

		baseClass.stepInfo("Pure hits count value after Configuring a Query with Assignments:[Assigned:\"true\"] is "
				+ PureHitCount);
		baseClass.selectproject();
		sessionSearch.navigateToAdvancedSearchPage();
		// Adding WP tag into search text box
		sessionSearch.workProductSearch("tag", tagName, true);
		baseClass.stepInfo(
				"Inserting a query with  a Tag " + tagName + "and Count of docs avail in that tag is " + tagHitsCount);
		// Adding Operator into search text box
		sessionSearch.selectOperator("AND");
		baseClass.stepInfo("Selecting AND Operator");

		// Adding WP assignment Assigned status into search text box
		baseClass.stepInfo("Selecting Assigned status assignments into Query");
		sessionSearch.selectCompletedAssignmentInWP("Assigned");
		baseClass.stepInfo("Configured a Query with TagName:[ " + tagName + "] AND  Assignments:[Assigned:\"true\"]");

		sessionSearch.serarchWP();
		driver.waitForPageToBeReady();
		String PureHitCount_AND = sessionSearch.verifyPureHitsCount();
		baseClass.stepInfo("Pure hits count value after Configuring a Query with TagName:[ " + tagName
				+ "] AND  Assignments:[Assigned:\"true\"] is " + PureHitCount);
		SoftAssert assertion = new SoftAssert();
		// validation of pure hits
		assertion.assertEquals(PureHitCount, PureHitCount_AND);
		assertion.assertAll();
		baseClass.passedStep("Application   returned all the documents which are available under "
				+ "selected  group in search result   for the configured query." + PureHitCount);
		baseClass.passedStep(
				"Sucessfully Verified that - Application returns all the documents which are available under selected group and Assignments - Assigned status with "
						+ "AND operator in search result.");

		loginPage.logout();

	}

	/**
	 * @author Jayanthi.ganesan
	 */
	@Test(description = "RPMXCON-57167", groups = { "regression" }, enabled = true)
	public void verifyDocsCntAssgnments_AND() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-57167");
		baseClass.stepInfo("Verify that - Application returns all the documents which are available under "
				+ "selected group and Assignments with AND operator in search result.");
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");
		AssignmentsPage agnmt = new AssignmentsPage(driver);

		baseClass.stepInfo("**Pre Requesite Assignment Creation**");

		agnmt.createAssignment(assignmentName, Input.codeFormName);
		sessionSearch.basicContentSearch(Input.testData1);
		String assignedPureHitCount = sessionSearch.verifyPureHitsCount();
		sessionSearch.bulkAssignExisting(assignmentName);

		baseClass.selectproject();
		sessionSearch.navigateToAdvancedSearchPage();
		// Adding WP tag into search text box
		sessionSearch.workProductSearch("tag", tagName, true);
		baseClass.stepInfo(
				"Inserting a query with  a Tag " + tagName + "and Count of docs avail in that tag is " + tagHitsCount);
		// Adding Operator into search text box
		sessionSearch.selectOperator("AND");
		baseClass.stepInfo("Selecting AND Operator");

		// Adding WP assignment into search text box
		baseClass.stepInfo("Selecting  assignment into Query");
		sessionSearch.workProductSearch("assignments", assignmentName, false);
		baseClass.stepInfo(
				"Configured a Query with TagName:[ " + tagName + "] AND  Assignments:[" + assignmentName + "]");

		sessionSearch.serarchWP();
		driver.waitForPageToBeReady();
		String PureHitCount = sessionSearch.verifyPureHitsCount();
		baseClass.stepInfo("Pure hits count value after Configuring a Query with TagName:[ " + tagName
				+ "] AND  Assignments:[" + assignmentName + "] is " + PureHitCount);
		SoftAssert assertion = new SoftAssert();
		// validation of pure hits
		assertion.assertEquals(PureHitCount, assignedPureHitCount);
		assertion.assertAll();
		baseClass.passedStep("Application   returned all the documents which are available under "
				+ "selected  group in search result   for the configured query." + PureHitCount);
		baseClass.passedStep(
				"Sucessfully Verified that - Application returns all the documents which are available under selected group and Assignment "
						+ "with AND operator in search result.");

		agnmt.deleteAssgnmntUsingPagination(assignmentName);
		loginPage.logout();

	}

	/**
	 * @author Jayanthi.ganesan
	 */
	@Test(description = "RPMXCON-53947", groups = { "regression" }, enabled = true)
	public void verifyPersistentSeachHit() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-53947");
		baseClass.stepInfo(
				"To verify that RMU is able to view Persistent Search Hits option on Assign/Unassign pop up.");

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");
		AssignmentsPage agnmt = new AssignmentsPage(driver);

		baseClass.stepInfo("Performing basic content search and dragging pure hit to cart.");
		sessionSearch.basicContentSearch(Input.testData1);
		baseClass.stepInfo("Clicking on bulk assign button");
		sessionSearch.bulkAssign();
		baseClass.stepInfo(
				"Verification of Persistent Search Hits option for Existing assignment Tab on Assign/Unassign pop up.");
		agnmt.verifyPersistentHitIcon(true);
		baseClass.stepInfo(
				"Verification of Persistent Search Hits option for new assignments tab on Assign/Unassign pop up.");
		agnmt.verifyPersistentHitIcon(false);

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
		System.out.println("**Executed Advanced search Regression6**");
	}
}
