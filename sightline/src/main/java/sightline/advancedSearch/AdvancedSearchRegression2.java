package sightline.advancedSearch;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
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
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.Categorization;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class AdvancedSearchRegression2 {

	Driver driver;
	LoginPage login;
	SavedSearch saveSearch;
	SessionSearch session;
	BaseClass base;
	SoftAssert softAssertion;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		Input in = new Input();
		in.loadEnvConfig();

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
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("Test case Id: RPMXCON-57259 Advanced Search");
		base.stepInfo(
				"Verify that Dropped tiles are retained in shopping cart when User Navigates Basic Search (Pure Hit) >> \"Analyze in Concept Explorer\" screen and Come back to Search Page.");

		// configure and performing search
		session.basicMetaDataSearch(Input.metaDataName, null, Input.metaDataCustodianNameInput, null);

		// adding pureHit to shopping cart and navigating to concept Explorer page
		session.addPurehitAndActionAsConceptOrComm(false, true);

		// navigating to basic search and verifying whether the title is retained in
		// shopping cart
		session.navigateToSessionSearchPageURL();
		softAssertion.assertEquals((boolean)session.getRemovedocsfromresult().isElementAvailable(5), true);
		softAssertion.assertAll();
		base.passedStep("Dropped title is retained in shopping cart when User navigates Basic Search");

		// logout
		login.logout();

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
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("Test case Id: RPMXCON-57262 Advanced Search");
		base.stepInfo(
				"Verify that Dropped tiles are retained in shopping cart when User Navigates Basic  Search (Pure Hit) >> \"Manage >> Categorize\" screen and Come back to Search Page.");

		// configure and performing search
		session.basicMetaDataSearch(Input.metaDataName, null, Input.metaDataCustodianNameInput, null);
		// adding pureHit to shopping cart
		session.addPureHit();

		// navigating to categories page
		categories.navigateToCategorizePage();

		// navigating to basic search and verifying whether the title is retained in
		// shopping cart
		session.navigateToSessionSearchPageURL();
		softAssertion.assertEquals((boolean)session.getRemovedocsfromresult().isElementAvailable(5), true);
		softAssertion.assertAll();
		base.passedStep("Dropped title is retained in shopping cart when User navigates Basic Search");

		// logout
		login.logout();
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
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("Test case Id: RPMXCON-57263 Advanced Search");
		base.stepInfo(
				"Verify that Dropped tiles are retained in shopping cart when User Navigates Basic Search (Pure Hit) >> \"Manage >>Users\" screen and Come back to Search Page.");

		// configure and performing search
		session.basicMetaDataSearch(Input.metaDataName, null, Input.metaDataCustodianNameInput, null);
		// adding pureHit to shopping cart
		session.addPureHit();
		base.stepInfo("performing MetaData search and adding pureHit to cart");

		// navigating to user Management page
		user.navigateToUsersPAge();

		// navigating to basic search and verifying whether the title is retained in
		// shopping cart
		session.navigateToSessionSearchPageURL();
		softAssertion.assertEquals((boolean)session.getRemovedocsfromresult().isElementAvailable(5), true);
		softAssertion.assertAll();
		base.passedStep("Dropped title is retained in shopping cart when User navigates Basic Search");

		// logout
		login.logout();

	}

	/**
	 * @Author
	 * @Description : Verify that Dropped tiles are retained in shopping cart when
	 *              User Navigates Advanced Search (Pure Hit) >> \"Analyze in
	 *              Communications Explorer\" screen and Come back to Search Page.
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57264",enabled = true, groups = { "regression" })
	public void verifyTilesRetainedInCartWhenUserNavigatesFromCommunicationsExplorerToAdvancedSearch()
			throws InterruptedException {

		// login as Users
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("Test case Id: RPMXCON-57264 Advanced Search");
		base.stepInfo(
				"Verify that Dropped tiles are retained in shopping cart when User Navigates Advanced Search (Pure Hit) >> \"Analyze in Communications Explorer\" screen and Come back to Search Page.");

		// configure and performing search
		session.advancedMetaDataSearch(Input.metaDataName, null, Input.metaDataCustodianNameInput, null);

		// navigating to communication Explorer page
		session.addPurehitAndActionAsConceptOrComm(true, false);

		// navigating back to advanced search
		session.navigateToSessionSearchPageURL();

		// verifying whether the title is retained in shopping cart
		softAssertion.assertEquals((boolean)session.getRemovedocsfromresult().isElementAvailable(5), true);
		softAssertion.assertAll();
		base.passedStep("Dropped title is retained in shopping cart when User navigates Advanced Search");

		// logout
		login.logout();
	}

	/**
	 * @Author :
	 * @Description : Verify that Dropped tiles are retained in shopping cart when
	 *              User Navigates Basic Search (Pure Hit) >> \"View in Doc View\"
	 *              screen and Come back to Search Page.
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57265",enabled = true, groups = { "regression" })
	public void verifyTilesRetainedInCartWhenUserNavigatesFromDocViewToBasicSearch() throws InterruptedException {

		// login as Users
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("Test case Id: RPMXCON-57265 Advanced Search");
		base.stepInfo(
				"Verify that Dropped tiles are retained in shopping cart when User Navigates Basic Search (Pure Hit) >> \"View in Doc View\" screen and Come back to Search Page.");

		// configure and performing search
		session.basicMetaDataSearch(Input.metaDataName, null, Input.metaDataCustodianNameInput, null);

		// adding the tile to shopping cart and View in doc view action is performed
		session.ViewInDocViews();
		base.stepInfo("Navigated to docView to view docs");

		// navigating to basic search and verifying whether the title is retained in
		// shopping cart
		base.waitTime(3);
		session.navigateToSessionSearchPageURL();
		softAssertion.assertEquals((boolean)session.getRemovedocsfromresult().isElementAvailable(5), true);
		softAssertion.assertAll();
		base.passedStep("Dropped title is retained in shopping cart when User navigates Basic Search");

		// logout
		login.logout();
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
	@Test(description = "RPMXCON-57291", dataProvider = "invaildSearchQueries",enabled = true, groups = { "regression" })
	public void verifyBellyBandMessageForSearchHavingRightDoubleQuoteOnly(String searchString)
			throws InterruptedException {

		// login as Users
		login.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("Test case Id: RPMXCON-57291 Advanced Search");
		base.stepInfo(
				"Verify that belly band message appears when user tries to run search having Right double quote only in Advanced Search Query Screen.");

		// navigate to advanced search Page
		session.navigateToAdvancedSearchPage();

		// configuring and searching
		session.advancedContentSearchConfigure(searchString);
		session.getQuerySearchButton().waitAndClick(5);

		// verifying the warning message
		session.verifyProximitySearch();

		// logout
		login.logout();
	}
	
	/**
	 * Author :Krishna date: 12/07/2022
	 * TestCase Id:RPMXCON-57083
	 * Description :Verify that User Navigates from Advanced Search result to Document View screen.
	 * 
	 */
	
	@Test(description ="RPMXCON-57083", dataProvider = "Users", groups = { "regression" })
	public void verifyNavigatesfrmAdvsearchsreentoDocViewScreen(String username, String password) throws InterruptedException {
		BaseClass baseClass = new BaseClass(driver);
		LoginPage loginPage = new LoginPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-57083");
		baseClass.stepInfo(
				"Verify that User Navigates from Advanced Search result to Document View screen.");
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
	 * Author :Krishna date: 12/07/2022
	 * TestCase Id:RPMXCON-57089
	 * Description :To Verify as an user login into the Application 
	 * User will be able to copy a search query into the new search box by clicking on 'Copy to new search' in advanced search page
	 * 
	 */
	
	@Test(description ="RPMXCON-57089",dataProvider = "Users", groups = { "regression" })
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
	 * Author :Krishna date: 12/07/2022
	 * TestCase Id:RPMXCON-57158 
	 * Description :Verify that Remove functionality working fine for 'Audio' block on 'Advanced Search' screen 
	 * 
	 */
	
	@Test(description ="RPMXCON-57078", dataProvider = "Users", groups = { "regression" })
	public void verifyRemoveFunctionallityAudioAdvSearch(String username, String password) throws InterruptedException {
		BaseClass baseClass = new BaseClass(driver);
		LoginPage loginPage = new LoginPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-57078");
		baseClass.stepInfo("Verify that Remove functionality working fine for 'Audio' block on 'Advanced Search' screen");
		
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged In As : " + username);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.navigateToAdvancedSearchPage();	
		
		baseClass.waitForElement(sessionSearch.getAs_Audio());
		sessionSearch.getAs_Audio().waitAndClick(3);
		
		if(sessionSearch.getAs_AudioLanguage().isElementAvailable(10)) {
			baseClass.passedStep("Audio Button Clicked & Audio Block Successfully Added");
		}else {
			baseClass.failedStep("Audio Block Not Added");
		}
		
		baseClass.waitForElement(sessionSearch.getRemoveLink());
		sessionSearch.getRemoveLink().waitAndClick(3);
		
		if(sessionSearch.getAs_AudioLanguage().isElementAvailable(10)== false) {
			baseClass.passedStep("Remove Functionality Worked Properly");
			baseClass.passedStep("Audio Block Successfully Removed");		
		}else {
			baseClass.failedStep("Remove Functionality Not Worked Properly");
		}
		
		baseClass.passedStep("Verified - that Remove functionality working fine for \"Audio\" block on \"Advanced Search\" screen");
		loginPage.logout();
	}
	
	/**
	 * Author :Krishna date: 12/07/2022  
	 * TestCase Id:RPMXCON-57242 
	 * Description :Verify that Conceptual tile return the result for Metatadata Search in Advanced Search 
	 * 
	 */
	
	@Test(description ="RPMXCON-57242", dataProvider = "Users", groups = { "regression" })
	public void verifyConceptTileRtnMetadataInAdvSrcScreen (String username, String password) throws InterruptedException {
		BaseClass baseClass = new BaseClass(driver);
		LoginPage loginPage = new LoginPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-57242");
		baseClass.stepInfo("Verify that Conceptual tile return the result for Metatadata Search in Advanced Search Screen.");
		
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged In As : " + username);
		sessionSearch.advancedMetaDataSearch(Input.docFileExt, null, ".xls", null );
		
		int pureHit =Integer.parseInt(sessionSearch.verifyPureHitsCount());
		SoftAssert asserts = new SoftAssert();
		asserts.assertNotNull(pureHit, "Verified that Search result return documents which satisfied above configured query");
		
		int conceptPureHit = sessionSearch.runAndVerifyConceptualSearch();
		
		if(sessionSearch.getConceptualTileHit(conceptPureHit).Visible()) {
			baseClass.passedStep("Verified that Conceptual tile return the result for Metadata Search in Advanced  Search Screen");
		}else {
			baseClass.failedStep("Still Loading on the Screen");
		}
		baseClass.passedStep("Verified - that Conceptual tile return the result for Metatadata Search in Advanced Search Screen");
		loginPage.logout();
	}
	
	/**
	 * Author :Krishna date: 12/07/2022  
	 * TestCase Id:RPMXCON-57343 
	 * Description :Verify that Advanced Proximity Search upload functionality is working proper in Saved searches 
	 * 
	 */
	
	@Test(description ="RPMXCON-57343", dataProvider = "Users", groups = { "regression" })
	public void verifyAdvProxUploadFunctinSS (String username, String password) throws Exception {
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
		baseClass.passedStep("Verified - that upload functionality is working proper and all saved Search appears  under Saved searches");

		driver.scrollPageToTop();
		driver.Navigate().refresh();
		savedSearch.deleteUplodedBatchFile(renameFile);
		
		// logOut
		loginPage.logout();
		
	}
	
	/**
	 * Author :Krishna date: 12/07/2022  
	 * TestCase Id:RPMXCON-48394 
	 * Description :Verify that Conceptually similar tile 
	 * does not display any document when search is run on Advanced Search Screen. 
	 * 
	 */
	
	@Test(description ="RPMXCON-48394", dataProvider = "Users"  , groups = { "regression" })
	public void verifyConceptTiledoesnotRtnDocInAdvSrcScreen (String username, String password) throws InterruptedException {
		String docFileType = "Spreadsheet";
		BaseClass baseClass = new BaseClass(driver);
		LoginPage loginPage = new LoginPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48394");
		baseClass.stepInfo("Verify that Conceptually similar tile does not display any document when search is run on Advanced Search Screen.");
		
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged In As : " + username);
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.advancedMetaDataSearch(Input.docFileType, null, docFileType, null );
		driver.waitForPageToBeReady();
		
		sessionSearch.verifyTileSpinning();
		int conceptTileHits = sessionSearch.runAndVerifyConceptualSearch();
		SoftAssert asserts = new SoftAssert();
		asserts.assertEquals(0, conceptTileHits, "Conceptually similar tile does not display any document");
		asserts.assertAll();
		baseClass.passedStep("Verified - that Conceptually similar tile does not display any document when search is run on Advanced Search Screen.");	
		loginPage.logout();
	}
	
	/**
	 * Author :Krishna date: 12/07/2022  
	 * TestCase Id:RPMXCON-48416 
	 * Description : Verify that Search status display Completed on My BackGround Page 
	 * 
	 */
	
	@Test(description ="RPMXCON-48416", dataProvider = "Users", groups = { "regression" })
	public void verifySearchStatusDisplayCompletedOnMyBGPage (String username, String password) throws InterruptedException {
		BaseClass baseClass = new BaseClass(driver);
		LoginPage loginPage = new LoginPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48416");
		baseClass.stepInfo("Verify that Search status display Completed on My BackGround Page");
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged In As : " + username);
		baseClass.selectproject(Input.highVolumeProject);
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.metadataSearchesUsingOperators(Input.metaDataName, Input.custodianName_Andrew, "OR", Input.metaDataName, Input.searchStringStar, true);
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
	 * Author :Krishna date: 12/07/2022  
	 * TestCase Id:RPMXCON-48403
	 * Description : Verify that 3 options appears on a bellyband 
	 * when search goes to BackGround on Advanced Search Result Screen"
	 * 
	 */
	
	@Test(description ="RPMXCON-48403", dataProvider = "Users",  groups = { "regression" })
	public void verifyOptionsInBellyBandWhenSrcGoesBG (String username, String password) throws InterruptedException {
		BaseClass baseClass = new BaseClass(driver);
		LoginPage loginPage = new LoginPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48403");
		baseClass.stepInfo("Verify that 3 options appears on a bellyband when search goes to BackGround on Advanced Search Result Screen");
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged In As : " + username);
		baseClass.selectproject(Input.highVolumeProject);
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.metadataSearchesUsingOperators(Input.metaDataName, Input.custodianName_Andrew, "OR", Input.metaDataName, Input.searchStringStar, true);
		sessionSearch.SearchBtnAction();
		
		sessionSearch.verifyTileSpinning();
		sessionSearch.verifyBellyBandOptions();
		baseClass.passedStep("Verified - that 3 options appears on a bellyband when search goes BackGround on Advanced Search Result Screen");
		loginPage.logout();
		}
	
	
	/**
	 * Author :Krishna date: 12/07/2022  
	 * TestCase Id:RPMXCON-48408
	 * Description : Verify that Search status display Executed in left hand side
	 * 
	 */
	@Test(description ="RPMXCON-48408", dataProvider = "Users", groups = { "regression" })
	public void verifySearchStatusDisplayExecutedinLHS (String username, String password) throws InterruptedException {
		BaseClass baseClass = new BaseClass(driver);
		LoginPage loginPage = new LoginPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48408");
		baseClass.stepInfo("Verify that Search status display Executed in left hand side");
		loginPage.loginToSightLine(username, password);	
		baseClass.stepInfo("Logged In As : " + username);
		baseClass.selectproject(Input.highVolumeProject);
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.metadataSearchesUsingOperators(Input.metaDataName, Input.custodianName_Andrew, "OR", Input.metaDataName, Input.searchStringStar, true);
		sessionSearch.SearchBtnAction();		
		sessionSearch.verifyTileSpinning();
		sessionSearch.verifyAllTilesResultsinAdvSrcScrn();
		baseClass.passedStep("All Tiles Are succesfully returned the Results..");
		sessionSearch.verifySessionSearchPage();
		
		if(sessionSearch.getExecutedStatusinLHS(1).isElementAvailable(10)) {
			String status = sessionSearch.getExecutedStatusinLHS(1).getText();
			baseClass.passedStep("Executed Status Present in Left Hand Side of Session Search Page" + status);
		
		}else {
			baseClass.failedStep("Executed Status Not Present in Left Hand Side of Session Search Page");
		}
		baseClass.passedStep("Verified - that Search status display Executed in left hand side ");
		loginPage.logout();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.logBefore(testMethod.getName());

		// Open browser
		driver = new Driver();
		base = new BaseClass(driver);
		login = new LoginPage(driver);
		softAssertion = new SoftAssert();
		session = new SessionSearch(driver);
		saveSearch = new SavedSearch(driver);
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		Reporter.setCurrentTestResult(result);
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			login.logoutWithoutAssert();
		}
		try {
			login.quitBrowser();
		} catch (Exception e) {
			login.quitBrowser();
		}
		System.out.println("Executed :" + result.getMethod().getMethodName());
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR Batch Redactions EXECUTED******");
		try {
//				login.clearBrowserCache();
		} catch (Exception e) {
			// no session avilable

		}
	}
}
