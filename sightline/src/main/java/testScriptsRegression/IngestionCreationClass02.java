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
	@Test(description ="RPMXCON-51236",enabled = true, groups = { "regression" })
	public void verifyIngestMetaDataMessageDisplayTEXTFile() throws InterruptedException {

		IngestionPage_Indium ingestionPage = new IngestionPage_Indium(driver);
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
	@Test(description ="RPMXCON-51235",enabled = true, groups = { "regression" })
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
	@Test(description ="RPMXCON-51237",enabled = true, groups = { "regression" })
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
	@Test(description ="RPMXCON-51238",enabled = true, groups = { "regression" })
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
	@Test(description ="RPMXCON-48607",enabled = true, groups = { "regression" })
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
		dataSets.navigateToDataSetsPage();
		dataSets.selectDataSetWithName(Input.PP_PDFGen_10Docs);
		String docId=docList.getDocumetId();
		sessionSearch.basicSearchWithMetaDataQuery(docId, "DocID");
		sessionSearch.saveSearch(BasicSearchName);

		// Go to UnpublishPage
		ingestionPage.navigateToUnPublishPage();
		ingestionPage.unpublish(BasicSearchName);
		
		baseClass.stepInfo("Navigate to ingestion page.");
		ingestionPage.navigateToIngestionPage();
		
		baseClass.stepInfo("Select ingestion type and specify source loaction.");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.overlayOnly, null, Input.sourceLocation,
				Input.PDFGen_10Docs);

		baseClass.stepInfo("Select DAT delimiters.");
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);

		baseClass.stepInfo("Select DAT source.");
		ingestionPage.selectDATSource(Input.newdateformat_5Docs, Input.prodBeg);

		ingestionPage.selectTextSource(Input.TextPPPDF10Docs, false);

		baseClass.stepInfo("Select Date and Time format.");
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);

		baseClass.stepInfo("Click on next button.");
		ingestionPage.clickOnNextButton();

		baseClass.stepInfo("Click on preview and run button.");
		ingestionPage.clickOnPreviewAndRunButton();
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();

		dataSets.navigateToDataSetsPage();
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
	@Test(description ="RPMXCON-49262",enabled = true, groups = { "regression" })
	public void verifyUniqueCountNotIncludeUnpublished() throws InterruptedException {

		dataSets = new DataSets(driver);
		savedSearch = new SavedSearch(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocListPage docList= new DocListPage(driver);
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
		// getting unique ingested count before overlay
		int uniqueCountBefore = ingestionPage.getIngestedUniqueCount();
		System.out.println(uniqueCountBefore);
		baseClass.stepInfo("Total unique count before performing overlay : '" + uniqueCountBefore + "'");
				
		dataSets.selectDataSetWithName(Input.PP_PDFGen_10Docs);
		String docId = docList.getDocumetId();
		sessionSearch.basicSearchWithMetaDataQuery(docId, "DocID");
		sessionSearch.saveSearch(BasicSearchName);
		// Go to UnpublishPage
		ingestionPage.navigateToUnPublishPage();
		ingestionPage.unpublish(BasicSearchName);
		// perform overlay only ingestion with source system as Mapped data
		ingestionPage.navigateToIngestionPage();
		String ingestionType="Overlay Only";
		ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,ingestionType, "TRUE",  Input.DATPPPDF10Docs, null,
				Input.TextPPPDF10Docs, null, Input.ImagePPPDF10docs,"select", null, null, null);
		
		// getting unique ingested count after overlay
		ingestionPage.navigateToIngestionPage();
		int uniqueCountAfter = ingestionPage.getIngestedUniqueCount();
		baseClass.stepInfo("Total unique count After performing overlay : '" + uniqueCountAfter + "'");
		System.out.println(uniqueCountAfter);
		if (uniqueCountBefore == uniqueCountAfter) {
			baseClass.passedStep("Only unique ingested count displayed after performing  overlay");
		} else {
			baseClass.failedStep("Unique ingested count not displayed after performing  overlay");
		}
		loginPage.logout();
		
	}
	
	
	/**
	 * Author :Vijaya.Rani date: 11/5/2022 Modified date: Modified by: Description
	 * :To verify that after Text overlay, if there are no other file variants ,
	 * then DocView uses that text as the default viewer file for that document.
	 * 'RPMXCON-48606'
	 * 
	 */
	@Test(description ="RPMXCON-48606",enabled = true, groups = { "regression" })
	public void verifyOverlayDocViewTextWillReflectOverlaidText() throws InterruptedException {

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
		ingestionPage.navigateToIngestionPage();
		baseClass.stepInfo("Select ingestion type and specify source loaction.");
		ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.overlayOnly, Input.sourceSystem, Input.sourceLocation,
				Input.PDFGen_10Docs);

		baseClass.stepInfo("Select DAT delimiters.");
		ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);

		baseClass.stepInfo("Select DAT source.");
		ingestionPage.selectDATSource(Input.newdateformat_5Docs, Input.prodBeg);

		ingestionPage.selectTextSource(Input.TextPPPDF10Docs, false);

		baseClass.stepInfo("Select Date and Time format.");
		ingestionPage.selectDateAndTimeFormat(Input.dateFormat);

		baseClass.stepInfo("Click on next button.");
		ingestionPage.clickOnNextButton();

		baseClass.stepInfo("Click on preview and run button.");
		ingestionPage.clickOnPreviewAndRunButton();
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();

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
		loginPage.logout();

	}
	
	
	/**
	 * Author :Aathith date: NA Modified date: Modified by: Description : Verify
	 * that if PA ingested both Text's and TIFF's file,and the "Generate Searchable
	 * PDFs" option is set to False, then it should display TIFF in default viewer
	 */
	@Test(description ="RPMXCON-49511",enabled = true, groups = { "regression" })
	public void VerifyTiffImageInDefautViewer() throws InterruptedException {

		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-49511");
		baseClass.stepInfo(
				"Verify that if PA ingested both Text's and TIFF's file,and the \"Generate Searchable PDFs\" option is set to False, then it should display TIFF in default viewer");
		IngestionPage_Indium ingestion = new IngestionPage_Indium(driver);
		boolean status = ingestion.verifyIngestionpublish(Input.PDFGen_10Docs);
		if(!status) {
			ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,Input.ingestionType, "TRUE", Input.DATPPPDF10Docs, null,
					Input.TextPPPDF10Docs, null, Input.ImagePPPDF10docs,"select", null, null, null);
		}
		ingestion.navigateToDataSetsPage();
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
	@Test(description ="RPMXCON-49498",enabled = true, groups = { "regression" })
	public void verifyPdfandTiffInDocView() throws InterruptedException {

		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-49498");
		baseClass.stepInfo(
				"Verify that if PA ingested both native's and TIFF's file,and the \"Generate Searchable PDFs\" option is set to true, then PDF should be generated from the TIFF's only");
		
		IngestionPage_Indium ingestion = new IngestionPage_Indium(driver);
		boolean status = ingestion.verifyIngestionpublish(Input.PDFGen_10Docs);
		if(!status) {
			ingestion.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,Input.ingestionType, "TRUE", Input.DATPPPDF10Docs, null,
					Input.TextPPPDF10Docs, null, Input.ImagePPPDF10docs,"select", null, null, null);
		}
		ingestion.navigateToDataSetsPage();
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
	@Test(description ="RPMXCON-49510",enabled = true, groups = { "regression" })
	public void VerifySelectSearchablePDFTiffImageInDefautViewer() throws InterruptedException {

		
		dataSets = new DataSets(driver);
		sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-49510");
		baseClass.stepInfo(
				"Verify that if PA ingested both TIFF's and Text's file,and the 'Generate Searchable PDFs' option is set to true, then PDF should be generated from the TIFF's only.");

		IngestionPage_Indium ingestion = new IngestionPage_Indium(driver);
		boolean status = ingestion.verifyIngestionpublish(Input.PDFGen_10Docs);
		if(!status) {
			ingestion.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,Input.ingestionType, "TRUE", Input.DATPPPDF10Docs, null,
					Input.TextPPPDF10Docs, null, Input.ImagePPPDF10docs,"select", null, null, null);
		}
		ingestion.navigateToDataSetsPage();
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
	@Test(description ="RPMXCON-48277",enabled = true, groups = { "regression" })
	public void verifyUnpublishOverLayIngestion() throws InterruptedException {
		
		baseClass.stepInfo("Test case Id: RPMXCON-48277 ");
		baseClass.stepInfo(
				"###  To Verify unpublish for Overlay Ingestion. ###");
		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		sessionsearch = new SessionSearch(driver);
		docView = new DocViewPage(driver);
		IngestionPage_Indium ingestion = new IngestionPage_Indium(driver);
		String BasicSearchName = "search"+Utility.dynamicNameAppender();

		baseClass.stepInfo("Navigate to ingestion page.");
		ingestion.nativigateToIngestionViaButton();
		boolean status = ingestion.verifyIngestionpublish(Input.PDFGen_10Docs);
		if(!status) {
			ingestion.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,Input.ingestionType, "TRUE", Input.DATPPPDF10Docs, null,
					Input.TextPPPDF10Docs, null, Input.ImagePPPDF10docs,"select", null, null, null);
		}

			baseClass.stepInfo("Select ingestion type and specify source loaction.");
			ingestion.selectIngestionTypeAndSpecifySourceLocation(Input.overlayOnly, null, Input.sourceLocation,
					Input.PDFGen_10Docs);

			baseClass.stepInfo("Select DAT delimiters.");
			ingestion.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);

			baseClass.stepInfo("Select DAT source.");
			//Input.newdateformat_5Docs, Input.prodBeg used for dat file name and Document Key
			ingestion.selectDATSource(Input.newdateformat_5Docs, Input.prodBeg);

			baseClass.stepInfo("Select Pdf varient source.");
			//Input.PDF5DocsLst is used for pdf file Name
			ingestion.selectPDFSource(Input.PDF5DocsLst, false);
			
			baseClass.stepInfo("Select Tiff varient source.");
			//Input.Images5DocsLst used for Image loadfile name 
			ingestion.selectTIFFSource(Input.Images5DocsLst, false, true);

			baseClass.stepInfo("Select Date and Time format.");
			//Input.dateFormat used for choosing the stranded data format
			ingestion.selectDateAndTimeFormat(Input.dateFormat);

			baseClass.stepInfo("Click on next button.");
			ingestion.clickOnNextButton();

			baseClass.stepInfo("Click on preview and run button.");
			ingestion.clickOnPreviewAndRunButton();
			ingestion.verifyApprovedStatusForOverlayIngestion();
			ingestion.runFullAnalysisAndPublish();
			
			ingestion.navigateToDataSetsPage();

		
		String ingestionFullName = dataSets.isDataSetisAvailable(Input.PDFGen_10Docs);
		if(ingestionFullName!=null) {
			// Search ingestionName
			sessionsearch.basicSearchWithMetaDataQuery(ingestionFullName, "IngestionName");
			sessionsearch.saveSearch(BasicSearchName);
			sessionsearch.unReleaseDocsFromSecuritygroup(Input.securityGroup);
			// Go to UnpublishPage
			ingestion.navigateToUnPublishPage();
			ingestion.unpublish(BasicSearchName);
			
		}

		baseClass.passedStep(
				"Verified unpublish for Overlay Ingestion.");
		loginPage.logout();

	}
	
	/**
	 * Author :Aathith date: NA Modified date: Modified by: Description : Verify
	 * that if PA ingested both PDF and TIFF's file and the "Generate Searchable
	 * PDFs" option is set to true, then PDF should be generated from the TIFF's
	 */
	@Test(description ="RPMXCON-49499",enabled = true, groups = { "regression" })
	public void verifyJanTiffPdfandTiffInDocView() throws InterruptedException {

		dataSets = new DataSets(driver);
		sessionsearch = new SessionSearch(driver);
		docview = new DocViewPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-49499");
		baseClass.stepInfo("Verify that if PA ingested both PDF and TIFF's file"
				+ " and the \"Generate Searchable PDFs\" option is set to true, then PDF should be generated from the TIFF's");

		IngestionPage_Indium ingestion = new IngestionPage_Indium(driver);
		boolean status = ingestion.verifyIngestionpublish(Input.JanMultiPTIFF);
		if(!status) {
			ingestion.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem, Input.sourceLocation, Input.JanMultiPTIFF);
			ingestion.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);
			ingestion.selectDATSource("SearchablePDFGeneration.DAT", Input.documentKeyBNum);
			ingestion.selectPDFSource("PDF.lst",false);
			ingestion.selectTIFFSource("Image.lst",false,true);
			ingestion.selectDateAndTimeFormat(Input.dateFormat);
			ingestion.clickOnNextButton();
			ingestion.ingestionMapping(Input.documentKeyBNum, Input.documentKeyBNum, Input.documentKeyCName);
			ingestion.clickOnPreviewAndRunButton();
			ingestion.IngestionCatlogtoCopying(Input.JanMultiPTIFF);
			ingestion.ingestionIndexing(Input.JanMultiPTIFF);
			ingestion.approveIngestion(1);
			ingestion.runFullAnalysisAndPublish();
			
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
			if (docview.ProductionNameInImageTab(Input.JanMultiPTIFF).isElementAvailable(10)) {
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
	@Test(description ="RPMXCON-49500",enabled = true, groups = { "regression" })
	public void verifyJanTiffPdfandnotTiffInDocView() throws InterruptedException {

		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		sessionsearch = new SessionSearch(driver);
		docview = new DocViewPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-49500");
		baseClass.stepInfo(
				"Verify that if PA ingested both PDF and TIFF's file,the 'Generate Searchable PDFs' option is set to true, and if the Generation of the PDF from the TIFF's fails,then pre-existing PDF should be retained as the PDF file variant");
		IngestionPage_Indium ingestion = new IngestionPage_Indium(driver);
		boolean status = ingestion.verifyIngestionpublish(Input.JanMultiPTIFF);
		if(status == false) {
			ingestion.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem, Input.sourceLocation, Input.JanMultiPTIFF);
			ingestion.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);
			ingestion.selectDATSource("SearchablePDFGeneration.DAT", Input.documentKeyBNum);
			ingestion.selectPDFSource("PDF.lst",false);
			ingestion.selectTIFFSource("Image.lst",false,true);
			ingestion.selectDateAndTimeFormat(Input.dateFormat);
			ingestion.clickOnNextButton();
			ingestion.ingestionMapping(Input.documentKeyBNum, Input.documentKeyBNum, Input.documentKeyCName);
			ingestion.clickOnPreviewAndRunButton();
			ingestion.IngestionCatlogtoCopying(Input.JanMultiPTIFF);
			ingestion.ingestionIndexing(Input.JanMultiPTIFF);
			ingestion.approveIngestion(1);
			ingestion.runFullAnalysisAndPublish();
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
	 * Author :Vijaya.Rani date: 9/5/2022 Modified date: Modified by: Description :
	 * Verify that if "Generate Searchable PDFs" is TRUE, then Ingestion should
	 * generate successfully for Multi-page TIFF images.'RPMXCON-49502'
	 * 
	 */
	@Test(description ="RPMXCON-49502",enabled = true, groups = { "regression" })
	public void verifyJanMultiTiffPdfandTiffInDocView() throws InterruptedException {

		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		DocViewPage docview = new DocViewPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-49502");
		baseClass.stepInfo(
				"Verify that if 'Generate Searchable PDFs' is TRUE, then Ingestion should generate successfully for Multi-page TIFF images.");

		IngestionPage_Indium ingestion = new IngestionPage_Indium(driver);
		boolean status = ingestion.verifyIngestionpublish(Input.JanMultiPTIFF);
		if(status == false) {
			ingestion.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem, Input.sourceLocation, Input.JanMultiPTIFF);
			ingestion.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);
			ingestion.selectDATSource("SearchablePDFGeneration.DAT", Input.documentKeyBNum);
			ingestion.selectTIFFSource("Image.lst",false,true);
			ingestion.selectDateAndTimeFormat(Input.dateFormat);
			ingestion.clickOnNextButton();
			ingestion.ingestionMapping(Input.documentKeyBNum, Input.documentKeyBNum, Input.documentKeyCName);
			ingestion.clickOnPreviewAndRunButton();
			ingestion.IngestionCatlogtoCopying(Input.JanMultiPTIFF);
			ingestion.ingestionIndexing(Input.JanMultiPTIFF);
			ingestion.approveIngestion(1);
			ingestion.runFullAnalysisAndPublish();
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
	 * Author :Aathith date: NA Modified date: Modified by: 
	 * Description : Verify that if "Generate Searchable PDFs" is TRUE, then Ingestion should generate successfully for Single page TIFF images.
	 */
	@Test(description ="RPMXCON-49501",enabled = true, groups = { "regression" })
	public void verifyMarTiffPdfandTiffInDocView() throws InterruptedException {
		
		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		sessionsearch = new SessionSearch(driver);
		docview = new DocViewPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-49501");
		baseClass.stepInfo("Verify that if \"Generate Searchable PDFs\" is TRUE, then Ingestion should generate successfully for Single page TIFF images.");
		
		IngestionPage_Indium ingestion = new IngestionPage_Indium(driver);
		boolean status = ingestion.verifyIngestionpublish(Input.SinglePageTIFFFolder);
		if(!status) {
			ingestion.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem, Input.sourceLocation, Input.SinglePageTIFFFolder);
			ingestion.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);
			ingestion.selectDATSource("C2322695-9767-44C3-A290-B21B12B82A32_DAT.dat", Input.documentKeyBNum);
			ingestion.getTIFFSearchablePDFCheckBox().waitAndClick(10);
			ingestion.selectDateAndTimeFormat(Input.dateFormat);
			ingestion.clickOnNextButton();
			ingestion.ingestionMapping(Input.documentKeyBNum, Input.documentKeyBNum, Input.documentKeyCName);
			ingestion.clickOnPreviewAndRunButton();
			ingestion.IngestionCatlogtoCopying(Input.JanMultiPTIFF);
			ingestion.ingestionIndexing(Input.JanMultiPTIFF);
			ingestion.approveIngestion(1);
			ingestion.runFullAnalysisAndPublish();
			
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
	 * Author :Vijaya.Rani date: 29/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-49545 Description :Verify that value for all the metadata
	 * fields having DATETIME/DATE data type.
	 */
	@Test(description ="RPMXCON-49545",enabled = true, groups = { "regression" })
	public void verifyMetaDataFieldsDateTimeInDataType() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49545");
		baseClass.stepInfo("Verify that value for all the metadata fields having DATETIME/DATE data type.");
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocListPage docList = new DocListPage(driver);
		dataSets = new DataSets(driver);
		sessionsearch = new SessionSearch(driver);

		ingestionPage = new IngestionPage_Indium(driver);
		boolean status = ingestionPage.verifyIngestionpublish(Input.HiddenPropertiesFolder);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Addonly saved ingestion with mapping field selection And Publish");
			ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "MM/DD/YYYY",Input.DAT_MMDDYYYY_Slash,Input.Natives_MMDDYYYY_Slash);
			ingestionPage.ignoreErrorsAndCatlogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.HiddenPropertiesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}
		baseClass.stepInfo("Search the documents and Save");
		String ingestionFullName = dataSets.isDataSetisAvailable(Input.HiddenPropertiesFolder);
		sessionsearch.basicSearchWithMetaDataQuery(ingestionFullName, "IngestionName");
		sessionSearch.ViewInDocList();
		// Remove selected Colunm Add new Column
		docList.SelectColumnDisplayByRemovingExistingOnesAddMultiipleColumns();
		loginPage.logout();
	}
	
	/**
	 * Author :Vijaya.Rani date: 27/04/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-49902 Description :Unpublish documents - Verify Search as
	 * source.
	 */
	@Test(description ="RPMXCON-49902",enabled = true, groups = { "regression" })
	public void verifyUnpublishDocumentsSource() throws InterruptedException {

		SessionSearch sessionSearch = new SessionSearch(driver);
		savedSearch = new SavedSearch(driver);
		dataSets = new DataSets(driver);
		String BasicSearchName = "Newone" + Utility.dynamicNameAppender();

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49902");
		baseClass.stepInfo("Unpublish documents - Verify Search as source.");

		boolean status = ingestionPage.verifyIngestionpublish(Input.HiddenPropertiesFolder);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Addonly saved ingestion with mapping field selection And Publish");
			ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "MM/DD/YYYY",Input.DAT_MMDDYYYY_Slash,Input.Natives_MMDDYYYY_Slash);
			ingestionPage.ignoreErrorsAndCatlogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.HiddenPropertiesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}
		baseClass.stepInfo("Search the documents and Save");
		String ingestionFullName = dataSets.isDataSetisAvailable(Input.HiddenPropertiesFolder);
		sessionSearch.basicSearchWithMetaDataQuery(ingestionFullName, "IngestionName");
		sessionSearch.saveSearchSharedWithPA(BasicSearchName);

		// Go to UnpublishPage
		ingestionPage.navigateToUnPublishPage();
		ingestionPage.unpublish(BasicSearchName);
		loginPage.logout();
	}
	
	/**
	 * Author :Aathith date: NA Modified date: Modified by: 
	 * Description : To Verify In Ingestions, if the user tries to unpublish non-Nexidia indexed audio files, the unpublish should be successful.
	 */
	@Test(description ="RPMXCON-48260",enabled = true, groups = { "regression" })
	public void verifyUnpublishIndexedAudioFile() throws InterruptedException {
		
		dataSets = new DataSets(driver);
		sessionsearch = new SessionSearch(driver);
		docview = new DocViewPage(driver);
		IngestionPage_Indium ingestion = new IngestionPage_Indium(driver);
		String BasicSearchName = "search"+Utility.dynamicNameAppender();
		
		baseClass.stepInfo("Test case Id: RPMXCON-48260");
		baseClass.stepInfo("To Verify In Ingestions, if the user tries to unpublish non-Nexidia indexed audio files, the unpublish should be successful.");
		
		boolean status = ingestion.verifyIngestionpublish(Input.AK_NativeFolder);
		System.out.println(status);
		if (status == false) {
			ingestion.performAKNativeFolderIngestion(Input.DATFile1);
			ingestion.ignoreErrorsAndCatlogging();
			ingestion.ignoreErrorsAndCopying();
			ingestion.ingestionIndexing(Input.AK_NativeFolder);
			ingestion.approveIngestion(1);
			ingestion.runFullAnalysisAndPublish();
			
		}
		
		String ingestionFullName = dataSets.isDataSetisAvailable(Input.AK_NativeFolder);
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
	 * Author :Vijaya.Rani date: 10/5/2022 Modified date: Modified by: Description
	 * :To verify that the total unique count should not include the docs that have
	 * been unpublished. 'RPMXCON-49263'
	 * 
	 */
	@Test(description ="RPMXCON-49263",enabled = true, groups = { "regression" })
	public void verifyTotalUniqueCountAfterUnpublished() throws InterruptedException {

		dataSets = new DataSets(driver);
		savedSearch = new SavedSearch(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		DocListPage doclist = new DocListPage(driver);
		String BasicSearchName = "Search" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id: RPMXCON-49263");
		baseClass.stepInfo(
				"To verify that the total unique count should not include the docs that have been unpublished.");

		
		IngestionPage_Indium ingestionPage = new IngestionPage_Indium(driver);
		
		boolean status = ingestionPage.verifyIngestionpublish(Input.SinglePageTIFFFolder);
		if(!status) {
			ingestionPage.selectIngestionTypeAndSpecifySourceLocation(Input.ingestionType, Input.sourceSystem, Input.sourceLocation, Input.SinglePageTIFFFolder);
			ingestionPage.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);
			ingestionPage.selectDATSource("C2322695-9767-44C3-A290-B21B12B82A32_DAT.dat", Input.documentKeyBNum);
			ingestionPage.getTIFFSearchablePDFCheckBox().waitAndClick(10);
			ingestionPage.selectDateAndTimeFormat(Input.dateFormat);
			ingestionPage.clickOnNextButton();
			ingestionPage.ingestionMapping(Input.documentKeyBNum, Input.documentKeyBNum, Input.documentKeyCName);
			ingestionPage.clickOnPreviewAndRunButton();
			ingestionPage.ignoreErrorsAndCatlogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.JanMultiPTIFF);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
			
		}
		
		// getting before unique count
		int uniqueCountBefore = ingestionPage.getIngestedUniqueCount();
		System.out.println(uniqueCountBefore);
		baseClass.stepInfo("Total unique count Before performing overlay : '" + uniqueCountBefore + "'");
		
		dataSets.navigateToDataSetsPage();
		dataSets.selectDataSetWithName(Input.SinglePageTIFFFolder);
		String docId=doclist.getDocumetId();
		sessionSearch.basicSearchWithMetaDataQuery(docId, "DocID");
		sessionSearch.saveSearch(BasicSearchName);

		// Go to UnpublishPage
		ingestionPage.navigateToUnPublishPage();
		ingestionPage.unpublish(BasicSearchName);

		int uniqueCountAfter = ingestionPage.getIngestedUniqueCount();
		System.out.println(uniqueCountAfter);
		baseClass.stepInfo("Total unique count after performing overlay : '" + uniqueCountAfter + "'");

		if (uniqueCountBefore == uniqueCountAfter) {
			baseClass.failedStep("Total Unique Count included the document that have been unpublished ");
		} else {
			baseClass.passedStep("Total Unique Count not included the document that have been unpublished ");
		}
		loginPage.logout();
	}

	/**
	 * Author :Vijaya.Rani date: 10/5/2022 Modified date: Modified by: Description
	 * :To Verify Full Analytics run successfully in Ingestion for Overlays Mode and
	 * all the Metadata Updated in Overlays should get displayed after Overlay's
	 * Successful. 'RPMXCON-48084'
	 * 
	 */
	@Test(description ="RPMXCON-48084",enabled = true, groups = { "regression" })
	public void verifyFullAnalyticsRunIngestionForOverlay() throws InterruptedException {

		dataSets = new DataSets(driver);
		savedSearch = new SavedSearch(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		String BasicSearchName = "Test" + Utility.dynamicNameAppender();

		baseClass.stepInfo("Test case Id: RPMXCON-48084");
		baseClass.stepInfo(
				"To Verify Full Analytics run successfully in Ingestion for Overlays Mode and all the Metadata Updated in Overlays should get displayed after Overlay's Successful.");


		IngestionPage_Indium ingestionPage = new IngestionPage_Indium(driver);

		boolean status = ingestionPage.verifyIngestionpublish(Input.UniCodeFilesFolder);
		System.out.println(status);

		if (status == false) {

			ingestionPage.unicodeFilesIngestion(Input.datLoadFile1, Input.textFile1, Input.documentKey);
			ingestionPage.ignoreErrorsAndCatlogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.UniCodeFilesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
			
		}
		int beforeOverlayCount = ingestionPage.getIngestedUniqueCount();
		System.out.println(beforeOverlayCount);
		
		String ingestionFullName = dataSets.isDataSetisAvailable(Input.UniCodeFilesFolder);
		// Search ingestionName
		sessionSearch.basicSearchWithMetaDataQuery(ingestionFullName, "IngestionName");
		// Saved the My SavedSearch
		sessionSearch.saveSearch(BasicSearchName);
		sessionSearch.unReleaseDocsFromSecuritygroup(Input.securityGroup);
		
		// Go to UnpublishPage
		ingestionPage.navigateToUnPublishPage();
		ingestionPage.unpublish(BasicSearchName);
		// perform overlay with same data
		ingestionPage.OverlayIngestionWithoutDat(Input.UniCodeFilesFolder, "text", Input.textFile1);
		ingestionPage.verifyApprovedStatusForOverlayIngestion();
		ingestionPage.runFullAnalysisAndPublish();
		
		int afterOverlayCount =ingestionPage.getIngestedUniqueCount();
		System.out.println(afterOverlayCount);
		
		if (beforeOverlayCount == afterOverlayCount) {
			baseClass.passedStep("Metadata Updated in Overlays displayed after Overlay's Successfully");
		} else {
			baseClass.failedStep(" Metadata Updated in Overlays not displayed after Overlay's");
		}
		loginPage.logout();
	}
	
	

	/**
	 * Author :Vijaya.Rani date: 10/5/2022 Modified date: Modified by: Description:
	 * To Verify Ingestion Overlays of PDF without unpublish. 'RPMXCON-46875'
	 * 
	 */
	@Test(description ="RPMXCON-46875",enabled = true, groups = { "regression" })
	public void verifyingestionOverlayWithoutUnpublish() throws InterruptedException {

		dataSets = new DataSets(driver);
		savedSearch = new SavedSearch(driver);
		DocViewPage docview = new DocViewPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-46875");
		baseClass.stepInfo("To Verify Ingestion Overlays of PDF without unpublish.");


		IngestionPage_Indium ingestionPage = new IngestionPage_Indium(driver);
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Performing add only ingestion");
			ingestionPage.allSourcesIngestionWithText(Input.DATFile, Input.prodBeg);
			ingestionPage.ignoreErrorsAndCatlogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.AllSourcesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
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
	 * 
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49569",enabled = true, groups = { "regression" })
	public void verifyIngestionEmailMetaDataOnlyName() throws InterruptedException {

		dataSets = new DataSets(driver);
		DocListPage docList = new DocListPage(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49569");
		baseClass.stepInfo("Verify Ingestion should published successfully if Email metadata is having only Name.");
		String[] addEmailColumn = { "EmailAuthorNameAndAddress", "EmailBCCNamesAndAddresses", "EmailCCNamesAndAddresses", "EmailToNamesAndAddresses" };
		
		IngestionPage_Indium ingestionPage = new IngestionPage_Indium(driver);
		boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Performing add only ingestion");
			ingestionPage.IngestionOnlyForDatFile(Input.GD994NativeTextForProductionFolder, Input.datFormatFile);
			ingestionPage.ignoreErrorsAndCatlogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.GD994NativeTextForProductionFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
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
	*
	*/
	@Test(description ="RPMXCON-49567",enabled = true, groups = { "regression" })
	public void verifyingNamesAndAddressesMetadataInDocListPage() throws InterruptedException {
		
	baseClass.stepInfo("Test case Id: RPMXCON-49567");
	baseClass.stepInfo("Verify Ingestion with Email metadata if 'NamesAndAddresses' with different format");

	IngestionPage_Indium ingestionPage = new IngestionPage_Indium(driver);
	
	boolean status = ingestionPage.verifyIngestionpublish(Input.GD994NativeTextForProductionFolder);
	System.out.println(status);
	if (status == false) {
		baseClass.stepInfo("Performing add only ingestion");
		ingestionPage.IngestionOnlyForDatFile(Input.GD994NativeTextForProductionFolder, Input.datFormatFile);
		ingestionPage.ignoreErrorsAndCatlogging();
		ingestionPage.ignoreErrorsAndCopying();
		ingestionPage.ingestionIndexing(Input.GD994NativeTextForProductionFolder);
		ingestionPage.approveIngestion(1);
		ingestionPage.runFullAnalysisAndPublish();
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

		dataSets = new DataSets(driver);
		sessionsearch = new SessionSearch(driver);
		SoftAssert sofassertion = new SoftAssert();

		baseClass.stepInfo("Test case Id: RPMXCON-49802");
		baseClass.stepInfo(
				"To verify that if Email data contained space before the '@' sign , it should not calculate two distinct values");
		IngestionPage_Indium ingestionPage = new IngestionPage_Indium(driver);
		boolean status = ingestionPage.verifyIngestionpublish(Input.IngestionEmailDataFolder);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Performing add only ingestion");
			ingestionPage.IngestionOnlyForDatFile(Input.IngestionEmailDataFolder, Input.emailDatFile);
			ingestionPage.ignoreErrorsAndCatlogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.IngestionEmailDataFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}
		
		String ingestionFullName = dataSets.isDataSetisAvailable(Input.IngestionEmailDataFolder);
		if (ingestionFullName != null) {
			baseClass.stepInfo(ingestionFullName + "Ingestion already published in this project");
			int count = sessionsearch.MetaDataSearchInBasicSearch(Input.emailAuthorDomain, " @");
			sessionsearch.addNewSearch();
			int count1 = sessionsearch.newMetaDataSearchInBasicSearch(Input.emailAuthorDomain, " @ ");
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

		
		dataSets = new DataSets(driver);
		sessionsearch = new SessionSearch(driver);
		tagandfolder = new TagsAndFoldersPage(driver);
		doclist = new DocListPage(driver);
		docview = new DocViewPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-49558");
		baseClass.stepInfo("Verify Email metadata in DocList and in DocView");

		String foldername = "IngestionFolder" + Utility.dynamicNameAppender();
		String[] addEmailColumn = { "EmailAuthorName", "EmailAuthorAddress", "EmailToNames", "EmailToAddresses",
				"EmailCCNames", "EmailCCAddresses", "EmailBCCNames", "EmailBCCAddresses" };
		tagandfolder.CreateFolder(foldername, Input.securityGroup);

		IngestionPage_Indium ingestion = new IngestionPage_Indium(driver);
		boolean status = ingestion.verifyIngestionpublish(Input.IngestionEmailDataFolder);
		if(status ==false) {
			ingestionPage.IngestionOnlyForDatFile(Input.IngestionEmailDataFolder, Input.emailDatFile);
			ingestionPage.ignoreErrorsAndCatlogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.IngestionEmailDataFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
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
	 * Author :Aathith date: NA Modified date: Modified by: 
	 * Description : To verify that for image based document Sightline should receive 'RequirePDFGeneration' as set to 'true', 
	 * by ICE and 'RequirePDFGeneration' metadata should be displays in Doc View
	 */
	@Test(description ="RPMXCON-49364",enabled = true, groups = { "regression" })
	public void verifyMetaDataRequiredPDFGenereationIsTrue() throws InterruptedException {
		
		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		sessionsearch = new SessionSearch(driver);
		docview = new DocViewPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-49364");
		baseClass.stepInfo("To verify that for image based document Sightline should receive 'RequirePDFGeneration' as set to 'true',"
				+ " by ICE and 'RequirePDFGeneration' metadata should be displays in Doc View");
		
		IngestionPage_Indium ingestion = new IngestionPage_Indium(driver);
		boolean status = ingestion.verifyIngestionpublish(Input.GNon_searchable_PDF_Load_file);
		if(!status) {
			
			ingestion.IngestionOnlyForDatFile(Input.GNon_searchable_PDF_Load_file, Input.nonSearchablePdfLoadFile);
			ingestion.ignoreErrorsAndCatlogging();
			ingestion.ignoreErrorsAndCopying();
			ingestion.ingestionIndexing(Input.GNon_searchable_PDF_Load_file);
			ingestion.approveIngestion(1);
			ingestion.runFullAnalysisAndPublish();
			
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
	 * Author :Vijaya.Rani date: 06/05/2022 Modified date: NA Modified by: NA Test
	 * Case Id:RPMXCON-48189 Description :To Verify Unpublish for Already published
	 * Documents after Ingestion.
	 * 
	 */
	@Test(description ="RPMXCON-48189",enabled = true, groups = { "regression" })
	public void verifyUnpublishForAlreadyPublishedDocsIngestion() throws InterruptedException {

		dataSets = new DataSets(driver);
		SessionSearch sessionSearch = new SessionSearch(driver);
		savedSearch = new SavedSearch(driver);

		baseClass.stepInfo("Test case Id: RPMXCON-48189");
		baseClass.stepInfo("To Verify Unpublish for Already published Documents after Ingestion.");
		String BasicSearchName = "Newone" + Utility.dynamicNameAppender();
		IngestionPage_Indium ingestion = new IngestionPage_Indium(driver);
		
		boolean status = ingestion.verifyIngestionpublish(Input.UniCodeFilesFolder);
		if(!status) {
			
			ingestion.unicodeFilesIngestion(Input.datLoadFile1, Input.textFile1, Input.documentKey);
			ingestion.ignoreErrorsAndCatlogging();
			ingestion.ignoreErrorsAndCopying();
			ingestion.ingestionIndexing(Input.UniCodeFilesFolder);
			ingestion.approveIngestion(1);
			ingestion.runFullAnalysisAndPublish();
			
		}
		String ingestionFullName = dataSets.isDataSetisAvailable(Input.UniCodeFilesFolder);
		
		sessionSearch.basicSearchWithMetaDataQuery(ingestionFullName, "IngestionName");
		// Saved the My SavedSearch
		sessionSearch.saveSearch(BasicSearchName);
		sessionSearch.unReleaseDocsFromSecuritygroup(Input.securityGroup);

		// Go to UnpublishPage
		ingestion.navigateToUnPublishPage();
		ingestion.unpublish(BasicSearchName);
		
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
		
		
		baseClass.stepInfo("Test case Id: RPMXCON-46894 ");
		baseClass.stepInfo(
				"### Verify the overlay Ingestion for Audio Documents against International English language pack. ###");
		
		IngestionPage_Indium ingestionPage = new IngestionPage_Indium(driver);
		
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Performing add only ingestion");
			ingestionPage.allSourcesIngestionWithText(Input.DATFile, Input.prodBeg);
			ingestionPage.ignoreErrorsAndCatlogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.AllSourcesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
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
		
		baseClass = new BaseClass(driver);
		baseClass = new BaseClass(driver);
		dataSets = new DataSets(driver);
		sessionsearch = new SessionSearch(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-48526 ");
		baseClass.stepInfo(
				"### Verify the overlay Ingestion for Audio Documents against International English language pack ###");
		IngestionPage_Indium ingestionPage = new IngestionPage_Indium(driver);
		boolean status = ingestionPage.verifyIngestionpublish(Input.AllSourcesFolder);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Performing add only ingestion");
			ingestionPage.allSourcesIngestionWithText(Input.DATFile, Input.prodBeg);
			ingestionPage.ignoreErrorsAndCatlogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.AllSourcesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
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
	 * ////@TestCase id: 49550 : Verify that in Ingestion Overlay if 'Generate Searchable PDFs'
	 *  is selected in TIFF section, then PDF should be generated from the TIFF's
	 */
	@Test(description ="RPMXCON-49550",enabled = true, groups = { "regression" })
	public void verifySearchablePdfTiffDocView() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49550 ");
		baseClass.stepInfo(
				"###  Verify that in Ingestion Overlay if 'Generate Searchable PDFs' is selected in TIFF section, then PDF should be generated from the TIFF's ###");
		IngestionPage_Indium ingestion = new IngestionPage_Indium(driver);

		boolean status = ingestion.verifyIngestionpublish(Input.TiffImagesFolder);
		if(!status) {
			ingestion.tiffImagesIngestion(Input.DATFile2, Input.tiffLoadFile, "false");
			ingestion.ignoreErrorsAndCatlogging();
			ingestion.ignoreErrorsAndCopying();
			ingestion.ingestionIndexing(Input.TiffImagesFolder);
			ingestion.approveIngestion(1);
			ingestion.runFullAnalysisAndPublish();
			
		}
		// Perform overlay ingestion
		ingestion.selectIngestionTypeAndSpecifySourceLocation("Overlay Only", Input.sourceSystem, Input.sourceLocation, Input.TiffImagesFolder);
		ingestion.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);
		ingestion.selectDATSource(Input.DATFile2, Input.prodBeg);
		ingestion.getTIFFLST().selectFromDropdown().selectByVisibleText(Input.tiffLoadFile);
		ingestion.getTIFFSearchablePDFCheckBox().isElementAvailable(10);
		ingestion.getTIFFSearchablePDFCheckBox().waitAndClick(5);
		ingestion.selectDateAndTimeFormat(Input.dateFormat);
		ingestion.clickOnNextButton();
		ingestion.clickOnPreviewAndRunButton();
		ingestion.ignoreErrorsAndCatlogging();
		ingestion.ignoreErrorsAndCopying();
		ingestion.ingestionIndexing(Input.TiffImagesFolder);
		ingestion.approveIngestion(1);
		ingestion.runFullAnalysisAndPublish();
		
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

		dataSets = new DataSets(driver);
		sessionsearch = new SessionSearch(driver);
		docview = new DocViewPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-49491");
		baseClass.stepInfo(
				"Verified that if \"Generate Searchable PDF \" check box is not selected in the TIFF section, Ingestion should generate successfully with TIFF images only");

		IngestionPage_Indium ingestion = new IngestionPage_Indium(driver);
		boolean status = ingestion.verifyIngestionpublish(Input.TiffImagesFolder);
		if(!status) {
			ingestion.tiffImagesIngestion("DAT4_STC_NewDateFormat - Copy.dat", Input.tiffLoadFile, "false");
			ingestion.ignoreErrorsAndCatlogging();
			ingestion.ignoreErrorsAndCopying();
			ingestion.ingestionIndexing(Input.TiffImagesFolder);
			ingestion.approveIngestion(1);
			ingestion.runFullAnalysisAndPublish();
			
		}
		String ingestionFullName = dataSets.isDataSetisAvailable(Input.TiffImagesFolder);
		if (ingestionFullName != null) {
			dataSets.selectDataSetWithNameInDocView(Input.TiffImagesFolder);
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
	 * @author Aathith
	 * @throws InterruptedException //@TestCase id: 49547 : Verify Count of Generate
	 *                              Searchable PDFs if 'Required PDF Generation' is
	 *                              TRUE and 'searchable PDF for TIFFs' is TRUE
	 */
	@Test(description ="RPMXCON-49547",enabled = true, groups = { "regression" })
	public void verifySearchablePdfCount() throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-49547 ");
		baseClass.stepInfo(
				"###  Verify Count of Generate Searchable PDFs if 'Required PDF Generation' is TRUE and 'searchable PDF for TIFFs' is TRUE ###");
		IngestionPage_Indium ingestion = new IngestionPage_Indium(driver);
		
		boolean status = ingestion.verifyIngestionpublish(Input.PDFGen_10Docs);
		if(!status) {
			ingestion.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,Input.ingestionType, "TRUE", Input.DATPPPDF10Docs, null,
					Input.TextPPPDF10Docs, null, Input.ImagePPPDF10docs,"select", null, null, null);
		}

			baseClass.stepInfo("Select ingestion type and specify source loaction.");
			ingestion.selectIngestionTypeAndSpecifySourceLocation(Input.overlayOnly, Input.sourceSystem, Input.sourceLocation,
					Input.PDFGen_10Docs);

			baseClass.stepInfo("Select DAT delimiters.");
			ingestion.addDelimitersInIngestionWizard(Input.fieldSeperator, Input.textQualifier, Input.multiValue);

			baseClass.stepInfo("Select DAT source.");
			ingestion.selectDATSource(Input.newdateformat_5Docs, Input.prodBeg);

			baseClass.stepInfo("Select MP3 varient source.");
			ingestion.selectPDFSource(Input.PDF5DocsLst, false);

			ingestion.selectTIFFSource(Input.Images5DocsLst, false, true);

			baseClass.stepInfo("Select Date and Time format.");
			ingestion.selectDateAndTimeFormat(Input.dateFormat);

			baseClass.stepInfo("Click on next button.");
			ingestion.clickOnNextButton();

			baseClass.stepInfo("Click on preview and run button.");
			ingestion.clickOnPreviewAndRunButton();
			ingestion.verifyApprovedStatusForOverlayIngestion();
			
			baseClass.stepInfo("Verify count of searchable pdf");
			ingestion.searchablePdfCountCheck();

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

		
		baseClass.stepInfo("Test case Id: RPMXCON-48013");
		baseClass.stepInfo("To Verify In Ingestion Overlays Ignore All Errors at Cataloge Stage, Should work.");
		
		String foldername = "RedactionFolder" + Utility.dynamicNameAppender();
		String BasicSearchName = "Search" + Utility.dynamicNameAppender();
		tagandfolder = new TagsAndFoldersPage(driver);
		tagandfolder.CreateFolder(foldername, Input.securityGroup);
		SessionSearch sessionsearch = new SessionSearch(driver);
		dataSets = new DataSets(driver);
		IngestionPage_Indium ingestionPage = new IngestionPage_Indium(driver);
		
		boolean status = ingestionPage.verifyIngestionpublish(Input.HiddenPropertiesFolder);
		System.out.println(status);
		if (status == false) {
			baseClass.stepInfo("Addonly saved ingestion with mapping field selection And Publish");
			ingestionPage.IngestionRegressionForDateFormate(Input.HiddenPropertiesFolder, "MM/DD/YYYY",Input.DAT_MMDDYYYY_Slash,Input.Natives_MMDDYYYY_Slash);
			ingestionPage.ignoreErrorsAndCatlogging();
			ingestionPage.ignoreErrorsAndCopying();
			ingestionPage.ingestionIndexing(Input.HiddenPropertiesFolder);
			ingestionPage.approveIngestion(1);
			ingestionPage.runFullAnalysisAndPublish();
		}
		
		String ingestionFullName = dataSets.isDataSetisAvailable(Input.HiddenPropertiesFolder);
		sessionsearch.MetaDataSearchInBasicSearch(Input.metadataIngestion, ingestionFullName);
		sessionsearch.saveSearch(BasicSearchName);
		sessionsearch.bulkFolderExisting(foldername);
		sessionsearch.bulkReleaseIngestions(Input.securityGroup);
		
		loginPage.logout();
		// perform redaction
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		tagandfolder.selectFolderViewInDocView(foldername);
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
