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
	 * Author: Mohan date: 02/05/2022 Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-49521 Description :Verify that if PA ingested both TEXT and TIFF's
	 * file,the "Generate Searchable PDFs"is true and TIFF is missing then it should
	 * displays default PDF file
	 * 
	 * @throws InterruptedException
	 */
    @Test(enabled = true, groups = { "regression" }, priority = 1)
	public void verifyTEXTAndTIFFSFileGenerateSearchablePDFsIsTrue() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		String ingestionType="Add Only";
		baseClass.stepInfo("Test case Id: RPMXCON-49521");
		baseClass.stepInfo(
				"Verify that if PA ingested both TEXT and TIFF's file,the 'Generate Searchable PDFs' is true and TIFF is missing then it should displays default PDF file");
		boolean status = ingestionPage.verifyIngestionpublish(Input.PP_PDFGen_10Docs);
		System.out.println(status);

		if (status == false) {

			ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,ingestionType, "TRUE", Input.DATPPPDF10Docs, null,
					Input.TextPPPDF10Docs, null, Input.ImagePPPDF10docs,"select", null, null, null);
			
		}
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicSearchWithMetaDataQuery(Input.sourceDocIDPPPDF10Docs, "SourceDocID");
		sessionSearch.viewInDocView();
		DocViewPage docViewPage = new DocViewPage(driver);

		driver.waitForPageToBeReady();
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
	 * Description :Verify when user ingest only metadata, message like 'No files
	 * associated with this document' should be displayed on
	 * text/Images/Translations view.'RPMXCON-51236'
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 2)
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
	@Test(enabled = true, groups = { "regression" }, priority = 3)
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
	@Test(enabled = true, groups = { "regression" }, priority = 4)
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
	@Test(enabled = true, groups = { "regression" }, priority = 5)
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
     *Author: Mohan date: 06/05/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-50377
	 * Description :Verify that if PA ingested Native, PDF and TIFF's file and the "Generate Searchable PDFs" option is set to true, then PDF should be generated from the TIFF's
     * @throws InterruptedException 
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 6)
	public void verifyNativePDFAndTIFFSFileGenerateSearchablePDFsIsTrue() throws InterruptedException  {
		
	
		ingestionPage = new IngestionPage_Indium(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-50377");
		baseClass.stepInfo("Verify that if PA ingested Native, PDF and TIFF's file and the 'Generate Searchable PDFs' option is set to true, then PDF should be generated from the TIFF's");
		String ingestionType="Add Only";
		boolean status = ingestionPage.verifyIngestionpublishWithAdditionalOptions(Input.PP_PDFGen_10Docs,"50");
		System.out.println(status);

		if(status==false) {
		ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,ingestionType, "TRUE", "DAT4_STC_09172014_newdateformat_50Docs.dat", "Natives_50Docs.lst",
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
	 * 
	 * @throws InterruptedException
	 *
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 7)
	public void verifyFamilyMemberCountInDocList() throws InterruptedException {
		baseClass.stepInfo("Test case Id: RPMXCON-48195");
		baseClass.stepInfo("To Very the Family Member Counts After Ingestion completed successfully.");

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
	 * Author :Aathith date: 10/5/2022 Modified date: Modified by: Description
	 * :To verify that after Text overlay, if there are other file variants , then DocView follow the set precedence and 
	 * Text will reflect the overlaid text. 'RPMXCON-48607'
	 * 
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 8)
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
	@Test(alwaysRun = true, groups = { "regression" }, priority = 9)
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
	@Test(alwaysRun = true, groups = { "regression" }, priority = 10)
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
     *Author: Mohan date: 02/05/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49520
	 * Description :Verify that if PA ingested both PDF and TIFF's file,the "Generate Searchable PDFs"is true and TIFF is missing then it PDF should displays PDF in viewer
     * @throws InterruptedException 
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 11)
	public void verifyPDFAndTIFFSFileGenerateSearchablePDFsIsTrue() throws InterruptedException  {
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49520");
		baseClass.stepInfo("Verify that if PA ingested both PDF and TIFF's file,the 'Generate Searchable PDFs' is true and TIFF is missing then it PDF should displays PDF in viewer");
		String ingestionType="Overlay Only";
		
		ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,ingestionType, null, Input.DATPPPDF10Docs, null,
					null, "PDFs - 5Docs.lst", Input.ImagePPPDF10docs,"Select", null, null, null);
		ingestionPage.navigateToIngestionPage();
		
		driver.Navigate().refresh();
		String ingestionName = ingestionPage.selectPublishedFromFilterDropDown(Input.PP_PDFGen_10Docs);
		System.out.println(ingestionName);
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicSearchWithMetaDataQuery(ingestionName, "IngestionName");
		sessionSearch.viewInDocView();
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
	@Test(enabled = true,  groups = {"regression" },priority = 12)
	public void verifyNativeAndTIFFSFileGenerateSearchablePDFsIsTrue() throws InterruptedException  {
		
		ingestionPage = new IngestionPage_Indium(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-49519");
		baseClass.stepInfo("Verify that if PA ingested both Native and TIFF's file, the 'Generate Searchable PDFs' is true and TIFF is missing then searchable PDF's should be generated from the Natives.");
		String ingestionType="Overlay Only";
		
		ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,ingestionType, null, Input.DATPPPDF10Docs, "Natives -5Docs.lst",
					null,null , Input.ImagePPPDF10docs,"Select", null, null, null);
		ingestionPage.navigateToIngestionPage();
		
		driver.Navigate().refresh();
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
	@Test(enabled = true,  groups = {"regression" },priority = 13)
	public void verifyNativeAndTIFFSFileGenerateSearchablePDFsIsFalse() throws InterruptedException  {
		
		ingestionPage = new IngestionPage_Indium(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49509");
		baseClass.stepInfo("Verify that if PA ingested both native's and TIFF's file,and the \"Generate Searchable PDFs\" option is set to false then it should display TIFF in default viewer");
		String ingestionType="Overlay Only";
		
		ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,ingestionType, null, Input.DATPPPDF10Docs, "Natives -5Docs.lst",
					null,null , Input.ImagePPPDF10docs,null, null, null, null);
		ingestionPage.navigateToIngestionPage();
		
		driver.Navigate().refresh();
		driver.Navigate().refresh();
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
	
	/**
	 * @author Aathith
	 * @throws InterruptedException //@TestCase id: 49547 : Verify Count of Generate
	 *                              Searchable PDFs if 'Required PDF Generation' is
	 *                              TRUE and 'searchable PDF for TIFFs' is TRUE
	 */
	@Test(alwaysRun = true, groups = { "regression" }, priority = 14)
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
	@Test(alwaysRun = true, groups = { "regression" }, priority = 15)
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
	@Test(alwaysRun = true, groups = { "regression" }, priority = 16)
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
	@Test(alwaysRun = true, groups = { "regression" }, priority = 17)
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
	@Test(alwaysRun = true, groups = { "regression" }, priority = 18)
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
