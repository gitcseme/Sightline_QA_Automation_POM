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
import pageFactory.DataSets;
import pageFactory.DocListPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BatchPrint_Phase2_Regression {

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
		Object[][] users = { { Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password }, };
		return users;
	}

	@DataProvider(name = "UserSaUDaUPaU")
	public Object[][] UserSaUDaUPaU() {
		Object[][] users = { { Input.sa1userName, Input.sa1password, "SA", "PA" },
				{ Input.sa1userName, Input.sa1password, "SA", "RMU" },
				{ Input.da1userName, Input.da1password, "SA", "PA" },
				{ Input.da1userName, Input.da1password, "SA", "RMU" }, };
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
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

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
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

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

	/**
	 * @Author Jeevitha
	 * @Description : Validate printing document with MEDIA file type
	 *              [RPMXCON-50730]
	 * @throws IOException
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-50730", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void verifyMediaFileType(String username, String password)
			throws InterruptedException, IOException, AWTException {
		String Tag = "TAG" + Utility.dynamicNameAppender();

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-50730 Batch Print");
		baseClass.stepInfo("Validate printing document with MEDIA file type");

		// configure query & perform Bulk Tag
		int purehit = session.basicMetaDataSearch(Input.docFileExt, null, "mpeg", null);
		session.bulkTag(Tag);

		boolean[] selectPdfRadio = { false, true };
		for (int i = 0; i < 2; i++) {
			boolean radio = selectPdfRadio[i];

			// Select TAG & Select Native in basis for printing
			batchPrint.navigateToBatchPrintPage();
			batchPrint.fillingSourceSelectionTab(Input.tag, Tag, true);
			batchPrint.fillingBasisForPrinting(true, true, null);
			batchPrint.navigateToNextPage(1);

			// verify Media file count in exception file Tab
			batchPrint.DisableMediaToggle(true, purehit);
			batchPrint.fillingExceptioanlFileTypeTab(false, Input.documentKey, null, true);

			// filling SlipSheet With metadata
			batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true, null);
			batchPrint.navigateToNextPage(1);

			// Generate Batch Print , When Radio is True it will select One PDF for all
			// when Radio is false it will select one PDF for each
			batchPrint.generateBatchPrint(Input.documentKey, Input.documentKey, radio);

			// verify Media Files are SKIPPED
			String status = null;
			if (batchPrint.getbackgroundDownLoadLink().isElementAvailable(10)) {
				status = batchPrint.getbackgroundDownLoadLink().getText();
			} else {
				driver.Navigate().refresh();
				baseClass.waitForElement(batchPrint.getbackgroundDownLoadLink());
				status = batchPrint.getbackgroundDownLoadLink().getText();
			}

			baseClass.compareTextViaContains(status, "Error Info", "Media files are Skipped as expected",
					"Media files are not Skipped ");

			// Verify Document Count Since we selected media files
			int index = baseClass.getIndex(batchPrint.getBackGroundPageHeader(), "Actual Docs");
			String actualDocValue = batchPrint.getValuesFromBackGroundPage(index).getText();

			softassert.assertEquals(Integer.parseInt(actualDocValue), 0);
			baseClass.passedStep("Actual Doc Count is 0 since Media files are SKIPPED");

			softassert.assertAll();
			driver.Navigate().refresh();
		}

		loginPage.logout();
	}

	/**
	 * @throws Exception
	 * @Author sowndarya
	 * @Description :Verify PDF file should be generated with branding for the
	 *              documents from selected production set with Begin Bates as
	 *              export file name [RPMXCON-47856]
	 */
	@Test(description = "RPMXCON-47856", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void verifyPDFWithBeginBates(String username, String password) throws Exception {

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-47856 Batch Print");
		baseClass.stepInfo(
				"Verify PDF file should be generated with branding for the documents from selected production set with Begin Bates as export file name");
		String tag = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String productionname = "P" + Utility.dynamicNameAppender();
		String slipsheetDD = "Create new slip sheets";

		tagsAndFolderPage.CreateTagwithClassification(tag, Input.tagNamePrev);

		session.basicContentSearch(Input.testData1);
		session.bulkTagExisting(tag);

		// Generate Production with TIFF
		ProductionPage page = new ProductionPage(driver);
		page.navigateToProductionPage();
		String beginningBates = page.getRandomNumber(2);
		System.out.println(beginningBates);
		System.out.println(productionname);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionWithBranding(tag, Input.searchString4, Input.tagNamePrev);
		page.slipSheetToggleEnable();
		page.fillingSlipSheetInTiffSection(Input.docId);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingSelectDocumentUsingTags(tag);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		// Select Tag & Production
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab(Input.tag, tag, true);
		batchPrint.fillingBasisForPrinting(false, true, productionname);
		batchPrint.navigateToNextPage(1);
		if (!batchPrint.getSlipSheetDD_prod().isElementAvailable(10)) {
			batchPrint.navigateToNextPage(1);
		}

		// filling SlipSheet With metadata
		batchPrint.selectDropdownFromSlipSheet_prod(slipsheetDD);
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true, null);
		if (!batchPrint.getSelectExportFileName().isElementAvailable(10)) {
			batchPrint.navigateToNextPage(1);
		}

		batchPrint.fillingExportFormatPage(Input.BeginBates, Input.documentKey, false, 20);

		// Download Batch Print File
		String fileName = batchPrint.DownloadBatchPrintFile();

		// extract zip file
		String extractedFile = batchPrint.extractFile(fileName);

		// verify Downloaded File Count ,filename and Format
		List<String> actualFileName = batchPrint
				.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);
		baseClass.compareTextViaContains(actualFileName.get(0), beginningBates, "pass", "Fail");
		driver.waitForPageToBeReady();
		baseClass.compareTextViaContains(actualFileName.get(0), Input.searchString3, "pass", "Fail");
		baseClass.passedStep(
				"Selected branding as per the location and Generated PDF file name as Begin Bates number is verified");
		;
	}

	/**
	 * @throws Exception
	 * @Author sowndarya
	 * @Description :Verify PDF file should be generated with the prior production
	 *              slipsheets fields for the production set with Begin Bates as
	 *              export file name [RPMXCON-47855]
	 */
	@Test(description = "RPMXCON-47855", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void verifySlipsheetFromPriorProduction(String username, String password) throws Exception {

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-47855 Batch Print");
		baseClass.stepInfo(
				"Verify PDF file should be generated with the prior production slipsheets fields for the production set with Begin Bates as export file name");
		String tag = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String productionname = "P" + Utility.dynamicNameAppender();
		String slipsheetDD = "Create new slip sheets";

		tagsAndFolderPage.CreateTag(tag, Input.securityGroup);

		session.basicContentSearch(Input.testData1);
		session.bulkTagExisting(tag);

		// Generate Production with TIFF
		ProductionPage page = new ProductionPage(driver);
		page.navigateToProductionPage();
		String beginningBates = page.getRandomNumber(2);
		System.out.println(beginningBates);
		System.out.println(productionname);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionwithBurnRedaction();
		baseClass.stepInfo("Enabling slipsheet toggle in production");
		page.slipSheetToggleEnable();
		page.fillingSlipSheetInTiffSection(Input.docId);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingSelectDocumentUsingTags(tag);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		// Select Tag & Production
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab(Input.tag, tag, true);
		batchPrint.fillingBasisForPrinting(false, true, productionname);
		batchPrint.navigateToNextPage(1);
		if (!batchPrint.getSlipSheetDD_prod().isElementAvailable(10)) {
			batchPrint.navigateToNextPage(1);
		}

		// filling SlipSheet With metadata
		batchPrint.selectDropdownFromSlipSheet_prod(slipsheetDD);
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true, null);
		if (!batchPrint.getSelectExportFileName().isElementAvailable(10)) {
			batchPrint.navigateToNextPage(1);
			baseClass.stepInfo("used prior slipsheet toggle from production");
		}
		batchPrint.fillingExportFormatPage(Input.BeginBates, Input.documentKey, false, 20);

		// Download Batch Print File
		String fileName = batchPrint.DownloadBatchPrintFile();

		// extract zip file
		String extractedFile = batchPrint.extractFile(fileName);

		// verify Downloaded File Count ,filename and Format
		List<String> actualFileName = batchPrint
				.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);
		baseClass.compareTextViaContains(actualFileName.get(0), beginningBates, "pass", "Fail");
		baseClass.passedStep(" Generated PDF file name as Begin Bates number is verified");
		;
	}

	/**
	 * @throws Exception
	 * @Author sowndarya
	 * @Description :To verify that 'Beg Bates' export file name displays only if
	 *              production set is selected [RPMXCON-47827]
	 */
	@Test(description = "RPMXCON-47827", enabled = true, groups = { "regression" })
	public void verifyBeginBatesInExportFileName() throws Exception {

		// Login As User
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		baseClass.stepInfo("Test case Id: RPMXCON-47827 Batch Print");
		baseClass.stepInfo("To verify that 'Beg Bates' export file name displays only if production set is selected");
		String tag = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String productionname = "P" + Utility.dynamicNameAppender();
		String slipsheetDD = "Create new slip sheets";
		String expected = "Begin Bates";

		tagsAndFolderPage.CreateTag(tag, Input.securityGroup);

		session.basicContentSearch(Input.testData1);
		session.bulkTagExisting(tag);

		// Generate Production with TIFF
		ProductionPage page = new ProductionPage(driver);
		page.navigateToProductionPage();
		String beginningBates = page.getRandomNumber(2);
		System.out.println(beginningBates);
		System.out.println(productionname);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionwithBurnRedaction();
		baseClass.stepInfo("Enabling slipsheet toggle in production");
		page.slipSheetToggleEnable();
		page.fillingSlipSheetInTiffSection(Input.docId);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingSelectDocumentUsingTags(tag);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		// Select Tag & Production
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab(Input.tag, tag, true);
		batchPrint.fillingBasisForPrinting(false, true, productionname);
		batchPrint.navigateToNextPage(1);
		if (!batchPrint.getSlipSheetDD_prod().isElementAvailable(10)) {
			batchPrint.navigateToNextPage(1);
		}

		// filling SlipSheet With metadata
		batchPrint.selectDropdownFromSlipSheet_prod(slipsheetDD);
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true, null);
		if (!batchPrint.getSelectExportFileName().isElementAvailable(10)) {
			batchPrint.navigateToNextPage(1);
			baseClass.stepInfo("used prior slipsheet toggle from production");
		}

		baseClass.waitForElement(batchPrint.getSelectExportFileName());
		String actual = batchPrint.getSelectExportFileName().getText();
		System.out.println(actual);

		if (actual.contains(expected)) {
			baseClass.passedStep("'Beg Bates' displayed in export file name drop down.");

		}
	}

	/**
	 * @Author Jeevitha
	 * @Description : To verify that RMU can view the fields in 'Slip Sheets' if it
	 *              is associated to the security group. [RPMXCON-47836]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-47836", enabled = true, groups = { "regression" })
	public void verifyFelidsInSlipsheet() throws Exception {
		String Folder = "Folder" + Utility.dynamicNameAppender();
		SecurityGroupsPage security = new SecurityGroupsPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-47836 Batch Print");
		baseClass.stepInfo(
				"To verify that RMU can view the fields in 'Slip Sheets' if it is associated to the security group.");

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		// fetch Fields associated with Security group
		security.selectSecurityGroupAndClickOnProjectFldLink(Input.securityGroup);
		baseClass.waitForElementCollection(security.getSelectdFieldsList());
		List<String> sgAssociatedFields = baseClass.availableListofElements(security.getSelectdFieldsList());

		// logout
		loginPage.logout();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// configure query and bulk folder
		session.basicContentSearch(Input.testData1);
		session.bulkFolder(Folder);

		// Select Folder & navigate to slipsheet tab
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab("Folder", Folder, true);
		batchPrint.fillingBasisForPrinting(true, true, null);
		batchPrint.navigateToNextPage(1);
		batchPrint.fillingExceptioanlFileTypeTab(false, Input.documentKey, null, true);

		// fetch avaialble fields in slipsheet tab
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		baseClass.waitForElementCollection(batchPrint.getSlipsheetsFields());
		List<String> slipsheetFields = baseClass.availableListofElements(batchPrint.getSlipsheetsFields());

		// verify All the fields associated with Security group is displayed
		baseClass.sortAndCompareList(sgAssociatedFields, slipsheetFields, true, "Ascending",
				"Fields associated to the security group is Displayed",
				"Fields associated to the security group is not Displayed");

		// logout
		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify user can on/off the 'Include Applied Redactions' from
	 *              Branding and Redactions tab [RPMXCON-47828]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-47828", enabled = true, groups = { "regression" })
	public void verifyUserCanOnAndOffRedactionToggle() throws Exception {
		String Folder = "Folder" + Utility.dynamicNameAppender();
		SecurityGroupsPage security = new SecurityGroupsPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-47828 Batch Print");
		baseClass.stepInfo("Verify user can on/off the 'Include Applied Redactions' from Branding and Redactions tab");

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// configure query and bulk folder
		session.basicContentSearch(Input.testData1);
		session.bulkFolder(Folder);

		// Select Folder & navigate to slipsheet tab
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab("Folder", Folder, true);
		batchPrint.fillingBasisForPrinting(true, true, null);
		batchPrint.navigateToNextPage(1);
		batchPrint.fillingExceptioanlFileTypeTab(false, Input.documentKey, null, true);

		// filling SlipSheet With metadata
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true, null);

		// OFF Include Applied Redaction toggle
		batchPrint.verifyBrandingAndReadctTab(false, false, null, false);

		// ON Include Applied Redaction toggle & Configure the positions & verify
		// location are fixed in selected position
		batchPrint.verifyBrandingAndReadctTab(true, true, Input.searchString1, false);

		// logout
		loginPage.logout();
	}

	/**
	 * @throws Exception
	 * @Author sowndarya
	 * @Description :Verify after impersonation native should be printed as per
	 *              selected option from analysis tab [RPMXCON-47470]
	 */
	@Test(description = "RPMXCON-47470", enabled = true, dataProvider = "UserSaUDaUPaU", groups = { "regression" })
	public void verifyNativeFromAnalysisTabAfterImpersonation(String username, String password, String fromRole,
			String toRole) throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-47470 Batch Print");
		baseClass.stepInfo(
				"Verify after impersonation native should be printed as per selected option from analysis tab");

		String slipsheetDD = "Create new slip sheets";
		int count = 7;

		// Login via User
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("Loggedin As : " + fromRole);
		// Pre-requesties
		baseClass.rolesToImp(fromRole, toRole);

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
		System.out.println("Trranslated doc :" + translationDocCount);

		baseClass.waitForElement(batchPrint.getRequestedDocCountInAnalysisPage());
		String completeText = batchPrint.getRequestedDocCountInAnalysisPage().getText();
		String[] reqDocCount = completeText.split(" ");
		System.out.println("file count : " + reqDocCount[4]);
		int docToPrint = Integer.parseInt(reqDocCount[4]);

		// verifying selected documents and requested documents are having same count
		baseClass.digitCompareEquals(count, docToPrint,
				"selected documents and requested documents are having same count",
				"selected documents and requested documents are not having same count");

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
		List<String> list = batchPrint
				.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);

		int downloadedDocCount = list.size();
		System.out.println(downloadedDocCount);

		// verifying translation documents are excluded
		int translationExcludedDoc = docToPrint - Integer.parseInt(translationDocCount);
		baseClass.digitCompareEquals(translationExcludedDoc, downloadedDocCount,
				"verifying translation documents and downloaded documents are having same count",
				"verifying translation documents and downloaded documents are not having same count");

		loginPage.logout();
	}

	/**
	 * @throws Exception
	 * @Author sowndarya
	 * @Description :Verify native should be printed when 'Ignore these documents
	 *              from print request.' is selected from analysis tab
	 *              [RPMXCON-47469]
	 */
	@Test(description = "RPMXCON-47469", enabled = true, dataProvider = "Users", groups = { "regression" })
	public void verifyNativeFromAnalysisTab(String username, String password) throws Exception {

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
		System.out.println("Trranslated doc :" + translationDocCount);

		baseClass.waitForElement(batchPrint.getRequestedDocCountInAnalysisPage());
		String completeText = batchPrint.getRequestedDocCountInAnalysisPage().getText();
		String[] reqDocCount = completeText.split(" ");
		System.out.println("file count : " + reqDocCount[4]);
		int docToPrint = Integer.parseInt(reqDocCount[4]);

		// verifying selected documents and requested documents are having same count
		baseClass.digitCompareEquals(count, docToPrint,
				"selected documents and requested documents are having same count",
				"selected documents and requested documents are not having same count");

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
		List<String> list = batchPrint
				.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);

		int downloadedDocCount = list.size();
		System.out.println(downloadedDocCount);

		// verifying translation documents excluded
		int translationExcludedDoc = docToPrint - Integer.parseInt(translationDocCount);
		baseClass.digitCompareEquals(translationExcludedDoc, downloadedDocCount,
				"verifying translation documents and downloaded documents are having same count",
				"verifying translation documents and downloaded documents are not having same count");

		loginPage.logout();
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
			baseClass.compareTextViaContains(list.get(0), docId, "Native file is downloaded",
					"Native file is downloaded");
		}
		loginPage.logout();
	}

	/**
	 * @Author sowndarya
	 * @Description :Validate printing an EXCEL file type and doesn't have it's
	 *              Native or text file [RPMXCON-50733]
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

		// configure query for native and text file & view in doclist
		session.basicMetaDataSearch(Input.sourceDocIdSearch, null, Input.TextSourceDocId, null);
		int index = baseClass.getIndex(session.getDetailsTable(), "DOCID");
		String docId = session.getValueFromTable(index).getText();
		System.out.println(docId);
		session.bulkTag(Tag);

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
			baseClass.compareTextViaContains(list.get(0), docId,
					"Downloaded documents doesn't have native and text files",
					"Downloaded documents have native and text files");
		}
		loginPage.logout();
	}

	/**
	 * @Author sowndarya
	 * @Description :Validate printing an EXCEL file type and has text file but not
	 *              it's native [RPMXCON-50732]
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

			// extract Zip fileCommitting :
			String extractedFile = batchPrint.extractFile(fileName);

			// verify Downloaded File Count and Format
			List<String> list = batchPrint
					.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);
			baseClass.compareTextViaContains(list.get(0), docId, "Text file is downloaded",
					"Text file is not downloaded");
		}
		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Validate Batch Print Sorting docs by DocID [Prior Productions
	 *              (TIFFs/PDFs)]with one PDF for all docs in ascending order
	 *              [RPMXCON-49194]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-49194", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void validateSortProdByDocId(String username, String password) throws Exception {
		String Folder = "Folder" + Utility.dynamicNameAppender();
		String slipsheetDD = "Create new slip sheets";
		DocListPage doclist = new DocListPage(driver);

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-49194 Batch Print");
		baseClass.stepInfo(
				"Validate Batch Print Sorting docs by DocID [Prior Productions (TIFFs/PDFs)]with one PDF for all docs in ascending order");

		// create folder in Default SG
		tagsAndFolderPage.CreateFolder(Folder, Input.securityGroup);

		// configure query & view in doclist
		session.basicContentSearch(Input.testData1);
		session.bulkFolderExisting(Folder);
		session.ViewInDocList();

		// verify DOCFILENAME column is present & add the column if it is not available
		driver.waitForPageToBeReady();
		doclist.verifyAndAddColumn(Input.docFileName);

		// Sort DocID column in ASCending For Final Result Verification
		driver.waitForPageToBeReady();
		doclist.sortColumn(true, Input.documentKey, true);

		// Get the expected sorting docid List For Final Result Verification
		int index = baseClass.getIndex(doclist.getColumnHeader(), Input.documentKey);
		baseClass.waitForElementCollection(doclist.GetColumnData(index));
		List<String> actualdocIdList = doclist.availableListofElementsForDocList(doclist.GetColumnData(index));

		// Remove not downloadable & Null Docid's from list For Final Result
		// Verification
		List<String> docIDList = doclist.addDocsToListOfOnlyDownloadableFormat(actualdocIdList);

		// Get The first DocFileName after sorting to verify downloaded file name For
		// Final Result Verification
		int docfileNameIndex = baseClass.getIndex(doclist.getColumnHeader(), Input.docFileName);
		baseClass.waitForElement(doclist.getColumValue(docfileNameIndex));
		String docfileName = doclist.getColumValue(docfileNameIndex).getText();

		// Generate Production with TIFF
		String productionname = page.preRequisiteGenerateProduction(Folder);

		// Select Folder in source selection & Production in basis for print
		batchPrint.navigateToBatchPrintPage();
		driver.waitForPageToBeReady();
		batchPrint.fillingSourceSelectionTab("Folder", Folder, true);
		batchPrint.fillingBasisForPrinting(false, true, productionname);
		batchPrint.navigateToNextPage(1);

		// filling SlipSheet With metadata
		batchPrint.selectDropdownFromSlipSheet_prod(slipsheetDD);
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true, null);

		// Filling Export File Name as 'DOCFILENAME', select Sort by 'DOCID' In
		// "ASC" Order And select [One PDF for All doc]
		batchPrint.selectSortingFromExportPage("ASC");
		batchPrint.fillingExportFormatPage(Input.docFileName, Input.documentKey, true, 20);

		// Download Batch Print File
		String fileName = batchPrint.DownloadBatchPrintFile();

		// extract zip file
		String extractedFile = batchPrint.extractFile(fileName);

		// verify Downloaded File Count ,filename and Format
		List<String> actualFileName = batchPrint
				.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);

		// verify whether downloaded filename is DOCFIELNAME & is as expected
		baseClass.waitTime(3);
		baseClass.compareTextViaContains(actualFileName.get(0), docfileName,
				"Downloaded FileName is as expected : " + docfileName, "Downloaded filename is not as expected");

		// Read the PDF FILE And get the Order of downloaded documents DOCID
		List<String> downloadedsortedDocID = batchPrint.verifyDownloadedPDfFileOrder(
				Input.fileDownloadLocation + "\\" + extractedFile + "\\" + actualFileName.get(0));

		// verify the Documents sorting option
		boolean result = baseClass.compareListViaContains(downloadedsortedDocID, docIDList);
		String passMsg = "Downloaded file is Sorted in ASC Of DOCID : " + docIDList;
		baseClass.printResutInReport(result, passMsg, "Sorting is not as Expected", "Pass");
		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : To verify that Meta Data should be displayed if clicks on
	 *              Insert Meta Data link [RPMXCON-47802]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-47802", enabled = true, groups = { "regression" })
	public void verifyMetadataFields() throws Exception {
		String SearchName = "Search" + Utility.dynamicNameAppender();
		SecurityGroupsPage security = new SecurityGroupsPage(driver);

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		baseClass.stepInfo("Test case Id: RPMXCON-47802 Batch Print");
		baseClass.stepInfo("To verify that Meta Data should be displayed if clicks on Insert Meta Data link");

		// fetch Fields associated with Security group
		security.selectSecurityGroupAndClickOnProjectFldLink(Input.securityGroup);
		baseClass.waitForElementCollection(security.getSelectdFieldsList());
		List<String> sgAssociatedFields = baseClass.availableListofElements(security.getSelectdFieldsList());

		// logout
		loginPage.logout();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// configure query & view in doclist
		session.basicContentSearch(Input.testData1);
		session.saveSearch(SearchName);

		// Select search & Native from basis for printing
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab("Search", SearchName, false);
		batchPrint.fillingBasisForPrinting(true, true, null);
		batchPrint.navigateToNextPage(2);

		// Toggle OFF the 'Enable Slip Sheets'
		driver.waitForPageToBeReady();
		baseClass.waitForElement(batchPrint.getEnableSlipSheetToggle());
		batchPrint.getEnableSlipSheetToggle().waitAndClick(10);

		// fetch the Metadata Fields available in Branding & Redaction Tab
		batchPrint.navigateToNextPage(1);
		driver.waitForPageToBeReady();
		batchPrint.verifyCurrentTab("Branding and Redactions");
		List<String> availableMetadatainBrandingTab = batchPrint.fillBrandingSelectedPosition("CENTER", false, null,
				true, Input.documentKey, true);

		// verify all the fields associated with security group available in insert
		// metadata dropdown in Branding tab
		baseClass.listCompareEquals(sgAssociatedFields, availableMetadatainBrandingTab,
				"All The Associated Fields is available in Branding TAb",
				"All The Associated Fields is Not available in Branding Tab");

		// logout
		loginPage.logout();
	}

	/**
	 * @throws Exception
	 * @Author Jeevitha
	 * @Description : Verify that PDF should be generated of Batch Print with
	 *              documents having same file name as per selected option 'One PDF
	 *              for each document', ExportFileName as 'DocFileName' and Sort By
	 *              'DocID' [RPMXCON-58916]
	 */
	@Test(description = "RPMXCON-58916", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void verifPDFForDocWithSameName(String username, String password) throws Exception {
		String Folder = "Folder" + Utility.dynamicNameAppender();

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-58916 Batch Print");
		baseClass.stepInfo(
				"Verify that PDF should be generated of Batch Print with documents having same file name as per selected option 'One PDF for each document', ExportFileName as 'DocFileName' and Sort By 'DocID'");

		// Configure query of Documents with same file name with uppercase, lowecase,
		// combination of upper and lower case & perform Bulk FOLDER
		session.basicMetaDataSearch(Input.docFileName, null, "confidential", null);
		session.addPureHit();
		session.addNewSearch();
		session.newMetaDataSearchInBasicSearch(Input.docFileName, "CONFIDENTIAL");
		session.addPureHit();
		session.addNewSearch();
		session.newMetaDataSearchInBasicSearch(Input.docFileName, "ConFidenTIAL");
		session.addPureHit();
		session.ViewInDocListWithOutPureHit();

		doclist.documentSelectionIncludeChildDoc(6);
		doclist.bulkFolderInDocListPage(Folder);

		// Select Folder from Source selection Tab
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab("Folder", Folder, true);

		// select Native Viewable file variant
		batchPrint.fillingBasisForPrinting(true, true, null);
		batchPrint.navigateToNextPage(1);

		// Verify user will be on 'Exception File Types' tab & select any metadata
		batchPrint.fillingExceptioanlFileTypeTab(false, Input.documentKey, null, true);

		// filling SlipSheet With metadata & click Next
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true, null);

		// keep the toggle ON in branding TAB & Navigate to export page
		batchPrint.navigateToNextPage(1);

		// Keep All Default options As it is I.e.., Except Export by DOCFILENAME as
		// DOCID, Sort BY DOCID
		// , ASC Order

		// Verify Success Message, BackgroundTask page & downloaded link In this Method
		// itself
		batchPrint.fillingExportFormatPage(Input.docFileName, Input.documentKey, false, 20);

		// Download Batch Print File
		String fileName = batchPrint.DownloadBatchPrintFile();

		// extract zip file
		String extractedFile = batchPrint.extractFile(fileName);

		// verify Downloaded File Count ,filename and Format
		List<String> actualFileName = batchPrint
				.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);

		// verify Documents Exported Correctly By selecting One PDF for Each
		softassert.assertNotEquals(actualFileName.size(), 1);
		baseClass.passedStep("Document Successfully exported as 'One PDF For Each'");
		softassert.assertAll();

		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Validate Batch Print Sorting docs by LastSaveDate [Prior
	 *              Productions (TIFFs/PDFs)]with one PDF for all docs in ascending
	 *              order [RPMXCON-49185]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-49185", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void validateSortProdByLastSaveDate(String username, String password) throws Exception {
		String Tag = "TAG" + Utility.dynamicNameAppender();
		String Folder = "Folder" + Utility.dynamicNameAppender();
		String columnName = "LastSaveDate";
		String slipsheetDD = "Create new slip sheets";
		DocListPage doclist = new DocListPage(driver);

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-49185 Batch Print");
		baseClass.stepInfo(
				"Validate Batch Print Sorting docs by LastSaveDate [Prior Productions (TIFFs/PDFs)]with one PDF for all docs in ascending order");

		// create folder in Default SG
		tagsAndFolderPage.CreateFolder(Folder, Input.securityGroup);

		// configure query & view in doclist
		session.basicContentSearch(Input.searchStringStar);
		session.ViewInDocList();

		// select Few docs with LastSaveDate
		driver.waitForPageToBeReady();
		doclist.verifyAndAddColumn(columnName);
		doclist.sortColumn(true, columnName, false);
		doclist.documentSelectionIncludeChildDoc(4);

		// bulk tag selected docs
		doclist.addNewBulkTag(Tag);
		tagsAndFolderPage.ViewinDocListthrTag(Tag);

		// bulk folder same doc & sort LastSaveDate Column in Ascending Order for final
		// verification
		driver.waitForPageToBeReady();
		doclist.selectAllDocs();
		doclist.bulkFolderExisting(Folder);
		doclist.sortColumn(true, columnName, true);

		// Expected Downloaded DOCID list ,in ASC Order of LastSaveDate for final
		// verification
		int index = baseClass.getIndex(doclist.getColumnHeader(), Input.documentKey);
		baseClass.waitForElementCollection(doclist.GetColumnData(index));
		List<String> expectedDocSorting = doclist.availableListofElementsForDocList(doclist.GetColumnData(index));

		// Generate Production with TIFF
		String productionname = page.preRequisiteGenerateProduction(Folder);

		// Select Tag & Production
		batchPrint.navigateToBatchPrintPage();
		driver.waitForPageToBeReady();
		batchPrint.fillingSourceSelectionTab(Input.tag, Tag, true);
		batchPrint.fillingBasisForPrinting(false, true, productionname);
		batchPrint.navigateToNextPage(1);

		// filling SlipSheet With metadata as DOCID
		batchPrint.selectDropdownFromSlipSheet_prod(slipsheetDD);
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true, null);

		// select Export by as : 'DOCID', select Sort by 'LastSaveDate' In
		// "ASC" Order And [One PDF for All doc]
		batchPrint.selectSortingFromExportPage("ASC");
		batchPrint.fillingExportFormatPage(Input.documentKey, columnName, true, 20);

		// Download Batch Print File
		String fileName = batchPrint.DownloadBatchPrintFile();

		// extract zip file
		String extractedFile = batchPrint.extractFile(fileName);

		// verify Downloaded File Count ,filename and Format
		List<String> actualFileName = batchPrint
				.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);

		// verify whether downloaded filename is DOCID & is as expected
		baseClass.waitTime(3);
		baseClass.compareTextViaContains(actualFileName.get(0), expectedDocSorting.get(0),
				"Downloaded FileName is as expected : " + actualFileName.get(0),
				"Downloaded filename is not as expected");

		// Read the PDF FILE And get the Order of downloaded documents DOCID
		List<String> actualDownloadedSorting = batchPrint.verifyDownloadedPDfFileOrder(
				Input.fileDownloadLocation + "\\" + extractedFile + "\\" + actualFileName.get(0));

		// compare both expected & actual sorting & verify the Documents sorting option
		boolean result = baseClass.compareListViaContainsTrimSpace(actualDownloadedSorting, expectedDocSorting);
		String passMsg = "Downloaded file is Sorted in ASC Of LastSaveDate : " + expectedDocSorting;
		baseClass.printResutInReport(result, passMsg, "Sorting is not as Expected", "Pass");
		loginPage.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description : Validate Batch Print Sorting docs by CustodianName [Prior
	 *              Productions (TIFFs/PDFs)]with one PDF for all docs in ascending
	 *              order [RPMXCON-49191]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-49191", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void validateSortProdByCustodian(String username, String password) throws Exception {
		String Tag = "TAG" + Utility.dynamicNameAppender();
		String Folder = "Folder" + Utility.dynamicNameAppender();
		String columnName = "CustodianName";
		String slipsheetDD = "Create new slip sheets";
		DocListPage doclist = new DocListPage(driver);

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-49191 Batch Print");
		baseClass.stepInfo(
				"Validate Batch Print Sorting docs by CustodianName [Prior Productions (TIFFs/PDFs)]with one PDF for all docs in ascending order");

		// create folder in Default SG
		tagsAndFolderPage.CreateFolder(Folder, Input.securityGroup);

		// configure query & view in doclist
		session.basicContentSearch(Input.searchStringStar);
		session.ViewInDocList();

		// select Few docs with & without CustodianName
		driver.waitForPageToBeReady();
		doclist.verifyAndAddColumn(columnName);
		doclist.sortColumn(true, columnName, true);
		doclist.documentSelectionIncludeChildDoc(3);
		driver.waitForPageToBeReady();
		doclist.sortColumn(true, columnName, false);
		doclist.documentSelectionIncludeChildDoc(3);

		// bulk tag selected docs
		driver.waitForPageToBeReady();
		doclist.addNewBulkTag(Tag);
		tagsAndFolderPage.ViewinDocListthrTag(Tag);

		// bulk folder same doc & sort Custodian Column in Ascending Order for final
		// verification
		driver.waitForPageToBeReady();
		doclist.selectAllDocs();
		driver.waitForPageToBeReady();
		doclist.bulkFolderExisting(Folder);
		doclist.sortColumn(true, columnName, true);

		// Expected Downloaded DOCID list ,in ASC Order of CustodianName
		int index = baseClass.getIndex(doclist.getColumnHeader(), Input.documentKey);
		baseClass.waitForElementCollection(doclist.GetColumnData(index));
		List<String> expectedDocSorting = doclist.availableListofElementsForDocList(doclist.GetColumnData(index));

		// Generate Production with TIFF
		String productionname = page.preRequisiteGenerateProduction(Folder);

		// Select Tag & Production
		batchPrint.navigateToBatchPrintPage();
		driver.waitForPageToBeReady();
		batchPrint.fillingSourceSelectionTab(Input.tag, Tag, true);
		batchPrint.fillingBasisForPrinting(false, true, productionname);
		batchPrint.navigateToNextPage(1);

		// filling SlipSheet With metadata
		batchPrint.selectDropdownFromSlipSheet_prod(slipsheetDD);
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true, null);

		// select Export by as: 'DOCID', select Sort by 'CustodianName' In
		// "ASC" Order And [One PDF for All doc]
		batchPrint.selectSortingFromExportPage("ASC");
		batchPrint.fillingExportFormatPage(Input.documentKey, columnName, true, 20);

		// Download Batch Print File
		String fileName = batchPrint.DownloadBatchPrintFile();

		// extract zip file
		String extractedFile = batchPrint.extractFile(fileName);

		// verify Downloaded File Count ,filename and Format
		List<String> actualFileName = batchPrint
				.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);

		// verify whether downloaded filename is DOCID & is as expected
		baseClass.waitTime(3);
		baseClass.compareTextViaContains(actualFileName.get(0), expectedDocSorting.get(0),
				"Downloaded FileName is as expected : " + actualFileName.get(0),
				"Downloaded filename is not as expected");

		// Read the PDF FILE And get the Order of downloaded documents DOCID
		List<String> actualDownloadedSorting = batchPrint.verifyDownloadedPDfFileOrder(
				Input.fileDownloadLocation + "\\" + extractedFile + "\\" + actualFileName.get(0));

		// compare both expected & actual sorting & verify the Documents sorting option
		boolean result = baseClass.compareListViaContainsTrimSpace(actualDownloadedSorting, expectedDocSorting);
		String passMsg = "Downloaded file is Sorted in ASC Of CustodianName : " + expectedDocSorting;
		baseClass.printResutInReport(result, passMsg, "Sorting is not as Expected", "Pass");

		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Validate Batch Print Sorting docs by DocDate [Prior
	 *              Productions (TIFFs/PDFs)]with one PDF for all docs in ascending
	 *              order [RPMXCON-49190]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-49190", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void validateSortProdByDocDate(String username, String password) throws Exception {
		String Tag = "TAG" + Utility.dynamicNameAppender();
		String Folder = "Folder" + Utility.dynamicNameAppender();
		String columnName = "DocDate";
		String slipsheetDD = "Create new slip sheets";
		DocListPage doclist = new DocListPage(driver);

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-49190 Batch Print");
		baseClass.stepInfo(
				"Validate Batch Print Sorting docs by DocDate [Prior Productions (TIFFs/PDFs)]with one PDF for all docs in ascending order");

		// create folder in Default SG
		tagsAndFolderPage.CreateFolder(Folder, Input.securityGroup);

		// configure query & view in doclist
		session.basicContentSearch(Input.searchStringStar);
		session.ViewInDocList();

		// select Few docs with DocDate
		driver.waitForPageToBeReady();
		doclist.verifyAndAddColumn(columnName);
		doclist.sortColumn(true, columnName, false);
		doclist.documentSelectionIncludeChildDoc(4);

		// bulk tag selected docs
		doclist.addNewBulkTag(Tag);
		tagsAndFolderPage.ViewinDocListthrTag(Tag);

		// bulk folder & sort in Ascending Order of DocDate
		driver.waitForPageToBeReady();
		doclist.selectAllDocs();
		doclist.bulkFolderExisting(Folder);
		doclist.sortColumn(true, columnName, true);

		// fetch Expected Downloaded DOCID list ,ASC Order of DocDate for Final Result
		// Verification
		int index = baseClass.getIndex(doclist.getColumnHeader(), Input.documentKey);
		baseClass.waitForElementCollection(doclist.GetColumnData(index));
		List<String> expectedDocSorting = doclist.availableListofElementsForDocList(doclist.GetColumnData(index));

		// Get The first DocFileName after sorting to verify downloaded file name For
		// Final Result Verification
		int docfileNameIndex = baseClass.getIndex(doclist.getColumnHeader(), Input.docFileName);
		baseClass.waitForElement(doclist.getColumValue(docfileNameIndex));
		String docfileName = doclist.getColumValue(docfileNameIndex).getText();

		// Generate Production with TIFF
		String productionname = page.preRequisiteGenerateProduction(Folder);

		// Select Tag & Production
		batchPrint.navigateToBatchPrintPage();
		driver.waitForPageToBeReady();
		batchPrint.fillingSourceSelectionTab(Input.tag, Tag, true);
		batchPrint.fillingBasisForPrinting(false, true, productionname);
		batchPrint.navigateToNextPage(1);

		// filling SlipSheet With metadata
		batchPrint.selectDropdownFromSlipSheet_prod(slipsheetDD);
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true, null);

		// select Export by as: 'DOCFILENAME', select Sort by 'DocDate' In
		// "ASC" Order And [One PDF for All doc]
		batchPrint.selectSortingFromExportPage("ASC");
		batchPrint.fillingExportFormatPage(Input.docFileName, columnName, true, 20);

		// Download Batch Print File
		String fileName = batchPrint.DownloadBatchPrintFile();

		// extract zip file
		String extractedFile = batchPrint.extractFile(fileName);

		// verify Downloaded File Count ,filename and Format
		List<String> actualFileName = batchPrint
				.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);

		// verify whether downloaded filename is DOCFILENAME & is as expected
		baseClass.waitTime(3);
		baseClass.compareTextViaContains(actualFileName.get(0), docfileName,
				"Downloaded FileName is as expected : " + actualFileName.get(0),
				"Downloaded filename is not as expected");

		// Read the PDF FILE And get the Order of downloaded documents DOCID
		List<String> actualDownloadedSorting = batchPrint.verifyDownloadedPDfFileOrder(
				Input.fileDownloadLocation + "\\" + extractedFile + "\\" + actualFileName.get(0));

		// verify DocDate ASC Sorting order of Downloaded file ID is as
		// Expected
		boolean result = baseClass.compareListViaContainsTrimSpace(actualDownloadedSorting, expectedDocSorting);
		String passMsg = "Downloaded file is Sorted in ASC Of LastSaveDate : " + expectedDocSorting;
		baseClass.printResutInReport(result, passMsg, "Sorting is not as Expected", "Pass");
		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that 'My Shared' it should be removed from Batch Print
	 *              [RPMXCON-47800]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-47800", enabled = true, groups = { "regression" })
	public void validateMySharedIsRemoved() throws Exception {

		// Login As PA User
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		baseClass.stepInfo("Test case Id: RPMXCON-47800 Batch Print");
		baseClass.stepInfo("Verify that 'My Shared' it should be removed from Batch Print");

		// Click Select Searched & verify My Shared searches is not available
		batchPrint.navigateToBatchPrintPage();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(batchPrint.getSelectRadioButton());
		batchPrint.getSelectRadioButton().waitAndClick(10);
		baseClass.stepInfo("Clicked Select searches Radio Button");
		driver.waitForPageToBeReady();
		baseClass.ValidateElement_Absence(batchPrint.getSearchNodeExpand("My Shared"),
				"My Shared searches is not Available");

		// logout
		loginPage.logout();
	}
}
