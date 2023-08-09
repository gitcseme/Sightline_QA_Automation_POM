package sightline.batchPrint;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
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

public class BatchPrint_Regression1 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	DocViewMetaDataPage docViewMetaDataPage;
	ManageAssignment manageAssignment;
	DocViewPage docView;
	Utility utility;
	String searchText;
	BatchPrintPage batchPrint;

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

	/**
	 * Author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * Testcase id : 47841 - Verify PDF file should be generated for the selected
	 * production set. Description : To Verify PDF file should be generated for the
	 * selected production set.
	 */
	@Test(description = "RPMXCON-47841", enabled = true, groups = { "regression" })
	public void verifyPdfFileGeneratedBySelectedProductionSet() throws InterruptedException {
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagName = "Tag" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.rmu3userName, Input.rmu3password);
		UtilityLog.info("Logged in as User: " + Input.rmu3userName);

		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47841");

		SessionSearch search = new SessionSearch(driver);
		String searchname = Input.randomText + Utility.dynamicNameAppender();

		baseClass.stepInfo("#### Verify PDF file should be generated for the selected production set ####");

		// Create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.CreateFolderInRMU(foldername);
		tagsAndFolderPage.createNewTagwithClassificationInRMU(tagName, Input.tagNamePrev);

		// Bulk Folder
		baseClass.stepInfo("Basic Search");
		search.basicContentSearch(Input.testData1);

		baseClass.stepInfo("Save searched content");
		search.saveSearch(searchname);

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

		batchPrint = new BatchPrintPage(driver);

		baseClass.stepInfo("Verify PDF file should be generated for the selected production set");
		batchPrint.BatchPrintWithProduction(searchname, Input.orderCriteria, Input.orderType, true, productionname,
				false, false);
		loginPage.logout();

	}

	/**
	 * @Author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * @Testcase id : 49900 - Verify and generate BatchPrint with Search as source.
	 * @Description : Verify and generate BatchPrint with Search as source
	 * @Stabilized
	 */
	@Test(description = "RPMXCON-49900", enabled = true, groups = { "regression" })
	public void verifyBatchPrintWithSearchAsSource() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa3userName, Input.pa3password);
		UtilityLog.info("Logged in as User: " + Input.pa3userName);

		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49900");

		SessionSearch search = new SessionSearch(driver);
		String searchnamePA = Input.randomText + Utility.dynamicNameAppender();

		baseClass.stepInfo("#### Verify and generate BatchPrint with Search as source ####");

		// Bulk Tag
		baseClass.stepInfo("Basic Search");
		search.basicContentSearch(Input.testData1);

		baseClass.stepInfo("Save searched content");
		search.saveSearch(searchnamePA);

		batchPrint = new BatchPrintPage(driver);

		baseClass.stepInfo("Perform batch print by saved search");
		batchPrint.BatchPrintWithProduction(searchnamePA, Input.orderCriteria, Input.orderType, false, null, true,
				false);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu3userName, Input.rmu3password);
		String searchnameRMU = Input.randomText + Utility.dynamicNameAppender();

		baseClass.stepInfo("Basic Search");
		search.basicContentSearch(Input.testData1);

		baseClass.stepInfo("Save searched content");
		search.saveSearch(searchnameRMU);

		batchPrint = new BatchPrintPage(driver);

		baseClass.stepInfo("Perform batch print by saved search");
		batchPrint.BatchPrintWithProduction(searchnameRMU, Input.orderCriteria, Input.orderType, false, null, true,
				false);

		loginPage.logout();

	}

	/**
	 * @Author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * @Testcase id : 48728 - To verify that user can view the total count of Excel
	 *           files is displayed.
	 * @Description : To verify that user can view the total count of Excel files is
	 *              displayed.
	 */
	@Test(description = "RPMXCON-48728", enabled = true, groups = { "regression" })
	public void verifyTotalCountExcelFilesDisplayed() throws InterruptedException {
		loginPage.loginToSightLine(Input.rmu3userName, Input.rmu3password);
		UtilityLog.info("Logged in as User: " + Input.rmu3userName);

		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48728");

		SessionSearch search = new SessionSearch(driver);
		String searchname = Input.randomText + Utility.dynamicNameAppender();

		baseClass.stepInfo("#### To verify that user can view the total count of Excel files is displayed. ####");

		baseClass.stepInfo("Basic Search");
		search.basicContentSearch(Input.searchString1);

		baseClass.stepInfo("Save searched content");
		search.saveSearch(searchname);

		batchPrint = new BatchPrintPage(driver);

		baseClass.stepInfo("Perform batch print by saved search");
		batchPrint.BatchPrintWithProduction(searchname);

		baseClass.stepInfo("Verify Print Excel File Issues Without Skip Excel Print");
		batchPrint.verifyPrintExcelFileIssuesWithoutSkipExcelPrint();

		baseClass.stepInfo("Slip Sheet To Batch Print Creation");
		batchPrint.slipSheetToBatchPrintCreation("CustodianName");

		baseClass.stepInfo("Navigate to back");
		driver.Navigate().back();

		baseClass.stepInfo("Perform batch print by saved search");
		batchPrint.BatchPrintWithProduction(searchname);

		baseClass.stepInfo("Verify Print Excel File Issues Without Skip Excel Print");
		batchPrint.verifyPrintExcelFileIssuesWithoutSkipExcelPrint();

		baseClass.stepInfo(
				"Clickon excel file printing issues without slip excel print and disable place holder toogle.");
		batchPrint.clickOnSkipExcelFilesAndDisablePlaceHolderToogle(true);

		baseClass.stepInfo("Slip Sheet To Batch Print Creation");
		batchPrint.slipSheetToBatchPrintCreation("CustodianName");

		baseClass.stepInfo("Navigate to back");
		driver.Navigate().back();

		baseClass.stepInfo("Perform batch print by saved search");
		batchPrint.BatchPrintWithProduction(searchname);

		baseClass.stepInfo("Verify Print Excel File Issues Without Skip Excel Print");
		batchPrint.verifyPrintExcelFileIssuesWithoutSkipExcelPrint();

		baseClass.stepInfo(
				"Clickon excel file printing issues without slip excel print and disable place holder toogle.");
		batchPrint.clickOnSkipExcelFilesAndDisablePlaceHolderToogle(false);

		batchPrint.verifyRedactorSkipPlaceHolderAndSelectMetaData("CustodianName");

		baseClass.stepInfo("Slip Sheet To Batch Print Creation");
		batchPrint.slipSheetToBatchPrintCreation("CustodianName");
		loginPage.logout();

	}

	/**
	 * @Author : Gopinath Created date: NA Modified date: NA Modified by: Gopinath
	 * @Testcase id : 48727 - To verify that user can view the total count of Media
	 *           files is displayed.
	 * @Description : To verify that user can view the total count of Media files is
	 *              displayed.
	 */
	@Test(description = "RPMXCON-48727", enabled = true, groups = { "regression" })
	public void verifyTotalCountMediaFilesDisplayed() throws InterruptedException {
		loginPage.loginToSightLine(Input.rmu3userName, Input.rmu3password);
		UtilityLog.info("Logged in as User: " + Input.rmu3userName);

		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48727 Sprint 12");

		SessionSearch search = new SessionSearch(driver);
		String searchname = Input.randomText + Utility.dynamicNameAppender();

		baseClass.stepInfo("#### To verify that user can view the total count of Media files is displayed. ####");

		baseClass.stepInfo("Basic Search");
		search.audioSearch(Input.audioSearch, Input.audioLanguage);

		baseClass.stepInfo("Save searched content");
		search.saveSearch(searchname);

		batchPrint = new BatchPrintPage(driver);

		baseClass.stepInfo("Perform batch print by saved search");
		batchPrint.BatchPrintWithProduction(searchname);

		baseClass.stepInfo("Verify details displayed for media files.");
		batchPrint.verifyDetailsDisplayedForMediaFiles();

		baseClass.stepInfo("Verify media files text field displayed by disabling toogle.");
		batchPrint.verifyMediaFilesFieldsDisplayedByDisableToogle();

		baseClass.stepInfo("Verify media files text field displayed by enabling toogle.");
		batchPrint.verifyMediaFilesFieldsDisplayedByEnablingToogle();

		baseClass.stepInfo("Insert metadata field for media files");
		batchPrint.insertMetaDataFieldForMediaFiles(Input.metaDataName);
		loginPage.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description :Verify that PDF should be generated of Batch Print with
	 *              documents having same file name as per selected option 'One PDF
	 *              for all documents', ExportFileName as 'DocID' and Sort By
	 *              DocFileName [RPMXCON-58917]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-58917", enabled = true, dataProvider = "Users", groups = {
			"regression" })
	public void verifyPDFForAllDoc(String username, String password) throws InterruptedException {
		String tagName = Input.randomText + Utility.dynamicNameAppender();
		SessionSearch search = new SessionSearch(driver);

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-58917 Batch Print");
		baseClass.stepInfo(
				"Verify that PDF should be generated of Batch Print with documents having same file name as per selected option 'One PDF for all documents', ExportFileName as 'DocID' and Sort By DocFileName");

		// Bulk Tag
		search.basicMetaDataSearch(Input.docFileName, null, "conFIdenTial", null);
		search.bulkTag(tagName);

		// Select TAG
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab(Input.tag, tagName, true);
		batchPrint.fillingBasisForPrinting(true, true, null);
		batchPrint.navigateToNextPage(2);

		// filling SlipSheet WIth metadata
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true,null);
		batchPrint.navigateToNextPage(1);

		// Filling Export File Name as 'DocID', select Sort by 'DocFileName'
		batchPrint.fillingExportFormatPage(Input.documentKey, Input.docFileName, true, 20);
		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description :Verify that PDF should be generated of Batch Print with
	 *              documents having same file name as per selected option 'One PDF
	 *              for all documents', ExportFileName as 'DocFileName' and Sort By
	 *              'DocID' [RPMXCON-58915]
	 * @throws InterruptedException
	 * @throws ZipException
	 */
	@Test(description = "RPMXCON-58915", enabled = true, dataProvider = "Users", groups = {
			"regression" })
	public void verifyPDFForAllDocWithFileNameDocID(String username, String password)
			throws InterruptedException, ZipException {
		String tagName = Input.randomText + Utility.dynamicNameAppender();
		SessionSearch search = new SessionSearch(driver);

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-58915 Batch Print");
		baseClass.stepInfo(
				"Verify that PDF should be generated of Batch Print with documents having same file name as per selected option 'One PDF for all documents', ExportFileName as 'DocFileName' and Sort By 'DocID'");

		// Bulk Tag
		search.basicMetaDataSearch(Input.docFileName, null, "conFIdenTial", null);
		search.bulkTag(tagName);

		// Select TAG
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab(Input.tag, tagName, true);
		batchPrint.fillingBasisForPrinting(true, true, null);
		batchPrint.navigateToNextPage(2);

		// filling SlipSheet WIth metadata
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true,null);
		batchPrint.navigateToNextPage(1);

		// Filling Export File Name as 'DocFileName', select Sort by 'DocID'
		batchPrint.fillingExportFormatPage(Input.docFileName, Input.documentKey, true, 20);

		// Download ABtch Print File
		String fileName = batchPrint.DownloadBatchPrintFile();

		// extract zip file
		String extractedFile = batchPrint.extractFile(fileName);

		// verify Downloaded File Count and Format
		batchPrint.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);
		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description :Verify that PDF should be generated of Batch Print with
	 *              documents having same file name as per selected option 'One PDF
	 *              for each document', ExportFileName as 'DocID' and Sort By
	 *              DocFileName [RPMXCON-58918]
	 * @throws InterruptedException
	 * @throws ZipException
	 */
	@Test(description = "RPMXCON-58918", enabled = true, dataProvider = "Users", groups = {
			"regression" })
	public void verifyPDFForEachDoc(String username, String password) throws InterruptedException, ZipException {
		String tagName = Input.randomText + Utility.dynamicNameAppender();
		SessionSearch search = new SessionSearch(driver);

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-58918 Batch Print");
		baseClass.stepInfo(
				"Verify that PDF should be generated of Batch Print with documents having same file name as per selected option 'One PDF for each document', ExportFileName as 'DocID' and Sort By DocFileName");

		// Bulk Tag
		search.basicMetaDataSearch(Input.docFileName, null, "conFIdenTial", null);
		search.bulkTag(tagName);

		// Select TAG
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab(Input.tag, tagName, true);
		batchPrint.fillingBasisForPrinting(true, true, null);
		batchPrint.navigateToNextPage(2);

		// filling SlipSheet WIth metadata
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true,null);
		batchPrint.navigateToNextPage(1);

		// Filling Export File Name as 'DOCID', select Sort by 'DOCFileName'
		batchPrint.fillingExportFormatPage(Input.documentKey, Input.docFileName, false, 20);
		loginPage.logout();

	}

	/**
	 * @Jeevitha
	 * @Description :Verify batch print should generate successfully for the files
	 *              containing special characters in DocFileName. [RPMXCON-58973]
	 * @param username
	 * @param password
	 * @throws InterruptedException
	 * @throws ZipException
	 */
	@Test(description = "RPMXCON-58973", enabled = true, dataProvider = "Users", groups = {
			"regression" })
	public void verifySpecialCharactForBatchPrint(String username, String password)
			throws InterruptedException, ZipException {
		String tagName = Input.randomText + Utility.dynamicNameAppender();
		String value = "SpecialCharacter&";
		SessionSearch search = new SessionSearch(driver);

		// Login As User
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-58973 Batch Print");
		baseClass.stepInfo(
				"Verify batch print should generate successfully for the files containing special characters in DocFileName.");

		// Bulk Tag
		search.basicContentSearch(value);
		search.bulkTag(tagName);

		// Select TAG
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab(Input.tag, tagName, true);
		batchPrint.fillingBasisForPrinting(true, true, null);
		batchPrint.navigateToNextPage(2);

		// filling SlipSheet WIth metadata
		batchPrint.fillingSlipSheetWithMetadata(Input.documentKey, true,null);
		batchPrint.navigateToNextPage(1);

		// Filling Export File Name as 'DOCFileName', select Sort by 'DOCID'
		batchPrint.fillingExportFormatPage(Input.docFileName, Input.documentKey, true, 20);
		loginPage.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description : Validate Batch Print generated PDF docs Sort by DocID,
	 *              ExportFileName as 'DocFileName' [Prior Productions
	 *              (TIFFs/PDFs)]with one PDF for all documents [RPMXCON-58921]
	 * @throws InterruptedException
	 * @throws ZipException
	 */
	@Test(description = "RPMXCON-58921", enabled = true, groups = { "regression" })
	public void validateBatchPrintWithProduction() throws InterruptedException, ZipException {
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagName = "Tag" + Utility.dynamicNameAppender();

		SessionSearch search = new SessionSearch(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		// Login As User
		loginPage.loginToSightLine(Input.pa3userName, Input.pa3password);

		baseClass.stepInfo("Test case Id: RPMXCON-58921 Batch Print");
		baseClass.stepInfo(
				"Validate Batch Print generated PDF docs Sort by DocID, ExportFileName as 'DocFileName' [Prior Productions (TIFFs/PDFs)]with one PDF for all documents");

		// Create tag and folder
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagName, Input.tagNamePrev);

		// Bulk Tag
		search.basicMetaDataSearch(Input.docFileName, null, "conFIdenTial", null);
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
		batchPrint.navigateToNextPage(2);

		// Filling Export File Name as 'DOCFileName', select Sort by 'DOCID'
		batchPrint.fillingExportFormatPage(Input.docFileName, Input.documentKey, true, 20);

		// Download ABtch Print File
		String fileName = batchPrint.DownloadBatchPrintFile();

		// extract zip file
		String extractedFile = batchPrint.extractFile(fileName);

		// verify Downloaded File Count and Format
		batchPrint.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);
		loginPage.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify when user does Batch Print with prior production -
	 *              While printing specific produced documents with DocID, All the
	 *              documents with name as DocID should be displayed.[RPMXCON-60553]
	 * @throws InterruptedException
	 * @throws ZipException
	 */
	@Test(description = "RPMXCON-60553", enabled = true, groups = { "regression" })
	public void validateBatchPrintWithPriorProduction() throws InterruptedException, ZipException {
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagName = "Tag" + Utility.dynamicNameAppender();

		SessionSearch search = new SessionSearch(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		// Login As User
		loginPage.loginToSightLine(Input.pa3userName, Input.pa3password);

		baseClass.stepInfo("Test case Id: RPMXCON-60553 Batch Print");
		baseClass.stepInfo(
				"Verify when user does Batch Print with prior production - While printing specific produced documents with DocID, All the documents with name as DocID should be displayed.");

		// Create tag and folder
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagName, Input.tagNamePrev);

		// Bulk Tag
		search.basicMetaDataSearch(Input.docFileName, null, "conFIdenTial", null);
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
		batchPrint.navigateToNextPage(2);

		// Filling Export File Name as 'DOCFileName', select Sort by 'DOCID'
		batchPrint.fillingExportFormatPage(Input.documentKey, Input.documentKey, false, 20);

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
	 * @Description : Verify the ‘back’ button from top left (above page title) to
	 *              top right (next to the group of buttons) in Batch Print -all
	 *              tabs [RPMXCON-58909]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-58909", enabled = true, groups = { "regression" })
	public void verifyBackBtnInBatchPrintPage() throws InterruptedException {
		String tagName = "TagName" + utility.dynamicNameAppender();
		SessionSearch search = new SessionSearch(driver);
		batchPrint = new BatchPrintPage(driver);

		// login as PAU
		loginPage.loginToSightLine(Input.pa3userName, Input.pa3password);

		baseClass.stepInfo("Test case Id: RPMXCON-58909");
		baseClass.stepInfo(
				"Verify the ‘back’ button from top left (above page title) to top right (next to the group of buttons) in Batch Print -all tabs");

		// creating tag
		search.basicContentSearch(Input.searchString1);
		search.bulkTag(tagName);

		// navigating through Batch Print Tabs
		batchPrint.navigateToBatchPrintPage();
		batchPrint.navigateAndVerifyBackBtn("tag", tagName, false);

		// logout
		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : To verify that user can view the details of Basis for
	 *              Printing.[RPMXCON-47795]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-47795", enabled = true, dataProvider = "Users", groups = {
			"regression" })
	public void verifyAnalysisTab(String username, String password) throws InterruptedException {
		String searchName = "Saerch" + utility.dynamicNameAppender();

		SessionSearch search = new SessionSearch(driver);
		batchPrint = new BatchPrintPage(driver);

		// login as USER
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-47795 BatchPrint");
		baseClass.stepInfo("To verify that user can view the details of Basis for Printing.");

		// creating tag
		search.basicContentSearch(Input.searchString1);
		search.saveSearch(searchName);

		// Select Search
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab("Search", searchName, false);
		batchPrint.fillingBasisForPrinting(true, true, null);

		// verify Analysis Tab
		batchPrint.fillingAnalysisTab(false, false, true, false);

		// logout
		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Validate BatchPrint - Generating single PDF file for corpus
	 *              containing multiple files with same name but have different file
	 *              extension[RPMXCON-50040]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-50040", enabled = true, dataProvider = "Users", groups = { "regression" })
	public void generateSinglePdfForMultipleFile(String username, String password) throws InterruptedException, ZipException {
		String tagName = "TAG" + utility.dynamicNameAppender();

		SessionSearch search = new SessionSearch(driver);
		batchPrint = new BatchPrintPage(driver);

		// login as PAU
		loginPage.loginToSightLine(username, password);
		
		baseClass.stepInfo("Test case Id: RPMXCON-50040 BatchPrint");
		baseClass.stepInfo(
				"Validate BatchPrint - Generating single PDF file for corpus containing multiple files with same name but have different file extension");

		// creating tag
		search.navigateToSessionSearchPageURL();
		search.newMetaDataSearchInBasicSearch(Input.docFileName, "SM134 Proforma");
		search.bulkTag(tagName);

		// Select Search
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab("Tag", tagName, true);
		batchPrint.fillingBasisForPrinting(true, true, null);

		batchPrint.navigateToNextPage(2);
		batchPrint.disableSlipSheet(true);
		batchPrint.navigateToNextPage(1);

		// Filling Export File Name as 'DOCFileName', select Sort by 'DOCID'  
		//one pdf for all doc
		batchPrint.fillingExportFormatPage(Input.docFileName, Input.masterDateText, true, 30);

		// Download Batch Print File
		String fileName = batchPrint.DownloadBatchPrintFile();

		// extract zip file
		String extractedFile = batchPrint.extractFile(fileName);

		// verify Downloaded File Count ,filename and Format
		List<String> fileList = new ArrayList<>();
		fileList = batchPrint.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);

		if (fileList.size() > 1) {
			baseClass.passedStep("Second PDF file is generated since total page count is more than 250 pages.");
		}
		
		// logout
		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Validate BatchPrint - Generating individual PDF file for
	 *              corpus containing multiple files(document with more than 250
	 *              page) with same name but have different file
	 *              extension[RPMXCON-50041]
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-50041", enabled = true, dataProvider = "Users", groups = { "regression" })
	public void generateSepeartePdfForMultipleFile(String username, String password) throws InterruptedException, ZipException {
		String tagName = "TAG" + utility.dynamicNameAppender();

		SessionSearch search = new SessionSearch(driver);
		batchPrint = new BatchPrintPage(driver);

		// login as PAU
		loginPage.loginToSightLine(username, password);

		baseClass.stepInfo("Test case Id: RPMXCON-50041 BatchPrint");
		baseClass.stepInfo(
				"Validate BatchPrint - Generating individual PDF file for corpus containing multiple files(document with more than 250 page) with same name but have different file extension");

		// creating tag
		search.navigateToSessionSearchPageURL();
		search.newMetaDataSearchInBasicSearch(Input.docFileName, "SM134 Proforma");
		search.bulkTag(tagName);

		// Select Search
		batchPrint.navigateToBatchPrintPage();
		batchPrint.fillingSourceSelectionTab("Tag", tagName, true);
		batchPrint.fillingBasisForPrinting(true, true, null);

		batchPrint.navigateToNextPage(2);
		batchPrint.disableSlipSheet(true);
		batchPrint.navigateToNextPage(1);

		// Filling Export File Name as 'DOCFileName', select Sort by 'DOCID'
		//one pdf for each doc
		batchPrint.fillingExportFormatPage(Input.docFileName, Input.masterDateText, false, 30);

		// Download Batch Print File
		String fileName = batchPrint.DownloadBatchPrintFile();
		System.out.println(fileName);

		// extract zip file
		String extractedFile = batchPrint.extractFile(fileName);

		// verify Downloaded File Count ,filename and Format
		List<String> fileList = new ArrayList<>();
		fileList = batchPrint.verifyDownloadedFileCountAndFormat(Input.fileDownloadLocation + "\\" + extractedFile);

		if (fileList.size() > 1) {
			baseClass.passedStep("Seperate PDF is generated for every individual file");
		}

		// logout
		loginPage.logout();
		
	}

	@DataProvider(name = "Users")
	public Object[][] Users() {
		Object[][] users = { { Input.pa3userName, Input.pa3password }, { Input.rmu3userName, Input.rmu3password }, };
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
