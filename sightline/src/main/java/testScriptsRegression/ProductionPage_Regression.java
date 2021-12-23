package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.NoSuchElementException;
import java.util.concurrent.Callable;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.DataSets;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.RedactionPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

/**
 * @author Indium-Baskar
 */

public class ProductionPage_Regression {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	ProductionPage productionPage;
	TagsAndFoldersPage tagsAndFolderPage;
	SessionSearch sessionSearch;
	DocListPage docPage;
	DocViewPage docViewPage;
	SoftAssert softAssertion;
	String exportname;
	String prefixID = "A_" + Utility.dynamicNameAppender();
	String suffixID = "_P" + Utility.dynamicNameAppender();
	String foldername;
	String tagname;
	String productionname;
	String TempName;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		// Open browser
		Input in = new Input();
		in.loadEnvConfig();
		driver = new Driver();
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		productionPage = new ProductionPage(driver);
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		sessionSearch = new SessionSearch(driver);
		softAssertion = new SoftAssert();

	}

	/**
	 * @author Indium-Baskar date: 29/8/2021 Modified date: N/A Modified by: Baskar
	 * @Description : Verify that Preivew documents should be display Standard
	 *              Template along with Slip sheets
	 */

//	@Test(enabled = true, groups = { "regression" }, priority = 1)
	public void verifySlipSheetDisplayed() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-56117- Production Sprint 02");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String productionName = "Prod05" + Utility.dynamicNameAppender();
		String tagName = "Tags" + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;
//		String tagNamePrev = Input.tagNamePrev;
		String folderName = "FolderProd" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(folderName, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagName, "Privileged");

		// search for folder
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(folderName);

		// create production and verify preview tab button should display
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		productionPage.addANewProduction(productionName);
		productionPage.fillingDATSection();
		productionPage.fillingNativeSection();
		productionPage.tiffBrandingSelection(tagName);
		productionPage.tiffPrivilegeDocumentSelection(tagName);
		productionPage.slipSheetToggleEnable();
		productionPage.availableFieldSelection("BatesNumber");
		productionPage.fillingTextSection();
		productionPage.navigateToNextSection();
		baseClass.passedStep("Production components completed and navigated to numbering and sorting page");
		productionPage.fillingNumberingAndSortingPage(prefixID, suffixID);
		productionPage.navigateToNextSection();
		productionPage.fillingDocumentSelectionPage(folderName);
		productionPage.navigateToNextSection();
		productionPage.fillingPrivGuardPage();
		productionPage.fillingProductionLocationPage(productionName);
		productionPage.navigateToNextSection();
		productionPage.viewingPreviewButtonInSummaryTab();
		productionPage.verifyContentInPDf(prefixID, suffixID);
		driver.waitForPageToBeReady();
		driver.getWebDriver().navigate().back();
		driver.getWebDriver().navigate().refresh();

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(folderName, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagName, "Default Security Group");
		loginPage.logout();

	}

	/**
	 * @author Indium-Baskar date: 29/8/2021 Modified date: N/A Modified by: Baskar
	 * @Description : Verify that if user selects only DAT in production then
	 *              Preview button is not available on 'Summary and Preview' tab
	 */

	@Test(enabled = true, groups = { "regression" }, priority = 2)
	public void verifyPreviewButtonShouldNotDisplayWhileDatOnlySelection() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-56116- Production Sprint 02");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String productionName = "Prod05" + Utility.dynamicNameAppender();
		String tagName = "Tags" + Utility.dynamicNameAppender();
		String folderName = "FolderProd" + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;

		// Pre-requisites

		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(folderName, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagName, "Privileged");

		// search for folder
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(folderName);

		// create production and verify preview tab button should not display
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		productionPage.addANewProduction(productionName);
		productionPage.fillingDATSection();
		productionPage.afterCompletingNavigatingToNextPage();
		baseClass.passedStep("Production components completed and navigated to numbering and sorting page");
		productionPage.fillingNumberingAndSortingPage(prefixID, suffixID);
		productionPage.afterCompletingNavigatingToNextPage();
		productionPage.fillingDocumentSelectionPage(folderName);
		productionPage.afterCompletingNavigatingToNextPage();
		productionPage.fillingPrivGuardPage();
		productionPage.fillingProductionLocationPage(productionName);
		productionPage.afterCompletingNavigatingToNextPage();
		productionPage.viewingPreviewButtonInSummaryTab();

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(folderName, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagName, "Default Security Group");
		loginPage.logout();

	}

	/**
	 * @author Indium-Baskar date: 29/8/2021 Modified date: N/A Modified by: Baskar
	 * @Description : Verify that if user selects the DAT and MP3 option in
	 *              production then Preview button is not available on 'Summary and
	 *              Preview' tab
	 */

//	@Test(enabled = true, groups = { "regression" }, priority = 3)
	public void verifyPreviewButtonShouldNotDisplayWhileDatAndMp3OnlySelection() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-56116- Production Sprint 02");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String productionName = "Prod05" + Utility.dynamicNameAppender();
		String tagName = "Tags" + Utility.dynamicNameAppender();
		String folderName = "FolderProd" + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;
		// Pre-requisites
		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(folderName, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagName, "Privileged");

		// search for folder
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(folderName);

		// create production and verify preview tab button should not display
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		productionPage.addANewProduction(productionName);
		productionPage.fillingDATSection();
		productionPage.advancedProductionComponentsMP3();
		productionPage.afterCompletingNavigatingToNextPage();
		baseClass.passedStep("Production components completed and navigated to numbering and sorting page");
		productionPage.fillingNumberingAndSortingPage(prefixID, suffixID);
		productionPage.afterCompletingNavigatingToNextPage();
		productionPage.fillingDocumentSelectionPage(folderName);
		productionPage.afterCompletingNavigatingToNextPage();
		productionPage.fillingPrivGuardPage();
		productionPage.fillingProductionLocationPage(productionName);
		productionPage.afterCompletingNavigatingToNextPage();
		baseClass.passedStep("Preview button is not displayed while selecting DAT and MP3 Files only selection");
		productionPage.viewingPreviewButtonInSummaryTab();

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(folderName, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagName, "Default Security Group");
		loginPage.logout();

	}

	/**
	 * @author Indium-Baskar date: 1/9/2021 Modified date: N/A Modified by: Baskar
	 * @Description : Verify that configuration format should applied for OCRed text
	 *              files
	 */

//	@Test(enabled = true, groups = { "regression" }, priority = 4)
	public void verifyConfigurationFormat() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-56080- Production Sprint 02");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info(Input.prodPath);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String productionName = "Prod05" + Utility.dynamicNameAppender();
		String tagName = "Tags" + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;
		String folderName = "FolderProd" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(folderName, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagName, "Privileged");

		// search for folder
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(folderName);

		// create production and verify preview tab button should display
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		productionPage.addANewProduction(productionName);
		productionPage.fillingDATSection();
		productionPage.tiffBrandingSelection(tagName);
		productionPage.tiffPrivilegeDocumentSelection(tagName);
		productionPage.fillingTextSection();
		productionPage.selectFormatInTextFile();
		productionPage.afterCompletingNavigatingToNextPage();
		baseClass.passedStep("Production components completed and navigated to numbering and sorting page");
		productionPage.fillingNumberingAndSortingPage(prefixID, suffixID);
		productionPage.afterCompletingNavigatingToNextPage();
		productionPage.fillingDocumentSelectionPage(folderName);
		productionPage.afterCompletingNavigatingToNextPage();
		productionPage.fillingPrivGuardPage();
		productionPage.fillingProductionLocationPage(productionName);
		productionPage.afterCompletingNavigatingToNextPage();
		productionPage.afterCompletingNavigatingToNextPage();
		baseClass.stepInfo("Completed summary and preview tab");
		productionPage.fillingGeneratePage();
		baseClass.passedStep("OCRed text files verified according to ansi text configuration");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(folderName, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagName, "Default Security Group");
		loginPage.logout();

	}

	/**
	 * @author Indium-Baskar date: 1/9/2021 Modified date: N/A Modified by: Baskar
	 * @Description : Verify that 'Page level' option should not displays on
	 *              Production-Text components
	 */

//	@Test(enabled = true, groups = { "regression" }, priority = 5)
	public void verifyingPageLevelOptionsOnTextComponents() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-56117- Production Sprint 02");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info(Input.prodPath);
		String productionName = "Prod05" + Utility.dynamicNameAppender();

		// create production and verify preview tab button should display
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		productionPage.addANewProduction(productionName);
		productionPage.fillingTextSection();
		try {
			softAssertion.assertEquals(productionPage.getPageLevelTextComponent().Displayed().booleanValue(), true);
			baseClass.failedStep("Page level options displayed in production component when clicking the  Text tab");
		} catch (org.openqa.selenium.NoSuchElementException e) {
			baseClass
					.passedStep("Page level options not displayed in production component when clicking the  Text tab");
			e.printStackTrace();
		}
		loginPage.logout();

	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56147
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description:Verify that Production is generated with 'Natively Produced
	 *                     Documents' Placeholder for the problem document
	 * 
	 */
//	@Test(enabled = true, groups = { "regression" }, priority = 6)
	public void verifyProductionForPDFWithNativelyPlaceholder() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-56147- Production Sprint 05");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info(Input.prodPath);
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;

		// Pre-requisites
		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");
		sessionSearch = new SessionSearch(driver);
		// testData string contains redacted documents as per Pre-requisites
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production using dat,native and enable natively placeholder and
		// disable burn redactions
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		productionname = "p" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSectionwithNativelyPlaceholder(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePage();
		baseClass.passedStep(
				"Verified that Production is generated with 'Natively Produced Documents' Placeholder for the problem document");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56152
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description:Verify that Production generate successfully for redacted
	 *                     document if user select TIFF Or PDF with Naitvely
	 *                     Placeholder and Redaction is not enabled.
	 */
//	@Test(groups = { "regression" }, priority = 7)
	public void verifyProductionForNativelyPlaceholderAndRedactionNotEnabled() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-56152- Production Sprint 05");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info(Input.prodPath);
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;

		// Pre-requisites
		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.CreateTagwithClassification(tagname, "Privileged");

		sessionSearch = new SessionSearch(driver);
		// testData string contains redacted documents as per Pre-requisites
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production using dat,native and enable natively placeholder and
		// disable burn redactions
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		productionname = "p" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionwithNativelyPlaceholder(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePage();
		baseClass.passedStep(
				"Verified that Production generate successfully for redacted document if user select TIFF Or PDF with Naitvely Placeholder and Redaction is not enabled.");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");

		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         NO:RPMXCON_56003.
	 * @Description:Verify the disclaimer text on Shareble links TESTCASE
	 *                     NO:RPMXCON_56004
	 * @Description:Verify the shareable links and a password is available on
	 *                     'Shareable Link' window
	 * 
	 * @throws Exception
	 */
//	@Test(groups = { "regression" }, priority = 8)
	public void verifySharableLinks() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-56003- Production Sprint 05");
		baseClass.stepInfo("Test case Id: RPMXCON-56004- Production Sprint 05");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info(Input.prodPath);
		String testData1 = Input.testData1;
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");
		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();

		// To verify prod generation failed in progress bar
		page.prodGenerationInProgressBar();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionwithFontColour(tagname,"Black with white font");
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.selectDownloadInGeneratePage();
		baseClass.passedStep("Verified the shareable links and a password is available on 'Shareable Link' window");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();

	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON_56137
	 * @Description:Verify that Count displays correctly on Priv Guard if child
	 *                     documents is assigned to Priv Tag
	 */
//	@Test(groups = { "regression" }, priority = 9)
	public void verifyPrivGuardBySelectingChildDocuments() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-56137- Production Sprint 05");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info(Input.prodPath);

		tagname = "AATag" + Utility.dynamicNameAppender();
		String documentId = Input.documentID;

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(documentId);
		sessionSearch.ViewInDocList();

		docPage = new DocListPage(driver);
		docPage.selectChildDocumentAndSave(documentId);
		docPage.bulkTagExisting(tagname);

		// create production
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		productionname = "p" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFWithDisablePrivilegdedDocs();
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID);
		page.navigateToNextSection();
		page.fillingSelectDocumentUsingTags(tagname);
		page.navigateToNextSection();
		page.priviledgeDocCheck(tagname);
		page.fillingPrivGuardPage();
		driver.waitForPageToBeReady();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		baseClass.passedStep(
				"Verified that Count displays correctly on Priv Guard if child documents is assigned to Priv Tag");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();

	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON_56121
	 * @Description:Verify that updated redaction text is displays on generated
	 *                     Tiff/PDF for orphan redactions document
	 */
//	@Test(groups = { "regression" }, priority = 10)
	public void orphanRedactionsInSelectedDocument() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-56121- Production Sprint 05");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String orphanDocument = Input.orphanDocument;
		// Pre-requisites
		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Technical Issue");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		sessionSearch = new SessionSearch(driver);
		// passing orphan redacted DOCID as string
		sessionSearch.basicContentSearch(orphanDocument);
		sessionSearch.bulkFolderExisting(foldername);

		// create production using dat,native and tiff for redaction
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		productionname = "p" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFWithBurnRedactionAndSelectingOneTag();
		page.fillingTextSection();
		page.navigateToNextSection();
		driver.waitForPageToBeReady();
		page.fillingNumberingAndSortingPage(prefixID, suffixID);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePage();
		baseClass.passedStep(
				"Verified that updated redaction text is displays on generated Tiff/PDF for orphan redactions document");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON_55990
	 * @Description:Verify that if Producion is UnCommitted then 'Post Generation QC
	 *                     Checks' status should be display on Production progress
	 *                     status bar
	 */

//	@Test(groups = { "regression" }, priority = 11)
	public void AssertionOnUnCommitInQCPage() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-55990- Production Sprint 05");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info(Input.prodPath);

		String testData1 = Input.testData1;
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String tagNamePrev = Input.tagNamePrev;

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname, tagNamePrev);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.AssertionUnCommitInQCPage();
		page.VerifyinginProductionQCMessage();
		baseClass.passedStep(
				"Verify that if Producion is UnCommitted then 'Post Generation QC Checks' status should be display on Production progress status bar");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();

	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON_55971.
	 * @Description:Verify that Production status displays in Draft with blank
	 *                     progress bar on Tile View TESTCASE No: RPMXCON_55972
	 * @Description:Verify that Production status displays as Draft on Generation
	 *                     Page
	 * 
	 */

//	@Test(groups = { "regression" }, priority = 12)
	public void ViewDraftAssertionInGeneratePage() throws Exception {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-55971- Production Sprint 05");
		baseClass.stepInfo("Test case Id: RPMXCON-55972- Production Sprint 05");
		UtilityLog.info(Input.prodPath);
		String testData1 = Input.testData1;
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.CreateTagwithClassification(tagname, "Privileged");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();

		// To verify prod generation failed in progress bar

		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionwithFontColour(tagname,"Black with white font");
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.DraftAssertionInGeneratePage();
		baseClass.passedStep(" Production status displays as Draft on Generation Page");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();
	}

	/*
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 * No:RPMXCON_58594
	 * 
	 * @Description:Verify the name of load files should be used the name of the
	 * Production TESTCASE No:RPMXCON_58592 Description:Verify the load files should
	 * be created under the mentioned default directory in the production
	 * configuration
	 */
//	@Test(groups = { "regression" }, priority = 13)
	public void passProductionNameAsLoadFilesName() throws Exception {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info(Input.prodPath);
		baseClass.stepInfo("RPMXCON-58592 Production - Sprint 05");
		baseClass.stepInfo("RPMXCON-58594 Production - Sprint 05");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String tagNamePrev = Input.tagNamePrev;
		String testData1 = Input.testData1;

		// Pre-requisites
		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

		// search for folder
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production and pass production name as load files name
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		productionname = "p" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname, tagNamePrev);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePage();
		baseClass.passedStep("passed ProductionName As LoadFiles Name.");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();
	}

	/*
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 * No:RPMXCON_56080
	 * 
	 * @Description:Verify that configuration format should applied for OCRed text
	 * files
	 */
//	@Test(groups = { "regression" }, priority = 14)
	public void verifyFormatOCRedTextFiles() throws Exception {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info(Input.prodPath);
		baseClass.stepInfo("RPMXCON-56080 Production - Sprint 05");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String tagNamePrev = Input.tagNamePrev;
		String testData1 = Input.testData1;

		// Pre-requisites
		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

		// search for folder
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production and pass production name as load files name
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		productionname = "p" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname, tagNamePrev);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerate(prefixID, suffixID, foldername, productionname);
		baseClass.passedStep("Verify that configuration format should applied for OCRed text files");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();

	}
	
	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-55987
	 * @Description:Verify that after Post Generation is completed, it will displays
	 *                     status on Production Progress bar Tile View as 'Post
	 *                     Generation QC Checks'
	 */

//	@Test(groups = { "regression" }, priority = 15)

	public void verifyPostGenInTileView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-55987- Production Sprint 05");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info(Input.prodPath);

		String testData1 = Input.testData1;
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String tagNamePrev = Input.tagNamePrev;

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname, tagNamePrev);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		SoftAssert softAssertion = new SoftAssert();
		String expectedText = "Success";

		// Wait until Generate button enables
		baseClass.waitForElement(page.getbtnProductionGenerate());
		page.getbtnProductionGenerate().waitAndClick(10);
		page.getbtnProductionGenerate().waitAndFind(540);

		Reporter.log("Wait for generate to complete", true);
		System.out.println("Wait for generate to complete");
		UtilityLog.info("Wait for generate to complete");

		// Get Documents Generated Text
		baseClass.waitForElement(page.getDocumentGeneratetext());
		String actualText = page.getStatusSuccessTxt().getText();
		System.out.println(actualText);

		softAssertion.assertTrue(actualText.contains(expectedText));
		baseClass.passedStep("Documents Generated successfully");

		// Go To Production Home Page
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();

		// To filter In progress state Production
		page.prodGenerationInProgressStatus();

		// verify Production in Post-Gen QC Checks Complete status
		if (page.gettxtPostGenCompleted().Displayed()) {
			String statusText = page.gettxtPostGenCompleted().getText();
			System.out.println(statusText);
			baseClass.passedStep(
					"Verified that after Post Generation is completed, it will displays status on Production Progress bar Tile View as 'Post Generation QC Checks'");
		}

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();
	}

	/**
	 * @Author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56165
	 * @throws InterruptedException
	 * @Description:To Verify that End-to-end Production Export functionality is
	 *                 working properly with TIFF files(Production Export).
	 */
	@Test(groups = { "regression" }, priority = 17)
	public void createExport() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-56165- Production Sprint 05");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String tagNameTechnical = Input.tagNameTechnical;
		String testData1 = Input.testData1;

		// Pre-requisites
		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

		// search for the created folder and check the pure hit count
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create export with TIFF
		exportname = "E" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.selectExportSetFromDropDown();
		page.addANewExport(exportname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname, tagNameTechnical);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();

		SoftAssert softAssertion = new SoftAssert();
		String expectedText = "Success";

		// Wait until Generate button enables
		baseClass.waitForElement(page.getbtnProductionGenerate());
		page.getbtnProductionGenerate().waitAndClick(10);
		page.getbtnProductionGenerate().waitAndFind(540);

		Reporter.log("Wait for generate to complete", true);
		System.out.println("Wait for generate to complete");
		UtilityLog.info("Wait for generate to complete");

		// Get Documents Generated Text
		baseClass.waitForElement(page.getDocumentGeneratetext());
		String actualText = page.getStatusSuccessTxt().getText();
		System.out.println(actualText);

		softAssertion.assertTrue(actualText.contains(expectedText));
		baseClass.passedStep("Documents Generated successfully");

		// To delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();
	}

	/**
	 * @author sowndarya.velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON_56123
	 * @Description:Verify that if redaction text is different for " Redaction
	 *                     without redaction tags" and Redaction tag then
	 *                     specified/respective redaction text should be displayed
	 *                     on generated documents TESTCASE No:RPMXCON_56122
	 * @Description:Verify that on Tiff/PDF section," Redaction without redaction
	 *                     tags" is not selected in 'Selected Redaction Tags' then
	 *                     updated redacted text should not be displays for Orphan
	 *                     redaction tags
	 * 
	 */

//	@Test(groups = { "regression" }, priority = 18)
	public void generatingTheProductionAfterBurnRedaction() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-56122- Production Sprint 05");
		baseClass.stepInfo("Test case Id: RPMXCON-56123- Production Sprint 05");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info(Input.prodPath);

		String Redactiontag1;

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		UtilityLog.info(Input.prodPath);

		Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();

		RedactionPage redactionpage = new RedactionPage(driver);
		redactionpage.selectDefaultSecurityGroup();
		redactionpage.manageRedactionTagsPage(Redactiontag1);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch("ID00000110");
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);

		productionname = "p" + Utility.dynamicNameAppender();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFFSectionWithRedactionTags(Redactiontag1);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePage();
		baseClass.passedStep("production is generated successfully when redaction tag is selected in burn redaction");

		// To delete tag and folder created
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		loginPage.logout();
	}

	/**
	 * 
	 * @author Gopinath created on:NA modified by:NA
	 * @Testcase Id : RPMXCON_56124 : Verify that if Tag is already specified with
	 *           Left Branding then that Tag should not be available.
	 * @Description : Verify that if Tag is already specified with Left Branding
	 *              then that Tag should not be available.
	 */

//	@Test(groups = { "regression" }, priority = 16)

	public void tiffSectionLeftBranding() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-56124- Production Sprint 05");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info(Input.prodPath);
		baseClass.stepInfo(
				"#### Verify that if Tag is already specified with Right Branding then that Tag should not be available. ####");

		String tagname = "tag" + Utility.dynamicNameAppender();
		String productionname = "production" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		baseClass.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

		ProductionPage page = new ProductionPage(driver);
		baseClass.stepInfo("Add New Production");
		page.addANewProduction(productionname);
		baseClass.stepInfo("Filling DAT Section");
		page.fillingDATSection();
		baseClass.stepInfo("Filling Native Section");
		page.fillingNativeSection();
		baseClass.stepInfo("Verify The Tag On Left Branding");
		page.verifyTheTagOnLeftBranding(tagname, "testing");
		baseClass.stepInfo("filling Text Section");
		page.fillingTextSection();
		baseClass.stepInfo("Click Component Mark Complete And Incomplete");
		page.clickComponentMarkCompleteAndIncomplete();
		baseClass.stepInfo("Again Selecting Left Header Branding");
		page.againSelectingLeftHeaderBranding(tagname);
		baseClass.stepInfo("verifying tag selected in the  specify branding at Left Header branding");

		// To delete tag and folder created
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();
	}

	/**
	 * @Author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         NO:RPMXCON_49242 Description:To verify that Project Admin can modify
	 *         the populated the bates number fields and production should generated
	 *         with modified bates number
	 */
//	@Test(groups = { "regression" }, priority = 19)
	public void verificationOnNumberingAndSortingPage() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-49242- Production Sprint 05");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info(Input.prodPath);

		String testData1 = Input.testData1;
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.CreateTagwithClassification(tagname, "Privileged");
		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);
		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFWithSelectingBrandingSelectionTags(tagname);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID);
		page.reFillingTheNumberingAndSortingPage(prefixID, suffixID);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePage();

		// To delete tag and folder created
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();

	}

	
	
	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56150
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description:Verify that for Native section should be displayed message which will inform the user about the privileged and redacted docs from export
	 * 
	 */
//	@Test(groups = { "regression" }, priority = 21)
	public void verifyNativeSectionDisplayForDocumentsFromExport() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-56150- Production Sprint 06");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String tagNameTechnical = Input.tagNameTechnical;
		String testData1 = Input.testData1;

		// Pre-requisites
		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.CreateTagwithClassification(tagname, "Privileged");

		sessionSearch = new SessionSearch(driver);
		// testData string contains redacted documents as per Pre-requisites
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create export with native which displays message about priviledged and
		// redacted export
		exportname = "E" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.selectExportSetFromDropDown();
		page.addANewExport(exportname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname, tagNameTechnical);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportname);
		page.navigateToNextSection();
		page.viewingToolTipInSummaryAndPreview();
		baseClass.passedStep("Verified that for Native section should be displayed message which will inform the user about the privileged and redacted docs from export");
	
		//To delete tag and folder 
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();
	}
	
	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49969
	 * @Description:Verify that Production should generate only PDF with Redaction text
	 * 
	 */
//	@Test(groups = { "regression" }, priority = 20)
	public void verifyProductionWithPDFRedaction() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-49969- Production Sprint 06");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;

		// Pre-requisites
		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.CreateTagwithClassification(tagname, "Privileged");

		sessionSearch = new SessionSearch(driver);
		// testData string contains redacted documents as per Pre-requisites
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);

		//create Production
		productionname = "P" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFForRedaction();
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerate(prefixID, suffixID, foldername, productionname);
		baseClass.passedStep("Verified that Production should generate only PDF with Redaction text");
		
		//To delete tag and folder 
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();
	}
	
	
	
	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49134
	 * @Description:To verify that when text is exported for Exception file then it should export the text with the Placeholder
	 * 
	 */
//	@Test(groups = { "regression" }, priority = 22)
	public void verifyTextExportWithPlaceholder() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-49134- Production Sprint 06");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		UtilityLog.info(foldername);
		tagsAndFolderPage.CreateTagwithClassification(tagname, "Privileged");
		UtilityLog.info(tagname);
		
		sessionSearch = new SessionSearch(driver);
		// testData string contains redacted documents as per Pre-requisites
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		//create Production
		productionname = "P" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTiffSectionTechIssueWithEnteringText(Input.searchString4,tagname);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerate(prefixID, suffixID, foldername, productionname);
		baseClass.passedStep("verify that when text is exported for Exception file then it should export the text with the Placeholder");
		
		//To delete tag and folder 
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();
	}
	
	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49856
	 * @Description:Verify that Production should generated successfully and PDF/TIFF should produced with Comments/Signature
	 * 
	 */
//	@Test(groups = { "regression" }, priority = 23)
	public void verifyTIFFWithSignature() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-49856- Production Sprint 06");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		UtilityLog.info(foldername);
		tagsAndFolderPage.CreateTagwithClassification(tagname, "Privileged");
		UtilityLog.info(tagname);
		
		sessionSearch = new SessionSearch(driver);
		// testData string contains redacted documents as per Pre-requisites
		sessionSearch.basicContentSearch(Input.searchString20Docs);
		sessionSearch.bulkFolderExisting(foldername);

		//create Production
		productionname = "P" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname, Input.tagNameTechnical);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerate(prefixID, suffixID, foldername, productionname);
		baseClass.passedStep("Verified that Production should generated successfully and PDF/TIFF should produced with Comments/Signature");
		
		//To delete tag and folder 
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();
	}
	
	
	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56110
	 * @Description:Verify that if user selects the 'Genrate PDF' option in production then preview document will be standard template file
	 * 
	 */
//	@Test(groups = { "regression" }, priority = 24)
	public void verifyPDFGeneration() throws Exception{
		
		baseClass.stepInfo("RPMXCON_56110 Production- Sprint 06");
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		
		//Pre-requisites
		//create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.CreateTagwithClassification(tagname, "Privileged");
		
		//search for documents and adding to bulk folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		
		//verify that pdf is genarated
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSection(tagname, Input.tagNameTechnical);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.getPreviewprod().waitAndClick(10);
		baseClass.passedStep("Standard production template should be displayed as PDF is selected in production.");
		
		
		//To delete tag and folder 
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();
	}
	
	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49113
	 * @Description:To verify that the value of Number of Natives on Production-Summary tab if Native component is selected and document is redacted
	 * 
	 */
//	@Test(groups = { "regression" }, priority = 26)
	public void verifyNatives() throws Exception{
		
		baseClass.stepInfo("RPMXCON_49113 Production- Sprint 06");
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		
		//Pre-requisites
		//create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.CreateTagwithClassification(tagname, "Privileged");
		
		//search for documents and adding to bulk folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		
		//verify that pdf is genarated
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionwithBurnRedaction(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		
		baseClass.waitForElement(page.getNumber_of_natives_count());
		if(page.getNumber_of_natives_count().isElementPresent())
		{
		baseClass.passedStep("verified that the value of Number of Natives on Production-Summary tab if Native component is selected and document is redacted");
		}
		
		//To delete tag and folder 
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();
	}
	
	
	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48317
	 * @Description:To verify the Production for Audio files which include redaction from beginning, middle and end of the audio file
	 * 
	 */
//	@Test(groups = { "regression" }, priority = 25)
	public void verifyAudioFilesWithRedactedDocuments() throws InterruptedException {

		baseClass.stepInfo("RPMXCON_48317 Production- Sprint 06");
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		
		// Pre-requisites
		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		sessionSearch = new SessionSearch(driver);
		sessionSearch.audioSearch("Morning", "International English");
		sessionSearch.bulkFolderExisting(foldername);

		sessionSearch.ViewInDocView();

		docViewPage = new DocViewPage(driver);
		docViewPage.addAudioRedaction();

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.advancedProductionComponentsMP3WithBurnReductionTag();
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerate(prefixID, suffixID, foldername, productionname);
		baseClass.passedStep("verified Audio files by  redacting them in beginning,middle and end");
		
		//To delete tag and folder 
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		loginPage.logout();	
}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56135
	 * @Description:Verify that for the saved template under MP3 File component- Redaction Tags should be disabled
	 * 
	 */
//	@Test(groups = { "regression" }, priority = 28)
	public void verifyMp3ComponentRedactionTag() throws Exception{
		
	UtilityLog.info(Input.prodPath);
	baseClass.stepInfo("RPMXCON_56135 Production- Sprint 06");
	loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
	ProductionPage page = new ProductionPage(driver);
	productionname = "p" + Utility.dynamicNameAppender();
	TempName = "T" + Utility.dynamicNameAppender();
	page.selectingDefaultSecurityGroup();
	page.addANewProduction(productionname);
	page.fillingDATSection();
	driver.waitForPageToBeReady();
	page.advancedProductionComponentsMP3WithBurnReductionTag();
	page.getMarkCompleteLink().waitAndClick(10);
	page.getProductionHomePage().waitAndClick(10);
	page.getprod_ActionButton_Reusable(productionname).waitAndClick(5);
	page.getprod_Action_SaveTemplate_Reusable(productionname).waitAndClick(5);
	driver.waitForPageToBeReady();
	page.saveTemplate(TempName);
	page.getManageTemplates().waitAndClick(10);
	page.manageTemplateLastPage().waitAndClick(10);
	driver.scrollingToBottomofAPage();
	page.getViewTemplate(TempName).waitAndClick(10);
	page.viewTempProductionNext();
	page.mp3DisableCheck();
	baseClass.passedStep("Verified saved template under MP3 File component- Redaction Tags is disabled");
	loginPage.logout();

	}
	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48495
	 * @Description:To verify that if annotation layer option is selected and audio document is redacted then native should not produced
	 * 
	 */
//	@Test(groups = { "regression" }, priority = 27)
	public void verifyAudioFilesWithAnnotationLayerOption() throws InterruptedException {

		baseClass.stepInfo("RPMXCON_48495 Production- Sprint 06");
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		
		// Pre-requisites
		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.CreateTagwithClassification(tagname, "Privileged");

		sessionSearch = new SessionSearch(driver);
		sessionSearch.audioSearch("Morning", "International English");
		sessionSearch.bulkFolderExisting(foldername);

		sessionSearch.ViewInDocView();

		docViewPage = new DocViewPage(driver);
		docViewPage.addAudioRedaction();

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname, Input.tagNameTechnical);
		page.fillingMP3();
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerate(prefixID, suffixID, foldername, productionname);
		baseClass.passedStep("verified  annotation layer option is selected and audio document is redacted then native should not produced");
		
		//To delete tag and folder 
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		loginPage.logout();	
}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48025
	 * @Description:To Verify Redaction Style in PDF & TIFF Section "White with Black font" is not breaking the existing "Black with white font" option and its functionality.
	 */
	
//	@Test(groups = { "regression" }, priority = 30)
	public void toggleBurnOnAndReductionStyle() throws Exception{
		
		UtilityLog.info(Input.prodPath);
		baseClass.stepInfo("RPMXCON_48025 Production- Sprint 06");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		
		//Pre-requisites
		//create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");
		
		//search for redacted documents and adding to bulk folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		
		//verify 
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionwithFontColour(tagname,"White with black font");
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname);
		
		//To delete tag and folder 
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		
		baseClass.stepInfo("Burn reduction with white with black text enabled");
	}	
	
	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48382
	 * @Description:To verify that if Redacted document is selected and Burn Redaction is disabled then Native should be generated
	 */
	
//	@Test(groups = { "regression" }, priority = 29)
	public void verfiyTIFFwithoutBurnRedactionTag() throws Exception{
		
		UtilityLog.info(Input.prodPath);
		baseClass.stepInfo("RPMXCON_48382 Production- Sprint 06");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		
		//Pre-requisites
		//create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");
		
		//search for redacted documents and adding to bulk folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		
		//verify pdf genarate with DAT NATIVE PDF and TIFF
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname,Input.tagNameTechnical);
		page.burnRedactionToggleDisableCheck();
		page.fillingTextSection();
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname);
		baseClass.passedStep("verify pdf in DAT NATIVE PDF and TIFF file");
		
		//To delete tag and folder 
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		
		loginPage.logout();
	}
	
	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49044
	 * @Description:To verify that if Redacted document is selected and Burn Redaction is disabled then Native should be generated
	 */
	@Test(groups = { "regression" }, priority = 31)
	public void generatetDocumentswithMultipleBrandingTags() throws Exception {
	UtilityLog.info(Input.prodPath);
	baseClass.stepInfo("RPMXCON-49044 -Production Sprint 06");
	loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
	foldername = "FolderProd" + Utility.dynamicNameAppender();
	String tagname1 = "Tag1" + Utility.dynamicNameAppender();
	String tagname2 = "Tag2" + Utility.dynamicNameAppender();
	String testData1 = Input.testData1;
	
	//Pre-requisites
	//create tag and folder
	TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.CreateTagwithClassification(tagname1, "Privileged");
	tagsAndFolderPage.CreateTagwithClassification(tagname2, "Technical Issue");

	//search for redacted documents and adding to bulk folder
	SessionSearch sessionSearch = new SessionSearch(driver);
	sessionSearch = new SessionSearch(driver);
	sessionSearch.basicContentSearch(testData1);
	sessionSearch.bulkTagExisting(tagname1);
	sessionSearch.bulkTagExisting(tagname2);


	//document with multibranding
	ProductionPage page = new ProductionPage(driver);
	productionname = "p" + Utility.dynamicNameAppender();
	page.addANewProduction(productionname);
	page.fillingDATSection();
	page.fillingNativeSection();
	page.specifyBrandingInTiffSection(tagname1,tagname2,"Testing");
	page.fillingTextSection();
	page.navigateToNextSection();
	page.fillingNumberingAndSortingPage(prefixID, suffixID);
	page.navigateToNextSection();
	page.fillingDocumentSelectionPageWithTag(tagname1,tagname2);
	page.navigateToNextSection();
	page.fillingPrivGuardPage();
	page.fillingProductionLocationPageAndPassingText(productionname);
	page.navigateToNextSection();
	page.fillingSummaryAndPreview();
	page.fillingGeneratePageWithContinueGenerationPopup();
	baseClass.passedStep("Verified in Productions, the Documents with Multiple Branding Tags count on pre-gen checks page is showing correct count");

	//delete tags and folders
//	tagsAndFolderPage=new TagsAndFoldersPage(driver);
//	tagsAndFolderPage.DeleteTagWithClassification(tagname1, "Default Security Group");
//	tagsAndFolderPage.DeleteTagWithClassification(tagname2, "Default Security Group");

	loginPage.logout();
	}
	
	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {

		loginPage=new LoginPage(driver);
		Reporter.setCurrentTestResult(result);
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
			try { // if any tc got failed and does not logout!
				loginPage.logout();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		UtilityLog.info("Executed :" + result.getMethod().getMethodName());
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			loginPage.closeBrowser();
		} finally {
			loginPage.clearBrowserCache();
		}
	}

}
