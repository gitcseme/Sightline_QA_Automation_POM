package sightline.productions;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Export_Phase2_Regression {
	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	DocViewPage docView;
	Utility utility;

	@BeforeClass(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		base = new BaseClass(driver);
		Input input = new Input();
		input.loadEnvConfig();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());

		driver = new Driver();
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
	}
	
	
	/**
	 * @author Brundha Testcase Id: RPMXCON-48070  Date:7/21/2022
	 * @DescriptionTo Verify document matches multiple criteria (file type or tags),
	 *                the precedence will be from top to bottom.(For Export)
	 * 
	 */
	@Test(description = "RPMXCON-48070", enabled = true, groups = { "regression" })

	public void verifyingPlaceholderTextForFileTypeAndTag() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48070 -Export Component");
		base.stepInfo(
				"To Verify document matches multiple criteria (file type or tags), the precedence will be from top to bottom.(For Export)");

		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagname1 = "Tag" + Utility.dynamicNameAppender();
		String tagname2 = "Tag" + Utility.dynamicNameAppender();
		String newExport = "Ex" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname1, "Select Tag Classification");
		tagsAndFolderPage.createNewTagwithClassification(tagname2, "Select Tag Classification");

		SessionSearch sessionSearch = new SessionSearch(driver);
		int purehit = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname1);

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		String text = page.getProdExport_ProductionSets().getText();
		if (text.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(newExport);
		}
		page.addANewExport(productionname);
		page.fillingDATSection();
		page.selectGenerateOption(false);
		page.nativePlaceholderWithTwoTags(true, tagname1, tagname2);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommitandDownload();
		page.getCopyPath().waitAndClick(10);
		page.verifyingExportFile(purehit,prefixID,suffixID,subBates,Input.searchString4);


		// delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname1, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Brundha RPMXCON-63066 Date:7/21/2022
	 * @DescriptionTo Verify that user should be able to change the automatically
	 *                enabled native placeholdering under TIFF/PDF section from new
	 *                export
	 * 
	 */
	@Test(description = "RPMXCON-63066", enabled = true, groups = { "regression" })

	public void verifyingNativePlaceholderTextInExport() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-63066 -Export Component");
		base.stepInfo(
				"Verify that user should be able to change the automatically enabled native placeholdering under TIFF/PDF section from new export");

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "P" + Utility.dynamicNameAppender();
		String suffixID = "S" + Utility.dynamicNameAppender();
		String newExport = "Ex" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");
		
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.SearchMetaData(Input.docFileType, Input.MetaDataFileType);
		sessionSearch.ViewInDocList();
		DocListPage doclist = new DocListPage(driver);
		int doc = 6;
		doclist.documentSelection(doc);
		doclist.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		String text = page.getProdExport_ProductionSets().getText();
		if (text.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(newExport);
		}
		page.addANewExport(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.verifyingTheDefaultSelectedOptionInNative();
		page.verifyingNativeSectionFileType(Input.MetaDataFileType);
		page.verifyingNativeSectionFileType(Input.NativeFileType);
		driver.waitForPageToBeReady();
		page.getSelectFileTypeInTifffNative(Input.NativeFileType).waitAndClick(10);
		base.waitTime(1);
		String ActualText = page.getNativeDocsPlaceholderText().getText();
		System.out.println(ActualText);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommitandDownload();
		page.getCopyPath().waitAndClick(10);
		page.verifyingExportFile(doc,prefixID,suffixID,subBates,ActualText);
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		page = new ProductionPage(driver);
		String productionname1 = "p" + Utility.dynamicNameAppender();
		String subBates1 = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.selectExportSetFromDropDown();
		page.addANewExport(productionname1);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.verifyingTheDefaultSelectedOptionInNative();
		page.verifyingNativeSectionFileType(Input.MetaDataFileType);
		page.verifyingNativeSectionFileType(Input.NativeFileType);
		page.getSelectFileTypeInTifffNative(Input.NativeFileType).waitAndClick(10);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates1);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(productionname1);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommitandDownload();
		page.getCopyPath().waitAndClick(10);
		page.verifyingExportFile(doc,prefixID,suffixID,subBates1,ActualText);

		// delete tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}
	
	/**
	 * @author Brundha Testcase Id: RPMXCON-50748 Date:7/28/2022
	 * @DescriptionVerify that 'Download' is disabled in Production-Export
	 * 
	 */
	@Test(description = "RPMXCON-50748", enabled = true, groups = { "regression" })

	public void verifyingPlaceholderTextForFileTypeAndTag2() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-50748 -Export Component");
		base.stepInfo("Verify that 'Download' is disabled in Production-Export");

		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String newExport = "Ex" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		String text = page.getProdExport_ProductionSets().getText();
		if (text.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(newExport);
		}
		page.addANewExport(productionname);
		page.fillingDATSection();
		page.selectGenerateOption(false);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommitandDownload();
		page.getQC_Download().isElementAvailable(1);
		String DownloadBtn = page.getQC_Download().GetAttribute("disabled");
		base.textCompareEquals(DownloadBtn, "true", "Download option is disabled as expected","Download option is not disabled");
		String DownloadOptionDisabledTextColor = page.getDownloadDisabledMsg().GetAttribute("class");
		String DownloadOptionDisabledText = page.getDownloadDisabledMsg().getText();
		base.compareTextViaContains(DownloadOptionDisabledText, "Download option not available for Exports","Text is displayed", "Text is not displayed");
		base.compareTextViaContains(DownloadOptionDisabledTextColor, "blue", "Text is displayed in expected color","Text is not displayed in expected color");
				

		// delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Brundha Testcase Id: RPMXCON-48066 Date:7/28/2022
	 * @DescriptionTo Verify selection of one or more tags for placeholdering a set
	 *                of documents.(For Export)
	 * 
	 */
	@Test(description = "RPMXCON-48066", enabled = true, groups = { "regression" })

	public void verifyPlaceholderTextAndTiffPageCount() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48066 -Export Component");
		base.stepInfo("To Verify selection of one or more tags for placeholdering a set of documents.(For Export)");

		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagname1 = "Tag" + Utility.dynamicNameAppender();
		String tagname2 = "Tag" + Utility.dynamicNameAppender();
		String newExport = "Ex" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname1, "Select Tag Classification");
		tagsAndFolderPage.createNewTagwithClassification(tagname2, "Select Tag Classification");

		SessionSearch sessionSearch = new SessionSearch(driver);
		int purehit = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname1);

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		String text = page.getProdExport_ProductionSets().getText();
		if (text.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(newExport);
		}
		page.addANewExport(productionname);
		page.fillingDATSection();
		page.addDATFieldAtSecondRow(Input.productionText, Input.tiffPageCountNam, Input.tiffPageCountText);
		page.selectGenerateOption(false);
		page.nativePlaceholderWithTwoTags(true, tagname1, tagname2);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommitandDownload();
		String actualCopedText = page.getCopiedTextFromClipBoard();
		String parentTab = page.openNewTab(actualCopedText);
		page.goToImageFiles();
		page.verifyTiffFile(purehit, prefixID, suffixID, subBates, Input.searchString4);
		for (int i = 0; i < 2; i++) {
			driver.Navigate().back();
		}
		page.getLoadFileLink().waitAndClick(3);
		page.getDatFileLink(productionname).waitAndClick(3);
		String DatFileText = page.getDATFileText().getText();
		String[] c = DatFileText.split("\\s+");
		for (int i = 1; i < c.length; i++) {
			String Dat = c[i];
			String[] DatFile = Dat.split("þ");
			base.digitCompareEquals(Integer.valueOf(DatFile[3]), Integer.valueOf(Input.pageCount),
					"Tiffpage count is displayed", "Tiff page count is not displayed");
		}
		driver.close();
		driver.getWebDriver().switchTo().window(parentTab);

		// delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname1, Input.securityGroup);
		loginPage.logout();

	}
	/**
	 * @author Brundha RPMXCON-50729 Date:8/02/2022
	 * @Description Verify that Preview option is not available if TIFF/PDF is not selected in Productionp-Export
	 */
	@Test(description = "RPMXCON-50729", enabled = true, groups = { "regression" })
	public void verifyingMP3WithPreviewBtnDisabled() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-50729 -Export component");
		base.stepInfo(
				"Verify that Preview option is not available if TIFF/PDF is not selected in Productionp-Export");

		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String newExport = "Ex" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		String text = page.getProdExport_ProductionSets().getText();
		if (text.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(newExport);
		}
		page.addANewExport(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingMP3FileWithBurnRedaction();
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(productionname);
		page.navigateToNextSection();
		driver.waitForPageToBeReady();
		if(!page.getbtnPreview().isElementAvailable(2)) {
			base.passedStep("Preview button is not displayed as expected");
		}else {
			base.failedStep("Preview button is  displayed");
		}
		loginPage.logout();
	}
	
	/**
	 * @author Brundha RPMXCON-50728 Date:8/02/2022
	 * @Description Verify that if user selects the 'Genrate TIFF' option in Production-Export then preview document should be displays
	 */
	@Test(description = "RPMXCON-50728", enabled = true, groups = { "regression" })
	public void verifyingTIFFFWithPreviewBtnEnabled() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-50728 -Export component");
		base.stepInfo(
				"Verify that if user selects the 'Genrate TIFF' option in Production-Export then preview document should be displays");

		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String newExport = "Ex" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		String text = page.getProdExport_ProductionSets().getText();
		if (text.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(newExport);
		}
		page.addANewExport(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionWithBranding(tagname,Input.searchString4, Input.tagNamePrev);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(productionname);
		page.navigateToNextSection();
		driver.waitForPageToBeReady();
		page.viewingPreviewInSummaryTab();
		page.verifyingPdfgeneration(Input.searchString4);
		loginPage.logout();
	}
	
	/**
	 * @author Brundha RPMXCON-50727 Date:8/04/2022
	 * @Description Verify that if user selects the 'Genrate PDF' option in
	 *              Production-Export then Preview document will be standard
	 *              template file
	 */
	@Test(description = "RPMXCON-50727", enabled = true, groups = { "regression" })
	public void verifyingPDFWithPreviewBtnEnabled() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-50727 -Export component");
		base.stepInfo(
				"Verify that if user selects the 'Genrate PDF' option in Production-Export then Preview document will be standard template file");

		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String newExport = "Ex" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		String text = page.getProdExport_ProductionSets().getText();
		if (text.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(newExport);
		}
		page.addANewExport(productionname);
		page.fillingDATSection();
		page.fillingPDFSection(tagname, Input.searchString4);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(productionname);
		page.navigateToNextSection();
		driver.waitForPageToBeReady();
		page.viewingPreviewInSummaryTab();
		page.verifyingPdfgeneration(Input.searchString4);
		loginPage.logout();
	}

	/**
	 * @author Brundha RPMXCON-63063 Date:8/04/2022
	 * @Description Verify that for new export in TIFF/PDF section native placeholdering should be enabled by default with requested text for spreadsheets and multimedia files
	 */
	@Test(description = "RPMXCON-63063", enabled = true, groups = { "regression" })
	public void verifyingDefaultEnabledOptionInTIFFSection() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-63063 -Export component");
		base.stepInfo("Verify that for new export in TIFF/PDF section native placeholdering should be enabled by default with requested text for spreadsheets and multimedia files");

		String newExport = "Ex" + Utility.dynamicNameAppender();

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		String productionname1 = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		String text = page.getProdExport_ProductionSets().getText();
		if (text.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(newExport);
		}
		page.addANewExportAndSave(productionname);
		page.verifyingTheDefaultSelectedOptionInNative();
		page.verifyingNativeSectionFileType(Input.MetaDataFileType);
		page.verifyingNativeSectionFileType(Input.NativeFileType);
		
		page.navigatingToProductionHomePage();
		page.selectExportSetFromDropDown();
		page.addANewExportAndSave(productionname1);
		page.selectGenerateOption(true);
		driver.waitForPageToBeReady();
		String ActualText = page.getNativeDocsPlaceholderText().getText();
		String ExpectedText = "Document Produced in Native Format.";
		base.textCompareEquals(ActualText, ExpectedText, "Default text in native placeholder is displayed as expected","Text is not Displayed as expected");
		page.verifyingNativeSectionFileType(Input.MetaDataFileType);
		page.verifyingNativeSectionFileType(Input.NativeFileType);

		loginPage.logout();
	}


	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
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
		try {
			// LoginPage.clearBrowserCache();

		} catch (Exception e) {
			System.out.println("Sessions already closed");
		}
	}

}
