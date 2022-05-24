package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
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
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class IngestionCreationClass02 {
	
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	IngestionPage_Indium ingestionPage;
	Input ip;
	SoftAssert softAssertion;
	DataSets dataSets;
	SavedSearch savedSearch;
	DocViewPage docView;
	SessionSearch sessionsearch;
	TagsAndFoldersPage tagandfolder;
	DocListPage doclist;
	DocViewPage docview;

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

		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
	}
	
	
	

	/**
	 * Author: Vijaya.Rani date: 02/05/2022 Modified date: NA Modified by: NA
	 * Description :Verify when user ingest only metadata, message like 'No files
	 * associated with this document' should be displayed on
	 * text/Images/Translations view.'RPMXCON-51236'
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 1)
	public void verifyIngestMetaDataMessageDisplayTEXTFile() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		String ingestionType="Add Only";
		baseClass.stepInfo("Test case Id: RPMXCON-51236");
		baseClass.stepInfo(
				"Verify when user ingest only metadata, message like 'No files associated with this document' should be displayed on text/Images/Translations view");

		boolean status = ingestionPage.verifyIngestionpublish(Input.PP_PDFGen_10Docs);
		System.out.println(status);

		if (status == false) {
			ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,ingestionType, "TRUE", Input.DATPPPDF10Docs, null,
					Input.TextPPPDF10Docs, null, Input.ImagePPPDF10docs, "select",null, null, null);
		}

		SessionSearch sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("step 1:Search Metadata SourceDocID go to docView ");
		sessionSearch.basicSearchWithMetaDataQuery(Input.sourceDocIDPPPDF10Docs, "SourceDocID");
		sessionSearch.viewInDocView();
		DocViewPage docViewPage = new DocViewPage(driver);

		driver.waitForPageToBeReady();
		baseClass.stepInfo("step 2:Select Text Tab in Docview");
		baseClass.waitForElement(docViewPage.getDocView_TextTab());
		docViewPage.getDocView_TextTab().waitAndClick(5);
		String text = docViewPage.getDocViewDefaultViewText().getText();
		if (text.contains("There is no file")) {
			baseClass.passedStep(
					"In text tab it displayed as 'There is no file associated with this document on text view'");
		} else {
			baseClass.failedStep("There is no such message");
		}

		loginPage.logout();

	}

	/**
	 * Author: Vijaya.Rani date: 02/05/2022 Modified date: NA Modified by: NA
	 * Description :Verify when user ingest only metadata, 'No files associated with
	 * this document' message should be displayed on default view.'RPMXCON-51235'
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 2)
	public void verifyIngestMetaDataMessageDisplayDefaultAndTEXTFile() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51235");
		baseClass.stepInfo(
				"Verify when user ingest only metadata, 'No files associated with this document' message should be displayed on default view");

		String ingestionType="Add Only";
		boolean status = ingestionPage.verifyIngestionpublish(Input.PP_PDFGen_10Docs);
		System.out.println(status);

		if (status == false) {
			ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,ingestionType, "TRUE", Input.DATPPPDF10Docs, null,
					Input.TextPPPDF10Docs, null, Input.ImagePPPDF10docs,"select", null, null, null);
		}


		
		SessionSearch sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("step 1:Search Metadata SourceDocID go to docView ");
		sessionSearch.basicSearchWithMetaDataQuery(Input.sourceDocIDPPPDF10Docs, "SourceDocID");
		sessionSearch.viewInDocView();
		
		baseClass.stepInfo("step 2:Select Default PDF Tab in Docview");
		baseClass.waitForElement(docViewPage.getDocView_DefaultViewTab());
		docViewPage.getDocView_DefaultViewTab().waitAndClick(5);
		if (docViewPage.getDocview_DefaultTextArea().Displayed()) {
			baseClass.passedStep(
					"In default tab it displayed as 'This record does not contain a PDF rendering,A TIFF rendering,TEXT or a supported native file type.In order to view This document,you must download the native and view it locally.'");
			baseClass.passedStep(
					"In default tab it displayed as 'Alternatively,contact your review manager who can engage with the data processing team to see if a PDF rendering of the document can be created so it is viewable here.'");
		} else {
			baseClass.failedMessage("There is no such message");
		}

		// TEXT tab Verification
		driver.waitForPageToBeReady();
		baseClass.stepInfo("step 3:Select Text Tab in Docview");
		baseClass.waitForElement(docViewPage.getDocView_TextTab());
		docViewPage.getDocView_TextTab().waitAndClick(5);
		String text = docViewPage.getDocViewDefaultViewText().getText();
		if (text.contains("There is no file")) {
			baseClass.passedStep(
					"In text tab it displayed as 'There is no file associated with this document on text view'");
		} else {
			baseClass.failedStep("There is no such message");
		}
		loginPage.logout();

	}

	/**
	 * Author: Vijaya.Rani date: 02/05/2022 Modified date: NA Modified by: NA
	 * Description :Verify when user ingest only metadata, error PDF should be
	 * displayed on Images view.'RPMXCON-51237'
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 3)
	public void verifyIngestMetaDataMessageDisplayIMAGEAndTEXTFile() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51237");
		baseClass.stepInfo("Verify when user ingest only metadata, error PDF should be displayed on Images view");

		String ingestionType="Add Only";
		boolean status = ingestionPage.verifyIngestionpublish(Input.PP_PDFGen_10Docs);
		System.out.println(status);

		if (status == false) {
			ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,ingestionType, "TRUE", Input.DATPPPDF10Docs, null,
					Input.TextPPPDF10Docs, null, Input.ImagePPPDF10docs,"select", null, null, null);
		}

		SessionSearch sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("step 1:Search Metadata SourceDocID go to docView ");
		sessionSearch.basicSearchWithMetaDataQuery(Input.sourceDocIDPPPDF10Docs, "SourceDocID");
		sessionSearch.viewInDocView();
		DocViewPage docViewPage = new DocViewPage(driver);
		// DEFAULTView Tab Verification
		driver.waitForPageToBeReady();
		baseClass.stepInfo("step 2:Select Default PDF Tab IN Docview");
		baseClass.waitForElement(docViewPage.getDocView_DefaultViewTab());
		docViewPage.getDocView_DefaultViewTab().waitAndClick(5);
		if (docViewPage.getDocview_DefaultTextArea().Displayed()) {
			baseClass.passedStep(
					"In default tab it displayed as 'This record does not contain a PDF rendering,A TIFF rendering,TEXT or a supported native file type.In order to view This document,you must download the native and view it locally.'");
			baseClass.passedStep(
					"In default tab it displayed as 'Alternatively,contact your review manager who can engage with the data processing team to see if a PDF rendering of the document can be created so it is viewable here.'");
		} else {
			baseClass.failedMessage("There is no such message");
		}

		// TEXT tab Verification
		driver.waitForPageToBeReady();
		baseClass.stepInfo("step 3:Select Text Tab in Docview");
		baseClass.waitForElement(docViewPage.getDocView_TextTab());
		docViewPage.getDocView_TextTab().waitAndClick(5);
		String text2 = docViewPage.getDocViewDefaultViewText().getText();
		if (text2.contains("There is no file")) {
			baseClass.passedStep(
					"In text tab it displayed as 'There is no file associated with this document on text view'");
		} else {
			baseClass.failedStep("There is no such message");
		}
		loginPage.logout();
	}

	/**
	 * Author: Vijaya.Rani date: 04/05/2022 Modified date: NA Modified by: NA
	 * Description :Verify when user ingest only dat file, only metadata should get
	 * ingested.'RPMXCON-51238'
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 4)
	public void verifyIngestMetaDataDATFileIsIngested() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51238");
		baseClass.stepInfo("Verify when user ingest only dat file, only metadata should get ingested.");

		String ingestionType="Add Only";
		boolean status = ingestionPage.verifyIngestionpublish(Input.PP_PDFGen_10Docs);
		System.out.println(status);

		if (status == false) {
			ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,ingestionType, "TRUE", Input.DATPPPDF10Docs, null,
					Input.TextPPPDF10Docs, null, Input.ImagePPPDF10docs,"select", null, null, null);
		}

		SessionSearch sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("step 1:Search Metadata SourceDocID go to docView ");
		sessionSearch.basicSearchWithMetaDataQuery(Input.sourceDocIDPPPDF10Docs, "SourceDocID");
		sessionSearch.viewInDocView();
		DocViewPage docViewPage = new DocViewPage(driver);
		// DEFAULTView Tab Verification
		driver.waitForPageToBeReady();
		baseClass.stepInfo("step 2:Select Default PDF Tab in Docview");
		baseClass.waitForElement(docViewPage.getDocView_DefaultViewTab());
		docViewPage.getDocView_DefaultViewTab().waitAndClick(5);
		if (docViewPage.getDocview_DefaultTextArea().Displayed()) {
			baseClass.passedStep(
					"In default tab it displayed as 'This record does not contain a PDF rendering,A TIFF rendering,TEXT or a supported native file type.In order to view This document,you must download the native and view it locally.'");
			baseClass.passedStep(
					"In default tab it displayed as 'Alternatively,contact your review manager who can engage with the data processing team to see if a PDF rendering of the document can be created so it is viewable here.'");
		} else {
			baseClass.failedMessage("There is no such message");
		}
		loginPage.logout();
	}

	
	
	
	
	
	/**
	 * Author :Aathith date: 10/5/2022 Modified date: Modified by: Description
	 * :To verify that after Text overlay, if there are other file variants , then DocView follow the set precedence and 
	 * Text will reflect the overlaid text. 'RPMXCON-48607'
	 * 
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 5)
	public void verifyOverlayTheDocViewTextWillReflectOverlaidText() throws InterruptedException {

		dataSets = new DataSets(driver);
		DocListPage docList= new DocListPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docView=new DocViewPage(driver);
		String BasicSearchName = "Search" + Utility.dynamicNameAppender();
		
		
		baseClass.stepInfo("Test case Id: RPMXCON-48607");
		baseClass.stepInfo(
				"To verify that after Text overlay, if there are other file variants , then DocView follow the set precedence and Text will reflect the overlaid text.");

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
	

	/**
	 * Author :Vijaya.Rani date: 9/5/2022 Modified date: Modified by: Description
	 * :To verify that total unique ingested document count displays unique count if
	 * user perform only Text overlay.'RPMXCON-49262'
	 * 
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 6)
	public void verifyUniqueCountNotIncludeUnpublished() throws InterruptedException {

		dataSets = new DataSets(driver);
		savedSearch = new SavedSearch(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		String BasicSearchName = "Newone" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id: RPMXCON-49262");
		baseClass.stepInfo(
				"To verify that total unique ingested document count displays unique count if user perform only Text overlay.");

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
	 * Author :Vijaya.Rani date: 11/5/2022 Modified date: Modified by: Description
	 * :To verify that after Text overlay, if there are no other file variants ,
	 * then DocView uses that text as the default viewer file for that document.
	 * 'RPMXCON-48606'
	 * 
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 7)
	public void verifyOverlayDocViewTextWillReflectOverlaidText() throws InterruptedException {

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
	 * @author Aathith
	 * @throws InterruptedException //@TestCase id: 49547 : Verify Count of Generate
	 *                              Searchable PDFs if 'Required PDF Generation' is
	 *                              TRUE and 'searchable PDF for TIFFs' is TRUE
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 8)
	public void verifySearchablePdfCount() throws InterruptedException {
		baseClass = new BaseClass(driver);

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
	 * that if PA ingested both Text's and TIFF's file,and the "Generate Searchable
	 * PDFs" option is set to False, then it should display TIFF in default viewer
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 9)
	public void VerifyTiffImageInDefautViewer() throws InterruptedException {

		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		
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
			String name = docView.getDefaultViewerFileType().GetAttribute("xlink:href");
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
	@Test(alwaysRun = true, groups = { "regression" }, priority = 10)
	public void verifyPdfandTiffInDocView() throws InterruptedException {

		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-49498");
		baseClass.stepInfo(
				"Verify that if PA ingested both native's and TIFF's file,and the \"Generate Searchable PDFs\" option is set to true, then PDF should be generated from the TIFF's only");
		
		IngestionPage_Indium ingetion = new IngestionPage_Indium(driver);
		boolean status = ingetion.verifyIngestionpublish(Input.PDFGen_10Docs);
		if(!status) {
			ingetion.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,Input.ingestionType, "TRUE", Input.DATPPPDF10Docs, null,
					Input.TextPPPDF10Docs, null, Input.ImagePPPDF10docs,"select", null, null, null);
		}
			dataSets.selectDataSetWithNameInDocView(Input.PDFGen_10Docs);
			driver.waitForPageToBeReady();
			if (docView.getFileType().isElementAvailable(10)) {
				driver.waitForPageToBeReady();
				docView.waitforFileType();
				String filetype = docView.getFileType().getText();
				System.out.println(filetype);
				if (filetype.contains("PDF")) {
					baseClass.passedStep("PDF file only displayed in default viewer");
				} else {
					baseClass.failedStep("verification failed");
				}
			} else {
				baseClass.failedStep("file type is  not displayed");
			}
			docView.getImageTab().waitAndClick(10);
			driver.waitForPageToBeReady();
			if (docView.getDocViewImage().isElementAvailable(10)) {
				baseClass.passedStep("Tiff file displayed in Tiff Tab");
			} else {
				baseClass.failedStep("verification failed");
			}
		
		baseClass.passedStep("Verified that if PA ingested both native's and TIFF's file,"
				+ "and the \"Generate Searchable PDFs\" option is set to true, then PDF should be generated from the TIFF's only");
		loginPage.logout();

	}
	
	/**
	 * Author :Vijaya.Rani date:8/5/2022 Modified date: Modified by: Description :
	 * Verify that if PA ingested both TIFF's and Text's file,and the "Generate
	 * Searchable PDFs" option is set to true, then PDF should be generated from the
	 * TIFF's only. RPMXCON-49510
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 11)
	public void VerifySelectSearchablePDFTiffImageInDefautViewer() throws InterruptedException {

		
		dataSets = new DataSets(driver);
		sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);

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
			String name = docView.getDefaultViewerFileType().GetAttribute("xlink:href");
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
	 * @author Aathith
	 * @throws InterruptedException 
	 * //@TestCase id: 48277 : To Verify unpublish for Overlay Ingestion.
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 12)
	public void verifyUnpublishOverLayIngestion() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-48277 ");
		baseClass.stepInfo(
				"###  To Verify unpublish for Overlay Ingestion. ###");
		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
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
	 * Author :Aathith date: NA Modified date: Modified by: Description : Verify
	 * Email metadata in DocList and in DocView
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 13)
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
		String[] addEmailColumn = { "EmailAuthorName", "EmailAuthorAddress", "EmailToNames", "EmailToAddresses",
				"EmailCCNames", "EmailCCAddresses", "EmailBCCNames", "EmailBCCAddresses" };
		tagandfolder.CreateFolder(foldername, Input.securityGroup);

		IngestionPage_Indium ingestion = new IngestionPage_Indium(driver);
		boolean status = ingestion.verifyIngestionpublish(Input.IngestionEmailDataFolder);
		if(!status) {
			ingestion.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem, Input.sourceLocation, Input.IngestionEmailDataFolder);
			ingestion.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);
			ingestion.selectDATSource("0188_loadfile.dat", "DocumentID");
			ingestion.selectDateAndTimeForamt(Input.dateFormat);
			ingestion.clickOnNextButton();
			ingestion.ingestionMapping("DocumentID", "Document Author", "CustUserID");
			ingestion.clickOnPreviewAndRunButton();
			ingestion.selectAllOptionsFromFilterByDropdown();
			ingestion.removeCatalogError();
			ingestion.getIngestionSatatusAndPerform();
		}
		
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

			docview.verifyTheadMapValue(10, "participant");

			loginPage.logout();
			baseClass.stepInfo("perform task for review manager");
			loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
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

			docview.verifyTheadMapValue(10, "participant");

		}

		baseClass.passedStep("Verified Email metadata in DocList and in DocView");
		loginPage.logout();

	}
	/**
	 * Author :Aathith date: NA Modified date: Modified by: Description : To verify
	 * that if Email data contained space before the '@' sign , it should not
	 * calculate two distinct values
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 14)
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
	 * Author :Aathith date: NA Modified date: Modified by: Description : Verify
	 * that if "Generate Searchable PDF " check box is not selected in the TIFF
	 * section, Ingestion should generate successfully with TIFF images only
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 15)
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

		IngestionPage_Indium ingestion = new IngestionPage_Indium(driver);
		boolean status = ingestion.verifyIngestionpublish("Tiff_Images");
		if(!status) {
			ingestion.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem, Input.sourceLocation, "Tiff_Images");
			ingestion.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);
			ingestion.selectDATSource("DAT4_STC_Image_PDFs.dat", Input.prodBeg);
			ingestion.getTIFFLST().selectFromDropdown().selectByVisibleText("DAT4_STC_Images - 38577.OPT");
			ingestion.selectDateAndTimeForamt(Input.dateFormat);
			ingestion.clickOnNextButton();
			ingestion.ingestionMapping("ProdBegAttach", Input.dataSource, Input.custodian);
			ingestion.clickOnPreviewAndRunButton();
			ingestion.selectAllOptionsFromFilterByDropdown();
			ingestion.removeCatalogError();
			ingestion.getIngestionSatatusAndPerform();
		}
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

		IngestionPage_Indium ingestion = new IngestionPage_Indium(driver);
		boolean status = ingestion.verifyIngestionpublish(Input.JanMultiPTIFF);
		if(!status) {
			ingestion.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem, Input.sourceLocation, Input.JanMultiPTIFF);
			ingestion.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);
			ingestion.selectDATSource("OLDE9EE8713-E5DD-44B8-BDC5-1466BEE66AB5_DAT.dat", "DId");
			ingestion.selectDateAndTimeForamt(Input.dateFormat);
			ingestion.clickOnNextButton();
			ingestion.ingestionMapping("DId", "DSource", "CName");
			ingestion.clickOnPreviewAndRunButton();
			ingestion.selectAllOptionsFromFilterByDropdown();
			ingestion.getIngestionSatatusAndPerform();
		}
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
	 * Author :Vijaya.Rani date: 9/5/2022 Modified date:NA Modified by:NA 
	 * Description :Verify that if PA ingested both PDF and TIFF's file, the "Generate Searchable
	 * PDFs" option is set to true, and if the Generation of the PDF from the TIFF's
	 * fails, then pre-existing PDF should be retained as the PDF file variant
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 17)
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
		IngestionPage_Indium ingestion = new IngestionPage_Indium(driver);
		boolean status = ingestion.verifyIngestionpublish(Input.JanMultiPTIFF);
		if(!status) {
			ingestion.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem, Input.sourceLocation, Input.JanMultiPTIFF);
			ingestion.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);
			ingestion.selectDATSource("OLDE9EE8713-E5DD-44B8-BDC5-1466BEE66AB5_DAT.dat", "DId");
			ingestion.selectDateAndTimeForamt(Input.dateFormat);
			ingestion.clickOnNextButton();
			ingestion.ingestionMapping("DId", "DSource", "CName");
			ingestion.clickOnPreviewAndRunButton();
			ingestion.selectAllOptionsFromFilterByDropdown();
			ingestion.getIngestionSatatusAndPerform();
		}
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
	@Test(alwaysRun = true, groups = { "regression" }, priority = 18)
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
		
		IngestionPage_Indium ingestion = new IngestionPage_Indium(driver);
		boolean status = ingestion.verifyIngestionpublish(Input.SinglePageTIFFFolder);
		if(!status) {
			ingestion.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem, Input.sourceLocation, Input.SinglePageTIFFFolder);
			ingestion.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);
			ingestion.selectDATSource("C2322695-9767-44C3-A290-B21B12B82A32_DAT.dat", "BNum");
			ingestion.getTIFFSearchablePDFCheckBox().waitAndClick(10);
			ingestion.selectDateAndTimeForamt(Input.dateFormat);
			ingestion.clickOnNextButton();
			ingestion.ingestionMapping("BNum", "DSource", "CName");
			ingestion.clickOnPreviewAndRunButton();
			ingestion.selectAllOptionsFromFilterByDropdown();
			ingestion.getIngestionSatatusAndPerform();
		}
		
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
	@Test(alwaysRun = true, groups = { "regression" }, priority = 19)
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
		
		IngestionPage_Indium ingestion = new IngestionPage_Indium(driver);
		boolean status = ingestion.verifyIngestionpublish(Input.GNon_searchable_PDF_Load_file);
		if(!status) {
			ingestion.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem, Input.sourceLocation, "GNon searchable PDF Load file");
			ingestion.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);
			ingestion.selectDATSource("Cracked Files_loadfile.dat", Input.sourceDocIdSearch);
			ingestion.selectDateAndTimeForamt(Input.dateFormat);
			ingestion.clickOnNextButton();
			ingestion.ingestionMapping("SourceParentDocID", "DataSource", "CustodianName");
			ingestion.clickOnPreviewAndRunButton();
			ingestion.selectAllOptionsFromFilterByDropdown();
			ingestion.getIngestionSatatusAndPerform();
		}
		String ingestionFullName = dataSets.isDataSetisAvailable(Input.GNon_searchable_PDF_Load_file);
		if(ingestionFullName!=null) {
			dataSets.selectDataSetWithNameInDocView(Input.GNon_searchable_PDF_Load_file);
			driver.waitForPageToBeReady();
			String value = docview.getMetadataFieldValueText("RequirePDFGeneration").getText().trim();
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
	 * Author :Vijaya.Rani date: 9/5/2022 Modified date: Modified by: Description :
	 * Verify that if "Generate Searchable PDFs" is TRUE, then Ingestion should
	 * generate successfully for Multi-page TIFF images.'RPMXCON-49502'
	 * 
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 20)
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

		IngestionPage_Indium ingestion = new IngestionPage_Indium(driver);
		boolean status = ingestion.verifyIngestionpublish(Input.JanMultiPTIFF);
		if(!status) {
			ingestion.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem, Input.sourceLocation, Input.JanMultiPTIFF);
			ingestion.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);
			ingestion.selectDATSource("OLDE9EE8713-E5DD-44B8-BDC5-1466BEE66AB5_DAT.dat", "DId");
			ingestion.selectDateAndTimeForamt(Input.dateFormat);
			ingestion.clickOnNextButton();
			ingestion.ingestionMapping("DId", "DSource", "CName");
			ingestion.clickOnPreviewAndRunButton();
			ingestion.selectAllOptionsFromFilterByDropdown();
			ingestion.getIngestionSatatusAndPerform();
		}
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
	@Test(enabled = true, groups = { "regression" }, priority = 21)
	public void verifyingNamesAndAddressesMetadataInDocListPage() throws InterruptedException {
		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
	baseClass.stepInfo("Test case Id: RPMXCON-49567");
	baseClass.stepInfo("Verify Ingestion with Email metadata if 'NamesAndAddresses' with different format");

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
	DataSets dataset = new DataSets(driver);
	dataset.selectDataSetWithName(Input.GD994NativeTextForProductionFolder);

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
	@Test(enabled = true, groups = { "regression" }, priority = 22)
	public void verifySearchablePdfTiffDocView() throws InterruptedException {
		baseClass = new BaseClass(driver);

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
		baseClass.stepInfo("Select project");
		driver.waitForPageToBeReady();

		baseClass.stepInfo("Test case Id: RPMXCON-49550 ");
		baseClass.stepInfo(
				"###  Verify that in Ingestion Overlay if 'Generate Searchable PDFs' is selected in TIFF section, then PDF should be generated from the TIFF's ###");
		IngestionPage_Indium ingetion = new IngestionPage_Indium(driver);

		baseClass.stepInfo("Navigate to ingestion page.");
		ingetion.nativigateToIngestionViaButton();
		
		IngestionPage_Indium ingestion = new IngestionPage_Indium(driver);
		boolean status = ingestion.verifyIngestionpublish("Tiff_Images");
		if(!status) {
			ingestion.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem, Input.sourceLocation, "Tiff_Images");
			ingestion.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);
			ingestion.selectDATSource("DAT4_STC_Image_PDFs.dat", Input.prodBeg);
			ingestion.getTIFFLST().selectFromDropdown().selectByVisibleText("DAT4_STC_Images - 38577.OPT");
			ingestion.selectDateAndTimeForamt(Input.dateFormat);
			ingestion.clickOnNextButton();
			ingestion.ingestionMapping("ProdBegAttach", Input.dataSource, Input.custodian);
			ingestion.clickOnPreviewAndRunButton();
			ingestion.selectAllOptionsFromFilterByDropdown();
			ingestion.removeCatalogError();
			ingestion.getIngestionSatatusAndPerform();
		}
		
		baseClass = new BaseClass(driver);
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
	 * Author :Aathith date: NA Modified date: Modified by: 
	 * Description : To Verify In Ingestions, if the user tries to unpublish non-Nexidia indexed audio files, the unpublish should be successful.
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 23)
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
		
		boolean status = ingestion.verifyIngestionpublish(Input.AK_NativeFolder);
		System.out.println(status);
		if (status == false) {
		String ingestionType="Add Only";
		baseClass.stepInfo(" addonly ingestion with mapping field selection");
		ingestion.IngestionRegressionForDifferentDAT(Input.AK_NativeFolder,ingestionType,Input.sourceSystem, Input.DATFile1,
		null, null, null, null,null, Input.MP3File, null, null);
		}
		
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
	 * //@TestCase id: 46894 : Verify the overlay Ingestion for Audio Documents against International English language pack
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 24)
	public void verifyAudioDocumentInternationEnglish() throws InterruptedException {
		
		baseClass = new BaseClass(driver);

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
	 * Author :Vijaya.Rani date: 27/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-49902 Description :Unpublish documents - Verify Search as
	 * source.
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 25)
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
	@Test(enabled = true, groups = { "regression" }, priority = 26)
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
			ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder,Input.YYYYMMDDHHMISSDat);
			ingestionPage.IngestionCatlogtoCopying(Input.HiddenPropertiesFolder);
			ingestionPage.ingestionIndexing(Input.HiddenPropertiesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}
		baseClass.stepInfo("Search the documents and Save");
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocList();
		// Remove selected Colunm Add new Column
		docList.SelectColumnDisplayByRemovingExistingOnesAddMultiipleColumns();
	}
	
	/**
	 * Author :Vijaya.Rani date: 05/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-48013 Description :To Verify In Ingestion Overlays Ignore All
	 * Errors at Cataloge Stage, Should work.
	 * 
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 27)
	public void verifyIngestionOverlayIngareAllErrorsAndCatalogStage() throws InterruptedException {

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-48013");
		baseClass.stepInfo("To Verify In Ingestion Overlays Ignore All Errors at Cataloge Stage, Should work.");
		ingestionPage.OverlayIngestionWithoutDat(Input.AK_NativeFolder, "mp3", Input.MP3File);
		ingestionPage.ignoreErrorsAndCatlogging();

		// Rollback Ingestion
		ingestionPage.rollBackIngestion();
	}
	
	/**
	 * Author :Vijaya.Rani date: 06/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-48189 Description :To Verify Unpublish for Already published
	 * Documents after Ingestion.
	 * 
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 28)
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
	 * Author :Vijaya.Rani date: 10/5/2022 Modified date: Modified by: Description
	 * :To verify that the total unique count should not include the docs that have
	 * been unpublished. 'RPMXCON-49263'
	 * 
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 29)
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
		System.out.println(uniqueCountBefore);
		baseClass.stepInfo("Total unique count Before performing overlay : '" + uniqueCountBefore + "'");

		// Search ingestionName
		sessionSearch.basicSearchWithMetaDataQuery(Input.TiffImagesFolder, "IngestionName");
		sessionSearch.getPureHitAddButton().isElementAvailable(2);
		sessionSearch.getPureHitAddButton().waitAndClick(5);
		// Saved the My SavedSearch
		sessionSearch.saveSearch(BasicSearchName);
		// Go to UnpublishPage
		ingestionPage.navigateToUnPublishPage();
		ingestionPage.unpublish(BasicSearchName);

		int uniqueCountAfter = ingestionPage.getIngestedUniqueCount();
		System.out.println(uniqueCountAfter);
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
	@Test(alwaysRun = true, groups = { "regression" }, priority = 30)
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
	@Test(alwaysRun = true, groups = { "regression" }, priority = 31)
	public void verifyingestionOverlayWithoutUnpublish() throws InterruptedException {

		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		savedSearch = new SavedSearch(driver);
		DocViewPage docview = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-46875");
		baseClass.stepInfo("To Verify Ingestion Overlays of PDF without unpublish.");

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);

		ingestionPage = new IngestionPage_Indium(driver);
		boolean status = ingestionPage.verifyIngestionpublish(Input.HiddenPropertiesFolder);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Addonly saved ingestion with mapping field selection And Publish");
			ingestionPage.IngestionOnlyForDatFile(Input.HiddenPropertiesFolder,Input.YYYYMMDDHHMISSDat);
			ingestionPage.IngestionCatlogtoCopying(Input.HiddenPropertiesFolder);
			ingestionPage.ingestionIndexing(Input.HiddenPropertiesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}
		driver.Navigate().refresh();
		String ingestionName = ingestionPage.selectPublishedFromFilterDropDown(Input.HiddenPropertiesFolder);
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
	}
	
	
	/**
	 * Author :Vijaya.Rani date: 10/5/2022 Modified date: Modified by: 
	 * Description : Verify the overlay Ingestion for Audio Documents against International English language pack
	 * 'RPMXCON-48526'
	 * 
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 32)
	public void verifyAudioDocumentOverlayInternationEnglish() throws InterruptedException {
		
		baseClass = new BaseClass(driver);
		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		sessionsearch = new SessionSearch(driver);
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
	 * Author: Vijaya.Rani date: 04/05/2022 Modified date: NA Modified by: NA
	 * Description :Verify Ingestion should published successfully if Email metadata is having only Name.'RPMXCON-49569'
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 33)
	public void verifyIngestionEmailMetaDataOnlyName() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		DocListPage docList = new DocListPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49569");
		baseClass.stepInfo("Verify Ingestion should published successfully if Email metadata is having only Name.");
		String[] addEmailColumn = { "EmailAuthorNameAndAddress", "EmailBCCNamesAndAddresses", "EmailCCNamesAndAddresses", "EmailToNamesAndAddresses" };

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1FullName);
		
		baseClass.stepInfo("Navigate to ingestion page.");
		ingestionPage.nativigateToIngestionViaButton();
		
		boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
		String ingestionType="Add Only";
		
		
		if (status) {
			baseClass.stepInfo("Edit of addonly saved ingestion with mapping field selection");
			ingestionPage.IngestionRegressionForDifferentDAT(Input.GD994NativeTextForProductionFolder, ingestionType, Input.sourceSystem,
					Input.DATFile1, null, null, null, null, null, Input.MP3File, null, null);

		}
		
		driver.Navigate().refresh();
		String ingestionName = ingestionPage.selectPublishedFromFilterDropDown(Input.GD994NativeTextForProductionFolder);
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
	}
	loginPage.quitBrowser();

	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR INGESTION EXECUTED******");

	}

}
