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

public class Production_Sprint16_Regression1 {
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
	 * @author Brundha.T No:RPMXCON-47915
	 * @Description:To Verify The failed status (Production Generation Failed)
	 *                 should be clickable and should provide the detailed
	 *                 information on why the generate failed.
	 **/

	@Test(description = "RPMXCON-47915", enabled = true)
	public void verifyingErrorMsgInGeneratePage() throws Exception {

		base = new BaseClass(driver);
		base.stepInfo("Test case Id:RPMXCON-47915- Production component");
		base.stepInfo(
				"To Verify The failed status (Production Generation Failed) should be clickable and should provide the detailed information on why the generate failed.");
		UtilityLog.info(Input.prodPath);

		String foldername = "Prod_Folder" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String productionname1 = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname1);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname1);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickGenarateWithoutWait();
		page.getDocumentGeneratetext().isElementAvailable(60);
		page.verifyingFailedStatusInProduction(productionname1);
		loginPage.logout();
	}

	/**
	 * @author Brundha.T No:RPMXCON-47996
	 * @Description:To Verify Placeholders for Tech Issue documents.
	 **/

	@Test(description = "RPMXCON-47996", enabled = true)
	public void verifyingPlaceholderTextForTechIssueDoc() throws Exception {

		base = new BaseClass(driver);
		base.stepInfo("Test case Id:RPMXCON-47996- Production component");
		base.stepInfo("To Verify Placeholders for Tech Issue documents.");
		UtilityLog.info(Input.prodPath);

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String foldername = "Folder" + Utility.dynamicNameAppender();
		String prefixID = "P" + Utility.dynamicNameAppender();
		String suffixID = "S" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Technical Issue");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		sessionSearch.ViewInDocList();
		DocListPage doc = new DocListPage(driver);
		doc.documentSelection(3);
		doc.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.addDATFieldAtSecondRow(Input.productionText, Input.tiffPageCountNam, Input.tiffPageCountText);
		page.addDATFieldAtThirdRow(Input.docBasic, Input.docName, Input.documentID);
		page.fillingNativeSection();
		page.fillingTiffSectionTechIssueWithEnteringText(tagname, Input.tagNameTechnical);
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
		base.waitTime(2);
		page.extractFile();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		String name = page.getProduction().getText().trim();
		driver.waitForPageToBeReady();
		File DatFile = new File(home + "/Downloads/VOL0001/Load Files/" + name + "_DAT.dat");
		File TiffFile = new File(
				home + "/Downloads/VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tiff");
		page.OCR_Verification_In_Generated_Tiff_tess4j(TiffFile, Input.tagNameTechnical);
		if (DatFile.exists()) {
			base.passedStep("Dat file is displayed as expected");
		} else {
			base.failedStep("Dat file is not displayed as expected");
		}
		base.waitTime(1);
		int TiffPageCount = 1;
		String line;
		List<String> lines = new ArrayList<>();
		BufferedReader brReader = new BufferedReader(new InputStreamReader(new FileInputStream(DatFile), "UTF16"));
		while ((line = brReader.readLine()) != null) {
			lines.add(line);
		}
		System.out.println(lines.get(1));
		String[] arrOfStr = lines.get(1).split("þþ");
		String output = arrOfStr[1];
		if (TiffPageCount == Integer.parseInt(output)) {
			base.passedStep("Tiff page count is displayed as expected");
		} else {
			base.failedStep("verification failed");
		}
		brReader.close();
		loginPage.logout();

	}

	/**
	 * @author Brundha.T No:RPMXCON-63078
	 * @Description:Verify that production should be generated successfully with
	 *                     default enabled native placeholder under TIFF section
	 **/

	@Test(description = "RPMXCON-63078", enabled = true)
	public void verifyingTheGeneratedFileType() throws Exception {

		base = new BaseClass(driver);
		base.stepInfo("Test case Id:RPMXCON-63078- Production component");
		base.stepInfo(
				"Verify that production should be generated successfully with default enabled native placeholder under TIFF section");
		UtilityLog.info(Input.prodPath);

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "P" + Utility.dynamicNameAppender();
		String suffixID = "S" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");

		SessionSearch sessionSearch = new SessionSearch(driver);
		this.driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.addNewSearch();
		sessionSearch.SearchMetaData("DocFileType", "Spreadsheet");
		sessionSearch.addPureHit();

		sessionSearch.ViewInDocList();
		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(6);
		doclist.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);

		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProductionAndSave(productionname);
		page.fillingDATSection();
		page.verifyingTheDefaultSelectedOptionInNative();
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();
		String home = System.getProperty("user.home");
		driver.waitForPageToBeReady();
		File Native = new File(
				home + "/Downloads/VOL0001/Natives/0001/" + prefixID + beginningBates + suffixID + ".xls");
		if (Native.exists()) {
			base.passedStep("Native file are generated correctly : " + prefixID + beginningBates + suffixID + ".xls");
		} else {
			base.failedStep("verification failed");
		}
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Brundha.T No:RPMXCON-47986
	 * @Description:To Verify Editing an existing draft production by clicking on
	 *                 Mark Incomplete
	 **/

	@Test(description = "RPMXCON-47986", enabled = true)
	public void verifyDraftedProduction() throws Exception {

		base = new BaseClass(driver);
		base.stepInfo("Test case Id:RPMXCON-47986- Production component");
		base.stepInfo("To Verify Editing an existing draft production by clicking on Mark Incomplete");
		UtilityLog.info(Input.prodPath);

		String tagname = "Tag" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.getMarkCompleteLink().waitAndClick(10);

		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		page.verifyingDraftedProductionInComponentTab(productionname, tagname);

		loginPage.logout();
	}

	/**
	 * @author Brundha.T No:RPMXCON-47995
	 * @Description:To Verify Admin will be able to regenerate an existing
	 *                 production (for the same configuration) with the previous
	 *                 bates numbers,(Before commit Bates Number).
	 **/

	@Test(description = "RPMXCON-47995", enabled = true)
	public void verfyBatesNumberInProduction() throws Exception {

		base = new BaseClass(driver);
		base.stepInfo("Test case Id:RPMXCON-47995- Production component");
		base.stepInfo(
				"To Verify Admin will be able to regenerate an existing production (for the same configuration) with the previous bates numbers,(Before commit Bates Number).");
		UtilityLog.info(Input.prodPath);

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String prefixID = "P" + Utility.dynamicNameAppender();
		String suffixID = "S" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		page.gettext("Back").Click();
		String Batesno = page.getProd_BatesRange().getText();
		page.verifyProductionStatusInGenPage("Post-Generation QC checks Complete");
		page.navigateToProductionPage();
		page.getProductionNameLink(productionname).waitAndClick(5);
		page.gettext("Back").Click();
		page.getMarkInCompleteBtn().Click();
		page.getbtnReGenerateMarkComplete().waitAndClick(5);
		base.waitForElement(page.getbtnRegenerateContinue());
		page.getbtnRegenerateContinue().Click();
		page.getbtnContinueGeneration().isElementAvailable(80);
		if (page.getbtnContinueGeneration().isElementAvailable(1)) {
			page.getbtnContinueGeneration().Click();
		}
		String BatesNumber = page.getProd_BatesRange().getText();
		base = new BaseClass(driver);
		base.textCompareEquals(Batesno, BatesNumber, "Bates number are equal expecetd",
				"Bates number are not equal as expecetd");
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-48004
	 * @Description:To Verify Split Count in production is actually splitting the
	 *                 folder directory.
	 */
	@Test(description = "RPMXCON-48004", enabled = true, groups = { "regression" })
	public void verifyTheSubFoldersAfterGenration() throws Exception {
		UtilityLog.info(Input.prodPath);
		base = new BaseClass(driver);
		base.stepInfo("RPMXCON-48004 -Production Sprint 10");
		base.stepInfo("To Verify Split Count in production is actually splitting the folder directory.");

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		int purehit = sessionSearch.basicContentSearch(Input.testData1);
		int Count = purehit - 1;
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectPrivDocsInTiffSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		page.ProductionLocationSplitCount().Clear();
		page.ProductionLocationSplitCount().SendKeys(Integer.toString(Count));
		driver.scrollPageToTop();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.waitTime(2);
		page.extractFile();
		String home = System.getProperty("user.home");
		driver.waitForPageToBeReady();
		File ImageFile1 = new File(home + "\\Downloads\\VOL0001\\Images\\0001");
		if (ImageFile1.exists()) {
			base.passedStep("TIFF file is splited as expected");
		} else {
			base.failedStep("TIFF file is not splited as expected");
		}
		driver.waitForPageToBeReady();
		File ImageFile2 = new File(home + "\\Downloads\\VOL0001\\Images\\0002");
		if (ImageFile2.exists()) {
			base.passedStep("TIFF file is splited as expected");
		} else {
			base.failedStep("TIFF file is not splited as expected");
		}
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-48017
	 * @Description:To Verify In Native section User should be able to select one or
	 *                 more tags
	 */
	@Test(description = "RPMXCON-48017", enabled = true, groups = { "regression" })
	public void verifySelectedTagInNativeSection() throws Exception {
		UtilityLog.info(Input.prodPath);
		base = new BaseClass(driver);
		base.stepInfo("RPMXCON-48017 -Production component");
		base.stepInfo("To Verify In Native section User should be able to select one or more tags");

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String tagname1 = "Tag" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateTagwithClassification(tagname1, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.bulkTagExisting(tagname1);

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSectionWithTag(tagname, tagname1, false);
		page.getMarkCompleteLink().waitAndClick(10);
		base.VerifySuccessMessage("Mark Complete successful");
		page.getNativeTab().waitAndClick(10);
		String NativeTag = page.getNativeTags().getText();
		String Tags = tagname + "," + tagname1;
		base.textCompareEquals(Tags, NativeTag, "Native tag is displayed in production component as expecetd",
				"Tag is not displayed as expected");
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-48019
	 * @Description:User should be able to select one or more tags along with one or
	 *                   more file types.
	 */
	@Test(description = "RPMXCON-48019", enabled = true, groups = { "regression" })
	public void verifyFileTypeAndTagInComponentTab() throws Exception {
		UtilityLog.info(Input.prodPath);
		base = new BaseClass(driver);
		base.stepInfo("RPMXCON-48019-Production component");
		base.stepInfo("User should be able to select one or more tags along with one or more file types.");

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String tagname1 = "Tag" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateTagwithClassification(tagname1, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.bulkTagExisting(tagname1);

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSectionWithTag(tagname, tagname1, false);
		page.getMarkCompleteLink().waitAndClick(10);
		base.VerifySuccessMessage("Mark Complete successful");
		page.getNativeTab().waitAndClick(10);
		String NativeTag = page.getNativeTags().getText();
		String Tags = tagname + "," + tagname1;
		base.textCompareEquals(Tags, NativeTag, "Native tag is displayed in production component as expecetd",
				"Tag is not displayed as expected");
		page.verifyingNativeSectionFileType("Spreadsheets");
		page.verifyingNativeSectionFileType("Multimedia");
		loginPage.logout();
	}

	/**
	 * @author Brundha RPMXCON-48008
	 * @Description To verify production branding is longer, it is going off the
	 *              page instead of wrapping
	 */
	@Test(description = "RPMXCON-48008", enabled = true, groups = { "regression" })

	public void verifyProductionWithBrandingText() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48008 -Production component");
		base.stepInfo("To verify production branding is longer, it is going off the page instead of wrapping");

		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "P" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String BrandingPlaceHolder = "Longer text for Branding in tiff section";

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		int docno = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int firstFile = Integer.parseInt(beginningBates);
		int lastFile = docno + firstFile;
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionWithBranding(tagname, BrandingPlaceHolder, Input.tagNameTechnical);
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

		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		driver.waitForPageToBeReady();
		File TiffFile = new File(
				home + "/Downloads/VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tiff");
		if (TiffFile.exists()) {
			base.passedStep("Tiff  file is displayed as expected");
		} else {
			base.failedStep("Tiff file is not displayed as expected");
		}
		page.OCR_Verification_In_Generated_Tiff_tess4j(firstFile, lastFile, prefixID, suffixID, BrandingPlaceHolder);
		loginPage.logout();

	}

	/**
	 * @author Brundha.T No:RPMXCON-48005
	 * @Description:To Verify In Production produce documents are correct, And there
	 *                 Should not be any error on QC check for that produced docs.
	 **/

	@Test(description = "RPMXCON-48005", enabled = true)
	public void GenerationOfProductionUsingDocumentLevel() throws Exception {

		base = new BaseClass(driver);
		base.stepInfo("Test case Id:RPMXCON-48005- Production component");
		base.stepInfo(
				"To Verify In Production produce documents are correct, And there Should not be any error on QC check for that produced docs.");
		UtilityLog.info(Input.prodPath);

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "ATag" + Utility.dynamicNameAppender();
		String suffixID = "S" + Utility.dynamicNameAppender();
		String prefixID = "P" + Utility.dynamicNameAppender();
		

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.CreateTagwithClassification(tagname, "Select Tag Classification");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingPageWithDocumentLevelAndSubBates(beginningBates,prefixID,suffixID);
		page.selectingTaginSortingPage(tagname);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		String ActualText = page.getStatusSuccessTxt().getText();
		String ExpectedText = "Success";
		base.textCompareEquals(ActualText, ExpectedText, "Production Generated successfully without any error",
				"Error in Production generation");
		loginPage.logout();
	}
	/**
	 * @author Brundha RPMXCON-63183
	 * @Description Verify if no text file exists in the SL Workspace and "OCR
	 *              non-redacted docs... " option is selected in Production-text
	 *              component then it should export OCR text for non-redacted
	 *              document
	 */
	@Test(description = "RPMXCON-63183", enabled = true, groups = { "regression" })

	public void verifyingOCRTextInAllGeneratedDocument() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-63183 -Production component");
		base.stepInfo(
				"Verify if no text file exists in the SL Workspace and \"OCR non-redacted docs... \" option is selected in Production-text component then it should export OCR text for non-redacted document");

		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "P" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.CreateTagwithClassification(tagname,Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		int Purehit=sessionSearch.basicContentSearch(Input.telecaSearchString);
		sessionSearch.bulkRelease(Input.securityGroup);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int firstFile = Integer.parseInt(beginningBates);
		int LastFile=Purehit+firstFile;
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.addDATFieldAtSecondRow(Input.productionText, Input.tiffPageCountNam, Input.tiffPageCountText);
		page.fillingNativeSection();
		page.fillingTIFFSectionPrivDocs(tagname,Input.searchString4);
		page.fillingTextSection();
		page.textComponentVerification();
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
		File TiffFile = new File(home + "/Downloads/VOL0001/Load Files/" + productionname + "_TIFF.OPT");
		File DatFile = new File(home + "/Downloads/VOL0001/Load Files/" + productionname + "_DAT.dat");
		File Textfile = new File(home + "/Downloads/VOL0001/Text/0001");

		if (Textfile.exists()) {
			base.passedStep("Text file is displayed as expected");
		} else {base.failedStep("Text file is not displayed as expected");}

		if (TiffFile.exists()) {
			base.passedStep("Tiff file is generated as expected");
		} else {base.failedStep("Tiff file is not generated as expected");}

		if (DatFile.exists()) {
			base.passedStep("Dat file is generated as expected");
		} else {base.failedStep("Dat file is not generated as expected");}
		
		page.OCR_Verification_In_Generated_Tiff_tess4j(firstFile, LastFile, prefixID, suffixID,Input.searchString4);
		loginPage.logout();

	}
	
	/**
	 * @author Brundha.T No:RPMXCON-47999
	 * @Description:Verify the Matched Documents count when Include Family Members
	 *                     is selected
	 **/

	@Test(description = "RPMXCON-47999", enabled = true)
	public void verifyingParentndChildDocumentInDocumentSelectionTab() throws Exception {

		base = new BaseClass(driver);
		base.stepInfo("Test case Id:RPMXCON-47999- Production Component");
		base.stepInfo("Verify the Matched Documents count when Include Family Members is selected");
		UtilityLog.info(Input.prodPath);

		String tagname = "Tag" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, "Select Tag Classification");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.telecaSearchString);
		sessionSearch.ViewInDocList();

		DocListPage doc = new DocListPage(driver);
		doc.getSelectDropDown().waitAndClick(10);
		driver.waitForPageToBeReady();
		String ParentDocId = doc.getParentDocumentDocId().getText();
		System.out.println(ParentDocId);
		doc.bulkRelease(Input.securityGroup);

		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		sessionSearch = new SessionSearch(driver);
		int purehit = sessionSearch.basicContentSearch(ParentDocId);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String productionname1 = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname1);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		driver.scrollingToBottomofAPage();
		page.getIncludeFamilies().waitAndClick(10);
		driver.scrollPageToTop();
		page.getMarkCompleteLink().waitAndClick(10);
		base.waitTime(2);
		String DocumentCount = page.getDocumentSelectionLink().getText();
		System.out.println(":"+DocumentCount);
		int DocCount = Integer.valueOf(DocumentCount);
		if (DocCount >=purehit) {
			base.passedStep("Family member Document  is displayed as expected");
		}else {
			base.failedStep("Family member is not displayed as expecetd");
		}

	}
	
	/**
	 * @author Brundha RPMXCON-48275
	 * @Description To verify MP3 productions with DAT and Text
	 */
	@Test(description = "RPMXCON-48275", enabled = true, groups = { "regression" })

	public void verifyingGenerationOfProduction() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48275 -Production component");
		base.stepInfo("To verify MP3 productions with DAT and Text");
				

		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String productionname = "P" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearchForTwoItems(Input.telecaSearchString,Input.testData1);
		sessionSearch.addNewSearch();
		sessionSearch.SearchMetaData(Input.ingDocFileType,"MP3");
		sessionSearch.addPureHit();
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTextSection();
		base.clickButton(page.getAdvancedProductionComponent());
		page.getMP3CheckBox().waitAndClick(10);
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
