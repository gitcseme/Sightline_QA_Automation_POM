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

import org.apache.pdfbox.pdmodel.PDDocument;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
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
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Production_Phase2_Regression1 {
	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	DocViewPage docView;
	Utility utility;
	String foldername;
	String tagname;
	String productionname;
	SoftAssert softAssertion;
	ProductionPage page;

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
		softAssertion = new SoftAssert();
		page = new ProductionPage(driver);
		base = new BaseClass(driver);
	}

	/**
	 * @author Brundha TESTCASE No:RPMXCON-49341 Date:9/5/2022
	 * @Description:To verify that Tiff/PDF should generate with Tech Issue
	 *                 placeholdering even though Burn redactions placeholdering is
	 *                 exists in the document.
	 */

	@Test(description = "RPMXCON-49341", enabled = true, groups = { "regression" })

	public void AsverifyingGenerationOfTechPlaceholder() throws Exception {

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(Input.prodPath);

		base.stepInfo("RPMXCON-49341-from Production Component");
		base.stepInfo(
				"To verify that Tiff/PDF should generate with Tech Issue placeholdering even though Burn redactions placeholdering is exists in the document.");
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String foldername = "Folder" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String prefixID1 = Input.randomText + Utility.dynamicNameAppender();
		String suffixID1 = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassificationInRMU(tagname, Input.technicalIssue);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		int PureHit = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.ViewInDocViewWithoutPureHit();

		driver.waitForPageToBeReady();
		docViewRedactions.selectDoc2();
		base.waitTime(2);
		docViewRedactions.redactRectangleUsingOffset(10, 10, 120, 130);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Input.defaultRedactionTag);

		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectGenerateOption(false);
		page.selectTechIssueDoc(tagname);
		page.getClk_burnReductiontoggle().waitAndClick(10);
		page.burnRedactionWithRedactionTag(Input.defaultRedactionTag);
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
		page.extractFile();
		int FirstFile = Integer.valueOf(beginningBates);
		int LastFile = PureHit + FirstFile;
		driver.waitForPageToBeReady();
		page.OCR_Verification_In_Generated_Tiff_tess4j(FirstFile, LastFile, prefixID, suffixID, Input.tagNameTechnical);

		page = new ProductionPage(driver);
		String productionname1 = "p" + Utility.dynamicNameAppender();
		String beginningBates1 = page.getRandomNumber(2);
		int FirstFileNo = Integer.valueOf(beginningBates1);
		page.addANewProduction(productionname1);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectGenerateOption(true);
		page.selectTechIssueDoc(tagname);
		page.getClk_burnReductiontoggle().waitAndClick(10);
		page.burnRedactionWithRedactionTag(Input.defaultRedactionTag);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID1, suffixID1, beginningBates1);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname1);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();
		int LastFiles = PureHit + FirstFileNo;
		driver.waitForPageToBeReady();
		page.pdf_Verification_In_Generated_PlaceHolder(FirstFileNo, LastFiles, prefixID1, suffixID1,
				Input.tagNameTechnical);

		loginPage.logout();
	}

	/**
	 * @author Brundha TESTCASE No:RPMXCON-47860 Date:9/5/2022
	 * @Description:Verify the admin able to regenerate the already completed
	 *                     production.
	 */
	@Test(description = "RPMXCON-47860", enabled = true, groups = { "regression" })

	public void verifyingRegenerationOfProduction() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		BaseClass base = new BaseClass(driver);
		base.stepInfo("RPMXCON-47860-from Production Component");
		base.stepInfo("Verify the admin able to regenerate the already completed production.");

		String foldername = "folder" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String CompareString = "Restart the production from where it left off";
		String ExpectedText = "Restart the Production From the Beginning";

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.addANewProduction(productionname);
		page.fillingDATSection();
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

		page.navigatingToProductionHomePage();
		page.getDatFileLink(productionname).waitAndClick(2);
		page.getBackButton().waitAndClick(5);
		String batesno = page.getProd_BatesRange().getText();
		page.getMarkInCompleteBtn().waitAndClick(5);

		base.ValidateElement_Presence(page.getbtnReGenerateMarkComplete(), "Regenerate Button");
		page.getbtnReGenerateMarkComplete().waitAndClick(5);
		base.validatingGetTextElement(page.getRegenerateOptions(), CompareString);
		base.validatingGetTextElement(page.getRegenerateAllOptions(), ExpectedText);
		page.getbtnRegenerateContinue().waitAndClick(5);
		driver.waitForPageToBeReady();
		base.VerifySuccessMessage("Generation Started Successfully");
		page.getbtnProductionGenerate().isElementAvailable(4);
		base.validatingGetTextElement(page.getbtnProductionGenerate(), "In Progress");
		if (page.getbtnContinueGeneration().isElementAvailable(120)) {
			page.getbtnContinueGeneration().waitAndClick(2);
		}
		base.validatingGetTextElement(page.getProd_BatesRange(), batesno);
		loginPage.logout();
	}

	/**
	 * @author Brundha TESTCASE No:RPMXCON-47793 Date:9/5/2022
	 * @Description:Verify the availability of the options on Production
	 *                     Components-Native
	 */
	@Test(description = "RPMXCON-47793", enabled = true, groups = { "regression" })

	public void verifyingNativeSectionInComponentTab() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-47793-from Production Component");
		base.stepInfo("Verify the availability of the options on Production Components-Native");

		BaseClass base = new BaseClass(driver);
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.addANewProduction(productionname);
		driver.waitForPageToBeReady();
		page.fillingDATSection();
		page.getNativeTab().waitAndClick(5);
		base.validatingGetTextElement(page.getNativeProduceFileOption(), "Produce Native Files");

		driver.scrollingToElementofAPage(page.getNative_AdvToggle());
		page.getNative_AdvToggle().waitAndClick(5);
		base.validatingGetTextElement(page.getDoNotProduceNativeOption(1), "Do not produce Natives of");
		base.validatingGetTextElement(page.getGenerateLSTOption(), "Generate Load File (LST)");
		page.toggleOffCheck(page.getNative_GenerateLoadFileLST());
		base.validatingGetTextElement(page.getNativeRadioButtonChecked(),
				"Parents of Privileged and Redacted Documents");
		base.validatingGetTextElement(page.getNativeRadioButtonUnChecked(),
				"Complete Families of Privileged and Redacted Documents");
		base.passedStep("Verified Advanced option in Native Section");
		loginPage.logout();

	}

	/**
	 * @author Brundha TESTCASE No:RPMXCON-49375 Date:9/5/2022
	 * @Description:To verify that when the option "do not export tiffs for natively
	 *                 produced docs" is enabled,and document is associated to
	 *                 'TechIssue', then production should generate without any
	 *                 error
	 */
	@Test(description = "RPMXCON-49375", enabled = true, groups = { "regression" })

	public void verifyingTiffImageForNativeDoc() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-49375-from Production Component");
		base.stepInfo(
				"To verify that when the option 'do not export tiffs for natively produced docs' is enabled,and document is associated to 'TechIssue', then production should generate without any error");

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String foldername = "folder" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.technicalIssue);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		int PureHit = sessionSearch.basicContentSearch(Input.telecaSearchString);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.selectGenerateOption(false);
		page.selectTechIssueDoc(tagname);
		driver.scrollPageToTop();
		page.getDoNotProduceFullContentTiff().waitAndClick(5);
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
		File dir = new File(home + "/Downloads/VOL0001/Images/0001/");
		File imageFile = new File(
				home + "/Downloads/VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tif");
		page.isfileisExists(imageFile);
		File[] dir_contents = dir.listFiles();
		System.out.println(dir_contents.length);
		int nativefile = dir_contents.length;

		if (PureHit <= nativefile) {
			base.passedStep("TIFFs  produced for native is generated successfully as expected");
		} else {
			base.failedStep("TIFFs  produced for native is not generated successfully as expected");
		}
		loginPage.logout();
	}

	/**
	 * @author Brundha TESTCASE No:RPMXCON-48644 Date:9/5/2022
	 * @Description:To verify that if "Do Not Produce TIFFs for Natively Produced
	 *                 Docs" is disabled, then TIFFs is generated for all docs being
	 *                 produced.
	 */
	@Test(description = "RPMXCON-48644", enabled = true, groups = { "regression" })

	public void verifyTiffGenerationWhenNativeToggleOff() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-48644-from Production Component");
		base.stepInfo(
				"To verify that if 'Do Not Produce TIFFs for Natively Produced Docs' is disabled, then TIFFs is generated for all docs being produced.");

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.telecaSearchString);
		sessionSearch.addNewSearch();
		sessionSearch.SearchMetaData(Input.docFileType, ".tiff");
		sessionSearch.addPureHit();
		sessionSearch.ViewInDocList();

		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(6);
		doclist.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionwithNativelyPlaceholder(tagname);
		driver.scrollPageToTop();
		page.toggleOffCheck(page.getDoNotProduceFullContentTiff());
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

		String home = System.getProperty("user.home");
		page.deleteFiles();
		page.extractFile();
		driver.waitForPageToBeReady();
		page.isdatFileExist();
		File NativeFile = new File(home + "/Downloads/VOL0001/Natives/0001/");
		File imageFile = new File(
				home + "/Downloads/VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tif");
		page.isfileisExists(NativeFile);
		page.isfileisExists(imageFile);
		loginPage.logout();
	}

	/**
	 * @author Brundha TESTCASE No:RPMXCON-48333 Date:5/9/2022
	 * @Description:To verify that if "Do Not Produce TIFFs for Natively Produced
	 *                 Docs" is disabled , then TIFFs is generated with Placeholder
	 */
	@Test(description = "RPMXCON-48333", enabled = true, groups = { "regression" })

	public void verifyingDatSectionForBurnRedactionDocument() throws Exception {

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48333-from Production Component");
		base.stepInfo(
				"To verify that if 'Do Not Produce TIFFs for Natively Produced Docs' is disabled , then TIFFs is generated with Placeholder");

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		String Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();
		RedactionPage redactionpage = new RedactionPage(driver);
		driver.waitForPageToBeReady();
		redactionpage.manageRedactionTagsPage(Redactiontag1);
		System.out.println("First Redaction Tag is created" + Redactiontag1);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassificationInRMU(tagname, "Select Tag Classification");

		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.ViewInDocViewWithoutPureHit();

		docViewRedactions.selectDoc2();
		driver.waitForPageToBeReady();
		docViewRedactions.redactRectangleUsingOffset(15, 10, 100, 100);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag1);

		driver.waitForPageToBeReady();
		docViewRedactions.redactRectangleUsingOffsetWithDoubleClick(5, 5, 60, 60);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Input.defaultRedactionTag);

		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.addDATFieldAtSecondRow(Input.bates, "EndingBates", Input.bates);
		page.getRedactDATCheckBox(1).waitAndClick(5);
		page.fillingNativeSection();
		page.selectGenerateOption(false);
		page.getClk_burnReductiontoggle().waitAndClick(10);
		page.burnRedactionWithRedactionTag(Redactiontag1);
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
		String name = page.getProduction().getText().trim();
		driver.waitForPageToBeReady();
		File DatFile = new File(home + "/Downloads/VOL0001/Load Files/" + name + "_DAT.dat");
		page.isdatFileExist();
		page.verifyingGeneratedDATFile(DatFile, 1, 1, "þ");
		loginPage.logout();

	}

	/**
	 * @author Brundha TESTCASE No:RPMXCON-47721 Date:8/23/2022
	 * @Description:To Verify ProjectAdmin will be able to flag a production as
	 *                 ‘Locked’ after it has been generated.
	 */
	@Test(description = "RPMXCON-47721", enabled = true, groups = { "regression" })

	public void validatingLockOptionInProductionPage() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-47721-from Production");
		base.stepInfo(
				"To Verify ProjectAdmin will be able to flag a production as ‘Locked’ after it has been generated.");
		String foldername = "folder" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
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
		page.fillingGeneratePageWithContinueGenerationPopup();

		page.navigatingToProductionHomePage();
		page.selectingOptionInProduction(productionname);
		page.verfyingBellyBandMsgAndLockProduction();

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Brundha.T No:RPMXCON-48303 Date:8/23/2022
	 * @Description:To verify the value of EndingBates on Production DAT
	 **/

	@Test(description = "RPMXCON-48303", enabled = true, groups = { "regression" })
	public void verifyingDATFileWithEndingBates() throws Exception {

		base.stepInfo("Test case Id:RPMXCON-48303- Production Component");
		base.stepInfo("To verify the value of EndingBates on Production DAT");

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		foldername = "Folder" + Utility.dynamicNameAppender();
		String prefixID = "P" + Utility.dynamicNameAppender();
		String suffixID = "S" + Utility.dynamicNameAppender();
		String prefixID1 = "P" + Utility.dynamicNameAppender();
		String suffixID1 = "S" + Utility.dynamicNameAppender();
		String BatesNumber = "B" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProductionAndSave(productionname);
		page.fillingDATSection();
		page.addDATFieldAtSecondRow(Input.bates, "EndingBates", BatesNumber);
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
		page.extractFile();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		String name = page.getProduction().getText().trim();
		driver.waitForPageToBeReady();
		File DatFile = new File(home + "/Downloads/VOL0001/Load Files/" + name + "_DAT.dat");
		page.isdatFileExist();
		int EndingBates = Integer.valueOf(beginningBates) + 3;
		System.out.println(EndingBates);
		String bates = prefixID + EndingBates + suffixID + "þ";
		page.verifyingGeneratedDATFile(DatFile, 1, 1, bates);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		page = new ProductionPage(driver);
		String beginningBates1 = page.getRandomNumber(2);
		String productionname1 = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname1);
		page.fillingDATSection();
		page.addDATFieldAtSecondRow(Input.bates, "EndingBates", BatesNumber);
		page.selectGenerateOption(false);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID1, suffixID1, beginningBates1);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname1);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();
		driver.waitForPageToBeReady();
		String name1 = page.getProduction().getText().trim();
		int EndingBates1 = Integer.valueOf(beginningBates1) + 3;
		File DatFile1 = new File(home + "/Downloads/VOL0001/Load Files/" + name1 + "_DAT.dat");
		page.isdatFileExist();
		page.verifyingGeneratedDATFile(DatFile1, 1, 1, prefixID1 + EndingBates1 + suffixID1 + "þ");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-48602 Date:8/23/2022
	 * @Description To verify that once export is completed notification should be
	 *              displayed to the user
	 * 
	 */
	@Test(description = "RPMXCON-48602", enabled = true, groups = { "regression" })
	public void verifyNotificationOnExportInGenerateTab() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		BaseClass base = new BaseClass(driver);
		base.stepInfo("RPMXCON-48602 - from Production Component");
		base.stepInfo("To verify that once export is completed notification should be displayed to the user");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = "p" + Utility.dynamicNameAppender();
		String suffixID = "s" + Utility.dynamicNameAppender();
		String SuccessMsg = "Export bates range has been added to background services. You will get notification once completed";

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.selectGenerateOption(false);
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
		page.getbtnProductionGenerate().waitAndClick(10);
		page.verifyProductionStatusInGenPage("Reserving Bates Range Complete");
		base.ValidateElement_Presence(page.getExportBatesButton(), "ExportBates Button");
		base.waitTillElemetToBeClickable(page.getExportBatesButton());
		page.getExportBatesButton().waitAndClick(2);
		base.VerifySuccessMessage(SuccessMsg);
		base.waitTime(2);
		base.ValidateElement_Presence(page.getNotificationLink(), "NotificationIcon");
		page.getNotificationLink().Click();
		page.verifyExportedCSVFile();

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Brundha.T No:RPMXCON-48981 Date:8/23/2022
	 * @Description:To Verify that in productions, the Bates Number field supports
	 *                 Bates Numbers as large as 1,000,000,000
	 **/

	@Test(description = "RPMXCON-48981", enabled = true, groups = { "regression" })
	public void verifyingBatesNumberInGeneratedProduction() throws Exception {

		base.stepInfo("Test case Id:RPMXCON-48981- Production Component");
		base.stepInfo(
				"To Verify that in productions, the Bates Number field supports Bates Numbers as large as 1,000,000,000");

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		foldername = "Folder" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String prefixID = page.getRandomNumber(10);
		String suffixID = page.getRandomNumber(10);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		int PureHit = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int FirstFile = Integer.valueOf(beginningBates);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
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
		page.extractFile();
		driver.waitForPageToBeReady();
		page.isdatFileExist();
		int Lastile = FirstFile + PureHit;
		page.isImageFileExist(FirstFile, Lastile, prefixID, suffixID);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-48327 Date:8/23/2022
	 * @Description To verify that the selected metadata is not displayed in DAT if
	 *              the document has at least one of the selected PRIV tags in PRIV
	 *              placeholdering for TIFF
	 * 
	 */
	@Test(description = "RPMXCON-48327", enabled = true, groups = { "regression" })
	public void verifyingTextInGeneratedDat() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-48327 -Production component");
		base.stepInfo(
				"To verify that the selected metadata is not displayed in DAT if the document has at least one of the selected PRIV tags in PRIV placeholdering for TIFF");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		int PureHit = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.addDATFieldAtSecondRow(Input.productionText, Input.tiffPageCountNam, Input.tiffPageCountText);
		driver.waitForPageToBeReady();
		page.getPrivledgedDATCheckBox(1).waitAndClick(2);
		page.fillingTIFFSectionPrivDocs(tagname, Input.searchString4);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
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
		String name = page.getProduction().getText().trim();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		driver.waitForPageToBeReady();
		File DatFile1 = new File(home + "/Downloads/VOL0001/Load Files/" + name + "_DAT.dat");
		page.isdatFileExist();
		String Empty = "þ";
		for (int i = 1; i < PureHit; i++) {
			page.verifyingGeneratedDATFile(DatFile1, i, 1, Empty);
		}
		driver.waitForPageToBeReady();
		File TiffFile = new File(
				home + "/Downloads/VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tif");
		page.isfileisExists(TiffFile);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-47882 Date:8/23/2022
	 * @Description To Verify Basic info page User should be able to select Custom
	 *              Template (if available under custom template) and move ahead
	 *              with Production
	 * 
	 */
	@Test(description = "RPMXCON-47882", enabled = true, groups = { "regression" })
	public void verifyingLoadTemplateInProduction() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		BaseClass base = new BaseClass(driver);
		base.stepInfo("RPMXCON-47882 -Production component");
		base.stepInfo(
				"To Verify Basic info page User should be able to select Custom Template (if available under custom template) and move ahead with Production");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String productionname1 = "p" + Utility.dynamicNameAppender();
		String Templatename = "T" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionPrivDocs(tagname, Input.searchString4);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();

		page.navigatingToProductionHomePage();
		page.savedTemplateAndNewProdcution(productionname, Templatename);
		page.baseInfoLoadTemplate(productionname1, Templatename);
		driver.scrollPageToTop();
		page.getMarkCompleteLink().waitAndClick(10);
		base.VerifySuccessMessage("Mark Complete successful");
		driver.waitForPageToBeReady();
		page.getNextButton().waitAndClick(2);
		page.visibleCheck("Numbering and Sorting");
		loginPage.logout();
	}

	/**
	 * @author Brundha.T No:RPMXCON-49124 Date:8/25/2022
	 * @Description:To verify that in Production-TechIssue Docs Placeholder section,
	 *                 Metadata Field drop down should be sorted by alpha ascending
	 **/

	@Test(description = "RPMXCON-49124", enabled = true, groups = { "regression" })
	public void verifyingAscendingOrderInTechDocMetaDataField() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		BaseClass base = new BaseClass(driver);
		base.stepInfo("Test case Id:RPMXCON-49124- Production Component");
		base.stepInfo(
				"To verify that in Production-TechIssue Docs Placeholder section, Metadata Field drop down should be sorted by alpha ascending");

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.selectGenerateOption(false);
		page.getTechissue_toggle().ScrollTo();
		page.getTechissue_toggle().waitAndClick(5);
		page.getTechInsertMetadataField().waitAndClick(5);
		List<String> TechIssueMetaDataField = base.availableListofElements(page.InsertMetaDataFieldValues());
		base.verifyOriginalSortOrder(TechIssueMetaDataField, TechIssueMetaDataField, "Ascending", true);

		page.navigatingToProductionHomePage();
		String productionname1 = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname1);
		page.selectGenerateOption(true);
		page.getTechissue_toggle().ScrollTo();
		page.getTechissue_toggle().waitAndClick(5);
		page.getTechInsertMetadataField().waitAndClick(5);
		List<String> PDFTechIssueMetaDataField = base.availableListofElements(page.InsertMetaDataFieldValues());
		base.verifyOriginalSortOrder(PDFTechIssueMetaDataField, PDFTechIssueMetaDataField, "Ascending", true);
		loginPage.logout();
	}

	/**
	 * @author Brundha.T No:RPMXCON-49126 Date:8/25/2022
	 * @Description:To verify that in Production-File Type Group Placeholder
	 *                 section, Metadata Field drop down should be sorted by alpha
	 *                 ascending
	 **/

	@Test(description = "RPMXCON-49126", enabled = true, groups = { "regression" })
	public void verifyingAscendingOrderInFileTypeMetaDataField() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		BaseClass base = new BaseClass(driver);
		base.stepInfo("Test case Id:RPMXCON-49126- Production Component");
		base.stepInfo(
				"To verify that in Production-File Type Group Placeholder section, Metadata Field drop down should be sorted by alpha ascending");

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.selectGenerateOption(false);
		page.getNativePlaceHolderIUnsertMetaDataFeild().ScrollTo();
		page.getNativePlaceHolderIUnsertMetaDataFeild().waitAndClick(5);
		List<String> TechIssueMetaDataField = base.availableListofElements(page.InsertMetaDataFieldValues());
		base.verifyOriginalSortOrder(TechIssueMetaDataField, TechIssueMetaDataField, "Ascending", true);

		page.navigatingToProductionHomePage();
		String productionname1 = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname1);
		page.selectGenerateOption(true);
		page.getNativePlaceHolderIUnsertMetaDataFeild().ScrollTo();
		page.getNativePlaceHolderIUnsertMetaDataFeild().waitAndClick(5);
		List<String> PDFTechIssueMetaDataField = base.availableListofElements(page.InsertMetaDataFieldValues());
		base.verifyOriginalSortOrder(PDFTechIssueMetaDataField, PDFTechIssueMetaDataField, "Ascending", true);
		loginPage.logout();
	}

	/**
	 * @author Brundha.T No:RPMXCON-63065 Date:25/8/2022
	 * @Description:Verify that user should be able to remove the automatically
	 *                     enabled native placeholdering under TIFF/PDF section from
	 *                     new production
	 **/

	@Test(description = "RPMXCON-63065", enabled = true, groups = { "regression" })
	public void verifyingGenerationOfTiffFileForSpreadSheet() throws Exception {

		base = new BaseClass(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case Id:RPMXCON-63065- Production component");
		base.stepInfo(
				"Verify that user should be able to remove the automatically enabled native placeholdering under TIFF/PDF section from new production");
		UtilityLog.info(Input.prodPath);

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "P" + Utility.dynamicNameAppender();
		String prefixID1 = "P" + Utility.dynamicNameAppender();
		String suffixID = "S" + Utility.dynamicNameAppender();
		String suffixID1 = "S" + Utility.dynamicNameAppender();
		String productionname = "P" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");

		SessionSearch sessionSearch = new SessionSearch(driver);
		this.driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionSearch.SearchMetaData(Input.docFileType, Input.MetaDataFileType);
		sessionSearch.ViewInDocList();
		DocListPage doclist = new DocListPage(driver);
		int doc = 3;
		doclist.documentSelection(doc);
		doclist.bulkTagExistingFromDoclist(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int FirstFile = Integer.valueOf(beginningBates);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.verifyingTheDefaultSelectedOptionInNative();
		page.getNativePlaceHolderCloseBtn().waitAndClick(2);
		page.getEnableForNativelyToggle().waitAndClick(2);
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
		int Lastile = FirstFile + doc;
		page.isImageFileExist(FirstFile, Lastile, prefixID, suffixID);

		page.navigateToProductionPage();
		String beginningBates1 = page.getRandomNumber(2);
		String ProductionName1 = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(ProductionName1);
		page.fillingDATSection();
		page.selectGenerateOption(true);
		page.verifyingNativeSectionFileType(Input.MetaDataFileType);
		page.verifyingNativeSectionFileType(Input.NativeFileType);
		page.getNativePlaceHolderCloseBtn().waitAndClick(2);
		page.getEnableForNativelyToggle().waitAndClick(2);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID1, suffixID1, beginningBates1);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(ProductionName1);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		page.extractFile();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		File PdfFile = new File(
				home + "/Downloads/VOL0001/PDF/0001/" + prefixID1 + beginningBates1 + suffixID1 + ".pdf");
		page.isfileisExists(PdfFile);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-49244 Date:8/25/2022
	 * @DescriptionTo verify that if user select Document level option and select
	 *                the Next BatesNumbers then value should be auto-populated
	 * 
	 */
	@Test(description = "RPMXCON-49244", enabled = true, groups = { "regression" })
	public void verifyingNextAvailableBatesNumber() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		BaseClass base = new BaseClass(driver);
		base.stepInfo("RPMXCON-49244 -Production component");
		base.stepInfo(
				"To verify that if user select Document level option and select the Next BatesNumbers then value should be auto-populated");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String productionname1 = "p" + Utility.dynamicNameAppender();
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
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.getBackButton().waitAndClick(5);
		driver.waitForPageToBeReady();
		String batesno = page.getProd_BatesRange().getText();

		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname1);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.getClickHereLink().waitAndClick(2);
		page.getNextBatesNumber().waitAndClick(2);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname1);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.getbtnProductionGenerate().waitAndClick(10);
		page.verifyProductionStatusInGenPage("Reserving Bates Range Complete");
		driver.waitForPageToBeReady();
		String BatesNumber = page.getProd_BatesRange().getText();
		base.textCompareNotEquals(batesno, BatesNumber, "Production Generated with modified BatesNumber",
				"BatesNumber not modified");
		loginPage.logout();

	}

	/**
	 * @author Brundha TESTCASE No:RPMXCON-49342 Date:8/25/2022
	 * @Description:To verify that Tiff/PDF should generate with Tech Issue
	 *                 placeholdering even though File group placeholdering is
	 *                 exists.
	 */
	@Test(description = "RPMXCON-49342", enabled = true, groups = { "regression" })

	public void verifyingGeneratedTechIssuePlaceHolder() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-49342-from Production Component");
		base.stepInfo(
				"To verify that Tiff/PDF should generate with Tech Issue placeholdering even though File group placeholdering is exists.");
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String foldername = "folder" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String prefixID1 = Input.randomText + Utility.dynamicNameAppender();
		String suffixID1 = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.technicalIssue);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		int PureHit = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		int FirstFile = Integer.valueOf(beginningBates);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.selectGenerateOption(false);
		page.selectTechIssueDoc(tagname);
		driver.waitForPageToBeReady();
		driver.scrollingToElementofAPage(page.getNativeDocsPlaceholderText());
		page.getNativeDocsPlaceholderText().SendKeys(Input.testData1);
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
		int LastFile = PureHit + FirstFile;
		driver.waitForPageToBeReady();
		page.OCR_Verification_In_Generated_Tiff_tess4j(FirstFile, LastFile, prefixID, suffixID, Input.tagNameTechnical);

		page = new ProductionPage(driver);
		String productionname1 = "p" + Utility.dynamicNameAppender();
		String beginningBates1 = page.getRandomNumber(2);
		int FirstFileNo = Integer.valueOf(beginningBates1);
		page.addANewProduction(productionname1);
		page.fillingDATSection();
		page.selectGenerateOption(true);
		page.selectTechIssueDoc(tagname);
		driver.waitForPageToBeReady();
		driver.scrollingToElementofAPage(page.getNativeDocsPlaceholderText());
		page.getNativeDocsPlaceholderText().SendKeys(Input.testData1);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID1, suffixID1, beginningBates1);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname1);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();
		int LastFiles = PureHit + FirstFileNo;
		driver.waitForPageToBeReady();
		page.pdf_Verification_In_Generated_PlaceHolder(FirstFileNo, LastFiles, prefixID1, suffixID1,
				Input.tagNameTechnical);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Brundha TESTCASE No:RPMXCON-49343 Date:8/25/2022
	 * @Description:To verify that Tiff/PDF should generate with Priv placeholdering
	 *                 even though Tech Issue placeholdering is exists in the
	 *                 document.
	 */
	@Test(description = "RPMXCON-49343", enabled = true, groups = { "regression" })

	public void verifyingGeneratedPrivDocPlaceHolder() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-49343-from Production Component");
		base.stepInfo(
				"To verify that Tiff/PDF should generate with Priv placeholdering even though Tech Issue placeholdering is exists in the document.");
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String PrivTag = "Tag" + Utility.dynamicNameAppender();
		String foldername = "folder" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String prefixID1 = Input.randomText + Utility.dynamicNameAppender();
		String suffixID1 = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.technicalIssue);
		tagsAndFolderPage.createNewTagwithClassification(PrivTag, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		int PureHit = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(PrivTag);
		sessionSearch.ViewInDocList();
		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(3);
		doclist.bulkTagExistingFromDoclist(tagname);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		int FirstFile = Integer.valueOf(beginningBates);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionPrivDocs(PrivTag, Input.testData1);
		page.selectTechIssueDoc(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		String PrivDoc = page.getPrivDocCountInSummaryPage().getText();
		base.digitCompareEquals(6, Integer.valueOf(PrivDoc), "DocCount Displayed Correctly", "verification failed");
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();
		int LastFile = PureHit + FirstFile;
		driver.waitForPageToBeReady();
		page.OCR_Verification_In_Generated_Tiff_tess4j(FirstFile, LastFile, prefixID, suffixID, Input.testData1);

		page = new ProductionPage(driver);
		String productionname1 = "p" + Utility.dynamicNameAppender();
		String beginningBates1 = page.getRandomNumber(2);
		int FirstFileNo = Integer.valueOf(beginningBates1);
		page.addANewProduction(productionname1);
		page.fillingDATSection();
		page.fillingTIFFSectionPrivDocs(PrivTag, Input.testData1);
		page.selectTechIssueDoc(tagname);
		driver.scrollPageToTop();
		page.getPDFGenerateRadioButton().waitAndClick(5);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID1, suffixID1, beginningBates1);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname1);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();
		int LastFiles = PureHit + FirstFileNo;
		driver.waitForPageToBeReady();
		page.pdf_Verification_In_Generated_PlaceHolder(FirstFileNo, LastFiles, prefixID1, suffixID1, Input.testData1);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Brundha.T No:RPMXCON-49128 Date:8/30/2022
	 * @Description:To verify that in Production-'Redaction Text by Selecting
	 *                 Redaction Tags' section, Metadata Field drop down should be
	 *                 sorted by alpha ascending
	 **/

	@Test(description = "RPMXCON-49128", enabled = true, groups = { "regression" })
	public void verifyingAscendingOrderInBurnRedactionMetaDataField() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		BaseClass base = new BaseClass(driver);
		base.stepInfo("Test case Id:RPMXCON-49128- Production Component");
		base.stepInfo(
				"To verify that in Production-'Redaction Text by Selecting Redaction Tags' section, Metadata Field drop down should be sorted by alpha ascending");

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.selectGenerateOption(false);
		page.getClk_burnReductiontoggle().waitAndClick(5);
		page.getClkLink_selectingRedactionTags().waitAndClick(5);
		page.getBurnRedaction_InsertMetaData().waitAndClick(5);
		List<String> BurnRedactionMetaDataField = base.availableListofElements(page.InsertMetaDataFieldValues());
		base.verifyOriginalSortOrder(BurnRedactionMetaDataField, BurnRedactionMetaDataField, "Ascending", true);

		page.navigatingToProductionHomePage();
		String productionname1 = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname1);
		page.selectGenerateOption(true);
		page.getClk_burnReductiontoggle().waitAndClick(5);
		page.getClkLink_selectingRedactionTags().waitAndClick(5);
		page.getBurnRedaction_InsertMetaData().waitAndClick(5);
		List<String> PDFBurnRedactionMetaDataField = base.availableListofElements(page.InsertMetaDataFieldValues());
		base.verifyOriginalSortOrder(PDFBurnRedactionMetaDataField, PDFBurnRedactionMetaDataField, "Ascending", true);
		loginPage.logout();
	}

	/**
	 * @author Brundha TESTCASE No:RPMXCON-47807 Date:8/30/2022
	 * @Description:To Verify Text generation as part of the actual generation
	 *                 process
	 */
	@Test(description = "RPMXCON-47807", enabled = true, groups = { "regression" })

	public void validatingPresenceOfTextFile() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		base.stepInfo("RPMXCON-47807-from Production Component");
		base.stepInfo("To Verify Text generation as part of the actual generation process");
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String prefixID1 = Input.randomText + Utility.dynamicNameAppender();
		String suffixID1 = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassificationInRMU(tagname, "Select Tag Classification");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearchForTwoItems(Input.testData1, Input.telecaSearchString);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.ViewInDocViewWithoutPureHit();

		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		base.waitTime(2);
		docViewRedactions.redactRectangleUsingOffset(10, 10, 100, 100);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Input.defaultRedactionTag);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTextSection();
		base.ValidateElement_Presence(page.getAnsiRadioBtn(), "Ansi RadioBtn");
		base.ValidateElement_Presence(page.getAnsiDrpDwn(), "Ansi Dropdown");
		base.ValidateElement_Presence(page.getAdvancedOptionInTextTab(), "Advanced Option");
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
		File Textfile = new File(
				home + "/Downloads/VOL0001/Text/0001/" + prefixID + beginningBates + suffixID + ".txt");
		page.isfileisExists(Textfile);

		page = new ProductionPage(driver);
		String productionname1 = "p" + Utility.dynamicNameAppender();
		String beginningBates1 = page.getRandomNumber(2);
		page.addANewProduction(productionname1);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID1, suffixID1, beginningBates1);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname1);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();
		driver.waitForPageToBeReady();
		File Textfile1 = new File(
				home + "/Downloads/VOL0001/Text/0001/" + prefixID1 + beginningBates1 + suffixID1 + ".txt");
		if (!Textfile1.exists()) {
			base.passedStep("Text file is not Exist as expected");
		} else {
			base.failedStep("verification failed");
		}

		loginPage.logout();

	}

	/**
	 * @author Brundha TESTCASE No:RPMXCON-47806 Date:8/30/2022
	 * @Description:To Verify As an Admin be able to include slip sheets for the
	 *                 documents in the generated PDF
	 */
	@Test(description = "RPMXCON-47806", enabled = true, groups = { "regression" })

	public void verifyingSlipsheetValueInPDF() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-47806 -Production Component");
		base.stepInfo("To Verify As an Admin be able to include slip sheets for the documents in the generated PDF");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectGenerateOption(true);
		page.fillingSlipSheet(true, foldername);
		String Text = page.getSlipSheetSelectedText().getText();
		base.compareTextViaContains(Text, foldername, "Slipsheet Text displayed", "Text not displayed");
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

		File pdfFile = new File(home + "/Downloads/VOL0001/PDF/0001/" + prefixID + beginningBates + suffixID + ".pdf");
		page.isfileisExists(pdfFile);
		page.pdf_Verification_In_Generated_PlaceHolder(prefixID, suffixID, beginningBates, foldername);

		loginPage.logout();
	}

	/**
	 * @author Brundha TESTCASE No:RPMXCON-48328 Date:8/30/2022
	 * @Description:To verify that the selected metadata is not displayed only when
	 *                 the doc has at least one of the selected redaction tags in
	 *                 Burn Redactions in Tiff
	 */
	@Test(description = "RPMXCON-48328", enabled = true, groups = { "regression" })

	public void validatingDATSection() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("RPMXCON-48328-from Production Component");
		base.stepInfo(
				"To verify that the selected metadata is not displayed only when the doc has at least one of the selected redaction tags in Burn Redactions in Tiff");
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String BatesNumber = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");

		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.ViewInDocViewWithoutPureHit();

		base.waitTime(2);
		docViewRedactions.redactRectangleUsingOffset(10, 10, 120, 120);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Input.defaultRedactionTag);

		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.addDATFieldAtSecondRow(Input.bates, "EndingBates", BatesNumber);
		page.getRedactDATCheckBox(1).waitAndClick(5);
		page.fillingNativeSection();
		page.selectGenerateOption(false);
		page.getClk_burnReductiontoggle().waitAndClick(5);
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
		String name = page.getProduction().getText().trim();
		driver.waitForPageToBeReady();
		File DatFile = new File(home + "/Downloads/VOL0001/Load Files/" + name + "_DAT.dat");
		page.isdatFileExist();
		page.verifyingGeneratedDATFile(DatFile, 1, 1, "þ");
		loginPage.logout();

	}

	/**
	 * @author Brundha TESTCASE No:RPMXCON-48330 Date:8/30/2022
	 * @Description:To verify that the selected metadata is not displayed in DAT if
	 *                 the doc has at least one of the selected PRIV tags in PRIV
	 *                 placeholdering for PDF
	 */
	@Test(description = "RPMXCON-48330", enabled = true, groups = { "regression" })

	public void validatingDATSectionForPrivDoc() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-48330-from Production Component");
		base.stepInfo(
				"To verify that the selected metadata is not displayed in DAT if the doc has at least one of the selected PRIV tags in PRIV placeholdering for PDF");
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String foldername = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String BatesNumber = Input.randomText + Utility.dynamicNameAppender();
		String bates = "þ";

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		sessionSearch.ViewInDocList();
		DocListPage doc = new DocListPage(driver);
		doc.documentSelection(2);
		doc.bulkTagExistingFromDoclist(tagname);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.addDATFieldAtSecondRow(Input.bates, "EndingBates", BatesNumber);
		page.getPrivledgedDATCheckBox(1).waitAndClick(5);
		page.selectPrivDocsInPDFSection(tagname);
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
		String name = page.getProduction().getText().trim();
		driver.waitForPageToBeReady();
		File DatFile = new File(home + "/Downloads/VOL0001/Load Files/" + name + "_DAT.dat");
		page.isdatFileExist();
		page.verifyingGeneratedDATFile(DatFile, 1, 1, bates);
		loginPage.logout();

	}

	/**
	 * @author Brundha TESTCASE No:RPMXCON-47717 Date:8/30/2022
	 * @Description:To Verify ProjectAdmin will be able to edit configuration of a
	 *                 production that hasn’t yet been locked and bates number has
	 *                 not been committed
	 */
	@Test(description = "RPMXCON-47717", enabled = true, groups = { "regression" })

	public void verifyingTheProductionWithNewConfiguration() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-47717-from Production Component");
		base.stepInfo(
				"To Verify ProjectAdmin will be able to edit configuration of a production that hasn’t yet been locked and bates number has not been committed");

		String foldername = "folder" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String prefixID1 = Input.randomText + Utility.dynamicNameAppender();
		String suffixID1 = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		String beginningBates1 = page.getRandomNumber(2);
		page.addANewProduction(productionname);
		page.fillingDATSection();
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
		String batesno = page.getProd_BatesRange().getText();

		page.clickElementNthtime(page.getBackButton(), 5);
		page.getMarkInCompleteBtn().waitAndClick(5);
		page.fillingNumberingAndSortingTab(prefixID1, suffixID1, beginningBates1);
		page.clickMArkCompleteMutipleTimes(2);
		page.fillingPrivGuardPage();
		page.clickMArkCompleteMutipleTimes(2);
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommitandDownload();
		page.getBackButton().waitAndClick(5);
		String BatesNumber = page.getProd_BatesRange().getText();
		base.textCompareNotEquals(batesno, BatesNumber, "Production Generated with modified BatesNumber",
				"BatesNumber not modified");
		loginPage.logout();
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
		File TiffFile = new File(home + "/Downloads/" + "VOL0001/Images/0001/" + prefixID + File + suffixID + ".tif");
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
		int Bates = Integer.valueOf(beginningBates) + 4;
		driver.waitForPageToBeReady();
		File TiffFile = new File(home + "/Downloads/VOL0001/Load Files/" + productionname + "_TIFF.OPT");
		File Textfile = new File(home + "/Downloads/VOL0001/Text/0001");
		File TextFile = new File(home + "/Downloads/VOL0001/Text/0001/" + prefixID + Bates + suffixID + ".txt");
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
		base.stepInfo("Production Path" + actualCopedText);
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
		String prefixID = "P" + Utility.dynamicNameAppender();
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
		page.addDATFieldAtSecondRow(Input.fldClassification, Input.docName, Input.testData1);
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
		page.fillingDocumentSelectionPageWithTag(tagname, tagname1);
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
		page.navigateToProductionPage();
		String totalProduction = page.getProductionItemsTileItems().getText();
		if (Integer.valueOf(totalProduction) >= Count) {
			base.stepInfo("Navigating to production grid view");
			page.getGridView().waitAndClick(10);
			driver.scrollingToBottomofAPage();
			base.stepInfo("Verifying production grid view pagination");
			page.verifyingGridView();
		} else {
			base.stepInfo("Creating Multiple Production");
			for (int i = 0; i < Count; i++) {
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
		docViewRedactions.redactRectangleUsingOffset(10, 10, 120, 120);
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
				home + "/Downloads/VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tif");
		page.OCR_Verification_In_Generated_Tiff_tess4j(imageFile, PlaceholderText);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		loginPage.logout();

	}

	/**
	 * @author sowndarya Testcase No:RPMXCON-48979
	 * @Description: Verify that branding text should be truncated when Branding
	 *               text exceeds the space and no space after wrapping while
	 *               production for a TIFF file
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
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		SessionSearch sessionSearch = new SessionSearch(driver);
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
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.waitUntilFileDownload();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		page.deleteFiles();
		page.extractFile();
		driver.waitForPageToBeReady();
		File tiffFile = new File(
				home + "/Downloads/" + "VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tif");
		softAssertion.assertTrue(page.extractTextFromTiff(tiffFile).contains("Top - Lef"));
		softAssertion.assertTrue(
				page.extractTextFromTiff(tiffFile).contains("Top - Center Multiple Line Branding Text Verify"));
		softAssertion.assertTrue(
				page.extractTextFromTiff(tiffFile).contains("Top - Right Multiple Line Branding Text Ve..."));
		softAssertion.assertTrue(
				page.extractTextFromTiff(tiffFile).contains("Bottom - Left Multiple Line Branding Text..."));
		softAssertion.assertTrue(
				page.extractTextFromTiff(tiffFile).contains("Bottom - Center Multiple Line Branding Text Ve"));
		softAssertion
				.assertTrue(page.extractTextFromTiff(tiffFile).contains("Bottom - Right Multiple Line Branding Tex.."));
		softAssertion.assertAll();
		base.passedStep("Verify that branding text should be truncated when Branding text "
				+ "exceeds the space and no space after wrapping while production for a TIFF file");
		loginPage.logout();
	}

	/**
	 * @author sowndarya Testcase No:RPMXCON-48976
	 * @Description: Verify that branding text should be wrapped when Branding text
	 *               to all the six locations exceeds the space while production for
	 *               a TIFF file
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
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
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
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.waitUntilFileDownload();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		page.deleteFiles();
		page.extractFile();
		driver.waitForPageToBeReady();
		File tiffFile = new File(
				home + "/Downloads/" + "VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tif");
		softAssertion.assertTrue(
				page.extractTextFromTiff(tiffFile).contains("Top - Center Multiple Line Branding Text Verify"));
		softAssertion.assertTrue(
				page.extractTextFromTiff(tiffFile).contains("Bottom - Center Multiple Line Branding Text Ve"));
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
		/*
		 * String FirstFile = FileExtense.get(0); System.out.println(FirstFile); String
		 * SecondFileFile = FileExtense.get(1); System.out.println(SecondFileFile);
		 * String ThirdFile = FileExtense.get(2).toString().trim();
		 * System.out.println(ThirdFile);
		 */
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
		base.waitTime(2);

		base.stepInfo("verifying the docfile extension in downloaded nativefile");
		int Count = Integer.valueOf(beginningBates) + Integer.valueOf(Input.pageCount);
		int ThirdDoc = Count + +Integer.valueOf(Input.pageCount);
		File Native = new File(
				home + "/Downloads/VOL0001/Natives/0001/" + prefixID + beginningBates + suffixID + ".docx");
		File Native2File = new File(home + "/Downloads/VOL0001/Natives/0001/" + prefixID + Count + suffixID + ".docx");
		File Native3File = new File(
				home + "/Downloads/VOL0001/Natives/0001/" + prefixID + ThirdDoc + suffixID + ".docx");
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
		sessionSearch.metaDataSearchInAdvancedSearch("RequirePDFGeneration", Input.pageCount);
		// sessionSearch.SearchMetaData("RequirePDFGeneration", Input.pageCount);
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

		// production saved as template
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

		// Delete tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();
	}

	/**
	 * @author sowndarya Testcase No:RPMXCON-63236
	 * @Description: Verify that existing Production template should display with
	 *               'Skip text generation/OCR for non-redacted docs' " + "under
	 *               Text section and Production should be generated successfully
	 *               using same template
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

		ProductionPage page = new ProductionPage(driver);
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
		page.fillingGeneratePageWithContinueGenerationPopup();
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
		base.passedStep(
				"Verify that existing Production template should display with 'Skip text generation/OCR for non-redacted docs' under"
						+ " Text section and Production should be generated successfully using same template");
		loginPage.logout();
	}

	/**
	 * @author sowndarya Testcase No:RPMXCON-63237
	 * @Description: Verify when user selects existing production template and
	 *               selects 'Do not OCR non-redacted docs...' " + "in
	 *               Production-text component then it should export blank text for
	 *               non-redacted document.
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

		ProductionPage page = new ProductionPage(driver);
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
		page.fillingGeneratePageWithContinueGenerationPopup();
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
		base.passedStep(
				"Verify when user selects existing production template and selects 'Do not OCR non-redacted docs...'"
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
		page.fillingGeneratePageWithContinueGenerationPopupHigerWaitTime();
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

	/**
	 * @author Brundha Testcase No:RPMXCON-49116
	 * @Description:To verify that the value of Number of MP3 Files on
	 *                 Production-Summary tab if MP3 file component is selected and
	 *                 Priv tags selected in TIFF/PDF component.
	 **/
	@Test(description = "RPMXCON-49116", enabled = true, groups = { "regression" })
	public void verifyingPrivDocCountOnSummaryTab2() throws Exception {
		UtilityLog.info(Input.prodPath);

		base.stepInfo("RPMXCON-49116 -Production Component");
		base.stepInfo(
				"To verify that the value of Number of MP3 Files on Production-Summary tab if MP3 file component is selected and Priv tags selected in TIFF/PDF component.");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String Foldername = "p" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(Foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		this.driver.getWebDriver().get(Input.url + "Search/Searches");
		int PureHit = sessionSearch.metaDataSearchInBasicSearch("DocFileType", ".mp3");
		sessionSearch.bulkFolderExisting(Foldername);
		sessionSearch.ViewInDocListWithOutPureHit();

		DocListPage doc = new DocListPage(driver);
		int docCount = 5;
		doc.documentSelection(docCount);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionPrivDocs(tagname, Input.searchString4);
		page.advancedProductionComponentsMP3();
		driver.scrollPageToTop();
		page.getMarkCompleteLink().waitAndClick(5);
		base.VerifySuccessMessage("Mark Complete successful");
		page.getNextButton().waitAndClick(5);
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(Foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();

		driver.waitForPageToBeReady();
		String Mp3DocCount = page.countOfNumberOfMP3().getText().trim();
		System.out.println(Mp3DocCount);
		int Mp3FileCount = PureHit - docCount;
		System.out.println(Mp3FileCount);
		base.stepInfo("verifying Mp3 Document count in summary tab");
		base.digitCompareEquals(Integer.valueOf(Mp3DocCount), Mp3FileCount,
				"Mp3 document count is displayed as expected", "Mp3 Doc count mismatched");
		loginPage.logout();
	}

	/**
	 * @author Brundha Testcase No:RPMXCON-49381
	 * @Description:Verify 'Advanced Options' should be removed from the DAT
	 *                     component section.
	 **/
	@Test(description = "RPMXCON-49381", enabled = true, groups = { "regression" })
	public void verifyingDATSection2() throws Exception {
		UtilityLog.info(Input.prodPath);

		base.stepInfo("RPMXCON-49381 -Production Component");
		base.stepInfo("Verify 'Advanced Options' should be removed from the DAT component section.");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		String productionname = "p" + Utility.dynamicNameAppender();

		ProductionPage page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		base.waitForElement(page.getDATChkBox());
		page.getDATChkBox().waitAndClick(10);

		base.waitForElement(page.getDATTab());
		page.getDATTab().waitAndClick(10);
		if (!page.getAdvancedToggle().isDisplayed()) {
			base.passedStep("Advanced option is removed from DAT section");
		} else {
			base.failedStep("Advanced option is not removed");
		}
		loginPage.logout();

	}

	/**
	 * @author NA Testcase No:RPMXCON-47975
	 * @Description:To Verify Rich Text configuration in Production branding should
	 *                 be of Arial, Font Size 10 and Bold format
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
		base.stepInfo(
				"To Verify Rich Text configuration in Production branding should be of Arial, Font Size 10 and Bold format");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
//		 create tag
		base = new BaseClass(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
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
		page.fillingGeneratePageWithContinueGenerationPopup();
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
		base.passedStep(
				"Verified -  Rich Text configuration in Production branding should be of Arial, Font Size 10 and Bold format");
		loginPage.logout();
	}

	/**
	 * @author Brundha Testcase No:RPMXCON-49727
	 * @Description:Verify that branding with Bates Number and 'Confidentiality' is
	 *                     applied on all pages for image based documents for
	 *                     generated TIFF/PDF file
	 **/
	@Test(description = "RPMXCON-49727", enabled = true, groups = { "regression" })
	public void verifyingBrandingTextInReGeneratedDocuments2() throws Exception {
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
		base.waitTillElemetToBeClickable(page.getQC_Download());
		base.waitForElement(page.getQC_Downloadbutton_allfiles());
		page.getQC_Downloadbutton_allfiles().Click();
		base.waitUntilFileDownload();
		int Count = Integer.valueOf(beginningBates) + PageCount;
		int LastDoc = Count + PageCount2Doc;
		page.extractFile();
		driver.waitForPageToBeReady();
		String BatesNumber = prefixID + beginningBates + suffixID;
		String BatesNumber1 = prefixID + Count + suffixID;
		String BatesNumber2 = prefixID + LastDoc + suffixID;

		String[] Bates = { BatesNumber, BatesNumber1, BatesNumber2 };
		String home = System.getProperty("user.home");
		for (int i = 0; i < Bates.length; i++) {
			File fileName = new File(home + "/Downloads/VOL0001/PDF/0001/" + Bates[i] + ".pdf");
			driver.waitForPageToBeReady();
			System.out.println(Bates[i]);
			base.waitTime(1);
			if (fileName.exists()) {
				base.passedStep(" Batesnumber is maintained in sequence order");
			} else {
				base.failedStep("Bates number is not maintained in sequence order");
			}
		}
		page.pdfVerificationInDownloadedFile(BatesNumber, PageCount, prefixID, PlaceholderText);
		page.pdfVerificationInDownloadedFile(BatesNumber1, PageCount2Doc, prefixID, PlaceholderText);
		page.pdfVerificationInDownloadedFile(BatesNumber2, PageCount3Doc, prefixID, PlaceholderText);
		loginPage.logout();
	}

	/**
	 * @author Brundha Testcase No:RPMXCON-49118
	 * @Description:To verify that value of Redacted Documents on Production-Summary
	 *                 tab
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
		tagsAndFolderPage.CreateFolder(Foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.comments);
		sessionSearch.bulkFolderExisting(Foldername);
		sessionSearch.ViewInDocViewWithoutPureHit();

		DocViewRedactions DocRedactions = new DocViewRedactions(driver);
		DocRedactions.doclistTable(2).waitAndClick(5);
		driver.waitForPageToBeReady();
		DocRedactions.redactRectangleUsingOffset(10, 10, 60, 60);
		DocRedactions.selectingRedactionTag2(Redactiontag1);

		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		DocRedactions.doclistTable(3).waitAndClick(5);
		driver.waitForPageToBeReady();
		DocRedactions.redactRectangleUsingOffset(10, 10, 70, 70);
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
		String RedactDocCount = page.redactedDocCountInSummaryPage().getText();
		System.out.println(RedactDocCount);
		base.stepInfo("verifying Redacted Document count in summary tab");
		base.digitCompareEquals(Integer.valueOf(RedactDocCount), 3, "Redacted document count is displayed as expected",
				"Redacted Doc count mismatch");
		loginPage.logout();

	}

	/**
	 * @author Brundha Testcase No:RPMXCON-49117
	 * @Description:To verify that the value of Privileged Documents on production
	 *                 summary tab
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
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(Foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(Foldername);
		sessionSearch.ViewInDocListWithOutPureHit();

		DocListPage doc = new DocListPage(driver);
		int docCount = 2;
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
		String PrivDocCount = page.getPrivDocCountInSummaryPage().getText().trim();
		System.out.println(PrivDocCount);
		base.stepInfo("verifying Priviledged Document count in summary tab");
		base.digitCompareEquals(Integer.valueOf(PrivDocCount), docCount,
				"Priviledged document count is displayed as expected", "Priviledged Doc count mismatch");
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
		int PureHit = sessionSearch.metaDataSearchInBasicSearch("SourceDocID", "35ID00000182");
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
		page.fillingProductionLocationPageAdditonal(productionname);
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
				home + "/Downloads/VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tif");
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
		page.fillingProductionLocationPageAdditonal(productionname);
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
				home + "/Downloads/VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tif");
		page.isfileisExists(TiffFile);
		page.verificationOfTiffFile(firstFile, ImageFiles, prefixID, suffixID);
		// loginPage.logout();
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

		DocViewPage docView = new DocViewPage(driver);
		int TotalPages = docView.docViewDocPageCount();
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
		page.fillingProductionLocationPageAdditonal(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.validatingGetTextElement(page.getBlankPageRemovalMsg(), "Success");
		page.extractFile();
		String home = System.getProperty("user.home");
		base.waitTime(2);
		PDDocument doc = PDDocument
				.load(new File(home + "/Downloads/VOL0001/PDF/0001/" + prefixID + beginningBates + suffixID + ".pdf"));
		int count = doc.getNumberOfPages();
		System.out.println(count);
		base.digitCompareEquals(TotalPages, count, "Blank Page is not removed as expected", "Blank Page is Removed");
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
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SecurityGroupsPage sg = new SecurityGroupsPage(driver);
		sg.navigateToSecurityGropusPageURL();
		sg.createSecurityGroups(securityGroup);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
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

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		SessionSearch sessionSearch = new SessionSearch(driver);
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
		System.out.println("******TEST CASES FOR EXPORT EXECUTED******");

	}
}
