
package sightline.productions;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;
import javax.imageio.ImageIO;
import net.sourceforge.tess4j.util.LoadLibs;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import net.sourceforge.tess4j.Tesseract1;
import pageFactory.BaseClass;
import pageFactory.BatchPrintPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.DomainDashboard;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.ProjectFieldsPage;
import pageFactory.ProjectPage;
import pageFactory.RedactionPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Production_Phase2_Regression3 {
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
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		 page = new ProductionPage(driver);
		 base = new BaseClass(driver);
		 softAssertion = new SoftAssert();
		 tagsAndFolderPage = new TagsAndFoldersPage(driver);
		 sessionSearch = new SessionSearch(driver);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
	}

	@DataProvider(name = "Users")
	public Object[][] users() {
		Object[][] users = { { Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password } };
		return users;
	}

	/**
	 * @author Brundha RPMXCON-48178
	 * @Description To Verify For Multimedia file group native Generation without
	 *              any Priv Tag Classification.
	 */
	@Test(description = "RPMXCON-48178", enabled = true, groups = { "regression" })

	public void verifyingGenerationOfMp3File() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48178 -Production component");
		base.stepInfo("To Verify For Multimedia file group native Generation without any Priv Tag Classification.");

		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "P" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.SearchMetaData("SourceDocID", Input.fileGroup);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionPrivDocs(tagname, Input.tagNameTechnical);
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
		File NativeFile = new File(home + "/Downloads/VOL0001/Load Files/" + productionname + "_Native.lst");

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
		base.waitTime(2);
		if (NativeFile.exists()) {
			base.passedStep("Native file is generated as expected");
		} else {
			base.failedStep("Native file is not generated as expected");
		}

		loginPage.logout();

	}

	/**
	 * @author Brundha RPMXCON-49336
	 * @Description To verify that 'Tech Issue placeholdering' is available on
	 *              Tiff/PDF on Production-Component section
	 */
	@Test(description = "RPMXCON-49336", enabled = true, groups = { "regression" })

	public void verifyTechIssuePlaceholderInComponentTab() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49336 -Production component");
		base.stepInfo(
				"To verify that 'Tech Issue placeholdering' is available on Tiff/PDF on Production-Component section");
		String productionname = "P" + Utility.dynamicNameAppender();

		ProductionPage page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.getTIFFTab().waitAndClick(5);
		driver.scrollingToElementofAPage(page.getTechissue_toggle());
		base.ValidateElement_Presence(page.getTechissue_toggle(), "TechIssue Toggle");
		page.getTechissue_toggle().waitAndClick(10);
		base.ValidateElement_Presence(page.getTechissue_SelectTagButton(), "TechIssue Select Tags");
		base.ValidateElement_Presence(page.getTechIssuePlaceHolder(), "TechIssue Paceholder");
		base.ValidateElement_Presence(page.getTechInsertMetadataField(), "TechIssue Insert Metadata Field");
		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-48162
	 * @Description To Verify Availability of MP3 Files Option In Production
	 *              Component Section under Advance Production Component.
	 */
	@Test(description = "RPMXCON-48162", enabled = true, groups = { "regression" })
	public void asVerifyProductionGeneratedwithNonRedactedArea() throws Exception {

		UtilityLog.info(Input.prodPath);

		base.stepInfo("RPMXCON-48162 -Production component");
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo(
				"To Verify Availability of MP3 Files Option In Production Component Section under Advance Production Component.");

		String redactiontag = "Redaction" + Utility.dynamicNameAppender();
		String tagname = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");

		RedactionPage redactionpage = new RedactionPage(driver);
		driver.waitForPageToBeReady();

		redactionpage.manageRedactionTagsPage(redactiontag);
		System.out.println("First Redaction Tag is created" + redactiontag);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.audioSearch(Input.audioSearch, Input.audioLanguage);
		driver.waitForPageToBeReady();
		sessionSearch.ViewInDocView();

		DocViewRedactions redact = new DocViewRedactions(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		docViewPage.navigateToDocViewPageURL();
		base.waitTime(2);
		docViewPage.documentSelection(3);

		redact.deleteAllAppliedRedactions();
		driver.waitForPageToBeReady();
		redact.clickOnAddRedactionForAudioDocument();
		redact.addAudioRedaction(Input.startTime, Input.endTime, redactiontag);

		driver.waitForPageToBeReady();
		docViewPage.getViewDocAllList().waitAndClick(10);
		driver.waitForPageToBeReady();
		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(1);
		doclist.bulkTagExisting(tagname);

		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.burnRedactionWithRedactionTagInTiffSection(redactiontag);
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
		File file = new File(home + "/Downloads/VOL0001/Natives/0001");
		File imageFile = new File(
				home + "/Downloads/VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tif");
		page.isfileisExists(imageFile);
		if (file.exists()) {
			base.passedStep("Mp3 document generatd successfully");
		} else {
			base.failedStep("Document not generated");
		}

		loginPage.logout();
	}

	/**
	 * @author Brundha Test case id-RPMXCON-49238
	 * @Description To verify that on clicking on link, it displays next available
	 *              bates number in all patterns
	 * 
	 */
	@Test(description = "RPMXCON-49238", enabled = true, groups = { "regression" })

	public void verifyClickHereLinkInSortingPage() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49238 -Production Component");
		base.stepInfo("To verify that on clicking on link, it displays next available bates number in all patterns");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String productionname1 = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		driver.waitForPageToBeReady();
		page.fillingGeneratePageWithContinueGenerationPopup();

		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname1);
		page.fillingDATSection();
		page.navigateToNextSection();
		driver.waitForPageToBeReady();
		page.verifyAvailbleLinkAtNumberingAndSorting();
		page.getClickHereLink().waitAndClick(5);
		driver.waitForPageToBeReady();
		base.ValidateElement_Presence(page.NextBatesNumberPopup(), "Next Bates Number Popup");
		base.ValidateElement_Presence(page.getNextBatesNumberInSortingPage(), "Next Bates Number");
		base.ValidateElement_Presence(page.getNextBatesNumber(), "Select Button");

		loginPage.logout();
	}

	/**
	 * @author Brundha Test case id-RPMXCON-47941
	 * @Description To Verify Place holder should get generated for selected File
	 *              Types.
	 * 
	 */
	@Test(description = "RPMXCON-47941", enabled = true, groups = { "regression" })

	public void verifyFileTypeInGeneratedProduction() throws Exception {

		UtilityLog.info(Input.prodPath);

		base.stepInfo("RPMXCON-47941 -Production Component");
		base.stepInfo("To Verify Place holder should get generated for selected File Types.");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		int pureHit = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int firstFile = Integer.valueOf(beginningBates);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingNativePlaceHolderFileTypeInTiffSection(Input.searchString1);
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
		base.waitTime(2);
//		page.deleteFiles();
		page.extractFile();
		driver.waitForPageToBeReady();
		File dir = new File(home + "/Downloads/VOL0001/Natives/0001/");
		File[] dir_contents = dir.listFiles();
		System.out.println(dir_contents.length);
		int nativefile = dir_contents.length;

		if (pureHit != nativefile) {
			base.passedStep("Placeholder is generated for selected File Types.");
		} else {
			base.failedStep("Placeholder is not generated for selected filetype");
		}
		page.OCR_Verification_In_Generated_Tiff_tess4j(firstFile, nativefile, prefixID, suffixID, Input.searchString1);
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Brundha Date:7/15/2022 TestCase Id:RPMXCON-49246
	 * @Description:Verify that when production components step is completed then
	 *                     DAT component should retain the 'TIFFPageCount'
	 */
	@Test(description = "RPMXCON-49246", enabled = true, groups = { "regression" })
	public void verifyDatFiledWithTiffPageCount() throws Exception {
		UtilityLog.info(Input.prodPath);

		base.stepInfo("Test case id : RPMXCON-49246 from production component ");
		base.stepInfo(
				"Verify that when production components step is completed then DAT component should retain the 'TIFFPageCount'");

		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.addDATFieldAtSecondRow(Input.productionText, Input.tiffPageCountNam, Input.tiffPageCountText);
		page.fillingTIFFSectionPrivDocs(tagname, Input.searchString1);
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
		String name = page.getProduction().getText().trim();
		driver.waitForPageToBeReady();
		File DatFile = new File(home + "/Downloads/VOL0001/Load Files/" + name + "_DAT.dat");
		if (DatFile.exists()) {
			base.passedStep("Dat file is exists in generated production");
		} else {
			base.failedStep("Dat file is not displayed as expected");
		}

		int TiffPageCount = 1;
		String line;
		List<String> lines = new ArrayList<>();
		BufferedReader brReader = new BufferedReader(new InputStreamReader(new FileInputStream(DatFile), "UTF16"));
		while ((line = brReader.readLine()) != null) {
			lines.add(line);
		}
		System.out.println(lines.get(1));
		String[] arrOfStr = lines.get(1).split("þ");
		String output = arrOfStr[3];
		if (TiffPageCount == Integer.parseInt(output)) {
			base.passedStep("Tiff page count is displayed as expected");
		} else {
			base.failedStep("verification failed");
		}

		brReader.close();

		loginPage.logout();
	}

	/**
	 * @author Brundha.T Date:7/15/2022 TestCase Id :RPMXCON-48029 Description :To
	 *         Verify by default "No Rotation" is selected for the pages in TIFFing.
	 * 
	 */
	@Test(description = "RPMXCON-48029", enabled = true, groups = { "regression" })
	public void verifyingDefaultNoRotationOptionInTiffSection() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48029 -Production Component");
		base.stepInfo("To Verify by default 'No Rotation' is selected for the pages in TIFFing.");

		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		BaseClass base = new BaseClass(driver);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.SearchMetaData(Input.sourceDocIdSearch, Input.sourceDocument);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectGenerateOption(false);
		page.fillingNativeDocsPlaceholder(tagname);
		driver.scrollPageToTop();
		page.getRotationDropDown();
		Select select = new Select(page.getRotationDropDown().getWebElement());
		String option = select.getFirstSelectedOption().getText();
		System.out.println("the value is " + option);
		base.textCompareEquals(option, "No Rotation", "No Rotation option is selected by default in Tiff Section",
				"No Rotation option is not selecetd by default");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Brundha.T Date:7/15/2022 TestCase Id :RPMXCON-48024 Description :To
	 *         Verify Generated TIFF and PDF (Page Content) should be correct.
	 * 
	 */
	@Test(description = "RPMXCON-48024", enabled = true, groups = { "regression" })
	public void verifyingTheTiffPageContentInGeneratedProduction() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48024 -Production Component");
		base.stepInfo("To Verify Generated TIFF and PDF (Page Content) should be correct.");
		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		BaseClass base = new BaseClass(driver);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		int PureHit = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectGenerateOption(false);
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
		base.waitTime(2);
		page.deleteFiles();
		page.extractFile();
		driver.waitForPageToBeReady();
		File dir = new File(home + "/Downloads/VOL0001/Images/0001/");
		File[] dir_contents = dir.listFiles();
		System.out.println(dir_contents.length);
		int TiffFile = dir_contents.length;

		if (PureHit < TiffFile) {
			base.passedStep("Tiff image is generated for all pages");
		} else {
			base.failedStep("Tiff image is not generated for all pages");
		}
		String firstFile = prefixID + beginningBates + suffixID;
		File file = new File(home + "/Downloads/VOL0001/Images/0001/" + firstFile + ".tif");
		page.isfileisExists(file);
		page.OCR_Verification_In_Generated_Tiff_tess4j(file, "Crammer");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);

		loginPage.logout();

	}

	/**
	 * @author Brundha.T TestCase Id:RPMXCON-63189 Date:7/19/2022
	 * @Description:Verify that template should display with default native
	 *                     placeholder by default under TIFF/PDF section and
	 *                     production should be generated successfully using same
	 *                     template
	 **/

	@Test(description = "RPMXCON-63189", enabled = true, groups = { "regression" })
	public void verifyTheProductionGenerationUsingTemplate() throws Exception {

		base = new BaseClass(driver);
		base.stepInfo("Test case Id:RPMXCON-63189- Production component");
		base.stepInfo(
				"Verify that template should display with default native placeholder by default under TIFF/PDF section and production should be generated successfully using same template");
		UtilityLog.info(Input.prodPath);

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "P" + Utility.dynamicNameAppender();
		String prefixID1 = "P" + Utility.dynamicNameAppender();
		String suffixID = "S" + Utility.dynamicNameAppender();
		String suffixID2 = "S" + Utility.dynamicNameAppender();
		String Templatename = "Temp" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");

		SessionSearch sessionSearch = new SessionSearch(driver);
		this.driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.addNewSearch();
		sessionSearch.SearchMetaData(Input.docFileType, Input.MetaDataFileType);
		sessionSearch.addPureHit();

		sessionSearch.ViewInDocList();
		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(6);
		doclist.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		driver.waitForPageToBeReady();
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProductionAndSave(productionname);
		page.fillingDATSection();
		page.verifyingTheDefaultSelectedOptionInNative();
		page.verifyingNativeSectionFileType(Input.MetaDataFileType);
		page.verifyingNativeSectionFileType(Input.NativeFileType);
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
		page = new ProductionPage(driver);
		String productionname1 = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectSavedTemplateAndManageTemplate(productionname, Templatename);
		page.verifyingComponentTabSection();
		driver.waitForPageToBeReady();
		page = new ProductionPage(driver);
		page.navigateToProductionPage();
		page.baseInfoLoadTemplate(productionname1, Templatename);
		page.getCheckBoxCheckedVerification(page.chkIsDATSelected());
		page.getCheckBoxCheckedVerification(page.chkIsTIFFSelected());
		page.verifyingTheDefaultSelectedOptionInNative();
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID1, suffixID2, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname1);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Brundha Test case id-RPMXCON-47967 Date:7/19/2022
	 * @Description To Verify Native Path are populated in an Export/Production
	 * 
	 */
	@Test(description = "RPMXCON-47967", enabled = true, groups = { "regression" })

	public void verifyNativePathInGenerationOfDATFile() throws Exception {

		UtilityLog.info(Input.prodPath);

		base.stepInfo("RPMXCON-47967 -Production Component");
		base.stepInfo("To Verify Native Path are populated in an Export/Production");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.addDATFieldAtSecondRow(Input.DatFieldClassification, Input.DatSourceClassification, Input.randomText);
		page.fillingNativeSection();
		page.fillingTIFFSectionwithNativelyPlaceholder(tagname);
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
		base.waitTime(2);
		page.extractFile();
		driver.waitForPageToBeReady();
		File DatFile = new File(home + "/Downloads/VOL0001/Load Files/" + productionname + "_DAT.dat");
		page.isdatFileExist();

		String line;
		List<String> lines = new ArrayList<>();
		BufferedReader brReader = new BufferedReader(new InputStreamReader(new FileInputStream(DatFile), "UTF16"));
		while ((line = brReader.readLine()) != null) {
			lines.add(line);
		}
		for (String a : lines) {
			System.out.println(a);
		}
		String NativePath = "Z:\\VOL0001\\Natives\\0001";
		base.compareTextViaContains(lines.get(4), NativePath, "Native path is dispalyed as expected",
				"Native path is not displayed in DAT file");
		brReader.close();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Brundha Test case id-RPMXCON-47940 Date:7/19/2022
	 * @Description To Verify in production, branding based on the tag types
	 * 
	 */

	@Test(description = "RPMXCON-47940", enabled = true, groups = { " regression" })

	public void tiffSectionWithBrandingTags() throws Exception {
		base.stepInfo("Test case Id: RPMXCON-47940- Production Component");

		UtilityLog.info(Input.prodPath);
		base.stepInfo("#### To Verify in production, branding based on the tag types ####");

		String tagname = "tag" + Utility.dynamicNameAppender();
		String productionname = "production" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		int PureHit = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int FirstFile = Integer.valueOf(beginningBates);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.verifyTheTagOnLeftBranding(tagname, Input.tagNamePrev);
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
		int LastFile = PureHit + FirstFile;
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		driver.waitForPageToBeReady();
		File TiffFile = new File(
				home + "/Downloads/VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tif");
		if (TiffFile.exists()) {
			base.passedStep("Tiff  file is displayed as expected");
		} else {
			base.failedStep("Tiff file is not displayed as expected");
		}

		page.OCR_Verification_In_Generated_Tiff_tess4j(FirstFile, LastFile, prefixID, suffixID, Input.tagNamePrev);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Brundha.T TestCase Id :RPMXCON-47943 Date:7/19/2022 Description :To
	 *         Verify Include Families Doc counts should get Generated in Production
	 * 
	 * 
	 */
	@Test(description = "RPMXCON-47943", enabled = true, groups = { "regression" })
	public void asVerifyingIncludingFamilyDocsProducedInGenratedFile() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47943 -Production Component");
		base.stepInfo("To Verify Include Families Doc counts should get Generated in Production");
		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		BaseClass base = new BaseClass(driver);

		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		int PureHit = sessionSearch.basicContentSearch(Input.telecaSearchString);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionPrivDocs(tagname, Input.tagNamePrev);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		driver.scrollingToBottomofAPage();
		base.waitForElement(page.getIncludeFamilies());
		page.getIncludeFamilies().Click();
		driver.scrollPageToTop();
		page.getMarkCompleteLink().waitAndClick(10);
		base.waitTime(2);
		String DocCount = page.getDocumentSelectionLink().getText();
		System.out.println(DocCount);
		if (Integer.valueOf(DocCount) != PureHit) {
			base.passedStep("Family Documents are included");
		} else {
			base.failedStep("Family Documents are not included");
		}
		driver.scrollPageToTop();
		page.getNextButton().waitAndClick(10);
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupHigerWaitTime();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		base.waitTime(2);
		page.extractFile();
		driver.waitForPageToBeReady();
		File dir = new File(home + "/Downloads/VOL0001/Natives/0001/");
		File[] dir_contents = dir.listFiles();
		System.out.println(dir_contents.length);
		int NativeFile = dir_contents.length;

		if (Integer.valueOf(DocCount) <= (NativeFile)) {
			base.passedStep("Family Document is included in generated file");
		} else {
			base.failedStep("Family Document is not included");
		}

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);

		loginPage.logout();

	}

	/**
	 * @author Brundha.T Date:7/21/2022 TestCase Id :RPMXCON-48030 Description :To
	 *         Verify "No Rotation" is selected, pages are not rotated and are
	 *         branded in the existing layout.(portrait/landscape layout remains as
	 *         it is).
	 * 
	 */
	@Test(description = "RPMXCON-48030", enabled = true, groups = { "regression" })
	public void verifyTheNonRotatedDocumentInTiffImage() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48030 -Production Component");
		base.stepInfo(
				"To Verify 'No Rotation' is selected, pages are not rotated and are branded in the existing layout.(portrait/landscape layout remains as it is).");
		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		BaseClass base = new BaseClass(driver);

		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.telecaSearchString);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionPrivDocs(tagname, Input.tagNamePrev);
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
		String firstFile = prefixID + beginningBates + suffixID;
		File file = new File(home + "/Downloads/VOL0001/Images/0001/" + firstFile + ".tif");
		driver.waitForPageToBeReady();
		BufferedImage bimg = ImageIO.read(file);
		int width = bimg.getWidth();
		int height = bimg.getHeight();
		driver.waitForPageToBeReady();
		if (width < height) {
			base.passedStep("Tiff image is not rotated as expected");
		} else {
			base.failedStep("Tiff image is rotated");
		}

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);

		loginPage.logout();

	}

	/**
	 * @author Brundha.T Date:7/21/2022 TestCase Id :RPMXCON-48205 Description :To
	 *         Verify In Productions, Date format of the DateOnly fields should not
	 *         include time portion
	 * 
	 */
	@Test(description = "RPMXCON-48205", enabled = true, groups = { "regression" })
	public void verifyingMasterDateOnlyInDownloadedFile() throws Exception {

		UtilityLog.info(Input.prodPath);
		BaseClass base = new BaseClass(driver);
		base.stepInfo("RPMXCON-48205 -Production Component");
		base.stepInfo("To Verify In Productions, Date format of the DateOnly fields should not include time portion");

		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectGenerateOption(false);
		page.slipSheetToggleEnable();
		page.fillingSlipSheetInTiffSection("MasterDateDateOnly");
		page.fillingSlipSheetInTiffSection("DocID");
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
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		String firstFile = prefixID + beginningBates + suffixID;
		File file = new File(home + "/Downloads/VOL0001/Images/0001/" + firstFile + ".ss.tif");

		ITesseract instance = new Tesseract1();
		File tessDataFolder = LoadLibs.extractTessResources("tessdata");
		instance.setDatapath(tessDataFolder.getPath());
		driver.waitForPageToBeReady();
		String result = instance.doOCR(file);
		System.out.println(result);

		String[] MasterDateDateOnly = result.split(":");
		String Output = MasterDateDateOnly[1];

		String MasterDate = Output.trim().replaceAll("[a-zA-Z]", "");
		int MasterDateSize = MasterDate.length();
		if (MasterDateSize >= 11) {
			base.passedStep("MasterDateOnly displayed as expected");
		} else {
			base.failedStep("MasterDate is not displayed as expected");
		}

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Brundha.T Date:7/21/2022 TestCase Id :RPMXCON-47980 Description :To
	 *         Verify In Productions, Document Level Numbering options Sub-bates
	 *         number and min. number length should not be mandatory
	 * 
	 */
	@Test(description = "RPMXCON-47980", enabled = true, groups = { "regression" })
	public void productionGenerationWithoutDocumentLevel() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47980 -Production Component");
		base.stepInfo(
				"To Verify In Productions, Document Level Numbering options Sub-bates number and min. number length should not be mandatory");
		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		base = new BaseClass(driver);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		driver.waitForPageToBeReady();
		page.getNumbering_Document_RadioButton().waitAndClick(10);
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		loginPage.logout();
	}

	/**
	 * @author Brundha.T Date:7/21/2022 TestCase Id :RPMXCON-48286 Description :To
	 *         Verify In Production , Selection of PDF or TIFF for Rotation on
	 *         Component Page should not throw any error message.
	 * 
	 */
	@Test(description = "RPMXCON-48286", enabled = true, groups = { "regression" })
	public void verifyingSuccessMessageInComponentTab() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48286 -Production Component");
		base.stepInfo(
				"To Verify In Production , Selection of PDF or TIFF for Rotation on Component Page should not throw any error message.");

		String tagname = "Tag" + Utility.dynamicNameAppender();
		BaseClass base = new BaseClass(driver);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSection(tagname, Input.searchString1);
		page.getRotationLandScapeDropdown().waitAndClick(10);
		driver.scrollPageToTop();
		base.waitTillElemetToBeClickable(page.getMarkCompleteLink());
		page.getMarkCompleteLink().waitAndClick(10);
		if (page.gettext("Rotation sections in PDF and TIFF components must have the same configuration")
				.isElementAvailable(2)) {
			base.failedStep("Error Message is Displayed");
		} else if (base.getSuccessMsgHeader().isElementAvailable(2)) {
			base.passedStep("Success message is displayed as expected");
			base.VerifySuccessMessage("Mark Complete successful");
			page.getNextButton().waitAndClick(10);
			driver.waitForPageToBeReady();
			page.visibleCheck("Numbering and Sorting");
		}

		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47847
	 * @Description:To Verify the Create/Display/View of Template with newly created
	 *                 Security Group.
	 **/
	@Test(description = "RPMXCON-47847", enabled = true, groups = { "regression" })
	public void verifyTemplateInSecurityGroup() throws Exception {

		UtilityLog.info(Input.prodPath);


		base.stepInfo("Test case Id:RPMXCON-47847 Production Component Sprint 19");
		base.stepInfo("To Verify the Create/Display/View of Template with newly created Security Group.");

		String securityGroup = "Security" + UtilityLog.dynamicNameAppender();
		String productionSet = "ProdSet" + UtilityLog.dynamicNameAppender();
		String template = "Template" + UtilityLog.dynamicNameAppender();

		// add new security group
		SecurityGroupsPage security = new SecurityGroupsPage(driver);
		security.navigateToSecurityGropusPageURL();
		security.AddSecurityGroup(securityGroup);

		// select created security group
		productionname = "p" + Utility.dynamicNameAppender();

		// create new production set
		page.navigateToProductionPage();
		page.CreateProductionSets(productionSet);
		page.navigateToProductionPageByNewProductionSet(productionSet);

		// create production and save as custom template
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingCreatedSecurityGroup(securityGroup);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.navigateToProductionPage();
		page.saveProductionAsTemplateAndVerifyInManageTemplateTab(productionname, template);
		loginPage.logout();

	}

	/**
	 * @author NA created on:NA modified by:NA TESTCASE No:RPMXCON-48869
	 * @Description:To verify that if user added leading space for DAT field, then
	 *                 after generating the production, warning message should be
	 *                 displayed
	 **/
	@Test(description = "RPMXCON-48869", enabled = true, groups = { "regression" })
	public void verifyErrorMessageinDATWithLeadSpc() throws Exception {

		UtilityLog.info(Input.prodPath);
		

		base.stepInfo("Test case Id:RPMXCON-48869");
		base.stepInfo(
				"To verify that if user added leading space for DAT field, then after generating the production, warning message should be displayed");
		String expected = "DAT field names must begin with an alphabet. Please check.";

		productionname = "p" + Utility.dynamicNameAppender();
		String batesWithSpaces = " " + "B" + " " + page.getRandomNumber(2);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSectionWithBates(Input.bates, Input.batesNumber, batesWithSpaces);
		page.getComponentsMarkComplete().waitAndClick(5);
		base = new BaseClass(driver);
		base.VerifyErrorMessage(expected);
		base.passedStep(
				"verified - that if user added leading space for DAT field, then after generating the production, warning message should be displayed");
		loginPage.logout();
	}

	/**
	 * @author NA created on:NA modified by:NA TESTCASE No:RPMXCON-50015
	 * @Description:To Verify that help text should be displays as This is not a
	 *                 searchable field for 'AllProductionBatesRanges' metadata"
	 **/
	@Test(description = "RPMXCON-50015", enabled = true, groups = { "regression" })
	public void verifyHelpTxtForAllProdBateRange() throws Exception {
		UtilityLog.info(Input.prodPath);
		

		base.stepInfo("Test case Id:RPMXCON-50015");
		base.stepInfo(
				"To Verify that help text should be displays as This is not a searchable field for 'AllProductionBatesRanges' metadata");
		String expHelpText = "Due to the nature of the content, this field cannot be made searchable";
		String fieldName = "AllProductionBatesRanges";

		ProjectFieldsPage projectField = new ProjectFieldsPage(driver);
		base = new BaseClass(driver);
		projectField.navigateToProjectFieldsPage();
		projectField.applyFilterByFilterName(fieldName);
		base.waitTillElemetToBeClickable(projectField.getApplyButton());
		base.waitForElement(projectField.getFieldNameEdititButton(fieldName));
		projectField.getFieldNameEdititButton(fieldName).waitAndClick(3);
		base.waitTillElemetToBeClickable(projectField.getFieldNameEdititButton(fieldName));
		base.waitForElement(projectField.getIsSearcHelpBtn());
		projectField.getIsSearcHelpBtn().waitAndClick(3);
		base.waitForElement(projectField.getIsSearcHelpTxt());
		String actHelpText = projectField.getIsSearcHelpTxt().getText();
		if (actHelpText.equals(expHelpText)) {
			base.passedStep("Help text for AllProductionBatesRanges metadata is displaying as Expected");
		} else {
			base.failedStep("Help text for AllProductionBatesRanges metadata is Not displaying as Expected");
		}
		base.passedStep(
				"Verified - that help text should be displays as \"This is not a searchable field.\" for 'AllProductionBatesRanges' metadata");
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49111
	 * @Description:To verify that the value of Number of Natives on
	 *                 Production-Summary tab if Native component is selected with
	 *                 few File types
	 **/
	@Test(description = "RPMXCON-49111", enabled = true, groups = { "regression" })
	public void verifyNativeCount() throws Exception {
		UtilityLog.info(Input.prodPath);
		

		base.stepInfo("Test case Id:RPMXCON-49111");
		base.stepInfo(
				"To verify that the value of Number of Natives on Production-Summary tab if Native component is selected with few File types");

		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// create tag and folder
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		sessionSearch.basicContentSearch(Input.searchString5);
		sessionSearch.bulkTagExisting(tagname);

		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		driver.waitForPageToBeReady();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		base.waitForElement(page.countOfNumberOfNative());
		String nativeCount = page.countOfNumberOfNative().getText();
		System.out.println(nativeCount);
		String count = "0";

		if (!(nativeCount == count)) {
			base.passedStep(
					"verified that the value of Number of Natives on Production-Summary tab if Native component");
		} else {
			base.failedStep("verification not successful");
		}
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49115
	 * @Description:To verify that the value of Number of MP3 Files on
	 *                 Production-Summary tab if MP3 Files component is selected.
	 **/

	@Test(description = "RPMXCON-49115", enabled = true, groups = { "regression" })
	public void verifyMP3Count() throws Exception {

		UtilityLog.info(Input.prodPath);
		

		base.stepInfo("Test case Id:RPMXCON-49115 Production Component Sprint 19");
		base.stepInfo(
				"To verify that the value of Number of MP3 Files on Production-Summary tab if MP3 Files component is selected.");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// create tag and folder
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		base.waitForElement(page.countOfNumberOfNative());
		String nativeCount = page.countOfNumberOfNative().getText();
		System.out.println(nativeCount);
		String count = "0";

		if (!(nativeCount == count)) {
			base.passedStep(
					"verified that the value of Number of Natives on Production-Summary tab if Native component");
		} else {
			base.failedStep("verification not successful");
		}
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49112
	 * @Description:To verify that the value of Number of Natives on
	 *                 Production-Summary tab if Native component is selected and
	 *                 document is Privileged
	 **/
	@Test(description = "RPMXCON-49112", enabled = true, groups = { "regression" })
	public void verifyNativeCountWithPrivTag() throws Exception {
		UtilityLog.info(Input.prodPath);
		

		base.stepInfo("Test case Id:RPMXCON-49112");
		base.stepInfo(
				"To verify that the value of Number of Natives on Production-Summary tab if Native component is selected and document is Privileged");

		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// create tag and folder
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		base.stepInfo("created a privileged tag");

		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		driver.waitForPageToBeReady();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		base.waitForElement(page.countOfNumberOfNative());
		String nativeCount = page.countOfNumberOfNative().getText();
		System.out.println(nativeCount);
		String count = "0";

		if (!(nativeCount == count)) {
			base.passedStep(
					"verified that the value of Number of Natives on Production-Summary tab if  Native component is selected and document is Privileged");
		} else {
			base.failedStep("verification not successful");
		}

		loginPage.logout();
	}

	/**
	 * @author NA created on:NA modified by:NA TESTCASE No:RPMXCON-48499
	 * @Description:To verify that after clicking on InComplete button on Production
	 *                 Components, last selected Native File Group types and Tags
	 *                 should be displayed
	 **/
	public void verifySelectedTypesTags_MarkIncomplete(String userName, String password) throws Exception {
		loginPage.loginToSightLine(userName, password);
		base.stepInfo("Logged in as" + userName);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id:RPMXCON-48499 Production");
		base.stepInfo(
				"To verify that after clicking on InComplete button on Production Components, last selected Native File Group types and Tags should be displayed");

		// create tag and folder
		base = new BaseClass(driver);
		tagname = "Tag" + Utility.dynamicNameAppender();
		if (userName.equals(Input.pa1userName)) {
			tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		} else {
			tagsAndFolderPage.createNewTagwithClassificationInRMU(tagname, Input.tagNamePrev);
		}

		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		base.waitForElement(page.getNativeChkBox());
		page.getNativeChkBox().Click();
		page.fillingDATSection();
		page.fillingNativeSectionWithTags(tagname);
		page.clickComponentMarkCompleteAndIncomplete();
		base.waitForElement(page.getNativeTab());
		page.getNativeTab().Click();

		List<String> types = base.availableListofElementsWithAttributeValues(page.getAllNativeFileTypes(), "value");
		for (String type : types) {
			System.out.println(type);
			page.verifyingNativeSectionFileType(type);
		}
		base.passedStep("All Native File Types are Selected After Mark InComplete");

		driver.waitForPageToBeReady();
		base.waitForElement(page.getNativeSelectTags());
		page.getNativeSelectTags().waitAndClick(10);
		base.waitForElement(page.getNativeCheckBox(tagname));
		driver.waitForPageToBeReady();
		if (page.getNativeCheckBox(tagname).GetAttribute("class").contains("clicked")) {
			base.passedStep(
					"Verified - that after clicking on InComplete button on Production Components, last selected Native File Group types and Tags should be displayed");
		} else {
			base.failedStep(
					"after clicking on InComplete button on Production Components, last selected Tags not displayed");
		}
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49981
	 * @Description:To verify that RMU can view the existing productions in his
	 *                 Security Group
	 **/
	@Test(description = "RPMXCON-49981", enabled = true, groups = { "regression" })
	public void verifyProductionFromRMU() throws Exception {

		UtilityLog.info(Input.prodPath);
		

		base.stepInfo("Test case Id:RPMXCON-49981 Production Component Sprint 19");
		base.stepInfo("To verify that RMU can view the existing productions in his Security Group");

		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// create tag and folder
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		String productionNameInPA = page.addANewProduction(productionname);
		System.out.println(productionNameInPA);
		page.fillingDATSection();
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
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		page.navigateToProductionPage();
		base.waitForElement(page.getProductionFromHomepage(productionname));
		String productionNameInRMU = page.getProductionFromHomepage(productionname).getText();
		System.out.println(productionNameInRMU);
		softAssertion.assertEquals(productionNameInPA, productionNameInRMU);
		softAssertion.assertAll();
		base.passedStep("verified that RMU can view the existing productions in his Security Group");
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49816
	 * @Description:To verify that Audio production with redaction should generate
	 *                 successfully if bates number includes hyphens with space
	 **/
	@Test(description = "RPMXCON-49816", enabled = true, groups = { "regression" })
	public void verifyBatesWithHypensAndSpace() throws Exception {

		UtilityLog.info(Input.prodPath);
		

		base.stepInfo("Test case Id:RPMXCON-49816 Production Component Sprint 19");
		base.stepInfo(
				"To verify that Audio production with redaction should generate successfully if bates number includes hyphens with space");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// create tag and folder
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		String batesWithHypenAndSpace = "B" + " " + page.getRandomNumber(2) + "-";
		System.out.println(batesWithHypenAndSpace);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSectionWithBates(Input.bates, Input.batesNumber, batesWithHypenAndSpace);
		driver.waitForPageToBeReady();
		page.fillingMP3FileWithBurnRedaction();
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
		page.OCR_Verification__BatesNo_In_GeneratedFile(prefixID, suffixID, batesWithHypenAndSpace);
		base.passedStep(
				"verified that Audio production with Redaction should generate successfully if bates number contains Hypen");
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47896
	 * @Description:To Verify Sorting options from numbering and sorting Section of
	 *                 production wizard
	 **/
	@Test(description = "RPMXCON-47896", enabled = true, groups = { "regression" })
	public void verifyDefaultSelectionInNumbering() throws Exception {

		UtilityLog.info(Input.prodPath);
		

		base.stepInfo("Test case Id:RPMXCON-47896 Production Component Sprint 19");
		base.stepInfo("To Verify Sorting options from numbering and sorting Section of production wizard");

		// create a privileged tag
		tagname = "Tag" + Utility.dynamicNameAppender();
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search and bulk tag
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		// create production
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		driver.scrollingToBottomofAPage();

		// verify sort by metadata is clicked by default
		page.verifyDefaultSelection_SortByMetadata();

		// verify sort by selected tags
		page.verifySortByTags_SortedByAscending();

		// verify custom sort link
		page.verifyCustomSort_Link();

		loginPage.logout();

	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47938
	 * @Description:To Verify Sorting Based on Tag in Production
	 **/
	@Test(description = "RPMXCON-47938", enabled = true, groups = { "regression" })
	public void verifySortingInTag() throws Exception {

		UtilityLog.info(Input.prodPath);
		

		base.stepInfo("Test case Id:RPMXCON-47938 Production Component Sprint 19");
		base.stepInfo("To Verify Sorting Based on Tag in Production");

		// create a privileged tag
		tagname = "Tag" + Utility.dynamicNameAppender();
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search and bulk tag
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		// create production
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		driver.scrollingToBottomofAPage();

		// verify sort by selected tags
		base.waitForElement(page.getSortingRadioBtn());
		page.getSortingRadioBtn().waitAndClick(10);

		List<String> availableTags = base.availableListofElements(page.getTotalTagsInSorting());
		List<String> availableTags2 = base.availableListofElements(page.getTotalTagsInSorting());
		System.out.println(availableTags);
		base.verifyOriginalSortOrder(availableTags, availableTags2, "Ascending", true);
		base.passedStep("'Available Tags' are sorted");
		loginPage.logout();

	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47719
	 * @Description:To Verify ProjectAdmin will be able to enter production
	 *                 components information on the self production wizard
	 **/
	@Test(description = "RPMXCON-47719", enabled = true, groups = { "regression" })
	public void verifySelfProductionWizard() throws Exception {

		UtilityLog.info(Input.prodPath);
		

		base.stepInfo("Test case Id:RPMXCON-47719 Production Component Sprint 19");
		base.stepInfo(
				"To Verify ProjectAdmin will be able to enter production components information on the self production wizard");

		// create a privileged tag
		tagname = "Tag" + Utility.dynamicNameAppender();
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search and bulk tag
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname);
		page.fillingAdvancedProductionComponent();
		page.fillingMP3();
		page.getComponentsMarkComplete().waitAndClick(10);
		base.passedStep("Following components can be selected   DAT,TIFF,NATIVE,PDF,TEXT,MP3 etc");
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49101
	 * @Description:To verify that EmailAuthorNameAndAddress,
	 *                 EmailToNamesAndAddresses, EmailCCNamesAndAddresses, and
	 *                 EmailBCCNamesAndAddresses fields should be display properly
	 *                 in the correct format in the DAT.
	 **/
	@Test(description = "RPMXCON-49101", enabled = true, groups = { "regression" })
	public void verifyDATWithEmailClassification() throws Exception {

		UtilityLog.info(Input.prodPath);
		

		base.stepInfo("Test case Id:RPMXCON-49101 Production Component Sprint 19");
		base.stepInfo(
				"To verify that EmailAuthorNameAndAddress, EmailToNamesAndAddresses, EmailCCNamesAndAddresses, and EmailBCCNamesAndAddresses fields should be display properly in the correct format in the DAT.");

		// create a privileged tag
		tagname = "Tag" + Utility.dynamicNameAppender();
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search and bulk tag
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		String productionName = page.addANewProduction(productionname);
		System.out.println(productionName);
		page.fillingDATSection();
		page.addNewFieldOnDAT();
		page.addDatField(1, "Email", "EmailAuthorNameAndAddress");
		page.addNewFieldOnDAT();
		page.addDatField(2, "Email", "EmailToNamesAndAddresses");
		page.addNewFieldOnDAT();
		page.addDatField(3, "Email", "EmailBCCNamesAndAddresses");
		page.addNewFieldOnDAT();
		page.addDatField(4, "Email", "EmailCCNamesAndAddresses");
		page.fillingNativeSection();
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
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		page.getConfirmProductionCommit().Enabled();
		page.getConfirmProductionCommit().isDisplayed();
		page.getConfirmProductionCommit().waitAndClick(10);
		page.getCopyPath().isDisplayed();
		page.getCopyPath().waitAndClick(10);
		page.getQC_Download().isDisplayed();
		page.getQC_Download().waitAndClick(30);
		page.getQC_Downloadbutton_allfiles().waitAndClick(30);
		
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
		loginPage.logout();
	}

	/**
	 * @author NA created on:NA modified by:NA TESTCASE No:RPMXCON-47741
	 * @Description:To Verify default Filter By selections for Production status
	 *                 (In-Progress, ...). Verify for both Grid and Tile view
	 **/
	@Test(description = "RPMXCON-47741", enabled = true, groups = { "regression" })
	public void verifyFilterByINPROGRESS() throws Exception {
		UtilityLog.info(Input.prodPath);
		

		base.stepInfo("Test Cases Id : RPMXCON-47741");
		base.stepInfo(
				"To Verify default Filter By selections for Production status (In-Progress, ...). Verify for both Grid and Tile view");

		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag
		BaseClass base = new BaseClass(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for tag
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		// Create Prod for failed state
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		base.waitForElement(page.getBeginningBates());
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickGenarateWaitForRegenarate();

		// Create Prod for Draft state
		page = new ProductionPage(driver);
		beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();

		// Create Prod for Inprogress state
		page = new ProductionPage(driver);
		beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickOnGenerateButton();

		// view all state
		page = new ProductionPage(driver);
		page.navigateToProductionPage();
		base.waitForElement(page.getFilterByButton());
		page.getFilterByButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		page.getElementDisplayCheck(page.getFilterByDRAFT());
		page.getElementDisplayCheck(page.getFilterByINPROGRESS());
		page.getElementDisplayCheck(page.getFilterByFAILED());
		page.getElementDisplayCheck(page.getFilterByCOMPLETED());
		base.stepInfo("All option is available in the list");
		base.waitForElement(page.getFilterByINPROGRESS());
		page.getFilterByINPROGRESS().waitAndClick(10);
		driver.waitForPageToBeReady();
		page.getRefreshButton().waitAndClick(10);
		base.stepInfo("Filtered all options");

		// view INPROGRESS status only
		page.filterInprogressProduction();
		page.verifyProdctionState(page.getProductionSate(), "INPROGRESS");

		page.getGridView().waitAndClick(10);
		driver.waitForPageToBeReady();

		int status = base.getIndex(page.getGridWebTableHeader(), "STATUS");
		driver.waitForPageToBeReady();
		page.verifyProductionStateWebTableGridView(status, "INPROGRESS");

		base.passedStep(
				"Verified - that default Filter By selections for Production status (In-Progress, ...). Verify for both Grid and Tile view");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		base.stepInfo("deleting created tags and folders");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	@Test(description = "RPMXCON-47739", enabled = true, groups = { "regression" })
	public void verifySortByinTileView() throws Exception {
		UtilityLog.info(Input.prodPath);
		

		base.stepInfo("Test Cases Id : RPMXCON-47739");
		base.stepInfo(
				"To Verify the default Sorting and required sequence of sorting. And default production sorting (Most recent First). Tile View.");

		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

//	// Pre-requisites
//	// create tag 
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		BaseClass base = new BaseClass(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for tag
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

////  Create Prod for failed state
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		base.waitForElement(page.getBeginningBates());
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickGenarateWaitForRegenarate();

		// Create Prod for Draft state
		page = new ProductionPage(driver);
		beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();

//	  Create Prod for Inprogress state
		page = new ProductionPage(driver);
		beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickOnGenerateButton();

		page = new ProductionPage(driver);
		page.navigateToProductionPage();
		if (page.getLoadMore().Enabled()) {
			do {
				driver.scrollingToBottomofAPage();
				if (page.getNoProdExistText().isElementAvailable(4)) {
					break;
				}
			} while (page.getLoadMore().Visible());
		}

		base.waitForElementCollection(page.getProductionSate());
		List<String> expProdStatusOrder = base.getAvailableListofElements(page.getProductionSate());
		System.out.println(expProdStatusOrder);
		Collections.sort(expProdStatusOrder);
		System.out.println(expProdStatusOrder);
		base.stepInfo("Before Sorting : " + expProdStatusOrder);

		base.waitForElement(page.getSortByButton());
		page.getSortByButton().selectFromDropdown().selectByValue("Status");
		base.waitForElement(page.getRefreshButton());
		page.getRefreshButton().Click();
		driver.waitForPageToBeReady();

		if (page.getLoadMore().Enabled()) {
			do {
				driver.scrollingToBottomofAPage();
				if (page.getNoProdExistText().isElementAvailable(4)) {
					break;
				}
			} while (page.getLoadMore().Visible());
		}

		base.waitForElementCollection(page.getProductionSate());
		List<String> actProdStatusOrder = base.getAvailableListofElements(page.getProductionSate());
		System.out.println(actProdStatusOrder);
		base.stepInfo("After Sorting : " + actProdStatusOrder);

		if (expProdStatusOrder.equals(actProdStatusOrder)) {
			base.passedStep("Productions list sorted as per the selected list in Tile View   ");
		} else {
			base.failedStep("Productions list Not sorted as per the selected list in Tile View   ");
		}

		base.passedStep(
				"Verified that default Sorting and required sequence of sorting. And default production sorting (Most recent First). Tile View.");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		base.stepInfo("deleting created tags and folders");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author NA created on:NA modified by:NA TESTCASE No:RPMXCON-47816
	 * @Description:To Verify Production Guard Page option (close) Issue for Tag and
	 *                 Redaction.
	 **/
	@Test(description = "RPMXCON-47816", enabled = true, groups = { "regression" })
	public void verifyProdGuardCloseOpt() throws Exception {
		UtilityLog.info(Input.prodPath);
		

		base.stepInfo("Test Cases Id : RPMXCON-47816");
		base.stepInfo("To Verify Production Guard Page option (close) Issue for Tag and Redaction");
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for tag
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();

		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		driver.waitForPageToBeReady();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.priviledgeDocCheck(true, tagname);
		base.waitForElementCollection(page.getPrivGuardTextBox_Value());
		page.fillingPrivGuardPage();
		base.stepInfo("Navigated to Production location page");

		// Click Back Btn in production location page
		page.clickBackBtnUntilElementFound(page.getAddRuleBtn());
		base.stepInfo("Navigated Back to Priv Guard page");

		if (page.getRemoveLink().Visible()) {
			base.failedStep("Remove Option displaying after Mark Complete");
		} else {
			base.passedStep("Remove Option Not displaying after Mark Complete");
		}

		base.waitForElement(page.getMarkInCompleteBtn());
		page.getMarkInCompleteBtn().waitAndClick(3);

		if (page.getRemoveLink().Visible()) {
			base.passedStep("Remove Option displaying after Mark InComplete");
		} else {
			base.failedStep("Remove Option Not displaying after Mark Complete");
		}

		base.passedStep("verified - that Production Guard Page option (close) Issue for Tag and Redaction");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		base.stepInfo("deleting created tags and folders");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author NA created on:NA modified by:NA TESTCASE No:RPMXCON-47998
	 * @Description:To Verify In Priv Guard, the message saying that the production
	 *                 has privileged documents
	 **/
	@Test(description = "RPMXCON-47998", enabled = true, dataProvider = "Users", groups = { "regression" })
	public void verifyPrivGuardNotDisWarniMSg(String userName, String password) throws Exception {
		try {
			loginPage.logout();
		}catch(Exception e) {}
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(userName, password);

		base.stepInfo("Test Cases Id : RPMXCON-47998");
		base.stepInfo("To Verify In Priv Guard, the message saying that the production has privileged documents "
				+ "even when there are no privileged documents");

		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		base = new BaseClass(driver);

		if (userName.equals(Input.pa1userName)) {
			tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		} else {
			tagsAndFolderPage.createNewTagwithClassificationInRMU(tagname, Input.tagNamePrev);
		}

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname);
		driver.waitForPageToBeReady();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		base.VerifySuccessMessage("Mark Complete successful");
		base.passedStep("Warning message not be displayed as there is no Priv document in selected document source");
		base.passedStep("Verified In Priv Guard, the message saying that the production has privileged documents "
				+ "even when there are no privileged documents");
		loginPage.logout();
	}

	/**
	 * @author NA created on:NA modified by:NA TESTCASE No:RPMXCON-63080
	 * @Description:To Verify production regeneration without any change in default
	 *                 enabled native placeholder under TIFF section
	 **/
	@Test(description = "RPMXCON-63080", enabled = true, dataProvider = "Users", groups = { "regression" })
	public void verifyProdRegeFrDefNatPHTiff(String userName, String password) throws Exception {
		try {
			loginPage.logout();
		}catch(Exception e) {}
		loginPage.loginToSightLine(userName, password);
		base.stepInfo("Logged in as" + userName);

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test Cases Id : RPMXCON-63080");
		base.stepInfo("To Verify production regeneration without any change in "
				+ "default enabled native placeholder under TIFF section");

		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		int doccount = 1;
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		base = new BaseClass(driver);

		if (userName.equals(Input.pa1userName)) {
			tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		} else {
			tagsAndFolderPage.createNewTagwithClassificationInRMU(tagname, Input.tagNamePrev);
		}

		base.stepInfo("perform basic search and bulk folder");
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch("DocFileType", "spreadsheet");
		sessionSearch.ViewInDocList();

		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(doccount);
		doclist.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);

		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionwithNativelyPlaceholder(tagname);
		driver.waitForPageToBeReady();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageAndRegeneratingAgain();
		page.getbtnContinueGeneration().isElementAvailable(320);
		if (page.getbtnContinueGeneration().isDisplayed()) {
			base.waitForElement(page.getbtnContinueGeneration());
			page.getbtnContinueGeneration().waitAndClick(10);
		}
		page.getbtnContinueGeneration().waitAndClick(10);
		base.waitForElement(page.getQC_Download());
		page.getQC_Download().Click();
		base.waitForElement(page.getQC_Downloadbutton_allfiles());
		page.getQC_Downloadbutton_allfiles().Click();
		base.waitUntilFileDownload();
		driver.waitForPageToBeReady();

		String home = System.getProperty("user.home");
		String name = page.getProduction().getText().trim();
		driver.waitForPageToBeReady();
		page.deleteFiles();
		page.extractFile();
		driver.waitForPageToBeReady();

		File Native = new File(
				home + "/Downloads/VOL0001/Natives/0001/" + prefixID + beginningBates + suffixID + ".xls");
		File DatFile = new File(home + "/Downloads/VOL0001/" + name + "/" + name + "_DAT.dat");
		File tiffFile = new File(
				home + "/Downloads/VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tif");
		System.out.println(DatFile);

		if (Native.exists()) {
			base.passedStep("Native placeholder generated for the selected file type ");
		} else {
			base.failedStep("Native placeholder Not generated for the selected file type ");
		}
		if (DatFile.exists()) {
			base.passedStep("Dat file is exists in generated production");
		} else {
			base.failedStep("Dat file is not displayed as expected");
		}
		if (tiffFile.exists()) {
			base.passedStep("Tiff is generated successfully");
		} else {
			base.failedStep("Tiff is not generated successfully");
		}
		base.passedStep("Verify production regeneration without any change in "
				+ "default enabled native placeholder under TIFF section");
		loginPage.logout();
	}

	/**
	 * @author NA created on:NA modified by:NA TESTCASE No:RPMXCON-63084
	 * @Description:To Verify production regeneration with change(additional, change
	 *                 existing file type to spreadsheet only) in default enabled
	 *                 native placeholder under PDF section
	 **/
	@Test(description = "RPMXCON-63084", enabled = true, dataProvider = "Users", groups = { "regression" })
	public void verifyProdRegeFrDefNatPHpdf2(String userName, String password) throws Exception {
		try {
			loginPage.logout();
		}catch(Exception e) {}
		loginPage.loginToSightLine(userName, password);
		base.stepInfo("Logged in as" + userName);

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test Cases Id : RPMXCON-63084");
		base.stepInfo("To Verify production regeneration with change(additional, change "
				+ "existing file type to spreadsheet only) in default enabled native placeholder under PDF section");

		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		int doccount = 1;
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		base = new BaseClass(driver);

		if (userName.equals(Input.pa1userName)) {
			tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		} else {
			tagsAndFolderPage.createNewTagwithClassificationInRMU(tagname, Input.tagNamePrev);
		}

		base.stepInfo("perform basic search and bulk folder");
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch("DocFileType", "spreadsheet");
		sessionSearch.ViewInDocList();

		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(doccount);
		doclist.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);

		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSectionwithNativelyPlaceholder(tagname);
		driver.waitForPageToBeReady();
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

		String name = page.getProduction().getText().trim();
		String home = System.getProperty("user.home");
		File file = new File(home + "/Downloads/" + name + ".zip");
		File file1 = new File(Input.fileDownloadLocation + name + ".zip");

		if (file.exists()) {
			driver.waitForPageToBeReady();
			file.delete();
		} else if (file1.exists()) {
			driver.waitForPageToBeReady();
			file1.delete();
		}

		base.waitForElement(page.getQC_backbutton());
		page.getQC_backbutton().waitAndClick(10);

		base.waitForElement(page.getMarkInCompleteBtn());
		page.getMarkInCompleteBtn().waitAndClick(10);

		page.clickBackBtnUntilElementFound(page.getProductionName());
		base.waitForElement(page.getNextButton());
		page.getNextButton().Enabled();
		page.getNextButton().waitAndClick(10);

		driver.waitForPageToBeReady();
		base.waitForElement(page.getbtnComponentsMarkIncomplete());
		page.getbtnComponentsMarkIncomplete().waitAndClick(10);
		;

		base.waitForElement(page.getTIFFTab());
		page.getTIFFTab().Click();

		driver.scrollingToBottomofAPage();
		base.waitForElement(page.getSelectMultiFileTypeInTifffNative(2, "Images"));
		page.getSelectMultiFileTypeInTifffNative(2, "Images").waitAndClick(5);
		driver.scrollPageToTop();

		driver.waitForPageToBeReady();
		page.navigateToNextSection();
		page.navigateToNextSection();
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		base.waitUntilFileDownload();

		driver.waitForPageToBeReady();
		page.deleteFiles();
		page.extractFile();
		driver.waitForPageToBeReady();

		File Native = new File(
				home + "/Downloads/VOL0001/Natives/0001/" + prefixID + beginningBates + suffixID + ".xls");
		File DatFile = new File(home + "/Downloads/VOL0001/" + name + "/" + name + "_DAT.dat");
		File pdfFile = new File(home + "/Downloads/VOL0001/PDF/0001/" + prefixID + beginningBates + suffixID + ".pdf");
		System.out.println(DatFile);

		if (Native.exists()) {
			base.passedStep("Native placeholder generated for the selected file type ");
		} else {
			base.failedStep("Native placeholder Not generated for the selected file type ");
		}
		if (DatFile.exists()) {
			base.passedStep("Dat file is exists in generated production");
		} else {
			base.failedStep("Dat file is not displayed as expected");
		}
		if (pdfFile.exists()) {
			base.passedStep("Pdf is generated successfully");
		} else {
			base.failedStep("Pdf is not generated successfully");
		}
		base.passedStep(
				"Verified - production regeneration with change(additional, change existing file type to spreadsheet only) "
						+ "in default enabled native placeholder under PDF section");
		loginPage.logout();
	}

	/**
	 * @author sowndarya.velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47936
	 * @Description:To Verify Slip Sheet->Work Product Should get saved in
	 *                 Production Component Section
	 **/
	@Test(description = "RPMXCON-47936", enabled = true, groups = { "regression" })
	public void verifyDefaultSelection() throws Exception {

		UtilityLog.info(Input.prodPath);
		
		base.stepInfo("Test Cases Id : RPMXCON-47936");
		base.stepInfo("To Verify Slip Sheet->Work Product Should get saved in Production Component Section");

		productionname = "p" + Utility.dynamicNameAppender();

		ProductionPage page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.selectGenerateOption(false);
		page.slipSheetToggleEnable();
		page.fillingSlipSheetInTiffSection("MasterDateDateOnly");
		page.fillingSlipSheetInTiffSection("DocID");
		page.clickComponentMarkCompleteAndIncomplete();
		page.getTIFFTab().waitAndClick(10);
		page.getAdvancedTabInTIFF().waitAndClick(10);
		page.toVerifyDefaultSelection("MasterDateDateOnly");
		page.toVerifyDefaultSelection("DocID");
		base.passedStep("Verified Slip Sheet->Work Product Should get saved in Production Component Section");
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48001
	 * @Description:To Verify DAT Bates number generated should be in sync with
	 *                 actual bates number generated for the Documents.
	 **/
	@Test(description = "RPMXCON-48001", enabled = true, groups = { "regression" })
	public void verifySubBates() throws Exception {

		UtilityLog.info(Input.prodPath);
		

		base.stepInfo("Test case Id:RPMXCON-48001 Production Component Sprint 19");
		base.stepInfo(
				"To Verify DAT Bates number generated should be in sync with actual bates number generated for the Documents.");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// create tag and folder
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		String datField = "B" + page.getRandomNumber(2);
		String beginningBates = page.getRandomNumber(2);
		int firstFile = Integer.parseInt(beginningBates);
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.addingDatField(0, Input.bates, Input.batesNumber, datField);
		page.navigateToNextSection();
		page.fillingNumberingPageWithDocumentLevelAndSubBates(beginningBates, prefixID, suffixID);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		page.getQC_Download().waitAndClick(3);
		base.waitForElement(page.getQC_Downloadbutton_allfiles());
		page.getQC_Downloadbutton_allfiles().Click();
		base.waitUntilFileDownload();
		String PDocCount = page.getProductionDocCount().getText();
		int DocCount = Integer.parseInt(PDocCount);
		int lastfile = firstFile + DocCount;
		page.extractFile();
		page.verifyDATFileForSubBatesNumber(firstFile, lastfile, prefixID, suffixID);
		base.passedStep("verified that Bates Number in DAT file should be AK01000.00001");
		loginPage.logout();
	}

	/**
	 * @author sowndarya.velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49110
	 * @Description:To verify that the value of Number of Natives on
	 *                 Production-Summary tab if Native component is selected and
	 *                 Tags is also selected.
	 **/
	@Test(description = "RPMXCON-49110", enabled = true, groups = { "regression" })
	public void verifyNativeCountInSummaryWithSelectedTags() throws Exception {

		UtilityLog.info(Input.prodPath);
		

		base.stepInfo("Test Cases Id : RPMXCON-49110");
		base.stepInfo(
				"To verify that the value of Number of Natives on Production-Summary tab if Native component is selected and Tags is also selected.");

		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// create tag and folder
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		int doCount = sessionSearch.basicContentSearch(Input.testData1);
		System.out.println(doCount);
		sessionSearch.bulkTagExisting(tagname);

		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSectionWithTags(tagname);
		driver.waitForPageToBeReady();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		base.waitForElement(page.countOfNumberOfNative());
		String count = page.countOfNumberOfNative().getText();
		System.out.println(count);
		int nativeCount = Integer.parseInt(count);
		System.out.println(nativeCount);
		softAssertion.assertEquals(nativeCount, doCount);
		loginPage.logout();
	}

	/**
	 * @author sowndarya.velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49109
	 * @Description:To verify that the value of Number of Natives on
	 *                 Production-Summary tab if Native component is not selected.
	 **/
	@Test(description = "RPMXCON-49109", enabled = true, groups = { "regression" })
	public void verifyNativeCountInSummary() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test Cases Id : RPMXCON-49109");
		base.stepInfo(
				"To verify that the value of Number of Natives on Production-Summary tab if Native component is not selected.");

		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		
		// create tag and folder
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch("DocFileType", "mp3");
		sessionSearch.ViewInDocList();
		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(2);
		doclist.bulkTagExisting(tagname);

		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		driver.waitForPageToBeReady();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		base.waitForElement(page.countOfNumberOfNative());
		String nativeCount = page.countOfNumberOfNative().getText();
		System.out.println(nativeCount);
		String count = "0";
		softAssertion.assertEquals(nativeCount, count);
		softAssertion.assertAll();
		base.passedStep(
				"the value of Number of Natives on  Production-Summary tab if Native component is not selected.");
		loginPage.logout();
	}

	/**
	 * @author sowndarya.velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49120
	 * @Description:To verify that in Production-Branding, Metadata Field drop down
	 *                 should be sorted by alpha ascending
	 **/
	@Test(description = "RPMXCON-49120", enabled = true, groups = { "regression" })
	public void verifyBrandingSorting() throws Exception {

		UtilityLog.info(Input.prodPath);
		

		base.stepInfo("Test Cases Id : RPMXCON-49120");
		base.stepInfo(
				"To verify that in Production-Branding, Metadata Field drop down should be sorted by alpha ascending");

		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		base.stepInfo("Verify meta data list in drop down native will be in ascending order on pdf section.");
		page.verifyMetaDataDropdownNativeAscendingOrderPdfSec();
		base.stepInfo("Verify meta data list in drop down native will be in ascending order on tiff section.");
		page.verifyMetaDataDropdownNativeAscendingOrderTiffSec();
		loginPage.logout();

	}

	/**
	 * @author sowndarya.velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47858
	 * @Description:Verify QC & Result page displays Review Production and
	 *                     Production location details.
	 **/
	@Test(description = "RPMXCON-47858", enabled = true, groups = { "regression" })
	public void verifyQCPage() throws Exception {

		UtilityLog.info(Input.prodPath);
		

		base.stepInfo("Test Cases Id : RPMXCON-47858");
		base.stepInfo("Verify QC & Result page displays Review Production and Production location details.");

		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// create tag and folder
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		String productionNameInPA = page.addANewProduction(productionname);
		System.out.println(productionNameInPA);
		page.fillingDATSection();
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
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();

		base.ValidateElement_PresenceReturn(page.getConfirmProductionCommit());
		base.printResutInReport(base.ValidateElement_PresenceReturn(page.getConfirmProductionCommit()),
				"commit button is visible", "commit button is not  visible", "Pass");

		base.ValidateElement_PresenceReturn(page.getCopyPath());
		base.printResutInReport(base.ValidateElement_PresenceReturn(page.getCopyPath()), "copy path button is visible",
				"copy path button is not  visible", "Pass");

		base.ValidateElement_PresenceReturn(page.getQC_Download());
		base.printResutInReport(base.ValidateElement_PresenceReturn(page.getQC_Download()),
				"Download button is visible", "Download button is not  visible", "Pass");

		loginPage.logout();

	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47805
	 * @Description:To Verify that Load file should be created successfully as part
	 *                 of the production generation
	 **/
	@Test(description = "RPMXCON-47805", enabled = true, groups = { "regression" })
	public void verifyLoadFileGeneration() throws Exception {

		UtilityLog.info(Input.prodPath);
		

		base.stepInfo("Test case Id:RPMXCON-47805 Production Component Sprint 19");
		base.stepInfo("To Verify that Load file should be created successfully as part of the production generation");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// create tag and folder
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		String beginningBates = page.getRandomNumber(2);
		int firstFile = Integer.parseInt(beginningBates);
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		driver.waitForPageToBeReady();
		page.fillingMP3FileWithBurnRedaction();
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
		String PDocCount = page.getProductionDocCount().getText();
		int DocCount = Integer.parseInt(PDocCount);
		int lastfile = firstFile + DocCount;
		page.verifyLoadFileDocumentsAfterDownload(firstFile, lastfile);

		base.passedStep("Verified that Load file should be created successfully as part of the production generation");
		loginPage.logout();
	}

	/**
	 * @author NA created on:NA modified by:NA TESTCASE No:RPMXCON-47982
	 * @Description:To Verify Branding provided for a document should not
	 *                 overlapping/written over the actual content, on Preview
	 **/
	@Test(description = "RPMXCON-47982", enabled = true, groups = { "regression" })
	public void verifyBrndOverActualText() throws Exception {
		UtilityLog.info(Input.prodPath);
		

		base.stepInfo("Test Cases Id : RPMXCON-47982");
		base.stepInfo(
				"To Verify Branding provided for a document should not overlapping/written over the actual content, on Preview");
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String brandingString = "Testing";
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		base = new BaseClass(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.verifyTheTagOnRightBranding(tagname, brandingString);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.viewingPreviewButtonInSummaryTab();
		base.waitUntilFileDownload();

		String name = prefixID + beginningBates + suffixID;
		String home = System.getProperty("user.home");
		File file = new File(home + "/Downloads/" + name + ".pdf");
		File file1 = new File(Input.fileDownloadLocation + name + ".pdf");
		if (file.exists()) {
			try {
				String url = home + "/Downloads/";
				String content = page.verifyBrandingOverlapping(url, name + ".pdf", brandingString, 0);
				System.out.println(content);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (file1.exists()) {
			try {
				String url1 = Input.fileDownloadLocation;
				String content1 = page.verifyBrandingOverlapping(url1, name + ".pdf", brandingString, 0);
				System.out.println(content1);
			} catch (IOException e) {
				e.printStackTrace();
			}
			base.passedStep("Verified-Branding provided for a document should not overlapping/written "
					+ "over the actual content, on Preview");
		}
		loginPage.logout();
	}

	/**
	 * @author NA created on:NA modified by:NA TESTCASE No:RPMXCON-47794
	 * @Description:To Verify the default selections in Production component Native
	 *                 section
	 **/
	@Test(description = "RPMXCON-47794", enabled = true, groups = { "regression" })
	public void verifyDefSelecinNativeComp() throws Exception {
		UtilityLog.info(Input.prodPath);
		

		base.stepInfo("Test Cases Id : RPMXCON-47794");
		base.stepInfo("To Verify the default selections in Production component Native section");
		base = new BaseClass(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();

		base.waitForElement(page.getNativeTab());
		page.getNativeTab().waitAndClick(3);

		// Verifying Default File Type In Native
		String spreadSheet = page.getNativeCheckBoxChecked("Spreadsheets").GetAttribute("checked");
		String multiMedia = page.getNativeCheckBoxChecked("Multimedia").GetAttribute("checked");
		System.out.println(multiMedia);
		if (spreadSheet.equalsIgnoreCase("true") && multiMedia.equalsIgnoreCase("true")) {
			base.passedStep("By default 'Spreadsheets (.xls, .xlsx, .csv, etc.)	\r\n"
					+ "Multimedia (.wav, .mp3, .mp4, etc.)' check box gets chekced");
		} else {
			base.failedStep("By default 'Spreadsheets (.xls, .xlsx, .csv, etc.)	\r\n"
					+ "Multimedia (.wav, .mp3, .mp4, etc.)' check box not gets chekced");
		}

		base.waitForElement(page.getAdvancedTabInNative());
		page.getAdvancedTabInNative().waitAndClick(3);
		driver.scrollingToBottomofAPage();

		// Verifying LF Toogle in Native Component
		if (page.getNative_GenerateLoadFileLST().Selected() == false) {
			base.passedStep("LF Toogle Not Selected as Expected");
		} else {
			base.failedStep("LF Toogle Selected By Default");
		}

		// Verifying Radio Btn in native Component
		if (page.getDefRadioinNativeTab().GetAttribute("checked").equalsIgnoreCase("true")) {
			base.passedStep("Complete Families of Privileged and Redacted Documents Selected as Default");
		} else {
			base.failedStep("Complete Families of Privileged and Redacted Documents Not Selected as Default");
		}

		base.passedStep("Verified - that default selections in Production component Native section");
		loginPage.logout();
	}

	/**
	 * @author NA created on:NA modified by:NA TESTCASE No:RPMXCON-47740
	 * @Description:To Verify sorting in Grid View from Productions page
	 **/
	@Test(description = "RPMXCON-47740", enabled = true, groups = { "regression" })
	public void verifyGridViewInProdPage() throws Exception {
		List<String> beforeSortOrder = new ArrayList<String>();
		List<String> afterSortOrder = new ArrayList<String>();
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test Cases Id : RPMXCON-47740");
		base.stepInfo("To Verify sorting in Grid View from Productions page");

		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for tag
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		// Create Prod for failed state
		page.navigateToProductionPage();
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		base.waitForElement(page.getBeginningBates());
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickGenarateWaitForRegenarate();

		// Create Prod for Draft state
		page = new ProductionPage(driver);
		beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();

//	  Create Prod for Inprogress state
		page = new ProductionPage(driver);
		beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickOnGenerateButton();

		page.goToProductionGridView();
		base = new BaseClass(driver);
		base.waitForElementCollection(page.getProdCrtDateGridView());
		beforeSortOrder.addAll(base.getAvailableListofElements(page.getProdCrtDateGridView()));
		driver.scrollingToBottomofAPage();
		for (int i = 2; i <= Integer.parseInt(page.getLastPageGridView().getText()); i++) {
			driver.scrollingToBottomofAPage();
			if (page.getPageNumGridView(i).isElementAvailable(4)) {
				page.getPageNumGridView(i).waitAndClick(4);
				base.waitTime(1);
				beforeSortOrder.addAll(base.getAvailableListofElements(page.getProdCrtDateGridView()));
			}
		}
		base.stepInfo("Before Sorting : " + beforeSortOrder);

		page.goToProductionGridView();
		base.waitForElement(page.getProdSortCrtDateGridView());
		page.getProdSortCrtDateGridView().Click();
		driver.waitForPageToBeReady();

		base.waitForElementCollection(page.getProdCrtDateGridView());
		afterSortOrder.addAll(base.getAvailableListofElements(page.getProdCrtDateGridView()));
		driver.scrollingToBottomofAPage();
		for (int i = 2; i <= Integer.parseInt(page.getLastPageGridView().getText()); i++) {
			driver.scrollingToBottomofAPage();
			if (page.getPageNumGridView(i).isElementAvailable(4)) {
				page.getPageNumGridView(i).waitAndClick(4);
				base.waitTime(1);
				afterSortOrder.addAll(base.getAvailableListofElements(page.getProdCrtDateGridView()));
			}
		}
		base.stepInfo("After Sorting : " + afterSortOrder);
		base.verifyOriginalSortOrder(afterSortOrder, beforeSortOrder, "Ascending", true);
		base.stepInfo("Productions list sorted as per the selected list in Grid View");
		base.passedStep("To Verify sorting in Grid View from Productions page");
		loginPage.logout();
	}

	/**
	 * @author NA created on:NA modified by:NA TESTCASE No:RPMXCON-47790
	 * @Description:To Verify toggle control works for the Filepath and Volume under
	 *                 the Production Location
	 **/
	@Test(description = "RPMXCON-47790", enabled = true, groups = { "regression" })
	public void verifyToCtrlAndVolProdLocation() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test Cases Id : RPMXCON-47790");
		base.stepInfo("To Verify toggle control works for the Filepath and Volume under the Production Location.");

		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		productionname = "p" + Utility.dynamicNameAppender();

		// create tag and folder
		
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch("DocFileType", "mp3");
		sessionSearch.ViewInDocList();
		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(1);
		doclist.bulkTagExisting(tagname);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		base.waitForElement(page.getLoadFilePath());
		page.getLoadFilePath().waitAndClick(4);

		base.waitForElement(page.getVolumeIncluded());
		page.getVolumeIncluded().waitAndClick(4);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		base.waitUntilFileDownload();

		String expUrl = "\\\\ld9nas02.consilio.com\\UKSLPERF\\Productions\\Automation\\" + productionname;
		String actUrl = page.getCopyPathUrl().GetAttribute("value");
		System.out.println(expUrl);
		System.out.println(actUrl);
		base.stepInfo(actUrl);
		softAssertion.assertEquals(expUrl, actUrl);
		softAssertion.assertAll();
		base.passedStep("Verified - toggle control works for the Filepath and Volume under the Production Location.");
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47973
	 * @Description:To Verify Production generation for PDF and Excel Docs
	 **/
	@Test(description = "RPMXCON-47973", enabled = true, groups = { "regression" })
	public void verifyPDFAndExcelFileDocs() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test Cases Id : RPMXCON-47973");
		base.stepInfo("To Verify Production generation for PDF and Excel Docs");

		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		
		// create tag
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for tag
		sessionSearch.MetaDataSearchInBasicSearch("DocFileExtension", ".pdf");
		sessionSearch.bulkTagExisting(tagname);

//  Create Prod for failed state
		page.navigateToProductionPage();
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		loginPage.logout();
	}

	/**
	 * @author sowndarya.velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47872
	 * @Description:To verify Bates No Generation should be in Sync, when using
	 *                 continue from Previous bates No
	 **/
	@Test(description = "RPMXCON-47872", enabled = true, groups = { "regression" })
	public void verifyBatNoGenSyncwithPreBatNo() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test Cases Id : RPMXCON-47872");
		base.stepInfo("To verify Bates No Generation should be in Sync, when using continue from Previous bates No");

		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);

		
		// create tag
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for tag
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		// Pre req - Production For Completed
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
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

		page.navigateToProductionPage();
		driver.Navigate().refresh();
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		base.waitForElement(page.getClickHereLink());
		page.getClickHereLink().Click();
		base.stepInfo("Click Here Link Clicked and Next Bates Num Popup displaying Successfully");
		base.waitForElement(page.getSelectNextBatesNumber(prefixID, suffixID));
		page.getSelectNextBatesNumber(prefixID, suffixID).Click();
		base.stepInfo("Bates Number Selected in Next Bates Num Popup ");
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		base.waitForElement(page.getQC_backbutton());
		page.getQC_backbutton().waitAndClick(3);

		base.waitForElement(page.getProd_BatesRange());
		String actBatesno = page.getProd_BatesRange().getText();
		System.out.println(actBatesno);
		if (actBatesno.contains(prefixID) && actBatesno.contains(suffixID)) {
			base.passedStep("Bates Numbersync with previous bates number ");
		} else {
			base.failedStep("Bates Number Not sync with previous bates number ");
		}
		base.passedStep("Verified - Bates No Generation should be in Sync, when using continue from Previous bates No");
		loginPage.logout();

	}

	/**
	 * @author sowndarya.velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49060
	 * @Description:To verify that the 'production start date' should contain and
	 *                 present the date when the production regeneration was started
	 *                 from Scratch
	 **/
	@Test(description = "RPMXCON-49060", enabled = true, groups = { "regression" })
	public void verifyProdStartDateafterRegen() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test Cases Id : RPMXCON-49060");
		base.stepInfo(
				"To verify that the 'production start date' should contain and present the date when the production regeneration was started from Scratch");
		tagname = "Tag" + Utility.dynamicNameAppender();
		productionname = "p" + Utility.dynamicNameAppender();

		
		// create tag
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for tag
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		// Create Prod For Failed State
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		base.waitForElement(page.getBeginningBates());
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickGenarateWaitForRegenarate();

		// Open Existing Failed Production and Perform regenerate
		page.openExistingProduction(productionname);
		base.waitForElement(page.getbtnRegenerate());
		page.getbtnRegenerate().Click();
		base.waitForElement(page.getRegenerateAllRadioBtn());
		page.getRegenerateAllRadioBtn().Click();
		base.waitForElement(page.getbtnRegenerateContinue());
		page.getbtnRegenerateContinue().Click();
		base.stepInfo("Re-Generate started Successfully");

		String expStartDate = base.getcurrentdateinGMT();
		System.out.println(expStartDate);
		base.stepInfo("Expected Time: " + expStartDate);
		page.goToProductionGridView();
		base.waitForElement(page.getGridProdValues(productionname, 6));
		String actStartDate = page.getGridProdValues(productionname, 6).getText();
		System.out.println(actStartDate);
		base.stepInfo("Actual Time: " + actStartDate);

		if (actStartDate.contains(expStartDate)) {
			base.passedStep(
					"The 'production start date' contain and present the date when the last regenerate was started.");
		} else {
			base.failedStep(
					"The 'production start date' not contain and present the date when the last regenerate was started.");
		}

		base.passedStep("Verified - that the 'production start date' should contain and present the date "
				+ "when the production regeneration was started from Scratch");
		loginPage.logout();
	}

	/**
	 * @author NA created on:NA modified by:NA TESTCASE No:RPMXCON-47913
	 * @Description:To Verify in production, the placeholders enabled for priv docs,
	 *                 Generated Priv Doc (PDF/TIFF/Text) should contain Placeholder
	 *                 with Branding
	 **/
	@Test(description = "RPMXCON-47913", enabled = true, groups = { "regression" })
	public void verifyProdPhEnabPrivDocsCntPhBrand() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test Cases Id : RPMXCON-47913");
		base.stepInfo(
				"To Verify in production, the placeholders enabled for priv docs, Generated Priv Doc (PDF/TIFF/Text) should contain Placeholder with Branding.");
		tagname = "Tag" + Utility.dynamicNameAppender();
		productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String brandingString = Input.searchString4;

		
		// create tag
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for tag
		int pureHit = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		page.navigateToProductionPage();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSection(tagname, tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		String privCount = page.getPrivDocCountInSummaryPage().getText();
		base.stepInfo("Priv Doc Count in Summary Page: " + privCount);
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.waitUntilFileDownload();
		driver.waitForPageToBeReady();
		page.deleteFiles();
		page.extractFile();

		String home = System.getProperty("user.home");
		String name = prefixID + beginningBates + suffixID;
		driver.waitForPageToBeReady();
		File file = new File(home + "/Downloads/VOL0001/PDF/0001/" + name + ".pdf");
		if (file.exists()) {
			try {
				String url = home + "/Downloads/VOL0001/PDF/0001/";
				String content = page.verifyTopCenterBrandingInPDF(url, name + ".pdf", brandingString, 0);
				System.out.println(content);
				base.passedStep(
						"Verified - that branding specified (in the header and footer) applied to the placeholder documents");

			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			base.failedStep("File Not Exists");
		}

		if (privCount.equals(String.valueOf(pureHit))) {
			base.passedStep("Privileged Documents' count displays correctly in Privileged Document Summary");
		} else {
			base.failedStep("Privileged Documents' count Not displays correctly in Privileged Document Summary");
		}

		base.passedStep("verified - that in production, the placeholders enabled for priv docs, "
				+ "Generated Priv Doc (PDF/TIFF/Text) should contain Placeholder with Branding.");
		loginPage.logout();
	}

	/**
	 * @author NA created on:NA modified by:NA TESTCASE No:RPMXCON-63088
	 * @Description:Verify that if spreadsheet is redacted and Native placeholder is
	 *                     default enabled from TIFF/PDF section then PDF should be
	 *                     produced with natively placeholder
	 **/
	@Test(description = "RPMXCON-63088", enabled = true, groups = { "regression" })
	public void verifyRedactNativePHTiffPdf() throws Exception {

		base = new BaseClass(driver);
		base.stepInfo("RPMXCON-63088");
		String RedactName = "Redact" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String tagName = "Tag" + Utility.dynamicNameAppender();
		base.stepInfo("Verify that if spreadsheet is redacted and Native placeholder is default enabled from "
				+ "TIFF/PDF section then PDF should be produced with natively placeholder");
		try {
			loginPage.logout();
		}catch(Exception e) {}
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.selectproject(Input.largeVolDataProject);

		RedactionPage redactionpage = new RedactionPage(driver);
		redactionpage.navigateToRedactionsPageURL();
		redactionpage.AddRedaction(RedactName, Input.rmu1FullName);
		base.stepInfo("Basic meta data search");

		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch("DocFileType", "Spreadsheet");
		sessionSearch.ViewInDocList();

		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(3);
		doclist.viewSelectedDocumentsInDocView();

		DocViewPage doc = new DocViewPage(driver);
		driver.waitForPageToBeReady();
		doc.pageRedaction(RedactName);

		tagsAndFolderPage.createNewTagwithClassificationInRMU(tagName, Input.tagNamePrev);

		SessionSearch sessionSearch1 = new SessionSearch(driver);
		sessionSearch1 = new SessionSearch(driver);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch1.ViewInDocList();
		DocListPage doclist1 = new DocListPage(driver);
		doclist1.documentSelection(1);
		doclist.bulkTagExistingFromDoclist(tagName);

		String beginningBates = page.getRandomNumber(2);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSectionwithNativelyPlaceholder(tagName);
		driver.waitForPageToBeReady();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		base.waitUntilFileDownload();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		driver.waitForPageToBeReady();
		page.deleteFiles();
		page.extractFile();
		driver.waitForPageToBeReady();
		File pdfFile = new File(home + "/Downloads/VOL0001/PDF/0001/" + prefixID + beginningBates + suffixID + ".pdf");
		String text = page.verifyTextinPDF(pdfFile, tagName);
		base.stepInfo(text);

		String productionname1 = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname1);
		page.fillingDATSection();
		page.fillingTIFFSectionwithNativelyPlaceholder(tagName);
		driver.waitForPageToBeReady();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname1);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		base.waitUntilFileDownload();
		driver.waitForPageToBeReady();
		String name1 = page.getProduction().getText().trim();
		driver.waitForPageToBeReady();
		page.deleteFiles();
		page.extractFile();
		driver.waitForPageToBeReady();
		File tiffFile = new File(
				home + "/Downloads/VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tif");
		page.OCR_Verification_In_Generated_Tiff_tess4j(tiffFile, tagName);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49181
	 * @Description:To verify that upon completion of commit, notification should be
	 *                 displayed on right top corner
	 **/

	@Test(description = "RPMXCON-49181", enabled = true, groups = { "regression" })
	public void verifyCommitMessage() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id:RPMXCON-49181 Production Component Sprint 20");
		base.stepInfo("To verify that upon completion of commit, notification should be displayed on right top corner");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		
		// create tag and folder
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
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
		base = new BaseClass(driver);
		int initialBg = base.initialBgCount();
		page.toStartGenerate();
		base.waitForElement(page.getConfirmProductionCommit());
		page.getConfirmProductionCommit().waitAndClick(10);
		String expected = "Commit action has been started as a background task. You will be notified upon completion. Please refresh this page to see the latest status.";
		base.VerifySuccessMessage(expected);
		base.checkNotificationCount(initialBg, 1);
		SavedSearch saveSearch = new SavedSearch(driver);
		saveSearch.verifyExecuteAndReleaseNotify(initialBg, 1);
		driver.Navigate().refresh();
		loginPage.logout();
	}

	/**
	 * @author sowndarya.velraj No:RPMXCON-49254
	 * @Description:To verify Production should be failed if Bates Numbers is
	 *                 duplicate
	 **/

	@Test(description = "RPMXCON-49254", enabled = true, groups = { "regression" })
	public void verfyBatesRangeFailedStatus() throws Exception {

		base = new BaseClass(driver);
		base.stepInfo("Test case Id:RPMXCON-49254- Production component");
		base.stepInfo("To verify Production should be failed if Bates Numbers is duplicate");
		UtilityLog.info(Input.prodPath);

		
		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		String Prod_A = page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.toFillNumberingPageWithClickHereLink(1);
		page.navigateToNextSection();
		base.passedStep(Prod_A + "is created with next bates number and is in  draft status");

		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		String productionname2 = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		String Prod_B = page.addANewProduction(productionname2);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.toFillNumberingPageWithClickHereLink(1);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname2);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.passedStep(Prod_B + " is generated successfully");

		page.navigateToProductionPage();
		base.waitForElement(page.getProductionFromHomepage(Prod_A));
		page.getProductionFromHomepage(Prod_A).waitAndClick(10);
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		base.waitForElement(page.getbtnProductionGenerate());
		page.getbtnProductionGenerate().waitAndClick(10);
		page.getVerifyGenStatus("Reserving Bates").isElementAvailable(150);
		page.reservingBatesRangeFailedStatusInGenPage().isElementAvailable(10);
		base.passedStep("Production A failed in generation due to duplicate bates number");
		loginPage.logout();

	}

	/**
	 * @author sowndarya.velraj No:RPMXCON-46867
	 * @Description:To Verify Project Admin will have the ability to confirm
	 *                 production. Upon confirmation, bates numbers in the documents
	 *                 in the database shall be committed
	 **/

	@Test(description = "RPMXCON-46867", enabled = true, groups = { "regression" })
	public void verifyCommittedProdAsUneditable() throws Exception {

		base = new BaseClass(driver);
		base.stepInfo("Test case Id:RPMXCON-46867- Production component sprint 20");
		base.stepInfo(
				"To Verify Project Admin will have the ability to confirm production. Upon confirmation, bates numbers in the documents in the database shall be committed");
		UtilityLog.info(Input.prodPath);

		

		String foldername = "Folder" + Utility.dynamicNameAppender();
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		String productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.toFillNumberingPageWithClickHereLink(1);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		base.stepInfo("navigate back and try to mark incomplete");
		page.getQC_backbutton().waitAndClick(10);
		for (int i = 0; i < 4; i++) {
			base.waitForElement(page.getBackButton());
			page.getBackButton().waitAndClick(10);
		}
		page.getMarkInCompleteBtn().GetAttribute("disabled").contains("disabled");
		base.passedStep("After confirmation user cannot edit the Production.");
		loginPage.logout();

	}

	/**
	 * @author NA No:RPMXCON-47861
	 * @Description:Verify the regeneration of Production with same configuration
	 *                     and some new documents; before commit and confirm bates
	 *                     number for that Production
	 **/
	@Test(description = "RPMXCON-47861", enabled = true, groups = { "regression" })
	public void verifyRegenProdWithNewDoc() throws Exception {
		UtilityLog.info(Input.prodPath);
		base = new BaseClass(driver);
		base.stepInfo("Test Cases Id : RPMXCON-47861");
		base.stepInfo(
				"Verify the regeneration of Production with same configuration and some new documents; before commit and confirm bates number for that Production");

		tagname = "Tag" + Utility.dynamicNameAppender();
		productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String bates = "B" + Utility.dynamicNameAppender();
		int noOfDoc1 = 1;
		int noOfDoc2 = 4;

		
		// create tag
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for tag
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch("DocFileType", "Spreadsheet");
		sessionSearch.ViewInDocList();
		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(noOfDoc1);
		doclist.bulkTagExistingFromDoclist(tagname);
		page.navigateToProductionPage();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSectionWithBates(Input.bates, Input.batesNumber, bates);
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.priviledgeDocCheck(true, tagname);
		List<String> expPrivGuard = page.privGuardTextBoxValue();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		String expRootProdLocPage = Input.prodPath;
		String expProdOut = productionname;
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		String exptotaldoc = page.getDoc_Count().getText();
		page.clickBackBtnUntilElementFound(page.getBatesRange());

		SessionSearch sessionSearch1 = new SessionSearch(driver);
		sessionSearch1.navigateToSessionSearchPageURL();
		sessionSearch1.ViewInDocList();
		DocListPage doclist1 = new DocListPage(driver);
		doclist1.documentSelection(noOfDoc2);
		doclist1.bulkTagExistingFromDoclist(tagname);

		page.navigateToProductionPage();
		page.openExistingProduction(productionname);
		page.clickBackBtnUntilElementFound(page.getDATTab());

		driver.waitForPageToBeReady();
		page.verifyDATAndTIFFField();
		page.clickElementNthtime(page.getNextButton(), 2);
		String tagStatus = page.getSelecTaginDocSelTab(tagname).GetAttribute("aria-selected");
		System.out.println(tagStatus);
		page.clickElementNthtime(page.getNextButton(), 1);

		List<String> actPrivGuard = page.privGuardTextBoxValue();
		System.out.println(actPrivGuard);

		page.clickElementNthtime(page.getNextButton(), 1);
		String actRootProdLocPage = page.getProdRootinProdLOcTab().getText();
		String actProdOut = page.getProductionOutputLocation_ProductionDirectory().Value();
		System.out.println(actRootProdLocPage + actProdOut);
		page.clickElementNthtime(page.getNextButton(), 2);
		base.waitForElement(page.getbtnGenMarkIncomplete());
		page.getbtnGenMarkIncomplete().waitAndClick(2);

		page.clickBackBtnUntilElementFound(page.getBtnMarkIncomplete());
		driver.waitForPageToBeReady();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.clickMArkCompleteMutipleTimes(2);
		driver.waitForPageToBeReady();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		String acttotaldoc = page.getDoc_Count().getText();
		page.clickBackBtnUntilElementFound(page.getBatesRange());
		String actBatesnum = page.getBatesRange().getText();

		SoftAssert asserts = new SoftAssert();
		asserts.assertEquals(tagStatus, "true");
		System.out.println(actPrivGuard);
		asserts.assertEquals(expPrivGuard, actPrivGuard);
		asserts.assertEquals(expRootProdLocPage, actRootProdLocPage);
		asserts.assertEquals(expProdOut, actProdOut);
		asserts.assertTrue(actBatesnum.contains(prefixID));
		asserts.assertAll();

		if (Integer.parseInt(exptotaldoc) < Integer.parseInt(acttotaldoc)) {
			base.passedStep("Production should generate successfully including new documents");
		} else {
			base.failedStep("Production should generate successfully Not including new documents");
		}
		base.passedStep(
				"Verify the regeneration of Production with same configuration and some new documents; before commit and confirm bates number for that Production");
		loginPage.logout();
	}

	/**
	 * @author sowndarya.velraj No:RPMXCON-47862
	 * @Description:Verify the regeneration of already produced document with
	 *                     different location
	 **/
	@Test(description = "RPMXCON-47862", enabled = true, groups = { "regression" })
	public void verifyRegenProdWithNewLoc() throws Exception {
		UtilityLog.info(Input.prodPath);
		

		base.stepInfo("Test Cases Id : RPMXCON-47862");
		base.stepInfo("Verify the regeneration of already produced document with different location");
		tagname = "Tag" + Utility.dynamicNameAppender();
		productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String bates = "B" + Utility.dynamicNameAppender();
		int noOfDoc1 = 2;
		String location = "P" + Utility.dynamicNameAppender();

//	// create tag 
		base = new BaseClass(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for tag
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch("DocFileType", "Spreadsheet");
		sessionSearch.ViewInDocList();
		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(noOfDoc1);
		doclist.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSectionWithBates(Input.bates, Input.batesNumber, bates);
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.priviledgeDocCheck(true, tagname);
		List<String> expPrivGuard = page.privGuardTextBoxValue();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		String expRootProdLocPage = Input.prodPath;
		String expProdOut = productionname;
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();

		page.navigateToProductionPage();
		page.openExistingProduction(productionname);
		page.clickBackBtnUntilElementFound(page.getDATTab());
		driver.waitForPageToBeReady();
		page.verifyDATAndTIFFField();
		page.clickElementNthtime(page.getNextButton(), 2);

		String tagStatus = page.getSelecTaginDocSelTab(tagname).GetAttribute("aria-selected");
		System.out.println(tagStatus);
		page.clickElementNthtime(page.getNextButton(), 1);
		List<String> actPrivGuard = page.privGuardTextBoxValue();
		System.out.println(actPrivGuard);
		page.clickElementNthtime(page.getNextButton(), 1);
		String actRootProdLocPage = page.getProdRootinProdLOcTab().getText();
		String actProdOut = page.getProductionOutputLocation_ProductionDirectory().Value();
		System.out.println(actRootProdLocPage + actProdOut);
		page.clickElementNthtime(page.getNextButton(), 2);
		base.waitForElement(page.getbtnGenMarkIncomplete());
		page.getbtnGenMarkIncomplete().waitAndClick(2);

		page.clickBackBtnUntilElementFound(page.getProdLocMarkIncomplete());
		driver.waitForPageToBeReady();
		page.fillingProductionLocationPage(location);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		driver.waitForPageToBeReady();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		String name = page.getProduction().getText().trim();

		SoftAssert asserts = new SoftAssert();
		asserts.assertEquals(tagStatus, "true");
		System.out.println(actPrivGuard);
		asserts.assertEquals(expPrivGuard, actPrivGuard);
		asserts.assertEquals(expRootProdLocPage, actRootProdLocPage);
		asserts.assertEquals(expProdOut, actProdOut);
		asserts.assertAll();

		driver.waitForPageToBeReady();
		File DatFile = new File(home + "/Downloads/VOL0001/Load Files/" + name + "_DAT.dat");
		if (DatFile.exists()) {
			base.passedStep("Generated New Location");
		} else {
			base.failedStep("Not Generated New Location");
		}
		base.passedStep("Verify the regeneration of already produced document with different location");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-47970
	 * @Description: Verify Bates Number Generated is correct for All Documents in
	 *               Production
	 **/
	@Test(description = "RPMXCON-47970", enabled = true, groups = { "regression" })
	public void verifyBatesNoSyncwithProdDoc() throws Exception {

		UtilityLog.info(Input.prodPath);
		

		base.stepInfo("Test Cases Id : RPMXCON-47970");
		base.stepInfo("To Verify Bates Number Generated is correct for All Documents in Production");
		tagname = "Tag" + Utility.dynamicNameAppender();
		productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// create tag
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for tag
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		// Verifying Tiff with Multi Page
		String beginningBates = page.getRandomNumber(2);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFWithMultiPage(tagname);
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
		page.deleteFiles();
		page.extractFile();
		String home = System.getProperty("user.home");
		File dirPath = new File(home + "/Downloads/VOL0001/Images/0001");
		page.verifyBatesSyncWithAllDoc(dirPath, prefixID, suffixID);

		base.passedStep("Verified - Bates Number Generated is correct for All Documents in Production");
		loginPage.logout();

	}

	/**
	 * @author NA Testcase No:RPMXCON-63081
	 * @Description: Verify production regeneration without any change in default
	 *               enabled native placeholder under PDF section
	 **/
	@Test(description = "RPMXCON-63081", enabled = true, groups = { "regression" })
	public void verifyProdRegeFrDefNatPHPDF() throws Exception {

		UtilityLog.info(Input.prodPath);
		

		base.stepInfo("Test Cases Id : RPMXCON-63081");
		base.stepInfo("To Verify production regeneration without any change in "
				+ "default enabled native placeholder under PDF section");

		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		int doccount = 1;

		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		base.stepInfo("perform basic search and bulk folder");
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch("DocFileType", "spreadsheet");
		sessionSearch.ViewInDocList();

		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(doccount);
		doclist.bulkTagExisting(tagname);

		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSectionwithNativelyPlaceholder(tagname);
		driver.waitForPageToBeReady();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageAndRegeneratingAgain();

		page.getbtnContinueGeneration().isElementAvailable(320);
		if (page.getbtnContinueGeneration().isDisplayed()) {
			base.waitForElement(page.getbtnContinueGeneration());
			page.getbtnContinueGeneration().waitAndClick(10);
		}
		base.waitForElement(page.getQC_Download());
		page.getQC_Download().Click();
		base.waitTillElemetToBeClickable(page.getQC_Download());
		base.waitForElement(page.getQC_Downloadbutton_allfiles());
		page.getQC_Downloadbutton_allfiles().Click();
		base.waitUntilFileDownload();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		String name = page.getProduction().getText().trim();
		driver.waitForPageToBeReady();
		page.deleteFiles();
		page.extractFile();
		driver.waitForPageToBeReady();

		File Native = new File(
				home + "/Downloads/VOL0001/Natives/0001/" + prefixID + beginningBates + suffixID + ".xls");
		File DatFile = new File(home + "/Downloads/VOL0001/" + name + "/" + name + "_DAT.dat");
		File pdfFile = new File(home + "/Downloads/VOL0001/PDF/0001/" + prefixID + beginningBates + suffixID + ".pdf");
		System.out.println(DatFile);

		if (Native.exists()) {
			base.passedStep("Native placeholder generated for the selected file type ");
		} else {
			base.failedStep("Native placeholder Not generated for the selected file type ");
		}
		if (DatFile.exists()) {
			base.passedStep("Dat file is exists in generated production");
		} else {
			base.failedStep("Dat file is not displayed as expected");
		}
		if (pdfFile.exists()) {
			base.passedStep("PDF is generated successfully");
		} else {
			base.failedStep("PDF is not generated successfully");
		}
		base.passedStep("Verify production regeneration without any change in "
				+ "default enabled native placeholder under PDF section");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-49819
	 * @Description: Verify that Pagination the next tiles get loaded on 'Manage
	 *               Production' screen
	 **/
	@Test(description = "RPMXCON-49819", enabled = true, groups = { "regression" })
	public void verifyPaginationNextTilesgetLoad() throws Exception {

		UtilityLog.info(Input.prodPath);
		

		base.stepInfo("Test Cases Id : RPMXCON-49819");
		base.stepInfo("Verify that Pagination the next tiles get loaded on 'Manage Production' screen.");

		page.navigateToProductionPage();
		page.verifyLoadMorePagination();
		base.passedStep("Verified - that Pagination the next tiles get loaded on 'Manage Production' screen.");
		loginPage.logout();
	}

///
	/**
	 * @author NA Testcase No:RPMXCON-47863
	 * @Description: verify regenerate of production, before commit and confirm
	 *               bates number, overwrites any previously produced document(s)
	 **/
	@Test(description = "RPMXCON-47863", enabled = true, groups = { "regression" })
	public void verifyRegenProdOverwritePrePD() throws Exception {
		UtilityLog.info(Input.prodPath);
		
		base.stepInfo("Test Cases Id : RPMXCON-47863");
		base.stepInfo(
				"To verify regenerate of production, before commit and confirm bates number, overwrites any previously produced document(s).");

		tagname = "Tag" + Utility.dynamicNameAppender();
		productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String bates = "B" + Utility.dynamicNameAppender();

		// create tag
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for tag
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		String productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSectionWithBates(Input.bates, Input.batesNumber, bates);
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.priviledgeDocCheck(true, tagname);
		List<String> expPrivGuard = page.privGuardTextBoxValue();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		String expRootProdLocPage = Input.prodPath;
		String expProdOut = productionname;
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		int exptotaldoc = page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		page.clickBackBtnUntilElementFound(page.getBatesRange());
		String expBatesnum = page.getBatesRange().getText();

		page.navigateToProductionPage();
		page.openExistingProduction(productionname);
		page.clickBackBtnUntilElementFound(page.getDATTab());

		driver.waitForPageToBeReady();
		page.verifyDATAndTIFFField();
		page.clickElementNthtime(page.getNextButton(), 2);
		String tagStatus = page.getSelecTaginDocSelTab(tagname).GetAttribute("aria-selected");
		System.out.println(tagStatus);
		page.clickElementNthtime(page.getNextButton(), 1);

		List<String> actPrivGuard = page.privGuardTextBoxValue();
		System.out.println(actPrivGuard);

		page.clickElementNthtime(page.getNextButton(), 1);
		String actRootProdLocPage = page.getProdRootinProdLOcTab().getText();
		String actProdOut = page.getProductionOutputLocation_ProductionDirectory().Value();
		System.out.println(actRootProdLocPage + actProdOut);
		page.clickElementNthtime(page.getNextButton(), 2);
		base.waitForElement(page.getbtnGenMarkIncomplete());
		page.getbtnGenMarkIncomplete().waitAndClick(2);

		page.clickBackBtnUntilElementFound(page.getBtnMarkIncomplete());
		driver.waitForPageToBeReady();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.clickMArkCompleteMutipleTimes(2);
		driver.waitForPageToBeReady();
		int acttotaldoc = page.fillingGeneratePageWithContinueGenerationPopup();
		page.clickBackBtnUntilElementFound(page.getBatesRange());
		String actBatesnum = page.getBatesRange().getText();

		SoftAssert asserts = new SoftAssert();
		asserts.assertEquals(tagStatus, "true");
		System.out.println(actPrivGuard);
		asserts.assertEquals(expPrivGuard, actPrivGuard);
		asserts.assertEquals(expRootProdLocPage, actRootProdLocPage);
		asserts.assertEquals(expProdOut, actProdOut);
		asserts.assertAll();

		if (exptotaldoc == acttotaldoc && expBatesnum.equals(actBatesnum)) {
			base.passedStep("Same bates number & overwrites previously produced document(s).");
		} else {
			base.failedStep("Not Same bates number & overwrites previously produced document(s).");
		}
		base.passedStep("Verified - regenerate of production, before commit and confirm bates number, "
				+ "overwrites any previously produced document(s).");
		loginPage.logout();
	}

	/**
	 * @author sowndarya.velraj
	 * @Description: Verify that if spreadsheet is priv and Priv, Native placeholder
	 *               is default enabled from TIFF section then production should be
	 *               generated for Priv placeholder [RPMXCON-63085]
	 **/
	@Test(description = "RPMXCON-63085", enabled = true, dataProvider = "Users", groups = { "regression" })
	public void verifyProdRegeFrDefNatPHpdf(String userName, String password) throws Exception {

		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		int doccount = 1;
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		loginPage.logout();
		loginPage.loginToSightLine(userName, password);
		base.stepInfo("Logged in as" + userName);
		base.stepInfo("Test Cases Id : RPMXCON-63085 Production");
		base.stepInfo(
				"Verify that if spreadsheet is priv and Priv, Native placeholder is default enabled from TIFF section then production should be generated for Priv placeholder");

		if (userName.equals(Input.pa1userName)) {
			tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		} else {
			tagsAndFolderPage.createNewTagwithClassificationInRMU(tagname, Input.tagNamePrev);
		}

		sessionSearch.basicMetaDataSearch(Input.docFileType, null, "spreadsheet", null);
		sessionSearch.ViewInDocList();

		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(doccount);
		doclist.bulkTagExisting(tagname);

		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.tiffBrandingSelection(tagname);
		page.tiffPrivilegeDocumentSelection(tagname);
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

		String name = page.getProduction().getText().trim();
		String home = System.getProperty("user.home");
		File file = new File(home + "/Downloads/" + name + ".zip");
		File file1 = new File(Input.fileDownloadLocation + name + ".zip");

		page.extractFile();
		if (file.exists()) {
			driver.waitForPageToBeReady();
			file.delete();
		} else if (file1.exists()) {
			driver.waitForPageToBeReady();
			file1.delete();
		}

		File Native = new File(Input.fileDownloadLocation + name + "\\VOL0001\\" + name + "\\" + name + "_Native");
		File DatFile = new File(Input.fileDownloadLocation + name + "\\VOL0001\\" + name + "\\" + name + "_DAT.dat");
		File tiffFile = new File(Input.fileDownloadLocation + name + "\\VOL0001\\" + name + "\\" + name + "TIFF.OPT");
		System.out.println(Native);
		System.out.println(DatFile);
		System.out.println(tiffFile);

		if (Native.exists()) {
			base.passedStep("Native placeholder generated for the selected file type ");
		} else {
			base.failedMessage("Native placeholder Not generated for the selected file type ");
		}
		if (DatFile.exists()) {
			base.passedStep("Dat file is exists in generated production");
		} else {
			base.failedMessage("Dat file is not displayed as expected");
		}
		if (tiffFile.exists()) {
			base.passedStep("Pdf is generated successfully");
		} else {
			base.failedMessage("Pdf is not generated successfully");
		}
		base.passedStep(
				"Verified - production regeneration with change(additional, change existing file type to spreadsheet only) "
						+ "in default enabled native placeholder under PDF section");
		loginPage.logout();
	}

	/**
	 * @author sowndarya.velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48499
	 * @Description:To verify that after clicking on InComplete button on Production
	 *                 Components, last selected Native File Group types and Tags
	 *                 should be displayed
	 **/
	@Test(description = "RPMXCON-48499", enabled = true, groups = { "regression" }, dataProvider = "Users")
	public void verifySelectedTypesTags_MarkIncomplete2(String userName, String password) throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id:RPMXCON-48499 Production");
		base.stepInfo(
				"To verify that after clicking on InComplete button on Production Components, last selected Native File Group types and Tags should be displayed");
		try {
			loginPage.logout();
		}catch(Exception e) {}
		loginPage.loginToSightLine(userName, password);
		base.stepInfo("Logged in as" + userName);

		// create tag and folder
		base = new BaseClass(driver);
		tagname = "Tag" + Utility.dynamicNameAppender();
		if (userName.equals(Input.pa1userName)) {
			tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		} else {
			tagsAndFolderPage.createNewTagwithClassificationInRMU(tagname, Input.tagNamePrev);
		}

		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		base.waitForElement(page.getNativeChkBox());
		page.getNativeChkBox().Click();
		page.fillingDATSection();
		page.fillingNativeSectionWithTags(tagname);
		page.clickComponentMarkCompleteAndIncomplete();
		base.waitForElement(page.getNativeTab());
		page.getNativeTab().Click();

		List<String> types = base.availableListofElementsWithAttributeValues(page.getAllNativeFileTypes(), "value");
		for (String type : types) {
			System.out.println(type);
			page.verifyingNativeSectionFileType(type);
		}
		base.passedStep("All Native File Types are Selected After Mark InComplete");

		driver.waitForPageToBeReady();
		base.waitForElement(page.getNativeSelectTags());
		page.getNativeSelectTags().waitAndClick(10);
		base.waitForElement(page.getNativeCheckBox(tagname));
		driver.waitForPageToBeReady();
		if (page.getNativeCheckBox(tagname).GetAttribute("class").contains("clicked")) {
			base.passedStep(
					"Verified - that after clicking on InComplete button on Production Components, last selected Native File Group types and Tags should be displayed");
		} else {
			base.failedStep(
					"after clicking on InComplete button on Production Components, last selected Tags not displayed");
		}
		loginPage.logout();
	}

	/**
	 * @author sowndarya.velraj Testcase No:RPMXCON-47614
	 * @Description: To Verify ProjectAdmin will be able to enter basic information
	 *               on the self production wizard
	 **/
	@Test(description = "RPMXCON-47614", enabled = true, groups = { "regression" })
	public void verifyBasicInfoSelfProdWiz() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test Cases Id : RPMXCON-47614");
		base.stepInfo("To Verify ProjectAdmin will be able to enter basic information on the self production wizard");
		String nameAlpha = Utility.randomCharacterAppender(5);
		String nameAlphaNum = "p" + Utility.dynamicNameAppender();
		String nameSpChar = "p@" + Utility.dynamicNameAppender();
		String nameLenChar = Utility.randomCharacterAppender(260);
		String expErrorFrSch = "Special characters are not allowed in a production name";
		String expErrorFrBlField = "It is mandatory to provide the production name";
		String expErrorLenChar = "Production name entered is too long";
		String productionname = "p" + Utility.dynamicNameAppender();
		String tempName = "Temp" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);

		
		base = new BaseClass(driver);

//	Customize Template
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionWithBatesNumber();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.getProductionHomePage().waitAndClick(10);
		base.waitForElement(page.getprod_ActionButton_Reusable(productionname));
		page.getprod_ActionButton_Reusable(productionname).waitAndClick(5);
		base.waitForElement(page.getprod_Action_SaveTemplate_Reusable(productionname));
		page.getprod_Action_SaveTemplate_Reusable(productionname).waitAndClick(5);
		driver.waitForPageToBeReady();
		page.saveTemplate(tempName);
		base.stepInfo("Detail Panel with Header Basic Information & Template Selection Should have been displayed.");

//	 Verifying Name Field
		page.navigatingToProductionHomePage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(nameSpChar);
		driver.waitForPageToBeReady();
		base.textFromElementToVerify(page.getProdNameError(), expErrorFrSch);
		base.stepInfo("error message displayed as 'Special characters are not allowed in a production name'");

		page.navigatingToProductionHomePage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(nameLenChar);
		base.textFromElementToVerify(page.getProdNameError(), expErrorLenChar);
		base.stepInfo("error message displayed as 'It is mandatory to provide the production name");

		page.navigatingToProductionHomePage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(nameAlpha);
		base.VerifySuccessMessageB("Mark Complete successful");
		page.navigatingToProductionHomePage();
		page.deleteProduction(nameAlpha);
		base.stepInfo("Alpha characters got accepted as name field");

		page.navigatingToProductionHomePage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(nameAlphaNum);
		base.VerifySuccessMessageB("Mark Complete successful");
		page.navigatingToProductionHomePage();
		page.deleteProduction(nameAlphaNum);
		base.stepInfo("Alpha numeric characters got accepted as name field");

		page.navigatingToProductionHomePage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction("");
		base.textFromElementToVerify(page.getProdNameError(), expErrorFrBlField);
		base.stepInfo("error message displayed as 'It is mandatory to provide the production name'");

//	 Verifying Description Field
		page.navigatingToProductionHomePage();
		page.selectingDefaultSecurityGroup();
		base.waitForElement(page.getAddNewProductionbutton());
		page.getAddNewProductionbutton().Click();
		base.waitForElement(page.getProductionName());
		page.getProductionName().SendKeys(nameAlphaNum);
		base.waitForElement(page.getBasicInfoMarkComplete());
		page.getBasicInfoMarkComplete().waitAndClick(4);
		base.VerifySuccessMessageB("Mark Complete successful");
		page.navigatingToProductionHomePage();
		page.deleteProduction(nameAlphaNum);

//	Verifying Load Template
		page.navigatingToProductionHomePage();
		page.selectingDefaultSecurityGroup();
		page.addANewProductiontWithTemplate(nameAlphaNum, tempName);
		page.verifyDATAndTIFFField();
		page.navigateToNextSection();
		page.verifyingPrefixAndSuffixText(prefixID, suffixID);
		page.navigatingToProductionHomePage();
		page.deleteProduction(nameAlphaNum);

//	Verifying Save, Mark Complete, Next
		page.navigatingToProductionHomePage();
		page.selectingDefaultSecurityGroup();
		base.waitForElement(page.getAddNewProductionbutton());
		page.getAddNewProductionbutton().Click();
		base.waitForElement(page.getBasicInfoSave());
		page.getBasicInfoSave().waitAndClick(5);
		base.textFromElementToVerify(page.getProdNameError(), expErrorFrBlField);
		base.waitForElement(page.getBasicInfoMarkComplete());
		page.getBasicInfoMarkComplete().waitAndClick(4);
		base.textFromElementToVerify(page.getProdNameError(), expErrorFrBlField);
		base.stepInfo("Warning Message displayed for Blank Field");

		page.navigatingToProductionHomePage();
		page.selectingDefaultSecurityGroup();
		base.waitForElement(page.getAddNewProductionbutton());
		page.getAddNewProductionbutton().Click();
		base.ValidateElement_Presence(page.getBasicInfoSaveDisable(), "Next Button Disabled As Expected");
		base.waitForElement(page.getProductionName());
		page.getProductionName().SendKeys(nameAlphaNum);
		base.waitForElement(page.getBasicInfoSave());
		page.getBasicInfoSave().waitAndClick(5);
		base.VerifySuccessMessageB("Information Saved Successfully");
		base.waitForElement(page.getBasicInfoMarkComplete());
		page.getBasicInfoMarkComplete().waitAndClick(4);
		base.VerifySuccessMessageB("Mark Complete successful");

		base.passedStep("Verified -ProjectAdmin will be able to enter basic information on the self production wizard");
		loginPage.logout();
	}

	/**
	 * @author sowndarya.velraj
	 * @Description: Verify that new production should be generated with additional
	 *               placeholder sections in addition to the default enabled native
	 *               placeholdering under TIFF/PDF section [RPMXCON-63076]
	 **/
	@Test(description = "RPMXCON-63076", enabled = true, groups = { "regression" })
	public void verifyNewProdWithAdditionPH() throws Exception {

		base.stepInfo("RPMXCON-63076");
		productionname = "p" + Utility.dynamicNameAppender();
		int doc = 1;
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String prefixIDA = "B_" + Utility.dynamicNameAppender();
		String suffixIDB = "_Q" + Utility.dynamicNameAppender();
		String tagName = "Tag" + Utility.dynamicNameAppender();
		base.stepInfo("Verify that new production should be generated with additional placeholder sections "
				+ "in addition to the default enabled native placeholdering under TIFF/PDF section");

		
		tagsAndFolderPage.createNewTagwithClassification(tagName, Input.tagNamePrev);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch("DocFileType", "Spreadsheet");
		sessionSearch.ViewInDocList();
		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(doc);
		doclist.bulkTagExistingFromDoclist(tagName);

		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.addNewSearch();
		sessionSearch.removeAllAddedTiles();
		sessionSearch.newMetaDataSearchInBasicSearch("DocFileType", "Image");
		sessionSearch.ViewInDocList();
		DocListPage doclist1 = new DocListPage(driver);
		doclist1.documentSelection(doc);
		doclist1.bulkTagExistingFromDoclist(tagName);

		String beginningBates = page.getRandomNumber(2);
		int FirstFile = Integer.valueOf(beginningBates);
		base = new BaseClass(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTiffSectionDisablePrivilegedDocs();
		page.addAdditionalNativPlaceHolder("Images", tagName);
		base.stepInfo(
				"Native placeholdering should be enabled by default with requested text for spreadsheets and multimedia files");
		driver.waitForPageToBeReady();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		page.getConfirmProductionCommit().Enabled();
		page.getConfirmProductionCommit().isDisplayed();
		page.getConfirmProductionCommit().waitAndClick(10);
		page.getCopyPath().isDisplayed();
		page.getCopyPath().waitAndClick(10);
		page.getQC_Download().isDisplayed();
		page.getQC_Download().waitAndClick(30);
		page.getQC_Downloadbutton_allfiles().waitAndClick(30);
		
		base.waitUntilFileDownload();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		driver.waitForPageToBeReady();
//		page.deleteFiles();
		page.extractFile();
		driver.waitForPageToBeReady();
		int Lastile = FirstFile + doc;
		File TiffFile1 = new File(
				home + "/Downloads/" + "VOL0001/Images/0001/" + prefixID + FirstFile + suffixID + ".tif");
		File TiffFile2 = new File(
				home + "/Downloads/" + "VOL0001/Images/0001/" + prefixID + Lastile + suffixID + ".tif");
		String tiff1 = page.extractTextFromTiff(TiffFile1);
		String tiff2 = page.extractTextFromTiff(TiffFile2);
		softAssertion.assertTrue(tiff1.contains("Document Produced in Native Format") || tiff1.contains(tagName));
		softAssertion.assertTrue(tiff2.contains("Document Produced in Native Format") || tiff2.contains(tagName));

		base.stepInfo(
				"Native placeholder should be generated in TIFF for the selected file type as part of selected source of document selection.Make sure that for other file types apart from Native placeholder TIFF file should be generated");
		String beginningBates1 = page.getRandomNumber(2);
		int FirstFile1 = Integer.valueOf(beginningBates1);
		page.navigateToProductionPage();
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSectionDisablePrivilegedDocs();
		page.addAdditionalNativPlaceHolder("Images", tagName);
		base.stepInfo(
				"Native placeholdering should be enabled by default with requested text for spreadsheets and multimedia files");
		driver.waitForPageToBeReady();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixIDA, suffixIDB, beginningBates1);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		page.getConfirmProductionCommit().Enabled();
		page.getConfirmProductionCommit().isDisplayed();
		page.getConfirmProductionCommit().waitAndClick(10);
		page.getCopyPath().isDisplayed();
		page.getCopyPath().waitAndClick(10);
		page.getQC_Download().isDisplayed();
		page.getQC_Download().waitAndClick(30);
		page.getQC_Downloadbutton_allfiles().waitAndClick(30);
		base.waitUntilFileDownload();
		driver.waitForPageToBeReady();
//		page.deleteFiles();
		page.extractFile();
		driver.waitForPageToBeReady();
		int Lastile1 = FirstFile1 + doc;
		File pdfFile1 = new File(home + "/Downloads/" + "VOL0001/PDF/0001/" + prefixIDA + FirstFile1 + suffixIDB + ".pdf");
		File pdfFile2 = new File(home + "/Downloads/" + "VOL0001/PDF/0001/" + prefixIDA + Lastile1 + suffixIDB + ".pdf");
		String pdf1 = page.extractTextFromPdf(pdfFile1);
		String pdf2 = page.extractTextFromPdf(pdfFile2);
		softAssertion.assertTrue(pdf1.contains("Document Produced in Native Format") || pdf1.contains(tagName));
		softAssertion.assertTrue(pdf2.contains("Document Produced in Native Format") || pdf2.contains(tagName));
		softAssertion.assertAll();
		base.stepInfo(
				"Native placeholder should be generated in PDF for the selected file type as part of selected source of document selection.Make sure that for other file types apart from Native placeholder PDF file should be generated");
		base.passedStep("Verified  - that new production should be generated with additional placeholder sections "
				+ "in addition to the default enabled native placeholdering under TIFF/PDF section");
		loginPage.logout();
	}

	/**
	 * @author NA
	 * @Description: To Verify In production, the file-based placeholdering for
	 *               certain file types (Multimedia) [RPMXCON-48177]
	 **/
	@Test(description = "RPMXCON-48177", enabled = true, groups = { "regression" })
	public void verifyProdFileBasedPhForMM() throws Exception {

		base.stepInfo("RPMXCON-48177");
		productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String tagName = "Tag" + Utility.dynamicNameAppender();
		int doc = 1;
		base.stepInfo("To Verify In production, the file-based placeholdering for certain file types");

		
		tagsAndFolderPage.createNewTagwithClassification(tagName, Input.tagNamePrev);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch("SourceDocID", "STC4_00001009");
		base.stepInfo("Source document ID is selected as per pre requisite");
		sessionSearch.ViewInDocList();
		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(doc);
		doclist.bulkTagExistingFromDoclist(tagName);

		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.addNewSearch();
		sessionSearch.removeAllAddedTiles();
		sessionSearch.newMetaDataSearchInBasicSearch("DocFileType", "MP3");
		sessionSearch.ViewInDocList();
		DocListPage doclist1 = new DocListPage(driver);
		doclist1.documentSelection(doc);
		doclist1.bulkTagExistingFromDoclist(tagName);

		String beginningBates = page.getRandomNumber(2);
		int FirstFile = Integer.valueOf(beginningBates);
		base = new BaseClass(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTiffSectionDisablePrivilegedDocs();
		page.addAdditionalNativPlaceHolder("Multimedia", tagName);
		driver.waitForPageToBeReady();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		base.waitUntilFileDownload();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		driver.waitForPageToBeReady();
		page.deleteFiles();
		page.extractFile();
		driver.waitForPageToBeReady();
		int Lastile = FirstFile + doc;
		base.stepInfo("Production should be generate successfully along with placeholder");
		File TiffFile1 = new File(
				home + "/Downloads/" + "VOL0001/Images/0001/" + prefixID + FirstFile + suffixID + ".tif");
		File TiffFile2 = new File(
				home + "/Downloads/" + "VOL0001/Images/0001/" + prefixID + Lastile + suffixID + ".tif");
		page.OCR_Verification_In_Generated_Tiff_tess4j(TiffFile1, "Document Produced in Native Format");
		page.OCR_Verification_In_Generated_Tiff_tess4j(TiffFile2, tagName);
		base.passedStep("Verified In production, the file-based placeholdering for certain file types");
		loginPage.logout();
	}

	/**
	 * @author sowndarya.velraj Testcase No:RPMXCON-63086
	 * @Description: Verify that if spreadsheet is priv and Priv, Native placeholder
	 *               is default enabled from PDF section then production should be
	 *               generated for Priv placeholder
	 **/
	@Test(description = "RPMXCON-63086", enabled = true, groups = { "regression" })
	public void verifyPrivPHinProduction() throws Exception {
		UtilityLog.info(Input.prodPath);
		tagname = "Tag" + Utility.dynamicNameAppender();
		String tagNamePrev = Input.tagNamePrev;
		productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);

		base.stepInfo("Test Cases Id : RPMXCON-63086");
		base.stepInfo("Verify that if spreadsheet is priv and Priv, Native placeholder is default enabled "
				+ "from PDF section then production should be generated for Priv placeholder");
		

		// create tag
		base = new BaseClass(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for tag
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch("DocFileType", "spreadsheet");
		sessionSearch.ViewInDocList();
		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(1);
		doclist.bulkTagExisting(tagname);

		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.SelectPDFGenerateRadioButton();
		page.fillingPrivledgedDocForPDFSection(tagname, tagNamePrev);
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

		base.stepInfo(
				"Priv placeholder should be generated for PDF file for the selected file type as part of selected source of document selection");
		File pdfFile = new File(home + "/Downloads/VOL0001/PDF/0001/" + prefixID + beginningBates + suffixID + ".pdf");
		String text = page.verifyTextinPDF(pdfFile, Input.tagNameTechnical);
		base.stepInfo(text);
		base.passedStep("Verified - that if spreadsheet is priv and Priv, Native placeholder is "
				+ "default enabled from PDF section then production should be generated for Priv placeholder");
		loginPage.logout();
	}

	/**
	 * @author sowndarya.velraj Testcase No:RPMXCON-63087
	 * @Description: Verify that if spreadsheet is tech issue and Tech issue, Native
	 *               placeholder is default enabled from TIFF/PDF section then
	 *               production should be generated for Tech placeholder
	 **/
	@Test(description = "RPMXCON-63087", enabled = true, groups = { "regression" })
	public void verifyTechIssuePHinProd() throws Exception {
		UtilityLog.info(Input.prodPath);
		tagname = "Tag" + Utility.dynamicNameAppender();
		productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		base.stepInfo("Test Cases Id : RPMXCON-63087");
		base.stepInfo("Verify that if spreadsheet is tech issue and Tech issue, Native placeholder is default "
				+ "enabled from TIFF/PDF section then production should be generated for Tech placeholder");
		

		// create tag
		base = new BaseClass(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.technicalIssue);

		// search for tag
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch("DocFileType", "spreadsheet");
		sessionSearch.ViewInDocList();
		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(1);
		doclist.bulkTagExisting(tagname);

		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.selectGenerateOption(true);
		page.selectTechIssueDoc(tagname);
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
		String text = page.verifyTextinPDF(pdfFile, Input.tagNameTechnical);
		base.stepInfo(text);

		page.navigateToProductionPage();
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.selectGenerateOption(false);
		page.selectTechIssueDoc(tagname);
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
		driver.waitForPageToBeReady();
		page.deleteFiles();
		page.extractFile();
		base.stepInfo(
				"Tech placeholder should be generated for TIFF file for the selected file type as part of selected source of document selection");
		File tiffFile = new File(
				home + "/Downloads/VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tif");
		page.OCR_Verification_In_Generated_Tiff_tess4j(tiffFile, Input.tagNameTechnical);
		base.passedStep(
				"Verify that if spreadsheet is tech issue and Tech issue, Native placeholder is default enabled "
						+ "from TIFF/PDF section then production should be generated for Tech placeholder ");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-48886
	 * @Description: Add Multiple line branding to all six locations " + "(TOP LEFT,
	 *               TOP RIGHT, TOP MIDDLE, BOTTOM LEFT, BOTTOM RIGHT, BOTTOM
	 *               MIDDLE) for a Tiff file
	 **/
	@Test(description = "RPMXCON-48886", enabled = true, groups = { "regression" })
	public void VerifyMultiLineBrandingFrTiff() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48886");
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		base.stepInfo("Add Multiple line branding to all six locations "
				+ "(TOP LEFT, TOP RIGHT, TOP MIDDLE, BOTTOM LEFT, BOTTOM RIGHT, BOTTOM MIDDLE) for a Tiff file");

		
		base.stepInfo("Logged in As " + Input.pa1userName);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch("DocFileType", "Spreadsheet");
		sessionSearch.ViewInDocList();
		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(2);
		doclist.bulkTagExistingFromDoclist(tagname);
		base = new BaseClass(driver);
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
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.waitUntilFileDownload();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		page.deleteFiles();
		page.extractFile();
		driver.waitForPageToBeReady();
		File tiffFile = new File(
				home + "/Downloads/" + "VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tif");
		page.OCR_Verification_In_Generated_Tiff_tess4j(tiffFile, "Top - Lef");
		page.OCR_Verification_In_Generated_Tiff_tess4j(tiffFile, "Top - Center Multiple Line Branding");
		page.OCR_Verification_In_Generated_Tiff_tess4j(tiffFile, "Top - Right Multiple Line Branding");
		page.OCR_Verification_In_Generated_Tiff_tess4j(tiffFile, "Bottom - Left Multiple Line Branding");
		page.OCR_Verification_In_Generated_Tiff_tess4j(tiffFile, "Bottom - Center Multiple Line Branding");
		page.OCR_Verification_In_Generated_Tiff_tess4j(tiffFile, "Bottom - Right Multiple Line Branding");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-49820
	 * @Description: Verify that from Productions Basic Info tab "Disclaimer for
	 *               Sightline goes here" should be removed
	 **/
	@Test(description = "RPMXCON-49820", enabled = true, groups = { "regression" })
	public void verifyDisclaimerinNewProdTab() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49820");
		base.stepInfo("To Verify that from Productions Basic Info tab "
				+ "\"Disclaimer for Sightline goes here\" should be removed");
		try {
			loginPage.logout();
		}catch(Exception e) {}
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Logged in as" + Input.da1userName);
		
		base.impersonateDAtoPA();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		base.waitForElement(page.getAddNewProductionbutton());
		page.getAddNewProductionbutton().Click();
		base.stepInfo("Navigated to Basic Info Tab..");
		if (page.getDisclaimerBasicInfoTab().isElementAvailable(3)) {
			base.failedStep("'Disclaimer SightLine goes here' not Removed");
		} else {
			base.passedStep("'Disclaimer Sighline goes here' Removed as Expected");
		}
		base.passedStep(
				"Verified - that from Productions Basic Info tab Disclaimer for Sightline goes here is be removed");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-48887
	 * @Description:Add Single line branding to all six locations (TOP LEFT, TOP
	 *                  RIGHT, TOP MIDDLE, BOTTOM LEFT, BOTTOM RIGHT, BOTTOM MIDDLE)
	 *                  for a PDF file
	 **/
	@Test(description = "RPMXCON-48887", enabled = true, groups = { "regression" })
	public void VerifySingleBrandingFrPDF() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48887");
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		base.stepInfo("Add Single line branding to all six locations "
				+ "(TOP LEFT, TOP RIGHT, TOP MIDDLE, BOTTOM LEFT, BOTTOM RIGHT, BOTTOM MIDDLE) for a PDF file");

		
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
		page.fillingAndverifySixBrandingSingleLine(tagname);
		base.stepInfo("Added a Single line branding to all six  locations");
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
		File pdfFile = new File(
				home + "/Downloads/" + "VOL0001/PDF/0001/" + prefixID + beginningBates + suffixID + ".pdf");
		page.verifyTextinPDF(pdfFile, "Top - Left");
		page.verifyTextinPDF(pdfFile, "Top - Center");
		page.verifyTextinPDF(pdfFile, "Top - Right");
		page.verifyTextinPDF(pdfFile, "Bottom - Left");
		page.verifyTextinPDF(pdfFile, "Bottom - Center");
		page.verifyTextinPDF(pdfFile, "Bottom - Right");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-48888
	 * @Description:Add Multiple line branding to all six locations (TOP LEFT, TOP
	 *                  RIGHT, TOP MIDDLE, BOTTOM LEFT, BOTTOM RIGHT, BOTTOM MIDDLE)
	 *                  for a PDF file
	 **/
	@Test(description = "RPMXCON-48888", enabled = true, groups = { "regression" })
	public void VerifyMultiLineBrandingFrPDF() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48888");
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		base.stepInfo("Add Multiple line branding to all six locations "
				+ "(TOP LEFT, TOP RIGHT, TOP MIDDLE, BOTTOM LEFT, BOTTOM RIGHT, BOTTOM MIDDLE) for a PDF file");

		
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
		base.stepInfo("Added a Single line branding to all six  locations");
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
		File pdfFile = new File(
				home + "/Downloads/" + "VOL0001/PDF/0001/" + prefixID + beginningBates + suffixID + ".pdf");
		page.verifyTextinPDF(pdfFile, "Top - Left Multiple Line Branding");
		page.verifyTextinPDF(pdfFile, "Top - Center Multiple Line Branding");
		page.verifyTextinPDF(pdfFile, "Top - Right Multiple Line Branding");
		page.verifyTextinPDF(pdfFile, "Bottom - Left Multiple Line Branding");
		page.verifyTextinPDF(pdfFile, "Bottom - Center Multiple Line Branding");
		page.verifyTextinPDF(pdfFile, "Bottom - Right Multiple Line Branding");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-48883
	 * @Description: Add Single line branding to all six locations " + "(TOP LEFT,
	 *               TOP RIGHT, TOP MIDDLE, BOTTOM LEFT, BOTTOM RIGHT, BOTTOM
	 *               MIDDLE) for a Tiff file
	 **/
	@Test(description = "RPMXCON-48883", enabled = true, groups = { "regression" })
	public void VerifySingleLineBrandingFrTiff() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48883");
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		base.stepInfo("Add Single line branding to all six locations "
				+ "(TOP LEFT, TOP RIGHT, TOP MIDDLE, BOTTOM LEFT, BOTTOM RIGHT, BOTTOM MIDDLE) for a Tiff file");

		
		base.stepInfo("Logged in As " + Input.pa1userName);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch("DocFileType", "Spreadsheet");
		sessionSearch.ViewInDocList();
		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(2);
		doclist.bulkTagExistingFromDoclist(tagname);
		base = new BaseClass(driver);
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
		page.fillingAndverifySixBrandingSingleLine(tagname);
		base.stepInfo("Added a Single line branding to all six  locations");
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
		File tiffFile = new File(
				home + "/Downloads/" + "VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tif");
		page.OCR_Verification_In_Generated_Tiff_tess4j(tiffFile, "Top - Left");
		page.OCR_Verification_In_Generated_Tiff_tess4j(tiffFile, "Top - Center");
		page.OCR_Verification_In_Generated_Tiff_tess4j(tiffFile, "Top - Right");
		page.OCR_Verification_In_Generated_Tiff_tess4j(tiffFile, "Bottom - Left");
		page.OCR_Verification_In_Generated_Tiff_tess4j(tiffFile, "Bottom - Center");
		page.OCR_Verification_In_Generated_Tiff_tess4j(tiffFile, "Bottom - Right");
		loginPage.logout();
	}

	/**
	 * @author NA Testcase No:RPMXCON-50014
	 * @Description: Verify that for 'AllProductionsBatesRange' field 'isSearchable'
	 *               option is disabled
	 **/
	@Test(description = "RPMXCON-50014", enabled = true, groups = { "regression" })
	public void verifyIsSearchableDisabled() throws InterruptedException {

		base = new BaseClass(driver);
		ProjectPage project = new ProjectPage(driver);
		DomainDashboard dash = new DomainDashboard(driver);

		base.stepInfo("Test case Id: RPMXCON-50014");
		base.stepInfo("Verify that for 'AllProductionsBatesRange' field 'isSearchable' option is disabled");

		String projectName = Input.randomText + Utility.dynamicNameAppender();
		String fieldName = "AllProductionBatesRanges";
try {
	loginPage.logout();
}catch(Exception e) {}
//	//login as sa
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Login as a sa user :" + Input.sa1userName);

		project.navigateToProductionPage();
		base.clearBullHornNotification();
		project.AddDomainProject(projectName, Input.domainName);
		base.waitForNotification();
		dash.getNotificationMessage(0, projectName);

		UserManagement users = new UserManagement(driver);
		users.navigateToUsersPAge();
		users.ProjectSelectionForUser(projectName, Input.pa1FullName, "Project Administrator", "", false, false);
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.selectproject(projectName);
		ProjectFieldsPage projectField = new ProjectFieldsPage(driver);
		projectField.navigateToProjectFieldsPage();
		projectField.applyFilterByFilterName(fieldName);
		base.waitTillElemetToBeClickable(projectField.getApplyButton());
		base.waitForElement(projectField.getFieldNameEdititButton(fieldName));
		projectField.getFieldNameEdititButton(fieldName).waitAndClick(3);
		base.waitForElement(projectField.getIsSearchableCheckBox());
		projectField.getIsSearchableCheckBox().waitAndClick(2);
		if (projectField.getIsSearchableCheckBox().Selected()) {
			base.failedStep("Is Searchable Field Is Enabled");
		} else {
			base.passedStep("Is Searchable Field Is Disabled As Expected");
		}
		base.passedStep("Verified - that for 'AllProductionsBatesRange' field 'isSearchable' option is disabled");
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

		
		base.stepInfo("Logged in As " + Input.pa1userName);

		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.verifyNewLineDimiOptions();
		base.passedStep("Verified - that New Line delimiters on Production");
		loginPage.logout();
	}

	/**
	 * @author sowndarya.velraj Testcase No:RPMXCON-48980
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
	 * @author Brundha.T RPMXCON-49217
	 * @throws Exception
	 * @Description To verify that DocView Images tab should not show the produced
	 *              TIFF/PDF for this uncommitted production
	 * 
	 */
	//@Test(description = "RPMXCON-49217", enabled = true, groups = { "regression" })
	public void verifyDocumentDisplayedInProductionInDocViewPage() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-49217");
		base.stepInfo(
				"To verify that DocView Images tab should not show the produced TIFF/PDF for this uncommitted production");
		base = new BaseClass(driver);

		// Login As PA
		
		base.stepInfo("Logged in As " + Input.pa1userName + "");

		UtilityLog.info(Input.prodPath);
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		DocListPage doclist = new DocListPage(driver);
		DocViewPage doc = new DocViewPage(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();

		doclist.documentSelection(2);
		doclist.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSection(tagname, Input.searchString4);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.ViewInDocViewWithoutPureHit();
		doc.clickOnImageTab();
		base.stepInfo("verifying committed production document in image tab");
		doc.verifyProductionNameForPDFFileInDocView(productionname);

		page.navigateToProductionPage();
		driver.waitForPageToBeReady();
		page.getProductionNameLink(productionname).waitAndClick(5);
		base.waitForElement(page.getConfirmProductionUnCommit());
		page.getConfirmProductionUnCommit().waitAndClick(5);
		driver.Navigate().refresh();
		base.waitTime(3);

		sessionsearch = new SessionSearch(driver);
		sessionSearch.ViewInDocViewWithoutPureHit();
		driver.waitForPageToBeReady();
		base.stepInfo("verifying uncommitted production document not in image tab");
		doc.clickOnImageTab();
		if (!doc.ProductionNameInImageTab(productionname).isDisplayed()) {
			base.passedStep("Produced document is not displayed after uncommitting production");
		} else {

			base.failedStep("Produced document is displayed");
		}
		loginPage.logout();
	}

	/**
	 * @author Brundha.T RPMXCON-49219
	 * @throws Exception
	 * @Description To verify that in Batch Printing, uncommitted production will
	 *              not show up as it was uncommitted
	 * 
	 */
	//@Test(description = "RPMXCON-49219", enabled = true, groups = { "regression" })
	public void verfyingReflectionOfProductionInBatchPrint() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-49219");
		base.stepInfo(
				"To verify that in Batch Printing, uncommitted production will not show up as it was uncommitted");
		base = new BaseClass(driver);

		// Login As PA
		
		base.stepInfo("Logged in As " + Input.pa1userName + "");

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		BatchPrintPage batch = new BatchPrintPage(driver);
		DocListPage doclist = new DocListPage(driver);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();

		doclist.documentSelection(2);
		doclist.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSection(tagname, Input.searchString4);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		batch.navigateToBatchPrintPage();
		batch.fillingSourceSelectionTab("Tag", tagname, true);
		base.waitForElement(batch.getProductionRadioButton());
		batch.getProductionRadioButton().waitAndClick(5);
		base.waitForElementCollection(batch.getProductionList());
		base.stepInfo("verifying committed production is displayed");
		List<String> availableProduction = base.availableListofElements(batch.getProductionList());
		base.stepInfo("Committed production" + availableProduction);
		if (availableProduction != null) {
			if (batch.getBasisProduction(productionname).isElementAvailable(2)) {
				base.passedStep("Committed production is displayed in batch print");
			} else {
				base.failedStep("committed production is not displayed");
			}
		} else {
			base.failedStep("Productions are not displayed");
		}
		page.navigateToProductionPage();
		driver.waitForPageToBeReady();
		page.getProductionNameLink(productionname).waitAndClick(5);
		base.waitForElement(page.getConfirmProductionUnCommit());
		page.getConfirmProductionUnCommit().waitAndClick(5);

		for (int i = 0; i < 3; i++) {
			driver.Navigate().refresh();
			base.waitTime(1);
		}

		batch.navigateToBatchPrintPage();
		batch.fillingSourceSelectionTab("Tag", tagname, true);
		base.waitForElement(batch.getProductionRadioButton());
		batch.getProductionRadioButton().waitAndClick(5);
		base.stepInfo("verifying after uncommit production is not displayed");
		base.waitForElementCollection(batch.getProductionList());
		if (!batch.getBasisProduction(productionname).isElementAvailable(2)) {
			base.passedStep("UnCommitted production is not displayed in batch print");
		} else {
			base.failedStep("Uncommitted production is displayed");
		}
		loginPage.logout();
	}

	/**
	 * @author Brundha.T RPMXCON-49223
	 * @throws Exception
	 * @Description To verify that after uncommit, if user change any Production
	 *              Components, it should regenerate and commit Production
	 *              successfully
	 * 
	 */
	//@Test(description = "RPMXCON-49223", enabled = true, groups = { "regression" })
	public void verfyingRegenartionOfProduction() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-49223");
		base.stepInfo(
				"To verify that after uncommit, if user change any Production Components, it should regenerate and commit Production successfully");
		base = new BaseClass(driver);

		// Login As PA
		
		base.stepInfo("Logged in As " + Input.pa1userName + "");

		UtilityLog.info(Input.prodPath);
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		DocListPage doclist = new DocListPage(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();

		doclist.documentSelection(2);
		doclist.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSection(tagname, Input.searchString4);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
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
		base.stepInfo("Changing DATconfiguration and regenerating the production");
		page.clickElementNthtime(page.getBackButton(), 7);
		driver.scrollPageToTop();
		page.getMarkInCompleteBtn().waitAndClick(5);
		base.waitForElement(page.getDATTab());
		page.getDATTab().waitAndClick(5);
		driver.waitForPageToBeReady();
		page.getDAT_DATField1().waitAndClick(5);
		page.getDAT_DATField1().SendKeys("B" + Utility.dynamicNameAppender());
		page.clickMArkCompleteMutipleTimes(3);
		page.fillingPrivGuardPage();
		page.clickMArkCompleteMutipleTimes(2);

		base.stepInfo("Committing the regenerated production");
		page.fillingGeneratePageWithContinueGenerationPopup();
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


