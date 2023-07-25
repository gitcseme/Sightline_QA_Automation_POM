package sightline.reports;

import java.awt.AWTException;

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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CommunicationExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ReportsPage;
import pageFactory.SavedSearch;
import pageFactory.SearchTermReportPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class CommunicationExplorer_Phase1_Regression1 {
	Driver driver;
	LoginPage lp;
	SessionSearch search;
	SoftAssert softAssertion;
	BaseClass bc;
	String hitsCount;
	CommunicationExplorerPage comExpPage;
	int pureHit;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();		in.loadEnvConfig();

	}
	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		softAssertion = new SoftAssert();
		driver = new Driver();
		lp = new LoginPage(driver);
		bc = new BaseClass(driver);
		comExpPage = new CommunicationExplorerPage(driver);
	}
	
	@Test(description ="RPMXCON-56969",dataProvider = "Users_PARMU", groups = { "regression" },alwaysRun = true )
	public void verifyReportGeneration_searches(String username, String password, String role)
			throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-56969");
		bc.stepInfo("Verify and generate Communications Explorer with source as Search");
		lp.loginToSightLine(username, password);
		SoftAssert softAssertion = new SoftAssert();
		String saveSearchName = "ST" + Utility.dynamicNameAppender();
		bc.stepInfo("Logged in as -" + role);
		search = new SessionSearch(driver);
		search.basicContentSearch(Input.testData1);
		hitsCount = search.verifyPureHitsCount();
		search.saveSearch(saveSearchName);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		CommunicationExplorerPage comExpPage = new CommunicationExplorerPage(driver);
		comExpPage.generateReportusingSearch();
		bc.waitForElement(comExpPage.getCommunicationExplorer_ApplyResult());
		if (comExpPage.getCommunicationExplorer_ApplyResult().Displayed()) {
			softAssertion.assertTrue(true);
			bc.passedStep("After Selecting the My searches in select sources tab Report is generated.");

		} else {
			softAssertion.assertTrue(false);
			bc.failedStep("After Selecting the My searches in select sources tab Report is not generated.");

		}
		softAssertion.assertAll();
		 SavedSearch savedSearch = new SavedSearch(driver);
		 savedSearch.SaveSearchDelete(saveSearchName);
		lp.logout();

	}

	/**
	 * [Module is grouped under doc explorer in JIRA but the flow is in
	 * communications exp so grouped it under this class file for scripting
	 * convinience]
	 * 
	 * @author Jayanthi
	 * @throws InterruptedException
	 * @description Verify that Exclude filter functionality works properly when TAG
	 *              name contains word between on Communications Explorer screen
	 */
	@Test(description ="RPMXCON-54955",dataProvider = "Users_PARMU", groups = { "regression" }, enabled = true)
	public void VerifyExcludeFiltersFunctionality_ComMapExp(String username, String password, String role)
			throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-54955");
		bc.stepInfo("Verify that Exclude filter functionality works properly when TAG name "
				+ "contains word between on Communications Explorer screen");
		lp.loginToSightLine(username, password);
		bc.stepInfo("Logged in as -" + role);
		search = new SessionSearch(driver);
		TagsAndFoldersPage tagPage = new TagsAndFoldersPage(driver);
		// Creating tag with name contains 'between' and buk tagging the docs obtained
		// from metadata search(Email author name-"Gouri dhavalikar")
		String TagName1 = "Correspondence between the parties" + Utility.dynamicNameAppender();
		String MetaSearch = Input.EmailAuthourName;
		search.MetaDataSearchInAdvancedSearch(Input.MetaDataEAName, MetaSearch);
		search.bulkTag(TagName1);
		bc.stepInfo("Created a Tag with name--" + TagName1);
		// Generating Communication exp report with Def sec group as source
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		comExpPage.generateReportusingDefaultSG();
		bc.stepInfo("Report Generated.");
		// Applying Exclude filter for Tag (if false it will perform as exclude filter)
		comExpPage.include(TagName1, "Tags", null, false);
		comExpPage.getCommunicationExplorer_ApplyBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		bc.waitTime(3);
		comExpPage.clickReport();
		// selecting the docs from communication exp page report and viewing in doc list
		// page
		// to check whether the exclude functionality filtered the selcted Tag docs
		comExpPage.viewinDoclist();
		DocListPage DLPage = new DocListPage(driver);
		DLPage.SelectColumnDisplay(DLPage.getSelectEmailAuthorName());
		DLPage.emailAuthorNameVerificationWithOutFilter(Input.EmailAuthourName, false);
		/*if (role == "PA") {
			this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
			tagPage.deleteAllTags("Correspondence between the parties");
			lp.logout();
		}*/
	}

	/**
	 * @author Jayanthi
	 * @throws InterruptedException
	 * @description To verify that Users are able to Save Communication Map report.
	 */
	@Test(description ="RPMXCON-56415",dataProvider = "Users_PARMU", groups = { "regression" }, enabled = true)
	public void VerifySaveFunctionality_ComMapExp(String username, String password, String role)
			throws InterruptedException {
		String reportName = "IndiumReport" + Utility.dynamicNameAppender();
		bc.stepInfo("Test case Id: RPMXCON-56415");
		bc.stepInfo("To verify that Users are able to Save  Communication Map report.");
		ReportsPage reportPage = new ReportsPage(driver);
		lp.loginToSightLine(username, password);
		bc.stepInfo("Logged in as -" + role);
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		comExpPage.generateReportusingDefaultSG();
		bc.stepInfo("Report Generated.");
		comExpPage.saveReport(reportName);
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		reportPage.verifyCustomReportDisplaydetails(reportName);
		reportPage.deleteCustomReport(reportName);
		lp.logout();
	}

	/**
	 * @author Jayanthi
	 * @throws InterruptedException
	 * @description Validate list for Tags in on page filter on Communication
	 *              Explorer report
	 */
	@Test(description ="RPMXCON-56918",dataProvider = "Users_PARMU", groups = { "regression" }, enabled = true)
	public void VerifyFiltersFunctionality_ComMapExp(String username, String password, String role)
			throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-56918");
		bc.stepInfo("Validate list for Tags in on page filter on Communication Explorer report");
		lp.loginToSightLine(username, password);
		bc.stepInfo("Logged in as -" + role);
		String TagName = "ASTag" + Utility.dynamicNameAppender();
		TagsAndFoldersPage tagPage = new TagsAndFoldersPage(driver);
		tagPage.CreateTagwithClassification(TagName, Input.tagNamePrev);
		bc.stepInfo("Created a Tag with name--" + TagName);
		bc.selectproject();
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		comExpPage.generateReportusingDefaultSG();
		bc.stepInfo("Report Generated.");
		comExpPage.verifyFilters("Tags", true, TagName);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagPage.deleteAllTags(TagName);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		comExpPage.generateReportusingDefaultSG();
		bc.stepInfo("Report Generated.");
		comExpPage.verifyFilters("Tags", false, TagName);
		lp.logout();
	}

	/**
	 * @author Jayanthi
	 * @throws InterruptedException
	 * @description Validate on Page filter by Tag on Communication Explorer (same
	 *              documents are part of multiple tags)
	 */
	@Test(description ="RPMXCON-56917",dataProvider = "Users_PARMU", groups = { "regression" }, enabled = true)
	public void VerifyIncludeFiltersFunctionality_ComMapExp(String username, String password, String role)
			throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-56917");
		bc.stepInfo("Validate on Page filter by Tag on Communication Explorer"
				+ "(same documents are part of multiple tags)");
		lp.loginToSightLine(username, password);
		bc.stepInfo("Logged in as -" + role);
		String TagName2 = "ReportsTag" + Utility.dynamicNameAppender();
		String TagName1 = "ReportsTag" + Utility.dynamicNameAppender();
		TagsAndFoldersPage tagPage = new TagsAndFoldersPage(driver);
		DocListPage DLPage = new DocListPage(driver);
		search = new SessionSearch(driver);
		String MetaSearch = "Gouri";
		tagPage.CreateTagwithClassification(TagName1, Input.tagNamePrev);
		search.MetaDataSearchInAdvancedSearch(Input.MetaDataEAName, MetaSearch);
		search.bulkTagExisting(TagName1);
		bc.stepInfo("Created a Tag with name--" + TagName1);
		bc.selectproject();
		tagPage.CreateTagwithClassification(TagName2, Input.tagNamePrev);
		search.MetaDataSearchInAdvancedSearch(Input.MetaDataEAName, MetaSearch);
		bc.stepInfo("Created a Tag with name--" + TagName2);
		search.bulkTagExisting(TagName2);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		comExpPage.generateReportusingDefaultSG();
		bc.stepInfo("Report Generated.");
		comExpPage.include(TagName1, "Tags", TagName2, true);
		comExpPage.getCommunicationExplorer_ApplyBtn().waitAndClick(10);
		bc.waitTime(3);
		comExpPage.clickReport();
		comExpPage.viewinDoclist();
		DLPage.SelectColumnDisplay(DLPage.getSelectEmailAuthorName());
		DLPage.emailAuthorNameVerificationWithOutFilter(Input.EmailAuthourName, true);
		bc.selectproject();
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		comExpPage.generateReportusingDefaultSG();
		bc.stepInfo("Report Generated.");
		comExpPage.include(TagName1, "Tags", TagName2, false);
		comExpPage.getCommunicationExplorer_ApplyBtn().Click();
		bc.waitTime(3);
		comExpPage.clickReport();
		comExpPage.viewinDoclist();
		DLPage.SelectColumnDisplay(DLPage.getSelectEmailAuthorName());
		DLPage.emailAuthorNameVerificationWithOutFilter(Input.EmailAuthourName, false);
		/*if (role == "PA") {
			this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
			tagPage.deleteAllTags("ASTag");
			lp.logout();
		}*/
	}

	/**
	 * @author Jayanthi
	 * @throws InterruptedException
	 * @description Validate on page tag filter for Communication Explorer(few
	 *              documents are not part of any Tag)
	 */
	@Test(description ="RPMXCON-56916",dataProvider = "Users_PARMU", groups = { "regression" }, enabled = true)
	public void VerifyFilters_ComMapExp(String username, String password, String role) throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-56916");
		bc.stepInfo("Validate on page tag filter for Communication Explorer(few documents are not part of any Tag)");
		lp.loginToSightLine(username, password);
		TagsAndFoldersPage tagPage = new TagsAndFoldersPage(driver);
		bc.stepInfo("Logged in as -" + role);
		String TagName3 = "ReportsTag" + Utility.dynamicNameAppender();
		search = new SessionSearch(driver);
		tagPage.CreateTagwithClassification(TagName3, Input.tagNamePrev);
		search.MetaDataSearchInAdvancedSearch(Input.MetaDataReciepientsDomain, Input.MetaDataDomainName);
		bc.stepInfo("Completed Metadata search using EmailRecipientDomains as MetaData");
		pureHit = Integer.parseInt(search.verifyPureHitsCount());
		search.bulkTagExisting(TagName3);
		bc.stepInfo("Created a Tag with name--" + TagName3);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		comExpPage.generateReportusingDefaultSG();
		bc.stepInfo("Report Generated.");
		Thread.sleep(10000);
		int DocCount1 = Integer.parseInt(comExpPage.VerifyTaggedDocsCountDisplay());
		System.out.println(DocCount1);
		comExpPage.include(TagName3, "Tags", null, true);
		comExpPage.getCommunicationExplorer_ApplyBtn().Click();
		driver.waitForPageToBeReady();
		bc.stepInfo("Report Generated after including the tag " + TagName3);
		Thread.sleep(10000);
		int DocCount2 = Integer.parseInt(comExpPage.VerifyTaggedDocsCountDisplay());
		System.out.println(DocCount2);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		comExpPage.generateReportusingDefaultSG();
		comExpPage.include(TagName3, "Tags", null, false);
		comExpPage.getCommunicationExplorer_ApplyBtn().Click();
		driver.waitForPageToBeReady();
		bc.stepInfo("Report Generated after Excluding the tag " + TagName3);
		Thread.sleep(10000);
		int DocCount3 = Integer.parseInt(comExpPage.VerifyTaggedDocsCountDisplay());
		System.out.println(DocCount3);
		try {
			int ExpectedDocCount_Include = pureHit;
			int ExpectedDocCount_Exclude = DocCount1 - pureHit;
			softAssertion.assertEquals(ExpectedDocCount_Include, DocCount2);
			softAssertion.assertEquals(ExpectedDocCount_Exclude, DocCount3);
			softAssertion.assertAll();
			bc.passedStep(
					"Result set  filter the documents  as expected when we  exclude/include Tags in report generation"
							+ "part of the selected Tags. ");
		} catch (Exception e) {
			e.getStackTrace();
			bc.failedStep(e.getMessage());
		}
		lp.logout();

	}

	/**
	 * @author Jayanthi
	 * @throws InterruptedException
	 * @description To verify that Users are able to View In Doc View option is
	 *              displayed in Action dropdown in Comm Map
	 */
	@Test(description ="RPMXCON-56414",dataProvider = "Users_PARMU", groups = { "regression" }, enabled = true)
	public void VerifyViewInDocView(String username, String password, String role) throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-56414");
		bc.stepInfo("To verify that Users are able to  View In Doc View option is displayed in"
				+ " Action dropdown in Comm Map");
		lp.loginToSightLine(username, password);
		bc.stepInfo("Logged in as -" + role);
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		comExpPage.generateReportusingDefaultSG();
		bc.stepInfo("Report Generated.");
		comExpPage.clickReport();
		bc.waitForElement(comExpPage.getMailCountOFSelectedReport());
		String DocCountInreportsPage = comExpPage.getMailCountOFSelectedReport().getText();
		bc.stepInfo(DocCountInreportsPage + "  Doc Count in report page that is selected to view in doc view.");
		comExpPage.viewinDocView();
		driver.waitForPageToBeReady();
		bc.waitForElementCollection(comExpPage.metaDataslist_reviewerPg());
		int x = comExpPage.metaDataslist_reviewerPg().FindWebElements().size();
		System.out.println(x);
		String count = String.valueOf((comExpPage.metaDataslist_reviewerPg().FindWebElements().size()));
		bc.stepInfo(count + "  Doc Count in Doc view page that is selected to view in doc view.");
		System.out.println(DocCountInreportsPage);
		System.out.println(count);
		if (DocCountInreportsPage.contentEquals(count)) {
			softAssertion.assertTrue(true);
			bc.passedStep("All selected documents are displayed in Doc View");
		} else {
			softAssertion.assertFalse(false);
			bc.failedStep("All selected documents are not displayed in Doc View");
		}
		lp.logout();
	}

	

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		lp = new LoginPage(driver);
		bc = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
		}
		try {
			lp.quitBrowser();
		} catch (Exception e) {
			lp.quitBrowser();
		}
	}

	@DataProvider(name = "Users_PARMU")
	public Object[][] PA_RMU() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "RMU" },
				{ Input.pa1userName, Input.pa1password, "PA" } };
		return users;
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		
		bc.stepInfo("***Executed Communications Explorer_Regression1****");
		  

	}
}
