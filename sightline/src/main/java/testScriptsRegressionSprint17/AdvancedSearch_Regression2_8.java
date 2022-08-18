package testScriptsRegressionSprint17;

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

public class AdvancedSearch_Regression2_8 {

	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	TagsAndFoldersPage tagPage;
	AssignmentsPage assignPage;
	BaseClass baseClass;
	Input in;
	SoftAssert assertion;

	// Global variable name for the class
	String tagName = "tagName" + Utility.dynamicNameAppender();

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
		sessionSearch = new SessionSearch(driver);
		savedSearch = new SavedSearch(driver);
		tagPage = new TagsAndFoldersPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		assertion = new SoftAssert();
	}

	@DataProvider(name = "paRmuRevUsers")
	public Object[][] paRmuRevUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, "PA" },
				{ Input.rev1userName, Input.rev1password, "REV" }, { Input.rmu1userName, Input.rmu1password, "RMU" } };
		return users;
	}

	/**
	 * @Author Jayanthi
	 * @description:To Verify Regression Issue: After bulk tagging, Session search
	 *                 with workproduct 'Tag' is not working:1
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-49048", enabled = true,groups = { "regression" })
	public void verifyTagCount_Tally() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-49048");
		baseClass.stepInfo("To Verify Regression Issue: After bulk tagging, Session search with workproduct 'Tag' "
				+ "is not working:1");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Loggin in as PA USer.");

		String tagTallyName = "tagTally" + Utility.dynamicNameAppender();

		TallyPage tp = new TallyPage(driver);

		tp.navigateTo_Tallypage();
		tp.SelectSource_SecurityGroup(Input.securityGroup);

		// source verification
		tp.verifySourceSelected();
		tp.applyFilterToTallyBy(Input.metaDataName, "include", Input.custodianName_Andrew); // Applying filter 'Include
		baseClass.stepInfo("**Applying  Tally by field as Metadata :" + Input.metaDataName + "**");
		tp.selectTallyByMetaDataField(Input.metaDataName);
		tp.verifyTallyChart();
		int taggedCount = tp.verifyDocCountBarChart();

		// bulk tag
		tp.tallyActions();
		baseClass.waitTime(2);
		baseClass.stepInfo("**Bulk Tagging All Docs From Tally chart.**");
		tp.bulkTag(tagTallyName, 1);
		baseClass.stepInfo("Bulk Tag Action done successfully from tally page for doc count -" + taggedCount);

		sessionSearch.navigateToAdvancedSearchPage();
		// Adding WP Tag into search text box
		sessionSearch.workProductSearch("tag", tagTallyName, true);
		baseClass.stepInfo("Configured a Query with tag " + tagTallyName);
		sessionSearch.serarchWP();
		driver.waitForPageToBeReady();
		String PureHitCount = sessionSearch.verifyPureHitsCount();
		baseClass.stepInfo(
				"Total no docs after configured query as per test case[tag created from tally] is   " + PureHitCount);
		System.out.println(taggedCount + PureHitCount);
		SoftAssert assertion = new SoftAssert();
		assertion.assertEquals(String.valueOf(taggedCount), PureHitCount);// Validation of hit count.
		assertion.assertAll();
		baseClass.passedStep("Bulk Tagged docs Count for Tag created from Bulk ACtions Tally Page and  Advacned search"
				+ " Work PRoduct/Tag Search using tag created from tally page are the same.");

		TagsAndFoldersPage tagPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagPage.deleteAllTags(tagTallyName);

		loginPage.logout();

	}

	/**
	 * @author Jayanthi.ganesan
	 * @description:Verify that - Application returns all the documents which are
	 *                     available under selected group and Assignments -
	 *                     Completed status with OR operator in search result.
	 */
	@Test(description = "RPMXCON-57162", groups = { "regression" }, enabled = true)
	public void verifyDocsCntCompletedAssgnments_OR() throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-57162");
		baseClass.stepInfo(
				"Verify that - Application returns all the documents which are available under selected group and"
						+ " Assignments - Completed status with OR operator in search result.");
		String tagName = "combined" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");
		SessionSearch sessionSearch = new SessionSearch(driver);

		// Pre requesite creation
		// Performing Star search since this will add all avail docs from default sec
		// group .
		sessionSearch.basicContentSearch(Input.searchStringStar);
		String tagHitsCount = sessionSearch.verifyPureHitsCount();// expected value
		sessionSearch.bulkTag(tagName);
		baseClass.stepInfo("Created a Tag " + tagName + "Count of docs bulk tagged " + tagHitsCount);

		baseClass.selectproject();
		sessionSearch.navigateToAdvancedSearchPage();
		// Adding WP tag into search text box
		sessionSearch.workProductSearch("tag", tagName, true);
		// Adding Operator into search text box
		sessionSearch.selectOperator("OR");
		// Adding WP assignment into search text box
		sessionSearch.selectCompletedAssignmentInWP("Completed");
		baseClass.stepInfo("Configured a Query with TagName:[ " + tagName + "] OR  Assignments:[completed:\"true\"]");
		sessionSearch.serarchWP();
		driver.waitForPageToBeReady();
		String PureHitCount = sessionSearch.verifyPureHitsCount();
		baseClass.stepInfo("Pure hits count value after Configuring a Query with TagName:[ " + tagName
				+ "] OR  Assignments:[completed:\"true\"] is " + PureHitCount);
		SoftAssert assertion = new SoftAssert();
		// validation of pure hits
		assertion.assertEquals(PureHitCount, tagHitsCount);

		TagsAndFoldersPage tp = new TagsAndFoldersPage(driver);
		tp.deleteAllTags(tagName);
		assertion.assertAll();
		baseClass.passedStep("Application   returned all the documents which are available under "
				+ "selected  group in search result   for the configured query." + PureHitCount);
		baseClass.passedStep(
				"Sucessfully Verified that - Application returns all the documents which are available under selected group and Assignments - Completed status with "
						+ "OR operator in search result.");

		loginPage.logout();

	}

	/**
	 * @author
	 * @throws Exception
	 * @Date: 07/15/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that search result appears the added Remark documents
	 *              in Advanced Search.. RPMXCON-48474
	 */
	@Test(description = "RPMXCON-48474", enabled = true, groups = { "regression" })
	public void verifySearchResultAppearsAddRemarkDocumentsInAdvancedSearch() throws ParseException, Exception {
		int remarkCount1 = 1;
		int remarkCount2 = 2;
		String selectField = "Remark";
		DocViewPage docView = new DocViewPage(driver);
		String remark = "Reviewed on 09-20-2009";
		String regularExpression = "\"##Reviewed on [0-9]{2}-[0-9]{2}-[0-9]{4}\"";

		// login as reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		baseClass.stepInfo("Test case Id: RPMXCON-48474");
		baseClass.stepInfo("Verify that search result appears the added Remark documents in Advanced Search.");

		// performing metaData search and adding pureHit to cart and viewing in docView
		sessionSearch.advancedMetaDataSearch(Input.metaDataName, null, Input.metaDataCustodianNameInput, null);
		baseClass.stepInfo("performing advanced metadata search.");
		sessionSearch.viewInDocView();

		// adding remark and performing search using remark
		docView.addRemarkByText(Input.reviewed);
		baseClass.stepInfo("adding Remark to document");
		baseClass.selectproject();
		int pureHit1 = sessionSearch.getCommentsOrRemarksCount_AdvancedSearch(selectField, Input.reviewed);
		// verifying the pureHit count with number of remark added
		baseClass.digitCompareEquals(pureHit1, remarkCount1, "pureHit count match with remark count",
				"pureHit count doesn't match with remark count");

		// performing advanced search metaData search and adding pureHit to cart and
		// viewing in docView
		baseClass.selectproject();
		sessionSearch.advancedMetaDataSearch(Input.metaDataName, null, Input.metaDataCustodianNameInput, null);
		baseClass.stepInfo("performing advanced metadata search.");
		sessionSearch.viewInDocView();

		// adding remark and performing search using remark
		docView.addRemarkByText(remark);
		baseClass.stepInfo("adding Remark to document");
		baseClass.selectproject();
		int pureHit2 = sessionSearch.getCommentsOrRemarksCount_AdvancedSearch(selectField, regularExpression);
		// verifying the pureHit count with number of remark added
		baseClass.digitCompareEquals(pureHit2, remarkCount1, "pureHit count match with remark count",
				"pureHit count doesn't match with remark count");

		// performing audio search and adding pureHit to cart and viewing in docView
		baseClass.selectproject();
		sessionSearch.audioSearch(Input.audioSearch, Input.language);
		baseClass.stepInfo("performing Audio search.");
		sessionSearch.viewInDocView();

		// adding remark to audio documents and performing search using remark
		docView.audioRemark(Input.reviewed);
		baseClass.stepInfo("adding Remark to Audio document");
		baseClass.selectproject();
		int pureHit3 = sessionSearch.getCommentsOrRemarksCount_AdvancedSearch(selectField, Input.reviewed);
		// verifying the pureHit count with number of remark added
		baseClass.digitCompareEquals(pureHit3, remarkCount2, "pureHit count match with remark count",
				"pureHit count doesn't match with remark count");

		// performing search using remark in basic search
		baseClass.selectproject();
		int pureHit4 = sessionSearch.getCommentsOrRemarksCount_AdvancedSearch(selectField, Input.reviewed);
		// verifying the pureHit count with number of remark added
		baseClass.digitCompareEquals(pureHit4, remarkCount2, "pureHit count match with remark count",
				"pureHit count doesn't match with remark count");

		// performing search using remark in basic search
		baseClass.selectproject();
		int pureHit5 = sessionSearch.getCommentsOrRemarksCount_AdvancedSearch(selectField, regularExpression);
		// verifying the pureHit count with number of remark added
		baseClass.digitCompareEquals(pureHit5, remarkCount1, "pureHit count match with remark count",
				"pureHit count doesn't match with remark count");

		// deleting the remark
		baseClass.selectproject();
		sessionSearch.audioSearch(Input.audioSearch, Input.language);
		sessionSearch.viewInDocView();
		docView.deleteAudioRemark();

		// logout
		loginPage.logout();

	}

	/**
	 * @author
	 * @throws Exception
	 * @Date: 07/20/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that while Searching Audio with Workproduct searches-
	 *              configured Audio search settings does not revert back to
	 *              inappropriate setting in \"Audio\" block on \"Advanced Search\"
	 *              screen. RPMXCON-48159
	 */
	@Test(description = "RPMXCON-48159", dataProvider = "paRmuRevUsers", enabled = true, groups = { "regression" })
	public void verifySearchingAudioWithWorkProductNotRevertBackToInappropriateSetting(String username, String password,
			String User) throws InterruptedException {
		List<String> searchString = new ArrayList<String>();
		String language = Input.language;
		int thresholdInput = 65;
		String operator = "ALL";
		String location = "Within:";
		String seconds = "5";
		searchString.add(Input.audioSearchString1);
		searchString.add(Input.audioSearch);

		// login as User
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("RPMXCON-48159 Advanced Search");
		baseClass.stepInfo(
				"Verify that while Searching Audio with Workproduct searches- configured Audio search settings does not revert back to inappropriate setting in \"Audio\" block on \"Advanced Search\" screen");

		// create tag
		tagPage.tagCreationDeletionBasedOnUser(true, User, tagName, Input.securityGroup, false, "PA", null,
				"additional");

		// configure audio search
		baseClass.stepInfo("Configuring Audio Search Block.");
		String thresholdValue = sessionSearch.configureAudioSearchBlock(searchString.get(0), searchString.get(1),
				language, thresholdInput, operator, location, seconds);

		// configure Tag workproduct search
		baseClass.stepInfo("Configuring WorkProduct search Block Tag.");
		sessionSearch.workProductSearch("tag", tagName, true);
		// perform search
		sessionSearch.serarchWP();

		// validating audio search result
		sessionSearch.validateAudioSearchResult(searchString, operator, language, seconds, thresholdValue);

		// validating Tag WorkProduct search Result
		String actualTagSearchResult = sessionSearch.getWorkProductSearchResult().getText();
		baseClass.compareTextViaContains(actualTagSearchResult, tagName,
				"Tag Name selected while configuring the Search matches with the Tag Name appears in the search Result",
				"Tag Name selected while configuring the Search doesn't matches with the Tag Name appears in the search Result");

		// delete tag
		tagPage.tagCreationDeletionBasedOnUser(false, User, tagName, Input.securityGroup, true, "RMU", null,
				"additional");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @throws Exception
	 * @Date: 07/22/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that tiles sequence remains as it is when user clicks
	 *              on Run icon and Unpinned multiple tiles from Shopping cart in
	 *              Advanced Search Result.. RPMXCON-48521
	 */
	@Test(description = "RPMXCON-48521", enabled = true, groups = { "regression" })
	public void verifyTilesSequenceRemainsWhenUserClicksRunIconAndUnpinnedMultipleTitlesFromCartAndRefreshPage()
			throws InterruptedException {

		String[] tilesNameInSequence = { "Docs That Met Your Criteria", "Threaded Documents", "Near Duplicates",
				"Family Members", "Conceptually Similar" };

		// login as Users
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		baseClass.stepInfo("Test case Id: RPMXCON-48521 Advanced Search");
		baseClass.stepInfo(
				"Verify that tiles sequence remains as it is when user clicks on Run icon and Unpinned multiple tiles from Shopping cart in Advanced Search Result.");

		// configure and performing search
		baseClass.stepInfo("**Step-2 Configure valid Query  like     CustodianName: Andrew**");
		sessionSearch.advancedMetaDataSearch(Input.metaDataName, null, Input.metaDataCustodianNameInput, null);

		// adding all tiles to cart and click conceptual play button
		baseClass.stepInfo(
				"**Step-3 & 4 & 5 Click on \"Search\" button and wait till when all results are ready and Now Drag all tiles except Conceptual into Shopping cartand Click on Run Icon on Conceptual tile**");
		sessionSearch.addingAllTilesToShoppingCart("yes", "yes", "yes", "yes", "yes", "no");
		baseClass.stepInfo(
				"all tiles are added to shopping cart except Conceptual tile and Conceptual paly button is clicked");

		// Adding all tiles to cart and click conceptual play button
		baseClass.stepInfo("**Step-6 Repeat Step 3 to Step 6 - thrice**");
		sessionSearch.performAdvSearchandAddToCart(3);
		baseClass.stepInfo(
				"thrice Performed adding all tiles to shopping cart except Conceptual tile and Conceptual paly button is clicked");

		// remove all tiles from cart
		baseClass.stepInfo("**Step-7 Now Unpinned - all tiles from Shopping cart**");
		sessionSearch.removeAllAddedTiles();

		// Refresh
		baseClass.stepInfo("**Step-8 Now Refresh (F5) page**");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		baseClass.stepInfo(
				"**Step-9 In Session Search - Verify that After Refresh - tiles sequence remains as it is when user clicks on Run icon and Unpinned multiple tiles from Shopping cart in Advanced Search Result.**");
		List<String> actualSequenceOfTiless = baseClass
				.availableListofElementsWithAttributeValues(sessionSearch.getAllTilesName(), "data-original-title");

		sessionSearch.verifyTilesPresentInSequence(actualSequenceOfTiless, tilesNameInSequence);
		baseClass.passedStep(
				"Verified that After Refresh tiles sequence remains as it is when user clicks on Run icon and Unpinned multiple tiles from Shopping cart in Advanced Search Result.");

		// logout
		loginPage.logout();
	}

	/**
	 * @author
	 * @throws Exception
	 * @Date: 07/22/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that tiles sequence remains as it is when user clicks
	 *              on Run icon and Unpinned multiple tiles from Shopping cart in
	 *              Advanced Search Result. RPMXCON-48469
	 */
	@Test(description = "RPMXCON-48469", enabled = true, groups = { "regression" })
	public void verifyTilesRemainsWhenUserClicksRunIconAndUnpinnedMultipleTitlesFromCart() throws InterruptedException {

		String[] tilesNameInSequence = { "Docs That Met Your Criteria", "Threaded Documents", "Near Duplicates",
				"Family Members", "Conceptually Similar" };

		// login as Users
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		baseClass.stepInfo("Test case Id: RPMXCON-48469 Advanced Search");
		baseClass.stepInfo(
				"Verify that tiles sequence remains as it is when user clicks on Run icon and Unpinned multiple tiles from Shopping cart in Advanced Search Result.");

		// configure and performing search
		baseClass.stepInfo("**Step-2 Configure valid Query  like     CustodianName: Andrew**");
		sessionSearch.advancedMetaDataSearch(Input.metaDataName, null, Input.metaDataCustodianNameInput, null);

		// adding all tiles to cart and click conceptual play button
		baseClass.stepInfo(
				"**Step-3 & 4 & 5 Click on \"Search\" button and wait till when all results are ready and Now Drag all tiles except Conceptual into Shopping cartand Click on Run Icon on Conceptual tile**");
		sessionSearch.addingAllTilesToShoppingCart("yes", "yes", "yes", "yes", "yes", "no");
		baseClass.stepInfo(
				"all tiles are added to shopping cart except Conceptual tile and Conceptual paly button is clicked");

		// Adding all tiles to cart and click conceptual play button
		baseClass.stepInfo("**Step-6 Repeat Step 3 to Step 6 - thrice**");
		sessionSearch.performAdvSearchandAddToCart(3);
		baseClass.stepInfo(
				"thrice Performed adding all tiles to shopping cart except Conceptual tile and Conceptual paly button is clicked");

		// remove all tiles from cart
		baseClass.stepInfo("**Step-7 Now Unpinned - all tiles from Shopping cart**");
		sessionSearch.removeAllAddedTiles();

		// verifying the tiles sequence
		baseClass.stepInfo(
				"**Step-9 In Session Search - Verify that tiles sequence remains as it is  when user clicks on Run icon and Unpinned multiple tiles from Shopping cart in Advanced Search Result.**");
		baseClass.waitTime(3);
		List<String> actualSequenceOfTiless = baseClass
				.availableListofElementsWithAttributeValues(sessionSearch.getAllTilesName(), "data-original-title");
		sessionSearch.verifyTilesPresentInSequence(actualSequenceOfTiless, tilesNameInSequence);
		baseClass.passedStep(
				"Verified that After Refresh tiles sequence remains as it is when user clicks on Run icon and Unpinned multiple tiles from Shopping cart in Advanced Search Result.");

		// logout
		loginPage.logout();
	}

	/**
	 * @authorS
	 * @throws Exception
	 * @Date: 07/22/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that tiles sequence remains as it is when user clicks
	 *              on Run icon and Unpinned multiple tiles from Shopping cart in
	 *              Advanced Search Result. RPMXCON-48469
	 */
	@Test(description = "RPMXCON-48229", enabled = true, groups = { "regression" })
	public void verifyDeletReviewerRemarksForNonAudioDocumentsInAdvancedSearch() throws Exception {

		String selectField = "Remark";
		DocViewPage docView = new DocViewPage(driver);
		int countBeforeRemarkDelet = 1;
		int countAfterRemarkDelet = 0;

		// login as Users
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		baseClass.stepInfo("Test case Id: RPMXCON-48229 Advanced Search");
		baseClass.stepInfo(
				"Verify that deleted reviewer remarks for Non-audio documents is working correctly in  Advanced Search.");

		// performing metaData search and adding pureHit to cart and viewing in docView
		baseClass.stepInfo(
				"**Step-3 & 4 & 5 Select \"Remarks\"  from \"Comment Fields / Remarks\" combo box and Enter Same Remark in \"Remark\" Text box which we mentioned in Prerequisite :  and Click on Search      **");
		sessionSearch.advancedMetaDataSearch(Input.metaDataName, null, Input.metaDataCustodianNameInput, null);
		baseClass.stepInfo("performing advanced metadata search.");
		sessionSearch.viewInDocView();

		// adding remark and performing search using remark
		docView.addRemarkByText(Input.reviewed);
		baseClass.stepInfo("adding Remark to document");
		baseClass.selectproject();
		int pureHit1 = sessionSearch.getCommentsOrRemarksCount_AdvancedSearch(selectField, Input.reviewed);
		baseClass.digitCompareEquals(countBeforeRemarkDelet, pureHit1, "pureHit match with the count of remark added",
				"pureHit doesn't match with the count of remark added");

		// performing search using remark
		baseClass.stepInfo("**Step-6 Delete the remarks for the documents (refer TC12266 for deleting remarks)**");
		sessionSearch.viewInDocView();
		docView.deleteReamark(Input.reviewed);
		baseClass.selectproject();
		
		baseClass.stepInfo("**Step-7 Navigate to Advanced search and Select \"Remarks\"  from \"Comment Fields / Remarks\" combo box**");
		int pureHitAfterDelet = sessionSearch.getCommentsOrRemarksCount_AdvancedSearch(selectField, Input.reviewed);

		// verifying that Application should display the latest count excluding the
		// documents for which remarks are deleted
		baseClass.stepInfo("**Step-8 Enter Same Remark in \"Remark\" Text box as before -> click on Search**");
		baseClass.digitCompareEquals(countAfterRemarkDelet, pureHitAfterDelet,
				"Verified that Application displaying the latest count excluding the documents for which remarks are deleted",
				"pureHit doesn't match with the count of remark After deletion");

		// logOut
		loginPage.logout();
	}
	
	/**
	 * @Author Jeevitha
	 * @Description : Verify that User Navigates from Advanced Search result to
	 *              Document list screen [RPMXCON-
	 * @param username
	 * @param password
	 * @param User
	 * @throws ParseException
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-57082", dataProvider = "paRmuRevUsers", enabled = true, groups = { "regression" })
	public void verifyDoclIstPage_Adv(String username, String password, String User) throws ParseException, Exception {
		String expectedTxt = "SESSIONSEARCH";
		DocListPage doclist = new DocListPage(driver);

		// login as User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-57082  Advanced Search");
		baseClass.stepInfo("Verify that User Navigates from Advanced Search result to Document list screen");

		// configure content query & view in doclist
		sessionSearch.advancedContentSearch(Input.searchString5);
		sessionSearch.ViewInDocList();

		// verify text in Source Criteria Panel
		driver.waitForPageToBeReady();
		doclist.verifySourceCriteriaPanel(expectedTxt);

		// logOut
		loginPage.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description : To verify as an user login into the Application, I will get
	 *              the search result for near dupes, when I will search some query
	 *              along with multiple Redaction tags filter applied from work
	 *              product tab of advanced search
	 * @param username
	 * @param password
	 * @param User
	 * @throws ParseException
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-57071", dataProvider = "paRmuRevUsers", enabled = true, groups = { "regression" })
	public void verifySearchResultForNearDupe(String username, String password, String User)
			throws ParseException, Exception {
		String expectedTxt = "SESSIONSEARCH";
		DocListPage doclist = new DocListPage(driver);

		// login as User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-57071  Advanced Search");
		baseClass.stepInfo(
				"To verify as an user login into the Application, I will get the search result for near dupes, when I will search some query along with multiple Redaction tags filter applied from work product tab of advanced search");

		// Select Redaction Tag in workproduct
		sessionSearch.switchToWorkproduct();
		sessionSearch.workProductSearch("redactions", Input.defaultRedactionTag, false);
		sessionSearch.saveAndReturnPureHitCount();

		// verify near dupe is count is displayed
		String nearDup = sessionSearch.verifyNearDupeCount();
		assertion.assertNotEquals(nearDup, "");
		assertion.assertAll();

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
