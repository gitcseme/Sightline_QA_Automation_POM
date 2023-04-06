package sightline.productions;

import java.awt.AWTException;
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

public class Production_Phase1_Regression2 {

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

	@BeforeMethod(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		Input input = new Input();
		input.loadEnvConfig();
		
		driver = new Driver();
		loginPage = new LoginPage(driver);
		base = new BaseClass(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56052
	 * @Description: To Verify that after Archiving is completed it should displays
	 *               'Creating Archive Complete' status on Production Grid View
	 */
	@Test(description = "RPMXCON-56052", enabled = true, groups = { "regression" })
	public void archivingStatusVerifyOnGridView() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56052 -Production Sprint 06");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		prefixID = Input.randomText + Utility.dynamicNameAppender();
		suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// Verify archive status on Grid view
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
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

		// Wait until Generate button enables
		page.fillingGeneratePageWithContinueGenerationPopupWithoutWait();

		// Go To Production Home Page
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		page.getGridView().waitAndClick(10);
		// Thread.sleep(5000);
		driver.waitForPageToBeReady();
		page.verifyProductionStatusInHomePageGridView("Creating Archive", productionname);
		base.passedStep(
				"Archiving is completed it should displays 'Creating Archive Complete' status on Production Grid View");

		// delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-55975
	 * @Description: To Verify that after Pregen check completed it should displays
	 *               'Reserving Bates Range' status on Progress bar in Tile View on
	 *               Production Home page
	 */
	@Test(description = "RPMXCON-55975", enabled = true, groups = { "regression" })
	public void reservingBateRangeStatusVerifyOnTileView() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-55975 -Production Sprint 06");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		prefixID = Input.randomText + Utility.dynamicNameAppender();
		suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// Verify Reserving Bates Range status on Tile view
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
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
		// click genarate without wait
		page.clickGenarateWithoutWait();

		// Go To Production Home Page
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		// verification
		page.verifyProductionStatusInHomePage("Reserving Bates Range", productionname);
		base.passedStep(
				"Verified that after Pregen check completed it should displays 'Reserving Bates Range' status on Progress bar in Tile View on Production Home page");

		// delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-55973
	 * @Description: To Verify that after Pre-gen checks is in progress, it will
	 *               displays status on Production Progress bar Tile View
	 */
	@Test(description = "RPMXCON-55973", enabled = true, groups = { "regression" })
	public void preGenChecksStatusVerifyOnTileView() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-55973 -Production Sprint 06");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		prefixID = Input.randomText + Utility.dynamicNameAppender();
		suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// Verify Pre-gen checks is in progress status on Tile view
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
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
		// click genarate without wait
		page.clickGenarateWithoutWait();

		// Go To Production Home Page
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		// verification
		page.verifyProductionStatusInHomePage("Pre-Gen Checks - 6/6 Docs", productionname);
		base.passedStep(
				"Verified that after Pre-gen checks is in progress as a PA, it will displays status on Production Progress bar Tile View");

		loginPage.logout();

		// login as a RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		// Verify Pre-gen checks is in progress status on Tile view
		page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		beginningBates = page.getRandomNumber(2);
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
		// click genarate without wait
		page.clickGenarateWithoutWait();

		// Go To Production Home Page
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		// verification
		page.verifyProductionStatusInHomePage("Pre-Gen Checks - 6/6 Docs", productionname);
		base.passedStep(
				"Verified that after Pre-gen checks is in progress as a RMU, it will displays status on Production Progress bar Tile View");

		// delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();

	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56136
	 * @Description: To Verify that for the saved template under Translations
	 *               component- File Type Options should be disabled.
	 */
	@Test(description = "RPMXCON-56136", enabled = true, groups = { "regression" })
	public void verifyTranslationComponentDisableAtMangeTemp() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON_56136 Production- Sprint 06");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		prefixID = Input.randomText + Utility.dynamicNameAppender();
		suffixID = Input.randomText + Utility.dynamicNameAppender();
		// Pre-requisites

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		TempName = "T" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		driver.waitForPageToBeReady();
		page.advancedProductionComponentsTranslations();
		page.getMarkCompleteLink().waitAndClick(10);
		page.getProductionHomePage().waitAndClick(10);
		page.getprod_ActionButton_Reusable(productionname).Click();
		driver.waitForPageToBeReady();
		page.getprod_Action_SaveTemplate_Reusable(productionname).Click();

		page.saveTemple(TempName);
		page.getManageTemplates().waitAndClick(10);
		driver.scrollingToBottomofAPage();
		page.getNextBtn().waitAndClick(10);
		driver.scrollingToBottomofAPage();
		page.getViewTemplate(TempName).waitAndClick(10);

		page.viewTempProductionNextAdvTranslation();
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		page.translationDisableCheck();
		base.passedStep(
				"Verified that for the saved template under Translations component- File Type Options should be disabled");
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56091
	 * @Description: To Verify that PDF should generate with Burned Redaction if
	 *               Only Burn Redaction is enabled.
	 */
	@Test(description = "RPMXCON-56091", enabled = true, groups = { "regression" })
	public void getPdfWithBurnRedactionTag() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56091 -Production Sprint 06");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		prefixID = Input.randomText + Utility.dynamicNameAppender();
		suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// Verify pdf with burn redaction
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFForRedaction1();
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
		base.passedStep("Verified that PDF should generate with Burned Redaction if Only Burn Redaction is enabled");

		// delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56073
	 * @Description: To Verify that Production should export Text files for Document
	 *               level successfully.
	 */
	@Test(description = "RPMXCON-56073", enabled = true, groups = { "regression" })
	public void getExortTextforDocument() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56073 -Production Sprint 06");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		prefixID = Input.randomText + Utility.dynamicNameAppender();
		suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		// Verify pdf with burn redaction
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFWithMultiPage(tagname);
		page.fillingTextSection();
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
		base.passedStep("Verified that Production should export Text files for Document level successfully");

		// delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56083
	 * @Description: To Verify that Production should generate successfully by
	 *               selecting only DAT and 'Generate TIFF' option.
	 */
	@Test(description = "RPMXCON-56083", enabled = true, groups = { "regression" })
	public void getProductionPageWithDatTiff() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56083 -Production Sprint 06");

		String tagNameTechnical = Input.tagNameTechnical;
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		prefixID = Input.randomText + Utility.dynamicNameAppender();
		suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);
		// Verify
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname, tagNameTechnical);
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
		base.passedStep(
				"Verified that Production should generate successfully by selecting only DAT and 'Generate TIFF' option");

		// delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56086
	 * @Description: To Verify that Production should generate successfully by
	 *               selecting only DAT and 'Generate PDF' option with Priv
	 *               Placholder.
	 */
	@Test(description = "RPMXCON-56086", enabled = true, groups = { "regression" })
	public void getProductionPageWithDatPdf() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56086 -Production Sprint 06");

		String tagNameTechnical = Input.tagNameTechnical;
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// Verify
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSection(tagname, tagNameTechnical);
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
		base.passedStep(
				"Verified that Production should generate successfully by selecting only DAT and 'Generate PDF' option with Priv Placholder");

		// delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith.Senthilkumar RPMXCON-56151
	 * @Description:To Verify that Regenerate button Popup should close on clicking
	 *                 on Cancel button.
	 */
	@Test(description = "RPMXCON-56151", enabled = true, groups = { "regression" })
	public void regeneratePopUpClickCancel() throws InterruptedException {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56151 -Production Sprint 07");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// Verify
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickGenarateWaitForRegenarate();
		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.prodReservingBatesRangeFailedProduction1();
		base.passedStep("Verified that  Regenerate button Popup should close on clicking on Cancel button");

		// delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith.Senthilkumar RPMXCON-56109
	 * @Description:To Verify the error message for MP3 component when 'Disable
	 *                 generate load File.
	 */
	@Test(description = "RPMXCON-56109", enabled = true, groups = { "regression" })
	public void getMp3DisableGenarateLoadFile() throws InterruptedException {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56109 -Production Sprint 07");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		BaseClass base = new BaseClass(driver);
		// Verify
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		driver.scrollingToBottomofAPage();
		page.advancedProductionComponentsMP3DisableGenarateFileLoad();
		driver.scrollPageToTop();
		base.waitForElement(page.getMarkCompleteLink());
		page.getMarkCompleteLink().waitAndClick(10);
		;
		base.VerifyWarningMessage(
				"For the MP3 Files component, you must either enable LST load file option or specify the MP3 file path in the DAT, in order to generate a load file for the generated MP3s files.");

		base.passedStep("Verified the error message for MP3 component when 'Disable generate load File ");
		loginPage.logout();
	}

	/**
	 * @author Aathith.Senthilkumar RPMXCON-56101
	 * @Description:To Verify the error message for TIFF/PDF component when 'Enabled
	 *                 privileg doc without tag or text'.
	 */
	@Test(description = "RPMXCON-56101", enabled = true, groups = { "regression" })
	public void verifyErrormsgTiffPdf() throws InterruptedException {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56101 -Production Sprint 07");

		foldername = "FolderProd" + Utility.dynamicNameAppender();

		// Verify
		BaseClass base = new BaseClass(driver);
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionWithError();

		driver.scrollPageToTop();
		base.waitForElement(page.getMarkCompleteLink());
		page.getMarkCompleteLink().waitAndClick(10);
		;
		base.VerifyErrorMessage(
				"Privileged tags or corresponding placeholder text is missing in the Privileged Placeholdering of the TIFF/PDF section.");

		foldername = "FolderProd" + Utility.dynamicNameAppender();

		// Verify
		page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSectionWithError();
		driver.scrollPageToTop();
		base.waitForElement(page.getMarkCompleteLink());
		page.getMarkCompleteLink().waitAndClick(10);
		;
		base.VerifyErrorMessage(
				"Privileged tags or corresponding placeholder text is missing in the Privileged Placeholdering of the TIFF/PDF section.");

		base.passedStep(
				"Verified the error message for TIFF/PDF component when 'Enabled privileg doc without tag or text'");
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56041
	 * @Description: To Verify that after Reserving Bates Range completed it should
	 *               displays 'Reserving Bates Range Complete' status on Grid View
	 *               on Production Home page.
	 */
	@Test(description = "RPMXCON-56041", enabled = true, groups = { "regression" })
	public void verifyBatesRangecompletedOnGridView() throws Exception {
		loginPage.logout();

		// Login as a RMU
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56041 -Production Sprint 07");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		prefixID = Input.randomText + Utility.dynamicNameAppender();
		suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolderInRMU(foldername);
		tagsAndFolderPage.createNewTagwithClassificationInRMU(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// Verify archive status on Grid view
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
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickOnGenerateButton();

		page.goToProductionGridView();
		page.verifyProductionStatusInHomePageGridView("Reserving Bates Range Complete", productionname);
		base.passedStep(
				"Verified that after Reserving Bates Range completed it should displays 'Reserving Bates Range Complete' status on Grid View on Production Home page");

		// delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56034
	 * @Description: To Verify that after Archiving is completed it should displays
	 *               'Creating Archive Complete' status on Production Generation
	 *               page.
	 */
	@Test(description = "RPMXCON-56034", enabled = true, groups = { "regression" })
	public void createArchivingStatusVerifyOnGenPage() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56034 -Production Sprint 07");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		// tagname = "Tag" + Utility.dynamicNameAppender();
		prefixID = Input.randomText + Utility.dynamicNameAppender();
		suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
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
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();

		// Wait until Generate button enables
		page.fillingGeneratePageWithContinueGenerationPopupWithoutWait();

		driver.waitForPageToBeReady();
		page.verifyProductionStatusInGenPage("Creating Archive Complete");
		base.passedStep(
				"Verified that after Archiving is completed it should displays 'Creating Archive Complete' status on Production Generation page");

		// delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56035
	 * @Description: To Verify that Production status displays as Draft on
	 *               Production Grid View.
	 */
	@Test(description = "RPMXCON-56035", enabled = true, groups = { "regression" })
	public void verifyDraftStatusOnGridView() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56035 -Production Sprint 07");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		// tagname = "Tag" + Utility.dynamicNameAppender();
		prefixID = Input.randomText + Utility.dynamicNameAppender();
		suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// Verify archive status on Gen page
		ProductionPage page = new ProductionPage(driver);
		page.navigateToProductionPage();
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
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();

		// Go To Production Home Page
		page.navigateToProductionPage();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		page.getGridView().waitAndClick(10);
		driver.waitForPageToBeReady();
		page.verifyProductionStatusInHomePageGridView("DRAFT", productionname);
		loginPage.logout();

		// Login as a RMU
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		beginningBates = page.getRandomNumber(2);
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

		// Go To Production Home Page
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		page.getGridView().waitAndClick(10);
		driver.waitForPageToBeReady();
		page.verifyProductionStatusInHomePageGridView("DRAFT", productionname);

		base.passedStep("Verify that Production status displays as Draft on Production Grid View");

		// delete tag ang folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56036
	 * @Description: To Verify that after Pre-gen checks is in progress, it will
	 *               displays status on Production Grid view.
	 */
	@Test(description = "RPMXCON-56036", enabled = true, groups = { "regression" })
	public void verifyPreGenStatusOnGridView() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56036 -Production Sprint 07");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		// tagname = "Tag" + Utility.dynamicNameAppender();
		prefixID = Input.randomText + Utility.dynamicNameAppender();
		suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		// tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

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
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickGenarateWithoutWait();

		// Go To Production Home Page
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		page.getGridView().waitAndClick(10);
		driver.waitForPageToBeReady();
		page.verifyProductionStatusInHomePageGridView("Pre-Gen Checks -", productionname);
		loginPage.logout();

		// Login as a RMU
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);

		page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		beginningBates = page.getRandomNumber(2);
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
		page.clickGenarateWithoutWait();

		// Go To Production Home Page
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		page.getGridView().waitAndClick(10);
		driver.waitForPageToBeReady();
		page.verifyProductionStatusInHomePageGridView("Pre-Gen Checks -", productionname);

		base.passedStep(
				"Verified that after Pre-gen checks is in progress, it will displays status on Production Grid view");

		// delete tag ang folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		// tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56029
	 * @Description: To Verify that after destination copy is completed it should
	 *               displays 'Exporting Files' status on Production Progress bar
	 *               Tile View.
	 */
	@Test(description = "RPMXCON-56029", enabled = true, groups = { "regression" })
	public void verifyExportinFilesStatusOnTileView() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56029 -Production Sprint 07");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		prefixID = Input.randomText + Utility.dynamicNameAppender();
		suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
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
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutWait();

		// Go To Production Home Page
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		// verification
		page.verifyProductionStatusInHomePage("Exporting Files", productionname);
		base.passedStep(
				"Verified that after destination copy is completed it should displays 'Exporting Files' status on Production Progress bar Tile View");

		// delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56030
	 * @Description: To Verify that after destination copy is completed it should
	 *               displays 'Exporting Files' status on Production Generate tab.
	 */
	@Test(description = "RPMXCON-56030", enabled = true, groups = { "regression" })
	public void verifyExportinFilesStatusOnGen() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56030 -Production Sprint 07");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		prefixID = Input.randomText + Utility.dynamicNameAppender();
		suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
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
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutWait();

		page.verifyProductionStatusInGenPage("Exporting Files");
		base.passedStep(
				"Verified that after destination copy is completed it should displays 'Exporting Files' status on Production Generate tab");

		// delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56048
	 * @Description: Verify that after LST generation, if Destination Copy is in
	 *               progress, it will displays status as 'Exporting Files' on
	 *               Production Grid View.
	 */
	@Test(description = "RPMXCON-56048", enabled = true, groups = { "regression" })
	public void verifyExportinFilesStatusOnGridView() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56048 -Production Sprint 08");
		base.stepInfo(
				"Verify that after LST generation, if Destination Copy is in progress, it will displays status as 'Exporting Files' on Production Grid View.");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		prefixID = Input.randomText + Utility.dynamicNameAppender();
		suffixID = Input.randomText + Utility.dynamicNameAppender();

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
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutWait();

		// Go To Production Home Page
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		page.getGridView().waitAndClick(10);
		driver.waitForPageToBeReady();
		page.verifyProductionStatusInHomePageGridView("Exporting Files", productionname);
		base.passedStep(
				"Verified that after LST generation, if Destination Copy is in progress, it will displays status as 'Exporting Files' on Production Grid View");

		// delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-50017
	 * @Description: Verify if currently 'AllProductionBatesRanges' is searchable,
	 *               then we should leave the field to be searchable..
	 */
	//@Test(description = "RPMXCON-50017", enabled = true, groups = { "regression" })
	public void verifyAllProductionBatesRangesSearchable() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-50017 -Production Sprint 09");

		ProjectFieldsPage projectField = new ProjectFieldsPage(driver);
		projectField.navigateToProjectFieldsPage();
		projectField.getAllProductionBatesRanges().waitAndClick(5);
		//projectField.enableIsSearchableBatesRangeIsSelected();

		SessionSearch sessionSearch = new SessionSearch(driver);
		this.driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.waitForPageToBeReady();
		sessionSearch.getBasicSearch_MetadataBtn().waitAndClick(5);
		sessionSearch.getSelectMetaData().waitAndClick(5);
		boolean flag = sessionSearch.getAllProductionBatesRanges().isDisplayed();
		SoftAssert softAssertion = new SoftAssert();
		if (flag) {
			softAssertion.assertTrue(flag);
			base.passedStep("'AllProductionBatesRanges' field is Available in Dropdown");
		} else {
			base.failedStep("'AllProductionBatesRanges' field is not Available in Dropdown");

		}
		projectField.navigateToProjectFieldsPage();
		projectField.getAllProductionBatesRanges().waitAndClick(5);
		projectField.enableIsSearchableBatesRangeIsSelected();
		base.passedStep(
				"Verified if currently 'AllProductionBatesRanges' is searchable, then we should leave the field to be searchable..");
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-50018
	 * @Description: Verify if in existing project, 'AllProductionBatesRange' field
	 *               is searchable and if this field has been edited and is make it
	 *               non-searchable, then this field cannot make as searchable again
	 */
	// commenting since the functionality is not present
	// @Test(description="RPMXCON-50018",enabled = true, groups = { "regression" } )
	public void verifyAllProductionBatesRangesNotSearchable() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-50018 -Production Sprint 09");

		ProjectFieldsPage projectField = new ProjectFieldsPage(driver);
		projectField.navigateToProjectFieldsPage();
		projectField.getAllProductionBatesRanges().waitAndClick(5);
		projectField.enableIsSearchableBatesRangeIsSelected();

		SessionSearch sessionSearch = new SessionSearch(driver);
		this.driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.waitForPageToBeReady();
		sessionSearch.getBasicSearch_MetadataBtn().waitAndClick(5);
		sessionSearch.getSelectMetaData().waitAndClick(5);
		boolean flag = sessionSearch.getAllProductionBatesRanges().isDisplayed();
		SoftAssert softAssertion = new SoftAssert();
		if (flag) {
			softAssertion.assertTrue(flag);
			base.passedStep("'AllProductionBatesRanges' field is Available in Dropdown");
		} else {
			base.failedStep("'AllProductionBatesRanges' field is not Available in Dropdown");

		}
		projectField.navigateToProjectFieldsPage();
		projectField.getAllProductionBatesRanges().waitAndClick(5);
		projectField.disableIsSearchableBatesRangeIsSelected();
		projectField.getAllProductionBatesRanges().waitAndClick(5);
		projectField.disableIsSearchableBatesRangeIsSelected();
		base.passedStep(
				"Verified if in existing project, 'AllProductionBatesRange' field is searchable and if  this field has been edited and is make it non-searchable, then this field cannot make as searchable again");
		loginPage.logout();
	}

	/**
	 * @author Aathith.Senthilkumar 49099
	 * @Description In production, Preview should displays correctly
	 * 
	 */
	@Test(description = "RPMXCON-49099", enabled = true, groups = { "regression" })
	public void verifyPreviewInProduction() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49099 -Production Sprint 09");
		base.stepInfo("In production, Preview should displays correctly");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		prefixID = Input.randomText + Utility.dynamicNameAppender();
		suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// Verify
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname, Input.tagNameTechnical);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.getPreviewprod().waitAndClick(5);
		driver.waitForPageToBeReady();
		base.passedStep("In production, Preview should displays correctly");

		// delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Aathith.Senthilkumar 49104
	 * @Description To verify that EmailAuthorNameAndAddress,
	 *              EmailToNamesAndAddresses, EmailCCNamesAndAddresses, and
	 *              EmailBCCNamesAndAddresses fields are exported properly in the
	 *              correct format in the Production, DAT.
	 * 
	 */
	@Test(description = "RPMXCON-49104", enabled = true, groups = { "regression" })
	public void verifyDatFieldsAreExport() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49104 -Production Sprint 09");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String newExport = "Ex" + Utility.dynamicNameAppender();
		prefixID = Input.randomText + Utility.dynamicNameAppender();
		suffixID = Input.randomText + Utility.dynamicNameAppender();

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

		// Verify
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
		page.getDAT_AddField().waitAndClick(5);
		page.addDatField(1, "Email", "EmailAuthorNameAndAddress");
		page.getDAT_AddField().waitAndClick(5);
		page.addDatField(2, "Email", "EmailToNamesAndAddresses");
		page.getDAT_AddField().waitAndClick(5);
		page.addDatField(3, "Email", "EmailCCNamesAndAddresses");
		page.getDAT_AddField().waitAndClick(5);
		page.addDatField(4, "Email", "EmailBCCNamesAndAddresses");

		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommitandDownload();

		base.passedStep(
				"Verified that EmailAuthorNameAndAddress, EmailToNamesAndAddresses, EmailCCNamesAndAddresses, and EmailBCCNamesAndAddresses fields are exported properly in the correct format in the Production, DAT.");
		// delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Aathith.Senthilkumar 48978
	 * @Description Verify that branding text should be wrapped when Branding text
	 *              to all the six locations exceeds the space while production for
	 *              a PDF file.
	 * 
	 */
	@Test(description = "RPMXCON-48978", enabled = true, groups = { "regression" })
	public void verifyBrandingTextToSixLocation() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48978 -Production Sprint 09");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		prefixID = Input.randomText + Utility.dynamicNameAppender();
		suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		// Verify
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSectionWithMultiBranding(tagname);
		base.stepInfo("Added a multi line branding to all six  locations");
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

		base.passedStep(
				"Verified that branding text should be wrapped when Branding text to all the six locations exceeds the space while production for a PDF file");

		// delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith.Senthilkumar 48660
	 * @Description To verify that if "Do Not Produce TIFFs/PDFs for Natively
	 *              Produced Docs" is enabled , then TIFFs with redaction should be
	 *              produced. It should not export Natives
	 * 
	 */
	@Test(description = "RPMXCON-48660", enabled = true, groups = { "regression" })
	public void verifyDoNotProduceTiffToggleOn() throws Exception {
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48660 -Production Sprint 09");

		foldername = "RedactFolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();
		prefixID = Input.randomText + Utility.dynamicNameAppender();
		suffixID = Input.randomText + Utility.dynamicNameAppender();

		RedactionPage redactionpage = new RedactionPage(driver);

		driver.waitForPageToBeReady();
		redactionpage.manageRedactionTagsPage(Redactiontag1);
		System.out.println("First Redaction Tag is created" + Redactiontag1);

		DocExplorerPage docExp = new DocExplorerPage(driver);
		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		docExp.navigateToDocExplorerPage();
		docExp.documentSelectionIteration();
		docExp.docExpViewInDocView();

		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		DocViewPage doc = new DocViewPage(driver);
		doc.documentSelection(1);
		driver.waitForPageToBeReady();
		Thread.sleep(3000);
		docViewRedactions.redactRectangleUsingOffset(10, 10, 20, 20);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag1);

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolderInRMU(foldername);

		// Adding folder to bulkfolder
		DocExplorerPage docExplorer = new DocExplorerPage(driver);
		driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		docExplorer.documentSelectionIteration();
		docExplorer.bulkFolderExisting(foldername);

		// Verify
		ProductionPage page = new ProductionPage(driver);
		driver.getWebDriver().get(Input.url + "Production/Home");
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTextSection();
		page.fillingTIFFSectionwithBurnRedactionSelectRedactTag(Redactiontag1);
		driver.scrollPageToTop();
		page.getDoNotProduceFullContentTiff().ScrollTo();
		page.getDoNotProduceFullContentTiff().waitAndClick(10);
		base.stepInfo(
				"Enabled 'Do not produce full content TIFF / PDFs or placeholder TIFF / PDFs for Natively Produced Docs' toggle ON");
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

		// Verify
		page = new ProductionPage(driver);
		driver.getWebDriver().get(Input.url + "Production/Home");
		productionname = "p" + Utility.dynamicNameAppender();
		beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTextSection();
		page.fillingPDFSectionwithBurnRedactionSelectRedactTag(Redactiontag1);
		driver.scrollPageToTop();
		page.getDoNotProduceFullContentTiff().ScrollTo();
		page.getDoNotProduceFullContentTiff().waitAndClick(10);
		base.stepInfo(
				"Enabled 'Do not produce full content TIFF / PDFs or placeholder TIFF / PDFs for Natively Produced Docs' toggle ON");
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupHigerWaitTime();

		base.passedStep(
				"Verified that if 'Do Not Produce TIFFs/PDFs for Natively Produced Docs' is enabled , then TIFFs with redaction should be produced. It should not export Natives");

		// delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		loginPage.logout();
	}

	/**
	 * @author Aathith.Senthilkumar 48454
	 * @Description To verify that Production should generate successfully for PDF
	 *              docs.
	 * 
	 */
	@Test(description = "RPMXCON-48454", enabled = true, groups = { "regression" })
	public void verifyProdGenSuccesInPdfDoc() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48454 -Production Sprint 09");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		prefixID = Input.randomText + Utility.dynamicNameAppender();
		suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		// Verify
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSectionwithBurnRedaction(tagname);
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

		base.passedStep("Verified that Production should generate successfully for PDF docs");

		// delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith.Senthilkumar 48279
	 * @Description To Verify Enabling Placeholder for Privilege Doc at PrivGuard.
	 * 
	 */
	@Test(description = "RPMXCON-48279", enabled = true, groups = { "regression" })
	public void verifiyEnablePlaceholderAtPrivDoc() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48279 -Production Sprint 09");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		prefixID = Input.randomText + Utility.dynamicNameAppender();
		suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// Verify
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTiffSectionDisablePrivilegedDocs();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPageWithPrivPlaceHolder(tagname);
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		// Verify
		page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSectionDisablePrivilegedDocs();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPageWithPrivPlaceHolder(tagname);
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		base.passedStep("Verified Enabling Placeholder for Privilege Doc at PrivGuard.");

		// delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-55981
	 * @Description: Verify that after LST generation, if Destination Copy is in
	 *               progress, it will displays status as 'Exporting Files' on
	 *               Production Generation tab
	 */
	@Test(description = "RPMXCON-55981", enabled = true, groups = { "regression" })
	public void verifyLstGenExportinFilesStatusOnGen() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-55981 -Production Sprint 09");
		base.stepInfo(
				"Verify that after LST generation, if Destination Copy is in progress, it will displays status as 'Exporting Files' on Production Generation tab");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		prefixID = Input.randomText + Utility.dynamicNameAppender();
		suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.bulkFolderExisting(foldername);

		// Verify archive status on Gen page
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
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickOnGenerateButton();

		page.verifyProductionStatusInGenPage("Load File Generation In Progress");
		page.verifyProductionStatusInGenPage("Exporting Files");
		base.passedStep(
				"Verified that after LST generation, if Destination Copy is in progress, it will displays status as 'Exporting Files' on Production Generation tab");

		// delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-55980
	 * @Description: Verify that after LST generation, if Destination Copy is in
	 *               progress, it will displays status as 'Exporting Files' on
	 *               Production Progress bar Tile View.
	 */
	@Test(description = "RPMXCON-55980", enabled = true, groups = { "regression" })
	public void verifyLstGenExportinFilesStatusOnTileView() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-55980 -Production Sprint 09");
		base.stepInfo(
				"Verify that after LST generation, if Destination Copy is in progress, it will displays status as 'Exporting Files' on Production Progress bar Tile View");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		prefixID = Input.randomText + Utility.dynamicNameAppender();
		suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.bulkFolderExisting(foldername);

		// Verify archive status on Gen page
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
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickOnGenerateButton();

		// Go To Production Home Page
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		// verification
		page.verifyProductionStatusInHomePage("Generating Load File", productionname);
		page.verifyProductionStatusInHomePage("Exporting Files", productionname);
		base.passedStep(
				"Verified that after LST generation, if Destination Copy is in progress, it will displays status as 'Exporting Files' on Production Progress bar Tile View");

		// delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49040
	 * @Description: To verify that 'Production Creation Date' should displayed when
	 *               it saved first time.
	 */
	@Test(description = "RPMXCON-49040", enabled = true, groups = { "regression" })
	public void verifyProductionCreationDate() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49040 -Production Sprint 09");
		base.stepInfo("To verify that 'Production Creation Date' should displayed when it saved first time");

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.getAddNewProductionbutton().waitAndClick(10);
		page.getProductionName().SendKeys(productionname);
		page.getSaveButton().waitAndClick(10);
		base.stepInfo("production saved");
		driver.waitForPageToBeReady();
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		page.getGridView().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.stepInfo("Nativated to production Grid View");
		String createdTime = page.getProductionCreatedTimeInGridView(productionname).getText();

		DateFormat dateFormat = new SimpleDateFormat("dd");
		Date date = new Date();
		String date1 = dateFormat.format(date);
		System.out.println("Current date" + date1);
		boolean flag = createdTime.contains(date1);
		if (flag) {
			base.passedStep("current date is displayed on production grid view");
			System.out.println("date visible");
		} else {
			base.failedStep("date is not contain in text");
			System.out.println("date not visible");
		}
		base.passedStep("Verified that 'Production Creation Date' should displayed when it saved first time");
		loginPage.logout();

	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48569
	 * @Description: To verify that 'Bates Range' should be blank before pre-gen
	 *               check completed.
	 */
	@Test(description = "RPMXCON-48569", enabled = true, groups = { "regression" })
	public void verifyBateRangeBlankGenPage() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48569 -Production Sprint 09");
		base.stepInfo("To verify that 'Bates Range' should be blank before pre-gen check completed.");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		prefixID = Input.randomText + Utility.dynamicNameAppender();
		suffixID = Input.randomText + Utility.dynamicNameAppender();

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
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickOnGenerateButton();
		String blank = page.getProd_BatesRange().getText();
		System.out.println("bank text" + blank + "message");
		if (blank.trim().equals("")) {
			base.passedStep("Bates Range' is blank before pre-gen check complete");
		} else {
			base.failedStep("Bates Range' didn't blank before pre-gen check complete");
		}
		base.passedStep("To verify that 'Bates Range' should be blank before pre-gen check completed.");

		// delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48344
	 * @Description: To verify that Tiff/PDF should generate with Priv
	 *               placeholdering even though 'Tech Issue Doc' placeholdering,
	 *               Burn redactions and File group/tag based placeholdering is
	 *               exists.
	 */
	@Test(description = "RPMXCON-48344", enabled = true, groups = { "regression" })
	public void verifyGenWithPrivplcholderTechIssueRedactionTag() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48344 -Production Sprint 09");
		base.stepInfo(
				"To verify that Tiff/PDF should generate with Priv placeholdering even though 'Tech Issue Doc' placeholdering, Burn redactions and File group/tag based placeholdering is exists.");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String tagname1 = "Tag" + Utility.dynamicNameAppender();
		prefixID = Input.randomText + Utility.dynamicNameAppender();
		suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateTagwithClassification(tagname1, Input.technicalIssue);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		 String redactiontag1 ="Default Redaction Tag";
		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		driver.waitForPageToBeReady();
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname1);

		// prod tiff
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname);
		page.selectTechIssueDoc(tagname1);
		page.fillingNativeDocsPlaceholder(tagname);
		page.selectBurnReduction();
		page.RedactionWithTag(redactiontag1);
		driver.scrollPageToTop();
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerate(prefixID, suffixID, foldername, productionname, beginningBates);

		// Verify
		page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSection(tagname);
		page.selectTechIssueDoc(tagname1);
		page.fillingNativeDocsPlaceholder(tagname);
		page.selectBurnReduction();
		page.RedactionWithTag(redactiontag1);
		driver.scrollPageToTop();
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerate(prefixID, suffixID, foldername, productionname, beginningBates);
		base.passedStep(
				"verified that Tiff/PDF should generate with Priv placeholdering even though 'Tech Issue Doc' placeholdering, Burn redactions and File group/tag based placeholdering is exists.");

		// delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48533
	 * @Description: To verify that if Blank Page Removal toggle is OFF then it
	 *               should produced the PDF with blank pages
	 */
	@Test(description = "RPMXCON-48533", enabled = true, groups = { "regression" })
	public void verifyBlankRemovalToggleWithpdfGen() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48533 -Production Sprint 09");
		base.stepInfo(
				"To verify that if Blank Page Removal toggle is OFF then it should produced the PDF with blank pages");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		prefixID = Input.randomText + Utility.dynamicNameAppender();
		suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.bulkFolderExisting(foldername);

		// Verify archive status on Gen page
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSection(tagname);
		page.banlkPageRemovalToggleOffCheck();
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerate(prefixID, suffixID, foldername, productionname, beginningBates);

		base.passedStep("verified that if Blank Page Removal toggle is OFF then it should produced the PDF with blank pages");

		// delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-55920
	 * @Description: Verify if PA Select the Production using a template that has
	 *               only tags selected in the native components, then Component tab
	 *               should Complete without any error.
	 */
	@Test(description = "RPMXCON-55920", enabled = true, groups = { "regression" })
	public void verifiyProdComponentTabWithoutError() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-55920 -Production Sprint 10");
		base.stepInfo(
				"Verify if PA Select the Production using a template that has only tags selected in the native components, then Component tab should Complete without any error.");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		TempName = "Templete" + Utility.dynamicNameAppender();
		prefixID = Input.randomText + Utility.dynamicNameAppender();
		suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.bulkFolderExisting(foldername);

		// Verify archive status on Gen page
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSectionWithTags(tagname);
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);

		page = new ProductionPage(driver);
		page.navigateToProductionPage();
		page.filterCompletedProduction();
		page.getprod_ActionButton_Reusable(productionname).waitAndClick(10);
		driver.waitForPageToBeReady();
		page.getprod_Action_SaveTemplate_Reusable(productionname).Enabled();
		page.getprod_Action_SaveTemplate_Reusable(productionname).waitAndClick(10);

		page.saveTemple(TempName);

		page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.getAddNewProductionbutton().waitAndClick(10);
		page.getProductionName().SendKeys(productionname);
		String loadfile = TempName + " (Production)";
		page.getprod_LoadTemplate().selectFromDropdown().selectByVisibleText(loadfile);
		base.waitTillElemetToBeClickable(page.getBasicInfoMarkComplete());
		page.getBasicInfoMarkComplete().waitAndClick(10);
		driver.waitForPageToBeReady();
		page.getDATTab().waitAndClick(10);
		page.getElementDisplayCheck(page.getDAT_FieldClassification1());
		driver.waitForPageToBeReady();
		base.waitTillElemetToBeClickable(page.getMarkCompleteLink());
		page.getMarkCompleteLink().Enabled();
		page.getMarkCompleteLink().waitAndClick(10);
		BaseClass base = new BaseClass(driver);
		base.VerifySuccessMessage("Mark Complete successful");
		base.passedStep("It should be completed without any error.");
		base.passedStep(
				"Verified if PA Select the Production using a template that has only tags selected in the native components, then Component tab should Complete without any error.");

		// delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48183
	 * @Description: To Verify Document Selection Page (for Folder/Tag/Search;
	 *               Include Family ;Total Count)
	 */
	@Test(description = "RPMXCON-48183", enabled = true, groups = { "regression" })
	public void verifyDocmentSelectionPage() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48183 -Production Sprint 10");
		base.stepInfo("To Verify Document Selection Page (for Folder/Tag/Search; Include Family ;Total Count)");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		TempName = "Templete" + Utility.dynamicNameAppender();
		prefixID = Input.randomText + Utility.dynamicNameAppender();
		suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.bulkFolderExisting(foldername);

		// Verify archive status on Gen page
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		driver.waitForPageToBeReady();
		page.visibleCheck("Folder");
		page.visibleCheck("Tag");
		page.visibleCheck("Search");
		page.visibleCheck("Include Family");
		page.visibleCheck("Count");

		base.passedStep("Verified Document Selection Page (for Folder/Tag/Search; Include Family ;Total Count)");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56019
	 * @Description: Verify that after Reserving Bates Range completed it should
	 *               displays 'Reserving Bates Range Completed' status on Progress
	 *               bar in Tile View on Production Home page
	 */
	@Test(description = "RPMXCON-56019", enabled = true, groups = { "regression" })
	public void verifiyBateRangeCompletedOnTileView() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56019 -Production Sprint 10");
		base.stepInfo(
				"Verify that after Reserving Bates Range completed it should displays 'Reserving Bates Range Completed' status on Progress bar in Tile View on Production Home page");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		TempName = "Templete" + Utility.dynamicNameAppender();
		prefixID = Input.randomText + Utility.dynamicNameAppender();
		suffixID = Input.randomText + Utility.dynamicNameAppender();

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

		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		// verification
		page.verifyProductionStatusInHomePage("Reserving Bates Range Complete", productionname);
		base.passedStep(
				"Verified that after Reserving Bates Range completed it should displays 'Reserving Bates Range Completed' status on Progress bar in Tile View on Production Home page");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56022
	 * @Description: Verify that once LST generation is started it should displays '
	 *               Generating Load Files' status on Production Tile View
	 */
	@Test(description = "RPMXCON-56022", enabled = true, groups = { "regression" })
	public void verifiyLSTGenOnTileView() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56022 -Production Sprint 10");
		base.stepInfo(
				"Verify that once LST generation is started it should displays ' Generating Load Files' status on Production Tile View");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		TempName = "Templete" + Utility.dynamicNameAppender();
		prefixID = Input.randomText + Utility.dynamicNameAppender();
		suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.bulkFolderExisting(foldername);

		// Verify archive status on Gen page
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
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
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
		// verification
		page.verifyProductionStatusInHomePage("Generating Load Files", productionname);
		base.passedStep(
				"Verify that once LST generation is started it should displays ' Generating Load Files' status on Production Tile View");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56045
	 * @Description: Verify that once LST generation is started it should displays '
	 *               Generating Load Files' status on Production Grid View
	 */
	@Test(description = "RPMXCON-56045", enabled = true, groups = { "regression" })
	public void verifiyGenarationloadFilesOnGridView() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56045 -Production Sprint 10");
		base.stepInfo(
				"Verify that once LST generation is started it should displays ' Generating Load Files' status on Production Grid View");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		prefixID = Input.randomText + Utility.dynamicNameAppender();
		suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.bulkFolderExisting(foldername);

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
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
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
		driver.waitForPageToBeReady();
		page.getGridView().waitAndClick(10);
		driver.waitForPageToBeReady();
		page.verifyProductionStatusInHomePageGridView("Generating Load Files", productionname);
		base.passedStep(
				"Verified that once LST generation is started it should displays ' Generating Load Files' status on Production Grid View");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56012
	 * @Description: Verify that if Production is regeneate then previous sharable
	 *               link should not be usabel
	 */
	@Test(description = "RPMXCON-56012", enabled = true, groups = { "regression" })
	public void verifyRegenarateSharable() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56012 -Production Sprint 10");
		base.stepInfo("Verify that if Production is regeneate then previous sharable link should not be usabel");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		TempName = "Templete" + Utility.dynamicNameAppender();
		prefixID = Input.randomText + Utility.dynamicNameAppender();
		suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
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
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		String link = page.getCopySharableLink();

		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		page.getGearIconForProdName(productionname).waitAndClick(10);
		page.getOpenWizard().waitAndClick(10);
		page.getBackButton().waitAndClick(10);
		page.getBackButton().waitAndClick(10);
		page.getBackButton().waitAndClick(10);
		page.getMarkInCompleteBtn().waitAndClick(10);
		page.fillingProductionLocationPageAndPassingText(TempName);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		String parentWindow = page.openNewTab(link);
		page.visibleCheck("LINK EXPIRED");
		driver.close();
		driver.getWebDriver().switchTo().window(parentWindow);
		base.passedStep("Verified that if Production is regeneate then previous sharable link should not be usabel");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56006
	 * @Description: Verify that after the regenerate the new links, previous links
	 *               and password will no longer work,
	 */
	@Test(description = "RPMXCON-56006", enabled = true, groups = { "regression" })
	public void verifyRegenareOldLinkNoLongerWork() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56006 -Production Sprint 10");
		base.stepInfo(
				"Verify that  after the regenerate the new links, previous links and password will no longer work");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		prefixID = Input.randomText + Utility.dynamicNameAppender();
		suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
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
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		String link = page.getCopySharableLink();

		String parentWindow = page.openNewTab(link);
		page.visibleCheck("Download Production Archive File");
		base.passedStep("this Link should accessible to the user");
		driver.close();
		driver.getWebDriver().switchTo().window(parentWindow);

		page.getRegenarateLinkBtn().waitAndClick(10);
		base.stepInfo("new link should be generated");

		String parentWindow1 = page.openNewTab(link);
		page.visibleCheck("LINK EXPIRED");
		base.passedStep("Old link Expired");
		driver.close();
		driver.getWebDriver().switchTo().window(parentWindow1);

		base.passedStep(
				"Verified that  after the regenerate the new links, previous links and password will no longer work");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-55997
	 * @Description: Verify that in the Production components page 'Archive File
	 *               from FTP' component is not available
	 */
	@Test(description = "RPMXCON-55997", enabled = true, groups = { "regression" })
	public void verifyArchiveFileFromFTPNotDisplayed() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-55997 -Production Sprint 10");
		base.stepInfo(
				"Verify that in the Production components page 'Archive File from FTP' component is not available");
		String Component = "Archive File from FTP";

		// Verify
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		driver.waitForPageToBeReady();
		base.stepInfo("page is in Production Component page");

		if (page.text(Component).isDisplayed()) {
			base.failedStep(Component + " is visibled");
			System.out.println(Component + " is visible");
		} else {
			base.passedStep(Component + " is not visible");
			System.out.println(Component + " is not visible");
		}
		base.passedStep(
				"Verified that in the Production components page 'Archive File from FTP' component is not available");
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-49236
	 * @Description:To verify that in Production, from Document Selection tab, on
	 *                 clicking on document count link it should redirect to Doc
	 *                 List page with correct document count
	 */
	@Test(description = "RPMXCON-49236", enabled = true, groups = { "regression" })
	public void verifyNavigationToDocListPageFromDocumentSelectionTab() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49236 -Production Sprint 08");

		String testData1 = Input.testData1;
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);

		base.stepInfo("Navigating to doclist page");
		String docCount = page.navigatingToDocViewPage();

		DocListPage doc = new DocListPage(driver);
		base.stepInfo("Navigated  to doclist page and verifying the DocCount");
		String DocumentCount = doc.verifyingDocCount();

		base.stepInfo("Navigated back to Production page");
		page.verifyNavigationToProductionPage();
		base.textCompareEquals(docCount, DocumentCount, "The document count is equal as expected",
				"The document count is not equal as expected");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		loginPage.logout();

	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-49239
	 * @Description:To verify that after selecting the Next BatesNumbers, value
	 *                 should be auto-populated
	 */
	@Test(description = "RPMXCON-49239", enabled = true, groups = { "regression" })
	public void SelectNextBatesNumberAndVerifyingAutoPopulatedValue() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49239 -Production Sprint 08");
		String testData1 = Input.testData1;
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
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
		page.navigatingBackToNumberingAndSortingPage();
		page.SelectNextBatesNumber();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-49241
	 * @Description:To verify that 'Click here to View and select the bates
	 *                 number(S)' link should not be available in Mark Complete
	 *                 mode.
	 */
	@Test(description = "RPMXCON-49241", enabled = true, groups = { "regression" })
	public void verifyClickHerelinkNotAvailableInMarkComplete() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49241 -Production Sprint 08");

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.verifyClickHereLinkNotAvailableAtMarkComplete();
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-49243
	 * @Description:To verify that after clicking on 'Mark InComplete' button ,
	 *                 ''Click here to View and select the bates number(S)'' should
	 *                 be available and user can select the bates numbers
	 */
	@Test(description = "RPMXCON-49243", enabled = true, groups = { "regression" })
	public void verifyClickHerelinkAvailableInMarkInComplete() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49243 -Production Sprint 08");

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.verifyClickHereLinkNotAvailableAtMarkComplete();
		page.getMarkInCompleteBtn().waitAndClick(10);
		page.enteringNewNextBatesNumber();
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-49133
	 * @Description:To verify that when text is exported for Priv file then it
	 *                 should export the text with the Placeholder
	 */
	@Test(description = "RPMXCON-49133", enabled = true, groups = { "regression" })
	public void verifyExportInTIFFAndPDFPriviledgedPlaceHolder() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49133 -Production Sprint 08");
		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String exportname = "E" + Utility.dynamicNameAppender();
		String exportname1 = "E" + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;
		String newExport = "Ex" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();

		// create folder and tag
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch session = new SessionSearch(driver);
		session.basicContentSearch(testData1);
		session.bulkFolderExisting(foldername);

		// create export with TIFF
		ProductionPage page = new ProductionPage(driver);
		driver.getWebDriver().get(Input.url + "Production/Home");
		String text = page.getProdExport_ProductionSets().getText();
		if (text.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(newExport);
		}
		page.addANewExport(exportname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectPrivDocsInTiffSection(tagname);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		base.stepInfo("Export for priviledged doc in Tiff section is to generate");
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommitandDownload();

		page = new ProductionPage(driver);
		driver.getWebDriver().get(Input.url + "Production/Home");
		String text1 = page.getProdExport_ProductionSets().getText();
		if (text1.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(newExport);
		}
		page.addANewExport(exportname1);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSection(tagname, Input.searchString4);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportname1);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		base.stepInfo("Export for priviledged doc in pdf section is to generate");
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommitandDownload();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();

	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-49108
	 * @Description:To verify that ' Number Of Custodians' on Production Summary
	 *                 page
	 */
	@Test(description = "RPMXCON-49108", enabled = true, groups = { "regression" })
	public void verifyingUniqueCustodianInSummaryAndPreviewTab() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49108 -Production Sprint 08");
		String testData1 = Input.testData1;
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
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
		page.verifyingUniqueCustodianNameInSummaryPreviewTab();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		loginPage.logout();

	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-48662
	 * @Description:Create a Production with the Prerequisite: MP3 files and by
	 *                     selecting just the DAT file as a production component
	 */
	@Test(description = "RPMXCON-48662", enabled = true, groups = { "regression" })
	public void verifyProductionGenerateForMP3Docs() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48662 -Production Sprint 09");

		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.getMetaDataSearch();
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
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
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-48661
	 * @Description:Verify that production should be generated successfully for
	 *                     audio files
	 */
	@Test(description = "RPMXCON-48661", enabled = true, groups = { "regression" })
	public void verifyProductionGenerateForAudioFile() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48661 -Production Sprint 09");

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.audioSearch(Input.audioSearch, Input.language);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.SelectMP3FileAndVerifyLstFile();
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
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();

	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-48492
	 * @Description:To verify that If user select PrivTag and if Audio document is
	 *                 associated to that tag then Native should not produced
	 */
	@Test(description = "RPMXCON-48492", enabled = true, groups = { "regression" })
	public void SelectPrivTagWithAudioDocumentAndNativeNotProduced() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48492 -Production Sprint 09");

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.audioSearch(Input.audioSearch, Input.language);
//		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectPrivDocsInTiffSection(tagname);
		page.getAdvancedProductionComponent().waitAndClick(10);
		page.getMP3CheckBox().waitAndClick(10);
		page.fillingTextSection();
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
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);

		loginPage.logout();

	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-48493
	 * @Description:To verify that If user select PrivTag and if Audio document is
	 *                 not associated to that tag then Native should be produced
	 */
	@Test(description = "RPMXCON-48493", enabled = true, groups = { "regression" })
	public void SelectPrivTagWithAudioDocumentAndNativeProduced() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48493 -Production Sprint 09");

		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.audioSearch(Input.audioSearch, Input.language);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectPrivDocsInTiffSection(tagname);
		page.getAdvancedProductionComponent().waitAndClick(10);
		page.getMP3CheckBox().waitAndClick(10);
		page.fillingTextSection();
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
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();
	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-48504
	 * @Description:To verify that Tiff/PDF should generate with Priv placeholdering
	 *                 even though Burn redactions and File group/tag based
	 *                 placeholdering is exists.
	 */
	@Test(description = "RPMXCON-48504", enabled = true, groups = { "regression" })
	public void ProductionGenerateWithPrivHolderWithBurnRedaction() throws Exception {
		UtilityLog.info(Input.prodPath);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		base.stepInfo("RPMXCON-48504 -Production Sprint 09");
		String productionname = "p" + Utility.dynamicNameAppender();
		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname1 = "p" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String tagname1 = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();
		RedactionPage redactionpage = new RedactionPage(driver);

		driver.waitForPageToBeReady();
		redactionpage.manageRedactionTagsPage(Redactiontag1);
		System.out.println("First Redaction Tag is created" + Redactiontag1);

		DocExplorerPage docExp = new DocExplorerPage(driver);
		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		docExp.documentSelectionIteration();
		docExp.docExpViewInDocView();

		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		DocViewPage doc = new DocViewPage(driver);
		doc.documentSelection(1);
		driver.waitForPageToBeReady();
		docViewRedactions.redactRectangleUsingOffset(10, 10, 20, 20);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag1);

		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateTagwithClassification(tagname1, "Select Tag Classification");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.selectPrivDocsInTiffSection(tagname);
		page.fillingNativeDocsPlaceholder(tagname1);
		page.getClk_burnReductiontoggle().ScrollTo();
		page.getClk_burnReductiontoggle().waitAndClick(5);
		page.burnRedactionWithRedactionTag(Redactiontag1);
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

		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname1);
		String beginningBates1 = page.getRandomNumber(2);
		page.fillingDATSection();
		page.fillingPDFSection(tagname, Input.searchString4);
		page.fillingNativeDocsPlaceholder(tagname1);
		page.getClk_burnReductiontoggle().ScrollTo();
		page.getClk_burnReductiontoggle().waitAndClick(5);
		page.burnRedactionWithRedactionTag(Redactiontag1);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates1);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname1);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(tagname1, "Default Security Group");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		loginPage.logout();

	}

	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-48204
	 * @Description:To verify In Productions DAT, provide the TIFFPageCount for each
	 *                 document produced
	 */
	@Test(description = "RPMXCON-48204", enabled = true, groups = { "regression" })
	public void verifyProductionDATProvideTIFFPageCount() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48204 -Production Sprint 09");
		String bates = "B" + Utility.dynamicNameAppender();
		String bates1 = "B" + Utility.dynamicNameAppender();
		String foldername = "Folder" + Utility.dynamicNameAppender();

		String productionname1 = "p" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname1);
		page.fillingDATSection();
		page.addDATFieldAtSecondRow("Production", "TIFFPageCount", bates);
		page.addDATFieldAtThirdRow("Doc Basic", "DocID", bates1);
		page.fillingNativeSection();
		page.fillingPDFSectionWithBrandingText();
		page.slipSheetToggleEnable();
		page.availableFieldSelection("BatesNumber");
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname1);
		page.navigateToNextSection();
		page.viewingPreviewInSummaryTab();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		String beginningBates1 = page.getRandomNumber(2);
		page.fillingDATSection();
		page.addDATFieldAtSecondRow("Production", "TIFFPageCount", bates);
		page.addDATFieldAtThirdRow("Doc Basic", "DocID", bates1);
		page.fillingNativeSection();
		page.fillingTIFFSectionWithBatesNumber();
		page.slipSheetToggleEnable();
		page.availableFieldSelection("BatesNumber");
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates1);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.viewingPreviewInSummaryTab();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);

		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-48379
	 * @Description To verify that If user select RedactionTag and if non-audio
	 *              document is associated to the selected Redaction Tag then Native
	 *              should not produced
	 * 
	 */
	@Test(description = "RPMXCON-48379", enabled = true, groups = { "regression" })
	public void verifyNativeIsNotProducedAtGeneration() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48379 -Production Sprint 09");

		String foldername = "RedactFolderProd" + Utility.dynamicNameAppender();
		String Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		RedactionPage redactionpage = new RedactionPage(driver);
		this.driver.getWebDriver().get(Input.url+"Redaction/Redaction");
		redactionpage.selectDefaultSecurityGroup();
		driver.waitForPageToBeReady();

		redactionpage.getselectAllRedactionTag().isElementAvailable(15);
		base.waitForElement(redactionpage.getselectAllRedactionTag());
		redactionpage.getselectAllRedactionTag().waitAndClick(10);
		
		redactionpage.getselectActionToggle().isElementAvailable(15);
		base.waitForElement(redactionpage.getselectActionToggle());
		redactionpage.getselectActionToggle().waitAndClick(10);

		redactionpage.getselectNewFromDropdown().isElementAvailable(15);
		base.waitForElement(redactionpage.getselectNewFromDropdown());
		redactionpage.getselectNewFromDropdown().waitAndClick(10);

		redactionpage.getRedactionTagName().isElementAvailable(15);
		base.waitForElement(redactionpage.getRedactionTagName());
		redactionpage.getRedactionTagName().SendKeys(Redactiontag1);

		redactionpage.getSaveBtn().isElementAvailable(15);
		base.waitForElement(redactionpage.getSaveBtn());
		redactionpage.getSaveBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		System.out.println("First Redaction Tag is created" + Redactiontag1);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		DocExplorerPage docExp = new DocExplorerPage(driver);
		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		docExp.documentSelectionIteration();
		docExp.docExpViewInDocView();

		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		// doc1
		docViewRedactions.selectDoc1();

		driver.waitForPageToBeReady();
		docViewRedactions.redactRectangleUsingOffset(10, 10, 100, 100);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag1);

		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		DocExplorerPage docExplorer = new DocExplorerPage(driver);
		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		docExplorer.documentSelectionIteration();
		docExplorer.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTextSection();
		page.fillingTIFFSectionwithBurnRedactionSelectRedactTag(Redactiontag1);
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
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");

		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-48380
	 * @Description To verify that If user select RedactionTag and if non-audio
	 *              document is not associated to the selected Redaction Tag then
	 *              Native should produced
	 * 
	 */
	@Test(description = "RPMXCON-48380", enabled = true, groups = { "regression" })
	public void verifyNativeIsProducedAtGeneration() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48380 -Production Sprint 09");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		String Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		RedactionPage redactionpage = new RedactionPage(driver);
		

		redactionpage.manageRedactionTagsPage(Redactiontag1);
		System.out.println("First Redaction Tag is created" + Redactiontag1);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		DocExplorerPage docExp = new DocExplorerPage(driver);
		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		docExp.documentSelectionIteration();
		docExp.docExpViewInDocView();

		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		// doc1
		docViewRedactions.selectDoc1();

		driver.waitForPageToBeReady();
		docViewRedactions.redactRectangleUsingOffset(10, 10, 100, 100);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag1);

		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTextSection();
		page.fillingTIFFSectionwithBurnRedactionSelectRedactTag(Redactiontag1);
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
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		loginPage.logout();
	}

	/**
	 * @author Brundha Test case id-RPMXCON-48376
	 * @Description To verify that user select the PrivTag and if non-audio document
	 *              is not associated to that selected tag then Native should
	 *              produced.
	 * 
	 */
	@Test(description = "RPMXCON-48376", enabled = true, groups = { "regression" })
	public void VerifyPrivTagNotAssociatedNativeProduced() throws Exception {
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48376 -Production Sprint 09");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectPrivDocsInTiffSection(tagname);
		page.fillingTextSection();
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
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");

		loginPage.logout();
	}

	/**
	 * @author Brundha Test case id-RPMXCON-48378
	 * @Description To verify that if PRIV document is selected and TIFF or PDF
	 *              sections are not selected then Native should be generated
	 * 
	 */
	@Test(description = "RPMXCON-48378", enabled = true, groups = { "regression" })
	public void verifyPDFOrTIFFNotSelectedNativeGenerate() throws Exception {
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48378 -Production Sprint 09");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
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
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();
	}

	/**
	 * @author Brundha Test case id-RPMXCON-48375
	 * @Description To verify that If user select PrivTag and if non-audio document
	 *              is associated to that tag then Native should not produced
	 * 
	 */
	@Test(description = "RPMXCON-48375", enabled = true, groups = { "regression" })
	public void verifyNativeNotProduced() throws Exception {

		UtilityLog.info(Input.prodPath);

		base.stepInfo("RPMXCON-48375 -Production Sprint 09");
		base.stepInfo(
				"To verify that If user select PrivTag and if non-audio document is associated to that tag then Native should not produced");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String productionname1 = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectPrivDocsInTiffSection(tagname);
		page.fillingTextSection();
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

		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname1);
		String beginningBates1 = page.getRandomNumber(2);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSection(tagname, Input.searchString4);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates1);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname1);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);

		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-48534
	 * @Description To verify that if user click on Yes button on confirmation
	 *              message, Tiff/PDF should produced with blank pages
	 * 
	 */
	@Test(description = "RPMXCON-48534", enabled = true, groups = { "regression" })
	public void verifyTiffWithBlankPagesAfterGeneration() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-48534 -Production Sprint 09");
		base.stepInfo(
				"To verify that if user click on Yes button on confirmation message, Tiff/PDF should produced with blank pages");

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
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.selectBlankRemovalInTiffSection();
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
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		loginPage.logout();
	}

	/**
	 * @author Brundha Test case id-RPMXCON-48304
	 * @Description To verify the value of BeginningAttachBates should be displayed
	 *              Beginning bates of the parent of the family on Production
	 * 
	 */
	@Test(description = "RPMXCON-48304", enabled = true, groups = { "regression" })
	public void BeginningAttachBatesInDatAndGenerateProduction() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-48304 -Production Sprint 09");
		base.stepInfo(
				"To verify the value of BeginningAttachBates should be displayed Beginning bates of the parent of the family on Production");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String BatesNumber = "B" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.addDATFieldAtSecondRow("Bates", "BeginingAttachBates", BatesNumber);
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
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-48318
	 * @Description To verify that Production should be generated successfully if
	 *              there is one single non-redacted area.
	 */
	@Test(description = "RPMXCON-48318", enabled = true, groups = { "regression" })
	public void verifyProductionGeneratedwithNonRedactedArea() throws Exception {

		UtilityLog.info(Input.prodPath);

		base.stepInfo("RPMXCON-48318 -Production Sprint 09");
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo(
				"To verify that Production should be generated successfully if there is one single non-redacted area.");

		String redactiontag = "Redaction" + Utility.dynamicNameAppender();
		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		RedactionPage redactionpage = new RedactionPage(driver);
		driver.waitForPageToBeReady();

		redactionpage.manageRedactionTagsPage(redactiontag);
		System.out.println("First Redaction Tag is created" + redactiontag);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.audioSearch("morning", "North American English");
		sessionSearch.bulkFolderExisting(foldername);
		driver.waitForPageToBeReady();
		sessionSearch.ViewInDocViewWithoutPureHit();

		docViewPage = new DocViewPage(driver);
		docViewPage.navigateToDocViewPageURL();

		DocViewRedactions redact = new DocViewRedactions(driver);
		redact.deleteAllAppliedRedactions();
		redact.clickOnAddRedactionForAudioDocument();
		redact.addAudioRedaction(Input.startTime, Input.endTime, redactiontag);

		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.burnRedactionWithRedactionTagInTiffSection(redactiontag);
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
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		loginPage.logout();
	}

	/**
	 * @author Brundha Test case id-RPMXCON-48381
	 * @Description To verify that if Redacted document is selected and Tiff/PDF
	 *              sections are not selected then Native should be generated
	 * 
	 */
	@Test(description = "RPMXCON-48381", enabled = true, groups = { "regression" })
	public void verifyNativeProducedAtGeneration() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48381 -Production Sprint 09");
		base.stepInfo(
				"To verify that if Redacted document is selected and Tiff/PDF sections are not selected then Native should be generated");

		foldername = "RedactFolderProd" + Utility.dynamicNameAppender();
		String Redactiontag;
		Redactiontag = "FirstRedactionTag" + Utility.dynamicNameAppender();
		RedactionPage redactionpage = new RedactionPage(driver);
		driver.getWebDriver().get(Input.url+"Redaction/Redaction");
		redactionpage.selectDefaultSecurityGroup();
		driver.waitForPageToBeReady();

		redactionpage.getselectAllRedactionTag().isElementAvailable(15);
		base.waitForElement(redactionpage.getselectAllRedactionTag());
		redactionpage.getselectAllRedactionTag().waitAndClick(10);
		
		redactionpage.getselectActionToggle().isElementAvailable(15);
		base.waitForElement(redactionpage.getselectActionToggle());
		redactionpage.getselectActionToggle().waitAndClick(10);

		redactionpage.getselectNewFromDropdown().isElementAvailable(15);
		base.waitForElement(redactionpage.getselectNewFromDropdown());
		redactionpage.getselectNewFromDropdown().waitAndClick(10);

		redactionpage.getRedactionTagName().isElementAvailable(15);
		base.waitForElement(redactionpage.getRedactionTagName());
		redactionpage.getRedactionTagName().SendKeys(Redactiontag);

		redactionpage.getSaveBtn().isElementAvailable(15);
		base.waitForElement(redactionpage.getSaveBtn());
		redactionpage.getSaveBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		System.out.println("First Redaction Tag is created" + Redactiontag);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		DocExplorerPage docExp = new DocExplorerPage(driver);
		driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		docExp.documentSelectionIteration();
		docExp.docExpViewInDocView();

		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		// doc1
		// docViewRedactions.selectDoc1();
		DocViewPage doc = new DocViewPage(driver);
		// doc1
		doc.documentSelection(1);

		driver.waitForPageToBeReady();
		docViewRedactions.redactRectangleUsingOffset(10, 10, 100, 100);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag);

		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		// Pre-requisites
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		// Adding folder to bulkfolder
		DocExplorerPage docExplorer = new DocExplorerPage(driver);
		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		docExplorer.documentSelectionIteration();
		docExplorer.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
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

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		loginPage.logout();
	}

	/**
	 * @author Brundha Test case id-RPMXCON-49055
	 * @Description Verify Remove documents option is not getting displayed in
	 *              Production.
	 * 
	 */
	@Test(description = "RPMXCON-49055", enabled = true, groups = { "regression" })
	public void verifyRemoveDocumentOptionNotDisplay() throws Exception {
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49055-Production Sprint 09");
		base.stepInfo("Verify Remove documents option is not getting displayed in Production.");

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

		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.waitForPageToBeReady();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		base.stepInfo("Verifying the dropdown option in the completed production");
		page.verifyDropDownValueInCompletedProduction("Remove");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		loginPage.logout();
	}

	/**
	 * @author Brundha Test case id-RPMXCON-49053
	 * @Description Verify Add documents option is not getting displayed in
	 *              Production.
	 * 
	 */
	@Test(description = "RPMXCON-49053", enabled = true, groups = { "regression" })
	public void verifyAddDocumentOptionNotDisplay() throws Exception {
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49053-Production Sprint 09");
		base.stepInfo("Verify Add documents option is not getting displayed in Production.");

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

		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.waitForPageToBeReady();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

		base.stepInfo("Verifying the dropdown option in the completed production");
		page.verifyDropDownValueInCompletedProduction("Add");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-49058
	 * @Description In Productions, text was produced with redaction burned, when
	 *              Burn Redactions option was disabled-2
	 * 
	 */
	@Test(description = "RPMXCON-49058", enabled = true, groups = { "regression" })
	public void productionWithBurnedRedaction() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		base.stepInfo("RPMXCON-49058 -Production Sprint 09");
		base.stepInfo(
				"In Productions, text was produced with redaction burned, when Burn Redactions option was disabled-2");

		tagname = "Tag" + Utility.dynamicNameAppender();
		foldername = "RedactFolderProd" + Utility.dynamicNameAppender();
		String Redactiontag;
		Redactiontag = "FirstRedactionTag" + Utility.dynamicNameAppender();
		RedactionPage redactionpage = new RedactionPage(driver);
		driver.waitForPageToBeReady();

		redactionpage.manageRedactionTagsPage(Redactiontag);
		System.out.println("First Redaction Tag is created" + Redactiontag);
		driver.waitForPageToBeReady();

		DocExplorerPage docExp = new DocExplorerPage(driver);
		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		docExp.documentSelectionIteration();
		docExp.docExpViewInDocView();

		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		DocViewPage doc = new DocViewPage(driver);
		// doc1
		doc.documentSelection(1);

		driver.waitForPageToBeReady();
		docViewRedactions.redactRectangleUsingOffset(10, 10, 20, 20);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolderInRMU(foldername);
		tagsAndFolderPage.createNewTagwithClassificationInRMU(tagname, Input.tagNamePrev);

		// Adding folder to bulkfolder
		DocExplorerPage docExplorer = new DocExplorerPage(driver);
		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		docExplorer.documentSelectionIteration();
		docExplorer.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectPrivDocsInTiffSection(tagname);
		page.fillingTextSection();
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
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);

		loginPage.logout();
	}

	/**
	 * @author Brundha Test case id-RPMXCON-55944
	 * @Description Verify that REDACTED text should displayd by default if user
	 *              selects the Redaction Tag in TIFF
	 * 
	 */
	@Test(description = "RPMXCON-49102", enabled = true, groups = { "regression" })
	public void verifyPlaceholderInTIFFBurnRedaction() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-49102 -Production Sprint 10");
		base.stepInfo("Verify that REDACTED text should displayd by default if user selects the Redaction Tag in TIFF");

		String productionname = "p" + Utility.dynamicNameAppender();

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);

		page.selectGenerateOption(false);
		page.verifyPlaceholderTextInBurnRedaction(Input.defaultRedactionTag);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56132
	 * @Description:Verify that for the saved template controls in Specify DAT Field
	 *                     Mapping should be disabled
	 */

	@Test(description = "RPMXCON-56132", enabled = true, groups = { "regression" })
	public void verifySavedTemplateControlIsDisabled() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56132 Production-Sprint 01");
		tagname = "Tag" + Utility.dynamicNameAppender();
		// create production and fill dat field and verify specify controls
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		productionname = "p" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		// verify saved template controls in dat mapping
		page.viewSaveTemplateControlForDATMapping();
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56130
	 * @Description:Verify that on click of Mark Incomplete from the Component
	 *                     section, already selected Redaction tags should not
	 *                     available for Redaction text
	 */
	@Test(description = "RPMXCON-56130", enabled = true, groups = { "regression" })
	public void verifyClickMarkIncompleteDisablesALreadyRedactedTags() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56130 Production - Sprint 01");
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// create production and fill dat field and verify specify controls
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		productionname = "p" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionwithBurnRedaction(tagname);
		page.fillingTextSection();
		page.clickComponentMarkCompleteAndIncomplete();
		page.fillingTIFFWithBurningRedactionsAndPreviouslySelectedTagDisabled(tagname);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON_56163
	 * @Description:Verify if user selects document level numbering and Sub-Bates
	 *                     number is null and user select multipage Tiff or PDF then
	 *                     Production preview is displays without any error
	 * 
	 */
	@Test(description = "RPMXCON-56163", enabled = true, groups = { "regression" })
	public void passingSubBatesNullAndPreview() throws InterruptedException, AWTException {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-56163 -Production Sprint 02");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// viewing preview in summary tab after passing null value in sub bates
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFWithMultiPage(tagname);
		page.navigateToNextSection();
		page.fillingNumberingPageWithDocumentAndPassingNullSubBatesSuccess();
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.viewingPreviewInSummaryTab();
		base.passedStep("Verified sub bates Null and preview");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON_56164
	 * @Description:Verify if user selects document level numbering and Sub-Bates
	 *                     number is null and user select SinglePage Tiff or PDF
	 *                     then error message should be displays on 'Numbering and
	 *                     Sorting' tab
	 */
	@Test(description = "RPMXCON-56164", enabled = true, groups = { "regression" })
	public void passingSubBatesNullAndVerifyErrorMessage() throws InterruptedException, AWTException {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-56164 -Production Sprint 02");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// viewing preview in summary tab after passing null value in sub bates
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFWithSinglePage(tagname);
		page.navigateToNextSection();
		page.fillingNumberingPageWithDocumentAndPassingNullSubBatesError();
		base.passedStep("Verified Error message while passing sub bates Null value");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56149
	 * @Description:Verify that for Native section should be displayed message which
	 *                     will inform the user about the privileged and redacted
	 *                     docs from Production
	 */
	@Test(description = "RPMXCON-56149", enabled = true, groups = { "regression" })
	public void verifyTooltipDisplayedOnPreview() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-56149 -Production Sprint 02");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for the created folder and check the pure hit count
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production using dat,native and display tooltip display on preview
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		productionname = "p" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname, Input.tagNameTechnical);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.viewingToolTipInSummaryAndPreview();
		base.passedStep("verified Tooltip Displayed On Preview");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON_56120
	 * @Description:Verify that on Tiff/PDF section," Redaction without redaction
	 *                     tags" is pre-activated with the text "REDACTED" when user
	 *                     clicks on 'Specify Redaction Text by Selecting Redaction
	 *                     Tags:'
	 * 
	 */
	@Test(description = "RPMXCON-56120", enabled = true, groups = { "regression" })
	public void verifyTiffSectionRedactionTag() throws InterruptedException, AWTException {

		UtilityLog.info(Input.prodPath);

		base.stepInfo("Test case Id: RPMXCON-56120 -Production Sprint 02");
		// viewing preview in summary tab after passing null value in sub bates
		ProductionPage page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingTheTIFFSection();
		base.passedStep("Verified TIFF section");
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON_56099
	 * @Description:Verify the error message for NATIVE component when 'Select
	 *                     Native component without tag and file type
	 */

	//@Test(description = "RPMXCON-56099", enabled = true, groups = { "regression" })
	public void AssertionOnNativeSection() throws Exception {
		UtilityLog.info(Input.prodPath);

		base.stepInfo("Test case Id: RPMXCON-56099 -Production Sprint 02");
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSectionWithoutSelectingTag();
		base.passedStep("Error Message for NATIVE is Displayed");
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56005
	 * @Description: Verify that user can regenerate the Shareable links and reset
	 *               the expiration time
	 */
	@Test(description = "RPMXCON-56005", enabled = true, groups = { "regression" })
	public void verifySharableLinkExpirationTime() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56005 -Production Sprint 10");
		base.stepInfo("Verify that user can regenerate the Shareable links and reset the expiration time");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		TempName = "Templete" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		// tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		// sessionSearch.bulkTagExisting(tagname);
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
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		page.getQC_Download().waitAndClick(10);
		page.getSelectSharableLinks().waitAndClick(10);
		page.getRegenarateLinkBtn().waitAndClick(10);

		String ExpiryDate = page.getSharableLinkExpiryDate().getText().trim();
		System.out.println(ExpiryDate);
		base.stepInfo("Expiry date show in webPage : " + ExpiryDate);

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		String date1 = dateFormat.format(date);
		System.out.println("Current date " + date1);
		base.stepInfo("Current Date" + date1);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date2 = simpleDateFormat.parse(date1);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date2);
		calendar.add(Calendar.DATE, 14); // 14 == duration
		String TwoWeekAfter = simpleDateFormat.format(calendar.getTime()).trim();
		System.out.println("Day after 2 Week : " + TwoWeekAfter);
		base.stepInfo("Day after 2 Week: " + TwoWeekAfter);

		if (ExpiryDate.contains(TwoWeekAfter)) {
			base.passedStep("Expiry date reset verified");
			System.out.println("date visible");
		} else {
			base.failedStep("date verification failed");
			System.out.println("date not visible");
		}
		base.passedStep("Verified that user can regenerate the Shareable links and reset the expiration time");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		// tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security
		// Group");
		loginPage.logout();
	}

	/**
	 * @author Aathith Test case id-RPMXCON-55949
	 * @Description Verify that use cannot access the Production deatils by copying
	 *              the Production URL if user does not have Production rights
	 * 
	 */
	@Test(description = "RPMXCON-55949", enabled = true, groups = { "regression" })
	public void verifyingProductionAccessPAtoRMU() throws Exception {

		UtilityLog.info(Input.prodPath);

		base.stepInfo("RPMXCON-55949 -Production Sprint 10");
		base.stepInfo(
				"Verify that RMU cannot access the Production by copying the Production URL if user is not part of that security group");
		SecurityGroupsPage sg = new SecurityGroupsPage(driver);
		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		String securityGroup = "Production_Security_Group" + Utility.dynamicNameAppender();
		sg.createSecurityGroups(securityGroup);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingSecurityGroup(securityGroup);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		driver.waitForPageToBeReady();

		String currentURL = driver.getWebDriver().getCurrentUrl();
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("Logined as RMU");

		driver.Navigate().to(currentURL);
		driver.waitForPageToBeReady();
		String ErrorMsg = page.getErrorMsgText().getText();
		if (ErrorMsg.contains("Error")) {
			base.passedStep("Error message is displayed as expected");
		} else {
			base.failedStep("Error message is not  displayed as expected");
		}
		driver.Navigate().back();

		base.passedStep(
				"Verified that RMU cannot access the Production by copying the Production URL if user is not part of that security group");
		loginPage.logout();
	}

	/**
	 * @author Aathith Test case id-RPMXCON-55950
	 * @Description Verify that RMU cannot access the Production if he is not part
	 *              of that Project
	 * 
	 */
	@Test(description = "RPMXCON-55950", enabled = true, groups = { "regression" })
	public void verifyingProductionAccessPAtoRMUdiffProject() throws Exception {

		UtilityLog.info(Input.prodPath);

		base.stepInfo("RPMXCON-55950 -Production Sprint 10");
		base.stepInfo("Verify that RMU cannot access the Production if he is not part of that Project");
		String securityGroup = "Production_Security_Group" + Utility.dynamicNameAppender();

		SecurityGroupsPage sg = new SecurityGroupsPage(driver);
		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		sg.createSecurityGroups(securityGroup);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingSecurityGroup(securityGroup);
		driver.waitForPageToBeReady();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		driver.waitForPageToBeReady();

		String currentURL = driver.getWebDriver().getCurrentUrl();
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		BaseClass base = new BaseClass(driver);
		base.selectproject();
		base.stepInfo("Logined as RMU");

		driver.Navigate().to(currentURL);
		driver.waitForPageToBeReady();
		String ErrorMsg = page.getErrorMsgText().getText();
		if (ErrorMsg.contains("Error")) {
			base.passedStep("Error message is displayed as expected");
		} else {
			base.failedStep("Error message is not displayed as expected");
		}
		driver.Navigate().back();

		base.passedStep("Verified that RMU cannot access the Production if he is not part of that Project");
		loginPage.logout();

	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56033
	 * @Description: Verify that after Archiving is completed it should displays
	 *               'Creating Archive Complete' status on Production Progress bar
	 *               Tile View
	 */
	@Test(description = "RPMXCON-56033", enabled = true, groups = { "regression" })
	public void verifiyCreateArchiCompleteOnTileView() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56033 -Production Sprint 10");
		base.stepInfo(
				"Verify that after Archiving is completed it should displays 'Creating Archive Complete' status on Production Progress bar Tile View");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		TempName = "Templete" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.bulkFolderExisting(foldername);

		// Verify archive status on Gen page
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
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
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
		// verification
		page.verifyProductionStatusInHomePage("Creating Archive Complete", productionname);
		base.passedStep(
				"Verify that after Archiving is completed it should displays 'Creating Archive Complete' status on Production Progress bar Tile View");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Test case id-RPMXCON-55951
	 * @Description Verify that PAU cannot access the Production if he is not part
	 *              of that Project
	 * 
	 */
	@Test(description = "RPMXCON-55951", enabled = true, groups = { "regression" })
	public void verifyingProductionAccessPAtoPAdiffProject() throws Exception {

		UtilityLog.info(Input.prodPath);

		base.stepInfo("RPMXCON-55951 -Production Sprint 10");
		base.stepInfo("Verify that PAU cannot access the Production if he is not part of that Project");
		String securityGroup = "Production_Security_Group" + Utility.dynamicNameAppender();

		SecurityGroupsPage sg = new SecurityGroupsPage(driver);
		this.driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		sg.createSecurityGroups(securityGroup);
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingSecurityGroup(securityGroup);

		page.addANewProduction(productionname);
		page.fillingDATSection();
		driver.waitForPageToBeReady();

		String currentURL = driver.getWebDriver().getCurrentUrl();
		loginPage.logout();
		loginPage.loginToSightLine(Input.pa2userName, Input.pa2password);

		BaseClass base = new BaseClass(driver);
		base.selectproject();
		base.stepInfo("Logined as another PA");

		driver.Navigate().to(currentURL);
		driver.waitForPageToBeReady();
		String ErrorMsg = page.getErrorMsgText().getText();
		if (ErrorMsg.contains("Error")) {
			base.passedStep("Error message is displayed as expected");
		} else {
			base.failedStep("Error message is not  displayed as expected");
		}
		driver.Navigate().back();

		base.passedStep("Verify that PAU cannot access the Production if he is not part of that Project");
		loginPage.logout();

	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-55994
	 * @Description: Verify that the text should not go out the progress bar or
	 *               wrap, even when the user zooms out/in the browser with
	 *               different screen resolution on tile view
	 */
	@Test(description = "RPMXCON-55994", enabled = true, groups = { "regression" })
	public void verifyStatusBarTextWithResolutionAndZoomSize() throws Exception {

		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		base.stepInfo("RPMXCON-55994 -Production Sprint 10");
		base.stepInfo(
				"Verify that the text should not go out the progress bar or wrap, even when the user zooms out/in the browser with different screen resolution  on tile view");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		int[][] diemen = { { 1024, 768 }, { 1280, 800 }, { 1440, 900 }, { 1600, 900 }, { 1280, 1024 } };
		double[][] zoom = { { 0.75, 1.25 }, { 0.75, 1 }, { 0.80, 1 }, { 0.90, 1.25 }, { 0.80, 1.25 } };

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		// tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		// sessionSearch.bulkTagExisting(tagname);
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

		for (int i = 0; i < diemen.length; i++) {

			Dimension pram1 = new Dimension(diemen[i][0], diemen[i][1]);
			driver.Manage().window().setSize(pram1);
			((JavascriptExecutor) driver.getWebDriver())
					.executeScript("document.body.style.zoom = '" + zoom[i][0] + "'");

			driver.Navigate().refresh();
			if (page.getProductionFromHomePage(productionname).isDisplayed()) {
				base.passedStep("Status bar text is displayed in : " + diemen[i][0] + "x" + diemen[i][1]
						+ " with zoom size : " + zoom[i][0]);
				System.out.println("displayed");
			} else {
				base.failedStep("Status bar text is not displayed");
				System.out.println("Not displayed");
			}
			((JavascriptExecutor) driver.getWebDriver())
					.executeScript("document.body.style.zoom = '" + zoom[i][1] + "'");
			if (page.getProductionFromHomePage(productionname).isDisplayed()) {
				base.passedStep("Status bar text is displayed in : " + diemen[i][0] + "x" + diemen[i][1]
						+ " with zoom size : " + zoom[i][1]);
				System.out.println("displayed");
			} else {
				base.failedStep("Status bar text is not displayed");
				System.out.println("Not displayed");
			}
		}

		base.passedStep(
				"Verified that the text should not go out the progress bar or wrap, even when the user zooms out/in the browser with different screen resolution  on tile view");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		// tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security
		// Group");
		loginPage.logout();
	}

	/**
	 * @author Aathith.Senthilkumar RPMXCONO-55909
	 * @Description Verify that the production field PageCount is renamed to
	 *              TIFFPageCount
	 */
	@Test(description = "RPMXCON-55909", enabled = true, groups = { "regression" })
	public void verifyProductionFieldRenamed() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Testcase No: RPMXCON-55909");
		base.stepInfo("Verify that the production field PageCount is renamed to TIFFPageCount");

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSectionWithBates("Production", "TIFFPageCount", "TIFFPAGECOUNT");
		page.nonVisibleCheck("PageCount");
		page.visibleCheck("TIFFPageCount");
		base.stepInfo("PageCount was be renamed to TIFFPageCount");

		base.passedStep("Verified that the production field PageCount is renamed to TIFFPageCount");
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49967
	 * @Description: Verify that production should generated with modified Redaction
	 *               placeholder text
	 */
	@Test(description = "RPMXCON-49967", enabled = true, groups = { "regression" })
	public void verifyReductionPlacholderText() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49967 -Production Sprint 10");
		base.stepInfo("Verify that production should generated with modified Redaction placeholder text");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		// Verify
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.visibleCheck("Productions & Exports");
		base.stepInfo("production home page is Displayed");
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionwithBurnRedactionWithoutUpdatedText();
		base.waitForElement(page.gettextRedactionPlaceHolder());
		String text = page.gettextRedactionPlaceHolder().getText();
		if (text.equalsIgnoreCase("REDACTED")) {
			base.passedStep("By default 'REDACTED' text should be displayed");
		}
		page.gettextRedactionPlaceHolder().waitAndClick(10);
		page.gettextRedactionPlaceHolder().SendKeys(Input.searchString4);
		base.stepInfo("Reduction text is updated");
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
		base.passedStep("Verified that production should generated with modified Redaction placeholder text");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49815
	 * @Description: To verify that Production should generate successfully if
	 *               Prefix is up to 50 characters
	 */
	@Test(description = "RPMXCON-49815", enabled = true, groups = { "regression" })
	public void verifyPrifixWith50char() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49815 -Production Sprint 10");
		base.stepInfo("To verify that Production should generate successfully if Prefix is up to 50 characters");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		// tagname = "Tag" + Utility.dynamicNameAppender();
		prefixID = Utility.randomCharacterAppender(50);
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		// tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		// sessionSearch.bulkTagExisting(tagname);

		// Verify archive status on Grid view
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
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
		page.fillingGeneratePageAndVerfyingBatesRange(prefixID);
		base.passedStep("Verified that Production should generate successfully if Prefix is up to 50 characters");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		// tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security
		// Group");
		loginPage.logout();
	}

	/**
	 * @author Aathith.Senthilkumar RPMXCONO-47174
	 * @Description Verify that TIFF files should be copied to folder when 'Split
	 *              Sub Folders' is OFF with split count as 1000
	 */
	@Test(description = "RPMXCON-47174", enabled = true, groups = { "regression" })
	public void genaratetDocumentswithMultipleBrandingTagsnotsplit() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47174 -Production Sprint 10");
		base.stepInfo(
				"Verify that TIFF files should be copied to folder when 'Split Sub Folders' is OFF with split count as 1000");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.bulkFolderExisting(foldername);

		// Verify archive status on Gen page
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
		page.visibleCheck("Numbering and Sorting");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
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
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.visibleCheck("Summary & Preview");
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.getBackButton().waitAndClick(10);
		page.getBackButton().waitAndClick(10);
		page.getBackButton().waitAndClick(10);
		driver.scrollingToBottomofAPage();
		page.toggleOffCheck(page.getsplitSubFolderbtn());

		base.passedStep(
				"Verified that TIFF files should be copied to folder when 'Split Sub Folders' is OFF with split count as 1000");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith.Senthilkumar RPMXCONO-55653
	 * @Description To Verify the availability of 'Translations' under the Advanced
	 *              Production Types show/hide section (in Production Component).
	 */
	@Test(description = "RPMXCON-55653", enabled = true, groups = { "regression" })
	public void verifyAvailabilityOfTranslationComponent() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Testcase No: RPMXCON-55653");
		base.stepInfo(
				"To  Verify the availability of 'Translations' under the Advanced Production Types show/hide section (in Production Component).");

		// foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		// tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		// sessionSearch.bulkFolderExisting(foldername);

		// Verify
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname);
		driver.scrollingToBottomofAPage();
		page.getAdvancedProductionToggle().waitAndClick(10);
		page.getCheckBoxUnCheckVerificaation(page.getTranlationCheckMarkVerication());
		base.stepInfo("By default Translation check box is unchecked");
		boolean flag = page.getTranlationOpenCloseCheck().GetAttribute("class").contains("in");
		if (!flag) {
			base.passedStep("user didn't view Translation details ");
		} else {
			base.failedStep("Tranlation tab is open");
		}
		page.getTranslationsCheckBox().waitAndClick(10);
		base.stepInfo("Translation tab is clicked");
		driver.waitForPageToBeReady();

		boolean flag1 = page.getTranlationOpenCloseCheck().GetAttribute("class").contains("in");
		if (flag1) {
			base.passedStep("Translation component details is displayed");
		} else {
			base.failedStep("Tranlation tab is not open");
		}

		base.passedStep(
				"Verified the availability of 'Translations' under the Advanced Production Types show/hide section (in Production Component).");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		// tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security
		// Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47884
	 * @Description: To Verify Native Section with various options(Produce Native
	 *               Files selection/Generate Load LST/Advance Show Hide/and Toggles
	 *               in Advance)
	 */
	@Test(description = "RPMXCON-47884", enabled = true, groups = { "regression" })
	public void verifyNativeSectionElements() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47884 -Production Sprint 11");
		base.stepInfo(
				"To Verify Native Section with various options(Produce Native Files selection/Generate Load LST/Advance Show Hide/and Toggles in Advance)");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		// tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		// sessionSearch.bulkTagExisting(tagname);

		// Verify archive status on Grid view
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSectionWithVerification();
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
		base.passedStep(
				"To Verify Native Section with various options(Produce Native Files selection/Generate Load LST/Advance Show Hide/and Toggles in Advance)");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		// tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security
		// Group");
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47994
	 * @Description: To Verify On grid view of the productions the start date and
	 *               end date for a production that is still in Completed state.
	 */
	@Test(description = "RPMXCON-47944", enabled = true, groups = { "regression" })
	public void startDateEndDateVarification() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47944 -Production Sprint 11");
		base.stepInfo(
				"To Verify On grid view of the productions  the start date and end date  for a production that is still in Completed state.");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		// tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		// sessionSearch.bulkTagExisting(tagname);

		// Verify archive status on Grid view
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigatingToProductionHomePage();
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

		driver.waitForPageToBeReady();
		page.goToProductionGridView();
		int startDateIndex = base.getIndex(page.getGridWebTableHeader(), "START DATE");
		int endDateIndex = base.getIndex(page.getGridWebTableHeader(), "END DATE");
		String startDate = page.getGridProdValues(productionname, startDateIndex).getText();
		String EndDate = page.getGridProdValues(productionname, endDateIndex).getText();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		String date1 = dateFormat.format(date);
		System.out.println("Current date " + date1);
		boolean flag = startDate.contains(date1);
		boolean flag1 = EndDate.contains(date1);
		if (flag) {
			base.passedStep("Start date is displayed on production grid view");
			System.out.println("date visible");
		} else {
			base.failedStep("date is not contain in text");
			System.out.println("date not visible");
		}
		if (flag1) {
			base.passedStep("End date is displayed on production grid view");
			System.out.println("date visible");
		} else {
			base.failedStep("date is not contain in text");
			System.out.println("date not visible");
		}

		base.passedStep(
				"To Verify On grid view of the productions  the start date and end date  for a production that is still in Completed state.");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		// tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security
		// Group");
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56078
	 * @Description: Verify that Production should export Native with the Text file
	 *               in selected format if Text is not ingested
	 */
	@Test(description = "RPMXCON-56078", enabled = true, groups = { "regression" })
	public void verifyTextFileWithSelectedFormat() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56078 -Production Sprint 11");
		base.stepInfo(
				"Verify that Production should export Native with  the Text file in selected format if Text is not ingested");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		// tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		// sessionSearch.bulkTagExisting(tagname);

		// Verify archive status on Grid view
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTextSectionWithTextFormat("ANSI Arabic; Arabic (Windows)");
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);
		base.passedStep(
				"Verified that Production should export Native with  the Text file in selected format if Text is not ingested");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		// tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security
		// Group");
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47889
	 * @Description: To Verify Generate Section for Production Name and Status.
	 */
	@Test(description = "RPMXCON-47889", enabled = true, groups = { "regression" })
	public void verifyProdNameAndSatatus() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47889 -Production Sprint 11");
		base.stepInfo("To Verify Generate Section for Production Name and Status.");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		// Verify archive status on Grid view
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname);
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
		String prodName = page.getProductionNameInGenPage().getText().trim();
		if (prodName.equals(productionname)) {
			base.passedStep("Production name is displayed on genaration tab");
			System.out.println("name displayed");
		} else {
			base.failedStep("Production name is not displayed on genaration tab");
			System.out.println("name not displayed");
		}
		page.verifyProductionStatusInGenPage("Draft");
		base.stepInfo("Status is in Draft by default");
		base.passedStep("Verified Generate Section for Production Name and Status.");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49968
	 * @Description: Verify that Production should generated with redaction text if
	 *               user selects the annotation layer
	 */
	@Test(description = "RPMXCON-49968", enabled = true, groups = { "regression" })
	public void verifyProductionRedactionTextWithAnnotation() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49968 -Production Sprint 11");
		base.stepInfo(
				"Verify that Production should generated with redaction text if user selects the annotation layer");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		// tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		// sessionSearch.bulkTagExisting(tagname);

		// Verify archive status on Grid view
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSectionwithBurnRedaction();
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
		base.passedStep(
				"Verify that Production should generated with redaction text if user selects the annotation layer");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		// tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security
		// Group");
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49728
	 * @Description: Verify that branding is applied on all pages for redacted image
	 *               based documents
	 */
	@Test(description = "RPMXCON-49728", enabled = true, groups = { "regression" })
	public void verifyBrandingRedactedImage() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49728 -Production Sprint 11");
		base.stepInfo("Verify that branding is applied on all pages for redacted image based documents");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		// Verify archive status on Grid view
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.getTiffPdfBranding(tagname, null);
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

		page = new ProductionPage(driver);
		beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.getTiffPdfBranding(tagname, "pdf");
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
		base.passedStep("Verify that branding is applied on all pages for redacted image based documents");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith.Senthilkumar RPMXCON-49057
	 * @Description In Productions, text was produced with redaction burned, when
	 *              Burn Redactions option was enabled
	 * 
	 */
	@Test(description = "RPMXCON-49057", enabled = true, groups = { "regression" })
	public void verifyBurnRedactionIsEnabled() throws Exception {
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49057 -Production Sprint 11");
		base.stepInfo(
				"In Productions, text was produced with redaction burned, when Burn Redactions option was enabled");

		foldername = "RedactFolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String Redactiontag1;
		Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		RedactionPage redactionpage = new RedactionPage(driver);

		driver.waitForPageToBeReady();
		redactionpage.manageRedactionTagsPage(Redactiontag1);
		System.out.println("First Redaction Tag is created" + Redactiontag1);

		DocExplorerPage docExp = new DocExplorerPage(driver);
		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		docExp.documentSelectionIteration();
		docExp.docExpViewInDocView();

		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		// doc1
		docViewRedactions.selectDoc1();
		docViewRedactions.selectDoc2();
		docViewRedactions.selectDoc3();

		driver.waitForPageToBeReady();
		docViewRedactions.redactRectangleUsingOffset(10, 10, 100, 100);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag1);

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolderInRMU(foldername);

		// Adding folder to bulkfolder
		DocExplorerPage docExplorer = new DocExplorerPage(driver);
		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		docExplorer.documentSelectionIteration();
		docExplorer.bulkFolderExisting(foldername);

		// Verify
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectGenerateOption(false);
		page.getClk_burnReductiontoggle().waitAndClick(10);
		page.burnRedactionWithRedactionTag(Redactiontag1);
		page.fillingTextSection();
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
		base.passedStep(
				"In Productions, text was produced with redaction burned, when Burn Redactions option was enabled");

		// delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48816
	 * @Description: Verify that redaction text displays on generated Tiff/PDF for
	 *               orphan redactions documents, if user selects the option as 'All
	 *               redactions in annotation layer' for Burn Redactions
	 */
	@Test(description = "RPMXCON-48816", enabled = true, groups = { "regression" })
	public void verifyProductionRedactionTextWithAnnotationLayer() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48816 -Production Sprint 11");
		base.stepInfo(
				"Verify that redaction text displays on generated Tiff/PDF for orphan redactions documents, if user selects the option as 'All redactions in annotation layer' for Burn Redactions");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		// tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		// tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.orphanDocument);
		sessionSearch.bulkFolderExisting(foldername);
		// sessionSearch.bulkTagExisting(tagname);

		// Verify archive status on Grid view
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionwithBurnRedaction();
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
		base.passedStep(
				"Verified that redaction text displays on generated Tiff/PDF for orphan redactions documents, if user selects the option as 'All redactions in annotation layer' for Burn Redactions");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		// tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security
		// Group");
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47987
	 * @Description: To Verify The format of the date produced in the Production DAT
	 *               should honor the date format configured in DAT section
	 */
	@Test(description = "RPMXCON-47987", enabled = true, groups = { "regression" })
	public void verifyDatWithDeffDateFormate() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47987 -Production Sprint 11");
		base.stepInfo(
				"To Verify The format of the date produced in the Production DAT should honor the date format configured in DAT section");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		// Verify archive status on Grid view
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.getDatDateFormate().selectFromDropdown().selectByVisibleText("DD/MM/YYYY");
		base.stepInfo("DAT section selected with different date format");
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);

		base.passedStep(
				"Verified The format of the date produced in the Production DAT should honor the date format configured in DAT section");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47846
	 * @Description: To Verify the View of the already created Template with
	 *               existing Project and Production Set
	 */
	@Test(description = "RPMXCON-47846", enabled = true, groups = { "regression" })
	public void verifyTemplateforexitingProductionSet() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47846 Production- Sprint 11");
		base.stepInfo("To Verify the View of the already created Template with existing Project and Production Set");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		TempName = "templateName" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		// sessionSearch.bulkTagExisting(tagname);

		// Verify archive status on Grid view
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);

		page = new ProductionPage(driver);
		driver.getWebDriver().get(Input.url + "Production/Home");
		driver.waitForPageToBeReady();
		page.getprod_ActionButton_Reusable(productionname).Click();
		driver.waitForPageToBeReady();
		page.getprod_Action_SaveTemplate_Reusable(productionname).Click();

		page.saveTemple(TempName);
		page.getManageTemplates().waitAndClick(10);
		try {
		base.CloseSuccessMsgpopup();}catch(Exception e) {}
		driver.scrollingToBottomofAPage();
		page.getNextBtn().waitAndClick(10);
		driver.scrollingToBottomofAPage();
		page.getElementDisplayCheck(page.getViewBtn(TempName));
		page.getElementDisplayCheck(page.getDeleteBtn(TempName));
		page.getViewBtn(TempName).waitAndClick(10);

		page.visibleCheck(TempName);
		page.visibleCheck("Priv Guard");
		page.visibleCheck("Production Components");
		page.visibleCheck("Numbering & Sorting");
		page.clickElementNthtime(page.getviewProductionNextbtn(), 2);
		base.stepInfo("Next button working proberly");
		page.clickElementNthtime(page.getviewProductionBackbtn(), 2);
		base.stepInfo("Back button working proberly");
		page.getviewProductionNextbtn().waitAndClick(10);
		page.getCheckBoxCheckedVerification(page.chkIsDATSelected());
		base.stepInfo("Dat is selected by default");
		page.getElementDisplayCheck(page.closeButtonInTemplate());
		base.stepInfo("close button is displayed");
		driver.waitForPageToBeReady();
		page.templateCloseBtn(TempName).waitAndClick(10);
		base.passedStep("To Verify the View of the already created Template with existing Project and Production Set");

		base.stepInfo("Deleting the tags and folders after the production gets completed");
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48061
	 * @Description: To Verify selection of one or more tags for placeholdering with
	 *               File type a set of documents (For Production)
	 */
	@Test(description = "RPMXCON-48061", enabled = true, groups = { "regression" })
	public void verifyNativelyPlaceHolderWithFileType() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48061 -Production Sprint 11");
		base.stepInfo(
				"To Verify selection of one or more tags for placeholdering with File type a set of documents (For Production)");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String tagname1 = "Tag1" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.createNewTagwithClassification(tagname1, Input.tagNamePrev);
		base.stepInfo("Tags are created with file type");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		driver.waitForPageToBeReady();
		sessionSearch.ViewInDocListWithOutPureHit();

		DocListPage doc = new DocListPage(driver);
		doc.documentSelection(3);
		driver.waitForPageToBeReady();
		doc.bulkTagExistingFromDoclist(tagname);
		doc.documentSelection(3);
		doc.documentSelection(6);
		driver.scrollPageToTop();
		doc.bulkTagExistingFromDoclist(tagname1);

		// Verify archive status on Grid view
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionwithNativelyPlaceholder(tagname, tagname1);
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);
		driver.waitForPageToBeReady();
		String name = page.getProduction().getText().trim();
		System.out.println(name);
		page.isFileDownloaded(Input.fileDownloadLocation, name);

		base.passedStep(
				"Verified selection of one or more tags for placeholdering with File type a set of documents (For Production)");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48062
	 * @Description: To Verify selection of one or more tags without selecting any
	 *               file types for placeholdering a set of documents (For
	 *               Production)
	 */
	@Test(description = "RPMXCON-48062", enabled = true, groups = { "regression" })
	public void verifyNativelyPlaceHolderWithoutFileType() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48062 -Production Sprint 11");
		base.stepInfo(
				"To Verify selection of one or more tags without selecting any file types for placeholdering a set of documents (For Production)");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String tagname1 = "Tag1" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.CreateTag(tagname, Input.securityGroup);
		tagsAndFolderPage.CreateTag(tagname1, Input.securityGroup);
		base.stepInfo("Tags are created without file type");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		driver.waitForPageToBeReady();
		sessionSearch.ViewInDocListWithOutPureHit();

		DocListPage doc = new DocListPage(driver);
		doc.documentSelection(3);
		driver.waitForPageToBeReady();
		doc.bulkTagExistingFromDoclist(tagname);
		doc.documentSelection(3);
		doc.documentSelection(6);
		driver.scrollPageToTop();
		doc.bulkTagExistingFromDoclist(tagname1);

		// Verify archive status on Grid view
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionwithNativelyPlaceholder(tagname, tagname1);
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);
		driver.waitForPageToBeReady();
		String name = page.getProduction().getText().trim();
		System.out.println(name);
		page.isFileDownloaded(Input.fileDownloadLocation, name);

		base.passedStep(
				"Verified selection of one or more tags without selecting any file types for placeholdering a set of documents (For Production)");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48065
	 * @Description: To Verify document matches multiple criteria (file type or
	 *               tags), the precedence will be from top to bottom.(For
	 *               Production)
	 */
	@Test(description = "RPMXCON-48065", enabled = true, groups = { "regression" })
	public void verifyNativelyPlaceHolderWithFileTypeAndTag() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48065 -Production Sprint 11");
		base.stepInfo(
				"To Verify document matches multiple criteria (file type or tags), the precedence will be from top to bottom.(For Production)");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String tagname1 = "Tag1" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.createNewTagwithClassification(tagname1, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		driver.waitForPageToBeReady();
		sessionSearch.ViewInDocListWithOutPureHit();

		DocListPage doc = new DocListPage(driver);
		doc.documentSelection(3);
		driver.waitForPageToBeReady();
		doc.bulkTagExistingFromDoclist(tagname);
		doc.documentSelection(6);
		driver.scrollPageToTop();
		doc.bulkTagExistingFromDoclist(tagname1);

		// Verify archive status on Grid view
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionwithNativelyPlaceholderWithTagTypeAndTags(tagname, tagname1);
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);
		driver.waitForPageToBeReady();
		String name = page.getProduction().getText().trim();
		System.out.println(name);
		page.isFileDownloaded(Input.fileDownloadLocation, name);

		base.passedStep(
				"Verified document matches multiple criteria (file type or tags), the precedence will be from top to bottom.(For Production)");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47887
	 * @Description: To Verify Priv Guard with various tag and redaction options;
	 */
	@Test(description = "RPMXCON-47887", enabled = true, groups = { "regression" })
	public void verifyPrivGuardVariousTag() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47887 -Production Sprint 11");
		base.stepInfo("To Verify Priv Guard  with various tag and redaction options");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String tagname1 = "Tag1" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.createNewTagwithClassification(tagname1, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		int count = sessionSearch.basicContentSearch(Input.testData1);
		System.out.println(count);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		// Verify
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTiffSectionDisablePrivilegedDocs();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.AddRuleAndRemoveRule(tagname);
		driver.waitForPageToBeReady();
		String docNo = page.getDocumentSelectionLink().getText().trim();
		int displayCount = Integer.parseInt(docNo);
		System.out.println(displayCount);
		SoftAssert softAssertion = new SoftAssert();
		if (count == displayCount) {
			softAssertion.assertEquals(count, displayCount);
			base.passedStep("Document count is get displayed Correctly");
			System.out.println("count is verified");
		} else {
			base.failedStep("Document Count verification failed");
			System.out.println("count not verified");
		}

		page = new ProductionPage(driver);
		beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSectionDisablePrivilegedDocs();
		page.getTiffSinglePage().ScrollTo();
		page.getTiffSinglePage().waitAndClick(10);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.AddRuleAndRemoveRule(tagname);
		String docNo1 = page.getDocumentSelectionLink().getText().trim();
		driver.waitForPageToBeReady();
		int displayCount1 = Integer.parseInt(docNo1);
		if (count == displayCount) {
			softAssertion.assertEquals(count, displayCount1);
			base.passedStep("Document count is get displayed Correctly");
		} else {
			base.failedStep("Document Count verification failed");
		}
		base.passedStep("Verified Priv Guard  with various tag and redaction options;");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);

	}

	/**
	 * @author Aathith Test case id-RPMXCON-48288
	 * @Description To Verify Production for a document with no error message as
	 *              'WidthAndHeightCannotBeNegative'
	 */
	@Test(description = "RPMXCON-48288", enabled = true, groups = { "regression" })
	public void verifyNotDisplayOfErrorMessage() throws Exception {

		base.stepInfo("RPMXCON-48288-Production Sprint 11");
		base.stepInfo("To Verify Production for a document with no error message as 'WidthAndHeightCannotBeNegative'");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.SearchMetaData("SourceDocID", "STC4_00000995");
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSection(tagname, Input.tagNamePrev);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickOnGenerateButton();

		SoftAssert softAssertion = new SoftAssert();
		boolean flag = page.gettext("WidthAndHeightCannotBeNegative").isElementAvailable(60);
		if (flag) {
			softAssertion.assertTrue(flag);
			base.failedStep("Error message displayed");
		} else {
			softAssertion.assertFalse(flag);
			base.passedStep("There Should not be any error message  such as WidthAndHeightCannotBeNegative");
		}
		base.passedStep("Verified Production for a document with no error message as 'WidthAndHeightCannotBeNegative'");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48095
	 * @Description: To verify that Rotate 90 degrees clockwise page is rotated
	 *               before the branding is applied
	 */
	@Test(description = "RPMXCON-48095", enabled = true, groups = { "regression" })
	public void verify90DegreeRotationbeforeBrandingApply() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48095 -Production Sprint 11");
		base.stepInfo("To verify that Rotate 90 degrees clockwise page is rotated before the branding is applied");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearchForTwoItems("921ID00000169", "921ID00000171");
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		// Verify archive status on Grid view
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionRotate90DegreeClockWise(tagname);
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);
		base.passedStep("verified that Rotate 90 degrees clockwise page is rotated before the branding is applied");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		base.stepInfo("deleting created tags and folders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-46905
	 * @Description: Verify that production should be generated successfully for
	 *               audio files
	 */
	@Test(description = "RPMXCON-46905", enabled = true, groups = { "regression" })
	public void verifyAudiofileGenaratedSuccessfully() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-46905 -Production Sprint 11");
		base.stepInfo("Verify that production should be generated successfully for audio files");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		// tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		// tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.SearchMetaData("AudioPlayerReady", "1");
		sessionSearch.bulkFolderExisting(foldername);
		// sessionSearch.bulkTagExisting(tagname);

		// Verify archive status on Grid view
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.SelectMP3FileAndVerifyLstFile();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupHigerWaitTime();
		base.passedStep("Verified that production should be generated successfully for audio files");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		base.stepInfo("deleting created tags and folders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		// tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security
		// Group");
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-55627
	 * @Description: To Verify ordering sequence for production status for 'Filter
	 *               By:' field. Verify for both Grid and Tile view.
	 */
	@Test(description = "RPMXCON-55627", enabled = true, groups = { "regression" })
	public void verifyProductionState() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test Cases Id : RPMXCON-55627");
		base.stepInfo(
				"To Verify  ordering sequence for production status for 'Filter By:' field. Verify for both Grid and Tile view.");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		// completed state
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
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
		page.fillingGeneratePageWithContinueGenerationPopup();

		// failed
		page = new ProductionPage(driver);
		// beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		// page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickGenarateWaitForRegenarate();

		// Draft state
		page = new ProductionPage(driver);
		beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();

		// Inprogress state
		page = new ProductionPage(driver);
		beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
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
		page.clickOnGenerateButton();

		// view all state
		page = new ProductionPage(driver);
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		base.waitForElement(page.getFilterByButton());
		page.getFilterByButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		page.getElementDisplayCheck(page.getFilterByDRAFT());
		page.getElementDisplayCheck(page.getFilterByINPROGRESS());
		page.getElementDisplayCheck(page.getFilterByFAILED());
		page.getElementDisplayCheck(page.getFilterByCOMPLETED());
		base.stepInfo("All option is available in the list");
		base.waitForElement(page.getFilterByCOMPLETED());
		page.getFilterByCOMPLETED().waitAndClick(10);
		driver.waitForPageToBeReady();
		page.getRefreshButton().waitAndClick(10);
		base.stepInfo("Filtered all options");

		// different state
		page.visibleCheck("DRAFT");
		page.visibleCheck("FAILED");
		page.visibleCheck("INPROGRESS");
		page.visibleCheck("COMPLETED");

		// view completed status only
		page.filterCompletedProduction();
		page.verifyProdctionState(page.getProductionSate(), "COMPLETED");

		page.getGridView().waitAndClick(10);
		driver.waitForPageToBeReady();

		int status = base.getIndex(page.getGridWebTableHeader(), "STATUS");
		driver.waitForPageToBeReady();
		page.verifyProductionStateWebTableGridView(status, "COMPLETED");

		base.passedStep(
				"Verified  ordering sequence for production status for 'Filter By:' field. Verify for both Grid and Tile view.");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		base.stepInfo("deleting created tags and folders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-55650
	 * @Description: To Verify Admin will be able to select the metadata and work
	 *               production information to be included in a slip sheet, similar
	 *               to existing RPM
	 */
	@Test(description = "RPMXCON-55650", enabled = true, groups = { "regression" })
	public void verifySlipSheetflow() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test Cases Id : RPMXCON-55650");
		base.stepInfo(
				"To Verify Admin will be able to select the metadata and work production information to be included in a slip sheet, similar to existing RPM");

		softAssertion = new SoftAssert();
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();

		page.getTIFFTab().Click();
		 driver.scrollingToBottomofAPage();
		page.getAdvanceBtnOpenCloseCheck().ScrollTo();
		//page.getTiffAdvanceBtn().waitAndClick(10);
		page.getAdvanceBtnOpenCloseCheck().waitAndClick(10);
		driver.waitForPageToBeReady();
	   
		page.toggleOffCheck(page.getSlipSheets());
		page.getSlipSheets().waitAndClick(10);
		page.toggleOnCheck(page.getSlipSheets());
		base.stepInfo("Slip Sheet Section Toggle Button is work Accordingly");

		page.visibleCheck("METADATA");
		page.visibleCheck("WORKPRODUCT");
		page.visibleCheck("CALCULATED");
		base.stepInfo(" Fields is Contain Following Section 'METADATA/WORKPRODUCT & CALCULATED'");

		// metadata verification
		base.waitForElement(page.getSlipSheetMetaData());
		//page.getSlipSheetMetaData().waitAndClick(10);
		boolean flag = page.getSlipSheetMetaDataActiveCheck().GetAttribute("class").contains("active");
		if (flag) {
			softAssertion.assertTrue(flag);
			softAssertion.assertAll();
			base.passedStep("Metadta tab is open");
		} else {
			base.failedStep("verification failed");
		}

		boolean flag1 = page.getSlipSheetMetaDataTypeCheck().GetAttribute("class").contains("checkbox");
		if (flag1) {
			softAssertion.assertTrue(flag1);
			softAssertion.assertAll();
			base.passedStep("Metadata field is check box");
		} else {
			base.failedStep("verification failed");
		}
		base.stepInfo("Clicked on METADATA TAB it is Display the list of Metadata Fields with Check Box.");

		// workproduct verification
		page.getSlipSheetWorkProduct().waitAndClick(10);
		boolean flag3 = page.getSlipSheetWorkProductActiveCheck().GetAttribute("class").contains("active");
		if (flag3) {
			softAssertion.assertTrue(flag3);
			softAssertion.assertAll();
			base.passedStep("WorkProduct tab is open");
		} else {
			base.failedStep("verification failed");
		}
		driver.waitForPageToBeReady();
		// driver.scrollingToBottomofAPage();
		boolean flag4 = page.getSlipSheetWorkProductFolder().GetAttribute("class").contains("tree");
		if (flag4) {
			softAssertion.assertTrue(flag4);
			softAssertion.assertAll();
			base.passedStep("work product field type is tree");
		} else {
			base.failedStep("verification failed");
		}
		base.stepInfo("Clicked on WORKPRODUCTION TAB it is Display WorkProduct List with Tress Structure.");

		// add to select btn verification
		page.getSlipSheetWorkProductFolderProduction().ScrollTo();
		page.getSlipSheetWorkProductFolderProduction().waitAndClick(10);
		base.waitForElement(page.getbtnAddToSelect());
		page.getbtnAddToSelect().waitAndClick(10);
		page.getElementDisplayCheck(page.getSelectedFieldItems());
		base.stepInfo(
				"Add to Selected Button should work as expected.(Selected Value should Get populated in Selected Fields");

		// adv btn open close verification
		boolean flag5 = page.getAdvanceBtnOpenCloseCheck().GetAttribute("class").contains("down");
		if (flag5) {
			softAssertion.assertTrue(flag5);
			softAssertion.assertAll();
			base.passedStep("Advanced button is open");
		} else {
			base.failedStep("verification failed");
		}
		page.getTiffAdvanceBtn().ScrollTo();
		page.getTiffAdvanceBtn().waitAndClick(10);
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

		driver.waitForPageToBeReady();
		page.getAdvancedTabInTIFF().ScrollTo();
		page.getAdvancedTabInTIFF().waitAndClick(10);
		driver.waitForPageToBeReady();
		page.visibleCheck("Generate Load File (LST)");
		page.getElementDisplayCheck(page.getAdvancedLSTToggle());
		base.stepInfo("Advance Section is contain 'Generate Load File (LST)' with Toggle button.");

		base.passedStep(
				"To Verify Admin will be able to select the metadata and work production information to be included in a slip sheet, similar to existing RPM");
		loginPage.logout();

	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48341
	 * @Description: To verify that Tiff /PDF should burn Redactions even though
	 *               file based or tag based placeholdering is exists in the
	 *               document
	 */
	@Test(description = "RPMXCON-48341", enabled = true, groups = { "regression" })
	public void verifyProductionTiffPdfRedactionTextWithAnnotation() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case id : RPMXCON-48341 ");
		base.stepInfo(
				"To verify that Tiff /PDF should burn Redactions even though file based or tag based placeholdering is exists in the document");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		// tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		// tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		// sessionSearch.bulkTagExisting(tagname);

		// Verify
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionwithBurnRedaction();
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

		page = new ProductionPage(driver);
		beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSectionwithBurnRedaction();
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
		base.passedStep(
				"verified that Tiff /PDF should burn Redactions even though file based or tag based placeholdering is exists in the document");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		// tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security
		// Group");
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48072
	 * @Description: To Verify Field EndingBates in Production
	 */
	@Test(description = "RPMXCON-48072", enabled = true, groups = { "regression" })
	public void verifyEndBatesInProduction() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case id : RPMXCON-48072 ");
		base.stepInfo("To Verify Field EndingBates in Production");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		int docno = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		// Verify
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int number = Integer.parseInt(beginningBates);
		int endingBates = docno + number - 1;
		String endingBates1 = Integer.toString(endingBates);
		System.out.println(beginningBates);
		System.out.println(endingBates);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.getDAT_AddField().waitAndClick(5);
		page.addDatField(1, "Bates", "EndingBates");
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageAndVerfyingBatesRangeValue(endingBates1);

		// create export with TIFF
		String exportname = "E" + Utility.dynamicNameAppender();
		endingBates1 = Integer.toString(docno);
		System.out.println(beginningBates);
		System.out.println(endingBates);
		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.selectExportSetFromDropDown();
		page.addANewExport(exportname);
		page.fillingDATSection();
		page.getDAT_AddField().waitAndClick(5);
		page.addDatField(1, "Bates", "EndingBates");
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickOnGenerateButton();

		base.passedStep("Verified Field EndingBates in Production");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49062
	 * @Description: To verify that the 'Production End Date' should contain and
	 *               present the date when the post-gen checks are completed
	 */
	@Test(description = "RPMXCON-49062", enabled = true, groups = { "regression" })
	public void verifyEndDateafterPostGenCheckCompleted() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id : RPMXCON-49062");
		base.stepInfo(
				"To verify that the 'Production End Date' should contain and present the date when the post-gen checks are completed");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		// Verify archive status on Gen page
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerate(prefixID, suffixID, foldername, productionname, beginningBates);

		softAssertion = new SoftAssert();
		page.filterCompletedProduction();
		driver.waitForPageToBeReady();
		page.getGridView().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.stepInfo("Nativated to production Grid View");
		int endDateIndex = base.getIndex(page.getGridWebTableHeader(), "END DATE");
		String EndDate = page.getGridProdValues(productionname, endDateIndex).getText();
		System.out.println("Sightline show date and time : " + EndDate);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		String date1 = dateFormat.format(date);
		System.out.println("Current date and time : " + date1);
		base.stepInfo("current date : " + date1);

		boolean flag = EndDate.contains(date1);
		if (flag) {
			softAssertion.assertTrue(flag);
			softAssertion.assertAll();
			base.passedStep("End date is displayed on production grid view");
			System.out.println("date visible");
		} else {
			base.failedStep("date is not contain in text");
			System.out.println("date not visible");
		}

		base.passedStep(
				"verified that the 'Production End Date' should contain and present the date when the post-gen checks are completed");

		// delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49361
	 * @Description: Verify if PA Select the Production using a template that has
	 *               Native Files and Tags selected in the native components, then
	 *               Component tab should Complete without any error.
	 */
	@Test(description = "RPMXCON-49361", enabled = true, groups = { "regression" })
	public void verifiyProdTempInComponentTabWithoutAnyError() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id : RPMXCON-49361");
		base.stepInfo(
				"Verify if PA Select the Production using a template that has Native Files and Tags selected in the native components, then Component tab should Complete without any error.");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		TempName = "Templete" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		base = new BaseClass(driver);

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		// Verify archive status on Gen page
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSectionWithTags(tagname);
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerate(prefixID, suffixID, foldername, productionname, beginningBates);

		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.waitForPageToBeReady();
		page.getprod_ActionButton_Reusable(productionname).waitAndClick(10);
		driver.waitForPageToBeReady();
		page.getprod_Action_SaveTemplate_Reusable(productionname).waitAndClick(10);

		page.saveTemple(TempName);

		page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.getAddNewProductionbutton().waitAndClick(10);
		page.getProductionName().SendKeys(productionname);
		String loadfile = TempName + " (Production)";
		page.getprod_LoadTemplate().selectFromDropdown().selectByVisibleText(loadfile);
		driver.waitForPageToBeReady();
		page.getBasicInfoMarkComplete().waitAndClick(10);
		driver.waitForPageToBeReady();
		page.getDATTab().waitAndClick(10);
		page.getElementDisplayCheck(page.getDAT_FieldClassification1());
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		base.waitTillElemetToBeClickable(page.getMarkCompleteLink());
		page.getMarkCompleteLink().waitAndClick(10);
		base.VerifySuccessMessage("Mark Complete successful");
		base.passedStep("It should be completed without any error.");
		base.passedStep(
				"Verified if PA Select the Production using a template that has Native Files and Tags selected in the native components, then Component tab should Complete without any error.");

		// delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47903
	 * @Description: To Verify regenerate Option in Production
	 */
	@Test(description = "RPMXCON-47903", enabled = true, groups = { "regression" })
	public void verifyRegenerationOptions() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test Case ID : RPMXCON-47903");
		base.stepInfo("To Verify regenerate Option in Production");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		// production
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickOnGenerateButton();

		// open exiting production and Status
		page.openExistingProduction(productionname);
		page.verifyProductionGenerateSuccussfully();
		driver.waitForPageToBeReady();
		page.getBackButton().waitAndClick(5);
		driver.waitForPageToBeReady();
		page.verifyProductionStatusInGenPage("Post-Generation QC checks Complete");

		// regenerate
		base.waitForElement(page.getbtnGenMarkIncomplete());
		page.getbtnGenMarkIncomplete().waitAndClick(5);
		base.waitForElement(page.getbtnReGenerateMarkComplete());
		page.getbtnReGenerateMarkComplete().waitAndClick(5);
		base.stepInfo("Clicked on Regenerate button");

		// verification
		page.visibleCheck("Regenerate Production");
		page.visibleCheck(
				"You have run this production earlier with errors. Select the below option to continue with Regenerate");
		page.visibleCheck("Restart the Production From the Beginning (Removes all previously generated files)");
		page.visibleCheck(
				"Restart the production from where it left off (Keeps any previously successfully generated files)");
		page.visibleCheck("Cancel");
		page.visibleCheck("Continue");

		page.getRegenerateAllRadioBtn().waitAndClick(5);
		base.stepInfo(
				"Clicked on  'Restart the Production From the Beginning (Removes all previously generated files)  ' option and click on Continue");

		page.getbtnRegenerateContinue().waitAndClick(5);
		page.verifyProductionGenerateSuccussfully();

		base.passedStep("Verified regenerate Option in Production");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		base.stepInfo("deleting created tags and folders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48166
	 * @Description: To Verify All the Parameters configured for MP3 is saved for
	 *               the production on save Production as Template.
	 */
	@Test(description = "RPMXCON-48166", enabled = true, groups = { "regression" })
	public void verifyProductionTempSaveSuccessfully() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id : RPMXCON-48166");
		base.stepInfo(
				"To Verify  All the Parameters configured for MP3 is saved for the production on save Production as Template.");

		// foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		TempName = "Templete" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		base = new BaseClass(driver);

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		// tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		// sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		// Verify archive status on Gen page
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname);
		page.fillingMP3FileWithBurnRedaction();
		page.navigateToNextSection();

		this.driver.getWebDriver().get(Input.url + "Production/Home");
		page.getprod_ActionButton_Reusable(productionname).waitAndClick(10);
		driver.waitForPageToBeReady();
		page.getprod_Action_SaveTemplate_Reusable(productionname).waitAndClick(10);

		page.saveTemple(TempName);
		base.passedStep(
				"To Verify  All the Parameters configured for MP3 is saved for the production on save Production as Template.");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		// tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security
		// Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48573
	 * @Description: To verify that PA creates new production using continue with
	 *               last bates range of last production, which is completed with
	 *               only pre-gen check
	 */
	@Test(description = "RPMXCON-48573", enabled = true, groups = { "regression" })
	public void verifyNextBatesRage() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case id : RPMXCON-48573 ");
		base.stepInfo(
				"To verify that PA creates new production using continue with last bates range of last production, which is completed with only pre-gen check");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Utility.randomCharacterAppender(1);
		String suffixID = Utility.randomCharacterAppender(1);

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		int docno = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		// Verify
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int number = Integer.parseInt(beginningBates);
		int endingBates = docno + number;
		String nextBates = Integer.toString(endingBates);
		System.out.println(beginningBates);
		System.out.println(endingBates);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		System.out.println(prefixID + beginningBates + suffixID);
		page.fillingGeneratePageAndVerfyingBatesRangeValue(beginningBates, prefixID, suffixID);

		// next bates
		page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, nextBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();

		base = new BaseClass(driver);

		if (base.getCloseSucessmsg().isElementAvailable(5)) {
			base.CloseSuccessMsgpopup();
		}

		page.clickOnGenerateButton();
		driver.waitForPageToBeReady();
		base.VerifySuccessMessage("Generation Started Successfully");

		base.passedStep(
				"To verify that PA creates new production using continue with last bates range of last production, which is completed with only pre-gen check");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Test case id-RPMXCON-47912
	 * @DescriptionTo To Verify generated Generate Load File Should point to
	 *                respective Directories.(Eg.Native Load File Should point to
	 *                Native Directory).
	 * 
	 */
	@Test(description = "RPMXCON-47912", enabled = true, groups = { "regression" })
	public void verifyProductionDirectory() throws Exception {

		UtilityLog.info(Input.prodPath);

		base.stepInfo("RPMXCON-47912-Production component");
		base.stepInfo("To Verify generated Generate Load File Should point to respective Directories.(Eg.Native Load File Should point to Native Directory).");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Utility.randomCharacterAppender(1);
		String suffixID = Utility.randomCharacterAppender(1);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

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
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		driver.scrollPageToTop();
		base.waitForElement(page.getMarkCompleteLink());
		page.getMarkCompleteLink().waitAndClick(10);
		driver.waitForPageToBeReady();

		String location = page.getProductionOutputLocation_VolumeName().GetAttribute("value");
		String loadfile = page.getProductionComponentsFolderDetails_FolderName_LoadFiles().GetAttribute("value");
		String directory = page.getProductionOutputLocation_DriveText().GetAttribute("value");
		String image = page.getProductionComponentsFolderDetails_FolderName_Images().GetAttribute("value");
		String Text = page.getProductionComponentsFolderDetails_FolderName_Text().GetAttribute("value");
		String Native = page.getProductionComponentsFolderDetails_FolderName_Natives().GetAttribute("value");

		driver.waitForPageToBeReady();
		base.waitForElement(page.getNextButton());
		page.getNextButton().Enabled();
		page.getNextButton().waitAndClick(10);

		page.fillingSummaryAndPreview();
		page.fillingGeneratePage();

		String name = page.getProduction().getText().trim();
		System.out.println(name);
		String home = System.getProperty("user.home");

		driver.waitForPageToBeReady();

		page.extractFile();

		File f = new File(home + "/Downloads/" + location + "/" + loadfile + "/");
		File dat = new File(home + "/Downloads/" + location + "/" + loadfile + "/" + name + "_DAT.dat");
		File tiff = new File(home + "/Downloads/" + location + "/" + loadfile + "/" + name + "_TIFF.OPT");
		File text = new File(home + "/Downloads/" + location + "/" + loadfile + "/" + name + "_Text.lst");
		File NativeLST = new File(home + "/Downloads/" + location + "/" + loadfile + "/" + name + "_Native.lst");
		driver.waitForPageToBeReady();

		if (f.exists()) {
			System.out.println("load file is Exists in pointed directory");
			base.passedStep("load file is Exists in pointed directory");
		} else {
			System.out.println("Does not Exists");
			base.failedStep("Does not Exists");
		}
		if (dat.exists()) {
			System.out.println("dat file is Exists in pointed directory");
			base.passedStep("dat file is Exists");
		} else {
			System.out.println("Does not Exists");
			base.failedStep("Dat does not Exists");
		}
		if (tiff.exists()) {
			System.out.println("tiff file is Exists in pointed directory");
			base.passedStep("tiff loadfile exists");

			driver.waitForPageToBeReady();
			for (String line : Files.readAllLines(
					Paths.get(home + "/Downloads/" + location + "/" + loadfile + "/" + name + "_TIFF.OPT"))) {

				if (line.contains(directory + location + "\\" + image + "\\")) {
					System.out.println("tiff is displayed as expected");
					base.passedStep("Tiff load file point Image directory");
				} else {
					base.failedStep("tiff verification failed");
				}
			}
		} else {
			System.out.println("Tiff Does not Exists");
			base.stepInfo("Tiff load file is not generated");
		}
		if (text.exists()) {
			System.out.println("text file is Exists in pointed directory");
			base.passedStep("text loadfile is exists");
			driver.waitForPageToBeReady();
			for (String line : Files.readAllLines(
					Paths.get(home + "/Downloads/" + location + "/" + loadfile + "/" + name + "_Text.lst"))) {

				if (line.contains(directory + location + "\\" + Text + "\\")) {
					System.out.println("Text is displayed as expected");
					base.passedStep("Text load file point text directory");
				} else {
					base.failedStep("Text load file not displayed");
				}
			}
		} else {
			System.out.println("Does not Exists");
			base.stepInfo("Text load file is not generated");
		}
		if (NativeLST.exists()) {
			System.out.println("Native file is Exists in pointed directory");
			base.passedStep("Native loadfile exists");

			driver.waitForPageToBeReady();
			for (String line : Files.readAllLines(
					Paths.get(home + "/Downloads/" + location + "/" + loadfile + "/" + name + "_Native.lst"))) {

				if (line.contains(directory + location + "\\" + Native + "\\")) {
					System.out.println("native is displayed as expected");
					base.passedStep("Native load file point Native directory");
				} else {
					base.failedStep("the text is not displayed as expected");
				}
			}
		} else {
			System.out.println("Does not Exists");
			base.stepInfo("Native load file is not generated");
		}

		base.passedStep("Text is displayed as expected");
		base.passedStep(
				"Verified generated Generate Load File Should point to respective Directories.(Eg.Native Load File Should point to Native Directory).");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Aathith.Senthilkumar 47930
	 * @Description To verify, Priv Guard in Production should show correct document
	 *              counts
	 * 
	 */
	@Test(description = "RPMXCON-47930", enabled = true, groups = { "regression" })
	public void verifyPrivGuardDocCount() throws Exception {
		driver.waitForPageToBeReady();
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47930 -Production Sprint 09");
		base.stepInfo("To verify, Priv Guard in Production should show correct document counts");

		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String Redactiontag1;
		Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();
		int count = 3;

		RedactionPage redactionpage = new RedactionPage(driver);

		driver.waitForPageToBeReady();
		redactionpage.manageRedactionTagsPage(Redactiontag1);
		System.out.println("First Redaction Tag is created" + Redactiontag1);

		DocExplorerPage docExp = new DocExplorerPage(driver);
		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
		docExp.documentSelectionIteration();
		docExp.docExpViewInDocView();

		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		// doc1
		docViewRedactions.selectDoc1();
		docViewRedactions.selectDoc2();
		docViewRedactions.selectDoc3();

		driver.waitForPageToBeReady();
		docViewRedactions.redactRectangleUsingOffset(10, 10, 100, 100);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag1);

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.createNewTagwithClassificationInRMU(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolderInRMU(foldername);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		driver.waitForPageToBeReady();
		sessionSearch.ViewInDocListWithOutPureHit();

		DocListPage doc = new DocListPage(driver);
		doc.documentSelection(count);
		driver.waitForPageToBeReady();
		doc.bulkTagExistingFromDoclist(tagname);

		// Verify
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname);
		base.waitForElement(page.getClk_burnReductiontoggle());
		page.getClk_burnReductiontoggle().waitAndClick(10);
		driver.waitForPageToBeReady();
		page.burnRedactionWithRedactionTag(Redactiontag1);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.AddRuleAndRemoveRuleTagAndRedaction(tagname, Redactiontag1);
		driver.waitForPageToBeReady();
		String docNo = page.getDocumentSelectionLink().getText().trim();
		int displayCount = Integer.parseInt(docNo);
		System.out.println(displayCount);
		SoftAssert softAssertion = new SoftAssert();
		if (count == displayCount) {
			softAssertion.assertEquals(count, displayCount);
			base.passedStep("Document count is get displayed Correctly");
			System.out.println("count is verified");
		} else {
			base.failedStep("Document Count verification failed");
			System.out.println("count not verified");
		}
		base.passedStep("verified, Priv Guard in Production should show correct document counts");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();

	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48020
	 * @Description: To Verify Natives of the docs of the selected file types or
	 *               selected tags are produced unless they are to excluded due to
	 *               Redaction or PrivTags.
	 */
	@Test(description = "RPMXCON-48020", enabled = true, groups = { "regression" })
	public void verifyNativeDocs() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case id : RPMXCON-48020 ");
		base.stepInfo(
				"To Verify Natives of the docs of the selected file types or selected tags are produced unless they are to excluded due to Redaction or PrivTags.");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Utility.randomCharacterAppender(1);
		String suffixID = Utility.randomCharacterAppender(1);

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		int docno = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		// Verify
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		System.out.println("beg bates : " + beginningBates);
		int number = Integer.parseInt(beginningBates);
		int lastfile = number + docno;
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionwithNativelyPlaceholder(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);

		driver.scrollPageToTop();
		base.waitForElement(page.getMarkCompleteLink());
		page.getMarkCompleteLink().waitAndClick(10);
		driver.waitForPageToBeReady();

		String production = page.getProductionOutputLocationProductionDirectory().GetAttribute("value");
		String location = page.getProductionOutputLocation_VolumeName().GetAttribute("value");
		String Native = page.getProductionComponentsFolderDetails_FolderName_Natives().GetAttribute("value");

		driver.waitForPageToBeReady();
		base.waitForElement(page.getNextButton());
		page.getNextButton().Enabled();
		page.getNextButton().waitAndClick(10);

		page.fillingSummaryAndPreview();
		System.out.println(prefixID + beginningBates + suffixID);
		page.fillingGeneratePageWithContinueGenerationPopup();

		String home = System.getProperty("user.home");

		driver.waitForPageToBeReady();
		page.extractFile();

		for (int i = number + 3; i < lastfile; i++) {
			File f = new File(
					home + "/Downloads/" + location + "/" + Native + "/0001/" + prefixID + i + suffixID + ".doc");
			if (f.exists()) {
				base.passedStep("Native file copied from selected tag");
				System.out.println("passeed");
			} else {
				base.failedStep("verification failed");
				System.out.println("failed");
			}

		}
		driver.waitForPageToBeReady();

		base.passedStep(
				"To verify that PA creates new production using continue with last bates range of last production, which is completed with only pre-gen check");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		//tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
//			loginPage.logoutWithoutAssert();
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