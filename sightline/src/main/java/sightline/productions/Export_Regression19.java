package sightline.productions;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

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
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Export_Regression19 {
	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	DocViewPage docView;
	Utility utility;

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
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String newExport = "Export" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
	
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		SessionSearch sessionSearch = new SessionSearch(driver);
		int purehit = sessionSearch.basicContentSearch(Input.telecaSearchString);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.bulkRelease(Input.securityGroup);
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
		page.selectPrivDocsInTiffSection(tagname);
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
