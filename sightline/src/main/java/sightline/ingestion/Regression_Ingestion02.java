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
import pageFactory.DocListPage;
import pageFactory.IngestionPage_Indium;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Regression_Ingestion02 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	IngestionPage_Indium ingestionPage;
	SessionSearch sessionsearch;
	DocListPage docList;
	Input ip;

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
	
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());

		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		
	}
	
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
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
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
	public void TCA5verifyCountInAudioDocsIndexing() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48244");
		baseClass.stepInfo("To verify Document Count for Audio Docs in Indexing section");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Perform add only ingestion till indexing");
		ingestionPage.performAudio96DocsIngestion(Input.audioDatFile, Input.docIdKey);
		ingestionPage.ingestionCatalogging();
		ingestionPage.ignoreErrorsAndCopying();
		ingestionPage.ingestionIndexing(Input.audio96DocsFolder);
		baseClass.stepInfo("Verify audio docs count in copying and indexing stage");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return ingestionPage.getIngestionDetailPopup(1).Displayed();
			}
		}), Input.wait30);
		ingestionPage.getIngestionDetailPopup(1).waitAndClick(5);
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
	public void TCA6verifyAudioIndexingErrorMessage() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48236");
		baseClass.stepInfo("To Verify In Ingestions, for audio indexing, there should not be any error message.");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
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
	public void TCA7verifyDataSearchWhileindexProcessRunning() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47372");
		baseClass.stepInfo("To verify that user is not able to search the data if Ingestions Indexing is running");
		sessionsearch = new SessionSearch(driver);
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("perform add only ingestion and start indexing");
		ingestionPage.performAutomationAllsourcesIngestion(Input.sourceSystem,Input.DATFile1, Input.prodBeg);
		ingestionPage.ignoreErrorsAndCatlogging();
		ingestionPage.ignoreErrorsAndCopying();
		String ingestionName=ingestionPage.startIndexing(true, Input.language);
		baseClass.stepInfo("verify options available in settings");
		ingestionPage.verifyOptionsAvailableInIngestionSetting();
		baseClass.stepInfo("search the data of ingestion");
		int dataCount = sessionsearch.basicSearchWithMetaDataQuery(ingestionName, Input.metadataIngestion);
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
	public void TCA8verifyIngestionMetaDataAvailable() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49552");
		baseClass.stepInfo("Verify that Ingestion Email Metadata 'EmailToNamesAndAddresses' is available");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
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
	public void TCA9verifyAddOnlyIngestionWithNewEmailMetadata() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49556");
		baseClass.stepInfo("Verify Ingestion should published successfully with new Email metadata");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
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
	public void TCB4verifyAddOnlyIngestionWithDefaultDelimiter() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48290");
		baseClass.stepInfo("To verify In Ingestion, ADD Only ,ASCII(59) should be default New Line delimiter");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("perform add only ingestion with default new line delimiter");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem, 
				Input.sourceLocation, Input.HiddenPropertiesFolder);
		baseClass.stepInfo("verify default value of new line delimiter");
		ingestionPage.verifyDefaultValueOfDelimiter();
		baseClass.stepInfo("Selecting Dat and Native file");
		ingestionPage.selectDATSource(Input.YYYYMMDDHHMISSDat, Input.sourceDocIdSearch);
		ingestionPage.selectNativeSource(Input.YYYYMMDDHHMISSLst, false);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return ingestionPage.getDateFormat().Visible();
			}
		}), Input.wait30);
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
	public void TCB1verifyIngestingTranscriptAlongWithNative() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-48148");
		baseClass.stepInfo("To Verify User is able to Ingest Transcript  along with native");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
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
	public void TCB2verifyProgressBarAndCountDetailsOnTiles() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47305");
		baseClass.stepInfo("Verify progress bar and count data updated once ingestion process started");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
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
	public void TCB3verifyIngestionFromDraftMode() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47312");
		baseClass.stepInfo("To verify that user can Ingest the files which are in Draft Mode");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
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
