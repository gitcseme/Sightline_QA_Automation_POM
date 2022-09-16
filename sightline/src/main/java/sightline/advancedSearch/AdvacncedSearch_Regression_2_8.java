package sightline.advancedSearch;

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
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class AdvacncedSearch_Regression_2_8 {

	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	TagsAndFoldersPage tagPage;
	AssignmentsPage assignPage;
	BaseClass baseClass;
	Input in;
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
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);

	}

	@DataProvider(name = "paRmuRevUsers")
	public Object[][] paRmuRevUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, "PA" },
				{ Input.rev1userName, Input.rev1password, "REV" }, { Input.rmu1userName, Input.rmu1password, "RMU" } };
		return users;
	}

	/**
	 * @author Jayanthi.ganesan
	 * @description:Verify that - Application returns all the documents which are
	 *                     available under selected group and Assignments - Assigned
	 *                     status with OR operator in search result.
	 */
	@Test(description = "RPMXCON-57163", groups = { "regression" }, enabled = true)
	public void verifyDocsCntAssignedAssgnments_OR() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-57163");
		baseClass.stepInfo(
				"Verify that - Application returns all the documents which are available under selected group and"
						+ " Assignments - Assigned status with OR operator in search result.");

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");

		sessionSearch.navigateToAdvancedSearchPage();
		// Adding WP tag into search text box
		sessionSearch.workProductSearch("tag", tagName, true);
		baseClass.stepInfo(
				"Inserting a query with  a Tag " + tagName + "and Count of docs avail in that tag is " + tagHitsCount);
		// Adding Operator into search text box
		sessionSearch.selectOperator("OR");
		baseClass.stepInfo("Selecting OR Operator");

		// Adding WP assignment Assigned status into search text box
		baseClass.stepInfo("Selecting Assigned status assignments into Query");
		sessionSearch.selectCompletedAssignmentInWP("Assigned");
		baseClass.stepInfo("Configured a Query with TagName:[ " + tagName + "] OR  Assignments:[Assigned:\"true\"]");

		sessionSearch.serarchWP();
		driver.waitForPageToBeReady();
		String PureHitCount = sessionSearch.verifyPureHitsCount();
		baseClass.stepInfo("Pure hits count value after Configuring a Query with TagName:[ " + tagName
				+ "] OR  Assignments:[Assigned:\"true\"] is " + PureHitCount);
		SoftAssert assertion = new SoftAssert();
		// validation of pure hits
		assertion.assertEquals(PureHitCount, tagHitsCount);
		assertion.assertAll();
		baseClass.passedStep("Application   returned all the documents which are available under "
				+ "selected  group in search result   for the configured query." + PureHitCount);
		baseClass.passedStep(
				"Sucessfully Verified that - Application returns all the documents which are available under selected group and Assignments - Assigned status with "
						+ "OR operator in search result.");

		loginPage.logout();

	}

	/**
	 * @author Jayanthi.ganesan
	 * @description:Verify that - Application returns all the documents which are
	 *                     available under selected group and Assignments -
	 *                     Distributed status with OR operator in search result.
	 */
	@Test(description = "RPMXCON-57161", groups = { "regression" }, enabled = true)
	public void verifyDocsCntDistributedAssgnments_OR() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-57161");
		baseClass.stepInfo(
				"Verify that - Application returns all the documents which are available under selected group and Assignments - "
						+ "distributed with OR operator in search result.");
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");
		AssignmentsPage agnmt = new AssignmentsPage(driver);

		baseClass.stepInfo("Pre Requesite Assignment Creation/Docs distribution.");
		agnmt.createAssignment(assignmentName, Input.codeFormName);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkAssignExisting(assignmentName);

		baseClass.selectproject();
		agnmt.editAssignmentUsingPaginationConcept(assignmentName);
		agnmt.addReviewerAndDistributeDocs();

		baseClass.selectproject();
		sessionSearch.navigateToAdvancedSearchPage();
		// Adding WP tag into search text box
		sessionSearch.workProductSearch("tag", tagName, true);
		baseClass.stepInfo(
				"Inserting a query with  a Tag " + tagName + "and Count of docs avail in that tag is " + tagHitsCount);
		// Adding Operator into search text box
		sessionSearch.selectOperator("OR");
		baseClass.stepInfo("Selecting OR Operator");

		// Adding WP assignment Assigned status into search text box
		baseClass.stepInfo("Selecting distributed status assignment into Query");
		sessionSearch.selectAssignmentInWPSWthFilters(assignmentName, Input.rev1userName, null);
		baseClass.stepInfo("Configured a Query with TagName:[ " + tagName + "] OR  Assignments:[" + assignmentName
				+ ":DistributedTo]");

		sessionSearch.serarchWP();
		driver.waitForPageToBeReady();
		String PureHitCount = sessionSearch.verifyPureHitsCount();
		baseClass.stepInfo("Pure hits count value after Configuring a Query with TagName:[ " + tagName
				+ "] OR  Assignments:[" + assignmentName + ":DistributedTo] is " + PureHitCount);
		SoftAssert assertion = new SoftAssert();
		// validation of pure hits
		assertion.assertEquals(PureHitCount, tagHitsCount);
		assertion.assertAll();
		baseClass.passedStep("Application   returned all the documents which are available under "
				+ "selected  group in search result   for the configured query." + PureHitCount);
		baseClass.passedStep(
				"Sucessfully Verified that - Application returns all the documents which are available under selected group and Assignments - distributed status with "
						+ "OR operator in search result.");

		agnmt.deleteAssgnmntUsingPagination(assignmentName);
		loginPage.logout();

	}

	/**
	 * Author :Jayanthi TestCase Id:RPMXCON-57171 Description :Verify that -
	 * Application returns all the documents which are available under selected
	 * group with NOT operator and production optional filters - Date Range in
	 * search result.
	 * 
	 */
	@Test(description = "RPMXCON-57171", groups = { "regression" })
	public void prod_verifyDocumentWithORProdOptFiltersDateRange() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-57171");
		baseClass.stepInfo("Verify that - Application returns all the documents which are available "
				+ "under selected group with NOT operator and production optional filters - Date Range in search result");
		// login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		sessionSearch.switchToWorkproduct();
		baseClass.stepInfo("Configure query with  production status with date range ");
		sessionSearch.configureQueryWithSecurityGroupAndProductionStatus(null, null, true);
		sessionSearch.serarchWP();
		int pureHit_Prod = Integer.parseInt(sessionSearch.verifyPureHitsCount());

		baseClass.selectproject();
		sessionSearch.switchToWorkproduct();
		baseClass.stepInfo("Configured query with security group NOT production status with date");
		sessionSearch.configureQueryWithSecurityGroupAndProductionStatus(Input.securityGroup, "NOT", true);
		sessionSearch.serarchWP();
		baseClass.stepInfo("Searched query and verifying search result");
		int pureHit_SG_OR_Prod = Integer.parseInt(sessionSearch.verifyPureHitsCount());

		int countinSG = sessionSearch.verifyDocsCountAvailableInSg();
		int expectedCount = countinSG - pureHit_Prod;
		SoftAssert assertion = new SoftAssert();
		// validation of pure hits
		assertion.assertEquals(pureHit_SG_OR_Prod, expectedCount);
		assertion.assertAll();
		baseClass.stepInfo(
				"Sucessfully verified that - Application returns all the documents which are available under selected group with NOT operator and "
						+ "production optional filters - status with Date Range  in search result.");
		loginPage.logout();
	}

	/**
	 * Author :Jayanthi TestCase Id:RPMXCON-57170 Description :Verify that -
	 * Application returns all the documents which are available under selected
	 * group with NOT operator and production optional filters - status in search
	 * result.
	 * 
	 */
	@Test(description = "RPMXCON-57170", groups = { "regression" })
	public void prod_VerifyDocumentWithNOTProdOptFiltersStatus() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-57170");
		baseClass.stepInfo(
				"Verify that - Application returns all the documents which are available under selected group with NOT operator and "
						+ "production optional filters - status  in search result.");
		// login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		sessionSearch.switchToWorkproduct();
		// getting pure hit count by selecting prod status as true.
		baseClass.stepInfo("Configure query with  production status as true. ");
		sessionSearch.configureQueryWithSecurityGroupAndProductionStatus(null, null, false);
		sessionSearch.serarchWP();
		int pureHit_Prod = Integer.parseInt(sessionSearch.verifyPureHitsCount());

		baseClass.selectproject();
		sessionSearch.switchToWorkproduct();
		sessionSearch.configureQueryWithSecurityGroupAndProductionStatus(Input.securityGroup, "NOT", false);
		baseClass.stepInfo("Configured query with securitygroups: "
				+ "[ name: [\"Default Security Group\"] ]  NOT   productions: [produced: \"true\" ]  ");
		sessionSearch.serarchWP();
		int pureHit_SG_OR_Prod = Integer.parseInt(sessionSearch.verifyPureHitsCount());

		// calculating expected doc count.
		int countinSG = sessionSearch.verifyDocsCountAvailableInSg();
		int expectedCount = countinSG - pureHit_Prod;

		SoftAssert assertion = new SoftAssert();
		// validation of pure hits
		assertion.assertEquals(pureHit_SG_OR_Prod, expectedCount);
		assertion.assertAll();
		baseClass.stepInfo(
				"Sucessfully verified that - Application returns all the documents which are available under selected group with NOT operator and "
						+ "production optional filters - status  in search result.");
		loginPage.logout();
	}

	/**
	 * @author
	 * @throws Exception
	 * @Date: 07/28/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that while Searching Audio with Conceptual searches-
	 *              configured Audio search settings does not revert back to
	 *              inappropriate setting in \"Audio\" block on \"Advanced Search\"
	 *              screen. RPMXCON-48160
	 */
	@Test(description = "RPMXCON-48160", dataProvider = "paRmuRevUsers", enabled = true, groups = { "regression" })
	public void verifySearchingAudioWithConceptualSearchesNotRevertBackInappropriateSetting(String username,
			String password, String User) throws InterruptedException {
		List<String> searchString = new ArrayList<String>();
		String conceptualSearchString = "sample";
		String language = Input.language;
		int thresholdInput = 65;
		String operator = "ALL";
		String location = "Within:";
		String seconds = "5";
		searchString.add(Input.audioSearchString1);
		searchString.add(Input.audioSearch);

		// login as User
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("RPMXCON-48160 Advanced Search");
		baseClass.stepInfo(
				"Verify that while Searching Audio with Conceptual searches-  configured Audio search settings does not revert back to inappropriate setting in \"Audio\" block  on \"Advanced Search\" screen");

		// configure audio search
		baseClass.stepInfo("Configuring Audio Search Block.");
		String thresholdValue = sessionSearch.configureAudioSearchBlock(searchString.get(0), searchString.get(1),
				language, thresholdInput, operator, location, seconds);

		// performing Conceptual Search
		baseClass.stepInfo("Configuring Conceptual Search.");
		sessionSearch.advancedSearchConceptual(conceptualSearchString);
		sessionSearch.serarchWP();

		// validating audio search result
		sessionSearch.validateAudioSearchResult(searchString, operator, language, seconds, thresholdValue);

		// validating Conceptual search Result
		String actualTagSearchResult = sessionSearch.getConceptualSearchResult().getText();
		baseClass.compareTextViaContains(actualTagSearchResult, conceptualSearchString,
				"actual Conceptual search String Present in Adavnced search Result Page Match with the Expected Search String.",
				"actual search String doesn't match with the Expected Search String.");

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
		System.out.println("**Executed Advanced search Regression6**");
	}
}
