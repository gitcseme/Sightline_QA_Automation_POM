package sightline.productions;

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
import org.testng.annotations.Listeners;
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
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
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
		List<String> beforeSortOrder = new ArrayList<String>();
		List<String> afterSortOrder = new ArrayList<String>();
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test Cases Id : RPMXCON-47740");
		base.stepInfo(
				"To Verify sorting in Grid View from Productions page");

		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		
		// search for tag
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

	    //  Create Prod for failed state
		page.navigateToProductionPage();
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
		base=new BaseClass(driver);
		base.waitForElementCollection(page.getProdCrtDateGridView());	
		beforeSortOrder.addAll(base.getAvailableListofElements(page.getProdCrtDateGridView()));
		driver.scrollingToBottomofAPage();
		for(int i = 2; i <= Integer.parseInt(page.getLastPageGridView().getText()); i++) {
			driver.scrollingToBottomofAPage();
			if(page.getPageNumGridView(i).isElementAvailable(4)) {
				page.getPageNumGridView(i).waitAndClick(4);
				base.waitTime(1);	
				beforeSortOrder.addAll(base.getAvailableListofElements(page.getProdCrtDateGridView()));
			}
		}
		base.stepInfo("Before Sorting : " + beforeSortOrder);

		page.goToProductionGridView();
		base.waitForElement(page.getProdSortCrtDateGridView());
		page.getProdSortCrtDateGridView().Click();
		driver.waitForPageToBeReady();
		
		base.waitForElementCollection(page.getProdCrtDateGridView());
		afterSortOrder.addAll(base.getAvailableListofElements(page.getProdCrtDateGridView()));
		driver.scrollingToBottomofAPage();
		for(int i = 2; i <= Integer.parseInt(page.getLastPageGridView().getText()); i++) {
			driver.scrollingToBottomofAPage();
			if(page.getPageNumGridView(i).isElementAvailable(4)) {
				page.getPageNumGridView(i).waitAndClick(4);
				base.waitTime(1);	
				afterSortOrder.addAll(base.getAvailableListofElements(page.getProdCrtDateGridView()));
			}
		}
		base.stepInfo("After Sorting : " + afterSortOrder);
		base.verifyOriginalSortOrder(afterSortOrder, beforeSortOrder, "Ascending", true);
		base.stepInfo("Productions list sorted as per the selected list in Grid View");
		base.passedStep("To Verify sorting in Grid View from Productions page");
		loginPage.logout();
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
		
		// create tag and folder
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
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

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		// create tag 
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for tag
		sessionSearch.MetaDataSearchInBasicSearch("DocFileExtension", ".pdf");
		sessionSearch.bulkTagExisting(tagname);

	//  Create Prod for failed state
		page.navigateToProductionPage();
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

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		// create tag 
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for tag
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		
		// Pre req - Production For Completed
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
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
	
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		// create tag 
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for tag
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		
		//Create Prod For Failed State
		page.navigateToProductionPage();
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
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		// create tag 
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for tag
		int pureHit = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		
		page.navigateToProductionPage();
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

		base = new BaseClass(driver);
		base.stepInfo("RPMXCON-63088");
		String RedactName = "Redact" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String tagName = "Tag" + Utility.dynamicNameAppender();
		base.stepInfo(
				"Verify that if spreadsheet is redacted and Native placeholder is default enabled from "
				+ "TIFF/PDF section then PDF should be produced with natively placeholder");
 
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.selectproject(Input.largeVolDataProject);
		
		RedactionPage redactionpage = new RedactionPage(driver);
		redactionpage.navigateToRedactionsPageURL();
		redactionpage.AddRedaction(RedactName, Input.rmu1FullName);
		base.stepInfo("Basic meta data search");
		
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch("DocFileType", "Spreadsheet");
		sessionSearch.ViewInDocList();
		
		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(3);	
		doclist.viewSelectedDocumentsInDocView();
		
		DocViewPage doc = new DocViewPage(driver);
		driver.waitForPageToBeReady();
		doc.pageRedaction(RedactName);
		
		tagsAndFolderPage.createNewTagwithClassificationInRMU(tagName,  Input.tagNamePrev);

		SessionSearch sessionSearch1 = new SessionSearch(driver);
		sessionSearch1 = new SessionSearch(driver);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch1.ViewInDocList();
		DocListPage doclist1 = new DocListPage(driver);
		doclist1.documentSelection(1);	
		doclist.bulkTagExistingFromDoclist(tagName);
		
		String beginningBates = page.getRandomNumber(2);
		String productionname = "p" + Utility.dynamicNameAppender();	
		page.navigateToProductionPage();
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
		driver.waitForPageToBeReady();
		page.deleteFiles();
		page.extractFile();
		driver.waitForPageToBeReady();
		File pdfFile = new File(
				home + "/Downloads/VOL0001/PDF/0001/" + prefixID + beginningBates + suffixID +".pdf");
		String text = page.verifyTextinPDF(pdfFile, tagName);
		base.stepInfo(text);
			
		String productionname1 = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname1);
		page.fillingDATSection();
		page.fillingTIFFSectionwithNativelyPlaceholder(tagName);
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
		File tiffFile = new File(
				home + "/Downloads/VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tiff");
		page.OCR_Verification_In_Generated_Tiff_tess4j(tiffFile, tagName);
		loginPage.logout();
	}
	
	/**
	 * @author Sowndarya.Velraj created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-49181
	 * @Description:To verify that upon completion of commit, notification should be
	 *                 displayed on right top corner
	 **/

	@Test(description = "RPMXCON-49181", enabled = true, groups = { "regression" })
	public void verifyCommitMessage() throws Exception {

		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test case Id:RPMXCON-49181 Production Component Sprint 20");
		base.stepInfo("To verify that upon completion of commit, notification should be displayed on right top corner");
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		// create tag and folder
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);

		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		base = new BaseClass(driver);
		int initialBg = base.initialBgCount();
		page.toStartGenerate();
		base.waitForElement(page.getConfirmProductionCommit());
		page.getConfirmProductionCommit().waitAndClick(10);
		String expected = "Commit action has been started as a background task. You will be notified upon completion. Please refresh this page to see the latest status.";
		base.VerifySuccessMessage(expected);
		base.checkNotificationCount(initialBg, 1);
		saveSearch.verifyExecuteAndReleaseNotify(initialBg, 1);
		driver.Navigate().refresh();
		loginPage.logout();
	}
	
	/**
	 * @author sowndarya.velraj No:RPMXCON-49254
	 * @Description:To verify Production should be failed if Bates Numbers is duplicate
	 **/

	@Test(description = "RPMXCON-49254", enabled = true, groups = { "regression" })
	public void verfyBatesRangeFailedStatus() throws Exception {

		base = new BaseClass(driver);
		base.stepInfo("Test case Id:RPMXCON-49254- Production component");
		base.stepInfo("To verify Production should be failed if Bates Numbers is duplicate");
		UtilityLog.info(Input.prodPath);

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		String Prod_A = page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.toFillNumberingPageWithClickHereLink(1);
		page.navigateToNextSection();
		base.passedStep(Prod_A+ "is created with next bates number and is in  draft status");
		
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		String productionname2= "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		String Prod_B = page.addANewProduction(productionname2);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.toFillNumberingPageWithClickHereLink(1);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname2);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.passedStep(Prod_B+" is generated successfully");
		
		page.navigateToProductionPage();
		base.waitForElement(page.getProductionFromHomepage(Prod_A));
		page.getProductionFromHomepage(Prod_A).waitAndClick(10);
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		base.waitForElement(page.getbtnProductionGenerate());
		page.getbtnProductionGenerate().waitAndClick(10);
		page.getVerifyGenStatus("Reserving Bates").isElementAvailable(150);
		page.reservingBatesRangeFailedStatusInGenPage().isElementAvailable(10);
		base.passedStep("Production A failed in generation due to duplicate bates number");
		loginPage.logout();
		
	}
	
	/**
	 * @author sowndarya.velraj No:RPMXCON-46867
	 * @Description:To Verify Project Admin will have the ability to confirm production. Upon confirmation, bates numbers in the documents in the database shall be committed
	 **/

	@Test(description = "RPMXCON-46867", enabled = true, groups = { "regression" })
	public void verifyCommittedProdAsUneditable() throws Exception {

		base = new BaseClass(driver);
		base.stepInfo("Test case Id:RPMXCON-46867- Production component sprint 20");
		base.stepInfo("To Verify Project Admin will have the ability to confirm production. Upon confirmation, bates numbers in the documents in the database shall be committed");
		UtilityLog.info(Input.prodPath);

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		String foldername = "Folder" + Utility.dynamicNameAppender();
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		String productionname= "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.toFillNumberingPageWithClickHereLink(1);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		
		base.stepInfo("navigate back and try to mark incomplete");
		page.getQC_backbutton().waitAndClick(10);
		for (int i = 0; i < 4; i++) {
			base.waitForElement(page.getBackButton());
			page.getBackButton().waitAndClick(10);
		}
		page.getMarkInCompleteBtn().GetAttribute("disabled").contains("disabled");
		base.passedStep("After confirmation user cannot edit the Production.");
		loginPage.logout();
	
	}
	
	/**
	 * @author NA No:RPMXCON-47861
	 * @Description:Verify the regeneration of Production with same configuration and some new documents; before commit and confirm bates number for that Production
	 **/
	@Test(description = "RPMXCON-47861", enabled = true, groups = { "regression" })
	public void verifyRegenProdWithNewDoc() throws Exception {
		UtilityLog.info(Input.prodPath);
		base = new BaseClass(driver);
		base.stepInfo("Test Cases Id : RPMXCON-47861");
		base.stepInfo(
				"Verify the regeneration of Production with same configuration and some new documents; before commit and confirm bates number for that Production");
	
		tagname = "Tag" + Utility.dynamicNameAppender();
		productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String bates = "B" + Utility.dynamicNameAppender();
		int noOfDoc1 = 1;
		int noOfDoc2 = 4;
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		// create tag 	
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for tag
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch("DocFileType", "Spreadsheet");
		sessionSearch.ViewInDocList();
		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(noOfDoc1);
		doclist.bulkTagExistingFromDoclist(tagname);
		page.navigateToProductionPage();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSectionWithBates(Input.bates, Input.batesNumber, bates);
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.priviledgeDocCheck(true, tagname);
		List<String> expPrivGuard = page.privGuardTextBoxValue();
		page.fillingPrivGuardPage();	
		page.fillingProductionLocationPage(productionname);
		String expRootProdLocPage = Input.prodPath;
		String expProdOut = productionname;
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		String exptotaldoc = page.getDoc_Count().getText();
		page.clickBackBtnUntilElementFound(page.getBatesRange());
		
		SessionSearch sessionSearch1 = new SessionSearch(driver);
		sessionSearch1.navigateToSessionSearchPageURL();
		sessionSearch1.ViewInDocList();
		DocListPage doclist1 = new DocListPage(driver);
		doclist1.documentSelection(noOfDoc2);
		doclist1.bulkTagExistingFromDoclist(tagname);
		
		page.navigateToProductionPage();
		page.openExistingProduction(productionname);
		page.clickBackBtnUntilElementFound(page.getDATTab());

		driver.waitForPageToBeReady();
		page.verifyDATAndTIFFField();
		page.clickElementNthtime(page.getNextButton(), 2);
		String tagStatus = page.getSelecTaginDocSelTab(tagname).GetAttribute("aria-selected");
		System.out.println(tagStatus);
		page.clickElementNthtime(page.getNextButton(), 1);

		List<String> actPrivGuard = page.privGuardTextBoxValue();
		System.out.println(actPrivGuard);

		page.clickElementNthtime(page.getNextButton(), 1);
		String actRootProdLocPage = page.getProdRootinProdLOcTab().getText();
		String actProdOut = page.getProductionOutputLocation_ProductionDirectory().Value();
		System.out.println(actRootProdLocPage + actProdOut);
		page.clickElementNthtime(page.getNextButton(), 2);
		base.waitForElement(page.getbtnGenMarkIncomplete());
		page.getbtnGenMarkIncomplete().waitAndClick(2);
		
		page.clickBackBtnUntilElementFound(page.getBtnMarkIncomplete());
		driver.waitForPageToBeReady();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.clickMArkCompleteMutipleTimes(2);
		driver.waitForPageToBeReady();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		String acttotaldoc = page.getDoc_Count().getText();
		page.clickBackBtnUntilElementFound(page.getBatesRange());
		String actBatesnum = page.getBatesRange().getText();
		
		SoftAssert asserts = new SoftAssert();
		asserts.assertEquals(tagStatus, "true");
		System.out.println(actPrivGuard);
		asserts.assertEquals(expPrivGuard, actPrivGuard);
		asserts.assertEquals(expRootProdLocPage, actRootProdLocPage);
		asserts.assertEquals(expProdOut, actProdOut);
		asserts.assertTrue(actBatesnum.contains(prefixID));
		asserts.assertAll();
		
		if(Integer.parseInt(exptotaldoc) < Integer.parseInt(acttotaldoc)) {
			base.passedStep("Production should generate successfully including new documents");
		} else {
			base.failedStep("Production should generate successfully Not including new documents");
		}	
		base.passedStep("Verify the regeneration of Production with same configuration and some new documents; before commit and confirm bates number for that Production");
		loginPage.logout();
	}



	/**
	 * @author sowndarya.velraj No:RPMXCON-47862
	 * @Description:Verify the regeneration of already produced document with different location
	 **/
	@Test(description = "RPMXCON-47862", enabled = true, groups = { "regression" })
	public void verifyRegenProdWithNewLoc() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		base.stepInfo("Test Cases Id : RPMXCON-47862");
		base.stepInfo(
				"Verify the regeneration of already produced document with different location");
		tagname = "Tag" + Utility.dynamicNameAppender();
		productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String bates = "B" + Utility.dynamicNameAppender();
		int noOfDoc1 = 2;
		String location = "P" + Utility.dynamicNameAppender();
		
		
//		// create tag 
		base = new BaseClass(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for tag
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch("DocFileType", "Spreadsheet");
		sessionSearch.ViewInDocList();
		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(noOfDoc1);
		doclist.bulkTagExisting(tagname);
		
		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSectionWithBates(Input.bates, Input.batesNumber, bates);
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.priviledgeDocCheck(true, tagname);
		List<String> expPrivGuard = page.privGuardTextBoxValue();
		page.fillingPrivGuardPage();	
		page.fillingProductionLocationPage(productionname);
		String expRootProdLocPage = Input.prodPath;
		String expProdOut = productionname;
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		
		page.navigateToProductionPage();
		page.openExistingProduction(productionname);
		page.clickBackBtnUntilElementFound(page.getDATTab());
		driver.waitForPageToBeReady();
		page.verifyDATAndTIFFField();
		page.clickElementNthtime(page.getNextButton(), 2);

		String tagStatus = page.getSelecTaginDocSelTab(tagname).GetAttribute("aria-selected");
		System.out.println(tagStatus);
		page.clickElementNthtime(page.getNextButton(), 1);
		List<String> actPrivGuard = page.privGuardTextBoxValue();
		System.out.println(actPrivGuard);
		page.clickElementNthtime(page.getNextButton(), 1);
		String actRootProdLocPage = page.getProdRootinProdLOcTab().getText();
		String actProdOut = page.getProductionOutputLocation_ProductionDirectory().Value();
		System.out.println(actRootProdLocPage + actProdOut);
		page.clickElementNthtime(page.getNextButton(), 2);
		base.waitForElement(page.getbtnGenMarkIncomplete());
		page.getbtnGenMarkIncomplete().waitAndClick(2);
		
		page.clickBackBtnUntilElementFound(page.getProdLocMarkIncomplete());
		driver.waitForPageToBeReady();
		page.fillingProductionLocationPage(location);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		driver.waitForPageToBeReady();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		String name = page.getProduction().getText().trim();
		
		SoftAssert asserts = new SoftAssert();
		asserts.assertEquals(tagStatus, "true");
		System.out.println(actPrivGuard);
		asserts.assertEquals(expPrivGuard, actPrivGuard);
		asserts.assertEquals(expRootProdLocPage, actRootProdLocPage);
		asserts.assertEquals(expProdOut, actProdOut);
		asserts.assertAll();
		
		driver.waitForPageToBeReady();
		File DatFile = new File(home + "/Downloads/VOL0001/Load Files/" + name + "_DAT.dat");
		if (DatFile.exists()) {
			base.passedStep("Generated New Location");
		} else {
			base.failedStep("Not Generated New Location");
		}
		base.passedStep("Verify the regeneration of already produced document with different location");
		loginPage.logout();
	}
	
	/**
	 * @author NA Testcase No:RPMXCON-47970
	 * @Description: Verify Bates Number Generated is correct for All Documents in Production
	 **/
	@Test(description = "RPMXCON-47970", enabled = true, groups = { "regression" })
	public void verifyBatesNoSyncwithProdDoc() throws Exception {
		
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		base.stepInfo("Test Cases Id : RPMXCON-47970");
		base.stepInfo(
				"To Verify Bates Number Generated is correct for All Documents in Production");
		tagname = "Tag" + Utility.dynamicNameAppender();
		productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		
		// create tag 
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for tag
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		
		// Verifying Tiff with Multi Page
		String beginningBates = page.getRandomNumber(2);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFWithMultiPage(tagname);
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
		base.waitUntilFileDownload();
		driver.waitForPageToBeReady();
		page.deleteFiles();
		page.extractFile();
		String home = System.getProperty("user.home");
		File dirPath = new File(home + "/Downloads/VOL0001/Images/0001");
		page.verifyBatesSyncWithAllDoc(dirPath, prefixID, suffixID);
	
		base.passedStep("Verified - Bates Number Generated is correct for All Documents in Production");
		loginPage.logout();
		
	}
	
	/**
	 * @author NA Testcase No:RPMXCON-63081
	 * @Description: Verify production regeneration without any change in default enabled native placeholder under PDF section
	 **/
	@Test(description = "RPMXCON-63081", enabled = true, groups = { "regression" })
	public void verifyProdRegeFrDefNatPHPDF() throws Exception {
		
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		base.stepInfo("Test Cases Id : RPMXCON-63081");
		base.stepInfo("To Verify production regeneration without any change in "
				+ "default enabled native placeholder under PDF section");

		tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		int doccount = 1;
		
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		
		base.stepInfo("perform basic search and bulk folder");
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch("DocFileType", "spreadsheet");
		sessionSearch.ViewInDocList();

		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(doccount);
		doclist.bulkTagExisting(tagname);

		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSectionwithNativelyPlaceholder(tagname);
		driver.waitForPageToBeReady();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPageAndPassingText(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageAndRegeneratingAgain();
		
		page.getbtnContinueGeneration().isElementAvailable(320);
		if (page.getbtnContinueGeneration().isDisplayed()) {
			base.waitForElement(page.getbtnContinueGeneration());
			page.getbtnContinueGeneration().waitAndClick(10);
		}
		base.waitForElement(page.getQC_Download());
		page.getQC_Download().Click();
		base.waitTillElemetToBeClickable(page.getQC_Download());
		base.waitForElement(page.getQC_Downloadbutton_allfiles());
		page.getQC_Downloadbutton_allfiles().Click();
		base.waitUntilFileDownload();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		String name = page.getProduction().getText().trim();
		driver.waitForPageToBeReady();
		page.deleteFiles();
		page.extractFile();
		driver.waitForPageToBeReady();

		File Native = new File(
				home + "/Downloads/VOL0001/Natives/0001/" + prefixID + beginningBates + suffixID + ".xls");
		File DatFile = new File(home + "/Downloads/VOL0001/" + name + "/" + name + "_DAT.dat");
		File pdfFile = new File(
				home + "/Downloads/VOL0001/PDF/0001/" + prefixID + beginningBates + suffixID + ".pdf");
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
			base.passedStep("PDF is generated successfully");
		} else {
			base.failedStep("PDF is not generated successfully");
		}
		base.passedStep("Verify production regeneration without any change in "
				+ "default enabled native placeholder under PDF section");
		loginPage.logout();
	}
	
	/**
	 * @author NA Testcase No:RPMXCON-49819
	 * @Description: Verify that Pagination the next tiles get loaded on 'Manage Production' screen
	 **/
	@Test(description = "RPMXCON-49819", enabled = true, groups = { "regression" })
	public void verifyPaginationNextTilesgetLoad() throws Exception {
		
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		base.stepInfo("Test Cases Id : RPMXCON-49819");
		base.stepInfo("Verify that Pagination the next tiles get loaded on 'Manage Production' screen.");	
		
		page.navigateToProductionPage();
	    page.verifyLoadMorePagination();
		base.passedStep("Verified - that Pagination the next tiles get loaded on 'Manage Production' screen.");
		loginPage.logout();
	}
	
	///
	/**
     * @author NA Testcase No:RPMXCON-47863
     * @Description: verify regenerate of production, before commit and confirm bates number, overwrites any previously produced document(s)
     **/
	@Test(description = "RPMXCON-47863", enabled = true, groups = { "regression" })
	public void verifyRegenProdOverwritePrePD() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test Cases Id : RPMXCON-47863");
		base.stepInfo("To verify regenerate of production, before commit and confirm bates number, overwrites any previously produced document(s).");
	
		tagname = "Tag" + Utility.dynamicNameAppender();
		productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String bates = "B" + Utility.dynamicNameAppender();
		
		// create tag 
		tagsAndFolderPage.createNewTagwithClassification(tagname,  Input.tagNamePrev);

		// search for tag
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		
		String productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSectionWithBates(Input.bates, Input.batesNumber, bates);
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.priviledgeDocCheck(true, tagname);
		List<String> expPrivGuard = page.privGuardTextBoxValue();
		page.fillingPrivGuardPage();	
		page.fillingProductionLocationPage(productionname);
		String expRootProdLocPage = Input.prodPath;
		String expProdOut = productionname;
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		int exptotaldoc = page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		page.clickBackBtnUntilElementFound(page.getBatesRange());
		String expBatesnum = page.getBatesRange().getText();
				
		page.navigateToProductionPage();
		page.openExistingProduction(productionname);
		page.clickBackBtnUntilElementFound(page.getDATTab());

		driver.waitForPageToBeReady();
		page.verifyDATAndTIFFField();
		page.clickElementNthtime(page.getNextButton(), 2);
		String tagStatus = page.getSelecTaginDocSelTab(tagname).GetAttribute("aria-selected");
		System.out.println(tagStatus);
		page.clickElementNthtime(page.getNextButton(), 1);

		List<String> actPrivGuard = page.privGuardTextBoxValue();
		System.out.println(actPrivGuard);
	

		page.clickElementNthtime(page.getNextButton(), 1);
		String actRootProdLocPage = page.getProdRootinProdLOcTab().getText();
		String actProdOut = page.getProductionOutputLocation_ProductionDirectory().Value();
		System.out.println(actRootProdLocPage + actProdOut);
		page.clickElementNthtime(page.getNextButton(), 2);
		base.waitForElement(page.getbtnGenMarkIncomplete());
		page.getbtnGenMarkIncomplete().waitAndClick(2);
		
		page.clickBackBtnUntilElementFound(page.getBtnMarkIncomplete());
		driver.waitForPageToBeReady();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.clickMArkCompleteMutipleTimes(2);
		driver.waitForPageToBeReady();
	    int acttotaldoc = page.fillingGeneratePageWithContinueGenerationPopup();
		page.clickBackBtnUntilElementFound(page.getBatesRange());
		String actBatesnum = page.getBatesRange().getText();
		
		
		SoftAssert asserts = new SoftAssert();
		asserts.assertEquals(tagStatus, "true");
		System.out.println(actPrivGuard);
		asserts.assertEquals(expPrivGuard, actPrivGuard);
		asserts.assertEquals(expRootProdLocPage, actRootProdLocPage);
		asserts.assertEquals(expProdOut, actProdOut);	
		asserts.assertAll();
		
		if(exptotaldoc == acttotaldoc && expBatesnum.equals(actBatesnum)){
			base.passedStep("Same bates number & overwrites previously produced document(s).");
		} else {
			base.failedStep("Not Same bates number & overwrites previously produced document(s).");
		}	
		base.passedStep("Verified - regenerate of production, before commit and confirm bates number, "
				+ "overwrites any previously produced document(s).");
	    loginPage.logout();
	}
	
	/**
     * @author sowndarya.velraj
     * @Description: Verify that if spreadsheet is priv and Priv, Native placeholder
     *               is default enabled from TIFF section then production should be
     *               generated for Priv placeholder [RPMXCON-63085]
     **/
    @Test(description = "RPMXCON-63085", enabled = true, dataProvider = "Users", groups = { "regression" })
    public void verifyProdRegeFrDefNatPHpdf(String userName, String password) throws Exception {
    	
        tagname = "Tag" + Utility.dynamicNameAppender();
        String prefixID = "A_" + Utility.dynamicNameAppender();
        String suffixID = "_P" + Utility.dynamicNameAppender();
        int doccount = 1;
        String beginningBates = page.getRandomNumber(2);
        productionname = "p" + Utility.dynamicNameAppender();
        
        loginPage.loginToSightLine(userName, password);
        base.stepInfo("Logged in as" + userName);
       base.stepInfo("Test Cases Id : RPMXCON-63085 Production");
        base.stepInfo(
                "Verify that if spreadsheet is priv and Priv, Native placeholder is default enabled from TIFF section then production should be generated for Priv placeholder");

       if (userName.equals(Input.pa1userName)) {
            tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
        } else {
            tagsAndFolderPage.createNewTagwithClassificationInRMU(tagname, Input.tagNamePrev);
        }

       sessionSearch.basicMetaDataSearch(Input.docFileType, null, "spreadsheet", null);
        sessionSearch.ViewInDocList();

       DocListPage doclist = new DocListPage(driver);
        doclist.documentSelection(doccount);
        doclist.bulkTagExisting(tagname);

       page.navigateToProductionPage();
        page.selectingDefaultSecurityGroup();
        page.addANewProduction(productionname);
        page.fillingDATSection();
        page.fillingNativeSection();
        page.tiffBrandingSelection(tagname);
        page.tiffPrivilegeDocumentSelection(tagname);
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

       String name = page.getProduction().getText().trim();
        String home = System.getProperty("user.home");
        File file = new File(home + "/Downloads/" + name + ".zip");
        File file1 = new File(Input.fileDownloadLocation + name + ".zip");

        page.extractFile();
        if (file.exists()) {
            driver.waitForPageToBeReady();
            file.delete();
        } else if (file1.exists()) {
            driver.waitForPageToBeReady();
            file1.delete();
        }

       File Native = new File(Input.fileDownloadLocation+name+"\\VOL0001\\"+name+ "\\" + name + "_Native");
        File DatFile = new File(Input.fileDownloadLocation+name+"\\VOL0001\\"+name+ "\\" + name + "_DAT.dat");
        File tiffFile = new File(Input.fileDownloadLocation+name+"\\VOL0001\\"+name+ "\\" + name + "TIFF.OPT");
        System.out.println(Native);
        System.out.println(DatFile);
        System.out.println(tiffFile);

       if (Native.exists()) {
            base.passedStep("Native placeholder generated for the selected file type ");
        } else {
            base.failedMessage("Native placeholder Not generated for the selected file type ");
        }
        if (DatFile.exists()) {
            base.passedStep("Dat file is exists in generated production");
        } else {
            base.failedMessage("Dat file is not displayed as expected");
        }
        if (tiffFile.exists()) {
            base.passedStep("Pdf is generated successfully");
        } else {
            base.failedMessage("Pdf is not generated successfully");
        }
        base.passedStep(
                "Verified - production regeneration with change(additional, change existing file type to spreadsheet only) "
                        + "in default enabled native placeholder under PDF section");
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
