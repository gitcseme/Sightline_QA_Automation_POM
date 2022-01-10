package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ManageAssignment;
import pageFactory.ProductionPage;
import pageFactory.ProjectFieldsPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Export_Regression {

	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	DocViewMetaDataPage docViewMetaDataPage;
	ManageAssignment manageAssignment;
	DocViewPage docView;
	Utility utility;

	@BeforeMethod(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");

		Input input = new Input();
		input.loadEnvConfig();

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
	 * @Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * @TestCase id :  49359 - Verify that if PA selects the Export with Production and has only Native Files selected in the native components section, then Component tab should Complete without any error.
	 * @Description : Verify that if PA selects the Export with Production and has only Native Files selected in the native components section, then Component tab should Complete without any error.
	 */
	@Test(alwaysRun = true,groups={"regression"},priority = 1)
	public void verifyExportWithProductionNativeComponentsSection() throws Exception {		
		base=new BaseClass(driver);
		base.stepInfo("Test case Id: RPMXCON-49359 Export Sprint-8");
		utility = new Utility(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		base.stepInfo("#### Verify that if PA selects the Export with Production and has only Native Files selected in the native components section, then Component tab should Complete without any error. ####");
 		String foldername = Input.randomText + Utility.dynamicNameAppender();
 		String tagname = Input.randomText + Utility.dynamicNameAppender();
 		String tagname1 = Input.randomText + Utility.dynamicNameAppender();
 		String productionname = Input.randomText+ Utility.dynamicNameAppender();
 		String testData1 = Input.testData1;
 		String prefixID = Utility.randomCharacterAppender(25);
 		String suffixID =Utility.randomCharacterAppender(25);
 		String exportName = Input.randomText+ Utility.dynamicNameAppender();
     
 		
        TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
 		
 		base.stepInfo("Navigate To Tags And Folder Page");
 		tagsAndFolderPage.navigateToTagsAndFolderPage();
 		
 		base.stepInfo("Create folder");
     	tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
     	
     	base.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateTagwithClassification(tagname1, Input.tagNamePrev);
		
     	
 		SessionSearch sessionSearch = new SessionSearch(driver);
 		
 		base.stepInfo("Basic content search");
 		sessionSearch.basicContentSearch(testData1);
 		
 		base.stepInfo("Bulk folder existing");
 		sessionSearch.bulkFolderExisting(foldername);
 		
 		base.stepInfo("Bulk Tag Existing");
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.bulkTagExisting(tagname1);
 		
 		ProductionPage page = new ProductionPage(driver);
 		String beginningBates = page.getRandomNumber(2);
 		
 		base.stepInfo("Selecting Default Security Group");
 		page.selectingDefaultSecurityGroup();
 
 		base.stepInfo("Add New Production");
 		page.addANewProduction(productionname);
 		
 		base.stepInfo("Filling DAT Section");
 		page.fillingDATSection();
 		
 		base.stepInfo("Filling Native Section");
		page.fillingNativeSectionWithTags(tagname,tagname1);
 		
 		base.stepInfo("Filling Text Section");
 		page.fillingTextSection();
 		
 		base.stepInfo("Navigate To Next Section");
 		page.navigateToNextSection();
 	
 		base.stepInfo("Filling Numbering And Sorting Page");
 		page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
 		
 		base.stepInfo("Navigate To Next Section");
 		page.navigateToNextSection();
 		
 		base.stepInfo("Filling Document Selection Page");
 		page.fillingDocumentSelectionPage(foldername);
 		
 		base.stepInfo("Navigate To Next Section");
 		page.navigateToNextSection();
 		
 		base.stepInfo("Filling Priv Guard Page");
 		page.fillingPrivGuardPage();
 		
 		base.stepInfo("Filling Production Location Page And Passing Text");
 		page.fillingProductionLocationPageAndPassingText(productionname);
 		
 		base.stepInfo("Navigate To Next Section");
 		page.navigateToNextSection();
 		
 		base.stepInfo("Filling Summary And Preview");
 		page.fillingSummaryAndPreview();
 		
 		base.stepInfo("Filling Generate Page");
 		page.fillingGeneratePageWithContinueGenerationPopup();
 		
 		base.stepInfo("Navigate To Export Page");
 		page.navigateToExportPageByNewExportSet(exportName);
 		
 		base.stepInfo("Enter Basic Details Stage By Selecting Production");
 		page.enterBasicDetailsStageBySelectingProduction(exportName, productionname);
 		
 		base.stepInfo("Click On Complete And Verify Success Message");
 		page.clickOnCompleteAndVerifySuccessMessage();
	}
	

	/**
	 * @author Gopinath created on:NA modified by:NA
	 * @TESTCASE_No:RPMXCON-49253 : To verify In Export DAT, TIFFPageCount for each document should be displayed when export is done with any component.
	 * @Description:To verify In Export DAT, TIFFPageCount for each document should be displayed when export is done with any component.
	 * 
	 */
	@Test(groups = { "regression" }, priority = 2)
	public void verifyTIFFPageCountAndTiffSectionExport() throws Exception {
		base=new BaseClass(driver);
		base.stepInfo("#### To verify In Export DAT, TIFFPageCount for each document should be displayed when export is done with any component ####");
 		

		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String tagNameTechnical = Input.tagNameTechnical;
		String testData1 = Input.testData1;
		String exportname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Utility.randomCharacterAppender(25);
 		String suffixID =Utility.randomCharacterAppender(25);
		
		
		// Pre-requisites
		// create folder and tag
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		
		base.stepInfo("Navigate to security group");
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		
		base.stepInfo("Create Folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		
		base.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		
		// testData string contains redacted documents as per Pre-requisites
		base.stepInfo("Basic Content Search");
		sessionSearch.basicContentSearch(testData1);
		
		base.stepInfo("Bulk Folder Existing");
		sessionSearch.bulkFolderExisting(foldername);

		// create export with native which displays message about priviledged and
		// redacted export
		ProductionPage page = new ProductionPage(driver);
		
		base.stepInfo("Selecting Default Security Group");
		page.selectingDefaultSecurityGroup();
		
		base.stepInfo("select Export Set From DropDown");
		page.selectExportSetFromDropDown();
		
		base.stepInfo("Add New Export");
		page.addANewExport(exportname);
		
		base.stepInfo("Filling DAT Section");
 		page.fillingDATSection();
		
 		base.stepInfo("Add DAT Field At SecondRow");
		page.addDATFieldAtSecondRow(Input.productionText, Input.tiffPageCountNam, Input.tiffPageCountText);
		
 		base.stepInfo("Add DAT Field At Third row");
		page.addDATFieldAtThirdRow(Input.docBasic,Input.docName,Input.documentID);
		
		base.stepInfo("Filling Native Section");
		page.fillingNativeSection();
		
		base.stepInfo("Filling TIFF Section");
		page.fillingTIFFSection(tagname, tagNameTechnical);
		
		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();
		
		base.stepInfo("Filling Export Numbering And Sorting Page");
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID);
		
		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();
		
		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);
		
		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();
		
		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();
		
		base.stepInfo("Filling Export Location Page");
		page.fillingExportLocationPage(exportname);
		
		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();
		
		base.stepInfo("Viewing Tool Tip In Summary And Preview");
		page.viewingToolTipInSummaryAndPreview();
		base.passedStep("Verified that for Native section should be displayed message which will inform the user about the privileged and redacted docs from export");
	
		//To delete tag and folder 
		base.stepInfo("Navigate To Tags And Folder Page");
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		
		base.stepInfo("Delete Folder With Security Group");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		
		base.stepInfo("Delete Tag With Classification");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
	}
	
	/**
	 * @author Gopinath created on:NA modified by:NA
	 * @TESTCASE_No:RPMXCON-49252 : To verify In Export DAT, provide the TIFFPageCount for each document should be zero when only DAT component is selected
	 * @Description: To verify In Export DAT, provide the TIFFPageCount for each document should be zero when only DAT component is selected
	 * 
	 */
	@Test(groups = { "regression" }, priority = 3)
	public void verifyTiffPageCountOnDATForExport() throws Exception {
		base=new BaseClass(driver);
		base.stepInfo("#### To verify In Export DAT, provide the TIFFPageCount for each document should be zero when only DAT component is selected ####");
 		

		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;
		String exportname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Utility.randomCharacterAppender(25);
 		String suffixID =Utility.randomCharacterAppender(25);
		
		
		// Pre-requisites
		// create folder and tag
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		
		base.stepInfo("Navigate to security group");
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		
		base.stepInfo("Create Folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		
		base.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		
		// testData string contains redacted documents as per Pre-requisites
		base.stepInfo("Basic Content Search");
		sessionSearch.basicContentSearch(testData1);
		
		base.stepInfo("Bulk Folder Existing");
		sessionSearch.bulkFolderExisting(foldername);

		// create export with native which displays message about priviledged and
		// redacted export
		ProductionPage page = new ProductionPage(driver);
		
		base.stepInfo("Selecting Default Security Group");
		page.selectingDefaultSecurityGroup();
		
		base.stepInfo("select Export Set From DropDown");
		page.selectExportSetFromDropDown();
		
		base.stepInfo("Add New Export");
		page.addANewExport(exportname);
		
		base.stepInfo("Filling DAT Section");
 		page.fillingDATSection();
		
 		base.stepInfo("Add DAT Field At SecondRow");
		page.addDATFieldAtSecondRow(Input.productionText, Input.tiffPageCountNam, Input.tiffPageCountText);
		
 		base.stepInfo("Add DAT Field At Third row");
		page.addDATFieldAtThirdRow(Input.docBasic,Input.docName,Input.documentID);
		
		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();
		
		base.stepInfo("Filling Export Numbering And Sorting Page");
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID);
		
		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();
		
		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);
		
		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();
		
		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();
		
		base.stepInfo("Filling Export Location Page");
		page.fillingExportLocationPage(exportname);
		
		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();
		
		base.stepInfo("Viewing Tool Tip In Summary And Preview");
		page.viewingToolTipInSummaryAndPreview();
		base.passedStep("Verified that for Native section should be displayed message which will inform the user about the privileged and redacted docs from export");
	
		//To delete tag and folder 
		base.stepInfo("Navigate To Tags And Folder Page");
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		
		base.stepInfo("Delete Folder With Security Group");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		
		base.stepInfo("Delete Tag With Classification");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
	}
	
	
	/**
	 * @Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * @TestCase id :  49220 -To verify that in Production Export, this production will not show up as it was uncommitted.
	 * @Description : Verify that in Production Export, this production will not show up as it was uncommitted.
	 */
	@Test(alwaysRun = true,groups={"regression"},priority = 4)
	public void verifyProductionNotShownInExportWhenUncommited() throws Exception {		
		base=new BaseClass(driver);
		base.stepInfo("Test case Id: RPMXCON-49220 Export Sprint-8");
		utility = new Utility(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		base.stepInfo("#### Verify that in Production Export, this production will not show up as it was uncommitted. ####");
 		String foldername = Input.randomText + Utility.dynamicNameAppender();
 		String productionname = Input.randomText+ Utility.dynamicNameAppender();
 		String testData1 = Input.testData1;
 		String prefixID = Utility.randomCharacterAppender(25);
 		String suffixID =Utility.randomCharacterAppender(25);
 		String exportName = Input.randomText+ Utility.dynamicNameAppender();
     
 		
        TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
 		
 		base.stepInfo("Navigate To Tags And Folder Page");
 		tagsAndFolderPage.navigateToTagsAndFolderPage();
 		
 		base.stepInfo("Create folder");
     	tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
     	
 		SessionSearch sessionSearch = new SessionSearch(driver);
 		
 		base.stepInfo("Basic content search");
 		sessionSearch.basicContentSearch(testData1);
 		
 		base.stepInfo("Bulk folder existing");
 		sessionSearch.bulkFolderExisting(foldername);
 		
 		ProductionPage page = new ProductionPage(driver);
 		String beginningBates = page.getRandomNumber(2);
 		
 		base.stepInfo("Selecting Default Security Group");
 		page.selectingDefaultSecurityGroup();
 
 		base.stepInfo("Add New Production");
 		page.addANewProduction(productionname);
 		
 		base.stepInfo("Filling DAT Section");
 		page.fillingDATSection();
 		
 		base.stepInfo("Navigate To Next Section");
 		page.navigateToNextSection();
 	
 		base.stepInfo("Filling Numbering And Sorting Page");
 		page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
 		
 		base.stepInfo("Navigate To Next Section");
 		page.navigateToNextSection();
 		
 		base.stepInfo("Filling Document Selection Page");
 		page.fillingDocumentSelectionPage(foldername);
 		
 		base.stepInfo("Navigate To Next Section");
 		page.navigateToNextSection();
 		
 		base.stepInfo("Filling Priv Guard Page");
 		page.fillingPrivGuardPage();
 		
 		base.stepInfo("Filling Production Location Page And Passing Text");
 		page.fillingProductionLocationPageAndPassingText(productionname);
 		
 		base.stepInfo("Navigate To Next Section");
 		page.navigateToNextSection();
 		
 		base.stepInfo("Filling Summary And Preview");
 		page.fillingSummaryAndPreview();
 		
 		base.stepInfo("Filling Generate Page");
 		page.fillingGeneratePageWithContinueGenerationPopup();
 		
 		base.stepInfo("Navigate To Export Page");
 		page.navigateToExportPageByNewExportSet(exportName);
 		
 		base.stepInfo("Verify committed production is added to export production drop down.");
 		page.verifyCommittedProdIsAddedToExportProductionDropdown(exportName, productionname);
 		
 		base.stepInfo("Perform operation on production filter");
		page.ProductionFilter();
		
		base.stepInfo("Select row by production name and open in wizard.");
		page.selectRowByProductionNameAndOpenWizard(productionname);
		
		base.stepInfo("Click on uncommit bates");
		page.uncommitBates();
		
		base.stepInfo("Navigate To Production Page");
		page.navigateToProductionPage();
		
		
		base.stepInfo("Navigating To Export Page by already existing export set.");
		page.navigateToExportPageByAlreadyExistingExportSet(exportName);
		
		base.stepInfo("Verify uncommitted production is not added to export production drop down.");
		page.verifyUnCommittedProdIsNotAddedToExportProdDropdown(exportName, productionname);
	}
	
	
	
	/**
	 * @author Gopinath created on:NA modified by:NA
	 * @TESTCASE_No:RPMXCON-49137 : To verify that when text is for document then it should export the actual text file.
	 * @Description: To verify that when text is for document then it should export the actual text file.
	 * 
	 */
	@Test(groups = { "regression" }, priority = 5)
	public void verifyTextSecDocument() throws Exception {
		base=new BaseClass(driver);
		base.stepInfo("#### To verify that when text is for document then it should export the actual text file ####");
 	
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49137 Export-sprint:08");
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText+ Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag
		
		loginPage = new LoginPage(driver);
		
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		
		base.stepInfo("Navigate To Tags And Folder Page");
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		
		base.stepInfo("Create Folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		// search for the created folder and check the pure hit count
		SessionSearch search = new SessionSearch(driver);
		search.basicContentSearch(Input.testData1);
		search.bulkFolderExisting(foldername);
		
		// create production and fill dat field and verify specify controls
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		base.stepInfo("Select default security group");
		page.selectingDefaultSecurityGroup();
		
		page.navigateToProductionPage();
		
		base.stepInfo("Add new production");
		page.addANewProduction(productionname);
		
		base.stepInfo("Filling DAT Section");
		page.fillingDATSection();
		
		base.stepInfo("Filling Native Section");
		page.fillingNativeSection();
		
		base.stepInfo("Filling text section");
		page.fillingTextSection();
		
		base.stepInfo("Navigate to next section");
		page.navigateToNextSection();
		
		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID,beginningBates);
		
		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();
		
		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);
		
		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();
		
		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();
		
		base.stepInfo("Filling Production Location Page");
		page.fillingProductionLocationPage(productionname);
		
		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();
		
		base.stepInfo("Filling Summary And Preview");
		page.fillingSummaryAndPreview();
		
		base.stepInfo("Filling Generate Page");
		page.fillingGeneratePageWithContinueGenerationPopup();
		
		base.stepInfo("Click on navigate back");
		page.navigateBack();
		
		base.stepInfo("Verify export bates button is enabled");
		page.verifyExportBatesButtonIsEnabled();
	}
	
	
	/**
	 * @author Gopinath created on:NA modified by:NA
	 * @TESTCASE_No:RPMXCON-49054 : Verify Add documents option is not getting displayed in Export.
	 * @Description: Verify Add documents option is not getting displayed in Export.
	 * 
	 */
	@Test(groups = { "regression" }, priority = 6)
	public void verifyDocumentSectionOptionNotDisplayedExport() throws Exception {
		base=new BaseClass(driver);
		base=new BaseClass(driver);
		base.stepInfo("####Verify Add documents option is not getting displayed in Export. ####");
 		
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49054 Export-sprint:08");
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;
		String exportname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Utility.randomCharacterAppender(25);
 		String suffixID =Utility.randomCharacterAppender(25);
		
		
		// Pre-requisites
		// create folder and tag
 		
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		
		base.stepInfo("Navigate to security group");
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		
		base.stepInfo("Create Folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		
		base.stepInfo("Create Tag with Classification");
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		
		// testData string contains redacted documents as per Pre-requisites
		base.stepInfo("Basic Content Search");
		sessionSearch.basicContentSearch(testData1);
		
		base.stepInfo("Bulk Folder Existing");
		sessionSearch.bulkFolderExisting(foldername);

		// create export with native which displays message about priviledged and
		// redacted export
		ProductionPage page = new ProductionPage(driver);
		
		base.stepInfo("Selecting Default Security Group");
		page.selectingDefaultSecurityGroup();
		
		base.stepInfo("select Export Set From DropDown");
		page.selectExportSetFromDropDown();
		
		base.stepInfo("Add New Export");
		page.addANewExport(exportname);
		
		base.stepInfo("Filling DAT Section");
 		page.fillingDATSection();
		
		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();
		
		base.stepInfo("Filling Export Numbering And Sorting Page");
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID);
		
		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();
		
		base.stepInfo("Filling Document Selection Page");
		page.fillingDocumentSelectionPage(foldername);
		
		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();
		
		base.stepInfo("Filling Priv Guard Page");
		page.fillingPrivGuardPage();
		
		base.stepInfo("Filling Export Location Page");
		page.fillingExportLocationPage(exportname);
		
		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();
		
		base.stepInfo("Viewing Tool Tip In Summary And Preview");
		page.viewingToolTipInSummaryAndPreview();
		
		base.stepInfo("Click on Next Button");
		page.getNextButton().waitAndClick(10);
		
		base.stepInfo("Filling Generate Page");
		page.fillingExportGeneratePageWithContinueGenerationPopup();
		
		for(int i=0;i<5;i++) {
			page.navigateBack();
		}
		base.stepInfo("Verify user not able to select documents.");
		page.verifyUserNotAbleToSelectDocuments();
		
		//To delete tag and folder 
		base.stepInfo("Navigate To Tags And Folder Page");
		tagsAndFolderPage.navigateToTagsAndFolderPage();
		
		base.stepInfo("Delete Folder With Security Group");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		
		base.stepInfo("Delete Tag With Classification");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
	}
	
	
	
	@AfterMethod(alwaysRun = true)
	public void close() {
		try {
			loginPage.logout();
		} finally {
			loginPage.closeBrowser();
			LoginPage.clearBrowserCache();
		}
	}
	
     @AfterMethod(alwaysRun = true)
	 public void takeScreenShot(ITestResult result) {
 	 if(ITestResult.FAILURE==result.getStatus()){
 		 
 		Utility bc = new Utility(driver);
 		bc.screenShot(result);
 		try{ //if any tc failed and dint logout!
 		loginPage.logout();
 		}catch (Exception e) {
			// TODO: handle exception
		}
 	}
 	 System.out.println("Executed :" + result.getMethod().getMethodName());
 	
     }
    
}

