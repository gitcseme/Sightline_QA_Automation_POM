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
import pageFactory.BaseClass;
import pageFactory.DataSets;
import pageFactory.DocListPage;
import pageFactory.IngestionPage_Indium;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Ingestion_Regression_3 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	IngestionPage_Indium ingestionPage;
	SessionSearch sessionSearch;
	DocListPage docList;
	DataSets dataSets;
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
	 * Author :Arunkumar date: 25/07/2022 TestCase Id:RPMXCON-47364
	 * Description :To verify that in Grid view, information of tiles are displayed in tabular with the
	 * required columns
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47364",enabled = true, groups = { "regression" })
	public void TCA1verifyGridViewInformation() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47364");
		baseClass.stepInfo("Validate the columns available in grid view table");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
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
	public void TCA2verifyFieldsSelectedInMapping() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47297");
		baseClass.stepInfo("verify that mandatory fields must be selected in mapping");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
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
	public void TCA3verifySourceSelectionInSourceSettings() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47578");
		baseClass.stepInfo("verify that in 'Source & Overwrite Settings'section, the source selection is changed.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
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
	public void TCA4verifyRowPopulationInConfigureMappingSection() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47581");
		baseClass.stepInfo("verify that row population in the Configure Mapping section.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
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
	public void TCA5verifyConfigureMappingWarningWhenMappingNotMatched() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47586");
		baseClass.stepInfo("verify configure mapping warning message when mapping not matched");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
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
	public void TCA6verifyConfigureMappingWhenMappingMatched() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47585");
		baseClass.stepInfo("verify configure mapping warning message when mapping matched");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
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
	public void TCA7verifyRollbackAndDeleteIngestion() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47375");
		baseClass.stepInfo("verify that ingestion which is Rolled back can be deleted");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Add new ingestion and Perform Catalogging");
		ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder, Input.YYYYMMDDHHMISSDat);
		ingestionPage.ignoreErrorsAndCatlogging();
		baseClass.stepInfo("Rollback and Delete ingestion using action dropdown menu");
		ingestionPage.performRollbackAndDeleteIngestion();
		baseClass.passedStep("Verified that ingestion which is rolledback can be deleted");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 12/07/2022 TestCase Id:RPMXCON-48291
	 * Description :To verify In Ingestion, Copy ,ASCII(59) should be default New Line delimiter.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48291",enabled = true, groups = { "regression" })
	public void TCA8verifyDefaultNewLineDelimiter() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-48291");
		baseClass.stepInfo("verify In Ingestion, Copy ,ASCII(59) should be default New Line delimiter.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
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
	public void TCA9verifyResultOfOverlaidText() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48626");
		baseClass.stepInfo("verify that after the publish is been done,user can view the search result for overlaid text");
		docList= new DocListPage(driver);
		dataSets = new DataSets(driver);
		String BasicSearchName = "search"+Utility.dynamicNameAppender();
		String ingestionName = null;
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
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
	public void TCB1verifyIngestionRollbackForAudio() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-46886");
		baseClass.stepInfo("Verify Ingestion rollback for Audio indexed & non Audio Indexed.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
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
		loginPage.logout();
		}
		else {
			baseClass.failedMessage("Add only ingestion for the dataset already present in the current project");;
		}
		}
	
	/**
	 * Author :Arunkumar date: 12/07/2022 TestCase Id:RPMXCON-47824
	 * Description :Verify overlay of the same files, which are already ingested and available in Production DB.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47824",enabled = true, groups = { "regression" })
	public void TCB2verifyOverlayIngestionOfSameFiles() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47824");
		baseClass.stepInfo("verify overlay ingestion of the same files, which are already ingested");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
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
	public void TCB3verifyOverlayIngestionOfAudioWithTextFiles() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-48595");
		baseClass.stepInfo("Verify the Analytics process should take places when Audio files with Text files are overlayed.");
		String BasicSearchName = "search"+Utility.dynamicNameAppender();
		String ingestionName = null;
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
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
	public void TCB4verifyOverlayIngestionOfTiffWithTextFiles() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-48596");
		baseClass.stepInfo("Verify the Analytics process should take places when Tiff files and Text files are overlayed.");
		String BasicSearchName = "search"+Utility.dynamicNameAppender();
		String ingestionName = null;
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
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
	public void TCB5verifyOverlayIngestionOfNativeWithTextFiles() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-48599");
		baseClass.stepInfo("Verify the Analytics process should take places when Native files and Text files are overlayed.");
		String BasicSearchName = "search"+Utility.dynamicNameAppender();
		String ingestionName = null;
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
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
	public void TCB6verifyOverlayWhenDocsUnpublished() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48629");
		baseClass.stepInfo("verify that user can overlay text only when all the docs being overlaid are unpublished");
		docList= new DocListPage(driver);
		dataSets = new DataSets(driver);
		String BasicSearchName = "search"+Utility.dynamicNameAppender();
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
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
	public void TCB7verifyTotalUniqueDocsCountIngestedAndPublished() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49257");
		baseClass.stepInfo("verify that ingestions page should present the unique number of docs ingested and published");
		String BasicSearchName = "search"+Utility.dynamicNameAppender();
		String ingestionName=null;
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
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
	public void TCB8verifyCountOfTextFilesWhenOverlay() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-48608");
		baseClass.stepInfo("Verify that ingestion detail popup reflect the count of text files.");
		String BasicSearchName = "search"+Utility.dynamicNameAppender();
		String ingestionName = null;
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
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
	public void TCB9verifyPerformingIngestionAnalyticsFor500Docs() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-49019");
		baseClass.stepInfo("Verify ingesting 500 documents and do analytics these documents.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
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
	public void TCC1verifyCloseButtonRedirectsToHomePage() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47368");
		baseClass.stepInfo("verify that selection of Close button redirects to Ingestion Home page");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
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
	public void TCC2verifyExportErrorDetails() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-49331");
		baseClass.stepInfo("Verify that if Ingestion failed in Cataloging, then user can export the error details successfully");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage = new IngestionPage_Indium(driver);
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
