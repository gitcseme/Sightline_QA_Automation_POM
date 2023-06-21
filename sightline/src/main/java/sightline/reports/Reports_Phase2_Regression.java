package sightline.reports;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
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
import executionMaintenance.UtilityLog;
import pageFactory.ABMReportPage;
import pageFactory.AssignmentReviewProgressReport;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CommentsPage;
import pageFactory.CommunicationExplorerPage;
import pageFactory.ConceptExplorerPage;
import pageFactory.CustomDocumentDataReport;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.ReportsPage;
import pageFactory.ReviewerCountsReportPage;
import pageFactory.ReviewerReviewProgressReport;
import pageFactory.SavedSearch;
import pageFactory.SearchTermReportPage;
import pageFactory.SessionSearch;
import pageFactory.TagCountbyTagReport;
import pageFactory.TagsAndFoldersPage;
import pageFactory.TallyPage;
import pageFactory.TimelineReportPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Reports_Phase2_Regression {

	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	Utility utility;
	SoftAssert softAssertion;
	ProjectPage projects;
	String projectName;
	String clientName;
	SearchTermReportPage searchterm;
	SavedSearch savedSearch;
	SessionSearch sessionSearch;
	ReportsPage reports;
	
	@BeforeClass(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		
		Input input = new Input();
		input.loadEnvConfig();
		

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());
		
		
		driver = new Driver();
		loginPage = new LoginPage(driver);
		base = new BaseClass(driver);
		softAssertion = new SoftAssert();
		projects = new ProjectPage(driver);
		sessionSearch=new SessionSearch(driver);
		reports=new ReportsPage(driver);
		savedSearch=new SavedSearch(driver);
		searchterm=new SearchTermReportPage(driver);
	}

	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		base = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility base = new Utility(driver);
			base.screenShot(result);
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR Reports EXECUTED******");

	}

	@DataProvider(name = "PA & RMU")
	public Object[][] users() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password }, { Input.pa1userName, Input.pa1password } };
		return users;
	}

	@DataProvider(name = "paRmuUsers")
	public Object[][] paRmuUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, "PA" },
				{ Input.rmu1userName, Input.rmu1password, "RMU" } };
		return users;
	}


	/**
	 * @author NA Testcase No:RPMXCON-56244
	 * @Description:verify that Project Admin can view all Tag Group and Tags
	 *                     irrespective of Security Group.
	 **/
	@Test(description = "RPMXCON-56244", enabled = true, groups = { "regression" })
	public void verifyProjectAdminViewAllTagGrpsandTags() throws Exception {
		List<String> expectedTags = null;
		List<String> actualTags = null;

		base.stepInfo("RPMXCON - 56244");
		base.stepInfo("To verify that Project Admin can view all Tag Group and Tags irrespective of Security Group.");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in As  : " + Input.pa1userName);

		TagsAndFoldersPage tags = new TagsAndFoldersPage(driver);
		tags.navigateToTagsAndFolderPage();
		driver.waitForPageToBeReady();
		tags.expandAllClosedArrow();
		base.waitForElementCollection(tags.getAvailableTagList());
		expectedTags = base.getAvailableListofElements(tags.getAvailableTagList());

		TagCountbyTagReport tagReport = new TagCountbyTagReport(driver);
		tagReport.navigateToTagCountByTagReportPage();
		driver.waitForPageToBeReady();
		base.waitForElement(tagReport.getTags_Expandbutton());
		tagReport.getTags_Expandbutton().waitAndClick(5);

		base.waitForElementCollection(tagReport.getTags());
		actualTags = base.getAvailableListofElements(tagReport.getTags());
		System.out.println(actualTags);
		System.out.println(expectedTags);

		base.listCompareEquals(expectedTags, actualTags,
				"All security group Tag Groups and Tags are displayed in Tree as Expected",
				"All security group Tag Groups and Tags are displayed in Tree As Not Expected");
		base.passedStep("Verified -that Project Admin can view all Tag Group and Tags irrespective of Security Group.");
		loginPage.logout();

	}

	/**
	 * @author NA Testcase No:RPMXCON-56303
	 * @Description:verify that User is able to can remove the selected multiple
	 *                     options in Source Criteria.
	 **/
	@Test(description = "RPMXCON-56303", dataProvider = "PA & RMU", enabled = true, groups = { "regression" })
	public void verifyUserABletoRemoveSrcCrit(String username, String password) throws Exception {
		TagsAndFoldersPage tagFolder = new TagsAndFoldersPage(driver);
		TimelineReportPage timeLine = new TimelineReportPage(driver);

		String tagName1 = "Tag" + Utility.dynamicNameAppender();
		String tagName2 = "Tag" + Utility.dynamicNameAppender();
		String tagName3 = "Tag" + Utility.dynamicNameAppender();
		String[] tags = { tagName1, tagName2, tagName3 };

		base.stepInfo("RPMXCON - 56303");
		base.stepInfo("To verify that User is able to can remove the selected multiple options in Source Criteria.");
		loginPage.loginToSightLine(username, password);
		base.stepInfo("Logged in As : " + username);

		tagFolder.navigateToTagsAndFolderPage();
		driver.waitForPageToBeReady();

		if (username.equals(Input.rmu1userName)) {
			tagFolder.createNewTagwithClassificationInRMU(tagName1, Input.tagNamePrev);
			tagFolder.createNewTagwithClassificationInRMU(tagName2, Input.tagNamePrev);
			tagFolder.createNewTagwithClassificationInRMU(tagName3, Input.tagNamePrev);
		} else {
			tagFolder.createNewTagwithClassification(tagName1, Input.tagNamePrev);
			tagFolder.createNewTagwithClassification(tagName2, Input.tagNamePrev);
			tagFolder.createNewTagwithClassification(tagName3, Input.tagNamePrev);
		}

		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		timeLine.navigateToTimelineAndGapsReport();
		driver.waitForPageToBeReady();
		timeLine.selectTagsinSource(tags);
		timeLine.verifySelctedOptnsInSourceCriteria(tags);
		timeLine.RemoveAndVerifyOptionFrmSrcCriteria(tags);
		base.passedStep(
				"Verified -  that User is able to can remove the selected multiple options in Source Criteria.");
		loginPage.logout();

	}

	/**
	 * @author NA Testcase No:RPMXCON-56298
	 * @Description:verify that Timeline and Gaps Report option is displayed on
	 *                     Report menu
	 **/
	@Test(description = "RPMXCON-56298", dataProvider = "PA & RMU", enabled = true, groups = { "regression" })
	public void verifyTimeLineReportDisplayRepMenu(String username, String password) throws Exception {
		TimelineReportPage timeLineGaps = new TimelineReportPage(driver);

		String timeLine = "MasterDate";
		String fromDate = "2019/01/01";
		String toDate = timeLineGaps.getCurrentDate();
		String reportName = "Report" + Utility.dynamicNameAppender();

		base.stepInfo("RPMXCON - 56298");
		base.stepInfo("To verify that Timeline and Gaps Report option is displayed on Report menu.");
		loginPage.loginToSightLine(username, password);
		base.stepInfo("Logged In As : " + username);

		timeLineGaps.fillingDetailsinTimeGaps(timeLine, fromDate, toDate);
		driver.waitForPageToBeReady();
		base.waitForElement(timeLineGaps.selectChart());
		timeLineGaps.selectBarChartandRtnDocCount("yearly");
		timeLineGaps.SaveReport(reportName);

		ReportsPage report = new ReportsPage(driver);
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();

		if (report.getdeleteToolTip_CustomReport(reportName).isElementAvailable(5)) {
			base.passedStep("Timeline and Gaps Report option is displaying in Report Menu.");
		} else {
			base.failedStep("Timeline and Gaps Report option Not displaying in Report Menu.");
		}

		base.passedStep("Verified - that Timeline and Gaps Report option is displayed on Report menu.");
		loginPage.logout();

	}

	/**
	 * @Author NA
	 * @Description : verify that Users are able to view the saved Custom Report
	 *              Using style as CSV and multiple/all Workproduct. [RPMXCON-56404]
	 */
	@Test(description = "RPMXCON-56404", enabled = true, dataProvider = "PA & RMU", groups = { "regression" })
	public void verifySavedCustomReprtCSVandMultipleWP(String username, String password) throws Exception {
		CustomDocumentDataReport cddr = new CustomDocumentDataReport(driver);
		ReportsPage report = new ReportsPage(driver);
		TagsAndFoldersPage tags = new TagsAndFoldersPage(driver);

		String tagName1 = "Tag" + Utility.dynamicNameAppender();
		String tagName2 = "Tag" + Utility.dynamicNameAppender();
		String reportName = "Report" + Utility.dynamicNameAppender();

		String[] workProductFields = { tagName1, tagName2 };
		String expExportStyle = "CSV";

		base.stepInfo("RPMXCON-56404");
		base.stepInfo("To verify that Users are able to view the saved Custom Report"
				+ " Using style as CSV and multiple/all Workproduct.");
		loginPage.loginToSightLine(username, password);
		base.stepInfo("Logged in As : " + username);

		if (username.equals(Input.rmu1userName)) {
			tags.createNewTagwithClassificationInRMU(tagName1, Input.tagNamePrev);
			tags.createNewTagwithClassificationInRMU(tagName2, Input.tagNamePrev);
		} else {
			tags.createNewTagwithClassification(tagName1, Input.tagNamePrev);
			tags.createNewTagwithClassification(tagName2, Input.tagNamePrev);
		}

		sessionSearch.navigateToSessionSearchPageURL();
		driver.waitForPageToBeReady();
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagName1);
		sessionSearch.bulkTagExisting(tagName2);

		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		driver.waitForPageToBeReady();
		cddr.selectSource("Security Groups", Input.securityGroup);
		cddr.selectExportStyle("CSV");
		cddr.selectWorkProductFields(workProductFields);
		driver.waitForPageToBeReady();

		base.waitForElement(cddr.getRunReport());
		cddr.getRunReport().waitAndClick(10);
		cddr.reportRunSuccessMsg();
		cddr.SaveReport(reportName);
		base.stepInfo("Saved the Custom report " + reportName);

		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		base.waitForElement(report.getSavedCustomReport(reportName));
		report.getSavedCustomReport(reportName).waitAndClick(10);
		driver.waitForPageToBeReady();
		cddr.validateSelectedExports(workProductFields);
		base.waitForElement(cddr.getExportStyleDD());
		String actStyle = cddr.getExportStyleDD().selectFromDropdown().getFirstSelectedOption().getText();
		if (actStyle.equals(expExportStyle)) {
			base.passedStep("Style As Expected");
		} else {
			base.failedStep("Style Not As Expected");
		}
		base.passedStep("Verified - that Users are able to view the saved Custom Report"
				+ "Using style as CSV and multiple/all Workproduct.");
		loginPage.logout();
	}

	/**
	 * @Author NA
	 * @Description: verify that Reviewer Count report is displayed in Report menu
	 *               [RPMXCON-56253]
	 */
	@Test(description = "RPMXCON-56253", enabled = true, dataProvider = "PA & RMU", groups = { "regression" })
	public void verifyRevCountReportinReportMenu(String username, String password) throws Exception {
		AssignmentsPage agmt = new AssignmentsPage(driver);
		String assignment = "Assignment" + Utility.dynamicNameAppender();
		String report = "RevCountReport" + Utility.dynamicNameAppender();

		base.stepInfo("RPMXCON - 56253");
		base.stepInfo("To verify that Reviewer Count report is displayed in Report menu");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.verifyPureHitsCount();
		sessionSearch.bulkAssign();
		agmt.assignmentCreation(assignment, Input.codeFormName);
		agmt.editAssignmentUsingPaginationConcept(assignment);
		driver.waitForPageToBeReady();
		agmt.addReviewerAndDistributeDocs();
		loginPage.logout();

		loginPage.loginToSightLine(username, password);
		ReviewerCountsReportPage countReport = new ReviewerCountsReportPage(driver);
		countReport.navigateTOReviewerCountsReportPage();
		driver.waitForPageToBeReady();
		countReport.generateReport(assignment);
		base.waitForElementCollection(countReport.getTableHeaders());
		base.waitForElement(countReport.getSaveReportBtn());
		countReport.getSaveReportBtn().waitAndClick(5);
		base.waitForElement(countReport.getSaveReportName());
		countReport.getSaveReportName().SendKeys(report);
		base.waitForElement(countReport.getSaveBtn());
		countReport.getSaveBtn().waitAndClick(5);
		base.VerifySuccessMessage("Report save successfully");

		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		base.waitForElement(countReport.getCustomReport(report));
		base.mouseHoverOnElement(countReport.getCustomReport(report));
		if (countReport.getCustomReport(report).Visible()) {
			System.out.println("Reviewer Count Report option is displayed in Report Menu As Expected");
		} else {
			System.out.println("Reviewer Count Report option is displayed in Report Menu Not As Expected");
		}
		base.passedStep("Verified - that Reviewer Count report is displayed in Report menu");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-58560
	 * @Description: Timeline and Gaps report: saved Document Data Export Report
	 *               should work
	 **/
	@Test(description = "RPMXCON-58560", enabled = true, groups = { "regression" })
	public void verifySavedDocExportRep() throws Exception {
		TimelineReportPage timeGaps = new TimelineReportPage(driver);
		TagsAndFoldersPage tags = new TagsAndFoldersPage(driver);
		CustomDocumentDataReport custom = new CustomDocumentDataReport(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		ReportsPage report = new ReportsPage(driver);

		String text1 = "039";
		String field1 = "039";
		String text2 = "034";
		String field2 = "034";
		String tagname1 = "Tag1" + Utility.dynamicNameAppender();
		String tagname2 = "Tag2" + Utility.dynamicNameAppender();
		String[] metadata = { "CustodianName" };
		String[] workProduct = { tagname1 };
		String[] metadata1 = { "DocID" };
		String[] workProduct1 = { tagname2 };

		String report1 = "Report1" + Utility.dynamicNameAppender();
		String report2 = "Report2" + Utility.dynamicNameAppender();
		String timeLine = "MasterDate";
		String fromDate = "2019/01/01";
		String toDate = timeGaps.getCurrentDate();

		base.stepInfo("RPMXCON - 58560");
		base.stepInfo("Timeline and Gaps report: saved Document Data Export Report should work");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		tags.navigateToTagsAndFolderPage();
		tags.createNewTagwithClassificationInRMU(tagname1, Input.tagNamePrev);
		tags.createNewTagwithClassificationInRMU(tagname2, Input.tagNamePrev);

		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname1);
		sessionSearch.bulkTagExisting(tagname2);

		timeGaps.fillingDetailsinTimeGaps(timeLine, fromDate, toDate);
		timeGaps.selectBarChartandRtnDocCount("yearly");
		timeGaps.selectActions(" Export Data", "yearlyActions");

		driver.waitForPageToBeReady();
		base.verifyPageNavigation("Report/ExportData");
		custom.selectExportTextFormat(text1);
		custom.selectExportFieldFormat(field1);
		custom.selectMetaDataFields(metadata);
		custom.selectWorkProductFields(workProduct);
		custom.SaveReport(report1);
		custom.validateSelectedExports(metadata);
		custom.validateSelectedExports(workProduct);
		base.waitForElement(custom.getRunReport());
		custom.getRunReport().waitAndClick(5);
		custom.reportRunSuccessMsg();

		driver.waitForPageToBeReady();
		custom.selectExportTextFormat(text2);
		custom.selectExportFieldFormat(field2);
		base.waitForElement(custom.getSaveReportBtn());
		custom.getSaveReportBtn().waitAndClick(5);
		base.waitForElement(custom.getSaveReportName());
		custom.getSaveReportName().waitAndClick(5);
		custom.getSaveReportName().SendKeys(report2);
		custom.getSaveBtn().waitAndClick(5);
		custom.validateSelectedExports(metadata);
		custom.validateSelectedExports(workProduct);

		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		base.waitForElement(report.getSavedCustomReport(report1));
		report.getSavedCustomReport(report1).waitAndClick(10);
		driver.waitForPageToBeReady();
		custom.selectSources("Security Groups", Input.securityGroup);
		driver.scrollPageToTop();
		base.waitForElement(custom.getRunReport());
		custom.getRunReport().waitAndClick(10);
		custom.reportRunSuccessMsg();

		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		base.waitForElement(report.getSavedCustomReport(report2));
		report.getSavedCustomReport(report2).waitAndClick(5);
		custom.selectSources("Security Groups", Input.securityGroup);
		custom.selectMetaDataFields(metadata1);
		custom.selectWorkProductFields(workProduct1);
		String fileName = custom.runReportandVerifyFileDownloaded();
		String actualValue = custom.csvfileVerification("", fileName);
		base.stepInfo(actualValue);
		System.out.println(actualValue);
		SoftAssert assets = new SoftAssert();
		assets.assertTrue(actualValue.contains(tagname1));
		assets.assertTrue(actualValue.contains(tagname2));
		assets.assertTrue(actualValue.contains("CustodianName"));
		assets.assertTrue(actualValue.contains("DocID"));
		assets.assertAll();
		base.passedStep("Timeline and Gaps report: saved Document Data Export Report should work");
		loginPage.logout();
	}

	/**
	 * @author Jeevitha.R
	 * @Description : To verify that user can save the Search Term report and user
	 *              can view the saved report [RPMXCON-56508]
	 */
	@Test(description = "RPMXCON-56508", dataProvider = "paRmuUsers", groups = { "regression" }, enabled = true)
	public void verifyUserCanSaveSTR(String userName, String password, String role)
			throws InterruptedException, ParseException {
		String saveSearch1 = "Search1" + Utility.dynamicNameAppender();
		String saveSearch2 = "Search2" + Utility.dynamicNameAppender();
		String[] columnName = { "Hits", "Family Members" };
		String[] searchList = { saveSearch1, saveSearch2 };

		base.stepInfo("Test case Id:RPMXCON-56508 Reports/Search Term");
		base.stepInfo("To verify that user can save the Search Term report and user can view the saved report");

		// Login as User
		loginPage.loginToSightLine(userName, password);
		base.stepInfo("Logged In As : " + role);

		// Save searches
		sessionSearch.basicContentSearch(Input.searchString6);
		sessionSearch.saveSearch(saveSearch1);
		sessionSearch.saveSearch(saveSearch2);

		// select Search and generate STR report
		reports.navigateToReportsPage("");
		searchterm.GenerateReportWithAllSearches(searchList);

		// Unselect Columns and verify column present in report
		searchterm.ClickGearIconAndSelectOrUnselectColm(false, true, columnName, false);

		// save custom Report ,verify and click saved custom report
		String reportName = searchterm.ValidateSearchTermreportSaveandImpact(saveSearch1, true);
		reports.getCustomReport(reportName).waitAndClick(10);
		driver.waitForPageToBeReady();

		// Check report generated with saved searches is still Saved
		for (int i = 0; i < searchList.length; i++) {
			base.waitForElement(searchterm.getsearchOrTab_CB(searchList[i]));
			String searchStatus = searchterm.getsearchOrTab_CB(searchList[i]).GetAttribute("class");
			base.compareTextViaContains(searchStatus, "clicked",
					"Custom Report is saved with selected criteria : " + searchList[i],
					"Custom Report is not Saved with expected criteria");
		}

		// verify column is not available in Custom Report as Saved criteria
		searchterm.ClickGearIconAndSelectOrUnselectColm(true, false, columnName, false);

		// delete search
		savedSearch.deleteSearch(saveSearch1, Input.mySavedSearch, Input.yesButton);

		// logout
		loginPage.logout();
	}

	/**
	 * @author Jeevitha.R
	 * @throws AWTException
	 * @Description : Search Term Report - Verify bulk tag action on Unique Hits /
	 *              Unique Family Hits records [RPMXCON-56595]
	 */
	@Test(description = "RPMXCON-56595", groups = { "regression" }, enabled = true)
	public void verifyViewBulkTag() throws InterruptedException, ParseException, AWTException {
		String saveSearch1 = "Search1" + Utility.dynamicNameAppender();
		String saveSearch2 = "Search2" + Utility.dynamicNameAppender();
		String saveSearch3 = "Search3" + Utility.dynamicNameAppender();
		String tagName = "TAG" + Utility.dynamicNameAppender();

		String columnName = "Unique Hits".toUpperCase();

		String[] tabList = { Input.mySavedSearch, Input.shareSearchPA, Input.shareSearchDefaultSG };

		base.stepInfo("Test case Id:RPMXCON-56595 Reports/Search Term");
		base.stepInfo("Search Term Report - Verify bulk tag action on Unique Hits / Unique Family Hits records");

		// Login
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		// save search in All 3 TAbs
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(saveSearch1);

		sessionSearch.getNewSearchButton().waitAndClick(5);
		sessionSearch.multipleBasicContentSearch(Input.searchString2);
		sessionSearch.saveSearchAtAnyRootGroup(saveSearch2, Input.shareSearchPA);
		sessionSearch.saveSearchAtAnyRootGroup(saveSearch3, Input.shareSearchDefaultSG);

		// select ALL tabs and generate STR report
		reports.navigateToReportsPage("");
		searchterm.GenerateReportWithAnySearchOrTab(tabList, true);

		// Select Unique Hit column
		searchterm.selectColumnFromSTRPage(columnName);

		// perform Bulk Tag
		searchterm.BulkTag(tagName);
		base.passedStep("Bulk tag Performed Successfully");

		// delete search
		savedSearch.deleteSearch(saveSearch1, Input.mySavedSearch, Input.yesButton);
		savedSearch.deleteSearch(saveSearch2, Input.shareSearchPA, Input.yesButton);
		savedSearch.deleteSearch(saveSearch3, Input.shareSearchDefaultSG, Input.yesButton);

		// logout
		loginPage.logout();
	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :To verify that on Report Landing page Custom Document Data
	 *              Report option is provided.RPMXCON-56389
	 */

	@Test(description = "RPMXCON-56389", groups = { "regression" }, enabled = true)
	public void verifyOnReportPageCustomDocumentDataReportOptionProvided() {

		base.stepInfo("Test case Id:RPMXCON-56389 Reports/Search Term");
		base.stepInfo("To verify that on Report Landing page Custom Document Data Report option is provided.");

		// Login as PAU
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		// navigating to Reports page
		reports.navigateToReportsPage("");

		// verify that In Others section option should be provided with Customer
		// Documents Data Report
		base.ValidateElement_Presence(reports.getCustomDocumentDataReport(), "Custom Document Data Report");
		base.passedStep("verified that In Others section option provided with Customer Documents Data Report.");

		// logout
		loginPage.logout();

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// navigating to Reports page
		reports.navigateToReportsPage("");

		// verify that In Others section option should be provided with Customer
		// Documents Data Report
		base.ValidateElement_Presence(reports.getCustomDocumentDataReport(), "Custom Document Data Report");
		base.passedStep("verified that In Others section option provided with Customer Documents Data Report.");

		// logout
		loginPage.logout();

	}

	/**
	 * @author Jeevitha.R
	 * @Description : Verify the Total result counts by selecting multiple searches
	 *              in Search Term report [RPMXCON-56824]
	 */

	@Test(description = "RPMXCON-56824", dataProvider = "paRmuUsers", groups = { "regression" }, enabled = true)
	public void selectMultipleSearchAndVerifyTotalCount(String userName, String password, String role)
			throws InterruptedException, ParseException {
		String saveSearch1 = "Search1" + Utility.dynamicNameAppender();
		String saveSearch2 = "Search2" + Utility.dynamicNameAppender();
		String saveSearch3 = "Search3" + Utility.dynamicNameAppender();
		String[] searchList = { saveSearch1, saveSearch2, saveSearch3 };

		base.stepInfo("Test case Id:RPMXCON-56824 Reports/Search Term");
		base.stepInfo("Verify the Total result counts by selecting multiple searches in Search Term report");

		// Login
		loginPage.loginToSightLine(userName, password);

		int expectedHitCount = sessionSearch.basicContentSearch(Input.searchString4);
		sessionSearch.saveSearch(saveSearch1);
		sessionSearch.saveSearch(saveSearch2);
		sessionSearch.saveSearch(saveSearch3);

		// select multiple Searches
		reports.navigateToReportsPage("");
		searchterm.GenerateReportWithAllSearches(searchList);

		// verify aggregate hit count with total hit count
		searchterm.verifyaggregateCount("HITS");

		base.waitForElement(searchterm.getHitsCount());
		String ActualHitCount = searchterm.getHitsCount().getText();
		base.textCompareEquals(ActualHitCount, String.valueOf(expectedHitCount),
				"Aggregate Hit Count of searches is :  " + ActualHitCount, "Aggregate hitcount is not as expected");

		// delete search
		savedSearch.deleteSearch(saveSearch1, Input.mySavedSearch, Input.yesButton);
		savedSearch.deleteSearch(saveSearch2, Input.mySavedSearch, Input.yesButton);
		savedSearch.deleteSearch(saveSearch3, Input.mySavedSearch, Input.yesButton);

		// logout
		loginPage.logout();

	}

	/**
	 * @author Jeevitha.R
	 * @Description : Verify the Total result counts by selecting only single search
	 *              in Search Term report [RPMXCON-56823]
	 */

	@Test(description = "RPMXCON-56823", dataProvider = "paRmuUsers", groups = { "regression" }, enabled = true)
	public void selectSingleSearchAndVerifyTotalCount(String userName, String password, String role)
			throws InterruptedException, ParseException {
		String saveSearch1 = "Search1" + Utility.dynamicNameAppender();
		String[] searchList = { saveSearch1 };

		base.stepInfo("Test case Id:RPMXCON-56823 Reports/Search Term");
		base.stepInfo("Verify the Total result counts by selecting only single search in Search Term report");

		// Login
		loginPage.loginToSightLine(userName, password);

		int expectedHitCount = sessionSearch.basicContentSearch(Input.searchString6);
		sessionSearch.saveSearch(saveSearch1);

		// select Single Searches
		reports.navigateToReportsPage("");
		searchterm.GenerateReportWithAllSearches(searchList);

		// verify aggregate hit count with total hit count
		searchterm.verifyaggregateCount("HITS");

		base.waitForElement(searchterm.getHitsCount());
		String ActualHitCount = searchterm.getHitsCount().getText();
		base.textCompareEquals(ActualHitCount, String.valueOf(expectedHitCount),
				"Aggregate Hit Count of searches is :  " + ActualHitCount, "Aggregate hitcount is not as expected");

		// delete search
		savedSearch.deleteSearch(saveSearch1, Input.mySavedSearch, Input.yesButton);

		// logout
		loginPage.logout();

	}

	/**
	 * @author Jeevitha.R
	 * @Description : To verify that user can export the custom Search Term Report
	 *              [RPMXCON-56511]
	 */
	@Test(description = "RPMXCON-56511", dataProvider = "paRmuUsers", groups = { "regression" }, enabled = true)
	public void verifyUserCanExport(String userName, String password, String role)
			throws InterruptedException, ParseException {
		String saveSearch1 = "Search1" + Utility.dynamicNameAppender();
		String columnName = "Hits".toUpperCase();
		String[] searchList = { saveSearch1 };

		base.stepInfo("Test case Id:RPMXCON-56511 Reports/Search Term");
		base.stepInfo("To verify that user can export the custom Search Term Report");

		// Login
		loginPage.loginToSightLine(userName, password);

		sessionSearch.basicContentSearch(Input.searchString6);
		sessionSearch.saveSearch(saveSearch1);

		// select Search and generate STR report
		reports.navigateToReportsPage("");
		searchterm.GenerateReportWithAllSearches(searchList);

		// Select Hit column
		searchterm.selectColumnFromSTRPage(columnName);

		// save custom Report ,verify and click saved custom report
		String reportName = searchterm.ValidateSearchTermreportSaveandImpact(saveSearch1, true);
		reports.getCustomReport(reportName).waitAndClick(10);

		// Download export and verify Format
		searchterm.exportReportAndVerifyFormat(true, true);

		// delete search
		savedSearch.deleteSearch(saveSearch1, Input.mySavedSearch, Input.yesButton);

		// logout
		loginPage.logout();
	}

	/**
	 * @author Jeevitha.R
	 * @Description : Search Term Report - Verify view bulk action on Unique Hits /
	 *              Unique Family Hits records [RPMXCON-56593]
	 */
	@Test(description = "RPMXCON-56593", groups = { "regression" }, enabled = true)
	public void verifyViewBulkActions() throws InterruptedException, ParseException {
		String saveSearch1 = "Search1" + Utility.dynamicNameAppender();
		String saveSearch2 = "Search2" + Utility.dynamicNameAppender();
		String saveSearch3 = "Search3" + Utility.dynamicNameAppender();
		String columnName = "Unique Hits".toUpperCase();

		String[] tabList = { Input.mySavedSearch, Input.shareSearchPA, Input.shareSearchDefaultSG };

		base.stepInfo("Test case Id:RPMXCON-56593 Reports/Search Term");
		base.stepInfo("Search Term Report - Verify view bulk action on Unique Hits / Unique Family Hits records");

		// Login
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		// save search in All 3 TAbs
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(saveSearch1);

		sessionSearch.getNewSearchButton().waitAndClick(5);
		sessionSearch.multipleBasicContentSearch(Input.searchString2);
		sessionSearch.saveSearchAtAnyRootGroup(saveSearch2, Input.shareSearchPA);
		sessionSearch.saveSearchAtAnyRootGroup(saveSearch3, Input.shareSearchDefaultSG);

		// select ALL tabs and generate STR report
		reports.navigateToReportsPage("");
		searchterm.GenerateReportWithAnySearchOrTab(tabList, true);

		// Select Unique Hit column
		searchterm.selectColumnFromSTRPage(columnName);

		// navigate to doclist
		searchterm.ViewInDocList();
		driver.waitForPageToBeReady();
		base.verifyPageNavigation("en-us/Document/DocList");

		// delete search
		savedSearch.deleteSearch(saveSearch1, Input.mySavedSearch, Input.yesButton);
		savedSearch.deleteSearch(saveSearch2, Input.shareSearchPA, Input.yesButton);
		savedSearch.deleteSearch(saveSearch3, Input.shareSearchDefaultSG, Input.yesButton);

		// logout
		loginPage.logout();
	}

	/**
	 * @author Jeevitha.R
	 * @Description : To verify that user can Export all searches on Search Term
	 *              Report [RPMXCON-56486]
	 */
	@Test(description = "RPMXCON-56486", dataProvider = "paRmuUsers", groups = { "regression" }, enabled = true)
	public void verifyUserCanExportSearchOnSTR(String userName, String password, String role)
			throws InterruptedException, ParseException {
		String saveSearch1 = "Search1" + Utility.dynamicNameAppender();
		String saveSearch2 = "Search2" + Utility.dynamicNameAppender();
		String[] searchList = { saveSearch1, saveSearch2 };
		String columnName = "Hits".toUpperCase();
		String[] metaDataFields = { "CustodianName", Input.documentKey };

		CustomDocumentDataReport cddReport = new CustomDocumentDataReport(driver);

		base.stepInfo("Test case Id:RPMXCON-56486 Reports/Search Term");
		base.stepInfo("To verify that user can Export all searches on Search Term Report");

		// Login
		loginPage.loginToSightLine(userName, password);

		// Configure query & save search
		sessionSearch.basicContentSearch(Input.searchString6);
		sessionSearch.saveSearch(saveSearch1);
		sessionSearch.saveSearch(saveSearch2);

		// select Search and generate STR report
		reports.navigateToReportsPage("");
		searchterm.GenerateReportWithAllSearches(searchList);

		// Select Column
		searchterm.selectColumnFromSTRPage(columnName);

		// click on Export Data from action & navigate to export page
		searchterm.STR_ToExportData();
		driver.waitForPageToBeReady();

		// Select Metadata
		cddReport.selectMetaDataFields(metaDataFields);

		// run report,verify Success Msg & click on recieved Notification
		cddReport.runReportandVerifyFileDownloaded();

		// delete search
		savedSearch.deleteSearch(saveSearch1, Input.mySavedSearch, Input.yesButton);
		savedSearch.deleteSearch(saveSearch2, Input.mySavedSearch, Input.yesButton);

		// logout
		loginPage.logout();
	}

	/**
	 * @author Jeevitha.R
	 * @Description : To verify custom Search Term report should be overwrite or
	 *              update. [RPMXCON-56357]
	 */
	@Test(description = "RPMXCON-56357", groups = { "regression" }, enabled = true)
	public void verifyCustomReportOverwriteOrUpdate() throws InterruptedException, ParseException {
		String saveSearch1 = "Search1" + Utility.dynamicNameAppender();
		String[] searchList = { saveSearch1 };

		base.stepInfo("Test case Id:RPMXCON-56357 Reports/Search Term");
		base.stepInfo("To verify custom Search Term report should be overwrite or update.");

		// Login
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		sessionSearch.basicContentSearch(Input.searchString6);
		sessionSearch.saveSearch(saveSearch1);

		// select Search and generate STR report
		reports.navigateToReportsPage("");
		searchterm.GenerateReportWithAllSearches(searchList);

		// save custom Report ,verify and click saved custom report
		String reportName1 = searchterm.ValidateSearchTermreportSaveandImpact(saveSearch1, true);
		driver.waitForPageToBeReady();

		// generate second custom report
		searchterm.GenerateReportWithAllSearches(searchList);
		String reportName2 = searchterm.ValidateSearchTermreportSaveandImpact(saveSearch1, true);

		// Overwrite the Custom Report , verify popup & click yes button
		reports.getCustomReport(reportName2).waitAndClick(1);
		searchterm.CustomStrOverwriteOrUpdate(reportName2, true, reportName2, true, true);

		// Overwrite the Custom Report , verify popup & click No button
		reports.getCustomReport(reportName2).waitAndClick(1);
		searchterm.CustomStrOverwriteOrUpdate(reportName2, true, reportName2, true, false);
		
		
		// delete search
		savedSearch.deleteSearch(saveSearch1, Input.mySavedSearch, Input.yesButton);

		// logout
		loginPage.logout();
	}

	/**
	 * @author Jeevitha.R
	 * @Description : To verify that Users can expand/collapsed the Searches
	 *              Criteria. [RPMXCON-56364]
	 */
	@Test(description = "RPMXCON-56364", groups = { "regression" }, enabled = true)
	public void verifyExpandAndCollapsed() throws InterruptedException, ParseException {

		base.stepInfo("Test case Id:RPMXCON-56364 Reports/Search Term");
		base.stepInfo("To verify that Users can expand/collapsed the Searches Criteria.");

		// Login
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		// select Search and generate STR report
		reports.navigateToReportsPage("");
		base.waitForElement(searchterm.getSearchTermReport());
		searchterm.getSearchTermReport().waitAndClick(10);

		searchterm.verifySearchExpand(true, false);
		searchterm.verifySearchExpand(true, true);

		// logout
		loginPage.logout();

	}

	/**
	 * @author NA Testcase No:RPMXCON-56579
	 * @Description:To verify that custom export repot should be shared to user or
	 *                 by email with the \"Scrub export of special characters\"
	 *                 settings
	 **/
	@Test(description = "RPMXCON-56579", enabled = true, groups = { "regression" })
	public void verifyScrubExportSplChShareByUser() throws Exception {
		SoftAssert asserts = new SoftAssert();
		CustomDocumentDataReport custom = new CustomDocumentDataReport(driver);
		CommentsPage comments = new CommentsPage(driver);
		String comment = "C_" + Utility.randomCharacterAppender(2);
		String[] workProduct = { comment };
		String[] metadata1 = { "DocID" };

		base.stepInfo("RPMXCON - 56579");
		base.stepInfo(
				"To verify that custom export repot should be shared to user or by email with the \"Scrub export of special characters\" settings");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		comments.AddComments(comment);
		base.stepInfo("Comments with special character is created");

		driver.waitForPageToBeReady();
		custom.navigateToCDDReportPage();
		custom.selectSources("Security Groups", Input.securityGroup);
		custom.selectMetaDataFields(metadata1);
		custom.selectWorkProductFields(workProduct);
		base.waitForElement(custom.getScrubLink());
		custom.getScrubLink().waitAndClick(10);
		driver.waitForPageToBeReady();

		// Delete
		base.waitForElement(custom.getSpecialCharDD(0));
		String befSpcOptDel = custom.getSpecialCharDD(0).selectFromDropdown().getFirstSelectedOption().getText();
		String befReplOptDel = custom.getReplaceCharDD(0).selectFromDropdown().getFirstSelectedOption().getText();
		System.out.println(befSpcOptDel);
		System.out.println(befReplOptDel);
		base.waitForElement(custom.getRedXIcon(0));
		custom.getRedXIcon(0).waitAndClick(5);
		asserts.assertFalse(custom.getSpecialCharDD(0).isElementAvailable(5));
		asserts.assertFalse(custom.getReplaceCharDD(0).isElementAvailable(5));
		asserts.assertAll();
		base.waitForElement(custom.getCloseBtn_ScrubPopup());
		custom.getCloseBtn_ScrubPopup().waitAndClick(5);

		// Newly Added
		base.waitForElement(custom.getScrubLink());
		custom.getScrubLink().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.waitForElement(custom.getAddNewBtn_ScrubLink());
		custom.getAddNewBtn_ScrubLink().ScrollTo();
		custom.getAddNewBtn_ScrubLink().waitAndClick(10);
		base.waitForElement(custom.getSpecialCharDDLast());
		String expSpcOptNew = custom.getSpecialCharDDLast().selectFromDropdown().getFirstSelectedOption().getText();
		String expRplcOptNew = custom.getReplaceCharLast().selectFromDropdown().getFirstSelectedOption().getText();
		System.out.println(expSpcOptNew);
		System.out.println(expRplcOptNew);
		base.waitForElement(custom.getCloseBtn_ScrubPopup());
		custom.getCloseBtn_ScrubPopup().waitAndClick(5);

		// Modify
		base.waitForElement(custom.getScrubLink());
		custom.getScrubLink().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.waitForElement(custom.getSpecialCharDD(0));
		String expSpcOptMod = custom.selectSpecialORReplaceCharByIndex("Special Character", 2, 3);
		String expRplcOptMod = custom.selectSpecialORReplaceCharByIndex("Replacement Character", 2, 3);
		System.out.println(expSpcOptMod);
		System.out.println(expRplcOptMod);

		custom.runReportandVerifyFileDownloaded();
		base.waitForElement(base.getBackgroundTask_Button());
		base.getBackgroundTask_Button().waitAndClick(10);
		driver.waitForPageToBeReady();
		custom.performSharingAction(Input.pa2FullName, Input.pa2userName);
		base.verifyMegaPhoneIconAndBackgroundTasks(true, false);
		driver.waitForPageToBeReady();
		String Id = custom.SharedIDFromNotification(1);
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);
		driver.waitForPageToBeReady();
		base.verifyMegaPhoneIconAndBackgroundTasks(true, false);
		custom.openSharedNotification(Id);
		driver.waitForPageToBeReady();
		base.waitForElement(custom.getScrubLink());
		custom.getScrubLink().waitAndClick(10);

		base.waitForElement(custom.getSpecialCharDDLast());
		String actSpcOptNew = custom.getSpecialCharDDLast().selectFromDropdown().getFirstSelectedOption().getText();
		String actRplcOptNew = custom.getReplaceCharLast().selectFromDropdown().getFirstSelectedOption().getText();
		asserts.assertEquals(expSpcOptNew, actSpcOptNew);
		asserts.assertEquals(expRplcOptNew, actRplcOptNew);
		asserts.assertAll();

		driver.waitForPageToBeReady();
		String actSpcOptMod = custom.getSpecialCharDD(1).selectFromDropdown().getFirstSelectedOption().getText();
		String actRplcOptMod = custom.getReplaceCharDD(1).selectFromDropdown().getFirstSelectedOption().getText();
		asserts.assertEquals(expSpcOptMod, actSpcOptMod);
		asserts.assertEquals(expRplcOptMod, actRplcOptMod);
		asserts.assertAll();
		base.passedStep(
				"To verify that custom export repot should be shared to user or by email with the \"Scrub export of special characters\" settings");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-56702
	 * @Description:Verify Advanced Batch Management Report layout
	 **/
	@Test(description = "RPMXCON-56702", enabled = true, groups = { "regression" })
	public void verifyABMreportLayout() throws InterruptedException, Exception {
		ABMReportPage abmReport = new ABMReportPage(driver);
		SessionSearch session = new SessionSearch(driver);
		AssignmentsPage assign = new AssignmentsPage(driver);
		SoftAssert asserts = new SoftAssert();

		String searchName = "Search" + Utility.dynamicNameAppender();
		String assignmentName = "Assignment" + Utility.dynamicNameAppender();
		String expPageTitle = "Advanced Batch Management";

		base.stepInfo("RPMXCON-56702");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Verify Advanced Batch Management Report layout");

//		create folder and add save search in folder
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName);
		session.bulkAssign();

//		create Assignment and disturbute docs
		assign.assignmentCreation(assignmentName, Input.codeFormName);
		assign.add2ReviewerAndDistribute();

		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		base.waitForElement(abmReport.getReport_ABM());
		abmReport.getReport_ABM().waitAndClick(5);

//		Verify Report Title
		base.waitForElement(abmReport.getPageTitle());
		String pageTitle = abmReport.getPageTitle().getText().trim();
		asserts.assertEquals(expPageTitle, pageTitle);
		asserts.assertTrue(abmReport.getHelpButton().isElementAvailable(6));
		asserts.assertAll();

//		Verify Report Options
		base.waitForElement(abmReport.getManageBatchAt());
		asserts.assertTrue(abmReport.getManageBatchAt().isElementAvailable(5));
		asserts.assertAll();

//		Verify Report Source
		base.waitForElement(abmReport.getABM_SelectSource());
		abmReport.getABM_SelectSource().waitAndClick(5);
		base.waitForElement(abmReport.getABM_SearchButton());
		asserts.assertTrue(abmReport.getABM_SearchButton().isElementAvailable(5));
		asserts.assertAll();

//		Verify Other Features..
		abmReport.generateABM_Report(searchName, assignmentName, false);
		String ReportTime = abmReport.getDateTimeinReport().getText();
		String serverTime = base.getcurrentdateinUTC();
		System.out.println(ReportTime);
		System.out.println(serverTime);
		asserts.assertTrue(ReportTime.contains(serverTime));
		asserts.assertTrue(abmReport.getShareButton().isElementAvailable(5));
		asserts.assertAll();
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.impersonatePAtoRMU();
		base.passedStep("Verified Advanced Batch Management Report layout");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-56701
	 * @Description:To Verify Advanced Batch Management report option
	 **/
	@Test(description = "RPMXCON-56701", enabled = true, groups = { "regression" })
	public void verifyABMReportOption() throws InterruptedException, Exception {
		ABMReportPage abmReport = new ABMReportPage(driver);
		SessionSearch session = new SessionSearch(driver);
		AssignmentsPage assign = new AssignmentsPage(driver);
		AssignmentReviewProgressReport arpr = new AssignmentReviewProgressReport(driver);
		ReportsPage report = new ReportsPage(driver);
		SoftAssert aserts = new SoftAssert();

		String searchName = "Search" + Utility.dynamicNameAppender();
		String assignmentName1 = "Assignment" + Utility.dynamicNameAppender();
		String assignmentName2 = "Assignment" + Utility.dynamicNameAppender();
		String assignmentGrpName = "AssignmentGrp" + Utility.dynamicNameAppender();
		String reportABM = "ABMReport" + Utility.dynamicNameAppender();
		String reportARP = "ARPReport" + Utility.dynamicNameAppender();

		base.stepInfo("RPMXCON-56701");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("To Verify Advanced Batch Management report option");

		assign.navigateToAssignmentsPage();
		assign.createCascadeNonCascadeAssgnGroup(assignmentGrpName, "No");
		assign.selectAssignmentGroup(assignmentGrpName);
		assign.createAssignmentFromAssgnGroup(assignmentName2, Input.codeFormName);

		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName);
		session.bulkAssignExisting(assignmentName2);
		session.bulkAssign();

//		create Assignment and disturbute docs
		assign.assignmentCreation(assignmentName1, Input.codeFormName);
		assign.add2ReviewerAndDistribute();
		abmReport.generateABM_Report(searchName, assignmentName1, false);
		abmReport.SaveReport(reportABM);

		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		arpr.navigateToAssgnmntReviewProgressReport();
		arpr.generateARPreport(assignmentGrpName, "1LR");
		arpr.SaveReport(reportARP);

		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		base.waitForElementCollection(report.getCustomReportList());
		List<String> reportList = base.availableListofElements(report.getCustomReportList());
		int indexOfABM = reportList.indexOf(reportABM);
		System.out.println(indexOfABM);
		int indexOfARP = reportList.indexOf(reportARP);
		System.out.println(indexOfARP);
		aserts.assertTrue(indexOfABM < indexOfARP);
		aserts.assertTrue(report.getReportReviewCustom(reportABM).isElementAvailable(5));
		aserts.assertAll();

		base.passedStep("Verify Advanced Batch Management report option");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-56255
	 * @Description:To verify that User is able to select Date and Time from date
	 *                 selection criteria on Reviewer Counts by Day/Hour Report
	 **/
	@Test(description = "RPMXCON-56255", dataProvider = "PA & RMU", enabled = true, groups = { "regression" })
	public void verifyUserAbleToSelectDateTime(String username, String password) throws Exception {
		ReviewerCountsReportPage report = new ReviewerCountsReportPage(driver);
		SoftAssert asserts = new SoftAssert();

		base.stepInfo("RPMXCON - 56255");
		loginPage.loginToSightLine(username, password);
		base.stepInfo(
				"To verify that User is able to select Date and Time from date selection criteria on Reviewer Counts by Day/Hour Report");
		base.stepInfo("Logged in As : " + username);
		report.navigateTOReviewerCountsReportPage();
		driver.waitForPageToBeReady();
		base.waitForElement(report.getHourlyRadio());
		report.getHourlyRadio().waitAndClick(5);
		base.waitForElement(report.getExpandDateRange());
		report.getExpandDateRange().waitAndClick(5);
		driver.waitForPageToBeReady();

		String actFromDate = report.getSelectFromDateTime().Value();
		base.stepInfo("By Default Date and Time From Range : " + actFromDate);
		String actToDate = report.getSelectToDateTime().Value();
		base.stepInfo("By Default Date and Time To Range : " + actToDate);

		String expDateTime = base.getcurrentdateinUTC();
		report.selectFromDate(report.getSelectFromDateTime(), "From");
		report.selectFromDate(report.getSelectToDateTime(), "To");

		String afterFrmChange = report.getSelectFromDateTime().Value();
		base.stepInfo("After Change From Range : " + afterFrmChange);
		String afterToChange = report.getSelectToDateTime().Value();
		base.stepInfo("After Change To Range : " + afterFrmChange);
		asserts.assertFalse(afterFrmChange.contains(expDateTime));
		asserts.assertFalse(afterToChange.contains(expDateTime));
		asserts.assertAll();
		base.passedStep("User Can Select Date Time From and To Range");

		asserts.assertTrue(actFromDate.contains(expDateTime));
		asserts.assertTrue(actToDate.contains(expDateTime));
		asserts.assertAll();
		base.passedStep("Date Selection criteria is displayed with current date and time by default selected.");
		base.passedStep("Verified - that User is able to select Date and Time from date selection "
				+ "criteria on Reviewer Counts by Day/Hour Report");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-56568
	 * @Description:To verify that 'Docs Distributed' should appear as 'My Batch
	 *                 Docs' in 'reviewer progress report'
	 **/
	@Test(description = "RPMXCON-56568", enabled = true, groups = { "regression" })
	public void verifyDocDistriMyBatchDocs() throws Exception {
		AssignmentsPage assignment = new AssignmentsPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		ReviewerReviewProgressReport rrpr = new ReviewerReviewProgressReport(driver);
		UserManagement user = new UserManagement(driver);

		String assignmentName = "Assignment" + Utility.dynamicNameAppender();
		String assignmentGrpName = "AssignmentGrp" + Utility.dynamicNameAppender();
		String expMsg = "Total no of Docs distributed should be displayed on 'My Batch Docs' column";
		String failMsg = "Total no of Docs distributed should be displayed on 'My Batch Docs' column";

		base.stepInfo("RPMXCON - 56568");
		base.stepInfo(
				"To verify that 'Docs Distributed' should appear as 'My Batch Docs' in 'reviewer progress report'");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Logged in As : " + Input.rmu1userName);

		user.navigateToUsersPAge();
		driver.waitForPageToBeReady();
		user.passingUserName(Input.rev1userName);
		user.applyFilter();
		String firstName = user.getTableData("FIRST NAME", 1);
		String lastName = user.getTableData("LAST NAME", 1);
		String userName = firstName + " " + lastName;

		assignment.navigateToAssignmentsPage();
		assignment.createCascadeNonCascadeAssgnGroup(assignmentGrpName, "No");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		assignment.selectAssignmentGroup(assignmentGrpName);
		assignment.createAssignmentFromAssgnGroup(assignmentName, Input.codeFormName);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		// search to Assignment creation
		sessionSearch.navigateToSessionSearchPageURL();
		int purehit = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkAssignExisting(assignmentName);
		driver.waitForPageToBeReady();
		assignment.navigateToAssignmentsPage();
		driver.waitForPageToBeReady();
		assignment.editAssignmentInAssignGroup(assignmentGrpName, assignmentName);
		assignment.addReviewerInManageReviewerTab();
		assignment.distributeGivenDocCountToReviewers(String.valueOf(purehit));

		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		rrpr.navigateToReviewerReviewProgressReport();
		driver.waitForPageToBeReady();
		rrpr.generateRRPreport(assignmentGrpName, userName);
		driver.waitForPageToBeReady();
		String actMyBatchDogs = rrpr.getColoumnValue(rrpr.reviewerColumnNameHeader(), "My Batch Docs", assignmentName);
		base.textCompareEquals(actMyBatchDogs, String.valueOf(6), expMsg, failMsg);
		base.passedStep(
				"Verified - that 'Docs Distributed' should appear as 'My Batch Docs' in 'reviewer progress report'");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-56722
	 * @Description: Executing the tally report with CustodianName & EmailAuthorName
	 *               filters selected
	 **/
	@Test(description = "RPMXCON-56722", dataProvider = "PA & RMU", enabled = true, groups = { "regression" })
	public void validateTallyReportWithCNandEA(String username, String password) throws Exception {
		CommunicationExplorerPage commExpl = new CommunicationExplorerPage(driver);
		TallyPage tally = new TallyPage(driver);
		SoftAssert asserts = new SoftAssert();

		base.stepInfo("RPMXCON-56722");
		base.stepInfo("To Executing the tally report with CustodianName & EmailAuthorName filters selected");
		loginPage.loginToSightLine(username, password);
		base.stepInfo("Logged in As : " + username);

		commExpl.navigateToCommunicationExpPage();
		commExpl.generateReportusingDefaultSG();
		commExpl.clickReport();
		commExpl.analyzeInTallyAction();
		driver.waitForPageToBeReady();

		tally.selectTallyByMetaDataField(Input.metaDataName);
		driver.waitForPageToBeReady();
		base.waitForElement(tally.metaDataFilterForTallyBy(Input.metaDataName));
		tally.metaDataFilterForTallyBy(Input.metaDataName).waitAndClick(10);
		base.waitForElement(tally.FilterInputTextBoxTallyBy());
		float beforeCustName = System.currentTimeMillis();
		tally.FilterInputTextBoxTallyBy().waitAndClick(10);
		base.waitForElementCollection(tally.getAllValueinCustNameFilter());
		float afterCustName = System.currentTimeMillis();
		base.waitForElement(tally.getCloseByCNFilterPopUp());
		tally.getCloseByCNFilterPopUp().waitAndClick(5);
		float totalSecCustName = afterCustName - beforeCustName;
		System.out.println(totalSecCustName);
		asserts.assertTrue(totalSecCustName < 4000);
		driver.waitForPageToBeReady();
		tally.applyFilterToTallyByIndex(Input.metaDataName, "exclude", 1);
		driver.waitForPageToBeReady();

		base.waitForElement(tally.metaDataFilterForTallyBy(Input.MetaDataEAName));
		tally.metaDataFilterForTallyBy(Input.MetaDataEAName).ScrollTo();
		tally.metaDataFilterForTallyBy(Input.MetaDataEAName).waitAndClick(10);
		base.waitForElement(tally.FilterInputTextBoxTallyBy());
		float beforeEmailAuthor = System.currentTimeMillis();
		tally.FilterInputTextBoxTallyBy().waitAndClick(5);
		base.waitForElementCollection(tally.getAllValueinEmailAuthorFilter());
		float afterEmailAuthor = System.currentTimeMillis();
		base.waitForElement(tally.getCloseByEAFilterPopUp());
		tally.getCloseByEAFilterPopUp().waitAndClick(5);
		float totalSecEmailAuthor = afterEmailAuthor - beforeEmailAuthor;
		asserts.assertTrue(totalSecEmailAuthor < 4000);
		driver.waitForPageToBeReady();
		tally.applyFilterToTallyByIndex(Input.MetaDataEAName, "exclude", 1);
		driver.waitForPageToBeReady();

		base.waitForElement(tally.getTally_btnTallyApply());
		base.waitTillElemetToBeClickable(tally.getTally_btnTallyApply());
		tally.getTally_btnTallyApply().waitAndClick(5);
		;
		if (tally.getPopupYesBtn().isElementAvailable(30)) {
			tally.getPopupYesBtn().waitAndClick(5);
		}
		tally.verifyTallyChart();
		base.passedStep("Executed - the tally report with CustodianName & EmailAuthorName filters selected");
		loginPage.logout();
	}

	/**
	 * @author sowndarya Testcase No:RPMXCON-56576
	 * @Description:To verify that if “Scrub export of special characters" option is
	 *                 toggled OFF in Export Report
	 **/
	@Test(description = "RPMXCON-56576", enabled = true, groups = { "regression" })
	public void verifyScrubExportSplChToggle() throws Exception {

		CustomDocumentDataReport custom = new CustomDocumentDataReport(driver);
		ReportsPage report = new ReportsPage(driver);
		CommentsPage comments = new CommentsPage(driver);
		String comment = "C_" + Utility.randomCharacterAppender(2);
		String[] workProduct = { comment };
		String[] metadata1 = { "DocID" };
		base.stepInfo("RPMXCON - 56576");
		base.stepInfo("verify that if Scrub export of special characters option is toggled OFF in Export Report");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		comments.AddComments(comment);
		base.stepInfo("Comments with special character is created");

		driver.waitForPageToBeReady();
		custom.navigateToCDDReportPage();
		custom.selectSources("Security Groups", Input.securityGroup);
		custom.selectMetaDataFields(metadata1);
		custom.selectWorkProductFields(workProduct);

		base.stepInfo("Toggle Scrub Export of special characters to Off and run report");
		base.waitForElement(custom.getToggle_ScrubSpecChar());
		custom.getToggle_ScrubSpecChar().waitAndClick(10);
		String fileName = custom.runReportandVerifyFileDownloaded();
		String actualValue = custom.csvfileVerification("", fileName);
		base.stepInfo(actualValue);
		System.out.println(actualValue);
		SoftAssert assets = new SoftAssert();
		assets.assertTrue(actualValue.contains("_"));
		assets.assertAll();
		base.passedStep("Special characters are not be replaced.");
	}

	/**
	 * @author sowndarya Testcase No:RPMXCON-56574
	 * @Description: To verify that any row/pair may be deleted by clicking the red
	 *               "x" icon on export report->Scrub export of special characters
	 *               pop up
	 **/
	@Test(description = "RPMXCON-56574", enabled = true, groups = { "regression" })
	public void verifyRowDeletedInSCrubReport() throws Exception {

		CustomDocumentDataReport custom = new CustomDocumentDataReport(driver);
		CommentsPage comments = new CommentsPage(driver);
		String comment = "C_" + Utility.randomCharacterAppender(2);
		String[] workProduct = { comment };
		String[] metadata1 = { "DocID" };
		base.stepInfo("RPMXCON - 56574");
		base.stepInfo(
				"To verify that any row/pair may be deleted by clicking the red \"x\" icon on export report->Scrub export of special characters pop up");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		comments.AddComments(comment);
		base.stepInfo("Comments with special character is created");

		driver.waitForPageToBeReady();
		custom.navigateToCDDReportPage();
		custom.selectSources("Security Groups", Input.securityGroup);
		custom.selectMetaDataFields(metadata1);
		custom.selectWorkProductFields(workProduct);

		base.stepInfo("Toggle Scrub Export of special characters and delete existing character set");
		base.waitForElement(custom.getToggle_ScrubSpecChar());
		custom.getToggle_ScrubSpecChar().waitAndClick(10);
		base.waitForElement(custom.getScrubLink());
		custom.getScrubLink().waitAndClick(10);
		base.waitForElement(custom.getRedXIcon());
		custom.getRedXIcon().waitAndClick(10);
		base.passedStep("Deleted  existing replacement character set on clicking on 'x' icon");

	}

	/**
	 * @author NA Testcase No:RPMXCON-56721
	 * @Description: Executing the tally report with EmailAuthorName filters
	 *               selected
	 **/
	@Test(description = "RPMXCON-56721", dataProvider = "PA & RMU", enabled = true, groups = { "regression" })
	public void validateTallyReportWithEA(String username, String password) throws Exception {
		CommunicationExplorerPage commExpl = new CommunicationExplorerPage(driver);
		TallyPage tally = new TallyPage(driver);
		SoftAssert asserts = new SoftAssert();

		base.stepInfo("RPMXCON - 56721");
		base.stepInfo("To Executing the tally report with EmailAuthorName filters selected");
		loginPage.loginToSightLine(username, password);
		base.stepInfo("Logged in As : " + username);

		commExpl.navigateToCommunicationExpPage();
		driver.waitForPageToBeReady();
		commExpl.generateReportusingDefaultSG();
		commExpl.clickReport();
		commExpl.analyzeInTallyAction();
		driver.waitForPageToBeReady();

		tally.selectTallyByMetaDataField(Input.metaDataName);
		base.waitForElement(tally.metaDataFilterForTallyBy(Input.MetaDataEAName));
		tally.metaDataFilterForTallyBy(Input.MetaDataEAName).waitAndClick(10);
		base.waitForElement(tally.FilterInputTextBoxTallyBy());
		tally.FilterInputTextBoxTallyBy().waitAndClick(5);
		float beforeEmailAuthor = System.currentTimeMillis();
		base.waitForElementCollection(tally.getAllValueinEmailAuthorFilter());
		float afterEmailAuthor = System.currentTimeMillis();
		base.waitForElement(tally.getCloseByEAFilterPopUp());
		tally.getCloseByEAFilterPopUp().waitAndClick(5);
		float totalSecEmailAuthor = afterEmailAuthor - beforeEmailAuthor;
		System.out.println(totalSecEmailAuthor);
		asserts.assertTrue(totalSecEmailAuthor < 4000);

		driver.waitForPageToBeReady();
		tally.applyFilterToTallyByIndex(Input.MetaDataEAName, "exclude", 1);
		driver.waitForPageToBeReady();

		base.waitForElement(tally.getTally_btnTallyApply());
		base.waitTillElemetToBeClickable(tally.getTally_btnTallyApply());
		tally.getTally_btnTallyApply().Click();
		if (tally.getPopupYesBtn().isElementAvailable(30)) {
			tally.getPopupYesBtn().waitAndClick(5);
		}
		tally.verifyTallyChart();

		base.passedStep("Executed - the tally report with EmailAuthorName filters selected");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-56716
	 * @Description: Validate Navigate to Tally report from ABM Report
	 **/
	@Test(description = "RPMXCON-56716", enabled = true, groups = { "regression" })
	public void verifyNavigationTallyFromABM() throws Exception {
		ABMReportPage abmReportPage = new ABMReportPage(driver);
		TallyPage tally = new TallyPage(driver);
		SoftAssert asserts = new SoftAssert();
		SessionSearch sessionsearch = new SessionSearch(driver);
		AssignmentsPage assignmentsPage = new AssignmentsPage(driver);

		String assignmentName1 = "assgnment" + Utility.dynamicNameAppender();
		String SaveSaerchName = "ABMSaveSearch" + UtilityLog.dynamicNameAppender();
		String expSource = "Selected Documents from AdvBatchMangementRpt";

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		base.stepInfo("Test case Id:RPMXCON-56716");
		base.stepInfo("Validate Navigate to Tally report from ABM Report");

		// Basic Search and Bulk Assign
		sessionsearch.basicContentSearch(Input.testData1);
		base.stepInfo("Search for text input completed");
		sessionsearch.verifyPureHitsCount();
		sessionsearch.saveSearch(SaveSaerchName);
		sessionsearch.bulkAssign();

		// create Assignment and disturbute docs
		assignmentsPage.assignmentCreation(assignmentName1, Input.codeFormName);
		assignmentsPage.add2ReviewerAndDistribute();
		base.stepInfo(assignmentName1 + "  Assignment Created and distributed ");

		abmReportPage.validateRevListAndgenerateABM_Report(SaveSaerchName, assignmentName1, false, false);
		driver.waitForPageToBeReady();
		abmReportPage.selectDocsinTable(assignmentName1, "IN SET", true);
		driver.waitForPageToBeReady();
		abmReportPage.performTallyResult();
		driver.waitForPageToBeReady();

		tally.selectTallyByMetaDataField(Input.metaDataName);
		driver.waitForPageToBeReady();

		base.waitForElement(tally.getTally_btnTallyAll());
		tally.getTally_btnTallyAll().waitAndClick(5);
		driver.waitForPageToBeReady();
		tally.runBgReportandVerifyFileDownloaded();

		base.waitForElement(tally.getTally_SelectedSource());
		String selectedSource = tally.getTally_SelectedSource().getText();
		asserts.assertTrue(selectedSource.contains(expSource));
		asserts.assertAll();
		base.passedStep("Verified - Navigate to Tally report from ABM Report");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-56947
	 * @Description: To Validate Distributed document count for Review Count report
	 *               by hourly (Project Admin login)
	 **/
	@Test(description = "RPMXCON-56947", enabled = true, groups = { "regression" })
	public void verifyDisCountFrRevCountRep() throws Exception {
		ReviewerCountsReportPage count = new ReviewerCountsReportPage(driver);
		AssignmentsPage assign = new AssignmentsPage(driver);
		SoftAssert asserts = new SoftAssert();
		SessionSearch session = new SessionSearch(driver);

		String assignment1 = "Assignment" + Utility.dynamicNameAppender();
		String[] reviewers = { Input.rev1FullName, Input.rmu1FullName };

		base.stepInfo("RPMXCON-56947");
		base.stepInfo("To Validate Distributed document count for Review Count report by hourly (Project Admin login)");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Logged in As : " + Input.rmu1FullName);

		assign.navigateToAssignmentsPage();
		driver.waitForPageToBeReady();
		assign.createAssignment(assignment1, Input.codeFormName);
		driver.waitForPageToBeReady();
		session.basicContentSearch(Input.searchString1);
		driver.waitForPageToBeReady();
		session.bulkAssignExisting(assignment1);
		assign.editAssignment(assignment1);
		driver.waitForPageToBeReady();
		assign.add2ReviewerAndDistribute();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in As : " + Input.pa1FullName);
		count.navigateTOReviewerCountsReportPage();
		driver.waitForPageToBeReady();
		count.generateReportMultiRev(assignment1, reviewers);
		driver.waitForPageToBeReady();
		String completedDocsInReportPage = count.verifyColumnValueDisplay(count.getTableHeaders(), "Distributed Docs");
		System.out.println(completedDocsInReportPage);
		asserts.assertTrue(completedDocsInReportPage.equals("100"));
		asserts.assertAll();
		base.passedStep(
				"Validated - Distributed document count for Review Count report by hourly (Project Admin login)");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-56948
	 * @Description: Validate Distributed document count for Review Count report by
	 *               hourly (RMU login)
	 **/
	@Test(description = "RPMXCON-56948", enabled = true, groups = { "regression" })
	public void verifyDisCountFrRevCountRepRMULgin() throws Exception {
		ReviewerCountsReportPage count = new ReviewerCountsReportPage(driver);
		AssignmentsPage assign = new AssignmentsPage(driver);
		SoftAssert asserts = new SoftAssert();
		SessionSearch session = new SessionSearch(driver);

		String assignment1 = "Assignment" + Utility.dynamicNameAppender();
		String[] reviewers = { Input.rev1FullName, Input.rmu1FullName };

		base.stepInfo("RPMXCON-56948");
		base.stepInfo("Validate Distributed document count for Review Count report by hourly (RMU login)");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Logged in As : " + Input.rmu1FullName);

		assign.navigateToAssignmentsPage();
		driver.waitForPageToBeReady();
		assign.createAssignment(assignment1, Input.codeFormName);
		driver.waitForPageToBeReady();
		session.basicContentSearch(Input.searchString1);
		driver.waitForPageToBeReady();
		session.bulkAssignExisting(assignment1);
		assign.editAssignment(assignment1);
		driver.waitForPageToBeReady();
		assign.add2ReviewerAndDistribute();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		count.navigateTOReviewerCountsReportPage();
		driver.waitForPageToBeReady();
		count.generateReportMultiRev(assignment1, reviewers);
		driver.waitForPageToBeReady();
		String completedDocsInReportPage = count.verifyColumnValueDisplay(count.getTableHeaders(), "Distributed Docs");
		System.out.println(completedDocsInReportPage);
		asserts.assertTrue(completedDocsInReportPage.equals("100"));
		asserts.assertAll();
		base.passedStep("Validated - Distributed document count for Review Count report by hourly (RMU login)");
		loginPage.logout();
	}

	/**
	 * @author sowndarya Testcase No:RPMXCON-56581
	 * @Description:To verify export report if ‘Export Object Name’ and “Scrub
	 *                 export of special characters" option is toggled ‘OFF’
	 **/
	@Test(description = "RPMXCON-56581", enabled = true, groups = { "regression" })
	public void verifyScrubExportSplChAndExportObjNameToggle() throws Exception {

		CustomDocumentDataReport custom = new CustomDocumentDataReport(driver);
		CommentsPage comments = new CommentsPage(driver);
		String comment = "C_" + Utility.randomCharacterAppender(2);
		String[] workProduct = { comment };
		String[] metadata1 = { "DocID" };
		base.stepInfo("RPMXCON - 56581");
		base.stepInfo(
				"To verify export report if ‘Export Object Name’ and “Scrub export of special characters\" option is toggled ‘OFF’");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		comments.AddComments(comment);
		base.stepInfo("Comments with special character is created");

		driver.waitForPageToBeReady();
		custom.navigateToCDDReportPage();
		custom.selectSources("Security Groups", Input.securityGroup);
		custom.selectMetaDataFields(metadata1);
		custom.selectWorkProductFields(workProduct);

		base.waitForElement(custom.getToggle_ScrubSpecChar());
		custom.getToggle_ScrubSpecChar().waitAndClick(10);
		base.stepInfo("Toggle Scrub Export of special characters is turned Off ");

		base.waitForElement(custom.getToggle_ObjectName());
		custom.getToggle_ObjectName().waitAndClick(10);
		base.stepInfo("Toggle Export object name is turned Off ");

		custom.verifyScrubToggleAddDeleteAndModify();
		base.stepInfo("verified scrub Toggle Add/Delete/Modify ");

		String fileName = custom.runReportandVerifyFileDownloaded();
		String actualValue = custom.csvfileVerification("", fileName);
		base.stepInfo(actualValue);
		System.out.println(actualValue);
		SoftAssert assets = new SoftAssert();
		assets.assertTrue(actualValue.contains("_"));
		assets.assertAll();
		base.passedStep("Report generated successfully");
	}

	/**
	 * @author NA TESTCASE No:RPMXCON-56773
	 * @Description: Verified - Conceptual Report generation based on Clusters
	 **/
	@Test(description = "RPMXCON-56773", enabled = true, groups = { "regression" })
	public void verifyConceptualReportBasedOnCLuster() throws Exception {
		ConceptExplorerPage concept = new ConceptExplorerPage(driver);
		SoftAssert asserts = new SoftAssert();
		SessionSearch sessionSearch = new SessionSearch(driver);

		String sourceToSelect = "Security Groups";
		String folderName = "Folder" + Utility.dynamicNameAppender();

		base.stepInfo("RPMXCON-56773");
		base.stepInfo("Verified - Conceptual Report generation based on Clusters");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in As  : " + Input.pa1userName);

		concept.navigateToConceptExplorerPage();
		driver.waitForPageToBeReady();
		concept.clickSelectSources();
		concept.selectSGsource(sourceToSelect, Input.securityGroup);
		driver.waitForPageToBeReady();
		concept.filterAction(Input.custodianName_Andrew, Input.metaDataName, null, true);
		concept.applyFilter("Yes", 10);
		driver.waitForPageToBeReady();
		base.waitForElementCollection(concept.getDataToAddInCart());
		asserts.assertTrue(concept.getDataToAddInCart().isElementAvailable(5));
		asserts.assertAll();
		base.stepInfo("Clusters are displayed at the bottom of the page ");
		String docCount = concept.addMultipleTilesToCart(1);
		driver.waitForPageToBeReady();
		concept.performActions("Bulk Folder");
		String bulkFolderCount = sessionSearch.BulkActions_Folder_returnCount(folderName);
		base.textCompareEquals(docCount, bulkFolderCount, "Folder Document count matches as expected",
				"Mis-match in document count");
		base.stepInfo("Bulk Folder Action done successfully");
		base.passedStep("To Verify Conceptual Report generation based on Clusters");
		loginPage.logout();
	}
}