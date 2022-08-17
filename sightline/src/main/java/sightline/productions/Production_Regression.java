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
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.DocListPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.RedactionPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Production_Regression {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	DocViewRedactions docViewRedact;
	Input ip;
	ProductionPage productionPage;
	TagsAndFoldersPage tagsAndFolderPage;
	SessionSearch sessionSearch;
	SoftAssert softAssertion;
	String  prefixID = "A" + Utility.dynamicNameAppender();
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

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47916
	 * @Description:To Verify the placeholder text is available in the generated
	 *                 placeholder PDFs/TIFF's
	 */

	@Test(description = "RPMXCON-47916", enabled = true, groups = { "regression" })
	public void verifyPlaceholderText() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-47916- Production Sprint 16");
		baseClass.stepInfo("To Verify the placeholder text is available in the generated placeholder PDFs/TIFF's.");
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
		int firstFile = Integer.parseInt(beginningBates);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		String prodName=page.addANewProduction(productionname);
		System.out.println("created a new production - "+prodName);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		int doccount = page.fillingGeneratePageWithContinueGenerationPopup();
		int lastFile = firstFile + doccount;

		page.extractFile();
		page.OCR_Verification_In_Generated_Tiff_tess4j(firstFile, lastFile, prefixID, suffixID, Input.searchString4);
		baseClass.stepInfo("verified placeholder in tiff");
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47895
	 * @Description:To Verify Quality Control & Confirmation Section on the self
	 *                 production wizard
	 */

	@Test(description = "RPMXCON-47895", enabled = true, groups = { "regression" })
	public void verifySelfProductionWizard() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-47895- Production Sprint 16");
		baseClass.stepInfo("To Verify Quality Control & Confirmation Section on the self production wizard");
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
		String prodName=page.addANewProduction(productionname);
		System.out.println("created a new production - "+prodName);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickOnGenerateButton();
		page.navigateToProductionPage();
		page.prodGenerationInProgressStatus(productionname);
		baseClass.stepInfo("Navigated to QC Tab");
		if (page.getProduction().isDisplayed()) {
			System.out.println("Production name is present : " + prodName);
		}
		Element count = page.getProductionDocCount();
		if (page.getProductionDocCount().isDisplayed()) {
			System.out.println("Generated Document count : " + count);
		}
		if (page.getConfirmProductionCommit().isDisplayed()) {
			System.out.println("commit Button is available");
		}
		if (page.getCopyPath().isDisplayed()) {
			System.out.println("CopyPath Button is available");
		}
		if (page.getQC_Download().isDisplayed()) {
			System.out.println("Download Button is available");
		}
		if (page.automatedCheck_prodfiles().isDisplayed()) {
			System.out.println("Automated QC check is available");
		}
		if (page.automatedCheck_DocumentCounts().isDisplayed()) {
			System.out.println("Automated QC check is available");
		}
		if (page.automatedCheck_BlankPageRemoval().isDisplayed()) {
			System.out.println("Automated QC check is available");
		}
		if (page.automatedCheck_BatesNumberGeneration().isDisplayed()) {
			System.out.println("Automated QC check is available");
		}
		baseClass.passedStep("verified QC Tab");
	}
	
	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47935
	 * @Description:To verify On Document Level Numbering Bates Number should get displayed on Production Home Page.
	 */

	@Test(description = "RPMXCON-47935", enabled = true, groups = { "regression" })
	public void verifyDocumentLevelNumbering() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-47935- Production Sprint 16");
		baseClass.stepInfo("To verify On Document Level Numbering Bates Number should get displayed on Production Home Page.");
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
		String prodName=page.addANewProduction(productionname);
		System.out.println("created a new production - "+prodName);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingDocumentPage(beginningBates,prefixID, suffixID);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.navigateToProductionPage();
		if(page.batesRangeInHomePage(suffixID).isElementAvailable(5)) {
			System.out.println("Bates Range is displayed ");
			
		}
		baseClass.passedStep("Bates Number is displayed on Production tile");
	}
	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47929
	 * @Description:To Verify in DAT section of a production configuration step availability of calculated fields
	 */

	@Test(description = "RPMXCON-47929", enabled = true, groups = { "regression" })
	public void verifyDATSectionOfCalculatedFields() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-47929- Production Sprint 16");
		baseClass.stepInfo("To Verify in DAT section of a production configuration step availability of calculated fields");
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
		String prodName=page.addANewProduction(productionname);
		System.out.println("created a new production - "+prodName);
		page.fillingDATSection();
		page.datMetaDataTiffPageCount();
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingDocumentPage(beginningBates,prefixID, suffixID);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		baseClass.passedStep("Verified DAT section of a production configuration step availability of calculated fields");
		}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47963
	 * @Description:To Verify Place holder for PDF file type in Production Generation
	 */

	@Test(description = "RPMXCON-47963", enabled = true, groups = { "regression" })
	public void verifyPlaceholderTextForPDF() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-47963- Production Sprint 16");
		baseClass.stepInfo("To Verify Place holder for PDF file type in Production Generation");
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
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		String beginningBates = page.getRandomNumber(2);
		int firstFile = Integer.parseInt(beginningBates);
		String prodName=page.addANewProduction(productionname);
		System.out.println("created a new production - "+prodName);
		page.fillingDATSection();
		page.fillingPDFWithNativelyProduceddDocsSelectingFileType(tagname, Input.searchString4,Input.orderCriteria,Input.pdfFileType);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		int doccount = page.fillingGeneratePageWithContinueGenerationPopup();
		int lastFile = firstFile + doccount;

		page.extractFile();
		page.OCR_Verification_In_Generated_PDF(firstFile, lastFile, prefixID, suffixID, Input.searchString4);
		baseClass.stepInfo("verified placeholder in PDF");
	}
	
	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47905
	 * @Description:To Verify The Bates No Generated is in Sync with Bates No enter in The Application for Generation
	 */

	@Test(description = "RPMXCON-47905", enabled = true, groups = { "regression" })
	public void verifyBatesNumberSync() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-47905- Production Sprint 16");
		baseClass.stepInfo("To Verify The Bates No Generated is in Sync with Bates No enter in The Application for Generation");
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
		String prodName=page.addANewProduction(productionname);
		System.out.println("created a new production - "+prodName);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		System.out.println("Bates Number is : "+beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		int doccount = page.fillingGeneratePageWithContinueGenerationPopup();

		page.extractFile();
		page.OCR_Verification__BatesNo_In_GeneratedFile(prefixID, suffixID,beginningBates);
		baseClass.stepInfo("verified Bates Number Sync With Generates Files");
	}
	
	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47911
	 * @Description:To Verify In generated load files, the key is the ID (e.g. bates number) of the produced document, not the DocID of the document
	 */

	@Test(description = "RPMXCON-47911", enabled = true, groups = { "regression" })
	public void verifyKeyIDAndBatesSync() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-47911- Production Sprint 16");
		baseClass.stepInfo("To Verify In generated load files, the key is the ID (e.g. bates number) of the produced document, not the DocID of the document");
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
		String prodName=page.addANewProduction(productionname);
		System.out.println("created a new production - "+prodName);
		page.fillingDATSection();
		String datField = page.fillingDatWithSpecificClassification(Input.productionText,Input.tiffPageCountNam, Input.bates);
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		System.out.println("Bates Number is : "+beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		page.extractFile();
		page.OCR_Verification__BatesNo_In_GeneratedFile(prefixID, suffixID,datField);
		baseClass.stepInfo("Verified the key is the ID in generated load files,");
	}
	
	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47919
	 * @Description:To Verify In Summary step, Should Display the Redaction Documents Count
	 **/

	@Test(description = "RPMXCON-47919", enabled = true, groups = { "regression" })
	public void verifyRedactedCountInSummary() throws Exception {

		baseClass.stepInfo("Test case Id:RPMXCON-47915- Production Sprint 16");
		baseClass.stepInfo("To Verify In Summary step, Should Display the Redaction Documents Count");
		UtilityLog.info(Input.prodPath);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		
		baseClass.selectproject();
		foldername = "Prod_Folder" + Utility.dynamicNameAppender();
		tagname = "Prod_Tag" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Create tags and folders");
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassificationInRMU(tagname, Input.tagNamePrev);

		String redactTag= "Redaction"+ Utility.dynamicNameAppender();
		RedactionPage redact=new RedactionPage(driver);
		redact.selectDefaultSecurityGroup();
		redact.manageRedactionTagsPage(redactTag);
		
		baseClass.stepInfo("perform basic search and bulk folder");
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.searchString4);
		sessionSearch.viewInDocView();
		
		DocViewRedactions docview=new DocViewRedactions(driver);
		docview.redactRectangleUsingOffset(10, 10, 20, 20);
		driver.waitForPageToBeReady();
		docview.selectingRedactionTag(redactTag);

		baseClass.stepInfo("Create production using required inputs");
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		String prodName=page.addANewProduction(productionname);
		System.out.println("created a new production - "+prodName);
		page.fillingDATSection();
		page.TIFFSectionEnableBurnRedactionAndSelectRedactedTag(redactTag);
		page.navigateToNextSection();
		String batesNum=page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		System.out.println("Bates Number is : "+batesNum);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		driver.scrollingToBottomofAPage();
		String docCount = page.redactedDocCountInSummaryPage().getText();
		int redactedCount = Integer.parseInt(docCount);
		int count=0;
		
		if(redactedCount>count) {
			baseClass.passedStep("Redacted count displayed");
		}
	}
	
	
	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-63064
	 * @Description:Verify that user should be able to change the automatically enabled native placeholdering under TIFF/PDF section from new production
	 **/

	@Test(description = "RPMXCON-63064", enabled = true, groups = { "regression" })
	public void verifyNativePlaceholderInSpreadsheet() throws Exception {

		baseClass.stepInfo("Test case Id:RPMXCON-63064- Production Sprint 16");
		baseClass.stepInfo("Verify that user should be able to change the automatically enabled native placeholdering under TIFF/PDF section from new production");
		UtilityLog.info(Input.prodPath);

		foldername = "Prod_Folder" + Utility.dynamicNameAppender();
		tagname = "Prod_Tag" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Create tags and folders");
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		sessionSearch = new SessionSearch(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		sessionSearch = new SessionSearch(driver);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch("DocFileType", "Spreadsheet");
		sessionSearch.ViewInDocList();
		 DocListPage doclist=new DocListPage(driver);
		doclist.documentSelection(2);
		doclist.bulkTagExisting(tagname);
		

		baseClass.stepInfo("Create production using required inputs");
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		String prodName=page.addANewProduction(productionname);
		System.out.println("created a new production - "+prodName);
		page.fillingDATSection();
		page.getTIFFChkBox().waitAndClick(10);
		page.getTIFFTab().waitAndClick(10);
		page.getTIFF_EnableforPrivilegedDocs().ScrollTo();
		page.getTIFF_EnableforPrivilegedDocs().waitAndClick(10);
		page.verifyEnableNativelyProduceddocs();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		System.out.println("Bates Number is : "+beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		
		baseClass.stepInfo("Create production by selecting PDF and using required inputs");
		String beginningBates2 = page.getRandomNumber(2);
		String productionname2 = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		String prodName2=page.addANewProduction(productionname2);
		System.out.println("created a new production - "+prodName2);
		page.fillingDATSection();
		page.getTIFFChkBox().waitAndClick(10);
		page.getTIFFTab().waitAndClick(10);
		page.getTIFF_EnableforPrivilegedDocs().ScrollTo();
		page.getTIFF_EnableforPrivilegedDocs().waitAndClick(10);
		page.getPDFGenerateRadioButton().waitAndClick(10);
		page.verifyEnableNativelyProduceddocs();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates2);
		System.out.println("Bates Number is : "+beginningBates2);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname2);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
	}
	
	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-63711
	 * @Description:Field Delimiter values should display consistently on all project selections in Export DAT componen
	 **/

	@Test(description = "RPMXCON-63711", enabled = true, groups = { "regression" })
	public void verifyFieldDelimeter() throws Exception {

		baseClass.stepInfo("Test case Id:RPMXCON-63711- Production Sprint 16");
		baseClass.stepInfo("Field Delimiter values should display consistently on all project selections in Export DAT componen");
		UtilityLog.info(Input.prodPath);

		baseClass.stepInfo("Create Export using required inputs");
		String export = "Ex" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String exportName = "E" + Utility.dynamicNameAppender();

		page.selectingDefaultSecurityGroup();
		String text = page.getProdExport_ProductionSets().getText();
		if (text.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(export);
		}
		page.addANewExport(exportName);
		page.getDATTab().waitAndClick(10);
		page.getDATChkBox().waitAndClick(10);
		
		String defaultFielsSep = page.defaultFielsSeperator().getText();
		System.out.println("Default Field seperator -"+defaultFielsSep);
		
		String defaultTextQualifier = page.defaultTextQualifier().getText();
		System.out.println("Default Text Qualifier -"+defaultTextQualifier);
		
		String defaultMultiValue = page.defaultMultiValue().getText();
		System.out.println("Default Multi value -"+defaultMultiValue);
		
		String defaultNewLineSeperator = page.defaultNewLineSeperator().getText();
		System.out.println("Default New Line Seperator -"+defaultNewLineSeperator);
	}
	
	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-64076
	 * @Description:Verify for new production Native, Spreadsheets, and Multimedia checkbox should be checked
	 **/

	@Test(description = "RPMXCON-64076", enabled = true, groups = { "regression" })
	public void verifySpreadsheetAndMultimedia() throws Exception {

		baseClass.stepInfo("Test case Id:RPMXCON-64076- Production Sprint 16");
		baseClass.stepInfo("Verify for new production Native, Spreadsheets, and Multimedia checkbox should be checked");
		UtilityLog.info(Input.prodPath);
		
		foldername = "Prod_Folder" + Utility.dynamicNameAppender();
		tagname = "Prod_Tag" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Create tags and folders");
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		baseClass.stepInfo("perform basic search and bulk folder");
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch("DocFileType", "Spreadsheet");
		sessionSearch.ViewInDocList();
		 DocListPage doclist=new DocListPage(driver);
		doclist.documentSelection(2);
		doclist.bulkTagExisting(tagname);
		

		baseClass.stepInfo("Create production using required inputs");
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int firstFile = Integer.parseInt(beginningBates);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		String prodName=page.addANewProduction(productionname);
		System.out.println("created a new production - "+prodName);
		page.fillingDATSection();
		String defSelectedFiletype = page.ddnselectFileType().getText();
		System.out.println("File type:"+defSelectedFiletype);
		
		page.fillingTIFFSectionwithNativelyPlaceholder(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		System.out.println("Bates Number is : "+beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();
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
