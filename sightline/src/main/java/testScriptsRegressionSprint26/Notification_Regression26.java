package testScriptsRegressionSprint26;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Collections;
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
import pageFactory.BaseClass;
import pageFactory.BatchPrintPage;
import pageFactory.CustomDocumentDataReport;
import pageFactory.LoginPage;
import pageFactory.ReportsPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.TimelineReportPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Notification_Regression26 {
	
	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	SessionSearch sessionSearch;
	SavedSearch saveSearch;
	TagsAndFoldersPage tagsAndFolderPage;
	Utility utility;
	SoftAssert softAssertion;
	BatchPrintPage batch;

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
		base = new BaseClass(driver);
		saveSearch = new SavedSearch(driver);
		sessionSearch = new SessionSearch(driver);
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

	@DataProvider(name = "Users")
	public Object[][] Users() {
		Object[][] users = { 
				{ Input.rmu1userName, Input.rmu1password },
				{ Input.pa1userName, Input.pa1password }, 
				{Input.rev1userName, Input.rev1password}
				};
		return users;
	}
	
	/**
	 * @author NA Testcase No:RPMXCON-54427
	 * @Description:Verify that correct status Completed appears on My BackGround screen when user clicks When All Results Are Ready button and search results are Ready on Basic Search Screen.
	 **/
	@Test(description = "RPMXCON-54427", enabled = true, dataProvider = "Users",  groups = { "regression" })
	public void verifyStatusCOMPLETEDOnBG(String username, String password) throws Exception {
		BaseClass  base = new BaseClass(driver);
		
		base.stepInfo("RPMXCON-54427");
		base.stepInfo("Verify that correct status Completed appears on My BackGround screen "
				+ "when user clicks When All Results Are Ready button and search results are Ready on Basic Search Screen.");
		
		loginPage.loginToSightLine(username, password);
		base.stepInfo("Logged in As " + username);
		base.selectproject(Input.largeVolDataProject);
		
		base.waitForElement(sessionSearch.getBullHornIcon());
        sessionSearch.getBullHornIcon().waitAndClick(20);
       
        sessionSearch.navigateToSessionSearchPageURL();
		driver.waitForPageToBeReady();
        sessionSearch.basicMetadataSearchesUsingOperators(Input.metaDataName, Input.custodianName_Andrew, "OR", Input.metaDataName, Input.searchStringStar);
		sessionSearch.SearchBtnAction();
		
		sessionSearch.verifyTileSpinning();
		base.clearBullHornNotification();
		String backGroundID = sessionSearch.handleWhenAllResultsPopUpDynamic();

		base.waitForElement(sessionSearch.getBullHornIcon());
		sessionSearch.getBullHornIcon().waitAndClick(20);
		base.waitForElement(sessionSearch.getViewAllBtn());
		sessionSearch.getViewAllBtn().waitAndClick(20);

		driver.waitForPageToBeReady();
		base.stepInfo("Navigated to My BackGround Task Page...");
		
		sessionSearch.checkingStatusUsingRefresh(backGroundID, "COMPLETED");
		driver.waitForPageToBeReady();
		String status = sessionSearch.getRowData_BGT_Page("STATUS", backGroundID);
		System.out.println(status);
		
		if (status.equals("COMPLETED")) {
			base.passedStep("COMPLETED Status Appear On BackGround Screen For Respective BackGround ID ..As Expected");
		} else {
			base.failedStep("COMPLETED Status Appear On BackGround Screen For Respective BackGround I... Not As Expected");
		}
		
		base.passedStep("Verified - that correct status Completed appears on My BackGround screen "
				+ "when user clicks When All Results Are Ready button and search results are Ready on Basic Search Screen.");
		loginPage.logout();
	}
	
	/**
	 * @author NA Testcase No:RPMXCON-54428
	 * @Description: Verify that correct status Completed appears on My BackGround screen when user clicks When Pure Hits Are Ready button and search results are Ready on Basic Search Screen.
	 **/

	@Test(description = "RPMXCON-54428", enabled = true, dataProvider = "Users",  groups = { "regression" })
	public void verifyCompletedStatusWhenPHReady(String username, String password) throws Exception {
		BaseClass  base = new BaseClass(driver);
		
		base.stepInfo("RPMXCON-54428");
		base.stepInfo("Verify that correct status Completed appears on My BackGround screen when user clicks"
				+ " When Pure Hits Are Ready button and search results are Ready on Basic Search Screen.");
		
		loginPage.loginToSightLine(username, password);
		base.stepInfo("Logged in As " + username);
		base.selectproject(Input.largeVolDataProject);
		
		base.waitForElement(sessionSearch.getBullHornIcon());
        sessionSearch.getBullHornIcon().waitAndClick(20);
       
        sessionSearch.navigateToSessionSearchPageURL();
		driver.waitForPageToBeReady();
        sessionSearch.basicMetadataSearchesUsingOperators(Input.metaDataName, Input.custodianName_Andrew, "OR", Input.metaDataName, Input.searchStringStar);
		sessionSearch.SearchBtnAction();
		
		sessionSearch.verifyTileSpinning();
		base.clearBullHornNotification();
		String backGroundID = sessionSearch.handleWhenPureHitsAreReadyInBellyBandPopup(20);

		base.waitForElement(sessionSearch.getBullHornIcon());
		sessionSearch.getBullHornIcon().waitAndClick(20);
		base.waitForElement(sessionSearch.getViewAllBtn());
		sessionSearch.getViewAllBtn().waitAndClick(20);

		driver.waitForPageToBeReady();
		base.stepInfo("Navigated to My BackGround Task Page...");
		
		sessionSearch.checkingStatusUsingRefresh(backGroundID, "COMPLETED");
		driver.waitForPageToBeReady();
		String status = sessionSearch.getRowData_BGT_Page("STATUS", backGroundID);
		System.out.println(status);
		
		if (status.equals("COMPLETED")) {
			base.passedStep("COMPLETED Status Appear On BackGround Screen For Respective BackGround ID ..As Expected");
		} else {
			base.failedStep("COMPLETED Status Appear On BackGround Screen For Respective BackGround I... Not As Expected");
		}
		
		base.passedStep("Verify that correct status Completed appears on My BackGround screen when user clicks "
				+ "When Pure Hits Are Ready button and search results are Ready on Basic Search Screen.");
		loginPage.logout();
	}
	
	/**
	 * @author NA Testcase No:RPMXCON-54446
	 * @Description:Verify that correct status \"InProgress\" appears on My BackGround screen when user performed Batch Print with 100+ documents from Search Group
	 **/
	@Test(description = "RPMXCON-54446", enabled = true, groups = { "regression" })
	public void verifyInProgressBatchPrintWithSG() throws Exception {
		SavedSearch search =new SavedSearch(driver);
		BaseClass base = new BaseClass(driver);
		
		base.stepInfo("RPMXCON-54446");
		base.stepInfo("Verify that correct status \"InProgress\" appears on My BackGround screen "
				+ "when user performed Batch Print with 100+ documents from Search Group");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		//create a tag
		search.navigateToSavedSearchPage();
		String searchGroup = search.createSearchGroupAndReturn(Input.mySavedSearch, "PA", "No");
		String searchName = "Search" + Utility.dynamicNameAppender();
		
		// configure query & view in doclist
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearchInNewNode(searchName, searchGroup);
		
		// Select TAG & Native
		BatchPrintPage batchPrint= new BatchPrintPage(driver);
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTag(Input.mySavedSearch, searchGroup, searchName);
		batchPrint.navigateToNextPage(1);
		driver.waitForPageToBeReady();
		batchPrint.navigateToNextPage(1);
		driver.waitForPageToBeReady();
		batchPrint.fillingExceptioanlFileTypeTab(false, Input.documentKey, null, true);
		driver.waitForPageToBeReady();
		// filling SlipSheet With metadata
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true, null);
		driver.waitForPageToBeReady();
		batchPrint.navigateToNextPage(1);
		driver.waitForPageToBeReady();
		// Filling Export File Name as 'DocID', select Sort by 'DocFileName' In
		batchPrint.selectSortingFromExportPage("DESC");
		driver.waitForPageToBeReady();
		batchPrint.generateBatchPrint(Input.documentKey, Input.documentKey, true);
		driver.waitForPageToBeReady();
		
		base.verifyMegaPhoneIconAndBackgroundTasks(true, true);
		
		String idValue=batchPrint.getBatchId(1).getText();
		System.out.println("Id : "+ idValue);
		
		driver.waitForPageToBeReady();
		String status = sessionSearch.getRowData_BGT_Page("STATUS", idValue);
		System.out.println("status is : "+status);

		String passMsg="Batch Print status of Id : "+idValue +"is : "+status;
		String failedMsg="Batch print status is not displayed as expected";
	
		base.textCompareEquals(status, "INPROGRESS", passMsg, failedMsg);
		driver.waitForPageToBeReady();
		List<String> id = sessionSearch.getAllIDFromBGPage();
		System.out.println(id);
		System.out.println(id.size());
		int occurence = Collections.frequency(id, idValue);
		System.out.println(occurence);
		if(occurence == 1) {
			base.passedStep("Same ID not appear multiple times on BackGround Screen.");
		} else {
			base.failedStep("Same ID  appear multiple times on BackGround Screen.");
		}
		base.passedStep("Verify that correct status \"InProgress\" appears on My BackGround screen "
				+ "when user performed Batch Print with 100+ documents from Search Group");
		loginPage.logout();
	}
	
	
	/**
	 * @author NA Testcase No:RPMXCON-54450
	 * @Description:Verify that correct status \"InProgress\" appears on My BackGround screen when user performed Batch Print with 100+ documents from PA/Security Group Shared Group
	 **/
	@Test(description = "RPMXCON-54450", enabled = true, groups = { "regression" })
	public void verifyInProgressBatchPrintWithPASG() throws Exception {
		SavedSearch search =new SavedSearch(driver);
		BaseClass base = new BaseClass(driver);
		
		base.stepInfo("RPMXCON-54450");
		base.stepInfo("Verify that correct status \"InProgress\" appears on My BackGround screen "
				+ "when user performed Batch Print with 100+ documents from PA/Security Group Shared Group");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
	
		search.navigateToSavedSearchPage();
		String searchGroup = search.createSearchGroupAndReturn(Input.mySavedSearch, "PA", "No");
		String searchName = "Search" + Utility.dynamicNameAppender();
		
		// configure query & view in doclist
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearchInNewNode(searchName, searchGroup);
		
		search.navigateToSavedSearchPage();
		driver.waitForPageToBeReady();
		search.selectNode1(searchGroup);
		search.shareSavedNodeWithDesiredGroup(searchGroup, Input.shareSearchPA);
		
		// Select TAG & Native
		BatchPrintPage batchPrint= new BatchPrintPage(driver);
		batchPrint.navigateToBatchPrintPage();
		driver.waitForPageToBeReady();
		batchPrint.fillingSourceSelectionTag(Input.shareSearchPA, searchGroup, searchName);
		driver.waitForPageToBeReady();
		batchPrint.navigateToNextPage(1);
		driver.waitForPageToBeReady();
		batchPrint.navigateToNextPage(1);
		driver.waitForPageToBeReady();
		batchPrint.fillingExceptioanlFileTypeTab(false, Input.documentKey, null, true);
		driver.waitForPageToBeReady();
		// filling SlipSheet With metadata
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true, null);
		driver.waitForPageToBeReady();
		batchPrint.navigateToNextPage(1);
		driver.waitForPageToBeReady();
		// Filling Export File Name as 'DocID', select Sort by 'DocFileName' In
		batchPrint.selectSortingFromExportPage("DESC");
		driver.waitForPageToBeReady();
		batchPrint.generateBatchPrint(Input.documentKey, Input.documentKey, true);
		driver.waitForPageToBeReady();
		base.verifyMegaPhoneIconAndBackgroundTasks(true, true);
		driver.waitForPageToBeReady();
		String idValue=batchPrint.getBatchId(1).getText();
		System.out.println("Id : "+ idValue);
		driver.waitForPageToBeReady();
		String status = sessionSearch.getRowData_BGT_Page("STATUS", idValue);
		System.out.println("status is : "+status);

		String passMsg="Batch Print status of Id : "+idValue +"is : "+status;
		String failedMsg="Batch print status is not displayed as expected";
	
		base.textCompareEquals(status, "INPROGRESS", passMsg, failedMsg);
		driver.waitForPageToBeReady();
		List<String> id = sessionSearch.getAllIDFromBGPage();
		System.out.println(id);
		System.out.println(id.size());
		int occurence = Collections.frequency(id, idValue);
		System.out.println(occurence);
		if(occurence == 1) {
			base.passedStep("Same ID not appear multiple times on BackGround Screen.");
		} else {
			base.failedStep("Same ID appear multiple times on BackGround Screen.");
		}
		base.passedStep("Verify that correct status \"InProgress\" appears on My BackGround screen "
				+ "when user performed Batch Print with 100+ documents from PA/Security Group Shared Group");
		loginPage.logout();
	
	}
	
}
