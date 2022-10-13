package testScriptsRegressionSprint23;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Production_Regression23 {
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
