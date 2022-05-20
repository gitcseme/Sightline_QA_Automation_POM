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
	 * Author :Vijaya.Rani date: 27/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-49902 Description :Unpublish documents - Verify Search as
	 * source.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 5)
	public void verifyUnpublishDocumentsSource() throws InterruptedException {

		SessionSearch sessionSearch = new SessionSearch(driver);
		savedSearch = new SavedSearch(driver);
		String BasicSearchName = "Newone" + Utility.dynamicNameAppender();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);

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
	 * Author :Vijaya.Rani date: 29/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-49545 Description :Verify that value for all the metadata
	 * fields having DATETIME/DATE data type.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 6)
	public void verifyMetaDataFieldsDateTimeInDataType() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49545");
		baseClass.stepInfo("Verify that value for all the metadata fields having DATETIME/DATE data type.");
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);

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
	 * Author :Brundha Test Case Id:RPMXCON-50083 Description :verify Ingestion
	 * should published successfully if Email metadata is having only Address
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 7)
	public void verifyEmailAddressInDocListPage() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-50083");
		baseClass.stepInfo("verify Ingestion should published successfully if Email metadata is having only Address");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);

		ingestionPage = new IngestionPage_Indium(driver);
		boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Edit of addonly saved ingestion with mapping field selection");
			String ingestionType="Add Only";
			ingestionPage.IngestionRegressionForDifferentDAT(Input.GD994NativeTextForProductionFolder,ingestionType,
					Input.sourceSystem, Input.datFormatFile, "DAT4_STC_NativesEmailData NEWID.lst",
					"DAT4_STC_TextEmailData NEWID.lst",null, null, null, null, null, null);
		}
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
	@Test(enabled = true, groups = { "regression" }, priority = 8)
	public void verifyEmailAllDomainDistinctData() throws InterruptedException {

		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49804");
		baseClass.stepInfo(
				"To verify that if Email data contained space after the '@' sign , it should not calculate two distinct values");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		
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
	@Test(enabled = true, groups = { "regression" }, priority = 9)
	public void verifyDocumentInAudioPlayerReady() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48173");
		baseClass.stepInfo("To Verify for AudioPlayer ready are Nexidia indexed.");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);

		ingestionPage = new IngestionPage_Indium(driver);
		boolean status = ingestionPage.verifyIngestionpublish(Input.AK_NativeFolder);
		System.out.println(status);
		if (status == false) {
			
			ingestionPage = new IngestionPage_Indium(driver);
			baseClass.stepInfo(" addonly ingestion with mapping field selection");
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation("Add Only", Input.sourceSystem,Input.sourceLocation, Input.AK_NativeFolder);
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
			ingestionPage.selectDATSource(Input.DATFile1,Input.prodBeg);
			ingestionPage.selectMP3VarientSource(Input.MP3File, false);
			ingestionPage.selectDateAndTimeForamt(Input.dateFormat);
			ingestionPage.clickOnNextButton();
			ingestionPage.fillingSourceField();
			ingestionPage.clickOnPreviewAndRunButton();
			ingestionPage.ignoreErrorsAndCatlogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.AK_NativeFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}
		SessionSearch sessionsearch = new SessionSearch(driver);
		sessionsearch.SearchMetaData(Input.audioPlayerReady, Input.pageCount);
		sessionsearch.verifyDocCountForAudioPlayerReady();
		loginPage.logout();

	}

	

	/**
	 * Author :Brundha Test Case Id:RPMXCON-48171 Description :Verify that
	 * 'AudioPlayerReady' should set to '1' when MP3 file variant are ingested with
	 * .MP3 files
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 10)
	public void verifyDocumentInAudioPlayerReadyValue() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48171");
		baseClass.stepInfo(
				"Verify that 'AudioPlayerReady' should set to '1' when MP3 file variant are ingested with .MP3 files");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		
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
	 * Author :Vijaya.Rani date: 05/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-48013 Description :To Verify In Ingestion Overlays Ignore All
	 * Errors at Cataloge Stage, Should work.
	 * 
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 11)
	public void verifyIngestionOverlayIngareAllErrorsAndCatalogStage() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48013");
		baseClass.stepInfo("To Verify In Ingestion Overlays Ignore All Errors at Cataloge Stage, Should work.");
		ingestionPage.performAKNativeFolderIngestionInOverlay(Input.DATFile1);
		ingestionPage.ignoreErrorsAndCatlogging();

		// Rollback Ingestion
		ingestionPage.rollBackIngestion();
	}

	
	/**
	 * Author :Brundha Test Case Id:RPMXCON-48170 Description :Verify that
	 * 'AudioPlayerReady' should set to '0' when natives are ingested with .MP3
	 * files
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 12)
	public void verifyingMp3FileDocumentCount() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48170");
		baseClass
				.stepInfo("Verify that 'AudioPlayerReady' should set to '0' when natives are ingested with .MP3 files");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);

	
		ingestionPage = new IngestionPage_Indium(driver);
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		System.out.println(status);

		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("addonly  ingestion with mapping field selection");
			String ingestionType="Add Only";
			ingestionPage.IngestionRegressionForDifferentDAT(Input.AllSourcesFolder,ingestionType, Input.sourceSystem, Input.DATFile1,
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
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 13)
	public void verfyingErrorMsgInIngestionPage() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49489");
		baseClass.stepInfo("Verify error message when user tried to do Ingestion overlay for non-existing dataset");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		
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
			ingestionPage.fillingSourceField();
			ingestionPage.clickOnPreviewAndRunButton();
			ingestionPage.ignoreErrorsAndCatlogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.IgnoreErrorAndIndexing();
			ingestionPage.approveAndPublishIngestion(Input.TiffImagesFolder);
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
	@Test(enabled = true, groups = { "regression" }, priority = 14)
	public void verifyingTiffImageTabInDocViewPage() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49507");
		baseClass.stepInfo(
				"Verify in DocView, the Default tab displays Searchable PDF generated from TIFF and Image tab should displays TIFF file");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		
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
			ingestionPage.fillingSourceField();
			ingestionPage.clickOnPreviewAndRunButton();
			ingestionPage.ignoreErrorsAndCatlogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.IgnoreErrorAndIndexing();
			ingestionPage.approveAndPublishIngestion(Input.TiffImagesFolder);
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
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 15)
	public void verifyingMultipleTiffImageTabInDocViewPage() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49704");
		baseClass.stepInfo(
				"Verify if 'Generate Searchable PDFs' is True for TIFF image and document has multi-page TIFF's then searchable PDF's should be created successfully for any stitched TIFF's");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);

		
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

		DataSets dataSets=new DataSets(driver);
		dataSets.navigateToDataSetsPage();
		dataSets.selectDataSetWithNameInDocView(Input.TiffImagesFolder);
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
	@Test(enabled = true, groups = { "regression" }, priority = 16)
	public void verifyUnpublishForAlreadyPublishedDocsIngestion() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
	
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
	 * Author :Brundha Test Case Id:RPMXCON-48188 Description :To Verify
	 * AudioPlayerReady is set to 1 only if the document has an MP3 File Variant.
	 * 
	 * @throws InterruptedException
	 *
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 17)
	public void verifyingAudioPlayerReadyDocumentCount() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48188");
		baseClass.stepInfo("To Verify AudioPlayerReady is set to 1 only if the document has an MP3 File Variant.");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		
		ingestionPage = new IngestionPage_Indium(driver);
		boolean status = ingestionPage.verifyIngestionpublish(Input.AK_NativeFolder);
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
	@Test(enabled = true, groups = { "regression" }, priority = 18)
	public void verifyingMetadataInDocListPage() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-49566");
		baseClass.stepInfo("Verify Ingestion with Email metadata if 'Email Name and Address' is in incorrect format");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);

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
	 * 
	 * @throws InterruptedException
	 *
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 19)
	public void verifyTiffImageInDocViewPage() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48202");
		baseClass.stepInfo("To Verify Ingestion overlay of TIFF without Unpublish");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		
		
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
			ingestionPage.fillingSourceField();
			ingestionPage.clickOnPreviewAndRunButton();
			ingestionPage.ignoreErrorsAndCatlogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.IgnoreErrorAndIndexing();
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
		ingestionPage = new IngestionPage_Indium(driver);
		driver.Navigate().refresh();
		
		String ingestionName = ingestionPage.selectPublishedFromFilterDropDown(Input.TiffImagesFolder);
		System.out.println(ingestionName);
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicSearchWithMetaDataQuery(ingestionName, "IngestionName");
		sessionSearch.viewInDocView();
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
	 * Author :Vijaya.Rani date: 10/5/2022 Modified date: Modified by: Description
	 * :To verify that the total unique count should not include the docs that have
	 * been unpublished. 'RPMXCON-49263'
	 * 
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 20)
	public void verifyTotalUniqueCountAfterUnpublished() throws InterruptedException {

		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		savedSearch = new SavedSearch(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		String BasicSearchName = "Search" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id: RPMXCON-49263");
		baseClass.stepInfo(
				"To verify that the total unique count should not include the docs that have been unpublished.");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		IngestionPage_Indium ingestionPage = new IngestionPage_Indium(driver);
		ingestionPage.nativigateToIngestionViaButton();
		// getting before unique count
		int uniqueCountBefore = ingestionPage.getIngestedUniqueCount();
		baseClass.stepInfo("Total unique count Before performing overlay : '" + uniqueCountBefore + "'");

		// Search ingestionName
		sessionSearch.basicSearchWithMetaDataQuery("8B61_27Mar_MultiPageTIFF_20220321153205930", "IngestionName");
		sessionSearch.getPureHitAddButton().isElementAvailable(2);
		sessionSearch.getPureHitAddButton().waitAndClick(5);
		// Saved the My SavedSearch
		sessionSearch.saveSearch(BasicSearchName);
		// Go to UnpublishPage
		ingestionPage.navigateToUnPublishPage();
		ingestionPage.unpublish(BasicSearchName);

		int uniqueCountAfter = ingestionPage.getIngestedUniqueCount();
		baseClass.stepInfo("Total unique count Before performing overlay : '" + uniqueCountAfter + "'");

		if (uniqueCountBefore == uniqueCountAfter) {
			baseClass.passedStep("Total Unique Count is not include the document that have been unpublished ");
		} else {
			baseClass.failedStep("Total Unique Count is include the document that have been unpublished ");
		}
	}

	/**
	 * Author :Vijaya.Rani date: 10/5/2022 Modified date: Modified by: Description
	 * :To Verify Full Analytics run successfully in Ingestion for Overlays Mode and
	 * all the Metadata Updated in Overlays should get displayed after Overlay's
	 * Successful. 'RPMXCON-48084'
	 * 
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 21)
	public void verifyFullAnalyticsRunIngestionForOverlay() throws InterruptedException {

		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		savedSearch = new SavedSearch(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		String BasicSearchName = "Test" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id: RPMXCON-48084");
		baseClass.stepInfo(
				"To Verify Full Analytics run successfully in Ingestion for Overlays Mode and all the Metadata Updated in Overlays should get displayed after Overlay's Successful.");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		IngestionPage_Indium ingestionPage = new IngestionPage_Indium(driver);
		ingestionPage.nativigateToIngestionViaButton();

		boolean status = ingestionPage.verifyIngestionpublish(Input.TiffImagesFolder);
		System.out.println(status);
		String ingestionType = "Add Only";

		System.out.println(status);
		if (status == false) {

			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(ingestionType, Input.sourceSystem,
					Input.sourceLocation, Input.TiffImagesFolder);
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);
			ingestionPage.selectDATSource(Input.DATFile3, Input.prodBeg);
			ingestionPage.selectDateAndTimeForamt(Input.dateFormat);
			ingestionPage.clickOnNextButton();
			ingestionPage.IngestionCatlogtoIndexing(Input.TiffImagesFolder);
			ingestionPage.approveAndPublishIngestion(Input.TiffImagesFolder);
		}
		// Search ingestionName And bulkRelease
		sessionSearch.basicContentSearch(Input.searchStringStar);
		String beforeStringCount = sessionSearch.getPureHitsCount().getText();
		System.out.println(beforeStringCount);
		baseClass.stepInfo("First String persistent hit count is : " + beforeStringCount);

		// Saved the My SavedSearch
		sessionSearch.bulkRelease(Input.securityGroup);
		sessionSearch.saveSearch(BasicSearchName);

		// Go to UnpublishPage
		ingestionPage.navigateToUnPublishPage();
		ingestionPage.unpublish(BasicSearchName);

		// Go to Sessionsearch page
		this.driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionSearch.addNewSearch();
		sessionSearch.basicSearchWithMetaDataQuery(Input.searchString1);
		String afterStringCount = sessionSearch.getPureHitsCount().getText();
		System.out.println(afterStringCount);
		baseClass.stepInfo("First String persistent hit count is : " + afterStringCount);
		if (beforeStringCount != afterStringCount) {
			baseClass.passedStep("Metadata Updated in Overlays displayed after Overlay's Successfully");
		} else {
			baseClass.failedStep(" Metadata Updated in Overlays not displayed after Overlay's");
		}
	}

	/**
	 * Author :Vijaya.Rani date: 10/5/2022 Modified date: Modified by: Description:
	 * To Verify Ingestion Overlays of PDF without unpublish. 'RPMXCON-46875'
	 * 
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 22)
	public void verifyingestionOverlayWithoutUnpublish() throws InterruptedException {

		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		savedSearch = new SavedSearch(driver);
		DocViewPage docview = new DocViewPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-46875");
		baseClass.stepInfo("To Verify Ingestion Overlays of PDF without unpublish.");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

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
		driver.Navigate().refresh();
		String ingestionName = ingestionPage.selectPublishedFromFilterDropDown(Input.HiddenPropertiesFolder);
		System.out.println(ingestionName);
		SessionSearch sessionSearch = new SessionSearch(driver);
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
	}
	
	
	/**
	 * Author :Vijaya.Rani date: 10/5/2022 Modified date: Modified by: 
	 * Description : Verify the overlay Ingestion for Audio Documents against International English language pack
	 * 'RPMXCON-48526'
	 * 
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 23)
	public void verifyAudioDocumentOverlayInternationEnglish() throws InterruptedException {
		
		baseClass = new BaseClass(driver);
		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		sessionSearch = new SessionSearch(driver);
		IngestionPage_Indium ingetion = new IngestionPage_Indium(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		baseClass.stepInfo("Test case Id: RPMXCON-48526 ");
		baseClass.stepInfo(
				"### Verify the overlay Ingestion for Audio Documents against International English language pack ###");

		baseClass.stepInfo("Navigate to ingestion page.");
		ingetion.nativigateToIngestionViaButton();

		boolean status = ingetion.verifyIngestionpublish(Input.ingestionAutomationAllSource);
		if (status) {
			baseClass.stepInfo("Ingestion Add only is already done this project");
			baseClass.stepInfo("Select ingestion type and specify source loaction.");
			ingetion.selectIngestionTypeAndSpecifySourceLocation(Input.overlayOnly, Input.sourceSystem, Input.sourceLocation,
					Input.ingestionAutomationAllSource);

			baseClass.stepInfo("Select DAT delimiters.");
			ingetion.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);

			baseClass.stepInfo("Select DAT source.");
			ingetion.selectDATSource(Input.BEbomDat, Input.prodBeg);

			baseClass.stepInfo("Select Date and Time format.");
			ingetion.selectDateAndTimeForamt(Input.dateFormat);

			baseClass.stepInfo("Click on next button.");
			ingetion.clickOnNextButton();

			baseClass.stepInfo("Click on preview and run button.");
			ingetion.clickOnPreviewAndRunButton();

			baseClass.stepInfo("Select all options from filter by dropdown.");
			ingetion.selectAllOptionsFromFilterByDropdown();

			ingetion.getIngestionSatatusAndPerformUptoCopiedStage();

			ingetion.verifyLanguageIsSelectable("International English");

		}

		baseClass.passedStep(
				"Verified the overlay Ingestion for Audio Documents against International English language pack");

	}

	/**
	 * Author :Brundha Test Case Id:RPMXCON-48201 
	 * Description :To Verify Ingestion overlay of Native without Unpublish
	 * @throws InterruptedException
	 * 
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 24)
	public void verifyUnPublishOfNativeDocument() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48201");
		baseClass.stepInfo("To Verify Ingestion overlay of Native without Unpublish");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		
		ingestionPage = new IngestionPage_Indium(driver);
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
		ingestionPage.selectDateAndTimeForamt(Input.dateFormat);
		ingestionPage.clickOnNextButton();
		ingestionPage.clickOnPreviewAndRunButton();
		ingestionPage.ignoreErrorsAndCatlogging();
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();
		
		ingestionPage = new IngestionPage_Indium(driver);
		driver.Navigate().refresh();
		String ingestionName = ingestionPage.selectPublishedFromFilterDropDown(Input.GD994NativeTextForProductionFolder);
		System.out.println(ingestionName);
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicSearchWithMetaDataQuery(ingestionName, "IngestionName");
		sessionSearch.viewInDocView();
		
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
	 * 
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 25)
	public void verifyAudioPlayerReadyLanguage() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48237");
		baseClass.stepInfo("To verify In Ingestion User should be able to ignore Audio Indexing error and move ahead with Ingestion");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);

		
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo(" addonly ingestion with mapping field selection");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation("Add Only", Input.sourceSystem,Input.sourceLocation, Input.AK_NativeFolder);
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator,Input.textQualifier,Input.multiValue);
		ingestionPage.selectDATSource(Input.DATFile1,Input.prodBeg);
		ingestionPage.selectMP3VarientSource(Input.MP3File, false);
		ingestionPage.selectDateAndTimeForamt(Input.dateFormat);
		ingestionPage.clickOnNextButton();
		ingestionPage.fillingSourceField();
		ingestionPage.clickOnPreviewAndRunButton();
		ingestionPage.ignoreErrorsAndCatlogging();
		ingestionPage.ignoreErrorsAndCopying();
		ingestionPage.IgnoreErrorAndIndexing();
		loginPage.logout();
		
	}
	/**
	 * Author: Vijaya.Rani date: 04/05/2022 Modified date: NA Modified by: NA
	 * Description :Verify Ingestion should published successfully if Email metadata is having only Name.'RPMXCON-49569'
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 26)
	public void verifyIngestionEmailMetaDataOnlyName() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		DocListPage docList = new DocListPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49569");
		baseClass.stepInfo("Verify Ingestion should published successfully if Email metadata is having only Name.");
		String[] addEmailColumn = { "EmailAuthorNameAndAddress", "EmailBCCNamesAndAddresses", "EmailCCNamesAndAddresses", "EmailToNamesAndAddresses" };

		String ingestionType="Add Only";
		
		boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
		
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Edit of addonly saved ingestion with mapping field selection");
			ingestionPage.IngestionRegressionForDifferentDAT(Input.GD994NativeTextForProductionFolder, ingestionType, Input.sourceSystem,
					Input.DATFile1, null, null, null, null, null, Input.MP3File, null, null);

		}
		
		baseClass.stepInfo("Search the documents and Save");
		sessionSearch.basicSearchWithMetaDataQuery("8B61_GD_994_Native_Text_ForProduction_20220413074025033", "IngestionName");
		sessionSearch.ViewInDocList();
		
		docList.SelectColumnDisplayByRemovingExistingOnes(addEmailColumn);
		driver.waitForPageToBeReady();
		for(String metadata : addEmailColumn) {
			baseClass.visibleCheck(metadata);
		}
		baseClass.stepInfo("Email metadata is display correctly in doc list");
		
		//verify Emailname is Display
		String emailName = docList.getDocList_EmailName().getText();
		System.out.println(emailName);
		if(docList.getDocList_EmailName().Displayed()) {
			baseClass.passedStep("Email name is displayed successsfully");
		}
		else {
			baseClass.failedStep("Email name is not displayed");
		}

		//verify emailAddress Is Blank
		if(emailName.contains("@")) {
			baseClass.failedStep("Email Address is displayed");
		}
		else {
			baseClass.passedStep ("Email Address is blank");
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
