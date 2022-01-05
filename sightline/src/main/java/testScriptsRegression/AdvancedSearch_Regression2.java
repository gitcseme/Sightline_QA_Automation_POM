package testScriptsRegression;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;

import org.testng.Assert;
import org.testng.ITestResult;
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

	//	Input in = new Input();
	//	in.loadEnvConfig();
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
	// @Test(dataProvider = "UsersWithoutPA", groups = { "regression" }, priority =
	// 1,enabled = true)
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

	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description:To verify as an user login into the Application, User will be
	 *                 able to edit the advanced search saved query & able to search
	 *                 and save the query RPMXCON-47770
	 */
	@Test(dataProvider = "Users", groups = { "regression" }, priority = 2, enabled = true)
	public void validateSearchAndSavedTheModifiedQuery(String username, String password)
			throws InterruptedException, ParseException, IOException {
		baseClass.stepInfo("Test case Id: RPMXCON-47770");
		baseClass.stepInfo("To verify as an user login into the Application, User will be able "
				+ "to edit the advanced search saved query & able to search and save the query");
		loginPage.loginToSightLine(username, password);
		String savedSearchName = "test" + Utility.dynamicNameAppender();
		String savedSearchName_2 = "test" + Utility.dynamicNameAppender();
		baseClass.selectproject();
		search.MetaDataSearchInAdvancedSearch(Input.metaDataName, "Andrew");
		search.saveSearchAdvanced(savedSearchName);
		savedSearch.savesearch_Edit(savedSearchName);
		search.clearExistingQueryAndSearchNewQuery(Input.metaDataName, "allen");
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
		savedSearch.verifyTheNavigationOfAS("allen");
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
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description:To verify as an user login into the Application, User will be
	 *                 able to edit the advanced search saved query & after execute
	 *                 user will be able to save the query RPMXCON-47771
	 */
	@Test(dataProvider = "Users", groups = { "regression" }, priority = 3, enabled = true)
	public void validateSavedSearchModifiedQuery(String username, String password)
			throws InterruptedException, ParseException, IOException {
		baseClass.stepInfo("Test case Id: RPMXCON-47771");
		baseClass.stepInfo("To verify as an user login into the Application, User will be able to edit "
				+ "the advanced search saved query & after execute user will be able to save the query");
		loginPage.loginToSightLine(username, password);
		String savedSearchName = "test" + Utility.dynamicNameAppender();
		String savedSearchName_2 = "test" + Utility.dynamicNameAppender();
		baseClass.selectproject();
		search.MetaDataSearchInAdvancedSearch(Input.metaDataName, "Andrew");
		search.saveSearchAdvanced(savedSearchName);
		savedSearch.savesearch_Edit(savedSearchName);
		search.clearExistingQueryAndSearchNewQueryWithoutSearch(Input.metaDataName, "allen");
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
		savedSearch.verifyTheNavigationOfAS("allen");
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
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @throws AWTException
	 * @description Validate search with combination of Content/Metadata, Audio,
	 *              Conceptual, WorkProduct
	 */
	@Test(dataProvider = "Users", groups = { "regression" }, priority = 4)
	public void verifyCombinedSearch(String UserName, String PassWord) throws InterruptedException, AWTException {
		baseClass.stepInfo("Validate search with combination of Content/Metadata, " + "Audio, Conceptual, WorkProduct");
		baseClass.stepInfo("Test case Id: RPMXCON-46980");
		loginPage.loginToSightLine(UserName, PassWord);
		if (UserName.equals(Input.rmu1userName)) {
			search.advancedContentSearch(Input.searchString2);
			search.getIntoFullScreen();
			search.bulkTag(TagName);
			search.getExitFullScreen();
			baseClass.selectproject();
			search.advancedContentSearch(Input.searchString2);
			search.getIntoFullScreen();
			search.bulkFolder(FolderName);
			search.getExitFullScreen();
		}
		try {
			int ExpectedPureHit = search.verifyCombinedSearch(FolderName, "folder", "no", "AND", "NOT", "OR");
			baseClass.stepInfo("PureHit count after combined search " + ExpectedPureHit);
			int ExpectedPureHit3 = search.verifyCombinedSearch(TagName, "tag", "yes", "OR", "NOT", "OR");
			baseClass.stepInfo("PureHit count after combined search " + ExpectedPureHit3);
			softAssertion.assertEquals(834, ExpectedPureHit3);
			softAssertion.assertEquals(53, ExpectedPureHit);
			softAssertion.assertAll();
			baseClass.passedStep(
					"Sucessfully verified search with combination of Content/Metadata, Audio, Conceptual, WorkProduct");
		} catch (Exception e) {
			e.printStackTrace();
			baseClass.failedStep(
					"Failed to verify search with combination of Content/Metadata, Audio, Conceptual, WorkProduct");
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description To verify as an user login into the Application, user will be
	 *              able to save the In-Session search query from advanced search
	 */
	@Test(dataProvider = "Users", groups = { "regression" }, priority = 5, enabled = true)
	public void verifysavedSearch_sessionSearch(String username, String password)
			throws InterruptedException, ParseException, IOException {
		baseClass.stepInfo("To verify as an user login into the Application, user will be able to save"
				+ " the In-Session search query from advanced search");
		baseClass.stepInfo("Test case Id: RPMXCON-47758");
		loginPage.loginToSightLine(username, password);
		String savedSearchName = "test" + Utility.dynamicNameAppender();
		baseClass.selectproject();
		search.MetaDataSearchInAdvancedSearch(Input.metaDataName, "Andrew");
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
	}

	/**
	 * @author Sowndarya.Velraj
	 * @throws InterruptedException
	 * @description Verify that Term Operator - All is working properly on Advanced
	 *              Search screen
	 * @description Verify that Term Operator - Any is working properly on Advanced
	 *              Search screen
	 */
	// @Test(groups = { "regression" }, priority = 6,enabled = false)

	public void verifyTermOperator() throws InterruptedException {
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.selectproject();
		baseClass.stepInfo("Verify that Term Operator - All is working properly on Advanced Search screen");
		baseClass.stepInfo("Test case Id: RPMXCON-47728");

		baseClass.stepInfo("Verify that Term Operator - Any is working properly on Advanced Search screen");
		baseClass.stepInfo("Test case Id: RPMXCON-47729");

		int Expected1 = search.VerifyaudioSearchAndSetThreshold("morning", "International English", -34);
		baseClass.selectproject();
		int Expected2 = search.VerifyaudioSearchAndSetThreshold("well", "International English", -34);
		baseClass.selectproject();
		int ActualAll = search.audioSearchWithOperator("morning", "well", "International English", -34, "ALL");
		baseClass.selectproject();
		int ActualAny = search.audioSearchWithOperator("morning", "well", "International English", -34, "ANY");
		try {
			if (Expected1 >= Expected2) {
				softAssertion.assertEquals(Expected1, ActualAny);
				softAssertion.assertEquals(Expected2, ActualAll);
				softAssertion.assertAll();
			} else {
				softAssertion.assertEquals(Expected2, ActualAny);
				softAssertion.assertEquals(Expected1, ActualAll);
				softAssertion.assertAll();
			}
			baseClass.passedStep("All and Any Operator is working properly on Advanced Search screen");

		} catch (Exception e) {
			e.printStackTrace();
			baseClass.failedStep("All and Any Operator is not working properly on Advanced Search screen");
		}

	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @description To verify as an user login into the Application, I will get the
	 *              search result for Email thread Documents, when I will search
	 *              some query along with multiple Folder filter applied from work
	 *              product tab of advanced search RPMXCON-47657
	 */
	@Test(dataProvider = "Users", groups = { "regression" }, priority = 7)
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
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws AWTException
	 * @description: Validate distributed list and results for searching by
	 *               Completed Assignment workproduct (SysAdmin/DAU/PAU impersonated
	 *               as RMU) RPMXCON-57268
	 */
	@Test(groups = { "regression" }, priority = 8, enabled = true)
	public void verifyDistributedListUsingImpersonation() throws InterruptedException, AWTException {
		baseClass.stepInfo(
				"Validate distributed list and results for searching by Completed Assignment workproduct(SysAdmin/DAU/PAU impersonated as RMU)");
		baseClass.stepInfo("Test case Id: RPMXCON-57268");
		String assignmentName = "assignment" + Utility.dynamicNameAppender();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU user");
		assgnPage.createAssignment(assignmentName, Input.codeFormName);
		baseClass.selectproject();
		search.basicContentSearch("null");
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
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @description: Verify that "Export Data" window have a closing "x" button in
	 *               the top right. RPMXCON-47181
	 */
	@Test(groups = { "regression" }, priority = 9, enabled = true)
	public void verifyExportDataPopUpCloseButton() throws InterruptedException {
		baseClass.stepInfo("Verify that \"Export Data\" window have a closing \"x\" button in the top right.");
		baseClass.stepInfo("Test case Id: RPMXCON-47181");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Metadata search is done by basic search and validate export data popup close button");
		search.MetaDataSearchInBasicSearch(Input.metaDataName, "Andrew");
		search.exportData();
		search.closeExportDataPopup();
		baseClass.selectproject();
		baseClass.stepInfo("Metadata search is done by advance search and validate export data popup close button");
		search.MetaDataSearchInAdvancedSearch(Input.metaDataName, "Andrew");
		search.exportData();
		search.closeExportDataPopup();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @description: Search Query Saved Using Advanced Search RPMXCON-47214
	 */
	@Test(groups = { "regression" }, priority = 10)
	public void verifySavedSearchQuery() throws InterruptedException {
		baseClass.stepInfo("Search Query Saved Using Advanced Search ");
		baseClass.stepInfo("Test case Id: RPMXCON-47214");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		String savedSearchName = "test" + Utility.dynamicNameAppender();
		baseClass.selectproject();
		int pureHits = search.MetaDataSearchInAdvancedSearch("DocFileExtension", ".xls");
		search.saveSearchAdvanced(savedSearchName);
		baseClass.stepInfo("Created a saved search " + savedSearchName);
		savedSearch.savedSearch_Searchandclick(savedSearchName);
		if (savedSearch.getSearchName(savedSearchName).isElementPresent()) {
			baseClass.passedStep("Saved search query is displayed as expected");
		} else {
			baseClass.failedStep("Saved search query is displayed as expected");

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
	}

	/**
	 * @author Jayanthi.ganesan
	 * @description: Verify that pre-defined list of special characters should be
	 *               disallowed to be entered by the user in audio search
	 */
	@Test(groups = { "regression" }, priority = 11)
	public void VerifyWarningMessage_AudioSearchSpecialChars() throws InterruptedException {
		baseClass.stepInfo(
				"Verify that pre-defined list of special characters should be disallowed to be entered by the user in audio search");
		baseClass.stepInfo("Test case Id: RPMXCON-47135");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		String searchText = "~ ! @ # $ % & * ( ) { } | : ; , . ? / = '";
		search.VerifyaudioSearch_DisallowedSpecialCharachters(searchText);
		softAssertion.assertAll();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description Verify that user should be able to execute the saved audio
	 *              search which involve content in other languages - eg. German,
	 *              Japanese characters in search text
	 */
	@Test(groups = { "regression" }, priority = 12)
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
		search.saveSearchAdvanced_New(Search1, "My Saved Search");
		savedSearch.VerifySavedSearchExecuteStatus(Search1);
		baseClass.selectproject();
		String Search2 = "AudioSearch" + Utility.dynamicNameAppender();
		search.VerifyaudioSearchThreshold("です", "Japanese", "min");
		String actualPH2 = search.verifyPureHitsCount();
		baseClass.stepInfo("Audio Search is done using Japnese language  is done PureHit is : " + actualPH2 + "");
		search.saveSearchAdvanced_New(Search2, "My Saved Search");
		savedSearch.VerifySavedSearchExecuteStatus(Search2);
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description Verify that user should be able to save audio search which
	 *              involve content in other languages - eg. German, Japanese
	 *              characters in search text
	 */
	@Test(groups = { "regression" }, priority = 13)
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
		search.saveSearchAdvanced_New(Search1, "My Saved Search");
		baseClass.selectproject();
		String Search2 = "AudioSearch" + Utility.dynamicNameAppender();
		search.VerifyaudioSearchThreshold("です", "Japanese", "min");
		String actualPH2 = search.verifyPureHitsCount();
		baseClass.stepInfo(
				"Audio Search is done using Japnese language  PureHit is displayed as expected : " + actualPH2 + "");
		search.saveSearchAdvanced_New(Search2, "My Saved Search");
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @description Verify that user should be able to edit the existing saved audio
	 *              search which with the content in other languages - eg. German,
	 *              Japanese in search text
	 */
	@Test(dataProvider = "Users", groups = { "regression" }, priority = 14)
	public void VerifyAudio_ModifySearchWithOtherLanguages(String username, String password)
			throws InterruptedException {
		String Search1 = "AudioSearch" + Utility.dynamicNameAppender();
		baseClass.stepInfo("Test case Id: RPMXCON-47140");
		baseClass.stepInfo(
				"Verify that user should be able to edit the existing saved audio search which with the content"
						+ " in other languages - eg. German, Japanese in search text");
		loginPage.loginToSightLine(username, password);
		search.VerifyaudioSearchThreshold("right", "North American English", "min");
		String actualPH1 = search.verifyPureHitsCount();
		baseClass.stepInfo("Audio Search is done PureHit is : " + actualPH1);
		search.saveSearchAdvanced_New(Search1, "My Saved Search");
		try {
			savedSearch.savedSearch_Searchandclick(Search1);
			savedSearch.VerifyPureHitInSavedAudiosearch(Search1);
			baseClass.waitTillElemetToBeClickable(savedSearch.getSavedSearchEditButton());
			savedSearch.getSavedSearchEditButton().waitAndClick(10);
			driver.waitForPageToBeReady();
			search.modifyAudioSearch("grün", "German", "max");
			search.getPureHitsCount_ModifySearch().getText();
			baseClass.passedStep("Sucessfully modified the query and get the search results for the german language");
			driver.Navigate().refresh();
			driver.waitForPageToBeReady();
			search.modifyAudioSearch("が", "Japanese", "min");
			search.getPureHitsCount_ModifySearch().getText();
			baseClass.passedStep("Sucessfully modified the query and get the search results for the japanese language");
		} catch (Exception e) {
			e.printStackTrace();
			baseClass.failedStep("Threshold or PureHit count mismatched from sessions search page");

		}

	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @description Verify that user should be able to edit the saved audio search
	 *              which involve content in other languages - eg. German, Japanese
	 *              characters in search text
	 */
	@Test(dataProvider = "Users", groups = { "regression" }, priority = 15)
	public void VerifyAudio_ModifySearchTextAndLanguages(String username, String password) throws InterruptedException {
		String Search1 = "AudioSearch" + Utility.dynamicNameAppender();
		baseClass.stepInfo("Test case Id: RPMXCON-47139");
		baseClass.stepInfo("Verify that user should be able to edit the saved audio search which involve "
				+ "content in other languages - eg. German, Japanese characters in search text");
		loginPage.loginToSightLine(username, password);
		search.VerifyaudioSearchThreshold("grün", "German", "max");
		search.verifyPureHitsCount();
		baseClass.passedStep("Sucessfully modified the query and get the search results for the german language");
		search.saveSearchAdvanced_New(Search1, "My Saved Search");
		try {
			savedSearch.savedSearch_Searchandclick(Search1);
			savedSearch.VerifyPureHitInSavedAudiosearch(Search1);
			baseClass.waitTillElemetToBeClickable(savedSearch.getSavedSearchEditButton());
			savedSearch.getSavedSearchEditButton().waitAndClick(10);
			driver.waitForPageToBeReady();
			search.modifyAudioSearch("が", "Japanese", "min");
			search.getPureHitsCount_ModifySearch().getText();
			baseClass.passedStep("Sucessfully modified the query and get the search results for the japanese language");
		} catch (Exception e) {
			e.printStackTrace();
			baseClass.failedStep("Threshold or PureHit count mismatched from sessions search page");

		}

	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description Verify that user should be able to do audio search which involve
	 *              content in other languages - eg. German, Japanese, Mandarin by
	 *              being able to key in search text in these languages
	 */
	@Test(dataProvider = "Users", groups = { "regression" }, priority = 16)
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
			softAssertion.assertEquals(actualPH1, "2");
			softAssertion.assertEquals(actualPH2, "4");
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
		String ActualThresholdValue1 = search.VerifyaudioSearchThreshold("right", "North American English", "min");
		String actualPH1 = search.verifyPureHitsCount();
		baseClass.stepInfo(
				"Audio Search is done PureHit is : " + actualPH1 + "Threshold value is " + ActualThresholdValue1);
		search.saveSearchAdvanced_New(Search1, "My Saved Search");

		// Modifying the audio serach and saving it
		search.ModifyAudioSearch("morning well", "North American English", "max");
		String actualThreshold2 = search.GetConfidenceThresholdInSecondSearchResult().getText();
		String actualPH2 = search.getPureHitsCount_ModifySearch().getText();
		baseClass.stepInfo("Audio Search is done PureHit is : " + actualPH2 + "Threshold value is " + actualThreshold2);
		System.out.println("Audio Search is done PureHit is : " + actualPH2 + "Threshold value is " + actualThreshold2);
		search.saveSearchAdvanced_New(Search2, "My Saved Search");
		// Modifying the audio serach and saving it
		search.ModifyAudioSearch(null, "North American English", "min");
		String actualThreshold3 = search.GetConfidenceThresholdInSecondSearchResult().getText();
		String actualPH3 = search.getPureHitsCount_ModifySearch().getText();
		baseClass.stepInfo("Audio Search is done PureHit is : " + actualPH3 + "Threshold value is " + actualThreshold3);
		search.saveSearchAdvanced_New(Search3, "My Saved Search");
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
				search.ModifyAudioSearch("well", "North American English", "max");
				// saving the modified search by overwriting the existing search
				search.saveAsOverwrittenSearch("My Saved Search", searches, "First", "Success", "", null);

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
	}
	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 */
	@Test(groups = { "regression" }, priority = 18,enabled=false)
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
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
		}
		try {
			loginPage.logout();
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
