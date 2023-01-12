package sightline.ingestion;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.Callable;

import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import pageFactory.BaseClass;
import pageFactory.DataSets;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.IngestionPage_Indium;
import pageFactory.LoginPage;
import pageFactory.ProjectFieldsPage;
import pageFactory.ProjectPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TallyPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Ingestion_Phase2_Regression1 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	IngestionPage_Indium ingestionPage;
	SessionSearch sessionSearch;
	DocListPage docList;
	DataSets dataSets;
	Input ip;
	TallyPage tally;
	DocExplorerPage docExplorer;
	ProjectPage project;
	UserManagement userManage;
	SecurityGroupsPage securityGroup;

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		ip = new Input();
		ip.loadEnvConfig();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());

		driver = new Driver();
		baseClass = new BaseClass(driver);
		sessionSearch = new SessionSearch(driver);
		ingestionPage = new IngestionPage_Indium(driver);
		loginPage = new LoginPage(driver);	
	}
	
	/*This class contains 73 testCases from sprint 15 to 24.*/
	
	/**
	 * Author :Arunkumar date: 20/06/2022 TestCase Id:RPMXCON-49320 
	 * Description :ICE: Verify the help content for "Kick Off Analytics Automatically" option
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49320",enabled = true, groups = { "regression" })
	public void TCA1verifyHelpContentForKickOffAnalyticsAutomatically() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49320");
		baseClass.stepInfo("ICE: Verify the help content for Kick Off Analytics Automatically option");
		ingestionPage = new IngestionPage_Indium(driver);
		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Navigating to manage project page");
		ingestionPage.navigateToManageProjectPage();
		baseClass.stepInfo("Verifying the help content for Kick Off Analytics Automatically option");
		ingestionPage.verifyHelpContentOnProjectCreationPage(Input.kickOffAnalytics, Input.kickOffHelpContent);
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 20/06/2022 TestCase Id:RPMXCON-49321 
	 * Description :ICE: Verify the help content for "Run Incremental Analytics for New Small Data (<20%)" option
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49321",enabled = true, groups = { "regression" })
	public void TCA2verifyHelpContentForIncrementalAnalytics() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49321");
		baseClass.stepInfo("ICE: Verify the help content for Run Incremental Analytics for New Small Data (<20%) option");
		ingestionPage = new IngestionPage_Indium(driver);
		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Navigating to manage project page");
		ingestionPage.navigateToManageProjectPage();
		baseClass.stepInfo("Verifying the help content for run incremental analytics option");
		ingestionPage.verifyHelpContentOnProjectCreationPage(Input.incrementalAnalytics, Input.incrementalHelpContent);
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 20/06/2022 TestCase Id:RPMXCON-49319 
	 * Description :ICE: Verify that the options "Kick Off Analytics Automatically" and "Run Incremental Analytics
	 *  for New Small Data (<20%)" available in project settings
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49319",enabled = true, groups = { "regression" })
	public void TCA3verifyOptionsAvailableInProjectSetting() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49319");
		baseClass.stepInfo("ICE: Verify the options available in project settings");
		ingestionPage = new IngestionPage_Indium(driver);
		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Navigating to manage project page");
		ingestionPage.navigateToManageProjectPage();
		baseClass.stepInfo("Verifying the the options available in project settings");
		ingestionPage.verifyoptionsAvailability(Input.kickOffAnalytics, Input.incrementalAnalytics);
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 20/06/2022 TestCase Id:RPMXCON-49490 
	 * Description :Verify that in Ingestion Wizard page, in the TIFF section
	 *  "Generate Searchable PDF for TIFFs" options should be displayed
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49490",enabled = true, groups = { "regression" })
	public void TCA4verifyGenerateSearchablePdfOption() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49490");
		baseClass.stepInfo("Verify that in Ingestion Wizard page, in the TIFF section'Generate Searchable PDF for TIFFs' options should be displayed");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Verify Generate Searchable PDF option displayed in Tiff section");
		ingestionPage.verifyGeneratePdfOptionInTiffSection();
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 21/06/2022 TestCase Id:RPMXCON-48244
	 * Description :To verify Document Count for Audio Docs in Indexing section
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48244",enabled = true, groups = { "regression" })
	public void verifyCountInAudioDocsIndexing() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48244");
		baseClass.stepInfo("To verify Document Count for Audio Docs in Indexing section");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion till indexing");
		ingestionPage.performAudio96DocsIngestion(Input.audioDatFile, Input.docIdKey);
		ingestionPage.ingestionCatalogging();
		ingestionPage.ignoreErrorsAndCopying();
		ingestionPage.ingestionIndexing(Input.audio96DocsFolder);
		baseClass.stepInfo("Verify audio docs count in copying and indexing stage");
		baseClass.waitForElement(ingestionPage.getIngestionDetailPopup(1));
		ingestionPage.getIngestionDetailPopup(1).waitAndClick(10);
		// verify audio docs count in copy and indexing section
		int copySectionCount = Integer.parseInt(ingestionPage.copyTableDataValue(Input.mp3Variant, 1).getText());
		int indexSectionCount = Integer.parseInt(ingestionPage.indexTableDataValue(Input.audio, 1).getText());
		if(copySectionCount==indexSectionCount) {
			baseClass.passedStep("Document count of audio docs are same in both copy and index section");
		}
		else {
			baseClass.failedStep("Document count of audio docs not matched");
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return ingestionPage.getCloseButton().Enabled();
			}
		}), Input.wait30);
		ingestionPage.getCloseButton().waitAndClick(10);
		// Rollback
		ingestionPage.rollBackIngestion();
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 21/06/2022 TestCase Id:RPMXCON-48236
	 * Description :To Verify In Ingestions, for audio indexing, there should not be any error message.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48236",enabled = true, groups = { "regression" })
	public void verifyAudioIndexingErrorMessage() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48236");
		baseClass.stepInfo("To Verify In Ingestions, for audio indexing, there should not be any error message.");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion");
		ingestionPage.performAutomationAllsourcesIngestion(Input.sourceSystem,Input.DATFile1, Input.prodBeg);
		ingestionPage.ignoreErrorsAndCatlogging();
		ingestionPage.ignoreErrorsAndCopying();
		ingestionPage.ingestionIndexing(Input.AllSourcesFolder);
		baseClass.passedStep("Ingestion with audio and language completed indexing stage without any error");
		//rollback
		ingestionPage.rollBackIngestion();
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 21/06/2022 TestCase Id:RPMXCON-47372
	 * Description :To verify that user is not able to search the data if Ingestions Indexing is running
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47372",enabled = true, groups = { "regression" })
	public void verifyDataSearchWhileindexProcessRunning() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47372");
		baseClass.stepInfo("To verify that user is not able to search the data if Ingestions Indexing is running");
	
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("perform add only ingestion and start indexing");
		ingestionPage.performAutomationAllsourcesIngestion(Input.sourceSystem,Input.DATFile1, Input.prodBeg);
		ingestionPage.ignoreErrorsAndCatlogging();
		ingestionPage.ignoreErrorsAndCopying();
		String ingestionName=ingestionPage.startIndexing(true, Input.language);
		baseClass.stepInfo("verify options available in settings");
		ingestionPage.verifyOptionsAvailableInIngestionSetting();
		baseClass.stepInfo("search the data of ingestion");
		int dataCount = sessionSearch.basicSearchWithMetaDataQuery(ingestionName, Input.metadataIngestion);
		if(dataCount==0) {
			baseClass.passedStep("Not able to view the data if ingestion indexing is running");
		}
		else {
			baseClass.failedStep("able to view the data if ingestion indexing is running");
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 21/06/2022 TestCase Id:RPMXCON-49552 
	 * Description :Verify that Ingestion Email Metadata 'EmailToNamesAndAddresses' is available
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49552",enabled = true, groups = { "regression" })
	public void verifyIngestionMetaDataAvailable() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49552");
		baseClass.stepInfo("Verify that Ingestion Email Metadata 'EmailToNamesAndAddresses' is available");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Select dataset/source and move to mapping field section");
		ingestionPage.OverlayIngestionForDATWithMappingFieldSection(Input.GD994NativeTextForProductionFolder,
				Input.datLoadFile2, Input.documentKey);
		baseClass.stepInfo("Verify option available in dropdown");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return ingestionPage.getMappingCategoryField(5).Displayed();
			}
		}), Input.wait30);
		ingestionPage.getMappingCategoryField(5).selectFromDropdown().selectByVisibleText(Input.email);
		List<WebElement> optionList= ingestionPage.getMappingDestinationField(5).selectFromDropdown().getOptions();
		int size=optionList.size();
		for(int i=0; i<=size;i++) {
			String option =optionList.get(i).getText();
			if(option.equalsIgnoreCase(Input.emailToNamesAndAddresses)) {
				baseClass.passedStep("'EmailToNamesAndAddresses' field available in the destination field dropdown");
				break;
			}
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 22/06/2022 TestCase Id:RPMXCON-49556
	 * Description :Verify Ingestion should published successfully with new Email metadata
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49556",enabled = true, groups = { "regression" })
	public void verifyAddOnlyIngestionWithNewEmailMetadata() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49556");
		baseClass.stepInfo("Verify Ingestion should published successfully with new Email metadata");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
		if (status == false) {
			baseClass.stepInfo("Performing add only ingestion");
			ingestionPage.performGD_994NativeFolderIngestion(Input.sourceSystem,Input.datLoadFile2,Input.nativeLoadFile2, 
					Input.textLoadFile2);
			ingestionPage.publishAddonlyIngestion(Input.GD994NativeTextForProductionFolder);
			baseClass.passedStep("Ingestion published successfully with new email metadata");
		}
		else {
			baseClass.passedStep("Ingestion already present in the project");
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 22/06/2022 TestCase Id:RPMXCON-48290 
	 * Description :To verify In Ingestion, ADD Only ,ASCII(59) should be default New Line delimiter
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48290",enabled = true, groups = { "regression" })
	public void verifyAddOnlyIngestionWithDefaultDelimiter() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48290");
		baseClass.stepInfo("To verify In Ingestion, ADD Only ,ASCII(59) should be default New Line delimiter");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("perform add only ingestion with default new line delimiter");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem, 
				Input.sourceLocation, Input.HiddenPropertiesFolder);
		baseClass.stepInfo("verify default value of new line delimiter");
		ingestionPage.verifyDefaultValueOfDelimiter();
		baseClass.stepInfo("Selecting Dat and Native file");
		ingestionPage.selectDATSource(Input.YYYYMMDDHHMISSDat, Input.sourceDocIdSearch);
		ingestionPage.selectNativeSource(Input.YYYYMMDDHHMISSLst, false);
		baseClass.waitForElement(ingestionPage.getDateFormat());
		ingestionPage.getDateFormat().selectFromDropdown().selectByVisibleText(Input.dateFormat);
		ingestionPage.clickOnNextButton();
		ingestionPage.clickOnPreviewAndRunButton();
		//Complete analytics and publish ingestion
		ingestionPage.publishAddonlyIngestion(Input.HiddenPropertiesFolder);
		baseClass.passedStep("Ingestion completed successfully without error message");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 24/06/2022 TestCase Id:RPMXCON-48148 
	 * Description :To Verify User is able to Ingest Transcript  along with native
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48148",enabled = true, groups = { "regression" })
	public void verifyIngestingTranscriptAlongWithNative() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-48148");
		baseClass.stepInfo("To Verify User is able to Ingest Transcript  along with native");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		// perform add only ingestion
		baseClass.stepInfo("Ingest Transcript along with native");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		if (status == false) {
			ingestionPage.performAutomationAllsourcesIngestion(Input.sourceSystem,Input.DATFile1, Input.prodBeg);
			ingestionPage.publishAddonlyIngestion(Input.AllSourcesFolder);
			baseClass.passedStep("performed ingesting transcript along with native successfully");
		}
		else {
			baseClass.passedStep("Transcript along with native ingestion already present in the project");
		}
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 24/06/2022 TestCase Id:RPMXCON-47305
	 * Description :To verify that progress bar is displayed on tiles
	 * and Counts of Ingested and Errors keeps on updated once Ingestion process is started.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47305",enabled = true, groups = { "regression" })
	public void verifyProgressBarAndCountDetailsOnTiles() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47305");
		baseClass.stepInfo("Verify progress bar and count data updated once ingestion process started");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Start new ingestion and verify status on tiles");
		ingestionPage.IngestionOnlyForDatFile(Input.UniCodeFilesFolder,Input.datLoadFile1);
		ingestionPage.verifyDetailsAfterStartedIngestion();
		ingestionPage.ingestionCatalogging();
		baseClass.stepInfo("verify content on ingestion tiles");
		ingestionPage.verifyContentOnIngestionTiles();
		// rollback ingestion
		ingestionPage.rollBackIngestion();
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 24/06/2022 TestCase Id:RPMXCON-47312
	 * Description :To verify that user can Ingest the files which are in Draft Mode
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47312",enabled = true, groups = { "regression" })
	public void verifyIngestionFromDraftMode() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47312");
		baseClass.stepInfo("To verify that user can Ingest the files which are in Draft Mode");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Map mandatory fields for ingestion and save");
		ingestionPage.OverlayIngestionForDATWithMappingFieldSection(Input.HiddenPropertiesFolder,
				Input.YYYYMMDDHHMISSDat, Input.sourceDocIdSearch);
		ingestionPage.verifyIngestionStatusAfterSaveAsDraft();
		ingestionPage.getIngestionSettingGearIcon().waitAndClick(5);
		//verify options available after clicking settings icon
		ingestionPage.verifyOptionsAvailableForDraftStageIngestion();
		baseClass.stepInfo("perform ingestion from draft mode using open in wizard option");
		ingestionPage.IngestionFromDraftModeWithOpenWizardOption("Native",Input.YYYYMMDDHHMISSLst);
		ingestionPage.ignoreErrorsAndCatlogging();
		baseClass.passedStep("Ingestion status changed from draft to catalog state");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 05/07/2022 TestCase Id:RPMXCON-49338 
	 * Description :Verify value of metadata field "DocPrimaryLanguage" should be derived from CA for Add Only Ingestion
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49338",enabled = true, groups = { "regression" })
	public void verifyValueOfDocPrimaryLanguageMetadata() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-49338");
		baseClass.stepInfo("Verify value of metadata field 'DocPrimaryLanguage' should be derived from CA for Add Only Ingestion");
		dataSets = new DataSets(driver);
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion and navigate to doclist");
		boolean status = ingestionPage.verifyIngestionpublish(Input.HiddenPropertiesFolder);
		if (status == false) {
			ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder, Input.YYYYMMDDHHMISSDat);
			String ingestionName= ingestionPage.publishAddonlyIngestion(Input.HiddenPropertiesFolder);
		}
		dataSets.selectDataSetWithName(Input.HiddenPropertiesFolder);
		baseClass.stepInfo("Verify the value of metadata");
		ingestionPage.addMetadatAndVerifyValue(Input.docPrimaryLanguage, Input.english);
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 05/07/2022 TestCase Id:RPMXCON-49265 
	 * Description :To verify that option "ICE" is available in the Source System dropdown in Ingestion
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49265",enabled = true, groups = { "regression" })
	public void TCA5verifyIceOptionInSourceSystemDropdown() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-49265");
		baseClass.stepInfo("To verify that option 'ICE' is available in the Source System dropdown in Ingestion");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("verify option 'ICE' available under source system");
		ingestionPage.verifyOptionAvailableInSourceSystem();
		loginPage.logout();
	
	}
	
	/**
	 * Author :Arunkumar date: 08/07/2022 TestCase Id:RPMXCON-47376 
	 * Description :To verify that Delete button is available on Tile.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47376",enabled = true, groups = { "regression" })
	public void verifyOverlayIngestionForSameFiles() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47376");
		baseClass.stepInfo("verify that Delete button is available on Tile.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add new ingestion and save as draft");
		ingestionPage.sourceSelectionAndIngestionTypeSectionOnlyWithDATfile(Input.HiddenPropertiesFolder,
				Input.YYYYMMDDHHMISSDat);
		ingestionPage.verifyIngestionStatusAfterSaveAsDraft();
		baseClass.stepInfo("Verify delete option available in settings option");
		ingestionPage.verifyOptionsAvailableForDraftStageIngestion();
		ingestionPage.getIngestionSettingGearIcon().waitAndClick(5);
		baseClass.stepInfo("Verify ingestion detail popup display and action dropdown options");
		ingestionPage.verifyIngestionDetailPopupAndActionDropDown();
		baseClass.stepInfo("Delete ingestion");
		ingestionPage.deleteIngestion();
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 08/07/2022 TestCase Id:RPMXCON-47399 
	 * Description :To verify Back button functionality for ingestion wizard
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47399",enabled = true, groups = { "regression" })
	public void TCA6verifyBackButtonFunctionality() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47399");
		baseClass.stepInfo("verify Back button functionality for ingestion wizard");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion and Enter mandatory field values");
		ingestionPage.sourceSelectionAndIngestionTypeSectionOnlyWithDATfile(Input.HiddenPropertiesFolder,
				Input.YYYYMMDDHHMISSDat);
		baseClass.stepInfo("verify source and mapping section status after clicking Next button");
		ingestionPage.verifySourceSectionStatusAfterClickingNextButton();
		baseClass.stepInfo("verify source and mapping section status after clicking back button");
		ingestionPage.verifySourceAndMappingSectionStatusAfterClickingBackButton();
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 11/07/2022 TestCase Id:RPMXCON-47398
	 * Description :To verify the mandatory field validations in Ingestion Wizard.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47398",enabled = true, groups = { "regression" })
	public void verifyMandatoryFieldInWizard() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47398");
		baseClass.stepInfo("verify the mandatory field validations in Ingestion Wizard");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add ingestion and verify mandatory field warning message");
		ingestionPage.validateIngestionWizardMandatoryFieldWarningMessage();
		baseClass.stepInfo("Verify source and mapping section status after filling mandatory field");
		ingestionPage.navigateToIngestionPage();
		ingestionPage.sourceSelectionAndIngestionTypeSectionOnlyWithDATfile(Input.UniCodeFilesFolder,Input.datLoadFile1);
		ingestionPage.verifySourceSectionStatusAfterClickingNextButton();
		baseClass.stepInfo("verify mandatory field error in mapping section");
		ingestionPage.validateMappingSectionMandatoryFieldWarningMessage();
		baseClass.stepInfo("Map mandatory field and verify preview record popup displayed");
		ingestionPage.selectValueFromEnabledFirstThreeSourceDATFields(Input.documentKey,Input.documentKey,Input.custodian);
		ingestionPage.clickOnPreviewAndRunButton();
		baseClass.passedStep("Verified the mandatory field validations in Ingestion Wizard");
		loginPage.logout();
		
	}
	

	/**
	 * Author :Arunkumar date: 11/07/2022 TestCase Id:RPMXCON-47397
	 * Description :To verify the Source Selection in Ingestion Wizard Page.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47397",enabled = true, groups = { "regression" })
	public void verifySourceSelectionInWizard() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47397");
		baseClass.stepInfo("verify the Source Selection in Ingestion Wizard Page.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Check the file types availability under source selection");
		ingestionPage.verifyDifferentFileTypesAvailability();
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType,Input.sourceSystem, Input.sourceLocation, Input.UniCodeFilesFolder);
		baseClass.stepInfo("verify dat and remainingfile field options and their availability");
		ingestionPage.verifyDatAndRemainingFileFieldOptionsAvailability(Input.datLoadFile1);
		baseClass.stepInfo("verify Other file field availability and link type dropdown values");
		ingestionPage.verifyOtherFileFieldOptionAndDropdownValueAvailability();
		loginPage.logout();
		
	}
	

	/**
	 * Author :Arunkumar date: 13/07/2022 TestCase Id:RPMXCON-47584 
	 * Description :To verify that once Configure Mapping is done Admin is able to go on Preview Mapping section.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47584",enabled = true, groups = { "regression" })
	public void TCA7verifyConfigureMappingSection() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47584");
		baseClass.stepInfo("verify that once Configure Mapping is done Admin is able to go on Preview Mapping section.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion and click on next button");
		ingestionPage.sourceSelectionAndIngestionTypeSectionOnlyWithDATfile(Input.UniCodeFilesFolder,Input.datLoadFile1);
		baseClass.stepInfo("verify source and mapping section status after clicking Next button");
		ingestionPage.verifySourceSectionStatusAfterClickingNextButton();
		baseClass.stepInfo("Configure mapping and run ingestion");
		ingestionPage.selectValueFromEnabledFirstThreeSourceDATFields(Input.documentKey,Input.documentKey,Input.custodian);
		ingestionPage.clickOnPreviewAndRunButton();
		baseClass.stepInfo("verify tile in ingestion homepage");
		ingestionPage.verifyDetailsAfterStartedIngestion();
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 13/07/2022 TestCase Id:RPMXCON-47582
	 * Description :To verify mandatory field validation for mapping fields before Preview&Run.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47582",enabled = true, groups = { "regression" })
	public void TCA8verifyMandatoryFieldValidationInMappingSection() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47582");
		baseClass.stepInfo("verify mandatory field validation for mapping fields before Preview&Run.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add ingestion and click on next");
		ingestionPage.sourceSelectionAndIngestionTypeSectionOnlyWithDATfile(Input.UniCodeFilesFolder,Input.datLoadFile1);
		ingestionPage.verifySourceSectionStatusAfterClickingNextButton();
		baseClass.stepInfo("verify mandatory field error in mapping section");
		ingestionPage.validateMappingSectionMandatoryFieldWarningMessage();
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 13/07/2022 TestCase Id:RPMXCON-48146
	 * Description :To Verify selected audio and transcript file types are retained on opening of Draft Ingestion in Wizard.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48146",enabled = true, groups = { "regression" })
	public void verifyAudioAndTranscriptFilesRetainedStatus() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-48146");
		baseClass.stepInfo("Verify selected audio and transcript file types are retained on opening of Draft Ingestion in Wizard.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion with dat,mp3 and transcript");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType,Input.sourceSystem, 
				Input.sourceLocation, Input.AK_NativeFolder);
		baseClass.waitForElement(ingestionPage.getDATDelimitersNewLine());
		ingestionPage.getDATDelimitersNewLine().selectFromDropdown().selectByVisibleText(Input.multiValue);
		baseClass.waitForElement(ingestionPage.getSourceSelectionDATLoadFile());
		ingestionPage.selectDATSource(Input.DATFile1, Input.prodBeg);
		ingestionPage.selectMP3VarientSource(Input.MP3File, false);
		ingestionPage.selectAudioTranscriptSource(Input.TranscriptFile, false);
		baseClass.waitForElement(ingestionPage.getDateFormat());
		ingestionPage.getDateFormat().selectFromDropdown().selectByVisibleText(Input.dateFormat);
		baseClass.stepInfo("Save ingestion as draft");
		ingestionPage.verifyIngestionStatusAfterSaveAsDraft();
		baseClass.stepInfo("select ingestion from draft and open in wizard");
		driver.waitForPageToBeReady();
		ingestionPage.getIngestionDetailPopup(1).waitAndClick(5);
		baseClass.waitForElement(ingestionPage.getActionDropdownArrow());
		ingestionPage.getActionDropdownArrow().waitAndClick(5);
		baseClass.waitForElement(ingestionPage.getActionOpenWizard());
		ingestionPage.getActionOpenWizard().waitAndClick(5);
		baseClass.stepInfo("verify retained value of audio and transcript file");
		driver.waitForPageToBeReady();
		ingestionPage.validateDetailsAfterOpeningIngestionFromDraft(Input.ingestionType, Input.AK_NativeFolder);
		String retainedMp3 =ingestionPage.getMP3LST().selectFromDropdown().getFirstSelectedOption().getText();
		String retainedTranscript = ingestionPage.getAudioTranscriptLST().selectFromDropdown().getFirstSelectedOption().getText();
		if(retainedMp3.equalsIgnoreCase(Input.MP3File) && retainedTranscript.equalsIgnoreCase(Input.TranscriptFile)) {
			baseClass.passedStep("Both audio and transcript files retained after opening ingestion in wizard from draft");
		}
		else {
			baseClass.failedStep("Both audio and transcript files not retained after opening ingestion in wizard from draft");
		}	
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 25/07/2022 TestCase Id:RPMXCON-47364
	 * Description :To verify that in Grid view, information of tiles are displayed in tabular with the
	 * required columns
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47364",enabled = true, groups = { "regression" })
	public void verifyGridViewInformation() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47364");
		baseClass.stepInfo("Validate the columns available in grid view table");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Click on show all and grid view button");
		ingestionPage.navigateToIngestionPage();
		baseClass.waitForElement(ingestionPage.getShowAllCheckbox());
		ingestionPage.getShowAllCheckbox().waitAndClick(5);
		baseClass.passedStep("Validate columns displayed in grid view table");
		ingestionPage.validateColumnsPresentInGridViewTable();
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 25/07/2022 TestCase Id:RPMXCON-47297
	 * Description :If ‘Add Only’ option is selected, DoC ID, Custodian, ParentDocID and Datasource
	 * fields must be selected in the mapping.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47297",enabled = true, groups = { "regression" })
	public void verifyFieldsSelectedInMapping() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47297");
		baseClass.stepInfo("verify that mandatory fields must be selected in mapping");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion and click next button");
		ingestionPage.sourceSelectionAndIngestionTypeSectionOnlyWithDATfile(Input.UniCodeFilesFolder,
				Input.datLoadFile1);
		ingestionPage.verifySourceSectionStatusAfterClickingNextButton();
		baseClass.stepInfo("verify mandatory mapping fields and auto mapping for SourceDocID");
		ingestionPage.validateMappingSectionMandatoryFieldWarningMessage();
		String key = ingestionPage.getMappingSourceField(1).selectFromDropdown().getFirstSelectedOption().getText();
		if(key.equalsIgnoreCase(Input.documentKey)) {
			baseClass.passedStep("Destination 'SourceDocID' is auto mapped with DocID.");
		}
		else {
			baseClass.failedStep("SourceDocID source field not auto mapped");
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 20/07/2022 TestCase Id:RPMXCON-47578
	 * Description :To verify that in "Source & Overwrite Settings" section, the source selection is changed.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47578",enabled = true, groups = { "regression" })
	public void verifySourceSelectionInSourceSettings() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47578");
		baseClass.stepInfo("verify that in 'Source & Overwrite Settings'section, the source selection is changed.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion and verify available file option checkboxes");
		ingestionPage.validateIngestionWizardMandatoryFieldWarningMessage();
		ingestionPage.navigateToIngestionPage();
		ingestionPage.verifyDifferentFileTypesAvailability();
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType,Input.sourceSystem, Input.sourceLocation, Input.UniCodeFilesFolder);
		baseClass.stepInfo("verify file path,load file and IS DAT options status");
		ingestionPage.verifyDatAndRemainingFileFieldOptionsAvailability(Input.datLoadFile1);
		ingestionPage.verifyOtherFileFieldOptionAndDropdownValueAvailability();
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 20/07/2022 TestCase Id:RPMXCON-47581
	 * Description :To verify that row population in the Configure Mapping will be as per the fields 
	 * available in the DAT file.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47581",enabled = true, groups = { "regression" })
	public void verifyRowPopulationInConfigureMappingSection() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47581");
		baseClass.stepInfo("verify that row population in the Configure Mapping section.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion details and click on Next");
		ingestionPage.sourceSelectionAndIngestionTypeSectionOnlyWithDATfile(Input.HiddenPropertiesFolder,
				Input.YYYYMMDDHHMISSDat);
		baseClass.stepInfo("Verify row auto populated in configure mapping section");
		ingestionPage.verifySourceSectionStatusAfterClickingNextButton();
		if(ingestionPage.configureMappingRows().isElementAvailable(10)) {
			int rows = ingestionPage.configureMappingRows().size();
			baseClass.passedStep("Number of rows auto populated in configure mapping -'"+rows+"'");
		}
		else {
			baseClass.failedStep("Rows not populated or the configure mapping section not enabled");
		}
		baseClass.passedStep("Source field gets auto populated as per the fields available in the DAT file.");
		loginPage.logout();	
	}
	
	/**
	 * Author :Arunkumar date: 21/07/2022 TestCase Id:RPMXCON-47586
	 * Description :To verify that on "Copy Ingestion" once the Configure mapping is not matched 
	 * between Current and Copied Ingestion then Warning message should displayed.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47586",enabled = true, groups = { "regression" })
	public void verifyConfigureMappingWarningWhenMappingNotMatched() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47586");
		baseClass.stepInfo("verify configure mapping warning message when mapping not matched");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion and click next button");
		ingestionPage.sourceSelectionAndIngestionTypeSectionOnlyWithDATfile(Input.UniCodeFilesFolder,Input.datLoadFile1);
		baseClass.stepInfo("Configure mapping and run ingestion");
		ingestionPage.verifySourceSectionStatusAfterClickingNextButton();
		ingestionPage.configureMandatoryMappingAndRunIngestion(Input.documentKey,Input.documentKey,Input.custodian);
		baseClass.stepInfo("Verify available option in settings");
		ingestionPage.verifyDetailsAfterStartedIngestion();
		ingestionPage.verifyOptionsAvailableInIngestionSetting();
		baseClass.stepInfo("Copy ingestion and check details");
		ingestionPage.copyIngestionAndVerifyDetailsRetained(Input.ingestionType,Input.documentKey);
		baseClass.stepInfo("verify warning message");
		ingestionPage.verifyWarningMessageForCurrentAndCopiedIngestion(false, Input.IngestionEmailDataFolder,
				Input.emailDatFile, Input.documentIdKey, Input.emailTextFile);
		baseClass.stepInfo("Fill the mandatory fields and run ingestion");
		ingestionPage.configureMandatoryMappingAndRunIngestion(Input.documentIdKey,Input.documentIdKey,Input.custIdKey);
		loginPage.logout();
		
		
	}
	
	/**
	 * Author :Arunkumar date: 21/07/2022 TestCase Id:RPMXCON-47585
	 * Description :To verify that on "Copy Ingestion" once the Configure mapping is matched 
	 * between Current and Copied Ingestion then Warning message should not displayed. 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47585",enabled = true, groups = { "regression" })
	public void verifyConfigureMappingWhenMappingMatched() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47585");
		baseClass.stepInfo("verify configure mapping warning message when mapping matched");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion and click next button");
		ingestionPage.sourceSelectionAndIngestionTypeSectionOnlyWithDATfile(Input.UniCodeFilesFolder,Input.datLoadFile1);
		baseClass.stepInfo("Configure mapping and run ingestion");
		ingestionPage.verifySourceSectionStatusAfterClickingNextButton();
		ingestionPage.configureMandatoryMappingAndRunIngestion(Input.documentKey,Input.documentKey,Input.custodian);
		baseClass.stepInfo("Verify available option in settings");
		ingestionPage.verifyDetailsAfterStartedIngestion();
		ingestionPage.verifyOptionsAvailableInIngestionSetting();
		baseClass.stepInfo("Copy ingestion and check details");
		ingestionPage.copyIngestionAndVerifyDetailsRetained(Input.ingestionType,Input.documentKey);
		baseClass.stepInfo("verify warning message");
		ingestionPage.verifyWarningMessageForCurrentAndCopiedIngestion(true, Input.UniCodeFilesFolder,
				Input.datLoadFile1,Input.documentKey , Input.textFile1);
		ingestionPage.clickOnPreviewAndRunButton();
		baseClass.passedStep("Verified that application will move ahead to run ingestion if mapping is matched");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 22/07/2022 TestCase Id:RPMXCON-47375
	 * Description :To verify that ingestion which is Rolled back can be deleted. 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47375",enabled = true, groups = { "regression" })
	public void verifyRollbackAndDeleteIngestion() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47375");
		baseClass.stepInfo("verify that ingestion which is Rolled back can be deleted");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.HiddenPropertiesFolder);
		if (status == false) {
		baseClass.stepInfo("Add new ingestion and Perform Catalogging");
		ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder, Input.YYYYMMDDHHMISSDat);
		ingestionPage.ignoreErrorsAndCatlogging();
		baseClass.stepInfo("Rollback and Delete ingestion using action dropdown menu");
		ingestionPage.performRollbackAndDeleteIngestion();
		baseClass.passedStep("Verified that ingestion which is rolledback can be deleted");
		}
		baseClass.passedStep("add only ingestion already present in this project");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 12/07/2022 TestCase Id:RPMXCON-48291
	 * Description :To verify In Ingestion, Copy ,ASCII(59) should be default New Line delimiter.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48291",enabled = true, groups = { "regression" })
	public void verifyDefaultNewLineDelimiter() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-48291");
		baseClass.stepInfo("verify In Ingestion, Copy ,ASCII(59) should be default New Line delimiter.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion with value ASCII(59)");
		boolean status = ingestionPage.verifyIngestionpublish(Input.UniCodeFilesFolder);
		System.out.println(status);
		if (status == false) {
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem, 
				Input.sourceLocation, Input.UniCodeFilesFolder);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Selecting Dat file");
		ingestionPage.selectDATSource(Input.datLoadFile1, Input.documentKey);
		baseClass.waitForElement(ingestionPage.getDateFormat());
		ingestionPage.getDateFormat().selectFromDropdown().selectByVisibleText(Input.dateFormat);
		ingestionPage.clickOnNextButton();
		ingestionPage.selectValueFromEnabledFirstThreeSourceDATFields(Input.documentKey,Input.documentKey,Input.custodian);
		ingestionPage.clickOnPreviewAndRunButton();
		baseClass.stepInfo("Perform catalog,copy and indexing");
		ingestionPage.ingestionCatalogging();
		ingestionPage.ingestionCopying();
		ingestionPage.ingestionIndexing(Input.UniCodeFilesFolder);
		baseClass.stepInfo("Copy the ingestion and check default value in ingestion wizard");
		driver.waitForPageToBeReady();
		ingestionPage.getIngestionDetailPopup(1).waitAndClick(5);
		baseClass.waitForElement(ingestionPage.getActionDropdownArrow());	
		ingestionPage.getActionDropdownArrow().waitAndClick(5);
		baseClass.waitForElement(ingestionPage.getActionCopy());
		ingestionPage.getActionCopy().waitAndClick(5);
		ingestionPage.verifyDefaultValueOfDelimiter();
		}
		loginPage.logout();
	}
	
	/** 
	 * Author :Arunkumar date: 12/07/2022 TestCase Id:RPMXCON-48626
	 * Description :To verify that after the publish is been done,user can view the search result for overlaid text
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48626",enabled = true, groups = { "regression" })
	public void verifyResultOfOverlaidTextAfterPublish() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48626");
		baseClass.stepInfo("verify that after the publish is been done,user can view the search result for overlaid text");
		docList= new DocListPage(driver);
		dataSets = new DataSets(driver);
		String BasicSearchName = "search"+Utility.dynamicNameAppender();
		String ingestionName = null;
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion with text files");
		boolean status = ingestionPage.verifyIngestionpublish(Input.UniCodeFilesFolder);
		System.out.println(status);
		if (status == false) {
		ingestionPage.unicodeFilesIngestion(Input.datLoadFile1, Input.textFile1, Input.documentKey);
		ingestionName =ingestionPage.publishAddonlyIngestion(Input.UniCodeFilesFolder);
		}
		else {
			ingestionName= ingestionPage.getPublishedIngestionName(Input.UniCodeFilesFolder);
		}
		baseClass.stepInfo("Search text file and save the result");
		sessionSearch.basicSearchWithMetaDataQuery(ingestionName, Input.metadataIngestion);
		sessionSearch.saveSearch(BasicSearchName);
		baseClass.stepInfo("Unpublish the text file");
		ingestionPage.navigateToUnPublishPage();
		ingestionPage.unpublish(BasicSearchName);
		baseClass.stepInfo("perform overlay ingestion with unpublished text files");
		ingestionPage.OverlayIngestionWithDat(Input.UniCodeFilesFolder, Input.datLoadFile1, Input.documentKey,
								"text", Input.textFile1);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();
		baseClass.stepInfo("search for overlaid text file");
		baseClass.selectproject();
		sessionSearch.basicSearchWithMetaDataQuery(ingestionName, Input.metadataIngestion);
		int Count =Integer.parseInt(sessionSearch.getPureHitsCount().getText());
		if(Count==5) {
			baseClass.passedStep("user can view the search result of text file after publish is done");
		}
		else {
			baseClass.failedStep("user unable to view the search result of text file after publish is done");
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 27/07/2022 TestCase Id:RPMXCON-46886
	 * Description :To Verify Ingestion rollback for Audio indexed & non Audio Indexed.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-46886",enabled = true, groups = { "regression" })
	public void verifyIngestionRollbackForAudio() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-46886");
		baseClass.stepInfo("Verify Ingestion rollback for Audio indexed & non Audio Indexed.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		System.out.println(status);
		if (status == false) {
		baseClass.stepInfo("perform add only ingestion");
		ingestionPage.performAutomationAllsourcesIngestion(Input.sourceSystem,Input.DATFile1, Input.prodBeg);
		ingestionPage.ignoreErrorsAndCatlogging();
		ingestionPage.ignoreErrorsAndCopying();
		baseClass.stepInfo("perform indexing without selecting audio");
		ingestionPage.startIndexing(false,null);
		ingestionPage.validateIndexingStatus();
		ingestionPage.rollBackIngestion();
		baseClass.stepInfo("perform add only ingestion till copying stage");
		ingestionPage.performAutomationAllsourcesIngestion(Input.sourceSystem,Input.DATFile1, Input.prodBeg);
		ingestionPage.ignoreErrorsAndCatlogging();
		ingestionPage.ignoreErrorsAndCopying();
		baseClass.stepInfo("perform indexing with audio");
		ingestionPage.ingestionIndexing(Input.AllSourcesFolder);
		ingestionPage.rollBackIngestion();
		baseClass.passedStep("Roll back performed successfully for both audio and non audio indexed");
		
		}
		else {
			baseClass.failedMessage("Add only ingestion for the dataset already present in the current project");;
		}
		loginPage.logout();
		}
	
	/**
	 * Author :Arunkumar date: 12/07/2022 TestCase Id:RPMXCON-47824
	 * Description :Verify overlay of the same files, which are already ingested and available in Production DB.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47824",enabled = true, groups = { "regression" })
	public void verifyOverlayIngestionOfSameFiles() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47824");
		baseClass.stepInfo("verify overlay ingestion of the same files, which are already ingested");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion and publish");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		System.out.println(status);
		if (status == false) {
		ingestionPage.performAutomationAllsourcesIngestion(Input.sourceSystem,Input.DATFile1, Input.prodBeg);
		ingestionPage.publishAddonlyIngestion(Input.AllSourcesFolder);
		}
		baseClass.stepInfo("Perform overlay ingestion for same files");
		ingestionPage.IngestionRegressionForDifferentDAT(Input.AllSourcesFolder,Input.overlayOnly,null,Input.DATFile1,
				Input.NativeFile,null,Input.PDFFile,Input.TIFFFile,null,null,null,null);
		baseClass.passedStep("Ingestion overlay done successfully for the same files");
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 15/07/2022 TestCase Id:RPMXCON-48595
	 * Description :Verify the Analytics process should take places when Audio files with Text files are overlayed.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48595",enabled = true, groups = { "regression" })
	public void verifyOverlayIngestionOfAudioWithTextFiles() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-48595");
		baseClass.stepInfo("Verify the Analytics process should take places when Audio files with Text files are overlayed.");
		String BasicSearchName = "search"+Utility.dynamicNameAppender();
		String ingestionName = null;
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion and publish");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		System.out.println(status);
		if (status == false) {
		ingestionPage.performAutomationAllsourcesIngestion(Input.sourceSystem,Input.DATFile1, Input.prodBeg);
		 ingestionName =ingestionPage.publishAddonlyIngestion(Input.AllSourcesFolder);
		}
		else {
			ingestionName= ingestionPage.getPublishedIngestionName(Input.AllSourcesFolder);
		}
		baseClass.stepInfo("Unpublish the text file");
		System.out.println(ingestionName);
		sessionSearch.basicSearchWithMetaDataQuery(ingestionName, Input.metadataIngestion);
		sessionSearch.saveSearch(BasicSearchName);
		ingestionPage.navigateToUnPublishPage();
		ingestionPage.unpublish(BasicSearchName);
		baseClass.stepInfo("Perform overlay ingestion with audio and text files");
		ingestionPage.startOverlayIngestion(Input.AllSourcesFolder, Input.DATFile1, Input.prodBeg, Input.TextFile, null,
				null,null, Input.MP3File, null, null, null, false);
		ingestionPage.approveOverlayonlyTextWithAudioIngestion(Input.AllSourcesFolder);
		ingestionPage.runAnalyticsAndVerifyAnalyticStatus();
		baseClass.passedStep("Analytics takes place when audio and Text files are overlayed");
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 15/07/2022 TestCase Id:RPMXCON-48596
	 * Description :Verify the Analytics process should take places when Tiff files and Text files are overlayed.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48596",enabled = true, groups = { "regression" })
	public void verifyOverlayIngestionOfTiffWithTextFiles() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-48596");
		baseClass.stepInfo("Verify the Analytics process should take places when Tiff files and Text files are overlayed.");
		String BasicSearchName = "search"+Utility.dynamicNameAppender();
		String ingestionName = null;
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion and publish");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		System.out.println(status);
		if (status == false) {
		ingestionPage.performAutomationAllsourcesIngestion(Input.sourceSystem,Input.DATFile1, Input.prodBeg);
		 ingestionName =ingestionPage.publishAddonlyIngestion(Input.AllSourcesFolder);
		}
		else {
			ingestionName= ingestionPage.getPublishedIngestionName(Input.AllSourcesFolder);
		}
		baseClass.stepInfo("Unpublish the text file");
		System.out.println(ingestionName);
		sessionSearch.basicSearchWithMetaDataQuery(ingestionName, Input.metadataIngestion);
		sessionSearch.saveSearch(BasicSearchName);
		ingestionPage.navigateToUnPublishPage();
		ingestionPage.unpublish(BasicSearchName);
		baseClass.stepInfo("Perform overlay ingestion with tiff and text files");
		ingestionPage.startOverlayIngestion(Input.AllSourcesFolder, Input.DATFile1, Input.prodBeg, Input.TextFile, null,
				null, Input.TIFFFile, null, null, null, null, false);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runAnalyticsAndVerifyAnalyticStatus();
		baseClass.passedStep("Analytics takes place when Tiff files and Text files are overlayed");
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 15/07/2022 TestCase Id:RPMXCON-48599
	 * Description :Verify the Analytics process should take places when Native files and Text files are overlayed.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48599",enabled = true, groups = { "regression" })
	public void verifyOverlayIngestionOfNativeWithTextFiles() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-48599");
		baseClass.stepInfo("Verify the Analytics process should take places when Native files and Text files are overlayed.");
		String BasicSearchName = "search"+Utility.dynamicNameAppender();
		String ingestionName = null;
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion and publish");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		System.out.println(status);
		if (status == false) {
		ingestionPage.performAutomationAllsourcesIngestion(Input.sourceSystem,Input.DATFile1, Input.prodBeg);
		 ingestionName =ingestionPage.publishAddonlyIngestion(Input.AllSourcesFolder);
		}
		else {
			ingestionName= ingestionPage.getPublishedIngestionName(Input.AllSourcesFolder);
		}
		baseClass.stepInfo("Unpublish the text file");
		System.out.println(ingestionName);
		sessionSearch.basicSearchWithMetaDataQuery(ingestionName, Input.metadataIngestion);
		sessionSearch.saveSearch(BasicSearchName);
		ingestionPage.navigateToUnPublishPage();
		ingestionPage.unpublish(BasicSearchName);
		baseClass.stepInfo("Perform overlay ingestion with native and text files");
		ingestionPage.startOverlayIngestion(Input.AllSourcesFolder, Input.DATFile1, Input.prodBeg, Input.TextFile, Input.NativeFile,
				null, null, null, null, null, null, false);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runAnalyticsAndVerifyAnalyticStatus();
		baseClass.passedStep("Analytics takes place when native and Text files are overlayed");
		loginPage.logout();
		
	}
	
	/** 
	 * Author :Arunkumar date: 18/07/2022 TestCase Id:RPMXCON-48629
	 * Description :To verify that user can overlay text only when all the docs being overlaid are unpublished
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48629",enabled = true, groups = { "regression" })
	public void verifyOverlayWhenDocsUnpublished() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48629");
		baseClass.stepInfo("verify that user can overlay text only when all the docs being overlaid are unpublished");
		docList= new DocListPage(driver);
		dataSets = new DataSets(driver);
		String BasicSearchName = "search"+Utility.dynamicNameAppender();
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion with text files");
		boolean status = ingestionPage.verifyIngestionpublish(Input.UniCodeFilesFolder);
		System.out.println(status);
		if (status == false) {
		ingestionPage.unicodeFilesIngestion(Input.datLoadFile1, Input.textFile1, Input.documentKey);
		ingestionPage.publishAddonlyIngestion(Input.UniCodeFilesFolder);
		}
		baseClass.stepInfo("Search text file and save the result");
		dataSets.navigateToDataSetsPage();
		dataSets.selectDataSetWithName(Input.UniCodeFilesFolder);
		String docId=docList.getDocumetId();
		sessionSearch.basicSearchWithMetaDataQuery(docId, Input.docId);
		sessionSearch.saveSearch(BasicSearchName);
		baseClass.stepInfo("Unpublish the text file");
		ingestionPage.navigateToUnPublishPage();
		ingestionPage.unpublish(BasicSearchName);
		baseClass.stepInfo("perform overlay ingestion with unpublished text files");
		ingestionPage.OverlayIngestionWithDat(Input.UniCodeFilesFolder, Input.datLoadFile1, Input.documentKey,
								"text", Input.textFile1);
		ingestionPage.verifyOverlayTextUnpublishedErrorMessage();
		baseClass.passedStep("user can overlay text only when all the docs being overlaid are unpublished");
		// rollback ingestion
		ingestionPage.rollBackIngestion();
		loginPage.logout();
	}
	
	/** 
	 * Author :Arunkumar date: 18/07/2022 TestCase Id:RPMXCON-49257
	 * Description :To verify that the total unique ingested docs count in the ingestions page should present the unique 
	 * number of docs ingested and published
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49257",enabled = true, groups = { "regression" })
	public void verifyTotalUniqueDocsCountIngestedAndPublished() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49257");
		baseClass.stepInfo("verify that ingestions page should present the unique number of docs ingested and published");
		String BasicSearchName = "search"+Utility.dynamicNameAppender();
		String ingestionName=null;
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion for dataset 1 - '"+Input.HiddenPropertiesFolder+"'");
		boolean status1 = ingestionPage.verifyIngestionpublish(Input.HiddenPropertiesFolder);
		if (status1 == false) {
			ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder,Input.YYYYMMDDHHMISSDat);
			ingestionName =ingestionPage.publishAddonlyIngestion(Input.HiddenPropertiesFolder);
		}
		else {
			ingestionName= ingestionPage.getPublishedIngestionName(Input.HiddenPropertiesFolder);
			ingestionPage.OverlayIngestionForDATWithMappingFieldSection(Input.HiddenPropertiesFolder, Input.YYYYMMDDHHMISSDat,
					Input.sourceDocIdSearch);
			ingestionPage.clickOnPreviewAndRunButton();
			ingestionPage.verifyApprovedStatusForOverlayIngestion();
			ingestionPage.runFullAnalysisAndPublish();
		}
		baseClass.stepInfo("Perform add only ingestion for dataset 2 - '"+Input.UniCodeFilesFolder+"'");
		ingestionPage.navigateToIngestionPage();
		boolean status2 = ingestionPage.verifyIngestionpublish(Input.UniCodeFilesFolder);
		if (status2 == false) {
			ingestionPage.IngestionOnlyForDatFile(Input.UniCodeFilesFolder,Input.datLoadFile1);
			ingestionPage.publishAddonlyIngestion(Input.UniCodeFilesFolder);
		}
		baseClass.stepInfo("Perform add only ingestion for dataset 3 - '"+Input.AllSourcesFolder+"'");
		ingestionPage.navigateToIngestionPage();
		boolean status3 = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		if (status3 == false) {
			ingestionPage.IngestionOnlyForDatFile(Input.AllSourcesFolder,Input.DATFile1);
			ingestionPage.publishAddonlyIngestion(Input.AllSourcesFolder);
		}
		
		// getting unique ingested count before unpublishing documents
		int uniqueCountBefore = ingestionPage.getIngestedUniqueCount();
		baseClass.stepInfo("Total unique count before performing unpublish : '" + uniqueCountBefore + "'");
		baseClass.stepInfo("Search documents and save the result");
		sessionSearch.basicSearchWithMetaDataQuery(ingestionName, Input.metadataIngestion);
		sessionSearch.saveSearch(BasicSearchName);
		baseClass.stepInfo("Unpublish documents");
		ingestionPage.navigateToUnPublishPage();
		ingestionPage.unpublish(BasicSearchName);
		// getting unique ingested count after unpublishing documents
		int uniqueCountAfter = ingestionPage.getIngestedUniqueCount();
		baseClass.stepInfo("Total unique count after performing unpublish : '" + uniqueCountAfter + "'");
		if (uniqueCountAfter < uniqueCountBefore) {
			baseClass.passedStep("Only unique ingested count displayed after unpublishing documents");
		} else {
			baseClass.failedStep("Unique ingested count not displayed after unpublishing documents");
		}
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 19/07/2022 TestCase Id:RPMXCON-48608
	 * Description :To verify that after a text overlay ingestion, the Ingestion Details popup page should reflect
	 * the count of text files copied under the Copying section
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48608",enabled = true, groups = { "regression" })
	public void verifyCountOfTextFilesWhenOverlay() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-48608");
		baseClass.stepInfo("Verify that ingestion detail popup reflect the count of text files.");
		String BasicSearchName = "search"+Utility.dynamicNameAppender();
		String ingestionName = null;
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion and publish");
		boolean status = ingestionPage.verifyIngestionpublish(Input.UniCodeFilesFolder);
		System.out.println(status);
		if (status == false) {
		ingestionPage.unicodeFilesIngestion(Input.datLoadFile1, Input.textFile1, Input.documentKey);
		 ingestionName =ingestionPage.publishAddonlyIngestion(Input.UniCodeFilesFolder);
		}
		else {
			ingestionName= ingestionPage.getPublishedIngestionName(Input.UniCodeFilesFolder);
		}
		baseClass.stepInfo("Unpublish the text files");
		sessionSearch.basicSearchWithMetaDataQuery(ingestionName, Input.metadataIngestion);
		sessionSearch.saveSearch(BasicSearchName);
		ingestionPage.navigateToUnPublishPage();
		ingestionPage.unpublish(BasicSearchName);
		baseClass.stepInfo("perform overlay ingestion");
		ingestionPage.OverlayIngestionWithDat(Input.UniCodeFilesFolder, Input.datLoadFile1, Input.documentKey,
				Input.text, Input.textFile1);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		baseClass.stepInfo("verify count of text file under copying section");
		ingestionPage.verifyDataPresentInCopyTableColumn(Input.text, Input.sourceDocs);
		baseClass.stepInfo("verify count of text file under indexing section");
		ingestionPage.verifyDataPresentInIndexTableColumn(Input.text, Input.sourceDocs);
		baseClass.stepInfo("verify incremental analytics option status");
		String incrementalAnalyticstatus = ingestionPage.verifyIncrementalAnalyticsStatus();
		if(incrementalAnalyticstatus.equalsIgnoreCase("true")) {
			baseClass.passedStep("Incremental analytics option disabled for performing text overlay");
		}
		else {
			baseClass.failedStep("Incremental analytics option enabled for text overlay");
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 19/07/2022 TestCase Id:RPMXCON-49019
	 * Description :Verify-Needs to be ingest 500 documents and do analytics these documents
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49019",enabled = true, groups = { "regression" })
	public void verifyPerformingIngestionAnalyticsFor500Docs() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-49019");
		baseClass.stepInfo("Verify ingesting 500 documents and do analytics these documents.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion and publish");
		boolean status = ingestionPage.verifyIngestionpublish(Input.Collection1KFolder);
		System.out.println(status);
		if (status == false) {
		ingestionPage.performCollection1kTallyIngestion(Input.sourceSystem,Input.datLoadFile3, Input.textFile1);
		ingestionPage.publishAddonlyIngestion(Input.Collection1KFolder);
		}
		baseClass.passedStep("Ingestion completed successfully for 500 documents without any errors");		
		loginPage.logout();
	}
	
	
	/**
	 * Author :Arunkumar date: 22/07/2022 TestCase Id:RPMXCON-47368
	 * Description :To verify that selection of Close button, redirects to Ingestion Home page
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47368",enabled = true, groups = { "regression" })
	public void verifyCloseButtonRedirectsToHomePage() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47368");
		baseClass.stepInfo("verify that selection of Close button redirects to Ingestion Home page");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Navigate to Ingestion home page");
		ingestionPage.navigateToIngestionPage();
		baseClass.waitForElement(ingestionPage.getFilterByButton());
		ingestionPage.getFilterByButton().waitAndClick(5);
		baseClass.waitForElement(ingestionPage.getFilterByPUBLISHED());
		ingestionPage.getFilterByPUBLISHED().waitAndClick(5);
		ingestionPage.getRefreshButton().waitAndClick(5);
		baseClass.stepInfo("Click on ingestion Name link and close button");
		ingestionPage.performClickOnIngestionNameLinkAndCloseButton();
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 27/07/2022 TestCase Id:RPMXCON-49331
	 * Description :verify that if Ingestion failed in Cataloging, then user can export the error details successfully
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49331",enabled = true, groups = { "regression" })
	public void verifyExportErrorDetails() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-49331");
		baseClass.stepInfo("Verify that if Ingestion failed in Cataloging, then user can export the error details successfully");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("perform add only ingestion");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		if (status == false) {
			ingestionPage.performAutomationAllsourcesIngestion(Input.sourceSystem,Input.DATFile1, Input.prodBeg);
			ingestionPage.publishAddonlyIngestion(Input.AllSourcesFolder);	
		}
		baseClass.stepInfo("Perform add only ingestion with NUIX source system");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType,Input.nuix, 
				Input.sourceLocation, Input.AllSourcesFolder);
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);
		baseClass.stepInfo("Selecting Dat file");
		ingestionPage.selectDATSource(Input.DATFile1,Input.prodBeg);
		baseClass.stepInfo("Selecting Native file");
		ingestionPage.selectNativeSource(Input.NativeFile, false);
		baseClass.stepInfo("Selecting Text file");
		ingestionPage.selectTextSource(Input.TextFile, false);
		baseClass.stepInfo("Selecting Pdf file");
		ingestionPage.selectPDFSource(Input.PDFFile, false);
		baseClass.stepInfo("Selecting Mp3 file");
		ingestionPage.selectMP3VarientSource(Input.MP3File, false);
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		ingestionPage.clickOnNextButton();
		ingestionPage.selectValueFromEnabledFirstThreeSourceDATFields(Input.prodBeg,Input.prodBeg,Input.custodian);
		ingestionPage.clickOnPreviewAndRunButton();
		ingestionPage.ingestionAtCatlogState(Input.AllSourcesFolder);
		baseClass.stepInfo("verify Export error details");
		ingestionPage.exportErrorDetails();
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 01/08/2022 TestCase Id:RPMXCON-47291
	 * Description :Single/Multiple DAT file is Available.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47291",enabled = true, groups = { "regression" })
	public void verifyValuesInIngestedDataset() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47291");
		baseClass.stepInfo("Single/Multiple DAT file is Available.");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.HiddenPropertiesFolder);
		if (status == false) {
			baseClass.stepInfo("Add new ingestion with dat file");
			ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder, Input.YYYYMMDDHHMISSDat);
			ingestionPage.ingestionCatalogging();
			baseClass.passedStep("Ingestion executed successfully");
			ingestionPage.rollBackIngestion();
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 02/08/2022 TestCase Id:RPMXCON-47580
	 * Description :To Verify: No of Headers in DAT and No. of headers in Destination field in Configure mapping page. 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47580",enabled = true, groups = { "regression" })
	public void verifyNumberOfHeaderInDatAndDestinationField() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47580");
		baseClass.stepInfo("Verify: No of Headers in DAT and No. of headers in Destination field in Configure mapping page.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion details and click on Next");
		ingestionPage.sourceSelectionAndIngestionTypeSectionOnlyWithDATfile(Input.HiddenPropertiesFolder,
				Input.YYYYMMDDHHMISSDat);
		ingestionPage.verifySourceSectionStatusAfterClickingNextButton(); 
		baseClass.stepInfo("verify number of headers in dat and destination field");  
		int numberOfHeaders = ingestionPage.configureMappingRows().size();
		if(ingestionPage.getMappingDestinationField(numberOfHeaders).isElementAvailable(10)) {
			baseClass.passedStep("Number of headers in dat file matches the destination field in configure mapping page");
		}
		else {
			baseClass.failedStep("Number of headers in dat file not matched with destination field");
		}
		loginPage.logout();	
	}
	
	/**
	 * Author :Arunkumar date: 03/08/2022 TestCase Id:RPMXCON-47302
	 * Description :Verify the Preview of Ingestion display for first 50 records with valid inputs. 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47302",enabled = true, groups = { "regression" })
	public void verifyIngestionRecordPreviewDetails() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47302");
		baseClass.stepInfo("Verify the Preview of Ingestion display for first 50 records with valid inputs.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion details and click on Next");
		ingestionPage.sourceSelectionAndIngestionTypeSectionOnlyWithDATfile(Input.AllSourcesFolder,Input.DATFile1);
		ingestionPage.verifySourceSectionStatusAfterClickingNextButton(); 
		ingestionPage.selectValueFromEnabledFirstThreeSourceDATFields(Input.prodBeg,Input.prodBeg,Input.custodian);
		baseClass.stepInfo("verify 50 record preview details in popup");
		ingestionPage.verifyHeaderCountInPreviewRecordPopupPage();
		ingestionPage.validateRecordsCountInIngestionPreviewScreen();
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 11/08/2022 TestCase Id:RPMXCON-63254
	 * Description :Validate whether the user gets the error message during "Overlay" ingestion 
	 * that contains Destination field name's that are mapped repeatedly. 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-63254",enabled = true, groups = { "regression" })
	public void verifyRepeatedDestinationFieldErrorWhenOverlay() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-63254");
		baseClass.stepInfo("Verify repeated destination field error during overlay only.");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform overlay ingestion");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.overlayOnly, Input.nuix, Input.sourceLocation, 
				"RPMXCON-61759");
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		ingestionPage.selectDATSource(Input.datFile5, Input.sourceDocIdSearch);
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		ingestionPage.clickOnNextButton();
		baseClass.stepInfo("Perform repeated mapping on destination field and verify");
		ingestionPage.performRepeatedMapppingOnConfiguringSection("destinationField");
		baseClass.passedStep("User gets the error message when mapping same destination field repeatedly for overlay only ingestion");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 11/08/2022 TestCase Id:RPMXCON-63255
	 * Description :Validate whether user do not get any error message during "Overlay" ingestion type when 
	 * similar source dat field are mapped twice with different destination dat field.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-63255",enabled = true, groups = { "regression" })
	public void verifyRepeatedSourceFieldErrorWhenOverlay() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-63255");
		baseClass.stepInfo("Verify saving ingestion when repeated source dat field mapping during overlay only.");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform overlay ingestion");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.overlayOnly, Input.nuix, Input.sourceLocation, "RPMXCON-61759");
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		ingestionPage.selectDATSource(Input.datFile5, Input.sourceDocIdSearch);
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		ingestionPage.clickOnNextButton();
		baseClass.stepInfo("Perform repeated mapping on source field and verify");
		ingestionPage.performRepeatedMapppingOnConfiguringSection("sourceField");
		baseClass.passedStep("User able to save ingestion successfully as draft when mapping same source field repeatedly");
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 11/08/2022 TestCase Id:RPMXCON-63256
	 * Description :Validate whether the user gets the error message during "Add ONLY" ingestion type that 
	 * contains Destination field name's that are mapped repeatedly.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-63256",enabled = true, groups = { "regression" })
	public void verifyRepeatedDestinationFieldErrorWhenAddOnly() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-63256");
		baseClass.stepInfo("Verify repeated destination field error during add only.");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.nuix, Input.sourceLocation, "RPMXCON-61759");
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		ingestionPage.selectDATSource(Input.datFile5, Input.sourceDocIdSearch);
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		ingestionPage.clickOnNextButton();
		baseClass.stepInfo("Perform repeated mapping on destination field and verify");
		ingestionPage.performRepeatedMapppingOnConfiguringSection("destinationField");
		baseClass.passedStep("User gets the error message when mapping same destination field repeatedly for Add only ingestion");
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 16/08/2022 TestCase Id:RPMXCON-47344
	 * Description :To verify that ingestion which is Rolled back can be deleted once it is in Draft mode. 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47344",enabled = true, groups = { "regression" })
	public void verifyDeletingIngestionInDraftMode() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47344");
		baseClass.stepInfo("verify that ingestion which is Rolled back can be deleted once it is in Draft mode");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.HiddenPropertiesFolder);
		if (status == false) {
		baseClass.stepInfo("perform add only ingestion");
		ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder, Input.YYYYMMDDHHMISSDat);
		ingestionPage.verifyDetailsAfterStartedIngestion();
		ingestionPage.ignoreErrorsAndCatlogging();
		baseClass.stepInfo("Click on settings and perform Rollback option");
		ingestionPage.rollBackIngestion();
		ingestionPage.VerifyDraftIngestionStatusAfterRollback();
		baseClass.stepInfo("verify available settings option");
		ingestionPage.verifyOptionsAvailableForDraftStageIngestion();
		baseClass.stepInfo("Delete ingestion");
		ingestionPage.getRefreshButton().waitAndClick(5);
		ingestionPage.deleteIngestion();
		baseClass.passedStep("Verified that user can delete the ingestion which is in draft mode");
		
		}
		loginPage.logout();
	}
	
	
	
	/**
	 * Author :Arunkumar date: 11/08/2022  TestCase Id:RPMXCON-48958 
	 * Description :Verify user should be able to run couple of new ingestion simultaneously and make sure no Indexing is failed
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48958",enabled = true, groups = { "regression" } )
	public void verifyCoupleOfIngestionsRunSimultaneously() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-48958");
		baseClass.stepInfo("Verify user should be able to run couple of new ingestion simultaneously");
		String[] dataset = { Input.UniCodeFilesFolder, Input.HiddenPropertiesFolder };

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion");
		boolean status1 = ingestionPage.verifyIngestionpublish(Input.UniCodeFilesFolder);
		ingestionPage.navigateToIngestionPage();
		boolean status2 = ingestionPage.verifyIngestionpublish(Input.HiddenPropertiesFolder);
		if (status1 == false && status2 == false) {
			ingestionPage.unicodeFilesIngestion(Input.datLoadFile1, Input.textFile1, Input.documentKey);
			driver.waitForPageToBeReady();
			ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder, Input.YYYYMMDDHHMISSDat);
			ingestionPage.multipleIngestionCopying(2);
			ingestionPage.multipleIngestionIndexing(dataset, 2);
			ingestionPage.approveMultipleIngestion(2);
			ingestionPage.runFullAnalysisAndPublish();
		}
		else {
			baseClass.failedMessage("Required Dataset already ingested in this project,unable to ingest again simultaneously");
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 17/08/2022 TestCase Id:RPMXCON-48071
	 * Description :To Verify Field ParentDocID ,HeadOfHouseholdDocID and FamilyID in Ingested Data Set.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48071",enabled = true, groups = { "regression" })
	public void verifyFieldValuesInIngestedDataset() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48071");
		baseClass.stepInfo("Verify fields in ingested dataset.");
		String[] values = {"ParentDocID","HeadOfHouseholdDocID","FamilyID"};
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.HiddenPropertiesFolder);
		if (status == false) {
			ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder, Input.YYYYMMDDHHMISSDat);
			ingestionPage.publishAddonlyIngestion(Input.HiddenPropertiesFolder);
		}
		baseClass.stepInfo("Perform search and go to doclist");
		int docsCount=sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();
		baseClass.stepInfo("Verify values displayed in selected column");
		ingestionPage.verifyValuesDisplayedInSelectedColumnsDoclist(docsCount,values);
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 01/08/2022 TestCase Id:RPMXCON-49661
	 * Description :Validate Add Only ingestion with DAT file in ASCII format
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49661",enabled = true, groups = { "regression" })
	public void verifyAddOnlyIngestionInASCII() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49661");
		baseClass.stepInfo("Validate Add Only ingestion with DAT file in ASCII format.");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		if (status == false) {
			baseClass.stepInfo("Perform add only ingestion with dat file in ASCII format");
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem, 
					Input.sourceLocation, Input.AllSourcesFolder);
			baseClass.stepInfo("Selecting Dat file");
			ingestionPage.selectDATSource(Input.datFileASCII, Input.prodBeg);
			baseClass.waitForElement(ingestionPage.getDateFormat());
			ingestionPage.getDateFormat().selectFromDropdown().selectByVisibleText(Input.dateFormat);
			ingestionPage.clickOnNextButton();
			ingestionPage.configureMandatoryMappingAndRunIngestion(Input.prodBeg,Input.prodBeg,Input.custodian);
			ingestionPage.publishAddonlyIngestion(Input.AllSourcesFolder);
			baseClass.passedStep("Add only ingestion published successfully with dat file in ASCII format");
		}
		else {
			baseClass.failedMessage("Add only ingestion already present in this project");
		}
		loginPage.logout();
		
	}
	/**
	 * Author :Arunkumar date: 11/08/2022 TestCase Id:RPMXCON-48003
	 * Description :To Verify email metadata field is populated correctly for ingested data 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48003",enabled = true, groups = { "regression" })
	public void verifyEmailMetaDataField() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-48003");
		baseClass.stepInfo("To Verify email metadata field is populated correctly for ingested data");
		String ingestionName = null;
		String[] values = {"EmailAllDomainCount","EmailAllDomains","EmailAuthorDomain","EmailRecipientNames",
				"EmailToAddresses","EmailToNames" , "EmailRecipientDomainCount"};
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
		if (status == false) {
			ingestionPage.performGD_994NativeFolderIngestion(Input.sourceSystem,Input.datLoadFile2,Input.nativeLoadFile2,Input.textLoadFile2);
			ingestionName=ingestionPage.publishAddonlyIngestion(Input.GD994NativeTextForProductionFolder);
		}
		else {
			ingestionName= ingestionPage.getPublishedIngestionName(Input.GD994NativeTextForProductionFolder);
		}
		baseClass.stepInfo("Search document and go to doclist");
		sessionSearch.basicSearchWithMetaDataQuery(ingestionName, Input.metadataIngestion);
		sessionSearch.ViewInDocList();
		docList = new DocListPage(driver);
		baseClass.stepInfo("Verify values displayed on the selected Coulumns");
		docList.SelectColumnDisplayByRemovingAddNewValues(values);
		for(int j =4;j<=values.length+4;j++) {
			String data =docList.getDataInDoclist(1,j).getText();
			if(docList.getDataInDoclist(1,j).isDisplayed() && !data.isEmpty()) {
				baseClass.passedStep("values displayed in selected column");
			}
			else {
				for(int i=2;i<=values.length;i++) {
					String datavalue =docList.getDataInDoclist(i,j).getText();
					if(docList.getDataInDoclist(i,j).isDisplayed() && !datavalue.isEmpty()) {
						baseClass.passedStep("values displayed in selected column");
						break;
					}
				}
			}
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 17/08/2022 TestCase Id:RPMXCON-48002
	 * Description :To Verify EmailDuplicateDocID field is populated correctly for ingested data
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48002",enabled = true, groups = { "regression" })
	public void verifyEmailMetaDataFieldPopulatedForIngestedData() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-48002");
		baseClass.stepInfo("To Verify EmailDuplicateDocID field is populated correctly for ingested data");
		String ingestionName = null;
		String[] values = {"EmailDuplicateDocIDs", "EmailIsDuplicate"};
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.Collection1KFolder);
		if (status == false) {
			ingestionPage.performAutomationCollection1kIngestion(Input.sourceSystem,Input.DATFile2,Input.textFile1);
			ingestionName=ingestionPage.publishAddonlyIngestion(Input.Collection1KFolder);
		}
		else {
			ingestionName= ingestionPage.getPublishedIngestionName(Input.Collection1KFolder);
		}
		baseClass.stepInfo("Search document and go to doclist");
		int docsCount=sessionSearch.MetaDataSearchInBasicSearch(Input.metadataIngestion,ingestionName);
		sessionSearch.ViewInDocList();
		baseClass.stepInfo("Verify values displayed in selected column");
		ingestionPage.verifyValuesDisplayedInSelectedColumnsDoclist(docsCount,values);
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 22/08/2022 TestCase Id:RPMXCON-47370
	 * Description :To verify that user is not able to search the Ingested data if Ingestion is in Draft Mode.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47370",enabled = true, groups = { "regression" })
	public void verifySearchIngestionWhichIsInDraftMode() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47370");
		baseClass.stepInfo("verify user is not able to search the data if Ingestion is in Draft Mode.");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem, 
				Input.sourceLocation, Input.HiddenPropertiesFolder);
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		ingestionPage.selectDATSource(Input.YYYYMMDDHHMISSDat, Input.sourceDocIdSearch);
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		ingestionPage.clickOnNextButton();
		baseClass.stepInfo("save ingestion as draft");
		ingestionPage.verifyIngestionStatusAfterSaveAsDraft();
		String ingestionName =ingestionPage.getIngestionNameFromPopup();
		baseClass.stepInfo("search ingestion data which is in draft mode");
		int docsCount=sessionSearch.MetaDataSearchInBasicSearch(Input.metadataIngestion,ingestionName);
		if(docsCount==0) {
			baseClass.passedStep("User unable to search the ingestion data which is in draft mode");
		}
		else {
			baseClass.failedStep("user able to search/view the ingestion data in draft mode");
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 22/08/2022 TestCase Id:RPMXCON-48079
	 * Description :To Verify HiddenProperties in Tally and Search
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48079",enabled = true, groups = { "regression" })
	public void verifyHiddenPropertiesInTallyAndSearch() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48079");
		baseClass.stepInfo("Verify HiddenProperties in Tally and Search.");
		String field = "HiddenProperties";
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.HiddenPropertiesFolder);
		if (status == false) {
			baseClass.stepInfo("perform add only ingestion for hiddenproperties");
			ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder, Input.YYYYMMDDHHMISSDat);
			ingestionPage.runFullAnalysisAndPublish();
		}
		baseClass.stepInfo("Verify the default field value of 'HiddenProperties'");
		ProjectFieldsPage projectFieldPage = new ProjectFieldsPage(driver);
		projectFieldPage.applyFilterByFilterName(field);
		projectFieldPage.verifyFieldStatus(field);
		projectFieldPage.verifyFieldClassification(field, Input.docBasic);
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 24/08/2022 TestCase Id:RPMXCON-49773
	 * Description :Verify concatenated email value should be displayed correctly for CCName and CCAddress 
	 * fields in Doc List 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49773",enabled = true, groups = { "regression" })
	public void verifyConcatenatedValueForCCField() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-49773");
		baseClass.stepInfo("Verify concatenated email value displayed in doclist");
		String ingestionName = null;
		String[] values = {"EmailCCAddresses","EmailCCNames","EmailCCNamesAndAddresses"};
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
		if (status == false) {
			ingestionPage.performGD_994NativeFolderIngestion(Input.sourceSystem,Input.datLoadFile4,Input.nativeLoadFile3,
					Input.textLoadFile3);
			ingestionName=ingestionPage.publishAddonlyIngestion(Input.GD994NativeTextForProductionFolder);
		}
		else {
			ingestionName= ingestionPage.getPublishedIngestionName(Input.GD994NativeTextForProductionFolder);
		}
		baseClass.stepInfo("Click dataset and go to doclist");
		dataSets = new DataSets(driver);
		dataSets.selectDataSetWithName(ingestionName);
		baseClass.stepInfo("verify concatenated email values in doclist");
		ingestionPage.verifyConcatenatedEmailValueDisplayed(values);
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 24/08/2022 TestCase Id:RPMXCON-49774
	 * Description :Verify concatenated email value should be displayed correctly for 
	 * BCCName and BCCAddress fields in Doc List 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49774",enabled = true, groups = { "regression" })
	public void verifyConcatenatedValueForBCCField() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-49774");
		baseClass.stepInfo("Verify concatenated email value displayed in doclist");
		String ingestionName = null;
		String[] values = {"EmailBCCAddresses","EmailBCCNames","EmailBCCNamesAndAddresses"};
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
		if (status == false) {
			ingestionPage.performGD_994NativeFolderIngestion(Input.sourceSystem,Input.datLoadFile4,Input.nativeLoadFile3,
					Input.textLoadFile3);
			ingestionName=ingestionPage.publishAddonlyIngestion(Input.GD994NativeTextForProductionFolder);
		}
		else {
			ingestionName= ingestionPage.getPublishedIngestionName(Input.GD994NativeTextForProductionFolder);
		}
		baseClass.stepInfo("Click dataset and go to doclist");
		dataSets = new DataSets(driver);
		dataSets.selectDataSetWithName(ingestionName);
		baseClass.stepInfo("verify concatenated email values in doclist");
		ingestionPage.verifyConcatenatedEmailValueDisplayed(values);
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 24/08/2022 TestCase Id:RPMXCON-49775
	 * Description :Verify concatenated email value should be displayed correctly for ToName and ToAddress 
	 * fields in Doc List 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49775",enabled = true, groups = { "regression" })
	public void verifyConcatenatedValueForToField() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-49775");
		baseClass.stepInfo("Verify concatenated email value displayed in doclist");
		String ingestionName = null;
		String[] values = {"EmailToAddresses","EmailToNames","EmailToNamesAndAddresses"};
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
		if (status == false) {
			ingestionPage.performGD_994NativeFolderIngestion(Input.sourceSystem,Input.datLoadFile4,Input.nativeLoadFile3,
					Input.textLoadFile3);
			ingestionName=ingestionPage.publishAddonlyIngestion(Input.GD994NativeTextForProductionFolder);
		}
		else {
			ingestionName= ingestionPage.getPublishedIngestionName(Input.GD994NativeTextForProductionFolder);
		}
		baseClass.stepInfo("Click dataset and go to doclist");
		dataSets = new DataSets(driver);
		dataSets.selectDataSetWithName(ingestionName);
		baseClass.stepInfo("verify concatenated email values in doclist");
		ingestionPage.verifyConcatenatedEmailValueDisplayed(values);
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 24/08/2022 TestCase Id:RPMXCON-49776
	 * Description :Verify concatenated email value should be displayed correctly for AuthorName and AuthorAddress 
	 * fields in Doc List 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49776",enabled = true, groups = { "regression" })
	public void verifyConcatenatedValueForAuthorField() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-49776");
		baseClass.stepInfo("Verify concatenated email value displayed in doclist");
		String ingestionName = null;
		String[] values = {"EmailAuthorAddress","EmailAuthorName","EmailAuthorNameAndAddress"};
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
		if (status == false) {
			ingestionPage.performGD_994NativeFolderIngestion(Input.sourceSystem,Input.datLoadFile4,Input.nativeLoadFile3,
					Input.textLoadFile3);
			ingestionName=ingestionPage.publishAddonlyIngestion(Input.GD994NativeTextForProductionFolder);
		}
		else {
			ingestionName= ingestionPage.getPublishedIngestionName(Input.GD994NativeTextForProductionFolder);
		}
		baseClass.stepInfo("Click dataset and go to doclist");
		dataSets = new DataSets(driver);
		dataSets.selectDataSetWithName(ingestionName);
		baseClass.stepInfo("verify concatenated email values in doclist");
		ingestionPage.verifyConcatenatedEmailValueDisplayed(values);
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 29/08/2022 TestCase Id:RPMXCON-47825
	 * Description :overlay of the different files, keeping the same Unique id, 
	 * which are already ingested and available in Production DB.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47825",enabled = true, groups = { "regression" })
	public void verifyOverlayOfDifferentFiles() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47825");
		baseClass.stepInfo("Verify overlay of different files with same unique id");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("perform add only ingestion with Native and Tiff");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		if (status == false) {
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem, 
					Input.sourceLocation, Input.AllSourcesFolder);
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);
			baseClass.stepInfo("Selecting Dat file");
			ingestionPage.selectDATSource(Input.DATFile1,Input.prodBeg );
			baseClass.stepInfo("Selecting Native file");
			ingestionPage.selectNativeSource(Input.NativeFile, false);		
			baseClass.stepInfo("Selecting Tiff file");
			ingestionPage.selectTIFFSource(Input.TIFFFile, false,false);
			ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
			ingestionPage.clickOnNextButton();
			ingestionPage.selectValueFromEnabledFirstThreeSourceDATFields(Input.prodBeg, Input.prodBeg, Input.custodian);
			ingestionPage.clickOnPreviewAndRunButton();
			ingestionPage.publishAddonlyIngestion(Input.AllSourcesFolder);
		}
		baseClass.stepInfo("perform overlay ingestion with different files");
		ingestionPage.startOverlayIngestion(Input.AllSourcesFolder, Input.DATFile1, Input.prodBeg, null, null, 
				Input.PDFFile, null, null, Input.TranscriptFile, null, null, false);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();
		baseClass.passedStep("Able to perform overlay for different files with same unique id");
		loginPage.logout();
	}
	
	
	/**
	 * Author :Arunkumar date: 29/08/2022 TestCase Id:RPMXCON-46885
	 * Description :To Verify audio indexing for audio documents when audio indexing option is selected. 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-46885",enabled = true, groups = { "regression" })
	public void verifyAudioIndexingForAudioDocs() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-46885");
		baseClass.stepInfo("Verify audio indexing for audio documents when audio indexing option is selected");
		String ingestionName = null;
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("perform add only ingestion");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		if (status == false) {
			ingestionPage.performAutomationAllsourcesIngestion(Input.sourceSystem,Input.DATFile1, Input.prodBeg);
			ingestionName=ingestionPage.publishAddonlyIngestion(Input.AllSourcesFolder);
		}
		else {
			ingestionName= ingestionPage.getPublishedIngestionName(Input.AllSourcesFolder);
		}
		ingestionPage.OpenIngestionDetailPopupForPublishedFilter(ingestionName);
		driver.scrollingToBottomofAPage();
		baseClass.waitForElement(ingestionPage.getRunIndexing());
		int mp3Count = Integer.parseInt(ingestionPage.indexTableDataValue(Input.audio, 1).getText());
		ingestionPage.getCloseButton().waitAndClick(10);
		baseClass.stepInfo("Perform search for AudioPlayerReady");
		int count =sessionSearch.MetaDataSearchInBasicSearch(Input.audioPlayerReady,"1");
		if(count==mp3Count || count==0) {
			baseClass.passedStep("search count mapped with the number of docs in mp3 file variant");
		}
		else {
			baseClass.failedStep("Count not mapped");
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 01/09/2022 TestCase Id:RPMXCON-47295
	 * Description :New Ingestion with Overwrite option as 'Add Only' 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47295",enabled = true, groups = { "regression" })
	public void verifyPerformingNewAddOnlyIngestion() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47295");
		baseClass.stepInfo("New Ingestion with Overwrite option as 'Add Only'");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion with overwrite option as 'Add only'.");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		if (status == false) {
			ingestionPage.performAutomationAllsourcesIngestion(Input.sourceSystem,Input.DATFile1, Input.prodBeg);
			baseClass.stepInfo("Perform post ingestion validation steps");
			ingestionPage.verifyDetailsAfterStartedIngestion();
			String ingestionName=ingestionPage.getIngestionNameFromPopup();
			String modifiedDate =ingestionPage.getIngestionWizardDateFormat().getText();
			ingestionPage.postIngestionGridViewValidation(ingestionName, Input.projectName, 
					Input.pa1FullName, "Cataloged",modifiedDate);
			baseClass.stepInfo("Perform Catalogging and verify status");
			driver.Navigate().refresh();
			driver.waitForPageToBeReady();
			ingestionPage.ignoreErrorsAndCatlogging();
			ingestionPage.verifyStatusInTileAndIngestionDetailLink("Catalog stage");
			baseClass.stepInfo("Perform Copy and verify status");
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.verifyStatusInTileAndIngestionDetailLink("Copy stage");
			baseClass.stepInfo("Perform Indexing and verify status");
			ingestionPage.ignoreErrorsAndIndexing(Input.AllSourcesFolder);
			ingestionPage.verifyStatusInTileAndIngestionDetailLink("Indexing stage");
			baseClass.stepInfo("Perform Approve and verify status");
			ingestionPage.approveIngestion(1);
			ingestionPage.verifyStatusInTileAndIngestionDetailLink("Approve stage");
			ingestionPage.runFullAnalysisAndPublish();
		}
		else {
			baseClass.passedStep("Add only ingestion already published in this project");
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 02/09/2022 TestCase Id:RPMXCON-48257
	 * Description :To Verify Unpublish for Ingested audio documents
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48257",enabled = true, groups = { "regression" })
	public void verifyUnpublishForAudioIngestedDocs() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48257");
		baseClass.stepInfo("To Verify Unpublish for Ingested audio documents");
		String BasicSearchName = "search"+Utility.dynamicNameAppender();
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("perform add only ingestion and publish");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AK_NativeFolder);
		if (status == false) {
			ingestionPage.performAKNativeFolderIngestion(Input.DATFile1);
			ingestionPage.publishAddonlyIngestion(Input.AK_NativeFolder);
		}
		baseClass.stepInfo("Release all ingested docs");
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.bulkRelease(Input.securityGroup);
		baseClass.stepInfo("do basic search and save");
		baseClass.selectproject();
		sessionSearch.basicSearchWithMetaDataQuery("1", Input.audioPlayerReady);
		sessionSearch.saveSearch(BasicSearchName);
		baseClass.stepInfo("unrelease and unpublish documents");
		ingestionPage.unpublish(BasicSearchName);
		baseClass.passedStep("Documents unpublished successfully without error message");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 09/09/2022 TestCase Id:RPMXCON-63253 Description
	 * :Validate whether all the DAT fields from DAT File selected during "Over lay"
	 * ingestion is displayed under Source DAT fields in Configure Field Mapping of
	 * Ingestion Wizard Page.
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-63253", enabled = true, groups = { "regression" })
	public void verifyRepeatedDestinationFieldErrorsWhenOverlay() throws InterruptedException {

		String[] ExpectedDatFields = { "DocID", "RecBegAttach", "RecEndAttach", "ProdBeg", "ProdEnd", "ProdBegAttach",
				"ProdEndAttach", "AttachCount", "DocPages", "Custodian", "Date", "MasterDate", "Author",
				"AuthorAddress", "RecipientName", "RecipientAddress", "CCName", "CCAddress", "BCCName", "BCCAddress",
				"Subject", "Container", "DataSet", "Datasource", "FullPath", "NativeLink", "FileType", "FileName",
				"FileExt", "FileSize", "M_Duration", "ReceivedDate", "ApptSTDate", "ApptEndDate", "MessageID",
				"MessageType", "CreateDate", "LastAccDate", "LastEditDate", "LastSaveDate", "LastPrintedDate",
				"MD5Hash", "Title", "Comments", "Company", "Keywords", "Manager", "Category", "RevNumber",
				"EmailCCNameAndAddress", "EmailBCCNameAndAddress", "EmailAuthorNameandAddress", "DocumentSubject",
				"EmailConversationIndex", "EmailFamilyConversationIndex", "EmailToNameAndAddress", "Native Path",
				"Extracted Text", "FamilyVirusStatus", "ProcessingPlaceholders", "ZzrecipientsID", "ZzsenderID" };

		baseClass.stepInfo("Test case Id: RPMXCON-63253");
		baseClass.stepInfo("Verify DAT fields in configure mapping page.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion with type as 'Overlay'");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.overlayOnly, Input.nuix, Input.sourceLocation,
				Input.folder61759);
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);
		ingestionPage.selectDATSource(Input.datFile6, Input.prodBeg);
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		ingestionPage.clickOnNextButton();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify dat fields available in source field mapping in configuring page");
		baseClass.waitForElement(ingestionPage.getMappingSourceField(2));
		for (int i = 2; i < ExpectedDatFields.length; i++) {
			ingestionPage.getMappingSourceField(i).selectFromDropdown().selectByVisibleText(ExpectedDatFields[i]);
			String field = ingestionPage.getMappingSourceField(i).selectFromDropdown().getFirstSelectedOption()
					.getText();
			if (field.equalsIgnoreCase(ExpectedDatFields[i])) {
				baseClass.passedStep("Dat field available :" + ExpectedDatFields[i]);
			} else {
				baseClass.failedStep("Dat field not available" + ExpectedDatFields[i]);
			}
		}
		baseClass.passedStep("All the DAT fields available in configure mapping page");
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: 13/09/2022 TestCase Id:RPMXCON-48420 Description :To
	 * Verify Media Overlay Ingestion (with Unpublish).
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-48420", enabled = true, groups = { "regression" })
	public void verifyMediaOverlayIngestion() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48420");
		baseClass.stepInfo("To Verify Media Overlay Ingestion (with Unpublish).");
		String ingestionName = null;
		String BasicSearchName = "search" + Utility.dynamicNameAppender();
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.passedStep("perform add only ingestion without media indexing");
		boolean status = ingestionPage.verifyIngestionpublish(Input.audio96DocsFolder);
		if (status == false) {
			ingestionPage.performAudio96DocsIngestion(Input.audioDatFile, Input.docIdKey);
			ingestionName = ingestionPage.publishAddonlyIngestion(Input.audio96DocsFolder);
		} else {
			ingestionName = ingestionPage.getPublishedIngestionName(Input.audio96DocsFolder);
		}
		baseClass.stepInfo("unpublish docs");
		sessionSearch.MetaDataSearchInBasicSearch(Input.metadataIngestion, ingestionName);
		sessionSearch.saveSearch(BasicSearchName);
		ingestionPage.unpublish(BasicSearchName);
		baseClass.stepInfo("Perform overlay ingestion without DAT");
		ingestionPage.OverlayIngestionWithoutDat(Input.audio96DocsFolder, "mp3", Input.selectMp3File);
		ingestionPage.ignoreErrorsAndCatlogging();
		ingestionPage.ignoreErrorsAndCopying();
		baseClass.stepInfo("perform media indexing with language pack");
		ingestionPage.startIndexing(true, Input.language);
		ingestionPage.validateIndexingStatus();
		ingestionPage.approveIngestion(1);
		ingestionPage.runFullAnalysisAndPublish();
		baseClass.stepInfo("Perform overlay ingestion with DAT and language pack");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.overlayOnly, null, Input.sourceLocation,
				Input.audio96DocsFolder);
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);
		baseClass.stepInfo("Selecting Dat and mp3 file");
		ingestionPage.selectDATSource(Input.audioDatFile, Input.docIdKey);
		ingestionPage.selectMP3VarientSource(Input.selectMp3File, false);
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		ingestionPage.clickOnNextButton();
		ingestionPage.performMappingInConfigureSection(2, Input.docIdKey, Input.ingDocBasic, Input.sourceParentDocId);
		ingestionPage.clickOnPreviewAndRunButton();
		ingestionPage.ignoreErrorsAndCatlogging();
		ingestionPage.ignoreErrorsAndCopying();
		ingestionPage.startIndexing(true, Input.language);
		ingestionPage.validateIndexingStatus();
		ingestionPage.approveIngestion(1);
		ingestionPage.runFullAnalysisAndPublish();
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: 12/09/2022 TestCase Id:RPMXCON-49357 Description :To
	 * verify that Overlay should work for 'SourceParentDocID' metadata.
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-49357", enabled = true, groups = { "regression" })
	public void verifyIngestionOverlayForDifferentMetadata() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49357");
		baseClass.stepInfo("To verify that Overlay should work for  'SourceParentDocID' metadata");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.passedStep("perform add only ingestion");
		boolean status = ingestionPage.verifyIngestionpublish(Input.attachDocFolder);
		if (status == false) {
			baseClass.stepInfo("Add new ingestion");
			ingestionPage.IngestionOnlyForDatFile(Input.attachDocFolder, Input.datFile7);
			ingestionPage.publishAddonlyIngestion(Input.attachDocFolder);
		}
		baseClass.stepInfo("Perform overlay ingestion with 'SourceParentDocID'");
		ingestionPage.OverlayIngestionForDATWithMappingFieldSection(Input.attachDocFolder, Input.datFile7,
				Input.sourceDocIdSearch);
		if (ingestionPage.sourceFieldOption(1, Input.sourceParentDocId).isElementAvailable(10)) {
			baseClass.passedStep("metadata 'SourceParentDocID' available for overlay");
		} else {
			baseClass.failedStep("metadata not available for overlay");
		}
		ingestionPage.clickOnPreviewAndRunButton();
		driver.waitForPageToBeReady();
		ingestionPage.ignoreErrorsAndCatlogging();
		ingestionPage.ignoreErrorsAndCopying();
		ingestionPage.ingestionIndexing(Input.attachDocFolder);
		ingestionPage.approveIngestion(1);
		ingestionPage.runFullAnalysisAndPublish();
		baseClass.passedStep("Ingestion overlay performed successfully");
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: 14/09/2022 TestCase Id:RPMXCON-47155 Description :New
	 * Project - Ingestion audio documents, verify language packs and audio search
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-47155", enabled = true, groups = { "regression" })
	public void verifyAudioDocsSearchIngestion() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47155");
		baseClass.stepInfo("New Project -Ingestion audio documents, verify language packs and audio search");
		String[] audioSearch = { Input.audioSearchString1, Input.audioSearchString2 };
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.passedStep("perform audio ingestion and publish docs");
		boolean status = ingestionPage.verifyIngestionpublish(Input.audio96DocsFolder);
		if (status == false) {
			ingestionPage.performAudio96DocsIngestion(Input.audioDatFile, Input.docIdKey);
			driver.waitForPageToBeReady();
			ingestionPage.ignoreErrorsAndCatlogging();
			ingestionPage.ignoreErrorsAndCopying();
			baseClass.stepInfo("verify availability of language pack");
			ingestionPage.verifyAvailableLanguagePack();
			ingestionPage.ignoreErrorsAndIndexing(Input.audio96DocsFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();

		}
		baseClass.stepInfo("Execute few audio searches");
		for (int i = 0; i < audioSearch.length; i++) {
			int docsCount = sessionSearch.audioSearch(audioSearch[i], Input.language);
			sessionSearch.verifySearchResultReturnsForConfiguredQuery(docsCount);
			baseClass.selectproject();
		}
		baseClass.passedStep("Audio searches returned results");
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: 14/09/2022 TestCase Id:RPMXCON-48199 Description :To
	 * Verify Ingestion Overlays of DAT without unpublish.
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-48199", enabled = true, groups = { "regression" })
	public void verifyIngestionOverlaywithoutUnpublish() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48199");
		baseClass.stepInfo("To Verify Ingestion Overlays of DAT without unpublish.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion with overwrite option as 'Add only'.");
		boolean status = ingestionPage.verifyIngestionpublish(Input.Collection1KFolder);
		if (status == false) {
			ingestionPage.performCollection1kTallyIngestion(Input.sourceSystem, Input.datLoadFile3, Input.textFile1);
			ingestionPage.publishAddonlyIngestion(Input.Collection1KFolder);
		}
		baseClass.stepInfo("Perform overlay ingestion with modified dat file");
		ingestionPage.OverlayIngestionForDATWithMappingFieldSection(Input.Collection1KFolder, Input.overlayDatFile,
				Input.docId);
		ingestionPage.clickOnPreviewAndRunButton();
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();
		baseClass.passedStep("Overlay ingestion performed successfully without unpublish");
		baseClass.stepInfo("Search for custodian name docs");
		baseClass.selectproject();
		int docsCount = sessionSearch.MetaDataSearchInBasicSearch(Input.metaDataName, Input.custodianName_allen);
		sessionSearch.verifySearchResultReturnsForConfiguredQuery(docsCount);
		baseClass.passedStep("Able to perform overlay without unpublish");
		loginPage.logout();

	}

	/**
	 * Author :Arunkumar date: 28/09/2022 TestCase Id:RPMXCON-60860 Description
	 * :Check whether user is able to ignore the error obtained at cataloged stage
	 * while ingesting 'Dat' file containing Splitted fullpath(folder name) data
	 * having more than 400 chars in size
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-60860", enabled = true, groups = { "regression" })
	public void verifyDatIngestionMoreThan400Chars() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-60860");
		baseClass.stepInfo("Verify ingestion Dat file data having more than 400 chars.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("perform add only ingestion with more than 400 char dat");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AutomationAllSources);
		if (status == false) {
			ingestionPage.IngestionOnlyForDatFile(Input.AutomationAllSources, Input.char400Dat);
			baseClass.stepInfo("Ignore catalog error message and verify error");
			ingestionPage.ignoreErrorsAndCatlogging();
			ingestionPage.verifyCatalogedIngestionErrorMessage(Input.char400Error);
			baseClass.stepInfo("Perform copy,indexing and approve ingestion");
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ignoreErrorsAndIndexing(Input.AutomationAllSources);
			ingestionPage.approveIngestion(1);
			baseClass.stepInfo("Publish ingestion");
			ingestionPage.runFullAnalysisAndPublish();
			baseClass.passedStep("User able to ignore error and publish ingestion");
		} else {
			baseClass.passedStep("Ingestion already present in the project");
		}
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: 29/09/2022 TestCase Id:RPMXCON-59386 Description
	 * :Verify that error should not be displayed while running Ingestion when PDF
	 * option is selected with Is Path in DAT option
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-59386", enabled = true, groups = { "regression" })
	public void verifyPdfIsPathInDatAddOnly() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-59386");
		baseClass.stepInfo("Verify add only ingestion with pdf option is path in dat");
		String ingestionName = null;
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AK_NativeFolder);
		if (status == false) {
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
					Input.sourceLocation, Input.AK_NativeFolder);
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);
			baseClass.stepInfo("Selecting Dat file");
			ingestionPage.selectDATSource(Input.pdfPathDat, Input.prodBeg);
			baseClass.stepInfo("Select PDF with is  path in DAT");
			ingestionPage.selectPDFSource(Input.PDFFile, true);
			baseClass.waitForElement(ingestionPage.getPDFFilePathFieldinDAT());
			ingestionPage.getPDFFilePathFieldinDAT().selectFromDropdown().selectByVisibleText(Input.pdfPathKey);
			baseClass.stepInfo("Select date format");
			ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
			baseClass.stepInfo("Save as draft and verify status");
			ingestionPage.verifyIngestionStatusAfterSaveAsDraft();
			baseClass.stepInfo("action as open in wizard");
			ingestionPage.performActionAsOpenWizardOption();
			baseClass.stepInfo("click on next button");
			ingestionPage.clickOnNextButton();
			baseClass.stepInfo("perform mapping and run ingestion");
			ingestionPage.selectValueFromEnabledFirstThreeSourceDATFields(Input.prodBeg, Input.prodBeg,
					Input.custodian);
			ingestionPage.clickOnPreviewAndRunButton();
			baseClass.stepInfo("Publish ingestion");
			ingestionName = ingestionPage.publishAddonlyIngestion(Input.AK_NativeFolder);
		} else {
			ingestionName = ingestionPage.getPublishedIngestionName(Input.AK_NativeFolder);
		}
		baseClass.stepInfo("view docs in docview");
		dataSets = new DataSets(driver);
		dataSets.navigateToDataSetsPage();
		dataSets.selectDataSetWithNameInDocView(ingestionName);
		baseClass.verifyUrlLanding(Input.url + "DocumentViewer/DocView", " on docview page", "Not on docview page");
		baseClass.passedStep("Error not disaplayed when pdf is path in Dat option");
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: 29/09/2022 TestCase Id:RPMXCON-59387 Description
	 * :Verify that error should not be displayed with Ingestion overlay when PDF
	 * option is selected with Is Path in DAT option
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-59387", enabled = true, groups = { "regression" })
	public void verifyPdfIsPathInDatOverlay() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-59387");
		baseClass.stepInfo("Verify overlay ingestion with pdf option is path in dat");
		String ingestionName = null;
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AK_NativeFolder);
		if (status == false) {
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
					Input.sourceLocation, Input.AK_NativeFolder);
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);
			baseClass.stepInfo("Selecting Dat file");
			ingestionPage.selectDATSource(Input.pdfPathDat, Input.prodBeg);
			baseClass.stepInfo("Select date format");
			ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
			baseClass.stepInfo("click on next button");
			ingestionPage.clickOnNextButton();
			baseClass.stepInfo("perform mapping and run ingestion");
			ingestionPage.selectValueFromEnabledFirstThreeSourceDATFields(Input.prodBeg, Input.prodBeg,
					Input.custodian);
			ingestionPage.clickOnPreviewAndRunButton();
			baseClass.stepInfo("Publish ingestion");
			ingestionPage.publishAddonlyIngestion(Input.AK_NativeFolder);
		}
		baseClass.stepInfo("Perform overlay ingestion");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.overlayOnly, Input.sourceSystem,
				Input.sourceLocation, Input.AK_NativeFolder);
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);
		baseClass.stepInfo("Selecting Dat file");
		ingestionPage.selectDATSource(Input.pdfPathDat, Input.prodBeg);
		baseClass.stepInfo("Select PDF with is  path in DAT");
		ingestionPage.selectPDFSource(Input.PDFFile, true);
		baseClass.waitForElement(ingestionPage.getPDFFilePathFieldinDAT());
		ingestionPage.getPDFFilePathFieldinDAT().selectFromDropdown().selectByVisibleText(Input.pdfPathKey);
		baseClass.stepInfo("Select date format");
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		baseClass.stepInfo("click on next button and run ingestion");
		ingestionPage.clickOnNextButton();
		ingestionPage.clickOnPreviewAndRunButton();
		ingestionName = ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();
		baseClass.stepInfo("view docs in docview");
		dataSets = new DataSets(driver);
		dataSets.navigateToDataSetsPage();
		dataSets.selectDataSetWithNameInDocView(ingestionName);
		baseClass.verifyUrlLanding(Input.url + "DocumentViewer/DocView", " on docview page", "Not on docview page");
		baseClass.passedStep("Error not displayed when pdf is path in Dat option after performing overlay");
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: 03/10/2022 TestCase Id:RPMXCON-48627 Description :To
	 * verify that search result for overlaid text is not displays if document is
	 * not published
	 * 
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-48627", enabled = true, groups = { "regression" })
	public void verifyResultOfOverlaidText() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48627");
		baseClass.stepInfo("Verify search result for overlaid text");
		String ingestionName = null;
		String BasicSearchName = "search" + Utility.dynamicNameAppender();

		// pre-requisite
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");
		baseClass.stepInfo("Navigating to manage project page");
		project = new ProjectPage(driver);
		project.navigateToProductionPage();
		baseClass.stepInfo("Disable auto analytics");
		project.disableOrEnableKickOffAnalytics(Input.ingestDataProject, "Disable",false);
		loginPage.logout();
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion with text files");
		boolean status = ingestionPage.verifyIngestionpublish(Input.UniCodeFilesFolder);
		if (status == false) {
			ingestionPage.unicodeFilesIngestion(Input.datLoadFile1, Input.textFile1, Input.documentKey);
			ingestionName = ingestionPage.publishAddonlyIngestion(Input.UniCodeFilesFolder);
		} else {
			ingestionName = ingestionPage.getPublishedIngestionName(Input.UniCodeFilesFolder);
		}
		baseClass.stepInfo("Unpublish docs");
		sessionSearch.MetaDataSearchInBasicSearch(Input.metadataIngestion, ingestionName);
		sessionSearch.saveSearch(BasicSearchName);
		ingestionPage.unpublish(BasicSearchName);
		baseClass.stepInfo("Perform overlay ingestion for unpublished text files");
		ingestionPage.OverlayIngestionWithDat(Input.UniCodeFilesFolder, Input.datLoadFile1, Input.documentKey, "text",
				Input.textFile1);
		ingestionName = ingestionPage.verifyApprovedStatusForOverlayIngestion();
		baseClass.selectproject();
		int count = sessionSearch.MetaDataSearchInBasicSearch(Input.metadataIngestion, ingestionName);
		baseClass.stepInfo("count :" + count);
		if (count == 0) {
			baseClass.passedStep("Result of overlaid text not displayed when docs are not published");
		} else {
			baseClass.failedStep("Result of overlaid text displayed without publishing docs");
		}
		loginPage.logout();
		// setting kick off analytics back to default
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		ingestionPage.navigateToManageProjectPage();
		project.disableOrEnableKickOffAnalytics(Input.projectName, "Enable",false);
		loginPage.logout();
	}
	
	
	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		baseClass = new BaseClass(driver);
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
		System.out.println("******TEST CASES FOR INGESTION EXECUTED******");

	}
}
