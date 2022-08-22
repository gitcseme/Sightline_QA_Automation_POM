package testScriptsRegressionSprint16;

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
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.DataSets;
import pageFactory.DocListPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Production_Sprint16_Regression2 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	DocViewRedactions docViewRedact;
	Input ip;
	ProductionPage productionPage;
	TagsAndFoldersPage tagsAndFolderPage;
	SessionSearch sessionSearch;
	SoftAssert softAssertion;
	String prefixID = "A" + Utility.dynamicNameAppender();
	String suffixID = "P" + Utility.dynamicNameAppender();
	String foldername;
	String tagname;
	String productionname;
	String batesNumber;

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		Input in = new Input();
		in.loadEnvConfig();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());

		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
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

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56001
	 * @Description:Verify the Download option on Production QC page
	 **/

	@Test(description = "RPMXCON-56001", enabled = true, groups = { "regression" })
	public void verifyDownloadOption() throws Exception {

		baseClass.stepInfo("Test case Id:RPMXCON-56001- Production Sprint 16");
		baseClass.stepInfo("Verify the Download option on Production QC page");
		UtilityLog.info(Input.prodPath);
		foldername = "Prod_Folder" + Utility.dynamicNameAppender();
		tagname = "Prod_Tag" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Create tags and folders");

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		baseClass.stepInfo("perform basic search and bulk folder");
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		baseClass.stepInfo("Create production using required inputs");
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		String prodName = page.addANewProduction(productionname);
		System.out.println("created a new production - " + prodName);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingDocumentPage(beginningBates, prefixID, suffixID);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.toStartGenerate();
		page.getQC_Download().waitAndClick(10);
		page.getQC_Downloadbutton_allfiles().isElementAvailable(10);
		page.getClkBtnDownloadDATFiles().isElementAvailable(10);
		page.getSelectSharableLinks().isElementAvailable(10);
		baseClass.passedStep("Verified the Download option on Production QC page");
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48192
	 * @Description:To Verify Productions Generation for MP3 files using document
	 *                 level numbering.
	 **/

	@Test(description = "RPMXCON-48192", enabled = true, groups = { "regression" })
	public void verifyMP3WithDocumentLevelNumbering() throws Exception {

		baseClass.stepInfo("Test case Id:RPMXCON-48192- Production Sprint 16");
		baseClass.stepInfo("To Verify Productions Generation for MP3 files using document level numbering.");
		UtilityLog.info(Input.prodPath);
		foldername = "Prod_Folder" + Utility.dynamicNameAppender();
		tagname = "Prod_Tag" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Create tags and folders");

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		baseClass.stepInfo("perform basic search and bulk folder");
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch("DocFileType", "mp3");
		sessionSearch.ViewInDocList();
		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(2);
		doclist.bulkTagExisting(tagname);

		baseClass.stepInfo("Create production using required inputs");
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		String prodName = page.addANewProduction(productionname);
		System.out.println("created a new production - " + prodName);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingPageWithDocumentLevelAndSubBates(beginningBates,prefixID,suffixID);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		baseClass.passedStep("Verified Productions Generation for MP3 files using document level numbering");
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48268
	 * @Description:To Verify In Productions, Ending Bates generated in a production
	 *                 Should have correct Value..
	 **/

	@Test(description = "RPMXCON-48268", enabled = true, groups = { "regression" })
	public void verifyEndingBates() throws Exception {

		baseClass.stepInfo("Test case Id:RPMXCON-48268- Production Sprint 16");
		baseClass.stepInfo(
				"To Verify In Productions, Ending Bates generated in a production Should have correct Value.");
		UtilityLog.info(Input.prodPath);
		foldername = "Prod_Folder" + Utility.dynamicNameAppender();
		tagname = "Prod_Tag" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Create tags and folders");
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		baseClass.stepInfo("perform basic search and bulk folder");
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		baseClass.stepInfo("Create production using required inputs");
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		String beginingAttachgBates = "Begin" + page.getRandomNumber(2);
		String endingBates = "End" + page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		String prodName = page.addANewProduction(productionname);
		System.out.println("created a new production - " + prodName);
		page.fillingDATSection();
		page.addDATFieldAtSecondRow(Input.bates, "EndingBates", endingBates);
		page.addDATFieldAtThirdRow(Input.bates, "BeginingAttachBates", beginingAttachgBates);
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();
		page.OCR_Verification__BatesNo_In_GeneratedFile(prefixID, suffixID, endingBates);
		baseClass.passedStep(
				"Verified In Productions, Ending Bates generated in a production Should have correct Value");
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48784
	 * @Description:To verify that document should produced with 'Tech Issues Docs'
	 *                 placeholdering by selecting only one Tag
	 **/

	@Test(description = "RPMXCON-48784", enabled = true, groups = { "regression" })
	public void verifyTechIssue() throws Exception {

		baseClass.stepInfo("Test case Id:RPMXCON-48784- Production Sprint 16");
		baseClass.stepInfo(
				"To verify that document should produced with 'Tech Issues Docs' placeholdering by selecting only one Tag");
		UtilityLog.info(Input.prodPath);
		foldername = "Prod_Folder" + Utility.dynamicNameAppender();
		tagname = "Prod_Tag" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Create tags and folders");
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.technicalIssue);

		baseClass.stepInfo("perform basic search and bulk folder");
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		baseClass.stepInfo("Create production using required inputs");
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int firstFile = Integer.parseInt(beginningBates);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		String prodName = page.addANewProduction(productionname);
		System.out.println("created a new production - " + prodName);
		page.fillingDATSection();
		page.fillingTiffSectionTechIssueWithEnteringText(tagname, Input.technicalIssue);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		int doccount = page.fillingGeneratePageWithContinueGenerationPopup();
		int lastFile = firstFile + doccount;
		page.extractFile();
		page.OCR_Verification_In_Generated_Tiff_tess4j(firstFile, lastFile, prefixID, suffixID, Input.technicalIssue);
		baseClass.passedStep(
				"verified that document should produced with 'Tech Issues Docs' placeholdering by selecting only one Tag");
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48164
	 * @Description:To Verify for Error Message on Mark Complete for MP3 Files
	 *                 Selection ,without enabling LST generation for it.
	 **/

	@Test(description = "RPMXCON-48164", enabled = true, groups = { "regression" })
	public void verifyErrorMessage() throws Exception {

		baseClass.stepInfo("Test case Id:RPMXCON-48164- Production Sprint 16");
		baseClass.stepInfo(
				"To Verify for Error Message on Mark Complete for MP3 Files Selection ,without enabling LST generation for it.");
		UtilityLog.info(Input.prodPath);
		foldername = "Prod_Folder" + Utility.dynamicNameAppender();
		tagname = "Prod_Tag" + Utility.dynamicNameAppender();
		String expectedMsg = "For the MP3 Files component, you must either enable LST load file option or specify the MP3 file path in the DAT, in order to generate a load file for the generated MP3s files.";

		baseClass.stepInfo("Create production using required inputs");
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		String prodName = page.addANewProduction(productionname);
		System.out.println("created a new production - " + prodName);
		page.fillingDATSection();
		page.getAdvancedProductionComponent().waitAndClick(10);
		page.getMP3Tab().waitAndClick(10);
		page.getMP3CheckBox().waitAndClick(10);
		page.getAdvancedTabInMP3().waitAndClick(10);
		page.getMp3GenerateLoadFile().waitAndClick(10);
		baseClass.stepInfo("LST Toggle is turned OFF");
		driver.scrollPageToTop();
		baseClass.waitForElement(page.getMarkCompleteLink());
		page.getMarkCompleteLink().waitAndClick(10);
		baseClass.VerifyWarningMessage(expectedMsg);

	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48181
	 * @Description:To Verify Native Section with various options(Produce Native
	 *                 Files selection/Produce Load/Select Tags/Advance Show
	 *                 Hide/and Toggles in Advance)
	 **/

	@Test(description = "RPMXCON-48181", enabled = true, groups = { "regression" })
	public void verifyNativeSectionOptions() throws Exception {

		baseClass.stepInfo("Test case Id:RPMXCON-48181- Production Sprint 16");
		baseClass.stepInfo(
				"To Verify Native Section with various options Produce Native Files selection/Produce Load/Select Tags/Advance Show Hide/and Toggles in Advance");
		UtilityLog.info(Input.prodPath);

		baseClass.stepInfo("Create production using required inputs");
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		String prodName = page.addANewProduction(productionname);
		System.out.println("created a new production - " + prodName);
		page.getNativeTab().waitAndClick(10);
		page.getNative_AdvToggle().ScrollTo();
		page.getNative_AdvToggle().waitAndClick(10);
		page.RedactedMsgInNative().isElementAvailable(10);
		page.radioBtnInNative().isElementAvailable(10);
		page.radioButtonInNative().isElementAvailable(10);
		page.nativeTextInNative().isElementAvailable(10);
		page.getNative_GenerateLoadFileLST().isElementAvailable(10);
		baseClass.passedStep(
				"Verified Native Section with various options Produce Native Files selection/Produce Load/Select Tags/Advance Show Hide/and Toggles in Advance");

	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48182
	 * @Description:To Verify TIFF Section with various options
	 **/

	@Test(description = "RPMXCON-48182", enabled = true, groups = { "regression" })
	public void verifyTIFFSectionOptions() throws Exception {

		baseClass.stepInfo("Test case Id:RPMXCON-48182- Production Sprint 16");
		baseClass.stepInfo("To Verify TIFF Section with various options");
		UtilityLog.info(Input.prodPath);
		baseClass.stepInfo("Create production using required inputs");
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		String prodName = page.addANewProduction(productionname);
		System.out.println("created a new production - " + prodName);
		page.getTIFFTab().waitAndClick(10);
		page.getTIFFChkBox().waitAndClick(10);
		page.pageOptionsText().isElementAvailable(10);
		page.brandingText().isElementAvailable(10);
		page.placeHolderText().isElementAvailable(10);
		page.redactionsText().ScrollTo();
		page.redactionsText().isElementAvailable(10);
		page.getAdvancedTabInTIFF().waitAndClick(10);
		driver.scrollingToBottomofAPage();
		page.slipSheetsText().ScrollTo();
		page.slipSheetsText().isElementAvailable(10);
		baseClass.passedStep("Verified TIFF Section with various options");
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48184
	 * @Description:To Verify Production Location For Production Root
	 *                 Path/Production Directory/Load File Path/Volume
	 *                 Included/Production Component Folders.
	 **/

	@Test(description = "RPMXCON-48184", enabled = true, groups = { "regression" })
	public void verifyProductionLocationComponent() throws Exception {

		baseClass.stepInfo("Test case Id:RPMXCON-48184- Production Sprint 16");
		baseClass.stepInfo(
				"To Verify Production Location For Production Root Path/Production Directory/Load File Path/Volume Included/Production Component Folders.");
		UtilityLog.info(Input.prodPath);
		foldername = "Prod_Folder" + Utility.dynamicNameAppender();
		tagname = "Prod_Tag" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Create tags and folders");
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		baseClass.stepInfo("perform basic search and bulk folder");
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		baseClass.stepInfo("Create production using required inputs");
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		String prodName = page.addANewProduction(productionname);
		System.out.println("created a new production - " + prodName);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.getlstProductionRootPaths().isElementAvailable(10);
		page.getProductionOutputLocation_ProductionDirectory().isElementAvailable(10);
		System.out.println("Production Location : " + page.getProductionOutputLocation_ProductionDirectory().getText());
		page.loadFilePath().isElementAvailable(10);
		page.GetVolumeName().isElementAvailable(10);
		baseClass.passedStep("Verified Production Location component");
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48185
	 * @Description:To Verify Summary and Preview for (Total Documents/Total
	 *                 Pages/Number of Custodians/Number of Natives/Exception
	 *                 Files/Missing Text Files/First and Last Doc ID's/Privileged
	 *                 Documents/Documents identified by Production Guard/Redacted
	 *                 Documents/OCR/TIFF
	 **/

	@Test(description = "RPMXCON-48185", enabled = true, groups = { "regression" })
	public void verifySummaryComponent() throws Exception {

		baseClass.stepInfo("Test case Id:RPMXCON-48185- Production Sprint 16");
		baseClass.stepInfo(
				"To Verify Summary and Preview for (Total Documents/Total Pages/Number of Custodians/Number of Natives/Exception Files/Missing Text Files/First and Last Doc ID's/Privileged Documents/Documents identified by Production Guard/Redacted Documents/OCR/TIFF");
		UtilityLog.info(Input.prodPath);
		foldername = "Prod_Folder" + Utility.dynamicNameAppender();
		tagname = "Prod_Tag" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Create tags and folders");
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		baseClass.stepInfo("perform basic search and bulk folder");
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		baseClass.stepInfo("Create production using required inputs");
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		String prodName = page.addANewProduction(productionname);
		System.out.println("created a new production - " + prodName);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.getValueTotalDocuments().isDisplayed();
		System.out.println("Total Documents " + page.getValueTotalDocuments().getText());
		page.getValueTotalPagesCount().isDisplayed();
		System.out.println("Total pages count " + page.getValueTotalPagesCount().getText());
		page.getNoOfCustodians().isDisplayed();
		System.out.println("Number of custodians " + page.getNoOfCustodians().getText());
		page.getDocPages().isDisplayed();
		System.out.println("Document pages " + page.getDocPages().getText());
		page.getTextMp3File().isDisplayed();
		System.out.println("MP3 file " + page.getTextMp3File().getText());
		page.exceptionDocCount().isDisplayed();
		System.out.println("Exception Document Count " + page.exceptionDocCount().getText());
		page.documentMissingCount().isDisplayed();
		System.out.println("Document Missing count " + page.documentMissingCount().getText());
		page.getValueFirstLastDocs().isDisplayed();
		System.out.println("First and Last docIDs " + page.getValueFirstLastDocs().getText());
		page.getPrivDocCountInSummaryPage().isDisplayed();
		System.out.println("privilege Document count " + page.getPrivDocCountInSummaryPage().getText());
		page.docIdentifiedByProtectionGuard().isDisplayed();
		System.out
				.println("Document Identified by protection guard " + page.docIdentifiedByProtectionGuard().getText());
		page.redactedDocCountInSummaryPage().isDisplayed();
		System.out.println("Redacted Doc count in summary page" + page.redactedDocCountInSummaryPage().getText());
		page.getDocumentsOfOCR().isDisplayed();
		System.out.println("Documents of OCR " + page.getDocumentsOfOCR().getText());
		page.getDocumentsOfTIFF().isDisplayed();
		System.out.println("Documents of TIFF" + page.getDocumentsOfTIFF().getText());

		baseClass.passedStep(" Verified Summary and Preview  Component");

	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48165
	 * @Description:To Verify Filler Audio is available only on enabling Burn
	 *                 Redaction for MP3 Files under Advanced Production Components
	 **/

	@Test(description = "RPMXCON-48165", enabled = true, groups = { "regression" })
	public void verifyMP3Component() throws Exception {

		baseClass.stepInfo("Test case Id:RPMXCON-48165- Production Sprint 16");
		baseClass.stepInfo(
				"To Verify Filler Audio is available only on enabling Burn Redaction for MP3 Files under Advanced Production Components");
		UtilityLog.info(Input.prodPath);
		foldername = "Prod_Folder" + Utility.dynamicNameAppender();
		tagname = "Prod_Tag" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Create production using required inputs");
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		String prodName = page.addANewProduction(productionname);
		System.out.println("created a new production - " + prodName);
		page.fillingMP3();
			baseClass.stepInfo(
					"Redaction style drop down should be displayed  User can select one of the three filler Audio files for redaction in MP3 Files Advanced Production Components  ");
			System.out.println("Redaction Style Options available are :"+page.getSelect_RedactionStyle_Dropdown().getText());
			page.getSelect_Beep_RedactionStyle_Dropdown().isDisplayed( );
			page.getSelect_Bomb_RedactionStyle_Dropdown().isDisplayed( );
			page.getSelect_Can_RedactionStyle_Dropdown().isDisplayed( );

		baseClass.passedStep(
				"Verified Filler Audio is available only on enabling Burn Redaction for MP3 Files under Advanced Production Components");
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48276
	 * @Description:To verify MP3 productions with DAT and PDF.
	 **/

	@Test(description = "RPMXCON-48276", enabled = true, groups = { "regression" })
	public void verifyMP3WithDAT() throws Exception {

		baseClass.stepInfo("Test case Id:RPMXCON-48276- Production Sprint 16");
		baseClass.stepInfo("To verify MP3 productions with DAT and PDF.");
		UtilityLog.info(Input.prodPath);
		foldername = "Prod_Folder" + Utility.dynamicNameAppender();
		tagname = "Prod_Tag" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Create tags and folders");
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		baseClass.stepInfo("Ingestion should have Documents with following file Variants  Native /Text /PDF & MP3");
		DataSets dataSet = new DataSets(driver);
		dataSet.navigateToDataSetsPage();
		dataSet.selectDataSetWithName(Input.ingestionAutomationAllSource);

		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(6);
		doclist.bulkTagExisting(tagname);

		baseClass.stepInfo("Create production using required inputs");
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		String prodName = page.addANewProduction(productionname);
		System.out.println("created a new production - " + prodName);
		page.fillingDATSection();
		page.fillingPDFSection(tagname);
		page.fillingMP3();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		System.out.println("Bates Number is : " + beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		baseClass.passedStep("verified MP3 productions with DAT and PDF.");
	}
	
	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48274
	 * @Description:To verify MP3 productions with DAT and Native.
	 **/

	@Test(description = "RPMXCON-48274", enabled = true, groups = { "regression" })
	public void verifyMP3WithDATandNative() throws Exception {

		baseClass.stepInfo("Test case Id:RPMXCON-48274- Production Sprint 16");
		baseClass.stepInfo("To verify MP3 productions with DAT and Native.");
		UtilityLog.info(Input.prodPath);
		foldername = "Prod_Folder" + Utility.dynamicNameAppender();
		tagname = "Prod_Tag" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Create tags and folders");
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		baseClass.stepInfo("Ingestion should have Documents with following file Variants  Native /Text /PDF & MP3");
		DataSets dataSet = new DataSets(driver);
		dataSet.navigateToDataSetsPage();
		dataSet.selectDataSetWithName(Input.ingestionAutomationAllSource);

		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(6);
		doclist.bulkTagExisting(tagname);

		baseClass.stepInfo("Create production using required inputs");
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		String prodName = page.addANewProduction(productionname);
		System.out.println("created a new production - " + prodName);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingMP3();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		System.out.println("Bates Number is : " + beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		baseClass.passedStep("verified MP3 productions with DAT and Native");
	}
}