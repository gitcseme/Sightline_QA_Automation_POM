package sightline.advancedSearch;

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
import pageFactory.BaseClass;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class AdvanceSearchRegression_2_21 {

	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	BaseClass baseClass;
	Input in;
	SoftAssert assertion;

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

	@DataProvider(name = "Users")
	public Object[][] CombinedSearchwithUsers() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password }, { Input.pa1userName, Input.pa1password },
				{ Input.rev1userName, Input.rev1password } };
		return users;
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