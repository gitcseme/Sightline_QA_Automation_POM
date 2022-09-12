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
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ProductionRegression_Sprint21 {
	

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
     * @author NA Testcase No:RPMXCON-47614
     * @Description: To Verify ProjectAdmin will be able to enter basic information on the self production wizard
     **/
	@Test(description = "RPMXCON-47614", enabled = true, groups = { "regression" })
	public void verifyBasicInfoSelfProdWiz() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("Test Cases Id : RPMXCON-47614");
		base.stepInfo("To Verify ProjectAdmin will be able to enter basic information on the self production wizard");
		String nameAlpha = Utility.randomCharacterAppender(5);
		String nameAlphaNum = "p" + Utility.dynamicNameAppender();	
		String nameSpChar = "p@" + Utility.dynamicNameAppender();
		String nameLenChar = Utility.randomCharacterAppender(260);	
		String expErrorFrSch = "Special characters are not allowed in a production name";
		String expErrorFrBlField = "It is mandatory to provide the production name";
		String expErrorLenChar = "Production name entered is too long";
		String productionname = "p" + Utility.dynamicNameAppender();
		String tempName = "Temp" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base = new BaseClass(driver);
		
//		Customize Template
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionWithBatesNumber();	
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.getProductionHomePage().waitAndClick(10);
		base.waitForElement(page.getprod_ActionButton_Reusable(productionname));
		page.getprod_ActionButton_Reusable(productionname).waitAndClick(5);
		base.waitForElement(page.getprod_Action_SaveTemplate_Reusable(productionname));
		page.getprod_Action_SaveTemplate_Reusable(productionname).waitAndClick(5);
		driver.waitForPageToBeReady();
		page.saveTemplate(tempName);
		
//		 Verifying Name Field
		page.navigatingToProductionHomePage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(nameSpChar);
		driver.waitForPageToBeReady();
		base.textFromElementToVerify(page.getProdNameError(), expErrorFrSch);
		
		page.navigatingToProductionHomePage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(nameLenChar);
		base.textFromElementToVerify(page.getProdNameError(), expErrorLenChar);
		
		page.navigatingToProductionHomePage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(nameAlpha);
		base.VerifySuccessMessageB("Mark Complete successful");
		page.navigatingToProductionHomePage();
		page.deleteProduction(nameAlpha);
		
		page.navigatingToProductionHomePage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(nameAlphaNum);
		base.VerifySuccessMessageB("Mark Complete successful");
		page.navigatingToProductionHomePage();
		page.deleteProduction(nameAlphaNum);
		page.navigatingToProductionHomePage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction("");
		base.textFromElementToVerify(page.getProdNameError(), expErrorFrBlField);
		
//		 Verifying Description Field
		page.navigatingToProductionHomePage();
		page.selectingDefaultSecurityGroup();
		base.waitForElement(page.getAddNewProductionbutton());
		page.getAddNewProductionbutton().Click();
		base.waitForElement(page.getProductionName());
		page.getProductionName().SendKeys(nameAlphaNum);
		base.waitForElement(page.getBasicInfoMarkComplete());
		page.getBasicInfoMarkComplete().waitAndClick(4);
		base.VerifySuccessMessageB("Mark Complete successful");
		page.navigatingToProductionHomePage();
		page.deleteProduction(nameAlphaNum);
		
//		Verifying Load Template
		page.navigatingToProductionHomePage();
		page.selectingDefaultSecurityGroup();
		page.addANewProductiontWithTemplate(nameAlphaNum, tempName);
		page.verifyDATAndTIFFField();
		page.navigateToNextSection();
		page.verifyingPrefixAndSuffixText(prefixID, suffixID);
		base.passedStep("");
		page.navigatingToProductionHomePage();
		page.deleteProduction(nameAlphaNum);
		
//		Verifying Save, Mark Complete, Next
		page.navigatingToProductionHomePage();
		page.selectingDefaultSecurityGroup();
		base.waitForElement(page.getAddNewProductionbutton());
		page.getAddNewProductionbutton().Click();
		base.waitForElement(page.getBasicInfoSave());
		page.getBasicInfoSave().waitAndClick(5);
		base.textFromElementToVerify(page.getProdNameError(), expErrorFrBlField);
		base.waitForElement(page.getBasicInfoMarkComplete());
		page.getBasicInfoMarkComplete().waitAndClick(4);
		base.textFromElementToVerify(page.getProdNameError(), expErrorFrBlField);
		
		page.navigatingToProductionHomePage();
		page.selectingDefaultSecurityGroup();
		base.waitForElement(page.getAddNewProductionbutton());
		page.getAddNewProductionbutton().Click();
		base.ValidateElement_Presence(page.getBasicInfoSaveDisable(), "Next Button Disabled As Expected");
		base.waitForElement(page.getProductionName());
		page.getProductionName().SendKeys(nameAlphaNum);
		base.waitForElement(page.getBasicInfoSave());
		page.getBasicInfoSave().waitAndClick(5);
		base.VerifySuccessMessageB("Information Saved Successfully");
		base.waitForElement(page.getBasicInfoMarkComplete());
		page.getBasicInfoMarkComplete().waitAndClick(4);
		base.VerifySuccessMessageB("Mark Complete successful");
		
		base.passedStep("Verified -ProjectAdmin will be able to enter basic information on the self production wizard");
		loginPage.logout();
	}
	
    /**
     * @author sowndarya.velraj
     * @Description: Verify that new production should be generated with additional placeholder 
     *               sections in addition to the default enabled native placeholdering 
     *               under TIFF/PDF section [RPMXCON-63076]
     **/
    @Test(description = "RPMXCON-63076", enabled = true, groups = { "regression" })
   	public void verifyNewProdWithAdditionPH() throws Exception {
     
   		base.stepInfo("RPMXCON-63076");
   		productionname = "p" + Utility.dynamicNameAppender();
   		int doc = 1;
   		String prefixID = "A_" + Utility.dynamicNameAppender();
   		String suffixID = "_P" + Utility.dynamicNameAppender();
   		String tagName = "Tag" + Utility.dynamicNameAppender();
   		base.stepInfo(
   				"Verify that new production should be generated with additional placeholder sections "
   				+ "in addition to the default enabled native placeholdering under TIFF/PDF section");
    
   		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
   		tagsAndFolderPage.createNewTagwithClassification(tagName,  Input.tagNamePrev);
   		sessionSearch.navigateToSessionSearchPageURL();
   		sessionSearch.metaDataSearchInBasicSearch("DocFileType", "Spreadsheet");
   		sessionSearch.ViewInDocList();		
   		DocListPage doclist = new DocListPage(driver);
   		doclist.documentSelection(doc);
   		doclist.bulkTagExistingFromDoclist(tagName);
   		
   		sessionSearch.navigateToSessionSearchPageURL();
   		sessionSearch.addNewSearch();
   		sessionSearch.removeAllAddedTiles();
   		sessionSearch.newMetaDataSearchInBasicSearch("DocFileType", "Image");
   		sessionSearch.ViewInDocList();
   		DocListPage doclist1 = new DocListPage(driver);
   		doclist1.documentSelection(doc);	
   		doclist1.bulkTagExistingFromDoclist(tagName);
   		
   		String beginningBates = page.getRandomNumber(2);
   		int FirstFile = Integer.valueOf(beginningBates);
   		base = new BaseClass(driver);
   		productionname = "p" + Utility.dynamicNameAppender();	
   		page.navigateToProductionPage();
   		page.selectingDefaultSecurityGroup();
   		page.addANewProduction(productionname);
   		page.fillingDATSection();
   		page.fillingTiffSectionDisablePrivilegedDocs();
   		page.addAdditionalNativPlaceHolder("Images", tagName);
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
   		int Lastile = FirstFile + doc;
   		File TiffFile1 = new File(home + "/Downloads/" + "VOL0001/Images/0001/" + prefixID + FirstFile + suffixID + ".tiff");
   		File TiffFile2 = new File(home + "/Downloads/" + "VOL0001/Images/0001/" + prefixID + Lastile + suffixID + ".tiff");
   		String tiff1 = page.extractTextFromTiff(TiffFile1);
   		String tiff2 = page.extractTextFromTiff(TiffFile2);
   		softAssertion.assertTrue(tiff1.contains("Document Produced in Native Format")  || tiff1.contains(tagName));
   		softAssertion.assertTrue(tiff2.contains("Document Produced in Native Format")  || tiff2.contains(tagName));
   		
   		page.navigateToProductionPage();
   		productionname = "p" + Utility.dynamicNameAppender();	
   		page.selectingDefaultSecurityGroup();
   		page.addANewProduction(productionname);
   		page.fillingDATSection();
   		page.fillingPDFSectionDisablePrivilegedDocs();
   		page.addAdditionalNativPlaceHolder("Images", tagName);
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
   		page.deleteFiles();
   		page.extractFile();
   		driver.waitForPageToBeReady();		
   		File pdfFile1 = new File(home + "/Downloads/" + "VOL0001/PDF/0001/" + prefixID + FirstFile + suffixID + ".pdf");
   		File pdfFile2 = new File(home + "/Downloads/" + "VOL0001/PDF/0001/" + prefixID + Lastile + suffixID + ".pdf");
   		String pdf1 = page.extractTextFromPdf(pdfFile1);
   		String pdf2 = page.extractTextFromPdf(pdfFile2);
   		softAssertion.assertTrue(pdf1.contains("Document Produced in Native Format")  || pdf1.contains(tagName));
   		softAssertion.assertTrue(pdf2.contains("Document Produced in Native Format")  || pdf2.contains(tagName));
   		softAssertion.assertAll();
   		base.passedStep("Verified  - that new production should be generated with additional placeholder sections "
   				+ "in addition to the default enabled native placeholdering under TIFF/PDF section");
   		loginPage.logout();
       }
    
    /**
     * @author NA
     * @Description: To Verify In production, the file-based placeholdering for certain file types 
     *               (Multimedia) [RPMXCON-48177]
     **/
    @Test(description = "RPMXCON-48177", enabled = true, groups = { "regression" })
	public void verifyProdFileBasedPhForMM() throws Exception {
  
		base.stepInfo("RPMXCON-48177");
		productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String tagName = "Tag" + Utility.dynamicNameAppender();
		int doc = 1;
		base.stepInfo(
				"To Verify In production, the file-based placeholdering for certain file types");
 
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		tagsAndFolderPage.createNewTagwithClassification(tagName,  Input.tagNamePrev);
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch("SourceDocID",  "STC4_00001009");
		sessionSearch.ViewInDocList();		
		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(doc);
		doclist.bulkTagExistingFromDoclist(tagName);
		
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.addNewSearch();
		sessionSearch.removeAllAddedTiles();
		sessionSearch.newMetaDataSearchInBasicSearch("DocFileType", "MP3");
		sessionSearch.ViewInDocList();
		DocListPage doclist1 = new DocListPage(driver);
		doclist1.documentSelection(doc);	
		doclist1.bulkTagExistingFromDoclist(tagName);
		
		String beginningBates = page.getRandomNumber(2);
		int FirstFile = Integer.valueOf(beginningBates);
		base = new BaseClass(driver);
		productionname = "p" + Utility.dynamicNameAppender();	
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTiffSectionDisablePrivilegedDocs();
		page.addAdditionalNativPlaceHolder("Multimedia", tagName);
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
		int Lastile = FirstFile + doc;
		File TiffFile1 = new File(home + "/Downloads/" + "VOL0001/Images/0001/" + prefixID + FirstFile + suffixID + ".tiff");
		File TiffFile2 = new File(home + "/Downloads/" + "VOL0001/Images/0001/" + prefixID + Lastile + suffixID + ".tiff");
		page.OCR_Verification_In_Generated_Tiff_tess4j(TiffFile1, "Document Produced in Native Format");
		page.OCR_Verification_In_Generated_Tiff_tess4j(TiffFile2, tagName);
		base.passedStep("Verified In production, the file-based placeholdering for certain file types");
		loginPage.logout();
    }
    
	
	/**
     * @author sowndarya.velraj Testcase No:RPMXCON-63086
     * @Description: Verify that if spreadsheet is priv and Priv, Native placeholder is default enabled from PDF section then production should be generated for Priv placeholder
     **/
	@Test(description = "RPMXCON-63086", enabled = true, groups = { "regression" })
	public void verifyPrivPHinProduction() throws Exception {
		UtilityLog.info(Input.prodPath);
		tagname = "Tag" + Utility.dynamicNameAppender();
		String tagNamePrev = Input.tagNamePrev;
		productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
			
		base.stepInfo("Test Cases Id : RPMXCON-63086");
		base.stepInfo("Verify that if spreadsheet is priv and Priv, Native placeholder is default enabled "
				+ "from PDF section then production should be generated for Priv placeholder");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		// create tag 
		base = new BaseClass(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		// search for tag
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch("DocFileType", "spreadsheet");
		sessionSearch.ViewInDocList();
		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(1);
		doclist.bulkTagExisting(tagname);
		
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.SelectPDFGenerateRadioButton();
		page.fillingPrivledgedDocForPDFSection(tagname, tagNamePrev);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		base.waitUntilFileDownload();	
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		driver.waitForPageToBeReady();
		page.deleteFiles();
		page.extractFile();
		File pdfFile = new File(
				home + "/Downloads/VOL0001/PDF/0001/" +  prefixID + beginningBates + suffixID + ".pdf");
		String text = page.verifyTextinPDF(pdfFile, Input.tagNameTechnical);
		base.stepInfo(text);
	    base.passedStep("Verified - that if spreadsheet is priv and Priv, Native placeholder is "
	    		+ "default enabled from PDF section then production should be generated for Priv placeholder");
		loginPage.logout();
	}
	
	
	/**
     * @author sowndarya.velraj Testcase No:RPMXCON-63087
     * @Description: Verify that if spreadsheet is tech issue and Tech issue, Native placeholder is default enabled from TIFF/PDF 
     *               section then production should be generated for Tech placeholder
     **/
	@Test(description = "RPMXCON-63087", enabled = true, groups = { "regression" })
	public void verifyTechIssuePHinProd() throws Exception {
		UtilityLog.info(Input.prodPath);
		tagname = "Tag" + Utility.dynamicNameAppender();
		productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = "A_" + Utility.dynamicNameAppender();
		String suffixID = "_P" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		base.stepInfo("Test Cases Id : RPMXCON-63087");
		base.stepInfo("Verify that if spreadsheet is tech issue and Tech issue, Native placeholder is default "
				+ "enabled from TIFF/PDF section then production should be generated for Tech placeholder");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		// create tag 
		base = new BaseClass(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.technicalIssue);

		// search for tag
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.metaDataSearchInBasicSearch("DocFileType", "spreadsheet");
		sessionSearch.ViewInDocList();
		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(1);
		doclist.bulkTagExisting(tagname);
		
		page.navigateToProductionPage();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.selectGenerateOption(true);
		page.selectTechIssueDoc(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		base.waitUntilFileDownload();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		driver.waitForPageToBeReady();
		page.deleteFiles();
		page.extractFile();
		File pdfFile = new File(
				home + "/Downloads/VOL0001/PDF/0001/" + prefixID + beginningBates + suffixID +".pdf");
		String text = page.verifyTextinPDF(pdfFile, Input.tagNameTechnical);
		base.stepInfo(text);
	    
		page.navigateToProductionPage();
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.selectGenerateOption(false);
		page.selectTechIssueDoc(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		base.waitUntilFileDownload();		
		driver.waitForPageToBeReady();
		driver.waitForPageToBeReady();
		page.deleteFiles();
		page.extractFile();	
		File tiffFile = new File(
				home + "/Downloads/VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tiff");
		page.OCR_Verification_In_Generated_Tiff_tess4j(tiffFile, Input.tagNameTechnical);
		base.passedStep("Verify that if spreadsheet is tech issue and Tech issue, Native placeholder is default enabled "
				+ "from TIFF/PDF section then production should be generated for Tech placeholder ");
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
