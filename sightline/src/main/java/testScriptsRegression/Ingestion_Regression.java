package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
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

	@BeforeMethod(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Ex" + "ecution for prerequisite");

		Input input = new Input();
		input.loadEnvConfig();
		driver = new Driver();
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());
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
	@Test(alwaysRun = true, groups = { "regression" }, priority = 1)
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
	@Test(alwaysRun = true, groups = { "regression" }, priority = 2)
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
	@Test(alwaysRun = true, groups = { "regression" }, priority = 3)
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
	@Test(alwaysRun = true, groups = { "regression" }, priority = 4)
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
		ingetion.selectDateAndTimeForamt(dateFormat);

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
	@Test(alwaysRun = true, groups = { "regression" }, priority = 5)
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
		baseClass.stepInfo("Select project");

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
		ingetion.selectDateAndTimeForamt(dateFormat);

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
	@Test(alwaysRun = true, groups = { "regression" }, priority = 6)
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
		ingetion.selectDateAndTimeForamt(dateFormat);

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
	@Test(alwaysRun = true, groups = { "regression" }, priority = 7)
	public void verifySearchForSelectedMetadata() throws InterruptedException {
		baseClass = new BaseClass(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-49560");
		baseClass.stepInfo("### Verify Search should work by concatenated email metadata field ###");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
		baseClass.selectproject(Input.regressionConsilio1);
		
		SessionSearch search = new SessionSearch(driver);
		search.SearchMetaData("EmailCCNamesAndAddresses", Input.EmailAuthourName);
		search.verifyTheCountOfDocumentForMetaData();

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.selectproject(Input.regressionConsilio1);
		search.SearchMetaData("EmailCCNamesAndAddresses", Input.EmailAuthourName);
		search.verifyTheCountOfDocumentForMetaData();
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		baseClass.selectproject(Input.regressionConsilio1);
		
		search.SearchMetaData("EmailCCNamesAndAddresses", Input.EmailAuthourName);
		search.verifyTheCountOfDocumentForMetaData();
		baseClass.passedStep("Verified Search should work by concatenated email metadata field");
		loginPage.logout();
	}

	/**
	 * Author :Brundha date: NA Modified date: Modified by: Description :Verify
	 * Search should work by split email metadata field
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 8)
	public void verifySearchForEmailMetaData() throws InterruptedException {
		baseClass = new BaseClass(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-49565");
		baseClass.stepInfo("### Verify Search should work by split email metadata field ###");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
		baseClass.selectproject(Input.projectName02);
		
		SessionSearch search = new SessionSearch(driver);
		search.SearchMetaData("EmailCCNames", Input.EmailAuthourName);
		search.verifyTheCountOfDocumentForMetaData();

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		baseClass.selectproject(Input.projectName02);
		
		search.SearchMetaData("EmailCCNames", Input.EmailAuthourName);
		search.verifyTheCountOfDocumentForMetaData();
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

		baseClass.selectproject(Input.projectName02);
		
		search.SearchMetaData("EmailCCNames", Input.EmailAuthourName);
		search.verifyTheCountOfDocumentForMetaData();
		baseClass.passedStep("Verified Search should work by split email metadata field");

		loginPage.logout();
	}

	

	/**
	 * Author :Brundha date: NA Modified date: Modified by: Description :Verify
	 * Email metadata in Manage-Project fields
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 9)
	public void verifyingEmailMetaDataInProjectField() throws InterruptedException {
		baseClass = new BaseClass(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-49563");
		baseClass.stepInfo("### Verify Email metadata in Manage-Project fields ###");
		String EmailMetaData = "Email";
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
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
	 * Author :Aathith date: NA Modified date: Modified by: Description : Verify
	 * Email metadata in DocList and in DocView
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 10)
	public void verifyingEmailMetaDataInDoclistDocview() throws InterruptedException {

		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		sessionsearch = new SessionSearch(driver);
		tagandfolder = new TagsAndFoldersPage(driver);
		doclist = new DocListPage(driver);
		docview = new DocViewPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-49558");
		baseClass.stepInfo("Verify Email metadata in DocList and in DocView");

		String foldername = "IngestionFolder" + Utility.dynamicNameAppender();
		String folderTheadMap = "IngestionFolderTheadMap" + Utility.dynamicNameAppender();
		String[] addEmailColumn = { "EmailAuthorName", "EmailAuthorAddress", "EmailToNames", "EmailToAddresses",
				"EmailCCNames", "EmailCCAddresses", "EmailBCCNames", "EmailBCCAddresses" };
		baseClass.selectproject(Input.regressionConsilio1);
		tagandfolder.CreateFolder(foldername, Input.securityGroup);
		tagandfolder.CreateFolder(folderTheadMap, Input.securityGroup);

		
		String ingestionFullName = dataSets.isDataSetisAvailable(Input.IngestionEmailDataFolder);
		if (ingestionFullName != null) {

			sessionsearch.MetaDataSearchInBasicSearch(Input.metadataIngestion, ingestionFullName);
			sessionsearch.bulkReleaseIngestions(Input.securityGroup);
			sessionsearch.bulkFolderExisting(foldername);
			sessionsearch.ViewInDocListWithOutPureHit();

			doclist.SelectColumnDisplayByRemovingExistingOnes(addEmailColumn);
			driver.waitForPageToBeReady();
			for (String metadata : addEmailColumn) {
				baseClass.visibleCheck(metadata);
			}
			baseClass.stepInfo("Email metadata is display correctly in doc list");

			doclist.selectAllDocs();
			doclist.viewSelectedDocumentsInDocView();
			driver.waitForPageToBeReady();
			driver.scrollingToBottomofAPage();
			driver.waitForPageToBeReady();
			for (String metadata : addEmailColumn) {
				baseClass.visibleCheck(metadata);
			}
			baseClass.stepInfo(
					"In Doc View -Meta Data panel , Email Metadata like EmailAuthorName, EmailAuthorAddress etc. is displayed");

			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			for (String metadata : addEmailColumn) {
				docview.selectSourceDocIdInAvailableField(metadata);
			}
			for (String metadata : addEmailColumn) {
				baseClass.visibleCheck(metadata);
			}
			baseClass.stepInfo(
					"In Doc View -Mini Doc List , Email Metadata like EmailAuthorName, EmailAuthorAddress etc. is displayed");

			driver.getWebDriver().get(Input.url + "Search/Searches");
			sessionsearch.Removedocsfromresults();
			driver.waitForPageToBeReady();
			int doccount = Integer.parseInt(sessionsearch.getThreadedLastCount().getText().trim());
			System.out.println(doccount);
			sessionsearch.getThreadedAddButton().waitAndClick(10);
			sessionsearch.bulkFolderExistingWithoutPureHit(folderTheadMap);
			sessionsearch.ViewInDocViewWithoutPureHit();
			docview.verifyTheadMapValue(doccount, "participant");

			loginPage.logout();
			baseClass.stepInfo("perform task for review manager");
			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password, Input.regressionConsilio1);
			tagandfolder.selectFolderViewInDocList(foldername);

			doclist.SelectColumnDisplayByRemovingExistingOnes(addEmailColumn);
			driver.waitForPageToBeReady();
			for (String metadata : addEmailColumn) {
				baseClass.visibleCheck(metadata);
			}
			baseClass.stepInfo("Email metadata is display correctly in doc list");

			doclist.selectAllDocs();
			doclist.viewSelectedDocumentsInDocView();
			driver.scrollingToBottomofAPage();
			driver.waitForPageToBeReady();
			for (String metadata : addEmailColumn) {
				baseClass.visibleCheck(metadata);
			}
			baseClass.stepInfo(
					"In Doc View -Meta Data panel , Email Metadata like EmailAuthorName, EmailAuthorAddress etc. is displayed");

			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			for (String metadata : addEmailColumn) {
				docview.selectSourceDocIdInAvailableField(metadata);
			}
			for (String metadata : addEmailColumn) {
				baseClass.visibleCheck(metadata);
			}
			baseClass.stepInfo(
					"In Doc View -Mini Doc List , Email Metadata like EmailAuthorName, EmailAuthorAddress etc. is displayed");

			tagandfolder.selectFolderViewInDocView(folderTheadMap);
			docview.verifyTheadMapValue(doccount, "participant");

		}

		baseClass.passedStep("Verified Email metadata in DocList and in DocView");
		loginPage.logout();

	}

	/**
	 * Author :Aathith date: NA Modified date: Modified by: Description : To verify
	 * that if Email data contained space before the '@' sign , it should not
	 * calculate two distinct values
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 11)
	public void verifyEmailDataNotCalculateAsDistintValue() throws InterruptedException {

		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		sessionsearch = new SessionSearch(driver);
		SoftAssert sofassertion = new SoftAssert();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-49802");
		baseClass.stepInfo(
				"To verify that if Email data contained space before the '@' sign , it should not calculate two distinct values");

		baseClass.selectproject(Input.regressionConsilio1);
		String ingestionFullName = dataSets.isDataSetisAvailable(Input.IngestionEmailDataFolder);
		if (ingestionFullName != null) {
			baseClass.stepInfo(ingestionFullName + "Ingestion alredy published this project");
			int count = sessionsearch.MetaDataSearchInBasicSearch(Input.emailAuthorDomain, " @consilio.com");
			sessionsearch.addNewSearch();
			int count1 = sessionsearch.newMetaDataSearchInBasicSearch(Input.emailAuthorDomain, " @ consilio.com");
			if (count != 0) {
				if (count == count1) {
					sofassertion.assertEquals(count1, count);
					baseClass.passedStep(
							"It displays result correctly if Email data contained space before '@' sign . Also  if Email data contained space before and after the '@' sign , it not calculate two distinct values, it displays result correctly  ");
				} else {
					baseClass.failedStep("count is not equal");
				}
			} else {
				baseClass.failedStep("try another, project this project is not mapped domain values");
			}
		}
		baseClass.passedStep(
				"verified that if Email data contained space before the '@' sign , it should not calculate two distinct values");
		loginPage.logout();

	}

	/**
	 * @author Aathith
	 * @throws InterruptedException //@TestCase id: 49547 : Verify Count of Generate
	 *                              Searchable PDFs if 'Required PDF Generation' is
	 *                              TRUE and 'searchable PDF for TIFFs' is TRUE
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 12)
	public void verifySearchablePdfCount() throws InterruptedException {
		baseClass = new BaseClass(driver);

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
		baseClass.stepInfo("Select project");
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Test case Id: RPMXCON-49547 ");
		baseClass.stepInfo(
				"###  Verify Count of Generate Searchable PDFs if 'Required PDF Generation' is TRUE and 'searchable PDF for TIFFs' is TRUE ###");
		IngestionPage_Indium ingetion = new IngestionPage_Indium(driver);

		baseClass.stepInfo("Navigate to ingestion page.");
		ingetion.nativigateToIngestionViaButton();
		
		boolean status = ingetion.verifyIngestionpublish(Input.PDFGen_10Docs);
		if(!status) {
			ingetion.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,Input.ingestionType, "TRUE", Input.DATPPPDF10Docs, null,
					Input.TextPPPDF10Docs, null, Input.ImagePPPDF10docs,"select", null, null, null);
		}

			baseClass.stepInfo("Select ingestion type and specify source loaction.");
			ingetion.selectIngestionTypeAndSpecifySourceLocation(Input.overlayOnly, Input.sourceSystem, Input.sourceLocation,
					Input.PDFGen_10Docs);

			baseClass.stepInfo("Select DAT delimiters.");
			ingetion.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);

			baseClass.stepInfo("Select DAT source.");
			ingetion.selectDATSource(Input.newdateformat_5Docs, Input.prodBeg);

			baseClass.stepInfo("Select MP3 varient source.");
			ingetion.selectPDFSource(Input.PDF5DocsLst, false);

			ingetion.selectTIFFSource(Input.Images5DocsLst, false, true);

			baseClass.stepInfo("Select Date and Time format.");
			ingetion.selectDateAndTimeForamt(Input.dateFormat);

			baseClass.stepInfo("Click on next button.");
			ingetion.clickOnNextButton();

			baseClass.stepInfo("Click on preview and run button.");
			ingetion.clickOnPreviewAndRunButton();

			baseClass.stepInfo("Select all options from filter by dropdown.");
			ingetion.selectAllOptionsFromFilterByDropdown();

			baseClass.stepInfo("Create ingestion to cataloged stage");
			ingetion.ingestionCreationToCatalogedStage();

			baseClass.stepInfo("cataloged stage to Copied stage");
			ingetion.IngestionCatlogtoCopyingOrIndex(Input.PDFGen_10Docs);

			baseClass.stepInfo("Verify count of searchable pdf");
			ingetion.searchablePdfCountCheck();

		baseClass.passedStep(
				"Verified Count of Generate Searchable PDFs if 'Required PDF Generation' is TRUE and 'searchable PDF for TIFFs' is TRUE");
		loginPage.logout();

	}

	/**
	 * Author :Aathith date: NA Modified date: Modified by: Description : Verify
	 * that if "Generate Searchable PDF " check box is not selected in the TIFF
	 * section, Ingestion should generate successfully with TIFF images only
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 13)
	public void verifyTiffImageOnlyDisplayed() throws InterruptedException {

		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		sessionsearch = new SessionSearch(driver);
		docview = new DocViewPage(driver);

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-49491");
		baseClass.stepInfo(
				"Verified that if \"Generate Searchable PDF \" check box is not selected in the TIFF section, Ingestion should generate successfully with TIFF images only");

		baseClass.selectproject(Input.ingestionProjectName);
		String ingestionFullName = dataSets.isDataSetisAvailable("Tiff_Images");
		if (ingestionFullName != null) {
			dataSets.selectDataSetWithNameInDocView("Tiff_Images");
			docview.getImageTab().waitAndClick(10);
			driver.waitForPageToBeReady();
			if (docview.getDocViewImage().isElementAvailable(10)) {
				baseClass.passedStep("Tiff file only displayed");
			} else {
				baseClass.failedStep("verification failed");
			}
		}
		baseClass.passedStep(
				"Verified that if \"Generate Searchable PDF \" check box is not selected in the TIFF section, Ingestion should generate successfully with TIFF images only");
		loginPage.logout();

	}

	/**
	 * Author :Aathith date: NA Modified date: Modified by: Description : Verify
	 * that if PA ingested both Text's and TIFF's file,and the "Generate Searchable
	 * PDFs" option is set to False, then it should display TIFF in default viewer
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 14)
	public void VerifyTiffImageInDefautViewer() throws InterruptedException {

		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		sessionsearch = new SessionSearch(driver);
		docview = new DocViewPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-49511");
		baseClass.stepInfo(
				"Verify that if PA ingested both Text's and TIFF's file,and the \"Generate Searchable PDFs\" option is set to False, then it should display TIFF in default viewer");
		IngestionPage_Indium ingetion = new IngestionPage_Indium(driver);
		boolean status = ingetion.verifyIngestionpublish(Input.PDFGen_10Docs);
		if(!status) {
			ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,Input.ingestionType, "TRUE", Input.DATPPPDF10Docs, null,
					Input.TextPPPDF10Docs, null, Input.ImagePPPDF10docs,"select", null, null, null);
		}
			dataSets.selectDataSetWithNameInDocView(Input.PDFGen_10Docs);
			String name = docview.getDefaultViewerFileType().GetAttribute("xlink:href");
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
	@Test(alwaysRun = true, groups = { "regression" }, priority = 15)
	public void verifyPdfandTiffInDocView() throws InterruptedException {

		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		sessionsearch = new SessionSearch(driver);
		docview = new DocViewPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-49498");
		baseClass.stepInfo(
				"Verify that if PA ingested both native's and TIFF's file,and the \"Generate Searchable PDFs\" option is set to true, then PDF should be generated from the TIFF's only");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
		IngestionPage_Indium ingetion = new IngestionPage_Indium(driver);
		boolean status = ingetion.verifyIngestionpublish(Input.PDFGen_10Docs);
		if(!status) {
			ingetion.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,Input.ingestionType, "TRUE", Input.DATPPPDF10Docs, null,
					Input.TextPPPDF10Docs, null, Input.ImagePPPDF10docs,"select", null, null, null);
		}
			dataSets.selectDataSetWithNameInDocView(Input.PDFGen_10Docs);
			driver.waitForPageToBeReady();
			if (docview.getFileType().isElementAvailable(10)) {
				driver.waitForPageToBeReady();
				docview.waitforFileType();
				String filetype = docview.getFileType().getText();
				System.out.println(filetype);
				if (filetype.contains("PDF")) {
					baseClass.passedStep("PDF file only displayed in default viewer");
				} else {
					baseClass.failedStep("verification failed");
				}
			} else {
				baseClass.failedStep("file type is  not displayed");
			}
			docview.getImageTab().waitAndClick(10);
			driver.waitForPageToBeReady();
			if (docview.getDocViewImage().isElementAvailable(10)) {
				baseClass.passedStep("Tiff file displayed in Tiff Tab");
			} else {
				baseClass.failedStep("verification failed");
			}
		
		baseClass.passedStep("Verified that if PA ingested both native's and TIFF's file,"
				+ "and the \"Generate Searchable PDFs\" option is set to true, then PDF should be generated from the TIFF's only");
		loginPage.logout();

	}

	/**
	 * Author :Aathith date: NA Modified date: Modified by: Description : Verify
	 * that if PA ingested both PDF and TIFF's file and the "Generate Searchable
	 * PDFs" option is set to true, then PDF should be generated from the TIFF's
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 16)
	public void verifyJanTiffPdfandTiffInDocView() throws InterruptedException {

		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		sessionsearch = new SessionSearch(driver);
		docview = new DocViewPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-49499");
		baseClass.stepInfo("Verify that if PA ingested both PDF and TIFF's file"
				+ " and the \"Generate Searchable PDFs\" option is set to true, then PDF should be generated from the TIFF's");

		baseClass.selectproject(Input.ingestionProjectName);
		String ingestionFullName = dataSets.isDataSetisAvailable(Input.JanMultiPTIFF);
		if (ingestionFullName != null) {
			dataSets.selectDataSetWithNameInDocView(Input.JanMultiPTIFF);
			String filetype = docview.getDocView_TextFileType().getText().trim();
			if (filetype.isEmpty()) {
				baseClass.passedStep("PDF file in native formate displayed in default viewer");
			} else {
				baseClass.failedStep("verification failed");
			}
			docview.getImageTab().waitAndClick(10);
			driver.waitForPageToBeReady();
			if (docview.getDocview_DefaultTextArea().isElementAvailable(10)) {
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
	 * Author :Vijaya.Rani date:8/5/2022 Modified date: Modified by: Description :
	 * Verify that if PA ingested both TIFF's and Text's file,and the "Generate
	 * Searchable PDFs" option is set to true, then PDF should be generated from the
	 * TIFF's only. RPMXCON-49510
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 17)
	public void VerifySelectSearchablePDFTiffImageInDefautViewer() throws InterruptedException {

		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		sessionsearch = new SessionSearch(driver);
		docview = new DocViewPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-49510");
		baseClass.stepInfo(
				"Verify that if PA ingested both TIFF's and Text's file,and the 'Generate Searchable PDFs' option is set to true, then PDF should be generated from the TIFF's only.");

		IngestionPage_Indium ingetion = new IngestionPage_Indium(driver);
		boolean status = ingetion.verifyIngestionpublish(Input.PDFGen_10Docs);
		if(!status) {
			ingetion.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,Input.ingestionType, "TRUE", Input.DATPPPDF10Docs, null,
					Input.TextPPPDF10Docs, null, Input.ImagePPPDF10docs,"select", null, null, null);
		}
			dataSets.selectDataSetWithNameInDocView(Input.PDFGen_10Docs);
			String name = docview.getDefaultViewerFileType().GetAttribute("xlink:href");
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
	 * Author :Vijaya.Rani date: 9/5/2022 Modified date:NA Modified by:NA 
	 * Description :Verify that if PA ingested both PDF and TIFF's file, the "Generate Searchable
	 * PDFs" option is set to true, and if the Generation of the PDF from the TIFF's
	 * fails, then pre-existing PDF should be retained as the PDF file variant
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 18)
	public void verifyJanTiffPdfandnotTiffInDocView() throws InterruptedException {

		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		sessionsearch = new SessionSearch(driver);
		docview = new DocViewPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-49500");
		baseClass.stepInfo(
				"Verify that if PA ingested both PDF and TIFF's file,the 'Generate Searchable PDFs' option is set to true, and if the Generation of the PDF from the TIFF's fails,then pre-existing PDF should be retained as the PDF file variant");

		baseClass.selectproject(Input.ingestionProjectName);
		String ingestionFullName = dataSets.isDataSetisAvailable(Input.JanMultiPTIFF);
		if (ingestionFullName != null) {
			dataSets.selectDataSetWithNameInDocView(Input.JanMultiPTIFF);
			String filetype = docview.getDocView_TextFileType().getText().trim();
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
	 * Author :Aathith date: NA Modified date: Modified by: 
	 * Description : Verify that if "Generate Searchable PDFs" is TRUE, then Ingestion should generate successfully for Single page TIFF images.
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 19)
	public void verifyMarTiffPdfandTiffInDocView() throws InterruptedException {
		
		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		sessionsearch = new SessionSearch(driver);
		docview = new DocViewPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-49501");
		baseClass.stepInfo("Verify that if \"Generate Searchable PDFs\" is TRUE, then Ingestion should generate successfully for Single page TIFF images.");
		
		baseClass.selectproject(Input.ingestionProjectName);
		String ingestionFullName = dataSets.isDataSetisAvailable(Input.SinglePageTIFFFolder);
		if(ingestionFullName!=null) {
			dataSets.selectDataSetWithNameInDocView(Input.SinglePageTIFFFolder);
			driver.waitForPageToBeReady();
			docview.waitforFileType();
			String filetype=docview.getFileType().getText().trim();
			System.out.println(filetype);
			if(filetype.contains("PDF")) {
				baseClass.passedStep("PDF file only displayed in default viewer");
			}else {
				baseClass.failedStep("verification failed");
			}
			docview.getImageTab().waitAndClick(10);
			driver.waitForPageToBeReady();
			if(docview.getDocViewImage().isElementAvailable(10)) {
				baseClass.passedStep("Tiff file displayed in Tiff Tab");
			}else {
				baseClass.failedStep("verification failed");
			}
		}
		baseClass.passedStep("Verified that if \"Generate Searchable PDFs\" is TRUE, then Ingestion should generate successfully for Single page TIFF images.");
		loginPage.logout();
		
	}
	/**
	 * Author :Aathith date: NA Modified date: Modified by: 
	 * Description : To verify that for image based document Sightline should receive 'RequirePDFGeneration' as set to 'true', 
	 * by ICE and 'RequirePDFGeneration' metadata should be displays in Doc View
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 20)
	public void verifyMetaDataRequiredPDFGenereationIsTrue() throws InterruptedException {
		
		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		sessionsearch = new SessionSearch(driver);
		docview = new DocViewPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-49364");
		baseClass.stepInfo("To verify that for image based document Sightline should receive 'RequirePDFGeneration' as set to 'true',"
				+ " by ICE and 'RequirePDFGeneration' metadata should be displays in Doc View");
		
		baseClass.selectproject(Input.ingestionProjectName);
		String ingestionFullName = dataSets.isDataSetisAvailable(Input.GNon_searchable_PDF_Load_file);
		if(ingestionFullName!=null) {
			dataSets.selectDataSetWithNameInDocView(Input.GNon_searchable_PDF_Load_file);
			driver.waitForPageToBeReady();
			String value = docview.getMetadataFieldValueText("RequirePDFGeneration").getText().trim();
			if(value.equals("1")) {
				baseClass.passedStep("Meta data is displayed in Doc View 'RequirePDFGeneration' as set to 'true',");
			}else {
				baseClass.failedStep("verification failed");
			}
			
		}
		baseClass.passedStep("verified that for image based document Sightline should receive 'RequirePDFGeneration' as set to 'true',"
				+ " by ICE and 'RequirePDFGeneration' metadata should be displays in Doc View");
		loginPage.logout();
		
	}
	
	/**
	 * Author :Vijaya.Rani date: 9/5/2022 Modified date: Modified by: Description :
	 * Verify that if "Generate Searchable PDFs" is TRUE, then Ingestion should
	 * generate successfully for Multi-page TIFF images.'RPMXCON-49502'
	 * 
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 21)
	public void verifyJanMultiTiffPdfandTiffInDocView() throws InterruptedException {

		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		DocViewPage docview = new DocViewPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-49502");
		baseClass.stepInfo(
				"Verify that if 'Generate Searchable PDFs' is TRUE, then Ingestion should generate successfully for Multi-page TIFF images.");

		baseClass.selectproject(Input.ingestionProjectName);
		String ingestionFullName = dataSets.isDataSetisAvailable(Input.JanMultiPTIFF);
		if (ingestionFullName != null) {
			dataSets.selectDataSetWithNameInDocView(Input.JanMultiPTIFF);
			driver.waitForPageToBeReady();
			docview.getFileType().isElementAvailable(3);
			driver.waitForPageToBeReady();
			String filetype = docview.getDocView_TextFileType().getText().trim();
			if (filetype.contains("TIFF")) {
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
	* Author :Aathith Test Case Id:RPMXCON-49567 
	* Description :Verify Ingestion with Email metadata if 'NamesAndAddresses' with different format
	*
	*/
	@Test(enabled = true, groups = { "regression" }, priority = 22)
	public void verifyingNamesAndAddressesMetadataInDocListPage() throws InterruptedException {
		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
	baseClass.stepInfo("Test case Id: RPMXCON-49567");
	baseClass.stepInfo("Verify Ingestion with Email metadata if 'NamesAndAddresses' with different format");

	baseClass.selectproject(Input.ingestionProjectName);

	IngestionPage_Indium ingestionPage = new IngestionPage_Indium(driver);
	boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
	System.out.println(status);
	
	if (!status) {
	String ingestionType = Input.ingestionProjectName;
	baseClass.stepInfo("Edit of addonly saved ingestion with mapping field selection");
	ingestionPage.IngestionRegressionForDifferentDAT(Input.GD994NativeTextForProductionFolder, ingestionType,
	Input.sourceSystem, Input.datFormatFile, "DAT4_STC_NativesEmailData NEWID.lst",
	"DAT4_STC_TextEmailData NEWID.lst", null, null, null, null, null, null);
	}
	
	String[] addEmailColumn = { "EmailAuthorNameAndAddress"};
	SessionSearch sessionSearch = new SessionSearch(driver);
	sessionSearch.SearchMetaData(Input.metadataIngestion,"8D46_GD_994_Native_Text_ForProduction_20220413075857083");
	sessionSearch.ViewInDocList();

	DocListPage doc = new DocListPage(driver);
	doc.SelectColumnDisplayByRemovingExistingOnes(addEmailColumn);
	doc.emailAuthorNameParentheses("EMAILAUTHORNAMEANDADDRESS");
	doc.verifyingEmailMetadataInDocListPage("EMAILAUTHORNAMEANDADDRESS");
	
	baseClass.passedStep("Verified Ingestion with Email metadata if 'NamesAndAddresses' with different format");
	loginPage.logout();
	}
	/**
	 * @author Aathith
	 * @throws InterruptedException 
	 * ////@TestCase id: 49550 : Verify that in Ingestion Overlay if 'Generate Searchable PDFs'
	 *  is selected in TIFF section, then PDF should be generated from the TIFF's
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 23)
	public void verifySearchablePdfTiffDocView() throws InterruptedException {
		baseClass = new BaseClass(driver);
		String projectName = Input.ingestionPrjt;
		String ingestionType = Input.overlayOnly;
		String sourceSystem = Input.sourceSystem;
		String sourceLocation = Input.sourceLocation;
		String sourceFolder = Input.TiffImagesFolder;
		String fieldSeperator = Input.fieldSeperator;
		String textQualifier = Input.textQualifier;
		String multiValue = Input.multiValue;
		String datLoadFile = Input.DATFile3;
		String documentKey = Input.docId;
		String dateFormat = Input.dateFormat;

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
		baseClass.stepInfo("Select project");
		//baseClass.selectproject(projectName);
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Test case Id: RPMXCON-49550 ");
		baseClass.stepInfo(
				"###  Verify that in Ingestion Overlay if 'Generate Searchable PDFs' is selected in TIFF section, then PDF should be generated from the TIFF's ###");
		IngestionPage_Indium ingetion = new IngestionPage_Indium(driver);

		baseClass.stepInfo("Navigate to ingestion page.");
		ingetion.nativigateToIngestionViaButton();
		
		//overlay
		boolean status = ingetion.verifyIngestionpublish(sourceFolder);
		if (status) {

			baseClass.stepInfo("Select ingestion type and specify source loaction.");
			ingetion.selectIngestionTypeAndSpecifySourceLocation(ingestionType, sourceSystem, sourceLocation,
					sourceFolder);

			baseClass.stepInfo("Select DAT delimiters.");
			ingetion.addDelimitersInIngestionWizard(fieldSeperator, textQualifier, multiValue);

			baseClass.stepInfo("Select DAT source.");
			ingetion.selectDATSource(datLoadFile, documentKey);
			
			ingetion.getTIFFSearchablePDFCheckBox().Click();

			baseClass.stepInfo("Select Date and Time format.");
			ingetion.selectDateAndTimeForamt(dateFormat);

			baseClass.stepInfo("Click on next button.");
			ingetion.clickOnNextButton();

			baseClass.stepInfo("Click on preview and run button.");
			ingetion.clickOnPreviewAndRunButton();
			
			baseClass.stepInfo("Select all options from filter by dropdown.");
			ingetion.selectAllOptionsFromFilterByDropdown();
			
			ingetion.getIngestionSatatusAndPerform();
		}
		
		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		DocViewPage docview = new DocViewPage(driver);
		String ingestionFullName = dataSets.isDataSetisAvailable(Input.TiffImagesFolder);
		if(ingestionFullName!=null) {
			dataSets.selectDataSetWithNameInDocView(Input.TiffImagesFolder);
			driver.waitForPageToBeReady();
			docview.waitforFileType();
			String filetype=docview.getFileType().getText().trim();
			System.out.println(filetype);
			if(filetype.contains("PDF")) {
				baseClass.passedStep("PDF file only displayed in default viewer");
			}else {
				baseClass.failedStep("verification failed");
			}
			docview.getImageTab().waitAndClick(10);
			driver.waitForPageToBeReady();
			if(docview.getDocViewImage().isElementAvailable(10)) {
				baseClass.passedStep("Tiff file displayed in Tiff Tab");
			}else {
				baseClass.failedStep("verification failed");
			}
		}
		
		baseClass.passedStep(
				"Verified that in Ingestion Overlay if 'Generate Searchable PDFs' is selected in TIFF section, then PDF should be generated from the TIFF's");
		loginPage.logout();

	}
	/**
	 * Author :Aathith date: NA Modified date: Modified by: 
	 * Description : To Verify In Ingestions, if the user tries to unpublish non-Nexidia indexed audio files, the unpublish should be successful.
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 24)
	public void verifyUnpublishIndexedAudioFile() throws InterruptedException {
		
		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		sessionsearch = new SessionSearch(driver);
		docview = new DocViewPage(driver);
		IngestionPage_Indium ingestion = new IngestionPage_Indium(driver);
		String BasicSearchName = "search"+Utility.dynamicNameAppender();
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-48260");
		baseClass.stepInfo("To Verify In Ingestions, if the user tries to unpublish non-Nexidia indexed audio files, the unpublish should be successful.");
		
		baseClass.selectproject(Input.ingestionProjectName);
		String ingestionFullName = dataSets.isDataSetisAvailable("AK_Native_PDF_MP3_Transcript_ForProduction");
		if(ingestionFullName!=null) {
			// Search ingestionName
			sessionsearch.basicSearchWithMetaDataQuery(ingestionFullName, "IngestionName");
			sessionsearch.getPureHitAddButton().isElementAvailable(2);
			sessionsearch.getPureHitAddButton().waitAndClick(5);
			// Saved the My SavedSearch
			sessionsearch.saveSearch(BasicSearchName);
			// Go to UnpublishPage
			ingestion.navigateToUnPublishPage();
			ingestion.unpublish(BasicSearchName);
			
		}
		baseClass.passedStep("Verified In Ingestions, if the user tries to unpublish non-Nexidia indexed audio files, the unpublish should be successful.");
		loginPage.logout();
		
	}
	/**
	 * @author Aathith
	 * @throws InterruptedException 
	 * //@TestCase id: 48277 : To Verify unpublish for Overlay Ingestion.
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 25)
	public void verifyUnpublishOverLayIngestion() throws InterruptedException {
		
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Select project");
		baseClass.selectproject(Input.ingestionProjectName);
		driver.waitForPageToBeReady();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-48277 ");
		baseClass.stepInfo(
				"###  To Verify unpublish for Overlay Ingestion. ###");
		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		sessionsearch = new SessionSearch(driver);
		docview = new DocViewPage(driver);
		IngestionPage_Indium ingetion = new IngestionPage_Indium(driver);
		String BasicSearchName = "search"+Utility.dynamicNameAppender();

		baseClass.stepInfo("Navigate to ingestion page.");
		ingetion.nativigateToIngestionViaButton();
		boolean status = ingetion.verifyIngestionpublish(Input.PDFGen_10Docs);
		if(!status) {
			ingetion.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,Input.ingestionType, "TRUE", Input.DATPPPDF10Docs, null,
					Input.TextPPPDF10Docs, null, Input.ImagePPPDF10docs,"select", null, null, null);
		}

			baseClass.stepInfo("Select ingestion type and specify source loaction.");
			ingetion.selectIngestionTypeAndSpecifySourceLocation(Input.overlayOnly, null, Input.sourceLocation,
					Input.PDFGen_10Docs);

			baseClass.stepInfo("Select DAT delimiters.");
			ingetion.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);

			baseClass.stepInfo("Select DAT source.");
			//Input.newdateformat_5Docs, Input.prodBeg used for dat file name and Document Key
			ingetion.selectDATSource(Input.newdateformat_5Docs, Input.prodBeg);

			baseClass.stepInfo("Select Pdf varient source.");
			//Input.PDF5DocsLst is used for pdf file Name
			ingetion.selectPDFSource(Input.PDF5DocsLst, false);
			
			baseClass.stepInfo("Select Tiff varient source.");
			//Input.Images5DocsLst used for Image loadfile name 
			ingetion.selectTIFFSource(Input.Images5DocsLst, false, true);

			baseClass.stepInfo("Select Date and Time format.");
			//Input.dateFormat used for choosing the stranded data format
			ingetion.selectDateAndTimeForamt(Input.dateFormat);

			baseClass.stepInfo("Click on next button.");
			ingetion.clickOnNextButton();

			baseClass.stepInfo("Click on preview and run button.");
			ingetion.clickOnPreviewAndRunButton();
			
			baseClass.stepInfo("Select all options from filter by dropdown.");
			ingetion.selectAllOptionsFromFilterByDropdown();
			
			ingetion.getIngestionSatatusAndPerform();

		
		String ingestionFullName = dataSets.isDataSetisAvailable(Input.PDFGen_10Docs);
		if(ingestionFullName!=null) {
			// Search ingestionName
			sessionsearch.basicSearchWithMetaDataQuery(ingestionFullName, "IngestionName");
			sessionsearch.bulkRelease(Input.securityGroup);
			// Saved the My SavedSearch
			sessionsearch.saveSearch(BasicSearchName);
			// Go to UnpublishPage
			ingetion.navigateToUnPublishPage();
			ingetion.unpublish(BasicSearchName);
			
		}

		baseClass.passedStep(
				"Verified unpublish for Overlay Ingestion.");
		loginPage.logout();

	}
	/**
	 * @author Aathith
	 * @throws InterruptedException 
	 * //@TestCase id: 46894 : Verify the overlay Ingestion for Audio Documents against International English language pack
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 26)
	public void verifyAudioDocumentInternationEnglish() throws InterruptedException {
		
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Select project");
		baseClass.selectproject(Input.ingestionProjectName);
		driver.waitForPageToBeReady();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-46894 ");
		baseClass.stepInfo(
				"### Verify the overlay Ingestion for Audio Documents against International English language pack. ###");
		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		sessionsearch = new SessionSearch(driver);
		docview = new DocViewPage(driver);
		IngestionPage_Indium ingetion = new IngestionPage_Indium(driver);

		baseClass.stepInfo("Navigate to ingestion page.");
		ingetion.nativigateToIngestionViaButton();
		boolean status = ingetion.verifyIngestionpublish(Input.AutomationAllSources);
		System.out.println(status);

		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("addonly  ingestion with mapping field selection");
			String ingestionType="Add Only";
			ingetion.IngestionRegressionForDifferentDAT(Input.AllSourcesFolder,ingestionType, Input.sourceSystem, Input.DATFile1,
					Input.NativeFile, null,null, null, null, null, null, null);
		}

			baseClass.stepInfo("Select ingestion type and specify source loaction.");
			//Input.overlayOnly, null, Input.sourceLocation,Input.ingestionAutomationAllSource used for ingestionType, sourceLoaction ,Ingestion name
			ingetion.selectIngestionTypeAndSpecifySourceLocation(Input.overlayOnly, null, Input.sourceLocation,
					Input.ingestionAutomationAllSource);

			baseClass.stepInfo("Select DAT delimiters.");
			ingetion.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);

			baseClass.stepInfo("Select DAT source.");
			//Input.BEbomDat, Input.prodBeg used for dat file name and document key
			ingetion.selectDATSource(Input.BEbomDat, Input.prodBeg);

			baseClass.stepInfo("Select MP3 varient source.");
			//Input.MP3_OverlayLst is used mp3 load file name
			ingetion.selectMP3VarientSource(Input.MP3_OverlayLst, false);
			
			baseClass.stepInfo("Select Date and Time format.");
			ingetion.selectDateAndTimeForamt(Input.dateFormat);

			baseClass.stepInfo("Click on next button.");
			ingetion.clickOnNextButton();

			baseClass.stepInfo("Click on preview and run button.");
			ingetion.clickOnPreviewAndRunButton();
			
			baseClass.stepInfo("Select all options from filter by dropdown.");
			ingetion.selectAllOptionsFromFilterByDropdown();
			
			ingetion.getIngestionSatatusAndPerformUptoCopiedStage();
			
			ingetion.verifyLanguageIsSelectable(Input.audioLanguage);

		
		
		baseClass.passedStep(
				"Verified the overlay Ingestion for Audio Documents against International English language pack");
		loginPage.logout();

	}
	
	/**
	 * Author :Baskar date: 04/05/2022 Modified date: NA Modified by: NA
	 * Description:Verify Email metadata automatically released to Security Group
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 27)
	public void verifyEmailMetadataWithIngestionName() throws Exception {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.selectproject(Input.regressionRun);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);

		IngestionPage_Indium ingestionPage = new IngestionPage_Indium(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		ProjectPage projectPage = new ProjectPage(driver);
		SecurityGroupsPage securityGroupPage = new SecurityGroupsPage(driver);
		SessionSearch	sessionSearch = new SessionSearch(driver);
		UserManagement user = new UserManagement(driver);
		DocListPage doclist = new DocListPage(driver);

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
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
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
	@Test(enabled = true, groups = { "regression" }, priority = 28)
	public void verifyMoreThanHourAudioDocs() throws InterruptedException {
		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		docview = new DocViewPage(driver);
		SoftAssert softAssertion = new SoftAssert();
		String audioDocsIngestionName = "41AD_SSAudioSpeech_Transcript_20220321085634270";
		String moreThanHour = Input.ingestionOneHour;
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-48241");
		baseClass.stepInfo("To Verify for Audio longer than 1 hour, in Docview, \"Zoom In/Zoom Out\" should be "
				+ "available so user could switch between the short and long wave forms.");

		baseClass.selectproject("Indium_Regressionrun");
		String ingestionFullName = dataSets.isDataSetisAvailable(audioDocsIngestionName);
		if (ingestionFullName != null) {
			driver.waitForPageToBeReady();
			sessionSearch.MetaDataSearchInBasicSearch("IngestionName", audioDocsIngestionName);
			sessionSearch.viewInDocView();
			baseClass.waitForElement(docview.getDocumentByid(moreThanHour));
			docview.getDocumentByid(moreThanHour).waitAndClick(5);

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
	@Test(enabled = true, groups = { "regression" }, priority = 29)
	public void verifyLessThanHourAudioDocs() throws InterruptedException {
		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		SoftAssert softAssertion = new SoftAssert();
		docview = new DocViewPage(driver);
		String audioDocsIngestionName = "41AD_SSAudioSpeech_Transcript_20220321085634270";
		String moreThanHour = Input.ingestionLessThanHour;
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("Test case Id: RPMXCON-48239");
		baseClass.stepInfo(
				"To verify for Audio less than 1 hour, in Docview, \"Zoom In/Zoom Out\" is disabled or hidden.");

		baseClass.selectproject("Indium_Regressionrun");
		String ingestionFullName = dataSets.isDataSetisAvailable(audioDocsIngestionName);
		if (ingestionFullName != null) {
			sessionSearch.MetaDataSearchInBasicSearch("IngestionName", audioDocsIngestionName);
			sessionSearch.viewInDocView();
			baseClass.waitForElement(docview.getDocumentByid(moreThanHour));
			docview.getDocumentByid(moreThanHour).waitAndClick(5);

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
	@Test(enabled = true, dataProvider = "userDataSets", groups = { "regression" }, priority =30)
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
	@Test(enabled = true, groups = { "regression" }, priority =31)
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
	@Test(enabled = true, groups = { "regression" }, priority =32)
	public void validateExportingDatasetAsRMU() throws InterruptedException, IOException {

		loginPage.logout();
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
	@Test(enabled = true, dataProvider = "userDataSetsSAAndDA", groups = { "regression" }, priority =33)
	public void validateExportingDatasetAsSAandDA(String userName, String password, String fromRole)
			throws InterruptedException, IOException {

		loginPage.logout();
		ingestionPage = new IngestionPage_Indium(driver);
		DataSets datasets = new DataSets(driver);
		
		// login as user
		loginPage.loginToSightLine(userName, password,fromRole);
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
