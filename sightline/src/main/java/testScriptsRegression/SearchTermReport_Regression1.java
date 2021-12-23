package testScriptsRegression;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import pageFactory.ABMReportPage;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CommunicationExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SearchTermReportPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.TallyPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class SearchTermReport_Regression1 {
	Driver driver;
	LoginPage lp;
	SessionSearch search;
	BaseClass bc;
	String hitsCountPA;
	String hitsCountRMU;
	String hitsCountPA1;
	String hitsCountPA2;
	String hitsCountRMU1;
	String hitsCountRMU2;
	SearchTermReportPage st;
	String saveSearchNamePA = "ST" + Utility.dynamicNameAppender();
	String saveSearchNameRMU = "ST" + Utility.dynamicNameAppender();
	String saveSearchNamePA1 = "ST" + Utility.dynamicNameAppender();
	String saveSearchNameRMU1 = "ST" + Utility.dynamicNameAppender();
	String saveSearchNamePA2 = "ST" + Utility.dynamicNameAppender();
	String saveSearchNameRMU2= "ST" + Utility.dynamicNameAppender();
	String[] savedSearchNamesPA= {saveSearchNamePA2,saveSearchNamePA1,saveSearchNamePA};
	String[] savedSearchNamesRMU= {saveSearchNameRMU2,saveSearchNameRMU1,saveSearchNameRMU};
	String[] searchData= {"test","comments","null"};
	String[] Hits= {hitsCountPA1,hitsCountPA2,hitsCountPA};
	String[] HitsRMU= {hitsCountRMU1,hitsCountRMU2,hitsCountRMU};
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		Input in = new Input();
		in.loadEnvConfig();

	 // Open browser
		driver = new Driver();
		bc = new BaseClass(driver);
		// Login as a PA
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		// Search and save it
		search = new SessionSearch(driver);
		for(int i=0;i<savedSearchNamesPA.length;i++) {
			search.basicContentSearch(searchData[i]);
			search.saveSearch(savedSearchNamesPA[i]);
			Hits[i] = search.verifyPureHitsCount();	
			if(i!=2) {bc.selectproject();}
			}
		lp.logout();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		// Search and save it
		for(int i=0;i<savedSearchNamesRMU.length;i++) {
			search.basicContentSearch(searchData[i]);
			search.saveSearch(savedSearchNamesRMU[i]);
			HitsRMU[i] = search.verifyPureHitsCount();	
			if(i!=2) {bc.selectproject();}
		}
		lp.logout();
		lp.quitBrowser();   

	}

	@Test(dataProvider = "Users_PARMU", groups = { "regression" }, priority = 1)
	public void ValidateSearchTermreport_BulkTag(String username, String password, String role)
			throws InterruptedException, AWTException {
		bc.stepInfo("Test case Id: RPMXCON-56497");
		bc.stepInfo("To verify that Bulk action_Bulk Tag is working on Search Term Report");
		st = new SearchTermReportPage(driver);
		lp = new LoginPage(driver);
		lp.loginToSightLine(username, password);
		String saveSearchName = null;
		String hitsCount=null;
		SoftAssert	softAssertion = new SoftAssert();
		bc.stepInfo("Logged in as -" + role);
		if (role == "RMU") {
			saveSearchName = saveSearchNameRMU;
			hitsCount=hitsCountRMU;
		}
		if (role == "PA") {
			saveSearchName = saveSearchNamePA;
			hitsCount=hitsCountPA;
		}
		String TagName = "STRTag" + Utility.dynamicNameAppender();
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		String actualHitCount = st.GenerateReport(saveSearchName);
		softAssertion.assertEquals(actualHitCount, hitsCount);
		bc.stepInfo("Report generated for selected searche ");
		st.BulkTag(TagName);
		SessionSearch search = new SessionSearch(driver);
		bc.stepInfo("Bulk tagging of documents from STR is done and tag name is " + TagName);
		search.switchToWorkproduct();
		search.selectTagInASwp(TagName);
		search.serarchWP();
		softAssertion.assertEquals(actualHitCount, search.verifyPureHitsCount());
		softAssertion.assertAll();
		bc.stepInfo("Document Count associated to selected tag is as excpeted");
	}

	@Test(dataProvider = "Users_PARMU", groups = { "regression" }, priority = 2)
	public void ValidateSearchTermreport_BulkFolder(String username, String password, String role)
			throws InterruptedException, AWTException {
		bc.stepInfo("Test case Id: RPMXCON-56498");
		bc.stepInfo("To verify that Bulk action_Bulk Folder is working on Search Term Report");
		st = new SearchTermReportPage(driver);
		lp = new LoginPage(driver);
		lp.loginToSightLine(username, password);
		String saveSearchName = null;
		String hitsCount=null;
		SoftAssert	softAssertion = new SoftAssert();
		bc.stepInfo("Logged in as -" + role);
		if (role == "RMU") {
			saveSearchName = saveSearchNameRMU;
			hitsCount=hitsCountRMU;
		}
		if (role == "PA") {
			saveSearchName = saveSearchNamePA;
			hitsCount=hitsCountPA;
		}
		String folderName = "STRFolder" + Utility.dynamicNameAppender();
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		String actualHitCount = st.GenerateReport(saveSearchName);
		softAssertion.assertEquals(actualHitCount, hitsCount);
		bc.stepInfo("Report generated for selected search");
		st.BulkFolder(folderName);
		bc.stepInfo("Bulk Foldering of documents from STR is done and tag name is " + folderName);
		SessionSearch search = new SessionSearch(driver);
		search.switchToWorkproduct();
		search.selectFolderInASwp(folderName);
		search.serarchWP();
		softAssertion.assertEquals(actualHitCount, search.verifyPureHitsCount());
		softAssertion.assertAll();
		bc.stepInfo("Document Count associated to selected folder is as excpeted");
	}

	@Test(dataProvider = "Users_PARMU", groups = { "regression" }, priority = 3)
	public void ValidateSearchTermreport_viewInDocView(String username, String password, String role)
			throws InterruptedException, AWTException {
		bc.stepInfo("Test case Id: RPMXCON-56496");
		bc.stepInfo("To verify that Bulk action_View in Doc View is working on Search Term Report");
		st = new SearchTermReportPage(driver);
		lp = new LoginPage(driver);
		lp.loginToSightLine(username, password);
		String saveSearchName = null;
		String hitsCount=null;
		SoftAssert	softAssertion = new SoftAssert();
		bc.stepInfo("Logged in as -" + role);
		if (role == "RMU") {
			saveSearchName = saveSearchNameRMU;
			hitsCount=hitsCountRMU;
		}
		if (role == "PA") {
			saveSearchName = saveSearchNamePA;
			hitsCount=hitsCountPA;
		}
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		String actualHitCount = st.GenerateReport(saveSearchName);
		softAssertion.assertEquals(actualHitCount, hitsCount);
		bc.stepInfo("Report  generated for selected search and pure hit count also verified.");
		st.ViewInDocView();
		DocViewPage dc = new DocViewPage(driver);
		String ActualCount = dc.verifyDocCountDisplayed_DocView();
		System.out.println(ActualCount);
		softAssertion.assertEquals(actualHitCount, ActualCount);
		softAssertion.assertAll();
		bc.stepInfo("Document Count associated to selected saved search in STR page is  displayed"
				+ " as excpeted in doc view page");
	}

	@Test(groups = { "regression" }, priority = 4)
	public void ValidateSearchTermreport_BulkAssign()
			throws InterruptedException, AWTException {
		bc.stepInfo("Test case Id: RPMXCON-56501");
		bc.stepInfo("To verify that Bulk action_Bulk Assign is working on Search Term Report");
		st = new SearchTermReportPage(driver);
		lp = new LoginPage(driver);
		String assignmentName = "STRAssign" + Utility.dynamicNameAppender();
		lp.loginToSightLine( Input.rmu1userName, Input.rmu1password);
		SoftAssert	softAssertion = new SoftAssert();
		String saveSearchName = saveSearchNameRMU;
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		String actualHitCount = st.GenerateReport(saveSearchName);
		softAssertion.assertEquals(actualHitCount, hitsCountRMU);
		bc.stepInfo("Report  generated for selected search and pure hit count also verified. ");
		st.BulkAssign();
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		agnmt.FinalizeAssignmentAfterBulkAssign();
		agnmt.createAssignment_fromAssignUnassignPopup(assignmentName, Input.codeFormName);
		agnmt.getAssignmentSaveButton().waitAndClick(10);
		bc.selectproject();
		bc.stepInfo("Created a assignment by assigning saved search documents from search term report page -"
				+ assignmentName);
		String ActualCount = agnmt.verifydocsCountInAssgnPage(assignmentName);
	//	agnmt.deleteAllAssignments("STR");
		System.out.println(ActualCount);
		softAssertion.assertEquals(actualHitCount, ActualCount);
		softAssertion.assertAll();
		bc.passedStep("Document Count associated to selected Saved search in Search term report is displayed  as"
				+ " excpeted in manage assignments page after assigning the docs to assignment .");
	}
	/**
	 * @author Jayanthi.ganesan
	 * @param username
	 * @param password
	 * @param role
	 * @throws InterruptedException
	 */
	@Test(dataProvider = "Users_PARMU", groups = { "regression" }, priority =5 , enabled = true)
	public void  VerifyTotalDocsSelected(String username, String password, String role) throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-56507");
		bc.stepInfo("To verify that total selected unique count will be displayed as \"Total Selected\" under "
				+ "Actions drop down");
		st = new SearchTermReportPage(driver);
		lp = new LoginPage(driver);
		lp.loginToSightLine(username, password);
		bc.stepInfo("Logged in as -" + role);
		String saveSearchName = null;
		SoftAssert	softAssertion = new SoftAssert();
		if (role == "RMU") {
			saveSearchName = saveSearchNameRMU;
		}
		if (role == "PA") {
			saveSearchName = saveSearchNamePA;
		}
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		String actualHitCount = st.GenerateReport(saveSearchName);
		bc.passedStep("Report  generated for selected search ");
		bc.waitForElement(st.getTotalSelectedCount());
		bc.stepInfo("Total selected Hit count "+actualHitCount);
		bc.waitTime(3);
		String TotalCount=st.getTotalSelectedCount().getText();
		bc.stepInfo("Total selected doc count displayed under action button "+TotalCount);
		softAssertion.assertEquals(actualHitCount,TotalCount );
		softAssertion.assertAll();
		bc.passedStep("Sucessfully verified that total selected unique count will be displayed as Total Selected under Actions drop down");
		
	}
	@Test(dataProvider = "Users_PARMU",groups = { "regression" }, priority = 6)
	public void navigateToSearchTermReport(String username, String password, String role) {
		bc.stepInfo("Test case Id: RPMXCON-56361");
		bc.stepInfo("To verify that Search Term Report link is provided on Report Landing Page and report should be opened on clicking on link");
		st = new SearchTermReportPage(driver);
		lp = new LoginPage(driver);
		lp.loginToSightLine(username, password);
		bc.stepInfo("Logged in as -" + role);
        driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
        String expectedURL=Input.url+"DataAnalysisReport/SearchTermReport";
        bc.waitForElement(st.getSearchTermReport());
        if(st.getSearchTermReport().isDisplayed()) {
        	bc.passedStep("Search Term Report link is displayed in reports landing Page");
            st.getSearchTermReport().Click();
            SoftAssert    softAssertion = new SoftAssert();
            driver.waitForPageToBeReady();
            softAssertion.assertEquals(expectedURL,driver.getUrl());
            softAssertion.assertAll();
            bc.passedStep("Sucessfully navigated to  Search Term Report Page");
        	
        }
        else
        {
        	bc.failedStep("Search Term Report link is not found in Reports landing page.");
        }
        
    }
	/**
	 * @author Jayanthi.ganesan
	 * @param username
	 * @param password
	 * @param role
	 * @throws InterruptedException
	 */
	@Test(dataProvider = "Users_PARMU", groups = { "regression" }, priority =7 , enabled = true)
	public void  VerifyAggregateSummary(String username, String password, String role) throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-56482");
		bc.stepInfo("To verify that  two aggregate unique document counts displays under the Summary section on Search Term Report.");
		st = new SearchTermReportPage(driver);
		lp = new LoginPage(driver);
		lp.loginToSightLine(username, password);
		bc.stepInfo("Logged in as -" + role);
		
		SoftAssert	softAssertion = new SoftAssert();
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		if(role=="PA") {
		    st.GenerateReportWithAllSearches(savedSearchNamesPA);}
			
			if(role=="RMU") {
			    st.GenerateReportWithAllSearches(savedSearchNamesRMU);}
		bc.passedStep("Report  generated for selected search ");
		int i=st.getIndex("UNIQUE HITS");
		int j=st.getIndex("UNIQUE FAMILY HITS");
		List<Integer> Hits = new ArrayList<>();
		List<Integer> familyHits = new ArrayList<>();
		Hits=st.getColumn(st.getColumnValues(i));
		familyHits=st.getColumn(st.getColumnValues(j));
		System.out.println(Hits);
		System.out.println(familyHits);
		int uniqueHitsSum=st.sumUsingList(Hits);
		int uniquefamilyHitsSum=st.sumUsingList(familyHits);
		bc.waitForElement(st.getUniqueHitsFromSummary());
		int expecteduniqueHits=Integer.parseInt( st.getUniqueHitsFromSummary().getText());
		int expecteduniquefamilyHits=Integer.parseInt(st.getUniqueFamilyHitsFromSummary().getText());
		bc.stepInfo("Sum of all searches unique Hits  "+uniqueHitsSum);
		bc.stepInfo("Aggregate Unique hits value Displayed in summary of STR Page"+st.getUniqueHitsFromSummary().getText());
		bc.stepInfo("Sum of all searches unique family  Hits  "+uniquefamilyHitsSum);
		bc.stepInfo("Aggregate Unique family hits value Displayed in summary of STR Page"+st.getUniqueFamilyHitsFromSummary().getText());
		softAssertion.assertEquals(uniqueHitsSum,expecteduniqueHits);
		softAssertion.assertEquals(uniquefamilyHitsSum,expecteduniquefamilyHits);
		softAssertion.assertAll();
		bc.passedStep("Sucessfully verified that aggregate unique document counts displays under the Summary section on Search Term Report is sum of all Unique Doc values under report table.");
		
	}
	/**
	 * @author Jayanthi.ganesan
	 * @param username
	 * @param password
	 * @param role
	 * @throws InterruptedException
	 */
	//@Test(dataProvider = "Users_PARMU", groups = { "regression" }, priority =8, enabled = true)
	public void  VerifySorting(String username, String password, String role) throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-56588");
		bc.stepInfo("Search Term Report - Verify sorting feature on Unique Hits and Unique Family Hits columns");
		st = new SearchTermReportPage(driver);
		lp = new LoginPage(driver);
		lp.loginToSightLine(username, password);
		bc.stepInfo("Logged in as -" + role);
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		if(role=="PA") {
	    st.GenerateReportWithAllSearches(savedSearchNamesPA);}
		
		if(role=="RMU") {
		    st.GenerateReportWithAllSearches(savedSearchNamesRMU);}
			
		bc.passedStep("Report  generated for All saved searches.");
		st.verifyColumnSorting("UNIQUE HITS",st.getUniqueHits());
		driver.scrollPageToTop();
	//	st.getUniqueFamilyHits().ScrollTo();
		bc.waitTime(3);
		st.verifyColumnSorting("UNIQUE FAMILY HITS",st.getUniqueFamilyHits());	
		bc.stepInfo("Verifying sorting fueature of UNIQUE FAMILY HITS when order is ascending");
		bc.passedStep("Sucessfully verified sorting functionality of UNIQUE FAMILY HITSfor both ascending and descending order");
		bc.stepInfo("Verifying sorting fueature of UNIQUE FAMILY HITS when order is descending");
		bc.passedStep("Sucessfully verified sorting functionality of UNIQUE FAMILY HITSfor both ascending and descending order");

	}
	/**
	 * @author Jayanthi.ganesan
	 * @param username
	 * @param password
	 * @param role
	 * @throws InterruptedException
	 */
	@Test(dataProvider = "Users_PARMU", groups = { "regression" }, priority =9, enabled = true)
	public void  VerifyReportGeneration(String username, String password, String role) throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-56963");
		bc.stepInfo("Verify and generate Search Term Report with source as Search");
		st = new SearchTermReportPage(driver);
		lp = new LoginPage(driver);
		lp.loginToSightLine(username, password);
		bc.stepInfo("Logged in as -" + role);
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		if(role=="PA") {
	    st.GenerateReportWithAllSearches(savedSearchNamesPA);
		st.verifySearchInReportsTable(savedSearchNamesPA);
		}
		if(role=="RMU") {
		    st.GenerateReportWithAllSearches(savedSearchNamesRMU);
		st.verifySearchInReportsTable(savedSearchNamesRMU);
		}
	}
	/**
	 * @author Jayanthi.ganesan
	 * @param username
	 * @param password
	 * @param role
	 * @throws InterruptedException
	 */
	@Test(dataProvider = "Users_PARMU", groups = { "regression" }, priority =10, enabled = true)
	public void  VerifyUniqueHits(String username, String password, String role) throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-56584");
		bc.stepInfo("Search Term Report - Validate Unique Hits column value.");
		st = new SearchTermReportPage(driver);
		lp = new LoginPage(driver);
		lp.loginToSightLine(username, password);
		bc.stepInfo("Logged in as -" + role);
		String[] savedSearchPA= {saveSearchNamePA1,saveSearchNamePA2};
		String[] savedSearchRMU= {saveSearchNameRMU1,saveSearchNameRMU2};
		String[] HitsPA1= {hitsCountPA1,hitsCountPA};
		String[] HitsRMU1= {hitsCountRMU1,hitsCountRMU};
		SessionSearch ss=new SessionSearch(driver);
		ss.basicContentSearchUsingOperator(Input.searchString1, "NOT", Input.searchString2);
		String expectedPureHit1 = ss.verifyPureHitsCount();
		bc.stepInfo("Pure Hits count if Configured query 'test' 'NOT' 'Comments'"+expectedPureHit1);
		bc.selectproject();
		ss.basicContentSearchUsingOperator(Input.searchString2, "NOT", Input.searchString1);
		String expectedPureHit2 = ss.verifyPureHitsCount();
		bc.stepInfo("Pure Hits count if Configured query 'Comments''NOT' 'test' in basic search page "+expectedPureHit2);
		bc.stepInfo("Pure hit count after WP saved search is " + expectedPureHit2);
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		if(role=="PA") {
	    st.GenerateReportWithAllSearches(savedSearchPA);
	    SoftAssert SoftAssertion =new SoftAssert();
	    SoftAssertion.assertEquals(st.getHitsValueFromRow("UNIQUE HITS",saveSearchNamePA1) ,expectedPureHit2);
	    SoftAssertion.assertEquals(st.getHitsValueFromRow("UNIQUE HITS", saveSearchNamePA2),expectedPureHit1);
		bc.stepInfo("The unique Hits Count for saved saerch "+saveSearchNamePA1+"--"+st.getHitsValueFromRow("UNIQUE HITS",saveSearchNamePA1));
		bc.stepInfo("The unique Hits Count for saved saerch "+saveSearchNamePA2+"--"+st.getHitsValueFromRow("UNIQUE HITS",saveSearchNamePA2));
		SoftAssertion.assertAll();
		}
		if(role=="RMU") {
		st.GenerateReportWithAllSearches(savedSearchRMU);
		SoftAssert SoftAssertion =new SoftAssert();
		SoftAssertion.assertEquals(st.getHitsValueFromRow("UNIQUE HITS",saveSearchNameRMU1) ,expectedPureHit2);
	    SoftAssertion.assertEquals(st.getHitsValueFromRow("UNIQUE HITS", saveSearchNameRMU2),expectedPureHit1);
	    bc.stepInfo("The unique Hits Count for saved saerch "+saveSearchNameRMU1+"--"+st.getHitsValueFromRow("UNIQUE HITS",saveSearchNameRMU1));
	    bc.stepInfo("The unique Hits Count for saved saerch "+saveSearchNameRMU2+"--"+st.getHitsValueFromRow("UNIQUE HITS",saveSearchNameRMU2));
		SoftAssertion.assertAll();
		bc.passedStep("Suceddfully verified the Unique Hits Column value in STR Page");
		}
		
	}
	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 */
		@Test(groups = { "regression" }, priority = 11)
		public void ValidateBulkRelease() throws InterruptedException {
			bc.stepInfo("Test case Id: RPMXCON-56499");
			bc.stepInfo("To verify that Bulk action_Bulk Release is working on Search Term Report");
			lp = new LoginPage(driver);
			st = new SearchTermReportPage(driver);
			SoftAssert softAssertion = new SoftAssert();
			lp.loginToSightLine(Input.pa1userName, Input.pa1password);
			String securitygroupname = "STRSG" + Utility.dynamicNameAppender();
			SecurityGroupsPage securityPage = new SecurityGroupsPage(driver);
			driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
			securityPage.AddSecurityGroup(securitygroupname);
			bc.stepInfo("Added security group -"+securitygroupname);
			driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
			String actualHitCount = st.GenerateReport(saveSearchNamePA);
			bc.stepInfo("Report generated for selected search");
			bc.stepInfo("sucessfully released Search term report docs to security group  -"+securitygroupname);
			st.bulkRelease(securitygroupname);
			SessionSearch search = new SessionSearch(driver);
			search.switchToWorkproduct();
			search.selectSecurityGinWPS(securitygroupname);
			bc.stepInfo("Configured a search query with Security group-" + securitygroupname);
			search.serarchWP();
			String expectedCount = search.verifyPureHitsCount();
			softAssertion.assertEquals(actualHitCount, expectedCount);
			bc.stepInfo("Document Count associated to selected security group in search page is "+expectedCount);
			//securityPage.deleteSecurityGroups(securitygroupname);
			softAssertion.assertAll();
			bc.passedStep("The Search term report docs released to Security group "+securitygroupname+" is reflected as expected");
		}


	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		bc = new BaseClass(driver);
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
		}
		try {
			lp.logout();
			lp.quitBrowser();
		} catch (Exception e) {
			lp.quitBrowser();
		}

		System.out.println("Executed :" + result.getMethod().getMethodName());

	}

	@DataProvider(name = "Users_PARMU")
	public Object[][] PA_RMU() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "RMU" },
				{ Input.pa1userName, Input.pa1password, "PA" } };
		return users;
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			//Input in = new Input();
			//in.loadEnvConfig();
			driver = new Driver();
			lp = new LoginPage(driver);
			lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			TagsAndFoldersPage tp = new TagsAndFoldersPage(driver);
			this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
			tp.deleteAllFolders("STR");
			this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
			tp.deleteAllTags("STR");
			SavedSearch savedSearch = new SavedSearch(driver);
			savedSearch.SaveSearchDelete(saveSearchNameRMU);
			lp.logout();
			lp.loginToSightLine(Input.pa1userName, Input.pa1password);
			savedSearch.SaveSearchDelete(saveSearchNamePA);
			lp.logout();
			lp.quitBrowser();
		} catch (Exception e) {
			lp.quitBrowser();
		}

	}
}
