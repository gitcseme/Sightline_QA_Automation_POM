package testScriptsRegressionSprint27;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

import org.apache.poi.ss.formula.ptg.GreaterThanPtg;
import org.openqa.selenium.Dimension;
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
import net.lingala.zip4j.ZipFile;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.BatchPrintPage;
import pageFactory.DataSets;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.RedactionPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BatchPrintRegression_27 {

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

	@DataProvider(name = "Users")
	public Object[][] Users() {
		Object[][] users = { { Input.pa1userName, Input.pa1password },
				{ Input.rmu1userName, Input.rmu1password }, 
		};
		return users;
	}

	/**
	 * @Author Jeevitha
	 * @Description : To verify the details if user select option as 'Skip Excel
	 *              Files' on Exception File Types [RPMXCON-47813]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-47813", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void selectSkipExcelFileOnExceptionType(String username, String password) throws Exception {
		String SearchName = "SearchName" + Utility.dynamicNameAppender();

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-47813 Batch Print");
		baseClass.stepInfo("To verify the details if user select option as 'Skip Excel Files' on Exception File Types");

		// configure Excel query & save search
		session.basicMetaDataSearch(Input.docFileExt, null, "xlsx", null);
		session.saveSearch(SearchName);

		// Select source selection as search & select Print native from basis of
		// printing &
		// Navigate to Exception File type TAB
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab("Search", SearchName, false);
		batchPrint.fillingBasisForPrinting(true, true, null);
		batchPrint.navigateToNextPage(1);

		// select skip excel radio button & verify Incluse placeholder toggle , Insert
		// metadata link is displayed
		batchPrint.clickOnSkipExcelFilesAndDisablePlaceHolderToogle(false);
		baseClass.ValidateElement_Presence(batchPrint.getIncludePlaceHolderToogle(), "Include PlaceHolder Toggle");
		baseClass.ValidateElement_Presence(batchPrint.getExcel_InsertMetadata(), "Inset MetaData");

		// logout
		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description :To verify that the user will have an option to enable
	 *              placeholders for 'Other Exceptions Files' and Skip from printing
	 *              [RPMXCON-47477]
	 */
	@Test(description = "RPMXCON-47477", enabled = true, dataProvider = "Users", groups = { "regression" })
	public void verifySkipFromPrinting(String username, String password) throws Exception {
		String tagname = "NewTag" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id: RPMXCON-47477 Batch Print");
		baseClass.stepInfo(
				"To verify that the user will have an option to enable placeholders for 'Other Exceptions Files' and Skip from printing");

		// Login as User
		loginPage.loginToSightLine(username, password);

		// configure zip file query & bulk tag
		session.basicMetaDataSearch(Input.ingDocFileType, null, "zip", null);
		session.bulkTag(tagname);
		session.ViewInDocListWithOutPureHit();

		// Fetch DOCID's of Non-downloadable File.
		DocListPage doclist = new DocListPage(driver);
		int index = baseClass.getIndex(doclist.getColumnHeader(), Input.documentKey);
		baseClass.waitForElementCollection(doclist.GetColumnData(index));
		List<String> zipFileList = doclist.availableListofElementsForDocList(doclist.GetColumnData(index));

		// configure EXE file query & bulk tag in same tag
		baseClass.selectproject();
		session.basicMetaDataSearch(Input.sourceDocIdSearch, null, "ID00000174", null);
		session.bulkTagExisting(tagname);

		// Select TAG & Select Native in basis for printing
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab(Input.tag, tagname, true);
		batchPrint.fillingBasisForPrinting(true, true, null);
		batchPrint.navigateToNextPage(1);

		// Disable Exception File type Toggle & Navigate to Export Tab
		batchPrint.disableExceptionTypes();
		batchPrint.navigateToNextPage(1);
		baseClass.waitForElement(batchPrint.getEnableSlipSheetToggle());
		batchPrint.getEnableSlipSheetToggle().waitAndClick(10);
		batchPrint.navigateToNextPage(2);

		// Filling Export File Name as 'DOCID', select Sort by 'DOCID'
		batchPrint.fillingExportFormatPage(Input.documentKey, Input.documentKey, false, 20);

		// verify Skipped Document From Error Details Popup
		List<String> actualSkippedFile = batchPrint.clickErrorInfoAndReturn("Document Id", true);
		System.out.println(actualSkippedFile);
		baseClass.sortAndCompareList(zipFileList, actualSkippedFile, true, "Ascending",
				"Skipped Document are as expected & are not printed", "Skipped document are not as expected");

		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : To verify that user can select the Prior Production option and
	 *              view the details. [RPMXCON-47796]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-47796", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void verifyAllProductionDisplayedInBatchPrint(String username, String password) throws Exception {
		String Folder = "Folder" + Utility.dynamicNameAppender();

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-47796 Batch Print");
		baseClass.stepInfo("To verify that user can select the Prior Production option and view the details.");

		// create folder in Default SG
		tagsAndFolderPage.CreateFolder(Folder, Input.securityGroup);

		// configure query & Bulk Folder
		session.basicContentSearch(Input.testData1);
		session.bulkFolderExisting(Folder);

		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// create Production Set & Generate Production with TIFF
		String productionname = "P" + Utility.dynamicNameAppender();
		String productionSet = "P" + Utility.dynamicNameAppender();

		page.navigateToProductionPage();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.CreateProductionSets(productionSet);
		page.VerifyProductionSet(productionSet);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionwithBurnRedaction();
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, Folder, productionname,
				beginningBates);

		// Fetch Available Production in Created Production Set
		page.navigateToProductionPage();
		driver.waitForPageToBeReady();
		page.VerifyProductionSet(productionSet);
		baseClass.waitForElementCollection(page.getProductionItem());
		List<String> availableProduction = baseClass.availableListofElements(page.getProductionItem());

		// Select Folder in source selection
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab("Folder", Folder, true);

		// Fetch Available Production in basis for printing Tab & verify production
		// Displayed
		List<String> availableProdInBatchPrint = batchPrint.selectProductionFromBasisTab(productionSet, productionname,
				null);
		baseClass.sortAndCompareList(availableProduction, availableProdInBatchPrint, true, "Ascending",
				"All Production produced with TIFFs/PDFs files under the production set is Displayed.",
				"Expected Production is not dispalyed");

		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify PDF file should be generated for the production
	 *              documents when production contains the documents with same file
	 *              name [RPMXCON-47475]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-47475", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void verifyPdfFileForProdDoc(String username, String password) throws Exception {
		String tagName = "tagName" + Utility.dynamicNameAppender();

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-47475 Batch Print");
		baseClass.stepInfo(
				"Verify PDF file should be generated for the production documents when production contains the documents with same file name");

		// create folder in Default SG
		tagsAndFolderPage.CreateTagwithClassification(tagName, Input.tagNamePrev);

		// configure query & Bulk Folder
		session.basicContentSearch(Input.testData1);
		session.bulkTagExisting(tagName);

		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// Generate Production with PDF for multiple Pages
		String productionname = "P" + Utility.dynamicNameAppender();
		System.out.println("Creating production : " + productionname);
		page.navigateToProductionPage();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFWithMultiPage(tagName);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();

		// Verify Downloaded File name is same as production Name
		baseClass.waitUntilFileDownload();
		String downloadedFile = baseClass.GetLastModifiedFileName();
		baseClass.compareTextViaContains(downloadedFile, productionname,
				"Downloaded ZIP file is same as Craeted Producion Name", "Downloaded ZIP file name is not as expected");

		// Extract the downloaded zip file
		driver.waitForPageToBeReady();
		String name = page.getProduction().getText().trim();
		String extractFile = batchPrint.extractFile(name + ".zip");
		System.out.println(extractFile);
		driver.waitForPageToBeReady();
		List<String> fileName = page.verifyDownloadPDFFileCount(extractFile, false);

		// verify dowloaded has PDF file generated each for the document
		// & the document from the selected production set have same file name as
		// Production name
		for (int i = 0; i < fileName.size(); i++) {
			baseClass.validateFileFormat(fileName.get(i), "pdf");
			baseClass.passedStep("PDF file is generated each for the document : "+fileName.get(i));
		}

		// logout
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
}
