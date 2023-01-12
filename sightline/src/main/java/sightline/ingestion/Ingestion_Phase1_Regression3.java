package sightline.ingestion;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

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
import pageFactory.BaseClass;
import pageFactory.DataSets;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.IngestionPage_Indium;
import pageFactory.LoginPage;
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

public class Ingestion_Phase1_Regression3 {

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
	DocViewPage docView;
	SavedSearch savedSearch;
	TagsAndFoldersPage tagAndFolder;

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
	
	/*This class contains 52 testCases.*/
	/**
	 * @author Gopinath
	 * @TestCase id:50755 : Project level exported dataset details - Validate "Not
	 *           Processed and Not Loaded" tab details for a project having Uploaded
	 *           ingestions.
	 * @Description: Project level exported dataset details - Validate "Not
	 *               Processed and Not Loaded" tab details for a project having
	 *               Uploaded ingestions.
	 */
	@Test(description ="RPMXCON-50755",alwaysRun = true, groups = { "regression" })
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
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		
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
		loginPage.logout();
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
	@Test(description ="RPMXCON-50756",alwaysRun = true, groups = { "regression" })
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
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
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
		loginPage.logout();
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
	@Test(description ="RPMXCON-50758",alwaysRun = true, groups = { "regression" })
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
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		
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
		loginPage.logout();
	}

	/**
	 * @author Gopinath
	 * @TestCase id:50771 : Verify that after Re-run the copy process without
	 *           ignoring the errors, copy should continues.
	 * @Description: Verify that after Re-run the copy process without ignoring the
	 *               errors, copy should continues
	 */
	@Test(description ="RPMXCON-50771",alwaysRun = true, groups = { "regression" })
	public void verifyRerunCopyProcessWithoutIgnoringErrors() {
		
		baseClass.stepInfo("Test case Id: RPMXCON-50771 Sprint 12");
		baseClass.stepInfo(
				"### Verify that after Re-run the copy process without ignoring the errors, copy should continues ###");
	
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);

		baseClass.stepInfo("Navigate to ingestion page.");
		ingestionPage.navigateToIngestionPage();
		baseClass.stepInfo("Select ingestion type and specify source loaction.");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem, 
				Input.sourceLocation, Input.SSAudioSpeechFolder);
		baseClass.stepInfo("Select DAT delimiters.");
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);
		baseClass.stepInfo("Select DAT source.");
		ingestionPage.selectDATSource(Input.DATFile2, Input.documentKey);
		baseClass.stepInfo("Select MP3 varient source.");
		ingestionPage.selectMP3VarientSource(Input.mp3LoadFile, false);
		baseClass.stepInfo("Select Date and Time format.");
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		baseClass.stepInfo("Click on next button.");
		ingestionPage.clickOnNextButton();
		baseClass.stepInfo("Select value from first three source DAT fields");
		ingestionPage.selectValueFromEnabledFirstThreeSourceDATFields(Input.documentKey,Input.dataSource, Input.custodian);
		baseClass.stepInfo(" select field catagory and destination field by using source DAT field.");
		ingestionPage.selectFieldCatagoryDestinationFields(Input.fileExt, Input.ingDocBasic, Input.docFileExt);
		ingestionPage.selectFieldCatagoryDestinationFields(Input.fileName, Input.ingDocBasic, Input.ingDocFileName);
		ingestionPage.selectFieldCatagoryDestinationFields(Input.fileSize, Input.ingDocBasic, Input.ingDocFileSize);
		ingestionPage.selectFieldCatagoryDestinationFields(Input.fileType, Input.ingDocBasic, Input.ingDocFileType);
		baseClass.stepInfo("Click on preview and run button.");
		ingestionPage.clickOnPreviewAndRunButton();
		baseClass.stepInfo("Select all options from filter by dropdown.");
		ingestionPage.selectAllOptionsFromFilterByDropdown();
		baseClass.stepInfo("Create ingestion to cataloged stage");
		String ingestionName = ingestionPage.ingestionCreationToCatalogedStage();
		baseClass.stepInfo("Process to click on copied state without ignoring errors.");
		ingestionPage.clickOnCopiedStateWithoutIgnoringErrors(ingestionName);
		loginPage.logout();
	}

	/**
	 * @author Gopinath
	 * @throws InterruptedException
	 * @TestCase id:50744 : Verify that when rollback is in-progress then error page
	 *           should not be displayed on Datasets page.
	 * @Description: Verify that when rollback is in-progress then error page should
	 *               not be displayed on Datasets page.
	 */
	@Test(description ="RPMXCON-50744",alwaysRun = true, groups = { "regression" })
	public void verifyRollBackIsInProgressErrorMsgNotDisplayed() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-50744 Sprint 12");
		baseClass.stepInfo(
				"###  Verify that when rollback is in-progress then error page should not be displayed on Datasets page. ###");
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		baseClass.stepInfo("Navigate to ingestion page.");
		ingestionPage.navigateToIngestionPage();

		baseClass.stepInfo("Select ingestion type and specify source loaction.");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem, Input.sourceLocation,
				Input.sourceFolder);

		baseClass.stepInfo("Select DAT delimiters.");
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);
		baseClass.stepInfo("Select DAT source.");
		ingestionPage.selectDATSource(Input.datLoadFile, Input.documentKey);
		baseClass.stepInfo("Select MP3 varient source.");
		ingestionPage.selectMP3VarientSource(Input.mp3LoadFile, false);
		baseClass.stepInfo("Select Date and Time format.");
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		baseClass.stepInfo("Click on next button.");
		ingestionPage.clickOnNextButton();
		baseClass.stepInfo("Select value from first three source DAT fields");
		ingestionPage.selectValueFromEnabledFirstThreeSourceDATFields(Input.documentKey, Input.dataSource, Input.custodian);
		baseClass.stepInfo(" select field catagory and destination field by using source DAT field.");
		ingestionPage.selectFieldCatagoryDestinationFields(Input.fileExt, Input.ingDocBasic, Input.docFileExt);
		ingestionPage.selectFieldCatagoryDestinationFields(Input.fileName, Input.ingDocBasic, Input.ingDocFileName);
		ingestionPage.selectFieldCatagoryDestinationFields(Input.fileSize, Input.ingDocBasic, Input.ingDocFileSize);
		ingestionPage.selectFieldCatagoryDestinationFields(Input.fileType, Input.ingDocBasic, Input.ingDocFileType);

		baseClass.stepInfo("Click on preview and run button.");
		ingestionPage.clickOnPreviewAndRunButton();

		baseClass.stepInfo("Select all options from filter by dropdown.");
		ingestionPage.selectAllOptionsFromFilterByDropdown();

		baseClass.stepInfo("Create ingestion to cataloged stage");
		String ingestionName = ingestionPage.ingestionCreationToCatalogedStage();

		baseClass.stepInfo("Click on roll back from catalog stage and verify status changed is inprogress.");
		ingestionPage.verifyInprogressStatusByclickOnRollback(ingestionName);

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
	@Test(description ="RPMXCON-58503",enabled = true, groups = { "regression" })
	public void verifyErrorMsgDisplayedByIngestingPublishedData() throws InterruptedException {
				
		baseClass.stepInfo("Test case Id: RPMXCON-58503 Sprint 13");
		baseClass.stepInfo(
				"### Verify if user ingest documents with ICE as Source System then same dataset cannot ingest with any other Source System ###");
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);

		baseClass.stepInfo("Navigate to ingestion page.");
		ingestionPage.navigateToIngestionPage();

		baseClass.stepInfo("Select ingestion type and specify source loaction.");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.iceSourceSystem, 
				Input.sourceLocation, Input.multiPageTIFFSourceFolder);
		baseClass.stepInfo("Select DAT delimiters.");
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);
		baseClass.stepInfo("Select DAT source.");
		ingestionPage.selectDATSource(Input.multiPageTIFFDATLoadFile, Input.documentKeyBNum);
		baseClass.stepInfo("Select Date and Time format.");
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		baseClass.stepInfo("Click on next button.");
		ingestionPage.clickOnNextButton();
		baseClass.stepInfo("Select value from first three source DAT fields");
		ingestionPage.selectValueFromEnabledFirstThreeSourceDATFields(Input.documentKeyBNum, Input.documentKeyDSource, Input.documentKeyCName);
		baseClass.stepInfo("Click on preview and run button.");
		ingestionPage.clickOnPreviewAndRunButton();
		baseClass.stepInfo("Select all options from filter by dropdown.");
		ingestionPage.selectAllOptionsFromFilterByDropdown();
		baseClass.stepInfo("Create ingestion to cataloged stage");
		ingestionPage.ingestionCreationToFailedState();
		loginPage.logout();

	}
	/**
	 * Author :Brundha date: NA Modified date: Modified by: Description :Verify
	 * Search should work by concatenated email metadata field
	 */
	@Test(description ="RPMXCON-49560",enabled = true, groups = { "regression" })
	public void verifySearchForSelectedMetadata() throws InterruptedException {
		baseClass = new BaseClass(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-49560");
		baseClass.stepInfo("### Verify Search should work by concatenated email metadata field ###");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		sessionSearch.SearchMetaData("EmailCCNamesAndAddresses", Input.EmailAuthourName);
		sessionSearch.verifyTheCountOfDocumentForMetaData();
		loginPage.logout();
		// login as rmu and verify
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password, Input.ingestDataProject);
		sessionSearch.SearchMetaData("EmailCCNamesAndAddresses", Input.EmailAuthourName);
		sessionSearch.verifyTheCountOfDocumentForMetaData();
		loginPage.logout();
		// login as rev and verify
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password,Input.ingestDataProject);
		sessionSearch.SearchMetaData("EmailCCNamesAndAddresses", Input.EmailAuthourName);
		sessionSearch.verifyTheCountOfDocumentForMetaData();
		baseClass.passedStep("Verified Search should work by concatenated email metadata field");
		loginPage.logout();
	}

	/**
	 * Author :Brundha date: NA Modified date: Modified by: Description :Verify
	 * Search should work by split email metadata field
	 */
	@Test(description ="RPMXCON-49565",alwaysRun = true, groups = { "regression" })
	public void verifySearchForEmailMetaData() throws InterruptedException {
		baseClass = new BaseClass(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-49565");
		baseClass.stepInfo("### Verify Search should work by split email metadata field ###");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password,Input.projectName02);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
		
		sessionSearch.SearchMetaData("EmailCCNames", Input.EmailAuthourName);
		sessionSearch.verifyTheCountOfDocumentForMetaData();

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password,Input.projectName02);

		sessionSearch.SearchMetaData("EmailCCNames", Input.EmailAuthourName);
		sessionSearch.verifyTheCountOfDocumentForMetaData();
		loginPage.logout();
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password,Input.projectName02);
		
		sessionSearch.SearchMetaData("EmailCCNames", Input.EmailAuthourName);
		sessionSearch.verifyTheCountOfDocumentForMetaData();
		baseClass.passedStep("Verified Search should work by split email metadata field");
		loginPage.logout();
	}

	/**
	 * Author :Brundha date: NA Modified date: Modified by: Description :Verify
	 * Email metadata in Manage-Project fields
	 */
	@Test(description ="RPMXCON-49563",alwaysRun = true, groups = { "regression" })
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
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-49561",enabled = true, groups = { "regression" })
	public void verifyEmailMetadataWithIngestionName() throws Exception {
		baseClass = new BaseClass(driver);
		SavedSearch savedSearch = new SavedSearch(driver);
		ProjectPage projectPage = new ProjectPage(driver);
		SecurityGroupsPage securityGroupPage = new SecurityGroupsPage(driver);
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
	@Test(description ="RPMXCON-48241",enabled = true, groups = { "regression" })
	public void verifyMoreThanHourAudioDocs() throws InterruptedException {
		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		docView = new DocViewPage(driver);
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
				baseClass.waitForElement(docView.getDocumentByid(moreThanHourPT));
				docView.getDocumentByid(moreThanHourPT).waitAndClick(5);
			}
			if (uatFlag==true) {
				baseClass.waitForElement(docView.getDocumentByid(moreThanHourUAT));
				docView.getDocumentByid(moreThanHourUAT).waitAndClick(5);
			}

			// verifying more than one hour audio docs
			String overAllAudioTime = docView.getDocview_Audio_EndTime().getText();
			String[] splitData = overAllAudioTime.split(":");
			String data = splitData[0].toString();
			System.out.println(data);
			if (Integer.parseInt(data) >= 01) {
				baseClass.stepInfo("Audio docs have more than:" + overAllAudioTime + " hour to check zoom function");
			} else {
				baseClass.failedMessage("Lesser than one hour");
			}

			// checking zoom in function working for more than one hour audio docs
			docView.getAudioDocZoom().waitAndClick(5);
			boolean zoomBar = docView.getAudioZoomBar().Displayed();
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
	@Test(description ="RPMXCON-48239",enabled = true, groups = { "regression" })
	public void verifyLessThanHourAudioDocs() throws InterruptedException {
		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		SoftAssert softAssertion = new SoftAssert();
		docView = new DocViewPage(driver);
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
				baseClass.waitForElement(docView.getDocumentByid(lessThanHourPT));
				docView.getDocumentByid(lessThanHourPT).waitAndClick(5);
			}
			if (uatFlag==true) {
				baseClass.waitForElement(docView.getDocumentByid(lessThanHourUAT));
				docView.getDocumentByid(lessThanHourUAT).waitAndClick(5);
			}

			// verifying less than one hour audio docs
			String overAllAudioTime = docView.getDocview_Audio_EndTime().getText();
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
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(description ="RPMXCON-50749",enabled = true, groups = { "regression" })
	public void validateExportingDatasetAsPA() throws InterruptedException, IOException {

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
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(description ="RPMXCON-50750",enabled = true, groups = { "regression" })
	public void validateExportingDatasetAsRMU() throws InterruptedException, IOException {

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
	 * @Description :Validate exporting dataset details at project level for
	 *              impersonating as Project Admin
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(description ="RPMXCON-50751",enabled = true, dataProvider = "userDataSetsSAAndDA", groups = { "regression" })
	public void validateExportingDatasetAsSAandDA(String userName, String password, String fromRole)
			throws InterruptedException, IOException {

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
		loginPage.logout();
	}
	
	/**
	 * Author: Mohan date: 02/05/2022 Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-49521 Description :Verify that if PA ingested both TEXT and TIFF's
	 * file,the "Generate Searchable PDFs"is true and TIFF is missing then it should
	 * displays default PDF file
	 * 
	 * @throws InterruptedException
	 */
    @Test(description ="RPMXCON-49521",enabled = true, groups = { "regression" })
	public void verifyTEXTAndTIFFSFileGenerateSearchablePDFsIsTrue() throws InterruptedException {

    	loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		
		baseClass.stepInfo("Test case Id: RPMXCON-49521");
		baseClass.stepInfo(
				"Verify that if PA ingested both TEXT and TIFF's file,the 'Generate Searchable PDFs' is true and TIFF is missing then it should displays default PDF file");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.PP_PDFGen_10Docs);
		if (status == false) {

			ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,Input.ingestionType, "TRUE", Input.DATPPPDF10Docs, null,
					Input.TextPPPDF10Docs, null, Input.ImagePPPDF10docs,"select", null, null, null);
			
		}
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
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		baseClass.stepInfo("Test case Id: RPMXCON-50377");
		baseClass.stepInfo("Verify that if PA ingested Native, PDF and TIFF's file and the 'Generate Searchable PDFs' option is set to true, then PDF should be generated from the TIFF's");
		
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		DocViewPage docViewPage = new DocViewPage(driver);
		boolean status = ingestionPage.verifyIngestionpublishWithAdditionalOptions(Input.PP_PDFGen_10Docs,"50");
		if(status==false) {
		ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,Input.ingestionType, "TRUE", "DAT4_STC_09172014_newdateformat_50Docs.dat", "Natives_50Docs.lst",
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
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48195",enabled = true, groups = { "regression" })
	public void verifyFamilyMemberCountInDocList() throws InterruptedException {
				
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
	
		baseClass.stepInfo("Test case Id: RPMXCON-48195");
		baseClass.stepInfo("To Very the Family Member Counts After Ingestion completed successfully.");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.PP_PDFGen_10Docs);
		String familyMemberCount = "FamilyMemberCount";
		System.out.println(status);
		if (status == false) {
			ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs, Input.ingestionType, "TRUE",
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
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
	
		baseClass.stepInfo("Test case Id: RPMXCON-49520");
		baseClass.stepInfo("Verify that if PA ingested both PDF and TIFF's file,the 'Generate Searchable PDFs' is true and TIFF is missing then it PDF should displays PDF in viewer");
		
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,Input.overlayOnly, null, Input.DATPPPDF10Docs, null,
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
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		
		baseClass.stepInfo("Test case Id: RPMXCON-49519");
		baseClass.stepInfo("Verify that if PA ingested both Native and TIFF's file, the 'Generate Searchable PDFs' is true and TIFF is missing then searchable PDF's should be generated from the Natives.");
		
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,Input.overlayOnly, null, Input.DATPPPDF10Docs, "Natives -5Docs.lst",
					null,null , Input.ImagePPPDF10docs,"Select", null, null, null);
		ingestionPage.navigateToIngestionPage();
		
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
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		
		baseClass.stepInfo("Test case Id: RPMXCON-49509");
		baseClass.stepInfo("Verify that if PA ingested both native's and TIFF's file,and the \"Generate Searchable PDFs\" option is set to false then it should display TIFF in default viewer");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		
		
		ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,Input.overlayOnly, null, Input.DATPPPDF10Docs, "Natives -5Docs.lst",
					null,null , Input.ImagePPPDF10docs,null, null, null, null);
		ingestionPage.navigateToIngestionPage();
		
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
	
	@DataProvider(name = "userDetails")
	public Object[][] userLoginDetails() {
		return new Object[][] { { Input.pa1FullName, Input.pa1userName, Input.pa1password, Input.projectName02 },
				{ Input.rmu1FullName, Input.rmu1userName, Input.rmu1password, Input.projectName02 },
				{ Input.rev1FullName, Input.rev1userName, Input.rev1password, Input.projectName02 } };
	}

	/**
	 * Author : Mohan date: 22/04/22 NA Modified date: NA Modified by:NA Description
	 * :Verify that documents ingested with the Date Format than the sightline
	 * standard format should be searchable by date fields'RPMXCON-49542'
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-49542",enabled = true, dataProvider = "userDetails", groups = { "regression" } )
	public void verifyDateFormateInTheMeatadataFieldAndDocList(String fullName, String userName, String password,
			String projectName) throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-49542");
		baseClass.stepInfo(
				"Verify that documents ingested with the Date Format than the sightline standard format should be searchable by date fields");
		String fieldName = Input.masterDateText;

		// Login as a Admin
		loginPage.loginToSightLine(userName, password, projectName);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);

		baseClass.stepInfo("Step 2: Login as @User   Search the documents with the date fields");
		sessionSearch.basicContentSearch(fieldName);

		baseClass.stepInfo(
				"Step 3 : Search the documents and drag the result to shopping cartSelect action as 'View in DocView'");
		sessionSearch.ViewInDocView();

		baseClass.stepInfo("Step 4 : Verify the Date fields from the metadata");
		docView = new DocViewPage(driver);
		docView.selectMeatdataGetFieldValue(fieldName);

		baseClass.stepInfo(
				"Step 5 : Select all documents   Click on View in Doc List   Verify the Date fields from the metadata");
		baseClass.selectproject(Input.projectName02);
		sessionSearch.basicContentSearch(fieldName);
		sessionSearch.ViewInDocList();
		DocListPage docListPage = new DocListPage(driver);
		docListPage.verifyMasterDateColumnValue();
		loginPage.logout();
	}

	/**
	 * Author : Mohan date: 22/04/22 NA Modified date: NA Modified by:NA Description
	 * :Verify that documents ingested with the Date Format than the sightline
	 * standard format should be viewable in Doc View with metadata'RPMXCON-49543'
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-49543",enabled = true, dataProvider = "userDetails", groups = { "regression" })
	public void verifyDateFormateInTheMeatadataField(String fullName, String userName, String password,
			String projectName) throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-49543");
		baseClass.stepInfo(
				"Verify that documents ingested with the Date Format than the sightline standard format should be viewable in Doc View with metadata");
		String fieldName = Input.masterDateText;

		// Login as a Admin
		loginPage.loginToSightLine(userName, password, projectName);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);

		baseClass.stepInfo("Step 2: Login as @User   Search the documents with the date fields");
		sessionSearch.basicContentSearch(fieldName);

		baseClass.stepInfo(
				"Step 3 : Search the documents and drag the result to shopping cartSelect action as 'View in DocView'");
		sessionSearch.ViewInDocView();

		baseClass.stepInfo("Step 4 : Verify the Date fields from the metadata");
		docView = new DocViewPage(driver);
		docView.selectMeatdataGetFieldValue(fieldName);
		loginPage.logout();
	}

	/**
	 * Author : Mohan date: 22/04/22 NA Modified date: NA Modified by:NA Description
	 * :Verify that documents ingested with the Date Format than the sightline
	 * standard format should be viewable in Doc List with metadata'RPMXCON-49544'
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-49544",enabled = true, dataProvider = "userDetails", groups = { "regression" } )
	public void verifyDateFormateInTheMeatadataFieldInDocList(String fullName, String userName, String password,
			String projectName) throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-49544");
		baseClass.stepInfo(
				"Verify that documents ingested with the Date Format than the sightline standard format should be viewable in Doc List with metadata");
		String fieldName = Input.masterDateText;

		// Login as a Admin
		loginPage.loginToSightLine(userName, password, projectName);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);

		baseClass.stepInfo("Step 2: Login as @User   Search the documents with the date fields");
		sessionSearch.basicContentSearch(fieldName);

		baseClass.stepInfo(
				"Step 3 : Search the documents and drag the result to shopping cartSelect action as 'View in DocList'");
		sessionSearch.ViewInDocList();

		baseClass.stepInfo("Step 4 : Verify the Date fields from the metadata");
		DocListPage docListPage = new DocListPage(driver);
		docListPage.verifyMasterDateColumnValue();
		loginPage.logout();
	}

	/**
	 * Author : Mohan date: 17/02/22 NA Modified date: NA Modified by:NA Description
	 * :Verify 'Source System' is disabled if user select Ingestion-Overlay on
	 * Ingestion Wizard page'RPMXCON-58508'
	 * @throws Exception
	 */
	@Test(description ="RPMXCON-58508",enabled = true, groups = { "regression" })
	public void verifySourceSystemIsDisabled() throws Exception {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);

		baseClass.stepInfo("Test case Id: RPMXCON-58508");
		baseClass.stepInfo(
				"Verify 'Source System' is disabled if user select Ingestion-Overlay on Ingestion Wizard page");
		baseClass.stepInfo("Step 2: Create an new Ingestion");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);

		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Edit of addonly saved ingestion with mapping field selection");
			
			ingestionPage.IngestionRegressionForDifferentDAT(Input.AllSourcesFolder,Input.ingestionType, Input.sourceSystem, Input.DATFile1,
					Input.NativeFile, null,null, null, null, null, null, null);
		}
		baseClass.stepInfo(
				"Step 3&4 : Go to Ingestion Add New Ingestion and Ingestion Type as 'Overlay Only' and Ingestion Type as 'Overlay Only'");
		ingestionPage.verifySourceSystemDisabledWhenOverylayIsAddedAsIngestionType();
		loginPage.logout();
	}

	/**
	 * Author :Brundha Test Case Id:RPMXCON-50083 Description :verify Ingestion
	 * should published successfully if Email metadata is having only Address
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-50083",enabled = true, groups = { "regression" })
	public void verifyEmailAddressInDocListPage() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-50083");
		baseClass.stepInfo("verify Ingestion should published successfully if Email metadata is having only Address");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);

		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Edit of addonly saved ingestion with mapping field selection");
			
			ingestionPage.IngestionRegressionForDifferentDAT(Input.GD994NativeTextForProductionFolder,Input.ingestionType,
					Input.sourceSystem, Input.datFormatFile, "DAT4_STC_NativesEmailData NEWID.lst",
					"DAT4_STC_TextEmailData NEWID.lst",null, null, null, null, null, null);
		}
		ingestionPage.IngestionRegressionForDifferentDAT(Input.GD994NativeTextForProductionFolder,Input.overlayOnly,
				Input.sourceSystem, Input.datFormatFile, "DAT4_STC_NativesEmailData NEWID.lst",
				"DAT4_STC_TextEmailData NEWID.lst",null, null, null, null, null, null);
		
		String[] addEmailColumn = { "EmailAuthorName", "EmailAuthorAddress" };

        DataSets dataSets=new DataSets(driver);
		dataSets.navigateToDataSetsPage();
		dataSets.selectDataSetWithName(Input.GD994NativeTextForProductionFolder);

		DocListPage doc = new DocListPage(driver);
		baseClass.stepInfo("Verifying Email Metadata in DocList page");
		doc.SelectColumnDisplayByRemovingExistingOnes(addEmailColumn);
		for (String metadata : addEmailColumn) {
			baseClass.visibleCheck(metadata);
		}
		int EmailData = baseClass.getIndex(doc.getHeaderText(), Input.MetaDataEAName);
		doc.GetColumnData(EmailData);
		baseClass.stepInfo("verifying field value in Email Metadata");
		ArrayList<String> selectedDocs = doc.verifyingEmailMetaData(EmailData);
		if (selectedDocs.contains("")) {
			baseClass.passedStep("email author name is blank as expected");
		} else {
			baseClass.failedStep("email author name is not blank as expected");
		}
		int EmailMetaData = baseClass.getIndex(doc.getHeaderText(), "EmailAuthorAddress");
		doc.GetColumnData(EmailMetaData);
		ArrayList<String> selectedDoc = doc.verifyingEmailMetaData(EmailMetaData);
		if (selectedDoc.contains(Input.validationData)) {
			baseClass.passedStep("email author address is displayed with the expected text");
		} else {
			baseClass.failedStep("email author address is not displayed with expected text");
		}
		loginPage.logout();

	}
	/**
	 * Author :Brundha Test Case Id:RPMXCON-49804 Description :To verify that if
	 * Email data contained space after the '@' sign , it should not calculate two
	 * distinct values
	 *
	 */
	@Test(description ="RPMXCON-49804",enabled = true, groups = { "regression" })
	public void verifyEmailAllDomainDistinctData() throws InterruptedException {

		
		dataSets = new DataSets(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49804");
		baseClass.stepInfo(
				"To verify that if Email data contained space after the '@' sign , it should not calculate two distinct values");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
		System.out.println(status);
		if (status == false) {
			ingestionPage = new IngestionPage_Indium(driver);
			String ingestionType="Add Only";
			baseClass.stepInfo("Edit of addonly saved ingestion with mapping field selection");
			ingestionPage.IngestionRegressionForDifferentDAT(Input.GD994NativeTextForProductionFolder,ingestionType,
					Input.sourceSystem, Input.datFormatFile, "DAT4_STC_NativesEmailData NEWID.lst",
					"DAT4_STC_TextEmailData NEWID.lst",null, null, null, null, null, null);
		}

		int count = sessionSearch.MetaDataSearchInBasicSearch(Input.emailAllDomain, "hotmail.com");
		sessionSearch.addNewSearch();
		int count1 = sessionSearch.newMetaDataSearchInBasicSearch(Input.emailAllDomain, "aol.com");
		int DocCount = Integer.parseInt(Input.DocCount);
		if (count != 0 && count == DocCount) {
			baseClass.passedStep("Email MetaData count is displayed as expected");
		} else {
			baseClass.failedStep("The count is not displayed as expected");
		}
		int DocumentCount = Integer.parseInt(Input.DocCount);
		int doc = DocumentCount + 1;

		if (count1 != 0 && count1 == doc) {
			baseClass.passedStep("Email MetaData count is displayed as expected");
		} else {
			baseClass.failedStep("The count is not displayed as expected");
		}
		baseClass.passedStep(
				"verified that if Email data contained space before the '@' sign , it should not calculate two distinct values");
		loginPage.logout();
	}

	/**
	 * Author :Brundha Test Case Id:RPMXCON-48173 Description :To Verify for
	 * AudioPlayer ready are Nexidia indexed.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48173",enabled = true, groups = { "regression" })
	public void verifyDocumentInAudioPlayerReady() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48173");
		baseClass.stepInfo("To Verify for AudioPlayer ready are Nexidia indexed.");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);

		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.AK_NativeFolder);
		System.out.println(status);
		if (status == false) {
			
			baseClass.stepInfo(" addonly ingestion with mapping field selection");
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation("Add Only", Input.sourceSystem,Input.sourceLocation, Input.AK_NativeFolder);
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
			ingestionPage.selectDATSource(Input.DATFile1,Input.prodBeg);
			ingestionPage.selectMP3VarientSource(Input.MP3File, false);
			ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
			ingestionPage.clickOnNextButton();
			ingestionPage.fillingSourceField();
			ingestionPage.clickOnPreviewAndRunButton();
			ingestionPage.publishAddonlyIngestion(Input.AK_NativeFolder);	
		}
		sessionSearch.SearchMetaData(Input.audioPlayerReady, Input.pageCount);
		sessionSearch.verifyDocCountForAudioPlayerReady();
		loginPage.logout();
	}

	/**
	 * Author :Brundha Test Case Id:RPMXCON-48171 Description :Verify that
	 * 'AudioPlayerReady' should set to '1' when MP3 file variant are ingested with
	 * .MP3 files
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48171",enabled = true, groups = { "regression" })
	public void verifyDocumentInAudioPlayerReadyValue() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48171");
		baseClass.stepInfo(
				"Verify that 'AudioPlayerReady' should set to '1' when MP3 file variant are ingested with .MP3 files");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.AK_NativeFolder);

		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Edit of addonly saved ingestion with mapping field selection");
			
			ingestionPage.IngestionRegressionForDifferentDAT(Input.AK_NativeFolder,Input.ingestionType, Input.sourceSystem, Input.DATFile1,
					null, null, null, null, null,Input.MP3File, null, null);
		}
		baseClass.stepInfo(" addonly ingestion with mapping field selection");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.overlayOnly, Input.sourceSystem,Input.sourceLocation, Input.AK_NativeFolder);
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		ingestionPage.selectDATSource(Input.DATFile1,Input.prodBeg);
		ingestionPage.selectMP3VarientSource(Input.MP3File, false);
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		ingestionPage.clickOnNextButton();
		ingestionPage.clickOnPreviewAndRunButton();
		ingestionPage.ignoreErrorsAndCatlogging();
		ingestionPage.ignoreErrorsAndCopying();
		ingestionPage.ingestionIndexing(Input.AK_NativeFolder);
		ingestionPage.approveIngestion(1);
		ingestionPage.runFullAnalysisAndPublish();
	
        DataSets dataSets=new DataSets(driver);
		dataSets.navigateToDataSetsPage();
		dataSets.selectDataSetWithName(Input.AK_NativeFolder);

		DocListPage doc = new DocListPage(driver);
		baseClass.waitForElement(doc.getSelectDropDown());
		doc.getSelectDropDown().waitAndClick(10);
		doc.selectingSingleValueInCoumnAndRemovingExistingOne(Input.audioPlayerReady);
		doc.verifyingTheMp3FileAndOtherFile(0);
		int count = Integer.valueOf(Input.pageCount);
		doc.verifyingTheMp3FileAndOtherFile(count);
		loginPage.logout();
	}
	
	
	/**
	 * Author :Brundha Test Case Id:RPMXCON-48170 Description :Verify that
	 * 'AudioPlayerReady' should set to '0' when natives are ingested with .MP3
	 * files
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48170",enabled = true, groups = { "regression" } )
	public void verifyingMp3FileDocumentCount() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48170");
		baseClass
				.stepInfo("Verify that 'AudioPlayerReady' should set to '0' when natives are ingested with .MP3 files");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);

		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("addonly  ingestion with mapping field selection");
			
			ingestionPage.IngestionRegressionForDifferentDAT(Input.AllSourcesFolder,Input.ingestionType, Input.sourceSystem, Input.DATFile1,
					Input.NativeFile, null,null, null, null, null, null, null);
		}
		DataSets dataSets=new DataSets(driver);
		dataSets.navigateToDataSetsPage();
		dataSets.selectDataSetWithName(Input.AllSourcesFolder);

		DocListPage doc = new DocListPage(driver);
		driver.waitForPageToBeReady();
		doc.selectingSingleValueInCoumnAndRemovingExistingOne(Input.audioPlayerReady);
		doc.verifyingTheMp3FileAndOtherFile(0);
		int Doc = Integer.valueOf(Input.pageCount);
		if (!doc.getDocCount(Doc).isDisplayed()) {
			baseClass.passedStep("Document count is displayed as expected");
		} else {
			baseClass.failedStep("Document Count is not displayed as expecetd");
		}
		loginPage.logout();
	}

	/**
	 * Author :Brundha Test Case Id:RPMXCON-49489 Description :Verify error message
	 * when user tried to do Ingestion overlay for non-existing dataset
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49489",enabled = true, groups = { "regression" })
	public void verfyingErrorMsgInIngestionPage() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49489");
		baseClass.stepInfo("Verify error message when user tried to do Ingestion overlay for non-existing dataset");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.TiffImagesFolder);
		if (status == false) {
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
					Input.sourceLocation, Input.TiffImagesFolder);
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);
			ingestionPage.selectDATSource(Input.DATFile3, Input.prodBeg);
			ingestionPage.selectTIFFSource(Input.tiffFile2, false, true);
			ingestionPage.selectPDFSource("DAT4_STC_PDFs.lst", false);
			ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
			ingestionPage.clickOnNextButton();
			ingestionPage.fillingSourceField();
			ingestionPage.clickOnPreviewAndRunButton();
			ingestionPage.publishAddonlyIngestion(Input.TiffImagesFolder);	
		}
		ingestionPage.OverlayIngestionForDATWithMappingFieldSection(Input.HiddenPropertiesFolder,
				Input.YYYYMMDDHHMISSDat, Input.sourceDocIdSearch);
		ingestionPage.clickOnPreviewAndRunButton();
		ingestionPage.verifyingErrorMsgInOverLayMethod();
		loginPage.logout();
	}

	/**
	 * Author :Brundha Test Case Id:RPMXCON-49507 Description :Verify in DocView,
	 * the Default tab displays Searchable PDF generated from TIFF and Image tab
	 * should displays TIFF file
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49507",enabled = true, groups = { "regression" } )
	public void verifyingTiffImageTabInDocViewPage() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49507");
		baseClass.stepInfo(
				"Verify in DocView, the Default tab displays Searchable PDF generated from TIFF and Image tab should displays TIFF file");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.TiffImagesFolder);
		if (status == false) {

			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
					Input.sourceLocation, Input.TiffImagesFolder);
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);
			ingestionPage.selectDATSource(Input.DATFile3, Input.prodBeg);
			ingestionPage.selectTIFFSource(Input.tiffFile2, false, true);
			ingestionPage.selectPDFSource("DAT4_STC_PDFs.lst", false);
			ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
			ingestionPage.clickOnNextButton();
			ingestionPage.fillingSourceField();
			ingestionPage.clickOnPreviewAndRunButton();
			ingestionPage.publishAddonlyIngestion(Input.TiffImagesFolder);
		}
		
		DataSets dataSets=new DataSets(driver);
		dataSets.navigateToDataSetsPage();
		dataSets.selectDataSetWithNameInDocView(Input.TiffImagesFolder);
		DocViewPage doc = new DocViewPage(driver);
		doc.verifyingDefaultTextInDocView();
		doc.clickOnImageTab();
		for (int i = 0; i < 5; i++) {
			doc.getImageTabOption(Input.TiffImagesFolder).isElementAvailable(10);
			if (doc.getImageTabOption(Input.TiffImagesFolder).isDisplayed()) {
				baseClass.passedStep("Tiff image is displayed as expected");
				break;
			} else {
				baseClass.failedStep("Tiff image is not displayed as expected");
			}

		}
		loginPage.logout();
	}

	/**
	 * Author :Brundha Test Case Id:RPMXCON-49704 Description :Verify if 'Generate
	 * Searchable PDFs' is True for TIFF image and document has multi-page TIFF's
	 * then searchable PDF's should be created successfully for any stitched TIFF's
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49704",enabled = true, groups = { "regression" } )
	public void verifyingMultipleTiffImageTabInDocViewPage() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49704");
		baseClass.stepInfo(
				"Verify if 'Generate Searchable PDFs' is True for TIFF image and document has multi-page TIFF's then searchable PDF's should be created successfully for any stitched TIFF's");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);

		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.TiffImagesFolder);
		if (status == false) {
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
					Input.sourceLocation, Input.TiffImagesFolder);
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);
			ingestionPage.selectDATSource(Input.DATFile3, Input.prodBeg);
			ingestionPage.selectTIFFSource(Input.tiffFile2, false, true);
			ingestionPage.selectPDFSource("DAT4_STC_PDFs.lst", false);
			ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
			ingestionPage.clickOnNextButton();
			ingestionPage.fillingSourceField();
			ingestionPage.clickOnPreviewAndRunButton();
			ingestionPage.publishAddonlyIngestion(Input.TiffImagesFolder);
		}

		DataSets dataSets=new DataSets(driver);
		dataSets.navigateToDataSetsPage();
		dataSets.selectDataSetWithNameInDocView(Input.TiffImagesFolder);
		DocViewPage doc = new DocViewPage(driver);
		doc.verifyingDefaultTextInDocView();
		loginPage.logout();
	}

	/**
	 * Author :Brundha Test Case Id:RPMXCON-48188 Description :To Verify
	 * AudioPlayerReady is set to 1 only if the document has an MP3 File Variant.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48188",enabled = true, groups = { "regression" } )
	public void verifyingAudioPlayerReadyDocumentCount() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48188");
		baseClass.stepInfo("To Verify AudioPlayerReady is set to 1 only if the document has an MP3 File Variant.");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.AK_NativeFolder);
		
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Edit of addonly saved ingestion with mapping field selection");
			ingestionPage.IngestionRegressionForDifferentDAT(Input.AK_NativeFolder,Input.ingestionType, Input.sourceSystem, Input.DATFile1,
					null, null, null, null,null, Input.MP3File, null, null);
		}
		baseClass.stepInfo("Basic content search");
		sessionSearch.basicContentSearch(Input.searchStringStar);

		baseClass.stepInfo("Navigating to doclist page");
		sessionSearch.ViewInDocList();

		DocListPage doc = new DocListPage(driver);
		baseClass.waitForElement(doc.getSelectDropDown());
		doc.getSelectDropDown().waitAndClick(10);
		doc.selectingSingleValueInCoumnAndRemovingExistingOne(Input.audioPlayerReady);
		int count = Integer.valueOf(Input.pageCount);
		doc.verifyingTheMp3FileAndOtherFile(count);
		loginPage.logout();
	}
	
	/**
	 * Author :Brundha Test Case Id:RPMXCON-49566 Description :Verify Ingestion with
	 * Email metadata if 'Email Name and Address' is in incorrect format
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49566",enabled = true, groups = { "regression" } )
	public void verifyingMetadataInDocListPage() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-49566");
		baseClass.stepInfo("Verify Ingestion with Email metadata if 'Email Name and Address' is in incorrect format");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);

		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
		System.out.println(status);
		if (status == false) {
			
			baseClass.stepInfo("Edit of addonly saved ingestion with mapping field selection");
			ingestionPage.IngestionRegressionForDifferentDAT(Input.GD994NativeTextForProductionFolder,Input.ingestionType,
					Input.sourceSystem, Input.datFormatFile, "DAT4_STC_NativesEmailData NEWID.lst",
					"DAT4_STC_TextEmailData NEWID.lst",null, null, null, null, null, null);
		}
		String[] addEmailColumn = { "EmailAuthorName", "EmailAuthorAddress" };
		DataSets dataSets=new DataSets(driver);
		dataSets.navigateToDataSetsPage();
		dataSets.selectDataSetWithName(Input.GD994NativeTextForProductionFolder);

		DocListPage doc = new DocListPage(driver);
		doc.SelectColumnDisplayByRemovingExistingOnes(addEmailColumn);
		doc.emailAuthorAddressParentheses("EMAILAUTHORADDRESS");
		doc.verifyingEmailMetadataInDocListPage("EMAILAUTHORNAME");
		loginPage.logout();

	}
	
	/**
	 * Author :Brundha Test Case Id:RPMXCON-48202 Description :To Verify Ingestion
	 * overlay of TIFF without Unpublish
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48202",enabled = true, groups = { "regression" })
	public void verifyTiffImageInDocViewPage() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48202");
		baseClass.stepInfo("To Verify Ingestion overlay of TIFF without Unpublish");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		 boolean status=ingestionPage.verifyIngestionpublish(Input.TiffImagesFolder);
		
		if (status == false) {

			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem,
					Input.sourceLocation, Input.TiffImagesFolder);
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);
			ingestionPage.selectDATSource(Input.DATFile3, Input.prodBeg);
			ingestionPage.selectTIFFSource(Input.tiffFile2, false, true);
			ingestionPage.selectPDFSource("DAT4_STC_PDFs.lst", false);
			ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
			ingestionPage.clickOnNextButton();
			ingestionPage.fillingSourceField();
			ingestionPage.clickOnPreviewAndRunButton();
			ingestionPage.publishAddonlyIngestion(Input.TiffImagesFolder);
		}

		DataSets dataSets=new DataSets(driver);
		dataSets.navigateToDataSetsPage();
		dataSets.selectDataSetWithNameInDocView(Input.TiffImagesFolder);
		
		DocViewPage doc = new DocViewPage(driver);
		doc.clickOnImageTab();
		for (int i = 0; i < 5; i++) {
			doc.getImageTabOption(Input.TiffImagesFolder).isElementAvailable(10);
			if (doc.getImageTabOption(Input.TiffImagesFolder).isDisplayed()) {
				baseClass.passedStep("Tiff image file is displayed as expected");
				break;
			} else {
				baseClass.failedStep("Tiff image file is not displayed as expected");
			}

		}
		loginPage.logout();
	}


	/**
	 * Author :Brundha Test Case Id:RPMXCON-48201 
	 * Description :To Verify Ingestion overlay of Native without Unpublish
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48201",enabled = true, groups = { "regression" })
	public void verifyUnPublishOfNativeDocument() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48201");
		baseClass.stepInfo("To Verify Ingestion overlay of Native without Unpublish");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo(" addonly ingestion with mapping field selection");
			ingestionPage.IngestionRegressionForDifferentDAT(Input.GD994NativeTextForProductionFolder,"Add Only",
					Input.sourceSystem, Input.datFormatFile,null,null,null, null, null, null, null, null);
					
		}
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.overlayOnly, Input.sourceSystem,
				Input.sourceLocation, Input.GD994NativeTextForProductionFolder);
		ingestionPage.addDelimitersInIngestionWizard( Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		ingestionPage.selectDATSource(Input.datFormatFile,Input.docId);
		ingestionPage.selectNativeSource("DAT4_STC_NativesEmailData NEWID.lst",false);
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		ingestionPage.clickOnNextButton();
		ingestionPage.clickOnPreviewAndRunButton();
		ingestionPage.ignoreErrorsAndCatlogging();
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();
		
		DataSets dataSets=new DataSets(driver);
		dataSets.navigateToDataSetsPage();
		dataSets.selectDataSetWithNameInDocView(Input.GD994NativeTextForProductionFolder);
		DocViewPage doc = new DocViewPage(driver);
		for (int i = 0; i < 5; i++) {
			if (doc.getDocView_TextFileType().isDisplayed()) {
				String FileType=doc.getDocView_TextFileType().getText();
				baseClass.textCompareEquals(FileType,"NATIVE",""+FileType+" file is displayed as expected",""+FileType+" file is not displayed as expected");
				break;
			} else {
				driver.waitForPageToBeReady();
			}
		}
		
		loginPage.logout();
	}
	
	/**
	 * Author :Brundha Test Case Id:RPMXCON-48237 
	 * Description :To verify In Ingestion User should be able to ignore Audio Indexing error and move ahead with Ingestion
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48237",enabled = true, groups = { "regression" })
	public void verifyAudioPlayerReadyLanguage() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48237");
		baseClass.stepInfo("To verify In Ingestion User should be able to ignore Audio Indexing error and move ahead with Ingestion");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		baseClass.stepInfo(" addonly ingestion with mapping field selection");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation("Add Only", Input.sourceSystem,Input.sourceLocation, Input.AK_NativeFolder);
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		ingestionPage.selectDATSource(Input.DATFile1,Input.prodBeg);
		ingestionPage.selectMP3VarientSource(Input.MP3File, false);
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		ingestionPage.clickOnNextButton();
		ingestionPage.fillingSourceField();
		ingestionPage.clickOnPreviewAndRunButton();
		ingestionPage.ignoreErrorsAndCatlogging();
		ingestionPage.ignoreErrorsAndCopying();
		ingestionPage.IgnoreErrorAndIndexing();
		loginPage.logout();
		
	}
	
	/**
	 * Author :Vijaya.Rani date: 10/5/2022 Modified date: Modified by: Description:
	 * To Verify Ingestion Overlays of PDF without unpublish. 'RPMXCON-46875'
	 */
	@Test(description ="RPMXCON-46875",enabled = true, groups = { "regression" })
	public void verifyingestionOverlayWithoutUnpublish() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		dataSets = new DataSets(driver);
		savedSearch = new SavedSearch(driver);
		DocViewPage docview = new DocViewPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-46875");
		baseClass.stepInfo("To Verify Ingestion Overlays of PDF without unpublish.");

		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Performing add only ingestion");
			ingestionPage.allSourcesIngestionWithText(Input.DATFile, Input.prodBeg);
			ingestionPage.publishAddonlyIngestion(Input.AllSourcesFolder);
		}
		// perform overlay ingestion with pdf
		ingestionPage.OverlayIngestionWithoutDat(Input.AllSourcesFolder, "Pdf", Input.PDFFile);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();
		
		String ingestionName = dataSets.isDataSetisAvailable(Input.AllSourcesFolder);
		System.out.println(ingestionName);
		sessionSearch.basicSearchWithMetaDataQuery(ingestionName, "IngestionName");
		sessionSearch.viewInDocView();
		baseClass.waitForElement(docview.getDocView_DefaultViewTab());
		docview.getDocView_DefaultViewTab().waitAndClick(5);
		String text = docview.getDocViewDefaultViewPDF().getText();
		if (text.contains("PDF")) {
			baseClass.passedStep(
					"PDF is displays in PDF viewer");
		} else {
			baseClass.failedStep("There is no such message");
		}
		loginPage.logout();
	}
	
	
	/**
	 * Author: Vijaya.Rani date: 04/05/2022 Modified date: NA Modified by: NA
	 * Description :Verify Ingestion should published successfully if Email metadata is having only Name.'RPMXCON-49569'
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49569",enabled = true, groups = { "regression" })
	public void verifyIngestionEmailMetaDataOnlyName() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		dataSets = new DataSets(driver);
		DocListPage docList = new DocListPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49569");
		baseClass.stepInfo("Verify Ingestion should published successfully if Email metadata is having only Name.");
		String[] addEmailColumn = { "EmailAuthorNameAndAddress", "EmailBCCNamesAndAddresses", "EmailCCNamesAndAddresses", "EmailToNamesAndAddresses" };
		
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Performing add only ingestion");
			ingestionPage.IngestionOnlyForDatFile(Input.GD994NativeTextForProductionFolder, Input.datFormatFile);
			ingestionPage.publishAddonlyIngestion(Input.GD994NativeTextForProductionFolder);
		}
		
		String ingestionName = dataSets.isDataSetisAvailable(Input.GD994NativeTextForProductionFolder);
		System.out.println(ingestionName);
		sessionSearch.basicSearchWithMetaDataQuery(ingestionName, "IngestionName");
		sessionSearch.ViewInDocList();
		
		docList.SelectColumnDisplayByRemovingExistingOnes(addEmailColumn);
		driver.waitForPageToBeReady();
		for(String metadata : addEmailColumn) {
			baseClass.visibleCheck(metadata);
		}
		baseClass.stepInfo("Email metadata is display correctly in doc list");
		
		//verify Emailname is Display
		for(int i=1;i<=8;i++) {
		String emailName = docList.getDataInDoclist(i,4).getText();
		System.out.println(emailName);
		if(docList.getDataInDoclist(i,4).Displayed() && emailName.equalsIgnoreCase(Input.emailName)) {
			baseClass.passedStep("Email name displayed and email address is blank");
		}
		else {
			System.out.println("Email name and address not displayed correctly for this document");
		}
		}
		loginPage.logout();
		}
	
	/**
	* Author :Aathith Test Case Id:RPMXCON-49567 
	* Description :Verify Ingestion with Email metadata if 'NamesAndAddresses' with different format
	*/
	@Test(description ="RPMXCON-49567",enabled = true, groups = { "regression" })
	public void verifyingNamesAndAddressesMetadataInDocListPage() throws InterruptedException {
		
	loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
	baseClass.stepInfo("Logged in as PA");	
	baseClass.stepInfo("Test case Id: RPMXCON-49567");
	baseClass.stepInfo("Verify Ingestion with Email metadata if 'NamesAndAddresses' with different format");

	ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
	boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
	System.out.println(status);
	if (status == false) {
		baseClass.stepInfo("Performing add only ingestion");
		ingestionPage.IngestionOnlyForDatFile(Input.GD994NativeTextForProductionFolder, Input.datFormatFile);
		ingestionPage.publishAddonlyIngestion(Input.GD994NativeTextForProductionFolder);
	}
	
	String[] addEmailColumn = { "EmailAuthorNameAndAddress"};
	DataSets dataset = new DataSets(driver);
	dataset.selectDataSetWithName(Input.GD994NativeTextForProductionFolder);

	DocListPage docList = new DocListPage(driver);
	docList.SelectColumnDisplayByRemovingExistingOnes(addEmailColumn);
	
	for(int i=1;i<=8;i++) {
		String emailName = docList.getDataInDoclist(i,4).getText();
		System.out.println(emailName);
		if(docList.getDataInDoclist(i,4).Displayed() && emailName.equalsIgnoreCase(Input.emailAddress1) || emailName.equalsIgnoreCase(Input.emailAddress2) ) {
			baseClass.passedStep("Email name not displayed with enclosed paranthesis and email address displayed in enclosed paranthesis");
		}
		else {
			System.out.println("Email name and address not displayed correctly for this document");
		}
		}
	
	baseClass.passedStep("Verified Ingestion with Email metadata if 'NamesAndAddresses' with different format");
	loginPage.logout();
	}
	
	/**
	 * Author :Aathith date: NA Modified date: Modified by: Description : To verify
	 * that if Email data contained space before the '@' sign , it should not
	 * calculate two distinct values
	 */
	@Test(description ="RPMXCON-49802",enabled = true, groups = { "regression" })
	public void verifyEmailDataNotCalculateAsDistintValue() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		dataSets = new DataSets(driver);
		SoftAssert sofassertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-49802");
		baseClass.stepInfo(
				"To verify that if Email data contained space before the '@' sign , it should not calculate two distinct values");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.IngestionEmailDataFolder);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Performing add only ingestion");
			ingestionPage.IngestionOnlyForDatFile(Input.IngestionEmailDataFolder, Input.emailDatFile);
			ingestionPage.publishAddonlyIngestion(Input.IngestionEmailDataFolder);
		}
		
		String ingestionFullName = dataSets.isDataSetisAvailable(Input.IngestionEmailDataFolder);
		if (ingestionFullName != null) {
			baseClass.stepInfo(ingestionFullName + "Ingestion already published in this project");
			int count = sessionSearch.MetaDataSearchInBasicSearch(Input.emailAuthorDomain, " @");
			sessionSearch.addNewSearch();
			int count1 = sessionSearch.newMetaDataSearchInBasicSearch(Input.emailAuthorDomain, " @ ");
			if (count != 0) {
				if (count == count1) {
					sofassertion.assertEquals(count1, count);
					baseClass.passedStep(
							"It displays result correctly if Email data contained space before '@' sign . Also  if Email data contained space before and after the '@' sign , it not calculate two distinct values, it displays result correctly  ");
				} else {
					baseClass.failedStep("count is not equal");
				}
			} else {
				baseClass.failedMessage("Documents not present");
			}
		}
		baseClass.passedStep(
				"verified that if Email data contained space before the '@' sign , it should not calculate two distinct values");
		loginPage.logout();

	}
	
	/**
	 * Author :Aathith date: NA Modified date: Modified by: Description : Verify
	 * Email metadata in DocList and in DocView
	 */
	@Test(description ="RPMXCON-49558",enabled = true, groups = { "regression" })
	public void verifyingEmailMetaDataInDoclistDocview() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		dataSets = new DataSets(driver);
		tagAndFolder = new TagsAndFoldersPage(driver);
		docList = new DocListPage(driver);
		docView = new DocViewPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-49558");
		baseClass.stepInfo("Verify Email metadata in DocList and in DocView");

		String foldername = "IngestionFolder" + Utility.dynamicNameAppender();
		String[] addEmailColumn = { "EmailAuthorName", "EmailAuthorAddress", "EmailToNames", "EmailToAddresses",
				"EmailCCNames", "EmailCCAddresses", "EmailBCCNames", "EmailBCCAddresses" };
		tagAndFolder.CreateFolder(foldername, Input.securityGroup);

		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.IngestionEmailDataFolder);
		if(status ==false) {
			ingestionPage.IngestionOnlyForDatFile(Input.IngestionEmailDataFolder, Input.emailDatFile);
			ingestionPage.publishAddonlyIngestion(Input.IngestionEmailDataFolder);
		}
		
		String ingestionFullName = dataSets.isDataSetisAvailable(Input.IngestionEmailDataFolder);
		if (ingestionFullName != null) {

			sessionSearch.MetaDataSearchInBasicSearch(Input.metadataIngestion, ingestionFullName);
			sessionSearch.bulkReleaseIngestions(Input.securityGroup);
			sessionSearch.bulkFolderExisting(foldername);
			sessionSearch.ViewInDocListWithOutPureHit();

			docList.SelectColumnDisplayByRemovingExistingOnes(addEmailColumn);
			driver.waitForPageToBeReady();
			for (String metadata : addEmailColumn) {
				baseClass.visibleCheck(metadata);
			}
			baseClass.stepInfo("Email metadata is display correctly in doc list");

			docList.selectAllDocs();
			docList.viewSelectedDocumentsInDocView();
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
				docView.selectSourceDocIdInAvailableField(metadata);
			}
			for (String metadata : addEmailColumn) {
				baseClass.visibleCheck(metadata);
			}
			baseClass.stepInfo(
					"In Doc View -Mini Doc List , Email Metadata like EmailAuthorName, EmailAuthorAddress etc. is displayed");

			docView.verifyTheadMapValue(10, "participant");

			loginPage.logout();
			baseClass.stepInfo("perform task for review manager");
			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			tagAndFolder.selectFolderViewInDocList(foldername);

			docList.SelectColumnDisplayByRemovingExistingOnes(addEmailColumn);
			driver.waitForPageToBeReady();
			for (String metadata : addEmailColumn) {
				baseClass.visibleCheck(metadata);
			}
			baseClass.stepInfo("Email metadata is display correctly in doc list");

			docList.selectAllDocs();
			docList.viewSelectedDocumentsInDocView();
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
				docView.selectSourceDocIdInAvailableField(metadata);
			}
			for (String metadata : addEmailColumn) {
				baseClass.visibleCheck(metadata);
			}
			baseClass.stepInfo(
					"In Doc View -Mini Doc List , Email Metadata like EmailAuthorName, EmailAuthorAddress etc. is displayed");

			docView.verifyTheadMapValue(10, "participant");
		}
		baseClass.passedStep("Verified Email metadata in DocList and in DocView");
		loginPage.logout();

	}
	
	
	/**
	 * Author :Aathith date: NA Modified date: Modified by: 
	 * Description : To verify that for image based document Sightline should receive 'RequirePDFGeneration' as set to 'true', 
	 * by ICE and 'RequirePDFGeneration' metadata should be displays in Doc View
	 */
	@Test(description ="RPMXCON-49364",enabled = true, groups = { "regression" })
	public void verifyMetaDataRequiredPDFGenereationIsTrue() throws InterruptedException {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		dataSets = new DataSets(driver);
		docView = new DocViewPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-49364");
		baseClass.stepInfo("To verify that for image based document Sightline should receive 'RequirePDFGeneration' as set to 'true',"
				+ " by ICE and 'RequirePDFGeneration' metadata should be displays in Doc View");
		
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.GNon_searchable_PDF_Load_file);
		if(!status) {
			
			ingestionPage.IngestionOnlyForDatFile(Input.GNon_searchable_PDF_Load_file, Input.nonSearchablePdfLoadFile);
			ingestionPage.publishAddonlyIngestion(Input.GNon_searchable_PDF_Load_file);
			
		}
		String ingestionFullName = dataSets.isDataSetisAvailable(Input.GNon_searchable_PDF_Load_file);
		if(ingestionFullName!=null) {
			dataSets.selectDataSetWithNameInDocView(Input.GNon_searchable_PDF_Load_file);
			driver.waitForPageToBeReady();
			String value = docView.getMetadataFieldValueText("RequirePDFGeneration").getText().trim();
			if(value.equals("0")) {
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
	 * Author :Vijaya.Rani date: 06/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-48189 Description :To Verify Unpublish for Already published
	 * Documents after ingestionPage.
	 * 
	 */
	@Test(description ="RPMXCON-48189",enabled = true, groups = { "regression" })
	public void verifyUnpublishForAlreadyPublishedDocsIngestion() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		dataSets = new DataSets(driver);
		savedSearch = new SavedSearch(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-48189");
		baseClass.stepInfo("To Verify Unpublish for Already published Documents after ingestionPage.");
		String BasicSearchName = "Newone" + Utility.dynamicNameAppender();
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		
		boolean status = ingestionPage.verifyIngestionpublish(Input.UniCodeFilesFolder);
		if(!status) {
			
			ingestionPage.unicodeFilesIngestion(Input.datLoadFile1, Input.textFile1, Input.documentKey);
			ingestionPage.publishAddonlyIngestion(Input.UniCodeFilesFolder);
			
		}
		String ingestionFullName = dataSets.isDataSetisAvailable(Input.UniCodeFilesFolder);
		
		sessionSearch.basicSearchWithMetaDataQuery(ingestionFullName, "IngestionName");
		// Saved the My SavedSearch
		sessionSearch.saveSearch(BasicSearchName);
		sessionSearch.unReleaseDocsFromSecuritygroup(Input.securityGroup);

		// Go to UnpublishPage
		ingestionPage.navigateToUnPublishPage();
		ingestionPage.unpublish(BasicSearchName);
		
		sessionSearch.basicSearchWithMetaDataQuery(ingestionFullName, "IngestionName");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearch.getPureHitsCount().Visible();
			}
		}), Input.wait30);
		int docCount = Integer.parseInt(sessionSearch.getPureHitsCount().getText());
		if(docCount==0) {
			baseClass.passedStep("Document count is 0");
		}
		else {
			baseClass.failedStep("Document count is not 0");
		}
		loginPage.logout();
			
	}
	
	/**
	 * @author Aathith
	 * @throws InterruptedException 
	 * //@TestCase id: 46894 : Verify the overlay Ingestion for Audio Documents against International English language pack
	 */
	@Test(description ="RPMXCON-46894",enabled = true, groups = { "regression" })
	public void verifyAudioDocumentInternationEnglish() throws InterruptedException {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-46894 ");
		baseClass.stepInfo(
				"### Verify the overlay Ingestion for Audio Documents against International English language pack. ###");
		
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Performing add only ingestion");
			ingestionPage.allSourcesIngestionWithText(Input.DATFile, Input.prodBeg);
			ingestionPage.publishAddonlyIngestion(Input.AllSourcesFolder);
		}
		//perform overlay with mp3
		ingestionPage.OverlayIngestionWithoutDat(Input.AllSourcesFolder, "mp3", Input.MP3File);
		ingestionPage.ignoreErrorsAndCatlogging();
		ingestionPage.ignoreErrorsAndCopying();
		ingestionPage.verifyLanguageIsSelectable(Input.audioLanguage);
		baseClass.passedStep(
				"Verified the overlay Ingestion for Audio Documents against International English language pack");
		loginPage.logout();

	}
	
	/**
	 * Author :Vijaya.Rani date: 10/5/2022 Modified date: Modified by: 
	 * Description : Verify the overlay Ingestion for Audio Documents against International English language pack
	 * 'RPMXCON-48526'
	 * 
	 */
	@Test(description ="RPMXCON-48526",enabled = true, groups = { "regression" })
	public void verifyAudioDocumentOverlayInternationEnglish() throws InterruptedException {
		
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		
		dataSets = new DataSets(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-48526 ");
		baseClass.stepInfo(
				"### Verify the overlay Ingestion for Audio Documents against International English language pack ###");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Performing add only ingestion");
			ingestionPage.allSourcesIngestionWithText(Input.DATFile, Input.prodBeg);
			ingestionPage.publishAddonlyIngestion(Input.AllSourcesFolder);
		}
		//perform overlay with mp3
		ingestionPage.OverlayIngestionWithoutDat(Input.AllSourcesFolder, "mp3", Input.MP3File);
		ingestionPage.ignoreErrorsAndCatlogging();
		ingestionPage.ignoreErrorsAndCopying();
		ingestionPage.verifyLanguageIsSelectable(Input.audioLanguage);
		baseClass.passedStep(
				"Verified the overlay Ingestion for Audio Documents against International English language pack");
		loginPage.logout();

	}
	
	/**
	 * @author Aathith
	 * @throws InterruptedException 
	 * @TestCase id: 49550 : Verify that in Ingestion Overlay if 'Generate Searchable PDFs'
	 *  is selected in TIFF section, then PDF should be generated from the TIFF's
	 */
	@Test(description ="RPMXCON-49550",enabled = true, groups = { "regression" })
	public void verifySearchablePdfTiffDocView() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-49550 ");
		baseClass.stepInfo(
				"###  Verify that in Ingestion Overlay if 'Generate Searchable PDFs' is selected in TIFF section, then PDF should be generated from the TIFF's ###");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();

		boolean status = ingestionPage.verifyIngestionpublish(Input.TiffImagesFolder);
		if(!status) {
			ingestionPage.tiffImagesIngestion(Input.DATFile2, Input.tiffLoadFile, "false");
			ingestionPage.publishAddonlyIngestion(Input.TiffImagesFolder);
			
		}
		// Perform overlay ingestion
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation("Overlay Only", Input.sourceSystem, Input.sourceLocation, Input.TiffImagesFolder);
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);
		ingestionPage.selectDATSource(Input.DATFile2, Input.prodBeg);
		ingestionPage.getTIFFLST().selectFromDropdown().selectByVisibleText(Input.tiffLoadFile);
		ingestionPage.getTIFFSearchablePDFCheckBox().isElementAvailable(10);
		ingestionPage.getTIFFSearchablePDFCheckBox().waitAndClick(5);
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
		ingestionPage.clickOnNextButton();
		ingestionPage.clickOnPreviewAndRunButton();
		ingestionPage.ignoreErrorsAndCatlogging();
		ingestionPage.ignoreErrorsAndCopying();
		ingestionPage.ingestionIndexing(Input.TiffImagesFolder);
		ingestionPage.approveIngestion(1);
		ingestionPage.runFullAnalysisAndPublish();
		
		dataSets = new DataSets(driver);
		DocViewPage docview = new DocViewPage(driver);
		String ingestionFullName = dataSets.isDataSetisAvailable(Input.TiffImagesFolder);
		if(ingestionFullName!=null) {
			dataSets.selectDataSetWithNameInDocView(Input.TiffImagesFolder);
			
			String filetype=docview.getFileType().getText().trim();
			System.out.println(filetype);
			if(filetype.isEmpty()) {
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
	 * Author :Aathith date: NA Modified date: Modified by: Description : Verify
	 * that if "Generate Searchable PDF " check box is not selected in the TIFF
	 * section, Ingestion should generate successfully with TIFF images only
	 */
	@Test(description ="RPMXCON-49491",enabled = true, groups = { "regression" })
	public void verifyTiffImageOnlyDisplayed() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		dataSets = new DataSets(driver);
		docView = new DocViewPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-49491");
		baseClass.stepInfo(
				"Verified that if \"Generate Searchable PDF \" check box is not selected in the TIFF section, Ingestion should generate successfully with TIFF images only");

		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.TiffImagesFolder);
		if(!status) {
			ingestionPage.tiffImagesIngestion("DAT4_STC_NewDateFormat - Copy.dat", Input.tiffLoadFile, "false");
			ingestionPage.publishAddonlyIngestion(Input.TiffImagesFolder);
			
		}
		String ingestionFullName = dataSets.isDataSetisAvailable(Input.TiffImagesFolder);
		if (ingestionFullName != null) {
			dataSets.selectDataSetWithNameInDocView(Input.TiffImagesFolder);
			docView.getImageTab().waitAndClick(10);
			driver.waitForPageToBeReady();
			if (docView.getDocViewImage().isElementAvailable(10)) {
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
	 * @author Aathith
	 * @throws InterruptedException //@TestCase id: 49547 : Verify Count of Generate
	 *                              Searchable PDFs if 'Required PDF Generation' is
	 *                              TRUE and 'searchable PDF for TIFFs' is TRUE
	 */
	@Test(description ="RPMXCON-49547",enabled = true, groups = { "regression" })
	public void verifySearchablePdfCount() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-49547 ");
		baseClass.stepInfo(
				"###  Verify Count of Generate Searchable PDFs if 'Required PDF Generation' is TRUE and 'searchable PDF for TIFFs' is TRUE ###");
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		
		boolean status = ingestionPage.verifyIngestionpublish(Input.PDFGen_10Docs);
		if(!status) {
			ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,Input.ingestionType, "TRUE", Input.DATPPPDF10Docs, null,
					Input.TextPPPDF10Docs, null, Input.ImagePPPDF10docs,"select", null, null, null);
		}

			baseClass.stepInfo("Select ingestion type and specify source loaction.");
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.overlayOnly, Input.sourceSystem, Input.sourceLocation,
					Input.PDFGen_10Docs);

			baseClass.stepInfo("Select DAT delimiters.");
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);

			baseClass.stepInfo("Select DAT source.");
			ingestionPage.selectDATSource(Input.newdateformat_5Docs, Input.prodBeg);

			baseClass.stepInfo("Select MP3 varient source.");
			ingestionPage.selectPDFSource(Input.PDF5DocsLst, false);

			ingestionPage.selectTIFFSource(Input.Images5DocsLst, false, true);

			baseClass.stepInfo("Select Date and Time format.");
			ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
			baseClass.stepInfo("Click on next button.");
			ingestionPage.clickOnNextButton();
			baseClass.stepInfo("Click on preview and run button.");
			ingestionPage.clickOnPreviewAndRunButton();
			ingestionPage.verifyApprovedStatusForOverlayIngestion();
			baseClass.stepInfo("Verify count of searchable pdf");
			ingestionPage.searchablePdfCountCheck();

		baseClass.passedStep(
				"Verified Count of Generate Searchable PDFs if 'Required PDF Generation' is TRUE and 'searchable PDF for TIFFs' is TRUE");
		loginPage.logout();

	}
	
	/** cannot run in batch run as we need to perform redaction and overlay
	 * Author :Vijaya.Rani date: 05/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-48013 Description :To Verify In Ingestion Overlays Ignore All
	 * Errors at Cataloge Stage, Should work.
	 * 
	 */
	@Test(description ="RPMXCON-48013",enabled = true, groups = { "regression" })
	public void verifyIngestionOverlayIgnoreAllErrorsAndCatalogStage() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password, Input.ingestDataProject);		
		baseClass.stepInfo("Logged in as PA");
		baseClass.stepInfo("Test case Id: RPMXCON-48013");
		baseClass.stepInfo("To Verify In Ingestion Overlays Ignore All Errors at Cataloge Stage, Should work.");
		
		String foldername = "RedactionFolder" + Utility.dynamicNameAppender();
		String BasicSearchName = "Search" + Utility.dynamicNameAppender();
		tagAndFolder = new TagsAndFoldersPage(driver);
		tagAndFolder.CreateFolder(foldername, Input.securityGroup);
		dataSets = new DataSets(driver);
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		
		boolean status = ingestionPage.verifyIngestionpublish(Input.HiddenPropertiesFolder);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Addonly saved ingestion with mapping field selection And Publish");
			ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "MM/DD/YYYY",Input.DAT_MMDDYYYY_Slash,Input.Natives_MMDDYYYY_Slash);
			ingestionPage.publishAddonlyIngestion(Input.HiddenPropertiesFolder);
		}
		
		String ingestionFullName = dataSets.isDataSetisAvailable(Input.HiddenPropertiesFolder);
		sessionSearch.MetaDataSearchInBasicSearch(Input.metadataIngestion, ingestionFullName);
		sessionSearch.saveSearch(BasicSearchName);
		sessionSearch.bulkFolderExisting(foldername);
		sessionSearch.bulkReleaseIngestions(Input.securityGroup);
		
		loginPage.logout();
		// perform redaction
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		tagAndFolder.selectFolderViewInDocView(foldername);
		DocViewPage docViewPage = new DocViewPage(driver);
		docViewPage.nonAudioPageRedaction(Input.defaultRedactionTag);
		loginPage.logout();
		//perform unpublish
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		ingestionPage.navigateToUnPublishPage();
		ingestionPage.unpublish(BasicSearchName);
		//perform overlay ingestion
		ingestionPage.OverlayIngestionWithDat(Input.HiddenPropertiesFolder,Input.DAT_MMDDYYYY_Slash,Input.sourceDocIdSearch,"Native",Input.Natives_MMDDYYYY_Slash);
		ingestionPage.ignoreErrorsAndCatlogging();
		baseClass.passedStep("In overlay type ingestion,ignored all errors successfully");
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
