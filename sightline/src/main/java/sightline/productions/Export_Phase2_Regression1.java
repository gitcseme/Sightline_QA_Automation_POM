package sightline.productions;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
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
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Export_Phase2_Regression1 {
	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	DocViewPage docView;
	Utility utility;
	TagsAndFoldersPage tag;
	ProductionPage page;
	SessionSearch search;
	SoftAssert soft;
	
	String prefixID;
	String suffixID;
	String foldername;
	String tagName;
	String exportName;
	String productionname;
	String beginningBates;
	String subBates;
	String templateName;
	
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
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());

		
		driver = new Driver();
		base = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		page = new ProductionPage(driver);
		tag=new TagsAndFoldersPage(driver);
		search=new SessionSearch(driver);
		soft = new SoftAssert();
		
	}
	
	/**
	 * Author :Aathith
	 * date: 08/17/2022
	 * Description :To Verify Export using Production as Basis if there is count for "Number Of Export Document Selection Mismatch"
	 */
	@Test(description = "RPMXCON-47937", enabled = true, groups = { "regression" })
	public void verifyProductionBasicWithExport() throws Exception {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case Id: RPMXCON-47937");
		base.stepInfo("To Verify Export using Production as Basis if there is count for \"Number Of Export Document Selection Mismatch\"");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		String foldername1 = "FolderProd" + Utility.dynamicNameAppender();
		tagName = "Tag" + Utility.dynamicNameAppender();
		prefixID = Input.randomText + Utility.dynamicNameAppender();
		suffixID = Input.randomText + Utility.dynamicNameAppender();
	
		tag = new TagsAndFoldersPage(driver);
		tag.CreateFolder(foldername, Input.securityGroup);
		tag.CreateFolder(foldername1, Input.securityGroup);
		tag.createNewTagwithClassification(tagName, Input.tagNamePrev);
		
		search = new SessionSearch(driver);
		search.basicContentSearch(Input.testData1);
		search.bulkFolderExisting(foldername);
		search.bulkFolderExisting(foldername1);
		search.bulkTagExisting(tagName);	
		
		page = new ProductionPage(driver);
		beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.getSelectFolder(foldername1).waitAndClick(5);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		
		page.navigatingToProductionHomePage();
		exportName = "Export" + Utility.dynamicNameAppender();
		templateName = "Temp" + Utility.dynamicNameAppender();
		page.savedTemplateAndNewProdcution(productionname, templateName);
		subBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExportWithTemplate(exportName,templateName);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportName);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingExportGeneratePageWithContinueGenerationPopup();
		base.passedStep("Export done successfully with out any error");
		
		tag.navigateToTagsAndFolderPage();
		tag.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tag.DeleteTagWithClassification(tagName, Input.securityGroup);
		tag.DeleteFolderWithSecurityGroup(foldername1, Input.securityGroup);
		
		loginPage.logout();
		base.passedStep("Verified Export using Production as Basis if there is count for \"Number Of Export Document Selection Mismatch\"");

	}
	
	
	/**
	 * Author :Aathith
	 * date: 08/17/2022
	 * Description :To Verify file group type (.mdb/.mdf) option on selection in Translations section, the corresponding translations should be considered for Export.
	 */
	@Test(description = "RPMXCON-48036", enabled = true, groups = { "regression" })
	public void verifyMBDFileTypeInExport() throws Exception {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case Id: RPMXCON-48036");
		base.stepInfo("To Verify file group type (.mdb/.mdf) option on selection in Translations section, the corresponding translations should be considered for Export.");

		tagName = "Tag" + Utility.dynamicNameAppender();
		prefixID = Input.randomText + Utility.dynamicNameAppender();
		suffixID = Input.randomText + Utility.dynamicNameAppender();
		String[] souredocids = {"STC4_00000992", "STC4_00000001", "STC4_00000028"};
	
		tag = new TagsAndFoldersPage(driver);
		tag.createNewTagwithClassification(tagName, Input.tagNamePrev);
		
		search = new SessionSearch(driver);
		search.basicSourceDocIdsSearch(souredocids);
		search.bulkTagExisting(tagName);	
		
		page = new ProductionPage(driver);
		exportName = "Export" + Utility.dynamicNameAppender();
		templateName = "Temp" + Utility.dynamicNameAppender();
		subBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExport(exportName);
		page.fillingDATSection();
		page.fillingTIFFSectionwithNativelyPlaceholder(tagName);
		page.ddnselectFileType().selectFromDropdown().selectByVisibleText("Database Files (.mdb, .mdf, etc.)");
		page.fillingTranslationByFileType(".mdb");
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportName);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingExportGeneratePageWithContinueGenerationPopup();
		
		tag.navigateToTagsAndFolderPage();
		tag.DeleteTagWithClassification(tagName, Input.securityGroup);
		
		loginPage.logout();
		base.passedStep("Verified file group type (.mdb/.mdf) option on selection in Translations section, the corresponding translations should be considered for Export.");

	}
	
	/**
	 * Author :Aathith
	 * date: 08/17/2022
	 * Description :Verify that if PA selects the Export with Production and has only tags selected in the native components section, then Component tab should Complete without any error.
	 */
	@Test(description = "RPMXCON-49358", enabled = true, groups = { "regression" })
	public void verifyProductionComponentInExport() throws Exception {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case Id: RPMXCON-49358");
		base.stepInfo("Verify that if PA selects the Export with Production and has only tags selected in the native components section, then Component tab should Complete without any error.");

		tagName = "Tag" + Utility.dynamicNameAppender();
		prefixID = Input.randomText + Utility.dynamicNameAppender();
		suffixID = Input.randomText + Utility.dynamicNameAppender();
	
		tag = new TagsAndFoldersPage(driver);
		tag.createNewTagwithClassification(tagName, Input.tagNamePrev);
		
		search = new SessionSearch(driver);
		search.basicContentSearch(Input.testData1);
		search.bulkTagExisting(tagName);	
		
		page = new ProductionPage(driver);
		beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		
		page.navigatingToProductionHomePage();
		exportName = "Export" + Utility.dynamicNameAppender();
		templateName = "Temp" + Utility.dynamicNameAppender();
		page.savedTemplateAndNewProdcution(productionname, templateName);
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExportWithTemplate(exportName,templateName);
		driver.waitForPageToBeReady();
		page.getDATTab().waitAndClick(10);
		page.getElementDisplayCheck(page.getDAT_FieldClassification1());
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		base.waitTillElemetToBeClickable(page.getMarkCompleteLink());
		page.getMarkCompleteLink().waitAndClick(10);
		base.VerifySuccessMessage("Mark Complete successful");
		
		tag.navigateToTagsAndFolderPage();
		tag.DeleteTagWithClassification(tagName, Input.securityGroup);
		
		loginPage.logout();
		base.passedStep("Verify that if PA selects the Export with Production and has only tags selected in the native components section, then Component tab should Complete without any error.");

	}
	
	/**
	 * Author :Aathith
	 * date: 08/17/2022
	 * Description :Verify  Native section in Production Components section
	 */
	@Test(description = "RPMXCON-49384", enabled = true, groups = { "regression" })
	public void verifyNativeSection() throws Exception {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		soft = new SoftAssert();
		
		base.stepInfo("Test case Id: RPMXCON-49384");
		base.stepInfo("Verify  Native section in Production Components section");

		String export = "Export Set"+ Utility.dynamicNameAppender();
	
		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.createNewExport(export);
		page.getProdExport_ProductionSets().selectFromDropdown().selectByVisibleText(export+" (Export Set)");
		exportName = "Export" + Utility.dynamicNameAppender();
		page.addANewExport(exportName);
		page.getNativeTab().waitAndClick(5);
		
		//verify
		soft.assertTrue(base.text("Produce Native Files").isElementAvailable(4));
		soft.assertEquals(page.getNative_text_Color().GetAttribute("class"),"blue-text");
		base.passedStep("Newly text was added in Production");
		
		page.getNative_AdvToggle().waitAndClick(5);
		soft.assertTrue(base.text("Do not produce Natives of").isElementAvailable(5));
		soft.assertTrue(base.text("Generate Load File (LST)").isElementAvailable(3));
		soft.assertTrue(base.text("Parents of Privileged and Redacted Documents").isElementAvailable(3));
		soft.assertTrue(base.text("Complete Families of Privileged and Redacted Documents").isElementAvailable(3));
		base.passedStep("Verified Advanced option");
		
		soft.assertAll();
		loginPage.logout();
		base.passedStep("Verified  Native section in Production Components section");

	}

	/**
	 * Author :Arunkumar date: 09/08/2022 TestCase Id:RPMXCON-49135
	 * Description :Verify in Production-Export, if default option is selected in text section, Privileged placeholder is enabled 
	 * in TIFF/PDF component,and no text file exists in the SL Workspace then Priv placeholder text is exported
	 */
	@Test(description = "RPMXCON-49135", enabled = true, groups = { "regression" })
	public void verifyPrivPlaceholderTextExported() throws Exception {
		
		base.stepInfo("Test case Id: RPMXCON-49135");
		base.stepInfo("Verify Priv placeholder text is exported");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagName = "Tag" + Utility.dynamicNameAppender();
		String newExport = "Export" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
	
		TagsAndFoldersPage tag = new TagsAndFoldersPage(driver);
		tag.CreateFolder(foldername, Input.securityGroup);
		tag.createNewTagwithClassification(tagName, Input.tagNamePrev);
		SessionSearch search = new SessionSearch(driver);
		int purehit = search.basicContentSearch(Input.telecaSearchString);
		search.bulkFolderExisting(foldername);
		search.bulkTagExisting(tagName);
		search.bulkRelease(Input.securityGroup);
		ProductionPage page = new ProductionPage(driver);
		String subBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		String text = page.getProdExport_ProductionSets().getText();
		if (text.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(newExport);
		}
		page.addANewExport(productionname);
		page.fillingDATSection();
		page.selectPrivDocsInTiffSection(tagName);
		base.stepInfo("Select text component and mark complete");
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		base.stepInfo("generate production and verify");
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommitandDownload();
		String actualCopedText = page.getCopiedTextFromClipBoard();
		String parentTab = page.openNewTab(actualCopedText);
		page.goToImageFiles();
		page.verifyTiffFile(purehit, prefixID, suffixID, subBates, Input.tagNameTechnical);
		driver.close();
		driver.getWebDriver().switchTo().window(parentTab);
		loginPage.logout();

	}
	
	/**
	 * Author :Arunkumar date: 10/08/2022 TestCase Id:RPMXCON-47491
	 * Description :To verify that in Production-Export-Slip Sheet, Calculated Field should be sorted by alpha ascending 
	 */
	@Test(description = "RPMXCON-47491", enabled = true, groups = { "regression" })
	public void verifyCalculatedTabSortOrder() throws Exception {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case Id: RPMXCON-47491");
		base.stepInfo("Verify that in Production-Export-Slip Sheet, Calculated Field should be sorted by alpha ascending");

		String productionname = "p" + Utility.dynamicNameAppender();
		String newExport = "Export" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();	
		String text = page.getProdExport_ProductionSets().getText();
		if (text.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(newExport);
		}
		page.addANewExport(productionname);
		base.stepInfo("Verify the order for Tiff");
		base.waitForElement(page.getTIFFTab());
		page.getTIFFTab().waitAndClick(2);
		page.slipSheetToggleEnable();
		base.waitForElement(page.getSlipCalculatedTabSelection());
		page.getSlipCalculatedTabSelection().waitAndClick(2);
		driver.scrollingToBottomofAPage();
		base.waitForElementCollection(page.getCalculatedTabMetadata());
		List<String> Values = base.availableListofElements(page.getCalculatedTabMetadata());
		base.verifyOriginalSortOrder(Values, Values, "Ascending", true);
		base.passedStep("Meta data list for Tiff sorted by ascending order");
		driver.scrollPageToTop();
		base.stepInfo("Verify the order for pdf");
		base.waitForElement(page.getPDFGenerateRadioButton());
		page.getPDFGenerateRadioButton().waitAndClick(2);
		driver.scrollingToBottomofAPage();
		base.waitForElementCollection(page.getCalculatedTabMetadata());
		List<String> PdfValues = base.availableListofElements(page.getCalculatedTabMetadata());
		base.verifyOriginalSortOrder(PdfValues, PdfValues, "Ascending", true);
		base.passedStep("Meta data list for Pdf sorted by ascending order");
		loginPage.logout();

	}
	
	/**
	 * @author Brundha.T No:RPMXCON-63067 Date:25/8/2022
	 * @Description:Verify that user should be able to remove the automatically
	 *                     enabled native placeholdering under TIFF/PDF section from
	 *                     new export
	 **/

	@Test(description = "RPMXCON-63067", enabled = true, groups = { "regression" })
	public void verifyTheProductionGenerationUsingTemplate() throws Exception {

		base = new BaseClass(driver);
		base.stepInfo("Test case Id:RPMXCON-63067- Production component");
		base.stepInfo(
				"Verify that user should be able to remove the automatically enabled native placeholdering under TIFF/PDF section from new export");
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		String tagName = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "P" + Utility.dynamicNameAppender();
		String prefixID1 = "P" + Utility.dynamicNameAppender();
		String suffixID = "S" + Utility.dynamicNameAppender();
		String suffixID1 = "S" + Utility.dynamicNameAppender();
		String exportName = "Ex" + Utility.dynamicNameAppender();
		String exportName1 = "Ex" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tag = new TagsAndFoldersPage(driver);
		tag.createNewTagwithClassification(tagName, "Select Tag Classification");

		SessionSearch search = new SessionSearch(driver);
		this.driver.getWebDriver().get(Input.url + "Search/Searches");
		search.basicContentSearch(Input.testData1);
		search.addNewSearch();
		search.SearchMetaData(Input.docFileType, Input.MetaDataFileType);
		search.addPureHit();

		search.ViewInDocList();
		DocListPage doclist = new DocListPage(driver);
		int doc = 6;
		doclist.documentSelection(doc);
		doclist.bulkTagExistingFromDoclist(tagName);
		ProductionPage page = new ProductionPage(driver);
		String subBates = page.getRandomNumber(2);

		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExport(exportName);
		page.fillingDATSection();
		page.verifyingTheDefaultSelectedOptionInNative();
		page.getNativePlaceHolderCloseBtn().waitAndClick(2);
		page.getEnableForNativelyToggle().waitAndClick(2);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportName);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommitandDownload();
		String actualCopedText = page.getCopiedTextFromClipBoard();
		String parentTab = page.openNewTab(actualCopedText);
		page.goToImageFiles();
		for (int i = 2; i < doc; i++) {
			page.getFirstImageFile(prefixID + "(" + i + ")" + suffixID, subBates).isElementAvailable(2);
		}
		driver.close();
		driver.getWebDriver().switchTo().window(parentTab);

		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		String subBates1 = page.getRandomNumber(2);
		page.selectDefaultExport();
		page.addANewExport(exportName1);
		page.fillingDATSection();
		page.selectGenerateOption(true);
		page.verifyingNativeSectionFileType(Input.MetaDataFileType);
		page.verifyingNativeSectionFileType(Input.NativeFileType);
		driver.scrollingToElementofAPage(page.getNativePlaceHolderCloseBtn());
		page.getNativePlaceHolderCloseBtn().Click();
		page.getEnableForNativelyToggle().waitAndClick(2);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID1, suffixID1, subBates1);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportName1);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommitandDownload();
		String actualCopedTexts = page.getCopiedTextFromClipBoard();
		String parentTabs = page.openNewTab(actualCopedTexts);
		page.goToPDFImageFiles();
		for (int i = 2; i < doc; i++) {
			page.getFirstPDFImageFile(prefixID1 + "(" + i + ")" + suffixID1, subBates1).isElementAvailable(2);
		}
		driver.close();
		driver.getWebDriver().switchTo().window(parentTabs);
		loginPage.logout();
	}
	
	/**
	 * @author Brundha TESTCASE No:RPMXCON-63077 Date:9/14/2022
	 * @Description:Verify that new production export should be generated with
	 *                     additional placeholder sections in addition to the
	 *                     default enabled native placeholder under TIFF/PDF section
	 */
	@Test(description = "RPMXCON-63077", enabled = true, groups = { "regression" })

	public void verifyTiffGenerationWhenNativeToggleOff() throws Exception {
		UtilityLog.info(Input.prodPath);
		BaseClass base = new BaseClass(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-63077-from Production Component");
		base.stepInfo(
				"Verify that new production export should be generated with additional placeholder sections in addition to the default enabled native placeholder under TIFF/PDF section");

		String tagName = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String prefixID1 = Input.randomText + Utility.dynamicNameAppender();
		String suffixID1 = Input.randomText + Utility.dynamicNameAppender();
		String exportName = Input.randomText + Utility.dynamicNameAppender();
		String exportName1 = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tag = new TagsAndFoldersPage(driver);
		tag.createNewTagwithClassification(tagName, "Select Tag Classification");

		SessionSearch search = new SessionSearch(driver);
		search.basicContentSearch(Input.testData1);
		search.addNewSearch();
		search.SearchMetaData(Input.docFileType, Input.MetaDataFileType);
		search.addPureHit();
		search.ViewInDocList();

		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(10);
		doclist.documentSelection(3);
		doclist.bulkTagExisting(tagName);

		ProductionPage page = new ProductionPage(driver);
		String subBates = page.getRandomNumber(2);

		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExport(exportName);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.verifyingTheDefaultSelectedOptionInNative();
		page.getTiff_NativeDoc().waitAndClick(10);

		driver.waitForPageToBeReady();
		page.getNativeFileType(Input.fileTypeInNativeDocs).waitAndClick(5);
		driver.waitForPageToBeReady();
		page.getNativeDocsPlaceholder1().SendKeys(Input.searchString4);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportName);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommitandDownload();
		String actualCopedText = page.getCopiedTextFromClipBoard();
		System.out.println(actualCopedText);
		base.stepInfo("Export Path" + actualCopedText);
		String parentTab = page.openNewTab(actualCopedText);

		page.goToImageFiles();
		page.verifyTiffFile(7, prefixID, suffixID, subBates, Input.searchString4);
//		for (int i = 2; i < 6; i++) {
//			if (page.getFirstImageFile(prefixID + "(" + i + ")" + suffixID, subBates).isElementAvailable(2)) {
//				base.passedStep("Tiff files are generated successfully");
//			} else if (page.getFirstImageFile(prefixID + "0" + "(" + i + ")" + suffixID, subBates)
//					.isElementAvailable(2)) {
//				base.passedStep("Tiff files are generated successfully");
//			} else {
//				base.failedStep("Tiff file is not generated");
//			}
//		}
		driver.waitForPageToBeReady();
		page.navigatingBack(2);
		page.getFileDir("Natives/").waitAndClick(10);
		page.getFileDir("0001/").waitAndClick(10);
		driver.waitForPageToBeReady();
		page.verifyingGeneratedExporttedfile(5, prefixID, suffixID, subBates, ".doc");
		driver.close();
		driver.getWebDriver().switchTo().window(parentTab);

		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		String subBates1 = page.getRandomNumber(2);
		page.selectDefaultExport();
		page.addANewExport(exportName1);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectGenerateOption(true);
		page.verifyingNativeSectionFileType(Input.MetaDataFileType);
		page.verifyingNativeSectionFileType(Input.NativeFileType);
		page.getTiff_NativeDoc().waitAndClick(10);
		driver.waitForPageToBeReady();
		page.getNativeFileType(Input.fileTypeInNativeDocs).waitAndClick(5);
		driver.waitForPageToBeReady();
		page.getNativeDocsPlaceholder1().SendKeys(Input.searchString4);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID1, suffixID1, subBates1);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportName1);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommitandDownload();
		String actualCopedTexts = page.getCopiedTextFromClipBoard();
		System.out.println(actualCopedTexts);
		base.stepInfo("Export Path" + actualCopedTexts);
		String parentTabs = page.openNewTab(actualCopedTexts);
		page.goToPDFImageFiles();
		for (int i = 2; i < 6; i++) {
			if (page.getFirstPDFImageFile(prefixID1 + "(" + i + ")" + suffixID1, subBates1).isElementAvailable(5)) {
				base.passedStep("Pdf files are generated successfully");
			}
		}

		driver.waitForPageToBeReady();
		for (int i = 2; i < 6; i++) {
			File imageFile = new File(
					Input.fileDownloadLocation + prefixID1 + "(" + i + ")" + suffixID1+ ".000" + subBates1 + ".pdf");
			page.OCR_Verification_In_Generated_Tiff_tess4j(imageFile, Input.searchString4);
		}
		page.navigatingBack(2);
		page.getFileDir("Natives/").waitAndClick(10);
		page.getFileDir("0001/").waitAndClick(10);
		driver.waitForPageToBeReady();
		page.verifyingGeneratedExporttedfile(5, prefixID1, suffixID1, subBates1, ".doc");
		driver.close();
		driver.getWebDriver().switchTo().window(parentTabs);
		loginPage.logout();
	}

	/**
	 * @author Brundha TESTCASE No:RPMXCON-64077 Date:9/14/2022
	 * @Description:Verify for new export Native, Spreadsheets, and Multimedia
	 *                     checkbox should be checked
	 */
	@Test(description = "RPMXCON-64077", enabled = true, groups = { "regression" })

	public void verifyingNativeFilesInGenreratedExport() throws Exception {
		UtilityLog.info(Input.prodPath);
		BaseClass base = new BaseClass(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-64077-from Production Component");
		base.stepInfo("Verify for new export Native, Spreadsheets, and Multimedia checkbox should be checked");

		String tagName = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String exportName = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tag = new TagsAndFoldersPage(driver);
		tag.createNewTagwithClassification(tagName, "Select Tag Classification");

		SessionSearch search = new SessionSearch(driver);
		search.SearchMetaData(Input.ingDocFileType, Input.MetaDataFileType);
		search.addPureHit();
		search.ViewInDocList();

		DocListPage doclist = new DocListPage(driver);
		driver.waitForPageToBeReady();
		doclist.selectingSingleValueInCoumnAndRemovingExistingOne(Input.docFileExt);
		int DocFileExtension = base.getIndex(doclist.getHeaderText(), Input.docFileExt);
		List<String> FileExtense = base.availableListofElements(doclist.GetColumnData(DocFileExtension));
		String FirstFile = FileExtense.get(0).toString().trim();
		System.out.println(FirstFile);
		doclist.documentSelection(3);
		doclist.bulkTagExisting(tagName);

		ProductionPage page = new ProductionPage(driver);
		String subBates = page.getRandomNumber(2);

		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExport(exportName);
		page.fillingDATSection();
		page.getNativeTab().waitAndClick(5);
		page.verifyingNativeSectionFileType(Input.MetaDataFileType);
		page.verifyingNativeSectionFileType(Input.NativeFileType);
		page.getNative_AdvToggle().waitAndClick(5);
		page.getNative_GenerateLoadFileLST().waitAndClick(10);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportName);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommitandDownload();
		String actualCopedText = page.getCopiedTextFromClipBoard();
		String parentTab = page.openNewTab(actualCopedText);
		System.out.println(actualCopedText);
		base.stepInfo("Export Path" + actualCopedText);
		page.getFileDir("VOL0001").waitAndClick(10);
		page.getFileDir("Natives/").waitAndClick(10);
		page.getFileDir("0001/").waitAndClick(10);
		driver.waitForPageToBeReady();
		page.verifyingGeneratedExporttedfile(2, prefixID, suffixID, subBates, FirstFile);
		driver.close();
		driver.getWebDriver().switchTo().window(parentTab);
	}
	
	/**
	 * @author Brundha.T TESTCASE No:RPMXCON-50629
	 * @Description:Verify that Export should generated successfully and documents
	 *                     should exported with Comments/Signautre
	 **/
	@Test(description = "RPMXCON-50629", enabled = true, groups = { "regression" })
	public void verifyingCommentsInGeneratedExportFile() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-50629");
		base.stepInfo(
				"Verify that Export should generated successfully and documents should exported with Comments/Signautre");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		String Tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String exportName = "Export" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);

		TagsAndFoldersPage tag = new TagsAndFoldersPage(driver);
		tag.createNewTagwithClassification(Tagname, "Select Tag Classification");

		DataSets dataset = new DataSets(driver);
		base.stepInfo("Navigating to dataset page");
		dataset.navigateToDataSetsPage();
		base.stepInfo("Selecting dataset and navigating to doclist page");
		dataset.SelectingUploadedDataSetViewInDoclist("RPMXCON40140");
		DocListPage doc = new DocListPage(driver);
		driver.waitForPageToBeReady();

		doc.documentSelection(1);
		doc.bulkTagExisting(Tagname);
		doc.documentSelection(1);
		base.waitTime(4);
		doc.docListToDocView();
		DocViewPage docview = new DocViewPage(driver);
		base.waitTime(6);
		base.waitTillElemetToBeClickable(docview.getDocView_IconDownload());
		docview.getDocView_IconDownload().Click();
		base.waitTillElemetToBeClickable(docview.getDOcViewDoc_DownloadOption("PDF"));
		docview.getDOcViewDoc_DownloadOption("PDF").Click();
		base.waitUntilFileDownload();
		String fileName = base.GetFileName();
		String ActualPDFText = page.verifyingTextInPDFFile(fileName);
		
		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExport(exportName);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(Tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportName);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommitandDownload();
		String actualCopedText = page.getCopiedTextFromClipBoard();
		System.out.println(actualCopedText);
		base.stepInfo("Export Path" + actualCopedText);
		String parentTab = page.openNewTab(actualCopedText);
		
		base.stepInfo("Accessing the Copypoth URL");
		String[] Values= {"VOL0001","Natives/","0001/"};
		for(int i=0;i<Values.length;i++) {
			driver.waitForPageToBeReady();
			if(page.getFileDir(Values[i]).isElementAvailable(2)) {
			page.getFileDir(Values[i]).waitAndClick(10);
			}else {
				base.stepInfo("URL is not accessible");
				base.failedStep("URL is not found");
			}
		}
		base.stepInfo("getting the text inside pdf document");
		if (page.getFirstPDFImageFile(prefixID + suffixID, subBates).isElementAvailable(2)) {

			page.getFirstPDFImageFile(prefixID + suffixID, subBates).waitAndClick(10);
			String CurrentUrl=driver.getWebDriver().getCurrentUrl();
			System.out.println(CurrentUrl);
			 String DownloadedFile = page.getPdfContent(CurrentUrl);
			 soft = new SoftAssert();
			 base.stepInfo("verifying whether the comments are applied in all pages");
			 if (ActualPDFText.equals(DownloadedFile)) {
				 base.passedStep("Comments are displayed in all pages of downloaded file");
				} else  {
					base.failedStep( "Comments are not displayed");
				}

		} else if (page.getFirstPDFImageFile(prefixID + "Auto4053606;Auto5208918" + suffixID, subBates).isElementAvailable(2)) {

			page.getFirstPDFImageFile(prefixID + "Auto4053606;Auto5208918" + suffixID, subBates).waitAndClick(10);
			String CurrentUrl=driver.getWebDriver().getCurrentUrl();
			System.out.println(CurrentUrl);
			 String DownloadedFile2 = page.getPdfContent(CurrentUrl);
			 soft.assertEquals(ActualPDFText,DownloadedFile2);
			 base.passedStep("Comments are displayed in all pages of downloaded file");
		} else {
			base.failedStep("PDF file is not generated");
		}
		driver.close();
		driver.getWebDriver().switchTo().window(parentTab);
		loginPage.logout();

	}
	
		
	
	/**
	 * @author Brundha.T TESTCASE No:RPMXCON-50648
	 * @Description: Verify that after Post Generation is completed, it will
	 *               displays status on Export Progress bar Tile View as 'Completed'
	 **/
	@Test(description = "RPMXCON-50648", enabled = true, groups = { "regression" })
	public void verifyCompletedStatusInTileView() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-50648");
		base.stepInfo(
				"Verify that after Post Generation is completed, it will displays status on Export Progress bar Tile View as 'Completed'");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		String FolderName = "FolderName" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String exportName = "Export" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);

		TagsAndFoldersPage tag = new TagsAndFoldersPage(driver);
		tag.CreateFolder(FolderName, Input.securityGroup);

		search.basicContentSearch(Input.testData1);
		search.bulkFolderExisting(FolderName);

		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExport(exportName);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(FolderName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportName);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickOnGenerateButton();
		page.verifyProductionStatusInGenPage("Reserving Bates Range Complete");
		page.getbtnContinueGenerate().isElementAvailable(3);
		if (page.getbtnContinueGenerate().isDisplayed()) {
			page.getbtnContinueGenerate().waitAndClick(2);
		}

		page.navigateToProductionPage();
		page.selectDefaultExport();
		base.stepInfo("verifying Completed status in Tile view");
		page.verifyingProductionStatusInHomePage("Post-Gen QC Checks In Progress", exportName);
		page.verifyingProductionStatusInHomePage("Completed", exportName);
		loginPage.logout();

	}

	/**
	 * @author Brundha.T TESTCASE No:RPMXCON-50647
	 * @Description: Verify that after Post Generation is completed, it will
	 *               displays status on Export generation page as 'Completed'
	 **/
	@Test(description = "RPMXCON-50647", enabled = true, groups = { "regression" })
	public void verifyCompletedStatusInGenPage() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-50647");
		base.stepInfo(
				"Verify that after Post Generation is completed, it will displays status on Export generation page as 'Completed'");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		String FolderName = "FolderName" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String exportName = "Export" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);

		TagsAndFoldersPage tag = new TagsAndFoldersPage(driver);
		tag.CreateFolder(FolderName, Input.securityGroup);

		search.basicContentSearch(Input.testData1);
		search.bulkFolderExisting(FolderName);

		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExport(exportName);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(FolderName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportName);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommitandDownload();
		driver.waitForPageToBeReady();
		page.getBackButton().waitAndClick(5);
		base.stepInfo("verifying Completed status in Generate Page");
		page.verifyProductionStatusInGenPage("Completed");
		loginPage.logout();

	}
	
	/**
	 * @author Brundha.T TESTCASE No:RPMXCON-50669
	 * @Description: Verify that after Pregen checks completed it should displays
	 *               'Pre-Gen Checks Complete' status on Production-Export Grid View
	 **/
	@Test(description = "RPMXCON-50669", enabled = true, groups = { "regression" })
	public void verifyPreGenCheckCompletedStatusInGridView() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-50669");
		base.stepInfo(
				"Verify that after Pregen checks completed it should displays 'Pre-Gen Checks Complete' status on Production-Export Grid View");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		String tagName = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String exportName = "Export" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);

		TagsAndFoldersPage tag = new TagsAndFoldersPage(driver);
		tag.CreateTagwithClassification(tagName, "Select Tag Classification");

		search.basicContentSearch(Input.testData1);
		search.bulkTagExisting(tagName);

		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExport(exportName);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportName);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickOnGenerateButton();

		page.navigateToProductionPage();
		page.selectDefaultExport();
		driver.waitForPageToBeReady();
		page.getGridView().waitAndClick(10);
		base.stepInfo("verifying Pre-Gen Checks Complete status in Grid view");
		page.verifyProductionStatusInHomePageGridView("Pre-Gen Checks Complete", exportName);
		loginPage.logout();
	}

	/**
	 * @author Brundha.T TESTCASE No:RPMXCON-50646
	 * @Description: Verify that once Post Geneation is in progress, it will
	 *               displays status on Export Generation page as 'Post-Gen QC
	 *               checks in progress'
	 **/
	@Test(description = "RPMXCON-50646", enabled = true, groups = { "regression" })
	public void verifyPostGenInProgressInGenPage() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-50646");
		base.stepInfo(
				"Verify that once Post Geneation is in progress, it will displays status on Export Generation page as 'Post-Gen QC checks in progress'");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		String FolderName = "Folder" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String exportName = "Export" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);

		TagsAndFoldersPage tag = new TagsAndFoldersPage(driver);
		tag.CreateFolder(FolderName, Input.securityGroup);

		search.basicContentSearch(Input.telecaSearchString);
		search.bulkFolderExisting(FolderName);

		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExport(exportName);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(FolderName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportName);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickOnGenerateButton();
		page.verifyProductionStatusInGenPage("Reserving Bates Range Complete");
		page.getbtnContinueGenerate().isElementAvailable(3);
		if (page.getbtnContinueGenerate().isDisplayed()) {
			page.getbtnContinueGenerate().waitAndClick(2);
		}
		base.stepInfo("verifying Post-Gen QC Checks In Progress status in Generate page");
		page.verifyProductionStatusInGenPage("Post-Generation QC Checks In Progress");
		loginPage.logout();

	}

	/**
	 * @author Brundha.T TESTCASE No:RPMXCON-50645
	 * @Description: Verify that once Post Geneation is in progress, it will
	 *               displays status on Production Progress bar ,Tile View as
	 *               'Post-Gen checks in progress'
	 **/
	@Test(description = "RPMXCON-50645", enabled = true, groups = { "regression" })
	public void verifyPostGenCheckInProgressStatusInTileView() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-50645");
		base.stepInfo(
				"Verify that once Post Geneation is in progress, it will displays status on Production Progress bar ,Tile View as 'Post-Gen checks in progress'");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		String FolderName = "FolderName" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String exportName = "Export" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);

		TagsAndFoldersPage tag = new TagsAndFoldersPage(driver);
		tag.CreateFolder(FolderName, Input.securityGroup);

		search.basicContentSearch(Input.telecaSearchString);
		search.bulkFolderExisting(FolderName);

		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExport(exportName);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(FolderName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportName);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickOnGenerateButton();
		page.verifyProductionStatusInGenPage("Reserving Bates Range Complete");
		page.getbtnContinueGenerate().isElementAvailable(3);
		if (page.getbtnContinueGenerate().isDisplayed()) {
			page.getbtnContinueGenerate().waitAndClick(2);
		}

		page.navigateToProductionPage();
		page.selectDefaultExport();
		base.stepInfo("verifying Post-Gen QC Checks In Progress status in Tile view");
		page.verifyingProductionStatusInHomePage("Creating Archive Complete/Skipped", exportName);
		page.verifyingProductionStatusInHomePage("Post-Gen QC Checks In Progress", exportName);
		loginPage.logout();

	}

	/**
	 * @author Brundha.T TESTCASE No:RPMXCON-50666
	 * @Description:Verify that Production status displays as Draft on Production
	 *                     Grid View
	 **/
	@Test(description = "RPMXCON-50666", enabled = true, groups = { "regression" })
	public void verifyDraftStatusInGridView() throws Exception {
		base.stepInfo("Test case Id: RPMXCON-50666");
		base.stepInfo("Verify that Production status displays as Draft on Production Grid View");

		String[] UserName = { Input.pa1userName, Input.rmu1userName };
		String[] Password = { Input.pa1password, Input.rmu1password };
		for (int i = 0; i < UserName.length; i++) {

			loginPage.loginToSightLine(UserName[i], Password[i]);
			base.stepInfo(UserName[i] + " logged in to sightline successfully");

			String FolderName = "Folder" + Utility.dynamicNameAppender();
			String prefixID = Input.randomText + Utility.dynamicNameAppender();
			String suffixID = Input.randomText + Utility.dynamicNameAppender();
			String exportName = "Export" + Utility.dynamicNameAppender();
			String subBates = page.getRandomNumber(2);

			TagsAndFoldersPage tag = new TagsAndFoldersPage(driver);
			tag.CreateFolder(FolderName, Input.securityGroup);

			search.basicContentSearch(Input.testData1);
			search.bulkFolderExisting(FolderName);

			base = new BaseClass(driver);
			page.navigateToProductionPage();
			page.selectingDefaultSecurityGroup();
			page.selectDefaultExport();
			page.addANewExportAndSave(exportName);
			page.fillingDATSection();
			page.navigateToNextSection();
			page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
			page.navigateToNextSection();
			page.fillingDocumentSelectionPage(FolderName);
			page.navigateToNextSection();
			page.fillingPrivGuardPage();
			page.fillingExportLocationPage(exportName);
			page.navigateToNextSection();
			page.fillingSummaryAndPreview();
			base.waitForElement(page.getbtnProductionGenerate());

			page.navigateToProductionPage();
			page.selectDefaultExport();
			driver.waitForPageToBeReady();
			page.getGridView().waitAndClick(10);
			base.stepInfo("verifying Draft status in grid view");
			page.verifyProductionStatusInHomePageGridView("DRAFT", exportName);
			loginPage.logout();
		}
	}
	
	/**
	 * @author  sowndarya.velraj  created on:NA modified by:NA TESTCASE No:RPMXCON-49247
	 * @Description: verify In Export DAT, provide the TIFFPageCount for each document
	 **/
	@Test(description = "RPMXCON-49247", enabled = true, groups = { "regression" })
	public void verifyExportDatProvideTiffpageCount() throws Exception {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case Id: RPMXCON-49247");
		base.stepInfo("To verify In Export DAT, provide the TIFFPageCount for each document");

		String tagName = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
	    String exportName = "Export" + Utility.dynamicNameAppender();
	    String subBates = page.getRandomNumber(2);
	    
	    tag.CreateTagwithClassification(tagName, Input.tagNamePrev);

		search.basicContentSearch(Input.searchString1);
		search.ViewInDocList();		
   		DocListPage doclist = new DocListPage(driver);
   		doclist.documentSelection(2);
   		doclist.bulkTagExisting(tagName);	
		
		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExport(exportName);
		page.fillingDATSection();
        page.addDATFieldAtSecondRow(Input.productionText, Input.tiffPageCountNam, Input.tiffPageCountText);
		page.addDATFieldAtThirdRow(Input.docBasic,Input.docName,Input.documentID);
		page.fillingNativeSection();	
		page.selectGenerateOption(false);
		page.fillingAdvancedInTiffSection();
		base.waitForElement(page.getTIFFTab());
		page.getTIFFTab().waitAndClick(5);
		page.fillingSlipSheet(true, tagName);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportName);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();	
		page.fillingExportGeneratePageWithContinueGenerationPopup();
		String actualCopedText = page.getCopiedTextFromClipBoard();
		String parentTab = page.openNewTab(actualCopedText);	
		base.waitForElement(page.getFileDir("VOL0001"));
		page.getFileDir("VOL0001").waitAndClick(3);
		base.waitForElement(page.getLoadFileLink());
		page.getLoadFileLink().waitAndClick(3);
		base.waitForElement(page.getDatFileLink(exportName));
		page.getDatFileLink(exportName).waitAndClick(3);
		String DatFileText = page.getDATFileText().getText();
		System.out.println(DatFileText);
		String[] c = DatFileText.split("\\s+");
		for (int i = 1; i < c.length; i++) {
			String Dat = c[i];
			String[] DatFile = Dat.split("þ");
			System.out.println(Integer.valueOf(DatFile[3]));
			base.digitCompareEquals(Integer.valueOf(DatFile[3]), Integer.valueOf(Input.pageCount),
					"Tiffpage count is displayed", "Tiff page count is not displayed");
		}
		driver.close();
		driver.getWebDriver().switchTo().window(parentTab);
		
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		exportName = "Export" + Utility.dynamicNameAppender();
		page.addANewExport(exportName);
		page.fillingDATSection();
        page.addDATFieldAtSecondRow(Input.productionText, Input.tiffPageCountNam, Input.tiffPageCountText);
		page.addDATFieldAtThirdRow(Input.docBasic,Input.docName,Input.documentID);
		page.fillingNativeSection();	
		page.selectGenerateOption(true);
		page.fillingSlipSheet(true, tagName);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportName);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();	
		page.fillingExportGeneratePageWithContinueGenerationPopup();
		String actualCopedText1 = page.getCopiedTextFromClipBoard();
		String parentTab1 = page.openNewTab(actualCopedText1);	
		base.waitForElement(page.getFileDir("VOL0001"));
		page.getFileDir("VOL0001").waitAndClick(3);
		base.waitForElement(page.getLoadFileLink());
		page.getLoadFileLink().waitAndClick(3);
		base.waitForElement(page.getDatFileLink(exportName));
		page.getDatFileLink(exportName).waitAndClick(3);
		String DatFileText1 = page.getDATFileText().getText();
		System.out.println(DatFileText1);
		String[] c1 = DatFileText1.split("\\s+");
		for (int i = 1; i < c1.length; i++) {
			String Dat = c1[i];
			String[] DatFile = Dat.split("þ");
			System.out.println(Integer.valueOf(DatFile[3]));
			base.digitCompareEquals(Integer.valueOf(DatFile[3]), Integer.valueOf(Input.pageCount),
					"Tiffpage count is displayed", "Tiff page count is not displayed");
		}
		driver.close();
		driver.getWebDriver().switchTo().window(parentTab1);

		// delete tags and folders
		tag.navigateToTagsAndFolderPage();
		driver.waitForPageToBeReady();
		tag.DeleteTagWithClassification(tagName, Input.securityGroup);
		loginPage.logout();
	}
	
	
	 /**
     * @author  sowndarya.velraj  created on:NA modified by:NA TESTCASE No:RPMXCON-47978
     * @Description: Verify In Production, Bates Number for branding & in Productions (Exports), Bates information in the export data.
     **/
    @Test(description = "RPMXCON-47978", enabled = true, groups = { "regression" })
    public void verifyBatesNumBrandinExpData() throws Exception {
        loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
        base.stepInfo("Test case Id: RPMXCON-47978");
        base.stepInfo("To Verify In Production, Bates Number for branding & in Productions (Exports), Bates information in the export data.");

       String tagName = "Tag" + Utility.dynamicNameAppender();
        String prefixID = Input.randomText + Utility.dynamicNameAppender();
        String suffixID = Input.randomText + Utility.dynamicNameAppender();
        String prefixID1= Input.randomText + Utility.dynamicNameAppender();
        String suffixID1 = Input.randomText + Utility.dynamicNameAppender();
        String exportName = "Export" + Utility.dynamicNameAppender();
        String subBates = page.getRandomNumber(2);
        productionname = "p" + Utility.dynamicNameAppender();
        String beginningBates = page.getRandomNumber(2);
        String prodBatesRange = "B1-10";
        
        tag.CreateTagwithClassification(tagName, Input.tagNamePrev);
        int purehit = search.basicContentSearch(Input.testData1);
        search.bulkTagExisting(tagName);    

       base = new BaseClass(driver);
        page.navigateToProductionPage();
        page.selectingDefaultSecurityGroup();
        page.addANewProduction(productionname);
        page.fillingDATSection();
        page.addDATFieldAtSecondRow(Input.bates, "ProductionBatesRange", prodBatesRange);
        page.tiffBrandingSelection(tagName);
        page.tiffPrivilegeDocumentSelection(tagName);
        page.slipSheetToggleEnable();
        page.navigateToNextSection();
        page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
        page.navigateToNextSection();
        page.fillingDocumentSelectionWithTag(tagName);
        page.navigateToNextSection();
        page.fillingPrivGuardPage();
        page.fillingProductionLocationPage(productionname);
        page.navigateToNextSection();
        page.fillingSummaryAndPreview();
        page.fillingGeneratePageWithContinueGenerationPopup();
        
        page.navigateToProductionPage();
        page.selectingDefaultSecurityGroup();
        page.selectDefaultExport();
        page.addANewExportwithProduction(exportName,productionname);
        page.getNextButton().waitAndClick(10);
        page.navigateToNextSection();
        page.navigateToNextSection();
        page.fillingDocumentSelectionWithTag(tagName);
        page.navigateToNextSection();
        base.waitForElement(page.getOkButton());
        page.getOkButton().waitAndClick(3);
        page.fillingExportLocationPage(exportName);
        page.navigateToNextSection();
        page.fillingSummaryAndPreview();
        page.fillingExportGeneratePageWithContinueGenerationPopup();
        
        String batesRange = prefixID + beginningBates + suffixID ;
        String actualCopedText = page.getCopiedTextFromClipBoard();
        String parentTab = page.openNewTab(actualCopedText);   
        page.goToImageFiles();
        page.verifyTiffFile(purehit, prefixID, suffixID, subBates, batesRange);
        
        // delete tags and folders
        tag.navigateToTagsAndFolderPage();
        tag.DeleteTagWithClassification(tagName, Input.securityGroup);
        loginPage.logout();
    }
	
	/**
	 * @author sowndarya.velraj created on:NA modified by:NA TESTCASE No:RPMXCON-63853
	 * @Description: Verify that for existing production/export/template with configured natively placeholdering,should be with enabled native placeholdering under TIFF/PDF section
	 **/
	@Test(description = "RPMXCON-63853", enabled = true, groups = { "regression" })
	public void verifyExisProdWithConfigNativPH() throws Exception {
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case Id: RPMXCON-63853");
		base.stepInfo("Verify that for existing production/export/template with configured natively placeholdering,"
				+ " should be with enabled native placeholdering under TIFF/PDF section");

		String tagName = "Tag" + Utility.dynamicNameAppender();
	    String tempTIFF = "Temp" + Utility.dynamicNameAppender();
	    String tempPDF = "Temp" + Utility.dynamicNameAppender();
	    
	    tag.createNewTagwithClassification(tagName, Input.tagNamePrev);
	    search.basicContentSearch(Input.testData1);
		search.bulkTagExisting(tagName);	
		
		base = new BaseClass(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
        page.fillingTIFFSectionwithNativelyPlaceholder(tagName);
	    page.navigateToNextSection();
		page.navigateToProductionPage();
		page.saveProductionAsTemplateAndVerifyProductionComponentsInManageTemplateTab(productionname, tempTIFF);
		
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
        page.fillingPDFSectionwithNativelyPlaceholder(tagName);
	    page.navigateToNextSection();
		page.navigateToProductionPage();
		page.saveProductionAsTemplateAndVerifyProductionComponentsInManageTemplateTab(productionname, tempPDF);
		
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		String exportName = "Export" + Utility.dynamicNameAppender();
		page.addANewExportWithTemplate(exportName, tempTIFF);	
		base.waitForElement(page.getTIFFTab());
		page.getTIFFTab().waitAndClick(3);
		page.toggleOnCheck(page.getEnableForNativelyToggle());
		driver.scrollingToBottomofAPage();
		base.waitForElement(page.getNativeDocsPlaceholderText());
		String actphTextTiff = page.getNativeDocsPlaceholderText().getText();
		soft.assertEquals(tagName, actphTextTiff);


		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		exportName = "Export" + Utility.dynamicNameAppender();
		page.addANewExportWithTemplate(exportName, tempPDF);
		base.waitForElement(page.getTIFFTab());
		page.getTIFFTab().waitAndClick(3);
		page.toggleOnCheck(page.getEnableForNativelyToggle());
		driver.scrollingToBottomofAPage();
		base.waitForElement(page.gettextRedactionPlaceHolder());
		String actphTextPdf = page.gettextRedactionPlaceHolder().getText();
		soft.assertEquals(tagName, actphTextPdf);
		soft.assertAll();
		
		base.passedStep("Verify that for existing production/export/template with configured natively placeholdering,"
				+ " should be with enabled native placeholdering under TIFF/PDF section");
		loginPage.logout();
	}
	
	/**
	 * @author sowndarya TESTCASE No:RPMXCON-50659
	 * @Description: Verify that user can download the Export by using the Shareable link for 'DAT Only'
	 **/
	@Test(description = "RPMXCON-50659", enabled = true, groups = { "regression" })
	public void verifySharableLinkForDATOnlyFiles() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-50659");
		base.stepInfo("Verify that user can download the Export by using the Shareable link for 'DAT Only'");

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		String FolderName = "FolderName" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String productionname = "Prod" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);

		TagsAndFoldersPage tag = new TagsAndFoldersPage(driver);
		tag.CreateFolder(FolderName, Input.securityGroup);

		search.basicContentSearch(Input.testData1);
		search.bulkFolderExisting(FolderName);

		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSectionWithBrandingText();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(FolderName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		page.verifyDownloadProductionUsingSharableLink_DATFileonly();
		base.passedStep("Zip file saved into local machine");
		loginPage.logout();
	}
	
	/**
	 * @author sowndarya TESTCASE No:RPMXCON-50658
	 * @Description:Verify that user can download the Export by using the Shareable link for 'All Files'
	 **/
	@Test(description = "RPMXCON-50658", enabled = true, groups = { "regression" })
	public void verifySharableLinkFoAllFiles() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-50658");
		base.stepInfo("Verify that user can download the Export by using the Shareable link for 'All Files'");

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		String FolderName = "FolderName" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String productionname = "Prod" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);

		TagsAndFoldersPage tag = new TagsAndFoldersPage(driver);
		tag.CreateFolder(FolderName, Input.securityGroup);

		search.basicContentSearch(Input.testData1);
		search.bulkFolderExisting(FolderName);

		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSectionWithBrandingText();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(FolderName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		page.verifyDownloadProductionUsingSharableLink();
		base.passedStep("Zip file saved into local machine");
		loginPage.logout();
	}
	
	/**
	 * @author sowndarya TESTCASE No:RPMXCON-50374
	 * @Description:Verify that download option is available after post generation completed on Export-QC tab
	 **/
	@Test(description = "RPMXCON-50374", enabled = true, groups = { "regression" })
	public void verifyDownloadAndCopyPath() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-50374");
		base.stepInfo("Verify that download option is available after post generation completed on Export-QC tab");

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		String FolderName = "FolderName" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String productionname = "Prod" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);

		TagsAndFoldersPage tag = new TagsAndFoldersPage(driver);
		tag.CreateFolder(FolderName, Input.securityGroup);

		search.basicContentSearch(Input.testData1);
		search.bulkFolderExisting(FolderName);

		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSectionWithBrandingText();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(FolderName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		base.elementDisplayCheck(page.getQC_Download());
		base.elementDisplayCheck(page.getCopyPath());
		base.passedStep("On Production QC page,Download and CopyPath is displayed");
		loginPage.logout();
		
	}
	
	/**
	 * @author sowndarya TESTCASE No:RPMXCON-49136
	 * @Description:To verify that when text is exported for Exception file then it should export the text with the Placeholder (For Export)
	 **/
	@Test(description = "RPMXCON-49136", enabled = true, groups = { "regression" })
	public void verifyTechIssuePlaceholder() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-49136");
		base.stepInfo("To verify that when text is exported for Exception file then it should export the text with the Placeholder (For Export)");

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		String tagName = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String productionname = "Prod" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);

		TagsAndFoldersPage tag = new TagsAndFoldersPage(driver);
		tag.CreateTagwithClassification(tagName,Input.technicalIssue);

		search.basicContentSearch(Input.testData1);
		search.bulkTagExisting(tagName);

		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTiffSectionTechIssueWithEnteringText(tagName, Input.tagNameTechnical);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagName);
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
		File TiffFile = new File(
				home + "/Downloads/VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tiff");
		page.OCR_Verification_In_Generated_Tiff_tess4j(TiffFile, Input.tagNameTechnical);
		loginPage.logout();
	}
	
	/**
	 * @author sowndarya TESTCASE No:RPMXCON-50686
	 * @Description:Verify If TIFF is produced in the production which is selected as the basis for export then in export user can select 'Generate PDF',export should complete sucessfully
	 **/
	@Test(description = "RPMXCON-50686", enabled = true, groups = { "regression" })
	public void verifyGeneratePDF_MultiPage() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-50686");
		base.stepInfo("Verify If TIFF is produced in the production which is selected as the basis for export then in export user can select 'Generate PDF',export should complete sucessfully");

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		String tagName = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String productionname = "Prod" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);

		TagsAndFoldersPage tag = new TagsAndFoldersPage(driver);
		tag.CreateTagwithClassification(tagName,Input.tagNamePrev);

		search.basicContentSearch(Input.testData1);
		search.bulkTagExisting(tagName);

		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		System.out.println("created production "+ productionname);
		page.fillingDATSection();
		page.fillingPDFWithMultiPage(tagName);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.waitTime(2);
		
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		String name = page.getProduction().getText().trim();
		
		BatchPrintPage batch= new BatchPrintPage(driver);
		String extractFile = batch.extractFile(name+".zip");
		System.out.println(extractFile);

		driver.waitForPageToBeReady();

		page.verifyDownloadPDFFileCount(extractFile, false);
		loginPage.logout();
	}
	
	/**
	 * @author sowndarya TESTCASE No:RPMXCON-50688
	 * @Description:Verify If both TIFF and PDF are produced in the existing production (before upgrade) selected as the basis for export, in Export, you will only be able to export only TIFF.
	 **/
	@Test(description = "RPMXCON-50688", enabled = true, groups = { "regression" })
	public void verifyGeneratePDF_SinglePage() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-50688");
		base.stepInfo("Verify If both TIFF and PDF are produced in the existing production (before upgrade) selected as the basis for export, in Export, you will only be able to export only TIFF.");

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		String tagName = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String productionname = "Prod" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);

		TagsAndFoldersPage tag = new TagsAndFoldersPage(driver);
		tag.CreateTagwithClassification(tagName,Input.tagNamePrev);

		search.basicContentSearch(Input.testData1);
		search.bulkTagExisting(tagName);

		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		System.out.println("created production "+ productionname);
		page.fillingDATSection();
		base.stepInfo("selecting single page");
		page.fillingTIFFWithSinglePage(tagName);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.waitTime(2);
		
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		String name = page.getProduction().getText().trim();
		
		BatchPrintPage batch= new BatchPrintPage(driver);
		String extractFile = batch.extractFile(name+".zip");
		System.out.println(extractFile);
		base.passedStep("TIFF with single page is exported successfully");
		loginPage.logout();
	}
	
	/**
	 * @author sowndarya TESTCASE No:RPMXCON-50692
	 * @Description:Verify that Export should generate successfully by selecting only DAT and 'Generate TIFF' option with Priv Placholder
	 **/
	@Test(description = "RPMXCON-50692", enabled = true, groups = { "regression" })
	public void verifyPrivPlaceholder() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-50692");
		base.stepInfo("Verify that Export should generate successfully by selecting only DAT and 'Generate TIFF' option with Priv Placholder");

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		String tagName = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String productionname = "Prod" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);

		TagsAndFoldersPage tag = new TagsAndFoldersPage(driver);
		tag.CreateTagwithClassification(tagName,Input.tagNamePrev);

		int purehit = search.basicContentSearch(Input.testData1);
		search.bulkTagExisting(tagName);

		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		int FirstFile = Integer.valueOf(beginningBates);
		page.addANewProduction(productionname);
		System.out.println("created production "+ productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionPrivDocs(tagName, Input.tagNamePrev);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		String privCountInSummaryPage = page.getPrivDocCountInSummaryPage().getText();
		base.digitCompareEquals(purehit, Integer.parseInt(privCountInSummaryPage), "summary count is as expected", "summary count is not as expected");
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		page.extractFile();
		int LastFile = purehit + FirstFile;
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		driver.waitForPageToBeReady();
		File TiffFile = new File(
				home + "/Downloads/VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tiff");
		page.isfileisExists(TiffFile);

		page.OCR_Verification_In_Generated_Tiff_tess4j(FirstFile, LastFile, prefixID, suffixID, Input.tagNamePrev);
		loginPage.logout();
	}
	
	/**
	 * @author Brundha.T TESTCASE No:RPMXCON-50672
	 * @Description: Verify that after Reserving Bates Range completed it should
	 *               displays 'Reserving Bates Range Complete' status on Grid View
	 *               on Production-export Home page
	 **/
	@Test(description = "RPMXCON-50672", enabled = true, groups = { "regression" })
	public void verifyReservingBatesRangeCompleteStatusInGridView() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-50672");
		base.stepInfo(
				"Verify that after Reserving Bates Range completed it should displays 'Reserving Bates Range Complete' status on Grid View on Production-export Home page");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in as"+Input.pa1userName);
		
		String FolderName = "FolderName" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String exportName = "Export" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);

		TagsAndFoldersPage tag = new TagsAndFoldersPage(driver);
		tag.CreateFolder(FolderName, Input.securityGroup);

		search.basicContentSearch(Input.testData1);
		search.bulkFolderExisting(FolderName);

		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExport(exportName);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(FolderName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportName);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickOnGenerateButton();

		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		driver.waitForPageToBeReady();
		page.getGridView().waitAndClick(10);
		base.stepInfo("verifying Reserving Bates Range completed status in grid view");
		page.verifyProductionStatusInHomePageGridView("Reserving Bates Range Complete", exportName);

		loginPage.logout();

	}

	/**
	 * @author Brundha.T TESTCASE No:RPMXCON-50333
	 * @Description:Verify that Production-Export status displays in Draft with
	 *                     blank progress bar on Tile View
	 **/
	@Test(description = "RPMXCON-50333", enabled = true, groups = { "regression" })
	public void verifyDraftStatusInTileView() throws Exception {
		
		base.stepInfo("Test case Id: RPMXCON-50333");
		base.stepInfo("Verify that Production-Export  status displays in Draft with blank progress bar on Tile View");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo(Input.pa1userName + " logged in to sightline successfully");

		String exportName = "Export" + Utility.dynamicNameAppender();

		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExportAndSave(exportName);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.navigateToNextSection();

		page.navigateToProductionPage();
		page.selectDefaultExport();
		driver.waitForPageToBeReady();

		base.stepInfo("verifying Draft status in Tile view");
		base.waitTime(2);
		String ExpStatus = page.getProductionFromHomePage(exportName).getText();
		System.out.println(ExpStatus);
		if (ExpStatus.equals("")) {
			base.passedStep("blank progress bar is displayed as expected");
		} else {
			base.failedStep("Blank progress bar is not displayed");
		}
		base.ValidateElement_Presence(page.getDraftState(exportName), "DRAFT State");
		loginPage.logout();
	}

	/**
	 * @author Brundha.T TESTCASE No:RPMXCON-49256
	 * @Description:To verify that in Export, 'Click here to View and select the
	 *                 bates number(S)' link should not be shown
	 **/
	@Test(description = "RPMXCON-49256", enabled = true, groups = { "regression" })
	public void verifyInVisiblityOfClickHerelink() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-49256");
		base.stepInfo(
				"To verify that in Export, 'Click here to View and select the bates number(S)' link should not be shown");
		String exportName = "Export" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in as" + Input.pa1userName);

		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExport(exportName);
		page.fillingDATSection();
		page.navigateToNextSection();

		base.stepInfo("verifying Click here link is not shown");
		if (!page.getClickHereLink().isDisplayed()) {
			base.passedStep("Click here to View and select the bates number(S)' link is not displayed");
		} else {
			base.failedStep("Click here to View and select the bates number(S)' link is displayed");
		}

		loginPage.logout();
	}

	/**
	 * @author Brundha.T TESTCASE No:RPMXCON-50675
	 * @Description:Verify that once Generation is completed it should displays
	 *                     'Export File Complete' status on Grid View on
	 *                     Production-Export Home page
	 **/
	@Test(description = "RPMXCON-50675", enabled = true, groups = { "regression" })
	public void verifyExportFileCompleteStatusInGridView() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-50675");
		base.stepInfo(
				"Verify that once Generation is completed it should displays 'Export File Complete' status on Grid View on Production-Export Home page");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		String FolderName = "FolderName" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String exportName = "Export" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);

		TagsAndFoldersPage tag = new TagsAndFoldersPage(driver);
		tag.CreateFolder(FolderName, Input.securityGroup);

		search.basicContentSearch(Input.testData1);
		search.bulkFolderExisting(FolderName);

		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExport(exportName);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(FolderName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportName);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickOnGenerateButton();
		page.verifyProductionStatusInGenPage("Reserving Bates Range Complete");
		page.getbtnContinueGenerate().isElementAvailable(3);
		if (page.getbtnContinueGenerate().isDisplayed()) {
			page.getbtnContinueGenerate().waitAndClick(2);
		}
		driver.Navigate().refresh();
		page.navigateToProductionPage();
		base.waitTime(1);
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		base.waitForElement(page.getGridView());
		page.getGridView().waitAndClick(10);
		page.getGridView().waitAndClick(10);
		base.stepInfo("verifying Export File Complete status in Grid view");
		page.verifyProductionStatusInHomePageGridView("Exporting Files Complete", exportName);
		loginPage.logout();

	}
	
	/**
	 * @author Brundha.T TESTCASE No:RPMXCON-50680
	 * @Description:Verify that in Production-export components, TIFF/PDF section
	 *                     displays options for Generating TIFF or Generating PDF
	 **/
	@Test(description = "RPMXCON-50680", enabled = true, groups = { "regression" })
	public void verifyGenerateoptionInComponentTab() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-50680");
		base.stepInfo(
				"Verify that in Production-export components, TIFF/PDF section displays options for Generating TIFF or Generating PDF");
		String exportName = "Export" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in as" + Input.pa1userName);

		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExport(exportName);
		page.fillingDATSection();
		driver.scrollingToBottomofAPage();
		base.waitForElement(page.getTIFFTab());
		page.getTIFFTab().waitAndClick(5);
		driver.waitForPageToBeReady();
		base.stepInfo("verifying Generate TIFF and PDF option in Component tab");
		base.ValidateElement_Presence(base.text("Generate TIFF"), "Generating TIFF option");
		base.ValidateElement_Presence(base.text("Generate PDF"), "Generating PDF option");
		base.stepInfo("Verifying  Generate TIFF radio button in Component tab");
		page.getCheckBoxCheckedVerification(page.getGenrateTIFFRadioButton());

		if (page.getTiffContainer("Generate TIFF").isDisplayed()&&page.getTiffContainer("Generate PDF").isDisplayed()) {
			base.passedStep("Separate PDF section is not displayed in Component section.");
		} else {
			base.failedStep("Separate PDF section is displayed in Component section.");
		}
		loginPage.logout();
	}
	
	/**
	 * @author Brundha.T TESTCASE No:RPMXCON-47803
	 * @Description:To Verify Changes in Basic Info Page ( for Export)
	 **/
	@Test(description = "RPMXCON-47803", enabled = true, groups = { "regression" })
	public void verifyingBasicInfoTab() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-47803");
		base.stepInfo("To Verify Changes in Basic Info Page ( for Export)");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in as" + Input.pa1userName);

		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		
		base.waitForElement(page.getAddNewExport());
		page.getAddNewExport().Click();
		
		driver.waitForPageToBeReady();
		base.stepInfo("verifying Basic info label");
		if(page.getBasicInfoLabel().isDisplayed()) {
			base.passedStep("Basic info label is displayed");
		}else {
			base.failedStep("Basic info label is not displayed");
		}
		
		
		base.stepInfo("verifying the field like Name,Description and Template");
		base.ValidateElement_Presence(base.text("Name"),"Name Field");
		base.ValidateElement_Presence(page.getProductionName(),"Name Field");
		
		base.ValidateElement_Presence(base.text("Description"),"Description Field");
		base.ValidateElement_Presence(page.getProductionDesc(),"Description Field");
		
		base.ValidateElement_Presence(base.text("Template"),"Template Field");
		base.ValidateElement_Presence(page.getprod_LoadTemplate(),"Template Field");
		
		
		base.stepInfo("verifying toggle option and dropdown option");
		base.validatingGetTextElement(page.getToggleOptionText(),"Do you want to export prior production versions?");
		base.ValidateElement_Presence(page.getDropdownoptionText(),"Select Production Text");
		
		base.stepInfo("verifying the availability of Save,Markcomplete and Next Button");
		base.elementDisplayCheck(page.getSaveButton());
		base.elementDisplayCheck(page.getMarkCompleteLink());
		base.elementDisplayCheck(page.getNextButton());
		loginPage.logout();
	}
	
	
	/**
	 * @author Brundha.T TESTCASE No:RPMXCON-50689
	 * @Description:Verify the error message for DAT component when 'In export don’t select DocID and Bate Bumber data field'
	 **/
	@Test(description = "RPMXCON-50689", enabled = true, groups = { "regression" })
	public void verifyErrorMsgInDATSection() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-50689");
		base.stepInfo("Verify the error message for DAT component when 'In export don’t select DocID and Bate Bumber data field'");
		String exportName = "Export" + Utility.dynamicNameAppender();
		String BatesNum = "Bates" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in as" + Input.pa1userName);

		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExport(exportName);
		page.fillingDATSectionWithBates(Input.bates,"ChildBates",BatesNum);
		base.waitForElement(page.getMarkCompleteLink());
		page.getMarkCompleteLink().Click();
		base.stepInfo("verifying error message in DAT section");
		base.VerifyErrorMessage("Either Bates Number or DocID must be selected in the DAT for an export.");
		
		loginPage.logout();
	}
	
	/**
	 * @author Brundha TESTCASE No:RPMXCON-50687
	 * @Description:Verify If PDF is produced in the production which is selected as
	 *                     the basis for export then in export user can select
	 *                     'Generate TIFF',export should complete sucessfully
	 */
	@Test(description = "RPMXCON-50687", enabled = true, groups = { "regression" })

	public void verifyingExportWithTIFFAndText() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in as " + Input.pa1userName);

		base.stepInfo("RPMXCON-50687");
		base.stepInfo(
				"Verify If PDF is produced in the production which is selected as the basis for export then in export user can select 'Generate TIFF',export should complete sucessfully");
		String tagname = "Tag" + Utility.dynamicNameAppender();

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String exportName = "E" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tag = new TagsAndFoldersPage(driver);
		tag.createNewTagwithClassification(tagname, Input.tagNamePrev);
		tag.CreateFolder(foldername, Input.securityGroup);

		SessionSearch search = new SessionSearch(driver);
		search.basicContentSearch(Input.testData1);
		search.bulkFolderExisting(foldername);
		search.bulkTagExisting(tagname);

		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectPrivDocsInPDFSection(tagname);
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

		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExportwithProduction(exportName, productionname);
		base.waitTillElemetToBeClickable(page.getTIFFChkBox());
		base.waitForElement(page.getTIFFTab());
		page.getTIFFTab().Click();
		driver.scrollPageToTop();
		base.waitForElement(page.getRadioButton_GenerateTIFF());
		page.getGenrateTIFFRadioButton().waitAndClick(10);
		page.navigateToNextSection();
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		base.waitForElement(page.getOkButton());
		page.getOkButton().waitAndClick(3);
		page.fillingExportLocationPage(exportName);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingExportGeneratePageWithContinueGenerationPopup();

		String actualCopedText = page.getCopiedTextFromClipBoard();
		base.stepInfo("Generated export path" + actualCopedText);
		String parentTab = page.openNewTab(actualCopedText);

		page.goToImageFiles();
			if (base.text(prefixID+beginningBates+suffixID).isElementAvailable(5)) {
				base.passedStep("Tiff files are displayed in export ");

			} else {
				base.failedStep("Tiff file is not displayed");

			}
		page.navigatingBack(2);
		base.elementDisplayCheck(page.getFileDir("Text/"));
		page.getFileDir("Text/").waitAndClick(10);
		driver.waitForPageToBeReady();
		page.getFileDir("0001/").waitAndClick(10);
		base.elementDisplayCheck(base.text(prefixID+beginningBates+suffixID));
		driver.close();
		driver.getWebDriver().switchTo().window(parentTab);
		loginPage.logout();

	}
	/**
	 * @author Brundha.T TESTCASE No:RPMXCON-50654
	 * @Description: Verify that on Clicking on 'Copy Path' , it will copy the path
	 *               to review the documents
	 **/
	@Test(description = "RPMXCON-50654", enabled = true, groups = { "regression" })
	public void verifyExportedFileIsDisplayed() throws Exception {
		base.stepInfo("Test case Id: RPMXCON-50654");
		base.stepInfo("Verify that on Clicking on 'Copy Path' , it will copy the path to review the documents");

		String tagName = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String exportName = "Export" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in as" + Input.pa1userName);

		tag.CreateTagwithClassification(tagName, Input.tagNamePrev);
		search.basicContentSearch(Input.testData1);
		search.bulkTagExisting(tagName);

		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExport(exportName);
		page.fillingDATSection();
		page.fillingTIFFSection(tagName);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportName);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingExportGeneratePageWithContinueGenerationPopup();

		base.stepInfo("Copied path is paste in new tab");
		String actualCopedText = page.getCopiedTextFromClipBoard();
		String parentTab = page.openNewTab(actualCopedText);
		base.stepInfo("Generated export path" + actualCopedText);

		base.stepInfo("verifying user can able to view the exported file");
		base.waitForElement(page.getFileDir("VOL0001"));
		if (page.getFileDir("VOL0001").isDisplayed()) {
			page.getFileDir("VOL0001").waitAndClick(5);
			page.getFileDir("Images/").waitAndClick(10);
			page.getFileDir("0001/").waitAndClick(5);
			base.waitTime(2);
			base.elementDisplayCheck(page.getPath(prefixID));
			base.elementDisplayCheck(page.getPath(suffixID));
			base.passedStep("user can able to see the exported file");
		} else {
			base.failedStep("User can't able to view the exported file");
		}
		driver.close();
		driver.getWebDriver().switchTo().window(parentTab);
		loginPage.logout();

	}

	/**
	 * @author Brundha.T TESTCASE No:RPMXCON-50331
	 * @Description:Verify that use cannot access the Production Export deatils by
	 *                     copying the Production URL if user is not part of
	 *                     Project/SG
	 **/
	@Test(description = "RPMXCON-50331", enabled = true, groups = { "regression" })
	public void verifyingErrorMsgInExport() throws Exception {
		base.stepInfo("Test case Id: RPMXCON-50331");
		base.stepInfo(
				"Verify that use cannot access the Production Export deatils by copying the Production URL if user is not part of Project/SG");

		String exportName = "Export" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Logged in as " + Input.sa1userName);

		base.stepInfo("Impersonating SA as PA");
		base.impersonateSAtoPA();

		base.stepInfo("Copying export set URL");
		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExport(exportName);
		page.fillingDATSection();

		String currentURL = driver.getWebDriver().getCurrentUrl();
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in as " + Input.pa1userName);

		base.stepInfo("Selecting the same project");
		base.selectproject(Input.projectName);

		base.stepInfo("navigating to production set");
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();

		base.stepInfo("paste the encrypted URL and verifying error message");
		driver.Navigate().to(currentURL);
		driver.waitForPageToBeReady();
		String ErrorMsg = page.getErrorMsgText().getText();
		if (ErrorMsg.contains("Error")) {
			base.passedStep("Error message is displayed as expected");
		} else {
			base.failedStep("Error message is not  displayed as expected");
		}

		loginPage.logout();

	}
	
	/**
	 * @author Brundha.T TESTCASE No:RPMXCON-50667
	 * @Description: Verify that after Pre-gen checks is in progress, it will
	 *               displays status on Production Grid view
	 **/
	@Test(description = "RPMXCON-50667", enabled = true, groups = { "regression" })
	public void verifyPreGenCheckInProgressStatusInGridView() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-50667");
		base.stepInfo(
				"Verify that after Pre-gen checks is in progress, it will displays status on Production Grid view");

		String[] Username = { Input.pa1userName, Input.rmu1userName };
		String[] Password = { Input.pa1password, Input.rmu1password };

		for (int i = 0; i < Username.length; i++) {

			loginPage.loginToSightLine(Username[i], Password[i]);
			base.stepInfo("Logged in as" + Username[i]);

			String FolderName = "FolderName" + Utility.dynamicNameAppender();
			String prefixID = Input.randomText + Utility.dynamicNameAppender();
			String suffixID = Input.randomText + Utility.dynamicNameAppender();
			String exportName = "Export" + Utility.dynamicNameAppender();
			String subBates = page.getRandomNumber(2);
			TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
			tagsAndFolderPage.CreateFolder(FolderName, Input.securityGroup);

			int purehit = search.basicContentSearch(Input.telecaSearchString);
			search.bulkFolderExisting(FolderName);

			base = new BaseClass(driver);
			page.navigateToProductionPage();
			page.selectingDefaultSecurityGroup();
			page.selectDefaultExport();
			page.addANewExport(exportName);
			page.fillingDATSection();
			page.fillingNativeSection();
			page.navigateToNextSection();
			page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
			page.navigateToNextSection();
			page.fillingDocumentSelectionPage(FolderName);
			page.navigateToNextSection();
			page.fillingPrivGuardPage();
			page.fillingExportLocationPage(exportName);
			page.navigateToNextSection();
			page.fillingSummaryAndPreview();
			page.clickOnGenerateButton();

			page.navigateToProductionPage();
			page.selectingDefaultSecurityGroup();
			page.selectDefaultExport();
			driver.waitForPageToBeReady();
			page.getGridView().waitAndClick(10);
			base.stepInfo("verifying Pre-gen checks status in grid view");
			page.verifyProductionStatusInHomePageGridView("Pre-Gen Checks -", exportName);

			loginPage.logout();
		}

	}

	/**
	 * @author Brundha.T TESTCASE No:RPMXCON-50676
	 * @Description:Verify that once LST generation is started it should displays '
	 *                     Generating Load Files' status on Production-Export Grid
	 *                     View
	 **/
	@Test(description = "RPMXCON-50676", enabled = true, groups = { "regression" })
	public void verifyGeneratingloadFileStatusInGridView() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-50676");
		base.stepInfo(
				"Verify that once LST generation is started it should displays ' Generating Load Files' status on Production-Export Grid View");

		String FolderName ="Folder" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String exportName = "Export" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in as" + Input.pa1userName);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(FolderName, Input.securityGroup);

		search.basicContentSearch(Input.testData1);
		search.bulkFolderExisting(FolderName);

		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExport(exportName);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(FolderName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportName);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWaitForContinueGeneration();

		driver.Navigate().refresh();
		base.waitTime(1);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		driver.waitForPageToBeReady();
		page.getGridView().waitAndClick(10);
		base.stepInfo("verifying Generating Load Files status in grid view");
		page.verifyProductionStatusInHomePageGridView("Generating Load Files", exportName);

		loginPage.logout();

	}

	/**
	 * @author Brundha.T TESTCASE No:RPMXCON-50670
	 * @Description:Verify that after Pregen check completed it should displays
	 *                     'Reserving Bates Range' status on Grid View on
	 *                     Production-Export Home page
	 **/
	@Test(description = "RPMXCON-50670", enabled = true, groups = { "regression" })
	public void verifyReservingBatesRangeStatusInGridView() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-50670");
		base.stepInfo(
				"Verify that after Pregen check completed it should displays 'Reserving Bates Range' status on Grid View on Production-Export Home page");

		String FolderName = "FolderName" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String exportName = "Export" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in as" + Input.pa1userName);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(FolderName, Input.securityGroup);

		search.basicContentSearch(Input.testData1);
		search.bulkFolderExisting(FolderName);

		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExport(exportName);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(FolderName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportName);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickOnGenerateButton();
		base.waitTime(2);

		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		driver.waitForPageToBeReady();
		page.getGridView().waitAndClick(10);
		base.stepInfo("verifying Reserving Bates Range status in grid view");
		page.verifyProductionStatusInHomePageGridView("Reserving Bates Range", exportName);

		loginPage.logout();

	}
	/**
	 * @author Brundha.T No:RPMXCON-49123
	 * @Description:To verify that in Production-Export-Privileged Placeholder
	 *                 section, Metadata Field drop down should be sorted by alpha
	 *                 ascending
	 **/

	@Test(description = "RPMXCON-49123", enabled = true, groups = { "regression" })
	public void verifyingAscendingOrderInTechDocMetaDataField() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		BaseClass base = new BaseClass(driver);
		base.stepInfo("Test case Id:RPMXCON-49123-Export Component");
		base.stepInfo(
				"To verify that in Production-Export-Privileged Placeholder section, Metadata Field drop down should be sorted by alpha ascending");

		List<String> TIFFFPrivMetaData = new ArrayList<>();
		List<String> PDFPrivMetaData = new ArrayList<>();
		ProductionPage page = new ProductionPage(driver);

		String exportName = "Export" + Utility.dynamicNameAppender();

		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExport(exportName);
		base.waitForElement(page.getTIFFChkBox());
		page.getTIFFChkBox().Click();
		driver.scrollingToBottomofAPage();
		base.waitForElement(page.getTIFFTab());
		page.getTIFFTab().Click();
		base.waitForElement(page.getPrivInsertMetadataField());
		page.getPrivInsertMetadataField().waitAndClick(5);
		List<String> PrivilegedMetaDataField = base.availableListofElements(page.InsertMetaDataFieldValues());
		PrivilegedMetaDataField.replaceAll(String::toUpperCase);
		System.out.println("Before Sorting order" + PrivilegedMetaDataField);
		for (String Tiff : PrivilegedMetaDataField) {
			TIFFFPrivMetaData.add(Tiff);
		}
		Collections.sort(TIFFFPrivMetaData);
		System.out.println("After Sorting order" + TIFFFPrivMetaData);
		if (PrivilegedMetaDataField.equals(TIFFFPrivMetaData)) {
			base.passedStep("Sorting order is maintained  in TIFF");
		} else {
			base.failedStep("Sorting order is not maintained in TIFF");
		}

		page.navigatingToProductionHomePage();
		String ExportName1 = "Export" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectDefaultExport();
		page.addANewExport(ExportName1);
		base.waitForElement(page.getTIFFChkBox());
		page.getTIFFChkBox().Click();
		driver.scrollingToBottomofAPage();
		page.getTIFFTab().waitAndClick(10);
		driver.scrollPageToTop();
		page.getPDFGenerateRadioButton().Enabled();
		page.getPDFGenerateRadioButton().waitAndClick(10);
		driver.scrollingToElementofAPage(page.getPrivInsertMetadataField());
		base.waitForElement(page.getPrivInsertMetadataField());
		page.getPrivInsertMetadataField().waitAndClick(5);
		List<String> PDFPrivilegedMetaDataField = base.availableListofElements(page.InsertMetaDataFieldValues());
		PDFPrivilegedMetaDataField.replaceAll(String::toUpperCase);
		System.out.println("Before Sorting" + PDFPrivilegedMetaDataField);
		for (String PDFPriv : PDFPrivilegedMetaDataField) {
			PDFPrivMetaData.add(PDFPriv);
		}
		Collections.sort(PDFPrivMetaData);
		System.out.println("After Sorting" + PDFPrivMetaData);
		if (PDFPrivilegedMetaDataField.equals(PDFPrivMetaData)) {
			base.passedStep("Sorting order is maintained in PDF");
		} else {
			base.failedStep("Sorting order is not maintained In PDF");
		}
		loginPage.logout();
	}
	
	/**
	 * @author Brundha.T TESTCASE No:RPMXCON-50362
	 * @Description:Verify that after Post Generation is completed, it will displays status on Export generation page as 'Completed'
	 **/
	@Test(description = "RPMXCON-50362", enabled = true, groups = { "regression" })
	public void verifyCompletedStatusInGenPage2() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-50362");
		base.stepInfo("Verify that after Post Generation is completed, it will displays status on Export generation page as 'Completed'");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		String FolderName = "FolderName" + Utility.dynamicNameAppender();
		String TagName = "FolderName" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String exportName = "Export" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(FolderName, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(TagName,Input.tagNamePrev);

		search.basicContentSearch(Input.testData1);
		search.bulkFolderExisting(FolderName);
		search.bulkTagExisting(TagName);

		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExport(exportName);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSection(TagName);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(FolderName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportName);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommitandDownload();
		driver.waitForPageToBeReady();
		page.getBackButton().waitAndClick(5);
		base.stepInfo("verifying Completed status in Generate Page");
		page.verifyProductionStatusInGenPage("Completed");
		loginPage.logout();

	}
	/**
	 * @author Brundha.T Testcase No:RPMXCON-50372
	 * @throws Exception
	 * @Description:Verify that in the Export components page 'Archive File from
	 *                     FTP' component is not available
	 **/
	@Test(description = "RPMXCON-50372", enabled = true, groups = { "regression" })
	public void verifyingInVisiblityOfArchiveFile() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-50372");
		base.stepInfo("Verify that in the Export components page 'Archive File from FTP' component is not available");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		String ExportName = "p" + Utility.dynamicNameAppender();
		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExport(ExportName);
		base.waitTime(1);
		base.stepInfo("verifying archieve file from FTP is not available in Component tab");
		base.waitForElement(page.getAdvancedProductionComponents());
		page.getAdvancedProductionComponents().Click();
		base.elementNotdisplayed(page.getArchieveFile(), "Archieve files from FTP");
		loginPage.logout();
	}

	/**
	 * @author Brundha.T Testcase No:RPMXCON-47804
	 * @throws Exception
	 * @Description:To Verify Basic Info UI with Toggle option (For Export).
	 **/
	@Test(description = "RPMXCON-47804", enabled = true, groups = { "regression" })
	public void verifyingBasicInfoTabInExport() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47804");
		base.stepInfo("To Verify Basic Info UI with Toggle option (For Export).");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		String ExportName = "E" + Utility.dynamicNameAppender();
		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		base.waitForElement(page.getAddNewExport());
		page.getAddNewExport().Click();

		base.waitForElement(page.getProductionName());
		page.getProductionName().SendKeys(ExportName);
		base.stepInfo("verifying export production toggle is off and select production dropdown is disabled");
		page.toggleOffCheck(page.getExportProdToogle());
		base.elementDisplayCheck(page.getSelectProductionDisabled());
		
		base.stepInfo("export toggle is on and verifying the select dropdown is enabled");
		page.getExportProdToogle().waitAndClick(5);
		base.elementNotdisplayed(page.getSelectProductionDisabled(),"Select Production dropdown");
		page.getExportProdToogle().waitAndClick(5);
		driver.scrollPageToTop();
		page.getMarkCompleteLink().Click();
		base.waitTime(1);
		if(page.getDATChkBox().isDisplayed()) {
			base.passedStep("Navigated to component tab as expected");
		}else {
			base.failedStep("component tab is not displayed");
		}
		
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
		System.out.println("******TEST CASES FOR Export cases EXECUTED******");

	}
}
