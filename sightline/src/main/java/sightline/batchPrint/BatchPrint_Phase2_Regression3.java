package sightline.batchPrint;
import java.awt.AWTException;
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
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;


public class BatchPrint_Phase2_Regression3 {
	
	SoftAssert softassert;
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	BatchPrintPage batchPrint;
	SessionSearch session;
	TagsAndFoldersPage tagsAndFolderPage;
	ProductionPage page;
	DocListPage doclist;
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
		doclist = new DocListPage(driver);
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
		System.out.println("**Executed Batch print_Phase2_Regression .**");
	}

	@DataProvider(name = "Users")
	public Object[][] Users() {
		Object[][] users = { { Input.pa5userName, Input.pa5password }, { Input.rmu5userName, Input.rmu5password }, };
		return users;
	}

	@DataProvider(name = "UserSaUDaUPaU")
	public Object[][] UserSaUDaUPaU() {
		Object[][] users = {
				{ Input.sa1userName, Input.sa1password, "SA", "PA" },
				{ Input.sa1userName, Input.sa1password, "SA", "RMU" },
				{ Input.da1userName, Input.da1password, "SA", "PA" },
				{ Input.da1userName, Input.da1password, "SA", "RMU" },
				};
		return users;
	}
	
	
	/**
	 * @Author Jeevitha
	 * @Description : To verify that Batch print functionality is working correctly
	 *              and properly if user select 'Prior Productions (TIFFs/PDFs)' [RPMXCON-48191]
	 * @throws IOException
	 */
	@Test(description = "RPMXCON-48191", enabled = true, groups = { "regression" })
	public void validateBatchPrintWithPriorProduction() throws InterruptedException, IOException {
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagName = "Tag" + Utility.dynamicNameAppender();
		String savesearch = "Search" + Utility.dynamicNameAppender();
		String slipsheetDD = "Create new slip sheets";

		SessionSearch search = new SessionSearch(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		// Login As User
		loginPage.loginToSightLine(Input.pa5userName, Input.pa5password);

		baseClass.stepInfo("Test case Id: RPMXCON-48191 Batch Print");
		baseClass.stepInfo(
				"To verify that Batch print functionality is working correctly and properly if user select 'Prior Productions (TIFFs/PDFs)'");

		// Create tag and folder
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagName, Input.tagNamePrev);

		// Bulk Tag
		search.basicMetaDataSearch(Input.metaDataName, null, Input.custodianName_Andrew, null);
		search.saveSearch(savesearch);
		search.bulkFolderExisting(foldername);

		// Generate Production
		String productionname = "P" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		System.out.println(productionname);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionwithBurnRedaction();
		page.slipSheetToggleEnable();
		page.getSlipCalculatedTabSelection().ScrollTo();
		page.availableFieldSelection(Input.batesNumber);
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);

		// Select Search
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab("Search", savesearch, false);

		// select production
		batchPrint.fillingBasisForPrinting(false, true, productionname);
		batchPrint.navigateToNextPage(1);
		if (!batchPrint.getSlipSheetDD_prod().isElementAvailable(10) ) {
			batchPrint.navigateToNextPage(1);
		}
		
		// fill slipsheet tab
		batchPrint.selectDropdownFromSlipSheet_prod(slipsheetDD);
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true, null);
		if (!batchPrint.getSelectExportFileName().isElementAvailable(10)) {
			batchPrint.navigateToNextPage(1);
		}
		
		// Filling Export File Name as 'DOCFileName', select Sort by 'DOCID'
		batchPrint.fillingExportFormatPage(Input.documentKey, Input.documentKey, false, 20);

		// Download Batch Print File
		String fileName = batchPrint.DownloadBatchPrintFile();

		// extract zip file
		String extractedFile = batchPrint.extractFile(fileName);

		// verify Downloaded File Count and Format
		batchPrint.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);

		loginPage.logout();

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
		
		batchPrint.getDownload().waitAndClick(10);
		batchPrint.getDownloadAllFiles().waitAndClick(10);
		
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
			baseClass.passedStep("PDF file is generated each for the document : " + fileName.get(i));
		}

		// logout
		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify PDF file should be generated at the displayed path of
	 *              batch print background process as per selected 'One PDF for all
	 *              documents' [RPMXCON-47832]
	 * @throws IOException
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-47832", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void validatePdfFileIsGeneratedForAsc(String username, String password)
			throws InterruptedException, IOException, AWTException {
		String Tag = "TAG" + Utility.dynamicNameAppender();

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-47832 Batch Print");
		baseClass.stepInfo(
				"Verify PDF file should be generated at the displayed path of batch print background process as per selected 'One PDF for all documents'");

		// configure query & perform Bulk Tag
		session.basicContentSearch(Input.testData1);
		session.bulkTag(Tag);

		// Select TAG & Select Native in basis for printing
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab(Input.tag, Tag, true);
		batchPrint.fillingBasisForPrinting(true, true, null);
		batchPrint.navigateToNextPage(1);
		batchPrint.fillingExceptioanlFileTypeTab(false, Input.documentKey, null, true);

		// filling SlipSheet With metadata
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true, null);
		batchPrint.navigateToNextPage(1);

		// select sorting "Asc" Order
		batchPrint.selectSortingFromExportPage("ASC");

		// Select Export File Name as 'DocID', select Sort by 'DocID' & verify Batch
		// print is completed & check file download link in background task page
		batchPrint.fillingExportFormatPage(Input.documentKey, Input.documentKey, true, 20);

		// Download Batch Print File
		String fileName = batchPrint.DownloadBatchPrintFile();

		// extract zip file
		String extractedFile = batchPrint.extractFile(fileName);

		// check the Downloaded file
		batchPrint.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);

		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify PDF file should be generated at the displayed path of
	 *              batch print background process as per selected 'One PDF for each
	 *              document' with Doc ID as selected in desc sort [RPMXCON-47833]
	 * @throws IOException
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-47833", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void validatePdfFileIsGeneratedAsDesc(String username, String password)
			throws InterruptedException, IOException, AWTException {
		String Tag = "TAG" + Utility.dynamicNameAppender();

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-47833 Batch Print");
		baseClass.stepInfo(
				"Verify PDF file should be generated at the displayed path of batch print background process as per selected 'One PDF for each document' with Doc ID as selected in desc sort");

		// configure query & perform Bulk Tag
		session.basicContentSearch(Input.testData1);
		session.bulkTag(Tag);

		// Select TAG & Select Native in basis for printing
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab(Input.tag, Tag, true);
		batchPrint.fillingBasisForPrinting(true, true, null);
		batchPrint.navigateToNextPage(1);
		batchPrint.fillingExceptioanlFileTypeTab(false, Input.documentKey, null, true);

		// filling SlipSheet With metadata
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true, null);
		batchPrint.navigateToNextPage(1);

		// select sorting "DESC" Order
		batchPrint.selectSortingFromExportPage("DESC");

		// Select Export File Name as 'DocID', select Sort by 'DocID' & verify Batch
		// print is completed & check file download link in background task page
		batchPrint.fillingExportFormatPage(Input.documentKey, Input.documentKey, true, 20);

		// Download Batch Print File
		String fileName = batchPrint.DownloadBatchPrintFile();

		// extract zip file
		String extractedFile = batchPrint.extractFile(fileName);

		// check the Downloaded file
		batchPrint.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);

		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify PDF file should be generated at the displayed path of
	 *              batch print background process as per selected 'One PDF for each
	 *              document' with File Name as selected in asc sort [RPMXCON-47834]
	 * @throws IOException
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-47834", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void validatePdfFileIsGeneratedDocfileForAsc(String username, String password)
			throws InterruptedException, IOException, AWTException {
		String Tag = "TAG" + Utility.dynamicNameAppender();

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-47834 Batch Print");
		baseClass.stepInfo(
				"Verify PDF file should be generated at the displayed path of batch print background process as per selected 'One PDF for each document' with File Name as selected in asc sort");

		// configure query & perform Bulk Tag
		session.basicContentSearch(Input.testData1);
		session.bulkTag(Tag);

		// Select TAG & Select Native in basis for printing
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab(Input.tag, Tag, true);
		batchPrint.fillingBasisForPrinting(true, true, null);
		batchPrint.navigateToNextPage(1);
		batchPrint.fillingExceptioanlFileTypeTab(false, Input.documentKey, null, true);

		// filling SlipSheet With metadata
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true, null);
		batchPrint.navigateToNextPage(1);

		// select sorting "Asc" Order
		batchPrint.selectSortingFromExportPage("ASC");

		// Select Export File Name as 'DocFileName', select Sort by 'DocFileName' &
		// select one Pdf for each document
		// verify Batchprint is completed & check file download link in background task
		// page
		batchPrint.fillingExportFormatPage(Input.docFileName, Input.docFileName, false, 20);

		// Download Batch Print File
		String fileName = batchPrint.DownloadBatchPrintFile();

		// extract zip file
		String extractedFile = batchPrint.extractFile(fileName);

		// check the Downloaded file
		batchPrint.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);

		loginPage.logout();
	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :To verify that Help is displayed on exception file
	 *              types..RPMXCON-47801
	 */

	@Test(description = "RPMXCON-47801", enabled = true, groups = { "regression" })
	public void verifyHelpDispalyedOnExceptionFileTypes() {

		String searchName = "savedSearch" + Utility.dynamicNameAppender();
		SavedSearch savedSearch = new SavedSearch(driver);

		// Login As User
		loginPage.loginToSightLine(Input.pa5userName, Input.pa5password);

		baseClass.stepInfo("Test case Id: RPMXCON-47801 Batch Print");
		baseClass.stepInfo("To verify that Help is displayed on exception file types.");

		// performing basic content search and saving the search Result
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName);

		// selecting the source in source selection tab and navigating to next tab
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab("Search", searchName, false);

		// navigating to Exception File Types Tab
		batchPrint.navigateToNextPage(2);

		// clicking Other Exception File Type Help Icon and verifying is that display
		// Other Exception File Type Help Block
		batchPrint.getOtherExceptionFileTypeHelpIcon().waitAndClick(5);
		baseClass.ValidateElement_Presence(batchPrint.getOtherExceptionFileTypeHelpBlock(),
				"Other Exception File Type Help Block");

		// clicking Media Files Help Icon and verifying is that display Media File Help
		// Block
		batchPrint.getMediaFilesHelpIcon().waitAndClick(5);
		baseClass.ValidateElement_Presence(batchPrint.getMediaFilesHelpBlock(), "Media File Help Block");

		// clicking Excel Files Help Icon and verifying is that display Excel File Help
		// Block
		batchPrint.getExcelFilesHelpIcon().waitAndClick(5);
		baseClass.ValidateElement_Presence(batchPrint.getExcelFilesHelpBlock(), "Excel File Help Block");

		baseClass.passedStep("Verified that Help is displayed for Exception File type, Media Files and Excel Files.");

		// deleting the savedSearch
		savedSearch.SaveSearchDelete(searchName);

		// Logout
		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : To verify that user can select Export file options
	 *              [RPMXCON-47826]
	 * @throws IOException
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-47826", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void verifyUserCanSelectExportFile(String username, String password)
			throws InterruptedException, IOException, AWTException {
		String Tag = "TAG" + Utility.dynamicNameAppender();
		String[] expectedList = { Input.documentKey, Input.docFileName };

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-47826 Batch Print");
		baseClass.stepInfo("To verify that user can select Export file options");

		// configure query & perform Bulk Tag
		session.basicContentSearch(Input.testData1);
		session.bulkTag(Tag);

		boolean[] selectPdfRadio = { false, true };
		for (int i = 0; i < 2; i++) {
			boolean radio = selectPdfRadio[i];

			// Select TAG & Select Native in basis for printing
			batchPrint.navigateToBatchPrintPage();
			batchPrint.fillingSourceSelectionTab(Input.tag, Tag, true);
			batchPrint.fillingBasisForPrinting(true, true, null);
			batchPrint.navigateToNextPage(1);
			batchPrint.fillingExceptioanlFileTypeTab(false, Input.documentKey, null, true);

			// filling SlipSheet With metadata
			batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true, null);
			batchPrint.navigateToNextPage(1);

			// verify Export file name dropdown
			List<String> exportddList = baseClass.availableListofElements(batchPrint.getExportFileNameDD());
			baseClass.compareArraywithDataList(expectedList, exportddList, true,
					"Export File Dropdown options are : " + exportddList,
					"Export File Dropdown options are not as expected");

			// select sorting "DESC" Order
			batchPrint.selectSortingFromExportPage("DESC");
			int initialBgCount = baseClass.initialBgCount();

			// When Radio is True it will select One PDF for all
			// when Radio is false it will select one PDF for each
			batchPrint.fillingExportFormatPage(Input.documentKey, Input.documentKey, radio, 20);

			// check for notification
			baseClass.checkNotificationCount(initialBgCount, 1);
			int afterBgCount = baseClass.initialBgCount();
			int valuee = Integer.max(initialBgCount, afterBgCount);
			baseClass.digitCompareEquals(valuee, afterBgCount, "Recieved Notification.",
					"Notification is not Displayed.");

			// Verify Actual Document Count
			int index = baseClass.getIndex(batchPrint.getBackGroundPageHeader(), "Actual Docs");
			String actualDocValue = batchPrint.getValuesFromBackGroundPage(index).getText();

			softassert.assertNotEquals(Integer.parseInt(actualDocValue), 0);
			baseClass.passedStep("Actual Doc Count is : " + actualDocValue);

			softassert.assertAll();
			driver.Navigate().refresh();
		}

		loginPage.logout();
	}


}
