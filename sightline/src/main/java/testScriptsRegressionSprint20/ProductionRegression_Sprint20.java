package testScriptsRegressionSprint20;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.RedactionPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ProductionRegression_Sprint20 {
	

	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	DocViewPage docView;
	ProductionPage page;
	SessionSearch sessionSearch;
	SavedSearch saveSearch;
	TagsAndFoldersPage tagsAndFolderPage;
	Utility utility;
	String foldername;
	String tagname;
	String productionname;
	SoftAssert softAssertion;

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
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());

		driver = new Driver();
		loginPage = new LoginPage(driver);
		softAssertion = new SoftAssert();
		page = new ProductionPage(driver);
		saveSearch = new SavedSearch(driver);
		sessionSearch = new SessionSearch(driver);
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
	}

	@DataProvider(name = "Users")
	public Object[][] users() {
		Object[][] users = { { Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password } };
		return users;
	}
	
	/**
	 * @author NA created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47794
	 * @Description:To Verify the default selections in Production component Native section
	 **/
	@Test(description = "RPMXCON-47794", enabled = true, groups = { "regression" })
	public void verifyDefSelecinNativeComp() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test Cases Id : RPMXCON-47794");
		base.stepInfo(
				"To Verify the default selections in Production component Native section");
		base = new BaseClass(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		
		base.waitForElement(page.getNativeTab());
		page.getNativeTab().waitAndClick(3);
	
		// Verifying Default File Type In Native
		String spreadSheet = page.getNativeCheckBoxChecked("Spreadsheets").GetAttribute("checked");
		String multiMedia = page.getNativeCheckBoxChecked("Multimedia").GetAttribute("checked");
		System.out.println(multiMedia);
		if(spreadSheet.equalsIgnoreCase("true") && multiMedia.equalsIgnoreCase("true")) {
		base.passedStep("By default 'Spreadsheets (.xls, .xlsx, .csv, etc.)	\r\n"
					+ "Multimedia (.wav, .mp3, .mp4, etc.)' check box gets chekced");
		}else{
		base.failedStep("By default 'Spreadsheets (.xls, .xlsx, .csv, etc.)	\r\n"
					+ "Multimedia (.wav, .mp3, .mp4, etc.)' check box not gets chekced");
		}
		
		base.waitForElement(page.getAdvancedTabInNative());
		page.getAdvancedTabInNative().waitAndClick(3);
		driver.scrollingToBottomofAPage();
		
		// Verifying LF Toogle in Native Component
		if(page.getNative_GenerateLoadFileLST().Selected() == false) {
		base.passedStep("LF Toogle Not Selected as Expected");
		}else{
		base.failedStep("LF Toogle Selected By Default");
		}
		
		//Verifying Radio Btn in native Component
		if(page.getDefRadioinNativeTab().GetAttribute("checked").equalsIgnoreCase("true")) {
		base.passedStep("Complete Families of Privileged and Redacted Documents Selected as Default");
		}else {
		base.failedStep("Complete Families of Privileged and Redacted Documents Not Selected as Default");		
		}
		
		base.passedStep("Verified - that default selections in Production component Native section");
		loginPage.logout();
	}
	
	/**
	 * @author NA created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47740
	 * @Description:To Verify sorting in Grid View from Productions page
	 **/
	@Test(description = "RPMXCON-47740", enabled = true, groups = { "regression" })
	public void verifyGridViewInProdPage() throws Exception {
		List<String> expProdStatusOrder = new ArrayList<String>();
		List<String> actProdStatusOrder = new ArrayList<String>();
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test Cases Id : RPMXCON-47740");
		base.stepInfo(
				"To Verify sorting in Grid View from Productions page");

		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

//		// Pre-requisites
//		// create tag 
		base = new BaseClass(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for tag
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

//	//  Create Prod for failed state
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		base.waitForElement(page.getBeginningBates());
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickGenarateWaitForRegenarate();

		// Create Prod for Draft state
		page = new ProductionPage(driver);
		beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();

//		  Create Prod for Inprogress state
		page = new ProductionPage(driver);
		beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickOnGenerateButton();

		page.goToProductionGridView();
		base.waitForElementCollection(page.getProdCrtDateGridView());	
		expProdStatusOrder.addAll(base.getAvailableListofElements(page.getProdCrtDateGridView()));
		driver.scrollingToBottomofAPage();
		for(int i = 2; i <= Integer.parseInt(page.getLastPageGridView().getText()); i++) {
			driver.scrollingToBottomofAPage();
			if(page.getPageNumGridView(i).isElementAvailable(4)) {
				page.getPageNumGridView(i).waitAndClick(4);
				base.waitTime(1);	
				expProdStatusOrder.addAll(base.getAvailableListofElements(page.getProdCrtDateGridView()));
			}
		}
		
	    Collections.sort(expProdStatusOrder);   
		System.out.println(expProdStatusOrder);
		base.stepInfo("Before Sorting : " + expProdStatusOrder);

		page.goToProductionGridView();
		base.waitForElement(page.getProdSortCrtDateGridView());
		page.getProdSortCrtDateGridView().Click();
		driver.waitForPageToBeReady();
		
		base.waitForElementCollection(page.getProdCrtDateGridView());
		actProdStatusOrder.addAll(base.getAvailableListofElements(page.getProdCrtDateGridView()));
		driver.scrollingToBottomofAPage();
		for(int i = 2; i <= Integer.parseInt(page.getLastPageGridView().getText()); i++) {
			driver.scrollingToBottomofAPage();
			if(page.getPageNumGridView(i).isElementAvailable(4)) {
				page.getPageNumGridView(i).waitAndClick(4);
				base.waitTime(1);	
				actProdStatusOrder.addAll(base.getAvailableListofElements(page.getProdCrtDateGridView()));
			}
		}
		base.stepInfo("After Sorting : " + actProdStatusOrder);

		if (expProdStatusOrder.equals(actProdStatusOrder)) {
			base.passedStep("Productions list sorted as per the selected list in Grid View   ");
		} else {
			base.failedStep("Productions list Not sorted as per the selected list in Grid View   ");
		}
	}
	
	/**
	 * @author NA created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-47790
	 * @Description:To Verify toggle control works for the Filepath and Volume under the Production Location
	 **/
	@Test(description = "RPMXCON-47790", enabled = true, groups = { "regression" })
	public void verifyToCtrlAndVolProdLocation() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test Cases Id : RPMXCON-47790");
		base.stepInfo(
				"To Verify toggle control works for the Filepath and Volume under the Production Location.");

		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		productionname = "p" + Utility.dynamicNameAppender();		
		
//		// create tag and folder
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch("DocFileType", "mp3");
		sessionSearch.ViewInDocList();
		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(1);
		doclist.bulkTagExisting(tagname);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		base.waitForElement(page.getLoadFilePath());
		page.getLoadFilePath().waitAndClick(4);
		
		base.waitForElement(page.getVolumeIncluded());
		page.getVolumeIncluded().waitAndClick(4);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		base.waitUntilFileDownload();
		
		String expUrl = "\\\\ld9nas02.consilio.com\\UKSLPERF\\Productions\\Automation\\" + productionname;
		String actUrl = page.getCopyPathUrl().GetAttribute("value");
		System.out.println(expUrl);
		System.out.println(actUrl);
		base.stepInfo(actUrl);
		softAssertion.assertEquals(expUrl, actUrl);
		softAssertion.assertAll();
		base.passedStep("Verified - toggle control works for the Filepath and Volume under the Production Location.");
		loginPage.logout();
	}
	
	/**
	 * @author Sowndarya.velraj  created on:NA modified by:NA TESTCASE No:RPMXCON-47973
	 * @Description:To Verify Production generation for PDF and Excel Docs
	 **/
	@Test(description = "RPMXCON-47973", enabled = true, groups = { "regression" })
	public void verifyPDFAndExcelFileDocs() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test Cases Id : RPMXCON-47973");
		base.stepInfo("To Verify Production generation for PDF and Excel Docs");

		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

//		// Pre-requisites
//		// create tag 
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		BaseClass base = new BaseClass(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for tag
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.MetaDataSearchInBasicSearch("DocFileExtension", ".pdf");
		sessionSearch.bulkTagExisting(tagname);

//	//  Create Prod for failed state
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		loginPage.logout();	
		}
	
	/**
	 * @author sowndarya.velraj created on:NA modified by:NA TESTCASE No:RPMXCON-47872
	 * @Description:To verify Bates No Generation should be in Sync, when using continue from Previous bates No
	 **/
	@Test(description = "RPMXCON-47872", enabled = true, groups = { "regression" })
	public void verifyBatNoGenSyncwithPreBatNo() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test Cases Id : RPMXCON-47872");
		base.stepInfo(
				"To verify Bates No Generation should be in Sync, when using continue from Previous bates No");

		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);

		// create tag 
		base = new BaseClass(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for tag
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		
		// Pre req - Production For Completed
		page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		
		page.navigateToProductionPage();
		driver.Navigate().refresh();
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();	
		base.waitForElement(page.getClickHereLink());
		page.getClickHereLink().Click();	
		base.stepInfo("Click Here Link Clicked and Next Bates Num Popup displaying Successfully");
		base.waitForElement(page.getSelectNextBatesNumber(prefixID, suffixID));
		page.getSelectNextBatesNumber(prefixID, suffixID).Click();
		base.stepInfo("Bates Number Selected in Next Bates Num Popup ");
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();	
		base.waitForElement(page.getQC_backbutton());
		page.getQC_backbutton().waitAndClick(3);
		
		base.waitForElement(page.getProd_BatesRange());	
		String actBatesno = page.getProd_BatesRange().getText();
		System.out.println(actBatesno);		
		if(actBatesno.contains(prefixID) && actBatesno.contains(suffixID)) {
		base.passedStep("Bates Numbersync with previous bates number ");
		}else {
		base.failedStep("Bates Number Not sync with previous bates number ");
		}	
		base.passedStep("Verified - Bates No Generation should be in Sync, when using continue from Previous bates No");
		loginPage.logout();
		
	}
	
	/**
	 * @author sowndarya.velraj created on:NA modified by:NA TESTCASE No:RPMXCON-49060
	 * @Description:To verify that the 'production start date' should contain and present the date when the production regeneration was started from Scratch
	 **/
	@Test(description = "RPMXCON-49060", enabled = true, groups = { "regression" })
	public void verifyProdStartDateafterRegen() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test Cases Id : RPMXCON-49060");
		base.stepInfo(
				"To verify that the 'production start date' should contain and present the date when the production regeneration was started from Scratch");
		tagname = "Tag" + Utility.dynamicNameAppender();
		productionname = "p" + Utility.dynamicNameAppender();
	
		// create tag 
		base = new BaseClass(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for tag
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		
		//Create Prod For Failed State
		ProductionPage page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		base.waitForElement(page.getBeginningBates());
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.clickGenarateWaitForRegenarate();
		
		// Open Existing Failed Production and Perform regenerate
		page.openExistingProduction(productionname);		
		base.waitForElement(page.getbtnRegenerate());
		page.getbtnRegenerate().Click();
		base.waitForElement(page.getRegenerateAllRadioBtn());
		page.getRegenerateAllRadioBtn().Click();
		base.waitForElement(page.getbtnRegenerateContinue());
		page.getbtnRegenerateContinue().Click();
		base.stepInfo("Re-Generate started Successfully");
				
		String expStartDate = base.getcurrentdateinGMT();
		System.out.println(expStartDate);
		base.stepInfo("Expected Time: " + expStartDate);
		page.goToProductionGridView();
		base.waitForElement(page.getGridProdValues(productionname, 6));
		String actStartDate = page.getGridProdValues(productionname, 6).getText();
		System.out.println(actStartDate);
		base.stepInfo("Actual Time: " + actStartDate);
		
		if(actStartDate.contains(expStartDate)) {
		base.passedStep("The 'production start date' contain and present the date when the last regenerate was started.");
		}else {
		base.failedStep("The 'production start date' not contain and present the date when the last regenerate was started.");	
		}	
		
		base.passedStep("Verified - that the 'production start date' should contain and present the date "
				+ "when the production regeneration was started from Scratch");
		loginPage.logout();
	}
	
	/**
	 * @author NA created on:NA modified by:NA TESTCASE No:RPMXCON-47913
	 * @Description:To Verify in production, the placeholders enabled for priv docs, Generated Priv Doc (PDF/TIFF/Text) should contain Placeholder with Branding
	 **/
	@Test(description = "RPMXCON-47913", enabled = true, groups = { "regression" })
	public void verifyProdPhEnabPrivDocsCntPhBrand() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test Cases Id : RPMXCON-47913");
		base.stepInfo(
				"To Verify in production, the placeholders enabled for priv docs, Generated Priv Doc (PDF/TIFF/Text) should contain Placeholder with Branding.");
		tagname = "Tag" + Utility.dynamicNameAppender();
		productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String brandingString = Input.searchString4;
		
		// create tag 
		base = new BaseClass(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for tag
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		int pureHit = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSection(tagname, tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		String privCount = page.getPrivDocCountInSummaryPage().getText();
		base.stepInfo("Priv Doc Count in Summary Page: " + privCount);
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.waitUntilFileDownload();
		driver.waitForPageToBeReady();
		page.deleteFiles();
		page.extractFile();
		
		String home = System.getProperty("user.home");
		String name =prefixID + beginningBates +suffixID;
		driver.waitForPageToBeReady();
		File file = new File(home + "/Downloads/VOL0001/PDF/0001/" + name + ".pdf");	
		if (file.exists()) {
		try {
				String url = home + "/Downloads/VOL0001/PDF/0001/";
				String content = page.verifyTopCenterBrandingInPDF(url, name+".pdf", brandingString ,0);
				System.out.println(content);
				base.passedStep("Verified - that branding specified (in the header and footer) applied to the placeholder documents");
				
		}catch (IOException e) {
				e.printStackTrace();
		}
		}else {
			base.failedStep("File Not Exists");
		}
		
		if(privCount.equals(String.valueOf(pureHit))) {
			base.passedStep("Privileged Documents' count displays correctly in Privileged Document Summary");
		}else {
			base.failedStep("Privileged Documents' count Not displays correctly in Privileged Document Summary");
		}
		
		base.passedStep("verified - that in production, the placeholders enabled for priv docs, "
				+ "Generated Priv Doc (PDF/TIFF/Text) should contain Placeholder with Branding.");
		loginPage.logout();
	}
	
	/**
	 * @author NA created on:NA modified by:NA TESTCASE No:RPMXCON-63088
	 * @Description:Verify that if spreadsheet is redacted and Native placeholder is default enabled from TIFF/PDF section then PDF should be produced with natively placeholder
	 **/
	@Test(description = "RPMXCON-63088", enabled = true, groups = { "regression" })
	public void verifyRedactNativePHTiffPdf() throws Exception {
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base = new BaseClass(driver);
		base.stepInfo("RPMXCON-63088");
		String RedactName = "Redact" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String tagName = "Tag" + Utility.dynamicNameAppender();
		base.stepInfo(
				"Verify that if spreadsheet is redacted and Native placeholder is default enabled from "
				+ "TIFF/PDF section then PDF should be produced with natively placeholder");

		RedactionPage redactionpage = new RedactionPage(driver);
		redactionpage.navigateToRedactionsPageURL();
		redactionpage.AddRedaction(RedactName, Input.rmu1FullName);

		SessionSearch sessionSearch = new SessionSearch(driver);
		base.stepInfo("Basic meta data search");
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch("DocFileType", "spreadsheet");
		sessionSearch.ViewInDocList();
		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(1);	
		doclist.viewSelectedDocumentsInDocView();
		DocViewPage doc = new DocViewPage(driver);
		driver.waitForPageToBeReady();
		doc.pageRedaction(RedactName);
		
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassificationInRMU(tagName,  Input.tagNamePrev);

		SessionSearch sessionSearch1 = new SessionSearch(driver);
		sessionSearch1 = new SessionSearch(driver);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch1.ViewInDocList();
		DocListPage doclist1 = new DocListPage(driver);
		doclist1.documentSelection(1);	
		doclist.bulkTagExistingFromDoclist(tagName);
		
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		String productionname = "p" + Utility.dynamicNameAppender();		
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSectionwithNativelyPlaceholder(tagName);
		driver.waitForPageToBeReady();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();	
		base.waitUntilFileDownload();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		String name = page.getProduction().getText().trim();
		driver.waitForPageToBeReady();
		page.deleteFiles();
		page.extractFile();
		driver.waitForPageToBeReady();

		File DatFile = new File(home + "/Downloads/VOL0001/" + name + "/" + name + "_DAT.dat");
		File pdfFile = new File(home + "/Downloads/VOL0001/PDF/0001/" + prefixID + beginningBates + suffixID + ".pdf");
		File Native = new File(home + "/Downloads/VOL0001/Natives/0001/" + prefixID + beginningBates + suffixID + ".xls");
		System.out.println(DatFile);

		if (Native.exists()) {
		base.passedStep("Native placeholder generated for the selected file type ");
		} else {
		base.failedStep("Native placeholder Not generated for the selected file type ");
		}
		if (DatFile.exists()) {
		base.passedStep("Dat file is exists in generated production");
		} else {
		base.failedStep("Dat file is not displayed as expected");
		}
		if (pdfFile.exists()) {
		base.passedStep("Pdf is generated successfully");
		} else {
		base.failedStep("Pdf is not generated successfully");
		}
			
		String productionname1 = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname1);
		page.fillingDATSection();
		page.fillingPDFSectionwithNativelyPlaceholder(tagName);
		driver.waitForPageToBeReady();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagName);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname1);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		base.waitUntilFileDownload();
		driver.waitForPageToBeReady();
		String name1 = page.getProduction().getText().trim();
		driver.waitForPageToBeReady();
		page.deleteFiles();
		page.extractFile();
		driver.waitForPageToBeReady();
		File tiffFile = new File(home + "/Downloads/VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tiff");
		File DatFile1 = new File(home + "/Downloads/VOL0001/" + name1 + "/" + name1 + "_DAT.dat");
		File Native1 = new File(home + "/Downloads/VOL0001/Natives/0001/" + prefixID + beginningBates + suffixID + ".xls");
		System.out.println(DatFile);

		if (Native1.exists()) {
		base.passedStep("Native placeholder generated for the selected file type ");
		} else {
		base.failedStep("Native placeholder Not generated for the selected file type ");
		}
		if (DatFile1.exists()) {
		base.passedStep("Dat file is exists in generated production");
		} else {
		base.failedStep("Dat file is not displayed as expected");
		}
		if (tiffFile.exists()) {
		base.passedStep("Tiff is generated successfully");
		} else {
		base.failedStep("Tiff is not generated successfully");
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
