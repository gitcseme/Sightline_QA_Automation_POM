package sightline.advancedSearch;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
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
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.Categorization;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class AdvancedSearch_Phase2_Regression1 {
	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	BaseClass baseClass;
	Input in;
	SoftAssert assertion;
	SecurityGroupsPage securityGroupsPage;
	DocViewRedactions docViewRedact;

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

	@DataProvider(name = "Users")
	public Object[][] Users() {
		Object[][] users = { { Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password },
				{ Input.rev1userName, Input.rev1password }, };
		return users;
	}

	/**
	 * @Author
	 * @Description : Verify that Dropped tiles are retained in shopping cart when
	 *              User Navigates Basic Search (Pure Hit) >> \"Analyze in Concept
	 *              Explorer\" screen and Come back to Search Page.
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57259", enabled = true, groups = { "regression" })
	public void verifyTilesRetainedInCartWhenUserNavigatesFromConceptExplorerPageToBasicSearch()
			throws InterruptedException {

		// login as Users
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		baseClass.stepInfo("Test case Id: RPMXCON-57259 Advanced Search");
		baseClass.stepInfo(
				"Verify that Dropped tiles are retained in shopping cart when User Navigates Basic Search (Pure Hit) >> \"Analyze in Concept Explorer\" screen and Come back to Search Page.");

		// configure and performing search
		sessionSearch.basicMetaDataSearch(Input.metaDataName, null, Input.metaDataCustodianNameInput, null);

		// adding pureHit to shopping cart and navigating to concept Explorer page
		sessionSearch.addPurehitAndActionAsConceptOrComm(false, true);

		// navigating to basic search and verifying whether the title is retained in
		// shopping cart
		sessionSearch.navigateToSessionSearchPageURL();
		assertion.assertEquals((boolean) sessionSearch.getRemovedocsfromresult().isElementAvailable(5), true);
		assertion.assertAll();
		baseClass.passedStep("Dropped title is retained in shopping cart when User navigates Basic Search");

		// logout
		loginPage.logout();

	}

	/**
	 * @Author
	 * @Description : Verify that Dropped tiles are retained in shopping cart when
	 *              User Navigates Basic Search (Pure Hit) >> \"Manage >>
	 *              Categorize\" screen and Come back to Search Page.
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57262", enabled = true, groups = { "regression" })
	public void verifyTilesRetainedInCartWhenUserNavigatesFromCategorizePageToBasicSearch()
			throws InterruptedException {

		Categorization categories = new Categorization(driver);

		// login as Users
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		baseClass.stepInfo("Test case Id: RPMXCON-57262 Advanced Search");
		baseClass.stepInfo(
				"Verify that Dropped tiles are retained in shopping cart when User Navigates Basic  Search (Pure Hit) >> \"Manage >> Categorize\" screen and Come back to Search Page.");

		// configure and performing search
		sessionSearch.basicMetaDataSearch(Input.metaDataName, null, Input.metaDataCustodianNameInput, null);
		// adding pureHit to shopping cart
		sessionSearch.addPureHit();

		// navigating to categories page
		categories.navigateToCategorizePage();

		// navigating to basic search and verifying whether the title is retained in
		// shopping cart
		sessionSearch.navigateToSessionSearchPageURL();
		assertion.assertEquals((boolean) sessionSearch.getRemovedocsfromresult().isElementAvailable(5), true);
		assertion.assertAll();
		baseClass.passedStep("Dropped title is retained in shopping cart when User navigates Basic Search");

		// logout
		loginPage.logout();
	}

	/**
	 * @Author
	 * @Description :Verify that Dropped tiles are retained in shopping cart when
	 *              User Navigates Basic Search (Pure Hit) >> \"Manage >>Users\"
	 *              screen and Come back to Search Page.
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57263", enabled = true, groups = { "regression" })
	public void verifyTilesRetainedInCartWhenUserNavigatesFromUserManagementToBasicSearch()
			throws InterruptedException {

		UserManagement user = new UserManagement(driver);

		// login as Users
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		baseClass.stepInfo("Test case Id: RPMXCON-57263 Advanced Search");
		baseClass.stepInfo(
				"Verify that Dropped tiles are retained in shopping cart when User Navigates Basic Search (Pure Hit) >> \"Manage >>Users\" screen and Come back to Search Page.");

		// configure and performing search
		sessionSearch.basicMetaDataSearch(Input.metaDataName, null, Input.metaDataCustodianNameInput, null);
		// adding pureHit to shopping cart
		sessionSearch.addPureHit();
		baseClass.stepInfo("performing MetaData search and adding pureHit to cart");

		// navigating to user Management page
		user.navigateToUsersPAge();

		// navigating to basic search and verifying whether the title is retained in
		// shopping cart
		sessionSearch.navigateToSessionSearchPageURL();
		assertion.assertEquals((boolean) sessionSearch.getRemovedocsfromresult().isElementAvailable(5), true);
		assertion.assertAll();
		baseClass.passedStep("Dropped title is retained in shopping cart when User navigates Basic Search");

		// logout
		loginPage.logout();

	}

	/**
	 * @Author
	 * @Description : Verify that Dropped tiles are retained in shopping cart when
	 *              User Navigates Advanced Search (Pure Hit) >> \"Analyze in
	 *              Communications Explorer\" screen and Come back to Search Page.
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57264", enabled = true, groups = { "regression" })
	public void verifyTilesRetainedInCartWhenUserNavigatesFromCommunicationsExplorerToAdvancedSearch()
			throws InterruptedException {

		// login as Users
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		baseClass.stepInfo("Test case Id: RPMXCON-57264 Advanced Search");
		baseClass.stepInfo(
				"Verify that Dropped tiles are retained in shopping cart when User Navigates Advanced Search (Pure Hit) >> \"Analyze in Communications Explorer\" screen and Come back to Search Page.");

		// configure and performing search
		sessionSearch.advancedMetaDataSearch(Input.metaDataName, null, Input.metaDataCustodianNameInput, null);

		// navigating to communication Explorer page
		sessionSearch.addPurehitAndActionAsConceptOrComm(true, false);

		// navigating back to advanced search
		sessionSearch.navigateToSessionSearchPageURL();

		// verifying whether the title is retained in shopping cart
		assertion.assertEquals((boolean) sessionSearch.getRemovedocsfromresult().isElementAvailable(5), true);
		assertion.assertAll();
		baseClass.passedStep("Dropped title is retained in shopping cart when User navigates Advanced Search");

		// logout
		loginPage.logout();
	}

	/**
	 * @Author :
	 * @Description : Verify that Dropped tiles are retained in shopping cart when
	 *              User Navigates Basic Search (Pure Hit) >> \"View in Doc View\"
	 *              screen and Come back to Search Page.
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57265", enabled = true, groups = { "regression" })
	public void verifyTilesRetainedInCartWhenUserNavigatesFromDocViewToBasicSearch() throws InterruptedException {

		// login as Users
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		baseClass.stepInfo("Test case Id: RPMXCON-57265 Advanced Search");
		baseClass.stepInfo(
				"Verify that Dropped tiles are retained in shopping cart when User Navigates Basic Search (Pure Hit) >> \"View in Doc View\" screen and Come back to Search Page.");

		// configure and performing search
		sessionSearch.basicMetaDataSearch(Input.metaDataName, null, Input.metaDataCustodianNameInput, null);

		// adding the tile to shopping cart and View in doc view action is performed
		sessionSearch.ViewInDocViews();
		baseClass.stepInfo("Navigated to docView to view docs");

		// navigating to basic search and verifying whether the title is retained in
		// shopping cart
		baseClass.waitTime(3);
		sessionSearch.navigateToSessionSearchPageURL();
		assertion.assertEquals((boolean) sessionSearch.getRemovedocsfromresult().isElementAvailable(5), true);
		assertion.assertAll();
		baseClass.passedStep("Dropped title is retained in shopping cart when User navigates Basic Search");

		// logout
		loginPage.logout();
	}

	@DataProvider
	public Object[][] invaildSearchQueries() {
		return new Object[][] { { "stock investment”~5" }, { "iterative methodology”" } };
	}

	/**
	 * @Author Krishna 12/07/2022
	 * @Description : Verify that belly band message appears when user tries to run
	 *              search having Right double quote only in Advanced Search Query
	 *              Screen.
	 * @param searchString
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57291", dataProvider = "invaildSearchQueries", enabled = true, groups = {
			"regression" })
	public void verifyBellyBandMessageForSearchHavingRightDoubleQuoteOnly(String searchString)
			throws InterruptedException {

		// login as Users
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		baseClass.stepInfo("Test case Id: RPMXCON-57291 Advanced Search");
		baseClass.stepInfo(
				"Verify that belly band message appears when user tries to run search having Right double quote only in Advanced Search Query Screen.");

		// navigate to advanced search Page
		sessionSearch.navigateToAdvancedSearchPage();

		// configuring and searching
		sessionSearch.advancedContentSearchConfigure(searchString);
		sessionSearch.getQuerySearchButton().waitAndClick(5);

		// verifying the warning message
		sessionSearch.verifyProximitySearch();

		// logout
		loginPage.logout();
	}

	/**
	 * Author :Krishna date: 12/07/2022 TestCase Id:RPMXCON-57083 Description
	 * :Verify that User Navigates from Advanced Search result to Document View
	 * screen.
	 * 
	 */

	@Test(description = "RPMXCON-57083", dataProvider = "Users", groups = { "regression" })
	public void verifyNavigatesfrmAdvsearchsreentoDocViewScreen(String username, String password)
			throws InterruptedException {
		BaseClass baseClass = new BaseClass(driver);
		LoginPage loginPage = new LoginPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-57083");
		baseClass.stepInfo("Verify that User Navigates from Advanced Search result to Document View screen.");
		loginPage.loginToSightLine(username, password);
		driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionSearch.navigateToAdvancedSearchPage();
		baseClass.stepInfo("Perform search with metadata");
		sessionSearch.advancedMetaDataSearch(Input.metaDataName, null, Input.custodianName_allen, null);

		baseClass.waitForElement(sessionSearch.getDocsMetYourCriteriaLabel());
		baseClass.dragAndDrop(sessionSearch.getDocsMetYourCriteriaLabel(), sessionSearch.getActionPad());
		sessionSearch.verifySessionSearchPageUrl();

		sessionSearch.ViewInDocViews();

		String actualUrl = driver.getUrl();
		String expUrl = Input.url + "DocumentViewer/DocView";
		SoftAssert asserts = new SoftAssert();
		asserts.assertEquals(expUrl, actualUrl);
		baseClass.stepInfo("Successfully Navigated To DocView Scrren");
		baseClass.passedStep("Verified - that User Navigates from Advanced Search result to Document View screen");

		loginPage.logout();

	}

	/**
	 * Author :Krishna date: 12/07/2022 TestCase Id:RPMXCON-57089 Description :To
	 * Verify as an user login into the Application User will be able to copy a
	 * search query into the new search box by clicking on 'Copy to new search' in
	 * advanced search page
	 * 
	 */

	@Test(description = "RPMXCON-57089", dataProvider = "Users", groups = { "regression" })
	public void verifyCopytoNewSearchinAdvanceSearch(String username, String password) throws InterruptedException {
		BaseClass baseClass = new BaseClass(driver);
		LoginPage loginPage = new LoginPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-57089");
		baseClass.stepInfo(
				"To Verify as an user login into the Application, User will be able to copy a search query into the new search box by clicking on 'Copy to new search' in advanced search page");

		loginPage.loginToSightLine(username, password);

		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.advancedContentSearchConfigure(Input.TallySearch);
		sessionSearch.getAdvancedContentSearchInputAudio().Enter();

		sessionSearch.verifyQueryPresentinSearchbox("1", Input.TallySearch);

		baseClass.waitForElement(sessionSearch.getAdvSearchCopyToNewSearch());
		sessionSearch.getAdvSearchCopyToNewSearch().Click();
		sessionSearch.verifyQueryPresentinSearchbox("2", Input.TallySearch);

		baseClass.passedStep("Verified - as an user login into the Application, "
				+ "User will be able to copy a search query into the new search box by clicking on 'Copy to new search' in advanced search page");
		loginPage.logout();

	}

	/**
	 * Author :Krishna date: 12/07/2022 TestCase Id:RPMXCON-57158 Description
	 * :Verify that Remove functionality working fine for 'Audio' block on 'Advanced
	 * Search' screen
	 * 
	 */

	@Test(description = "RPMXCON-57078", dataProvider = "Users", groups = { "regression" })
	public void verifyRemoveFunctionallityAudioAdvSearch(String username, String password) throws InterruptedException {
		BaseClass baseClass = new BaseClass(driver);
		LoginPage loginPage = new LoginPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-57078");
		baseClass.stepInfo(
				"Verify that Remove functionality working fine for 'Audio' block on 'Advanced Search' screen");

		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged In As : " + username);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.navigateToAdvancedSearchPage();

		baseClass.waitForElement(sessionSearch.getAs_Audio());
		sessionSearch.getAs_Audio().waitAndClick(3);

		if (sessionSearch.getAs_AudioLanguage().isElementAvailable(10)) {
			baseClass.passedStep("Audio Button Clicked & Audio Block Successfully Added");
		} else {
			baseClass.failedStep("Audio Block Not Added");
		}

		baseClass.waitForElement(sessionSearch.getRemoveLink());
		sessionSearch.getRemoveLink().waitAndClick(3);

		if (sessionSearch.getAs_AudioLanguage().isElementAvailable(10) == false) {
			baseClass.passedStep("Remove Functionality Worked Properly");
			baseClass.passedStep("Audio Block Successfully Removed");
		} else {
			baseClass.failedStep("Remove Functionality Not Worked Properly");
		}

		baseClass.passedStep(
				"Verified - that Remove functionality working fine for \"Audio\" block on \"Advanced Search\" screen");
		loginPage.logout();
	}

	/**
	 * Author :Krishna date: 12/07/2022 TestCase Id:RPMXCON-57242 Description
	 * :Verify that Conceptual tile return the result for Metatadata Search in
	 * Advanced Search
	 * 
	 */

	@Test(description = "RPMXCON-57242", dataProvider = "Users", groups = { "regression" })
	public void verifyConceptTileRtnMetadataInAdvSrcScreen(String username, String password)
			throws InterruptedException {
		BaseClass baseClass = new BaseClass(driver);
		LoginPage loginPage = new LoginPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-57242");
		baseClass.stepInfo(
				"Verify that Conceptual tile return the result for Metatadata Search in Advanced Search Screen.");

		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged In As : " + username);
		sessionSearch.advancedMetaDataSearch(Input.docFileExt, null, ".xls", null);

		int pureHit = Integer.parseInt(sessionSearch.verifyPureHitsCount());
		SoftAssert asserts = new SoftAssert();
		asserts.assertNotNull(pureHit,
				"Verified that Search result return documents which satisfied above configured query");

		int conceptPureHit = sessionSearch.runAndVerifyConceptualSearch();

		if (sessionSearch.getConceptualTileHit(conceptPureHit).Visible()) {
			baseClass.passedStep(
					"Verified that Conceptual tile return the result for Metadata Search in Advanced  Search Screen");
		} else {
			baseClass.failedStep("Still Loading on the Screen");
		}
		baseClass.passedStep(
				"Verified - that Conceptual tile return the result for Metatadata Search in Advanced Search Screen");
		loginPage.logout();
	}

	/**
	 * Author :Krishna date: 12/07/2022 TestCase Id:RPMXCON-57343 Description
	 * :Verify that Advanced Proximity Search upload functionality is working proper
	 * in Saved searches
	 * 
	 */

	@Test(description = "RPMXCON-57343", dataProvider = "Users", groups = { "regression" })
	public void verifyAdvProxUploadFunctinSS(String username, String password) throws Exception {
		BaseClass baseClass = new BaseClass(driver);
		LoginPage loginPage = new LoginPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-57343");
		baseClass.stepInfo(
				"Verify that Advanced Proximity Search upload functionality is working proper in Saved searches");
		SavedSearch savedSearch = new SavedSearch(driver);
		String renameFile = savedSearch.renameFile(Input.WPbatchFile);
		String columnHeading = "Search Name";
		String fileLocation = System.getProperty("user.dir") + Input.WPbatchFile + renameFile;

		// Login
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged In As : " + username);
		savedSearch.navigateToSSPage();
		savedSearch.verifySavedSearchPage();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Navigated to SavedSearch Page");

		baseClass.waitForElement(savedSearch.getSavedSearchGroupName(Input.mySavedSearch));
		savedSearch.getSavedSearchGroupName(Input.mySavedSearch).Click();

		// Uploading Batch File
		List<String> searchNames = savedSearch.batchFileUpload(Input.WPbatchFile, renameFile);
		baseClass.passedStep("Verified - that Upload Functionality Working Properly");

		// collecting search Names in attached File
		List<String> excelData = baseClass.readExcelColumnData(fileLocation, columnHeading);
		System.out.println("datas in attached File are readed");
		baseClass.passedStep("data in attached File are readed");

		// comparing the search Names in excel sheet with search names in saved search
		// page
		baseClass.compareListViaContains(searchNames, excelData);
		baseClass.passedStep(
				"Verified - that upload functionality is working proper and all saved Search appears  under Saved searches");

		driver.scrollPageToTop();
		driver.Navigate().refresh();
		savedSearch.deleteUplodedBatchFile(renameFile);

		// logOut
		loginPage.logout();

	}

	/**
	 * Author :Krishna date: 12/07/2022 TestCase Id:RPMXCON-48394 Description
	 * :Verify that Conceptually similar tile does not display any document when
	 * search is run on Advanced Search Screen.
	 * 
	 */

	@Test(description = "RPMXCON-48394", dataProvider = "Users", groups = { "regression" })
	public void verifyConceptTiledoesnotRtnDocInAdvSrcScreen(String username, String password)
			throws InterruptedException {
		String docFileType = "Spreadsheet";
		BaseClass baseClass = new BaseClass(driver);
		LoginPage loginPage = new LoginPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48394");
		baseClass.stepInfo(
				"Verify that Conceptually similar tile does not display any document when search is run on Advanced Search Screen.");

		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged In As : " + username);
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.advancedMetaDataSearch(Input.docFileType, null, docFileType, null);
		driver.waitForPageToBeReady();

		sessionSearch.verifyTileSpinning();
		int conceptTileHits = sessionSearch.runAndVerifyConceptualSearch();
		SoftAssert asserts = new SoftAssert();
		asserts.assertEquals(0, conceptTileHits, "Conceptually similar tile does not display any document");
		asserts.assertAll();
		baseClass.passedStep(
				"Verified - that Conceptually similar tile does not display any document when search is run on Advanced Search Screen.");
		loginPage.logout();
	}

	/**
	 * Author :Krishna date: 12/07/2022 TestCase Id:RPMXCON-48416 Description :
	 * Verify that Search status display Completed on My BackGround Page
	 * 
	 */

	@Test(description = "RPMXCON-48416", dataProvider = "Users", groups = { "regression" })
	public void verifySearchStatusDisplayCompletedOnMyBGPage(String username, String password)
			throws InterruptedException {
		BaseClass baseClass = new BaseClass(driver);
		LoginPage loginPage = new LoginPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48416");
		baseClass.stepInfo("Verify that Search status display Completed on My BackGround Page");
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged In As : " + username);
		baseClass.selectproject(Input.highVolumeProject);
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.metadataSearchesUsingOperators(Input.metaDataName, Input.custodianName_Andrew, "OR",
				Input.metaDataName, Input.searchStringStar, true);
		sessionSearch.SearchBtnAction();

		sessionSearch.verifyTileSpinning();
		String backGroundID = sessionSearch.handleWhenAllResultsPopUpDynamic();
		sessionSearch.verifyAllTilesResultsinAdvSrcScrn();
		baseClass.passedStep("All Tiles Successfully Returned Results");

		baseClass.waitForElement(sessionSearch.getBullHornIcon());
		sessionSearch.getBullHornIcon().waitAndClick(20);

		baseClass.waitForElement(sessionSearch.getViewAllBtn());
		sessionSearch.getViewAllBtn().waitAndClick(20);

		driver.waitForPageToBeReady();
		baseClass.stepInfo("Navigated to My BackGround Task Page...");

		String status = sessionSearch.getRowData_BGT_Page("STATUS", backGroundID);

		SoftAssert asserts = new SoftAssert();
		asserts.assertEquals(status, "COMPLETED");
		asserts.assertAll();
		baseClass.passedStep("Verified - that Search status display Completed on My BackGround Page");
		loginPage.logout();

	}

	/**
	 * Author :Krishna date: 12/07/2022 TestCase Id:RPMXCON-48403 Description :
	 * Verify that 3 options appears on a bellyband when search goes to BackGround
	 * on Advanced Search Result Screen"
	 * 
	 */

	@Test(description = "RPMXCON-48403", dataProvider = "Users", groups = { "regression" })
	public void verifyOptionsInBellyBandWhenSrcGoesBG(String username, String password) throws InterruptedException {
		BaseClass baseClass = new BaseClass(driver);
		LoginPage loginPage = new LoginPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48403");
		baseClass.stepInfo(
				"Verify that 3 options appears on a bellyband when search goes to BackGround on Advanced Search Result Screen");
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged In As : " + username);
		baseClass.selectproject(Input.highVolumeProject);
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.metadataSearchesUsingOperators(Input.metaDataName, Input.custodianName_Andrew, "OR",
				Input.metaDataName, Input.searchStringStar, true);
		sessionSearch.SearchBtnAction();

		sessionSearch.verifyTileSpinning();
		sessionSearch.verifyBellyBandOptions();
		baseClass.passedStep(
				"Verified - that 3 options appears on a bellyband when search goes BackGround on Advanced Search Result Screen");
		loginPage.logout();
	}

	/**
	 * Author :Krishna date: 12/07/2022 TestCase Id:RPMXCON-48408 Description :
	 * Verify that Search status display Executed in left hand side
	 * 
	 */
	@Test(description = "RPMXCON-48408", dataProvider = "Users", groups = { "regression" })
	public void verifySearchStatusDisplayExecutedinLHS(String username, String password) throws InterruptedException {
		BaseClass baseClass = new BaseClass(driver);
		LoginPage loginPage = new LoginPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48408");
		baseClass.stepInfo("Verify that Search status display Executed in left hand side");
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged In As : " + username);
		baseClass.selectproject(Input.highVolumeProject);
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.metadataSearchesUsingOperators(Input.metaDataName, Input.custodianName_Andrew, "OR",
				Input.metaDataName, Input.searchStringStar, true);
		sessionSearch.SearchBtnAction();
		sessionSearch.verifyTileSpinning();
		sessionSearch.verifyAllTilesResultsinAdvSrcScrn();
		baseClass.passedStep("All Tiles Are succesfully returned the Results..");
		sessionSearch.verifySessionSearchPage();

		if (sessionSearch.getExecutedStatusinLHS(1).isElementAvailable(10)) {
			String status = sessionSearch.getExecutedStatusinLHS(1).getText();
			baseClass.passedStep("Executed Status Present in Left Hand Side of Session Search Page" + status);

		} else {
			baseClass.failedStep("Executed Status Not Present in Left Hand Side of Session Search Page");
		}
		baseClass.passedStep("Verified - that Search status display Executed in left hand side ");
		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify warning messgae displays if search token entered as
	 *              Date in Advanced Search Query Screen (Message 40001000011)
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57373", enabled = true, dataProvider = "Users", groups = { "regression" })
	public void verifyBellyBandMsg(String username, String password) throws InterruptedException {
		String search = "12/13/2000";

		// login as User
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("RPMXCON-57373 Advanced Search");
		baseClass.stepInfo(
				"Verify warning messgae displays if search token entered as Date in Advanced Search Query Screen (Message 40001000011)");

		// Verify Expanded Query
		sessionSearch.wrongQueryAlertAdvanceSaerch(search, 3, "non fielded", null);
		sessionSearch.advanedContentDraftSearch(search);

		// click yes Btn in warning message and verify result
		sessionSearch.saveAndReturnPureHitCount();

		// click No Btn
		driver.scrollPageToTop();
		sessionSearch.getModifyASearch().waitAndClick(10);
		sessionSearch.SearchBtnAction();
		baseClass.waitForElement(sessionSearch.getNoBtn());
		sessionSearch.getNoBtn().waitAndClick(10);

		// verify session search Page
		sessionSearch.verifySessionSearchPageUrl();

		assertion.assertAll();
		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify search result appears for parenthesis with terms in
	 *              Advanced Search Query screen ,(Message 40001000005)
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57369", enabled = true, dataProvider = "Users", groups = { "regression" })
	public void verifyResultForParenthesis(String username, String password) throws InterruptedException {
		String search = "(that this verification)";

		// login as User
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("RPMXCON-57369 Advanced Search");
		baseClass.stepInfo(
				"Verify search result appears for parenthesis with terms in Advanced Search Query screen ,(Message 40001000005)");

		// Verify Expanded Query
		sessionSearch.wrongQueryAlertAdvanceSaerch(search, 10, "non fielded", null);
		sessionSearch.advanedContentDraftSearch(search);

		// click yes Btn in warning message and verify result
		sessionSearch.saveAndReturnPureHitCount();

		// click No Btn
		driver.scrollPageToTop();
		sessionSearch.getModifyASearch().waitAndClick(10);
		sessionSearch.SearchBtnAction();
		baseClass.waitForElement(sessionSearch.getNoBtn());
		sessionSearch.getNoBtn().waitAndClick(10);

		// verify session search Page
		sessionSearch.verifySessionSearchPageUrl();

		assertion.assertAll();
		loginPage.logout();
	}

	@DataProvider(name = "reserve")
	public Object[][] reserve() {
		return new Object[][] { { "“iterative methodology(requirements OR collaboration)”~7" },
				{ "“iterative methodology(requirements OR collaboration)”~7" }, };
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that result appears for proximity having 2+ words with
	 *              OR in Advanced Search Query Screen.
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57306", enabled = true, dataProvider = "reserve", groups = { "regression" })
	public void verifyResultForProximityWith2plus(String search) throws InterruptedException {

		// login as User
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("RPMXCON-57306 Advanced Search");
		baseClass.stepInfo(
				"Verify that result appears for proximity having 2+ words with OR in Advanced Search Query Screen.");

		// Verify Expanded Query
		sessionSearch.wrongQueryAlertAdvanceSaerch(search, 13, "non fielded", null);

		assertion.assertAll();
		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description :Verify that Dropped tiles are retained in shopping cart when
	 *              User Navigates Advanced Search (Pure Hit) >> "Analyze in
	 *              Communications Explorer" screen and Come back to Search Page.
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57255", enabled = true, groups = { "regression" })
	public void verifyCartAfterNaviToCommExp() throws InterruptedException {

		// login as User
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("RPMXCON-57255 Advanced Search");
		baseClass.stepInfo(
				"Verify that Dropped tiles are retained in shopping cart when User Navigates Advanced Search (Pure Hit) >> \"Analyze in Communications Explorer\" screen and Come back to Search Page.");

		// Verify Expanded Query
		sessionSearch.advancedMetaDataSearch(Input.metaDataName, null, Input.custodianName_Andrew, null);
		sessionSearch.addPurehitAndActionAsConceptOrComm(true, false);

		sessionSearch.navigateToSessionSearchPageURL();
		String passMSg = "Added Purehit is Present in Cart";
		baseClass.ValidateElement_Presence(sessionSearch.getRemoveAddBtn(), passMSg);

		assertion.assertAll();
		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description :Verify that Dropped tiles are retained in shopping cart when
	 *              User Navigates Basic Search (Pure Hit) >> "Analyze in
	 *              Communications Explorer" screen and Come back to Search Page..
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57260", enabled = true, groups = { "regression" })
	public void verifyCartAfterNaviToCommExp_BS() throws InterruptedException {

		// login as User
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("RPMXCON-57260 Advanced Search");
		baseClass.stepInfo(
				"Verify that Dropped tiles are retained in shopping cart when User Navigates Basic Search (Pure Hit) >> \"Analyze in Communications Explorer\" screen and Come back to Search Page.");

		// Verify Expanded Query
		sessionSearch.basicMetaDataSearch(Input.metaDataName, null, Input.custodianName_Andrew, null);
		sessionSearch.addPurehitAndActionAsConceptOrComm(true, false);

		sessionSearch.navigateToSessionSearchPageURL();
		String passMSg = "Added Purehit is Present in Cart";
		baseClass.ValidateElement_Presence(sessionSearch.getRemoveAddBtn(), passMSg);

		assertion.assertAll();
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:15/12/2022 RPMXCON-48559
	 * @throws Exception
	 * @Description Verify that - After impersonation (RMU to Reviewer) - User can
	 *              search NON-Audio - commented documents in Advanced Search.
	 */
	@Test(description = "RPMXCON-48559", enabled = true, groups = { "regression" })
	public void verifyAfterimpersonateInNonAudioDocsInAdvancedSearch() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-48559");
		baseClass.stepInfo(
				"Verify that - After impersonation (RMU to Reviewer) - User can search NON-Audio - commented documents in Advanced Search.");

		SessionSearch search = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		String docComment = "Reviewed" + Utility.dynamicNameAppender();
		int count = 2;

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully logged in as '" + Input.rmu1userName + "'");

		// performing metaData search and adding pureHit to cart and viewing in docView
		search.advancedMetaDataSearch(Input.metaDataName, null, Input.metaDataCustodianNameInput, null);
		baseClass.stepInfo("performing advanced metadata search.");
		search.viewInDocView();

		// Apply comments to document
		docView.addCommentAndSave(docComment, true, count);

		// impersonate RMU to REV
		baseClass.impersonateRMUtoReviewer();

		int pureHit1 = search.getCommentsOrRemarksCount_AdvancedSearch(Input.documentComments, docComment);

		// verifying the pureHit count with number of comments added
		baseClass.digitCompareEquals(pureHit1, count, "pureHit count match with comments count",
				"pureHit count doesn't match with comments count");
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:15/12/2022 RPMXCON-48560
	 * @throws Exception
	 * @Description Verify that - After impersonation (RMU to Reviewer) - User can
	 *              search NON-Audio - Remarked documents in Advanced Search.
	 */
	@Test(description = "RPMXCON-48560", enabled = true, groups = { "regression" })
	public void verifyAfterImpersonateAddRemarkDocumentsInAdvancedSearch() throws ParseException, Exception {
		int remarkCount1 = 1;
		String selectField = "Remark";
		DocViewPage docView = new DocViewPage(driver);
		DocViewRedactions docviewRedact = new DocViewRedactions(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-48560");
		baseClass.stepInfo(
				"Verify that - After impersonation (RMU to Reviewer) - User can search NON-Audio - Remarked documents in Advanced Search.");

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully logged in as '" + Input.rmu1userName + "'");

		// performing metaData search and adding pureHit to cart and viewing in docView
		sessionSearch.advancedMetaDataSearch(Input.metaDataName, null, Input.metaDataCustodianNameInput, null);
		baseClass.stepInfo("performing advanced metadata search.");
		sessionSearch.viewInDocView();

		// adding remark and performing search using remark
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		docviewRedact.addremark(Input.reviewed, -10, -10, 40, 50);

		baseClass.stepInfo("adding Remark to document");

		// impersonate RMU to REV
		baseClass.impersonateRMUtoReviewer();

		int pureHit = sessionSearch.getCommentsOrRemarksCount_AdvancedSearch(selectField, Input.reviewed);
		// verifying the pureHit count with number of remark added
		baseClass.digitCompareEquals(pureHit, remarkCount1, "pureHit count match with remark count",
				"pureHit count doesn't match with remark count");

		loginPage.logout();
	}

	/**
	 * @author Brundha.T TestCase id-RPMXCON-48566
	 * @Description Verify that - After impersonation (RMU to Reviewer) - User can
	 *              search Audio - Remarked documents in Advanced Search.
	 */
	@Test(description = "RPMXCON-48566", enabled = true, groups = { "regression" })
	public void verifyingAudioRemarkCountWhileImpersonatingRmuAsRev() throws Exception {

		DocViewPage docView = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		String remark = "remark" + Utility.dynamicNameAppender();
		baseClass.stepInfo("RPMXCON-48566");
		baseClass.stepInfo(
				"Verify that - After impersonation (RMU to Reviewer) - User can search Audio - Remarked documents in Advanced Search.");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in As : " + Input.rmu1userName);
		int remarkCount = 1;

		baseClass.stepInfo("Navigate to Search page");
		sessionSearch.navigateToSessionSearchPageURL();

		baseClass.stepInfo("Searching audio doc and navigate to docview page");
		sessionSearch.audioSearch(Input.audioSearch, Input.language);
		sessionSearch.ViewInDocView();

		baseClass.stepInfo("Adding remark for audio document");
		docView.audioRemark(remark);

		baseClass.waitTime(2);
		baseClass.stepInfo("Impersonating RMU as REV");
		baseClass.impersonateRMUtoReviewer();

		int PureHit = sessionSearch.getCommentsOrRemarksCount_AdvancedSearch("Remark", remark);

		baseClass.digitCompareEquals(PureHit, remarkCount, "pureHit count match with remark count",
				"pureHit count doesn't match with remark count");

		loginPage.logout();

	}

	/**
	 * @author Brundha.T TestCase id-RPMXCON-48565
	 * @Description Verify that - After impersonation (RMU to Reviewer) - User can
	 *              search Audio - commented documents in Advanced Search.
	 */
	@Test(description = "RPMXCON-48565", enabled = true, groups = { "regression" })
	public void verifyingAudioCommentsCountWhileImpersonatingRmuAsRev() throws Exception {

		DocViewPage docView = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("RPMXCON-48565");
		baseClass.stepInfo(
				"Verify that - After impersonation (RMU to Reviewer) - User can search Audio - commented documents in Advanced Search.");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in As : " + Input.rmu1userName);
		int CommentCount = 2;
		String docComment = "Reviewed" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Navigate to Search page");
		sessionSearch.navigateToSessionSearchPageURL();

		baseClass.stepInfo("Searching audio doc and navigate to docview page");
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();

		driver.Navigate().refresh();
		baseClass.waitTime(2);

		baseClass.stepInfo("Add Comments in audio doc");
		docView.addCommentAndSave(docComment, true, CommentCount);

		baseClass.waitTime(2);
		baseClass.stepInfo("Impersonating RMU as REV");
		baseClass.impersonateRMUtoReviewer();

		baseClass.stepInfo("Navigate to Search page");
		sessionSearch.navigateToSessionSearchPageURL();

		baseClass.stepInfo("verifying comments document count");
		int PureHit = sessionSearch.getCommentsOrRemarksCount_AdvancedSearch(Input.documentComments, docComment);

		baseClass.digitCompareEquals(PureHit, CommentCount, "pureHit count match with comments count",
				"pureHit count doesn't match with comments count");

		loginPage.logout();

	}

	/**
	 * Author :Arunkumar date: 15/12/2022 TestCase Id:RPMXCON-49289 Description
	 * :Verify that information message does not appear above the Tiles and below
	 * "Your Search Results" labels in Result Screen when the user Edit search not
	 * having any Advanced Search option.
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-49289", enabled = true, groups = { "regression" })
	public void verifyInformationMessageWhenNotHavingOption() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49289");
		baseClass.stepInfo("verify information message when the user Edit search not having any option.");
		String searchName = "search" + Utility.dynamicNameAppender();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("perform advanced metadata search");
		sessionSearch.MetaDataSearchInAdvancedSearch(Input.metaDataName, Input.custodianName_Andrew);
		baseClass.stepInfo("save the search result without selecting any option");
		sessionSearch.saveSearchadvanced(searchName);
		System.out.println("saved search" + searchName);
		baseClass.stepInfo("Select the saved search");
		savedSearch.navigateToSavedSearchPage();
		savedSearch.selectSavedSearch(searchName);
		baseClass.stepInfo("Click on edit and verify");
		baseClass.waitForElement(savedSearch.getSavedSearchEditButton());
		savedSearch.getSavedSearchEditButton().waitAndClick(10);
		baseClass.waitForElement(sessionSearch.getNewSearch());
		baseClass.verifyUrlLanding(Input.url + "Search/Searches", "Redirected to search page",
				"not redirected to search page");
		baseClass.stepInfo("verify information message ");
		baseClass.ValidateElement_Absence(sessionSearch.getInformationMessage(), "Information message");
		baseClass.passedStep("Information message not displayed when not selected any option");
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: 15/12/2022 TestCase Id:RPMXCON-49274 Description
	 * :Verify that information message does not appear above the Tiles and below
	 * "Your Search Results" labels in Result Screen when the user Select none of
	 * Advanced Search option.
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-49274", enabled = true, groups = { "regression" })
	public void verifyInformationMessageWhenNoSelection() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49274");
		baseClass.stepInfo("verify information message when the user select none of option.");

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("configure advanced metadata search query");
		sessionSearch.configureAdvancedMetaDataQuery(Input.metaDataName, Input.custodianName_Andrew);
		baseClass.stepInfo("verify no option selected in advanced search option");
		sessionSearch.verifyAdvancedSearchOptionStatus();
		baseClass.passedStep("None of te advanced search option gets selected");
		baseClass.stepInfo("click on search");
		baseClass.waitForElement(sessionSearch.getQuerySearchButton());
		sessionSearch.getQuerySearchButton().waitAndClick(10);
		sessionSearch.returnPurehitCount();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify information message ");
		baseClass.ValidateElement_Absence(sessionSearch.getInformationMessage(), "Information message");
		baseClass.passedStep("Information message not displayed when not selected any option");
		loginPage.logout();

	}

	/**
	 * @author
	 * @Description :Verify that pure hit tile does not get corrupted (CSS Removed)
	 *              in shopping cart when search goes to BackGround and User elect
	 *              "When All Results Are Ready" on Advanced Search
	 *              Screen.RPMXCON-48651
	 */

	@Test(description = "RPMXCON-48651", enabled = true, groups = { "regression" })
	public void verifyPureHitTileDoesNotGetCorruptedInShoppingCartAndUserSelectWhenAllResultsAreReadyOnAdvanSearch()
			throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48651 Advanced Search.");
		baseClass.stepInfo(
				"Verify that pure hit tile does not get corrupted (CSS Removed) in shopping cart when search goes to BackGround and User elect \"When All Results Are Ready\" on Advanced Search Screen.");

		// login
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Configure valid Query and Click on "Search" button
		baseClass.stepInfo("Configure valid Query and Click on \"Search\" button.");
		sessionSearch.AdvContentSearchWithoutPopHandling(Input.searchStringStar);

		// Click on "When All Results Are Ready" button
		sessionSearch.handleWhenAllResultsBtnInUncertainPopup();
		baseClass.stepInfo("Click on \"When All Results Are Ready\" button.");
		// Drag only Pure Hit tile into Shipping cart and wait for other tiles appear
		// (Thread document/Near Duplicate/Family Member)
		baseClass.stepInfo(
				"Drag only Pure Hit tile into Shipping cart and wait for other tiles appear (Thread document/Near Duplicate/Family Member).");
		sessionSearch.addPureHit();
		sessionSearch.verifyAllTilesResultsinAdvSrcScrn();

		// Verify that pure hit tile does not get corrupted (CSS Removed) in shopping
		// cart when search goes to BackGround and User Select "When All Results Are
		// Ready" on Advanced Search Screen
		baseClass.ValidateElement_Presence(sessionSearch.getPureHitsCount(), "PureHit Tile");
		baseClass.passedStep(
				"Verified that pure hit tile does not get corrupted (CSS Removed) in shopping cart when search goes to BackGround and User Select \"When All Results Are Ready\" on Advanced Search Screen.");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Description :Verify that result appears for proximity having 2 Phrases
	 *              within brackets in Advanced Search Query Screen.RPMXCON-57335
	 */
	@Test(description = "RPMXCON-57335", enabled = true, groups = { "regression" })
	public void verifyResultAppearsForProximityHavingTwoPhrasesWithinBracketsInAdvancedSearch() {

		String searchString = "\"(\"development methodology\") (\"money related\")\"~5";
		String exampleSearchString = "\"(\"development methodology\") (\"money related\")\"~5";

		baseClass.stepInfo("Test case Id: RPMXCON-57335 Advanced Search.");
		baseClass.stepInfo(
				"Verify that result appears for proximity having 2 Phrases within brackets in Advanced Search Query Screen.");

		// login
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// configure search Query
		sessionSearch.advanedContentDraftSearch(searchString);
		baseClass.stepInfo("Search Query configured.");

		// verify that application displays Proximity warning message
		sessionSearch.verifyWarningMessage(true, true, 5);
		baseClass.passedStep("verified that application displays Proximity warning message.");

		// Click on "Yes" button
		baseClass.waitTime(2);
		sessionSearch.tallyContinue(5);
		baseClass.waitTime(2);
		// Verify that result appears for proximity having 2 Phrases within brackets in
		// Advanced Search Query Screen.
		int searchStringPureHit = sessionSearch.returnPurehitCount();
		baseClass.passedStep(
				"Verified that result appears for proximity having 2 Phrases within brackets in Advanced Search Query Screen.");

		// performing search for given example proximity search query.
		baseClass.stepInfo("performing search for given example proximity search query.");
		sessionSearch.advancedNewContentSearchNotPureHit(exampleSearchString);
		sessionSearch.tallyContinue(5);
		int exampleSearchStringPureHit = sessionSearch.returnPurehitCount();

		// verify that pureHit appear for proximity having 2 Phrases within brackets
		// match with pureHit appear for given example proximity Search Query.
		assertion.assertEquals(searchStringPureHit, exampleSearchStringPureHit);
		assertion.assertAll();
		baseClass.passedStep(
				"verified that pureHit appear for proximity having 2 Phrases within brackets match with pureHit appear for given example proximity Search Query.");

		// logOut
		loginPage.logout();

	}

	/**
	 * @author
	 * @Description :Verify that - Application returns all the documents which are
	 *              available under Assignments - Distributed To in search
	 *              result.RPMXCON-48112
	 */
	@Test(description = "RPMXCON-48112", enabled = true, groups = { "regression" })
	public void verifyApplicationReturnsAllDocumentsAvailableUnderAssignments() throws InterruptedException {

		AssignmentsPage assignment = new AssignmentsPage(driver);
		String assignmentName = "assignment" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id: RPMXCON-48112");
		baseClass.stepInfo(
				"Verify that - Application returns all the documents which are available under Assignments - Distributed To in search result.");

		// login
		loginPage.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		// performing basic search and bulkAssign
		baseClass.stepInfo("performing basic search and bulkAssign.");
		int pureHit = sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssign();

		// creating Assignment
		assignment.assignmentCreationAsPerCf(assignmentName, Input.codeFormName);
		int docDistributeCount = Math.round(pureHit / 2);

		// adding Reviewer and Distributing the documents to the Reviewer
		baseClass.stepInfo("adding Reviewer and Distributing the documents to the Reviewer.");
		assignment.distributeTheGivenDocCountToReviewer(Integer.toString(docDistributeCount));

		// getting Total Documents In Assignment
		assignment.navigateToAssignmentsPage();
		int assignmentDocCount = Integer.parseInt(assignment.selectAssignmentToView(assignmentName));
		baseClass.stepInfo("Total Documents In Assignment '" + assignmentName + "' : " + assignmentDocCount);

		// performing Assignment work product search.
		baseClass.stepInfo("performing Assignment work product search.");
		baseClass.selectproject();
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.workProductSearch("assignments", assignmentName, true);
		int WPAssignPureHitCount = sessionSearch.serarchWP();

		// Verify that - Application returns all the documents which are available under
		// Assignments - Distributed To in search result.
		baseClass.digitCompareEquals(assignmentDocCount, WPAssignPureHitCount,
				"Verified that Application returns all the documents which are available under Assignments - Distributed To  in search result.",
				"pureHit doesn't match with Assignment Document count.");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @Description Verify that New search/Advance Search/Modify Search should be
	 *              responsive when 10 session searches are in session search panel
	 *              and new search is removed from session search panel
	 */
	@Test(description = "RPMXCON-48616", enabled = true, dataProvider = "SavedSearchwithUsers", groups = { "regression" })
	public void veriyPgResponsiveAfter10SeacrchesInAdvSearch(String username, String password, String role)
			throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48616");
		baseClass.stepInfo(
				"Verify that New search/Advance Search/Modify Search should be responsive when 10 session searches are in session search panel and new search is removed from session search panel");
		TagsAndFoldersPage tagPage = new TagsAndFoldersPage(driver);
		ProductionPage page = new ProductionPage(driver);
		SoftAssert sa = new SoftAssert();
		String productionname = null;
		// login
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		page.navigateToProductionPage();
		baseClass.waitForElementCollection(page.getProductionItem());
		List<String> availableProduction = baseClass.availableListofElements(page.getProductionItem());
		String pName = availableProduction.get(0);
		if (pName != null) {
			productionname = pName;
		} else {
			productionname = "ASprod" + Utility.dynamicNameAppender();
			String PrefixID = "A_" + Utility.dynamicNameAppender();
			String SuffixID = "_P" + Utility.dynamicNameAppender();
			;
			String foldername = "FolderProd" + Utility.dynamicNameAppender();
			String Tagname = "Tag" + Utility.dynamicNameAppender();
			driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
			tagPage.CreateFolder(foldername, Input.securityGroup);
			tagPage.CreateTagwithClassification(Tagname, "Privileged");
			baseClass.selectproject();
			sessionSearch.basicContentSearch(Input.testData1);
			sessionSearch.bulkFolderExisting(foldername);
			page.CreateNewProduction(productionname, PrefixID, SuffixID, foldername, Tagname);
			baseClass.stepInfo("Created a Production " + productionname);
		}
		loginPage.logout();
		loginPage.loginToSightLine(username, password);
		driver.getWebDriver().get(Input.url + "Search/Searches");
		baseClass.waitForElement(sessionSearch.getAdvancedSearchLink());
		sessionSearch.getAdvancedSearchLink().waitAndClick(10);
		sessionSearch.advancedSearchConceptual("test");
		sessionSearch.getNewSearch().waitAndClick(10);
		sessionSearch.contentMetadataSearch(Input.metaDataName, Input.custodianName_Andrew, "Adv");
		sessionSearch.getNewSearch().waitAndClick(10);
		sessionSearch.contentMetadataSearch(Input.docFileType, Input.searchDocFileType, "Adv");
		sessionSearch.getNewSearch().waitAndClick(10);
		sessionSearch.contentMetadataSearch(Input.docFileExt, ".xls", "Adv");
		sessionSearch.getNewSearch().waitAndClick(10);
		sessionSearch.contentMetadataSearch(Input.docFileType, Input.filterDataInput1, "Adv");
		sessionSearch.getNewSearch().waitAndClick(10);
		sessionSearch.contentMetadataSearch(Input.docFileName, "AttachCount", "Adv");
		sessionSearch.getNewSearch().waitAndClick(10);
		sessionSearch.contentMetadataSearch(Input.emailAllDomain, Input.filterDataInput3, "Adv");
		if (role == "PA") {
			sessionSearch.getNewSearch().waitAndClick(10);
			baseClass.waitTillElemetToBeClickable(sessionSearch.getWorkproductBtn());
			sessionSearch.getWorkproductBtn().waitAndClick(5);
			sessionSearch.selectSecurityGinWPS(Input.securityGroup);
			baseClass.waitTillElemetToBeClickable(sessionSearch.searchBtn());
			sessionSearch.searchBtn().waitAndClick(10);
		}
		sessionSearch.getNewSearch().waitAndClick(10);
		baseClass.waitTillElemetToBeClickable(sessionSearch.getWorkproductBtn());
		sessionSearch.getWorkproductBtn().waitAndClick(5);
		sessionSearch.selectRedactioninWPS(Input.defaultRedactionTag);
		baseClass.waitTillElemetToBeClickable(sessionSearch.searchBtn());
		sessionSearch.searchBtn().waitAndClick(10);
		sessionSearch.getNewSearch().waitAndClick(10);
		baseClass.waitTillElemetToBeClickable(sessionSearch.getWorkproductBtn());
		sessionSearch.getWorkproductBtn().waitAndClick(5);
		sessionSearch.selectTagInASwp("Privileged");
		baseClass.waitTillElemetToBeClickable(sessionSearch.searchBtn());
		sessionSearch.searchBtn().waitAndClick(10);
		baseClass.waitForElement(sessionSearch.getPureHit_UsingLast());
		String pureHits = sessionSearch.getPureHit_UsingLast().getText();
		if (role != "REV") {
			sessionSearch.getNewSearch().waitAndClick(10);
			baseClass.waitTillElemetToBeClickable(sessionSearch.getWorkproductBtn());
			sessionSearch.getWorkproductBtn().waitAndClick(5);
			sessionSearch.selectProductionstInASwp(productionname);
			baseClass.waitTillElemetToBeClickable(sessionSearch.searchBtn());
			sessionSearch.searchBtn().waitAndClick(10);
		}
		sessionSearch.getNewSearch().waitAndClick(10);
		baseClass.waitForElement(sessionSearch.getContentAndMetaDatabtn());
		sessionSearch.getContentAndMetaDatabtn().waitAndClick(3);
		baseClass.waitTillElemetToBeClickable(sessionSearch.removeBtn());
		sessionSearch.removeBtn().waitAndClick(10);
		if (sessionSearch.contentBtnPanel().isElementAvailable(5) == false) {
			baseClass.passedStep("Search panel is removed successfully");
		} else {
			baseClass.passedStep("Search panel is not removed successfully");
		}
		baseClass.waitTillElemetToBeClickable(sessionSearch.getWorkproductBtn());
		sessionSearch.getWorkproductBtn().waitAndClick(5);
		sessionSearch.selectTagInASwp("Privileged");
		baseClass.waitTillElemetToBeClickable(sessionSearch.searchBtn());
		sessionSearch.searchBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sessionSearch.getPureHit_UsingLast());
		String pureHits1 = sessionSearch.getPureHit_UsingLast().getText();
		sa.assertEquals(pureHits, pureHits1);
		sa.assertAll();
		baseClass.passedStep("Page is responsive after the ten searches and removed the panel successfully");
		loginPage.logout();

	}

	/**
	 * @author Iyappan.Kasinathan
	 * @Description Verify that New search/Advance Search/Modify Search should be
	 *              responsive when 10 basic & advance session searches are in
	 *              session search panel and new search is removed from session
	 *              search panel
	 */
	@Test(description = "RPMXCON-48617", enabled = true, dataProvider = "SavedSearchwithUsers", groups = { "regression" })
	public void veriyPgResponsiveAfter10SearchesInAdvAndBasicSearch(String username, String password, String role)
			throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48617");
		baseClass.stepInfo(
				"Verify that New search/Advance Search/Modify Search should be responsive when 10 basic & advance session searches are in session search panel and new search is removed from session search panel");
		TagsAndFoldersPage tagPage = new TagsAndFoldersPage(driver);
		ProductionPage page = new ProductionPage(driver);
		SoftAssert sa = new SoftAssert();
		String productionname = null;
		// login
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		page.navigateToProductionPage();
		baseClass.waitForElementCollection(page.getProductionItem());
		List<String> availableProduction = baseClass.availableListofElements(page.getProductionItem());
		String pName = availableProduction.get(0);
		if (pName != null) {
			productionname = pName;
		} else {
			productionname = "ASprod" + Utility.dynamicNameAppender();
			String PrefixID = "A_" + Utility.dynamicNameAppender();
			String SuffixID = "_P" + Utility.dynamicNameAppender();
			;
			String foldername = "FolderProd" + Utility.dynamicNameAppender();
			String Tagname = "Tag" + Utility.dynamicNameAppender();
			driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
			tagPage.CreateFolder(foldername, Input.securityGroup);
			tagPage.CreateTagwithClassification(Tagname, "Privileged");
			baseClass.selectproject();
			sessionSearch.basicContentSearch(Input.testData1);
			sessionSearch.bulkFolderExisting(foldername);
			page.CreateNewProduction(productionname, PrefixID, SuffixID, foldername, Tagname);
			baseClass.stepInfo("Created a Production " + productionname);
		}
		loginPage.logout();
		loginPage.loginToSightLine(username, password);
		driver.getWebDriver().get(Input.url + "Search/Searches");
		baseClass.waitForElement(sessionSearch.getAdvancedSearchLink());
		sessionSearch.getAdvancedSearchLink().waitAndClick(10);
		sessionSearch.advancedSearchConceptual("test");
		sessionSearch.getNewSearch().waitAndClick(10);
		sessionSearch.getBasicSearchLink().waitAndClick(10);
		sessionSearch.contentMetadataSearch(Input.metaDataName, Input.custodianName_Andrew, "Basic");
		sessionSearch.getNewSearch().waitAndClick(10);
		sessionSearch.contentMetadataSearch(Input.docFileType, Input.searchDocFileType, "Basic");
		sessionSearch.getNewSearch().waitAndClick(10);
		sessionSearch.contentMetadataSearch(Input.docFileExt, ".xls", "Basic");
		sessionSearch.getNewSearch().waitAndClick(10);
		sessionSearch.contentMetadataSearch(Input.docFileType, Input.filterDataInput1, "Basic");
		sessionSearch.getNewSearch().waitAndClick(10);
		sessionSearch.contentMetadataSearch(Input.docFileName, "AttachCount", "Basic");
		sessionSearch.getNewSearch().waitAndClick(10);
		baseClass.waitForElement(sessionSearch.getAdvancedSearchLinkCurrent());
		sessionSearch.getAdvancedSearchLinkCurrent().waitAndClick(10);
		if (role == "PA") {
			baseClass.waitTillElemetToBeClickable(sessionSearch.getWorkproductBtn());
			sessionSearch.getWorkproductBtn().waitAndClick(5);
			sessionSearch.selectSecurityGinWPS(Input.securityGroup);
			baseClass.waitTillElemetToBeClickable(sessionSearch.searchBtn());
			sessionSearch.searchBtn().waitAndClick(10);
			sessionSearch.getNewSearch().waitAndClick(10);
		}
		baseClass.waitTillElemetToBeClickable(sessionSearch.getWorkproductBtn());
		sessionSearch.getWorkproductBtn().waitAndClick(5);
		sessionSearch.selectRedactioninWPS(Input.defaultRedactionTag);
		baseClass.waitTillElemetToBeClickable(sessionSearch.searchBtn());
		sessionSearch.searchBtn().waitAndClick(10);
		sessionSearch.getNewSearch().waitAndClick(10);
		baseClass.waitTillElemetToBeClickable(sessionSearch.getWorkproductBtn());
		sessionSearch.getWorkproductBtn().waitAndClick(5);
		sessionSearch.selectTagInASwp("Privileged");
		baseClass.waitTillElemetToBeClickable(sessionSearch.searchBtn());
		sessionSearch.searchBtn().waitAndClick(10);
		baseClass.waitForElement(sessionSearch.getPureHit_UsingLast());
		String pureHits = sessionSearch.getPureHit_UsingLast().getText();
		if (role != "REV") {
			sessionSearch.getNewSearch().waitAndClick(10);
			baseClass.waitTillElemetToBeClickable(sessionSearch.getWorkproductBtn());
			sessionSearch.getWorkproductBtn().waitAndClick(5);
			sessionSearch.selectProductionstInASwp(productionname);
			baseClass.waitTillElemetToBeClickable(sessionSearch.searchBtn());
			sessionSearch.searchBtn().waitAndClick(10);
		}
		sessionSearch.getNewSearch().waitAndClick(10);
		baseClass.waitForElement(sessionSearch.getContentAndMetaDatabtn());
		sessionSearch.getContentAndMetaDatabtn().waitAndClick(3);
		baseClass.waitTillElemetToBeClickable(sessionSearch.removeBtn());
		sessionSearch.removeBtn().waitAndClick(10);
		if (sessionSearch.contentBtnPanel().isElementAvailable(5) == false) {
			baseClass.passedStep("Search panel is removed successfully");
		} else {
			baseClass.passedStep("Search panel is not removed successfully");
		}
		baseClass.waitTillElemetToBeClickable(sessionSearch.getWorkproductBtn());
		sessionSearch.getWorkproductBtn().waitAndClick(5);
		sessionSearch.selectTagInASwp("Privileged");
		baseClass.waitTillElemetToBeClickable(sessionSearch.searchBtn());
		sessionSearch.searchBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sessionSearch.getPureHit_UsingLast());
		String pureHits1 = sessionSearch.getPureHit_UsingLast().getText();
		sa.assertEquals(pureHits, pureHits1);
		sa.assertAll();
		baseClass.passedStep("Page is responsive after the ten searches and removed the panel successfully");
		loginPage.logout();

	}

	/**
	 * @author Iyappan.Kasinathan
	 * @Description Verify that Advance Search/Modify Search should be responsive
	 *              when more than 10 advance saved searches are edited in session
	 *              search panel
	 */
	@Test(description = "RPMXCON-48575", enabled = true, dataProvider = "SavedSearchwithUsers", groups = { "regression" })
	public void veriyPgResponsiveAfter10SeacrchesInAdvSearchSS(String username, String password, String role)
			throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48575");
		baseClass.stepInfo(
				"Verify that Advance Search/Modify Search should be responsive when more than 10 advance saved searches are edited in session search panel");
		TagsAndFoldersPage tagPage = new TagsAndFoldersPage(driver);
		ProductionPage page = new ProductionPage(driver);
		SavedSearch saveSearch = new SavedSearch(driver);
		SoftAssert sa = new SoftAssert();
		String savedSearchName = "ss" + Utility.dynamicNameAppender();
		String productionname = null;
		// login
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		page.navigateToProductionPage();
		baseClass.waitForElementCollection(page.getProductionItem());
		List<String> availableProduction = baseClass.availableListofElements(page.getProductionItem());
		String pName = availableProduction.get(0);
		if (pName != null) {
			productionname = pName;
		} else {
			productionname = "ASprod" + Utility.dynamicNameAppender();
			String PrefixID = "A_" + Utility.dynamicNameAppender();
			String SuffixID = "_P" + Utility.dynamicNameAppender();
			;
			String foldername = "FolderProd" + Utility.dynamicNameAppender();
			String Tagname = "Tag" + Utility.dynamicNameAppender();
			driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
			tagPage.CreateFolder(foldername, Input.securityGroup);
			tagPage.CreateTagwithClassification(Tagname, "Privileged");
			baseClass.selectproject();
			sessionSearch.basicContentSearch(Input.testData1);
			sessionSearch.bulkFolderExisting(foldername);
			page.CreateNewProduction(productionname, PrefixID, SuffixID, foldername, Tagname);
			baseClass.stepInfo("Created a Production " + productionname);
		}
		loginPage.logout();
		loginPage.loginToSightLine(username, password);
		String newNode = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "PA", "Yes");
		driver.getWebDriver().get(Input.url + "Search/Searches");
		baseClass.waitForElement(sessionSearch.getAdvancedSearchLink());
		sessionSearch.getAdvancedSearchLink().waitAndClick(10);
		sessionSearch.advancedSearchConceptual("test");
		sessionSearch.saveSearchInNewNode(savedSearchName, newNode);
		sessionSearch.getNewSearch().waitAndClick(10);
		sessionSearch.contentMetadataSearch(Input.metaDataName, Input.custodianName_Andrew, "Adv");
		sessionSearch.saveSearchInNewNode("ss" + Utility.dynamicNameAppender(), newNode);
		sessionSearch.getNewSearch().waitAndClick(10);
		sessionSearch.contentMetadataSearch(Input.docFileType, Input.searchDocFileType, "Adv");
		sessionSearch.saveSearchInNewNode("ss" + Utility.dynamicNameAppender(), newNode);
		sessionSearch.getNewSearch().waitAndClick(10);
		sessionSearch.contentMetadataSearch(Input.docFileExt, ".xls", "Adv");
		sessionSearch.saveSearchInNewNode("ss" + Utility.dynamicNameAppender(), newNode);
		sessionSearch.getNewSearch().waitAndClick(10);
		sessionSearch.contentMetadataSearch(Input.docFileType, Input.filterDataInput1, "Adv");
		sessionSearch.saveSearchInNewNode("ss" + Utility.dynamicNameAppender(), newNode);
		sessionSearch.getNewSearch().waitAndClick(10);
		sessionSearch.contentMetadataSearch(Input.docFileName, "AttachCount", "Adv");
		sessionSearch.saveSearchInNewNode("ss" + Utility.dynamicNameAppender(), newNode);
		if (role == "PA") {
			sessionSearch.getNewSearch().waitAndClick(10);
			baseClass.waitTillElemetToBeClickable(sessionSearch.getWorkproductBtn());
			sessionSearch.getWorkproductBtn().waitAndClick(5);
			sessionSearch.selectSecurityGinWPS(Input.securityGroup);
			baseClass.waitTillElemetToBeClickable(sessionSearch.searchBtn());
			sessionSearch.searchBtn().waitAndClick(10);
			sessionSearch.saveSearchInNewNode("ss" + Utility.dynamicNameAppender(), newNode);
		}
		sessionSearch.getNewSearch().waitAndClick(10);
		baseClass.waitTillElemetToBeClickable(sessionSearch.getWorkproductBtn());
		sessionSearch.getWorkproductBtn().waitAndClick(5);
		sessionSearch.selectRedactioninWPS(Input.defaultRedactionTag);
		baseClass.waitTillElemetToBeClickable(sessionSearch.searchBtn());
		sessionSearch.searchBtn().waitAndClick(10);
		sessionSearch.saveSearchInNewNode("ss" + Utility.dynamicNameAppender(), newNode);
		sessionSearch.getNewSearch().waitAndClick(10);
		baseClass.waitTillElemetToBeClickable(sessionSearch.getWorkproductBtn());
		sessionSearch.getWorkproductBtn().waitAndClick(5);
		sessionSearch.selectTagInASwp("Privileged");
		baseClass.waitTillElemetToBeClickable(sessionSearch.searchBtn());
		sessionSearch.searchBtn().waitAndClick(10);
		baseClass.waitForElement(sessionSearch.getPureHit_UsingLast());
		String pureHits = sessionSearch.getPureHit_UsingLast().getText();
		sessionSearch.saveSearchInNewNode("ss" + Utility.dynamicNameAppender(), newNode);
		if (role != "REV") {
			sessionSearch.getNewSearch().waitAndClick(10);
			baseClass.waitTillElemetToBeClickable(sessionSearch.getWorkproductBtn());
			sessionSearch.getWorkproductBtn().waitAndClick(5);
			sessionSearch.selectProductionstInASwp(productionname);
			baseClass.waitTillElemetToBeClickable(sessionSearch.searchBtn());
			sessionSearch.searchBtn().waitAndClick(10);
			sessionSearch.saveSearchInNewNode("ss" + Utility.dynamicNameAppender(), newNode);
		}
		saveSearch.savedSearchEdit(savedSearchName);
		sessionSearch.getNewSearch().waitAndClick(10);
		baseClass.waitForElement(sessionSearch.getContentAndMetaDatabtn());
		sessionSearch.getContentAndMetaDatabtn().waitAndClick(3);
		baseClass.waitTillElemetToBeClickable(sessionSearch.removeBtn());
		sessionSearch.removeBtn().waitAndClick(10);
		if (sessionSearch.contentBtnPanel().isElementAvailable(5) == false) {
			baseClass.passedStep("Search panel is removed successfully");
		} else {
			baseClass.passedStep("Search panel is not removed successfully");
		}
		baseClass.waitTillElemetToBeClickable(sessionSearch.getWorkproductBtn());
		sessionSearch.getWorkproductBtn().waitAndClick(5);
		sessionSearch.selectTagInASwp("Privileged");
		baseClass.waitTillElemetToBeClickable(sessionSearch.searchBtn());
		sessionSearch.searchBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sessionSearch.getPureHit_UsingLast());
		String pureHits1 = sessionSearch.getPureHit_UsingLast().getText();
		sa.assertEquals(pureHits, pureHits1);
		sa.assertAll();
		baseClass.passedStep("Page is responsive after the ten searches and removed the panel successfully");
		saveSearch.deleteNode(Input.mySavedSearch, newNode);

		loginPage.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @Description Verify that New search/Advance Search/Modify Search should be
	 *              responsive when 10 basic & advance searches edited from saved
	 *              search and new search is removed from session search panel
	 */
	@Test(description = "RPMXCON-48620", enabled = true, dataProvider = "SavedSearchwithUsers", groups = { "regression" })
	public void veriyPgResponsiveAfter10SearchesInAdvAndBasicSearchSS(String username, String password, String role)
			throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48620");
		baseClass.stepInfo(
				"Verify that New search/Advance Search/Modify Search should be responsive when 10 basic & advance searches edited from saved search and new search is removed from session search panel");
		TagsAndFoldersPage tagPage = new TagsAndFoldersPage(driver);
		ProductionPage page = new ProductionPage(driver);
		SavedSearch saveSearch = new SavedSearch(driver);
		SoftAssert sa = new SoftAssert();
		String productionname = null;
		String savedSearchName = "ss" + Utility.dynamicNameAppender();
		// login
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		page.navigateToProductionPage();
		baseClass.waitForElementCollection(page.getProductionItem());
		List<String> availableProduction = baseClass.availableListofElements(page.getProductionItem());
		String pName = availableProduction.get(0);
		if (pName != null) {
			productionname = pName;
		} else {
			productionname = "ASprod" + Utility.dynamicNameAppender();
			String PrefixID = "A_" + Utility.dynamicNameAppender();
			String SuffixID = "_P" + Utility.dynamicNameAppender();
			;
			String foldername = "FolderProd" + Utility.dynamicNameAppender();
			String Tagname = "Tag" + Utility.dynamicNameAppender();
			driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
			tagPage.CreateFolder(foldername, Input.securityGroup);
			tagPage.CreateTagwithClassification(Tagname, "Privileged");
			baseClass.selectproject();
			sessionSearch.basicContentSearch(Input.testData1);
			sessionSearch.bulkFolderExisting(foldername);
			page.CreateNewProduction(productionname, PrefixID, SuffixID, foldername, Tagname);
			baseClass.stepInfo("Created a Production " + productionname);
		}
		loginPage.logout();
		loginPage.loginToSightLine(username, password);
		String newNode = saveSearch.createSearchGroupAndReturn(Input.mySavedSearch, "PA", "Yes");
		driver.getWebDriver().get(Input.url + "Search/Searches");
		baseClass.waitForElement(sessionSearch.getAdvancedSearchLink());
		sessionSearch.getAdvancedSearchLink().waitAndClick(10);
		sessionSearch.advancedSearchConceptual("test");
		sessionSearch.saveSearchInNewNode(savedSearchName, newNode);
		sessionSearch.getNewSearch().waitAndClick(10);
		sessionSearch.getBasicSearchLink().waitAndClick(10);
		sessionSearch.contentMetadataSearch(Input.metaDataName, Input.custodianName_Andrew, "Basic");
		sessionSearch.saveSearchInNewNode("ss" + Utility.dynamicNameAppender(), newNode);
		sessionSearch.getNewSearch().waitAndClick(10);
		sessionSearch.contentMetadataSearch(Input.docFileType, Input.searchDocFileType, "Basic");
		sessionSearch.saveSearchInNewNode("ss" + Utility.dynamicNameAppender(), newNode);
		sessionSearch.getNewSearch().waitAndClick(10);
		sessionSearch.contentMetadataSearch(Input.docFileExt, ".xls", "Basic");
		sessionSearch.saveSearchInNewNode("ss" + Utility.dynamicNameAppender(), newNode);
		sessionSearch.getNewSearch().waitAndClick(10);
		sessionSearch.contentMetadataSearch(Input.docFileType, Input.filterDataInput1, "Basic");
		sessionSearch.saveSearchInNewNode("ss" + Utility.dynamicNameAppender(), newNode);
		sessionSearch.getNewSearch().waitAndClick(10);
		sessionSearch.contentMetadataSearch(Input.docFileName, "AttachCount", "Basic");
		sessionSearch.saveSearchInNewNode("ss" + Utility.dynamicNameAppender(), newNode);
		sessionSearch.getNewSearch().waitAndClick(10);
		baseClass.waitForElement(sessionSearch.getAdvancedSearchLinkCurrent());
		sessionSearch.getAdvancedSearchLinkCurrent().waitAndClick(10);
		if (role == "PA") {
			baseClass.waitTillElemetToBeClickable(sessionSearch.getWorkproductBtn());
			sessionSearch.getWorkproductBtn().waitAndClick(5);
			sessionSearch.selectSecurityGinWPS(Input.securityGroup);
			baseClass.waitTillElemetToBeClickable(sessionSearch.searchBtn());
			sessionSearch.searchBtn().waitAndClick(10);
			sessionSearch.saveSearchInNewNode("ss" + Utility.dynamicNameAppender(), newNode);
			sessionSearch.getNewSearch().waitAndClick(10);
		}

		baseClass.waitTillElemetToBeClickable(sessionSearch.getWorkproductBtn());
		sessionSearch.getWorkproductBtn().waitAndClick(5);
		sessionSearch.selectRedactioninWPS(Input.defaultRedactionTag);
		baseClass.waitTillElemetToBeClickable(sessionSearch.searchBtn());
		sessionSearch.searchBtn().waitAndClick(10);
		sessionSearch.saveSearchInNewNode("ss" + Utility.dynamicNameAppender(), newNode);
		sessionSearch.getNewSearch().waitAndClick(10);
		baseClass.waitTillElemetToBeClickable(sessionSearch.getWorkproductBtn());
		sessionSearch.getWorkproductBtn().waitAndClick(5);
		sessionSearch.selectTagInASwp("Privileged");
		baseClass.waitTillElemetToBeClickable(sessionSearch.searchBtn());
		sessionSearch.searchBtn().waitAndClick(10);
		baseClass.waitForElement(sessionSearch.getPureHit_UsingLast());
		String pureHits = sessionSearch.getPureHit_UsingLast().getText();
		sessionSearch.saveSearchInNewNode("ss" + Utility.dynamicNameAppender(), newNode);
		if (role != "REV") {
			sessionSearch.getNewSearch().waitAndClick(10);
			baseClass.waitTillElemetToBeClickable(sessionSearch.getWorkproductBtn());
			sessionSearch.getWorkproductBtn().waitAndClick(5);
			sessionSearch.selectProductionstInASwp(productionname);
			baseClass.waitTillElemetToBeClickable(sessionSearch.searchBtn());
			sessionSearch.searchBtn().waitAndClick(10);
			sessionSearch.saveSearchInNewNode("ss" + Utility.dynamicNameAppender(), newNode);
		}
		saveSearch.savedSearchEdit(savedSearchName);
		sessionSearch.getNewSearch().waitAndClick(10);
		baseClass.waitForElement(sessionSearch.getContentAndMetaDatabtn());
		sessionSearch.getContentAndMetaDatabtn().waitAndClick(3);
		baseClass.waitTillElemetToBeClickable(sessionSearch.removeBtn());
		sessionSearch.removeBtn().waitAndClick(10);
		if (sessionSearch.contentBtnPanel().isElementAvailable(5) == false) {
			baseClass.passedStep("Search panel is removed successfully");
		} else {
			baseClass.passedStep("Search panel is not removed successfully");
		}
		baseClass.waitTillElemetToBeClickable(sessionSearch.getWorkproductBtn());
		sessionSearch.getWorkproductBtn().waitAndClick(5);
		sessionSearch.selectTagInASwp("Privileged");
		baseClass.waitTillElemetToBeClickable(sessionSearch.searchBtn());
		sessionSearch.searchBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sessionSearch.getPureHit_UsingLast());
		String pureHits1 = sessionSearch.getPureHit_UsingLast().getText();
		sa.assertEquals(pureHits, pureHits1);
		sa.assertAll();
		baseClass.passedStep("Page is responsive after the ten searches and removed the panel successfully");
		saveSearch.deleteNode(Input.mySavedSearch, newNode);
		loginPage.logout();

	}

	/**
	 * @author Vijaya.Rani ModifyDate:19/12/2022 RPMXCON-50025
	 * @throws Exception
	 * @Description Verify that correct result appears for Proximity Queries
	 *              containing Boolean components OR in Advanced Search Query
	 *              Screen.
	 */
	@Test(description = "RPMXCON-50025", enabled = true, groups = { "regression" })
	public void verifyResultAppearsForProximityQueriesContainingBooleanComponentsORinAdvanSearch() {

		baseClass.stepInfo("Test case Id: RPMXCON-50025");
		baseClass.stepInfo(
				"Verify that correct result appears for Proximity Queries containing Boolean components OR in Advanced Search Query Screen.");

		String searchString = "(\"ProximitySearch Iterative\"~15 OR money) OR TruthFinder";

		String exampleSearchString = "(\"ProximitySearch Iterative\"~15 OR money) OR TruthFinder ";

		// login
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully logged in as '" + Input.rmu1userName + "'");

		// configure search Query
		sessionSearch.advanedContentDraftSearch(searchString);
		baseClass.stepInfo("Search Query configured.");

		// Click on "Search" button
		baseClass.stepInfo("Clicking on 'Search' button.");
		sessionSearch.SearchBtnAction();

		// verify that application displays Proximity warning message
		baseClass.waitTime(2);
		baseClass.passedStep("verified that application displays Proximity warning message.");

		// Click on "Yes" button
		baseClass.waitTime(2);
		sessionSearch.tallyContinue(5);
		baseClass.waitTime(2);
		// Verify that correct result appears for Proximity Queries containing Boolean
		// components OR in Advanced Search Query Screen.
		int searchStringPureHit = sessionSearch.returnPurehitCount();
		baseClass.passedStep(
				"Verified that result appears for Proximity Queries containing Boolean components OR in Advanced Search Query Screen.");

		// performing search for given example proximity search query.
		baseClass.stepInfo("performing search for given example proximity search query.");
		sessionSearch.advancedNewContentSearchNotPureHit(exampleSearchString);
		sessionSearch.tallyContinue(5);
		int exampleSearchStringPureHit = sessionSearch.returnPurehitCount();

		// Verify that correct result appears for Proximity Queries containing Boolean
		// components OR in Advanced Search Query Screen. example ("ProximitySearch
		// Iterative"~15 OR m0ney) OR TruthFinder This query returns documents having -
		// term ProximitySearch and Iterative" within 15 words OR M0ney OR truthFinder.
		assertion.assertEquals(searchStringPureHit, exampleSearchStringPureHit);
		assertion.assertAll();
		baseClass.passedStep(
				"verified that pureHit appear for Proximity Queries containing Boolean components OR match with pureHit appear for given example proximity Search Query.");

		// logOut
		loginPage.logout();
	}

	@DataProvider(name = "proximityHavingPhrasesAndTerm")
	public Object[][] proximityHavingPhrasesAndTerm() {
		return new Object[][] { { "(\"Truthful Recall\"~5) AND (\"requirements collaboration\"~5)" },
				{ "(\"Truthful Recall\"~5) AND (\"requirements collaboration\"~5)" },
				{ "(\"Truthful Recall\"~5) AND (\"requirements collaboration\"~5)" } };
	}

	/**
	 * @author Vijaya.Rani ModifyDate:19/12/2022 RPMXCON-49591
	 * @throws Exception
	 * @Description Verify that result appears for proximity having AND operator
	 *              between 2 Proximity Searches in Advanced Search Query Screen.
	 */
	@Test(description = "RPMXCON-49591", dataProvider = "proximityHavingPhrasesAndTerm", enabled = true, groups = {
			"regression" })
	public void verifyResultAppearsForProximityHavingPhrasesANDTermInAdvancedSearchQuery(String searchString) {

		String exampleSearchString = "(\"Truthful Recall\"~5) AND (\"requirements collaboration\"~5)";

		baseClass.stepInfo("Test case Id: RPMXCON-49591 Advanced Search.");
		baseClass.stepInfo(
				"Verify that result appears for proximity having AND operator between 2 Proximity Searches in Advanced Search Query Screen.");

		// login
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// configure search Query
		sessionSearch.advanedContentDraftSearch(searchString);
		baseClass.stepInfo("Search Query configured.");

		// Click on "Search" button
		baseClass.stepInfo("Clicking on 'Search' button.");
		sessionSearch.SearchBtnAction();

		// verify that application displays Proximity warning message
		baseClass.waitTime(2);
		baseClass.passedStep("verified that application displays Proximity warning message.");

		// Click on "Yes" button
		sessionSearch.tallyContinue(5);
		// Verify that result appears for proximity having Phrases and Term in Advanced
		// Search Query Screen.
		int searchStringPureHit = sessionSearch.returnPurehitCount();
		baseClass.passedStep(
				"Verified that  result appears for proximity having  Phrases and Term in Advanced Search Query Screen.");

		// performing search for given example proximity search query.
		baseClass.stepInfo("performing search for given example proximity search query.");
		sessionSearch.advancedNewContentSearchNotPureHit(exampleSearchString);
		sessionSearch.tallyContinue(5);
		baseClass.waitTime(2);
		int exampleSearchStringPureHit = sessionSearch.returnPurehitCount();

		// Verify that Result should appear for proximity having Phrases and Term in
		// Advanced Search Query Screen. example ("Truthful Recall"~5) AND
		// ("requirements collaboration"~5) Documents that contain both Truthful within
		// 5 words of Recall and requirements within 5 of collaboration
		assertion.assertEquals(searchStringPureHit, exampleSearchStringPureHit);
		assertion.assertAll();
		baseClass.passedStep(
				"verified that pureHit appear for test Data proximity Search Query match with pureHit appear for given example proximity Search Query.");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:20/12/2022 RPMXCON-57370
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify belly band message appears when configured query without
	 *              enclosed parantheses "(" and ")" in Advanced Search Query
	 *              screen(Warning message 40001000008).
	 */
	@Test(description = "RPMXCON-57370", dataProvider = "SavedSearchwithUsers", enabled = true, groups = { "regression" })
	public void verifyApplicationAdvancedSearchDisplayWarningMsg(String username, String password, String role)
			throws InterruptedException, AWTException {

		baseClass.stepInfo("Test case Id: RPMXCON-57370");
		baseClass.stepInfo(
				"Verify belly band message appears when configured query without enclosed parantheses \"(\" and \")\" in Advanced Search Query screen(Warning message 40001000008).");

		SessionSearch sessionSearch = new SessionSearch(driver);
		String searchTerm = "(test test";

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Go to Advanced Search page enter the Searchterm");
		sessionSearch.getAdvancedSearchLink().Click();
		driver.waitForPageToBeReady();
		sessionSearch.getContentAndMetaDatabtn().Click();
		sessionSearch.getAdvancedContentSearchInput().SendKeys(searchTerm);
		// Click on Search button
		sessionSearch.getQuerySearchButton().waitAndClick(10);
		String actualMsg = sessionSearch.getAdvancedWaringMsg().getText();
		System.out.println(actualMsg);
		baseClass.stepInfo(actualMsg);
		String expectedMsg = "Parentheses are missing in your search query.";
		driver.waitForPageToBeReady();
		if (actualMsg.contains(expectedMsg)) {
			baseClass.passedStep("Observe that application warning message displayed successfully");
		} else {
			baseClass.failedStep("No such message display");
		}
		sessionSearch.getYesQueryAlert().waitAndClick(3);
		if (sessionSearch.getNewSearch().isDisplayed()) {
			baseClass.passedStep("It should redirect to Search page ");
		} else {
			baseClass.failedStep("It should not redirect to Search page ");
		}

		loginPage.logout();
	}

	@DataProvider(name = "SavedSearchwithUsers")
	public Object[][] SavedSearchwithUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, "PA" },
				{ Input.rmu1userName, Input.rmu1password, "RMU" }, { Input.rev1userName, Input.rev1password, "REV" } };
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

		System.out.println("**Executed Advanced search Regression2_21**");
	}

}
