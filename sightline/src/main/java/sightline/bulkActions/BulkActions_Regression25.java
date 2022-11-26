package sightline.bulkActions;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.CommunicationExplorerPage;
import pageFactory.ConceptExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SearchTermReportPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.TallyPage;
import pageFactory.TimelineReportPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BulkActions_Regression25 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	SessionSearch sessionSearch;
	SavedSearch saveSearch;
	TagsAndFoldersPage tagsAndFolderPage;
	Utility utility;
	SoftAssert softAssertion;
	String tagname;
	String foldername;

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
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());

		driver = new Driver();
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();
		baseClass = new BaseClass(driver);
		saveSearch = new SavedSearch(driver);
		sessionSearch = new SessionSearch(driver);
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
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
		try {
			// LoginPage.clearBrowserCache();

		} catch (Exception e) {
			System.out.println("Sessions already closed");
		}
	}	
	

	/**
	 * @author NA Testcase No:RPMXCON-54476
	 * @Description:To Verify - the fluctuation of document count for all the bulk actions in Saved Search
	 **/
	@Test(description = "RPMXCON-54476", enabled = true, groups = { "regression" })
	public void VerifyTotalDocsFluctBulkActionSS() throws Exception {
		SavedSearch search = new SavedSearch(driver);

		String search1 = "Search" + Utility.dynamicNameAppender();
		String search2 = "Search" + Utility.dynamicNameAppender();
		String folderTag = "folderTag" + Utility.dynamicNameAppender();
		
		baseClass.stepInfo("RPMXCON - 54476");
		baseClass.stepInfo("To Verify - the fluctuation of document count for all the bulk actions in Saved Search");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in As : " + Input.pa1FullName);
		
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
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in As : " + Input.rmu1FullName);
		
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
	 * @Description:To Verify the fluctuation of document count for all the bulk actions in DocList
	 **/
	@Test(description = "RPMXCON-54477", enabled = true, groups = { "regression" })
	public void VerifyTotalDocsFluctBulkActionDL() throws Exception {
	    DocListPage docList = new DocListPage(driver);
	    
	    String folderTag = "folderTag" + Utility.dynamicNameAppender();
	    String count = "3";
	    
	    baseClass.stepInfo("RPMXCON-54477");
	    baseClass.stepInfo("To Verify the fluctuation of document count for all the bulk actions in DocList");
	    loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
	    baseClass.stepInfo("Logged in As : " + Input.pa1FullName);
	    
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
	    
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	    baseClass.stepInfo("Logged in As : " + Input.rmu1FullName);
	    
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
	 * @Description:To Verify the fluctuation of document count for all the bulk actions in DocView
	 **/
	@Test(description = "RPMXCON-54478", enabled = true, groups = { "regression" })
	public void VerifyTotalDocsFluctBulkActionDV() throws Exception {
	    DocViewPage docView = new DocViewPage(driver);
	    
	    String folder = "Folder" + Utility.dynamicNameAppender();
	    String count = "3";
	    
	    baseClass.stepInfo("RPMXCON-54478");
	    baseClass.stepInfo("To Verify the fluctuation of document count for all the bulk actions in DocView");
	    loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
	    baseClass.stepInfo("Logged in As : " + Input.pa1FullName);
	    
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
	 * @Description:To Verify the fluctuation of document count for all the bulk actions in Search Term Report
	 **/
	@Test(description = "RPMXCON-54482", enabled = true, groups = { "regression" })
	public void VerifyTotalDocsFluctBulkActionST() throws Exception {
		SearchTermReportPage srcTermReport = new SearchTermReportPage(driver);
		
		 String folderTag = "folderTag" + Utility.dynamicNameAppender();
		 String saveSearchNamePA = "ST" + Utility.dynamicNameAppender();
		 String saveSearchNameRMU = "ST" + Utility.dynamicNameAppender();
		 
		 baseClass.stepInfo("RPMXCON-54482");
	     baseClass.stepInfo("To Verify the fluctuation of document count for all the bulk actions in Search Term Report");
	     loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
  	     baseClass.stepInfo("Logged in As : " + Input.pa1FullName);
		    
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
		 
		 loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
  	     baseClass.stepInfo("Logged in As : " + Input.rmu1FullName);
  	     
  	     sessionSearch.navigateToSessionSearchPageURL();
		 String countRMU = String.valueOf(sessionSearch.basicContentSearch(Input.searchString1));    
		 sessionSearch.saveSearch(saveSearchNameRMU);
		 
		 driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		 driver.waitForPageToBeReady();
		 srcTermReport.GenerateReport(saveSearchNameRMU);
		 srcTermReport.BulkAssign();
		 sessionSearch.verifyDocsFluctuation_BulkAssign(countRMU);
		 baseClass.passedStep("Verifyied - the fluctuation of document count for all the bulk actions in Search Term Report");
		 loginPage.logout();
	}
	
	/**
	 * @author NA Testcase No:RPMXCON-54480
	 * @Description:To Verify the fluctuation of document count for all the bulk actions in Communication Explorer report
	 **/
	@Test(description = "RPMXCON-54480", enabled = true, groups = { "regression" })
	public void VerifyTotalDocsFluctBulkActionCommExpRepPage() throws Exception {	
		CommunicationExplorerPage commExpl = new CommunicationExplorerPage(driver);
     
        String folderTag = "folderTag" + Utility.dynamicNameAppender();
        
        baseClass.stepInfo("RPMXCON-54480");
	    baseClass.stepInfo("To Verify the fluctuation of document count for all the bulk actions in Communication Explorer report");
        loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
        baseClass.stepInfo("Logged in As : " + Input.pa1FullName);
        
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
        
        loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
        baseClass.stepInfo("Logged in As : " + Input.rmu1FullName);
        
        commExpl.navigateToCommunicationExpPage();
        commExpl.generateReportusingDefaultSG();
        commExpl.clickReport();
        String count3 = commExpl.selectedDocsCount();       
        commExpl.clickActionBtn(false, false, true);
        sessionSearch.verifyDocsFluctuation_BulkAssign(count3);
        baseClass.passedStep("Verified -  the fluctuation of document count for all the bulk actions in Communication Explorer report");
        loginPage.logout();
	}
	
	/**
	 * @author NA Testcase No:RPMXCON-54479
	 * @Description:To Verify the fluctuation of document count for all the bulk actions in Concept Explorer Report
	 **/
	@Test(description = "RPMXCON-54479", enabled = true, groups = { "regression" })
	public void VerifyTotalDocsFluctBulkActionConceptExplPage() throws Exception {
		ConceptExplorerPage conExp = new ConceptExplorerPage(driver);
		
		String sourceToSelect = "Security Groups";
        String folderTag = "folderTag" + Utility.dynamicNameAppender();
        
        baseClass.stepInfo("RPMXCON-54479");
	    baseClass.stepInfo("To Verify the fluctuation of document count for all the bulk actions in Concept Explorer Report");
        loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
        baseClass.stepInfo("Logged in As : " + Input.pa1FullName);
        
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
	        
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	    baseClass.stepInfo("Logged in As : " + Input.rmu1FullName);
	        
	    conExp.navigateToConceptExplorerPage();
        conExp.clickSelectSources();
        conExp.selectSGsource(sourceToSelect, Input.securityGroup);
        conExp.applyFilter("Yes", 10);
		driver.waitForPageToBeReady();
		String count3 = conExp.addMultipleTilesToCart(3);
		conExp.performActions("Bulk Assign");
		sessionSearch.verifyDocsFluctuation_BulkAssign(count3);
		baseClass.passedStep("Verified - the fluctuation of document count for all the bulk actions in Concept Explorer Report");
	    loginPage.logout();
		
	}
	
	/**
	 * @author NA Testcase No:RPMXCON-54483
	 * @Description:To Verify the fluctuation of document count for all the bulk actions in Timeline and Gaps report
	 **/
	@Test(description = "RPMXCON-54483", enabled = true, groups = { "regression" })
	public void VerifyTotalDocsFluctBulkActTimeGapsReport() throws Exception {
		TimelineReportPage timeGaps = new TimelineReportPage(driver);
		
		String timeLine = "MasterDate";
		String fromDate =  "2019/01/01";
		String toDate = timeGaps.getCurrentDate();
		String folderTag = "folderTag" + Utility.dynamicNameAppender();
		
		baseClass.stepInfo("RPMXCON-54483");
	    baseClass.stepInfo("To Verify the fluctuation of document count for all the bulk actions in Timeline and Gaps report");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in As : " + Input.pa1FullName);
		
		timeGaps.fillingDetailsinTimeGaps(timeLine, fromDate, toDate);
		String count1 = timeGaps.selectBarChartandRtnDocCount("yearly");
		timeGaps.selectActions("Tag","yearlyActions");
		sessionSearch.bulkTag_FluctuationVerification(folderTag, count1);

		timeGaps.fillingDetailsinTimeGaps(timeLine, fromDate, toDate);
		String count2 = timeGaps.selectBarChartandRtnDocCount("yearly");
		timeGaps.selectActions(" Folder","yearlyActions");
		sessionSearch.bulkFolder_FluctuationVerification(folderTag, count2);
		
		timeGaps.fillingDetailsinTimeGaps(timeLine, fromDate, toDate);
		timeGaps.selectBarChartandRtnDocCount("yearly");
		timeGaps.selectActions("Monthly Timeline", "yearlyActions");
		driver.waitForPageToBeReady();
		String count3 = timeGaps.selectBarChartandRtnDocCount("monthly");
		timeGaps.selectActions("Tag","monthlyActions");
		sessionSearch.bulkTag_FluctuationVerification(folderTag, count3);
		
		timeGaps.fillingDetailsinTimeGaps(timeLine, fromDate, toDate);
		timeGaps.selectBarChartandRtnDocCount("yearly");
		timeGaps.selectActions("Monthly Timeline", "yearlyActions");
		driver.waitForPageToBeReady();
		String count4 = timeGaps.selectBarChartandRtnDocCount("monthly");
		timeGaps.selectActions(" Folder","monthlyActions");
		sessionSearch.bulkFolder_FluctuationVerification(folderTag, count4);
		
		timeGaps.fillingDetailsinTimeGaps(timeLine, fromDate, toDate);
		timeGaps.selectBarChartandRtnDocCount("yearly");
		timeGaps.selectActions("Monthly Timeline", "yearlyActions");
		driver.waitForPageToBeReady();
		timeGaps.selectBarChartandRtnDocCount("monthly");
		timeGaps.selectActions(" Daily Timeline","monthlyActions");
		driver.waitForPageToBeReady();
		String count5 = timeGaps.selectBarChartandRtnDocCount("daily");
		timeGaps.selectActions("Tag","dailyActions");
		sessionSearch.bulkTag_FluctuationVerification(folderTag, count5);
		
		timeGaps.fillingDetailsinTimeGaps(timeLine, fromDate, toDate);
		timeGaps.selectBarChartandRtnDocCount("yearly");
		timeGaps.selectActions("Monthly Timeline", "yearlyActions");
		driver.waitForPageToBeReady();
		timeGaps.selectBarChartandRtnDocCount("monthly");
		timeGaps.selectActions(" Daily Timeline","monthlyActions");
		driver.waitForPageToBeReady();
		String count6 = timeGaps.selectBarChartandRtnDocCount("daily");
		timeGaps.selectActions(" Folder","dailyActions");
		sessionSearch.bulkFolder_FluctuationVerification(folderTag, count6);
		
		loginPage.logout();
        
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	    baseClass.stepInfo("Logged in As : " + Input.rmu1FullName);
	    
	    timeGaps.fillingDetailsinTimeGaps(timeLine, fromDate, toDate);
		String count7 = timeGaps.selectBarChartandRtnDocCount("yearly");
		timeGaps.selectActions("Assign","yearlyActions");
		sessionSearch.verifyDocsFluctuation_BulkAssign(count7);
		
		timeGaps.fillingDetailsinTimeGaps(timeLine, fromDate, toDate);
		timeGaps.selectBarChartandRtnDocCount("yearly");
		timeGaps.selectActions("Monthly Timeline", "yearlyActions");
		driver.waitForPageToBeReady();
		String count8 = timeGaps.selectBarChartandRtnDocCount("monthly");
		timeGaps.selectActions("Assign","monthlyActions");
		sessionSearch.verifyDocsFluctuation_BulkAssign(count8);
		
		timeGaps.fillingDetailsinTimeGaps(timeLine, fromDate, toDate);
		timeGaps.selectBarChartandRtnDocCount("yearly");
		timeGaps.selectActions("Monthly Timeline", "yearlyActions");
		driver.waitForPageToBeReady();
		timeGaps.selectBarChartandRtnDocCount("monthly");
		timeGaps.selectActions(" Daily Timeline","monthlyActions");
		driver.waitForPageToBeReady();
		String count9 = timeGaps.selectBarChartandRtnDocCount("daily");
		timeGaps.selectActions("Assign","dailyActions");
		sessionSearch.verifyDocsFluctuation_BulkAssign(count9);
		
		baseClass.passedStep("Verified - the fluctuation of document count for all the bulk actions in Timeline and Gaps report");
		loginPage.logout();
	}
	
	
	@Test(description = "RPMXCON-54481", enabled = true, groups = { "regression" })
	public void VerifyTotalDocsFluctBulkActTallyReport() throws Exception {
		TallyPage tally = new TallyPage(driver);
		
		String tag = "Tag" + Utility.dynamicNameAppender();
		String tagRMU = "Tag" + Utility.dynamicNameAppender();
		String tagFolder = "TagFolder" + Utility.dynamicNameAppender();
		
		baseClass.stepInfo("To Verify the fluctuation of document count for all the bulk actions in Tally report");
		baseClass.stepInfo("RPMXCON-54481");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in As  : " + Input.pa1FullName);
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
		sessionSearch.bulkTag_FluctuationVerification(tagFolder, count1);
		
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
		sessionSearch.bulkFolder_FluctuationVerification(tagFolder, count2);
		
		loginPage.logout();
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in As : " + Input.pa1FullName);
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
}
