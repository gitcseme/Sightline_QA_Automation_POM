package testScriptsRegressionPhase2;

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
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Export_Regression1 {

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
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
	}

	/**
	 * @author Brundha RPMXCON-47500
	 * @Description Export: Verify that when production components step is completed
	 *              then DAT component should retain the 'TIFFPageCount'
	 * 
	 */
	@Test(description = "RPMXCON-47500", enabled = true, groups = { "regression" }, priority = 1)
	public void verifyDatSectionMappingField() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47500 -Export component");
		base.stepInfo(
				"Export: Verify that when production components step is completed then DAT component should retain the 'TIFFPageCount'");

		String newExport = "Ex" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();

		page.selectingDefaultSecurityGroup();
		String text = page.getProdExport_ProductionSets().getText();
		if (text.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(newExport);
		}
		page.addANewExport(productionname);
		page.verifyingDatFieldClassification();
		loginPage.logout();

	}

	/**
	 * @author Brundha RPMXCON-47983
	 * @Description To Verify Export Status after it complete the Export Generation
	 *              Task
	 * 
	 */
	@Test(description = "RPMXCON-47983", enabled = true, groups = { "regression" }, priority = 2)
	public void verifyingCompletedStatusInTileView() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47983 -Export component");
		base.stepInfo("To Verify Export Status after it complete the Export Generation Task");

		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String newExport = "Ex" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
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
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();

		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		page.selectExportSetFromDropDown();
		page.getProductionFromHomePage(productionname).isElementAvailable(10);
		String productionFromHomePage = page.getProductionFromHomePage(productionname).getText();
		if (productionFromHomePage.equals("Completed")) {
			base.passedStep("Completed status is displayed as expected");
		} else {
			base.failedStep("Status is not displayed as expected");
		}
		loginPage.logout();
	}

	/**
	 * @author Brundha RPMXCON-47471
	 * @Description To Verify PDF creation for export
	 * 
	 */
	@Test(description = "RPMXCON-47471", enabled = true, groups = { "regression" }, priority = 3)
	public void verifyGenerationOfExportWithPdfSection() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47471 -Export component");
		base.stepInfo("To Verify PDF creation for export");

		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String newExport = "Ex" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
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
		page.fillingPDFSection(tagname);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:21/06/2022 RPMXCON-48193
	 * @Description To Verify Export Generation for MP3 files using document level
	 *              numbering.
	 *
	 */
	@Test(description = "RPMXCON-48193", enabled = true, groups = { "regression" }, priority = 4)
	public void verifyGenerationOfExportForMp3File() throws Exception {
		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48193 -Export component");
		base.stepInfo("To Verify Export Generation for MP3 files using document level numbering.");
		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String newExport = "Ex" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
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
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani Modify Date:21/06/2022 RPMXCON-47499
	 * @Description Export: Verify that TIFFPageCount is no longer presented under
	 *              "Doc Metadata"
	 *
	 */
	@Test(description = "RPMXCON-47499", enabled = true, groups = { "regression" }, priority = 5)
	public void VerifydatSectionFieldMapping() throws Exception {
		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47499 -Export component");
		base.stepInfo("Export: Verify that TIFFPageCount is no longer presented under 'Doc Metadata'");
		String newExport = "Ex" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		String text = page.getProdExport_ProductionSets().getText();
		if (text.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(newExport);
		}
		page.addANewExport(productionname);
		page.verifyingDatMappingField();
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
