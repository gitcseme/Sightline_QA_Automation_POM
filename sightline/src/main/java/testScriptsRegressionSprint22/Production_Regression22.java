package testScriptsRegressionSprint22;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Production_Regression22 {
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
	 * @author Brundha RPMXCON-47742 Date:9/22/2022
	 * @Description To Verify the pagination for multiple productions for grid view.
	 */
	@Test(description = "RPMXCON-47742", enabled = true, groups = { "regression" })
	public void verifyingPaginationInGridView() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-47742 -Production component");
		base.stepInfo("To Verify the pagination for multiple productions for grid view.");

		int Count = 11;
		ProductionPage page = new ProductionPage(driver);
		String totalProduction = page.getProductionItemsTileItems().getText();
		if (Integer.valueOf(totalProduction)>=Count) {
			base.stepInfo("Navigating to production grid view");
			page.getGridView().waitAndClick(10);
			driver.scrollingToBottomofAPage();
			base.stepInfo("Verifying production grid view pagination");
			page.verifyingGridView();
		}
		else {
			base.stepInfo("Creating Multiple Production");
			for(int i=0;i<Count;i++) {
			String productionname = "P" + Utility.dynamicNameAppender();
			page.navigatingToProductionHomePage();
			page.selectingDefaultSecurityGroup();
			page.addANewProduction(productionname);
			driver.waitForPageToBeReady();
			}
			page.navigatingToProductionHomePage();
			base.stepInfo("Navigating to production grid view");
			page.getGridView().waitAndClick(10);
			driver.scrollingToBottomofAPage();
			base.stepInfo("Verifying production grid view pagination");
			page.verifyingGridView();
			
		}
		loginPage.logout();

	}
	
	/**
	 * @author Brundha Test case id-RPMXCON-48027
	 * @Description To Verify Redaction Style in PDF & TIFF Section "White with
	 *              Black font" works the same for both larger redactions or smaller
	 *              redactions that trigger the abbreviated text
	 * 
	 */
	@Test(description = "RPMXCON-48027", enabled = true, groups = { "regression" })
	public void verifyRedactionPlaceholderInGeneratedProduction() throws Exception {

		UtilityLog.info(Input.prodPath);
		BaseClass base = new BaseClass(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("RPMXCON-48027 -Production Component");
		base.stepInfo(
				"To Verify Redaction Style in PDF & TIFF Section 'White with Black font' works the same for both larger redactions or smaller redactions that trigger the abbreviated text");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();
		String PlaceholderText = Input.tag;
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		RedactionPage redactionpage = new RedactionPage(driver);
		driver.waitForPageToBeReady();
		redactionpage.manageRedactionTagsPage(Redactiontag1);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.viewInDocView();

		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		
		driver.waitForPageToBeReady();
		docViewRedactions.redactRectangleUsingOffset(10, 10, 20, 20);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag1);
		
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		docViewRedactions.redactRectangleUsingOffset(10, 10,120,120);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag1);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.selectGenerateOption(false);
		page.getClk_burnReductiontoggle().ScrollTo();
		page.getClk_burnReductiontoggle().waitAndClick(10);
		page.fillingBurnRedaction(Redactiontag1, "White with black font", true, Redactiontag1, PlaceholderText);
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
		page.extractFile();
		String home = System.getProperty("user.home");
		page.OCR_Verification_In_Generated_Tiff_tess4j(prefixID, suffixID, beginningBates);
		File imageFile = new File(
				home + "/Downloads/VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tiff");
		page.OCR_Verification_In_Generated_Tiff_tess4j(imageFile, PlaceholderText);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		loginPage.logout();

	}


	/**
	 * @author sowndarya Testcase No:RPMXCON-48979
	 * @Description: Verify that branding text should be truncated when Branding text
	 *  exceeds the space and no space after wrapping while production for a TIFF file
	 **/
	@Test(description = "RPMXCON-48979", enabled = true, groups = { "regression" })
	public void VerifyMultiLineBrTrunFrTiff() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48979");
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		base.stepInfo("Verify that branding text should be truncated when Branding text "
				+ "exceeds the space and no space after wrapping while production for a TIFF file");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in As " + Input.pa1userName);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch("DocFileType", "Spreadsheet");
		sessionSearch.ViewInDocList();
		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(2);
		driver.scrollPageToTop();
		doclist.bulkTagExistingFromDoclist(tagname);
		
		base = new BaseClass(driver);
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		base.waitForElement(page.getTIFFChkBox());
		page.getTIFFChkBox().Click();
		driver.scrollingToBottomofAPage();
		base.waitForElement(page.getTIFFTab());
		page.getTIFFTab().Click();
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
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		base.waitUntilFileDownload();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		page.deleteFiles();
		page.extractFile();
		driver.waitForPageToBeReady();
		File tiffFile = new File(
				home + "/Downloads/" + "VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tiff");
		softAssertion.assertTrue(page.extractTextFromTiff(tiffFile).contains("Top - Lef"));
		softAssertion.assertTrue(page.extractTextFromTiff(tiffFile).contains("Top - Center Multiple Line Branding Text Verify"));
		softAssertion.assertTrue(page.extractTextFromTiff(tiffFile).contains("Top - Right Multiple Line Branding Text Ve..."));
		softAssertion.assertTrue(page.extractTextFromTiff(tiffFile).contains("Bottom - Left Multiple Line Branding Text..."));
		softAssertion.assertTrue(page.extractTextFromTiff(tiffFile).contains("Bottom - Center Multiple Line Branding Text Ve"));
		softAssertion.assertTrue(page.extractTextFromTiff(tiffFile).contains("Bottom - Right Multiple Line Branding Tex.."));
		softAssertion.assertAll();
		base.passedStep("Verify that branding text should be truncated when Branding text "
				+ "exceeds the space and no space after wrapping while production for a TIFF file");
		loginPage.logout();
	}
	
	/**
	 * @author sowndarya Testcase No:RPMXCON-48976
	 * @Description: Verify that branding text should be wrapped when Branding text 
	 * to all the six locations exceeds the space while production for a TIFF file
	 **/
	@Test(description = "RPMXCON-48976", enabled = true, groups = { "regression" })
	public void VerifyMultiLineBrWrapFrTiff() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48976");
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		base.stepInfo("Verify that branding text should be wrapped when Branding text "
				+ "to all the six locations exceeds the space while production for a TIFF file");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in As " + Input.pa1userName);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch("DocFileType", "Spreadsheet");
		sessionSearch.ViewInDocList();
		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(2);
		doclist.bulkTagExistingFromDoclist(tagname);
		
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		base.waitForElement(page.getTIFFChkBox());
		page.getTIFFChkBox().Click();
		driver.scrollingToBottomofAPage();
		base.waitForElement(page.getTIFFTab());
		page.getTIFFTab().Click();
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
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		base.waitUntilFileDownload();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		page.deleteFiles();
		page.extractFile();
		driver.waitForPageToBeReady();
		File tiffFile = new File(
				home + "/Downloads/" + "VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tiff");
		softAssertion.assertTrue(page.extractTextFromTiff(tiffFile).contains("Top - Center Multiple Line Branding Text Verify"));
		softAssertion.assertTrue(page.extractTextFromTiff(tiffFile).contains("Bottom - Center Multiple Line Branding Text Ve"));
		softAssertion.assertAll();
   		base.passedStep("Verified -  that branding text should be wrapped when Branding text "
				+ "to all the six locations exceeds the space while production for a TIFF file");
		loginPage.logout();
	}
	
	/**
	 * @author Brundha RPMXCON-47744 Date:9/27/2022
	 * @Description To verify the DAT section in Production Components tab, on
	 *              selection.
	 */
	@Test(description = "RPMXCON-47744", enabled = true, groups = { "regression" })
	public void verifyingDATSection() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-47744 -Production component");
		base.stepInfo("To verify the DAT section in Production Components tab, on selection.");

		String productionname = "P" + Utility.dynamicNameAppender();

		ProductionPage page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		base.waitForElement(page.getDATChkBox());
		page.getDATTab().waitAndClick(10);
		base.stepInfo("verifying DAT Section");
		base.ValidateElement_Presence(page.getDAT_FieldClassification1(), "Field Classification");
		base.ValidateElement_Presence(page.getDAT_SourceField1(), "Source Field");
		base.ValidateElement_Presence(page.getDAT_DATField1(), "DAT Field");
		base.ValidateElement_Presence(base.text("Encoding"), "Encoding");
		base.ValidateElement_Presence(base.text("Field Delimiters"), "Field Delimiter");
		base.ValidateElement_Presence(base.text("Date Format"), "DateFormat");
		base.ValidateElement_Presence(page.getDatDateFormate(), "Date Format dropdown");
		base.ValidateElement_Presence(page.defaultFielsSeperator(), "Field Classification");
		base.ValidateElement_Presence(page.defaultTextQualifier(), "Text qualifier");
		base.ValidateElement_Presence(page.defaultMultiValue(), "MultiValue");
		base.ValidateElement_Presence(page.defaultNewLineSeperator(), "New line");
		base.ValidateElement_Presence(base.text("Unicode"), "Unicode");
		base.ValidateElement_Presence(page.getANSIDropDown(), "ANSI DropDown");
		
		loginPage.logout();

	}

	/**
	 * @author Brundha Testcase No:RPMXCON-49378
	 * @Description: To verify that in Production, DocFileExtension should be used
	 *               in the file name as Native , when DocFileExtension as non-blank
	 *               value for Uploaded documents
	 **/
	@Test(description = "RPMXCON-49378", enabled = true, groups = { "regression" })
	public void verifyingDocFileExtensionInDownloadedFiles() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-49378 -Production Component");
		base.stepInfo(
				"To verify that in Production, DocFileExtension should be used in the file name as Native , when DocFileExtension as non-blank value for Uploaded documents");

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");

		DataSets dataset = new DataSets(driver);
		base.stepInfo("Navigating to dataset page");
		dataset.navigateToDataSetsPage();
		dataset.SelectingUploadedDataSet();

		DocListPage doc = new DocListPage(driver);
		driver.waitForPageToBeReady();
		doc.selectingSingleValueInCoumnAndRemovingExistingOne(Input.docFileExt);
		int DocFileExtension = base.getIndex(doc.getHeaderText(), Input.docFileExt);
		List<String> FileExtense = base.availableListofElements(doc.GetColumnData(DocFileExtension));
		String FirstFile = FileExtense.get(0).toString().trim();
		System.out.println(FirstFile);
		String SecondFileFile = FileExtense.get(1).toString().trim();
		System.out.println(SecondFileFile);
		String ThirdFile = FileExtense.get(2).toString().trim();
		System.out.println(ThirdFile);
		doc.documentSelection(3);
		doc.bulkTagExistingFromDoclist(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.addDATFieldAtSecondRow(Input.productionText, Input.tiffPageCountNam, Input.tiffPageCountText);
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
		base.waitUntilFileDownload();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		page.deleteFiles();
		page.extractFile();
		driver.waitForPageToBeReady();
		
		base.stepInfo("verifying the docfile extension in downloaded nativefile");
		int Count = Integer.valueOf(beginningBates) + Integer.valueOf(Input.pageCount);
        int ThirdDoc=Count+ + Integer.valueOf(Input.pageCount);
		File Native = new File(
				home + "/Downloads/VOL0001/Natives/0001/" + prefixID + beginningBates + suffixID + FirstFile);
		File Native2File = new File(
				home + "/Downloads/VOL0001/Natives/0001/" + prefixID + Count + suffixID + SecondFileFile);
		File Native3File = new File(
				home + "/Downloads/VOL0001/Natives/0001/" + prefixID + ThirdDoc + suffixID + ThirdFile);
		if (Native.exists()) {
			base.passedStep("Native file is generated with DocFileExtension ");
		} else {
			base.failedStep("verification failed");
		}
		if (Native2File.exists()) {
			base.passedStep("Native file is generated with DocFileExtension");
		} else {
			base.failedStep("verification failed");
		}

		if (Native3File.exists()) {
			base.passedStep("Native file is generated with DocFileExtension");
		} else {
			base.failedStep("verification failed");
		}

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();

	}
	/**
	 * @author Brundha Testcase No:RPMXCON-49730
	 * @Description: Verify that production is generated using custom template
	 *               having branding then it should be applied on all
	 *               pages/documents on generated files
	 **/
	@Test(description = "RPMXCON-49730", enabled = true, groups = { "regression" })
	public void verifyingBrandingTextInGeneratedDocuments() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		DocViewPage Doc = new DocViewPage(driver);
		DocViewRedactions DocRedactions = new DocViewRedactions(driver);
		base.stepInfo("RPMXCON-49730 -Production Component");
		base.stepInfo(
				"Verify that production is generated using custom template having branding then it should be applied on all pages/documents on generated files");

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String productionname1 = "p" + Utility.dynamicNameAppender();
		String Templatename = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String PlaceholderText = "Confidentiality";

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassificationInRMU(tagname, "Select Tag Classification");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.SearchMetaData("RequirePDFGeneration", Input.pageCount);
		sessionSearch.ViewInDocList();

		DocListPage doc = new DocListPage(driver);
		doc.documentSelection(3);
		sessionSearch.bulkTagExisting(tagname);

		doc.documentSelection(3);
		doc.viewSelectedDocumentsInDocView();
		DocRedactions.redactRectangleUsingOffset(10, 10, 20, 20);
		driver.waitForPageToBeReady();
		DocRedactions.selectingRedactionTag2(Input.defaultRedactionTag);
		int PageCount = Doc.getTotalPagesCount();

		driver.Navigate().refresh();
		DocRedactions.selectDoc2();
		DocRedactions.redactRectangleUsingOffset(10, 10, 100, 100);
		DocRedactions.selectingRedactionTag2(Input.defaultRedactionTag);
		int PageCount2Doc = Doc.getTotalPagesCount();

		DocRedactions.doclistTable(3).waitAndClick(10);
		int PageCount3Doc = Doc.getTotalPagesCount();

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectGenerateOption(true);
		page.fillingBrandingInTiffSection(Input.batesNumber, PlaceholderText);
		page.navigateToNextSection();
		page.navigatingToProductionHomePage();
		
		//production saved as template 
		page.savedTemplateAndNewProdcution(productionname, Templatename);
		page.baseInfoLoadTemplate(productionname1, Templatename);
		driver.waitForPageToBeReady();
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

		int Count = Integer.valueOf(beginningBates) + PageCount;
		int LastDoc = Count + PageCount2Doc;
		page.extractFile();

		String BatesNumber = prefixID + beginningBates + suffixID;
		String BatesNumber1 = prefixID + Count + suffixID;
		String BatesNumber2 = prefixID + LastDoc + suffixID;

		page.pdfVerificationInDownloadedFile(BatesNumber, PageCount, prefixID, PlaceholderText);
		page.pdfVerificationInDownloadedFile(BatesNumber1, PageCount2Doc, prefixID, PlaceholderText);
		page.pdfVerificationInDownloadedFile(BatesNumber2, PageCount3Doc, prefixID, PlaceholderText);

		//Delete tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();
	}
	
	/**
	 * @author sowndarya Testcase No:RPMXCON-63236
	 * @Description: Verify that existing Production template should display with 'Skip text generation/OCR for non-redacted docs' "
				+ "under Text section and Production should be generated successfully using same template
	 **/
	@Test(description = "RPMXCON-63236", enabled = true, groups = { "regression" })
	public void verifyExProdTemplWIthSkipTextGen() throws Exception {
		UtilityLog.info(Input.prodPath);
		base = new BaseClass(driver);
		base.stepInfo("Test Cases Id : RPMXCON-63236");
		base.stepInfo(
				"Verify that existing Production template should display with 'Skip text generation/OCR for non-redacted docs' "
				+ "under Text section and Production should be generated successfully using same template");
	
		tagname = "Tag" + Utility.dynamicNameAppender();
		productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String bates = "B" + Utility.dynamicNameAppender();
		String templateName = "Temp" + Utility.dynamicNameAppender();
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		// create tag 	
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for tag
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch("DocFileType", "Spreadsheet");
		sessionSearch.ViewInDocList();
		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(2);
		doclist.bulkTagExistingFromDoclist(tagname);
		
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.addNewSearch();
		sessionSearch.removeAllAddedTiles();
		sessionSearch.newMetaDataSearchInBasicSearch("DocFileType", "TEXT");
		sessionSearch.ViewInDocList();
		doclist.documentSelection(2);
		doclist.bulkTagExistingFromDoclist(tagname);
		
		page.navigateToProductionPage();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSectionWithBates(Input.bates, Input.batesNumber, bates);
		page.fillingPDFSection(tagname);
		page.fillingTextSection();
		base.waitForElement(page.getRdbOcr());
		page.getRdbOcr().waitAndClick(5);
		base.waitForElement(page.getOkButton());
		page.getOkButton().waitAndClick(4);
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
		
		page.navigateToProductionPage();
		page.selectSavedTemplateAndManageTemplate(productionname, templateName);
		driver.waitForPageToBeReady();
		page.getTextTab().waitAndClick(5);
		driver.scrollingToBottomofAPage();
		String option = page.getSecondOptInText().Value();
		System.out.println(option);
		softAssertion.assertEquals(option, "True");
//		softAssertion.assertAll();
		
		page.navigateToProductionPage();
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProductiontWithTemplate(productionname, templateName);
		page.navigateToNextSection();
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
		String name = page.getProduction().getText().trim();
		page.deleteFiles();
		page.extractFile();
		driver.waitForPageToBeReady();
		File DatFile = new File(home + "/Downloads/VOL0001/Load Files/" + name + "_DAT.dat");
		File pdfFile = new File(
				home + "/Downloads/" + "VOL0001/PDF/0001/" + prefixID + beginningBates + suffixID + ".pdf");
		File textFile = new File(
				home + "/Downloads/" + "VOL0001/Text/0001/" + prefixID + beginningBates + suffixID + ".txt");
		
		if (DatFile.exists()) {
			base.passedStep("Dat File Generated As Expected");
		} else {
			base.failedStep("Dat File Not Generated As Expected");
		}
		if (pdfFile.exists()) {
			base.passedStep("PDF File Generated As Expected");
		} else {
			base.failedStep("PDF File Not Generated As Expected");
		}
		if (textFile.exists()) {
			base.passedStep("TEXT File Generated As Expected");
		} else {
			base.failedStep("TEXT File Not Generated As Expected");
		}
		base.passedStep("Verify that existing Production template should display with 'Skip text generation/OCR for non-redacted docs' under"
				+ " Text section and Production should be generated successfully using same template");
		loginPage.logout();
	}
	
	/**
	 * @author sowndarya Testcase No:RPMXCON-63237
	 * @Description: Verify when user selects existing production template and selects 'Do not OCR non-redacted docs...' "
				+ "in Production-text component then it should export blank text for non-redacted document.
	 **/
	@Test(description = "RPMXCON-63237", enabled = true, groups = { "regression" })
	public void verifyExProdTemplWIthSkipTextGenDoNotOCR() throws Exception {
		UtilityLog.info(Input.prodPath);
		base = new BaseClass(driver);
		base.stepInfo("Test Cases Id : RPMXCON-63237");
		base.stepInfo(
				"Verify when user selects existing production template and selects 'Do not OCR non-redacted docs...' "
				+ "in Production-text component then it should export blank text for non-redacted document.");
	
		tagname = "Tag" + Utility.dynamicNameAppender();
		productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String bates = "B" + Utility.dynamicNameAppender();
		String templateName = "Temp" + Utility.dynamicNameAppender();
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		// create tag 	
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		
		// search for tag
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch("DocFileType", "Spreadsheet");
		sessionSearch.ViewInDocList();
		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(2);
		doclist.bulkTagExistingFromDoclist(tagname);
		
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.addNewSearch();
		sessionSearch.removeAllAddedTiles();
		sessionSearch.newMetaDataSearchInBasicSearch("DocFileType", "TEXT");
		sessionSearch.ViewInDocList();
		doclist.documentSelection(2);
		doclist.bulkTagExistingFromDoclist(tagname);
		
		page = new ProductionPage(driver);
		page.navigateToProductionPage();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSectionWithBates(Input.bates, Input.batesNumber, bates);
		page.fillingPDFSection(tagname);
		page.fillingTextSection();
		base.waitForElement(page.getRdbOcr());
		page.getRdbOcr().waitAndClick(5);
		base.waitForElement(page.getOkButton());
		page.getOkButton().waitAndClick(4);
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
		
		page.navigateToProductionPage();
		page.selectSavedTemplateAndManageTemplate(productionname, templateName);
		driver.waitForPageToBeReady();
		
		page.navigateToProductionPage();
		productionname = "p" + Utility.dynamicNameAppender();
		page.addANewProductiontWithTemplate(productionname, templateName);	
		base.waitForElement(page.getTextTab());
		page.getTextTab().waitAndClick(5);
		driver.scrollingToBottomofAPage();
		base.waitForElement(page.getFirstOptInText());
		page.getFirstOptInText().waitAndClick(5);
		driver.waitForPageToBeReady();
		
		page.navigateToNextSection();
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
		page.deleteFiles();
		page.extractFile();
		driver.waitForPageToBeReady();
		File textFile = new File(
				home + "/Downloads/" + "VOL0001/Text/0001/" + prefixID + beginningBates + suffixID + ".txt");
		
		if (textFile.exists()) {
			base.passedStep("Text File Generated As Expected");
		} else {
			base.failedStep("Text File Not Generated As Expected");
		}
		base.passedStep("Verify when user selects existing production template and selects 'Do not OCR non-redacted docs...'"
				+ " in Production-text component then it should export blank text for non-redacted document.");
		loginPage.logout();
		}
	
	/**
	 * @author Brundha Testcase No:RPMXCON-49731
	 * @Description:Verify regeneration of production if user add/edit branding in
	 *                     TIFF/PDF section
	 **/
	@Test(description = "RPMXCON-49731", enabled = true, groups = { "regression" })
	public void verifyingBrandingTextInReGeneratedDocuments() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		base.stepInfo("RPMXCON-49731 -Production Component");
		base.stepInfo("Verify regeneration of production if user add/edit branding in TIFF/PDF section");

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String PlaceholderText = "Confidentiality";

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassificationInRMU(tagname, "Select Tag Classification");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.SearchMetaData("RequirePDFGeneration", Input.pageCount);
		sessionSearch.ViewInDocList();

		DocListPage doc = new DocListPage(driver);
		doc.documentSelection(3);
		sessionSearch.bulkTagExisting(tagname);
		doc.documentSelection(3);
		doc.viewSelectedDocumentsInDocView();

		DocViewPage Doc = new DocViewPage(driver);
		DocViewRedactions DocRedactions = new DocViewRedactions(driver);
		base.waitTime(3);
		DocRedactions.redactRectangleUsingOffset(10, 10, 20, 20);
		driver.waitForPageToBeReady();
		DocRedactions.selectingRedactionTag2(Input.defaultRedactionTag);
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
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		driver.waitForPageToBeReady();
		int Count = Integer.valueOf(beginningBates) + PageCount;
		int LastDoc = Count + PageCount2Doc;
		page.extractFile();

		String BatesNumber = prefixID + beginningBates + suffixID;
		String BatesNumber1 = prefixID + Count + suffixID;
		String BatesNumber2 = prefixID + LastDoc + suffixID;
		page.pdfVerificationInDownloadedFile(BatesNumber, 2, prefixID, PlaceholderText);
		page.pdfVerificationInDownloadedFile(BatesNumber1, PageCount2Doc, prefixID, PlaceholderText);
		page.pdfVerificationInDownloadedFile(BatesNumber2, PageCount3Doc, prefixID, PlaceholderText);
		driver.waitForPageToBeReady();
		page.deleteFiles();
		page.deleteProducedZipFile();
		String ReGeneratedText = Input.searchString1;
		driver.waitForPageToBeReady();
		page.clickElementNthtime(page.getBackButton(), 7);
		page.getMarkInCompleteBtn().waitAndClick(10);
		base.waitForElement(page.getTIFFTab());
		page.getTIFFTab().Click();
		driver.scrollingToElementofAPage(page.getPrivPlaceholderTextboInPrivGaurd());
		page.getPrivPlaceholderTextboInPrivGaurd().Clear();
		page.getPrivPlaceholderTextboInPrivGaurd().SendKeys(ReGeneratedText);
		page.clickMArkCompleteMutipleTimes(3);
		page.fillingPrivGuardPage();
		page.clickMArkCompleteMutipleTimes(2);
		page.fillingGeneratePageWithContinueGenerationPopup();
	    page.extractFile();
		driver.waitForPageToBeReady();
		page.pdfVerificationInDownloadedFile(BatesNumber, 2, prefixID, ReGeneratedText);
		page.pdfVerificationInDownloadedFile(BatesNumber1, PageCount2Doc, prefixID, ReGeneratedText);
		page.pdfVerificationInDownloadedFile(BatesNumber2, PageCount3Doc, prefixID, ReGeneratedText);

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
