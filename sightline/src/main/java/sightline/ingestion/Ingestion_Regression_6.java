package sightline.ingestion;

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
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.DataSets;
import pageFactory.DocListPage;
import pageFactory.IngestionPage_Indium;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Ingestion_Regression_6 {
	
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	IngestionPage_Indium ingestionPage;
	SessionSearch sessionSearch;
	DocListPage docList;
	DataSets dataSets;
	SecurityGroupsPage securityGroup;
	Input ip;
	ProjectPage project;

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
	 * Author :Arunkumar date: 09/09/2022 TestCase Id:RPMXCON-63253
	 * Description :Validate whether all the DAT fields from DAT File selected during "Over lay" ingestion is displayed
	 * under Source DAT fields in Configure Field Mapping of Ingestion Wizard Page.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-63253",enabled = true, groups = { "regression" })
	public void TCA1verifyRepeatedDestinationFieldErrorWhenOverlay() throws InterruptedException {
		
		String[] ExpectedDatFields={"DocID","RecBegAttach","RecEndAttach","ProdBeg","ProdEnd","ProdBegAttach","ProdEndAttach","AttachCount","DocPages","Custodian","Date",
				"MasterDate","Author","AuthorAddress","RecipientName","RecipientAddress","CCName","CCAddress","BCCName","BCCAddress","Subject","Container","DataSet","Datasource",
				"FullPath","NativeLink","FileType","FileName","FileExt","FileSize","M_Duration","ReceivedDate","ApptSTDate","ApptEndDate","MessageID","MessageType","CreateDate",
				"LastAccDate","LastEditDate","LastSaveDate","LastPrintedDate","MD5Hash","Title","Comments","Company","Keywords","Manager","Category","RevNumber",
				"EmailCCNameAndAddress","EmailBCCNameAndAddress","EmailAuthorNameandAddress","DocumentSubject","EmailConversationIndex","EmailFamilyConversationIndex",
				"EmailToNameAndAddress","Native Path","Extracted Text","FamilyVirusStatus","ProcessingPlaceholders","ZzrecipientsID","ZzsenderID"};

		baseClass.stepInfo("Test case Id: RPMXCON-63253");
		baseClass.stepInfo("Verify DAT fields in configure mapping page.");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Add new ingestion with type as 'Overlay'");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.overlayOnly, Input.nuix, Input.sourceLocation, 
				Input.folder61759);
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		ingestionPage.selectDATSource(Input.datFile6, Input.prodBeg);
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		ingestionPage.clickOnNextButton();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify dat fields available in source field mapping in configuring page" );
		baseClass.waitForElement(ingestionPage.getMappingSourceField(2));
		for(int i=2;i<ExpectedDatFields.length;i++) {
			ingestionPage.getMappingSourceField(i).selectFromDropdown().selectByVisibleText(ExpectedDatFields[i]);
			String field =ingestionPage.getMappingSourceField(i).selectFromDropdown().getFirstSelectedOption().getText();
			if(field.equalsIgnoreCase(ExpectedDatFields[i])) {
				baseClass.passedStep("Dat field available :"+ExpectedDatFields[i]);
			}
			else {
				baseClass.failedStep("Dat field not available"+ExpectedDatFields[i]);
			}
		}
		baseClass.passedStep("All the DAT fields available in configure mapping page");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 13/09/2022 TestCase Id:RPMXCON-48420
	 * Description :To Verify Media Overlay Ingestion (with Unpublish). 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48420",enabled = true, groups = { "regression" })
	public void TCA2verifyMediaOverlayIngestion() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-48420");
		baseClass.stepInfo("To Verify Media Overlay Ingestion (with Unpublish).");
		String ingestionName =null;
		String BasicSearchName = "search"+Utility.dynamicNameAppender();
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.passedStep("perform add only ingestion without media indexing");
		boolean status = ingestionPage.verifyIngestionpublish(Input.audio96DocsFolder);
		if (status == false) {
			ingestionPage.performAudio96DocsIngestion(Input.audioDatFile, Input.docIdKey);
			ingestionName = ingestionPage.publishAddonlyIngestion(Input.audio96DocsFolder);			
		}
		else {
			ingestionName = ingestionPage.getPublishedIngestionName(Input.audio96DocsFolder);
		}
		baseClass.stepInfo("unpublish docs");
		sessionSearch.MetaDataSearchInBasicSearch(Input.metadataIngestion,ingestionName);
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
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.overlayOnly, null, Input.sourceLocation, Input.audio96DocsFolder);
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		baseClass.stepInfo("Selecting Dat and mp3 file");
		ingestionPage.selectDATSource( Input.audioDatFile, Input.docIdKey);
		ingestionPage.selectMP3VarientSource(Input.selectMp3File, false);
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		ingestionPage.clickOnNextButton();
		ingestionPage.performMappingInConfigureSection(2, Input.docIdKey,Input.ingDocBasic, Input.sourceParentDocId);
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
	 * Author :Arunkumar date: 12/09/2022 TestCase Id:RPMXCON-49357
	 * Description :To verify that Overlay should work for  'SourceParentDocID' metadata. 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49357",enabled = true, groups = { "regression" })
	public void TCA5verifyIngestionOverlayForDifferentMetadata() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-49357");
		baseClass.stepInfo("To verify that Overlay should work for  'SourceParentDocID' metadata");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.passedStep("perform add only ingestion");
		boolean status = ingestionPage.verifyIngestionpublish(Input.attachDocFolder);
		if (status == false) {
			baseClass.stepInfo("Add new ingestion");
			ingestionPage.IngestionOnlyForDatFile(Input.attachDocFolder, Input.datFile7);
			ingestionPage.publishAddonlyIngestion(Input.attachDocFolder);			
		}
		baseClass.stepInfo("Perform overlay ingestion with 'SourceParentDocID'");
		ingestionPage.OverlayIngestionForDATWithMappingFieldSection(Input.attachDocFolder, Input.datFile7, Input.sourceDocIdSearch);
		if(ingestionPage.sourceFieldOption(1,Input.sourceParentDocId).isElementAvailable(10)) {
			baseClass.passedStep("metadata 'SourceParentDocID' available for overlay");
		}
		else {
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
	 * Author :Arunkumar date: 14/09/2022 TestCase Id:RPMXCON-47155
	 * Description :New Project - Ingestion audio documents, verify language packs and audio search
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47155",enabled = true, groups = { "regression" })
	public void TCA3verifyAudioDocsSearchIngestion() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47155");
		baseClass.stepInfo("New Project -Ingestion audio documents, verify language packs and audio search");
		String[] audioSearch = {Input.audioSearchString1,Input.audioSearchString2};
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
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
		for(int i=0;i<audioSearch.length;i++) {
			int docsCount = sessionSearch.audioSearch(audioSearch[i], Input.language);
			sessionSearch.verifySearchResultReturnsForConfiguredQuery(docsCount);
			baseClass.selectproject();
		}
		baseClass.passedStep("Audio searches returned results");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 14/09/2022 TestCase Id:RPMXCON-48199
	 * Description :To Verify Ingestion Overlays of DAT without unpublish. 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48199",enabled = true, groups = { "regression" })
	public void TCA4verifyIngestionOverlaywithoutUnpublish() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-48199");
		baseClass.stepInfo("To Verify Ingestion Overlays of DAT without unpublish.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Add new ingestion with overwrite option as 'Add only'.");
		boolean status = ingestionPage.verifyIngestionpublish(Input.Collection1KFolder);
		if (status == false) {
			ingestionPage.performCollection1kTallyIngestion(Input.sourceSystem,Input.datLoadFile3, Input.textFile1);
			ingestionPage.publishAddonlyIngestion(Input.Collection1KFolder);
		}
		baseClass.stepInfo("Perform overlay ingestion with modified dat file");
		ingestionPage.OverlayIngestionForDATWithMappingFieldSection(Input.Collection1KFolder,
				Input.overlayDatFile, Input.docId);
		ingestionPage.clickOnPreviewAndRunButton();
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();
		baseClass.passedStep("Overlay ingestion performed successfully without unpublish");
		baseClass.stepInfo("Search for custodian name docs");
		baseClass.selectproject();
		int docsCount=sessionSearch.MetaDataSearchInBasicSearch(Input.metaDataName, Input.custodianName_allen);
		sessionSearch.verifySearchResultReturnsForConfiguredQuery(docsCount);
		baseClass.passedStep("Able to perform overlay without unpublish");
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 28/09/2022 TestCase Id:RPMXCON-60860
	 * Description :Check whether user is able to ignore the error obtained at cataloged stage 
	 * while ingesting 'Dat' file containing Splitted fullpath(folder name) data having 
	 * more than 400 chars in size  
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-60860",enabled = true, groups = { "regression" })
	public void TCA6verifyDatIngestionMoreThan400Chars() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-60860");
		baseClass.stepInfo("Verify ingestion Dat file data having more than 400 chars.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("perform add only ingestion with more than 400 char dat");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AutomationAllSources);
		if (status == false) {
			ingestionPage.IngestionOnlyForDatFile(Input.AutomationAllSources,Input.char400Dat);
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
		}
		else {
			baseClass.passedStep("Ingestion already present in the project");
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 29/09/2022 TestCase Id:RPMXCON-59386
	 * Description :Verify that error should not be displayed while running Ingestion 
	 * when PDF option is selected with Is Path in DAT option  
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-59386",enabled = true, groups = { "regression" })
	public void TCA7verifyPdfIsPathInDatAddOnly() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-59386");
		baseClass.stepInfo("Verify add only ingestion with pdf option is path in dat");
		String ingestionName=null;
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Perform add only ingestion");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AK_NativeFolder);
		if (status == false) {
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
					Input.sourceLocation, Input.AK_NativeFolder);
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
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
			ingestionPage.selectValueFromEnabledFirstThreeSourceDATFields(Input.prodBeg, Input.prodBeg, Input.custodian);
			ingestionPage.clickOnPreviewAndRunButton();
			baseClass.stepInfo("Publish ingestion");
			ingestionName= ingestionPage.publishAddonlyIngestion(Input.AK_NativeFolder);
		}
		else {
			ingestionName = ingestionPage.getPublishedIngestionName(Input.AK_NativeFolder);
		}
		baseClass.stepInfo("view docs in docview");
		dataSets = new DataSets(driver);
		dataSets.navigateToDataSetsPage();
		dataSets.selectDataSetWithNameInDocView(ingestionName);
		baseClass.verifyUrlLanding(Input.url + "DocumentViewer/DocView", " on docview page",
					"Not on docview page");
		baseClass.passedStep("Error not disaplayed when pdf is path in Dat option");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 29/09/2022 TestCase Id:RPMXCON-59387
	 * Description :Verify that error should not be displayed with Ingestion overlay
	 * when PDF option is selected with Is Path in DAT option  
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-59387",enabled = true, groups = { "regression" })
	public void TCA8verifyPdfIsPathInDatOverlay() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-59387");
		baseClass.stepInfo("Verify overlay ingestion with pdf option is path in dat");
		String ingestionName = null;
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Perform add only ingestion");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AK_NativeFolder);
		if (status == false) {
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
					Input.sourceLocation, Input.AK_NativeFolder);
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
			baseClass.stepInfo("Selecting Dat file");
			ingestionPage.selectDATSource(Input.pdfPathDat, Input.prodBeg);
			baseClass.stepInfo("Select date format");
			ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
			baseClass.stepInfo("click on next button");
			ingestionPage.clickOnNextButton();
			baseClass.stepInfo("perform mapping and run ingestion");
			ingestionPage.selectValueFromEnabledFirstThreeSourceDATFields(Input.prodBeg, Input.prodBeg, Input.custodian);
			ingestionPage.clickOnPreviewAndRunButton();
			baseClass.stepInfo("Publish ingestion");
			ingestionPage.publishAddonlyIngestion(Input.AK_NativeFolder);
		}
		baseClass.stepInfo("Perform overlay ingestion");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.overlayOnly, Input.sourceSystem,
				Input.sourceLocation, Input.AK_NativeFolder);
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
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
		ingestionName =ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();
		baseClass.stepInfo("view docs in docview");
		dataSets = new DataSets(driver);
		dataSets.navigateToDataSetsPage();
		dataSets.selectDataSetWithNameInDocView(ingestionName);
		baseClass.verifyUrlLanding(Input.url + "DocumentViewer/DocView", " on docview page",
					"Not on docview page");
		baseClass.passedStep("Error not displayed when pdf is path in Dat option after performing overlay");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 03/10/2022 TestCase Id:RPMXCON-48627
	 * Description :To verify that search result for overlaid text is not displays
	 *  if document is not published
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48627",enabled = true, groups = { "regression" })
	public void TCA9verifyResultOfOverlaidText() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-48627");
		baseClass.stepInfo("Verify search result for overlaid text");
		String ingestionName = null;
		String BasicSearchName = "search"+Utility.dynamicNameAppender();
		
		//pre-requisite
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");
		baseClass.stepInfo("Navigating to manage project page");
		project = new ProjectPage(driver);
		project.navigateToProductionPage();
		baseClass.stepInfo("Disable auto analytics");
		project.disableOrEnableKickOffAnalytics(Input.projectName,"Disable");
		loginPage.logout();
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Perform add only ingestion with text files");
		boolean status = ingestionPage.verifyIngestionpublish(Input.UniCodeFilesFolder);
		if (status == false) {
			ingestionPage.unicodeFilesIngestion(Input.datLoadFile1, Input.textFile1, Input.documentKey);
			ingestionName =ingestionPage.publishAddonlyIngestion(Input.UniCodeFilesFolder);		
		}
		else {
			ingestionName = ingestionPage.getPublishedIngestionName(Input.UniCodeFilesFolder);
		}
		baseClass.stepInfo("Unpublish docs");
		sessionSearch.MetaDataSearchInBasicSearch(Input.metadataIngestion,ingestionName);
		sessionSearch.saveSearch(BasicSearchName);
		ingestionPage.unpublish(BasicSearchName);
		baseClass.stepInfo("Perform overlay ingestion for unpublished text files");
		ingestionPage.OverlayIngestionWithDat(Input.UniCodeFilesFolder, Input.datLoadFile1, Input.documentKey,
				"text", Input.textFile1);
		ingestionName=ingestionPage.verifyApprovedStatusForOverlayIngestion();
		baseClass.selectproject();
		int count =sessionSearch.MetaDataSearchInBasicSearch(Input.metadataIngestion,ingestionName);
		baseClass.stepInfo("count :"+count);
		if(count==0) {
			baseClass.passedStep("Result of overlaid text not displayed when docs are not published");
		}
		else {
			baseClass.failedStep("Result of overlaid text displayed without publishing docs");
		}
		loginPage.logout();
		//setting kick off analytics back to default
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		ingestionPage.navigateToManageProjectPage();
		project.disableOrEnableKickOffAnalytics(Input.projectName,"Enable");
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
