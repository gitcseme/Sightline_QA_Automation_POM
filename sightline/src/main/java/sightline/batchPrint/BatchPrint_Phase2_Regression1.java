package sightline.batchPrint;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.Callable;

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
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BatchPrint_Phase2_Regression1 {

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
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		baseClass.stepInfo("Test case Id: RPMXCON-47829 Batch Print");
		baseClass.stepInfo("To verify that 'Enable Slip Sheets' is off/on then panel should be disabled");

		// fetch Fields associated with Security group
		security.selectSecurityGroupAndClickOnProjectFldLink(Input.securityGroup);
		baseClass.waitForElementCollection(security.getSelectdFieldsList());
		List<String> sgAssociatedFields = baseClass.availableListofElements(security.getSelectdFieldsList());

		// logout
		loginPage.logout();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

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
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

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

		if (username.equals(Input.pa1userName)) {
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
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

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
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

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
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

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
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

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

	/**
	 * @Author Jeevitha
	 * @Description : To verify that 'AllProductionBatesRanges' should display
	 *              correct value on Batch Print slip sheets[RPMXCON-49435]
	 * @throws InterruptedException
	 * @throws ZipException
	 */
	@Test(description = "RPMXCON-49435", enabled = true, dataProvider = "Users", groups = { "regression" })
	public void validateBatchPrintProductionWithSlipSheet(String username, String password)
			throws InterruptedException, ZipException {
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagName = "Tag" + Utility.dynamicNameAppender();
		String slipsheetDD = "Create new slip sheets";

		SessionSearch search = new SessionSearch(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-49435 BatchPrint");
		baseClass.stepInfo(
				"To verify that 'AllProductionBatesRanges' should display correct value on Batch Print slip sheets");

		// Create tag and folder
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		if (username.equals(Input.pa1userName)) {
			tagsAndFolderPage.createNewTagwithClassification(tagName, Input.tagNamePrev);
		} else {
			tagsAndFolderPage.createNewTagwithClassificationInRMU(tagName, Input.tagNamePrev);
		}

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
		batchPrint.fillingBasisForPrinting(false, true, productionname);
		batchPrint.disableSlipSheet(true);
		batchPrint.selectDropdownFromSlipSheet_prod(slipsheetDD);
		batchPrint.fillingSlipSheetWithMetadata("AllProductionBatesRanges", true, null);

		// Filling Export File Name as 'DOCFileName', select Sort by 'DOCID'
		batchPrint.fillingExportFormatPage(Input.docFileName, Input.documentKey, true, 20);

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
	 * @Description : To verify that user can use the prior production slip sheets
	 *              for batch print.[RPMXCON-47830]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-47830", enabled = true, groups = { "regression" })
	public void verifyCanUsePrioProdForBatchPrint() throws Exception {
		SoftAssert softassert = new SoftAssert();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String Folder = "Folder" + Utility.dynamicNameAppender();
		String slipsheetDD = "Create new slip sheets";
		DocListPage doclist = new DocListPage(driver);

		// Login As User
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		baseClass.stepInfo("Test case Id: RPMXCON-47830 Batch Print");
		baseClass.stepInfo("To verify that user can use the prior production slip sheets for batch print.");

		// create folder in Default SG
		tagsAndFolderPage.CreateFolder(Folder, Input.securityGroup);

		// configure query & bulk folder
		session.basicContentSearch(Input.testData1);
		session.bulkFolderExisting(Folder);
		session.ViewInDocListWithOutPureHit();

		// sort in Ascending Order
		doclist.sortColumn(true, Input.documentKey, true);

		// expected Downloaded Document Names list ,Desc Order of LastSaveDate
		int index = baseClass.getIndex(doclist.getColumnHeader(), Input.documentKey);
		baseClass.waitForElementCollection(doclist.GetColumnData(index));
		List<String> docidList = doclist.availableListofElementsForDocList(doclist.GetColumnData(index));

		// Generate Production with dat,TIFF,native & select Slipsheet as docid
		String productionname = "P" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		System.out.println(productionname);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionwithBurnRedaction();
		page.fillingSlipSheetWithMetadataInTiffSection(Input.documentKey);
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, Folder, productionname,
				beginningBates);

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

		// Filling Export File Name as 'Docfilename', select Sort by 'DOCID'
		batchPrint.fillingExportFormatPage(Input.docFileName, Input.documentKey, false, 20);

		// Download Batch Print File
		String fileName = batchPrint.DownloadBatchPrintFile();

		// extract zip file
		String extractedFile = batchPrint.extractFile(fileName);

		// verify Downloaded File Count ,filename and Format
		List<String> actualFileName = batchPrint
				.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);

		// verify ProductionSlipsheet name in Downloaded file
		boolean result = baseClass.compareListViaContainsTrimSpace(actualFileName, docidList);
		baseClass.printResutInReport(result, "Downloaded FileNames has selected production slipsheet Name",
				"Production slipsheet name is not generated", "Pass");

		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify when user selects a tag/folder with zero count for
	 *              batch print[RPMXCON-49418]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-49418", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void verifyWhenUserselectZeroDoc(String username, String password) throws Exception {
		String tagname = "TAG" + Utility.dynamicNameAppender();

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-49418 Batch Print");
		baseClass.stepInfo("Verify when user selects a tag/folder with zero count for batch print");

		// create Empty Tag
		tagsAndFolderPage.CreateTag(tagname, Input.securityGroup);

		// Select Tag for source tab
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab(Input.tag, tagname, true);
		batchPrint.fillingBasisForPrinting(true, true, null);

		// verify Displayed Message in Analysis tab
		batchPrint.verifyCurrentTab("Analysis");
		batchPrint.verifyAnalysisReportMessage();

		// Delete Tag
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.DeleteTag(tagname, Input.securityGroup);

		// Logout
		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that for RMU, when Tag/Folder is unmapped should not be
	 *              displayed under source selection for batch print [RPMXCON-49432]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-49432", enabled = true, groups = { "regression" })
	public void verifyUnmappedTagIsnotDisplayed() throws Exception {
		String tagname = "TAG" + Utility.dynamicNameAppender();
		SecurityGroupsPage security = new SecurityGroupsPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-49432 Batch Print");
		baseClass.stepInfo(
				"Verify that for RMU, when Tag/Folder is unmapped should not be displayed under source selection for batch print");

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged In As PA");

		// create Tag
		tagsAndFolderPage.CreateTag(tagname, Input.securityGroup);

		// Unmap tag from security group
		security.navigateToSecurityGropusPageURL();
		security.unmapTagFromSecurityGrp(Input.securityGroup, tagname);

		// Logout
		loginPage.logout();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged In As RMU");
		baseClass.selectsecuritygroup(Input.securityGroup);

		// verify Unmapped Tag present in source selection
		batchPrint.navigateToBatchPrintPage();
		boolean tagStatus = batchPrint.fillingSourceSelectionTab(Input.tag, tagname, false);
		baseClass.printResutInReport(tagStatus, "Source slection tab doesnot Contain unmapped tag : " + tagname,
				"Source Selection Tab contains unmapped Tag", "Fail");

		// Logout
		loginPage.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify that for RMU, Tag/Folder should be displayed in that
	 *              security group only [RPMXCON-49429]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-49429", enabled = true, groups = { "regression" })
	public void verifyTagOrFolderDislayedInThatSG() throws Exception {
		String tagname = "TAG" + Utility.dynamicNameAppender();
		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname2 = "TAG" + Utility.dynamicNameAppender();
		String foldername2 = "Folder" + Utility.dynamicNameAppender();
		String securityGrp = "Security" + Utility.dynamicNameAppender();

		SecurityGroupsPage security = new SecurityGroupsPage(driver);
		UserManagement user = new UserManagement(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-49429 Batch Print");
		baseClass.stepInfo("Verify that for RMU, Tag/Folder should be displayed in that security group only");

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged In As PA");

		// add security grp and assign access to RMU user
		security.navigateToSecurityGropusPageURL();
		security.AddSecurityGroup(securityGrp);
		user.assignAccessToSecurityGroups(securityGrp, Input.rmu1userName);

		// Logout
		loginPage.logout();

		// Login As RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged In As RMU");

		// create Tag & Folder in default SG
		baseClass.selectsecuritygroup(Input.securityGroup);
		tagsAndFolderPage.CreateTag(tagname, Input.securityGroup);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// create Tag & Folder in Other SG
		baseClass.selectsecuritygroup(securityGrp);
		tagsAndFolderPage.CreateTag(tagname2, securityGrp);
		tagsAndFolderPage.CreateFolder(foldername2, securityGrp);

		// select default sg
		baseClass.selectsecuritygroup(Input.securityGroup);

		// verify only default sg Tags present in source selection tab
		batchPrint.navigateToBatchPrintPage();
		boolean tagStatus = batchPrint.fillingSourceSelectionTab(Input.tag, tagname, true);
		driver.Navigate().refresh();
		boolean tag2Status = batchPrint.fillingSourceSelectionTab(Input.tag, tagname2, false);
		baseClass.printResutInReport(tagStatus, "Source Selection tab Contain tag created in this sg : " + tagname,
				"Source Selection Tab doesnot contains Tag", "Pass");
		baseClass.printResutInReport(tag2Status,
				"Source Selection tab Doesnot Contain tag created in Different sg: " + tagname2,
				"Source Selection Tab contains Tag of other SG", "Fail");

		// verify only default sg Folder present in source selection tab
		driver.Navigate().refresh();
		boolean folderStatus = batchPrint.fillingSourceSelectionTab("Folder", foldername, true);
		driver.Navigate().refresh();
		boolean folder2Status = batchPrint.fillingSourceSelectionTab("Folder", foldername2, false);
		baseClass.printResutInReport(folderStatus,
				"Source Selection tab Contain folder created in this sg : " + foldername,
				"Source Selection Tab doesnot contains Folder", "Pass");
		baseClass.printResutInReport(folder2Status,
				"Source Selection tab Doesnot Contain folder created in other sg : " + foldername2,
				"Source Selection Tab contains Folder of other SG", "Fail");

		// Logout
		loginPage.logout();

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		// delete sg
		security.deleteSecurityGroups(securityGrp);

		// Logout
		loginPage.logout();
	}

	/**
	 * @author
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :To verify that on click on Back button, it will redirect to
	 *              Source Selection tab.RPMXCON-47798
	 */

	@Test(description = "RPMXCON-47798", dataProvider = "Users", enabled = true, groups = { "regression" })
	public void verifyOnClickBackButtonRedirectToSourceSelectionTab(String username, String password) throws Exception {
		String searchName = "savedSearch" + Utility.dynamicNameAppender();
		SavedSearch savedSearch = new SavedSearch(driver);
		String expectCurrentTab = "Basis for Printing";
		String expectNavigationTab = "Source Selection";

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-47798 Batch Print");
		baseClass.stepInfo("To verify that on click on Back button, it will redirect to Source Selection tab.");

		// performing basic content search and saving the search Result
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName);

		// selecting the source in source selection tab and navigating to next tab
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab("Search", searchName, false);
		baseClass.waitTime(4);

		// verifying redirect to previous tab
		batchPrint.ClickBackButtonAndVerify(true, expectCurrentTab, 1, true, expectNavigationTab);

		// deleting the savedSearch
		savedSearch.SaveSearchDelete(searchName);

		// Logout
		loginPage.logout();
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

		// verify current tab
		batchPrint.verifyCurrentTab("Branding");

		// verify Warning msg is not displayed
		driver.waitForPageToBeReady();
		baseClass.ValidateElement_Absence(baseClass.getWarningsMsgHeader(), "Warning Message is not Displayed");

		// verify navigation to export page
		batchPrint.navigateToNextPage(1);
		batchPrint.verifyCurrentTab("Export Format");

		// logout
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
		//batchPrint.getSelectFolder(Folder).waitAndClick(5);
		//batchPrint.navigateToNextPage(1);
		//batchPrint.fillingAnalysisTab(false, false, false, true);
		//strong[text()='Folder All Skipped Documents:']//following::label/i
		////input[@id='ignoreRadioButton']//parent::label
		//batchPrint.navigateToNextPage(1);
//		try {
//			batchPrint.getEnableAnalyseToggleButton().Displayed();

//			batchPrint.getEnableAnalyseToggleButton().waitAndClick(5);
			
//		} catch (Exception e1) {

//			System.out.println("No enable Analyse toggle is  displayed");
//		}
//		batchPrint.navigateToNextPage(1);
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
