package sightline.advancedSearch;

import java.awt.AWTException;
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
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class AdvanceSearch_Phase2_Regression {
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

	}

	@DataProvider(name = "paRmuRevUsers")
	public Object[][] paRmuRevUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, "PA" },
				{ Input.rev1userName, Input.rev1password, "REV" }, { Input.rmu1userName, Input.rmu1password, "RMU" } };
		return users;
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
		
		
		/**
		 * Author :Arunkumar date: 07/07/2022  TestCase Id:RPMXCON-49306 
		 * Description :Verify that Conceptual tile return the result for 
		 * WorkProduct >> Security Group  Search in Advanced Search Screen.
		 * 
		 */
		@Test(description ="RPMXCON-49306",groups = { "regression" })
		public void verifyConceptualTileForSecurityGroupQuery() throws InterruptedException {
			
			baseClass.stepInfo("Test case Id: RPMXCON-49306");
			baseClass.stepInfo(
					"Verify that Conceptual tile return the result for WorkProduct >> Security Group  Search in Advanced Search Screen.");
			//Login as PA
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
		 * Author :Arunkumar date: 07/07/2022  TestCase Id:RPMXCON-49310 
		 * Description :Verify that Conceptual tile return the result for 
		 * WorkProduct >> Assignments  Search in Advanced Search Screen
		 * 
		 */
		@Test(description ="RPMXCON-49310",groups = { "regression" })
		public void verifyConceptualTileForAssignmentsQuery() throws InterruptedException {
			
			baseClass.stepInfo("Test case Id: RPMXCON-49310");
			baseClass.stepInfo(
					"Verify that Conceptual tile return the result for WorkProduct >> Security Group  Search in Advanced Search Screen.");
			String assignmentName = "Aassignment"+Utility.dynamicNameAppender();
			//Login as PA
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
		 * Author :Arunkumar date: 29/06/2022 TestCase Id:RPMXCON-57148 
		 * Description :Verify that application displays correct options 
		 * for "DocDateDateOnly" field on Advanced Search screen.
		 */
		@Test(description ="RPMXCON-57148",groups = { "regression" })
		public void verifyDocDateOnlyFieldOnAdvancedSearch() throws InterruptedException{
			
			baseClass.stepInfo("Test case Id: RPMXCON-57148");
			baseClass.stepInfo("Verify 'DocDateDateOnly' field on advanced search");
			//Login as PA
			loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
			sessionSearch.navigateToAdvancedMetaDataSearch();
			//Verify the options available
			sessionSearch.verifyOptionsDisplayForDocDateDateOnlyField();
			loginPage.logout();
			
		}
		
		/**
		 * Author :Arunkumar date: 29/06/2022  TestCase Id:RPMXCON-57043 
		 * Description :To verify an an PA user login, I will be able to select all Folder
		 * from Folder column under Work Product tab & set that as a search criteria for advanced search 
		 * 
		 */
		@Test(description ="RPMXCON-57043",groups = { "regression" })
		public void verifyAllFolderSelectionUnderWorkProduct() throws InterruptedException {
			
			baseClass.stepInfo("Test case Id: RPMXCON-57043");
			baseClass.stepInfo("Verify all folder selection under work product and set as search criteria");
			//Login as PA
			loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
			baseClass.stepInfo("Navigate to advanced search from search page");
			sessionSearch.advanceSearchByFolder("All Folders");
			if(sessionSearch.getSearchCriteriaValue().isElementAvailable(10)) {
				baseClass.passedStep("Able to select all folder under work product and set as search criteria");
			}
			else {
				baseClass.failedStep("unable to set all folder as search criteria");
			}
			loginPage.logout();
		}
		
		/**
		 * Author :Arunkumar date: 30/06/2022  TestCase Id:RPMXCON-57256 
		 * Description :Verify that Dropped tiles are retained in shopping cart when User Navigates
		 *  Advanced Search (Pure Hit) >> "Manage >> Categorize" screen and Come back to Search Page.
		 * 
		 */
		@Test(description ="RPMXCON-57256",groups = { "regression" })
		public void verifyDroppedTileRetainedWhenNavigateToCategorize() throws InterruptedException {
			
			baseClass.stepInfo("Test case Id: RPMXCON-57256");
			baseClass.stepInfo(
					"Verify that Dropped tiles are retained in shopping cart when User Navigates Advanced Search (Pure Hit) >> \"Manage >> Categorize\" screen and Come back to Search Page.");
			//Login as PA
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
		 * Author :Arunkumar date: 30/06/2022  TestCase Id:RPMXCON-57257 
		 * Description :Verify that Dropped tiles are retained in shopping cart when User Navigates 
		 * Advanced Search (Pure Hit) >> "Manage >>Users" screen and Come back to Search Page.
		 * 
		 */
		@Test(description ="RPMXCON-57257",groups = { "regression" })
		public void verifyDroppedTileRetainedWhenNavigateToUserPage() throws InterruptedException {
			
			baseClass.stepInfo("Test case Id: RPMXCON-57257");
			baseClass.stepInfo(
					"Verify that Dropped tiles are retained in shopping cart when User Navigates Advanced Search (Pure Hit) >> \"Manage >>Users\" screen and Come back to Search Page.");
			//Login as PA
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
		 * Author :Arunkumar date: 07/07/2022  TestCase Id:RPMXCON-57081 
		 * Description :Verify that Bulk Release Action is working properly on Advanced Search result Screen 
		 * 
		 */
		@Test(description ="RPMXCON-57081",groups = { "regression" })
		public void verifyBulkReleaseOnAdvancedSearch() throws InterruptedException {
			
			baseClass.stepInfo("Test case Id: RPMXCON-57081");
			baseClass.stepInfo(
					"Verify that Bulk Release Action is working properly on Advanced Search result Screen");
			//Login as PA
			loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
			baseClass.stepInfo("Navigate to advanced search and search query");
			sessionSearch.advancedContentSearch(Input.testData1);
			baseClass.stepInfo("Add purehit to shopping cart and perform bulk release");
			sessionSearch.bulkRelease(Input.securityGroup);
			baseClass.passedStep("Bulk release action performed successfully on advanced search");
			loginPage.logout();
		}
		
		/**
		 * Author :Arunkumar date: 07/07/2022  TestCase Id:RPMXCON-57062 
		 * Description :Verify that Advanced Search is working properly for MasterDate Metadata with Range Operator
		 * 
		 */
		@Test(description ="RPMXCON-57062",dataProvider = "Users",groups = { "regression" })
		public void verifyMasterDateMetaDataWithRange(String username, String password) throws InterruptedException {
			
			baseClass.stepInfo("Test case Id: RPMXCON-57062");
			baseClass.stepInfo(
					"Verify that Advanced Search is working properly for MasterDate Metadata with Range Operator");
			//Login as PA
			loginPage.loginToSightLine(username,password);
			baseClass.stepInfo("perform 'MasterDate' metadata search with Range operator");
			sessionSearch.advancedMetaDataSearch(Input.masterDateText, Input.range,"2021-01-01","2022-01-01");
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
