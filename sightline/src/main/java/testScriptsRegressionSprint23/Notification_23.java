package testScriptsRegressionSprint23;

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
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.BatchPrintPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Notification_23 {

	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	SessionSearch sessionSearch;
	SavedSearch saveSearch;
	TagsAndFoldersPage tagsAndFolderPage;
	Utility utility;
	SoftAssert softAssertion;

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
	public Object[][] users() {
		Object[][] users = { { Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password },
				{ Input.rev1userName, Input.rev1password } };
		return users;
	}

	/**
	 * @author NA Testcase No:RPMXCON-54421
	 * @Description:Verify that correct status Completed appears on My BackGround
	 *                     screen when user clicks When All Results Are Ready" +
	 *                     "button and search results are Ready on Advanced Search
	 *                     Screen.
	 **/
	@Test(description = "RPMXCON-54421", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void verifyCompldInBGWhenAllResReady(String userName, String passWord) throws Exception {
		base.stepInfo("RPMXCON-54421");
		base.stepInfo(
				"Verify that correct status Completed appears on My BackGround screen when user clicks When All Results Are Ready"
						+ "button and search results are Ready on Advanced Search Screen.");

		loginPage.loginToSightLine(userName, passWord);
		base.stepInfo("Logged in As " + userName);
		base = new BaseClass(driver);
		base.selectproject(Input.largeVolDataProject);

		sessionSearch.metadataSearchesUsingOperators(Input.metaDataName, Input.custodianName_Andrew, "OR",
				Input.metaDataName, Input.searchStringStar, true);
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
		base.waitForNotification();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		String status = sessionSearch.getRowData_BGT_Page("STATUS", backGroundID);
		System.out.println(status);
		if (status.equals("COMPLETED")) {
			base.passedStep("COMPLETED Status Appear On BackGround Screen..As Expected");
		} else {
			base.failedStep("COMPLETED Status Appear On BackGround Screen... Not As Expected");
		}
		base.passedStep(
				"Verified - that correct status Completed appears on My BackGround screen when user clicks When All Results Are Ready"
						+ " button and search results are Ready on Advanced Search Screen.");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-54422
	 * @Description:Verify that correct status "Completed" appears on My BackGround
	 *                     screen when user clicks "When Pure Hits Are Ready" button
	 *                     and search results are Ready on Advanced Search Screen.
	 **/
	@Test(description = "RPMXCON-54422", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void verifyCompldInBGWhenPureHitsReady(String userName, String passWord) throws Exception {
		base.stepInfo("RPMXCON-54422");
		base.stepInfo(
				"Verify that correct status \"Completed\" appears on My BackGround screen when user clicks \"When Pure Hits Are Ready\" button and search results are Ready on Advanced Search Screen.");

		loginPage.loginToSightLine(userName, passWord);
		base.stepInfo("Logged in As " + userName);
		base = new BaseClass(driver);
		base.selectproject(Input.largeVolDataProject);

		sessionSearch.metadataSearchesUsingOperators(Input.metaDataName, Input.custodianName_Andrew, "OR",
				Input.metaDataName, Input.searchStringStar, true);
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
		base.waitForNotification();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		String status = sessionSearch.getRowData_BGT_Page("STATUS", backGroundID);
		System.out.println(status);
		if (status.equals("COMPLETED")) {
			base.passedStep("COMPLETED Status Appear On BackGround Screen..As Expected");
		} else {
			base.failedStep("COMPLETED Status Appear On BackGround Screen... Not As Expected");
		}
		base.passedStep(
				"Verified - that correct status Completed appears on My BackGround screen when user clicks When Pure Hits Are Ready"
						+ " button and search results are Ready on Advanced Search Screen.");
		loginPage.logout();
	}

	/**
	 * @author sowndarya Testcase No:RPMXCON-54452
	 * @Description:Verify that - After Impersonation (SysAdmin to PA)- correct
	 *                     status "InProgress" appears on My BackGround screen when
	 *                     user performed Batch Print with 100+ documents from
	 *                     Search Group
	 **/
	@Test(description = "RPMXCON-54452", enabled = true, groups = { "regression" })
	public void verifyCompletedOnBackgroundScreen() throws Exception {
		base.stepInfo("RPMXCON-54452");
		base.stepInfo(
				"Verify that - After Impersonation (SysAdmin to PA)- correct status \"InProgress\" appears on My BackGround screen when user performed Batch Print with 100+ documents from Search Group");
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.impersonateSAtoPA();

		String Tag = "TAG" + Utility.dynamicNameAppender();
		DocListPage doclist = new DocListPage(driver);

		// create a tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(Tag, Input.securityGroup);
		// configure query & view in doclist
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();

		// Sort in Docfilename in Desc Order
		doclist.Selectpagelength("100");
		doclist.selectAllDocumentsInCurrentPageOnly();

		// bulk tag selected docs
		driver.waitForPageToBeReady();
		doclist.addNewBulkTag(Tag);

		// Select TAG & Native
		BatchPrintPage batchPrint = new BatchPrintPage(driver);
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab(Input.tag, Tag, true);
		batchPrint.fillingBasisForPrinting(true, true, null);
		batchPrint.navigateToNextPage(1);
		batchPrint.fillingExceptioanlFileTypeTab(false, Input.documentKey, null, true);

		// filling SlipSheet With metadata
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true, null);
		batchPrint.navigateToNextPage(1);

		// Filling Export File Name as 'DocID', select Sort by 'DocFileName' In
		// "DESC" Order
		batchPrint.selectSortingFromExportPage("DESC");
		batchPrint.generateBatchPrint(Input.documentKey, Input.documentKey, true);

		base.verifyMegaPhoneIconAndBackgroundTasks(true, true);

		String idValue = batchPrint.getBatchId(1).getText();
		System.out.println("Id : " + idValue);
		sessionSearch.getTxtDownloadFile(idValue).isElementAvailable(200);
		driver.Navigate().refresh();

		driver.waitForPageToBeReady();
		String status = sessionSearch.getRowData_BGT_Page("STATUS", idValue);
		System.out.println("status is : " + status);

		String passMsg = "Batch Print status of Id : " + idValue + "is : " + status;
		String failedMsg = "Batch print status is not displayed as expected";
		base.textCompareEquals(status, "COMPLETED", passMsg, failedMsg);
		loginPage.logout();
	}

}
