package sightline.batchPrint;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

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
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ManageAssignment;
import pageFactory.ProductionPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BatchPrintRegression {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	BatchPrintPage batchPrint;
	SessionSearch session;

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

	
	@DataProvider(name = "Users")
	public Object[][] Users() {
		Object[][] users = {
				{ Input.pa1userName, Input.pa1password },
				{ Input.rmu1userName, Input.rmu1password }, };
		return users;
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
//							 TODO: handle exception
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
