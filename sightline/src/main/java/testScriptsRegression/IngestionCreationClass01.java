package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;

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
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.IngestionPage_Indium;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class IngestionCreationClass01 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	IngestionPage_Indium ingestionPage;
	Input ip;
	SoftAssert softAssertion;

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
		loginPage = new LoginPage(driver);

		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
	}

	
	
	/**
	 * Author :Arunkumar date: 29/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-47566 Description :To Verify status update in cataloging,
	 * copying, and Indexing Section in Ingestion Details Page
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 1)
	public void verifyStatusUpdateInPopup() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47566");
		baseClass.stepInfo(
				"To Verify status update in cataloging, copying, and Indexing Section in Ingestion Details Page");
		ingestionPage.unicodeFilesIngestion(Input.datLoadFile1, Input.textFile1, Input.documentKey);
		ingestionPage.ingestionCatalogging();
		ingestionPage.verifyStatusUpdatenInIngestionDetailPopup("Catalogingblock");
		ingestionPage.ingestionCopying();
		ingestionPage.verifyStatusUpdatenInIngestionDetailPopup("Copyingblock");
		ingestionPage.ingestionIndexing(Input.UniCodeFilesFolder);
		ingestionPage.verifyStatusUpdatenInIngestionDetailPopup("Indexingblock");
		// rollback
		ingestionPage.rollBackIngestion();
		loginPage.logout();

	}
	
	/**
	 * Author :Arunkumar date: 02/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-47404 Description :To verify that if configure mapping is not
	 * matched the warning message is displayed and as per selection, admin can
	 * proceed.
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 2)
	public void verifyConfigureMappingWarningMessage() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47404");
		baseClass.stepInfo(
				"verify that if configure mapping is not matched the warning message is displayed and as per selection, admin can proceed.");
		ingestionPage.unicodeFilesIngestion(Input.datLoadFile1, Input.textFile1, Input.documentKey);
		ingestionPage.ingestionCatalogging();
		ingestionPage.verifyWarningMessageForConfigureMappingSection(Input.UniCodeFilesFolder, Input.datLoadFile1,
				Input.textFile1, Input.prodBeg, Input.documentKey);
		loginPage.logout();

	}
	
	/** 
	 * Author :Arunkumar date: 29/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-48175 Description :To Verify Rollback In Ingestion for Files
	 * having Read Only Attributes.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 3)
	public void verifyRollbackReadOnlyAttribute() {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48175");
		baseClass.stepInfo("To Verify Rollback In Ingestion for Files having Read Only Attributes.");
		ingestionPage.performAKNativeFolderIngestion(Input.DATFile1);
		ingestionPage.ingestionCatalogging();
		ingestionPage.ignoreErrorsAndCopying();
		ingestionPage.ingestionIndexing(Input.AK_NativeFolder);
		// Rollback
		ingestionPage.rollBackIngestion();
		loginPage.logout();

	}
	
	/**
	 * Author :Arunkumar date: 02/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-49703 Description :Verify if 'Generate Searchable PDFs' is
	 * True for TIFF image and document has multi-page TIFF's then in the Copying
	 * section, Stitching TIFFs details should display before the "Generate
	 * Searchable PDFs" row on Ingestion details pop up
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 4)
	public void verifyStitchedTiffBeforeGeneratedSearchablePDFs() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49703");
		baseClass.stepInfo(
				"Verify if 'Generate Searchable PDFs' is True Stitching TIFFs details should display before the Generate Searchable PDFs row on Ingestion details pop up");
		ingestionPage.tiffImagesIngestion(Input.DATFile4, Input.tiffLoadFile, "true");
		ingestionPage.ignoreErrorsAndCatlogging();
		ingestionPage.ignoreErrorsAndCopying();
		ingestionPage.verifyTermPositionInCopyColumn(Input.StitchedTIFF);
		// Rollback Ingestion
		ingestionPage.rollBackIngestion();
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 02/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-48194 Description :To Verify In Ingestions the pop-up Details
	 * Window for media ;audio & Transcript Count.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 5)
	public void verifyMediaAndTranscriptDetailInPopup() throws InterruptedException {

		String[] selectedTerm = { "Native", "Text", "MP3 Variant", "Audio Transcript" };
		String[] unselectedTerm = { "PDF", Input.StitchedTIFF, Input.generateSearchablePDF };

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48194");
		baseClass.stepInfo("To Verify In Ingestions the pop-up Details Window for media ;audio & Transcript Count.");
		ingestionPage.mediaAndTranscriptIngestion(Input.AllSourcesFolder, Input.DATFile1);
		ingestionPage.ignoreErrorsAndCatlogging();
		ingestionPage.ignoreErrorsAndCopying();
		for (int i = 0; i <= selectedTerm.length - 1; i++) {
			ingestionPage.verifyDataPresentInCopyTableColumn(selectedTerm[i], "source");
		}
		for (int i = 0; i <= unselectedTerm.length - 1; i++) {
			ingestionPage.verifyUnselectedSourceCountInCopySection(unselectedTerm[i]);
		}
		// Rollback Ingestion
		ingestionPage.rollBackIngestion();
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 05/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-47402 Description :To verify "Copy Ingestion" with ingestion
	 * wizard (Using link "Add Ingestion(New approach)")
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 6)
	public void verifyCopyIngestionUsingNewApproach() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47402");
		baseClass.stepInfo(
				"To verify 'Copy Ingestion' with ingestion wizard (Using link 'Add Ingestion(New approach)')");
		ingestionPage.unicodeFilesIngestion(Input.datLoadFile1, Input.textFile1, Input.documentKey);
		ingestionPage.ingestionCatalogging();
		ingestionPage.performNewAddOnlyIngestionUsingCopyOption("Mapped Data", Input.HiddenPropertiesFolder,
				Input.YYYYMMDDHHMISSDat, Input.sourceDocIdSearch);
		loginPage.logout();

	}
	
	
	/**
	 * Author :Arunkumar date: 05/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-49282 Description :To Verify Ingestion Add Only end to End
	 * Flow with Source System as Mapped Data
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 7)
	public void verifyAddonlyIngestionWithMappedData() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49282");
		baseClass.stepInfo("To Verify Ingestion Add Only end to End Flow with Source System as Mapped Data");
		// perform add only ingestion with source system as Mapped data
		boolean status = ingestionPage.verifyIngestionpublish(Input.UniCodeFilesFolder);
		System.out.println(status);
		if (status == false) {
			ingestionPage.unicodeFilesIngestionWithDifferentSourceSystem("Mapped Data", Input.datLoadFile1,
					Input.textFile1, Input.documentKey);
			ingestionPage.IngestionCatlogtoCopying(Input.UniCodeFilesFolder);
			ingestionPage.ingestionIndexing(Input.UniCodeFilesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}
		ingestionPage.verifyTotalDocsIngestedWithPurehitCount();
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 06/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-49467 Description :Verify error message displays if adding
	 * same source Doc ID which is already exists in the DB
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 8)
	public void verifyErrorMessageIfDocidExists() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49467");
		baseClass.stepInfo(
				"Verify error message displays if adding same source Doc ID which is already exists in the DB");
		// perform add only ingestion
		boolean status = ingestionPage.verifyIngestionpublish(Input.UniCodeFilesFolder);
		System.out.println(status);
		if (status == false) {
			ingestionPage.unicodeFilesIngestion(Input.datLoadFile1, Input.textFile1, Input.documentKey);
			ingestionPage.IngestionCatlogtoCopying(Input.UniCodeFilesFolder);
			ingestionPage.ingestionIndexing(Input.UniCodeFilesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.navigateToAnalyticsPage();
			ingestionPage.runFullAnalysisAndPublish();
		}
		// Verify error message for overlay ingestion if docid is already available
		ingestionPage.unicodeFilesIngestion(Input.datLoadFile1, Input.textFile1, Input.documentKey);
		ingestionPage.verifyDuplicateIngestionErrorMessage();
		// Rollback
		ingestionPage.rollBackIngestion();
		loginPage.logout();

	}
	
	/**
	 * Author :Arunkumar date: 06/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-49466 Description :Verify error message if the source system
	 * is matching and if the doc ID is not available in the database
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 9)
	public void verifyErrorMessageIfDocIdNotAvailable() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49466");
		baseClass.stepInfo(
				"Verify error message if the source system is matching and if the doc ID is not available in the database");
		// perform add only ingestion
		boolean status = ingestionPage.verifyIngestionpublish(Input.UniCodeFilesFolder);
		System.out.println(status);
		if (status == false) {
			ingestionPage.unicodeFilesIngestionWithDifferentSourceSystem("TRUE", Input.datLoadFile1, Input.textFile1,
					Input.documentKey);
			ingestionPage.IngestionCatlogtoCopying(Input.UniCodeFilesFolder);
			ingestionPage.ingestionIndexing(Input.UniCodeFilesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.navigateToAnalyticsPage();
			ingestionPage.runFullAnalysisAndPublish();
		}
		// Verify error message for overlay ingestion if docid not available
		boolean ingestStatus = ingestionPage.verifyIngestionpublish(Input.UniCodeFilesFolder);
		System.out.println(ingestStatus);
		if (ingestStatus == false) {
		ingestionPage.OverlayIngestionForDATWithMappingFieldSection(Input.HiddenPropertiesFolder,
				Input.YYYYMMDDHHMISSDat, Input.sourceDocIdSearch);
		ingestionPage.clickOnPreviewAndRunButton();
		ingestionPage.verifyNonExistingDatasetErrorMessage();
		// Rollback
		ingestionPage.rollBackIngestion();
		loginPage.logout();
		}
		else {
			baseClass.passedStep("Ingestion already present in the project,unable to check error message for doc ID not available");
		}

	}
	
	/**
	 * Author :Arunkumar date: 09/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-46868 Description :Add only ingestion with only .mp3 audio
	 * files with MP3 Variant file types.
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 10)
	public void verifyAddOnlyIngestionForAudioFiles() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-46868");
		baseClass.stepInfo("Add only ingestion with only .mp3 audio files with MP3 Variant file types.");

		// perform add only ingestion
			ingestionPage.performAudio96DocsIngestion(Input.audioDatFile, Input.docIdKey);
			ingestionPage.ingestionCatalogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.audio96DocsFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
			loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 10/05/2022 Modified date: NA Modified by: NA TestCase Id:RPMXCON-47842 
	 * Description :Add only ingestion with only .mp3 audio files with MP3 Variant file types. 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 11)
	public void verifyAddOnlyIngestionForMp3VariantFileTypes() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47842");
		baseClass.stepInfo("Add only ingestion with only .mp3 audio files with MP3 Variant file types.");

		// perform add only ingestion for mp3 variant file type
		boolean status = ingestionPage.verifyIngestionpublish(Input.audio96DocsFolder);
		if (status == false) {
			ingestionPage.performAudio96DocsIngestion(Input.audioDatFile, Input.docIdKey);
			ingestionPage.ingestionCatalogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.audio96DocsFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}

	}
	
	/**
	 * Author :Arunkumar date: 09/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-48147 Description :To Verify User is able to Ingest MP3 File
	 * Variant along with native
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 12)
	public void verifyAddOnlyIngestionForMp3WithNative() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48147");
		baseClass.stepInfo("To Verify User is able to Ingest MP3  File Variant along with native");

		// perform add only ingestion for mp3 along with native
		boolean status = ingestionPage.verifyIngestionpublish(Input.audio96DocsFolder);
		if (status == false) {
			ingestionPage.performAudio96DocsIngestion(Input.audioDatFile, Input.docIdKey);
			ingestionPage.ingestionCatalogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.audio96DocsFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}

	}
	
	/**
	 * Author :Arunkumar date: 20/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-47293 Description :Ingesting Duplicate files.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 13)
	public void verifyErrorForIngestingDuplicateFiles() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47293");
		baseClass.stepInfo("Ingesting Duplicate files.");
		boolean status = ingestionPage.verifyIngestionpublish(Input.UniCodeFilesFolder);
		System.out.println(status);
		if (status == false) {
			ingestionPage.unicodeFilesIngestion(Input.datLoadFile1, Input.textFile1, Input.documentKey);
			ingestionPage.IngestionCatlogtoCopying(Input.UniCodeFilesFolder);
			ingestionPage.ingestionIndexing(Input.UniCodeFilesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.navigateToAnalyticsPage();
			ingestionPage.runFullAnalysisAndPublish();
		}
		// Verify error message for ingesting duplicate files
		ingestionPage.unicodeFilesIngestion(Input.datLoadFile1, Input.textFile1, Input.documentKey);
		ingestionPage.verifyDuplicateIngestionErrorMessage();
		// rollback ingestion
		ingestionPage.rollBackIngestion();
	}

	
	/**
	 * Author :Arunkumar date: 09/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-49258 Description :To verify that total unique ingested
	 * document count displays unique count if user perform only PDF overlay
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 14)
	public void verifyUniqueCountForOnlyPdfOverlay() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49258");
		baseClass.stepInfo(
				"To verify that total unique ingested document count displays unique count if user perform only PDF overlay");

		// perform add only ingestion
		boolean status = ingestionPage.verifyIngestionpublish(Input.AK_NativeFolder);
		if (status == false) {
			ingestionPage.performAKNativeFolderIngestion(Input.DATFile1);
			ingestionPage.ingestionCatalogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.AK_NativeFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.navigateToAnalyticsPage();
			ingestionPage.runFullAnalysisAndPublish();
		}
		// getting unique ingested count before overlay
		int uniqueCountBefore = ingestionPage.getIngestedUniqueCount();
		baseClass.stepInfo("Total unique count before performing overlay : '" + uniqueCountBefore + "'");
		// perform pdf overlay
		baseClass.stepInfo("Performing overlay ingestion with PDF");
		ingestionPage.OverlayIngestionWithoutDat(Input.AK_NativeFolder, "Pdf", Input.PDFFile);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();
		// getting unique ingested count after overlay
		int uniqueCountAfter = ingestionPage.getIngestedUniqueCount();
		baseClass.stepInfo("Total unique count before performing overlay : '" + uniqueCountAfter + "'");
		if (uniqueCountBefore == uniqueCountAfter) {
			baseClass.passedStep("Only unique ingested count displayed after performing Pdf overlay");
		} else {
			baseClass.failedStep("Unique ingested count not displayed after performing Pdf overlay");
		}
	}

	/**
	 * Author :Arunkumar date: 09/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-49259 Description :To verify that total unique ingested
	 * document count displays unique count if user perform only Native overlay
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 15)
	public void verifyUniqueCountForOnlyNativeOverlay() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49259");
		baseClass.stepInfo(
				"To verify that total unique ingested document count displays unique count if user perform only Native overlay");
		// perform add only ingestion
		boolean status = ingestionPage.verifyIngestionpublish(Input.AK_NativeFolder);
		if (status == false) {
			ingestionPage.performAKNativeFolderIngestion(Input.DATFile1);
			ingestionPage.ingestionCatalogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.AK_NativeFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.navigateToAnalyticsPage();
			ingestionPage.runFullAnalysisAndPublish();
		}
		// getting unique ingested count before overlay
		int uniqueCountBefore = ingestionPage.getIngestedUniqueCount();
		baseClass.stepInfo("Total unique count before performing overlay : '" + uniqueCountBefore + "'");
		// perform Native overlay
		baseClass.stepInfo("Performing overlay ingestion with Native");
		ingestionPage.OverlayIngestionWithoutDat(Input.AK_NativeFolder, "Native", Input.NativeFile);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();
		// getting unique ingested count after overlay
		int uniqueCountAfter = ingestionPage.getIngestedUniqueCount();
		baseClass.stepInfo("Total unique count before performing overlay : '" + uniqueCountAfter + "'");
		if (uniqueCountBefore == uniqueCountAfter) {
			baseClass.passedStep("Only unique ingested count displayed after performing Native overlay");
		} else {
			baseClass.failedStep("Unique ingested count not displayed after performing Native overlay");
		}
	}

	/**
	 * Author :Arunkumar date: 09/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-49261 Description :To verify that total unique ingested
	 * document count displays unique count if user perform only MP3 overlay
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 16)
	public void verifyUniqueCountForOnlyMp3Overlay() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49261");
		baseClass.stepInfo(
				"To verify that total unique ingested document count displays unique count if user perform only MP3 overlay");
		// perform add only ingestion
		boolean status = ingestionPage.verifyIngestionpublish(Input.AK_NativeFolder);
		if (status == false) {
			ingestionPage.performAKNativeFolderIngestion(Input.DATFile1);
			ingestionPage.ingestionCatalogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.AK_NativeFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.navigateToAnalyticsPage();
			ingestionPage.runFullAnalysisAndPublish();
		}
		// getting unique ingested count before overlay
		int uniqueCountBefore = ingestionPage.getIngestedUniqueCount();
		baseClass.stepInfo("Total unique count before performing overlay : '" + uniqueCountBefore + "'");
		// perform Mp3 overlay
		baseClass.stepInfo("Performing overlay ingestion with Native");
		ingestionPage.OverlayIngestionWithoutDat(Input.AK_NativeFolder, "mp3", Input.MP3File);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();
		// getting unique ingested count after overlay
		int uniqueCountAfter = ingestionPage.getIngestedUniqueCount();
		baseClass.stepInfo("Total unique count before performing overlay : '" + uniqueCountAfter + "'");
		if (uniqueCountBefore == uniqueCountAfter) {
			baseClass.passedStep("Only unique ingested count displayed after performing Mp3 overlay");
		} else {
			baseClass.failedStep("Unique ingested count not displayed after performing Mp3 overlay");
		}
	}
	
	/**
	 * Author :Arunkumar date: 10/05/2022 Modified date: NA Modified by: NA TestCase Id:RPMXCON-47587 
	 * Description :Ingestion with Overlay mode with only DAT file along with PDF or TIFF or Native file type 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 17)
	public void verifyOverlayIngestionWithDatAlongWithNative() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47587");
		baseClass.stepInfo("Ingestion with Overlay mode with only DAT file along with PDF or TIFF or Native file type");

		// perform add only ingestion for mp3 variant file type
		boolean status = ingestionPage.verifyIngestionpublish(Input.AK_NativeFolder);
		if (status == false) {
			ingestionPage.performAKNativeFolderIngestion(Input.DATFile1);
			ingestionPage.ingestionCatalogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.AK_NativeFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}
		// perform overlay ingestion with dat along with native
		baseClass.stepInfo("Performing overlay ingestion with Dat along with Native");
		ingestionPage.OverlayIngestionWithDat(Input.AK_NativeFolder,Input.DATFile1,Input.prodBeg,"Native",Input.NativeFile);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();
		
	}
	
	/**
	 * Author :Arunkumar date: 11/05/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-48591 
	 * Description :Verify the Analytics process should be skipped when Text files are not overlayed .
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 18)
	public void verifyAnalyticsStatusWhenOverlayDatWithoutText() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48591");
		baseClass.stepInfo("Verify the Analytics process should be skipped when Text files are not overlayed .");
		// perform add only ingestion 
		boolean status = ingestionPage.verifyIngestionpublish(Input.UniCodeFilesFolder);
		System.out.println(status);
		if (status == false) {
		ingestionPage.unicodeFilesIngestion(Input.datLoadFile1, Input.textFile1,Input.documentKey);
		ingestionPage.IngestionCatlogtoCopying(Input.UniCodeFilesFolder);
		ingestionPage.ingestionIndexing(Input.UniCodeFilesFolder);
		ingestionPage.approveIngestion(1);
		ingestionPage.navigateToAnalyticsPage();
		ingestionPage.runFullAnalysisAndPublish();
		}
		// Verify analytic status when overlay without text
		baseClass.stepInfo("Perform overlay ingestion without text file");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.overlayOnly, "source", Input.sourceLocation, Input.UniCodeFilesFolder);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return ingestionPage.getDATDelimitersNewLine().Visible();
			}
		}), Input.wait30);
		ingestionPage.getDATDelimitersNewLine().selectFromDropdown().selectByVisibleText(Input.multiValue);
		ingestionPage.selectDATSource(Input.datLoadFile1, Input.documentKey);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return ingestionPage.getDateFormat().Visible();
			}
		}), Input.wait30);
		ingestionPage.getDateFormat().selectFromDropdown().selectByVisibleText(Input.dateFormat);
		ingestionPage.clickOnNextButton();
		ingestionPage.clickOnPreviewAndRunButton();
		baseClass.stepInfo("Ingestion started");
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runAnalyticsAndVerifySkippedStatus();
		
	}
	
	/**
	 * Author :Arunkumar date: 11/05/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-48593 
	 * Description :Verify the Analytics process should be skipped when PDF Files are overlayed and Text files are not overlayed.
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 19)
	public void verifyAnalyticsStatusWhenOverlayPdfWithoutText() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48593");
		baseClass.stepInfo("Verify the Analytics process should be skipped when PDF Files are overlayed and Text files are not overlayed .");
		// perform add only ingestion 
		boolean status = ingestionPage.verifyIngestionpublish(Input.AK_NativeFolder);
		System.out.println(status);
		if (status == false) {
			ingestionPage.performAKNativeFolderIngestion(Input.DATFile1);
			ingestionPage.ingestionCatalogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.AK_NativeFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}
		// Verify analytic status when pdf overlay without text
		baseClass.stepInfo("Perform pdf overlay ingestion without text file");
		ingestionPage.OverlayIngestionWithDat(Input.AK_NativeFolder,Input.DATFile1,Input.prodBeg,"pdf",Input.PDFFile);
		baseClass.stepInfo("Ingestion started");
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runAnalyticsAndVerifySkippedStatus();
		
	}
	
	/**
	 * Author :Arunkumar date: 11/05/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-48592 
	 * Description :Verify the Analytics process should take places when Text files are overlayed .
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 20)
	public void verifyAnalyticsStatusWhenOverlayWithText() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48592");
		baseClass.stepInfo("Verify the Analytics process should take places when Text files are overlayed.");
		// perform add only ingestion 
		boolean status = ingestionPage.verifyIngestionpublish(Input.UniCodeFilesFolder);
		System.out.println(status);
		if (status == false) {
			ingestionPage.unicodeFilesIngestion(Input.datLoadFile1, Input.textFile1, Input.documentKey);
			ingestionPage.IngestionCatlogtoCopying(Input.UniCodeFilesFolder);
			ingestionPage.ingestionIndexing(Input.UniCodeFilesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}
		// Verify analytic status when overlay with text
		baseClass.stepInfo("Perform overlay ingestion with text file");
		ingestionPage.OverlayIngestionWithoutDat(Input.UniCodeFilesFolder, "text", Input.textFile1);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runAnalyticsAndVerifyAnalyticStatus();
		
	}
	
	/**
	 * Author :Arunkumar date: 11/05/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-48594
	 * Description :Verify the Analytics process should be skipped when we overlay Audio Files without Text File
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 21)
	public void verifyAnalyticsStatusWhenOverlayMp3WithoutText() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48594");
		baseClass.stepInfo("Verify the Analytics process should be skipped when we overlay Audio Files without Text File.");
		// perform add only ingestion 
		boolean status = ingestionPage.verifyIngestionpublish(Input.AK_NativeFolder);
		System.out.println(status);
		if (status == false) {
			ingestionPage.performAKNativeFolderIngestion(Input.DATFile1);
			ingestionPage.ingestionCatalogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.AK_NativeFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}
		//Verify analytic status when pdf overlay without text
		baseClass.stepInfo("Perform pdf overlay ingestion without text file");
		ingestionPage.OverlayIngestionWithoutDat(Input.AK_NativeFolder,"mp3",Input.MP3File);
		baseClass.stepInfo("Ingestion started");
		ingestionPage.ignoreErrorsAndCatlogging();
		ingestionPage.ignoreErrorsAndCopying();
		ingestionPage.ingestionIndexing(Input.AK_NativeFolder);
		ingestionPage.approveIngestion(1);
		ingestionPage.runAnalyticsAndVerifySkippedStatus();
		
	}
	

	
	/**
	 * Author :Arunkumar date: 04/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-48267 Description :To Verify Ingestion Overlay Without DAT
	 * for Translation.
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 22)
	public void verifyIngestionOverlayWithoutDatForTranslation() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48267");
		baseClass.stepInfo("To Verify Ingestion Overlay Without DAT for Translation.");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Performing add only ingestion");
			ingestionPage.IngestionOnlyForDatFile(Input.AllSourcesFolder, Input.DATFile1);
			ingestionPage.IngestionCatlogtoCopying(Input.AllSourcesFolder);
			ingestionPage.ingestionIndexing(Input.AllSourcesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.navigateToAnalyticsPage();
			ingestionPage.runFullAnalysisAndPublish();
		}
		// Perform overlay ingestion for translation without DAT
		ingestionPage.OverlayIngestionWithoutDat(Input.AllSourcesFolder, "Translation", Input.TranslationFile);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.navigateToAnalyticsPage();
		ingestionPage.runFullAnalysisAndPublish();
		ingestionPage.verifyDocAvailability();
		loginPage.logout();

	}

	/**
	 * Author :Arunkumar date: 04/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-48266 Description :To Verify Ingestion Overlay Without DAT
	 * for Transcript.
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 23)
	public void verifyIngestionOverlayWithoutDatForTranscript() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48266");
		baseClass.stepInfo("To Verify Ingestion Overlay Without DAT for Transcript.");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Performing add only ingestion");
			ingestionPage.IngestionOnlyForDatFile(Input.AllSourcesFolder, Input.DATFile1);
			ingestionPage.IngestionCatlogtoCopying(Input.AllSourcesFolder);
			ingestionPage.ingestionIndexing(Input.AllSourcesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.navigateToAnalyticsPage();
			ingestionPage.runFullAnalysisAndPublish();
		}
		// Perform overlay ingestion for transcript without DAT
		ingestionPage.OverlayIngestionWithoutDat(Input.AllSourcesFolder, "Transcript", Input.TranscriptFile);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.navigateToAnalyticsPage();
		ingestionPage.runFullAnalysisAndPublish();
		ingestionPage.verifyDocAvailability();
		loginPage.logout();

	}

	/**
	 * Author :Arunkumar date: 04/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-48265 Description :To Verify Ingestion Overlay Without DAT
	 * for MP3 Variant.
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 24)
	public void verifyIngestionOverlayWithoutDatForMp3() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48265");
		baseClass.stepInfo("To Verify Ingestion Overlay Without DAT for MP3 Variant.");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Performing add only ingestion");
			ingestionPage.IngestionOnlyForDatFile(Input.AllSourcesFolder, Input.DATFile1);
			ingestionPage.IngestionCatlogtoCopying(Input.AllSourcesFolder);
			ingestionPage.ingestionIndexing(Input.AllSourcesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.navigateToAnalyticsPage();
			ingestionPage.runFullAnalysisAndPublish();
		}
		// Perform overlay ingestion for mp3 without DAT
		ingestionPage.OverlayIngestionWithoutDat(Input.AllSourcesFolder, "mp3", Input.MP3File);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.navigateToAnalyticsPage();
		ingestionPage.runFullAnalysisAndPublish();
		ingestionPage.verifyDocAvailability();
		loginPage.logout();

	}

	

	/**
	 * Author :Arunkumar date: 04/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-48263 Description :To Verify Ingestion Overlay Without DAT
	 * for PDF.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 25)
	public void verifyIngestionOverlayWithoutDatForPdf() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48263");
		baseClass.stepInfo("To Verify Ingestion Overlay Without DAT for PDF");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Performing add only ingestion");
			ingestionPage.IngestionOnlyForDatFile(Input.AllSourcesFolder, Input.DATFile1);
			ingestionPage.IngestionCatlogtoCopying(Input.AllSourcesFolder);
			ingestionPage.ingestionIndexing(Input.AllSourcesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.navigateToAnalyticsPage();
			ingestionPage.runFullAnalysisAndPublish();
		}
		// Perform overlay ingestion for Pdf without DAT
		ingestionPage.OverlayIngestionWithoutDat(Input.AllSourcesFolder, "Pdf", Input.PDFFile);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.navigateToAnalyticsPage();
		ingestionPage.runFullAnalysisAndPublish();
		ingestionPage.verifyDocAvailability();
		loginPage.logout();

	}

	/**
	 * Author :Arunkumar date: 04/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-48262 Description :To Verify Ingestion Overlay Without DAT
	 * for Native.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 26)
	public void verifyIngestionOverlayWithoutDatForNative() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48262");
		baseClass.stepInfo("To Verify Ingestion Overlay Without DAT for Native.");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Performing add only ingestion");
			ingestionPage.IngestionOnlyForDatFile(Input.AllSourcesFolder, Input.DATFile1);
			ingestionPage.IngestionCatlogtoCopying(Input.AllSourcesFolder);
			ingestionPage.ingestionIndexing(Input.AllSourcesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.navigateToAnalyticsPage();
			ingestionPage.runFullAnalysisAndPublish();
		}
		// Perform overlay ingestion for Native without DAT
		ingestionPage.OverlayIngestionWithoutDat(Input.AllSourcesFolder, "Native", Input.NativeFile);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.navigateToAnalyticsPage();
		ingestionPage.runFullAnalysisAndPublish();
		ingestionPage.verifyDocAvailability();
		loginPage.logout();

	}
	
	
	
	/**
	 * Author :Arunkumar date: 10/05/2022 Modified date: NA Modified by: NA TestCase Id:RPMXCON-48203 
	 * Description :To Verify Ingestion overlay of Others without Unpublish
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 27)
	public void verifyOverlayIngestionWithOthers() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48203");
		baseClass.stepInfo("To Verify Ingestion overlay of Others without Unpublish");

		// perform add only ingestion
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		if (status == false) {
			ingestionPage.IngestionOnlyForDatFile(Input.AllSourcesFolder, Input.prodBeg);
			ingestionPage.ignoreErrorsAndCatlogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.AllSourcesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}
		// perform other overlay
		baseClass.stepInfo("Performing overlay ingestion with Others");
		ingestionPage.OverlayIngestionWithoutDat(Input.AllSourcesFolder, "Translation", Input.TranslationFile);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();
	}
	
	
	
	/**
	 * Author :Arunkumar date: 11/05/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-48597 
	 * Description :Verify the Analytics process should be skipped when Tiff Files are overlayed without Text Files.
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 28)
	public void verifyAnalyticsStatusWhenOverlayTiffWithoutText() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48597");
		baseClass.stepInfo("Verify the Analytics process should be skipped when Tiff Files are overlayed without Text Files.");
		// perform add only ingestion 
		boolean status = ingestionPage.verifyIngestionpublish(Input.TiffImagesFolder);
		System.out.println(status);
		if (status == false) {
			ingestionPage.tiffImagesIngestion(Input.DATFile2, Input.tiffLoadFile, "false");
			ingestionPage.ignoreErrorsAndCatlogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.TiffImagesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}
		// Verify analytic status when Tiff overlay without text
		baseClass.stepInfo("Perform Tiff overlay ingestion without text file");
		ingestionPage.OverlayIngestionWithoutDat(Input.TiffImagesFolder, "Tiff", Input.tiffLoadFile);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runAnalyticsAndVerifySkippedStatus();
		
	}
	
	

	/**
	 * Author :Arunkumar date: 08/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-49260 Description :To verify that total unique ingested
	 * document count displays unique count if user perform only TIFF overlay
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 29)
	public void verifyUniqueCountForOnlyTiffOverlay() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49260");
		baseClass.stepInfo(
				"To verify that total unique ingested document count displays unique count if user perform only TIFF overlay");
		// perform add only ingestion
		boolean status = ingestionPage.verifyIngestionpublish(Input.TiffImagesFolder);
		if (status == false) {
			ingestionPage.tiffImagesIngestion(Input.DATFile2, Input.tiffLoadFile, "false");
			ingestionPage.ignoreErrorsAndCatlogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.TiffImagesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.navigateToAnalyticsPage();
			ingestionPage.runFullAnalysisAndPublish();
		}
		// getting unique ingested count before overlay
		int uniqueCountBefore = ingestionPage.getIngestedUniqueCount();
		baseClass.stepInfo("Total unique count before performing overlay : '" + uniqueCountBefore + "'");
		// perform Tiff overlay
		baseClass.stepInfo("Performing overlay ingestion with Native");
		ingestionPage.OverlayIngestionWithoutDat(Input.TiffImagesFolder, "Tiff", Input.tiffLoadFile);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();
		// getting unique ingested count after overlay
		int uniqueCountAfter = ingestionPage.getIngestedUniqueCount();
		baseClass.stepInfo("Total unique count before performing overlay : '" + uniqueCountAfter + "'");
		if (uniqueCountBefore == uniqueCountAfter) {
			baseClass.passedStep("Only unique ingested count displayed after performing Tiff overlay");
		} else {
			baseClass.failedStep("Unique ingested count not displayed after performing Tiff overlay");
		}
	}
	
	/**
	 * Author :Arunkumar date: 04/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-48264 Description :To Verify Ingestion Overlay Without DAT
	 * for TIFF.
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 30)
	public void verifyIngestionOverlayWithoutDatForTiff() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48264");
		baseClass.stepInfo("To Verify Ingestion Overlay Without DAT for TIFF.");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Performing add only ingestion");
			ingestionPage.IngestionOnlyForDatFile(Input.AllSourcesFolder, Input.DATFile1);
			ingestionPage.IngestionCatlogtoCopying(Input.AllSourcesFolder);
			ingestionPage.ingestionIndexing(Input.AllSourcesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.navigateToAnalyticsPage();
			ingestionPage.runFullAnalysisAndPublish();
		}
		// Perform overlay ingestion for Tiff without DAT
		ingestionPage.OverlayIngestionWithoutDat(Input.AllSourcesFolder, "Tiff", Input.TIFFFile);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.navigateToAnalyticsPage();
		ingestionPage.runFullAnalysisAndPublish();
		ingestionPage.verifyDocAvailability();
		loginPage.logout();

	}

	
	
	/**
	 * Author :Arunkumar date: 10/05/2022 Modified date: NA Modified by: NA TestCase Id:RPMXCON-48292
	 * Description :To verify In Ingestion, with another New Line delimiter other than ASCII(59)
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 31)
	public void verifyAddOnlyIngestionWithAnotherNewLineDelimiter() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48292");
		baseClass.stepInfo("To verify In Ingestion, with another New Line delimiter other than ASCII(59)");

		// perform add only ingestion with new line delimiter 
		boolean status = ingestionPage.verifyIngestionpublish(Input.HiddenPropertiesFolder);
		if (status == false) {
			ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder,Input.YYYYMMDDHHMISSDat);
			ingestionPage.IngestionCatlogtoCopying(Input.HiddenPropertiesFolder);
			ingestionPage.ingestionIndexing(Input.HiddenPropertiesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}
		else {
			baseClass.passedStep("Ingestion already performed in this project successfully with New Line delimiter other than ASCII(59)");
		}

	}
	
	/**
	 * Author :Arunkumar date: 22/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-47598 Description :Edit of saved Overlay ingestion with out
	 * mapping field selection.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 32)
	public void verifyEditOverlayIngestionWithoutMapping() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47598");
		baseClass.stepInfo("Edit of saved Overlay ingestion with out mapping field selection.");
		ingestionPage.OverlayIngestionForDATWithoutMappingFieldSection(Input.HiddenPropertiesFolder,
				Input.YYYYMMDDHHMISSDat, Input.sourceDocIdSearch);
		baseClass.stepInfo("Saved ingestion as draft without clicking next button and mapping field in disabled state");
		ingestionPage.verifyIngestionStatusAfterSaveAsDraft();
		// click on open in wizard option to edit ingestion
		ingestionPage.IngestionFromDraftMode();
		ingestionPage.ingestionCatalogging();

	}

	/**
	 * Author :Arunkumar date: 22/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-47597 Description :Edit of Overlay saved ingestion with
	 * mapping field selection
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 33)
	public void verifyEditOverlayIngestionWithMapping() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47597");
		baseClass.stepInfo("Edit of Overlay saved ingestion with mapping field selection");
		ingestionPage.OverlayIngestionForDATWithMappingFieldSection(Input.HiddenPropertiesFolder,
				Input.YYYYMMDDHHMISSDat, Input.sourceDocIdSearch);
		baseClass.stepInfo("Saved ingestion as draft after clicking next button and mapping field enabled");
		ingestionPage.verifyIngestionStatusAfterSaveAsDraft();
		// click on open in wizard option to edit ingestion
		ingestionPage.IngestionFromDraftMode();
		ingestionPage.ingestionCatalogging();

	}
	
	
	/**
	 * Author :Arunkumar date: 26/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-47600 Description :Edit of saved Overlay with out ingestion
	 * with out mapping field selection.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 34)
	public void verifyEditOverlayIngestionWithoutDatAndIngestionMapping() {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47600");
		baseClass.stepInfo("Edit of saved Overlay with out DAT ingestion with out mapping field selection.");
		ingestionPage.OverlayForNativeWithoutIngestion(Input.HiddenPropertiesFolder, Input.YYYYMMDDHHMISSLst);
		baseClass.stepInfo("Saved ingestion as draft without Dat selection and mapping field in disabled state");
		ingestionPage.verifyIngestionStatusAfterSaveAsDraft();
		// click on open in wizard option to edit ingestion
		ingestionPage.IngestionFromDraftMode();
		ingestionPage.ingestionCatalogging();
	}
	
	/**
	 * Author :Arunkumar date: 26/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-47596 Description :Copy of Overlay saved ingestion with
	 * mapping field selection
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 35)
	public void verifyCopyOverlayIngestionWithMapping() {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47596");
		baseClass.stepInfo("Copy of Overlay saved ingestion with mapping field selection");
		ingestionPage.OverlayIngestionForDATWithMappingFieldSection(Input.HiddenPropertiesFolder,
				Input.YYYYMMDDHHMISSDat, Input.sourceDocIdSearch);
		baseClass.stepInfo("Saved ingestion as draft after clicking next button and mapping field enabled");
		ingestionPage.verifyIngestionStatusAfterSaveAsDraft();
		// click on copy option from draft mode
		ingestionPage.IngestionOverlayUsingCopyFromDraftMode("DAT", Input.HiddenPropertiesFolder,
				Input.YYYYMMDDHHMISSLst, Input.YYYYMMDDHHMISSDat, Input.sourceDocIdSearch);
		ingestionPage.ingestionCatalogging();
		// Rollback ingestion
		ingestionPage.rollBackIngestion();
		
	}

	/**
	 * Author :Arunkumar date: 27/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-47599 Description :Copy of saved Overlay without DAT
	 * ingestion with out mapping field selection.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 36)
	public void verifyCopyOverlayIngestionWithoutDatAndMapping() {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47599");
		baseClass.stepInfo("Copy of saved Overlay without DAT ingestion with out mapping field selection.");
		ingestionPage.OverlayForNativeWithoutIngestion(Input.HiddenPropertiesFolder, Input.YYYYMMDDHHMISSLst);
		baseClass.stepInfo("Saved ingestion as draft without Dat selection and mapping field in disabled state");
		ingestionPage.verifyIngestionStatusAfterSaveAsDraft();
		// click on copy option from draft mode
		ingestionPage.IngestionOverlayUsingCopyFromDraftMode("Native", Input.HiddenPropertiesFolder,
				Input.YYYYMMDDHHMISSLst, Input.YYYYMMDDHHMISSDat, Input.sourceDocIdSearch);
		ingestionPage.ingestionCatalogging();
		// Rollback ingestion
		ingestionPage.rollBackIngestion();

	}

	
	/**
	 * Author :Arunkumar date: 04/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-47369 Description :To verify 'Ingestion Details' pop up
	 * display
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 37)
	public void verifyIngestionDetailsPopupDisplay() {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47369");
		baseClass.stepInfo("To verify 'Ingestion Details' pop up display");
		ingestionPage.verifyIngestionDetails();
	}

	/**
	 * Author :Arunkumar date: 05/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-47362 Description :To verify that on Ingestion Home page,
	 * user is able to access all page by navigation controls.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 38)
	public void verifyIngestionHomePageNavigation() {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47362");
		baseClass.stepInfo(
				"To verify that on Ingestion Home page, user is able to access all page by navigation controls.");
		ingestionPage.verifyHomePageNavigationControl();
	}
	
	/**
	 * Author :Arunkumar date: 08/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-49022 Description :Verify two ingestions with step (Indexing
	 * , Approval ) having ingestion type add only must run simultaneously
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 39)
	public void verifyTwoIngestionRunTillApprovingSimultaneously() throws InterruptedException {

		String[] dataset = { Input.AllSourcesFolder, Input.TiffImagesFolder };

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49022");
		baseClass.stepInfo(
				"Verify two ingestions with step (Indexing  , Approval ) having ingestion type add only  must run simultaneously");
		// Verify two add only type ingestion run simultaneously
		ingestionPage.IngestionOnlyForDatFile(Input.AllSourcesFolder, Input.DATFile1);
		ingestionPage.IngestionOnlyForDatFile(Input.TiffImagesFolder, Input.DATFile3);
		ingestionPage.multipleIngestionCopying(2);
		ingestionPage.multipleIngestionIndexing(dataset, 2);
		ingestionPage.approveIngestion(2);
	}
	
	/**
	 * Author :Arunkumar date: 10/05/2022 Modified date: NA Modified by: NA TestCase Id:RPMXCON-47588 
	 * Description :Ingestion with Overlay mode with only load files for PDF or TIFF or Native file type
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 40)
	public void verifyOverlayIngestionWithOnlyLoadFiles() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47588");
		baseClass.stepInfo("Ingestion with Overlay mode with only load files for PDF or TIFF or Native file type");

		// perform add only ingestion
		boolean status = ingestionPage.verifyIngestionpublish(Input.AK_NativeFolder);
		if (status == false) {
			ingestionPage.performAKNativeFolderIngestion(Input.DATFile1);
			ingestionPage.ingestionCatalogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.AK_NativeFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}
		// perform pdf overlay
		baseClass.stepInfo("Performing overlay ingestion with PDF");
		ingestionPage.OverlayIngestionWithoutDat(Input.AK_NativeFolder, "Pdf", Input.PDFFile);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		
	}

	/**
	 * Author :Arunkumar date: 08/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-47594 Description :To Verify for Approved ingestions, there
	 * should not have any option for Rollback.
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 41)
	public void verifyRollbackStatusForApprovedIngestion() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47594");
		baseClass.stepInfo("To Verify for Approved ingestions, there should not have any option for Rollback.");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return ingestionPage.getFilterByButton().Visible();
			}
		}), Input.wait30);
		ingestionPage.getFilterByButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return ingestionPage.getFilterByAPPROVED().Visible();
			}
		}), Input.wait30);
		ingestionPage.getFilterByAPPROVED().waitAndClick(10);
		ingestionPage.getRefreshButton().waitAndClick(10);
		// Verify rollback option for approved ingestion
		if (ingestionPage.getIngestionDetailPopup(1).isElementAvailable(5)) {
			baseClass.stepInfo("Ingestion already present in approved stage");
			ingestionPage.verifyRollbackOptionForApprovedIngestion();
		} else {
			baseClass.stepInfo("Need to perform new ingestion");
			ingestionPage.IngestionOnlyForDatFile(Input.AllSourcesFolder, Input.DATFile1);
			ingestionPage.IngestionCatlogtoCopying(Input.AllSourcesFolder);
			ingestionPage.ingestionIndexing(Input.AllSourcesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.verifyRollbackOptionForApprovedIngestion();

		}
	}
	
	/**
	 * Author: Mohan date: 02/05/2022 Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-49521 Description :Verify that if PA ingested both TEXT and TIFF's
	 * file,the "Generate Searchable PDFs"is true and TIFF is missing then it should
	 * displays default PDF file
	 * 
	 * @throws InterruptedException
	 */
    @Test(enabled = false, groups = { "regression" }, priority = 42)
	public void verifyTEXTAndTIFFSFileGenerateSearchablePDFsIsTrue() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		String ingestionType="Add Only";
		baseClass.stepInfo("Test case Id: RPMXCON-49521");
		baseClass.stepInfo(
				"Verify that if PA ingested both TEXT and TIFF's file,the 'Generate Searchable PDFs' is true and TIFF is missing then it should displays default PDF file");
		boolean status = ingestionPage.verifyIngestionpublish(Input.PP_PDFGen_10Docs);
		System.out.println(status);

		if (status == false) {

			ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,ingestionType, "TRUE", Input.DATPPPDF10Docs, null,
					Input.TextPPPDF10Docs, null, Input.ImagePPPDF10docs,"select", null, null, null);
			
		}
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicSearchWithMetaDataQuery(Input.sourceDocIDPPPDF10Docs, "SourceDocID");
		sessionSearch.viewInDocView();
		DocViewPage docViewPage = new DocViewPage(driver);

		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocView_TextTab());
		docViewPage.getDocView_TextTab().waitAndClick(5);
		String text = docViewPage.getDocViewDefaultViewText().getText();
		if (text.contains("There is no file")) {
			baseClass.passedStep(
					"In text tab it displayed as 'There is no file associated with this document on text view'");
		} else {
			baseClass.failedStep("There is no such message");
		}

		loginPage.logout();

	}

	/**
	 * Author: Vijaya.Rani date: 02/05/2022 Modified date: NA Modified by: NA
	 * Description :Verify when user ingest only metadata, message like 'No files
	 * associated with this document' should be displayed on
	 * text/Images/Translations view.'RPMXCON-51236'
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = false, groups = { "regression" }, priority = 43)
	public void verifyIngestMetaDataMessageDisplayTEXTFile() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		String ingestionType="Add Only";
		baseClass.stepInfo("Test case Id: RPMXCON-51236");
		baseClass.stepInfo(
				"Verify when user ingest only metadata, message like 'No files associated with this document' should be displayed on text/Images/Translations view");

		boolean status = ingestionPage.verifyIngestionpublish(Input.PP_PDFGen_10Docs);
		System.out.println(status);

		if (status == false) {
			ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,ingestionType, "TRUE", Input.DATPPPDF10Docs, null,
					Input.TextPPPDF10Docs, null, Input.ImagePPPDF10docs, "select",null, null, null);
		}

		SessionSearch sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("step 1:Search Metadata SourceDocID go to docView ");
		sessionSearch.basicSearchWithMetaDataQuery(Input.sourceDocIDPPPDF10Docs, "SourceDocID");
		sessionSearch.viewInDocView();
		DocViewPage docViewPage = new DocViewPage(driver);

		driver.waitForPageToBeReady();
		baseClass.stepInfo("step 2:Select Text Tab in Docview");
		baseClass.waitForElement(docViewPage.getDocView_TextTab());
		docViewPage.getDocView_TextTab().waitAndClick(5);
		String text = docViewPage.getDocViewDefaultViewText().getText();
		if (text.contains("There is no file")) {
			baseClass.passedStep(
					"In text tab it displayed as 'There is no file associated with this document on text view'");
		} else {
			baseClass.failedStep("There is no such message");
		}

		loginPage.logout();

	}

	/**
	 * Author: Vijaya.Rani date: 02/05/2022 Modified date: NA Modified by: NA
	 * Description :Verify when user ingest only metadata, 'No files associated with
	 * this document' message should be displayed on default view.'RPMXCON-51235'
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = false, groups = { "regression" }, priority = 44)
	public void verifyIngestMetaDataMessageDisplayDefaultAndTEXTFile() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51235");
		baseClass.stepInfo(
				"Verify when user ingest only metadata, 'No files associated with this document' message should be displayed on default view");

		String ingestionType="Add Only";
		boolean status = ingestionPage.verifyIngestionpublish(Input.PP_PDFGen_10Docs);
		System.out.println(status);

		if (status == false) {
			ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,ingestionType, "TRUE", Input.DATPPPDF10Docs, null,
					Input.TextPPPDF10Docs, null, Input.ImagePPPDF10docs,"select", null, null, null);
		}


		
		SessionSearch sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("step 1:Search Metadata SourceDocID go to docView ");
		sessionSearch.basicSearchWithMetaDataQuery(Input.sourceDocIDPPPDF10Docs, "SourceDocID");
		sessionSearch.viewInDocView();
		
		baseClass.stepInfo("step 2:Select Default PDF Tab in Docview");
		baseClass.waitForElement(docViewPage.getDocView_DefaultViewTab());
		docViewPage.getDocView_DefaultViewTab().waitAndClick(5);
		if (docViewPage.getDocview_DefaultTextArea().Displayed()) {
			baseClass.passedStep(
					"In default tab it displayed as 'This record does not contain a PDF rendering,A TIFF rendering,TEXT or a supported native file type.In order to view This document,you must download the native and view it locally.'");
			baseClass.passedStep(
					"In default tab it displayed as 'Alternatively,contact your review manager who can engage with the data processing team to see if a PDF rendering of the document can be created so it is viewable here.'");
		} else {
			baseClass.failedMessage("There is no such message");
		}

		// TEXT tab Verification
		driver.waitForPageToBeReady();
		baseClass.stepInfo("step 3:Select Text Tab in Docview");
		baseClass.waitForElement(docViewPage.getDocView_TextTab());
		docViewPage.getDocView_TextTab().waitAndClick(5);
		String text = docViewPage.getDocViewDefaultViewText().getText();
		if (text.contains("There is no file")) {
			baseClass.passedStep(
					"In text tab it displayed as 'There is no file associated with this document on text view'");
		} else {
			baseClass.failedStep("There is no such message");
		}
		loginPage.logout();

	}

	/**
	 * Author: Vijaya.Rani date: 02/05/2022 Modified date: NA Modified by: NA
	 * Description :Verify when user ingest only metadata, error PDF should be
	 * displayed on Images view.'RPMXCON-51237'
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = false, groups = { "regression" }, priority = 45)
	public void verifyIngestMetaDataMessageDisplayIMAGEAndTEXTFile() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51237");
		baseClass.stepInfo("Verify when user ingest only metadata, error PDF should be displayed on Images view");

		String ingestionType="Add Only";
		boolean status = ingestionPage.verifyIngestionpublish(Input.PP_PDFGen_10Docs);
		System.out.println(status);

		if (status == false) {
			ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,ingestionType, "TRUE", Input.DATPPPDF10Docs, null,
					Input.TextPPPDF10Docs, null, Input.ImagePPPDF10docs,"select", null, null, null);
		}


		SessionSearch sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("step 1:Search Metadata SourceDocID go to docView ");
		sessionSearch.basicSearchWithMetaDataQuery(Input.sourceDocIDPPPDF10Docs, "SourceDocID");
		sessionSearch.viewInDocView();
		DocViewPage docViewPage = new DocViewPage(driver);
		// DEFAULTView Tab Verification
		driver.waitForPageToBeReady();
		baseClass.stepInfo("step 2:Select Default PDF Tab IN Docview");
		baseClass.waitForElement(docViewPage.getDocView_DefaultViewTab());
		docViewPage.getDocView_DefaultViewTab().waitAndClick(5);
		if (docViewPage.getDocview_DefaultTextArea().Displayed()) {
			baseClass.passedStep(
					"In default tab it displayed as 'This record does not contain a PDF rendering,A TIFF rendering,TEXT or a supported native file type.In order to view This document,you must download the native and view it locally.'");
			baseClass.passedStep(
					"In default tab it displayed as 'Alternatively,contact your review manager who can engage with the data processing team to see if a PDF rendering of the document can be created so it is viewable here.'");
		} else {
			baseClass.failedMessage("There is no such message");
		}

		// TEXT tab Verification
		driver.waitForPageToBeReady();
		baseClass.stepInfo("step 3:Select Text Tab in Docview");
		baseClass.waitForElement(docViewPage.getDocView_TextTab());
		docViewPage.getDocView_TextTab().waitAndClick(5);
		String text2 = docViewPage.getDocViewDefaultViewText().getText();
		if (text2.contains("There is no file")) {
			baseClass.passedStep(
					"In text tab it displayed as 'There is no file associated with this document on text view'");
		} else {
			baseClass.failedStep("There is no such message");
		}
		loginPage.logout();
	}

	/**
	 * Author: Vijaya.Rani date: 04/05/2022 Modified date: NA Modified by: NA
	 * Description :Verify when user ingest only dat file, only metadata should get
	 * ingested.'RPMXCON-51238'
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = false, groups = { "regression" }, priority = 46)
	public void verifyIngestMetaDataDATFileIsIngested() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51238");
		baseClass.stepInfo("Verify when user ingest only dat file, only metadata should get ingested.");

		String ingestionType="Add Only";
		boolean status = ingestionPage.verifyIngestionpublish(Input.PP_PDFGen_10Docs);
		System.out.println(status);

		if (status == false) {
			ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,ingestionType, "TRUE", Input.DATPPPDF10Docs, null,
					Input.TextPPPDF10Docs, null, Input.ImagePPPDF10docs,"select", null, null, null);
		}

		

		SessionSearch sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("step 1:Search Metadata SourceDocID go to docView ");
		sessionSearch.basicSearchWithMetaDataQuery(Input.sourceDocIDPPPDF10Docs, "SourceDocID");
		sessionSearch.viewInDocView();
		DocViewPage docViewPage = new DocViewPage(driver);
		// DEFAULTView Tab Verification
		driver.waitForPageToBeReady();
		baseClass.stepInfo("step 2:Select Default PDF Tab in Docview");
		baseClass.waitForElement(docViewPage.getDocView_DefaultViewTab());
		docViewPage.getDocView_DefaultViewTab().waitAndClick(5);
		if (docViewPage.getDocview_DefaultTextArea().Displayed()) {
			baseClass.passedStep(
					"In default tab it displayed as 'This record does not contain a PDF rendering,A TIFF rendering,TEXT or a supported native file type.In order to view This document,you must download the native and view it locally.'");
			baseClass.passedStep(
					"In default tab it displayed as 'Alternatively,contact your review manager who can engage with the data processing team to see if a PDF rendering of the document can be created so it is viewable here.'");
		} else {
			baseClass.failedMessage("There is no such message");
		}
		loginPage.logout();
	}

	/**
	 * Author: Vijaya.Rani date: 04/05/2022 Modified date: NA Modified by: NA
	 * Description :Verify Ingestion should published successfully if Email metadata is having only Name.'RPMXCON-49569'
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = false, groups = { "regression" }, priority = 47)
	public void verifyIngestionEmailMetaDataOnlyName() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		DocListPage docList = new DocListPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49569");
		baseClass.stepInfo("Verify Ingestion should published successfully if Email metadata is having only Name.");
		String[] addEmailColumn = { "EmailAuthorNameAndAddress", "EmailBCCNamesAndAddresses", "EmailCCNamesAndAddresses", "EmailToNamesAndAddresses" };

		String ingestionType="Add Only";
		


		boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
		
		System.out.println(status);



		if (status == false) {
			baseClass.stepInfo("Edit of addonly saved ingestion with mapping field selection");
			ingestionPage.IngestionRegressionForDifferentDAT(Input.GD994NativeTextForProductionFolder, ingestionType, Input.sourceSystem,
					Input.DATFile1, null, null, null, null, null, Input.MP3File, null, null);

		}
		
		baseClass.stepInfo("Search the documents and Save");
		sessionSearch.basicSearchWithMetaDataQuery("8B61_GD_994_Native_Text_ForProduction_20220413074025033", "IngestionName");
		sessionSearch.ViewInDocList();
		
		docList.SelectColumnDisplayByRemovingExistingOnes(addEmailColumn);
		driver.waitForPageToBeReady();
		for(String metadata : addEmailColumn) {
			baseClass.visibleCheck(metadata);
		}
		baseClass.stepInfo("Email metadata is display correctly in doc list");
		
		//verify Emailname is Display
		String emailName = docList.getDocList_EmailName().getText();
		System.out.println(emailName);
		if(docList.getDocList_EmailName().Displayed()) {
			baseClass.passedStep("Email name is displayed successsfully");
		}
		else {
			baseClass.failedStep("Email name is not displayed");
		}

		//verify emailAddress Is Blank
		if(emailName.contains("@")) {
			baseClass.failedStep("Email Address is displayed");
		}
		else {
			baseClass.passedStep ("Email Address is blank");
		}
		loginPage.logout();
		}
	
	
	/** 
     *Author: Mohan date: 02/05/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49520
	 * Description :Verify that if PA ingested both PDF and TIFF's file,the "Generate Searchable PDFs"is true and TIFF is missing then it PDF should displays PDF in viewer
     * @throws InterruptedException 
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 48)
	public void verifyPDFAndTIFFSFileGenerateSearchablePDFsIsTrue() throws InterruptedException  {
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49520");
		baseClass.stepInfo("Verify that if PA ingested both PDF and TIFF's file,the 'Generate Searchable PDFs' is true and TIFF is missing then it PDF should displays PDF in viewer");
		String ingestionType="Overlay Only";
		
		ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,ingestionType, null, Input.DATPPPDF10Docs, null,
					null, "PDFs - 5Docs.lst", Input.ImagePPPDF10docs,"Select", null, null, null);
			ingestionPage.navigateToAnalyticsPage();
			ingestionPage.runFullAnalysisAndPublish();
			this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		
		
		driver.Navigate().refresh();
		String ingestionName = ingestionPage.selectPublishedFromFilterDropDown(Input.PP_PDFGen_10Docs);
		System.out.println(ingestionName);
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicSearchWithMetaDataQuery(ingestionName, "IngestionName");
		sessionSearch.viewInDocView();
		DocViewPage docViewPage = new DocViewPage(driver);

		driver.waitForPageToBeReady();
		docViewPage.selectDocIdInMiniDocList(Input.sourceDocIDPPPDF10Docs);
		baseClass.waitForElement(docViewPage.getDocView_DefaultViewTab());
		docViewPage.getDocView_DefaultViewTab().waitAndClick(5);
		String text = docViewPage.getDocViewDefaultViewPDF().getText();
		if (text.contains("PDF")) {
			baseClass.passedStep(
					"PDF is displays in PDF viewer");
		} else {
			baseClass.failedStep("There is no such message");
		}

		loginPage.logout();
		

	}
	
	/** 
     *Author: Mohan date: 06/05/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49519
	 * Description :Verify that if PA ingested both Native and TIFF's file, the "Generate Searchable PDFs"is true and TIFF is missing then searchable PDF's should be generated from the Natives.
     * @throws InterruptedException 
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 49)
	public void verifyNativeAndTIFFSFileGenerateSearchablePDFsIsTrue() throws InterruptedException  {
		
	
		ingestionPage = new IngestionPage_Indium(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-49519");
		baseClass.stepInfo("Verify that if PA ingested both Native and TIFF's file, the 'Generate Searchable PDFs' is true and TIFF is missing then searchable PDF's should be generated from the Natives.");
		String ingestionType="Overlay Only";
		
		ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,ingestionType, null, Input.DATPPPDF10Docs, "Natives -5Docs.lst",
					null,null , Input.ImagePPPDF10docs,"Select", null, null, null);
			ingestionPage.navigateToAnalyticsPage();
			ingestionPage.runFullAnalysisAndPublish();
			ingestionPage.navigateToIngestionPage();
		
		
		driver.Navigate().refresh();
		String ingestionName = ingestionPage.selectPublishedFromFilterDropDown(Input.PP_PDFGen_10Docs);
		System.out.println(ingestionName);
		baseClass.waitForElement(ingestionPage.firstTileTitle());
		ingestionPage.firstTileTitle().waitAndClick(5);
		
		driver.scrollingToElementofAPage(ingestionPage.getIngestion_CopyingTableValue(1, 5));
		
		String text = ingestionPage.getIngestion_CopyingTableValue(1, 5).getText();
		System.out.println(text);
		String text2 = ingestionPage.getIngestion_CopyingTableValue(9, 5).getText();
		System.out.println(text2);
		
		if (text.equalsIgnoreCase(text2)) {
			baseClass.passedStep(
					"Searchable PDF is been generated from the Natives");
		} else {
			baseClass.failedStep("There is no such message");
		}

		loginPage.logout();
	}
	
	/** 
     *Author: Mohan date: 06/05/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-50377
	 * Description :Verify that if PA ingested Native, PDF and TIFF's file and the "Generate Searchable PDFs" option is set to true, then PDF should be generated from the TIFF's
     * @throws InterruptedException 
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 50)
	public void verifyNativePDFAndTIFFSFileGenerateSearchablePDFsIsTrue() throws InterruptedException  {
		
	
		ingestionPage = new IngestionPage_Indium(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-50377");
		baseClass.stepInfo("Verify that if PA ingested Native, PDF and TIFF's file and the 'Generate Searchable PDFs' option is set to true, then PDF should be generated from the TIFF's");
		String ingestionType="Add Only";
		boolean status = ingestionPage.verifyIngestionpublishWithAdditionalOptions(Input.PP_PDFGen_10Docs,"50");
		System.out.println(status);

		if(status==false) {
		ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,ingestionType, "TRUE", "DAT4_STC_09172014_newdateformat_50Docs.dat", "Natives_50Docs.lst",
					null,"PDFs_50Docs.lst" , "Images.lst","Select", null, null, null);
		}
		
			driver.Navigate().refresh();
			String ingestionFullName = ingestionPage.isDataSetisAvailable(Input.PP_PDFGen_10Docs,"50");
		if (ingestionFullName != null) {
			ingestionPage.selectDataSetWithNameInDocView(Input.PP_PDFGen_10Docs);
			String name = docViewPage.getDefaultViewerFileType().GetAttribute("xlink:href");
			System.out.println(name);
			if (name.contains("image")) {
				baseClass.passedStep("Tiff file only displayed in default viewer");
			} else {
				baseClass.failedStep("verification failed");
			}
		}

			loginPage.logout();
	}
	
	/** 
     *Author: Mohan date: 06/05/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49509
	 * Description :Verify that if PA ingested both native's and TIFF's file,and the "Generate Searchable PDFs" option is set to false then it should display TIFF in default viewer
     * @throws InterruptedException 
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 51)
	public void verifyNativeAndTIFFSFileGenerateSearchablePDFsIsFalse() throws InterruptedException  {
		
	
		ingestionPage = new IngestionPage_Indium(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49509");
		baseClass.stepInfo("Verify that if PA ingested both native's and TIFF's file,and the \"Generate Searchable PDFs\" option is set to false then it should display TIFF in default viewer");
		String ingestionType="Overlay Only";
		
		ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,ingestionType, null, Input.DATPPPDF10Docs, "Natives -5Docs.lst",
					null,null , Input.ImagePPPDF10docs,null, null, null, null);
			ingestionPage.navigateToAnalyticsPage();
			ingestionPage.runFullAnalysisAndPublish();
			ingestionPage.navigateToIngestionPage();
		
		
		driver.Navigate().refresh();
		driver.Navigate().refresh();
		String ingestionFullName = ingestionPage.isDataSetisAvailable(Input.PP_PDFGen_10Docs,"5");
	if (ingestionFullName != null) {
		ingestionPage.selectDataSetWithNameInDocView(Input.PP_PDFGen_10Docs);
		String name = docViewPage.getDefaultViewerFileType().GetAttribute("xlink:href");
		System.out.println(name);
		if (name.contains("image")) {
			baseClass.passedStep("Application displays 'TIFF file' in default viewer");
		} else {
			baseClass.failedStep("verification failed");
		}
	}

		loginPage.logout();
	}
	
	
	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		Reporter.setCurrentTestResult(result);
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
			try { // if any tc failed and dint logout!
				loginPage.logoutWithoutAssert();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		UtilityLog.info("Executed :" + result.getMethod().getMethodName());
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			loginPage.closeBrowser();
		} finally {
			System.out.println("******TEST CASES FOR INGESTION EXECUTED******");
		}

	}
		

	

}