package sightline.advancedSearch;

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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import automationLibrary.Driver;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
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

	@DataProvider(name = "paRmuRevUsers")
	public Object[][] paRmuRevUsers() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "RMU" },
				{ Input.pa1userName, Input.pa1password, "PA" }, { Input.rev1userName, Input.rev1password, "REV" }, };
		return users;
	}

	@DataProvider(name = "PaAndRevUser")
	public Object[][] PaAndRevUser() {
		Object[][] users = {
				{ Input.rev1userName, Input.rev1password, "REV" },
				{ Input.pa1userName, Input.pa1password, "PA" },
				};
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
		sessionSearch.verifyWarningMessage(true,false, 1);

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

	/**
	 * @Author Jeevitha
	 * @Description : Verify that - Application returns all the documents which are
	 *              available under selected group with AND operator and production
	 *              optional filters - Date Range in search result.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-57165", groups = { "regression" }, enabled = true)
	public void verifySgAndProdDate() throws Exception {
		String fromDate = "2022/07/01";

		baseClass.stepInfo("Test case Id: RPMXCON-57165 Advanced Search");
		baseClass.stepInfo(
				"Verify that - Application returns all the documents which are available under selected group with AND operator and production optional filters - Date Range in search result.");

		loginPage.loginToSightLine(Input.pa1userName,Input.pa1password);
		baseClass.stepInfo("Logged In As : " + Input.pa1FullName);

		// Select Work production Security group & "And" Operator
		sessionSearch.switchToWorkproduct();
		sessionSearch.selectSecurityGinWPS(Input.securityGroup);
		sessionSearch.selectOperator("AND");

		// Enter Production date Range
		sessionSearch.selectWPWithProdBatesAndDateRange(true, false, fromDate, "", "");

		// verify Purehit for configured query
		int purehit = sessionSearch.saveAndReturnPureHitCount();
		sessionSearch.configuredQuery();
		baseClass.stepInfo("Purehit is : " + purehit);

		// logout
		loginPage.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that - Application returns all the documents which are
	 *              available under selected group with AND operator in search
	 *              result.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-57164", groups = { "regression" }, enabled = true)
	public void verifyWPWithProd() throws Exception {
		String prodName = "Production" + Utility.dynamicNameAppender();
		ProductionPage production = new ProductionPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-57164 Advanced Search");
		baseClass.stepInfo(
				"Verify that - Application returns all the documents which are available under selected group with AND operator in search result.");

		loginPage.loginToSightLine(Input.pa1userName,Input.pa1password);
		baseClass.stepInfo("Logged In As : " + Input.pa1FullName);

		// create Production to select in WP
		production.navigateToProductionPage();
		production.addANewProductionAndSave(prodName);

		// Select Tag in Work product & "AND" operator
		driver.waitForPageToBeReady();
		sessionSearch.switchToWorkproduct();
		sessionSearch.selectTagInASwp(tagName);
		sessionSearch.selectOperator("AND");

		// Select production
		sessionSearch.selectProductionstInASwp(prodName);

		// verify purehit for configured query
		int purehit = sessionSearch.saveAndReturnPureHitCount();
		sessionSearch.configuredQuery();
		baseClass.stepInfo("Purehit is : " + purehit);

		// logout
		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that - Application returns all the documents which are
	 *              available under selected group with OR operator and production
	 *              optional filters - Bates Range in search result.
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-57160", groups = { "regression" }, enabled = true)
	public void verifySgAndProdBatesRange() throws Exception {
		String batesRange = "SP23571PS";
		baseClass.stepInfo("Test case Id: RPMXCON-57160 Advanced Search");
		baseClass.stepInfo(
				"Verify that - Application returns all the documents which are available under selected group with OR operator and production optional filters - Bates Range in search result.");

		loginPage.loginToSightLine(Input.pa1userName,Input.pa1password);
		baseClass.stepInfo("Logged In As : " + Input.pa1FullName);

		// Select Work production Security group & "And" Operator
		sessionSearch.switchToWorkproduct();
		sessionSearch.selectSecurityGinWPS(Input.securityGroup);
		sessionSearch.selectOperator("OR");

		// Enter Production Bates Range
		sessionSearch.selectWPWithProdBatesAndDateRange(false, true, "", batesRange, batesRange);

		// verify Purehit for configured query
		int purehit = sessionSearch.saveAndReturnPureHitCount();
		sessionSearch.configuredQuery();
		baseClass.stepInfo("Purehit is : " + purehit);

		// logout
		loginPage.logout();

	}

///////////////////// -------------New updates
	/**
	 * @author
	 * @throws Exception
	 * @Date: 08/2/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that pure hit tile does not get corrupted (CSS Removed)
	 *              in shopping cart when search goes to BackGround and User select
	 *              \"I Want to Wait\" on Advanced Search Screen. RPMXCON-48656
	 */
	@Test(description = "RPMXCON-48656", enabled = true, dataProvider = "paRmuRevUsers", groups = { "regression" })
	public void verifyPureHitTileNotCorruptedInShoppingCart(String userName, String password,String loginUser) {

		// login as PA
		loginPage.loginToSightLine(userName, password);
		baseClass.selectproject(Input.highVolumeProject);

		baseClass.stepInfo("Test case Id: RPMXCON-48656");
		baseClass.stepInfo(
				"Verify that pure hit tile does not get corrupted (CSS Removed) in shopping cart when search goes to BackGround and User select \"I Want to Wait\" on Advanced Search Screen");

		// configuring and performing metaData Search
		baseClass.stepInfo("configuring and performing metaData Search.");
		sessionSearch.advancedMetaDataForDraft(Input.metaDataName, null, Input.searchStringStar, null);
		sessionSearch.getAdvanceSearch_btn_Current().waitAndClick(5);

		// click 'I Want TO Wait' Button
		baseClass.stepInfo("Clicking 'I Want TO Wait' Button.");
		sessionSearch.handleIWantToWaitInBellyBandPopup(30);

		// click 'When Pure Hits Are Ready' Button
		baseClass.stepInfo("Clicking 'When Pure Hits Are Ready' Button");
		sessionSearch.handleWhenPureHitsAreReadyInBellyBandPopup(30);
		sessionSearch.handleIWantToWaitInBellyBandPopup(30);

		// Moving Pure Hit tile into Shipping cart
		sessionSearch.addPureHit();
		String expectedpureHit = sessionSearch.verifyPureHitsCount();
		// waiting for other Titles to Appear
		baseClass.stepInfo("waiting for other Titles to Appear.");
		sessionSearch.handleWhenAllResultsBtnInUncertainPopup();
		sessionSearch.verifyThreadedCount();
		sessionSearch.verifyNearDupeCount();
		sessionSearch.verifyFamilyount();

		// Verify that pure hit tile does not get corrupted (CSS Removed) in shopping
		// cart
		assertion.assertEquals(expectedpureHit, sessionSearch.verifyPureHitsCount());
		assertion.assertAll();
		baseClass.passedStep(
				"Verify that pure hit tile does not get corrupted (CSS Removed) in shopping cart when search goes to BackGround and User Select \"When All Results Are Ready\" on Advanced Search Screen");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @throws Exception
	 * @Date: 08/2/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that configured query with MasterDate and range
	 *              operator get inserted properly into Advanced Search Query
	 *              builder screen. RPMXCON-57055
	 */
	@Test(description = "RPMXCON-57055", dataProvider = "paRmuRevUsers", enabled = true, groups = { "regression" })
	public void verifyConfigureQueryMasterDateAndRangeOperatorInsertedProperlyInAdvancedSearch(String username,
			String password,String loginUser) {

		String fromDate = "2015-01-01";
		String toDate = "2015-05-04";

		// login as PA
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-57055");
		baseClass.stepInfo(
				"Verify that configured query with MasterDate  and range operator get inserted properly into Advanced Search Query builder screen");

		// Configuring MetaData Search Query
		baseClass.stepInfo("Configuring MetaData Search Query.");
		sessionSearch.advancedMetaDataForDraft(Input.masterDateText, "RANGE", fromDate, toDate);

		// getting the actual configured search query in Advanced Search Query builder
		// screen
		String actualConfiguredQuery = sessionSearch.getSavedSearchQueryAS().getText();

		// Verify that configured query with Document File Size and range operator get
		// inserted properly into Advanced Search Query builder screen
		baseClass.compareTextViaContains(
				actualConfiguredQuery, Input.masterDateText, "MetaData '" + Input.masterDateText
						+ "' is present in the configure Search Query '" + actualConfiguredQuery + "'.",
				"expected metaData is not present in configure search query");
		baseClass.compareTextViaContains(
				actualConfiguredQuery, fromDate, "From Date value '" + fromDate
						+ "' is present in the configure Search Query '" + actualConfiguredQuery + "'.",
				"expected From Date value is not present in configure search query");
		baseClass.compareTextViaContains(
				actualConfiguredQuery, toDate, "To Date value '" + toDate
						+ "' is present in the configure Search Query '" + actualConfiguredQuery + "'.",
				"expected To Date value is not present in configure search query");
		baseClass.passedStep(
				"Verified that configured query with MasterDate  and range operator get inserted properly into Advanced Search Query builder screen.");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @throws Exception
	 * @Date: 08/2/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : To verify an an PA user login, I will be able to select all
	 *              the Redaction Tags from Redaction Tag column under Work Product
	 *              tab & set that as a search criteria for advanced search.
	 *              RPMXCON-57045
	 */
	@Test(description = "RPMXCON-57045", enabled = true, groups = { "regression" })
	public void verifyPAUserAbleToSelectAllRedactionTagsInAdvancedSearch() throws Exception {

		String tagName1 = "tagName" + Utility.dynamicNameAppender();
		String tagName2 = "tagName" + Utility.dynamicNameAppender();
		List<String> tagNameList = new ArrayList<String>(Arrays.asList(tagName1, tagName2));

		// login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		baseClass.stepInfo("Test case Id: RPMXCON-57045");
		baseClass.stepInfo(
				"To verify an an PA user login, I will be able to select all the Redaction Tags from Redaction Tag column under Work Product tab & set that as a search criteria for advanced search");

		// creating the tags
		tagPage.CreateTag(tagName1, Input.securityGroup);
		tagPage.CreateTag(tagName2, Input.securityGroup);

		// configuring the workProduct search Query by inserting multiple tags
		sessionSearch.navigateToAdvancedSearchPage();
		baseClass.stepInfo("configuring the workProduct search Query by inserting multiple tags.");
		sessionSearch.selectMultipleTagsInASwp(tagNameList);

		// getting the actual Configure Search Query
		String actualConfiguredQuery = sessionSearch.getQueryTextArea().getText();

		// verify Selected Redaction Tags will be now inserted as a search criteria for
		// advanced search
		assertion.assertEquals(actualConfiguredQuery.contains(tagName1), true);
		assertion.assertEquals(actualConfiguredQuery.contains(tagName2), true);
		assertion.assertAll();
		baseClass
				.passedStep("verified that Selected Redaction Tags inserted as a search criteria for advanced search.");

		// Initiating Tag deletion
		baseClass.stepInfo("Initiating  tag deletion");
		tagPage.navigateToTagsAndFolderPage();
		tagPage.DeleteTag(tagName1, Input.securityGroup);
		tagPage.DeleteTag(tagName2, Input.securityGroup);

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @throws Exception
	 * @Date: 08/2/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that UnFolder works properly using Bulk Folder Action
	 *              in Advanced Search Screen. RPMXCON-57183
	 */
	@Test(description = "RPMXCON-57183", enabled = true, groups = { "regression" })
	public void verifyUnfolderWorksUsingBulkFolderActionInAdvancedSearch() throws Exception {

		String folderName = "myFolder" + Utility.dynamicNameAppender();
		int docCountAfterUnfold = 0;

		// login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		baseClass.stepInfo("Test case Id: RPMXCON-57183");
		baseClass.stepInfo("Verify that UnFolder works properly using Bulk Folder Action in Advanced Search Screen");

		// performing content search
		int pureHit = sessionSearch.advancedContentSearch(Input.searchString1);

		// performing bulk folder action
		sessionSearch.bulkFolder(folderName);

		// verifying the document count before unfolder action
		tagPage.navigateToTagsAndFolderPage();
		tagPage.selectingFolderAndVerifyingTheDocCount(folderName, pureHit);

		// performing unfolder action on created folder
		sessionSearch.navigateToSearchPageAndVerifyTileStatus();
		baseClass.stepInfo("Performing Unfolder action");
		sessionSearch.bulkUnFolder(folderName);

		// Verify that UnFolder works properly using Bulk Folder Action in Advanced
		// Search Screen
		tagPage.navigateToTagsAndFolderPage();
		tagPage.selectingFolderAndVerifyingTheDocCount(folderName, docCountAfterUnfold);
		baseClass.stepInfo("Verified that UnFolder works properly using Bulk Folder Action in Advanced Search Screen.");

		// delete folder
		tagPage.DeleteFolder(folderName, "All Groups");

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
		System.out.println("**Executed Advanced search Regression6**");
	}
}
