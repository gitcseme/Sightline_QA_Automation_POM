package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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

public class Ingestion_Regression02 {

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
		Input in = new Input();
		in.loadEnvConfig();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());

		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);

	}
	
	/**
	 * Author :Brundha Test Case Id:RPMXCON-49566 Description :Verify Ingestion with
	 * Email metadata if 'Email Name and Address' is in incorrect format
	 * 
	 * @throws InterruptedException
	 *
	 */
	@Test(description ="RPMXCON-49566",enabled = true, groups = { "regression" })
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
	 * Author :Brundha Test Case Id:RPMXCON-48195 Description :To Very the Family
	 * Member Counts After Ingestion completed successfully.
	 * 
	 * @throws InterruptedException
	 *
	 */
	@Test(description ="RPMXCON-48195",enabled = true, groups = { "regression" })
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
	@Test(description ="RPMXCON-48202",enabled = true, groups = { "regression" })
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

	/**
	 * Author :Vijaya.Rani date: 10/5/2022 Modified date: Modified by: Description
	 * :To verify that the total unique count should not include the docs that have
	 * been unpublished. 'RPMXCON-49263'
	 * 
	 */
	@Test(description ="RPMXCON-49263",alwaysRun = true, groups = { "regression" })
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
		baseClass.selectproject(Input.ingestionProjectName);

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
	@Test(description ="RPMXCON-48084",alwaysRun = true, groups = { "regression" })
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
		baseClass.selectproject(Input.ingestionProjectName);

		IngestionPage_Indium ingestionPage = new IngestionPage_Indium(driver);
		ingestionPage.nativigateToIngestionViaButton();

		boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
		System.out.println(status);
		if (status == false) {
			String ingestionType = "Add Only";
			baseClass.stepInfo("Edit of addonly saved ingestion with mapping field selection");
			ingestionPage.IngestionRegressionForDifferentDAT(Input.GD994NativeTextForProductionFolder, ingestionType,
					Input.sourceSystem, Input.datFormatFile, "DAT4_STC_NativesEmailData NEWID.lst",
					"DAT4_STC_TextEmailData NEWID.lst", null, null, null, null, null, null);
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
	@Test(description ="RPMXCON-46875",alwaysRun = true, groups = { "regression" })
	public void verifyingestionOverlayWithoutUnpublish() throws InterruptedException {

		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		savedSearch = new SavedSearch(driver);
		DocViewPage docview = new DocViewPage(driver);
	
		baseClass.stepInfo("Test case Id: RPMXCON-46875");
		baseClass.stepInfo("To Verify Ingestion Overlays of PDF without unpublish.");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.selectproject(Input.ingestionProjectName);

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
		String ingestionFullName = dataSets.isDataSetisAvailable(Input.JanMultiPTIFF);
		if (ingestionFullName != null) {
			dataSets.selectDataSetWithNameInDocView(Input.JanMultiPTIFF);
			driver.waitForPageToBeReady();
			docview.getFileType().isElementAvailable(3);
			driver.waitForPageToBeReady();
			String filetype = docview.getDocView_TextFileType().getText().trim();
			if (filetype.isEmpty()) {
			baseClass.passedStep("PDF is displays in PDF viewer");
		} else {
			baseClass.failedStep("There is no such message");
		}
	}
}
	
	
	
	
	
	
	/**
	 * Author :Vijaya.Rani date: 10/5/2022 Modified date: Modified by: 
	 * Description : Verify the overlay Ingestion for Audio Documents against International English language pack
	 * 'RPMXCON-48526'
	 * 
	 */
	@Test(description ="RPMXCON-48526",alwaysRun = true, groups = { "regression" })
	public void verifyAudioDocumentOverlayInternationEnglish() throws InterruptedException {
		
		baseClass = new BaseClass(driver);
		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		sessionSearch = new SessionSearch(driver);
		IngestionPage_Indium ingetion = new IngestionPage_Indium(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.selectproject(Input.ingestionProjectName);
		
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

			baseClass.stepInfo("Select MP3 varient source.");
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

			ingetion.verifyLanguageIsSelectable("International English");

		}

		baseClass.passedStep(
				"Verified the overlay Ingestion for Audio Documents against International English language pack");

	}

	/**
	 * Author :Vijaya.Rani date: 11/5/2022 Modified date: Modified by: Description
	 * :To verify that after Text overlay, if there are no other file variants ,
	 * then DocView uses that text as the default viewer file for that document.
	 * 'RPMXCON-48606'
	 * 
	 */
	@Test(description ="RPMXCON-48606",alwaysRun = true, groups = { "regression" })
	public void verifyOverlayTheDocViewTextWillReflectOverlaidText() throws InterruptedException {

		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		savedSearch = new SavedSearch(driver);
		DocListPage docList = new DocListPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		String BasicSearchName = "Search" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id: RPMXCON-48606");
		baseClass.stepInfo(
				"To verify that after Text overlay, if there are no other file variants , then DocView uses that text as the default viewer file for that document.");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.selectproject(Input.ingestionProjectName);

		String ingestionType = "Add Only";
		IngestionPage_Indium ingestionPage = new IngestionPage_Indium(driver);
		boolean status = ingestionPage.verifyIngestionpublish(Input.PP_PDFGen_10Docs);
		System.out.println(status);

		if (status == false) {

			ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs, ingestionType, "TRUE",
					Input.DATPPPDF10Docs, null, Input.TextPPPDF10Docs, null, Input.ImagePPPDF10docs, "select", null,
					null, null);

		}
		dataSets.selectDataSetWithName(Input.PP_PDFGen_10Docs);
		String docId = docList.getDocumetId();
		sessionSearch.basicSearchWithMetaDataQuery(docId, "DocID");
		sessionSearch.saveSearch(BasicSearchName);

		// Go to UnpublishPage
		ingestionPage.navigateToUnPublishPage();
		ingestionPage.unpublish(BasicSearchName);

		baseClass = new BaseClass(driver);

		baseClass.stepInfo("Navigate to ingestion page.");
		driver.scrollPageToTop();
		ingestionPage.nativigateToIngestionViaButton();

		boolean status1 = ingestionPage.verifyIngestionpublish(Input.PDFGen_10Docs);
		if (status1) {

			baseClass.stepInfo("Select ingestion type and specify source loaction.");
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.overlayOnly, Input.sourceSystem, Input.sourceLocation,
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
		String docId1 = docList.getDocumetId();
		sessionSearch.basicSearchWithMetaDataQuery(docId1, "DocID");
		sessionSearch.viewInDocView();
		driver.waitForPageToBeReady();
		docView.waitforFileType();
		docView.getDocView_TextTab().waitAndClick(10);
		driver.waitForPageToBeReady();
		if (docView.getDocViewDefaultViewText().isElementAvailable(10)) {
			baseClass.passedStep(
					"There are no other file variants ,then Doc View is  displays the text as the default viewer");
		} else {
			baseClass.failedStep("verification failed");
		}

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

		baseClass.selectproject(Input.ingestionProjectName);
		
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
		
		SessionSearch search = new SessionSearch(driver);
		search.basicContentSearch("8B61_GD_994_Native_Text_ForProduction_20220511061509400");
		search.viewInDocView();
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

		baseClass.selectproject(Input.ingestionProjectName);
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
	@Test(description ="RPMXCON-49569",enabled = true, groups = { "regression" })
	public void verifyIngestionEmailMetaDataOnlyName() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		DocListPage docList = new DocListPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49569");
		baseClass.stepInfo("Verify Ingestion should published successfully if Email metadata is having only Name.");
		String[] addEmailColumn = { "EmailAuthorNameAndAddress", "EmailBCCNamesAndAddresses", "EmailCCNamesAndAddresses", "EmailToNamesAndAddresses" };

		String ingestionType="Add Only";
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		
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