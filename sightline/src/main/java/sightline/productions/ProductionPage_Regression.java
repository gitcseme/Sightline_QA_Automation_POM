
package sightline.productions;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
		baseClass = new BaseClass(driver);
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

	@Test(description="RPMXCON-56117",enabled = true, groups = { "regression" })
	public void verifySlipSheetDisplayed() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-56117- Production Sprint 02");
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
		baseClass.passedStep("Production components completed and navigated to numbering and sorting page");
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

	@Test(description="RPMXCON-56116",enabled = true, groups = { "regression" })
	public void verifyPreviewButtonShouldNotDisplayWhileDatOnlySelection() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-56116- Production Sprint 02");
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
		baseClass.passedStep("Production components completed and navigated to numbering and sorting page");
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

	@Test(description="RPMXCON-56115",enabled = true, groups = { "regression" })
	public void verifyPreviewButtonShouldNotDisplayWhileDatAndMp3OnlySelection() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-56115- Production Sprint 02");

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
		baseClass.passedStep("Production components completed and navigated to numbering and sorting page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.afterCompletingNavigatingToNextPage();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		baseClass.passedStep("Preview button is not displayed while selecting DAT and MP3 Files only selection");
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

	@Test(description="RPMXCON-56080",enabled = true, groups = { "regression" })
	public void verifyConfigurationFormat() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-56080- Production Sprint 02");

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
		baseClass.passedStep("Production components completed and navigated to numbering and sorting page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		baseClass.stepInfo("Completed summary and preview tab");
		page.fillingGeneratePageWithContinueGenerationPopup();
		baseClass.passedStep("OCRed text files verified according to ansi text configuration");

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

	@Test(description="RPMXCON-56072",enabled = true, groups = { "regression" })
	public void verifyingPageLevelOptionsOnTextComponents() throws InterruptedException {
		baseClass.stepInfo("Test case Id:RPMXCON-56072- Production Sprint 02");
		UtilityLog.info(Input.prodPath);

		// create production with DAT,Native,PDF& ingested Text
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		driver.scrollingToBottomofAPage();
		page.fillingTextSection();

		if (page.getPageLevelTextComponent().isElementAvailable(5)) {
			baseClass.failedStep("Page level options displayed in production component when clicking the  Text tab");
		} else {
			baseClass
					.passedStep("Page level options not displayed in production component when clicking the  Text tab");
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
	@Test(description="RPMXCON-56147",enabled = true, groups = { "regression" })
	public void verifyProductionForPDFWithNativelyPlaceholder() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-56147- Production Sprint 05");

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
		baseClass.passedStep(
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
	@Test(description="RPMXCON-56152",enabled = true, groups = { " regression" })
	public void verifyProductionForNativelyPlaceholderAndRedactionNotEnabled() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-56152- Production Sprint 05");

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
		baseClass.passedStep(
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
	 * @throws Exception
	 * check
	 */
	@Test(description="RPMXCON-56003,RPMXCON-56004",enabled = true, groups = { " regression" })
	public void verifySharableLinks() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-56003- Production Sprint 05");
		baseClass.stepInfo("Test case Id: RPMXCON-56004- Production Sprint 05");

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
		baseClass.passedStep("Verified the shareable links and a password is available on 'Shareable Link' window");

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
	@Test(description="RPMXCON-56137",enabled = true, groups = { " regression" })
	public void verifyPrivGuardBySelectingChildDocuments() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-56137- Production Sprint 05");
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
		baseClass.passedStep(
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
	@Test(description="RPMXCON-56121",enabled = true, groups = { " regression" })
	public void orphanRedactionsInSelectedDocument() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-56121- Production Sprint 05");

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
		baseClass.passedStep(
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

	@Test(description="RPMXCON-55990",enabled = true, groups = { " regression" })
	public void AssertionOnUnCommitInQCPage() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-55990- Production Sprint 05");

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
		baseClass.waitTime(3);
//		page.verifyProductionStatusInGenPage("Post-Generation QC checks Complete");
		page.VerifyinginProductionQCMessage();
		baseClass.passedStep(
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
	 *                     Page
	 * check
	 */

	@Test(description="RPMXCON-55971,RPMXCON-55972",enabled = true, groups = { " regression" })
	public void ViewDraftAssertionInGeneratePage() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-55971- Production Sprint 05");
		baseClass.stepInfo("Test case Id: RPMXCON-55972- Production Sprint 05");
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
		baseClass.passedStep(" Production status displays as Draft on Generation Page");

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
	 * configuration
	 * check
	 */
	@Test(description="RPMXCON-58592,RPMXCON-58594",enabled = true, groups = { " regression" })
	public void passProductionNameAsLoadFilesName() throws Exception {

		UtilityLog.info(Input.prodPath);
		baseClass.stepInfo("RPMXCON-58592 Production - Sprint 05");
		baseClass.stepInfo("RPMXCON-58594 Production - Sprint 05");
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
		baseClass.passedStep("passed ProductionName As LoadFiles Name.");

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
	@Test(description="RPMXCON-56080",enabled = true, groups = { " regression" })
	public void verifyFormatOCRedTextFiles() throws Exception {

		UtilityLog.info(Input.prodPath);
		baseClass.stepInfo("RPMXCON-56080 Production - Sprint 05");

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
		baseClass.passedStep("Verify that configuration format should applied for OCRed text files");

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

	@Test(description="RPMXCON-55987",enabled = true, groups = { " regression" })
	public void verifyPostGenInTileView() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-55987- Production Sprint 05");
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
			baseClass.passedStep(
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
	@Test(description="RPMXCON-56165",enabled = true, groups = { " regression" })
	public void createExport() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-56165- Production Sprint 05");

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
	 *                     redaction tags
	 * check
	 */

	@Test(description="RPMXCON-56122,RPMXCON-56123",enabled = true, groups = { " regression" })
	public void generatingTheProductionAfterBurnRedaction() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-56122- Production Sprint 05");
		baseClass.stepInfo("Test case Id: RPMXCON-56123- Production Sprint 05");

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
		baseClass.passedStep("production is generated successfully when redaction tag is selected in burn redaction");

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

	@Test(description="RPMXCON-56124",enabled = true, groups = { " regression" })

	public void tiffSectionLeftBranding() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-56124- Production Sprint 05");

		UtilityLog.info(Input.prodPath);
		baseClass.stepInfo(
				"#### Verify that if Tag is already specified with Right Branding then that Tag should not be available. ####");

		String tagname = "tag" + Utility.dynamicNameAppender();
		String productionname = "production" + Utility.dynamicNameAppender();
		

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		baseClass.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

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
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @Author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         NO:RPMXCON_49242 Description:To verify that Project Admin can modify
	 *         the populated the bates number fields and production should generated
	 *         with modified bates number
	 */
	@Test(description="RPMXCON-49242",enabled = true, groups = { " regression" })
	public void verificationOnNumberingAndSortingPage() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-49242- Production Sprint 05");
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
	@Test(description="RPMXCON-56150",enabled = true, groups = { " regression" })
	public void verifyNativeSectionDisplayForDocumentsFromExport() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-56150- Production Sprint 06");
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
		baseClass.passedStep(
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
	@Test(description="RPMXCON-49969",enabled = true, groups = { " regression" })
	public void verifyProductionWithPDFRedaction() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-49969- Production Sprint 06");
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
		baseClass.passedStep("Verified that Production should generate only PDF with Redaction text");

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
	@Test(description="RPMXCON-49134",enabled = true, groups = { " regression" })
	public void verifyTextExportWithPlaceholder() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-49134- Production Sprint 06");

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
		baseClass.passedStep(
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
	@Test(description="RPMXCON-49856",enabled = true, groups = { " regression" })
	public void verifyTIFFWithSignature() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-49856- Production Sprint 06");

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
		baseClass.passedStep(
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
	@Test(description="RPMXCON-56110",enabled = true, groups = { " regression" })
	public void verifyPDFGeneration() throws Exception {

		baseClass.stepInfo("RPMXCON_56110 Production- Sprint 06");
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
		baseClass.passedStep("Standard production template should be displayed as PDF is selected in production.");

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
	@Test(description="RPMXCON-49334",enabled = true, groups = { " regression" })
	public void verifyNatives() throws Exception {

		baseClass.stepInfo("RPMXCON_49334 Production- Sprint 06");
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

		baseClass.waitForElement(page.getNumber_of_natives_count());
		if (page.getNumber_of_natives_count().isElementPresent()) {
			baseClass.passedStep(
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
	@Test(description="RPMXCON-48317",enabled = true, groups = { " regression" })
	public void verifyAudioFilesWithRedactedDocuments() throws InterruptedException {

		baseClass.stepInfo("RPMXCON_48317 Production- Sprint 06");
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
		baseClass.passedStep("verified Audio files by  redacting them in beginning,middle and end");

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
	@Test(description="RPMXCON-56135",enabled = true, groups = { " regression" })
	public void verifyMp3ComponentRedactionTag() throws Exception {

		baseClass.stepInfo("RPMXCON_56135 Production- Sprint 06");
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
			baseClass.stepInfo("View option is displayed");
		}
		else {
			page.NavigateToLastTemplatePage();
			driver.waitForPageToBeReady();
			page.getViewBtn(TempName).waitAndClick(10);
			baseClass.stepInfo("View option is displayed");
		}
		driver.waitForPageToBeReady();
		page.viewTempProductionNext();
		page.mp3DisableCheck();
		baseClass.passedStep("Verified saved template under MP3 File component- Redaction Tags is disabled");
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48495
	 * @Description:To verify that if annotation layer option is selected and audio
	 *                 document is redacted then native should not produced
	 * 
	 */
	@Test(description="RPMXCON-48495",enabled = true, groups = { " regression" })
	public void verifyAudioFilesWithAnnotationLayerOption() throws InterruptedException {

		baseClass.stepInfo("RPMXCON_48495 Production- Sprint 06");
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
		baseClass.passedStep(
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

	@Test(description="RPMXCON-48025",enabled = true, groups = { " regression" })
	public void toggleBurnOnAndReductionStyle() throws Exception {

		UtilityLog.info(Input.prodPath);
		baseClass.stepInfo("RPMXCON_48025 Production- Sprint 06");

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

		baseClass.stepInfo("Burn reduction with white with black text enabled");
		loginPage.logout();
	}

	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-48382
	 * @Description:To verify that if Redacted document is selected and Burn
	 *                 Redaction is disabled then Native should be generated
	 */

	@Test(description="RPMXCON-48382",enabled = true, groups = { " regression" })
	public void verfiyTIFFwithoutBurnRedactionTag() throws Exception {

		UtilityLog.info(Input.prodPath);
		baseClass.stepInfo("RPMXCON_48382 Production- Sprint 06");

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
		baseClass.passedStep("verify pdf in DAT NATIVE PDF and TIFF file");

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
	@Test(description="RPMXCON-49044",enabled = true, groups = { " regression" })
	public void generatetDocumentswithMultipleBrandingTags() throws Exception {
		UtilityLog.info(Input.prodPath);
		baseClass.stepInfo("RPMXCON-49044 -Production Sprint 06");

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
		baseClass.passedStep(
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
	@Test(description="RPMXCON-47900",enabled = true, groups = { "regression" })
	public void verifyPrivGuardForDocMatch() throws Exception {
		UtilityLog.info(Input.prodPath);
		baseClass.stepInfo("RPMXCON-47900 -Production Sprint 10");
		baseClass.stepInfo("To Verify Priv Guard Section on the self production wizard for Document Match");

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
	@Test(description="RPMXCON-47898",enabled = true, groups = { "regression" })
	public void verifyDocSelectionForTag() throws Exception {
		UtilityLog.info(Input.prodPath);
		baseClass.stepInfo("RPMXCON-47898 -Production Sprint 10");
		baseClass.stepInfo("To Verify Document Selection Section on the self production wizard For Tag");

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
	@Test(description="RPMXCON-49041",enabled = true, groups = { "regression" })
	public void verifyProductionCreationDateMarkComplete() throws Exception {
		UtilityLog.info(Input.prodPath);
		baseClass.stepInfo("Test case Id: RPMXCON-49041");
		baseClass.stepInfo("To verify that 'Production Creation Date' should displayed when it Mark Complete .");

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		// page.addANewProduction(productionname);
		page.getAddNewProductionbutton().waitAndClick(10);
		page.getProductionName().SendKeys(productionname);
		page.getMarkCompleteLink().waitAndClick(10);
		baseClass.stepInfo("Click action using mark complete button");
		driver.waitForPageToBeReady();
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		page.getGridView().waitAndClick(10);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Nativated to production Grid View");
		String createdTime = page.getProductionCreatedTimeInGridView(productionname).getText();

		DateFormat dateFormat = new SimpleDateFormat("dd");
		Date date = new Date();
		String date1 = dateFormat.format(date);
		System.out.println("Current date" + date1);
		boolean flag = createdTime.contains(date1);
		if (flag) {
			baseClass.passedStep("current date is displayed on production grid view");
			System.out.println("date visible");
		} else {
			baseClass.failedStep("date is not contain in text");
			System.out.println("date not visible");
		}
		loginPage.logout();
	}

	/**
	 * @author sowndarya.velraj TESTCASE No:RPMXCON-47883
	 * @Description:To Verify DAT Section with various Options (Show Hide/Add
	 *                 Field/Advance Field Toggle and All Dropdown)
	 */
	@Test(description="RPMXCON-47883",enabled = true, groups = { "regression" })
	public void verifyDATSectionWithVariousOption() throws Exception {

		UtilityLog.info(Input.prodPath);
		baseClass.stepInfo("RPMXCON-47883 -Production Sprint 12");
		baseClass.stepInfo(
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
		baseClass.stepInfo(" 1>Format  2>Field Delimiters  3>Date Format  4>Field Mapping are displayed in DAT ");

		baseClass.waitForElement(page.fieldSeperatorDdInDAT());
		page.fieldSeperatorDdInDAT().selectFromDropdown().selectByIndex(2);

		baseClass.waitForElement(page.dateFormatDdInDAT());
		page.dateFormatDdInDAT().selectFromDropdown().selectByIndex(2);
		baseClass.stepInfo("Selected  Format, Field Delimiters and Date Format other than default");

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

		baseClass.waitTime(3);
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
	@Test(description="RPMXCON-47990",enabled = true, groups = { "regression" })
	public void verifyDATWithFilenameForProduction() throws Exception {

		UtilityLog.info(Input.prodPath);
		baseClass.stepInfo("RPMXCON-47990 -Production Sprint 12");
		baseClass.stepInfo("To Verify in Generated Production DAT will always have one row for each document");
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

		baseClass.waitForElement(page.getDAT_FieldClassification2());
		page.getDAT_FieldClassification2().selectFromDropdown().selectByVisibleText(Input.fldClassification);

		baseClass.waitForElement(page.getDAT_SourceField2());
		page.getDAT_SourceField2().selectFromDropdown().selectByVisibleText(Input.docFileName);

		baseClass.waitForElement(page.getDAT_DATField2());
		page.getDAT_DATField2().SendKeys(Input.docFileName + Utility.dynamicNameAppender());

		baseClass.stepInfo("Filled DAT component with file name");

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
	@Test(description="RPMXCON-47944",enabled = true, groups = { "regression" })
	public void verifyPDFPreview() throws Exception {

		UtilityLog.info(Input.prodPath);
		baseClass.stepInfo("RPMXCON-47944-Production Sprint 12");
		baseClass.stepInfo(
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
		baseClass.passedStep("Preview of the PDF displayed along with Branding   ");
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
	@Test(description="RPMXCON-55680",enabled = true, groups = { "regression" })
	public void verifyTranslation() throws Exception {

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		ProductionPage page = new ProductionPage(driver);

		String tagname = "TAG" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id: RPMXCON-55680 Production Sprint 12");
		baseClass.stepInfo(
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

		baseClass.waitTime(4);
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
	@Test(description="RPMXCON-48163",enabled = true, groups = { "regression" })
	public void verifymp3ComponentWithRedaction() throws Exception {

		UtilityLog.info(Input.prodPath);
		baseClass.stepInfo("RPMXCON-48163 -Production Sprint 12");
		baseClass.stepInfo(
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
		baseClass.stepInfo(
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
	@Test(description="RPMXCON-48550",enabled = true, groups = { "regression" })
	public void verifyBatesRangeInGenerate() throws Exception {

		UtilityLog.info(Input.prodPath);
		baseClass.stepInfo("RPMXCON-48550 -Production Sprint 12");
		baseClass.stepInfo("To verify that 'Bates Range' is displayed in Production Generate step");
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
		baseClass.passedStep("Bates Range is displayed in generate page");
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
	}

	/**
	 * @author sowndarya.velraj TESTCASE No:RPMXCON-47988
	 * @Description:To verify Bates Number in generated production DAT and Load
	 *                 Files should be in Ascending Order
	 */
	@Test(description="RPMXCON-47988",enabled = true, groups = { "regression" })
	public void verifyBatesNumberAndSorting() throws Exception {

		UtilityLog.info(Input.prodPath);
		baseClass.stepInfo("RPMXCON-47988-Production Sprint 12");
		baseClass.stepInfo(
				"To verify Bates Number in generated production DAT and Load Files should be in Ascending Order");
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
	@Test(description="RPMXCON-47989",enabled = true, groups = { "regression" })
	public void verifyDATTemplate() throws Exception {

		UtilityLog.info(Input.prodPath);
		baseClass.stepInfo("RPMXCON-47989-Production Sprint 12");
		baseClass.stepInfo("To Verify production generation using Custom Template.(Check for Priv in DAT component).");
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
		baseClass.stepInfo("In DAT Selected fields with Priv check box  and  Selected TIFF add Priv Tag");
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
		baseClass.passedStep("Verified production generation using Custom Template.(Check for Priv in DAT component)");
	}

	/**
	 * @author sowndarya.velraj TESTCASE No:RPMXCON-47899
	 * @Description:To Verify Document Selection Section on the self production
	 *                 wizard For Searches
	 */
	@Test(description="RPMXCON-47899",enabled = true, groups = { "regression" })
	public void verifyDocumentSelectionOnSearches() throws Exception {

		UtilityLog.info(Input.prodPath);
		baseClass.stepInfo("RPMXCON-47899 -Production Sprint 12");
		baseClass.stepInfo("To Verify Document Selection Section on the self production wizard For Searches");
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
		baseClass.waitForElement(page.getMarkCompleteLink());
		page.getMarkCompleteLink().waitAndClick(10);

		softAssertion = new SoftAssert();
		softAssertion.assertTrue(page.getDocumentSelectionLink().isDisplayed());
		baseClass.passedStep("Total Document count is displayed");

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
	@Test(description="RPMXCON-49230",enabled = true, groups = { "regression" })
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

		baseClass.stepInfo("Test case Id:RPMXCON-49230 Production Sprint 12");
		baseClass.stepInfo(
				"To verify that Family members having the same FamilyID must be produced, if 'Do not produce natives of the parents of privileged and redacted docs'' option is enabled");

		// create tag and folder
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		// search for folder
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		baseClass.stepInfo(
				" Redacted documents are selected and preformed bulk tag action with classification as: privileged tag");

		baseClass = new BaseClass(driver);
		baseClass.selectproject();
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.telecaSearchString);
		sessionSearch.ViewInDocList();

		docPage = new DocListPage(driver);
		docPage.selectingParentDocument();
		driver.scrollPageToTop();
		docPage.bulkTagExistingFromDoclist(tagname);
		baseClass.stepInfo(
				"Parent document is selected from doclist and preformed bulk tag action with classification as: privileged tag");

		baseClass.stepInfo("Starting the production");
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		softAssertion.assertTrue(page.radioBtnInAdvancedNative_ForPrivRedactedDocs().Selected());
		baseClass.passedStep("Do not produce natives of the parents of privileged and redacted docs is selected");
		page.fillingTIFFSection(tagname);
		baseClass.stepInfo("Privileged Tag is selected in TIFF");
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
	@Test(description="RPMXCON-47901",enabled = true, groups = { "regression" })
	public void verifyPrivGuardForRunCategorization() throws Exception {

		UtilityLog.info(Input.prodPath);
		baseClass.stepInfo("RPMXCON-47901 -Production Sprint 12");
		baseClass.stepInfo("To Verify Priv Guard Section on the self production wizard for Run Categorization");
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
		baseClass.stepInfo("Redirected to Categorization page ");

		softAssertion = new SoftAssert();
		softAssertion.assertTrue(page.identifyByProductionGuardSource_radioBtn().isDisplayed());
		baseClass.passedStep("Identify By Production Guard Source radio button is displayed");

		softAssertion.assertTrue(page.analyzeSelectProductionSets_radioBtn().isDisplayed());
		baseClass.passedStep("Analyze Select Production Sets  radio button is displayed");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
	}

	/**
	 * @author Sowndarya.Velraj created on:02/03/22 TESTCASE No:RPMXCON-48039
	 * @Description:To Verify Removal of Priv Tag from a Document should get
	 *                 Produced in Production for Native
	 */
	@Test(description="RPMXCON-48039",enabled = true, groups = { " regression" })
	public void verifyPrivilegedTagInNative() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-48039- Production Sprint 11");
		baseClass
				.stepInfo("To Verify Removal of Priv Tag from a Document should get Produced in Production for Native");
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
		baseClass.stepInfo("set of 6 Documents are Bulk Tagged as Privileged tag");

		sessionSearch.navigateToSessionSearchPageURL();
		baseClass = new BaseClass(driver);
		baseClass.selectproject();
		sessionSearch.basicContentSearch(Input.searchString5);
		driver.waitForPageToBeReady();
		sessionSearch.bulkTagExisting(tagname2);
		baseClass.stepInfo("set of  Documents are Bulk Tagged as Tevhnical Issue tag");

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
		baseClass.waitForElement(page.getProductionFromHomepage(productionname));
		page.getProductionFromHomepage(productionname).waitAndClick(10);
		page.getBckBtn().waitAndClick(10);
		for (int i = 0; i < 4; i++) {
			page.getBckBtn().waitAndClick(10);
		}
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Navigating back to Document selection page");
		page.getMarkIncompleteButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		page.fillingDocumentSelectionWithTag(tagname2);
		baseClass.stepInfo("Removed Tags with native file");
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		baseClass.passedStep("Removed Priv Tag from a Document should get Produced in Production for Native");
	}

	/**
	 * @author sowndarya.velraj TESTCASE No:RPMXCON-47984
	 * @Description:To Verify On Productions landing page, Count of productions in
	 *                 the tile view should match with grid view,
	 */
	@Test(description="RPMXCON-47984",enabled = true, groups = { "regression" })
	public void verifyProductionCountInTileAndGrid() throws Exception {

		UtilityLog.info(Input.prodPath);
		baseClass.stepInfo("RPMXCON-47984 -Production Sprint 12");
		baseClass.stepInfo(
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
		baseClass.passedStep(
				"Verified On Productions landing page, Count of productions in the tile view should match with grid view");

	}

	/**
	 * @Author sowndarya.velraj
	 * @Description :Verify that for the saved template 'File Type Options' under
	 *              Native component should be disabled [ RPMXCON-56133]
	 * @throws Exception
	 */
	@Test(description="RPMXCON-56133",enabled = true, groups = { "regression" })
	public void verifySavedTemplateForNative() throws Exception {

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		String templatename = "TEMPLATE" + Utility.dynamicNameAppender();
		

		baseClass.stepInfo("Test case Id: RPMXCON-56133 Production Sprint 12");
		baseClass.stepInfo(
				"Verify that for the saved template 'File Type Options' under Native component should be disabled");

		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.navigateToNextSection();
		page.navigateToProductionPage();
		page.saveProductionAsTemplateAndVerifyProductionComponentsInManageTemplateTab(productionname, templatename);
		baseClass.waitForElement(page.nextButtonInTemplate());
		page.nextButtonInTemplate().waitAndClick(10);
		baseClass.stepInfo("Navigating to Production components page in manage templates tab");
		driver.waitForPageToBeReady();

		page.getNativeChkBox().ScrollTo();
		page.getNativeChkBox().waitAndClick(10);
		page.getNativeTab().waitAndClick(10);
		if (page.getChkBoxNativeOthers().Selected()) {
			baseClass.passedStep("Native File types are disabled for saved template");
		}
	}

	/**
	 * @author sowndarya.velraj TESTCASE No:RPMXCON-49059
	 * @Description:To verify that "Production Start Date" should present the date
	 *                 when the production was started
	 */
	@Test(description="RPMXCON-49059",enabled = true, groups = { "regression" })
	public void verifyStartDateInGridView() throws Exception {
		UtilityLog.info(Input.prodPath);
		baseClass.stepInfo("RPMXCON-49059 -Production Sprint 12");
		baseClass.stepInfo(
				"To verify that Production Start Date should present the date when the production was started");
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
		int startDateIndex = baseClass.getIndex(page.getGridWebTableHeader(), "START DATE");
		driver.waitForPageToBeReady();
		String startDate = page.getGridProdValues(productionname, startDateIndex).getText();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		String date1 = dateFormat.format(date);
		System.out.println("Current date " + date1);
		boolean flag = startDate.contains(date1);
		if (flag) {
			baseClass.passedStep("Start date is displayed on production grid view");
			System.out.println("date visible");
		} else {
			baseClass.failedStep("date is not contain in text");
			System.out.println("date not visible");
		}

		baseClass.passedStep("To Verify On grid view of the productions the start date for a production in grid view.");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
	}

	/**
	 * @author sowndarya.velraj TESTCASE No:RPMXCON-55928
	 * @Description:Verify new text in 'Redactions' section in Tiff/PDF components
	 */
	@Test(description="RPMXCON-55928",enabled = true, groups = { "regression" })
	public void verifyTextInRedaction() throws Exception {

		UtilityLog.info(Input.prodPath);
		baseClass.stepInfo("RPMXCON-55928-Production Sprint 12");
		baseClass.stepInfo("Verify new text in 'Redactions' section in Tiff/PDF components");
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
		baseClass.passedStep("New text is displayed in 'Redactions' section in Tiff/PDF components");
	}

	/**
	 * @author Sowndarya.Velraj created on:02/02/22 TESTCASE No:RPMXCON-46893
	 * @Description:To verify Production Generation for MP3 files Audio (redaction
	 *                 Applied).
	 */
	@Test(description="RPMXCON-46893",enabled = true, groups = { " regression" })
	public void verifyMP3RedactionForProduction() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-46893- Production Sprint 11");
		baseClass.stepInfo("To verify Production Generation for MP3 files Audio (redaction Applied).");
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
	@Test(description="RPMXCON-48343",enabled = true, groups = { "regression" })
	public void verifyTiffWIthTechIssueDocs() throws Exception {

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		ProductionPage page = new ProductionPage(driver);

		String tagname = "TAG" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id: RPMXCON-48343 Production Sprint 11");
		baseClass.stepInfo(
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

		baseClass.waitTime(4);
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
	@Test(description="RPMXCON-47888",enabled = true, groups = { " regression" })
	public void verifyDATWithPrivilegedCheckboxForAudioFiles() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-47888- Production Sprint 11");
		baseClass.stepInfo("To Verify in Priv Guard View in Doclist and DocView.");
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
		baseClass.stepInfo("Privileged checkbox is selected in DAT component");
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
	@Test(description="RPMXCON-48377",enabled = true, groups = { "regression" })
	public void VerifyNativeWithPrivPlaceholder() throws Exception {

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		ProductionPage page = new ProductionPage(driver);

		String tagname = "TAG" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id: RPMXCON-48377 Production Sprint 11");
		baseClass.stepInfo(
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
	@Test(description="RPMXCON-48501",enabled = true, groups = { "regression" })
	public void verifySelectedMetadataNotDisplayedOnDocs() throws Exception {

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		ProductionPage page = new ProductionPage(driver);

		String folder = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id: RPMXCON-48501 Production Sprint 11");
		baseClass.stepInfo(
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
		baseClass.waitForElement(page.getDATRedactionsCBox());
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

		baseClass.waitTime(4);
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
	@Test(description="RPMXCON-47888,RPMXCON-47894",enabled = true, groups = { " regression" })
	public void verifyPrivGuardSectionViewInDoclistAndDocView() throws Exception {

		baseClass.stepInfo("Test case Id RPMXCON-47888 & 47894- Production Sprint 11");
		baseClass.stepInfo("To Verify in Priv Guard View in Doclist and DocView.");
		baseClass.stepInfo("To Verify Priv Guard Section on the self production wizard");
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
		baseClass.passedStep("Matching Document Count is : " + matchingDocs);

		// verify doclist total document with purehit
		docPage = new DocListPage(driver);
		String doclistCount = docPage.verifyingDocCount();
		int docListCount = Integer.parseInt(doclistCount);
		softAssertion.assertEquals(purehit, docListCount);
		baseClass.passedStep("Total Document Count of  DocList : " + docListCount);

		// verify docview total document with purehit
		driver.waitForPageToBeReady();
		page.getDocView().waitAndClick(10);
		docViewPage = new DocViewPage(driver);
		int docviewCount = docViewPage.verifyingDocCount();
		softAssertion.assertEquals(purehit, docviewCount);
		baseClass.passedStep("Total Document Count of  DocView : " + docviewCount);

		softAssertion.assertAll();
		loginPage.logout();

	}

//60 cases

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
