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
import pageFactory.BatchPrintPage;
import pageFactory.DataSets;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Production_Phase2_Regression2 {

	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	Utility utility;
	String foldername;
	String tagname;
	String productionname;
	ProductionPage page;
	SessionSearch sessionSearch;
	TagsAndFoldersPage tagsAndFolderPage;
	SoftAssert softAssertion;
	String prefixID = "A" + Utility.dynamicNameAppender();
	String suffixID = "P" + Utility.dynamicNameAppender();

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
		base = new BaseClass(driver);
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		page = new ProductionPage(driver);
		sessionSearch = new SessionSearch(driver);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
	}

	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		base = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility base = new Utility(driver);
			base.screenShot(result);
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

	/**
	 * @author sowndarya Testcase No:RPMXCON-49214
	 * @Description:To verify that upon completion of uncommit, notification should
	 *                 be displayed on right top corner
	 **/
	//@Test(description = "RPMXCON-49214", enabled = true, groups = { "regression" })
	public void verifyCommitAndUncommit() throws Exception {

		UtilityLog.info(Input.prodPath);
		tagname = "Tag" + Utility.dynamicNameAppender();
		productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);

		base.stepInfo("Test Cases Id : RPMXCON-49214");
		base.stepInfo(
				"To verify that upon completion of uncommit, notification should be displayed on right top corner");

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();
		DocListPage doc = new DocListPage(driver);
		doc.documentSelection(2);
		doc.bulkTagExisting(tagname);

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

		base.stepInfo("Committing the production");
		page.fillingGeneratePageWithContinueGenerationPopup();
		for (int i = 0; i < 2; i++) {
			driver.Navigate().refresh();
			base.waitTime(2);
		}

		base.stepInfo("Uncommitting the committed production");
		base.waitForElement(page.getConfirmProductionUnCommit());
		page.getConfirmProductionUnCommit().waitAndClick(5);
		for (int i = 0; i < 2; i++) {
			driver.Navigate().refresh();
			base.waitTime(2);
		}

		// verify nofication, backgroud task
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		base.notifyBullIconVerification();
		base.isBackGroudTaskCompleted();
		base.clickFirstBackRoundTask();

		String actual = base.getFirstBackRoundTask().getText();
		String expected = "Uncommit successfully completed for the production " + productionname;
		if (actual.equals(expected)) {
			base.passedStep("Uncommit is successful");
		} else {
			base.failedMessage("Uncommit is not successful");
		}

		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		base.waitForElement(page.getConfirmProductionCommit());
		page.getConfirmProductionCommit().isDisplayed();
		base.passedStep("Link name is commit");

		page.navigateToProductionPage();
		page.verifyProductionStatusInHomePage("Post-Gen QC Checks Complete", productionname);

		base.passedStep("Verified - completion of uncommit, notification should be displayed on right top corner");
		loginPage.logout();
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
				home + "/Downloads/VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tiff");
		System.out.println(Native);
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
		page.fillingGeneratePageWithContinueGenerationPopupHigerWaitTime();		
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
		String Tags = tagname + "," + tagname1;
		Thread.sleep(2000);
		String NativeTag = page.getNativeTags().getText();
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
		page.fillingNumberingPageWithDocumentLevelAndSubBates(beginningBates, prefixID, suffixID);
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
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		int Purehit = sessionSearch.basicContentSearch(Input.telecaSearchString);
		sessionSearch.bulkRelease(Input.securityGroup);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int firstFile = Integer.parseInt(beginningBates);
		int LastFile = Purehit + firstFile;
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.addDATFieldAtSecondRow(Input.productionText, Input.tiffPageCountNam, Input.tiffPageCountText);
		page.fillingNativeSection();
		page.fillingTIFFSectionPrivDocs(tagname, Input.searchString4);
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
		} else {
			base.failedStep("Text file is not displayed as expected");
		}

		if (TiffFile.exists()) {
			base.passedStep("Tiff file is generated as expected");
		} else {
			base.failedStep("Tiff file is not generated as expected");
		}

		if (DatFile.exists()) {
			base.passedStep("Dat file is generated as expected");
		} else {
			base.failedStep("Dat file is not generated as expected");
		}

		page.OCR_Verification_In_Generated_Tiff_tess4j(firstFile, LastFile, prefixID, suffixID, Input.searchString4);
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
		doc.selectColumnMetaDataSelection();
		String ParentDocId = "ID00001007";
		
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
		System.out.println(":" + DocumentCount);
		int DocCount = Integer.valueOf(DocumentCount);
		if (DocCount >= purehit) {
			base.passedStep("Family member Document  is displayed as expected");
		} else {
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
		sessionSearch.basicContentSearchForTwoItems(Input.telecaSearchString, Input.testData1);
		sessionSearch.addNewSearch();
		sessionSearch.SearchMetaData(Input.ingDocFileType, "MP3");
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

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56001
	 * @Description:Verify the Download option on Production QC page
	 **/

	@Test(description = "RPMXCON-56001", enabled = true, groups = { "regression" })
	public void verifyDownloadOption() throws Exception {

		base.stepInfo("Test case Id:RPMXCON-56001- Production Sprint 16");
		base.stepInfo("Verify the Download option on Production QC page");
		UtilityLog.info(Input.prodPath);
		foldername = "Prod_Folder" + Utility.dynamicNameAppender();
		tagname = "Prod_Tag" + Utility.dynamicNameAppender();

		base.stepInfo("Create tags and folders");

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		base.stepInfo("perform basic search and bulk folder");
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		base.stepInfo("Create production using required inputs");
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
		base.passedStep("Verified the Download option on Production QC page");
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48192
	 * @Description:To Verify Productions Generation for MP3 files using document
	 *                 level numbering.
	 **/

	@Test(description = "RPMXCON-48192", enabled = true, groups = { "regression" })
	public void verifyMP3WithDocumentLevelNumbering() throws Exception {

		base.stepInfo("Test case Id:RPMXCON-48192- Production Sprint 16");
		base.stepInfo("To Verify Productions Generation for MP3 files using document level numbering.");
		UtilityLog.info(Input.prodPath);
		foldername = "Prod_Folder" + Utility.dynamicNameAppender();
		tagname = "Prod_Tag" + Utility.dynamicNameAppender();

		base.stepInfo("Create tags and folders");

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		base.stepInfo("perform basic search and bulk folder");
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch("DocFileType", "mp3");
		sessionSearch.ViewInDocList();
		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(2);
		doclist.bulkTagExisting(tagname);

		base.stepInfo("Create production using required inputs");
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		String prodName = page.addANewProduction(productionname);
		System.out.println("created a new production - " + prodName);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingPageWithDocumentLevelAndSubBates(beginningBates, prefixID, suffixID);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.passedStep("Verified Productions Generation for MP3 files using document level numbering");
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48268
	 * @Description:To Verify In Productions, Ending Bates generated in a production
	 *                 Should have correct Value..
	 **/

	@Test(description = "RPMXCON-48268", enabled = true, groups = { "regression" })
	public void verifyEndingBates() throws Exception {

		base.stepInfo("Test case Id:RPMXCON-48268- Production Sprint 16");
		base.stepInfo("To Verify In Productions, Ending Bates generated in a production Should have correct Value.");
		UtilityLog.info(Input.prodPath);
		foldername = "Prod_Folder" + Utility.dynamicNameAppender();
		tagname = "Prod_Tag" + Utility.dynamicNameAppender();

		base.stepInfo("Create tags and folders");
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		base.stepInfo("perform basic search and bulk folder");
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		base.stepInfo("Create production using required inputs");
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
		base.passedStep("Verified In Productions, Ending Bates generated in a production Should have correct Value");
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48784
	 * @Description:To verify that document should produced with 'Tech Issues Docs'
	 *                 placeholdering by selecting only one Tag
	 **/

	@Test(description = "RPMXCON-48784", enabled = true, groups = { "regression" })
	public void verifyTechIssue() throws Exception {

		base.stepInfo("Test case Id:RPMXCON-48784- Production Sprint 16");
		base.stepInfo(
				"To verify that document should produced with 'Tech Issues Docs' placeholdering by selecting only one Tag");
		UtilityLog.info(Input.prodPath);
		foldername = "Prod_Folder" + Utility.dynamicNameAppender();
		tagname = "Prod_Tag" + Utility.dynamicNameAppender();

		base.stepInfo("Create tags and folders");
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.technicalIssue);

		base.stepInfo("perform basic search and bulk folder");
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		base.stepInfo("Create production using required inputs");
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
		base.passedStep(
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

		base.stepInfo("Test case Id:RPMXCON-48164- Production Sprint 16");
		base.stepInfo(
				"To Verify for Error Message on Mark Complete for MP3 Files Selection ,without enabling LST generation for it.");
		UtilityLog.info(Input.prodPath);
		foldername = "Prod_Folder" + Utility.dynamicNameAppender();
		tagname = "Prod_Tag" + Utility.dynamicNameAppender();
		String expectedMsg = "For the MP3 Files component, you must either enable LST load file option or specify the MP3 file path in the DAT, in order to generate a load file for the generated MP3s files.";

		base.stepInfo("Create production using required inputs");
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
		base.stepInfo("LST Toggle is turned OFF");
		driver.scrollPageToTop();
		base.waitForElement(page.getMarkCompleteLink());
		page.getMarkCompleteLink().waitAndClick(10);
		base.VerifyWarningMessage(expectedMsg);

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

		base.stepInfo("Test case Id:RPMXCON-48181- Production Sprint 16");
		base.stepInfo(
				"To Verify Native Section with various options Produce Native Files selection/Produce Load/Select Tags/Advance Show Hide/and Toggles in Advance");
		UtilityLog.info(Input.prodPath);

		base.stepInfo("Create production using required inputs");
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
		base.passedStep(
				"Verified Native Section with various options Produce Native Files selection/Produce Load/Select Tags/Advance Show Hide/and Toggles in Advance");

	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48182
	 * @Description:To Verify TIFF Section with various options
	 **/

	@Test(description = "RPMXCON-48182", enabled = true, groups = { "regression" })
	public void verifyTIFFSectionOptions() throws Exception {

		base.stepInfo("Test case Id:RPMXCON-48182- Production Sprint 16");
		base.stepInfo("To Verify TIFF Section with various options");
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Create production using required inputs");
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
		base.passedStep("Verified TIFF Section with various options");
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

		base.stepInfo("Test case Id:RPMXCON-48184- Production Sprint 16");
		base.stepInfo(
				"To Verify Production Location For Production Root Path/Production Directory/Load File Path/Volume Included/Production Component Folders.");
		UtilityLog.info(Input.prodPath);
		foldername = "Prod_Folder" + Utility.dynamicNameAppender();
		tagname = "Prod_Tag" + Utility.dynamicNameAppender();

		base.stepInfo("Create tags and folders");
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		base.stepInfo("perform basic search and bulk folder");
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		base.stepInfo("Create production using required inputs");
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
		base.passedStep("Verified Production Location component");
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

		base.stepInfo("Test case Id:RPMXCON-48185- Production Sprint 16");
		base.stepInfo(
				"To Verify Summary and Preview for (Total Documents/Total Pages/Number of Custodians/Number of Natives/Exception Files/Missing Text Files/First and Last Doc ID's/Privileged Documents/Documents identified by Production Guard/Redacted Documents/OCR/TIFF");
		UtilityLog.info(Input.prodPath);
		foldername = "Prod_Folder" + Utility.dynamicNameAppender();
		tagname = "Prod_Tag" + Utility.dynamicNameAppender();

		base.stepInfo("Create tags and folders");
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		base.stepInfo("perform basic search and bulk folder");
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		base.stepInfo("Create production using required inputs");
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

		base.passedStep(" Verified Summary and Preview  Component");

	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48165
	 * @Description:To Verify Filler Audio is available only on enabling Burn
	 *                 Redaction for MP3 Files under Advanced Production Components
	 **/

	@Test(description = "RPMXCON-48165", enabled = true, groups = { "regression" })
	public void verifyMP3Component() throws Exception {

		base.stepInfo("Test case Id:RPMXCON-48165- Production Sprint 16");
		base.stepInfo(
				"To Verify Filler Audio is available only on enabling Burn Redaction for MP3 Files under Advanced Production Components");
		UtilityLog.info(Input.prodPath);
		foldername = "Prod_Folder" + Utility.dynamicNameAppender();
		tagname = "Prod_Tag" + Utility.dynamicNameAppender();

		base.stepInfo("Create production using required inputs");
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		String prodName = page.addANewProduction(productionname);
		System.out.println("created a new production - " + prodName);
		page.fillingMP3();
		base.stepInfo(
				"Redaction style drop down should be displayed  User can select one of the three filler Audio files for redaction in MP3 Files Advanced Production Components  ");
		System.out.println(
				"Redaction Style Options available are :" + page.getSelect_RedactionStyle_Dropdown().getText());
		page.getSelect_Beep_RedactionStyle_Dropdown().isDisplayed();
		page.getSelect_Bomb_RedactionStyle_Dropdown().isDisplayed();
		page.getSelect_Can_RedactionStyle_Dropdown().isDisplayed();

		base.passedStep(
				"Verified Filler Audio is available only on enabling Burn Redaction for MP3 Files under Advanced Production Components");
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48276
	 * @Description:To verify MP3 productions with DAT and PDF.
	 **/

	@Test(description = "RPMXCON-48276", enabled = true, groups = { "regression" })
	public void verifyMP3WithDAT() throws Exception {

		base.stepInfo("Test case Id:RPMXCON-48276- Production Sprint 16");
		base.stepInfo("To verify MP3 productions with DAT and PDF.");
		UtilityLog.info(Input.prodPath);
		foldername = "Prod_Folder" + Utility.dynamicNameAppender();
		tagname = "Prod_Tag" + Utility.dynamicNameAppender();

		base.stepInfo("Create tags and folders");
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		base.stepInfo("Ingestion should have Documents with following file Variants  Native /Text /PDF & MP3");
		DataSets dataSet = new DataSets(driver);
		dataSet.navigateToDataSetsPage();
		dataSet.selectDataSetWithName(Input.ingestionAutomationAllSource);

		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(6);
		doclist.bulkTagExisting(tagname);

		base.stepInfo("Create production using required inputs");
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
		base.passedStep("verified MP3 productions with DAT and PDF.");
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48274
	 * @Description:To verify MP3 productions with DAT and Native.
	 **/

	@Test(description = "RPMXCON-48274", enabled = true, groups = { "regression" })
	public void verifyMP3WithDATandNative() throws Exception {

		base.stepInfo("Test case Id:RPMXCON-48274- Production Sprint 16");
		base.stepInfo("To verify MP3 productions with DAT and Native.");
		UtilityLog.info(Input.prodPath);
		foldername = "Prod_Folder" + Utility.dynamicNameAppender();
		tagname = "Prod_Tag" + Utility.dynamicNameAppender();

		base.stepInfo("Create tags and folders");
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		base.stepInfo("Ingestion should have Documents with following file Variants  Native /Text /PDF & MP3");
		DataSets dataSet = new DataSets(driver);
		dataSet.navigateToDataSetsPage();
		dataSet.selectDataSetWithName(Input.ingestionAutomationAllSource);

		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(6);
		doclist.bulkTagExisting(tagname);

		base.stepInfo("Create production using required inputs");
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
		page.fillingGeneratePageWithContinueGenerationPopupHigerWaitTime();
		base.passedStep("verified MP3 productions with DAT and Native");
	}
}