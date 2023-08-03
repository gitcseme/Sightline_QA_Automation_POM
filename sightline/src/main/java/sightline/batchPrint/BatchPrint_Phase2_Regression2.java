package sightline.batchPrint;
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
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import net.lingala.zip4j.exception.ZipException;
import pageFactory.BaseClass;
import pageFactory.BatchPrintPage;
import pageFactory.DataSets;
import pageFactory.DocListPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.RedactionPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BatchPrint_Phase2_Regression2 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	BatchPrintPage batchPrint;
	SessionSearch session;
	TagsAndFoldersPage tagsAndFolderPage;
	ProductionPage page;
	SoftAssert softassert;

	String specificTag = "BatchPrintTag" + Utility.dynamicNameAppender();
	String specificTag2 = "BatchTag" + Utility.dynamicNameAppender();

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
		Object[][] users = { { Input.pa4userName, Input.pa4password }, { Input.rmu4userName, Input.rmu4password }, };
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
	 * @Description : To verify that 'Enable Slip Sheets' is off/on then panel
	 *              should be disabled[RPMXCON-47829]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-47829", enabled = true, groups = { "regression" })
	public void verifyEnableSlipSheet() throws Exception {
		String Folder = "Folder" + Utility.dynamicNameAppender();
		String slipsheetDD = "Create new slip sheets";
		SecurityGroupsPage security = new SecurityGroupsPage(driver);

		// Login As PA
		loginPage.loginToSightLine(Input.pa4userName, Input.pa4password);

		baseClass.stepInfo("Test case Id: RPMXCON-47829 Batch Print");
		baseClass.stepInfo("To verify that 'Enable Slip Sheets' is off/on then panel should be disabled");

		// fetch Fields associated with Security group
		security.selectSecurityGroupAndClickOnProjectFldLink(Input.securityGroup);
		baseClass.waitForElementCollection(security.getSelectdFieldsList());
		List<String> sgAssociatedFields = baseClass.availableListofElements(security.getSelectdFieldsList());

		// logout
		loginPage.logout();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu4userName, Input.rmu4password);

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

		// Verify Do you want to use slip sheets of prior productions or create new slip
		// sheets?: Dropdown is Enabled
		baseClass.waitForElement(batchPrint.getSlipSheetOfProd_DDText());
		baseClass.ValidateElement_Absence(batchPrint.getSlipSheetOfProd_DD(),
				batchPrint.getSlipSheetOfProd_DDText().getText() + " : is Enabled");

		// Select Create new slipSheet from Dropdown
		batchPrint.selectDropdownFromSlipSheet_prod(slipsheetDD);

		// fetch available metadata workproduct fields in slipsheet tab
		driver.scrollingToBottomofAPage();
		driver.waitForPageToBeReady();
		baseClass.waitForElementCollection(batchPrint.getSlipsheetsFields());
		List<String> slipsheetFields = baseClass.availableListofElements(batchPrint.getSlipsheetsFields());

		// verify All the fields associated with Security group is displayed
		driver.waitForPageToBeReady();
		baseClass.sortAndCompareList(sgAssociatedFields, slipsheetFields, true, "Ascending",
				"Fields associated to the security group is Displayed",
				"Fields associated to the security group is not Displayed");

		// Toggle OFF the 'Enable Slip Sheets'
		driver.waitForPageToBeReady();
		batchPrint.getEnableSlipSheetToggle().waitAndClick(10);

		// verify Panel is Disabled
		driver.waitForPageToBeReady();
		baseClass.waitForElement(batchPrint.getSlipSheetOfProd_DDText());
		baseClass.ValidateElement_Presence(batchPrint.getSlipSheetOfProd_DD(),
				batchPrint.getSlipSheetOfProd_DDText().getText() + " : is Disabled");

		// Toggle on Enable Slip Sheets
		driver.waitForPageToBeReady();
		batchPrint.getEnableSlipSheetToggle().waitAndClick(10);

		// verify Panel is enabled
		driver.waitForPageToBeReady();
		baseClass.waitForElement(batchPrint.getSlipSheetOfProd_DDText());
		baseClass.ValidateElement_Absence(batchPrint.getSlipSheetOfProd_DD(),
				batchPrint.getSlipSheetOfProd_DDText().getText() + " : is Enabled");

		// verify select Fields for Slip Sheets tab is displayed for MetaData
		// workproduct
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, false, null);
		baseClass.passedStep("select Fields for Slip Sheets tab is displayed for Metadata WorkProduct");

		// logout
		loginPage.logout();
	}

	/**
	 * @throws Exception
	 * @Author Jeevitha
	 * @Description : Verify PDF file should be generated with redaction on for the
	 *              documents from selected production set with File Name as export
	 *              file name [RPMXCON-47857]
	 */
	@Test(description = "RPMXCON-47857", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void verifyPDFFileWithRedaction(String username, String password) throws Exception {
		SoftAssert softassert = new SoftAssert();
		String Folder = "Folder" + Utility.dynamicNameAppender();
		String redactionTag = "RedactionTag" + Utility.dynamicNameAppender();
		String slipsheetDD = "Create new slip sheets";

		RedactionPage redaction = new RedactionPage(driver);
		DocViewRedactions docviewredact = new DocViewRedactions(driver);
		DocListPage doclist = new DocListPage(driver);

		// Login As User
		loginPage.loginToSightLine(Input.rmu4userName, Input.rmu4password);

		baseClass.stepInfo("Test case Id: RPMXCON-47857 Batch Print");
		baseClass.stepInfo(
				"Verify PDF file should be generated with redaction on for the documents from selected production set with File Name as export file name");

		// Add redaction tag
		redaction.AddRedaction(redactionTag, "RMU");

		// create folder in Default SG
		tagsAndFolderPage.CreateFolder(Folder, Input.securityGroup);

		// configure query & view in Docview & Add Redaction For Document
		session.basicContentSearch(Input.testData1);
		session.viewInDocView();
		driver.waitForPageToBeReady();
		docviewredact.clickingRedactionIcon();
		docviewredact.performThisPageRedaction(redactionTag);

		driver.waitForPageToBeReady();
		session.navigateToSessionSearchPageURL();
		session.bulkFolderExistingWithoutPureHit(Folder);
		session.ViewInDocListWithOutPureHit();

		// sort ascending
		doclist.sortColumn(true, Input.documentKey, true);

		// Expected Downloaded DOCFILENAME list ,ASC Order of DOCID
		int index = baseClass.getIndex(doclist.getColumnHeader(), Input.docFileName);
		baseClass.waitForElementCollection(doclist.GetColumnData(index));
		List<String> actualdocNameList = doclist.availableListofElementsForDocList(doclist.GetColumnData(index));

		// Remove not downloadable DOCFILENAME's from list
		List<String> docFileNameList = doclist.addDocsToListOfOnlyDownloadableFormat(actualdocNameList);

		if (username.equals(Input.pa4userName)) {
			loginPage.logout();
			// Login As PA
			loginPage.loginToSightLine(username, password);
		}

		// Generate Production with TIFF with burn redaction
		String productionname = page.preRequisiteGenerateProduction(Folder);

		boolean[] selectPdfRadio = { false, true };
		for (int i = 0; i < 2; i++) {
			boolean radio = selectPdfRadio[i];

			// Select Fodler from Source selection Tab
			batchPrint.navigateToBatchPrintPage();
			batchPrint.fillingSourceSelectionTab("Folder", Folder, true);

			// select Production set
			batchPrint.fillingBasisForPrinting(false, true, productionname);

			// Select the documents skipped from printing table should be displayed
			if (batchPrint.getAnalysisSkipDocBtn().isElementAvailable(5)) {
				batchPrint.fillingAnalysisTab(false, false, false, true);
			}
			batchPrint.navigateToNextPage(1);

			// Verify user will be on 'Exception File Types' tab only if applicable
			if (!batchPrint.getSlipSheetDD_prod().isElementAvailable(10)) {
				baseClass.stepInfo("User is in exception File Types Tab");
				batchPrint.navigateToNextPage(1);
			} else {
				baseClass.stepInfo("No Exception file types document available");
			}

			// filling SlipSheet With metadata & click Next
			batchPrint.selectDropdownFromSlipSheet_prod(slipsheetDD);
			batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true, null);

			// verify Branding and Redactions Tab Is Skipped
			batchPrint.verifyCurrentTab("Export Format");

			// Keep All Default options As it is I.e.., Export by DOCFILENAME, Sort BY DOCID
			// , ASC Order

			// Verify Success Message, BackgroundTask page & downloaded link In this Method
			// itself
			batchPrint.fillingExportFormatPage(Input.docFileName, Input.documentKey, radio, 20);

			// Download Batch Print File
			String fileName = batchPrint.DownloadBatchPrintFile();

			// extract zip file
			String extractedFile = batchPrint.extractFile(fileName);

			// verify Downloaded File Count ,filename and Format
			List<String> actualFileName = batchPrint
					.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);

			// verify DOCFILENAME & Sorting order
			boolean flag = baseClass.compareListViaContains(actualFileName, docFileNameList);
			baseClass.printResutInReport(flag, "Downloaded PDF Filenames & Sorting order is As Expected",
					"Downloaded PDF Filenames & Sorting order is Not As Expected", "Pass");

			// verify Documents Exported Correctly By selecting One PDF for Each & One PDF
			// for All
			if (!radio) {
				softassert.assertNotEquals(actualFileName.size(), 1);
				baseClass.passedStep("Document Successfully exported as 'One PDF For Each' By selecting ASC sort");
			} else {
				softassert.assertEquals(actualFileName.size(), 1);
				baseClass.passedStep("Document Successfully exported as 'One PDF For ALL' By Selecting ASC sort");
			}
			softassert.assertAll();
		}

		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify Analysis tab when 'Prior Productions (TIFFs/PDFs)'
	 *              radio button is selected from 'Basis for
	 *              Printing'[RPMXCON-47812]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-47812", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void validateAnalysisTabWithProdSelection(String username, String password) throws Exception {
		String SearchName = "SearchName" + Utility.dynamicNameAppender();
		String Folder = "Folder" + Utility.dynamicNameAppender();
		String slipsheetDD = "Create new slip sheets";

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-47812 Batch Print");
		baseClass.stepInfo(
				"Verify Analysis tab when 'Prior Productions (TIFFs/PDFs)' radio button is selected from 'Basis for Printing'");

		// create folder in Default SG
		tagsAndFolderPage.CreateFolder(Folder, Input.securityGroup);

		// configure query & view in doclist
		session.basicContentSearch(Input.testData1);
		session.bulkFolderExisting(Folder);

		session.addNewSearch();
		session.multipleBasicContentSearch(Input.searchString4);
		session.saveSearch(SearchName);

		// Generate Production with TIFF
		String productionname = page.preRequisiteGenerateProduction(Folder);

		// Select Tag & Production
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab("Search", SearchName, false);
		batchPrint.fillingBasisForPrinting(false, true, productionname);

		// verify Table Headers , Pagination & Radio Button for docs In the GRID
		batchPrint.verifyTableGridInAnalysisTab(true);

		// verify Analysis for request Details
		batchPrint.verifyAnalysisReportDetails();

		// Enable Skip fodler toggle & verify tree folder structure
		batchPrint.verifyFolderSkippedDoc(false, true);
		driver.waitForPageToBeReady();

		// Disable Skip folder toggle & verify tree folder structure
		batchPrint.verifyFolderSkippedDoc(false, false);

		// logout
		loginPage.logout();
	}

	/**
	 * @throws Exception
	 * @Author Jeevitha
	 * @Description : verify PDF file should be generated with the selected branding
	 *              & redactions for the selected folder and with DocID as export
	 *              file name [RPMXCON-49427]
	 */
	@Test(description = "RPMXCON-49427", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void verifyPDFFileWithRedaction1(String username, String password) throws Exception {
		SoftAssert softassert = new SoftAssert();
		String Folder = "Folder" + Utility.dynamicNameAppender();
		String slipsheetDD = "Create new slip sheets";

		DocListPage doclist = new DocListPage(driver);
		

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-49427 Batch Print");
		baseClass.stepInfo(
				"verify PDF file should be generated with the selected branding & redactions for the selected folder and with DocID as export file name");

		// create folder in Default SG
		tagsAndFolderPage.CreateFolderInRMU(Folder);

		// configure query &
		session.basicContentSearch(Input.testData1);
		session.bulkFolderExisting(Folder);
		session.ViewInDocListWithOutPureHit();

		// sort ascending
		doclist.sortColumn(true, Input.documentKey, true);

		// Expected Downloaded DOCID list ,ASC Order of DOCID
		int index = baseClass.getIndex(doclist.getColumnHeader(), Input.documentKey);
		baseClass.waitForElementCollection(doclist.GetColumnData(index));
		List<String> actualdocNameList = doclist.availableListofElementsForDocList(doclist.GetColumnData(index));

		// Remove not downloadable DOCID's from list
		List<String> docFileNameList = doclist.addDocsToListOfOnlyDownloadableFormat(actualdocNameList);

		boolean[] selectPdfRadio = { false, true };
		for (int i = 0; i < 2; i++) {
			boolean radio = selectPdfRadio[i];

			// Select Folder from Source selection Tab
			batchPrint.navigateToBatchPrintPage();
			batchPrint.fillingSourceSelectionTab("Folder", Folder, true);

			// select Native Viewable file variant
			batchPrint.fillingBasisForPrinting(true, true, null);

			// verify radio button & Select Any Option from Analysis tab
			batchPrint.fillingAnalysisTab(true, false, false, false);
			batchPrint.navigateToNextPage(1);

			// Verify user will be on 'Exception File Types' tab & select any metadata
			batchPrint.fillingExceptioanlFileTypeTab(false, Input.documentKey, null, true);

			// filling SlipSheet With metadata & click Next
			batchPrint.selectDropdownFromSlipSheet_prod(slipsheetDD);
			batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true, null);

			// ON the 'Include Applied Redactions' toggle and select Opaque/Transparent And
			// select locations for Branding & verify Colour then click on 'Next'
			batchPrint.verifyBrandingAndReadctTab(true, true, Input.searchString4, true);
			batchPrint.navigateToNextPage(1);

			// Keep All Default options As it is I.e.., Except Export by DOCFILENAME as
			// DOCID, Sort BY DOCID
			// , ASC Order

			// Verify Success Message, BackgroundTask page & downloaded link In this Method
			// itself
			batchPrint.fillingExportFormatPage(Input.documentKey, Input.documentKey, radio, 20);

			// Download Batch Print File
			String fileName = batchPrint.DownloadBatchPrintFile();

			// extract zip file
			String extractedFile = batchPrint.extractFile(fileName);

			// verify Downloaded File Count ,filename and Format
			List<String> actualFileName = batchPrint
					.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);

			// verify DOCFILENAME & Sorting order
			boolean flag = baseClass.compareListViaContains(actualFileName, docFileNameList);
			baseClass.printResutInReport(flag, "Downloaded PDF Filenames & Sorting order is As Expected",
					"Downloaded PDF Filenames & Sorting order is Not As Expected", "Pass");

			// verify Documents Exported Correctly By selecting One PDF for Each & One PDF
			// for All
			if (!radio) {
				softassert.assertNotEquals(actualFileName.size(), 1);
				baseClass.passedStep("Document Successfully exported as 'One PDF For Each' By selecting ASC sort");
			} else {
				softassert.assertEquals(actualFileName.size(), 1);
				baseClass.passedStep("Document Successfully exported as 'One PDF For ALL' By Selecting ASC sort");
			}
			softassert.assertAll();
		}

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

		if (username.equals(Input.pa4userName)) {

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
	 * @Author Jeevitha
	 * @Description : Validate Batch Print Sorting docs by DocFileName [Prior
	 *              Productions (TIFFs/PDFs)]with one PDF for each doc in descending
	 *              order[RPMXCON-49193]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-49193", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void validateSortProdByDocfile(String username, String password) throws Exception {
		String Tag = "TAG" + Utility.dynamicNameAppender();
		String Folder = "Folder" + Utility.dynamicNameAppender();
		String slipsheetDD = "Create new slip sheets";
		DocListPage doclist = new DocListPage(driver);

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-49193 Batch Print");
		baseClass.stepInfo(
				"Validate Batch Print Sorting docs by DocFileName [Prior Productions (TIFFs/PDFs)]with one PDF for each doc in descending order");

		// create folder in Default SG
		tagsAndFolderPage.CreateFolder(Folder, Input.securityGroup);

		// configure query & view in doclist
		session.basicContentSearch(Input.testData1);
		session.ViewInDocList();
		doclist.documentSelectionIncludeChildDoc(3);

		// bulk tag selected docs
		doclist.addNewBulkTag(Tag);

		// To verify Expected result , Select Tag And navigate to Doclist Page
		tagsAndFolderPage.ViewinDocListthrTag(Tag);

		// bulk folder & sort in Descending Order
		driver.waitForPageToBeReady();
		doclist.selectAllDocs();
		doclist.bulkFolderExisting(Folder);
		doclist.sortColumn(true, Input.docFileName, false);

		// Expected Downloaded Document ID'S list ,Desc Order of DOCFILENAME
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
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, false, null);

		// Click on Coversheet toggle & Enter all details for Cover sheet
		batchPrint.enableCoverToggleAnFillTheDetails(Input.collectionDataEmailId, Input.collection2ndEmailId,
				Input.comment);
		batchPrint.navigateToNextPage(1);

		// Filling Export File Name as 'DocID', select Sort by 'DOCFILENAME' In
		// "DESC" Order [one PDF for Each Doc]
		batchPrint.selectSortingFromExportPage("DESC");
		batchPrint.fillingExportFormatPage(Input.documentKey, Input.docFileName, false, 20);

		// Download Batch Print File
		String fileName = batchPrint.DownloadBatchPrintFile();

		// extract zip file
		String extractedFile = batchPrint.extractFile(fileName);

		// verify Downloaded File Count ,filename and Format
		List<String> actualFileName = batchPrint
				.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);

		// @docFileNameList : is the list of DOCID which is fetced after sorting in DESC
		// Order And The Expected Downloaded FileName
		// @actualFileName : is the Actual Downloaded FileName
		// Verify the order,Sorting of the FileNames & Verify the Filenames is Based on
		// DOCID
		boolean flag = baseClass.compareListViaContains(actualFileName, docFileNameList);
		baseClass.printResutInReport(flag, "Downloaded PDF Filenames & Sorting order is As Expected",
				"Downloaded PDF Filenames & Sorting order is Not As Expected", "Pass");
		loginPage.logout();
	}
	
	/**
	 * @Author Jeevitha
	 * @Description :Verify that Batch Print should generate successfully, if user
	 *              selected Source as 'Folder'[RPMXCON-49417]
	 * @throws InterruptedException
	 * @throws ZipException
	 */
	@Test(description = "RPMXCON-49417", enabled = true, dataProvider = "Users", groups = { "regression" })
	public void verifyBatchPrintForFolder(String username, String password) throws InterruptedException, ZipException {
		String folder = "Folder" + Utility.dynamicNameAppender();
		String search = Input.testData1 + " " + "DocFileExtension: (xlsx)";

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-49417 Batch Print");
		baseClass.stepInfo("Verify that Batch Print should generate successfully, if user selected Source as 'Folder'");

		// Bulk Tag
		session.basicContentSearch(search);
		session.bulkFolder(folder);

		// Select Folder
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab("Folder", folder, true);
		batchPrint.fillingBasisForPrinting(true, true, null);
		batchPrint.fillingAnalysisTab(false, true, false, false);
		batchPrint.navigateToNextPage(1);
		batchPrint.fillingExceptioanlFileTypeTab(false, Input.documentKey, null, true);

		// filling SlipSheet WIth metadata
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true, null);
		batchPrint.navigateToNextPage(1);

		// Filling Export File Name as 'DocFileName', select Sort by 'DocID'
		batchPrint.fillingExportFormatPage(Input.docFileName, Input.documentKey, false, 20);

		// Download Batch Print File
		String fileName = batchPrint.DownloadBatchPrintFile();

		// verify downloaded file is in Zip Format
		String fileFormat = FilenameUtils.getExtension(fileName);
		String expectedFormat = "zip";

		String passMsg = "Downloaded File Format is  : " + fileFormat;
		String failMsg = "Downloaded File Format is Not As Expected";
		baseClass.textCompareEquals(fileFormat, expectedFormat, passMsg, failMsg);

		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description :Verify that Batch Print should generate successfully, if user
	 *              selected Source as 'Tag'[RPMXCON-49416]
	 * @throws InterruptedException
	 * @throws ZipException
	 */
	@Test(description = "RPMXCON-49416", enabled = true, dataProvider = "Users", groups = { "regression" })
	public void verifyBatchPrintForTag(String username, String password) throws InterruptedException, ZipException {
		String Tag = "TAG" + Utility.dynamicNameAppender();
		String search = Input.testData1 + " " + Input.docFileExt + ": (xlsx)";

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-49416 Batch Print");
		baseClass.stepInfo("Verify that Batch Print should generate successfully, if user selected Source as 'Folder'");

		// Bulk Tag
		session.basicContentSearch(search);
		session.bulkTag(Tag);

		// Select Folder
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab(Input.tag, Tag, true);
		batchPrint.fillingBasisForPrinting(true, true, null);
		batchPrint.fillingAnalysisTab(false, true, false, false);
		batchPrint.navigateToNextPage(1);
		batchPrint.fillingExceptioanlFileTypeTab(false, Input.documentKey, null, true);

		// filling SlipSheet WIth metadata
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true, null);
		batchPrint.navigateToNextPage(1);

		// Filling Export File Name as 'DocFileName', select Sort by 'DocID'
		batchPrint.fillingExportFormatPage(Input.docFileName, Input.documentKey, false, 20);

		// Download Batch Print File
		String fileName = batchPrint.DownloadBatchPrintFile();

		// verify downloaded file is in Zip Format
		String fileFormat = FilenameUtils.getExtension(fileName);
		String expectedFormat = "zip";

		String passMsg = "Downloaded File Format is  : " + fileFormat;
		String failMsg = "Downloaded File Format is Not As Expected";
		baseClass.textCompareEquals(fileFormat, expectedFormat, passMsg, failMsg);

		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description :Verify that user can select only one tag or a folder or a
	 *              search at a time to perform batch print [RPMXCON-49415]
	 * @throws InterruptedException
	 * @throws ZipException
	 */
	@Test(description = "RPMXCON-49415", enabled = true, dataProvider = "Users", groups = { "regression" })
	public void verifyOnlyOneSourceIsSelected(String username, String password)
			throws InterruptedException, ZipException {
		String Savesearch = "Search" + Utility.dynamicNameAppender();
		String Tag = "TAG" + Utility.dynamicNameAppender();
		String Folder = "FOLDER" + Utility.dynamicNameAppender();

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-49415 Batch Print");
		baseClass.stepInfo(
				"Verify that user can select only one tag or a folder or a search at a time to perform batch print");

		// Bulk Tag , bulk folder and save search
		session.basicContentSearch(Input.testData1);
		session.saveSearch(Savesearch);
		session.bulkTag(Tag);
		session.bulkFolderWithOutHitADD(Folder);

		// Select Search
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab("Search", Savesearch, false);

		// Select Folder
		driver.Navigate().refresh();
		batchPrint.fillingSourceSelectionTab("Folder", Folder, false);

		// Select Tag
		batchPrint.fillingSourceSelectionTab("Tag", Tag, false);

		// verify Source selection
		baseClass.waitForElement(batchPrint.getTagStatus());
		String tagStatus = batchPrint.getTagStatus().GetAttribute("style");
		String folderStatus = batchPrint.getFolderStatus().GetAttribute("style");

		if (folderStatus.contains("none") && tagStatus.equals("")) {
			System.out.println("TAG is selcted  AND Folder Got Unselected");
			baseClass.passedStep("TAG is selcted  AND Folder Got Unselected");
			baseClass.passedStep("User is able to select only one source at a time ");
		} else {
			baseClass.failedStep("User is able to select More than One source at a time");
		}

		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description :Verify user can see the saved searches on Source Selection tab
	 *              of Batch Print [RPMXCON-47799]
	 * @throws InterruptedException
	 * @throws ZipException
	 */
	@Test(description = "RPMXCON-47799", enabled = true, groups = { "regression" })
	public void verifySearchesOnSourceSelection() throws InterruptedException, ZipException {
		String Savesearch1 = "Search" + Utility.dynamicNameAppender();
		String Savesearch2 = "Search" + Utility.dynamicNameAppender();

		// Login As User
		loginPage.loginToSightLine(Input.pa4userName, Input.pa4password);

		baseClass.stepInfo("Test case Id: RPMXCON-47799 Batch Print");
		baseClass.stepInfo("Verify user can see the saved searches on Source Selection tab of Batch Print");

		// Bulk Tag , bulk folder and save search
		session.basicContentSearch(Input.testData1);
		session.saveSearch(Savesearch1);
		driver.Navigate().refresh();
		session.saveSearch(Savesearch2);

		// Select Search
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab("Search", Savesearch1, false);

		driver.Navigate().refresh();
		batchPrint.fillingSourceSelectionTab("Search", Savesearch2, false);

		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description :Verify that batch print should be working fine when the name of
	 *              the exported file is the file name metadata
	 *              field.[RPMXCON-49356]
	 * @throws InterruptedException
	 * @throws ZipException
	 */
	@Test(description = "RPMXCON-49356", enabled = true, groups = { "regression" })
	public void verifyBatchPrintWIthExportAsFilename() throws InterruptedException, ZipException {
		String tagName = Input.randomText + Utility.dynamicNameAppender();
		SessionSearch search = new SessionSearch(driver);

		// Login As User
		loginPage.loginToSightLine(Input.pa4userName, Input.pa4password);

		baseClass.stepInfo("Test case Id: RPMXCON-49356 Batch Print");
		baseClass.stepInfo(
				"Verify that batch print should be working fine when the name of the exported file is the file name metadata field.");

		// Bulk Tag
		search.basicContentSearch(Input.testData1);
		search.bulkTag(tagName);

		// Select TAG
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab(Input.tag, tagName, true);
		batchPrint.fillingBasisForPrinting(true, true, null);
		batchPrint.navigateToNextPage(2);

		// filling SlipSheet WIth metadata
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true, null);
		batchPrint.navigateToNextPage(1);

		// Filling Export File Name as 'DocFileName', select Sort by 'DocID'
		batchPrint.fillingExportFormatPage(Input.docFileName, Input.documentKey, false, 20);

		// Download Batch Print File
		String fileName = batchPrint.DownloadBatchPrintFile();

		// extract zip file
		String extractedFile = batchPrint.extractFile(fileName);

		// verify Downloaded File Count ,filename and Format
		batchPrint.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);
		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : To verify that 'Branding and Redaction' tab is Skipped if
	 *              "Production" is selected from Basis for Printing.[RPMXCON-47875]
	 * @throws InterruptedException
	 * @throws ZipException
	 */
	@Test(description = "RPMXCON-47875", enabled = true, groups = { "regression" })
	public void validateBatchPrintWithProduction() throws InterruptedException, ZipException {
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagName = "Tag" + Utility.dynamicNameAppender();
		String savesearch = "Search" + Utility.dynamicNameAppender();
		String slipsheetDD = "Create new slip sheets";

		SessionSearch search = new SessionSearch(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		// Login As User
		loginPage.loginToSightLine(Input.pa4userName, Input.pa4password);

		baseClass.stepInfo("Test case Id: RPMXCON-47875 Batch Print");
		baseClass.stepInfo(
				"To verify that 'Branding and Redaction' tab is Skipped if \"Production\" is selected from Basis for Printing.");

		// Create tag and folder
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagName, Input.tagNamePrev);

		// Bulk Tag
		search.basicContentSearch(Input.testData1);
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

		// fill slipsheet tab
		batchPrint.selectDropdownFromSlipSheet_prod(slipsheetDD);
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true, null);

		loginPage.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify In Batch Print, PDF file should be generated with the
	 *              selected slip sheets field 'AllProductionBatesRanges' if Basis
	 *              for Printing is selected as Production[RPMXCON-49436]
	 * @throws InterruptedException
	 * @throws ZipException
	 */
	@Test(description = "RPMXCON-49436", enabled = true, groups = { "regression" })
	public void validateBatchPrintNativeWithSlipSheet() throws InterruptedException, ZipException {
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagName = "Tag" + Utility.dynamicNameAppender();
		String slipsheetDD = "Create new slip sheets";

		SessionSearch search = new SessionSearch(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		// Login As User
		loginPage.loginToSightLine(Input.pa4userName, Input.pa4password);

		baseClass.stepInfo("Test case Id: RPMXCON-49436 Batch Print");
		baseClass.stepInfo(
				"Verify In Batch Print, PDF file should be generated with the selected slip sheets field 'AllProductionBatesRanges' if Basis for Printing is selected as Production");

		// Create tag and folder
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagName, Input.tagNamePrev);

		// Bulk Tag
		search.basicContentSearch(Input.testData1);
		search.bulkFolderExisting(foldername);

		// Generate Production
		String productionname = "P" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSection(tagName);
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);

		// Select Folder
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab("Folder", foldername, true);
		batchPrint.fillingBasisForPrinting(true, true, null);
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
		batchPrint.fillingExportFormatPage(Input.docFileName, Input.documentKey, true, 20);

		// Download Batch Print File
		String fileName = batchPrint.DownloadBatchPrintFile();

		// extract Zip file
		String extractedFile = batchPrint.extractFile(fileName);

		// verify Downloaded File Count and Format
		batchPrint.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);

		loginPage.logout();
	}


	
	
	
	
	
	
	
	

	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		baseClass = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR DOCVIEW EXECUTED******");

	}
	
	
}
