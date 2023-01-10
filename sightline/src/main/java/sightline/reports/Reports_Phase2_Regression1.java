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
import pageFactory.BaseClass;
import pageFactory.CommunicationExplorerPage;
import pageFactory.ConceptExplorerPage;
import pageFactory.CustomDocumentDataReport;
import pageFactory.LoginPage;
import pageFactory.ReportsPage;
import pageFactory.SavedSearch;
import pageFactory.SearchTermReportPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.TimelineReportPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Reports_Phase2_Regression1 {

	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	BaseClass baseClass;
	Input in;
	SoftAssert assertion;
	ReportsPage reports;
	ConceptExplorerPage conceptExplorer;
	CommunicationExplorerPage communicationExpPage;
	SearchTermReportPage searchterm;

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

		sessionSearch = new SessionSearch(driver);
		reports = new ReportsPage(driver);
		conceptExplorer = new ConceptExplorerPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		assertion = new SoftAssert();
		savedSearch = new SavedSearch(driver);
		communicationExpPage = new CommunicationExplorerPage(driver);
		searchterm = new SearchTermReportPage(driver);
	}

	@DataProvider(name = "paRmuUsers")
	public Object[][] paRmuUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, "PA" },
				{ Input.rmu1userName, Input.rmu1password, "RMU" } };
		return users;
	}

	/**
	 * @author Jeevitha.R
	 * @Description : To verify that in Search Term Report page Searches criteria is
	 *              mandatory. [RPMXCON-56365]
	 */
	@Test(description = "RPMXCON-56365", groups = { "regression" }, enabled = true)
	public void verifySTRPageSearchIsMandatory() throws InterruptedException, ParseException {

		baseClass.stepInfo("Test case Id:RPMXCON-56365 Reports/Search Term");
		baseClass.stepInfo("To verify that in Search Term Report page Searches criteria is mandatory.");

		// Login as User
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		// Click Apply button without selecting any search
		reports.navigateToReportsPage("Search Term Report");
		driver.waitForPageToBeReady();
		baseClass.waitTillElemetToBeClickable(searchterm.mySavedSearchCheckbox());
		baseClass.waitTillElemetToBeClickable(searchterm.getApplyBtn());
		searchterm.getApplyBtn().waitAndClick(10);

		// verify Error message
		baseClass.VerifyErrorMessage("Please select at least one search.");

		// logout
		loginPage.logout();
	}

	/**
	 * @author Jeevitha.R
	 * @Description : To verify that if there is no data is STR then message is
	 *              displayed to User. [RPMXCON-56366]
	 */
	@Test(description = "RPMXCON-56366", groups = { "regression" }, enabled = true)
	public void verifyNoDatainSTRThenMsgDisplayed() throws InterruptedException, ParseException {

		baseClass.stepInfo("Test case Id:RPMXCON-56366 Reports/Search Term");
		baseClass.stepInfo("To verify that if there is no data is STR then message is displayed to User.");

		// Login as User
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		// create empty search group
		String searchGrp = savedSearch.createNewSearchGrp(Input.mySavedSearch);
		String[] selectSearchGrp = { searchGrp };

		// Select empty search group and Click Apply button
		reports.navigateToReportsPage("");
		searchterm.GenerateReportWithAnySearchOrTab(selectSearchGrp, false);

		// verify Error message
		baseClass.VerifyErrorMessage("Please select valid search. Report will not generate for empty group");

		// logout
		loginPage.logout();
	}

	/**
	 * @author Jeevitha.R
	 * @throws AWTException
	 * @Description : Search Term Report - Verify bulk folder action on Unique Hits
	 *              / Unique Family Hits records [RPMXCON-56596]
	 */
	@Test(description = "RPMXCON-56596", groups = { "regression" }, enabled = true)
	public void verifyViewBulkFolder() throws InterruptedException, ParseException, AWTException {
		String saveSearch1 = "Search1" + Utility.dynamicNameAppender();
		String saveSearch2 = "Search2" + Utility.dynamicNameAppender();
		String saveSearch3 = "Search3" + Utility.dynamicNameAppender();
		String folderName = "FOLDER" + Utility.dynamicNameAppender();

		String columnName = "Unique Hits".toUpperCase();

		String[] tabList = { Input.mySavedSearch, Input.shareSearchPA, Input.shareSearchDefaultSG };

		baseClass.stepInfo("Test case Id:RPMXCON-56596 Reports/Search Term");
		baseClass
				.stepInfo("Search Term Report - Verify bulk folder action on Unique Hits / Unique Family Hits records");

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

		// get Unique Hit count from search term report page
		int hitcount = searchterm.verifyaggregateCount(columnName);

		// perform Bulk Folder
		searchterm.BulkFolder(folderName);
		baseClass.passedStep("Bulk Folder Performed Successfully");

		// verify the selected document is moved to the folder
		TagsAndFoldersPage tagsAndFolder = new TagsAndFoldersPage(driver);
		tagsAndFolder.navigateToTagsAndFolderPage();
		tagsAndFolder.selectingFolderAndVerifyingTheDocCount(folderName, hitcount);

		// delete search
		savedSearch.deleteSearch(saveSearch1, Input.mySavedSearch, Input.yesButton);
		savedSearch.deleteSearch(saveSearch2, Input.shareSearchPA, Input.yesButton);
		savedSearch.deleteSearch(saveSearch3, Input.shareSearchDefaultSG, Input.yesButton);

		// logout
		loginPage.logout();
	}

	/**
	 * @author Jeevitha.R
	 * @throws AWTException
	 * @Description : Search Term Report - Verify Scheduling/Sharing/Expoting/Saving
	 *              STR report with Unique Hits / Unique Family Hits column
	 *              [RPMXCON-56589]
	 */
	@Test(description = "RPMXCON-56589", groups = { "regression" }, enabled = true)
	public void verifyStrReport() throws InterruptedException, ParseException, AWTException {
		String saveSearch1 = "Search1" + Utility.dynamicNameAppender();
		String saveSearch2 = "Search2" + Utility.dynamicNameAppender();
		String saveSearch3 = "Search3" + Utility.dynamicNameAppender();
		String columnName = "Unique Hits".toUpperCase();

		String[] tabList = { Input.mySavedSearch, Input.shareSearchPA, Input.shareSearchDefaultSG };

		baseClass.stepInfo("Test case Id:RPMXCON-56589 Reports/Search Term");
		baseClass.stepInfo(
				"Search Term Report - Verify Scheduling/Sharing/Expoting/Saving STR report with Unique Hits / Unique Family Hits column");

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

		// Perform Saving STR report
		searchterm.ValidateSearchTermreportSaveandImpact(columnName, false);

		// Perform Export STR Report
		driver.waitForPageToBeReady();
		baseClass.waitForElement(searchterm.getExportIcon());
		searchterm.getExportIcon().waitAndClick(10);
		baseClass.VerifySuccessMessage("Report save successfully");

		// perform Sharing STR Report
		driver.waitForPageToBeReady();
		searchterm.performSharingAction(Input.rmu1FullName, Input.rmu1userName);
		driver.waitForPageToBeReady();

		// perform Schedule STR Report
		reports.navigateToReportsPage("");
		driver.waitForPageToBeReady();
		searchterm.GenerateReportWithAnySearchOrTab(tabList, true);
		searchterm.selectColumnFromSTRPage(columnName);
		searchterm.performScheduleAction(Input.rmu1FullName, Input.rmu1userName);

		// delete search
		savedSearch.deleteSearch(saveSearch1, Input.mySavedSearch, Input.yesButton);
		savedSearch.deleteSearch(saveSearch2, Input.shareSearchPA, Input.yesButton);
		savedSearch.deleteSearch(saveSearch3, Input.shareSearchDefaultSG, Input.yesButton);

		// logout
		loginPage.logout();
	}

	/**
	 * @author NA
	 * @Description : To verify that on selecting Delete button the saved custom
	 *              report is removed [RPMXCON-56356]
	 */
	@Test(description = "RPMXCON-56356", enabled = true, groups = { "regression" })
	public void verifyDeleteFrmSavedCustomReport() throws Exception {
		ReportsPage report = new ReportsPage(driver);
		CustomDocumentDataReport cddr = new CustomDocumentDataReport(driver);
		SoftAssert asserts = new SoftAssert();

		String tagName = "Tag" + Utility.dynamicNameAppender();
		String[] metaDataFields = { "CustodianName" };
		String[] workProductFields = { tagName };
		String reportName = "Report" + Utility.dynamicNameAppender();

		baseClass.stepInfo("RPMXCON - 56356");
		baseClass.stepInfo("To verify that on selecting Delete button the saved custom report is removed.");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged In As : " + Input.pa1userName);

		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTag(tagName);

		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		cddr.selectSource("Security Groups", Input.securityGroup);
		cddr.selectExportFieldFormat("039");
		cddr.selectExportTextFormat("039");
		cddr.selectMetaDataFields(metaDataFields);
		cddr.selectWorkProductFields(workProductFields);
		baseClass.waitForElement(cddr.getRunReport());
		cddr.getRunReport().waitAndClick(3);
		cddr.reportRunSuccessMsg();
		cddr.SaveReport(reportName);
		baseClass.stepInfo("Saved the Custom report " + reportName);

		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(report.getdeleteToolTip_CustomReport(reportName));
		asserts.assertTrue(report.getdeleteToolTip_CustomReport(reportName).isElementAvailable(5));
		asserts.assertAll();
		baseClass.stepInfo("Saved Report Displaying As Expected");
		report.deleteCustomReport(reportName);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		if (report.getdeleteToolTip_CustomReport(reportName).isElementAvailable(5)) {
			baseClass.failedStep("After Deleted the Report, Saved Custom Report Not Removed...");
		} else {
			baseClass.passedStep("After Delete the Report, Saved Custom Report Removed As Expected");
		}
		baseClass.passedStep("Verified - that on selecting Delete button the saved custom report is removed.");
		loginPage.logout();
	}

	/**
	 * @author NA
	 * @Description : To verify that Source selection is mandatory for Custom Report
	 *              [RPMXCON-56394]
	 */
	@Test(description = "RPMXCON-56394", enabled = true, dataProvider = "PA & RMU", groups = { "regression" })
	public void verifySourceMandatoryForCustomRep(String username, String password) throws Exception {
		CustomDocumentDataReport cddr = new CustomDocumentDataReport(driver);

		String tagName = "Tag" + Utility.dynamicNameAppender();
		String[] metaDataFields = { "CustodianName" };
		String[] workProductFields = { tagName };
		String expErrorMsg = "Please select source";

		baseClass.stepInfo("RPMXCON-56394");
		baseClass.stepInfo("To verify that Source selection is mandatory for Custom Report");
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Logged in As : " + username);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTag(tagName);

		cddr.navigateToCDDReportPage();
		driver.waitForPageToBeReady();
		cddr.selectExportStyle("CSV");
		cddr.selectExportFieldFormat("039");
		cddr.selectExportTextFormat("039");
		cddr.selectExportNewLineFormat("039");
		cddr.selectExportDateStyleFormat("YYYY/MM/DD HH:MI:SS");
		cddr.selectMetaDataFields(metaDataFields);
		cddr.selectWorkProductFields(workProductFields);
		driver.waitForPageToBeReady();
		cddr.validateSelectedExports(workProductFields);
		cddr.validateSelectedExports(metaDataFields);
		baseClass.waitForElement(cddr.getRunReport());
		cddr.getRunReport().waitAndClick(3);
		baseClass.VerifyErrorMessage(expErrorMsg);
		baseClass.passedStep("Verified -  that Source selection is mandatory for Custom Report");
		loginPage.logout();
	}

	// QA
	/**
	 * @author NA Testcase No:RPMXCON-56549
	 * @Description:To verify that export report should be work correctly if comment
	 *                 is upto 2MB
	 **/
	@Test(description = "RPMXCON-56549", enabled = true, groups = { "regression" })
	public void verifyExportCorrectlyWith2MBCmtsdocs() throws Exception {
		CustomDocumentDataReport cddr = new CustomDocumentDataReport(driver);

		String dataSet = "2MBCommentData";
		String[] metaDataFields = { "CustodianName" };
		String[] workProductFields = { dataSet };
		String expValue = "CustodianName'''All Folders\\Datasets\\" + dataSet + "''";

		baseClass.stepInfo("RPMXCON-56549");
		baseClass.stepInfo("To verify that export report should be work correctly if comment is upto 2MB");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in As : " + Input.pa1FullName);
		baseClass.selectproject(Input.additionalDataProject);

		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		cddr.selectSource("Security Groups", Input.securityGroup);
		cddr.selectExportFieldFormat("039");
		cddr.selectExportTextFormat("039");
		cddr.selectMetaDataFields(metaDataFields);
		cddr.selectWorkProductFields(workProductFields);
		String fileName = cddr.runReportandVerifyFileDownloaded();
		String actualValue = cddr.csvfileVerification("", fileName);
		baseClass.stepInfo(actualValue);
		System.out.println(actualValue);
		if (actualValue.contains(expValue)) {
			baseClass
					.passedStep("Export file open without any error/warning message. All data displaying As Expected.");
		} else {
			baseClass.failedStep(
					"Export file open without any error/warning message but All data displaying Not As Expected.");
		}
		baseClass.passedStep("Verified - that export report should be work correctly if comment is upto 2MB");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-56302
	 * @Description:To verify that User is able to select multiple options in
	 *                 Source.
	 **/
	@Test(description = "RPMXCON-56302", dataProvider = "PA & RMU", enabled = true, groups = { "regression" })
	public void verifyMultipleOptnsinSource(String username, String password) throws Exception {
		TagsAndFoldersPage tagFolder = new TagsAndFoldersPage(driver);
		TimelineReportPage timeLine = new TimelineReportPage(driver);

		String tagName1 = "Tag" + Utility.dynamicNameAppender();
		String tagName2 = "Tag" + Utility.dynamicNameAppender();
		String[] tags = { tagName1, tagName2 };

		baseClass.stepInfo("RPMXCON - 56302");
		baseClass.stepInfo("To verify that User is able to select multiple options in Source.");
		loginPage.loginToSightLine(username, password);
		tagFolder.navigateToTagsAndFolderPage();
		driver.waitForPageToBeReady();
		if (username.equals(Input.rmu1userName)) {
			tagFolder.createNewTagwithClassificationInRMU(tagName1, Input.tagNamePrev);
			tagFolder.createNewTagwithClassificationInRMU(tagName2, Input.tagNamePrev);
		} else {
			tagFolder.createNewTagwithClassification(tagName1, Input.tagNamePrev);
			tagFolder.createNewTagwithClassification(tagName2, Input.tagNamePrev);
		}

		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		timeLine.navigateToTimelineAndGapsReport();
		driver.waitForPageToBeReady();
		timeLine.selectTagsinSource(tags);
		timeLine.verifySelctedOptnsInSourceCriteria(tags);

		baseClass.passedStep("Verified -  that User is able to select multiple options in Source.");
		loginPage.logout();

	}

	/**
	 * @author NA Testcase No:RPMXCON-56472
	 * @Description:To verify that when Admin impersonated as RMU and created
	 *                 report. " + "Then that Custom Report must not be visible to
	 *                 Project Admin in Admin role.
	 **/
	@Test(description = "RPMXCON-56472", enabled = true, groups = { "regression" })
	public void verifyCretedReportAsImp() throws Exception {
		CustomDocumentDataReport cddr = new CustomDocumentDataReport(driver);
		SoftAssert asserts = new SoftAssert();

		String tagName = "Tag" + Utility.dynamicNameAppender();
		String[] metaDataFields = { "CustodianName" };
		String[] workProductFields = { tagName };
		String reportName = "Report" + Utility.dynamicNameAppender();

		baseClass.stepInfo("RPMXCON-56472");
		baseClass.stepInfo("To verify that when Admin impersonated as RMU and created report. "
				+ "Then that Custom Report must not be visible to Project Admin in Admin role.");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.impersonatePAtoRMU();

		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTag(tagName);

		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		cddr.selectSource("Security Groups", Input.securityGroup);
		cddr.selectExportFieldFormat("039");
		cddr.selectExportTextFormat("039");
		cddr.selectMetaDataFields(metaDataFields);
		cddr.selectWorkProductFields(workProductFields);
		baseClass.waitForElement(cddr.getRunReport());
		cddr.getRunReport().waitAndClick(5);
		cddr.reportRunSuccessMsg();

		cddr.SaveReport(reportName);
		baseClass.stepInfo("Saved the Custom report " + reportName);

		ReportsPage report = new ReportsPage(driver);
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(report.getdeleteToolTip_CustomReport(reportName));
		asserts.assertTrue(report.getdeleteToolTip_CustomReport(reportName).isElementAvailable(5));
		asserts.assertAll();
		baseClass.stepInfo("On Report Landing page the custom report is displaying As Expected..");

		baseClass.rolesToImp("RMU", "PA");

		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();

		asserts.assertFalse(report.getdeleteToolTip_CustomReport(reportName).isElementAvailable(5));
		asserts.assertAll();
		baseClass.stepInfo("On Report Landing page the custom report is Not displaying As Expected..");

		baseClass.passedStep("Verified - that when Admin impersonated as RMU and created report."
				+ " Then that Custom Report must not be visible to Project Admin in Admin role.");
		loginPage.logout();

	}

	@DataProvider(name = "PA & RMU")
	public Object[][] PA_RMU() {
		Object[][] users = { { Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password } };
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
		System.out.println("**Executed Communication explorer sprint 21**");
	}
}
