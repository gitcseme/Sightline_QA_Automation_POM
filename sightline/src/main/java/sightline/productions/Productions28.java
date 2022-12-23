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
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.BatchPrintPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Productions28 {
	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	DocViewPage docView;
	Utility utility;
	String foldername;
	String tagname;
	String productionname;
	ProductionPage page;
	SessionSearch sessionSearch;
	SavedSearch saveSearch;
	TagsAndFoldersPage tagsAndFolderPage;
	SoftAssert softAssertion;

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
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());

		driver = new Driver();
		loginPage = new LoginPage(driver);
		base = new BaseClass(driver);
		saveSearch = new SavedSearch(driver);
		sessionSearch = new SessionSearch(driver);
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		softAssertion = new SoftAssert();
		page = new ProductionPage(driver);

	}

	/**
	 * @author Brundha.T RPMXCON-49217
	 * @throws Exception
	 * @Description To verify that DocView Images tab should not show the produced
	 *              TIFF/PDF for this uncommitted production
	 * 
	 */
	@Test(description = "RPMXCON-49217", enabled = true, groups = { "regression" })
	public void verifyDocumentDisplayedInProductionInDocViewPage() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-49217");
		base.stepInfo(
				"To verify that DocView Images tab should not show the produced TIFF/PDF for this uncommitted production");
		base = new BaseClass(driver);

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in As " + Input.pa1userName + "");

		UtilityLog.info(Input.prodPath);
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		DocListPage doclist = new DocListPage(driver);
		DocViewPage doc = new DocViewPage(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();

		doclist.documentSelection(2);
		doclist.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSection(tagname, Input.searchString4);
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

		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.ViewInDocViewWithoutPureHit();
		doc.clickOnImageTab();
		base.stepInfo("verifying committed production document in image tab");
		doc.verifyProductionNameForPDFFileInDocView(productionname);

		page.navigateToProductionPage();
		driver.waitForPageToBeReady();
		page.getProductionNameLink(productionname).waitAndClick(5);
		base.waitForElement(page.getConfirmProductionUnCommit());
		page.getConfirmProductionUnCommit().waitAndClick(5);
		driver.Navigate().refresh();
		base.waitTime(3);

		sessionsearch = new SessionSearch(driver);
		sessionSearch.ViewInDocViewWithoutPureHit();
		driver.waitForPageToBeReady();
		base.stepInfo("verifying uncommitted production document not in image tab");
		doc.clickOnImageTab();
		if (!doc.ProductionNameInImageTab(productionname).isDisplayed()) {
			base.passedStep("Produced document is not displayed after uncommitting production");
		} else {

			base.failedStep("Produced document is displayed");
		}
		loginPage.logout();
	}

	/**
	 * @author Brundha.T RPMXCON-49219
	 * @throws Exception
	 * @Description To verify that in Batch Printing, uncommitted production will
	 *              not show up as it was uncommitted
	 * 
	 */
	@Test(description = "RPMXCON-49219", enabled = true, groups = { "regression" })
	public void verfyingReflectionOfProductionInBatchPrint() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-49219");
		base.stepInfo(
				"To verify that in Batch Printing, uncommitted production will not show up as it was uncommitted");
		base = new BaseClass(driver);

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in As " + Input.pa1userName + "");

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		BatchPrintPage batch = new BatchPrintPage(driver);
		DocListPage doclist = new DocListPage(driver);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();

		doclist.documentSelection(2);
		doclist.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSection(tagname, Input.searchString4);
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

		batch.navigateToBatchPrintPage();
		batch.fillingSourceSelectionTab("Tag", tagname, true);
		base.waitForElement(batch.getProductionRadioButton());
		batch.getProductionRadioButton().waitAndClick(5);
		base.waitForElementCollection(batch.getProductionList());
		base.stepInfo("verifying committed production is displayed");
		List<String> availableProduction = base.availableListofElements(batch.getProductionList());
		base.stepInfo("Committed production" + availableProduction);
		if (availableProduction != null) {
			if (batch.getBasisProduction(productionname).isElementAvailable(2)) {
				base.passedStep("Committed production is displayed in batch print");
			} else {
				base.failedStep("committed production is not displayed");
			}
		}else {
			base.failedStep("Productions are not displayed");
		}
		page.navigateToProductionPage();
		driver.waitForPageToBeReady();
		page.getProductionNameLink(productionname).waitAndClick(5);
		base.waitForElement(page.getConfirmProductionUnCommit());
		page.getConfirmProductionUnCommit().waitAndClick(5);

		for (int i = 0; i < 3; i++) {
			driver.Navigate().refresh();
			base.waitTime(1);
		}

		batch.navigateToBatchPrintPage();
		batch.fillingSourceSelectionTab("Tag", tagname, true);
		base.waitForElement(batch.getProductionRadioButton());
		batch.getProductionRadioButton().waitAndClick(5);
		base.stepInfo("verifying after uncommit production is not displayed");
		base.waitForElementCollection(batch.getProductionList());
		if (!batch.getBasisProduction(productionname).isElementAvailable(2)) {
			base.passedStep("UnCommitted production is not displayed in batch print");
		} else {
			base.failedStep("Uncommitted production is displayed");
		}
		loginPage.logout();
	}

	/**
	 * @author Brundha.T RPMXCON-49223
	 * @throws Exception
	 * @Description To verify that after uncommit, if user change any Production
	 *              Components, it should regenerate and commit Production
	 *              successfully
	 * 
	 */
	@Test(description = "RPMXCON-49223", enabled = true, groups = { "regression" })
	public void verfyingRegenartionOfProduction() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-49223");
		base.stepInfo(
				"To verify that after uncommit, if user change any Production Components, it should regenerate and commit Production successfully");
		base = new BaseClass(driver);

		// Login As PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in As " + Input.pa1userName + "");

		UtilityLog.info(Input.prodPath);
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		DocListPage doclist = new DocListPage(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();

		doclist.documentSelection(2);
		doclist.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSection(tagname, Input.searchString4);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		base.stepInfo("Committing the production");
		page.fillingGeneratePageWithContinueGenerationPopup();
		for (int i = 0; i < 2; i++) {
			driver.Navigate().refresh();
			base.waitTime(2);
		}

		base.stepInfo("Uncommitting the committed production");
		base.waitForElement(page.getConfirmProductionUnCommit());
		page.getConfirmProductionUnCommit().waitAndClick(5);
		for (int i = 0; i < 2; i++) {
			driver.Navigate().refresh();
			base.waitTime(2);
		}
		base.stepInfo("Changing DATconfiguration and regenerating the production");
		page.clickElementNthtime(page.getBackButton(), 7);
		driver.scrollPageToTop();
		page.getMarkInCompleteBtn().waitAndClick(5);
		base.waitForElement(page.getDATTab());
		page.getDATTab().waitAndClick(5);
		driver.waitForPageToBeReady();
		page.getDAT_DATField1().waitAndClick(5);
		page.getDAT_DATField1().SendKeys("B" + Utility.dynamicNameAppender());
		page.clickMArkCompleteMutipleTimes(3);
		page.fillingPrivGuardPage();
		page.clickMArkCompleteMutipleTimes(2);

		base.stepInfo("Committing the regenerated production");
		page.fillingGeneratePageWithContinueGenerationPopup();
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
		System.out.println("******TEST CASES FOR PRODUCTION EXECUTED******");

	}
}
