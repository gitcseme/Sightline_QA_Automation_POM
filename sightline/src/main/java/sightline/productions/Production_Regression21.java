package sightline.productions;

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
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.RedactionPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Production_Regression21 {
	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	DocViewPage docView;
	Utility utility;
	String foldername;
	String tagname;
	String productionname;

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
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());

		driver = new Driver();
		loginPage = new LoginPage(driver);

	}

	/**
	 * @author Brundha TESTCASE No:RPMXCON-48645 Date:9/7/2022
	 * @Description:To verify that if "Do Not Produce TIFFs for Natively Produced
	 *                 Docs" is disabled , then TIFFs is generated with Placeholder
	 */
	@Test(description = "RPMXCON-48645", enabled = true, groups = { "regression" })

	public void verifyingPrivPlaceholderForSelectedDocs() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-48645-from Production Component");
		base.stepInfo(
				"To verify that if 'Do Not Produce TIFFs for Natively Produced Docs' is disabled , then TIFFs is generated with Placeholder");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.ViewInDocListWithOutPureHit();

		DocListPage doclist = new DocListPage(driver);
		int Doc = 3;
		doclist.documentSelection(Doc);
		doclist.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		int FirstFile = Integer.valueOf(beginningBates);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionPrivDocs(tagname, Input.searchString4);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		String home = System.getProperty("user.home");
		page.deleteFiles();
		page.extractFile();
		driver.waitForPageToBeReady();
		int Lastile = FirstFile + Doc;
		page.isImageFileExist(FirstFile, Lastile, prefixID, suffixID);
		page.OCR_Verification_In_Generated_Tiff_tess4j(FirstFile, Lastile, prefixID, suffixID, Input.searchString4);

		int File = FirstFile + Doc + Integer.valueOf(Input.pageCount);
		File TiffFile = new File(home + "/Downloads/" + "VOL0001/Images/0001/" + prefixID + File + suffixID + ".tiff");
		page.verifyingTiffImage(TiffFile, Input.searchString4);

		File NativeFile = new File(home + "/Downloads/VOL0001/Natives/0001/");
		page.isfileisExists(NativeFile);
		File[] dir_contents = NativeFile.listFiles();
		int nativefile = dir_contents.length;
		base.digitCompareEquals(nativefile, Doc, "Native files are generated as expected",
				"Native files are not generated as expected");
		loginPage.logout();
	}

	/**
	 * @author Brundha TESTCASE No:RPMXCON-47820 Date:9/7/2022
	 * @Description:To verify Native generation for JPEG and PPT file.
	 */
	@Test(description = "RPMXCON-47820", enabled = true, groups = { "regression" })

	public void verifyingNativeFileTypeInGeneratedProduction() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-47820-from Production Component");
		base.stepInfo("To verify Native generation for JPEG and PPT file.");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		int Count = Integer.valueOf(Input.pageCount);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.SearchMetaData(Input.docFileType, "JPEG");
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		int beginningBate = Integer.valueOf(beginningBates) + Count + Count;
		System.out.println(beginningBate);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		String home = System.getProperty("user.home");
		page.deleteFiles();
		page.extractFile();
		driver.waitForPageToBeReady();
		File NativeFile = new File(home + "/Downloads/VOL0001/Natives/0001/");

		File NativeFileType = new File(
				home + "/Downloads/VOL0001/Natives/0001/" + prefixID + beginningBate + suffixID + ".jpg");
		page.isfileisExists(NativeFile);
		if (NativeFileType.exists()) {
			base.passedStep("" + NativeFileType + " is displayed");
		} else {
			base.failedStep("" + NativeFileType + " is not displayed");
		}
		loginPage.logout();
	}

	/**
	 * @author Brundha TESTCASE No:RPMXCON-47859 Date:9/7/2022
	 * @Description:Verify the static and dynamic display of 'QC & confirmation'
	 *                     Page by actually generating and not generating production
	 */
	@Test(description = "RPMXCON-47859", enabled = true, groups = { "regression" })

	public void verifyingDynamicDisplayOfProduction() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-47859-from Production Component");
		base.stepInfo(
				"Verify the static and dynamic display of 'QC & confirmation' Page by actually generating and not generating production");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String prefixID1 = Input.randomText + Utility.dynamicNameAppender();
		String suffixID1 = Input.randomText + Utility.dynamicNameAppender();
		String BatesNum = "B" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		String beginningBates1 = page.getRandomNumber(2);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionPrivDocs(tagname, Input.searchString4);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommitandDownload();
		page.getBackButton().waitAndClick(5);
		driver.waitForPageToBeReady();
		String Batesno = page.getProd_BatesRange().getText();

		page.clickElementNthtime(page.getBackButton(), 6);
		driver.scrollPageToTop();
		page.getMarkIncompleteButton().waitAndClick(5);
		page.getDATTab().waitAndClick(5);
		driver.scrollingToElementofAPage(page.getDAT_DATField1());
		page.getDAT_DATField1().SendKeys(BatesNum);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID1, suffixID1, beginningBates1);
		page.clickMArkCompleteMutipleTimes(2);
		page.getMarkIncompleteButton().waitAndClick(5);
		page.priviledgeDocCheck(true, tagname);
		page.fillingPrivGuardPage();
		page.clickMArkCompleteMutipleTimes(2);
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		String name = page.getProduction().getText().trim();
		driver.waitForPageToBeReady();
		File DatFile = new File(home + "/Downloads/VOL0001/Load Files/" + name + "_DAT.dat");
		page.isdatFileExist();
		page.verifyingDATFile(DatFile, 0, 1, BatesNum);

		page.getBackButton().waitAndClick(5);
		String BatesNumber = page.getProd_BatesRange().getText();
		base.textCompareNotEquals(Batesno, BatesNumber, "Dynamic display of production is dsiplayed",
				"Dynamic display of production is not displayed");

		loginPage.logout();
	}

	/**
	 * @author Brundha RPMXCON-63190 Date:9/7/2022
	 * @DescriptionVerify that New Production template should display with 'Skip
	 *                    text generation/OCR for non-redacted docs' under Text
	 *                    section and Production should be generated successfully
	 *                    using same template
	 */
	@Test(description = "RPMXCON-63190", enabled = true, groups = { "regression" })
	public void verifyingTemplateInProductionComponent() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-63190 -Production component");
		base.stepInfo(
				"Verify that New Production template should display with 'Skip text generation/OCR for non-redacted docs' under Text section and Production should be generated successfully using same template");

		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "P" + Utility.dynamicNameAppender();
		String productionname1 = "P" + Utility.dynamicNameAppender();
		String Templatename = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.telecaSearchString);
		sessionSearch.bulkRelease(Input.securityGroup);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.addDATFieldAtSecondRow(Input.productionText, Input.tiffPageCountNam, Input.tiffPageCountText);
		page.fillingNativeSection();
		page.fillingTIFFSectionPrivDocs(tagname, Input.searchString4);
		page.fillingTextSection();
		page.navigateToNextSection();

		page.navigateToProductionPage();
		page.selectSavedTemplateAndManageTemplate(productionname, Templatename);
		driver.waitForPageToBeReady();
		page.getTextTab().waitAndClick(5);
		driver.scrollingToBottomofAPage();
		page.getCheckBoxCheckedVerification(page.getTextRadioBtn());
		base.validatingGetTextElement(page.getTextFirstRadioBtn(), "Do not OCR. Export blank text files");
		base.validatingGetTextElement(page.getTextSecondRadioBtn(), "OCR non-redacted documents without text");
		page.getCloseIconInManageTemplate().waitAndClick(5);

		page.navigateToProductionPage();
		page.baseInfoLoadTemplate(productionname1, Templatename);
		page.getCheckBoxCheckedVerification(page.chkIsDATSelected());
		page.getCheckBoxCheckedVerification(page.chkIsTIFFSelected());
		driver.scrollingToBottomofAPage();
		page.getTextTab().waitAndClick(5);
		page.getCheckBoxCheckedVerification(page.getTextRadioBtn());
		driver.scrollingToBottomofAPage();
		base.validatingGetTextElement(page.getTextFirstRadioBtn(), "Do not OCR. Export blank text files");
		base.validatingGetTextElement(page.getTextSecondRadioBtn(), "OCR non-redacted documents without text");
		loginPage.logout();

	}

	/**
	 * @author Brundha RPMXCON-55948 Date:9/12/2022
	 * @DescriptionVerify if user included default branding text along with branding
	 *                    that is based on the tag types then branding requested
	 *                    based on the tagging should displays on 'Preview' document
	 *                    and on produced documents also
	 */
	@Test(description = "RPMXCON-55948", enabled = true, groups = { "regression" })
	public void verifyBrandingSectionInTIFFAndPdfGenration() throws Exception {
		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-55948 -Production Component");
		base.stepInfo(
				"Verify if user included default branding text along with branding that is based on the tag types then branding requested based on the tagging should displays on 'Preview' document and on produced documents also");

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname1 = "p" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String prefixID1 = Input.randomText + Utility.dynamicNameAppender();
		String suffixID1 = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, "Select Tag Classification");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		int PureHit = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.ViewInDocList();

		DocListPage doc = new DocListPage(driver);
		doc.documentSelection(2);
		doc.bulkTagExistingFromDoclist(tagname);

		ProductionPage page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		String beginningBates1 = page.getRandomNumber(2);
		int FirstFile1 = Integer.valueOf(beginningBates1);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectGenerateOption(false);
		page.FillingBrandingInTiffSection(tagname, Input.searchString4);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID1, suffixID1, beginningBates1);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.viewingPreviewInSummaryTab();
		page.verifyingPdfgeneration(Input.searchString4);
		String TotalPageCount = page.getValueTotalPagesCount().getText();
		System.out.println(TotalPageCount);
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		int LastFile1 = FirstFile1 + PureHit;
		page.deleteFiles();
		page.extractFile();
		driver.waitForPageToBeReady();
		page.OCR_Verification_In_Generated_Tiff_tess4j(FirstFile1, LastFile1, prefixID1, suffixID1,
				Input.searchString4);
		int PDFFirstBrandingTextFiles = PureHit + FirstFile1;
		int PDFBrandingTextFile = PureHit + FirstFile1 + Integer.valueOf(Input.pageCount);
		page.OCR_Verification_In_Generated_Tiff_tess4j(PDFFirstBrandingTextFiles, PDFBrandingTextFile, prefixID1,
				suffixID1, Input.testData1);

		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname1);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectGenerateOption(true);
		page.FillingBrandingInTiffSection(tagname, Input.searchString4);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname1);
		page.navigateToNextSection();
		page.viewingPreviewInSummaryTab();
		page.verifyingPdfgeneration(Input.searchString4);
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.deleteFiles();
		page.extractFile();
		driver.waitForPageToBeReady();
		page.pdf_Verification_In_Generated_File(beginningBates, prefixID, suffixID, Input.searchString4);
		int BrandingTextFile = Integer.valueOf(beginningBates) + Integer.valueOf(TotalPageCount)
				+ Integer.valueOf(Input.pageCount);
		System.out.println(BrandingTextFile);
		String Bates = String.valueOf(BrandingTextFile);
		page.pdf_Verification_In_Generated_File(Bates, prefixID, suffixID, Input.testData1);
		loginPage.logout();

	}

	/**
	 * @author Brundha RPMXCON-47965 Date:9/12/2022
	 * @Description Verify if document with redaction,without text (ingested) and
	 *              second option ("OCR non-redacted docs... ") is selected in
	 *              Production-text component then it should OCRed to export text
	 *              with 'REDCATED TEXT'.
	 */
	@Test(description = "RPMXCON-47965", enabled = true, groups = { "regression" })
	public void verifyTextSectionInProducedDocument() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("RPMXCON-47965 -Production component");
		base.stepInfo(
				"Verify if document with redaction,without text (ingested) and second option (\"OCR non-redacted docs... \") is selected in Production-text component then it should OCRed to export text with 'REDCATED TEXT'.");

		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String productionname = "P" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();

		RedactionPage redactionpage = new RedactionPage(driver);
		driver.waitForPageToBeReady();
		redactionpage.manageRedactionTagsPage(Redactiontag1);
		System.out.println("First Redaction Tag is created" + Redactiontag1);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.telecaSearchString);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.ViewInDocViewWithoutPureHit();

		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		driver.waitForPageToBeReady();
		docViewRedactions.selectDoc2();
		base.waitTime(2);
		docViewRedactions.redactRectangleUsingOffset(10, 10, 80, 80);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag1);

		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.telecaSearchString);
		sessionSearch.bulkRelease(Input.securityGroup);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.addDATFieldAtSecondRow(Input.productionText, Input.tiffPageCountNam, Input.tiffPageCountText);
		page.selectGenerateOption(false);
		page.getClk_burnReductiontoggle().waitAndClick(10);
		page.burnRedactionWithRedactionTag(Redactiontag1);
		page.fillingTextSection();
		page.textComponentVerification();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		page.extractFile();
		int Bates=Integer.valueOf(beginningBates)+4;
		driver.waitForPageToBeReady();
		File TiffFile = new File(home + "/Downloads/VOL0001/Load Files/" + productionname + "_TIFF.OPT");
		File Textfile = new File(home + "/Downloads/VOL0001/Text/0001");
		File TextFile = new File(
				home + "/Downloads/VOL0001/Text/0001/" + prefixID + Bates + suffixID + ".txt");
		page.isdatFileExist();
		page.isfileisExists(Textfile);
		if (TiffFile.exists()) {
			base.passedStep("Tiff file is generated as expected");
		} else {
			base.failedStep("Tiff file is not generated as expected");
		}
		String Content;
		String totaltext = "";
		try (BufferedReader brReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(TextFile), "UTF16"))) {
			while ((Content = brReader.readLine()) != null) {
				totaltext += Content;
			}
			if (totaltext.contains(Input.searchString4)) {
				base.passedStep("Redacted text is displayed in text file");
			} else {
				base.failedStep("Redacted text is not displayed in text file");
			}
		}

		loginPage.logout();
	}

	/**
	 * @author Brundha TESTCASE No:RPMXCON-47808 Date:9/12/2022
	 * @Description:To Verify production place holder images for Privilege doc and
	 *                 documents based on selected file types
	 */
	@Test(description = "RPMXCON-47808", enabled = true, groups = { "regression" })

	public void verifyingPrivPlaceHolderInGeneratedFile() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-47808-from Production Component");
		base.stepInfo(
				"To Verify production place holder images for Privilege doc and documents based on selected file types");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.ViewInDocListWithOutPureHit();

		DocListPage doclist = new DocListPage(driver);
		int Doc = 3;
		doclist.documentSelection(Doc);
		doclist.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		int FirstFile = Integer.valueOf(beginningBates);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectGenerateOption(false);
		base.ValidateElement_Presence(page.getTIFF_EnableforPrivilegedDocs(), "Privileged Toggle");
		page.toggleOffCheck(page.getTIFF_EnableforPrivilegedDocs());
		if (!page.getPriveldge_SelectTagButton().isDisplayed()) {
			base.passedStep("Priviledged tag is non-editable as expected");
		} else {
			base.failedStep("Priviledged toggle is editable");
		}
		base.ValidateElement_Presence(page.getEnableForNativelyToggle(), "Natively Produced toggle");
		page.toggleOnCheck(page.getEnableForNativelyToggle());
		driver.scrollingToElementofAPage(page.getTIFF_EnableforPrivilegedDocs());
		page.getTIFF_EnableforPrivilegedDocs().waitAndClick(10);
		page.tiffPrivilegeDocumentSelection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		String home = System.getProperty("user.home");
		page.deleteFiles();
		page.extractFile();
		driver.waitForPageToBeReady();
		int Lastile = FirstFile + Doc;
		page.isImageFileExist(FirstFile, Lastile, prefixID, suffixID);
		page.OCR_Verification_In_Generated_Tiff_tess4j(FirstFile, Lastile, prefixID, suffixID, Input.tagNameTechnical);
		int File = FirstFile + Doc;
		driver.waitForPageToBeReady();
		File NativeFile = new File(home + "/Downloads/VOL0001/Natives/0001/" + prefixID + File + suffixID + ".doc");
		if (NativeFile.exists()) {
			base.passedStep("" + NativeFile + " is generated as expected");
		} else {
			base.failedStep("" + NativeFile + "  is not generated as expected");
		}

		loginPage.logout();
	}

	/**
	 * @author Brundha RPMXCON-63119 Date:9/12/2022
	 * @Description Verify if document with redaction, without text (ingested) and
	 *              default option ("Do not OCR non-redacted docs...") is selected
	 *              in Production-text component then it should OCRed to export text
	 *              with 'REDCATED TEXT'.
	 */
	@Test(description = "RPMXCON-63119", enabled = true, groups = { "regression" })
	public void verifyingRedactedDocumetInGeneratedTextFile() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("RPMXCON-63119 -Production component");
		base.stepInfo(
				"Verify if document with redaction, without text (ingested) and default option (\"Do not OCR non-redacted docs...\") is selected in Production-text component then it should OCRed to export text with 'REDCATED TEXT'.");

		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String productionname = "P" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();

		RedactionPage redactionpage = new RedactionPage(driver);
		driver.waitForPageToBeReady();
		redactionpage.manageRedactionTagsPage(Redactiontag1);
		System.out.println("First Redaction Tag is created" + Redactiontag1);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.telecaSearchString);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.ViewInDocViewWithoutPureHit();

		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		base.waitTime(2);
		docViewRedactions.redactRectangleUsingOffset(10, 10, 100, 80);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag1);

		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.telecaSearchString);
		sessionSearch.bulkRelease(Input.securityGroup);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectGenerateOption(false);
		page.getClk_burnReductiontoggle().waitAndClick(10);
		page.burnRedactionWithRedactionTag(Redactiontag1);
		driver.scrollingToElementofAPage(page.getTextChkBox());
		page.getTextChkBox().waitAndClick(5);
		page.getTextTab().waitAndClick(5);
		driver.scrollingToBottomofAPage();
		page.getCheckBoxCheckedVerification(page.getTextRadioBtn());
		driver.scrollPageToTop();
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
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		page.extractFile();
		driver.waitForPageToBeReady();
		File TextFile = new File(
				home + "/Downloads/VOL0001/Text/0001/" + prefixID + beginningBates + suffixID + ".txt");
		page.isdatFileExist();
		page.isfileisExists(TextFile);

		String Content;
		String totaltext = "";
		try (BufferedReader brReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(TextFile), "UTF16"))) {
			while ((Content = brReader.readLine()) != null) {
				totaltext += Content;
			}
			if (totaltext.contains(Input.searchString4)) {
				base.passedStep("Redacted text is displayed in text file");
			} else {
				base.failedStep("Redacted text is not displayed in text file");
			}
		}

		loginPage.logout();
	}

	/**
	 * @author Brundha RPMXCON-55655 Date:9/14/2022
	 * @Description To Verify ProjectAdmin will be able to view the produced
	 *              documents at production path
	 */
	@Test(description = "RPMXCON-55655", enabled = true, groups = { "regression" })
	public void verifyingPAUserCopyPathInGeneratedFile() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-55655 -Production component");
		base.stepInfo("To Verify ProjectAdmin will be able to view the produced documents at production path");

		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String productionname = "P" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		page.getCopyPath().waitAndClick(10);
		String actualCopedText = page.getCopiedTextFromClipBoard();
		System.out.println(actualCopedText);
		base.stepInfo("Production Path"+actualCopedText);
		String parentTab = page.openNewTab(actualCopedText);
		page.getFileDir("VOL0001").waitAndClick(5);
		page.getLoadFileLink().waitAndClick(3);
		if (page.getDatFileLink(productionname).isElementAvailable(2)) {
			base.passedStep("Documents are generated for production path");
		} else {
			base.failedStep("Documents are not generated for production path");
		}
		driver.close();
		driver.getWebDriver().switchTo().window(parentTab);

		loginPage.logout();
	}
	/**
	 * @author Brundha RPMXCON-47837 Date:9/16/2022
	 * @Description To Verify Project Admin will have the ability to confirm
	 *              production. Upon confirmation, bates numbers in the documents in
	 *              the database shall be committed
	 */
	@Test(description = "RPMXCON-47837", enabled = true, groups = { "regression" })
	public void verifyingCommitAndNonEditableProduction() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-47837 -Production component");
		base.stepInfo(
				"To Verify Project Admin will have the ability to confirm production. Upon confirmation, bates numbers in the documents in the database shall be committed");

		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String productionname = "P" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		page.getConfirmProductionCommit().waitAndClick(10);
		driver.waitForPageToBeReady();
		driver.Navigate().refresh();
		page.getQC_backbutton().waitAndClick(5);
		String DownloadBtn = page.getbtnGenMarkIncomplete().GetAttribute("disabled");
		base.textCompareEquals(DownloadBtn, "true", "After Commit production is non-editable as expected",
				"After commit production is editable");

		loginPage.logout();

	}

	/**
	 * @author Brundha RPMXCON-47914 Date:9/16/2022
	 * @DescriptionTo Verify when placeholdering is enabled for non-priv document,
	 *                the TIFF generated will be a placeholder, whereas the Text
	 *                generated is the extracted text from the native
	 */
	@Test(description = "RPMXCON-47914", enabled = true, groups = { "regression" })
	public void verifyingThePlaceholderInGeneratedFiles() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-47914 -Production component");
		base.stepInfo(
				"To Verify when placeholdering is enabled for non-priv document, the TIFF generated will be a placeholder, whereas the Text generated is the extracted text from the native");

		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String productionname = "P" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String tagname1 = "Tag" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.createNewTagwithClassification(tagname1, "Select Tag Classification");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		int PureHit = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.ViewInDocList();

		DocListPage doclist = new DocListPage(driver);
		int Doc = 3;
		doclist.documentSelection(Doc);
		doclist.bulkTagExisting(tagname);

		driver.waitForPageToBeReady();
		doclist.documentSelection(6);
		doclist.documentSelection(3);
		doclist.bulkTagExisting(tagname1);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int FirstFile = Integer.valueOf(beginningBates);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectPrivDocsInTiffSection(tagname);
		page.fillingNativeDocsPlaceholder(tagname1);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		int LastFile = FirstFile + Doc;
		int LastNativeFile = PureHit - Doc;
		page.extractFile();
		driver.waitForPageToBeReady();
		System.out.println(LastNativeFile);
		int nativeFile = LastNativeFile + FirstFile;
		System.out.println(nativeFile);
		int endFile = nativeFile + Doc;
		page.OCR_Verification_In_Generated_Tiff_tess4j(FirstFile, LastFile, prefixID, suffixID, Input.tagNameTechnical);
		page.OCR_Verification_In_Generated_Tiff_tess4j(nativeFile, endFile, prefixID, suffixID, Input.technicalIssue);

		loginPage.logout();

	}

	/**
	 * @author Brundha RPMXCON-47789 Date:9/16/2022
	 * @DescriptionTo Verify single/Multiple Privilege rules can be created by the
	 *                user with single multiple operators.
	 */
	@Test(description = "RPMXCON-47789", enabled = true, groups = { "regression" })
	public void verifyingpriviledgedRulesInPrivGuard() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-47789-Production component");

		base.stepInfo(
				"To Verify single/Multiple Privilege rules can be created by the user with single multiple operators.");
		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String tagname1 = Input.randomText + Utility.dynamicNameAppender();
		String tagname2 = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = "P"+ Utility.dynamicNameAppender();
		String suffixID = "S" + Utility.dynamicNameAppender();

		// create tag
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.createNewTagwithClassification(tagname1, Input.tagNamePrev);
		tagsAndFolderPage.createNewTagwithClassification(tagname2, Input.tagNamePrev);

		// search for Tag
		SessionSearch sessionSearch = new SessionSearch(driver);
		int purehit = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		int comment = sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkTagExisting(tagname2);

		int DocCount = comment + purehit;
		System.out.println(DocCount);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPageWithTag(tagname, tagname2);
		page.navigateToNextSection();
		driver.waitForPageToBeReady();
		page.getAddRuleBtn().waitAndClick(5);
		base.stepInfo("Adding multiple rules and operators in priv gaurd section");
		page.addingRulesWithOperatorsInPrivGuardSection(tagname, true, "OR");
		page.addingRulesWithOperatorsInPrivGuardSection(tagname1, true, "NOT");
		page.addingRulesWithOperatorsInPrivGuardSection(tagname2, false, null);
		page.getAddRuleBtn().waitAndClick(5);
		page.getAddRulePrivTag().waitAndClick(5);
		base.waitForElement(page.selectPrivGuardTag(tagname2));
		page.selectPrivGuardTag(tagname2).waitAndClick(10);
		page.getInsertQueryBtnInPrivGuard().waitAndClick(10);
		base.stepInfo("verifying Matching Document Count In Priv guard section");
		driver.waitForPageToBeReady();
		page.getCheckForMatchingDocuments().waitAndClick(10);
		driver.scrollPageToTop();
		base.waitTime(3);
		String Doc = page.getDocumentSelectionLink().getText();
		base.digitCompareEquals(DocCount, Integer.valueOf(Doc), "Matching document count is displayed as expected",
				"Document count is not displayed as expected");

		// Deleting the created tags
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname1, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Brundha RPMXCON-49229 Date:9/16/2022
	 * @Description To verify that in Production, if sorting option is Sort by
	 *              MetaData by Ascending and 'Keep Families Together' check box is
	 *              selected then produced document should be sorted by MetaData
	 *              with FamilyID
	 */
	@Test(description = "RPMXCON-49229", enabled = true, groups = { "regression" })
	public void verifyDATFileAscendingOrder() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-49229 -Production component");
		base.stepInfo(
				"To verify that in Production, if sorting option is Sort by MetaData by Ascending and 'Keep Families Together' check box is selected then produced document should be sorted by MetaData with FamilyID");

		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String productionname = "P" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String line;
		List<String> lines = new ArrayList<>();
		List<String> DatSorting = new ArrayList<>();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.addDATFieldAtSecondRow(Input.fldClassification,Input.docName,Input.testData1);
		page.addDATFieldAtThirdRow(Input.fldClassification, Input.metaDataName, Input.searchString1);
		page.fillingNativeSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		driver.scrollingToBottomofAPage();
		page.getKeepDocsMasterDate().waitAndClick(10);
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
		String name = page.getProduction().getText().trim();
		driver.waitForPageToBeReady();
		File DatFiles = new File(home + "/Downloads/VOL0001/Load Files/" + name + "_DAT.dat");
		page.isdatFileExist();
		base.stepInfo("Verifying DAT file in generated production");
		try (BufferedReader brReader = new BufferedReader(new InputStreamReader(new FileInputStream(DatFiles),"UTF16"))) {
			while ((line = brReader.readLine()) != null) {
				String[] DatFile = line.split("þþ");
				String DatText = DatFile[1];
				lines.add(DatText);
			}}
		lines.remove(0);
		System.out.println(lines);
		for (String value : lines) {
			DatSorting.add(value);
		}
		base.verifyOriginalSortOrder(lines, DatSorting, "Ascending", true);
		loginPage.logout();
	}
	
	
	/**
	 * @author Brundha RPMXCON-47942 Date:9/19/2022
	 * @Description To Verify Sort By DocFileSize "Ascending" in Numbering and
	 *              Sorting Section of Production.
	 */
	@Test(description = "RPMXCON-47942", enabled = true, groups = { "regression" })
	public void verifyDATFileDocFileSizeAscendingOrder() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-47942 -Production component");
		base.stepInfo("To Verify Sort By DocFileSize 'Ascending' in Numbering and Sorting Section of Production.");

		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "P" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String line;
		List<String> lines = new ArrayList<>();
		List<String> DatSorting = new ArrayList<>();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.addDATFieldAtSecondRow(Input.fldClassification, Input.ingDocFileSize, Input.testData1);
		page.addDATFieldAtThirdRow(Input.fldClassification, Input.docName, Input.searchString1);
		page.fillingNativeSection();
		page.selectPrivDocsInTiffSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		driver.scrollingToBottomofAPage();
		base.waitForElement(page.getlstSortingMetaData());
		page.getlstSortingMetaData().selectFromDropdown().selectByVisibleText(Input.ingDocFileSize);
		page.getlstSubSortingMetaData().selectFromDropdown().selectByVisibleText(Input.docName);
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
		String name = page.getProduction().getText().trim();
		driver.waitForPageToBeReady();
		File DatFiles = new File(home + "/Downloads/VOL0001/Load Files/" + name + "_DAT.dat");
		page.isdatFileExist();
		base.stepInfo("Verifying DAT file in generated production");
		try (BufferedReader brReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(DatFiles), "UTF16"))) {
			while ((line = brReader.readLine()) != null) {
				String[] DatFile = line.split("þþ");
				String DatText = DatFile[1];
				lines.add(DatText);
			}
		}
		lines.remove(0);
		System.out.println(lines);
		for (String value : lines) {
			DatSorting.add(value);
		}
		base.verifyOriginalSortOrder(lines, DatSorting, "Ascending", true);
		loginPage.logout();
	}

	/**
	 * @author Brundha TESTCASE No:RPMXCON-48649 Date:9/19/2022
	 * @Description:To verify that if "Do Not Produce PDFs for Natively Produced
	 *                 Docs" is disabled, then PDFs is generated for all docs being
	 *                 produced.
	 */
	@Test(description = "RPMXCON-48649", enabled = true, groups = { "regression" })

	public void verifyingGeneratedFileInProduction() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-48649-from Production Component");
		base.stepInfo(
				"To verify that if 'Do Not Produce PDFs for Natively Produced Docs' is disabled, then PDFs is generated for all docs being produced.");

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String tagname1 = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");
		tagsAndFolderPage.createNewTagwithClassification(tagname1, "Select Tag Classification");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearchForTwoItems(Input.testData1, Input.telecaSearchString);
		sessionSearch.addPureHit();
		sessionSearch.ViewInDocList();

		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(8);
		doclist.bulkTagExisting(tagname);
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		sessionSearch.SearchMetaData(Input.docFileType, ".tiff");
		sessionSearch.addPureHit();
		doclist.bulkTagExisting(tagname1);
		
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectGenerateOption(true);
		driver.scrollPageToTop();
		page.toggleOffCheck(page.getDoNotProduceFullContentTiff());
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPageWithTag(tagname,tagname1);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		String home = System.getProperty("user.home");
		page.deleteFiles();
		page.extractFile();
		driver.waitForPageToBeReady();
		page.isdatFileExist();
		File NativeFile = new File(home + "/Downloads/VOL0001/Natives/0001/");
		File imageFile = new File(
				home + "/Downloads/VOL0001/PDF/0001/" + prefixID + beginningBates + suffixID + ".pdf");
		page.isfileisExists(NativeFile);
		page.isfileisExists(imageFile);
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