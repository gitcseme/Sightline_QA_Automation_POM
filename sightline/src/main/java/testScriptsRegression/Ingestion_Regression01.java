package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

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
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.IngestionPage_Indium;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Ingestion_Regression01 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	DocViewPage docView;
	SessionSearch sessionSearch;
	Utility utility;
	DataSets dataSets;
	IngestionPage_Indium ingestionPage;
	Input ip;
	SavedSearch savedSearch;

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
		loginPage = new LoginPage(driver);

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
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 1)
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
		sessionSearch = new SessionSearch(driver);
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

	}

	/**
	 * Author : Mohan date: 22/04/22 NA Modified date: NA Modified by:NA Description
	 * :Verify that documents ingested with the Date Format than the sightline
	 * standard format should be viewable in Doc View with metadata'RPMXCON-49543'
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 2)
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
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(fieldName);

		baseClass.stepInfo(
				"Step 3 : Search the documents and drag the result to shopping cartSelect action as 'View in DocView'");
		sessionSearch.ViewInDocView();

		baseClass.stepInfo("Step 4 : Verify the Date fields from the metadata");
		docView = new DocViewPage(driver);
		docView.selectMeatdataGetFieldValue(fieldName);

	}

	/**
	 * Author : Mohan date: 22/04/22 NA Modified date: NA Modified by:NA Description
	 * :Verify that documents ingested with the Date Format than the sightline
	 * standard format should be viewable in Doc List with metadata'RPMXCON-49544'
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 3)
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
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(fieldName);

		baseClass.stepInfo(
				"Step 3 : Search the documents and drag the result to shopping cartSelect action as 'View in DocList'");
		sessionSearch.ViewInDocList();

		baseClass.stepInfo("Step 4 : Verify the Date fields from the metadata");
		DocListPage docListPage = new DocListPage(driver);
		docListPage.verifyMasterDateColumnValue();

	}

	/**
	 * Author : Mohan date: 17/02/22 NA Modified date: NA Modified by:NA Description
	 * :Verify 'Source System' is disabled if user select Ingestion-Overlay on
	 * Ingestion Wizard page'RPMXCON-58508'
	 * 
	 * @throws Exception
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 4)
	public void verifySourceSystemIsDisabled() throws Exception {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);

		baseClass.stepInfo("Test case Id: RPMXCON-58508");
		baseClass.stepInfo(
				"Verify 'Source System' is disabled if user select Ingestion-Overlay on Ingestion Wizard page");
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Step 2: Create an new Ingestion");
		ingestionPage.IngestionRegression(Input.AllSourcesFolder);
		baseClass.stepInfo(
				"Step 3&4 : Go to Ingestion Add New Ingestion and Ingestion Type as 'Overlay Only' and Ingestion Type as 'Overlay Only'");
		ingestionPage.verifySourceSystemDisabledWhenOverylayIsAddedAsIngestionType();

	}

	/**
	 * Author :Arunkumar date: 08/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-49022 Description :Verify two ingestions with step (Indexing
	 * , Approval ) having ingestion type add only must run simultaneously
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 5)
	public void verifyTwoIngestionRunTillApprovingSimultaneously() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);
		String[] dataset = { Input.AllSourcesFolder, Input.TiffImagesFolder };

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49022");
		baseClass.stepInfo(
				"Verify two ingestions with step (Indexing  , Approval ) having ingestion type add only  must run simultaneously");
		// Verify two add only type ingestion run simultaneously
		ingestionPage.IngestionOnlyForDatFile(Input.AllSourcesFolder, Input.DATFile1);
		ingestionPage.IngestionOnlyForDatFile(Input.TiffImagesFolder, Input.DATFile3);
		ingestionPage.multipleIngestionCopying(2);
		ingestionPage.multipleIngestionIndexing(dataset, 2);
		ingestionPage.approveIngestion(2);
	}

	/**
	 * Author :Arunkumar date: 08/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-47594 Description :To Verify for Approved ingestions, there
	 * should not have any option for Rollback.
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 6)
	public void verifyRollbackStatusForApprovedIngestion() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47594");
		baseClass.stepInfo("To Verify for Approved ingestions, there should not have any option for Rollback.");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return ingestionPage.getFilterByButton().Visible();
			}
		}), Input.wait30);
		ingestionPage.getFilterByButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return ingestionPage.getFilterByAPPROVED().Visible();
			}
		}), Input.wait30);
		ingestionPage.getFilterByAPPROVED().waitAndClick(10);
		ingestionPage.getRefreshButton().waitAndClick(10);
		// Verify rollback option for approved ingestion
		if (ingestionPage.getIngestionDetailPopup(1).isElementAvailable(5)) {
			baseClass.stepInfo("Ingestion already present in approved stage");
			ingestionPage.verifyRollbackOptionForApprovedIngestion();
		} else {
			baseClass.stepInfo("Need to perform new ingestion");
			ingestionPage.IngestionOnlyForDatFile(Input.AllSourcesFolder, Input.DATFile1);
			ingestionPage.IngestionCatlogtoCopying(Input.AllSourcesFolder);
			ingestionPage.ingestionIndexing(Input.AllSourcesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.verifyRollbackOptionForApprovedIngestion();

		}
	}

	/**
	 * Author :Arunkumar date: 22/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-47598 Description :Edit of saved Overlay ingestion with out
	 * mapping field selection.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 7)
	public void verifyEditOverlayIngestionWithoutMapping() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47598");
		baseClass.stepInfo("Edit of saved Overlay ingestion with out mapping field selection.");
		ingestionPage.OverlayIngestionForDATWithoutMappingFieldSection(Input.HiddenPropertiesFolder,
				Input.YYYYMMDDHHMISSDat, Input.sourceDocIdSearch);
		baseClass.stepInfo("Saved ingestion as draft without clicking next button and mapping field in disabled state");
		ingestionPage.verifyIngestionStatusAfterSaveAsDraft();
		// click on open in wizard option to edit ingestion
		ingestionPage.IngestionFromDraftMode();
		ingestionPage.ingestionCatalogging();

	}

	/**
	 * Author :Arunkumar date: 22/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-47597 Description :Edit of Overlay saved ingestion with
	 * mapping field selection
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 8)
	public void verifyEditOverlayIngestionWithMapping() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47597");
		baseClass.stepInfo("Edit of Overlay saved ingestion with mapping field selection");
		ingestionPage.OverlayIngestionForDATWithMappingFieldSection(Input.HiddenPropertiesFolder,
				Input.YYYYMMDDHHMISSDat, Input.sourceDocIdSearch);
		baseClass.stepInfo("Saved ingestion as draft after clicking next button and mapping field enabled");
		ingestionPage.verifyIngestionStatusAfterSaveAsDraft();
		// click on open in wizard option to edit ingestion
		ingestionPage.IngestionFromDraftMode();
		ingestionPage.ingestionCatalogging();

	}

	/**
	 * Author :Arunkumar date: 20/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-47293 Description :Ingesting Duplicate files.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 9)
	public void verifyErrorForIngestingDuplicateFiles() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47293");
		baseClass.stepInfo("Ingesting Duplicate files.");
		ingestionPage.IngestionOnlyForDatFile(Input.AllSourcesFolder, Input.DATFile1);
		// verify duplicate ingestion error
		ingestionPage.verifyDuplicateIngestionErrorMessage();
		// rollback ingestion
		ingestionPage.rollBackIngestion();
	}

	/**
	 * Author :Arunkumar date: 26/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-47600 Description :Edit of saved Overlay with out ingestion
	 * with out mapping field selection.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 10)
	public void verifyEditOverlayIngestionWithoutDatAndIngestionMapping() {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47600");
		baseClass.stepInfo("Edit of saved Overlay with out DAT ingestion with out mapping field selection.");
		ingestionPage.OverlayForNativeWithoutIngestion(Input.HiddenPropertiesFolder, Input.YYYYMMDDHHMISSLst);
		baseClass.stepInfo("Saved ingestion as draft without Dat selection and mapping field in disabled state");
		ingestionPage.verifyIngestionStatusAfterSaveAsDraft();
		// click on open in wizard option to edit ingestion
		ingestionPage.IngestionFromDraftMode();
		ingestionPage.ingestionCatalogging();
	}

	/**
	 * Author :Arunkumar date: 26/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-47596 Description :Copy of Overlay saved ingestion with
	 * mapping field selection
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 11)
	public void verifyCopyOverlayIngestionWithMapping() {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47596");
		baseClass.stepInfo("Copy of Overlay saved ingestion with mapping field selection");
		ingestionPage.OverlayIngestionForDATWithMappingFieldSection(Input.HiddenPropertiesFolder,
				Input.YYYYMMDDHHMISSDat, Input.sourceDocIdSearch);
		baseClass.stepInfo("Saved ingestion as draft after clicking next button and mapping field enabled");
		ingestionPage.verifyIngestionStatusAfterSaveAsDraft();
		// click on copy option from draft mode
		ingestionPage.IngestionOverlayUsingCopyFromDraftMode("DAT", Input.HiddenPropertiesFolder,
				Input.YYYYMMDDHHMISSLst, Input.YYYYMMDDHHMISSDat, Input.sourceDocIdSearch);
		ingestionPage.ingestionCatalogging();

	}

	/**
	 * Author :Arunkumar date: 27/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-47599 Description :Copy of saved Overlay without DAT
	 * ingestion with out mapping field selection.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 12)
	public void verifyCopyOverlayIngestionWithoutDatAndMapping() {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47599");
		baseClass.stepInfo("Copy of saved Overlay without DAT ingestion with out mapping field selection.");
		ingestionPage.OverlayForNativeWithoutIngestion(Input.HiddenPropertiesFolder, Input.YYYYMMDDHHMISSLst);
		baseClass.stepInfo("Saved ingestion as draft without Dat selection and mapping field in disabled state");
		ingestionPage.verifyIngestionStatusAfterSaveAsDraft();
		// click on copy option from draft mode
		ingestionPage.IngestionOverlayUsingCopyFromDraftMode("Native", Input.HiddenPropertiesFolder,
				Input.YYYYMMDDHHMISSLst, Input.YYYYMMDDHHMISSDat, Input.sourceDocIdSearch);
		ingestionPage.ingestionCatalogging();

	}

	/**
	 * Author :Vijaya.Rani date: 27/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-49902 Description :Unpublish documents - Verify Search as
	 * source.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 13)
	public void verifyUnpublishDocumentsSource() throws InterruptedException {

		SessionSearch sessionSearch = new SessionSearch(driver);
		savedSearch = new SavedSearch(driver);
		String BasicSearchName = "Newone" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);

		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49902");
		baseClass.stepInfo("Unpublish documents - Verify Search as source.");

		baseClass.stepInfo("Search the documents and Save");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.saveSearchSharedWithPA(BasicSearchName);

		// Go to UnpublishPage
		ingestionPage.navigateToUnPublishPage();
		ingestionPage.unpublish(BasicSearchName);
	}

	/**
	 * Author :Arunkumar date: 04/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-47369 Description :To verify 'Ingestion Details' pop up
	 * display
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 14)
	public void verifyIngestionDetailsPopupDisplay() {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47369");
		baseClass.stepInfo("To verify 'Ingestion Details' pop up display");
		ingestionPage.verifyIngestionDetails();
	}

	/**
	 * Author :Arunkumar date: 05/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-47362 Description :To verify that on Ingestion Home page,
	 * user is able to access all page by navigation controls.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 15)
	public void verifyIngestionHomePageNavigation() {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47362");
		baseClass.stepInfo(
				"To verify that on Ingestion Home page, user is able to access all page by navigation controls.");
		ingestionPage.verifyHomePageNavigationControl();
	}

	/**
	 * Author :Vijaya.Rani date: 29/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-49545 Description :Verify that value for all the metadata
	 * fields having DATETIME/DATE data type.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 16)
	public void verifyMetaDataFieldsDateTimeInDataType() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49545");
		baseClass.stepInfo("Verify that value for all the metadata fields having DATETIME/DATE data type.");
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);

		ingestionPage = new IngestionPage_Indium(driver);
		boolean status = ingestionPage.verifyIngestionpublish(Input.HiddenPropertiesFolder);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Addonly saved ingestion with mapping field selection And Publish");
			ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "YYYY/MM/DD HH:MM:SS",
					Input.DAT_MMDDYYYY, Input.Natives_MMDDYYYY);
			ingestionPage.IngestionCatlogtoIndexing(Input.HiddenPropertiesFolder);
			ingestionPage.approveAndPublishIngestion(Input.HiddenPropertiesFolder);
		}
		baseClass.stepInfo("Search the documents and Save");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocList();
		// Remove selected Colunm Add new Column
		docList.SelectColumnDisplayByRemovingExistingOnesAddMultiipleColumns();
	}

	/**
	 * Author :Arunkumar date: 29/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-48175 Description :To Verify Rollback In Ingestion for Files
	 * having Read Only Attributes.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 17)
	public void verifyRollbackReadOnlyAttribute() {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48175");
		baseClass.stepInfo("To Verify Rollback In Ingestion for Files having Read Only Attributes.");
		ingestionPage.performAKNativeFolderIngestion(Input.DATFile1);
		ingestionPage.ingestionCatalogging();
		ingestionPage.ignoreErrorsAndCopying();
		ingestionPage.ingestionIndexing(Input.AK_NativeFolder);
		// Rollback
		ingestionPage.rollBackIngestion();

	}

	/**
	 * Author :Arunkumar date: 29/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-47566 Description :To Verify status update in cataloging,
	 * copying, and Indexing Section in Ingestion Details Page
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 18)
	public void verifyStatusUpdateInPopup() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47566");
		baseClass.stepInfo(
				"To Verify status update in cataloging, copying, and Indexing Section in Ingestion Details Page");
		ingestionPage.unicodeFilesIngestion(Input.datLoadFile1, Input.textFile1, Input.documentKey);
		ingestionPage.ingestionCatalogging();
		ingestionPage.verifyStatusUpdatenInIngestionDetailPopup("Catalogingblock");
		ingestionPage.ingestionCopying();
		ingestionPage.verifyStatusUpdatenInIngestionDetailPopup("Copyingblock");
		ingestionPage.ingestionIndexing(Input.UniCodeFilesFolder);
		ingestionPage.verifyStatusUpdatenInIngestionDetailPopup("Indexingblock");
		// rollback
		ingestionPage.rollBackIngestion();

	}

	/**
	 * Author :Arunkumar date: 02/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-49703 Description :Verify if 'Generate Searchable PDFs' is
	 * True for TIFF image and document has multi-page TIFF's then in the Copying
	 * section, Stitching TIFFs details should display before the "Generate
	 * Searchable PDFs" row on Ingestion details pop up
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 19)
	public void verifyStitchedTiffBeforeGeneratedSearchablePDFs() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49703");
		baseClass.stepInfo(
				"Verify if 'Generate Searchable PDFs' is True Stitching TIFFs details should display before the Generate Searchable PDFs row on Ingestion details pop up");
		ingestionPage.tiffImagesIngestion(Input.DATFile4, Input.tiffLoadFile, "true");
		ingestionPage.ignoreErrorsAndCatlogging();
		ingestionPage.ignoreErrorsAndCopying();
		ingestionPage.verifyTermPositionInCopyColumn(Input.StitchedTIFF);
		// Rollback Ingestion
		ingestionPage.rollBackIngestion();
	}

	/**
	 * Author :Arunkumar date: 02/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-48194 Description :To Verify In Ingestions the pop-up Details
	 * Window for media ;audio & Transcript Count.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 20)
	public void verifyMediaAndTranscriptDetailInPopup() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);

		String[] selectedTerm = { "Native", "Text", "MP3 Variant", "Audio Transcript" };
		String[] unselectedTerm = { "PDF", Input.StitchedTIFF, Input.generateSearchablePDF };

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48194");
		baseClass.stepInfo("To Verify In Ingestions the pop-up Details Window for media ;audio & Transcript Count.");
		ingestionPage.mediaAndTranscriptIngestion(Input.AllSourcesFolder, Input.DATFile1);
		ingestionPage.ignoreErrorsAndCatlogging();
		ingestionPage.ignoreErrorsAndCopying();
		for (int i = 0; i <= selectedTerm.length - 1; i++) {
			ingestionPage.verifyDataPresentInCopyTableColumn(selectedTerm[i], "source");
		}
		for (int i = 0; i <= unselectedTerm.length - 1; i++) {
			ingestionPage.verifyUnselectedSourceCountInCopySection(unselectedTerm[i]);
		}
		// Rollback Ingestion
		ingestionPage.rollBackIngestion();
	}

	/**
	 * Author :Arunkumar date: 02/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-47404 Description :To verify that if configure mapping is not
	 * matched the warning message is displayed and as per selection, admin can
	 * proceed.
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 21)
	public void verifyConfigureMappingWarningMessage() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47404");
		baseClass.stepInfo(
				"verify that if configure mapping is not matched the warning message is displayed and as per selection, admin can proceed.");
		ingestionPage.unicodeFilesIngestion(Input.datLoadFile1, Input.textFile1, Input.documentKey);
		ingestionPage.ingestionCatalogging();
		ingestionPage.verifyWarningMessageForConfigureMappingSection(Input.UniCodeFilesFolder, Input.datLoadFile1,
				Input.textFile1, Input.prodBeg, Input.documentKey);

	}

	/**
	 * Author :Brundha Test Case Id:RPMXCON-50083 Description :verify Ingestion
	 * should published successfully if Email metadata is having only Address
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 22)
	public void verifyEmailAddressInDocListPage() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-50083");
		baseClass.stepInfo("verify Ingestion should published successfully if Email metadata is having only Address");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);

		baseClass.selectproject(Input.ingestionPrjt);

		ingestionPage = new IngestionPage_Indium(driver);
		boolean status = ingestionPage.verifyIngestionpublish(Input.nativeFileName);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Edit of addonly saved ingestion with mapping field selection");
			String ingestionType="Add Only";
			ingestionPage.IngestionRegressionForDifferentDAT(Input.GD994NativeTextForProductionFolder,ingestionType,
					Input.sourceSystem, Input.datFormatFile, "DAT4_STC_NativesEmailData NEWID.lst",
					"DAT4_STC_TextEmailData NEWID.lst",null, null, null, null, null, null);
		}
		String[] addEmailColumn = { "EmailAuthorName", "EmailAuthorAddress" };
		sessionSearch = new SessionSearch(driver);
		sessionSearch.SearchMetaData(Input.metadataIngestion, Input.nativeFileName);
		sessionSearch.ViewInDocList();

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
	@Test(enabled = true, groups = { "regression" }, priority = 23)
	public void verifyEmailAllDomainDistinctData() throws InterruptedException {

		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49804");
		baseClass.stepInfo(
				"To verify that if Email data contained space after the '@' sign , it should not calculate two distinct values");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestionPrjt);
		ingestionPage = new IngestionPage_Indium(driver);
		boolean status = ingestionPage.verifyIngestionpublish(Input.nativeFileName);
		System.out.println(status);
		if (status == false) {
			String ingestionType="Add Only";
			baseClass.stepInfo("Edit of addonly saved ingestion with mapping field selection");
			ingestionPage.IngestionRegressionForDifferentDAT(Input.GD994NativeTextForProductionFolder,ingestionType,
					Input.sourceSystem, Input.datFormatFile, "DAT4_STC_NativesEmailData NEWID.lst",
					"DAT4_STC_TextEmailData NEWID.lst",null, null, null, null, null, null);
		}

		int count = sessionsearch.MetaDataSearchInBasicSearch(Input.emailAllDomain, "hotmail.com");
		sessionsearch.addNewSearch();
		int count1 = sessionsearch.newMetaDataSearchInBasicSearch(Input.emailAllDomain, "aol.com");
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
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 24)
	public void verifyDocumentInAudioPlayerReady() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48173");
		baseClass.stepInfo("To Verify for AudioPlayer ready are Nexidia indexed.");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);

		baseClass.selectproject(Input.ingestionPrjt);

		ingestionPage = new IngestionPage_Indium(driver);
		boolean status = ingestionPage.verifyIngestionpublish(Input.nativeMp3FileFormat);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Edit of addonly saved ingestion with mapping field selection");
			String ingestionType="Add Only";
			ingestionPage.IngestionRegressionForDifferentDAT(Input.AK_NativeFolder,ingestionType, Input.sourceSystem, Input.DATFile1,
					null, null, null, null,null, Input.MP3File, null, null);
		}
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.SearchMetaData(Input.audioPlayerReady, Input.pageCount);
		sessionsearch.verifyDocCountForAudioPlayerReady();
		loginPage.logout();

	}

	/**
	 * Author :Arunkumar date: 04/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-48267 Description :To Verify Ingestion Overlay Without DAT
	 * for Translation.
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 25)
	public void verifyIngestionOverlayWithoutDatForTranslation() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48267");
		baseClass.stepInfo("To Verify Ingestion Overlay Without DAT for Translation.");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Performing add only ingestion");
			ingestionPage.IngestionOnlyForDatFile(Input.AllSourcesFolder, Input.DATFile1);
			ingestionPage.IngestionCatlogtoCopying(Input.AllSourcesFolder);
			ingestionPage.ingestionIndexing(Input.AllSourcesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.navigateToAnalyticsPage();
			ingestionPage.runFullAnalysisAndPublish();
		}
		// Perform overlay ingestion for translation without DAT
		ingestionPage.OverlayIngestionWithoutDat(Input.AllSourcesFolder, "Translation", Input.TranslationFile);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.navigateToAnalyticsPage();
		ingestionPage.runFullAnalysisAndPublish();
		ingestionPage.verifyDocAvailability();
		loginPage.logout();

	}

	/**
	 * Author :Arunkumar date: 04/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-48266 Description :To Verify Ingestion Overlay Without DAT
	 * for Transcript.
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 26)
	public void verifyIngestionOverlayWithoutDatForTranscript() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48266");
		baseClass.stepInfo("To Verify Ingestion Overlay Without DAT for Transcript.");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Performing add only ingestion");
			ingestionPage.IngestionOnlyForDatFile(Input.AllSourcesFolder, Input.DATFile1);
			ingestionPage.IngestionCatlogtoCopying(Input.AllSourcesFolder);
			ingestionPage.ingestionIndexing(Input.AllSourcesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.navigateToAnalyticsPage();
			ingestionPage.runFullAnalysisAndPublish();
		}
		// Perform overlay ingestion for transcript without DAT
		ingestionPage.OverlayIngestionWithoutDat(Input.AllSourcesFolder, "Transcript", Input.TranscriptFile);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.navigateToAnalyticsPage();
		ingestionPage.runFullAnalysisAndPublish();
		ingestionPage.verifyDocAvailability();
		loginPage.logout();

	}

	/**
	 * Author :Arunkumar date: 04/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-48265 Description :To Verify Ingestion Overlay Without DAT
	 * for MP3 Variant.
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 27)
	public void verifyIngestionOverlayWithoutDatForMp3() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48265");
		baseClass.stepInfo("To Verify Ingestion Overlay Without DAT for MP3 Variant.");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Performing add only ingestion");
			ingestionPage.IngestionOnlyForDatFile(Input.AllSourcesFolder, Input.DATFile1);
			ingestionPage.IngestionCatlogtoCopying(Input.AllSourcesFolder);
			ingestionPage.ingestionIndexing(Input.AllSourcesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.navigateToAnalyticsPage();
			ingestionPage.runFullAnalysisAndPublish();
		}
		// Perform overlay ingestion for mp3 without DAT
		ingestionPage.OverlayIngestionWithoutDat(Input.AllSourcesFolder, "mp3", Input.MP3File);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.navigateToAnalyticsPage();
		ingestionPage.runFullAnalysisAndPublish();
		ingestionPage.verifyDocAvailability();
		loginPage.logout();

	}

	/**
	 * Author :Arunkumar date: 04/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-48264 Description :To Verify Ingestion Overlay Without DAT
	 * for TIFF.
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 28)
	public void verifyIngestionOverlayWithoutDatForTiff() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48264");
		baseClass.stepInfo("To Verify Ingestion Overlay Without DAT for TIFF.");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Performing add only ingestion");
			ingestionPage.IngestionOnlyForDatFile(Input.AllSourcesFolder, Input.DATFile1);
			ingestionPage.IngestionCatlogtoCopying(Input.AllSourcesFolder);
			ingestionPage.ingestionIndexing(Input.AllSourcesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.navigateToAnalyticsPage();
			ingestionPage.runFullAnalysisAndPublish();
		}
		// Perform overlay ingestion for Tiff without DAT
		ingestionPage.OverlayIngestionWithoutDat(Input.AllSourcesFolder, "Tiff", Input.TIFFFile);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.navigateToAnalyticsPage();
		ingestionPage.runFullAnalysisAndPublish();
		ingestionPage.verifyDocAvailability();
		loginPage.logout();

	}

	/**
	 * Author :Arunkumar date: 04/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-48263 Description :To Verify Ingestion Overlay Without DAT
	 * for PDF.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 29)
	public void verifyIngestionOverlayWithoutDatForPdf() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48263");
		baseClass.stepInfo("To Verify Ingestion Overlay Without DAT for PDF");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Performing add only ingestion");
			ingestionPage.IngestionOnlyForDatFile(Input.AllSourcesFolder, Input.DATFile1);
			ingestionPage.IngestionCatlogtoCopying(Input.AllSourcesFolder);
			ingestionPage.ingestionIndexing(Input.AllSourcesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.navigateToAnalyticsPage();
			ingestionPage.runFullAnalysisAndPublish();
		}
		// Perform overlay ingestion for Pdf without DAT
		ingestionPage.OverlayIngestionWithoutDat(Input.AllSourcesFolder, "Pdf", Input.PDFFile);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.navigateToAnalyticsPage();
		ingestionPage.runFullAnalysisAndPublish();
		ingestionPage.verifyDocAvailability();
		loginPage.logout();

	}

	/**
	 * Author :Arunkumar date: 04/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-48262 Description :To Verify Ingestion Overlay Without DAT
	 * for Native.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 30)
	public void verifyIngestionOverlayWithoutDatForNative() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48262");
		baseClass.stepInfo("To Verify Ingestion Overlay Without DAT for Native.");
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Performing add only ingestion");
			ingestionPage.IngestionOnlyForDatFile(Input.AllSourcesFolder, Input.DATFile1);
			ingestionPage.IngestionCatlogtoCopying(Input.AllSourcesFolder);
			ingestionPage.ingestionIndexing(Input.AllSourcesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.navigateToAnalyticsPage();
			ingestionPage.runFullAnalysisAndPublish();
		}
		// Perform overlay ingestion for Native without DAT
		ingestionPage.OverlayIngestionWithoutDat(Input.AllSourcesFolder, "Native", Input.NativeFile);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.navigateToAnalyticsPage();
		ingestionPage.runFullAnalysisAndPublish();
		ingestionPage.verifyDocAvailability();
		loginPage.logout();

	}

	/**
	 * Author :Brundha Test Case Id:RPMXCON-48171 Description :Verify that
	 * 'AudioPlayerReady' should set to '1' when MP3 file variant are ingested with
	 * .MP3 files
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 31)
	public void verifyDocumentInAudioPlayerReadyValue() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48171");
		baseClass.stepInfo(
				"Verify that 'AudioPlayerReady' should set to '1' when MP3 file variant are ingested with .MP3 files");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestionPrjt);
		ingestionPage = new IngestionPage_Indium(driver);
		boolean status = ingestionPage.verifyIngestionpublish(Input.nativeMp3FileFormat);

		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Edit of addonly saved ingestion with mapping field selection");
			String ingestionType="Add Only";
			ingestionPage.IngestionRegressionForDifferentDAT(Input.AK_NativeFolder,ingestionType, Input.sourceSystem, Input.DATFile1,
					null, null, null, null, null,Input.MP3File, null, null);
		}
		SessionSearch sessionsearch = new SessionSearch(driver);
		baseClass.stepInfo("Basic content search");
		sessionsearch.basicContentSearch(Input.nativeMp3FileFormat);
		baseClass.stepInfo("Navigating to doclist page");
		sessionsearch.ViewInDocList();
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
	 * Author :Arunkumar date: 05/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-47402 Description :To verify "Copy Ingestion" with ingestion
	 * wizard (Using link "Add Ingestion(New approach)")
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 32)
	public void verifyCopyIngestionUsingNewApproach() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47402");
		baseClass.stepInfo(
				"To verify 'Copy Ingestion' with ingestion wizard (Using link 'Add Ingestion(New approach)')");
		ingestionPage.unicodeFilesIngestion(Input.datLoadFile1, Input.textFile1, Input.documentKey);
		ingestionPage.ingestionCatalogging();
		ingestionPage.performNewAddOnlyIngestionUsingCopyOption("Mapped Data", Input.HiddenPropertiesFolder,
				Input.YYYYMMDDHHMISSDat, Input.sourceDocIdSearch);
		loginPage.logout();

	}

	/**
	 * Author :Arunkumar date: 05/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-49282 Description :To Verify Ingestion Add Only end to End
	 * Flow with Source System as Mapped Data
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 33)
	public void verifyAddonlyIngestionWithMappedData() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49282");
		baseClass.stepInfo("To Verify Ingestion Add Only end to End Flow with Source System as Mapped Data");
		// perform add only ingestion with source system as Mapped data
		boolean status = ingestionPage.verifyIngestionpublish(Input.UniCodeFilesFolder);
		System.out.println(status);
		if (status == false) {
			ingestionPage.unicodeFilesIngestionWithDifferentSourceSystem("Mapped Data", Input.datLoadFile1,
					Input.textFile1, Input.documentKey);
			ingestionPage.IngestionCatlogtoCopying(Input.UniCodeFilesFolder);
			ingestionPage.ingestionIndexing(Input.UniCodeFilesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.navigateToAnalyticsPage();
			ingestionPage.runFullAnalysisAndPublish();
		}
		ingestionPage.verifyTotalDocsIngestedWithPurehitCount();
		loginPage.logout();
	}

	/**
	 * Author :Vijaya.Rani date: 05/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-48013 Description :To Verify In Ingestion Overlays Ignore All
	 * Errors at Cataloge Stage, Should work.
	 * 
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 34)
	public void verifyIngestionOverlayIngareAllErrorsAndCatalogStage() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48013");
		baseClass.stepInfo("To Verify In Ingestion Overlays Ignore All Errors at Cataloge Stage, Should work.");
		ingestionPage.performAKNativeFolderIngestionInOverlay(Input.DATFile1);
		ingestionPage.ignoreErrorsAndCatlogging();

		// Rollback Ingestion
		ingestionPage.rollBackIngestion();
	}

	/**
	 * Author :Arunkumar date: 06/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-49466 Description :Verify error message if the source system
	 * is matching and if the doc ID is not available in the database
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 35)
	public void verifyErrorMessageIfDocIdNotAvailable() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49466");
		baseClass.stepInfo(
				"Verify error message if the source system is matching and if the doc ID is not available in the database");
		// perform add only ingestion
		boolean status = ingestionPage.verifyIngestionpublish(Input.UniCodeFilesFolder);
		System.out.println(status);
		if (status == false) {
			ingestionPage.unicodeFilesIngestionWithDifferentSourceSystem("TRUE", Input.datLoadFile1, Input.textFile1,
					Input.documentKey);
			ingestionPage.IngestionCatlogtoCopying(Input.UniCodeFilesFolder);
			ingestionPage.ingestionIndexing(Input.UniCodeFilesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.navigateToAnalyticsPage();
			ingestionPage.runFullAnalysisAndPublish();
		}
		// Verify error message for overlay ingestion if docid not available
		ingestionPage.OverlayIngestionForDATWithMappingFieldSection(Input.HiddenPropertiesFolder,
				Input.YYYYMMDDHHMISSDat, Input.sourceDocIdSearch);
		ingestionPage.clickOnPreviewAndRunButton();
		ingestionPage.verifyNonExistingDatasetErrorMessage();
		// Rollback
		ingestionPage.rollBackIngestion();
		loginPage.logout();

	}

	/**
	 * Author :Arunkumar date: 06/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-49467 Description :Verify error message displays if adding
	 * same source Doc ID which is already exists in the DB
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 36)
	public void verifyErrorMessageIfDocidExists() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49467");
		baseClass.stepInfo(
				"Verify error message displays if adding same source Doc ID which is already exists in the DB");
		// perform add only ingestion
		boolean status = ingestionPage.verifyIngestionpublish(Input.UniCodeFilesFolder);
		System.out.println(status);
		if (status == false) {
			ingestionPage.unicodeFilesIngestion(Input.datLoadFile1, Input.textFile1, Input.documentKey);
			ingestionPage.IngestionCatlogtoCopying(Input.UniCodeFilesFolder);
			ingestionPage.ingestionIndexing(Input.UniCodeFilesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.navigateToAnalyticsPage();
			ingestionPage.runFullAnalysisAndPublish();
		}
		// Verify error message for overlay ingestion if docid is already available
		ingestionPage.unicodeFilesIngestion(Input.datLoadFile1, Input.textFile1, Input.documentKey);
		ingestionPage.verifyDuplicateIngestionErrorMessage();
		// Rollback
		ingestionPage.rollBackIngestion();
		loginPage.logout();

	}

	/**
	 * Author :Brundha Test Case Id:RPMXCON-48170 Description :Verify that
	 * 'AudioPlayerReady' should set to '0' when natives are ingested with .MP3
	 * files
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 37)
	public void verifyingMp3FileDocumentCount() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48170");
		baseClass
				.stepInfo("Verify that 'AudioPlayerReady' should set to '0' when natives are ingested with .MP3 files");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);

		baseClass.selectproject(Input.ingestionProjectName);
		ingestionPage = new IngestionPage_Indium(driver);
		boolean status = ingestionPage.verifyIngestionpublish(Input.AutomationAllSources);
		System.out.println(status);

		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("addonly  ingestion with mapping field selection");
			String ingestionType="Add Only";
			ingestionPage.IngestionRegressionForDifferentDAT(Input.AllSourcesFolder,ingestionType, Input.sourceSystem, Input.DATFile1,
					Input.NativeFile, null,null, null, null, null, null, null);
		}
		SessionSearch sessionsearch = new SessionSearch(driver);
		baseClass.stepInfo("Basic content search");
		sessionsearch.basicContentSearch(Input.AutomationAllSources);
		baseClass.stepInfo("Navigating to doclist page");
		sessionsearch.ViewInDocList();
		DocListPage doc = new DocListPage(driver);
		baseClass.waitForElement(doc.getSelectDropDown());
		doc.getSelectDropDown().waitAndClick(10);
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
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 38)
	public void verfyingErrorMsgInIngestionPage() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49489");
		baseClass.stepInfo("Verify error message when user tried to do Ingestion overlay for non-existing dataset");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);

		baseClass.selectproject(Input.ingestionPrjt);
		ingestionPage = new IngestionPage_Indium(driver);
		boolean status = ingestionPage.verifyIngestionpublish(Input.nativeMp3FileFormat);
		System.out.println(status);

		System.out.println(status);
		if (status == false) {
			String ingestionType="Add Only";
			baseClass.stepInfo(" addonly ingestion with mapping field selection");
			ingestionPage.IngestionRegressionForDifferentDAT(Input.AK_NativeFolder,ingestionType,Input.sourceSystem, Input.DATFile1,
					null, null, null, null,null, Input.MP3File, null, null);
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
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 40)
	public void verifyingTiffImageTabInDocViewPage() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49507");
		baseClass.stepInfo(
				"Verify in DocView, the Default tab displays Searchable PDF generated from TIFF and Image tab should displays TIFF file");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);

		baseClass.selectproject(Input.ingestionPrjt);
		ingestionPage = new IngestionPage_Indium(driver);
		boolean status = ingestionPage.verifyIngestionpublish(Input.TiffImagesFolder);
		System.out.println(status);

		String ingestionType = "Add Only";
		String fieldSeperator = Input.fieldSeperator;
		String textQualifier = Input.textQualifier;
		String multiValue = Input.multiValue;
		String dateFormat = Input.dateFormat;

		System.out.println(status);
		if (status == false) {

			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(ingestionType, Input.sourceSystem,
					Input.sourceLocation, Input.TiffImagesFolder);
			ingestionPage.addDelimitersInIngestionWizard(fieldSeperator, textQualifier, multiValue);
			ingestionPage.selectDATSource(Input.DATFile3, Input.prodBeg);
			ingestionPage.selectTIFFSource(Input.tiffFile2, false, true);
			ingestionPage.selectPDFSource("DAT4_STC_PDFs.lst", false);
			ingestionPage.selectDateAndTimeForamt(dateFormat);
			ingestionPage.clickOnNextButton();
			ingestionPage.IngestionCatlogtoIndexing(Input.TiffImagesFolder);
			ingestionPage.approveAndPublishIngestion(Input.TiffImagesFolder);
		}

		SessionSearch search = new SessionSearch(driver);
		search.basicContentSearch(Input.TiffImages);
		search.viewInDocView();
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
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 41)
	public void verifyingMultipleTiffImageTabInDocViewPage() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49704");
		baseClass.stepInfo(
				"Verify if 'Generate Searchable PDFs' is True for TIFF image and document has multi-page TIFF's then searchable PDF's should be created successfully for any stitched TIFF's");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);

		baseClass.selectproject(Input.ingestionPrjt);
		ingestionPage = new IngestionPage_Indium(driver);
		boolean status = ingestionPage.verifyIngestionpublish(Input.TiffImagesFolder);
		System.out.println(status);

		String ingestionType = "Add Only";
		String fieldSeperator = Input.fieldSeperator;
		String textQualifier = Input.textQualifier;
		String multiValue = Input.multiValue;
		String dateFormat = Input.dateFormat;

		System.out.println(status);
		if (status == false) {

			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(ingestionType, Input.sourceSystem,
					Input.sourceLocation, Input.TiffImagesFolder);
			ingestionPage.addDelimitersInIngestionWizard(fieldSeperator, textQualifier, multiValue);
			ingestionPage.selectDATSource(Input.DATFile3, Input.prodBeg);
			ingestionPage.selectTIFFSource(Input.tiffFile2, false, true);
			ingestionPage.selectPDFSource("DAT4_STC_PDFs.lst", false);
			ingestionPage.selectDateAndTimeForamt(dateFormat);
			ingestionPage.clickOnNextButton();
			ingestionPage.IngestionCatlogtoIndexing(Input.TiffImagesFolder);
			ingestionPage.approveAndPublishIngestion(Input.TiffImagesFolder);
		}

		SessionSearch search = new SessionSearch(driver);
		search.basicContentSearch(Input.TiffImages);
		search.viewInDocView();
		DocViewPage doc = new DocViewPage(driver);
		doc.verifyingDefaultTextInDocView();
		loginPage.logout();
	}

	/**
	 * Author :Vijaya.Rani date: 06/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-48189 Description :To Verify Unpublish for Already published
	 * Documents after Ingestion.
	 * 
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 42)
	public void verifyUnpublishForAlreadyPublishedDocsIngestion() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		savedSearch = new SavedSearch(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-48189");
		baseClass.stepInfo("To Verify Unpublish for Already published Documents after Ingestion.");
		String BasicSearchName = "Newone" + Utility.dynamicNameAppender();

		// Search ingestionName And bulkRelease
		sessionSearch.basicSearchWithMetaDataQuery(Input.nativeMp3FileFormat, "IngestionName");

		// Saved the My SavedSearch
		sessionSearch.saveSearch(BasicSearchName);
		sessionSearch.bulkRelease(Input.securityGroup);

		// Go to UnpublishPage
		ingestionPage.navigateToUnPublishPage();
		ingestionPage.unpublish(BasicSearchName);

		ingestionPage.navigteToUnpublished(BasicSearchName);
		// verify Document Count is 0
		int index = baseClass.getIndex(ingestionPage.getUnpublishTableHeader(), "DOC COUNT");
		String docCount = ingestionPage.getUnpublishtableValues(BasicSearchName, index).getText();
		System.out.println(docCount);
		baseClass.stepInfo("After Unpublish DocCount : " + docCount);
	}

	/**
	 * Author :Arunkumar date: 09/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-46868 Description :Add only ingestion with only .mp3 audio
	 * files with MP3 Variant file types.
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 43)
	public void verifyAddOnlyIngestionForAudioFiles() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-46868");
		baseClass.stepInfo("Add only ingestion with only .mp3 audio files with MP3 Variant file types.");

		// perform add only ingestion
		boolean status = ingestionPage.verifyIngestionpublish(Input.audio96DocsFolder);
		if (status == false) {
			ingestionPage.performAudio96DocsIngestion(Input.audioDatFile, Input.docIdKey);
			ingestionPage.ingestionCatalogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.audio96DocsFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}

	}

	/**
	 * Author :Arunkumar date: 09/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-49258 Description :To verify that total unique ingested
	 * document count displays unique count if user perform only PDF overlay
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 44)
	public void verifyUniqueCountForOnlyPdfOverlay() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49258");
		baseClass.stepInfo(
				"To verify that total unique ingested document count displays unique count if user perform only PDF overlay");

		// perform add only ingestion
		boolean status = ingestionPage.verifyIngestionpublish(Input.AK_NativeFolder);
		if (status == false) {
			ingestionPage.performAKNativeFolderIngestion(Input.DATFile1);
			ingestionPage.ingestionCatalogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.AK_NativeFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.navigateToAnalyticsPage();
			ingestionPage.runFullAnalysisAndPublish();
		}
		// getting unique ingested count before overlay
		int uniqueCountBefore = ingestionPage.getIngestedUniqueCount();
		baseClass.stepInfo("Total unique count before performing overlay : '" + uniqueCountBefore + "'");
		// perform pdf overlay
		baseClass.stepInfo("Performing overlay ingestion with PDF");
		ingestionPage.OverlayIngestionWithoutDat(Input.AK_NativeFolder, "Pdf", Input.PDFFile);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();
		// getting unique ingested count after overlay
		int uniqueCountAfter = ingestionPage.getIngestedUniqueCount();
		baseClass.stepInfo("Total unique count before performing overlay : '" + uniqueCountAfter + "'");
		if (uniqueCountBefore == uniqueCountAfter) {
			baseClass.passedStep("Only unique ingested count displayed after performing Pdf overlay");
		} else {
			baseClass.failedStep("Unique ingested count not displayed after performing Pdf overlay");
		}
	}

	/**
	 * Author :Arunkumar date: 09/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-49259 Description :To verify that total unique ingested
	 * document count displays unique count if user perform only Native overlay
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 45)
	public void verifyUniqueCountForOnlyNativeOverlay() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.selectproject(Input.ingestDataProject);

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49259");
		baseClass.stepInfo(
				"To verify that total unique ingested document count displays unique count if user perform only Native overlay");
		// perform add only ingestion
		boolean status = ingestionPage.verifyIngestionpublish(Input.AK_NativeFolder);
		if (status == false) {
			ingestionPage.performAKNativeFolderIngestion(Input.DATFile1);
			ingestionPage.ingestionCatalogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.AK_NativeFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.navigateToAnalyticsPage();
			ingestionPage.runFullAnalysisAndPublish();
		}
		// getting unique ingested count before overlay
		int uniqueCountBefore = ingestionPage.getIngestedUniqueCount();
		baseClass.stepInfo("Total unique count before performing overlay : '" + uniqueCountBefore + "'");
		// perform Native overlay
		baseClass.stepInfo("Performing overlay ingestion with Native");
		ingestionPage.OverlayIngestionWithoutDat(Input.AK_NativeFolder, "Native", Input.NativeFile);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();
		// getting unique ingested count after overlay
		int uniqueCountAfter = ingestionPage.getIngestedUniqueCount();
		baseClass.stepInfo("Total unique count before performing overlay : '" + uniqueCountAfter + "'");
		if (uniqueCountBefore == uniqueCountAfter) {
			baseClass.passedStep("Only unique ingested count displayed after performing Native overlay");
		} else {
			baseClass.failedStep("Unique ingested count not displayed after performing Native overlay");
		}
	}

	/**
	 * Author :Arunkumar date: 09/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-49261 Description :To verify that total unique ingested
	 * document count displays unique count if user perform only MP3 overlay
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 46)
	public void verifyUniqueCountForOnlyMp3Overlay() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.selectproject(Input.ingestDataProject);

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49261");
		baseClass.stepInfo(
				"To verify that total unique ingested document count displays unique count if user perform only MP3 overlay");
		// perform add only ingestion
		boolean status = ingestionPage.verifyIngestionpublish(Input.AK_NativeFolder);
		if (status == false) {
			ingestionPage.performAKNativeFolderIngestion(Input.DATFile1);
			ingestionPage.ingestionCatalogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.AK_NativeFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.navigateToAnalyticsPage();
			ingestionPage.runFullAnalysisAndPublish();
		}
		// getting unique ingested count before overlay
		int uniqueCountBefore = ingestionPage.getIngestedUniqueCount();
		baseClass.stepInfo("Total unique count before performing overlay : '" + uniqueCountBefore + "'");
		// perform Mp3 overlay
		baseClass.stepInfo("Performing overlay ingestion with Native");
		ingestionPage.OverlayIngestionWithoutDat(Input.AK_NativeFolder, "mp3", Input.MP3File);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();
		// getting unique ingested count after overlay
		int uniqueCountAfter = ingestionPage.getIngestedUniqueCount();
		baseClass.stepInfo("Total unique count before performing overlay : '" + uniqueCountAfter + "'");
		if (uniqueCountBefore == uniqueCountAfter) {
			baseClass.passedStep("Only unique ingested count displayed after performing Mp3 overlay");
		} else {
			baseClass.failedStep("Unique ingested count not displayed after performing Mp3 overlay");
		}
	}

	/**
	 * Author :Arunkumar date: 08/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-49260 Description :To verify that total unique ingested
	 * document count displays unique count if user perform only TIFF overlay
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 47)
	public void verifyUniqueCountForOnlyTiffOverlay() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.selectproject(Input.ingestDataProject);

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49260");
		baseClass.stepInfo(
				"To verify that total unique ingested document count displays unique count if user perform only TIFF overlay");
		// perform add only ingestion
		boolean status = ingestionPage.verifyIngestionpublish(Input.TiffImagesFolder);
		if (status == false) {
			ingestionPage.tiffImagesIngestion(Input.DATFile2, Input.tiffLoadFile, "false");
			ingestionPage.ignoreErrorsAndCatlogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.TiffImagesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.navigateToAnalyticsPage();
			ingestionPage.runFullAnalysisAndPublish();
		}
		// getting unique ingested count before overlay
		int uniqueCountBefore = ingestionPage.getIngestedUniqueCount();
		baseClass.stepInfo("Total unique count before performing overlay : '" + uniqueCountBefore + "'");
		// perform Tiff overlay
		baseClass.stepInfo("Performing overlay ingestion with Native");
		ingestionPage.OverlayIngestionWithoutDat(Input.TiffImagesFolder, "Tiff", Input.tiffLoadFile);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();
		// getting unique ingested count after overlay
		int uniqueCountAfter = ingestionPage.getIngestedUniqueCount();
		baseClass.stepInfo("Total unique count before performing overlay : '" + uniqueCountAfter + "'");
		if (uniqueCountBefore == uniqueCountAfter) {
			baseClass.passedStep("Only unique ingested count displayed after performing Tiff overlay");
		} else {
			baseClass.failedStep("Unique ingested count not displayed after performing Tiff overlay");
		}
	}

	/**
	 * Author :Arunkumar date: 09/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-48147 Description :To Verify User is able to Ingest MP3 File
	 * Variant along with native
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 48)
	public void verifyAddOnlyIngestionForMp3WithNative() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48147");
		baseClass.stepInfo("To Verify User is able to Ingest MP3  File Variant along with native");

		// perform add only ingestion for mp3 along with native
		boolean status = ingestionPage.verifyIngestionpublish(Input.audio96DocsFolder);
		if (status == false) {
			ingestionPage.performAudio96DocsIngestion(Input.audioDatFile, Input.docIdKey);
			ingestionPage.ingestionCatalogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.audio96DocsFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}

	}

	/**
	 * Author :Vijaya.Rani date: 9/5/2022 Modified date: Modified by: Description
	 * :To verify that total unique ingested document count displays unique count if
	 * user perform only Text overlay.'RPMXCON-49262'
	 * 
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 49)
	public void verifyUniqueCountNotIncludeUnpublished() throws InterruptedException {

		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		savedSearch = new SavedSearch(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		String BasicSearchName = "Newone" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id: RPMXCON-49262");
		baseClass.stepInfo(
				"To verify that total unique ingested document count displays unique count if user perform only Text overlay.");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.selectproject(Input.ingestionProjectName);

		IngestionPage_Indium ingestionPage = new IngestionPage_Indium(driver);
		// perform add only ingestion with source system as Mapped data
		boolean status = ingestionPage.verifyIngestionpublish(Input.PP_PDFGen_10Docs);
		System.out.println(status);

		if (status == false) {
			String ingestionType="Add Only";
			ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,ingestionType, "TRUE", Input.DATPPPDF10Docs, null,
					Input.TextPPPDF10Docs, null, Input.ImagePPPDF10docs,"select", null, null, null);
		}
		sessionSearch.basicSearchWithMetaDataQuery(Input.sourceDocIDPPPDF10Docs, "SourceDocID");
		// Saved the My SavedSearch
		sessionSearch.saveSearch(BasicSearchName);
		// Go to UnpublishPage
		ingestionPage.navigateToUnPublishPage();
		ingestionPage.unpublish(BasicSearchName);
		// perform add only ingestion with source system as Mapped data
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		
			String ingestionType="Overlay Only";
			baseClass.stepInfo("Edit of addonly saved ingestion with mapping field selection");
			ingestionPage.IngestionRegressionForDifferentDAT(Input.GD994NativeTextForProductionFolder,ingestionType,
					Input.sourceSystem, Input.datFormatFile, "DAT4_STC_NativesEmailData NEWID.lst",
					"DAT4_STC_TextEmailData NEWID.lst", null,null, null, null, null, null);
		
		// getting unique ingested count after overlay
		int uniqueCountAfter = ingestionPage.getIngestedUniqueCount();
		baseClass.stepInfo("Total unique count After performing overlay : '" + uniqueCountAfter + "'");
		baseClass.passedStep("Only Unique count should be displayed successfully");
	}

	/**
	 * Author :Brundha Test Case Id:RPMXCON-48188 Description :To Verify
	 * AudioPlayerReady is set to 1 only if the document has an MP3 File Variant.
	 * 
	 * @throws InterruptedException
	 *
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 50)
	public void verifyingAudioPlayerReadyDocumentCount() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48188");
		baseClass.stepInfo("To Verify AudioPlayerReady is set to 1 only if the document has an MP3 File Variant.");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		baseClass.selectproject(Input.ingestionPrjt);
		ingestionPage = new IngestionPage_Indium(driver);
		boolean status = ingestionPage.verifyIngestionpublish(Input.nativeMp3FileFormat);
		String ingestionType="Add Only";	
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Edit of addonly saved ingestion with mapping field selection");
			ingestionPage.IngestionRegressionForDifferentDAT(Input.AK_NativeFolder,ingestionType, Input.sourceSystem, Input.DATFile1,
					null, null, null, null,null, Input.MP3File, null, null);
		}
		SessionSearch sessionsearch = new SessionSearch(driver);
		baseClass.stepInfo("Basic content search");
		sessionsearch.basicContentSearch(Input.searchStringStar);

		baseClass.stepInfo("Navigating to doclist page");
		sessionsearch.ViewInDocList();

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
	 * 
	 * @throws InterruptedException
	 *
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 51)
	public void verifyingMetadataInDocListPage() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-49566");
		baseClass.stepInfo("Verify Ingestion with Email metadata if 'Email Name and Address' is in incorrect format");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);

		baseClass.selectproject(Input.ingestionPrjt);

		ingestionPage = new IngestionPage_Indium(driver);
		boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
		System.out.println(status);
		if (status == false) {
			String ingestionType="Add Only";
			baseClass.stepInfo("Edit of addonly saved ingestion with mapping field selection");
			ingestionPage.IngestionRegressionForDifferentDAT(Input.GD994NativeTextForProductionFolder,ingestionType,
					Input.sourceSystem, Input.datFormatFile, "DAT4_STC_NativesEmailData NEWID.lst",
					"DAT4_STC_TextEmailData NEWID.lst",null, null, null, null, null, null);
		}
		String[] addEmailColumn = { "EmailAuthorName", "EmailAuthorAddress" };
		sessionSearch = new SessionSearch(driver);
		sessionSearch.SearchMetaData(Input.metadataIngestion, Input.nativeFileName);
		sessionSearch.ViewInDocList();

		DocListPage doc = new DocListPage(driver);
		doc.SelectColumnDisplayByRemovingExistingOnes(addEmailColumn);
		doc.emailAuthorAddressParentheses("EMAILAUTHORADDRESS");
		doc.verifyingEmailMetadataInDocListPage("EMAILAUTHORNAME");
		loginPage.logout();

	}
	
	/**
	 * Author :Arunkumar date: 10/05/2022 Modified date: NA Modified by: NA TestCase Id:RPMXCON-48292
	 * Description :To verify In Ingestion, with another New Line delimiter other than ASCII(59)
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 52)
	public void verifyAddOnlyIngestionWithAnotherNewLineDelimiter() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48292");
		baseClass.stepInfo("To verify In Ingestion, with another New Line delimiter other than ASCII(59)");

		// perform add only ingestion with new line delimiter 
		boolean status = ingestionPage.verifyIngestionpublish(Input.HiddenPropertiesFolder);
		if (status == false) {
			ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder,Input.YYYYMMDDHHMISSDat);
			ingestionPage.IngestionCatlogtoCopying(Input.HiddenPropertiesFolder);
			ingestionPage.ingestionIndexing(Input.HiddenPropertiesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}
		else {
			baseClass.passedStep("Ingestion already performed in this project successfully with New Line delimiter other than ASCII(59)");
		}

	}
	
	/**
	 * Author :Arunkumar date: 10/05/2022 Modified date: NA Modified by: NA TestCase Id:RPMXCON-47842 
	 * Description :Add only ingestion with only .mp3 audio files with MP3 Variant file types. 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 53)
	public void verifyAddOnlyIngestionForMp3VariantFileTypes() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47842");
		baseClass.stepInfo("Add only ingestion with only .mp3 audio files with MP3 Variant file types.");

		// perform add only ingestion for mp3 variant file type
		boolean status = ingestionPage.verifyIngestionpublish(Input.audio96DocsFolder);
		if (status == false) {
			ingestionPage.performAudio96DocsIngestion(Input.audioDatFile, Input.docIdKey);
			ingestionPage.ingestionCatalogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.audio96DocsFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}

	}
	
	/**
	 * Author :Arunkumar date: 10/05/2022 Modified date: NA Modified by: NA TestCase Id:RPMXCON-47587 
	 * Description :Ingestion with Overlay mode with only DAT file along with PDF or TIFF or Native file type 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 54)
	public void verifyOverlayIngestionWithDatAlongWithNative() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47587");
		baseClass.stepInfo("Ingestion with Overlay mode with only DAT file along with PDF or TIFF or Native file type");

		// perform add only ingestion for mp3 variant file type
		boolean status = ingestionPage.verifyIngestionpublish(Input.AK_NativeFolder);
		if (status == false) {
			ingestionPage.performAKNativeFolderIngestion(Input.DATFile1);
			ingestionPage.ingestionCatalogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.AK_NativeFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}
		// perform overlay ingestion with dat along with native
		baseClass.stepInfo("Performing overlay ingestion with Dat along with Native");
		ingestionPage.OverlayIngestionWithDat(Input.AK_NativeFolder,Input.DATFile1,Input.prodBeg,"Native",Input.NativeFile);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();
		
	}
	
	/**
	 * Author :Arunkumar date: 10/05/2022 Modified date: NA Modified by: NA TestCase Id:RPMXCON-47588 
	 * Description :Ingestion with Overlay mode with only load files for PDF or TIFF or Native file type
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 55)
	public void verifyOverlayIngestionWithOnlyLoadFiles() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-47588");
		baseClass.stepInfo("Ingestion with Overlay mode with only load files for PDF or TIFF or Native file type");

		// perform add only ingestion
		boolean status = ingestionPage.verifyIngestionpublish(Input.AK_NativeFolder);
		if (status == false) {
			ingestionPage.performAKNativeFolderIngestion(Input.DATFile1);
			ingestionPage.ingestionCatalogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.AK_NativeFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}
		// perform pdf overlay
		baseClass.stepInfo("Performing overlay ingestion with PDF");
		ingestionPage.OverlayIngestionWithoutDat(Input.AK_NativeFolder, "Pdf", Input.PDFFile);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		
	}
	
	/**
	 * Author :Arunkumar date: 10/05/2022 Modified date: NA Modified by: NA TestCase Id:RPMXCON-48203 
	 * Description :To Verify Ingestion overlay of Others without Unpublish
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 56)
	public void verifyOverlayIngestionWithOthers() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.selectproject(Input.ingestDataProject);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48203");
		baseClass.stepInfo("To Verify Ingestion overlay of Others without Unpublish");

		// perform add only ingestion
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		if (status == false) {
			ingestionPage.IngestionOnlyForDatFile(Input.AllSourcesFolder, Input.prodBeg);
			ingestionPage.ignoreErrorsAndCatlogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.AllSourcesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}
		// perform other overlay
		baseClass.stepInfo("Performing overlay ingestion with Others");
		ingestionPage.OverlayIngestionWithoutDat(Input.AllSourcesFolder, "Translation", Input.TranslationFile);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();
	}
	
	/**
	 * Author :Brundha Test Case Id:RPMXCON-48195 Description :To Very the Family
	 * Member Counts After Ingestion completed successfully.
	 * 
	 * @throws InterruptedException
	 *
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 57)
	public void verifyFamilyMemberCountInDocList() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48195");
		baseClass.stepInfo("To Very the Family Member Counts After Ingestion completed successfully.");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);

		baseClass.selectproject(Input.ingestionProjectName);
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
		sessionsearch.basicContentSearch("ID00002731");

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
	 * Author :Brundha Test Case Id:RPMXCON-48202 Description :To Verify Ingestion
	 * overlay of TIFF without Unpublish
	 * 
	 * @throws InterruptedException
	 *
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 58)
	public void verifyTiffImageInDocViewPage() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48202");
		baseClass.stepInfo("To Verify Ingestion overlay of TIFF without Unpublish");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		
		baseClass.selectproject(Input.ingestionPrjt);
		ingestionPage = new IngestionPage_Indium(driver);
		 boolean status=ingestionPage.verifyIngestionpublish(Input.TiffImagesFolder);
		System.out.println(status);

		String ingestionType = "Add Only";
		String fieldSeperator = Input.fieldSeperator;
		String textQualifier = Input.textQualifier;
		String multiValue = Input.multiValue;
		String dateFormat = Input.dateFormat;

		System.out.println(status);
		if (status == false) {

			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(ingestionType, Input.sourceSystem,
					Input.sourceLocation, Input.TiffImagesFolder);
			ingestionPage.addDelimitersInIngestionWizard(fieldSeperator, textQualifier, multiValue);
			ingestionPage.selectDATSource(Input.DATFile3, Input.prodBeg);
			ingestionPage.selectDateAndTimeForamt(dateFormat);
			ingestionPage.clickOnNextButton();
			ingestionPage.IngestionCatlogtoIndexing(Input.TiffImagesFolder);
			ingestionPage.approveAndPublishIngestion(Input.TiffImagesFolder);
		}

		String ingestionOverlay=Input.overlayOnly;
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(ingestionOverlay, Input.sourceSystem,
				Input.sourceLocation, Input.TiffImagesFolder);
		ingestionPage.addDelimitersInIngestionWizard(fieldSeperator, textQualifier, multiValue);
		ingestionPage.selectDATSource(Input.DATFile3, Input.prodBeg);
		ingestionPage.selectTIFFSource(Input.tiffFile2, false, true);
		ingestionPage.selectPDFSource("DAT4_STC_PDFs.lst", false);
		ingestionPage.selectDateAndTimeForamt(dateFormat);
		ingestionPage.clickOnNextButton();
		ingestionPage.clickOnPreviewAndRunButton();
		ingestionPage.ignoreErrorsAndCatlogging();
		ingestionPage.ignoreErrorsAndCopying();
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();
		SessionSearch search = new SessionSearch(driver);
		search.basicContentSearch(Input.TiffImages);
		search.viewInDocView();
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


	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
			loginPage.logoutWithoutAssert();
		}
		try {
//			loginPage.logout();
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
			// LoginPage.clearBrowserCache();
		}
	}

	@AfterClass(alwaysRun = true)
	public void close2() {
		try {
			// LoginPage.clearBrowserCache();
		} catch (Exception e) {
			// no such session
		}

	}
}
