package sightline.reports;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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
import pageFactory.ABMReportPage;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CommunicationExplorerPage;
import pageFactory.CustomDocumentDataReport;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SearchTermReportPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagCountbyTagReport;
import pageFactory.TagsAndFoldersPage;
import pageFactory.TallyPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class SearchTermReport_Regression1 {
	Driver driver;
	LoginPage lp;
	SessionSearch search;
	BaseClass bc;
	SearchTermReportPage st;
	String saveSearchNamePA = "ST" + Utility.dynamicNameAppender();
	String saveSearchNameRMU ="ST" + Utility.dynamicNameAppender();
	String saveSearchNamePA1 = "ST" + Utility.dynamicNameAppender();
	String saveSearchNameRMU1 = "ST" + Utility.dynamicNameAppender();
	String saveSearchNamePA2 = "ST" + Utility.dynamicNameAppender();
	String saveSearchNameRMU2 = "ST" + Utility.dynamicNameAppender();
	String[] savedSearchNamesPA = { saveSearchNamePA2, saveSearchNamePA1, saveSearchNamePA };
	String[] savedSearchNamesRMU = { saveSearchNameRMU2, saveSearchNameRMU1, saveSearchNameRMU };
	String[] searchData = { "test", "comments", "null" };
	String[] Hits = new String[3];
	String[] HitsRMU = new String[3];
	

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
		SessionSearch search = new SessionSearch(driver);
		for (int i = 0; i < savedSearchNamesPA.length; i++) {
			search.basicContentSearch(searchData[i]);
			search.saveSearch(savedSearchNamesPA[i]);
			Hits[i] = search.verifyPureHitsCount();
			System.out.print(Hits[i]);
			if (i != 2) {
				bc.selectproject();
			}
		}

		lp.logout();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		// Search and save it
		for (int i = 0; i < savedSearchNamesRMU.length; i++) {
			search.basicContentSearch(searchData[i]);
			search.saveSearch(savedSearchNamesRMU[i]);
			HitsRMU[i] = search.verifyPureHitsCount();
			System.out.print(HitsRMU[i]);
			if (i != 2) {
				bc.selectproject();
			}
		}
		lp.logout();
		lp.quitBrowser();

	}

	@Test(description ="RPMXCON-56497",dataProvider = "Users_PARMU", groups = { "regression" })
	public void ValidateSearchTermreport_BulkTag(String username, String password, String role)
			throws InterruptedException, AWTException {
		bc.stepInfo("Test case Id: RPMXCON-56497");
		bc.stepInfo("To verify that Bulk action_Bulk Tag is working on Search Term Report");
		st = new SearchTermReportPage(driver);
		lp = new LoginPage(driver);
		lp.loginToSightLine(username, password);
		String saveSearchName = null;
		String hitsCount = null;
		SoftAssert softAssertion = new SoftAssert();
		bc.stepInfo("Logged in as -" + role);
		if (role == "RMU") {
			saveSearchName = saveSearchNameRMU;
			hitsCount = HitsRMU[2];
		}
		if (role == "PA") {
			saveSearchName = saveSearchNamePA;
			hitsCount = Hits[2];
		}
		String TagName = "STRTag" + Utility.dynamicNameAppender();
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		String actualHitCount = st.GenerateReport(saveSearchName);
		softAssertion.assertEquals(actualHitCount, hitsCount);
		bc.stepInfo("Report generated for selected searches");
		String hitCountTagged = st.BulkTag(TagName);
		bc.stepInfo("Bulk tagging of documents from STR is done and tag name is " + TagName);
		softAssertion.assertEquals(actualHitCount, hitCountTagged);
		softAssertion.assertAll();
		bc.stepInfo("Document Count associated to selected tag is as excpeted");
		lp.logout();
	}

	@Test(description ="RPMXCON-56498",dataProvider = "Users_PARMU", groups = { "regression" })
	public void ValidateSearchTermreport_BulkFolder(String username, String password, String role)
			throws InterruptedException, AWTException {
		bc.stepInfo("Test case Id: RPMXCON-56498");
		bc.stepInfo("To verify that Bulk action_Bulk Folder is working on Search Term Report");
		st = new SearchTermReportPage(driver);
		lp = new LoginPage(driver);
		lp.loginToSightLine(username, password);
		String saveSearchName = null;
		String hitsCount = null;
		SoftAssert softAssertion = new SoftAssert();
		bc.stepInfo("Logged in as -" + role);
		if (role == "RMU") {
			saveSearchName = saveSearchNameRMU;
			hitsCount = HitsRMU[2];
		}
		if (role == "PA") {
			saveSearchName = saveSearchNamePA;
			hitsCount = Hits[2];
		}
		String folderName = "STRFolder" + Utility.dynamicNameAppender();
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		String actualHitCount = st.GenerateReport(saveSearchName);
		softAssertion.assertEquals(actualHitCount, hitsCount);
		bc.stepInfo("Report generated for selected search");
		String folderedDocCount = st.BulkFolder(folderName);
		bc.stepInfo("Bulk Foldering of documents from STR is done and tag name is " + folderName);
		softAssertion.assertEquals(actualHitCount, folderedDocCount);
		softAssertion.assertAll();
		bc.stepInfo("Document Count associated to selected folder is as excpeted");
		lp.logout();
	}

	 @Test(description ="RPMXCON-56496",dataProvider = "Users_PARMU", groups = { "regression" })
	public void ValidateSearchTermreport_viewInDocView(String username, String password, String role)
			throws InterruptedException, AWTException {
		bc.stepInfo("Test case Id: RPMXCON-56496");
		bc.stepInfo("To verify that Bulk action_View in Doc View is working on Search Term Report");
		st = new SearchTermReportPage(driver);
		lp = new LoginPage(driver);
		lp.loginToSightLine(username, password);
		String saveSearchName = null;
		String hitsCount = null;
		SoftAssert softAssertion = new SoftAssert();
		bc.stepInfo("Logged in as -" + role);
		if (role == "RMU") {
			saveSearchName = saveSearchNameRMU;
			hitsCount = HitsRMU[2];
		}
		if (role == "PA") {
			saveSearchName = saveSearchNamePA;
			hitsCount = Hits[2];
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
		lp.logout();
	}

	@Test(description ="RPMXCON-56501",groups = { "regression" })
	public void ValidateSearchTermreport_BulkAssign() throws InterruptedException, AWTException {
		bc.stepInfo("Test case Id: RPMXCON-56501");
		bc.stepInfo("To verify that Bulk action_Bulk Assign is working on Search Term Report");
		st = new SearchTermReportPage(driver);
		lp = new LoginPage(driver);
		String assignmentName = "STRAssign" + Utility.dynamicNameAppender();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		SoftAssert softAssertion = new SoftAssert();
		String saveSearchName = saveSearchNameRMU;
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		String actualHitCount = st.GenerateReport(saveSearchName);
		softAssertion.assertEquals(actualHitCount, HitsRMU[2]);
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
		// agnmt.deleteAllAssignments("STR");
		System.out.println(ActualCount);
		softAssertion.assertEquals(actualHitCount, ActualCount);
		softAssertion.assertAll();
		bc.passedStep("Document Count associated to selected Saved search in Search term report is displayed  as"
				+ " excpeted in manage assignments page after assigning the docs to assignment .");
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param username
	 * @param password
	 * @param role
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-56507",dataProvider = "Users_PARMU", groups = { "regression" }, enabled = true)
	public void VerifyTotalDocsSelected(String username, String password, String role) throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-56507");
		bc.stepInfo("To verify that total selected unique count will be displayed as \"Total Selected\" under "
				+ "Actions drop down");
		st = new SearchTermReportPage(driver);
		lp = new LoginPage(driver);
		lp.loginToSightLine(username, password);
		bc.stepInfo("Logged in as -" + role);
		String saveSearchName = null;
		SoftAssert softAssertion = new SoftAssert();
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
		bc.stepInfo("Total selected Hit count " + actualHitCount);
		bc.waitTime(3);
		String TotalCount = st.getTotalSelectedCount().getText();
		bc.stepInfo("Total selected doc count displayed under action button " + TotalCount);
		softAssertion.assertEquals(actualHitCount, TotalCount);
		softAssertion.assertAll();
		bc.passedStep(
				"Sucessfully verified that total selected unique count will be displayed as Total Selected under Actions drop down");
		lp.logout();
	}

	@Test(description ="RPMXCON-56361",dataProvider = "Users_PARMU", groups = { "regression" })
	public void navigateToSearchTermReport(String username, String password, String role) {
		bc.stepInfo("Test case Id: RPMXCON-56361");
		bc.stepInfo(
				"To verify that Search Term Report link is provided on Report Landing Page and report should be opened on clicking on link");
		st = new SearchTermReportPage(driver);
		lp = new LoginPage(driver);
		lp.loginToSightLine(username, password);
		bc.stepInfo("Logged in as -" + role);
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		String expectedURL = Input.url + "DataAnalysisReport/SearchTermReport";
		bc.waitForElement(st.getSearchTermReport());
		if (st.getSearchTermReport().isDisplayed()) {
			bc.passedStep("Search Term Report link is displayed in reports landing Page");
			st.getSearchTermReport().Click();
			SoftAssert softAssertion = new SoftAssert();
			driver.waitForPageToBeReady();
			softAssertion.assertEquals(expectedURL, driver.getUrl());
			softAssertion.assertAll();
			bc.passedStep("Sucessfully navigated to  Search Term Report Page");

		} else {
			bc.failedStep("Search Term Report link is not found in Reports landing page.");
		}
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param username
	 * @param password
	 * @param role
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-56482",dataProvider = "Users_PARMU", groups = { "regression" }, enabled = true)
	public void VerifyAggregateSummary(String username, String password, String role) throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-56482");
		bc.stepInfo(
				"To verify that  two aggregate unique document counts displays under the Summary section on Search Term Report.");
		st = new SearchTermReportPage(driver);
		lp = new LoginPage(driver);
		lp.loginToSightLine(username, password);
		bc.stepInfo("Logged in as -" + role);

		SoftAssert softAssertion = new SoftAssert();
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		if (role == "PA") {
			st.GenerateReportWithAllSearches(savedSearchNamesPA);
		}

		if (role == "RMU") {
			st.GenerateReportWithAllSearches(savedSearchNamesRMU);
		}
		bc.passedStep("Report  generated for selected search ");
		int i = st.getIndex("UNIQUE HITS");
		int j = st.getIndex("UNIQUE FAMILY HITS");
		List<Integer> Hits = new ArrayList<>();
		List<Integer> familyHits = new ArrayList<>();
		Hits = st.getColumn(st.getColumnValues(i));
		familyHits = st.getColumn(st.getColumnValues(j));
		System.out.println(Hits);
		System.out.println(familyHits);
		int uniqueHitsSum = st.sumUsingList(Hits);
		int uniquefamilyHitsSum = st.sumUsingList(familyHits);
		bc.waitForElement(st.getUniqueHitsFromSummary());
		int expecteduniqueHits = Integer.parseInt(st.getUniqueHitsFromSummary().getText());
		int expecteduniquefamilyHits = Integer.parseInt(st.getUniqueFamilyHitsFromSummary().getText());
		bc.stepInfo("Sum of all searches unique Hits  " + uniqueHitsSum);
		bc.stepInfo("Aggregate Unique hits value Displayed in summary of STR Page"
				+ st.getUniqueHitsFromSummary().getText());
		bc.stepInfo("Sum of all searches unique family  Hits  " + uniquefamilyHitsSum);
		bc.stepInfo("Aggregate Unique family hits value Displayed in summary of STR Page"
				+ st.getUniqueFamilyHitsFromSummary().getText());
		softAssertion.assertEquals(uniqueHitsSum, expecteduniqueHits);
		softAssertion.assertEquals(uniquefamilyHitsSum, expecteduniquefamilyHits);
		softAssertion.assertAll();
		bc.passedStep(
				"Sucessfully verified that aggregate unique document counts displays under the Summary section on Search Term Report is sum of all Unique Doc values under report table.");
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param username
	 * @param password
	 * @param role
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-56588",dataProvider = "Users_PARMU", groups = { "regression" }, enabled = true)
	public void VerifySorting(String username, String password, String role) throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-56588");
		bc.stepInfo("Search Term Report - Verify sorting feature on Unique Hits and Unique Family Hits columns");
		st = new SearchTermReportPage(driver);
		lp = new LoginPage(driver);
		lp.loginToSightLine(username, password);
		bc.stepInfo("Logged in as -" + role);
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		if (role == "PA") {
			st.GenerateReportWithAllSearches(savedSearchNamesPA);
		}

		if (role == "RMU") {
			st.GenerateReportWithAllSearches(savedSearchNamesRMU);
		}
		bc.passedStep("Report  generated for All saved searches.");
		st.verifyColumnSorting("UNIQUE HITS", st.getUniqueHits());
		driver.scrollPageToTop();
		 st.getUniqueFamilyHits().ScrollTo();
		 st.verifyColumnSorting("UNIQUE FAMILY HITS",st.getUniqueFamilyHits());
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param username
	 * @param password
	 * @param role
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-56963",dataProvider = "Users_PARMU", groups = { "regression" }, enabled = true)
	public void VerifyReportGeneration(String username, String password, String role) throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-56963");
		bc.stepInfo("Verify and generate Search Term Report with source as Search");
		st = new SearchTermReportPage(driver);
		lp = new LoginPage(driver);
		lp.loginToSightLine(username, password);
		bc.stepInfo("Logged in as -" + role);
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		if (role == "PA") {
			st.GenerateReportWithAllSearches(savedSearchNamesPA);
			st.verifySearchInReportsTable(savedSearchNamesPA);
		}
		if (role == "RMU") {
			st.GenerateReportWithAllSearches(savedSearchNamesRMU);
			st.verifySearchInReportsTable(savedSearchNamesRMU);
			lp.logout();
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param username
	 * @param password
	 * @param role
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-56584",dataProvider = "Users_PARMU", groups = { "regression" }, enabled = true)
	public void VerifyUniqueHits(String username, String password, String role) throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-56584");
		bc.stepInfo("Search Term Report - Validate Unique Hits column value.");
		st = new SearchTermReportPage(driver);
		lp = new LoginPage(driver);
		lp.loginToSightLine(username, password);
		bc.stepInfo("Logged in as -" + role);
		String[] savedSearchPA = { saveSearchNamePA1, saveSearchNamePA2 };
		String[] savedSearchRMU = { saveSearchNameRMU1, saveSearchNameRMU2 };
		SessionSearch ss = new SessionSearch(driver);
		ss.basicContentSearchUsingOperator(Input.searchString1, "NOT", Input.searchString2);
		String expectedPureHit1 = ss.verifyPureHitsCount();
		bc.stepInfo("Pure Hits count if Configured query 'test' 'NOT' 'Comments'" + expectedPureHit1);
		bc.selectproject();
		ss.basicContentSearchUsingOperator(Input.searchString2, "NOT", Input.searchString1);
		String expectedPureHit2 = ss.verifyPureHitsCount();
		bc.stepInfo(
				"Pure Hits count if Configured query 'Comments''NOT' 'test' in basic search page " + expectedPureHit2);
		bc.stepInfo("Pure hit count after WP saved search is " + expectedPureHit2);
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		if (role == "PA") {
			st.GenerateReportWithAllSearches(savedSearchPA);
			SoftAssert SoftAssertion = new SoftAssert();
			SoftAssertion.assertEquals(st.getHitsValueFromRow("UNIQUE HITS", saveSearchNamePA1), expectedPureHit2);
			SoftAssertion.assertEquals(st.getHitsValueFromRow("UNIQUE HITS", saveSearchNamePA2), expectedPureHit1);
			bc.stepInfo("The unique Hits Count for saved saerch " + saveSearchNamePA1 + "--"
					+ st.getHitsValueFromRow("UNIQUE HITS", saveSearchNamePA1));
			bc.stepInfo("The unique Hits Count for saved saerch " + saveSearchNamePA2 + "--"
					+ st.getHitsValueFromRow("UNIQUE HITS", saveSearchNamePA2));
			SoftAssertion.assertAll();
			bc.passedStep("Sucessfully verified the Unique Hits Column value in STR Page");
		}
		if (role == "RMU") {
			st.GenerateReportWithAllSearches(savedSearchRMU);
			SoftAssert SoftAssertion = new SoftAssert();
			SoftAssertion.assertEquals(st.getHitsValueFromRow("UNIQUE HITS", saveSearchNameRMU1), expectedPureHit2);
			SoftAssertion.assertEquals(st.getHitsValueFromRow("UNIQUE HITS", saveSearchNameRMU2), expectedPureHit1);
			bc.stepInfo("The unique Hits Count for saved saerch " + saveSearchNameRMU1 + "--"
					+ st.getHitsValueFromRow("UNIQUE HITS", saveSearchNameRMU1));
			bc.stepInfo("The unique Hits Count for saved saerch " + saveSearchNameRMU2 + "--"
					+ st.getHitsValueFromRow("UNIQUE HITS", saveSearchNameRMU2));
			SoftAssertion.assertAll();
			bc.passedStep("Suceddfully verified the Unique Hits Column value in STR Page");
			lp.logout();
		}

	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-56499",groups = { "regression" })
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
		bc.stepInfo("Added security group -" + securitygroupname);
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		String actualHitCount = st.GenerateReport(saveSearchNamePA);
		bc.stepInfo("Report generated for selected search");
		String releasedDocs = st.bulkRelease(securitygroupname);
		bc.stepInfo("sucessfully released Search term report docs to security group  -" + securitygroupname);
		softAssertion.assertEquals(actualHitCount, releasedDocs);
		bc.stepInfo("Document Count associated to selected security group in PopUp is " + releasedDocs);
		// securityPage.deleteSecurityGroups(securitygroupname);
		softAssertion.assertAll();
		bc.passedStep("The Search term report docs released to Security group " + securitygroupname
				+ " is reflected as expected");
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-56500",dataProvider = "Users_PARMU", groups = { "regression" })
	public void validateExportData(String username, String password, String role) throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-56500");
		bc.stepInfo("To verify that Action_Export Data is working on Search Term Report");
		lp = new LoginPage(driver);
		st = new SearchTermReportPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		lp.loginToSightLine(username, password);
		String saveSearchName = null;
		bc.stepInfo("Logged in as -" + role);
		if (role == "RMU") {
			saveSearchName = saveSearchNameRMU;
		}
		if (role == "PA") {
			saveSearchName = saveSearchNamePA;
		}
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		st.GenerateReport(saveSearchName);
		bc.stepInfo("Report generated for selected search");
		st.STR_ToExportData();
		driver.waitForPageToBeReady();
		String[] metaDataFields1 = { "CustodianName", "DocFileName", "AttachCount" };
		CustomDocumentDataReport cddr = new CustomDocumentDataReport(driver);
		cddr.selectMetaDataFields(metaDataFields1);
		cddr.runReportandVerifyFileDownloaded();
		softAssertion.assertAll();
		bc.passedStep("Sucessfully verified that Export Data action is working on Search Term Report Page.");
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param username
	 * @param password
	 * @param role
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-56586",dataProvider = "Users_PARMU", groups = { "regression" }, enabled = true)
	public void VerifyCombinedUniqueHits(String username, String password, String role) throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-56586");
		bc.stepInfo(
				"Search Term Report - Validate Unique Hits and Unique Family Hits column value for combined search results");
		String cmbSearchName1 = "STR" + Utility.dynamicNameAppender();
		String cmbSearchName2 = "STR" + Utility.dynamicNameAppender();
		String expectedUHit2;
		String expectedUHit1;
		st = new SearchTermReportPage(driver);
		lp = new LoginPage(driver);
		lp.loginToSightLine(username, password);
		bc.stepInfo("Logged in as -" + role);
		String saveSearch[] = { cmbSearchName1, cmbSearchName2 };
		SessionSearch ss = new SessionSearch(driver);
		ss.advancedSearch_CombinedResults(Input.searchString1, ss.getadvoption_family());
		ss.saveSearchAdvanced_New(cmbSearchName1, "Shared with Default Security Group");
		bc.selectproject();
		ss.advancedSearch_CombinedResults(Input.searchString2, ss.getadvoption_family());
		ss.saveSearchAdvanced_New(cmbSearchName2, "Shared with Default Security Group");
		ss.selectSavedsearchInASWp(cmbSearchName1);
		driver.waitForPageToBeReady();
		ss.selectOperator("NOT");
		driver.waitForPageToBeReady();
		ss.searchSavedSearch(cmbSearchName2);
		bc.stepInfo("Configured a search query with two saved search and NOT operator in between ");
		expectedUHit1 = Integer.toString(ss.serarchWP());
		ss.selectSavedsearchInASWp(cmbSearchName2);
		driver.waitForPageToBeReady();
		ss.selectOperator("NOT");
		driver.waitForPageToBeReady();
		ss.searchSavedSearch(cmbSearchName1);
		expectedUHit2 = Integer.toString(ss.serarchWP());
		bc.stepInfo("Configured a search query with two saved search and NOT operator in between ");

		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		st.GenerateReportWithSharedWithSGSearches(saveSearch);
		SoftAssert SoftAssertion = new SoftAssert();
		SoftAssertion.assertEquals(st.getHitsValueFromRow("UNIQUE HITS", cmbSearchName1), expectedUHit1);
		SoftAssertion.assertEquals(st.getHitsValueFromRow("UNIQUE HITS", cmbSearchName2), expectedUHit2);
		bc.stepInfo("The unique Hits Count for Combined saved saerch " + cmbSearchName1 + "--"
				+ st.getHitsValueFromRow("UNIQUE HITS", cmbSearchName1));
		bc.stepInfo("The unique Hits Count for Combined saved saerch " + cmbSearchName2 + "--"
				+ st.getHitsValueFromRow("UNIQUE HITS", cmbSearchName2));
		SoftAssertion.assertAll();
		bc.passedStep("Sucessfully verified the Unique Hits Column value for Combined search in STR Page");

		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-56585",groups = { "regression" }, enabled = true)
	public void VerifyFamilyUniqueHits() throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-56585");
		bc.stepInfo("Search Term Report - Validate Unique Family Hits column value.");
		String tagName1 = "STR" + Utility.dynamicNameAppender();
		String tagName2 = "STR" + Utility.dynamicNameAppender();
		st = new SearchTermReportPage(driver);
		lp = new LoginPage(driver);
		SessionSearch search = new SessionSearch(driver);
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.stepInfo("Logged in as - RMU");
		String[] savedSearchRMU = { saveSearchNameRMU1, saveSearchNameRMU2 };
		SessionSearch ss = new SessionSearch(driver);
		ss.basicContentSearch(Input.searchString1);
		ss.bulkTagFamilyMemberDocuments(tagName1);
		bc.selectproject();
		ss.basicContentSearch(Input.searchString2);
		ss.bulkTagFamilyMemberDocuments(tagName2);
		bc.selectproject();
		search.navigateToAdvancedSearchPage();
		// Adding WP tag into search text box
		search.workProductSearch("tag", tagName1, true);
		// Adding Operator into search text box
		search.selectOperator("NOT");
		search.workProductSearch("tag", tagName2, false);
		search.serarchWP();
		bc.waitTime(2);
		driver.waitForPageToBeReady();
		String expectedFamilyHit1 = ss.verifyPureHitsCount();
		bc.stepInfo("Family members count if Configured query with family members of "
				+ "'test' 'NOT' 'Comments' search terms " + expectedFamilyHit1);
		bc.selectproject();
		search.navigateToAdvancedSearchPage();
		// Adding WP tag into search text box
		search.workProductSearch("tag", tagName2, true);
		// Adding Operator into search text box
		search.selectOperator("NOT");
		search.workProductSearch("tag", tagName1, false);
		search.serarchWP();
		bc.waitTime(2);
		driver.waitForPageToBeReady();
		String expectedFamilyHit2 = ss.verifyPureHitsCount();
		bc.stepInfo("Family members count if Configured query family members of  'Comments''NOT' 'test' search terms"
				+ expectedFamilyHit2);
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		st.GenerateReportWithAllSearches(savedSearchRMU);
		SoftAssert SoftAssertion = new SoftAssert();

		SoftAssertion.assertEquals(st.getHitsValueFromRow("UNIQUE FAMILY HITS", saveSearchNameRMU1),
				expectedFamilyHit2);
		SoftAssertion.assertEquals(st.getHitsValueFromRow("UNIQUE FAMILY HITS", saveSearchNameRMU2),
				expectedFamilyHit1);
		bc.stepInfo("The unique Family Hits Count for saved saerch " + saveSearchNameRMU1 + "--"
				+ st.getHitsValueFromRow("UNIQUE HITS", saveSearchNameRMU1));
		bc.stepInfo("The unique Family  Hits Count for saved saerch " + saveSearchNameRMU2 + "--"
				+ st.getHitsValueFromRow("UNIQUE HITS", saveSearchNameRMU2));
		SoftAssertion.assertAll();
		bc.passedStep("Sucessfully verified the Unique Family Hits Column value in STR Page");
		lp.logout();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param username
	 * @param password
	 * @param role
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-56582",groups = { "regression" }, enabled = true)
	public void UIvalidation_STR() throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-56582");
		bc.stepInfo(
				"Search Term Report - UI Validatation for additional columns 'Unique Hits' and 'Unique Family Hits'");
		st = new SearchTermReportPage(driver);
		lp = new LoginPage(driver);
		TagCountbyTagReport tcPage= new TagCountbyTagReport(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("Logged in as PA");
		String[] savedSearchPA = { saveSearchNamePA1, saveSearchNamePA2 };
		tcPage.navigateToReportPage();
		st.GenerateReportWithAllSearches(savedSearchPA);
		driver.waitForPageToBeReady();
		int conceptualColumn = bc.getIndex(st.getSummaryTableHeader(), "CONCEPTUALLY SIMILAR");
		int uniqueHitsColumn = bc.getIndex(st.getSummaryTableHeader(), "UNIQUE HITS");
		int uniqueFamilyHitsColumn = bc.getIndex(st.getSummaryTableHeader(), "UNIQUE FAMILY HITS");
		int conceptualColSummary = bc.getIndex(st.getSummaryTableHeader(), "CONCEPTUALLY SIMILAR");
		int uniqueHitsColSummary = bc.getIndex(st.getSummaryTableHeader(), "UNIQUE HITS");
		int uniqueFamilyHitsColSummary = bc.getIndex(st.getSummaryTableHeader(), "UNIQUE FAMILY HITS");

		SoftAssert SoftAssertion = new SoftAssert();
		if ((conceptualColumn + 1 == uniqueHitsColumn && conceptualColumn + 2 == uniqueFamilyHitsColumn)
				&& (conceptualColSummary + 1 == uniqueHitsColSummary
						&& conceptualColSummary + 2 == uniqueFamilyHitsColSummary)) {
			bc.passedStep(" Unique Hits column appeared next to the existing Conceptually Similar Column and Unique"
					+ " Family Hits appeared next to Unique Hits column in Search Term Report and summary report.. ");
			driver.waitForPageToBeReady();
			st.getRowCheckBox(Input.searchString2, uniqueHitsColumn).ScrollTo();
			SoftAssertion.assertTrue(st.getRowCheckBox(Input.searchString2, uniqueHitsColumn).isElementAvailable(2));
			SoftAssertion.assertTrue(st.getRowCheckBox(Input.searchString1, uniqueFamilyHitsColumn).isElementAvailable(5));
			SoftAssertion.assertAll();
			bc.passedStep("Each cell in both columns have checkboxes "
					+ " that will look identically to other columns in STR.");

		} else {
			bc.failedStep("Report not generated / columns are not displayed as expected");
		}
		lp.logout();
	}
	
	
	
	/**
	 * @author Jayanthi.ganesan
	 * @param username
	 * @param password
	 * @param role
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-61220",dataProvider = "Users_PARMU", groups = { "regression" }, enabled = true)
	public void VerifyTotalDocsSelectedFontSize(String username, String password, String role) throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-61220");
		bc.stepInfo("Verify that in Reports/Search Term Report , Doc Count Font Size is Increased");
		st = new SearchTermReportPage(driver);
		lp = new LoginPage(driver);
		lp.loginToSightLine(username, password);
		bc.stepInfo("Logged in as -" + role);
		String saveSearchName = null;
		if (role == "RMU") {
			saveSearchName = saveSearchNameRMU;
		}
		if (role == "PA") {
			saveSearchName = saveSearchNamePA;
		}
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		String actualHitCount = st.GenerateReport(saveSearchName);
		bc.passedStep("Report  generated for selected search ");
		bc.waitForElement(st.getTotalSelectedCount_fontSize());
		bc.stepInfo("Total selected Hit count " + actualHitCount);
		if(st.getTotalSelectedCount_fontSize().isElementAvailable(3)) {
			bc.stepInfo("Total selected doc count size is 16px");
		bc.passedStep("Sucessfully verified that in Reports/Search Term Report page, Doc Count Font Size is Increased");
		}else {
			bc.failedStep("Total selected doc count size is not  16px which is expected.");
		}
		lp.logout();
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
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
		}
		try {
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
			// Input in = new Input();
			// in.loadEnvConfig();
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
