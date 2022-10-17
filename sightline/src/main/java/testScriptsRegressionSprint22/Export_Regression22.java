package testScriptsRegressionSprint22;

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
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Export_Regression22 {

	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	DocViewPage docView;
	ProductionPage page;
	SessionSearch sessionSearch;
	SavedSearch saveSearch;
	TagsAndFoldersPage tagsAndFolderPage;
	Utility utility;
	String foldername;
	String tagname;
	String productionname;
	SoftAssert softAssertion;

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
		softAssertion = new SoftAssert();
		page = new ProductionPage(driver);
		saveSearch = new SavedSearch(driver);
		sessionSearch = new SessionSearch(driver);
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
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
	    
	    tagsAndFolderPage.CreateTagwithClassification(tagName, Input.tagNamePrev);

		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocList();		
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
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		driver.waitForPageToBeReady();
		tagsAndFolderPage.DeleteTagWithClassification(tagName, Input.securityGroup);
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
	    String exportName = "Export" + Utility.dynamicNameAppender();
	    String subBates = page.getRandomNumber(2);
	    productionname = "p" + Utility.dynamicNameAppender();
	    String beginningBates = page.getRandomNumber(2);
	    String prodBatesRange = "B1-10";
	    
	    tagsAndFolderPage.CreateTagwithClassification(tagName, Input.tagNamePrev);
		int purehit = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagName);	

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
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
        page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		page.addANewExportwithProduction(exportName, productionname);
		page.afterCompletingNavigatingToNextPage();	
		page.afterCompletingNavigatingToNextPage();
		page.fillingDocumentSelectionWithTag(tagName);
		page.afterCompletingNavigatingToNextPage();
		base.waitForElement(page.getOkButton());
		page.getOkButton().waitAndClick(3);
		page.fillingExportLocationPage(exportName);
		page.afterCompletingNavigatingToNextPage();
		page.afterCompletingNavigatingToNextPage();
		page.fillingExportGeneratePageWithContinueGenerationPopup();
		String batesRange = prefixID + beginningBates + suffixID ;
		String actualCopedText = page.getCopiedTextFromClipBoard();
		String parentTab = page.openNewTab(actualCopedText);	
		page.goToImageFiles();
		page.verifyTiffFile(purehit, prefixID, suffixID, subBates, batesRange);
		
		// delete tags and folders
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.DeleteTagWithClassification(tagName, Input.securityGroup);
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

		tagname = "Tag" + Utility.dynamicNameAppender();
	    String tempTIFF = "Temp" + Utility.dynamicNameAppender();
	    String tempPDF = "Temp" + Utility.dynamicNameAppender();
	    
	    tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
	    sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);	
		
		base = new BaseClass(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
        page.fillingTIFFSectionwithNativelyPlaceholder(tagname);
	    page.navigateToNextSection();
		page.navigateToProductionPage();
		page.saveProductionAsTemplateAndVerifyProductionComponentsInManageTemplateTab(productionname, tempTIFF);
		
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
        page.fillingPDFSectionwithNativelyPlaceholder(tagname);
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
		driver.scrollingToBottomofAPage();
		page.toggleOnCheck(page.getEnableForNativelyToggle());
		base.waitForElement(page.getNativeDocsPlaceholderText());
		String actphTextTiff = page.getNativeDocsPlaceholderText().getText();
		softAssertion.assertEquals(tagname, actphTextTiff);
		
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.selectDefaultExport();
		exportName = "Export" + Utility.dynamicNameAppender();
		page.addANewExportWithTemplate(exportName, tempPDF);
		base.waitForElement(page.getTIFFTab());
		page.getTIFFTab().waitAndClick(3);
		driver.scrollingToBottomofAPage();
		page.toggleOnCheck(page.getEnableForNativelyToggle());
		base.waitForElement(page.gettextRedactionPlaceHolder());
		String actphTextPdf = page.gettextRedactionPlaceHolder().getText();
		softAssertion.assertEquals(tagname, actphTextPdf);
		softAssertion.assertAll();
		
		base.passedStep("Verify that for existing production/export/template with configured natively placeholdering,"
				+ " should be with enabled native placeholdering under TIFF/PDF section");
		loginPage.logout();
	}
}
