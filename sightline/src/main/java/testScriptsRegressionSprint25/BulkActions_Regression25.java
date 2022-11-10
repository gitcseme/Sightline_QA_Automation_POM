package testScriptsRegressionSprint25;

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
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SearchTermReportPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
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
		sessionSearch.bulkTag_FluctuationVerify(folderTag, count);
		
		search.navigateToSavedSearchPage();
		search.savedSearch_Searchandclick(search1);
		baseClass.waitForElement(search.getSavedSearchToBulkFolder());
        search.getSavedSearchToBulkFolder().waitAndClick(5);
		sessionSearch.bulkFolder_FluctuationVerify(folderTag, count);
		
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
	    sessionSearch.bulkTag_FluctuationVerify(folderTag, count);
	    
	    driver.Navigate().refresh();
	    driver.waitForPageToBeReady();
	    
	    docList.documentSelection(Integer.parseInt(count));
	    baseClass.waitForElement(docList.getBulkActionButton());
	    docList.getBulkActionButton().waitAndClick(5);
	    baseClass.waitForElement(docList.getBulkFolderAction());
	    docList.getBulkFolderAction().waitAndClick(5);
	    sessionSearch.bulkFolder_FluctuationVerify(folderTag, count);  
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
	    sessionSearch.bulkFolder_FluctuationVerify(folder, count);  
	    
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
		 sessionSearch.bulkTag_FluctuationVerify(folderTag, count);
		 
		 driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		 driver.waitForPageToBeReady();
		 srcTermReport.GenerateReport(saveSearchNamePA);	 
		 baseClass.waitForElement(srcTermReport.getActionButton());
		 srcTermReport.getActionButton().waitAndClick(20);
		 baseClass.waitForElement(srcTermReport.getActionBulkFolder());
		 srcTermReport.getActionBulkFolder().waitAndClick(20);
		 sessionSearch.bulkFolder_FluctuationVerify(folderTag, count); 
		 
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
}
