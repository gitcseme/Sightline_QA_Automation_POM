package testScriptsRegressionSprint21;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import net.lingala.zip4j.exception.ZipException;
import pageFactory.BaseClass;
import pageFactory.BatchPrintPage;
import pageFactory.DocListPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ManageAssignment;
import pageFactory.ProductionPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BatchPrintRegression_21 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	BatchPrintPage batchPrint;
	SessionSearch session;
	TagsAndFoldersPage tagsAndFolderPage;

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
	}

	@DataProvider(name = "Users")
	public Object[][] Users() {
		Object[][] users = { { Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password }, };
		return users;
	}

	/**
	 * @Author Jeevitha
	 * @Description : Validate Batch Print Sorting docs by DocDate (Native) with one
	 *              PDF for each doc in descending order [RPMXCON-49189]
	 * @throws IOException
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-49189", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void validateSortingByDocDate(String username, String password)
			throws InterruptedException, IOException, AWTException {
		String Tag = "TAG" + Utility.dynamicNameAppender();
		String columnName = "DocDate";
		DocListPage doclist = new DocListPage(driver);

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-49189 Batch Print");
		baseClass.stepInfo(
				"Validate Batch Print Sorting docs by DocDate (Native) with one PDF for each doc in descending order");

		// configure query & view in doclist
		session.basicContentSearch(Input.searchStringStar);
		session.ViewInDocList();

		// select Few docs with & without Docdate
		driver.waitForPageToBeReady();
		doclist.verifyAndAddColumn(columnName);
		doclist.sortColumn(true, columnName, true);
		driver.waitForPageToBeReady();
		doclist.documentSelectionIncludeChildDoc(3);
		doclist.sortColumn(true, columnName, false);
		doclist.documentSelectionIncludeChildDoc(3);

		// bulk tag selected docs
		doclist.addNewBulkTag(Tag);

		// To verify Expected result , Get the Descending order List
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		driver.waitForPageToBeReady();
		tagsAndFolderPage.ViewinDocListthrTag(Tag);

		// sort in Descending Order
		doclist.sortColumn(true, columnName, false);

		// expected Downloaded Document Names Order
		baseClass.waitForElementCollection(doclist.getDocIds());
		List<String> docIdsList = baseClass.availableListofElements(doclist.getDocIds());

		// Select TAG
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab(Input.tag, Tag, true);
		batchPrint.fillingBasisForPrinting(true, true, null);
		batchPrint.navigateToNextPage(1);
		batchPrint.fillingExceptioanlFileTypeTab(false, Input.documentKey, null, true);

		// filling SlipSheet With metadata
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true, null);
		batchPrint.navigateToNextPage(1);

		// Filling Export File Name as 'DocID', select Sort by 'DocDate' In "DESC" Order
		batchPrint.selectSortingFromExportPage("DESC");
		batchPrint.fillingExportFormatPage(Input.documentKey, columnName, false, 20);

		// Download Batch Print File
		String fileName = batchPrint.DownloadBatchPrintFile();

		// extract zip file
		String extractedFile = batchPrint.extractFile(fileName);

		// verify Downloaded File Count ,filename and Format
		List<String> actualFileName = batchPrint
				.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);

		// verify DocDate DESC Sorting order of Downloaded File is As Expected
		boolean result = baseClass.compareListViaContains(actualFileName, docIdsList);
		baseClass.printResutInReport(result, "Downloaded FileNames Sorted as per DocDate DESC order ",
				"Sorting is not as Expected", "Pass");

		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Validate Batch Print Sorting docs by LastSaveDate (Native)
	 *              with one PDF for each doc in descending order [RPMXCON-49184]
	 * @throws IOException
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-49184", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void validateSortingByLastSaveDate(String username, String password)
			throws InterruptedException, IOException, AWTException {
		String Tag = "TAG" + Utility.dynamicNameAppender();
		String columnName = "LastSaveDate";
		DocListPage doclist = new DocListPage(driver);

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-49184 Batch Print");
		baseClass.stepInfo(
				"Validate Batch Print Sorting docs by LastSaveDate (Native) with one PDF for each doc in descending order");

		// configure query & view in doclist
		session.basicContentSearch(Input.searchStringStar);
		session.ViewInDocList();

		// select Few docs with & without LastSaveDate
		driver.waitForPageToBeReady();
		doclist.verifyAndAddColumn(columnName);
		doclist.sortColumn(true, columnName, true);
		driver.waitForPageToBeReady();
		doclist.documentSelectionIncludeChildDoc(3);
		doclist.sortColumn(true, columnName, false);
		doclist.documentSelectionIncludeChildDoc(3);

		// bulk tag selected docs
		doclist.addNewBulkTag(Tag);

		// To verify Expected result , Get the Descending order DocDate List
		tagsAndFolderPage.ViewinDocListthrTag(Tag);

		// sort in Descending Order
		doclist.sortColumn(true, columnName, false);

		// expected Downloaded Document Names list ,Desc Order of LastSaveDate
		int index = baseClass.getIndex(doclist.getColumnHeader(), Input.docFileName);
		baseClass.waitForElementCollection(doclist.GetColumnData(index));
		List<String> docNameList = doclist.availableListofElementsForDocList(doclist.GetColumnData(index));

		// Select TAG
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab(Input.tag, Tag, true);
		batchPrint.fillingBasisForPrinting(true, true, null);
		batchPrint.navigateToNextPage(1);
		batchPrint.fillingExceptioanlFileTypeTab(false, Input.documentKey, null, true);

		// filling SlipSheet With metadata
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true, null);
		batchPrint.navigateToNextPage(1);

		// Filling Export File Name as 'DocFileName', select Sort by 'LastSaveDate' In
		// "DESC" Order
		batchPrint.selectSortingFromExportPage("DESC");
		batchPrint.fillingExportFormatPage(Input.docFileName, columnName, false, 20);

		// Download Batch Print File
		String fileName = batchPrint.DownloadBatchPrintFile();

		// extract zip file
		String extractedFile = batchPrint.extractFile(fileName);

		// verify Downloaded File Count ,filename and Format
		List<String> actualFileName = batchPrint
				.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);

		// verify LastSaveDate DESC Sorting order of Downloaded File is As Expected
		boolean result = baseClass.compareListViaContainsTrimSpace(actualFileName, docNameList);
		baseClass.printResutInReport(result, "Downloaded FileNames Sorted as per LastSaveDate DESC order ",
				"Sorting is not as Expected", "Pass");

		loginPage.logout();
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
//									 TODO: handle exception
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
}
