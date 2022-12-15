package testScriptsRegressionSprint28;

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
import pageFactory.DocListPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BatchPrint_Regression28 {

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
		session = new SessionSearch(driver);
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		page = new ProductionPage(driver);
		softassert = new SoftAssert();
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
//												 TODO: handle exception
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

	@DataProvider(name = "Users")
	public Object[][] Users() {
		Object[][] users = { { Input.pa1userName, Input.pa1password },
//				{ Input.rmu1userName, Input.rmu1password }, 
		};
		return users;
	}

	/**
	 * @Author sowndarya
	 * @Description :Validate printing an EXCEL file type and has native and text
	 *              file [RPMXCON-50731]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-50731", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void verifyPrintingExcelWithNativeAndText(String username, String password) throws Exception {
		
		String Tag = "TAG" + Utility.dynamicNameAppender();
		String slipsheetDD = "Create new slip sheets";

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-50731 Batch Print");
		baseClass.stepInfo("Validate printing an EXCEL file type and has native and text file");

		// create folder in Default SG
		tagsAndFolderPage.CreateTagwithClassification(Tag, Input.tagNamePrev);

		// configure query for native and text file & view in doclist
		session.basicMetaDataSearch(Input.sourceDocIdSearch, null, Input.NativeSourceDocId, null);
		int index = baseClass.getIndex(session.getDetailsTable(), "DOCID");
		 String docId = session.getValueFromTable(index).getText();
		System.out.println(docId);
		session.bulkTagExisting(Tag);

		boolean[] selectPdfRadio = { false, true };
		for (int i = 0; i < selectPdfRadio.length; i++) {
			boolean radio = selectPdfRadio[i];

			// Select TAG & Select Native in basis for printing
			batchPrint.navigateToBatchPrintPage();
			batchPrint.fillingSourceSelectionTab(Input.tag, Tag, true);
			batchPrint.fillingBasisForPrinting(true, true, null);
			batchPrint.navigateToNextPage(2);
			batchPrint.selectDropdownFromSlipSheet_prod(slipsheetDD);
			batchPrint.fillingSlipSheetWithMetadata("AllProductionBatesRanges", true, null);
			batchPrint.navigateToNextPage(1);
			// Generate Batch Print , When Radio is True it will select One PDF for all
			// when Radio is false it will select one PDF for each
			batchPrint.fillingExportFormatPage(Input.documentKey, Input.documentKey, radio, 10);

			// Download Batch Print File
			String fileName = batchPrint.DownloadBatchPrintFile();

			// extract Zip file
			String extractedFile = batchPrint.extractFile(fileName);

			// verify Downloaded File Count and Format
			List<String> list = batchPrint
					.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);
			baseClass.compareTextViaContains(list.get(0),docId, "Native file is downloaded", "Native file is downloaded");
		}
		loginPage.logout();
	}
	

	/**
	 * @Author sowndarya
	 * @Description :Validate printing an EXCEL file type and doesn't have it's Native or text file [RPMXCON-50733]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-50733", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void verifyPrintingExcelWithoutNativeAndText(String username, String password) throws Exception {
		
		String Tag = "TAG" + Utility.dynamicNameAppender();
		String slipsheetDD = "Create new slip sheets";

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-50733 Batch Print");
		baseClass.stepInfo("Validate printing an EXCEL file type and has native and text file");

		// create folder in Default SG
		tagsAndFolderPage.CreateTagwithClassification(Tag, Input.tagNamePrev);

		// configure query for native and text file & view in doclist
		session.basicMetaDataSearch(Input.sourceDocIdSearch, null, Input.TextSourceDocId, null);
		int index = baseClass.getIndex(session.getDetailsTable(), "DOCID");
		 String docId = session.getValueFromTable(index).getText();
		System.out.println(docId);
		session.bulkTagExisting(Tag);

		boolean[] selectPdfRadio = { false, true };
		for (int i = 0; i < selectPdfRadio.length; i++) {
			boolean radio = selectPdfRadio[i];

			// Select TAG & Select Native in basis for printing
			batchPrint.navigateToBatchPrintPage();
			batchPrint.fillingSourceSelectionTab(Input.tag, Tag, true);
			batchPrint.fillingBasisForPrinting(true, true, null);
			batchPrint.navigateToNextPage(2);
			batchPrint.selectDropdownFromSlipSheet_prod(slipsheetDD);
			batchPrint.fillingSlipSheetWithMetadata("AllProductionBatesRanges", true, null);
			batchPrint.navigateToNextPage(1);
			// Generate Batch Print , When Radio is True it will select One PDF for all
			// when Radio is false it will select one PDF for each
			batchPrint.fillingExportFormatPage(Input.documentKey, Input.documentKey, radio, 10);

			// Download Batch Print File
			String fileName = batchPrint.DownloadBatchPrintFile();

			// extract Zip file
			String extractedFile = batchPrint.extractFile(fileName);

			// verify Downloaded File Count and Format
			List<String> list = batchPrint
					.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);
			baseClass.compareTextViaContains(list.get(0),docId, "Native file is downloaded", "Native file is downloaded");
		}
		loginPage.logout();
	}
	
	/**
	 * @Author sowndarya
	 * @Description :Validate printing an EXCEL file type and has text file but not it's native [RPMXCON-50732]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-50732", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void verifyPrintingExcelWithTextOnly(String username, String password) throws Exception {
		
		String Tag = "TAG" + Utility.dynamicNameAppender();
		String slipsheetDD = "Create new slip sheets";

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-50732 Batch Print");
		baseClass.stepInfo("Validate printing an EXCEL file type and has text file but not it's native");

		// create folder in Default SG
		tagsAndFolderPage.CreateTagwithClassification(Tag, Input.tagNamePrev);

		// configure query for text and not native file & Bulk tag
		session.basicMetaDataSearch(Input.sourceDocIdSearch, null, Input.TextSourceDocId, null);
		int index = baseClass.getIndex(session.getDetailsTable(), "DOCID");
		 String docId = session.getValueFromTable(index).getText();
		System.out.println(docId);
		session.bulkTagExisting(Tag);

		boolean[] selectPdfRadio = { false, true };
		for (int i = 0; i < selectPdfRadio.length; i++) {
			boolean radio = selectPdfRadio[i];

			// Select TAG & Select Native in basis for printing
			batchPrint.navigateToBatchPrintPage();
			batchPrint.fillingSourceSelectionTab(Input.tag, Tag, true);
			batchPrint.fillingBasisForPrinting(true, true, null);
			batchPrint.navigateToNextPage(2);
			batchPrint.selectDropdownFromSlipSheet_prod(slipsheetDD);
			batchPrint.fillingSlipSheetWithMetadata("AllProductionBatesRanges", true, null);
			batchPrint.navigateToNextPage(1);
			// Generate Batch Print , When Radio is True it will select One PDF for all
			// when Radio is false it will select one PDF for each
			batchPrint.fillingExportFormatPage(Input.documentKey, Input.documentKey, radio, 10);

			// Download Batch Print File
			String fileName = batchPrint.DownloadBatchPrintFile();

			// extract Zip file
			String extractedFile = batchPrint.extractFile(fileName);

			// verify Downloaded File Count and Format
			List<String> list = batchPrint
					.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);
			baseClass.compareTextViaContains(list.get(0),docId, "Native file is downloaded", "Native file is downloaded");
		}
		loginPage.logout();
	}
}