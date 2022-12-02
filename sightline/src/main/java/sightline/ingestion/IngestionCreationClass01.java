package sightline.ingestion;

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

		Input in = new Input();
		in.loadEnvConfig();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());

		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		ingestionPage = new IngestionPage_Indium(driver);

		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
	}

	
	
	/**
	 * Author :Arunkumar date: 29/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-47566 Description :To Verify status update in cataloging,
	 * copying, and Indexing Section in Ingestion Details Page
	 */
	@Test(description ="RPMXCON-47566",enabled = true, groups = { "regression" } )
	public void verifyStatusUpdateInPopup() throws InterruptedException {

		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Test case Id: RPMXCON-47566");
		baseClass.stepInfo(
				"To Verify status update in cataloging, copying, and Indexing Section in Ingestion Details Page");
		boolean status = ingestionPage.verifyIngestionpublish(Input.UniCodeFilesFolder);
		System.out.println(status);
		if (status == false) {
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

	}
	
	/**
	 * Author :Arunkumar date: 02/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-47404 Description :To verify that if configure mapping is not
	 * matched the warning message is displayed and as per selection, admin can
	 * proceed.
	 * 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47404",enabled = true, groups = { "regression" } )
	public void verifyConfigureMappingWarningMessage() throws InterruptedException {

		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Test case Id: RPMXCON-47404");
		baseClass.stepInfo(
				"verify that if configure mapping is not matched the warning message is displayed and as per selection, admin can proceed.");
		boolean status = ingestionPage.verifyIngestionpublish(Input.UniCodeFilesFolder);
		System.out.println(status);
		if (status == false) {
		ingestionPage.unicodeFilesIngestion(Input.datLoadFile1, Input.textFile1, Input.documentKey);
		ingestionPage.ingestionCatalogging();
		ingestionPage.verifyWarningMessageForConfigureMappingSection(Input.UniCodeFilesFolder, Input.datLoadFile1,
				Input.textFile1, Input.prodBeg, Input.documentKey);
		loginPage.logout();
		}

	}
	
	/** 
	 * Author :Arunkumar date: 29/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-48175 Description :To Verify Rollback In Ingestion for Files
	 * having Read Only Attributes.
	 * @throws InterruptedException 
	 */
	@Test(description ="RPMXCON-48175",enabled = true, groups = { "regression" } )
	public void verifyRollbackReadOnlyAttribute() throws InterruptedException {

		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Test case Id: RPMXCON-48175");
		baseClass.stepInfo("To Verify Rollback In Ingestion for Files having Read Only Attributes.");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AK_NativeFolder);
		System.out.println(status);
		if (status == false) {
		ingestionPage.performAKNativeFolderIngestion(Input.DATFile1);
		ingestionPage.ingestionCatalogging();
		ingestionPage.ignoreErrorsAndCopying();
		ingestionPage.ingestionIndexing(Input.AK_NativeFolder);
		// Rollback
		ingestionPage.rollBackIngestion();
		loginPage.logout();
		}

	}
	
	/**
	 * Author :Arunkumar date: 02/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-49703 Description :Verify if 'Generate Searchable PDFs' is
	 * True for TIFF image and document has multi-page TIFF's then in the Copying
	 * section, Stitching TIFFs details should display before the "Generate
	 * Searchable PDFs" row on Ingestion details pop up
	 */
	@Test(description ="RPMXCON-49703",enabled = true, groups = { "regression" } )
	public void verifyStitchedTiffBeforeGeneratedSearchablePDFs() throws InterruptedException {

		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Test case Id: RPMXCON-49703");
		baseClass.stepInfo(
				"Verify if 'Generate Searchable PDFs' is True Stitching TIFFs details should display before the Generate Searchable PDFs row on Ingestion details pop up");
		boolean status = ingestionPage.verifyIngestionpublish(Input.TiffImagesFolder);
		System.out.println(status);
		if (status == false) {
		ingestionPage.tiffImagesIngestion(Input.DATFile4, Input.tiffLoadFile, "true");
		ingestionPage.ignoreErrorsAndCatlogging();
		ingestionPage.ignoreErrorsAndCopying();
		ingestionPage.verifyTermPositionInCopyColumn(Input.StitchedTIFF);
		// Rollback Ingestion
		ingestionPage.rollBackIngestion();
		loginPage.logout();
		}
	}
	
	/**
	 * Author :Arunkumar date: 02/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-48194 Description :To Verify In Ingestions the pop-up Details
	 * Window for media ;audio & Transcript Count.
	 */
	@Test(description ="RPMXCON-48194",enabled = true, groups = { "regression" } )
	public void verifyMediaAndTranscriptDetailInPopup() throws InterruptedException {

		String[] selectedTerm = { "Native", "Text", "MP3 Variant", "Audio Transcript" };
		String[] unselectedTerm = { "PDF", Input.StitchedTIFF, Input.generateSearchablePDF };

		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Test case Id: RPMXCON-48194");
		baseClass.stepInfo("To Verify In Ingestions the pop-up Details Window for media ;audio & Transcript Count.");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		System.out.println(status);
		if (status == false) {
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
	}
	
	/**
	 * Author :Arunkumar date: 05/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-47402 Description :To verify "Copy Ingestion" with ingestion
	 * wizard (Using link "Add Ingestion(New approach)")
	 * 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47402",enabled = true, groups = { "regression" } )
	public void verifyCopyIngestionUsingNewApproach() throws InterruptedException {

		
		baseClass.stepInfo("Test case Id: RPMXCON-47402");
		baseClass.stepInfo(
				"To verify 'Copy Ingestion' with ingestion wizard (Using link 'Add Ingestion(New approach)')");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.UniCodeFilesFolder);
		System.out.println(status);
		if (status == false) {
		ingestionPage.unicodeFilesIngestion(Input.datLoadFile1, Input.textFile1, Input.documentKey);
		ingestionPage.ingestionCatalogging();
		ingestionPage.performNewAddOnlyIngestionUsingCopyOption("Mapped Data", Input.HiddenPropertiesFolder,
				Input.YYYYMMDDHHMISSDat, Input.sourceDocIdSearch);
		loginPage.logout();
		}

	}
	
	
	/**
	 * Author :Arunkumar date: 05/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-49282 Description :To Verify Ingestion Add Only end to End
	 * Flow with Source System as Mapped Data
	 * 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49282",enabled = true, groups = { "regression" } )
	public void verifyAddonlyIngestionWithMappedData() throws InterruptedException {

		
		baseClass.stepInfo("Test case Id: RPMXCON-49282");
		baseClass.stepInfo("To Verify Ingestion Add Only end to End Flow with Source System as Mapped Data");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
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
	@Test(description ="RPMXCON-49467",enabled = true, groups = { "regression" } )
	public void verifyErrorMessageIfDocidExists() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49467");
		baseClass.stepInfo(
				"Verify error message displays if adding same source Doc ID which is already exists in the DB");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
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
	@Test(description ="RPMXCON-49466",enabled = true, groups = { "regression" } )
	public void verifyErrorMessageIfDocIdNotAvailable() throws InterruptedException {

		
		baseClass.stepInfo("Test case Id: RPMXCON-49466");
		baseClass.stepInfo(
				"Verify error message if the source system is matching and if the doc ID is not available in the database");
		// perform add only ingestion
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.UniCodeFilesFolder);
		System.out.println(status);
		if (status == false) {
			ingestionPage.unicodeFilesIngestionWithDifferentSourceSystem("TRUE", Input.datLoadFile1, Input.textFile1,
					Input.documentKey);
			ingestionPage.IngestionCatlogtoCopying(Input.UniCodeFilesFolder);
			ingestionPage.ingestionIndexing(Input.UniCodeFilesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}
		// Verify error message for overlay ingestion if docid not available
		ingestionPage.navigateToIngestionPage();
		boolean ingestStatus = ingestionPage.verifyIngestionpublish(Input.HiddenPropertiesFolder);
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
	@Test(description ="RPMXCON-46868",enabled = true, groups = { "regression" } )
	public void verifyAddOnlyIngestionForAudioFiles() throws InterruptedException {

		
		baseClass.stepInfo("Test case Id: RPMXCON-46868");
		baseClass.stepInfo("Add only ingestion with only .mp3 audio files with MP3 Variant file types.");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		// perform add only ingestion
		boolean status = ingestionPage.verifyIngestionpublish(Input.audio96DocsFolder);
		System.out.println(status);
		if (status == false) {
			ingestionPage.performAudio96DocsIngestion(Input.audioDatFile, Input.docIdKey);
			ingestionPage.ingestionCatalogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.audio96DocsFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
			loginPage.logout();
		}
	}
	
	/**
	 * Author :Arunkumar date: 10/05/2022 Modified date: NA Modified by: NA TestCase Id:RPMXCON-47842 
	 * Description :Add only ingestion with only .mp3 audio files with MP3 Variant file types. 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47842",enabled = true, groups = { "regression" } )
	public void verifyAddOnlyIngestionForMp3VariantFileTypes() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47842");
		baseClass.stepInfo("Add only ingestion with only .mp3 audio files with MP3 Variant file types.");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
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
		loginPage.logout();

	}
	
	/**
	 * Author :Arunkumar date: 09/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-48147 Description :To Verify User is able to Ingest MP3 File
	 * Variant along with native
	 * 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48147",enabled = true, groups = { "regression" } )
	public void verifyAddOnlyIngestionForMp3WithNative() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48147");
		baseClass.stepInfo("To Verify User is able to Ingest MP3  File Variant along with native");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
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
		loginPage.logout();

	}
	
	/**
	 * Author :Arunkumar date: 20/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-47293 Description :Ingesting Duplicate files.
	 */
	@Test(description ="RPMXCON-47293",enabled = true, groups = { "regression" } )
	public void verifyErrorForIngestingDuplicateFiles() throws InterruptedException {

		
		baseClass.stepInfo("Test case Id: RPMXCON-47293");
		baseClass.stepInfo("Ingesting Duplicate files.");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.UniCodeFilesFolder);
		System.out.println(status);
		if (status == false) {
			ingestionPage.unicodeFilesIngestion(Input.datLoadFile1, Input.textFile1, Input.documentKey);
			ingestionPage.IngestionCatlogtoCopying(Input.UniCodeFilesFolder);
			ingestionPage.ingestionIndexing(Input.UniCodeFilesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}
		// Verify error message for ingesting duplicate files
		ingestionPage.unicodeFilesIngestion(Input.datLoadFile1, Input.textFile1, Input.documentKey);
		ingestionPage.verifyDuplicateIngestionErrorMessage();
		// rollback ingestion
		ingestionPage.rollBackIngestion();
		loginPage.logout();
	}

	
	/**
	 * Author :Arunkumar date: 09/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-49258 Description :To verify that total unique ingested
	 * document count displays unique count if user perform only PDF overlay
	 * 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49258",enabled = true, groups = { "regression" } )
	public void verifyUniqueCountForOnlyPdfOverlay() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49258");
		baseClass.stepInfo(
				"To verify that total unique ingested document count displays unique count if user perform only PDF overlay");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
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
		baseClass.stepInfo("Total unique count after performing overlay : '" + uniqueCountAfter + "'");
		if (uniqueCountBefore == uniqueCountAfter) {
			baseClass.passedStep("Only unique ingested count displayed after performing Pdf overlay");
		} else {
			baseClass.failedStep("Unique ingested count not displayed after performing Pdf overlay");
		}
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: 09/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-49259 Description :To verify that total unique ingested
	 * document count displays unique count if user perform only Native overlay
	 * 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49259",enabled = true, groups = { "regression" } )
	public void verifyUniqueCountForOnlyNativeOverlay() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49259");
		baseClass.stepInfo(
				"To verify that total unique ingested document count displays unique count if user perform only Native overlay");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
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
		baseClass.stepInfo("Total unique count after performing overlay : '" + uniqueCountAfter + "'");
		if (uniqueCountBefore == uniqueCountAfter) {
			baseClass.passedStep("Only unique ingested count displayed after performing Native overlay");
		} else {
			baseClass.failedStep("Unique ingested count not displayed after performing Native overlay");
		}
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: 09/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-49261 Description :To verify that total unique ingested
	 * document count displays unique count if user perform only MP3 overlay
	 * 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49261",enabled = true, groups = { "regression" } )
	public void verifyUniqueCountForOnlyMp3Overlay() throws InterruptedException {


		baseClass.stepInfo("Test case Id: RPMXCON-49261");
		baseClass.stepInfo(
				"To verify that total unique ingested document count displays unique count if user perform only MP3 overlay");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
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
		// getting unique ingested count before overlay
		int uniqueCountBefore = ingestionPage.getIngestedUniqueCount();
		baseClass.stepInfo("Total unique count before performing overlay : '" + uniqueCountBefore + "'");
		// perform Mp3 overlay
		baseClass.stepInfo("Performing overlay ingestion with Native");
		ingestionPage.OverlayIngestionWithoutDat(Input.AK_NativeFolder, "mp3", Input.MP3File);
		ingestionPage.ignoreErrorsAndCatlogging();
		ingestionPage.ignoreErrorsAndCopying();
		ingestionPage.ingestionIndexing(Input.AK_NativeFolder);
		ingestionPage.approveIngestion(1);
		ingestionPage.runFullAnalysisAndPublish();
		// getting unique ingested count after overlay
		int uniqueCountAfter = ingestionPage.getIngestedUniqueCount();
		baseClass.stepInfo("Total unique count after performing overlay : '" + uniqueCountAfter + "'");
		if (uniqueCountBefore == uniqueCountAfter) {
			baseClass.passedStep("Only unique ingested count displayed after performing Mp3 overlay");
		} else {
			baseClass.failedStep("Unique ingested count not displayed after performing Mp3 overlay");
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 10/05/2022 Modified date: NA Modified by: NA TestCase Id:RPMXCON-47587 
	 * Description :Ingestion with Overlay mode with only DAT file along with PDF or TIFF or Native file type 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47587",enabled = true, groups = { "regression" } )
	public void verifyOverlayIngestionWithDatAlongWithNative() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47587");
		baseClass.stepInfo("Ingestion with Overlay mode with only DAT file along with PDF or TIFF or Native file type");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
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
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 11/05/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-48591 
	 * Description :Verify the Analytics process should be skipped when Text files are not overlayed .
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48591",enabled = true, groups = { "regression" } )
	public void verifyAnalyticsStatusWhenOverlayDatWithoutText() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48591");
		baseClass.stepInfo("Verify the Analytics process should be skipped when Text files are not overlayed .");
		// perform add only ingestion 
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.UniCodeFilesFolder);
		System.out.println(status);
		if (status == false) {
		ingestionPage.unicodeFilesIngestion(Input.datLoadFile1, Input.textFile1,Input.documentKey);
		ingestionPage.IngestionCatlogtoCopying(Input.UniCodeFilesFolder);
		ingestionPage.ingestionIndexing(Input.UniCodeFilesFolder);
		ingestionPage.approveIngestion(1);
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
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return ingestionPage.getSourceSelectionDATLoadFile().Visible();
			}
		}), Input.wait30);
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
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 11/05/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-48593 
	 * Description :Verify the Analytics process should be skipped when PDF Files are overlayed and Text files are not overlayed.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48593",enabled = true, groups = { "regression" } )
	public void verifyAnalyticsStatusWhenOverlayPdfWithoutText() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48593");
		baseClass.stepInfo("Verify the Analytics process should be skipped when PDF Files are overlayed and Text files are not overlayed .");
		// perform add only ingestion 
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
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
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 11/05/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-48592 
	 * Description :Verify the Analytics process should take places when Text files are overlayed .
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48592",enabled = true, groups = { "regression" } )
	public void verifyAnalyticsStatusWhenOverlayWithText() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48592");
		baseClass.stepInfo("Verify the Analytics process should take places when Text files are overlayed.");
		// perform add only ingestion 
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
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
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 11/05/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-48594
	 * Description :Verify the Analytics process should be skipped when we overlay Audio Files without Text File
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48594",enabled = true, groups = { "regression" } )
	public void verifyAnalyticsStatusWhenOverlayMp3WithoutText() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48594");
		baseClass.stepInfo("Verify the Analytics process should be skipped when we overlay Audio Files without Text File.");
		// perform add only ingestion 
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
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
		//Verify analytic status when mp3 overlay without text
		baseClass.stepInfo("Perform pdf overlay ingestion without text file");
		ingestionPage.OverlayIngestionWithoutDat(Input.AK_NativeFolder,"mp3",Input.MP3File);
		baseClass.stepInfo("Ingestion started");
		ingestionPage.ignoreErrorsAndCatlogging();
		ingestionPage.ignoreErrorsAndCopying();
		ingestionPage.ingestionIndexing(Input.AK_NativeFolder);
		ingestionPage.approveIngestion(1);
		ingestionPage.runAnalyticsAndVerifySkippedStatus();
		loginPage.logout();
		
	}
	

	
	/**
	 * Author :Arunkumar date: 04/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-48267 Description :To Verify Ingestion Overlay Without DAT
	 * for Translation.
	 * 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48267",enabled = true, groups = { "regression" } )
	public void verifyIngestionOverlayWithoutDatForTranslation() throws InterruptedException {

		
		baseClass.stepInfo("Test case Id: RPMXCON-48267");
		baseClass.stepInfo("To Verify Ingestion Overlay Without DAT for Translation.");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Performing add only ingestion");
			ingestionPage.allSourcesIngestionWithText(Input.DATFile, Input.prodBeg);
			ingestionPage.ignoreErrorsAndCatlogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.AllSourcesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}
		// Perform overlay ingestion for translation without DAT
		ingestionPage.OverlayIngestionWithoutDat(Input.AllSourcesFolder, "Translation", Input.TranslationFile);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
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
	@Test(description ="RPMXCON-48266",enabled = true, groups = { "regression" } )
	public void verifyIngestionOverlayWithoutDatForTranscript() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48266");
		baseClass.stepInfo("To Verify Ingestion Overlay Without DAT for Transcript.");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Performing add only ingestion");
			ingestionPage.allSourcesIngestionWithText(Input.DATFile, Input.prodBeg);
			ingestionPage.IngestionCatlogtoCopying(Input.AllSourcesFolder);
			ingestionPage.ingestionIndexing(Input.AllSourcesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}
		// Perform overlay ingestion for transcript without DAT
		ingestionPage.OverlayIngestionWithoutDat(Input.AllSourcesFolder, "Transcript", Input.TranscriptFile);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
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
	@Test(description ="RPMXCON-48265",enabled = true, groups = { "regression" } )
	public void verifyIngestionOverlayWithoutDatForMp3() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48265");
		baseClass.stepInfo("To Verify Ingestion Overlay Without DAT for MP3 Variant.");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Performing add only ingestion");
			ingestionPage.allSourcesIngestionWithText(Input.DATFile, Input.prodBeg);
			ingestionPage.IngestionCatlogtoCopying(Input.AllSourcesFolder);
			ingestionPage.ingestionIndexing(Input.AllSourcesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}
		// Perform overlay ingestion for mp3 without DAT
		ingestionPage.OverlayIngestionWithoutDat(Input.AllSourcesFolder, "mp3", Input.MP3File);
		ingestionPage.ignoreErrorsAndCatlogging();
		ingestionPage.ignoreErrorsAndCopying();
		ingestionPage.ingestionIndexing(Input.AllSourcesFolder);
		ingestionPage.approveIngestion(1);
		ingestionPage.runFullAnalysisAndPublish();
		ingestionPage.verifyDocAvailability();
		loginPage.logout();

	}

	

	/**
	 * Author :Arunkumar date: 04/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-48263 Description :To Verify Ingestion Overlay Without DAT
	 * for PDF.
	 */
	@Test(description ="RPMXCON-48263",enabled = true, groups = { "regression" } )
	public void verifyIngestionOverlayWithoutDatForPdf() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48263");
		baseClass.stepInfo("To Verify Ingestion Overlay Without DAT for PDF");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Performing add only ingestion");
			ingestionPage.allSourcesIngestionWithText(Input.DATFile, Input.prodBeg);
			ingestionPage.IngestionCatlogtoCopying(Input.AllSourcesFolder);
			ingestionPage.ingestionIndexing(Input.AllSourcesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}
		// Perform overlay ingestion for Pdf without DAT
		ingestionPage.OverlayIngestionWithoutDat(Input.AllSourcesFolder, "Pdf", Input.PDFFile);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();
		ingestionPage.verifyDocAvailability();
		loginPage.logout();

	}

	/**
	 * Author :Arunkumar date: 04/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-48262 Description :To Verify Ingestion Overlay Without DAT
	 * for Native.
	 */
	@Test(description ="RPMXCON-48262",enabled = true, groups = { "regression" } )
	public void verifyIngestionOverlayWithoutDatForNative() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48262");
		baseClass.stepInfo("To Verify Ingestion Overlay Without DAT for Native.");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Performing add only ingestion");
			ingestionPage.allSourcesIngestionWithText(Input.DATFile, Input.prodBeg);
			ingestionPage.IngestionCatlogtoCopying(Input.AllSourcesFolder);
			ingestionPage.ingestionIndexing(Input.AllSourcesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}
		// Perform overlay ingestion for Native without DAT
		ingestionPage.OverlayIngestionWithoutDat(Input.AllSourcesFolder, "Native", Input.NativeFile);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();
		ingestionPage.verifyDocAvailability();
		loginPage.logout();

	}
	
	
	
	/**
	 * Author :Arunkumar date: 10/05/2022 Modified date: NA Modified by: NA TestCase Id:RPMXCON-48203 
	 * Description :To Verify Ingestion overlay of Others without Unpublish
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48203",enabled = true, groups = { "regression" } )
	public void verifyOverlayIngestionWithOthers() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48203");
		baseClass.stepInfo("To Verify Ingestion overlay of Others without Unpublish");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		// perform add only ingestion
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		if (status == false) {
			ingestionPage.IngestionOnlyForDatFile(Input.AllSourcesFolder, Input.DATFile);
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
		loginPage.logout();
	}
	
	
	
	/**
	 * Author :Arunkumar date: 11/05/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-48597 
	 * Description :Verify the Analytics process should be skipped when Tiff Files are overlayed without Text Files.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48597",enabled = true, groups = { "regression" } )
	public void verifyAnalyticsStatusWhenOverlayTiffWithoutText() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48597");
		baseClass.stepInfo("Verify the Analytics process should be skipped when Tiff Files are overlayed without Text Files.");
		// perform add only ingestion 
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
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
		loginPage.logout();
		
	}

	/**
	 * Author :Arunkumar date: 08/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-49260 Description :To verify that total unique ingested
	 * document count displays unique count if user perform only TIFF overlay
	 * 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49260",enabled = true, groups = { "regression" } )
	public void verifyUniqueCountForOnlyTiffOverlay() throws InterruptedException {

		
		baseClass.stepInfo("Test case Id: RPMXCON-49260");
		baseClass.stepInfo(
				"To verify that total unique ingested document count displays unique count if user perform only TIFF overlay");
		// perform add only ingestion
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.TiffImagesFolder);
		if (status == false) {
			ingestionPage.tiffImagesIngestion(Input.DATFile2, Input.tiffLoadFile, "false");
			ingestionPage.ignoreErrorsAndCatlogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.TiffImagesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}
		// getting unique ingested count before overlay
		int uniqueCountBefore = ingestionPage.getIngestedUniqueCount();
		System.out.println(uniqueCountBefore);
		baseClass.stepInfo("Total unique count before performing overlay : '" + uniqueCountBefore + "'");
		// perform Tiff overlay
		baseClass.stepInfo("Performing overlay ingestion with Native");
		ingestionPage.OverlayIngestionWithoutDat(Input.TiffImagesFolder, "Tiff", Input.tiffLoadFile);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();
		// getting unique ingested count after overlay
		int uniqueCountAfter = ingestionPage.getIngestedUniqueCount();
		System.out.println(uniqueCountAfter);
		baseClass.stepInfo("Total unique count After performing overlay : '" + uniqueCountAfter + "'");
		if (uniqueCountBefore == uniqueCountAfter) {
			baseClass.passedStep("Only unique ingested count displayed after performing Tiff overlay");
		} else {
			baseClass.failedStep("Unique ingested count not displayed after performing Tiff overlay");
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 04/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-48264 Description :To Verify Ingestion Overlay Without DAT
	 * for TIFF.
	 * 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48264",enabled = true, groups = { "regression" } )
	public void verifyIngestionOverlayWithoutDatForTiff() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48264");
		baseClass.stepInfo("To Verify Ingestion Overlay Without DAT for TIFF.");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
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
		// Perform overlay ingestion for Tiff without DAT
		ingestionPage.OverlayIngestionWithoutDat(Input.TiffImagesFolder, "Tiff", Input.tiffLoadFile);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();
		ingestionPage.verifyDocAvailability();
		loginPage.logout();

	}

	/**
	 * Author :Arunkumar date: 10/05/2022 Modified date: NA Modified by: NA TestCase Id:RPMXCON-48292
	 * Description :To verify In Ingestion, with another New Line delimiter other than ASCII(59)
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48292",enabled = true, groups = { "regression" } )
	public void verifyAddOnlyIngestionWithAnotherNewLineDelimiter() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48292");
		baseClass.stepInfo("To verify In Ingestion, with another New Line delimiter other than ASCII(59)");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		// perform add only ingestion with new line delimiter 
		boolean status = ingestionPage.verifyIngestionpublish(Input.HiddenPropertiesFolder);
		if (status == false) {
			ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder,Input.YYYYMMDDHHMISSDat);
			ingestionPage.IngestionCatlogtoCopying(Input.HiddenPropertiesFolder);
			ingestionPage.ingestionIndexing(Input.HiddenPropertiesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
			baseClass.passedStep("Ingestion performed in this project successfully with New Line delimiter other than ASCII(59)");
		}
		else {
			baseClass.passedStep("Ingestion already performed in this project successfully with New Line delimiter other than ASCII(59)");
		}
		loginPage.logout();

	}
	
	/**
	 * Author :Arunkumar date: 22/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-47598 Description :Edit of saved Overlay ingestion with out
	 * mapping field selection.
	 */
	@Test(description ="RPMXCON-47598",enabled = true, groups = { "regression" } )
	public void verifyEditOverlayIngestionWithoutMapping() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47598");
		baseClass.stepInfo("Edit of saved Overlay ingestion with out mapping field selection.");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.HiddenPropertiesFolder);
		if (status == false) {
			ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder,Input.YYYYMMDDHHMISSDat);
			ingestionPage.IngestionCatlogtoCopying(Input.HiddenPropertiesFolder);
			ingestionPage.ingestionIndexing(Input.HiddenPropertiesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}
		// perform overlay ingestion without mapping	
		ingestionPage.OverlayIngestionForDATWithoutMappingFieldSection(Input.HiddenPropertiesFolder,
				Input.YYYYMMDDHHMISSDat, Input.sourceDocIdSearch);
		baseClass.stepInfo("Saved ingestion as draft without clicking next button and mapping field in disabled state");
		ingestionPage.verifyIngestionStatusAfterSaveAsDraft();
		// click on open in wizard option to edit ingestion
		ingestionPage.IngestionFromDraftMode();
		ingestionPage.ingestionCatalogging();
		loginPage.logout();
		

	}

	/**
	 * Author :Arunkumar date: 22/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-47597 Description :Edit of Overlay saved ingestion with
	 * mapping field selection
	 */
	@Test(description ="RPMXCON-47597",enabled = true, groups = { "regression" } )
	public void verifyEditOverlayIngestionWithMapping() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47597");
		baseClass.stepInfo("Edit of Overlay saved ingestion with mapping field selection");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.HiddenPropertiesFolder);
		if (status == false) {
			ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder,Input.YYYYMMDDHHMISSDat);
			ingestionPage.IngestionCatlogtoCopying(Input.HiddenPropertiesFolder);
			ingestionPage.ingestionIndexing(Input.HiddenPropertiesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}
		// perform overlay ingestion with mapping field section
		ingestionPage.OverlayIngestionForDATWithMappingFieldSection(Input.HiddenPropertiesFolder,
				Input.YYYYMMDDHHMISSDat, Input.sourceDocIdSearch);
		baseClass.stepInfo("Saved ingestion as draft after clicking next button and mapping field enabled");
		ingestionPage.verifyIngestionStatusAfterSaveAsDraft();
		// click on open in wizard option to edit ingestion
		ingestionPage.IngestionFromDraftMode();
		ingestionPage.ingestionCatalogging();
		loginPage.logout();

	}
	
	
	/**
	 * Author :Arunkumar date: 26/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-47600 Description :Edit of saved Overlay with out ingestion
	 * with out mapping field selection.
	 * @throws InterruptedException 
	 */
	@Test(description ="RPMXCON-47600",enabled = true, groups = { "regression" } )
	public void verifyEditOverlayIngestionWithoutDatAndIngestionMapping() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47600");
		baseClass.stepInfo("Edit of saved Overlay with out DAT ingestion with out mapping field selection.");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.HiddenPropertiesFolder);
		if (status == false) {
			ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder,Input.YYYYMMDDHHMISSDat);
			ingestionPage.IngestionCatlogtoCopying(Input.HiddenPropertiesFolder);
			ingestionPage.ingestionIndexing(Input.HiddenPropertiesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}
		ingestionPage.OverlayForNativeWithoutIngestion(Input.HiddenPropertiesFolder, Input.YYYYMMDDHHMISSLst);
		baseClass.stepInfo("Saved ingestion as draft without Dat selection and mapping field in disabled state");
		ingestionPage.verifyIngestionStatusAfterSaveAsDraft();
		// click on open in wizard option to edit ingestion
		ingestionPage.IngestionFromDraftMode();
		ingestionPage.ingestionCatalogging();
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 26/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-47596 Description :Copy of Overlay saved ingestion with
	 * mapping field selection
	 * @throws InterruptedException 
	 */
	@Test(description ="RPMXCON-47596",enabled = true, groups = { "regression" } )
	public void verifyCopyOverlayIngestionWithMapping() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47596");
		baseClass.stepInfo("Copy of Overlay saved ingestion with mapping field selection");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.HiddenPropertiesFolder);
		if (status == false) {
			ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder,Input.YYYYMMDDHHMISSDat);
			ingestionPage.IngestionCatlogtoCopying(Input.HiddenPropertiesFolder);
			ingestionPage.ingestionIndexing(Input.HiddenPropertiesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}
		ingestionPage.OverlayIngestionForDATWithMappingFieldSection(Input.HiddenPropertiesFolder,
				Input.YYYYMMDDHHMISSDat, Input.sourceDocIdSearch);
		baseClass.stepInfo("Saved ingestion as draft after clicking next button and mapping field enabled");
		ingestionPage.verifyIngestionStatusAfterSaveAsDraft();
		// click on copy option from draft mode
		ingestionPage.IngestionOverlayUsingCopyFromDraftMode("DAT", Input.HiddenPropertiesFolder,
				Input.YYYYMMDDHHMISSLst, Input.YYYYMMDDHHMISSDat, Input.sourceDocIdSearch);
		ingestionPage.ingestionCatalogging();
		loginPage.logout();
		
	}

	/**
	 * Author :Arunkumar date: 27/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-47599 Description :Copy of saved Overlay without DAT
	 * ingestion with out mapping field selection.
	 * @throws InterruptedException 
	 */
	@Test(description ="RPMXCON-47599",enabled = true, groups = { "regression" } )
	public void verifyCopyOverlayIngestionWithoutDatAndMapping() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47599");
		baseClass.stepInfo("Copy of saved Overlay without DAT ingestion with out mapping field selection.");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.HiddenPropertiesFolder);
		if (status == false) {
			ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder,Input.YYYYMMDDHHMISSDat);
			ingestionPage.IngestionCatlogtoCopying(Input.HiddenPropertiesFolder);
			ingestionPage.ingestionIndexing(Input.HiddenPropertiesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}
		ingestionPage.OverlayForNativeWithoutIngestion(Input.HiddenPropertiesFolder, Input.YYYYMMDDHHMISSLst);
		baseClass.stepInfo("Saved ingestion as draft without Dat selection and mapping field in disabled state");
		ingestionPage.verifyIngestionStatusAfterSaveAsDraft();
		// click on copy option from draft mode
		ingestionPage.IngestionOverlayUsingCopyFromDraftMode("Native", Input.HiddenPropertiesFolder,
				Input.YYYYMMDDHHMISSLst, Input.YYYYMMDDHHMISSDat, Input.sourceDocIdSearch);
		ingestionPage.ingestionCatalogging();
		loginPage.logout();

	}

	
	/**
	 * Author :Arunkumar date: 04/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-47369 Description :To verify 'Ingestion Details' pop up
	 * display
	 */
	@Test(description ="RPMXCON-47369",enabled = true, groups = { "regression" } )
	public void verifyIngestionDetailsPopupDisplay() {

		baseClass.stepInfo("Test case Id: RPMXCON-47369");
		baseClass.stepInfo("To verify 'Ingestion Details' pop up display");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.verifyIngestionDetails();
		loginPage.logout();
	}

	/**
	 * Author :Arunkumar date: 05/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-47362 Description :To verify that on Ingestion Home page,
	 * user is able to access all page by navigation controls.
	 */
	@Test(description ="RPMXCON-47362",enabled = true, groups = { "regression" } )
	public void verifyIngestionHomePageNavigation() {

		baseClass.stepInfo("Test case Id: RPMXCON-47362");
		baseClass.stepInfo(
				"To verify that on Ingestion Home page, user is able to access all page by navigation controls.");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.verifyHomePageNavigationControl();
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 08/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-49022 Description :Verify two ingestions with step (Indexing
	 * , Approval ) having ingestion type add only must run simultaneously
	 * 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49022",enabled = true, groups = { "regression" } )
	public void verifyTwoIngestionRunTillApprovingSimultaneously() throws InterruptedException {

		String[] dataset = { Input.AllSourcesFolder, Input.TiffImagesFolder };

		baseClass.stepInfo("Test case Id: RPMXCON-49022");
		baseClass.stepInfo(
				"Verify two ingestions with step (Indexing  , Approval ) having ingestion type add only  must run simultaneously");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		// Verify two add only type ingestion run simultaneously
		boolean status1 = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		ingestionPage.navigateToIngestionPage();
		boolean status2 = ingestionPage.verifyIngestionpublish(Input.TiffImagesFolder);
		if (status1 == true || status2 == true) {
			baseClass.passedStep("As Ingestion already present in the published stage, unable to run add only ingestion till approve" );
		}
		else if (status1 == false && status2 == false) {
		ingestionPage.IngestionOnlyForDatFile(Input.AllSourcesFolder, Input.DATFile1);
		ingestionPage.IngestionOnlyForDatFile(Input.TiffImagesFolder, Input.DATFile3);
		ingestionPage.multipleIngestionCopying(2);
		ingestionPage.multipleIngestionIndexing(dataset, 2);
		ingestionPage.approveIngestion(2);
		
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 10/05/2022 Modified date: NA Modified by: NA TestCase Id:RPMXCON-47588 
	 * Description :Ingestion with Overlay mode with only load files for PDF or TIFF or Native file type
	 * 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47588",enabled = true, groups = { "regression" } )
	public void verifyOverlayIngestionWithOnlyLoadFiles() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47588");
		baseClass.stepInfo("Ingestion with Overlay mode with only load files for PDF or TIFF or Native file type");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
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
		loginPage.logout();
		
	}

	/**
	 * Author :Arunkumar date: 08/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-47594 Description :To Verify for Approved ingestions, there
	 * should not have any option for Rollback.
	 * 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47594",enabled = true, groups = { "regression" } )
	public void verifyRollbackStatusForApprovedIngestion() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47594");
		baseClass.stepInfo("To Verify for Approved ingestions, there should not have any option for Rollback.");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
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
			ingestionPage.OverlayIngestionWithoutDat(Input.AK_NativeFolder, "Pdf", Input.PDFFile);
			ingestionPage.verifyApprovedStatusForOverlayIngestion();
			ingestionPage.verifyRollbackOptionForApprovedIngestion();
			loginPage.logout();

		}
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