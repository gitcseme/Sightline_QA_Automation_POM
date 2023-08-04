package sightline.bulkActions;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.BatchPrintPage;
import pageFactory.CodingForm;
import pageFactory.CommunicationExplorerPage;
import pageFactory.ConceptExplorerPage;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SearchTermReportPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.TallyPage;
import pageFactory.TimelineReportPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BulkActions_Phase2_Regression {

	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	AssignmentsPage assignment;
	BaseClass baseClass;
	Input in;
	SoftAssert softAssert;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		in = new Input();
		in.loadEnvConfig();
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		assignment = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		sessionSearch = new SessionSearch(driver);
		savedSearch=new SavedSearch(driver);
		softAssert = new SoftAssert();

	}

	/**
	 * @throws Exception
	 * @Author :Indium-Baskar
	 * @Description : Verify the "Bulk Folder" functionality in selected results as
	 *              a Review Manager login
	 */
	@Test(description = "RPMXCON-48682", enabled = true, groups = { "regression" })
	public void verifyRmuCanBulkAssignMultipleDrag() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-48682");
		baseClass.stepInfo(
				"Verify the \"Bulk Folder\" functionality in selected " + "results as a Review Manager login");
		String folder = "folder" + Utility.dynamicNameAppender();
		SoftAssert softAssertion = new SoftAssert();
		sessionSearch = new SessionSearch(driver);
		TagsAndFoldersPage tagsAndFoldersPage = new TagsAndFoldersPage(driver);
		DocViewPage docViewPage = new DocViewPage(driver);

		// Login as rmu
		loginPage.loginToSightLine(Input.rmu3userName, Input.rmu3password);
		UtilityLog.info("Logged in as User: " + Input.rmu3userName);

		// session search to bulk folder
		int pureHit = sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkFolder(folder);
		baseClass.stepInfo("Making folder  for all purehit document");

		// validating the folder(select folder to docview page)
		tagsAndFoldersPage.selectFolderViewInDocView(folder);
		baseClass.stepInfo("Selecting folder and navigating to docview page");

		// verifying the doc count
		int docviewCount = docViewPage.verifyingDocCount();
		softAssertion.assertEquals(pureHit, docviewCount);
		softAssertion.assertAll();
		baseClass.passedStep("Assigned document as folder are displayed in docview page");
		baseClass.stepInfo("Rmu user can able to folder the documents");
		// logout
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-54476
	 * @Description:To Verify - the fluctuation of document count for all the bulk
	 *                 actions in Saved Search
	 **/
	@Test(description = "RPMXCON-54476", enabled = true, groups = { "regression" })
	public void VerifyTotalDocsFluctBulkActionSS() throws Exception {
		SavedSearch search = new SavedSearch(driver);

		String search1 = "Search" + Utility.dynamicNameAppender();
		String search2 = "Search" + Utility.dynamicNameAppender();
		String folderTag = "folderTag" + Utility.dynamicNameAppender();

		baseClass.stepInfo("RPMXCON - 54476");
		baseClass.stepInfo("To Verify - the fluctuation of document count for all the bulk actions in Saved Search");
		loginPage.loginToSightLine(Input.pa3userName, Input.pa3password);
		baseClass.stepInfo("Logged in As : " + Input.pa3FullName);

		sessionSearch.navigateToSessionSearchPageURL();
		String count = String.valueOf(sessionSearch.basicContentSearch(Input.searchString1));
		sessionSearch.saveSearch(search1);

		search.navigateToSavedSearchPage();
		search.savedSearch_Searchandclick(search1);
		baseClass.waitForElement(search.getSavedSearchToBulkTag());
		search.getSavedSearchToBulkTag().waitAndClick(5);
		sessionSearch.bulkTag_FluctuationVerification(folderTag, count);

		search.navigateToSavedSearchPage();
		search.savedSearch_Searchandclick(search1);
		baseClass.waitForElement(search.getSavedSearchToBulkFolder());
		search.getSavedSearchToBulkFolder().waitAndClick(5);
		sessionSearch.bulkFolder_FluctuationVerification(folderTag, count);

		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu3userName, Input.rmu3password);
		baseClass.stepInfo("Logged in As : " + Input.rmu3FullName);

		sessionSearch.navigateToSessionSearchPageURL();
		String count1 = String.valueOf(sessionSearch.basicContentSearch(Input.searchString1));
		sessionSearch.saveSearch(search2);

		search.navigateToSavedSearchPage();
		search.savedSearch_Searchandclick(search2);
		baseClass.waitForElement(search.getSavedSearchToBulkAssign());
		search.getSavedSearchToBulkAssign().waitAndClick(5);
		sessionSearch.verifyDocsFluctuation_BulkAssign(count1);
		baseClass.passedStep("Verified-  the fluctuation of document count for all the bulk actions in Saved Search");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-54477
	 * @Description:To Verify the fluctuation of document count for all the bulk
	 *                 actions in DocList
	 **/
	@Test(description = "RPMXCON-54477", enabled = true, groups = { "regression" })
	public void VerifyTotalDocsFluctBulkActionDL() throws Exception {
		DocListPage docList = new DocListPage(driver);

		String folderTag = "folderTag" + Utility.dynamicNameAppender();
		String count = "3";

		baseClass.stepInfo("RPMXCON-54477");
		baseClass.stepInfo("To Verify the fluctuation of document count for all the bulk actions in DocList");
		loginPage.loginToSightLine(Input.pa3userName, Input.pa3password);
		baseClass.stepInfo("Logged in As : " + Input.pa3FullName);

		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocList();

		docList.documentSelection(Integer.parseInt(count));
		baseClass.waitForElement(docList.getBulkActionButton());
		docList.getBulkActionButton().waitAndClick(5);
		baseClass.waitForElement(docList.getBulkTagAction());
		docList.getBulkTagAction().waitAndClick(5);
		sessionSearch.bulkTag_FluctuationVerification(folderTag, count);

		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		docList.documentSelection(Integer.parseInt(count));
		baseClass.waitForElement(docList.getBulkActionButton());
		docList.getBulkActionButton().waitAndClick(5);
		baseClass.waitForElement(docList.getBulkFolderAction());
		docList.getBulkFolderAction().waitAndClick(5);
		sessionSearch.bulkFolder_FluctuationVerification(folderTag, count);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu3userName, Input.rmu3password);
		baseClass.stepInfo("Logged in As : " + Input.rmu3FullName);

		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocList();

		docList.documentSelection(Integer.parseInt(count));
		baseClass.waitForElement(docList.getBulkActionButton());
		docList.getBulkActionButton().waitAndClick(5);
		baseClass.waitForElement(docList.getDocList_action_BulkAssignButton());
		docList.getDocList_action_BulkAssignButton().waitAndClick(5);
		sessionSearch.verifyDocsFluctuation_BulkAssign(count);
		baseClass.passedStep("Verified - the fluctuation of document count for all the bulk actions in DocList");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-54478
	 * @Description:To Verify the fluctuation of document count for all the bulk
	 *                 actions in DocView
	 **/
	@Test(description = "RPMXCON-54478", enabled = true, groups = { "regression" })
	public void VerifyTotalDocsFluctBulkActionDV() throws Exception {
		DocViewPage docView = new DocViewPage(driver);

		String folder = "Folder" + Utility.dynamicNameAppender();
		String count = "3";

		baseClass.stepInfo("RPMXCON-54478");
		baseClass.stepInfo("To Verify the fluctuation of document count for all the bulk actions in DocView");
		loginPage.loginToSightLine(Input.pa3userName, Input.pa3password);
		baseClass.stepInfo("Logged in As : " + Input.pa3FullName);

		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.ViewInDocView();

		docView.documentSelection(Integer.parseInt(count));

		baseClass.waitForElement(docView.getDocView_Mini_ActionButton());
		docView.getDocView_Mini_ActionButton().waitAndClick(5);
		baseClass.waitForElement(docView.getDocView_Mini_FolderAction());
		docView.getDocView_Mini_FolderAction().waitAndClick(5);
		sessionSearch.bulkFolder_FluctuationVerification(folder, count);

		baseClass.passedStep("Verified - the fluctuation of document count for all the bulk actions in DocView");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-54482
	 * @Description:To Verify the fluctuation of document count for all the bulk
	 *                 actions in Search Term Report
	 **/
	@Test(description = "RPMXCON-54482", enabled = true, groups = { "regression" })
	public void VerifyTotalDocsFluctBulkActionST() throws Exception {
		SearchTermReportPage srcTermReport = new SearchTermReportPage(driver);

		String folderTag = "folderTag" + Utility.dynamicNameAppender();
		String saveSearchNamePA = "ST" + Utility.dynamicNameAppender();
		String saveSearchNameRMU = "ST" + Utility.dynamicNameAppender();

		baseClass.stepInfo("RPMXCON-54482");
		baseClass
				.stepInfo("To Verify the fluctuation of document count for all the bulk actions in Search Term Report");
		loginPage.loginToSightLine(Input.pa3userName, Input.pa3password);
		baseClass.stepInfo("Logged in As : " + Input.pa3FullName);

		sessionSearch.navigateToSessionSearchPageURL();
		String count = String.valueOf(sessionSearch.basicContentSearch(Input.searchString1));
		sessionSearch.saveSearch(saveSearchNamePA);

		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		srcTermReport.GenerateReport(saveSearchNamePA);
		baseClass.waitForElement(srcTermReport.getActionButton());
		srcTermReport.getActionButton().waitAndClick(20);
		baseClass.waitForElement(srcTermReport.getActionBulkTag());
		srcTermReport.getActionBulkTag().waitAndClick(20);
		sessionSearch.bulkTag_FluctuationVerification(folderTag, count);

		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		srcTermReport.GenerateReport(saveSearchNamePA);
		baseClass.waitForElement(srcTermReport.getActionButton());
		srcTermReport.getActionButton().waitAndClick(20);
		baseClass.waitForElement(srcTermReport.getActionBulkFolder());
		srcTermReport.getActionBulkFolder().waitAndClick(20);
		sessionSearch.bulkFolder_FluctuationVerification(folderTag, count);

		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu3userName, Input.rmu3password);
		baseClass.stepInfo("Logged in As : " + Input.rmu3FullName);

		sessionSearch.navigateToSessionSearchPageURL();
		String countRMU = String.valueOf(sessionSearch.basicContentSearch(Input.searchString1));
		sessionSearch.saveSearch(saveSearchNameRMU);

		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		srcTermReport.GenerateReport(saveSearchNameRMU);
		srcTermReport.BulkAssign();
		sessionSearch.verifyDocsFluctuation_BulkAssign(countRMU);
		baseClass.passedStep(
				"Verifyied - the fluctuation of document count for all the bulk actions in Search Term Report");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-54480
	 * @Description:To Verify the fluctuation of document count for all the bulk
	 *                 actions in Communication Explorer report
	 **/
	@Test(description = "RPMXCON-54480", enabled = true, groups = { "regression" })
	public void VerifyTotalDocsFluctBulkActionCommExpRepPage() throws Exception {
		CommunicationExplorerPage commExpl = new CommunicationExplorerPage(driver);

		String folderTag = "folderTag" + Utility.dynamicNameAppender();

		baseClass.stepInfo("RPMXCON-54480");
		baseClass.stepInfo(
				"To Verify the fluctuation of document count for all the bulk actions in Communication Explorer report");
		loginPage.loginToSightLine(Input.pa3userName, Input.pa3password);
		baseClass.stepInfo("Logged in As : " + Input.pa3FullName);

		commExpl.navigateToCommunicationExpPage();
		commExpl.generateReportusingDefaultSG();
		commExpl.clickReport();
		String count1 = commExpl.selectedDocsCount();
		commExpl.clickActionBtn(true, false, false);
		sessionSearch.bulkTag_FluctuationVerification(folderTag, count1);

		commExpl.navigateToCommunicationExpPage();
		commExpl.generateReportusingDefaultSG();
		commExpl.clickReport();
		String count2 = commExpl.selectedDocsCount();
		commExpl.clickActionBtn(false, true, false);
		sessionSearch.bulkFolder_FluctuationVerification(folderTag, count2);

		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu3userName, Input.rmu3password);
		baseClass.stepInfo("Logged in As : " + Input.rmu3FullName);

		commExpl.navigateToCommunicationExpPage();
		commExpl.generateReportusingDefaultSG();
		commExpl.clickReport();
		String count3 = commExpl.selectedDocsCount();
		commExpl.clickActionBtn(false, false, true);
		sessionSearch.verifyDocsFluctuation_BulkAssign(count3);
		baseClass.passedStep(
				"Verified -  the fluctuation of document count for all the bulk actions in Communication Explorer report");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-54479
	 * @Description:To Verify the fluctuation of document count for all the bulk
	 *                 actions in Concept Explorer Report
	 **/
	@Test(description = "RPMXCON-54479", enabled = true, groups = { "regression" })
	public void VerifyTotalDocsFluctBulkActionConceptExplPage() throws Exception {
		ConceptExplorerPage conExp = new ConceptExplorerPage(driver);

		String sourceToSelect = "Security Groups";
		String folderTag = "folderTag" + Utility.dynamicNameAppender();

		baseClass.stepInfo("RPMXCON-54479");
		baseClass.stepInfo(
				"To Verify the fluctuation of document count for all the bulk actions in Concept Explorer Report");
		loginPage.loginToSightLine(Input.pa3userName, Input.pa3password);
		baseClass.stepInfo("Logged in As : " + Input.pa3FullName);

		conExp.navigateToConceptExplorerPage();
		conExp.clickSelectSources();
		conExp.selectSGsource(sourceToSelect, Input.securityGroup);
		conExp.applyFilter("Yes", 10);
		driver.waitForPageToBeReady();
		String count1 = conExp.addMultipleTilesToCart(3);
		conExp.performActions("Bulk Tag");
		sessionSearch.bulkTag_FluctuationVerification(folderTag, count1);

		conExp.navigateToConceptExplorerPage();
		conExp.clickSelectSources();
		conExp.selectSGsource(sourceToSelect, Input.securityGroup);
		conExp.applyFilter("Yes", 10);
		driver.waitForPageToBeReady();
		String count2 = conExp.addMultipleTilesToCart(3);
		conExp.performActions("Bulk Folder");
		sessionSearch.bulkFolder_FluctuationVerification(folderTag, count2);

		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu3userName, Input.rmu3password);
		baseClass.stepInfo("Logged in As : " + Input.rmu3FullName);

		conExp.navigateToConceptExplorerPage();
		conExp.clickSelectSources();
		conExp.selectSGsource(sourceToSelect, Input.securityGroup);
		conExp.applyFilter("Yes", 10);
		driver.waitForPageToBeReady();
		String count3 = conExp.addMultipleTilesToCart(3);
		conExp.performActions("Bulk Assign");
		sessionSearch.verifyDocsFluctuation_BulkAssign(count3);
		baseClass.passedStep(
				"Verified - the fluctuation of document count for all the bulk actions in Concept Explorer Report");
		loginPage.logout();

	}

	/**
	 * @author NA Testcase No:RPMXCON-54483
	 * @Description:To Verify the fluctuation of document count for all the bulk
	 *                 actions in Timeline and Gaps report
	 **/
	@Test(description = "RPMXCON-54483", enabled = true, groups = { "regression" })
	public void VerifyTotalDocsFluctBulkActTimeGapsReport() throws Exception {
		TimelineReportPage timeGaps = new TimelineReportPage(driver);

		String timeLine = "MasterDate";
		String fromDate = "2019/01/01";
		String toDate = timeGaps.getCurrentDate();
		String folderTag = "folderTag" + Utility.dynamicNameAppender();
		

		baseClass.stepInfo("RPMXCON-54483");
		baseClass.stepInfo(
				"To Verify the fluctuation of document count for all the bulk actions in Timeline and Gaps report");
		loginPage.loginToSightLine(Input.pa3userName, Input.pa3password);
		baseClass.stepInfo("Logged in As : " + Input.pa3FullName);

		timeGaps.fillingDetailsinTimeGaps(timeLine, fromDate, toDate);
		String count1 = timeGaps.selectBarChartandRtnDocCount("yearly");
		timeGaps.selectActions("Tag", "yearlyActions");
		sessionSearch.bulkTag_FluctuationVerification(folderTag+"_1", count1);

		timeGaps.fillingDetailsinTimeGaps(timeLine, fromDate, toDate);
		String count2 = timeGaps.selectBarChartandRtnDocCount("yearly");
		timeGaps.selectActions(" Folder", "yearlyActions");
		sessionSearch.bulkFolder_FluctuationVerification(folderTag+"_2", count2);

		timeGaps.fillingDetailsinTimeGaps(timeLine, fromDate, toDate);
		timeGaps.selectBarChartandRtnDocCount("yearly");
		timeGaps.selectActions("Monthly Timeline", "yearlyActions");
		driver.waitForPageToBeReady();
		String count3 = timeGaps.selectBarChartandRtnDocCount("monthly");
		timeGaps.selectActions("Tag", "monthlyActions");
		timeGaps.fillingDetailsinTimeGaps(timeLine, fromDate, toDate);
		timeGaps.selectBarChartandRtnDocCount("yearly");
		timeGaps.selectActions("Monthly Timeline", "yearlyActions");
		driver.waitForPageToBeReady();
		String count4 = timeGaps.selectBarChartandRtnDocCount("monthly");
		timeGaps.selectActions(" Folder", "monthlyActions");
		sessionSearch.bulkFolder_FluctuationVerification(folderTag+"_3", count4);

		timeGaps.fillingDetailsinTimeGaps(timeLine, fromDate, toDate);
		timeGaps.selectBarChartandRtnDocCount("yearly");
		timeGaps.selectActions("Monthly Timeline", "yearlyActions");
		driver.waitForPageToBeReady();
		timeGaps.selectBarChartandRtnDocCount("monthly");
		timeGaps.selectActions(" Daily Timeline", "monthlyActions");
		driver.waitForPageToBeReady();
		String count5 = timeGaps.selectBarChartandRtnDocCount("daily");
		timeGaps.selectActions("Tag", "dailyActions");
		sessionSearch.bulkTag_FluctuationVerification(folderTag+"_4", count5);

		timeGaps.fillingDetailsinTimeGaps(timeLine, fromDate, toDate);
		timeGaps.selectBarChartandRtnDocCount("yearly");
		timeGaps.selectActions("Monthly Timeline", "yearlyActions");
		driver.waitForPageToBeReady();
		timeGaps.selectBarChartandRtnDocCount("monthly");
		timeGaps.selectActions(" Daily Timeline", "monthlyActions");
		driver.waitForPageToBeReady();
		String count6 = timeGaps.selectBarChartandRtnDocCount("daily");
		timeGaps.selectActions(" Folder", "dailyActions");
		sessionSearch.bulkFolder_FluctuationVerification(folderTag+"_5", count6);

		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu3userName, Input.rmu3password);
		baseClass.stepInfo("Logged in As : " + Input.rmu3FullName);

		timeGaps.fillingDetailsinTimeGaps(timeLine, fromDate, toDate);
		String count7 = timeGaps.selectBarChartandRtnDocCount("yearly");
		timeGaps.selectActions("Assign", "yearlyActions");
		sessionSearch.verifyDocsFluctuation_BulkAssign(count7);

		timeGaps.fillingDetailsinTimeGaps(timeLine, fromDate, toDate);
		timeGaps.selectBarChartandRtnDocCount("yearly");
		timeGaps.selectActions("Monthly Timeline", "yearlyActions");
		driver.waitForPageToBeReady();
		String count8 = timeGaps.selectBarChartandRtnDocCount("monthly");
		timeGaps.selectActions("Assign", "monthlyActions");
		sessionSearch.verifyDocsFluctuation_BulkAssign(count8);

		timeGaps.fillingDetailsinTimeGaps(timeLine, fromDate, toDate);
		timeGaps.selectBarChartandRtnDocCount("yearly");
		timeGaps.selectActions("Monthly Timeline", "yearlyActions");
		driver.waitForPageToBeReady();
		timeGaps.selectBarChartandRtnDocCount("monthly");
		timeGaps.selectActions(" Daily Timeline", "monthlyActions");
		driver.waitForPageToBeReady();
		String count9 = timeGaps.selectBarChartandRtnDocCount("daily");
		timeGaps.selectActions("Assign", "dailyActions");
		sessionSearch.verifyDocsFluctuation_BulkAssign(count9);

		baseClass.passedStep(
				"Verified - the fluctuation of document count for all the bulk actions in Timeline and Gaps report");
		loginPage.logout();
	}

	@Test(description = "RPMXCON-54481", enabled = true, groups = { "regression" })
	public void VerifyTotalDocsFluctBulkActTallyReport() throws Exception {
		TallyPage tally = new TallyPage(driver);

		String tag = "Tag" + Utility.dynamicNameAppender();
		String tagRMU = "Tag" + Utility.dynamicNameAppender();
		String tagFolder = "TagFolder" + Utility.dynamicNameAppender();
		String tagFolder2 = "TagFolder2" + Utility.dynamicNameAppender();
		String tagFolder1 = "TagFolder1" + Utility.dynamicNameAppender();

		baseClass.stepInfo("To Verify the fluctuation of document count for all the bulk actions in Tally report");
		baseClass.stepInfo("RPMXCON-54481");
		loginPage.loginToSightLine(Input.pa3userName, Input.pa3password);
		baseClass.stepInfo("Logged in As  : " + Input.pa3FullName);
		String count = String.valueOf(sessionSearch.basicContentSearch(Input.TallySearch));
		sessionSearch.bulkTag(tag);

		tally.navigateTo_Tallypage();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		tally.SelectSource_SecurityGroup(Input.securityGroup);
		tally.selectTallyByTagName(tag);
		tally.tallyActions();
		baseClass.waitForElement(tally.getBulkTagAction(1));
		tally.getBulkTagAction(1).waitAndClick(5);
		sessionSearch.bulkTag_FluctuationVerification(tagFolder, count);

		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		tally.SelectSource_SecurityGroup(Input.securityGroup);
		tally.selectTallyByTagName(tag);
		tally.tallyActions();
		baseClass.waitForElement(tally.getBulkFolderAction(1));
		tally.getBulkFolderAction(1).waitAndClick(5);
		sessionSearch.bulkFolder_FluctuationVerification(tagFolder, count);

		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		tally.SelectSource_SecurityGroup(Input.securityGroup);
		tally.selectTallyByTagName(tag);
		tally.tallyActions();
		tally.selectSubTallyFromActionDropDown();
		tally.selectMetaData_SubTally("CustodianName", 1);
		String count1 = String.valueOf(tally.getDocCountSubTally());
		tally.subTallyActions();
		baseClass.waitForElement(tally.getBulkTagAction(2));
		tally.getBulkTagAction(2).waitAndClick(5);
		sessionSearch.bulkTag_FluctuationVerification(tagFolder1, count1);

		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		tally.SelectSource_SecurityGroup(Input.securityGroup);
		tally.selectTallyByTagName(tag);
		tally.tallyActions();
		tally.selectSubTallyFromActionDropDown();
		tally.selectMetaData_SubTally("CustodianName", 1);
		String count2 = String.valueOf(tally.getDocCountSubTally());
		tally.subTallyActions();
		baseClass.waitForElement(tally.getBulkFolderAction(2));
		tally.getBulkFolderAction(2).waitAndClick(5);
		sessionSearch.bulkFolder_FluctuationVerification(tagFolder2, count2);

		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu3userName, Input.rmu3password);
		baseClass.stepInfo("Logged in As : " + Input.pa3FullName);
		String countRMU = String.valueOf(sessionSearch.basicContentSearch(Input.TallySearch));
		sessionSearch.bulkTag(tagRMU);

		tally.navigateTo_Tallypage();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		tally.SelectSource_SecurityGroup(Input.securityGroup);
		tally.selectTallyByTagName(tagRMU);
		tally.tallyActions();
		tally.bulkAssign(1);
		sessionSearch.verifyDocsFluctuation_BulkAssign(countRMU);

		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		tally.SelectSource_SecurityGroup(Input.securityGroup);
		tally.selectTallyByTagName(tagRMU);
		tally.tallyActions();
		tally.selectSubTallyFromActionDropDown();
		tally.selectMetaData_SubTally("CustodianName", 1);
		String count3 = String.valueOf(tally.getDocCountSubTally());
		tally.subTallyActions();
		tally.bulkAssign(2);
		sessionSearch.verifyDocsFluctuation_BulkAssign(count3);
		baseClass.passedStep("Verifyied - that fluctuation of document count for all the bulk actions in Tally report");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-65734
	 * @Description:verify Warning message to validate for conflicts during bulk
	 *                     tagging from Doc Explorer
	 **/
	@Test(description = "RPMXCON-65734", enabled = true, groups = { "regression" })
	public void verifyValidateConflictDE() throws Exception {
		TagsAndFoldersPage tags = new TagsAndFoldersPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		CodingForm coding = new CodingForm(driver);
		TallyPage tally = new TallyPage(driver);
		SoftAssert asserts = new SoftAssert();
		BatchPrintPage batchPrint = new BatchPrintPage(driver);
		DocExplorerPage docExp = new DocExplorerPage(driver);

		String tagname1 = "ATag1" + Utility.dynamicNameAppender();
		String tagname2 = "ATag1" + Utility.dynamicNameAppender();
		String folderName = "Folder" + Utility.dynamicNameAppender();

		String cfName = "Coding" + Utility.dynamicNameAppender();
		String expLongText = "Warning: Bulk tagging/untagging may violate coding logic and create coding conflicts";
		String expShortText = "Below is a Tally Report of all your selected documents to be bulk tagged/untagged, reported "
				+ "by the tags each document presently carries in the coding form(s). In this report you can see where coding "
				+ "conflicts may arise. Please confirm that no coding conflicts exist with the set of documents you are about to bulk tag/untag.";

		baseClass.stepInfo("RPMXCON - 65734");
		baseClass.stepInfo("verify Warning message to validate for conflicts during bulk tagging from Doc Explorer");
		loginPage.loginToSightLine(Input.rmu3userName, Input.rmu3password);
		tags.navigateToTagsAndFolderPage();
		driver.waitForPageToBeReady();
		tags.createNewTagwithClassificationInRMU(tagname1, Input.tagNamePrev);
		tags.createNewTagwithClassificationInRMU(tagname2, Input.tagNamePrev);
		tags.CreateFolderInRMU(folderName);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname1);
		driver.waitForPageToBeReady();
		sessionSearch.bulkTagExisting(tagname2);
		coding.navigateToCodingFormPage();
		driver.waitForPageToBeReady();
		coding.saveCodingForm2TagsWithGrpAssociat(cfName, tagname1, tagname2);
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa3userName, Input.pa3password);
		sessionSearch.navigateToSessionSearchPageURL();
		driver.waitForPageToBeReady();
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(folderName);

		docExp.navigateToDocExplorerURL();
		driver.waitForPageToBeReady();
		docExp.performIncludeFolderFilter(folderName);
		driver.waitForPageToBeReady();
		docExp.selectAllDocumentsFromCurrentPage();
		docExp.performBulkTagAction();
		sessionSearch.bulkTagNotComplete(tagname1);
		baseClass.waitForElement(sessionSearch.getPopupHeader());
		String actLongText = sessionSearch.getPopupHeader().getText();
		String actShortTex = sessionSearch.getPopupText().getText();
		asserts.assertEquals(expLongText, actLongText);
		asserts.assertEquals(expShortText, actShortTex);
		asserts.assertAll();

		docExp.navigateToDocExplorerURL();
		driver.waitForPageToBeReady();
		docExp.performIncludeFolderFilter(folderName);
		driver.waitForPageToBeReady();
		docExp.selectAllDocumentsFromCurrentPage();
		docExp.performBulkTagAction();
		sessionSearch.bulkTagNotComplete(tagname1);
		baseClass.waitForElement(sessionSearch.getReportMetadat());
		asserts.assertTrue(sessionSearch.getTallyContinue().Enabled());
		asserts.assertTrue(sessionSearch.getNoBtn().Enabled());
		asserts.assertAll();

		docExp.navigateToDocExplorerURL();
		driver.waitForPageToBeReady();
		docExp.performIncludeFolderFilter(folderName);
		driver.waitForPageToBeReady();
		docExp.selectAllDocumentsFromCurrentPage();
		docExp.performBulkTagAction();
		sessionSearch.bulkTagNotComplete(tagname1);
		baseClass.waitForElement(sessionSearch.getPopupHeader());
		if (sessionSearch.getNoBtn().Enabled()) {
			sessionSearch.getNoBtn().waitAndClick(5);
			baseClass.passedStep("Cancel Button Enabled And Successfully Clicked in POPUP");
		} else {
			baseClass.failedStep("Cancel ButtonDisabled");
		}
		driver.waitForPageToBeReady();
		asserts.assertTrue(docExp.getDocExp_SelectAllDocs().Visible());
		asserts.assertAll();

		docExp.navigateToDocExplorerURL();
		driver.waitForPageToBeReady();
		docExp.performIncludeFolderFilter(folderName);
		driver.waitForPageToBeReady();
		docExp.selectAllDocumentsFromCurrentPage();
		docExp.performBulkTagAction();
		sessionSearch.bulkTagNotComplete(tagname1);
		baseClass.waitForElement(sessionSearch.getPopupHeader());
		sessionSearch.getTallyContinue().waitAndClick(5);
		baseClass.verifyMegaPhoneIconAndBackgroundTasks(true, true);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(batchPrint.getBatchId(1));
		String idValue = batchPrint.getBatchId(1).getText();
		String status = sessionSearch.getRowData_BGT_Page("STATUS", idValue);
		asserts.assertNotNull(status);
		asserts.assertAll();

		docExp.navigateToDocExplorerURL();
		driver.waitForPageToBeReady();
		docExp.performIncludeFolderFilter(folderName);
		driver.waitForPageToBeReady();
		docExp.selectAllDocumentsFromCurrentPage();
		docExp.performBulkTagAction();
		sessionSearch.bulkTagNotComplete(tagname1);
		baseClass.waitForElement(sessionSearch.getPopupHeader());
		if (!sessionSearch.getReportTotalCount().isElementAvailable(1)) {
			asserts.assertFalse(sessionSearch.getGoToTallyReport().Enabled());
			asserts.assertAll();
			baseClass.passedStep("Button Disabled As Expected");
		} else {
			baseClass.failedStep("Not as Expected");
		}
		driver.waitForPageToBeReady();
		baseClass.waitForElement(sessionSearch.getReportTotalCount());
		String expTotalCount = sessionSearch.getReportTotalCount().getText();
		baseClass.waitForElement(sessionSearch.getReportMetadat());
		String expMetadata = sessionSearch.getReportMetadat().getText();
		baseClass.waitForElement(sessionSearch.getGoToTallyReport());
		sessionSearch.getGoToTallyReport().waitAndClick(5);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(tally.getTallyCount());
		String actTotalCount = tally.getTallyCount().getText();
		baseClass.waitForElement(sessionSearch.getReportMetadat());
		String actMetadata = sessionSearch.getReportMetadat().getText();
		asserts.assertEquals(expTotalCount, actTotalCount);
		asserts.assertEquals(expMetadata, actMetadata);
		asserts.assertAll();
		baseClass.passedStep("verify Warning message to validate for conflicts during bulk tagging from Doc Explorer");
		loginPage.logout();

	}

	/**
	 * @author NA Testcase No:RPMXCON-54490
	 * @Description:To verify that while the 'Total Selected Count' is In
	 *                 Progress,user can select Existing Assignment in the 'Bulk
	 *                 Assign' -UnAssign Documents section
	 **/
	@Test(description = "RPMXCON-54490", enabled = true, groups = { "regression" })
	public void verifySelectExisAssgnWhenCountINPROGRESS() throws Exception {
		SessionSearch session = new SessionSearch(driver);
		AssignmentsPage assign = new AssignmentsPage(driver);

		String assignMent = "Assign" + Utility.dynamicNameAppender();

		baseClass.stepInfo("RPMXCON-54490");
		baseClass.stepInfo("To verify that while the 'Total Selected Count' is In Progress, "
				+ "user can select Existing Assignment in the 'Bulk Assign' -UnAssign Documents section");
		loginPage.loginToSightLine(Input.rmu3userName, Input.rmu3password);
		baseClass.stepInfo("Logged In As : " + Input.rmu3FullName);
		assign.navigateToAssignmentsPage();
		driver.waitForPageToBeReady();
		assign.createAssignment(assignMent, Input.codeFormName);
		driver.waitForPageToBeReady();
		session.navigateToSessionSearchPageURL();
		driver.waitForPageToBeReady();
		session.basicContentSearch(Input.searchStringStar);
		driver.waitForPageToBeReady();
		session.bulkAssign();

		baseClass.waitForElement(session.getUnAssignRadioBtn());
		session.getUnAssignRadioBtn().Click();
		baseClass.waitForElement(session.getExistingAssignmentToUnAssign(assignMent));
		session.getExistingAssignmentToUnAssign(assignMent).Click();

		if (session.getCountOfDocsLoading().Visible()
				&& session.getExistingAssignmentToUnAssign(assignMent).Enabled()) {
			baseClass.passedStep("User Can Select Existing Assignment When Count Document INPROGRESS");
		} else {
			baseClass.failedStep("User Can't Select Existing Assignment When Count Document INPROGRESS");
		}
		baseClass.passedStep("To verify that while the 'Total Selected Count' is In Progress, "
				+ "user can select Existing Assignment in the 'Bulk Assign' -UnAssign Documents section");
		loginPage.logout();
	}

	/**
	 * @throws Exception
	 * @Author :Indium-Baskar
	 * @Description : Verify that As a RMU I will be able to assign document
	 */
	@Test(description = "RPMXCON-53555", enabled = true, groups = { "regression" })
	public void verifyRmuCanAssignDocs() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-53555");
		baseClass.stepInfo("Verify that As a RMU I will be able to assign document");
		String savedsSarch = "Assign" + Utility.dynamicNameAppender();
		String assignment = "Assignment" + Utility.dynamicNameAppender();
		SoftAssert softAssertion = new SoftAssert();
		AssignmentsPage assignmentPage = new AssignmentsPage(driver);
		sessionSearch = new SessionSearch(driver);
		savedSearch = new SavedSearch(driver);

		// Login as rmu
		loginPage.loginToSightLine(Input.rmu3userName, Input.rmu3password);
		UtilityLog.info("Logged in as User: " + Input.rmu3userName);

		// saving the search as per pre-requistes
		int pureHit = sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearch(savedsSarch);
		savedSearch.savedSearch_Searchandclick(savedsSarch);
		savedSearch.getSavedSearchToBulkAssign().waitAndClick(10);
		assignmentPage.assignmentCreationAsPerCf(assignment, Input.codeFormName);
		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		assignmentPage.assignmentNameValidation(assignment);

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
		System.out.println("**Executed  BulkActions_sprint23 .**");
	}
}
