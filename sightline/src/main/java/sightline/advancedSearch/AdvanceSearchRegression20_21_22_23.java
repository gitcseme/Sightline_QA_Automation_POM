package sightline.advancedSearch;

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
import pageFactory.BaseClass;
import pageFactory.DataSets;
import pageFactory.DocListPage;
import pageFactory.DocViewRedactions;
import pageFactory.IngestionPage_Indium;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class AdvanceSearchRegression20_21_22_23 {
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
	public Object[][] CombinedSearchwithUsers() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password }, { Input.pa1userName, Input.pa1password },
				{ Input.rev1userName, Input.rev1password } };
		return users;
	}

	@DataProvider(name = "testDataWildcardAdvanceSearch")
	public Object[][] testDataWildcardAdvanceSearch() {
		return new Object[][] { { "\"discrepa* scripts\"" }, { "\"*screpancy in\"" }, { "\"discre*cy in\"" },
				{ "\"discrepancy i*\"" }, { "\"discrepancy *n\"" }, { "\"discr*ancy in\"" }, { "\"discrepan* i*\"" },
				{ "\"discrepan* script*\"" }, { "“discrepan* scripts”" }, { "“discrepan* scripts”" },
				{ "“discrepan* script*”" }, { "“discrepan* scripts”" }, { "“*screpancy scripts”~3" },
				{ "“discre*y scripts”~3" }, { "“discre*y scripts”~3" }, { "“*screpancy org*”" },
				{ "\"discrepa* org\"" }, { "“*screpancy and*”" }, { "“discre*y and”" }, { "“*screpancy not*”" },
				{ "“discre*y not*”" }, { "\"discrepa* not*\"" } };
	}
	
	@DataProvider(name = "invaildProximitySearchQueriesRightQuotesOnly")
	public Object[][] invaildProximitySearchQueriesRightQuotesOnly() {
		return new Object[][] { { "Precision", "ProximitySearch Truthful”~5", "AND" },
				{ "Precision", "ProximitySearch Truthful\"~5", "AND" },
				{ " “Truthful Balance”", "ProximitySearch Truthful ” ~5", "OR" },
				{ "\"Truthful Balance\"", "ProximitySearch Truthful \" ~5", "OR" } };
	}

	@DataProvider(name = "invaildProximitySearchQueriesLeftQuotesOnly")
	public Object[][][] invaildProximitySearchQueriesLeftQuotesOnly() {
		return new Object[][][] {
				{ new String[] { "Precision", "“ProximitySearch Truthful~5 " }, new String[] { "AND" } },
				{ new String[] { "Precision", "“ProximitySearch Truthful~5" }, new String[] { "AND" } },
				{ new String[] { "“Truthful Balance”", "Precision", "“ProximitySearch Truthful~5" },
						new String[] { "AND" } },
				{ new String[] { "Precision", "\"ProximitySearch Truthful~5" }, new String[] { "AND" } },
				{ new String[] { "\"Truthful Balance\"", "Precision", "\"ProximitySearch Truthful~5" },
						new String[] { "AND" } }, };
	}


	@DataProvider(name = "assignmentHelperWindoStatus")
	public Object[][] assignmentHelperWindoStatus() {
		Object[][] users = { { "Completed" }, { "Assigned" } };
		return users;
	}

	/**
	 * @author
	 * @Date: 25/8/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that all session searches are purged at the time of
	 *              logout on Advanced Search Result screen.RPMXCON-48368
	 */
	@Test(description = "RPMXCON-48368", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void verifyAllSesssionSearchesPurgedAtTimeOfLogoutOnAdvancedSearchResultScreen(String username,
			String password) throws Exception {

		SoftAssert assertion = new SoftAssert();
		String searchString = "Query; \"(all team) (thanks regards reply)\"~1000";
		int defaultSearchCount = 1;

		// login as Users
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-48368 Advanced Search");
		baseClass.stepInfo(
				"Verify that all session searches are purged at the time of logout on Advanced Search Result screen.");

		// configuring and performing the search
		baseClass.stepInfo("Performing Search using the SearchString '*'");
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.advancedSearchContentMetadata(Input.searchStringStar);
		sessionSearch.getQuerySearchBtn().waitAndClick(10);
		// clicking the 'When All Results are Ready' button and handling the generated
		// backGround ID
		sessionSearch.handleWhenAllResultsBtnInUncertainPopup();

		// configuring and performing the search
		baseClass.stepInfo(
				"Performing Search using the SearchString 'Query; \\\"(all team) (thanks regards reply)\\\"~1000'");
		sessionSearch.getNewSearchButton().waitAndClick(5);
		sessionSearch.advancedSearchContentMetadata(searchString);
		sessionSearch.getQuerySearchBtn().waitAndClick(10);
		sessionSearch.tallyContinue(5);
		sessionSearch.handleWhenAllResultsBtnInUncertainPopup();

		// logOut
		loginPage.logout();

		// login as Users
		loginPage.loginToSightLine(username, password);

		// navigating to advanced search page and verifying that all session searches
		// are purged at the time of logout on Advanced search Result Screen.
		sessionSearch.navigateToAdvancedSearchPage();
		int countOfSearchInPanel = sessionSearch.getSearchPanelCount().size();
		assertion.assertEquals(countOfSearchInPanel, defaultSearchCount);
		assertion.assertEquals((boolean)sessionSearch.getPureHitsCountNumText().isDisplayed(), false);
		assertion.assertAll();
		baseClass.passedStep(
				"verified that all session searches are purged at the time of logout on Advanced search Result  Screen.");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Date: 13/8/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that - Application returns consistent search result
	 *              when user resubmits a saved search(WorkProduct Block - Folder,
	 *              Content & Metadata Block, Audio Block and Conceptual Block with
	 *              Family Member Check Box ) multiple times(twice) RPMXCON-57199
	 */
	@Test(description = "RPMXCON-57199", enabled = true, groups = { "regression" })
	public void verifyApplicationReturnsConsistentSearchResultWhenUserResubmitsSavedSearch()
			throws InterruptedException {

		String folderNmae = "folderName" + Utility.dynamicNameAppender();
		String searchName = "savedSearch" + Utility.dynamicNameAppender();
		TagsAndFoldersPage tagPage = new TagsAndFoldersPage(driver);

		// login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		baseClass.stepInfo("Test case Id: RPMXCON-57199");
		baseClass.stepInfo(
				"Verify that - Application returns consistent search result when user resubmits a saved search(WorkProduct Block - Folder, Content & Metadata Block, Audio Block and Conceptual Block with Family Member Check Box ) multiple times(twice)");

		// creating folder
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkFolder(folderNmae);

		// configuring folder work product search
		baseClass.selectproject();
		sessionSearch.navigateToAdvancedSearchPage();
		baseClass.stepInfo("Configuring WorkProduct search Block Tag.");
		sessionSearch.workProductSearch("folder", folderNmae, true);

		// configuring contentMetadata search
		baseClass.stepInfo("Configuring ContentMetadata search.");
		sessionSearch.advancedSearchContentMetadata(Input.searchString1);
		sessionSearch.selectOperatorBetweenAdvancedSearchBlocks().selectFromDropdown().selectByVisibleText("OR");

		// configuring audioSearch
		baseClass.stepInfo("Configuring Audio search.");
		sessionSearch.audioSearch_Combined();
		sessionSearch.selectOperatorBetweenAdvancedSearchBlocks().selectFromDropdown().selectByVisibleText("OR");

		// conceptual search
		baseClass.stepInfo("Configuring Conceptual search.");
		sessionSearch.advancedSearchConceptual(Input.conceptualSearchString1);
		sessionSearch.selectOperatorBetweenAdvancedSearchBlocks().selectFromDropdown().selectByVisibleText("OR");

		// selecting family member documents checkBox
		sessionSearch.getadvoption_family().ScrollTo();
		sessionSearch.getadvoption_family().waitAndClick(5);
		baseClass.stepInfo("selecting the family member documents checkBox.");

		// perform search
		sessionSearch.SearchBtnAction();
		baseClass.stepInfo("Performing search Action");

		// saving the search
		sessionSearch.saveAdvancedSearchQuery(searchName);

		// performing searchResult work Product and getting the pureHit
		baseClass.selectproject();
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.advanceWorkProductSearchResult(searchName);
		int pureHitCount = sessionSearch.serarchWP();
		baseClass.stepInfo("PureHit Before re-Submitting  : " + pureHitCount);

		// resubmitting the search
		sessionSearch.resubmitSearch();
		baseClass.stepInfo("resubmitting the Search.");

		// getting the pureHit Count
		int resubmitPureHitCount = sessionSearch.returnPurehitCount();
		baseClass.stepInfo("PureHit After re-Submitting  : " + resubmitPureHitCount);

		// comparing the pureHit Count after resubmit with pureHit Count before resubmit
		baseClass.digitCompareEquals(pureHitCount, resubmitPureHitCount,
				"Hence Verified that Application returns consistent search result when user resubmits a saved search multiple times(twice)",
				"pureHit Count after resubmit doesn't match with the pureHit count before resubmit");

		// deleting the folder
		tagPage.DeleteFolderWithSecurityGroup(folderNmae, "All Groups");

		// deleting the saved search
		savedSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Date: 13/8/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that configured query with Document File Size and range
	 *              operator get inserted properly into Advanced Search Query
	 *              builder screen. RPMXCON-57053
	 */
	@Test(description = "RPMXCON-57053", enabled = true, dataProvider = "Users", groups = { "regression" })
	public void verifyConfigureQueryDocFileSizeAndRangeOperatorInsertedProperlyInAdvSearch(String userName,
			String password) {

		String fromFileSize = "7892";
		String toFileSize = "9990";

		// login as PA
		baseClass.stepInfo("**Step-1 Login as User**");
		loginPage.loginToSightLine(userName, password);

		baseClass.stepInfo("Test case Id: RPMXCON-57053");
		baseClass.stepInfo(
				"Verify that configured query with Document File Size  and range operator get inserted properly into Advanced Search Query builder screen");

		// Configuring MetaData Search Query
		baseClass.stepInfo("Step-2 & 3 & 4 Configuring MetaData Search Query.");
		baseClass.stepInfo(
				"Step-5 & 6 & 7 & 8 Verify that configured query with Document File Size  and range operator get inserted properly into Advanced Search Query builder screen");
		sessionSearch.advancedMetaDataForDraft(Input.ingDocFileSize, "RANGE", fromFileSize, toFileSize);

		// getting the actual configured search query in Advanced Search Query builder
		// screen
		String actualConfiguredQuery = sessionSearch.getSavedSearchQueryAS().getText();

		// Verify that configured query with Document File Size and range operator get
		// inserted properly into Advanced Search Query builder screen
		baseClass.stepInfo(
				"Step-9 Verify that configured query with Document File Size  and range operator get inserted properly into Advanced Search Query builder screen");
		baseClass.compareTextViaContains(
				actualConfiguredQuery, Input.ingDocFileSize, "MetaData '" + Input.ingDocFileSize
						+ "' is present in the configure Search Query '" + actualConfiguredQuery + "'.",
				"expected metaData is not present in configure search query");
		baseClass.compareTextViaContains(actualConfiguredQuery, fromFileSize,
				"From File Size value '" + fromFileSize + "' is present in the configure Search Query '"
						+ actualConfiguredQuery + "'.",
				"expected From File Size value is not present in configure search query");
		baseClass.compareTextViaContains(actualConfiguredQuery, toFileSize,
				"To File Size value '" + toFileSize + "' is present in the configure Search Query '"
						+ actualConfiguredQuery + "'.",
				"expected To File Size value is not present in configure search query");
		baseClass.passedStep(
				"Verified that configured query with Document File Size  and range operator get inserted properly into Advanced Search Query builder screen");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Date: 15/9/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :To verify as an user login into the Application, I will be able
	 *              to select All Search group as an search criteria & I am able to
	 *              search a query based on that.RPMXCON-47693
	 */
	@Test(description = "RPMXCON-47693", dataProvider = "Users", groups = { "regression" }, enabled = true)
	public void verifyUserLoginApplicationAbleToSelectAllSearchGroupAsSearchCriteriaAndAbleToSearchQueryBasedOnThat(
			String username, String password) throws InterruptedException {

		String searchName = "savedSearch" + Utility.dynamicNameAppender();
		baseClass.stepInfo("Test case Id: RPMXCON-47693 Advanced Search");
		baseClass.stepInfo(
				"To verify as an user login into the Application, I will be able to select All Search group as an search criteria & I am able to search a query based on that.");

		// login as Users
		baseClass.stepInfo("**Step-1 Login as User**");
		loginPage.loginToSightLine(username, password);

		// creating new Search Group under My Saved Search
		String searchGroup = savedSearch.createNewSearchGrp(Input.mySavedSearch);

		// performing searching and saving it in newly created node
		sessionSearch.advancedContentSearch(Input.searchString1);
		sessionSearch.saveAdvanceSearchInNode(searchName, searchGroup);

		// Select All search group from work product tab
		baseClass.stepInfo(
				"**Step-2 & 3 & 4 Go to advanced search page | Select All search group from work product tab and Enter some search query in Content & Metadata tab**");
		baseClass.selectproject();
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.getWorkproductBtn().waitAndClick(10);
		sessionSearch.searchSavedSearch(Input.mySavedSearch);
		baseClass.stepInfo("Selecting All search group from work product tab.");

		// configuring Content & Metadata search
		sessionSearch.advancedContentSearchConfigure(Input.searchString1);
		baseClass.stepInfo("Configuring Content & Metadata Search.");

		// perform search
		sessionSearch.searchAndReturnPureHit_BS();
		baseClass.passedStep(
				"Verified that We Will get some search result based on search criteria applied on work product.");

		// delete savedSearch node
		baseClass.stepInfo("Initiating delete node");
		savedSearch.deleteNode(Input.mySavedSearch, searchGroup);

		// logout
		loginPage.logout();
	}



	/**
	 * @author
	 * @Date: 29/9/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :Verify that Advanced Search works properly - for MasterDate
	 *              time metadata - Provide only dates (not times) with \"Is\"
	 *              operator.RPMXCON-57236
	 */

	@Test(description = "RPMXCON-57236", enabled = true, groups = { "regression" })
	public void verifyAdvancedSearchWorkProperlyForMasterDateTimeMetadataWithOnlyDateAndIsOperator() throws Exception {

		String masterDate = "2009-09-20";
		String ExpectedDate = "2009/09/20";
		List<String> masterDateValues = new ArrayList<String>();
		DocListPage docList = new DocListPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-57236 Advanced Search");
		baseClass.stepInfo(
				"Verify that Advanced  Search works properly - for MasterDate time metadata - Provide only dates (not times) with \"Is\" operator");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// configure and perform MasterDate metadata search query in Advanced Search
		// page
		baseClass.stepInfo("configure and perform MasterDate metadata search query in Advanced Search page");
		sessionSearch.advancedMetaDataSearch(Input.masterDateText, Input.is, masterDate, null);

		// verify search results should be inclusive of masterDate We passed as Input
		String searchResult = sessionSearch.contentAndMetaDataResult().getText();
		baseClass.compareTextViaContains(searchResult, masterDate,
				"Search Result '" + searchResult + "' contains the Date '" + masterDate + "'We passed as Input",
				"Search Result '" + searchResult + "' doesn't contains the Date '" + masterDate
						+ "'We passed as Input");
		sessionSearch.ViewInDocList();

		// Verify That MasterDate date/time field search result return documents which
		// satisfied above configured query. and search results should be inclusive of
		// both From and To dates.
		docList.checkMaseterDateAsExpected(masterDateValues, ExpectedDate, ExpectedDate, "Between", "yyyy/MM/dd");
		baseClass.passedStep(
				"Verified That MasterDate date/time field search result return documents which satisfied above configured query. and search results should be inclusive of both From and To dates.");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Date: 29/9/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :Verify that Advanced Search works properly for MasterDate
	 *              date/time field with \"Range\" operator.RPMXCON-57225
	 */

	@Test(description = "RPMXCON-57225", enabled = true, groups = { "regression" })
	public void verifyAdvancedSearchWorkProperlyForMasterDateDateAndTimeFieldWithRangeOperator()
			throws ParseException, InterruptedException {
		DocListPage docList = new DocListPage(driver);
		List<String> masterDateValues = new ArrayList<String>();
		String fromDate = "2009-09-20 06:30:12";
		String toDate = "2009-09-21 06:30:12";
		String expectedFromDate = "2009/09/20";
		String expectedToDate = "2009/09/21";
		baseClass.stepInfo("Test case Id: RPMXCON-57225 Advanced Search");
		baseClass.stepInfo(
				"Verify that Advanced Search works properly for MasterDate date/time field  with \"Range\" operator");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// configure and perform metadata MasterDate search query in Advanced Search
		// page
		baseClass.stepInfo("configure and perform MasterDate metadata search query in Advanced Search page");
		sessionSearch.advancedMetaDataSearch(Input.masterDateText, Input.is_Or_Range, fromDate, toDate);
		sessionSearch.ViewInDocList();

		// Verify that MasterDate date/time field search result return documents which
		// satisfied above configured query.
		docList.checkMaseterDateAsExpected(masterDateValues, expectedFromDate, expectedToDate, "Between", "yyyy/MM/dd");
		baseClass.passedStep(
				"Verify that \"MasterDate date/time\" field search result return documents which satisfied above configured query.");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :Verify that Search result when user tries to search phrase(in
	 *              double quote) which contains wildcard * in Advanced Search Query
	 *              Screen..RPMXCON-57270
	 */
	@Test(description = "RPMXCON-57270", dataProvider = "testDataWildcardAdvanceSearch", enabled = true, groups = {
			"regression" })
	public void verifySearchResultContainsWildcardInAdvancedSearch(String searchString) {

		baseClass.stepInfo("Test case Id: RPMXCON-57270 Advanced Search");
		baseClass.stepInfo(
				"Verify that Search result when user tries to search phrase(in double quote) which contains wildcard * in Advanced Search Query Screen.");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// performing Content Search in Advanced Search Page
		baseClass.stepInfo("performing Content Search in Advanced Search Page.");
		sessionSearch.advancedContentSearch(searchString);

		// logOut
		loginPage.logout();

	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :Verify that belly band message appears when user configured
	 *              Proximity without (proper) double quotes and combined with other
	 *              criteria in Advanced Search Query Screen.RPMXCON-57299
	 */
	@Test(description = "RPMXCON-57299", enabled = true, groups = { "regression" })
	public void verifyProximityWithoutDoubleQuotesAndCombinedWithOtherCriteria() {
		String search = "Precision AND (ProximitySearch Truthful~5)";
		
		baseClass.stepInfo("Test case Id: RPMXCON-57299 Advanced Search");
		baseClass.stepInfo(
				"Verify that belly band message appears when user configured Proximity without (proper) double quotes and combined with other criteria in Advanced Search Query Screen.");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// configuring advanced Content Search
		sessionSearch.advanedContentDraftSearch(search);

		// verifying whether the application displays warning message and Verify pop up
		// title
		sessionSearch.verifyWarningMessage(true, true, 4);
		baseClass.passedStep(
				" verified that the application displayed Expected warning message and Possible Wrong Query Alert pop up title");

		// logOut
		loginPage.logout();
	}

	@DataProvider(name = "testDataProximityContainsWildCardAdvanceSearch")
	public Object[][] testDataProximityContainsWildCardAdvanceSearch() {
		return new Object[][] { { "\"Proximity* (\"Trut* Re*\"~4)\"~7" }, { "\"Proximity* (\"Trut? Re*\"~4)\"~7" } };
	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that result appears for proximity which contains wild
	 *              card * in Proximity Search Team in Advanced Search Query
	 *              Screen.RPMXCON-57330
	 */

	@Test(description = "RPMXCON-57330", dataProvider = "testDataProximityContainsWildCardAdvanceSearch", enabled = true, groups = {
			"regression" })
	public void verifyProximityContainWildCardInProximitySearchTerm(String searchString) {

		baseClass.stepInfo("Test case Id: RPMXCON-57330 Advanced Search");
		baseClass.stepInfo(
				"Verify that result appears for proximity which contains wild card * in Proximity Search Team in Advanced Search Query Screen.");

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// configuring Content search in advanced search page
		sessionSearch.advanedContentDraftSearch(searchString);

		// verify that application displays Proximity warning message
		sessionSearch.verifyWarningMessage(true, true, 5);
		baseClass.passedStep("Verified that application displays Expected Proximity warning message.");
		sessionSearch.tallyContinue(10);

		// Verify that result appears for proximity which contains Proximity Search Team
		// in Advance Search Query Screen.
		sessionSearch.returnPurehitCount();
		String searchResult = sessionSearch.contentAndMetaDataResult().getText();
		baseClass.textCompareEquals(searchString, searchResult,
				"Verified that result appears for proximity which contains Proximity Search Team in Advance Search Query Screen.",
				"fail");

		// logOut
		loginPage.logout();
	}
	
	
	/**
	 * Author :Arunkumar date: 11/10/2022 TestCase Id:RPMXCON-49772
	 * Description :Advanced Search- Verify that when documents are released 
	 * all email concatenated field values should be displayed 
	 * @throws Exception 
	 */
	@Test(description ="RPMXCON-49772",enabled = true, groups = { "regression" })
	public void verifyConcatenatedValuesInDoclist() throws Exception {
		
		baseClass.stepInfo("Test case Id: RPMXCON-49772");
		baseClass.stepInfo("verify email concatenated field values should be displayed");
		IngestionPage_Indium ingestionPage = new IngestionPage_Indium(driver);
		securityGroupsPage = new SecurityGroupsPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		
		String[] values = {"EmailAuthorNameAndAddress","EmailBCCNamesAndAddresses",
				"EmailCCNamesAndAddresses","EmailToNamesAndAddresses"};
		String[] userName= {Input.rmu1userName,Input.rev1userName};
		String[] password= {Input.rmu1password,Input.rev1password};
		String securityGroup ="securityGroup"+Utility.dynamicNameAppender();
		
		//pre-requisite - Docs related to ingestion released to security group
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		securityGroupsPage.createSecurityGroups(securityGroup);
		docViewRedact.assignAccesstoSGs(securityGroup, Input.rmu1userName);
		docViewRedact.assignAccesstoSGs(securityGroup, Input.rev1userName);
		baseClass.stepInfo("Release docs to security group");
		ingestionPage.navigateToDataSetsPage();
		DataSets dataset = new DataSets(driver);
		String ingestionName = dataset.isDataSetisAvailable(Input.EmailConcatenatedDataFolder);
		sessionSearch.MetaDataSearchInBasicSearch(Input.metadataIngestion,ingestionName);
		sessionSearch.bulkRelease(securityGroup);
		loginPage.logout();
		// Login as RMU/Rev and verify
		for(int i=0;i<userName.length;i++) {
		loginPage.loginToSightLine(userName[i], password[i]);
		baseClass.stepInfo("Logged in as :"+userName[i]);
		baseClass.selectsecuritygroup(securityGroup);
		baseClass.stepInfo("Search for all docs and action as view in doclist");
		int count = sessionSearch.advancedContentSearch(Input.searchStringStar);
		baseClass.stepInfo("total docs in EmailConcatenatedDataFolder: "+count);
		sessionSearch.ViewInDocList();
		baseClass.stepInfo("Add the columns and verify email concatenated field values displayed");
		ingestionPage.verifyValuesDisplayedInSelectedColumnsDoclist(count,values);
		baseClass.passedStep("Email concatenated field values displayed");
		baseClass.selectsecuritygroup(Input.securityGroup);
		loginPage.logout();
		}
		//Delete security group
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		if (!(securityGroup.equalsIgnoreCase(Input.securityGroup))) {
		securityGroupsPage.deleteSecurityGroups(securityGroup);
		}
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

		System.out.println("**Executed Advanced search Regression2_21**");
	}

}
