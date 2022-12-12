package testScriptsRegressionSprint27;

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
import pageFactory.BaseClass;
import pageFactory.BatchPrintPage;
import pageFactory.DataSets;
import pageFactory.DocListPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BatchPrint_Regression26 {

	SoftAssert softassert;
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	BatchPrintPage batchPrint;
	SessionSearch session;
	TagsAndFoldersPage tagsAndFolderPage;
	ProductionPage page;
	String specificTag = "Tag" + Utility.dynamicNameAppender();

	@BeforeClass(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");

		Input in = new Input();
		in.loadEnvConfig();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());

		driver = new Driver();
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		batchPrint = new BatchPrintPage(driver);
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		Reporter.setCurrentTestResult(result);
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			try { // if any tc failed and dint logout!
				loginPage.logoutWithoutAssert();
			} catch (Exception e) {
//										 TODO: handle exception
			}
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
			loginPage.clearBrowserCache();
		}
		System.out.println("Executed :" + result.getMethod().getMethodName());
	}

	@AfterClass(alwaysRun = true)
	public void close() {

		try {
			loginPage.clearBrowserCache();
		} finally {
			loginPage.clearBrowserCache();
		}
	}

	@DataProvider(name = "UserSaUDaUPaU")
	public Object[][] UserSaUDaUPaU() {
		Object[][] users = { 
				{ Input.sa1userName, Input.sa1password,"SA","PA" },
				{ Input.sa1userName, Input.sa1password,"SA","RMU" },
				{ Input.da1userName, Input.da1password,"SA","PA" },
				{ Input.da1userName, Input.da1password,"SA","RMU" },
				};
		return users;
	}
	
	@DataProvider(name = "Users")
	public Object[][] Users() {
		Object[][] users = { { Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password } };
		return users;
	}
	
	/**
	 * @throws Exception
	 * @Author sowndarya
	 * @Description :Verify after impersonation native should be printed as per
	 *              selected option from analysis tab [RPMXCON-47470]
	 */
	@Test(description = "RPMXCON-47470", enabled = true, dataProvider = "UserSaUDaUPaU", groups = { "regression" })
	public void verifyNativeFromAnalysisTabAfterImpersonation(String username, String password, String fromRole, String toRole) throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-47470 Batch Print");
		baseClass.stepInfo(
				"Verify after impersonation native should be printed as per selected option from analysis tab");
	
		String slipsheetDD = "Create new slip sheets";
		int count = 7;
		
		// Login via User
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Loggedin As : " + fromRole);
		// Pre-requesties
		baseClass.rolesToImp(fromRole,toRole);
		
		if (toRole.equals("PA")) {
			
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(specificTag, Input.tagNamePrev);

		DataSets data = new DataSets(driver);
		data.NavigateToDataSets();
		data.selectDataSetWithName("_Automation_All");
		DocListPage doc = new DocListPage(driver);
		driver.waitForPageToBeReady();

		doc.documentSelection(count);
		doc.bulkTagExistingFromDoclist(specificTag);
		}
		
		// Select TAG & Select Native in basis for printing
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab(Input.tag, specificTag, true);
		batchPrint.fillingBasisForPrinting(true, true, null);
		batchPrint.fillingAnalysisTab(false, false, false, true);

		baseClass.waitForElement(batchPrint.getDocCountInAnalysisPage());
		String translationDocCount = batchPrint.getDocCountInAnalysisPage().getText();
		System.out.println("Trranslated doc :"+translationDocCount);

		baseClass.waitForElement(batchPrint.getRequestedDocCountInAnalysisPage());
		String completeText = batchPrint.getRequestedDocCountInAnalysisPage().getText();
		String[] reqDocCount = completeText.split(" ");
		System.out.println("file count : " + reqDocCount[4]);
		int docToPrint = Integer.parseInt(reqDocCount[4]);

		// verifying selected documents and requested documents are having same count
		baseClass.digitCompareEquals(count, docToPrint, "selected documents and requested documents are having same count", "selected documents and requested documents are not having same count");

		batchPrint.navigateToNextPage(1);
		if (!batchPrint.getSlipSheetDD_prod().isElementAvailable(10)) {
			batchPrint.navigateToNextPage(1);
		}
		batchPrint.selectDropdownFromSlipSheet_prod(slipsheetDD);
		batchPrint.fillingSlipSheetWithMetadata("AllProductionBatesRanges", true, null);
		if (!batchPrint.getSelectExportFileName().isElementAvailable(10)) {
			batchPrint.navigateToNextPage(1);
		}

		// Filling Export File Name as 'DOCFileName', select Sort by 'DOCID'
		batchPrint.fillingExportFormatPage(Input.docFileName, Input.documentKey, false, 20);

		// Download Batch Print File
		String fileName = batchPrint.DownloadBatchPrintFile();

		// extract Zip file
		String extractedFile = batchPrint.extractFile(fileName);

		// verify Downloaded File Count and Format
		List<String> list = batchPrint.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);

		int downloadedDocCount = list.size();
		System.out.println(downloadedDocCount);

	   // verifying translation documents are excluded
		int translationExcludedDoc =docToPrint-Integer.parseInt(translationDocCount);
		baseClass.digitCompareEquals(translationExcludedDoc, downloadedDocCount, "verifying translation documents and downloaded documents are having same count", "verifying translation documents and downloaded documents are not having same count");
	
		loginPage.logout();
	}
	
	/**
	 * @throws Exception
	 * @Author sowndarya
	 * @Description :Verify native should be printed when 'Ignore these documents from print request.' is selected from analysis tab [RPMXCON-47469]
	 */
	@Test(description = "RPMXCON-47469", enabled = true, dataProvider = "Users", groups = { "regression" })
	public void verifyNativeFromAnalysisTab(String username,String password) throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-47469 Batch Print");
		baseClass.stepInfo(
				"Verify native should be printed when 'Ignore these documents from print request.' is selected from analysis tab");
	
		String slipsheetDD = "Create new slip sheets";
		int count = 7;
		
		// Login via User
		loginPage.loginToSightLine(username, password);
		
		if (username.equals(Input.pa1userName)) {
			
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(specificTag, Input.tagNamePrev);

		DataSets data = new DataSets(driver);
		data.NavigateToDataSets();
		data.selectDataSetWithName("_Automation_All");
		DocListPage doc = new DocListPage(driver);
		driver.waitForPageToBeReady();

		doc.documentSelection(count);
		doc.bulkTagExistingFromDoclist(specificTag);
		}
		
		// Select TAG & Select Native in basis for printing
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab(Input.tag, specificTag, true);
		batchPrint.fillingBasisForPrinting(true, true, null);
		batchPrint.fillingAnalysisTab(false, false, false, true);

		baseClass.waitForElement(batchPrint.getDocCountInAnalysisPage());
		String translationDocCount = batchPrint.getDocCountInAnalysisPage().getText();
		System.out.println("Trranslated doc :"+translationDocCount);

		baseClass.waitForElement(batchPrint.getRequestedDocCountInAnalysisPage());
		String completeText = batchPrint.getRequestedDocCountInAnalysisPage().getText();
		String[] reqDocCount = completeText.split(" ");
		System.out.println("file count : " + reqDocCount[4]);
		int docToPrint = Integer.parseInt(reqDocCount[4]);

		// verifying selected documents and requested documents are having same count
		baseClass.digitCompareEquals(count, docToPrint, "selected documents and requested documents are having same count", "selected documents and requested documents are not having same count");

		batchPrint.navigateToNextPage(1);
		if (!batchPrint.getSlipSheetDD_prod().isElementAvailable(10)) {
			batchPrint.navigateToNextPage(1);
		}
		batchPrint.selectDropdownFromSlipSheet_prod(slipsheetDD);
		batchPrint.fillingSlipSheetWithMetadata("AllProductionBatesRanges", true, null);
		if (!batchPrint.getSelectExportFileName().isElementAvailable(10)) {
			batchPrint.navigateToNextPage(1);
		}

		// Filling Export File Name as 'DOCFileName', select Sort by 'DOCID'
		batchPrint.fillingExportFormatPage(Input.docFileName, Input.documentKey, false, 20);

		// Download Batch Print File
		String fileName = batchPrint.DownloadBatchPrintFile();

		// extract Zip file
		String extractedFile = batchPrint.extractFile(fileName);

		// verify Downloaded File Count and Format
		List<String> list = batchPrint.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);

		int downloadedDocCount = list.size();
		System.out.println(downloadedDocCount);

		// verifying translation documents excluded
		int translationExcludedDoc =docToPrint-Integer.parseInt(translationDocCount);
		baseClass.digitCompareEquals(translationExcludedDoc, downloadedDocCount, "verifying translation documents and downloaded documents are having same count", "verifying translation documents and downloaded documents are not having same count");
	
		loginPage.logout();
	}

}
