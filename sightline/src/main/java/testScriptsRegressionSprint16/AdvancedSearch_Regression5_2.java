package testScriptsRegressionSprint16;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

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

public class AdvancedSearch_Regression5_2 {

	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	TagsAndFoldersPage tagPage;
	BaseClass baseClass;
	Input in;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

	//	in = new Input();
	//	in.loadEnvConfig();
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		sessionSearch = new SessionSearch(driver);
		savedSearch = new SavedSearch(driver);
		tagPage = new TagsAndFoldersPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		
	}
	
	/**
	 * Author :Arunkumar date: 01/07/2022  TestCase Id:RPMXCON-57158 
	 * Description :Verify that - Application returns all the documents which are available under selected group 
	 * with OR operator and production optional filters - status  in search result.
	 * 
	 */
	@Test(description ="RPMXCON-57158",groups = { "regression" })
	public void verifyDocumentWithORProdOptFilters() throws InterruptedException{
		
		baseClass.stepInfo("Test case Id: RPMXCON-57158 ");
		baseClass.stepInfo(
				"Verify that - Application returns all the documents which are available under selected group with OR operator and production optional filters - status  in search result");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		sessionSearch.switchToWorkproduct();
		baseClass.stepInfo("Configure query with security group OR production");
		sessionSearch.configureQueryWithSecurityGroupAndProductionStatus(Input.securityGroup, "OR",false);
		baseClass.stepInfo("Search query and verify search result");
		sessionSearch.verifyDocsCountAvailableInSgWithSearchResult();
		loginPage.logout();	
	}
	
	/**
	 * Author :Arunkumar date: 01/07/2022  TestCase Id:RPMXCON-57159 
	 * Description :Verify that - Application returns all the documents which are available under selected group
	 *  with OR operator and production optional filters - Date Range in search result. 
	 * 
	 */
	@Test(description ="RPMXCON-57159",groups = { "regression" })
	public void verifyDocumentWithORProdOptFiltersDateRange() throws InterruptedException{
		baseClass.stepInfo("Test case Id: RPMXCON-57159 ");
		baseClass.stepInfo(
				"Verify that - Application returns all the documents which are available under selected group with OR operator and production optional filters - Date Range in search result");
		//login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		sessionSearch.switchToWorkproduct();
		baseClass.stepInfo("Configure query with security group OR production status with date");
		sessionSearch.configureQueryWithSecurityGroupAndProductionStatus(Input.securityGroup, "OR",true);
		baseClass.stepInfo("Search query and verify search result");
		sessionSearch.verifyDocsCountAvailableInSgWithSearchResult();
		loginPage.logout();		
	}
	
	/**
		 * Author :Arunkumar date: 04/07/2022  TestCase Id:RPMXCON-57258 
		 * Description :Verify that Dropped tiles are retained in shopping cart when User Navigates
		 * Advanced Search (Pure Hit) >> "View in Doc View" screen and Come back to Search Page.
		 * 
		 */
		@Test(description ="RPMXCON-57258",groups = { "regression" })
		public void verifyDroppedTileRetainedWhenNavigateToDocViewPage() throws InterruptedException {
			
			baseClass.stepInfo("Test case Id: RPMXCON-57258");
			baseClass.stepInfo(
					"Verify that Dropped tiles are retained in shopping cart when User Navigates Advanced Search (Pure Hit) >> \"View in Doc View\" screen and Come back to Search Page.");
			//Login as PA
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
		 * Author :Arunkumar date: 04/07/2022  TestCase Id:RPMXCON-57254 
		 * Description :Verify that Dropped tiles are retained in shopping cart when User Navigates 
		 * Advanced Search (Pure Hit) >> "Analyze in Concept Explorer" screen and Come back to Search Page.
		 * 
		 */
		@Test(description ="RPMXCON-57254",groups = { "regression" })
		public void verifyDroppedTileRetainedWhenNavigateToConceptExplorerPage() throws InterruptedException {
			
			baseClass.stepInfo("Test case Id: RPMXCON-57254");
			baseClass.stepInfo(
					"Verify that Dropped tiles are retained in shopping cart when User Navigates Advanced Search (Pure Hit) >> \"Analyze in Concept Explorer\" screen and Come back to Search Page.");
			//Login as PA
			loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
			baseClass.stepInfo("Perform search with metadata");
			sessionSearch.advancedMetaDataSearch(Input.metaDataName, null, Input.custodianName_Andrew, null);
			baseClass.stepInfo("Add pure hit and navigate to concept explorer page");
			sessionSearch.addPurehitAndActionAsConceptOrComm(false,true);
			driver.waitForPageToBeReady();
			baseClass.stepInfo("Navigate back to search page and verify tile status");
			sessionSearch.navigateToSearchPageAndVerifyTileStatus();
			loginPage.logout();
		}
		
		/**
		 * Author :Arunkumar date: 04/07/2022  TestCase Id:RPMXCON-57261 
		 * Description :Verify that Dropped tiles are retained in shopping cart when User Navigates
		 * Basic Search (Pure Hit) >> "Manage >> Assignments" screen and Come back to Search Page.
		 * 
		 */
		@Test(description ="RPMXCON-57261",groups = { "regression" })
		public void verifyDroppedTileRetainedWhenNavigateToAssignmentsPage() throws InterruptedException {
			
			baseClass.stepInfo("Test case Id: RPMXCON-57261");
			baseClass.stepInfo(
					"Verify that Dropped tiles are retained in shopping cart when User Navigates Basic Search (Pure Hit) >> \"Manage >> Assignments\" screen and Come back to Search Page.");
			//Login as PA
			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			baseClass.stepInfo("Perform search with metadata");
			sessionSearch.basicSearchWithMetaDataQuery(Input.custodianName_Andrew,Input.metaDataName);
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
			String eleValue[] = { "“ProximitySearch (Truthful OR Recall)”~9",
					"\"ProximitySearch (Truthful OR Recall)\"~9", "“ProximitySearch (Truthful OR Recall)”~9" };
			driver.scrollingToBottomofAPage();
			for (int i = 0; i < eleValue.length; i++) {	
				sessionSearch.wrongQueryAlertAdvanceSaerch(eleValue[i], 13, "non-fielded", null);
				assertion.assertNotNull(sessionSearch.verifyPureHitsCount(),
						"purehit is null after entering two proximity term with OR ");
				baseClass.passedStep("Pure hit count is not null and value displayed is " +sessionSearch.verifyPureHitsCount());
				if (i != 2) {
					baseClass.selectproject();
				}
			}
			baseClass.passedStep("Sucessfully Verified  that result appears for proximity having  "
					+ "2 words with OR in Advanced Search Query Screen.");
			loginPage.logout();
		}

		@DataProvider(name = "Users")
		public Object[][] CombinedSearchwithUsers() {
			Object[][] users = { { Input.rmu1userName, Input.rmu1password }, { Input.pa1userName, Input.pa1password },
					{ Input.rev1userName, Input.rev1password } };
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
		System.out.println("**Executed Advanced search Regression5**");
	}
}
