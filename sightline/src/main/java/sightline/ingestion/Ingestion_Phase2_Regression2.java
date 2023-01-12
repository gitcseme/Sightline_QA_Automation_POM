package sightline.ingestion;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
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

public class Ingestion_Phase2_Regression2 {
	
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
	ProjectFieldsPage projectFieldPage;
	

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

	/*This class contains 70 testCases from sprint 24 to 28.*/
	
	/**
	 * Author :Arunkumar date: 17/10/2022 TestCase Id:RPMXCON-48073
	 * Description :To Verify EmailDateSentTimeOnly in tally and in Search- Both fields should be false by default
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48073",enabled = true, groups = { "regression" })
	public void verifyEmailDateSentTimeOnlyInTallyAndSearch() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48073");
		baseClass.stepInfo("Verify EmailDateSentTimeOnly in tally and in Search.");
		String field = "EmailDateSentTimeOnly";
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("go to search and check field availability in metadata");
		sessionSearch.navigateToSessionSearchPageURL();
		baseClass.waitForElement(sessionSearch.getBasicSearch_MetadataBtn());
		sessionSearch.getBasicSearch_MetadataBtn().waitAndClick(10);
		if(sessionSearch.getMetaDataInDropdown(field).isElementAvailable(10)) {
			baseClass.failedStep(field+"is available in basic search metadata dropdown");
		}
		else {
			baseClass.passedStep(field+"is not available in basic search metadata dropdown");
		}
		baseClass.stepInfo("go to report-tally and check field availability in metadata");
		tally = new TallyPage(driver);
		tally.verifyMetaDataUnAvailabilityInTallyReport(field);
		baseClass.stepInfo("Verify the field value of 'EmailDateSentTimeOnly'");
		ProjectFieldsPage projectFieldPage = new ProjectFieldsPage(driver);
		projectFieldPage.applyFilterByFilterName(field);
		projectFieldPage.verifyExpectedFieldStatus(field,"false","false","active");
		baseClass.passedStep(field+"is tallyable and is searchable false by default");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 18/10/2022 TestCase Id:RPMXCON-48080
	 * Description :To Verify ExcelProtectedWorkbook in Tally and Search.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48080",enabled = true, groups = { "regression" })
	public void verifyExcelProtectedWorkbookInTallyAndSearch() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48080");
		baseClass.stepInfo("Verify ExcelProtectedWorkbook in Tally and Search.");
		String field = "ExcelProtectedWorkbook";
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion");
		boolean status = ingestionPage.verifyIngestionpublish(Input.HiddenPropertiesFolder);
		if (status == false) {
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
					Input.sourceLocation, Input.HiddenPropertiesFolder);
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
			baseClass.stepInfo("Selecting Dat file");
			ingestionPage.selectDATSource(Input.hiddenPropDat, Input.sourceDocIdSearch);
			baseClass.stepInfo("Selecting Native file");
			ingestionPage.selectNativeSource(Input.hiddenPropNative, false);
			baseClass.stepInfo("Select date format");
			ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
			baseClass.stepInfo("click on next button");
			ingestionPage.clickOnNextButton();
			ingestionPage.clickOnPreviewAndRunButton();
			baseClass.stepInfo("Publish add only ingestion");
			ingestionPage.publishAddonlyIngestion(Input.HiddenPropertiesFolder);
		}
		baseClass.stepInfo("Navigate to report-tally and select metadata");
		tally = new TallyPage(driver);
		tally.navigateTo_Tallypage();
		tally.selectSourceByProject();
		tally.selectTallyByMetaDataField(field);
		baseClass.waitForElement(tally.getTally_btnTallyAll());
		tally.getTally_btnTallyAll().waitAndClick(10);
		HashMap<String, Integer> map = tally.getDocsCountFortallyReport();
		int yesCount = map.get("Yes");
		baseClass.stepInfo("ExcelProtectedWorkbook-yes status count"+yesCount);
		int noCount = map.get("No");
		baseClass.stepInfo("ExcelProtectedWorkbook-No status count"+noCount );
		baseClass.stepInfo("perform search with field and verify purehit count with report");
		int excelprotectCount =sessionSearch.MetaDataSearchInBasicSearch("ExcelProtectedWorkbook", "Yes");
		baseClass.selectproject();
		int excelNotProtectCount =sessionSearch.MetaDataSearchInBasicSearch("ExcelProtectedWorkbook", "No");
		if(excelprotectCount==yesCount && excelNotProtectCount==noCount) {
			baseClass.passedStep(field + "Counts matched for search result and tally report");
		}
		else {
			baseClass.failedStep(field + "Counts not matched for search result and tally report");
		}
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 18/10/2022 TestCase Id:RPMXCON-48074
	 * Description :To Verify FileDescription in Tally and Search.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48074",enabled = true, groups = { "regression" })
	public void verifyFileDescriptionInTallyAndSearch() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48074");
		baseClass.stepInfo("Verify FileDescription in Tally and Search.");
		String field = "FileDescription";
		String fileType= "\""+"Microsoft Word Document"+"\"";
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Navigate to report-tally and select metadata");
		tally = new TallyPage(driver);
		tally.navigateTo_Tallypage();
		tally.selectSourceByProject();
		tally.selectTallyByMetaDataField(field);
		baseClass.waitForElement(tally.getTally_btnTallyAll());
		tally.getTally_btnTallyAll().waitAndClick(10);
		HashMap<String, Integer> map = tally.getDocsCountFortallyReport();
		int tallyCount = map.get("Microsoft Word Document");
		baseClass.stepInfo("FileDescription-"+fileType+ "count"+tallyCount);
		baseClass.stepInfo("perform search with field and verify purehit count with report");
		int purehitCount =sessionSearch.MetaDataSearchInBasicSearch(field, fileType);
		if(purehitCount==tallyCount) {
			baseClass.passedStep(field+"Counts matched for search result and tally report");
		}
		else {
			baseClass.failedStep(field+"Counts not matched for search result and tally report");
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 21/10/2022 TestCase Id:RPMXCON-49554
	 * Description :Verify that Ingestion Email Metadata 'EmailCCNamesAndAddresses' is available.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49554",enabled = true, groups = { "regression" })
	public void verifyEmailCcMetadataAvailability() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49554");
		baseClass.stepInfo("Verify that Ingestion Email Metadata 'EmailCCNamesAndAddresses' is available");
		String data ="EmailCCNamesAndAddresses";
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		//add dataset details and click next
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Adding ingestion details");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
				Input.sourceLocation, Input.GD994NativeTextForProductionFolder);
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		baseClass.stepInfo("Selecting Dat file");
		ingestionPage.selectDATSource(Input.datLoadFile4, Input.documentKey);
		baseClass.stepInfo("Select date format");
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		baseClass.stepInfo("click on next button");
		ingestionPage.clickOnNextButton();
		driver.waitForPageToBeReady();
		//Verify destination field 'EmailCCNamesAndAddresses' metadata
		baseClass.stepInfo("Select 'EMAIL' in field category");
		baseClass.waitForElement(ingestionPage.getMappingCategoryField(6));
		ingestionPage.getMappingCategoryField(6).selectFromDropdown().selectByVisibleText(Input.email);
		baseClass.stepInfo("verify metadata");
		ingestionPage.verifyMetadataAvailability(ingestionPage.getMappingDestinationField(6), data);
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 21/10/2022 TestCase Id:RPMXCON-49555
	 * Description :Verify that Ingestion Email Metadata 'EmailBCCNamesAndAddresses' is available
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49555",enabled = true, groups = { "regression" })
	public void verifyEmailBccMetadataAvailability() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49555");
		baseClass.stepInfo("Verify that Ingestion Email Metadata 'EmailBCCNamesAndAddresses' is available");
		String data ="EmailBCCNamesAndAddresses";
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		//add dataset details and click next
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Adding ingestion details");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
				Input.sourceLocation, Input.GD994NativeTextForProductionFolder);
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		baseClass.stepInfo("Selecting Dat file");
		ingestionPage.selectDATSource(Input.datLoadFile4, Input.documentKey);
		baseClass.stepInfo("Select date format");
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		baseClass.stepInfo("click on next button");
		ingestionPage.clickOnNextButton();
		driver.waitForPageToBeReady();
		//Verify destination field 'EmailBCCNamesAndAddresses' metadata
		baseClass.stepInfo("Select 'EMAIL' in field category");
		baseClass.waitForElement(ingestionPage.getMappingCategoryField(6));
		ingestionPage.getMappingCategoryField(6).selectFromDropdown().selectByVisibleText(Input.email);
		baseClass.stepInfo("verify metadata");
		ingestionPage.verifyMetadataAvailability(ingestionPage.getMappingDestinationField(6), data);
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 25/10/2022 TestCase Id:RPMXCON-49504
	 * Description :Verify that tooltip should be displayed for 'Date & Time Format' field.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49504",enabled = true, groups = { "regression" })
	public void verifyToolTipForDateFormat() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49504");
		baseClass.stepInfo("Verify that tooltip should be displayed for 'Date & Time Format' field");
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion details");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
				Input.sourceLocation, Input.GD994NativeTextForProductionFolder);
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		baseClass.stepInfo("Selecting Dat file");
		ingestionPage.selectDATSource(Input.datLoadFile4, Input.documentKey);
		//verify tool tip for date format
		baseClass.stepInfo("check the tooltip message for date field");
		ingestionPage.VerifyToolTipMessageForDateFormat();
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 25/10/2022 TestCase Id:RPMXCON-49503
	 * Description :Verify that in the ingestion wizard page, "date & time format" field should present 
	 * the dropdown with the different supported formats
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49503",enabled = true, groups = { "regression" })
	public void verifyToolTipAndDefaultSelectionForDateFormat() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49503");
		baseClass.stepInfo("Verify that different formats present in 'Date & Time Format' field");
	
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion details");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
				Input.sourceLocation, Input.GD994NativeTextForProductionFolder);
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		baseClass.stepInfo("Selecting Dat file");
		ingestionPage.selectDATSource(Input.datLoadFile4, Input.documentKey);
		//verify tool tip,number of formats and default selection for date-time format
		ingestionPage.VerifyToolTipMessageForDateFormat();
		ingestionPage.verifyDateFormatDropDownValidations();
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 25/10/2022 TestCase Id:RPMXCON-47363
	 * Description :To verify that user is able to view two buttons of Grid and Tile view.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47363",enabled = true, groups = { "regression" })
	public void verifyGridAndTileviewOptions() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47363");
		baseClass.stepInfo("To verify that user is able to view two buttons of Grid and Tile view.");
	
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.verifyUrlLanding(Input.url + "Ingestion/Home", "Ingestion home page displayed", 
				"not in ingestion home page");
		baseClass.waitForElement(ingestionPage.getIngestion_GridView());
		if(ingestionPage.getIngestion_GridView().isDisplayed() && 
				ingestionPage.getIngestion_TileView().isDisplayed()) {
			baseClass.passedStep("both grid and tile view button displayed in home page");
		}
		else {
			baseClass.failedStep("both grid and tile view buttons not available");
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 25/10/2022 TestCase Id:RPMXCON-47307
	 * Description :To verify that Ingestion Count on Ingested Home page is displayed correctly
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47307",enabled = true, groups = { "regression" })
	public void verifyIngestionCountOnHomePage() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47307");
		baseClass.stepInfo("To verify that Ingestion Count on Ingested Home page is displayed correctly");
	
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.verifyUrlLanding(Input.url + "Ingestion/Home", "Ingestion home page displayed", 
				"not in ingestion home page");
		baseClass.stepInfo("add new ingestion details and start ingestion");
		ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder, Input.YYYYMMDDHHMISSDat);
		ingestionPage.verifyDetailsAfterStartedIngestion();
		//verify
		baseClass.stepInfo("verify ingestion count on home page");
		ingestionPage.verifyIngestionCountInHomePage();
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 25/10/2022 TestCase Id:RPMXCON-47458
	 * Description :To verify that Back button functionality on Configure Mapping page.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47458",enabled = true, groups = { "regression" })
	public void verifyBackButtonFunctionality() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47458");
		baseClass.stepInfo("To verify that Back button functionality on Configure Mapping page.");
	
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.verifyUrlLanding(Input.url + "Ingestion/Home", "Ingestion home page displayed", 
				"not in ingestion home page");
		baseClass.stepInfo("Add new ingestion details and click on next");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
				Input.sourceLocation, Input.GD994NativeTextForProductionFolder);
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		baseClass.stepInfo("Selecting Dat file");
		ingestionPage.selectDATSource(Input.datLoadFile4, Input.documentKey);
		baseClass.stepInfo("Select date format");
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		baseClass.stepInfo("click on next button");
		ingestionPage.clickOnNextButton();
		//verify status after clicking next button
		ingestionPage.verifySourceSectionStatusAfterClickingNextButton();
		ingestionPage.verifyAutoPopulatedSourceFieldInMappingSection();
		baseClass.passedStep("Source field gets auto populated as per the fields available in the DAT file.");
		//verify status after clicking back button
		ingestionPage.verifySourceAndMappingSectionStatusAfterClickingBackButton();
		ingestionPage.verifyAutoPopulatedSourceFieldInMappingSection();
		baseClass.passedStep("Auto populated field remains on the disabled mapping section");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 26/10/2022 TestCase Id:RPMXCON-60818
	 * Description :Verify that if the Absolute path is present in the Transcript LST, 
	 * then Ingestion should be successful. 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-60818",enabled = true, groups = { "regression" })
	public void verifyAbsolutePathInTranscript() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-60818");
		baseClass.stepInfo("To verify that if the Absolute path is present in the Transcript LST.");
		String ingestionName = null;
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion with transcript");
		boolean status = ingestionPage.verifyIngestionpublish(Input.uncPath);
		if (status == false) {
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
					Input.sourceLocation, Input.uncPathFolder);
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
			baseClass.stepInfo("Selecting Dat file");
			ingestionPage.selectDATSource(Input.uncAbsoluteDat, Input.documentKey);
			baseClass.stepInfo("Selecting transcript file");
			ingestionPage.selectAudioTranscriptSource(Input.uncAbsoluteTranscript, false);
			ingestionPage.unCheckLoadFile(ingestionPage.getTIFFLST(), ingestionPage.getTIFFCheckBox());
			baseClass.stepInfo("Select date format");
			ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
			baseClass.stepInfo("click on next button");
			ingestionPage.clickOnNextButton();
			ingestionPage.selectValueFromEnabledFirstThreeSourceDATFields(Input.documentKey, 
					Input.documentKey, Input.documentKeyCName);
			ingestionPage.clickOnPreviewAndRunButton();
			baseClass.stepInfo("Publish add only ingestion");
			ingestionName=ingestionPage.publishAddonlyIngestion(Input.uncPathFolder);
		}
		else {
			ingestionName = ingestionPage.getPublishedIngestionName(Input.uncPath);
		}
		baseClass.passedStep("Ingestion Name :"+ingestionName);
		baseClass.stepInfo("go to doc explorer");
		docExplorer = new DocExplorerPage(driver);
		baseClass.verifyUrlLanding(Input.url + "DocExplorer/Explorer", "navigated to docexplorer page", 
				"not on docexplorer page");
		//verify selecting docs and navigate to docview
		docExplorer.docExpToDocViewWithIngestion(ingestionName,"no");
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 27/10/2022 TestCase Id:RPMXCON-60819
	 * Description :Verify that if the Absolute path is present in the Translation LST, 
	 * then Ingestion should be successful.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-60819",enabled = true, groups = { "regression" })
	public void verifyAbsolutePathInTranslation() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-60819");
		baseClass.stepInfo("To verify that Absolute path is present in the Translation LST.");
		String ingestionName = null;

		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion with transcript");
		boolean status = ingestionPage.verifyIngestionpublish(Input.uncPath);
		if (status == false) {
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
					Input.sourceLocation, Input.uncPathFolder);
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
			baseClass.stepInfo("Selecting Dat file");
			ingestionPage.selectDATSource(Input.uncAbsoluteDat, Input.documentKey);
			baseClass.stepInfo("Selecting translation file");
			ingestionPage.selectOtherSource("Translation", Input.uncAbsoluteTranslation, false);
			ingestionPage.unCheckLoadFile(ingestionPage.getTIFFLST(), ingestionPage.getTIFFCheckBox());
			baseClass.stepInfo("Select date format");
			ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
			baseClass.stepInfo("click on next button");
			ingestionPage.clickOnNextButton();
			ingestionPage.selectValueFromEnabledFirstThreeSourceDATFields(Input.documentKey, 
					Input.documentKey, Input.documentKeyCName);
			ingestionPage.clickOnPreviewAndRunButton();
			baseClass.stepInfo("Publish add only ingestion");
			ingestionName=ingestionPage.publishAddonlyIngestion(Input.uncPathFolder);
		}
		else {
			ingestionName = ingestionPage.getPublishedIngestionName(Input.uncPath);
		}
		baseClass.passedStep("Ingestion Name :"+ingestionName);
		baseClass.stepInfo("go to doc explorer");
		docExplorer = new DocExplorerPage(driver);
		baseClass.verifyUrlLanding(Input.url + "DocExplorer/Explorer", "navigated to docexplorer page", 
				"not on docexplorer page");
		//verify selecting docs and navigate to docview
		docExplorer.docExpToDocViewWithIngestion(ingestionName,"no");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 27/10/2022 TestCase Id:RPMXCON-58507
	 * Description :Verify if user ingest documents with 'Mapped Data' as Source System then 
	 * same dataset cannot ingest with any other Source System 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-58507",enabled = true, groups = { "regression" })
	public void verifyAddOnlyForDifferentSourceSystem() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-58507");
		baseClass.stepInfo("Verify add only ingestion for same dataset with different source system.");
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.verifyUrlLanding(Input.url + "Ingestion/Home", "Ingestion home page displayed", 
				"not in ingestion home page");
		//perform add only ingestion with 'mapped data' source system
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		if (status == false) {
			ingestionPage.performAutomationAllsourcesIngestion(Input.mappedData,Input.DATFile1, Input.prodBeg);
			ingestionPage.publishAddonlyIngestion(Input.AllSourcesFolder);
		}
		//perform same dataset ingestion with different source system
		ingestionPage.performAutomationAllsourcesIngestion(Input.sourceSystem,Input.DATFile1, Input.prodBeg);
		ingestionPage.verifyDuplicateIngestionErrorMessage();
		baseClass.passedStep("Ingestion failed in the catalog stage when ingesting same dataset");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 31/10/2022 TestCase Id:RPMXCON-48615
	 * Description :To verify that after a text overlay ingestion, the Ingestion Details popup page
	 * should reflect the count of indexed text files under the Indexing section
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48615",enabled = true, groups = { "regression" })
	public void verifyTextOverlayCountUnderIndexingSection() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48615");
		baseClass.stepInfo("Verify overlay text files count reflecting under indexing section");
		String ingestionName = null;
		String searchName = "Search"+Utility.dynamicNameAppender();
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		//add new ingestion with text files
		boolean status = ingestionPage.verifyIngestionpublish(Input.UniCodeFilesFolder);
		if (status == false) {
			baseClass.stepInfo("Perform new add only ingestion with text files");
			ingestionPage.unicodeFilesIngestion(Input.datLoadFile1, Input.textFile1, Input.documentKey);
			ingestionName =ingestionPage.publishAddonlyIngestion(Input.UniCodeFilesFolder);
		}
		else {
			ingestionName = ingestionPage.getPublishedIngestionName(Input.UniCodeFilesFolder);
		}
		//unpublish files and start overlay
		baseClass.stepInfo("search and unpublish text files");
		sessionSearch.MetaDataSearchInBasicSearch(Input.metadataIngestion,ingestionName);
		sessionSearch.saveSearch(searchName);
		ingestionPage.unpublish(searchName);
		baseClass.stepInfo("Perform overlay ingestion for unpublished text files");
		ingestionPage.OverlayIngestionWithDat(Input.UniCodeFilesFolder, Input.datLoadFile1, Input.documentKey,
				"text", Input.textFile1);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.verifyDataPresentInIndexTableColumn(Input.text, Input.sourceDocs);
		baseClass.passedStep("overlaid Text files count reflected under indexing section");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 31/10/2022 TestCase Id:RPMXCON-47290
	 * Description :Specify Source and overwrite option' page is accessable and
	 * verify that all fieldsets are mandatory.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47290",enabled = true, groups = { "regression" })
	public void verifyMandatoryFieldsInSourceAndSettingsOption() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47290");
		baseClass.stepInfo("verify the mandatory fields in source and overwrite settings option");
	
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Click on add new ingestion link");
		baseClass.waitForElement(ingestionPage.getAddanewIngestionButton());
		ingestionPage.getAddanewIngestionButton().waitAndClick(10);
		baseClass.verifyUrlLanding(Input.url + "en-us/Ingestion/Wizard", "source/overwrite page displayed", 
				"source/overwrite setting page not displayed");
		baseClass.stepInfo("Without entering mandatory fields click next button");
		baseClass.waitForElement(ingestionPage.getIngestion_IngestionType());
		ingestionPage.getIngestion_IngestionType().selectFromDropdown().selectByVisibleText(Input.ingestionType);
		baseClass.waitForElement(ingestionPage.getNextButton());
		ingestionPage.getNextButton().waitAndClick(10);
		baseClass.stepInfo("verify mandatory fields warning messages");
		ingestionPage.validateMandatoryFieldMessagesDisplayed();
		baseClass.passedStep("Validation messages displayed to all mandatory fields");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 31/10/2022 TestCase Id:RPMXCON-48605
	 * Description :To verify that users should be able to successfully conduct searches 
	 * which take the text files ingested into consideration
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48605",enabled = true, groups = { "regression" })
	public void verifyTextFilesSearchConsideration() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48605");
		baseClass.stepInfo("Verify that users should be able to successfully conduct searches");
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		//add new ingestion with text files
		boolean status = ingestionPage.verifyIngestionpublish(Input.UniCodeFilesFolder);
		if (status == false) {
			baseClass.stepInfo("Perform new add only ingestion with text files");
			ingestionPage.unicodeFilesIngestion(Input.datLoadFile1, Input.textFile1, Input.documentKey);
			ingestionPage.publishAddonlyIngestion(Input.UniCodeFilesFolder);
		}
		baseClass.stepInfo("Perform search with 'docfileextension' metadata");
		int count =sessionSearch.MetaDataSearchInBasicSearch(Input.docFileExt,".txt");
		baseClass.stepInfo("Text files search result:"+count);
		if(count!=0) {
			sessionSearch.verifySearchResultReturnsForConfiguredQuery(count);
			int textFormatDocs = ingestionPage.textFormatDocs().size();
			baseClass.stepInfo("text format docs:"+textFormatDocs);
			if(textFormatDocs>0) {
				baseClass.passedStep("pure hit considered docs with .txt format");
			}
			else {
				baseClass.failedStep("pure hit not considered docs with .txt format");
			}
		}
		else {
			baseClass.failedMessage("Doc file extension for text files not present");
		}
		
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 31/10/2022 TestCase Id:RPMXCON-47286
	 * Description :To verify Ingestion Wizards "Source Selection & Ingestion Type" Sections 
	 * for various Options
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47286",enabled = true, groups = { "regression" })
	public void verifyVariousOptionsInIngestionWizard() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47286");
		baseClass.stepInfo("verify 'Source Selection & Ingestion Type' Sections for various Options");
	
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Click on add new ingestion link");
		baseClass.waitForElement(ingestionPage.getAddanewIngestionButton());
		ingestionPage.getAddanewIngestionButton().waitAndClick(10);
		baseClass.verifyUrlLanding(Input.url + "en-us/Ingestion/Wizard", "source/overwrite page displayed", 
				"source/overwrite setting page not displayed");
		//validate fields availability
		ingestionPage.verifySourceAndOverwriteSectionFieldsAvailability();
		baseClass.passedStep("All the required fields displayed on 'source and overwrite'section");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 01/11/2022 TestCase Id:RPMXCON-58522
	 * Description :Verify that after Cataloging if user rollback the ingestion then draft Ingestion 
	 * with same data set with different source system should be completed successfully
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-58522",enabled = true, groups = { "regression" })
	public void verifyDifferentSourceSystemFromDraft() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-58522");
		baseClass.stepInfo("Verify add only ingestion for same dataset with different source system.");
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		if (status == false) {
			//perform add only ingestion
			ingestionPage.performAutomationAllsourcesIngestion(Input.sourceSystem,Input.DATFile1, Input.prodBeg);
			baseClass.stepInfo("perform catalogging");
			ingestionPage.ignoreErrorsAndCatlogging();
			baseClass.stepInfo("perform rollback using action dropdown menu");
			ingestionPage.rollBackIngestionUsingActionMenu();
			ingestionPage.VerifyDraftIngestionStatusAfterRollback();
			baseClass.stepInfo("perform open in wizard action from draft state");
			ingestionPage.performActionAsOpenWizardOption();
			ingestionPage.validateDetailsAfterOpeningIngestionFromDraft(Input.ingestionType, Input.AllSourcesFolder);
			baseClass.stepInfo("Change the source system and complete ingestion");
			baseClass.waitForElement(ingestionPage.getSpecifySourceSystem());
			ingestionPage.getSpecifySourceSystem().selectFromDropdown().selectByVisibleText(Input.mappedData);
			baseClass.stepInfo("click on next and preview");
			ingestionPage.clickOnNextButton();
			ingestionPage.clickOnPreviewAndRunButton();
			ingestionPage.publishAddonlyIngestion(Input.AllSourcesFolder);
			baseClass.passedStep("Ingestion completed successfully");
		}
		else {
			baseClass.failedMessage("Add  only ingestion already present in the current project");
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 01/11/2022 TestCase Id:RPMXCON-47457
	 * Description :To verify that unless mandatory fields are entered, user is not allowed to go to Mapping Page 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47457",enabled = true, groups = { "regression" })
	public void verifyNavigatingToMappingPageWithBlankFields() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47457");
		baseClass.stepInfo("Verify user is not allowed to go to Mapping Page unless mandatory fields are entered.");
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.ClickOnAddNewIngestionLink();
		//enter all fields and verify
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
				Input.sourceLocation, Input.GD994NativeTextForProductionFolder);
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		baseClass.stepInfo("Selecting Dat file");
		ingestionPage.selectDATSource(Input.datLoadFile4, Input.documentKey);
		baseClass.stepInfo("Select date format");
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		baseClass.stepInfo("click on next button");
		ingestionPage.clickOnNextButton();
		//verify status after clicking next button
		ingestionPage.verifySourceSectionStatusAfterClickingNextButton();
		ingestionPage.verifyAutoPopulatedSourceFieldInMappingSection();
		baseClass.passedStep("Source field gets auto populated as per the fields available in the DAT file.");
		//keep all fields blank and verify status
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Without entering mandatory fields click next button");
		baseClass.waitForElement(ingestionPage.getIngestion_IngestionType());
		ingestionPage.getIngestion_IngestionType().selectFromDropdown().selectByVisibleText(Input.ingestionType);
		baseClass.waitForElement(ingestionPage.getNextButton());
		ingestionPage.getNextButton().waitAndClick(10);
		baseClass.stepInfo("verify mandatory fields warning messages");
		ingestionPage.validateMandatoryFieldMessagesDisplayed();
		baseClass.passedStep("User is not allowed to go to mapping page section");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 01/11/2022 TestCase Id:RPMXCON-60813
	 * Description :Verify that if DAT file contain the Absolute path then Ingestion 
	 * should be completed successfully  
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-60813",enabled = true, groups = { "regression" })
	public void verifyAbsolutePathInDat() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-60813");
		baseClass.stepInfo("verify the ingestion status if DAT file contain the Absolute path.");
		String ingestionName = null;
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion with transcript");
		boolean status = ingestionPage.verifyIngestionpublish(Input.uncPath);
		if (status == false) {
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
					Input.sourceLocation, Input.uncPathFolder);
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
			baseClass.stepInfo("Selecting Dat file");
			ingestionPage.selectDATSource(Input.uncAbsoluteDat, Input.documentKey);
			baseClass.stepInfo("is path in dat for native");
			ingestionPage.isPathInDatForNativeFile(Input.nativePathField);
			baseClass.stepInfo("is path in dat for pdf");
			ingestionPage.isPathInDatForPdfFile(Input.pdfPathField);
			baseClass.stepInfo("is path in dat for tiff");
			ingestionPage.isPathInDatForTiffFile(Input.tiffPathField);
			baseClass.stepInfo("is path in dat for mp3");
			ingestionPage.isPathInDatForMp3File(Input.mp3PathField);
			baseClass.stepInfo("is path in dat for transcript");
			ingestionPage.isPathInDatForTranscriptFile(Input.transcriptPathField);
			baseClass.stepInfo("Select date format");
			ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
			baseClass.stepInfo("click on next button");
			ingestionPage.clickOnNextButton();
			ingestionPage.selectValueFromEnabledFirstThreeSourceDATFields(Input.documentKey, 
					Input.documentKey, Input.documentKeyCName);
			ingestionPage.clickOnPreviewAndRunButton();
			baseClass.stepInfo("Publish add only ingestion");
			ingestionName=ingestionPage.publishAddonlyIngestion(Input.uncPathFolder);
		}
		else {
			ingestionName = ingestionPage.getPublishedIngestionName(Input.uncPath);
		}
		baseClass.passedStep("Ingestion Name :"+ingestionName);
		baseClass.stepInfo("go to doc explorer");
		docExplorer = new DocExplorerPage(driver);
		baseClass.verifyUrlLanding(Input.url + "DocExplorer/Explorer", "navigated to docexplorer page", 
				"not on docexplorer page");
		//verify selecting docs and navigate to docview
		docExplorer.docExpToDocViewWithIngestion(ingestionName,"no");
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 04/11/2022 TestCase Id:RPMXCON-47411
	 * Description :To verify that pagination is provide to Error details pop 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47411",enabled = true, groups = { "regression" })
	public void verifyPaginationOptionInErrorDetailPopup() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47411");
		baseClass.stepInfo("To verify that pagination is provide to Error details pop");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
				Input.sourceLocation, Input.HiddenPropertiesFolder);
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		baseClass.stepInfo("Selecting Dat file");
		ingestionPage.selectDATSource(Input.DAT_MMDDYYYY, Input.sourceDocIdSearch);
		baseClass.stepInfo("Select date format");
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		baseClass.stepInfo("click on next button");
		ingestionPage.clickOnNextButton();
		ingestionPage.clickOnPreviewAndRunButton();
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		//verify pagination
		baseClass.waitForElement(ingestionPage.getIngestionDetailPopup(1));
		ingestionPage.getIngestionDetailPopup(1).waitAndClick(10);
		baseClass.waitForElement(ingestionPage.errorCountCatalogingStage());
		ingestionPage.errorCountCatalogingStage().waitAndClick(10);
		baseClass.waitForElement(ingestionPage.getErrorPagination());
		if(ingestionPage.getErrorPagination().isElementAvailable(10)) {
			baseClass.passedStep("Error popup displayed with pagination option");
		}
		else {
			baseClass.failedStep("pagination option not provided in error popup");
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 04/11/2022 TestCase Id:RPMXCON-60821
	 * Description :Verify that if LST file contain the Absolute path then Ingestion Overlay 
	 * should be completed successfully  
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-60821",enabled = true, groups = { "regression" })
	public void verifyAbsolutePathIngestionOverlay() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-60821");
		baseClass.stepInfo("verify the ingestion status if DAT file contain the Absolute path.");
		String ingestionName = null;
		docExplorer = new DocExplorerPage(driver);
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion");
		boolean status = ingestionPage.verifyIngestionpublish(Input.uncPath);
		if (status == false) {
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
					Input.sourceLocation, Input.uncPathFolder);
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
			baseClass.stepInfo("Selecting Dat file");
			ingestionPage.selectDATSource(Input.uncAbsoluteDat, Input.documentKey);
			baseClass.stepInfo("select lst only for native");
			ingestionPage.selectNativeSource(Input.uncAbsoluteNative, false);
			ingestionPage.unCheckLoadFile(ingestionPage.getTIFFLST(), ingestionPage.getTIFFCheckBox());
			baseClass.stepInfo("Select date format");
			ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
			baseClass.stepInfo("click on next button");
			ingestionPage.clickOnNextButton();
			ingestionPage.selectValueFromEnabledFirstThreeSourceDATFields(Input.documentKey, 
					Input.documentKey, Input.documentKeyCName);
			ingestionPage.clickOnPreviewAndRunButton();
			baseClass.stepInfo("Publish add only ingestion");
			ingestionName=ingestionPage.publishAddonlyIngestion(Input.uncPathFolder);
		}
		else {
			ingestionName = ingestionPage.getPublishedIngestionName(Input.uncPath);
		}
		baseClass.passedStep("Add only Ingestion Name :"+ingestionName);
		baseClass.stepInfo("go to doc explorer and verify ");
		docExplorer.navigateToDocExplorerPage();
		docExplorer.docExpToDocViewWithIngestion(ingestionName,"no");
		//perform overlay ingestion and verify
		baseClass.stepInfo("Perform overlay ingestion");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.overlayOnly, Input.sourceSystem,
				Input.sourceLocation, Input.uncPathFolder);
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		baseClass.stepInfo("Selecting Dat file");
		ingestionPage.selectDATSource(Input.uncAbsoluteDat, Input.documentKey);
		baseClass.stepInfo("select lst only for native");
		ingestionPage.selectNativeSource(Input.uncAbsoluteNative, false);
		ingestionPage.unCheckLoadFile(ingestionPage.getTIFFLST(), ingestionPage.getTIFFCheckBox());
		baseClass.stepInfo("Select date format");
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		baseClass.stepInfo("click on next button");
		ingestionPage.clickOnNextButton();
		ingestionPage.clickOnPreviewAndRunButton();
		ingestionName=ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();
		baseClass.passedStep("Overlay Ingestion Name :"+ingestionName);
		baseClass.stepInfo("go to doc explorer");
		docExplorer.navigateToDocExplorerPage();
		//verify selecting docs and navigate to docview
		docExplorer.docExpToDocViewWithIngestion(ingestionName,"no");
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 07/11/2022 TestCase Id:RPMXCON-60826
	 * Description :Verify if Ingestion is completed with Absolute path in DAT then Ingestion Overlay 
	 * should completed with DAT having Relative path 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-60826",enabled = true, groups = { "regression" })
	public void verifyAbsoluteRelativePathIngestionOverlay() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-60826");
		baseClass.stepInfo("verify the ingestion status of overlay with Dat relative path");
		String ingestionName = null;
		docExplorer = new DocExplorerPage(driver);
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion");
		boolean status = ingestionPage.verifyIngestionpublish(Input.uncPath);
		if (status == false) {
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
					Input.sourceLocation, Input.uncPathFolder);
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
			baseClass.stepInfo("Selecting Dat file");
			ingestionPage.selectDATSource(Input.uncAbsoluteDat, Input.documentKey);
			baseClass.stepInfo("select 'is path in dat' for native");
			ingestionPage.isPathInDatForNativeFile(Input.nativePathField);
			ingestionPage.unCheckLoadFile(ingestionPage.getTIFFLST(), ingestionPage.getTIFFCheckBox());
			baseClass.stepInfo("Select date format");
			ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
			baseClass.stepInfo("click on next button");
			ingestionPage.clickOnNextButton();
			ingestionPage.selectValueFromEnabledFirstThreeSourceDATFields(Input.documentKey, 
					Input.documentKey, Input.documentKeyCName);
			ingestionPage.clickOnPreviewAndRunButton();
			baseClass.stepInfo("Publish add only ingestion");
			ingestionName=ingestionPage.publishAddonlyIngestion(Input.uncPathFolder);
		}
		else {
			ingestionName = ingestionPage.getPublishedIngestionName(Input.uncPath);
		}
		baseClass.passedStep("Add only Ingestion Name :"+ingestionName);
		baseClass.stepInfo("go to doc explorer and verify ");
		docExplorer.navigateToDocExplorerPage();
		docExplorer.docExpToDocViewWithIngestion(ingestionName,"no");
		//perform overlay ingestion and verify
		baseClass.stepInfo("Perform overlay ingestion");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.overlayOnly, Input.sourceSystem,
				Input.sourceLocation, Input.uncPathFolder);
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		baseClass.stepInfo("Selecting Dat file");
		ingestionPage.selectDATSource(Input.relativeOverlayDat, Input.documentKey);
		baseClass.stepInfo("select 'is path in dat' for native");
		ingestionPage.isPathInDatForNativeFile(Input.nativePathField);
		ingestionPage.unCheckLoadFile(ingestionPage.getTIFFLST(), ingestionPage.getTIFFCheckBox());
		baseClass.stepInfo("Select date format");
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		baseClass.stepInfo("click on next button");
		ingestionPage.clickOnNextButton();
		ingestionPage.clickOnPreviewAndRunButton();
		ingestionName=ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();
		baseClass.passedStep("Overlay Ingestion Name :"+ingestionName);
		baseClass.stepInfo("go to doc explorer");
		docExplorer.navigateToDocExplorerPage();
		//verify selecting docs and navigate to docview
		docExplorer.docExpToDocViewWithIngestion(ingestionName,"no");
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 07/11/2022 TestCase Id:RPMXCON-60825
	 * Description :Verify if Ingestion is completed with Relative path in DAT then Ingestion Overlay 
	 * should completed with DAT having absolute path
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-60825",enabled = true, groups = { "regression" })
	public void verifyRelativeAbsolutePathIngestionOverlay() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-60825");
		baseClass.stepInfo("verify the ingestion status of overlay with Dat absolute path");
		String ingestionName = null;
		docExplorer = new DocExplorerPage(driver);
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion");
		boolean status = ingestionPage.verifyIngestionpublish(Input.uncPath);
		if (status == false) {
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
					Input.sourceLocation, Input.uncPathFolder);
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
			baseClass.stepInfo("Selecting Dat file");
			ingestionPage.selectDATSource(Input.uncRelativeDat, Input.documentKey);
			baseClass.stepInfo("select 'is path in dat' for native");
			ingestionPage.isPathInDatForNativeFile(Input.nativePathField);
			ingestionPage.unCheckLoadFile(ingestionPage.getTIFFLST(), ingestionPage.getTIFFCheckBox());
			baseClass.stepInfo("Select date format");
			ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
			baseClass.stepInfo("click on next button");
			ingestionPage.clickOnNextButton();
			ingestionPage.selectValueFromEnabledFirstThreeSourceDATFields(Input.documentKey, 
					Input.documentKey, Input.documentKeyCName);
			ingestionPage.clickOnPreviewAndRunButton();
			baseClass.stepInfo("Publish add only ingestion");
			ingestionName=ingestionPage.publishAddonlyIngestion(Input.uncPathFolder);
		}
		else {
			ingestionName = ingestionPage.getPublishedIngestionName(Input.uncPath);
		}
		baseClass.passedStep("Add only Ingestion Name :"+ingestionName);
		baseClass.stepInfo("go to doc explorer and verify ");
		docExplorer.navigateToDocExplorerPage();
		docExplorer.docExpToDocViewWithIngestion(ingestionName,"no");
		//perform overlay ingestion and verify
		baseClass.stepInfo("Perform overlay ingestion");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.overlayOnly, Input.sourceSystem,
				Input.sourceLocation, Input.uncPathFolder);
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		baseClass.stepInfo("Selecting Dat file");
		ingestionPage.selectDATSource(Input.absoluteOverlayDat, Input.documentKey);
		baseClass.stepInfo("select 'is path in dat' for native");
		ingestionPage.isPathInDatForNativeFile(Input.nativePathField);
		ingestionPage.unCheckLoadFile(ingestionPage.getTIFFLST(), ingestionPage.getTIFFCheckBox());
		baseClass.stepInfo("Select date format");
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		baseClass.stepInfo("click on next button");
		ingestionPage.clickOnNextButton();
		ingestionPage.clickOnPreviewAndRunButton();
		ingestionName=ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();
		baseClass.passedStep("Overlay Ingestion Name :"+ingestionName);
		baseClass.stepInfo("go to doc explorer");
		docExplorer.navigateToDocExplorerPage();
		//verify selecting docs and navigate to docview
		docExplorer.docExpToDocViewWithIngestion(ingestionName,"no");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 08/11/2022 TestCase Id:RPMXCON-47390
	 * Description :To verify that on Ingestion Home page displays default 10 tiles.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47390",enabled = true, groups = { "regression" })
	public void verifyIngestionHomePageTiles() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47390");
		baseClass.stepInfo("verify that on Ingestion Home page displays default 10 tiles");
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Select all filters");
		ingestionPage.applyFilters();
		//verify default tiles count
		int tilesCount = ingestionPage.getIngestionTilesCount().size();
		baseClass.passedStep("Number of tiles present for all filters selection: "+tilesCount);
		if(tilesCount<=10) {
			baseClass.passedStep("ingestion home page displays default limit of 10 tiles");
		}
		else {
			baseClass.failedStep("Ingestion home page not displayed default 10 tiles");
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 09/11/2022 TestCase Id:RPMXCON-48078
	 * Description :To verify Tally report should be generated with Metadata 'ExceptionResolution'
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48078",enabled = true, groups = { "regression" })
	public void verifyTallyReportForExceptionResolution() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48078");
		baseClass.stepInfo("verify Tally report should be generated with Metadata 'ExceptionResolution'");
		String metadata ="ExceptionResolution";
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Navigate to report-tally");
		tally = new TallyPage(driver);
		tally.navigateTo_Tallypage();
		baseClass.stepInfo("verify tally report generation for metadata"+metadata);
		tally.verifyTallyReportGenerationForMetadata(metadata,"project");
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 09/11/2022 TestCase Id:RPMXCON-48081
	 * Description :To Verify AllCustodians in Tally and Search
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48081",enabled = true, groups = { "regression" })
	public void verifyTallyReportForAllCustodians() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48081");
		baseClass.stepInfo("To Verify AllCustodians in Tally and Search");
		String metadata ="AllCustodians";
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Navigate to report-tally");
		tally = new TallyPage(driver);
		tally.navigateTo_Tallypage();
		baseClass.stepInfo("verify tally report generation for metadata"+metadata);
		tally.verifyTallyReportGenerationForMetadata(metadata,"project");
		baseClass.stepInfo("perform tally and search for 'AllCustodians' metadata");
		tally.performTallyAndSearchForAllCustodians();
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 10/11/2022 TestCase Id:RPMXCON-60822
	 * Description :Verify that Ingestion Overlay with text should be completed successfully 
	 * if the text LST file contains the Absolute path.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-60822",enabled = true, groups = { "regression" })
	public void verifyAbsolutePathTextFileIngestionOverlay() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-60822");
		baseClass.stepInfo("verify the ingestion status of overlay with text file contain absolute path");
		String ingestionName = null;
		String searchName = "search"+ Utility.dynamicNameAppender();
		docExplorer = new DocExplorerPage(driver);
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion");
		boolean status = ingestionPage.verifyIngestionpublish(Input.uncPath);
		if (status == false) {
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
					Input.sourceLocation, Input.uncPathFolder);
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
			baseClass.stepInfo("Selecting Dat file");
			ingestionPage.selectDATSource(Input.uncAbsoluteDat, Input.documentKey);
			baseClass.stepInfo("select Lst for native");
			ingestionPage.selectNativeSource(Input.uncAbsoluteNative, false);
			baseClass.stepInfo("select Lst for text");
			ingestionPage.selectTextSource(Input.uncAbsoluteText, false);
			ingestionPage.unCheckLoadFile(ingestionPage.getTIFFLST(), ingestionPage.getTIFFCheckBox());
			baseClass.stepInfo("Select date format");
			ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
			baseClass.stepInfo("click on next button");
			ingestionPage.clickOnNextButton();
			ingestionPage.selectValueFromEnabledFirstThreeSourceDATFields(Input.documentKey, 
					Input.documentKey, Input.documentKeyCName);
			ingestionPage.clickOnPreviewAndRunButton();
			baseClass.stepInfo("Publish add only ingestion");
			ingestionName=ingestionPage.publishAddonlyIngestion(Input.uncPathFolder);
		}
		else {
			ingestionName = ingestionPage.getPublishedIngestionName(Input.uncPath);
		}
		//go to docexplorer and view in docview
		baseClass.passedStep("Add only Ingestion Name :"+ingestionName);
		baseClass.stepInfo("go to doc explorer and verify ");
		docExplorer.navigateToDocExplorerPage();
		docExplorer.docExpToDocViewWithIngestion(ingestionName,"no");
		//unpublish docs
		baseClass.stepInfo("search and unpublish text files");
		sessionSearch.MetaDataSearchInBasicSearch(Input.metadataIngestion,ingestionName);
		sessionSearch.saveSearch(searchName);
		ingestionPage.unpublish(searchName);
		//perform overlay ingestion and verify
		baseClass.stepInfo("Perform overlay ingestion");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.overlayOnly, Input.sourceSystem,
				Input.sourceLocation, Input.uncPathFolder);
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		baseClass.stepInfo("Selecting Dat file");
		ingestionPage.selectDATSource(Input.absoluteOverlayDat, Input.documentKey);
		baseClass.stepInfo("select lst only for text");
		ingestionPage.selectTextSource(Input.absoluteOverlayText, false);
		ingestionPage.unCheckLoadFile(ingestionPage.getTIFFLST(), ingestionPage.getTIFFCheckBox());
		baseClass.stepInfo("Select date format");
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		baseClass.stepInfo("click on next button");
		ingestionPage.clickOnNextButton();
		ingestionPage.clickOnPreviewAndRunButton();
		ingestionName=ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();
		baseClass.passedStep("Overlay Ingestion Name :"+ingestionName);
		baseClass.stepInfo("go to doc explorer");
		docExplorer.navigateToDocExplorerPage();
		//verify selecting docs and navigate to docview
		docExplorer.docExpToDocViewWithIngestion(ingestionName,"no");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 10/11/2022 TestCase Id:RPMXCON-48082
	 * Description :To Verify ReviewExportID in Tally and Search.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48082",enabled = true, groups = { "regression" })
	public void verifyTallyReportForReviewExportID() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48082");
		baseClass.stepInfo("To Verify ReviewExportID in Tally and Search.");
		String metadata ="ReviewExportID";
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Navigate to report-tally");
		tally = new TallyPage(driver);
		tally.navigateTo_Tallypage();
		baseClass.stepInfo("verify tally report generation and search for metadata"+metadata);
		tally.verifyTallyReportGenerationForMetadata(metadata,"project");
		tally.performTallyAndSearchForMetadata(metadata);
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 15/11/2022 TestCase Id:RPMXCON-48270
	 * Description :To Verify NUIX created DATA, Ingestion should not failed In Approve for 
	 * DAT Delimiters "New Line" ASCII 174
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48270",enabled = true, groups = { "regression" })
	public void verifyNuixCreatedDataIngestion() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48270");
		baseClass.stepInfo("verify that Ingestion should not failed In Approve stage");
		String ingestionName = null;
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion details");
		boolean status = ingestionPage.verifyIngestionpublish(Input.H13696smallSetFolder);
		if (status == false) {
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.nuix,
					Input.sourceLocation, Input.H13696smallSetFolder);
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
			baseClass.stepInfo("Selecting Dat file");
			ingestionPage.selectDATSource(Input.smallSetDat, Input.sourceDocIdSearch);
			baseClass.stepInfo("Select date format");
			ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
			baseClass.stepInfo("click on next button");
			ingestionPage.clickOnNextButton();
			ingestionPage.clickOnPreviewAndRunButton();
			ingestionName=ingestionPage.verifyApprovedStatusForOverlayIngestion();
			ingestionPage.runFullAnalysisAndPublish();
			baseClass.stepInfo("Nuix data Ingestion Name :"+ingestionName);
			baseClass.passedStep("Ingestion not failed in any stage and published successfully");
		}
		else {
			ingestionName = ingestionPage.getPublishedIngestionName(Input.H13696smallSetFolder);
			baseClass.failedMessage("Ingestion already present in the project-"+ingestionName);
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 15/11/2022 TestCase Id:RPMXCON-54550
	 * Description :To verify that if docs have 'Require PDF Generation' field is blank then it will 
	 * not be considered for OCRing and searchable PDF creation.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-54550",enabled = true, groups = { "regression" })
	public void verifySearchablePdfCountInCopyingStage() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-54550");
		baseClass.stepInfo("verify that if docs have 'Require PDF Generation' field is blank");
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion details");
		boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
		if (status == false) {
			ingestionPage.performGD_994NativeFolderIngestion(Input.iceSourceSystem, Input.datLoadFile2,
					Input.nativeLoadFile2, Input.textLoadFile2);
			ingestionPage.verifyApprovedStatusForOverlayIngestion();
			baseClass.waitForElement(ingestionPage.getIngestionDetailPopup(1));
			ingestionPage.getIngestionDetailPopup(1).waitAndClick(10);
			ingestionPage.getSearchablePdfValueFromCopySection(Input.generateSearchablePDF, "false");
			ingestionPage.runFullAnalysisAndPublish();
		}
		else {
			ingestionPage.getIngestionDetailFromGrid(Input.GD994NativeTextForProductionFolder);
			ingestionPage.getSearchablePdfValueFromCopySection(Input.generateSearchablePDF, "false");
			baseClass.passedStep("searchable pdf not considered when field is blank");
		}
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 15/11/2022 TestCase Id:RPMXCON-54549
	 * Description :To verify that if docs have 'Require PDF Generation' field set to FALSE will not 
	 * be considered for OCRing and searchable PDF creation.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-54549",enabled = true, groups = { "regression" })
	public void verifyRequiredPdfGenerationWithFalse() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-54549");
		baseClass.stepInfo("verify that if docs have 'Require PDF Generation' field set to FALSE");
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion details");
		boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
		if (status == false) {
		ingestionPage.performGD_994NativeFolderIngestion(Input.iceSourceSystem, Input.datLoadFile2,
				Input.nativeLoadFile2, Input.textLoadFile2);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		baseClass.waitForElement(ingestionPage.getIngestionDetailPopup(1));
		ingestionPage.getIngestionDetailPopup(1).waitAndClick(10);
		ingestionPage.getSearchablePdfValueFromCopySection(Input.generateSearchablePDF, "false");
		ingestionPage.runFullAnalysisAndPublish();
		}
		else {
			//if add-only ingestion already present ,will get the data from ingestion grid
			ingestionPage.getIngestionDetailFromGrid(Input.GD994NativeTextForProductionFolder);
			ingestionPage.getSearchablePdfValueFromCopySection(Input.generateSearchablePDF, "false");
			baseClass.passedStep("searchable pdf not considered when field is false");
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 18/11/2022 TestCase Id:RPMXCON-48633
	 * Description :To verify that after text overlay PA can rerun the analytics at security group successfully
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48633",enabled = true, groups = { "regression" })
	public void verifyRerunAnalyticsAtSecurityGroup() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-48633");
		baseClass.stepInfo("verify that after text overlay PA can rerun the analytics at security group successfully");
		String ingestionName = null;
		String BasicSearchName = "Search"+ Utility.dynamicNameAppender();

		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("perform add only ingestion with text files");
		boolean status = ingestionPage.verifyIngestionpublish(Input.UniCodeFilesFolder);
		if (status == false) {
			ingestionPage.unicodeFilesIngestion(Input.datLoadFile1, Input.textFile1, Input.documentKey);
			ingestionName=ingestionPage.publishAddonlyIngestion(Input.UniCodeFilesFolder);
		}
		else {
			ingestionName= ingestionPage.getPublishedIngestionName(Input.UniCodeFilesFolder);
		}
		baseClass.stepInfo("Release docs to security group");
		sessionSearch.MetaDataSearchInBasicSearch(Input.metadataIngestion,ingestionName);
		sessionSearch.saveSearch(BasicSearchName);
		sessionSearch.bulkRelease(Input.securityGroup);
		baseClass.stepInfo("unrelease docs and unpublish the text file");
		ingestionPage.unpublish(BasicSearchName);
		baseClass.stepInfo("Perform overlay with unpublised text files");
		ingestionPage.OverlayIngestionWithDat(Input.UniCodeFilesFolder, Input.datLoadFile1,
				Input.documentKey, "text", Input.textFile1);
		String ingestionName1 =ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();
		baseClass.stepInfo("Release documents to security group");
		baseClass.selectproject();
		sessionSearch.MetaDataSearchInBasicSearch(Input.metadataIngestion,ingestionName1);
		sessionSearch.bulkRelease(Input.securityGroup);
		baseClass.stepInfo("navigate to manage-security group");
		securityGroup = new SecurityGroupsPage(driver);
		securityGroup.navigateToSecurityGropusPageURL();
		baseClass.stepInfo("Regenerate the run anaytics at SG level");
		securityGroup.regenerateAnalyticsAtSgLevel();
		loginPage.logout();			
	}
	
	/**
	 * Author :Arunkumar date: 21/11/2022 TestCase Id:RPMXCON-54551
	 * Description :To verify that if document is set to TRUE for ‘Require PDF Generation’ metadata 
	 * and if it is fails then Error count should be displayed
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-54551",enabled = true, groups = { "regression" })
	public void verifyErrorDisplayedOnCopyingStage() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-54551");
		baseClass.stepInfo("verify error if document is set to TRUE for ‘Require PDF Generation’.");
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion with ice source system");
		boolean status = ingestionPage.verifyIngestionpublish(Input.GNon_searchable_PDF_Load_file);
		if (status == false) {
			ingestionPage.performGNonSearchableFolderIngestion(Input.ingestionType, Input.iceSourceSystem,
					Input.nonSearchablePdfLoadFile, Input.selectNativeFile, Input.selectTextFile);
			ingestionPage.verifyApprovedStatusForOverlayIngestion();
			baseClass.waitForElement(ingestionPage.getIngestionDetailPopup(1));
			ingestionPage.getIngestionDetailPopup(1).waitAndClick(10);
			//verify details under copy section
			ingestionPage.verifyGenerateSearchablePdfValueInCopy(Input.generateSearchablePDF);
			ingestionPage.runFullAnalysisAndPublish();
		}
		else {
		//if add only ingestion already present,then will get the data from grid table
		ingestionPage.getIngestionDetailFromGrid(Input.GNon_searchable_PDF_Load_file);
		ingestionPage.verifyGenerateSearchablePdfValueInCopy(Input.generateSearchablePDF);
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 21/11/2022 TestCase Id:RPMXCON-54560
	 * Description :To verify that PA can Rollback the Ingestion once copy is completed.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-54560",enabled = true, groups = { "regression" })
	public void verifyRollbackOnceCopyCompleted() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-54560");
		baseClass.stepInfo("To verify that PA can Rollback the Ingestion once copy is completed");
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion");
		boolean status = ingestionPage.verifyIngestionpublish(Input.GNon_searchable_PDF_Load_file);
		if (status == false) {
		ingestionPage.performGNonSearchableFolderIngestion(Input.ingestionType, Input.sourceSystem,
				Input.nonSearchablePdfLoadFile, Input.selectNativeFile, Input.selectTextFile);
		ingestionPage.ignoreErrorsAndCatlogging();
		ingestionPage.ignoreErrorsAndCopying();
		baseClass.stepInfo("verify searchable pdf data after copy completed");
		baseClass.waitForElement(ingestionPage.getIngestionDetailPopup(1));
		ingestionPage.getIngestionDetailPopup(1).waitAndClick(10);
		driver.waitForPageToBeReady();
		ingestionPage.getSearchablePdfValueFromCopySection(Input.generateSearchablePDF, "true");
		//perform rollback once copy is completed
		ingestionPage.rollBackIngestionUsingActionMenu();
		}
		else {
			baseClass.failedMessage("unable to perform rollback as add only ingestion for this "
					+ "source folder already present in the project");
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 23/11/2022 TestCase Id:RPMXCON-60815
	 * Description :Verify that if the Absolute path is present in the PDF LST, then Ingestion 
	 * should be successful.  
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-60815",enabled = true, groups = { "regression" })
	public void verifyAbsolutePathInPdf() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-60815");
		baseClass.stepInfo("To verify that if the Absolute path is present in the PDF LST.");
		String ingestionName = null;
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion with PDF");
		boolean status = ingestionPage.verifyIngestionpublish(Input.uncPath);
		if (status == false) {
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
					Input.sourceLocation, Input.uncPathFolder);
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
			baseClass.stepInfo("Selecting Dat file");
			ingestionPage.selectDATSource(Input.uncAbsoluteDat, Input.documentKey);
			baseClass.stepInfo("Selecting Pdf file");
			ingestionPage.selectPDFSource(Input.uncAbsolutePdf, false);
			ingestionPage.unCheckLoadFile(ingestionPage.getTIFFLST(), ingestionPage.getTIFFCheckBox());
			baseClass.stepInfo("Select date format");
			ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
			baseClass.stepInfo("click on next button");
			ingestionPage.clickOnNextButton();
			ingestionPage.selectValueFromEnabledFirstThreeSourceDATFields(Input.documentKey, 
					Input.documentKey, Input.documentKeyCName);
			ingestionPage.clickOnPreviewAndRunButton();
			baseClass.stepInfo("Publish add only ingestion");
			ingestionName=ingestionPage.publishAddonlyIngestion(Input.uncPathFolder);
		}
		else {
			ingestionName = ingestionPage.getPublishedIngestionName(Input.uncPath);
		}
		baseClass.passedStep("Ingestion Name :"+ingestionName);
		baseClass.stepInfo("go to doc explorer");
		docExplorer = new DocExplorerPage(driver);
		docExplorer.navigateToDocExplorerPage();
		baseClass.verifyUrlLanding(Input.url + "DocExplorer/Explorer", "navigated to docexplorer page", 
				"not on docexplorer page");
		//verify selecting docs and navigate to docview
		docExplorer.docExpToDocViewWithIngestion(ingestionName,"no");
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 23/11/2022 TestCase Id:RPMXCON-60814
	 * Description :Verify that if the Absolute path is present in the Native LST, then Ingestion
	 * should be successful.  
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-60814",enabled = true, groups = { "regression" })
	public void verifyAbsolutePathInNative() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-60814");
		baseClass.stepInfo("To verify that if the Absolute path is present in the Native LST.");
		String ingestionName = null;
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion with Native");
		boolean status = ingestionPage.verifyIngestionpublish(Input.uncPath);
		if (status == false) {
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
					Input.sourceLocation, Input.uncPathFolder);
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
			baseClass.stepInfo("Selecting Dat file");
			ingestionPage.selectDATSource(Input.uncAbsoluteDat, Input.documentKey);
			baseClass.stepInfo("Selecting Native file");
			ingestionPage.selectNativeSource(Input.uncAbsoluteNative, false);
			ingestionPage.unCheckLoadFile(ingestionPage.getTIFFLST(), ingestionPage.getTIFFCheckBox());
			baseClass.stepInfo("Select date format");
			ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
			baseClass.stepInfo("click on next button");
			ingestionPage.clickOnNextButton();
			ingestionPage.selectValueFromEnabledFirstThreeSourceDATFields(Input.documentKey, 
					Input.documentKey, Input.documentKeyCName);
			ingestionPage.clickOnPreviewAndRunButton();
			baseClass.stepInfo("Publish add only ingestion");
			ingestionName=ingestionPage.publishAddonlyIngestion(Input.uncPathFolder);
		}
		else {
			ingestionName = ingestionPage.getPublishedIngestionName(Input.uncPath);
		}
		baseClass.passedStep("Ingestion Name :"+ingestionName);
		baseClass.stepInfo("go to doc explorer");
		docExplorer = new DocExplorerPage(driver);
		docExplorer.navigateToDocExplorerPage();
		baseClass.verifyUrlLanding(Input.url + "DocExplorer/Explorer", "navigated to docexplorer page", 
				"not on docexplorer page");
		//verify selecting docs and navigate to docview
		docExplorer.docExpToDocViewWithIngestion(ingestionName,"no");
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 23/11/2022 TestCase Id:RPMXCON-60820
	 * Description :Verify that Ingestion should be successful if the PDF LST contains the Absolute 
	 * path and the Native LST contains the Relative path. 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-60820",enabled = true, groups = { "regression" })
	public void verifyAbsoluteAndRelativePathFile() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-60820");
		baseClass.stepInfo("To verify that if the Absolute path in Native and Relative in Native LST.");
		String ingestionName = null;
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion with Pdf and Native");
		boolean status = ingestionPage.verifyIngestionpublish(Input.uncPath);
		if (status == false) {
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
					Input.sourceLocation, Input.uncPathFolder);
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
			baseClass.stepInfo("Selecting Dat file");
			ingestionPage.selectDATSource(Input.uncAbsoluteDat, Input.documentKey);
			baseClass.stepInfo("Selecting relative Native file");
			ingestionPage.selectNativeSource(Input.uncRelativeNative, false);
			baseClass.stepInfo("Selecting absolute Pdf file");
			ingestionPage.selectPDFSource(Input.uncAbsolutePdf, false);
			ingestionPage.unCheckLoadFile(ingestionPage.getTIFFLST(), ingestionPage.getTIFFCheckBox());
			baseClass.stepInfo("Select date format");
			ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
			baseClass.stepInfo("click on next button");
			ingestionPage.clickOnNextButton();
			ingestionPage.selectValueFromEnabledFirstThreeSourceDATFields(Input.documentKey, 
					Input.documentKey, Input.documentKeyCName);
			ingestionPage.clickOnPreviewAndRunButton();
			baseClass.stepInfo("Publish add only ingestion");
			ingestionName=ingestionPage.publishAddonlyIngestion(Input.uncPathFolder);
		}
		else {
			ingestionName = ingestionPage.getPublishedIngestionName(Input.uncPath);
		}
		baseClass.passedStep("Ingestion Name :"+ingestionName);
		baseClass.stepInfo("go to doc explorer");
		docExplorer = new DocExplorerPage(driver);
		docExplorer.navigateToDocExplorerPage();
		baseClass.verifyUrlLanding(Input.url + "DocExplorer/Explorer", "navigated to docexplorer page", 
				"not on docexplorer page");
		//verify selecting docs and navigate to docview
		docExplorer.docExpToDocViewWithIngestion(ingestionName,"no");
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 24/11/2022 TestCase Id:RPMXCON-49553
	 * Description :Verify that Ingestion Email Metadata 'EmailAuthorNameAndAddresses' is available.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49553",enabled = true, groups = { "regression" })
	public void verifyEmailAuthorMetadataAvailability() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49553");
		baseClass.stepInfo("Verify that Ingestion Email Metadata 'EmailAuthorNameAndAddresses' is available");
		String data ="EmailAuthorNameAndAddress";
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		//add dataset details and click next
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Adding ingestion details");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
				Input.sourceLocation, Input.GD994NativeTextForProductionFolder);
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		baseClass.stepInfo("Selecting Dat file");
		ingestionPage.selectDATSource(Input.datLoadFile4, Input.documentKey);
		baseClass.stepInfo("Select date format");
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		baseClass.stepInfo("click on next button");
		ingestionPage.clickOnNextButton();
		driver.waitForPageToBeReady();
		//Verify destination field 'EmailAuthorNameAndAddresses' metadata
		baseClass.stepInfo("Select 'EMAIL' in field category");
		baseClass.waitForElement(ingestionPage.getMappingCategoryField(5));
		ingestionPage.getMappingCategoryField(5).selectFromDropdown().selectByVisibleText(Input.email);
		baseClass.stepInfo("verify metadata");
		ingestionPage.verifyMetadataAvailability(ingestionPage.getMappingDestinationField(5), data);
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 24/11/2022 TestCase Id:RPMXCON-47427
	 * Description :To Verify Add New Ingestion in Ingestion Wizard.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47427",enabled = true, groups = { "regression" })
	public void verifySectionAvailableInIngestionWizard() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47427");
		baseClass.stepInfo("To Verify Add New Ingestion in Ingestion Wizard.");
	
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Click on add new ingestion in home page");
		ingestionPage.ClickOnAddNewIngestionLink();
		ingestionPage.verifySourceAndMappingSectionStatus();
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 24/11/2022 TestCase Id:RPMXCON-49505
	 * Description :Verify the default value for the 'Date & Time Format' field
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49505",enabled = true, groups = { "regression" })
	public void verifyDefaultSelectionForDateFormat() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49505");
		baseClass.stepInfo("Verify the default value for the 'Date & Time Format' field");
	
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion details");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
				Input.sourceLocation, Input.GD994NativeTextForProductionFolder);
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		//verify default selection value for date-time format
		ingestionPage.verifyDateFormatDropDownValidations();
		baseClass.passedStep("No specific date format selected in date and time format field");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 24/11/2022 TestCase Id:RPMXCON-63250
	 * Description :Validate whether user do not get any error message during "Add only" ingestion 
	 * type when similar source dat field are mapped twice with different destination dat field.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-63250",enabled = true, groups = { "regression" })
	public void verifySimilarSourceFieldErrorWhenAddOnly() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-63250");
		baseClass.stepInfo("Verify saving ingestion when repeated source dat field mapping during overlay only.");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.nuix, 
				Input.sourceLocation, Input.folder61759);
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		ingestionPage.selectDATSource("AllFieldsMapping_DAT_SourceDocID.dat", Input.sourceDocIdSearch);
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		ingestionPage.clickOnNextButton();
		baseClass.stepInfo("Perform similar mapping on source field and verify");
		ingestionPage.performRepeatedMapppingOnConfiguringSection("sourceField");
		baseClass.passedStep("User able to save ingestion successfully as draft when mapping similar source field");
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 25/11/2022 TestCase Id:RPMXCON-63245
	 * Description :Validate whether all the DAT fields from DAT File selected during "Add only" ingestion 
	 * is displayed under Source DAT fields in Configure Field Mapping of Ingestion Wizard Page.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-63245",enabled = true, groups = { "regression" })
	public void verifyDatFieldsInMappingWhenAddOnly() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-63245");
		baseClass.stepInfo("Verify DAT fields in configure mapping page.");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion with type as 'add only'");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.nuix, 
				Input.sourceLocation, Input.folder61759);
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		ingestionPage.selectDATSource("AllFieldsMapping_DAT_ProdBeg.dat", Input.prodBeg);
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		ingestionPage.clickOnNextButton();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("verify dat fields available in source field mapping in configuring page" );
		ingestionPage.verifyDatFieldsAvailableInMappingSection();
		baseClass.passedStep("All the DAT fields available in configure mapping page");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 25/11/2022 TestCase Id:RPMXCON-63249
	 * Description :Validate whether user gets error message during ingestion when similar Destination 
	 * field is mapped twice with different source dat field 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-63249",enabled = true, groups = { "regression" })
	public void verifySimilarDestinationFieldErrorWhenAddOnly() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-63249");
		baseClass.stepInfo("Verify error message when similar destination field mapped twice.");
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.nuix, 
				Input.sourceLocation, "RPMXCON-61759");
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		ingestionPage.selectDATSource("AllFieldsMapping_DAT_SourceDocID.dat", Input.sourceDocIdSearch);
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		ingestionPage.clickOnNextButton();
		baseClass.stepInfo("Perform similar mapping on destination field and verify");
		ingestionPage.performRepeatedMapppingOnConfiguringSection("destinationField");
		baseClass.passedStep("User gets the error message when mapping similar destination field for Add only ingestion");
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 28/11/2022 TestCase Id:RPMXCON-48169
	 * Description :Verify the type for the "AudioPlayerReady" is of Bit (values 1 or 0) and field is 
	 * Active by default, and IsSet and IsSearchable are enabled by default
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48169",enabled = true, groups = { "regression" })
	public void verifyAudioPlayerReadyField() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48169");
		baseClass.stepInfo("Verify 'AudioPlayerReady' field default status.");
		String field = "AudioPlayerReady";
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Navigate to manage-project fields");
		projectFieldPage = new ProjectFieldsPage(driver);
		projectFieldPage.navigateToProjectFieldsPage();
		baseClass.stepInfo("check the default value for 'AudioPlayerReady'");
		projectFieldPage.applyFilterByFilterName(field);
		projectFieldPage.verifyFieldDataType(field, "Bit");
		projectFieldPage.verifyExpectedFieldStatus(field,"true","true","active");
		baseClass.passedStep(field+"is tallyable and is searchable by default");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 28/11/2022 TestCase Id:RPMXCON-48930
	 * Description :Validate new metadata field DocLanguages on DocList  
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48930",enabled = true, groups = { "regression" })
	public void validateDocLanguagesFieldOnDoclist() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48930");
		baseClass.stepInfo("Validate new metadata field DocLanguages on DocList");
		docExplorer = new DocExplorerPage(driver);
		String ingestionName = null;
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		//publishing docs as pre-requisite
		baseClass.stepInfo("Perform add only ingestion");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		if (status == false) {
			ingestionPage.performAutomationAllsourcesIngestion(Input.sourceSystem,Input.DATFile1, 
					Input.prodBeg);
			baseClass.stepInfo("Publish add only ingestion");
			ingestionName=ingestionPage.publishAddonlyIngestion(Input.AllSourcesFolder);
		}
		else {
			ingestionName = ingestionPage.getPublishedIngestionName(Input.AllSourcesFolder);
		}
		baseClass.passedStep("Ingestion Name :"+ingestionName);
		sessionSearch.MetaDataSearchInBasicSearch(Input.metadataIngestion,ingestionName);
		sessionSearch.bulkRelease(Input.securityGroup);
		//login as PA and verify
		baseClass.stepInfo("Navigate to doclist and verify");
		docExplorer.navigateToDocExplorerPage();
		int docsCount=docExplorer.docExpToDocViewOrListWithIngestion(ingestionName, "yes", "doclist");
		ingestionPage.verifyDocLanguagesMetadata("doclist",docsCount);
		loginPage.logout();
		//login as RMU and verify
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Navigate to doclist and verify");
		int count =sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocList();
		ingestionPage.verifyDocLanguagesMetadata("doclist",count);
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 28/11/2022 TestCase Id:RPMXCON-48929
	 * Description :Validate new metadata field DocLanguages on DocView  
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48929",enabled = true, groups = { "regression" })
	public void validateDocLanguagesFieldOnDocview() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48929");
		baseClass.stepInfo("Validate new metadata field DocLanguages on DocView");
		docExplorer = new DocExplorerPage(driver);
		String ingestionName = null;
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		//publishing docs as pre-requisite
		baseClass.stepInfo("Perform add only ingestion");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		if (status == false) {
			ingestionPage.performAutomationAllsourcesIngestion(Input.sourceSystem,Input.DATFile1, 
					Input.prodBeg);
			baseClass.stepInfo("Publish add only ingestion");
			ingestionName=ingestionPage.publishAddonlyIngestion(Input.AllSourcesFolder);
		}
		else {
			ingestionName = ingestionPage.getPublishedIngestionName(Input.AllSourcesFolder);
		}
		baseClass.passedStep("Ingestion Name :"+ingestionName);
		sessionSearch.MetaDataSearchInBasicSearch(Input.metadataIngestion,ingestionName);
		sessionSearch.bulkRelease(Input.securityGroup);
		//login as PA and verify
		baseClass.stepInfo("Navigate to docview and verify");
		docExplorer.navigateToDocExplorerPage();
		int docsCount = docExplorer.docExpToDocViewOrListWithIngestion(ingestionName, "yes", "docview");
		ingestionPage.verifyDocLanguagesMetadata("docview",docsCount);
		loginPage.logout();
		//login as RMU and verify
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Navigate to docview and verify");
		int count =sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.ViewInDocView();
		ingestionPage.verifyDocLanguagesMetadata("docview",count);
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 28/11/2022 TestCase Id:RPMXCON-47412
	 * Description :To verify that Admin is able to browser all the Errors using navigation control
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47412",enabled = true, groups = { "regression" })
	public void verifyAdminBrowseAllErrorsUsingNavigation() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47412");
		baseClass.stepInfo("To verify that Admin is able to browser all the Errors using navigation control");
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion");
		ingestionPage.IngestionOnlyForDatFile(Input.attachDocFolder, Input.datFile7);
		ingestionPage.ignoreErrorsAndCatlogging();
		ingestionPage.verifybrowseErrorNavigationControl();
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 29/11/2022 TestCase Id:RPMXCON-48927
	 * Description :Validate new metadata field DocLanguages on Tally report  
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48927",enabled = true, groups = { "regression" })
	public void validateDocLanguagesFieldOnTally() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48927");
		baseClass.stepInfo("Validate new metadata field DocLanguages on Tally report");
		String metadata="DocLanguages";
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		//publishing docs as pre-requisite
		baseClass.stepInfo("Perform add only ingestion");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		if (status == false) {
			ingestionPage.performAutomationAllsourcesIngestion(Input.sourceSystem,Input.DATFile1, 
					Input.prodBeg);
			baseClass.stepInfo("Publish add only ingestion");
			ingestionPage.publishAddonlyIngestion(Input.AllSourcesFolder);
		}
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.bulkRelease(Input.securityGroup);
		//login as PA and verify
		baseClass.stepInfo("Navigate to report-tally");
		tally = new TallyPage(driver);
		baseClass.stepInfo("verify tally report generation for metadata"+metadata);
		tally.verifyMetaDataAvailabilityInTallyReport(metadata);
		tally.verifyTallyReportGenerationForMetadata(metadata,"project");
		loginPage.logout();
		//login as RMU and verify
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");
		baseClass.stepInfo("verify tally report generation for metadata"+metadata);
		tally.verifyMetaDataAvailabilityInTallyReport(metadata);
		tally.verifyTallyReportGenerationForMetadata(metadata,"security Group");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 29/11/2022 TestCase Id:RPMXCON-47306
	 * Description :To verify that Counts displayed on Tiles on Ingestion home page are correct.  
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47306",enabled = true, groups = { "regression" })
	public void verifyCountsDisplayedOnTiles() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47306");
		baseClass.stepInfo("verify Counts displayed on Tiles on Ingestion home page are correct.");
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("add new ingestion details");
		ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder, Input.YYYYMMDDHHMISSDat);
		ingestionPage.verifyDetailsAfterStartedIngestion();
		ingestionPage.ingestionAtCatlogState(Input.HiddenPropertiesFolder);
		baseClass.stepInfo("verify details present in ingestion tile");
		ingestionPage.verifyContentOnIngestionTiles();  
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 05/12/2022 TestCase Id:RPMXCON-47400
	 * Description :To verify Preview Records pop up display.  
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47400",enabled = true, groups = { "regression" })
	public void verifyPreviewRecordIngestionPopup() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47400");
		baseClass.stepInfo("verify Preview Records pop up display.");
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion details and click on next");
		ingestionPage.sourceSelectionAndIngestionTypeSectionOnlyWithDATfile(Input.AllSourcesFolder,Input.DATFile1);
		baseClass.stepInfo("perform mapping and verify mapping section");
		ingestionPage.verifySourceSectionStatusAfterClickingNextButton();
		ingestionPage.selectValueFromEnabledFirstThreeSourceDATFields(Input.prodBeg,Input.prodBeg,Input.custodian);
		ingestionPage.verifyAutoPopulatedSourceFieldInMappingSection();
		baseClass.passedStep("Source field gets auto populated as per the fields available in the DAT file.");
		//preview record popup verification
		baseClass.stepInfo("verify values present in preview record popup and click on back");
		ingestionPage.verifyHeaderCountInPreviewRecordPopupPage();
		baseClass.elementNotdisplayed(ingestionPage.previewRecordPopup(), "preview record popup");
		baseClass.passedStep("Preview record popup closed successfully without any errors");
		baseClass.stepInfo("Run ingestion and verify URL");
		String UrlBeforeRun = driver.getUrl();
		ingestionPage.clickOnPreviewAndRunButton();
		baseClass.waitForElement(ingestionPage.getIngestionDetailPopup(1));
		String UrlAfterRun = driver.getUrl();
		baseClass.textCompareNotEquals(UrlBeforeRun, UrlAfterRun, "URL changed after running ingestion", 
				"Url not changed after run ingestion");
		baseClass.verifyUrlLanding(Input.url + "en-us/Ingestion/Home", 
				"Navigated to Ingestion home page from wizard page successfully", "not in ingestion home page");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 05/12/2022 TestCase Id:RPMXCON-47567
	 * Description :To Verify Ingestion Status in Ingestion Detail Page .
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47567",enabled = true, groups = { "regression" })
	public void verifyIngestionStatusInDetailPage() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47567");
		baseClass.stepInfo("To Verify Ingestion Status in Ingestion Detail Page .");
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion with dat and text file");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
				Input.sourceLocation, Input.attachDocFolder);
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		baseClass.stepInfo("Selecting Dat file");
		ingestionPage.selectDATSource(Input.datFile7, Input.sourceDocIdSearch);
		baseClass.stepInfo("Selecting Text file");
		ingestionPage.selectTextSource("Text.lst", false);
		baseClass.stepInfo("Select date format");
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		baseClass.stepInfo("click on next button");
		ingestionPage.clickOnNextButton();
		ingestionPage.clickOnPreviewAndRunButton();
		baseClass.stepInfo("verify ingestion status in detail popup");
		ingestionPage.ingestionAtCatlogState(Input.attachDocFolder);
		ingestionPage.verifyFailedIngestionStatusInPopup();
		loginPage.logout();
	}
	
	@DataProvider(name = "users")
	public Object[][] Users() {
		return new Object[][] { { Input.pa1userName, Input.pa1password, "PA" },
								{ Input.rmu1userName, Input.rmu1password, "RMU" } };
	}

	/**
	 * Author :Arunkumar date: 07/12/2022 TestCase Id:RPMXCON-48926
	 * Description :Validate new metadata field DocLanguages
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48926",dataProvider = "users",enabled = true, groups = { "regression" })
	public void verifyDocLanguagesMetadata(String userName, String password, String role) throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-48926");
		baseClass.stepInfo("Validate new metadata field DocLanguages");
		String field = "DocLanguages";
		String tagName = "tag"+Utility.dynamicNameAppender();
		tally = new TallyPage(driver);
		docExplorer = new DocExplorerPage(driver);
		
		//verifying data availability
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA ");
		baseClass.selectproject(Input.additionalDataProject);
		docExplorer.navigateToDocExplorerPage();
		baseClass.waitForElement(docExplorer.getDocExp_IngestionNameFilter());
		docExplorer.getDocExp_IngestionNameFilter().waitAndClick(10);
		docExplorer.getIncludeRadioBtn().waitAndClick(10);
		docExplorer.getSearchTextArea().waitAndClick(10);
		if(docExplorer.getIngestionName("DocLanguagesExistingData").isElementAvailable(10)) {
			baseClass.passedStep("data available");
		}
		else {
			baseClass.failedStep("dataset not available");
		}
		loginPage.logout();
		
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Logged in as "+role);
		baseClass.selectproject(Input.additionalDataProject);
		baseClass.stepInfo("go to search and check field availability in metadata");
		sessionSearch.navigateToSessionSearchPageURL();
		baseClass.waitForElement(sessionSearch.getBasicSearch_MetadataBtn());
		sessionSearch.getBasicSearch_MetadataBtn().waitAndClick(10);
		baseClass.ValidateElement_Presence(sessionSearch.getMetaDataInDropdown(field), "doclanaguages metadata");
		baseClass.stepInfo("go to report-tally and check field availability in metadata");
		tally.verifyMetaDataAvailabilityInTallyReport(field);
		baseClass.stepInfo("perform tally by metadata");
		if(role.equalsIgnoreCase("PA")) {
		tally.verifyTallyReportGenerationForMetadata(field, "project");
		}
		else if(role.equalsIgnoreCase("RMU")){
			tally.verifyTallyReportGenerationForMetadata(field, "security group");
		}
		//verify purehit count with tally report for metadata
		tally.performTallyAndSearchForMetadata(field);
		baseClass.passedStep("pure hit count displayed correctly for metadata"+field);
		baseClass.stepInfo("perform tally by tags");
		docExplorer.navigateToDocExplorerPage();
		int count =docExplorer.docExpToDocViewOrListWithIngestion("DocLanguagesExistingData", "no", "doclist");
		driver.waitForPageToBeReady();
		docList = new DocListPage(driver);
		docList.selectAllDocsAndBulkTagFromDoclist(tagName);
		//docview verification
		baseClass.stepInfo("perform tally by tagname and subtally with metadata");
		tally.performTallyByTagAndSubTallyByMetadata(role,tagName,field);
		baseClass.waitForElement(tally.getTally_btnSubTallyAll());
		tally.getTally_btnSubTallyAll().waitAndClick(10);
		baseClass.stepInfo("Navigate to docview and verify doclanguages metadata");
		tally.subTallyNavigation("docview");
		ingestionPage.verifyDocLanguagesMetadata("docview",count);
		//doclist verification
		baseClass.stepInfo("perform tally by tagname and subtally with metadata");
		tally.performTallyByTagAndSubTallyByMetadata(role,tagName,field);
		baseClass.waitForElement(tally.getTally_btnSubTallyAll());
		tally.getTally_btnSubTallyAll().waitAndClick(10);
		baseClass.stepInfo("Navigate to doclist and verify doclanguages metadata");
		tally.subTallyNavigation("doclist");
		ingestionPage.verifyDocLanguagesMetadata("doclist",count);
		loginPage.logout();		
	}
	
	/**
	 * Author :Arunkumar date: 07/12/2022 TestCase Id:RPMXCON-48305
	 * Description :To verify that new ingested field "EmailConversationIndex", value will be 
	 * ingested from NUIX, should be done successfully. 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48305",enabled = true, groups = { "regression" })
	public void verifyEmailConversationIndexNuixValue() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48305");
		baseClass.stepInfo("To verify that 'EmailConversationIndex' value ingested from NUIX.");
		String ingestionName = null;
		String[] value = {"EmailConversationIndex"};
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Perform add only ingestion with PDF");
		boolean status = ingestionPage.verifyIngestionpublish("EmailConversationIndex_GD");
		if (status == false) {
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.nuix,
					Input.sourceLocation, "EmailConversationIndex_GD");
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
			baseClass.stepInfo("Selecting Dat file");
			ingestionPage.selectDATSource("GDEmailConversation_DAT.dat", Input.documentKey);
			baseClass.stepInfo("Select date format");
			ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
			baseClass.stepInfo("click on next button");
			ingestionPage.clickOnNextButton();
			ingestionPage.selectValueFromEnabledFirstThreeSourceDATFields(Input.documentKey, 
					Input.documentKey, Input.documentKeyCName);
			ingestionPage.clickOnPreviewAndRunButton();
			baseClass.stepInfo("Publish add only ingestion");
			ingestionName=ingestionPage.verifyApprovedStatusForOverlayIngestion();
			ingestionPage.runFullAnalysisAndPublish();
		}
		else {
			ingestionName = ingestionPage.getPublishedIngestionName("EmailConversationIndex_GD");
		}
		baseClass.passedStep("Ingestion Name :"+ingestionName);
		//performing search with ingestion name to filter only the docs with emailconversation index
		baseClass.stepInfo("perform search and navigate to doclist");
		int count =sessionSearch.MetaDataSearchInBasicSearch(Input.metadataIngestion,ingestionName);
		sessionSearch.ViewInDocList();
		docList = new DocListPage(driver);
		baseClass.stepInfo("select columns");
		docList.SelectColumnDisplayByRemovingAddNewValues(value);
		for(int i=1;i<=count;i++) {
			String emailIndex = docList.getDataInDoclist(1,4).getText();
			baseClass.stepInfo("Email conversation index value-"+emailIndex);
			if(emailIndex.isEmpty()) {
				baseClass.failedStep("Email conversation index value not displayed");
			}
			else {
				baseClass.passedStep("Email conversation index value displayed");
				break;
			}
		}
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 07/12/2022 TestCase Id:RPMXCON-60895
	 * Description :check user should not obtain any error in any stage of ingestion phase while 
	 * ingesting DAT file with fullpath metadata containing less than 400 chars in size 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-60895",enabled = true, groups = { "regression" })
	public void verifyDatIngestionLessThan400Chars() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-60895");
		baseClass.stepInfo("Verify ingestion Dat file data having less than 400 chars.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("perform add only ingestion with less than 400 char dat");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		if (status == false) {
			ingestionPage.IngestionOnlyForDatFile(Input.AllSourcesFolder,Input.DATFile1);
			baseClass.stepInfo("Perform catalog,copy,indexing and approve ingestion");
			ingestionPage.ignoreErrorsAndCatlogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ignoreErrorsAndIndexing(Input.AllSourcesFolder);
			ingestionPage.approveIngestion(1);
			baseClass.stepInfo("Publish ingestion");
			ingestionPage.runFullAnalysisAndPublish();
			baseClass.passedStep("Ingestion Published Successfully");
		}
		else {
			baseClass.passedStep("Ingestion already published in the project"+Input.ingestDataProject);
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 08/12/2022 TestCase Id:RPMXCON-48585
	 * Description :Verify that PA Users go to the Advance Search screen,  the Advance Search page 
	 * will show a count of unique DocIDs that are in the Project 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48585",enabled = true, groups = { "regression" })
	public void verifyUniqueDocIdsCountDisplayAsPA() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-48585");
		baseClass.stepInfo("Verify count of unique DocIDs present in Project as PA user");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		if (status == false) {
			ingestionPage.performAutomationAllsourcesIngestion(Input.sourceSystem, Input.DATFile1, Input.prodBeg);
			baseClass.stepInfo("Publish docs");
			ingestionPage.publishAddonlyIngestion(Input.AllSourcesFolder);
		}
		baseClass.stepInfo("Navigate to search>>Advanced search");
		sessionSearch.navigateToAdvancedSearchPage();
		baseClass.stepInfo("verify count of unique DocIDs present in project");
		sessionSearch.getUniqueDocCountAsDifferentUser("PA");
		loginPage.logout();
		
	}
	
	/**
	 * Author :Arunkumar date: 08/12/2022 TestCase Id:RPMXCON-48586
	 * Description :check user should not obtain any error in any stage of ingestion phase while 
	 * ingesting DAT file with fullpath metadata containing less than 400 chars in size 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48586",enabled = true, groups = { "regression" })
	public void verifyUniqueDocIdsCountDisplayAsRMU() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-48586");
		baseClass.stepInfo("Verify count of unique DocIDs present in Project as RMU user");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		if (status == false) {
			ingestionPage.performAutomationAllsourcesIngestion(Input.sourceSystem, Input.DATFile1, Input.prodBeg);
			baseClass.stepInfo("Publish docs");
			ingestionPage.publishAddonlyIngestion(Input.AllSourcesFolder);
		}
		baseClass.stepInfo("Release published docs to security group");
		sessionSearch.basicContentSearch(Input.searchStringStar);
		sessionSearch.bulkRelease(Input.securityGroup);
		loginPage.logout();
		baseClass.stepInfo("Login to the application as Rmu");
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as RMU");
		baseClass.stepInfo("navigate to search screen and verify");
		sessionSearch.navigateToSessionSearchPageURL();
		sessionSearch.getUniqueDocCountAsDifferentUser("RMU");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 10/12/2022 TestCase Id:RPMXCON-47391
	 * Description :To verify that on Ingestion Home page, on scrolling down next 10 tiles are 
	 * loaded and displayed and with sort options
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47391",enabled = true, groups = { "regression" })
	public void verifyDefault10TilesAndLoadMoreOption() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47391");
		baseClass.stepInfo("Verify default tiles count in ingestion home page");
		int tilesCount;
		int ingestionCount;
		
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		driver.waitForPageToBeReady();
		//pre-requisite -checking number of ingestion tiles is more than 10
		ingestionPage.applyFilters();
		baseClass.waitForElement(ingestionPage.getIngestionDetailPopup(1));
		baseClass.waitForElement(ingestionPage.getTotalIngestionCount());
		ingestionCount = Integer.parseInt(ingestionPage.getTotalIngestionCount().getText());
		baseClass.stepInfo("Ingestion tiles present  in home page--"+ingestionCount);
		if(ingestionCount<=10) {
			baseClass.stepInfo("need to add ingestion");
			for(int i=ingestionCount+1;i<=11;i++) {
				ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem, 
						Input.sourceLocation, Input.HiddenPropertiesFolder);
				ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);
				baseClass.stepInfo("Selecting Dat and Native file");
				ingestionPage.selectDATSource(Input.YYYYMMDDHHMISSDat, Input.sourceDocIdSearch);
				ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
				ingestionPage.clickOnNextButton();
				baseClass.waitForElement(ingestionPage.getIngestion_SaveAsDraft());
				ingestionPage.getIngestion_SaveAsDraft().waitAndClick(10);
				if(ingestionPage.getApproveMessageOKButton().isElementAvailable(10)) {
					ingestionPage.getApproveMessageOKButton().waitAndClick(5);
					}
				baseClass.VerifySuccessMessage("Your changes to the ingestion were successfully saved.");
			}
			baseClass.stepInfo("Navigate to ingestion home screen");
			ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
			ingestionPage.applyFilters();
		}
		else if(ingestionCount>10) {
			baseClass.passedStep("Ingestion home page have more than 10 tiles");
		}
		baseClass.stepInfo("scroll down and verify default tiles count");
		baseClass.waitForElement(ingestionPage.getIngestionDetailPopup(1));
		driver.scrollingToBottomofAPage();
		driver.waitForPageToBeReady();
		tilesCount = ingestionPage.getIngestionTilesCount().size();
		baseClass.stepInfo("Default tiles Count--"+tilesCount);
		baseClass.stepInfo("Click on load more button and verify ingestion tiles count");
		ingestionPage.clickOnLoadMoreAndRefresh();
		tilesCount = ingestionPage.getIngestionTilesCount().size();
		baseClass.stepInfo("Ingestion tiles count--"+tilesCount);
		ingestionCount = Integer.parseInt(ingestionPage.getTotalIngestionCount().getText());
		baseClass.stepInfo("Number of ingestion present--"+tilesCount);
		baseClass.digitCompareEquals(tilesCount, ingestionCount, 
				"All the existing ingestions displayed", "existing ingestions not displayed");
		baseClass.stepInfo("Select each option in sort by dropdown and verify default tiles count");
		ingestionPage.verifyDefaultTilesCountForDifferentSortOptions();
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 10/12/2022 TestCase Id:RPMXCON-47301
	 * Description :To Verify the reload of the ingestion with the 'Refresh' option.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47301",enabled = true, groups = { "regression" })
	public void verifyIngestionRefreshOption() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-47301");
		baseClass.stepInfo("Verify the reload of the ingestion with the 'Refresh' option.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);	
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("try to perform ingestion once");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		if (status == false) {
			ingestionPage.performAutomationAllsourcesIngestion(Input.sourceSystem, Input.DATFile1, Input.prodBeg);
			baseClass.stepInfo("Publish docs");
			ingestionPage.publishAddonlyIngestion(Input.AllSourcesFolder);
		}
		baseClass.stepInfo("after performing ingestion,select all filter");
		ingestionPage.navigateToIngestionPage();
		ingestionPage.applyFilters();
		baseClass.stepInfo("click on refresh link available in home Page");
		baseClass.waitForElement(ingestionPage.getIngestionDetailPopup(1));
		baseClass.waitForElement(ingestionPage.getRefreshButton());
		ingestionPage.getRefreshButton().waitAndClick(10);
		baseClass.stepInfo("verify count and other details after reload ingestion page");
		int tilesCount = ingestionPage.getIngestionTilesCount().size();
		baseClass.stepInfo("Ingestion tiles count--"+tilesCount);
		int ingestionCount = Integer.parseInt(ingestionPage.getTotalIngestionCount().getText());
		baseClass.stepInfo("Number of ingestion present in project--"+tilesCount);
		if((tilesCount>0) && (ingestionCount>0)){
			baseClass.passedStep("Ingestion tiles and count updated successfully.");
		}
		else {
			baseClass.failedStep("Count not updated successfully");
		}
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 10/12/2022 TestCase Id:RPMXCON-54734
	 * Description :Verify that “EmailAuthor” Column header Filter with CJK characters is working 
	 * correctly on Doc Explorer list
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-54734",enabled = true, groups = { "regression" })
	public void verifyEmailAuthorColumnHeader() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-54734");
		baseClass.stepInfo("Verify that “EmailAuthor” Column header Filter is working correctly");
		//taken the cjk char present in dat file mentioned in testdata
		String[] cjkChar = {"?","ã","ą","æ","ç","ĉ","ć","č","ð","è","é","ú",
				"ŭ","ü","ý","ż","ž","€","£","¥"};
		
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
		if (status == false) {
			ingestionPage.IngestionOnlyForDatFile(Input.GD994NativeTextForProductionFolder, 
					Input.specialCjkDat);
			baseClass.stepInfo("Publish docs");
			ingestionPage.publishAddonlyIngestion(Input.GD994NativeTextForProductionFolder);
		}
		docExplorer = new DocExplorerPage(driver);
		docExplorer.navigateToDocExplorerPage();
		baseClass.stepInfo("perform email author header filter");
		docExplorer.verifyEmailAuthorValuesInDocExp(cjkChar);
		baseClass.passedStep("Email author header filter is working correctly");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 11/12/2022 TestCase Id:RPMXCON-48075
	 * Description :To Verify SourceAttachDocIDs and AttachDocIDs  in Search.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48075",enabled = true, groups = { "regression" })
	public void verifyAttachDocIdsInSearch() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-48075");
		baseClass.stepInfo("To Verify SourceAttachDocIDs and AttachDocIDs in Search.");
		String[] field ={"AttachDocIDs"};
		String searchId=null;
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);		
		baseClass.stepInfo("Logged in as PA");
		//selecting project which have the pre-requisite data
		baseClass.selectproject(Input.additionalDataProject);
		//getting the attachdocid to perform search
		docExplorer = new DocExplorerPage(driver);
		docExplorer.navigateToDocExplorerPage();
		int count =docExplorer.docExpToDocViewOrListWithIngestion("Zip_DocView_20Family_20Threaded", "yes", "doclist");
		docList = new DocListPage(driver);
		baseClass.stepInfo("select columns");
		docList.SelectColumnDisplayByRemovingAddNewValues(field);
		driver.waitForPageToBeReady();
		String attachDocIds =docList.getDoclistMetaDataValue(count,4);
		if(attachDocIds.contains(";")) {
			String[] attachid = attachDocIds.split(";");
			searchId = attachid[0];
		}else {
			searchId =attachDocIds;
		}
		 
		baseClass.stepInfo("navigate to project field and verify");
		ProjectFieldsPage projectFieldPage = new ProjectFieldsPage(driver);
		projectFieldPage.navigateToProjectFieldsPage();
		projectFieldPage.applyFilterByFilterName(field[0]);
		projectFieldPage.verifyExpectedFieldStatus(field[0],"false","true","active");
		baseClass.passedStep(field[0]+"is searchable and not tallyable");
		baseClass.stepInfo("perform metadata search and verify purehit");
		int purehit =sessionSearch.MetaDataSearchInBasicSearch(field[0],searchId);
		baseClass.stepInfo("docs returned for the search--"+purehit);
		sessionSearch.verifySearchResultReturnsForConfiguredQuery(purehit);
		baseClass.passedStep(field[0]+"is searchable");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 11/12/2022 TestCase Id:RPMXCON-48077
	 * Description :To Verify FamilyRelationship in Tally and Search.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48077",enabled = true, groups = { "regression" })
	public void verifyFamilyRelationShipInSearch() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-48077");
		baseClass.stepInfo("To Verify FamilyRelationship in Tally and Search.");
		String metadata ="FamilyRelationship";
		String value ="Parent";
		
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);		
		baseClass.stepInfo("Logged in as PA");
		//selecting project which have the pre-requisite data
		baseClass.selectproject(Input.additionalDataProject);
		baseClass.stepInfo("Navigate to report-tally");
		tally = new TallyPage(driver);
		tally.navigateTo_Tallypage();
		baseClass.stepInfo("verify tally report generation for metadata"+metadata);
		tally.verifyTallyReportGenerationForMetadata(metadata,"project");
		List<String> values = tally.verifyTallyChart();
		HashMap<String, Integer> map = tally.getDocsCountFortallyReport();
		int tallyResult = map.get(value);
		baseClass.stepInfo("Tally report status for parent value--"+tallyResult);
		baseClass.stepInfo("perform search and verify pure hit count");
		int searchResult = sessionSearch.MetaDataSearchInBasicSearch(metadata, value);
		baseClass.stepInfo("purehit for parent value--"+searchResult);
		baseClass.digitCompareEquals(tallyResult, searchResult, 
				"pure hit count matches with the tally result", 
				"search and tally result count not matched");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 12/12/2022 TestCase Id:RPMXCON-46890
	 * Description :To Verify In Ingestions Overlay for Audio Doc with Trimmed audio duration 
	 * fields should be successfull.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-46890",enabled = true, groups = { "regression" })
	public void verifyOverlayWithTrimmedAudioDuration() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-46890");
		baseClass.stepInfo("Verify Overlay for Audio Doc with Trimmed audio duration.");
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("perform add only ingestion and publish");
		boolean status = ingestionPage.verifyIngestionpublish(Input.H13696smallSetFolder);
		if (status == false) {
			ingestionPage.performSmallSetGdIngestion(Input.ingestionType,Input.smallSetDat2);
			ingestionPage.publishAddonlyIngestion(Input.H13696smallSetFolder);
		}
		baseClass.stepInfo("perform overlay with trimmed audio duration field");
		ingestionPage.performSmallSetGdIngestion(Input.overlayOnly,Input.smallSetDat2);
		ingestionPage.publishAudioOverlayIngestion(Input.H13696smallSetFolder);
		baseClass.passedStep("Overlay ingestion performed successfully");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 13/12/2022 TestCase Id:RPMXCON-55584
	 * Description :To verify that user cannot Ingest the document beyond the limit.
	 * @throws Exception 
	 */
	@Test(description ="RPMXCON-55584",enabled = true, groups = { "regression" })
	public void verifyProjectIngestionLimit() throws Exception {
		
		baseClass.stepInfo("Test case Id: RPMXCON-55584");
		baseClass.stepInfo("verify that user cannot Ingest the document beyond the limit.");
		String projectName = "QAproject" +Utility.dynamicNameAppender();				
		
		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);		
		baseClass.stepInfo("Logged in as SA");
		project = new ProjectPage(driver);
		baseClass.stepInfo("create new project with specific limit");
		System.out.println(projectName);
		project.createIngestionProject(projectName, "Automation", "100");
		baseClass.stepInfo("provide access to PA user with ingestion functionality");
		userManage = new UserManagement(driver);
		userManage.provideAccessToPaAndEnableIngestion(Input.pa1userName, projectName);
		loginPage.logout();
		//login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.verifyIngestionAccess(projectName);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("try to ingest more number of docs and verify error message");
		ingestionPage.performAutomationAllsourcesIngestion(Input.sourceSystem, Input.DATFile1, Input.prodBeg);
		ingestionPage.ingestionAtCatlogState(Input.AllSourcesFolder);
		ingestionPage.verifyCatalogedIngestionErrorMessage("more than specified limit of documents for this project");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 13/12/2022 TestCase Id:RPMXCON-55873
	 * Description :Add new path using "Ingestion Folder" option for ingestion while creating the new project
	 * @throws Exception 
	 */
	@Test(description ="RPMXCON-55873",enabled = true, groups = { "regression" })
	public void verifyAddNewPathForIngestionFolder() throws Exception {
		
		baseClass.stepInfo("Test case Id: RPMXCON-55873");
		baseClass.stepInfo("Verify Add new path using 'Ingestion Folder' option for ingestion");
		String projectName = "QAproject" +Utility.dynamicNameAppender();
		String actualPath = "ingestion";
		String updatedPath = "Automation";
		
		// Login as SA
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("Logged in as SA");
		project = new ProjectPage(driver);
		baseClass.stepInfo("create new project with ingestion folder data");
		System.out.println(projectName);
		project.createIngestionProject(projectName, actualPath, "100");
		baseClass.stepInfo("select same project and edit ingestion folder");
		project.editIngestionFolderPath(projectName, updatedPath);
		baseClass.stepInfo("provide access to PA user with ingestion functionality");
		userManage = new UserManagement(driver);
		userManage.provideAccessToPaAndEnableIngestion(Input.pa1userName, projectName);
		loginPage.logout();
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);		
		baseClass.stepInfo("Logged in as PA");
		ingestionPage.verifyIngestionAccess(projectName);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("verify source location list for updated path");
		ingestionPage.verifyIngestionFolderPath(updatedPath);
		baseClass.passedStep("Newly added path available");
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 18/12/2022 TestCase Id:RPMXCON-49072 
	 * Description :To verify that Ingestion overlay with DAT only
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-49072", enabled = true, groups = { "regression" })
	public void verifyIngestionOverlaywithUpdatedDatOnly() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49072");
		baseClass.stepInfo("To verify that Ingestion overlay with DAT only.");
		String[] metadata = {Input.metaDataName};
		String ingestionName = null;
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo("Add new ingestion with overwrite option as 'Add only'.");
		boolean status = ingestionPage.verifyIngestionpublish(Input.Collection1KFolder);
		if (status == false) {
			ingestionPage.performCollection1kTallyIngestion(Input.sourceSystem, Input.datLoadFile3, Input.textFile1);
			ingestionName=ingestionPage.publishAddonlyIngestion(Input.Collection1KFolder);
		}
		else {
			ingestionName = ingestionPage.getPublishedIngestionName(Input.Collection1KFolder);
		}
		baseClass.stepInfo("Perform overlay ingestion with modified dat file");
		ingestionPage.OverlayIngestionForDATWithMappingFieldSection(Input.Collection1KFolder, Input.overlayDatFile,
				Input.docId);
		ingestionPage.updateMappingSection(2,Input.custodian,Input.ingDocBasic, Input.documentKeyCName);
		ingestionPage.clickOnPreviewAndRunButton();
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();
		baseClass.passedStep("Overlay ingestion performed successfully");
		baseClass.stepInfo("verify updated metadata");
		int count =sessionSearch.MetaDataSearchInBasicSearch(Input.metadataIngestion,ingestionName);
		sessionSearch.ViewInDocList();
		docList = new DocListPage(driver);
		baseClass.stepInfo("select columns");
		docList.SelectColumnDisplayByRemovingAddNewValues(metadata);
		String updatedData=docList.getDoclistMetaDataValue(count,4);
		baseClass.passedStep("updated metadata value--"+updatedData);
		baseClass.compareTextViaContains(updatedData, "OverlayCName" , 
				"updated metadata values displayed", "updated metadata values not displayed");
		loginPage.logout();

	}
	
	/**
	 * Author :Arunkumar date: 19/12/2022 TestCase Id:RPMXCON-68079 
	 * Description :Unrelease only few of the documents from user created SG and run security group 
	 * analytics still the project field should be searchable while running analytics & analytics 
	 * should complete
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-68079", enabled = true, groups = { "regression" })
	public void verifyUnreleaseFewDocsAndPerformAnalytics() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-68079");
		baseClass.stepInfo("Unrelease only few of the documents from user created SG and run security group analytics");
		String userSG = "userCreatedSG"+Utility.dynamicNameAppender();
		String ingestionName = null;
		String[] metadata = {"EmailDuplicateDocIDs","EmailInclusiveReason","EmailInclusiveScore"};
		List<String> metaValue = new ArrayList<>();
		securityGroup = new SecurityGroupsPage(driver);
		
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");	
		//Pre-Requisite
		securityGroup.navigateToSecurityGropusPageURL();
		securityGroup.AddSecurityGroup(userSG);
		baseClass.stepInfo("docs should be ingested and release to user created SG");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
		if (status == false) {
			ingestionPage.performGD_994NativeFolderIngestion(Input.sourceSystem, Input.datLoadFile2,
					Input.nativeLoadFile2, Input.textLoadFile2);
			baseClass.stepInfo("Publish docs");
			ingestionName= ingestionPage.publishAddonlyIngestion(Input.GD994NativeTextForProductionFolder);
		}
		else {
			ingestionName = ingestionPage.getPublishedIngestionName(Input.GD994NativeTextForProductionFolder);
		}
		sessionSearch.MetaDataSearchInBasicSearch(Input.metadataIngestion,ingestionName);
		sessionSearch.bulkRelease(userSG);
		//verification
		baseClass.stepInfo("unrelease few docs from user created SG");
		docExplorer = new DocExplorerPage(driver);
		docExplorer.navigateToDocExplorerPage();
		int count =docExplorer.docExpToDocViewOrListWithIngestion(ingestionName, "no", "doclist");
		driver.waitForPageToBeReady();
		docList = new DocListPage(driver);
		docList.documentSelection(1);
		docList.bulkUnRelease(userSG);
		baseClass.stepInfo("get value for project fields search");
		docList.SelectColumnDisplayByRemovingExistingOnes(metadata);
		for(int i=4;i<=6;i++) {
			String value =docList.getDoclistMetaDataValue(count, i);
			metaValue.add(value);
		}
		System.out.println(metaValue);
		baseClass.stepInfo("add SG analytics to background");
		securityGroup.navigateToSecurityGropusPageURL();
		securityGroup.startRegenerateAnalyticsAtSgLevel(userSG);
		baseClass.stepInfo("verify project fields should be searchable");
		baseClass.selectproject(Input.ingestDataProject);
		for(int j=0;j<metadata.length;j++) {
			if((!metaValue.get(j).isEmpty())){
				
				int result = sessionSearch.MetaDataSearchInBasicSearch(metadata[j],metaValue.get(j));
				sessionSearch.verifySearchResultReturnsForConfiguredQuery(result);
				baseClass.passedStep(metadata[j]+"--Project field is searchable while running analytics");
				baseClass.selectproject(Input.ingestDataProject);
			}
		}
		baseClass.stepInfo("verify analytics SG completed");
		securityGroup.verifyRegenerateAnalyticsStatus();
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 19/12/2022 TestCase Id:RPMXCON-68077 
	 * Description :Unrelease all the documents from user created SG and run security group analytics 
	 * still the project field should be searchable while running analytics
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-68077", enabled = true, groups = { "regression" })
	public void verifyUnreleaseAllDocsAndPerformAnalytics() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-68077");
		baseClass.stepInfo("Unrelease all the documents from user created SG and run security group analytics");
		String userSG = "userCreatedSG"+Utility.dynamicNameAppender();
		String ingestionName = null;
		String[] metadata = {"EmailDuplicateDocIDs","EmailInclusiveReason","EmailInclusiveScore"};
		List<String> metaValue = new ArrayList<>();
		securityGroup = new SecurityGroupsPage(driver);
		
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);	
		baseClass.stepInfo("Logged in as PA");	
		//Pre-Requisite
		baseClass.stepInfo("project field created and release to created SG");
		securityGroup.navigateToSecurityGropusPageURL();
		securityGroup.AddSecurityGroup(userSG);
		baseClass.stepInfo("docs should be ingested and release to SG");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
		if (status == false) {
			ingestionPage.performGD_994NativeFolderIngestion(Input.sourceSystem, Input.datLoadFile2,
					Input.nativeLoadFile2, Input.textLoadFile2);
			baseClass.stepInfo("Publish docs");
			ingestionName= ingestionPage.publishAddonlyIngestion(Input.GD994NativeTextForProductionFolder);
		}
		else {
			ingestionName = ingestionPage.getPublishedIngestionName(Input.GD994NativeTextForProductionFolder);
		}
		sessionSearch.MetaDataSearchInBasicSearch(Input.metadataIngestion,ingestionName);
		sessionSearch.bulkRelease(userSG);
		//verification
		baseClass.stepInfo("unrelease all docs from user created SG");
		docExplorer = new DocExplorerPage(driver);
		docExplorer.navigateToDocExplorerPage();
		int count =docExplorer.docExpToDocViewOrListWithIngestion(ingestionName, "no", "doclist");
		driver.waitForPageToBeReady();
		docList = new DocListPage(driver);
		docList.documentSelection(count);
		docList.bulkUnRelease(userSG);
		baseClass.stepInfo("get value for project fields search");
		docList.SelectColumnDisplayByRemovingExistingOnes(metadata);
		for(int i=4;i<=6;i++) {
			String value =docList.getDoclistMetaDataValue(count, i);
			metaValue.add(value);
		}
		System.out.println(metaValue);
		baseClass.stepInfo("add SG analytics to background");
		securityGroup.navigateToSecurityGropusPageURL();
		securityGroup.startRegenerateAnalyticsAtSgLevel(userSG);
		baseClass.stepInfo("verify project fields should be searchable");
		baseClass.selectproject(Input.ingestDataProject);
		for(int j=0;j<metadata.length;j++) {
			if((!metaValue.get(j).isEmpty())){
			int result = sessionSearch.MetaDataSearchInBasicSearch(metadata[j],metaValue.get(j));
			sessionSearch.verifySearchResultReturnsForConfiguredQuery(result);
			baseClass.passedStep(metadata[j]+"--Project field is searchable while running analytics");
			baseClass.selectproject(Input.ingestDataProject);
			}
		}
		baseClass.stepInfo("verify analytics SG failed");
		securityGroup.verifyRegenerateAnalyticsFailedStatus();
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 19/12/2022 TestCase Id:RPMXCON-67981 
	 * Description :create a EmailInclusiveScore or EmailDuplicateDocIDs or EmailInclusiveReason 
	 * project field should be searchable while default security group analytics is running.
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-67981", enabled = true, groups = { "regression" })
	public void verifyreleaseFewDocsAndPerformAnalytics() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-67981");
		baseClass.stepInfo("Unrelease all the documents from user created SG and run security group analytics");
		String ingestionName = null;
		securityGroup = new SecurityGroupsPage(driver);
		String[] metadata = {"EmailDuplicateDocIDs","EmailInclusiveReason","EmailInclusiveScore"};
		List<String> metaValue = new ArrayList<>();
		
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");	
		//Pre-Requisite
		baseClass.stepInfo("docs should be ingested and no docs release to SG");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
		if (status == false) {
			ingestionPage.performGD_994NativeFolderIngestion(Input.sourceSystem, Input.datLoadFile2,
					Input.nativeLoadFile2, Input.textLoadFile2);
			baseClass.stepInfo("Publish docs");
			ingestionName= ingestionPage.publishAddonlyIngestion(Input.GD994NativeTextForProductionFolder);
		}
		else {
			ingestionName = ingestionPage.getPublishedIngestionName(Input.GD994NativeTextForProductionFolder);
		}
		//verification
		baseClass.stepInfo("release few docs to default SG");
		docExplorer = new DocExplorerPage(driver);
		docExplorer.navigateToDocExplorerPage();
		int count =docExplorer.docExpToDocViewOrListWithIngestion(ingestionName, "no", "doclist");
		driver.waitForPageToBeReady();
		docList = new DocListPage(driver);
		docList.documentSelection(1);
		docList.docListToBulkRelease(Input.securityGroup);
		baseClass.stepInfo("get value for project fields search");
		docList.SelectColumnDisplayByRemovingExistingOnes(metadata);
		for(int i=4;i<=6;i++) {
			String value =docList.getDoclistMetaDataValue(count, i);
			metaValue.add(value);
		}
		System.out.println(metaValue);
		baseClass.stepInfo("add SG analytics to background");
		securityGroup.navigateToSecurityGropusPageURL();
		securityGroup.startRegenerateAnalyticsAtSgLevel(Input.securityGroup);
		baseClass.stepInfo("verify project fields should be searchable");
		for(int j=0;j<metadata.length;j++) {
			if((!metaValue.get(j).isEmpty())){
			int result = sessionSearch.MetaDataSearchInBasicSearch(metadata[j],metaValue.get(j));
			sessionSearch.verifySearchResultReturnsForConfiguredQuery(result);
			baseClass.passedStep(metadata[j]+"--Project field is searchable while running analytics");
			baseClass.selectproject(Input.ingestDataProject);
			}
		}
		baseClass.stepInfo("verify analytics SG completed");
		securityGroup.verifyRegenerateAnalyticsStatus();
		loginPage.logout();
	}
	
	/**
	 * Author :Arunkumar date: 19/12/2022 TestCase Id:RPMXCON-68078 
	 * Description :Unrelease only few of the documents from default SG and run security group 
	 * analytics still the project field should be searchable while running analytics & analytics 
	 * should complete
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-68078", enabled = true, groups = { "regression" })
	public void verifyUnreleaseFewDocsFromDefaultSGAndPerformAnalytics() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-68078");
		baseClass.stepInfo("Unrelease only few of the documents from default SG and run security group analytics");
		
		String ingestionName = null;
		String[] metadata = {"EmailDuplicateDocIDs","EmailInclusiveReason","EmailInclusiveScore"};
		List<String> metaValue = new ArrayList<>();
		securityGroup = new SecurityGroupsPage(driver);
		
		// Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");	
		//Pre-Requisite
		baseClass.stepInfo("docs should be ingested and release to SG");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
		if (status == false) {
			ingestionPage.performGD_994NativeFolderIngestion(Input.sourceSystem, Input.datLoadFile2,
					Input.nativeLoadFile2, Input.textLoadFile2);
			baseClass.stepInfo("Publish docs");
			ingestionName= ingestionPage.publishAddonlyIngestion(Input.GD994NativeTextForProductionFolder);
		}
		else {
			ingestionName = ingestionPage.getPublishedIngestionName(Input.GD994NativeTextForProductionFolder);
		}
		sessionSearch.MetaDataSearchInBasicSearch(Input.metadataIngestion,ingestionName);
		sessionSearch.bulkRelease(Input.securityGroup);
		//verification
		baseClass.stepInfo("unrelease few docs from SG");
		docExplorer = new DocExplorerPage(driver);
		docExplorer.navigateToDocExplorerPage();
		int count =docExplorer.docExpToDocViewOrListWithIngestion(ingestionName, "no", "doclist");
		driver.waitForPageToBeReady();
		docList = new DocListPage(driver);
		docList.documentSelection(1);
		docList.bulkUnRelease(Input.securityGroup);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("get value for project fields search");
		docList.SelectColumnDisplayByRemovingExistingOnes(metadata);
		for(int i=4;i<=6;i++) {
			String value =docList.getDoclistMetaDataValue(count, i);
			metaValue.add(value);
		}
		System.out.println(metaValue);
		baseClass.stepInfo("add SG analytics to background");
		securityGroup.navigateToSecurityGropusPageURL();
		securityGroup.startRegenerateAnalyticsAtSgLevel(Input.securityGroup);
		baseClass.stepInfo("verify project fields should be searchable");
		baseClass.selectproject(Input.ingestDataProject);
		for(int j=0;j<metadata.length;j++) {
			if((!metaValue.get(j).isEmpty())){
				int result = sessionSearch.MetaDataSearchInBasicSearch(metadata[j],metaValue.get(j));
				sessionSearch.verifySearchResultReturnsForConfiguredQuery(result);
				baseClass.passedStep(metadata[j]+"--Project field is searchable while running analytics");
				baseClass.selectproject(Input.ingestDataProject);
			}	
		}
		baseClass.stepInfo("verify analytics SG completed");
		securityGroup.verifyRegenerateAnalyticsStatus();
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
