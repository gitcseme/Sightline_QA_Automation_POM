package sightline.productions;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.DocListPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ManageAssignment;
import pageFactory.ProductionPage;
import pageFactory.ProjectFieldsPage;
import pageFactory.RedactionPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Export_Phase1_Regression {

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
	 * @TestCase id : 49359 - Verify that if PA selects the Export with Production
	 *           and has only Native Files selected in the native components
	 *           section, then Component tab should Complete without any error.
	 * @Description : Verify that if PA selects the Export with Production and has
	 *              only Native Files selected in the native components section,
	 *              then Component tab should Complete without any error.
	 */
	@Test(description = "RPMXCON-49359", alwaysRun = true, groups = { "regression" })
	public void verifyExportWithProductionNativeComponentsSection() throws Exception {
		base = new BaseClass(driver);
		base.stepInfo("Test case Id: RPMXCON-49359 Export Sprint-8");
		utility = new Utility(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		base.stepInfo(
				"#### Verify that if PA selects the Export with Production and has only Native Files selected in the native components section, then Component tab should Complete without any error. ####");
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String tagname1 = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;
		String prefixID = Utility.randomCharacterAppender(25);
		String suffixID = Utility.randomCharacterAppender(25);
		String exportName = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
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
		page.fillingNativeSectionWithTags(tagname, tagname1);

		base.stepInfo("Filling Text Section");
		page.fillingTextSection();

		base.stepInfo("Navigate To Next Section");
		page.navigateToNextSection();

		base.stepInfo("Filling Numbering And Sorting Page");
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

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
		loginPage.logout();

	}

	/**
	 * @author Gopinath created on:NA modified by:NA
	 * @TestCASE_No:RPMXCON-49253 : To verify In Export DAT, TIFFPageCount for each
	 *                            document should be displayed when export is done
	 *                            with any component.
	 * @Description:To verify In Export DAT, TIFFPageCount for each document should
	 *                 be displayed when export is done with any component.
	 * 
	 */
	@Test(description = "RPMXCON-49253", groups = { "regression" })
	public void verifyTIFFPageCountAndTiffSectionExport() throws Exception {
		base = new BaseClass(driver);
		base.stepInfo(
				"#### To verify In Export DAT, TIFFPageCount for each document should be displayed when export is done with any component ####");

		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String tagNameTechnical = Input.tagNameTechnical;
		String testData1 = Input.testData1;
		String exportname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Utility.randomCharacterAppender(25);
		String suffixID = Utility.randomCharacterAppender(25);

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
		page.addDATFieldAtThirdRow(Input.docBasic, Input.docName, Input.documentID);

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
		base.passedStep(
				"Verified that for Native section should be displayed message which will inform the user about the privileged and redacted docs from export");

		// To delete tag and folder
		base.stepInfo("Navigate To Tags And Folder Page");
		tagsAndFolderPage.navigateToTagsAndFolderPage();

		base.stepInfo("Delete Folder With Security Group");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);

		base.stepInfo("Delete Tag With Classification");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA
	 * @TestCASE_No:RPMXCON-49252 : To verify In Export DAT, provide the
	 *                            TIFFPageCount for each document should be zero
	 *                            when only DAT component is selected
	 * @Description: To verify In Export DAT, provide the TIFFPageCount for each
	 *               document should be zero when only DAT component is selected
	 * 
	 */
	@Test(description = "RPMXCON-49252", groups = { "regression" })
	public void verifyTiffPageCountOnDATForExport() throws Exception {
		base = new BaseClass(driver);
		base.stepInfo(
				"#### To verify In Export DAT, provide the TIFFPageCount for each document should be zero when only DAT component is selected ####");

		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;
		String exportname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Utility.randomCharacterAppender(25);
		String suffixID = Utility.randomCharacterAppender(25);

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
		page.addDATFieldAtThirdRow(Input.docBasic, Input.docName, Input.documentID);

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
		base.passedStep(
				"Verified that for Native section should be displayed message which will inform the user about the privileged and redacted docs from export");

		// To delete tag and folder
		base.stepInfo("Navigate To Tags And Folder Page");
		tagsAndFolderPage.navigateToTagsAndFolderPage();

		base.stepInfo("Delete Folder With Security Group");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);

		base.stepInfo("Delete Tag With Classification");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @Author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath
	 * @TestCase id : 49220 -To verify that in Production Export, this production
	 *           will not show up as it was uncommitted.
	 * @Description : Verify that in Production Export, this production will not
	 *              show up as it was uncommitted.
	 */
	 @Test(description ="RPMXCON-49220",alwaysRun = true,groups={"regression"})
	public void verifyProductionNotShownInExportWhenUncommited() throws Exception {
		base = new BaseClass(driver);
		base.stepInfo("Test case Id: RPMXCON-49220 Export Sprint-8");
		utility = new Utility(driver);
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		base.stepInfo(
				"#### Verify that in Production Export, this production will not show up as it was uncommitted. ####");
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;
		String prefixID = Utility.randomCharacterAppender(25);
		String suffixID = Utility.randomCharacterAppender(25);
		String exportName = Input.randomText + Utility.dynamicNameAppender();

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
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

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
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA
	 * @TestCASE_No:RPMXCON-49137 : To verify that when text is for document then it
	 *                            should export the actual text file.
	 * @Description: To verify that when text is for document then it should export
	 *               the actual text file.
	 * 
	 */
	@Test(description = "RPMXCON-49137", groups = { "regression" })
	public void verifyTextSecDocument() throws Exception {
		base = new BaseClass(driver);
		base.stepInfo("#### To verify that when text is for document then it should export the actual text file ####");

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49137 Export-sprint:08");
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String productionname = Input.randomText + Utility.dynamicNameAppender();
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
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);

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
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA
	 * @TestCASE_No:RPMXCON-49054 : Verify Add documents option is not getting
	 *                            displayed in Export.
	 * @Description: Verify Add documents option is not getting displayed in Export.
	 * 
	 */
	@Test(description = "RPMXCON-49054", groups = { "regression" })
	public void verifyDocumentSectionOptionNotDisplayedExport() throws Exception {
		base = new BaseClass(driver);
		base = new BaseClass(driver);
		base.stepInfo("####Verify Add documents option is not getting displayed in Export. ####");

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49054 Export-sprint:08");
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String tagname = Input.randomText + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;
		String exportname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Utility.randomCharacterAppender(25);
		String suffixID = Utility.randomCharacterAppender(25);

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

		for (int i = 0; i < 5; i++) {
			page.navigateBack();
		}
		base.stepInfo("Verify user not able to select documents.");
		page.verifyUserNotAbleToSelectDocuments();

		// To delete tag and folder
		base.stepInfo("Navigate To Tags And Folder Page");
		tagsAndFolderPage.navigateToTagsAndFolderPage();

		base.stepInfo("Delete Folder With Security Group");
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);

		base.stepInfo("Delete Tag With Classification");
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA
	 * @TestCASE_No:RPMXCON-50630 : Verify that TIFF should Export with Burned
	 *                            Redaction if Only Burn Redaction is enabled.
	 * @Description: Verify that TIFF should Export with Burned Redaction if Only
	 *               Burn Redaction is enabled.
	 * 
	 */
	@Test(description = "RPMXCON-50630", groups = { "regression" })
	public void verifyTIFFExportWithBurnRedaction() throws Exception {
		base = new BaseClass(driver);
		base.stepInfo("Test case Id: RPMXCON-50630");
		base.stepInfo(
				"#### Verify that TIFF should Export with Burned Redaction if Only Burn Redaction is enabled ####");

		String tagName = Input.randomText + Utility.dynamicNameAppender();
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String redactiontag1 = Input.randomText + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;
		String exportname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Utility.randomCharacterAppender(25);
		String suffixID = Utility.randomCharacterAppender(25);
		loginPage = new LoginPage(driver);
		loginPage.logout();

		base.stepInfo("Login with RMU");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Pre-requisites
		// create folder and tag
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Navigate to security group");
		tagsAndFolderPage.navigateToTagsAndFolderPage();

		base.stepInfo("Create Folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassificationInRMU(tagName, Input.tagNamePrev);

		RedactionPage redactionpage = new RedactionPage(driver);

		base.stepInfo("Creating redaction tag");
		redactionpage.manageRedactionTagsPage(redactiontag1);

		SessionSearch sessionSearch = new SessionSearch(driver);

		// testData string contains redacted documents as per Pre-requisites
		base.stepInfo("Basic Content Search");
		sessionSearch.basicContentSearch(testData1);

		base.stepInfo("Bulk Folder Existing");
		sessionSearch.bulkFolderExisting(foldername);

		base.stepInfo("View searched for audio docs in Doc view");
		sessionSearch.ViewInDocViewWithoutPureHit();

		base.stepInfo("Click on reduction button ");
		docViewMetaDataPage.clickOnRedactAndRectangle();

		base.stepInfo("Set rectangle reduct in doc");
		docViewMetaDataPage.redactbyrectangle(10, 15, redactiontag1);

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

		base.stepInfo("Filling TIFF Section with Burn Redaction");
		page.fillingPDFSection(tagName);

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

		base.stepInfo("Delete added redaction tag");
		driver.waitForPageToBeReady();
		redactionpage.DeleteRedaction(redactiontag1);

		// To delete tag and folder
		base.stepInfo("Navigate To Tags And Folder Page");
		base.stepInfo("Delete Folder With Security Group");
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA
	 * @TestCASE_No:RPMXCON-50330 : Verify that TIFF should Export with Burned
	 *                            Redaction if Only Burn Redaction is enabled.
	 * @Description: Verify that TIFF should Export with Burned Redaction if Only
	 *               Burn Redaction is enabled.
	 * 
	 */
	@Test(description = "RPMXCON-50330", groups = { "regression" })
	public void verifyTIFFExportWithBurnRedactionEnabled() throws Exception {
		base = new BaseClass(driver);
		base.stepInfo("Test case Id: RPMXCON-50330");
		base.stepInfo(
				"#### Verify that TIFF should Export with Burned Redaction if Only Burn Redaction is enabled ####");

		String tagName = Input.randomText + Utility.dynamicNameAppender();
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String redactiontag1 = Input.randomText + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;
		String exportname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Utility.randomCharacterAppender(25);
		String suffixID = Utility.randomCharacterAppender(25);
		loginPage = new LoginPage(driver);
		loginPage.logout();

		base.stepInfo("Login with RMU");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Pre-requisites
		// create folder and tag
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Navigate to security group");
		tagsAndFolderPage.navigateToTagsAndFolderPage();

		base.stepInfo("Create Folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassificationInRMU(tagName, Input.tagNamePrev);
		RedactionPage redactionpage = new RedactionPage(driver);

		base.stepInfo("Creating redaction tag");
		redactionpage.manageRedactionTagsPage(redactiontag1);

		SessionSearch sessionSearch = new SessionSearch(driver);

		// testData string contains redacted documents as per Pre-requisites
		base.stepInfo("Basic Content Search");
		sessionSearch.basicContentSearch(testData1);

		base.stepInfo("Bulk Folder Existing");
		sessionSearch.bulkFolderExisting(foldername);

		base.stepInfo("View searched for audio docs in Doc view");
		sessionSearch.ViewInDocViewWithoutPureHit();

		base.stepInfo("Click on reduction button ");
		docViewMetaDataPage.clickOnRedactAndRectangle();

		base.stepInfo("Set rectangle reduct in doc");
		docViewMetaDataPage.redactbyrectangle(10, 15, redactiontag1);

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

		base.stepInfo("Filling TIFF Section with Burn Redaction");
		page.fillingPDFSection(tagName);
//		page.fillingTIFFWithBurnRedaction(null, "Tag", Input.defaultRedactionTag);
//		page.getTIFF_MultiPage_RadioButton().ScrollTo();
//		base.waitForElement(page.getTIFF_MultiPage_RadioButton());
//		page.getTIFF_MultiPage_RadioButton().waitAndClick(10);

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

		// To delete tag and folder
		base.stepInfo("Navigate To Tags And Folder Page");
		tagsAndFolderPage.navigateToTagsAndFolderPage();

		base.stepInfo("Delete Folder With Security Group");
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);

		base.stepInfo("Delete added redaction tag");
		redactionpage.DeleteRedaction(redactiontag1);
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA
	 * @TestCASE_No:RPMXCON-49248 : To Verify in Export TIFFPageCount for
	 *                            placeholders.
	 * @Description: To Verify in Export TIFFPageCount for placeholders.
	 * 
	 */
	@Test(description = "RPMXCON-49248", groups = { "regression" })
	public void verifyTIFFPageCountForPlaceholders() throws Exception {
		base = new BaseClass(driver);
		base.stepInfo("Test case Id: RPMXCON-49248");
		base.stepInfo("#### To Verify in Export TIFFPageCount for placeholders. ####");

		String tagName = Input.randomText + Utility.dynamicNameAppender();
		String foldername = Input.randomText + Utility.dynamicNameAppender();
		String redactiontag1 = Input.randomText + Utility.dynamicNameAppender();
		String testData1 = Input.testData1;
		String exportname = Input.randomText + Utility.dynamicNameAppender();
		String prefixID = Utility.randomCharacterAppender(25);
		String suffixID = Utility.randomCharacterAppender(25);
		loginPage = new LoginPage(driver);
		loginPage.logout();

		base.stepInfo("Login with RMU");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		// Pre-requisites
		// create folder and tag
		docViewMetaDataPage = new DocViewMetaDataPage(driver);
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);

		base.stepInfo("Navigate to security group");
		tagsAndFolderPage.navigateToTagsAndFolderPage();

		tagsAndFolderPage.CreateTagwithClassification(tagName, Input.tagNamePrev);
		base.stepInfo("Create Folder");
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		RedactionPage redactionpage = new RedactionPage(driver);

		base.stepInfo("Creating redaction tag");
		redactionpage.manageRedactionTagsPage(redactiontag1);

		SessionSearch sessionSearch = new SessionSearch(driver);

		// testData string contains redacted documents as per Pre-requisites
		base.stepInfo("Basic Content Search");
		sessionSearch.basicContentSearch(testData1);

		base.stepInfo("Bulk Folder Existing");
		sessionSearch.bulkFolderExisting(foldername);

		base.stepInfo("View searched for audio docs in Doc view");
		sessionSearch.ViewInDocViewWithoutPureHit();

		base.stepInfo("Click on reduction button ");
		docViewMetaDataPage.clickOnRedactAndRectangle();

		base.stepInfo("Set rectangle reduct in doc");
		docViewMetaDataPage.redactbyrectangle(10, 15, redactiontag1);

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
		page.addDATFieldAtThirdRow(Input.docBasic, Input.docName, Input.documentID);

		base.stepInfo("Filling TIFF Section with Burn Redaction");
		page.fillingPDFSection(tagName);
//		page.fillingTIFFSection(tagName);
//		page.getTIFF_MultiPage_RadioButton().ScrollTo();
//		base.waitForElement(page.getTIFF_MultiPage_RadioButton());
//		page.getTIFF_MultiPage_RadioButton().waitAndClick(10);

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

		// To delete tag and folder
		base.stepInfo("Navigate To Tags And Folder Page");
		tagsAndFolderPage.navigateToTagsAndFolderPage();

		base.stepInfo("Delete Folder With Security Group");
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(foldername);

		base.stepInfo("Delete added redaction tag");
		redactionpage.DeleteRedaction(redactiontag1);
		loginPage.logout();
	}

	/**
	 * @author Gopinath created on:NA modified by:NA
	 * @TestCASE_No:RPMXCON-49131 : To verify that in Production-Export-Slip Sheet,
	 *                            Metadata Field should be sorted by alpha
	 *                            ascending.
	 * @Description: Verify that in Production-Export-Slip Sheet, Metadata Field
	 *               should be sorted by alpha ascending
	 * 
	 */
	@Test(description = "RPMXCON-49131", groups = { "regression" })
	public void verifyProductionExportSlipSheetMetaDataSorted() throws Exception {
		base = new BaseClass(driver);

		base.stepInfo(
				"#### To verify that in Production-Export-Slip Sheet, Metadata Field should be sorted by alpha ascending ####");

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49131 Export-sprint:08");
		String exportname = Input.randomText + Utility.dynamicNameAppender();

		ProductionPage page = new ProductionPage(driver);

		base.stepInfo("Selecting Default Security Group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("select Export Set From DropDown");
		page.selectExportSetFromDropDown();

		base.stepInfo("Add New Export");
		page.addANewExport(exportname);

		base.stepInfo("Verify MetaData List Slip Sheet In Ascending Order tiff section");
		page.verifyMetaDataListSlipSheetInAscendingOrderTiffSec();

		base.stepInfo("Refresh page");
		driver.Navigate().refresh();

		base.stepInfo("Verify MetaData List Slip Sheet In Ascending Order PDF section");
		page.verifyMetaDataListSlipSheetInAscendingOrderPDFSec();

	}

	/**
	 * @author Gopinath created on:NA modified by:NA
	 * @TestCASE_No:RPMXCON-49127 : To verify that in Production-Export-File Type
	 *                            Group Placeholder section, Metadata Field drop
	 *                            down should be sorted by alpha ascending.
	 * @Description: Verify that in Production-Export-File Type Group Placeholder
	 *               section, Metadata Field drop down should be sorted by alpha
	 *               ascending.
	 * 
	 */
	@Test(description = "RPMXCON-49127", groups = { "regression" })
	public void verifyProductionExportNativeMetaDataDropdownSorted() throws Exception {
		base = new BaseClass(driver);
		base.stepInfo(
				"#### Verify that in Production-Export-File Type Group Placeholder section, Metadata Field drop down should be sorted by alpha ascending. ####");

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49127 Export-sprint:08");
		String exportname = Input.randomText + Utility.dynamicNameAppender();

		ProductionPage page = new ProductionPage(driver);

		base.stepInfo("Selecting Default Security Group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("select Export Set From DropDown");
		page.selectExportSetFromDropDown();

		base.stepInfo("Add New Export");
		page.addANewExport(exportname);

		base.stepInfo("Refresh page");
		driver.Navigate().refresh();

		base.stepInfo("Verify meta data list in drop down native will be in ascending order on pdf section.");
		page.verifyMetaDataDropdownNativeAscendingOrderPdfSec();
		base.stepInfo("Verify meta data list in drop down native will be in ascending order on tiff section.");
		page.verifyMetaDataDropdownNativeAscendingOrderTiffSec();

	}

	/**
	 * @author Gopinath created on:NA modified by:NA
	 * @TestCASE_No:RPMXCON-49125 : Verify that in Production-Export-Exception Docs
	 *                            Placeholder section, Metadata Field drop down
	 *                            should be sorted by alpha ascending.
	 * @Description: Verify that in Production-Export-Exception Docs Placeholder
	 *               section, Metadata Field drop down should be sorted by alpha
	 *               ascending.
	 * 
	 */
	@Test(description = "RPMXCON-49125", groups = { "regression" })
	public void verifyProductionExportExceptionMetaDataDropdownSorted() throws Exception {
		base = new BaseClass(driver);
		base.stepInfo(
				"#### Verify that in Production-Export-Exception Docs Placeholder section, Metadata Field drop down should be sorted by alpha ascending. ####");

		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49125 Export-sprint:08");
		String exportname = Input.randomText + Utility.dynamicNameAppender();

		ProductionPage page = new ProductionPage(driver);

		base.stepInfo("Selecting Default Security Group");
		page.selectingDefaultSecurityGroup();

		base.stepInfo("select Export Set From DropDown");
		page.selectExportSetFromDropDown();

		base.stepInfo("Add New Export");
		page.addANewExport(exportname);

		base.stepInfo("Verify meta data list in drop down native will be in ascending order on tiff section.");
		page.verifyMetaDataDropdownExceptionAscendingOrderTiffSec();

		base.stepInfo("Refresh page");
		driver.Navigate().refresh();

		base.stepInfo("Verify meta data list in drop down native will be in ascending order on pdf section.");
		page.verifyMetaDataDropdownExceptionAscendingOrderPDFSec();

	}

	/**
	 * Author : Baskar date: NA Modified date:19/01/2021 Modified by: Baskar
	 * Description : Verify that if PA selects the Export with Production and has
	 * Native Files and Tags selected in the native components section, then
	 * Component tab should Complete without any error.
	 */
	@Test(description = "RPMXCON-49360", enabled = true, groups = { "regression" })
	public void verifyProductionCreationDateMarkComp() throws Exception {
		UtilityLog.info(Input.prodPath);
		base = new BaseClass(driver);
		base.stepInfo("Test case Id: RPMXCON-49360");
		base.stepInfo("Verify that if PA selects the Export with Production and has Native Files and Tags "
				+ "selected in the native components section, then Component tab should Complete without any error.");

		String AAfolder = "AFolderProd" + Utility.dynamicNameAppender();
		String AAAfolder = "AFolderProd" + Utility.dynamicNameAppender();
		// String tagNameTechnical = Input.tagNameTechnical;
		String newExport = "Ex" + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateTag(AAfolder, Input.securityGroup);
		tagsAndFolderPage.CreateTag(AAAfolder, Input.securityGroup);
		// tagsAndFolderPage.createNewTagwithClassification(tagname,Input.tagNamePrev);

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		String text = page.getProdExport_ProductionSets().getText();
		if (text.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(newExport);
		}
		page.addANewExport(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectNativeTag(AAfolder, AAAfolder);
		driver.scrollPageToTop();
		page.getComponentsMarkComplete().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.VerifySuccessMessage("Mark Complete successful");
		base.passedStep("Componenet tab completed without any error");
		loginPage.logout();
	}

	/**
	 * @author Aathith.Senthilkumar RPMXCON-48068
	 * @Description To Verify placeholders of the docs of the selected file types
	 *              are produced in Export.
	 * 
	 */
	@Test(description = "RPMXCON-48068", enabled = true, groups = { "regression" })
	public void verifyPlaceHolderOfExportDocuments() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48068 -Production");
		base.stepInfo("To Verify placeholders of the docs of the selected file types are produced in Export.");

		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String newExport = "Ex" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		// Pre-requisites
		// create tag and folder
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.CreateTag(tagname, Input.securityGroup);

		// search for folder
		SessionSearch sessionSearch = new SessionSearch(driver);
		int purehit = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		// Verify
		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		String text = page.getProdExport_ProductionSets().getText();
		if (text.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(newExport);
		}
		page.addANewExport(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionwithNativelyPlaceholder(tagname);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommitandDownload();
		page.getCopyPath().waitAndClick(10);

		String actualCopedText = page.getCopiedTextFromClipBoard();
		String parentTab = page.openNewTab(actualCopedText);
		page.goToImageFiles();
		page.verifyTiffFile(purehit, prefixID, suffixID, subBates, tagname);
//		page.getFirstImageFile(prefixID + suffixID, subBates).waitAndClick(10);
//		driver.waitForPageToBeReady();
//
//		File imageFile = new File(Input.fileDownloadLocation + prefixID + suffixID + ".000" + subBates + ".tiff");
//		page.OCR_Verification_In_Generated_Tiff_tess4j(imageFile, tagname);

		driver.close();
		driver.getWebDriver().switchTo().window(parentTab);
		base.passedStep("Verified placeholders of the docs of the selected file types are produced in Export.");

		// delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Brundha RPMXCON-47500
	 * @Description Export: Verify that when production components step is completed
	 *              then DAT component should retain the 'TIFFPageCount'
	 * 
	 */
	@Test(description = "RPMXCON-47500", enabled = true, groups = { "regression" })
	public void verifyDatSectionMappingField() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47500 -Export component");
		base.stepInfo(
				"Export: Verify that when production components step is completed then DAT component should retain the 'TIFFPageCount'");

		String newExport = "Ex" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();

		page.selectingDefaultSecurityGroup();
		String text = page.getProdExport_ProductionSets().getText();
		if (text.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(newExport);
		}
		page.addANewExport(productionname);
		page.verifyingDatFieldClassification();
		loginPage.logout();

	}

	/**
	 * @author Brundha RPMXCON-47983
	 * @Description To Verify Export Status after it complete the Export Generation
	 *              Task
	 * 
	 */
	@Test(description = "RPMXCON-47983", enabled = true, groups = { "regression" })
	public void verifyingCompletedStatusInTileView() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47983 -Export component");
		base.stepInfo("To Verify Export Status after it complete the Export Generation Task");

		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String newExport = "Ex" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		String text = page.getProdExport_ProductionSets().getText();
		if (text.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(newExport);
		}
		page.addANewExport(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommitandDownload();

		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		page.selectExportSetFromDropDown();
		page.getProductionFromHomePage(productionname).isElementAvailable(10);
		String productionFromHomePage = page.getProductionFromHomePage(productionname).getText();
		if (productionFromHomePage.equals("Completed")) {
			base.passedStep("Completed status is displayed as expected");
		} else {
			base.failedStep("Status is not displayed as expected");
		}
		loginPage.logout();
	}

	/**
	 * @author Brundha RPMXCON-47471
	 * @Description To Verify PDF creation for export
	 * 
	 */
	@Test(description = "RPMXCON-47471", enabled = true, groups = { "regression" })
	public void verifyGenerationOfExportWithPdfSection() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47471 -Export component");
		base.stepInfo("To Verify PDF creation for export");

		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String newExport = "Ex" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		String text = page.getProdExport_ProductionSets().getText();
		if (text.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(newExport);
		}
		page.addANewExport(productionname);
		page.fillingDATSection();
		page.fillingPDFSection(tagname);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommitandDownload();
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:21/06/2022 RPMXCON-48193
	 * @Description To Verify Export Generation for MP3 files using document level
	 *              numbering.
	 *
	 */
	@Test(description = "RPMXCON-48193", enabled = true, groups = { "regression" })
	public void verifyGenerationOfExportForMp3File() throws Exception {
		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48193 -Export component");
		base.stepInfo("To Verify Export Generation for MP3 files using document level numbering.");
		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String newExport = "Ex" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		String text = page.getProdExport_ProductionSets().getText();
		if (text.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(newExport);
		}
		page.addANewExport(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommitandDownload();
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani Modify Date:21/06/2022 RPMXCON-47499
	 * @Description Export: Verify that TIFFPageCount is no longer presented under
	 *              "Doc Metadata"
	 *
	 */
	@Test(description = "RPMXCON-47499", enabled = true, groups = { "regression" })
	public void VerifydatSectionFieldMapping() throws Exception {
		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47499 -Export component");
		base.stepInfo("Export: Verify that TIFFPageCount is no longer presented under 'Doc Metadata'");
		String newExport = "Ex" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		String text = page.getProdExport_ProductionSets().getText();
		if (text.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(newExport);
		}
		page.addANewExport(productionname);
		page.verifyingDatMappingField();
		loginPage.logout();
	}

	/**
	 * @author Brundha RPMXCON-47933
	 * @Description To verify Preview for Export *
	 */
	@Test(description = "RPMXCON-47933", enabled = true, groups = { "regression" })
	public void verifyGenerationOfPdfFile() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47933 -Export component");
		base.stepInfo("To verify Preview for Export");

		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String newExport = "Ex" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		String text = page.getProdExport_ProductionSets().getText();
		if (text.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(newExport);
		}
		page.addANewExport(productionname);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname, Input.tagNameTechnical);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(productionname);
		page.navigateToNextSection();
		driver.waitForPageToBeReady();
		page.viewingPreviewInSummaryTab();
		page.verifyingPdfgeneration(Input.searchString4);
		loginPage.logout();
	}

	/**
	 * @author Brundha RPMXCON-47473
	 * @Description To Verify TIFF creation for export
	 * 
	 */
	@Test(description = "RPMXCON-47473", enabled = true, groups = { "regression" })
	public void verifyGenerationOfExportWithTiffSection() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47473 -Export component");
		base.stepInfo("To Verify TIFF creation for export");

		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String newExport = "Ex" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		String text = page.getProdExport_ProductionSets().getText();
		if (text.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(newExport);
		}
		page.addANewExport(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionwithNativelyPlaceholder(tagname);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommitandDownload();
		loginPage.logout();
	}

	/**
	 * @author Brundha RPMXCON-47478
	 * @Description To Verify Export in production, basic Export without Production.
	 * 
	 */
	@Test(description = "RPMXCON-47478", enabled = true, groups = { "regression" })
	public void verifyingGenerationOfExport() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47478 -Export component");
		base.stepInfo("To Verify Export in production, basic Export without Production.");

		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String newExport = "Ex" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		String text = page.getProdExport_ProductionSets().getText();
		if (text.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(newExport);
		}
		page.addANewExport(productionname);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommitandDownload();
		loginPage.logout();
	}

	/**
	 * @author Brundha RPMXCON-47498
	 * @DescriptionExport: Verify that the production field PageCount is renamed to
	 *                     TIFFPageCount
	 * 
	 */
	@Test(description = "RPMXCON-47498", enabled = true, groups = { "regression" })
	public void verifyingTiffPageCountOptionInDatSection() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47498 -Export component");
		base.stepInfo("Export: Verify that the production field PageCount is renamed to TIFFPageCount");

		String newExport = "Ex" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();

		page.selectingDefaultSecurityGroup();
		String text = page.getProdExport_ProductionSets().getText();
		if (text.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(newExport);
		}
		page.addANewExport(productionname);
		page.verifyingDatFieldClassification();
		if (!page.getSourceFiledInDatSection().isElementAvailable(2)) {
			base.passedStep("PageCount is not displayed as expected");
		} else {
			base.failedStep("pagecount is displayed and not renamed ");
		}

		loginPage.logout();

	}

	/**
	 * @author Brundha RPMXCON-49383
	 * @Description Export: Verify 'Advanced Options' should be removed from the DAT
	 *              component section in Production-Export
	 * 
	 */
	@Test(description = "RPMXCON-49383", enabled = true, groups = { "regression" })
	public void verifyingAdvancedOptionInDatSection() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49383 -Export component");
		base.stepInfo(
				"Verify 'Advanced Options' should be removed from the DAT component section in Production-Export");

		String newExport = "Ex" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();

		page.selectingDefaultSecurityGroup();
		String text = page.getProdExport_ProductionSets().getText();
		if (text.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(newExport);
		}
		page.addANewExport(productionname);
		page.getDATTab().waitAndClick(5);
		if (!page.getAdvancedToggle().isDisplayed()) {
			base.passedStep("Advanced option is removed  as expected");
		} else {
			base.failedStep("Advanced option is not removed");
		}

		loginPage.logout();

	}

	/**
	 * @author Brundha RPMXCON-49386
	 * @DescriptionVerify the Advanced section in Tiff /PDF component
	 * 
	 */
	@Test(description = "RPMXCON-49386", enabled = true, groups = { "regression" })
	public void verifyingAdvancedOptionInTiffSection() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-49386 -Export component");
		base.stepInfo("Verify the Advanced section in Tiff /PDF component.");

		String newExport = "Ex" + Utility.dynamicNameAppender();

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		String text = page.getProdExport_ProductionSets().getText();
		if (text.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(newExport);
		}
		page.addANewExport(productionname);
		page.verifyingAdvancedOptionInTiffSection();
		loginPage.logout();
	}

	/**
	 * @author Brundha RPMXCON-48187
	 * @Description To Verify On Export Basic info page User should be able to
	 *              select Custom Template (if available under custom template) and
	 *              move ahead with Export
	 * 
	 */
	@Test(description = "RPMXCON-48187", enabled = true, groups = { "regression" })
	public void verifyTemplateValueInProduction() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48187 -Export component");
		base.stepInfo(
				"To Verify On Export Basic info page User should be able to select Custom Template (if available under custom template) and move ahead with Export");

		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String newExport = "Ex" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String TempName = "Temp" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		String text = page.getProdExport_ProductionSets().getText();
		if (text.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(newExport);
		}
		page.addANewExport(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommitandDownload();

		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		String productionname1 = "p" + Utility.dynamicNameAppender();
		page.selectExportSetFromDropDown();
		page.savedTemplateAndNewProdcution(productionname, TempName);

		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		page.baseInfoLoadTemplate(productionname1, TempName);
		page.getCheckBoxCheckedVerification(page.chkIsDATSelected());
		page.getCheckBoxCheckedVerification(page.getNativeCheckBox());
		driver.scrollPageToTop();
		page.getMarkCompleteLink().waitAndClick(10);
		base = new BaseClass(driver);
		base.VerifySuccessMessage("Mark Complete successful");
		loginPage.logout();
	}

	/**
	 * @author Brundha RPMXCON-48067
	 * @Description To Verify selection of one or more tags without selecting any
	 *              file types for placeholdering a set of documents.(For Export).
	 * 
	 */
	@Test(description = "RPMXCON-48067", enabled = true, groups = { "regression" })
	public void verifyingPlaceholderTextInGeneratedFile() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48067 -Export Component");
		base.stepInfo(
				"To Verify selection of one or more tags without selecting any file types for placeholdering a set of documents.(For Export).");

		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagname1 = "Tag" + Utility.dynamicNameAppender();
		String tagname2 = "Tag" + Utility.dynamicNameAppender();
		String newExport = "Ex" + Utility.dynamicNameAppender();
		String prefixID = "p" + Utility.dynamicNameAppender();
		String suffixID = "S" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname1, "Select Tag Classification");
		tagsAndFolderPage.createNewTagwithClassification(tagname2, "Select Tag Classification");

		SessionSearch sessionSearch = new SessionSearch(driver);
		int purehit = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname1);

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		String text = page.getProdExport_ProductionSets().getText();
		if (text.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(newExport);
		}
		page.addANewExport(productionname);
		page.fillingDATSection();
		page.selectGenerateOption(false);
		page.nativePlaceholderWithTwoTags(false, tagname1, tagname2);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommitandDownload();
		page.getCopyPath().waitAndClick(10);

		String actualCopedText = page.getCopiedTextFromClipBoard();
		String parentTab = page.openNewTab(actualCopedText);
		page.goToImageFiles();
//		driver.waitForPageToBeReady();
		page.verifyTiffFile(purehit, prefixID, suffixID, subBates, Input.searchString4);
//		for (int i = 2; i < purehit; i++) {
//			page.getFirstImageFile(prefixID + "(" + i + ")" + suffixID, subBates).waitAndClick(10);
//		}
//
//		driver.waitForPageToBeReady();
//		for (int i = 2; i < purehit; i++) {
//			File imageFile = new File(
//					Input.fileDownloadLocation + prefixID + "(" + i + ")" + suffixID + ".000" + subBates + ".tiff");
//			page.OCR_Verification_In_Generated_Tiff_tess4j(imageFile, Input.searchString4);
//		}
		driver.close();
		driver.getWebDriver().switchTo().window(parentTab);

		// delete tags and folders
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname1, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Brundha RPMXCON-47977
	 * @Description To Verify Count displayed for "Documents with Multiple Branding
	 *              Tags" on Production-Export-Generate Page.
	 * 
	 */
	@Test(description = "RPMXCON-47977", enabled = true, groups = { "regression" })
	public void verifyDocumentCountInMultipleBranding() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47977 -Export component");
		base.stepInfo(
				"To Verify Count displayed for 'Documents with Multiple Branding Tags' on Production-Export-Generate Page.");

		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String tagname1 = "Tag" + Utility.dynamicNameAppender();
		String newExport = "Ex" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.createNewTagwithClassification(tagname1, "Technical Issue");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearchForTwoItems(Input.testData1, Input.telecaSearchString);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.ViewInDocList();

		DocListPage doc = new DocListPage(driver);
		doc.documentSelection(3);
		doc.bulkTagExistingFromDoclist(tagname);
		doc.documentSelection(6);
		doc.documentSelection(2);
		doc.bulkTagExistingFromDoclist(tagname1);

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		String text = page.getProdExport_ProductionSets().getText();
		if (text.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(newExport);
		}
		page.addANewExport(productionname);
		page.fillingDATSection();
		page.selectGenerateOption(false);
		page.selectMultiBrandingTags(tagname, tagname1);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutWait();
		page.verifyingMultipleBrandingCount();
		loginPage.logout();
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
			loginPage.logoutWithoutAssert();
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
