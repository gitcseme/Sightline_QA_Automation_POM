package testScriptsRegressionSprint20;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

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
import pageFactory.DocListPage;
import pageFactory.IngestionPage_Indium;
import pageFactory.LoginPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Ingestion_Regression_5 {
	
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	IngestionPage_Indium ingestionPage;
	SessionSearch sessionSearch;
	DocListPage docList;
	DataSets dataSets;
	SecurityGroupsPage securityGroup;
	Input ip;

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
		loginPage = new LoginPage(driver);
		
	}
	
	/**
	 * Author :Arunkumar date: 24/08/2022 TestCase Id:RPMXCON-49773
	 * Description :Verify concatenated email value should be displayed correctly for CCName and CCAddress 
	 * fields in Doc List 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49773",enabled = true, groups = { "regression" })
	public void TCA1verifyConcatenatedValueForCCField() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-49773");
		baseClass.stepInfo("Verify concatenated email value displayed in doclist");
		String ingestionName = null;
		String[] values = {"EmailCCAddresses","EmailCCNames","EmailCCNamesAndAddresses"};
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
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
	public void TCA2verifyConcatenatedValueForBCCField() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-49774");
		baseClass.stepInfo("Verify concatenated email value displayed in doclist");
		String ingestionName = null;
		String[] values = {"EmailBCCAddresses","EmailBCCNames","EmailBCCNamesAndAddresses"};
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
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
	public void TCA3verifyConcatenatedValueForToField() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-49775");
		baseClass.stepInfo("Verify concatenated email value displayed in doclist");
		String ingestionName = null;
		String[] values = {"EmailToAddresses","EmailToNames","EmailToNamesAndAddresses"};
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
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
	public void TCA4verifyConcatenatedValueForAuthorField() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-49776");
		baseClass.stepInfo("Verify concatenated email value displayed in doclist");
		String ingestionName = null;
		String[] values = {"EmailAuthorAddress","EmailAuthorName","EmailAuthorNameAndAddress"};
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
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
	public void TCA6verifyOverlayOfDifferentFiles() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47825");
		baseClass.stepInfo("Verify overlay of different files with same unique id");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
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
		
	}
	
	
	/**
	 * Author :Arunkumar date: 29/08/2022 TestCase Id:RPMXCON-46885
	 * Description :To Verify audio indexing for audio documents when audio indexing option is selected. 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-46885",enabled = true, groups = { "regression" })
	public void TCA5verifyAudioIndexingForAudioDocs() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-46885");
		baseClass.stepInfo("Verify audio indexing for audio documents when audio indexing option is selected");
		String ingestionName = null;
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
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
	}
	
	/**
	 * Author :Arunkumar date: 01/09/2022 TestCase Id:RPMXCON-47295
	 * Description :New Ingestion with Overwrite option as 'Add Only' 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47295",enabled = true, groups = { "regression" })
	public void TCA7verifyPerformingNewAddOnlyIngestion() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47295");
		baseClass.stepInfo("New Ingestion with Overwrite option as 'Add Only'");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
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
	public void TCA8verifyUnpublishForAudioIngestedDocs() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48257");
		baseClass.stepInfo("To Verify Unpublish for Ingested audio documents");
		String BasicSearchName = "search"+Utility.dynamicNameAppender();
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
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
