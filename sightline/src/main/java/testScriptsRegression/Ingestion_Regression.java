package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
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
import pageFactory.ProjectFieldsPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.TallyPage;
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

	@BeforeMethod(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Ex" + "ecution for prerequisite");

		Input input = new Input();
		input.loadEnvConfig();

		driver = new Driver();
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);

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
		String projectName = "AutomationIngestionProject";
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

		baseClass.stepInfo("Select project");
		baseClass.selectproject(projectName);

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
		String projectName = Input.ingestionProjectName;
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

		baseClass.stepInfo("Select project");
		baseClass.selectproject(projectName);

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
		String projectName = "AutomationRegressionBackup";
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

		baseClass.stepInfo("Select project");
		baseClass.selectproject(projectName);

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

		SessionSearch search = new SessionSearch(driver);
		search.SearchMetaData("EmailCCNamesAndAddresses", Input.EmailAuthourName);
		search.verifyTheCountOfDocumentForMetaData();

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		search.SearchMetaData("EmailCCNamesAndAddresses", Input.EmailAuthourName);
		search.verifyTheCountOfDocumentForMetaData();
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

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

		SessionSearch search = new SessionSearch(driver);
		search.SearchMetaData("EmailCCNames", Input.EmailAuthourName);
		search.verifyTheCountOfDocumentForMetaData();

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		search.SearchMetaData("EmailCCNames", Input.EmailAuthourName);
		search.verifyTheCountOfDocumentForMetaData();
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

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

		baseClass.stepInfo("Test case Id: RPMXCON-49558");
		baseClass.stepInfo("Verify Email metadata in DocList and in DocView");

		String foldername = "IngestionFolder" + Utility.dynamicNameAppender();
		String folderTheadMap = "IngestionFolderTheadMap" + Utility.dynamicNameAppender();
		String[] addEmailColumn = { "EmailAuthorName", "EmailAuthorAddress", "EmailToNames", "EmailToAddresses",
				"EmailCCNames", "EmailCCAddresses", "EmailBCCNames", "EmailBCCAddresses" };

		tagandfolder.CreateFolder(foldername, Input.securityGroup);
		tagandfolder.CreateFolder(folderTheadMap, Input.securityGroup);

		baseClass.selectproject("AutomationRegressionBackup");
		String ingestionFullName = dataSets.isDataSetisAvailable("IngestionEmailData");
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
			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password, "AutomationRegressionBackup");
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

		baseClass.stepInfo("Test case Id: RPMXCON-49802");
		baseClass.stepInfo(
				"To verify that if Email data contained space before the '@' sign , it should not calculate two distinct values");

		baseClass.selectproject("AutomationRegressionBackup");
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
		String projectName = Input.ingestionProjectName;
		String ingestionType = Input.overlayOnly;
		String sourceSystem = Input.sourceSystem;
		String sourceLocation = Input.sourceLocation;
		String sourceFolder = Input.PDFGen_10Docs;
		String fieldSeperator = Input.fieldSeperator;
		String textQualifier = Input.textQualifier;
		String multiValue = Input.multiValue;
		String datLoadFile = Input.newdateformat_5Docs;
		String documentKey = Input.prodBeg;
		String pdfLoadFile = Input.PDF5DocsLst;
		String tiffLoadFile = Input.Images5DocsLst;
		String dateFormat = Input.dateFormat;

		baseClass.stepInfo("Select project");
		baseClass.selectproject(projectName);
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Test case Id: RPMXCON-49547 ");
		baseClass.stepInfo(
				"###  Verify Count of Generate Searchable PDFs if 'Required PDF Generation' is TRUE and 'searchable PDF for TIFFs' is TRUE ###");
		IngestionPage_Indium ingetion = new IngestionPage_Indium(driver);

		baseClass.stepInfo("Navigate to ingestion page.");
		ingetion.nativigateToIngestionViaButton();

		boolean status = ingetion.verifyIngestionpublish(sourceFolder);
		if (status) {

			baseClass.stepInfo("Select ingestion type and specify source loaction.");
			ingetion.selectIngestionTypeAndSpecifySourceLocation(ingestionType, sourceSystem, sourceLocation,
					sourceFolder);

			baseClass.stepInfo("Select DAT delimiters.");
			ingetion.addDelimitersInIngestionWizard(fieldSeperator, textQualifier, multiValue);

			baseClass.stepInfo("Select DAT source.");
			ingetion.selectDATSource(datLoadFile, documentKey);

			baseClass.stepInfo("Select MP3 varient source.");
			ingetion.selectPDFSource(pdfLoadFile, false);

			ingetion.selectTIFFSource(tiffLoadFile, false, true);

			baseClass.stepInfo("Select Date and Time format.");
			ingetion.selectDateAndTimeForamt(dateFormat);

			baseClass.stepInfo("Click on next button.");
			ingetion.clickOnNextButton();

			baseClass.stepInfo("Click on preview and run button.");
			ingetion.clickOnPreviewAndRunButton();

			baseClass.stepInfo("Select all options from filter by dropdown.");
			ingetion.selectAllOptionsFromFilterByDropdown();

			baseClass.stepInfo("Create ingestion to cataloged stage");
			ingetion.ingestionCreationToCatalogedStage();

			baseClass.stepInfo("cataloged stage to Copied stage");
			ingetion.IngestionCatlogtoCopyingOrIndex(dateFormat);

			baseClass.stepInfo("Verify count of searchable pdf");
			ingetion.searchablePdfCountCheck();
		}

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

		baseClass.stepInfo("Test case Id: RPMXCON-49511");
		baseClass.stepInfo(
				"Verify that if PA ingested both Text's and TIFF's file,and the \"Generate Searchable PDFs\" option is set to False, then it should display TIFF in default viewer");

		baseClass.selectproject(Input.ingestionProjectName);
		String ingestionFullName = dataSets.isDataSetisAvailable(Input.PDFGen_10Docs);
		if (ingestionFullName != null) {
			dataSets.selectDataSetWithNameInDocView(Input.PDFGen_10Docs);
			String name = docview.getDefaultViewerFileType().GetAttribute("xlink:href");
			System.out.println(name);
			if (name.contains("image")) {
				baseClass.passedStep("Tiff file only displayed in default viewer");
			} else {
				baseClass.failedStep("verification failed");
			}
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

		baseClass.selectproject(Input.ingestionProjectName);
		String ingestionFullName = dataSets.isDataSetisAvailable(Input.PDFGen_10Docs);
		if (ingestionFullName != null) {
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

		baseClass.stepInfo("Test case Id: RPMXCON-49510");
		baseClass.stepInfo(
				"Verify that if PA ingested both TIFF's and Text's file,and the 'Generate Searchable PDFs' option is set to true, then PDF should be generated from the TIFF's only.");

		baseClass.selectproject(Input.ingestionProjectName);
		String ingestionFullName = dataSets.isDataSetisAvailable(Input.PDFGen_10Docs);
		if (ingestionFullName != null) {
			dataSets.selectDataSetWithNameInDocView(Input.PDFGen_10Docs);
			String name = docview.getDefaultViewerFileType().GetAttribute("xlink:href");
			System.out.println(name);
			if (name.contains("image")) {
				baseClass.passedStep("Tiff file only displayed in default viewer");
			} else {
				baseClass.failedStep("verification failed");
			}
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
			String name = docview.getDefaultViewerFileType().GetAttribute("xlink:href");
			System.out.println(name);
			if (name == null) {
				baseClass.passedStep("Tiff file not converted as pdf");
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
	* Author :Aathith Test Case Id:RPMXCON-49567 
	* Description :Verify Ingestion with Email metadata if 'NamesAndAddresses' with different format
	*
	*/
	@Test(enabled = true, groups = { "regression" }, priority = 22)
	public void verifyingNamesAndAddressesMetadataInDocListPage() throws InterruptedException {
		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		
	baseClass.stepInfo("Test case Id: RPMXCON-49567");
	baseClass.stepInfo("Verify Ingestion with Email metadata if 'NamesAndAddresses' with different format");

	baseClass.selectproject(Input.ingestionPrjt);

	IngestionPage_Indium ingestionPage = new IngestionPage_Indium(driver);
	boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
	System.out.println(status);
	
	if (!status) {
	String ingestionType = Input.ingestionType;
	baseClass.stepInfo("Edit of addonly saved ingestion with mapping field selection");
	ingestionPage.IngestionRegressionForDifferentDAT(Input.GD994NativeTextForProductionFolder, ingestionType,
	Input.sourceSystem, Input.datFormatFile, "DAT4_STC_NativesEmailData NEWID.lst",
	"DAT4_STC_TextEmailData NEWID.lst", null, null, null, null, null, null);
	}
	
	String[] addEmailColumn = { "EmailAuthorNameAndAddress"};
	SessionSearch sessionSearch = new SessionSearch(driver);
	sessionSearch.SearchMetaData(Input.metadataIngestion, Input.nativeFileName);
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

		baseClass.stepInfo("Select project");
		baseClass.selectproject(projectName);
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
		
		//Input.PDFGen_10Docs is used for Ingestion Name
		boolean status = ingetion.verifyIngestionpublish(Input.PDFGen_10Docs);
		if (status) {

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

		}
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
	 * Author :Aathith date: 10/5/2022 Modified date: Modified by: Description
	 * :To verify that after Text overlay, if there are other file variants , then DocView follow the set precedence and 
	 * Text will reflect the overlaid text. 'RPMXCON-48607'
	 * 
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 27)
	public void verifyOverlayTheDocViewTextWillReflectOverlaidText() throws InterruptedException {

		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		DocListPage docList= new DocListPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView=new DocViewPage(driver);
		String BasicSearchName = "Search" + Utility.dynamicNameAppender();
		
		baseClass.stepInfo("Test case Id: RPMXCON-48607");
		baseClass.stepInfo(
				"To verify that after Text overlay, if there are other file variants , then DocView follow the set precedence and Text will reflect the overlaid text.");

		baseClass.selectproject(Input.ingestionProjectName);

		String ingestionType=Input.ingestionType;
		IngestionPage_Indium ingestionPage = new IngestionPage_Indium(driver);
		boolean status = ingestionPage.verifyIngestionpublish(Input.PP_PDFGen_10Docs);
		System.out.println(status);

		if (status == false) {

			ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,ingestionType, "TRUE", Input.DATPPPDF10Docs, null,
					Input.TextPPPDF10Docs, null, Input.ImagePPPDF10docs,"select", null, null, null);
			
		}
		dataSets.selectDataSetWithName(Input.PP_PDFGen_10Docs);
		String docId=docList.getDocumetId();
		sessionSearch.basicSearchWithMetaDataQuery(docId, "DocID");
		sessionSearch.saveSearch(BasicSearchName);

		// Go to UnpublishPage
		ingestionPage.navigateToUnPublishPage();
		ingestionPage.unpublish(BasicSearchName);
		
		baseClass.stepInfo("Navigate to ingestion page.");
		driver.scrollPageToTop();
		ingestionPage.nativigateToIngestionViaButton();

		boolean status1 = ingestionPage.verifyIngestionpublish(Input.PDFGen_10Docs);
		if (status1) {

			baseClass.stepInfo("Select ingestion type and specify source loaction.");
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.overlayOnly, null, Input.sourceLocation,
					Input.PDFGen_10Docs);

			baseClass.stepInfo("Select DAT delimiters.");
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);

			baseClass.stepInfo("Select DAT source.");
			ingestionPage.selectDATSource(Input.newdateformat_5Docs, Input.prodBeg);

			ingestionPage.selectTextSource(Input.TextPPPDF10Docs, false);

			baseClass.stepInfo("Select Date and Time format.");
			ingestionPage.selectDateAndTimeForamt(Input.dateFormat);

			baseClass.stepInfo("Click on next button.");
			ingestionPage.clickOnNextButton();

			baseClass.stepInfo("Click on preview and run button.");
			ingestionPage.clickOnPreviewAndRunButton();

			baseClass.stepInfo("Select all options from filter by dropdown.");
			ingestionPage.selectAllOptionsFromFilterByDropdown();

			ingestionPage.removeCatalogError();
			ingestionPage.getIngestionSatatusAndPerform();
		}
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

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {

			Utility bc = new Utility(driver);
			bc.screenShot(result);
			try { // if any tc failed and dint logout!
				loginPage.logoutWithoutAssert();
				loginPage.quitBrowser();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		System.out.println("Executed :" + result.getMethod().getMethodName());

	}

	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			// loginPage.quitBrowser();
		} finally {
			//loginPage.closeBrowser();
			LoginPage.clearBrowserCache();
		}
	}
}
