package sightline.productions;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
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
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.ProjectFieldsPage;
import pageFactory.RedactionPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Production_Test_Regression_03 {

	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	ProductionPage productionPage;
	TagsAndFoldersPage tagsAndFolderPage;
	SessionSearch sessionSearch;
	DocListPage docPage;
	DocViewPage docViewPage;
	ProjectFieldsPage projectField;
	SoftAssert softAssertion;
	String prefixID = "A_" + Utility.dynamicNameAppender();
	String suffixID = "_P" + Utility.dynamicNameAppender();
	String foldername;
	String tagname;
	String productionname;
	String TempName;

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());
		base = new BaseClass(driver);
		Input input = new Input();
		input.loadEnvConfig();
		base = new BaseClass(driver);
		driver = new Driver();

		// Login as a PA
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
	}
	
	/**
	 * @author Aathith Test case id-RPMXCON-49334
	 * @Description To verify that Production should generate successfully with ICE
	 *              data
	 * 
	 */
	@Test(description="RPMXCON-49334",enabled = true, groups = { "regression" })
	public void verifyIceDataGenerateSuccesfully() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49334 -Production Component");
		base.stepInfo("To verify that Production should generate successfully with ICE data");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		int doccount = 3;

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		DataSets dataset = new DataSets(driver);
		base.stepInfo("Navigating to dataset page");
		dataset.navigateToDataSetsPage();
		base.stepInfo("Selecting uploadedset and navigating to doclist page");
		dataset.SelectingUploadedDataSet();
		DocListPage doc = new DocListPage(driver);
		driver.waitForPageToBeReady();

		doc.documentSelection(doccount);
		doc.bulkTagExistingFromDoclist(tagname);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionwithNativelyPlaceholder(tagname);
		page.fillingTextSection();
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
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		driver.waitForPageToBeReady();

		page.extractFile();
		driver.waitForPageToBeReady();

		File Native = new File(home + "/Downloads/VOL0001/Natives");

		if (Native.exists()) {
			base.passedStep("Native folder exist for generated IcE data");
		} else {
			base.failedStep("filetype is not displayed as expected");
		}

		base.passedStep("verified that Production should generate successfully with ICE data");
		loginPage.logout();

	}

	/**
	 * @author Aathith Test case id-RPMXCON-49373
	 * @Description To verify that all produced Natives files should be provided by
	 *              file types for ICE processed data.
	 * 
	 */
	@Test(description="RPMXCON-49373",enabled = true, groups = { "regression" })
	public void verifyIceDatafilesGenerateSuccesfully() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49373 -Production Component");
		base.stepInfo(
				"To verify that all produced Natives files should be provided by file types for ICE processed data.");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		int doccount = 3;

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		DataSets dataset = new DataSets(driver);
		base.stepInfo("Navigating to dataset page");
		dataset.navigateToDataSetsPage();
		base.stepInfo("Selecting uploadedset and navigating to doclist page");
		dataset.SelectingUploadedDataSet();
		DocListPage doc = new DocListPage(driver);
		driver.waitForPageToBeReady();

		doc.documentSelection(doccount);
		doc.bulkTagExistingFromDoclist(tagname);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int number = Integer.parseInt(beginningBates);
		int lastfile = number + doccount;
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionwithNativelyPlaceholder(tagname);
		page.fillingTextSection();
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
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");

		page.extractFile();
		driver.waitForPageToBeReady();

		for (int i = number; i < lastfile; i++) {
			File Native = new File(home + "/Downloads/VOL0001/Natives/0001/" + prefixID + i + suffixID + ".docx");
			File Textfile = new File(home + "/Downloads/VOL0001/Text/0001/" + prefixID + i + suffixID + ".txt");
			File TiffFile = new File(home + "/Downloads/" + "VOL0001/Images/0001/" + prefixID + i + suffixID + ".tiff");
			if (Native.exists()) {
				base.passedStep("Native file are generated correctly");
				System.out.println("passeed");
			} else {
				base.failedStep("verification failed");
				System.out.println("failed");
			}
			if (Textfile.exists()) {
				base.passedStep("Text file are generated correctly");
				System.out.println("passeed");
			} else {
				base.failedStep("verification failed");
				System.out.println("failed");
			}
			if (TiffFile.exists()) {
				base.passedStep("Tiff file are generated coreectly");
				System.out.println("passeed");
			} else {
				base.failedStep("verification failed");
				System.out.println("failed");
			}
		}
		base.passedStep(
				"verifid that all produced Natives files should be provided by file types for ICE processed data.");
		loginPage.logout();

	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49337
	 * @Description: To verify that document should produced with 'Tech Issues Docs'
	 *               placeholdering by selecting more than one Tag
	 */
	@Test(description="RPMXCON-49337",enabled = true, groups = { "regression" })
	public void verifyTeccIssueDocPlaceholdering() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case id : RPMXCON-49337 ");
		base.stepInfo(
				"To verify that document should produced with 'Tech Issues Docs' placeholdering by selecting more than one Tag");
		
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String tagname1 = "tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.technicalIssue);
		tagsAndFolderPage.createNewTagwithClassification(tagname1, Input.technicalIssue);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		int docno = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.bulkTagExisting(tagname1);

		// Verify
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int firstFile = Integer.parseInt(beginningBates);
		int lastFile = docno + firstFile;
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTiffSectionTechIssueWithEnteringText(tagname, tagname1, Input.technicalIssue);
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
		page.OCR_Verification_In_Generated_Tiff_tess4j(firstFile, lastFile, prefixID, suffixID, Input.technicalIssue);

		base.passedStep(
				"Verified that document should produced with 'Tech Issues Docs' placeholdering by selecting more than one Tag");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Test case id-RPMXCON-49867
	 * @Description Verify that Production should be generated successfully if PDF
	 *              documents are ICE processed with Mapped set
	 * @Hint : test case run on the project Regression_AllDataset_Consilio1 in UAT
	 *       environment
	 */
	@Test(description="RPMXCON-49867",enabled = true, groups = { "regression" })
	public void verifyPdfIceMappedSetProdGenSuccesfully() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49867 -Production Component");
		base.stepInfo(
				"Verify that Production should be generated successfully if PDF documents are  ICE processed with Mapped set");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname,Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		DataSets dataset = new DataSets(driver);
		base.stepInfo("Navigating to dataset page");
		dataset.navigateToDataSetsPage();
		base.stepInfo("Selecting uploadedset and navigating to doclist page");
		dataset.selectDataSetWithName(Input.pdfDataSet);
		DocListPage doc = new DocListPage(driver);
		driver.waitForPageToBeReady();

		doc.selectAllDocs();
		doc.bulkTagExistingFromDoclist(tagname);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionwithNativelyPlaceholder(tagname);
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

		base.passedStep(
				"Verified that Production should be generated successfully if PDF documents are  ICE processed with Mapped set");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Aathith Test case id-RPMXCON-49046
	 * @Description Verify in Productions, when there is no document with multiple
	 *              tags it should provide the message that there is 0 "Documents
	 *              with Multiple Branding Tags"
	 * 
	 */
	@Test(description="RPMXCON-49046",enabled = true, groups = { "regression" })
	public void verifyMultipleDocumentCountisZero() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49046 -Production Component");
		base.stepInfo(
				"Verify in Productions, when there is no document with multiple tags it should provide the message that there is 0 \"Documents with Multiple Branding Tags\"");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String tagname1 = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String text = Input.telecaSearchString;
		String value = "0";

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.createNewTagwithClassification(tagname1, Input.technicalIssue);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		int docno = sessionSearch.basicContentSearch(text);
		base.stepInfo("total number of document : " + docno);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.ViewInDocListWithOutPureHit();

		DocListPage doc = new DocListPage(driver);
		driver.waitForPageToBeReady();

		doc.documentSelectionIncludeChildDoc(5);
		doc.bulkTagExistingFromDoclist(tagname);

		doc.documentSelectionIncludeChildDoc(5);
		doc.documentSelectionIncludeChildDoc(10);
		driver.scrollPageToTop();
		doc.bulkTagExistingFromDoclist(tagname1);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.specifyBrandingInTiffSection(tagname, tagname1, text);

		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickOnGenerateButton();
		page.verifyProductionStatusInGenPage("Pre-Generation Checks In Progress");

		page.verifyProductionStatusInGenPage("Reserving Bates Range Complete");
		page.getbtnContinueGeneration().isElementAvailable(30);
		page.getDocumentWithMultipleBrandingTagsOnGenerationPage().isElementAvailable(10);
		String count = page.getDocumentWithMultipleBrandingTagsOnGenerationPage().getText().trim();

		if (value.equals(count)) {
			base.passedStep("Multiple branding tags, here it should displayed 0");
			System.out.println("pass");
		} else {
			base.failedStep("verificatioin failed");
			System.out.println("failed");
		}

		base.passedStep(
				"Verified in Productions, when there is no document with multiple tags it should provide the message that there is 0 \"Documents with Multiple Branding Tags\"");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48034
	 * @Description: To Verify "Enable Placeholders by Selecting File Types" for
	 *               (.mdb/.mdf) under TIFF /PDF Should works for Production.
	 */
	@Test(description="RPMXCON-48034",enabled = true, groups = { "regression" })
	public void verifyPlaceholderForMDB() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case id : RPMXCON-48034 ");
		base.stepInfo(
				"To Verify \"Enable Placeholders by Selecting File Types\" for  (.mdb/.mdf) under TIFF /PDF Should works for Production.");

		// foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String testing = Input.comment;

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		// tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.technicalIssue);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		int docno = sessionSearch.MetaDataSearchInBasicSearch(Input.sourceDocIdSearch, Input.sourceDocIdDB992);
		// sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		// Verify
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int firstFile = Integer.parseInt(beginningBates);
		int lastFile = docno + firstFile;
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFWithNativelyProducedDocs(Input.dbFile, testing);
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
		page.OCR_Verification_In_Generated_Tiff_tess4j(firstFile, lastFile, prefixID, suffixID, testing);

		base.passedStep(
				"Verified \"Enable Placeholders by Selecting File Types\" for  (.mdb/.mdf) under TIFF /PDF Should works for Production.");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		// tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security
		// Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * Author : Vijaya.Rani date: 3/03/22 NA Modified date: NA Modified by:NA
	 * Description :To Verify if a document has both landscape and portrait pages,
	 * the rotation selected is applied only to the pages that are in landscape
	 * layout. 'RPMXCON-48031'
	 * 
	 */
	@Test(description="RPMXCON-48031",enabled = true, groups = { "regression" })
	public void verifyDocsBothLandScapeRotationLayout() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48031 -Production Sprint 13");
		base.stepInfo(
				"To Verify if a document has both landscape and portrait pages, the rotation selected is applied only to the pages that are in landscape layout.");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		BaseClass base = new BaseClass(driver);
		base.selectproject(Input.additionalDataProject);
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");
		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.SearchMetaData("SourceDocID", "35ID00000169");
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);
		// Verify archive status on Grid view
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionwithNativelyPlaceholder(tagname);
		driver.scrollPageToTop();
		page.getRotationDropDown().ScrollTo();
		page.getRotationDropDown().selectFromDropdown().selectByVisibleText("Rotate 90 degrees clock-wise");
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
		page.extractFile();
		System.out.println("Unzipped the downloaded files");
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		String firstFile =  prefixID + beginningBates + suffixID;
		File file = new File(home + "/Downloads/VOL0001/Images/0001/" + firstFile + ".tiff");

		BufferedImage bimg = ImageIO.read(file);
		int width = bimg.getWidth();
		int height = bimg.getHeight();
		if (width < height) {
			base.passedStep("Rotation is applied only to the pages is landscape successfully");
		} else {
			base.failedStep("Rotation is not applied only to the pages is not landscape");
		}

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		base.stepInfo("deleting created tags and folders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);

		loginPage.logout();

	}

	/**
	 * @author Aathith Test case id-RPMXCON-49374
	 * @Description To verify that all produced Natives files should be provided by
	 *              file types for NUIX processed data.
	 * 
	 */
	@Test(description="RPMXCON-49374",enabled = true, groups = { "regression" })
	public void verifyNuixDatafilesGenerateSuccesfully() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49374 -Production Component");
		base.stepInfo(
				"To verify that all produced Natives files should be provided by file types for NUIX processed data.");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		int doccount = 3;

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		DataSets dataset = new DataSets(driver);
		base.stepInfo("Navigating to dataset page");
		dataset.navigateToDataSetsPage();
		base.stepInfo("Selecting uploadedset and navigating to doclist page");
		dataset.SelectingUploadedDataSet();
		DocListPage doc = new DocListPage(driver);
		driver.waitForPageToBeReady();

		doc.documentSelection(doccount);
		doc.bulkTagExistingFromDoclist(tagname);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int firstFile = Integer.parseInt(beginningBates);
		int lastfile = firstFile + doccount;
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionwithNativelyPlaceholder(tagname);
		page.fillingTextSection();
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
		page.isImageFileExist(firstFile, lastfile, prefixID, suffixID);
		page.isTextFileExist(firstFile, lastfile, prefixID, suffixID);
		page.isNativeDocxFileExist(firstFile, lastfile, prefixID, suffixID);

		base.passedStep(
				"verified that all produced Natives files should be provided by file types for NUIX processed data.");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Test case id-RPMXCON-48255
	 * @Description To Verify Placeholder for Privilege Doc at Priv Guard section on
	 *              Mark complete.
	 * 
	 */
	@Test(description="RPMXCON-48255",enabled = true, groups = { "regression" })
	public void verifyPlaceholderPrivDocAtPrivGuard() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48255 -Production Component");
		base.stepInfo("To Verify Placeholder for Privilege Doc at Priv Guard section on Mark complete.");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		int docno = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = "1";
		int firstFile = Integer.parseInt(beginningBates);
		int lastfile = firstFile + docno;
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTiffSectionDisablePrivilegedDocs();
		page.getDoNotProduceFullContentTiff().ScrollTo();
		page.getDoNotProduceFullContentTiff().waitAndClick(10);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.getMarkCompleteLink().waitAndClick(10);
		driver.waitForPageToBeReady();

		String text = Integer.toString(docno);
		page.verifyText(page.getDocumentSelectionLink(), text);

		driver.waitForPageToBeReady();
		base.waitForElement(page.getNextButton());
		page.getNextButton().Enabled();
		page.getNextButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		page.fillingPrivGuardPageWithPrivPlaceHolder(tagname);
		page.clickElementNthtime(page.getBackButton(), 4);
		page.getTIFFTab().waitAndClick(10);
		page.toggleOnCheck(page.getTIFF_EnableforPrivilegedDocs());
		page.visibleCheck(Input.searchString2);
		driver.scrollPageToTop();
		page.clickElementNthtime(page.getNextButton(), 4);
		driver.waitForPageToBeReady();

		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.verifyText(page.getPrivDocCountInSummaryPage(), text);
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageAndVerfyingBatesRangeValue(beginningBates);

		page.extractFile();
		page.isImageFileExist(firstFile, lastfile, prefixID, suffixID);

		base.passedStep("Verified Placeholder for Privilege Doc at Priv Guard section on Mark complete.");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Test case id-RPMXCON-60902
	 * @Description Verify Production should be generated successfully for the
	 *              documents with annotation
	 * 
	 */
	@Test(description="RPMXCON-60902",enabled = true, groups = { "regression" })
	public void verifyProdGenDocumentWithAnnotation() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-60902 -Production Component");
		base.stepInfo("Verify Production should be generated successfully for the documents with annotation ");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		BaseClass base = new BaseClass(driver);
		base.selectproject("AutomationAdditionalDataProject");

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		DataSets dataset = new DataSets(driver);
		base.stepInfo("Navigating to dataset page");
		dataset.navigateToDataSetsPage();
		base.stepInfo("Selecting uploadedset and navigating to doclist page");
		dataset.selectDataSetWithName("PDF Annotations");
		DocListPage doc = new DocListPage(driver);
		driver.waitForPageToBeReady();

		doc.selectAllDocs();
		doc.docListToBulkRelease(Input.securityGroup);
		doc.bulkTagExistingFromDoclist(tagname);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSectionwithNativelyPlaceholder(tagname);
		page.fillingTextSection();
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
		page.pdf_Verification_In_Generated_Pdf(prefixID, suffixID, beginningBates);

		base.passedStep("Verified Production should be generated successfully for the documents with annotation ");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Aathith Test case id-RPMXCON-56146
	 * @Description Verify that if problem docs is assigned as Priv doc then
	 *              Production should generated with Priv Placeholder for the same
	 *              document
	 * 
	 */
   @Test(description="RPMXCON-56146",enabled = true, groups = { "regression" })
	public void verifyPrivPlaceholderGenerateSuccessfully() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56146 -Production Component");
		base.stepInfo(
				"Verify that if problem docs is assigned as Priv doc then Production should generated with Priv Placeholder for the same document");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		BaseClass base = new BaseClass(driver);
		base.selectproject("AutomationAdditionalDataProject");

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		int docno = sessionSearch.MetaDataSearchInBasicSearch(Input.docName, "ID00002051");
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int firstFile = Integer.parseInt(beginningBates);
		int lastfile = firstFile + docno;
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSection(tagname);
		page.fillingTextSection();
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
		page.pdf_Verification_In_Generated_PlaceHolder(firstFile, lastfile, prefixID, suffixID, Input.tagNameTechnical);

		base.passedStep(
				"Verified that if problem docs is assigned as Priv doc then Production should generated with Priv Placeholder for the same document");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}
	/**
	 * Author : Vijaya.Rani date: 3/03/22 NA Modified date: NA Modified by:NA
	 * Description :To Verify Removal of Redaction Tag from a documents Should get
	 * produced in Production for Native. 'RPMXCON-48038'
	 * 
	 */
	@Test(description="RPMXCON-48038",enabled = true, groups = { "regression" })
	public void verifyRemovalRedactionTagProductionForNative() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48038 -Production Component");
		base.stepInfo(
				"To Verify Removal of Redaction Tag from a documents Should get produced in Production for Native");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String tagname1 = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateTagwithClassification(tagname1, "Select Tag Classification");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.bulkTagExisting(tagname1);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int nativefile = Integer.parseInt(beginningBates);
		int NativeDocStart = nativefile+3;
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionwithNativelyPlaceholder(tagname1);
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
		driver.waitForPageToBeReady();
		page.getQC_Download().isElementAvailable(160);
		page.getCopyPath().isDisplayed();
		page.getCopyPath().Click();
		page.getQC_Download().waitAndClick(10);
		page.getQC_Downloadbutton_allfiles().waitAndClick(10);
		page.extractFile();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");

		driver.waitForPageToBeReady();
		page.clickBackBtnandSelectingNative(7, tagname);
		driver.scrollingToBottomofAPage();
		page.getTIFF_EnableforPrivilegedDocs().isDisplayed();
		page.getTIFF_EnableforPrivilegedDocs().waitAndClick(10);
		page.clickMArkCompleteMutipleTimes(3);
		page.fillingPrivGuardPage();
		page.clickMArkCompleteMutipleTimes(2);
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();

		driver.waitForPageToBeReady();

		File Native1 = new File(home+"\\Downloads\\VOL0001\\Natives\\0001\\"+prefixID+NativeDocStart+suffixID+".doc");

		if (Native1.exists()) {
			base.passedStep("Native file are generated correctly : " + prefixID + NativeDocStart + suffixID + ".doc");
			System.out.println("passeed");
		} else {
			base.failedStep("verification failed");
			System.out.println("failed");

		}

		loginPage.logout();

	}
	

	/**
	 * @author Vijaya.Rani Modify Date: 14/03/2022 Test case id-RPMXCON-47886
	 * @Description To Verify TIFF Section with various options
	 *
	 */

	@Test(description="RPMXCON-47886",enabled = true,groups = { "regression" })
	public void verifyTiffSectionVariosOption() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47886 -Production Component");
		base.stepInfo("To Verify TIFF Section with various options");
		String productionname = "p" + Utility.dynamicNameAppender();

		ProductionPage page = new ProductionPage(driver);
		BaseClass base = new BaseClass(driver);
		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);

		driver.waitForPageToBeReady();
		page.getTIFFChkBox().waitAndClick(10);
		base.waitForElement(page.getTIFFTab());
		page.getTIFFTab().Click();
		driver.waitForPageToBeReady();

		page.visibleCheck("Page Options:");
		page.visibleCheck("Branding:");
		page.getElementDisplayCheck(page.getPrivPlaceHolder());
		base.stepInfo("Privledge placholder is displayed");
		page.getTiff_NativeDoc().waitAndClick(10);
		page.getElementDisplayCheck(page.getNativePlaceHolder());
		base.stepInfo("Native placeholder is displayed");
		page.visibleCheck("Redactions:");

		page.getTiffAdvanceBtn().ScrollTo();
		page.getTiffAdvanceBtn().waitAndClick(10);
		page.visibleCheck("Slip Sheets:");
		page.getSlipSheets().waitAndClick(10);

		page.visibleCheck("METADATA");
		page.visibleCheck("WORKPRODUCT");
		page.visibleCheck("CALCULATED");

		page.getSlipSheetWorkProduct().waitAndClick(10);
		page.getSlipSheetWorkProductFolderProduction().ScrollTo();
		page.getSlipSheetWorkProductFolderProduction().waitAndClick(10);
		base.waitForElement(page.getbtnAddToSelect());
		page.getbtnAddToSelect().waitAndClick(10);
		page.getElementDisplayCheck(page.getSelectedFieldItems());
		base.stepInfo("Document is added");
		page.getRemoveBtnInSlipSheet().waitAndClick(10);
		if (page.getSelectedFieldItems().isElementAvailable(1)) {
			base.failedStep("document not removed");
		} else {
			base.passedStep("Document got removed");
		}
		base.stepInfo("Field Add-Remove Functionality worked well");

		// adv btn open close verification
		SoftAssert softAssertion = new SoftAssert();
		boolean flag5 = page.getAdvanceBtnOpenCloseCheck().GetAttribute("class").contains("down");
		if (flag5) {
			softAssertion.assertTrue(flag5);
			base.passedStep("Advanced button is open");
		} else {
			base.failedStep("verification failed");
		}
		page.getAdvanceBtnOpenCloseCheck().ScrollTo();
		driver.scrollPageToTop();
		page.getAdvanceBtnOpenCloseCheck().ScrollTo();
		page.getAdvanceBtnOpenCloseCheck().waitAndClick(10);
		driver.waitForPageToBeReady();
		boolean flag6 = page.getAdvanceBtnOpenCloseCheck().GetAttribute("class").contains("right");
		if (flag6) {
			softAssertion.assertTrue(flag6);
			softAssertion.assertAll();
			base.passedStep("Advanced button is close");
		} else {
			base.failedStep("verification failed");
		}
		base.stepInfo("Advance Show/Hide Button should work as expected (show/Hide).");

		base.passedStep("Verified TIFF Section with various options");

		loginPage.logout();

	}

	/**
	 * Author : Vijaya.Rani date: 15/03/22 NA Modified date: NA Modified by:NA
	 * Description :Verify concatenated email value should be displayed correctly on Slip Sheets in Production. 'RPMXCON-55940'
	 *
	 * @throws IOException
	 */
	@Test(description="RPMXCON-55940",enabled = true,groups = { "regression" })
	public void verifyEmailValueDisplayOnSlipSheets() throws Exception {
	UtilityLog.info(Input.prodPath);
	base.stepInfo("RPMXCON-55940 -Production Sprint 13");
	base.stepInfo(
	"Verify concatenated email value should be displayed correctly on Slip Sheets in Production.");

	String foldername = "Folder" + Utility.dynamicNameAppender();
	String productionname = "p" + Utility.dynamicNameAppender();
	String tagname = "Tag" + Utility.dynamicNameAppender();
	String prefixID = Input.randomText + Utility.dynamicNameAppender();
	String suffixID = Input.randomText + Utility.dynamicNameAppender();
	String s1="EmailAuthorAddress";
	String s2="EmailAuthorName";
	String s3="EmailBCCNamesAndAddresses";
	String s4="EmailCCNamesAndAddresses";
	String s5 ="AuthorName";

	tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
	tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

	SessionSearch sessionSearch = new SessionSearch(driver);
	sessionSearch.SearchMetaData("IngestionName", "C113_GD_994_Native_Text_ForProduction_20211223121754233");
	sessionSearch.bulkFolderExisting(foldername);

	ProductionPage page = new ProductionPage(driver);
	page = new ProductionPage(driver);
	String beginningBates=page.getRandomNumber(2);
	int firstFile = Integer.parseInt(beginningBates);
	page.selectingDefaultSecurityGroup();
	page.addANewProduction(productionname);
	page.fillingDATSection();
	page.fillingNativeSection();
	page.selectGenerateOption(false);
	page.addingSlipSheetWorkProduct(s1,s2,s3,s4);
	page.fillingTextSection();
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
	String PDocCount = page.getProductionDocCount().getText();
	int docno = Integer.parseInt(PDocCount);
	int lastfile = firstFile +docno;
	page.extractFile();
	page.OCR_Verification_In_Generated_Tiff_SS(firstFile, lastfile, prefixID, suffixID, s1);
	page.OCR_Verification_In_Generated_Tiff_SS(firstFile, lastfile, prefixID, suffixID, s5);
	page.OCR_Verification_In_Generated_Tiff_SS(firstFile, lastfile, prefixID, suffixID, s3);
	page.OCR_Verification_In_Generated_Tiff_SS(firstFile, lastfile, prefixID, suffixID, s4);

	tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
	loginPage.logout();
	}
	/**
	 * @author Aathith Test case id-RPMXCON-49729
	 * @Description Verify that branding is applied on all pages for  image based documents on generated PDF file 
	 * 
	 */
	@Test(description="RPMXCON-49729",enabled = true,groups = { "regression" })
	public void verifyDiffFileBrandingGenerateSuccessfully() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49729 -Production Component");
		base.stepInfo("Verify that branding is applied on all pages for  image based documents on generated PDF file");
		
		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		
		BaseClass base = new BaseClass(driver);
		base.selectproject("AutomationAdditionalDataProject");

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		DataSets dataset = new DataSets(driver);
		base.stepInfo("Navigating to dataset page");
		dataset.navigateToDataSetsPage();
		base.stepInfo("Selecting uploadedset and navigating to doclist page");
		dataset.selectDataSetWithName("RPMXCON39718");
		DocListPage doc = new DocListPage(driver);
		driver.waitForPageToBeReady();

		doc.selectAllDocs();
		
		doc.bulkTagExistingFromDoclist(tagname);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int firstFile = Integer.parseInt(beginningBates);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSection(tagname);
		page.fillingTextSection();
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
		int DocCount = Integer.parseInt(PDocCount);
		int lastfile = firstFile + DocCount;
		page.extractFile();
		page.pdf_Verification_In_Generated_PlaceHolder(firstFile, lastfile, prefixID, suffixID, Input.searchString4);
		
		base.passedStep("Verified that branding is applied on all pages for  image based documents on generated PDF file");
		
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");	
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();
		
	}
	/**
	 * @author Aathith Test case id-RPMXCON-47932
	 * @Description To verify, User able to edit a production to regenerate it
	 * 
	 */
	@Test(description="RPMXCON-47932",enabled = true,groups = { "regression" })
	public void verifyRegenerateWithEditSuccessfully() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47932 -Production Component");
		base.stepInfo("To verify, User able to edit a production to regenerate it");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String newProdName = "N" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		
		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		page.getBackButton().waitAndClick(5);
		page.verifyProductionStatusInGenPage("Post-Generation QC checks Complete");
		page.clickElementNthtime(page.getBackButton(), 6);
		page.getMarkIncompleteButton().waitAndClick(5);
		page.getDATTab().waitAndClick(10);
		driver.waitForPageToBeReady();
		page.getDAT_DATField1().SendKeys("B" + Utility.dynamicNameAppender());
		page.navigateToNextSection();
		page.navigateToNextSection();
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(newProdName);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		
		base.passedStep("verified, User able to edit a production to regenerate it");
		
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();
		
	}
	/**
	 * @author Aathith Test case id-RPMXCON-48814
	 * @Description Verify that Production should generated successfully for Natively PDF documents
	 * @Hint : test case run on the project Regression_AllDataset_Consilio1 in UAT environment
	 */
	@Test(description="RPMXCON-48814",enabled = true,groups = { "regression" })
	public void verifyProdGenSuccussNativePdf() throws Exception {

		UtilityLog.info(Input.prodPath);
		base= new BaseClass(driver);
		
		base.stepInfo("RPMXCON-48814 -Production Component");
		base.stepInfo("Verify that Production should generated successfully for Natively PDF documents");

		
		base.selectproject(Input.additionalDataProject);
		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		DataSets dataset = new DataSets(driver);
		base.stepInfo("Navigating to dataset page");
		dataset.navigateToDataSetsPage();
		base.stepInfo("Selecting uploadedset and navigating to doclist page");
		dataset.selectDataSetWithName("NativelyPDF");
		DocListPage doc = new DocListPage(driver);
		driver.waitForPageToBeReady();

		doc.selectAllDocs();
		doc.bulkTagExistingFromDoclist(tagname);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSection(tagname,Input.searchString4);
		page.fillingTextSection();
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
		
		base.passedStep("Verified that Production should generated successfully for Natively PDF documents");
		
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();
		
	}
	/**
	 * @author Aathith 
	 * Test case id-RPMXCON-55952
	 * @Description Verify that if DAU clicks on project from dashboard and Copy the Production URL which is not part of assigned domain Project
	 * 
	 */
	@Test(description="RPMXCON-55952",enabled = true,groups = { "regression" })
	public void verifyProdUrlNotPartOfDomain() throws Exception {
		
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-55952 -Production Sprint 10");
		base.stepInfo("Verify that if DAU clicks on project from dashboard and Copy the Production URL which is not part of assigned domain Project");
		base = new BaseClass(driver);
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.impersonateSAtoPA();
		
        ProductionPage page = new ProductionPage(driver);
        productionname = "p" + Utility.dynamicNameAppender();
        page.selectingSecurityGroup(Input.securityGroup);
		page.addANewProduction(productionname);
		page.fillingDATSection();
        driver.waitForPageToBeReady();
		
		String currentURL=driver.getWebDriver().getCurrentUrl();
		loginPage.logout();
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		driver.waitForPageToBeReady();
		page.getDaAdditionalDataProject(Input.additionalDataProject).waitAndClick(10);
		page.gotoDAtoRMU(Input.additionalDataProject).waitAndClick(10);
		driver.waitForPageToBeReady();
		base.stepInfo("switched to RMU");
		
		page = new ProductionPage(driver);
		driver.Navigate().to(currentURL);
		driver.waitForPageToBeReady();
		String ErrorMsg=page.getErrorMsgHeader().getText();
		if(ErrorMsg.contains("Error")) { base.passedStep("Error message is displayed as expected"); }
		else {base.failedStep("Error message is not  displayed as expected");	}
		driver.Navigate().back();
		
		base.passedStep("Verified that if DAU clicks on project from dashboard and Copy the Production URL which is not part of assigned domain Project");
		loginPage.logout();
		
	}
	/**
	 * @author Aathith Test case id-RPMXCON-47924
	 * @Description To Verify Production Generation in Different Security Group.
	 * 
	 */
	@Test(description="RPMXCON-47924",enabled = true,groups = { "regression" })
	public void verifyProdDiffSecuriyGroup() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47924 -Production Component");
		base.stepInfo("To Verify Production Generation in Different Security Group.");

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String securityGroup = "Production_Security_Group"+ Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		
		SecurityGroupsPage sg = new SecurityGroupsPage(driver);
		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		sg.createSecurityGroups(securityGroup);
		
		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.translationDocumentId);
		sessionSearch.ViewInDocList();
		DocListPage doc = new DocListPage(driver);
		doc.selectAllDocs();
		doc.docListToBulkRelease(securityGroup);
		driver.waitForPageToBeReady();
		doc.bulkTagExistingFromDoclist(tagname);
		
		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		sg.addTagToSecurityGroup(securityGroup, tagname);
		
		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingSecurityGroup(securityGroup);
		driver.waitForPageToBeReady();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTiffSectionDisablePrivilegedDocs();
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
		
		base.passedStep("To Verify Production Generation in Different Security Group.");
		
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();
		
	}
	/**
	 * @author Aathith Test case id-RPMXCON-49725
	 * @Description Verify that branding is applied on all pages for  image based documents on generated TIFF file
	 * 
	 */
	@Test(description="RPMXCON-49725",enabled = true,groups = { "regression" })
	public void verifyBrandingText() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49725 -Production Component");
		base.stepInfo("Verify that branding is applied on all pages for  image based documents on generated TIFF file");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		DataSets dataset = new DataSets(driver);
		base.stepInfo("Navigating to dataset page");
		dataset.navigateToDataSetsPage();
		base.stepInfo("Selecting uploadedset and navigating to doclist page");
		dataset.SelectingUploadedDataSet();
		DocListPage doc = new DocListPage(driver);
		driver.waitForPageToBeReady();

		doc.selectAllDocs();
		doc.bulkTagExistingFromDoclist(tagname);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int firstFile = Integer.parseInt(beginningBates);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname,Input.searchString4);
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		int doccount =page.fillingGeneratePageWithContinueGenerationPopup();
		int lastFile = firstFile + doccount;
		page.extractFile();
		page.OCR_Verification_In_Generated_Tiff_tess4j(firstFile, lastFile, prefixID, suffixID, Input.searchString4);
		
		base.passedStep("Verified that branding is applied on all pages for  image based documents on generated TIFF file");
		
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();
		
	}
	/**
	 * @author Aathith Test case id-RPMXCON-47969
	 * @Description To Verify Correct  Count of Native Documents produce in (Production in Different Security Group).
	 * 
	 */
	@Test(description="RPMXCON-47969",enabled = true,groups = { "regression" })
	public void verifyProdPrivCoutDiffSecuriyGroup() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47969 -Production Component");
		base.stepInfo("To Verify Correct  Count of Native Documents produce in (Production in Different Security Group).");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String securityGroup = "Production_Security_Group"+ Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		
		SecurityGroupsPage sg = new SecurityGroupsPage(driver);
		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		sg.createSecurityGroups(securityGroup);
		
		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkRelease(securityGroup);
		sessionSearch.bulkFolderExisting(foldername);
		
		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		sg.addFolderToSecurityGroup(securityGroup, foldername);
		
		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingSecurityGroup(securityGroup);
		driver.waitForPageToBeReady();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTiffSectionDisablePrivilegedDocs();
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.verifyText(page.getNumberOfNativeDocs(), "0");
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		
		base.passedStep("To Verify Correct  Count of Native Documents produce in (Production in Different Security Group).");
		
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		loginPage.logout();
		
	}
	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-55993
	 * @Description: Verify that status text along with the docment counts show properly in the space available for the progress bar on tile view
	 */
	@Test(description="RPMXCON-55993",enabled = true, groups = { "regression" })
	public void verifyStatusTextAndDocumentCountWithDifferentResolution() throws Exception {

		UtilityLog.info(Input.prodPath);

		base.stepInfo("RPMXCON-55993 -Production component");
		base.stepInfo(
				"Verify that status text along with the docment counts show properly in the space available for the progress bar on tile view");
		
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		int[] diemen = { 1920, 1080 };
		double[] zoom = { 0.75, 0.80, 0.90, 1, 1.25 };

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// Verify archive status on Gen page
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.getbtnProductionGenerate().waitAndClick(10);
		base.stepInfo("Production with different progress status should be available");

		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();

		for (int i = 0; i < zoom.length; i++) {

			Dimension pram1 = new Dimension(diemen[0], diemen[1]);
			driver.Manage().window().setSize(pram1);
			((JavascriptExecutor) driver.getWebDriver()).executeScript("document.body.style.zoom = '"+zoom[i]+"'");

			driver.Navigate().refresh();
			if (page.getProductionFromHomePage(productionname).isDisplayed()) {
				base.passedStep("Status bar text is displayed in : " + diemen[0] + "x" + diemen[1]
						+ " with zoom size : " + zoom[i]);
				System.out.println("displayed");
			} else {
				base.failedStep("Status bar text is not displayed");
				System.out.println("Not displayed");
			}
			if (page.getProductionDocCountFromHomePage(productionname).isDisplayed()) {
				base.passedStep("Status bar document count is displayed in : " + diemen[0] + "x" + diemen[1]
						+ " with zoom size : " + zoom[i]);
				System.out.println("displayed");
			} else {
				base.failedStep("Status bar document count is not displayed");
				System.out.println("Not displayed");
			}
		}

		base.passedStep(
				"Verified that status text along with the docment counts show properly in the space available for the progress bar on tile view");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();
	}
	/**
	* @author Aathith created on:NA modified by:NA TESTCASE No:RPMXCON-47926
	* @Description:To Verify PRIV flag configured in the DAT section of Production is to being honored for all docs in the generated production
	*/
	@Test(description="RPMXCON-47926",enabled = true,groups = { "regression" })
	public void verifyDatFiledisBlank() throws Exception {
	UtilityLog.info(Input.prodPath);

	base.stepInfo("Test case id : RPMXCON-47926 ");
	base.stepInfo(
	"To Verify PRIV flag configured in the DAT section of Production is to being honored for all docs in the generated production");
	
	foldername = "FolderProd" + Utility.dynamicNameAppender();
	tagname = "Tag" + Utility.dynamicNameAppender();
	String prefixID = Input.randomText + Utility.dynamicNameAppender();
	String suffixID = Input.randomText + Utility.dynamicNameAppender();

	TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
	tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

	SessionSearch sessionSearch = new SessionSearch(driver);
	sessionSearch = new SessionSearch(driver);
	sessionSearch.basicContentSearch(Input.testData1);
	sessionSearch.bulkFolderExisting(foldername);
	sessionSearch.bulkTagExisting(tagname);

	ProductionPage page = new ProductionPage(driver);
	String beginningBates = page.getRandomNumber(2);
	productionname = "p" + Utility.dynamicNameAppender();
	page.selectingDefaultSecurityGroup();
	page.addANewProduction(productionname);
	page.fillingDATSection();
	page.addNewFieldOnDAT();
	page.addDatField(1, "Production", "TIFFPageCount");
	page.getPrivledgedDATCheckBox(1).waitAndClick(10);
	page.fillingTIFFSection(tagname);
	page.navigateToNextSection();
	page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
	page.navigateToNextSection();
	page.fillingDocumentSelectionWithTag(tagname);
	page.getIncludeFamilies().waitAndClick(10);
	page.navigateToNextSection();
	page.fillingPrivGuardPage();
	page.fillingProductionLocationPage(productionname);
	page.navigateToNextSection();
	page.fillingSummaryAndPreview();
	page.fillingGeneratePageWithContinueGenerationPopup();
	page.extractFile();
	
	driver.waitForPageToBeReady();
	String home = System.getProperty("user.home");
	String name = page.getProduction().getText().trim();
	driver.waitForPageToBeReady();
	File DatFile = new File(home + "/Downloads/VOL0001/Load Files/" + name + "_DAT.dat");
	if (DatFile.exists()) {
	base.passedStep("Dat file is exists in generated production");
	} else {
	base.failedStep("Dat file is not displayed as expected");
	}

	String line;
	List<String> lines = new ArrayList<>();
	BufferedReader brReader = new BufferedReader(new InputStreamReader(new FileInputStream(DatFile), "UTF16"));
	while ((line = brReader.readLine()) != null) {
	lines.add(line);
	}
	for (String a : lines) {
	System.out.println(a);
	String[] arrOfStr = a.split("þ");
	for(String text : arrOfStr)
	{
		System.out.println("value : "+ text);
	if (text.trim().equals("")||text.isEmpty()) {
	base.passedStep("meta data field is blank");
	} else {
	base.stepInfo("this field is bates number");
	}
	}
	}
	brReader.close();
	loginPage.logout();
	base.passedStep("Verified PRIV flag configured in the DAT section of Production is to being honored for all docs in the generated production");
	}
	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56050
	 * @Description: Verify that after destination copy is completed it should displays 'Exporting Files Complete' status on Grid  View
	 */
	@Test(description="RPMXCON-56050",groups = { "regression" })
	public void verifyExportinFilesCompleteStatusOnGridView() throws Exception {
	UtilityLog.info(Input.prodPath);
	base.stepInfo("RPMXCON-56050 -Production Compinent");
	base.stepInfo("Verify that after destination copy is completed it should displays 'Exporting Files Complete' status on Grid  View");
	
	foldername = "FolderProd" + Utility.dynamicNameAppender();
	String prefixID = Input.randomText + Utility.dynamicNameAppender();
	String suffixID = Input.randomText + Utility.dynamicNameAppender();
	
	// Pre-requisites
	// create tag and folder
	TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
	this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
	tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

	// search for folder
	SessionSearch sessionSearch = new SessionSearch(driver);
	sessionSearch = new SessionSearch(driver);
	sessionSearch.basicContentSearch(Input.testData1);
	sessionSearch.bulkFolderExisting(foldername);

	//Verify archive status on Gen page
	ProductionPage page = new ProductionPage(driver);
	productionname = "p" + Utility.dynamicNameAppender();
	String beginningBates = page.getRandomNumber(2);
	page.selectingDefaultSecurityGroup();
	page.addANewProduction(productionname);
	page.fillingDATSection();
	page.navigateToNextSection();
	page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
	page.navigateToNextSection();
	page.fillingDocumentSelectionPage(foldername);
	page.navigateToNextSection();
	page.fillingPrivGuardPage();
	page.fillingProductionLocationPage(productionname);
	page.navigateToNextSection();
	page.fillingSummaryAndPreview();
	page.fillingGeneratePageWithContinueGenerationPopupWithoutWait();
	
	// Go To Production Home Page
	page.goToProductionGridView();
	page.verifyProductionStatusInGridViewForHighVolumeProject("Exporting Files Complete", productionname);
		
	//delete tags and folders
	tagsAndFolderPage = new TagsAndFoldersPage(driver);
	this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
	tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
	loginPage.logout();
	}
	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56047
	 * @Description: Verify that after LST generation completed it should displays ' Generating Load Files Complete' status on Grid View on home page
	 */
	@Test(description="RPMXCON-56047",groups = { "regression" })
	public void verifyGeneratingLoadCompleteStatusOnGridView() throws Exception {
	UtilityLog.info(Input.prodPath);
	base.stepInfo("RPMXCON-56047 -Production Compinent");
	base.stepInfo("Verify that after LST generation completed it should displays ' Generating Load Files Complete' status on Grid View on home page");
	
	foldername = "FolderProd" + Utility.dynamicNameAppender();
	String prefixID = Input.randomText + Utility.dynamicNameAppender();
	String suffixID = Input.randomText + Utility.dynamicNameAppender();
	
	// Pre-requisites
	// create tag and folder
	TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
	this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
	tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

	// search for folder
	SessionSearch sessionSearch = new SessionSearch(driver);
	sessionSearch = new SessionSearch(driver);
	sessionSearch.basicContentSearch(Input.testData1);
	sessionSearch.bulkFolderExisting(foldername);

	//Verify archive status on Gen page
	ProductionPage page = new ProductionPage(driver);
	productionname = "p" + Utility.dynamicNameAppender();
	String beginningBates = page.getRandomNumber(2);
	page.selectingDefaultSecurityGroup();
	page.addANewProduction(productionname);
	page.fillingDATSection();
	page.navigateToNextSection();
	page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
	page.navigateToNextSection();
	page.fillingDocumentSelectionPage(foldername);
	page.navigateToNextSection();
	page.fillingPrivGuardPage();
	page.fillingProductionLocationPage(productionname);
	page.navigateToNextSection();
	page.fillingSummaryAndPreview();
	page.fillingGeneratePageWithContinueGenerationPopupWithoutWait();
	
	// Go To Production Home Page
	page.goToProductionGridView();
	page.verifyProductionStatusInGridViewForHighVolumeProject("Generating Load Files Complete", productionname);
		
	//delete tags and folders
	tagsAndFolderPage = new TagsAndFoldersPage(driver);
	this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
	tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
	loginPage.logout();
	}
	/**
	 * @author Aathith Test case id-RPMXCON-60903
	 * @Description Verify Production should be generated successfully with the redacted documents (for documents with annotation) 
	 * 
	 */
	@Test(description="RPMXCON-60903",enabled = true,groups = { "regression" })
	public void verifyAnotationForRedactionPdf() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-60903 -Production Component");
		base.stepInfo("Verify Production should be generated successfully with the redacted documents (for documents with annotation)");
		
		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		
		BaseClass base = new BaseClass(driver);
		base.selectproject(Input.additionalDataProject);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		DataSets dataset = new DataSets(driver);
		base.stepInfo("Navigating to dataset page");
		dataset.navigateToDataSetsPage();
		base.stepInfo("Selecting uploadedset and navigating to doclist page");
		dataset.selectDataSetWithName("PDF Annotations");
		DocListPage doc = new DocListPage(driver);
		driver.waitForPageToBeReady();

		doc.selectAllDocs();
		doc.docListToBulkRelease(Input.securityGroup);
		doc.bulkTagExistingFromDoclist(tagname);
		doc.selectAllDocs();
		doc.bulkFolderExisting(foldername);
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.selectproject(Input.additionalDataProject);
		
		RedactionPage redactionpage=new RedactionPage(driver);
        driver.waitForPageToBeReady();
        redactionpage.manageRedactionTagsPage(Redactiontag1);
		
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.selectFolderViewInDocView(foldername);
		
		DocViewRedactions docViewRedactions=new DocViewRedactions(driver);
		DocViewPage docView = new DocViewPage(driver);
		docView.documentSelection(3);
        driver.waitForPageToBeReady();
        docViewRedactions.redactRectangleUsingOffset(10,10,20,20);
        driver.waitForPageToBeReady();
        docViewRedactions.selectingRedactionTag2(Redactiontag1);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSectionwithNativelyPlaceholder(tagname);
		page.getClk_burnReductiontoggle().ScrollTo();
		page.getClk_burnReductiontoggle().waitAndClick(10);
		page.burnRedactionWithRedactionTag(Redactiontag1);
		page.fillingTextSection();
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
		page.pdf_Verification_In_Generated_Pdf(prefixID, suffixID, beginningBates);
		
		base.passedStep("Verified Production should be generated successfully with the redacted documents (for documents with annotation)");
		
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);	
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();
		
	}

	/**
	* @author Vijaya.Rani created on:25/03/2022 modified by:NA TESTCASE No:RPMXCON-47171
	* @Description:Verify that PDF files should be copied to folder when 'Split Sub Folders' is ON with split count as 10.
	* 'RPMXCON-47171' 
	*/
     @Test(description="RPMXCON-47171",enabled = true, groups = { "regression" })
	public void verifyTheSubFolderAfterGenrationPDFFile() throws Exception {
	UtilityLog.info(Input.prodPath);
	loginPage.logout();
	loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
	base.stepInfo("RPMXCON-47171 -Production Sprint 14");
	base.stepInfo(
	"Verify that PDF files should be copied to folder when 'Split Sub Folders' is ON with split count as 10");

	String tagname = "Tag" + Utility.dynamicNameAppender();
	String prefixID = Input.randomText + Utility.dynamicNameAppender();
	String suffixID = Input.randomText + Utility.dynamicNameAppender();

	TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

	SessionSearch sessionSearch = new SessionSearch(driver);
	sessionSearch = new SessionSearch(driver);
	sessionSearch.basicContentSearch(Input.telecaSearchString);
	sessionSearch.bulkTagExisting(tagname);

	// document for pdf section
	ProductionPage page = new ProductionPage(driver);
	String productionname = "p" + Utility.dynamicNameAppender();
	String beginningBates = page.getRandomNumber(2);
	page.addANewProduction(productionname);
	page.fillingDATSection();
	page.fillingNativeSection();
	page.fillingPDFSectionwithNativelyPlaceholder(tagname);
	page.fillingTextSection();
	page.navigateToNextSection();
	page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
	page.navigateToNextSection();
	page.fillingDocumentSelectionWithTag(tagname);
	page.navigateToNextSection();
	page.fillingPrivGuardPage();
	driver.waitForPageToBeReady();
	driver.scrollingToBottomofAPage();
	page.ProductionLocationSplitCount().Clear();
	page.ProductionLocationSplitCount().SendKeys(Input.pageNumber);
	driver.scrollPageToTop();
	page.fillingProductionLocationPage(productionname);
	page.navigateToNextSection();
	page.fillingSummaryAndPreview();
	page.fillingGeneratePageWithContinueGenerationPopup();
	page.extractFile();
	String home = System.getProperty("user.home");
	
	File dirPdf = new File(home+"\\Downloads\\VOL0001\\PDF");
	File dirText = new File(home+"\\Downloads\\VOL0001\\Text");
	File dirNative = new File(home+"\\Downloads\\VOL0001\\Natives");
	int dirPdfCount = page.dirFoldersCount(dirPdf);
	int dirTextCount = page.dirFoldersCount(dirText);
	int dirNativeCount = page.dirFoldersCount(dirNative);
	if(1!=dirPdfCount) {
		System.out.println("pdf verified");
		base.stepInfo("Pdf folder is split");
	}
	if(1!=dirNativeCount) {
		System.out.println("Native verified");
		base.stepInfo("Native folder is split");
	}
	if(1!=dirTextCount) {
		System.out.println("Text verified");
		base.stepInfo("Text folder is split");
	}
	base.passedStep("files is split as per the split folder count");
	base.passedStep("Verified that PDF files should be copied to folder when 'Split Sub Folders' is ON with split count as 10");
	
	page.deleteFiles();
	tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
	loginPage.logout();
    }
     /**
		 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
		 *         No:RPMXCON-55983
		 * @Description: Verify that once Archiving is in progress, it will displays status on Production Progress bar Tile View as 'Creating Archive - 10%'
		 */
		@Test(description="RPMXCON-55983",enabled = true,groups = { "regression" })
		public void verifiyCreateArchicTenPercenteOnTileView() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-55983 - Production Component");
		base.stepInfo("Verify that once Archiving is in progress, it will displays status on Production Progress bar Tile View as 'Creating Archive - 10%'");
		String testData1 = Input.testData1;
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		TempName ="Templete" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateTagwithClassification(tagname, "Privileged");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		
		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.bulkFolderExisting(foldername);
		
		//Verify archive status on Gen page
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.getbtnProductionGenerate().waitAndClick(10);
		
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		//verification
		page.verifyProductionStatusInTileViewForHighVolumeProject("Creating Archive - 10%", productionname);
		
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();
		}
		/**
		 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
		 *         No:RPMXCON-55982
		 * @Description: Verify that once Archiving is in progress, it will displays status on Production Generation page as 'Creating Archive - 10%'
		 */
		@Test(description="RPMXCON-55982",enabled = true,groups = { "regression" })
		public void createArchivingTenPercentStatusVerifyOnGenPage() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-55982 -Production");
		base.stepInfo("Verify that once Archiving is in progress, it will displays status on Production Generation page as 'Creating Archive - 10%'");
		
		String testData1 = Input.testData1;
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);

		//Verify archive status on Gen page
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();

		// Wait until Generate button enables
		page.fillingGeneratePageWithContinueGenerationPopupWithoutWait();
		
		driver.waitForPageToBeReady();
		page.verifyProductionStatusInGenerationPageForHighVolumeProject("Creating Archive - 10%");
		
		//delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();
		}
		/**
		 * @author Aathith Test case id-RPMXCON-49726
		 * @Description Verify that branding is applied on all pages for  image based documents on generated PDF file 
		 * 
		 */
		@Test(description="RPMXCON-49726",enabled = true,groups = { "regression" })
		public void verifyPdfBrandingGenerateSuccessfully() throws Exception {

			UtilityLog.info(Input.prodPath);
			base.stepInfo("RPMXCON-49726 -Production Component");
			base.stepInfo("Verify that branding is applied on all pages for  image based documents on generated PDF file");
			
			String foldername = "Folder" + Utility.dynamicNameAppender();
			String tagname = "Tag" + Utility.dynamicNameAppender();
			String productionname = "p" + Utility.dynamicNameAppender();
			String prefixID = Input.randomText + Utility.dynamicNameAppender();
			String suffixID = Input.randomText + Utility.dynamicNameAppender();
			
			TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
			tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");
			tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

			DataSets dataset = new DataSets(driver);
			base.stepInfo("Navigating to dataset page");
			dataset.navigateToDataSetsPage();
			base.stepInfo("Selecting uploadedset and navigating to doclist page");
			dataset.selectDataSetWithName(Input.pdfDataSet);
			DocListPage doc = new DocListPage(driver);
			driver.waitForPageToBeReady();

			doc.selectAllDocs();
			
			doc.bulkTagExistingFromDoclist(tagname);

			ProductionPage page = new ProductionPage(driver);
			page = new ProductionPage(driver);
			String beginningBates = page.getRandomNumber(2);
			int firstFile = Integer.parseInt(beginningBates);
			page.selectingDefaultSecurityGroup();
			page.addANewProduction(productionname);
			page.fillingDATSection();
			page.fillingNativeSection();
			page.fillingPDFSection(tagname);
			page.fillingTextSection();
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
			String PDocCount = page.getProductionDocCount().getText();
			int DocCount = Integer.parseInt(PDocCount);
			int lastfile = firstFile + DocCount;
			page.extractFile();
			page.pdf_Verification_In_Generated_PlaceHolder(firstFile, lastfile, prefixID, suffixID, Input.searchString4);
			
			base.passedStep("Verified that branding is applied on all pages for  image based documents on generated PDF file");
			
			tagsAndFolderPage = new TagsAndFoldersPage(driver);
			this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
			tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");	
			tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
			loginPage.logout();
			
		}
		/**
		 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
		 *         No:RPMXCON-55979
		 * @Description: Verify that after LST generation completed it should displays ' Generating Load Files Complete' status on Production Progress bar Tile View
		 */
		@Test(description="RPMXCON-55979",groups = { "regression" })
		public void verifiyLSTGenCompleteOnTileView() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-55979 -Production Component");
		base.stepInfo("Verify that after LST generation completed it should displays ' Generating Load Files Complete' status on Production Progress bar Tile View");
		String testData1 = Input.testData1;
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		TempName ="Templete" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateTagwithClassification(tagname, "Privileged");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.bulkFolderExisting(foldername);
		
		//Verify archive status on Gen page
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.getbtnProductionGenerate().waitAndClick(10);
		
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		//verification
		page.verifyProductionStatusInHomePage("Generating Load Files", productionname);
		base.passedStep("Verified that after LST generation completed it should displays ' Generating Load Files Complete' status on Production Progress bar Tile View");
		
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();
		}
		/**
		 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
		 *         No:RPMXCON-55978
		 * @Description: Verify that after LST generation completed it should displays ' Generating Load Files Complete' status on Production Generation Page
		 */
		@Test(description="RPMXCON-55978",groups = { "regression" })
		public void verifyGenerationLoadFileCompleteStatusInGenPage() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-55978 -Production");
		base.stepInfo("Verify that after LST generation completed it should displays ' Generating Load Files Complete' status on Production Generation Page");
		
		String testData1 = Input.testData1;
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);

		//Verify archive status on Gen page
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();

		// Wait until Generate button enables
		page.fillingGeneratePageWithContinueGenerationPopupWithoutWait();
		page.verifyProductionStatusInGenPage("Load File Generation");
		base.passedStep("Verified that after LST generation completed it should displays ' Generating Load Files Complete' status on Production Generation Page");
		
		//delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();
		}
		/**
		 * @author Aathith.Senthilkumar
		 * 			RPMXCON-58561
		 * @Description Verify the load files should be created under the mentioned default directory in the Export location
		 * 
		 */
			@Test(description="RPMXCON-58561",groups = { "regression" })
			public void verifyLoadFileMentionedDirectory() throws Exception {
			UtilityLog.info(Input.prodPath);
			base.stepInfo("RPMXCON-58561 -Production");
			base.stepInfo("Verify the load files should be created under the mentioned default directory in the Export location");
			
			foldername = "FolderProd" + Utility.dynamicNameAppender();
			tagname = "Tag" + Utility.dynamicNameAppender();
			String newExport = "Ex" + Utility.dynamicNameAppender();

			// Pre-requisites
			// create tag and folder
			TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
			this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
			tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
			tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

			// search for folder
			SessionSearch sessionSearch = new SessionSearch(driver);
			sessionSearch = new SessionSearch(driver);
			sessionSearch.basicContentSearch(Input.testData1);
			sessionSearch.bulkFolderExisting(foldername);
			sessionSearch.bulkTagExisting(tagname);

			//Verify 
			ProductionPage page = new ProductionPage(driver);
			productionname = "p" + Utility.dynamicNameAppender();
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
			page.fillingTIFFSection(tagname);
			page.fillingTextSection();
			page.fillingMP3();
			page.getAdvancedProductionComponent().waitAndClick(10);
			page.advancedProductionComponentsTranslations();
			page.navigateToNextSection();
			page.fillingExportNumberingAndSortingPage(prefixID, suffixID);
			page.navigateToNextSection();
			page.fillingDocumentSelectionPage(foldername);
			page.navigateToNextSection();
			page.fillingPrivGuardPage();
			page.GetVolumeLocation().selectFromDropdown().selectByVisibleText("In Delivery Folder");
			page.visibleCheck("Load Files:");
			page.visibleCheck("Folder Name:");
			page.fillingExportLocationPage(productionname);
			
			String location = page.getProductionOutputLocation_VolumeName().GetAttribute("value");
			String loadfile = page.getProductionComponentsFolderDetails_FolderName_LoadFiles().GetAttribute("value");
			
			page.navigateToNextSection();
			page.fillingSummaryAndPreview();
			page.fillingGeneratePageWithContinueGenerationPopupWithoutCommitandDownload();
			String name = page.getProduction().getText().trim();
			page.getCopyPath().waitAndClick(10);
			
	        String actualCopedText = page.getCopiedTextFromClipBoard();
	        
			String parentTab = page.openNewTab(actualCopedText);
			page.getFileDir(location).waitAndClick(10);
			driver.waitForPageToBeReady();
			page.getFileDir(loadfile).waitAndClick(10);
	        driver.waitForPageToBeReady();
	        
	        page.visibleCheck(name+"_DAT.dat");
	        page.visibleCheck(name+"_MP3.lst");
	        page.visibleCheck(name+"_Native.lst");
	        page.visibleCheck(name+"_Text.lst");
	        page.visibleCheck(name+"_TIFF.OPT");
	        page.visibleCheck(name+"_TRANSLATION.lst");
	        
	        driver.close();
			driver.getWebDriver().switchTo().window(parentTab);
			base.passedStep("Verified the load files should be created under the mentioned default directory in the Export location");
			
			//delete tags and folders
			tagsAndFolderPage = new TagsAndFoldersPage(driver);
			this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
			tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
			tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
			loginPage.logout();
			
		 }
			/**
			 * @author Aathith Test case id-RPMXCON-48314
			 * @Description Verify that Producing a PDF with text highlighting and reviewer remarks, 
			 * 		block redactions and 'this page', must not eliminate any text characters 
			 * 		unintentionally in the produced document 
			 * 
			 */
			@Test(description="RPMXCON-48314",enabled  = true,groups = { "regression" })
			public void verifyProducedPadWithTextReviewRemarkRedaction() throws Exception {

				UtilityLog.info(Input.prodPath);
				base.stepInfo("RPMXCON-48314 -Production Component");
				base.stepInfo("Verify that Producing a PDF with text highlighting and reviewer remarks, block redactions and 'this page', must not eliminate any text characters unintentionally in the produced document");
				
				loginPage.logout();
				loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
				
				String foldername = "Folder" + Utility.dynamicNameAppender();
				String tagname = "Tag" + Utility.dynamicNameAppender();
				String productionname = "p" + Utility.dynamicNameAppender();
				String Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();
				String prefixID = Input.randomText + Utility.dynamicNameAppender();
				String suffixID = Input.randomText + Utility.dynamicNameAppender();
				
				
				TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
				tagsAndFolderPage.createNewTagwithClassificationInRMU(tagname, "Select Tag Classification");
				tagsAndFolderPage.CreateFolderInRMU(foldername);
				
				RedactionPage redactionpage=new RedactionPage(driver);
		        driver.waitForPageToBeReady();
		        redactionpage.manageRedactionTagsPage(Redactiontag1);
				
				SessionSearch sessionSearch = new SessionSearch(driver);
				int docCount =sessionSearch.basicContentSearch(Input.testData1);
				sessionSearch.bulkFolderExisting(foldername);
				sessionSearch.bulkTagExisting(tagname);
				sessionSearch.ViewInDocViewWithoutPureHit();
				
				DocViewRedactions docViewRedactions=new DocViewRedactions(driver);
				DocViewPage docView = new DocViewPage(driver);
				docView.documentSelection(docCount);
	            driver.waitForPageToBeReady();
	            docViewRedactions.RedactTextInDocView(10,10,20,20);
	            driver.waitForPageToBeReady();
	            docViewRedactions.selectingRedactionTag2(Redactiontag1);
	            
				ProductionPage page = new ProductionPage(driver);
				page = new ProductionPage(driver);
				String beginningBates = page.getRandomNumber(2);
				page.selectingDefaultSecurityGroup();
				page.addANewProduction(productionname);
				page.fillingDATSection();
				page.fillingNativeSection();
				page.fillingPDFSectionwithNativelyPlaceholder(tagname);
				page.getClk_burnReductiontoggle().ScrollTo();
				page.getClk_burnReductiontoggle().waitAndClick(10);
				page.burnRedactionWithRedactionTag(Redactiontag1);
				page.fillingTextSection();
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
				String home = System.getProperty("user.home");
				File Native = new File(home + "/Downloads/VOL0001/Natives/");
				page.extractFile();
				page.isdatFileExist();
				page.isfileisExists(Native);
				page.pdf_Verification_In_Generated_Pdf(prefixID, suffixID, beginningBates);
				page.pdf_Verification_In_Generated_PlaceHolder(prefixID, suffixID, beginningBates, "REDACTED");
				
				base.passedStep("Verified that Producing a PDF with text highlighting and reviewer remarks, block redactions and 'this page', must not eliminate any text characters unintentionally in the produced document");
				
				tagsAndFolderPage = new TagsAndFoldersPage(driver);
				this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
				tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);	
				tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
				loginPage.logout();
				
			}
			/**
			 * @author Aathith Test case id-RPMXCON-48315
			 * @Description Verify that Producing a TIFF with text highlighting and reviewer remarks, block redactions and 'this page', must not eliminate any text characters unintentionally in the produced document
			 * 
			 */
			@Test(description="RPMXCON-48315",enabled  = true,groups = { "regression" })
			public void verifyProducedTiffWithTextReviewRemarkRedaction() throws Exception {

				UtilityLog.info(Input.prodPath);
				base.stepInfo("RPMXCON-48315 -Production Component");
				base.stepInfo("Verify that Producing a TIFF with text highlighting and reviewer remarks, block redactions and 'this page', must not eliminate any text characters unintentionally in the produced document");
				
				loginPage.logout();
				loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
				
				String foldername = "Folder" + Utility.dynamicNameAppender();
				String tagname = "Tag" + Utility.dynamicNameAppender();
				String productionname = "p" + Utility.dynamicNameAppender();
				String Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();
				String prefixID = Input.randomText + Utility.dynamicNameAppender();
				String suffixID = Input.randomText + Utility.dynamicNameAppender();
				
				
				TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
				tagsAndFolderPage.createNewTagwithClassificationInRMU(tagname, "Select Tag Classification");
				tagsAndFolderPage.CreateFolderInRMU(foldername);
				
				RedactionPage redactionpage=new RedactionPage(driver);
		        driver.waitForPageToBeReady();
		        redactionpage.manageRedactionTagsPage(Redactiontag1);
				
				SessionSearch sessionSearch = new SessionSearch(driver);
				int docCount =sessionSearch.basicContentSearch(Input.testData1);
				sessionSearch.bulkFolderExisting(foldername);
				sessionSearch.bulkTagExisting(tagname);
				sessionSearch.ViewInDocViewWithoutPureHit();
				
				DocViewRedactions docViewRedactions=new DocViewRedactions(driver);
				DocViewPage docView = new DocViewPage(driver);
				docView.documentSelection(docCount);
	            driver.waitForPageToBeReady();
	            docViewRedactions.RedactTextInDocView(10,10,20,20);
	            driver.waitForPageToBeReady();
	            docViewRedactions.selectingRedactionTag2(Redactiontag1);
	            
				ProductionPage page = new ProductionPage(driver);
				page = new ProductionPage(driver);
				String beginningBates = page.getRandomNumber(2);
				page.selectingDefaultSecurityGroup();
				page.addANewProduction(productionname);
				page.fillingDATSection();
				page.fillingNativeSection();
				page.fillingTIFFSectionBurnRedaction(Redactiontag1, "REDACTED");
				page.fillingTextSection();
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
				String home = System.getProperty("user.home");
				File Native = new File(home + "/Downloads/VOL0001/Natives/");
				page.extractFile();
				page.isdatFileExist();
				page.isfileisExists(Native);
				page.OCR_Verification_In_Generated_Tiff_tess4j(prefixID, suffixID, beginningBates);
				
				base.passedStep("Verified that Producing a TIFF with text highlighting and reviewer remarks, block redactions and 'this page', must not eliminate any text characters unintentionally in the produced document");
				
				tagsAndFolderPage = new TagsAndFoldersPage(driver);
				this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
				tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);	
				tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
				loginPage.logout();
				
			}
			/**
			 * @author Aathith Test case id-RPMXCON-48494
			 * @Description To verify that If user select RedactionTag and if Audio document is associated to the selected Redaction Tag then Native should not produced
			 * 
			 */
			@Test(description="RPMXCON-48494",enabled = true,groups = { "regression" })
			public void verifyRedactionTagforAudioDoc() throws Exception {

				UtilityLog.info(Input.prodPath);
				base.stepInfo("RPMXCON-48494-Production Component");
				base.stepInfo("To verify that If user select RedactionTag and if Audio document is associated to the selected Redaction Tag then Native should not produced");
				
				String foldername = "Folder" + Utility.dynamicNameAppender();
				String tagname = "Tag" + Utility.dynamicNameAppender();
				String productionname = "p" + Utility.dynamicNameAppender();
				String Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();
				String prefixID = Input.randomText + Utility.dynamicNameAppender();
				String suffixID = Input.randomText + Utility.dynamicNameAppender();
				
				
				TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
				tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");
				tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
				
		        SessionSearch sessionSearch = new SessionSearch(driver);
				sessionSearch.SearchMetaData("IngestionName", "B2F9_Automation_AllSources_20211130043120500");
				sessionSearch.selectOperatorInBasicSearch("AND");
				sessionSearch.SearchMetaDataWithoutUrlPassing("AudioPlayerReady", "1");
				sessionSearch.addPureHit();
				sessionSearch.bulkFolderExisting(foldername);
				sessionSearch.bulkTagExisting(tagname);
	            
	            loginPage.logout();
				loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
				
				RedactionPage redactionpage=new RedactionPage(driver);
		        driver.waitForPageToBeReady();
		        redactionpage.manageRedactionTagsPage(Redactiontag1);
				
				tagsAndFolderPage.selectFolderViewInDocView(foldername);
				DocViewRedactions docViewRedactions=new DocViewRedactions(driver);
				docViewRedactions.deleteAllAppliedRedactions();
				driver.waitForPageToBeReady();
	            docViewRedactions.clickOnAddRedactionForAudioDocument();
	            docViewRedactions.addAudioRedaction(Input.startTime, Input.endTime, Redactiontag1);
	            
				ProductionPage page = new ProductionPage(driver);
				page = new ProductionPage(driver);
				String beginningBates = page.getRandomNumber(2);
				int firstFile = Integer.parseInt(beginningBates);
				page.selectingDefaultSecurityGroup();
				page.addANewProduction(productionname);
				page.fillingDATSection();
				page.fillingNativeSection();
				page.fillingTIFFSectionBurnRedaction(Redactiontag1, "REDACTED");
				page.fillingTextSection();
				page.fillingMP3FileWithBurnRedaction(Redactiontag1);
				page.navigateToNextSection();
				page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
				page.navigateToNextSection();
				page.fillingDocumentSelectionWithTag(tagname);
				page.navigateToNextSection();
				page.fillingPrivGuardPage();
				page.fillingProductionLocationPage(productionname);
				page.navigateToNextSection();
				page.fillingSummaryAndPreview();
				int doccount =page.fillingGeneratePageWithContinueGenerationPopup();
				int lastFile = firstFile + doccount;
				String home = System.getProperty("user.home");
				File Native = new File(home + "/Downloads/VOL0001/Natives/"+prefixID+beginningBates+suffixID+".MP3");
				driver.waitForPageToBeReady();
				
				page.waitForFileDownload();
				page.extractFile();
				
				page.isMp3FileExist(firstFile, lastFile, prefixID, suffixID);
				
				if (Native.exists()) {
		            System.out.println(" file is Exists in pointed directory");
		            base.failedStep(Native+" file is Exists in pointed directory");
		            }
		        else {
		            base.passedStep("Natives is not be produced for MP3 if it is associated to selected Redaction Tag ");
		        }
				
				base.passedStep("verifed that If user select RedactionTag and if Audio document is associated to the selected Redaction Tag then Native should not produced");
				
				loginPage.logout();
				
			}
			/**
			 * @author Aathith.Senthilkumar
			 * 			RPMXCONO-47172
			 * @Description Verify that PDF files should be copied to folder when 'Split Sub Folders' is OFF with split count as 10
			 */
			@Test(description="RPMXCON-47172",enabled = true,groups = { "regression" })
			public void verifyPdfFileAreNotSplit() throws Exception {
				
				UtilityLog.info(Input.prodPath);
				base.stepInfo("RPMXCON-47172 -Production");
				base.stepInfo("Verify that PDF files should be copied to folder when 'Split Sub Folders' is OFF with split count as 10");
				foldername = "FolderProd" + Utility.dynamicNameAppender();
				tagname = "Tag" + Utility.dynamicNameAppender();
				String prefixID = Input.randomText + Utility.dynamicNameAppender();
				String suffixID = Input.randomText + Utility.dynamicNameAppender();
				
				// Pre-requisites
				// create tag and folder
				TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
				this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
				tagsAndFolderPage.CreateTagwithClassification(tagname, "Privileged");
				tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
				
				// search for folder
				SessionSearch sessionSearch = new SessionSearch(driver);
				sessionSearch = new SessionSearch(driver);
				sessionSearch.basicContentSearch(Input.telecaSearchString);
				sessionSearch.bulkTagExisting(tagname);
				sessionSearch.bulkFolderExisting(foldername);
				
				//Verify archive status on Gen page
				ProductionPage page = new ProductionPage(driver);
				productionname = "p" + Utility.dynamicNameAppender();
				String beginningBates = page.getRandomNumber(2);
				page.selectingDefaultSecurityGroup();
				page.addANewProduction(productionname);
				page.fillingDATSection();
				page.fillingNativeSection();
				page.fillingPDFSectionwithNativelyPlaceholder(tagname);
				page.fillingTextSection();
				page.navigateToNextSection();
				page.visibleCheck("Numbering and Sorting");
				page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
				page.navigateToNextSection();
				page.fillingDocumentSelectionPage(foldername);
				page.navigateToNextSection();
				page.visibleCheck("Priv Guard");
				page.fillingPrivGuardPage();
				page.visibleCheck("Production Location");
				driver.scrollingToBottomofAPage();
				page.getsplitSubFolderbtn().waitAndClick(10);
				base.stepInfo("split sub folder toggle as OFF");
				driver.scrollPageToTop();
				page.fillingProductionLocationPage(productionname);
				page.navigateToNextSection();
				page.visibleCheck("Summary & Preview");
				page.fillingSummaryAndPreview();
				page.fillingGeneratePageWithContinueGenerationPopup();
				page.extractFile();
				String home = System.getProperty("user.home");
				
				File dirPdf = new File(home+"\\Downloads\\VOL0001\\PDF");
				File dirNative = new File(home+"\\Downloads\\VOL0001\\Natives");
				File dirText = new File(home+"\\Downloads\\VOL0001\\Text");
				softAssertion = new SoftAssert();
				int dirPdfCount = page.dirFoldersCount(dirPdf);
				int dirTextCount = page.dirFoldersCount(dirText);
				softAssertion.assertEquals(1, dirPdfCount);
				softAssertion.assertEquals(1, dirTextCount);
				softAssertion.assertEquals(1, dirNative);
				base.passedStep("files is not split as 'Split Folder' toggle was OFF");
				base.passedStep("Verified that PDF files should be copied to folder when 'Split Sub Folders' is OFF with split count as 10");
				
				page.deleteFiles();
				tagsAndFolderPage = new TagsAndFoldersPage(driver);
				this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
				tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
				tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
				loginPage.logout();
			}
			/**
			 * @author Aathith Test case id-RPMXCON-48316
			 * @Description Verify the production for Audio files which includes the Audio redaction extend to end of the audio file 
			 * 
			 */
			@Test(description="RPMXCON-48316",enabled = true,groups = { "regression" })
			public void verifyProductionMp3File() throws Exception {

				UtilityLog.info(Input.prodPath);
				base.stepInfo("RPMXCON-48316 -Production Component");
				base.stepInfo("Verify the production for Audio files which includes the Audio redaction extend to end of the audio file");
				
				String foldername = "Folder" + Utility.dynamicNameAppender();
				String tagname = "Tag" + Utility.dynamicNameAppender();
				String productionname = "p" + Utility.dynamicNameAppender();
				String Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();
				String prefixID = Input.randomText + Utility.dynamicNameAppender();
				String suffixID = Input.randomText + Utility.dynamicNameAppender();
				
				BaseClass base = new BaseClass(driver);
				base.selectproject(Input.additionalDataProject);

				TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
				tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");
				tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
				
				DataSets dataset = new DataSets(driver);
				base.stepInfo("Navigating to dataset page");
				dataset.navigateToDataSetsPage();
				base.stepInfo("Selecting uploadedset and navigating to doclist page");
				dataset.selectDataSetWithName("AudioEndFile");
				DocListPage doc = new DocListPage(driver);
				driver.waitForPageToBeReady();

				doc.selectAllDocs();
				doc.docListToBulkRelease(Input.securityGroup);
				doc.bulkTagExistingFromDoclist(tagname);
				doc.selectAllDocs();
				doc.bulkFolderExisting(foldername);
				
				loginPage.logout();
				loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
				base.selectproject(Input.additionalDataProject);
				
				RedactionPage redactionpage=new RedactionPage(driver);
		        driver.waitForPageToBeReady();
		        redactionpage.manageRedactionTagsPage(Redactiontag1);
				
				tagsAndFolderPage = new TagsAndFoldersPage(driver);
				tagsAndFolderPage.selectFolderViewInDocView(foldername);
				
				DocViewRedactions docViewRedactions=new DocViewRedactions(driver);
				DocViewPage docView = new DocViewPage(driver);
				driver.waitForPageToBeReady();
				docView.documentSelection(3);
	            docViewRedactions.deleteAllAppliedRedactions();
				driver.waitForPageToBeReady();
	            docViewRedactions.clickOnAddRedactionForAudioDocument();
	            docViewRedactions.addAudioRedaction(Input.startTime, Input.endTime, Redactiontag1);

				ProductionPage page = new ProductionPage(driver);
				page = new ProductionPage(driver);
				String beginningBates = page.getRandomNumber(2);
				int firstFile = Integer.parseInt(beginningBates);
				page.selectingDefaultSecurityGroup();
				page.addANewProduction(productionname);
				page.fillingDATSection();
				page.fillingMP3FileWithBurnRedaction(Redactiontag1);
				page.navigateToNextSection();
				page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
				page.navigateToNextSection();
				page.fillingDocumentSelectionWithTag(tagname);
				page.navigateToNextSection();
				page.fillingPrivGuardPage();
				page.fillingProductionLocationPage(productionname);
				page.navigateToNextSection();
				page.fillingSummaryAndPreview();
				int doccount =page.fillingGeneratePageWithContinueGenerationPopup();
				int lastFile = firstFile + doccount;
				page.waitForFileDownload();
				page.extractFile();
				
				page.isMp3FileExist(firstFile, lastFile, prefixID, suffixID);
				base.passedStep("Verified the production for Audio files which includes the Audio redaction extend to end of the audio file");
				
				page.deleteFiles();
				tagsAndFolderPage = new TagsAndFoldersPage(driver);
				this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
				tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
				tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
				loginPage.logout();
				
			}
			/**
			 * @author Aathith.Senthilkumar
			 * @Description :To verify that in Production, if sorting option is Sort by
			 *              Selected Tags and 'Keep Families Together' check box is selected
			 *              then produced document should be sorted by Tags with FamilyID [
			 *              RPMXCON-49228]
			 * @throws Exception
			 */
			@Test(description="RPMXCON-49228",enabled = true, groups = { "regression" })
			public void verifySortByTags() throws Exception {

				TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
				SessionSearch sessionSearch = new SessionSearch(driver);
				ProductionPage page = new ProductionPage(driver);

				String tagname = "TAG" + Utility.dynamicNameAppender();
				String foldername = "prodFolder" + Utility.dynamicNameAppender();
				String productionname = "p" + Utility.dynamicNameAppender();
				String beginningBates = page.getRandomNumber(2);
				String prefixID = Input.randomText + Utility.dynamicNameAppender();
				String suffixID = Input.randomText + Utility.dynamicNameAppender();

				base.stepInfo("Test case Id: RPMXCON-49228 Production Sprint 13");
				base.stepInfo(
						"To verify that in Production, if sorting option is Sort by Selected Tags and 'Keep Families Together' check box is selected then produced document should be sorted by Tags with FamilyID");

				// create tag and folder
				tagsAndFolderPage.navigateToTagsAndFolderPage();
				tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
				tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
				
				// search for folder
				sessionSearch.basicContentSearch(Input.telecaSearchString);
				sessionSearch.bulkFolderExisting(foldername);
				sessionSearch.ViewInDocListWithOutPureHit();
				
				DocListPage doclist = new DocListPage(driver);
				doclist.selectFirstParentDocumentWithChildDocument();
				String docId = doclist.getParentDocumetId();
				System.out.println(docId);
				driver.scrollPageToTop();
				doclist.bulkTagExistingFromDoclist(tagname);

				base.stepInfo("Starting the production");
				page.navigateToProductionPage();
				page.selectingDefaultSecurityGroup();
				page.addANewProduction(productionname);
				page.fillingDATSection();
				page.addNewFieldOnDAT();
				page.addDatField(1, "Doc Basic", "DocID");
				page.fillingNativeSection();
				page.fillingTiffSectionDisablePrivilegedDocs();
				page.fillingTextSection();
				page.advancedProductionComponentsTranslations();
				page.navigateToNextSection();
				page.fillingNumberingAndSortingPageWithSortByTags(prefixID, suffixID, beginningBates, tagname);
				page.navigateToNextSection();
				page.fillingDocumentSelectionPage(foldername);
				page.navigateToNextSection();
				page.fillingPrivGuardPage();
				page.fillingProductionLocationPage(productionname);
				page.navigateToNextSection();
				page.fillingSummaryAndPreview();
				page.fillingGeneratePageWithContinueGenerationPopup();
				page.waitForFileDownload();
				page.extractFile();
				
				driver.waitForPageToBeReady();
				String home = System.getProperty("user.home");
				String name = page.getProduction().getText().trim();
				driver.waitForPageToBeReady();
				File DatFile = new File(home + "/Downloads/VOL0001/Load Files/" + name + "_DAT.dat");
				if (DatFile.exists()) {
				base.passedStep("Dat file is exists in generated production");
				} else {
				base.failedStep("Dat file is not displayed as expected");
				}

				String line;
				List<String> lines = new ArrayList<>();
				BufferedReader brReader = new BufferedReader(new InputStreamReader(new FileInputStream(DatFile), "UTF16"));
				while ((line = brReader.readLine()) != null) {
				lines.add(line);
				}
				for (String a : lines) {
				System.out.println(a);
				}

				System.out.println("secount row value : "+lines.get(1));
				if (lines.get(1).contains(docId)) {
				base.passedStep("Document is sorted as per order");
				} else {
				base.failedStep("failed");
				}
				
				brReader.close();
				
				base.passedStep("verified that in Production, if sorting option is Sort by Selected Tags and 'Keep Families Together' check box is selected then produced document should be sorted by Tags with FamilyID");
				page.deleteFiles();
				
				// Delete Tag and folder
				tagsAndFolderPage.navigateToTagsAndFolderPage();
				tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
				tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
				loginPage.logout();
			}
			/**
			 * @author Aathith Test case id-RPMXCON-49781
			 * @Description Verify that if document is produced  and if user rotate the redacted images then after copying to any other file then redacted image should not be displayed 
			 * 
			 */
			@Test(description="RPMXCON-49781",enabled = true,groups = { "regression" })
			public void verifyAfterRotationRedactionNotDisplayed() throws Exception {

				UtilityLog.info(Input.prodPath);
				base.stepInfo("RPMXCON-49781 -Production Component");
				base.stepInfo("Verify that if document is produced  and if user rotate the redacted images then after copying to any other file then redacted image should not be displayed");
				
				String foldername = "Folder" + Utility.dynamicNameAppender();
				String tagname = "Tag" + Utility.dynamicNameAppender();
				String productionname = "p" + Utility.dynamicNameAppender();
				String Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();
				String prefixID = Input.randomText + Utility.dynamicNameAppender();
				String suffixID = Input.randomText + Utility.dynamicNameAppender();
				
				BaseClass base = new BaseClass(driver);
				base.selectproject(Input.additionalDataProject);

				TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
				tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");
				tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
				
				DataSets dataset = new DataSets(driver);
				base.stepInfo("Navigating to dataset page");
				dataset.navigateToDataSetsPage();
				base.stepInfo("Selecting uploadedset and navigating to doclist page");
				dataset.selectDataSetWithName("RPMXCON39718");
				DocListPage doc = new DocListPage(driver);
				driver.waitForPageToBeReady();

				doc.documentSelection(3);
				doc.docListToBulkRelease(Input.securityGroup);
				doc.bulkTagExistingFromDoclist(tagname);
				doc.documentSelection(3);
				doc.bulkFolderExisting(foldername);
				
				loginPage.logout();
				loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
				base.selectproject(Input.additionalDataProject);
				
				RedactionPage redactionpage=new RedactionPage(driver);
		        driver.waitForPageToBeReady();
		        redactionpage.manageRedactionTagsPage(Redactiontag1);
				
				tagsAndFolderPage = new TagsAndFoldersPage(driver);
				tagsAndFolderPage.selectFolderViewInDocView(foldername);
				
				DocViewRedactions docViewRedactions=new DocViewRedactions(driver);
				DocViewPage docView = new DocViewPage(driver);
				docView.documentSelection(3);
	            driver.waitForPageToBeReady();
	            docViewRedactions.redactRectangleUsingOffset(10,10,20,20);
	            driver.waitForPageToBeReady();
	            docViewRedactions.selectingRedactionTag2(Redactiontag1);

				ProductionPage page = new ProductionPage(driver);
				page = new ProductionPage(driver);
				String beginningBates = page.getRandomNumber(2);
				String firstDocument = prefixID+beginningBates+suffixID;
				page.selectingDefaultSecurityGroup();
				page.addANewProduction(productionname);
				page.fillingDATSection();
				page.fillingNativeSection();
				page.fillingPDFSectionwithNativelyPlaceholder(tagname);
				page.selectBurnRedaction(Redactiontag1);
				page.fillingTextSection();
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
				page.waitForFileDownload();
				page.extractFile();
				
				String home = System.getProperty("user.home");
				File source = new File(home+"\\Downloads\\VOL0001\\PDF\\0001\\"+firstDocument+".pdf");
				File dest = new File(home+"\\Downloads\\CopiedPdf.pdf");
				
				page.copyFileUsingStream(source, dest);
				int pageCount = page.pdfToJpgConverter(dest);
				page.RotatePdfFile(dest, pageCount);
				
				PDDocument document = PDDocument.load(dest);
				if (!document.isEncrypted()) {
				    PDFTextStripper stripper = new PDFTextStripper();
				    String text = stripper.getText(document);
				    System.out.println("Text:" + text);
				    if(!text.contains("RED")) {
				    	base.passedStep("Redacted area is not displayed");
				    }else {
				    	base.failedStep("Redacted area displayed");
				    }
				}
				document.close();					
				base.passedStep("Verified that if document is produced  and if user rotate the redacted images then after copying to any other file then redacted image should not be displayed");
				
				page.deleteFiles();
				loginPage.logout();
				
			}
     
     
	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		base = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
			loginPage.logoutWithoutAssert();
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
//		try {
//			loginPage.quitBrowser();
//		} catch (Exception e) {
//			loginPage.quitBrowser();
//
//		}
	}

}
