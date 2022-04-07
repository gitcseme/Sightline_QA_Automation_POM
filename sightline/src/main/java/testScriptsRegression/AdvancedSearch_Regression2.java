 package testScriptsRegression;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CustomDocumentDataReport;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.RedactionPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class AdvancedSearch_Regression2 {

	Driver driver;
	LoginPage loginPage;
	SessionSearch search;
	SoftAssert softAssertion;
	AssignmentsPage assgnPage;
	SavedSearch savedSearch;
	TagsAndFoldersPage tagPage;
	int pureHit;
	BaseClass baseClass;
	ArrayList<String> reviwersListInAssgnPg;
	ArrayList<String> reviwersListInSearchPg;
	String TagName = "ASTag" + Utility.dynamicNameAppender();
	String FolderName = "ASFolder" + Utility.dynamicNameAppender();
	String folderName = "ASFolder" + Utility.dynamicNameAppender();
	String folderName_2 = "ASFolder" + Utility.dynamicNameAppender();
	String searchText = Input.searchString1;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		Input in = new Input();
		in.loadEnvConfig();
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		search = new SessionSearch(driver);
		assgnPage = new AssignmentsPage(driver);
		savedSearch = new SavedSearch(driver);
		tagPage = new TagsAndFoldersPage(driver);
		softAssertion = new SoftAssert();

	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description To verify as an user login into the Application, user will be
	 *              able to search based on Remarks text on Content & Metadata in
	 *              basic search Modified on : 10/26/21 - DataProvider name and try
	 *              block
	 */
	 @Test(dataProvider = "UsersWithoutPA", groups = { "regression" }, priority =
	 1,enabled = true)
	public void verifyRemarks(String username, String password) throws InterruptedException {
		String RemarksName = "Remarks" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(username, password);
		baseClass.selectproject();
		baseClass.stepInfo(
				"To verify as an user login into the Application, user will be able to search based on Remarks "
						+ "text on Content & Metadata in basic search");
		baseClass.stepInfo("Test case Id: RPMXCON-47779");
		search.basicContentSearch("null");
		search.ViewInDocView();
		search.createRemarks(RemarksName);
		try {
			baseClass.selectproject();
			int count = search.getCommentsOrRemarksCount("Remark", RemarksName);
			softAssertion.assertEquals(1, count);
			softAssertion.assertAll();
			baseClass.passedStep(
					"sucessfully verified whether a user will be able to search based on Remarks text on Content & Metadata"
							+ " in basic search  ");
		} catch (Exception e) {
			e.printStackTrace();
			baseClass.failedStep("A user will be unable to search based on Remarks text on Content & Metadata"
					+ " in basic search.Assertion failed ");
		}
		loginPage.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description:To verify as an user login into the Application, User will be
	 *                 able to edit the advanced search saved query & able to search
	 *                 and save the query RPMXCON-47770
	 */
	@Test(description ="RPMXCON-47770",dataProvider = "Users", groups = { "regression" }, priority = 2, enabled = true)
	public void validateSearchAndSavedTheModifiedQuery(String username, String password)
			throws InterruptedException, ParseException, IOException {
		baseClass.stepInfo("Test case Id: RPMXCON-47770");
		baseClass.stepInfo("To verify as an user login into the Application, User will be able "
				+ "to edit the advanced search saved query & able to search and save the query");
		loginPage.loginToSightLine(username, password);
		String savedSearchName = "test" + Utility.dynamicNameAppender();
		String savedSearchName_2 = "test" + Utility.dynamicNameAppender();
		baseClass.selectproject();
		search.MetaDataSearchInAdvancedSearch(Input.metaDataName, Input.custodianName_Andrew);
		search.saveSearchAdvanced(savedSearchName);
		savedSearch.savesearch_Edit(savedSearchName);
		search.clearExistingQueryAndSearchNewQuery(Input.metaDataName, Input.custodianName_allen);
		search.saveAdvancedSearchQuery(savedSearchName_2);
		if (username == Input.pa1userName) {
			baseClass.stepInfo(
					"After the saved query edited in saved search page, modified query is search and saved using PA user");
		}
		if (username == Input.rmu1userName) {
			baseClass.stepInfo(
					"After the saved query edited in saved search page, modified query is search and saved using RMU user");
		}
		if (username == Input.rev1userName) {
			baseClass.stepInfo(
					"After the saved query edited in saved search page, modified query is search and saved usingg Reviewer");
		}
		savedSearch.savesearch_Edit(savedSearchName_2);
		savedSearch.verifyTheNavigationOfAS(Input.custodianName_allen);
		if (username == Input.pa1userName) {
			baseClass.stepInfo(
					"Saved modified query in saved search page is successfully validated in advance search page using PA user");
		}
		if (username == Input.rmu1userName) {
			baseClass.stepInfo(
					"Saved modified query in saved search page is successfully validated in advance search page using RMU user");
		}
		if (username == Input.rev1userName) {
			baseClass.stepInfo(
					"Saved modified query in saved search page is successfully validated in advance search page using Reviewer");
		}
		savedSearch.SaveSearchDelete(savedSearchName);
		savedSearch.SaveSearchDelete(savedSearchName_2);
		loginPage.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description:To verify as an user login into the Application, User will be
	 *                 able to edit the advanced search saved query & after execute
	 *                 user will be able to save the query RPMXCON-47771
	 */
	@Test(description ="RPMXCON-47771",dataProvider = "Users", groups = { "regression" }, priority = 3, enabled = true)
	public void validateSavedSearchModifiedQuery(String username, String password)
			throws InterruptedException, ParseException, IOException {
		baseClass.stepInfo("Test case Id: RPMXCON-47771");
		baseClass.stepInfo("To verify as an user login into the Application, User will be able to edit "
				+ "the advanced search saved query & after execute user will be able to save the query");
		loginPage.loginToSightLine(username, password);
		String savedSearchName = "test" + Utility.dynamicNameAppender();
		String savedSearchName_2 = "test" + Utility.dynamicNameAppender();
		baseClass.selectproject();
		search.MetaDataSearchInAdvancedSearch(Input.metaDataName, Input.custodianName_Andrew);
		search.saveSearchAdvanced(savedSearchName);
		savedSearch.savesearch_Edit(savedSearchName);
		search.clearExistingQueryAndSearchNewQueryWithoutSearch(Input.metaDataName, Input.custodianName_allen);
		search.saveASQueryWithoutSearch(savedSearchName_2);
		if (username == Input.pa1userName) {
			baseClass.stepInfo(
					"After the saved query edited in saved search page, modified query is saved using PA user");
		}
		if (username == Input.rmu1userName) {
			baseClass.stepInfo("After the saved query edit in saved search, modified query is saved using RMU user");
		}
		if (username == Input.rev1userName) {
			baseClass.stepInfo("After the saved query edit in saved search, modified query is saved using Reviewer");
		}
		savedSearch.savesearch_Edit(savedSearchName_2);
		savedSearch.verifyTheNavigationOfAS(Input.custodianName_allen);
		if (username == Input.pa1userName) {
			baseClass.stepInfo(
					"Saved modified query in saved search page is successfully validated in advance search page using PA user");
		}
		if (username == Input.rmu1userName) {
			baseClass.stepInfo(
					"Saved modified query in saved search page is successfully validated in advance search page using RMU user");
		}
		if (username == Input.rev1userName) {
			baseClass.stepInfo(
					"Saved modified query in saved search page is successfully validated in advance search page using Reviewer");
		}
		savedSearch.SaveSearchDelete(savedSearchName);
		savedSearch.SaveSearchDelete(savedSearchName_2);
		loginPage.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @throws AWTException
	 * @description Validate search with combination of Content/Metadata, Audio,
	 *              Conceptual, WorkProduct
	 */
	@Test(description ="RPMXCON-46980",dataProvider = "Users", groups = { "regression" }, priority = 4)
	public void verifyCombinedSearch(String UserName, String PassWord) throws InterruptedException, AWTException {
		baseClass.stepInfo("Validate search with combination of Content/Metadata, " + "Audio, Conceptual, WorkProduct");
		baseClass.stepInfo("Test case Id: RPMXCON-46980");
		loginPage.loginToSightLine(UserName, PassWord);
		if (UserName.equals(Input.rmu1userName)) {
			search.advancedContentSearch(Input.searchString2);
			search.bulkTag(TagName);
			baseClass.selectproject();
			search.advancedContentSearch(Input.searchString2);
			search.bulkFolder(FolderName);
		}
		try {
			int ExpectedPureHit = search.verifyCombinedSearch(FolderName, "folder", "no", "AND", "NOT", "OR");
			baseClass.stepInfo("PureHit count after combined search " + ExpectedPureHit);
			int ExpectedPureHit3 = search.verifyCombinedSearch(TagName, "tag", "yes", "OR", "NOT", "OR");
			baseClass.stepInfo("PureHit count after combined search " + ExpectedPureHit3);
			softAssertion.assertEquals(Input.expectedCombinedSearchHits1, ExpectedPureHit3);
			softAssertion.assertEquals(Input.expectedCombinedSearchHits2, ExpectedPureHit);
			softAssertion.assertAll();
			baseClass.passedStep(
					"Sucessfully verified search with combination of Content/Metadata, Audio, Conceptual, WorkProduct");
		} catch (Exception e) {
			e.printStackTrace();
			baseClass.failedStep(
					"Failed to verify search with combination of Content/Metadata, Audio, Conceptual, WorkProduct");
		}
		loginPage.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description To verify as an user login into the Application, user will be
	 *              able to save the In-Session search query from advanced search
	 */
	@Test(description ="RPMXCON-47758",dataProvider = "Users", groups = { "regression" }, priority = 5, enabled = true)
	public void verifysavedSearch_sessionSearch(String username, String password)
			throws InterruptedException, ParseException, IOException {
		baseClass.stepInfo("To verify as an user login into the Application, user will be able to save"
				+ " the In-Session search query from advanced search");
		baseClass.stepInfo("Test case Id: RPMXCON-47758");
		loginPage.loginToSightLine(username, password);
		String savedSearchName = "test" + Utility.dynamicNameAppender();
		baseClass.selectproject();
		search.MetaDataSearchInAdvancedSearch(Input.metaDataName, Input.custodianName_Andrew);
		search.saveSearchAdvanced(savedSearchName);
		baseClass.stepInfo("Created a saved search " + savedSearchName);
		savedSearch.savedSearch_Searchandclick(savedSearchName);
		if (savedSearch.getSearchName(savedSearchName).isElementPresent()) {
			baseClass.passedStep(
					"Sucessfuly verified as an " + username + " user login into the Application, user will be "
							+ "able to save the In-Session search query from advanced search");
		} else {
			baseClass.failedStep("As an " + username + " user login into the Application, user will not be "
					+ "able to save the In-Session search query from advanced search");

		}
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj
	 * @throws InterruptedException
	 * @description Verify that Term Operator - All is working properly on Advanced
	 *              Search screen
	 */
	 @Test(description ="RPMXCON-47728",groups = { "regression" },dataProvider = "Users", priority = 6,enabled = true)
	public void verifyTermOperator(String username, String password) throws InterruptedException {
		loginPage.loginToSightLine(username, password);
		baseClass.selectproject();
		DocListPage dlPage= new DocListPage(driver);
		baseClass.stepInfo("Verify that Term Operator - All is working properly on Advanced Search screen");
		baseClass.stepInfo("Test case Id: RPMXCON-47728");
		search.VerifyaudioSearchAndSetThreshold(Input.audioSearch, Input.audioLanguage, -34);
		driver.scrollPageToTop();
		search.ViewInDocList();
		//getting doc id's after saerch with 'morning'
		List<String> DocIdsWithSearchString1 = dlPage.gettingAllDocIDs();
		baseClass.selectproject();
		search.VerifyaudioSearchAndSetThreshold(Input.SearchString_Audio,Input.audioLanguage, -34);
		driver.scrollPageToTop();
		search.ViewInDocList();
		//getting doc id's after saerch with 'well'
		List<String> DocIdsWithSearchString2 = dlPage.gettingAllDocIDs();

		//getting list of common docs with both search terms --expected result after performing search with both terms and ALL operator.
		DocIdsWithSearchString2.retainAll(DocIdsWithSearchString1);
		baseClass.selectproject();
		int ActualAny = search.audioSearchWithOperator(Input.audioSearch, Input.SearchString_Audio, Input.audioLanguage, -34, "ALL");
		softAssertion.assertEquals(DocIdsWithSearchString2.size(), ActualAny);
		softAssertion.assertAll();
		baseClass.passedStep("All Operator is working properly on Advanced Search screen");
		loginPage.logout();
		}



	

	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @description To verify as an user login into the Application, I will get the
	 *              search result for Email thread Documents, when I will search
	 *              some query along with multiple Folder filter applied from work
	 *              product tab of advanced search RPMXCON-47657
	 */
	@Test(description ="RPMXCON-47657",dataProvider = "Users", groups = { "regression" }, priority = 7)
	public void validateThreadedDocsForMultipleFolders(String username, String password) throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-47657");
		baseClass.stepInfo("Verify the search result for threaded Documents is working properly for multiple folders"
				+ "search filter of work product");
		loginPage.loginToSightLine(username, password);
		if (username == Input.rmu1userName) {
			baseClass.selectproject();
			driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
			tagPage.CreateFolder(folderName, Input.securityGroup);
			baseClass.selectproject();
			search.basicContentSearch(Input.searchString1);
			search.bulkFolderExisting(folderName);
			baseClass.selectproject();
			driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
			tagPage.CreateFolder(folderName_2, Input.securityGroup);
			baseClass.selectproject();
			search.basicContentSearch(Input.searchString1);
			search.bulkFolderExisting(folderName_2);
			baseClass.selectproject();
		}
		search.switchToWorkproduct();
		baseClass.stepInfo("Navigated to advance search and work product is clicked");
		baseClass.waitForElement(search.getWP_FolderBtn());
		search.getWP_FolderBtn().Click();
		search.selectFolderInTree(folderName);
		search.selectFolderInTree(folderName_2);
		try {
			baseClass.waitTillElemetToBeClickable(search.getMetaDataInserQuery());
			search.getMetaDataInserQuery().ScrollTo();
			search.getMetaDataInserQuery().waitAndClick(15);
			baseClass.passedStep("Folders are selected from tree and inserted into query");
		} catch (Exception e) {
			baseClass.failedStep("Folders are not selected from tree and inserted into query");
		}
		driver.scrollPageToTop();
		baseClass.waitForElement(search.getQuerySearchButton());
		search.getQuerySearchButton().waitAndClick(5);
		String threadedDocs = search.verifyThreadedCount();
		try {
			softAssertion.assertNotNull(threadedDocs, "There is No hits occured for the the given search term");
			baseClass.passedStep("Getting threaded docs for the search successfully");
		} catch (Exception e) {
			baseClass.failedStep("Not getting threaded docs for the search successfully");
		}
		softAssertion.assertAll();
		loginPage.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws AWTException
	 * @description: Validate distributed list and results for searching by
	 *               Completed Assignment workproduct (SysAdmin/DAU/PAU impersonated
	 *               as RMU) RPMXCON-57268
	 */
	@Test(description ="RPMXCON-57268",groups = { "regression" }, priority = 8, enabled = true)
	public void verifyDistributedListUsingImpersonation() throws InterruptedException, AWTException {
		baseClass.stepInfo(
				"Validate distributed list and results for searching by Completed Assignment workproduct(SysAdmin/DAU/PAU impersonated as RMU)");
		baseClass.stepInfo("Test case Id: RPMXCON-57268");
		String assignmentName = "assignment" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU user");
		assgnPage.createAssignment(assignmentName, Input.codeFormName);
		baseClass.selectproject();
		search.basicContentSearch(Input.TallySearch);
		search.bulkAssignExisting(assignmentName);
		baseClass.stepInfo("Created a assignment " + assignmentName);
		baseClass.selectproject();
		assgnPage.editAssignmentUsingPaginationConcept(assignmentName);
		assgnPage.add3ReviewerAndDistribute();
		baseClass.impersonateRMUtoReviewer();
		baseClass.stepInfo("Impersonated to reviewer successfully");
		assgnPage.completeDistributesDocsByReviewer(assignmentName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA user");
		baseClass.selectproject();
		baseClass.impersonatePAtoReviewer();
		baseClass.stepInfo("Impersonated to reviewer successfully");
		assgnPage.completeDistributesDocsByReviewer(assignmentName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.stepInfo("Logged in as reviewer successfully");
		assgnPage.completeDistributesDocsByReviewer(assignmentName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA user");
		baseClass.impersonateSAtoRMU();
		baseClass.stepInfo("Impersonated to RMU user successfully");
		search.switchToWorkproduct();
		reviwersListInSearchPg = search.selectAssignmentAndMultipleReviewersWPSWthFilters(assignmentName,
				Input.rev1userName, Input.rmu1userName, Input.pa1userName, "Completed");
		search.serarchWP();
		String PureHitCountAfterDistribution = search.verifyPureHitsCount();
		baseClass.stepInfo("Pure hit count after Distribution of Docs  is " + PureHitCountAfterDistribution);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA user");
		baseClass.impersonatePAtoRMU();
		baseClass.stepInfo("Impersonated to rmu user successfully");
		search.switchToWorkproduct();
		reviwersListInSearchPg = search.selectAssignmentAndMultipleReviewersWPSWthFilters(assignmentName,
				Input.rev1userName, Input.rmu1userName, Input.pa1userName, "Completed");
		search.serarchWP();
		String PureHitCountAfterDistribution1 = search.verifyPureHitsCount();
		baseClass.stepInfo("Pure hit count after Distribution of Docs  is " + PureHitCountAfterDistribution1);
		assgnPage.deleteAssgnmntUsingPagination(assignmentName);
		loginPage.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @description: Verify that "Export Data" window have a closing "x" button in
	 *               the top right. RPMXCON-47181
	 */
	@Test(description ="RPMXCON-47181",groups = { "regression" }, priority = 9, enabled = true)
	public void verifyExportDataPopUpCloseButton() throws InterruptedException {
		baseClass.stepInfo("Verify that \"Export Data\" window have a closing \"x\" button in the top right.");
		baseClass.stepInfo("Test case Id: RPMXCON-47181");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Metadata search is done by basic search and validate export data popup close button");
		search.MetaDataSearchInBasicSearch(Input.metaDataName, Input.custodianName_Andrew);
		search.exportData();
		search.closeExportDataPopup();
		baseClass.selectproject();
		baseClass.stepInfo("Metadata search is done by advance search and validate export data popup close button");
		search.MetaDataSearchInAdvancedSearch(Input.metaDataName, Input.custodianName_Andrew);
		search.exportData();
		search.closeExportDataPopup();
		loginPage.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @description: Search Query Saved Using Advanced Search RPMXCON-47214
	 */
	@Test(description ="RPMXCON-47214",groups = { "regression" }, priority = 10)
	public void verifySavedSearchQuery() throws InterruptedException {
		baseClass.stepInfo("Search Query Saved Using Advanced Search ");
		baseClass.stepInfo("Test case Id: RPMXCON-47214");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		String savedSearchName = "test" + Utility.dynamicNameAppender();
		baseClass.selectproject();
		int pureHits = search.MetaDataSearchInAdvancedSearch(Input.docFileExt, ".xls");
		search.saveSearchAdvanced(savedSearchName);
		baseClass.stepInfo("Created a saved search " + savedSearchName);
		savedSearch.savedSearch_Searchandclick(savedSearchName);
		if (savedSearch.getSearchName(savedSearchName).isElementPresent()) {
			baseClass.passedStep("Saved search query is displayed as expected");
		} else {
			baseClass.failedStep("Saved search query is not  displayed as expected");

		}
		try {
			softAssertion.assertEquals(search.getSavedSearchCount(savedSearchName).getText(),
					Integer.toString(pureHits));
			softAssertion.assertAll();
			baseClass.passedStep("Purehits of saved search query is displayed as expected");
		} catch (Exception e) {
			baseClass.failedStep("Purehits of saved search query is not displayed as expected");
		}
		savedSearch.SaveSearchDelete(savedSearchName);
		loginPage.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @description: Verify that pre-defined list of special characters should be
	 *               disallowed to be entered by the user in audio search
	 */
	@Test(description ="RPMXCON-47135",groups = { "regression" }, priority = 11)
	public void VerifyWarningMessage_AudioSearchSpecialChars() throws InterruptedException {
		baseClass.stepInfo(
				"Verify that pre-defined list of special characters should be disallowed to be entered by the user in audio search");
		baseClass.stepInfo("Test case Id: RPMXCON-47135");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		String searchText = "~ ! @ # $ % & * ( ) { } | : ; , . ? / = '";
		search.VerifyaudioSearch_DisallowedSpecialCharachters(searchText);
		softAssertion.assertAll();
		loginPage.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description Verify that user should be able to execute the saved audio
	 *              search which involve content in other languages - eg. German,
	 *              Japanese characters in search text
	 */
	@Test(description ="RPMXCON-47138",groups = { "regression" }, priority = 12)
	public void AudioSearch_SaveAndExecute() throws InterruptedException {
		String Search1 = "AudioSearch" + Utility.dynamicNameAppender();
		baseClass.stepInfo("Test case Id: RPMXCON-47138");
		baseClass.stepInfo(
				"Verify that user should be able to execute the saved audio search which involve content in other"
						+ " languages - eg. German, Japanese characters in search text");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		search.VerifyaudioSearchThreshold("grün", "German", "min");
		String actualPH1 = search.verifyPureHitsCount();
		baseClass.stepInfo("Audio Search is done PureHit is : " + actualPH1 + "");
		search.saveSearchAdvanced_New(Search1, Input.mySavedSearch);
		savedSearch.VerifySavedSearchExecuteStatus(Search1);
		baseClass.selectproject();
		String Search2 = "AudioSearch" + Utility.dynamicNameAppender();
		search.VerifyaudioSearchThreshold("です", "Japanese", "min");
		String actualPH2 = search.verifyPureHitsCount();
		baseClass.stepInfo("Audio Search is done using Japnese language  is done PureHit is : " + actualPH2 + "");
		search.saveSearchAdvanced_New(Search2, Input.mySavedSearch);
		savedSearch.VerifySavedSearchExecuteStatus(Search2);
		loginPage.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description Verify that user should be able to save audio search which
	 *              involve content in other languages - eg. German, Japanese
	 *              characters in search text
	 */
	@Test(description ="RPMXCON-47137",groups = { "regression" }, priority = 13)
	public void AudioSearch_Save() throws InterruptedException {
		String Search1 = "AudioSearch" + Utility.dynamicNameAppender();
		baseClass.stepInfo("Test case Id: RPMXCON-47137");
		baseClass.stepInfo(
				"Verify that user should be able to save audio search which involve content in other languages - "
						+ "eg. German, Japanese characters in search text");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		search.VerifyaudioSearchThreshold("grün", "German", "min");
		String actualPH1 = search.verifyPureHitsCount();
		baseClass.stepInfo(
				"Audio Search is done using german language PureHit is displayed as expected : " + actualPH1 + "");
		search.saveSearchAdvanced_New(Search1, Input.mySavedSearch);
		baseClass.selectproject();
		String Search2 = "AudioSearch" + Utility.dynamicNameAppender();
		search.VerifyaudioSearchThreshold("です", "Japanese", "min");
		String actualPH2 = search.verifyPureHitsCount();
		baseClass.stepInfo(
				"Audio Search is done using Japnese language  PureHit is displayed as expected : " + actualPH2 + "");
		search.saveSearchAdvanced_New(Search2, Input.mySavedSearch);
		loginPage.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @description Verify that user should be able to edit the existing saved audio
	 *              search which with the content in other languages - eg. German,
	 *              Japanese in search text
	 */
	@Test(description ="RPMXCON-47140",dataProvider = "Users", groups = { "regression" }, priority = 14)
	public void VerifyAudio_ModifySearchWithOtherLanguages(String username, String password)
            throws InterruptedException {
        String Search1 = "AudioSearch" + Utility.dynamicNameAppender();
        SoftAssert sa = new SoftAssert();
        baseClass.stepInfo("Test case Id: RPMXCON-47140");
        baseClass.stepInfo(
                "Verify that user should be able to edit the existing saved audio search which with the content"
                        + " in other languages - eg. German, Japanese in search text");
        loginPage.loginToSightLine(username, password);
        search.VerifyaudioSearchThreshold(Input.audioSearchString1, Input.language, "min");
        String actualPH1 = search.verifyPureHitsCount();
        baseClass.stepInfo("Audio Search is done PureHit is : " + actualPH1);
        search.saveSearchAdvanced_New(Search1, Input.mySavedSearch);
        savedSearch.savedSearch_Searchandclick(Search1);
        savedSearch.VerifyPureHitInSavedAudiosearch(Search1);
        baseClass.waitTillElemetToBeClickable(savedSearch.getSavedSearchEditButton());
        savedSearch.getSavedSearchEditButton().waitAndClick(10);
        driver.waitForPageToBeReady();
        search.modifyAudioSearch("grün", "German", "max");
        String expectedGermaneHits = search.getPureHitsCount_ModifySearch().getText();
        sa.assertEquals(expectedGermaneHits,Input.expectedPH_german);
        baseClass.passedStep("Sucessfully modified the query and get the search results for the german language");
        driver.Navigate().refresh();
        driver.waitForPageToBeReady();
        search.modifyAudioSearch("が", "Japanese", "min");
        String expectedJapaneseHits = search.getPureHitsCount_ModifySearch().getText();
        sa.assertEquals(expectedJapaneseHits, Input.expectedPH_Japanese);
        sa.assertAll();
        baseClass.passedStep("Sucessfully modified the query and get the search results for the japanese language");
        loginPage.logout();
    }
	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @description Verify that user should be able to edit the saved audio search
	 *              which involve content in other languages - eg. German, Japanese
	 *              characters in search text
	 */
	@Test(description ="RPMXCON-47139",dataProvider = "Users", groups = { "regression" }, priority = 15)
	public void VerifyAudio_ModifySearchTextAndLanguages(String username, String password) throws InterruptedException {
		String Search1 = "AudioSearch" + Utility.dynamicNameAppender();
		baseClass.stepInfo("Test case Id: RPMXCON-47139");
		baseClass.stepInfo("Verify that user should be able to edit the saved audio search which involve "
				+ "content in other languages - eg. German, Japanese characters in search text");
		loginPage.loginToSightLine(username, password);
		search.VerifyaudioSearchThreshold("grün", "German", "max");
		search.verifyPureHitsCount();
		baseClass.passedStep("Sucessfully modified the query and get the search results for the german language");
		search.saveSearchAdvanced_New(Search1, Input.mySavedSearch);
		try {
			savedSearch.savedSearch_Searchandclick(Search1);
			savedSearch.VerifyPureHitInSavedAudiosearch(Search1);
			baseClass.waitTillElemetToBeClickable(savedSearch.getSavedSearchEditButton());
			savedSearch.getSavedSearchEditButton().waitAndClick(10);
			driver.waitForPageToBeReady();
			search.modifyAudioSearch("が", "Japanese", "min");
			SoftAssert assertion=new SoftAssert();
			assertion.assertNotNull(search.getPureHitsCount_ModifySearch().getText());
			assertion.assertAll();
			baseClass.passedStep("Sucessfully modified the query and get the search results for the japanese language");
		} catch (Exception e) {
			e.printStackTrace();
			baseClass.failedStep("Threshold or PureHit count mismatched from sessions search page");
		}
		loginPage.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description Verify that user should be able to do audio search which involve
	 *              content in other languages - eg. German, Japanese, Mandarin by
	 *              being able to key in search text in these languages
	 */
	@Test(description ="RPMXCON-47136",dataProvider = "Users", groups = { "regression" }, priority = 16)
	public void AudioSearch(String username, String password) throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-47136");
		baseClass.stepInfo(
				"Verify that user should be able to do audio search which involve content in other languages - eg. German, Japanese,\r\n"
						+ "Mandarin by being able to key in search text in these languages ");
		loginPage.loginToSightLine(username, password);
		search.VerifyaudioSearchThreshold("grün", "German", "min");
		String actualPH1 = search.verifyPureHitsCount();
		baseClass.selectproject();
		search.VerifyaudioSearchThreshold("です", "Japanese", "min");
		String actualPH2 = search.verifyPureHitsCount();
		try {
			softAssertion.assertEquals(actualPH1, Input.expectedPH_german);
			softAssertion.assertEquals(actualPH2, Input.expectedPH_Japanese);
			softAssertion.assertAll();
			baseClass.passedStep(
					"Audio Search is done using german language PureHit is displayed as expected : " + actualPH1 + "");
			baseClass.passedStep("Audio Search is done using Japnese language  PureHit is displayed as expected : "
					+ actualPH2 + "");
		} catch (Exception e) {
			baseClass.failedStep("PureHit Count value is not as Exepcted");

		}
		// Added to perform Audio search using Mandarin as language
		// but right now we dont have Mandarin Language Docs in the current Project So
		// commented those lines.

		/*
		 * search.VerifyaudioSearchThreshold("所", "Mandarin", "min"); String actualPH3 =
		 * search.verifyPureHitsCount(); Assert.assertEquals(actualPH3,"" ); baseClass.
		 * stepInfo("Audio Search is done using Japnese language  PureHit is displayed as expected : "
		 * + actualPH3 + "");
		 */
		loginPage.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description Verify modifying saved audio searches and cross check Audio
	 *              threshold value saved correctly
	 */
	// @Test(dataProvider = "AudioSearchwithUsers", groups = { "regression" },
	// priority =17,enabled= true)
	public void VerifyAudio_ModifySavedSearchInMySavedSearch(String username, String password, String ImpersonateOption)
			throws InterruptedException {
		String Search1 = "AudioSearchONE" + Utility.dynamicNameAppender();
		String Search2 = "AudioSearcTWO" + Utility.dynamicNameAppender();
		String Search3 = "AudioSearchThree" + Utility.dynamicNameAppender();
		baseClass.stepInfo("Test case Id: RPMXCON-46983");
		baseClass.stepInfo(
				"Verify modifying saved audio searches and cross check Audio threshold value saved correctly");
		loginPage.loginToSightLine(username, password);
		if (ImpersonateOption == "PA") {
			baseClass.impersonateSAtoPA();
			baseClass.stepInfo("Logged in as SA and impersonated as PA");
		}
		if (ImpersonateOption == "RMU") {
			baseClass.impersonateSAtoRMU();
			baseClass.stepInfo("Logged in as SA and impersonated as RMU");
		}
		// creating audio search
		String ActualThresholdValue1 = search.VerifyaudioSearchThreshold(Input.audioSearchString1, Input.language, "min");
		String actualPH1 = search.verifyPureHitsCount();
		baseClass.stepInfo(
				"Audio Search is done PureHit is : " + actualPH1 + "Threshold value is " + ActualThresholdValue1);
		search.saveSearchAdvanced_New(Search1, Input.mySavedSearch);

		// Modifying the audio serach and saving it
		search.ModifyAudioSearch("morning well", Input.language, "max");
		String actualThreshold2 = search.GetConfidenceThresholdInSecondSearchResult().getText();
		String actualPH2 = search.getPureHitsCount_ModifySearch().getText();
		baseClass.stepInfo("Audio Search is done PureHit is : " + actualPH2 + "Threshold value is " + actualThreshold2);
		System.out.println("Audio Search is done PureHit is : " + actualPH2 + "Threshold value is " + actualThreshold2);
		search.saveSearchAdvanced_New(Search2, Input.mySavedSearch);
		// Modifying the audio serach and saving it
		search.ModifyAudioSearch(null, Input.language, "min");
		String actualThreshold3 = search.GetConfidenceThresholdInSecondSearchResult().getText();
		String actualPH3 = search.getPureHitsCount_ModifySearch().getText();
		baseClass.stepInfo("Audio Search is done PureHit is : " + actualPH3 + "Threshold value is " + actualThreshold3);
		search.saveSearchAdvanced_New(Search3, Input.mySavedSearch);
		baseClass.selectproject();
		try {
			String pureHit0;
			String dataSet[][] = { { Search1, actualPH1 }, { Search2, actualPH2 }, { Search3, actualPH3 } };
			String ThresholdList[][] = { { ActualThresholdValue1 }, { actualThreshold2 }, { actualThreshold3 } };

			int k = 0;
			for (int i = 0; i < dataSet.length; i++) {
				int j = 0;
				int l = 0;
				String searches = dataSet[i][j];
				j++;
				pureHit0 = dataSet[i][j];
				String ThresholdValueExpected = ThresholdList[k][l];
				// Verifying the pure hit and threshold Value in saved search page for all the
				// searches saved in above steps.
				savedSearch.savedSearch_Searchandclick(searches);
				driver.waitForPageToBeReady();
				softAssertion.assertEquals(ThresholdValueExpected,
						savedSearch.VerifyThresholdValueInSavedAudiosearch(searches));
				softAssertion.assertEquals(pureHit0, savedSearch.VerifyPureHitInSavedAudiosearch(searches));
				baseClass.passedStep(
						"Sucessfuly Verified whether the threshold value and PureHit Count for modified Audio search Displays as Expected in"
								+ " saved search page");
				// editing the saved audio search
				baseClass.waitForElement(savedSearch.getSavedSearchEditButton());
				savedSearch.getSavedSearchEditButton().waitAndClick(10);
				search.ModifyAudioSearch(Input.SearchString_Audio, Input.language, "max");
				// saving the modified search by overwriting the existing search
				search.saveAsOverwrittenSearch(Input.mySavedSearch, searches, "First", "Success", "", null);

				String actualThreshold = search.GetConfidenceThresholdInSecondSearchResult().getText();
				String actualPH = search.getPureHitsCount_ModifySearch().getText();
				savedSearch.savedSearch_Searchandclick(searches);
				driver.waitForPageToBeReady();
				// Verifying the pure hit and threshold Value in saved search page for all the
				// overwriten searches saved in above steps.
				softAssertion.assertEquals(actualThreshold,
						savedSearch.VerifyThresholdValueInSavedAudiosearch(searches));
				softAssertion.assertEquals(actualPH, savedSearch.VerifyPureHitInSavedAudiosearch(searches));
				baseClass.passedStep(
						"Sucessfuly Verified whether the threshold value and PureHit Count for OverWritten modified Audio search Displays as Expected in"
								+ " saved search page");
				k++;
			}

		} catch (Exception e) {
			e.printStackTrace();
			baseClass.failedStep("excpetion occured.");

		}
		softAssertion.assertAll();
		loginPage.logout();
	}
	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-57377",groups = { "regression" }, priority = 18,enabled=false)
	public void verifytruncate_500chars() throws InterruptedException {
		String expectedSearchName="Abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijkl"
				+ "mnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnop...";
		baseClass.stepInfo("Saved Search Name with < 500 chars is truncating on edit search page");
		baseClass.stepInfo("Test case Id: RPMXCON-57377");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		String savedSearchName = "Abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijk"
				+ "lmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghij"
				+ "klmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghi"
				+ "jklmnopqrstuvwxyzabcdefghijklmnopqabcdefghijklmnopqrstuvwxyzabcdefghijklmnopq"
				+ "rstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnop"
				+ "qrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmno"
				+ "pqrstuvwxyzabcdefghijklmnopqrstuvwxyzabc500"+Utility.dynamicNameAppender();
		search.basicContentSearch(Input.TallySearch);
		search.saveSearch(savedSearchName);
		baseClass.stepInfo("Created a saved search with 499 chars  " + savedSearchName);
		savedSearch.savedSearch_Searchandclick(savedSearchName);		
		if (savedSearch.getSearchName(savedSearchName).isElementPresent()) {
			baseClass.stepInfo("Saved search name query is displayed as expected in saved saerch page");
			softAssertion.assertEquals(savedSearch.getSearchName(savedSearchName).getText(),savedSearchName);
			savedSearch.getSavedSearchEditButton().waitAndClick(10);
			driver.waitForPageToBeReady();
		} else {
			baseClass.failedStep("Saved search query is not displayed as expected in saved search page.");
		}
			softAssertion.assertEquals(search.getSearchName().getText().trim(),"SS: "+expectedSearchName);
			softAssertion.assertAll();
			baseClass.passedStep("Saved search query is displayed as expected in saved saerch page and search screen.");
			baseClass.passedStep("Sucessfully verified whether Saved Search Name with < 500 chars is truncating on edit search page");
		//savedSearch.SaveSearchDelete(savedSearchName);
			loginPage.logout();
	}
	/**
	 * @author Jayanthi.ganesan
	 */
	@Test(description ="RPMXCON-47151",dataProvider = "Users", groups = { "regression" }, priority = 19,enabled=true)
	public void audioSearch_warningTile(String username, String password) throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-47151");
		baseClass.stepInfo("Verify that warning should be displayed for search results when audio docs searched with German/Japanese characters and"
				+ " selected language is other than German/Japanese");
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged in as "+username);
		search.verifyaudioSearchWarning("grün",Input.language);
		baseClass.waitTime(3);
		baseClass.stepInfo("Entered a german text and selected language as "+Input.language);
		driver.getWebDriver().navigate().refresh();
		String eleValue[]= {"Docs That Met Your Criteria","Threaded Documents","Near Duplicates","Family Members"};
		driver.scrollingToBottomofAPage();
		for(int i=0;i<eleValue.length;i++) {
			if(search.getExclamationTile(eleValue[i]).isElementAvailable(0)) {
				baseClass.failedStep("Warning Tile icon is not displayed as expected ");
			}
			else {
				baseClass.passedStep("Warning Tile icon is displayed as expected ");
				baseClass.passedStep("Sucessfully Verified that whther warning should be displayed for search results when audio docs searched with German/Japanese "
						+ "characters and selected language is other than German/Japanese");
				
			}
		}
		loginPage.logout();
			}
	
	/**
	 * @author Jayanthi.ganesan
	 */
	@Test(description ="RPMXCON-48787", groups = { "regression" }, priority = 20,enabled=true)
	public void verifyDroppedTile() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48787");
		baseClass.stepInfo("Verify that Dropped tiles are retained in shopping cart when User Navigates Advanced Search"
				+ " (Pure Hit) >> \"Manage >> Assignments\" screen and Come back to Search Page.");
		loginPage.loginToSightLine( Input.rmu1userName, Input.rmu1password);
		search.basicContentSearch(Input.testData1);
		driver.waitForPageToBeReady();
		search.getPureHitAddButton().waitAndClick(10);
		baseClass.waitForElement(search.getPureHitDroppedTileBtn());
		if(search.getPureHitDroppedTileBtn().isElementAvailable(2)){
			baseClass.stepInfo("PureHit block added to shopping cart block and "
					+ " dropped hit tile is displayed in cart");
		}else {
			baseClass.stepInfo("Pure Hit tile not added to cart.");
		}
		
		baseClass.stepInfo("Navigating to manage assignments page");
		driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Again Navigating to Advacned search page");
		driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.waitForPageToBeReady();
		if(search.getPureHitDroppedTileBtn().isElementAvailable(3)){
			baseClass.passedStep("Dropped hit tile is displayed in cart after navigating from manage assignments page to "
					+ "advanced search page");
		}else {
			baseClass.failedStep("Dropped Pure Hit tile not in cart.");
		}
		loginPage.logout();		
		}
	/**
	 * @author Jayanthi.ganesan
	 */
	@Test(description ="RPMXCON-57272",dataProvider = "Users", groups = { "regression" }, priority = 21,enabled=true)
	public void verifyDoubleQuotesSearch(String username, String password) throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-57272");
		baseClass.stepInfo("Verify that result appears for phrase(in double quote) search in Advanced Search Query Screen.");
		loginPage.loginToSightLine( username,  password);
		baseClass.stepInfo("Logged in as "+username);
		String eleValue[]= {"\"discrepancy in\"","“john@consilio.com”"};
		driver.scrollingToBottomofAPage();
		for(int i=0;i<eleValue.length;i++) {
		search.basicContentSearch(eleValue[i]);
		driver.waitForPageToBeReady();
		String hitsCount=search.verifyPureHitsCount();
		softAssertion.assertNotNull(hitsCount);
		if(i==0) {
		baseClass.selectproject();}
		baseClass.stepInfo("Performed a search for phrase "+eleValue[i]+" and get a pure hit value of "+hitsCount);
		}
		softAssertion.assertAll();
		baseClass.passedStep("Sucessfully Verified that result appears for phrase(in double quote) search in Advanced Search Query Screen.");
		loginPage.logout();
		}
	/**
	 * @author Jayanthi.ganesan
	 */
	@Test(description ="RPMXCON-46876",dataProvider = "Export", groups = { "regression" }, priority = 22,enabled=true)
	public void verifyExportAction(String username, String password,boolean saerch) throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-46876");
		baseClass.stepInfo("Verify that Bulk Export Data Action is working properly on Advanced Search result Screen");
		loginPage.loginToSightLine( username,  password);
	
		if(saerch) {
		search.advancedContentSearch(Input.testData1);
		baseClass.stepInfo("Logged in as "+username+"Verify Export option for advanced  search");
		}
		else{
			search.basicContentSearch(Input.testData1);
			baseClass.stepInfo("Logged in as "+username+"Verify Export option for basic  search");
		}
		driver.waitForPageToBeReady();
		search.exportData();
		driver.waitForPageToBeReady();
		String[] metaDataFields1 = { Input.metaDataName, Input.docFileName, "AttachCount" };
		CustomDocumentDataReport cddr = new CustomDocumentDataReport(driver);
		cddr.selectMetaDataFields(metaDataFields1);
		String Filename2=cddr.runReportandVerifyFileDownloaded();
		System.out.println(Filename2);
		String value = cddr.csvfileVerification("", Filename2);
		String[] strArray = value.split(",");
		List<String> slectdFieldList_excel = Arrays.asList(strArray);
		List<String> slectdFieldList = Arrays.asList(metaDataFields1);	
		for (int i = 0; i < slectdFieldList_excel.size(); i++) {
			String temp = slectdFieldList_excel.get(i).replaceAll("\"", "");
			slectdFieldList_excel.set(i, temp);
			slectdFieldList_excel.get(i).trim();
			System.out.println(slectdFieldList_excel.get(i));
		}
		if (slectdFieldList.containsAll(slectdFieldList_excel)) {
			baseClass.passedStep("Sucessfully Verified that Bulk Export Data Action is working properly on Advanced Search result Screen");
		} else {
			baseClass.failedStep("Exported Data is not  reflected in excel file.");
		}
		loginPage.logout();
		}
	
	/**
	 * @author Jayanthi.ganesan
	 */
	@Test(description ="RPMXCON-57290",dataProvider = "Users", groups = { "regression" }, priority = 22,enabled=true)
	public void verifyWarningDForDoubleQuote(String username, String password) throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-57290");
		baseClass.stepInfo("Verify that belly band message appears when user tries to run search having "
				+ "left double quote only in Advanced Search Query Screen.");
		loginPage.loginToSightLine( username,  password);
		baseClass.stepInfo("Logged in as "+username);
		String eleValue[]= {"iterative methodology”","iterative methodology”","“stock investment~5"};
		driver.scrollingToBottomofAPage();
		for(int i=0;i<eleValue.length;i++) {
		search.AdvContentSearchWithoutPopHandling(eleValue[i]);
		baseClass.stepInfo("Entered a search string with left double quote "+eleValue[i]);
		driver.waitForPageToBeReady();
		search.verifyProximitySearch();
		// Click on Search button
		baseClass.waitForElement(search.getQuerySearchButton());
		search.getQuerySearchButton().waitAndClick(10);
		search.verifyProximitySearch();
		if(i!=2) {
			baseClass.selectproject();
		}
		baseClass.passedStep("Sucessfully Verified that belly band message appears when user tries to run search having "
				+ "left double quote only in Advanced Search Query Screen.");
		}
		loginPage.logout();		
		}
	
	
	/**
	 * @author jayanthi 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-57202",dataProvider = "Users",enabled = true, groups = { "regression" }, priority = 23)  
	public void verifyBGAdvancesearch(String username, String password) throws InterruptedException {
		
		SessionSearch search = new SessionSearch(driver);
		SavedSearch ss = new SavedSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-57202");
		baseClass.stepInfo("Verify that Search Result appears without"
				+ " Refresh  on Advanced Search Result Screen");
		loginPage.loginToSightLine(username, password);
		baseClass.selectproject(Input.highVolumeProject);
		String searchText1 = Input.SearchString_HighVolume;
		String searchText3 = "2";
		search.navigateToSessionSearchPageURL();
		driver.waitForPageToBeReady();
		search.getAdvancedSearchLinkCurrent().Click();
		driver.waitForPageToBeReady();
		int Bgcount = baseClass.initialBgCount();

		search.advancedContentBGSearch(searchText1, 1,true);
		search.advancedContentBGSearch(Input.searchString9, 2,true);
		search.advancedContentBGSearch(searchText3, 3,true);
		ss.navigateToSavedSearchPage();
		search.navigateToSessionSearchPageURL();
		search.getNewSearchButton().waitAndClick(5);
		baseClass.stepInfo("Navigated back to session search page and clicked on  new search button." );
		// checking for notification for BG search completed status.
		for (int i = 0; i < 2; i++) {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return baseClass.initialBgCount() == Bgcount + 3;
				}
			}), Input.wait120);
			if (baseClass.initialBgCount() == Bgcount + 3) {
				break;
			}
		}
		baseClass.stepInfo("Notifications are notified for BG Search completion.");
		search.verifySearchDisplayWithoutRefresh(searchText1, "3");
		search.verifySearchDisplayWithoutRefresh(Input.searchString9, "2");
		search.verifySearchDisplayWithoutRefresh(searchText3, "1");
		loginPage.logout();

	}
	/**
	 * @author Jayanthi 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-48434", dataProvider = "Users", enabled = true, groups = {
			"regression" }, priority = 23)
	public void verifyBG_CountAdvancesearchToDocList(String username, String password) throws InterruptedException {

		SessionSearch search = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48434");
		baseClass.stepInfo("Verify that Actual count appears on \"My Background Tasks\" page When User Navigate between"
				+ " Search to DocList and Navigation action takes longer time.(8 Sec)");
		loginPage.loginToSightLine(username, password);
		baseClass.selectproject(Input.highVolumeProject);
		String searchText1 = Input.SearchString_HighVolume;
		String maxDocCountViewedinDocList = Input.DocCount_BG_Page;
		search.navigateToSessionSearchPageURL();
		driver.waitForPageToBeReady();
		search.getAdvancedSearchLinkCurrent().Click();
		driver.waitForPageToBeReady();
		int Bgcount = baseClass.initialBgCount();
		// Content search in advanced search page
		search.advancedContentBGSearch(searchText1, 1, false);
		baseClass.stepInfo("performed a content search in advanced search pagae and it goes to back ground. ");
		// checking for notification for BG search completed status.
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return baseClass.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait120);
		baseClass.stepInfo("Notifications are notified after BackGround Search completion.");
		search.ViewInDocList();
		// handling display of spinning wheel for 8 seconds and pushing the doc list
		// navigation to back ground task.
		String backGroundTask_ID = search.pushingBulkNavigationToBackGround();
		search.verifyNotificationAndNavigateBackGroundTaskPg(Bgcount + 1);
		String actualDocs = search.getRowData_BGT_Page("Actual Docs", backGroundTask_ID);
		SoftAssert softassert = new SoftAssert();
		softassert.assertEquals(actualDocs, maxDocCountViewedinDocList);
		softassert.assertAll();
		baseClass.passedStep(actualDocs + " is the doc count displayed in my back ground task page.");
		baseClass.passedStep("Sucessfully verified that Actual count appears on My Background Tasks page When"
				+ " User Navigate between Search to DocList and Navigation action takes longer time.(8 Sec)");
		loginPage.logout();

	}
	/**
	* @author Sowndarya.Velraj
	* @throws InterruptedException
	*/
	@Test(description ="RPMXCON-47729",groups = { "regression" },dataProvider = "Users", priority = 35,enabled = true)
	public void verifyWarningMSg_Audisaercho(String username, String password) throws InterruptedException {
	loginPage.loginToSightLine(username, password);
	baseClass.stepInfo("Verify that warning message appears if user does not select Language pack "
	+ "and click on Search button on Advanced Search screen");
	baseClass.stepInfo("Test case Id: RPMXCON-47729");
	search.navigateToAdvancedSearchPage();
	search.verifyWarningAudioSearch_NotSelctedLanguage(Input.audioSearchString1);
	baseClass.passedStep("Warning message displayed if user does not select Language pack "
	+ "and click on Search button on Advanced Search screen");
	loginPage.logout();
	}
	
	@DataProvider(name = "Export")
	public Object[][] Export() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, true },
				{ Input.rmu1userName, Input.rmu1password, true },
				{ Input.rev1userName, Input.rev1password, true },
				{ Input.pa1userName, Input.pa1password, false },
				{ Input.rmu1userName, Input.rmu1password, false },
				{ Input.rev1userName, Input.rev1password, false }
				};
		return users;
	}


	@DataProvider(name = "AudioSearchwithUsers")
	public Object[][] AudioSearchwithUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, null },
				{ Input.rmu1userName, Input.rmu1password, null }, { Input.sa1userName, Input.sa1password, "PA" },
				{ Input.sa1userName, Input.sa1password, "RMU" } };
		return users;
	}

	@DataProvider(name = "Users")
	public Object[][] CombinedSearchwithUsers() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password }, { Input.pa1userName, Input.pa1password },
				{ Input.rev1userName, Input.rev1password } };
		return users;
	}

	// Added on 10/26/21
	@DataProvider(name = "UsersWithoutPA")
	public Object[][] UsersWithoutPA() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password }, { Input.rev1userName, Input.rev1password } };
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
		try {
		//	LoginPage.clearBrowserCache();

		} catch (Exception e) {
			System.out.println("Sessions already closed");
		}
	}

}
