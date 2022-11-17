package sightline.ingestion;

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
		

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());

		driver = new Driver();
		baseClass = new BaseClass(driver);
		ingestionPage = new IngestionPage_Indium(driver);
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
	@Test(description ="RPMXCON-58508",enabled = true, groups = { "regression" })
	public void verifySourceSystemIsDisabled() throws Exception {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
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
			String ingestionType="Add Only";
			ingestionPage.IngestionRegressionForDifferentDAT(Input.AllSourcesFolder,ingestionType, Input.sourceSystem, Input.DATFile1,
					Input.NativeFile, null,null, null, null, null, null, null);
			
			
		}
		baseClass.stepInfo(
				"Step 3&4 : Go to Ingestion Add New Ingestion and Ingestion Type as 'Overlay Only' and Ingestion Type as 'Overlay Only'");
		ingestionPage.verifySourceSystemDisabledWhenOverylayIsAddedAsIngestionType();

	}



	/**
	 * Author :Brundha Test Case Id:RPMXCON-50083 Description :verify Ingestion
	 * should published successfully if Email metadata is having only Address
	 * 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-50083",enabled = true, groups = { "regression" })
	public void verifyEmailAddressInDocListPage() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-50083");
		baseClass.stepInfo("verify Ingestion should published successfully if Email metadata is having only Address");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);

		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Edit of addonly saved ingestion with mapping field selection");
			String ingestionType="Add Only";
			ingestionPage.IngestionRegressionForDifferentDAT(Input.GD994NativeTextForProductionFolder,ingestionType,
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

		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49804");
		baseClass.stepInfo(
				"To verify that if Email data contained space after the '@' sign , it should not calculate two distinct values");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
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
	@Test(description ="RPMXCON-48173",enabled = true, groups = { "regression" })
	public void verifyDocumentInAudioPlayerReady() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48173");
		baseClass.stepInfo("To Verify for AudioPlayer ready are Nexidia indexed.");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);

		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.AK_NativeFolder);
		System.out.println(status);
		if (status == false) {
			
			ingestionPage = new IngestionPage_Indium(driver);
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
	@Test(description ="RPMXCON-48171",enabled = true, groups = { "regression" })
	public void verifyDocumentInAudioPlayerReadyValue() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48171");
		baseClass.stepInfo(
				"Verify that 'AudioPlayerReady' should set to '1' when MP3 file variant are ingested with .MP3 files");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
		boolean status = ingestionPage.verifyIngestionpublish(Input.AK_NativeFolder);

		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Edit of addonly saved ingestion with mapping field selection");
			String ingestionType="Add Only";
			ingestionPage.IngestionRegressionForDifferentDAT(Input.AK_NativeFolder,ingestionType, Input.sourceSystem, Input.DATFile1,
					null, null, null, null, null,Input.MP3File, null, null);
			
			
		}
		ingestionPage = new IngestionPage_Indium(driver);
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
	 * 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-48170",enabled = true, groups = { "regression" } )
	public void verifyingMp3FileDocumentCount() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48170");
		baseClass
				.stepInfo("Verify that 'AudioPlayerReady' should set to '0' when natives are ingested with .MP3 files");
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);

	
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
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
	@Test(description ="RPMXCON-49489",enabled = true, groups = { "regression" })
	public void verfyingErrorMsgInIngestionPage() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49489");
		baseClass.stepInfo("Verify error message when user tried to do Ingestion overlay for non-existing dataset");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
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
			ingestionPage.selectDateAndTimeFormat(dateFormat);
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
	@Test(description ="RPMXCON-49507",enabled = true, groups = { "regression" } )
	public void verifyingTiffImageTabInDocViewPage() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49507");
		baseClass.stepInfo(
				"Verify in DocView, the Default tab displays Searchable PDF generated from TIFF and Image tab should displays TIFF file");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
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
			ingestionPage.selectDateAndTimeFormat(dateFormat);
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
	@Test(description ="RPMXCON-49704",enabled = true, groups = { "regression" } )
	public void verifyingMultipleTiffImageTabInDocViewPage() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49704");
		baseClass.stepInfo(
				"Verify if 'Generate Searchable PDFs' is True for TIFF image and document has multi-page TIFF's then searchable PDF's should be created successfully for any stitched TIFF's");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);

		
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
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
			ingestionPage.selectDateAndTimeFormat(dateFormat);
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
		loginPage.logout();
	}

	/**
	 * Author :Brundha Test Case Id:RPMXCON-48188 Description :To Verify
	 * AudioPlayerReady is set to 1 only if the document has an MP3 File Variant.
	 * 
	 * @throws InterruptedException
	 *
	 */
	@Test(description ="RPMXCON-48188",enabled = true, groups = { "regression" } )
	public void verifyingAudioPlayerReadyDocumentCount() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48188");
		baseClass.stepInfo("To Verify AudioPlayerReady is set to 1 only if the document has an MP3 File Variant.");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
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
	@Test(description ="RPMXCON-49566",enabled = true, groups = { "regression" } )
	public void verifyingMetadataInDocListPage() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-49566");
		baseClass.stepInfo("Verify Ingestion with Email metadata if 'Email Name and Address' is in incorrect format");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);

		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
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
	@Test(description ="RPMXCON-48202",enabled = true, groups = { "regression" })
	public void verifyTiffImageInDocViewPage() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48202");
		baseClass.stepInfo("To Verify Ingestion overlay of TIFF without Unpublish");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		
		
		ingestionPage.navigateToIngestionHomePageAndVerifyUrl();
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
			ingestionPage.selectTIFFSource(Input.tiffFile2, false, true);
			ingestionPage.selectPDFSource("DAT4_STC_PDFs.lst", false);
			ingestionPage.selectDateAndTimeFormat(dateFormat);
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
	 * 
	 */
	@Test(description ="RPMXCON-48201",enabled = true, groups = { "regression" })
	public void verifyUnPublishOfNativeDocument() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48201");
		baseClass.stepInfo("To Verify Ingestion overlay of Native without Unpublish");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
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
	 * 
	 */
	@Test(description ="RPMXCON-48237",enabled = true, groups = { "regression" })
	public void verifyAudioPlayerReadyLanguage() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48237");
		baseClass.stepInfo("To verify In Ingestion User should be able to ignore Audio Indexing error and move ahead with Ingestion");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
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
