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

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DataSets;
import pageFactory.DocExplorerPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.IngestionPage_Indium;
import pageFactory.LoginPage;
import pageFactory.ManageAssignment;
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
	 * //@TestCase id:50755 : Project level exported dataset details - Validate "Not Processed and Not Loaded" tab details for a project having Uploaded ingestions.
	 * @Description: Project level exported dataset details - Validate "Not Processed and Not Loaded" tab details for a project having Uploaded ingestions.
	 */
	//@Test(alwaysRun = true, groups = { "regression" }, priority = 1)
	public void verifyProjectLvelExportedDataSetWithExpFields() {
		baseClass = new BaseClass(driver);
		String location = "C:\\\\BatchPrintFiles\\\\downloads";
		String sheetName = "Not Processed and Not Loaded";
		String fieldName = "Dataset Name";
		String fieldName2 = "File Name";
		String fieldName3 = "Exclusion Reason";
		baseClass.stepInfo("Test case Id: RPMXCON-50755 Sprint 12");
		baseClass.stepInfo("### Project level exported dataset details - Validate 'Not Processed and Not Loaded' tab details for a project having Uploaded ingestions ###");
		dataSets = new DataSets(driver);
		
		baseClass.stepInfo("Navigate data sets page.");
		dataSets.navigateToDataSetsPage();
		
		baseClass.stepInfo("Perform export data sets.");
		dataSets.exportDataSets();
		
		baseClass.stepInfo("Download exported file.");
		dataSets.downloadExportedFile();
		
		baseClass.stepInfo("Verify '"+fieldName+"' Field Displayed Of Downlodeded Excel File.");
		dataSets.verifyFieldDisplayedOfDownlodedExcelFile(location,sheetName,fieldName);
		
		baseClass.stepInfo("Verify '"+fieldName2+"' Field Displayed Of Downlodeded Excel File.");
		dataSets.verifyFieldDisplayedOfDownlodedExcelFile(location,sheetName,fieldName2);
		
		baseClass.stepInfo("Verify '"+fieldName3+"' Field Displayed Of Downlodeded Excel File.");
		dataSets.verifyFieldDisplayedOfDownlodedExcelFile(location,sheetName,fieldName3);
	}
	
	/**  
	 * @author Gopinath
	 * //@TestCase id:50756 : Project level exported dataset details - Validate "Not Processed and Not Loaded" tab details for an existing project having mapped ingestions and uploaded datasets.
	 * @Description: Project level exported dataset details - Validate "Not Processed and Not Loaded" tab details for an existing project having mapped ingestions and uploaded datasets
	 */
	//@Test(alwaysRun = true, groups = { "regression" }, priority = 2)
	public void verifyProjectLvelExportedDataSetsExistingProject() {
		baseClass = new BaseClass(driver);
		String location = "C:\\\\BatchPrintFiles\\\\downloads";
		String sheetName = "Not Processed and Not Loaded";
		String fieldName = "Dataset Name";
		String fieldName2 = "File Name";
		String fieldName3 = "Exclusion Reason";
		baseClass.stepInfo("Test case Id: RPMXCON-50756 Sprint 12");
		baseClass.stepInfo("### Project level exported dataset details - Validate 'Not Processed and Not Loaded' tab details for an existing project having mapped ingestions and uploaded datasets ###");
		dataSets = new DataSets(driver);
		
		baseClass.stepInfo("Navigate data sets page.");
		dataSets.navigateToDataSetsPage();
		
		baseClass.stepInfo("Perform export data sets.");
		dataSets.exportDataSets();
		
		baseClass.stepInfo("Download exported file.");
		dataSets.downloadExportedFile();
		
		baseClass.stepInfo("Verify '"+fieldName+"' Field Displayed Of Downlodeded Excel File.");
		dataSets.verifyFieldDisplayedOfDownlodedExcelFile(location,sheetName,fieldName);
		
		baseClass.stepInfo("Verify '"+fieldName2+"' Field Displayed Of Downlodeded Excel File.");
		dataSets.verifyFieldDisplayedOfDownlodedExcelFile(location,sheetName,fieldName2);
		
		baseClass.stepInfo("Verify '"+fieldName3+"' Field Displayed Of Downlodeded Excel File.");
		dataSets.verifyFieldDisplayedOfDownlodedExcelFile(location,sheetName,fieldName3);
	}
	
  
	/**  
	 * @author Gopinath
	 * //@TestCase id:50758 : Project level exported dataset details - Validate "Loaded with Error" tab details for an existing project having mapped and uploaded ingestions.
	 * @Description: Project level exported dataset details - Validate "Loaded with Error" tab details for an existing project having mapped and uploaded ingestions
	 */
	//@Test(alwaysRun = true, groups = { "regression" }, priority = 1)
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
		baseClass.stepInfo("### Project level exported dataset details - Validate \"Loaded with Error\" tab details for an existing project having mapped and uploaded ingestions ###");
		dataSets = new DataSets(driver);
		
		baseClass.stepInfo("Navigate data sets page.");
		dataSets.navigateToDataSetsPage();
		
		baseClass.stepInfo("Perform export data sets.");
		dataSets.exportDataSets();
		
		baseClass.stepInfo("Download exported file.");
		dataSets.downloadExportedFile();
		
		baseClass.stepInfo("Verify '"+fieldName+"' Field Displayed Of Downlodeded Excel File.");
		dataSets.verifyFieldDisplayedOfDownlodedExcelFile(location,sheetName,fieldName);
		
		baseClass.stepInfo("Verify '"+fieldName2+"' Field Displayed Of Downlodeded Excel File.");
		dataSets.verifyFieldDisplayedOfDownlodedExcelFile(location,sheetName,fieldName2);
		
		baseClass.stepInfo("Verify '"+fieldName3+"' Field Displayed Of Downlodeded Excel File.");
		dataSets.verifyFieldDisplayedOfDownlodedExcelFile(location,sheetName,fieldName3);
		
		baseClass.stepInfo("Verify '"+fieldName3+"' Field Displayed Of Downlodeded Excel File.");
		dataSets.verifyFieldDisplayedOfDownlodedExcelFile(location,sheetName2,errorTypeFieldType);
	}
	
	/**  
	 * @author Gopinath
	 * //@TestCase id:50771 : Verify that after Re-run the copy process without ignoring the errors, copy should continues.
	 * @Description: Verify that after Re-run the copy process without ignoring the errors, copy should continues
	 */
	//@Test(alwaysRun = true, groups = { "regression" }, priority = 1)
	public void verifyRerunCopyProcessWithoutIgnoringErrors() {
		baseClass = new BaseClass(driver);
		String projectName = "AutomationIngestionProject";
		String ingestionType = "Add Only";
		String sourceSystem = "TRUE";
		String sourceLocation =  "IngestionTestData\\Automation";
		String sourceFolder =  "SSAudioSpeech_Transcript";
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
		baseClass.stepInfo("### Verify that after Re-run the copy process without ignoring the errors, copy should continues ###");
		IngestionPage_Indium ingetion = new IngestionPage_Indium(driver);
		
		baseClass.stepInfo("Select project");
		baseClass.selectproject(projectName);
		
		baseClass.stepInfo("Navigate to ingestion page.");
		ingetion.navigateToIngestionPage();
		
		baseClass.stepInfo("Select ingestion type and specify source loaction.");
		ingetion.selectIngestionTypeAndSpecifySourceLocation(ingestionType, sourceSystem, sourceLocation,sourceFolder);
		
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
		ingetion.selectValueFromEnabledFirstThreeSourceDATFields(docId,dataSource, custodian);
		
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
	 * //@TestCase id:50744 : Verify that when rollback is in-progress then error page should not be displayed on Datasets page.
	 * @Description: Verify that when rollback is in-progress then error page should not be displayed on Datasets page.
	 */
	//@Test(alwaysRun = true, groups = { "regression" }, priority = 1)
	public void verifyRollBackIsInProgressErrorMsgNotDisplayed() throws InterruptedException {
		baseClass = new BaseClass(driver);
		String projectName = Input.ingestionProjectName;
		String ingestionType = Input.ingestionType;
		String sourceSystem = Input.sourceSystem;
		String sourceLocation =  Input.sourceLocation;
		String sourceFolder =  Input.sourceFolder;
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
		String fileExt =Input.fileExt;
		String fileName = Input.fileName;
		String fileSize = Input.fileSize;
		String fileType =Input.fileType;
		String docBasic = Input.ingDocBasic;
		String docFileExt = Input.docFileExt;
		String docFileName = Input.ingDocFileName;
		String docFileSize = Input.ingDocFileSize;
		String docFileType = Input.ingDocFileType;
		baseClass.stepInfo("Test case Id: RPMXCON-50744 Sprint 12");
		baseClass.stepInfo("###  Verify that when rollback is in-progress then error page should not be displayed on Datasets page. ###");
		IngestionPage_Indium ingetion = new IngestionPage_Indium(driver);
		
		baseClass.stepInfo("Select project");
		baseClass.selectproject(projectName);
		
		baseClass.stepInfo("Navigate to ingestion page.");
		ingetion.navigateToIngestionPage();
		
		baseClass.stepInfo("Select ingestion type and specify source loaction.");
		ingetion.selectIngestionTypeAndSpecifySourceLocation(ingestionType, sourceSystem, sourceLocation,sourceFolder);
		
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
		ingetion.selectValueFromEnabledFirstThreeSourceDATFields(docId,dataSource, custodian);
		
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
	 * //@TestCase id:58503 : Verify if user ingest documents with ICE as Source System then same dataset cannot ingest with any other Source System.
	 * @Description: Verify if user ingest documents with ICE as Source System then same dataset cannot ingest with any other Source System
	 */  
	//@Test(alwaysRun = true, groups = { "regression" }, priority = 1)
	public void verifyErrorMsgDisplayedByIngestingPublishedData() throws InterruptedException {
		baseClass = new BaseClass(driver);
		String projectName = "AutomationRegressionBackup";
		String ingestionType = Input.ingestionType;
		String sourceSystem = Input.iceSourceSystem;
		String sourceLocation =  Input.sourceLocation;
		String sourceFolder =  Input.multiPageTIFFSourceFolder;
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
		baseClass.stepInfo("### Verify if user ingest documents with ICE as Source System then same dataset cannot ingest with any other Source System ###");
		IngestionPage_Indium ingetion = new IngestionPage_Indium(driver);
		
		baseClass.stepInfo("Select project");
		baseClass.selectproject(projectName);
		
		baseClass.stepInfo("Navigate to ingestion page.");
		ingetion.navigateToIngestionPage();
		
		baseClass.stepInfo("Select ingestion type and specify source loaction.");
		ingetion.selectIngestionTypeAndSpecifySourceLocation(ingestionType, sourceSystem, sourceLocation,sourceFolder);
		
		baseClass.stepInfo("Select DAT delimiters.");
		ingetion.addDelimitersInIngestionWizard(fieldSeperator, textQualifier, multiValue);
		
		baseClass.stepInfo("Select DAT source.");
		ingetion.selectDATSource(datLoadFile, documentKey);
		
		baseClass.stepInfo("Select Date and Time format.");
		ingetion.selectDateAndTimeForamt(dateFormat);
		
		baseClass.stepInfo("Click on next button.");
		ingetion.clickOnNextButton();
		
		baseClass.stepInfo("Select value from first three source DAT fields");
		ingetion.selectValueFromEnabledFirstThreeSourceDATFields(docId,dataSource, custodian);
		
		baseClass.stepInfo("Click on preview and run button.");
		ingetion.clickOnPreviewAndRunButton();
		
		baseClass.stepInfo("Select all options from filter by dropdown.");
		ingetion.selectAllOptionsFromFilterByDropdown();
		
		baseClass.stepInfo("Create ingestion to cataloged stage");
		ingetion.ingestionCreationToFailedState();
		
		loginPage.logout();
		
	}
	
	
	
	/**
	 * Author :Brundha date: NA Modified date: Modified by: 
	 * Description :Verify Search should work by concatenated email metadata field
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority =3)
	public void verifySearchForSelectedMetadata() throws InterruptedException {
		baseClass = new BaseClass(driver);
		
	
		baseClass.stepInfo("Test case Id: RPMXCON-49560");
		baseClass.stepInfo("### Verify Search should work by concatenated email metadata field ###");
		
		SessionSearch search=new SessionSearch(driver);
		search.SearchMetaData("EmailCCNamesAndAddresses",Input.EmailAuthourName);
		search.verifyTheCountOfDocumentForMetaData();
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		
		search.SearchMetaData("EmailCCNamesAndAddresses",Input.EmailAuthourName);
		search.verifyTheCountOfDocumentForMetaData();
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		
		search.SearchMetaData("EmailCCNamesAndAddresses",Input.EmailAuthourName);
		search.verifyTheCountOfDocumentForMetaData();
		baseClass.passedStep("Verified Search should work by concatenated email metadata field");
		loginPage.logout();
	}
	
	
	/**
	 * Author :Brundha date: NA Modified date: Modified by: 
	 * Description :Verify Search should work by split email metadata field
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority =4)
	public void verifySearchForEmailMetaData() throws InterruptedException {
		baseClass = new BaseClass(driver);
		
	
		baseClass.stepInfo("Test case Id: RPMXCON-49565");
		baseClass.stepInfo("### Verify Search should work by split email metadata field ###");
		
		SessionSearch search=new SessionSearch(driver);
		search.SearchMetaData("EmailCCNames",Input.EmailAuthourName);
		search.verifyTheCountOfDocumentForMetaData();
		
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		
		search.SearchMetaData("EmailCCNames",Input.EmailAuthourName);
		search.verifyTheCountOfDocumentForMetaData();
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		
		search.SearchMetaData("EmailCCNames",Input.EmailAuthourName);
		search.verifyTheCountOfDocumentForMetaData();
		baseClass.passedStep("Verified Search should work by split email metadata field");
		
		loginPage.logout();
	}
	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {

			Utility bc = new Utility(driver);
			bc.screenShot(result);
			try { // if any tc failed and dint logout!
				loginPage.logoutWithoutAssert();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		System.out.println("Executed :" + result.getMethod().getMethodName());

	}
	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			//loginPage.quitBrowser();
		} finally {
			loginPage.closeBrowser();
			LoginPage.clearBrowserCache();
		}
	}
}
