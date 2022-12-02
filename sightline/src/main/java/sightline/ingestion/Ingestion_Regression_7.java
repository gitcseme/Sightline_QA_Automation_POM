package sightline.ingestion;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import pageFactory.DocViewPage;
import pageFactory.IngestionPage_Indium;
import pageFactory.LoginPage;
import pageFactory.ProjectFieldsPage;
import pageFactory.ProjectPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TallyPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Ingestion_Regression_7 {

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
	TallyPage tally;
	DocExplorerPage docExplorer;
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
	
	/**
	 * Author :Arunkumar date: 17/10/2022 TestCase Id:RPMXCON-48073
	 * Description :To Verify EmailDateSentTimeOnly in tally and in Search- Both fields should be false by default
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48073",enabled = true, groups = { "regression" })
	public void TCA1verifyEmailDateSentTimeOnlyInTallyAndSearch() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48073");
		baseClass.stepInfo("Verify EmailDateSentTimeOnly in tally and in Search.");
		String field = "EmailDateSentTimeOnly";
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
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
	public void TCB9verifyExcelProtectedWorkbookInTallyAndSearch() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48080");
		baseClass.stepInfo("Verify ExcelProtectedWorkbook in Tally and Search.");
		String field = "ExcelProtectedWorkbook";
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
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
	public void TCC1verifyFileDescriptionInTallyAndSearch() throws InterruptedException {

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
	public void TCA2verifyEmailCcMetadataAvailability() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49554");
		baseClass.stepInfo("Verify that Ingestion Email Metadata 'EmailCCNamesAndAddresses' is available");
		String data ="EmailCCNamesAndAddresses";
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
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
	public void TCA3verifyEmailBccMetadataAvailability() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49555");
		baseClass.stepInfo("Verify that Ingestion Email Metadata 'EmailBCCNamesAndAddresses' is available");
		String data ="EmailBCCNamesAndAddresses";
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
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
	public void TCA4verifyToolTipForDateFormat() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49504");
		baseClass.stepInfo("Verify that tooltip should be displayed for 'Date & Time Format' field");
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
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
	public void TCA5verifyToolTipAndDefaultSelectionForDateFormat() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49503");
		baseClass.stepInfo("Verify that different formats present in 'Date & Time Format' field");
	
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
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
	public void TCA6verifyGridAndTileviewOptions() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47363");
		baseClass.stepInfo("To verify that user is able to view two buttons of Grid and Tile view.");
	
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
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
	public void TCA7verifyIngestionCountOnHomePage() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47307");
		baseClass.stepInfo("To verify that Ingestion Count on Ingested Home page is displayed correctly");
	
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
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
	public void TCA8verifyBackButtonFunctionality() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47458");
		baseClass.stepInfo("To verify that Back button functionality on Configure Mapping page.");
	
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
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
	public void TCB8verifyAbsolutePathInTranscript() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-60818");
		baseClass.stepInfo("To verify that if the Absolute path is present in the Transcript LST.");
		String ingestionName = null;
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
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
	public void TCB7verifyAbsolutePathInTranslation() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-60819");
		baseClass.stepInfo("To verify that Absolute path is present in the Translation LST.");
		String ingestionName = null;

		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
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
	public void TCB5verifyAddOnlyForDifferentSourceSystem() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-58507");
		baseClass.stepInfo("Verify add only ingestion for same dataset with different source system.");
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
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
	public void TCB6verifyTextOverlayCountUnderIndexingSection() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48615");
		baseClass.stepInfo("Verify overlay text files count reflecting under indexing section");
		String ingestionName = null;
		String searchName = "Search"+Utility.dynamicNameAppender();
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
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
	public void TCB4verifyMandatoryFieldsInSourceAndSettingsOption() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47290");
		baseClass.stepInfo("verify the mandatory fields in source and overwrite settings option");
	
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
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
	public void TCB3verifyTextFilesSearchConsideration() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-48605");
		baseClass.stepInfo("Verify that users should be able to successfully conduct searches");
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
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
	public void TCB1verifyVariousOptionsInIngestionWizard() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47286");
		baseClass.stepInfo("verify 'Source Selection & Ingestion Type' Sections for various Options");
	
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
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
	public void TCB2verifyDifferentSourceSystemFromDraft() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-58522");
		baseClass.stepInfo("Verify add only ingestion for same dataset with different source system.");
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
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
	public void TCA9verifyNavigatingToMappingPageWithBlankFields() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-47457");
		baseClass.stepInfo("Verify user is not allowed to go to Mapping Page unless mandatory fields are entered.");
		
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
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
	public void TCC2verifyAbsolutePathInDat() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-60813");
		baseClass.stepInfo("verify the ingestion status if DAT file contain the Absolute path.");
		String ingestionName = null;
		//Login as PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
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
