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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import pageFactory.DataSets;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.IngestionPage_Indium;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.TallyPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class AdvancedSearch_Phase2_Regression {
	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	BaseClass baseClass;
	Input in;
	SoftAssert assertion;
	SecurityGroupsPage securityGroupsPage;
	DocViewRedactions docViewRedact;
	TagsAndFoldersPage tagPage;
	AssignmentsPage assignmentPage;
	DocViewPage docView;

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
		loginPage = new LoginPage(driver);
		sessionSearch = new SessionSearch(driver);
		baseClass = new BaseClass(driver);
		assertion = new SoftAssert();
		savedSearch = new SavedSearch(driver);
		tagPage = new TagsAndFoldersPage(driver);
		docView = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver); 

	}

	@DataProvider(name = "paRmuRevUsers")
	public Object[][] paRmuRevUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, "PA" },
				{ Input.rev1userName, Input.rev1password, "REV" }, { Input.rmu1userName, Input.rmu1password, "RMU" } };
		return users;
	}

	@DataProvider(name = "Users")
	public Object[][] CombinedSearchwithUsers() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password }, { Input.pa1userName, Input.pa1password },
				{ Input.rev1userName, Input.rev1password } };
		return users;
	}

	@DataProvider(name = "assignmentHelperWindoStatus")
	public Object[][] assignmentHelperWindoStatus() {
		Object[][] users = { { "Completed" }, { "Assigned" } };
		return users;
	}

	@DataProvider(name = "userForRmuRev")
	public Object[][] userForRmuRev() {
		return new Object[][] { { Input.rmu1userName, Input.rmu1password },
				{ Input.rev1userName, Input.rev1password } };
	}

	@DataProvider(name = "PaRmuAndRevUSer")
	public Object[][] PaRmuAndRevUSer() {
		return new Object[][] { { Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password },
				{ Input.rev1userName, Input.rev1password } };
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

		// verify that application displays Proximity warning message
		sessionSearch.verifyWarningMessage(true, true, 5);
		baseClass.passedStep("verified that application displays Proximity warning message.");

		// Click on "Yes" button
		baseClass.waitTime(2);
		sessionSearch.tallyContinue(5);
		baseClass.waitTime(2);
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
			}
			else {
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
		//sessionSearch.SearchBtnAction(); Removing this since search action is been handled in 'verifyWarningMessage' methods

		// verify that application displays Proximity warning message
		sessionSearch.verifyWarningMessage(true, true, 5);
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
		return new Object[][] { { "\"money (\"development requirements\"~4)\"~6" },
				{ "“money (“development requirements”~4)”~6" }, { "“money (“development requirements”~4)”~6" } };
	}

	/**
	 * @author
	 * @Description : Verify that result appears for query when User configured
	 *              proximity within proximity query in Advanced Search Query
	 *              Screen.RPMXCON-57338
	 */
	@Test(description = "RPMXCON-57338", dataProvider = "proximityWithinProximityQuery", enabled = true, groups = {
			"regression" })
	public void verifyResultAppearsForQueryUserConfiguredProximityWithinProximityQueryInAdvanceSearch(
			String searchString) {

		String exampleSearchString = "\"money (\"development requirements\"~4)\"~6";

		baseClass.stepInfo("Test case Id: RPMXCON-57338 Advanced Search.");
		baseClass.stepInfo(
				"Verify that result appears for query when User configured proximity within proximity query in  Advanced Search Query Screen.");

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
		// Verify that result appears for query when User configured proximity within
		// proximity query in Advanced Search Query Screen.
		int searchStringPureHit = sessionSearch.returnPurehitCount();
		baseClass.passedStep(
				"Verified that result appears for query when User configured proximity within proximity query in  Advanced Search Query Screen.");

		// performing search for given example proximity search query.
		baseClass.stepInfo("performing search for given example proximity search query.");
		sessionSearch.advancedNewContentSearchNotPureHit(exampleSearchString);
		sessionSearch.tallyContinue(5);
		int exampleSearchStringPureHit = sessionSearch.returnPurehitCount();

		// Verify that Result should appear for proximity within proximity in Advanced
		// Search Query Screen. example "money ("development requirements"~4)"~6 money
		// within 6 words of instances where development is within 4 words of
		// requirements
		assertion.assertEquals(searchStringPureHit, exampleSearchStringPureHit);
		assertion.assertAll();
		baseClass.passedStep(
				"verified that pureHit appear for test Data proximity Search Query match with pureHit appear for given example proximity Search Query.");

		// logOut
		loginPage.logout();
	}

	@DataProvider(name = "proximityHavingPhrasesAndTerm")
	public Object[][] proximityHavingPhrasesAndTerm() {
		return new Object[][] { { "\"iterative  (\"development methodology\") (\"money related\")\"~9" },
				{ "“iterative  (“development methodology”) (“money related”)”~9" },
				{ "“iterative  (“development methodology”) (“money related”)”~9" } };
	}

	/**
	 * @author
	 * @Description :Verify that result appears for proximity having Phrases and
	 *              Term in Advanced Search Query Screen.RPMXCON-57338
	 */
	@Test(description = "RPMXCON-57337", dataProvider = "proximityHavingPhrasesAndTerm", enabled = true, groups = {
			"regression" })
	public void verifyResultAppearsForProximityHavingPhrasesAndTermInAdvancedSearchQuery(String searchString) {

		String exampleSearchString = "\"iterative  (\"development methodology\") (\"money related\")\"~9";

		baseClass.stepInfo("Test case Id: RPMXCON-57337 Advanced Search.");
		baseClass.stepInfo(
				"Verify that result appears for proximity having Phrases and Term in  Advanced Search Query Screen.");

		// login
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// configure search Query
		sessionSearch.advanedContentDraftSearch(searchString);
		baseClass.stepInfo("Search Query configured.");

		// verify that application displays Proximity warning message
		sessionSearch.verifyWarningMessage(true, true, 5);
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
		// Advanced Search Query Screen. example "iterative ("development methodology")
		// ("money related")"~9 iterative Term within phrases ("development
		// methodology") OR ("money related") within 9 words of each other
		assertion.assertEquals(searchStringPureHit, exampleSearchStringPureHit);
		assertion.assertAll();
		baseClass.passedStep(
				"verified that pureHit appear for test Data proximity Search Query match with pureHit appear for given example proximity Search Query.");

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

			baseClass.stepInfo("set zoom function" + zoom[i]);
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
		int CompletedDoc = sessionSearch.serarchWP();
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

	/**
	 * @author
	 * @Description :Verify that correct result appears for Proximity Queries
	 *              containing Boolean components AND in Advanced Search Query
	 *              Screen.RPMXCON-50026
	 */
	@Test(description = "RPMXCON-50026", enabled = true, groups = { "regression" })
	public void verifyResultAppearsForProximityQueriesContainingBooleanComponentsANDinAdvanSearch() {

		String searchString = "(\"ProximitySearch Iterative\"~15 AND financial) OR m0ney";
		String exampleSearchString = "(\"ProximitySearch Iterative\"~15 AND financial) OR m0ney";

		baseClass.stepInfo("Test case Id: RPMXCON-50026 Advanced Search.");
		baseClass.stepInfo(
				"Verify that  correct result appears for Proximity Queries containing Boolean components AND in Advanced Search Query Screen.");

		// login
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// configure search Query
		sessionSearch.advanedContentDraftSearch(searchString);
		baseClass.stepInfo("Search Query configured.");

		// Click on "Search" button
		baseClass.stepInfo("Clicking on 'Search' button.");
//		sessionSearch.SearchBtnAction(); Removing this since search action is been handled in 'verifyWarningMessage' methods

		// verify that application displays Proximity warning message
		sessionSearch.verifyWarningMessage(true, true, 5);
		baseClass.passedStep("verified that application displays Proximity warning message.");

		// Click on "Yes" button
		baseClass.waitTime(2);
		sessionSearch.tallyContinue(5);
		baseClass.waitTime(2);
		// Verify that correct result appears for Proximity Queries containing Boolean
		// components AND in Advanced Search Query Screen.
		int searchStringPureHit = sessionSearch.returnPurehitCount();
		baseClass.passedStep(
				"Verified that result appears for Proximity Queries containing Boolean components AND in Advanced Search Query Screen.");

		// performing search for given example proximity search query.
		baseClass.stepInfo("performing search for given example proximity search query.");
		sessionSearch.advancedNewContentSearchNotPureHit(exampleSearchString);
		sessionSearch.tallyContinue(5);
		int exampleSearchStringPureHit = sessionSearch.returnPurehitCount();

		// Verify that Correct result should appear for Proximity Queries containing
		// Boolean components AND in Advanced Search Query Screen. example
		// ("ProximitySearch Iterative"~15 AND financial) OR m0ney This query returns
		// documents having - "ProximitySearch and Iterative" within 15 words AND term
		// "financial " OR M0ney
		assertion.assertEquals(searchStringPureHit, exampleSearchStringPureHit);
		assertion.assertAll();
		baseClass.passedStep(
				"verified that pureHit appear for Proximity Queries containing Boolean components AND match with pureHit appear for given example proximity Search Query.");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Description :To verify, as an user login into the Application, I will be
	 *              able to set the minimum precision for concept search in advanced
	 *              search & get the search results based on that.RPMXCON-57084
	 */

	@Test(description = "RPMXCON-57084", enabled = true, dataProvider = "PaRmuAndRevUSer", groups = { "regression" })
	public void verifyUserAbleToSetMinimumPrecisionForConceptualSearchInAdvanSearch(String userName, String password) {

		baseClass.stepInfo("Test case Id: RPMXCON-57084 Advanced Search.");
		baseClass.stepInfo(
				"To verify, as an user login into the Application, I will be able to set the minimum precision for concept search in advanced search & get the search results based on that.");

		// login
		loginPage.loginToSightLine(userName, password);

		// Configuring concept search with minimum precision and Performing search
		// operation.
		baseClass.stepInfo("Navigating to Advanced Search Page.");
		baseClass.stepInfo("Configuring concept search with minimum precision and Performing search operation.");
		sessionSearch.conceptualSearch_new(Input.conceptualSearchString1, "left");
		baseClass.passedStep("verified that search results appeared for concept search with minimum precision.");

		// Configuring concept search with maximum precision and Performing search
		// operation.
		baseClass.selectproject();
		baseClass.stepInfo("Navigating to Advanced Search Page.");
		baseClass.stepInfo("Configuring concept search with maximum precision and Performing search operation.");
		sessionSearch.conceptualSearch_new(Input.conceptualSearchString1, "right");
		baseClass.passedStep("verified that search results appeared for concept search with maximum precision.");

		// Configuring concept search with random precision and Performing search
		// operation.
		baseClass.selectproject();
		baseClass.stepInfo("Navigating to Advanced Search Page.");
		baseClass.stepInfo("Configuring concept search with random precision and Performing search operation.");
		sessionSearch.conceptualSearch_new(Input.conceptualSearchString1, "mid");
		baseClass.passedStep("verified that search results appeared for concept search with random precision.");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Description :Verify that user should be able to search with the field
	 *              EmailBCCNamesAndAddresses from Advanced search."RPMXCON-49768
	 */
	@Test(description = "RPMXCON-49768", dataProvider = "PaRmuAndRevUSer", enabled = true, groups = { "regression" })
	public void verifyUserAbleToSearchWithFieldEmailBCCNamesAndAddressesInAdvancedSearch(String userName,
			String password) {

		String metaDataField = "EmailBCCNamesAndAddresses";
		String searchStringWithDoubleQuotes = "\"" + Input.emailAllDomainOption + "\"";
		String searchStringWithOutDoubleQuotes = Input.emailAllDomainOption;
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Test case Id: RPMXCON-49768 Advanced Search");
		baseClass.stepInfo(
				"Verify that user should be able to search with the field EmailBCCNamesAndAddresses from Advanced search.");

		// Configure the query to search with Metadata EmailBCCNamesAndAddresses with
		// double quotes
		baseClass.stepInfo(
				"Configuring the query to search with Metadata EmailBCCNamesAndAddresses with double quotes.");
		sessionSearch.advancedMetaDataForDraft(metaDataField, null, searchStringWithDoubleQuotes, null);

		// verify that Result should appear for entered EmailBCCNamesAndAddresses with
		// double quote in Search Query Screen with exact match.
		sessionSearch.SearchBtnAction();
		sessionSearch.returnPurehitCount();
		baseClass.waitForElement(sessionSearch.contentAndMetaDataResult());
		baseClass.stepInfo("Resultant Search Query : " + sessionSearch.contentAndMetaDataResult().getText()
				+ " in Advanced Search Query Screen.");
		baseClass.passedStep(
				"verified that Result appear for entered EmailBCCNamesAndAddresses with double quote in Search Query Screen with exact match.");

		// click add new search button
		sessionSearch.addNewSearch();

		// Configure the query in ADVANCED SEARCH to search with Metadata
		// EmailBCCNamesAndAddresses without double quotes
		driver.waitForPageToBeReady();
		baseClass.stepInfo(
				"Configuring the query to search with Metadata EmailBCCNamesAndAddresses without double quotes.");
		sessionSearch.getContentAndMetaDatabtnC().waitAndClick(10);
		sessionSearch.newMetaDataSearchInBasicSearch(metaDataField, searchStringWithOutDoubleQuotes);
		driver.waitForPageToBeReady();

		// verify that Result should appear for entered EmailBCCNamesAndAddresses
		// without double quote in Search Query Screen.
		baseClass.stepInfo("Resultant Search Query : " + sessionSearch.contentAndMetaDataResult().getText()
				+ " in Advanced Search Query Screen.");
		baseClass.passedStep(
				"verified that Result appear for entered EmailBCCNamesAndAddresses without double quote in  Search Query Screen");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Description :dvanced Search- Verify that fields should be released
	 *              automatically to security group: "EmailToNamesAndAddresses",
	 *              "EmailAuthorNameAndAddresses", "EmailCCNamesAndAddresses",
	 *              "EmailBCCNamesAndAddresses".RPMXCON-49771
	 */
	@Test(description = "RPMXCON-49771", dataProvider = "userForRmuRev", enabled = true, groups = { "regression" })
	public void verifyFieldsReleasedAutomaticallyToSecurityGroup(String userName, String password) throws Exception {
		securityGroupsPage = new SecurityGroupsPage(driver);
		UserManagement user = new UserManagement(driver);
		String securityGroup = "securityGroup" + Utility.dynamicNameAppender();
		List<String> listOfMetaData = new ArrayList<String>(Arrays.asList("EmailToNamesAndAddresses",
				"EmailAuthorNameAndAddress", "EmailCCNamesAndAddresses", "EmailBCCNamesAndAddresses"));

		baseClass.stepInfo("Test case Id: RPMXCON-49771");
		baseClass.stepInfo(
				"Advanced Search- Verify that fields should be released automatically to security group: \"EmailToNamesAndAddresses\", \"EmailAuthorNameAndAddresses\", \"EmailCCNamesAndAddresses\", \"EmailBCCNamesAndAddresses\".");

		// login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		// create securityGroup
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.AddSecurityGroup(securityGroup);
		// assign securityGroup to User
		baseClass.stepInfo("assigning securityGroup to User.");
		user.assignAccessToSecurityGroups(securityGroup, userName);
		// logOut
		loginPage.logout();

		// login as User
		loginPage.loginToSightLine(userName, password);
		baseClass.selectsecuritygroup(securityGroup);

		// verify That "EmailToNamesAndAddresses", "EmailAuthorNameAndAddresses",
		// "EmailCCNamesAndAddresses", "EmailBCCNamesAndAddresses" fields should be
		// released to the security group automatically
		sessionSearch.verifyListOfMetaDataFromMetaDataDropDown(listOfMetaData);
		baseClass.stepInfo(
				"verified That \"EmailToNamesAndAddresses\", \"EmailAuthorNameAndAddresses\", \"EmailCCNamesAndAddresses\", \"EmailBCCNamesAndAddresses\" fields released to the security group automatically.");

		baseClass.selectsecuritygroup(Input.securityGroup);

		// logOut
		loginPage.logout();

		// Deleting SecurityGroup
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		securityGroupsPage.deleteSecurityGroups(securityGroup);

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Description :Verify that Audio search functionality is working proper with
	 *              only alphabets terms in Advanced Search.RPMXCON-49365
	 */
	@Test(description = "RPMXCON-49365", dataProvider = "PaRmuAndRevUSer", enabled = true, groups = { "regression" })
	public void verifyAudioSearchFunctionalityWorkingProperWithOnlyAlphabetsTermInAdvanSearch(String userName,
			String password) throws InterruptedException {
		String searchString = Input.audioSearch;
		String language = Input.language;
		String operator = "ANY";
		baseClass.stepInfo("Test case Id: RPMXCON-49365 Advanced Search");
		baseClass.stepInfo(
				"Verify that Audio search functionality is working proper with only alphabets terms in Advanced Search.");

		loginPage.loginToSightLine(userName, password);

		// verify That Audio search Result should get displayed in Advanced Search
		// Result and Pure Hit count should get display on Result screen
		baseClass.stepInfo("Navigating to Advanced Search Page.");
		baseClass.stepInfo(
				"Configuring Valid query only alphabets like Term Operator : Any Language Pack / Dialect: North American English Minimum Confidence Threshold: 55  Search Term : Morning");
		sessionSearch.audioSearchWithOperator(searchString, "", language, -110, operator);
		baseClass.passedStep(
				"verified That Audio search Result get displayed in Advanced Search  Result and Pure Hit count get display on Result screen.");

		// logOut
		loginPage.logout();

	}

	@DataProvider(name = "phraseWithoutWrappingInQuotes")
	public Object[][] phraseWithoutWrappingInQuotes() {
		return new Object[][] { { "CustodianName: (John R. Shaw) OR Balance money" }, { "Balance money Mobile" },
				{ "Balance money Mobile OR CustodianName:(John R. Shaw)" } };
	}

	/**
	 * @author
	 * @Description :Verify that warning and pure hit result appears While Editing
	 *              an existing CustodianName Metadata search having phrase included
	 *              in the query without wrapping in quotes on Advanced Search
	 *              Screen.RPMXCON-49700
	 */

	@Test(description = "RPMXCON-49700", dataProvider = "phraseWithoutWrappingInQuotes", enabled = true, groups = {
			"regression" })
	public void verifyWaringAndPureHitResultAppearsWhileEditingExistingCustodianNameMetadataSearchHavingPhraseQueryWithoutWrappingInQuotes(
			String searchString) {

		String savedSearchString = "savedSearch" + Utility.dynamicNameAppender();
		baseClass.stepInfo("Test case Id: RPMXCON-49700");
		baseClass.stepInfo(
				"Verify that warning and pure hit result appears While Editing an existing CustodianName Metadata search having phrase included in the query without wrapping in quotes on Advanced Search Screen.");

		// login
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Pre-Requisite
		// performing search for given search Query and saving the search Result
		baseClass.stepInfo("performing search for given search Query and saving the search Result.");
		sessionSearch.advancedContentSearch(searchString);
		sessionSearch.saveSearch(savedSearchString);

		// Clearing All the searches in session Search since it is Pre-Requisite
		baseClass.selectproject();

		// selecting the saved search and Click on Edit Button
		baseClass.stepInfo("selecting the saved search and Click on Edit Button.");
		savedSearch.savesearch_Edit(savedSearchString);

		// Click On Search Button
		baseClass.stepInfo("Click On Search Button.");
		sessionSearch.resubmitSearch();

		// Verify that application displays Query Alert message.
		sessionSearch.verifyWarningMessage(false, true, 6);
		baseClass.passedStep("Verified that application displays Query Alert message.");

		// Verify that pure hit result appears for TestData Metadata search while
		// editing Metadata having phrase included in the query without wrapping in
		// quotes on Advanced Search Screen.
		sessionSearch.tallyContinue(5);
		sessionSearch.returnPurehitCount();
		baseClass.stepInfo("Verified that pure hit result appears for '" + searchString
				+ "' Metadata search while editing Metadata having phrase included in the query without wrapping in quotes on Advanced Search Screen.");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author Raghuram A
	 * @Date: 08/08/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : To verify an an user login, I will be able to select multiple
	 *              node from Saved search results column under Work Product tab &
	 *              set that as a search criteria for advanced search. RPMXCON-57049
	 */
	@Test(description = "RPMXCON-57049", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void verifyUserAbleToSelectMultipleNodeFromSavedSearchResultsInAdvancedSearch(String userName,
			String password) throws Exception {

		String searchName1 = "savedSearch" + Utility.dynamicNameAppender();
		String searchName2 = "savedSearch" + Utility.dynamicNameAppender();
		List<String> nodeList = new ArrayList<String>();
		HashMap<String, String> nodeAndSavedSearchPair = new HashMap<String, String>();
		HashMap<String, String> savedSearchAndSearchIdPair = new HashMap<String, String>();

		// login as PA
		loginPage.loginToSightLine(userName, password);

		baseClass.stepInfo("RPMXCON-57049 Advanced Search");
		baseClass.stepInfo(
				"To verify an an user login, I will be able to select multiple node from Saved search results column under Work Product tab & set that as a search criteria for advanced search");

		// creating Node in savedSearch page
		nodeList.add(savedSearch.createNewSearchGrp(Input.mySavedSearch));
		nodeList.add(savedSearch.createNewSearchGrp(Input.mySavedSearch));

		// performing search and saving it in created Node
		sessionSearch.advancedContentSearch(Input.searchString1);
		sessionSearch.saveAdvanceSearchInNode(searchName1, nodeList.get(0));
		nodeAndSavedSearchPair.put(nodeList.get(0), searchName1);
		sessionSearch.saveAdvanceSearchInNode(searchName2, nodeList.get(1));
		nodeAndSavedSearchPair.put(nodeList.get(1), searchName2);

		// getting search ID of the search from savedSearch.
		savedSearch.selectNodeUnderSpecificSearchGroup(Input.mySavedSearch, nodeList.get(0));
		savedSearchAndSearchIdPair.put(nodeAndSavedSearchPair.get(nodeList.get(0)),
				savedSearch.getSelectSearchWithID(nodeAndSavedSearchPair.get(nodeList.get(0))).getText());
		savedSearch.selectNodeUnderSpecificSearchGroup(Input.mySavedSearch, nodeList.get(1));
		savedSearchAndSearchIdPair.put(nodeAndSavedSearchPair.get(nodeList.get(1)),
				savedSearch.getSelectSearchWithID(nodeAndSavedSearchPair.get(nodeList.get(1))).getText());

		// configuring the workProduct search Query by inserting multiple savedSearch
		// Node.
		baseClass.selectproject();
		sessionSearch.navigateToAdvancedSearchPage();
		baseClass.stepInfo("configuring the workProduct search Query by inserting multiple SavedSearch Results.");
		sessionSearch.insertMultipleSavedSearchResultsInWp(nodeList);

		// getting the actual Configure Search Query
		String actualConfiguredQuery = sessionSearch.getSavedSearchQueryAS().getText();

		// verify that Selected Node inserted as a search criteria for advanced search.
		assertion.assertEquals(actualConfiguredQuery
				.contains(savedSearchAndSearchIdPair.get(nodeAndSavedSearchPair.get(nodeList.get(0)))), true);
		assertion.assertEquals(actualConfiguredQuery
				.contains(savedSearchAndSearchIdPair.get(nodeAndSavedSearchPair.get(nodeList.get(1)))), true);
		//assertion.assertAll();
		baseClass.passedStep("verified that Selected Node inserted as a search criteria for advanced search.");

		// delete node from savedSearch
		savedSearch.deleteNode(Input.mySavedSearch, nodeList.get(0));
		savedSearch.deleteNode(Input.mySavedSearch, nodeList.get(1));

		// logOut
		loginPage.logout();

	}

	/**
	 * @author Raghuram A
	 * @Date: 08/08/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that workproduct - Assignments helper window does not
	 *              truncate "Insert into Query" button at the bottom of the page.
	 *              RPMXCON-48208
	 */
	@Test(description = "RPMXCON-48208", dataProvider = "assignmentHelperWindoStatus", enabled = true, groups = {
			"regression" })
	public void verifyWorkProductAssignmentHelperWindowDoesNotTruncateInsetIntoQueryButton(String status)
			throws Exception {

		String assignmentName = "assignment" + Utility.dynamicNameAppender();

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		baseClass.stepInfo("Test case Id: RPMXCON-48208");
		baseClass.stepInfo(
				"Verify that workproduct - Assignments helper window does not truncate \"Insert into Query\" button at the bottom of the page");

		// create Assignment
		assignmentPage.createAssignment(assignmentName, Input.codeFormName);

		// configure Work Product Search with Assignment and verifying the 'Insert into
		// Query' button
		sessionSearch.navigateToAdvancedSearchPage();
		baseClass.waitForElement(sessionSearch.getWorkproductBtn());
		sessionSearch.getWorkproductBtn().Click();
		baseClass.stepInfo("Configuring the Assignment WorkProduct Search Query with '" + status + "' Status.");
		sessionSearch.selectAssignmentAndReviewers_Status_dateRange(assignmentName, Input.rev1userName, null, null,
				status, true);
		baseClass.stepInfo(
				"Verified that workproduct - Assignments helper window does not truncate \"Insert into Query\" button at the bottom of the page");

		// delete Assignment
		assignmentPage.deleteAssignment(assignmentName);

		// logOut
		loginPage.logout();

	}

	/**
	 * @author S
	 * @Date: 08/10/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that Modified reviewer remarks for audio documents is
	 *              working correctly in Advanced Search.. RPMXCON-46884
	 */
	@Test(description = "RPMXCON-46884", enabled = true, groups = { "regression" })
	public void verifyModifiedReviewerRemarkForAudioDocumentsWorkingCorrectlyInAdvSearch()
			throws InterruptedException, Exception {

		String remark = "remark" + Utility.dynamicNameAppender();
		String modifiedRemark = "modified";
		int noOfAudioRemarkAdd = 1;
		int ExpectedRemarkDocCountAfterModification = 0;
		Map<String, String> updateDatas = new HashMap<String, String>();

		// login as PA
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		baseClass.stepInfo("Test case Id: RPMXCON-46884");
		baseClass.stepInfo(
				"Verify that Modified reviewer remarks for audio documents is working correctly in  Advanced Search.");

		// performing audio search and navigating to DocView
		baseClass.stepInfo("Performing Audio Search.");
		sessionSearch.audioSearch(Input.audioSearch, Input.language);
		baseClass.stepInfo("adding pureHit to Shopping cart and viewing the Documents in docView page.");
		sessionSearch.ViewInDocViews();

		// adding reamrk to audio file
		docView.audioRemark(remark);

		// performing remark search and viewing the remark Documents in docView page.
		baseClass.selectproject();
		int remarkDocCountBeforeModification = sessionSearch.getCommentsOrRemarksCount_AdvancedSearch("Remark", remark);
		assertion.assertEquals(remarkDocCountBeforeModification, noOfAudioRemarkAdd);
		baseClass.stepInfo("adding pureHit to Shopping cart and viewing the Documents in docView page.");
		sessionSearch.ViewInDocViews();

		// modifying the remark
		docView.getAdvancedSearchAudioRemarkIcon().waitAndClick(5);
		docView.editAndVerifyData(remark, updateDatas, modifiedRemark);

		// verifying whether that Application displaying the latest count excluding the
		// documents for which remarks was modified.
		baseClass.selectproject();
		int remarkDocCountAfterModification = sessionSearch.getCommentsOrRemarksCount_AdvancedSearch("Remark", remark);
		assertion.assertEquals(remarkDocCountAfterModification, ExpectedRemarkDocCountAfterModification);
		assertion.assertNotEquals(remarkDocCountBeforeModification, remarkDocCountAfterModification);
		assertion.assertAll();
		baseClass.passedStep(
				"Verified that Application displaying the latest count excluding the documents for which remarks was modified.");

		// deleting the remark
		baseClass.selectproject();
		sessionSearch.getCommentsOrRemarksCount_AdvancedSearch("Remark", modifiedRemark);
		sessionSearch.ViewInDocViews();
		baseClass.stepInfo("adding pureHit to Shopping cart and viewing the Documents in docView page.");
		docView.deleteAudioRemark();

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Date: 08/12/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that 2 Search criteria gets combined with AND/OR/NOT
	 *              condition. RPMXCON-47612
	 */
	@Test(description = "RPMXCON-47612", enabled = true, groups = { "regression" })
	public void verifyThatTwoSearchCriteriaCombinedWithAND_OR_NOTCondition() throws Exception {

		// login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		baseClass.stepInfo("Test case Id: RPMXCON-47612");
		baseClass.stepInfo("Verify that 2 Search criteria gets combined with AND/OR/NOT condition");

		// getting the metaData pureHit Count
		int metaDataPureHitCount = sessionSearch.MetaDataSearchInAdvancedSearch(Input.metaDataName,
				Input.metaDataCustodianNameInput);

		// getting the Work Product Security Group pureHit Count
		baseClass.selectproject();
		sessionSearch.navigateToAdvancedSearchPage();
		baseClass.stepInfo("performing the Work Product Security Group.");
		sessionSearch.workProductSearch("security group", Input.securityGroup, true);
		int workProductSGPureHitCount = sessionSearch.serarchWP();

		// Work Product Security Group pureHit Count Excluding the metaData pureHit
		// Count
		int expectedSGNotMetaPureHitCount = workProductSGPureHitCount - metaDataPureHitCount;

		// performing Combined Search of metaData with Work Product Security Group with
		// AND Operator
		baseClass.selectproject();
		baseClass
				.stepInfo("performing Combined Search of metaData with Work Product Security Group with AND Operator.");
		sessionSearch.advancedMetaDataForDraft(Input.metaDataName, null, Input.metaDataCustodianNameInput, null);
		sessionSearch.workProductSearch("security group", Input.securityGroup, true);
		sessionSearch.selectOperator("AND", 1);
		int metaAndSGPureHitCount = sessionSearch.serarchWP();
		// Verifying that Search results appears which satisfied both condition
		// 'metaData AND Work Product Security Group'
		baseClass.digitCompareEquals(metaAndSGPureHitCount, metaDataPureHitCount,
				"Verified that Search results appears which satisfied both condition 'metaData AND Work Product Security Group'.",
				"Search results Doesn't match with the Expected PureHit count");

		// performing Combined Search of metaData with Work Product Security Group with
		// OR Operator
		baseClass.selectproject();
		baseClass.stepInfo("performing Combined Search of metaData with Work Product Security Group with OR Operator.");
		sessionSearch.advancedMetaDataForDraft(Input.metaDataName, null, Input.metaDataCustodianNameInput, null);
		sessionSearch.workProductSearch("security group", Input.securityGroup, true);
		sessionSearch.selectOperator("OR", 1);
		int metaOrSGPureHitCount = sessionSearch.serarchWP();
		// Verified that Search results appears which satisfied anyone condition
		// 'metaData OR Work Product Security Group'
		baseClass.digitCompareEquals(metaOrSGPureHitCount, workProductSGPureHitCount,
				"Verified that Search results appears which satisfied anyone condition 'metaData OR Work Product Security Group'.",
				"Search results Doesn't match with the Expected PureHit count");

		// performing Combined Search of Work Product Security Group with metaData with
		// NOT Operator
		baseClass.selectproject();
		baseClass.stepInfo(
				"performing Combined Search of  Work Product Security Group with metaData with NOT Operator.");
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.workProductSearch("security group", Input.securityGroup, true);
		sessionSearch.advMetaDataSearchQueryInsertTest(Input.metaDataName, Input.metaDataCustodianNameInput);
		sessionSearch.selectOperator("NOT", 1);
		int SGNotMetaPureHitCount = sessionSearch.serarchWP();
		// "Verified that Search results appears NOT having metaData Document Count
		// 'Work Product Security Group NOT metaData'
		baseClass.digitCompareEquals(SGNotMetaPureHitCount, expectedSGNotMetaPureHitCount,
				"Verified that Search results appears NOT having metaData Document Count 'Work Product Security Group NOT metaData'.",
				"Search results Doesn't match with the Expected PureHit count");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Date: 08/12/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that Advanced Search is working properly for Datasource
	 *              Metadata. RPMXCON-57058
	 */
	@Test(description = "RPMXCON-57058", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void verifyAdvancedSearchWorkingProperlyForDatasourceMetadata(String userName, String password)
			throws Exception {

		String dataSource;
		String[] columnToSelect = { Input.dataSourceHeader };
		DocListPage docList = new DocListPage(driver);
		List<String> dataSourceColumnValues = new ArrayList<String>();

		// login as User
		loginPage.loginToSightLine(userName, password);

		baseClass.stepInfo("Test case Id: RPMXCON-57058");
		baseClass.stepInfo("Verify that Advanced Search is working properly for Datasource Metadata.");

		// getting metaData DataSource SearchString
		baseClass.stepInfo("getting metaData DataSource SearchString");
		sessionSearch.advancedContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();
		docList.SelectColumnDisplayByRemovingExistingOnes(columnToSelect);
		dataSourceColumnValues = docList.getColumnValue(columnToSelect[0], false);
		dataSource = dataSourceColumnValues.get(0);
		baseClass.stepInfo("DataSource searchString : '" + dataSource + "'");

		// performing metaData search with DataSource
		baseClass.selectproject();
		baseClass.stepInfo("Performing metaData Search With DataSource");
		sessionSearch.advancedMetaDataSearch("DataSource", null, dataSource, null);

		// viewing the documents in docList
		sessionSearch.ViewInDocList();

		// adding the DataSource metaData column in DocList
		baseClass.stepInfo("adding DataSource metaData column in DocList.");
		docList.SelectColumnDisplayByRemovingExistingOnes(columnToSelect);
		// getting all the values from DataSource column
		baseClass.stepInfo("getting all the values from DataSource column.");
		dataSourceColumnValues = docList.getColumnValue(columnToSelect[0], false);

		// verifying that Advanced Search is working properly for Datasource Metadata by
		// comparing dataSource searchString with DataSource column values in DocList
		baseClass.compareListWithString(dataSourceColumnValues, dataSource,
				"Verified that Advanced Search is working properly for Datasource Metadata.",
				"Advanced Search is Not working properly for Datasource Metadata.");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Date: 08/16/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that Advanced Search is working properly for
	 *              DocumentFileTypeName Metadata. RPMXCON-57059
	 */
	@Test(description = "RPMXCON-57059", enabled = true, dataProvider = "Users", groups = { "regression" })
	public void verifyAdvancedSearchWorkingProperlyForDocumentFileTypeNameMetadat(String userName, String password)
			throws Exception {

		String[] columnToSelect = { Input.docFileType };
		String docFileTypeValue = "TIFF image";
		DocListPage docList = new DocListPage(driver);
		List<String> docFileTypeColumnValues = new ArrayList<String>();

		// login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		baseClass.stepInfo("Test case Id: RPMXCON-57059.");
		baseClass.stepInfo("Verify that Advanced Search is working properly for  DocumentFileTypeName Metadata.");

		// performing metaData search with DocFileType
		baseClass.stepInfo("Performing metaData Search With DocFileType");
		sessionSearch.MetaDataSearchInAdvancedSearch(Input.docFileType, Input.searchDocFileType);
		sessionSearch.ViewInDocList();

		// adding the DocFileType metaData column in DocList
		baseClass.stepInfo("adding DocFileType metaData column in DocList.");
		docList.SelectColumnDisplayByRemovingExistingOnes(columnToSelect);
		// getting all the values from DocFileType column
		baseClass.stepInfo("getting all the values from DocFileType column.");
		docFileTypeColumnValues = docList.getColumnValue(columnToSelect[0], false);

		// Verifying that Advanced Search is working properly for DocumentFileTypeName
		// Metadata.
		baseClass.compareListWithString(docFileTypeColumnValues, docFileTypeValue,
				"verified that  Advanced Search is working properly for DocumentFileTypeName Metadata ",
				"Advanced Search is Not working properly for DocumentFileTypeName Metadata");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Description :Verify that Saved Search >> Execute works properly for
	 *              EmailSentDate date/time field with "Range"operator.RPMXCON-57235
	 */

	@Test(description = "RPMXCON-57235", enabled = true, groups = { "regression" })
	public void verifySavedSearchExecuteWorksForEmailSentDateFieldWithRangeOperator() throws InterruptedException {

		assertion = new SoftAssert();
		String metaDataField = "EmailSentDate";
		String operator = "Range";
		String searchName = "savedSearch" + Utility.dynamicNameAppender();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();

		baseClass.stepInfo("Test case Id: RPMXCON-57235 Advanced Search");
		baseClass.stepInfo(
				"Verify that Saved Search >> Execute works properly for EmailSentDate date/time field with \"Range\"operator.");

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// performing metadata search for given search query and saving the search
		// result
		baseClass.stepInfo("performing metadata search for given search query and saving the search result.");
		sessionSearch.navigateToAdvancedSearchPage();
		int PureHits = sessionSearch.advancedMetaDataSearch(metaDataField, operator, dateFormat.format(date),
				dateFormat.format(date));
		sessionSearch.saveAdvancedSearchQuery(searchName);

		// Verify that Saved Search >> Execute works properly for EmailSentDate
		// date/time field
		savedSearch.navigateToSavedSearchPage();
		savedSearch.savedSearch_SearchandSelect(searchName, "Yes");
		savedSearch.savedSearchExecute_Draft(searchName, PureHits);
		baseClass.passedStep("Verified that Saved Search >> Execute works properly for EmailSentDate date/time field.");

		// selecting the saved search and performing Edit action.
		baseClass.stepInfo("selecting the saved search and performing Edit action.");
		baseClass.selectproject();
		savedSearch.navigateToSavedSearchPage();
		savedSearch.savesearch_Edit(searchName);

		// Verify that "EmailSentDate date/time" field search result return documents
		// which satisfied above configured query.
		baseClass.stepInfo("Click on \"Search\" button in session search page.");
		sessionSearch.resubmitSearch();
		int reSubmitPureHits = sessionSearch.returnPurehitCount();
		assertion.assertEquals(reSubmitPureHits, PureHits);
		assertion.assertAll();
		baseClass.passedStep(
				"verified that EmailSentDate date/time field search result return documents which satisfied above configured query.");

		// delete savedSearch
		savedSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Description :Verify that Saved Search >> Execute works properly for
	 *              EmailSentDate date/time field with \"Is\"operator.RPMXCON-57234
	 */

	@Test(description = "RPMXCON-57234", enabled = true, groups = { "regression" })
	public void verifySavedSearchExecuteWorksForEmailSentDateFieldWithISOperator() throws InterruptedException {

		assertion = new SoftAssert();
		String metaDataField = "EmailSentDate";
		String operator = "IS";
		String searchName = "savedSearch" + Utility.dynamicNameAppender();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();

		baseClass.stepInfo("Test case Id: RPMXCON-57234 Advanced Search");
		baseClass.stepInfo(
				"Verify that Saved Search >> Execute works properly for EmailSentDate date/time field with \"Is\"operator.");

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// performing metadata search for given search query and saving the search
		// result
		baseClass.stepInfo("performing metadata search for given search query and saving the search result.");
		sessionSearch.navigateToAdvancedSearchPage();
		int PureHits = sessionSearch.advancedMetaDataSearch(metaDataField, operator, dateFormat.format(date), null);
		sessionSearch.saveAdvancedSearchQuery(searchName);

		// Verify that Saved Search >> Execute works properly for EmailSentDate
		// date/time field
		savedSearch.navigateToSavedSearchPage();
		savedSearch.selectSavedSearch(searchName);
		savedSearch.savedSearchExecute(searchName, PureHits);
		baseClass.passedStep("Verified that Saved Search >> Execute works properly for EmailSentDate date/time field.");

		// selecting the saved search and performing Edit action.
		baseClass.stepInfo("selecting the saved search and performing Edit action.");
		baseClass.selectproject();
		savedSearch.navigateToSavedSearchPage();
		savedSearch.savesearch_Edit(searchName);

		// Verify that "EmailSentDate date/time" field search result return documents
		// which satisfied above configured query.
		baseClass.stepInfo("Click on \"Search\" button in session search page.");
		sessionSearch.resubmitSearch();
		int reSubmitPureHits = sessionSearch.returnPurehitCount();
		assertion.assertEquals(reSubmitPureHits, PureHits);
		assertion.assertAll();
		baseClass.passedStep(
				"verified that EmailSentDate date/time field search result return documents which satisfied above configured query.");

		// delete savedSearch
		savedSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Description :Verify that Saved Search >> Execute works properly for
	 *              CreateDate date/time field with \"Range\"operator.RPMXCON-57233
	 */

	@Test(description = "RPMXCON-57233", enabled = true, groups = { "regression" })
	public void verifySavedSearchExecuteWorksForCreateDateFieldWithRangeOperator() throws InterruptedException {

		assertion = new SoftAssert();
		String metaDataField = "CreateDate";
		String operator = "Range";
		String searchName = "savedSearch" + Utility.dynamicNameAppender();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();

		baseClass.stepInfo("Test case Id: RPMXCON-57233 Advanced Search");
		baseClass.stepInfo(
				"Verify that Saved Search >> Execute works properly for CreateDate date/time field with \"Range\"operator.");

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// performing metadata search for given search query and saving the search
		// result
		baseClass.stepInfo("performing metadata search for given search query and saving the search result.");
		sessionSearch.navigateToAdvancedSearchPage();
		int PureHits = sessionSearch.advancedMetaDataSearch(metaDataField, operator, dateFormat.format(date),
				dateFormat.format(date));
		sessionSearch.saveAdvancedSearchQuery(searchName);

		// Verify that Saved Search >> Execute works properly for CreateDate date/time
		// field
		savedSearch.navigateToSavedSearchPage();
		savedSearch.selectSavedSearch(searchName);
		savedSearch.savedSearchExecute(searchName, PureHits);
		baseClass.passedStep("Verified that Saved Search >> Execute works properly for CreateDate date/time field.");

		// selecting the saved search and performing Edit action.
		baseClass.stepInfo("selecting the saved search and performing Edit action.");
		baseClass.selectproject();
		savedSearch.navigateToSavedSearchPage();
		savedSearch.savesearch_Edit(searchName);

		// Verify that "CreateDate date/time" field search result return documents which
		// satisfied above configured query.
		baseClass.stepInfo("Click on \"Search\" button in session search page.");
		sessionSearch.resubmitSearch();
		int reSubmitPureHits = sessionSearch.returnPurehitCount();
		assertion.assertEquals(reSubmitPureHits, PureHits);
		assertion.assertAll();
		baseClass.passedStep(
				"verified that CreateDate date/time field search result return documents which satisfied above configured query.");

		// delete savedSearch
		savedSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Description : Verify that Saved Search >> Execute works properly for
	 *              CreateDate date/time field with "Is"operator[RPMXCON-57232]
	 */

	@Test(description = "RPMXCON-57232", enabled = true, groups = { "regression" })
	public void verifySavedSearchExecuteWorksForCreateDateFieldWithISOperator() throws InterruptedException {

		assertion = new SoftAssert();
		String metaDataField = "CreateDate";
		String operator = "IS";
		String searchName = "savedSearch" + Utility.dynamicNameAppender();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();

		baseClass.stepInfo("Test case Id: RPMXCON-57232 Advanced Search.");
		baseClass.stepInfo(
				"Verify that Saved Search >> Execute works properly for CreateDate date/time field with \"Is\"operator.");

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// performing metadata search for given search query and saving the search
		// result
		baseClass.stepInfo("performing metadata search for given search query and saving the search result.");
		sessionSearch.navigateToAdvancedSearchPage();
		int PureHits = sessionSearch.advancedMetaDataSearch(metaDataField, operator, dateFormat.format(date), null);
		sessionSearch.saveAdvancedSearchQuery(searchName);

		// Verify that Saved Search >> Execute works properly for CreateDate date/time
		// field
		savedSearch.navigateToSavedSearchPage();
		savedSearch.selectSavedSearch(searchName);
		savedSearch.savedSearchExecute(searchName, PureHits);
		baseClass.passedStep("Verified that Saved Search >> Execute works properly for CreateDate  date/time field.");

		// selecting the saved search and performing Edit action.
		baseClass.stepInfo("selecting the saved search and performing Edit action.");
		baseClass.selectproject();
		savedSearch.navigateToSavedSearchPage();
		savedSearch.savesearch_Edit(searchName);

		// Verify that "CreateDate date/time" field search result return documents which
		// satisfied above configured query.
		baseClass.stepInfo("Click on \"Search\" button in session search page.");
		sessionSearch.resubmitSearch();
		int reSubmitPureHits = sessionSearch.returnPurehitCount();
		assertion.assertEquals(reSubmitPureHits, PureHits);
		assertion.assertAll();
		baseClass.passedStep(
				"verified that CreateDate date/time field search result should return documents which satisfied above configured query.");

		// delete savedSearch
		savedSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author:
	 * @Date: :N/A
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :Verify that all Search options get displayed inside "Content &
	 *              Metadata" block on "Advanced Search" screen.RPMXCON-57236
	 */

	@Test(description = "RPMXCON-57037", enabled = true, groups = { "regression" })
	public void verifySearchOptionsDisplayedContentMetaDataBlock() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-57037 Advanced Search");
		baseClass.stepInfo(
				"Verify that all  Search options get displayed inside \"Content & Metadata\" block on \"Advanced Search\" screen");
		SoftAssert softassert = new SoftAssert();
		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("configure and perform MasterDate metadata search query in Advanced Search page");
		sessionSearch.navigateToSessionSearchPageURL();
		driver.waitForPageToBeReady();
		sessionSearch.getAdvancedSearchLink().waitAndClick(5);
		baseClass.waitForElement(sessionSearch.getContentAndMetaDatabtn());
		sessionSearch.getContentAndMetaDatabtn().waitAndClick(5);
		baseClass.stepInfo("Content & Metadata block is clicked as expected");

		// content metadata options displayed
		baseClass.waitForElement(sessionSearch.getNewSearch_MetadataBtn());
		softassert.assertTrue(sessionSearch.getNewSearch_MetadataBtn().Displayed());
		baseClass.passedStep("Metadata option is displayed successfully");
		baseClass.waitForElement(sessionSearch.getCommentsFieldAndRemarksSec());
		softassert.assertTrue(sessionSearch.getCommentsFieldAndRemarksSec().Displayed());
		baseClass.passedStep("Comments/Remarks option is displayed successfully");
		baseClass.waitForElement(sessionSearch.getOperatorDD());
		softassert.assertTrue(sessionSearch.getOperatorDD().Displayed());
		sessionSearch.getOperatorDD().waitAndClick(5);
		baseClass.passedStep("operator option is displayed successfully");

		baseClass.waitForElement(sessionSearch.getOperatorAND());
		softassert.assertTrue(sessionSearch.getOperatorAND().Displayed());
		baseClass.passedStep("AND operator option is displayed successfully");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sessionSearch.getOperatorOR());
		softassert.assertTrue(sessionSearch.getOperatorOR().Displayed());
		baseClass.passedStep("OR operator option is displayed successfully");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sessionSearch.getOperatorNOT());
		softassert.assertTrue(sessionSearch.getOperatorNOT().Displayed());
		baseClass.waitForElement(sessionSearch.getOperatorNOT());
		baseClass.passedStep("NOT operator option is displayed successfully");
		softassert.assertTrue(sessionSearch.getContentMetaDataBtnDisabled().isElementPresent());
		baseClass.passedStep("Content & Metadata block has been disabled as expected");
		softassert.assertAll();

		// logOut
		loginPage.logout();
	}

	/**
	 * @author:
	 * @Date: :N/A
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :To verify an an PA user login, I will be able to select all the
	 *              Security Groups from Security Group column under Work Product
	 *              tab & set that as a search criteria for advanced
	 *              search.RPMXCON-57047
	 */

	@Test(description = "RPMXCON-57047", enabled = true, groups = { "regression" })
	public void verifySgWpTabSearchCriteriaAdvancedSearch() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-57047 Advanced Search");
		// login as PAU
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PAU as with " + Input.pa1userName + "");
		baseClass.stepInfo(
				"To verify an an PA user login, I will be able to select all the Security Groups from Security Group column under Work Product tab & set that as a search criteria for advanced search");
		sessionSearch.switchToWorkproduct();
		baseClass.waitForElement(sessionSearch.getSecurityGrpBtn());
		sessionSearch.getSecurityGrpBtn().waitAndClick(5);
		baseClass.waitTime(5);
		baseClass.waitForElement(sessionSearch.getSelectAllSecurityGroupBtn());
		sessionSearch.getSelectAllSecurityGroupBtn().waitAndClick(5);
		baseClass.stepInfo("All Security Groups is selected from Security Group list as expected");
		driver.waitForPageToBeReady();
		List<String> Securitygroups = baseClass.availableListofElements(sessionSearch.getSecurityNamesTree());
		baseClass.stepInfo(Securitygroups + "  Present in workproduct SG list");
		driver.scrollingToBottomofAPage();
		baseClass.waitForElement(sessionSearch.getMetaDataInserQuery());
		sessionSearch.getMetaDataInserQuery().waitAndClick(5);
		baseClass.stepInfo("Insert Into Query is Clicked");
		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		String Searchbox = sessionSearch.getEnterSearchBox().getText();
		for (int i = 0; i < Securitygroups.size(); i++) {
			if (Searchbox.contains(Securitygroups.get(i))) {
				baseClass.passedStep(Securitygroups.get(i)
						+ "Selected Security Group has been  inserted in search criteria for advanced search  as expected");
			} else {
				baseClass.failedStep(
						"Selected Security Group is did not inserted in search criteria for advanced search ");
			}
		}
		loginPage.logout();

	}

	/**
	 * @author
	 * @Description :Verify that Saved Search >> Execute works properly for
	 *              MasterDate date/time field with \"Range\"operator.RPMXCON-57231
	 */
	@Test(description = "RPMXCON-57231", enabled = true, groups = { "regression" })
	public void verifySavedSearchExecuteWorksForMasterDateFieldWithRangeOperator() throws InterruptedException {

		assertion = new SoftAssert();
		String metaDataField = "MasterDate";
		String operator = "Range";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();
		String searchName = "savedSearch" + Utility.dynamicNameAppender();
		baseClass.stepInfo("Test case Id: RPMXCON-57231 Advanced Search");
		baseClass.stepInfo(
				"Verify that Saved Search >> Execute works properly for MasterDate date/time field with \"Range\"operator.");

		// login
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// performing metadata search for given search query and saving the search
		// result
		baseClass.stepInfo("performing metadata search for given search query and saving the search result.");
		sessionSearch.navigateToAdvancedSearchPage();
		int PureHits = sessionSearch.advancedMetaDataSearch(metaDataField, operator, dateFormat.format(date),
				dateFormat.format(date));
		sessionSearch.saveAdvancedSearchQuery(searchName);

		// Verify that Saved Search >> Execute works properly for MasterDate date/time
		// field
		savedSearch.navigateToSavedSearchPage();
		savedSearch.selectSavedSearch(searchName);
		savedSearch.savedSearchExecute(searchName, PureHits);
		baseClass.passedStep("Verified that Saved Search >> Execute works properly for MasterDate date/time field.");

		// selecting the saved search and performing Edit action.
		baseClass.stepInfo("selecting the saved search and performing Edit action.");
		baseClass.selectproject();
		savedSearch.navigateToSavedSearchPage();
		savedSearch.savesearch_Edit(searchName);

		// Verify that MasterDate date/time field search result return documents which
		// satisfied above configured query.
		baseClass.stepInfo("Click on \"Search\" button in session search page.");
		sessionSearch.resubmitSearch();
		int reSubmitPureHits = sessionSearch.returnPurehitCount();
		assertion.assertEquals(reSubmitPureHits, PureHits);
		assertion.assertAll();
		baseClass.passedStep(
				"verified that MasterDate date/time field search result return documents which satisfied above configured query.");

		// delete savedSearch
		savedSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Description :Verify that Saved Search >> Execute works properly for
	 *              MasterDate date/time field with \"Is\"operator.RPMXCON-57230
	 */

	@Test(description = "RPMXCON-57230", enabled = true, groups = { "regression" })
	public void verifySavedSearchExecuteWorksForMasterDateFieldWithISOperator() throws InterruptedException {

		assertion = new SoftAssert();
		String metaDataField = "MasterDate";
		String operator = "IS";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();
		String searchName = "savedSearch" + Utility.dynamicNameAppender();
		baseClass.stepInfo("Test case Id: RPMXCON-57230 Advanced Search.");
		baseClass.stepInfo(
				"Verify that Saved Search >> Execute works properly for MasterDate date/time field with \"Is\"operator.");

		// login
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// performing metadata search for given search query and saving the search
		// result
		baseClass.stepInfo("performing metadata search for given search query and saving the search result.");
		sessionSearch.navigateToAdvancedSearchPage();
		int PureHits = sessionSearch.advancedMetaDataSearch(metaDataField, operator, dateFormat.format(date), null);
		sessionSearch.saveAdvancedSearchQuery(searchName);

		// Verify that Saved Search >> Execute works properly for MasterDate date/time
		// field
		savedSearch.navigateToSavedSearchPage();
		savedSearch.selectSavedSearch(searchName);
		savedSearch.savedSearchExecute(searchName, PureHits);
		baseClass.passedStep("Verified that Saved Search >> Execute works properly for MasterDate date/time field.");

		// selecting the saved search and performing Edit action.
		baseClass.stepInfo("selecting the saved search and performing Edit action.");
		baseClass.selectproject();
		savedSearch.navigateToSavedSearchPage();
		savedSearch.savesearch_Edit(searchName);

		// Verify that "MasterDate date/time" field search result return documents which
		// satisfied above configured query.
		baseClass.stepInfo("Click on \"Search\" button in session search page.");
		sessionSearch.resubmitSearch();
		int reSubmitPureHits = sessionSearch.returnPurehitCount();
		assertion.assertEquals(reSubmitPureHits, PureHits);
		assertion.assertAll();
		baseClass.passedStep(
				"verified that MasterDate date/time field search result return documents which satisfied above configured query.");

		// delete savedSearch
		savedSearch.deleteSearch(searchName, Input.mySavedSearch, "Yes");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Description :Verify that Advanced Search works properly for EmailSentDate
	 *              date/time field with \"Range\" operator.RPMXCON-57227
	 */

	@Test(description = "RPMXCON-57227", enabled = true, groups = { "regression" })
	public void verifyAdvancedSearchWorksProperlyForEmailSentDateFieldWithRangeOperator() {

		String metaDataField = "EmailSentDate";
		String operator = "Range";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();

		baseClass.stepInfo("Test case Id: RPMXCON-57227 Advanced Search.");
		baseClass.stepInfo(
				"Verify that Advanced  Search works properly for EmailSentDate  date/time field  with \"Range\" operator.");

		// login
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Configure MetaData Query in Advanced Search with MetaData as 'EmailSentDate'
		// and Operator as 'Range'
		baseClass.stepInfo(
				"Configure  MetaData Query in Advanced Search with MetaData as 'EmailSentDate' and Operator as 'Range'.");
		sessionSearch.advancedMetaDataForDraft(metaDataField, operator, dateFormat.format(date),
				dateFormat.format(date));

		// perform search action and Verify that "EmailSentDate date/time" field search
		// result return documents which satisfied above configured query.
		baseClass.stepInfo("perform search action");
		sessionSearch.serarchWP();
		baseClass.passedStep(
				"verified that EmailSentDate date/time field search result return documents which satisfied above configured query.");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Description :Verify that Advanced Search works properly for EmailSentDate
	 *              date/time field with \"Is\" operator.RPMXCON-57226
	 */

	@Test(description = "RPMXCON-57226", enabled = true, groups = { "regression" })
	public void verifyAdvancedSearchWorksProperlyForEmailSentDateFieldWithISOperator() {

		String metaDataField = "EmailSentDate";
		String operator = "IS";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();

		baseClass.stepInfo("Test case Id: RPMXCON-57226 Advanced Search.");
		baseClass.stepInfo(
				"Verify that Advanced  Search works properly for EmailSentDate date/time field with \"Is\" operator.");

		// login
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Configure MetaData Query in Advanced Search with MetaData as 'EmailSentDate'
		// and Operator as 'IS'
		baseClass.stepInfo(
				"Configure  MetaData Query in Advanced Search with MetaData as 'EmailSentDate' and Operator as 'IS'.");
		sessionSearch.advancedMetaDataForDraft(metaDataField, operator, dateFormat.format(date), null);

		// perform search action and Verify that "EmailSentDate date/time" field search
		// result return documents which satisfied above configured query.
		baseClass.stepInfo("perform search action");
		sessionSearch.serarchWP();
		baseClass.passedStep(
				"verified that EmailSentDate date/time field search result return documents which satisfied above configured query.");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Description :Verify that configured query with MasterDate get inserted
	 *              properly into Advanced Search Query builder screen.RPMXCON-57054
	 */
	@Test(description = "RPMXCON-57054", enabled = true, groups = { "regression" })
	public void verifyConfiguredQueryWithMasterDateInsertedProperlyIntoAdvancedSearchQueryBuilderScreen() {

		String[] metaDataField = { "MasterDate", "CreateDate" };
		String operator = "IS";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();
		String searchString = dateFormat.format(date);

		baseClass.stepInfo("Test case Id: RPMXCON-57054 Advanced Search.");
		baseClass.stepInfo(
				"Verify that configured query with MasterDate get inserted properly into Advanced Search Query builder screen.");

		// login
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo(
				"Configure  MetaData Query in Advanced Search with MetaData as 'MasterDate' and Operator as 'IS'.");

		// Configure MetaData Query in Advanced Search with MetaData as 'MasterDate' &
		// 'CraeteDate'
		// and Operator as 'IS'
		for (int i = 0; i < metaDataField.length; i++) {

			baseClass.selectproject();
			sessionSearch.advancedMetaDataForDraft(metaDataField[i], operator, searchString, null);

			// Verify that configured query with MasterDate get inserted properly into
			// Advanced Search Query builder screen
			String configuredQuery = sessionSearch.getQueryTextArea().getText();
			baseClass.compareTextViaContains(configuredQuery, metaDataField[i],
					"metaData : '" + metaDataField[i] + "' is present in the Configured Query : '" + configuredQuery
							+ "' in Query Bulider Screen.",
					"metaData : '" + metaDataField[i] + "' is Not present in the Configured Query : '" + configuredQuery
							+ "' in Query Bulider Screen");
			baseClass.compareTextViaContains(configuredQuery, searchString,
					"Expected Search Query : '" + searchString + "' is present in the Configured Query : '"
							+ configuredQuery + "' in Query Bulider Screen.",
					"Expected Search Query : '" + metaDataField[i] + "' is Not present in the Configured Query : '"
							+ configuredQuery + "' in Query Bulider Screen.");
			baseClass.passedStep(
					"Verified that Configured query with Metadata should get inserted properly into Advanced Search Query builder screen.");
		}

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Description : Verify that Advanced Search works properly for MasterDate
	 *              field with \"Is\" operator and NOT having time
	 *              components.[RPMXCON-49169]
	 */

	@Test(description = "RPMXCON-49169", enabled = true, groups = { "regression" })
	public void verifiedAdancedSearchWorkProperlyForMasterDatewithISOperatorAndNotHavingTimeComponents() {

		String metaDataField = "MasterDate";
		String operator = "IS";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		baseClass.stepInfo("Test case Id: RPMXCON-49169 Advanced Search.");
		baseClass.stepInfo(
				"Verify that Advanced  Search works properly for 'MasterDate' field with \"Is\" operator and NOT having time components.");

		// login
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// configuring MetaData Search Query with metaData as MasterDate and Operator as
		// 'IS'.
		baseClass.stepInfo("configuring MetaData Search Query with metaData as MasterDate and Operator as 'IS'.");
		sessionSearch.navigateToAdvancedSearchPage();
		sessionSearch.advancedMetaDataForDraft(metaDataField, operator, dateFormat.format(date), null);

		// Click on Search and Verify that "MasterDate" field search result return
		// documents which satisfied above configured query.
		baseClass.stepInfo("Click on 'Search' button");
		sessionSearch.serarchWP();
		baseClass.passedStep(
				"Verified that \"MasterDate\" field search result return documents which satisfied above configured query.");

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @Description : verify that Proximity Queries along with Regular Expression
	 *              search in Korean should return the result correctly in Advanced
	 *              Search.[RPMXCON-62495]
	 */

	@Test(description = "RPMXCON-62495", enabled = true, groups = { "regression" })
	public void verifyProximityQueriesWithRegularExpressionSearchInKoreanReturnResultCorrectlyInAdvancedSearch()
			throws InterruptedException {

		String searchString = "\"(\"##[0-9]{4}\") 월\"~4";

		baseClass.stepInfo("Test case Id: RPMXCON-62495 Advanced Search.");
		baseClass.stepInfo(
				"verify that Proximity Queries along with Regular Expression search in Korean should return the result correctly in Advanced Search.");

		// login
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// configure search Query
		sessionSearch.advanedContentDraftSearch(searchString);
		baseClass.stepInfo("Search Query configured.");

		// Click on "Search" button
		baseClass.stepInfo("Clicking on 'Search' button.");
		//sessionSearch.SearchBtnAction(); //Commenting this because search action is been handled in 'verifyWarningMessage' methods

		// verify that application displays Proximity warning message
		sessionSearch.verifyWarningMessage(true, true, 5);
		baseClass.passedStep("verified that application displays Proximity warning message.");

		// Click on "Yes" button and Verify that correct result appears when combine
		// Proximity Queries and Regular Expression in Advanced Search Query Screen.
		sessionSearch.tallyContinue(5);
		sessionSearch.returnPurehitCount();
		baseClass.passedStep(
				"verified that correct result appears when combine Proximity Queries and Regular Expression in Advanced Search Query Screen.");

		// logOut
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: 01/07/2022 TestCase Id:RPMXCON-57158 Description
	 * :Verify that - Application returns all the documents which are available
	 * under selected group with OR operator and production optional filters -
	 * status in search result.
	 * 
	 */
	@Test(description = "RPMXCON-57158", groups = { "regression" })
	public void verifyDocumentWithORProdOptFilters() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-57158 ");
		baseClass.stepInfo(
				"Verify that - Application returns all the documents which are available under selected group with OR operator and production optional filters - status  in search result");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		sessionSearch.switchToWorkproduct();
		baseClass.stepInfo("Configure query with security group OR production");
		sessionSearch.configureQueryWithSecurityGroupAndProductionStatus(Input.securityGroup, "OR", false);
		baseClass.stepInfo("Search query and verify search result");
		sessionSearch.verifyDocsCountAvailableInSgWithSearchResult();
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: 01/07/2022 TestCase Id:RPMXCON-57159 Description
	 * :Verify that - Application returns all the documents which are available
	 * under selected group with OR operator and production optional filters - Date
	 * Range in search result.
	 * 
	 */
	@Test(description = "RPMXCON-57159", groups = { "regression" })
	public void verifyDocumentWithORProdOptFiltersDateRange() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-57159 ");
		baseClass.stepInfo(
				"Verify that - Application returns all the documents which are available under selected group with OR operator and production optional filters - Date Range in search result");
		// login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		sessionSearch.switchToWorkproduct();
		baseClass.stepInfo("Configure query with security group OR production status with date");
		sessionSearch.configureQueryWithSecurityGroupAndProductionStatus(Input.securityGroup, "OR", true);
		baseClass.stepInfo("Search query and verify search result");
		sessionSearch.verifyDocsCountAvailableInSgWithSearchResult();
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: 04/07/2022 TestCase Id:RPMXCON-57258 Description
	 * :Verify that Dropped tiles are retained in shopping cart when User Navigates
	 * Advanced Search (Pure Hit) >> "View in Doc View" screen and Come back to
	 * Search Page.
	 * 
	 */
	@Test(description = "RPMXCON-57258", groups = { "regression" })
	public void verifyDroppedTileRetainedWhenNavigateToDocViewPage() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-57258");
		baseClass.stepInfo(
				"Verify that Dropped tiles are retained in shopping cart when User Navigates Advanced Search (Pure Hit) >> \"View in Doc View\" screen and Come back to Search Page.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Perform search with metadata");
		sessionSearch.advancedMetaDataSearch(Input.metaDataName, null, Input.custodianName_Andrew, null);
		baseClass.stepInfo("Add pure hit and navigate to docview page");
		sessionSearch.viewInDocView();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Navigate back to search page and verify tile status");
		sessionSearch.navigateToSearchPageAndVerifyTileStatus();
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: 04/07/2022 TestCase Id:RPMXCON-57254 Description
	 * :Verify that Dropped tiles are retained in shopping cart when User Navigates
	 * Advanced Search (Pure Hit) >> "Analyze in Concept Explorer" screen and Come
	 * back to Search Page.
	 * 
	 */
	@Test(description = "RPMXCON-57254", groups = { "regression" })
	public void verifyDroppedTileRetainedWhenNavigateToConceptExplorerPage() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-57254");
		baseClass.stepInfo(
				"Verify that Dropped tiles are retained in shopping cart when User Navigates Advanced Search (Pure Hit) >> \"Analyze in Concept Explorer\" screen and Come back to Search Page.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Perform search with metadata");
		sessionSearch.advancedMetaDataSearch(Input.metaDataName, null, Input.custodianName_Andrew, null);
		baseClass.stepInfo("Add pure hit and navigate to concept explorer page");
		sessionSearch.addPurehitAndActionAsConceptOrComm(false, true);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Navigate back to search page and verify tile status");
		sessionSearch.navigateToSearchPageAndVerifyTileStatus();
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: 04/07/2022 TestCase Id:RPMXCON-57261 Description
	 * :Verify that Dropped tiles are retained in shopping cart when User Navigates
	 * Basic Search (Pure Hit) >> "Manage >> Assignments" screen and Come back to
	 * Search Page.
	 * 
	 */
	@Test(description = "RPMXCON-57261", groups = { "regression" })
	public void verifyDroppedTileRetainedWhenNavigateToAssignmentsPage() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-57261");
		baseClass.stepInfo(
				"Verify that Dropped tiles are retained in shopping cart when User Navigates Basic Search (Pure Hit) >> \"Manage >> Assignments\" screen and Come back to Search Page.");
		// Login as PA
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Perform search with metadata");
		sessionSearch.basicSearchWithMetaDataQuery(Input.custodianName_Andrew, Input.metaDataName);
		baseClass.stepInfo("Add pure hit and navigate to docview page");
		sessionSearch.addPureHitAndNavigate("Assignment");
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Navigate back to search page and verify tile status");
		sessionSearch.navigateToSearchPageAndVerifyTileStatus();
		loginPage.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 */
	@Test(description = "RPMXCON-57305", dataProvider = "Users")
	public void verifyWarningForTwoWordsWithOR(String username, String password) throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-57305");
		baseClass.stepInfo("Verify that result appears for proximity having "
				+ "2 words with OR in Advanced Search Query Screen.");
		SoftAssert assertion = new SoftAssert();
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged in as " + username);
		String eleValue[] = { "“ProximitySearch (Truthful OR Recall)”~9", "\"ProximitySearch (Truthful OR Recall)\"~9",
				"“ProximitySearch (Truthful OR Recall)”~9" };
		driver.scrollingToBottomofAPage();
		for (int i = 0; i < eleValue.length; i++) {
			sessionSearch.wrongQueryAlertAdvanceSaerch(eleValue[i], 13, "non-fielded", null);
			assertion.assertNotNull(sessionSearch.verifyPureHitsCount(),
					"purehit is null after entering two proximity term with OR ");
			baseClass.passedStep(
					"Pure hit count is not null and value displayed is " + sessionSearch.verifyPureHitsCount());
			if (i != 2) {
				baseClass.selectproject();
			}
		}
		baseClass.passedStep("Sucessfully Verified  that result appears for proximity having  "
				+ "2 words with OR in Advanced Search Query Screen.");
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: 07/07/2022 TestCase Id:RPMXCON-49306 Description
	 * :Verify that Conceptual tile return the result for WorkProduct >> Security
	 * Group Search in Advanced Search Screen.
	 * 
	 */
	@Test(description = "RPMXCON-49306", groups = { "regression" })
	public void verifyConceptualTileForSecurityGroupQuery() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49306");
		baseClass.stepInfo(
				"Verify that Conceptual tile return the result for WorkProduct >> Security Group  Search in Advanced Search Screen.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Configure query with workproduct-securitygroup and search");
		sessionSearch.switchToWorkproduct();
		sessionSearch.selectSecurityGinWPS(Input.securityGroup);
		baseClass.passedStep("Inserted query");
		sessionSearch.serarchWP();
		baseClass.stepInfo("verify query search result and conceptual tile return result");
		sessionSearch.verifySearchResultAndConceptualTileReturnResult();
		loginPage.logout();

	}

	/**
	 * Author :Arunkumar date: 07/07/2022 TestCase Id:RPMXCON-49310 Description
	 * :Verify that Conceptual tile return the result for WorkProduct >> Assignments
	 * Search in Advanced Search Screen
	 * 
	 */
	@Test(description = "RPMXCON-49310", groups = { "regression" })
	public void verifyConceptualTileForAssignmentsQuery() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49310");
		baseClass.stepInfo(
				"Verify that Conceptual tile return the result for WorkProduct >> Security Group  Search in Advanced Search Screen.");
		String assignmentName = "Aassignment" + Utility.dynamicNameAppender();
		// Login as PA
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Bulk assign docs to new assignment");
		AssignmentsPage assignPage = new AssignmentsPage(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkAssignWithNewAssignment();
		assignPage.createAssignmentByBulkAssignOperation(assignmentName, Input.codeFormName);
		baseClass.selectproject();
		baseClass.stepInfo("Configure query with workproduct-Assignment");
		sessionSearch.switchToWorkproduct();
		sessionSearch.selectAssignmentInWPS(assignmentName);
		baseClass.passedStep("Inserted query");
		sessionSearch.serarchWP();
		baseClass.stepInfo("verify query search result and conceptual tile return result");
		sessionSearch.verifySearchResultAndConceptualTileReturnResult();
		loginPage.logout();

	}

	/**
	 * Author :Arunkumar date: 29/06/2022 TestCase Id:RPMXCON-57148 Description
	 * :Verify that application displays correct options for "DocDateDateOnly" field
	 * on Advanced Search screen.
	 */
	@Test(description = "RPMXCON-57148", groups = { "regression" })
	public void verifyDocDateOnlyFieldOnAdvancedSearch() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-57148");
		baseClass.stepInfo("Verify 'DocDateDateOnly' field on advanced search");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		sessionSearch.navigateToAdvancedMetaDataSearch();
		// Verify the options available
		sessionSearch.verifyOptionsDisplayForDocDateDateOnlyField();
		loginPage.logout();

	}

	/**
	 * Author :Arunkumar date: 29/06/2022 TestCase Id:RPMXCON-57043 Description :To
	 * verify an an PA user login, I will be able to select all Folder from Folder
	 * column under Work Product tab & set that as a search criteria for advanced
	 * search
	 * 
	 */
	@Test(description = "RPMXCON-57043", groups = { "regression" })
	public void verifyAllFolderSelectionUnderWorkProduct() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-57043");
		baseClass.stepInfo("Verify all folder selection under work product and set as search criteria");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Navigate to advanced search from search page");
		sessionSearch.advanceSearchByFolder("All Folders");
		if (sessionSearch.getSearchCriteriaValue().isElementAvailable(10)) {
			baseClass.passedStep("Able to select all folder under work product and set as search criteria");
		} else {
			baseClass.failedStep("unable to set all folder as search criteria");
		}
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: 30/06/2022 TestCase Id:RPMXCON-57256 Description
	 * :Verify that Dropped tiles are retained in shopping cart when User Navigates
	 * Advanced Search (Pure Hit) >> "Manage >> Categorize" screen and Come back to
	 * Search Page.
	 * 
	 */
	@Test(description = "RPMXCON-57256", groups = { "regression" })
	public void verifyDroppedTileRetainedWhenNavigateToCategorize() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-57256");
		baseClass.stepInfo(
				"Verify that Dropped tiles are retained in shopping cart when User Navigates Advanced Search (Pure Hit) >> \"Manage >> Categorize\" screen and Come back to Search Page.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Perform search with metadata");
		sessionSearch.advancedMetaDataSearch(Input.metaDataName, null, Input.custodianName_Andrew, null);
		baseClass.stepInfo("Add pure hit and navigate to categorize page");
		sessionSearch.addPureHitAndNavigate("Categorize");
		baseClass.stepInfo("Navigate back to search page and verify tile status");
		sessionSearch.navigateToSearchPageAndVerifyTileStatus();
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: 30/06/2022 TestCase Id:RPMXCON-57257 Description
	 * :Verify that Dropped tiles are retained in shopping cart when User Navigates
	 * Advanced Search (Pure Hit) >> "Manage >>Users" screen and Come back to Search
	 * Page.
	 * 
	 */
	@Test(description = "RPMXCON-57257", groups = { "regression" })
	public void verifyDroppedTileRetainedWhenNavigateToUserPage() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-57257");
		baseClass.stepInfo(
				"Verify that Dropped tiles are retained in shopping cart when User Navigates Advanced Search (Pure Hit) >> \"Manage >>Users\" screen and Come back to Search Page.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Perform search with metadata");
		sessionSearch.advancedMetaDataSearch(Input.metaDataName, null, Input.custodianName_Andrew, null);
		baseClass.stepInfo("Add pure hit and navigate to users page");
		sessionSearch.addPureHitAndNavigate("Users");
		baseClass.stepInfo("Navigate back to search page and verify tile status");
		sessionSearch.navigateToSearchPageAndVerifyTileStatus();
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: 07/07/2022 TestCase Id:RPMXCON-57081 Description
	 * :Verify that Bulk Release Action is working properly on Advanced Search
	 * result Screen
	 * 
	 */
	@Test(description = "RPMXCON-57081", groups = { "regression" })
	public void verifyBulkReleaseOnAdvancedSearch() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-57081");
		baseClass.stepInfo("Verify that Bulk Release Action is working properly on Advanced Search result Screen");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Navigate to advanced search and search query");
		sessionSearch.advancedContentSearch(Input.testData1);
		baseClass.stepInfo("Add purehit to shopping cart and perform bulk release");
		sessionSearch.bulkRelease(Input.securityGroup);
		baseClass.passedStep("Bulk release action performed successfully on advanced search");
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: 07/07/2022 TestCase Id:RPMXCON-57062 Description
	 * :Verify that Advanced Search is working properly for MasterDate Metadata with
	 * Range Operator
	 * 
	 */
	@Test(description = "RPMXCON-57062", dataProvider = "Users", groups = { "regression" })
	public void verifyMasterDateMetaDataWithRange(String username, String password) throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-57062");
		baseClass.stepInfo(
				"Verify that Advanced Search is working properly for MasterDate Metadata with Range Operator");
		// Login as PA
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("perform 'MasterDate' metadata search with Range operator");
		sessionSearch.advancedMetaDataSearch(Input.masterDateText, Input.range, "2021-01-01", "2022-01-01");
		baseClass.stepInfo("verify search result for query and master date details");
		sessionSearch.verifyMasterDateSearchResults(Input.yearRange1, Input.yearRange2);
		loginPage.logout();
	}

	/**
	 * @Author Jayanthi
	 * @description:To Verify Regression Issue: After bulk tagging, Session search
	 *                 with workproduct 'Tag' is not working:1
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-49048", enabled = true, groups = { "regression" })
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

		baseClass.stepInfo(
				"**Step-7 Navigate to Advanced search and Select \"Remarks\"  from \"Comment Fields / Remarks\" combo box**");
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

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that warning message appears after pre-submittal
	 *              validations WP on Advanced Search Screen. RPMXCON-57280
	 */
	@Test(description = "RPMXCON-57280", enabled = true, groups = { "regression" })
	public void verifyWarningMessageAppearsAfterPreSubmittalValidationsWPAdv() {

		baseClass.stepInfo("Test case Id: RPMXCON-57280 Advanced Search");
		baseClass.stepInfo(
				"Verify that warning message appears after pre-submittal validations  WP on Advanced Search Screen.");

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// navigating to advanced search page and Configuring the search Query in
		// WorkProduct Tab
		sessionSearch.switchToWorkproduct();
		sessionSearch.getWorkProductTextBox().SendKeys(Input.searchString1);
		baseClass.stepInfo("navigating to advanced search page and Configuring the search Query in WorkProduct Tab.");

		// perform search action
		sessionSearch.SearchBtnAction();
		baseClass.stepInfo("perform search action.");

		// Verify that application displays warning message
		String expectedWarningMessage = "Please enter a valid search expression";
		baseClass.VerifyWarningMessage(expectedWarningMessage);
		baseClass.passedStep("Verified that application displays warning message.");
		baseClass.printResutInReport(null, expectedWarningMessage, expectedWarningMessage, expectedWarningMessage);

		// logOut
		loginPage.logout();
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
		assertion.assertEquals((boolean) sessionSearch.getPureHitsCountNumText().isDisplayed(), false);
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
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
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
		//sessionSearch.searchAndReturnPureHit_BS(); //Commented this line and added searchSavedSearchResult to perform the correctSearch
		sessionSearch.searchSavedSearchResult(searchGroup);
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
	 * Author :Arunkumar date: 11/10/2022 TestCase Id:RPMXCON-49772 Description
	 * :Advanced Search- Verify that when documents are released all email
	 * concatenated field values should be displayed
	 * 
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-49772", enabled = true, groups = { "regression" })
	public void verifyConcatenatedValuesInDoclist() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-49772");
		baseClass.stepInfo("verify email concatenated field values should be displayed");
		IngestionPage_Indium ingestionPage = new IngestionPage_Indium(driver);
		securityGroupsPage = new SecurityGroupsPage(driver);
		docViewRedact = new DocViewRedactions(driver);

		String[] values = { "EmailAuthorNameAndAddress", "EmailBCCNamesAndAddresses", "EmailCCNamesAndAddresses",
				"EmailToNamesAndAddresses" };
		String[] userName = { Input.rmu1userName, Input.rev1userName };
		String[] password = { Input.rmu1password, Input.rev1password };
		String securityGroup = "securityGroup" + Utility.dynamicNameAppender();

		// pre-requisite - Docs related to ingestion released to security group
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		securityGroupsPage.createSecurityGroups(securityGroup);
		docViewRedact.assignAccesstoSGs(securityGroup, Input.rmu1userName);
		docViewRedact.assignAccesstoSGs(securityGroup, Input.rev1userName);
		baseClass.stepInfo("Release docs to security group");
		ingestionPage.navigateToDataSetsPage();
		DataSets dataset = new DataSets(driver);
		String ingestionName = dataset.isDataSetisAvailable(Input.EmailConcatenatedDataFolder);
		sessionSearch.MetaDataSearchInBasicSearch(Input.metadataIngestion, ingestionName);
		sessionSearch.bulkRelease(securityGroup);
		loginPage.logout();
		// Login as RMU/Rev and verify
		for (int i = 0; i < userName.length; i++) {
			loginPage.loginToSightLine(userName[i], password[i]);
			baseClass.stepInfo("Logged in as :" + userName[i]);
			baseClass.selectsecuritygroup(securityGroup);
			baseClass.stepInfo("Search for all docs and action as view in doclist");
			int count = sessionSearch.advancedContentSearch(Input.searchStringStar);
			baseClass.stepInfo("total docs in EmailConcatenatedDataFolder: " + count);
			sessionSearch.ViewInDocList();
			baseClass.stepInfo("Add the columns and verify email concatenated field values displayed");
			ingestionPage.verifyValuesDisplayedInSelectedColumnsDoclist(count, values);
			baseClass.passedStep("Email concatenated field values displayed");
			baseClass.selectsecuritygroup(Input.securityGroup);
			loginPage.logout();
		}
		// Delete security group
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
