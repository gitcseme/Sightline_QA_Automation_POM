package testScriptsRegressionSprint22;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
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
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.RedactionPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Production_Regression22 {
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
		softAssertion=new SoftAssert();

	}
	/**
	 * @author Brundha RPMXCON-47742 Date:9/22/2022
	 * @Description To Verify the pagination for multiple productions for grid view.
	 */
	@Test(description = "RPMXCON-47742", enabled = true, groups = { "regression" })
	public void verifyingPaginationInGridView() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-47742 -Production component");
		base.stepInfo("To Verify the pagination for multiple productions for grid view.");

		int Count = 11;
		ProductionPage page = new ProductionPage(driver);
		String totalProduction = page.getProductionItemsTileItems().getText();
		if (Integer.valueOf(totalProduction)>=Count) {
			base.stepInfo("Navigating to production grid view");
			page.getGridView().waitAndClick(10);
			driver.scrollingToBottomofAPage();
			base.stepInfo("Verifying production grid view pagination");
			page.verifyingGridView();
		}
		else {
			base.stepInfo("Creating Multiple Production");
			for(int i=0;i<Count;i++) {
			String productionname = "P" + Utility.dynamicNameAppender();
			page.navigatingToProductionHomePage();
			page.selectingDefaultSecurityGroup();
			page.addANewProduction(productionname);
			driver.waitForPageToBeReady();
			}
			page.navigatingToProductionHomePage();
			base.stepInfo("Navigating to production grid view");
			page.getGridView().waitAndClick(10);
			driver.scrollingToBottomofAPage();
			base.stepInfo("Verifying production grid view pagination");
			page.verifyingGridView();
			
		}
		loginPage.logout();

	}
	
	/**
	 * @author Brundha Test case id-RPMXCON-48027
	 * @Description To Verify Redaction Style in PDF & TIFF Section "White with
	 *              Black font" works the same for both larger redactions or smaller
	 *              redactions that trigger the abbreviated text
	 * 
	 */
	@Test(description = "RPMXCON-48027", enabled = true, groups = { "regression" })
	public void verifyRedactionPlaceholderInGeneratedProduction() throws Exception {

		UtilityLog.info(Input.prodPath);
		BaseClass base = new BaseClass(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("RPMXCON-48027 -Production Component");
		base.stepInfo(
				"To Verify Redaction Style in PDF & TIFF Section 'White with Black font' works the same for both larger redactions or smaller redactions that trigger the abbreviated text");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();
		String PlaceholderText = Input.tag;
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		RedactionPage redactionpage = new RedactionPage(driver);
		driver.waitForPageToBeReady();
		redactionpage.manageRedactionTagsPage(Redactiontag1);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.viewInDocView();

		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		
		driver.waitForPageToBeReady();
		docViewRedactions.redactRectangleUsingOffset(10, 10, 20, 20);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag1);
		
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		docViewRedactions.redactRectangleUsingOffset(10, 10,120,120);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag1);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.selectGenerateOption(false);
		page.getClk_burnReductiontoggle().ScrollTo();
		page.getClk_burnReductiontoggle().waitAndClick(10);
		page.fillingBurnRedaction(Redactiontag1, "White with black font", true, Redactiontag1, PlaceholderText);
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();
		String home = System.getProperty("user.home");
		page.OCR_Verification_In_Generated_Tiff_tess4j(prefixID, suffixID, beginningBates);
		File imageFile = new File(
				home + "/Downloads/VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tiff");
		page.OCR_Verification_In_Generated_Tiff_tess4j(imageFile, PlaceholderText);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		loginPage.logout();

	}


	/**
	 * @author sowndarya Testcase No:RPMXCON-48979
	 * @Description: Verify that branding text should be truncated when Branding text
	 *  exceeds the space and no space after wrapping while production for a TIFF file
	 **/
	@Test(description = "RPMXCON-48979", enabled = true, groups = { "regression" })
	public void VerifyMultiLineBrTrunFrTiff() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48979");
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		base.stepInfo("Verify that branding text should be truncated when Branding text "
				+ "exceeds the space and no space after wrapping while production for a TIFF file");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in As " + Input.pa1userName);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch("DocFileType", "Spreadsheet");
		sessionSearch.ViewInDocList();
		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(2);
		driver.scrollPageToTop();
		doclist.bulkTagExistingFromDoclist(tagname);
		
		base = new BaseClass(driver);
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		base.waitForElement(page.getTIFFChkBox());
		page.getTIFFChkBox().Click();
		driver.scrollingToBottomofAPage();
		base.waitForElement(page.getTIFFTab());
		page.getTIFFTab().Click();
		page.fillingAndverifySixBrandingMultiLine(tagname);
		base.stepInfo("Added a Multi line branding to all six  locations");
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		base.waitUntilFileDownload();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		page.deleteFiles();
		page.extractFile();
		driver.waitForPageToBeReady();
		File tiffFile = new File(
				home + "/Downloads/" + "VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tiff");
		softAssertion.assertTrue(page.extractTextFromTiff(tiffFile).contains("Top - Lef"));
		softAssertion.assertTrue(page.extractTextFromTiff(tiffFile).contains("Top - Center Multiple Line Branding Text Verify"));
		softAssertion.assertTrue(page.extractTextFromTiff(tiffFile).contains("Top - Right Multiple Line Branding Text Ve..."));
		softAssertion.assertTrue(page.extractTextFromTiff(tiffFile).contains("Bottom - Left Multiple Line Branding Text..."));
		softAssertion.assertTrue(page.extractTextFromTiff(tiffFile).contains("Bottom - Center Multiple Line Branding Text Ve"));
		softAssertion.assertTrue(page.extractTextFromTiff(tiffFile).contains("Bottom - Right Multiple Line Branding Tex.."));
		softAssertion.assertAll();
		base.passedStep("Verify that branding text should be truncated when Branding text "
				+ "exceeds the space and no space after wrapping while production for a TIFF file");
		loginPage.logout();
	}
	
	/**
	 * @author sowndarya Testcase No:RPMXCON-48976
	 * @Description: Verify that branding text should be wrapped when Branding text 
	 * to all the six locations exceeds the space while production for a TIFF file
	 **/
	@Test(description = "RPMXCON-48976", enabled = true, groups = { "regression" })
	public void VerifyMultiLineBrWrapFrTiff() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48976");
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		base.stepInfo("Verify that branding text should be wrapped when Branding text "
				+ "to all the six locations exceeds the space while production for a TIFF file");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Logged in As " + Input.pa1userName);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch("DocFileType", "Spreadsheet");
		sessionSearch.ViewInDocList();
		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(2);
		doclist.bulkTagExistingFromDoclist(tagname);
		
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		base.waitForElement(page.getTIFFChkBox());
		page.getTIFFChkBox().Click();
		driver.scrollingToBottomofAPage();
		base.waitForElement(page.getTIFFTab());
		page.getTIFFTab().Click();
		page.fillingAndverifySixBrandingMultiLine(tagname);
		base.stepInfo("Added a Multi line branding to all six  locations");
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		base.waitUntilFileDownload();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		page.deleteFiles();
		page.extractFile();
		driver.waitForPageToBeReady();
		File tiffFile = new File(
				home + "/Downloads/" + "VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tiff");
		softAssertion.assertTrue(page.extractTextFromTiff(tiffFile).contains("Top - Center Multiple Line Branding Text Verify"));
		softAssertion.assertTrue(page.extractTextFromTiff(tiffFile).contains("Bottom - Center Multiple Line Branding Text Ve"));
		softAssertion.assertAll();
   		base.passedStep("Verified -  that branding text should be wrapped when Branding text "
				+ "to all the six locations exceeds the space while production for a TIFF file");
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
