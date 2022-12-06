package testScriptsRegressionSprint27;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
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
import pageFactory.DocListPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class AdvancedSearchRegression_27 {
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

	@DataProvider(name = "invaildProximitySearchQueriesRightQuotesOnly")
	public Object[][] invaildProximitySearchQueriesRightQuotesOnly() {
		return new Object[][] { { "Precision AND “ProximitySearch Truthful~5" },
				{ "“Truthful Balance” AND Precision AND “ProximitySearch Truthful~5" },
				{ "\"Truthful Balance\" AND Precision AND \"ProximitySearch Truthful~5" },
				{ "“Truthful Balance” AND Precision AND “ProximitySearch Truthful ~5" } };
	}

	@DataProvider(name = "invaildProximitySearchQueriesLeftQuotesOnly")
	public Object[][] invaildProximitySearchQueriesLeftQuotesOnly() {
		return new Object[][] { { "Precision AND ProximitySearch Truthful”~5" },
				{ "“Truthful Balance” OR ProximitySearch Truthful ” ~5" },
				{ "“Truthful Balance” OR ProximitySearch Truthful” ~5" },
				{ "\"Truthful Balance\" OR ProximitySearch Truthful \" ~5" },
				{ "\"Truthful Balance\" OR ProximitySearch Truthful\" ~5" },
				{ "“Truthful Balance” OR ProximitySearch Truthful ” ~5" } };
	}

	/**
	 * @author
	 * @Date: 13/8/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that belly band message appears when user configured
	 *              Proximity with Left double quotes only and combined with other
	 *              criteria in Advanced Search Query Screen. RPMXCON-57300
	 */
	@Test(description = "RPMXCON-57300", enabled = true, dataProvider = "invaildProximitySearchQueriesLeftQuotesOnly", groups = {
			"regression" })
	public void verifyBellyBandMessageForSearchQueriesWithOperatorHavingLeftDoubleQuoteOnly(String searches)
			throws InterruptedException {

		// login as Users
		baseClass.stepInfo("**Step-1 Login as User**");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		baseClass.stepInfo("Test case Id: RPMXCON-57300 Advanced Search");
		baseClass.stepInfo(
				"Verify that belly band message appears when user configured Proximity with Left double quotes only and combined with other criteria in Advanced Search Query Screen.");

		// navigate to advanced search Page
		sessionSearch.navigateToAdvancedSearchPage();
		baseClass.stepInfo("Navigating to Advanced Search page");

		// configuring and searching
		baseClass.stepInfo("**Step-2 & 3 Configure query like @TestData and Click on \"Search\" button *");
		baseClass.stepInfo("Input String : " + searches);
		sessionSearch.advanedContentDraftSearch(searches);
		sessionSearch.getQuerySearchButton().waitAndClick(5);
		baseClass.stepInfo("Configuring and performing search");

		// verifying the warning message
		baseClass.stepInfo(
				"**Step-4 & 5 Observe that application displays warning message and Verify that pop up title*");
		sessionSearch.verifyProximitySearch();

		// logout
		loginPage.logout();

	}

	/**
	 * @author
	 * @Date: 13/8/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that belly band message appears when user configured
	 *              Proximity with Right double quotes only and combined with other
	 *              criteria in Advanced Search Query Screen. RPMXCON-57301
	 */
	@Test(description = "RPMXCON-57301", enabled = true, dataProvider = "invaildProximitySearchQueriesRightQuotesOnly", groups = {
			"regression" })
	public void verifyBellyBandMessageForSearchQueriesWithOperatorHavingRightDoubleQuoteOnly(String searchString1)
			throws InterruptedException {

		// login as Users
		baseClass.stepInfo("**Step-1 Login as User**");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		baseClass.stepInfo("Test case Id: RPMXCON-57301 Advanced Search");
		baseClass.stepInfo(
				"Verify that belly band message appears when user configured Proximity with Right double quotes only and combined with other criteria in Advanced Search Query Screen.");

		// navigate to advanced search Page
		sessionSearch.navigateToAdvancedSearchPage();
		baseClass.stepInfo("Navigating to Advanced Search page");

		// configuring and searching
		baseClass.stepInfo("**Step-2 & 3 Configure query like @TestData and Click on \"Search\" button *");
		baseClass.stepInfo("Input String : " + searchString1);
		sessionSearch.advanedContentDraftSearch(searchString1);
		sessionSearch.getQuerySearchButton().waitAndClick(5);
		baseClass.stepInfo("Configuring and performing search");

		// verifying the warning message
		baseClass.stepInfo(
				"**Step-4 & 5 Observe that application displays warning message and Verify that pop up title*");
		sessionSearch.verifyProximitySearch();

		// logout
		loginPage.logout();
	}

	/**
	 * @author Brundha.T Test Case id:RPMXCON-48939
	 * @Description :Verify Work Product selection is working for Edge and Chrome
	 *              browsers
	 */

	@Test(description = "RPMXCON-48939", enabled = true, groups = { "regression" })
	public void verifySearchQueryInWorkProduct() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48939 Advanced Search");
		baseClass.stepInfo("Verify Work Product selection is working for Edge and Chrome browsers");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in As:" + Input.pa1userName);
		String TagName = "Tag" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tf = new TagsAndFoldersPage(driver);
		tf.createNewTagwithClassification(TagName, "Select Tag Classification");

		SessionSearch search = new SessionSearch(driver);
		search.basicContentSearch(Input.testData1);
		search.bulkTagExisting(TagName);
		loginPage.logout();

		String[] UserName = { Input.pa1userName, Input.rmu1userName, Input.rev1userName };
		String[] Password = { Input.pa1password, Input.rmu1password, Input.rev1password };
		for (int i = 0; i < UserName.length; i++) {
			loginPage.loginToSightLine(UserName[i], Password[i]);
			baseClass.stepInfo("Logged in As:" + UserName[i]);

			baseClass.stepInfo("Switching to work product");
			search.switchToWorkproduct();
			search.selectTagInASwp(TagName);
			search.serarchWP();

			baseClass.stepInfo("verifying Selected Search query is displayed");
			baseClass.ValidateElement_Presence(search.getSelectQueryText(TagName), "Search Query");

			loginPage.logout();
		}

	}

	/**
	 * @author
	 * @throws ParseException
	 * @Description : Verify that Advanced Search works properly for EmailSentDate
	 *              field with "Is" operator and NOT having time components.
	 *              RPMXCON-49173
	 */

	@Test(description = "RPMXCON-49173", enabled = true, groups = { "regression" })
	public void verifiedAdancedSearchWorkProperlyForEmailSentDateWithISOperatorAndNotHavingTimeComponents()
			throws ParseException {

		String metaDataField = "EmailSentDate";
		String operator = "IS";
		String inputData = "2001-11-21";
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

		baseClass.stepInfo("Test case Id: RPMXCON-49173 Advanced Search.");
		baseClass.stepInfo(
				"Verify that Advanced Search works properly for EmailSentDate field with \"Is\" operator and NOT having time components.");

		// login
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		// configuring EmailSentDate Search Query with metaData as MasterDate and
		// Operator as 'IS'.
		baseClass.stepInfo("configuring EmailSentDate Search Query with metaData as MasterDate and Operator as 'IS'.");
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.advancedMetaDataForDraft(metaDataField, operator, inputData, null);

		// configured Query
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sessionSearch.getModifiableSavedSearchQueryAS());
		String configuredQuery = sessionSearch.getModifiableSavedSearchQueryAS().getText();
		baseClass.stepInfo("Configure Query in \"Search Edit box\" : " + configuredQuery);

		// Click on Search and Verify that "EmailSentDate" field search result return
		// documents which satisfied above configured query.
		baseClass.stepInfo("Click on 'Search' button");
		sessionSearch.serarchWP();

		// verify search result return documents which satisfied above configured query.
		driver.waitForPageToBeReady();
		sessionSearch.getPureHitsCount().waitAndClick(10);
		baseClass.waitForElement(sessionSearch.getMasterDate());
		String masterDate = sessionSearch.getMasterDate().getText();

		driver.waitForPageToBeReady();
		baseClass.compareTextViaContains(masterDate.replace("/", "-"), inputData,
				"result returned documents which satisfied above configured query.", "Result is not as expected");
		try {
			dateFormat.parse(masterDate.trim());
			baseClass.passedStep(masterDate + " : Match The Expected Format");
		} catch (ParseException e) {
			baseClass.failedStep(masterDate + " : Didnot Match The Expected Format");

		}

		baseClass.passedStep(
				"Verified that \"EmailSentDate\" field search result return documents which satisfied above configured query.");

		// logOut
		loginPage.logout();
	}

	@DataProvider(name = "proximityQueryHavingWildCard")
	public Object[][] proximityQueryHavingWildCard() {
		return new Object[][] { { "\"fin* (\"develo* requir*\"~4)\"~6" }, { "\"financia? (\"develo* requir*\"~4)\"~6" },
				{ "“fin* (“develo* require*”~4)”~6" } };
	}

	/**
	 * @author
	 * @Description : Verify that result appears for query when User configured
	 *              proximity within proximity query having wild card in Advanced
	 *              Search Query Screen.RPMXCON-57340
	 */

	@Test(description = "RPMXCON-57340", dataProvider = "proximityQueryHavingWildCard", enabled = true, groups = {
			"regression" })
	public void verifyResultAppearsForQueryUserConfiguredProximityWithinProximityQueryHavingWildCardInAdvanceSearch(
			String searchString) {

		String exampleSearchString = "\"fin* (\"develo* requir*\"~4)\"~6";

		baseClass.stepInfo("Test case Id: RPMXCON-57340 Advanced Search.");
		baseClass.stepInfo(
				"Verify that result appears for query when User configured proximity within proximity query having wild card in  Advanced Search Query Screen.");

		// login
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// configure search Query
		sessionSearch.advanedContentDraftSearch(searchString);
		baseClass.stepInfo("Search Query configured.");

		// Click on "Search" button
		baseClass.stepInfo("Clicking on 'Search' button.");
		sessionSearch.SearchBtnAction();

		// verify that application displays Proximity warning message
		sessionSearch.verifyWarningMessage(false, true, 5);
		baseClass.passedStep("verified that application displays Proximity warning message.");

		// Click on "Yes" button
		sessionSearch.tallyContinue(5);
		int searchStringPureHit = sessionSearch.returnPurehitCount();

		// performing search for given example search query.
		baseClass.stepInfo("performing search for given example search query.");
		sessionSearch.advancedNewContentSearchNotPureHit(exampleSearchString);
		sessionSearch.tallyContinue(5);
		int exampleSearchStringPureHit = sessionSearch.returnPurehitCount();

		// Verify that Result should appear for proximity within proximity having wild
		// card in Advanced Search Query Screen. example "fin* ("develo* requir*"~4)"~6
		// fin* within 6 words of instances where develo* is within 4 words of requir*
		assertion.assertEquals(searchStringPureHit, exampleSearchStringPureHit);
		assertion.assertAll();
		baseClass.passedStep(
				"verified that Result appear for proximity within proximity  having wild card in Advanced Search Query Screen. example \"fin* (\"develo* requir*\"~4)\"~6  fin* within 6 words of instances where develo* is within 4 words of requir*");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author:
	 * @Date: :N/A
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :To verify an an user login, I will be able to select multiple
	 *              Folder from Folder column under Work Product tab & set that as a
	 *              search criteria for advanced search.RPMXCON-57042
	 */

	@Test(description = "RPMXCON-57042", enabled = true, groups = { "regression" })
	public void verifySelectFolderUnderWpSearchCriteriaAdvancedSearch() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-57042 Advanced Search");
		baseClass.stepInfo(
				"To verify an an PA user login, I will be able to select all the Security Groups from Security Group column under Work Product tab & set that as a search criteria for advanced search");
		TagsAndFoldersPage tagsandfolder = new TagsAndFoldersPage(driver);
		String Foldername = "AANew" + UtilityLog.dynamicNameAppender();
		String Foldername1 = "AANew" + UtilityLog.dynamicNameAppender();

		// login as Rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");
		tagsandfolder.navigateToTagsAndFolderPage();
		tagsandfolder.CreateFolderInRMU(Foldername);
		tagsandfolder.CreateFolderInRMU(Foldername1);
		sessionSearch.switchToWorkproduct();
		sessionSearch.selectFolderInASwp(Foldername);
		sessionSearch.selectFolderInASwp(Foldername1);
		driver.waitForPageToBeReady();
		if (baseClass.text(Foldername).Displayed() && baseClass.text(Foldername1).Displayed()) {
			baseClass.passedStep(Foldername + Foldername1
					+ "   Selected Folders has been  inserted in search criteria for advanced search  as expected");
		} else {
			baseClass.failedStep("Selected Folders did not inserted in search criteria for advanced search ");
		}
		baseClass.waitForElement(sessionSearch.getWP_FolderBtn());
		sessionSearch.getWP_FolderBtn().waitAndClick(10);
		baseClass.waitTime(5);
		baseClass.waitForElement(sessionSearch.getAllFoldersTabInwp());
		sessionSearch.getAllFoldersTabInwp().waitAndClick(10);
		baseClass.waitTime(2);
		List<String> AllFolders = baseClass.availableListofElements(savedSearch.getcurrentClickedNode());
		baseClass.stepInfo(AllFolders + "  Present in workproduct SG list");
		System.out.println(AllFolders);
		baseClass.waitForElement(sessionSearch.getMetaDataInserQuery());
		sessionSearch.getMetaDataInserQuery().waitAndClick(10);
		driver.scrollPageToTop();
		baseClass.waitTime(5);
		String Searchboxcriteria = sessionSearch.getEnterSearchBox().getText();
		System.out.println(Searchboxcriteria);
		baseClass.stepInfo(Searchboxcriteria + "  Present in workproduct search list");

		for (int i = 0; i < AllFolders.size(); i++) {
			if (Searchboxcriteria.contains(AllFolders.get(i))) {
				baseClass.passedStep(AllFolders.get(i)
						+ "Selected All folders has been  inserted in search criteria for advanced search  as expected");
			} else {
				baseClass.failedStep(
						"Selected  All folders is did not inserted in search criteria for advanced search ");
			}
		}

		loginPage.logout();

		// login as Rev
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  REV as with " + Input.rev1userName + "");
		sessionSearch.switchToWorkproduct();
		sessionSearch.selectFolderInASwp(Foldername);
		sessionSearch.selectFolderInASwp(Foldername1);
		driver.waitForPageToBeReady();
		if (baseClass.text(Foldername).Displayed() && baseClass.text(Foldername1).Displayed()) {
			baseClass.passedStep(Foldername + Foldername1
					+ "   Selected Folders has been  inserted in search criteria for advanced search  as expected");
		} else {
			baseClass.failedStep("Selected Folders did not inserted in search criteria for advanced search ");
		}

		baseClass.waitForElement(sessionSearch.getWP_FolderBtn());
		sessionSearch.getWP_FolderBtn().waitAndClick(10);
		baseClass.waitTime(5);
		baseClass.waitForElement(sessionSearch.getAllFoldersTabInwp());
		sessionSearch.getAllFoldersTabInwp().waitAndClick(10);
		baseClass.waitTime(2);
		List<String> AllFolders1 = baseClass.availableListofElements(savedSearch.getcurrentClickedNode());
		baseClass.stepInfo(AllFolders1 + "  Present in workproduct SG list");
		baseClass.waitForElement(sessionSearch.getMetaDataInserQuery());
		sessionSearch.getMetaDataInserQuery().waitAndClick(10);
		driver.scrollPageToTop();
		baseClass.waitTime(5);
		String Searchboxcriteria1 = sessionSearch.getEnterSearchBox().getText();
		System.out.println(Searchboxcriteria1);
		baseClass.stepInfo(Searchboxcriteria1 + "  Present in workproduct search list");

		for (int j = 0; j < AllFolders1.size(); j++) {
			if (Searchboxcriteria1.contains(AllFolders1.get(j))) {

				baseClass.passedStep(AllFolders1.get(j)
						+ "Selected All folders has been  inserted in search criteria for advanced search  as expected");
			} else {
				baseClass.failedStep(
						"Selected  All folders is did not inserted in search criteria for advanced search ");

			}
		}
	}

	/**
	 * @author:
	 * @Date: :N/A
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :Verify that warning and pure hit result appears for
	 *              EmailAuthorAddress Metadata search having phrase included in the
	 *              query without wrapping in quotes on Advanced Search Screen.
	 *              .RPMXCON-49679
	 */

	@Test(description = "RPMXCON-49679", enabled = true, groups = { "regression" })
	public void verifyWarningPureHitResultIncludedQuery() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-49679 Advanced Search");
		baseClass.stepInfo(
				"Verify that warning and pure hit result appears for EmailAuthorAddress Metadata search having phrase included in the query without wrapping in quotes on Advanced Search Screen.");
		String testdataSearch = "EmailAuthorAddress:(John Shaw)";
		String testdataSearch1 = "EmailAuthorAddress:(John R. Shaw) OR Balance money";
		String[] searchList = { testdataSearch, testdataSearch1 };
		String emailAuthorAddress = "EmailAuthorAddress";
		SoftAssert soft = new SoftAssert();

		// login as Rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");
		for (int i = 0; i < searchList.length; i++) {
			baseClass.selectproject();
			sessionSearch.navigateToAdvancedSearchPage();
			baseClass.stepInfo("User navigate to session search page as expected");
			sessionSearch.advMetaDataSearchQueryInsertTest(emailAuthorAddress, searchList[i]);
			baseClass.stepInfo(searchList[i] + "  User has been able to configure query TestData");
			baseClass.waitForElement(sessionSearch.getQuerySearchButton());
			sessionSearch.getQuerySearchButton().waitAndClick(5);
			baseClass.stepInfo("Search button is clicked");
			baseClass.waitForElement(sessionSearch.getQueryAlertGetText());
			String Warningmsg = "Your query contains two or more arguments that do not have an operator between them. In Sightline, each term without an operator between them will be treated as A OR B, not \"A B\" as an exact phrase. If you want to perform a phrase search, wrap the terms in quotations (ex. \"A B\" returns all documents with the phrase A B).Does your query reflect your intent? Click YES to continue with your search as is, or NO to cancel your search so you can edit the syntax.";
			Assert.assertEquals(Warningmsg.replaceAll(" ", ""),
					sessionSearch.getQueryAlertGetText().getText().replaceAll(" ", "").replaceAll("\n", ""));
			baseClass.passedStep("  Query Alert message is displayed  " + Warningmsg);
			driver.waitForPageToBeReady();
			if (sessionSearch.getYesQueryAlert().isElementAvailable(8)) {
				sessionSearch.getYesQueryAlert().waitAndClick(8);
			}
			baseClass.waitForElement(sessionSearch.getPureHitsCountNumText());
			soft.assertTrue(sessionSearch.getPureHitsCountNumText().isDisplayed());
			baseClass.passedStep(
					"Pure hit result has been appear for " + searchList[i] + "Metadata search as expected ");
			soft.assertAll();

		}

	}

	/**
	 * @author:
	 * @Date: :N/A
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :Verify that Advanced Search works properly for "CreateDate"
	 *              field with "Is" operator and NOT having time components
	 *              .RPMXCON-49171
	 */
	@Test(description = "RPMXCON-49171", enabled = true, groups = { "regression" })
	public void verifyAdvancedSearchWorksForCreateDateWithISOperator() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-49171 Advanced Search");
		baseClass.stepInfo(
				"Verify that Advanced Search works properly for \"CreateDate\" field with \"Is\" operator and NOT having time components");
		String testdataSearch = "2010-10-18";
		DocListPage doclist = new DocListPage(driver);
		String createDate = "CreateDate";
		String[] values = { "CreateDate" };
		// login as Rmu
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");
		int result = sessionSearch.advancedMetaDataSearch(createDate, "IS", testdataSearch, null);
		baseClass.passedStep(result + "CreateDate field search result has been return documents as expected ");
		sessionSearch.ViewInDocList();
		driver.waitForPageToBeReady();
		doclist.SelectColumnDisplayByRemovingExistingOnes(values);
		String date = doclist.getDataInDoclist(1, 4).getText();
		baseClass.passedStep("Date Format present-" + date);
		int size = date.length();
		System.out.println(size);
		baseClass.digitCompareEquals(size, 19, "CreateDate format is displayed as expected",
				"CreateDate format is not displayed as expected");
		loginPage.logout();
	}

	/**
	 * @author
	 * @Description :Validate searching for @ found in phrases in Advanced
	 *              Search.[RPMXCON-49746]
	 */
	@Test(description = "RPMXCON-49746", enabled = true, groups = { "regression" })
	public void validateSearchingForAtSymbolFoundInPhrasesInAdvancedSearch() {

		String searchString = "\"@consilio  test”~10";
		String exampleSearchString = "@consilio";

		baseClass.stepInfo("Test case Id: RPMXCON-49746 Advanced Search.");
		baseClass.stepInfo("Validate searching for @ found in phrases in Advanced Search.");

		// login
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		// configure search Query
		sessionSearch.advanedContentDraftSearch(searchString);
		baseClass.stepInfo("Search Query configured.");

		// Click on "Search" button
		baseClass.stepInfo("Clicking on 'Search' button.");
		sessionSearch.SearchBtnAction();

		// verify that application displays Proximity warning message
		sessionSearch.verifyWarningMessage(false, true, 5);
		baseClass.passedStep("verified that application displays Proximity warning message.");

		// Click on "Yes" button
		baseClass.waitTime(2);
		sessionSearch.tallyContinue(5);
		baseClass.waitTime(2);
		// Verify that result appears for given TestData in Advanced Search Query
		// Screen.
		int searchStringPureHit = sessionSearch.returnPurehitCount();
		baseClass.passedStep("Verified that result appears for given TestData in Advanced Search Query Screen.");

		// Verify that result appears for terms that include @.
		baseClass.stepInfo("performing search for terms that include @.");
		sessionSearch.advancedNewContentSearchNotPureHit(exampleSearchString);
		sessionSearch.tallyContinue(5);
		int exampleSearchStringPureHit = sessionSearch.returnPurehitCount();

		// Verify that pureHit appear for given test Data match with pureHit appear for
		// terms that include @ .
		assertion.assertEquals(searchStringPureHit, exampleSearchStringPureHit);
		assertion.assertAll();
		baseClass.passedStep(
				"verified that pureHit appear for given test Data match with pureHit appear for terms that include @ .");

		// logOut
		loginPage.logout();
	}

	@DataProvider(name = "proximityWithinProximityQuery")
	public Object[][] proximityWithinProximityQuery() {
		return new Object[][] {
				{"\"money (\"development requirements\"~4)\"~6"},
				{"“money (“development requirements”~4)”~6"},
				{"“money (“development requirements”~4)”~6"} };
	}
	
	/**
	 * @author
	 * @Description : Verify that result appears for query when User configured
	 *  proximity within proximity query in  Advanced Search Query Screen.RPMXCON-57338
	 */
	@Test(description = "RPMXCON-57338",dataProvider = "proximityWithinProximityQuery", enabled = true, groups = { "regression" })
	public void verifyResultAppearsForQueryUserConfiguredProximityWithinProximityQueryInAdvanceSearch(String searchString) {
		
		String exampleSearchString ="\"money (\"development requirements\"~4)\"~6";
		
		baseClass.stepInfo("Test case Id: RPMXCON-57338 Advanced Search.");
		baseClass.stepInfo("Verify that result appears for query when User configured proximity within proximity query in  Advanced Search Query Screen.");
		
		// login
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		
		// configure search Query
		sessionSearch.advanedContentDraftSearch(searchString);
		baseClass.stepInfo("Search Query configured.");
				
		//Click on "Search" button
		baseClass.stepInfo("Clicking on 'Search' button.");
		sessionSearch.SearchBtnAction();
		
		//verify that application displays Proximity warning message
		sessionSearch.verifyWarningMessage(false,true,5);
		baseClass.passedStep("verified that application displays Proximity warning message.");
		
		//Click on "Yes" button
		sessionSearch.tallyContinue(5);
		baseClass.waitTime(2);
		//Verify that result appears for query when User configured proximity within proximity query in  Advanced Search Query Screen.
		int searchStringPureHit = sessionSearch.returnPurehitCount();
		baseClass.passedStep("Verified that result appears for query when User configured proximity within proximity query in  Advanced Search Query Screen.");
		
		// performing search for given example proximity search query.
		baseClass.stepInfo("performing search for given example proximity search query.");
		sessionSearch.advancedNewContentSearchNotPureHit(exampleSearchString);
		sessionSearch.tallyContinue(5);
		int exampleSearchStringPureHit = sessionSearch.returnPurehitCount();
		
		// Verify that Result should appear for proximity within proximity in Advanced Search Query Screen. example "money ("development requirements"~4)"~6 money within 6 words of instances where development is within 4 words of requirements
		assertion.assertEquals(searchStringPureHit, exampleSearchStringPureHit);
		assertion.assertAll();
		baseClass.passedStep("verified that pureHit appear for test Data proximity Search Query match with pureHit appear for given example proximity Search Query.");
		
		// logOut
		loginPage.logout();
	}
	
	@DataProvider(name = "proximityHavingPhrasesAndTerm")
	public Object[][] proximityHavingPhrasesAndTerm() {
		return new Object[][] {
				{"\"iterative  (\"development methodology\") (\"money related\")\"~9"},
				{"“iterative  (“development methodology”) (“money related”)”~9"},
				{"“iterative  (“development methodology”) (“money related”)”~9"} };
	}
	
	/**
	 * @author
	 * @Description :Verify that result appears for proximity having
	 *  Phrases and Term in  Advanced Search Query Screen.RPMXCON-57338
	 */
	@Test(description = "RPMXCON-57337",dataProvider = "proximityHavingPhrasesAndTerm", enabled = true, groups = { "regression" })
	public void verifyResultAppearsForProximityHavingPhrasesAndTermInAdvancedSearchQuery(String searchString) {
		
		String exampleSearchString ="\"iterative  (\"development methodology\") (\"money related\")\"~9";
		
		baseClass.stepInfo("Test case Id: RPMXCON-57337 Advanced Search.");
		baseClass.stepInfo("Verify that result appears for proximity having Phrases and Term in  Advanced Search Query Screen.");
		
		// login
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		
		// configure search Query
		sessionSearch.advanedContentDraftSearch(searchString);
		baseClass.stepInfo("Search Query configured.");
				
		//Click on "Search" button
		baseClass.stepInfo("Clicking on 'Search' button.");
		sessionSearch.SearchBtnAction();
		
		//verify that application displays Proximity warning message
		sessionSearch.verifyWarningMessage(false,true,5);
		baseClass.passedStep("verified that application displays Proximity warning message.");
		
		//Click on "Yes" button
		sessionSearch.tallyContinue(5);
		//Verify that result appears for proximity having  Phrases and Term in Advanced Search Query Screen.
		int searchStringPureHit = sessionSearch.returnPurehitCount();
		baseClass.passedStep("Verified that  result appears for proximity having  Phrases and Term in Advanced Search Query Screen.");
		
		// performing search for given example proximity search query.
		baseClass.stepInfo("performing search for given example proximity search query.");
		sessionSearch.advancedNewContentSearchNotPureHit(exampleSearchString);
		sessionSearch.tallyContinue(5);
		baseClass.waitTime(2);
		int exampleSearchStringPureHit = sessionSearch.returnPurehitCount();
		
		// Verify that Result should appear for proximity having Phrases and Term in Advanced Search Query Screen. example "iterative  ("development methodology") ("money related")"~9 iterative Term within phrases ("development methodology") OR ("money related") within 9 words of each other
		assertion.assertEquals(searchStringPureHit, exampleSearchStringPureHit);
		assertion.assertAll();
		baseClass.passedStep("verified that pureHit appear for test Data proximity Search Query match with pureHit appear for given example proximity Search Query.");
		
		// logOut
		loginPage.logout();
	}
	
   /**
  	 * @author Brundha.T TestCase id:RPMXCON-49234
  	 * @Description : Verify that entire "Tag" modal appears within the view-able
  	 *              area of the "Advanced Search" screen. Browser Resolution
  	 *              -1280x1024
  	 */
  	@Test(description = "RPMXCON-49234", enabled = true, groups = { "regression" })

  	public void verifyingTagHelperMenuinBrowserResolution() throws InterruptedException {

  		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

  		baseClass.stepInfo("Test case Id: RPMXCON-49234 ");
  		baseClass.stepInfo(
  				"Verify that entire 'Tag' modal appears within the view-able area of the 'Advanced Search' screen. Browser Resolution -1280x1024");

  		TagsAndFoldersPage tf = new TagsAndFoldersPage(driver);
  		tf.navigateToTagsAndFolderPage();
  		if (baseClass.getAllTags().size() < 6) {
  			for (int i = 1; i <= 5; i++) {
  				String Tag = "Tag" + Utility.dynamicNameAppender();
  				tf.createNewTagwithClassification(Tag, "Select Tag Classification");
  			}

  		}
  		baseClass.stepInfo("Navigating to Advanced Search page");
  		sessionSearch.navigateToAdvancedSearchPage();
  	
  		double[] zoom = { 1, 1.1, 0.9, 0.8, 0.75 };

  		
  		baseClass.stepInfo("Set browser resolution as 1280*1024 ");
  		Dimension pram1 = new Dimension(1280, 1024);
  		driver.Manage().window().setSize(pram1);

  		for (int i = 0; i < zoom.length; i++) {

  			baseClass.stepInfo("set zoom function"+zoom[i]);
  			((JavascriptExecutor) driver.getWebDriver()).executeScript("document.body.style.zoom = '" + zoom[i] + "'");
  			baseClass.waitTime(2);
  			sessionSearch.navigateToAdvancedSearchPage();
  			
  			baseClass.waitForElement(sessionSearch.getWorkproductBtn());
  			sessionSearch.getWorkproductBtn().waitAndClick(10);
  			baseClass.stepInfo("Switched to Advanced search - Work product");
  			baseClass.waitForElement(sessionSearch.getWP_TagBtn());
  			sessionSearch.getWP_TagBtn().Click();

  			if (sessionSearch.getTagMenuPopup().isDisplayed()) {
  				baseClass.passedStep("Insert Tags helper menu is opened");

  			} else {
  				baseClass.failedStep("Insert Tags helper menu is not opened");
  			}

  		}
  		loginPage.logout();

  	}

  	/**
  	 * @author Brundha.T Test case Id:RPMXCON-48113
  	 * @throws InterruptedException
  	 * @Description :Verify that - Application returns all the documents which are
  	 *              available under Assignments - Completed To in search result.
  	 */
  	@Test(description = "RPMXCON-48113", enabled = true, groups = { "regression" })
  	public void verifyingCompletedDocInWP() throws InterruptedException {

  		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
  		baseClass.stepInfo("Test case Id: RPMXCON-48113 ");
  		baseClass.stepInfo(
  				"Verify that - Application returns all the documents which are available under Assignments - Completed To in search result.");

  		String assignmentName = "AssgnName" + Utility.dynamicNameAppender();

  		AssignmentsPage assignment = new AssignmentsPage(driver);
  		baseClass.stepInfo("Navigating to Assignment Page.");
  		assignment.navigateToAssignmentsPage();

  		baseClass.stepInfo("Create Assignment");
  		assignment.createAssignment(assignmentName, Input.codeFormName);

  		baseClass.stepInfo("Bulk action in created Assignment");
  		int PureHit = sessionSearch.basicContentSearch(Input.testData1);
  		sessionSearch.bulkAssignExisting(assignmentName);

  		baseClass.stepInfo("Adding reviewers and distributing");
  		assignment.editAssignmentUsingPaginationConcept(assignmentName);
  		assignment.add2ReviewerAndDistribute();

  		baseClass.stepInfo("select action and complete all document");
  		assignment.getAssignment(assignmentName, "Complete All Documents");

  		baseClass.stepInfo("Navigate to advance search page");
  		sessionSearch.navigateToAdvancedSearchPage();
  		sessionSearch.getWorkproductBtn().waitAndClick(10);
  		
  		baseClass.stepInfo("verifying all the documents under completed assignment-reflecting in search result");
  		 sessionSearch.selectAssignmentInWPS(assignmentName, "Completed", null);
  		 int CompletedDoc =sessionSearch.serarchWP();
  		baseClass.digitCompareEquals(PureHit, CompletedDoc,
  				"Application returns all the documents under Assignments and Completed in search result.",
  				"Application not returning exact document");

  		loginPage.logout();

  	}

  	/**
  	 * @author Brundha.T Test case Id:RPMXCON-48114
  	 * @throws InterruptedException
  	 * @Description :Verify that - Application returns all the documents which are
  	 *              available under Assignments - Assigned in search result.
  	 */
  	@Test(description = "RPMXCON-48114", enabled = true, groups = { "regression" })
  	public void verifyingAssignedDocinWP() throws InterruptedException {

  		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
  		baseClass.stepInfo("Test case Id: RPMXCON-48114");
  		baseClass.stepInfo(
  				"Verify that - Application returns all the documents which are available under Assignments - Assigned in search result.");

  		String assignmentName = "AssgnName" + Utility.dynamicNameAppender();

  		AssignmentsPage assignment = new AssignmentsPage(driver);
  		baseClass.stepInfo("Navigating to Assignment Page.");
  		assignment.navigateToAssignmentsPage();

  		baseClass.stepInfo("Create Assignment");
  		assignment.createAssignment(assignmentName, Input.codeFormName);

  		baseClass.stepInfo("Bulk action in created Assignment");
  		int PureHit = sessionSearch.basicContentSearch(Input.testData1);
  		sessionSearch.bulkAssignExisting(assignmentName);

  		baseClass.stepInfo("Assigning reviewers and distributing");
  		assignment.editAssignmentUsingPaginationConcept(assignmentName);
  		assignment.add2ReviewerAndDistribute();
  		baseClass.getSelectProject();
  		sessionSearch.navigateToAdvancedSearchPage();
  		sessionSearch.getWorkproductBtn().waitAndClick(10);
  		
  		baseClass.stepInfo("verifying all the documents under assignment and Assigned in search result");
  		sessionSearch.selectAssignmentInWPS(assignmentName, "Assigned", null);
  		int CompletedDoc = sessionSearch.serarchWP();
  		baseClass.digitCompareEquals(PureHit, CompletedDoc,
  				"Application returns all the documents under Assignments and Assigned in search result.",
  				"Application not returning exact document");

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
