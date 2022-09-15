package testScriptsRegressionSprint21;
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

public class Export_Regression21 {
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

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String prefixID1 = Input.randomText + Utility.dynamicNameAppender();
		String suffixID1 = Input.randomText + Utility.dynamicNameAppender();
		String exportName = Input.randomText + Utility.dynamicNameAppender();
		String exportName1 = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.addNewSearch();
		sessionSearch.SearchMetaData(Input.docFileType, Input.MetaDataFileType);
		sessionSearch.addPureHit();
		sessionSearch.ViewInDocList();

		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(10);
		doclist.documentSelection(3);
		doclist.bulkTagExisting(tagname);

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
		page.getNativeDocsPlaceholder().SendKeys(Input.searchString4);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportName);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommitandDownload();
		String actualCopedText = page.getCopiedTextFromClipBoard();
		System.out.println(actualCopedText);
		base.stepInfo("Export Path"+actualCopedText);
		String parentTab = page.openNewTab(actualCopedText);
		
		page.goToImageFiles();
		for (int i = 2; i < 6; i++) {
			if (page.getFirstImageFile(prefixID + "(" + i + ")" + suffixID, subBates).isElementAvailable(2)) {
				base.passedStep("Tiff files are generated successfully");
			} else {
				base.failedStep("Tiff files are not generated");
			}
		}
		driver.waitForPageToBeReady();
		page.navigatingBack(2);
		page.getFileDir("Natives/").waitAndClick(10);
		page.getFileDir("0001/").waitAndClick(10);
		driver.waitForPageToBeReady();
		page.verifyingGeneratedExporttedfile(2, prefixID, suffixID, subBates,".doc");
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
		page.getNativeDocsPlaceholder().SendKeys(Input.searchString4);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID1, suffixID1, subBates1);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportName1);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommitandDownload();
		String actualCopedTexts = page.getCopiedTextFromClipBoard();
		System.out.println(actualCopedTexts);
		base.stepInfo("Export Path"+actualCopedTexts);
		String parentTabs = page.openNewTab(actualCopedTexts);
		page.goToPDFImageFiles();
		for (int i = 2; i < 6; i++) {
			if (page.getFirstPDFImageFile(prefixID1 + "(" + i + ")" + suffixID1, subBates1).isElementAvailable(2)) {
				base.passedStep("Pdf files are generated successfully");
			} else {
				base.failedStep("Pdf files are not generated");
			}
		}
		page.navigatingBack(2);
		page.getFileDir("Natives/").waitAndClick(10);
		page.getFileDir("0001/").waitAndClick(10);
		driver.waitForPageToBeReady();
		page.verifyingGeneratedExporttedfile(2, prefixID1, suffixID1, subBates1,".doc");
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

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String exportName = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.SearchMetaData(Input.ingDocFileType, Input.MetaDataFileType);
		sessionSearch.addPureHit();
		sessionSearch.ViewInDocList();

		DocListPage doclist = new DocListPage(driver);
		doclist = new DocListPage(driver);
		doclist.documentSelection(3);
		doclist.bulkTagExisting(tagname);

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
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportName);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommitandDownload();
		String actualCopedText = page.getCopiedTextFromClipBoard();
		String parentTab = page.openNewTab(actualCopedText);
		System.out.println(actualCopedText);
		base.stepInfo("Export Path"+actualCopedText);
		page.getFileDir("VOL0001").waitAndClick(10);
		page.getFileDir("Natives/").waitAndClick(10);
		page.getFileDir("0001/").waitAndClick(10);
		driver.waitForPageToBeReady();
		page.verifyingGeneratedExporttedfile(2, prefixID, suffixID, subBates,".xlsx");
		driver.close();
		driver.getWebDriver().switchTo().window(parentTab);
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
