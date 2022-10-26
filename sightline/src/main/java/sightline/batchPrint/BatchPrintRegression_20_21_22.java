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

public class BatchPrintRegression_20_21_22 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	BatchPrintPage batchPrint;
	SessionSearch session;
	TagsAndFoldersPage tagsAndFolderPage;
	ProductionPage page;

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
	}

	@DataProvider(name = "Users")
	public Object[][] Users() {
		Object[][] users = { { Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password }, };
		return users;
	}
	
	/**
	 * @Author Jeevitha
	 * @Description : Validate different Sorting option for Batch Print(Prior
	 *              Production) [RPMXCON-49159]
	 * @throws IOException
	 */
	@Test(description = "RPMXCON-49159", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void validateSortingOptionForBPProd(String username, String password)
			throws InterruptedException, IOException {
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagName = "Tag" + Utility.dynamicNameAppender();
		String[] expectedSortByList = { "DocID", "DocFileName", "MasterDate", "EmailSentDate", "CreateDate",
				"CustodianName", "DocDate", "LastEditDate", "LastModifiedDate", "LastSaveDate", "Begin Bates" };

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-49159 Batch Print");
		baseClass.stepInfo("Validate different Sorting option for Batch Print(Prior Production)");

		// Create tag and folder
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		if (username.equals(Input.pa1userName)) {
			tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
			tagsAndFolderPage.createNewTagwithClassification(tagName, Input.tagNamePrev);
		} else {
			tagsAndFolderPage.CreateFolderInRMU(foldername);
			tagsAndFolderPage.createNewTagwithClassificationInRMU(tagName, Input.tagNamePrev);
		}

		// Bulk Tag
		session.basicContentSearch(Input.testData1);
		session.bulkFolderExisting(foldername);

		// Generate Production
		String productionname = "P" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		System.out.println(productionname);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);

		// select DAT/Native/TIFF
		page.fillingDATSection();
		page.fillingTIFFSectionwithBurnRedaction();

		// select Slepsheet From Advanced TAB
		page.slipSheetToggleEnable();
		page.getSlipCalculatedTabSelection().ScrollTo();
		page.availableFieldSelection(Input.batesNumber);
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);

		// Select Search
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab("Folder", foldername, true);

		// select production
		batchPrint.fillingBasisForPrinting(false, true, productionname);
		batchPrint.navigateToNextPage(1);
		if (!batchPrint.getSlipSheetDD_prod().isElementAvailable(8)) {
			batchPrint.navigateToNextPage(2);
		} else {
			batchPrint.navigateToNextPage(1);
		}

		// verify Sort by list and order
		driver.waitForPageToBeReady();
		List<String> actualSortByList = baseClass.availableListofElements(batchPrint.getSortByDDList());

		String passMsg = "Sort By list is in Same order : " + actualSortByList;
		String failMsg = "Sort By list is not as expected";
		baseClass.compareArraywithDataList(expectedSortByList, actualSortByList, false, passMsg, failMsg);

		// logout
		loginPage.logout();
	}
	
	/**
	 * @Author Jeevitha
	 * @Description : To verify that on clicking of Back it should redirect to Slip
	 *              Sheet tab if Basis for Printing is 'Production' [RPMXCON-47876]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-47876", enabled = true, groups = { "regression" })
	public void verifyOnClickOfBackBtn() throws Exception {
		String Folder = "Folder" + Utility.dynamicNameAppender();
		String slipsheetDD = "Create new slip sheets";

		// Login As User
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		baseClass.stepInfo("Test case Id: RPMXCON-47876 Batch Print");
		baseClass.stepInfo(
				"To verify that on clicking of Back it should redirect to Slip Sheet tab if Basis for Printing is 'Production'");

		// create folder in Default SG
		tagsAndFolderPage.CreateFolder(Folder, Input.securityGroup);

		// configure query & view in doclist
		session.basicContentSearch(Input.testData1);
		session.bulkFolderExisting(Folder);

		// Generate Production with TIFF
		String productionname = page.preRequisiteGenerateProduction(Folder);

		// Select Folder & Production
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab("Folder", Folder, true);
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

		// navigated to export TAB
		batchPrint.selectSortingFromExportPage("ASC");

		// Click back button & verify navigated tab
		batchPrint.ClickBackButtonAndVerify(true, "Export Format", 1, true, "Slip Sheets");

		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : To verify that if search result deleted from Save Search then
	 *              it should not be displayed on Source Selection. [RPMXCON-47811]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-47811", enabled = true, groups = { "regression" })
	public void verifySearchIsNotDisplayedAfterDeleting() throws Exception {
		String Search1 = "Search" + Utility.dynamicNameAppender();
		String Search2 = "Search" + Utility.dynamicNameAppender();
		SavedSearch savesearch = new SavedSearch(driver);

		// Login As User
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		baseClass.stepInfo("Test case Id: RPMXCON-47811 Batch Print");
		baseClass.stepInfo(
				"To verify that if search result deleted from Save Search then it should not be displayed on Source Selection.");

		// configure query & view in doclist
		session.basicContentSearch(Input.testData1);
		session.saveSearch(Search1);
		session.saveSearch(Search2);

		// Check Search is Displayed
		batchPrint.navigateToBatchPrintPage();
		boolean search1Status = batchPrint.saveSearchRadiobutton(Search1);
		baseClass.printResutInReport(search1Status, Search1 + ": is Displayed in Source selection",
				Search1 + " is not Displayed", "Pass");

		driver.Navigate().refresh();
		boolean search2Status = batchPrint.saveSearchRadiobutton(Search2);
		baseClass.printResutInReport(search2Status, Search2 + ": is Displayed in Source selection",
				Search2 + " is not Displayed", "Pass");

		// Delete Search
		savesearch.deleteSearch(Search1, Input.mySavedSearch, "Yes");

		// Check Search is Not Displayed
		batchPrint.navigateToBatchPrintPage();
		boolean searchAfterDelete = batchPrint.saveSearchRadiobutton(Search1);
		baseClass.printResutInReport(searchAfterDelete, Search1 + ": is Not Displayed in Source selection",
				Search1 + " is Displayed after Deleting", "Fail");

		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify PDF file should be generated at the displayed path of
	 *              batch print background process as per selected 'One PDF for each
	 *              document' with File Name as selected in desc sort
	 *              [RPMXCON-47835]
	 * @throws IOException
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-47835", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void verifyPdfFileGeneratedInDescOrder(String username, String password)
			throws InterruptedException, IOException, AWTException {
		String Tag = "TAG" + Utility.dynamicNameAppender();
		DocListPage doclist = new DocListPage(driver);

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-47835 Batch Print");
		baseClass.stepInfo(
				"Verify PDF file should be generated at the displayed path of batch print background process as per selected 'One PDF for each document' with File Name as selected in desc sort");

		// configure query & view in doclist
		session.basicContentSearch(Input.searchStringStar);
		session.ViewInDocList();

		// Sort in Docfilename in Desc Order
		driver.waitForPageToBeReady();
		doclist.verifyAndAddColumn(Input.docFileName);
		driver.waitForPageToBeReady();
		doclist.sortColumn(true, Input.docFileName, false);
		doclist.documentSelectionIncludeChildDoc(4);

		// bulk tag selected docs
		doclist.addNewBulkTag(Tag);

		// To verify Expected result , Get the Descending order DocFilename List
		tagsAndFolderPage.ViewinDocListthrTag(Tag);

		// sort in Descending Order
		doclist.sortColumn(true, Input.docFileName, false);

		// expected Downloaded Document Names list ,Desc Order of Docfilename
		int index = baseClass.getIndex(doclist.getColumnHeader(), Input.documentKey);
		baseClass.waitForElementCollection(doclist.GetColumnData(index));
		List<String> actualdocIdList = doclist.availableListofElementsForDocList(doclist.GetColumnData(index));

		// Remove not downloadable Docid's from list
		List<String> docIdList = doclist.addDocsToListOfOnlyDownloadableFormat(actualdocIdList);

		// Select TAG & Native
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
		batchPrint.fillingExportFormatPage(Input.documentKey, Input.docFileName, false, 20);

		// Download Batch Print File
		String fileName = batchPrint.DownloadBatchPrintFile();

		// extract zip file
		String extractedFile = batchPrint.extractFile(fileName);

		// verify Downloaded File Count ,filename and Format
		List<String> actualFileName = batchPrint
				.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);

		// verify DocId DESC Sorting order of Downloaded File is As Expected
		boolean result = baseClass.compareListViaContainsTrimSpace(actualFileName, docIdList);
		baseClass.printResutInReport(result, "Downloaded FileNames Sorted as per LastSaveDate DESC order ",
				"Sorting is not as Expected", "Pass");

		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify PDF file should be generated with the production
	 *              slipsheet fields for the production set with DocID as export
	 *              file name [RPMXCON-47850]
	 * @throws IOException
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-47850", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void verifyPdfFileGenerated(String username, String password)
			throws InterruptedException, IOException, AWTException {
		String Tag = "TAG" + Utility.dynamicNameAppender();
		DocListPage doclist = new DocListPage(driver);

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-47850 Batch Print");
		baseClass.stepInfo(
				"Verify PDF file should be generated with the production slipsheet fields for the production set with DocID as export file name");

		// configure query & view in doclist
		session.basicContentSearch(Input.testData1);
		session.ViewInDocList();

		// Sort in Docfilename in Desc Order
		driver.waitForPageToBeReady();
		doclist.verifyAndAddColumn(Input.documentKey);
		driver.waitForPageToBeReady();
		doclist.sortColumn(true, Input.documentKey, false);
		doclist.selectAllDocs();

		// expected Downloaded Document Names list ,Desc Order of Docfilename
		int index = baseClass.getIndex(doclist.getColumnHeader(), Input.documentKey);
		baseClass.waitForElementCollection(doclist.GetColumnData(index));
		List<String> actualdocIdList = doclist.availableListofElementsForDocList(doclist.GetColumnData(index));

		// bulk tag selected docs
		doclist.addNewBulkTag(Tag);

		// Remove not downloadable Docid's from list
		List<String> docIdList = doclist.addDocsToListOfOnlyDownloadableFormat(actualdocIdList);

		// Select TAG & Native
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab(Input.tag, Tag, true);
		batchPrint.fillingBasisForPrinting(true, true, null);
		batchPrint.navigateToNextPage(1);
		batchPrint.fillingExceptioanlFileTypeTab(false, Input.documentKey, null, true);

		// filling SlipSheet With metadata
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true, null);
		batchPrint.navigateToNextPage(1);

		// Filling Export File Name as 'DocID', select Sort by 'DocFileName'
		batchPrint.selectSortingFromExportPage("DESC");
		batchPrint.fillingExportFormatPage(Input.documentKey, Input.documentKey, false, 20);

		// Download Batch Print File
		String fileName = batchPrint.DownloadBatchPrintFile();

		// extract zip file
		String extractedFile = batchPrint.extractFile(fileName);

		// verify Downloaded File Count ,filename and Format
		List<String> actualFileName = batchPrint
				.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);

		// verify DocId DESC Sorting order of Downloaded File is As Expected
		boolean result = baseClass.compareListViaContainsTrimSpace(actualFileName, docIdList);
		baseClass.printResutInReport(result, "Downloaded FileNames Sorted as per LastSaveDate DESC order ",
				"Sorting is not as Expected", "Pass");

		loginPage.logout();
	}

	@DataProvider(name = "UsersToImp")
	public Object[][] UsersToImp() {
		Object[][] UsersToImp = { { Input.sa1userName, Input.sa1password, "SA", "PA" },
				{ Input.sa1userName, Input.sa1password, "SA", "RMU" },
				{ Input.pa1userName, Input.pa1password, "PA", "RMU" } };
		return UsersToImp;
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify after impersonation user can go to the Branding and
	 *              Redactions tab [RPMXCON-47465]
	 * @throws IOException
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-47465", dataProvider = "UsersToImp", enabled = true, groups = { "regression" })
	public void verifyBrandingTabAfterImp(String username, String password, String role, String roleToImp)
			throws InterruptedException, IOException, AWTException {
		String Tag = "TAG" + Utility.dynamicNameAppender();

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-47465 Batch Print");
		baseClass.stepInfo("Verify after impersonation user can go to the Branding and Redactions tab");

		// Impersonate to PA
		baseClass.rolesToImp(role, roleToImp);

		// configure query & view in doclist
		session.basicContentSearch(Input.testData1);
		session.bulkTag(Tag);

		// Select TAG & Native
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab(Input.tag, Tag, true);
		batchPrint.fillingBasisForPrinting(true, true, null);
		batchPrint.navigateToNextPage(1);
		batchPrint.fillingExceptioanlFileTypeTab(false, Input.documentKey, null, true);

		// filling SlipSheet With metadata
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true, null);

		//verify current tab
		batchPrint.verifyCurrentTab("Branding");
		
		//verify Warning msg is not displayed
		driver.waitForPageToBeReady();
		baseClass.ValidateElement_Absence(baseClass.getWarningsMsgHeader(), "Warning Message is not Displayed");
		
		//verify navigation to export page
		batchPrint.navigateToNextPage(1);
		batchPrint.verifyCurrentTab("Export Format");

		//logout
		loginPage.logout();

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

	/**
	 * @Author Jeevitha
	 * @Description : Validate Batch Print Sorting docs by LastEditDate (Native)
	 *              with one PDF for each doc in descending order [RPMXCON-49187]
	 * @throws IOException
	 * @throws AWTException
	 */
	@Test(description = "RPMXCON-49187", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void validateSortingByLastEditDate(String username, String password)
			throws InterruptedException, IOException, AWTException {
		String Tag = "TAG" + Utility.dynamicNameAppender();
		String columnName = "LastEditDate";
		DocListPage doclist = new DocListPage(driver);

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-49187 Batch Print");
		baseClass.stepInfo(
				"Validate Batch Print Sorting docs by LastEditDate (Native) with one PDF for each doc in descending order");

		// configure query & view in doclist
		session.basicContentSearch(Input.searchStringStar);
		session.ViewInDocList();

		// select Few docs with & without LastSaveDate
		driver.waitForPageToBeReady();
		doclist.verifyAndAddColumn(columnName);
		doclist.sortColumn(true, columnName, true);
		doclist.documentSelectionIncludeChildDoc(3);
		driver.waitForPageToBeReady();
		doclist.sortColumn(true, columnName, false);
		doclist.documentSelectionIncludeChildDoc(3);

		// bulk tag selected docs
		doclist.addNewBulkTag(Tag);

		// To verify Expected result , Get the Descending order DocDate List
		tagsAndFolderPage.ViewinDocListthrTag(Tag);

		// sort in Descending Order
		doclist.sortColumn(true, columnName, false);

		// Esxpected Downloaded Document Names list ,Desc Order of LastEditDate
		int index = baseClass.getIndex(doclist.getColumnHeader(), Input.documentKey);
		baseClass.waitForElementCollection(doclist.GetColumnData(index));
		List<String> actualdocIdList = doclist.availableListofElementsForDocList(doclist.GetColumnData(index));

		// Remove not downloadable Docid's from list
		List<String> docIdList = doclist.addDocsToListOfOnlyDownloadableFormat(actualdocIdList);

		// Select Tag & Native
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab(Input.tag, Tag, true);
		batchPrint.fillingBasisForPrinting(true, true, null);
		batchPrint.navigateToNextPage(1);
		batchPrint.fillingExceptioanlFileTypeTab(false, Input.documentKey, null, true);

		// filling SlipSheet With metadata
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true, null);
		batchPrint.navigateToNextPage(1);

		// Filling Export File Name as 'DocID', select Sort by 'LastEditDate' In "DESC"
		// Order
		batchPrint.selectSortingFromExportPage("DESC");
		batchPrint.fillingExportFormatPage(Input.documentKey, columnName, false, 20);

		// Download Batch Print File
		String fileName = batchPrint.DownloadBatchPrintFile();

		// extract zip file
		String extractedFile = batchPrint.extractFile(fileName);

		// verify Downloaded File Count ,filename and Format
		List<String> actualFileName = batchPrint
				.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);

		// verify LastEditDate DESC Sorting order of Downloaded File is As Expected
		boolean result = baseClass.compareListViaContainsTrimSpace(actualFileName, docIdList);
		baseClass.printResutInReport(result, "Downloaded FileNames Sorted as per LastSaveDate DESC order ",
				"Sorting is not as Expected", "Pass");

		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Validate Batch Print Sorting docs by LastEditDate [Prior
	 *              Productions (TIFFs/PDFs)]with one PDF for all docs in ascending
	 *              order [RPMXCON-49188]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-49188", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void validateSortProdByLastEditDate(String username, String password) throws Exception {
		String Tag = "TAG" + Utility.dynamicNameAppender();
		String Folder = "Folder" + Utility.dynamicNameAppender();
		String columnName = "LastEditDate";
		String slipsheetDD = "Create new slip sheets";
		DocListPage doclist = new DocListPage(driver);

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-49188 Batch Print");
		baseClass.stepInfo(
				"Validate Batch Print Sorting docs by LastEditDate [Prior Productions (TIFFs/PDFs)]with one PDF for all docs in ascending order");

		// create folder in Default SG
		tagsAndFolderPage.CreateFolder(Folder, Input.securityGroup);

		// configure query & view in doclist
		session.basicContentSearch(Input.searchStringStar);
		session.ViewInDocList();

		// select Few docs with & without LastEditDate
		driver.waitForPageToBeReady();
		doclist.verifyAndAddColumn(columnName);
		doclist.sortColumn(true, columnName, true);
		doclist.documentSelectionIncludeChildDoc(3);
		driver.waitForPageToBeReady();
		doclist.sortColumn(true, columnName, false);
		doclist.documentSelectionIncludeChildDoc(3);

		// bulk tag selected docs
		doclist.addNewBulkTag(Tag);

		// To verify Expected result , Get the Descending order DocDate List
		tagsAndFolderPage.ViewinDocListthrTag(Tag);

		// bulk folder & sort in Ascending Order
		driver.waitForPageToBeReady();
		doclist.selectAllDocs();
		doclist.bulkFolderExisting(Folder);
		doclist.sortColumn(true, columnName, true);

		// Esxpected Downloaded Document Names list ,Desc Order of LastEditDate
		int index = baseClass.getIndex(doclist.getColumnHeader(), Input.docFileName);
		baseClass.waitForElementCollection(doclist.GetColumnData(index));
		List<String> actualdocNameList = doclist.availableListofElementsForDocList(doclist.GetColumnData(index));

		// Remove not downloadable Docid's from list
		List<String> docFileNameList = doclist.addDocsToListOfOnlyDownloadableFormat(actualdocNameList);

		// Generate Production with TIFF
		String productionname = page.preRequisiteGenerateProduction(Folder);

		// Select Tag & Production
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab(Input.tag, Tag, true);
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

		// Filling Export File Name as 'Docfilename', select Sort by 'LastEditDate' In
		// "ASC" Order [one PDF for All Doc]
		batchPrint.selectSortingFromExportPage("ASC");
		batchPrint.fillingExportFormatPage(Input.docFileName, columnName, true, 20);

		// Download Batch Print File
		String fileName = batchPrint.DownloadBatchPrintFile();

		// extract zip file
		String extractedFile = batchPrint.extractFile(fileName);

		// verify Downloaded File Count ,filename and Format
		List<String> actualFileName = batchPrint
				.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);

		// verify LastEditDate ASC Sorting order of Downloaded FileName is As Expected
		batchPrint.compareListWithStringviaContains(docFileNameList, actualFileName.get(0),
				"The Downloaded file is As Expected", "Downloaded file is not as expected");

		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Validate Batch Print Sorting docs by LastModifiedDate [Prior
	 *              Productions (TIFFs/PDFs)]with one PDF for each doc in descending
	 *              order[RPMXCON-49186]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-49186", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void validateSortProdByLastModifiedDate(String username, String password) throws Exception {
		String Tag = "TAG" + Utility.dynamicNameAppender();
		String Folder = "Folder" + Utility.dynamicNameAppender();
		String columnName = "LastModifiedDate";
		String slipsheetDD = "Create new slip sheets";
		DocListPage doclist = new DocListPage(driver);

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-49186 Batch Print");
		baseClass.stepInfo(
				"Validate Batch Print Sorting docs by LastModifiedDate [Prior Productions (TIFFs/PDFs)]with one PDF for each doc in descending order");

		// create folder in Default SG
		tagsAndFolderPage.CreateFolder(Folder, Input.securityGroup);

		// configure query & view in doclist
		session.basicContentSearch(Input.searchStringStar);
		session.ViewInDocList();

		// select Few docs with & without LastModifiedDate
		driver.waitForPageToBeReady();
		doclist.verifyAndAddColumn(columnName);
		doclist.sortColumn(true, columnName, true);
		doclist.documentSelectionIncludeChildDoc(3);
		driver.waitForPageToBeReady();
		doclist.sortColumn(true, columnName, false);
		doclist.documentSelectionIncludeChildDoc(3);

		// bulk tag selected docs
		doclist.addNewBulkTag(Tag);

		// To verify Expected result , Get the Descending order DocDate List
		tagsAndFolderPage.ViewinDocListthrTag(Tag);

		// bulk folder & sort in Descending Order
		driver.waitForPageToBeReady();
		doclist.selectAllDocs();
		doclist.bulkFolderExisting(Folder);
		doclist.sortColumn(true, columnName, false);

		// Esxpected Downloaded Document Names list ,Desc Order of LastModifiedDate
		int index = baseClass.getIndex(doclist.getColumnHeader(), Input.documentKey);
		baseClass.waitForElementCollection(doclist.GetColumnData(index));
		List<String> actualdocIdList = doclist.availableListofElementsForDocList(doclist.GetColumnData(index));

		// Remove not downloadable Docid's from list
		List<String> docIDList = doclist.addDocsToListOfOnlyDownloadableFormat(actualdocIdList);

		// Generate Production with TIFF
		String productionname = page.preRequisiteGenerateProduction(Folder);

		// Select Tag & Production
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab(Input.tag, Tag, true);
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

		// Filling Export File Name as 'Docfilename', select Sort by 'LastEditDate' In
		// "DESC" Order And [One PDF for each doc]
		batchPrint.selectSortingFromExportPage("DESC");
		batchPrint.fillingExportFormatPage(Input.documentKey, columnName, false, 20);

		// Download Batch Print File
		String fileName = batchPrint.DownloadBatchPrintFile();

		// extract zip file
		String extractedFile = batchPrint.extractFile(fileName);

		// verify Downloaded File Count ,filename and Format
		List<String> actualFileName = batchPrint
				.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);

		// verify LastModifiedDate DESC Sorting order of Downloaded file ID is as
		// Expected
		boolean result = baseClass.compareListViaContainsTrimSpace(actualFileName, docIDList);
		String passMsg = "Downloaded file is Sorted in DESC Of LastModifiedDate : " + docIDList;
		baseClass.printResutInReport(result, passMsg, "Sorting is not as Expected", "Pass");

		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Validate Batch Print Sorting docs by CreatedDateTime [Prior
	 *              Productions (TIFFs/PDFs)]with one PDF for each doc in descending
	 *              order[RPMXCON-49161]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-49161", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void validateSortProdByCreateDate(String username, String password) throws Exception {
		String Tag = "TAG" + Utility.dynamicNameAppender();
		String Folder = "Folder" + Utility.dynamicNameAppender();
		String columnName = "CreateDate";
		String slipsheetDD = "Create new slip sheets";
		DocListPage doclist = new DocListPage(driver);

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-49161 Batch Print");
		baseClass.stepInfo(
				"Validate Batch Print Sorting docs by CreatedDateTime [Prior Productions (TIFFs/PDFs)]with one PDF for each doc in descending order");

		// create folder in Default SG
		tagsAndFolderPage.CreateFolder(Folder, Input.securityGroup);

		// configure query & view in doclist
		session.basicContentSearch(Input.searchStringStar);
		session.ViewInDocList();

		// select Few docs with & without CreateDate
		driver.waitForPageToBeReady();
		doclist.verifyAndAddColumn(columnName);
		doclist.sortColumn(true, columnName, true);
		doclist.documentSelectionIncludeChildDoc(3);
		driver.waitForPageToBeReady();
		doclist.sortColumn(true, columnName, false);
		doclist.documentSelectionIncludeChildDoc(3);

		// bulk tag selected docs
		doclist.addNewBulkTag(Tag);

		// To verify Expected result , Get the Descending order DocDate List
		tagsAndFolderPage.ViewinDocListthrTag(Tag);

		// bulk folder & sort in Descending Order
		driver.waitForPageToBeReady();
		doclist.selectAllDocs();
		doclist.bulkFolderExisting(Folder);
		doclist.sortColumn(true, columnName, false);

		// Esxpected Downloaded Document Names list ,Desc Order of CreateDate
		int index = baseClass.getIndex(doclist.getColumnHeader(), Input.documentKey);
		baseClass.waitForElementCollection(doclist.GetColumnData(index));
		List<String> actualdocIdList = doclist.availableListofElementsForDocList(doclist.GetColumnData(index));

		// Remove not downloadable Docid's from list
		List<String> docIDList = doclist.addDocsToListOfOnlyDownloadableFormat(actualdocIdList);

		// Generate Production with TIFF
		String productionname = page.preRequisiteGenerateProduction(Folder);

		// Select Tag & Production
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab(Input.tag, Tag, true);
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

		// Filling Export File Name as 'Docfilename', select Sort by 'CreatedDateTime'
		// in "DESC" Order And [One PDF for each doc]
		batchPrint.selectSortingFromExportPage("DESC");
		batchPrint.fillingExportFormatPage(Input.documentKey, columnName, false, 20);

		// Download Batch Print File
		String fileName = batchPrint.DownloadBatchPrintFile();

		// extract zip file
		String extractedFile = batchPrint.extractFile(fileName);

		// verify Downloaded File Count ,filename and Format
		List<String> actualFileName = batchPrint
				.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);

		// verify CreateDate DESC Sorting order of Downloaded file ID is as
		// Expected
		boolean result = baseClass.compareListViaContainsTrimSpace(actualFileName, docIDList);
		String passMsg = "Downloaded file is Sorted in DESC Of CreateDate : " + docIDList;
		baseClass.printResutInReport(result, passMsg, "Sorting is not as Expected", "Pass");

		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Validate Batch Print Sorting docs by MasterDateTime [Prior
	 *              Productions (TIFFs/PDFs)]with one PDF for all docs in ascending
	 *              order [RPMXCON-49160]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-49160", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void validateSortProdByMasterDate(String username, String password) throws Exception {
		String Tag = "TAG" + Utility.dynamicNameAppender();
		String Folder = "Folder" + Utility.dynamicNameAppender();
		String columnName = "MasterDate";
		String slipsheetDD = "Create new slip sheets";
		DocListPage doclist = new DocListPage(driver);

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-49160 Batch Print");
		baseClass.stepInfo(
				"Validate Batch Print Sorting docs by MasterDateTime [Prior Productions (TIFFs/PDFs)]with one PDF for all docs in ascending order");

		// create folder in Default SG
		tagsAndFolderPage.CreateFolder(Folder, Input.securityGroup);

		// configure query & view in doclist
		session.basicContentSearch(Input.searchStringStar);
		session.ViewInDocList();

		// select Few docs with & without MasterDate
		driver.waitForPageToBeReady();
		doclist.verifyAndAddColumn(columnName);
		doclist.sortColumn(true, columnName, true);
		doclist.documentSelectionIncludeChildDoc(3);
		driver.waitForPageToBeReady();
		doclist.sortColumn(true, columnName, false);
		doclist.documentSelectionIncludeChildDoc(3);

		// bulk tag selected docs
		doclist.addNewBulkTag(Tag);

		// To verify Expected result , Get the Descending order DocDate List
		tagsAndFolderPage.ViewinDocListthrTag(Tag);

		//// bulk folder & sort in Ascending Order
		driver.waitForPageToBeReady();
		doclist.selectAllDocs();
		doclist.bulkFolderExisting(Folder);
		doclist.sortColumn(true, columnName, true);

		// Esxpected Downloaded Document Names list ,ASC Order of MasterDate
		int index = baseClass.getIndex(doclist.getColumnHeader(), Input.documentKey);
		baseClass.waitForElementCollection(doclist.GetColumnData(index));
		List<String> actualdocNameList = doclist.availableListofElementsForDocList(doclist.GetColumnData(index));

		// Remove not downloadable Docid's from list
		List<String> docFileNameList = doclist.addDocsToListOfOnlyDownloadableFormat(actualdocNameList);

		// Generate Production with TIFF
		String productionname = page.preRequisiteGenerateProduction(Folder);

		// Select Tag & Production
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab(Input.tag, Tag, true);
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

		// Filling Export File Name as 'DocID', select Sort by 'MasterDate' In
		// "ASC" Order [one PDF for All Doc]
		batchPrint.selectSortingFromExportPage("ASC");
		batchPrint.fillingExportFormatPage(Input.documentKey, columnName, true, 20);

		// Download Batch Print File
		String fileName = batchPrint.DownloadBatchPrintFile();

		// extract zip file
		String extractedFile = batchPrint.extractFile(fileName);

		// verify Downloaded File Count ,filename and Format
		List<String> actualFileName = batchPrint
				.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);

		// verify MasterDate ASC Sorting order of Downloaded FileID is As Expected
		batchPrint.compareListWithStringviaContains(docFileNameList, actualFileName.get(0),
				"The Downloaded file is As Expected", "Downloaded file is not as expected");

		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Validate Batch Print Sorting docs by EmailSentDate [Prior
	 *              Productions (TIFFs/PDFs)]with one PDF for all docs in ascending
	 *              order [RPMXCON-49162]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-49162", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void validateSortProdByEmailSentDate(String username, String password) throws Exception {
		String Tag = "TAG" + Utility.dynamicNameAppender();
		String Folder = "Folder" + Utility.dynamicNameAppender();
		String columnName = "EmailSentDate";
		String slipsheetDD = "Create new slip sheets";
		DocListPage doclist = new DocListPage(driver);

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-49162 Batch Print");
		baseClass.stepInfo(
				"Validate Batch Print Sorting docs by EmailSentDate [Prior Productions (TIFFs/PDFs)]with one PDF for all docs in ascending order");

		// create folder in Default SG
		tagsAndFolderPage.CreateFolder(Folder, Input.securityGroup);

		// configure query & view in doclist
		session.basicContentSearch(Input.searchStringStar);
		session.ViewInDocList();

		// select Few Docs With & Without EmailSentDate
		driver.waitForPageToBeReady();
		doclist.verifyAndAddColumn(columnName);
		doclist.sortColumn(true, columnName, true);
		doclist.documentSelectionIncludeChildDoc(3);
		driver.waitForPageToBeReady();
		doclist.sortColumn(true, columnName, false);
		doclist.documentSelectionIncludeChildDoc(3);

		// bulk Tag selected docs
		doclist.addNewBulkTag(Tag);

		// To verify Expected result , Get the Descending order DocDate List
		tagsAndFolderPage.ViewinDocListthrTag(Tag);

		// bulk folder & sort in Ascending Order
		driver.waitForPageToBeReady();
		doclist.selectAllDocs();
		doclist.bulkFolderExisting(Folder);
		doclist.sortColumn(true, columnName, true);

		// Esxpected Downloaded Document Names list ,ASC Order of EmailSentDate
		int index = baseClass.getIndex(doclist.getColumnHeader(), Input.docFileName);
		baseClass.waitForElementCollection(doclist.GetColumnData(index));
		List<String> actualdocNameList = doclist.availableListofElementsForDocList(doclist.GetColumnData(index));

		// Generate Production with TIFF
		String productionname = page.preRequisiteGenerateProduction(Folder);
		baseClass.waitUntilFileDownload();
		
		// Select Tag & Production
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab(Input.tag, Tag, true);
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

		// Filling Export File Name as 'DocFileName', select Sort by 'EmailSentDate ' In
		// "ASC" Order [one PDF for All Doc]
		batchPrint.selectSortingFromExportPage("ASC");
		batchPrint.fillingExportFormatPage(Input.docFileName, columnName, true, 20);

		// Download Batch Print File
		String fileName = batchPrint.DownloadBatchPrintFile();

		// extract zip file
		String extractedFile = batchPrint.extractFile(fileName);

		// verify Downloaded File Count ,filename and Format
		List<String> actualFileName = batchPrint
				.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);

		// verify EmailSentDate ASC Sorting order of Downloaded Filename is As Expected
		batchPrint.compareListWithStringviaContains(actualdocNameList, actualFileName.get(0),
				"The Downloaded file is As Expected", "Downloaded file is not as expected");

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
