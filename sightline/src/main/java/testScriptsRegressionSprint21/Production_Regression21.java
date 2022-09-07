package testScriptsRegressionSprint21;
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
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.RedactionPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Production_Regression21 {
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

	}
	
	

	/**
	 * @author Brundha TESTCASE No:RPMXCON-48645 Date:9/7/2022
	 * @Description:To verify that if "Do Not Produce TIFFs for Natively Produced
	 *                 Docs" is disabled , then TIFFs is generated with Placeholder
	 */
	@Test(description = "RPMXCON-48645", enabled = true, groups = { "regression" })

	public void verifyingPrivPlaceholderForSelectedDocs() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-48645-from Production Component");
		base.stepInfo(
				"To verify that if 'Do Not Produce TIFFs for Natively Produced Docs' is disabled , then TIFFs is generated with Placeholder");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.ViewInDocListWithOutPureHit();

		DocListPage doclist = new DocListPage(driver);
		int Doc = 3;
		doclist.documentSelection(Doc);
		doclist.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		int FirstFile = Integer.valueOf(beginningBates);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionPrivDocs(tagname, Input.searchString4);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		String home = System.getProperty("user.home");
		page.deleteFiles();
		page.extractFile();
		driver.waitForPageToBeReady();
		int Lastile = FirstFile + Doc;
		page.isImageFileExist(FirstFile, Lastile, prefixID, suffixID);
		page.OCR_Verification_In_Generated_Tiff_tess4j(FirstFile, Lastile, prefixID, suffixID, Input.searchString4);

		int File = FirstFile + Doc + Integer.valueOf(Input.pageCount);
		File TiffFile = new File(home + "/Downloads/" + "VOL0001/Images/0001/" + prefixID + File+ suffixID + ".tiff");
		page.verifyingTiffImage(TiffFile, Input.searchString4);

		File NativeFile = new File(home + "/Downloads/VOL0001/Natives/0001/");
		page.isfileisExists(NativeFile);
		File[] dir_contents = NativeFile.listFiles();
		int nativefile = dir_contents.length;
		base.digitCompareEquals(nativefile, Doc, "Native files are generated as expected","Native files are not generated as expected");
		loginPage.logout();
	}

	/**
	 * @author Brundha TESTCASE No:RPMXCON-47820 Date:9/7/2022
	 * @Description:To verify Native generation for JPEG and PPT file.
	 */
	@Test(description = "RPMXCON-47820", enabled = true, groups = { "regression" })

	public void verifyingNativeFileTypeInGeneratedProduction() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-47820-from Production Component");
		base.stepInfo("To verify Native generation for JPEG and PPT file.");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		int Count=Integer.valueOf(Input.pageCount);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.SearchMetaData(Input.docFileType, "JPEG");
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		int beginningBate=Integer.valueOf(beginningBates)+Count+Count;
		System.out.println(beginningBate);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		String home = System.getProperty("user.home");
		page.deleteFiles();
		page.extractFile();
		driver.waitForPageToBeReady();
		File NativeFile = new File(home + "/Downloads/VOL0001/Natives/0001/");
		
		File NativeFileType = new File(
				home + "/Downloads/VOL0001/Natives/0001/" + prefixID + beginningBate + suffixID + ".jpg");
		page.isfileisExists(NativeFile);
		if(NativeFileType.exists()) {
			base.passedStep(""+NativeFileType+" is displayed");
		}else {
			base.failedStep(""+NativeFileType+" is not displayed");
		}
		loginPage.logout();
	}
	/**
	 * @author Brundha TESTCASE No:RPMXCON-47859 Date:9/7/2022
	 * @Description:Verify the static and dynamic display of 'QC & confirmation'
	 *                     Page by actually generating and not generating production
	 */
	@Test(description = "RPMXCON-47859", enabled = true, groups = { "regression" })

	public void verifyingDynamicDisplayOfProduction() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-47859-from Production Component");
		base.stepInfo(
				"Verify the static and dynamic display of 'QC & confirmation' Page by actually generating and not generating production");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String prefixID1 = Input.randomText + Utility.dynamicNameAppender();
		String suffixID1 = Input.randomText + Utility.dynamicNameAppender();
		String BatesNum = "B" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		String beginningBates1 = page.getRandomNumber(2);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFSectionPrivDocs(tagname, Input.searchString4);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommitandDownload();
		page.getBackButton().waitAndClick(5);
		driver.waitForPageToBeReady();
		String Batesno = page.getProd_BatesRange().getText();

		page.clickElementNthtime(page.getBackButton(), 6);
		driver.scrollPageToTop();
		page.getMarkIncompleteButton().waitAndClick(5);
		page.getDATTab().waitAndClick(5);
		driver.scrollingToElementofAPage(page.getDAT_DATField1());
		page.getDAT_DATField1().SendKeys(BatesNum);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID1, suffixID1, beginningBates1);
		page.clickMArkCompleteMutipleTimes(2);
		page.getMarkIncompleteButton().waitAndClick(5);
		page.priviledgeDocCheck(true, tagname);
		page.fillingPrivGuardPage();
		page.clickMArkCompleteMutipleTimes(2);
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		String name = page.getProduction().getText().trim();
		driver.waitForPageToBeReady();
		File DatFile = new File(home + "/Downloads/VOL0001/Load Files/" + name + "_DAT.dat");
		page.isdatFileExist();
		page.verifyingDATFile(DatFile, 0, 1, BatesNum);

		page.getBackButton().waitAndClick(5);
		String BatesNumber = page.getProd_BatesRange().getText();
		base.textCompareNotEquals(Batesno, BatesNumber, "Dynamic display of production is dsiplayed","Dynamic display of production is not displayed");
				
		loginPage.logout();
	}

	/**
	 * @author Brundha RPMXCON-63190 Date:9/7/2022
	 * @DescriptionVerify that New Production template should display with 'Skip
	 *                    text generation/OCR for non-redacted docs' under Text
	 *                    section and Production should be generated successfully
	 *                    using same template
	 */
	@Test(description = "RPMXCON-63190", enabled = true, groups = { "regression" })
	public void verifyingTemplateInProductionComponent() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-63190 -Production component");
		base.stepInfo(
				"Verify that New Production template should display with 'Skip text generation/OCR for non-redacted docs' under Text section and Production should be generated successfully using same template");

		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "P" + Utility.dynamicNameAppender();
		String productionname1 = "P" + Utility.dynamicNameAppender();
		String Templatename = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.telecaSearchString);
		sessionSearch.bulkRelease(Input.securityGroup);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.addDATFieldAtSecondRow(Input.productionText, Input.tiffPageCountNam, Input.tiffPageCountText);
		page.fillingNativeSection();
		page.fillingTIFFSectionPrivDocs(tagname, Input.searchString4);
		page.fillingTextSection();
		page.navigateToNextSection();
		
		page.navigateToProductionPage();
		page.selectSavedTemplateAndManageTemplate(productionname, Templatename);
		driver.waitForPageToBeReady();
		page.getTextTab().waitAndClick(5);
		driver.scrollingToBottomofAPage();
		page.getCheckBoxCheckedVerification(page.getTextRadioBtn());
		base.validatingGetTextElement(page.getTextFirstRadioBtn(),"Do not OCR. Export blank text files");
		base.validatingGetTextElement(page.getTextSecondRadioBtn(),"OCR non-redacted documents without text");
		page.getCloseIconInManageTemplate().waitAndClick(5);
		
		page.navigateToProductionPage();
		page.baseInfoLoadTemplate(productionname1, Templatename);
		page.getCheckBoxCheckedVerification(page.chkIsDATSelected());
		page.getCheckBoxCheckedVerification(page.chkIsTIFFSelected());
		driver.scrollingToBottomofAPage();
		page.getTextTab().waitAndClick(5);
		page.getCheckBoxCheckedVerification(page.getTextRadioBtn());
		driver.scrollingToBottomofAPage();
		base.validatingGetTextElement(page.getTextFirstRadioBtn(),"Do not OCR. Export blank text files");
		base.validatingGetTextElement(page.getTextSecondRadioBtn(),"OCR non-redacted documents without text");
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
