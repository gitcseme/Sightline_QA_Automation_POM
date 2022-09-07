package testScriptsRegressionSprint20;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
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
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Production_Regression20 {
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
	 * @author Brundha TESTCASE No:RPMXCON-47721 Date:8/23/2022
	 * @Description:To Verify ProjectAdmin will be able to flag a production as
	 *                 ‘Locked’ after it has been generated.
	 */
	@Test(description = "RPMXCON-47721", enabled = true, groups = { "regression" })

	public void validatingLockOptionInProductionPage() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-47721-from Production");
		base.stepInfo(
				"To Verify ProjectAdmin will be able to flag a production as ‘Locked’ after it has been generated.");
		String foldername = "folder" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		page.navigatingToProductionHomePage();
		page.selectingOptionInProduction(productionname);
		page.verfyingBellyBandMsgAndLockProduction();

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Brundha.T No:RPMXCON-48303 Date:8/23/2022
	 * @Description:To verify the value of EndingBates on Production DAT
	 **/

	@Test(description = "RPMXCON-48303", enabled = true, groups = { "regression" })
	public void verifyingDATFileWithEndingBates() throws Exception {

		base.stepInfo("Test case Id:RPMXCON-48303- Production Component");
		base.stepInfo("To verify the value of EndingBates on Production DAT");

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		foldername = "Folder" + Utility.dynamicNameAppender();
		String prefixID = "P" + Utility.dynamicNameAppender();
		String suffixID = "S" + Utility.dynamicNameAppender();
		String prefixID1 = "P" + Utility.dynamicNameAppender();
		String suffixID1 = "S" + Utility.dynamicNameAppender();
		String BatesNumber = "B" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProductionAndSave(productionname);
		page.fillingDATSection();
		page.addDATFieldAtSecondRow(Input.bates, "EndingBates", BatesNumber);
		page.selectGenerateOption(false);
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
		page.extractFile();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		String name = page.getProduction().getText().trim();
		driver.waitForPageToBeReady();
		File DatFile = new File(home + "/Downloads/VOL0001/Load Files/" + name + "_DAT.dat");
		page.isdatFileExist();
		int EndingBates = Integer.valueOf(beginningBates) + 3;
		System.out.println(EndingBates);
		String bates = prefixID + EndingBates + suffixID + "þ";
		page.verifyingGeneratedDATFile(DatFile, 1, 1, bates);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		page = new ProductionPage(driver);
		String beginningBates1 = page.getRandomNumber(2);
		String productionname1 = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname1);
		page.fillingDATSection();
		page.addDATFieldAtSecondRow(Input.bates, "EndingBates", BatesNumber);
		page.selectGenerateOption(false);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID1, suffixID1, beginningBates1);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname1);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();
		driver.waitForPageToBeReady();
		String name1 = page.getProduction().getText().trim();
		int EndingBates1 = Integer.valueOf(beginningBates1) + 3;
		File DatFile1 = new File(home + "/Downloads/VOL0001/Load Files/" + name1 + "_DAT.dat");
		page.isdatFileExist();
		page.verifyingGeneratedDATFile(DatFile1, 1, 1, prefixID1 + EndingBates1 + suffixID1 + "þ");

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-48602 Date:8/23/2022
	 * @Description To verify that once export is completed notification should be
	 *              displayed to the user
	 * 
	 */
	@Test(description = "RPMXCON-48602", enabled = true, groups = { "regression" })
	public void verifyNotificationOnExportInGenerateTab() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		BaseClass base = new BaseClass(driver);
		base.stepInfo("RPMXCON-48602 - from Production Component");
		base.stepInfo("To verify that once export is completed notification should be displayed to the user");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = "p" + Utility.dynamicNameAppender();
		String suffixID ="s" + Utility.dynamicNameAppender();
		String SuccessMsg = "Export bates range has been added to background services. You will get notification once completed";

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.selectGenerateOption(false);
	    page.fillingTextSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.getbtnProductionGenerate().waitAndClick(10);
		page.verifyProductionStatusInGenPage("Reserving Bates Range Complete");
		base.ValidateElement_Presence(page.getExportBatesButton(), "ExportBates Button");
		base.waitTillElemetToBeClickable(page.getExportBatesButton());
		page.getExportBatesButton().waitAndClick(2);
		base.VerifySuccessMessage(SuccessMsg);
		base.waitTime(2);
		base.ValidateElement_Presence(page.getNotificationLink(), "NotificationIcon");
		page.getNotificationLink().Click();
		page.verifyExportedCSVFile();

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Brundha.T No:RPMXCON-48981 Date:8/23/2022
	 * @Description:To Verify that in productions, the Bates Number field supports
	 *                 Bates Numbers as large as 1,000,000,000
	 **/

	@Test(description = "RPMXCON-48981", enabled = true, groups = { "regression" })
	public void verifyingBatesNumberInGeneratedProduction() throws Exception {

		base.stepInfo("Test case Id:RPMXCON-48981- Production Component");
		base.stepInfo(
				"To Verify that in productions, the Bates Number field supports Bates Numbers as large as 1,000,000,000");

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		foldername = "Folder" + Utility.dynamicNameAppender();
		tagname = "Tag" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String prefixID = page.getRandomNumber(10);
		String suffixID = page.getRandomNumber(10);

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		int PureHit = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int FirstFile = Integer.valueOf(beginningBates);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
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
		page.extractFile();
		driver.waitForPageToBeReady();
		page.isdatFileExist();
		int Lastile = FirstFile + PureHit;
		page.isImageFileExist(FirstFile, Lastile, prefixID, suffixID);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-48327 Date:8/23/2022
	 * @Description To verify that the selected metadata is not displayed in DAT if
	 *              the document has at least one of the selected PRIV tags in PRIV
	 *              placeholdering for TIFF
	 * 
	 */
	@Test(description = "RPMXCON-48327", enabled = true, groups = { "regression" })
	public void verifyingTextInGeneratedDat() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-48327 -Production component");
		base.stepInfo(
				"To verify that the selected metadata is not displayed in DAT if the document has at least one of the selected PRIV tags in PRIV placeholdering for TIFF");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		int PureHit = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.addDATFieldAtSecondRow(Input.productionText, Input.tiffPageCountNam, Input.tiffPageCountText);
		driver.waitForPageToBeReady();
		page.getPrivledgedDATCheckBox(1).waitAndClick(2);
		page.fillingTIFFSectionPrivDocs(tagname, Input.searchString4);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
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
		String name = page.getProduction().getText().trim();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		driver.waitForPageToBeReady();
		File DatFile1 = new File(home + "/Downloads/VOL0001/Load Files/" + name + "_DAT.dat");
		page.isdatFileExist();
		String Empty = "þ";
		for (int i = 1; i < PureHit; i++) {
			page.verifyingGeneratedDATFile(DatFile1, i, 1, Empty);
		}
		driver.waitForPageToBeReady();
		File TiffFile = new File(
				home + "/Downloads/VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tiff");
		page.isfileisExists(TiffFile);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-47882 Date:8/23/2022
	 * @Description To Verify Basic info page User should be able to select Custom
	 *              Template (if available under custom template) and move ahead
	 *              with Production
	 * 
	 */
	@Test(description = "RPMXCON-47882", enabled = true, groups = { "regression" })
	public void verifyingLoadTemplateInProduction() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		BaseClass base = new BaseClass(driver);
		base.stepInfo("RPMXCON-47882 -Production component");
		base.stepInfo(
				"To Verify Basic info page User should be able to select Custom Template (if available under custom template) and move ahead with Production");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String productionname1 = "p" + Utility.dynamicNameAppender();
		String Templatename = "T" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionPrivDocs(tagname, Input.searchString4);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();

		page.navigatingToProductionHomePage();
		page.savedTemplateAndNewProdcution(productionname, Templatename);
		page.baseInfoLoadTemplate(productionname1, Templatename);
		driver.scrollPageToTop();
		page.getMarkCompleteLink().waitAndClick(10);
		base.VerifySuccessMessage("Mark Complete successful");
		driver.waitForPageToBeReady();
		page.getNextButton().waitAndClick(2);
		page.visibleCheck("Numbering and Sorting");
		loginPage.logout();
	}
	
	/**
	 * @author Brundha.T No:RPMXCON-49124 Date:8/25/2022
	 * @Description:To verify that in Production-TechIssue Docs Placeholder section,
	 *                 Metadata Field drop down should be sorted by alpha ascending
	 **/

	@Test(description = "RPMXCON-49124", enabled = true, groups = { "regression" })
	public void verifyingAscendingOrderInTechDocMetaDataField() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		BaseClass base = new BaseClass(driver);
		base.stepInfo("Test case Id:RPMXCON-49124- Production Component");
		base.stepInfo(
				"To verify that in Production-TechIssue Docs Placeholder section, Metadata Field drop down should be sorted by alpha ascending");

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.selectGenerateOption(false);
		page.getTechissue_toggle().ScrollTo();
		page.getTechissue_toggle().waitAndClick(5);
		page.getTechInsertMetadataField().waitAndClick(5);
		List<String> TechIssueMetaDataField = base.availableListofElements(page.InsertMetaDataFieldValues());
		base.verifyOriginalSortOrder(TechIssueMetaDataField, TechIssueMetaDataField, "Ascending", true);

		page.navigatingToProductionHomePage();
		String productionname1 = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname1);
		page.selectGenerateOption(true);
		page.getTechissue_toggle().ScrollTo();
		page.getTechissue_toggle().waitAndClick(5);
		page.getTechInsertMetadataField().waitAndClick(5);
		List<String> PDFTechIssueMetaDataField = base.availableListofElements(page.InsertMetaDataFieldValues());
		base.verifyOriginalSortOrder(PDFTechIssueMetaDataField, PDFTechIssueMetaDataField, "Ascending", true);
		loginPage.logout();
	}

	/**
	 * @author Brundha.T No:RPMXCON-49126 Date:8/25/2022
	 * @Description:To verify that in Production-File Type Group Placeholder
	 *                 section, Metadata Field drop down should be sorted by alpha
	 *                 ascending
	 **/

	@Test(description = "RPMXCON-49126", enabled = true, groups = { "regression" })
	public void verifyingAscendingOrderInFileTypeMetaDataField() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		BaseClass base = new BaseClass(driver);
		base.stepInfo("Test case Id:RPMXCON-49126- Production Component");
		base.stepInfo(
				"To verify that in Production-File Type Group Placeholder section, Metadata Field drop down should be sorted by alpha ascending");

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.selectGenerateOption(false);
		page.getNativePlaceHolderIUnsertMetaDataFeild().ScrollTo();
		page.getNativePlaceHolderIUnsertMetaDataFeild().waitAndClick(5);
		List<String> TechIssueMetaDataField = base.availableListofElements(page.InsertMetaDataFieldValues());
		base.verifyOriginalSortOrder(TechIssueMetaDataField, TechIssueMetaDataField, "Ascending", true);

		page.navigatingToProductionHomePage();
		String productionname1 = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname1);
		page.selectGenerateOption(true);
		page.getNativePlaceHolderIUnsertMetaDataFeild().ScrollTo();
		page.getNativePlaceHolderIUnsertMetaDataFeild().waitAndClick(5);
		List<String> PDFTechIssueMetaDataField = base.availableListofElements(page.InsertMetaDataFieldValues());
		base.verifyOriginalSortOrder(PDFTechIssueMetaDataField, PDFTechIssueMetaDataField, "Ascending", true);
		loginPage.logout();
	}

	/**
	 * @author Brundha.T No:RPMXCON-63065 Date:25/8/2022
	 * @Description:Verify that user should be able to remove the automatically
	 *                     enabled native placeholdering under TIFF/PDF section from
	 *                     new production
	 **/

	@Test(description = "RPMXCON-63065", enabled = true, groups = { "regression" })
	public void verifyingGenerationOfTiffFileForSpreadSheet() throws Exception {

		base = new BaseClass(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case Id:RPMXCON-63065- Production component");
		base.stepInfo(
				"Verify that user should be able to remove the automatically enabled native placeholdering under TIFF/PDF section from new production");
		UtilityLog.info(Input.prodPath);

		String tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = "P" + Utility.dynamicNameAppender();
		String prefixID1 = "P" + Utility.dynamicNameAppender();
		String suffixID = "S" + Utility.dynamicNameAppender();
		String suffixID1 = "S" + Utility.dynamicNameAppender();
		String productionname = "P" + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");

		SessionSearch sessionSearch = new SessionSearch(driver);
		this.driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionSearch.SearchMetaData(Input.docFileType, Input.MetaDataFileType);
		sessionSearch.ViewInDocList();
		DocListPage doclist = new DocListPage(driver);
		int doc = 3;
		doclist.documentSelection(doc);
		doclist.bulkTagExistingFromDoclist(tagname);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		int FirstFile = Integer.valueOf(beginningBates);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.verifyingTheDefaultSelectedOptionInNative();
		page.getNativePlaceHolderCloseBtn().waitAndClick(2);
		page.getEnableForNativelyToggle().waitAndClick(2);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		page.extractFile();
		int Lastile = FirstFile + doc;
		page.isImageFileExist(FirstFile, Lastile, prefixID, suffixID);

		page.navigateToProductionPage();
		String beginningBates1 = page.getRandomNumber(2);
		String ProductionName1 = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(ProductionName1);
		page.fillingDATSection();
		page.selectGenerateOption(true);
		page.verifyingNativeSectionFileType(Input.MetaDataFileType);
		page.verifyingNativeSectionFileType(Input.NativeFileType);
		page.getNativePlaceHolderCloseBtn().waitAndClick(2);
		page.getEnableForNativelyToggle().waitAndClick(2);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID1, suffixID1, beginningBates1);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(ProductionName1);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();

		page.extractFile();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		File PdfFile = new File(home + "/Downloads/VOL0001/PDF/0001/" + prefixID + beginningBates1 + suffixID + ".pdf");
		page.isfileisExists(PdfFile);
		
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();

	}

	/**
	 * @author Brundha Test case id-RPMXCON-49244 Date:8/25/2022
	 * @DescriptionTo verify that if user select Document level option and select
	 *                the Next BatesNumbers then value should be auto-populated
	 * 
	 */
	@Test(description = "RPMXCON-49244", enabled = true, groups = { "regression" })
	public void verifyingNextAvailableBatesNumber() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		BaseClass base = new BaseClass(driver);
		base.stepInfo("RPMXCON-49244 -Production component");
		base.stepInfo(
				"To verify that if user select Document level option and select the Next BatesNumbers then value should be auto-populated");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String productionname1 = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.getBackButton().waitAndClick(5);
		driver.waitForPageToBeReady();
		String batesno = page.getProd_BatesRange().getText();

		page = new ProductionPage(driver);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname1);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.getClickHereLink().waitAndClick(2);
		page.getNextBatesNumber().waitAndClick(2);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname1);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.getbtnProductionGenerate().waitAndClick(10);
		page.verifyProductionStatusInGenPage("Reserving Bates Range Complete");
		driver.waitForPageToBeReady();
		String BatesNumber = page.getProd_BatesRange().getText();
		base.textCompareNotEquals(batesno, BatesNumber, "Production Generated with modified BatesNumber",
				"BatesNumber not modified");
		loginPage.logout();

	}

	/**
	 * @author Brundha TESTCASE No:RPMXCON-49342 Date:8/25/2022
	 * @Description:To verify that Tiff/PDF should generate with Tech Issue
	 *                 placeholdering even though File group placeholdering is
	 *                 exists.
	 */
	@Test(description = "RPMXCON-49342", enabled = true, groups = { "regression" })

	public void verifyingGeneratedTechIssuePlaceHolder() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-49342-from Production Component");
		base.stepInfo(
				"To verify that Tiff/PDF should generate with Tech Issue placeholdering even though File group placeholdering is exists.");
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String foldername = "folder" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String prefixID1 = Input.randomText + Utility.dynamicNameAppender();
		String suffixID1 = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.technicalIssue);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		int PureHit = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(tagname);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		int FirstFile = Integer.valueOf(beginningBates);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.selectGenerateOption(false);
		page.selectTechIssueDoc(tagname);
		driver.waitForPageToBeReady();
		driver.scrollingToElementofAPage(page.getNativeDocsPlaceholderText());
		page.getNativeDocsPlaceholderText().SendKeys(Input.testData1);
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
		page.extractFile();
		int LastFile = PureHit + FirstFile;
		driver.waitForPageToBeReady();
		page.OCR_Verification_In_Generated_Tiff_tess4j(FirstFile, LastFile, prefixID, suffixID, Input.tagNameTechnical);

		page = new ProductionPage(driver);
		String productionname1 = "p" + Utility.dynamicNameAppender();
		String beginningBates1 = page.getRandomNumber(2);
		int FirstFileNo = Integer.valueOf(beginningBates1);
		page.addANewProduction(productionname1);
		page.fillingDATSection();
		page.selectGenerateOption(true);
		page.selectTechIssueDoc(tagname);
		driver.waitForPageToBeReady();
		driver.scrollingToElementofAPage(page.getNativeDocsPlaceholderText());
		page.getNativeDocsPlaceholderText().SendKeys(Input.testData1);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID1, suffixID1, beginningBates1);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname1);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();
		int LastFiles = PureHit + FirstFileNo;
		driver.waitForPageToBeReady();
		page.pdf_Verification_In_Generated_PlaceHolder(FirstFileNo, LastFiles, prefixID1, suffixID1,
				Input.tagNameTechnical);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	/**
	 * @author Brundha TESTCASE No:RPMXCON-49343 Date:8/25/2022
	 * @Description:To verify that Tiff/PDF should generate with Priv placeholdering
	 *                 even though Tech Issue placeholdering is exists in the
	 *                 document.
	 */
	@Test(description = "RPMXCON-49343", enabled = true, groups = { "regression" })

	public void verifyingGeneratedPrivDocPlaceHolder() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-49343-from Production Component");
		base.stepInfo(
				"To verify that Tiff/PDF should generate with Priv placeholdering even though Tech Issue placeholdering is exists in the document.");
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String PrivTag = "Tag" + Utility.dynamicNameAppender();
		String foldername = "folder" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String prefixID1 = Input.randomText + Utility.dynamicNameAppender();
		String suffixID1 = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.technicalIssue);
		tagsAndFolderPage.createNewTagwithClassification(PrivTag, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		int PureHit = sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkTagExisting(PrivTag);
		sessionSearch.ViewInDocList();
		DocListPage doclist = new DocListPage(driver);
		doclist.documentSelection(3);
		doclist.bulkTagExistingFromDoclist(tagname);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		int FirstFile = Integer.valueOf(beginningBates);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTIFFSectionPrivDocs(PrivTag, Input.testData1);
		page.selectTechIssueDoc(tagname);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		String PrivDoc = page.getPrivDocCountInSummaryPage().getText();
		base.digitCompareEquals(6, Integer.valueOf(PrivDoc), "DocCount Displayed Correctly", "verification failed");
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();
		int LastFile = PureHit + FirstFile;
		driver.waitForPageToBeReady();
		page.OCR_Verification_In_Generated_Tiff_tess4j(FirstFile, LastFile, prefixID, suffixID, Input.testData1);

		page = new ProductionPage(driver);
		String productionname1 = "p" + Utility.dynamicNameAppender();
		String beginningBates1 = page.getRandomNumber(2);
		int FirstFileNo = Integer.valueOf(beginningBates1);
		page.addANewProduction(productionname1);
		page.fillingDATSection();
		page.fillingTIFFSectionPrivDocs(PrivTag, Input.testData1);
		page.selectTechIssueDoc(tagname);
		driver.scrollPageToTop();
		page.getPDFGenerateRadioButton().waitAndClick(5);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID1, suffixID1, beginningBates1);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname1);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();
		int LastFiles = PureHit + FirstFileNo;
		driver.waitForPageToBeReady();
		page.pdf_Verification_In_Generated_PlaceHolder(FirstFileNo, LastFiles, prefixID1, suffixID1, Input.testData1);

		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
		tagsAndFolderPage.DeleteTagWithClassification(tagname, Input.securityGroup);
		loginPage.logout();
	}

	
	/**
	 * @author Brundha.T No:RPMXCON-49128 Date:8/30/2022
	 * @Description:To verify that in Production-'Redaction Text by Selecting
	 *                 Redaction Tags' section, Metadata Field drop down should be
	 *                 sorted by alpha ascending
	 **/

	@Test(description = "RPMXCON-49128", enabled = true, groups = { "regression" })
	public void verifyingAscendingOrderInBurnRedactionMetaDataField() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		BaseClass base = new BaseClass(driver);
		base.stepInfo("Test case Id:RPMXCON-49128- Production Component");
		base.stepInfo(
				"To verify that in Production-'Redaction Text by Selecting Redaction Tags' section, Metadata Field drop down should be sorted by alpha ascending");

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.selectGenerateOption(false);
		page.getClk_burnReductiontoggle().waitAndClick(5);
		page.getClkLink_selectingRedactionTags().waitAndClick(5);
		page.getBurnRedaction_InsertMetaData().waitAndClick(5);
		List<String> BurnRedactionMetaDataField = base.availableListofElements(page.InsertMetaDataFieldValues());
		base.verifyOriginalSortOrder(BurnRedactionMetaDataField, BurnRedactionMetaDataField, "Ascending", true);

		page.navigatingToProductionHomePage();
		String productionname1 = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname1);
		page.selectGenerateOption(true);
		page.getClk_burnReductiontoggle().waitAndClick(5);
		page.getClkLink_selectingRedactionTags().waitAndClick(5);
		page.getBurnRedaction_InsertMetaData().waitAndClick(5);
		List<String> PDFBurnRedactionMetaDataField = base.availableListofElements(page.InsertMetaDataFieldValues());
		base.verifyOriginalSortOrder(PDFBurnRedactionMetaDataField, PDFBurnRedactionMetaDataField, "Ascending", true);
		loginPage.logout();
	}

	/**
	 * @author Brundha TESTCASE No:RPMXCON-47807 Date:8/30/2022
	 * @Description:To Verify Text generation as part of the actual generation
	 *                 process
	 */
	@Test(description = "RPMXCON-47807", enabled = true, groups = { "regression" })

	public void validatingPresenceOfTextFile() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		base.stepInfo("RPMXCON-47807-from Production Component");
		base.stepInfo("To Verify Text generation as part of the actual generation process");
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String prefixID1 = Input.randomText + Utility.dynamicNameAppender();
		String suffixID1 = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassificationInRMU(tagname, "Select Tag Classification");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearchForTwoItems(Input.testData1, Input.telecaSearchString);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.ViewInDocViewWithoutPureHit();

		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		base.waitTime(2);
		docViewRedactions.redactRectangleUsingOffset(10, 10, 100, 100);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Input.defaultRedactionTag);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingTextSection();
		base.ValidateElement_Presence(page.getAnsiRadioBtn(), "Ansi RadioBtn");
		base.ValidateElement_Presence(page.getAnsiDrpDwn(), "Ansi Dropdown");
		base.ValidateElement_Presence(page.getAdvancedOptionInTextTab(), "Advanced Option");
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();
		driver.waitForPageToBeReady();

		String home = System.getProperty("user.home");
		File Textfile = new File(
				home + "/Downloads/VOL0001/Text/0001/" + prefixID + beginningBates + suffixID + ".txt");
		page.isfileisExists(Textfile);

		page = new ProductionPage(driver);
		String productionname1 = "p" + Utility.dynamicNameAppender();
		String beginningBates1 = page.getRandomNumber(2);
		page.addANewProduction(productionname1);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID1, suffixID1, beginningBates1);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingProductionLocationPage(productionname1);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopup();
		page.extractFile();
		driver.waitForPageToBeReady();
		File Textfile1 = new File(
				home + "/Downloads/VOL0001/Text/0001/" + prefixID1 + beginningBates1 + suffixID1 + ".txt");
		if (!Textfile1.exists()) {
			base.passedStep("Text file is not Exist as expected");
		} else {
			base.failedStep("verification failed");
		}

		loginPage.logout();

	}

	/**
	 * @author Brundha TESTCASE No:RPMXCON-47806 Date:8/30/2022
	 * @Description:To Verify As an Admin be able to include slip sheets for the
	 *                 documents in the generated PDF
	 */
	@Test(description = "RPMXCON-47806", enabled = true, groups = { "regression" })

	public void verifyingSlipsheetValueInPDF() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		base.stepInfo("RPMXCON-47806 -Production Component");
		base.stepInfo("To Verify As an Admin be able to include slip sheets for the documents in the generated PDF");

		String foldername = "Folder" + Utility.dynamicNameAppender();
		String productionname = "p" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, "Default Security Group");

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		page = new ProductionPage(driver);
		String beginningBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.fillingNativeSection();
		page.selectGenerateOption(true);
		page.fillingSlipSheet(true, foldername);
		String Text = page.getSlipSheetSelectedText().getText();
		base.compareTextViaContains(Text, foldername, "Slipsheet Text displayed", "Text not displayed");
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
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		page.extractFile();

		File pdfFile = new File(home + "/Downloads/VOL0001/PDF/0001/" + prefixID + beginningBates + suffixID + ".pdf");
		page.isfileisExists(pdfFile);
		page.pdf_Verification_In_Generated_PlaceHolder(prefixID, suffixID, beginningBates, foldername);

		loginPage.logout();
	}

	/**
	 * @author Brundha TESTCASE No:RPMXCON-48328 Date:8/30/2022
	 * @Description:To verify that the selected metadata is not displayed only when
	 *                 the doc has at least one of the selected redaction tags in
	 *                 Burn Redactions in Tiff
	 */
	@Test(description = "RPMXCON-48328", enabled = true, groups = { "regression" })

	public void validatingDATSection() throws Exception {

		
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		base.stepInfo("RPMXCON-48328-from Production Component");
		base.stepInfo(
				"To verify that the selected metadata is not displayed only when the doc has at least one of the selected redaction tags in Burn Redactions in Tiff");
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String BatesNumber = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");

		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkTagExisting(tagname);
		sessionSearch.ViewInDocViewWithoutPureHit();

		base.waitTime(2);
		docViewRedactions.redactRectangleUsingOffset(10, 10,120,120);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Input.defaultRedactionTag);

		loginPage.logout();
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.addDATFieldAtSecondRow(Input.bates, "EndingBates", BatesNumber);
		page.getRedactDATCheckBox(1).waitAndClick(5);
		page.fillingNativeSection();
		page.selectGenerateOption(false);
		page.getClk_burnReductiontoggle().waitAndClick(5);
		page.navigateToNextSection();
		page.fillingNumberingAndSortingTab(prefixID, suffixID, beginningBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionWithTag(tagname);
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
		page.isdatFileExist();
		page.verifyingGeneratedDATFile(DatFile, 1, 1, "þ");
		loginPage.logout();

	}

	/**
	 * @author Brundha TESTCASE No:RPMXCON-48330 Date:8/30/2022
	 * @Description:To verify that the selected metadata is not displayed in DAT if
	 *                 the doc has at least one of the selected PRIV tags in PRIV
	 *                 placeholdering for PDF
	 */
	@Test(description = "RPMXCON-48330", enabled = true, groups = { "regression" })

	public void validatingDATSectionForPrivDoc() throws Exception {

		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-48330-from Production Component");
		base.stepInfo(
				"To verify that the selected metadata is not displayed in DAT if the doc has at least one of the selected PRIV tags in PRIV placeholdering for PDF");
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String foldername = "Tag" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String BatesNumber = Input.randomText + Utility.dynamicNameAppender();
		String bates = "þ";

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateTagwithClassification(tagname, Input.tagNamePrev);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		sessionSearch.ViewInDocList();
		DocListPage doc = new DocListPage(driver);
		doc.documentSelection(2);
		doc.bulkTagExistingFromDoclist(tagname);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		page.addANewProduction(productionname);
		page.fillingDATSection();
		page.addDATFieldAtSecondRow(Input.bates, "EndingBates", BatesNumber);
		page.getPrivledgedDATCheckBox(1).waitAndClick(5);
		page.selectPrivDocsInPDFSection(tagname);
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
		page.extractFile();
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		String name = page.getProduction().getText().trim();
		driver.waitForPageToBeReady();
		File DatFile = new File(home + "/Downloads/VOL0001/Load Files/" + name + "_DAT.dat");
		page.isdatFileExist();
		page.verifyingGeneratedDATFile(DatFile, 1, 1, bates);
		loginPage.logout();

	}

	/**
	 * @author Brundha TESTCASE No:RPMXCON-47717 Date:8/30/2022
	 * @Description:To Verify ProjectAdmin will be able to edit configuration of a
	 *                 production that hasn’t yet been locked and bates number has
	 *                 not been committed
	 */
	@Test(description = "RPMXCON-47717", enabled = true, groups = { "regression" })

	public void verifyingTheProductionWithNewConfiguration() throws Exception {
		UtilityLog.info(Input.prodPath);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("RPMXCON-47717-from Production Component");
		base.stepInfo(
				"To Verify ProjectAdmin will be able to edit configuration of a production that hasn’t yet been locked and bates number has not been committed");

		String foldername = "folder" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		String prefixID1 = Input.randomText + Utility.dynamicNameAppender();
		String suffixID1 = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		productionname = "p" + Utility.dynamicNameAppender();
		String beginningBates = page.getRandomNumber(2);
		String beginningBates1 = page.getRandomNumber(2);
		page.addANewProduction(productionname);
		page.fillingDATSection();
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
		String batesno = page.getProd_BatesRange().getText();

		page.clickElementNthtime(page.getBackButton(), 5);
		page.getMarkInCompleteBtn().waitAndClick(5);
		page.fillingNumberingAndSortingTab(prefixID1, suffixID1, beginningBates1);
		page.clickMArkCompleteMutipleTimes(2);
		page.fillingPrivGuardPage();
		page.clickMArkCompleteMutipleTimes(2);
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommitandDownload();
		page.getBackButton().waitAndClick(5);
		String BatesNumber = page.getProd_BatesRange().getText();
		base.textCompareNotEquals(batesno, BatesNumber, "Production Generated with modified BatesNumber",
				"BatesNumber not modified");
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
		System.out.println("******TEST CASES FOR Production EXECUTED******");

	}

}
