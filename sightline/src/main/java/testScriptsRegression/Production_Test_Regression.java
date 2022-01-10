package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.ProjectFieldsPage;
import pageFactory.RedactionPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Production_Test_Regression {

	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	ProductionPage productionPage;
	TagsAndFoldersPage tagsAndFolderPage;
	SessionSearch sessionSearch;
	DocListPage docPage;
	DocViewPage docViewPage;
	ProjectFieldsPage projectField;
	SoftAssert softAssertion;
	String prefixID = "A_" + Utility.dynamicNameAppender();
	String suffixID = "_P" + Utility.dynamicNameAppender();
	String foldername;
	String tagname;
	String productionname;
	String TempName;

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();
		driver = new Driver();
		base = new BaseClass(driver);
		loginPage = new LoginPage(driver);

		// Login as a PA
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
	}
	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56052
	 * @Description: To Verify that after Archiving is completed it should displays 'Creating Archive Complete' status on Production Grid View
	 */
	//@Test(groups = { "regression" }, priority = 1)
	public void archivingStatusVerifyOnGridView() throws Exception {
	UtilityLog.info(Input.prodPath);
	base.stepInfo("RPMXCON-56052 -Production Sprint 06");
	
	String testData1 = Input.testData1;
	foldername = "FolderProd" + Utility.dynamicNameAppender();
	tagname = "Tag" + Utility.dynamicNameAppender();

	// Pre-requisites
	// create tag and folder
	TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
	tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

	// search for folder
	SessionSearch sessionSearch = new SessionSearch(driver);
	sessionSearch = new SessionSearch(driver);
	sessionSearch.basicContentSearch(testData1);
	sessionSearch.bulkFolderExisting(foldername);

	//Verify archive status on Grid view
	ProductionPage page = new ProductionPage(driver);
	String beginningBates = page.getRandomNumber(2);
	productionname = "p" + Utility.dynamicNameAppender();
	page.selectingDefaultSecurityGroup();
	page.addANewProduction(productionname);
	page.fillingDATSection();
	page.navigateToNextSection();
	page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
	page.navigateToNextSection();
	page.fillingDocumentSelectionPage(foldername);
	page.navigateToNextSection();
	page.fillingPrivGuardPage();
	page.fillingProductionLocationPage(productionname);
	page.navigateToNextSection();
	page.fillingSummaryAndPreview();

	// Wait until Generate button enables
	page.fillingGeneratePageWithContinueGenerationPopupWithoutWait();
	
	// Go To Production Home Page
	this.driver.getWebDriver().get(Input.url + "Production/Home");
	driver.Navigate().refresh();
	driver.waitForPageToBeReady();
	page.getGridView().waitAndClick(10);
	//Thread.sleep(5000);
	driver.waitForPageToBeReady();
	page.verifyProductionStatusInHomePageGridView("Creating Archive", productionname);
	base.passedStep("Archiving is completed it should displays 'Creating Archive Complete' status on Production Grid View");
	
	//delete tags and folders
	tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
	tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
	
	}
	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-55975
	 * @Description: To Verify that after Pregen check completed it should displays 'Reserving Bates Range' status on Progress bar in Tile View on Production Home page
	 */
	//@Test(groups = { "regression" }, priority = 2)
	public void reservingBateRangeStatusVerifyOnTileView() throws Exception {
			UtilityLog.info(Input.prodPath);
			base.stepInfo("RPMXCON-55975 -Production Sprint 06");
			
			String testData1 = Input.testData1;
			foldername = "FolderProd" + Utility.dynamicNameAppender();
			tagname = "Tag" + Utility.dynamicNameAppender();
			

			// Pre-requisites
			// create tag and folder
			TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
			tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
			tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

			// search for folder
			SessionSearch sessionSearch = new SessionSearch(driver);
			sessionSearch = new SessionSearch(driver);
			sessionSearch.basicContentSearch(testData1);
			sessionSearch.bulkFolderExisting(foldername);

			//Verify Reserving Bates Range status on Tile view
			ProductionPage page = new ProductionPage(driver);
			String beginningBates = page.getRandomNumber(2);
			productionname = "p" + Utility.dynamicNameAppender();
			page.selectingDefaultSecurityGroup();
			page.addANewProduction(productionname);
			page.fillingDATSection();
			page.navigateToNextSection();
			page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
			page.navigateToNextSection();
			page.fillingDocumentSelectionPage(foldername);
			page.navigateToNextSection();
			page.fillingPrivGuardPage();
			page.fillingProductionLocationPage(productionname);
			page.navigateToNextSection();
			page.fillingSummaryAndPreview();
			//click genarate without wait
			page.clickGenarateWithoutWait();
			
			// Go To Production Home Page
			this.driver.getWebDriver().get(Input.url + "Production/Home");
			driver.Navigate().refresh();
			//verification
			page.verifyProductionStatusInHomePage("Reserving Bates Range", productionname);
			base.passedStep("Verified that after Pregen check completed it should displays 'Reserving Bates Range' status on Progress bar in Tile View on Production Home page");
			
			//delete tags and folders
			tagsAndFolderPage = new TagsAndFoldersPage(driver);
			tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
			tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
	 }
	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-55973
	 * @Description: To Verify that after Pre-gen checks is in progress, it will displays status on Production Progress bar Tile View
	 */
	//@Test(groups = { "regression" }, priority = 3)
	public void preGenChecksStatusVerifyOnTileView() throws Exception {
			UtilityLog.info(Input.prodPath);
			base.stepInfo("RPMXCON-55973 -Production Sprint 06");
			
			String testData1 = Input.testData1;
			foldername = "FolderProd" + Utility.dynamicNameAppender();
			tagname = "Tag" + Utility.dynamicNameAppender();
			

			// Pre-requisites
			// create tag and folder
			TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
			tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
			tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

			// search for folder
			SessionSearch sessionSearch = new SessionSearch(driver);
			sessionSearch = new SessionSearch(driver);
			sessionSearch.basicContentSearch(testData1);
			sessionSearch.bulkFolderExisting(foldername);

			//Verify Pre-gen checks is in progress status on Tile view
			ProductionPage page = new ProductionPage(driver);
			String beginningBates = page.getRandomNumber(2);
			productionname = "p" + Utility.dynamicNameAppender();
			page.selectingDefaultSecurityGroup();
			page.addANewProduction(productionname);
			page.fillingDATSection();
			page.navigateToNextSection();
			page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
			page.navigateToNextSection();
			page.fillingDocumentSelectionPage(foldername);
			page.navigateToNextSection();
			page.fillingPrivGuardPage();
			page.fillingProductionLocationPage(productionname);
			page.navigateToNextSection();
			page.fillingSummaryAndPreview();
			//click genarate without wait
			page.clickGenarateWithoutWait();
			
			// Go To Production Home Page
			this.driver.getWebDriver().get(Input.url + "Production/Home");
			driver.Navigate().refresh();
			//verification
			page.verifyProductionStatusInHomePage("Pre-Gen Checks - 6/6 Docs", productionname);
			base.passedStep("Verified that after Pre-gen checks is in progress as a PA, it will displays status on Production Progress bar Tile View");
			
			loginPage.logout();
			
			//login as a RMU
			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			UtilityLog.info("Logged in as User: " + Input.rmu1userName);
			

			//Verify Pre-gen checks is in progress status on Tile view
			page = new ProductionPage(driver);
			productionname = "p" + Utility.dynamicNameAppender();
			page.selectingDefaultSecurityGroup();
			page.addANewProduction(productionname);
			page.fillingDATSection();
			page.navigateToNextSection();
			page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
			page.navigateToNextSection();
			page.fillingDocumentSelectionPage(foldername);
			page.navigateToNextSection();
			page.fillingPrivGuardPage();
			page.fillingProductionLocationPage(productionname);
			page.navigateToNextSection();
			page.fillingSummaryAndPreview();
			//click genarate without wait
			page.clickGenarateWithoutWait();
			
			// Go To Production Home Page
			this.driver.getWebDriver().get(Input.url + "Production/Home");
			driver.Navigate().refresh();
			//verification
			page.verifyProductionStatusInHomePage("Pre-Gen Checks - 6/6 Docs", productionname);
			base.passedStep("Verified that after Pre-gen checks is in progress as a RMU, it will displays status on Production Progress bar Tile View");
			
			//delete tags and folders
			tagsAndFolderPage = new TagsAndFoldersPage(driver);
			tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
			tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
			
	 }
	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56136
	 * @Description: To Verify that for the saved template under Translations component- File Type Options should be disabled.
	 */
	//@Test(groups = { "regression" }, priority =4)
	public void verifyTranslationComponentDisableAtMangeTemp() throws Exception{
	UtilityLog.info(Input.prodPath);
	base.stepInfo("RPMXCON_56136 Production- Sprint 06");
	foldername = "FolderProd" + Utility.dynamicNameAppender();
	tagname = "Tag" + Utility.dynamicNameAppender();
	//Pre-requisites


	ProductionPage page = new ProductionPage(driver);
	productionname = "p" + Utility.dynamicNameAppender();
	TempName = "T" + Utility.dynamicNameAppender();
	page.selectingDefaultSecurityGroup();
	page.addANewProduction(productionname);
	page.fillingDATSection();
	driver.waitForPageToBeReady();
	page.advancedProductionComponentsTranslations();
	page.getMarkCompleteLink().waitAndClick(10);
	page.getProductionHomePage().waitAndClick(10);
	page.getprod_ActionButton_Reusable(productionname).Click();
	driver.waitForPageToBeReady();
	page.getprod_Action_SaveTemplate_Reusable(productionname).Click();

	page.saveTemple(TempName);
	page.getManageTemplates().waitAndClick(10);
	driver.scrollingToBottomofAPage();
	page.getNextBtn().waitAndClick(10);
	driver.scrollingToBottomofAPage();
	page.getViewTemplate(TempName).waitAndClick(10);

	page.viewTempProductionNextAdvTranslation();
	driver.waitForPageToBeReady();
	driver.scrollingToBottomofAPage();
	page.translationDisableCheck();
	base.passedStep("Verified that for the saved template under Translations component- File Type Options should be disabled");
	}
	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56091
	 * @Description: To Verify that PDF should generate with Burned Redaction if Only Burn Redaction is enabled.
	 */
	//@Test(groups = { "regression" }, priority = 5)
	public void getPdfWithBurnRedactionTag() throws Exception {
	UtilityLog.info(Input.prodPath);
	base.stepInfo("RPMXCON-56091 -Production Sprint 06");
	
	String testData1 = Input.testData1;
	foldername = "FolderProd" + Utility.dynamicNameAppender();
	tagname = "Tag" + Utility.dynamicNameAppender();
	

	// Pre-requisites
	// create tag and folder
	TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
	tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

	// search for folder
	SessionSearch sessionSearch = new SessionSearch(driver);
	sessionSearch = new SessionSearch(driver);
	sessionSearch.basicContentSearch(testData1);
	sessionSearch.bulkFolderExisting(foldername);

	//Verify pdf with burn redaction
	ProductionPage page = new ProductionPage(driver);
	String beginningBates = page.getRandomNumber(2);
	productionname = "p" + Utility.dynamicNameAppender();
	page.selectingDefaultSecurityGroup();
	page.addANewProduction(productionname);
	page.fillingDATSection();
	page.fillingPDFForRedaction1();
	page.navigateToNextSection();
	page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
	page.navigateToNextSection();
	page.fillingDocumentSelectionPage(foldername);
	page.navigateToNextSection();
	page.fillingPrivGuardPage();
	page.fillingProductionLocationPage(productionname);
	page.navigateToNextSection();
	page.fillingSummaryAndPreview();
	page.fillingGeneratePageWithContinueGenerationPopup();
	base.passedStep("Verified that PDF should generate with Burned Redaction if Only Burn Redaction is enabled");
	
	//delete tag and folder
	tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
	tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
	
	}
	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56073
	 * @Description: To Verify that Production should export Text files for Document level successfully.
	 */
	//@Test(groups = { "regression" }, priority = 6)
	public void getExortTextforDocument() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56073 -Production Sprint 06");
		
		String testData1 = Input.testData1;
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);

		//Verify pdf with burn redaction
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.fillingTIFFWithMultiPage(tagname);
		page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.passedStep("Verified that Production should export Text files for Document level successfully");
		
		//delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
	}
	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56083
	 * @Description: To Verify that Production should generate successfully by selecting only DAT and 'Generate TIFF' option.
	 */
	//@Test(groups = { "regression" }, priority = 7)
	public void getProductionPageWithDatTiff() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56083 -Production Sprint 06");
		
		String testData1 = Input.testData1;
		String tagNameTechnical = Input.tagNameTechnical;
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);

		//Verify
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname,tagNameTechnical);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.passedStep("Verified that Production should generate successfully by selecting only DAT and 'Generate TIFF' option");
		
		//delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
	}
	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56086
	 * @Description: To Verify that Production should generate successfully by selecting only DAT and 'Generate PDF' option with Priv Placholder.
	 */
	//@Test(groups = { "regression" }, priority = 8)
	public void getProductionPageWithDatPdf() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56086 -Production Sprint 06");
		
		String testData1 = Input.testData1;
		String tagNameTechnical = Input.tagNameTechnical;
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);

		//Verify
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingPDFSection(tagname,tagNameTechnical);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		base.passedStep("Verified that Production should generate successfully by selecting only DAT and 'Generate PDF' option with Priv Placholder");
		
		//delete tag and folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-56151
	 * @Description:To Verify that Regenerate button Popup should close on clicking on Cancel button.
	 */
	//@Test(groups = { "regression" }, priority = 9)
	public void regeneratePopUpClickCancel() throws InterruptedException { 
		UtilityLog.info(Input.prodPath); base.stepInfo("RPMXCON-56151 -Production Sprint 07"); 
		String testData1 = Input.testData1; 
		String tagNameTechnical = Input.tagNameTechnical; 
		foldername = "FolderProd" + Utility.dynamicNameAppender(); 
		tagname = "Tag" + Utility.dynamicNameAppender(); 
		// Pre-requisites 
		// create tag and folder 
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver); 
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group"); 
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged"); 
		
		// search for folder 
		SessionSearch sessionSearch = new SessionSearch(driver); 
		sessionSearch = new SessionSearch(driver); sessionSearch.basicContentSearch(testData1); 
		sessionSearch.bulkFolderExisting(foldername);
		
		//Verify 
		ProductionPage page = new ProductionPage(driver); 
		productionname = "p" + Utility.dynamicNameAppender(); 
		page.selectingDefaultSecurityGroup(); 
		page.addANewProduction(productionname); 
		page.fillingDATSection(); 
		page.navigateToNextSection();
		// page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates); 
		page.navigateToNextSection(); 
		page.fillingDocumentSelectionPage(foldername); 
		page.navigateToNextSection(); 
		page.fillingPrivGuardPage(); 
		page.fillingProductionLocationPage(productionname); 
		page.navigateToNextSection(); 
		page.fillingSummaryAndPreview(); 
		page.clickGenarateWaitForRegenarate(); 
		page = new ProductionPage(driver); 
		page.selectingDefaultSecurityGroup(); 
		page.prodReservingBatesRangeFailedProduction1(); 
		base.passedStep("Verified that  Regenerate button Popup should close on clicking on Cancel button"); 
		
		//delete tag and folder 
		tagsAndFolderPage = new TagsAndFoldersPage(driver); 
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group"); 
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group"); 
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-56109
	 * @Description:To Verify the error message for MP3 component when 'Disable generate load File. 
	 */
	//@Test(groups = { "regression" }, priority = 10)
	public void getMp3DisableGenarateLoadFile() throws InterruptedException { 
		UtilityLog.info(Input.prodPath); base.stepInfo("RPMXCON-56109 -Production Sprint 07"); 

		foldername = "FolderProd" + Utility.dynamicNameAppender(); 

		
		//Verify 
		ProductionPage page = new ProductionPage(driver); 
		productionname = "p" + Utility.dynamicNameAppender(); 
		page.selectingDefaultSecurityGroup(); 
		page.addANewProduction(productionname); 
		page.fillingDATSection();
		driver.scrollingToBottomofAPage();
		page.advancedProductionComponentsMP3DisableGenarateFileLoad();
		driver.scrollPageToTop();
		base.waitForElement(page.getMarkCompleteLink());
		page.getMarkCompleteLink().waitAndClick(10);;
		base.VerifyWarningMessage("For the MP3 Files component, you must either enable LST load file option or specify the MP3 file path in the DAT, in order to generate a load file for the generated MP3s files.");
		
		base.passedStep("Verified the error message for MP3 component when 'Disable generate load File ");
		
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCON-56101
	 * @Description:To Verify the error message for TIFF/PDF component when 'Enabled privileg doc without tag or text'. 
	 */
	//@Test(groups = { "regression" }, priority = 11)
	public void verifyErrormsgTiffPdf() throws InterruptedException { 
		UtilityLog.info(Input.prodPath); base.stepInfo("RPMXCON-56101 -Production Sprint 07"); 

		foldername = "FolderProd" + Utility.dynamicNameAppender(); 

		
		//Verify 
		ProductionPage page = new ProductionPage(driver); 
		productionname = "p" + Utility.dynamicNameAppender(); 
		page.selectingDefaultSecurityGroup(); 
		page.addANewProduction(productionname); 
		page.fillingDATSection();
		page.fillingTIFFSectionWithError();
		
		driver.scrollPageToTop();
		base.waitForElement(page.getMarkCompleteLink());
		page.getMarkCompleteLink().waitAndClick(10);;
		base.VerifyErrorMessage("Privileged tags or corresponding placeholder text is missing in the Privileged Placeholdering of the TIFF/PDF section.");
		
		foldername = "FolderProd" + Utility.dynamicNameAppender(); 

		
		//Verify 
		page = new ProductionPage(driver); 
		productionname = "p" + Utility.dynamicNameAppender(); 
		page.selectingDefaultSecurityGroup(); 
		page.addANewProduction(productionname); 
		page.fillingDATSection();
		page.fillingPDFSectionWithError();
		driver.scrollPageToTop();
		base.waitForElement(page.getMarkCompleteLink());
		page.getMarkCompleteLink().waitAndClick(10);;
		base.VerifyErrorMessage("Privileged tags or corresponding placeholder text is missing in the Privileged Placeholdering of the TIFF/PDF section.");
		
		base.passedStep("Verified the error message for TIFF/PDF component when 'Enabled privileg doc without tag or text'");
		
	}
	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56041
	 * @Description: To Verify that after Reserving Bates Range completed it should displays 'Reserving Bates Range Complete' status on Grid View on Production Home page.
	 */
	//@Test(groups = { "regression" }, priority = 12)
	public void verifyBatesRangecompletedOnGridView() throws Exception {
		loginPage.logout();
		
		// Login as a RMU
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-56041 -Production Sprint 07");
	
		String testData1 = Input.testData1;
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
	

	// 	Pre-requisites
	// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolderInRMU(foldername);
		tagsAndFolderPage.createNewTagwithClassificationInRMU(tagname, "Privileged");

	// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);

	//Verify archive status on Grid view
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();

	// Wait until Generate button enables
		page.fillingGeneratePageWaitForContinueGeneration();
	
	// Go To Production Home Page
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		page.getGridView().waitAndClick(10);
	//Thread.sleep(5000);
		driver.waitForPageToBeReady();
		page.verifyProductionStatusInHomePageGridView("Reserving Bates Range Complete", productionname);
		base.passedStep("Verified that after Reserving Bates Range completed it should displays 'Reserving Bates Range Complete' status on Grid View on Production Home page");
	
	//delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
	
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			RPMXCONO-47175
	 * @Description To Verify that produced PDF/TIFF files should not be split when 'Split Sub Folders' is ON with split count as 1000 and selected documents <= 1000. 
	 */
	////@Test(groups = { "regression" }, priority = 14)
	public void genaratetDocumentswithMultipleBrandingTags() throws Exception {
	UtilityLog.info(Input.prodPath);
	base.stepInfo("RPMXCON-47175 -Production Sprint 7");
	foldername = "FolderProd" + Utility.dynamicNameAppender();
	String tagname1 = "Tag" + Utility.dynamicNameAppender();
	String tagname2 = "Tag1" + Utility.dynamicNameAppender();
	String testData1 = Input.testData1;
	//Pre-requisites
	//create tag and folder
	TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
	this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
	tagsAndFolderPage.CreateTagwithClassification(tagname1, "Privileged");
	RedactionPage reductionPage = new RedactionPage(driver);
//	reductionPage.selectDefaultSecurityGroup();
//	reductionPage.manageRedactionTagsPage(tagname2);
	
	//search for redacted documents and adding to bulk folder
	SessionSearch sessionSearch = new SessionSearch(driver);
	sessionSearch = new SessionSearch(driver);
	sessionSearch.basicContentSearch(testData1);
	sessionSearch.bulkTagExisting(tagname1);
	//sessionSearch.bulkTagExisting(tagname2);
	
	
	//document 
	ProductionPage page = new ProductionPage(driver);
	productionname = "p" + Utility.dynamicNameAppender();
	String beginningBates = page.getRandomNumber(2);
	page.addANewProduction(productionname);
	page.fillingDATSection();
	page.fillingNativeSection();
	page.fillingTIFFSection(tagname1,tagname2);
	page.fillingTextSection();
	page.navigateToNextSection();
	page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
	page.navigateToNextSection();
	page.fillingDocumentSelectionPageWithTag(tagname1,tagname2);
	page.navigateToNextSection();
	page.fillingPrivGuardPage();
	page.splictCountCheck();
	page.fillingProductionLocationPageAndPassingText(productionname);
	page.navigateToNextSection();
	page.fillingSummaryAndPreview();
	page.fillingGeneratePageWithContinueGenerationPopup();
	//page.InsertingDataFromNumberingToGenerate(prefixID, suffixID, foldername, productionname);
	base.passedStep("Verify that produced PDF/TIFF files should not be split when 'Split Sub Folders' is ON with split count as 1000 and selected documents <= 1000");
	
	//delete tags and folders
	tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.DeleteTagWithClassification(tagname1, "Default Security Group");
	driver.waitForPageToBeReady();
	reductionPage.DeleteRedaction(tagname2);
	}
	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56034
	 * @Description: To Verify that after Archiving is completed it should displays 'Creating Archive Complete' status on Production Generation page.
	 */
	//@Test(groups = { "regression" }, priority = 13)
	public void createArchivingStatusVerifyOnGenPage() throws Exception {
	UtilityLog.info(Input.prodPath);
	base.stepInfo("RPMXCON-56034 -Production Sprint 07");
	
	String testData1 = Input.testData1;
	foldername = "FolderProd" + Utility.dynamicNameAppender();
	tagname = "Tag" + Utility.dynamicNameAppender();
	

	// Pre-requisites
	// create tag and folder
	TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
	tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

	// search for folder
	SessionSearch sessionSearch = new SessionSearch(driver);
	sessionSearch = new SessionSearch(driver);
	sessionSearch.basicContentSearch(testData1);
	sessionSearch.bulkFolderExisting(foldername);

	//Verify archive status on Gen page
	ProductionPage page = new ProductionPage(driver);
	productionname = "p" + Utility.dynamicNameAppender();
	String beginningBates = page.getRandomNumber(2);
	page.selectingDefaultSecurityGroup();
	page.addANewProduction(productionname);
	page.fillingDATSection();
	page.navigateToNextSection();
	page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
	page.navigateToNextSection();
	page.fillingDocumentSelectionPage(foldername);
	page.navigateToNextSection();
	page.fillingPrivGuardPage();
	page.fillingProductionLocationPage(productionname);
	page.navigateToNextSection();
	page.fillingSummaryAndPreview();

	// Wait until Generate button enables
	page.fillingGeneratePageWithContinueGenerationPopupWithoutWait();
	
	driver.waitForPageToBeReady();
	page.verifyProductionStatusInGenPage("Creating Archive Complete");
	base.passedStep("Verified that after Archiving is completed it should displays 'Creating Archive Complete' status on Production Generation page");
	
	//delete tags and folders
	tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
	tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
	
	}
	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56035
	 * @Description: To Verify that Production status displays as Draft on Production Grid View.
	 */
	//@Test(groups = { "regression" }, priority = 14)
	public void verifyDraftStatusOnGridView() throws Exception {
	UtilityLog.info(Input.prodPath);
	base.stepInfo("RPMXCON-56035 -Production Sprint 07");
	
	String testData1 = Input.testData1;
	foldername = "FolderProd" + Utility.dynamicNameAppender();
	tagname = "Tag" + Utility.dynamicNameAppender();
	

	// Pre-requisites
	// create tag and folder
	TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
	tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

	// search for folder
	SessionSearch sessionSearch = new SessionSearch(driver);
	sessionSearch = new SessionSearch(driver);
	sessionSearch.basicContentSearch(testData1);
	sessionSearch.bulkFolderExisting(foldername);

	//Verify archive status on Gen page
	ProductionPage page = new ProductionPage(driver);
	productionname = "p" + Utility.dynamicNameAppender();
	String beginningBates = page.getRandomNumber(2);
	page.selectingDefaultSecurityGroup();
	page.addANewProduction(productionname);
	page.fillingDATSection();
	page.navigateToNextSection();
	page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
	page.navigateToNextSection();
	page.fillingDocumentSelectionPage(foldername);
	page.navigateToNextSection();
	page.fillingPrivGuardPage();
	page.fillingProductionLocationPage(productionname);
	page.navigateToNextSection();
	page.fillingSummaryAndPreview();
	
	// Go To Production Home Page
	this.driver.getWebDriver().get(Input.url + "Production/Home");
	driver.Navigate().refresh();
	driver.waitForPageToBeReady();
	page.getGridView().waitAndClick(10);
	driver.waitForPageToBeReady();
	page.verifyProductionStatusInHomePageGridView("DRAFT", productionname);
	loginPage.logout();
	
	// Login as a RMU
	loginPage = new LoginPage(driver);
	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	UtilityLog.info("Logged in as User: " + Input.rmu1userName);
	
	page = new ProductionPage(driver);
	productionname = "p" + Utility.dynamicNameAppender();
	page.selectingDefaultSecurityGroup();
	page.addANewProduction(productionname);
	page.fillingDATSection();
	page.navigateToNextSection();
	page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
	page.navigateToNextSection();
	page.fillingDocumentSelectionPage(foldername);
	page.navigateToNextSection();
	page.fillingPrivGuardPage();
	page.fillingProductionLocationPage(productionname);
	page.navigateToNextSection();
	page.fillingSummaryAndPreview();
	
	// Go To Production Home Page
	this.driver.getWebDriver().get(Input.url + "Production/Home");
	driver.Navigate().refresh();
	driver.waitForPageToBeReady();
	page.getGridView().waitAndClick(10);
	driver.waitForPageToBeReady();
	page.verifyProductionStatusInHomePageGridView("DRAFT", productionname);
	
	
	base.passedStep("Verify that Production status displays as Draft on Production Grid View");
	
	//delete tag ang folder
	tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
	tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
	
	}
	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56036
	 * @Description: To Verify that after Pre-gen checks is in progress, it will displays status on Production Grid view.
	 */
	//@Test(groups = { "regression" }, priority = 15)
	public void verifyPreGenStatusOnGridView() throws Exception {
	UtilityLog.info(Input.prodPath);
	base.stepInfo("RPMXCON-56036 -Production Sprint 07");
	
	String testData1 = Input.testData1;
	foldername = "FolderProd" + Utility.dynamicNameAppender();
	tagname = "Tag" + Utility.dynamicNameAppender();
	

	// Pre-requisites
	// create tag and folder
	TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
	this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
	tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
	tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

	// search for folder
	SessionSearch sessionSearch = new SessionSearch(driver);
	sessionSearch = new SessionSearch(driver);
	sessionSearch.basicContentSearch(testData1);
	sessionSearch.bulkFolderExisting(foldername);

	//Verify archive status on Gen page
	ProductionPage page = new ProductionPage(driver);
	productionname = "p" + Utility.dynamicNameAppender();
	String beginningBates = page.getRandomNumber(2);
	page.selectingDefaultSecurityGroup();
	page.addANewProduction(productionname);
	page.fillingDATSection();
	page.navigateToNextSection();
	page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
	page.navigateToNextSection();
	page.fillingDocumentSelectionPage(foldername);
	page.navigateToNextSection();
	page.fillingPrivGuardPage();
	page.fillingProductionLocationPage(productionname);
	page.navigateToNextSection();
	page.fillingSummaryAndPreview();
	page.clickGenarateWithoutWait();
	
	// Go To Production Home Page
	this.driver.getWebDriver().get(Input.url + "Production/Home");
	driver.Navigate().refresh();
	driver.waitForPageToBeReady();
	page.getGridView().waitAndClick(10);
	driver.waitForPageToBeReady();
	page.verifyProductionStatusInHomePageGridView("Pre-Gen Checks -", productionname);
	loginPage.logout();
	
	// Login as a RMU
	loginPage = new LoginPage(driver);
	loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	UtilityLog.info("Logged in as User: " + Input.rmu1userName);
	
	page = new ProductionPage(driver);
	productionname = "p" + Utility.dynamicNameAppender();
	page.selectingDefaultSecurityGroup();
	page.addANewProduction(productionname);
	page.fillingDATSection();
	page.navigateToNextSection();
	page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
	page.navigateToNextSection();
	page.fillingDocumentSelectionPage(foldername);
	page.navigateToNextSection();
	page.fillingPrivGuardPage();
	page.fillingProductionLocationPage(productionname);
	page.navigateToNextSection();
	page.fillingSummaryAndPreview();
	page.clickGenarateWithoutWait();
	
	// Go To Production Home Page
	this.driver.getWebDriver().get(Input.url + "Production/Home");
	driver.Navigate().refresh();
	driver.waitForPageToBeReady();
	page.getGridView().waitAndClick(10);
	driver.waitForPageToBeReady();
	page.verifyProductionStatusInHomePageGridView("Pre-Gen Checks -", productionname);
	
	
	base.passedStep("Verified that after Pre-gen checks is in progress, it will displays status on Production Grid view");
	
	//delete tag ang folder
	tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
	tagsAndFolderPage.DeleteTagWithClassificationInRMU(tagname);
	
	}
	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56029
	 * @Description: To Verify that after destination copy is completed it should displays 'Exporting Files' status on Production Progress bar Tile View.
	 */
	//@Test(groups = { "regression" }, priority = 16)
	public void verifyExportinFilesStatusOnTileView() throws Exception {
	UtilityLog.info(Input.prodPath);
	base.stepInfo("RPMXCON-56029 -Production Sprint 07");
	
	String testData1 = Input.testData1;
	foldername = "FolderProd" + Utility.dynamicNameAppender();
	tagname = "Tag" + Utility.dynamicNameAppender();
	

	// Pre-requisites
	// create tag and folder
	TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
	

	// search for folder
	SessionSearch sessionSearch = new SessionSearch(driver);
	sessionSearch = new SessionSearch(driver);
	sessionSearch.basicContentSearch(testData1);
	sessionSearch.bulkFolderExisting(foldername);

	//Verify archive status on Gen page
	ProductionPage page = new ProductionPage(driver);
	productionname = "p" + Utility.dynamicNameAppender();
	String beginningBates = page.getRandomNumber(2);
	page.selectingDefaultSecurityGroup();
	page.addANewProduction(productionname);
	page.fillingDATSection();
	page.navigateToNextSection();
	page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
	page.navigateToNextSection();
	page.fillingDocumentSelectionPage(foldername);
	page.navigateToNextSection();
	page.fillingPrivGuardPage();
	page.fillingProductionLocationPage(productionname);
	page.navigateToNextSection();
	page.fillingSummaryAndPreview();
	page.fillingGeneratePageWithContinueGenerationPopupWithoutWait();
	
	// Go To Production Home Page
	this.driver.getWebDriver().get(Input.url + "Production/Home");
	driver.Navigate().refresh();
	//verification
	page.verifyProductionStatusInHomePage("Exporting Files", productionname);
	base.passedStep("Verified that after destination copy is completed it should displays 'Exporting Files' status on Production Progress bar Tile View");
	
	//delete tags and folders
	tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
	
}
	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56030
	 * @Description: To Verify that after destination copy is completed it should displays 'Exporting Files' status on Production Generate tab.
	 */
	//@Test(groups = { "regression" }, priority = 17)
	public void verifyExportinFilesStatusOnGen() throws Exception {
	UtilityLog.info(Input.prodPath);
	base.stepInfo("RPMXCON-56030 -Production Sprint 07");
	
	String testData1 = Input.testData1;
	foldername = "FolderProd" + Utility.dynamicNameAppender();
	tagname = "Tag" + Utility.dynamicNameAppender();
	

	// Pre-requisites
	// create tag and folder
	TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
	

	// search for folder
	SessionSearch sessionSearch = new SessionSearch(driver);
	sessionSearch = new SessionSearch(driver);
	sessionSearch.basicContentSearch(testData1);
	sessionSearch.bulkFolderExisting(foldername);

	//Verify archive status on Gen page
	ProductionPage page = new ProductionPage(driver);
	productionname = "p" + Utility.dynamicNameAppender();
	String beginningBates = page.getRandomNumber(2);
	page.selectingDefaultSecurityGroup();
	page.addANewProduction(productionname);
	page.fillingDATSection();
	page.navigateToNextSection();
	page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
	page.navigateToNextSection();
	page.fillingDocumentSelectionPage(foldername);
	page.navigateToNextSection();
	page.fillingPrivGuardPage();
	page.fillingProductionLocationPage(productionname);
	page.navigateToNextSection();
	page.fillingSummaryAndPreview();
	page.fillingGeneratePageWithContinueGenerationPopupWithoutWait();
	
	page.verifyProductionStatusInGenPage("Exporting Files");
	base.passedStep("Verified that after destination copy is completed it should displays 'Exporting Files' status on Production Generate tab");
	
	//delete tags and folders
	tagsAndFolderPage = new TagsAndFoldersPage(driver);
	tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
	
}
	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-56048
	 * @Description: Verify that after LST generation, if Destination Copy is in progress, it will displays status as 'Exporting Files' on Production Grid View.
	 */
	//@Test(groups = { "regression" }, priority = 18)
	public void verifyExportinFilesStatusOnGridView() throws Exception {
	UtilityLog.info(Input.prodPath);
	base.stepInfo("RPMXCON-56048 -Production Sprint 08");
	
	String testData1 = Input.testData1;
	foldername = "FolderProd" + Utility.dynamicNameAppender();

	// Pre-requisites
	// create tag and folder
	TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
	this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
	tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
	

	// search for folder
	SessionSearch sessionSearch = new SessionSearch(driver);
	sessionSearch = new SessionSearch(driver);
	sessionSearch.basicContentSearch(testData1);
	sessionSearch.bulkFolderExisting(foldername);

	//Verify archive status on Gen page
	ProductionPage page = new ProductionPage(driver);
	productionname = "p" + Utility.dynamicNameAppender();
	String beginningBates = page.getRandomNumber(2);
	page.selectingDefaultSecurityGroup();
	page.addANewProduction(productionname);
	page.fillingDATSection();
	page.navigateToNextSection();
	page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
	page.navigateToNextSection();
	page.fillingDocumentSelectionPage(foldername);
	page.navigateToNextSection();
	page.fillingPrivGuardPage();
	page.fillingProductionLocationPage(productionname);
	page.navigateToNextSection();
	page.fillingSummaryAndPreview();
	page.fillingGeneratePageWithContinueGenerationPopup();
	
	// Go To Production Home Page
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		page.getGridView().waitAndClick(10);
		driver.waitForPageToBeReady();
		page.verifyProductionStatusInHomePageGridView("Exporting Files ", productionname);
		base.passedStep("Verified that after LST generation, if Destination Copy is in progress, it will displays status as 'Exporting Files' on Production Grid View");
		
		//delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		
	}
	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-50017
	 * @Description: Verify if currently 'AllProductionBatesRanges' is searchable, then we should leave the field to be searchable..
	 */
	//@Test(groups = { "regression" }, priority = 19)
	public void verifyAllProductionBatesRangesSearchable() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-50017 -Production Sprint 09");
		
		ProjectFieldsPage projectField=new ProjectFieldsPage(driver);
		projectField.navigateToProjectFieldsPage();
		projectField.getAllProductionBatesRanges().waitAndClick(5);
		projectField.enableIsSearchableBatesRangeIsSelected();
	
		SessionSearch sessionSearch = new SessionSearch(driver);
		this.driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.waitForPageToBeReady();
		sessionSearch.getBasicSearch_MetadataBtn().waitAndClick(5);
		sessionSearch.getSelectMetaData().waitAndClick(5);
		boolean flag=sessionSearch.getAllProductionBatesRanges().isDisplayed();
		SoftAssert softAssertion= new SoftAssert();
		if(flag) {
			softAssertion.assertTrue(flag);
			base.passedStep("'AllProductionBatesRanges' field is Available in Dropdown");
		}else {
			base.failedStep("'AllProductionBatesRanges' field is not Available in Dropdown");
		
		}
		projectField.navigateToProjectFieldsPage();
		projectField.getAllProductionBatesRanges().waitAndClick(5);
		projectField.enableIsSearchableBatesRangeIsSelected();
		base.passedStep("Verified if currently 'AllProductionBatesRanges' is searchable, then we should leave the field to be searchable..");
		
	}
	/**
	 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
	 *         No:RPMXCON-50018
	 * @Description: Verify if in existing project, 'AllProductionBatesRange' field is searchable and if  this field has been edited and is make it non-searchable, then this field cannot make as searchable again
	 */
	//@Test(groups = { "regression" }, priority = 20)
	public void verifyAllProductionBatesRangesNotSearchable() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-50018 -Production Sprint 09");
		
		ProjectFieldsPage projectField=new ProjectFieldsPage(driver);
		projectField.navigateToProjectFieldsPage();
		projectField.getAllProductionBatesRanges().waitAndClick(5);
		projectField.enableIsSearchableBatesRangeIsSelected();
	
		SessionSearch sessionSearch = new SessionSearch(driver);
		this.driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.waitForPageToBeReady();
		sessionSearch.getBasicSearch_MetadataBtn().waitAndClick(5);
		sessionSearch.getSelectMetaData().waitAndClick(5);
		boolean flag=sessionSearch.getAllProductionBatesRanges().isDisplayed();
		SoftAssert softAssertion= new SoftAssert();
		if(flag) {
			softAssertion.assertTrue(flag);
			base.passedStep("'AllProductionBatesRanges' field is Available in Dropdown");
		}else {
			base.failedStep("'AllProductionBatesRanges' field is not Available in Dropdown");
		
		}
		projectField.navigateToProjectFieldsPage();
		projectField.getAllProductionBatesRanges().waitAndClick(5);
		projectField.disableIsSearchableBatesRangeIsSelected();
		projectField.getAllProductionBatesRanges().waitAndClick(5);
		projectField.disableIsSearchableBatesRangeIsSelected();
		base.passedStep("Verified if in existing project, 'AllProductionBatesRange' field is searchable and if  this field has been edited and is make it non-searchable, then this field cannot make as searchable again");
		
	}
	/**
	 * @author Aathith.Senthilkumar
	 * 			49099
	 * @Description In production, Preview should displays correctly
	 * 
	 */
		//@Test(groups = { "regression" }, priority = 21)
		public void verifyPreviewInProduction() throws Exception {
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49099 -Production Sprint 09");
		
		String testData1 = Input.testData1;
		foldername = "FolderProd" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		String tagNameTechnical = Input.tagNameTechnical;

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(testData1);
		sessionSearch.bulkFolderExisting(foldername);

		//Verify 
		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname,tagNameTechnical);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.getPreviewprod().waitAndClick(5);
		driver.waitForPageToBeReady();
		base.passedStep("In production, Preview should displays correctly");
		
		//delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
		
	 }
		/**
		 * @author Aathith.Senthilkumar
		 * 			49104
		 * @Description To verify that EmailAuthorNameAndAddress, EmailToNamesAndAddresses, EmailCCNamesAndAddresses, and EmailBCCNamesAndAddresses fields are exported properly in the correct format in the Production, DAT.
		 * 
		 */
			//@Test(groups = { "regression" }, priority = 22)
			public void verifyDatFieldsAreExport() throws Exception {
			UtilityLog.info(Input.prodPath);
			base.stepInfo("RPMXCON-49104 -Production Sprint 09");
			
			String testData1 = Input.testData1;
			foldername = "FolderProd" + Utility.dynamicNameAppender();
			tagname = "Tag" + Utility.dynamicNameAppender();
			//String tagNameTechnical = Input.tagNameTechnical;
			String newExport = "Ex" + Utility.dynamicNameAppender();

			// Pre-requisites
			// create tag and folder
			TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
			this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
			tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
			//tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

			// search for folder
			SessionSearch sessionSearch = new SessionSearch(driver);
			sessionSearch = new SessionSearch(driver);
			sessionSearch.basicContentSearch(testData1);
			sessionSearch.bulkFolderExisting(foldername);

			//Verify 
			ProductionPage page = new ProductionPage(driver);
			productionname = "p" + Utility.dynamicNameAppender();
			page.selectingDefaultSecurityGroup();
			String text = page.getProdExport_ProductionSets().getText();
			if (text.contains("Export Set")) {
				page.selectExportSetFromDropDown();
			} else {
				page.createNewExport(newExport);
			}
			page.addANewExport(productionname);
			page.fillingDATSection();
			page.getDAT_AddField().waitAndClick(5);
			page.addDatField(1, "Email", "EmailAuthorNameAndAddress");
			page.getDAT_AddField().waitAndClick(5);
			page.addDatField(2, "Email", "EmailToNamesAndAddresses");
			page.getDAT_AddField().waitAndClick(5);
			page.addDatField(3, "Email", "EmailCCNamesAndAddresses");
			page.getDAT_AddField().waitAndClick(5);
			page.addDatField(4, "Email", "EmailBCCNamesAndAddresses");
			
	
			page.navigateToNextSection();
			page.fillingExportNumberingAndSortingPage(prefixID, suffixID);
			page.navigateToNextSection();
			page.fillingDocumentSelectionPage(foldername);
			page.navigateToNextSection();
			page.fillingPrivGuardPage();
			page.fillingExportLocationPage(productionname);
			page.navigateToNextSection();
			page.fillingSummaryAndPreview();
			page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
			
			base.passedStep("Verified that EmailAuthorNameAndAddress, EmailToNamesAndAddresses, EmailCCNamesAndAddresses, and EmailBCCNamesAndAddresses fields are exported properly in the correct format in the Production, DAT.");
			//delete tags and folders
			tagsAndFolderPage = new TagsAndFoldersPage(driver);
			this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
			tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
			
		 }
			/**
			 * @author Aathith.Senthilkumar
			 * 			48978
			 * @Description Verify that branding text should be wrapped when Branding text to all the six locations exceeds the space while production for a PDF file.
			 * 
			 */
				//@Test(groups = { "regression" }, priority = 23)
				public void verifyBrandingTextToSixLocation() throws Exception {
				UtilityLog.info(Input.prodPath);
				base.stepInfo("RPMXCON-48978 -Production Sprint 09");
				
				String testData1 = Input.testData1;
				foldername = "FolderProd" + Utility.dynamicNameAppender();
				tagname = "Tag" + Utility.dynamicNameAppender();
				//String tagNameTechnical = Input.tagNameTechnical;

				// Pre-requisites
				// create tag and folder
				TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
				this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
				tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
				tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

				// search for folder
				SessionSearch sessionSearch = new SessionSearch(driver);
				sessionSearch = new SessionSearch(driver);
				sessionSearch.basicContentSearch(testData1);
				sessionSearch.bulkFolderExisting(foldername);

				//Verify 
				ProductionPage page = new ProductionPage(driver);
				productionname = "p" + Utility.dynamicNameAppender();
				String beginningBates = page.getRandomNumber(2);
				page.selectingDefaultSecurityGroup();
				page.addANewProduction(productionname);
				page.fillingDATSection();
				page.fillingPDFSectionWithMultiBranding(tagname);
				base.stepInfo("Added a multi line branding to all six  locations");
				page.navigateToNextSection();
				page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
				page.navigateToNextSection();
				page.fillingDocumentSelectionPage(foldername);
				page.navigateToNextSection();
				page.fillingPrivGuardPage();
				page.fillingProductionLocationPage(productionname);
				page.navigateToNextSection();
				page.fillingSummaryAndPreview();
				page.fillingGeneratePageWithContinueGenerationPopup();
				
				base.passedStep("Verified that branding text should be wrapped when Branding text to all the six locations exceeds the space while production for a PDF file");
				
				//delete tags and folders
				tagsAndFolderPage = new TagsAndFoldersPage(driver);
				tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
				tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
			}
				/**
				 * @author Aathith.Senthilkumar
				 * 			48660
				 * @Description To verify that if "Do Not Produce TIFFs/PDFs for Natively Produced Docs" is enabled , then TIFFs with redaction should be produced. It should not export Natives
				 * 
				 */
			//@Test(groups = { "regression" }, priority = 24)
			public void verifyDoNotProduceTiffToggleOn() throws Exception {
					loginPage.logout();
					loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
					UtilityLog.info(Input.prodPath);
					base.stepInfo("RPMXCON-48660 -Production Sprint 09");
					
					foldername = "RedactFolderProd" + Utility.dynamicNameAppender();
					tagname = "Tag" + Utility.dynamicNameAppender();
					String Redactiontag1;
					Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();
					
					RedactionPage redactionpage=new RedactionPage(driver);

			        driver.waitForPageToBeReady();
			        redactionpage.manageRedactionTagsPage(Redactiontag1);
			        System.out.println("First Redaction Tag is created"+Redactiontag1);
			        
			        DocExplorerPage docExp=new DocExplorerPage(driver);
		     		docExp.documentSelectionIteration();
		     		docExp.docExpViewInDocView();
		     		  
		     		DocViewRedactions docViewRedactions=new DocViewRedactions(driver);
		     		//doc1
		     		 docViewRedactions.selectDoc1();

		            driver.waitForPageToBeReady();
		             docViewRedactions.redactRectangleUsingOffset(10,10,100,100);
		             driver.waitForPageToBeReady();
		             docViewRedactions.selectingRedactionTag2(Redactiontag1);
		             

					// Pre-requisites
					// create tag and folder
					TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
					this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
					tagsAndFolderPage.CreateFolderInRMU(foldername);

					 //Adding folder to bulkfolder
		            DocExplorerPage docExplorer=new DocExplorerPage(driver);
		            docExplorer.documentSelectionIteration();
		            docExplorer.bulkFolderExisting(foldername);
					

					//Verify 
					ProductionPage page = new ProductionPage(driver);
					productionname = "p" + Utility.dynamicNameAppender();
					String beginningBates = page.getRandomNumber(2);
					page.selectingDefaultSecurityGroup();
					page.addANewProduction(productionname);
					page.fillingDATSection();
					page.fillingNativeSection();
					page.fillingTextSection();
					page.fillingTIFFSectionwithBurnRedactionSelectRedactTag(Redactiontag1);
					driver.scrollPageToTop();
					page.getDoNotProduceFullContentTiff().ScrollTo();
					page.getDoNotProduceFullContentTiff().waitAndClick(10);
					base.stepInfo("Enabled 'Do not produce full content TIFF / PDFs or placeholder TIFF / PDFs for Natively Produced Docs' toggle ON");
					page.navigateToNextSection();
					page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
					page.navigateToNextSection();
					page.fillingDocumentSelectionPage(foldername);
					page.navigateToNextSection();
					page.fillingPrivGuardPage();
					page.fillingProductionLocationPage(productionname);
					page.navigateToNextSection();
					page.fillingSummaryAndPreview();
					page.fillingGeneratePageWithContinueGenerationPopup();
					
					//Verify 
					page = new ProductionPage(driver);
					productionname = "p" + Utility.dynamicNameAppender();
					page.selectingDefaultSecurityGroup();
					page.addANewProduction(productionname);
					page.fillingDATSection();
					page.fillingNativeSection();
					page.fillingTextSection();
					page.fillingPDFSectionwithBurnRedactionSelectRedactTag(Redactiontag1);
					driver.scrollPageToTop();
					page.getDoNotProduceFullContentTiff().ScrollTo();
					page.getDoNotProduceFullContentTiff().waitAndClick(10);
					base.stepInfo("Enabled 'Do not produce full content TIFF / PDFs or placeholder TIFF / PDFs for Natively Produced Docs' toggle ON");
					page.navigateToNextSection();
					page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
					page.navigateToNextSection();
					page.fillingDocumentSelectionPage(foldername);
					page.navigateToNextSection();
					page.fillingPrivGuardPage();
					page.fillingProductionLocationPage(productionname);
					page.navigateToNextSection();
					page.fillingSummaryAndPreview();
					page.fillingGeneratePageWithContinueGenerationPopup();
					
					base.passedStep("Verified that if 'Do Not Produce TIFFs/PDFs for Natively Produced Docs' is enabled , then TIFFs with redaction should be produced. It should not export Natives");
					
					//delete tags and folders
					tagsAndFolderPage = new TagsAndFoldersPage(driver);
					tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
				}
			/**
			 * @author Aathith.Senthilkumar
			 * 			48454
			 * @Description To verify that Production should generate successfully for PDF docs.
			 * 
			 */
				//@Test(groups = { "regression" }, priority = 25)
				public void verifyProdGenSuccesInPdfDoc() throws Exception {
				UtilityLog.info(Input.prodPath);
				base.stepInfo("RPMXCON-48454 -Production Sprint 09");
				
				String testData1 = Input.testData1;
				foldername = "FolderProd" + Utility.dynamicNameAppender();
				tagname = "Tag" + Utility.dynamicNameAppender();
				//String tagNameTechnical = Input.tagNameTechnical;

				// Pre-requisites
				// create tag and folder
				TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
				this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
				tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
				tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

				// search for folder
				SessionSearch sessionSearch = new SessionSearch(driver);
				sessionSearch = new SessionSearch(driver);
				sessionSearch.basicContentSearch(testData1);
				sessionSearch.bulkFolderExisting(foldername);

				//Verify 
				ProductionPage page = new ProductionPage(driver);
				productionname = "p" + Utility.dynamicNameAppender();
				String beginningBates = page.getRandomNumber(2);
				page.selectingDefaultSecurityGroup();
				page.addANewProduction(productionname);
				page.fillingDATSection();
				page.fillingPDFSectionwithBurnRedaction(tagname);
				page.navigateToNextSection();
				page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
				page.navigateToNextSection();
				page.fillingDocumentSelectionPage(foldername);
				page.navigateToNextSection();
				page.fillingPrivGuardPage();
				page.fillingProductionLocationPage(productionname);
				page.navigateToNextSection();
				page.fillingSummaryAndPreview();
				page.fillingGeneratePageWithContinueGenerationPopup();
				
				base.passedStep("Verified that Production should generate successfully for PDF docs");
				
				//delete tags and folders
				tagsAndFolderPage = new TagsAndFoldersPage(driver);
				tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
				tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
			}
				/**
				 * @author Aathith.Senthilkumar
				 * 			48279
				 * @Description To Verify Enabling Placeholder for Privilege Doc at PrivGuard.
				 * 
				 */
					//@Test(groups = { "regression" }, priority = 26)
					public void verifiyEnablePlaceholderAtPrivDoc() throws Exception {
					UtilityLog.info(Input.prodPath);
					base.stepInfo("RPMXCON-48279 -Production Sprint 09");
					
					String testData1 = Input.testData1;
					foldername = "FolderProd" + Utility.dynamicNameAppender();
					tagname = "Tag" + Utility.dynamicNameAppender();
					//String tagNameTechnical = Input.tagNameTechnical;

					// Pre-requisites
					// create tag and folder
					TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
					this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
					tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
					tagsAndFolderPage.createNewTagwithClassification(tagname, "Privileged");

					// search for folder
					SessionSearch sessionSearch = new SessionSearch(driver);
					sessionSearch = new SessionSearch(driver);
					sessionSearch.basicContentSearch(testData1);
					sessionSearch.bulkFolderExisting(foldername);

					//Verify 
					ProductionPage page = new ProductionPage(driver);
					productionname = "p" + Utility.dynamicNameAppender();
					String beginningBates = page.getRandomNumber(2);
					page.selectingDefaultSecurityGroup();
					page.addANewProduction(productionname);
					page.fillingDATSection();
					page.fillingTiffSectionDisablePrivilegedDocs();
					page.navigateToNextSection();
					page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
					page.navigateToNextSection();
					page.fillingDocumentSelectionPage(foldername);
					page.navigateToNextSection();
					page.fillingPrivGuardPageWithPrivPlaceHolder(tagname);
					page.fillingProductionLocationPage(productionname);
					page.navigateToNextSection();
					page.fillingSummaryAndPreview();
					page.fillingGeneratePageWithContinueGenerationPopup();
					
					//Verify 
					page = new ProductionPage(driver);
					productionname = "p" + Utility.dynamicNameAppender();
					page.selectingDefaultSecurityGroup();
					page.addANewProduction(productionname);
					page.fillingDATSection();
					page.fillingPDFSectionDisablePrivilegedDocs();
					page.navigateToNextSection();
					page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
					page.navigateToNextSection();
					page.fillingDocumentSelectionPage(foldername);
					page.navigateToNextSection();
					page.fillingPrivGuardPageWithPrivPlaceHolder(tagname);
					page.fillingProductionLocationPage(productionname);
					page.navigateToNextSection();
					page.fillingSummaryAndPreview();
					page.fillingGeneratePageWithContinueGenerationPopup();
					
					base.passedStep("Verified Enabling Placeholder for Privilege Doc at PrivGuard.");
					
					//delete tags and folders
					tagsAndFolderPage = new TagsAndFoldersPage(driver);
					tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
					tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
				}
					/**
					 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
					 *         No:RPMXCON-55981
					 * @Description: Verify that after LST generation, if Destination Copy is in progress, it will displays status as 'Exporting Files' on Production Generation tab
					 */
					//@Test(groups = { "regression" }, priority = 27)
					public void verifyLstGenExportinFilesStatusOnGen() throws Exception {
					UtilityLog.info(Input.prodPath);
					base.stepInfo("RPMXCON-55981 -Production Sprint 09");
					base.stepInfo("Verify that after LST generation, if Destination Copy is in progress, it will displays status as 'Exporting Files' on Production Generation tab");
					
					String testData1 = Input.testData1;
					foldername = "FolderProd" + Utility.dynamicNameAppender();
					tagname = "Tag" + Utility.dynamicNameAppender();
					

					// Pre-requisites
					// create tag and folder
					TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
					this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
					tagsAndFolderPage.CreateTagwithClassification(tagname, "Privileged");
					tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
					

					// search for folder
					SessionSearch sessionSearch = new SessionSearch(driver);
					sessionSearch = new SessionSearch(driver);
					sessionSearch.basicContentSearch(testData1);
					sessionSearch.bulkTagExisting(tagname);
					sessionSearch.bulkFolderExisting(foldername);
					

					//Verify archive status on Gen page
					ProductionPage page = new ProductionPage(driver);
					productionname = "p" + Utility.dynamicNameAppender();
					String beginningBates = page.getRandomNumber(2);
					page.selectingDefaultSecurityGroup();
					page.addANewProduction(productionname);
					page.fillingDATSection();
					page.fillingNativeSection();
					page.fillingTIFFSection(tagname);
					page.fillingTextSection();
					page.navigateToNextSection();
					page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
					page.navigateToNextSection();
					page.fillingDocumentSelectionPage(foldername);
					page.navigateToNextSection();
					page.fillingPrivGuardPage();
					page.fillingProductionLocationPage(productionname);
					page.navigateToNextSection();
					page.fillingSummaryAndPreview();
					page.clickOnGenerateButton();
					
					page.verifyProductionStatusInGenPage("Load File Generation In Progress");
					page.verifyProductionStatusInGenPage("Exporting Files");
					base.passedStep("Verified that after LST generation, if Destination Copy is in progress, it will displays status as 'Exporting Files' on Production Generation tab");
					
					//delete tags and folders
					tagsAndFolderPage = new TagsAndFoldersPage(driver);
					this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
					tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
					tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
				}
					/**
					 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
					 *         No:RPMXCON-55980
					 * @Description: Verify that after LST generation, if Destination Copy is in progress, it will displays status as 'Exporting Files' on Production Progress bar Tile View.
					 */
					//@Test(groups = { "regression" }, priority = 28)
					public void verifyLstGenExportinFilesStatusOnTileView() throws Exception {
					UtilityLog.info(Input.prodPath);
					base.stepInfo("RPMXCON-55980 -Production Sprint 09");
					base.stepInfo("Verify that after LST generation, if Destination Copy is in progress, it will displays status as 'Exporting Files' on Production Progress bar Tile View");
					
					String testData1 = Input.testData1;
					foldername = "FolderProd" + Utility.dynamicNameAppender();
					tagname = "Tag" + Utility.dynamicNameAppender();
					

					// Pre-requisites
					// create tag and folder
					TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
					this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
					tagsAndFolderPage.CreateTagwithClassification(tagname, "Privileged");
					tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

					// search for folder
					SessionSearch sessionSearch = new SessionSearch(driver);
					sessionSearch = new SessionSearch(driver);
					sessionSearch.basicContentSearch(testData1);
					sessionSearch.bulkTagExisting(tagname);
					sessionSearch.bulkFolderExisting(foldername);

					//Verify archive status on Gen page
					ProductionPage page = new ProductionPage(driver);
					productionname = "p" + Utility.dynamicNameAppender();
					String beginningBates = page.getRandomNumber(2);
					page.selectingDefaultSecurityGroup();
					page.addANewProduction(productionname);
					page.fillingDATSection();
					page.fillingNativeSection();
					page.fillingTIFFSection(tagname);
					page.fillingTextSection();
					page.navigateToNextSection();
					page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
					page.navigateToNextSection();
					page.fillingDocumentSelectionPage(foldername);
					page.navigateToNextSection();
					page.fillingPrivGuardPage();
					page.fillingProductionLocationPage(productionname);
					page.navigateToNextSection();
					page.fillingSummaryAndPreview();
					page.clickOnGenerateButton();
					
					// Go To Production Home Page
					this.driver.getWebDriver().get(Input.url + "Production/Home");
					driver.Navigate().refresh();
					//verification
					page.verifyProductionStatusInHomePage("Generating Load File", productionname);
					page.verifyProductionStatusInHomePage("Exporting Files", productionname);
					base.passedStep("Verified that after LST generation, if Destination Copy is in progress, it will displays status as 'Exporting Files' on Production Progress bar Tile View");
					
					//delete tags and folders
					tagsAndFolderPage = new TagsAndFoldersPage(driver);
					this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
					tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
					tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
					
				}
					/**
					 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
					 *         No:RPMXCON-49040
					 * @Description: To verify that 'Production Creation Date' should displayed when it saved first time.
					 */
					//@Test(groups = { "regression" }, priority = 29)
					public void verifyProductionCreationDate() throws Exception {
					UtilityLog.info(Input.prodPath);
					base.stepInfo("RPMXCON-49040 -Production Sprint 09");
					base.stepInfo("To verify that 'Production Creation Date' should displayed when it saved first time");
					
					ProductionPage page = new ProductionPage(driver);
					productionname = "p" + Utility.dynamicNameAppender();
					page.selectingDefaultSecurityGroup();
					//page.addANewProduction(productionname);
					page.getAddNewProductionbutton().waitAndClick(10);
					page.getProductionName().SendKeys(productionname);
					page.getSaveButton().waitAndClick(10);
					base.stepInfo("production saved");
					driver.waitForPageToBeReady();
					this.driver.getWebDriver().get(Input.url + "Production/Home");
					driver.Navigate().refresh();
					driver.waitForPageToBeReady();
					page.getGridView().waitAndClick(10);
					driver.waitForPageToBeReady();
					base.stepInfo("Nativated to production Grid View");
					String createdTime =page.getProductionCreatedTimeInGridView(productionname).getText();
					
					DateFormat dateFormat = new SimpleDateFormat("dd");
					Date date = new Date();
					String date1= dateFormat.format(date);
					System.out.println("Current date" +date1);
					boolean flag =createdTime.contains(date1);
					if(flag) {
						base.passedStep("current date is displayed on production grid view");
						System.out.println("date visible");
					}else {
						base.failedStep("date is not contain in text");
						System.out.println("date not visible");
					}
					base.passedStep("Verified that 'Production Creation Date' should displayed when it saved first time");
					
					}
					/**
					 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
					 *         No:RPMXCON-48569
					 * @Description: To verify that 'Bates Range' should be blank before pre-gen check completed.
					 */
					//@Test(groups = { "regression" }, priority = 30)
					public void verifyBateRangeBlankGenPage() throws Exception {
					UtilityLog.info(Input.prodPath);
					base.stepInfo("RPMXCON-48569 -Production Sprint 09");
					base.stepInfo("To verify that 'Bates Range' should be blank before pre-gen check completed.");
					String testData1 = Input.testData1;
					foldername = "FolderProd" + Utility.dynamicNameAppender();
					tagname = "Tag" + Utility.dynamicNameAppender();
					
					// Pre-requisites
					// create tag and folder
					TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
					this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
					//tagsAndFolderPage.CreateTagwithClassification(tagname, "Privileged");
					tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
					

					// search for folder
					SessionSearch sessionSearch = new SessionSearch(driver);
					sessionSearch = new SessionSearch(driver);
					sessionSearch.basicContentSearch(testData1);
					//sessionSearch.bulkTagExisting(tagname);
					sessionSearch.bulkFolderExisting(foldername);
					
					//Verify archive status on Gen page
					ProductionPage page = new ProductionPage(driver);
					productionname = "p" + Utility.dynamicNameAppender();
					String beginningBates = page.getRandomNumber(2);
					page.selectingDefaultSecurityGroup();
					page.addANewProduction(productionname);
					page.fillingDATSection();
					page.navigateToNextSection();
					page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
					page.navigateToNextSection();
					page.fillingDocumentSelectionPage(foldername);
					page.navigateToNextSection();
					page.fillingPrivGuardPage();
					page.fillingProductionLocationPage(productionname);
					page.navigateToNextSection();
					page.fillingSummaryAndPreview();
					page.clickOnGenerateButton();
					String blank = page.getProd_BatesRange().getText();
					System.out.println("bank text"+blank+"message");
					if(blank.isBlank()) {
						base.passedStep("Bates Range' is blank before pre-gen check complete");
					}else {
						base.failedStep("Bates Range' didn't blank before pre-gen check complete");
					}
					page.verifyProductionStatusInGenPage("Pre-Generation Checks Completed");
					base.passedStep("To verify that 'Bates Range' should be blank before pre-gen check completed.");
					
					//delete tags and folders
					tagsAndFolderPage = new TagsAndFoldersPage(driver);
					this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
					tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
					//tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
				}
					/**
					 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
					 *         No:RPMXCON-48344
					 * @Description: To verify that Tiff/PDF should generate with Priv placeholdering even though 'Tech Issue Doc' placeholdering, Burn redactions and File group/tag based placeholdering is exists.
					 */
				@Test(groups = { "regression" }, priority = 31)
					public void verifyGenWithPrivplcholderTechIssueRedactionTag() throws Exception {
					UtilityLog.info(Input.prodPath);
					base.stepInfo("RPMXCON-48344 -Production Sprint 09");
					base.stepInfo("To verify that Tiff/PDF should generate with Priv placeholdering even though 'Tech Issue Doc' placeholdering, Burn redactions and File group/tag based placeholdering is exists.");
					String testData1 = Input.testData1;
					foldername = "FolderProd" + Utility.dynamicNameAppender();
					tagname = "Tag" + Utility.dynamicNameAppender();
					String tagname1 = "Tag" + Utility.dynamicNameAppender();
					
					// Pre-requisites
					// create tag and folder
					TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
					this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
					tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
					tagsAndFolderPage.CreateTagwithClassification(tagname1, Input.technicalIssue);
					tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
					

					// search for folder
					SessionSearch sessionSearch = new SessionSearch(driver);
					sessionSearch = new SessionSearch(driver);
					sessionSearch.basicContentSearch(testData1);
					sessionSearch.bulkTagExisting(tagname);
					driver.waitForPageToBeReady();
					sessionSearch.bulkFolderExisting(foldername);
					sessionSearch.bulkTagExisting(tagname1);
					
					
					//prod tiff
					ProductionPage page = new ProductionPage(driver);
					productionname = "p" + Utility.dynamicNameAppender();
					String beginningBates = page.getRandomNumber(2);
					page.selectingDefaultSecurityGroup();
					page.addANewProduction(productionname);
					page.fillingDATSection();
					page.fillingTIFFSection(tagname);
					page.selectTechIssueDoc(tagname1);
					page.fillingNativeDocsPlaceholder(tagname);
					page.selectBurnReduction();
					driver.scrollPageToTop();
					page.navigateToNextSection();
					page.InsertingDataFromNumberingToGenerate(prefixID, suffixID, foldername, productionname,beginningBates);
					
					
					//Verify archive status on Gen page
					page = new ProductionPage(driver);
					productionname = "p" + Utility.dynamicNameAppender();
					page.selectingDefaultSecurityGroup();
					page.addANewProduction(productionname);
					page.fillingDATSection();
					page.fillingPDFSection(tagname);
					page.selectTechIssueDoc(tagname1);
					page.fillingNativeDocsPlaceholder(tagname);
					page.selectBurnReduction();
					driver.scrollPageToTop();
					page.navigateToNextSection();
					page.InsertingDataFromNumberingToGenerate(prefixID, suffixID, foldername, productionname,beginningBates);
					base.passedStep("verified that Tiff/PDF should generate with Priv placeholdering even though 'Tech Issue Doc' placeholdering, Burn redactions and File group/tag based placeholdering is exists.");
					
					//delete tags and folders
					tagsAndFolderPage = new TagsAndFoldersPage(driver);
					this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
					tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
					tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
				}
					/**
					 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
					 *         No:RPMXCON-48533
					 * @Description: To verify that if Blank Page Removal toggle is OFF then it should produced the PDF with blank pages
					 */
					@Test(groups = { "regression" }, priority = 32)
					public void verifyBlankRemovalToggleWithpdfGen() throws Exception {
					UtilityLog.info(Input.prodPath);
					base.stepInfo("RPMXCON-48533 -Production Sprint 09");
					base.stepInfo("To verify that if Blank Page Removal toggle is OFF then it should produced the PDF with blank pages");
					String testData1 = Input.testData1;
					foldername = "FolderProd" + Utility.dynamicNameAppender();
					tagname = "Tag" + Utility.dynamicNameAppender();
					
					// Pre-requisites
					// create tag and folder
					TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
					this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
					tagsAndFolderPage.CreateTagwithClassification(tagname, "Privileged");
					tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
					

					// search for folder
					SessionSearch sessionSearch = new SessionSearch(driver);
					sessionSearch = new SessionSearch(driver);
					sessionSearch.basicContentSearch(testData1);
					sessionSearch.bulkTagExisting(tagname);
					sessionSearch.bulkFolderExisting(foldername);
					
					//Verify archive status on Gen page
					ProductionPage page = new ProductionPage(driver);
					productionname = "p" + Utility.dynamicNameAppender();
					String beginningBates = page.getRandomNumber(2);
					page.selectingDefaultSecurityGroup();
					page.addANewProduction(productionname);
					page.fillingDATSection();
					page.fillingPDFSection(tagname);
					page.banlkPageRemovalToggleOffCheck();
					page.navigateToNextSection();
					page.InsertingDataFromNumberingToGenerate(prefixID, suffixID, foldername, productionname,beginningBates);
					
					base.passedStep("verified that if Blank Page Removal toggle is OFF then it should produced the PDF with blank pages");
					
					//delete tags and folders
					tagsAndFolderPage = new TagsAndFoldersPage(driver);
					this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
					tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
					tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
				}
					/**
					 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
					 *         No:RPMXCON-55920
					 * @Description: Verify if PA Select the Production using a template that has only tags selected in the native components, then Component tab should Complete without any error.
					 */
					@Test(groups = { "regression" }, priority = 33)
					public void verifiyProdComponentTabWithoutError() throws Exception {
					UtilityLog.info(Input.prodPath);
					base.stepInfo("RPMXCON-55920 -Production Sprint 10");
					base.stepInfo("Verify if PA Select the Production using a template that has only tags selected in the native components, then Component tab should Complete without any error.");
					String testData1 = Input.testData1;
					foldername = "FolderProd" + Utility.dynamicNameAppender();
					tagname = "Tag" + Utility.dynamicNameAppender();
					TempName ="Templete" + Utility.dynamicNameAppender();
					
					// Pre-requisites
					// create tag and folder
					TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
					this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
					tagsAndFolderPage.CreateTagwithClassification(tagname, "Privileged");
					tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
					

					// search for folder
					SessionSearch sessionSearch = new SessionSearch(driver);
					sessionSearch = new SessionSearch(driver);
					sessionSearch.basicContentSearch(testData1);
					sessionSearch.bulkTagExisting(tagname);
					sessionSearch.bulkFolderExisting(foldername);
					
					//Verify archive status on Gen page
					ProductionPage page = new ProductionPage(driver);
					productionname = "p" + Utility.dynamicNameAppender();
					String beginningBates = page.getRandomNumber(2);
					page.selectingDefaultSecurityGroup();
					page.addANewProduction(productionname);
					page.fillingDATSection();
					page.fillingNativeSectionWithTags(tagname);
					page.navigateToNextSection();
					page.InsertingDataFromNumberingToGenerateWithContinuePopup(prefixID, suffixID, foldername, productionname,beginningBates);
					
					page = new ProductionPage(driver);
					page.getFilterByButton().waitAndClick(10);
					page.getFilterByCOMPLETED().waitAndClick(10);
					page.getRefreshButton().waitAndClick(10);
					//page.getProductionHomePage().waitAndClick(10);
					page.getprod_ActionButton_Reusable(productionname).waitAndClick(10);
					driver.waitForPageToBeReady();
					page.getprod_Action_SaveTemplate_Reusable(productionname).waitAndClick(10);

					page.saveTemple(TempName);
					
					page = new ProductionPage(driver);
					productionname = "p" + Utility.dynamicNameAppender();
					page.selectingDefaultSecurityGroup();
					page.getAddNewProductionbutton().waitAndClick(10);
					page.getProductionName().SendKeys(productionname);
					String loadfile=TempName+" (Production)";
					page.getprod_LoadTemplate().selectFromDropdown().selectByVisibleText(loadfile);
					page.navigateToNextSection();
					driver.waitForPageToBeReady();
					page.navigateToNextSection();
					base.passedStep("It should be completed without any error.");
					base.passedStep("Verified if PA Select the Production using a template that has only tags selected in the native components, then Component tab should Complete without any error.");
					
					//delete tags and folders
					tagsAndFolderPage = new TagsAndFoldersPage(driver);
					this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
					tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
					tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
				}
					/**
					 * @author Aathith Senthilkumar created on:NA modified by:NA TESTCASE
					 *         No:RPMXCON-48183
					 * @Description: To Verify Document Selection Page (for Folder/Tag/Search; Include Family ;Total Count)
					 */
					@Test(groups = { "regression" }, priority = 34)
					public void verifyDocmentSelectionPage() throws Exception {
					UtilityLog.info(Input.prodPath);
					base.stepInfo("RPMXCON-48183 -Production Sprint 10");
					base.stepInfo("To Verify Document Selection Page (for Folder/Tag/Search; Include Family ;Total Count)");
					String testData1 = Input.testData1;
					foldername = "FolderProd" + Utility.dynamicNameAppender();
					tagname = "Tag" + Utility.dynamicNameAppender();
					TempName ="Templete" + Utility.dynamicNameAppender();
					
					// Pre-requisites
					// create tag and folder
					TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
					this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
					tagsAndFolderPage.CreateTagwithClassification(tagname, "Privileged");
					tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");
					

					// search for folder
					SessionSearch sessionSearch = new SessionSearch(driver);
					sessionSearch = new SessionSearch(driver);
					sessionSearch.basicContentSearch(testData1);
					sessionSearch.bulkTagExisting(tagname);
					sessionSearch.bulkFolderExisting(foldername);
					
					//Verify archive status on Gen page
					ProductionPage page = new ProductionPage(driver);
					productionname = "p" + Utility.dynamicNameAppender();
					String beginningBates = page.getRandomNumber(2);
					page.selectingDefaultSecurityGroup();
					page.addANewProduction(productionname);
					page.fillingDATSection();
					page.fillingNativeSection();
					page.fillingTIFFSection(tagname);
					page.navigateToNextSection();
					page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
					page.navigateToNextSection();
					driver.waitForPageToBeReady();
					page.visibleCheck("Folder");
					page.visibleCheck("Tag");
					page.visibleCheck("Search");
					page.visibleCheck("Include Family");
					page.visibleCheck("Count");
					
					base.passedStep("Verified Document Selection Page (for Folder/Tag/Search; Include Family ;Total Count)");
					tagsAndFolderPage = new TagsAndFoldersPage(driver);
					this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
					tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, "Default Security Group");
					tagsAndFolderPage.DeleteTagWithClassification(tagname, "Default Security Group");
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
			loginPage.logout();
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
			LoginPage.clearBrowserCache();
		}
	}
	

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR PRODUCTION EXECUTED******");
		try {
			loginPage.clearBrowserCache();
		} catch (Exception e) {
			// no session avilable

		}
	}
	
}

