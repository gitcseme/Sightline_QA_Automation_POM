package testScriptsRegressionSprint27;

import java.io.File;
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
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Export_Regression27 {

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
		sessionSearch = new SessionSearch(driver);
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		softAssertion = new SoftAssert();
		page = new ProductionPage(driver);

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

	/**
	 * @author sowndarya TESTCASE No:RPMXCON-50659
	 * @Description: Verify that user can download the Export by using the Shareable link for 'DAT Only'
	 **/
	@Test(description = "RPMXCON-50659", enabled = true, groups = { "regression" })
	public void verifySharableLinkForDATOnlyFiles() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-50659");
		base.stepInfo("Verify that user can download the Export by using the Shareable link for 'DAT Only'");

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		String FolderName = "FolderName" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String productionname = "Prod" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(FolderName, Input.securityGroup);

		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(FolderName);

		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSectionWithBrandingText();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(FolderName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		page.verifyDownloadProductionUsingSharableLink_DATFileonly();
		base.passedStep("Zip file saved into local machine");
		loginPage.logout();
	}
	
	/**
	 * @author sowndarya TESTCASE No:RPMXCON-50658
	 * @Description:Verify that user can download the Export by using the Shareable link for 'All Files'
	 **/
	@Test(description = "RPMXCON-50658", enabled = true, groups = { "regression" })
	public void verifySharableLinkFoAllFiles() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-50658");
		base.stepInfo("Verify that user can download the Export by using the Shareable link for 'All Files'");

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		String FolderName = "FolderName" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String productionname = "Prod" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(FolderName, Input.securityGroup);

		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(FolderName);

		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSectionWithBrandingText();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(FolderName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		page.verifyDownloadProductionUsingSharableLink();
		base.passedStep("Zip file saved into local machine");
		loginPage.logout();
	}
	
	/**
	 * @author sowndarya TESTCASE No:RPMXCON-50374
	 * @Description:Verify that download option is available after post generation completed on Export-QC tab
	 **/
	@Test(description = "RPMXCON-50374", enabled = true, groups = { "regression" })
	public void verifyDownloadAndCopyPath() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-50374");
		base.stepInfo("Verify that download option is available after post generation completed on Export-QC tab");

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		String FolderName = "FolderName" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String productionname = "Prod" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(FolderName, Input.securityGroup);

		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(FolderName);

		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSectionWithBrandingText();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(FolderName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		base.elementDisplayCheck(page.getQC_Download());
		base.elementDisplayCheck(page.getCopyPath());
		base.passedStep("On Production QC page,Download and CopyPath is displayed");
		loginPage.logout();
		
	}
	
	/**
	 * @author sowndarya TESTCASE No:RPMXCON-49136
	 * @Description:To verify that when text is exported for Exception file then it should export the text with the Placeholder (For Export)
	 **/
	@Test(description = "RPMXCON-49136", enabled = true, groups = { "regression" })
	public void verifyTechIssuePlaceholder() throws Exception {

		base.stepInfo("Test case Id: RPMXCON-49136");
		base.stepInfo("To verify that when text is exported for Exception file then it should export the text with the Placeholder (For Export)");

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		String tagName = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String productionname = "Prod" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagName,Input.technicalIssue);

		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagName);

		base = new BaseClass(driver);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTiffSectionTechIssueWithEnteringText(tagName, Input.tagNameTechnical);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.waitTime(2);
		page.extractFile();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		String name = page.getProduction().getText().trim();
		driver.waitForPageToBeReady();
		File TiffFile = new File(
				home + "/Downloads/VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tiff");
		page.OCR_Verification_In_Generated_Tiff_tess4j(TiffFile, Input.tagNameTechnical);
		loginPage.logout();
	}
}
