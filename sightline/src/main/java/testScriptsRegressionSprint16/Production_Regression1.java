package testScriptsRegressionSprint16;

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

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Production_Regression1 {
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
	 * @author Brundha.T
	 *         No:RPMXCON-47915
	 * @Description:To Verify The failed status (Production Generation Failed) should be clickable and should provide the detailed information on why the generate failed.
	 **/

	@Test(description = "RPMXCON-47915", enabled = true)
	public void verifyingErrorMsgInGeneratePage() throws Exception {

		base = new BaseClass(driver);
		base.stepInfo("Test case Id:RPMXCON-47915- Production component");
		base.stepInfo("To Verify The failed status (Production Generation Failed) should be clickable and should provide the detailed information on why the generate failed.");
		UtilityLog.info(Input.prodPath);

		String foldername = "Prod_Folder" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String productionname1 = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname1);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname1);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickGenarateWithoutWait();
		page.getDocumentGeneratetext().isElementAvailable(60);
		page.verifyingFailedStatusInProduction(productionname1);
		loginPage.logout();
	}
	
	/**
	 * @author Brundha.T
	 *         No:RPMXCON-47996
	 * @Description:To Verify Placeholders for Tech Issue documents.
	 **/

	@Test(description = "RPMXCON-47996", enabled = true)
	public void verifyingPlaceholderTextForTechIssueDoc() throws Exception {

		base = new BaseClass(driver);
		base.stepInfo("Test case Id:RPMXCON-47996- Production component");
		base.stepInfo("To Verify Placeholders for Tech Issue documents.");
		UtilityLog.info(Input.prodPath);

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String foldername="Folder"+ Utility.dynamicNameAppender();
		String prefixID="P"+ Utility.dynamicNameAppender();
		String suffixID="S"+ Utility.dynamicNameAppender();
		
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname,"Technical Issue");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		
		sessionSearch.ViewInDocList();
		DocListPage doc=new DocListPage(driver);
		doc.documentSelection(3);
		doc.bulkTagExisting(tagname);
		
		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates=page.getRandomNumber(2);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.addDATFieldAtSecondRow(Input.productionText, Input.tiffPageCountNam, Input.tiffPageCountText);
		page.addDATFieldAtThirdRow(Input.docBasic, Input.docName, Input.documentID);
		page.fillingNativeSection();
		page.fillingTiffSectionTechIssueWithEnteringText(tagname,Input.tagNameTechnical);
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
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		String name = page.getProduction().getText().trim();
		driver.waitForPageToBeReady();
		File DatFile = new File(home + "/Downloads/VOL0001/Load Files/" + name + "_DAT.dat");
		File TiffFile = new File(home + "/Downloads/VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tiff");
		page.OCR_Verification_In_Generated_Tiff_tess4j(TiffFile,Input.tagNameTechnical);
		if (DatFile.exists()) {
			base.passedStep("Dat file is displayed as expected");
		} else {
			base.failedStep("Dat file is not displayed as expected");
		}
		int TiffPageCount =1;
		String line;
		List<String> lines = new ArrayList<>();
		BufferedReader brReader = new BufferedReader(new InputStreamReader(new FileInputStream(DatFile), "UTF16"));
		while ((line = brReader.readLine()) != null) {
			lines.add(line);
		}
			System.out.println(lines.get(1));
			String[] arrOfStr = lines.get(1).split("þþ");
			String output=arrOfStr[1];
				if (TiffPageCount==Integer.parseInt(output)) {
					base.passedStep("Tiff page count is displayed as expected");
				}else {base.failedStep("verification failed");	}
		brReader.close();
		loginPage.logout();
		
	}
	
	
	/**
	 * @author Brundha.T
	 *         No:RPMXCON-63078
	 * @Description:Verify that production should be generated successfully with default enabled native placeholder under TIFF section
	 **/

	@Test(description = "RPMXCON-63078", enabled = true)
	public void verifyingTheGeneratedFileType() throws Exception {

		base = new BaseClass(driver);
		base.stepInfo("Test case Id:RPMXCON-63078- Production component");
		base.stepInfo("Verify that production should be generated successfully with default enabled native placeholder under TIFF section");
		UtilityLog.info(Input.prodPath);

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID="P"+ Utility.dynamicNameAppender();
		String suffixID="S"+ Utility.dynamicNameAppender();
		
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname,"Select Tag Classification");

		SessionSearch sessionSearch = new SessionSearch(driver);
		this.driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionSearch.metaDataSearchInBasicSearch("DocFileType", "Spreadsheet",Input.testData1);
		sessionSearch.ViewInDocList();
		 DocListPage doclist=new DocListPage(driver);
		doclist.documentSelection(6);
		doclist.bulkTagExisting(tagname);
		
		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates=page.getRandomNumber(2);
		
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProductionAndSave(productionname);
		page.fillingDATSection();
		page.verifyingTheDefaultSelectedOptionInNative();
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();
		String home = System.getProperty("user.home");
		driver.waitForPageToBeReady();
			File Native = new File(home + "/Downloads/VOL0001/Natives/0001/" + prefixID + beginningBates + suffixID + ".xls");
			if (Native.exists()) {
				base.passedStep("Native file are generated correctly : " + prefixID + beginningBates + suffixID + ".xls");
			}else {
				base.failedStep("verification failed");
			}
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}
	
	/**
	 * @author Brundha.T
	 *         No:RPMXCON-47986
	 * @Description:To Verify Editing an existing draft production by clicking on Mark Incomplete
	 **/

	@Test(description = "RPMXCON-47986", enabled = true)
	public void verifyDraftedProduction() throws Exception {

		base = new BaseClass(driver);
		base.stepInfo("Test case Id:RPMXCON-47986- Production component");
		base.stepInfo("To Verify Editing an existing draft production by clicking on Mark Incomplete");
		UtilityLog.info(Input.prodPath);

		String tagname = "Tag" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		
		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.getMarkCompleteLink().waitAndClick(10);
		
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		page.verifyingDraftedProductionInComponentTab(productionname, tagname);
		
		loginPage.logout();
	}
	
	
	/**
	 * @author Brundha.T
	 *         No:RPMXCON-47995
	 * @Description:To Verify Admin will be able to regenerate an existing production (for the same configuration) with the previous bates numbers,(Before commit Bates Number).
	 **/

	@Test(description = "RPMXCON-47995", enabled = true)
	public void verfyBatesNumberInProduction() throws Exception {

		base = new BaseClass(driver);
		base.stepInfo("Test case Id:RPMXCON-47995- Production component");
		base.stepInfo("To Verify Admin will be able to regenerate an existing production (for the same configuration) with the previous bates numbers,(Before commit Bates Number).");
		UtilityLog.info(Input.prodPath);

		String foldername="Folder"+ Utility.dynamicNameAppender();
		String prefixID="P"+ Utility.dynamicNameAppender();
		String suffixID="S"+ Utility.dynamicNameAppender();
		
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		
		ProductionPage page = new ProductionPage(driver);
		String beginningBates=page.getRandomNumber(2);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSorting(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		page.gettext("Back").Click();
		String Batesno =page. getProd_BatesRange().getText();
		page.verifyProductionStatusInGenPage("Post-Generation QC checks Complete");
		page.navigateToProductionPage();
		page.getProductionNameLink(productionname).waitAndClick(5);
		page.gettext("Back").Click();
		page.getMarkInCompleteBtn().Click();
		page.getbtnReGenerateMarkComplete().waitAndClick(5);
		base.waitForElement(page.getbtnRegenerateContinue());
		page.getbtnRegenerateContinue().Click();
		page.getbtnContinueGeneration().isElementAvailable(80);
		if (page.getbtnContinueGeneration().isElementAvailable(1)) {
			page.getbtnContinueGeneration().Click();
		}	
		String BatesNumber =page. getProd_BatesRange().getText();
		base = new BaseClass(driver);
		base.textCompareEquals(Batesno, BatesNumber, "Bates number are equal expecetd", "Bates number are not equal as expecetd");
		loginPage.logout();
	}
	
	/**
	 * @author Brundha created on:NA modified by:NA TESTCASE No:RPMXCON-48004
	 * @Description:To Verify Split Count in production is actually splitting the folder directory.
	 */
	 @Test(description="RPMXCON-48004",enabled = true, groups = { "regression" })
	public void verifyTheSubFoldersAfterGenration() throws Exception {
		UtilityLog.info(Input.prodPath);
		base = new BaseClass(driver);
		base.stepInfo("RPMXCON-48004 -Production Sprint 10");
		base.stepInfo("To Verify Split Count in production is actually splitting the folder directory.");

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		int purehit=sessionSearch.basicContentSearch(Input.testData1);
		int Count=purehit-1;
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectPrivDocsInTiffSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		page.ProductionLocationSplitCount().Clear();
		page.ProductionLocationSplitCount().SendKeys(Integer.toString(Count));
		driver.scrollPageToTop();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();
		String home = System.getProperty("user.home");
		driver.waitForPageToBeReady();
			File ImageFile1 = new File(home+"\\Downloads\\VOL0001\\Images\\0001");
			if (ImageFile1.exists()) {
				base.passedStep("TIFF file is splited as expected");
			}else {
				base.failedStep("TIFF file is not splited as expected");}
			File ImageFile2 = new File(home+"\\Downloads\\VOL0001\\Images\\0002");
			if (ImageFile2.exists()) {
				base.passedStep("TIFF file is splited as expected");
			}else {
				base.failedStep("TIFF file is not splited as expected");
			}
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
