package testScriptsRegressionSprint20;

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

public class Production_Regression20_01 {
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
		base.validatingGetTextElement(page.getRegenerateOptions(),CompareString);
		base.validatingGetTextElement(page.getRegenerateAllOptions(),ExpectedText);
		page.getbtnRegenerateContinue().waitAndClick(5);
		driver.waitForPageToBeReady();
		base.VerifySuccessMessage("Generation Started Successfully");
		page.getbtnProductionGenerate().isElementAvailable(4);
		base.validatingGetTextElement(page.getbtnProductionGenerate(),"In Progress");
		if (page.getbtnContinueGeneration().isElementAvailable(120)) {
			page.getbtnContinueGeneration().waitAndClick(2);
		}
		base.validatingGetTextElement(page.getProd_BatesRange(),batesno);
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
		base.validatingGetTextElement(page.getNativeProduceFileOption(),"Produce Native Files");

		driver.scrollingToElementofAPage(page.getNative_AdvToggle());
		page.getNative_AdvToggle().waitAndClick(5);
		base.validatingGetTextElement(page.getDoNotProduceNativeOption(1),"Do not produce Natives of");
		base.validatingGetTextElement(page.getGenerateLSTOption(),"Generate Load File (LST)");
		page.toggleOffCheck(page.getNative_GenerateLoadFileLST());
		base.validatingGetTextElement(page.getNativeRadioButtonChecked(),"Parents of Privileged and Redacted Documents");
		base.validatingGetTextElement(page.getNativeRadioButtonUnChecked(),"Complete Families of Privileged and Redacted Documents");
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
				home + "/Downloads/VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tiff");
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
				home + "/Downloads/VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tiff");
		page.isfileisExists(NativeFile);
		page.isfileisExists(imageFile);
		loginPage.logout();
	}

	/**
	 * @author Brundha TESTCASE No:RPMXCON-48333 Date:5/9/2022
	 * @Description:To verify that if "Do Not Produce TIFFs for Natively Produced Docs" is disabled , then TIFFs is generated with Placeholder
	 */
	@Test(description = "RPMXCON-48333", enabled = true, groups = { "regression" })

	public void verifyingDatSectionForBurnRedactionDocument() throws Exception {

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48333-from Production Component");
		base.stepInfo("To verify that if 'Do Not Produce TIFFs for Natively Produced Docs' is disabled , then TIFFs is generated with Placeholder");

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
