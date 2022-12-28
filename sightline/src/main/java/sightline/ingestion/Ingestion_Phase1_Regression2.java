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

import automationLibrary.Driver;
import pageFactory.BaseClass;
import pageFactory.DataSets;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.IngestionPage_Indium;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TallyPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Ingestion_Phase1_Regression2 {

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
	SavedSearch savedSearch;
	DocViewPage docView;

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
	
	/*This class contains 54 testCases.*/
	/**
	 * Author :Arunkumar date: 05/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-49282 Description :To Verify Ingestion Add Only end to End
	 * Flow with Source System as Mapped Data
	 * 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49282",enabled = true, groups = { "regression" } )
	public void verifyAddonlyIngestionWithMappedData() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
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
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
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

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
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

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
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

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
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

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
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

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
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

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
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

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
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

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
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

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
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

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
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

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
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

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
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

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
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

		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
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

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
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

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
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

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
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

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
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

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
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

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
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

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
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

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
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

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
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

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
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

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
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

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
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

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
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

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
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

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-47369");
		baseClass.stepInfo("To verify 'Ingestion Details' pop up display");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.verifyIngestionDetails();
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

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
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

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
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

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
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
	
	/**
	 * Author: Vijaya.Rani date: 02/05/2022 Modified date: NA Modified by: NA
	 * Description :Verify when user ingest only metadata, message like 'No files
	 * associated with this document' should be displayed on
	 * text/Images/Translations view.'RPMXCON-51236'
	 * 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-51236",enabled = true, groups = { "regression" })
	public void verifyIngestMetaDataMessageDisplayTEXTFile() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		String ingestionType="Add Only";
		baseClass.stepInfo("Test case Id: RPMXCON-51236");
		baseClass.stepInfo(
				"Verify when user ingest only metadata, message like 'No files associated with this document' should be displayed on text/Images/Translations view");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
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
	@Test(description ="RPMXCON-51235",enabled = true, groups = { "regression" })
	public void verifyIngestMetaDataMessageDisplayDefaultAndTEXTFile() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		DocViewPage docViewPage = new DocViewPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51235");
		baseClass.stepInfo(
				"Verify when user ingest only metadata, 'No files associated with this document' message should be displayed on default view");

		String ingestionType="Add Only";
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
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
	@Test(description ="RPMXCON-51237",enabled = true, groups = { "regression" })
	public void verifyIngestMetaDataMessageDisplayIMAGEAndTEXTFile() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-51237");
		baseClass.stepInfo("Verify when user ingest only metadata, error PDF should be displayed on Images view");

		String ingestionType="Add Only";
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
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
	@Test(description ="RPMXCON-51238",enabled = true, groups = { "regression" })
	public void verifyIngestMetaDataDATFileIsIngested() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-51238");
		baseClass.stepInfo("Verify when user ingest only dat file, only metadata should get ingested.");

		String ingestionType="Add Only";
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
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
	 * Author :Aathith date: 10/5/2022 Modified date: Modified by: Description
	 * :To verify that after Text overlay, if there are other file variants , then DocView follow the set precedence and 
	 * Text will reflect the overlaid text. 'RPMXCON-48607'
	 * 
	 */
	@Test(description ="RPMXCON-48607",enabled = true, groups = { "regression" })
	public void verifyOverlayTheDocViewTextWillReflectOverlaidText() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		dataSets = new DataSets(driver);
		DocListPage docList= new DocListPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView=new DocViewPage(driver);
		String BasicSearchName = "Search" + Utility.dynamicNameAppender();
		
		
		baseClass.stepInfo("Test case Id: RPMXCON-48607");
		baseClass.stepInfo(
				"To verify that after Text overlay, if there are other file variants , then DocView follow the set precedence and Text will reflect the overlaid text.");

		String ingestionType=Input.ingestionType;
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.PP_PDFGen_10Docs);
		System.out.println(status);

		if (status == false) {

			ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,ingestionType, "TRUE", Input.DATPPPDF10Docs, null,
					Input.TextPPPDF10Docs, null, Input.ImagePPPDF10docs,"select", null, null, null);
			
		}
		dataSets.navigateToDataSetsPage();
		dataSets.selectDataSetWithName(Input.PP_PDFGen_10Docs);
		String docId=docList.getDocumetId();
		sessionSearch.basicSearchWithMetaDataQuery(docId, "DocID");
		sessionSearch.saveSearch(BasicSearchName);

		// Go to UnpublishPage
		ingestionPage.navigateToUnPublishPage();
		ingestionPage.unpublish(BasicSearchName);
		
		baseClass.stepInfo("Navigate to ingestion page.");
		ingestionPage.navigateToIngestionPage();
		
		baseClass.stepInfo("Select ingestion type and specify source loaction.");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.overlayOnly, null, Input.sourceLocation,
				Input.PDFGen_10Docs);

		baseClass.stepInfo("Select DAT delimiters.");
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);

		baseClass.stepInfo("Select DAT source.");
		ingestionPage.selectDATSource(Input.newdateformat_5Docs, Input.prodBeg);

		ingestionPage.selectTextSource(Input.TextPPPDF10Docs, false);

		baseClass.stepInfo("Select Date and Time format.");
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);

		baseClass.stepInfo("Click on next button.");
		ingestionPage.clickOnNextButton();

		baseClass.stepInfo("Click on preview and run button.");
		ingestionPage.clickOnPreviewAndRunButton();
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();

		dataSets.navigateToDataSetsPage();
		dataSets.selectDataSetWithName(Input.PP_PDFGen_10Docs);
		String docId1=docList.getDocumetId();
		sessionSearch.basicSearchWithMetaDataQuery(docId1, "DocID");
		sessionSearch.viewInDocView();
		driver.waitForPageToBeReady();
		docView.waitforFileType();
		String filetype=docView.getFileType().getText().trim();
		System.out.println(filetype);
		if(filetype.contains("PDF")) {
			baseClass.passedStep("PDF file only displayed in default viewer");
		}else {
			baseClass.failedStep("verification failed");
		}
		docView.getDocView_TextTab().waitAndClick(10);
		driver.waitForPageToBeReady();
		if(docView.getDocViewDefaultViewText().isElementAvailable(10)) {
			baseClass.passedStep("Text file displayed in Text Tab");
		}else {
			baseClass.failedStep("verification failed");
		}
		baseClass.passedStep("verified that after Text overlay, if there are no other file variants , then DocView uses that text as the default viewer file for that document");
		loginPage.logout();
	}
	

	/**
	 * Author :Vijaya.Rani date: 9/5/2022 Modified date: Modified by: Description
	 * :To verify that total unique ingested document count displays unique count if
	 * user perform only Text overlay.'RPMXCON-49262'
	 * 
	 */
	@Test(description ="RPMXCON-49262",enabled = true, groups = { "regression" })
	public void verifyUniqueCountNotIncludeUnpublished() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		dataSets = new DataSets(driver);
		savedSearch = new SavedSearch(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocListPage docList= new DocListPage(driver);
		String BasicSearchName = "Newone" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id: RPMXCON-49262");
		baseClass.stepInfo(
				"To verify that total unique ingested document count displays unique count if user perform only Text overlay.");

		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		// perform add only ingestion with source system as Mapped data
		boolean status = ingestionPage.verifyIngestionpublish(Input.PP_PDFGen_10Docs);
		System.out.println(status);

		if (status == false) {
			String ingestionType="Add Only";
			ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,ingestionType, "TRUE", Input.DATPPPDF10Docs, null,
					Input.TextPPPDF10Docs, null, Input.ImagePPPDF10docs,"select", null, null, null);
		}
		// getting unique ingested count before overlay
		int uniqueCountBefore = ingestionPage.getIngestedUniqueCount();
		System.out.println(uniqueCountBefore);
		baseClass.stepInfo("Total unique count before performing overlay : '" + uniqueCountBefore + "'");
				
		dataSets.selectDataSetWithName(Input.PP_PDFGen_10Docs);
		String docId = docList.getDocumetId();
		sessionSearch.basicSearchWithMetaDataQuery(docId, "DocID");
		sessionSearch.saveSearch(BasicSearchName);
		// Go to UnpublishPage
		ingestionPage.navigateToUnPublishPage();
		ingestionPage.unpublish(BasicSearchName);
		// perform overlay only ingestion with source system as Mapped data
		ingestionPage.navigateToIngestionPage();
		String ingestionType="Overlay Only";
		ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,ingestionType, "TRUE",  Input.DATPPPDF10Docs, null,
				Input.TextPPPDF10Docs, null, Input.ImagePPPDF10docs,"select", null, null, null);
		
		// getting unique ingested count after overlay
		ingestionPage.navigateToIngestionPage();
		int uniqueCountAfter = ingestionPage.getIngestedUniqueCount();
		baseClass.stepInfo("Total unique count After performing overlay : '" + uniqueCountAfter + "'");
		System.out.println(uniqueCountAfter);
		if (uniqueCountBefore == uniqueCountAfter) {
			baseClass.passedStep("Only unique ingested count displayed after performing  overlay");
		} else {
			baseClass.failedStep("Unique ingested count not displayed after performing  overlay");
		}
		loginPage.logout();
		
	}
	
	
	/**
	 * Author :Vijaya.Rani date: 11/5/2022 Modified date: Modified by: Description
	 * :To verify that after Text overlay, if there are no other file variants ,
	 * then DocView uses that text as the default viewer file for that document.
	 * 'RPMXCON-48606'
	 * 
	 */
	@Test(description ="RPMXCON-48606",enabled = true, groups = { "regression" })
	public void verifyOverlayDocViewTextWillReflectOverlaidText() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		dataSets = new DataSets(driver);
		savedSearch = new SavedSearch(driver);
		DocListPage docList = new DocListPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		String BasicSearchName = "Search" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id: RPMXCON-48606");
		baseClass.stepInfo(
				"To verify that after Text overlay, if there are no other file variants , then DocView uses that text as the default viewer file for that document.");

		String ingestionType = "Add Only";
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.PP_PDFGen_10Docs);
		System.out.println(status);

		if (status == false) {

			ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs, ingestionType, "TRUE",
					Input.DATPPPDF10Docs, null, Input.TextPPPDF10Docs, null, Input.ImagePPPDF10docs, "select", null,
					null, null);

		}
		dataSets.selectDataSetWithName(Input.PP_PDFGen_10Docs);
		String docId = docList.getDocumetId();
		sessionSearch.basicSearchWithMetaDataQuery(docId, "DocID");
		sessionSearch.saveSearch(BasicSearchName);

		// Go to UnpublishPage
		ingestionPage.navigateToUnPublishPage();
		ingestionPage.unpublish(BasicSearchName);

		baseClass = new BaseClass(driver);

		baseClass.stepInfo("Navigate to ingestion page.");
		ingestionPage.navigateToIngestionPage();
		baseClass.stepInfo("Select ingestion type and specify source loaction.");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.overlayOnly, Input.sourceSystem, Input.sourceLocation,
				Input.PDFGen_10Docs);

		baseClass.stepInfo("Select DAT delimiters.");
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);

		baseClass.stepInfo("Select DAT source.");
		ingestionPage.selectDATSource(Input.newdateformat_5Docs, Input.prodBeg);

		ingestionPage.selectTextSource(Input.TextPPPDF10Docs, false);

		baseClass.stepInfo("Select Date and Time format.");
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);

		baseClass.stepInfo("Click on next button.");
		ingestionPage.clickOnNextButton();

		baseClass.stepInfo("Click on preview and run button.");
		ingestionPage.clickOnPreviewAndRunButton();
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();

		dataSets.selectDataSetWithName(Input.PP_PDFGen_10Docs);
		String docId1 = docList.getDocumetId();
		sessionSearch.basicSearchWithMetaDataQuery(docId1, "DocID");
		sessionSearch.viewInDocView();
		driver.waitForPageToBeReady();
		docView.waitforFileType();
		docView.getDocView_TextTab().waitAndClick(10);
		driver.waitForPageToBeReady();
		if (docView.getDocViewDefaultViewText().isElementAvailable(10)) {
			baseClass.passedStep(
					"There are no other file variants ,then Doc View is  displays the text as the default viewer");
		} else {
			baseClass.failedStep("verification failed");
		}
		loginPage.logout();

	}
	
	
	/**
	 * Author :Aathith date: NA Modified date: Modified by: Description : Verify
	 * that if PA ingested both Text's and TIFF's file,and the "Generate Searchable
	 * PDFs" option is set to False, then it should display TIFF in default viewer
	 */
	@Test(description ="RPMXCON-49511",enabled = true, groups = { "regression" })
	public void VerifyTiffImageInDefautViewer() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-49511");
		baseClass.stepInfo(
				"Verify that if PA ingested both Text's and TIFF's file,and the \"Generate Searchable PDFs\" option is set to False, then it should display TIFF in default viewer");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.PDFGen_10Docs);
		if(!status) {
			ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,Input.ingestionType, "TRUE", Input.DATPPPDF10Docs, null,
					Input.TextPPPDF10Docs, null, Input.ImagePPPDF10docs,"select", null, null, null);
		}
		ingestionPage.navigateToDataSetsPage();
		dataSets.selectDataSetWithNameInDocView(Input.PDFGen_10Docs);
		String name = docView.getDefaultViewerFileType().GetAttribute("xlink:href");
		System.out.println(name);
		if (name.contains("image")) {
			baseClass.passedStep("Tiff file only displayed in default viewer");
		} else {
			baseClass.failedStep("verification failed");
		}
		
		baseClass.passedStep(
				"Verified that if PA ingested both Text's and TIFF's file,and the \"Generate Searchable PDFs\" option is set to False, then it should display TIFF in default viewer");
		loginPage.logout();

	}

	/**
	 * Author :Aathith date: NA Modified date: Modified by: Description : Verify
	 * that if PA ingested both native's and TIFF's file,and the "Generate
	 * Searchable PDFs" option is set to true, then PDF should be generated from the
	 * TIFF's only
	 */
	@Test(description ="RPMXCON-49498",enabled = true, groups = { "regression" })
	public void verifyPdfandTiffInDocView() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-49498");
		baseClass.stepInfo(
				"Verify that if PA ingested both native's and TIFF's file,and the \"Generate Searchable PDFs\" option is set to true, then PDF should be generated from the TIFF's only");
		
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.PDFGen_10Docs);
		if(!status) {
			ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,Input.ingestionType, "TRUE", Input.DATPPPDF10Docs, null,
					Input.TextPPPDF10Docs, null, Input.ImagePPPDF10docs,"select", null, null, null);
		}
		ingestionPage.navigateToDataSetsPage();
			dataSets.selectDataSetWithNameInDocView(Input.PDFGen_10Docs);
			driver.waitForPageToBeReady();
			if (docView.getFileType().isElementAvailable(10)) {
				driver.waitForPageToBeReady();
				docView.waitforFileType();
				String filetype = docView.getFileType().getText();
				System.out.println(filetype);
				if (filetype.contains("PDF")) {
					baseClass.passedStep("PDF file only displayed in default viewer");
				} else {
					baseClass.failedStep("verification failed");
				}
			} else {
				baseClass.failedStep("file type is  not displayed");
			}
			docView.getImageTab().waitAndClick(10);
			driver.waitForPageToBeReady();
			if (docView.getDocViewImage().isElementAvailable(10)) {
				baseClass.passedStep("Tiff file displayed in Tiff Tab");
			} else {
				baseClass.failedStep("verification failed");
			}
		
		baseClass.passedStep("Verified that if PA ingested both native's and TIFF's file,"
				+ "and the \"Generate Searchable PDFs\" option is set to true, then PDF should be generated from the TIFF's only");
		loginPage.logout();

	}
	
	/**
	 * Author :Vijaya.Rani date:8/5/2022 Modified date: Modified by: Description :
	 * Verify that if PA ingested both TIFF's and Text's file,and the "Generate
	 * Searchable PDFs" option is set to true, then PDF should be generated from the
	 * TIFF's only. RPMXCON-49510
	 */
	@Test(description ="RPMXCON-49510",enabled = true, groups = { "regression" })
	public void VerifySelectSearchablePDFTiffImageInDefautViewer() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		dataSets = new DataSets(driver);
		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-49510");
		baseClass.stepInfo(
				"Verify that if PA ingested both TIFF's and Text's file,and the 'Generate Searchable PDFs' option is set to true, then PDF should be generated from the TIFF's only.");

		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.PDFGen_10Docs);
		if(!status) {
			ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,Input.ingestionType, "TRUE", Input.DATPPPDF10Docs, null,
					Input.TextPPPDF10Docs, null, Input.ImagePPPDF10docs,"select", null, null, null);
		}
		ingestionPage.navigateToDataSetsPage();
			dataSets.selectDataSetWithNameInDocView(Input.PDFGen_10Docs);
			String name = docView.getDefaultViewerFileType().GetAttribute("xlink:href");
			System.out.println(name);
			if (name.contains("image")) {
				baseClass.passedStep("Tiff file only displayed in default viewer");
			} else {
				baseClass.failedStep("verification failed");
			}
		
		baseClass.passedStep(
				"Verify that if PA ingested both TIFF's and Text's file,and the 'Generate Searchable PDFs' option is set to true, then PDF is generated from the TIFF's only");
		loginPage.logout();

	}
	
	/**
	 * @author Aathith
	 * @throws InterruptedException 
	 * //@TestCase id: 48277 : To Verify unpublish for Overlay ingestionPage.
	 */
	@Test(description ="RPMXCON-48277",enabled = true, groups = { "regression" })
	public void verifyUnpublishOverLayIngestion() throws InterruptedException {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-48277 ");
		baseClass.stepInfo(
				"###  To Verify unpublish for Overlay ingestionPage. ###");
		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		String BasicSearchName = "search"+Utility.dynamicNameAppender();

		baseClass.stepInfo("Navigate to ingestion page.");
		ingestionPage.nativigateToIngestionViaButton();
		boolean status = ingestionPage.verifyIngestionpublish(Input.PDFGen_10Docs);
		if(!status) {
			ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,Input.ingestionType, "TRUE", Input.DATPPPDF10Docs, null,
					Input.TextPPPDF10Docs, null, Input.ImagePPPDF10docs,"select", null, null, null);
		}

			baseClass.stepInfo("Select ingestion type and specify source loaction.");
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.overlayOnly, null, Input.sourceLocation,
					Input.PDFGen_10Docs);

			baseClass.stepInfo("Select DAT delimiters.");
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);

			baseClass.stepInfo("Select DAT source.");
			//Input.newdateformat_5Docs, Input.prodBeg used for dat file name and Document Key
			ingestionPage.selectDATSource(Input.newdateformat_5Docs, Input.prodBeg);

			baseClass.stepInfo("Select Pdf varient source.");
			//Input.PDF5DocsLst is used for pdf file Name
			ingestionPage.selectPDFSource(Input.PDF5DocsLst, false);
			
			baseClass.stepInfo("Select Tiff varient source.");
			//Input.Images5DocsLst used for Image loadfile name 
			ingestionPage.selectTIFFSource(Input.Images5DocsLst, false, true);

			baseClass.stepInfo("Select Date and Time format.");
			//Input.dateFormat used for choosing the stranded data format
			ingestionPage.selectDateAndTimeFormat(Input.dateFormat);

			baseClass.stepInfo("Click on next button.");
			ingestionPage.clickOnNextButton();

			baseClass.stepInfo("Click on preview and run button.");
			ingestionPage.clickOnPreviewAndRunButton();
			ingestionPage.verifyApprovedStatusForOverlayIngestion();
			ingestionPage.runFullAnalysisAndPublish();
			
			ingestionPage.navigateToDataSetsPage();

		
		String ingestionFullName = dataSets.isDataSetisAvailable(Input.PDFGen_10Docs);
		if(ingestionFullName!=null) {
			// Search ingestionName
			sessionSearch.basicSearchWithMetaDataQuery(ingestionFullName, "IngestionName");
			sessionSearch.saveSearch(BasicSearchName);
			sessionSearch.unReleaseDocsFromSecuritygroup(Input.securityGroup);
			// Go to UnpublishPage
			ingestionPage.navigateToUnPublishPage();
			ingestionPage.unpublish(BasicSearchName);
			
		}

		baseClass.passedStep(
				"Verified unpublish for Overlay ingestionPage.");
		loginPage.logout();

	}
	
	/**
	 * Author :Aathith date: NA Modified date: Modified by: Description : Verify
	 * that if PA ingested both PDF and TIFF's file and the "Generate Searchable
	 * PDFs" option is set to true, then PDF should be generated from the TIFF's
	 */
	@Test(description ="RPMXCON-49499",enabled = true, groups = { "regression" })
	public void verifyJanTiffPdfandTiffInDocView() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		dataSets = new DataSets(driver);
		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-49499");
		baseClass.stepInfo("Verify that if PA ingested both PDF and TIFF's file"
				+ " and the \"Generate Searchable PDFs\" option is set to true, then PDF should be generated from the TIFF's");

		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.JanMultiPTIFF);
		if(!status) {
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem, Input.sourceLocation, Input.JanMultiPTIFF);
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);
			ingestionPage.selectDATSource("SearchablePDFGeneration.DAT", Input.documentKeyBNum);
			ingestionPage.selectPDFSource("PDF.lst",false);
			ingestionPage.selectTIFFSource("Image.lst",false,true);
			ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
			ingestionPage.clickOnNextButton();
			ingestionPage.ingestionMapping(Input.documentKeyBNum, Input.documentKeyBNum, Input.documentKeyCName);
			ingestionPage.clickOnPreviewAndRunButton();
			ingestionPage.IngestionCatlogtoCopying(Input.JanMultiPTIFF);
			ingestionPage.ingestionIndexing(Input.JanMultiPTIFF);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
			
		}
		String ingestionFullName = dataSets.isDataSetisAvailable(Input.JanMultiPTIFF);
		if (ingestionFullName != null) {
			dataSets.selectDataSetWithNameInDocView(Input.JanMultiPTIFF);
			String filetype = docView.getDocView_TextFileType().getText().trim();
			if (filetype.isEmpty()) {
				baseClass.passedStep("PDF file in native formate displayed in default viewer");
			} else {
				baseClass.failedStep("verification failed");
			}
			docView.getImageTab().waitAndClick(10);
			driver.waitForPageToBeReady();
			if (docView.ProductionNameInImageTab(Input.JanMultiPTIFF).isElementAvailable(10)) {
				baseClass.passedStep("Tiff file displayed in Tiff Tab");
			} else {
				baseClass.failedStep("verification failed");
			}
		}
		baseClass.passedStep("Verified that if PA ingested both PDF and TIFF's file"
				+ " and the \"Generate Searchable PDFs\" option is set to true, then PDF should be generated from the TIFF's");
		loginPage.logout();

	}

	
	/**
	 * Author :Vijaya.Rani date: 9/5/2022 Modified date:NA Modified by:NA 
	 * Description :Verify that if PA ingested both PDF and TIFF's file, the "Generate Searchable
	 * PDFs" option is set to true, and if the Generation of the PDF from the TIFF's
	 * fails, then pre-existing PDF should be retained as the PDF file variant
	 */
	@Test(description ="RPMXCON-49500",enabled = true, groups = { "regression" })
	public void verifyJanTiffPdfandnotTiffInDocView() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-49500");
		baseClass.stepInfo(
				"Verify that if PA ingested both PDF and TIFF's file,the 'Generate Searchable PDFs' option is set to true, and if the Generation of the PDF from the TIFF's fails,then pre-existing PDF should be retained as the PDF file variant");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.JanMultiPTIFF);
		if(status == false) {
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem, Input.sourceLocation, Input.JanMultiPTIFF);
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);
			ingestionPage.selectDATSource("SearchablePDFGeneration.DAT", Input.documentKeyBNum);
			ingestionPage.selectPDFSource("PDF.lst",false);
			ingestionPage.selectTIFFSource("Image.lst",false,true);
			ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
			ingestionPage.clickOnNextButton();
			ingestionPage.ingestionMapping(Input.documentKeyBNum, Input.documentKeyBNum, Input.documentKeyCName);
			ingestionPage.clickOnPreviewAndRunButton();
			ingestionPage.IngestionCatlogtoCopying(Input.JanMultiPTIFF);
			ingestionPage.ingestionIndexing(Input.JanMultiPTIFF);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}
		String ingestionFullName = dataSets.isDataSetisAvailable(Input.JanMultiPTIFF);
		if (ingestionFullName != null) {
			dataSets.selectDataSetWithNameInDocView(Input.JanMultiPTIFF);
			String filetype = docView.getDocView_TextFileType().getText().trim();
			if (filetype.isEmpty()) {
				baseClass.passedStep("PDF file in native formate displayed in default viewer");
			} else {
				baseClass.failedStep("verification failed");
			}
			
		}
		baseClass.passedStep(
				"Verify that if PA ingested both PDF and TIFF's file, the 'Generate Searchable PDFs' option is set to true, and if the Generation of the PDF from the TIFF's fails,then pre-existing PDF should be retained as the PDF file variant");
		loginPage.logout();

	}
	
	/**
	 * Author :Vijaya.Rani date: 9/5/2022 Modified date: Modified by: Description :
	 * Verify that if "Generate Searchable PDFs" is TRUE, then Ingestion should
	 * generate successfully for Multi-page TIFF images.'RPMXCON-49502'
	 * 
	 */
	@Test(description ="RPMXCON-49502",enabled = true, groups = { "regression" })
	public void verifyJanMultiTiffPdfandTiffInDocView() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		DocViewPage docview = new DocViewPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-49502");
		baseClass.stepInfo(
				"Verify that if 'Generate Searchable PDFs' is TRUE, then Ingestion should generate successfully for Multi-page TIFF images.");

		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.JanMultiPTIFF);
		if(status == false) {
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem, Input.sourceLocation, Input.JanMultiPTIFF);
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);
			ingestionPage.selectDATSource("SearchablePDFGeneration.DAT", Input.documentKeyBNum);
			ingestionPage.selectTIFFSource("Image.lst",false,true);
			ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
			ingestionPage.clickOnNextButton();
			ingestionPage.ingestionMapping(Input.documentKeyBNum, Input.documentKeyBNum, Input.documentKeyCName);
			ingestionPage.clickOnPreviewAndRunButton();
			ingestionPage.IngestionCatlogtoCopying(Input.JanMultiPTIFF);
			ingestionPage.ingestionIndexing(Input.JanMultiPTIFF);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}
		String ingestionFullName = dataSets.isDataSetisAvailable(Input.JanMultiPTIFF);
		if (ingestionFullName != null) {
			dataSets.selectDataSetWithNameInDocView(Input.JanMultiPTIFF);
			driver.waitForPageToBeReady();
			docview.getFileType().isElementAvailable(3);
			driver.waitForPageToBeReady();
			String filetype = docview.getDocView_TextFileType().getText().trim();
			if (filetype.isEmpty()) {
				baseClass.passedStep("PDF file only displayed in default viewer");
			} else {
				baseClass.failedStep("verification failed");
			}
			docview.getImageTab().waitAndClick(10);
			driver.waitForPageToBeReady();
			if (docview.getDocViewImage().isElementAvailable(10)) {
				baseClass.passedStep("Tiff file displayed in Tiff Tab");
			} else {
				baseClass.failedStep("verification failed");
			}
		}
		baseClass.passedStep(
				"Verify that if 'Generate Searchable PDFs' is TRUE, then Ingestion should generate successfully for Multi-page TIFF images.");
		loginPage.logout();

	}
	/**
	 * Author :Aathith date: NA Modified date: Modified by: 
	 * Description : Verify that if "Generate Searchable PDFs" is TRUE, then Ingestion should generate successfully for Single page TIFF images.
	 */
	@Test(description ="RPMXCON-49501",enabled = true, groups = { "regression" })
	public void verifyMarTiffPdfandTiffInDocView() throws InterruptedException {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-49501");
		baseClass.stepInfo("Verify that if \"Generate Searchable PDFs\" is TRUE, then Ingestion should generate successfully for Single page TIFF images.");
		
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.SinglePageTIFFFolder);
		if(!status) {
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem, Input.sourceLocation, Input.SinglePageTIFFFolder);
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);
			ingestionPage.selectDATSource("C2322695-9767-44C3-A290-B21B12B82A32_DAT.dat", Input.documentKeyBNum);
			ingestionPage.getTIFFSearchablePDFCheckBox().waitAndClick(10);
			ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
			ingestionPage.clickOnNextButton();
			ingestionPage.ingestionMapping(Input.documentKeyBNum, Input.documentKeyBNum, Input.documentKeyCName);
			ingestionPage.clickOnPreviewAndRunButton();
			ingestionPage.IngestionCatlogtoCopying(Input.JanMultiPTIFF);
			ingestionPage.ingestionIndexing(Input.JanMultiPTIFF);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
			
		}
		
		String ingestionFullName = dataSets.isDataSetisAvailable(Input.SinglePageTIFFFolder);
		if(ingestionFullName!=null) {
			dataSets.selectDataSetWithNameInDocView(Input.SinglePageTIFFFolder);
			driver.waitForPageToBeReady();
			docView.waitforFileType();
			String filetype=docView.getFileType().getText().trim();
			System.out.println(filetype);
			if(filetype.contains("PDF")) {
				baseClass.passedStep("PDF file only displayed in default viewer");
			}else {
				baseClass.failedStep("verification failed");
			}
			docView.getImageTab().waitAndClick(10);
			driver.waitForPageToBeReady();
			if(docView.getDocViewImage().isElementAvailable(10)) {
				baseClass.passedStep("Tiff file displayed in Tiff Tab");
			}else {
				baseClass.failedStep("verification failed");
			}
		}
		baseClass.passedStep("Verified that if \"Generate Searchable PDFs\" is TRUE, then Ingestion should generate successfully for Single page TIFF images.");
		loginPage.logout();
		
	}
	
	/**
	 * Author :Vijaya.Rani date: 29/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-49545 Description :Verify that value for all the metadata
	 * fields having DATETIME/DATE data type.
	 */
	@Test(description ="RPMXCON-49545",enabled = true, groups = { "regression" })
	public void verifyMetaDataFieldsDateTimeInDataType() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-49545");
		baseClass.stepInfo("Verify that value for all the metadata fields having DATETIME/DATE data type.");
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		dataSets = new DataSets(driver);
		sessionSearch = new SessionSearch(driver);

		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.HiddenPropertiesFolder);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Addonly saved ingestion with mapping field selection And Publish");
			ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "MM/DD/YYYY",Input.DAT_MMDDYYYY_Slash,Input.Natives_MMDDYYYY_Slash);
			ingestionPage.ignoreErrorsAndCatlogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.HiddenPropertiesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}
		baseClass.stepInfo("Search the documents and Save");
		String ingestionFullName = dataSets.isDataSetisAvailable(Input.HiddenPropertiesFolder);
		sessionSearch.basicSearchWithMetaDataQuery(ingestionFullName, "IngestionName");
		sessionSearch.ViewInDocList();
		// Remove selected Colunm Add new Column
		docList.SelectColumnDisplayByRemovingExistingOnesAddMultiipleColumns();
		loginPage.logout();
	}
	
	/**
	 * Author :Vijaya.Rani date: 27/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-49902 Description :Unpublish documents - Verify Search as
	 * source.
	 */
	@Test(description ="RPMXCON-49902",enabled = true, groups = { "regression" })
	public void verifyUnpublishDocumentsSource() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		SessionSearch sessionSearch = new SessionSearch(driver);
		savedSearch = new SavedSearch(driver);
		dataSets = new DataSets(driver);
		String BasicSearchName = "Newone" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id: RPMXCON-49902");
		baseClass.stepInfo("Unpublish documents - Verify Search as source.");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.HiddenPropertiesFolder);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Addonly saved ingestion with mapping field selection And Publish");
			ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "MM/DD/YYYY",Input.DAT_MMDDYYYY_Slash,Input.Natives_MMDDYYYY_Slash);
			ingestionPage.ignoreErrorsAndCatlogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.HiddenPropertiesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}
		baseClass.stepInfo("Search the documents and Save");
		String ingestionFullName = dataSets.isDataSetisAvailable(Input.HiddenPropertiesFolder);
		sessionSearch.basicSearchWithMetaDataQuery(ingestionFullName, "IngestionName");
		sessionSearch.saveSearchSharedWithPA(BasicSearchName);

		// Go to UnpublishPage
		ingestionPage.navigateToUnPublishPage();
		ingestionPage.unpublish(BasicSearchName);
		loginPage.logout();
	}
	
	/**
	 * Author :Aathith date: NA Modified date: Modified by: 
	 * Description : To Verify In Ingestions, if the user tries to unpublish non-Nexidia indexed audio files, the unpublish should be successful.
	 */
	@Test(description ="RPMXCON-48260",enabled = true, groups = { "regression" })
	public void verifyUnpublishIndexedAudioFile() throws InterruptedException {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		dataSets = new DataSets(driver);
		sessionSearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		String BasicSearchName = "search"+Utility.dynamicNameAppender();
		
		baseClass.stepInfo("Test case Id: RPMXCON-48260");
		baseClass.stepInfo("To Verify In Ingestions, if the user tries to unpublish non-Nexidia indexed audio files, the unpublish should be successful.");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.AK_NativeFolder);
		System.out.println(status);
		if (status == false) {
			ingestionPage.performAKNativeFolderIngestion(Input.DATFile1);
			ingestionPage.ignoreErrorsAndCatlogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.AK_NativeFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
			
		}
		
		String ingestionFullName = dataSets.isDataSetisAvailable(Input.AK_NativeFolder);
		if(ingestionFullName!=null) {
			// Search ingestionName
			sessionSearch.basicSearchWithMetaDataQuery(ingestionFullName, "IngestionName");
			sessionSearch.getPureHitAddButton().isElementAvailable(2);
			sessionSearch.getPureHitAddButton().waitAndClick(5);
			// Saved the My SavedSearch
			sessionSearch.saveSearch(BasicSearchName);
			// Go to UnpublishPage
			ingestionPage.navigateToUnPublishPage();
			ingestionPage.unpublish(BasicSearchName);
			
		}
		baseClass.passedStep("Verified In Ingestions, if the user tries to unpublish non-Nexidia indexed audio files, the unpublish should be successful.");
		loginPage.logout();
		
	}
	
	
	/**
	 * Author :Vijaya.Rani date: 10/5/2022 Modified date: Modified by: Description
	 * :To verify that the total unique count should not include the docs that have
	 * been unpublished. 'RPMXCON-49263'
	 * 
	 */
	@Test(description ="RPMXCON-49263",enabled = true, groups = { "regression" })
	public void verifyTotalUniqueCountAfterUnpublished() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		dataSets = new DataSets(driver);
		savedSearch = new SavedSearch(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocListPage doclist = new DocListPage(driver);
		String BasicSearchName = "Search" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id: RPMXCON-49263");
		baseClass.stepInfo(
				"To verify that the total unique count should not include the docs that have been unpublished.");

		
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		
		boolean status = ingestionPage.verifyIngestionpublish(Input.SinglePageTIFFFolder);
		if(!status) {
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem, Input.sourceLocation, Input.SinglePageTIFFFolder);
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);
			ingestionPage.selectDATSource("C2322695-9767-44C3-A290-B21B12B82A32_DAT.dat", Input.documentKeyBNum);
			ingestionPage.getTIFFSearchablePDFCheckBox().waitAndClick(10);
			ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
			ingestionPage.clickOnNextButton();
			ingestionPage.ingestionMapping(Input.documentKeyBNum, Input.documentKeyBNum, Input.documentKeyCName);
			ingestionPage.clickOnPreviewAndRunButton();
			ingestionPage.ignoreErrorsAndCatlogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.JanMultiPTIFF);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
			
		}
		
		// getting before unique count
		int uniqueCountBefore = ingestionPage.getIngestedUniqueCount();
		System.out.println(uniqueCountBefore);
		baseClass.stepInfo("Total unique count Before performing overlay : '" + uniqueCountBefore + "'");
		
		dataSets.navigateToDataSetsPage();
		dataSets.selectDataSetWithName(Input.SinglePageTIFFFolder);
		String docId=doclist.getDocumetId();
		sessionSearch.basicSearchWithMetaDataQuery(docId, "DocID");
		sessionSearch.saveSearch(BasicSearchName);

		// Go to UnpublishPage
		ingestionPage.navigateToUnPublishPage();
		ingestionPage.unpublish(BasicSearchName);

		int uniqueCountAfter = ingestionPage.getIngestedUniqueCount();
		System.out.println(uniqueCountAfter);
		baseClass.stepInfo("Total unique count after performing overlay : '" + uniqueCountAfter + "'");

		if (uniqueCountBefore == uniqueCountAfter) {
			baseClass.failedStep("Total Unique Count included the document that have been unpublished ");
		} else {
			baseClass.passedStep("Total Unique Count not included the document that have been unpublished ");
		}
		loginPage.logout();
	}

	/**
	 * Author :Vijaya.Rani date: 10/5/2022 Modified date: Modified by: Description
	 * :To Verify Full Analytics run successfully in Ingestion for Overlays Mode and
	 * all the Metadata Updated in Overlays should get displayed after Overlay's
	 * Successful. 'RPMXCON-48084'
	 * 
	 */
	@Test(description ="RPMXCON-48084",enabled = true, groups = { "regression" })
	public void verifyFullAnalyticsRunIngestionForOverlay() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		dataSets = new DataSets(driver);
		savedSearch = new SavedSearch(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		String BasicSearchName = "Test" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id: RPMXCON-48084");
		baseClass.stepInfo(
				"To Verify Full Analytics run successfully in Ingestion for Overlays Mode and all the Metadata Updated in Overlays should get displayed after Overlay's Successful.");


		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();

		boolean status = ingestionPage.verifyIngestionpublish(Input.UniCodeFilesFolder);
		System.out.println(status);

		if (status == false) {

			ingestionPage.unicodeFilesIngestion(Input.datLoadFile1, Input.textFile1, Input.documentKey);
			ingestionPage.ignoreErrorsAndCatlogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.UniCodeFilesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
			
		}
		int beforeOverlayCount = ingestionPage.getIngestedUniqueCount();
		System.out.println(beforeOverlayCount);
		
		String ingestionFullName = dataSets.isDataSetisAvailable(Input.UniCodeFilesFolder);
		// Search ingestionName
		sessionSearch.basicSearchWithMetaDataQuery(ingestionFullName, "IngestionName");
		// Saved the My SavedSearch
		sessionSearch.saveSearch(BasicSearchName);
		sessionSearch.unReleaseDocsFromSecuritygroup(Input.securityGroup);
		
		// Go to UnpublishPage
		ingestionPage.navigateToUnPublishPage();
		ingestionPage.unpublish(BasicSearchName);
		// perform overlay with same data
		ingestionPage.OverlayIngestionWithoutDat(Input.UniCodeFilesFolder, "text", Input.textFile1);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();
		
		int afterOverlayCount =ingestionPage.getIngestedUniqueCount();
		System.out.println(afterOverlayCount);
		
		if (beforeOverlayCount == afterOverlayCount) {
			baseClass.passedStep("Metadata Updated in Overlays displayed after Overlay's Successfully");
		} else {
			baseClass.failedStep(" Metadata Updated in Overlays not displayed after Overlay's");
		}
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
