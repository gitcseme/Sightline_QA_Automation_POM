package testScriptsRegressionSprint23;

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
import pageFactory.DataSets;
import pageFactory.DocListPage;
import pageFactory.DocViewRedactions;
import pageFactory.IngestionPage_Indium;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class AdvanceSearchRegression_23 {
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

	

	/**
	 * @author
	 * @Date: 13/8/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Verify that belly band message appears when user configured
	 *              Proximity with Left double quotes only and combined with other
	 *              criteria in Advanced Search Query Screen. RPMXCON-57300
	 */
//   @Test(description = "RPMXCON-57300", enabled = true, dataProvider = "invaildProximitySearchQueriesLeftQuotesOnly", groups = {
//			"regression" }, priority = 6)
	public void verifyBellyBandMessageForSearchQueriesWithOperatorHavingLeftDoubleQuoteOnly(String[] searches,
			String[] operator) throws InterruptedException {

		List<String> searchQueries = Arrays.asList(searches);
		String operatorCombine = operator[0];

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
		baseClass.stepInfo("Input String : ");
		baseClass.printListString(searchQueries);
		sessionSearch.advancedMultipleContentSearchWithOperator(searchQueries, operatorCombine);
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
	//@Test(description = "RPMXCON-57301", enabled = true, dataProvider = "invaildProximitySearchQueriesRightQuotesOnly", groups = {
	//		"regression" })
	public void verifyBellyBandMessageForSearchQueriesWithOperatorHavingRightDoubleQuoteOnly(String searchString1,
			String searchString2, String operator) throws InterruptedException {

		List<String> searchQueries = new ArrayList<String>(Arrays.asList(searchString1, searchString2));

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
		baseClass.stepInfo("Input String : " + searchString1 + " || " + operator + " || " + searchString2);
		sessionSearch.advancedMultipleContentSearchWithOperator(searchQueries, operator);
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
