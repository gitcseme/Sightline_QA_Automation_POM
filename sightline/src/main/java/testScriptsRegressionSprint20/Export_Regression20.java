package testScriptsRegressionSprint20;

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
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Export_Regression20 {
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
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
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

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "P" + Utility.dynamicNameAppender();
		String prefixID1 = "P" + Utility.dynamicNameAppender();
		String suffixID = "S" + Utility.dynamicNameAppender();
		String suffixID1 = "S" + Utility.dynamicNameAppender();
		String exportName = "Ex" + Utility.dynamicNameAppender();
		String exportName1 = "Ex" + Utility.dynamicNameAppender();

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
		int doc = 6;
		doclist.documentSelection(doc);
		doclist.bulkTagExistingFromDoclist(tagname);
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
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportName);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		page.getCopyPath().waitAndClick(2);
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
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportName1);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();

		page.getCopyPath().waitAndClick(2);
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
