package testScriptsRegressionSprint27;

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
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.BatchPrintPage;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Notification_Regression27 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Utility utility;
	SoftAssert softAssertion;
	ProjectPage projects;
	String projectName;
	String clientName;

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
		baseClass = new BaseClass(driver);
		softAssertion = new SoftAssert();
		projects = new ProjectPage(driver);

	}

	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		baseClass = new BaseClass(driver);
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
		System.out.println("******TEST CASES FOR PROJECTS EXECUTED******");

	}
	
	/**
	 * @author NA Testcase No:RPMXCON-54445
	 * @Description:Verify that correct status \"Completed\" appears on My BackGround screen "
				+ "when user performed Batch Print with 100+ documents from Search Group
	 **/
	@Test(description = "RPMXCON-54445", enabled = true, groups = { "regression" })
	public void verifyCompletedBatchPrintWithSG() throws Exception {
		SavedSearch search =new SavedSearch(driver);
		BaseClass base = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		
		base.stepInfo("RPMXCON-54445");
		base.stepInfo("Verify that correct status \"Completed\" appears on My BackGround screen "
				+ "when user performed Batch Print with 100+ documents from Search Group");
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		//create a tag
		search.navigateToSavedSearchPage();
		String searchGroup = search.createSearchGroupAndReturn(Input.mySavedSearch, "PA", "No");
		String searchName = "Search1" + Utility.dynamicNameAppender();
		
		// configure query & view in doclist
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearchForThreeItems(Input.searchString5, Input.searchString6, Input.testData1);
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

		driver.waitForPageToBeReady();
		String idValue = batchPrint.getBatchId(1).getText();
		System.out.println("Id : "+ idValue);
//		sessionSearch.verifyba
		sessionSearch.refreshAndVerifyStatus(idValue, "COMPLETED", 50);
		driver.waitForPageToBeReady();
		String status = sessionSearch.getRowData_BGT_Page("STATUS", idValue);
		System.out.println("status is : "+status);

		String passMsg="Batch Print status of Id : "+idValue +"is : "+status;
		String failedMsg="Batch print status is not displayed as expected";
	
		base.textCompareEquals(status, "COMPLETED", passMsg, failedMsg);
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
		base.passedStep("Verify that correct status \"Completed\" appears on My BackGround screen "
				+ "when user performed Batch Print with 100+ documents from Search Group");
		loginPage.logout();
	}
	
	/**
	 * @author NA Testcase No:RPMXCON-54451
	 * @Description:Verify that - After Impersonation (SysAdmin to PA) - correct status \"Completed\" appears on My BackGround screen"
				+ " when user performed Batch Print with 100+ documents from Search Group
	 **/
	@Test(description = "RPMXCON-54451", enabled = true, groups = { "regression" })
	public void verifyCompletedBatchPrintWithSGSAtoPA() throws Exception {
		SavedSearch search =new SavedSearch(driver);
		BaseClass base = new BaseClass(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		
		base.stepInfo("RPMXCON-54451");
		base.stepInfo("Verify that - After Impersonation (SysAdmin to PA) - correct status \"Completed\" appears on My BackGround screen"
				+ " when user performed Batch Print with 100+ documents from Search Group");
		
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
        base.impersonateSAtoPA(Input.projectName);
        driver.waitForPageToBeReady();
        
		//create a tag
		search.navigateToSavedSearchPage();
		String searchGroup = search.createSearchGroupAndReturn(Input.mySavedSearch, "PA", "No");
		String searchName = "Search1" + Utility.dynamicNameAppender();
		
		// configure query & view in doclist
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearchForThreeItems(Input.searchString5, Input.searchString6, Input.testData1);
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

		driver.waitForPageToBeReady();
		String idValue = batchPrint.getBatchId(1).getText();
		System.out.println("Id : "+ idValue);
		sessionSearch.refreshAndVerifyStatus(idValue, "COMPLETED", 50);
		driver.waitForPageToBeReady();
		String status = sessionSearch.getRowData_BGT_Page("STATUS", idValue);
		System.out.println("status is : "+status);

		String passMsg="Batch Print status of Id : "+idValue +"is : "+status;
		String failedMsg="Batch print status is not displayed as expected";
	
		base.textCompareEquals(status, "COMPLETED", passMsg, failedMsg);
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
		base.passedStep("Verify that - After Impersonation (SysAdmin to PA) - correct status \"Completed\" appears on My BackGround screen"
				+ " when user performed Batch Print with 100+ documents from Search Group");
		loginPage.logout();
	}
	
	/**
	 * @author NA Testcase No:RPMXCON-54449
	 * @Description:Verify that correct status \"Completed\" appears on My BackGround screen "
				+ "when user performed Batch Print with 100+ documents from PA/Security Group Shared Group
	 **/
	@Test(description = "RPMXCON-54449", enabled = true, groups = { "regression" })
	public void verifyCompletedBatchPrintWithPASG() throws Exception {
		SessionSearch sessionSearch = new SessionSearch(driver);
		SavedSearch search =new SavedSearch(driver);
		BaseClass base = new BaseClass(driver);
		
		base.stepInfo("RPMXCON-54449");
		base.stepInfo("Verify that correct status \"Completed\" appears on My BackGround screen "
				+ "when user performed Batch Print with 100+ documents from PA/Security Group Shared Group");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
	
		search.navigateToSavedSearchPage();
		String searchGroup = search.createSearchGroupAndReturn(Input.mySavedSearch, "PA", "No");
		String searchName = "Search" + Utility.dynamicNameAppender();
		
		// configure query & view in doclist
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearchForThreeItems(Input.searchString5, Input.searchString6, Input.testData1);
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
		
		driver.waitForPageToBeReady();
		String idValue = batchPrint.getBatchId(1).getText();
		System.out.println("Id : "+ idValue);
		sessionSearch.refreshAndVerifyStatus(idValue, "COMPLETED", 50);
		driver.waitForPageToBeReady();
		String status = sessionSearch.getRowData_BGT_Page("STATUS", idValue);
		System.out.println("status is : "+status);

		String passMsg="Batch Print status of Id : "+idValue +"is : "+status;
		String failedMsg="Batch print status is not displayed as expected";
	
		base.textCompareEquals(status, "COMPLETED", passMsg, failedMsg);
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
		base.passedStep("Verify that correct status \"Completed\" appears on My BackGround screen "
				+ "when user performed Batch Print with 100+ documents from PA/Security Group Shared Group");
		loginPage.logout();
	
	}
}
