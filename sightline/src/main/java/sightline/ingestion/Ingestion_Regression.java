package sightline.ingestion;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DataSets;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.IngestionPage_Indium;
import pageFactory.LoginPage;
import pageFactory.ManageAssignment;
import pageFactory.ManageUsersPage;
import pageFactory.ProjectFieldsPage;
import pageFactory.ProjectPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.TallyPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Ingestion_Regression {
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	DocViewMetaDataPage docViewMetaDataPage;
	ManageAssignment manageAssignment;
	DocViewPage docView;
	Utility utility;
	TagsAndFoldersPage tagAndFol;
	TallyPage tally;
	SecurityGroupsPage securityGroup;
	DocExplorerPage docexp;
	DataSets dataSets;
	SessionSearch sessionsearch;
	TagsAndFoldersPage tagandfolder;
	DocListPage doclist;
	DocViewPage docview;
	IngestionPage_Indium ingestionPage;
	Input ip;

	@BeforeClass(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		
		ip = new Input();
		ip.loadEnvConfig();

	
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());

		driver = new Driver();
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
	}

	/**
	 * @author Gopinath
	 * @TestCase id:50755 : Project level exported dataset details - Validate "Not
	 *           Processed and Not Loaded" tab details for a project having Uploaded
	 *           ingestions.
	 * @Description: Project level exported dataset details - Validate "Not
	 *               Processed and Not Loaded" tab details for a project having
	 *               Uploaded ingestions.
	 */
	@Test(description ="RPMXCON-50755",alwaysRun = true, groups = { "regression" } )
	public void verifyProjectLvelExportedDataSetWithExpFields() {
		baseClass = new BaseClass(driver);
		String location = "C:\\\\BatchPrintFiles\\\\downloads";
		String sheetName = "Not Processed and Not Loaded";
		String fieldName = "Dataset Name";
		String fieldName2 = "File Name";
		String fieldName3 = "Exclusion Reason";
		baseClass.stepInfo("Test case Id: RPMXCON-50755 Sprint 12");
		baseClass.stepInfo(
				"### Project level exported dataset details - Validate 'Not Processed and Not Loaded' tab details for a project having Uploaded ingestions ###");
		dataSets = new DataSets(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
		baseClass.selectproject(Input.projectName02);

		baseClass.stepInfo("Navigate data sets page.");
		dataSets.navigateToDataSetsPage();

		baseClass.stepInfo("Perform export data sets.");
		dataSets.exportDataSets();

		baseClass.stepInfo("Download exported file.");
		dataSets.downloadExportedFile();

		baseClass.stepInfo("Verify '" + fieldName + "' Field Displayed Of Downlodeded Excel File.");
		dataSets.verifyFieldDisplayedOfDownlodedExcelFile(location, sheetName, fieldName);

		baseClass.stepInfo("Verify '" + fieldName2 + "' Field Displayed Of Downlodeded Excel File.");
		dataSets.verifyFieldDisplayedOfDownlodedExcelFile(location, sheetName, fieldName2);

		baseClass.stepInfo("Verify '" + fieldName3 + "' Field Displayed Of Downlodeded Excel File.");
		dataSets.verifyFieldDisplayedOfDownlodedExcelFile(location, sheetName, fieldName3);
	}

	/**
	 * @author Gopinath
	 * @TestCase id:50756 : Project level exported dataset details - Validate "Not
	 *           Processed and Not Loaded" tab details for an existing project
	 *           having mapped ingestions and uploaded datasets.
	 * @Description: Project level exported dataset details - Validate "Not
	 *               Processed and Not Loaded" tab details for an existing project
	 *               having mapped ingestions and uploaded datasets
	 */
	@Test(description ="RPMXCON-50756",alwaysRun = true, groups = { "regression" } )
	public void verifyProjectLvelExportedDataSetsExistingProject() {
		baseClass = new BaseClass(driver);
		String location = "C:\\\\BatchPrintFiles\\\\downloads";
		String sheetName = "Not Processed and Not Loaded";
		String fieldName = "Dataset Name";
		String fieldName2 = "File Name";
		String fieldName3 = "Exclusion Reason";
		baseClass.stepInfo("Test case Id: RPMXCON-50756 Sprint 12");
		baseClass.stepInfo(
				"### Project level exported dataset details - Validate 'Not Processed and Not Loaded' tab details for an existing project having mapped ingestions and uploaded datasets ###");
		dataSets = new DataSets(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
		baseClass.selectproject(Input.projectName02);
		baseClass.stepInfo("Navigate data sets page.");
		dataSets.navigateToDataSetsPage();

		baseClass.stepInfo("Perform export data sets.");
		dataSets.exportDataSets();

		baseClass.stepInfo("Download exported file.");
		dataSets.downloadExportedFile();

		baseClass.stepInfo("Verify '" + fieldName + "' Field Displayed Of Downlodeded Excel File.");
		dataSets.verifyFieldDisplayedOfDownlodedExcelFile(location, sheetName, fieldName);

		baseClass.stepInfo("Verify '" + fieldName2 + "' Field Displayed Of Downlodeded Excel File.");
		dataSets.verifyFieldDisplayedOfDownlodedExcelFile(location, sheetName, fieldName2);

		baseClass.stepInfo("Verify '" + fieldName3 + "' Field Displayed Of Downlodeded Excel File.");
		dataSets.verifyFieldDisplayedOfDownlodedExcelFile(location, sheetName, fieldName3);
	}

	/**
	 * @author Gopinath
	 * @TestCase id:50758 : Project level exported dataset details - Validate
	 *           "Loaded with Error" tab details for an existing project having
	 *           mapped and uploaded ingestions.
	 * @Description: Project level exported dataset details - Validate "Loaded with
	 *               Error" tab details for an existing project having mapped and
	 *               uploaded ingestions
	 */
	@Test(description ="RPMXCON-50758",alwaysRun = true, groups = { "regression" } )
	public void verifyProjectLvelExportedDataSetWithLoadedWithErrorFields() {
		baseClass = new BaseClass(driver);
		String location = "C:\\\\BatchPrintFiles\\\\downloads";
		String sheetName = "Loaded with Errors";
		String sheetName2 = "Summary";
		String errorTypeFieldType = "Documents Loaded With Errors";
		String fieldName = "Dataset Name";
		String fieldName2 = "Type of Error Encountered";
		String fieldName3 = "Count of Records";
		baseClass.stepInfo("Test case Id: RPMXCON-50758 Sprint 12");
		baseClass.stepInfo(
				"### Project level exported dataset details - Validate \"Loaded with Error\" tab details for an existing project having mapped and uploaded ingestions ###");
		dataSets = new DataSets(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
		baseClass.selectproject(Input.projectName02);
		baseClass.stepInfo("Navigate data sets page.");
		dataSets.navigateToDataSetsPage();

		baseClass.stepInfo("Perform export data sets.");
		dataSets.exportDataSets();

		baseClass.stepInfo("Download exported file.");
		dataSets.downloadExportedFile();

		baseClass.stepInfo("Verify '" + fieldName + "' Field Displayed Of Downlodeded Excel File.");
		dataSets.verifyFieldDisplayedOfDownlodedExcelFile(location, sheetName, fieldName);

		baseClass.stepInfo("Verify '" + fieldName2 + "' Field Displayed Of Downlodeded Excel File.");
		dataSets.verifyFieldDisplayedOfDownlodedExcelFile(location, sheetName, fieldName2);

		baseClass.stepInfo("Verify '" + fieldName3 + "' Field Displayed Of Downlodeded Excel File.");
		dataSets.verifyFieldDisplayedOfDownlodedExcelFile(location, sheetName, fieldName3);

		baseClass.stepInfo("Verify '" + fieldName3 + "' Field Displayed Of Downlodeded Excel File.");
		dataSets.verifyFieldDisplayedOfDownlodedExcelFile(location, sheetName2, errorTypeFieldType);
	}

	/**
	 * @author Gopinath
	 * @TestCase id:50771 : Verify that after Re-run the copy process without
	 *           ignoring the errors, copy should continues.
	 * @Description: Verify that after Re-run the copy process without ignoring the
	 *               errors, copy should continues
	 */
	@Test(description ="RPMXCON-50771",alwaysRun = true, groups = { "regression" } )
	public void verifyRerunCopyProcessWithoutIgnoringErrors() {
		baseClass = new BaseClass(driver);
		String ingestionType = "Add Only";
		String sourceSystem = "TRUE";
		String sourceLocation = "IngestionTestData\\Automation";
		String sourceFolder = "SSAudioSpeech_Transcript";
		String fieldSeperator = "ASCII(20)";
		String textQualifier = "ASCII(254)";
		String multiValue = "ASCII(174)";
		String datLoadFile = "DAT4_STC_newdateformat.dat";
		String documentKey = "DocID";
		String mp3LoadFile = "MP3 Files.LST";
		String dateFormat = "YYYY/MM/DD HH:MM:SS";
		String docId = "DocID";
		String dataSource = "Datasource";
		String custodian = "Custodian";
		String fileExt = "FileExt";
		String fileName = "FileName";
		String fileSize = "FileSize";
		String fileType = "FileType";
		String docBasic = "DOCBASIC";
		String docFileExt = "DocFileExtension";
		String docFileName = "DocFileName";
		String docFileSize = "DocFileSize";
		String docFileType = "DocFileType";
		baseClass.stepInfo("Test case Id: RPMXCON-50771 Sprint 12");
		baseClass.stepInfo(
				"### Verify that after Re-run the copy process without ignoring the errors, copy should continues ###");
		IngestionPage_Indium ingetion = new IngestionPage_Indium(driver);

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
		baseClass.stepInfo("Select project");
		baseClass.selectproject(Input.projectName);

		baseClass.stepInfo("Navigate to ingestion page.");
		ingetion.navigateToIngestionPage();

		baseClass.stepInfo("Select ingestion type and specify source loaction.");
		ingetion.selectIngestionTypeAndSpecifySourceLocation(ingestionType, sourceSystem, sourceLocation, sourceFolder);

		baseClass.stepInfo("Select DAT delimiters.");
		ingetion.addDelimitersInIngestionWizard(fieldSeperator, textQualifier, multiValue);

		baseClass.stepInfo("Select DAT source.");
		ingetion.selectDATSource(datLoadFile, documentKey);

		baseClass.stepInfo("Select MP3 varient source.");
		ingetion.selectMP3VarientSource(mp3LoadFile, false);

		baseClass.stepInfo("Select Date and Time format.");
		ingetion.selectDateAndTimeFormat(dateFormat);

		baseClass.stepInfo("Click on next button.");
		ingetion.clickOnNextButton();

		baseClass.stepInfo("Select value from first three source DAT fields");
		ingetion.selectValueFromEnabledFirstThreeSourceDATFields(docId, dataSource, custodian);

		baseClass.stepInfo(" select field catagory and destination field by using source DAT field.");
		ingetion.selectFieldCatagoryDestinationFields(fileExt, docBasic, docFileExt);

		ingetion.selectFieldCatagoryDestinationFields(fileName, docBasic, docFileName);

		ingetion.selectFieldCatagoryDestinationFields(fileSize, docBasic, docFileSize);

		ingetion.selectFieldCatagoryDestinationFields(fileType, docBasic, docFileType);

		baseClass.stepInfo("Click on preview and run button.");
		ingetion.clickOnPreviewAndRunButton();

		baseClass.stepInfo("Select all options from filter by dropdown.");
		ingetion.selectAllOptionsFromFilterByDropdown();

		baseClass.stepInfo("Create ingestion to cataloged stage");
		String ingestionName = ingetion.ingestionCreationToCatalogedStage();

		baseClass.stepInfo("Process to click on copied state without ignoring errors.");
		ingetion.clickOnCopiedStateWithoutIgnoringErrors(ingestionName);

	}

	/**
	 * @author Gopinath
	 * @throws InterruptedException
	 * @TestCase id:50744 : Verify that when rollback is in-progress then error page
	 *           should not be displayed on Datasets page.
	 * @Description: Verify that when rollback is in-progress then error page should
	 *               not be displayed on Datasets page.
	 */
	@Test(description ="RPMXCON-50744",alwaysRun = true, groups = { "regression" } )
	public void verifyRollBackIsInProgressErrorMsgNotDisplayed() throws InterruptedException {
		baseClass = new BaseClass(driver);
		String ingestionType = Input.ingestionType;
		String sourceSystem = Input.sourceSystem;
		String sourceLocation = Input.sourceLocation;
		String sourceFolder = Input.sourceFolder;
		String fieldSeperator = Input.fieldSeperator;
		String textQualifier = Input.textQualifier;
		String multiValue = Input.multiValue;
		String datLoadFile = Input.datLoadFile;
		String documentKey = Input.documentKey;
		String mp3LoadFile = Input.mp3LoadFile;
		String dateFormat = Input.dateFormat;
		String docId = Input.docId;
		String dataSource = Input.dataSource;
		String custodian = Input.custodian;
		String fileExt = Input.fileExt;
		String fileName = Input.fileName;
		String fileSize = Input.fileSize;
		String fileType = Input.fileType;
		String docBasic = Input.ingDocBasic;
		String docFileExt = Input.docFileExt;
		String docFileName = Input.ingDocFileName;
		String docFileSize = Input.ingDocFileSize;
		String docFileType = Input.ingDocFileType;
		baseClass.stepInfo("Test case Id: RPMXCON-50744 Sprint 12");
		baseClass.stepInfo(
				"###  Verify that when rollback is in-progress then error page should not be displayed on Datasets page. ###");
		IngestionPage_Indium ingetion = new IngestionPage_Indium(driver);

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);

		baseClass.stepInfo("Navigate to ingestion page.");
		ingetion.navigateToIngestionPage();

		baseClass.stepInfo("Select ingestion type and specify source loaction.");
		ingetion.selectIngestionTypeAndSpecifySourceLocation(ingestionType, sourceSystem, sourceLocation, sourceFolder);

		baseClass.stepInfo("Select DAT delimiters.");
		ingetion.addDelimitersInIngestionWizard(fieldSeperator, textQualifier, multiValue);

		baseClass.stepInfo("Select DAT source.");
		ingetion.selectDATSource(datLoadFile, documentKey);

		baseClass.stepInfo("Select MP3 varient source.");
		ingetion.selectMP3VarientSource(mp3LoadFile, false);

		baseClass.stepInfo("Select Date and Time format.");
		ingetion.selectDateAndTimeFormat(dateFormat);

		baseClass.stepInfo("Click on next button.");
		ingetion.clickOnNextButton();

		baseClass.stepInfo("Select value from first three source DAT fields");
		ingetion.selectValueFromEnabledFirstThreeSourceDATFields(docId, dataSource, custodian);

		baseClass.stepInfo(" select field catagory and destination field by using source DAT field.");
		ingetion.selectFieldCatagoryDestinationFields(fileExt, docBasic, docFileExt);

		ingetion.selectFieldCatagoryDestinationFields(fileName, docBasic, docFileName);

		ingetion.selectFieldCatagoryDestinationFields(fileSize, docBasic, docFileSize);

		ingetion.selectFieldCatagoryDestinationFields(fileType, docBasic, docFileType);

		baseClass.stepInfo("Click on preview and run button.");
		ingetion.clickOnPreviewAndRunButton();

		baseClass.stepInfo("Select all options from filter by dropdown.");
		ingetion.selectAllOptionsFromFilterByDropdown();

		baseClass.stepInfo("Create ingestion to cataloged stage");
		String ingestionName = ingetion.ingestionCreationToCatalogedStage();

		baseClass.stepInfo("Click on roll back from catalog stage and verify status changed is inprogress.");
		ingetion.verifyInprogressStatusByclickOnRollback(ingestionName);

		dataSets = new DataSets(driver);

		baseClass.stepInfo("Navigate data sets page.");
		dataSets.navigateToDataSetsPage();

		baseClass.stepInfo("Verify data sets page is loaded.");
		dataSets.verifyDatasetsPageIsLoaded();

		baseClass.stepInfo("Impersonate PA to RMU");
		baseClass.impersonatePAtoRMU();

		baseClass.stepInfo("Navigate data sets page.");
		dataSets.navigateToDataSetsPage();

		baseClass.stepInfo("Verify data sets page is loaded.");
		dataSets.verifyDatasetsPageIsLoaded();

		loginPage.logout();

	}

	/**
	 * @author Gopinath
	 * @throws InterruptedException
	 * @TestCase id:58503 : Verify if user ingest documents with ICE as Source
	 *           System then same dataset cannot ingest with any other Source
	 *           System.
	 * @Description: Verify if user ingest documents with ICE as Source System then
	 *               same dataset cannot ingest with any other Source System
	 */
	@Test(description ="RPMXCON-58503",enabled = true, groups = { "regression" } )
	public void verifyErrorMsgDisplayedByIngestingPublishedData() throws InterruptedException {
		baseClass = new BaseClass(driver);
		String ingestionType = Input.ingestionType;
		String sourceSystem = Input.iceSourceSystem;
		String sourceLocation = Input.sourceLocation;
		String sourceFolder = Input.multiPageTIFFSourceFolder;
		String fieldSeperator = Input.fieldSeperator;
		String textQualifier = Input.textQualifier;
		String multiValue = Input.multiValue;
		String datLoadFile = Input.multiPageTIFFDATLoadFile;
		String documentKey = Input.documentKeyBNum;
		String dateFormat = Input.dateFormat;
		String docId = Input.documentKeyBNum;
		String dataSource = Input.documentKeyDSource;
		String custodian = Input.documentKeyCName;

		baseClass.stepInfo("Test case Id: RPMXCON-58503 Sprint 13");
		baseClass.stepInfo(
				"### Verify if user ingest documents with ICE as Source System then same dataset cannot ingest with any other Source System ###");
		IngestionPage_Indium ingetion = new IngestionPage_Indium(driver);

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
		baseClass.stepInfo("Select project");
		baseClass.selectproject(Input.ingestDataProject);

		baseClass.stepInfo("Navigate to ingestion page.");
		ingetion.navigateToIngestionPage();

		baseClass.stepInfo("Select ingestion type and specify source loaction.");
		ingetion.selectIngestionTypeAndSpecifySourceLocation(ingestionType, sourceSystem, sourceLocation, sourceFolder);

		baseClass.stepInfo("Select DAT delimiters.");
		ingetion.addDelimitersInIngestionWizard(fieldSeperator, textQualifier, multiValue);

		baseClass.stepInfo("Select DAT source.");
		ingetion.selectDATSource(datLoadFile, documentKey);

		baseClass.stepInfo("Select Date and Time format.");
		ingetion.selectDateAndTimeFormat(dateFormat);

		baseClass.stepInfo("Click on next button.");
		ingetion.clickOnNextButton();

		baseClass.stepInfo("Select value from first three source DAT fields");
		ingetion.selectValueFromEnabledFirstThreeSourceDATFields(docId, dataSource, custodian);

		baseClass.stepInfo("Click on preview and run button.");
		ingetion.clickOnPreviewAndRunButton();

		baseClass.stepInfo("Select all options from filter by dropdown.");
		ingetion.selectAllOptionsFromFilterByDropdown();

		baseClass.stepInfo("Create ingestion to cataloged stage");
		ingetion.ingestionCreationToFailedState();

		loginPage.logout();

	}
	/**
	 * Author :Brundha date: NA Modified date: Modified by: Description :Verify
	 * Search should work by concatenated email metadata field
	 */
	@Test(description ="RPMXCON-49560",enabled = true, groups = { "regression" } )
	public void verifySearchForSelectedMetadata() throws InterruptedException {
		baseClass = new BaseClass(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-49560");
		baseClass.stepInfo("### Verify Search should work by concatenated email metadata field ###");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password,Input.projectName02);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
		
		SessionSearch search = new SessionSearch(driver);
		search.SearchMetaData("EmailCCNamesAndAddresses", Input.EmailAuthourName);
		search.verifyTheCountOfDocumentForMetaData();

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password,Input.projectName02);
		search.SearchMetaData("EmailCCNamesAndAddresses", Input.EmailAuthourName);
		search.verifyTheCountOfDocumentForMetaData();
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password,Input.projectName02);

		
		search.SearchMetaData("EmailCCNamesAndAddresses", Input.EmailAuthourName);
		search.verifyTheCountOfDocumentForMetaData();
		baseClass.passedStep("Verified Search should work by concatenated email metadata field");
		loginPage.logout();
	}

	/**
	 * Author :Brundha date: NA Modified date: Modified by: Description :Verify
	 * Search should work by split email metadata field
	 */
	@Test(description ="RPMXCON-49565",alwaysRun = true, groups = { "regression" } )
	public void verifySearchForEmailMetaData() throws InterruptedException {
		baseClass = new BaseClass(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-49565");
		baseClass.stepInfo("### Verify Search should work by split email metadata field ###");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password,Input.projectName02);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
		
		SessionSearch search = new SessionSearch(driver);
		search.SearchMetaData("EmailCCNames", Input.EmailAuthourName);
		search.verifyTheCountOfDocumentForMetaData();

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password,Input.projectName02);

		
		search.SearchMetaData("EmailCCNames", Input.EmailAuthourName);
		search.verifyTheCountOfDocumentForMetaData();
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password,Input.projectName02);

		
		search.SearchMetaData("EmailCCNames", Input.EmailAuthourName);
		search.verifyTheCountOfDocumentForMetaData();
		baseClass.passedStep("Verified Search should work by split email metadata field");

		loginPage.logout();
	}

	

	/**
	 * Author :Brundha date: NA Modified date: Modified by: Description :Verify
	 * Email metadata in Manage-Project fields
	 */
	@Test(description ="RPMXCON-49563",alwaysRun = true, groups = { "regression" } )
	public void verifyingEmailMetaDataInProjectField() throws InterruptedException {
		baseClass = new BaseClass(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-49563");
		baseClass.stepInfo("### Verify Email metadata in Manage-Project fields ###");
		String EmailMetaData = "Email";
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password,Input.projectName02);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
		baseClass.stepInfo(" Go to Manage > Project Fields");
		ProjectFieldsPage projectFieldsPage = new ProjectFieldsPage(driver);
		projectFieldsPage.navigateToProjectFieldsPage();

		baseClass.stepInfo(" Enter the text in 'Filter Fields By' and click on Apply");
		projectFieldsPage.applyFilterByFilterName(EmailMetaData);
		projectFieldsPage.validateFilterFieldsByContainsValueInTheGrid(
				projectFieldsPage.getProjectGridFieldNameValue(EmailMetaData), EmailMetaData);

		baseClass.passedStep("Verified Email metadata in Manage-Project fields");
		loginPage.logout();

	}

	
	
	/**
	 * Author :Baskar date: 04/05/2022 Modified date: NA Modified by: NA
	 * Description:Verify Email metadata automatically released to Security Group
	 * 
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-49561",enabled = true, groups = { "regression" } )
	public void verifyEmailMetadataWithIngestionName() throws Exception {
		baseClass = new BaseClass(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		ProjectPage projectPage = new ProjectPage(driver);
		SecurityGroupsPage securityGroupPage = new SecurityGroupsPage(driver);
		SessionSearch	sessionSearch = new SessionSearch(driver);
		UserManagement user = new UserManagement(driver);
		DocListPage doclist = new DocListPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.projectName02);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);

		String newSg = "Sg" + Utility.dynamicNameAppender();
		String toDocList = "toDoclist" + Utility.dynamicNameAppender();
		String[] metaData = { "EmailCCNamesAndAddresses", "EmailBCCNamesAndAddresses", "EmailToNamesAndAddresses",
				"EmailAuthorNameAndAddress" };

		String securityTab = "Shared with " + newSg;
		String ingestionName = "41AD_IngestionEmailData_20220321091148016";
		baseClass.stepInfo("Test case Id: RPMXCON-49561");
		baseClass.stepInfo("Verify Email metadata automatically released to Security Group");
		driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		// creating new sg
		securityGroupPage.createSecurityGroups(newSg);
		// assign project field to sg
		for (int i = 0; i < metaData.length; i++) {
			System.out.println(metaData[i]);
			securityGroupPage.addProjectFieldtoSG(newSg, metaData[i]);
		}
		baseClass.stepInfo("Email metadata project field are assigned to :" + newSg);

		// releasing document to new sg
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkRelease(newSg);

		// searching by ingestion name in metadata
		Actions act = new Actions(driver.getWebDriver());
		act.moveToElement(sessionSearch.getMouseOver_ToTextBoxString(Input.searchString1).getWebElement()).build()
				.perform();
		sessionSearch.getDeletePreviousSearch().waitAndClick(5);
		sessionSearch.metaDataSearchInBasicSearch("IngestionName", ingestionName);
		// saving the ingestion name as pa users
		sessionSearch.saveSearchAction();
		baseClass.waitForElement(sessionSearch.getSaveSearchPopupFolderName(securityTab));
		sessionSearch.getSaveSearchPopupFolderName(securityTab).ScrollTo();
		sessionSearch.getSaveSearchPopupFolderName(securityTab).waitAndClick(5);
		baseClass.waitForElement(sessionSearch.getSaveSearch_Name());
		sessionSearch.getSaveSearch_Name().SendKeys(toDocList);
		baseClass.waitForElement(sessionSearch.getSaveSearch_SaveButton());
		sessionSearch.getSaveSearch_SaveButton().Click();

		Reporter.log("Saved the search with name '" + toDocList + "'", true);
		UtilityLog.info("Saved search with name - " + toDocList);

		// access to rmu user new sg
		this.driver.getWebDriver().get(Input.url + "User/UserListView");
		user.passingUserName(Input.rmu1userName);
		user.applyFilter();
		baseClass.waitTime(3);
		user.editLoginUser();
		user.addingSGToUser(Input.securityGroup, newSg);
//		user.getSubmit().waitAndClick(5);

		// logout as pa
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password,Input.projectName02);
		baseClass.selectsecuritygroup(newSg);

		// navigating to doclist
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		baseClass.waitForElement(sessionSearch.getSaveSearchPopupFolderName(securityTab));
		sessionSearch.getSaveSearchPopupFolderName(securityTab).waitAndClick(5);
		savedSearch.savedSearchToDocList(toDocList);
		doclist.verifyTheMetaDataPresences();
		baseClass.selectsecuritygroup(Input.securityGroup);

		// logout as rmu
		loginPage.logout();

	}

	/**
	 * Author :Baskar date: 09/05/2022 Modified date: NA Modified by: NA
	 * Description:To Verify for Audio longer than 1 hour, in Docview, "Zoom In/Zoom
	 * Out" should be available so user could switch between the short and long wave
	 * forms.
	 */
	@Test(description ="RPMXCON-48241",enabled = true, groups = { "regression" } )
	public void verifyMoreThanHourAudioDocs() throws InterruptedException {
		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		docview = new DocViewPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		String audioDocsIngestionName = "SSAudioSpeech";
		String moreThanHourPT = Input.ingestionLessThanHour;
		String moreThanHourUAT = "ID00005184";
		baseClass.stepInfo("Test case Id: RPMXCON-48241");
		baseClass.stepInfo("To Verify for Audio longer than 1 hour, in Docview, \"Zoom In/Zoom Out\" should be "
				+ "available so user could switch between the short and long wave forms.");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.projectName02);
		boolean uatFlag=Input.url.contains("sightlineuat");
		boolean ptFlag=Input.url.contains("sightlinept");
		String ingestionFullName = dataSets.isDataSetisAvailable(audioDocsIngestionName);
		if (ingestionFullName!= null) {
			sessionSearch.MetaDataSearchInBasicSearch("IngestionName", ingestionFullName);
			sessionSearch.viewInDocView();
			if (ptFlag==true) {
				baseClass.waitForElement(docview.getDocumentByid(moreThanHourPT));
				docview.getDocumentByid(moreThanHourPT).waitAndClick(5);
			}
			if (uatFlag==true) {
				baseClass.waitForElement(docview.getDocumentByid(moreThanHourUAT));
				docview.getDocumentByid(moreThanHourUAT).waitAndClick(5);
			}

			// verifying more than one hour audio docs
			String overAllAudioTime = docview.getDocview_Audio_EndTime().getText();
			String[] splitData = overAllAudioTime.split(":");
			String data = splitData[0].toString();
			System.out.println(data);
			if (Integer.parseInt(data) >= 01) {
				baseClass.stepInfo("Audio docs have more than:" + overAllAudioTime + " hour to check zoom function");
			} else {
				baseClass.failedMessage("Lesser than one hour");
			}

			// checking zoom in function working for more than one hour audio docs
			docview.getAudioDocZoom().waitAndClick(5);
			boolean zoomBar = docview.getAudioZoomBar().Displayed();
			softAssertion.assertTrue(zoomBar);
			baseClass.passedStep("Zoom functionality working for more than one hour of document");
		} else {
			baseClass.failedStep("Ingestion not available, Need to ingest for this project");
		}
		softAssertion.assertAll();
		loginPage.logout();

	}

	/**
	 * Author :Baskar date: 09/05/2022 Modified date: NA Modified by: NA
	 * Description:To verify for Audio less than 1 hour, in Docview, "Zoom In/Zoom
	 * Out" is disabled or hidden.
	 */
	@Test(description ="RPMXCON-48239",enabled = true, groups = { "regression" } )
	public void verifyLessThanHourAudioDocs() throws InterruptedException {
		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		SoftAssert softAssertion = new SoftAssert();
		docview = new DocViewPage(driver);
		String audioDocsIngestionName = "SSAudioSpeech";
		String lessThanHourPT = Input.ingestionLessThanHour;
		String lessThanHourUAT = "ID00005180";
		baseClass.stepInfo("Test case Id: RPMXCON-48239");
		baseClass.stepInfo(
				"To verify for Audio less than 1 hour, in Docview, \"Zoom In/Zoom Out\" is disabled or hidden.");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.projectName02);
		boolean uatFlag=Input.url.contains("sightlineuat");
		boolean ptFlag=Input.url.contains("sightlinept");
		String ingestionFullName = dataSets.isDataSetisAvailable(audioDocsIngestionName);
		if (ingestionFullName!= null) {
			sessionSearch.MetaDataSearchInBasicSearch("IngestionName", ingestionFullName);
			sessionSearch.viewInDocView();
			if (ptFlag==true) {
				baseClass.waitForElement(docview.getDocumentByid(lessThanHourPT));
				docview.getDocumentByid(lessThanHourPT).waitAndClick(5);
			}
			if (uatFlag==true) {
				baseClass.waitForElement(docview.getDocumentByid(lessThanHourUAT));
				docview.getDocumentByid(lessThanHourUAT).waitAndClick(5);
			}

			// verifying less than one hour audio docs
			String overAllAudioTime = docview.getDocview_Audio_EndTime().getText();
			String[] splitData = overAllAudioTime.split(":");
			String data = splitData[0].toString();
			System.out.println(data);
			if (Integer.parseInt(data) >= 01) {
				baseClass.failedStep("more than one hour docs selected");
			} else {
				baseClass.stepInfo("Audio docs have less than:" + overAllAudioTime + " hour to check zoom function");
			}

			// checking zoom in function working for less than one hour audio docs
			baseClass.waitTime(5);
			boolean zoomBar = (boolean) ((JavascriptExecutor) driver.getWebDriver()).executeScript(
					"return document.querySelector(\"div.col-md-2.disabledbutton div:nth-child(3) > div\").hidden;");
			softAssertion.assertFalse(zoomBar);
			baseClass.passedStep("Zoom functionality not working for less than one hour of document,Zoom is hidden");
		} else {
			baseClass.failedStep("Ingestion not available, Need to ingest for this project");
		}
		softAssertion.assertAll();
		loginPage.logout();

	}

	/**
	 * @Author jeevitha
	 * @Descripyion : Validate exporting dataset details at project level for
	 *              impersonating as RMU [RPMXCON-50752]
	 * @param userName
	 * @param password
	 * @param fromRole
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-50752",enabled = true, dataProvider = "userDataSets", groups = { "regression" })
	public void verifyingTheExportIconWithDiffUsers(String userName, String password, String fromRole)
			throws Exception {
		String status = "true";

		ManageUsersPage manage = new ManageUsersPage(driver);
		UserManagement user = new UserManagement(driver);
		DataSets data = new DataSets(driver);

		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Logged in as : " + fromRole);

		baseClass.stepInfo("Test case Id: RPMXCON-50752  Ingestion");
		baseClass.stepInfo("Validate exporting dataset details at project level for impersonating as RMU");

		// navigate to user page
		user.navigateToUsersPAge();

		// verify Dataset Checkbox
		user.passingUserName(userName);
		user.applyFilter();
		user.editLoginUser();
		user.getFunctionalityTab().waitAndClick(5);
		user.verifyStatusDataSet(status);
		user.saveButtonOfFunctionTab();
		baseClass.stepInfo("DataSet Check Box is Verified");

		// Imp to RMU
		user.navigateToUsersPAge();
		baseClass.rolesToImp(fromRole, "RMU");

		// Navigate to dataset Page & verify Export Icon
		data.navigateToDataSetsPage();
		baseClass.ValidateElement_Presence(data.getExportIconButton(), "Export Icon");

		loginPage.logout();

	}

	/**
	 * Author: Mohan date: 06/05/2022 Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-50749 Description :Validate exporting dataset details at project
	 * level for Project Admin
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(description ="RPMXCON-50749",enabled = true, groups = { "regression" })
	public void validateExportingDatasetAsPA() throws InterruptedException, IOException {

		

		ingestionPage = new IngestionPage_Indium(driver);
		DataSets datasets = new DataSets(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-50749");
		baseClass.stepInfo("Validate exporting dataset details at project level for Project Admin");

		ingestionPage.navigateToDataSetsPage();
		ingestionPage.verifyToolTipOfExportIcon();
		String expectedMsg = "A task for Dataset Summary report has been added to the background. You will receive a notification when it completes.";
		int bgCountBefore = baseClass.initialBgCount();

		baseClass.waitForElement(datasets.getExportIconButton());
		datasets.getExportIconButton().waitAndClick(10);
		baseClass.VerifySuccessMessage(expectedMsg);
		String exportFile = datasets.downloadExportFile(bgCountBefore);
		String sheetName1 = baseClass.readExcelDataSheetNameOnly(exportFile, 0);
		String sheetName2 = baseClass.readExcelDataSheetNameOnly(exportFile, 1);
		String sheetName3 = baseClass.readExcelDataSheetNameOnly(exportFile, 2);

		if (sheetName1.equalsIgnoreCase("Summary") && sheetName2.equalsIgnoreCase("Not Processed and Not Loaded")
				&& sheetName3.equalsIgnoreCase("Loaded with Errors")) {
			baseClass.passedStep("The Excel contains all mentioned tabs");

		} else {
			baseClass.failedStep("The excel file doesn't contains any metioned tabs");
		}

		loginPage.logout();

	}

	/**
	 * Author: Mohan date: 06/05/2022 Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-50750 Description :Validate exporting dataset details at security
	 * group level for RMU
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(description ="RPMXCON-50750",enabled = true, groups = { "regression" })
	public void validateExportingDatasetAsRMU() throws InterruptedException, IOException {

		ingestionPage = new IngestionPage_Indium(driver);
		DataSets datasets = new DataSets(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-50750");
		baseClass.stepInfo("Validate exporting dataset details at security group level for RMU");
		
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password,Input.projectName02);
		ingestionPage.navigateToDataSetsPage();
		ingestionPage.verifyToolTipOfExportIcon();
		String expectedMsg = "A task for Dataset Summary report has been added to the background. You will receive a notification when it completes.";
		int bgCountBefore = baseClass.initialBgCount();

		baseClass.waitForElement(datasets.getExportIconButton());
		datasets.getExportIconButton().waitAndClick(10);
		baseClass.VerifySuccessMessage(expectedMsg);
		String exportFile = datasets.downloadExportFile(bgCountBefore);
		String sheetName1 = baseClass.readExcelDataSheetNameOnly(exportFile, 0);
		String sheetName2 = baseClass.readExcelDataSheetNameOnly(exportFile, 1);
		String sheetName3 = baseClass.readExcelDataSheetNameOnly(exportFile, 2);

		if (sheetName1.equalsIgnoreCase("Summary") && sheetName2.equalsIgnoreCase("Not Processed and Not Loaded")
				&& sheetName3.equalsIgnoreCase("Loaded with Errors")) {
			baseClass.passedStep("The Excel contains all mentioned tabs");

		} else {
			baseClass.failedStep("The excel file doesn't contains any metioned tabs");
		}

		loginPage.logout();

	}

	/**
	 * Author: Jeevitha
	 * 
	 * @Description :Validate exporting dataset details at project level for
	 *              impersonating as Project Admin
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(description ="RPMXCON-50751",enabled = true, dataProvider = "userDataSetsSAAndDA", groups = { "regression" })
	public void validateExportingDatasetAsSAandDA(String userName, String password, String fromRole)
			throws InterruptedException, IOException {

		
		ingestionPage = new IngestionPage_Indium(driver);
		DataSets datasets = new DataSets(driver);
		
		// login as user
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("Test case Id: RPMXCON-50751 Ingestion");
		baseClass.stepInfo("Validate exporting dataset details at project level for impersonating as Project Admin");

		//imp to PA
		baseClass.rolesToImp(fromRole, "PA");

		// verify tooltip of export icon
		ingestionPage.navigateToDataSetsPage();
		ingestionPage.verifyToolTipOfExportIcon();
		String expectedMsg = "A task for Dataset Summary report has been added to the background. You will receive a notification when it completes.";
		int bgCountBefore = baseClass.initialBgCount();

		// download export file
		baseClass.waitForElement(datasets.getExportIconButton());
		datasets.getExportIconButton().waitAndClick(10);
		baseClass.VerifySuccessMessage(expectedMsg);
		String exportFile = datasets.downloadExportFile(bgCountBefore);

		// verify sheetnames
		String sheetName1 = baseClass.readExcelDataSheetNameOnly(exportFile, 0);
		String sheetName2 = baseClass.readExcelDataSheetNameOnly(exportFile, 1);
		String sheetName3 = baseClass.readExcelDataSheetNameOnly(exportFile, 2);

		if (sheetName1.equalsIgnoreCase("Summary") && sheetName2.equalsIgnoreCase("Not Processed and Not Loaded")
				&& sheetName3.equalsIgnoreCase("Loaded with Errors")) {
			baseClass.passedStep(
					"The Excel contains all mentioned tabs : " + sheetName1 + " , " + sheetName2 + " , " + sheetName3);

		} else {
			baseClass.failedStep("The excel file doesn't contains any metioned tabs");
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
    @Test(description ="RPMXCON-49521",enabled = true, groups = { "regression" } )
	public void verifyTEXTAndTIFFSFileGenerateSearchablePDFsIsTrue() throws InterruptedException {

		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
		String ingestionType="Add Only";
		baseClass.stepInfo("Test case Id: RPMXCON-49521");
		baseClass.stepInfo(
				"Verify that if PA ingested both TEXT and TIFF's file,the 'Generate Searchable PDFs' is true and TIFF is missing then it should displays default PDF file");
		ingestionPage = new IngestionPage_Indium(driver);
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
		if (text.contains("There is no file")||text.contains("From: Phillip K Allen")) {
			baseClass.passedStep(
					"In text tab it displayed as 'There is no file associated with this document on text view'");
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
	@Test(description ="RPMXCON-50377",enabled = true,  groups = {"regression" })
	public void verifyNativePDFAndTIFFSFileGenerateSearchablePDFsIsTrue() throws InterruptedException  {
		
	
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-50377");
		baseClass.stepInfo("Verify that if PA ingested Native, PDF and TIFF's file and the 'Generate Searchable PDFs' option is set to true, then PDF should be generated from the TIFF's");
		String ingestionType="Add Only";
		ingestionPage = new IngestionPage_Indium(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
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
	 * Author :Brundha Test Case Id:RPMXCON-48195 Description :To Very the Family
	 * Member Counts After Ingestion completed successfully.
	 * 
	 * @throws InterruptedException
	 *
	 */
	@Test(description ="RPMXCON-48195",enabled = true, groups = { "regression" } )
	public void verifyFamilyMemberCountInDocList() throws InterruptedException {
		

		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-48195");
		baseClass.stepInfo("To Very the Family Member Counts After Ingestion completed successfully.");
		ingestionPage = new IngestionPage_Indium(driver);
		boolean status = ingestionPage.verifyIngestionpublish(Input.PP_PDFGen_10Docs);
		String ingestionType = "Add Only";
		String familyMemberCount = "FamilyMemberCount";
		System.out.println(status);
		if (status == false) {
			ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs, ingestionType, "TRUE",
					Input.DATPPPDF10Docs, null, Input.TextPPPDF10Docs, null, null, Input.ImagePPPDF10docs, null, null,
					null);
		}
		SessionSearch sessionsearch = new SessionSearch(driver);
		baseClass.stepInfo("Basic content search");
		sessionsearch.basicSearchWithMetaDataQuery("1","FamilyMemberCount");

		baseClass.stepInfo("Navigating to doclist page");
		sessionsearch.ViewInDocList();

		DocListPage doc = new DocListPage(driver);
		baseClass.waitForElement(doc.getSelectDropDown());
		doc.getSelectDropDown().waitAndClick(10);
		doc.selectingSingleValueInCoumnAndRemovingExistingOne(familyMemberCount);
		String FamilyMemberCount=doc.getDocList_EmailName().getText();
		int DocCount=Integer.valueOf(FamilyMemberCount);
		if(DocCount!=0) {
			baseClass.passedStep("Family Members count is displayed as expected");
		}else {
			baseClass.failedStep("Family Members Count is not displayed as expected");
		}
		loginPage.logout();
	}
	
	/** 
     *Author: Mohan date: 02/05/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49520
	 * Description :Verify that if PA ingested both PDF and TIFF's file,the "Generate Searchable PDFs"is true and TIFF is missing then it PDF should displays PDF in viewer
     * @throws InterruptedException 
	 */
	@Test(description ="RPMXCON-49520",enabled = true,  groups = {"regression" })
	public void verifyPDFAndTIFFSFileGenerateSearchablePDFsIsTrue() throws InterruptedException  {
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-49520");
		baseClass.stepInfo("Verify that if PA ingested both PDF and TIFF's file,the 'Generate Searchable PDFs' is true and TIFF is missing then it PDF should displays PDF in viewer");
		String ingestionType="Overlay Only";
		ingestionPage = new IngestionPage_Indium(driver);
		ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,ingestionType, null, Input.DATPPPDF10Docs, null,
					null, "PDFs - 5Docs.lst", Input.ImagePPPDF10docs,"Select", null, null, null);
		
		DataSets dataSets=new DataSets(driver);
		dataSets.navigateToDataSetsPage();
		dataSets.selectDataSetWithNameInDocView(Input.PP_PDFGen_10Docs);
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
	@Test(description ="RPMXCON-49519",enabled = true,  groups = {"regression" })
	public void verifyNativeAndTIFFSFileGenerateSearchablePDFsIsTrue() throws InterruptedException  {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-49519");
		baseClass.stepInfo("Verify that if PA ingested both Native and TIFF's file, the 'Generate Searchable PDFs' is true and TIFF is missing then searchable PDF's should be generated from the Natives.");
		String ingestionType="Overlay Only";
		ingestionPage = new IngestionPage_Indium(driver);
		ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,ingestionType, null, Input.DATPPPDF10Docs, "Natives -5Docs.lst",
					null,null , Input.ImagePPPDF10docs,"Select", null, null, null);
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
     *Author: Mohan date: 06/05/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49509
	 * Description :Verify that if PA ingested both native's and TIFF's file,and the "Generate Searchable PDFs" option is set to false then it should display TIFF in default viewer
     * @throws InterruptedException 
	 */
	@Test(description ="RPMXCON-49509",enabled = true,  groups = {"regression" })
	public void verifyNativeAndTIFFSFileGenerateSearchablePDFsIsFalse() throws InterruptedException  {
		
		
		DocViewPage docViewPage = new DocViewPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-49509");
		baseClass.stepInfo("Verify that if PA ingested both native's and TIFF's file,and the \"Generate Searchable PDFs\" option is set to false then it should display TIFF in default viewer");
		ingestionPage = new IngestionPage_Indium(driver);
		String ingestionType="Overlay Only";
		
		ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,ingestionType, null, Input.DATPPPDF10Docs, "Natives -5Docs.lst",
					null,null , Input.ImagePPPDF10docs,null, null, null, null);
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
	
	

	@DataProvider(name = "userDataSets")
	public Object[][] userDataSet() {
		return new Object[][] { { Input.sa1userName, Input.sa1password, "SA" },
				{ Input.da1userName, Input.da1password, "DA" }, { Input.pa1userName, Input.pa1password, "PA" } };
	}

	@DataProvider(name = "userDataSetsSAAndDA")
	public Object[][] userDataSetsSAAndDA() {
		return new Object[][] { { Input.sa1userName, Input.sa1password, "SA" },
				{ Input.da1userName, Input.da1password, "DA" } };
	}
	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)
	public void close() {

		System.out.println("******TEST CASES FOR CODINGFORM EXECUTED******");
	}
}
