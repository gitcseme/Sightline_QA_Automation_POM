package testScriptsRegressionSprint28;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class AdvancedSearchRegression_28 {
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
	 * @Description Verify that - After impersonation (RMU to Reviewer) - User can search NON-Audio - Remarked documents in Advanced Search.
	 */
	@Test(description = "RPMXCON-48560", enabled = true, groups = { "regression" })
	public void verifyAfterImpersonateAddRemarkDocumentsInAdvancedSearch() throws ParseException, Exception {
		int remarkCount1 = 1;
		String selectField = "Remark";
		DocViewPage docView = new DocViewPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-48560");
		baseClass.stepInfo("Verify that - After impersonation (RMU to Reviewer) - User can search NON-Audio - Remarked documents in Advanced Search.");

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Successfully logged in as '" + Input.rmu1userName + "'");

		// performing metaData search and adding pureHit to cart and viewing in docView
		sessionSearch.advancedMetaDataSearch(Input.metaDataName, null, Input.metaDataCustodianNameInput, null);
		baseClass.stepInfo("performing advanced metadata search.");
		sessionSearch.viewInDocView();

		// adding remark and performing search using remark
		docView.verifyRemarkIsAdded(Input.reviewed);
		baseClass.stepInfo("adding Remark to document");

		// impersonate RMU to REV
		baseClass.impersonateRMUtoReviewer();

		int pureHit1 = sessionSearch.getCommentsOrRemarksCount_AdvancedSearch(selectField, Input.reviewed);
		// verifying the pureHit count with number of remark added
		baseClass.digitCompareEquals(pureHit1, remarkCount1, "pureHit count match with remark count",
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
	 * Author :Arunkumar date: 15/12/2022 TestCase Id:RPMXCON-49289
	 * Description :Verify that information message does not appear above the Tiles and below 
	 * "Your Search Results" labels in Result Screen when the user Edit search not having any
	 *  Advanced Search option. 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49289",enabled = true, groups = { "regression" })
	public void verifyInformationMessageWhenNotHavingOption() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49289");
		baseClass.stepInfo("verify information message when the user Edit search not having any option.");
		String searchName = "search"+Utility.dynamicNameAppender();
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("perform advanced metadata search");
		sessionSearch.MetaDataSearchInAdvancedSearch(Input.metaDataName, Input.custodianName_Andrew);
		baseClass.stepInfo("save the search result without selecting any option");
		sessionSearch.saveSearchadvanced(searchName);
		System.out.println("saved search"+searchName);
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
	 * Author :Arunkumar date: 15/12/2022 TestCase Id:RPMXCON-49274
	 * Description :Verify that information message does not appear above the Tiles and below 
	 * "Your Search Results" labels in Result Screen when the user Select none of 
	 * Advanced Search option.  
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49274",enabled = true, groups = { "regression" })
	public void verifyInformationMessageWhenNoSelection() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49274");
		baseClass.stepInfo("verify information message when the user select none of option.");
		
		//Login as PA
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
	 * @Description :Verify that pure hit tile does not get corrupted (CSS Removed) in shopping cart when
	 *  search goes to BackGround and User elect "When All Results Are Ready" on Advanced Search Screen.RPMXCON-48651
	 */
	
	@Test(description = "RPMXCON-48651", enabled = true, groups = { "regression" })
	public void verifyPureHitTileDoesNotGetCorruptedInShoppingCartAndUserSelectWhenAllResultsAreReadyOnAdvanSearch() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-48651 Advanced Search.");
		baseClass.stepInfo("Verify that pure hit tile does not get corrupted (CSS Removed) in shopping cart when search goes to BackGround and User elect \"When All Results Are Ready\" on Advanced Search Screen.");
		
		// login
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		
	    // Configure valid Query and Click on "Search" button
		baseClass.stepInfo("Configure valid Query and Click on \"Search\" button.");
		sessionSearch.AdvContentSearchWithoutPopHandling(Input.searchStringStar);
		
		// Click on "When All Results Are Ready" button
		sessionSearch.handleWhenAllResultsBtnInUncertainPopup();
		baseClass.stepInfo("Click on \"When All Results Are Ready\" button.");
		//Drag only Pure Hit tile into Shipping cart and wait for other tiles appear (Thread document/Near Duplicate/Family Member)  
		baseClass.stepInfo("Drag only Pure Hit tile into Shipping cart and wait for other tiles appear (Thread document/Near Duplicate/Family Member).");
		sessionSearch.addPureHit();
		sessionSearch.verifyAllTilesResultsinAdvSrcScrn();
		
		// Verify that pure hit tile does not get corrupted (CSS Removed) in shopping cart when search goes to BackGround and User Select "When All Results Are Ready" on Advanced Search Screen
		baseClass.ValidateElement_Presence(sessionSearch.getPureHitsCount(),"PureHit Tile");
		baseClass.passedStep("Verified that pure hit tile does not get corrupted (CSS Removed) in shopping cart when search goes to BackGround and User Select \"When All Results Are Ready\" on Advanced Search Screen.");
	
		// logOut
		loginPage.logout();
	}
	
	/**
	 * @author
	 * @Description :Verify that result appears for proximity having
	 *  2 Phrases within brackets in Advanced Search Query Screen.RPMXCON-57335
	 */
	@Test(description = "RPMXCON-57335", enabled = true, groups = { "regression" })
	public void verifyResultAppearsForProximityHavingTwoPhrasesWithinBracketsInAdvancedSearch() {
		
		String searchString = "\"(\"development methodology\") (\"money related\")\"~5";
		String exampleSearchString = "\"(\"development methodology\") (\"money related\")\"~5";

		baseClass.stepInfo("Test case Id: RPMXCON-57335 Advanced Search.");
		baseClass.stepInfo("Verify that result appears for proximity having 2 Phrases within brackets in Advanced Search Query Screen.");

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
		// Verify that result appears for proximity having 2 Phrases within brackets in Advanced Search Query Screen.
		int searchStringPureHit = sessionSearch.returnPurehitCount();
		baseClass.passedStep(
				"Verified that result appears for proximity having 2 Phrases within brackets in Advanced Search Query Screen.");

		// performing search for given example proximity search query.
		baseClass.stepInfo("performing search for given example proximity search query.");
		sessionSearch.advancedNewContentSearchNotPureHit(exampleSearchString);
		sessionSearch.tallyContinue(5);
		int exampleSearchStringPureHit = sessionSearch.returnPurehitCount();

		// verify that pureHit appear for proximity having 2 Phrases within brackets match with pureHit appear for given example proximity Search Query.
		assertion.assertEquals(searchStringPureHit, exampleSearchStringPureHit);
		assertion.assertAll();
		baseClass.passedStep("verified that pureHit appear for proximity having 2 Phrases within brackets match with pureHit appear for given example proximity Search Query.");

		// logOut
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
