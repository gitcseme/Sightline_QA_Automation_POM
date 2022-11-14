package sightline.productions;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.apache.pdfbox.pdmodel.PDDocument;
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
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.RedactionPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Production_Regression23_24 {
	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	DocViewPage docView;
	Utility utility;
	String foldername;
	String tagname;
	String productionname;
	ProductionPage page;
	SessionSearch sessionSearch;
	SavedSearch saveSearch;
	TagsAndFoldersPage tagsAndFolderPage;
	SoftAssert softAssertion;


	@BeforeClass(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		Input input = new Input();
		input.loadEnvConfig();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());

		driver = new Driver();
		loginPage = new LoginPage(driver);
		base = new BaseClass(driver);
		saveSearch = new SavedSearch(driver);
		sessionSearch = new SessionSearch(driver);
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		softAssertion=new SoftAssert();
		page= new ProductionPage(driver);

	}
	
	/**
	 * @author NA Testcase No:RPMXCON-47975
	 * @Description:To Verify Rich Text configuration in Production branding should be of Arial, Font Size 10 and Bold format
	 **/
	@Test(description = "RPMXCON-47975", enabled = true, groups = { "regression" })
	public void verifyRIchTextBranding() throws Exception {
		UtilityLog.info(Input.prodPath);	
		tagname = "Tag" + Utility.dynamicNameAppender();
		productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);

		base.stepInfo("Test Cases Id : RPMXCON-47975");
		base.stepInfo("To Verify Rich Text configuration in Production branding should be of Arial, Font Size 10 and Bold format");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
//		 create tag
		base = new BaseClass(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch("DocFileType", "MP3");
		sessionSearch.ViewInDocList();
		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(1);
		doclist.bulkTagExisting(tagname);
		
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSectionWithBrandingText();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		base.waitUntilFileDownload();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		driver.waitForPageToBeReady();
		page.deleteFiles();
		page.extractFile();	
		File pdfFile = new File(home + "/Downloads/VOL0001/PDF/0001/" + prefixID + beginningBates + suffixID + ".pdf");
		String fontStyle = page.verifyFontDetailsPDF(pdfFile).getFontDescriptor().getFontFamily();
		System.out.println(fontStyle);
	    softAssertion.assertEquals(fontStyle, "Arial");
	    softAssertion.assertAll();
	    base.stepInfo("Font Style : " + fontStyle);
	    base.stepInfo("Branding Text Printed in Arial Format");
	    base.passedStep("Verified -  Rich Text configuration in Production branding should be of Arial, Font Size 10 and Bold format");
	    loginPage.logout();
	}
	
	/**
	 * @author Brundha Testcase No:RPMXCON-49727
	 * @Description:Verify that branding with Bates Number and 'Confidentiality' is
	 *                     applied on all pages for image based documents for
	 *                     generated TIFF/PDF file
	 **/
	@Test(description = "RPMXCON-49727", enabled = true, groups = { "regression" })
	public void verifyingBrandingTextInReGeneratedDocuments() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.selectproject(Input.additionalDataProject);
		base.stepInfo("RPMXCON-49727 -Production Component");
		base.stepInfo(
				"Verify that branding with Bates Number and 'Confidentiality' is applied on all pages for image based documents for generated TIFF/PDF file");

		DocViewPage Doc = new DocViewPage(driver);
		DocViewRedactions DocRedactions = new DocViewRedactions(driver);
		
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String PlaceholderText = "Confidentiality";

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.SearchMetaData("RequirePDFGeneration", Input.pageCount);
		sessionSearch.ViewInDocList();

		DocListPage doc = new DocListPage(driver);
		doc.documentSelection(3);
		sessionSearch.bulkTagExisting(tagname);
		doc.documentSelection(3);
		doc.viewSelectedDocumentsInDocView();
		base.waitTime(8);
		int PageCount = Doc.getTotalPagesCount();

		DocRedactions.selectDoc2();
		driver.waitForPageToBeReady();
		int PageCount2Doc = Doc.getTotalPagesCount();

		DocRedactions.doclistTable(3).waitAndClick(10);
		driver.waitForPageToBeReady();
		int PageCount3Doc = Doc.getTotalPagesCount();

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.selectGenerateOption(true);
		page.fillingBrandingInTiffSection(Input.batesNumber, PlaceholderText);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageForOtherProject(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		driver.waitForPageToBeReady();
		int Count = Integer.valueOf(beginningBates) + PageCount;
		int LastDoc = Count + PageCount2Doc;
		page.extractFile();
		driver.waitForPageToBeReady();
		String BatesNumber = prefixID + beginningBates + suffixID;
		String BatesNumber1 = prefixID + Count + suffixID;
		String BatesNumber2 = prefixID + LastDoc + suffixID;
		
		String []Bates= {BatesNumber,BatesNumber1,BatesNumber2};
		String home = System.getProperty("user.home");
		for(int i=0;i<Bates.length;i++) {
		File fileName = new File(home + "/Downloads/VOL0001/PDF/0001/" + Bates[i] + ".pdf");
		driver.waitForPageToBeReady();
		System.out.println(Bates[i]);
		base.waitTime(1);
		if(fileName.exists()) {
			base.passedStep(" Batesnumber is maintained in sequence order");
		}else {
			base.failedStep("Bates number is not maintained in sequence order");
		}
		}
		page.pdfVerificationInDownloadedFile(BatesNumber,PageCount, prefixID, PlaceholderText);
		page.pdfVerificationInDownloadedFile(BatesNumber1, PageCount2Doc, prefixID, PlaceholderText);
		page.pdfVerificationInDownloadedFile(BatesNumber2, PageCount3Doc, prefixID, PlaceholderText);
		loginPage.logout();
	}
	/**
	 * @author Brundha Testcase No:RPMXCON-49118
	 * @Description:To verify that value of Redacted Documents on Production-Summary tab
	 **/
	@Test(description = "RPMXCON-49118", enabled = true, groups = { "regression" })
	public void verifyingRedactDocOnSummaryTab() throws Exception {
		UtilityLog.info(Input.prodPath);
		
		base.stepInfo("RPMXCON-49118 -Production Component");
		base.stepInfo("To verify that value of Redacted Documents on Production-Summary tab");

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		
		String Foldername = "Fold" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		String Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();
		RedactionPage redactionpage = new RedactionPage(driver);
		driver.waitForPageToBeReady();
		redactionpage.manageRedactionTagsPage(Redactiontag1);
		
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(Foldername,Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.comments);
		sessionSearch.bulkFolderExisting(Foldername);
		sessionSearch.ViewInDocViewWithoutPureHit();

		DocViewRedactions DocRedactions = new DocViewRedactions(driver);
		DocRedactions.doclistTable(2).waitAndClick(5);
		driver.waitForPageToBeReady();
		DocRedactions.redactRectangleUsingOffset(10,10,60,60);
		DocRedactions.selectingRedactionTag2(Redactiontag1);
		
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		DocRedactions.doclistTable(3).waitAndClick(5);
		driver.waitForPageToBeReady();
		DocRedactions.redactRectangleUsingOffset(10,10,70,70);
		DocRedactions.selectingRedactionTag2(Redactiontag1);
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.selectGenerateOption(true);
		page.getClk_burnReductiontoggle().ScrollTo();
		page.getClk_burnReductiontoggle().waitAndClick(10);
		page.fillingBurnRedaction(Redactiontag1, "White with black font", false, null, null);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(Foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		
		driver.waitForPageToBeReady();
		String RedactDocCount=page.redactedDocCountInSummaryPage().getText();
		System.out.println(RedactDocCount);
		base.stepInfo("verifying Redacted Document count in summary tab");
		base.digitCompareEquals(Integer.valueOf(RedactDocCount),2, "Redacted document count is displayed as expected","Redacted Doc count mismatch");
		loginPage.logout();
		
	}
	
	/**
	 * @author Brundha Testcase No:RPMXCON-49117
	 * @Description:To verify that the value of Privileged Documents on production summary tab
	 **/
	@Test(description = "RPMXCON-49117", enabled = true, groups = { "regression" })
	public void verifyingPrivDocCountOnSummaryTab() throws Exception {
		UtilityLog.info(Input.prodPath);
		
		base.stepInfo("RPMXCON-49117 -Production Component");
		base.stepInfo("To verify that the value of Privileged Documents on production summary tab");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String Foldername = "p" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname,Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(Foldername,Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(Foldername);
		sessionSearch.ViewInDocListWithOutPureHit();

		DocListPage doc = new DocListPage(driver);
		int docCount=3;
		doc.documentSelection(docCount);
		sessionSearch.bulkTagExisting(tagname);
		
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionPrivDocs(tagname, tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(Foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		
		driver.waitForPageToBeReady();
		String PrivDocCount=page.getPrivDocCountInSummaryPage().getText().trim();
		System.out.println(PrivDocCount);
		base.stepInfo("verifying Priviledged Document count in summary tab");
		base.digitCompareEquals(Integer.valueOf(PrivDocCount),docCount, "Priviledged document count is displayed as expected","Priviledged Doc count mismatch");
		loginPage.logout();
	}

	/**
	 * @author Brundha Testcase No:RPMXCON-47962
	 * @Description:To Verify Production Generation successfull if archive files
	 *                 (with placeholdering) are included in the production docs
	 **/
	@Test(description = "RPMXCON-47962", enabled = true, groups = { "regression" })
	public void verifyingPlaceholderInInArchiveFiles() throws Exception {
		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.selectproject(Input.projectName01);
		base.stepInfo("RPMXCON-47962 -Production Component");
		base.stepInfo(
				"To Verify Production Generation successfull if archive files (with placeholdering) are included in the production docs");

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");

		SessionSearch sessionSearch = new SessionSearch(driver);
		driver.getWebDriver().get(Input.url + "Search/Searches");
		int PureHit = sessionSearch.metaDataSearchInBasicSearch("SourceDocID","35ID00000182");
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionwithNativelyPlaceholder(tagname);
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
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		int FirstFile = Integer.valueOf(beginningBates);
		int LastFile = FirstFile + PureHit;
		base.waitTime(2);
		File TiffFile = new File(
				home + "/Downloads/VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tiff");
		if (TiffFile.exists()) {
			base.passedStep("Tiff  file is displayed as expected");
		} else {
			base.failedStep("Tiff file is not displayed as expected");
		}
		page.OCR_Verification_In_Generated_Tiff_tess4j(FirstFile, LastFile, prefixID, suffixID, tagname);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();

	}

	/**
	 * @author Brundha Testcase No:RPMXCON-63118
	 * @Description: Verify if text is ingested and default option ("Do not OCR
	 *               non-redacted docs...") is selected in Production-text component
	 *               then it should export text for non-redacted document
	 **/
	@Test(description = "RPMXCON-63118", enabled = true, groups = { "regression" })
	public void verifyingTextInGenratedDocument() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		SessionSearch session = new SessionSearch(driver);
		base.stepInfo("RPMXCON-63118 -Production Component");
		base.stepInfo(
				"Verify if text is ingested and default option ('Do not OCR non-redacted docs...') is selected in Production-text component then it should export text for non-redacted document");

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");

		DataSets dataset = new DataSets(driver);
		base.stepInfo("Navigating to dataset page");
		dataset.navigateToDataSetsPage();
		dataset.selectDataSetWithName(Input.GD994NativeTextForProductionFolder);

		DocListPage doc = new DocListPage(driver);
		driver.waitForPageToBeReady();
		doc.documentSelection(3);
		session.bulkTagExisting(tagname);

		doc.documentSelection(3);
		doc.viewSelectedDocumentsInDocView();
		DocViewPage dv = new DocViewPage(driver);
		DocViewRedactions DocRedaction = new DocViewRedactions(driver);
		String TextInFirstDoc = dv.gettingExactTextInDocument();

		DocRedaction.selectDoc2();
		String TextInSecondDoc = dv.gettingExactTextInDocument();

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.addDATFieldAtSecondRow(Input.productionText, Input.tiffPageCountNam, Input.tiffPageCountText);
		page.fillingTextSection();
		driver.scrollingToBottomofAPage();
		page.getCheckBoxCheckedVerification(page.getTextRadioBtn());
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
		base.waitUntilFileDownload();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		int Count = Integer.valueOf(beginningBates) + Integer.valueOf(Input.pageCount);
		page.deleteFiles();
		page.extractFile();
		driver.waitForPageToBeReady();
		File TextFile = new File(
				home + "/Downloads/VOL0001/Text/0001/" + prefixID + beginningBates + suffixID + ".txt");
		File SecondTextFile = new File(home + "/Downloads/VOL0001/Text/0001/" + prefixID + Count + suffixID + ".txt");

		base.stepInfo("verifying text file in generated production");
		page.verifyingTextFile(TextFile, TextInFirstDoc);
		page.verifyingTextFile(SecondTextFile, TextInSecondDoc);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();

	}

	/**
	 * @author sowndarya Testcase No:RPMXCON-55749
	 * @Description: To verify that 'EmailThreadSequenceID' displays correct value
	 *               in Production
	 **/
	@Test(description = "RPMXCON-55749", enabled = true, groups = { "regression" })
	public void VerifyEmailThreadSequenceId() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48976");
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		base.stepInfo("To verify that 'EmailThreadSequenceID' displays correct value in Production");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in As " + Input.pa1userName);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		String s1 = "EmailThreadSequenceID";
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		int firstFile = Integer.parseInt(beginningBates);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		String prodName = page.addANewProduction(productionname);
		System.out.println("production created : " + prodName);
		page.fillingDATSection();
		base.stepInfo(
				"In component section, select TIFF  Enabled Slip Sheet from Advanced Tab  Select metadata EmailThreadSequenceID");
		page.fillingTIFFSection(tagname);
		page.fillingSlipSheetWithMetadataInTiffSection(s1);
		driver.scrollPageToTop();
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
		String PDocCount = page.getProductionDocCount().getText();
		int docno = Integer.parseInt(PDocCount);
		int lastfile = firstFile + docno;
		page.extractFile();

		System.out.println("____________________");
		System.out.println(firstFile);
		System.out.println(lastfile);
		System.out.println(prefixID);
		System.out.println(suffixID);
		System.out.println("EmailThreadSequencelD" + ":");
		System.out.println("____________________");

		page.OCR_Verification_In_Generated_Tiff_SS(firstFile, lastfile, prefixID, suffixID,
				"EmailThreadSequencelD" + ":");
		base.passedStep("verified EmailThreadSequenceID displays correct value in Production");

	}
	/**
	 * @author Brundha RPMXCON-48059
	 * @Description To verify that if Blank Page Removal toggle is ON then it should
	 *              produced the TIFF without blank pages
	 */
	@Test(description = "RPMXCON-48059", enabled = true, groups = { "regression" })

	public void verifyBlankPageRemovalONInGeneratedProduction() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.selectproject(Input.projectName01);
		base.stepInfo("RPMXCON-48059 -Production component");
		base.stepInfo(
				"To verify that if Blank Page Removal toggle is ON then it should produced the TIFF without blank pages");
		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String productionname = "P" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.SearchMetaData("SourceDocID", "49ID00001312");
		sessionSearch.bulkRelease(Input.securityGroup);
		sessionSearch.bulkFolderExisting(foldername);
		

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int firstFile = Integer.parseInt(beginningBates);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectGenerateOption(false);
		driver.scrollPageToTop();
		page.getBlankPageRemovalToggle().waitAndClick(5);
		base.waitForElement(page.getContinueBtn());
		page.getContinueBtn().waitAndClick(5);
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.validatingGetTextElement(page.getBlankPageRemovalMsg(), "Success");
		page.deleteFiles();
		page.extractFile();
		String home = System.getProperty("user.home");
		driver.waitForPageToBeReady();
		File dir = new File(home + "/Downloads/VOL0001/Images/0001/");
		File[] dir_contents = dir.listFiles();
		System.out.println(dir_contents.length);
		int Image = dir_contents.length;
		int ImageFiles = firstFile + Image;
		File TiffFile = new File(
				home + "/Downloads/VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tiff");
		page.isfileisExists(TiffFile);
		page.verificationOfTiffFile(firstFile, ImageFiles, prefixID, suffixID);
		loginPage.logout();
	}

	/**
	 * @author Brundha RPMXCON-48060
	 * @Description To verify that if Blank Page Removal toggle is OFF then it
	 *              should produced the TIFF/PDF with blank pages
	 */
	@Test(description = "RPMXCON-48060", enabled = true, groups = { "regression" })

	public void verifyBlankPageRemovalOffInGeneratedProduction() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.selectproject(Input.projectName01);
		base.stepInfo("RPMXCON-48060 -Production component");
		base.stepInfo(
				"To verify that if Blank Page Removal toggle is OFF then it should produced the TIFF/PDF with blank pages");


		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String productionname = "P" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.SearchMetaData("SourceDocID", "49ID00001312");
		sessionSearch.bulkRelease(Input.securityGroup);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.ViewInDocViewWithoutPureHit();
		
		DocViewPage docView=new DocViewPage(driver);
		int TotalPages=docView.docViewDocPageCount();
		System.out.println(TotalPages);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectGenerateOption(true);
		page.banlkPageRemovalToggleOffCheck();
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.validatingGetTextElement(page.getBlankPageRemovalMsg(), "Success");
		page.extractFile();
		String home = System.getProperty("user.home");
		base.waitTime(2);
		PDDocument doc = PDDocument.load(new File(home + "/Downloads/VOL0001/PDF/0001/" + prefixID + beginningBates + suffixID + ".pdf"));
		int count = doc.getNumberOfPages();
		System.out.println(count);
		base.digitCompareEquals(TotalPages, count,"Blank Page is not removed as expected","Blank Page is Removed");
		loginPage.logout();
	}
	/**
	 * @author NA created on:NA modified by:NA TESTCASE No:RPMXCON-49047
	 * @Description: Verify Priv tag of a document from one security is correctly
	 *               considered for the same document in another security group
	 **/
	@Test(description = "RPMXCON-49047", enabled = true, groups = { "regression" })
	public void verifyPrivCountDiffSG() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49047 -Production Component");
		base.stepInfo("Verify Priv tag of a document from one security is correctly"
				+ "considered for the same document in another security group");

		tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String securityGroup = "Production_Security_Group" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SecurityGroupsPage sg = new SecurityGroupsPage(driver);
		sg.navigateToSecurityGropusPageURL();
		sg.createSecurityGroups(securityGroup);

		// search for folder
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.ViewInDocList();
		DocListPage doc = new DocListPage(driver);
		doc.selectAllDocs();
		doc.docListToBulkRelease(securityGroup);
		driver.waitForPageToBeReady();
		doc.bulkTagExistingFromDoclist(tagname);

		sg.navigateToSecurityGropusPageURL();
		sg.addTagToSecurityGroup(securityGroup, tagname);

		base = new BaseClass(driver);
		String beginningBates = page.getRandomNumber(2);
		page.navigateToProductionPage();
		page.selectingSecurityGroup(securityGroup);
		driver.waitForPageToBeReady();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTiffSectionDisablePrivilegedDocs();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		driver.waitForPageToBeReady();
		String privCount = page.getPrivDocCountInSummaryPage().getText();
		if (Integer.valueOf(privCount).equals(0)) {
			base.passedStep("Priv Doc Count in Summary Tab as Expected");
		} else {
			base.failedStep("Priv Doc Count in Summary Tab Not as Expected");
		}
		base.passedStep("Verified - that Priv tag of a document from one security is correctly considered"
				+ " for the same document in another security group");
		loginPage.logout();
	}
	/**
	 * @author  sowndarya.velraj  Testcase No:RPMXCON-48980
	 * @throws Exception
	 * @Description: Verify that branding text should be truncated when Branding
	 *               text exceeds the space and no space after wrapping while
	 *               production for a PDF file
	 **/
	@Test(description = "RPMXCON-48980", enabled = true, groups = { "regression" })
	public void verifyBrandingTextTruncated() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48980");
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		base.stepInfo("Verify that branding text should be truncated when Branding text exceeds the "
				+ "space and no space after wrapping while production for a PDF file");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch("DocFileType", "Spreadsheet");
		sessionSearch.ViewInDocList();
		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(2);
		doclist.bulkTagExistingFromDoclist(tagname);

		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSection();
		page.fillingAndverifySixBrandingMultiLine(tagname);
		base.stepInfo("Added a Multi line branding to all six  locations");
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.waitUntilFileDownload();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		page.deleteFiles();
		page.extractFile();
		driver.waitForPageToBeReady();

		String url = home + "/Downloads/VOL0001/PDF/0001/";
		String name = prefixID + beginningBates + suffixID;

		String topLeftText = page.verifyMultiLineBrandingText(url, name + ".pdf", "Top - Left", 0);
		softAssertion.assertTrue(topLeftText.contains("..."));

		String topCenterText = page.verifyMultiLineBrandingText(url, name + ".pdf", "Top - Center", 0);
		softAssertion.assertFalse(topCenterText.contains("..."));

		String topRightText = page.verifyMultiLineBrandingText(url, name + ".pdf", "Top - Right", 0);
		softAssertion.assertTrue(topRightText.contains("..."));

		String bottomLeftText = page.verifyMultiLineBrandingText(url, name + ".pdf", "Bottom - Left", 0);
		softAssertion.assertTrue(bottomLeftText.contains("..."));

		String bottomCenterText = page.verifyMultiLineBrandingText(url, name + ".pdf", "Bottom - Center", 0);
		softAssertion.assertFalse(bottomCenterText.contains("..."));

		String bottomRightText = page.verifyMultiLineBrandingText(url, name + ".pdf", "Bottom - Right", 0);
		softAssertion.assertTrue(bottomRightText.contains("..."));
		softAssertion.assertAll();
		base.passedStep("Verified - that branding text should be truncated when Branding text exceeds the space"
				+ " and no space after wrapping while production for a PDF file");
		loginPage.logout();
	}
	/**
	 * @author N/A Testcase No:RPMXCON-48295
	 * @Description: Verify New Line delimiters on Production.
	 **/
	@Test(description = "RPMXCON-48295", enabled = true, groups = { "regression" })
	public void verifyNewLineDelimiter() throws Exception {
		base.stepInfo("RPMXCON-48295");
		base.stepInfo("To Verify -  New Line delimiters on Production");
		base = new BaseClass(driver);
		productionname = "p" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in As " + Input.pa1userName);

		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.verifyNewLineDimiOptions();
		base.passedStep("Verified - that New Line delimiters on Production");
		loginPage.logout();
	}
	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		base = new BaseClass(driver);
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
		System.out.println("******TEST CASES FOR PRODUCTION EXECUTED******");

	}
}
