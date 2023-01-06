
package sightline.productions;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.concurrent.Callable;

import org.openqa.selenium.interactions.Actions;
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
import pageFactory.DocExplorerPage;
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

public class Production_Phase1_Regression3 {

	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	ProductionPage productionPage;
	TagsAndFoldersPage tagsAndFolderPage;
	SessionSearch sessionSearch;
	DocListPage docPage;
	DocViewPage docViewPage;
	SoftAssert softAssertion;
	String exportname;
	String prefixID;
	String suffixID;
	String foldername;
	String tagname;
	String productionname;
	String TempName;
	String templateName;

	@BeforeMethod(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		Input input = new Input();
		input.loadEnvConfig();
		base = new BaseClass(driver);
		driver = new Driver();
		loginPage = new LoginPage(driver);
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
	 * @author Indium-Baskar date: 29/8/2021 Modified date: N/A Modified by: Baskar
	 * @Description : Verify that Preivew documents should be display Standard
	 *              Template along with Slip sheets
	 */

	@Test(description = "RPMXCON-56117", enabled = true, groups = { "regression" })
	public void verifySlipSheetDisplayed() throws InterruptedException {
		base.stepInfo("Test case Id: RPMXCON-56117- Production Sprint 02");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.tiffBrandingSelection(tagname);
		page.tiffPrivilegeDocumentSelection(tagname);
		page.slipSheetToggleEnable();
		page.availableFieldSelection("BatesNumber");
		page.fillingTextSection();
		page.navigateToNextSection();
		base.passedStep("Production components completed and navigated to numbering and sorting page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.viewingPreviewButtonInSummaryTab();
		page.verifyContentInPDf(prefixID, suffixID);
		driver.waitForPageToBeReady();
		driver.getWebDriver().navigate().back();
		driver.getWebDriver().navigate().refresh();

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);

		loginPage.logout();
	}

	/**
	 * @author Indium-Baskar date: 29/8/2021 Modified date: N/A Modified by: Baskar
	 * @Description : Verify that if user selects only DAT in production then
	 *              Preview button is not available on 'Summary and Preview' tab
	 */

	@Test(description = "RPMXCON-56116", enabled = true, groups = { "regression" })
	public void verifyPreviewButtonShouldNotDisplayWhileDatOnlySelection() throws InterruptedException {
		base.stepInfo("Test case Id: RPMXCON-56116- Production Sprint 02");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		base.passedStep("Production components completed and navigated to numbering and sorting page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.viewingPreviewButtonInSummaryTab();

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Indium-Baskar date: 29/8/2021 Modified date: N/A Modified by: Baskar
	 * @Description : Verify that if user selects the DAT and MP3 option in
	 *              production then Preview button is not available on 'Summary and
	 *              Preview' tab
	 */

	@Test(description = "RPMXCON-56115", enabled = true, groups = { "regression" })
	public void verifyPreviewButtonShouldNotDisplayWhileDatAndMp3OnlySelection() throws InterruptedException {
		base.stepInfo("Test case Id: RPMXCON-56115- Production Sprint 02");

		UtilityLog.info(Input.prodPath);
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.advancedProductionComponentsMP3();
		page.navigateToNextSection();
		base.passedStep("Production components completed and navigated to numbering and sorting page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.afterCompletingNavigatingToNextPage();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		base.passedStep("Preview button is not displayed while selecting DAT and MP3 Files only selection");
		page.viewingPreviewButtonInSummaryTab();

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Indium-Baskar date: 1/9/2021 Modified date: N/A Modified by: Baskar
	 * @Description : Verify that configuration format should applied for OCRed text
	 *              files
	 */

	@Test(description = "RPMXCON-56080", enabled = true, groups = { "regression" })
	public void verifyConfigurationFormat() throws InterruptedException {
		base.stepInfo("Test case Id: RPMXCON-56080- Production Sprint 02");

		UtilityLog.info(Input.prodPath);
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.tiffBrandingSelection(tagname);
		page.tiffPrivilegeDocumentSelection(tagname);
		page.fillingTextSection();
		page.selectFormatInTextFile();
		page.navigateToNextSection();
		base.passedStep("Production components completed and navigated to numbering and sorting page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		base.stepInfo("Completed summary and preview tab");
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.passedStep("OCRed text files verified according to ansi text configuration");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Indium-Baskar date: 1/9/2021 Modified date: N/A Modified by: Baskar
	 * @Description : Verify that 'Page level' option should not displays on
	 *              Production-Text components
	 */

	@Test(description = "RPMXCON-56072", enabled = true, groups = { "regression" })
	public void verifyingPageLevelOptionsOnTextComponents() throws InterruptedException {
		base.stepInfo("Test case Id:RPMXCON-56072- Production Sprint 02");
		UtilityLog.info(Input.prodPath);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		driver.scrollingToBottomofAPage();
		page.fillingTextSection();

		if (page.getPageLevelTextComponent().isElementAvailable(5)) {
			base.failedStep("Page level options displayed in production component when clicking the  Text tab");
		} else {
			base.passedStep("Page level options not displayed in production component when clicking the  Text tab");
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
	@Test(description = "RPMXCON-56147", enabled = true, groups = { "regression" })
	public void verifyProductionForPDFWithNativelyPlaceholder() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-56147- Production Sprint 05");

		UtilityLog.info(Input.prodPath);
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production using dat,native and enable natively placeholder and
		// disable burn redactions
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		productionname = "p" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSectionwithNativelyPlaceholder(tagname);
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);
		base.passedStep(
				"Verified that Production is generated with 'Natively Produced Documents' Placeholder for the problem document");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
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
	@Test(description = "RPMXCON-56152", enabled = true, groups = { " regression" })
	public void verifyProductionForNativelyPlaceholderAndRedactionNotEnabled() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-56152- Production Sprint 05");

		UtilityLog.info(Input.prodPath);
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		sessionSearch = new SessionSearch(driver);
		// testData string contains redacted documents as per Pre-requisites
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production using dat,native and enable natively placeholder and
		// disable burn redactions
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		productionname = "p" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionwithNativelyPlaceholder(tagname);
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);
		base.passedStep(
				"Verified that Production generate successfully for redacted document if user select TIFF Or PDF with Naitvely Placeholder and Redaction is not enabled.");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
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
	 * @throws Exception check
	 */
	@Test(description = "RPMXCON-56003,RPMXCON-56004", enabled = true, groups = { " regression" })
	public void verifySharableLinks() throws Exception {
		base.stepInfo("Test case Id: RPMXCON-56003- Production Sprint 05");
		base.stepInfo("Test case Id: RPMXCON-56004- Production Sprint 05");

		UtilityLog.info(Input.prodPath);
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

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		productionname = "p" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionwithFontColour(tagname, "Black with white font");
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
		page.selectDownloadInGeneratePage();
		base.passedStep("Verified the shareable links and a password is available on 'Shareable Link' window");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON_56137
	 * @Description:Verify that Count displays correctly on Priv Guard if child
	 *                     documents is assigned to Priv Tag
	 */
	@Test(description = "RPMXCON-56137", enabled = true, groups = { " regression" })
	public void verifyPrivGuardBySelectingChildDocuments() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-56137- Production Sprint 05");
		UtilityLog.info(Input.prodPath);

		tagname = "AATag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.telecaSearchString);
		sessionSearch.ViewInDocList();

		docPage = new DocListPage(driver);
		docPage.selectingParentDocument();
		driver.scrollPageToTop();
		docPage.bulkTagExistingFromDoclist(tagname);

		// create production
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		productionname = "p" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFWithDisablePrivilegdedDocs();
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingSelectDocumentUsingTags(tagname);
		page.navigateToNextSection();
		page.priviledgeDocCheck(true, tagname);
		page.fillingPrivGuardPage();
		driver.waitForPageToBeReady();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.passedStep(
				"Verified that Count displays correctly on Priv Guard if child documents is assigned to Priv Tag");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON_56121
	 * @Description:Verify that updated redaction text is displays on generated
	 *                     Tiff/PDF for orphan redactions document
	 */
	@Test(description = "RPMXCON-56121", enabled = true, groups = { " regression" })
	public void orphanRedactionsInSelectedDocument() throws InterruptedException {

		base.stepInfo("Test case Id: RPMXCON-56121- Production Sprint 05");

		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.technicalIssue);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		sessionSearch = new SessionSearch(driver);
		// passing orphan redacted DOCID as string
		sessionSearch.basicContentSearch(Input.orphanDocument);
		sessionSearch.bulkFolderExisting(foldername);

		// create production using dat,native and tiff for redaction
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		productionname = "p" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFWithBurnRedactionAndSelectingOneTag();
		page.fillingTextSection();
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);
		base.passedStep(
				"Verified that updated redaction text is displays on generated Tiff/PDF for orphan redactions document");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON_55990
	 * @Description:Verify that if Producion is UnCommitted then 'Post Generation QC
	 *                     Checks' status should be display on Production progress
	 *                     status bar
	 */

	@Test(description = "RPMXCON-55990", enabled = true, groups = { " regression" })
	public void AssertionOnUnCommitInQCPage() throws Exception {
		base.stepInfo("Test case Id: RPMXCON-55990- Production Sprint 05");

		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname, Input.tagNamePrev);
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
		page.AssertionUnCommitInQCPage();
		base.waitTime(3);
//		page.verifyProductionStatusInGenPage("Post-Generation QC checks Complete");
		page.VerifyinginProductionQCMessage();
		base.passedStep(
				"Verify that if Producion is UnCommitted then 'Post Generation QC Checks' status should be display on Production progress status bar");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON_55971.
	 * @Description:Verify that Production status displays in Draft with blank
	 *                     progress bar on Tile View TESTCASE No: RPMXCON_55972
	 * @Description:Verify that Production status displays as Draft on Generation
	 *                     Page check
	 */

	@Test(description = "RPMXCON-55971,RPMXCON-55972", enabled = true, groups = { " regression" })
	public void ViewDraftAssertionInGeneratePage() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-55971- Production Sprint 05");
		base.stepInfo("Test case Id: RPMXCON-55972- Production Sprint 05");
		UtilityLog.info(Input.prodPath);
		String testData1 = Input.testData1;
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();

		// To verify prod generation failed in progress bar

		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionwithFontColour(tagname, "Black with white font");
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
		page.DraftAssertionInGeneratePage();
		base.passedStep(" Production status displays as Draft on Generation Page");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/*
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 * No:RPMXCON_58594
	 * 
	 * @Description:Verify the name of load files should be used the name of the
	 * Production TESTCASE No:RPMXCON_58592 Description:Verify the load files should
	 * be created under the mentioned default directory in the production
	 * configuration check
	 */
	@Test(description = "RPMXCON-58592,RPMXCON-58594", enabled = true, groups = { " regression" })
	public void passProductionNameAsLoadFilesName() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-58592 Production - Sprint 05");
		base.stepInfo("RPMXCON-58594 Production - Sprint 05");
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production and pass production name as load files name
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		productionname = "p" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		driver.waitForPageToBeReady();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname, Input.tagNamePrev);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);
		base.passedStep("passed ProductionName As LoadFiles Name.");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/*
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 * No:RPMXCON_56080
	 * 
	 * @Description:Verify that configuration format should applied for OCRed text
	 * files
	 */
	@Test(description = "RPMXCON-56080", enabled = true, groups = { " regression" })
	public void verifyFormatOCRedTextFiles() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56080 Production - Sprint 05");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create production and pass production name as load files name
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		productionname = "p" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname, Input.tagNamePrev);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);
		base.passedStep("Verify that configuration format should applied for OCRed text files");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-55987
	 * @Description:Verify that after Post Generation is completed, it will displays
	 *                     status on Production Progress bar Tile View as 'Post
	 *                     Generation QC Checks'
	 */

	@Test(description = "RPMXCON-55987", enabled = true, groups = { " regression" })
	public void verifyPostGenInTileView() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-55987- Production Sprint 05");
		UtilityLog.info(Input.prodPath);

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

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname, Input.tagNamePrev);
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
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();

		// Go To Production Home Page
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();

		// To filter In progress state Production
		page.prodGenerationInProgressStatus(productionname);

		// verify Production in Post-Gen QC Checks Complete status
		if (page.gettxtPostGenCompleted().Displayed()) {
			String statusText = page.gettxtPostGenCompleted().getText();
			System.out.println(statusText);
			base.passedStep(
					"Verified that after Post Generation is completed, it will displays status on Production Progress bar Tile View as 'Post Generation QC Checks'");
		}

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @Author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56165
	 * @throws InterruptedException
	 * @Description:To Verify that End-to-end Production Export functionality is
	 *                 working properly with TIFF files(Production Export).
	 */
	@Test(description = "RPMXCON-56165", enabled = true, groups = { " regression" })
	public void createExport() throws InterruptedException {

		base.stepInfo("Test case Id: RPMXCON-56165- Production Sprint 05");

		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String tagNameTechnical = Input.tagNameTechnical;
		String testData1 = Input.testData1;
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

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
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommitandDownload();

		// To delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
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
	 *                     redaction tags check
	 */

	@Test(description = "RPMXCON-56122,RPMXCON-56123", enabled = true, groups = { " regression" })
	public void generatingTheProductionAfterBurnRedaction() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-56122- Production Sprint 05");
		base.stepInfo("Test case Id: RPMXCON-56123- Production Sprint 05");

		UtilityLog.info(Input.prodPath);

		String Redactiontag1;

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
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
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFFSectionWithRedactionTags(Redactiontag1);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);
		base.passedStep("production is generated successfully when redaction tag is selected in burn redaction");

		// To delete tag and folder created
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
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

	@Test(description = "RPMXCON-56124", enabled = true, groups = { " regression" })

	public void tiffSectionLeftBranding() throws Exception {
		base.stepInfo("Test case Id: RPMXCON-56124- Production Sprint 05");

		UtilityLog.info(Input.prodPath);
		base.stepInfo(
				"#### Verify that if Tag is already specified with Right Branding then that Tag should not be available. ####");

		String tagname = "tag" + Utility.dynamicNameAppender();
		String productionname = "production" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		base.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		ProductionPage page = new ProductionPage(driver);
		base.stepInfo("Add New Production");
		page.addANewProduction(productionname);
		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();
		base.stepInfo("Filling Native Section");
		page.fillingNativeSection();
		base.stepInfo("Verify The Tag On Left Branding");
		page.verifyTheTagOnLeftBranding(tagname, "testing");
		base.stepInfo("filling Text Section");
		page.fillingTextSection();
		base.stepInfo("Click Component Mark Complete And Incomplete");
		page.clickComponentMarkCompleteAndIncomplete();
		base.stepInfo("Again Selecting Left Header Branding");
		page.againSelectingLeftHeaderBranding(tagname);
		base.stepInfo("verifying tag selected in the  specify branding at Left Header branding");

		// To delete tag and folder created
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @Author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         NO:RPMXCON_49242 Description:To verify that Project Admin can modify
	 *         the populated the bates number fields and production should generated
	 *         with modified bates number
	 */
	@Test(description = "RPMXCON-49242", enabled = true, groups = { " regression" })
	public void verificationOnNumberingAndSortingPage() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-49242- Production Sprint 05");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFWithSelectingBrandingSelectionTags(tagname);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);

		// To delete tag and folder created
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56150
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description:Verify that for Native section should be displayed message which
	 *                     will inform the user about the privileged and redacted
	 *                     docs from export
	 * 
	 */
	@Test(description = "RPMXCON-56150", enabled = true, groups = { " regression" })
	public void verifyNativeSectionDisplayForDocumentsFromExport() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-56150- Production Sprint 06");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		sessionSearch = new SessionSearch(driver);
		// testData string contains redacted documents as per Pre-requisites
		sessionSearch.basicContentSearch(Input.testData1);
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
		page.fillingTIFFSection(tagname, Input.tagNameTechnical);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(exportname);
		page.navigateToNextSection();
		page.viewingToolTipInSummaryAndPreview();
		base.passedStep(
				"Verified that for Native section should be displayed message which will inform the user about the privileged and redacted docs from export");

		// To delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49969
	 * @Description:Verify that Production should generate only PDF with Redaction
	 *                     text
	 * 
	 */
	@Test(description = "RPMXCON-49969", enabled = true, groups = { " regression" })
	public void verifyProductionWithPDFRedaction() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-49969- Production Sprint 06");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		sessionSearch = new SessionSearch(driver);
		// testData string contains redacted documents as per Pre-requisites
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create Production
		productionname = "P" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFForRedaction();
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);
		base.passedStep("Verified that Production should generate only PDF with Redaction text");

		// To delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49134
	 * @Description:To verify that when text is exported for Exception file then it
	 *                 should export the text with the Placeholder
	 * 
	 */
	@Test(description = "RPMXCON-49134", enabled = true, groups = { " regression" })
	public void verifyTextExportWithPlaceholder() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-49134- Production Sprint 06");

		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		UtilityLog.info(foldername);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.technicalIssue);
		UtilityLog.info(tagname);

		sessionSearch = new SessionSearch(driver);
		// testData string contains redacted documents as per Pre-requisites
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// create Production
		productionname = "P" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTiffSectionTechIssueWithEnteringText(Input.searchString4, tagname);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);
		base.passedStep(
				"verify that when text is exported for Exception file then it should export the text with the Placeholder");

		// To delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49856
	 * @Description:Verify that Production should generated successfully and
	 *                     PDF/TIFF should produced with Comments/Signature
	 * 
	 */
	@Test(description = "RPMXCON-49856", enabled = true, groups = { " regression" })
	public void verifyTIFFWithSignature() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-49856- Production Sprint 06");

		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		UtilityLog.info(foldername);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		UtilityLog.info(tagname);

		sessionSearch = new SessionSearch(driver);
		// testData string contains redacted documents as per Pre-requisites
		sessionSearch.basicContentSearch(Input.searchString20Docs);
		sessionSearch.bulkFolderExisting(foldername);

		// create Production
		productionname = "P" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		driver.scrollingToBottomofAPage();
		page.fillingTIFFSection(tagname, Input.tagNameTechnical);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);
		base.passedStep(
				"Verified that Production should generated successfully and PDF/TIFF should produced with Comments/Signature");

		// To delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56110
	 * @Description:Verify that if user selects the 'Genrate PDF' option in
	 *                     production then preview document will be standard
	 *                     template file
	 * 
	 */
	@Test(description = "RPMXCON-56110", enabled = true, groups = { " regression" })
	public void verifyPDFGeneration() throws Exception {

		base.stepInfo("RPMXCON_56110 Production- Sprint 06");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for documents and adding to bulk folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// verify that pdf is genarated
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSection(tagname, Input.tagNameTechnical);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.getPreviewprod().waitAndClick(10);
		base.passedStep("Standard production template should be displayed as PDF is selected in production.");

		// To delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49113
	 * @Description:To verify that the value of Number of Natives on
	 *                 Production-Summary tab if Native component is selected and
	 *                 document is redacted
	 * 
	 */
	@Test(description = "RPMXCON-49334", enabled = true, groups = { " regression" })
	public void verifyNatives() throws Exception {

		base.stepInfo("RPMXCON_49334 Production- Sprint 06");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for documents and adding to bulk folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// verify that pdf is genarated
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionwithBurnRedaction(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();

		base.waitForElement(page.getNumber_of_natives_count());
		if (page.getNumber_of_natives_count().isElementPresent()) {
			base.passedStep(
					"verified that the value of Number of Natives on Production-Summary tab if Native component is selected and document is redacted");
		}

		// To delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48317
	 * @Description:To verify the Production for Audio files which include redaction
	 *                 from beginning, middle and end of the audio file
	 * 
	 */
	@Test(description = "RPMXCON-48317", enabled = true, groups = { " regression" })
	public void verifyAudioFilesWithRedactedDocuments() throws InterruptedException {

		base.stepInfo("RPMXCON_48317 Production- Sprint 06");
		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		sessionSearch = new SessionSearch(driver);
		sessionSearch.audioSearch(Input.audioSearch, Input.audioLanguage);
		sessionSearch.bulkFolderExisting(foldername);

		sessionSearch.ViewInDocView();

		docViewPage = new DocViewPage(driver);
		docViewPage.addAudioRedaction();

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.advancedProductionComponentsMP3WithBurnReductionTag();
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);
		base.passedStep("verified Audio files by  redacting them in beginning,middle and end");

		// To delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		loginPage.logout();

	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56135
	 * @Description:Verify that for the saved template under MP3 File component-
	 *                     Redaction Tags should be disabled
	 * 
	 */
	@Test(description = "RPMXCON-56135", enabled = true, groups = { " regression" })
	public void verifyMp3ComponentRedactionTag() throws Exception {

		base.stepInfo("RPMXCON_56135 Production- Sprint 06");
		UtilityLog.info(Input.prodPath);

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
		driver.scrollingToBottomofAPage();

		if (page.getViewBtn(TempName).isElementAvailable(5)) {
			page.getViewBtn(TempName).waitAndClick(10);
			base.stepInfo("View option is displayed");
		} else {
			page.NavigateToLastTemplatePage();
			driver.waitForPageToBeReady();
			page.getViewBtn(TempName).waitAndClick(10);
			base.stepInfo("View option is displayed");
		}
		driver.waitForPageToBeReady();
		page.viewTempProductionNext();
		page.mp3DisableCheck();
		base.passedStep("Verified saved template under MP3 File component- Redaction Tags is disabled");
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48495
	 * @Description:To verify that if annotation layer option is selected and audio
	 *                 document is redacted then native should not produced
	 * 
	 */
	@Test(description = "RPMXCON-48495", enabled = true, groups = { " regression" })
	public void verifyAudioFilesWithAnnotationLayerOption() throws InterruptedException {

		base.stepInfo("RPMXCON_48495 Production- Sprint 06");
		UtilityLog.info(Input.prodPath);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		sessionSearch = new SessionSearch(driver);
		sessionSearch.audioSearch(Input.audioSearch, Input.language);
		sessionSearch.bulkFolderExisting(foldername);

		sessionSearch.ViewInDocView();

		docViewPage = new DocViewPage(driver);
		docViewPage.addAudioRedaction();

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname, Input.tagNameTechnical);
		page.fillingMP3();
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);
		base.passedStep(
				"verified  annotation layer option is selected and audio document is redacted then native should not produced");

		// To delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
		loginPage.logout();

	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48025
	 * @Description:To Verify Redaction Style in PDF & TIFF Section "White with
	 *                 Black font" is not breaking the existing "Black with white
	 *                 font" option and its functionality.
	 */

	@Test(description = "RPMXCON-48025", enabled = true, groups = { " regression" })
	public void toggleBurnOnAndReductionStyle() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON_48025 Production- Sprint 06");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for redacted documents and adding to bulk folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// verify
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionwithFontColour(tagname, "White with black font");
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);

		// To delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);

		base.stepInfo("Burn reduction with white with black text enabled");
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48382
	 * @Description:To verify that if Redacted document is selected and Burn
	 *                 Redaction is disabled then Native should be generated
	 */

	@Test(description = "RPMXCON-48382", enabled = true, groups = { " regression" })
	public void verfiyTIFFwithoutBurnRedactionTag() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON_48382 Production- Sprint 06");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for redacted documents and adding to bulk folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// verify pdf genarate with DAT NATIVE PDF and TIFF
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname, Input.tagNameTechnical);
		page.burnRedactionToggleDisableCheck();
		page.fillingTextSection();
		page.navigateToNextSection();
		page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,
				beginningBates);
		base.passedStep("verify pdf in DAT NATIVE PDF and TIFF file");

		// To delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49044
	 * @Description:To verify that if Redacted document is selected and Burn
	 *                 Redaction is disabled then Native should be generated
	 */
	@Test(description = "RPMXCON-49044", enabled = true, groups = { " regression" })
	public void generatetDocumentswithMultipleBrandingTags() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49044 -Production Sprint 06");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagname1 = "Tag1" + Utility.dynamicNameAppender();
		String tagname2 = "Tag2" + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname1, Input.tagNamePrev);
		tagsAndFolderPage.CreateTagwithClassification(tagname2, Input.technicalIssue);

		// search for redacted documents and adding to bulk folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkTagExisting(tagname1);
		sessionSearch.bulkTagExisting(tagname2);

		// document with multibranding
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.specifyBrandingInTiffSection(tagname1, tagname2, "Testing");
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPageWithTag(tagname1, tagname2);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.passedStep(
				"Verified in Productions, the Documents with Multiple Branding Tags count on pre-gen checks page is showing correct count");

		// delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(tagname1, Input.securityGroup);
		driver.waitForPageToBeReady();
		tagsAndFolderPage.DeleteTagWithClassification(tagname2, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description : To Verify Priv Guard Section on the self production wizard for
	 *              Document Match [RPMXCON-47900]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-47900", enabled = true, groups = { "regression" })
	public void verifyPrivGuardForDocMatch() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47900 -Production Sprint 10");
		base.stepInfo("To Verify Priv Guard Section on the self production wizard for Document Match");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		softAssertion = new SoftAssert();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.priviledgeDocCheck(false, Input.defaultRedactionTag);

		driver.waitForPageToBeReady();
		String docCount = page.navigatingToDocViewPage();
		System.out.println(docCount);

		softAssertion.assertNotEquals(docCount, "0");
		softAssertion.assertAll();

		// delete Folder
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : To Verify Document Selection Section on the self production
	 *              wizard For Tag [RPMXCON-47898]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-47898", enabled = true, groups = { "regression" })
	public void verifyDocSelectionForTag() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47898 -Production Sprint 10");
		base.stepInfo("To Verify Document Selection Section on the self production wizard For Tag");

		String tagname = "TAG" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		softAssertion = new SoftAssert();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		driver.waitForPageToBeReady();
		String docCount = page.navigatingToDocViewPage();
		System.out.println(docCount);
		softAssertion.assertNotEquals(docCount, "0");
		softAssertion.assertAll();

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * Author : Baskar date: NA Modified date:19/01/2021 Modified by: Baskar
	 * Description : Verify that read only custom metadata field value should be
	 * retained on click of saved stamp in context of security group
	 */
	@Test(description = "RPMXCON-49041", enabled = true, groups = { "regression" })
	public void verifyProductionCreationDateMarkComplete() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-49041");
		base.stepInfo("To verify that 'Production Creation Date' should displayed when it Mark Complete .");

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		// page.addANewProduction(productionname);
		page.getAddNewProductionbutton().waitAndClick(10);
		page.getProductionName().SendKeys(productionname);
		page.getMarkCompleteLink().waitAndClick(10);
		base.stepInfo("Click action using mark complete button");
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
		loginPage.logout();
	}

	/**
	 * @author sowndarya.velraj TESTCASE No:RPMXCON-47883
	 * @Description:To Verify DAT Section with various Options (Show Hide/Add
	 *                 Field/Advance Field Toggle and All Dropdown)
	 */
	@Test(description = "RPMXCON-47883", enabled = true, groups = { "regression" })
	public void verifyDATSectionWithVariousOption() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47883 -Production Sprint 12");
		base.stepInfo(
				"To Verify DAT Section with various Options (Show Hide/Add Field/Advance Field Toggle and All Dropdown)");
		tagname = "Tag" + Utility.dynamicNameAppender();

		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();

		page.FormatTextInDAT().isDisplayed();
		page.fieldDelimitersTextInDAT().isDisplayed();
		page.fieldMappingtTextInDAT().isDisplayed();
		page.dateFormatTextInDAT().isDisplayed();
		base.stepInfo(" 1>Format  2>Field Delimiters  3>Date Format  4>Field Mapping are displayed in DAT ");

		base.waitForElement(page.fieldSeperatorDdInDAT());
		page.fieldSeperatorDdInDAT().selectFromDropdown().selectByIndex(2);

		base.waitForElement(page.dateFormatDdInDAT());
		page.dateFormatDdInDAT().selectFromDropdown().selectByIndex(2);
		base.stepInfo("Selected  Format, Field Delimiters and Date Format other than default");

		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingSelectDocumentUsingTags(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		base.waitTime(3);
		String name = page.getProduction().getText().trim();
		System.out.println(name);
		String downloadsHome = "C:\\BatchPrintFiles\\downloads";
		page.isFileDownloaded(downloadsHome, name);

	}

	/**
	 * @author sowndarya.velraj TESTCASE No:RPMXCON-47990
	 * @Description:To Verify in Generated Production DAT will always have one row
	 *                 for each document
	 */
	@Test(description = "RPMXCON-47990", enabled = true, groups = { "regression" })
	public void verifyDATWithFilenameForProduction() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47990 -Production Sprint 12");
		base.stepInfo("To Verify in Generated Production DAT will always have one row for each document");
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.getAddFieldButtonInDAT().waitAndClick(10);

		base.waitForElement(page.getDAT_FieldClassification2());
		page.getDAT_FieldClassification2().selectFromDropdown().selectByVisibleText(Input.fldClassification);

		base.waitForElement(page.getDAT_SourceField2());
		page.getDAT_SourceField2().selectFromDropdown().selectByVisibleText(Input.docFileName);

		base.waitForElement(page.getDAT_DATField2());
		page.getDAT_DATField2().SendKeys(Input.docFileName + Utility.dynamicNameAppender());

		base.stepInfo("Filled DAT component with file name");

		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingSelectDocumentUsingTags(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
	}

	/**
	 * @author sowndarya.velraj TESTCASE No:RPMXCON-47944
	 * @Description:To Verify 'Preview' action prior to the actual production
	 *                 generation should include any branding specified in the
	 *                 configuration.
	 */
	@Test(description = "RPMXCON-47944", enabled = true, groups = { "regression" })
	public void verifyPDFPreview() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47944-Production Sprint 12");
		base.stepInfo(
				"To Verify 'Preview' action prior to the actual production generation should include any branding specified in the configuration.");
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionWithDefaultBrandingText(Input.tagNamePrev);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingSelectDocumentUsingTags(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.verifyContentInPDf(prefixID, suffixID);
		base.passedStep("Preview of the PDF displayed along with Branding   ");
		driver.waitForPageToBeReady();
		driver.getWebDriver().navigate().back();
		driver.getWebDriver().navigate().refresh();

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);

	}

	/**
	 * @Author sowndarya.velraj
	 * @Description :To Verify In Production for Translation, File Extn in
	 *              Translation LST & Translated document Produced [ RPMXCON-55680]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-55680", enabled = true, groups = { "regression" })
	public void verifyTranslation() throws Exception {

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		ProductionPage page = new ProductionPage(driver);

		String tagname = "TAG" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-55680 Production Sprint 12");
		base.stepInfo(
				"To Verify In Production for Translation, File Extn in Translation LST & Translated document Produced");

		// create tag and folder
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.technicalIssue);

		// search for folder
		sessionSearch.basicContentSearch(Input.translationDocumentId);
		sessionSearch.bulkTagExisting(tagname);

		// create production with DAT,Native,tiff
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTiffSectionDisablePrivilegedDocs();
		page.fillingTextSection();
		page.advancedProductionComponentsTranslations();
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

		base.waitTime(4);
		String name = page.getProduction().getText().trim();
		page.isFileDownloaded(Input.fileDownloadLocation, name);

		// Delete Tag and folder
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
	}

	/**
	 * @author sowndarya.velraj TESTCASE No:RPMXCON-48163
	 * @Description:To Verify on enabling Burn Redaction for MP3 Files, provides
	 *                 user options to select "All redactions in annotation layer"
	 *                 or "Individual Redaction".
	 */
	@Test(description = "RPMXCON-48163", enabled = true, groups = { "regression" })
	public void verifymp3ComponentWithRedaction() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48163 -Production Sprint 12");
		base.stepInfo(
				"To Verify on enabling Burn Redaction for MP3 Files, provides user options to select \"All redactions in annotation layer\" or \"Individual Redaction\".");
		tagname = "Tag" + Utility.dynamicNameAppender();
		templateName = "Template" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname);
		page.fillingMP3();
		base.stepInfo(
				"Selected MP3Files check box  Burn Redaction toggle should be ON and also Selected option as Annotation Layer  Select any redaction style");
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingSelectDocumentUsingTags(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
	}

	/**
	 * @author sowndarya.velraj TESTCASE No:RPMXCON-48550
	 * @Description:To verify that 'Bates Range' is displayed in Production Generate
	 *                 step
	 */
	@Test(description = "RPMXCON-48550", enabled = true, groups = { "regression" })
	public void verifyBatesRangeInGenerate() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48550 -Production Sprint 12");
		base.stepInfo("To verify that 'Bates Range' is displayed in Production Generate step");
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingSelectDocumentUsingTags(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		softAssertion = new SoftAssert();
		softAssertion.assertTrue(page.getProd_BatesRange().isDisplayed());
		base.passedStep("Bates Range is displayed in generate page");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
	}

	/**
	 * @author sowndarya.velraj TESTCASE No:RPMXCON-47988
	 * @Description:To verify Bates Number in generated production DAT and Load
	 *                 Files should be in Ascending Order
	 */
	@Test(description = "RPMXCON-47988", enabled = true, groups = { "regression" })
	public void verifyBatesNumberAndSorting() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47988-Production Sprint 12");
		base.stepInfo("To verify Bates Number in generated production DAT and Load Files should be in Ascending Order");
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);

		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingWithSortingByMetaData(prefixID, suffixID, beginningBates, Input.metaDataName,
				Input.masterDateText);
		page.navigateToNextSection();
		page.fillingSelectDocumentUsingTags(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author sowndarya.velraj TESTCASE No:RPMXCON-47989
	 * @Description:To Verify production generation using Custom Template.(Check for
	 *                 Priv in DAT component).
	 */
	@Test(description = "RPMXCON-47989", enabled = true, groups = { "regression" })
	public void verifyDATTemplate() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47989-Production Sprint 12");
		base.stepInfo("To Verify production generation using Custom Template.(Check for Priv in DAT component).");
		tagname = "Tag" + Utility.dynamicNameAppender();
		templateName = "Template" + Utility.dynamicNameAppender();

		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.getDATPrivledgedCheckbox().waitAndClick(10);
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		base.stepInfo("In DAT Selected fields with Priv check box  and  Selected TIFF add Priv Tag");
		page.navigateToProductionPage();
		driver.waitForPageToBeReady();
		page.getProductionFromHomepage(productionname).isDisplayed();
		page.saveProductionAsTemplateAndVerifyProductionComponentsInManageTemplateTab(productionname, templateName);
		page.nextButtonInTemplate().waitAndClick(10);
		page.productionComponentsInTemplate().isDisplayed();
		page.getDATChkBox().waitAndClick(10);
		page.getDATTab().waitAndClick(10);
		driver.waitForPageToBeReady();
		softAssertion = new SoftAssert();
		softAssertion.assertTrue(page.getDATPrivledgedCheckbox().Selected());
		base.passedStep("Verified production generation using Custom Template.(Check for Priv in DAT component)");
	}

	/**
	 * @author sowndarya.velraj TESTCASE No:RPMXCON-47899
	 * @Description:To Verify Document Selection Section on the self production
	 *                 wizard For Searches
	 */
	@Test(description = "RPMXCON-47899", enabled = true, groups = { "regression" })
	public void verifyDocumentSelectionOnSearches() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47899 -Production Sprint 12");
		base.stepInfo("To Verify Document Selection Section on the self production wizard For Searches");
		tagname = "Tag" + Utility.dynamicNameAppender();
		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);

		driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionSearch.advancedContentSearch(Input.testData1);
		sessionSearch.saveSearchAtAnyRootGroup(searchName, Input.shareSearchPA);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocuSelectionPage(searchName, null);
		base.waitForElement(page.getMarkCompleteLink());
		page.getMarkCompleteLink().waitAndClick(10);

		softAssertion = new SoftAssert();
		softAssertion.assertTrue(page.getDocumentSelectionLink().isDisplayed());
		base.passedStep("Total Document count is displayed");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
	}

	/**
	 * @Author sowndarya.velraj
	 * @Description :To verify that Family members having the same FamilyID must be
	 *              produced, if 'Do not produce natives of the parents of
	 *              privileged and redacted docs'' option is enabled[RPMXCON-49230]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-49230", enabled = true, groups = { "regression" })
	public void verifyNativeWithFamilyMembers() throws Exception {

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		ProductionPage page = new ProductionPage(driver);
		SoftAssert softAssertion = new SoftAssert();

		String tagname = "TAG" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id:RPMXCON-49230 Production Sprint 12");
		base.stepInfo(
				"To verify that Family members having the same FamilyID must be produced, if 'Do not produce natives of the parents of privileged and redacted docs'' option is enabled");

		// create tag and folder
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		// search for folder
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		base.stepInfo(
				" Redacted documents are selected and preformed bulk tag action with classification as: privileged tag");

		base = new BaseClass(driver);
		base.selectproject();
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.telecaSearchString);
		sessionSearch.ViewInDocList();

		docPage = new DocListPage(driver);
		docPage.selectingParentDocument();
		driver.scrollPageToTop();
		docPage.bulkTagExistingFromDoclist(tagname);
		base.stepInfo(
				"Parent document is selected from doclist and preformed bulk tag action with classification as: privileged tag");

		base.stepInfo("Starting the production");
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		softAssertion.assertTrue(page.radioBtnInAdvancedNative_ForPrivRedactedDocs().Selected());
		base.passedStep("Do not produce natives of the parents of privileged and redacted docs is selected");
		page.fillingTIFFSection(tagname);
		base.stepInfo("Privileged Tag is selected in TIFF");
		page.fillingTextSection();
		page.advancedProductionComponentsTranslations();
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

		// Delete Tag and folder
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
	}

	/**
	 * @author sowndarya.velraj TESTCASE No:RPMXCON-47901
	 * @Description:To Verify Priv Guard Section on the self production wizard for
	 *                 Run Categorization
	 */
	@Test(description = "RPMXCON-47901", enabled = true, groups = { "regression" })
	public void verifyPrivGuardForRunCategorization() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47901 -Production Sprint 12");
		base.stepInfo("To Verify Priv Guard Section on the self production wizard for Run Categorization");
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingSelectDocumentUsingTags(tagname);
		page.navigateToNextSection();
		page.priviledgeDocCheck(true, tagname);
		page.runCategorizationBtn().waitAndClick(10);
		base.stepInfo("Redirected to Categorization page ");

		softAssertion = new SoftAssert();
		softAssertion.assertTrue(page.identifyByProductionGuardSource_radioBtn().isDisplayed());
		base.passedStep("Identify By Production Guard Source radio button is displayed");

		softAssertion.assertTrue(page.analyzeSelectProductionSets_radioBtn().isDisplayed());
		base.passedStep("Analyze Select Production Sets  radio button is displayed");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
	}

	/**
	 * @author Sowndarya.Velraj created on:02/03/22 TESTCASE No:RPMXCON-48039
	 * @Description:To Verify Removal of Priv Tag from a Document should get
	 *                 Produced in Production for Native
	 */
	@Test(description = "RPMXCON-48039", enabled = true, groups = { " regression" })
	public void verifyPrivilegedTagInNative() throws Exception {

		base.stepInfo("Test case Id RPMXCON-48039- Production Sprint 11");
		base.stepInfo("To Verify Removal of Priv Tag from a Document should get Produced in Production for Native");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String tagname2 = "tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.createNewTagwithClassification(tagname2, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		base.stepInfo("set of 6 Documents are Bulk Tagged as Privileged tag");

		sessionSearch.navigateToSessionSearchPageURL();
		base = new BaseClass(driver);
		base.selectproject();
		sessionSearch.basicContentSearch(Input.searchString5);
		driver.waitForPageToBeReady();
		sessionSearch.bulkTagExisting(tagname2);
		base.stepInfo("set of  Documents are Bulk Tagged as Tevhnical Issue tag");

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPageWithTag(tagname, tagname2);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		page.navigateToProductionPage();
		driver.Navigate().refresh();
		base.waitForElement(page.getProductionFromHomepage(productionname));
		page.getProductionFromHomepage(productionname).waitAndClick(10);
		page.getBckBtn().waitAndClick(10);
		for (int i = 0; i < 4; i++) {
			page.getBckBtn().waitAndClick(10);
		}
		driver.waitForPageToBeReady();
		base.stepInfo("Navigating back to Document selection page");
		page.getMarkIncompleteButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		page.fillingDocumentSelectionWithTag(tagname2);
		base.stepInfo("Removed Tags with native file");
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.passedStep("Removed Priv Tag from a Document should get Produced in Production for Native");
	}

	/**
	 * @author sowndarya.velraj TESTCASE No:RPMXCON-47984
	 * @Description:To Verify On Productions landing page, Count of productions in
	 *                 the tile view should match with grid view,
	 */
	@Test(description = "RPMXCON-47984", enabled = true, groups = { "regression" })
	public void verifyProductionCountInTileAndGrid() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47984 -Production Sprint 12");
		base.stepInfo(
				"To Verify On Productions landing page, Count of productions in the tile view should match with grid view,");

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		String tileViewCount = page.getProductionItemsTileItems().getText();
		page.getGridView().waitAndClick(10);
		String gridViewCount = page.gridAndTileViewProdCount().getText();
		softAssertion = new SoftAssert();
		softAssertion.assertEquals(tileViewCount, gridViewCount);
		softAssertion.assertAll();
		base.passedStep(
				"Verified On Productions landing page, Count of productions in the tile view should match with grid view");

	}

	/**
	 * @Author sowndarya.velraj
	 * @Description :Verify that for the saved template 'File Type Options' under
	 *              Native component should be disabled [ RPMXCON-56133]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-56133", enabled = true, groups = { "regression" })
	public void verifySavedTemplateForNative() throws Exception {

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		String templatename = "TEMPLATE" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-56133 Production Sprint 12");
		base.stepInfo(
				"Verify that for the saved template 'File Type Options' under Native component should be disabled");

		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.navigateToNextSection();
		page.navigateToProductionPage();
		page.saveProductionAsTemplateAndVerifyProductionComponentsInManageTemplateTab(productionname, templatename);
		base.waitForElement(page.nextButtonInTemplate());
		page.nextButtonInTemplate().waitAndClick(10);
		base.stepInfo("Navigating to Production components page in manage templates tab");
		driver.waitForPageToBeReady();

		page.getNativeChkBox().ScrollTo();
		page.getNativeChkBox().waitAndClick(10);
		page.getNativeTab().waitAndClick(10);
		if (page.getChkBoxNativeOthers().Selected()) {
			base.passedStep("Native File types are disabled for saved template");
		}
	}

	/**
	 * @author sowndarya.velraj TESTCASE No:RPMXCON-49059
	 * @Description:To verify that "Production Start Date" should present the date
	 *                 when the production was started
	 */
	@Test(description = "RPMXCON-49059", enabled = true, groups = { "regression" })
	public void verifyStartDateInGridView() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49059 -Production Sprint 12");
		base.stepInfo("To verify that Production Start Date should present the date when the production was started");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.searchString5);
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
		page.fillingGeneratePageWithContinueGenerationPopup();

		driver.waitForPageToBeReady();
		page.goToProductionGridView();
		int startDateIndex = base.getIndex(page.getGridWebTableHeader(), "START DATE");
		driver.waitForPageToBeReady();
		String startDate = page.getGridProdValues(productionname, startDateIndex).getText();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		String date1 = dateFormat.format(date);
		System.out.println("Current date " + date1);
		boolean flag = startDate.contains(date1);
		if (flag) {
			base.passedStep("Start date is displayed on production grid view");
			System.out.println("date visible");
		} else {
			base.failedStep("date is not contain in text");
			System.out.println("date not visible");
		}

		base.passedStep("To Verify On grid view of the productions the start date for a production in grid view.");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
	}

	/**
	 * @author sowndarya.velraj TESTCASE No:RPMXCON-55928
	 * @Description:Verify new text in 'Redactions' section in Tiff/PDF components
	 */
	@Test(description = "RPMXCON-55928", enabled = true, groups = { "regression" })
	public void verifyTextInRedaction() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-55928-Production Sprint 12");
		base.stepInfo("Verify new text in 'Redactions' section in Tiff/PDF components");
		tagname = "Tag" + Utility.dynamicNameAppender();

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionwithBurnRedaction();
		page.getClkLink_selectingRedactionTags().waitAndClick(10);
		softAssertion = new SoftAssert();
		softAssertion.assertTrue(page.redactedTextInRedaction().isDisplayed());
		softAssertion.assertAll();
		base.passedStep("New text is displayed in 'Redactions' section in Tiff/PDF components");
	}

	/**
	 * @author Sowndarya.Velraj created on:02/02/22 TESTCASE No:RPMXCON-46893
	 * @Description:To verify Production Generation for MP3 files Audio (redaction
	 *                 Applied).
	 */
	@Test(description = "RPMXCON-46893", enabled = true, groups = { " regression" })
	public void verifyMP3RedactionForProduction() throws Exception {

		base.stepInfo("Test case Id RPMXCON-46893- Production Sprint 11");
		base.stepInfo("To verify Production Generation for MP3 files Audio (redaction Applied).");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		driver.scrollingToBottomofAPage();
		page.advancedProductionComponentsMP3WithBurnReductionTag();
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

		// To delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
	}

	/**
	 * @Author Jeevitha
	 * @Description :To verify that Tiff/PDF should generate with 'Tech Issue Docs'
	 *              placeholdering even though Burn redactions and File group/tag
	 *              based placeholdering is exists in the document. [ RPMXCON-48343]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-48343", enabled = true, groups = { "regression" })
	public void verifyTiffWIthTechIssueDocs() throws Exception {

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		ProductionPage page = new ProductionPage(driver);

		String tagname = "TAG" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-48343 Production Sprint 11");
		base.stepInfo(
				"To verify that Tiff/PDF should generate with 'Tech Issue Docs' placeholdering even though Burn redactions and File group/tag based placeholdering is exists in the document.");

		// create tag and folder
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.technicalIssue);

		// search for folder
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		// create production with DAT,Native,tiff
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTiffSectionTechIssueMetaDataField(tagname);
		page.fillingNativeDocsPlaceholder(tagname);
		page.getClk_burnReductiontoggle().ScrollTo();
		page.getClk_burnReductiontoggle().waitAndClick(10);
		page.specifyRedactionTextAreaInBurnRedact(Input.defaultRedactionTag, Input.searchString4);
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

		base.waitTime(4);
		String name = page.getProduction().getText().trim();
		page.isFileDownloaded(Input.fileDownloadLocation, name);

		// Delete Tag and folder
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.deleteAllTags(tagname);
	}

	/**
	 * @author Sowndarya.Velraj created on:01/28/22 TESTCASE No:RPMXCON-48331
	 * @Description:To verify that the selected metadata is not displayed in DAT if
	 *                 the doc has at least one of the selected PRIV tags in PRIV
	 *                 placeholdering for Audio files
	 */
	@Test(description = "RPMXCON-47888", enabled = true, groups = { " regression" })
	public void verifyDATWithPrivilegedCheckboxForAudioFiles() throws Exception {

		base.stepInfo("Test case Id RPMXCON-47888- Production Sprint 11");
		base.stepInfo("To Verify in Priv Guard View in Doclist and DocView.");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.getDATPrivledgedCheckbox().waitAndClick(10);
		base.stepInfo("Privileged checkbox is selected in DAT component");
		page.advancedProductionComponentsMP3WithBurnReductionTag();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingSelectDocumentUsingTags(tagname);

		// To delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
	}

	/**
	 * @Author Jeevitha
	 * @Description : To verify that Native should be generated when PRIV
	 *              placeholdering and Burn Redactions are not enabled [
	 *              RPMXCON-48377]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-48377", enabled = true, groups = { "regression" })
	public void VerifyNativeWithPrivPlaceholder() throws Exception {

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		ProductionPage page = new ProductionPage(driver);

		String tagname = "TAG" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-48377 Production Sprint 11");
		base.stepInfo(
				"To verify that Native should be generated when PRIV placeholdering and Burn Redactions are not enabled");

		// create tag and folder
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		// create production with DAT,Native,tiff
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectPrivilegedTagAndEnterPlaceHolderValue(tagname, Input.searchString4);
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

		// Delete Tag and folder
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.deleteAllTags(tagname);
	}

	/**
	 * @Author Jeevitha
	 * @Description : To verify that the selected metadata is not displayed only
	 *              when the doc has at least one of the selected redaction tags in
	 *              Burn Redactions in PDF section [RPMXCON-48501]
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-48501", enabled = true, groups = { "regression" })
	public void verifySelectedMetadataNotDisplayedOnDocs() throws Exception {

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		ProductionPage page = new ProductionPage(driver);

		String folder = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-48501 Production Sprint 11");
		base.stepInfo(
				"To verify that the selected metadata is not displayed only when the doc has at least one of the selected redaction tags in Burn Redactions in PDF section");

		tagsAndFolderPage.CreateFolder(folder, Input.securityGroup);

		// search for folder
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(folder);

		// create production with DAT,Native,PDF with burn redaction
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		base.waitForElement(page.getDATRedactionsCBox());
		page.getDATRedactionsCBox().waitAndClick(10);
		page.fillingNativeSection();
		page.fillingPDFSectionwithBurnRedaction();
		driver.waitForPageToBeReady();
		page.getClkLink_selectingRedactionTags().ScrollTo();
		page.specifyRedactionTextAreaInBurnRedact(Input.defaultRedactionTag, Input.searchString4);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(folder);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		base.waitTime(4);
		String name = page.getProduction().getText().trim();
		page.isFileDownloaded(Input.fileDownloadLocation, name);

		// Delete Tag and folder
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(folder, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:01/28/22 TESTCASE
	 *         No:RPMXCON-47888,RPMXCON-47894
	 * @Description:To Verify in Priv Guard View in Doclist and DocView.
	 */
	@Test(description = "RPMXCON-47888,RPMXCON-47894", enabled = true, groups = { " regression" })
	public void verifyPrivGuardSectionViewInDoclistAndDocView() throws Exception {

		base.stepInfo("Test case Id RPMXCON-47888 & 47894- Production Sprint 11");
		base.stepInfo("To Verify in Priv Guard View in Doclist and DocView.");
		base.stepInfo("To Verify Priv Guard Section on the self production wizard");
		UtilityLog.info(Input.prodPath);

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// create tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		softAssertion = new SoftAssert();
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		int purehit = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		// create production with DAT,Native,PDF& ingested Text
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
		page.fillingSelectDocumentUsingTags(tagname);
		page.navigateToNextSection();
		page.priviledgeDocCheck(true, tagname);
		driver.waitForPageToBeReady();

		// checking matching documents with purehit
		String docCount = page.VerifyingDocListCountWithPrivGaurdCount();
		int matchingDocs = Integer.parseInt(docCount);
		softAssertion.assertEquals(purehit, matchingDocs);
		base.passedStep("Matching Document Count is : " + matchingDocs);

		// verify doclist total document with purehit
		docPage = new DocListPage(driver);
		String doclistCount = docPage.verifyingDocCount();
		int docListCount = Integer.parseInt(doclistCount);
		softAssertion.assertEquals(purehit, docListCount);
		base.passedStep("Total Document Count of  DocList : " + docListCount);

		// verify docview total document with purehit
		driver.waitForPageToBeReady();
		page.getDocView().waitAndClick(10);
		docViewPage = new DocViewPage(driver);
		int docviewCount = docViewPage.verifyingDocCount();
		softAssertion.assertEquals(purehit, docviewCount);
		base.passedStep("Total Document Count of  DocView : " + docviewCount);

		softAssertion.assertAll();
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
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
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
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
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

	@Test(description = "RPMXCON-56099", enabled = true, groups = { "regression" })
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
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON_56100
	 * @Description:Verify the error message for TIFF/PDF component when 'Enable
	 *                     natively produce placeholder without tag and file type'
	 */

	@Test(description = "RPMXCON-56100", enabled = true, groups = { "regression" })
	public void AssertionOnTiffSection() throws Exception {
		UtilityLog.info(Input.prodPath);

		base.stepInfo("Test case Id: RPMXCON-56100 -Production Sprint 02");
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.AssertionInTIFFSection();
		base.passedStep("Error Message Displayed");
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON_56112
	 * @Description:Verify that if user selects the 'Genrate TIFF' option in
	 *                     production then preview document should be displays with
	 *                     the bates number in branding section
	 */

	@Test(description = "RPMXCON-56112", enabled = true, groups = { "regression" })
	public void verifyTIFFWithBatesNUmber() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id: RPMXCON-56112 -production Sprint 03");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for folder
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// create production and verify preview tab button should display
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.getDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.tiffBrandingSelection(tagname);
		page.tiffPrivilegeDocumentSelection(tagname);
		page.slipSheetToggleEnable();
		page.availableFieldSelection("BatesNumber");
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.viewingPreviewButtonInSummaryTab();
		base.passedStep("Bates Number is Displayed");

		// To delete the created Tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @Author sowndarya.Velraj Created on:NA Modified by:NA TESTCASE
	 *         No:RPMXCON_56162
	 * @Description:Verify that count of redacted documents displays correct in the
	 *                     Summary & Preview tab when documents having orphan
	 *                     redactions and redaction un-released redaction tags
	 */
	@Test(description = "RPMXCON-56162", enabled = true, groups = { "regression" })
	public void selectingThreeRedactionDocs() throws Exception {
		String Redactiontag1;
		String Redactiontag2;
		String Redactiontag3;

		base.stepInfo("Test case Id: RPMXCON-56162-production Sprint 03");

		UtilityLog.info(Input.prodPath);
		Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();
		Redactiontag2 = "SecondRedactionTag" + Utility.dynamicNameAppender();
		Redactiontag3 = "ThirdRedactionTag" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();

		RedactionPage redactionpage = new RedactionPage(driver);

		redactionpage.selectDefaultSecurityGroup();
		driver.waitForPageToBeReady();
		redactionpage.manageRedactionTagsPage(Redactiontag1);
		System.out.println("First Redaction Tag is created" + Redactiontag1);

		redactionpage.selectDefaultSecurityGroup();
		driver.waitForPageToBeReady();
		redactionpage.manageRedactionTagsPage(Redactiontag2);
		System.out.println("second Redaction Tag is created" + Redactiontag2);

		redactionpage.selectDefaultSecurityGroup();
		driver.waitForPageToBeReady();
		redactionpage.manageRedactionTagsPage(Redactiontag3);
		System.out.println("Third Redaction Tag is created" + Redactiontag3);

//        manage-->security group--redaction tags-->select 3 tags move to right

		loginPage.logout();

		// Login as RMU
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		DocExplorerPage docExp = new DocExplorerPage(driver);
		docExp.documentSelectionIteration();
		docExp.docExpViewInDocView();

		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		// doc1
		docViewRedactions.selectDoc1();

		driver.waitForPageToBeReady();
		docViewRedactions.redactRectangleUsingOffset(10, 10, 100, 100);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag1);

		// d-2

		docViewRedactions.selectDoc2();
		driver.waitForPageToBeReady();
		docViewRedactions.redactRectangleUsingOffsetWithDoubleClick(20, 20, 60, 60);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag3);

		driver.waitForPageToBeReady();
		docViewRedactions.redactRectangleUsingOffsetWithDoubleClick(30, 30, 70, 50);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag2);

		// d-3

		driver.waitForPageToBeReady();
		docViewRedactions.selectDoc3();
		docViewRedactions.redactRectangleUsingOffsetWithDoubleClick(12, 12, 8, 8);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag2);

		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		SecurityGroupsPage securityGroupsPage = new SecurityGroupsPage(driver);
		securityGroupsPage.navigateToSecurityGropusPageURL();
		securityGroupsPage.unAssigningTheTagInRedaction(Redactiontag3);

		// create tags and folders

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// Adding folder to bulkfolder
		DocExplorerPage docExplorer = new DocExplorerPage(driver);
		docExplorer.documentSelectionIteration();
		docExplorer.bulkFolderExisting(foldername);

		// remaining
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTiffSectionBySelectingTwoRedactedTags(Redactiontag1, Redactiontag2);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.SummaryAndPreviewWithAssert();
		base.passedStep("Selected three documents and redacted them,then released to Default Security Group");

		// To delete the created Tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @Author sowndarya.Velraj Created on:NA Modified by:NA TESTCASE
	 *         No:RPMXCON_56108
	 * @Description:Verify the error message for MP3 component when 'Enable burn
	 *                     redaction without selecting redaction tag'
	 */
	@Test(description = "RPMXCON-56108", enabled = true, groups = { "regression" })
	public void verifyErrorMessageForMP3() throws Exception {
		String message = "You have chosen MP3 redactions but have not specified redaction tags. In the MP3 section, please specify"
				+ " the redaction tags for which you want redactions burned in the production.";
		base.stepInfo("Test case Id: RPMXCON-56108-production Sprint 04");
		UtilityLog.info(Input.prodPath);

		ProductionPage page = new ProductionPage(driver);
		page.navigateToProductionPage();
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.getAdvancedProductionComponent().WaitUntilPresent().ScrollTo();
		page.getAdvancedProductionComponent().Click();
		page.getMP3CheckBox().Click();
		page.getbtnMP3BurnRedactionTab().Click();
		page.getbtnMP3BurnRedaction().waitAndClick(5);
		page.getbtnMP3SelectRedactionTags().waitAndClick(5);
		driver.scrollPageToTop();
		page.getMarkCompleteLink().waitAndClick(5);

		try {
			Assert.assertTrue(true, message);
			base.passedStep("Error message is displayed for mp3 component");
		} catch (Exception e) {

			Assert.assertTrue(false, message);
			base.failedStep("Error message is not displayed for mp3 component");
		}
		loginPage.logout();
	}

	/**
	 * @Author sowndarya.Velraj Created on:NA Modified by:NA TESTCASE
	 *         No:RPMXCON_56106
	 * @Description:Verify the error message for TIFF/PDF component when 'Enable
	 *                     redaction without selecting redaction tag'
	 */
	@Test(description = "RPMXCON-56106", enabled = true, groups = { "regression" })
	public void verifyErrorMessageInSpecifyRedactionWithNoText() throws Exception {
		String message = "You have chosen TIFF/PDF redactions but have not specified redaction tags. In the TIFF/PDF section, "
				+ "please specify the redaction tags for which you want redactions burned in the production.";
		base.stepInfo("Test case Id: RPMXCON-56106-production Sprint 04");
		UtilityLog.info(Input.prodPath);
		ProductionPage page = new ProductionPage(driver);
		page.navigateToProductionPage();
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.getTIFFChkBox().waitAndClick(5);
		page.getTIFFTab().waitAndClick(5);
		page.getTIFF_EnableforPrivilegedDocs().ScrollTo();
		page.getTIFF_EnableforPrivilegedDocs().Enabled();
		page.getTIFF_EnableforPrivilegedDocs().waitAndClick(5);
		page.getClk_burnReductiontoggle().ScrollTo();
		page.getClk_burnReductiontoggle().waitAndClick(5);
		driver.scrollingToElementofAPage(page.getclklinkSpecifyRedactionText());
		base.clickButton(page.getclklinkSpecifyRedactionText());
		base.waitForElement(page.gettextRedactionPlaceHolder());
		page.gettextRedactionPlaceHolder().SendKeys("");
		page.getSelectCloseBtn().waitAndClick(10);
		page.getEnableForNativelyToggle().waitAndClick(10);
		driver.scrollPageToTop();
		page.getComponentsMarkComplete().waitAndClick(5);
		try {
			Assert.assertTrue(true, message);
			base.passedStep("Error message is displayed for not selecting redaction tags");
		} catch (Exception e) {

			Assert.assertTrue(false, message);
			base.failedStep("Error message is not displayed for  not selecting redaction tags");
		}
		loginPage.logout();
	}

	/**
	 * @Author sowndarya.Velraj Created on:NA Modified by:NA TESTCASE
	 *         No:RPMXCON_56102
	 * @Description:Verify the error message for TIFF/PDF component when 'Enable
	 *                     Tech issue doc without tag or text'
	 */
	@Test(description = "RPMXCON-56102", enabled = true, groups = { "regression" })
	public void verifyErrorMessageForEnableTechIssue() throws Exception {
		String message = "Technical Issue tags or corresponding placeholder text is missing in the Technical Issue Placeholdering of the TIFF/PDF section.";
		base.stepInfo("Test case Id: RPMXCON-56102-production Sprint 04");
		UtilityLog.info(Input.prodPath);
		ProductionPage page = new ProductionPage(driver);
		page.navigateToProductionPage();
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.getTIFFChkBox().waitAndClick(5);
		page.getTIFFTab().waitAndClick(5);
		page.getTIFF_EnableforPrivilegedDocs().ScrollTo();
		page.getTIFF_EnableforPrivilegedDocs().Enabled();
		page.getTIFF_EnableforPrivilegedDocs().waitAndClick(5);
		page.getbtnEnableForTechIssue().ScrollTo();
		page.getbtnEnableForTechIssue().waitAndClick(5);
		page.getSelectCloseBtn().waitAndClick(10);
		page.getEnableForNativelyToggle().waitAndClick(10);
		driver.scrollPageToTop();
		page.getComponentsMarkComplete().waitAndClick(5);
		try {
			Assert.assertTrue(true, message);
			base.passedStep("Error message is displayed for not selecting redaction tags");
		} catch (Exception e) {

			Assert.assertTrue(false, message);
			base.failedStep("Error message is not displayed for  not selecting redaction tags");
		}
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON_56079
	 * @Description: generate production with ingested text
	 */
	@Test(description = "RPMXCON-56079", enabled = true, groups = { "regression" })
	public void generateProductionWithIngestedText() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56079 Production - Sprint 04");

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

		// create production using dat/ingested text
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		productionname = "p" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
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
		base.passedStep("Generated production with ingested text");

		// To delete the created Tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56129
	 * @Description:Verify that on click of Mark Incomplete from the Component
	 *                     section, already selected tags should not available for
	 *                     selection in branding
	 */
	@Test(description = "RPMXCON-56129", enabled = true, groups = { "regression" })
	public void verifyClickMarkIncompleteDisablesALreadySelectedTags() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56129 Production - Sprint 04");
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
		page.fillingTIFFWithSelectingBrandingSelectionTags(tagname);
		page.fillingTextSection();
		page.clickComponentMarkCompleteAndIncomplete();
		page.fillingTIFFWithBrandingSelectionTagsAndPreviouslySelectedTagDisabled(tagname);
		base.passedStep(
				"Verify that on click of Mark Incomplete from the Component section, already selected tags should not available for  selection in branding");

		// To delete the created Tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON_56077
	 * @Description:generateProductionWithIngestedText1.
	 */
	@Test(description = "RPMXCON-56077", enabled = true, groups = { "regression" })
	public void generateProductionWithIngestedText1() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56077 Production - Sprint 04");

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
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFSection(tagname, Input.tagNamePrev);
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
		base.passedStep("generate Production With Ingested Text");

		// To delete the created Tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56140
	 * @Description:Verify that Count displays correctly on Priv Guard if Parent
	 *                     documents is assigned to Priv Tag
	 */
	@Test(description = "RPMXCON-56140", enabled = true, groups = { "regression" })
	public void verifyCountDisplaysCorrectlyOnPrivGuardForParentDocuments() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56140 Production - Sprint 04");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// search for the created folder and check the pure hit count
		sessionSearch = new SessionSearch(driver);

		sessionSearch.basicContentSearch(Input.parentDocument);
		sessionSearch.ViewInDocList();

		docPage = new DocListPage(driver);

		docPage.SelectingParentDocumentFromDocList(Input.parentDocument);
		System.out.println(foldername);

		docPage.bulkFolderExisting(foldername);

		// create production using dat,native and display tooltip display on preview
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		productionname = "p" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname, Input.tagNameTechnical);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPageExcludingFamilies(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.passedStep(
				"Verify that Count displays correctly on Priv Guard if Parent documents is assigned to Priv Tag");

		// To delete the created Tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON_58592
	 * @Description: generateProductionUnderDefaultDirectory.
	 */
	@Test(description = "RPMXCON-58592", enabled = true, groups = { "regression" })
	public void generateProductionUnderDefaultDirectory() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-58592 Production- Sprint 04");
		String testData1 = Input.testData1;

		// Pre-requisites
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		// searching for foldername and check pure hit count
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// create production under default directory
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = " p" + Utility.dynamicNameAppender();
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
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.passedStep("generate Production Under Default Directory");

		// To delete the created Tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56159
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description:Verify that Production should generate with Natively placholder
	 *                     for Orphan redacted document. TESTCASE NO: RPMXCON-56160
	 * @Description:Verify that Production should generate with Priv placholder for
	 *                     Orphan redacted document and also document is associated
	 *                     as Natively produced TESTCASE NO: RPMXCON-56157
	 * @Description: Verify that Production should generate with Priv placholder for
	 *               Orphan redacted document
	 */
	@Test(description = "RPMXCON-56159,RPMXCON-56157,RPMXCON-56160", enabled = true, groups = { "regression" })
	public void verifyProductionWithOrphanRedactedDocumemtsForNativelyPlaceholder() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56159 Production- Sprint 04");
		base.stepInfo("RPMXCON-56157 Production- Sprint 04");
		base.stepInfo("RPMXCON-56160 Production- Sprint 04");

		tagname = "Tag" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		// Tag created as privileged tag
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		sessionSearch = new SessionSearch(driver);
		// passing orphan redacted DOCID as string
		sessionSearch.basicContentSearch(Input.orphanDocument);
		sessionSearch.ViewInDocList();

		docPage = new DocListPage(driver);
		docPage.saveDocListToProfile(Input.orphanDocument);
		// adding document to bulk tag
		docPage.bulkTagExisting(tagname);

		// create production using dat,native and tiff for redaction
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		productionname = "p" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFWithRedactionAndSelectingDoubleTags();
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingSelectDocumentUsingTags(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.passedStep("Verified that Production should generate with Priv placholder for Orphan redacted document");

		// To delete the created Tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56158
	 * @Description:Verify that Production should generate with Tech Issue
	 *                     placholder for Orphan redacted document
	 */
	@Test(description = "RPMXCON-56158", enabled = true, groups = { "regression" })
	public void verifyProductionWithOrphanRedactedDocumemtsForTechIssuePlaceholders() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56158 Production- Sprint 04");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create folder and tag
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, "Technical Issue");

		sessionSearch = new SessionSearch(driver);
		// passing orphan redacted DOCID as string
		sessionSearch.basicContentSearch(Input.orphanDocument);
		sessionSearch.ViewInDocList();

		docPage = new DocListPage(driver);
		docPage.saveDocListToProfile(Input.orphanDocument);
		// adding document to bulk tag
		docPage.bulkTagExisting(tagname);

		// create production using dat,native and tiff for redaction
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		productionname = "p" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFWithRedactionAndSelectingDoubleTags();
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingSelectDocumentUsingTags(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.passedStep(
				"Verify that Production should generate with Tech Issue placholder for Orphan redacted documents");

		// To delete the created Tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         NO:RPMXCON_56156 Validate text on "Text redactions" and "Batch
	 *         redactions" applied documents with PDF generated files TESTCASE
	 *         No:RPMXCON_56154
	 * @Description:Validate text on "Text redactions" applied documents with PDF
	 *                       generated files
	 */

	@Test(description = "RPMXCON-56154,RPMXCON-56156", enabled = true, groups = { "regression" })
	public void generateProductionWithPDFFiles() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56156 Production- Sprint 04");
		base.stepInfo("RPMXCON-56154 Production- Sprint 04");
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingPDFForRedaction();
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
		base.passedStep("Validate text on Text redactions applied documents with PDF generated files");

		// To delete the created Tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON_56153
	 * @Description:Validate text on "Text redactions" applied documents with TIFF
	 *                       generated files. TESTCASE No:RPMXCON-56155
	 * @Description:Validate text for "Text redactions" and "Batch redactions" are
	 *                       applied area on documents with TIFF generated files
	 */

	@Test(description = "RPMXCON-56153,RPMXCON-56155", enabled = true, groups = { "regression" })
	public void generateProductionWithTIFFFiles() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56153 Production- Sprint 04");
		base.stepInfo("RPMXCON-56155 Production- Sprint 04");

		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		// Pre-requisites
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionwithFontColour(tagname, "Black with white font");
		page.fillingTextSection();
		page.navigateToNextSection();
		driver.waitForPageToBeReady();
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
				"Validate text for Text redactions and Batch redactions are applied area on documents with TIFF generated files");

		// To delete the created Tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON_47968
	 * @Description:To verify In Production user can select the only DAT and TIFF
	 *                 component and Production should generated successfully
	 */
	@Test(description = "RPMXCON-47968", enabled = true, groups = { "regression" })
	public void generateProductionWithDATAndTIFF() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47968 Production- Sprint 04");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
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
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.passedStep(
				"To verify In Production user can select the only DAT and TIFF component and Production should generated successfully");

		// To delete the created Tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON_48308
	 * @Description:To verify that the all the pages are together in proper sequence
	 *                 for a document in OPT.
	 */
	@Test(description = "RPMXCON-48308", enabled = true, groups = { "regression" })
	public void verifyDocumentsInOPT() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48308 Production- Sprint 04");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFWithMultiPage(tagname);
		page.getSelectCloseBtn().waitAndClick(10);
		page.getEnableForNativelyToggle().waitAndClick(10);
		driver.scrollingToBottomofAPage();
		base.clickButton(page.getAdvancedTabInTIFF());
		driver.waitForPageToBeReady();
		base.clickButton(page.getLoadFileTypeInTIFF());
		base.clickButton(page.getOPTInLoadFileType());
		driver.scrollingToBottomofAPage();
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
		base.passedStep("To verify that the all the pages are together in proper sequence for a document in OPT.");

		// To delete the created Tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON_48309
	 * @Description:To verify that the order of docs in "Text, Native, MP3, PDF" LST
	 *                 is matching the order of docs in DAT.
	 */
	@Test(description = "RPMXCON-48309", enabled = true, groups = { "regression" })
	public void verifyDocOrder() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48309 Production- Sprint 04");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		base.waitForElement(page.getAdvancedTabInNative());
		new Actions(driver.getWebDriver()).moveToElement(page.getAdvancedTabInNative().getWebElement()).click();
		base.clickButton(page.getGenerateLoadFileToggleInNative());
		driver.scrollingToBottomofAPage();
		page.fillingPDFSection(tagname, Input.tagNameTechnical);
		base.clickButton(page.getAdvancedTabInTIFF());
		page.fillingTextSection();
		base.clickButton(page.getAdvancedTabInText());
		base.clickButton(page.getAdvancedProductionComponent());
		base.clickButton(page.getMP3CheckBox());
		base.clickButton(page.getMP3Tab());
		driver.scrollingToBottomofAPage();
		base.clickButton(page.getAdvancedTabInMP3());
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
				"verified that the order of docs in Text, Native, MP3, PDF LST is matching the order of docs in DAT");

		// To delete the created Tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON_49225
	 * @Description:To verify that Family members having the same FamilyID must be
	 *                 withheld, if 'Withhold Natives for Entire Family for Priv and
	 *                 Redacted Docs' option is enabled
	 */
	@Test(description = "RPMXCON-49225", enabled = true, groups = { "regression" })
	public void verifyParentIDInPrivAndRedactedDocs() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49225 Production- Sprint 04");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for redacted documents and adding to bulk folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		// search for parent document and adding to bulk folder
		base = new BaseClass(driver);
		base.selectproject();
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.parentDocument);
		sessionSearch.ViewInDocList();
		docPage = new DocListPage(driver);
		docPage.SelectingParentDocumentFromDocList(Input.parentDocument);
		System.out.println(foldername);
		docPage.bulkFolderExisting(foldername);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname, Input.tagNameTechnical);
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
				"verified FamilyID withheld, if 'Withhold Natives for Entire Family for Priv and Redacted Docs' option is enabled");

		// To delete the created Tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56070
	 * @Description: Verify if user selectes only TIFF for Native Placeholdering
	 *               file then PageCount is always 1 and it will skip the
	 *               'DOCPGCOUNTUPDT'
	 */
	@Test(description = "RPMXCON-56070", enabled = true, groups = { "regression" })
	public void getTIFFpageCountAlwaysOne() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56070 -Production Sprint 06");

		String testData1 = Input.testData1;
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		// Verify pdf with burn redaction
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.datMetaDataTiffPageCount();
		page.fillingNativeSection();
		page.fillingTIFFSectionwithNativelyPlaceholder(tagname);
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

		// String ProductionDBVerification =Input.pageCount;
		base.stepInfo_DataBase("TODO / DB Verification has to be Done");
		base.stepInfo(" DB verification has to be done");
		base.passedStep(
				"Verified if user selectes only TIFF for Native Placeholdering file then PageCount is always 1 and it will skip the 'DOCPGCOUNTUPDT'");

		// delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56076
	 * @Description: To Verify if user selects only PDF for Native Placeholdering
	 *               file then PageCount is always 1 and it will not skip the
	 *               'DOCPGCOUNTUPDT'.
	 */
	@Test(description = "RPMXCON-56076", enabled = true, groups = { "regression" })
	public void getPageCountOneWithoutSkip() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56076 -Production Sprint 06");

		String testData1 = Input.testData1;
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		// Verify
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.datMetaDataTiffPageCount();
		page.fillingNativeSection();
		page.fillingPDFSectionwithNativelyPlaceholder(tagname);
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

		// String ProductionDBVerification =Input.pageCount;
		base.stepInfo_DataBase("TODO / DB Verification has to be Done");
		base.passedStep(
				"Verified if user selects only PDF for Native Placeholdering file then PageCount is always 1 and it will not skip the 'DOCPGCOUNTUPDT'");

		// delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56081
	 * @Description: To Verify in production if selects DAT,Native ,TIFF AND Text
	 *               (ingested) then it will not skip the page count.
	 */
	@Test(description = "RPMXCON-56081", enabled = true, groups = { "regression" })
	public void getProductionPageCountWithoutSkip() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56081 -Production Sprint 06");

		String testData1 = Input.testData1;
		String tagNameTechnical = Input.tagNameTechnical;
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		// Verify
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSection(tagname, tagNameTechnical);
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

		// String ProductionDBVerification =Input.pageCount;
		base.stepInfo_DataBase("TODO / DB Verification has to be Done");
		base.passedStep(
				"Verified in production if selects DAT,Native ,TIFF AND Text (ingested) then it will not skip the page count");

		// delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
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
